package com.Manage.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.util.LogUtil;

@Service
public class IpPhoneClientSer  extends BaseService{
	
	private Logger logger = LogUtil.getInstance(IpPhoneClientSer.class);

	
	public  int deleteIpClient(String customerID){
		return iPhoneClientDao.deleteIpClient(customerID);
	}
	

}
