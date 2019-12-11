package com.yy.wxpay.config;

import cn.hutool.core.util.StrUtil;
import com.yy.common.config.BasePayPropertiesConfig;
import com.yy.common.constant.enums.SignType;
import lombok.Getter;

import javax.net.ssl.SSLContext;

/**
 * @author YY
 * @date 2019/12/2
 * @description
 */
@Getter
public class WxPayConfig extends BasePayPropertiesConfig {
    public static final String DEFAULT_PAY_BASE_URL = "https://api.mch.weixin.qq.com";

    /**
     * 统一下单 https://api.mch.weixin.qq.com/pay/unifiedorder
     * 增加sandboxnew路径，变更为https://api.mch.weixin.qq.com/sandboxnew/pay/micropay，即可接入沙箱验收环境，其它接口类似;
     */
    public static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 微信支付接口请求地址域名部分.
     */
    private String payBaseUrl = DEFAULT_PAY_BASE_URL;

    /**
     * http请求连接超时时间.
     */
    private int httpConnectionTimeout = 5000;

    /**
     * http请求数据读取等待时间.
     */
    private int httpTimeout = 10000;

    /**
     * 服务商模式下的子商户公众账号ID.
     */
    private String subAppId;
    /**
     * 商户号.
     */
    private String mchId;
    /**
     * 商户密钥.
     */
    private String mchKey;
    /**
     * 服务商模式下的子商户号.
     */
    private String subMchId;
    /**
     * 交易类型.
     * <pre>
     * JSAPI--公众号支付
     * NATIVE--原生扫码支付
     * APP--app支付
     * </pre>
     */
    private String tradeType;

    private SSLContext sslContext;
    /**
     * p12证书文件的绝对路径或者以classpath:开头的类路径.
     */
    private String keyPath;

    /**
     * p12证书文件内容的字节数组.
     */
    private byte[] keyContent;
    /**
     * 微信支付是否使用仿真测试环境.
     * 默认不使用
     */
    private boolean useSandboxEnv = false;


    private String openId;


    /**
     * 返回所设置的微信支付接口请求地址域名.
     *
     * @return 微信支付接口请求地址域名
     */
    public String getPayBaseUrl() {
        if (StrUtil.isEmpty(this.payBaseUrl)) {
            return DEFAULT_PAY_BASE_URL;
        }
        return this.payBaseUrl;
    }

    @Override
    public String getAppId() {
        return this.appId;
    }

    @Override
    public String getNotifyUrl() {
        return this.notifyUrl;
    }

    @Override
    public SignType getSignType() {
        return this.signType;
    }

    public void setPayBaseUrl(String payBaseUrl) {
        this.payBaseUrl = payBaseUrl;
    }

    public void setSubAppId(String subAppId) {
        this.subAppId = subAppId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public void setSslContext(SSLContext sslContext) {
        this.sslContext = sslContext;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

    public void setKeyContent(byte[] keyContent) {
        this.keyContent = keyContent;
    }

    public void setUseSandboxEnv(boolean useSandboxEnv) {
        this.useSandboxEnv = useSandboxEnv;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }




}
