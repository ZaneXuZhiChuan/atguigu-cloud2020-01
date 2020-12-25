package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.CommonResult;
import org.springframework.stereotype.Component;

/*
该方法看似实现了 Payment_Feign_Service 的接口，
实际上这个实现类的作用是用来满足 Feign 进行微服务之间调用时候出现 “运行时异常” 要触发的 Fallback 逻辑响应；
 */
@Component
public class Payment_Feign_Service_Fallback implements Payment_Feign_Service {
    @Override
    public CommonResult getPaymentById(Long id) {
        return new CommonResult(403,"Feign + Sentinel 服务降级返回，----Payment_Feign_Service_Fallback======ID： "+ id);
    }
}
