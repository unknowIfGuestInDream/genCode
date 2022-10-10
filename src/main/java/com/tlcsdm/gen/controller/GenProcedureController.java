package com.tlcsdm.gen.controller;

import com.tlcsdm.common.annotation.Log;
import com.tlcsdm.gen.base.BaseUtils;
import com.tlcsdm.gen.base.CacheManage;
import com.tlcsdm.gen.enums.DataBaseType;
import com.tlcsdm.gen.enums.GenProcedureModelType;
import com.tlcsdm.gen.enums.NameConventType;
import com.tlcsdm.gen.factory.AbstractGenProcedureModelFactory;
import com.tlcsdm.gen.factory.DataBaseProcedureFactory;
import com.tlcsdm.gen.factory.DataSourceUtilFactory;
import com.tlcsdm.gen.factory.NameConventFactory;
import com.tlcsdm.gen.service.AbstractGenProcedureModel;
import com.tlcsdm.gen.service.DataBaseProcedureService;
import com.tlcsdm.gen.service.NameConventService;
import com.tlcsdm.gen.util.cache.Cache;
import com.tlcsdm.gen.util.dataSource.DataSourceUtil;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 存储过程代码生成
 *
 * @author: TangLiang
 * @date: 2021/6/20 10:05
 * @since: 1.0
 */
@RestController
@RequiredArgsConstructor
public class GenProcedureController {

	private final FreeMarkerConfigurer freeMarkerConfigurer;

	/**
	 * 代码生成页面
	 */
	@GetMapping("/manageGenerate")
	public ModelAndView manageGenerate() {
		return new ModelAndView("pages/codeGenerate/manageGenerate");
	}

	/**
	 * 查询数据库中的过程信息
	 * @param url 数据库url 用于获取数据库连接
	 * @param driver 数据库驱动 用于获取存储过程sql
	 * @param userName 数据库账户
	 * @param password 数据库密码
	 * @param name 过程名 模糊查询
	 */
	@GetMapping("selectProcedures")
	@Log(title = "存储过程代码生成", operateType = "查询数据库中的过程信息")
	public Map<String, Object> selectProcedures(String url, String driver, String userName, String password,
			@RequestParam(required = false, defaultValue = "") String name) {
		List<Map<String, Object>> list = CacheManage.PROCEDURES_CACHE
				.get(url.replaceAll("/", "") + userName + name + "procedures");
		// 缓存方案 url+用户名+查询条件为主键
		// 存储过程名称条件为空代表全查询 缓存一天，否则缓存一分钟
		if (list == null) {
			DataSourceUtil dataSourceUtil = DataSourceUtilFactory.getDataSourceUtil(url, driver, userName, password);
			DataBaseProcedureService dbProcedure = DataBaseProcedureFactory
					.getDataBaseProcedure(DataBaseType.fromTypeName(driver));
			list = dataSourceUtil.executeQuery(dbProcedure.selectProcedures(name));
			CacheManage.PROCEDURES_CACHE.put(url.replaceAll("/", "") + userName + name + "procedures", list,
					StringUtils.isEmpty(name) ? Cache.CACHE_HOLD_FOREVER : Cache.CACHE_HOLD_30MINUTE);
		}
		return BaseUtils.success(list);
	}

	/**
	 * 加载过程信息
	 * @param url 数据库url 用于获取数据库连接
	 * @param driver 数据库驱动 用于获取存储过程sql
	 * @param userName 数据库账户
	 * @param password 数据库密码
	 * @param name 存储过程名称
	 */
	@GetMapping("loadProcedureInfo")
	@Log(title = "存储过程代码生成", operateType = "加载过程信息")
	public Map<String, Object> loadProcedureInfo(String url, String driver, String userName, String password,
			String name) {
		// 获取数据库连接
		DataSourceUtil dataSourceUtil = DataSourceUtilFactory.getDataSourceUtil(url, driver, userName, password);
		// 获取数据库过程sql
		DataBaseProcedureService dbProcedure = DataBaseProcedureFactory
				.getDataBaseProcedure(DataBaseType.fromTypeName(driver));
		String result = dbProcedure.loadProcedure(name, dataSourceUtil);
		return BaseUtils.success(result);
	}

