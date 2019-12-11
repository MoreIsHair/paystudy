package com.yy.common.constant.enums;

/**
 * @author YY
 * @date 2019/12/4
 * @description
 */

public enum ResultEnum {
    /**
     * 操作成功
     */
    SUCCESS(10000, "操作成功"),
    /**
     * 操作失败
     */
    FAIL(9999, "操作失败"),
    /**
     * token 无效
     */
    SHIRO_AUTHENTICATION_INVALID(7777, "TOKEN无效"),
    /**
     * 用户名或密码错误
     */
    SHIRO_AUTHENTICATION_WRONG(7777, "用户名或密码错误"),
    /**
     * 权限不够
     */
    SHIRO_AUTHENTICATION_NO_PRIVILEGE(6666, "无权限"),

    /**
     * 未知错误（不是自定义的错误）
     */
    INTERNAL_SERVER_ERROR(8888, "未知错误");
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
