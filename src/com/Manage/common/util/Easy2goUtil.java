package com.Manage.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;

import org.apache.log4j.Logger;

/**
 * 把一些常用的业务相关的小工具方法放这里， 如兑换码生成等
 *
 * Easy2goUtil.java
 * @author tangming@easy2go.cn 2015-12-1
 *
 */
public class Easy2goUtil {
//	private Logger logger = LogUtil.getInstance(Easy2goUtil.class);

	/**
	 * 使用当前时间戳结合phone生成兑换码 调用了 {@link #generateExchangeKey(String, String)}
	 *
	 * @param phone
	 * @return
	 */
	public static Map<String, String> generateExchangeKey(String phone) {
		Long keyTimestamp = System.currentTimeMillis();
		String paddingSecondKey = generateExchangeKey(phone, keyTimestamp);

		Map<String, String> result = new HashMap<String, String>();
		result.put(keyTimestamp + "" , paddingSecondKey);
		return result;
	}

	/**
	 * 使用phone和timestamp生成兑换码 接受String类型的timestamp 调用了 {@link #generateExchangeKey(String, String)}
	 *
	 * @param phone
	 * @param keyTimestampString
	 * @return
	 */
	public static String generateExchangeKey(String phone, String keyTimestampString) {
		Long keyTimestamp;
		try {
			keyTimestamp = Long.valueOf(keyTimestampString);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}

		return generateExchangeKey(phone, keyTimestamp);
	}

	/**
	 * 使用phone和timestamp生成兑换码
	 *
	 * @param phone
	 * @param keyTimestamp
	 * @return
	 */
	public static String generateExchangeKey(String phone, Long keyTimestamp) {
		CRC32 c = new CRC32();
		c.update(phone.getBytes());
		Long firstKey = c.getValue();

		String paddingFirstKey = String.format("%010d", firstKey) + keyTimestamp;
		c.update(paddingFirstKey.getBytes());
		Long secondKey = c.getValue();
		String paddingSecondKey = String.format("%010d", secondKey);

////		logger.info("getKey: phone=" + phone + " firstKey=" + firstKey + " paddingFirstKey=" + paddingFirstKey
////		+ " paddingSecondKey=" + paddingSecondKey);
//		logger.info("generateExchangeKey ok"); // 上线时禁止日志显示手机和兑换码

		return paddingSecondKey;
	}

	/**
	 * 测试 generateExchangeKey(String phone, Long keyTimestamp)
	 * @param args
	 */
	public static void main(String[] args) {
		// 13691639673 1448869905976 3155235945
		// 13691639673 1448867916606 2280011920
		// 13512344321 1448866899606 1555816423

		System.out.println("generateExchangeKey('13691639673', 1448869905976)="
				+ generateExchangeKey("13691639673", 1448869905976L));
		System.out.println("generateExchangeKey('13691639673', 1448867916606)="
				+ generateExchangeKey("13691639673", 1448867916606L));
		System.out.println("generateExchangeKey('13512344321', 1448866899606)="
				+ generateExchangeKey("13512344321", 1448866899606L));

		System.out.println("generateExchangeKey('13691639673', 1448869905976)="
				+ generateExchangeKey("13691639673", "1448869905976"));
		System.out.println("generateExchangeKey('13691639673', 1448867916606)="
				+ generateExchangeKey("13691639673", "448867916606"));
	}
	
	public static String lldw(String l){
		java.text.DecimalFormat   df   =new   java.text.DecimalFormat("0.00");  
		if(StringUtils.isBlank(l)){
			return "";
		}
		String dw = l.substring(l.length()-1);
		String result = "";
		switch (dw) {
		case "k":
			if(Double.parseDouble(l.replace("k", ""))/1024/1024/1024>=1){
				result = df.format(Double.parseDouble(l.replace("k", ""))/1024/1024/1024)+"T";
			}else if(Double.parseDouble(l.replace("k", ""))/1024/1024>=1){
				result = df.format(Double.parseDouble(l.replace("k", ""))/1024/1024)+"G";
			}else if(Double.parseDouble(l.replace("k", ""))/1024>=1){
				result = df.format(Double.parseDouble(l.replace("k", ""))/1024)+"M";
			}else{
				result = df.format(Double.parseDouble(l.replace("k", "")))+"kb";
			}
			break;
		case "M":
			if(Double.parseDouble(l.replace("M", ""))/1024/1024/1024>=1){
				result = df.format(Double.parseDouble(l.replace("M", ""))/1024/1024/1024)+"T";
			}else if(Double.parseDouble(l.replace("M", ""))/1024/1024>=1){
				result = df.format(Double.parseDouble(l.replace("M", ""))/1024/1024)+"G";
			}else{
				result = df.format(Double.parseDouble(l.replace("M", "")))+"M";
			}
			break;
		case "G":
			if(Double.parseDouble(l.replace("G", ""))/1024/1024/1024>=1){
				result = df.format(Double.parseDouble(l.replace("G", ""))/1024/1024/1024)+"T";
			}else{
				result = df.format(Double.parseDouble(l.replace("G", "")))+"G";
			}
			break;
		case "T":
			result =df.format( Double.parseDouble(l.replace("T", "")))+"T";
			break;

		default:
			break;
		}
		return result;
	}

}
