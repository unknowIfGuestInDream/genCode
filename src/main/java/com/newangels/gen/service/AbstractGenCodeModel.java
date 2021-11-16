package com.newangels.gen.service;

import com.newangels.gen.util.template.AbstractFreeMarkerTemplate;
import freemarker.template.Configuration;
import org.springframework.beans.factory.InitializingBean;

import java.util.*;

/**
 * 表生成后台代码
 *
 * @author: TangLiang
 * @date: 2021/11/7 21:40
 * @since: 1.0
 */
public abstract class AbstractGenCodeModel extends AbstractFreeMarkerTemplate implements InitializingBean {

    @Override
    protected String getRootPackageName() {
        return "genCodeModel";
    }

    /**
     * 处理代码基本信息
     *
     * @param tableName   表名
     * @param tableDesc   表描述信息
     * @param nameConvent 命名规范
     * @param objectMap   代码模版值
     */
    protected void dealCommonCode(String tableName, String tableDesc, NameConventService nameConvent, Map<String, Object> objectMap) {
        objectMap.put("tableName", tableName);
        objectMap.put("tableDesc", tableDesc);
        objectMap.put("result", nameConvent.getProcOutParamName("result"));
        objectMap.put("message", nameConvent.getProcOutParamName("message"));
        objectMap.put("page", nameConvent.getProcOutParamName("page"));
        objectMap.put("limit", nameConvent.getProcOutParamName("limit"));
        objectMap.put("total", nameConvent.getProcOutParamName("total"));
    }

    /**
     * 生成加载代码值
     *
     * @param tableName    表名
     * @param primarys     主键集合
     * @param primaryTypes 主键数据库类型集合
     * @param primaryDesc  字段描述
     * @param nameConvent  命名规范
     * @param objectMap    代码模版值
     */
    protected abstract void dealGetCode(String tableName, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, NameConventService nameConvent, Map<String, Object> objectMap);

    /**
     * 生成查询代码值(无分页)
     *
     * @param tableName     表名
     * @param selParams     参数
     * @param selParamTypes 参数类型
     * @param selParamDescs 字段描述
     * @param selType       查询类型(0精确/1模糊/2区间查询)
     * @param nameConvent   命名规范
     * @param objectMap     代码模版值
     */
    protected abstract void dealSelCode(String tableName, List<String> selParams, List<String> selParamTypes, List<String> selParamDescs, List<Integer> selType, NameConventService nameConvent, Map<String, Object> objectMap);

    /**
     * 生成查询代码值(有分页)
     *
     * @param tableName     表名
     * @param selParams     参数
     * @param selParamTypes 参数类型
     * @param selParamDescs 字段描述
     * @param selType       查询类型(0精确/1模糊/2区间查询)
     * @param nameConvent   命名规范
     * @param objectMap     代码模版值
     */
    protected abstract void dealSelWithPageCode(String tableName, List<String> selParams, List<String> selParamTypes, List<String> selParamDescs, List<Integer> selType, NameConventService nameConvent, Map<String, Object> objectMap);

    /**
     * 生成新增代码值
     *
     * @param tableName     表名
     * @param insParams     新增代码参数
     * @param insParamTypes 新增代码参数类型
     * @param insParamDescs 新增代码字段描述
     * @param nameConvent   命名规范
     * @param objectMap     代码模版值
     */
    protected abstract void dealInsCode(String tableName, List<String> insParams, List<String> insParamTypes, List<String> insParamDescs, NameConventService nameConvent, Map<String, Object> objectMap);

    /**
     * 生成修改代码值
     *
     * @param tableName     表名
     * @param primarys      主键集合
     * @param primaryTypes  主键数据库类型集合
     * @param primaryDesc   字段描述
     * @param updParams     参数
     * @param updParamTypes 参数类型
     * @param updParamDescs 字段描述
     * @param nameConvent   命名规范
     * @param objectMap     代码模版值
     */
    protected abstract void dealUpdCode(String tableName, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, List<String> updParams, List<String> updParamTypes, List<String> updParamDescs, NameConventService nameConvent, Map<String, Object> objectMap);

    /**
     * 生成保存代码值
     *
     * @param tableName     表名
     * @param insParams     新增代码参数
     * @param insParamTypes 新增代码参数类型
     * @param insParamDescs 新增代码字段描述
     * @param primarys      主键集合
     * @param primaryTypes  主键数据库类型集合
     * @param primaryDesc   字段描述
     * @param updParams     参数
     * @param updParamTypes 参数类型
     * @param updParamDescs 字段描述
     * @param nameConvent   命名规范
     * @param objectMap     代码模版值
     */
    protected abstract void dealSaveCode(String tableName, List<String> insParams, List<String> insParamTypes, List<String> insParamDescs, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, List<String> updParams, List<String> updParamTypes, List<String> updParamDescs, NameConventService nameConvent, Map<String, Object> objectMap);

    /**
     * 生成删除代码值
     *
     * @param tableName    表名
     * @param primarys     主键集合
     * @param primaryTypes 主键数据库类型集合
     * @param primaryDesc  字段描述
     * @param nameConvent  命名规范
     * @param objectMap    代码模版值
     */
    protected abstract void dealDelCode(String tableName, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, NameConventService nameConvent, Map<String, Object> objectMap);

