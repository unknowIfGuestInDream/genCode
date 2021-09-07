package com.newangels.gen.service;

import com.newangels.gen.util.DataSourceUtil;
import com.newangels.gen.util.FreeMarkerUtil;
import freemarker.template.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.List;
import java.util.Map;

/**
 * 存储过程代码生成模板抽象类
 *
 * @author: TangLiang
 * @date: 2021/9/7 21:40
 * @since: 1.0
 */
public abstract class AbstractGenProcedureModel {

    /**
     * 获取ftl包下各自规范的包名
     */
    protected abstract String getFtlPackageName();

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
     */
    public abstract Map<String, Object> genCode(String moduleName, String packageName, String userName, List<String> procedureNameList, NameConventService nameConvent, DataBaseProcedureService dbProcedure, DataSourceUtil dataSourceUtil, String author);

    /**
     * 获取模板代码
     *
     * @param configuration ftl模板引擎配置
     * @param objectMap     模板参数
     * @param fileName      模板文件名
     */
    protected String getFtlModel(Configuration configuration, Map<String, Object> objectMap, String fileName) {
        return FreeMarkerUtil.getTemplateContent(configuration, objectMap, "genProcedureModel/" + getFtlPackageName() + "/" + fileName);
    }

    /**
     * 获取模板代码
     *
     * @param freeMarkerConfigurer ftl模板引擎配置
     * @param objectMap            模板参数
     * @param fileName             模板文件名
     */
    protected String getFtlModel(FreeMarkerConfigurer freeMarkerConfigurer, Map<String, Object> objectMap, String fileName) {
        return getFtlModel(freeMarkerConfigurer.getConfiguration(), objectMap, "genProcedureModel/" + getFtlPackageName() + "/" + fileName);
    }

    /**
     * 获取controller层代码
     */
    protected String getController(FreeMarkerConfigurer freeMarkerConfigurer, Map<String, Object> objectMap) {
        return getFtlModel(freeMarkerConfigurer, objectMap, "controller.ftl");
    }

    /**
     * 获取controller层代码
     */
    protected String getController(Configuration configuration, Map<String, Object> objectMap) {
        return getFtlModel(configuration, objectMap, "controller.ftl");
    }

    /**
     * 获取service层代码
     */
    protected String getService(FreeMarkerConfigurer freeMarkerConfigurer, Map<String, Object> objectMap) {
        return getFtlModel(freeMarkerConfigurer, objectMap, "service.ftl");
    }

    /**
     * 获取service层代码
     */
    protected String getService(Configuration configuration, Map<String, Object> objectMap) {
        return getFtlModel(configuration, objectMap, "service.ftl");
    }

    /**
     * 获取serviceImpl层代码
     */
    protected String getServiceImpl(FreeMarkerConfigurer freeMarkerConfigurer, Map<String, Object> objectMap) {
        return getFtlModel(freeMarkerConfigurer, objectMap, "serviceImpl.ftl");
    }

    /**
     * 获取service层代码
     */
    protected String getServiceImpl(Configuration configuration, Map<String, Object> objectMap) {
        return getFtlModel(configuration, objectMap, "serviceImpl.ftl");
    }

    /**
     * 获取repository层代码
     */
    protected String getRepository(FreeMarkerConfigurer freeMarkerConfigurer, Map<String, Object> objectMap) {
        return getFtlModel(freeMarkerConfigurer, objectMap, "repository.ftl");
    }

    /**
     * 获取repository层代码
     */
    protected String getRepository(Configuration configuration, Map<String, Object> objectMap) {
        return getFtlModel(configuration, objectMap, "repository.ftl");
    }

}
