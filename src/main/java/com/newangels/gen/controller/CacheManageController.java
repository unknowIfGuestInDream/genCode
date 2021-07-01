package com.newangels.gen.controller;

import com.newangels.gen.annotation.Log;
import com.newangels.gen.base.BaseUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
@RequestMapping("clearCaches")
public class CacheManageController {

    /**
     * 清除缓存
     * 为空清除所有缓存，否则清除指定缓存
     */
    @DeleteMapping(value = {"/{name}", "/"})
    @Log
    public Map<String, Object> clearCaches(@PathVariable(required = false) String name) {
        if (StringUtils.isEmpty(name)) {
            CACHE_MAP.forEach((s, cache) -> cache.clear());
        } else {
            if (CACHE_MAP.containsKey(name)) {
                CACHE_MAP.get(name).clear();
            }
        }

        return BaseUtils.success();
    }

    /**
     * 获取缓存信息
     * //TODO 待后续设计开发
     */
    @GetMapping("getCaches")
    @Log
    public Map<String, Object> getCaches(String name) {
        return BaseUtils.success();
    }
}
