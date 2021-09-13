package com.newangels.gen.factory;

import com.newangels.gen.util.dataSource.DataSourceUtil;
import com.newangels.gen.util.dataSource.DataSourceUtilTypes;
import com.newangels.gen.util.dataSource.DruidDataSourceUtil;
import com.newangels.gen.util.dataSource.HikariDataSourceUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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

    /**
     * 获取strategyMap中所有DataSourceUtil的连接池信息
     */
    public static List<String> getDataSourceInfoList() {
        return strategyMap.values().stream()
                .map(DataSourceUtil::getDataSourceInfo)
                .collect(Collectors.toList());
    }

    /**
     * 获取DataSourceUtil连接池工具类
     */
    public static DataSourceUtil getDataSourceUtil(String key) {
        return strategyMap.get(key);
    }

    /**
     * 获取DataSourceUtil连接池工具类，为null则初始化
     */
    public static DataSourceUtil getDataSourceUtil(String url, String driver, String userName, String password) {
        return getDataSourceUtil(url, driver, userName, password, DataSourceUtilTypes.DRUID);
    }

    /**
     * 获取DataSourceUtil连接池工具类，为null则初始化
     */
    public static DataSourceUtil getDataSourceUtil(String url, String driver, String userName, String password, int dataSourceUtilType) {
        return Optional.ofNullable(DataSourceUtilFactory.getDataSourceUtil(url + userName))
                .orElseGet(() -> createDataSourceUtil(url, driver, userName, password, dataSourceUtilType));
    }

    /**
     * 创建DataSourceUtil并注册到strategyMap中
     */
    private static DataSourceUtil createDataSourceUtil(String url, String driver, String userName, String password, int dataSourceUtilType) {
        DataSourceUtil dataSourceUtil = null;
        try {
            switch (dataSourceUtilType) {
                case DataSourceUtilTypes.HIKARI:
                    dataSourceUtil = new HikariDataSourceUtil(driver, url, userName, password);
                    break;
                case DataSourceUtilTypes.DRUID:
                default:
                    dataSourceUtil = new DruidDataSourceUtil(driver, url, userName, password);
                    break;
            }
            register(url + userName, dataSourceUtil);
        } catch (Exception e) {
            e.printStackTrace();
            remove(url + userName);
        }
        return dataSourceUtil;
    }

    /**
     * 将DataSourceUtil注册到strategyMap中
     */
    public static void register(String name, DataSourceUtil handler) {
        if (StringUtils.isEmpty(name) || null == handler) {
            return;
        }
        strategyMap.put(name, handler);
    }

    /**
     * 删除strategyMap指定值
     */
    public static void remove(String key) {
        if (StringUtils.isEmpty(key)) {
            return;
        }
        Optional.ofNullable(strategyMap.remove(key)).ifPresent(DataSourceUtil::close);
    }

    /**
     * 删除strategyMap所有值
     */
    public static void removeAll() {
        strategyMap.values().forEach(DataSourceUtil::close);
        strategyMap.clear();
    }

}
