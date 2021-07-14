package com.newangels.gen.factory;

import com.newangels.gen.enums.DataBaseType;
import com.newangels.gen.service.DataBaseProcedureService;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据库存储过程相关工厂
 *
 * @author: TangLiang
 * @date: 2021/6/19 8:57
 * @since: 1.0
 */
public class DataBaseProcedureFactory {
    private static Map<DataBaseType, DataBaseProcedureService> strategyMap = new ConcurrentHashMap<>();

    public static DataBaseProcedureService getDataBaseProcedure(DataBaseType name) {
        return strategyMap.get(name);
    }

    public static void register(DataBaseType name, DataBaseProcedureService handler) {
        if (Objects.isNull(name) || null == handler) {
            return;
        }
        strategyMap.put(name, handler);
    }
}
