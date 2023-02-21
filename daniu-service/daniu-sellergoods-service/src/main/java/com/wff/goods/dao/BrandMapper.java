package com.wff.goods.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wff.sellergoods.pojo.Brand;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2023-02-17
 */
public interface BrandMapper extends BaseMapper<Brand> {

    /**
     * 查询品牌列表
     * @param id
     * @return
     */
    @Select("SELECT id , `name` AS `text` FROM tb_brand")
    List<Map> queryBrandOptions();
}
