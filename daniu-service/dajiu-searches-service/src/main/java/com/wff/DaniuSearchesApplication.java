package com.wff;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.wff.search.feign")
@EnableElasticsearchRepositories(basePackages = "com.wff.dao")
public class DaniuSearchesApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaniuSearchesApplication.class,args);
    }
}
