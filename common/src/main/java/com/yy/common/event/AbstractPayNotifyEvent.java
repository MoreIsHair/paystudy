package com.yy.common.event;

import com.yy.common.constant.enums.PlatformFlag;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * @author YY
 * @date 2019/12/9
 * @description 抽象支付成功后回调事件
 */
@Getter
public abstract class AbstractPayNotifyEvent extends ApplicationEvent {

    private static final long serialVersionUID = -604927662823181016L;

    private Map<String, String> notifyMap;
    private PlatformFlag flag;


    /**
     * 在自定义事件的构造方法中除了第一个source参数，其他参数都可以去自定义
     * 可以根据项目实际情况进行监听传参
     */
    public AbstractPayNotifyEvent(Object source, Map<String, String> notifyMap, PlatformFlag flag) {
        super(source);
        this.notifyMap = notifyMap;
        this.flag = flag;
    }
}