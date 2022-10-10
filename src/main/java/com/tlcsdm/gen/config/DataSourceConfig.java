package com.tlcsdm.gen.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * 数据库配置
 *
 * @author: TangLiang
 * @date: 2021/6/18 23:18
 * @since: 1.0
 */
@Configuration
@ConditionalOnProperty(name = "gen.isdb", havingValue = "true", matchIfMissing = true)
public class DataSourceConfig {

	@Primary
	@Bean(name = "genDataSource")
	@Qualifier("genDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.druid")
	public DataSource genDataSource() {
		return new DruidDataSource();
	}

	@Bean(name = "genJdbcTemplate")
	public JdbcTemplate genJdbcTemplate(@Qualifier("genDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

}
