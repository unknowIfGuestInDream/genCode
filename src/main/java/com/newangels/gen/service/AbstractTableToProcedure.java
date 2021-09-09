package com.newangels.gen.service;

import com.newangels.gen.util.FreeMarkerUtil;
import freemarker.template.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.List;
import java.util.Map;

/**
 * 表生成存储过程
 *
 * @author: TangLiang
 * @date: 2021/9/9 21:40
 * @since: 1.0
 */
public abstract class AbstractTableToProcedure {

    /**
     * 获取ftl包下各自规范的包名
     */
    protected abstract String getFtlPackageName();

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
        return getFtlModel(freeMarkerConfigurer.getConfiguration(), objectMap, fileName);
    }

    /**
     * 生成加载过程
     *
     * @param tableName    表名
     * @param primarys     主键集合
     * @param primaryTypes 主键数据库类型集合
     * @param primaryDesc  字段描述
     * @param nameConvent  命名规范
     */
    protected abstract String genGetProcedure(String tableName, String tableDesc, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, NameConventService nameConvent);

    /**
     * 生成查询过程(无分页)
     *
     * @param tableName     表名
     * @param selParams     参数
     * @param selParamTypes 参数类型
     * @param selParamDescs 字段描述
     * @param selType       查询类型(0精确/1模糊/2区间查询)
     * @param nameConvent   命名规范
     */
    protected abstract String genSelProcedure(String tableName, String tableDesc, List<String> selParams, List<String> selParamTypes, List<String> selParamDescs, List<Integer> selType, NameConventService nameConvent);

    /**
     * 生成查询过程(有分页)
     *
     * @param tableName     表名
     * @param selParams     参数
     * @param selParamTypes 参数类型
     * @param selParamDescs 字段描述
     * @param selType       查询类型(0精确/1模糊/2区间查询)
     * @param nameConvent   命名规范
     */
    protected abstract String genSelProcedureWithPage(String tableName, String tableDesc, List<String> selParams, List<String> selParamTypes, List<String> selParamDescs, List<Integer> selType, NameConventService nameConvent);

    /**
     * 生成新增过程
     *
     * @param tableName     表名
     * @param insParams     新增过程参数
     * @param insParamTypes 新增过程参数类型
     * @param insParamDescs 新增过程字段描述
     * @param nameConvent   命名规范
     */
    protected abstract String genInsProcedure(String tableName, String tableDesc, List<String> insParams, List<String> insParamTypes, List<String> insParamDescs, NameConventService nameConvent);

    /**
     * 生成修改过程
     *
     * @param tableName     表名
     * @param primarys      主键集合
     * @param primaryTypes  主键数据库类型集合
     * @param primaryDesc   字段描述
     * @param updParams     参数
     * @param updParamTypes 参数类型
     * @param updParamDescs 字段描述
     * @param nameConvent   命名规范
     */
    protected abstract String genUpdProcedure(String tableName, String tableDesc, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, List<String> updParams, List<String> updParamTypes, List<String> updParamDescs, NameConventService nameConvent);

    /**
     * 生成保存过程
     *
     * @param tableName     表名
     * @param insParams     新增过程参数
     * @param insParamTypes 新增过程参数类型
     * @param insParamDescs 新增过程字段描述
     * @param primarys      主键集合
     * @param primaryTypes  主键数据库类型集合
     * @param primaryDesc   字段描述
     * @param updParams     参数
     * @param updParamTypes 参数类型
     * @param updParamDescs 字段描述
     * @param nameConvent   命名规范
     */
    protected abstract String genSaveProcedure(String tableName, String tableDesc, List<String> insParams, List<String> insParamTypes, List<String> insParamDescs, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, List<String> updParams, List<String> updParamTypes, List<String> updParamDescs, NameConventService nameConvent);

    /**
     * 生成删除过程
     *
     * @param tableName    表名
     * @param primarys     主键集合
     * @param primaryTypes 主键数据库类型集合
     * @param primaryDesc  字段描述
     * @param nameConvent  命名规范
     */
    protected abstract String genDelProcedure(String tableName, String tableDesc, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, NameConventService nameConvent);

    /**
     * 根据表生成存储过程
     *
     * @param tableName      表名
     * @param tableDesc      表描述
     * @param params         参数
     * @param paramTypes     参数类型
     * @param paramDescs     字段描述
     * @param priParamIndex  主键列索引
     * @param selParamsIndex 查询列索引
     * @param selType        查询类型(0精确/1模糊/2区间查询)
     * @param insParamIndex  新增列索引
     * @param updParamIndex  修改列索引
     * @param nameConvent    命名规范
     */
    protected abstract Map<String, Object> genProceduresByTable(String tableName, String tableDesc, List<String> params, List<String> paramTypes, List<String> paramDescs, List<Integer> priParamIndex, List<Integer> selParamsIndex, List<Integer> selType, List<Integer> insParamIndex, List<Integer> updParamIndex, NameConventService nameConvent);

}
