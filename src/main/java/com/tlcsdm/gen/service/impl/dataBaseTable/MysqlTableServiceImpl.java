package com.tlcsdm.gen.service.impl.dataBaseTable;

import com.tlcsdm.gen.enums.DataBaseType;
import com.tlcsdm.gen.exception.UnSupportedDataSourceException;
import com.tlcsdm.gen.factory.DataBaseTableFactory;
import com.tlcsdm.gen.service.DataBaseTableService;
import com.tlcsdm.gen.util.dataSource.DataSourceUtil;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * mysql表信息
 *
 * @author: TangLiang
 * @date: 2021/7/14 14:01
 * @since: 1.0
 */
@Service
public class MysqlTableServiceImpl implements DataBaseTableService {

	@Override
	public String loadTable(@NonNull String name) {
		throw new UnSupportedDataSourceException("暂时不支持mysql数据库表信息查询");
	}

	@Override
	public Map<String, Object> loadTable(@NonNull String name, @NonNull DataSourceUtil dataSourceUtil) {
		List<Map<String, Object>> list = dataSourceUtil.executeQuery(loadTable(name));
		return list.size() > 0 ? list.get(0) : new HashMap<>();
	}

	@Override
	public String selectTables(String name, String schema) {
		String sql = "select * from information_schema.tables where table_schema = '" + schema + "' ";
		if (StringUtils.isNotEmpty(name)) {
			sql += "and table_Name like '%" + name + "%'";
		}
		return sql;
	}

	@Override
	public List<Map<String, Object>> selectTables(String name, String schema, DataSourceUtil dataSourceUtil) {
		return dataSourceUtil.executeQuery(selectTables(name, schema));
	}

	@Override
	public String selectTableInfo(@NonNull String name, String schema) {
		String sql = "SELECT T.COLUMN_NAME, T.DATA_TYPE, T.CHARACTER_MAXIMUM_LENGTH as DATA_LENGTH, (case when T.IS_NULLABLE = 'YES' then 'Y' else 'N' end) as NULLABLE, T.ORDINAL_POSITION as COLUMN_ID, T.COLUMN_DEFAULT as DATA_DEFAULT, '' as NUM_DISTINCT, '' as NUM_NULLS, P.CHECK_TIME as LAST_ANALYZED, '' as AVG_COL_LEN, T.COLUMN_COMMENT as COMMENTS FROM INFORMATION_SCHEMA.columns T LEFT JOIN INFORMATION_SCHEMA.TABLES P ON T.TABLE_NAME = P.TABLE_NAME AND T.TABLE_SCHEMA = P.TABLE_SCHEMA WHERE T.TABLE_SCHEMA = '"
				+ schema.toLowerCase() + "'";
		if (StringUtils.isNotEmpty(name)) {
			sql += " and T.TABLE_NAME = '" + name.toLowerCase() + "' ";
		}
		sql += " ORDER BY T.ORDINAL_POSITION";
		return sql;
	}

	@Override
	public List<Map<String, Object>> selectTableInfo(@NonNull String name, String schema,
			@NonNull DataSourceUtil dataSourceUtil) {
		return dataSourceUtil.executeQuery(selectTableInfo(name, schema));
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		DataBaseTableFactory.register(DataBaseType.MYSQL, this);
		DataBaseTableFactory.register(DataBaseType.MYSQL8, this);
	}

}
