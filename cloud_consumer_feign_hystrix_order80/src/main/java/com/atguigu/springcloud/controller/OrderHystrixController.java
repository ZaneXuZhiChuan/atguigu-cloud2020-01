package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
/*
我们客户端在与远端进行交互的时候，对于服务过程中可能会出现的各种熔断、降级在很多函数逻辑中都是要进行处理的，
而且大多数情况下处理的逻辑其实都是大同小异，所以对于我们在函数中涉及到的 hystrix 的 fallbackmethond 逻辑
我们应该抽取一个统一的 fallbcak 逻辑出来供整个业务使用，避免代码冗余；

而 @DefaultProperties 注解寓意就是如果没有在函数中通过@HystrixCommand去指明fallbackmethond逻辑的话，
就默认使用 @DefaultProperties 的逻辑；
 */
@DefaultProperties(defaultFallback = "payment_global_fallbackMethod")
public class OrderHystrixController {

    @Autowired
    PaymentHystrixService paymentHystrixService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_ok(@PathVariable("id") Integer id){
        String result = paymentHystrixService.paymentInfo_ok(id);
        return result;
    }


    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    /*
    客户端对远程响应的要求是希望远端能够在 1.5s 内返回结果，如果没有满足则触发 Hystrix 的 fallback逻辑；
     */
    @HystrixCommand/*(fallbackMethod = "paymentInfo_TimeOut_Fallback", commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "1500")
    })*/
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
        String result = paymentHystrixService.paymentInfo_TimeOut(id);
        return result;
    }

    public String paymentInfo_TimeOut_Fallback(Integer id){
        return "。".repeat(15) + "客户端80，支付超时或者系统繁忙。。。。。";
    }

    /*
    下面走的是全局 fallbackmethod 逻辑
     */
    public String payment_global_fallbackMethod(){
        return "。".repeat(15) +  "全局的异常处理信息，请稍后再试";
    }
}
