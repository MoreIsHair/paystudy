package com.yy.common.listener;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import com.yy.common.event.AbstractPayNotifyEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author YY
 * @date 2019/12/9
 * @description
 */
public class AbstractPayNotifyListener implements ApplicationListener<AbstractPayNotifyEvent> {
    @Override
    public void onApplicationEvent(AbstractPayNotifyEvent event) {
        Console.log("监听到来自{}的回调处理", event.getFlag().name());
        Console.log(StrUtil.toString(event.getNotifyMap()));
    }
}
