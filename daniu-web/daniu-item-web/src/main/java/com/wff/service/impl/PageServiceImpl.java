package com.wff.service.impl;

import com.alibaba.fastjson.JSON;
import com.wff.common.entity.Result;
import com.wff.sellergoods.entity.GoodEntity;
import com.wff.sellergoods.feign.GoodsFeign;
import com.wff.sellergoods.feign.ItemCatFeign;
import com.wff.sellergoods.pojo.Goods;
import com.wff.sellergoods.pojo.GoodsDesc;
import com.wff.sellergoods.pojo.Item;
import com.wff.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PageServiceImpl implements PageService {
    @Autowired
    private GoodsFeign goodsFeign;

    @Autowired
    private ItemCatFeign itemCatFeign;

    @Autowired
    private TemplateEngine templateEngine;

    //生成静态文件路径
    @Value("${pagepath}")
    private String pagepath;

    private Map<String,Object> packModeValue(Long spuId){
        Map<String,Object> map = new HashMap<>();

        Result<GoodEntity> result = goodsFeign.findById(spuId);
        GoodEntity goodsEntity= result.getData();

        Goods goods = goodsEntity.getGoods();

        GoodsDesc goodsDesc = goodsEntity.getGoodsDesc();

        List<Item> itemList = goodsEntity.getItemList();

        map.put("goods", goodsEntity);

        map.put("goodsDesc", goodsDesc);
        map.put("specificationList", JSON.parseArray(goodsDesc.getSpecificationItems(),Map.class));
        map.put("imageList",JSON.parseArray(goodsDesc.getItemImages(),Map.class));
        map.put("itemList",itemList);

        //4.加载分类数据
        map.put("category1",itemCatFeign.findById((long) goods.getCategory1Id().intValue()).getData());
        map.put("category2",itemCatFeign.findById((long) goods.getCategory2Id().intValue()).getData());
        map.put("category3",itemCatFeign.findById((long) goods.getCategory3Id().intValue()).getData());

        return map;
    }

    @Override
    public void createPageHtml(Long spuId) {
        // 1.上下文
        Context context = new Context();
        Map<String, Object> dataModel = packModeValue(spuId);
        context.setVariables(dataModel);
        // 2.准备文件
        File dir = new File(pagepath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File dest = new File(dir, spuId + ".html");
        // 3.生成页面
        try (PrintWriter writer = new PrintWriter(dest, "UTF-8")) {
            templateEngine.process("item", context, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
