package com.newangels.gen.controller;

import com.newangels.gen.annotation.Log;
import com.newangels.gen.base.BaseUtils;
import com.newangels.gen.factory.DataSourceUtilFactory;
import com.newangels.gen.util.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.newangels.gen.base.CacheManage.CACHE_MAP;

/**
 * 缓存管理
 *
 * @author: TangLiang
 * @date: 2021/6/30 9:34
 * @since: 1.0
 */
@RestController
@RequiredArgsConstructor
public class CacheManageController {

    /**
     * 清除缓存
     * 为空清除所有缓存，否则清除指定缓存
     */
    @DeleteMapping(value = {"clearCaches/{name}", "clearCaches/"})
    @Log
    public Map<String, Object> clearCaches(@PathVariable(required = false) String name) {
        if (StringUtils.isEmpty(name)) {
            CACHE_MAP.forEach((s, cache) -> cache.clear());
        } else {
            Optional.ofNullable(CACHE_MAP.get(name)).ifPresent(Cache::clear);
        }

        return BaseUtils.success();
    }

    /**
     * 获取CacheManage类下的缓存信息
     */
    @GetMapping(value = {"caches/{name}", "caches/", "caches"})
    @Log
    public Map<String, Object> getCaches(@PathVariable(required = false) String name) {
        List<Object> list = new ArrayList<>();

        if (StringUtils.isEmpty(name)) {
            for (Cache cache : CACHE_MAP.values()) {
                list.addAll(cache.keys());
            }
        } else {
            for (Cache cache : CACHE_MAP.values()) {
                Object o = cache.get(name);
                if (null != o) {
                    list.add(o);
                    break;
                }
            }
        }
        return BaseUtils.success(list);
    }

    /**
     * 获取数据库连接池DataSourceUtilFactory缓存信息
     */
    @GetMapping(value = {"dataSourceCaches/", "dataSourceCaches"})
    @Log
    public Map<String, Object> getDataSourceUtilCaches() {
        return BaseUtils.success(DataSourceUtilFactory.getDataSourceInfoList());
    }
}
