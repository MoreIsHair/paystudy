package com.yy.alipay.service;

import com.yy.alipay.config.AliPayPropertiesConfig;
import com.yy.common.service.PayService;

/**
 * @author YY
 * @date 2019/12/10
 * @description
 */
public interface AliPayService extends PayService {

    @Override
     AliPayPropertiesConfig getConfig();

}
