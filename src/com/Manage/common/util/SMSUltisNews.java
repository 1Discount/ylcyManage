package com.Manage.common.util;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;


import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.mail.HtmlEmail;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.Manage.common.mobiephone.DateUtil;
import com.Manage.common.mobiephone.EncryptUtil;
import com.Manage.common.mobiephone.SSLHttpClient;
import com.Manage.common.mobiephone.SysConfig;
import com.Manage.common.mobiephone.client.AbsRestClient;
import com.Manage.common.mobiephone.model.TemplateSMS;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.google.gson.Gson;

/**
 * @author lipeng@easy2go.cn 2016年6月3日16:37:54
 *
 */
public class SMSUltisNews {

	private Logger logger = LogUtil.getInstance(SMSUltisNews.class);


	public boolean isTest=Boolean.parseBoolean(SysConfig.getInstance().getProperty("is_test"));
	public String server=SysConfig.getInstance().getProperty("rest_server");
	public String sslIP=SysConfig.getInstance().getProperty("http_ssl_ip");
	public int sslPort=SysConfig.getInstance().getPropertyInt("http_ssl_port");
	public String version=SysConfig.getInstance().getProperty("version");

	/**
	 * 发短信公共方法
	 * @param phone 电话
	 * @param templateId 短信模板id
	 * @param conectionCount  设备连接数
	 * @param dl 电量
	 * @param SMStext 设备号
	 * @return
	 */
	public boolean sendTemplateSMSCloopen(final String phone,String templateId,String conectionCount,String dl,String SMStext,String ip,String excStr) {

			System.out.println("进入云之讯发短信接口...");
			// TODO Auto-generated method stub
			String result = "";
			DefaultHttpClient httpclient=getDefaultHttpClient();

			//默认值：
			String accountSid="d7d114bd922e6d5e4e291f1a29a3cbfe";//开发者id
			String authToken="1231ddab9efa242f45cbae5f8a905c90";//key
			String appId="f8644583bf7049dd9aa6aa469c38c58d";//应用Id
			String to=phone;//收短信号码
			String param = "";//传参数，多个参数用英文下的逗号隔开

			if(templateId.equals("25021")){//低电量提醒
				param=SMStext+","+dl;
			}else if(templateId.equals("25019")){//设备连接数过多
				param=SMStext+","+conectionCount;
			}else if(templateId.equals("25958")){//VPN预警 //系统预警
				param=excStr;
			}
//			else if(templateId.equals("25011")){
//				param=ip;
//			}
			else{
				param=SMStext;
			}

			//param根据模板中的{1}{2}对应来填，多个参数用英文逗号隔开
			try {
				//MD5加密
				EncryptUtil encryptUtil = new EncryptUtil();
				// 构造请求URL内容
				String timestamp = DateUtil.dateToStr(new Date(),
						DateUtil.DATE_TIME_NO_SLASH);// 时间戳
				String signature =AbsRestClient.getSignature(accountSid,authToken,timestamp,encryptUtil);
				String url = AbsRestClient.getStringBuffer().append("/").append(version)
						.append("/Accounts/").append(accountSid)
						.append("/Messages/templateSMS")
						.append("?sig=").append(signature).toString();
				TemplateSMS templateSMS=new TemplateSMS();
				templateSMS.setAppId(appId);
				templateSMS.setTemplateId(templateId);
				templateSMS.setTo(to);
				templateSMS.setParam(param);
				Gson gson = new Gson();
				String body = gson.toJson(templateSMS);
				body="{\"templateSMS\":"+body+"}";
				logger.info(body);
				HttpResponse response=AbsRestClient.post("application/json",accountSid, authToken, timestamp, url, httpclient, encryptUtil, body);
				HttpEntity entity = response.getEntity();
				boolean res=false;
				if (entity != null) {
					result = EntityUtils.toString(entity, "UTF-8");
					if(result.substring(21, 27).equals("000000")){//短信发送是否成功
						//System.out.println("短信通知发送成功！");
						res=  true;
					}else{
						res= false;
					}
				}
				EntityUtils.consume(entity);
				return res;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} finally{
				// 关闭连接
			    httpclient.getConnectionManager().shutdown();
			    System.out.println("云之讯发短信接口操作结束...");
			}
		//云之讯发短信接口结束
	}


	public DefaultHttpClient getDefaultHttpClient(){
		DefaultHttpClient httpclient=null;
		if (isTest) {
			try {
				SSLHttpClient chc = new SSLHttpClient();
				httpclient = chc.registerSSL(sslIP,"TLS",sslPort,"https");
				HttpParams hParams=new BasicHttpParams();
				hParams.setParameter("https.protocols", "SSLv3,SSLv2Hello");
				httpclient.setParams(hParams);
			} catch (KeyManagementException e) {
				// TODO: handle exception
				logger.error(e);
			}catch (NoSuchAlgorithmException e) {
				// TODO: handle exception
				logger.error(e);
			}
		}else {
			httpclient=new DefaultHttpClient();
		}
		return httpclient;
	}


}
