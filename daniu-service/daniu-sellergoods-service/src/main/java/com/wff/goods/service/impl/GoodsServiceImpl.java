package com.wff.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wff.common.entity.PageResult;
import com.wff.goods.dao.*;
import com.wff.goods.entity.GoodEntity;
import com.wff.sellergoods.pojo.*;
import com.wff.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/****
 * @Author:jeff
 * @Description:Goods业务层接口实现类
 * @Date 2021/2/1 14:19
 *****/
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Autowired(required = false)
    private GoodsDescMapper goodsDescMapper;
    @Autowired(required = false)
    private ItemMapper itemMapper;
    @Autowired(required = false)
    private ItemCatMapper itemCatMapper;
    @Autowired(required = false)
    private BrandMapper brandMapper;
    @Autowired(required = false)
    private GoodsMapper goodsMapper;

    /**
     * Goods条件+分页查询
     *
     * @param goods 查询条件
     * @param page  页码
     * @param size  页大小
     * @return 分页结果
     */
    @Override
    public PageResult<Goods> findPage(Goods goods, int page, int size) {
        Page<Goods> mypage = new Page<>(page, size);
        QueryWrapper<Goods> queryWrapper = this.createQueryWrapper(goods);
        IPage<Goods> iPage = this.page(mypage, queryWrapper);
        return new PageResult<Goods>(iPage.getTotal(), iPage.getRecords());
    }

    /**
     * Goods分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult<Goods> findPage(int page, int size) {
        Page<Goods> mypage = new Page<>(page, size);
        IPage<Goods> iPage = this.page(mypage, new QueryWrapper<Goods>());

        return new PageResult<Goods>(iPage.getTotal(), iPage.getRecords());
    }

    /**
     * Goods条件查询
     *
     * @param goods
     * @return
     */
    @Override
    public List<Goods> findList(Goods goods) {
        //构建查询条件
        QueryWrapper<Goods> queryWrapper = this.createQueryWrapper(goods);
        //根据构建的条件查询数据
        return this.list(queryWrapper);
    }


    /**
     * Goods构建查询对象
     *
     * @param goods
     * @return
     */
    public QueryWrapper<Goods> createQueryWrapper(Goods goods) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        if (goods != null) {
            // 主键
            if (!StringUtils.isEmpty(goods.getId())) {
                queryWrapper.eq("id", goods.getId());
            }
            // 商家ID
            if (!StringUtils.isEmpty(goods.getSellerId())) {
                queryWrapper.eq("seller_id", goods.getSellerId());
            }
            // SPU名
            if (!StringUtils.isEmpty(goods.getGoodsName())) {
                queryWrapper.eq("goods_name", goods.getGoodsName());
            }
            // 默认SKU
            if (!StringUtils.isEmpty(goods.getDefaultItemId())) {
                queryWrapper.eq("default_item_id", goods.getDefaultItemId());
            }
            // 状态
            if (!StringUtils.isEmpty(goods.getAuditStatus())) {
                queryWrapper.eq("audit_status", goods.getAuditStatus());
            }
            // 是否上架
            if (!StringUtils.isEmpty(goods.getIsMarketable())) {
                queryWrapper.eq("is_marketable", goods.getIsMarketable());
            }
            // 品牌
            if (!StringUtils.isEmpty(goods.getBrandId())) {
                queryWrapper.eq("brand_id", goods.getBrandId());
            }
            // 副标题
            if (!StringUtils.isEmpty(goods.getCaption())) {
                queryWrapper.eq("caption", goods.getCaption());
            }
            // 一级类目
            if (!StringUtils.isEmpty(goods.getCategory1Id())) {
                queryWrapper.eq("category1_id", goods.getCategory1Id());
            }
            // 二级类目
            if (!StringUtils.isEmpty(goods.getCategory2Id())) {
                queryWrapper.eq("category2_id", goods.getCategory2Id());
            }
            // 三级类目
            if (!StringUtils.isEmpty(goods.getCategory3Id())) {
                queryWrapper.eq("category3_id", goods.getCategory3Id());
            }
            // 小图
            if (!StringUtils.isEmpty(goods.getSmallPic())) {
                queryWrapper.eq("small_pic", goods.getSmallPic());
            }
            // 商城价
            if (!StringUtils.isEmpty(goods.getPrice())) {
                queryWrapper.eq("price", goods.getPrice());
            }
            // 分类模板ID
            if (!StringUtils.isEmpty(goods.getTypeTemplateId())) {
                queryWrapper.eq("type_template_id", goods.getTypeTemplateId());
            }
            // 是否启用规格
            if (!StringUtils.isEmpty(goods.getIsEnableSpec())) {
                queryWrapper.eq("is_enable_spec", goods.getIsEnableSpec());
            }
            // 是否删除
            if (!StringUtils.isEmpty(goods.getIsDelete())) {
                queryWrapper.eq("is_delete", goods.getIsDelete());
            }
        }
        return queryWrapper;
    }

    /**
     * 逻辑删除
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        Goods goods = goodsMapper.selectById(id);
        if ("1".equals(goods.getAuditStatus())){
            throw new RuntimeException("必须先下架再删除");
        }
        goods.setIsDelete("1");
        goods.setAuditStatus("0");
        goodsMapper.updateById(goods);
    }

    /**
     * 修改Goods
     *
     * @param goodEntity
     */
    @Override
    public void update(GoodEntity goodEntity) {
        goodEntity.getGoods().setAuditStatus("0");
        goodsMapper.updateById(goodEntity.getGoods());
        goodsDescMapper.updateById(goodEntity.getGoodsDesc());
        itemMapper.delete(new QueryWrapper<Item>().eq("goods_id",goodEntity.getGoods().getId()));
        this.addItemList(goodEntity);
    }

    public void add(GoodEntity goodEntity){
        goodEntity.getGoods().setAuditStatus("0");
        goodsMapper.insert(goodEntity.getGoods());
        goodEntity.getGoodsDesc().setGoodsId(goodEntity.getGoods().getId());
        goodsDescMapper.insert(goodEntity.getGoodsDesc());
        this.addItemList(goodEntity);
    }
    /**
     * 增加Goods
     *
     * @param goodEntity
     */
    @Override
    public void addItemList(GoodEntity goodEntity) {
        if ("1".equals(goodEntity.getGoods().getIsEnableSpec())) {
            //保存SKU
            if (!CollectionUtils.isEmpty(goodEntity.getItems())) {
                for (Item item : goodEntity.getItems()) {
                    String title = goodEntity.getGoods().getGoodsName();
                    Map<String, String> specMap = JSON.parseObject(item.getSpec(), Map.class);
                    for (String key : specMap.keySet()) {
                        title += specMap.get(key);
                    }
                    item.setTitle(title);
                    this.setItemValues(goodEntity, item);
                    itemMapper.insert(item);
                }
            } else {
                Item item = new Item();
                item.setTitle(goodEntity.getGoods().getGoodsName());
                item.setPrice(goodEntity.getGoods().getPrice());
                item.setNum(999);
                item.setStatus("1");
                item.setIsDefault("1");
                item.setSpec("{}");
                this.setItemValues(goodEntity, item);
                itemMapper.insert(item);
            }
        }
    }

    private void setItemValues(GoodEntity goodEntity, Item item) {
        item.setCategory(goodEntity.getGoods().getCategory3Id() + "");
        item.setCreateTime(new Date());
        item.setUpdateTime(new Date());
        item.setGoodsId(goodEntity.getGoods().getId());
        item.setSeller(goodEntity.getGoods().getSellerId());
        ItemCat itemCat = itemCatMapper.selectById(goodEntity.getGoods().getCategory3Id());
        item.setCategory(itemCat.getName());
        Brand brand = brandMapper.selectById(goodEntity.getGoods().getBrandId());
        item.setBrand(brand.getName());
        List<Map> images = JSON.parseArray(goodEntity.getGoodsDesc().getItemImages(), Map.class);
        if (images.size() > 0) {
            item.setImage((String) images.get(0).get("url"));
        }
    }

    /**
     * 根据ID查询Goods
     *
     * @param id
     * @return
     */
    @Override
    public GoodEntity findById(Long id) {
        GoodEntity goodEntity = new GoodEntity();
        goodEntity.setGoods(this.getById(id));
        goodEntity.setGoodsDesc(goodsDescMapper.selectOne(new QueryWrapper<GoodsDesc>().eq("goods_id", id)));
        goodEntity.setItems(itemMapper.selectList(new QueryWrapper<Item>().eq("goods_id", id)));
        return goodEntity;
    }

    /**
     * 查询Goods全部数据
     *
     * @return
     */
    @Override
    public List<Goods> findAll() {
        return this.list(new QueryWrapper<Goods>());
    }

    @Override
    public void audit(Long goodsId) {
        Goods goods = goodsMapper.selectById(goodsId);
        if ("1".equals(goods.getIsDelete())){
            throw new RuntimeException("商品已删除");
        }
        goods.setAuditStatus("1");
        goods.setIsMarketable("1");
        goodsMapper.updateById(goods);
    }
}
