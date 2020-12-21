package com.atguigu.springcloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.util.Date;

@Component
@Slf4j
public class MyLogGateWayFilter implements GlobalFilter, Order {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("1".repeat(15) + "ServerWebExchange : "  + exchange.toString());
        log.info(chain.toString());
        return chain.filter(exchange);
    }

    /*
    返回的值决定了当前过滤器的顺序
    返回的值越小，过滤器的优先级越高；
     */
    @Override
    public int value() {
        return 0;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
