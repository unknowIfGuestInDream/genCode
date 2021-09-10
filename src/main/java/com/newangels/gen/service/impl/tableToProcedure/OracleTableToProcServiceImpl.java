package com.newangels.gen.service.impl.tableToProcedure;

import cn.hutool.core.util.StrUtil;
import com.newangels.gen.enums.DataBaseType;
import com.newangels.gen.factory.DataBaseTableToProcFactory;
import com.newangels.gen.service.NameConventService;
import com.newangels.gen.service.TableToProcedureService;
import com.newangels.gen.util.ProcTypes;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * oracle表生成存储过程
 *
 * @author: TangLiang
 * @date: 2021/7/23 9:55
 * @since: 1.0
 */
@Service
@Deprecated
public class OracleTableToProcServiceImpl implements TableToProcedureService {

    @Override
    public String genGetProcedure(String tableName, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, NameConventService nameConvent) {
        //查询条件
        StringBuilder sqlWhere = new StringBuilder();
        //存储过程入参
        StringJoiner inParams = new StringJoiner("\n");
        for (int i = 0, length = primarys.size(); i < length; i++) {
            if (i == 0) {
                sqlWhere.append(" \nWHERE");
            } else {
                sqlWhere.append(" \nAND");
            }
            inParams.add(nameConvent.genSelProcInParam(primarys.get(i), primaryTypes.get(i), primaryDesc.get(i), 0));
            sqlWhere.append(nameConvent.genSelProcSqlWhere(primarys.get(i), primaryTypes.get(i), 0));
        }
        //添加出参字段, 出参类型和出参信息
        inParams.add(nameConvent.getProcOutParamName("message") + " OUT VARCHAR2, --成功执行信息为：‘success’，失败执行信息为错误信息");
        inParams.add(nameConvent.getProcOutParamName("result") + " OUT SYS_REFCURSOR --成功返回结果集");
        //存储过程名称
        String procName = nameConvent.getProcName(tableName, ProcTypes.GET);
        //存储过程
        StringBuilder proc = new StringBuilder(128);
        proc.append("CREATE OR REPLACE PROCEDURE " + procName + "\n");
        proc.append("(\n");
        proc.append(inParams.toString() + "\n");
        proc.append(") IS\n");
        proc.append("BEGIN\n");
        proc.append("  BEGIN\n");
        proc.append("    OPEN " + nameConvent.getProcOutParamName("result") + " FOR\n");
        proc.append("      SELECT T.* FROM " + tableName.toUpperCase() + " T" + sqlWhere.toString() + ";\n");
        proc.append("  " + nameConvent.getProcOutParamName("message") + " := 'success';\n");
        proc.append("  EXCEPTION\n");
        proc.append("    WHEN OTHERS THEN\n");
        proc.append("      " + nameConvent.getProcOutParamName("message") + " := sqlerrm;\n");
        proc.append("  END;\n");
        proc.append("END " + procName + ";");
        return proc.toString();
    }

    @Override
    public String genSelProcedure(String tableName, List<String> selParams, List<String> selParamTypes, List<String> selParamDescs, List<Integer> selType, NameConventService nameConvent) {
        //查询条件
        StringBuilder sqlWhere = new StringBuilder();
        //存储过程入参
        StringJoiner inParams = new StringJoiner("\n");
        for (int i = 0, length = selParams.size(); i < length; i++) {
            if (i == 0) {
                sqlWhere.append(" \nWHERE");
            } else {
                sqlWhere.append(" \nAND");
            }
            inParams.add(nameConvent.genSelProcInParam(selParams.get(i), selParamTypes.get(i), selParamDescs.get(i), selType.get(i)));
            sqlWhere.append(nameConvent.genSelProcSqlWhere(selParams.get(i), selParamTypes.get(i), selType.get(i)));
        }
        //添加出参字段, 出参类型和出参信息
        inParams.add(nameConvent.getProcOutParamName("message") + " OUT VARCHAR2, --成功执行信息为：‘success’，失败执行信息为错误信息");
        inParams.add(nameConvent.getProcOutParamName("result") + " OUT SYS_REFCURSOR --成功返回结果集");
        //存储过程名称
        String procName = nameConvent.getProcName(tableName, ProcTypes.SELECT);
        //存储过程
        StringBuilder proc = new StringBuilder(128);
        proc.append("CREATE OR REPLACE PROCEDURE " + procName + "\n");
        proc.append("(\n");
        proc.append(inParams.toString() + "\n");
        proc.append(") IS\n");
        proc.append("BEGIN\n");
        proc.append("  BEGIN\n");
        proc.append("    OPEN " + nameConvent.getProcOutParamName("result") + " FOR\n");
        proc.append("      SELECT T.* FROM " + tableName.toUpperCase() + " T" + sqlWhere.toString() + ";\n");
        proc.append("  " + nameConvent.getProcOutParamName("message") + " := 'success';\n");
        proc.append("  EXCEPTION\n");
        proc.append("    WHEN OTHERS THEN\n");
        proc.append("      " + nameConvent.getProcOutParamName("message") + " := sqlerrm;\n");
        proc.append("  END;\n");
        proc.append("END " + procName + ";");
        return proc.toString();
    }

