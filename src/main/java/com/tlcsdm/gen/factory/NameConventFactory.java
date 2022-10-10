package com.tlcsdm.gen.factory;

import com.tlcsdm.gen.enums.NameConventType;
import com.tlcsdm.gen.service.NameConventService;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 命名规范工厂
 *
 * @author: TangLiang
 * @date: 2021/6/21 10:28
 * @since: 1.0
 */
public class NameConventFactory {

	private static Map<NameConventType, NameConventService> strategyMap = new ConcurrentHashMap<>();

	public static NameConventService getNameConvent(NameConventType name) {
		return strategyMap.get(name);
	}

	public static void register(NameConventType name, NameConventService handler) {
		if (Objects.isNull(name) || null == handler) {
			return;
		}
		strategyMap.put(name, handler);
	}

}
