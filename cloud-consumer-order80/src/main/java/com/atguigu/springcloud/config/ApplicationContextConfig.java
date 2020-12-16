package com.atguigu.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig  {
    /*
    将RestTemplate注入到Spring容器中
     */
    @Bean
    /*
    通过@LoadBalanced开启RestTemplate的负载均衡机制
     */
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
