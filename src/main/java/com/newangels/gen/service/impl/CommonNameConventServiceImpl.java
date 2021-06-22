package com.newangels.gen.service.impl;

import com.newangels.gen.enums.NameConventType;
import com.newangels.gen.factory.NameConventFactory;
import com.newangels.gen.service.NameConventService;
import org.springframework.stereotype.Service;

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

    Map<String, String> map = new ConcurrentHashMap<>(32);

    @Override
    public String getName(String procedureName) {
        String name = procedureName.substring(procedureName.lastIndexOf("_") + 1).toLowerCase();
        return map.getOrDefault(name, name);
    }

    @Override
    public String getMappingType(String procedureName) {
        if ("select".equals(getName(procedureName)) || "load".equals(getName(procedureName))) {
            return "GetMapping";
        }
        return "PostMapping";
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
        NameConventFactory.register(NameConventType.COMMON, this);
    }
}
