package com.yy.wxpay.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author YY
 * @date 2019/12/4
 * @description 统一下单（预）请求微信响应结果
 */
@Data
public class WXPrePayResult {
    private List a;
    private String b;
    private String c;
    private Map d;
    private Map f;
}
