package com.yy.common.config;

import com.yy.common.constant.enums.SignType;
import lombok.Setter;

/**
 * @author YY
 * @date 2019/12/10
 * @description
 */
@Setter
public abstract class BasePayPropertiesConfig {

    /**
     * 应用ID,APPID
     */
    protected  String appId;

    /**
     * 回调地址
     */
    protected String notifyUrl;

    /**
     * 签名方式
     * 微信有两种HMAC_SHA256 和MD5
     * 支付宝 RSA2
     */
    protected SignType signType;


    public   String getAppId(){
        return appId;
    }

    public  String getNotifyUrl(){
        return notifyUrl;
    }

    public  SignType getSignType(){
        return signType;
    }

    abstract public String getPayBaseUrl();

}
