package com.atguigu.springcloud.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;

public class CustomBlockHandler {
    /*
    要注意自定义的Sentinel blockHandler 逻辑必须是 static 类型的函数
     */
    public static CommonResult handlerException01(BlockException exception){
        return new CommonResult(404,"按客户自定义，global handlerException---------01");
    }

    public static CommonResult handlerException02(BlockException exception){
        return new CommonResult(404,"按客户自定义，global handlerException----------02");
    }
}
