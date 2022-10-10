package com.tlcsdm.gen.factory;

import com.tlcsdm.gen.enums.DataBaseType;
import com.tlcsdm.gen.service.DataBaseTableService;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据库表相关工厂
 *
 * @author: TangLiang
 * @date: 2021/6/19 8:57
 * @since: 1.0
 */
public class DataBaseTableFactory {

	private static Map<DataBaseType, DataBaseTableService> strategyMap = new ConcurrentHashMap<>();

	public static DataBaseTableService getDataBaseTable(DataBaseType name) {
		return strategyMap.get(name);
	}

	public static void register(DataBaseType name, DataBaseTableService handler) {
		if (Objects.isNull(name) || null == handler) {
			return;
		}
		strategyMap.put(name, handler);
	}

}
