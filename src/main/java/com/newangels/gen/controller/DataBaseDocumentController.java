package com.newangels.gen.controller;

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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
     * //TODO 请求加随机数
     */
    @GetMapping("genDataBaseDocument")
    @Log
    public void genDataBaseDocument(String url, String driver, String userName, String password, @RequestParam(required = false, defaultValue = "1.0.0") String version, String description, @RequestParam(required = false, defaultValue = "数据库文档") String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String file = dataBaseDocumentService.executeFile(url, driver, userName, password, version, description, fileName);
        @Cleanup InputStream inputStream = new ByteArrayInputStream(file.getBytes(StandardCharsets.UTF_8));
        BaseUtils.download(inputStream, "数据库文档.doc", request, response);
    }

}
