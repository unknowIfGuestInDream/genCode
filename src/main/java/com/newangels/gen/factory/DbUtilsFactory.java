package com.newangels.gen.factory;

import com.alibaba.druid.util.StringUtils;
import com.newangels.gen.util.DBUtil;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * DBUtil工厂
 *
 * @author: TangLiang
 * @date: 2021/6/19 8:36
 * @since: 1.0
 */
public class DbUtilsFactory {
    private static Map<String, DBUtil> strategyMap = new WeakHashMap<>();

    public static DBUtil getDbUtil(String name) {
        return strategyMap.get(name);
    }

    public static void register(String name, DBUtil handler) {
        if (StringUtils.isEmpty(name) || null == handler) {
            return;
        }
        strategyMap.put(name, handler);
    }

    public static void remove(String name) {
        if (StringUtils.isEmpty(name) || !strategyMap.containsKey("name")) {
            return;
        }
        strategyMap.remove(name);
    }

    public static void removeAll() {
        strategyMap.clear();
    }

}
