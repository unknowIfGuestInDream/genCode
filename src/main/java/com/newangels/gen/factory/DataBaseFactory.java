package com.newangels.gen.factory;

import com.newangels.gen.service.DataBaseProcedureService;
import com.newangels.gen.util.DataBaseType;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据库工厂
 *
 * @author: TangLiang
 * @date: 2021/6/19 8:57
 * @since: 1.0
 */
public class DataBaseFactory {
    private static Map<DataBaseType, DataBaseProcedureService> strategyMap = new ConcurrentHashMap<>();

    public static DataBaseProcedureService getDataBaseProcedure(DataBaseType name) {
        return strategyMap.get(name);
    }

    public static void register(DataBaseType name, DataBaseProcedureService handler) {
        if (StringUtils.isEmpty(name) || null == handler) {
            return;
        }
        strategyMap.put(name, handler);
    }
}
