package com.yy.common.service.impl;

import com.yy.common.config.BasePayPropertiesConfig;
import com.yy.common.service.PayService;

/**
 * @author YY
 * @date 2019/12/10
 * @description
 */
public abstract class BasePayServiceImpl implements PayService {

    protected BasePayPropertiesConfig config;

    @Override
    public  BasePayPropertiesConfig getConfig() {
        return config;
    }

    @Override
    public String getPayBaseUrl() {
        return config.getPayBaseUrl();
    }

}
