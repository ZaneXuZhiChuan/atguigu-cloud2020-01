package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/*
Gateway的主要作用是：在微服务架构中每个微服务如果都向外暴露自己的IP地址，那么显然是不合适的，一个是不安全；另一个没有统一的访问入口也不方便管理微服务架构；
所以 Gateway 的作用主要就是凸显在这里；
 */
@SpringBootApplication
@EnableEurekaClient
public class GatewayMain9527 {
    public static void main(String[] args) {
        SpringApplication.run(GatewayMain9527.class, args);
    }
}
