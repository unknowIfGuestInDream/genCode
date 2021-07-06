package com.newangels.gen.service.impl;

import cn.hutool.core.util.StrUtil;
import com.newangels.gen.base.BaseUtils;
import com.newangels.gen.enums.GenProcedureModelType;
import com.newangels.gen.factory.GenProcedureModelFactory;
import com.newangels.gen.service.DataBaseProcedureService;
import com.newangels.gen.service.GenProcedureModelService;
import com.newangels.gen.service.NameConventService;
import com.newangels.gen.util.DataSourceUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * EAM项目风格代码
 *
 * @author: TangLiang
 * @date: 2021/6/20 13:00
 * @since: 1.0
 */
@Service
public class EamProcedureModelServiceImpl implements GenProcedureModelService {
    @Override
    public String getControllerCode(String moduleName, String packageName, String author) {
        return "package " + packageName + ".controller;\n" +
                "\n" +
                "import " + packageName + ".base.BaseUtils;\n" +
                "import " + packageName + ".service." + moduleName + "Service;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.web.bind.annotation.GetMapping;\n" +
                "import org.springframework.web.bind.annotation.PostMapping;\n" +
                "import org.springframework.web.bind.annotation.ResponseBody;\n" +
                "import org.springframework.stereotype.Controller;\n" +
                "\n" +
                "import javax.servlet.http.HttpServletRequest;\n" +
                "import java.util.HashMap;\n" +
                "import java.util.Map;\n" +
                "\n" +
                "/**\n" +
                " *\n" +
                " *\n" +
                " * @author: " + author + "\n" +
                " * @date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")) + "\n" +
                " * @since: 1.0\n" +
                " */\n" +
                "@Controller\n" +
                "public class " + moduleName + "Controller {\n" +
                "    @Autowired\n" +
                "    private " + moduleName + "Service " + BaseUtils.toLowerCase4Index(moduleName) + "Service;\n" +
                "{}" +
                "}";
    }

    @Override
    public String getServiceCode(String moduleName, String packageName, String author) {
        return "package " + packageName + ".service;\n" +
                "\n" +
                "import java.util.List;\n" +
                "import java.util.Map;\n" +
                "\n" +
                "/**\n" +
                " * \n" +
                " *\n" +
                " * @author: " + author + "\n" +
                " * @date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")) + "\n" +
                " * @since: 1.0\n" +
                " */\n" +
                "public interface " + moduleName + "Service {\n" +
                "{}" +
                "\n" +
                "}";
    }

    @Override
    public String getServiceImplCode(String moduleName, String packageName, String author) {
        return "package " + packageName + ".service.impl;\n" +
                "\n" +
                "import " + packageName + ".repository." + moduleName + "Repository;\n" +
                "import " + packageName + ".service." + moduleName + "Service;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.stereotype.Service;\n" +
                "import org.springframework.transaction.annotation.Propagation;\n" +
                "import org.springframework.transaction.annotation.Transactional;\n" +
                "\n" +
                "import java.util.Map;\n" +
                "\n" +
                "/**\n" +
                " * @author: " + author + "\n" +
                " * @date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")) + "\n" +
                " * @since: 1.0\n" +
                " */\n" +
                "@Service\n" +
                "@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)\n" +
                "public class " + moduleName + "ServiceImpl implements " + moduleName + "Service {\n" +
                "    @Autowired\n" +
                "    private " + moduleName + "Repository " + BaseUtils.toLowerCase4Index(moduleName) + "Repository;\n" +
                "{}" +
                "}";
    }

    @Override
    public String getRepositoryCode(String moduleName, String packageName, String author) {
        return "package " + packageName + ".repository;\n" +
                "\n" +
                "import " + packageName + ".util.ProcedureUtils;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import oracle.jdbc.OracleTypes;\n" +
                "import org.springframework.dao.DataAccessException;\n" +
                "import org.springframework.jdbc.core.CallableStatementCallback;\n" +
                "import org.springframework.jdbc.core.CallableStatementCreator;\n" +
                "import org.springframework.jdbc.core.JdbcTemplate;\n" +
                "import org.springframework.stereotype.Repository;\n" +
                "\n" +
                "import java.sql.CallableStatement;\n" +
                "import java.sql.Connection;\n" +
                "import java.sql.ResultSet;\n" +
                "import java.sql.SQLException;\n" +
                "import java.util.HashMap;\n" +
                "import java.util.Map;\n" +
                "\n" +
                "/**\n" +
                " * @author: " + author + "\n" +
                " * @date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")) + "\n" +
                " * @since: 1.0\n" +
                " */\n" +
                "@Repository\n" +
                "public class " + moduleName + "Repository {\n" +
                "    @Autowired\n" +
                "    private JdbcTemplate " + packageName.substring(packageName.lastIndexOf(".") + 1).toLowerCase() + "JdbcTemplate;\n" +
                "{}" +
                "}";
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
                        repositoryParam.add("                statement.set" + dbProcedure.getRepositoryOutTypeCode(dataType) + "(\"" + value + "\", new Timestamp(" + value + ".getTime()));");
                    } else {
                        repositoryParam.add("                statement.set" + dbProcedure.getRepositoryOutTypeCode(dataType) + "(\"" + value + "\", " + value + ");");
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
                    repositoryParam.add("                statement.registerOutParameter(\"" + value + "\", " + dbProcedure.getRepositoryOutType(dataType) + ");");
                    if ("REF CURSOR".equals(dataType)) {
                        repositoryResult.add("                returnValue.put(\"" + nameConvent.getResultName(value) + "\", ProcedureUtils.ResultHash((ResultSet) cs.get" + dbProcedure.getRepositoryOutTypeCode(dataType) + "(\"" + value + "\")));");
                    } else {
                        repositoryResult.add("                returnValue.put(\"" + nameConvent.getResultName(value) + "\", cs.get" + dbProcedure.getRepositoryOutTypeCode(dataType) + "(\"" + value + "\"));");
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
                        repositoryParam.add("                statement.set" + dbProcedure.getRepositoryOutTypeCode(dataType) + "(\"" + value + "\", new Timestamp(" + value + ".getTime()));");
                    } else {
                        repositoryParam.add("                statement.set" + dbProcedure.getRepositoryOutTypeCode(dataType) + "(\"" + value + "\", " + value + ");");
                    }
                    serviceNote.add("     * @param " + value);
                    if ("REF CURSOR".equals(dataType)) {
                        repositoryResult.add("                returnValue.put(\"" + nameConvent.getResultName(value) + "\", ProcedureUtils.ResultHash((ResultSet) cs.get" + dbProcedure.getRepositoryOutTypeCode(dataType) + "(\"" + value + "\")));");
                    } else {
                        repositoryResult.add("                returnValue.put(\"" + nameConvent.getResultName(value) + "\", cs.get" + dbProcedure.getRepositoryOutTypeCode(dataType) + "(\"" + value + "\"));");
                    }
                }
            }
            //方法名称前缀
            String preName = nameConvent.getName(procedureName);
            //请求协议
            String mappingType = getMappingType(procedureName, nameConvent);
            controllerCode.append("\n" +
                    "    /**\n" +
                    "     * \n" +
                    "     */\n" +
                    "    @" + mappingType + "(\"" + preName + moduleName + "\")\n" +
                    "    @ResponseBody\n" +
                    "    public Map<String, Object> " + preName + moduleName + "(" + inParams + (inParams.length() > 0 ? ", " : "") + "HttpServletRequest request) {\n" +
                    "        return BaseUtils.success(" + BaseUtils.toLowerCase4Index(moduleName) + "Service." + preName + moduleName + "(" + outParams + "));\n" +
                    "    }\n");

            serviceCode.append("\n" +
                    "    /**\n" +
                    "     * \n" +
                    "     *\n" +
                    serviceNote +
                    "\n     * @return\n" +
                    "     */\n" +
                    "    Map<String, Object> " + preName + moduleName + "(" + inParams + ");\n");

            serviceImplCode.append("\n" +
                    "    @Override\n" +
                    "    public Map<String, Object> " + preName + moduleName + "(" + inParams + ") {\n" +
                    "        return " + BaseUtils.toLowerCase4Index(moduleName) + "Repository." + procedureName + "(" + outParams + ");\n" +
                    "    }\n");

            repositoryCode.append("\n" +
                    "    /**\n" +
                    "     * \n" +
                    "     */\n" +
                    "    public Map<String, Object> " + procedureName + "(" + inParams + ") {\n" +
                    "\n" +
                    "        return " + packageName.substring(packageName.lastIndexOf(".") + 1).toLowerCase() + "JdbcTemplate.execute(new CallableStatementCreator() {\n" +
                    "            @Override\n" +
                    "            public CallableStatement createCallableStatement(Connection con)\n" +
                    "                    throws SQLException {\n" +
                    "                String sql = \"{call " + procedureName + "(" + procedureParams + ")}\";\n" +
                    "                CallableStatement statement = con.prepareCall(sql);\n" +
                    repositoryParam +
                    "\n                return statement;\n" +
                    "            }\n" +
                    "        }, new CallableStatementCallback<Map<String, Object>>() {\n" +
                    "            @Override\n" +
                    "            public Map<String, Object> doInCallableStatement(CallableStatement cs)\n" +
                    "                    throws SQLException, DataAccessException {\n" +
                    "                cs.execute();\n" +
                    "                Map<String, Object> returnValue = new HashMap<>(8);\n" +
                    repositoryResult +
                    "\n                return returnValue;\n" +
                    "            }\n" +
                    "        });\n" +
                    "    }\n");
        }

        //tab页集合
        List<String> list = new ArrayList<>(Arrays.asList("controller", "service", "serviceImpl", "repository", "BaseUtils", "ProcedureUtils"));
        result.put("controller", StrUtil.format(getControllerCode(moduleName, packageName, author), controllerCode.toString()));
        result.put("service", StrUtil.format(getServiceCode(moduleName, packageName, author), serviceCode.toString()));
        result.put("serviceImpl", StrUtil.format(getServiceImplCode(moduleName, packageName, author), serviceImplCode.toString()));
        result.put("repository", StrUtil.format(getRepositoryCode(moduleName, packageName, author), repositoryCode.toString()));
        result.put("BaseUtils", getBaseUtils(packageName));
        result.put("ProcedureUtils", getProcedureUtils(packageName));
        result.put("list", list);
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        GenProcedureModelFactory.register(GenProcedureModelType.EAM, this);
    }
}