    @Override
    public String genSelProcedureWithPage(String tableName, List<String> selParams, List<String> selParamTypes, List<String> selParamDescs, List<Integer> selType, NameConventService nameConvent) {
        //查询条件
        StringBuilder sqlWhere = new StringBuilder();
        //存储过程入参
        StringJoiner inParams = new StringJoiner("\n");
        for (int i = 0, length = selParams.size(); i < length; i++) {
            if (i == 0) {
                sqlWhere.append(" \nWHERE");
            } else {
                sqlWhere.append(" \nAND");
            }
            inParams.add(nameConvent.genSelProcInParam(selParams.get(i), selParamTypes.get(i), selParamDescs.get(i), selType.get(i)));
            sqlWhere.append(nameConvent.genSelProcSqlWhere(selParams.get(i), selParamTypes.get(i), selType.get(i)));
        }
        //添加出参字段, 出参类型和出参信息以及入参分页相关参数
        String page = nameConvent.getProcOutParamName("page");
        String limit = nameConvent.getProcOutParamName("limit");
        inParams.add(page + " IN VARCHAR2, --页数");
        inParams.add(limit + " IN VARCHAR2, --每页显示条数");
        inParams.add(nameConvent.getProcOutParamName("total") + " OUT VARCHAR2, --返回总条数");
        inParams.add(nameConvent.getProcOutParamName("result") + " OUT SYS_REFCURSOR --成功返回结果集");
        //存储过程名称
        String procName = nameConvent.getProcName(tableName, ProcTypes.SELECT);
        //存储过程
        StringBuilder proc = new StringBuilder(128);
        proc.append("CREATE OR REPLACE PROCEDURE " + procName + "\n");
        proc.append("(\n");
        proc.append(inParams.toString() + "\n");
        proc.append(") IS\n");
        proc.append("BEGIN\n");
        proc.append("  IF " + page + " IS NOT NULL AND " + limit + " IS NOT NULL AND " + limit + " > 0 THEN\n");
        proc.append("    OPEN " + nameConvent.getProcOutParamName("result") + " FOR\n");
        proc.append("      SELECT *\n");
        proc.append("         FROM (SELECT FULLTABLE.*, ROWNUM RN\n");
        proc.append("           FROM (SELECT * FROM " + tableName + " T\n");
        proc.append("                   " + sqlWhere + ") FULLTABLE\n");
        proc.append("                 WHERE ROWNUM <= " + page + " * " + limit + ")");
        proc.append("         WHERE RN >= (" + page + " - 1) * " + limit + ";");
        proc.append("  ELSE");
        proc.append("    OPEN " + nameConvent.getProcOutParamName("result") + " FOR\n");
        proc.append("      SELECT * FROM " + tableName + " T\n");
        proc.append("        " + sqlWhere + ";\n");
        proc.append("  END IF;\n");
        proc.append("  SELECT COUNT(1)\n");
        proc.append("  INTO " + nameConvent.getProcOutParamName("total") + "\n");
        proc.append("  FROM " + tableName + " T\n");
        proc.append("  " + sqlWhere + ";\n");
        proc.append("END " + procName + ";");
        return proc.toString();
    }

