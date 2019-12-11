package com.yy.common.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author YY
 * @date 2019/12/4
 * @description xml 与对象，Map 之间的转换工具类
 */
public class XMLUtils {
    /**
     * xml 根元素默认标签
     */
    public final static String XML_ROOT_NAME_DEFAULT = "xml";

    /**
     * xml字符串转map
     */
    public static Map xml2Map(String xmlString) {
        return XmlUtil.xmlToMap(xmlString);
    }

    /**
     * xml转对象
     */
    public static <T> T xml2Object(String xmlString, Class<T> clazz) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Map<String, Object> map = XMLUtils.xml2Map(xmlString);
        T t = clazz.newInstance();
        Method[] methods = ReflectUtil.getMethods(clazz, new MethodFilter());
        for (Method method : methods) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            Console.log(method.getName());
            method.invoke(t
                    , Convert.convert(parameterTypes[0]
                            , map.get(StrUtil.removePreAndLowerFirst(method.getName()
                                    , MethodFilter.SET_PRE))));
        }
        return t;
    }

    /**
     * 对象转xml字符串
     * XML命名规则：
     * 1.名称的开头必须是字母或下划线“_”开头
     * 2.标记名称中不能有空格
     * 3.名称的字符串只能包含字母，数字，“_”，“-”，“.”；
     * @param rootName 根元素标签
     */
    public static <T> String object2XMLString(T t, String rootName) {
        Map convert = Convert.convert(new TypeReference<Map<String, Object>>() {
        }, t);
        return XmlUtil.mapToXmlStr(convert,
                StrUtil.isNotBlank(rootName) ? rootName : XML_ROOT_NAME_DEFAULT);
    }

}
