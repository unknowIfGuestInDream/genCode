package com.newangels.gen.factory;

import com.newangels.gen.enums.DataBaseType;
import com.newangels.gen.service.TableToProcedureService;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据库表生成存储过程实现类工厂
 *
 * @author: TangLiang
 * @date: 2021/7/23 9:56
 * @since: 1.0
 */
@Deprecated
public class DataBaseTableToProcFactory {
    private static Map<DataBaseType, TableToProcedureService> strategyMap = new ConcurrentHashMap<>();

    public static TableToProcedureService getTableToProcedure(DataBaseType name) {
        return strategyMap.get(name);
    }

    public static void register(DataBaseType name, TableToProcedureService handler) {
        if (Objects.isNull(name) || null == handler) {
            return;
        }
        strategyMap.put(name, handler);
    }
}