    /**
     * 根据表生成后台代码
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
     * @param configuration  ftl模板引擎配置
     */
    public Map<String, Object> genCodesByTable(String tableName, String tableDesc, List<String> params, List<String> paramTypes, List<String> paramDescs, List<Integer> priParamIndex, List<Integer> selParamsIndex, List<Integer> selType, List<Integer> insParamIndex, List<Integer> updParamIndex, NameConventService nameConvent, Configuration configuration) {
        //一个集合包含所有字段，以及其它相关的存储集合的索引（0开始）
        //通过遍历所有字段集合来为相关集合赋值
        int priLength = priParamIndex.size();
        int selLength = selParamsIndex.size();
        int insLength = insParamIndex.size();
        int updLength = updParamIndex.size();
        List<String> primarys = new ArrayList<>(priLength);
        List<String> primaryTypes = new ArrayList<>(priLength);
        List<String> primaryDesc = new ArrayList<>(priLength);
        List<String> selParams = new ArrayList<>(selLength);
        List<String> selParamTypes = new ArrayList<>(selLength);
        List<String> selParamDescs = new ArrayList<>(selLength);
        List<String> insParams = new ArrayList<>(insLength);
        List<String> insParamTypes = new ArrayList<>(insLength);
        List<String> insParamDescs = new ArrayList<>(insLength);
        List<String> updParams = new ArrayList<>(updLength);
        List<String> updParamTypes = new ArrayList<>(updLength);
        List<String> updParamDescs = new ArrayList<>(updLength);

        for (int i = 0, length = params.size(); i < length; i++) {
            if (i < priLength) {
                primarys.add(params.get(priParamIndex.get(i)));
                primaryTypes.add(paramTypes.get(priParamIndex.get(i)));
                primaryDesc.add(paramDescs.get(priParamIndex.get(i)));
            }
            if (i < selLength) {
                selParams.add(params.get(selParamsIndex.get(i)));
                selParamTypes.add(paramTypes.get(selParamsIndex.get(i)));
                selParamDescs.add(paramDescs.get(selParamsIndex.get(i)));
            }
            if (i < insLength) {
                insParams.add(params.get(insParamIndex.get(i)));
                insParamTypes.add(paramTypes.get(insParamIndex.get(i)));
                insParamDescs.add(paramDescs.get(insParamIndex.get(i)));
            }
            if (i < updLength) {
                updParams.add(params.get(updParamIndex.get(i)));
                updParamTypes.add(paramTypes.get(updParamIndex.get(i)));
                updParamDescs.add(paramDescs.get(updParamIndex.get(i)));
            }
            if (i >= priParamIndex.size() && i >= selParamsIndex.size() && i >= insParamIndex.size() && i >= updParamIndex.size()) {
                break;
            }
        }

        //模版值
        Map<String, Object> objectMap = new HashMap<>(64);
        dealCommonCode(tableName, tableDesc, nameConvent, objectMap);
        dealGetCode(tableName, primarys, primaryTypes, primaryDesc, nameConvent, objectMap);
        dealSelCode(tableName, selParams, selParamTypes, selParamDescs, selType, nameConvent, objectMap);
        dealSelWithPageCode(tableName, selParams, selParamTypes, selParamDescs, selType, nameConvent, objectMap);
        dealInsCode(tableName, insParams, insParamTypes, insParamDescs, nameConvent, objectMap);
        dealUpdCode(tableName, primarys, primaryTypes, primaryDesc, updParams, updParamTypes, updParamDescs, nameConvent, objectMap);
        dealSaveCode(tableName, insParams, insParamTypes, insParamDescs, primarys, primaryTypes, primaryDesc, updParams, updParamTypes, updParamDescs, nameConvent, objectMap);
        dealDelCode(tableName, primarys, primaryTypes, primaryDesc, nameConvent, objectMap);
        //返回结果
        Map<String, Object> result = new HashMap<>(16);
        //tab页集合, 保证返回顺序
        List<String> list = new ArrayList<>(Arrays.asList("get", "select", "selectWithPage", "insert", "update", "save", "delete"));
        result.put("list", list);
//        result.put("get", getGetCode(configuration, objectMap));
//        result.put("select", getSelCode(configuration, objectMap));
//        result.put("selectWithPage", getSelWithPageCode(configuration, objectMap));
//        result.put("insert", getInsCode(configuration, objectMap));
//        result.put("update", getUpdCode(configuration, objectMap));
//        result.put("save", getSaveCode(configuration, objectMap));
//        result.put("delete", getDelCode(configuration, objectMap));
        return result;
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
    protected String getService(Configuration configuration, Map<String, Object> objectMap) {
        return getFtlModel(configuration, objectMap, "service.ftl");
    }

    /**
     * 获取service层代码
     */
    protected String getServiceImpl(Configuration configuration, Map<String, Object> objectMap) {
        return getFtlModel(configuration, objectMap, "serviceImpl.ftl");
    }

}
