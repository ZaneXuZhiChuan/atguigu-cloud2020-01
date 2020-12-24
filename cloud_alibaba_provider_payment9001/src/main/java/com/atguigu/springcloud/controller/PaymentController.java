package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {
    @Value("${server.port}")
    String serverPort;

    @GetMapping("/payment/nacos/{id}")
    public String getPayment(@PathVariable("id")Integer id){
        return "Nacos registry, serverport: " + serverPort + "--------ID:" + id;
    }

//  =============================================  以下代码是为了测试 Sentinel服务熔断 功能准备的测试环境
    @Resource
    PaymentService paymentService;
//    展示给调用者的信息
    @Autowired
    DiscoveryClient discoveryClient;

    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int res = paymentService.create(payment);
        log.info("********CREATE..." + serverPort + ": " + res);
        if (res > 0){
            return new CommonResult(200,"CREATE sucess..." + serverPort,res);
        }else {
            return new CommonResult(404,"CREATE filed..." + serverPort);
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("=".repeat(5)+"********QUERY..." + serverPort + ": "  + payment);
        if (payment != null){
            return new CommonResult(200,"QUERY sucess..." + serverPort,payment);
        }else {
            return new CommonResult(404,"QUERY filed..." + serverPort);
        }
    }

//    通过 DiscoveryClient 可以获取注册进EurekaServer应用中的各种微服务的详细信息，包括该微服务的名称、IP、端口以及访问Uri等信息
    @GetMapping("/payment/discovery")
    public Object discovery(){
        log.info("~".repeat(15) +"DiscoveryClient"+ serverPort + "-".repeat(5)+ discoveryClient.toString());
        return this.discoveryClient;
    }
}
