package com.tlcsdm.gen.controller;

import com.tlcsdm.common.annotation.Log;
import com.tlcsdm.gen.base.BaseUtils;
import com.tlcsdm.gen.factory.DataSourceUtilFactory;
import com.tlcsdm.gen.util.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import static com.tlcsdm.gen.base.CacheManage.CACHE_MAP;

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
	 * 缓存管理页
	 */
	@GetMapping("/manageCache")
	public ModelAndView manageCache() {
		return new ModelAndView("pages/cache/manageCache");
	}

	/**
	 * 查询项目缓存
	 */
	@GetMapping("selectCaches")
	@Log(title = "缓存管理", operateType = "查询项目缓存")
	public Map<String, Object> selectCaches() {
		List<Map<String, Object>> list = new ArrayList<>();
		for (Cache cache : CACHE_MAP.values()) {
			Map<String, Object> map = cache.getMap();
			Map<String, Object> result = new HashMap<>(4);
			for (Map.Entry entry : map.entrySet()) {
				String mapKey = (String) entry.getKey();
				Object mapValue = entry.getValue();
				result.put("key", mapKey);
				result.put("value", mapValue);
				list.add(result);
			}
		}
		return BaseUtils.success(list);
	}

	/**
	 * 清除缓存 为空清除所有缓存，否则清除指定缓存
	 */
	@DeleteMapping(value = { "clearCaches/{name}", "clearCaches/" })
	@Log(title = "缓存管理", operateType = "清除缓存")
	public Map<String, Object> clearCaches(@PathVariable(required = false) String name) {
		if (StringUtils.isEmpty(name)) {
			CACHE_MAP.forEach((s, cache) -> cache.clear());
		}
		else {
			Optional.ofNullable(CACHE_MAP.get(name)).ifPresent(Cache::clear);
		}

		return BaseUtils.success();
	}

	/**
	 * 获取CacheManage类下的缓存信息
	 */
	@GetMapping(value = { "caches/{name}", "caches/", "caches" })
	@Log(title = "缓存管理", operateType = "获取缓存信息")
	public Map<String, Object> getCaches(@PathVariable(required = false) String name) {
		List<Object> list = new ArrayList<>();

		if (StringUtils.isEmpty(name)) {
			for (Cache cache : CACHE_MAP.values()) {
				list.addAll(cache.keys());
			}
		}
		else {
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
	@GetMapping(value = { "dataSourceCaches/", "dataSourceCaches" })
	@Log(title = "缓存管理", operateType = "获取数据库连接池缓存信息")
	public Map<String, Object> getDataSourceUtilCaches() {
		return BaseUtils.success(DataSourceUtilFactory.getDataSourceInfoList());
	}

}
