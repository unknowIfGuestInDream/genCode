package com.tlcsdm.gen.util.template;

import freemarker.template.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.util.Map;

/**
 * FreeMarker模版引擎 处理资源路径下的ftl模版
 * <p>
 * 可以考虑复用FreeMarkerTemplateUtils类
 * </p>
 *
 * @author: TangLiang
 * @date: 2021/9/10 10:31
 * @since: 1.0
 */
public abstract class AbstractFreeMarkerTemplate {

	/**
	 * 获取当前模块的包名
	 */
	protected abstract String getRootPackageName();

	/**
	 * 获取当前模块下各自规范的包名
	 */
	protected abstract String getFtlPackageName();

	/**
	 * 获取模板代码
	 * @param configuration ftl模板引擎配置
	 * @param objectMap 模板参数
	 * @param fileName 模板文件名
	 */
	protected String getFtlModel(Configuration configuration, Map<String, Object> objectMap, String fileName) {
		return FreeMarkerUtil.getTemplateContent(configuration, objectMap,
				getRootPackageName() + File.separator + getFtlPackageName() + File.separator + fileName);
	}

	/**
	 * 获取模板代码
	 * @param freeMarkerConfigurer ftl模板引擎配置
	 * @param objectMap 模板参数
	 * @param fileName 模板文件名
	 */
	protected String getFtlModel(FreeMarkerConfigurer freeMarkerConfigurer, Map<String, Object> objectMap,
			String fileName) {
		return getFtlModel(freeMarkerConfigurer.getConfiguration(), objectMap, fileName);
	}

}
