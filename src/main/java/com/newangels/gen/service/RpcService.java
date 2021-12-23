package com.newangels.gen.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.Map;

/**
 * RMI远程调用服务
 *
 * @author: TangLiang
 * @date: 2021/12/13 13:45
 * @since: 1.0
 */
public interface RpcService {

    /**
     * 获取git信息
     */
    Map<String, Object> getGitInfo() throws IOException;

    /**
     * 获取项目自定义配置
     */
    String getGenProperty() throws JsonProcessingException;

    /**
     * 开启定时
     */
    String startSchedule();

    /**
     * 关闭定时
     */
    String stopSchedule();
}
