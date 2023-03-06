package com.wff.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wff.common.entity.PageResult;
import com.wff.goods.entity.GoodEntity;
import com.wff.sellergoods.pojo.Goods;

import java.util.List;

/****
 * @Author:jeff
 * @Description:Goods业务层接口
 * @Date 2021/2/1 14:19
 *****/

public interface GoodsService extends IService<Goods> {

    /***
     * Goods多条件分页查询
     * @param goods
     * @param page
     * @param size
     * @return
     */
    PageResult<Goods> findPage(Goods goods, int page, int size);

    /***
     * Goods分页查询
     * @param page
     * @param size
     * @return
     */
    PageResult<Goods> findPage(int page, int size);

    /***
     * Goods多条件搜索方法
     * @param goods
     * @return
     */
    List<Goods> findList(Goods goods);

    /***
     * 删除Goods
     * @param id
     */
    void delete(Long id);

    /***
     * 修改Goods数据
     * @param goodsEntity
     */
    void update(GoodEntity goodsEntity);

    /***
     * 新增Goods
     * @param goodEntity
     */
    void add(GoodEntity goodEntity);

    /**
     * 保存item sku
     * @param goodEntity
     */
    void addItemList(GoodEntity goodEntity);

    /**
     * 根据ID查询Goods
     * @param id
     * @return
     */
     GoodEntity findById(Long id);

    /***
     * 查询所有Goods
     * @return
     */
    List<Goods> findAll();

    /**
     * 审核商品
     */
    void audit(Long goodsId);
}
