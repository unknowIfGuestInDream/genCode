package com.newangels.gen.util;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据源工具类
 *
 * @author: TangLiang
 * @date: 2021/7/6 11:43
 * @since: 1.0
 */
public class DataSourceUtil {
    private DruidDataSource dataSource;

    public DataSourceUtil() {
        dataSource = new DruidDataSource();
    }

    public DataSourceUtil(String driverClass, String url, String userName, String password) {
        dataSource = new DruidDataSource();
        init(driverClass, url, userName, password);
    }

    public void init(String driverClass, String url, String userName, String password) {
        if (StringUtils.isEmpty(url) || StringUtils.isEmpty(driverClass) || StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            throw new RuntimeException("数据库配置不能为空");
        }
        if (dataSource != null && !dataSource.isInited()) {
            dataSource.setUrl(url);//设置url
            dataSource.setDriverClassName(driverClass);//设置驱动
            dataSource.setUsername(userName);//账号
            dataSource.setPassword(password);//密码
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
            //获得结果集中列的信息
            ResultSetMetaData rst = rs.getMetaData();
            //获得结果集的列的数量
            int column = rst.getColumnCount();
            //处理结果
            while (rs.next()) {
                //创建Map容器存取每一列对应的值
                Map<String, Object> m = new HashMap<String, Object>();
                for (int i = 1; i <= column; i++) {
                    m.put(rst.getColumnName(i), rs.getObject(i));
                }
                list.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
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

    public void close() {
        if (dataSource != null) {
            dataSource.close();
        }
        dataSource = null;
    }
}