package com.yy.common.service;

import com.yy.common.config.BasePayPropertiesConfig;

/**
 * @author YY
 * @date 2019/12/10
 * @description
 */
public interface PayService {
    /**
     * 获取配置.
     *
     * @return the config
     */
    BasePayPropertiesConfig getConfig();

    /**
     * 获取支付请求url前缀，沙箱环境可能不一样.
     *
     * @return the pay base url
     */
    String getPayBaseUrl();

    /**
     * 退款
     */
    Boolean tradeRefund(String orderNo, Integer refundFee);

    /**
     * 预支付
     */
    String toPay(String orderNo, Integer totalFee) throws Exception;
}
