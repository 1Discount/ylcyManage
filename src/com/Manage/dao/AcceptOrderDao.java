package com.Manage.dao;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.CountryUtils;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.AcceptOrder;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

@SuppressWarnings("rawtypes")
@Repository
public class AcceptOrderDao extends BaseDao
{

	private static final String NAMESPACE = AcceptOrderDao.class.getName() + ".";



	@SuppressWarnings("unchecked")
	public String getPage(SearchDTO searchDTO)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "queryPage", "getcount", searchDTO);

			List<AcceptOrder> arr = (List<AcceptOrder>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (AcceptOrder a : arr)
			{
				a.setStartTime(a.getStartTime().substring(0, a.getStartTime().length() - 2));
				if (StringUtils.isNotBlank(a.getBelowOrderDate()))
				{
					a.setBelowOrderDate(a.getBelowOrderDate().substring(0, a.getBelowOrderDate().length() - 2));
				}

				if (StringUtils.isNotBlank(a.getCreatorDate()))
				{
					a.setCreatorDate(a.getCreatorDate().substring(0, a.getCreatorDate().length() - 2));
				}


				JSONObject obj = JSONObject.fromObject(a);
				CountryUtils.CountryListWrapper wrapper = new CountryUtils.CountryListWrapper(a.getCountryList());

				obj.put("countryName", wrapper.getCountryNameStrings());
				obj.put("address", a.getCustomerAddress());

				ja.add(obj);
			}

			object.put("data", ja);
			return object.toString();  
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	/**
	 * 新增
	 *
	 * @param acceptOrder
	 * @return
	 */
	public int insert(AcceptOrder acceptOrder)
	{
		try
		{

			return getSqlSession().insert(NAMESPACE + "insertSelective", acceptOrder);

		}
		catch (Exception e)
		{

			throw new BmException(Constants.common_errors_1001, e);
		}
	}



	/**
	 * 根据ID查询接单信息
	 *
	 * @param aoID
	 * @return
	 */
	public AcceptOrder getById(String aoID)
	{

		try
		{

			return getSqlSession().selectOne(NAMESPACE + "selectByPrimaryKey", aoID);

		}
		catch (Exception e)
		{

			throw new BmException(Constants.common_errors_1001, e);
		}

	}



	/**
	 * 根据ID修改接单信息
	 *
	 * @param acceptOrder
	 * @return
	 */
	public int update(AcceptOrder acceptOrder)
	{
		try
		{

			return getSqlSession().update(NAMESPACE + "updateByPrimaryKeySelective", acceptOrder);

		}
		catch (Exception e)
		{

			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	public int deleteById(String aoID)
	{
		try
		{

			return getSqlSession().update(NAMESPACE + "deleteById", aoID);

		}
		catch (Exception e)
		{

			throw new BmException(Constants.common_errors_1002, e);
		}
	}



	/**
	 * 修改接单状态
	 *
	 * @param acceptOrder
	 * @return
	 */
	public int updateStatus(AcceptOrder acceptOrder)
	{
		try
		{

			return getSqlSession().update(NAMESPACE + "updateStatus", acceptOrder);

		}
		catch (Exception e)
		{

			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	public List<AcceptOrder> exprotexcel(AcceptOrder acceptOrder)
	{
		try
		{
			return getSqlSession().selectList(NAMESPACE + "exprotexcel", acceptOrder);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	public int updateAccepOrderBySn(String aco){
		try {
			return getSqlSession().update(NAMESPACE + "updateAccepOrderBySn",aco);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
}
