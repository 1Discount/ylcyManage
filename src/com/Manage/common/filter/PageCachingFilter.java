package com.Manage.common.filter;


import net.sf.ehcache.CacheManager;
import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;

import com.Manage.common.cache.CacheUtils;

/**
 * 页面高速缓存过滤器
 */
public class PageCachingFilter extends SimplePageCachingFilter {
	@Override
	protected CacheManager getCacheManager() {
		return CacheUtils.getCacheManager();
	}
}

