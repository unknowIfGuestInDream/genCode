package com.newangels.gen.controller;

import cn.hutool.core.util.StrUtil;
import com.newangels.gen.annotation.Log;
import com.newangels.gen.base.BaseUtils;
import com.newangels.gen.factory.DataBaseFactory;
import com.newangels.gen.factory.DbUtilsFactory;
import com.newangels.gen.factory.GenProcedureModelFactory;
import com.newangels.gen.factory.NameConventFactory;
import com.newangels.gen.service.DataBaseProcedureService;
import com.newangels.gen.service.GenProcedureModelService;
import com.newangels.gen.service.NameConventService;
import com.newangels.gen.util.DBUtil;
import com.newangels.gen.util.DataBaseType;
import com.newangels.gen.util.GenProcedureModelType;
import com.newangels.gen.util.NameConventType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 存储过程代码生成
 *
 * @author: TangLiang
 * @date: 2021/6/20 10:05
 * @since: 1.0
 */
@RestController
@RequiredArgsConstructor
public class GenProcedureController {

    /**
     * 代码生成页面
     */
    @GetMapping("/manageGenerate")
    public ModelAndView manageGenerate() {
        return new ModelAndView("pages/manageGenerate");
    }

    /**
     * 查询数据库中的过程信息
     *
     * @param url      数据库url 用于获取数据库连接
     * @param driver   数据库驱动 用于获取存储过程sql
     * @param userName 数据库账户
     * @param password 数据库密码
     * @param name     过程名 模糊查询
     */
    @GetMapping("selectProcedures")
    @Log
    public Map<String, Object> selectProcedures(String url, String driver, String userName, String password, String name) {
        //获取数据库连接
        DBUtil dbUtil = DbUtilsFactory.getDbUtil(url);
        if (dbUtil == null) {
            dbUtil = (DBUtil) DBUtil.getDbUtil().clone();
            dbUtil.init(driver, url, userName, password);
            DbUtilsFactory.register(url, dbUtil);
        }
        //获取数据库过程sql
        DataBaseProcedureService dbProcedure = DataBaseFactory.getDataBaseProcedure(DataBaseType.fromTypeName(driver));
        String allProceduresSql = dbProcedure.selectProcedures(name);
        //执行sql
        List<Map<String, Object>> list = dbUtil.executeQuery(allProceduresSql);

        return BaseUtils.success(list);
    }

    /**
     * 加载过程信息
     *
     * @param url      数据库url 用于获取数据库连接
     * @param driver   数据库驱动 用于获取存储过程sql
     * @param userName 数据库账户
     * @param password 数据库密码
     * @param name     存储过程名称
     */
    @GetMapping("loadProcedureInfo")
    @Log
    public Map<String, Object> loadProcedureInfo(String url, String driver, String userName, String password, String name) {
        //获取数据库连接
        DBUtil dbUtil = DbUtilsFactory.getDbUtil(url);
        if (dbUtil == null) {
            dbUtil = (DBUtil) DBUtil.getDbUtil().clone();
            dbUtil.init(driver, url, userName, password);
            DbUtilsFactory.register(url, dbUtil);
        }
        //获取数据库过程sql
        DataBaseProcedureService dbProcedure = DataBaseFactory.getDataBaseProcedure(DataBaseType.fromTypeName(driver));
        String allProceduresSql = dbProcedure.loadProcedure(name);
        //执行sql
        List<Map<String, Object>> list = dbUtil.executeQuery(allProceduresSql);
        //获取结果集
        StringBuffer sb = new StringBuffer();
        list.forEach(l -> sb.append(l.get("TEXT")));

        return BaseUtils.success(sb.toString());
    }

