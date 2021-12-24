package com.newangels.gen.controller;

import com.newangels.gen.annotation.Log;
import com.newangels.gen.base.BaseUtils;
import com.newangels.gen.base.CacheManage;
import com.newangels.gen.enums.DataBaseType;
import com.newangels.gen.enums.NameConventType;
import com.newangels.gen.factory.AbstractTableToProcedureFactory;
import com.newangels.gen.factory.DataBaseTableFactory;
import com.newangels.gen.factory.DataSourceUtilFactory;
import com.newangels.gen.factory.NameConventFactory;
import com.newangels.gen.service.AbstractTableToProcedure;
import com.newangels.gen.service.DataBaseTableService;
import com.newangels.gen.service.NameConventService;
import com.newangels.gen.util.cache.Cache;
import com.newangels.gen.util.dataSource.DataSourceUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.List;
import java.util.Map;

/**
 * 数据库表
 *
 * @author: TangLiang
 * @date: 2021/7/14 13:48
 * @since: 1.0
 */
@RestController
@RequiredArgsConstructor
public class DataBaseTableController {
    private final FreeMarkerConfigurer freeMarkerConfigurer;

    /**
     * 根据表生成存储过程
     */
    @GetMapping("/manageGenerateByTable")
    public ModelAndView manageGenerateByTable() {
        return new ModelAndView("pages/dataBaseTable/manageGenerateByTable");
    }

    /**
     * 表详细信息
     */
    @GetMapping("/viewTableDetailedInfo")
    public ModelAndView viewTableDetailedInfo() {
        return new ModelAndView("pages/dataBaseTable/viewTableDetailedInfo");
    }

    /**
     * 加载表详细信息
     *
     * @param name     表名(精确查询)
     * @param url      数据库url 用于获取数据库连接
     * @param driver   数据库驱动 用于获取存储过程sql
     * @param userName 数据库账户
     * @param password 数据库密码
     */
    @GetMapping("loadTable")
    @Log
    public Map<String, Object> loadTable(String name, String url, String driver, String userName, String password) {
        DataSourceUtil dataSourceUtil = DataSourceUtilFactory.getDataSourceUtil(url, driver, userName, password);
        DataBaseTableService dataBaseTable = DataBaseTableFactory.getDataBaseTable(DataBaseType.fromTypeName(driver));
        return BaseUtils.success(dataBaseTable.loadTable(name, dataSourceUtil));
    }

    /**
     * 获取数据库表数据
     *
     * @param name     表名(模糊查询)
     * @param url      数据库url 用于获取数据库连接
     * @param driver   数据库驱动 用于获取存储过程sql
     * @param userName 数据库账户
     * @param password 数据库密码
     */
    @GetMapping("selectTables")
    @Log
    public Map<String, Object> selectTables(@RequestParam(required = false, defaultValue = "") String name, String schema, String url, String driver, String userName, String password) {
        List<Map<String, Object>> list = CacheManage.TABLES_CACHE.get(url.replaceAll("/", "") + userName + name + "tables");
        //缓存方案 url+用户名 + 查询条件为主键
        //存储过程名称条件为空代表全查询一直缓存，否则30分钟
        if (list == null) {
            DataSourceUtil dataSourceUtil = DataSourceUtilFactory.getDataSourceUtil(url, driver, userName, password);
            DataBaseTableService dataBaseTable = DataBaseTableFactory.getDataBaseTable(DataBaseType.fromTypeName(driver));
            list = dataBaseTable.selectTables(name, schema, dataSourceUtil);
            CacheManage.TABLES_CACHE.put(url.replaceAll("/", "") + userName + name + "tables", list, StringUtils.isEmpty(name) ? Cache.CACHE_HOLD_FOREVER : Cache.CACHE_HOLD_30MINUTE);
        }
        return BaseUtils.success(list);
    }

    /**
     * 查询数据库某表字段相关的详细信息
     *
     * @param name     表名(精确查询)
     * @param url      数据库url 用于获取数据库连接
     * @param driver   数据库驱动 用于获取存储过程sql
     * @param userName 数据库账户
     * @param password 数据库密码
     */
    @GetMapping("selectTableInfo")
    @Log
    public Map<String, Object> selectTableInfo(String name, String schema, String url, String driver, String userName, String password) {
        DataSourceUtil dataSourceUtil = DataSourceUtilFactory.getDataSourceUtil(url, driver, userName, password);
        DataBaseTableService dataBaseTable = DataBaseTableFactory.getDataBaseTable(DataBaseType.fromTypeName(driver));
        return BaseUtils.success(dataBaseTable.selectTableInfo(name, schema, dataSourceUtil));
    }

    /**
     * 根据数据库表生成存储过程
     *
     * @param nameConventType 命名规范
     * @param driver          数据库驱动, 用于判断数据库类型
     * @param tableName       表名
     * @param tableDesc       表描述
     * @param params          表所有字段
     * @param paramTypes      表所有字段的类型
     * @param paramDescs      表所有字段的类型
     * @param priParamIndex   主键列索引
     * @param selParamsIndex  查询条件列索引
     * @param selType         查询条件类型
     * @param insParamIndex   新增列索引
     * @param updParamIndex   修改列索引
     * @param orderParamIndex 排序列索引
     * @param orderParamTypes 排序类型
     */
    @PostMapping("genProceduresByTable")
    @Log
    public Map<String, Object> genProceduresByTable(String nameConventType, String driver, String tableName, String tableDesc, @RequestParam("params") List<String> params, @RequestParam("paramTypes") List<String> paramTypes, @RequestParam("paramDescs") List<String> paramDescs, @RequestParam("priParamIndex") List<Integer> priParamIndex, @RequestParam(value = "selParamsIndex", required = false) List<Integer> selParamsIndex, @RequestParam(value = "selType", required = false) List<Integer> selType, @RequestParam("insParamIndex") List<Integer> insParamIndex, @RequestParam("updParamIndex") List<Integer> updParamIndex, @RequestParam(value = "orderParamIndex", required = false) List<Integer> orderParamIndex, @RequestParam(value = "orderParamTypes", required = false) List<String> orderParamTypes) {
        //获取命名规范
        NameConventService nameConvent = NameConventFactory.getNameConvent(NameConventType.fromCode(nameConventType));
        //获取表生成存储过程实现类
        AbstractTableToProcedure tableToProcedure = AbstractTableToProcedureFactory.getTableToProcedure(DataBaseType.fromTypeName(driver));
        return BaseUtils.success(tableToProcedure.genProceduresByTable(tableName, tableDesc, params, paramTypes, paramDescs, priParamIndex, selParamsIndex, selType, insParamIndex, updParamIndex, orderParamIndex, orderParamTypes, nameConvent, freeMarkerConfigurer.getConfiguration()));
    }

    /**
     * 生成主键id的sql
     */
    @PostMapping("genAutoInsKey")
    @Log
    public Map<String, Object> genAutoInsKey(String tableName, String primaryKey, String driver) {
        AbstractTableToProcedure tableToProcedure = AbstractTableToProcedureFactory.getTableToProcedure(DataBaseType.fromTypeName(driver));
        return BaseUtils.success(tableToProcedure.genAutoInsKey(tableName, primaryKey, freeMarkerConfigurer.getConfiguration()));
    }
}