    @Override
    public String genInsProcedure(String tableName, List<String> insParams, List<String> insParamTypes, List<String> insParamDescs, NameConventService nameConvent) {
        //存储过程入参
        StringJoiner inParams = new StringJoiner("\n");
        //新增语句字段
        StringJoiner insField = new StringJoiner(",\n");
        //新增语句传参参数
        StringJoiner sqlParams = new StringJoiner(",\n");
        for (int i = 0, length = insParams.size(); i < length; i++) {
            insField.add(insParams.get(i));
            sqlParams.add(nameConvent.genProcInParamName(insParams.get(i), insParamTypes.get(i)));
            inParams.add(nameConvent.genSelProcInParam(insParams.get(i), insParamTypes.get(i), insParamDescs.get(i), 0));
        }
        //添加出参字段, 出参类型和出参信息
        String message = nameConvent.getProcOutParamName("message");
        inParams.add(message + " OUT VARCHAR2 --成功执行信息为：‘success’，失败执行信息为错误信息");
        //存储过程
        String proc = "CREATE OR REPLACE PROCEDURE {procName}\n(\n" +
                "{inParams}\n" +
                ") IS\n" +
                "BEGIN\n" +
                "    BEGIN\n" +
                "\n" +
                "        INSERT INTO {tableName}\n" +
                "        ({insField})\n" +
                "        VALUES ({sqlParams}\n" +
                "        );\n" +
                "\n" +
                "        {message} := 'success';\n" +
                "\n" +
                "    EXCEPTION\n" +
                "        WHEN OTHERS THEN\n" +
                "            {message} := SQLERRM;\n" +
                "\n" +
                "    END;\n" +
                "END {procName};\n";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("procName", nameConvent.getProcName(tableName, ProcTypes.INSERT));
        paramMap.put("inParams", inParams.toString());
        paramMap.put("tableName", tableName);
        paramMap.put("insField", insField.toString());
        paramMap.put("sqlParams", sqlParams.toString());
        paramMap.put("message", message);
        return StrUtil.format(proc, paramMap);
    }

    @Override
    public String genUpdProcedure(String tableName, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, List<String> updParams, List<String> updParamTypes, List<String> updParamDescs, NameConventService nameConvent) {
        //修改语句查询条件
        StringBuilder primarySqlWhere = new StringBuilder();
        for (int i = 0, length = primarys.size(); i < length; i++) {
            if (i == 0) {
                primarySqlWhere.append(" \nWHERE");
            } else {
                primarySqlWhere.append(" \nAND");
            }
            primarySqlWhere.append(nameConvent.genSelProcSqlWhere(primarys.get(i), primaryTypes.get(i), 0));
        }
        //存储过程入参
        StringJoiner inParams = new StringJoiner("\n");
        //修改语句传参参数
        StringJoiner updSqlParams = new StringJoiner(",\n");
        for (int i = 0, length = primarys.size(); i < length; i++) {
            inParams.add(nameConvent.genSelProcInParam(primarys.get(i), primaryTypes.get(i), primaryDesc.get(i), 0));
        }
        for (int i = 0, length = updParams.size(); i < length; i++) {
            updSqlParams.add(updParams.get(i) + " = " + nameConvent.genProcInParamName(updParams.get(i), updParamTypes.get(i)));
            inParams.add(nameConvent.genSelProcInParam(updParams.get(i), updParamTypes.get(i), updParamDescs.get(i), 0));
        }
        //添加出参字段, 出参类型和出参信息
        String message = nameConvent.getProcOutParamName("message");
        inParams.add(message + " OUT VARCHAR2 --成功执行信息为：‘success’，失败执行信息为错误信息");
        //存储过程
        String proc = "CREATE OR REPLACE PROCEDURE {procName}\n" +
                "(\n" +
                "{inParams}\n" +
                ") IS\n" +
                "BEGIN\n" +
                "  BEGIN\n" +
                "    UPDATE {tableName} T \n" +
                "    SET\n" +
                "    {updSqlParams} {primarySqlWhere};\n" +
                "  \n" +
                "      {message} :='success';\n" +
                "  EXCEPTION \n" +
                "    WHEN OTHERS THEN\n" +
                "      {message} := SQLERRM;\n" +
                "     \n" +
                "    END;\n" +
                "  \n" +
                "END {procName};\n";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("procName", nameConvent.getProcName(tableName, ProcTypes.UPDATE));
        paramMap.put("inParams", inParams.toString());
        paramMap.put("tableName", tableName);
        paramMap.put("primarySqlWhere", primarySqlWhere.toString());
        paramMap.put("updSqlParams", updSqlParams.toString());
        paramMap.put("message", message);
        return StrUtil.format(proc, paramMap);
    }

