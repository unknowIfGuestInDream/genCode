package com.newangels.gen.factory;

import com.newangels.gen.enums.DataBaseType;
import com.newangels.gen.service.AbstractTableToCode;

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
public class AbstractTableToCodeFactory {
    private static Map<DataBaseType, AbstractTableToCode> strategyMap = new ConcurrentHashMap<>();

    public static AbstractTableToCode getTableToProcedure(DataBaseType name) {
        return strategyMap.get(name);
    }

    public static void register(DataBaseType name, AbstractTableToCode handler) {
        if (Objects.isNull(name) || null == handler) {
            return;
        }
        strategyMap.put(name, handler);
    }
}
