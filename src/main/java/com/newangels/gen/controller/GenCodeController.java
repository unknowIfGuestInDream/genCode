package com.newangels.gen.controller;

import com.newangels.gen.annotation.Log;
import com.newangels.gen.base.BaseUtils;
import com.newangels.gen.enums.GenCodeModelType;
import com.newangels.gen.factory.AbstractGenCodeModelFactory;
import com.newangels.gen.service.AbstractGenCodeModel;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 后台代码生成
 *
 * @author: TangLiang
 * @date: 2021/11/7 10:05
 * @since: 1.0
 */
@RestController
@RequiredArgsConstructor
public class GenCodeController {
    private final FreeMarkerConfigurer freeMarkerConfigurer;

    /**
     * 后台代码生成页面
     */
    @GetMapping("/manageCodeByTable")
    public ModelAndView manageCodeByTable() {
        return new ModelAndView("pages/codeGenerate/manageCodeByTable");
    }

    /**
     * 生成后台代码
     *
     * @param tableName        表名
     * @param tableDesc        表描述
     * @param moduleName       模块名称
     * @param moduleDesc       模块描述
     * @param packageName      包名
     * @param hasDelBatch      是否包含批量删除
     * @param hasExport        是否包含导出接口
     * @param genCodeModelType 生成代码模版类型
     * @param driver           数据库驱动 用于获取存储过程sql
     * @param author           作者
     * @param params           表所有字段
     * @param paramJavaClass   参数对应java类
     * @param paramDescs       表所有字段的类型
     * @param priParamIndex    主键列索引
     * @param selParamsIndex   查询条件列索引
     * @param selType          查询条件类型
     * @param insParamIndex    新增列索引
     * @param updParamIndex    修改列索引
     * @param orderParamIndex  排序列索引
     * @param orderParamTypes  排序类型
     */
    @PostMapping("genCodeByTable")
    @Log
    public Map<String, Object> genCodeByTable(String tableName, String tableDesc, String moduleName, String moduleDesc, String packageName, boolean hasDelBatch, boolean hasExport, String genCodeModelType, String driver, @RequestParam(required = false, defaultValue = "admin") String author, @RequestParam("params") List<String> params, @RequestParam("paramJavaClass") List<String> paramJavaClass, @RequestParam("paramDescs") List<String> paramDescs, @RequestParam("priParamIndex") List<Integer> priParamIndex, @RequestParam(value = "selParamsIndex", required = false) List<Integer> selParamsIndex, @RequestParam(value = "selType", required = false) List<Integer> selType, @RequestParam("insParamIndex") List<Integer> insParamIndex, @RequestParam("updParamIndex") List<Integer> updParamIndex, @RequestParam(value = "orderParamIndex", required = false) List<Integer> orderParamIndex, @RequestParam(value = "orderParamTypes", required = false) List<String> orderParamTypes) {
        moduleName = BaseUtils.toUpperCase4Index(moduleName);
        //获取生成代码模版
        AbstractGenCodeModel codeModel = AbstractGenCodeModelFactory.getGenCodeModel(GenCodeModelType.fromCode(genCodeModelType));
        Map<String, Object> result = codeModel.genCodeByTable(tableName, tableDesc, moduleName, moduleDesc, packageName, author, hasDelBatch, hasExport, driver, params, paramDescs, paramJavaClass, priParamIndex, selParamsIndex, selType, insParamIndex, updParamIndex, orderParamIndex, orderParamTypes, freeMarkerConfigurer.getConfiguration());
        return BaseUtils.success(result);
    }

    /**
     * 生成代码下载
     */
    @GetMapping("downloadCodeByTable")
    @Log
    public void downloadCodeByTable(String tableName, String tableDesc, String moduleName, String moduleDesc, String packageName, boolean hasDelBatch, boolean hasExport, String genCodeModelType, String driver, @RequestParam(required = false, defaultValue = "admin") String author, @RequestParam("params") List<String> params, @RequestParam("paramJavaClass") List<String> paramJavaClass, @RequestParam("paramDescs") List<String> paramDescs, @RequestParam("priParamIndex") List<Integer> priParamIndex, @RequestParam(value = "selParamsIndex", required = false) List<Integer> selParamsIndex, @RequestParam(value = "selType", required = false) List<Integer> selType, @RequestParam("insParamIndex") List<Integer> insParamIndex, @RequestParam("updParamIndex") List<Integer> updParamIndex, @RequestParam(value = "orderParamIndex", required = false) List<Integer> orderParamIndex, @RequestParam(value = "orderParamTypes", required = false) List<String> orderParamTypes, HttpServletRequest request, HttpServletResponse response) {
        try {
            String zipName = moduleName + ".zip";
            moduleName = BaseUtils.toUpperCase4Index(moduleName);

            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment;filename=" + BaseUtils.getFormatString(request, zipName));

            ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
            //获取生成代码模版
            AbstractGenCodeModel codeModel = AbstractGenCodeModelFactory.getGenCodeModel(GenCodeModelType.fromCode(genCodeModelType));
            Map<String, Object> map = codeModel.genCodeByTable(tableName, tableDesc, moduleName, moduleDesc, packageName, author, hasDelBatch, hasExport, driver, params, paramDescs, paramJavaClass, priParamIndex, selParamsIndex, selType, insParamIndex, updParamIndex, orderParamIndex, orderParamTypes, freeMarkerConfigurer.getConfiguration());
            List<String> list = (List<String>) map.get("list");
            //BaseUtils，BaseSqlCriteria不下载，公用后台代码补充.java后缀，其他自定义文件名称无操作
            for (String name : list) {
                String fileName;
                if ("BaseUtils".equals(name) || "BaseSqlCriteria".equals(name)) {
                    continue;
                } else if ("controller".equals(name) || "service".equals(name) || "serviceImpl".equals(name)) {
                    fileName = moduleName + BaseUtils.toUpperCase4Index(name) + ".java";
                } else {
                    fileName = name;
                }
                @Cleanup InputStream inputStream = new ByteArrayInputStream(map.get(name).toString().getBytes(StandardCharsets.UTF_8));
                //创建输入流读取文件
                @Cleanup BufferedInputStream bis = new BufferedInputStream(inputStream);
                //将文件写入zip内，即将文件进行打包
                zos.putNextEntry(new ZipEntry(fileName));
                //写入文件的方法，同上
                int size = 0;
                byte[] buffer = new byte[4096];
                //设置读取数据缓存大小
                while ((size = bis.read(buffer)) > 0) {
                    zos.write(buffer, 0, size);
                }
                //关闭输入输出流
                zos.closeEntry();
            }
            zos.close();
        } catch (IOException e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=utf-8");
            @Cleanup PrintWriter out = null;
            try {
                out = response.getWriter();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            out.print("<span style=\"display:block;text-align: center;margin:0 auto;min-width: 150px;\">" + e.getMessage() + "</span><br/>");
            out.print("<br/><button autocomplete=\"off\" onclick=\"javascript:window.history.back(-1);return false;\" autofocus=\"true\"\n" +
                    "            style=\"display:block;margin:0 auto;min-width: 150px;background-color:rgb(0, 138, 203);color: rgb(255, 255, 255);\">\n" +
                    "        返回上一个页面\n" +
                    "    </button>");
            out.flush();
        }
    }
}
