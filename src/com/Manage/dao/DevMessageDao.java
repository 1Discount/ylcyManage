package com.Manage.dao;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.DateUtils;
import com.Manage.entity.DevMessage;
import com.Manage.entity.OrdersInfo;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

@Repository
public class DevMessageDao extends BaseDao
{

	private static final String NAMESPACE = DevMessageDao.class.getName() + ".";
	/**
	 * 分页查询
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpageString(SearchDTO searchDTO)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "queryPage", "getcount", searchDTO);
			List<DevMessage> arr = (List<DevMessage>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (DevMessage a : arr)
			{
				String myDate = a.getCreatorDate().substring(0,a.getCreatorDate().length()-2);
				a.setCreatorDate(myDate);
				
				JSONObject obj = JSONObject.fromObject(a);
				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 根据ID删除DevMessage
	 * @param devMessage
	 * @return
	 */
	public int deldevmessage(DevMessage devMessage){
		try
		{
			return getSqlSession().update(NAMESPACE+"updateAll",devMessage);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
	
	/**
	 * excel导出
	 * @param devMessage
	 * @return
	 */
	public List<DevMessage> exprotexcel(DevMessage devMessage){
		try
		{
			return getSqlSession().selectList(NAMESPACE+"exportexcel",devMessage);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
}
