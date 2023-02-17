package com.wff.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wff.goods.dao.BrandMapper;
import com.wff.goods.service.BrandService;
import com.wff.sellergoods.pojo.Brand;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

    @Override
    public List<Brand> findAllBrand() {
        return this.list();
    }

    @Override
    public Brand findBrandById(Long id) {
        return this.getOne(new QueryWrapper<Brand>().eq("id", id));
    }

    @Override
    public void saveBrand(Brand brand) {
        this.save(brand);
    }

    @Override
    public void updateBrandById(Brand brand) {
        this.updateById(brand);
    }

    @Override
    public void deleteBrandById(Long id) {
        this.removeById(id);
    }

    @Override
    public List<Brand> findBrandByParams(Brand brand) {
        return this.list(createQueryWrapper(brand));
    }

    @Override
    public Page<Brand> findBrandPage(int size, int num, Brand brand) {
        Page<Brand> page = new Page<>(num, size);
        return this.page(page,createQueryWrapper(brand));
    }

    public QueryWrapper<Brand> createQueryWrapper(Brand brand) {
        QueryWrapper<Brand> wrapper = new QueryWrapper<>();
        if (brand != null) {
            if (brand.getId() != null) {
                wrapper.eq("id", brand.getId());
            }
            if (brand.getName() != null) {
                wrapper.eq("name", brand.getName());
            }
            if (brand.getFirstChar() != null) {
                wrapper.eq("first_char", brand.getFirstChar());
            }
        }
        return wrapper;
    }

}
