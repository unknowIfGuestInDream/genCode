package com.newangels.gen.service.impl;

import com.newangels.gen.service.DataBaseInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author: TangLiang
 * @date: 2021/6/19 13:21
 * @since: 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@RequiredArgsConstructor
public class DataBaseInfoServiceImpl implements DataBaseInfoService {
    private final JdbcTemplate genJdbcTemplate;

    @Override
    public List<Map<String, Object>> selectDataBaseInfo() {
        return genJdbcTemplate.queryForList("select * from database_info");
    }

    @Override
    public int insertDataBaseInfo(String name, String url, String driver, String userName, String password) {
        return genJdbcTemplate.update("insert into database_info(name, url, driver, userName, password, update_time, create_time) values(?, ?, ?, ?, ?, sysdate(), sysdate())", name, url, driver, userName, password);
    }

    @Override
    public int updateDataBaseInfo(String id, String name, String url, String driver, String userName, String password) {
        return genJdbcTemplate.update("update database_info set name = ?, url= ?, driver = ?, userName = ?, password = ?, update_time = sysdate() where id = ?", name, url, driver, userName, password, id);
    }

    @Override
    public int deleteDataBaseInfo(String id) {
        return genJdbcTemplate.update("delete from database_info  where id = ?", id);
    }
}
