package com.newangels.gen.service;

import java.io.IOException;
import java.util.Map;

/**
 * RMI远程调用服务
 *
 * @author: TangLiang
 * @date: 2021/12/13 13:45
 * @since: 1.0
 */
public interface RmiService {

    /**
     * 获取git信息
     */
    Map<String, Object> getGitInfo() throws IOException;
}
