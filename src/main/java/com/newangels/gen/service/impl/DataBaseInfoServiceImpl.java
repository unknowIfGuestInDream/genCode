package com.newangels.gen.service.impl;

import com.newangels.gen.service.DataBaseInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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
    public Map<String, Object> loadDataBaseInfo(String ID) {
        List<Map<String, Object>> result = genJdbcTemplate.queryForList("select * from database_info where ID = ?", ID);
        return result.size() == 1 ? result.get(0) : new HashMap();
    }

    @Override
    public List<Map<String, Object>> selectDataBaseInfo() {
        return genJdbcTemplate.queryForList("select * from database_info");
    }

    @Override
    public int insertDataBaseInfo(String NAME, String URL, String DRIVER, String USERNAME, String PASSWORD) {
        return genJdbcTemplate.update("insert into database_info(NAME, URL, DRIVER, USERNAME, PASSWORD, UPDATE_TIME, CREATE_TIME) values(?, ?, ?, ?, ?, sysdate(), sysdate())", NAME, URL, DRIVER, USERNAME, PASSWORD);
    }

    @Override
    public int updateDataBaseInfo(String ID, String NAME, String URL, String DRIVER, String USERNAME, String PASSWORD) {
        return genJdbcTemplate.update("update database_info set NAME = ?, URL= ?, DRIVER = ?, USERNAME = ?, PASSWORD = ?, UPDATE_TIME = sysdate() where ID = ?", NAME, URL, DRIVER, USERNAME, PASSWORD, ID);
    }

    @Override
    public int deleteDataBaseInfo(String ID) {
        return genJdbcTemplate.update("delete from database_info  where ID = ?", ID);
    }
}
