package com.newangels.gen.template;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: TangLiang
 * @date: 2021/9/5 12:50
 * @since: 1.0
 */
@SpringBootTest
public class TemplateTest {
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Test
    public void test1() {
        Context context = new Context();
        context.setVariable("code", "Gena");
        String template = templateEngine.process("/templates/code/BaseUtils.text", context);
        System.out.println(template);
    }

    @Test
    public void test2() throws IOException, TemplateException {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("code", "Gena");
        Template template = configuration.getTemplate("common/BaseUtils.ftl");
        template.process(objectMap, stringWriter);
        System.out.println(stringWriter.toString());
    }
}
