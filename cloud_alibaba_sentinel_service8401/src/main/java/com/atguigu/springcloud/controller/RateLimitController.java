package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.handler.CustomBlockHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RateLimitController {

    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handlerException")
    public CommonResult byResource() {
        return new CommonResult(200, "按资源名称限流测试ok", new Payment(2020L, "serial001"));
    }
    public CommonResult handlerException(BlockException exception){
        return new CommonResult(404, exception.getClass().getCanonicalName()+"=========服务不可用");
    }

    @GetMapping("/ratelimit/customBlockHandler")
    /*
    对当前方法用Sentinel进行服务限流、降级、熔断等管理
    blockHandlerClass指定处理降级的类；
    blockHandler精确到哪个函数；
     */
    @SentinelResource(value = "customBlockHandler", blockHandlerClass = CustomBlockHandler.class,
            blockHandler = "handlerException02")
    public CommonResult customBlockHandler() {
        return new CommonResult(200, "Sentinel 自定义FallBack逻辑", new Payment(2020L, "serial001"));
    }
}
