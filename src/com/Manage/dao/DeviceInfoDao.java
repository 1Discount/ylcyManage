package com.Manage.dao;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.DeviceDealOrders;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.DeviceUpgrading;
import com.Manage.entity.FactoryTestInfo;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

@Repository
public class DeviceInfoDao extends BaseDao
{

	private static final String NAMESPACE = DeviceInfoDao.class.getName() + ".";



	/**
	 * 分页查询返回json
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpage(SearchDTO searchDTO)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "queryPage2", "getcount2", searchDTO);
			List<DeviceInfo> arr = (List<DeviceInfo>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (DeviceInfo a : arr)
			{
				String distributorName = a.getDistributorName();
				if (StringUtils.isNotBlank(distributorName))
				{
					a.setDistributorName(distributorName.split(",")[distributorName.split(",").length - 1]);
				}
				else
				{
					a.setDistributorName("");
				}
				JSONObject obj = JSONObject.fromObject(a);
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



	public String getpagedelete(SearchDTO searchDTO)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "queryPagedelete", "getcountdelete", searchDTO);
			List<DeviceInfo> arr = (List<DeviceInfo>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (DeviceInfo a : arr)
			{
				JSONObject obj = JSONObject.fromObject(a);
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
	 * 查询所有客户信息
	 * 
	 * @param page
	 * @param pageCount
	 * @return
	 */
	// public List<CustomerInfo> getAllCustomerInfo(Integer page, Integer
	// pageCount)
	// {
	// return getSqlSession().selectList("querycustomerlist",page);
	// }

