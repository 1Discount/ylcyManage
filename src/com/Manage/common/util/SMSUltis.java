package com.Manage.common.util;

import java.util.HashMap;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Manage.common.cache.CacheUtils;
import com.cloopen.rest.sdk.CCPRestSmsSDK;

/**
 * 目前使用的短信服务商系 容联云通讯 http://www.yuntongxun.com/
 * 参考文档:
 *     http://docs.yuntongxun.com/index.php/%E5%BC%80%E5%8F%91%E6%8C%87%E5%8D%97:%E7%9F%AD%E4%BF%A1%E9%AA%8C%E8%AF%81%E7%A0%81/%E9%80%9A%E7%9F%A5
 *     CCP_REST_SMS_DEMO_JAVA_v2.6r
 *
 * SMSUltis.java
 * @author tangming@easy2go.cn 2015-7-23
 *
 */
public class SMSUltis {
	private final static String ACCOUNT_SID = "aaf98f894c49ea4f014c5023128e0538";
	private final static String ACCOUNT_TOKEN = "7a1ac4f1d1534c39824f85c43dfc50c3";
	private final static String APP_ID = "8a48b5514d32a2a8014d56264bca1a87";
	private final static String SERVER_IP = "app.cloopen.com";
	private final static String SERVER_PORT = "8883";
	private final static String SOFT_VERSION = "2013-12-26";

	/** NodeJS 实现的一个样例
	 var postData = JSON.stringify({
		to:to,
		appId:appId,
		templateId:tempId || "20151",
		datas:infoArr
	 });
	 */

	/** 注册/重置等的手机验证码短信模板ID */
	private final static String TEMPLATE_ID_PHONE_CODE = "20151";

	/** 设备日志处所用的短信模板ID，但所用更多的模板ID见下面 {@link #sendTemplateSmsDeviceLog(String, String, String, String, String)} **/
	private static String TEMPLATE_ID_DEVICE_LOG = "41566"; // templateId:tempId || "40663"
	//43573电量过低
	//43576连接设备太多
	//43575上传下载流量过大
	//43572本地信号异常

	/** 兑换码发送成功短信模板ID */
	private final static String TEMPLATE_ID_EXCHANGE_KEY_SEND = "54473";
	// 【途狗全球WIFI】您好，您的兑换码为{1}，请及时到途狗官网，APP或进入微信公众号进行兑换。
	private final static String TEMPLATE_ID_VPNWARN_SEND = "71460";
	//系统预警短信提醒
	private final static String TEMPLATE_ID_SYSWARN_SEND = "79505";
	/** 手机验证码的有效时间系固定的, 与业务逻辑实现的缓存时间相关 目前按 NodeJS v2 实现使用 2 分钟 本系统为10分钟 */
	private final static String TEMPLATE_CACHE_DUATION = "10"; // 单位分钟
	public final static int TEMPLATE_CACHE_DUATION_INT_SECOND = 10 * 60 ;
	private final static String CACHE_NAME = "userCache";
	public final static int CODE_LENGHT = 6 ;

