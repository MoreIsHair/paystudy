package com.yy.common.util;

import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.util.StrUtil;

import java.lang.reflect.Method;

/**
 * @author YY
 * @date 2019/12/4
 * @description
 */
public class MethodFilter implements Filter<Method> {
    public static final String GET_PRE = "get";
    public static final String SET_PRE = "set";
    public static final String GET_CLASS_METHOD_NAME = "getClass";

    @Override
    public boolean accept(Method method) {
        String name = method.getName();
        Console.log("MethodFilter 中 methodName is {}", name);
        if (GET_CLASS_METHOD_NAME.equals(name)) {
            Console.log("过滤掉getClass方法");
            return false;
        }
        return StrUtil.containsIgnoreCase(name, SET_PRE);
    }
}
