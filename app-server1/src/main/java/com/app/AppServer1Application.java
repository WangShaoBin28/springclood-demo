package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient //如果是Eureka的话推荐使用
@EnableDiscoveryClient //如果是其他的注册中心，那么推荐使用
@EnableFeignClients
@EnableHystrix
@SpringBootApplication
public class AppServer1Application {

    public static void main(String[] args) {
        SpringApplication.run(AppServer1Application.class, args);
    }

}
