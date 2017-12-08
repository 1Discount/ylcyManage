package com.Manage.common.util;

import java.util.Random;

public class randomUtil {
	/**
	 * @deprecated 获取指定数字之间的随机数
	 * @param max
	 * @param min
	 * @return
	 */
	public static int getrandom(int max,int min){
	        Random random = new Random();
	        return random.nextInt(max)%(max-min+1) + min;
	}
}
