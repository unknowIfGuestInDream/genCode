package com.newangels.gen.factory;

import com.newangels.gen.service.GenProcedureModelService;
import com.newangels.gen.util.GenProcedureModelType;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 存储过程代码生成模板工厂
 *
 * @author: TangLiang
 * @date: 2021/6/20 10:11
 * @since: 1.0
 */
public class GenProcedureModelFactory {
    private static Map<GenProcedureModelType, GenProcedureModelService> strategyMap = new ConcurrentHashMap<>();

    public static GenProcedureModelService getGenProcedureModel(GenProcedureModelType name) {
        return strategyMap.get(name);
    }

    public static void register(GenProcedureModelType name, GenProcedureModelService handler) {
        if (StringUtils.isEmpty(name) || null == handler) {
            return;
        }
        strategyMap.put(name, handler);
    }
}