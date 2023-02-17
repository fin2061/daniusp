package com.wff;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.wff.sellergoods.dao")
public class SellergoodsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SellergoodsServiceApplication.class);
    }
}
