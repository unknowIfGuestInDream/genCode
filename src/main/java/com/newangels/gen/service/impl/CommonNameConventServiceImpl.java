package com.newangels.gen.service.impl;

import com.newangels.gen.factory.NameConventFactory;
import com.newangels.gen.service.NameConventService;
import com.newangels.gen.util.NameConventType;
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
        if (name.indexOf("V_INFO") > 0 || name.indexOf("MESSAGE") > 0) {
            return "message";
        }
        if (name.indexOf("V_C_CURSOR") > 0 || name.indexOf("RET") > 0 || name.indexOf("V_CURSOR") > 0 || name.indexOf("RESULT") > 0) {
            return "result";
        }
        if (name.indexOf("NUM") > 0 || name.indexOf("TOTAL") > 0) {
            return "total";
        }
        return null;
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
