package com.coupon.system.template.schedule;

import com.coupon.system.common.vo.TemplateRule;
import com.coupon.system.template.entity.CouponTemplate;
import com.coupon.system.template.repository.CouponTemplateRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 定时清理已过期的优惠券模板
 */
@Slf4j
@Component
public class ScheduleTask {


    private final CouponTemplateRepository templateRepository;

    @Autowired
    public ScheduleTask(CouponTemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    /**
     * <h2>下线已过期的优惠券模板</h2>
     * 每小时检测一次
     */
    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void offlineCouponTemplate() {
        log.info("Start TExpire CouponTemplate");

        List<CouponTemplate> templates = templateRepository.findAllByExpired(false);
        if (CollectionUtils.isEmpty(templates)) {
            log.info("Done To Expire CouponTemplate.");
            return;
        }

        Date cur = new Date();
        List<CouponTemplate> expiredTemplates = new ArrayList<>(templates.size());
        templates.forEach(t -> {
            // 根据优惠券模板规则中的 "过期规则" 校验模板是否过期
            TemplateRule rule = t.getRule();
            if (rule.getExpiration().getDeadline() < cur.getTime()) {
                t.setExpired(true);
                expiredTemplates.add(t);
            }
        });

        if (CollectionUtils.isNotEmpty(expiredTemplates)) {
            log.info("Expired CouponTemplate Num: {}", templateRepository.saveAll(expiredTemplates));
        }
        log.info("Done To Expire CouponTemplate.");
    }
}