	/**
	 * 容联云通讯 发送短信 - {@link com.Manage.control.DeviceLogsControl#startsendmsm()} 设备日志里使用
	 *
	 * @param phone
	 * @param SMStext
	 * @param conectionCount
	 * @param dl
	 * @param type
	 * @return
	 */
	public static boolean sendTemplateSmsDeviceLog(final String phone,String SMStext,String conectionCount,String dl,String type) {
		//notificationrestart  tooManyDevicesConnected uploadAndDownloadTraffic regionalsignaldifference

		HashMap<String, Object> result = null;

		//初始化SDK
		CCPRestSmsSDK restAPI = new CCPRestSmsSDK();

		//******************************注释*********************************************
		//*初始化服务器地址和端口                                                       *
		//*沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
		//*生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883");       *
		//*******************************************************************************
		//restAPI.init("sandboxapp.cloopen.com", "8883");
		restAPI.init(SERVER_IP, SERVER_PORT);

		//******************************注释*********************************************
		//*初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN     *
		//*ACOUNT SID和AUTH TOKEN在登陆官网后，在“应用-管理控制台”中查看开发者主账号获取*
		//*参数顺序：第一个参数是ACOUNT SID，第二个参数是AUTH TOKEN。                   *
		//*******************************************************************************
		restAPI.setAccount(ACCOUNT_SID, ACCOUNT_TOKEN);

		//******************************注释*********************************************
		//*初始化应用ID                                                                 *
		//*测试开发可使用“测试Demo”的APP ID，正式上线需要使用自己创建的应用的App ID     *
		//*应用ID的获取：登陆官网，在“应用-应用列表”，点击应用名称，看应用详情获取APP ID*
		//*******************************************************************************
		restAPI.setAppId(APP_ID);

		//******************************注释****************************************************************
		//*调用发送模板短信的接口发送短信                                                                  *
		//*参数顺序说明：                                                                                  *
		//*第一个参数:是要发送的手机号码，可以用逗号分隔，一次最多支持100个手机号                          *
		//*第二个参数:是模板ID，在平台上创建的短信模板的ID值；测试的时候可以使用系统的默认模板，id为1。    *
		//*系统默认模板的内容为“【云通讯】您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入”*
		//*第三个参数是要替换的内容数组。																														       *
		//**************************************************************************************************

		//**************************************举例说明***********************************************************************
		//*假设您用测试Demo的APP ID，则需使用默认模板ID 1，发送手机号是13800000000，传入参数为6532和5，则调用方式为           *
		//*result = restAPI.sendTemplateSMS("13800000000","1" ,new String[]{"6532","5"});																		  *
		//*则13800000000手机号收到的短信内容是：【云通讯】您使用的是云通讯短信模板，您的验证码是6532，请于5分钟内正确输入     *
		//
		// 返回结果说明:
		// statusCode    String 必选 请求状态码，取值000000（成功）!! 此步响应只表明客户的短信请求发送成功，不表明短信通道已经发送短信成功。
		// smsMessageSid String 必选 短信唯一标识符
		// dateCreated   String 必选 短信的创建时间，格式：年-月-日 时:分:秒（如2013-02-01 15:38:09）
		//
		// 对响应解析后，statusCode为“000000”表示请求发送成功。statusCode不是“000000”，表示请求发送失败，客户服务端可以根据自己的逻辑进行重发或者其他处理。

		//*********************************************************************************************************************
		//result = restAPI.sendTemplateSMS("号码1,号码2等","模板Id" ,new String[]{"模板内容1","模板内容2"});
		if("batterylow".equals(type)){
			//电量低
			//43573电量过低
			TEMPLATE_ID_DEVICE_LOG="43573";
			result = restAPI.sendTemplateSMS(phone,TEMPLATE_ID_DEVICE_LOG , new String[] {SMStext,dl});
		}else if("notificationrestart".equals(type)){
			//通知重启
			//43570开关机异常
			TEMPLATE_ID_DEVICE_LOG="43570";
			result = restAPI.sendTemplateSMS(phone,TEMPLATE_ID_DEVICE_LOG , new String[] {SMStext});
		}else if("tooManyDevicesConnected".equals(type)){
			//设备连接太多
			//43576连接设备太多
			TEMPLATE_ID_DEVICE_LOG="43576";
			result = restAPI.sendTemplateSMS(phone,TEMPLATE_ID_DEVICE_LOG , new String[] {SMStext,conectionCount});
		}else if("uploadAndDownloadTraffic".equals(type)){
			//上传下载流量过大
			//43575上传下载流量过大
			TEMPLATE_ID_DEVICE_LOG="43575";
			result = restAPI.sendTemplateSMS(phone,TEMPLATE_ID_DEVICE_LOG , new String[] {SMStext});
		}else if("regionalsignaldifference".equals(type)){
			//地区信号差
			//43572本地信号异常
			TEMPLATE_ID_DEVICE_LOG="43572";
			result = restAPI.sendTemplateSMS(phone,TEMPLATE_ID_DEVICE_LOG , new String[] {SMStext});
		}
		//result = restAPI.sendTemplateSMS(phone,TEMPLATE_ID_DEVICE_LOG , new String[] {SMStext});
		System.out.println("SDKTestGetSubAccounts result=" + result);
		if("000000".equals(result.get("statusCode"))){
			//正常返回输出data包体信息（map）
//			HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
//			Set<String> keySet = data.keySet();
//			for(String key:keySet){
//				Object object = data.get(key);
//				System.out.println(key +" = "+object);
//			}

			return true;
		}else{
			//异常返回输出错误码和错误信息
			//前面已打印 System.out.println("发送短信失败 错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));

			// TODO: 尝试重发一次 // 未成功发送的话不算入计费条数? // 或者由客户直接再重试?
			// 需要适当延时吗?
		}

		return false;
	}

