package com.yy.wxpay.service.impl;


import com.yy.common.service.impl.BasePayServiceImpl;
import com.yy.wxpay.config.WxPayConfig;
import com.yy.wxpay.service.WxPayService;

/**
 * @author YY
 * @date 2019/12/2
 * @description
 */
public class WxPayServiceImpl extends BasePayServiceImpl implements WxPayService {

    public WxPayServiceImpl(WxPayConfig wxPayConfig) {
        this.config = wxPayConfig;
    }

    @Override
    public WxPayConfig getConfig() {
        return super.getConfig() instanceof  WxPayConfig ? (WxPayConfig)super.getConfig() : null;
    }

    @Override
    public Boolean tradeRefund(String orderNo, Integer refundFee) {
        return null;
    }

    @Override
    public String toPay(String orderNo, Integer totalFee) {
        return null;
    }
}
