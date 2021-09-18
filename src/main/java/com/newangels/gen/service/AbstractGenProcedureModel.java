package com.newangels.gen.service;

import com.newangels.gen.util.dataSource.DataSourceUtil;
import com.newangels.gen.util.template.AbstractFreeMarkerTemplate;
import com.newangels.gen.util.template.FreeMarkerUtil;
import freemarker.template.Configuration;
import org.springframework.beans.factory.InitializingBean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 存储过程代码生成模板抽象类
 *
 * @author: TangLiang
 * @date: 2021/9/7 21:40
 * @since: 1.0
 */
public abstract class AbstractGenProcedureModel extends AbstractFreeMarkerTemplate implements InitializingBean {

    @Override
    protected String getRootPackageName() {
        return "genProcedureModel";
    }

    /**
     * 获取请求协议
     *
     * @param procedureName 存储过程名称
     * @param nameConvent   命名规范
     */
    protected abstract String getMappingType(String procedureName, NameConventService nameConvent);

    /**
     * 处理通用代码以及tab页集合list
     *
     * @param configuration ftl模板引擎配置
     * @param objectMap     方法模版参数
     * @param result        返回结果集
     */
    protected void dealCommonCode(Configuration configuration, Map<String, Object> objectMap, Map<String, Object> result) {
        //tab页集合, 保证返回顺序
        List<String> list = new ArrayList<>(Arrays.asList("controller", "service", "serviceImpl", "repository", "BaseUtils", "ProcedureUtils"));
        result.put("BaseUtils", FreeMarkerUtil.getTemplateContent(configuration, objectMap, "common/BaseUtils.ftl"));
        result.put("ProcedureUtils", FreeMarkerUtil.getTemplateContent(configuration, objectMap, "common/ProcedureUtils.ftl"));
        result.put("list", list);
    }

    /**
     * 根据存储过程的入参出参生成各部分需要代码
     *
     * @param map              存储过程参数
     * @param inParams         方法传参
     * @param outParams        调用方法入参
     * @param procedureParams  repository存储过程中的参数
     * @param serviceNote      service层注释
     * @param repositoryParam  存储过程赋值
     * @param repositoryResult 存储过程结果集
     * @param nameConvent      命名规范
     * @param dbProcedure      代码规范
     */
    protected void dealProcParams(Map<String, Object> map, StringJoiner inParams, StringJoiner outParams, StringJoiner procedureParams, StringJoiner serviceNote, StringJoiner repositoryParam, StringJoiner repositoryResult, NameConventService nameConvent, DataBaseProcedureService dbProcedure) {
        if ("IN".equals(map.get("IN_OUT")) || "false".equals(map.get("IN_OUT").toString())) {
            //存储过程中传参去掉V_
            String value = map.get("ARGUMENT_NAME").toString();
            if (value.startsWith("@")) {
                value = value.replaceFirst("@", "");
            } else if (value.startsWith("I_I_")) {
                value = value.replaceFirst("I_", "");
            } else if (value.startsWith("V_")) {
                value = value.replaceFirst("V_", "");
            }
            //参数类型
            String dataType = map.get("DATA_TYPE").toString();
            inParams.add(dbProcedure.getJavaClass(map.get("DATA_TYPE").toString()) + " " + value);
            outParams.add(value);
            procedureParams.add(":" + value + "");
            if ("DATE".equals(dataType)) {
                repositoryParam.add("            statement.set" + dbProcedure.getRepositoryOutTypeCode(dataType) + "(\"" + value + "\", new Timestamp(" + value + ".getTime()));");
            } else {
                repositoryParam.add("            statement.set" + dbProcedure.getRepositoryOutTypeCode(dataType) + "(\"" + value + "\", " + value + ");");
            }
            serviceNote.add("     * @param " + value);
        } else if ("OUT".equals(map.get("IN_OUT")) || "true".equals(map.get("IN_OUT").toString())) {
            //参数名
            String value = map.get("ARGUMENT_NAME").toString();
            if (value.startsWith("@")) {
                value = value.replaceFirst("@", "");
            }
            //参数类型
            String dataType = map.get("DATA_TYPE").toString();
            procedureParams.add(":" + value + "");
            repositoryParam.add("            statement.registerOutParameter(\"" + value + "\", " + dbProcedure.getRepositoryOutType(dataType) + ");");
            if ("REF CURSOR".equals(dataType)) {
                repositoryResult.add("            returnValue.put(\"" + nameConvent.getResultName(value) + "\", ProcedureUtils.resultHash((ResultSet) cs.get" + dbProcedure.getRepositoryOutTypeCode(dataType) + "(\"" + value + "\")));");
            } else {
                repositoryResult.add("            returnValue.put(\"" + nameConvent.getResultName(value) + "\", cs.get" + dbProcedure.getRepositoryOutTypeCode(dataType) + "(\"" + value + "\"));");
            }
        } else if ("IN/OUT".equals(map.get("IN_OUT"))) {
            //存储过程中传参去掉V_
            String value = map.get("ARGUMENT_NAME").toString();
            if (value.startsWith("@")) {
                value = value.replaceFirst("@", "");
            } else if (value.startsWith("I_I_")) {
                value = value.replaceFirst("I_", "");
            } else if (value.startsWith("V_")) {
                value = value.replaceFirst("V_", "");
            }
            //参数类型
            String dataType = map.get("DATA_TYPE").toString();
            inParams.add(dbProcedure.getJavaClass(map.get("DATA_TYPE").toString()) + " " + value);
            outParams.add(value);
            procedureParams.add(":" + value + "");
            if ("DATE".equals(dataType)) {
                repositoryParam.add("            statement.set" + dbProcedure.getRepositoryOutTypeCode(dataType) + "(\"" + value + "\", new Timestamp(" + value + ".getTime()));");
            } else {
                repositoryParam.add("            statement.set" + dbProcedure.getRepositoryOutTypeCode(dataType) + "(\"" + value + "\", " + value + ");");
            }
            serviceNote.add("     * @param " + value);
            if ("REF CURSOR".equals(dataType)) {
                repositoryResult.add("            returnValue.put(\"" + nameConvent.getResultName(value) + "\", ProcedureUtils.resultHash((ResultSet) cs.get" + dbProcedure.getRepositoryOutTypeCode(dataType) + "(\"" + value + "\")));");
            } else {
                repositoryResult.add("            returnValue.put(\"" + nameConvent.getResultName(value) + "\", cs.get" + dbProcedure.getRepositoryOutTypeCode(dataType) + "(\"" + value + "\"));");
            }
        }
    }

