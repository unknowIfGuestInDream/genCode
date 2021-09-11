package com.newangels.gen.base;

import com.newangels.gen.util.cache.Cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存管理
 *
 * @author: TangLiang
 * @date: 2021/6/30 9:31
 * @since: 1.0
 */
public class CacheManage {

    //存储过程缓存
    public static Cache<String, List<Map<String, Object>>> PROCEDURES_CACHE = new Cache<>();
    //表缓存
    public static Cache<String, List<Map<String, Object>>> TABLES_CACHE = new Cache<>();
    //所有缓存存储
    public static Map<String, Cache> CACHE_MAP = new ConcurrentHashMap() {
        {
            put("procedures", PROCEDURES_CACHE);
            put("tables", TABLES_CACHE);
        }
    };
}
