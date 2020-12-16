package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    PaymentService paymentService;

    @Value("${server.port}")
    String port;

    /*
    展示给调用者的信息
     */
    @Autowired
    DiscoveryClient discoveryClient;

    @PostMapping("/payment/create")
    /*
    注意：当Order模块来调用当前模块create服务的时候，
    由于Payment模块的create服务是post请求，而Order模块是通过get请求访问过来的，
    所以要记得给Payment模块中的create服务参数加上@RequestBody注解
     */
    public CommonResult create(@RequestBody Payment payment) {
        int res = paymentService.create(payment);
        log.info("********CREATE..." + port + ": " + res);
        if (res > 0){
            return new CommonResult(200,"CREATE sucess..." + port,res);
        }else {
            return new CommonResult(404,"CREATE filed..." + port);
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("=".repeat(5)+"********QUERY..." + port + ": "  + payment);
        if (payment != null){
            return new CommonResult(200,"QUERY sucess..." + port,payment);
        }else {
            return new CommonResult(404,"QUERY filed..." + port);
        }
    }

    /*
    通过 DiscoveryClient 可以获取注册进EurekaServer应用中的各种微服务的详细信息，包括该微服务的名称、IP、端口以及访问Uri等信息
     */
    @GetMapping("/payment/discovery")
    public Object discovery(){
        log.info("~".repeat(15) +"DiscoveryClient"+ port + "-".repeat(5)+ discoveryClient.toString());
        /*List<String> services = discoveryClient.getServices();
        services.forEach(element -> log.info("*".repeat(8) + "element: "+ element));
        log.info("#".repeat(15));
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        instances.forEach(serviceInstance -> log.info(serviceInstance.getServiceId() + "|".repeat(5)+serviceInstance.getHost()+ "|".repeat(5)+
        serviceInstance.getPort()+ "|".repeat(5)+serviceInstance.getUri()));*/
        return this.discoveryClient;
    }
}
