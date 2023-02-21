package com.wff.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wff.common.entity.PageResult;
import com.wff.goods.entity.SpecificationItemOption;
import com.wff.sellergoods.pojo.Specification;

import java.util.List;
import java.util.Map;

/****
 * @Author:jeff
 * @Description:Specification业务层接口
 * @Date 2021/2/1 14:19
 *****/

public interface SpecificationService extends IService<Specification> {

    /***
     * Specification多条件分页查询
     * @param specification
     * @param page
     * @param size
     * @return
     */
    PageResult<Specification> findPage(Specification specification, int page, int size);

    /***
     * Specification分页查询
     * @param page
     * @param size
     * @return
     */
    PageResult<Specification> findPage(int page, int size);

    /***
     * Specification多条件搜索方法
     * @param specification
     * @return
     */
    List<Specification> findList(Specification specification);

    /***
     * 删除Specification
     * @param id
     */
    void delete(Long id);

    /***
     * 修改Specification数据
     * @param specificationItemOption
     */
    void update(SpecificationItemOption specificationItemOption);

    /***
     * 新增Specification
     * @param specificationItemOption
     */
    void add(SpecificationItemOption specificationItemOption);

    /**
     * 根据ID查询specificationItemOption
     * @param id
     * @return
     */
    SpecificationItemOption findById(Long id);

    /***
     * 查询所有Specification
     * @return
     */
    List<Specification> findAll();

    /**
     * 查询规格列表
     * @return
     */
    List<Map> findOptions();
}
