package com.newangels.gen.controller;

import com.newangels.gen.annotation.Log;
import com.newangels.gen.base.BaseUtils;
import com.newangels.gen.factory.DbUtilsFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 通用模块
 *
 * @author: TangLiang
 * @date: 2021/6/21 9:03
 * @since: 1.0
 */
@RestController
@RequiredArgsConstructor
public class CommonController {
    /**
     * 目录
     */
    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("pages/index");
    }

    /**
     * 清空DbUtilsFactory缓存
     */
    @PostMapping("clearDbUtilsFactory")
    @Log
    public Map<String, Object> clearDbUtilsFactory() {
        DbUtilsFactory.removeAll();
        return BaseUtils.success();
    }

}
