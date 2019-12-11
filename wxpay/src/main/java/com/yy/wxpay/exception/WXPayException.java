package com.yy.wxpay.exception;

import com.yy.common.constant.enums.ResultEnum;
import com.yy.common.exception.BasePayException;

/**
 * @author YY
 * @date 2019/12/4
 * @description
 */
public class WXPayException extends BasePayException {

    public WXPayException(ResultEnum resultEnum) {
        super(resultEnum);
        this.resultEnum = resultEnum;
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    public WXPayException(String msg) {
        this.msg = msg;
    }
}
