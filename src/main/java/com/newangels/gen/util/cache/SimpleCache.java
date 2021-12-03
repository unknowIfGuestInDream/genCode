package com.newangels.gen.util.cache;

import lombok.NonNull;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简易缓存实现
 *
 * @author: TangLiang
 * @date: 2021/9/11 19:46
 * @since: 1.0
 */
public class SimpleCache {

    public static String DATABASETYPE = "dataBaseType";
    public static String GENPROCEDUREMODELTYPE = "genProcedureModelType";
    public static String NAMECONVENTTYPE = "nameConventType";
    public static String ENGINEFILETYPE = "engineFileType";
    public static String GENCODEMODELTYPE = "genCodeModelType";
    public static String JAVACLASS = "javaClass";

    private static Map<String, List<Map<String, Object>>> map = new ConcurrentHashMap<>();

    public static List<Map<String, Object>> put(@NonNull String key, @NonNull List<Map<String, Object>> value) {
        return map.put(key, value);
    }

    public static List<Map<String, Object>> get(@NonNull String key) {
        return map.get(key);
    }

    public static List<Map<String, Object>> remove(@NonNull String key) {
        return map.remove(key);
    }

    public static void removeAll() {
        map.clear();
    }

}
