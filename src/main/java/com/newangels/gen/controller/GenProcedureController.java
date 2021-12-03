package com.newangels.gen.controller;

import com.newangels.gen.annotation.Log;
import com.newangels.gen.base.BaseUtils;
import com.newangels.gen.base.CacheManage;
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
import com.newangels.gen.util.cache.Cache;
import com.newangels.gen.util.dataSource.DataSourceUtil;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
 * 存储过程代码生成
 *
 * @author: TangLiang
 * @date: 2021/6/20 10:05
 * @since: 1.0
 */
@RestController
@RequiredArgsConstructor
public class GenProcedureController {
    private final FreeMarkerConfigurer freeMarkerConfigurer;

    /**
     * 代码生成页面
     */
    @GetMapping("/manageGenerate")
    public ModelAndView manageGenerate() {
        return new ModelAndView("pages/codeGenerate/manageGenerate");
    }

    /**
     * 查询数据库中的过程信息
     *
     * @param url      数据库url 用于获取数据库连接
     * @param driver   数据库驱动 用于获取存储过程sql
     * @param userName 数据库账户
     * @param password 数据库密码
     * @param name     过程名 模糊查询
     */
    @GetMapping("selectProcedures")
    @Log
    public Map<String, Object> selectProcedures(String url, String driver, String userName, String password, @RequestParam(required = false, defaultValue = "") String name) {
        List<Map<String, Object>> list = CacheManage.PROCEDURES_CACHE.get(url.replaceAll("/", "") + userName + name + "procedures");
        //缓存方案 url+用户名+查询条件为主键
        //存储过程名称条件为空代表全查询 缓存一天，否则缓存一分钟
        if (list == null) {
            DataSourceUtil dataSourceUtil = DataSourceUtilFactory.getDataSourceUtil(url, driver, userName, password);
            DataBaseProcedureService dbProcedure = DataBaseProcedureFactory.getDataBaseProcedure(DataBaseType.fromTypeName(driver));
            list = dataSourceUtil.executeQuery(dbProcedure.selectProcedures(name));
            CacheManage.PROCEDURES_CACHE.put(url.replaceAll("/", "") + userName + name + "procedures", list, StringUtils.isEmpty(name) ? Cache.CACHE_HOLD_FOREVER : Cache.CACHE_HOLD_30MINUTE);
        }
        return BaseUtils.success(list);
    }

    /**
     * 加载过程信息
     *
     * @param url      数据库url 用于获取数据库连接
     * @param driver   数据库驱动 用于获取存储过程sql
     * @param userName 数据库账户
     * @param password 数据库密码
     * @param name     存储过程名称
     */
    @GetMapping("loadProcedureInfo")
    @Log
    public Map<String, Object> loadProcedureInfo(String url, String driver, String userName, String password, String name) {
        //获取数据库连接
        DataSourceUtil dataSourceUtil = DataSourceUtilFactory.getDataSourceUtil(url, driver, userName, password);
        //获取数据库过程sql
        DataBaseProcedureService dbProcedure = DataBaseProcedureFactory.getDataBaseProcedure(DataBaseType.fromTypeName(driver));
        String result = dbProcedure.loadProcedure(name, dataSourceUtil);
        return BaseUtils.success(result);
    }

    /**
     * 生成代码
     *
     * @param moduleName            模块名称
     * @param genProcedureModelType 生成代码模版类型
     * @param nameConventType       命名规范类型
     * @param packageName           包名
     * @param url                   数据库url 用于获取数据库连接
     * @param driver                数据库驱动 用于获取存储过程sql
     * @param userName              数据库账户
     * @param password              数据库密码
     * @param procedureNameList     存储过程名称集合
     */
    @PostMapping("genProcedure")
    @Log
    public Map<String, Object> genProcedure(String moduleName, String genProcedureModelType, String nameConventType, String packageName, String url, String driver, String userName, String password, @RequestParam("procedureNameList") List<String> procedureNameList, @RequestParam(required = false, defaultValue = "admin") String author) {
        moduleName = BaseUtils.toUpperCase4Index(moduleName);
        //获取数据库连接，为空则创建
        DataSourceUtil dataSourceUtil = DataSourceUtilFactory.getDataSourceUtil(url, driver, userName, password);
        //获取生成代码模版
        AbstractGenProcedureModel genProcedureModel = AbstractGenProcedureModelFactory.getGenProcedureModel(GenProcedureModelType.fromCode(genProcedureModelType));
        //获取命名规范
        NameConventService nameConvent = NameConventFactory.getNameConvent(NameConventType.fromCode(nameConventType));
        //获取数据库过程sql
        DataBaseProcedureService dbProcedure = DataBaseProcedureFactory.getDataBaseProcedure(DataBaseType.fromTypeName(driver));
        Map<String, Object> result = genProcedureModel.genCode(moduleName, packageName, userName, procedureNameList, author, nameConvent, dbProcedure, dataSourceUtil, freeMarkerConfigurer.getConfiguration());
        return BaseUtils.success(result);
    }

    /**
     * 生成代码下载
     */
    @GetMapping("downloadCode")
    @Log
    public void downloadCode(String moduleName, String genProcedureModelType, String nameConventType, String packageName, String url, String driver, String userName, String password, @RequestParam("procedureNameList") List<String> procedureNameList, String author, HttpServletRequest request, HttpServletResponse response) {
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
