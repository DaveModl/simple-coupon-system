package com.coupon.system.settlement.controller;

import com.alibaba.fastjson.JSON;
import com.coupon.system.common.exception.CouponException;
import com.coupon.system.common.vo.SettlementInfo;
import com.coupon.system.settlement.excutor.ExecuteManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>结算服务</h1>
 */
@Slf4j
@RestController
public class SettlementController {

    /**
     * 结算规则执行管理器
     */
    private final ExecuteManager executeManager;

    @Autowired
    public SettlementController(ExecuteManager executeManager) {
        this.executeManager = executeManager;
    }

    /**
     * <h2>优惠券结算</h2>
     * 127.0.0.1:7003/coupon-settlement/settlement/compute
     * 127.0.0.1:8084/coupon-settlement/settlement/compute
     * @param settlement
     * @return 结算信息
     */
    @PostMapping("/settlement/compute")
    public SettlementInfo computeRule(@RequestBody SettlementInfo settlement)
            throws CouponException {

        log.info("settlement: {}", JSON.toJSONString(settlement));

        return executeManager.computeRule(settlement);
    }
}
