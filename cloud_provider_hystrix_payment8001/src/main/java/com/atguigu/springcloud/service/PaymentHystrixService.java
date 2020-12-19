package com.atguigu.springcloud.service;

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
    模拟访问超时的场景
     */
    public String paymentInfo_TimeOut(Integer id){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "2".repeat(15)+"ThreadPool： " + Thread.currentThread().getName() + " paymentInfo_TimeOut: " + id;
    }

}
