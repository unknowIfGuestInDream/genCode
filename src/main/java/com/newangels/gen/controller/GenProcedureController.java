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

import java.util.List;
import java.util.Map;

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
        boolean isNotExist = false;
        DBUtil dbUtil = DbUtilsFactory.getDbUtil(url);
        if (dbUtil == null) {
            isNotExist = true;
            dbUtil = (DBUtil) DBUtil.getDbUtil().clone();
            dbUtil.init(driver, url, userName, password);
        }

        //获取数据库过程sql
        DataBaseProcedureService dbProcedure = DataBaseFactory.getDataBaseProcedure(DataBaseType.fromTypeName(driver));
        String allProceduresSql = dbProcedure.selectProcedures(name);

        List<Map<String, Object>> list = dbUtil.executeQuery(allProceduresSql);

        //将dbutil注册到工厂中
        if (isNotExist) {
            DbUtilsFactory.register(url, dbUtil);
        }

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
        boolean isNotExist = false;
        DBUtil dbUtil = DbUtilsFactory.getDbUtil(url);
        if (dbUtil == null) {
            isNotExist = true;
            dbUtil = (DBUtil) DBUtil.getDbUtil().clone();
            dbUtil.init(driver, url, userName, password);
        }

        //获取数据库过程sql
        DataBaseProcedureService dbProcedure = DataBaseFactory.getDataBaseProcedure(DataBaseType.fromTypeName(driver));
        String allProceduresSql = dbProcedure.loadProcedure(name);

        List<Map<String, Object>> list = dbUtil.executeQuery(allProceduresSql);

        //将dbutil注册到工厂中
        if (isNotExist) {
            DbUtilsFactory.register(url, dbUtil);
        }

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
        moduleName = BaseUtils.toUpperCase4Index(moduleName);
        //获取数据库连接
        boolean isNotExist = false;
        DBUtil dbUtil = DbUtilsFactory.getDbUtil(url);
        if (dbUtil == null) {
            isNotExist = true;
            dbUtil = (DBUtil) DBUtil.getDbUtil().clone();
            dbUtil.init(driver, url, userName, password);
        }

        //生成代码模版
        GenProcedureModelService genProcedureModel = GenProcedureModelFactory.getGenProcedureModel(GenProcedureModelType.fromTypeName(genProcedureModelType));

        //获取命名规范
        NameConventService nameConvent = NameConventFactory.getNameConvent(NameConventType.fromTypeName(nameConventType));

        //获取数据库过程sql
        DataBaseProcedureService dbProcedure = DataBaseFactory.getDataBaseProcedure(DataBaseType.fromTypeName(driver));

        StringBuffer sb = new StringBuffer();
        for (String procedureName : procedureNameList) {
            List<Map<String, Object>> list = dbUtil.executeQuery(dbProcedure.selectArguments(userName.toUpperCase(), procedureName.toUpperCase()));

            String preName = nameConvent.getName(procedureName);
            String mappingType = nameConvent.getMappingType(procedureName);
            sb.append("\n" +
                    "    /**\n" +
                    "     * \n" +
                    "     */\n" +
                    "    @" + mappingType + "(\"" + preName + moduleName + "\")\n" +
                    "    public Map<String, Object> " + preName + moduleName + "(String V_PERCODE, String V_GUID, HttpServletRequest request) {\n" +
                    "        return BaseUtils.success(" + BaseUtils.toLowerCase4Index(moduleName) + "Service." + preName + moduleName + "(BaseUtils.getIp(request), V_PERCODE, V_GUID));\n" +
                    "    }\n");
        }
        String conCode = genProcedureModel.getControllerCode(moduleName, packageName);
        StrUtil.format(conCode, sb.toString());
        //将dbutil注册到工厂中
        if (isNotExist) {
            DbUtilsFactory.register(url, dbUtil);
        }
        return BaseUtils.success();
    }
}
