package com.atguigu.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
在Controller层定义 fallbackMethod 导致controller层的业务逻辑和fallbackMethod之间耦合度变高，
所以定义PaymentHystrixServiceFallBackImpl类抽取出来fallback逻辑，
并通过 @FeignClient 注解中的 fallbcak 属性来指定 PaymentHystrixServiceFallBackImpl 类作为fallback逻辑；
 */
@FeignClient(value = "CLOUD-HYSTRIX-PAYMENT-SERVICE", fallback = PaymentHystrixServiceFallBackImpl.class)
@Component
public interface PaymentHystrixService {
    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_ok(@PathVariable("id") Integer id);

    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id);
}
