package com.newangels.gen.service;

import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * 命名规范
 *
 * @author: TangLiang
 * @date: 2021/6/21 10:28
 * @since: 1.0
 */
public interface NameConventService extends InitializingBean {

    /**
     * 根据存储过程名称获取方法前缀名称
     *
     * @param procedureName 存储过程名称
     */
    String getName(String procedureName);

    /**
     * 结果集参数key值
     *
     * @param name 名称
     */
    String getResultName(String name);

    /**
     * 获取方法名集合
     *
     * @param moduleName        模块名
     * @param procedureNameList 存储过程名称集合
     */
    List<String> getMethodNames(String moduleName, List<String> procedureNameList);

    /**
     * 排序方法
     *
     * @param procedureNameList 存储过程集合
     */
    void sortMethod(List<String> procedureNameList);

    /**
     * 获得存储过程名称
     *
     * @param tableName 表名
     * @param procType  存储过程类别 (1 get加载;2 select查询;3 insert新增;4 update修改;5 delete删除;6 save保存;其它)
     */
    String getProcName(String tableName, int procType);

    /**
     * 获取存储过程出参名称
     *
     * @param name 名称 result 结果集，message 信息， total 结果集数量
     */
    String getProcOutParamName(String name);

    /**
     * 根据表字段跟字段类型生成入参名称
     *
     * @param paramName 字段名
     * @param paramType 字段类型
     */
    String genProcInParamName(String paramName, String paramType);

    /**
     * 生成查询过程入参
     *
     * @param param     字段
     * @param paramType 字段类型
     * @param paramDesc 字段描述
     * @param selType   查询类型(0精确/1模糊/2区间查询)
     */
    String genSelProcInParam(String param, String paramType, String paramDesc, Integer selType);

    /**
     * 生成查询过程的sql的where条件
     *
     * @param param     字段
     * @param paramType 字段类型
     * @param selType   查询类型(0精确/1模糊/2区间查询)
     */
    String genSelProcSqlWhere(String param, String paramType, Integer selType);
}