    @Override
    public String genSaveProcedure(String tableName, List<String> insParams, List<String> insParamTypes, List<String> insParamDescs, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, List<String> updParams, List<String> updParamTypes, List<String> updParamDescs, NameConventService nameConvent) {
        //修改语句查询条件
        StringBuilder primarySqlWhere = new StringBuilder();
        for (int i = 0, length = primarys.size(); i < length; i++) {
            if (i == 0) {
                primarySqlWhere.append(" \nWHERE");
            } else {
                primarySqlWhere.append(" \nAND");
            }
            primarySqlWhere.append(nameConvent.genSelProcSqlWhere(primarys.get(i), primaryTypes.get(i), 0));
        }
        //存储过程入参 以修改传参为主
        StringJoiner inParams = new StringJoiner("\n");
        //修改语句传参参数
        StringJoiner updSqlParams = new StringJoiner(",\n");
        for (int i = 0, length = primarys.size(); i < length; i++) {
            inParams.add(nameConvent.genSelProcInParam(primarys.get(i), primaryTypes.get(i), primaryDesc.get(i), 0));
        }
        for (int i = 0, length = updParams.size(); i < length; i++) {
            updSqlParams.add(updParams.get(i) + " = " + nameConvent.genProcInParamName(updParams.get(i), updParamTypes.get(i)));
            inParams.add(nameConvent.genSelProcInParam(updParams.get(i), updParamTypes.get(i), updParamDescs.get(i), 0));
        }
        //新增语句字段
        StringJoiner insField = new StringJoiner(",\n");
        //新增语句传参参数
        StringJoiner insSqlParams = new StringJoiner(",\n");
        for (int i = 0, length = insParams.size(); i < length; i++) {
            insField.add(insParams.get(i));
            insSqlParams.add(nameConvent.genProcInParamName(insParams.get(i), insParamTypes.get(i)));
        }
        //添加出参字段, 出参类型和出参信息
        String message = nameConvent.getProcOutParamName("message");
        inParams.add(message + " OUT VARCHAR2 --成功执行信息为：‘success’，失败执行信息为错误信息");
        String proc = "CREATE OR REPLACE PROCEDURE {procName}\n" +
                "(\n" +
                "{inParams}\n" +
                ") IS\n" +
                "  V_I_NUMBER INT;\n" +
                "\n" +
                "BEGIN\n" +
                "  BEGIN\n" +
                "  \n" +
                "    SELECT COUNT(1)\n" +
                "    INTO   V_I_NUMBER\n" +
                "    FROM   {tableName} T {primarySqlWhere};\n" +
                "    IF (V_I_NUMBER = 0)\n" +
                "    THEN\n" +
                "    \n" +
                "      INSERT INTO {tableName}\n" +
                "        ({insField})\n" +
                "      VALUES\n" +
                "        ({insSqlParams});\n" +
                "    ELSE\n" +
                "      UPDATE {tableName} T\n" +
                "      SET\n{updSqlParams} {primarySqlWhere};\n" +
                "    \n" +
                "    END IF;\n" +
                "    {message} := 'success';\n" +
                "  \n" +
                "  EXCEPTION\n" +
                "    WHEN OTHERS THEN\n" +
                "      {message} := SQLERRM;\n" +
                "  END;\n" +
                "\n" +
                "END {procName};";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("procName", nameConvent.getProcName(tableName, ProcTypes.SAVE));
        paramMap.put("inParams", inParams.toString());
        paramMap.put("tableName", tableName);
        paramMap.put("primarySqlWhere", primarySqlWhere.toString());
        paramMap.put("insField", insField.toString());
        paramMap.put("insSqlParams", insSqlParams.toString());
        paramMap.put("updSqlParams", updSqlParams.toString());
        paramMap.put("message", nameConvent.getProcOutParamName("message"));
        return StrUtil.format(proc, paramMap);
    }

    @Override
    public String genDelProcedure(String tableName, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, NameConventService nameConvent) {
        //查询条件
        StringBuilder sqlWhere = new StringBuilder();
        //存储过程入参
        StringJoiner inParams = new StringJoiner("\n");
        for (int i = 0, length = primarys.size(); i < length; i++) {
            if (i == 0) {
                sqlWhere.append(" \nWHERE");
            } else {
                sqlWhere.append(" \nAND");
            }
            inParams.add(nameConvent.genSelProcInParam(primarys.get(i), primaryTypes.get(i), primaryDesc.get(i), 0));
            sqlWhere.append(nameConvent.genSelProcSqlWhere(primarys.get(i), primaryTypes.get(i), 0));
        }
        //添加出参字段, 出参类型和出参信息
        inParams.add(nameConvent.getProcOutParamName("message") + " OUT VARCHAR2 --成功执行信息为：‘success’，失败执行信息为错误信息");
        String proc = "CREATE OR REPLACE PROCEDURE {procName}\n(\n{inParams}\n) IS\n" +
                "BEGIN\n" +
                "  DELETE FROM {tableName} T\n" +
                "  {sqlWhere};\n" +
                "  {message} := 'success';\n" +
                "EXCEPTION\n" +
                "  WHEN OTHERS THEN\n" +
                "    {message} := sqlerrm;\n" +
                "END {procName};";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("procName", nameConvent.getProcName(tableName, ProcTypes.DELETE));
        paramMap.put("inParams", inParams.toString());
        paramMap.put("tableName", tableName);
        paramMap.put("sqlWhere", sqlWhere.toString());
        paramMap.put("message", nameConvent.getProcOutParamName("message"));
        return StrUtil.format(proc, paramMap);
    }

