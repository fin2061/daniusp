package com.wff.goods.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wff.sellergoods.pojo.Specification;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/****
 * @Author:jeff
 * @Description:Specification的Dao
 * @Date 2021/2/1 14:19
 *****/
public interface SpecificationMapper extends BaseMapper<Specification> {

    /**
     * 查询规格列表
     */
    @Select("SELECT id , `spec_name` AS `text` FROM tb_specification")
    List<Map> findOptions();
}
