package com.newangels.gen.service;

import com.newangels.gen.util.dataSource.DataSourceUtil;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;
import java.util.Map;

/**
 * 存储过程代码生成模板
 *
 * @author: TangLiang
 * @date: 2021/6/20 10:15
 * @since: 1.0
 */
@Deprecated
public interface GenProcedureModelService extends InitializingBean {

    /**
     * 控制层代码生成
     *
     * @param moduleName  模块名
     * @param packageName 包名
     * @param author      作者
     * @param methodCode  方法代码
     */
    String getControllerCode(String moduleName, String packageName, String author, String methodCode);

    /**
     * 接口层代码生成
     *
     * @param moduleName  模块名
     * @param packageName 包名
     * @param author      作者
     * @param methodCode  方法代码
     */
    String getServiceCode(String moduleName, String packageName, String author, String methodCode);

    /**
     * 实现层代码生成
     *
     * @param moduleName  模块名
     * @param packageName 包名
     * @param author      作者
     * @param methodCode  方法代码
     */
    String getServiceImplCode(String moduleName, String packageName, String author, String methodCode);

    /**
     * 数据层代码生成
     *
     * @param moduleName  模块名
     * @param packageName 包名
     * @param author      作者
     * @param methodCode  方法代码
     */
    String getRepositoryCode(String moduleName, String packageName, String author, String methodCode);

    /**
     * 获取请求协议
     *
     * @param procedureName 存储过程名称
     * @param nameConvent   命名规范
     */
    String getMappingType(String procedureName, NameConventService nameConvent);

    /**
     * 根据代码规范和命名规范以及数据库连接生成对应代码
     *
     * @param moduleName        模块名称
     * @param packageName       包名
     * @param userName          用户名
     * @param procedureNameList 存储过程集合
     * @param nameConvent       命名规范
     * @param dbProcedure       代码规范
     * @param dataSourceUtil    数据库连接
     * @param author            作者
     */
    Map<String, Object> genCode(String moduleName, String packageName, String userName, List<String> procedureNameList, NameConventService nameConvent, DataBaseProcedureService dbProcedure, DataSourceUtil dataSourceUtil, String author);
}
