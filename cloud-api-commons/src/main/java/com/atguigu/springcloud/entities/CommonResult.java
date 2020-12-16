package com.atguigu.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult<T> {
    Integer code;
    String msg;
    T t;

    public CommonResult(Integer code, String msg) {
        this(code,msg,null);
    }
}
