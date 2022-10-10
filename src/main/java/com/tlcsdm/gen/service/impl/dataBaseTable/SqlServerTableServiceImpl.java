package com.tlcsdm.gen.service.impl.dataBaseTable;

import com.tlcsdm.gen.enums.DataBaseType;
import com.tlcsdm.gen.exception.UnSupportedDataSourceException;
import com.tlcsdm.gen.factory.DataBaseTableFactory;
import com.tlcsdm.gen.service.DataBaseTableService;
import com.tlcsdm.gen.util.dataSource.DataSourceUtil;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * sqlserver表信息
 *
 * @author: TangLiang
 * @date: 2021/7/14 14:01
 * @since: 1.0
 */
@Service
public class SqlServerTableServiceImpl implements DataBaseTableService {

	@Override
	public String loadTable(@NonNull String name) {
		throw new UnSupportedDataSourceException("暂时不支持sqlserver数据库表信息查询");
	}

	@Override
	public Map<String, Object> loadTable(@NonNull String name, @NonNull DataSourceUtil dataSourceUtil) {
		throw new UnSupportedDataSourceException("暂时不支持sqlserver数据库表信息查询");
	}

	@Override
	public String selectTables(String name, String schema) {
		throw new UnSupportedDataSourceException("暂时不支持sqlserver数据库表信息查询");
	}

	@Override
	public List<Map<String, Object>> selectTables(String name, String schema, DataSourceUtil dataSourceUtil) {
		throw new UnSupportedDataSourceException("暂时不支持sqlserver数据库表信息查询");
	}

	@Override
	public String selectTableInfo(@NonNull String name, String schema) {
		throw new UnSupportedDataSourceException("暂时不支持sqlserver数据库表信息查询");
	}

	@Override
	public List<Map<String, Object>> selectTableInfo(@NonNull String name, String schema,
			@NonNull DataSourceUtil dataSourceUtil) {
		throw new UnSupportedDataSourceException("暂时不支持sqlserver数据库表信息查询");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		DataBaseTableFactory.register(DataBaseType.SQLSERVER, this);
	}

}