    @Override
    public Map<String, Object> genProceduresByTable(String tableName, List<String> params, List<String> paramTypes, List<String> paramDescs, List<Integer> priParamIndex, List<Integer> selParamsIndex, List<Integer> selType, List<Integer> insParamIndex, List<Integer> updParamIndex, NameConventService nameConvent) {
        //一个集合包含所有字段，以及其它相关的存储集合的索引（0开始）
        //通过遍历所有字段集合来为相关集合赋值
        List<String> primarys = new ArrayList<>();
        List<String> primaryTypes = new ArrayList<>();
        List<String> primaryDesc = new ArrayList<>();
        List<String> selParams = new ArrayList<>();
        List<String> selParamTypes = new ArrayList<>();
        List<String> selParamDescs = new ArrayList<>();
        List<String> insParams = new ArrayList<>();
        List<String> insParamTypes = new ArrayList<>();
        List<String> insParamDescs = new ArrayList<>();
        List<String> updParams = new ArrayList<>();
        List<String> updParamTypes = new ArrayList<>();
        List<String> updParamDescs = new ArrayList<>();

        for (int i = 0, length = params.size(); i < length; i++) {
            if (i < priParamIndex.size()) {
                primarys.add(params.get(priParamIndex.get(i)));
                primaryTypes.add(paramTypes.get(priParamIndex.get(i)));
                primaryDesc.add(paramDescs.get(priParamIndex.get(i)));
            }
            if (i < selParamsIndex.size()) {
                selParams.add(params.get(selParamsIndex.get(i)));
                selParamTypes.add(paramTypes.get(selParamsIndex.get(i)));
                selParamDescs.add(paramDescs.get(selParamsIndex.get(i)));
            }
            if (i < insParamIndex.size()) {
                insParams.add(params.get(insParamIndex.get(i)));
                insParamTypes.add(paramTypes.get(insParamIndex.get(i)));
                insParamDescs.add(paramDescs.get(insParamIndex.get(i)));
            }
            if (i < updParamIndex.size()) {
                updParams.add(params.get(updParamIndex.get(i)));
                updParamTypes.add(paramTypes.get(updParamIndex.get(i)));
                updParamDescs.add(paramDescs.get(updParamIndex.get(i)));
            }
            if (i >= priParamIndex.size() && i >= selParamsIndex.size() && i >= insParamIndex.size() && i >= updParamIndex.size()) {
                break;
            }
        }

        Map<String, Object> result = new HashMap<>(16);
        List<String> list = new ArrayList<>(Arrays.asList("get", "select", "selectWithPage", "insert", "update", "save", "delete"));
        result.put("list", list);
        result.put("get", genGetProcedure(tableName, primarys, primaryTypes, primaryDesc, nameConvent));
        result.put("select", genSelProcedure(tableName, selParams, selParamTypes, selParamDescs, selType, nameConvent));
        result.put("selectWithPage", genSelProcedureWithPage(tableName, selParams, selParamTypes, selParamDescs, selType, nameConvent));
        result.put("insert", genInsProcedure(tableName, insParams, insParamTypes, insParamDescs, nameConvent));
        result.put("update", genUpdProcedure(tableName, primarys, primaryTypes, primaryDesc, updParams, updParamTypes, updParamDescs, nameConvent));
        result.put("save", genSaveProcedure(tableName, insParams, insParamTypes, insParamDescs, primarys, primaryTypes, primaryDesc, updParams, updParamTypes, updParamDescs, nameConvent));
        result.put("delete", genDelProcedure(tableName, primarys, primaryTypes, primaryDesc, nameConvent));
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DataBaseTableToProcFactory.register(DataBaseType.ORACLE, this);
    }
}
