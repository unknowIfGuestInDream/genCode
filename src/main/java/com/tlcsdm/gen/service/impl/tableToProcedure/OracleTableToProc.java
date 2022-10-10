package com.tlcsdm.gen.service.impl.tableToProcedure;

import com.tlcsdm.gen.enums.DataBaseType;
import com.tlcsdm.gen.factory.AbstractTableToProcedureFactory;
import com.tlcsdm.gen.service.AbstractTableToProcedure;
import com.tlcsdm.gen.service.NameConventService;
import com.tlcsdm.gen.util.ProcTypes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * oracle表生成存储过程
 *
 * @author: TangLiang
 * @date: 2021/9/10 14:02
 * @since: 1.0
 */
@Service
public class OracleTableToProc extends AbstractTableToProcedure {

	@Override
	protected String getFtlPackageName() {
		return "oracle";
	}

	@Override
	protected void dealGetProcedure(String tableName, List<String> primarys, List<String> primaryTypes,
			List<String> primaryDesc, NameConventService nameConvent, Map<String, Object> objectMap) {
		// 查询条件
		StringBuilder getSqlWhere = new StringBuilder();
		// 存储过程入参
		StringJoiner getInParams = new StringJoiner("\n");
		for (int i = 0, length = primarys.size(); i < length; i++) {
			if (i == 0) {
				getSqlWhere.append(" \n      WHERE");
			}
			else {
				getSqlWhere.append(" \n      AND");
			}
			getInParams.add(nameConvent.genSelProcInParam(primarys.get(i), primaryTypes.get(i), primaryDesc.get(i), 0));
			getSqlWhere.append(nameConvent.genSelProcSqlWhere(primarys.get(i), primaryTypes.get(i), 0));
		}
		// 添加出参字段, 出参类型和出参信息
		getInParams.add(nameConvent.getProcOutParamName("message") + " OUT VARCHAR2, --成功执行信息为：‘success’，失败执行信息为错误信息");
		getInParams.add(nameConvent.getProcOutParamName("result") + " OUT SYS_REFCURSOR --成功返回结果集");
		// 赋值
		objectMap.put("getProcName", nameConvent.getProcName(tableName, ProcTypes.GET));
		objectMap.put("getInParams", getInParams.toString());
		objectMap.putIfAbsent("getSqlWhere", getSqlWhere.toString());
	}

	@Override
	protected void dealSelProcedure(String tableName, List<String> selParams, List<String> selParamTypes,
			List<String> selParamDescs, List<Integer> selType, NameConventService nameConvent,
			Map<String, Object> objectMap) {
		// 查询条件
		StringBuilder selectSqlWhere = new StringBuilder();
		// 存储过程入参
		StringJoiner selectInParams = new StringJoiner("\n");
		for (int i = 0, length = selParams.size(); i < length; i++) {
			if (i == 0) {
				selectSqlWhere.append(" \n      WHERE");
			}
			else {
				selectSqlWhere.append(" \n      AND");
			}
			selectInParams.add(nameConvent.genSelProcInParam(selParams.get(i), selParamTypes.get(i),
					selParamDescs.get(i), selType.get(i)));
			selectSqlWhere
					.append(nameConvent.genSelProcSqlWhere(selParams.get(i), selParamTypes.get(i), selType.get(i)));
		}
		// 添加出参字段, 出参类型和出参信息
		selectInParams
				.add(nameConvent.getProcOutParamName("message") + " OUT VARCHAR2, --成功执行信息为：‘success’，失败执行信息为错误信息");
		selectInParams.add(nameConvent.getProcOutParamName("result") + " OUT SYS_REFCURSOR --成功返回结果集");
		// 赋值
		objectMap.put("selProcName", nameConvent.getProcName(tableName, ProcTypes.SELECT));
		objectMap.put("selectInParams", selectInParams.toString());
		objectMap.put("selectSqlWhere", selectSqlWhere.toString());
	}

