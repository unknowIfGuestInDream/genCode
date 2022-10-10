package com.tlcsdm.gen.factory;

import com.tlcsdm.gen.enums.GenProcedureModelType;
import com.tlcsdm.gen.service.AbstractGenProcedureModel;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 存储过程代码生成模板工厂
 *
 * @author: TangLiang
 * @date: 2021/6/20 10:11
 * @since: 1.0
 */
public class AbstractGenProcedureModelFactory {

	private static Map<GenProcedureModelType, AbstractGenProcedureModel> strategyMap = new ConcurrentHashMap<>();

	public static AbstractGenProcedureModel getGenProcedureModel(GenProcedureModelType name) {
		return strategyMap.get(name);
	}

	public static void register(GenProcedureModelType name, AbstractGenProcedureModel handler) {
		if (Objects.isNull(name) || null == handler) {
			return;
		}
		strategyMap.put(name, handler);
	}

}
