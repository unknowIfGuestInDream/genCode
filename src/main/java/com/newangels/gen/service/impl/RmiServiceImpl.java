package com.newangels.gen.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newangels.gen.base.BaseUtils;
import com.newangels.gen.domain.GenProperty;
import com.newangels.gen.service.RmiService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

/**
 * @author: TangLiang
 * @date: 2021/12/13 13:46
 * @since: 1.0
 */
@Service
@RequiredArgsConstructor
public class RmiServiceImpl implements RmiService {
    private final ObjectMapper objectMapper;
    private final GenProperty genProperty;

    @Override
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

    @Override
    public String getGenProperty() throws JsonProcessingException {
        return objectMapper.writeValueAsString(genProperty);
    }

    @Override
    public void startSchedule() {
        genProperty.setSchedule(true);
    }

    @Override
    public void stopSchedule() {
        genProperty.setSchedule(false);
    }
}
