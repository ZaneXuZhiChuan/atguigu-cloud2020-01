package com.atguigu.springcloud.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentHystrixServiceFallBackImpl implements  PaymentHystrixService{
    @Override
    public String paymentInfo_ok(Integer id) {
        return "FALLBACK PaymentHystrixServiceFallBackImpl------paymentInfo_ok: 服务机宕机了";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "FALLBACK PaymentHystrixServiceFallBackImpl------paymentInfo_TimeOut: 服务机宕机了";
    }
}
