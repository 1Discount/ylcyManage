package com.Manage.dao;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.SIMInfoCountryTemp;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

@Repository
public class SIMInfoCountryTempDao extends BaseDao{

	private static final String NAMESPACE = SIMInfoCountryTempDao.class.getName() + ".";
 
	public List<SIMInfoCountryTemp> beika(SIMInfoCountryTemp s){
	    try{	
	    	return getSqlSession().selectList(NAMESPACE+"beika",s);
	    } catch (Exception e) {
		e.printStackTrace();
		throw new BmException(Constants.common_errors_1004, e);
	    }
	}
	
	public List<SIMInfoCountryTemp> QDbeika(SIMInfoCountryTemp s){
	    try{	
	    	return getSqlSession().selectList(NAMESPACE+"QDbeika",s);
	    } catch (Exception e) {
		e.printStackTrace();
		throw new BmException(Constants.common_errors_1004, e);
	    }
	}

}
