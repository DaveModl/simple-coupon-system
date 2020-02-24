package com.coupon.system.settlement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class CouponSettlementMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(CouponSettlementMainApplication.class,args);
    }
}
