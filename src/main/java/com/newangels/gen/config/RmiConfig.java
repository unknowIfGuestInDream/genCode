package com.newangels.gen.config;

import com.newangels.gen.service.RmiService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

/**
 * RMI远程调用配置
 *
 * <p>
 * 客户端代码
 * <blockquote><pre>
 * @Configuration
 * public class RmiClient {
 *
 *     @Bean(name = "userService")
 *     public RmiProxyFactoryBean getUserService() {
 *         RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
 *         rmiProxyFactoryBean.setServiceUrl("rmi://127.0.0.1:8769/rmiService");
 *         rmiProxyFactoryBean.setServiceInterface(RmiService.class);
 *         return rmiProxyFactoryBean;
 *     }
 * }
 *
 * @SpringBootTest
 * public class RmiTest {
 *     @Autowired
 *     private RmiService rmiService;
 *     @Test
 *     public void excel() throws Exception {
 *         System.out.println(rmiService.getGitInfo());
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
@ConditionalOnProperty(name = "gen.rmi", havingValue = "true")
public class RmiConfig {
    private final RmiService rmiService;

    @Bean
    public RmiServiceExporter getRmiServiceExporter() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("rmiService");
        rmiServiceExporter.setService(rmiService);
        rmiServiceExporter.setServiceInterface(RmiService.class);
        rmiServiceExporter.setRegistryPort(8769);
        return rmiServiceExporter;
    }
}
