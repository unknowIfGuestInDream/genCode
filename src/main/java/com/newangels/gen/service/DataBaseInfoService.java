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
     */
    List<Map<String, Object>> selectDataBaseInfo();

    /**
     * 新增数据源
     *
     * @param name     数据源名称
     * @param url      路径
     * @param driver   驱动名
     * @param userName 用户
     * @param password 密码
     */
    int insertDataBaseInfo(String name, String url, String driver, String userName, String password);

    /**
     * 修改数据源
     *
     * @param id       id
     * @param name     数据源名称
     * @param url      路径
     * @param driver   驱动名
     * @param userName 用户
     * @param password 密码
     */
    int updateDataBaseInfo(String id, String name, String url, String driver, String userName, String password);

    /**
     * 删除数据源
     *
     * @param id id
     */
    int deleteDataBaseInfo(String id);
}
