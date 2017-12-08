package com.Manage.dao;

import org.springframework.stereotype.Repository;

@Repository
public class IpPhoneClientDao extends BaseDao{

	private static final String NAMESPACE = IpPhoneClientDao.class.getName() + ".";

	
	public int deleteIpClient(String id){
		return getSqlSession().update(NAMESPACE+"deleIpClient",id);
	}
	
	
}