	@Override
	protected void dealSelWithPageProcedure(String tableName, List<String> selParams, List<String> selParamTypes,
			List<String> selParamDescs, List<Integer> selType, NameConventService nameConvent,
			Map<String, Object> objectMap) {
		// 查询条件
		StringBuilder selWithPageSqlWhere = new StringBuilder();
		// 存储过程入参
		StringJoiner selectWithPageInParams = new StringJoiner("\n");
		for (int i = 0, length = selParams.size(); i < length; i++) {
			if (i == 0) {
				selWithPageSqlWhere.append(" \n                  WHERE");
			}
			else {
				selWithPageSqlWhere.append(" \n                  AND");
			}
			selectWithPageInParams.add(nameConvent.genSelProcInParam(selParams.get(i), selParamTypes.get(i),
					selParamDescs.get(i), selType.get(i)));
			selWithPageSqlWhere
					.append(nameConvent.genSelProcSqlWhere(selParams.get(i), selParamTypes.get(i), selType.get(i)));
		}
		// 添加出参字段, 出参类型和出参信息以及入参分页相关参数
		String page = nameConvent.getProcOutParamName("page");
		String limit = nameConvent.getProcOutParamName("limit");
		selectWithPageInParams.add(page + " IN VARCHAR2, --页数");
		selectWithPageInParams.add(limit + " IN VARCHAR2, --每页显示条数");
		selectWithPageInParams.add(nameConvent.getProcOutParamName("total") + " OUT VARCHAR2, --返回总条数");
		selectWithPageInParams.add(nameConvent.getProcOutParamName("result") + " OUT SYS_REFCURSOR, --成功返回结果集");
		selectWithPageInParams
				.add(nameConvent.getProcOutParamName("message") + " OUT VARCHAR2 --成功执行信息为：‘success’，失败执行信息为错误信息");
		// 赋值
		objectMap.putIfAbsent("selProcName", nameConvent.getProcName(tableName, ProcTypes.SELECT));
		objectMap.put("selectWithPageInParams", selectWithPageInParams.toString());
		objectMap.put("selWithPageSqlWhere", selWithPageSqlWhere.toString());
	}

	@Override
	protected void dealInsProcedure(String tableName, List<String> insParams, List<String> insParamTypes,
			List<String> insParamDescs, NameConventService nameConvent, Map<String, Object> objectMap) {
		// 存储过程入参
		StringJoiner insInParams = new StringJoiner("\n");
		// 新增语句字段
		StringJoiner insField = new StringJoiner(", ");
		// 新增语句传参参数
		StringJoiner insValueParams = new StringJoiner(", ");
		for (int i = 0, length = insParams.size(); i < length; i++) {
			insField.add(insParams.get(i));
			insValueParams.add(nameConvent.genProcInParamName(insParams.get(i), insParamTypes.get(i)));
			insInParams.add(
					nameConvent.genSelProcInParam(insParams.get(i), insParamTypes.get(i), insParamDescs.get(i), 0));
		}
		// 添加出参字段, 出参类型和出参信息
		String message = nameConvent.getProcOutParamName("message");
		insInParams.add(message + " OUT VARCHAR2 --成功执行信息为：‘success’，失败执行信息为错误信息");
		// 赋值
		objectMap.put("insProcName", nameConvent.getProcName(tableName, ProcTypes.INSERT));
		objectMap.put("insInParams", insInParams.toString());
		objectMap.putIfAbsent("insField", insField.toString());
		objectMap.putIfAbsent("insValueParams", insValueParams.toString());
	}

	@Override
	protected void dealUpdProcedure(String tableName, List<String> primarys, List<String> primaryTypes,
			List<String> primaryDesc, List<String> updParams, List<String> updParamTypes, List<String> updParamDescs,
			NameConventService nameConvent, Map<String, Object> objectMap) {
		// 修改语句查询条件
		StringBuilder getSqlWhere = new StringBuilder();
		for (int i = 0, length = primarys.size(); i < length; i++) {
			if (i == 0) {
				getSqlWhere.append(" \n      WHERE");
			}
			else {
				getSqlWhere.append(" \n      AND");
			}
			getSqlWhere.append(nameConvent.genSelProcSqlWhere(primarys.get(i), primaryTypes.get(i), 0));
		}
		// 存储过程入参
		StringJoiner updInParams = new StringJoiner("\n");
		// 修改语句传参参数
		StringJoiner updSqlParams = new StringJoiner(",\n        ");
		for (int i = 0, length = primarys.size(); i < length; i++) {
			updInParams.add(nameConvent.genSelProcInParam(primarys.get(i), primaryTypes.get(i), primaryDesc.get(i), 0));
		}
		for (int i = 0, length = updParams.size(); i < length; i++) {
			updSqlParams.add(
					updParams.get(i) + " = " + nameConvent.genProcInParamName(updParams.get(i), updParamTypes.get(i)));
			updInParams.add(
					nameConvent.genSelProcInParam(updParams.get(i), updParamTypes.get(i), updParamDescs.get(i), 0));
		}
		// 添加出参字段, 出参类型和出参信息
		String message = nameConvent.getProcOutParamName("message");
		updInParams.add(message + " OUT VARCHAR2 --成功执行信息为：‘success’，失败执行信息为错误信息");
		// 赋值
		objectMap.put("updProcName", nameConvent.getProcName(tableName, ProcTypes.UPDATE));
		objectMap.put("updInParams", updInParams.toString());
		objectMap.putIfAbsent("updSqlParams", updSqlParams.toString());
		objectMap.putIfAbsent("getSqlWhere", getSqlWhere.toString());
	}

