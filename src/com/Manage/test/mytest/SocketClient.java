package com.Manage.test.mytest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

import com.Manage.common.constants.Constants;
import com.Manage.dao.DeviceInfoDao;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.common.ApplicationContext2;

import net.sf.json.JSONObject;



/** * @author  wangbo: * @date 创建时间：2015-6-25 下午2:35:03 * @version 1.0 * @parameter  * @since  * @return  */
public class SocketClient {

	static String ip="localhost";
	//static String ip="test.easy2go.cn";//58.250.57.153
	static int port=8070;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			client();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static  void client() throws UnknownHostException, IOException{
		Socket socket = new Socket(ip,port);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream());
		//"roam_xmlLientVersion":""|"roam_apkVersion":""|"roam_imei":""|"roam_sn":""|"roam_iccid":""|"roam_simStatus":""|"roam_calibrationFlag":""|"roam_netType":""|"roam_netStrength":""|"roam_dataLinkStatus":""|"roam_xmclient_step":""|"local_clientVersion":""|"local_apkVersion":""|"local_calibrationFlag":""|"local_sn":""|"local_imsi":""|"local_netType":""|"local_battery":""|"local_iccid":""|"local_simStatus":""|"local_netStrength":""|"local_dataLinkStatus":""|"local_WifiPwd":""|"local_imei":""
		String temp = "";

		out.print("{'type':50,'sn':'860172008141059',  'num':'1604061424|1604290000|860172008141059|860172008141059|89314404000224389487|正在使用中|[gsm.serial]:[01020110]|HSPA|97|1|1|1605041005|1603180000|[gsm.serial]: [04060110]|860172008141059|809460060049030311|0|62|'}");
		out.flush();
		socket.close();
		try {
			Thread.sleep(80);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	//发送远程关机命令
	public static void socketSend(String content) throws NumberFormatException, UnknownHostException, IOException{
		Socket socket = new Socket(Constants.getConfig("POS.ip"),Integer.parseInt(Constants.getConfig("POS.port")));
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream());
		out.print(content);
		out.flush();
	}

	public static int remoteShutdown(String SN){
		JSONObject object=new JSONObject();
		if((object=addfd(object,SN))==null){
			return -1;
		}
		object.put("type",14);
		object.put("sn",SN);
		try {
			SocketClient.socketSend(object.toString());
			//FileWrite.printlog("远程关机发送命令成功:"+object.toString());
			//logger.info("远程关机发送命令成功:"+object.toString());
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
		return 1;
	}

	/**
	 * 根据SN查询socket是否断开
	 * @param object
	 * @param sn
	 * @return
	 */
	public static  JSONObject addfd(JSONObject object,String sn){
		DeviceInfoDao deviceInfoDao =ApplicationContext2.getBean(DeviceInfoDao.class);
		DeviceInfo diDeviceInfo=new DeviceInfo();
		diDeviceInfo.setSN(sn);
		DeviceInfo di= deviceInfoDao.searchfdInfo(diDeviceInfo);
		if(di==null) return null;
		if(di.getFd1()==0 && di.getFd2()==0){
			return null;
		}
		object.put("fd1",di.getFd1());
		object.put("fd2",di.getFd2());
		return object;
	}


}
