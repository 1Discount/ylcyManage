package com.Manage.service;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.Manage.common.util.LogUtil;
import com.Manage.dao.DeviceFlowDao;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.DeviceFlow;
import com.Manage.entity.common.SearchDTO;

@Service
public class DeviceFlowSer extends BaseService
{

	private Logger logger = LogUtil.getInstance(DeviceFlowSer.class);



	/**
	 * 新增数据
	 * 
	 * @param deviceFlow
	 * @return
	 */
	public int insertInfo(DeviceFlow deviceFlow)
	{
		try
		{
			return deviceFlowDao.insertInfo(deviceFlow);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}



	/**
	 * 修改数据
	 * 
	 * @param deviceFlow
	 * @return
	 */
	public int updateInfo(DeviceFlow deviceFlow)
	{
		try
		{
			return deviceFlowDao.updateInfo(deviceFlow);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}



	public List<DeviceFlow> getDeviceInBySnAndDate(DeviceFlow deviceFlow)
	{
		logger.info("查询设备日接入情况和流量开始");
		try
		{
			return deviceFlowDao.getDeviceInBySnAndDate(deviceFlow);
		}
		catch (Exception e)
		{
			logger.info("查询失败");
			e.printStackTrace();
		}
		return null;
	}
	
	public List<DeviceFlow> getDeviceInByBjtime(DeviceFlow deviceFlow)
	{
		logger.info("查询设备日接入情况和流量开始");
		try
		{
			return deviceFlowDao.getDeviceInByBjtime(deviceFlow);
		}
		catch (Exception e)
		{
			logger.info("查询失败");
			e.printStackTrace();
		}
		return null;
	}
	

	public List<DeviceFlow> getDeviceInBySnAndDateASC(DeviceFlow deviceFlow)
	{
		logger.info("查询设备日接入情况和流量开始");
		try
		{
			return deviceFlowDao.getDeviceInBySnAndDateASC(deviceFlow);
		}
		catch (Exception e)
		{
			logger.info("查询失败");
			e.printStackTrace();
		}
		return null;
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
			return deviceFlowDao.getUserDayGroupMCC(deviceFlow);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public String deviceflowstatisticsPage(SearchDTO searchDTO)
	{
		List<CountryInfo> countries = countryInfoDao.getAll("");
		HashMap<String, String> mccNameMap = new HashMap<String, String>();
		for (CountryInfo item : countries)
		{
			// ahming marks: ! 注意, 目前 CountryInfo MCC 类型为 int , 而在 SIMInfo
			// 中为 String
			mccNameMap.put(String.valueOf(item.getCountryCode()), item.getCountryName());
		}
		return deviceFlowDao.deviceflowstatisticsPage(searchDTO,mccNameMap);
	}
	
	public String deviceflowstatisticsPage1(SearchDTO searchDTO)
	{
		List<CountryInfo> countries = countryInfoDao.getAll("");
		HashMap<String, String> mccNameMap = new HashMap<String, String>();
		for (CountryInfo item : countries)
		{
			mccNameMap.put(String.valueOf(item.getCountryCode()), item.getCountryName());
		}
		return deviceFlowDao.deviceflowstatisticsPage1(searchDTO,mccNameMap);
	}
	
	public List<DeviceFlow> getDeviceflowstatisticsExport(DeviceFlow deviceFlow)
	{
		List<CountryInfo> countries = countryInfoDao.getAll("");
		HashMap<String, String> mccNameMap = new HashMap<String, String>();
		for (CountryInfo item : countries)
		{
			mccNameMap.put(String.valueOf(item.getCountryCode()), item.getCountryName());
		}
		return deviceFlowDao.getDeviceflowstatisticsExport(deviceFlow,mccNameMap);
	}
	
	public List<DeviceFlow> getDeviceflowstatisticsExport1(DeviceFlow deviceFlow)
	{
		List<CountryInfo> countries = countryInfoDao.getAll("");
		HashMap<String, String> mccNameMap = new HashMap<String, String>();
		for (CountryInfo item : countries)
		{
			mccNameMap.put(String.valueOf(item.getCountryCode()), item.getCountryName());
		}
		return deviceFlowDao.getDeviceflowstatisticsExport1(deviceFlow,mccNameMap);
	}
}
