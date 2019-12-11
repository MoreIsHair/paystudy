package com.yy.wxpay.model;

import lombok.Data;

/**
 * @author YY
 * @date 2019/12/4
 * @description 统一下单（预）请求微信参数
 */
@Data
public class WXPrePayParam {
    String appid;
    String body;
    String mch_id;
    String nonce_str;
    String openid;
    String out_trade_no;
    String spbill_create_ip;
    String total_fee;
    String trade_type;
    String notify_url;
    String sign;
}
