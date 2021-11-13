package com.newangels.gen.util.cache;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;

/**
 * 具有过期时间的缓存
 * 向缓存添加内容时，给每一个key设定过期时间，系统自动将超过过期时间的key清除。
 * <p>
 * 当向缓存中添加key-value对时，如果这个key在缓存中存在并且还没有过期，需要用这个key对应的新过期时间
 * 为了能够让DelayQueue将其已保存的key删除，需要重写实现Delayed接口添加到DelayQueue的DelayedItem的hashCode函数和equals函数
 * 当缓存关闭，监控程序也应关闭，因而监控线程应当用守护线程
 *
 * @author: TangLiang
 * @date: 2021/6/29 14:35
 * @since: 1.0
 */
public class Cache<K, V> {

    //存储缓存
    public ConcurrentHashMap<K, V> map = new ConcurrentHashMap<>();
    //延迟队列
    public DelayQueue<DelayedItem<K>> queue = new DelayQueue<>();
    //缓存30秒
    public static final long CACHE_HOLD_30SECOND = 30 * 1000 * 1000 * 1000L;
    //缓存30分钟
    public static final long CACHE_HOLD_30MINUTE = 30 * 60 * 1000 * 1000 * 1000L;
    //缓存1天
    public static final long CACHE_HOLD_1DAY = 24 * 60 * 60 * 1000 * 1000 * 1000L;
    //永久保存
    public static final long CACHE_HOLD_FOREVER = Long.MAX_VALUE;

    /**
     * 缓存值
     */
    public void put(K k, V v, long liveTime) {
        V v2 = map.put(k, v);
        DelayedItem<K> tmpItem = new DelayedItem<K>(k, liveTime);
        if (v2 != null) {
            queue.remove(tmpItem);
        }
        queue.put(tmpItem);
    }

    /**
     * 缓存值 默认缓存一天
     */
    public void put(K k, V v) {
        put(k, v, CACHE_HOLD_1DAY);
    }

    //清空缓存
    public void clear() {
        queue.clear();
        map.clear();
    }

    /**
     * 取出一个缓存对象
     *
     * @param cacheName 缓存名称
     */
    public V get(String cacheName) {
        return map.get(cacheName);
    }

    /**
     * 取出缓存对象全部key值
     */
    public Set<K> keys() {
        return map.keySet();
    }

    /**
     * 取出一个缓存对象
     */
    public Map<K, V> getMap() {
        return map;
    }

    public Cache() {
        Thread t = new Thread() {
            @Override
            public void run() {
                checkOverdueKey();
            }
        };
        t.setDaemon(true);
        t.start();
    }

    public void checkOverdueKey() {
        while (true) {
            DelayedItem<K> delayedItem = queue.poll();
            if (delayedItem != null) {
                map.remove(delayedItem.getT());
            }
            try {
                Thread.sleep(300);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
