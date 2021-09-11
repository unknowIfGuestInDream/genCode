package com.newangels.gen.controller;

import com.newangels.gen.annotation.Log;
import com.newangels.gen.base.BaseUtils;
import com.newangels.gen.enums.DataBaseType;
import com.newangels.gen.enums.GenProcedureModelType;
import com.newangels.gen.enums.NameConventType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
     * 获取DataBaseType的数据
     */
    @GetMapping("selectDataBaseType")
    @Log
    public Map<String, Object> selectDataBaseType() {

        List<Map<String, Object>> list = new ArrayList<>();
        for (DataBaseType dataBaseType : DataBaseType.values()) {
            if ("UNKNOW".equals(dataBaseType.toString())) {
                continue;
            }
            Map<String, Object> result = new HashMap<>();
            result.put("CODE_", dataBaseType.getTypeName());
            result.put("NAME_", dataBaseType.toString());
            list.add(result);
        }
        return BaseUtils.success(list);
    }

    /**
     * 获取GenProcedureModelType的数据
     */
    @GetMapping("selectGenProcedureModelType")
    @Log
    public Map<String, Object> selectGenProcedureModelType() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (GenProcedureModelType genProcedureModelType : GenProcedureModelType.values()) {
            Map<String, Object> result = new HashMap<>();
            result.put("CODE_", genProcedureModelType.getCode());
            result.put("NAME_", genProcedureModelType.getDesc());
            list.add(result);
        }
        return BaseUtils.success(list);
    }

    /**
     * 获取NameConventType的数据
     */
    @GetMapping("selectNameConventType")
    @Log
    public Map<String, Object> selectNameConventType() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (NameConventType nameConventType : NameConventType.values()) {
            Map<String, Object> result = new HashMap<>();
            result.put("CODE_", nameConventType.getCode());
            result.put("NAME_", nameConventType.getDesc());
            list.add(result);
        }
        return BaseUtils.success(list);
    }

}
