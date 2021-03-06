package com.coupon.system.template.service.impl;

import com.coupon.system.common.constant.Constant;
import com.coupon.system.template.entity.CouponTemplate;
import com.coupon.system.template.repository.CouponTemplateRepository;
import com.coupon.system.template.service.IAsyncService;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author wangheng
 * description  异步服务接口实现
 * date 2020-02-06 17:05
 * version 1.0
 */
@Slf4j
@Service
public class AsyncServiceImpl implements IAsyncService {


    private final CouponTemplateRepository templateRepository;

    private final StringRedisTemplate redisTemplate;

    @Autowired
    public AsyncServiceImpl(CouponTemplateRepository templateRepository, StringRedisTemplate redisTemplate) {
        this.templateRepository = templateRepository;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 根据模板异步的创建优惠券码
     *
     * @param: template {@link CouponTemplate} 优惠券模板实体
     * @return:
     */
    @Async("getAsyncExecutor")
    @Override
    public void asyncConstructCouponByTemplate(CouponTemplate template) {

        Stopwatch watch = Stopwatch.createStarted();
        Set<String> couponCodes = buildCouponCode(template);

        // 优惠券码在redis中key存储的样例  coupon_template_code_1
        String redisKey = String.format("%s%s", Constant.RedisPrefix.COUPON_TEMPLATE, template.getId().toString());
        log.info("Push CouponCode To Redis: {}",
                redisTemplate.opsForList().rightPushAll(redisKey,couponCodes));

        watch.stop();
        log.info("Construct CouponCode By Template Cost: {}ms",
                watch.elapsed(TimeUnit.MILLISECONDS));

        log.info("CouponTemplate({}) Is Available!", template.getId());
    }

    /**
     * 构造优惠券码   优惠券码(对应于每一张优惠券, 18位)
     * 前四位:  产品线 + 类型
     * 中间六位: 日期随机
     * 后八位: 0 ~ 9 随机数构成
     *
     * @param: template {@link CouponTemplate} 优惠券模板实体
     * @return: Set<String> 与 template.count 相同个数的优惠券码
     */
    private Set<String> buildCouponCode(CouponTemplate template) {
        Stopwatch watch = Stopwatch.createStarted();
        //固定生成容量
        Set<String> result = new HashSet<>(template.getCount());
        // 前四位
        String prefix4 = template.getProductLine().getCode().toString()
                + template.getCategory().getCode();
        String date = new SimpleDateFormat("yyMMdd").format(template.getCreateTime());

        for (int i = 0; i != template.getCount(); ++i) {
            result.add(prefix4 + buildCouponCodeSuffix14(date));
        }
        while (result.size() < template.getCount()) {
            result.add(prefix4 + buildCouponCodeSuffix14(date));
        }
        assert result.size() == template.getCount();

        watch.stop();
        log.info("Build Coupon Code Cost: {}ms", watch.elapsed(TimeUnit.MILLISECONDS));
        return result;
    }

    /**
     *  构造优惠券码后14位
     *  @param date 创建优惠券的日期
     *  @return 14位的优惠券码
     */
    private String buildCouponCodeSuffix14(String date) {

        char[] bases = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9'};

        //中间六位
        List<Character> chars = date.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
        String mid6 = chars.stream().map(Objects::toString).collect(Collectors.joining());

        //后八位
        String suffix8 = RandomStringUtils.random(1, bases)
                + RandomStringUtils.randomNumeric(7);

        return mid6 + suffix8;
    }
}
