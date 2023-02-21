package com.wff.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wff.common.entity.PageResult;
import com.wff.goods.dao.SpecificationMapper;
import com.wff.goods.dao.SpecificationOptionMapper;
import com.wff.goods.entity.SpecificationItemOption;
import com.wff.sellergoods.pojo.Specification;
import com.wff.goods.service.SpecificationService;
import com.wff.sellergoods.pojo.SpecificationOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/****
 * @Author:jeff
 * @Description:Specification业务层接口实现类
 * @Date 2021/2/1 14:19
 *****/
@Service
public class SpecificationServiceImpl extends ServiceImpl<SpecificationMapper,Specification> implements SpecificationService {
    @Autowired(required = false)
    private SpecificationOptionMapper specificationOptionMapper;

    @Autowired(required = false)
    private SpecificationMapper specificationMapper;

    /**
     * Specification条件+分页查询
     * @param specification 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageResult<Specification> findPage(Specification specification, int page, int size){
         Page<Specification> mypage = new Page<>(page, size);
        QueryWrapper<Specification> queryWrapper = this.createQueryWrapper(specification);
        IPage<Specification> iPage = this.page(mypage, queryWrapper);
        return new PageResult<Specification>(iPage.getTotal(),iPage.getRecords());
    }

    /**
     * Specification分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult<Specification> findPage(int page, int size){
        Page<Specification> mypage = new Page<>(page, size);
        IPage<Specification> iPage = this.page(mypage, new QueryWrapper<Specification>());

        return new PageResult<Specification>(iPage.getTotal(),iPage.getRecords());
    }

    /**
     * Specification条件查询
     * @param specification
     * @return
     */
    @Override
    public List<Specification> findList(Specification specification){
        //构建查询条件
        QueryWrapper<Specification> queryWrapper = this.createQueryWrapper(specification);
        //根据构建的条件查询数据
        return this.list(queryWrapper);
    }


    /**
     * Specification构建查询对象
     * @param specification
     * @return
     */
    public QueryWrapper<Specification> createQueryWrapper(Specification specification){
        QueryWrapper<Specification> queryWrapper = new QueryWrapper<>();
        if(specification!=null){
            // 主键
            if(!StringUtils.isEmpty(specification.getId())){
                 queryWrapper.eq("id",specification.getId());
            }
            // 名称
            if(!StringUtils.isEmpty(specification.getSpecName())){
                 queryWrapper.eq("spec_name",specification.getSpecName());
            }
        }
        return queryWrapper;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        this.removeById(id);
        specificationOptionMapper.delete(new QueryWrapper<SpecificationOption>().eq("spec_id",id));
    }

    /**
     * 修改Specification
     * @param specificationItemOption
     */
    @Override
    public void update(SpecificationItemOption specificationItemOption){
        //更新规格
        this.updateById(specificationItemOption.getSpecification());
        //删除选项
        specificationOptionMapper.delete(new QueryWrapper<SpecificationOption>().eq("spec_id",specificationItemOption.getSpecification().getId()));
        //更新选项
        specificationItemOption.getSpecificationOptions().forEach((p)->{ p.setSpecId(specificationItemOption.getSpecification().getId());specificationOptionMapper.insert(p); });
    }

    /**
     * 增加Specification
     * @param specificationItemOption
     */
    @Override
    public void add(SpecificationItemOption specificationItemOption){
        this.save(specificationItemOption.getSpecification());

        List<SpecificationOption> specificationOptions = specificationItemOption.getSpecificationOptions();

        for (SpecificationOption option:specificationOptions) {
            option.setSpecId(specificationItemOption.getSpecification().getId());
            specificationOptionMapper.insert(option);
        }
    }

    /**
     * 根据ID查询Specification
     * @param id
     * @return
     */
    @Override
    public SpecificationItemOption findById(Long id){
        SpecificationItemOption specificationItemOption = new SpecificationItemOption();
        specificationItemOption.setSpecification(this.getById(id));
        specificationItemOption.setSpecificationOptions(specificationOptionMapper.selectList(new QueryWrapper<SpecificationOption>().eq("spec_id",id)));
        return specificationItemOption;
    }

    /**
     * 查询Specification全部数据
     * @return
     */
    @Override
    public List<Specification> findAll() {
        return this.list(new QueryWrapper<Specification>());
    }

    @Override
    public List<Map> findOptions() {
        return specificationMapper.findOptions();
    }
}
