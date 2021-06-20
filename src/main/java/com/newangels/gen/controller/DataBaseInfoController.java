package com.newangels.gen.controller;

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
    public Map<String, Object> selectDataBaseInfo() {
        return BaseUtils.success(dataBaseInfoService.selectDataBaseInfo());
    }

    /**
     * 新增数据源
     */
    @PostMapping("insertDataBaseInfo")
    public Map<String, Object> insertDataBaseInfo(String NAME, String URL, String DRIVER, String USERNAME, String PASSWORD) {
        return BaseUtils.success(dataBaseInfoService.insertDataBaseInfo(NAME, URL, DRIVER, USERNAME, PASSWORD));
    }

    /**
     * 修改数据源
     */
    @PostMapping("updateDataBaseInfo")
    public Map<String, Object> updateDataBaseInfo(String ID, String NAME, String URL, String DRIVER, String USERNAME, String PASSWORD) {
        return BaseUtils.success(dataBaseInfoService.updateDataBaseInfo(ID, NAME, URL, DRIVER, USERNAME, PASSWORD));
    }

    /**
     * 删除数据源
     */
    @PostMapping("deleteDataBaseInfo")
    public Map<String, Object> deleteDataBaseInfo(String ID) {
        return BaseUtils.success(dataBaseInfoService.deleteDataBaseInfo(ID));
    }
}
