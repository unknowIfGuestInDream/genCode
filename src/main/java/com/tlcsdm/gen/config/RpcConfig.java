package com.tlcsdm.gen.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * RMI远程调用配置
 *
 * <p>
 * 客户端代码 <blockquote><pre>

 * &#64;Configuration
 * public class RpcClient {
 *
 *     &#64;Bean
 *     public RmiProxyFactoryBean RmiRpcService() {
 *         RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
 *         rmiProxyFactoryBean.setServiceUrl("rmi://127.0.0.1:8769/rpcService");
 *         rmiProxyFactoryBean.setServiceInterface(RpcService.class);
 *         return rmiProxyFactoryBean;
 *     }
 *
 *     //@Bean
 *     //public HttpInvokerProxyFactoryBean HttpInvokerRpcService() {
 *     //    HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean = new HttpInvokerProxyFactoryBean();
 *     //    httpInvokerProxyFactoryBean.setServiceUrl("http://127.0.0.1:8669/gen/invoker");
 *     //    httpInvokerProxyFactoryBean.setServiceInterface(RpcService.class);
 *     //    return httpInvokerProxyFactoryBean;
 *     //}
 * }
 *
 *
 * &#64;SpringBootTest
 * public class RpcTest {
 *     &#64;Autowired
 *     private RpcService rpcService;
 *

@Test
 *     public void excel() throws Exception {
 *         System.out.println(rpcService.getGitInfo());
 *     }
 * }
 * </pre></blockquote>
 * </p>
 * @author: TangLiang
 * @date: 2021/12/13 13:46
 * @since: 1.0
 */
@Configuration
@Slf4j
@ConditionalOnProperty(name = "gen.rpc.enabled", havingValue = "true")
public class RpcConfig {

	@PostConstruct
	public void warnRpcRemoval() {
		log.warn("RMI/HttpInvoker remoting endpoints are not available with Spring Framework 6; RPC exporters are skipped.");
	}

}
