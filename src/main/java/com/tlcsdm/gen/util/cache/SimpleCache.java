package com.tlcsdm.gen.util.cache;

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

	public static final String DATABASETYPE = "dataBaseType";

	public static final String GENPROCEDUREMODELTYPE = "genProcedureModelType";

	public static final String NAMECONVENTTYPE = "nameConventType";

	public static final String ENGINEFILETYPE = "engineFileType";

	public static final String GENCODEMODELTYPE = "genCodeModelType";

	public static final String JAVACLASS = "javaClass";

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
