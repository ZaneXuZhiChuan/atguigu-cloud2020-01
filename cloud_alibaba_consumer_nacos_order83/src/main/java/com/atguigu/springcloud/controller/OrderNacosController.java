package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.sertinel.fallback.MyFallBack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class OrderNacosController {
    @Autowired
    RestTemplate restTemplate;
    @Value("${service-url.nacos-user-service}")
    String SEVER_URL;

    @GetMapping("/consumer/payment/nacos/{id}")
    public String paymentInfo(@PathVariable("id")Integer id){
        return restTemplate.getForObject(SEVER_URL + "/payment/nacos/" + id, String.class);
    }

    //  测试 Sentinel 的服务熔断功能
    @RequestMapping("/consumer/fallback/{id}")
    @SentinelResource(value = "fallback", fallback = "fallbackHandler",blockHandler = "blockHandler"/*,fallbackClass = MyFallBack.class*/)
    public CommonResult<Payment> fallback(@PathVariable("id") Long id) {
        CommonResult result = restTemplate.getForObject(SEVER_URL + "/payment/get/" + id, CommonResult.class, id);
        if (id == 4) {
            log.info("2".repeat(15) + "=========4");
            throw new IllegalArgumentException("IllegalArgumentException ，非法参数异常。。。。");
        } else if (result.getT() == null) {
            log.info("2".repeat(15) + "=========NULL");
            throw new NullPointerException("NullPointerException , 该ID没有值 空指针异常");
        }
        return result;
    }
    public CommonResult fallbackHandler(@PathVariable Long id, Throwable throwable) {
        return new CommonResult<>(401,"fallbackException===Sentinel的fallback处理运行时异常： fallbackHandler " + "======="+ throwable.getMessage()+"====="+id);
    }

    public CommonResult blockHandler(@PathVariable Long id, BlockException exception) {
        return new CommonResult<>(402,"Sentinel blockHandler 限流： blockHandler " + "======="+ exception.getMessage()+"====="+id);
    }
}
