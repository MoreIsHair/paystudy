package com.yy.alipay.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.yy.alipay.config.AliPayPropertiesConfig;
import com.yy.alipay.service.AliPayService;
import com.yy.common.service.impl.BasePayServiceImpl;

import java.util.HashMap;

/**
 * @author YY
 * @date 2019/12/10
 * @description
 */
public class AliPayServiceImpl extends BasePayServiceImpl implements AliPayService {

    private AlipayClient alipayClient;

    public AliPayServiceImpl(AliPayPropertiesConfig config) {
        this.config = config;
    }

    public AliPayServiceImpl(AliPayPropertiesConfig config,AlipayClient alipayClient) {
        this.config = config;
        this.alipayClient = alipayClient;
    }

    @Override
    public AliPayPropertiesConfig getConfig() {
        return super.getConfig() instanceof  AliPayPropertiesConfig ? (AliPayPropertiesConfig)super.getConfig() : null;
    }



    @Override
    public Boolean tradeRefund(String orderNo, Integer refundFee) {
        return null;
    }

    @Override
    public String toPay(String orderNo, Integer totalFee) throws AlipayApiException {
        // 2、设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        // 页面跳转同步通知页面路径
        alipayRequest.setReturnUrl(getConfig().getReturnUrl());
        // 服务器异步通知页面路径
        alipayRequest.setNotifyUrl(getConfig().getNotifyUrl());
        // 封装参数
        HashMap<String, Object> payMap = MapUtil.of("out_trade_no", orderNo);
        payMap.put("total_amount",totalFee);
        payMap.put("body","商品描述，可空");
        payMap.put("timeout_express","10m");
        alipayRequest.setBizContent(JSONUtil.toJsonStr(payMap));
        // 3、请求支付宝进行付款，并获取支付结果
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        // 返回付款信息
        return result;
    }
}
