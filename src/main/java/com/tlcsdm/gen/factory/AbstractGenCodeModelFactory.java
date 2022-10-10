package com.tlcsdm.gen.factory;

import com.tlcsdm.gen.enums.GenCodeModelType;
import com.tlcsdm.gen.service.AbstractGenCodeModel;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据库表生成后台代码实现类工厂
 *
 * @author: TangLiang
 * @date: 2021/11/7 14:04
 * @since: 1.0
 */
public class AbstractGenCodeModelFactory {

	private static Map<GenCodeModelType, AbstractGenCodeModel> strategyMap = new ConcurrentHashMap<>();

	public static AbstractGenCodeModel getGenCodeModel(GenCodeModelType name) {
		return strategyMap.get(name);
	}

	public static void register(GenCodeModelType name, AbstractGenCodeModel handler) {
		if (Objects.isNull(name) || null == handler) {
			return;
		}
		strategyMap.put(name, handler);
	}

}
