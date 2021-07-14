package com.newangels.gen.controller;

import com.newangels.gen.annotation.Log;
import com.newangels.gen.base.BaseUtils;
import com.newangels.gen.enums.DataBaseType;
import com.newangels.gen.factory.DataBaseTableFactory;
import com.newangels.gen.factory.DataSourceUtilFactory;
import com.newangels.gen.service.DataBaseTableService;
import com.newangels.gen.util.DataSourceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 加载表详细信息
     */
    @GetMapping("loadTable/{name}")
    @Log
    public Map<String, Object> loadTable(@PathVariable(required = false) String name, String url, String driver, String userName, String password) {
        DataSourceUtil dataSourceUtil = DataSourceUtilFactory.getDataSourceUtil(url, driver, userName, password);
        DataBaseTableService dataBaseTable = DataBaseTableFactory.getDataBaseTable(DataBaseType.fromTypeName(driver));
        return BaseUtils.success(dataBaseTable.loadTable(name, dataSourceUtil));
    }

    /**
     * 获取数据库表数据
     */
    @GetMapping(value = {"selectTables/{name}", "selectTables/"})
    @Log
    public Map<String, Object> selectTables(@PathVariable(required = false) String name, String url, String driver, String userName, String password) {
        DataSourceUtil dataSourceUtil = DataSourceUtilFactory.getDataSourceUtil(url, driver, userName, password);
        DataBaseTableService dataBaseTable = DataBaseTableFactory.getDataBaseTable(DataBaseType.fromTypeName(driver));
        return BaseUtils.success(dataBaseTable.selectTables(name, dataSourceUtil));
    }

    /**
     * 查询数据库某表字段相关的详细信息
     */
    @GetMapping("selectTableInfo/{name}")
    @Log
    public Map<String, Object> selectTableInfo(@PathVariable(required = false) String name, String url, String driver, String userName, String password) {
        DataSourceUtil dataSourceUtil = DataSourceUtilFactory.getDataSourceUtil(url, driver, userName, password);
        DataBaseTableService dataBaseTable = DataBaseTableFactory.getDataBaseTable(DataBaseType.fromTypeName(driver));
        return BaseUtils.success(dataBaseTable.selectTableInfo(name, dataSourceUtil));
    }
}
