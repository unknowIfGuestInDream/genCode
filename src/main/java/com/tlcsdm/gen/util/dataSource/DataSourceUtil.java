package com.tlcsdm.gen.util.dataSource;

import com.tlcsdm.gen.enums.DataBaseType;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据源工具抽象类
 *
 * @author: TangLiang
 * @date: 2021/7/6 11:43
 * @since: 1.0
 */
public abstract class DataSourceUtil {

	protected DataSource dataSource;

	/**
	 * 初始化
	 */
	public abstract void init(String driverClass, String url, String userName, String password);

	/**
	 * 连接池释放
	 */
	public abstract void close();

	/**
	 * 连接池新增配置参数
	 */
	protected abstract void addDataSourceProperty(String propertyName, String value);

	/**
	 * 初始化连接池时配置参数 设计时用于生成数据库文档功能用
	 */
	protected void addDataSourcePropertys(String driver) {
		switch (DataBaseType.fromTypeName(driver)) {
			case MYSQL:
			case MYSQL8:
			case MARIADB:
				// 设置mysql/mariadb可以获取 tables remarks 信息
				addDataSourceProperty("useInformationSchema", "true");
				addDataSourceProperty("characterEncoding", "UTF-8");
				break;
			case ORACLE:
				// 设置oracle是否获取注释
				addDataSourceProperty("remarksReporting", "true");
				break;
			case SQLSERVER:
			case UNKNOW:
			default:
				break;
		}
	}

	/**
	 * 用于数据库增删改
	 */
	public int executeUpdate(String sql, Object... obj) throws SQLException {
		Connection conn = dataSource.getConnection();
		PreparedStatement ps = null;
		int x = 0;
		try {
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < obj.length; i++) {
				ps.setObject(i + 1, obj[i]);
			}
			x = ps.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			release(conn, ps);
		}
		return x;
	}

	/**
	 * 用于获取表数据个数
	 */
	public int getCount(String sql, Object... obj) throws SQLException {
		Connection conn = dataSource.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try {
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < obj.length; i++) {
				ps.setObject(i + 1, obj[i]);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			release(conn, ps, rs);
		}
		return count;
	}

	/**
	 * 查询返回List容器
	 */
	public List<Map<String, Object>> executeQuery(String sql, Object... obj) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String, Object>> list = new ArrayList<>();
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			int paramsIndex = 1;
			for (Object p : obj) {
				ps.setObject(paramsIndex++, p);
			}
			rs = ps.executeQuery();
			// 获得结果集中列的信息
			ResultSetMetaData rst = rs.getMetaData();
			// 获得结果集的列的数量
			int column = rst.getColumnCount();
			// 处理结果
			while (rs.next()) {
				// 创建Map容器存取每一列对应的值
				Map<String, Object> m = new HashMap<String, Object>();
				for (int i = 1; i <= column; i++) {
					m.put(rst.getColumnName(i), rs.getObject(i));
				}
				list.add(m);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			release(conn, ps, rs);
		}
		return list;
	}

	/**
	 * 释放连接池连接,如果释放不了,就把它的引用置为空,让GC去回收对象
	 */
	public void release(Connection conn, PreparedStatement ps, ResultSet rs, CallableStatement cs) {
		if (cs != null) {
			try {
				cs.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (rs != null) {
			try {
				rs.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (ps != null) {
			try {
				ps.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 释放连接池连接,如果释放不了,就把它的引用置为空,让GC去回收对象
	 */
	public void release(Connection conn, CallableStatement cs) {
		release(conn, null, null, cs);
	}

	/**
	 * 释放连接池连接,如果释放不了,就把它的引用置为空,让GC去回收对象
	 */
	public void release(Connection conn, PreparedStatement ps) {
		release(conn, ps, null, null);
	}

	/**
	 * 释放连接池连接,如果释放不了,就把它的引用置为空,让GC去回收对象
	 */
	public void release(Connection conn, PreparedStatement ps, ResultSet rs) {
		release(conn, ps, rs, null);
	}

	/**
	 * 获取缓存数据源信息
	 */
	public String getDataSourceInfo() {
		return dataSource.toString();
	}

	/**
	 * 获取连接池
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

}
