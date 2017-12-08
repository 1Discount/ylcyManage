package com.Manage.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.JizhanInfo;

@Repository
public class JizhanInfoDao extends BaseDao
{

	private static final String NAMESPACE = JizhanInfoDao.class.getName() + ".";


	/**
	 * 新增
	 *
	 * @param acceptOrder
	 * @return
	 */
	public int insert(JizhanInfo jizhanInfo)
	{
		try
		{
			return getSqlSession().insert(NAMESPACE + "insertSelective", jizhanInfo);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1001, e);
		}
	}
	
	public JizhanInfo getJizhanInfoByParams(Map<String,Object> params){
		return	getSqlSession().selectOne(NAMESPACE + "selectJizhanInfoByParams", params);
	}
}