    /**
     * 返回各层方法代码
     *
     * @param moduleName        模块名称
     * @param packageName       包名
     * @param userName          用户名
     * @param procedureNameList 存储过程集合
     * @param nameConvent       命名规范
     * @param dbProcedure       代码规范
     * @param dataSourceUtil    数据库连接
     * @param configuration     ftl模板引擎配置
     */
    protected Map<String, Object> dealProcdure(String moduleName, String packageName, String userName, List<String> procedureNameList, NameConventService nameConvent, DataBaseProcedureService dbProcedure, DataSourceUtil dataSourceUtil, Configuration configuration) {
        Map<String, Object> result = new HashMap<>(16);
        int length = procedureNameList.size();
        if (length == 0) {
            return result;
        }
        //各层方法代码 初始化容量按照每次生成四个过程赋予初始值
        StringBuilder controllerMethod = new StringBuilder(256 * length);
        StringBuilder serviceMethod = new StringBuilder(128 * length);
        StringBuilder serviceImplMethod = new StringBuilder(128 * length);
        StringBuilder repositoryMethod = new StringBuilder(768 * length);
        //排序
        nameConvent.sortMethod(procedureNameList);
        //获取方法名称集合
        List<String> methodNames = nameConvent.getMethodNames(moduleName, procedureNameList);
        //循环存储过程
        for (int i = 0; i < length; i++) {
            String procedureName = procedureNameList.get(i);
            List<Map<String, Object>> list = dataSourceUtil.executeQuery(dbProcedure.selectArguments(userName.toUpperCase(), procedureName.toUpperCase()));
            //方法传参
            StringJoiner inParams = new StringJoiner(", ");
            //入参
            StringJoiner outParams = new StringJoiner(", ");
            //repository存储过程中的参数
            StringJoiner procedureParams = new StringJoiner(", ");
            //service层注释
            StringJoiner serviceNote = new StringJoiner("\n");
            //存储过程赋值
            StringJoiner repositoryParam = new StringJoiner("\n");
            //存储过程结果集
            StringJoiner repositoryResult = new StringJoiner("\n");
            //获取存储过程所有参数
            for (Map<String, Object> map : list) {
                dealProcParams(map, inParams, outParams, procedureParams, serviceNote, repositoryParam, repositoryResult, nameConvent, dbProcedure);
            }
            //存储过程名称处理（主要用于处理包下的存储过程）
            String procedureFullName = procedureName;
            //处理包下的存储过程
            int start = procedureName.indexOf(".");
            if (start >= 0) {
                procedureName = procedureName.substring(start).replaceFirst(".", "");
            }
            //请求协议
            String mappingType = getMappingType(procedureName, nameConvent);
            //方法模版参数
            Map<String, Object> objectMap = new HashMap<>(32);
            objectMap.put("package", packageName);
            objectMap.put("mappingType", mappingType);
            objectMap.put("method", methodNames.get(i));
            objectMap.put("inParams", inParams);
            objectMap.put("outParams", outParams);
            objectMap.put("module", moduleName);
            objectMap.put("serviceNote", serviceNote);
            objectMap.put("procedureFullName", procedureFullName);
            objectMap.put("procedureName", procedureName);
            objectMap.put("procedureParams", procedureParams);
            objectMap.put("repositoryParam", repositoryParam);
            objectMap.put("repositoryResult", repositoryResult);
            //方法模版返回
            controllerMethod.append(getControllerMethod(configuration, objectMap)).append("\n");
            serviceMethod.append(getServiceMethod(configuration, objectMap)).append("\n");
            serviceImplMethod.append(getServiceImplMethod(configuration, objectMap)).append("\n");
            repositoryMethod.append(getRepositoryMethod(configuration, objectMap)).append("\n");
        }
        result.put("controllerMethod", controllerMethod.toString());
        result.put("serviceMethod", serviceMethod.toString());
        result.put("serviceImplMethod", serviceImplMethod.toString());
        result.put("repositoryMethod", repositoryMethod.toString());
        return result;
    }

