package com.tlcsdm.gen.config;

import com.alibaba.druid.spring.boot3.autoconfigure.properties.DruidStatProperties;
import com.alibaba.druid.support.jakarta.StatViewServlet;
import com.alibaba.druid.support.jakarta.WebStatFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * druid监控配置
 *
 * @author: TangLiang
 * @date: 2021/6/18 23:18
 * @since: 1.0
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(name = { "spring.datasource.druid.stat-view-servlet.enabled", "gen.isdb" }, havingValue = "true")
@EnableConfigurationProperties(DruidStatProperties.class)
public class DruidMonitorConfig {

	@Bean
	public ServletRegistrationBean<StatViewServlet> statViewServletRegistrationBean(DruidStatProperties properties) {
		DruidStatProperties.StatViewServlet config = properties.getStatViewServlet();
		ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>();
		bean.setServlet(new StatViewServlet());
		String urlPattern = config.getUrlPattern() != null ? config.getUrlPattern() : "/druid/*";
		bean.addUrlMappings(urlPattern);
		if (config.getAllow() != null) {
			bean.addInitParameter("allow", config.getAllow());
		}
		if (config.getDeny() != null) {
			bean.addInitParameter("deny", config.getDeny());
		}
		if (config.getLoginUsername() != null) {
			bean.addInitParameter("loginUsername", config.getLoginUsername());
		}
		if (config.getLoginPassword() != null) {
			bean.addInitParameter("loginPassword", config.getLoginPassword());
		}
		if (config.getResetEnable() != null) {
			bean.addInitParameter("resetEnable", config.getResetEnable());
		}
		return bean;
	}

	@Bean
	public FilterRegistrationBean<WebStatFilter> webStatFilterRegistrationBean(DruidStatProperties properties) {
		DruidStatProperties.WebStatFilter config = properties.getWebStatFilter();
		FilterRegistrationBean<WebStatFilter> bean = new FilterRegistrationBean<>();
		bean.setFilter(new WebStatFilter());
		String urlPattern = config.getUrlPattern() != null ? config.getUrlPattern() : "/*";
		bean.addUrlPatterns(urlPattern);
		if (config.getExclusions() != null) {
			bean.addInitParameter("exclusions", config.getExclusions());
		}
		return bean;
	}

}
