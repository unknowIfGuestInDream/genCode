package com.newangels.gen.service;

import org.springframework.beans.factory.InitializingBean;

/**
 * oracle存储过程
 *
 * @author: TangLiang
 * @date: 2021/6/19 8:42
 * @since: 1.0
 */
public interface DataBaseProcedureService extends InitializingBean {

    /**
     * 查询所有过程
     * @param NAME
     */
    String selectProcedures(String NAME);

    /**
     * 查询过程信息
     *
     * @param NAME 过程名
     * @return 过程信息
     */
    String loadProcedure(String NAME);

    /**
     * 查询过程参数
     *
     * @param OWNER       账户名
     * @param OBJECT_NAME 过程名
     * @return 过程参数
     */
    String selectArguments(String OWNER, String OBJECT_NAME);

}
