package com.tlcsdm.gen.service.impl.dataBaseProcedure;

import com.tlcsdm.gen.enums.DataBaseType;
import com.tlcsdm.gen.factory.DataBaseProcedureFactory;
import com.tlcsdm.gen.service.DataBaseProcedureService;
import com.tlcsdm.gen.util.dataSource.DataSourceUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * sqlserver过程信息
 *
 * @author: TangLiang
 * @date: 2021/7/6 8:55
 * @since: 1.0
 */
@Service
public class SqlServerProcedureServiceImpl implements DataBaseProcedureService {

	// 数据库对java类型映射
	private final Map<String, String> map = new ConcurrentHashMap<>(8);

	// 存储过程出参对应值
	private final Map<String, String> dataTypeMap = new ConcurrentHashMap<>(8);

	// 存储过程出参结果集获取值
	private final Map<String, String> dataTypeOutMap = new ConcurrentHashMap<>(8);

	@Override
	public String selectProcedures(String name) {
		String sql = "select name as NAME, modify_date as LAST_UPDATE_TIME from sys.procedures where type = 'P'";
		if (StringUtils.isNotEmpty(name)) {
			sql += " and name like '%" + name.toUpperCase() + "%'";
		}
		return sql;
	}

	@Override
	public List<Map<String, Object>> selectProcedures(String name, DataSourceUtil dataSourceUtil) {
		return dataSourceUtil.executeQuery(selectProcedures(name));
	}

	@Override
	public String loadProcedure(String name) {
		// return "SP_HELPTEXT " + name;
		return "SELECT TEXT FROM syscomments WHERE id=object_id('" + name + "')";
	}

	@Override
	public String loadProcedure(String name, DataSourceUtil dataSourceUtil) {
		String allProceduresSql = loadProcedure(name);
		// 执行sql
		List<Map<String, Object>> list = dataSourceUtil.executeQuery(allProceduresSql);
		// 获取结果集
		return list.size() > 0 ? list.get(0).get("TEXT").toString() : "";
	}

	@Override
	public String selectArguments(String owner, String objectName) {
		return "select P.name as ARGUMENT_NAME, P.is_output as IN_OUT, T.name as DATA_TYPE from sys.parameters P inner join sys.types T on P.system_type_id = T.system_type_id where P.object_id =object_id('"
				+ objectName + "') and T.name <> 'sysname' ORDER BY P.parameter_id";
	}

	@Override
	public String getJavaClass(String type) {
		return map.getOrDefault(type.toUpperCase(), "String");
	}

	@Override
	public String getRepositoryOutType(String type) {
		return dataTypeMap.getOrDefault(type.toUpperCase(), "OracleTypes.VARCHAR");
	}

	@Override
	public String getRepositoryOutTypeCode(String type) {
		return dataTypeOutMap.getOrDefault(type.toUpperCase(), "String");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		map.put("VARCHAR", "String");
		map.put("NVARCHAR", "String");
		map.put("INT", "int");
		map.put("BIGINT", "int");
		map.put("TINYINT", "int");
		map.put("TIMESTAMP", "Date");

		dataTypeMap.put("VARCHAR2", "OracleTypes.VARCHAR");
		dataTypeMap.put("REF CURSOR", "OracleTypes.CURSOR");
		dataTypeMap.put("BLOB", "OracleTypes.BLOB");
		dataTypeMap.put("NUMBER", "OracleTypes.NUMERIC");

		dataTypeOutMap.put("VARCHAR2", "String");
		dataTypeOutMap.put("REF CURSOR", "Object");
		dataTypeOutMap.put("BLOB", "Blob");
		dataTypeOutMap.put("NUMBER", "Double");
		DataBaseProcedureFactory.register(DataBaseType.SQLSERVER, this);
	}

}
