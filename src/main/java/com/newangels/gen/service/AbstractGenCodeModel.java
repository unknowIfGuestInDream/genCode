package com.newangels.gen.service;

import com.newangels.gen.base.BaseUtils;
import com.newangels.gen.enums.DataBaseType;
import com.newangels.gen.enums.JavaClass;
import com.newangels.gen.exception.UnSupportedDataSourceException;
import com.newangels.gen.util.template.AbstractFreeMarkerTemplate;
import com.newangels.gen.util.template.FreeMarkerUtil;
import freemarker.template.Configuration;
import org.springframework.beans.factory.InitializingBean;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 表生成后台代码
 *
 * @author: TangLiang
 * @date: 2021/11/7 21:40
 * @since: 1.0
 */
public abstract class AbstractGenCodeModel extends AbstractFreeMarkerTemplate implements InitializingBean {

    //service注释所用的StringJoiner间隔
    protected String noteJoiner = "\n     * ";
    //DateTimeFormat注解pattern参数
    protected String dateTimeFormat = "yyyy-MM-dd";

    @Override
    protected String getRootPackageName() {
        return "genCodeModel";
    }

    /**
     * 获取当前数据源的类型对应的包名
     */
    protected String getDbTypePackageName(String driver) {
        DataBaseType dbt = DataBaseType.fromTypeName(driver);
        if (dbt == DataBaseType.MYSQL8) {
            return "mysql";
        } else if (dbt == DataBaseType.UNKNOW) {
            throw new UnSupportedDataSourceException("不支持当前数据库代码生成");
        } else {
            return dbt.toString().toLowerCase();
        }
    }

