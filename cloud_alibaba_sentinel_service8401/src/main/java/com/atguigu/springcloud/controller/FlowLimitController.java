package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class FlowLimitController {
    @GetMapping("/testA")
    public String testA() {
        return "--------testA";
    }

    @GetMapping("/testB")
    public String testB() {
        return "--------testB";
    }

    @GetMapping("/testC")
    public String testC() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("2".repeat(15) + " testC 测试 慢调用比例");
        return "--------testC";
    }

    @GetMapping("/testD")
    public String testD() {
        log.info("2".repeat(15) + " testD 测试 异常比例");
        int i = 10 / 0;
        return "--------testD";
    }

    @GetMapping("/testHotkey")
    @SentinelResource(value = "testHotkey", blockHandler = "deal_testHotkey")
    public String testHotkey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2){
        return "--------testHotkey=======P1: " + p1 + "P2: " + p2;
    }
    /*
    替代 Sentinel 默认的降级提示函数
     */
    public String deal_testHotkey(String p1, String p2, BlockException exception){
        return "--------deal_testHotkey=======P1: " + p1 + "P2: " + p2;
    }
}
