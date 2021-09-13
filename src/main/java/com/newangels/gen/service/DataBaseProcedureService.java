package com.newangels.gen.service;

import com.newangels.gen.util.dataSource.DataSourceUtil;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;
import java.util.Map;

/**
 * 数据库存储过程
 *
 * @author: TangLiang
 * @date: 2021/6/19 8:42
 * @since: 1.0
 */
public interface DataBaseProcedureService extends InitializingBean {

    /**
     * 查询所有过程
     *
     * @param name 过程名
     */
    String selectProcedures(String name);

    /**
     * 查询过程
     *
     * @param name           过程名
     * @param dataSourceUtil 数据库连接工具类
     */
    List<Map<String, Object>> selectProcedures(String name, DataSourceUtil dataSourceUtil);

    /**
     * 查询过程信息
     *
     * @param name 过程名
     * @return 过程信息
     */
    String loadProcedure(String name);

    /**
     * 获取存储过程完整信息
     *
     * @param name           过程名
     * @param dataSourceUtil 数据库连接工具类
     */
    String loadProcedure(String name, DataSourceUtil dataSourceUtil);

    /**
     * 查询过程参数
     *
     * @param owner      账户名
     * @param objectName 过程名
     * @return 过程参数
     */
    String selectArguments(String owner, String objectName);

    /**
     * 数据库对java类型映射
     *
     * @param type 数据库参数类型
     */
    String getJavaClass(String type);

    /**
     * 返回存储过程出参信息
     *
     * @param type 数据库参数类型
     */
    String getRepositoryOutType(String type);

    /**
     * 返回存储过程结果集处理代码
     *
     * @param type 数据库参数类型
     */
    String getRepositoryOutTypeCode(String type);
}
