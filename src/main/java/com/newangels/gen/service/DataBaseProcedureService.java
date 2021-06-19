package com.newangels.gen.service;

import org.springframework.beans.factory.InitializingBean;

import java.util.List;
import java.util.Map;

/**
 * oracle存储过程
 *
 * @author: TangLiang
 * @date: 2021/6/19 8:42
 * @since: 1.0
 */
public interface DataBaseProcedureService extends InitializingBean {

    /**
     * 查询过程信息
     *
     * @param NAME 过程名
     * @return 过程信息
     */
    List<Map<String, Object>> selectProcedure(String NAME);

    /**
     * 查询过程参数
     *
     * @param OWNER       账户名
     * @param OBJECT_NAME 过程名
     * @return 过程参数
     */
    List<Map<String, Object>> selectArguments(String OWNER, String OBJECT_NAME);

}
