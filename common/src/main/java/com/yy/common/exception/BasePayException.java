package com.yy.common.exception;


import com.yy.common.constant.enums.ResultEnum;

/**
 * @author YY
 * @date 2019/12/2
 * @description
 */
public class BasePayException extends RuntimeException {

    private static final long serialVersionUID = -3653207096440030721L;
    protected ResultEnum resultEnum;
    protected String msg;
    protected Integer code;

    public BasePayException() {
        super();
    }

    public BasePayException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BasePayException(String msg, Throwable throwable) {
        super(msg, throwable);
        this.msg = msg;
    }

    public BasePayException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.resultEnum = resultEnum;
        this.msg = resultEnum.getMsg();
        this.code = resultEnum.getCode();
    }


    public ResultEnum getResultEnum() {
        return resultEnum;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }
}
