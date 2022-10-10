package com.tlcsdm.gen.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 项目自定义配置
 *
 * @author: TangLiang
 * @date: 2021/12/13 17:16
 * @since: 1.0
 */
@Component
@ConfigurationProperties("gen")
@Data
public class GenProperty {

	private Boolean isdb;

	private String version;

	private String author;

	private Boolean schedule;

	private Boolean async;

	private GenRpc rpc = new GenRpc();

	private GenShutDown shutdown = new GenShutDown();

	@Data
	private static class GenRpc {

		private Boolean enabled;

		private Boolean rmi;

		private int rmiPort;

		private String rmiServiceName;

		private Boolean httpInvoker;

	}

	@Data
	private static class GenShutDown {

		private Boolean enabled;

		private String password;

	}

}
