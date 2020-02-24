package com.coupon.system.gateway.filter;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 限流全局过滤器
 */
@Slf4j
public class RateLimitGatewayFilter implements GatewayFilter, Ordered {
private RateLimiter rateLimiter;
    public RateLimitGatewayFilter(double tokens) {
        this.rateLimiter = RateLimiter.create(tokens);
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (rateLimiter.tryAcquire()){
            log.info("get rate token success.");
            return chain.filter(exchange);
        }else {
            log.error("rate limit: {}",exchange.getRequest().getURI().getRawPath());
            exchange.getResponse().setStatusCode(HttpStatus.PAYMENT_REQUIRED);
            return exchange.getResponse().setComplete();
        }
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
