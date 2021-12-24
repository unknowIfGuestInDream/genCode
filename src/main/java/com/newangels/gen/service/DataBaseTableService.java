package com.newangels.gen.service;

import com.newangels.gen.util.dataSource.DataSourceUtil;
import lombok.NonNull;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;
import java.util.Map;

/**
 * 数据库表相关sql
 *
 * @author: TangLiang
 * @date: 2021/7/14 9:34
 * @since: 1.0
 */
public interface DataBaseTableService extends InitializingBean {

    /**
     * 加载表信息的sql
     *
     * @param name 表名
     */
    String loadTable(@NonNull String name);

    /**
     * 加载表信息结果集
     *
     * @param name           表名
     * @param dataSourceUtil 数据库连接工具类
     */
    Map<String, Object> loadTable(@NonNull String name, @NonNull DataSourceUtil dataSourceUtil);

    /**
     * 查询数据库表信息的sql
     *
     * @param name   表名
     * @param schema 表空间
     */
    String selectTables(String name, String schema);

    /**
     * 查询数据库表信息结果集
     *
     * @param name           表名
     * @param schema         表空间
     * @param dataSourceUtil 数据库连接工具类
     */
    List<Map<String, Object>> selectTables(String name, String schema, DataSourceUtil dataSourceUtil);

    /**
     * 查询数据库某表字段相关的详细信息的sql
     *
     * @param name   表名
     * @param schema 表空间
     */
    String selectTableInfo(@NonNull String name, String schema);

    /**
     * 查询数据库某表字段相关的详细信息的结果集
     *
     * @param name           表名
     * @param schema         表空间
     * @param dataSourceUtil 数据库连接工具类
     */
    List<Map<String, Object>> selectTableInfo(@NonNull String name, String schema, @NonNull DataSourceUtil dataSourceUtil);
}
