/*
 * 文件名     ：ApplicationContext.java
 * 创建时间：Feb 25, 2014 4:34:09 PM
 * 创建人     : FuLei
 */
package com.Manage.entity.common;
import java.util.Locale;

import org.springframework.context.ApplicationContext;

/**
 * Spring容器对象。
 * 
 * @author wangbo
 * 
 */
public final class ApplicationContext2
{
	/**
	 * Spring应用上下文实例。
	 */
	private static ApplicationContext instance;
	
	//项目根目录
	private static String rootpath;
	/**
	 * @return the instance
	 */
	public static ApplicationContext getInstance()
	{
		return instance;
	}

	/**
	 * @param instance the instance to set
	 */
	public static void setInstance(ApplicationContext instance)
	{
		ApplicationContext2.instance = instance;
	}
	
	public static void setRootpath(String path){
		ApplicationContext2.rootpath=path;
	}
	
	public static String getRootpath(){
		return rootpath;
	}

	/**
	 * 从Spring容器中获取Bean实例。
	 * 
	 * @param clazz Bean的标识。
	 * @return 实例Bean。
	 */
	public static <T> T getBean(Class<T> clazz)
	{
		return instance.getBean(clazz);
	}

	/**
	 * 从Spring容器中获取Bean实例。
	 * 
	 * @param beanID Bean的标识。
	 * @return 实例Bean。
	 */
	public static Object getBean(String beanID)
	{
		return instance.getBean(beanID);
	}

	/**
	 * 从Spring容器中获取指定key的信息。
	 * 
	 * @param key 属性Key
	 * @param args 信息中待填充的参数列表
	 * @param locale 本地语言
	 * @return
	 */
	public static String getMessage(String key, Object[] args, Locale locale)
	{
		return instance.getMessage(key, args, locale);
	}
}
