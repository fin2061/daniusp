package com.wff.goods.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wff.common.entity.PageResult;
import com.wff.common.entity.Result;
import com.wff.common.entity.StatusCode;
import com.wff.goods.service.BrandService;
import com.wff.sellergoods.pojo.Brand;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
@CrossOrigin
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping
    public Result<List<Brand>> findAllBrand() {
        return new Result<List<Brand>>(true, StatusCode.OK, brandService.findAllBrand());
    }

    @GetMapping("/{id}")
    public Result<Brand> findBrandById(@PathVariable Long id) {
        return new Result<Brand>(true, StatusCode.OK, brandService.findBrandById(id));
    }

    @ApiOperation(value = "Brand列表根据ID查询",notes = "根据ID查询Brandt方法详情",tags = {"BrandController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Long")
    @GetMapping("/findOptions")
    public Result<List<Map>> findBrandOptions() {
        return new Result<List<Map>>(true, StatusCode.OK, brandService.findBrandOptions());
    }

    @PostMapping
    public Result saveBrand(@RequestBody Brand brand) {
        brandService.saveBrand(brand);
        return new Result();
    }

    @PostMapping("/{id}")
    public Result updateBrandById(@PathVariable Long id, @RequestBody Brand brand){
        brand.setId(id);
        brandService.updateBrandById(brand);
        return new Result();
    }

    @DeleteMapping("/{id}")
    public Result deleteBrandById(@PathVariable Long id){
        brandService.deleteBrandById(id);
        return new Result();
    }

    @PostMapping("/serach")
    public Result<List<Brand>> findBrandByParams(@RequestBody(required = false) Brand brand){
        return new Result<List<Brand>>(true,StatusCode.OK,brandService.findBrandByParams(brand));
    }

    @PostMapping("/serach/{size}/{num}")
    public Result<PageResult<Brand>> findBrandPage(@PathVariable int size, @PathVariable int num, @RequestBody(required = false) Brand brand){
        Page<Brand> brandPage = brandService.findBrandPage(size, num, brand);
        return new Result<PageResult<Brand>>(true,StatusCode.OK,new PageResult<Brand>(brandPage.getTotal(),brandPage.getRecords()));
    }
}
