package com.wff.sellergoods.feign;

import com.wff.common.entity.Result;
import com.wff.sellergoods.entity.GoodEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="DANIU-SELLERGOODS")
@RequestMapping("/goods")
public interface GoodsFeign {

    /***
     * 根据ID查询Spu数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    Result<GoodEntity> findById(@PathVariable Long id);
}
