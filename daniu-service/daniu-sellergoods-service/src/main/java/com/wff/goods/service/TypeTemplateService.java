package com.wff.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wff.common.entity.PageResult;
import com.wff.sellergoods.pojo.TypeTemplate;

import java.util.List;

/****
 * @Author:jeff
 * @Description:TypeTemplate业务层接口
 * @Date 2021/2/1 14:19
 *****/

public interface TypeTemplateService extends IService<TypeTemplate> {

    /***
     * TypeTemplate多条件分页查询
     * @param typeTemplate
     * @param page
     * @param size
     * @return
     */
    PageResult<TypeTemplate> findPage(TypeTemplate typeTemplate, int page, int size);

    /***
     * TypeTemplate分页查询
     * @param page
     * @param size
     * @return
     */
    PageResult<TypeTemplate> findPage(int page, int size);

    /***
     * TypeTemplate多条件搜索方法
     * @param typeTemplate
     * @return
     */
    List<TypeTemplate> findList(TypeTemplate typeTemplate);

    /***
     * 删除TypeTemplate
     * @param id
     */
    void delete(Long id);

    /***
     * 修改TypeTemplate数据
     * @param typeTemplate
     */
    void update(TypeTemplate typeTemplate);

    /***
     * 新增TypeTemplate
     * @param typeTemplate
     */
    void add(TypeTemplate typeTemplate);

    /**
     * 根据ID查询TypeTemplate
     * @param id
     * @return
     */
     TypeTemplate findById(Long id);

    /***
     * 查询所有TypeTemplate
     * @return
     */
    List<TypeTemplate> findAll();
}