	/**
	 * 插入设备信息数据
	 * 
	 * @param cus
	 * @return
	 */
	public int insertDeviceInfo(DeviceInfo device)
	{
		try
		{
			return getSqlSession().insert(NAMESPACE + "insertdevice", device);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1001, e);
		}
	}



	public int insertDeviceInfotwo(DeviceInfo device)
	{
		try
		{
			return getSqlSession().insert(NAMESPACE + "insertdevicetwo", device);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1001, e);
		}
	}



	/**
	 * 获取需修改的数据
	 * 
	 * @param uid
	 * @return
	 */
	public DeviceInfo getdeviceinfoEdit(String uid)
	{
		try
		{
			return getSqlSession().selectOne(NAMESPACE + "getOnedeviceInfo", uid);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	/**
	 * 获取统计数据
	 * 
	 * @param uid
	 * @return
	 */
	public DeviceInfo getDeviceInfoCount()
	{
		try
		{
			return getSqlSession().selectOne(NAMESPACE + "getDeviceCount");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	/**
	 * 删除设备
	 * 
	 * @param uid
	 * @return
	 */
	public int getdeviceinfoDelete(String uid)
	{
		try
		{
			return getSqlSession().update(NAMESPACE + "getDeleteDeviceInfo", uid);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1002, e);
		}
	}



	// 获取单个设备信息
	public DeviceInfo getdeviceInfodetail(String uid)
	{
		try
		{
			return getSqlSession().selectOne(NAMESPACE + "getOnedeviceInfo", uid);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	public int getdevice(String uid)
	{
		try
		{
			return getSqlSession().selectOne(NAMESPACE + "getOnedevice", uid);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	public DeviceInfo getdevicetwo(String uid)
	{
		try
		{
			return getSqlSession().selectOne(NAMESPACE + "getOnedevice1", uid);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	/**
	 * 获取单个客户信息详情
	 * 
	 * @param uid
	 * @return
	 */
	// public CustomerInfo getOneCustomerDetail(String uid)
	// {
	// return getSqlSession().selectOne("selectOneCustomerinfo", uid);
	// }
	//
	// public List<CustomerInfo> getSearchCustomerInfoList(CustomerInfo cus)
	// {
	// return getSqlSession().selectList("searchCustomerInfoList", cus);
	// }

	/**
	 * 修改客户信息
	 * 
	 * @param id
	 * @return
	 */
	public int updateDevice(DeviceInfo id)
	{
		try
		{
			return getSqlSession().update(NAMESPACE + "ToupdateDevice", id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	public int updatedeviceToEnd(DeviceInfo device)
	{
		try
		{
			return getSqlSession().update(NAMESPACE + "DeviceInfostatus", device);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	// 修改 设备交易订单表为已归还
	public int updateDeviceDealOrders(DeviceDealOrders snid)
	{
		try
		{
			return getSqlSession().update(NAMESPACE + "ToupdateDeviceDealOrders", snid);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	public String searchDeviceInfoTypeforSnDao(String sn)
	{
		return getSqlSession().selectOne("searchDeviceInfoTypeforSn", sn);
	}



	// 查看此设备是否存在没有结束是交易记录
	public int searchDevice(DeviceDealOrders ddo)
	{
		return getSqlSession().selectOne("searchDevice", ddo);
	}



	// 查看此设备是否存在没有结束是交易记录
	public int searchDevice2(DeviceDealOrders ddo)
	{
		return getSqlSession().selectOne("searchDevice2", ddo);
	}



	/**
	 * 删除客户信息
	 * 
	 * @param cus
	 * @return
	 */
	// public int deleteCus(String cus)
	// {
	// return getSqlSession().update("deleteCustomerInfo", cus);
	// }

	/**
	 * 分页查询设备信息
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getdevpage(SearchDTO searchDTO)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "selectpage", "getdevcount", searchDTO);
			List<DeviceInfo> arr = (List<DeviceInfo>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (DeviceInfo a : arr)
			{
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
	 * 根据设备SN查询设备
	 * 
	 * @param sn
	 * @return
	 */
	public DeviceInfo getbysn(String sn)
	{
		try
		{
			return getSqlSession().selectOne(NAMESPACE + "getbysn", sn);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1002, e);
		}
	}



	public List<DeviceInfo> getCountsforTime(DeviceInfo device)
	{
		try
		{
			return getSqlSession().selectList(NAMESPACE + "getCountTimes", device);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	/**
	 * 下拉菜单显示 设备的使用状态
	 * 
	 * @return
	 */
	public List<DeviceInfo> getdeviceType()
	{
		try
		{
			return getSqlSession().selectList(NAMESPACE + "getDeviceTypes");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	/**
	 * 统计查询
	 * 
	 * @param DeviceInfo
	 * @return
	 */
	public Map<String, String> statistics(DeviceInfo deviceinfo)
	{
		try
		{
			return getSqlSession().selectOne(NAMESPACE + "statistics", deviceinfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
			// TODO: handle exception
		}
	}



	public int kyDevice(DeviceInfo device)
	{
		try
		{
			return getSqlSession().selectOne(NAMESPACE + "kyDevice", device);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
			// TODO: handle exception
		}
	}



	public int chDevice(DeviceInfo device)
	{
		try
		{
			return getSqlSession().selectOne(NAMESPACE + "chDevice", device);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
			// TODO: handle exception
		}
	}



	public int wxDevice(DeviceInfo device)
	{
		try
		{
			return getSqlSession().selectOne(NAMESPACE + "wxDevice", device);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
			// TODO: handle exception
		}
	}



	public int deleDevice(DeviceInfo device)
	{
		try
		{
			return getSqlSession().selectOne(NAMESPACE + "deleDevice", device);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
			// TODO: handle exception
		}
	}



	public int getallDevice(DeviceInfo device)
	{
		try
		{
			return getSqlSession().selectOne(NAMESPACE + "getallDevice", device);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
			// TODO: handle exception
		}
	}



	/**
	 * 订单统计
	 * 
	 * @param ordersInfo
	 * @return
	 */
	public int statisticscount(DeviceInfo device)
	{
		try
		{
			return getSqlSession().selectOne(NAMESPACE + "devicecount", device);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	/**
	 * 设备归还将设备状态改为可使用
	 * 
	 * @param sn
	 * @return
	 */
	public int updateReturn(String sn)
	{
		try
		{
			return getSqlSession().update(NAMESPACE + "updateReturn", sn);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}

	}



	/**
	 * 下设备订单后将设备状态改为使用中
	 * 
	 * @param sn
	 * @return
	 */
	public int updateDeviceOrder(String SN)
	{
		try
		{
			return getSqlSession().update(NAMESPACE + "updateDeviceOrder", SN);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}

	}



	public int forsncount(String sn)
	{
		try
		{
			return getSqlSession().selectOne(NAMESPACE + "forsncount", sn);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	/**
	 * 更改设备订单 SN
	 * 
	 * @param SN
	 * @param oldSN
	 * @return
	 */
	public int updateSN(DeviceDealOrders deviceDealOrders)
	{
		try
		{
			return getSqlSession().update(NAMESPACE + "updateSN", deviceDealOrders);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1003, e);
		}

	}



	/**
	 * 将设备状态更改为可使用
	 * 
	 * @param deviceInfo
	 * @return
	 */
	public int updateSN_Deviceinfo(DeviceInfo deviceInfo)
	{
		try
		{
			return getSqlSession().update(NAMESPACE + "updateReturn", deviceInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	/**
	 * 根据SN查询
	 * 
	 * @param sn
	 * @return
	 */
	public DeviceInfo getonebysn(String sn)
	{
		try
		{
			return getSqlSession().selectOne("getonebysn", sn);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	public int searchDevicekeshiyong(DeviceDealOrders ddo)
	{
		return getSqlSession().selectOne("searchDevicekeshiyong", ddo);
	}



	/**
	 * 根据SN查询FD
	 * 
	 * @param info
	 * @return
	 */
	public DeviceInfo searchfdInfo(DeviceInfo info)
	{
		try
		{
			return getSqlSession().selectOne(NAMESPACE + "searchfdInfo", info);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	public int updatedevicestatus(DeviceInfo info)
	{
		try
		{
			return getSqlSession().update(NAMESPACE + "updatedevicestatus", info);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	public int deviceDistribute(DeviceInfo deviceInfo)
	{
		try
		{
			return getSqlSession().update(NAMESPACE + "deviceDistribute", deviceInfo);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	public String getpageString(SearchDTO searchDTO)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "queryPage", "getcount", searchDTO);
			List<DeviceInfo> arr = (List<DeviceInfo>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (DeviceInfo a : arr)
			{
				JSONObject obj = JSONObject.fromObject(a);
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



	public DeviceInfo selectsnDistributor(DeviceInfo deviceInfo)
	{
		try
		{
			return getSqlSession().selectOne(NAMESPACE + "selectsnDistributor", deviceInfo);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}

	}



	public int deldistributorID(DeviceInfo deviceInfo)
	{
		try
		{
			return getSqlSession().update(NAMESPACE + "deldistributorID", deviceInfo);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	/**
	 * 
	 * @param deviceInfo
	 * @return
	 */
	public int ckqdsforsn(DeviceInfo deviceInfo)
	{
		try
		{
			return getSqlSession().selectOne(NAMESPACE + "ckqdsforsn", deviceInfo);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	public int updateRemark(DeviceInfo deviceInfo)
	{
		try
		{
			return getSqlSession().update(NAMESPACE + "updateRemark", deviceInfo);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1003, e);
		}

	}



	public DeviceInfo selectDeviceInfoByDis(DeviceInfo deviceInfo)
	{
		try
		{
			return getSqlSession().selectOne(NAMESPACE + "selectDeviceInfoByDis", deviceInfo);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	public String getkucunpage(SearchDTO searchDTO)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "queryPage2kucun", "getcount2kucun", searchDTO);

			List<DeviceInfo> arr = (List<DeviceInfo>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (DeviceInfo a : arr)
			{
				JSONObject obj = JSONObject.fromObject(a);
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



	public String getygqSN(SearchDTO searchDTO)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "queryPage2getygqSN", "getcount2getygqSN", searchDTO);

			List<DeviceInfo> arr = (List<DeviceInfo>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (DeviceInfo a : arr)
			{
				JSONObject obj = JSONObject.fromObject(a);
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
	 * * 查询订单已过期设备未归还的数据
	 * 
	 * @param device
	 * @return
	 */
	public List<DeviceInfo> getygqSNdevice(DeviceInfo device)
	{
		try
		{
			return getSqlSession().selectList(NAMESPACE + "getygqSNdevice", device);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	/**
	 * 分页查询设备更新配置信息返回json
	 * 
	 * @param searchDTO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getUpgradePage(SearchDTO searchDTO)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "getUpgradePage", "getUpgradeCount", searchDTO);
			List<DeviceUpgrading> arr = (List<DeviceUpgrading>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (DeviceUpgrading a : arr)
			{
				JSONObject obj = JSONObject.fromObject(a);
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

	public List<DeviceUpgrading> getUpgrade(Map<String,Object> params){
		try
		{
			return getSqlSession().selectList(NAMESPACE+"getUpgrade", params);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 新增设备更新配置信息
	 * 
	 * @param upgrading
	 * @return
	 */
	public int insert(DeviceUpgrading upgrading)
	{
		try
		{

			return getSqlSession().insert(NAMESPACE + "insertSelective", upgrading);

		}
		catch (Exception e)
		{

			throw new BmException(Constants.common_errors_1001, e);
		}
	}



	/**
	 * 修改设备更新配置状态信息
	 * 
	 * @param ids
	 * @return
	 */
	public int updateStatus(List<String> list)
	{
		try
		{
			return getSqlSession().update(NAMESPACE + "updateUpgradeStatus", list);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1003, e);
		}
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
			return getSqlSession().selectOne(NAMESPACE + "getNotUpdatedBySN", SN);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}

	}


//	public DeviceUpgrading getNotUpdatedBySNone(String SN)
//	{
//
//		try
//		{
//			return getSqlSession().selectOne(NAMESPACE + "getNotUpdatedBySNone", SN);
//		}
//		catch (Exception e)
//		{
//			throw new BmException(Constants.common_errors_1004, e);
//		}
//
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
			return getSqlSession().selectOne(NAMESPACE + "getAllUpdatingBySN", SN);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}

	}



	/**
	 * 更新设备配置信息(设置更新状态为已更新)
	 * 
	 * @param deviceUpgradingID
	 * @return
	 */
	public int updateUpgradeSuccess(String SN)
	{

		try
		{
			return getSqlSession().update(NAMESPACE + "updateUpgradeSuccess", SN);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1003, e);
		}

	}
	/**
	 * 更新设备配置信息(设置更新状态为已更新)
	 * 
	 * @param deviceUpgradingID
	 * @return
	 */
	public int updateUpgradeSuccessForPortal(DeviceUpgrading upgrading)
	{

		try
		{
			return getSqlSession().update(NAMESPACE + "updateUpgradeSuccessForPortal", upgrading);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1003, e);
		}

	}
	


	/**
	 * 更新设备配置信息
	 * 
	 * @param deviceUpgradingID
	 * @return
	 */
	public int updateUpgrade(DeviceUpgrading upgrading)
	{

		try
		{
			return getSqlSession().update(NAMESPACE + "updateSelective", upgrading);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1003, e);
		}

	}



	/**
	 * 新增工厂测试记录
	 * 
	 * @param factoryTestInfo
	 * @return
	 */
	public int insertFactory(FactoryTestInfo factoryTestInfo)
	{
		try
		{

			return getSqlSession().insert(NAMESPACE + "insertFactorySelective", factoryTestInfo);

		}
		catch (Exception e)
		{

			throw new BmException(Constants.common_errors_1001, e);
		}
	}



	/**
	 * 分页查询工厂测试记录
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getFactoryPage(SearchDTO searchDTO)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "getFactoryPage", "getFactoryCount", searchDTO);
			List<FactoryTestInfo> arr = (List<FactoryTestInfo>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (FactoryTestInfo a : arr)
			{
				JSONObject obj = JSONObject.fromObject(a);

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



	public List<FactoryTestInfo> selectFactoryExport(FactoryTestInfo factoryTestInfo)
	{

		try
		{

			return getSqlSession().selectList(NAMESPACE + "selectFactoryExport", factoryTestInfo);

		}
		catch (Exception e)
		{

			throw new BmException(Constants.common_errors_1004, e);
		}

	}



	public List<FactoryTestInfo> selectDistinctFactoryExport(FactoryTestInfo factoryTestInfo)
	{

		try
		{

			return getSqlSession().selectList(NAMESPACE + "selectDistinctFactoryExport", factoryTestInfo);

		}
		catch (Exception e)
		{

			throw new BmException(Constants.common_errors_1004, e);
		}

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

			return getSqlSession().update(NAMESPACE + "updaterepertoryStatus", deviceInfo);

		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1003, e);
		}
	}



	public String getkeyongpage(SearchDTO searchDTO)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "queryPagekeyongdevice", "getcountkeyongdevice", searchDTO);

			List<DeviceInfo> arr = (List<DeviceInfo>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (DeviceInfo a : arr)
			{
				JSONObject obj = JSONObject.fromObject(a);
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



	public String getrukupage(SearchDTO searchDTO)
	{
		try
		{
			Page page = queryPage(NAMESPACE, "queryPagerukudevice", "getcountrukudevice", searchDTO);

			List<DeviceInfo> arr = (List<DeviceInfo>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (DeviceInfo a : arr)
			{
				JSONObject obj = JSONObject.fromObject(a);
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



	public int updateDeviceStatus_chuku(DeviceInfo info)
	{
		try
		{
			return getSqlSession().update(NAMESPACE + "updateDeviceStatus_chuku", info);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	public int updateDevice_ruku(String sn)
	{
		try
		{
			return getSqlSession().update(NAMESPACE + "updateDevice_ruku", sn);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}



	public int updaterepertoryStatus2(DeviceInfo repertoryStatus)
	{
		try
		{
			return getSqlSession().update(NAMESPACE + "updaterepertoryStatus2", repertoryStatus);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}

	}



	public List<DeviceUpgrading> getDeviceUpgradeExport(DeviceUpgrading upgrading)
	{
		try
		{
			return getSqlSession().selectList(NAMESPACE + "selectDeviceUpgradeExport", upgrading);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}

	}
	//导出渠道商选中的设备
	public List<DeviceInfo> excelExportDevice(DeviceInfo deviceIDstr){
		try
		{
			return  getSqlSession().selectList(NAMESPACE + "selectExcelExportDevice", deviceIDstr);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	public int updateVersionBySN(DeviceInfo deviceInfo){
	     try {
			return getSqlSession().update(NAMESPACE+"updateVersionBySN",deviceInfo);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	 }
}
