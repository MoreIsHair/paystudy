package com.yy.wxpay.config;

import cn.hutool.core.util.StrUtil;
import com.yy.wxpay.constant.enums.PayWay;
import com.yy.wxpay.service.WxPayService;
import com.yy.wxpay.service.impl.WxPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author YY
 * @date 2019/12/2
 * @description
 */
@Configuration
@EnableConfigurationProperties(WxPayProperties.class)
@ConditionalOnClass(WxPayService.class)
public class WxPayConfiguration {

    @Autowired
    private WxPayProperties wxPayProperties;

    @Bean
    @ConditionalOnMissingBean
    public WxPayService wxPayService() {
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId(StrUtil.trimToNull(wxPayProperties.getAppId()));
        wxPayConfig.setKeyPath(StrUtil.trimToNull(wxPayProperties.getKeyPath()));
        wxPayConfig.setMchId(StrUtil.trimToNull(wxPayProperties.getMchId()));
        wxPayConfig.setMchKey(StrUtil.trimToNull(wxPayProperties.getMchKey()));
        wxPayConfig.setNotifyUrl(StrUtil.trimToNull(wxPayProperties.getNotifyUrl()));
        wxPayConfig.setTradeType(StrUtil.isBlank(wxPayProperties.getTradeType())
                ? PayWay.NATIVE.name() : wxPayProperties.getTradeType());
        // 可以指定是否使用沙箱环境
        wxPayConfig.setUseSandboxEnv(false);
        WxPayService wxPayService = new WxPayServiceImpl(wxPayConfig);
        return wxPayService;
    }
}
