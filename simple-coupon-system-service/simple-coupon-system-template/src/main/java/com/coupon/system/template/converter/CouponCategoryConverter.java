package com.coupon.system.template.converter;



import com.coupon.system.common.constant.CouponCategory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 分类枚举类型转换器
 *
 */
@Converter
public class CouponCategoryConverter implements AttributeConverter<CouponCategory, String> {
    /**
     * entity ---> database
     * @param couponCategory
     * @return
     */
    @Override
    public String convertToDatabaseColumn(CouponCategory couponCategory) {
        return couponCategory.getCode();
    }

    /**
     * entity ---> database
     * @param code
     * @return
     */
    @Override
    public CouponCategory convertToEntityAttribute(String code) {
        return CouponCategory.of(code);
    }
}
