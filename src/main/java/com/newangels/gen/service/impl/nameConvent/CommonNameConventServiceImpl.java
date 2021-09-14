package com.newangels.gen.service.impl.nameConvent;

import com.newangels.gen.enums.NameConventType;
import com.newangels.gen.factory.NameConventFactory;
import com.newangels.gen.service.NameConventService;
import com.newangels.gen.util.ProcTypes;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 常用命名规范
 *
 * @author: TangLiang
 * @date: 2021/6/21 10:35
 * @since: 1.0
 */
@Service
public class CommonNameConventServiceImpl implements NameConventService {

    private Map<String, String> map = new ConcurrentHashMap<>(32);
    //排序规则
    private Map<String, Integer> sortMap = new ConcurrentHashMap<>(16);
    //出参名称映射
    private Map<String, String> outParamNameMap = new ConcurrentHashMap<>(8);
    //入参名称映射
    private Map<String, String> inParamNameMap = new ConcurrentHashMap<>(8);
    //参数类型映射
    private Map<Integer, String> procTypeMap = new ConcurrentHashMap<>(8);

    @Override
    public String getName(String procedureName) {
        String name = procedureName.substring(procedureName.lastIndexOf("_") + 1).toLowerCase();
        return map.getOrDefault(name, name);
    }

    @Override
    public String getResultName(String name) {
        name = name.toUpperCase();
        if (name.contains("V_INFO") || name.contains("MESSAGE") || name.contains("RET")) {
            return "message";
        }
        if (name.contains("V_C_CURSOR") || name.contains("V_CURSOR") || name.contains("RESULT")) {
            return "result";
        }
        if (name.contains("NUM") || name.contains("TOTAL")) {
            return "total";
        }
        return name;
    }

    @Override
    public List<String> getMethodNames(String moduleName, List<String> procedureNameList) {
        Map<String, AtomicInteger> map = new HashMap<>();
        List<String> methodNames = new ArrayList<>(procedureNameList.size());
        for (String procedureName : procedureNameList) {
            //方法名称前缀
            String preName = getName(procedureName);
            String methodName;
            if (map.get(preName) == null) {
                map.put(preName, new AtomicInteger(0));
                methodName = preName + moduleName;
            } else {
                methodName = preName + moduleName + map.get(preName).incrementAndGet();
            }
            methodNames.add(methodName);
        }
        return methodNames;
    }

    @Override
    public void sortMethod(List<String> procedureNameList) {
        // 方法顺序 load select insert update save delete 其它
        procedureNameList.sort((procedureName1, procedureName2) -> {
            String preName1 = getName(procedureName1);
            String preName2 = getName(procedureName2);
            return sortMap.getOrDefault(preName1, 999) - sortMap.getOrDefault(preName2, 999);
        });
    }

    @Override
    public String getProcName(String tableName, int procType) {
        return "PRO_" + tableName.toUpperCase() + procTypeMap.getOrDefault(procType, "_OTHER");
    }

    @Override
    public String getProcOutParamName(String name) {
        return outParamNameMap.getOrDefault(name, name);
    }

    @Override
    public String genProcInParamName(String paramName, String paramType) {
        return inParamNameMap.getOrDefault(paramType, "V_") + paramName;
    }

    @Override
    public String genSelProcInParam(String param, String paramType, String paramDesc, Integer selType) {
        String inParamName = genProcInParamName(param, paramType);
        //为区间查询
        if (selType == 2) {
            return "START_" + inParamName + " IN " + paramType + ", --开始" + paramDesc + "\n" + "END_" + inParamName + " IN " + paramType + ", --结束" + paramDesc;
        } else {
            return inParamName + " IN " + paramType + ", --" + paramDesc;
        }
    }

    @Override
    public String genSelProcSqlWhere(String param, String paramType, Integer selType) {
        String inParamName = genProcInParamName(param, paramType);
        String sqlWhere;
        switch (selType) {
            case 2://区间
                sqlWhere = " " + param + " >= START_" + inParamName + "\n AND " + param + " <= END_" + inParamName;
                break;
            case 1://模糊
                sqlWhere = " " + param + " like '%' || " + inParamName + " || '%'";
                break;
            case 0://精确
            default:
                sqlWhere = " " + param + " = " + inParamName;
                break;
        }
        return sqlWhere;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        map.put("get", "load");
        map.put("load", "load");

        map.put("list", "select");
        map.put("sel", "select");
        map.put("select", "select");
        map.put("tree", "select");
        map.put("all", "select");

        map.put("num", "count");

        map.put("upd", "update");
        map.put("set", "update");
        map.put("update", "update");
        map.put("upa", "update");

        map.put("save", "save");

        map.put("add", "insert");
        map.put("ins", "insert");
        map.put("insert", "insert");

        map.put("del", "delete");
        map.put("delete", "delete");

        sortMap.put("load", 1);
        sortMap.put("select", 2);
        sortMap.put("count", 3);
        sortMap.put("insert", 4);
        sortMap.put("update", 5);
        sortMap.put("save", 6);
        sortMap.put("delete", 7);

        outParamNameMap.put("result", "V_C_CURSOR");
        outParamNameMap.put("message", "V_V_INFO");
        outParamNameMap.put("total", "V_V_SNUM");
        outParamNameMap.put("page", "V_PAGE");
        outParamNameMap.put("limit", "V_PAGESIZE");

        procTypeMap.put(ProcTypes.GET, "_GET");
        procTypeMap.put(ProcTypes.SELECT, "_SEL");
        procTypeMap.put(ProcTypes.INSERT, "_INS");
        procTypeMap.put(ProcTypes.UPDATE, "_UPD");
        procTypeMap.put(ProcTypes.DELETE, "_DEL");
        procTypeMap.put(ProcTypes.SAVE, "_SAVE");

        inParamNameMap.put("VARCHAR2", "V_");
        inParamNameMap.put("BLOB", "B_");
        inParamNameMap.put("NUMBER", "I_");
        inParamNameMap.put("DATE", "V_");
        NameConventFactory.register(NameConventType.COMMON, this);
    }
}
