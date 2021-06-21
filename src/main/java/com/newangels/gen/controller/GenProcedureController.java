package com.newangels.gen.controller;

import com.newangels.gen.base.BaseUtils;
import com.newangels.gen.factory.DataBaseFactory;
import com.newangels.gen.factory.DbUtilsFactory;
import com.newangels.gen.factory.NameConventFactory;
import com.newangels.gen.service.DataBaseProcedureService;
import com.newangels.gen.service.NameConventService;
import com.newangels.gen.util.DBUtil;
import com.newangels.gen.util.DataBaseType;
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
     */
    @GetMapping("selectProcedures")
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
     */
    @GetMapping("loadProcedureInfo")
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

        return BaseUtils.success(list);
    }

    /**
     * 生成代码
     *
     * @param url               数据库url 用于获取数据库连接
     * @param driver            数据库驱动 用于获取存储过程sql
     * @param nameConventType   命名规范类型
     * @param procedureNameList 存储过程名称集合
     */
    @PostMapping("genProcedure")
    public Map<String, Object> genProcedure(String url, String driver, String userName, String password, String nameConventType, @RequestParam("procedureNameList") List<String> procedureNameList) {
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

        //获取命名规范
        NameConventService nameConvent = NameConventFactory.getNameConvent(NameConventType.fromTypeName(nameConventType));

        //将dbutil注册到工厂中
        if (isNotExist) {
            DbUtilsFactory.register(url, dbUtil);
        }
        return BaseUtils.success();
    }
}
