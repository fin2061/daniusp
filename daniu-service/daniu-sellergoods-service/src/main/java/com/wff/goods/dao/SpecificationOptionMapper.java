package com.wff.goods.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wff.sellergoods.pojo.SpecificationOption;
import org.apache.ibatis.annotations.Insert;

/****
 * @Author:jeff
 * @Description:SpecificationOption的Dao
 * @Date 2021/2/1 14:19
 *****/
public interface SpecificationOptionMapper extends BaseMapper<SpecificationOption> {

    @Insert("INSERT ")
    void insertOption(SpecificationOption specificationOption);
}
