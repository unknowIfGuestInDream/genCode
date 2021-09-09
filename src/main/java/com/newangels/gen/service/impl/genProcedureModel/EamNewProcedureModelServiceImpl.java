package com.newangels.gen.service.impl.genProcedureModel;

import com.newangels.gen.enums.GenProcedureModelType;
import com.newangels.gen.factory.GenProcedureModelFactory;
import com.newangels.gen.service.DataBaseProcedureService;
import com.newangels.gen.service.GenProcedureModelService;
import com.newangels.gen.service.NameConventService;
import com.newangels.gen.util.DataSourceUtil;
import com.newangels.gen.util.FreeMarkerUtil;
import freemarker.template.Configuration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Eam3期风格代码
 *
 * @author: TangLiang
 * @date: 2021/8/13 13:00
 * @since: 1.0
 */
@Service
@RequiredArgsConstructor
@Deprecated
public class EamNewProcedureModelServiceImpl implements GenProcedureModelService {
    private final FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public String getControllerCode(String moduleName, String packageName, String author, String methodCode) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("package", packageName);
        objectMap.put("module", moduleName);
        objectMap.put("author", author);
        objectMap.put("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
        objectMap.put("controllerMethod", methodCode);
        return FreeMarkerUtil.getTemplateContent(freeMarkerConfigurer, objectMap, "genProcedureModel/eamNew/controller.ftl");
    }

    @Override
    public String getServiceCode(String moduleName, String packageName, String author, String methodCode) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("package", packageName);
        objectMap.put("module", moduleName);
        objectMap.put("author", author);
        objectMap.put("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
        objectMap.put("serviceMethod", methodCode);
        return FreeMarkerUtil.getTemplateContent(freeMarkerConfigurer, objectMap, "genProcedureModel/eamNew/service.ftl");
    }

    @Override
    public String getServiceImplCode(String moduleName, String packageName, String author, String methodCode) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("package", packageName);
        objectMap.put("module", moduleName);
        objectMap.put("author", author);
        objectMap.put("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
        objectMap.put("serviceImplMethod", methodCode);
        return FreeMarkerUtil.getTemplateContent(freeMarkerConfigurer, objectMap, "genProcedureModel/eamNew/serviceImpl.ftl");
    }

    @Override
    public String getRepositoryCode(String moduleName, String packageName, String author, String methodCode) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("package", packageName);
        objectMap.put("module", moduleName);
        objectMap.put("author", author);
        objectMap.put("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
        objectMap.put("repositoryMethod", methodCode);
        return FreeMarkerUtil.getTemplateContent(freeMarkerConfigurer, objectMap, "genProcedureModel/eamNew/repository.ftl");
    }

    @Override
    public String getMappingType(String procedureName, NameConventService nameConvent) {
        if ("select".equals(nameConvent.getName(procedureName)) || "load".equals(nameConvent.getName(procedureName))) {
            return "GetMapping";
        }
        return "PostMapping";
    }

    @Override
    public Map<String, Object> genCode(String moduleName, String packageName, String userName, List<String> procedureNameList, NameConventService nameConvent, DataBaseProcedureService dbProcedure, DataSourceUtil dataSourceUtil, String author) {
        Map<String, Object> result = new HashMap<>(16);
        //各层代码
        StringBuilder controllerCode = new StringBuilder();
        StringBuilder serviceCode = new StringBuilder();
        StringBuilder serviceImplCode = new StringBuilder();
        StringBuilder repositoryCode = new StringBuilder();
        //排序
        nameConvent.sortMethod(procedureNameList);
        //循环存储过程
        for (String procedureName : procedureNameList) {
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
            //方法名称前缀
            String preName = nameConvent.getName(procedureName);
            //请求协议
            String mappingType = getMappingType(procedureName, nameConvent);

            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("package", packageName);
            objectMap.put("mappingType", mappingType);
            objectMap.put("method", preName + moduleName);
            objectMap.put("inParams", inParams);
            objectMap.put("outParams", outParams);
            objectMap.put("module", moduleName);
            objectMap.put("serviceNote", serviceNote);
            objectMap.put("procedureName", procedureName);
            objectMap.put("procedureParams", procedureParams);
            objectMap.put("repositoryParam", repositoryParam);
            objectMap.put("repositoryResult", repositoryResult);

            controllerCode.append(FreeMarkerUtil.getTemplateContent(configuration, objectMap, "genProcedureModel/eamNew/controllerMethod.ftl")).append("\n");
            serviceCode.append(FreeMarkerUtil.getTemplateContent(configuration, objectMap, "genProcedureModel/eamNew/serviceMethod.ftl")).append("\n");
            serviceImplCode.append(FreeMarkerUtil.getTemplateContent(configuration, objectMap, "genProcedureModel/eamNew/serviceImplMethod.ftl")).append("\n");
            repositoryCode.append(FreeMarkerUtil.getTemplateContent(configuration, objectMap, "genProcedureModel/eamNew/repositoryMethod.ftl")).append("\n");
        }

        //ant design pro规范
        String controller = controllerCode.toString()
                .replace("V_PAGESIZE", "pageSize")
                .replace("V_PAGE", "current");

        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("package", packageName);

        //tab页集合
        List<String> list = new ArrayList<>(Arrays.asList("controller", "service", "serviceImpl", "repository", "BaseUtils", "ProcedureUtils"));
        result.put("controller", getControllerCode(moduleName, packageName, author, controller));
        result.put("service", getServiceCode(moduleName, packageName, author, serviceCode.toString()));
        result.put("serviceImpl", getServiceImplCode(moduleName, packageName, author, serviceImplCode.toString()));
        result.put("repository", getRepositoryCode(moduleName, packageName, author, repositoryCode.toString()));
        result.put("BaseUtils", FreeMarkerUtil.getTemplateContent(configuration, objectMap, "common/BaseUtils.ftl"));
        result.put("ProcedureUtils", FreeMarkerUtil.getTemplateContent(configuration, objectMap, "common/ProcedureUtils.ftl"));
        result.put("list", list);
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        GenProcedureModelFactory.register(GenProcedureModelType.EAM3, this);
    }
}
