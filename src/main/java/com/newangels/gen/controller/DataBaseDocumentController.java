package com.newangels.gen.controller;

import cn.smallbun.screw.core.engine.EngineFileType;
import com.newangels.gen.annotation.Log;
import com.newangels.gen.base.BaseUtils;
import com.newangels.gen.service.DataBaseDocumentService;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 数据库文档生成
 *
 * @author: TangLiang
 * @date: 2021/10/8 13:45
 * @since: 1.0
 */
@RestController
@RequiredArgsConstructor
public class DataBaseDocumentController {
    private final DataBaseDocumentService dataBaseDocumentService;

    /**
     * 数据库文档生成
     */
    @GetMapping("/preGenDataBaseDocument")
    public ModelAndView preGenDataBaseDocument() {
        return new ModelAndView("pages/dataBaseInfo/preGenDataBaseDocument");
    }

    /**
     * 生成数据库文档
     */
    @GetMapping("genDataBaseDocument")
    @Log
    public void genDataBaseDocument(String url, String driver, String userName, String password, @RequestParam(required = false, defaultValue = "1.0.0") String version, String description, @RequestParam(required = false, defaultValue = "数据库文档") String fileName, @RequestParam List<String> engineFileTypes, @RequestParam(required = false, defaultValue = "") List<String> tableNames, @RequestParam(required = false, defaultValue = "") List<String> tablePrefixs, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (engineFileTypes.size() == 1) {
            String file = dataBaseDocumentService.executeFile(url, driver, userName, password, version, description, fileName, engineFileTypes.get(0), tableNames, tablePrefixs);
            @Cleanup InputStream inputStream = new ByteArrayInputStream(file.getBytes(StandardCharsets.UTF_8));
            BaseUtils.download(inputStream, fileName + EngineFileType.valueOf(engineFileTypes.get(0)).getFileSuffix(), request, response);
        } else {
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment;filename=" + BaseUtils.getFormatString(request, fileName + ".zip"));
            ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
            for (String engineFileType : engineFileTypes) {
                String file = dataBaseDocumentService.executeFile(url, driver, userName, password, version, description, fileName, engineFileType, tableNames, tablePrefixs);
                @Cleanup InputStream inputStream = new ByteArrayInputStream(file.getBytes(StandardCharsets.UTF_8));
                //创建输入流读取文件
                @Cleanup BufferedInputStream bis = new BufferedInputStream(inputStream);
                //将文件写入zip内，即将文件进行打包
                zos.putNextEntry(new ZipEntry(fileName + EngineFileType.valueOf(engineFileType).getFileSuffix()));
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
        }
    }

}
