package com.coupon.system.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * 优惠劵分类
 */
@Getter
@AllArgsConstructor
public enum CouponCategory {

    /**
     * 满减券
     */
    MANJIAN("满减券", "001"),
    /**
     * 折扣券
     */
    ZHEKOU("折扣券", "002"),
    /**
     * 立减券
     */
    LIJIAN("立减券", "003");
    /**
     * 优惠劵描述 分类
     */
    private String description;

    /**
     * 优惠券分类 编码
     */
    private String code;

    /**
     * 判断优惠劵是否合法
     * @param code
     * @return
     */
    public static CouponCategory of(String code) {
        Objects.requireNonNull(code);
        return Stream.of(values())
                .filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + "not exist!"));
    }

}
