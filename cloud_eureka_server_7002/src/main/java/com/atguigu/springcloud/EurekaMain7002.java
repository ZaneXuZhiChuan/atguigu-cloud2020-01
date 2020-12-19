package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
/*
通过@EnableEurekaServer注解标注该类为Eureka服务端；
 */
@EnableEurekaServer
public class EurekaMain7002 {
    public static void main(String[] args) {
        /*
        目前是http方式提交代码到github，所以每次提交代码到远端时候都要输入好几次代码；所以现在要将http提交方式转换成ssh方式；
         */
        SpringApplication.run(EurekaMain7002.class, args);
    }
}
