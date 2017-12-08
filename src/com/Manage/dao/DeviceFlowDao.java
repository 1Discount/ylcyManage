package com.Manage.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.DeviceFlow;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.DeviceLogs;
import com.Manage.entity.OrdersInfo;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

@Repository
public class DeviceFlowDao extends BaseDao
{
	private static final String NAMESPACE = DeviceFlowDao.class.getName() + ".";



	/**
	 * 总订单表分页查询
	 * 
	 * @param searchDTO
	 * @return
	 */
	/*public String getpageString(SearchDTO searchDTO)
	{

		try
		{
			Page page = queryPage(NAMESPACE, "queryPage", "getcount", searchDTO);
			List<DeviceFlow> arr = (List<DeviceFlow>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (DeviceFlow a : arr)
			{
				JSONObject obj = JSONObject.fromObject(a);
				Date date = a.getCreatorDate();
				String dString = DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss");
				obj.remove("creatorDate");
				obj.put("creatorDate", dString);

				Date mdate = a.getModifyDate();
				if (mdate != null)
				{
					String mString = DateUtils.formatDate(mdate, "yyyy-MM-dd HH:mm:ss");
					obj.remove("modifyDate");
					obj.put("modifyDate", mString);
				}

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
	}*/



	/**
	 * 新增数据 全部字段
	 * 
	 * @param deviceFlow
	 * @return
	 */
	public int insertInfo(DeviceFlow deviceFlow)
	{
		try
		{
			return getSqlSession().insert(NAMESPACE + "insertInfo", deviceFlow);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1001, e);
		}
	}



	/**
	 * 修改数据 全部字段
	 * 
	 * @param deviceFlow
	 * @return
	 */
	public int updateInfo(DeviceFlow deviceFlow)
	{
		try
		{
			return getSqlSession().update(NAMESPACE + "updateInfo", deviceFlow);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	/**
	 * 根据时间段查询订单使用详情（设备流量报表查询）desc
	 * 
	 * @param deviceFlow
	 * @return
	 */
	public List<DeviceFlow> getDeviceInBySnAndDate(DeviceFlow deviceFlow)
	{
		try
		{
			return getSqlSession().selectList(NAMESPACE + "getDeviceInBySnAndDate", deviceFlow);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	
	public List<DeviceFlow> getDeviceInByBjtime(DeviceFlow deviceFlow)
	{
		try
		{
			return getSqlSession().selectList(NAMESPACE + "getDeviceInByBjtime", deviceFlow);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	/**
	 * 根据时间段查询订单使用详情（设备流量报表查询）asc
	 * 
	 * @param deviceFlow
	 * @return
	 */
	public List<DeviceFlow> getDeviceInBySnAndDateASC(DeviceFlow deviceFlow)
	{
		try
		{
			return getSqlSession().selectList(NAMESPACE + "getDeviceInBySnAndDateASC", deviceFlow);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	
	/**
	 * 查询订单使用天数
	 * 
	 * @param deviceFlow
	 * @return
	 */
	public List<DeviceFlow> getUserDayGroupMCC(DeviceFlow deviceFlow)
	{
		try
		{
			return getSqlSession().selectList(NAMESPACE + "getUserDayGroupMCC", deviceFlow);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 分页查询返回json
	 *
	 * @param searchDTO
	 * @return
	 */
	public String deviceflowstatisticsPage(SearchDTO searchDTO, HashMap<String, String> mccNameMap)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "deviceflowstatisticsPage", "deviceflowstatisticsCount", searchDTO);
			List<DeviceFlow> arr = (List<DeviceFlow>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (DeviceFlow a : arr)
			{
				DeviceFlow select = a;
				select.setFlowCount(((DeviceFlow)searchDTO.getObj()).getFlowCount());
				DeviceFlow d = getSqlSession().selectOne("getMiddleFlow", select);
				
				a.setMiddleFlow(d==null?a.getAvgFlow():d.getMiddleFlow());
				String cnameString="";
				
				JSONObject obj = JSONObject.fromObject(a);
				cnameString+=mccNameMap.get(a.getMCC());
				obj.put("countryName", cnameString);
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
	 * 分页查询返回json
	 *
	 * @param searchDTO
	 * @return
	 */
	public String deviceflowstatisticsPage1(SearchDTO searchDTO, HashMap<String, String> mccNameMap)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "deviceflowstatisticsPage1", "deviceflowstatisticsCount1", searchDTO);
			List<DeviceFlow> arr = (List<DeviceFlow>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (DeviceFlow a : arr)
			{
				//查询中位数
				/*DeviceFlow d = getSqlSession().selectOne("getMiddleFlow1", a);
				a.setMiddleFlow(d.getMiddleFlow());*/
				
				JSONObject obj = JSONObject.fromObject(a);
				String cnameString="";
				cnameString+=mccNameMap.get(a.getMCC());
				obj.put("countryName", cnameString);
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
	 * 查询需要导出的设备流量报表数据
	 * @param deviceFlow
	 * @return
	 */
	public List<DeviceFlow> getDeviceflowstatisticsExport(DeviceFlow deviceFlow, HashMap<String, String> mccNameMap)
	{
		try
		{
			List<DeviceFlow> list = getSqlSession().selectList(NAMESPACE + "getDeviceflowstatisticsExport", deviceFlow);
			
			for (DeviceFlow deviceFlow2 : list) 
			{
				deviceFlow2.setCountryName(mccNameMap.get(deviceFlow2.getMCC()));
				//查询中位数
				DeviceFlow d = getSqlSession().selectOne("getMiddleFlow", deviceFlow2);
				deviceFlow2.setMiddleFlow(d.getMiddleFlow());
			}
			
			return list;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 查询需要导出的设备流量报表数据
	 * @param deviceFlow
	 * @return
	 */
	public List<DeviceFlow> getDeviceflowstatisticsExport1(DeviceFlow deviceFlow, HashMap<String, String> mccNameMap)
	{
		try
		{
			List<DeviceFlow> list = getSqlSession().selectList(NAMESPACE + "getDeviceflowstatisticsExport1", deviceFlow);
			
			for (DeviceFlow deviceFlow2 : list) 
			{
				deviceFlow2.setCountryName(mccNameMap.get(deviceFlow2.getMCC()));
			}
			
			return list;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
}
