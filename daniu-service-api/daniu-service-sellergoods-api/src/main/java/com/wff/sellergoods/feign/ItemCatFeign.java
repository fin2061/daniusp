package com.wff.sellergoods.feign;

import com.wff.common.entity.Result;
import com.wff.sellergoods.pojo.ItemCat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("DANIU-SELLERGOODS")
@RequestMapping("/itemCat")
public interface ItemCatFeign {

    /**
     * 获取分类的对象信息
     * @param id
     * @return
     */
    @GetMapping("/item/{id}")
    Result<ItemCat> findById(@PathVariable Long id);
}
