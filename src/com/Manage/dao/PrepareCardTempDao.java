package com.Manage.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.PrepareCardTemp;

@Repository
public class PrepareCardTempDao extends BaseDao
{

	private static final String NAMESPACE = PrepareCardTempDao.class.getName() + ".";
	
	public int insertInfo(PrepareCardTemp prepareCardTemp){
		try
		{
			return getSqlSession().insert("com.Manage.dao.PrepareCardTempDao.insertInfo",prepareCardTemp);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1001, e);
		}
	}
	
	public List<PrepareCardTemp> getPreparInfoByTime(PrepareCardTemp prepareCardTemp){
		try
		{
			return getSqlSession().selectList("com.Manage.dao.PrepareCardTempDao.getPreparInfoByTime",prepareCardTemp);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
}
