package com.newangels.gen.controller;

import com.newangels.gen.annotation.Log;
import com.newangels.gen.base.BaseUtils;
import com.newangels.gen.factory.DbUtilsFactory;
import com.newangels.gen.service.DataBaseInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
     * 管理数据源
     */
    @GetMapping(value = "manageDataBaseInfo")
    public ModelAndView manageDataBaseInfo() {
        return new ModelAndView("pages/dataBaseInfo/manageDataBaseInfo");
    }

    /**
     * 新增数据源
     */
    @GetMapping(value = "preInsertDataBaseInfo")
    public ModelAndView preInsertDataBaseInfo() {
        return new ModelAndView("pages/dataBaseInfo/preInsertDataBaseInfo");
    }

    /**
     * 修改数据源
     */
    @GetMapping(value = "preUpdateDataBaseInfo")
    public ModelAndView preUpdateDataBaseInfo() {
        return new ModelAndView("pages/dataBaseInfo/preUpdateDataBaseInfo");
    }

    /**
     * 查询数据源
     */
    @GetMapping("selectDataBaseInfo")
    @Log
    public Map<String, Object> selectDataBaseInfo() {
        return BaseUtils.success(dataBaseInfoService.selectDataBaseInfo());
    }

    /**
     * 加载数据源
     */
    @GetMapping("loadDataBaseInfo")
    @Log
    public Map<String, Object> loadDataBaseInfo(String ID) {
        return BaseUtils.success(dataBaseInfoService.loadDataBaseInfo(ID));
    }


    /**
     * 新增数据源
     */
    @PostMapping("insertDataBaseInfo")
    @Log
    public Map<String, Object> insertDataBaseInfo(String NAME, String URL, String DRIVER, String USERNAME, String PASSWORD) {
        return BaseUtils.success(dataBaseInfoService.insertDataBaseInfo(NAME, URL, DRIVER, USERNAME, PASSWORD));
    }

    /**
     * 修改数据源
     */
    @PostMapping("updateDataBaseInfo")
    @Log
    public Map<String, Object> updateDataBaseInfo(String ID, String NAME, String URL, String DRIVER, String USERNAME, String PASSWORD) {
        DbUtilsFactory.remove(URL);
        return BaseUtils.success(dataBaseInfoService.updateDataBaseInfo(ID, NAME, URL, DRIVER, USERNAME, PASSWORD));
    }

    /**
     * 删除数据源
     */
    @PostMapping("deleteDataBaseInfo")
    @Log
    public Map<String, Object> deleteDataBaseInfo(String ID, String URL) {
        DbUtilsFactory.remove(URL);
        return BaseUtils.success(dataBaseInfoService.deleteDataBaseInfo(ID));
    }
}
