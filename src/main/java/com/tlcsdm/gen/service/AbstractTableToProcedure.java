package com.tlcsdm.gen.service;

import com.tlcsdm.gen.util.template.AbstractFreeMarkerTemplate;
import freemarker.template.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import java.util.*;

/**
 * 表生成存储过程
 *
 * @author: TangLiang
 * @date: 2021/9/9 21:40
 * @since: 1.0
 */
public abstract class AbstractTableToProcedure extends AbstractFreeMarkerTemplate implements InitializingBean {

	@Override
	protected String getRootPackageName() {
		return "tableToProcedure";
	}

	/**
	 * 处理存储过程基本信息
	 * @param tableName 表名
	 * @param tableDesc 表描述信息
	 * @param nameConvent 命名规范
	 * @param objectMap 过程模版值
	 */
	protected void dealCommonProcedure(String tableName, String tableDesc, NameConventService nameConvent,
			Map<String, Object> objectMap) {
		objectMap.put("tableName", tableName);
		objectMap.put("tableDesc", tableDesc);
		objectMap.put("result", nameConvent.getProcOutParamName("result"));
		objectMap.put("message", nameConvent.getProcOutParamName("message"));
		objectMap.put("page", nameConvent.getProcOutParamName("page"));
		objectMap.put("limit", nameConvent.getProcOutParamName("limit"));
		objectMap.put("total", nameConvent.getProcOutParamName("total"));
	}

	/**
	 * 生成加载过程值
	 * @param tableName 表名
	 * @param primarys 主键集合
	 * @param primaryTypes 主键数据库类型集合
	 * @param primaryDesc 字段描述
	 * @param nameConvent 命名规范
	 * @param objectMap 过程模版值
	 */
	protected abstract void dealGetProcedure(String tableName, List<String> primarys, List<String> primaryTypes,
			List<String> primaryDesc, NameConventService nameConvent, Map<String, Object> objectMap);

	/**
	 * 生成查询过程值(无分页)
	 * @param tableName 表名
	 * @param selParams 参数
	 * @param selParamTypes 参数类型
	 * @param selParamDescs 字段描述
	 * @param selType 查询类型(0精确/1模糊/2区间查询)
	 * @param nameConvent 命名规范
	 * @param objectMap 过程模版值
	 */
	protected abstract void dealSelProcedure(String tableName, List<String> selParams, List<String> selParamTypes,
			List<String> selParamDescs, List<Integer> selType, NameConventService nameConvent,
			Map<String, Object> objectMap);

	/**
	 * 生成查询过程值(有分页)
	 * @param tableName 表名
	 * @param selParams 参数
	 * @param selParamTypes 参数类型
	 * @param selParamDescs 字段描述
	 * @param selType 查询类型(0精确/1模糊/2区间查询)
	 * @param nameConvent 命名规范
	 * @param objectMap 过程模版值
	 */
	protected abstract void dealSelWithPageProcedure(String tableName, List<String> selParams,
			List<String> selParamTypes, List<String> selParamDescs, List<Integer> selType,
			NameConventService nameConvent, Map<String, Object> objectMap);

	/**
	 * 生成新增过程值
	 * @param tableName 表名
	 * @param insParams 新增过程参数
	 * @param insParamTypes 新增过程参数类型
	 * @param insParamDescs 新增过程字段描述
	 * @param nameConvent 命名规范
	 * @param objectMap 过程模版值
	 */
	protected abstract void dealInsProcedure(String tableName, List<String> insParams, List<String> insParamTypes,
			List<String> insParamDescs, NameConventService nameConvent, Map<String, Object> objectMap);

	/**
	 * 生成修改过程值
	 * @param tableName 表名
	 * @param primarys 主键集合
	 * @param primaryTypes 主键数据库类型集合
	 * @param primaryDesc 字段描述
	 * @param updParams 参数
	 * @param updParamTypes 参数类型
	 * @param updParamDescs 字段描述
	 * @param nameConvent 命名规范
	 * @param objectMap 过程模版值
	 */
	protected abstract void dealUpdProcedure(String tableName, List<String> primarys, List<String> primaryTypes,
			List<String> primaryDesc, List<String> updParams, List<String> updParamTypes, List<String> updParamDescs,
			NameConventService nameConvent, Map<String, Object> objectMap);

	/**
	 * 生成保存过程值
	 * @param tableName 表名
	 * @param insParams 新增过程参数
	 * @param insParamTypes 新增过程参数类型
	 * @param insParamDescs 新增过程字段描述
	 * @param primarys 主键集合
	 * @param primaryTypes 主键数据库类型集合
	 * @param primaryDesc 字段描述
	 * @param updParams 参数
	 * @param updParamTypes 参数类型
	 * @param updParamDescs 字段描述
	 * @param nameConvent 命名规范
	 * @param objectMap 过程模版值
	 */
	protected abstract void dealSaveProcedure(String tableName, List<String> insParams, List<String> insParamTypes,
			List<String> insParamDescs, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc,
			List<String> updParams, List<String> updParamTypes, List<String> updParamDescs,
			NameConventService nameConvent, Map<String, Object> objectMap);

