package com.newangels.gen.factory;

import com.alibaba.druid.util.StringUtils;
import com.newangels.gen.util.DataSourceUtil;
import com.newangels.gen.util.dataSource.DruidDataSourceUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据库连接池工厂
 * url+name作为主键
 *
 * @author: TangLiang
 * @date: 2021/6/19 8:36
 * @since: 1.0
 */
public class DataSourceUtilFactory {
    private static Map<String, DataSourceUtil> strategyMap = new ConcurrentHashMap<>();

    public static DataSourceUtil getDataSourceUtil(String name) {
        return strategyMap.get(name);
    }

    public static DataSourceUtil getDataSourceUtil(String url, String driver, String userName, String password) {
        DataSourceUtil dataSourceUtil = DataSourceUtilFactory.getDataSourceUtil(url + userName);
        if (dataSourceUtil == null) {
            try {
                dataSourceUtil = new DruidDataSourceUtil(driver, url, userName, password);
                register(url + userName, dataSourceUtil);
            } catch (Exception e) {
                e.printStackTrace();
                remove(url + userName);
            }
        }
        return dataSourceUtil;
    }

    public static void register(String name, DataSourceUtil handler) {
        if (StringUtils.isEmpty(name) || null == handler) {
            return;
        }
        strategyMap.put(name, handler);
    }

    public static void remove(String name) {
        if (StringUtils.isEmpty(name) || !strategyMap.containsKey(name)) {
            return;
        }
        DataSourceUtil dataSourceUtil = strategyMap.remove(name);
        if (dataSourceUtil != null) {
            dataSourceUtil.close();
        }
    }

    public static void removeAll() {
        strategyMap.values().forEach(DataSourceUtil::close);
        strategyMap.clear();
    }

}
