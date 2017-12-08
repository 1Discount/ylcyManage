package com.Manage.test.mytest;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.Manage.common.constants.Constants;
import com.Manage.common.util.DES;



public class test20161031{
	public static void main(String[] args) throws Exception {
	    //加密
	    System.out.println(DES.toHexString(DES.encrypt("yanhai@123456", Constants.DES_KEY)));
	    //解密
	    DES.decrypt("4e6dbdef3ea91bf4c33c33f32c3d94ac88f035df8188b22b", Constants.DES_KEY);
	    //System.out.println(new DeviceLogsSer().getDeviceInCountByDay2("20170105"));
	    //System.out.println(DateUtil.dateComparison(DateUtil.parseToDate("2016-12-28"),DateUtil.parseToDate("2016-12-27")));
	    
	  /*  System.out.println(new SIMInfoSer().selectAllFlow("20160617"));*/
	   /* System.out.println(System.currentTimeMillis());
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	    System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
*/	}
}