	/**
	 * 生成删除过程值
	 * @param tableName 表名
	 * @param primarys 主键集合
	 * @param primaryTypes 主键数据库类型集合
	 * @param primaryDesc 字段描述
	 * @param nameConvent 命名规范
	 * @param objectMap 过程模版值
	 */
	protected abstract void dealDelProcedure(String tableName, List<String> primarys, List<String> primaryTypes,
			List<String> primaryDesc, NameConventService nameConvent, Map<String, Object> objectMap);

	/**
	 * 根据表生成存储过程
	 * @param tableName 表名
	 * @param tableDesc 表描述
	 * @param params 参数
	 * @param paramTypes 参数类型
	 * @param paramDescs 字段描述
	 * @param priParamIndex 主键列索引
	 * @param selParamsIndex 查询列索引
	 * @param selType 查询类型(0精确/1模糊/2区间查询)
	 * @param insParamIndex 新增列索引
	 * @param updParamIndex 修改列索引
	 * @param orderParamIndex 排序列索引
	 * @param orderParamTypes 排序类型
	 * @param nameConvent 命名规范
	 * @param configuration ftl模板引擎配置
	 */
	public Map<String, Object> genProceduresByTable(String tableName, String tableDesc, List<String> params,
			List<String> paramTypes, List<String> paramDescs, List<Integer> priParamIndex, List<Integer> selParamsIndex,
			List<Integer> selType, List<Integer> insParamIndex, List<Integer> updParamIndex,
			List<Integer> orderParamIndex, List<String> orderParamTypes, NameConventService nameConvent,
			Configuration configuration) {
		// 一个集合包含所有字段，以及其它相关的存储集合的索引（0开始）
		// 通过遍历所有字段集合来为相关集合赋值
		int priLength = priParamIndex.size();
		int selLength = selParamsIndex == null ? 0 : selParamsIndex.size();
		int insLength = insParamIndex.size();
		int updLength = updParamIndex.size();
		int orderLength = orderParamIndex == null ? 0 : orderParamIndex.size();
		List<String> primarys = new ArrayList<>(priLength);
		List<String> primaryTypes = new ArrayList<>(priLength);
		List<String> primaryDesc = new ArrayList<>(priLength);
		List<String> selParams = new ArrayList<>(selLength);
		List<String> selParamTypes = new ArrayList<>(selLength);
		List<String> selParamDescs = new ArrayList<>(selLength);
		List<String> insParams = new ArrayList<>(insLength);
		List<String> insParamTypes = new ArrayList<>(insLength);
		List<String> insParamDescs = new ArrayList<>(insLength);
		List<String> updParams = new ArrayList<>(updLength);
		List<String> updParamTypes = new ArrayList<>(updLength);
		List<String> updParamDescs = new ArrayList<>(updLength);
		List<String> orderParams = new ArrayList<>(orderLength);

		for (int i = 0, length = params.size(); i < length; i++) {
			if (i < priLength) {
				primarys.add(params.get(priParamIndex.get(i)));
				primaryTypes.add(paramTypes.get(priParamIndex.get(i)));
				primaryDesc.add(paramDescs.get(priParamIndex.get(i)));
			}
			if (i < selLength) {
				selParams.add(params.get(selParamsIndex.get(i)));
				selParamTypes.add(paramTypes.get(selParamsIndex.get(i)));
				selParamDescs.add(paramDescs.get(selParamsIndex.get(i)));
			}
			if (i < insLength) {
				insParams.add(params.get(insParamIndex.get(i)));
				insParamTypes.add(paramTypes.get(insParamIndex.get(i)));
				insParamDescs.add(paramDescs.get(insParamIndex.get(i)));
			}
			if (i < updLength) {
				updParams.add(params.get(updParamIndex.get(i)));
				updParamTypes.add(paramTypes.get(updParamIndex.get(i)));
				updParamDescs.add(paramDescs.get(updParamIndex.get(i)));
			}
			if (i < orderLength) {
				orderParams.add(params.get(orderParamIndex.get(i)));
			}
			if (i >= priLength && i >= selLength && i >= insLength && i >= updLength && i >= orderLength) {
				break;
			}
		}

		// 模版值
		Map<String, Object> objectMap = new HashMap<>(64);
		dealCommonProcedure(tableName, tableDesc, nameConvent, objectMap);
		dealGetProcedure(tableName, primarys, primaryTypes, primaryDesc, nameConvent, objectMap);
		dealSelProcedure(tableName, selParams, selParamTypes, selParamDescs, selType, nameConvent, objectMap);
		dealSelWithPageProcedure(tableName, selParams, selParamTypes, selParamDescs, selType, nameConvent, objectMap);
		dealSelOrderBy(orderParams, orderParamTypes, objectMap);
		dealInsProcedure(tableName, insParams, insParamTypes, insParamDescs, nameConvent, objectMap);
		dealUpdProcedure(tableName, primarys, primaryTypes, primaryDesc, updParams, updParamTypes, updParamDescs,
				nameConvent, objectMap);
		dealSaveProcedure(tableName, insParams, insParamTypes, insParamDescs, primarys, primaryTypes, primaryDesc,
				updParams, updParamTypes, updParamDescs, nameConvent, objectMap);
		dealDelProcedure(tableName, primarys, primaryTypes, primaryDesc, nameConvent, objectMap);
		// 返回结果
		Map<String, Object> result = new HashMap<>(16);
		// tab页集合, 保证返回顺序
		List<String> list = new ArrayList<>(
				Arrays.asList("get", "select", "selectWithPage", "insert", "update", "save", "delete"));
		result.put("list", list);
		result.put("get", getGetProcedure(configuration, objectMap));
		result.put("select", getSelProcedure(configuration, objectMap));
		result.put("selectWithPage", getSelWithPageProcedure(configuration, objectMap));
		result.put("insert", getInsProcedure(configuration, objectMap));
		result.put("update", getUpdProcedure(configuration, objectMap));
		result.put("save", getSaveProcedure(configuration, objectMap));
		result.put("delete", getDelProcedure(configuration, objectMap));
		return result;
	}

