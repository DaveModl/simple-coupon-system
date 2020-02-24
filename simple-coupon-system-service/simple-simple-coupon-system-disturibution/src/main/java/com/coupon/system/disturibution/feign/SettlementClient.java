package com.coupon.system.disturibution.feign;


import com.coupon.system.common.exception.CouponException;
import com.coupon.system.common.vo.CommonResponse;
import com.coupon.system.common.vo.SettlementInfo;
import com.coupon.system.disturibution.feign.hystrix.SettlementClientHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 优惠券结算微服务 Feign 接口定义
 */
@SuppressWarnings("all")
@FeignClient(value = "simple-coupon-system-settlement",fallback = SettlementClientHystrix.class)
public interface SettlementClient {

    /**
     * <h2>优惠券规则计算</h2>
     */
    @RequestMapping(value = "/coupon-settlement/settlement/compute",
            method = RequestMethod.POST)
    CommonResponse<SettlementInfo> computeRule(
            @RequestBody SettlementInfo settlement) throws CouponException;

}
