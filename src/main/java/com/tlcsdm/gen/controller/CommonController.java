package com.tlcsdm.gen.controller;

import cn.smallbun.screw.core.engine.EngineFileType;
import com.tlcsdm.common.annotation.Log;
import com.tlcsdm.gen.base.BaseUtils;
import com.tlcsdm.gen.enums.*;
import com.tlcsdm.gen.util.cache.SimpleCache;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用模块
 *
 * @author: TangLiang
 * @date: 2021/6/21 9:03
 * @since: 1.0
 */
@RestController
@RequiredArgsConstructor
public class CommonController {

	/**
	 * 目录
	 */
	@GetMapping("/")
	public ModelAndView index() {
		return new ModelAndView("pages/index");
	}

	/**
	 * 获取DataBaseType的数据
	 */
	@GetMapping("selectDataBaseType")
	@Log(title = "通用模块", operateType = "获取数据库驱动名称")
	public Map<String, Object> selectDataBaseType() {
		List<Map<String, Object>> list = SimpleCache.get(SimpleCache.DATABASETYPE);
		if (list == null) {
			list = new ArrayList<>();
			for (DataBaseType dataBaseType : DataBaseType.values()) {
				if (DataBaseType.UNKNOW == dataBaseType) {
					continue;
				}
				Map<String, Object> result = new HashMap<>(4);
				result.put("CODE_", dataBaseType.getTypeName());
				result.put("NAME_", dataBaseType.toString());
				list.add(result);
			}
			SimpleCache.put(SimpleCache.DATABASETYPE, list);
		}
		return BaseUtils.success(list);
	}

	/**
	 * 获取GenProcedureModelType的数据
	 */
	@GetMapping("selectGenProcedureModelType")
	@Log(title = "通用模块", operateType = "获取存储过程模板名称")
	public Map<String, Object> selectGenProcedureModelType() {
		List<Map<String, Object>> list = SimpleCache.get(SimpleCache.GENPROCEDUREMODELTYPE);
		if (list == null) {
			list = new ArrayList<>();
			for (GenProcedureModelType genProcedureModelType : GenProcedureModelType.values()) {
				if (genProcedureModelType.getStatus() == 0) {
					continue;
				}
				Map<String, Object> result = new HashMap<>(4);
				result.put("CODE_", genProcedureModelType.getCode());
				result.put("NAME_", genProcedureModelType.getDesc());
				list.add(result);
			}
			SimpleCache.put(SimpleCache.GENPROCEDUREMODELTYPE, list);
		}
		return BaseUtils.success(list);
	}

	/**
	 * 获取GenCodeModelType的数据
	 */
	@GetMapping("selectGenCodeModelType")
	@Log(title = "通用模块", operateType = "获取后台代码模板名称")
	public Map<String, Object> selectGenCodeModelType() {
		List<Map<String, Object>> list = SimpleCache.get(SimpleCache.GENCODEMODELTYPE);
		if (list == null) {
			list = new ArrayList<>();
			for (GenCodeModelType genCodeModelType : GenCodeModelType.values()) {
				if (genCodeModelType.getStatus() == 0) {
					continue;
				}
				Map<String, Object> result = new HashMap<>(4);
				result.put("CODE_", genCodeModelType.getCode());
				result.put("NAME_", genCodeModelType.getDesc());
				list.add(result);
			}
			SimpleCache.put(SimpleCache.GENCODEMODELTYPE, list);
		}
		return BaseUtils.success(list);
	}

	/**
	 * 获取NameConventType的数据
	 */
	@GetMapping("selectNameConventType")
	@Log(title = "通用模块", operateType = "获取命名规范")
	public Map<String, Object> selectNameConventType() {
		List<Map<String, Object>> list = SimpleCache.get(SimpleCache.NAMECONVENTTYPE);
		if (list == null) {
			list = new ArrayList<>();
			for (NameConventType nameConventType : NameConventType.values()) {
				if (nameConventType.getStatus() == 0) {
					continue;
				}
				Map<String, Object> result = new HashMap<>(4);
				result.put("CODE_", nameConventType.getCode());
				result.put("NAME_", nameConventType.getDesc());
				list.add(result);
			}
			SimpleCache.put(SimpleCache.NAMECONVENTTYPE, list);
		}
		return BaseUtils.success(list);
	}

	/**
	 * 获取JavaClass的数据
	 */
	@GetMapping("selectJavaClass")
	@Log(title = "通用模块", operateType = "获取数据库对应java类信息")
	public Map<String, Object> selectJavaClass() {
		List<Map<String, Object>> list = SimpleCache.get(SimpleCache.JAVACLASS);
		if (list == null) {
			list = new ArrayList<>();
			for (JavaClass javaClass : JavaClass.values()) {
				Map<String, Object> result = new HashMap<>(4);
				result.put("CODE_", javaClass.getCode());
				result.put("NAME_", javaClass.toString());
				list.add(result);
			}
			SimpleCache.put(SimpleCache.JAVACLASS, list);
		}
		return BaseUtils.success(list);
	}

	/**
	 * 获取EngineFileType的数据
	 */
	@GetMapping("selectEngineFileType")
	@Log(title = "通用模块", operateType = "获取数据库文档类型信息")
	public Map<String, Object> selectEngineFileType() {
		List<Map<String, Object>> list = SimpleCache.get(SimpleCache.ENGINEFILETYPE);
		if (list == null) {
			list = new ArrayList<>();
			for (EngineFileType engineFileType : EngineFileType.values()) {
				Map<String, Object> result = new HashMap<>(4);
				result.put("CODE_", engineFileType.toString());
				result.put("NAME_", engineFileType.getDesc());
				list.add(result);
			}
			SimpleCache.put(SimpleCache.ENGINEFILETYPE, list);
		}
		return BaseUtils.success(list);
	}

	/**
	 * 获取file文件夹下的文件
	 */
	@GetMapping("downloadFile/{fileName}")
	@Log(title = "通用模块", operateType = "获取静态文件")
	public void downloadFile(@PathVariable String fileName, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 为空返回
		if (StringUtils.isEmpty(fileName) || "favicon.ico".equals(fileName)) {
			return;
		}
		Resource resource = new ClassPathResource("file/" + fileName);
		if (!resource.exists()) {
			throw new FileNotFoundException("文件未找到");
		}
		InputStream inputStream = resource.getInputStream();
		BaseUtils.download(inputStream, fileName, request, response);

	}

}
