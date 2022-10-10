package com.tlcsdm.gen.config;

import com.tlcsdm.gen.factory.DataSourceUtilFactory;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import static com.tlcsdm.gen.base.CacheManage.CACHE_MAP;

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
		CACHE_MAP.forEach((s, cache) -> cache.clear());
		DataSourceUtilFactory.removeAll();
		this.running = false;
	}

	@Override
	public boolean isRunning() {
		return this.running;
	}

}
