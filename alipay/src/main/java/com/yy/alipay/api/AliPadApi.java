package com.yy.alipay.api;

import com.yy.alipay.config.AliPayPropertiesConfig;
import com.yy.alipay.service.AliPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author YY
 * @date 2019/12/2
 * @description
 */
@Api(tags = "支付宝支付API")
@RestController
@RequestMapping("/alipay")
public class AliPadApi {

    @Autowired
    AliPayService aliPayService;


    @PostMapping("/notify")
    @ApiOperation("支付宝回调接口")
    public String notify(HttpServletRequest request) {
        AliPayPropertiesConfig config = aliPayService.getConfig();
        return null;

    }

    /**
     * 最终通过调用 AlipayClient 的 pageExecute 方法返回支付页面
     */
    @PostMapping("/toPay")
    @ApiOperation("预支付")
    public String toPay(HttpServletRequest request) {

        return null;
    }
}
