package com.tlcsdm.gen.factory;

import com.tlcsdm.gen.enums.DataBaseType;
import com.tlcsdm.gen.service.AbstractTableToProcedure;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据库表生成存储过程实现类工厂
 *
 * @author: TangLiang
 * @date: 2021/9/10 14:04
 * @since: 1.0
 */
public class AbstractTableToProcedureFactory {

	private static Map<DataBaseType, AbstractTableToProcedure> strategyMap = new ConcurrentHashMap<>();

	public static AbstractTableToProcedure getTableToProcedure(DataBaseType name) {
		return strategyMap.get(name);
	}

	public static void register(DataBaseType name, AbstractTableToProcedure handler) {
		if (Objects.isNull(name) || null == handler) {
			return;
		}
		strategyMap.put(name, handler);
	}

}
