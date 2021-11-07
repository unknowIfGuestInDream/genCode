package com.newangels.gen.controller;

import com.newangels.gen.annotation.Log;
import com.newangels.gen.base.BaseUtils;
import com.newangels.gen.enums.DataBaseType;
import com.newangels.gen.enums.GenProcedureModelType;
import com.newangels.gen.enums.NameConventType;
import com.newangels.gen.factory.AbstractGenProcedureModelFactory;
import com.newangels.gen.factory.DataBaseProcedureFactory;
import com.newangels.gen.factory.DataSourceUtilFactory;
import com.newangels.gen.factory.NameConventFactory;
import com.newangels.gen.service.AbstractGenProcedureModel;
import com.newangels.gen.service.DataBaseProcedureService;
import com.newangels.gen.service.NameConventService;
import com.newangels.gen.util.dataSource.DataSourceUtil;
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
     * //TODO
     *
     * @param moduleName            模块名称
     * @param genProcedureModelType 生成代码模版类型
     * @param nameConventType       命名规范类型
     * @param packageName           包名
     * @param url                   数据库url 用于获取数据库连接
     * @param driver                数据库驱动 用于获取存储过程sql
     * @param userName              数据库账户
     * @param password              数据库密码
     * @param author                作者
     * @param tableName             表名
     * @param tableDesc             表描述
     * @param params                表所有字段
     * @param paramTypes            表所有字段的类型
     * @param paramDescs            表所有字段的类型
     * @param priParamIndex         主键列索引
     * @param selParamsIndex        查询条件列索引
     * @param selType               查询条件类型
     * @param insParamIndex         新增列索引
     * @param updParamIndex         修改列索引
     */
    @PostMapping("genCodeByTable")
    @Log
    public Map<String, Object> genCodeByTable(String moduleName, String genProcedureModelType, String nameConventType, String packageName, String url, String driver, String userName, String password, @RequestParam(required = false, defaultValue = "admin") String author, String tableName, String tableDesc, @RequestParam("params") List<String> params, @RequestParam("paramTypes") List<String> paramTypes, @RequestParam("paramDescs") List<String> paramDescs, @RequestParam("priParamIndex") List<Integer> priParamIndex, @RequestParam("selParamsIndex") List<Integer> selParamsIndex, @RequestParam("selType") List<Integer> selType, @RequestParam("insParamIndex") List<Integer> insParamIndex, @RequestParam("updParamIndex") List<Integer> updParamIndex) {
        moduleName = BaseUtils.toUpperCase4Index(moduleName);
        //获取数据库连接，为空则创建
        DataSourceUtil dataSourceUtil = DataSourceUtilFactory.getDataSourceUtil(url, driver, userName, password);
        //获取生成代码模版
        AbstractGenProcedureModel genProcedureModel = AbstractGenProcedureModelFactory.getGenProcedureModel(GenProcedureModelType.fromCode(genProcedureModelType));
        //获取命名规范
        NameConventService nameConvent = NameConventFactory.getNameConvent(NameConventType.fromCode(nameConventType));
        //获取数据库过程sql
        DataBaseProcedureService dbProcedure = DataBaseProcedureFactory.getDataBaseProcedure(DataBaseType.fromTypeName(driver));
        Map<String, Object> result = genProcedureModel.genCode(moduleName, packageName, userName, null, author, nameConvent, dbProcedure, dataSourceUtil, freeMarkerConfigurer.getConfiguration());
        return BaseUtils.success(result);
    }

    /**
     * 生成代码下载
     * //TODO
     */
    @GetMapping("downloadCodeByTable")
    @Log
    public void downloadCodeByTable(String moduleName, String genProcedureModelType, String nameConventType, String packageName, String url, String driver, String userName, String password, @RequestParam("procedureNameList") List<String> procedureNameList, String author, HttpServletRequest request, HttpServletResponse response) {
        try {
            String zipName = moduleName + ".zip";
            moduleName = BaseUtils.toUpperCase4Index(moduleName);
            //获取数据库连接，为空则创建
            DataSourceUtil dataSourceUtil = DataSourceUtilFactory.getDataSourceUtil(url, driver, userName, password);
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment;filename=" + BaseUtils.getFormatString(request, zipName));

            ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
            //获取生成代码模版
            AbstractGenProcedureModel genProcedureModel = AbstractGenProcedureModelFactory.getGenProcedureModel(GenProcedureModelType.fromCode(genProcedureModelType));
            //获取命名规范
            NameConventService nameConvent = NameConventFactory.getNameConvent(NameConventType.fromCode(nameConventType));
            //获取数据库过程sql
            DataBaseProcedureService dbProcedure = DataBaseProcedureFactory.getDataBaseProcedure(DataBaseType.fromTypeName(driver));
            Map<String, Object> map = genProcedureModel.genCode(moduleName, packageName, userName, procedureNameList, author, nameConvent, dbProcedure, dataSourceUtil, freeMarkerConfigurer.getConfiguration());
            List<String> list = (List<String>) map.get("list");

            for (String name : list) {
                String fileName;
                if ("BaseUtils".equals(name) || "ProcedureUtils".equals(name)) {
                    //fileName = name + ".java";
                    continue;
                } else {
                    fileName = moduleName + BaseUtils.toUpperCase4Index(name) + ".java";
                }
                @Cleanup InputStream inputStream = new ByteArrayInputStream(map.get(name).toString().getBytes("UTF-8"));
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
