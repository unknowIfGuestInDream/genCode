package com.tlcsdm.gen.service.impl.dataBaseInfo;

import com.tlcsdm.gen.service.DataBaseInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 有数据库实现类 gen.nodb=true
 *
 * @author: TangLiang
 * @date: 2021/6/19 13:21
 * @since: 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@RequiredArgsConstructor
@ConditionalOnProperty(name = "gen.isdb", havingValue = "true", matchIfMissing = true)
public class DataBaseInfoServiceImpl implements DataBaseInfoService {

	private final JdbcTemplate genJdbcTemplate;

	@Override
	public Map<String, Object> loadDataBaseInfo(String ID) {
		List<Map<String, Object>> list = genJdbcTemplate.queryForList("select * from database_info where ID = ?", ID);
		return list.size() > 0 ? list.get(0) : new HashMap<>();
	}

	@Override
	public List<Map<String, Object>> selectDataBaseInfo() {
		return genJdbcTemplate.queryForList("select * from database_info");
	}

	@Override
	public int insertDataBaseInfo(String NAME, String URL, String DRIVER, String USERNAME, String PASSWORD)
			throws SQLException {
		String sql;
		switch (genJdbcTemplate.getDataSource().getConnection().getMetaData().getDatabaseProductName()) {
			case "MySQL":
				sql = "insert into database_info(NAME, URL, DRIVER, USERNAME, PASSWORD, UPDATE_TIME, CREATE_TIME) values(?, ?, ?, ?, ?, sysdate(), sysdate())";
				break;
			case "Oracle":
			default:
				sql = "insert into database_info(NAME, URL, DRIVER, USERNAME, PASSWORD, UPDATE_TIME, CREATE_TIME) values(?, ?, ?, ?, ?, sysdate, sysdate)";
				break;
		}
		return genJdbcTemplate.update(sql, NAME, URL, DRIVER, USERNAME, PASSWORD);
	}

	@Override
	public int updateDataBaseInfo(String ID, String NAME, String URL, String DRIVER, String USERNAME, String PASSWORD)
			throws SQLException {
		String sql;
		switch (genJdbcTemplate.getDataSource().getConnection().getMetaData().getDatabaseProductName()) {
			case "MySQL":
				sql = "update database_info set NAME = ?, URL= ?, DRIVER = ?, USERNAME = ?, PASSWORD = ?, UPDATE_TIME = sysdate() where ID = ?";
				break;
			case "Oracle":
			default:
				sql = "update database_info set NAME = ?, URL= ?, DRIVER = ?, USERNAME = ?, PASSWORD = ?, UPDATE_TIME = sysdate where ID = ?";
				break;
		}
		return genJdbcTemplate.update(sql, NAME, URL, DRIVER, USERNAME, PASSWORD, ID);
	}

	@Override
	public int deleteDataBaseInfo(String ID) {
		return genJdbcTemplate.update("delete from database_info  where ID = ?", ID);
	}

}
