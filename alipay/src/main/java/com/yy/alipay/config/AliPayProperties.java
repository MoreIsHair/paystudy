package com.yy.alipay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author YY
 * @date 2019/12/11
 * @description
 */

@Component
@Data
@ConfigurationProperties(prefix = "ali")
public class AliPayProperties {
    private String appId;

    /**
     * 回调地址支付宝服务器异步通知
     * 页面路径需http://格式的完整路径，不能加?id=123这类自定义参数
     */
    private String notifyUrl;

    /**
     * 签名方式
     * 支付宝 RSA2
     */
    private String signType;

    /**
     * 私钥
     */
    private String privateKey;
    /**
     * 公钥
     */
    private String publicKey;
    /**
     * 页面跳转同步通知页面路径
     * 需http://格式的完整路径，不能加?id=123这类自定义参数
     */
    private String returnUrl;
    /**
     * 支付宝网关
     */
    private String gatewayUrl;

    /**
     * 字符编码格式
     */
    private String charset;

    private String format;
}
