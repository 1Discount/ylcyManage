package com.Manage.service.SocketClient;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.util.FileWrite;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.DeviceInfo;
import com.Manage.service.BaseService;

@Service
public class CommandSer extends BaseService {

	private Logger logger = LogUtil.getInstance(CommandSer.class);

	/**
	 * 限速命令
	 * @param limitValve
	 * @param limitSpeed
	 * @return
	 */
	public int limitspeed(String limitValve,String limitSpeed,String sn){
		JSONObject object=new JSONObject();
		if((object=addfd(object,sn))==null){
			return -1;
		}
		object.put("type",10);
		object.put("sn",sn);
		object.put("num",limitValve+","+limitSpeed);
		String ipandport=object.getString("ipandport");
		String iparr[]=ipandport.split(":");
		object.remove("ipandport");
		try {
			return send(iparr[0],iparr[1],object.toString(),"远程服务限速发送命令");
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}
	/**
	 * 远程查询
	 * @param mes
	 * @param sn
	 * @return
	 */
	public int remoteQuery(String mes,String sn){
		JSONObject object=new JSONObject();
		if((object=addfd(object,sn))==null){
			return -1;
		}
		object.put("type",8);
		object.put("sn",sn);
		object.put("num",mes);
		String ipandport=object.getString("ipandport");
		String iparr[]=ipandport.split(":");
		object.remove("ipandport");
		try {
			return send(iparr[0],iparr[1],object.toString(),"远程查询发送命令");
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}

	/**
	 * 修改设备APN
	 * @param apn
	 * @param sn
	 * @return
	 */
	public int modificationAPN(String apn,String sn){
		JSONObject object=new JSONObject();
		if((object=addfd(object,sn))==null){
			return -1;
		}
		object.put("type",11);
		object.put("sn",sn);
		object.put("num",apn);
		String ipandport=object.getString("ipandport");
		String iparr[]=ipandport.split(":");
		object.remove("ipandport");
		try {
			return send(iparr[0],iparr[1],object.toString(),"远程修改APN发送命令");
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}

	/**
	 * 远程获取日志
	 * @param bm 0：本地 1：漫游
	 * @param num
	 * @param sn
	 * @return
	 */
	public int getDevLogs(int bm,String num,String sn){
		JSONObject object=new JSONObject();
		if((object=addfd(object,sn))==null){
			return -1;
		}
		//本地
		if(bm==0){
			object.put("type",2);

		//漫游
		}else if(bm==1){
			object.put("type",1);
		}
		object.put("sn",sn);
		object.put("num",Integer.parseInt(num));
		String ipandport=object.getString("ipandport");
		String iparr[]=ipandport.split(":");
		object.remove("ipandport");
		try {
			if(bm==0){
				return send(iparr[0],iparr[1],object.toString(),"远程获取本地日志发送命令");
			}else{
				return send(iparr[0],iparr[1],object.toString(),"远程获取漫游日志发送命令");
			}
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}


	}

	/**
	 * 远程升级
	 * @param type
	 * @param sn
	 * @return
	 */
	public int remoteUpgrade(String type,String sn){
		JSONObject object=new JSONObject();
		if((object=addfd(object,sn))==null){
			return -1;
		}
		object.put("type",Integer.parseInt(type));
		object.put("sn",sn);
		String ipandport=object.getString("ipandport");
		String iparr[]=ipandport.split(":");
		object.remove("ipandport");
		try {
			return send(iparr[0],iparr[1],object.toString(),"远程升级发送命令");
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}
	/**
	 * 重插卡
	 * @param SN
	 * @return
	 */
	public int rePlugSIM(String SN){
		JSONObject object=new JSONObject();
		if((object=addfd(object,SN))==null){
			return -1;
		}
		object.put("type",13);
		object.put("sn",SN);
		String ipandport=object.getString("ipandport");
		String iparr[]=ipandport.split(":");
		object.remove("ipandport");
		try {
			return send(iparr[0],iparr[1],object.toString(),"远程重插卡发送命令");
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}

	/**
	 * 远程修改VPN
	 * @param SN
	 * @return
	 */
	public int modifyVPN(String SN,String vpn){
		JSONObject object=new JSONObject();
		if((object=addfd(object,SN))==null){
			return -1;
		}
		object.put("type",15);
		object.put("sn",SN);
		object.put("num",vpn);
		String ipandport=object.getString("ipandport");
		String iparr[]=ipandport.split(":");
		object.remove("ipandport");
		try {
			return send(iparr[0],iparr[1],object.toString(),"修改VPN发送命令");
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}

	public int remoteShutdown(String SN){
		JSONObject object=new JSONObject();
		if((object=addfd(object,SN))==null){
			return -1;
		}
		object.put("type",14);
		object.put("sn",SN);
		String ipandport=object.getString("ipandport");
		String iparr[]=ipandport.split(":");
		object.remove("ipandport");
		try {
			return send(iparr[0],iparr[1],object.toString(),"远程关机发送命令");
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}

	/**
	 * 换卡命令
	 * @param SN
	 * @return
	 */
	public int changeCard(String SN){
		JSONObject object=new JSONObject();
		if((object=addfd(object,SN))==null){
			return -1;
		}
		object.put("type",9);
		object.put("sn",SN);
		String ipandport=object.getString("ipandport");
		String iparr[]=ipandport.split(":");
		object.remove("ipandport");
		try {
			return send(iparr[0],iparr[1],object.toString(),"远程换卡发送命令");
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}


	public int restPwd(String SN){
		JSONObject object=new JSONObject();
		if((object=addfd(object,SN))==null){
			return -1;
		}
		object.put("type",16);
		object.put("sn",SN);
		String ipandport=object.getString("ipandport");
		String iparr[]=ipandport.split(":");
		object.remove("ipandport");
		try {
			return send(iparr[0],iparr[1],object.toString(),"远程重置密码命令");
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}

	/**
	 * 根据SN查询socket是否断开
	 * @param object
	 * @param sn
	 * @return
	 */
	public JSONObject addfd(JSONObject object,String sn){
		DeviceInfo diDeviceInfo=new DeviceInfo();
		diDeviceInfo.setSN(sn);
		DeviceInfo di= deviceInfoDao.searchfdInfo(diDeviceInfo);
		if(di==null) return null;
		//zwh 修改过  去 fd1 fd2的值 由客户端判断
//		if(di.getFd1()==0 && di.getFd2()==0){
//			return null;
//		}
//		object.put("fd1",di.getFd1());
//		object.put("fd2",di.getFd2());
		object.put("fd1",0);
		object.put("fd2",0);
		object.put("ipandport",di.getServiceIP());
		return object;
	}
	
	
	/**
	 * 发送命令
	 */
	public int send(String ip,String port,String msg,String logsType){
		int res=1;
		String resStatus="成功";
		if(!SocketClient.send(SocketClient.getSocket(ip,port),msg)){
			res=0;
			resStatus="失败";
		}
		FileWrite.printlog(logsType+resStatus+":"+msg);
		logger.info(logsType+resStatus+":"+msg);
		return res;
	}
	
	
	
}
