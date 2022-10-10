package com.tlcsdm.gen.config;

import io.undertow.server.DefaultByteBufferPool;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

/**
 * undertow服务器配置buffer pool 解决启动警告 无当前类不影响项目启动
 *
 * @author: TangLiang
 * @date: 2021/10/2 19:57
 * @since: 1.0
 */
@Component
public class UndertowPoolCustomizer implements WebServerFactoryCustomizer<UndertowServletWebServerFactory> {

	@Override
	public void customize(UndertowServletWebServerFactory factory) {
		factory.addDeploymentInfoCustomizers(deploymentInfo -> {
			WebSocketDeploymentInfo webSocketDeploymentInfo = new WebSocketDeploymentInfo();
			webSocketDeploymentInfo.setBuffers(new DefaultByteBufferPool(false, 1024));
			deploymentInfo.addServletContextAttribute("io.undertow.websockets.jsr.WebSocketDeploymentInfo",
					webSocketDeploymentInfo);
		});
	}

}
