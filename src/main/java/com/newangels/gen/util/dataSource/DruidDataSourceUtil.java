package com.newangels.gen.util.dataSource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;

/**
 * Druid连接池工具实现类
 *
 * @author: ZhangXu
 * @date: 2021/7/7 14:47
 * @since: 1.0
 */
public class DruidDataSourceUtil extends DataSourceUtil {
    private DruidDataSource dataSource;

    public DruidDataSourceUtil() {
        dataSource = new DruidDataSource();
        super.dataSource = dataSource;
    }

    public DruidDataSourceUtil(String driver, String url, String userName, String password) {
        dataSource = new DruidDataSource();
        super.dataSource = dataSource;
        init(driver, url, userName, password);
    }

    @Override
    public void init(String driverClass, String url, String userName, String password) {
        if (StringUtils.isEmpty(url) || StringUtils.isEmpty(driverClass) || StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("数据库配置不能为空");
        }
        if (dataSource != null && !dataSource.isInited()) {
            dataSource.setUrl(url);//设置url
            dataSource.setDriverClassName(driverClass);//设置驱动
            dataSource.setUsername(userName);//账号
            dataSource.setPassword(password);//密码
            dataSource.setBreakAfterAcquireFailure(true);
            addDataSourcePropertys();
        }
    }

    @Override
    public void close() {
        if (dataSource != null) {
            dataSource.close();
        }
        dataSource = null;
    }

    @Override
    public void addDataSourcePropertys() {
        String databaseProductName = "";
        try {
            databaseProductName = dataSource.getConnection().getMetaData().getDatabaseProductName();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        switch (databaseProductName) {
            case "MySQL":
                //设置mysql/mariadb可以获取 tables remarks 信息
                dataSource.addConnectionProperty("useInformationSchema", "true");
                dataSource.addConnectionProperty("characterEncoding", "UTF-8");
                break;
            case "Oracle":
                //设置oracle是否获取注释
                dataSource.addConnectionProperty("remarksReporting", "true");
                break;
            case "Microsoft SQL Server":
                break;
            default:
                break;
        }
    }
}
