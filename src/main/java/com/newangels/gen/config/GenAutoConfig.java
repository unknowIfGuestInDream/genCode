package com.newangels.gen.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * 当gen.isdb=false时，取消druid初始化
 *
 * @author: TangLiang
 * @date: 2021/12/7 11:47
 * @since: 1.0
 */
@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
@ConditionalOnProperty(name = "gen.isdb", havingValue = "false")
public class GenAutoConfig {
}
