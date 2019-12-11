package com.yy.common.util;

import cn.hutool.core.date.DateUtil;
import com.yy.common.constant.enums.ResultEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author YY
 * @date 2019/12/4
 * @description 统一的返回结果
 */

@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -6289127653431238006L;
    private Integer code;
    private String msg;
    private T data;
    private Long timestamp = DateUtil.current(true);

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    public Result(ResultEnum resultEnum, T t) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
        this.data = t;
    }

    public static Result<Void> success() {
        return new Result<>(ResultEnum.SUCCESS);
    }

    public static <T> Result<T> success(T t) {
        return new Result<>(ResultEnum.SUCCESS, t);
    }

    public static Result<Void> fail() {
        return new Result<>(ResultEnum.FAIL);
    }

    public static Result<Void> fail(ResultEnum resultEnum) {
        return new Result<>(resultEnum);
    }

    public static Result<Void> fail(String msg) {
        return new Result<>(ResultEnum.FAIL.getCode(), msg);
    }
}
