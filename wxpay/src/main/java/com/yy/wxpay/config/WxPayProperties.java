package com.yy.wxpay.config;

import com.yy.wxpay.constant.enums.PayWay;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author YY
 * @date 2019/12/2
 * @description
 */
@Data
@Component
@ConfigurationProperties(prefix = "wx")
public class WxPayProperties {
    /**
     * 设置微信公众号或者小程序等的appid
     */
    private String appId;

    /**
     * 微信支付商户号
     */
    private String mchId;

    /**
     * 微信支付商户密钥
     */
    private String mchKey;

    /**
     * 服务商模式下的子商户公众账号ID，普通模式请不要配置，请在配置文件中将对应项删除
     */
    private String subAppId;

    /**
     * 服务商模式下的子商户号，普通模式请不要配置，最好是请在配置文件中将对应项删除
     */
    private String subMchId;

    /**
     * apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
     */
    private String keyPath;

    /**
     * 回调地址（需要和接口中的地址一致）
     */
    private String notifyUrl;

    /**
     * 支付类型
     *
     * @see PayWay
     */
    private String tradeType;

}
