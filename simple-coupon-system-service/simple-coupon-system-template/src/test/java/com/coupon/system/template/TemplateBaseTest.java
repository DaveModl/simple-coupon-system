package com.coupon.system.template;

import com.alibaba.fastjson.JSON;
import com.coupon.system.common.exception.CouponException;
import com.coupon.system.template.service.ITemplateBaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * @author wangheng
 * description 优惠券模板基础服务测试
 * date 2020-02-07 17:03
 * version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TemplateBaseTest {

    @Autowired
    private ITemplateBaseService templateBaseService;

    @Test
    public void testBuildTemplateInfo() throws CouponException {
        System.out.println(JSON.toJSONString(templateBaseService.buildTemplateInfo(10)));
//        System.out.println(JSON.toJSONString(templateBaseService.buildTemplateInfo(12)));
    }

    @Test
    public void testFindAllUsableTemplate() {
        System.out.println(JSON.toJSONString(templateBaseService.findAllUsableTemplate()));
    }

    @Test
    public void testFindId2TemplateSDK() {

        System.out.println(JSON.toJSONString(
                templateBaseService.findIds2TemplateSDK(Arrays.asList(10, 2, 3))
        ));
    }
}
