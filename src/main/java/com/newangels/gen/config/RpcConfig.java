package com.newangels.gen.config;

import com.newangels.gen.service.RpcService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.remoting.rmi.RmiServiceExporter;

/**
 * RMI远程调用配置
 *
 * <p>
 * 客户端代码
 * <blockquote><pre>
 * @Configuration
 * public class RpcClient {
 *
 *     @Bean
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
 * @SpringBootTest
 * public class RpcTest {
 *     @Autowired
 *     private RpcService rpcService;
 *     @Test
 *     public void excel() throws Exception {
 *         System.out.println(rpcService.getGitInfo());
 *     }
 * }
 * </pre></blockquote></p>
 *
 * @author: TangLiang
 * @date: 2021/12/13 13:46
 * @since: 1.0
 */
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "gen.rpc.enabled", havingValue = "true")
public class RpcConfig {
    private final RpcService rpcService;

    @Value("${gen.rpc.rmiPort}")
    private int rmiPort;
    @Value("${gen.rpc.rmiServiceName}")
    private String rmiServiceName;

    @Bean
    @ConditionalOnProperty(name = "gen.rpc.rmi", havingValue = "true")
    public RmiServiceExporter getRmiServiceExporter() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName(rmiServiceName);
        rmiServiceExporter.setService(rpcService);
        rmiServiceExporter.setServiceInterface(RpcService.class);
        rmiServiceExporter.setRegistryPort(rmiPort);
        return rmiServiceExporter;
    }

    /**
     * org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping
     * 它的作用就是把Spring MVC上下文中以“/”开头的Bean进行对外提供服务
     */
    @Bean("/invoker")
    @ConditionalOnProperty(name = "gen.rpc.httpInvoker", havingValue = "true")
    public HttpInvokerServiceExporter getHttpInvokerServiceExporter() {
        HttpInvokerServiceExporter httpInvokerServiceExporter = new HttpInvokerServiceExporter();
        httpInvokerServiceExporter.setService(rpcService);
        httpInvokerServiceExporter.setServiceInterface(RpcService.class);
        return httpInvokerServiceExporter;
    }
}
