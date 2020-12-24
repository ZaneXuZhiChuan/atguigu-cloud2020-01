package com.atguigu.springcloud.sertinel.fallback;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.web.bind.annotation.PathVariable;

public class MyFallBack {
    public CommonResult<Payment> fallbackException(@PathVariable Long id, Throwable throwable) {
        return new CommonResult<>(400,"fallbackException===Sentinel的fallback处理运行时异常： fallbackException " + "======="+throwable.getClass().getName()+"****"+id);
    }
}
