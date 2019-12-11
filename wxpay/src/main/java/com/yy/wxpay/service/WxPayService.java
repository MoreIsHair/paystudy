package com.yy.wxpay.service;


import com.yy.common.service.PayService;
import com.yy.wxpay.config.WxPayConfig;

/**
 * @author YY
 * @date 2019/12/2
 * @description
 */
public interface WxPayService extends PayService {
    @Override
    WxPayConfig getConfig();
}
