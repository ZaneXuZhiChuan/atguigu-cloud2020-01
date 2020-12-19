package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentHystrixService {
    //    服务降级=========================================================================================
    /*
    模拟正常访问的场景
     */
    public String paymentInfo_OK(Integer id) {
        return "1".repeat(15) + "ThreadPool： " + Thread.currentThread().getName() + " paymentInfo_OK: " + id;
    }

    /*
    模拟访问超时的场景，如果 paymentInfo_TimeOut 函数出现问题，比如超时、出现异常等，那么就会启用 @HystrixCommand 注解的函数去做 “保险”处理
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public String paymentInfo_TimeOut(Integer id) {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        int i = 10 / 0 ;
        return "2".repeat(15) + "ThreadPool： " + Thread.currentThread().getName() + " paymentInfo_TimeOut， id: " + id;
    }

    public String paymentInfo_TimeOutHandler(Integer id) {
        return "3".repeat(15) + "ThreadPool： " + Thread.currentThread().getName() + " 系统繁忙，请稍后再试， id: " + id;
    }

    /*
    服务熔断=========================================================================================
    服务熔断的功能主要是用来保护服务器：比如如果某个时刻有10亿次的请求同时打到负载只有10w的服务机上，
    如果不采用任何”保险“策略，此时该服务机必然会产生”雪崩“效应，对服务机造成重大伤害，
    所以hytrix的服务熔断功能就相当于给部署了服务应用的服务机上一个”保险丝“，当有这种大批次的请求可能会”摧毁“服务机的情况，
    hytrix的服务熔断功能能保护服务机不会被立刻“摧毁”，并且hytrix的服务熔断功能还具有重新“拉起”服务的功能，
    当能“摧毁”服务机的条件消失后，hytrix熔断机制会尝试重新拉起服务应用；
    */
    /*
    commandProperties 属性中配置的参数联合起来实现逻辑是：
    开启hytrix的断路器功能，该断路器在满足时间窗口期中定义的时间(此处定义10s)内，发送了10次请求如果有60%的失败率，则触发断路器功能实现”跳闸“
     */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
//            是否开启断路器
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
//            请求次数阈值
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
//            时间窗口期
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
//            失败率达到多少后”跳闸“
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("1".repeat(15) + "ID cannot litter than ZERO");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + " 调用成功，流水号：" + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id")Integer id){
        return "2".repeat(15)+"id 不能为负数，请稍后再试 ~~ id：" + id;
    }
}
