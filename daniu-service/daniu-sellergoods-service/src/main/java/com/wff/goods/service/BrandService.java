package com.wff.goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wff.sellergoods.pojo.Brand;

import java.util.List;
import java.util.Map;

public interface BrandService {

    /**
     * 查询所有品牌
     * @return
     */
    List<Brand> findAllBrand();

    /**
     * 通过id查询品牌
     * @param id
     * @return
     */
    Brand findBrandById(Long id);

    /**
     * 保存品牌
     * @param brand
     */
    void saveBrand(Brand brand);

    /**
     * 根据id更新品牌
     * @param brand
     */
    void updateBrandById(Brand brand);

    /**
     * 根据id删除品牌
     * @param id
     */
    void deleteBrandById(Long id);

    /**
     * 根据字段查询品牌
     * @param brand
     * @return
     */
    List<Brand> findBrandByParams(Brand brand);

    /**
     * 分页查询品牌
     * @param size
     * @param num
     * @return
     */
    Page<Brand> findBrandPage(int size, int num, Brand brand);

    /**
     * 查询品牌列表
     * @return
     */
    List<Map> findBrandOptions();
}
