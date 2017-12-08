package com.Manage.dao;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.CountryUtils;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.AcceptOrder;
import com.Manage.entity.UpgradeFileInfo;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

@SuppressWarnings("rawtypes")
@Repository
public class UpgradeFileInfoDao extends BaseDao
{

	private static final String NAMESPACE = UpgradeFileInfoDao.class.getName() + ".";

	/**
	 * 新增
	 *
	 * @param acceptOrder
	 * @return
	 */
	public int insert(UpgradeFileInfo upgradeFileInfo)
	{
		try
		{

			return getSqlSession().insert(NAMESPACE + "add", upgradeFileInfo);

		}
		catch (Exception e)
		{

			throw new BmException(Constants.common_errors_1001, e);
		}
	}
	
	/**
	 * 根据文件名称查询最新的文件记录
	 */
	public UpgradeFileInfo selectOne(Map<String,Object> params){
	    try
		{

			return getSqlSession().selectOne(NAMESPACE+"selectOne", params);

		}
		catch (Exception e)
		{

			throw new BmException(Constants.common_errors_1001, e);
		}
	}
}
