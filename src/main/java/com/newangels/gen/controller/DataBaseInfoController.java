package com.newangels.gen.controller;

import com.newangels.gen.base.BaseUtils;
import com.newangels.gen.service.DataBaseInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
}
