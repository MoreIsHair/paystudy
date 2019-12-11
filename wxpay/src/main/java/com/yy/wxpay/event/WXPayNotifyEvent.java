package com.yy.wxpay.event;

import com.yy.common.event.AbstractPayNotifyEvent;
import com.yy.common.constant.enums.PlatformFlag;

import java.util.Map;

/**
 * @author YY
 * @date 2019/12/9
 * @description
 */
public class WXPayNotifyEvent extends AbstractPayNotifyEvent {
    private static final long serialVersionUID = 1645901454310028242L;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public WXPayNotifyEvent(Object source, Map<String, String> notifyMap) {
        super(source, notifyMap, PlatformFlag.WXPAY);
    }
}
