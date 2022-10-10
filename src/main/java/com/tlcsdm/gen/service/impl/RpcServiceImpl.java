package com.tlcsdm.gen.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tlcsdm.gen.base.BaseUtils;
import com.tlcsdm.gen.domain.GenProperty;
import com.tlcsdm.gen.service.RpcService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
public class RpcServiceImpl implements RpcService {

	private final ObjectMapper objectMapper;

	private final GenProperty genProperty;

	@Value("${schedule.enabled}")
	private Boolean enabled;

	@Override
	public Map<String, Object> getGitInfo() throws IOException {
		Resource resource = new ClassPathResource("git.properties");
		if (!resource.exists()) {
			throw new FileNotFoundException("git.properties文件未找到");
		}
		String versionJson = BaseUtils.inputStreamToString(resource.getInputStream());
		Map<String, Object> map = objectMapper.readValue(versionJson, new TypeReference<Map<String, Object>>() {
		});
		return map;
	}

	@Override
	public String getGenProperty() throws JsonProcessingException {
		return objectMapper.writeValueAsString(genProperty);
	}

	@Override
	public String startSchedule() {
		if (!enabled) {
			return "定时任务模块未初始化，无法进行启动停止操作";
		}
		if (genProperty.getSchedule()) {
			return "定时任务已启动";
		}
		genProperty.setSchedule(true);
		return "定时任务成功启动";
	}

	@Override
	public String stopSchedule() {
		if (!enabled) {
			return "定时任务模块未初始化，无法进行启动停止操作";
		}
		if (!genProperty.getSchedule()) {
			return "定时任务已停止";
		}
		genProperty.setSchedule(false);
		return "定时任务成功停止";
	}

}
