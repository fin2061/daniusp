package com.wff;

import com.xpand.starter.canal.annotation.EnableCanalClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCanalClient
public class CanalServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CanalServiceApplication.class,args);
    }

}
