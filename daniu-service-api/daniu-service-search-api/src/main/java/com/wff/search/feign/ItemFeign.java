package com.wff.search.feign;

import com.wff.common.entity.Result;
import com.wff.sellergoods.pojo.Item;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name="DANIU-SELLERGOODS")
@RequestMapping(value = "/item")
public interface ItemFeign {

    /***
     * 根据审核状态查询Sku
     * @param status
     * @return
     */
    @GetMapping("/status/{status}")
    Result<List<Item>> findByStatus(@PathVariable String status);
}
