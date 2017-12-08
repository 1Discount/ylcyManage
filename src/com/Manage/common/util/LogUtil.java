package com.Manage.common.util;

import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class LogUtil {
	private static Logger instance;
	
	static {
		try {
			Properties prop = new Properties();
			prop.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("log4j.properties"));
			PropertyConfigurator.configure(prop);
			String loghome = prop.getProperty("loghome");
			if (StringUtils.isNotEmpty(loghome))
				System.setProperty("loghome", StringUtils.trim(loghome));
		} catch (Exception e) {
			e.printStackTrace();
		}
		instance = null;
	}

	public static Logger getDefaultInstance() {
		if (instance == null) {
			synchronized (LogUtil.class) {
				if (instance == null) {
					instance = Logger.getLogger("LOGGER");
				}
			}
		}
		return instance;
	}
	public static Logger getInstance(Class clazz) {
		if (clazz == null)
			return null;
		return Logger.getLogger(clazz);
	}

	public static Logger getInstance(String className) {
		if (className == null)
			return null;
		return Logger.getLogger(className);
	}
}