	/**
	 * 构建排序sql
	 * @param orderParams 排序参数
	 * @param orderParamTypes 排序类型（DESC/ASC）
	 * @param objectMap 过程模版值
	 */
	protected void dealSelOrderBy(List<String> orderParams, List<String> orderParamTypes,
			Map<String, Object> objectMap) {
		if (orderParams.size() == 0)
			return;
		StringBuilder orderBy = new StringBuilder();
		StringJoiner sj = new StringJoiner(", ");
		orderBy.append("\n      ORDER BY ");
		for (int i = 0, length = orderParams.size(); i < length; i++) {
			sj.add(orderParams.get(i) + " " + orderParamTypes.get(i));
		}
		orderBy.append(sj.toString());
		objectMap.put("orderBy", orderBy.toString());
	}

	/**
	 * 生成自增主键
	 * @param tableName 表名
	 * @param primaryKey 主键字段名
	 * @param configuration ftl模板引擎配置
	 */
	public Map<String, Object> genAutoInsKey(String tableName, String primaryKey, Configuration configuration) {
		Map<String, Object> result = new HashMap<>(4);
		Map<String, Object> objectMap = new HashMap<>(4);
		objectMap.put("tableName", tableName);
		objectMap.put("primaryKey", StringUtils.isEmpty(primaryKey) ? "I_ID" : primaryKey);
		result.put("autoInsKey", getAutoInsKey(configuration, objectMap));
		return result;
	}

	/**
	 * 获取加载数据过程
	 */
	protected String getGetProcedure(Configuration configuration, Map<String, Object> objectMap) {
		return getFtlModel(configuration, objectMap, "get.ftl");
	}

	/**
	 * 获取查询过程
	 */
	protected String getSelProcedure(Configuration configuration, Map<String, Object> objectMap) {
		return getFtlModel(configuration, objectMap, "select.ftl");
	}

	/**
	 * 获取分页查询过程
	 */
	protected String getSelWithPageProcedure(Configuration configuration, Map<String, Object> objectMap) {
		return getFtlModel(configuration, objectMap, "selectWithPage.ftl");
	}

	/**
	 * 获取新增过程
	 */
	protected String getInsProcedure(Configuration configuration, Map<String, Object> objectMap) {
		return getFtlModel(configuration, objectMap, "insert.ftl");
	}

	/**
	 * 获取修改过程
	 */
	protected String getUpdProcedure(Configuration configuration, Map<String, Object> objectMap) {
		return getFtlModel(configuration, objectMap, "update.ftl");
	}

	/**
	 * 获取保存过程
	 */
	protected String getSaveProcedure(Configuration configuration, Map<String, Object> objectMap) {
		return getFtlModel(configuration, objectMap, "save.ftl");
	}

	/**
	 * 获取删除过程
	 */
	protected String getDelProcedure(Configuration configuration, Map<String, Object> objectMap) {
		return getFtlModel(configuration, objectMap, "delete.ftl");
	}

	/**
	 * 获取自增主键
	 */
	protected String getAutoInsKey(Configuration configuration, Map<String, Object> objectMap) {
		return getFtlModel(configuration, objectMap, "autoInsKey.ftl");
	}

}
