package com.Manage.common.util;

import java.security.MessageDigest;

import javax.servlet.http.HttpServletRequest;


public class EncryptUtil {
	private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7',
		'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	/**
	 * md5加密
	 * @param password
	 * @return
	 */
	public static String md5(String password) {
		if (password == null || password.length() == 0) {
			return null;
		}

		try {
			byte[] temp = password.getBytes();
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(temp);

			byte[] md = digest.digest();
			int length = md.length;
			char buffer[] = new char[length * 2];
			int k = 0;
			for (int i = 0; i < length; i++) {
				byte byte0 = md[i];
				buffer[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buffer[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buffer);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 
	 * 功能说明：获取IP地址
	 * 参数及返回值:
	 * @param request
	 * @return
	 */
	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
