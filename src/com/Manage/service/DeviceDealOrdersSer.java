package com.Manage.service;


import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.util.LogUtil;
import com.Manage.entity.DeviceDealOrders;
import com.Manage.entity.OrdersInfo;
import com.Manage.entity.common.SearchDTO;



/** * @author  wangbo: * @date 创建时间：2015-5-28 下午6:35:57 * @version 1.0 * @parameter  * @since  * @return  */
@Service
public class DeviceDealOrdersSer extends BaseService {
private Logger logger = LogUtil.getInstance(DeviceDealOrdersSer.class);

	/**
	 * 添加设备订单
	 * @param menuInfo
	 * @return
	 */
	public boolean insertinfo(DeviceDealOrders orders){
		logger.debug("添加设备订单service");
		try {
			int temp=deviceDealOrdersDao.insertinfo(orders);
			if(temp>0){
				logger.debug("添加设备订单成功");
				return true;
			}else{
				logger.debug("添加设备订单失败");
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 更新未完成设备订单
	 * @param ordersID
	 * @return
	 */
	public boolean updateiffinish(DeviceDealOrders orders){
		logger.debug("更新设备订单service");
		try {
			int temp=deviceDealOrdersDao.updateiffinish(orders);
			DeviceDealOrders dd= deviceDealOrdersDao.getbyid(orders.getDeviceDealID());
			deviceInfoDao.updateDeviceOrder(dd.getSN());
			if(temp>0){
				logger.debug("更新设备订单成功");
				return true;
			}else{
				logger.debug("更新设备订单失败");
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 删除
	 * @param ordersID
	 * @return
	 */
	public boolean deldevorder(String ordersID){
		logger.debug("删除设备订单service");
		try {
			int temp=deviceDealOrdersDao.deldevorder(ordersID);
			if(temp>0){
				//删除设备订单，将设备状态改为可使用
				DeviceDealOrders dOrders=deviceDealOrdersDao.getbyid(ordersID);
				deviceInfoDao.updateReturn(dOrders.getSN());
				logger.debug("删除设备订单成功");
				return true;
			}else{
				logger.debug("删除设备订单失败");
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 根据订单Id查询
	 * @param oid
	 * @return
	 */
	public List<DeviceDealOrders> getbyoid(String oid){
	try {
		return  deviceDealOrdersDao.getlistbyoid(oid);
	} catch (Exception e) {
		// TODO: handle exception
		logger.debug(e.getMessage());
		e.printStackTrace();
	}
	return null;
	}

	/**
	 * 分页查询
	 * @param searchDTO
	 * @return
	 */
	public String getpageString(SearchDTO searchDTO){
		  logger.debug("分页server开始");
		  try {
			  String jsonString=deviceDealOrdersDao.getpageString(searchDTO);
			  logger.debug("分页查询结果:"+jsonString);
			  return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	  }

	/**
	 * 根据ID查询设备交易
	 * @param devID
	 * @return
	 */
	public DeviceDealOrders getbyid(String devID){
		logger.debug("删除设备订单service");
		try {
			return deviceDealOrdersDao.getbyid(devID);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 编辑保存
	 * @param ordersID
	 * @return
	 */
	public boolean editsave(DeviceDealOrders dealOrders){
		logger.debug("编辑设备订单service");
		try {
			int temp=deviceDealOrdersDao.editsave(dealOrders);
			if(temp>0){
				logger.debug("编辑设备订单成功");
				return true;
			}else{
				logger.debug("编辑设备订单失败");
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}


	public List<DeviceDealOrders> getCustomerIdDevice(String uid){
		return deviceDealOrdersDao.getCustomerIdDevice(uid);
	}
/**
 * 根据sn查询设备交易表设备的信息
 * @param device
 * @return
 */
	public List<DeviceDealOrders> getdeviceInfodetail(String device){
		return deviceDealOrdersDao.getdeviceInfodetail(device);
	}

	/**
	 * 统计查询
	 * @param deviceDealOrders
	 * @return
	 */
	public Map<String,String> statistics(DeviceDealOrders deviceDealOrders){
		try {
			logger.debug("设备订单统计查询");
			return deviceDealOrdersDao.statistics(deviceDealOrders);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 分页查询今天或历史在线设备
	 * @param searchDTO
	 * @return
	 */
	public String getpageDeviceStatusString(SearchDTO searchDTO){
		  logger.debug("分页查询在线设备开始");
		  try {
			  String jsonString=deviceDealOrdersDao.getpageDeviceStatusString(searchDTO);
			  //logger.debug("分页查询结果:"+jsonString);
			  return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	  }


	/**
	 * 根据订单ID查询
	 * @param oid
	 * @return
	 */
	public List<DeviceDealOrders> getsnbyoid(String oid){
		logger.debug("根据订单ID查询可绑定设备开始");
		  try {
			   return deviceDealOrdersDao.getsnbyoid(oid);
			  //logger.debug("分页查询结果:"+jsonString);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
		}
		  return null;
	}

	public int isReturnForDeviceInfoSer(String sn){
		return deviceDealOrdersDao.isReturnForDeviceInfoDao(sn);
	}

	public DeviceDealOrders getbysn(String sn){
		logger.debug("根据sn查询可绑定设备开始");
		  try {
			   return deviceDealOrdersDao.getbysn(sn);
			  //logger.debug("分页查询结果:"+jsonString);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
		}
		  return null;
	}
	  /**
	   * 查询总订单下的所有有效设备订单sysstatus=1;
	   * @param devicedeal
	   * @return
	   */
	  public int getdealcount(DeviceDealOrders devicedeal){
		  try {
			  return deviceDealOrdersDao.getdealcount(devicedeal);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		  return 0;

	  }
	  /**
	   *
	   * @deprecated 获取设备订单详细信息
	   * @author jiangxuecheng
	   * @date 2015-11-20
	   *
	   * @return
	   */
	  public String getdevicedealinfo(SearchDTO searchDTO){
		  try {
			  return deviceDealOrdersDao.getdevicedealinfo(searchDTO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		  return null;
	  }

		/**
		 * 取消标3星且全部未发货的总订单, 和其他的流量订单/设备订单设置为不可用
		 *
		 * @param orders 注意参数类型使用总订单
		 * @return
		 */
		public boolean cancelYouzanStar3FlowOrder(OrdersInfo orders) {
			logger.debug("设置有赞标3星设备订单为不可用");
			try {
				int temp = deviceDealOrdersDao.cancelYouzanStar3DeviceOrder(orders);
				if (temp > 0) {
					logger.debug("更新订单成功");
					return true;
				} else {
					logger.debug("更新订单失败");
					return false;
				}
			} catch (Exception e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
			return false;
		}
		
		public List<DeviceDealOrders> getdealOrderbyOrderID(DeviceDealOrders dealOrders){
			try
			{
				return deviceDealOrdersDao.getdealOrderbyOrderID(dealOrders);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return null;
		}
		
		public DeviceDealOrders isbindPlan(DeviceDealOrders dealOrders){
			try
			{
				return deviceDealOrdersDao.isbindPlan(dealOrders);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return null;
		}
		
		/**
		 * 取消订单
		 * @param orderID
		 * @return
		 */
		public boolean cancelOrder(String orderID){
			try{
				
				if(deviceDealOrdersDao.cancelOrder(orderID)>0){
					
					return true;
				}
				
			}catch (Exception e) {

				e.printStackTrace();
			}
			return false;
		}
		
	    public List<DeviceDealOrders> getGWOrdData(String cusid){
	    	return deviceDealOrdersDao.getGWOrdData(cusid);
	    }
}
