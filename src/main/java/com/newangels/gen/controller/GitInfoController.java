package com.newangels.gen.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newangels.gen.annotation.Log;
import com.newangels.gen.base.BaseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

/**
 * git信息
 *
 * @author: TangLiang
 * @date: 2021/11/26 9:52
 * @since: 1.0
 */
@RestController
@RequiredArgsConstructor
public class GitInfoController {
    private final ObjectMapper objectMapper;

    /**
     * 获取git信息
     */
    @GetMapping("getGitInfo")
    @Log
    public Map<String, Object> getGitInfo() throws IOException {
        Resource resource = new ClassPathResource("git.properties");
        if (!resource.exists()) {
            throw new FileNotFoundException("git.properties文件未找到");
        }
        String versionJson = BaseUtils.inputStreamToString(resource.getInputStream());
        Map<String, Object> map = objectMapper.readValue(versionJson, new TypeReference<Map<String, Object>>() {
        });
        return BaseUtils.success(map);
    }

}
