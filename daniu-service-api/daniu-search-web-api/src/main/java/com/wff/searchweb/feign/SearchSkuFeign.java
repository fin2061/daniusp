package com.wff.searchweb.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@FeignClient("SEARCH")
@RequestMapping("/search")
public interface SearchSkuFeign {

    /**
     * 搜索
     * @param searchMap
     * @return
     */
    @PostMapping
    Map search(@RequestBody(required = false) Map searchMap);
}
