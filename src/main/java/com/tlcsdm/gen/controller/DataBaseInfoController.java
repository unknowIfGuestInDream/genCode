package com.tlcsdm.gen.controller;

import com.tlcsdm.common.annotation.Log;
import com.tlcsdm.gen.base.BaseUtils;
import com.tlcsdm.gen.factory.DataSourceUtilFactory;
import com.tlcsdm.gen.service.DataBaseInfoService;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

/**
 * 数据源管理
 *
 * @author: TangLiang
 * @date: 2021/6/20 0:16
 * @since: 1.0
 */
@RestController
@RequiredArgsConstructor
public class DataBaseInfoController {

	private final DataBaseInfoService dataBaseInfoService;

	/**
	 * 管理数据源
	 */
	@GetMapping("/manageDataBaseInfo")
	public ModelAndView manageDataBaseInfo() {
		return new ModelAndView("pages/dataBaseInfo/manageDataBaseInfo");
	}

	/**
	 * 新增数据源
	 */
	@GetMapping("/preInsertDataBaseInfo")
	public ModelAndView preInsertDataBaseInfo() {
		return new ModelAndView("pages/dataBaseInfo/preInsertDataBaseInfo");
	}

	/**
	 * 修改数据源
	 */
	@GetMapping("/preUpdateDataBaseInfo")
	public ModelAndView preUpdateDataBaseInfo() {
		return new ModelAndView("pages/dataBaseInfo/preUpdateDataBaseInfo");
	}

	/**
	 * 加载数据源
	 */
	@GetMapping("loadDataBaseInfo")
	@Log
	public Map<String, Object> loadDataBaseInfo(String ID) {
		return BaseUtils.success(dataBaseInfoService.loadDataBaseInfo(ID));
	}

	/**
	 * 查询数据源
	 */
	@GetMapping("selectDataBaseInfo")
	@Log(title = "数据源管理", operateType = "查询数据源")
	public Map<String, Object> selectDataBaseInfo() {
		return BaseUtils.success(dataBaseInfoService.selectDataBaseInfo());
	}

	/**
	 * 新增数据源
	 */
	@PostMapping("insertDataBaseInfo")
	@Log(title = "数据源管理", operateType = "新增数据源")
	public Map<String, Object> insertDataBaseInfo(String NAME, String URL, String DRIVER, String USERNAME,
			String PASSWORD) throws SQLException {
		return BaseUtils.success(dataBaseInfoService.insertDataBaseInfo(NAME, URL, DRIVER, USERNAME, PASSWORD));
	}

	/**
	 * 修改数据源
	 */
	@PostMapping("updateDataBaseInfo")
	@Log(title = "数据源管理", operateType = "修改数据源")
	public Map<String, Object> updateDataBaseInfo(String ID, String NAME, String URL, String DRIVER, String USERNAME,
			String PASSWORD) throws SQLException {
		DataSourceUtilFactory.remove(URL + USERNAME);
		return BaseUtils.success(dataBaseInfoService.updateDataBaseInfo(ID, NAME, URL, DRIVER, USERNAME, PASSWORD));
	}

	/**
	 * 删除数据源
	 */
	@PostMapping("deleteDataBaseInfo")
	@Log(title = "数据源管理", operateType = "删除数据源")
	public Map<String, Object> deleteDataBaseInfo(String ID, String URL, String USERNAME) {
		DataSourceUtilFactory.remove(URL + USERNAME);
		return BaseUtils.success(dataBaseInfoService.deleteDataBaseInfo(ID));
	}

	/**
	 * 数据源连接测试
	 */
	@PostMapping("testConnect")
	@Log(title = "数据源管理", operateType = "数据源连接测试")
	public Map<String, Object> testConnect(String URL, String DRIVER, String USERNAME, String PASSWORD)
			throws SQLException, ClassNotFoundException {
		@Cleanup
		Connection con = null;
		Class.forName(DRIVER);
		try {
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		if (con != null) {
			return BaseUtils.success();
		}
		else {
			String mes = "Error establishing connection with database";
			return BaseUtils.failed(mes);
		}
	}

}
