package com.tlcsdm.gen.util.template;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * FreeMarker模板引擎工具类
 *
 * @author: TangLiang
 * @date: 2021/9/6 8:39
 * @since: 1.0
 */
public class FreeMarkerUtil {

	private FreeMarkerUtil() {

	}

	/**
	 * 获取模版内容
	 * @param configuration freeMarkerConfigurer.getConfiguration();
	 * @param objectMap 参数
	 * @param name 模版位置
	 */
	public static String getTemplateContent(Configuration configuration, Map<String, Object> objectMap, String name) {
		StringWriter stringWriter = new StringWriter();
		Template template;
		try {
			template = configuration.getTemplate(name);
			template.process(objectMap, stringWriter);
		}
		catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
		return stringWriter.toString();
	}

	/**
	 * 获取模版内容
	 * @param freeMarkerConfigurer FreeMarkerConfigurer
	 * @param objectMap 参数
	 * @param name 模版位置
	 */
	public static String getTemplateContent(FreeMarkerConfigurer freeMarkerConfigurer, Map<String, Object> objectMap,
			String name) {
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		StringWriter stringWriter = new StringWriter();
		Template template;
		try {
			template = configuration.getTemplate(name);
			template.process(objectMap, stringWriter);
		}
		catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
		return stringWriter.toString();
	}

}
