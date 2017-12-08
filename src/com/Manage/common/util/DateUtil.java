package com.Manage.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 日期处理的工具类
 * 
 */
public class DateUtil {

	/**
	 * 格式化Date型为String pattern
	 * 
	 * @param date
	 * @return
	 */
	private static String dateFormatStr = "yyyy-MM-dd";
	public static String dateFormatStr1 = "yyyy年MM月dd日";
	public static String dateFormatStr2 = "yyyy年MM月dd日 HH时mm分ss秒";
	public static String dateFormatStr3 = "MM-dd HH:mm";
	public static String dateFormatStr4 = "dd.MM月";
	public static String dateFormatStr5 = "yyyy.MM.dd";
	private static DateFormat mdhm = new SimpleDateFormat(dateFormatStr3);
	private static DateFormat dfmts = new SimpleDateFormat(dateFormatStr4);
	private static DateFormat sdf5 = new SimpleDateFormat(dateFormatStr5);
	private static DateFormat sdf = new SimpleDateFormat(dateFormatStr);
	static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 格式化时
	 * 
	 * @param time
	 * @param needHMS
	 *            不需要时分秒
	 * @return
	 */
	public static String formatDateToString(long time, boolean needHMS) {
		SimpleDateFormat sdf = null;
		if (needHMS) {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}

		return sdf.format(new Date(time));

	}
	
	
	/**
	 * 格式化时
	 * 
	 * @param time 字符串yyyy-MM-dd HH:mm:ss
	 * @return 字符串 yyyy-MM-dd
	 */
	public static String formatDateToString(String time) {
		if(time==null){
			return DateUtil.getCurrentYYYYMMDDString();
		}
		String[] array=time.split(" ");
		return array[0];
	}

	/**
	 * 格式化时
	 * 
	 * @param time
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static String formatDateToString(long time, String format) {
		SimpleDateFormat sdf = null;
		sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(time));

	}

	/**
	 * 格式化时�?
	 * 
	 * @param time
	 * @return
	 */
	public static String formatDateYYYYMMDDHHMM(long time) {
		SimpleDateFormat sdf = null;
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		return sdf.format(new Date(time));

	}

	/**
	 * 格式化时�?
	 * 
	 * @param time
	 * @return
	 */
	public static String formatDateYYYYMMDDHHMM(Date time) {
		SimpleDateFormat sdf = null;
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (time != null) {
			return sdf.format(time);
		}
		return null;

	}

	/**
	 * 格式化时�?
	 * 
	 * @param time
	 * @param needHMS
	 *            �?不需要时分秒
	 * @return
	 */
	public static String formatDateToString(Date date, boolean needHMS) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat sdf = null;
		if (needHMS) {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}

