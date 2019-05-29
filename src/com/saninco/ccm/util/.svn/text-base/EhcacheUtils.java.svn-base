package com.saninco.ccm.util;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

public abstract class EhcacheUtils {
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(EhcacheUtils.class);

	/**
	 * 缓存配置文件
	 */
	private static String CACHE_CONFIG_FILE = "classpath:ehcache.xml";

	/**
	 * Ehanche的缓存管理
	 */
	private static CacheManager cacheManager = null;

	/**
	 * 设置缓存配置文件，最开始设置才有效果，一旦缓存加载则不能改变
	 * 
	 * @param cacheConfigFile
	 */
	public static void setCacheConfigFile(String cacheConfigFile) {
		CACHE_CONFIG_FILE = cacheConfigFile;
	}

	/**
	 * 按缺省配置创建缓存
	 * 
	 * @param cacheName
	 */
	public static void createCache(String cacheName) {
		getCacheManager().addCache(cacheName);
	}

	/**
	 * 添加缓存
	 * 
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public static void put(String cacheName, String key, Object value) {
		Ehcache cache = getCacheManager().getEhcache(cacheName);
		cache.put(new Element(key, value));
	}

	/**
	 * 根据缓存名与key获取值
	 * 
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static Object get(String cacheName, String key) {
		Ehcache cache = getCacheManager().getEhcache(cacheName);
		Element e = cache.get(key);
		return e == null ? null : e.getObjectValue();
	}

	/**
	 * 获取缓存名
	 * 
	 * @return
	 */
	public static String[] getCacheNames() {
		return getCacheManager().getCacheNames();
	}

	/**
	 * 获取缓存的Keys
	 * 
	 * @param cacheName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getKeys(String cacheName) {
		Ehcache cache = getCacheManager().getEhcache(cacheName);
		return (List<String>) cache.getKeys();
	}

	/**
	 * 清除所有
	 */
	public static void clearAll() {
		getCacheManager().clearAll();
	}

	/**
	 * 清空指定缓存
	 * 
	 * @param cacheName
	 */
	public static void clear(String cacheName) {
		getCacheManager().getCache(cacheName).removeAll();
	}

	/**
	 * 删除指定对象
	 * 
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static boolean remove(String cacheName, String key) {
		return getCacheManager().getCache(cacheName).remove(key);
	}

	/**
	 * 获取缓存大小
	 * 
	 * @param cacheName
	 * @return
	 */
	public static int getSize(String cacheName) {
		return getCacheManager().getCache(cacheName).getSize();
	}

	/**
	 * 获取CacheManager
	 * 
	 * @return
	 */
	private synchronized static CacheManager getCacheManager() {
		if (cacheManager != null) {
			return cacheManager;
		}

		try {
			URL url = ResourceUtils.getURL(CACHE_CONFIG_FILE);
			cacheManager = CacheManager.create(url);
		} catch (RuntimeException e) {
			LOGGER.error("init flat cache failed", e);
			throw e;
		} catch (FileNotFoundException fe) {
			LOGGER.error("init flat cache failed", fe);
			throw new RuntimeException(fe);
		}

		return cacheManager;
	}
}
