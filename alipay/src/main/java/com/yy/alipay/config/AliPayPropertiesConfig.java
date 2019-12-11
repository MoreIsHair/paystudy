package com.yy.alipay.config;

import cn.hutool.core.util.StrUtil;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.yy.alipay.service.AliPayService;
import com.yy.alipay.service.impl.AliPayServiceImpl;
import com.yy.common.config.BasePayPropertiesConfig;
import com.yy.common.constant.enums.SignType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author YY
 * @date 2019/12/10
 * @description
 */
@Configuration
@EnableConfigurationProperties(AliPayProperties.class)
@ConditionalOnClass(AliPayService.class)
@Getter
public class AliPayPropertiesConfig extends BasePayPropertiesConfig {

    private static final String payBaseUrl = "";
    private static final String DEFAULT_FORMAT = "json";
    private String privateKey;
    private String publicKey;
    private String returnUrl;
    private String gatewayUrl;
    private String charset;
    private String format;

    @Autowired
    AliPayProperties aliPayProperties;

    @Override
    public String getPayBaseUrl() {
        return payBaseUrl;
    }

    @ConditionalOnMissingBean
    @Bean
    public AliPayService aliPayService(AlipayClient alipayClient){
        AliPayPropertiesConfig aliPayPropertiesConfig = new AliPayPropertiesConfig();
        aliPayPropertiesConfig.setAppId(StrUtil.trimToNull(aliPayProperties.getAppId()));
        aliPayPropertiesConfig.setNotifyUrl(StrUtil.trimToNull(aliPayPropertiesConfig.getNotifyUrl()));
        aliPayPropertiesConfig.setSignType(
                Arrays.stream(SignType.values()).filter(signType1 ->
                        signType1.name().equals(StrUtil.trim(aliPayProperties.getSignType())))
                        .findFirst().get());
        aliPayPropertiesConfig.setCharset((StrUtil.trimToNull(aliPayProperties.getCharset())));
        aliPayPropertiesConfig.setPrivateKey((StrUtil.trimToNull(aliPayProperties.getPrivateKey())));
        aliPayPropertiesConfig.setPublicKey((StrUtil.trimToNull(aliPayProperties.getPublicKey())));
        aliPayPropertiesConfig.setGatewayUrl((StrUtil.trimToNull(aliPayProperties.getGatewayUrl())));
        aliPayPropertiesConfig.setReturnUrl((StrUtil.trimToNull(aliPayProperties.getReturnUrl())));
        aliPayPropertiesConfig.setFormat((StrUtil.isBlank(aliPayProperties.getFormat())
                ?DEFAULT_FORMAT:StrUtil.trimToNull(aliPayProperties.getFormat())));
        return new AliPayServiceImpl(aliPayPropertiesConfig,alipayClient);
    }

    @Bean
    @ConditionalOnMissingBean
    public AlipayClient alipayClient(){
     return  new DefaultAlipayClient(aliPayProperties.getGatewayUrl()
             ,aliPayProperties.getAppId(),aliPayProperties.getPrivateKey()
             ,aliPayProperties.getFormat(),aliPayProperties.getCharset()
             ,aliPayProperties.getPublicKey(),aliPayProperties.getSignType());
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
