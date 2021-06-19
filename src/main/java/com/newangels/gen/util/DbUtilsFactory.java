package com.newangels.gen.util;

import com.alibaba.druid.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DBUtil工厂
 *
 * @author: TangLiang
 * @date: 2021/6/19 8:36
 * @since: 1.0
 */
public class DbUtilsFactory {
    private static Map<String, DBUtil> strategyMap = new ConcurrentHashMap<>();

    public static DBUtil getDbUtil(String name) {
        return strategyMap.get(name);
    }

    public static void register(String name, DBUtil handler) {
        if (StringUtils.isEmpty(name) || null == handler) {
            return;
        }
        strategyMap.put(name, handler);
    }
}
