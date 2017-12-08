package com.Manage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.exception.BmException;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.DeviceDealOrders;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.DeviceUpgrading;
import com.Manage.entity.FactoryTestInfo;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.SIMInfo;
import com.Manage.entity.common.SearchDTO;

/**
 * @author lipeng
 */
@Service
public class DeviceInfoSer extends BaseService
{

	private final Logger logger = LogUtil.getInstance(DeviceInfoSer.class);



	/**
	 * 查询所有客户信息
	 *
	 * @param page
	 * @param pageCount
	 * @return
	 */
	// public List<CustomerInfo> getAllCustomerInfo(Integer page, Integer
	// pageCount)
	// {
	// return customerInfoDao.getAllCustomerInfo(page,pageCount);
	// }

	/**
	 * 插入设备信息
	 *
	 * @param cus
	 * @return
	 */
	public int insertDeviceInfo(DeviceInfo device)
	{
		return deviceInfoDao.insertDeviceInfo(device);
	}



	public int insertDeviceInfotwo(DeviceInfo device)
	{
		return deviceInfoDao.insertDeviceInfotwo(device);
	}



	/**
	 * 获取需修改的设备信息
	 *
	 * @param device
	 * @return
	 */
	public DeviceInfo getdeviceinfoEdit(String device)
	{
		return deviceInfoDao.getdeviceinfoEdit(device);
	}



	public int getdeviceinfoDelete(String device)
	{
		return deviceInfoDao.getdeviceinfoDelete(device);
	}



