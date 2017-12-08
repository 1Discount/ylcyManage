package com.Manage.common.util;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Result;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;

import com.Manage.common.constants.Constants;




/**
 * 国家地区常用工具类 java 端实现
 *
 * @author tangming 20150525
 */
public class JiguangPushUtils {
	private static Logger logger = LogUtil.getInstance(JiguangPushUtils.class);
	
	/*public static void main( String[] arg ){
		sendPush_fromJSON("android","144384,140583",  "我是标题", "推送内容：简单文本消息","",null,null);
	}*/

    //use String to build PushPayload instance
    /**
     * 通过构建json来推送消息
     * @param deviceTypes  推送平台 ：可选值：all 或者  "android", "ios", "winphone" 多个平台用","分割
     * @param title  消息标题
     * @param content  消息内容
     * @param tagAllAndOr 用标签来进行大规模的设备属性、用户属性分群。 一次推送最多 20 个。可选值："all":全部；"and":"取tag的交集"；"or":"取tag的并集"; "":不设置tag属性
     * @param tag 数组 标签
     * @param alias 数组 设备别名
     * @param time_to_live 离线消息保留时长(秒)，推送当前用户不在线时，为该用户保留多长时间的离线消息，以便其上线时再次推送。默认 86400 （1 天），最长 10 天。设置为 0 表示不保留离线消息，只有推送当前在线的用户可以收到。
     * @param big_push_duration 定速推送时长(分钟)	又名缓慢推送，把原本尽可能快的推送速度，降低下来，给定的n分钟内，均匀地向这次推送的目标用户推送。最大值为1400.未设置则不是定速推送。
     */
    public static Map<String,Object> sendPush_fromJSON(String deviceTypes,String alias,String title,String content,String tagAllAndOr,String[] tag,Map extras) {
    	//返回结果
    	Map<String,Object> resultMap=new HashMap<String,Object>();
    	resultMap.put("error",0);
    	resultMap.put("msg","错误");
    	//校验参数
    	if(StringUtils.isBlank(deviceTypes) || StringUtils.isBlank(alias) || StringUtils.isBlank(content)){
    		resultMap.put("error",-1);
        	resultMap.put("msg","必要参数错误");
        	return resultMap;
    	}
        //推送主体
    	ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(Constants.JIGUANG_MASTERSECRET, Constants.JIGUANG_APPKEY, null, clientConfig);
        try {
        	//推送结果,如有错误，请参考极光推送官网  网址：http://docs.jiguang.cn/jpush/server/push/rest_api_v3_push/#_1  ； 目录： 极光文档> REST API > Push API v3
        	String[] aliasSZ=null; 
        	if(alias.indexOf("，")>-1){
        		alias=alias.replaceAll("，",",");
        	}
        	if(alias.indexOf(",")>-1){
        		aliasSZ=alias.split(",");
        	}else{
        		aliasSZ=new String[]{alias};
        	}
        	PushResult result=null;
        	if(deviceTypes.equals("all") && tagAllAndOr.equals("all")){
        		result =jpushClient.sendNotificationAll(content);
        	}else{
        		if(deviceTypes.equals("all")){
        			result=jpushClient.sendAndroidNotificationWithAlias(title, content, extras, aliasSZ);
        			result=jpushClient.sendIosNotificationWithAlias(content, extras, alias);
        		}
        		if(deviceTypes.equals("android")){
        			result=jpushClient.sendAndroidNotificationWithAlias(title, content, extras, aliasSZ);
        		}
        		if(deviceTypes.equals("ios")){
        			result=jpushClient.sendIosNotificationWithAlias(content, extras, aliasSZ);
        		}
        	}
        	logger.info("Got result - " + result);
        	System.out.println(result);
        	resultMap.put("error",1);
        	resultMap.put("msg","推送成功！");
        	resultMap.put("msgId",result.msg_id);
        	resultMap.put("sendno",result.sendno);
        	resultMap.put("pushResult",result);
        } catch (APIConnectionException e) {
        	resultMap.put("error",-2);
        	resultMap.put("msg","推送失败！");
        	resultMap.put("pushResult",e);
        	logger.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
        	resultMap.put("error",-3);
        	resultMap.put("msg","推送失败！详细错误请看日志");
        	resultMap.put("pushResult",e);
        	logger.error("Error response from JPush server. Should review and fix it. ", e);
        	logger.info("HTTP Status: " + e.getStatus());
        	logger.info("Error Code: " + e.getErrorCode());
        	logger.info("Error Message: " + e.getErrorMessage());
        	logger.info("Msg ID: " + e.getMsgId());
        }
        return resultMap;
    }
}