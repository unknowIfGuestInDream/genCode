package com.newangels.gen.controller;

import com.newangels.gen.annotation.Log;
import com.newangels.gen.base.BaseUtils;
import com.newangels.gen.service.DataBaseInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 数据源管理
 *
 * @author: TangLiang
 * @date: 2021/6/20 0:16
 * @since: 1.0
 */
@RestController
@RequiredArgsConstructor
public class DataBaseInfoController {
    private final DataBaseInfoService dataBaseInfoService;

    /**
     * 查询数据源
     */
    @GetMapping("selectDataBaseInfo")
    @Log
    public Map<String, Object> selectDataBaseInfo() {
        return BaseUtils.success(dataBaseInfoService.selectDataBaseInfo());
    }

    /**
     * 新增数据源
     */
    @PostMapping("insertDataBaseInfo")
    @Log
    public Map<String, Object> insertDataBaseInfo(String name, String url, String driver, String userName, String password) {
        return BaseUtils.success(dataBaseInfoService.insertDataBaseInfo(name, url, driver, userName, password));
    }

    /**
     * 修改数据源
     */
    @PostMapping("updateDataBaseInfo")
    @Log
    public Map<String, Object> updateDataBaseInfo(String id, String name, String url, String driver, String userName, String password) {
        return BaseUtils.success(dataBaseInfoService.updateDataBaseInfo(id, name, url, driver, userName, password));
    }

    /**
     * 删除数据源
     */
    @PostMapping("deleteDataBaseInfo")
    @Log
    public Map<String, Object> deleteDataBaseInfo(String id) {
        return BaseUtils.success(dataBaseInfoService.deleteDataBaseInfo(id));
    }
}