	/**
	 * 生成代码
	 * @param moduleName 模块名称
	 * @param genProcedureModelType 生成代码模版类型
	 * @param nameConventType 命名规范类型
	 * @param packageName 包名
	 * @param url 数据库url 用于获取数据库连接
	 * @param driver 数据库驱动 用于获取存储过程sql
	 * @param userName 数据库账户
	 * @param password 数据库密码
	 * @param procedureNameList 存储过程名称集合
	 */
	@PostMapping("genProcedure")
	@Log(title = "存储过程代码生成", operateType = "生成代码")
	public Map<String, Object> genProcedure(String moduleName, String genProcedureModelType, String nameConventType,
			String packageName, String url, String driver, String userName, String password,
			@RequestParam("procedureNameList") List<String> procedureNameList,
			@RequestParam(required = false, defaultValue = "admin") String author) {
		moduleName = BaseUtils.toUpperCase4Index(moduleName);
		// 获取数据库连接，为空则创建
		DataSourceUtil dataSourceUtil = DataSourceUtilFactory.getDataSourceUtil(url, driver, userName, password);
		// 获取生成代码模版
		AbstractGenProcedureModel genProcedureModel = AbstractGenProcedureModelFactory
				.getGenProcedureModel(GenProcedureModelType.fromCode(genProcedureModelType));
		// 获取命名规范
		NameConventService nameConvent = NameConventFactory.getNameConvent(NameConventType.fromCode(nameConventType));
		// 获取数据库过程sql
		DataBaseProcedureService dbProcedure = DataBaseProcedureFactory
				.getDataBaseProcedure(DataBaseType.fromTypeName(driver));
		Map<String, Object> result = genProcedureModel.genCode(moduleName, packageName, userName, procedureNameList,
				author, nameConvent, dbProcedure, dataSourceUtil, freeMarkerConfigurer.getConfiguration());
		return BaseUtils.success(result);
	}

	/**
	 * 生成代码下载
	 */
	@GetMapping("downloadCode")
	@Log(title = "存储过程代码生成", operateType = "生成代码下载")
	public void downloadCode(String moduleName, String genProcedureModelType, String nameConventType,
			String packageName, String url, String driver, String userName, String password,
			@RequestParam("procedureNameList") List<String> procedureNameList, String author,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String zipName = moduleName + ".zip";
			moduleName = BaseUtils.toUpperCase4Index(moduleName);
			// 获取数据库连接，为空则创建
			DataSourceUtil dataSourceUtil = DataSourceUtilFactory.getDataSourceUtil(url, driver, userName, password);
			response.reset();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + BaseUtils.getFormatString(request, zipName));

			ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
			// 获取生成代码模版
			AbstractGenProcedureModel genProcedureModel = AbstractGenProcedureModelFactory
					.getGenProcedureModel(GenProcedureModelType.fromCode(genProcedureModelType));
			// 获取命名规范
			NameConventService nameConvent = NameConventFactory
					.getNameConvent(NameConventType.fromCode(nameConventType));
			// 获取数据库过程sql
			DataBaseProcedureService dbProcedure = DataBaseProcedureFactory
					.getDataBaseProcedure(DataBaseType.fromTypeName(driver));
			Map<String, Object> map = genProcedureModel.genCode(moduleName, packageName, userName, procedureNameList,
					author, nameConvent, dbProcedure, dataSourceUtil, freeMarkerConfigurer.getConfiguration());
			List<String> list = (List<String>) map.get("list");
			for (String name : list) {
				String fileName;
				if ("BaseUtils".equals(name) || "ProcedureUtils".equals(name)) {
					continue;
				}
				else {
					fileName = moduleName + BaseUtils.toUpperCase4Index(name) + ".java";
				}
				@Cleanup
				InputStream inputStream = new ByteArrayInputStream(
						map.get(name).toString().getBytes(StandardCharsets.UTF_8));
				// 将文件写入zip内，即将文件进行打包
				zos.putNextEntry(new ZipEntry(fileName));
				inputStream.transferTo(zos);
				// 关闭输入输出流
				zos.closeEntry();
			}
			zos.close();
		}
		catch (IOException e) {
			BaseUtils.callbackNotFound(response, e);
		}
	}

}