    /**
     * 根据代码规范和命名规范以及数据库连接生成对应代码
     *
     * @param moduleName        模块名称
     * @param packageName       包名
     * @param userName          用户名
     * @param procedureNameList 存储过程集合
     * @param author            作者
     * @param nameConvent       命名规范
     * @param dbProcedure       代码规范
     * @param dataSourceUtil    数据库连接
     * @param configuration     ftl模板引擎配置
     */
    public Map<String, Object> genCode(String moduleName, String packageName, String userName, List<String> procedureNameList, String author, NameConventService nameConvent, DataBaseProcedureService dbProcedure, DataSourceUtil dataSourceUtil, Configuration configuration) {
        Map<String, Object> result = new HashMap<>(16);
        //方法代码
        Map<String, Object> objectMap = dealProcdure(moduleName, packageName, userName, procedureNameList, nameConvent, dbProcedure, dataSourceUtil, configuration);
        objectMap.put("package", packageName);
        objectMap.put("module", moduleName);
        objectMap.put("author", author);
        objectMap.put("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
        //返回结果处理
        dealCommonCode(configuration, objectMap, result);
        result.put("controller", getController(configuration, objectMap));
        result.put("service", getService(configuration, objectMap));
        result.put("serviceImpl", getServiceImpl(configuration, objectMap));
        result.put("repository", getRepository(configuration, objectMap));
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

    /**
     * 获取repository层代码
     */
    protected String getRepository(Configuration configuration, Map<String, Object> objectMap) {
        return getFtlModel(configuration, objectMap, "repository.ftl");
    }

    /**
     * 获取controller层方法代码
     */
    protected String getControllerMethod(Configuration configuration, Map<String, Object> objectMap) {
        return getFtlModel(configuration, objectMap, "controllerMethod.ftl");
    }

    /**
     * 获取service层方法代码
     */
    protected String getServiceMethod(Configuration configuration, Map<String, Object> objectMap) {
        return getFtlModel(configuration, objectMap, "serviceMethod.ftl");
    }

    /**
     * 获取serviceImpl层方法代码
     */
    protected String getServiceImplMethod(Configuration configuration, Map<String, Object> objectMap) {
        return getFtlModel(configuration, objectMap, "serviceImplMethod.ftl");
    }

    /**
     * 获取repository层方法代码
     */
    protected String getRepositoryMethod(Configuration configuration, Map<String, Object> objectMap) {
        return getFtlModel(configuration, objectMap, "repositoryMethod.ftl");
    }

}
