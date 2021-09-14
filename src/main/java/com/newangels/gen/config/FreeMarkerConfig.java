package com.newangels.gen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.HashMap;
import java.util.Map;

/**
 * FreeMarker模板引擎配置
 *
 * @author: TangLiang
 * @date: 2021/9/5 13:47
 * @since: 1.0
 */
@Configuration
public class FreeMarkerConfig {

    @Bean(name = "freeMarkerConfigurer")
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setDefaultEncoding("UTF-8");
        configurer.setTemplateLoaderPath("classpath:/ftl");
        Map<String, Object> variables = new HashMap<>(1 << 1);
        variables.put("xml_escape", "fmXmlEscape");
        configurer.setFreemarkerVariables(variables);
        return configurer;
    }

}
