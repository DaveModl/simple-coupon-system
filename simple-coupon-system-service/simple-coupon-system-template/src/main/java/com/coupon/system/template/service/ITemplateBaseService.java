package com.coupon.system.template.service;



import com.coupon.system.common.exception.CouponException;
import com.coupon.system.common.vo.CouponTemplateSDK;
import com.coupon.system.template.entity.CouponTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 优惠券模板基础(view, delete...)服务定义
 */
public interface ITemplateBaseService {

    /**
     * <h2>根据优惠券模板 id 获取优惠券模板信息</h2>
     * @param id 模板 id
     * @return {@link CouponTemplate} 优惠券模板实体
     * */
    CouponTemplate buildTemplateInfo(Integer id) throws CouponException;

    /**
     * <h2>查找所有可用的优惠券模板</h2>
     * @return {@link CouponTemplateSDK}s
     * */
    List<CouponTemplateSDK> findAllUsableTemplate();

    /**
     * <h2>获取模板 ids 到 CouponTemplateSDK 的映射</h2>
     * @param ids 模板 ids
     * @return Map<key: 模板 id， value: CouponTemplateSDK>
     * */
    Map<Integer, CouponTemplateSDK> findIds2TemplateSDK(Collection<Integer> ids);
}
