package com.wff.service.impl;


import com.alibaba.fastjson.JSON;
import com.wff.common.entity.Result;
import com.wff.dao.SkuEsMapper;
import com.wff.search.feign.ItemFeign;
import com.wff.search.pojo.SkuInfo;
import com.wff.sellergoods.pojo.Item;
import com.wff.service.SkuService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class SkuServiceImpl implements SkuService {
    @Autowired
    private SkuEsMapper skuEsMapper;

    @Autowired
    private ItemFeign itemFeign;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public void importSku() {
        Result<List<Item>> result = itemFeign.findByStatus("1");
        List<SkuInfo> skuInfoList = JSON.parseArray(JSON.toJSONString(result.getData()), SkuInfo.class);

        //遍历sku集合
        for (SkuInfo skuInfo : skuInfoList) {
            //获取规格
            Map<String, Object> specMap = JSON.parseObject(skuInfo.getSpec());
            //关联设置到specMap
            skuInfo.setSpecMap(specMap);
        }

        //保存sku集合数据到es
        skuEsMapper.saveAll(skuInfoList);
    }

    @Override
    public Map search(Map<String, String> searchMap) {
        //获取关键字
        String keyWord = searchMap.get("keyWord");

        if (StringUtils.isEmpty(keyWord)) {
            keyWord = "华为";
        }

        //构建查询条件
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //分组
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuCategorygroup").field("category"));
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuBrandgroup").field("brand"));
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuSpecgroup").field("spec.keyword").size(100));
        //match
        nativeSearchQueryBuilder.withHighlightFields(new HighlightBuilder.Field("title"));
        nativeSearchQueryBuilder.withHighlightBuilder(new HighlightBuilder().preTags("<em style=\"color:red\">").postTags("</em>"));
        nativeSearchQueryBuilder.withQuery(QueryBuilders.multiMatchQuery(keyWord,"title","brand","category"));
        //过滤 filter
        nativeSearchQueryBuilder.withFilter(buildSpec(searchMap));

        //分页查询
        String stringNum = searchMap.get("pageNum");
        if (stringNum == null) {
            stringNum = "1";
        }
        Integer pageNum = Integer.parseInt(stringNum);
        Integer pageSize = 20;
        nativeSearchQueryBuilder.withPageable(PageRequest.of(pageNum - 1, pageSize));

        //排序
        String sortRule = searchMap.get("sortRule");
        String sortField = searchMap.get("sortField");
        if (!StringUtils.isEmpty(sortRule) && !StringUtils.isEmpty(sortField)){
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort(sortField).order(sortRule.equals("DESC") ? SortOrder.DESC : SortOrder.ASC));
        }

        //构建查询对象
        NativeSearchQuery query = nativeSearchQueryBuilder.build();

        //执行查询
        SearchHits<SkuInfo> hits = elasticsearchRestTemplate.search(query, SkuInfo.class);
        SearchPage<SkuInfo> hitsPage = SearchHitSupport.searchPageFor(hits, query.getPageable());
        Terms terms = hits.getAggregations().get("skuCategorygroup");
        Terms brandTerms = hits.getAggregations().get("skuBrandgroup");
        Terms specTerms = hits.getAggregations().get("skuSpecgroup");

        //SearchHits<SkuInfo> ===> Map
        List<SkuInfo> skus = new ArrayList<>();
        for (SearchHit<SkuInfo> skuInfoSearchHit : hitsPage.getContent()) {
            SkuInfo content = skuInfoSearchHit.getContent();
            SkuInfo skuInfo = new SkuInfo();
            BeanUtils.copyProperties(content, skuInfo);
            //高亮
            Map<String,List<String>> highlightField = skuInfoSearchHit.getHighlightFields();
            for(Map.Entry<String,List<String>> highlight: highlightField.entrySet()){
                String key = highlight.getKey();
                if("title".equalsIgnoreCase(key)){
                    List<String> fragments = highlight.getValue();
                    StringBuilder sb = new StringBuilder();
                    for (String fragment : fragments) {
                        sb.append(fragment.toString());
                    }
                    skuInfo.setTitle(sb.toString());
                }
            }
            skus.add(skuInfo);
        }

        //品牌
        List<String> brands = new ArrayList<>();
        if (terms != null) {
            for (Terms.Bucket bucket : brandTerms.getBuckets()) {
                String brand = bucket.getKeyAsString();
                brands.add(brand);
            }
        }

        //分类
        List<String> catGories = new ArrayList<>();
        if (terms != null) {
            for (Terms.Bucket bucket : terms.getBuckets()) {
                String cat = bucket.getKeyAsString();
                catGories.add(cat);
            }
        }

        //规格
        Map<String,Set<String>> specMap = packSkuSpec(specTerms);

        //返回结果
        Map resultMap = new HashMap();
        resultMap.put("rows", skus);
        resultMap.put("total", hitsPage.getTotalElements());
        resultMap.put("totalPages", hitsPage.getTotalPages());
        resultMap.put("categoryList", catGories);
        resultMap.put("brandList", brands);
        resultMap.put("specMap", specMap);
        return resultMap;
    }

    //分类规格封装
    private Map<String, Set<String>> packSkuSpec(Terms terms){
        Map<String,Set<String>> specMap = new HashMap<>();
        Set<String> spec = new HashSet<>();
        if (terms != null) {
            for (Terms.Bucket bucket : terms.getBuckets()) {
                spec.add(bucket.getKeyAsString());
            }
        }
        for (String specjson : spec) {
            Map<String, String> map = JSON.parseObject(specjson, Map.class);
            for (Map.Entry<String, String> entry : map.entrySet()) {//
                String key = entry.getKey();
                String value = entry.getValue();
                Set<String> specValues = specMap.get(key);
                if (specValues == null) {
                    specValues = new HashSet<String>();
                }
                //将当前规格加入到集合中
                specValues.add(value);
                //将数据存入到specMap中
                specMap.put(key, specValues);
            }
        }
        return specMap;
    }

    private BoolQueryBuilder buildSpec(Map<String, String> searchMap) {
        //获取规格信息 构建查询条件
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        if (searchMap != null) {
            for (String key : searchMap.keySet()) {
                if (key.startsWith("spec_")) {
                    boolQueryBuilder.filter(QueryBuilders.termQuery("specMap." + key.substring(5) + ".keyword", searchMap.get(key)));
                }
            }
        }
        //分类过滤
        if (!StringUtils.isEmpty(searchMap.get("category"))) {
            boolQueryBuilder.filter(QueryBuilders.matchQuery("category", searchMap.get("category")));
        }
        //品牌过滤
        if (!StringUtils.isEmpty(searchMap.get("brand"))) {
            boolQueryBuilder.filter(QueryBuilders.matchQuery("brand", searchMap.get("brand")));
        }
        //价格过滤
        String price = searchMap.get("price");
        if (!StringUtils.isEmpty(price)) {
            String[] prices = price.split("-");
            if (!prices[1].equalsIgnoreCase("*")) {
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").from(prices[0], true).to(prices[1], false));
            } else {
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(prices[0]));
            }
        }
        return boolQueryBuilder;
    }


}
