package com.newangels.gen.service.nameConvent;

/**
 * 表生成过程命名规范
 *
 * @author: TangLiang
 * @date: 2021/9/11 23:32
 * @since: 1.0
 */
public abstract class AbstractTableToProcNameConvent {

    /**
     * 获得存储过程名称
     *
     * @param tableName 表名
     * @param procType  存储过程类别 (1 get加载;2 select查询;3 insert新增;4 update修改;5 delete删除;6 save保存;其它)
     */
    public abstract String getProcName(String tableName, int procType);

    /**
     * 获取存储过程出参名称
     *
     * @param name 名称 result 结果集，message 信息， total 结果集数量
     */
    public abstract String getProcOutParamName(String name);

    /**
     * 根据表字段跟字段类型生成入参名称
     *
     * @param paramName 字段名
     * @param paramType 字段类型
     */
    public abstract String genProcInParamName(String paramName, String paramType);

    /**
     * 生成查询过程入参
     *
     * @param param     字段
     * @param paramType 字段类型
     * @param paramDesc 字段描述
     * @param selType   查询类型(0精确/1模糊/2区间查询)
     */
    public abstract String genSelProcInParam(String param, String paramType, String paramDesc, Integer selType);

    /**
     * 生成查询过程的sql的where条件
     *
     * @param param     字段
     * @param paramType 字段类型
     * @param selType   查询类型(0精确/1模糊/2区间查询)
     */
    public abstract String genSelProcSqlWhere(String param, String paramType, Integer selType);
}
