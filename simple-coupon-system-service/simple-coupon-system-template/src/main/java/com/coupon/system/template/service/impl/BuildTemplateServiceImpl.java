package com.coupon.system.template.service.impl;


import com.coupon.system.common.exception.CouponException;
import com.coupon.system.template.entity.CouponTemplate;
import com.coupon.system.template.repository.CouponTemplateRepository;
import com.coupon.system.template.service.IAsyncService;
import com.coupon.system.template.service.IBuildTemplateService;
import com.coupon.system.template.vo.TemplateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangheng
 * description 构建优惠券模板接口实现
 * date 2020-02-06 18:17
 * version 1.0
 */
@Slf4j
@Service
public class BuildTemplateServiceImpl implements IBuildTemplateService {


    private final IAsyncService asyncService;


    private final CouponTemplateRepository templateRepository;

    @Autowired
    public BuildTemplateServiceImpl(IAsyncService asyncService, CouponTemplateRepository templateRepository) {
        this.asyncService = asyncService;
        this.templateRepository = templateRepository;
    }

    /**
     * <h2>创建优惠券模板</h2>
     * @param: request {@link TemplateRequest} 模板信息请求对象
     * @return: {@link CouponTemplate} 优惠券模板实体
     */
    @Override
    public CouponTemplate buildTemplate(TemplateRequest request) throws CouponException {

        //参数合法性校验
        if (!request.validate()) {
            throw new CouponException("BuildTemplate Param Is Not Valid!");
        }

        //判断同名的优惠券模板是否存在
        if (null != templateRepository.findByName(request.getName())) {
            throw new CouponException("Exist Same Name Template!");
        }

        // 构造 CouponTemplate 并保存到数据库中
        CouponTemplate template = requestToTemplate(request);
        template = templateRepository.save(template);

        //根据优惠券模板异步生成优惠券码
        asyncService.asyncConstructCouponByTemplate(template);
        return template;
    }

    /**
     * <h2>将 TemplateRequest 转换为 CouponTemplate</h2>
     */
    private CouponTemplate requestToTemplate(TemplateRequest request) {

        return new CouponTemplate(
                request.getName(),
                request.getLogo(),
                request.getDesc(),
                request.getCategory(),
                request.getProductLine(),
                request.getCount(),
                request.getUserId(),
                request.getTarget(),
                request.getRule()
        );
    }
}
