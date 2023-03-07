package com.wff.controller;

import com.wff.searchweb.feign.SearchSkuFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/find")
public class SearchController {
    @Autowired
    private SearchSkuFeign searchSkuFeign;

    @GetMapping("/index")
    public String index(){
        return "search";
    }

    @GetMapping("/list")
    public String search(@RequestParam(required = false) Map searchMap, Model model){
        //调用daniu-search-service微服务
        Map<String,Object> resultMap = searchSkuFeign.search(searchMap);
        //搜索数据结果
        model.addAttribute("result",resultMap);
        //搜索条件
        model.addAttribute("searchMap",searchMap);
        return "search";
    }

    @PostMapping("/list")
    public String searchPost(@RequestParam(required = false) Map searchMap, Model model){
        //调用daniu-search-service微服务
        Map<String,Object> resultMap = searchSkuFeign.search(searchMap);
        //搜索数据结果
        model.addAttribute("result",resultMap);
        //搜索条件
        model.addAttribute("searchMap",searchMap);
         return "search";
    }
}