	@Override
	protected void dealSaveProcedure(String tableName, List<String> insParams, List<String> insParamTypes,
			List<String> insParamDescs, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc,
			List<String> updParams, List<String> updParamTypes, List<String> updParamDescs,
			NameConventService nameConvent, Map<String, Object> objectMap) {
		// 修改语句查询条件
		StringBuilder getSqlWhere = new StringBuilder();
		for (int i = 0, length = primarys.size(); i < length; i++) {
			if (i == 0) {
				getSqlWhere.append(" \n      WHERE");
			}
			else {
				getSqlWhere.append(" \n      AND");
			}
			getSqlWhere.append(nameConvent.genSelProcSqlWhere(primarys.get(i), primaryTypes.get(i), 0));
		}
		// 存储过程入参 以修改传参为主
		StringJoiner saveInParams = new StringJoiner("\n");
		// 修改语句传参参数
		StringJoiner updSqlParams = new StringJoiner(",\n        ");
		for (int i = 0, length = primarys.size(); i < length; i++) {
			saveInParams
					.add(nameConvent.genSelProcInParam(primarys.get(i), primaryTypes.get(i), primaryDesc.get(i), 0));
		}
		for (int i = 0, length = updParams.size(); i < length; i++) {
			updSqlParams.add(
					updParams.get(i) + " = " + nameConvent.genProcInParamName(updParams.get(i), updParamTypes.get(i)));
			saveInParams.add(
					nameConvent.genSelProcInParam(updParams.get(i), updParamTypes.get(i), updParamDescs.get(i), 0));
		}
		// 新增语句字段
		StringJoiner insField = new StringJoiner(", ");
		// 新增语句传参参数
		StringJoiner insValueParams = new StringJoiner(", ");
		for (int i = 0, length = insParams.size(); i < length; i++) {
			insField.add(insParams.get(i));
			insValueParams.add(nameConvent.genProcInParamName(insParams.get(i), insParamTypes.get(i)));
		}
		// 添加出参字段, 出参类型和出参信息
		String message = nameConvent.getProcOutParamName("message");
		saveInParams.add(message + " OUT VARCHAR2 --成功执行信息为：‘success’，失败执行信息为错误信息");
		// 赋值
		objectMap.put("saveProcName", nameConvent.getProcName(tableName, ProcTypes.SAVE));
		objectMap.put("saveInParams", saveInParams.toString());
		objectMap.putIfAbsent("updSqlParams", updSqlParams.toString());
		objectMap.putIfAbsent("getSqlWhere", getSqlWhere.toString());
		objectMap.putIfAbsent("insValueParams", insValueParams.toString());
		objectMap.putIfAbsent("insField", getSqlWhere.toString());
	}

	@Override
	protected void dealDelProcedure(String tableName, List<String> primarys, List<String> primaryTypes,
			List<String> primaryDesc, NameConventService nameConvent, Map<String, Object> objectMap) {
		// 查询条件
		StringBuilder delSqlWhere = new StringBuilder();
		// 存储过程入参
		StringJoiner delInParams = new StringJoiner("\n");
		for (int i = 0, length = primarys.size(); i < length; i++) {
			if (i == 0) {
				delSqlWhere.append(" \n         WHERE");
			}
			else {
				delSqlWhere.append(" \n         AND");
			}
			delInParams.add(nameConvent.genSelProcInParam(primarys.get(i), primaryTypes.get(i), primaryDesc.get(i), 0));
			delSqlWhere.append(nameConvent.genSelProcSqlWhere(primarys.get(i), primaryTypes.get(i), 0));
		}
		// 添加出参字段, 出参类型和出参信息
		delInParams.add(nameConvent.getProcOutParamName("message") + " OUT VARCHAR2 --成功执行信息为：‘success’，失败执行信息为错误信息");
		// 赋值
		objectMap.put("delProcName", nameConvent.getProcName(tableName, ProcTypes.DELETE));
		objectMap.put("delInParams", delInParams.toString());
		objectMap.put("delSqlWhere", delSqlWhere.toString());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		AbstractTableToProcedureFactory.register(DataBaseType.ORACLE, this);
	}

}
