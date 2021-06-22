package com.newangels.gen.controller;

import com.newangels.gen.annotation.Log;
import com.newangels.gen.base.BaseUtils;
import com.newangels.gen.enums.DataBaseType;
import com.newangels.gen.enums.GenProcedureModelType;
import com.newangels.gen.enums.NameConventType;
import com.newangels.gen.factory.DataBaseFactory;
import com.newangels.gen.factory.DbUtilsFactory;
import com.newangels.gen.factory.GenProcedureModelFactory;
import com.newangels.gen.factory.NameConventFactory;
import com.newangels.gen.service.DataBaseProcedureService;
import com.newangels.gen.service.GenProcedureModelService;
import com.newangels.gen.service.NameConventService;
import com.newangels.gen.util.DBUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
     * 代码生成页面
     */
    @GetMapping("/manageGenerate")
    public ModelAndView manageGenerate() {
        return new ModelAndView("pages/codeGenerate/manageGenerate");
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

        return BaseUtils.success(sb.toString().replaceAll("\n","</br>"));
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

        return BaseUtils.success(genProcedureModel.genCode(moduleName, packageName, userName, procedureNameList, nameConvent, dbProcedure, dbUtil));
    }
}
