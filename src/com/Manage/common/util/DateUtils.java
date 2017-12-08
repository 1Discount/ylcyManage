/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.Manage.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.Manage.common.exception.BmException;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy-MM-dd HH:mm:ss.S",
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}

	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss.S",
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}

	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}

	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }

	/**
	 * 获取两个日期之间的天数
	 *
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}

	/**
	 * @param args
	 * @throws ParseException
	 */
	/*public static void main(String[] args) throws ParseException {
//		System.out.println(formatDate(parseDate("2010/3/6")));
//		System.out.println(getDate("yyyy年MM月dd日 E"));
//		long time = new Date().getTime()-parseDate("2012-11-19").getTime();
//		System.out.println(time/(24*60*60*1000));
	}*/

	/**
	 * 功能说明：获取当前时间的后n天时间，并返回字符串。
	 * @param date
	 * @param n 注意! 按下面的实现, n大于零时, 结果系未来n天, n小于零时, 才系过去n
	 * @param pattern
	 * @return
	 * @throws BmException
	 */
	public static String beforeNDateToString(Date date,int n,String pattern) {
		if ( date == null ) {
			date = new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, n); 
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(calendar.getTime());
	}

	/**
	 * 获取当时间的前n分钟的时间
	 * @author jiangxuecheng
	 * @date 2015-12-14
	 * @param datestring
	 * @param n
	 */
	public static String getcurDate(String datestring,int n){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = df.parse(datestring);
			date.setTime(date.getTime() - n * 60 * 1000);
			return df.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 功能说明：获取当前时间的后n天时间，并返回时间对象.
	 * @param date
	 * @param n 注意! 按下面的实现, n大于零时, 结果系未来n天, n小于零时, 才系过去n
	 * @return
	 * @throws BmException
	 */
	public static Date beforeNDate(Date date,int n) {
		if ( date == null ) {
			date = new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, n);
		//SimpleDateFormat format = new SimpleDateFormat(pattern);
		return calendar.getTime();
	}

	/**
	 * 功能说明：获取当前时间的后n小时时间，并返回字符串。
	 * @param date
	 * @param pattern
	 * @return
	 * @throws BmException
	 */
	public static String beforeNHourToString(Date date,int n,String pattern) {
		if ( date == null ) {
			date = new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, n);
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(calendar.getTime());
	}

	/**
	 * 功能说明：获取当前时间的后n小时时间，并返回时间对象.
	 * @param date
	 * @param n
	 * @return
	 * @throws BmException
	 */
	public static Date beforeNHour(Date date,int n) {
		if ( date == null ) {
			date = new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, n);
		//SimpleDateFormat format = new SimpleDateFormat(pattern);
		return calendar.getTime();
	}

	/**
	 * 功能说明：获取当前时间的后n分钟时间，并返回字符串。
	 * @param date
	 * @param pattern
	 * @return
	 * @throws BmException
	 */
	public static String beforeNMinuteToString(Date date,int n,String pattern) {
		if ( date == null ) {
			date = new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, n);
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(calendar.getTime());
	}

	/**
	 * 功能说明：获取当前时间的后n分钟时间，并返回时间对象.
	 * @param date
	 * @param n
	 * @return
	 * @throws BmException
	 */
	public static Date beforeNMinute(Date date,int n) {
		if ( date == null ) {
			date = new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, n);
		//SimpleDateFormat format = new SimpleDateFormat(pattern);
		return calendar.getTime();
	}

	/**
	 * 功能说明：获取当前时间的后n秒时间，并返回字符串。
	 * @param date
	 * @param pattern
	 * @return
	 * @throws BmException
	 */
	public static String beforeNSecondToString(Date date,int n,String pattern) {
		if ( date == null ) {
			date = new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, n);
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(calendar.getTime());
	}

	/**
	 * 功能说明：获取当前时间的后n秒时间，并返回时间对象.
	 * @param date
	 * @param n
	 * @return
	 * @throws BmException
	 */
	public static Date beforeNSecond(Date date,int n) {
		if ( date == null ) {
			date = new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, n);
		//SimpleDateFormat format = new SimpleDateFormat(pattern);
		return calendar.getTime();
	}

	/**
	 * 得到两个日期之间的天数(会返两个日期之间具体的天数)
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws ParseException
	 */
	  public static List<Date> getDates(String startTime,  String endTime) throws ParseException {
			Calendar endCalendar=Calendar.getInstance();
			Calendar startCalendar=Calendar.getInstance();
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
			Date date1=dateFormat.parse(endTime);
	        Date date=dateFormat.parse(startTime);
	        startCalendar.setTime(date);
			endCalendar.setTime(date1);
	        List<Date> result = new ArrayList<Date>();
	        while(true){
		        startCalendar.add(Calendar.DAY_OF_MONTH, 1);
		        if(startCalendar.getTimeInMillis() < endCalendar.getTimeInMillis()){
		            //System.out.println(dateFormat.format(startCalendar.getTime()));
		        	result.add(startCalendar.getTime());
		        }else{
		            break;
		        }
			 }
	        return result;
	    }
	  
	  /****
		 * 返回两个时间之间的天数（会返回之间的天数之和）
		 * @author jiangxuecheng
		 * @date 2015-12-31
		 * @param startTime
		 * @param endTime
		 * @param format
		 * @return
		 * @throws ParseException
		 */
		public static long getDateSUM(String startTime, String endTime, String format) throws ParseException{
			SimpleDateFormat sd = new SimpleDateFormat(format);
			long nd = 1000*24*60*60;//一天的毫秒数
			long nh = 1000*60*60;//一小时的毫秒数
			long nm = 1000*60;//一分钟的毫秒数
			long ns = 1000;//一秒钟的毫秒数long
			long  diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			long day = diff/nd;//计算差多少天
			long hour = diff%nd/nh;//计算差多少小时
			long min = diff%nd%nh/nm;//计算差多少分钟
			long sec = diff%nd%nh%nm/ns;//计算差多少秒//输出结果
			//System.out.println("时间相差："+day+"天"+hour+"小时"+min+"分钟"+sec+"秒");
			return day;
		}
		/**
		 * 字符串转日期
		 * @param str
		 * @return
		 */
		public static Date StrToDate(String str) {
			   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			   Date date = null;
			   try {
			    date = format.parse(str);
			   } catch (ParseException e) {
			    e.printStackTrace();
			   }
			   return date;
		}
		
		/**
		 * 字符串转日期
		 * @param str
		 * @return
		 */
		public static Date StrToDatetwo(String str,String format) {
			   SimpleDateFormat ft = new SimpleDateFormat(format);
			   Date date = null;
			   try {
			    date = ft.parse(str);
			   } catch (ParseException e) {
			    e.printStackTrace();
			   }
			   return date;
		}
		/**
		 * 日期转字符串
		 * @param date
		 * @return
		 */
		public static String DateToStr(Date date) {
			   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			   String str = format.format(date);
			   return str;
		}
		public static String DateToStrtwo(Date date,String ft) {
			   SimpleDateFormat format = new SimpleDateFormat(ft);
			   String str = format.format(date);
			   return str;
		}
		/**
		 * 获取N年以后的时间
		 */
		public static String getDatebyYear(int year){
			java.text.Format formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date todayDate=new java.util.Date();
			long afterTime=(todayDate.getTime()/1000)+60*60*24*365*year;
			todayDate.setTime(afterTime*1000);
			String afterDate=formatter.format(todayDate);
			return afterDate;
		}
		
		

		
		/** 
	     * 时间戳转换成日期格式字符串 
	     * @param seconds 精确到秒的字符串 
	     * @param formatStr 
	     * @return 
	     */  
	    public static String timeStampToDate(String seconds,String format) {  
	        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){  
	            return "";  
	        }  
	        if(format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";  
	        SimpleDateFormat sdf = new SimpleDateFormat(format);  
	        return sdf.format(new Date(Long.valueOf(seconds+"000")));  
	    } 
	    
	    
	    /** 
	     * 日期格式字符串转换成时间戳 
	     * @param date 字符串日期 
	     * @param format 如：yyyy-MM-dd HH:mm:ss 
	     * @return  ss
	     */  
	    public static String dateToTimeStamp(String date_str,String format){  
	        try {  
	            SimpleDateFormat sdf = new SimpleDateFormat(format);  
	            return String.valueOf(sdf.parse(date_str).getTime()/1000);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        return "";  
	    }  
	    
	    /** 
	     * 获取当月的 天数 
	     * */  
	    public static int getCurrentMonthDay() {  
	          
	        Calendar a = Calendar.getInstance();  
	        a.set(Calendar.DATE, 1);  
	        a.roll(Calendar.DATE, -1);  
	        int maxDate = a.get(Calendar.DATE);  
	        return maxDate;  
	    }  
	  
	    /** 
	     * 根据年 月 获取对应的月份 天数 
	     * */  
	    public static int getDaysByYearMonth(int year, int month) {  
	          
	        Calendar a = Calendar.getInstance();  
	        a.set(Calendar.YEAR, year);  
	        a.set(Calendar.MONTH, month - 1);  
	        a.set(Calendar.DATE, 1);  
	        a.roll(Calendar.DATE, -1);  
	        int maxDate = a.get(Calendar.DATE);  
	        return maxDate;  
	    }
	    public static void main(String[] args)
		{
			try
			{
				System.out.println(getDateSUM("2015-10-21 11:00	", "2015-10-31 12:00", "yyyy-MM-dd HH:mm"));
			}
			catch (ParseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
