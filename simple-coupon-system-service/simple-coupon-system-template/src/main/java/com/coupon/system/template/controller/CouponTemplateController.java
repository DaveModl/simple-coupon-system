package com.coupon.system.template.controller;

import com.alibaba.fastjson.JSON;
import com.coupon.system.common.exception.CouponException;
import com.coupon.system.common.vo.CouponTemplateSDK;
import com.coupon.system.template.entity.CouponTemplate;
import com.coupon.system.template.service.IBuildTemplateService;
import com.coupon.system.template.service.ITemplateBaseService;
import com.coupon.system.template.vo.TemplateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * 优惠券模板相关功能控制器
 */
@Slf4j
@RestController
public class CouponTemplateController {

    /**
     * 注入构建优惠券模版服务
     */
    private final IBuildTemplateService buildTemplateService;

    /**
     * 注入优惠券模板基础服务
     */
    private final ITemplateBaseService templateBaseService;
    @Autowired
    public CouponTemplateController(IBuildTemplateService buildTemplateService, ITemplateBaseService templateBaseService) {
        this.buildTemplateService = buildTemplateService;
        this.templateBaseService = templateBaseService;
    }

    /**
     * 构建优惠券模板
     * 127.0.0.1:7001/coupon-template/template/build
     * 网关转发服务接口: 127.0.0.1:8084/coupon-template/template/build
     *
     * @param request
     * @return CouponTemplate 优惠券模板
     */
    @PostMapping("/template/build")
    public CouponTemplate buildTemplate(@RequestBody TemplateRequest request) throws CouponException {
        log.info("Build Template: {}", JSON.toJSONString(request));
        return buildTemplateService.buildTemplate(request);
    }

    /**
     * 构建优惠券模版详情
     * 127.0.0.1:7001/coupon-template/template/info?id=1
     * 网关转发服务接口: 127.0.0.1:8084/coupon-template/template/info?id=1
     *
     * @param id
     * @return CouponTemplate 优惠券模板
     */
    @GetMapping("/template/info")
    public CouponTemplate buildTemplateInfo(@RequestParam("id") Integer id) throws CouponException {
        log.info("Build Template Info For: {}", id);
        return templateBaseService.buildTemplateInfo(id);
    }

    /**
     * 查找所有可用的优惠券模板
     * 127.0.0.1:7001/coupon-template/template/sdk/all
     * 网关转发服务接口: 127.0.0.1:8084/coupon-template/template/sdk/all
     *
     * @param
     * @return List<CouponTemplateSDK>
     */
    @GetMapping("/template/sdk/all")
    public List<CouponTemplateSDK> findAllUsableTemplate() {
        log.info("Find All Usable Template");
        return templateBaseService.findAllUsableTemplate();
    }

    /**
     * 获取模板ids 到CouponTemplateSDK d的映射
     * 127.0.0.1:7001/coupon-template/template/sdk/infos
     * 网关转发服务接口: 127.0.0.1:8084/coupon-template/template/sdk/infos
     *
     * @param ids
     * @return Map<Integer , CouponTemplateSDK>
     */
    @GetMapping("/template/sdk/infos")
    public Map<Integer, CouponTemplateSDK> findIds2TemplateSDK(@RequestParam("ids") Collection<Integer> ids) {
        log.info("findIds2TemplateSDK: {}", JSON.toJSONString(ids));
        return templateBaseService.findIds2TemplateSDK(ids);
    }
}
