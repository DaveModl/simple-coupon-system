package com.coupon.system.template;

import com.alibaba.fastjson.JSON;
import com.coupon.system.common.constant.CouponCategory;
import com.coupon.system.common.constant.DistributeTarget;
import com.coupon.system.common.constant.PeriodType;
import com.coupon.system.common.constant.ProductLine;
import com.coupon.system.common.exception.CouponException;
import com.coupon.system.common.vo.TemplateRule;
import com.coupon.system.template.service.IBuildTemplateService;
import com.coupon.system.template.vo.TemplateRequest;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

/**
 * @author wangheng
 * description 构建优惠券模板测试
 * date 2020-02-07 16:08
 * version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BuildTemplateTest {

    @Autowired
    private IBuildTemplateService buildTemplateService;

    @Test
    public void testBuildTemplate() throws InterruptedException, CouponException {

        System.out.println(JSON.toJSONString(buildTemplateService.buildTemplate(fakeTemplateRequest())));
        Thread.sleep(5000);
    }

    /**
     * 创建测试模板  TemplateRequest
     */
    private TemplateRequest fakeTemplateRequest() {

        TemplateRequest request = new TemplateRequest();
        request.setName("优惠券模板-" + new Date().getTime());
        request.setLogo("模版图标");
        request.setDesc("这是一张优惠券模板");
        request.setCategory(CouponCategory.MANJIAN.getCode());
        request.setProductLine(ProductLine.DAOMA.getCode());
        request.setCount(2000);
        request.setUserId(20001L);
        request.setTarget(DistributeTarget.SINGLE.getCode());

        TemplateRule rule = new TemplateRule();
        rule.setExpiration(new TemplateRule.Expiration(
                PeriodType.SHIFT.getCode(), 1, DateUtils.addDays(
                new Date(), 60).getTime()));
        rule.setDiscount(new TemplateRule.Discount(5, 1));
        rule.setLimitation(1);
        rule.setUsage(new TemplateRule.Usage(
                "北京", "北京市", JSON.toJSONString(Arrays.asList("生鲜", "全品类"))));
        rule.setWeight(JSON.toJSONString(Collections.EMPTY_LIST));
        request.setRule(rule);
        return request;
    }
}
