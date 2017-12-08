/*
 * 文件名     ：MyBatisLogListener.java
 * 创建时间：Jan 15, 2014 11:09:29 AM
 * 创建人     : FuLei
 */
package com.Manage.common.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;

import com.Manage.common.cache.CacheUtils;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.common.ApplicationContext2;




/**
 * 获取Spring容器监听器，在web.xml中配置。
 * 
 * @author wangbo
 * 
 */
public class ApplicationContextListener implements ServletContextListener
{
	private Logger logger = LogUtil.getInstance(ApplicationContextListener.class);

	public void contextDestroyed(ServletContextEvent arg0)
	{
		// do nothing
		//CacheUtils.cacheManager.shutdown();
	}

	/**
	 * 初始化Spring上下文。
	 */
	public void contextInitialized(ServletContextEvent arg0)
	{
		logger.info("Begin to init ApplicationContext...");

		System.out.println("=====================init");
		// 如果上下文容器为null，则重新获取
		if (null == ApplicationContext2.getInstance())
		{
			// 同步锁
			// synchronized (applicationContext)
			// {
			// applicationContext =
			// ContextLoader.getCurrentWebApplicationContext();
			// }
			// 如果获取不到，打印错误日志并返回null
			if (null != ContextLoader.getCurrentWebApplicationContext())
			{
				ApplicationContext2.setInstance(ContextLoader.getCurrentWebApplicationContext());
			}
			else
			{
				
				logger.error("spring容器加載失敗");
			}
		}
		ServletContext servletContext = arg0.getServletContext();
		String rpString=servletContext.getRealPath("/");
		//CacheUtils.put("Rootpath",rpString);
		ApplicationContext2.setRootpath(rpString);
		System.out.println("-----------项目根路径---------"+ApplicationContext2.getRootpath()+"-----------------------");
		logger.info("End to init ApplicationContext.");
	}
}