	/**
	 * 注册/重置密码等的发送手机验证码
	 *
	 * @param phone
	 * @return
	 */
	public static boolean sendTemplateSmsPhoneCode(final String phone) {
		HashMap<String, Object> result = null;
		//初始化SDK
		CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
		restAPI.init(SERVER_IP, SERVER_PORT);
		restAPI.setAccount(ACCOUNT_SID, ACCOUNT_TOKEN);
		restAPI.setAppId(APP_ID);

		//样例: result = restAPI.sendTemplateSMS("号码1,号码2等","模板Id" ,new String[]{"模板内容1","模板内容2"});
		result = restAPI.sendTemplateSMS(phone, TEMPLATE_ID_PHONE_CODE, new String[] { generateCode(phone), TEMPLATE_CACHE_DUATION });

		System.out.println("SDKTestGetSubAccounts result=" + result);
		if("000000".equals(result.get("statusCode"))){
			//正常返回输出data包体信息（map）
			return true;
		}else{
			//异常返回输出错误码和错误信息
			//前面已打印 System.out.println("发送短信失败 错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));

			// TODO: 尝试重发一次 // 未成功发送的话不算入计费条数? // 或者由客户直接再重试?
			// 需要适当延时吗?
		}
		return false;
	}

	/**
	 * 内部使用生成注册/重置密码等的短信验证码
	 * @param phone
	 * @return
	 */
	private static String generateCode(String phone) {
		// 验证号码
		if(null == phone) {
			return null;
		}
		// 生成随机数字字符串
		String code = RandomStringUtils.randomNumeric(CODE_LENGHT);
		CacheUtils.put(CACHE_NAME, phone, code);
		return code;
	}

	/**
	 * 检验短信验证码
	 * @param phone
	 * @param code
	 * @return
	 */
	public static boolean veriryCode(String phone, String code) {
		// 验证号码
		if(null == phone) {
			return false;
		}
		Object cachedCodeObject = CacheUtils.get(CACHE_NAME, phone);
		if(null != cachedCodeObject) {
			if(code.equals((String)cachedCodeObject)) {
				// 此处未应该去清除, 直接后面例如注册成功/重置密码成功才清除或缓存到期自动清除
				return true;
			}
		}
		return false;
	}

	/**
	 * 清除该手机的短信验证码
	 * @param phone
	 */
	public static void clearCode(String phone) {
		if (null != phone) {
			CacheUtils.remove(CACHE_NAME, phone);
		}
	}

