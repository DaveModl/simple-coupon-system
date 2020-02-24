package com.coupon.system.disturibution.feign.hystrix;


import com.coupon.system.common.exception.CouponException;
import com.coupon.system.common.vo.CommonResponse;
import com.coupon.system.common.vo.SettlementInfo;
import com.coupon.system.disturibution.feign.SettlementClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 熔断策略
 */
@Slf4j
@Component
public class SettlementClientHystrix implements SettlementClient {

    /**
     * <h2>优惠券规则计算</h2>
     * @param settlement {@link SettlementInfo}
     */
    @Override
    public CommonResponse<SettlementInfo> computeRule(SettlementInfo settlement)
            throws CouponException {

        log.error("[simple-coupon-system-settlement] computeRule" +
                "request error");

        settlement.setEmploy(false);
        settlement.setCost(-1.0);

        return new CommonResponse<>(
                -1,
                "[simple-coupon-system-settlement] request error",
                settlement
        );
    }
}
