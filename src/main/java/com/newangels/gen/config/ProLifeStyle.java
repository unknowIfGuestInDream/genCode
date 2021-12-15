package com.newangels.gen.config;

import com.newangels.gen.factory.DataSourceUtilFactory;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

/**
 * 项目关闭时清空DataSourceUtilFactory
 *
 * @author: TangLiang
 * @date: 2021/12/15 13:52
 * @since: 1.0
 */
@Component
public class ProLifeStyle implements SmartLifecycle {
    private volatile boolean running;

    @Override
    public void start() {
        this.running = true;
    }

    @Override
    public void stop() {
        DataSourceUtilFactory.removeAll();
        this.running = false;
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }
}
