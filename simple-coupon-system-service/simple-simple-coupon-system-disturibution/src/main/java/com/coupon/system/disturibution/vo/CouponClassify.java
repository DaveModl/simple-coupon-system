package com.coupon.system.disturibution.vo;


import com.coupon.system.common.constant.PeriodType;
import com.coupon.system.disturibution.constant.CouponStatus;
import com.coupon.system.disturibution.entity.Coupon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据优惠券状态, 将用户优惠券进行分类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponClassify {

    /**
     * 可使用的
     */
    private List<Coupon> usable;
    /**
     * 已使用的
     */
    private List<Coupon> used;
    /**
     * 已过期的
     */
    private List<Coupon> expired;

    /**
     * 对当前优惠券进行分类
     */
    public static CouponClassify classify(List<Coupon> coupons) {

        ArrayList<Coupon> usable = new ArrayList<>(coupons.size());
        ArrayList<Coupon> used = new ArrayList<>(coupons.size());
        ArrayList<Coupon> expired = new ArrayList<>(coupons.size());

        coupons.forEach(e -> {

            //判断优惠券是否过期
            boolean isTimeExpire;
            long curTime = System.currentTimeMillis();

            if (e.getTemplateSDK().getRule().getExpiration().getPeriod().equals(
                    PeriodType.REGULAR.getCode())) {
                isTimeExpire = e.getTemplateSDK().getRule().getExpiration().getDeadline() <= curTime;
            } else {
                isTimeExpire = DateUtils.addDays(
                        e.getAssignTime(),
                        e.getTemplateSDK().getRule().getExpiration().getGap()).getTime() <= curTime;
            }

            if (e.getStatus() == CouponStatus.USED) {
                used.add(e);
            } else if (e.getStatus() == CouponStatus.EXPIRED || isTimeExpire) {
                expired.add(e);
            } else {
                usable.add(e);
            }
        });

        return new CouponClassify(usable, used, expired);
    }


}