    /**
     * 处理代码基本信息
     *
     * @param tableName   表名
     * @param tableDesc   表描述信息
     * @param moduleName  模块名称
     * @param moduleDesc  模块描述
     * @param packageName 包名
     * @param author      作者
     * @param hasDelBatch 是否包含批量删除
     * @param hasExport   是否包含导出接口
     * @param hasView     是否包含详情查看
     * @param primarys    主键参数集合
     * @param objectMap   代码模版值
     */
    protected void dealCommonCode(String tableName, String tableDesc, String moduleName, String moduleDesc, String packageName, String author, boolean hasDelBatch, boolean hasExport, boolean hasView, List<String> primarys, Map<String, Object> objectMap) {
        objectMap.put("tableName", tableName);
        objectMap.put("tableDesc", tableDesc);
        objectMap.put("module", moduleName);
        objectMap.put("moduleDesc", moduleDesc);
        objectMap.put("package", packageName);
        objectMap.put("author", author);
        objectMap.put("hasDelBatch", hasDelBatch);
        objectMap.put("hasExport", hasExport);
        objectMap.put("hasView", hasView);
        objectMap.put("primarys", primarys);
        objectMap.put("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
    }

    /**
     * 生成加载代码值
     *
     * @param primarys         主键集合
     * @param primaryDesc      字段描述
     * @param primaryJavaClass 参数对应类对象
     * @param objectMap        代码模版值
     */
    protected void dealLoadCode(List<String> primarys, List<String> primaryDesc, List<String> primaryJavaClass, Map<String, Object> objectMap) {
        StringJoiner loadInParams = new StringJoiner(", ");
        StringJoiner loadSqlParams = new StringJoiner(", ");
        StringJoiner loadWhere = new StringJoiner(" and ");
        StringJoiner loadNote = new StringJoiner(noteJoiner);
        for (int i = 0, length = primarys.size(); i < length; i++) {
            loadInParams.add(primaryJavaClass.get(i) + " " + primarys.get(i));
            loadSqlParams.add(primarys.get(i));
            loadWhere.add(primarys.get(i) + " = ?");
            loadNote.add("@param " + primarys.get(i) + " " + primaryDesc.get(i));
        }
        objectMap.put("loadInParams", loadInParams.toString());
        objectMap.put("loadSqlParams", loadSqlParams.toString());
        objectMap.put("loadNote", loadNote.toString());
        objectMap.put("loadWhere", loadWhere.toString());
        //加载跟删除入参相同
        objectMap.put("delInParams", loadInParams.toString());
        objectMap.put("delSqlParams", loadSqlParams.toString());
        objectMap.put("delNote", loadNote.toString());
        objectMap.put("delWhere", loadWhere.toString());
    }

    /**
     * 生成查询代码值
     *
     * @param selParams         参数
     * @param selParamDescs     字段描述
     * @param selParamJavaClass 参数对应类对象
     * @param selType           查询类型(0精确/1模糊/2区间查询)
     * @param objectMap         代码模版值
     */
    protected void dealSelCode(List<String> selParams, List<String> selParamDescs, List<String> selParamJavaClass, List<Integer> selType, Map<String, Object> objectMap) {
        StringJoiner selInParams = new StringJoiner(", ");
        StringJoiner selConInParams = new StringJoiner(", ");
        StringJoiner selSqlParams = new StringJoiner(", ");
        StringJoiner selNote = new StringJoiner(noteJoiner);
        StringJoiner selBuildParams = new StringJoiner("\n");
        for (int i = 0, length = selParams.size(); i < length; i++) {
            //判断参数类型，如果是Date需要加注解@DateTimeFormat
            JavaClass javaClass = JavaClass.fromCode(selParamJavaClass.get(i));
            boolean paramIsDate = javaClass == JavaClass.Date;
            //为区间查询
            if (selType.get(i) == 2) {
                selInParams.add(selParamJavaClass.get(i) + " START_" + selParams.get(i));
                selInParams.add(selParamJavaClass.get(i) + " END_" + selParams.get(i));
                selConInParams.add((paramIsDate ? "@DateTimeFormat(pattern = \"" + dateTimeFormat + "\") " : "") + selParamJavaClass.get(i) + " START_" + selParams.get(i));
                selConInParams.add((paramIsDate ? "@DateTimeFormat(pattern = \"" + dateTimeFormat + "\") " : "") + selParamJavaClass.get(i) + " END_" + selParams.get(i));
                selSqlParams.add("START_" + selParams.get(i));
                selSqlParams.add("END_" + selParams.get(i));
                selNote.add("@param START_" + selParams.get(i) + " 开始" + selParamDescs.get(i));
                selNote.add("@param END_" + selParams.get(i) + " 结束" + selParamDescs.get(i));
            } else {
                selInParams.add(selParamJavaClass.get(i) + " " + selParams.get(i));
                selConInParams.add((paramIsDate ? "@DateTimeFormat(pattern = \"" + dateTimeFormat + "\") " : "") + selParamJavaClass.get(i) + " " + selParams.get(i));
                selSqlParams.add(selParams.get(i));
                selNote.add("@param " + selParams.get(i) + " " + selParamDescs.get(i));
            }
            buildSelParam(selBuildParams, selParams.get(i), selParamJavaClass.get(i), selType.get(i));
        }
        objectMap.put("selInParams", selInParams.toString());
        objectMap.put("selConInParams", selConInParams.toString());
        objectMap.put("selSqlParams", selSqlParams.toString());
        objectMap.put("selNote", selNote.toString());
        objectMap.put("selMapSize", BaseUtils.newHashMapWithExpectedSize(selParams.size()));
        objectMap.put("selBuildParams", selBuildParams.toString());
    }

    /**
     * 构建排序sql
     *
     * @param orderParams     排序参数
     * @param orderParamTypes 排序类型（DESC/ASC）
     * @param objectMap       过程模版值
     */
    protected void dealSelOrderBy(List<String> orderParams, List<String> orderParamTypes, Map<String, Object> objectMap) {
        if (orderParams.size() == 0) return;
        StringJoiner orderBy = new StringJoiner(", ");
        for (int i = 0, length = orderParams.size(); i < length; i++) {
            orderBy.add(orderParams.get(i) + " " + orderParamTypes.get(i));
        }
        objectMap.put("orderBy", orderBy.toString());
    }

    /**
     * 组装查询代码
     *
     * @param selBuildParams    StringJoiner
     * @param selParam          数据库字段
     * @param selParamJavaClass 字段对应java对象
     * @param selType           查询类型(0精确/1模糊/2区间查询)
     */
    protected void buildSelParam(StringJoiner selBuildParams, String selParam, String selParamJavaClass, Integer selType) {
        //为区间查询
        if (selType == 2) {
            //为String时调用StringUtils.isNotEmpty判空
            if (JavaClass.fromCode(selParamJavaClass) == JavaClass.String) {
                selBuildParams.add("        if (StringUtils.isNotEmpty(START_" + selParam + ")) {");
            } else {
                selBuildParams.add("        if (START_" + selParam + " != null) {");
            }
            selBuildParams.add("            sql += \" and " + selParam + " >= :START_" + selParam + "\";");
            selBuildParams.add("            paramMap.put(\"START_" + selParam + "\", START_" + selParam + ");");
            selBuildParams.add("        }");

            if (JavaClass.fromCode(selParamJavaClass) == JavaClass.String) {
                selBuildParams.add("        if (StringUtils.isNotEmpty(END_" + selParam + ")) {");
            } else {
                selBuildParams.add("        if (END_" + selParam + " != null) {");
            }
            selBuildParams.add("            sql += \" and " + selParam + " <= :END_" + selParam + "\";");
            selBuildParams.add("            paramMap.put(\"END_" + selParam + "\", END_" + selParam + ");");
            selBuildParams.add("        }");
        } else {
            //为String时调用StringUtils.isNotEmpty判空
            if (JavaClass.fromCode(selParamJavaClass) == JavaClass.String) {
                selBuildParams.add("        if (StringUtils.isNotEmpty(" + selParam + ")) {");
            } else {
                selBuildParams.add("        if (" + selParam + " != null) {");
            }
            if (selType == 0) {
                selBuildParams.add("            sql += \" and " + selParam + " = :" + selParam + "\";");
            } else if (selType == 1) {
                selBuildParams.add("            sql += \" and " + selParam + " like '%' || :" + selParam + " || '%'\";");
            }
            selBuildParams.add("            paramMap.put(\"" + selParam + "\", " + selParam + ");");
            selBuildParams.add("        }");
        }

    }

    /**
     * 生成新增代码值
     * 规定主键在controller中生成
     *
     * @param insParams         新增代码参数
     * @param insParamDescs     新增代码字段描述
     * @param insParamJavaClass 参数对应类对象
     * @param primarys          主键参数集合
     * @param objectMap         代码模版值
     */
    protected void dealInsCode(List<String> insParams, List<String> insParamDescs, List<String> insParamJavaClass, List<String> primarys, Map<String, Object> objectMap) {
        StringJoiner insInParams = new StringJoiner(", ");
        StringJoiner insConInParams = new StringJoiner(", ");
        StringJoiner insSqlParams = new StringJoiner(", ");
        StringJoiner insConSqlParams = new StringJoiner(", ");
        StringJoiner insNote = new StringJoiner(noteJoiner);
        StringJoiner insSqlMark = new StringJoiner(", ");
        for (int i = 0, length = insParams.size(); i < length; i++) {
            insInParams.add(insParamJavaClass.get(i) + " " + insParams.get(i));
            //主键在controller中生成，无需前台传参
            if (!primarys.contains(insParams.get(i))) {
                insConInParams.add(insParamJavaClass.get(i) + " " + insParams.get(i));
                insConSqlParams.add(insParams.get(i));
            } else {
                insConSqlParams.add("BaseUtils.getUuid()");
            }
            insSqlParams.add(insParams.get(i));
            insNote.add("@param " + insParams.get(i) + " " + insParamDescs.get(i));
            insSqlMark.add("?");
        }
        objectMap.put("insInParams", insInParams.toString());
        objectMap.put("insConInParams", insConInParams.toString());
        objectMap.put("insSqlParams", insSqlParams.toString());
        objectMap.put("insConSqlParams", insConSqlParams.toString());
        objectMap.put("insSqlMark", insSqlMark.toString());
        objectMap.put("insNote", insNote.toString());
    }

    /**
     * 生成修改代码值
     *
     * @param primarys          主键集合
     * @param primaryDesc       字段描述
     * @param primaryJavaClass  参数对应类对象
     * @param updParams         参数
     * @param updParamDescs     字段描述
     * @param updParamJavaClass 参数对应类对象
     * @param objectMap         代码模版值
     */
    protected void dealUpdCode(List<String> primarys, List<String> primaryDesc, List<String> primaryJavaClass, List<String> updParams, List<String> updParamDescs, List<String> updParamJavaClass, Map<String, Object> objectMap) {
        StringJoiner updInParams = new StringJoiner(", ");
        StringJoiner updSqlParams = new StringJoiner(", ");
        StringJoiner updSet = new StringJoiner(", ");
        StringJoiner updWhere = new StringJoiner(" and ");
        StringJoiner updJdbcParams = new StringJoiner(", ");
        StringJoiner updJdbcParamsMerge = new StringJoiner(", ");
        StringJoiner updNote = new StringJoiner(noteJoiner);
        for (int i = 0, length = primarys.size(); i < length; i++) {
            updInParams.add(primaryJavaClass.get(i) + " " + primarys.get(i));
            updSqlParams.add(primarys.get(i));
            updNote.add("@param " + primarys.get(i) + " " + primaryDesc.get(i));
            updWhere.add(primarys.get(i) + " = ?");
            updJdbcParamsMerge.add(primarys.get(i));
        }
        for (int i = 0, length = updParams.size(); i < length; i++) {
            updInParams.add(updParamJavaClass.get(i) + " " + updParams.get(i));
            updSqlParams.add(updParams.get(i));
            updNote.add("@param " + updParams.get(i) + " " + updParamDescs.get(i));
            updSet.add(updParams.get(i) + " = ?");
            updJdbcParams.add(updParams.get(i));
        }
        //合并
        updJdbcParams.merge(updJdbcParamsMerge);
        objectMap.put("updInParams", updInParams.toString());
        objectMap.put("updSqlParams", updSqlParams.toString());
        objectMap.put("updNote", updNote.toString());
        objectMap.put("updSet", updSet.toString());
        objectMap.put("updWhere", updWhere.toString());
        objectMap.put("updJdbcParams", updJdbcParams.toString());
    }

    /**
     * 生成批量删除代码值
     *
     * @param primarys         主键集合
     * @param primaryDesc      字段描述
     * @param primaryJavaClass 参数对应类对象
     * @param objectMap        代码模版值
     * @param hasDelBatch      是否包含批量删除
     */
    protected void dealDelBatchCode(List<String> primarys, List<String> primaryDesc, List<String> primaryJavaClass, Map<String, Object> objectMap, boolean hasDelBatch) {
        if (!hasDelBatch) {
            objectMap.put("delBatchInParams", "");
            objectMap.put("delBatchControllerParams", "");
            objectMap.put("delBatchSqlParams", "");
            objectMap.put("delBatchNote", "");
            objectMap.put("delBatchWhere", "");
            objectMap.put("delBatchMapSize", "4");
            return;
        }
        StringJoiner delBatchInParams = new StringJoiner(", ");
        StringJoiner delBatchSqlParams = new StringJoiner(", ");
        StringJoiner delBatchWhere = new StringJoiner(" and ");
        StringJoiner delBatchControllerParams = new StringJoiner(", ");
        StringJoiner delBatchNote = new StringJoiner(noteJoiner);
        for (int i = 0, length = primarys.size(); i < length; i++) {
            delBatchInParams.add("List<" + primaryJavaClass.get(i) + "> " + primarys.get(i) + "LIST");
            delBatchControllerParams.add("@RequestParam(value = \"" + primarys.get(i) + "LIST\") List<" + primaryJavaClass.get(i) + "> " + primarys.get(i) + "LIST");
            delBatchSqlParams.add(primarys.get(i) + "LIST");
            delBatchNote.add("@param " + primarys.get(i) + "LIST " + primaryDesc.get(i) + "集合");
            delBatchWhere.add(primarys.get(i) + " in (:" + primarys.get(i) + "LIST)");
        }
        objectMap.put("delBatchInParams", delBatchInParams.toString());
        objectMap.put("delBatchControllerParams", delBatchControllerParams.toString());
        objectMap.put("delBatchSqlParams", delBatchSqlParams.toString());
        objectMap.put("delBatchNote", delBatchNote.toString());
        objectMap.put("delBatchWhere", delBatchWhere.toString());
        objectMap.put("delBatchMapSize", BaseUtils.newHashMapWithExpectedSize(primarys.size()));
    }

    /**
     * 处理导出接口后台代码所需参数
     * 子类可能需要重写以实现前台导出路径
     *
     * @param params     参数
     * @param paramDescs 字段描述
     * @param primarys   主键参数
     * @param hasExport  是否包含导出接口
     * @param objectMap  代码模版值
     */
    protected void dealExportCode(List<String> params, List<String> paramDescs, List<String> primarys, boolean hasExport, Map<String, Object> objectMap) {
        if (!hasExport) {
            objectMap.put("mapSize", "");
            objectMap.put("exportLinkHashMap", "");
            return;
        }
        StringJoiner exportLinkHashMap = new StringJoiner("\n        ");
        for (int i = 0, length = params.size(); i < length; i++) {
            //主键默认不显示在导出数据中
            if (primarys.contains(params.get(i))) {
                continue;
            }
            exportLinkHashMap.add("linkedHashMap.put(\"" + paramDescs.get(i) + "\", \"" + params.get(i) + "\");");
        }
        objectMap.put("mapSize", BaseUtils.newHashMapWithExpectedSize(params.size()));
        objectMap.put("exportLinkHashMap", exportLinkHashMap.toString());
    }

    /**
     * 导出接口的前台路径生成
     *
     * @param selParams         查询参数
     * @param selParamJavaClass 字段对应java对象
     * @param selType           查询类型(0精确/1模糊/2区间查询)
     * @param hasExport         是否包含导出接口
     * @param objectMap         代码模版值
     */
    protected void dealExportUrl(List<String> selParams, List<String> selParamJavaClass, List<Integer> selType, boolean hasExport, Map<String, Object> objectMap) {
    }

    /**
     * 根据表生成后台代码
     *
     * @param tableName       表名
     * @param tableDesc       表描述
     * @param moduleName      模块名称
     * @param moduleDesc      模块描述
     * @param packageName     包名
     * @param author          作者
     * @param hasDelBatch     是否包含批量删除
     * @param hasExport       是否包含导出接口
     * @param hasView         是否包含详情查看
     * @param driver          数据库驱动
     * @param params          参数
     * @param paramDescs      字段描述
     * @param paramJavaClass  参数对应类对象
     * @param priParamIndex   主键列索引
     * @param selParamsIndex  查询列索引
     * @param selType         查询类型(0精确/1模糊/2区间查询)
     * @param insParamIndex   新增列索引
     * @param updParamIndex   修改列索引
     * @param orderParamIndex 排序列索引
     * @param orderParamTypes 排序类型
     * @param configuration   ftl模板引擎配置
     */
    public Map<String, Object> genCodeByTable(String tableName, String tableDesc, String moduleName, String moduleDesc, String packageName, String author, boolean hasDelBatch, boolean hasExport, boolean hasView, String driver, List<String> params, List<String> paramDescs, List<String> paramJavaClass, List<Integer> priParamIndex, List<Integer> selParamsIndex, List<Integer> selType, List<Integer> insParamIndex, List<Integer> updParamIndex, List<Integer> orderParamIndex, List<String> orderParamTypes, Configuration configuration) {
        //一个集合包含所有字段，以及其它相关的存储集合的索引（0开始）
        //通过遍历所有字段集合来为相关集合赋值
        int priLength = priParamIndex.size();
        int selLength = selParamsIndex == null ? 0 : selParamsIndex.size();
        int insLength = insParamIndex.size();
        int updLength = updParamIndex.size();
        int orderLength = orderParamIndex == null ? 0 : orderParamIndex.size();
        List<String> primarys = new ArrayList<>(priLength);
        List<String> primaryDesc = new ArrayList<>(priLength);
        List<String> primaryJavaClass = new ArrayList<>(priLength);
        List<String> selParams = new ArrayList<>(selLength);
        List<String> selParamDescs = new ArrayList<>(selLength);
        List<String> selParamJavaClass = new ArrayList<>(selLength);
        List<String> insParams = new ArrayList<>(insLength);
        List<String> insParamDescs = new ArrayList<>(insLength);
        List<String> insParamJavaClass = new ArrayList<>(insLength);
        List<String> updParams = new ArrayList<>(updLength);
        List<String> updParamDescs = new ArrayList<>(updLength);
        List<String> updParamJavaClass = new ArrayList<>(updLength);
        List<String> orderParams = new ArrayList<>(orderLength);

        for (int i = 0, length = params.size(); i < length; i++) {
            if (i < priLength) {
                primarys.add(params.get(priParamIndex.get(i)));
                primaryDesc.add(paramDescs.get(priParamIndex.get(i)));
                primaryJavaClass.add(paramJavaClass.get(priParamIndex.get(i)));
            }
            if (i < selLength) {
                selParams.add(params.get(selParamsIndex.get(i)));
                selParamDescs.add(paramDescs.get(selParamsIndex.get(i)));
                selParamJavaClass.add(paramJavaClass.get(selParamsIndex.get(i)));
            }
            if (i < insLength) {
                insParams.add(params.get(insParamIndex.get(i)));
                insParamDescs.add(paramDescs.get(insParamIndex.get(i)));
                insParamJavaClass.add(paramJavaClass.get(insParamIndex.get(i)));
            }
            if (i < updLength) {
                updParams.add(params.get(updParamIndex.get(i)));
                updParamDescs.add(paramDescs.get(updParamIndex.get(i)));
                updParamJavaClass.add(paramJavaClass.get(updParamIndex.get(i)));
            }
            if (i < orderLength) {
                orderParams.add(params.get(orderParamIndex.get(i)));
            }
            if (i >= priLength && i >= selLength && i >= insLength && i >= updLength && i >= orderLength) {
                break;
            }
        }

        //模版值
        Map<String, Object> objectMap = new HashMap<>(128);
        dealCommonCode(tableName, tableDesc, moduleName, moduleDesc, packageName, author, hasDelBatch, hasExport, hasView, primarys, objectMap);
        dealLoadCode(primarys, primaryDesc, primaryJavaClass, objectMap);
        dealSelCode(selParams, selParamDescs, selParamJavaClass, selType, objectMap);
        dealSelOrderBy(orderParams, orderParamTypes, objectMap);
        dealInsCode(insParams, insParamDescs, insParamJavaClass, primarys, objectMap);
        dealUpdCode(primarys, primaryDesc, primaryJavaClass, updParams, updParamDescs, updParamJavaClass, objectMap);
        dealDelBatchCode(primarys, primaryDesc, primaryJavaClass, objectMap, hasDelBatch);
        dealExportCode(params, paramDescs, primarys, hasExport, objectMap);
        dealExportUrl(selParams, selParamJavaClass, selType, hasExport, objectMap);
        dealOtherCode(tableName, tableDesc, moduleName, moduleDesc, packageName, author, hasDelBatch, hasExport, hasView, params, paramDescs, paramJavaClass, primarys, primaryDesc, primaryJavaClass, selParams, selParamDescs, selParamJavaClass, selType, insParams, insParamDescs, insParamJavaClass, updParams, updParamDescs, updParamJavaClass, objectMap);
        //返回结果
        return getResult(driver, objectMap, configuration);
    }

    /**
     * 交给子类实现，方便子类实现别的代码
     */
    protected void dealOtherCode(String tableName, String tableDesc, String moduleName, String moduleDesc, String packageName, String author, boolean hasDelBatch, boolean hasExport, boolean hasView, List<String> params, List<String> paramDescs, List<String> paramJavaClass, List<String> primarys, List<String> primaryDesc, List<String> primaryJavaClass, List<String> selParams, List<String> selParamDescs, List<String> selParamJavaClass, List<Integer> selType, List<String> insParams, List<String> insParamDescs, List<String> insParamJavaClass, List<String> updParams, List<String> updParamDescs, List<String> updParamJavaClass, Map<String, Object> objectMap) {
    }

    /**
     * 处理返回结果，方便子类重写
     */
    protected Map<String, Object> getResult(String driver, Map<String, Object> objectMap, Configuration configuration) {
        //返回结果
        Map<String, Object> result = new HashMap<>(16);
        //tab页集合, 保证返回顺序
        result.put("list", getTabList(objectMap));
        result.put("BaseUtils", FreeMarkerUtil.getTemplateContent(configuration, objectMap, "common/BaseUtils.ftl"));
        result.put("BaseSqlCriteria", FreeMarkerUtil.getTemplateContent(configuration, objectMap, "common/BaseSqlCriteria.ftl"));
        result.put("controller", getController(configuration, objectMap, driver));
        result.put("service", getService(configuration, objectMap, driver));
        result.put("serviceImpl", getServiceImpl(configuration, objectMap, driver));
        return result;
    }

    /**
     * 获取前台tab的list集合
     */
    protected List<String> getTabList(Map<String, Object> objectMap) {
        return new ArrayList<>(Arrays.asList("controller", "service", "serviceImpl", "BaseUtils", "BaseSqlCriteria"));
    }

    /**
     * 获取controller层代码
     */
    protected String getController(Configuration configuration, Map<String, Object> objectMap, String driver) {
        return getFtlModel(configuration, objectMap, getDbTypePackageName(driver) + File.separator + "controller.ftl");
    }

    /**
     * 获取service层代码
     */
    protected String getService(Configuration configuration, Map<String, Object> objectMap, String driver) {
        return getFtlModel(configuration, objectMap, getDbTypePackageName(driver) + File.separator + "service.ftl");
    }

    /**
     * 获取service层代码
     */
    protected String getServiceImpl(Configuration configuration, Map<String, Object> objectMap, String driver) {
        return getFtlModel(configuration, objectMap, getDbTypePackageName(driver) + File.separator + "serviceImpl.ftl");
    }

}
