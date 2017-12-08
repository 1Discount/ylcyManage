package com.Manage.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.PrepareCardTemp;

@Service
public class PrepareCardTempSer extends BaseService{

	private Logger logger = LogUtil.getInstance(PrepareCardTempSer.class);

	public boolean insertInfo(PrepareCardTemp prepareCardTemp){
		try
		{
			if( prepareCardTempDao.insertInfo(prepareCardTemp)>0){
				return true;
			}
			return false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public List<PrepareCardTemp> getPreparInfoByTime(PrepareCardTemp prepareCardTemp){
		try
		{
			return prepareCardTempDao.getPreparInfoByTime(prepareCardTemp);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}
