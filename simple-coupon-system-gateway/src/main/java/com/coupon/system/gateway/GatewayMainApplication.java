package com.coupon.system.gateway;

import com.coupon.system.gateway.filter.AccessLogGatewayFilter;
import com.coupon.system.gateway.filter.RateLimitGatewayFilter;
import com.coupon.system.gateway.filter.TokenGatewayFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayMainApplication.class,args);
    }

    @Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("basic_coupon_path",r ->r.path("/coupon-template/**")
                        .filters(f -> f.filter(new AccessLogGatewayFilter(),0))
                        .uri("http://localhost:7001"))
                .route("limit_coupon_path",r -> r.path("/coupon-template/**")
                .filters(f -> f.filter(new RateLimitGatewayFilter(2.0),0))
                .uri("http://localhost:7001"))


                .route("basic_coupon_path2",r ->r.path("/coupon-distribution/**")
                        .filters(f -> f.filter(new AccessLogGatewayFilter(),0))
                        .uri("http://localhost:7002"))
                .route("limit_coupon_path",r -> r.path("/coupon-distribution/**")
                        .filters(f -> f.filter(new RateLimitGatewayFilter(2.0),0))
                        .uri("http://localhost:7002"))


                .route("basic_coupon_path3",r ->r.path(" /coupon-settlement/**")
                .filters(f -> f.filter(new AccessLogGatewayFilter(),0))
                .uri("http://localhost:7003"))
                .route("limit_coupon_path3",r -> r.path(" /coupon-settlement/**")
                        .filters(f -> f.filter(new RateLimitGatewayFilter(2.0),0))
                        .uri("http://localhost:7003")
                ).build();

    }
    @Bean
    public TokenGatewayFilter tokenGatewayFilter(){
        return new TokenGatewayFilter();
    }

}