	/**
	 * 发放兑换码给客户手机发信息
	 *
	 * @param phone
	 * @return
	 */
	public static boolean sendTemplateSmsExchangeCodeSend(final String phone, final String exchangeKey) {
		HashMap<String, Object> result = null;
		//初始化SDK
		CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
		restAPI.init(SERVER_IP, SERVER_PORT);
		restAPI.setAccount(ACCOUNT_SID, ACCOUNT_TOKEN);
		restAPI.setAppId(APP_ID);

		//样例: result = restAPI.sendTemplateSMS("号码1,号码2等","模板Id" ,new String[]{"模板内容1","模板内容2"});
		result = restAPI.sendTemplateSMS(phone, TEMPLATE_ID_EXCHANGE_KEY_SEND, new String[] { exchangeKey }); // phone,

		System.out.println("SDKTestGetSubAccounts result=" + result);
		if("000000".equals(result.get("statusCode"))){
			//正常返回输出data包体信息（map）
			return true;
		}else{
			//异常返回输出错误码和错误信息
			//前面已打印 System.out.println("发送短信失败 错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));

			// TODO: 尝试重发一次 // 未成功发送的话不算入计费条数? // 或者由客户直接再重试?
			// 需要适当延时吗?
		}
		return false;
	}
	
	
	/**
	 * pos机移动提示
	 *
	 * @param phone
	 * @return
	 */
	public static boolean sendTemplateSmsPOSSend(final String phone, final String date,String SN) {
		HashMap<String, Object> result = null;
		//初始化SDK
		CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
		restAPI.init(SERVER_IP, SERVER_PORT);
		restAPI.setAccount(ACCOUNT_SID, ACCOUNT_TOKEN);
		restAPI.setAppId(APP_ID);

		//样例: result = restAPI.sendTemplateSMS("号码1,号码2等","模板Id" ,new String[]{"模板内容1","模板内容2"});
		result = restAPI.sendTemplateSMS(phone, TEMPLATE_ID_EXCHANGE_KEY_SEND, new String[] {SN, date}); // phone,

		System.out.println("SDKTestGetSubAccounts result=" + result);
		if("000000".equals(result.get("statusCode"))){
			//正常返回输出data包体信息（map）
			return true;
		}else{
			//异常返回输出错误码和错误信息
			//前面已打印 System.out.println("发送短信失败 错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));

			// TODO: 尝试重发一次 // 未成功发送的话不算入计费条数? // 或者由客户直接再重试?
			// 需要适当延时吗?
		}
		return false;
	}
	
	
	/**
	 * VPN预警提示
	 *
	 * @param phone
	 * @return
	 */
	public static boolean sendTemplateSmsVPNWARN(final String phone, final String ip) {
		HashMap<String, Object> result = null;
		//初始化SDK
		CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
		restAPI.init(SERVER_IP, SERVER_PORT);
		restAPI.setAccount(ACCOUNT_SID, ACCOUNT_TOKEN);
		restAPI.setAppId(APP_ID);

		//样例: result = restAPI.sendTemplateSMS("号码1,号码2等","模板Id" ,new String[]{"模板内容1","模板内容2"});
		result = restAPI.sendTemplateSMS(phone,TEMPLATE_ID_VPNWARN_SEND, new String[] {ip}); // phone,

		System.out.println("SDKTestGetSubAccounts result=" + result);
		if("000000".equals(result.get("statusCode"))){
			//正常返回输出data包体信息（map）
			return true;
		}else{
			//异常返回输出错误码和错误信息
			//前面已打印 System.out.println("发送短信失败 错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));

			// TODO: 尝试重发一次 // 未成功发送的话不算入计费条数? // 或者由客户直接再重试?
			// 需要适当延时吗?
		}
		return false;
	}
	
	
	/**
	 * 
	 * @param phone
	 * @param ip
	 * @return
	 */
	public static boolean sendTemplateSmsSysWarn(final String phone, final String msg) {
		HashMap<String, Object> result = null;
		//初始化SDK
		CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
		restAPI.init(SERVER_IP, SERVER_PORT);
		restAPI.setAccount(ACCOUNT_SID, ACCOUNT_TOKEN);
		restAPI.setAppId(APP_ID);

		//样例: result = restAPI.sendTemplateSMS("号码1,号码2等","模板Id" ,new String[]{"模板内容1","模板内容2"});
		result = restAPI.sendTemplateSMS(phone,TEMPLATE_ID_SYSWARN_SEND, new String[] {msg}); // phone,

		System.out.println("SDKTestGetSubAccounts result=" + result);
		if("000000".equals(result.get("statusCode"))){
			//正常返回输出data包体信息（map）
			return true;
		}else{
			//异常返回输出错误码和错误信息
			//前面已打印 System.out.println("发送短信失败 错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));

			// TODO: 尝试重发一次 // 未成功发送的话不算入计费条数? // 或者由客户直接再重试?
			// 需要适当延时吗?
		}
		return false;
	}

}