	// 根据SN获取单个的设备信息
	public DeviceInfo getdeviceInfodetail(String device)
	{
		try
		{
			return deviceInfoDao.getdeviceInfodetail(device);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}



	public int getdevice(String device)
	{
		return deviceInfoDao.getdevice(device);
	}



	public DeviceInfo getdevicetwo(String device)
	{
		try
		{
			return deviceInfoDao.getdevicetwo(device);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * 获取单个客户信息详情
	 *
	 * @param uid
	 * @return
	 */
	// public CustomerInfo getOneCustomerDetail(String uid){
	// return customerInfoDao.getOneCustomerDetail(uid);
	// }

	// public List<CustomerInfo> getSearchCustomerInfoList(CustomerInfo cus){
	// return customerInfoDao.getSearchCustomerInfoList(cus);
	// }
	public int updatedeviceToEnd(DeviceInfo device)
	{
		return deviceInfoDao.updatedeviceToEnd(device);
	}



	/**
	 * 返还设备信息（修改设备为可用）
	 *
	 * @param cus
	 * @return
	 */
	public int updateDeviceInfo(DeviceInfo device)
	{
		return deviceInfoDao.updateDevice(device);
	}



	/**
	 * 修改设备订单表状态
	 *
	 * @param snid
	 * @return
	 */
	public int updateDeviceDealOrders(DeviceDealOrders snid)
	{
		return deviceInfoDao.updateDeviceDealOrders(snid);
	}



	public String searchDeviceInfoTypeforSn(String sn)
	{
		return deviceInfoDao.searchDeviceInfoTypeforSnDao(sn);
	}



	/**
	 * 查看此设备是否还未归还状态
	 *
	 * @param dea
	 * @return
	 */
	public int searchDevice(DeviceDealOrders dea)
	{
		return deviceInfoDao.searchDevice(dea);
	}



	public int searchDevice2(DeviceDealOrders dea)
	{
		return deviceInfoDao.searchDevice2(dea);
	}



	/**
	 * 设备统计
	 *
	 * @return
	 */
	public DeviceInfo DeviceInfoCount()
	{
		return deviceInfoDao.getDeviceInfoCount();
	}



	/**
	 * 根据ID删除客户信息
	 *
	 * @param cus
	 * @return
	 */
	// public int deleteCustomerInfo(String cus){
	// return customerInfoDao.deleteCus(cus);
	// }

	/**
	 * 分页，排序，条件查询.
	 *
	 * @param searchDTO
	 * @return
	 */
	public String getpageString(SearchDTO searchDTO)
	{
		logger.debug("分页server开始");
		try
		{
			String jsonString = deviceInfoDao.getpage(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}



	public String getpageString1(SearchDTO searchDTO)
	{
		logger.debug("分页server开始");
		try
		{
			String jsonString = deviceInfoDao.getpageString(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}



	// public String getpageStringCount(SearchDTO searchDTO){
	// logger.debug("分页server开始");
	// try {
	// String jsonString=deviceInfoDao.getpageCount(searchDTO);
	// logger.debug("分页查询结果:"+jsonString);
	// return jsonString;
	// } catch (Exception e) {
	// logger.debug(e.getMessage());
	// // TODO: handle exception
	// e.printStackTrace();
	// return "";
	// }
	// }

	public String getpageStringDelete(SearchDTO searchDTO)
	{
		logger.debug("分页server开始");
		try
		{
			String jsonString = deviceInfoDao.getpagedelete(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}



	/**
	 * 分页查询设备信息
	 *
	 * @param searchDTO
	 * @return
	 */
	public String getdevpage(SearchDTO searchDTO)
	{
		logger.debug("分页server开始");
		try
		{
			String jsonString = deviceInfoDao.getdevpage(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}



	/**
	 * 根据SN查询设备信息
	 *
	 * @param sn
	 * @return
	 */
	public DeviceInfo getbysn(String sn)
	{
		logger.debug("根据sn查询设备server开始");
		try
		{
			return deviceInfoDao.getbysn(sn);
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}



	public List<DeviceInfo> getdeviceCountsTime(DeviceInfo device)
	{
		return deviceInfoDao.getCountsforTime(device);
	}



	/**
	 * //设备状态 下拉菜单 数据
	 *
	 * @return
	 */
	public List<DeviceInfo> getdeviceType()
	{
		return deviceInfoDao.getdeviceType();
	}



	/**
	 * 设备统计
	 *
	 * @param deviceinfo
	 * @return
	 */
	public Map<String, String> statistics(DeviceInfo deviceinfo)
	{
		try
		{
			logger.debug("设备统计查询");
			return deviceInfoDao.statistics(deviceinfo);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}



	public int getallDevice(DeviceInfo device)
	{
		return deviceInfoDao.getallDevice(device);
	}



	public int kyDevice(DeviceInfo device)
	{
		return deviceInfoDao.kyDevice(device);
	}



	public int chDevice(DeviceInfo device)
	{
		return deviceInfoDao.chDevice(device);
	}



	public int wxDevice(DeviceInfo device)
	{
		return deviceInfoDao.wxDevice(device);
	}



	public int deleDevice(DeviceInfo device)
	{
		return deviceInfoDao.deleDevice(device);
	}



	/**
	 * 统计
	 *
	 * @param ordersInfo
	 * @return
	 */
	public int statisticscount(DeviceInfo device)
	{
		try
		{
			return deviceInfoDao.statisticscount(device);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}



	public boolean updateReturn(String sn)
	{
		try
		{
			int temp = deviceInfoDao.updateReturn(sn);
			if (temp > 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;

	}



	/**
	 * 下设备订单后将设备状态改为使用中
	 *
	 * @param sn
	 * @return
	 */
	public boolean updateDeviceOrder(String sn)
	{
		try
		{
			int temp = deviceInfoDao.updateDeviceOrder(sn);
			if (temp > 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;

	}



	public int forsncount(String sn)
	{
		return deviceInfoDao.forsncount(sn);
	}



	/**
	 * 修改 SN
	 *
	 * @param SN
	 * @param oldSN
	 * @return
	 */
	public int updateSN(DeviceDealOrders deviceDealOrders)
	{
		try
		{
			return deviceInfoDao.updateSN(deviceDealOrders);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.info("修改SN失败");
		}
		return 0;
	}



	/**
	 * 将设备信息表SN修改为可使用
	 *
	 * @param deviceInfo
	 * @return
	 */
	public int updateSN_Deviceinfo(DeviceInfo deviceInfo)
	{
		try
		{
			return deviceInfoDao.updateSN_Deviceinfo(deviceInfo);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			logger.info("修改失败");
		}
		return 0;
	}



	public DeviceInfo getonebysn(String sn)
	{
		try
		{
			return deviceInfoDao.getbysn(sn);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			logger.info("查询失败");
		}
		return null;
	}



	/**
	 * 查看此设备订单是否为 可使用状态
	 *
	 * @param dea
	 * @return 2015-9-25 11:51:20
	 */
	public int searchDevicekeshiyong(DeviceDealOrders dea)
	{
		return deviceInfoDao.searchDevicekeshiyong(dea);
	}



	public int updatedevicestatus(DeviceInfo info)
	{
		try
		{
			return deviceInfoDao.updatedevicestatus(info);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}



	public int deviceDistribute(DeviceInfo deviceInfo)
	{
		try
		{
			return deviceInfoDao.deviceDistribute(deviceInfo);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}



	public DeviceInfo selectsnDistributor(DeviceInfo deviceInfo)
	{
		try
		{
			return deviceInfoDao.selectsnDistributor(deviceInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}



	public int deldistributorID(DeviceInfo deviceInfo)
	{
		try
		{
			return deviceInfoDao.deldistributorID(deviceInfo);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}



	/**
	 * 根据sn验证设备是否属于渠道商
	 *
	 * @return
	 */
	public int ckqdsforsn(DeviceInfo deviceInfo)
	{
		return deviceInfoDao.ckqdsforsn(deviceInfo);
	}



	public int updateRemark(DeviceInfo deviceInfo)
	{
		try
		{
			return deviceInfoDao.updateRemark(deviceInfo);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}



	public DeviceInfo selectDeviceInfoByDis(DeviceInfo deviceInfo)
	{
		try
		{
			return deviceInfoDao.selectDeviceInfoByDis(deviceInfo);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}



	public String getpageStringkucun(SearchDTO searchDTO)
	{
		logger.debug("分页server开始");
		try
		{
			String jsonString = deviceInfoDao.getkucunpage(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}

	public String getygqSN(SearchDTO searchDTO)
	{
		logger.debug("分页server开始");
		try
		{
			String jsonString = deviceInfoDao.getygqSN(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 查询订单已过期设备未归还的数据
	 * @param device
	 * @return
	 */
	public List<DeviceInfo> getygqSNdevice(DeviceInfo device)
	{
	  return deviceInfoDao.getygqSNdevice(device);
	}

	/**
	 * 校验SN是否能被下单
	 *
	 * @param SN
	 * @return -1表示SN不存在。-2表示SN使用中,0表示SN或漫游卡不支持国家，1表示可以被下单.
	 */
	public int verifySN(String SN, String flowCtring)
	{
		DeviceInfo d = getdevicetwo(SN);
		if (d == null)
		{
			return -1;
		}
		if (!d.getDeviceStatus().equals("可使用"))
		{
			return -2;
		}
		if (d != null)
		{
			// 查询SN的漫游卡,判断该SN是否支持该国家
			SIMInfo simInfo = roamingSimInfoDao.getInfoBySN(d.getSN());
			if (simInfo == null)
			{
				if (StringUtils.isBlank(d.getSupportCountry()))
				{
					return 1;
				}
				else
				{
					String[] mcc = null;
					// 将流量订单的国家解析为国家码数组
					if (flowCtring.indexOf("|") > 0)
					{
						String[] temp = flowCtring.split("\\|");
						mcc = new String[temp.length];
						for (int i = 0; i < temp.length; i++)
						{
							String[] temp1 = temp[i].split(",");
							mcc[i] = temp1[1];
							// 判断是否包含
							if (d.getSupportCountry().indexOf("-") == 0)
							{
								if (d.getSupportCountry().contains(temp1[1]))
								{
									break;
								}
							}
							else
							{
								if (!d.getSupportCountry().contains(temp1[1]))
								{
									break;
								}
							}
							if (i == temp.length - 1)
							{
								return 1;
							}
						}
					}
					else
					{
						String[] temp = flowCtring.split(",");

						mcc = new String[] { temp[1] };
						// 判断是否包含
						if (d.getSupportCountry().indexOf("-") == 0)
						{
							if (!d.getSupportCountry().contains(temp[1]))
							{
								return 1;
							}
						}
						else
						{
							if (d.getSupportCountry().contains(temp[1]))
							{
								return 1;
							}
						}
					}

				}
			}
			else
			{
				if (StringUtils.isBlank(d.getSupportCountry()) && StringUtils.isBlank(simInfo.getCountryList()))
				{
					return 1;
				}
				else if (StringUtils.isBlank(d.getSupportCountry()) && StringUtils.isNotBlank(simInfo.getCountryList()))
				{

					String[] mcc = null;
					// 将流量订单的国家解析为国家码数组

					String[] temp = null;
					if (flowCtring.indexOf("|") > 0)
					{
						temp = flowCtring.split("\\|");
					}
					else
					{
						temp = new String[] { flowCtring };
					}

					mcc = new String[temp.length];
					for (int i = 0; i < temp.length; i++)
					{
						String[] temp1 = temp[i].split(",");
						mcc[i] = temp1[1];
						// 判断是否包含
						if (simInfo.getCountryList().indexOf("-") == 0)
						{
							if (simInfo.getCountryList().contains(temp1[1]))
							{
								break;
							}
						}
						else
						{
							if (!simInfo.getCountryList().contains(temp1[1]))
							{
								break;
							}
						}

						if (i == temp.length - 1)
						{
							return 1;
						}
					}

				}
				else if (StringUtils.isNotBlank(d.getSupportCountry()) && StringUtils.isBlank(simInfo.getCountryList()))
				{
					String[] mcc = null;
					// 将流量订单的国家解析为国家码数组
					String[] temp = null;
					if (flowCtring.indexOf("|") > 0)
					{
						temp = flowCtring.split("\\|");
					}
					else
					{
						temp = new String[] { flowCtring };
					}
					mcc = new String[temp.length];
					for (int i = 0; i < temp.length; i++)
					{
						String[] temp1 = temp[i].split(",");
						mcc[i] = temp1[1];
						// 判断是否包含
						if (d.getSupportCountry().indexOf("-") == 0)
						{
							if (d.getSupportCountry().contains(temp1[1]))
							{
								break;
							}
						}
						else
						{
							if (!d.getSupportCountry().contains(temp1[1]))
							{
								break;
							}
						}

						if (i == temp.length - 1)
						{
							return 1;
						}
					}

				}
				else
				{
					String[] mcc = null;
					// 将流量订单的国家解析为国家码数组
					if (flowCtring.indexOf("|") > 0)
					{
						String[] temp = flowCtring.split("\\|");
						mcc = new String[temp.length];
						for (int i = 0; i < temp.length; i++)
						{
							String[] temp1 = temp[i].split(",");
							mcc[i] = temp1[1];
							// 判断是否包含
							if (d.getSupportCountry().indexOf("-") == 0 && simInfo.getCountryList().indexOf("-") == 0)
							{
								if (d.getSupportCountry().contains(temp1[1]) || simInfo.getCountryList().contains(temp1[1]))
								{
									break;
								}
							}
							else if (d.getSupportCountry().indexOf("-") == 0 && simInfo.getCountryList().indexOf("-") != 0)
							{
								if (d.getSupportCountry().contains(temp1[1]) || !simInfo.getCountryList().contains(temp1[1]))
								{
									break;
								}
							}
							else if (d.getSupportCountry().indexOf("-") != 0 && simInfo.getCountryList().indexOf("-") == 0)
							{
								if (!d.getSupportCountry().contains(temp1[1]) || simInfo.getCountryList().contains(temp1[1]))
								{
									break;
								}
							}
							else
							{
								if (!d.getSupportCountry().contains(temp1[1]) || !simInfo.getCountryList().contains(temp1[1]))
								{
									break;
								}
							}
							if (i == temp.length - 1)
							{
								return 1;
							}
						}
					}
					else
					{
						String[] temp = flowCtring.split(",");

						mcc = new String[] { temp[1] };
						// 判断是否包含
						if (d.getSupportCountry().indexOf("-") == 0 && simInfo.getCountryList().indexOf("-") == 0)
						{
							if (!d.getSupportCountry().contains(temp[1]) && !simInfo.getCountryList().contains(temp[1]))
							{
								return 1;
							}
						}
						else if (d.getSupportCountry().indexOf("-") == 0 && simInfo.getCountryList().indexOf("-") != 0)
						{
							if (!d.getSupportCountry().contains(temp[1]) && simInfo.getCountryList().contains(temp[1]))
							{
								return 1;
							}
						}
						else if (d.getSupportCountry().indexOf("-") != 0 && simInfo.getCountryList().indexOf("-") == 0)
						{
							if (d.getSupportCountry().contains(temp[1]) && !simInfo.getCountryList().contains(temp[1]))
							{
								return 1;
							}
						}
						else
						{
							if (d.getSupportCountry().contains(temp[1]) && simInfo.getCountryList().contains(temp[1]))
							{
								return 1;
							}
						}
					}
				}
			}
		}
		return 0;
	}



	/**
	 * 分页，排序，条件查询.
	 *
	 * @param searchDTO
	 * @return
	 */
	public String getUpgradePage(SearchDTO searchDTO)
	{
		logger.debug("分页server开始");
		try
		{
			String jsonString = deviceInfoDao.getUpgradePage(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}
	/**
	 * 不分页查询设备升级配置
	 * @param params
	 * @return
	 */
	
	public List<DeviceUpgrading> getUpgrade(Map<String,Object> params){
		try
		{
			return deviceInfoDao.getUpgrade(params);
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 新增设备更新配置信息
	 *
	 * @param upgrading
	 * @return
	 */
	public boolean insert(DeviceUpgrading upgrading)
	{
		logger.debug("开始执行插入设备更新配置信息");
		try
		{
			if (deviceInfoDao.insert(upgrading) > 0)
			{
				logger.debug("插入设备更新配置信息成功");
				return true;
			}
			else
			{
				logger.debug("插入设备更新配置信息失败");
				return false;
			}
		}
		catch (BmException e)
		{
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}



	/**
	 * 修改设备更新配置状态信息
	 *
	 * @param ids
	 * @return
	 */
	public boolean updateUpdatingStatus(List<String> list)
	{
		logger.debug("开始执行更新设备更新配置信息");
		try
		{
			if (deviceInfoDao.updateStatus(list) > 0)
			{
				logger.debug("更新设备更新配置信息成功");
				return true;
			}
			else
			{
				logger.debug("更新设备更新配置信息失败");
				return false;
			}
		}
		catch (BmException e)
		{
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}



	/**
	 * 根据SN查询设备更新配置信息(状态为未更新)
	 *
	 * @param SN
	 * @return
	 */
	public DeviceUpgrading getNotUpdatedBySN(String SN)
	{
		try
		{
			return deviceInfoDao.getNotUpdatedBySN(SN);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

//	public DeviceUpgrading getNotUpdatedBySNone(String SN)
//	{
//		try
//		{
//			return deviceInfoDao.getNotUpdatedBySNone(SN);
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//		return null;
//	}


	/**
	 * 根据SN查询设备更新配置信息
	 *
	 * @param SN
	 * @return
	 */
	public DeviceUpgrading getAllUpdatingBySN(String SN)
	{
		try
		{
			return deviceInfoDao.getAllUpdatingBySN(SN);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * 更新设备配置信息(设置更新状态为已更新)
	 *
	 * @param deviceUpgradingID
	 * @return
	 */
	public boolean updateUpgradeSuccess(String SN)
	{
		logger.debug("开始执行更新设备更新配置信息");
		try
		{
			if (deviceInfoDao.updateUpgradeSuccess(SN) > 0)
			{
				logger.debug("更新设备更新配置信息成功");
				return true;
			}
			else
			{
				logger.debug("更新设备更新配置信息失败");
				return false;
			}
		}
		catch (BmException e)
		{
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 更新设备配置信息(设置更新状态为已更新)Portal升级成功后，修改升级记录状态
	 *
	 * @param deviceUpgradingID
	 * @return
	 */
	public boolean updateUpgradeSuccessForPortal(String SN,String fileType)
	{
		logger.debug("开始执行更新设备更新配置信息");
		DeviceUpgrading upgrading=new DeviceUpgrading();
		upgrading.setSN(SN);
		upgrading.setUpgradeFileType(fileType);
		try
		{
			if (deviceInfoDao.updateUpgradeSuccessForPortal(upgrading) > 0)
			{
				logger.debug("更新设备更新配置信息成功");
				return true;
			}
			else
			{
				logger.debug("更新设备更新配置信息失败");
				return false;
			}
		}
		catch (BmException e)
		{
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}


	/**
	 * 更新设备配置信息
	 *
	 * @param deviceUpgradingID
	 * @return
	 */
	public boolean updateUpgrade(DeviceUpgrading upgrading)
	{
		logger.debug("开始执行更新设备更新配置信息");
		try
		{
			if (deviceInfoDao.updateUpgrade(upgrading) > 0)
			{
				logger.debug("更新设备更新配置信息成功");
				return true;
			}
			else
			{
				logger.debug("更新设备更新配置信息失败");
				return false;
			}
		}
		catch (BmException e)
		{
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}



	public boolean insertFactory(FactoryTestInfo factoryTestInfo)
	{
		logger.debug("开始执行新增工厂测试记录信息");
		try
		{
			if (deviceInfoDao.insertFactory(factoryTestInfo) > 0)
			{
				logger.debug("新增工厂测试记录信息成功");
				return true;
			}
			else
			{
				logger.debug("新增工厂测试记录信息失败");
				return false;
			}
		}
		catch (BmException e)
		{
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}



	/**
	 * ======= }
	 *
	 * /** >>>>>>> Stashed changes 分页，排序，条件查询
	 *
	 * @param searchDTO
	 * @return
	 */

	public String getFactoryPage(SearchDTO searchDTO)
	{

		logger.debug("分页开始");
		try
		{
			String jsonString = deviceInfoDao.getFactoryPage(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}



	public List<FactoryTestInfo> selectFactoryExport(FactoryTestInfo factoryTestInfo)
	{
		return deviceInfoDao.selectFactoryExport(factoryTestInfo);
	}



	public List<FactoryTestInfo> selectDistinctFactoryExport(FactoryTestInfo factoryTestInfo)
	{
		return deviceInfoDao.selectDistinctFactoryExport(factoryTestInfo);
	}



	/**
	 * 更改设备状态为出库
	 *
	 * @return
	 */
	public int updaterepertoryStatus(DeviceInfo deviceInfo)
	{
		try
		{
			return deviceInfoDao.updaterepertoryStatus(deviceInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}



	public String getkeyongdevicepageString(SearchDTO searchDTO)
	{
		logger.debug("分页server开始");
		try
		{
			String jsonString = deviceInfoDao.getkeyongpage(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}



	public String getrukudevicepageString(SearchDTO searchDTO)
	{
		logger.debug("分页server开始");
		try
		{
			String jsonString = deviceInfoDao.getrukupage(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}



	public int updateDeviceStatus_chuku(DeviceInfo info)
	{
		return deviceInfoDao.updateDeviceStatus_chuku(info);
	}



	public int updateDevice_ruku(String sn)
	{
		return deviceInfoDao.updateDevice_ruku(sn);
	}



	public int updaterepertoryStatus2(DeviceInfo repertoryStatus)
	{
		return deviceInfoDao.updaterepertoryStatus2(repertoryStatus);
	}



	/**
	 * 对于在使用中的设备， 确认是否可以在下单，条件为：国家相同，时单不同可以下单，时间相同，国家不同，可以被 下单
	 *
	 * @param flowDealOrders
	 * @return -1:请确认同一国家订单，使用时间是否有重复 1：表示可以被 下单
	 */
	public int affirmIScreatorOrder(FlowDealOrders flowDealOrders)
	{

		// 保存订单使用国家所使用的具体天数
		// 保存格式如： tempMap.put("中国","12521547851-25361445214");
		Map<String, String> tempMap = new HashMap<String, String>();

		// 获取到此设备当前可使用的订单
		List<FlowDealOrders> flowDealOrders2 = flowDealOrdersDao.getFlowOrderbysn(flowDealOrders.getSN());

		for (FlowDealOrders flowDealOrders3 : flowDealOrders2)
		{
			String[] userCountryArr = flowDealOrders3.getUserCountry().split("\\|");

			String panlUserDate = DateUtils.dateToTimeStamp(flowDealOrders3.getPanlUserDate(), "yyyy-MM-dd HH:mm:ss");

			String flowExpireDate = DateUtils.dateToTimeStamp(flowDealOrders3.getFlowExpireDate(), "yyyy-MM-dd HH:mm:ss");

			String userDate = panlUserDate + "-" + flowExpireDate;

			for (int i = 0; i < userCountryArr.length; i++)
			{
				String userCountry = userCountryArr[i].split(",")[0];

				if (tempMap.containsKey(userCountry))
				{
					String value = tempMap.get(userCountry);

					userDate = value + "|" + userDate;

					tempMap.put(userCountry, userDate);
				}
				else
				{
					tempMap.put(userCountry, userDate);
				}
			}
		}
		// 当前创建订单所使用的国家，开始时间，结束时间
		String userCountry = flowDealOrders.getUserCountry();
		Long panlUserDate = Long.parseLong(DateUtils.dateToTimeStamp(flowDealOrders.getPanlUserDate() + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		String expireDate = DateUtils.getcurDate(DateUtils.beforeNDateToString(DateUtils.parseDate(flowDealOrders.getPanlUserDate()), flowDealOrders.getFlowDays(), "yyyy-MM-dd HH:mm:ss"), 1);
		Long flowExpireDate = Long.parseLong(DateUtils.dateToTimeStamp(expireDate, "yyyy-MM-dd HH:mm:ss"));

		String[] userCountryArr = new String[userCountry.split(",").length];
		if (userCountry.indexOf(",") != -1)
		{
			for (int i = 0; i < userCountry.split(",").length; i++)
			{
				userCountryArr[i] = userCountry.split(",")[i];
			}
		}
		else
		{
			userCountryArr[0] = userCountry;
		}

		for (int k = 0; k < userCountryArr.length; k++)
		{
			userCountry = userCountryArr[k];

			if (tempMap.containsKey(userCountry))
			{
				// 获取到当前国家的使用时间
				String userDate = tempMap.get(userCountry);

				String[] userDateArr = userDate.split("\\|");

				for (int i = 0; i < userDateArr.length; i++)
				{
					// 获取到旧订单的具体使用的开始，结否时间
					Long panlUserDateOld = Long.parseLong(userDateArr[i].split("-")[0]);
					Long flowExpireDateOld = Long.parseLong(userDateArr[i].split("-")[1]);

					if (panlUserDate >= panlUserDateOld && panlUserDate <= flowExpireDateOld)
					{
						// 不可以下单
						logger.info("订单重复");
						logger.info("新创建订单使用国家：" + userCountry + "，使用时间：" + flowDealOrders.getPanlUserDate() + " 00:00:00" +"||"+ expireDate);
						logger.info("    原订单使用国家：" + userCountry + "，使用时间：" + DateUtils.timeStampToDate(panlUserDateOld + "", "yyyy-MM-dd HH:mm:ss") + "||" + DateUtils.timeStampToDate(flowExpireDateOld + "", "yyyy-MM-dd HH:mm:ss"));
						return -1;
					}
					else if (flowExpireDate >= panlUserDateOld && flowExpireDate <= flowExpireDateOld)
					{
						// 不可以下单
						logger.info("订单重复");
						logger.info("新创建订单使用国家：" + userCountry + "，使用时间：" + flowDealOrders.getPanlUserDate() + " 00:00:00_" +"||"+ expireDate);
						logger.info("    原订单使用国家：" + userCountry + "，使用时间：" + DateUtils.timeStampToDate(panlUserDateOld + "", "yyyy-MM-dd HH:mm:ss") + "||" + DateUtils.timeStampToDate(flowExpireDateOld + "", "yyyy-MM-dd HH:mm:ss"));
						return -1;
					}
				}
			}
			else
			{
				// 订单中没有这个国家，暂时不考虑时间是否重复，如果重复还是可以下单的
			}

		}

		return 1;
	}
	
	public List<DeviceUpgrading> getDeviceUpgradeExport(DeviceUpgrading upgrading){
		return deviceInfoDao.getDeviceUpgradeExport(upgrading);
	}
	//导出渠道商选中的设备
	public List<DeviceInfo> excelExportDevice(DeviceInfo deviceIDstr){
		return deviceInfoDao.excelExportDevice(deviceIDstr);
	}
	public int updateVersionBySN(DeviceInfo deviceInfo){
	    try
	    {
		return deviceInfoDao.updateVersionBySN(deviceInfo);
	    }
	    catch (Exception e)
	    {
		e.printStackTrace();
	    }
	    return 0;
	}
}
