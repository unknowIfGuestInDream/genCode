package com.tlcsdm.gen;

import com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class })
@Slf4j
public class GenApplication {

	private static final String TIPS = "\n\n" + "******************** 代码生成系统启动成功 ********************\n";

	public static void main(String[] args) {
		SpringApplication.run(GenApplication.class, args);
		log.info(TIPS);
	}

}
