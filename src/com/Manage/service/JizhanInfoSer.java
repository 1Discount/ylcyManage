package com.Manage.service;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.util.LogUtil;
import com.Manage.entity.JizhanInfo;

@Service
public class JizhanInfoSer extends BaseService{

	private final Logger logger = LogUtil.getInstance(JizhanInfoSer.class);

	public boolean insert(JizhanInfo jizhanInfo){
		if(jizhanInfoDao.insert(jizhanInfo)>0)
			return true;
		else
			return false;
	}
	
	public boolean insert(String jizhan,String lat,String lon,String adress,String result){
		try {
			JizhanInfo jizhanInfo=new JizhanInfo();
			jizhanInfo.setJizhan(jizhan);
			jizhanInfo.setLat(lat);
			jizhanInfo.setLon(lon);
			jizhanInfo.setAddress(adress);
			jizhanInfo.setResultJson(result);
			if(jizhanInfoDao.insert(jizhanInfo)>0)
				return true;
			else
				return false;
		} catch (Exception e) {
			System.out.println("插入数据失败！数据库中已存在该数据");
			return false;
		}
	}
	
	public JizhanInfo getJizhanInfoByParams(Map<String,Object> params){
		String jizhan=params.get("mcc")+"."+params.get("mnc")+"."+params.get("lac")+"."+params.get("cellId");
		params.put("jizhan", jizhan);
		return jizhanInfoDao.getJizhanInfoByParams(params);
	}
	
}
