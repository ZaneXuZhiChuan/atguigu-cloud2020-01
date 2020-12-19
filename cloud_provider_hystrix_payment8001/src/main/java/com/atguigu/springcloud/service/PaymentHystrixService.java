package com.atguigu.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentHystrixService {
    /*
    模拟正常访问的场景
     */
    public String paymentInfo_OK(Integer id){
        return "1".repeat(15)+"ThreadPool： " + Thread.currentThread().getName() + " paymentInfo_OK: " + id;
    }

    /*
    模拟访问超时的场景，如果 paymentInfo_TimeOut 函数出现问题，比如超时、出现异常等，那么就会启用 @HystrixCommand 注解的函数去做 “保险”处理
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public String paymentInfo_TimeOut(Integer id){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        int i = 10 / 0 ;
        return "2".repeat(15)+"ThreadPool： " + Thread.currentThread().getName() + " paymentInfo_TimeOut， id: " + id;
    }

    public String paymentInfo_TimeOutHandler(Integer id){
        return "3".repeat(15)+"ThreadPool： " + Thread.currentThread().getName() + " 系统繁忙，请稍后再试， id: " + id;
    }

}
