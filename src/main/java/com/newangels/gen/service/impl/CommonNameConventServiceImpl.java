package com.newangels.gen.service.impl;

import com.newangels.gen.enums.NameConventType;
import com.newangels.gen.factory.NameConventFactory;
import com.newangels.gen.service.NameConventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    @Override
    public String getName(String procedureName) {
        String name = procedureName.substring(procedureName.lastIndexOf("_") + 1).toLowerCase();
        return map.getOrDefault(name, name);
    }

    @Override
    public String getResultName(String name) {
        name = name.toUpperCase();
        if (name.contains("V_INFO") || name.contains("MESSAGE")) {
            return "message";
        }
        if (name.contains("V_C_CURSOR") || name.contains("RET") || name.contains("V_CURSOR") || name.contains("RESULT")) {
            return "result";
        }
        if (name.contains("NUM") || name.contains("TOTAL")) {
            return "total";
        }
        return name;
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
        NameConventFactory.register(NameConventType.COMMON, this);
    }
}
