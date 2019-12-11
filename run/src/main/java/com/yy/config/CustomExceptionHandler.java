package com.yy.config;

import com.yy.common.constant.enums.ResultEnum;
import com.yy.common.exception.BasePayException;
import com.yy.common.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

import static com.yy.common.constant.enums.ResultEnum.INTERNAL_SERVER_ERROR;

/**
 * @author YY
 * @date 2019/12/4
 * @description
 */
@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(BasePayException.class)
    public ResponseEntity<Result> wxPayExceptionHandler(BasePayException e) {
        log.error(e.getResultEnum().getMsg());
        return ResponseEntity.ok(Result.fail(e.getResultEnum()));
    }

    @ExceptionHandler(value = ShiroException.class)
    public ResponseEntity<Result> authenticationExceptionHandler(ShiroException e) {
        log.error("未知异常！原因是:", e);
        ResultEnum resultEnum = Arrays.stream(ResultEnum.values()).filter(c -> c.getCode().toString() == e.getMessage()).findFirst().get();
        return ResponseEntity.ok(Result.fail(resultEnum));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Result> exceptionHandler(Exception e) {
        log.error("未知异常！原因是:", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.fail(INTERNAL_SERVER_ERROR));
    }

}