    /**
     * 生成代码
     *
     * @param moduleName            模块名称
     * @param genProcedureModelType 生成代码模版类型
     * @param nameConventType       命名规范类型
     * @param packageName           包名
     * @param url                   数据库url 用于获取数据库连接
     * @param driver                数据库驱动 用于获取存储过程sql
     * @param userName              数据库账户
     * @param password              数据库密码
     * @param procedureNameList     存储过程名称集合
     */
    @PostMapping("genProcedure")
    @Log
    public Map<String, Object> genProcedure(String moduleName, String genProcedureModelType, String nameConventType, String packageName, String url, String driver, String userName, String password, @RequestParam("procedureNameList") List<String> procedureNameList) {
        Map<String, Object> result = new HashMap<>(16);
        moduleName = BaseUtils.toUpperCase4Index(moduleName);
        //获取数据库连接，为空则创建
        DBUtil dbUtil = DbUtilsFactory.getDbUtil(url);
        if (dbUtil == null) {
            dbUtil = (DBUtil) DBUtil.getDbUtil().clone();
            dbUtil.init(driver, url, userName, password);
            DbUtilsFactory.register(url, dbUtil);
        }
        //获取生成代码模版
        GenProcedureModelService genProcedureModel = GenProcedureModelFactory.getGenProcedureModel(GenProcedureModelType.fromTypeName(genProcedureModelType));
        //获取命名规范
        NameConventService nameConvent = NameConventFactory.getNameConvent(NameConventType.fromTypeName(nameConventType));
        //获取数据库过程sql
        DataBaseProcedureService dbProcedure = DataBaseFactory.getDataBaseProcedure(DataBaseType.fromTypeName(driver));
        //各层代码
        StringBuffer controllerCode = new StringBuffer();
        StringBuffer serviceCode = new StringBuffer();
        StringBuffer serviceImplCode = new StringBuffer();
        StringBuffer repositoryCode = new StringBuffer();
        //循环存储过程
        for (String procedureName : procedureNameList) {
            List<Map<String, Object>> list = dbUtil.executeQuery(dbProcedure.selectArguments(userName.toUpperCase(), procedureName.toUpperCase()));
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
                if ("IN".equals(map.get("IN_OUT"))) {
                    //存储过程中传参去掉V_
                    String value = map.get("ARGUMENT_NAME").toString().replaceFirst("V_", "");
                    if (map.get("ARGUMENT_NAME").toString().startsWith("I_I_")) {
                        value = map.get("ARGUMENT_NAME").toString().replaceFirst("I_", "");
                    }
                    inParams.add(dbProcedure.getJavaClass(map.get("DATA_TYPE").toString()) + " " + value);
                    outParams.add(value);
                    procedureParams.add(":" + value + "");
                    repositoryParam.add("                statement.setString(\"" + value + "\", " + value + ");");
                    serviceNote.add("     * @param " + value);
                } else {
                    //参数名
                    String value = map.get("ARGUMENT_NAME").toString();
                    //参数类型
                    String dataType = map.get("DATA_TYPE").toString();
                    procedureParams.add(":" + value + "");
                    repositoryParam.add("                statement.registerOutParameter(\"" + value + "\", " + dbProcedure.getRepositoryOutType(dataType) + ");");
                    repositoryResult.add("                returnValue.put(\"" + nameConvent.getResultName(value) + "\", cs.get" + dbProcedure.getRepositoryOutTypeCode(dataType) + "(\"" + value + "\"));");
                }
            }
            //方法名称前缀
            String preName = nameConvent.getName(procedureName);
            //请求协议
            String mappingType = nameConvent.getMappingType(procedureName);
            controllerCode.append("\n" +
                    "    /**\n" +
                    "     * \n" +
                    "     */\n" +
                    "    @" + mappingType + "(\"" + preName + moduleName + "\")\n" +
                    "    public Map<String, Object> " + preName + moduleName + "(" + inParams + ", HttpServletRequest request) {\n" +
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
                    "        return budgetJdbcTemplate.execute(new CallableStatementCreator() {\n" +
                    "            @Override\n" +
                    "            public CallableStatement createCallableStatement(Connection con)\n" +
                    "                    throws SQLException {\n" +
                    "                String sql = \"{call " + procedureName + "(" + procedureParams + ")}\";\n" +
                    "                CallableStatement statement = con.prepareCall(sql);\n" +
                    repositoryParam +
                    "\n                return statement;\n" +
                    "            }\n" +
                    "        }, new CallableStatementCallback<Map<String, Object>>() {\n" +
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
        //结果集
        result.put("controller", StrUtil.format(genProcedureModel.getControllerCode(moduleName, packageName), controllerCode.toString()));
        result.put("service", StrUtil.format(genProcedureModel.getServiceCode(moduleName, packageName), serviceCode.toString()));
        result.put("serviceImpl", StrUtil.format(genProcedureModel.getServiceImplCode(moduleName, packageName), serviceImplCode.toString()));
        result.put("repository", StrUtil.format(genProcedureModel.getRepositoryCode(moduleName, packageName), repositoryCode.toString()));
        result.put("BaseUtils", genProcedureModel.getBaseUtils(packageName));
        result.put("ProcedureUtils", genProcedureModel.getProcedureUtils(packageName));
        return BaseUtils.success(result);
    }
}