		return sdf.format(date);

	}

	/**
	 * 功能：获取上�?个月的第�?天和�?后一天，返回�?个数�?
	 * 
	 * @return
	 */
	public static String[] getLastMonthOfFirstLastDay() {
		// 取得系统当前时间
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		// 取得系统当前时间�?在月第一天时间对�?
		cal.set(Calendar.DAY_OF_MONTH, 1);
		// 日期减一,取得上月�?后一天时间对�?
		cal.add(Calendar.DAY_OF_MONTH, -1);
		// 输出上月�?后一天日�?
		int day = cal.get(Calendar.DAY_OF_MONTH);

		String months = "";
		String days = "";

		if (month > 1) {
			month--;
		} else {
			year--;
			month = 12;
		}
		if (!(String.valueOf(month).length() > 1)) {
			months = "0" + month;
		} else {
			months = String.valueOf(month);
		}
		if (!(String.valueOf(day).length() > 1)) {
			days = "0" + day;
		} else {
			days = String.valueOf(day);
		}
		String firstDay = "" + year + "-" + months + "-01";
		String lastDay = "" + year + "-" + months + "-" + days;

		String[] lastMonth = new String[2];
		lastMonth[0] = firstDay;
		lastMonth[1] = lastDay;

		// System.out.println(lastMonth[0] + "||" + lastMonth[1]);
		return lastMonth;
	}

	/**
	 * 
	 * 功能：获取上�?周的�?始日期和结束日期
	 */

	public static String[] getLastWeekFirstLastDay() {

		Calendar currentDate = Calendar.getInstance();

		currentDate.setFirstDayOfWeek(Calendar.MONDAY);

		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String firstDay = sdf.format(currentDate.getTimeInMillis() - 1000 * 60
				* 60 * 24 * 7);

		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

		String lastDay = sdf.format(currentDate.getTimeInMillis() - 1000 * 60
				* 60 * 24 * 7);

		String[] strs = new String[2];
		strs[0] = firstDay;
		strs[1] = lastDay;
		return strs;

	}

	/**
	 * 
	 * 功能：获取本年的�?有星期日日期
	 */

	public static String[] getWeekFirstLastDay() {

		Calendar currentDate = Calendar.getInstance();

		currentDate.setFirstDayOfWeek(Calendar.MONDAY);

		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String firstDay = sdf.format(currentDate.getTimeInMillis() - 1000 * 60
				* 60 * 24 * 7);

		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

		String lastDay = sdf.format(currentDate.getTimeInMillis() - 1000 * 60
				* 60 * 24 * 7);

		String[] strs = new String[2];
		strs[0] = firstDay;
		strs[1] = lastDay;
		return strs;

	}

	public static DateFormat getDateFormat() {
		return sdf;
	}

	/**
	 * 日期比较（年-�?-日）
	 * 
	 * @param oneDate
	 * @param twoDate
	 * @return String
	 */
	public static String dateComparison(Date oneDate, Date twoDate) {
		Date d1, d2;
		try {
			d1 = sdf.parse(getDateString(oneDate));
			d2 = sdf.parse(getDateString(twoDate));
			return d1.equals(d2) ? "same"
					: (d1.before(d2) ? "before" : "after");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getDateString(Date date) {
		return getDateFormat().format(date);
	}

	public static String getFormatDateByFormat(Date date, String patten) {
		SimpleDateFormat sdf = new SimpleDateFormat(patten);
		return sdf.format(date);
	}

	/**
	 * 获取系统当前日期
	 * 
	 * @return String
	 */
	public static String getCurrentDateString() {

		return sf.format(new java.util.Date());
	}

	/**
	 * 获取系统当前时间
	 * 
	 * @return yyyy-mm-dd
	 * */
	public static synchronized String getCurrentYYYYMMDDString() {
		return sdf.format(new java.util.Date());
	}

	/**
	 * 获取系统当前日期
	 * 
	 * @param pattern
	 * @return String
	 */
	public static String getCurrentDateString(String pattern) {
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		return sf.format(new java.util.Date());
	}

	/**
	 * 格式化String型为Date yyyy-MM-dd
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date parseToDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (date == null || date.trim().equals("")) {
			date = sdf.format(new Date());
		}
		try {
			return sdf.parse(date);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 增加日期�?
	 * 
	 * @param date
	 * @param x
	 *            (天数)
	 * @return Date
	 */
	public static Date addDate(Date date, int x) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, x);
		date = cal.getTime();
		cal = null;
		return parseToDate(format.format(date));
	}

	/**
	 * 增加月份数后的日�?
	 * 
	 * @param date
	 * @param intMonths
	 *            (月份�?)
	 * @return Date
	 */
	public static Date addMonth(Date date, int intMonths) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, intMonths);
		date = cal.getTime();
		cal = null;
		return parseToDate(format.format(date));
	}

	/**
	 * 格式化日期yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDate(Date date) {
		if (date != null) {
			return sf.format(date);
		} else {
			return null;
		}
	}

	/**
	 * 格式化日期为yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateYYYYMMDD(Date date) {

		return sdf.format(date);
	}

	public static Date parseDate(String date) {

		try {
			return sf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static synchronized Date parseDateYYYY_MM_DD(String date) {

		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当前时间
	 * 
	 * @return Timestamp
	 */
	public static Timestamp getTimestamp() {

		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 
	 * @param time
	 * @return String[]
	 */
	public static String[] splitTime(String time) {

		String[] t = { "", "", "", "", "", "" };

		String[] tm1 = time.split(",");
		int x = 0;
		for (int i = 0; i < tm1.length; i++) {

			if (tm1[i].length() > 3) {
				String[] tm2 = tm1[i].split("-");
				t[x] = tm2[0];
				t[x + 1] = tm2[1];
			}
			x = x + 2;
		}

		return t;
	}

	public static String caseCover(String[] strStatus, int operation) {
		String str = new String();
		if (operation == 1) {
			char[] c = { '0', '0', '0', '0', '0', '0', '0' };
			if (null != strStatus && strStatus.length > 0) {
				for (int i = 0; i < strStatus.length; i++) {
					for (int j = 0; j <= 7; j++) {
						if (Integer.parseInt(strStatus[i]) == j) {
							if (j == 7)
								c[0] = '1';
							else
								c[j] = '1';
						}
					}
				}
			} else
				str = "0000000";
			str = new String(c);
		}
		if (operation == 2) {
			char[] c = { '7', '1', '2', '3', '4', '5', '6' };
			if (null != strStatus && strStatus.length > 0) {
				for (int i = 0; i < strStatus.length; i++) {
					String strValue = strStatus[0];
					for (int j = 0; j < strValue.length(); j++) {
						if ("1".equals(strValue.substring(j, j + 1))) {
							str += c[j] + ",";
						}
					}
				}
			}
			if (str.length() != 0) {
				str = str.substring(0, str.length() - 1);
			}
		}

		return str;
	}

	/**
	 * 计算两个时间之间的间�?
	 * 
	 * @param d1
	 * @param d2
	 * @return 间隔的毫秒数
	 */
	public static long compareDate(Date d1, Date d2) {

		long val = d2.getTime() - d1.getTime();
		return Math.abs(val);
	}

	/**
	 * 计算两个日期的天数间�?
	 * 
	 * @param d1
	 * @param d2
	 * @return 间隔的天�?
	 */
	public static long calculateDayCount(Date d1, Date d2) {
		long val = (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24);
		return Math.abs(val);
	}

	static String[] six = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
			"A", "B", "C", "D", "E", "F" };

	// 二进制转换成十六进制
	public static String TwoConvertSixteen(String value) {
		int length = value.length();
		int temp = length % 4;
		String binaryLength = "";
		if (temp != 0) {
			for (int i = 0; i < 16 - value.length(); i++) {
				binaryLength = binaryLength + "0";
			}
		}
		value += binaryLength;
		length = value.length();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length / 4; i++) {
			int num = 0;
			for (int j = i * 4; j < i * 4 + 4; j++) {
				num <<= 1;
				num |= (value.charAt(j) - '0');
			}
			sb.append(six[num]);
		}
		return sb.toString();
	}

	// 十六进制转换成二进制
	public static String SixteenConvertTwo(String value) {
		int i = Integer.parseInt(value, 16);
		String returnValue = Integer.toBinaryString(i);
		return returnValue;
	}

	// 二进制转换成普�?�参�?
	public static String TwoConvertCommon(String value) {

		String[] c = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
				"11", "12", "13", "14", "15" };
		String str = "";
		if (null != value && value.length() > 0) {
			String[] a = value.split(",");
			for (int j = 0; j < a.length; j++) {
				if ("1".equals(a[j])) {
					str += c[j] + ",";
				}
			}
		}
		if (str.length() != 0) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	// 普�?�参数转换成二进�?
	public static String CommonConvertTwo(String[] value) {
		char[] c = { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
				'0', '0', '0', '0', '0' };
		String[] a = value[0].split(",");
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j <= 16; j++) {
				if (Integer.parseInt(a[i]) == j) {
					c[j] = '1';
				}
			}
		}
		return new String(c);
	}

	/**
	 * 判断日期格式是否正确
	 * 
	 * @param sDate
	 * @return true or false
	 * @author Bacel.chen
	 */
	public static boolean isValidDate(String sDate) {
		String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
		String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
				+ "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
				+ "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
				+ "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		if ((sDate != null)) {
			Pattern pattern = Pattern.compile(datePattern1);
			Matcher match = pattern.matcher(sDate);
			if (match.matches()) {
				pattern = Pattern.compile(datePattern2);
				match = pattern.matcher(sDate);
				return match.matches();
			} else {
				return false;
			}
		}
		return false;
	}

	public static Timestamp parseToTimestamp(String dateStr) {

		Calendar cal = Calendar.getInstance();
		try {
			Date date = sf.parse(dateStr);
			date.getTime();
			cal.setTime(date);
			return new Timestamp(cal.getTimeInMillis());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 将时间转换成 几分钟前，几小时�? 等字符串
	 * 
	 * @author 钟文�?
	 * @param date
	 * @return
	 */
	public static String getTimeFormatText(Date date) {
		long currTime = System.currentTimeMillis();
		long time = date.getTime();
		long diffTime = currTime - time;
		long aday = 1000 * 60 * 60 * 24;
		long ahour = 1000 * 60 * 60;
		long amin = 1000 * 60;
		long r = 0;
		if (diffTime < amin) {
			return "刚刚";
		} else if (diffTime >= amin && diffTime < ahour) {
			r = diffTime / amin;
			return r + "分钟前";
		} else if (diffTime >= ahour && diffTime < aday) {
			r = diffTime / ahour;
			return r + "小时前";
		} else if (diffTime >= aday && diffTime <= 3 * aday) {
			r = diffTime / aday;
			return r + "天前";
		} else {
			return DateUtil.formatDateYYYYMMDDHHMM(date);
		}
	}

	/**
	 * 将时间转换成几分钟，几小时，几天前等
	 * 
	 * @author lhl
	 * @param date
	 * */
	public static String getTimeFormatText2(Date date) {
		long currTime = System.currentTimeMillis();
		long time = date.getTime();
		long diffTime = currTime - time;
		long aday = 1000 * 60 * 60 * 24;
		long ahour = 1000 * 60 * 60;
		long amin = 1000 * 60;
		long r = 0;
		if (diffTime < amin) {
			return "刚刚";
		} else if (diffTime >= amin && diffTime < ahour) {
			r = diffTime / amin;
			return r + "分钟前";
		} else if (diffTime >= ahour && diffTime < aday) {
			r = diffTime / ahour;
			return r + "小时前";
		} else if (diffTime >= aday && diffTime <= 7 * aday) {
			r = diffTime / aday;
			return r + "天前";
		} else {
			return 7+"天前";
		}
	}

	public static long getTwoTimeDiffS(String stime, String etime) {
		long diff = 0;
		try {
			Date startTime = sf.parse(stime);
			Date endTime = sf.parse(etime);

			if (startTime.before(endTime)) {
				diff = endTime.getTime() - startTime.getTime();
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return diff;
	}
	
	public static long compareDate(Date startTime){
		long diff = 0;
		try {							
				long start=startTime.getTime();
			    String today=formatDateYYYYMMDD(new Date());
			    Date day=parseToDate(today);
			    long tod=day.getTime();
				diff = (start - tod) / (1000 * 60 * 60 * 24);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return diff;
	}
	

	public static Date getLastMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		return cal.getTime();
	}

	public static String getCurrMonth(String date) {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sf.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.add(Calendar.MONTH, 0);
		return formatDateToString(cal.getTime().getTime(), "yyyyMM");
	}

	public static int getCurrMonthDay(String date) {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sf.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.get(Calendar.DAY_OF_MONTH);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static List<String> getTwoTimeDiffMonth(String stime, String etime) {
		List<String> months = new ArrayList<String>();
		try {
			Date startTime = sf.parse(stime);
			Date endTime = sf.parse(etime);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			startTime = sdf.parse(sdf.format(startTime));
			endTime = sdf.parse(sdf.format(endTime));

			Date lastMonth = getLastMonth(endTime);

			while (startTime.before(lastMonth)) {
				String month = sdf.format(lastMonth);
				months.add(month);
				// System.out.println(month);
				lastMonth = getLastMonth(lastMonth);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return months;
	}

	public static boolean isSameYearMonth(String stime, String etime) {
		Date startTime = null;
		Date endTime = null;
		try {
			startTime = sf.parse(stime);
			endTime = sf.parse(etime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Calendar calStime = Calendar.getInstance();
		calStime.setTime(startTime);

		Calendar calEtime = Calendar.getInstance();
		calEtime.setTime(endTime);

		if (calStime.get(Calendar.YEAR) == calEtime.get(Calendar.YEAR)
				&& calStime.get(Calendar.MONTH) == calEtime.get(Calendar.MONTH)) {
			return true;
		} else {
			return false;
		}

	}

	/** 年月日 */
	public static String getDate(Date time) {
		if(time == null || "".equals(time)){
			return "";
		}
		DateFormat sdf = new SimpleDateFormat(dateFormatStr5);
		return sdf.format(time);
	}

	/** 获取月 日 时 分 */
	public static String getStringMMDDHHmm(Date time) {

		return mdhm.format(time);
	}

	/** 格式为10.10月 */
	public static String getStringDDMM(Date time) {
		return dfmts.format(time);
	}

	/** 格式化为yyyy.mm.dd */
	public static String getStringyyyymmdd(Date time) {
		return sdf5.format(time);

	}
	
	/**
	 * 组装两个时间   如：2014-12-05 23:34至 2014-12-05 23:50  变为   12.05 23:34-23:50
	 * @param args
	 * @author zwh
	 */
	public static String package2Time(Date startTime,Date endTime){
		
		//开始时间
		Calendar startCal=Calendar.getInstance();
		startCal.setTime(startTime);
		int startYear=startCal.get(Calendar.YEAR);
		int startMonth=startCal.get(Calendar.MONTH);
		int startDay=startCal.get(Calendar.DAY_OF_MONTH);
		
		//结束时间
		Calendar endCal=Calendar.getInstance();
		endCal.setTime(endTime);
		int endYear=endCal.get(Calendar.YEAR);
		int endMonth=endCal.get(Calendar.MONTH);
		int endDay=endCal.get(Calendar.DAY_OF_MONTH);
		
		//当前时间
		Calendar currCal=Calendar.getInstance();
		int currYear=currCal.get(Calendar.YEAR);
		int currMonth=currCal.get(Calendar.MONTH);
		int currDay=currCal.get(Calendar.DAY_OF_MONTH);
		
		String t1="";
		String t2="";
		if(startYear==endYear){
			if(startMonth==endMonth){
				if(startDay==endDay){
					if(currYear==startYear&&currMonth==startMonth&&currDay==startDay){//当天
						t1=formatDateToString(startTime.getTime(),"HH:mm");
						t2=formatDateToString(endTime.getTime(), "HH:mm");
						return "今天  "+t1+"-"+t2;
					}
					t1=formatDateToString(startTime.getTime(),"MM.dd HH:mm");
					t2=formatDateToString(endTime.getTime(), "HH:mm");
					return t1+"-"+t2;
				}
			}
			t1=formatDateToString(startTime.getTime(),"MM.dd HH:mm");
			t2=formatDateToString(endTime.getTime(), "MM.dd HH:mm");
			return t1+"-"+t2;
		}else{
			t1=formatDateToString(startTime.getTime(),"yyyy.MM.dd HH:mm");
			t2=formatDateToString(endTime.getTime(), "yyyy.MM.dd HH:mm");
		}
		return t1+"-"+t2;
	}
	
	/**
	 * 组装两个时间   如：2014-12-05 23:34至 2014-12-05 23:50  变为   12.05 23:34-23:50
	 * @param args
	 * @author zwh
	 */
	public static String package2Date(Date startTime,Date endTime){
		
		if(startTime==null||"".equals(startTime)){
			return "";
		}
		
		//开始时间
		Calendar startCal=Calendar.getInstance();
		startCal.setTime(startTime);
		int startYear=startCal.get(Calendar.YEAR);
		int startMonth=startCal.get(Calendar.MONTH);
		int startDay=startCal.get(Calendar.DAY_OF_MONTH);
		
		//当前时间
		Calendar currCal=Calendar.getInstance();
		int currYear=currCal.get(Calendar.YEAR);
		
		if(endTime==null||"".equals(endTime)){
			if(startYear==currYear){
				return formatDateToString(startTime.getTime(),"MM月dd日");
			}
			return formatDateToString(startTime.getTime(),"yyyy年MM月dd日");
		}
		
		//结束时间
		Calendar endCal=Calendar.getInstance();
		endCal.setTime(endTime);
		int endYear=endCal.get(Calendar.YEAR);
		int endMonth=endCal.get(Calendar.MONTH);
		int endDay=endCal.get(Calendar.DAY_OF_MONTH);
	
		if(startYear==endYear){
			if(startYear==currYear){//是当年
				if(startMonth==endMonth){
					if(startDay==endDay){
						return formatDateToString(startTime.getTime(),"MM月dd日");
					}
					return formatDateToString(startTime.getTime(),"MM月dd日")+"-"+formatDateToString(endTime.getTime(), "dd日");
				}
				return formatDateToString(startTime.getTime(),"MM月dd日")+"-"+formatDateToString(endTime.getTime(), "MM月dd日");
			}else{//不是当年
				if(startMonth==endMonth){
					if(startDay==endDay){
						return formatDateToString(startTime.getTime(),"yyyy年MM月dd日");
					}
					return formatDateToString(startTime.getTime(),"yyyy年MM月dd日")+"-"+formatDateToString(endTime.getTime(), "dd日");
				}
			}
		}
		return formatDateToString(startTime.getTime(),"yyyy.MM.dd")+"-"+formatDateToString(endTime.getTime(), "yyyy.MM.dd");
	}
	
	
	

	/**判断是不是当年*/
	public static String toCompareYear(Date time){
		String result="";
		try{
		   if(time ==null){
				return "";					   
		   }			
		 Calendar calStime = Calendar.getInstance();
		 calStime.setTime(time);

		 Calendar calEtime = Calendar.getInstance();
		 calEtime.setTime(new Date());
		if (calStime.get(Calendar.YEAR) == calEtime.get(Calendar.YEAR)) {
			result=formatDateToString(time.getTime(),"MM.dd");
		} else {
			result=getDate(time);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
				
	}
	
	/**字符串秒数转换成Date*/
	public static Date str2Date(String intSec){
		try {
			long sec=Long.parseLong(intSec);
			return new Date(sec*1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new Date();
	}
	
	/**判断两个时间是否是同一自然天*/
	public static boolean isSameDate(long t1,long t2){
		Calendar cal1=Calendar.getInstance();
		cal1.setTimeInMillis(t1);
		Calendar cal2=Calendar.getInstance();
		cal2.setTimeInMillis(t2);
		
		if(cal1.get(Calendar.YEAR)!=cal2.get(Calendar.YEAR)){
			return false;
		}
		if(cal1.get(Calendar.MONTH)!=cal2.get(Calendar.MONTH)){
			return false;
		}
		if(cal1.get(Calendar.DAY_OF_MONTH)!=cal2.get(Calendar.DAY_OF_MONTH)){
			return false;
		}
		return true;
		
	}

	// public static void main(String[] args) {
	// // System.out.println(getLastWeekFirstLastDay()[0]);
	// // System.out.println(getLastWeekFirstLastDay()[1]);
	//
	// Long time=null;
	// formatDateToString(null, true);
	//
	// }

	public static void main(String[] args) {
		// System.out.println(addMonth(new
		// Date(), 1));

		// System.out.println(compareDate(new Date(),addMonth(new
		// Date(),10))/(1000*60*60*24));

		// System.out.println(calculateDayCount(new Date(),addMonth(new
		// Date(),10)));
		// System.out.println(dateComparison(new Date(), addDate(new Date(),
		// 2)));
		// System.out.println((getTimestamp().getTime()-Timestamp.valueOf("2012-04-19 12:00:00").getTime())/60000);
		// System.out.println(isValidDate("忘记�?"));
//		long time = 1406356200000l;// 20140726 14:30:00
//		long currTime = System.currentTimeMillis();
		// if(relLikeNum>300){//当真实赞大于300时才加虚拟赞
		// }
		// System.out.println(((currTime-time)/(1000*60l))/3);

		// List<String> list=getTwoTimeDiffMonth("2014-02-08 12:00:00",
		// "2014-06-08 12:00:00");
		// System.out.println(getCurrMonth("2014-07-26 12:00:00"));
		// System.out.println(getCurrMonthDay("2014-07-26 12:00:00"));
		//
		// System.out.println(isSameYearMonth("2014-06-26 12:00:00",
		// "2014-07-26 12:00:00"));
		//
		// System.out.println((30-DateUtil.getCurrMonthDay("2014-06-26 12:00:00"))/30.0);

//		System.out.println(getTwoTimeDiffMonth("2014-06-01 01:34:44",
//				"2014-07-26 12:00:00"));
//		String str=package2Date(new Date(System.currentTimeMillis()), null);
//        System.out.println(formatDateToString("2016-09-21 17:24:50"));
	}

}
