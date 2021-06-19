package com.newangels.gen.service;

import java.util.List;
import java.util.Map;

/**
 * 数据源管理
 *
 * @author: TangLiang
 * @date: 2021/6/19 13:16
 * @since: 1.0
 */
public interface DataBaseInfoService {

    /**
     * 查询数据源
     *
     * @return
     */
    List<Map<String, Object>> selectDataBaseInfo();

    /**
     * 新增数据源
     *
     * @param NAME     数据源名称
     * @param URL      路径
     * @param DRIVER   驱动名
     * @param USERNAME 用户
     * @param PASSWORD 密码
     * @return
     */
    int insertDataBaseInfo(String NAME, String URL, String DRIVER, String USERNAME, String PASSWORD);

    /**
     * 修改数据源
     *
     * @param ID       id
     * @param NAME     数据源名称
     * @param URL      路径
     * @param DRIVER   驱动名
     * @param USERNAME 用户
     * @param PASSWORD 密码
     * @return
     */
    int updateDataBaseInfo(String ID, String NAME, String URL, String DRIVER, String USERNAME, String PASSWORD);

    /**
     * 删除数据源
     *
     * @param ID id
     * @return
     */
    int deleteDataBaseInfo(String ID);
}
