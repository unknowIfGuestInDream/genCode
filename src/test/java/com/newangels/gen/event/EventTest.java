package com.newangels.gen.event;

import org.apache.commons.lang3.event.EventListenerSupport;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: TangLiang
 * @date: 2021/11/30 9:12
 * @since: 1.0
 */
@SpringBootTest
public class EventTest {

    @Test
    public void enum1() {
        EventListenerSupport<IListeners> listenersEvent = new EventListenerSupport<>(IListeners.class);
        // 注册监听
        listenersEvent.addListener(new Courier("快递员小马"));
        listenersEvent.addListener(new User("用户小刘"));
        listenersEvent.fire().receive();
    }
}
