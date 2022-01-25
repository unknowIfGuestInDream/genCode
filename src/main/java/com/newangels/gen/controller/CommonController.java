package com.newangels.gen.controller;

import cn.smallbun.screw.core.engine.EngineFileType;
import com.newangels.gen.annotation.Log;
import com.newangels.gen.base.BaseUtils;
import com.newangels.gen.enums.*;
import com.newangels.gen.util.cache.SimpleCache;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
        List<Map<String, Object>> list = SimpleCache.get(SimpleCache.DATABASETYPE);
        if (list == null) {
            list = new ArrayList<>();
            for (DataBaseType dataBaseType : DataBaseType.values()) {
                if (DataBaseType.UNKNOW == dataBaseType) {
                    continue;
                }
                Map<String, Object> result = new HashMap<>(4);
                result.put("CODE_", dataBaseType.getTypeName());
                result.put("NAME_", dataBaseType.toString());
                list.add(result);
            }
            SimpleCache.put(SimpleCache.DATABASETYPE, list);
        }
        return BaseUtils.success(list);
    }

    /**
     * 获取GenProcedureModelType的数据
     */
    @GetMapping("selectGenProcedureModelType")
    @Log
    public Map<String, Object> selectGenProcedureModelType() {
        List<Map<String, Object>> list = SimpleCache.get(SimpleCache.GENPROCEDUREMODELTYPE);
        if (list == null) {
            list = new ArrayList<>();
            for (GenProcedureModelType genProcedureModelType : GenProcedureModelType.values()) {
                Map<String, Object> result = new HashMap<>(4);
                result.put("CODE_", genProcedureModelType.getCode());
                result.put("NAME_", genProcedureModelType.getDesc());
                list.add(result);
            }
            SimpleCache.put(SimpleCache.GENPROCEDUREMODELTYPE, list);
        }
        return BaseUtils.success(list);
    }

    /**
     * 获取GenCodeModelType的数据
     */
    @GetMapping("selectGenCodeModelType")
    @Log
    public Map<String, Object> selectGenCodeModelType() {
        List<Map<String, Object>> list = SimpleCache.get(SimpleCache.GENCODEMODELTYPE);
        if (list == null) {
            list = new ArrayList<>();
            for (GenCodeModelType genCodeModelType : GenCodeModelType.values()) {
                Map<String, Object> result = new HashMap<>(4);
                result.put("CODE_", genCodeModelType.getCode());
                result.put("NAME_", genCodeModelType.getDesc());
                list.add(result);
            }
            SimpleCache.put(SimpleCache.GENCODEMODELTYPE, list);
        }
        return BaseUtils.success(list);
    }

    /**
     * 获取NameConventType的数据
     */
    @GetMapping("selectNameConventType")
    @Log
    public Map<String, Object> selectNameConventType() {
        List<Map<String, Object>> list = SimpleCache.get(SimpleCache.NAMECONVENTTYPE);
        if (list == null) {
            list = new ArrayList<>();
            for (NameConventType nameConventType : NameConventType.values()) {
                Map<String, Object> result = new HashMap<>(4);
                result.put("CODE_", nameConventType.getCode());
                result.put("NAME_", nameConventType.getDesc());
                list.add(result);
            }
            SimpleCache.put(SimpleCache.NAMECONVENTTYPE, list);
        }
        return BaseUtils.success(list);
    }

    /**
     * 获取JavaClass的数据
     */
    @GetMapping("selectJavaClass")
    @Log
    public Map<String, Object> selectJavaClass() {
        List<Map<String, Object>> list = SimpleCache.get(SimpleCache.JAVACLASS);
        if (list == null) {
            list = new ArrayList<>();
            for (JavaClass javaClass : JavaClass.values()) {
                Map<String, Object> result = new HashMap<>(4);
                result.put("CODE_", javaClass.getCode());
                result.put("NAME_", javaClass.toString());
                list.add(result);
            }
            SimpleCache.put(SimpleCache.JAVACLASS, list);
        }
        return BaseUtils.success(list);
    }

    /**
     * 获取EngineFileType的数据
     */
    @GetMapping("selectEngineFileType")
    @Log
    public Map<String, Object> selectEngineFileType() {
        List<Map<String, Object>> list = SimpleCache.get(SimpleCache.ENGINEFILETYPE);
        if (list == null) {
            list = new ArrayList<>();
            for (EngineFileType engineFileType : EngineFileType.values()) {
                Map<String, Object> result = new HashMap<>(4);
                result.put("CODE_", engineFileType.toString());
                result.put("NAME_", engineFileType.getDesc());
                list.add(result);
            }
            SimpleCache.put(SimpleCache.ENGINEFILETYPE, list);
        }
        return BaseUtils.success(list);
    }

    /**
     * 获取file文件夹下的文件
     */
    @GetMapping("downloadFile/{fileName}")
    @Log
    public void downloadFile(@PathVariable String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //为空返回
        if (StringUtils.isEmpty(fileName) || "favicon.ico".equals(fileName)) {
            return;
        }
        Resource resource = new ClassPathResource("file/" + fileName);
        if (!resource.exists()) {
            throw new FileNotFoundException("文件未找到");
        }
        InputStream inputStream = resource.getInputStream();
        BaseUtils.download(inputStream, fileName, request, response);

    }

}
