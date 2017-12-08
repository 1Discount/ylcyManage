package com.Manage.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Repository;
import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.DateUtils;
import com.Manage.entity.DeviceDealOrders;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.OrdersInfo;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;
import com.Manage.entity.DeviceDealOrders;

/**
 * * @author wangbo: * @date 创建时间：2015-5-28 下午6:33:09 * @version 1.0 * @parameter
 * * @since * @return
 */
@Repository
public class DeviceDealOrdersDao extends BaseDao<DeviceDealOrdersDao> {
	private static final String NAMESPACE = DeviceDealOrdersDao.class.getName()
			+ ".";

	/**
	 * 插入设备订单
	 *
	 * @param ordersInfo
	 * @return
	 */
	public int insertinfo(DeviceDealOrders ordersInfo) {
		try {
			return getSqlSession().update(NAMESPACE + "insertinfo", ordersInfo);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1001, e);
		}
	}

	/**
	 * 更新设备订单完成
	 *
	 * @param orderID
	 * @return
	 */
	public int updateiffinish(DeviceDealOrders ordersInfo) {
		try {
			return getSqlSession().update(NAMESPACE + "updateiffinish",
					ordersInfo);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 删除
	 *
	 * @param devorderID
	 * @return
	 */
	public int deldevorder(String devorderID) {
		try {
			return getSqlSession()
					.update(NAMESPACE + "deldevorder", devorderID);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 根据订单ID删除
	 *
	 * @param orderID
	 * @return
	 */
	public int deldevorderbyoid(String orderID) {
		try {
			return getSqlSession().update(NAMESPACE + "deldevorderbyoid",
					orderID);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 查询列表
	 *
	 * @param oid
	 * @return
	 */
	public List<DeviceDealOrders> getlistbyoid(String customerID) {
		try {
			return getSqlSession().selectList(NAMESPACE + "getbyoid",
					customerID);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 分页查询
	 *
	 * @param searchDTO
	 * @return
	 */
	public String getpageString(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPage", "getcount", searchDTO);
			List<DeviceDealOrders> arr = (List<DeviceDealOrders>) page
					.getRows();
			System.out.println("arr" + arr.size());
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (DeviceDealOrders a : arr) {
				JSONObject obj = JSONObject.fromObject(a);
				Date date = a.getCreatorDate();
				String dString = DateUtils.formatDate(date,
						"yyyy-MM-dd HH:mm:ss");
				obj.remove("creatorDate");
				obj.put("creatorDate", dString);

				Date mdate = a.getModifyDate();
				if (mdate != null) {
					String mString = DateUtils.formatDate(mdate,
							"yyyy-MM-dd HH:mm:ss");
					obj.remove("modifyDate");
					obj.put("modifyDate", mString);
				}

				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 根据ID查询
	 *
	 * @param devID
	 * @return
	 */
	public DeviceDealOrders getbyid(String devID) {
		try {
			return getSqlSession().selectOne(NAMESPACE + "getbyid", devID);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 编辑保存
	 *
	 * @param orderID
	 * @return
	 */
	public int editsave(DeviceDealOrders deviceDealOrders) {
		try {
			return getSqlSession().update(NAMESPACE + "editsave",
					deviceDealOrders);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 查询客户的设备
	 *
	 * @param customerid
	 *            客户ID
	 * @return
	 */
	public List<DeviceDealOrders> getCustomerIdDevice(String customerid) {
		try {
			return getSqlSession().selectList(
					NAMESPACE + "getCustomerIdDevice", customerid);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
			// TODO: handle exception
		}
	}

	/**
	 * 根据sn查询设备交易表设备的信息
	 *
	 * @param customerid
	 * @return
	 */
	public List<DeviceDealOrders> getdeviceInfodetail(String customerid) {
		try {
			return getSqlSession().selectList(NAMESPACE + "getOnedeviceInfos",
					customerid);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
			// TODO: handle exception
		}
	}

	/**
	 * 统计查询
	 *
	 * @param deviceDealOrders
	 * @return
	 */
	public Map<String, String> statistics(DeviceDealOrders deviceDealOrders) {
		try {
			return getSqlSession().selectOne(NAMESPACE + "statistics",
					deviceDealOrders);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
			// TODO: handle exception
		}
	}

	/**
	 * 分页查询今天或历史在线设备
	 *
	 * @param searchDTO
	 * @return
	 */
	public String getpageDeviceStatusString(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPageDeviceStatus",
					"getcountDeviceStatus", searchDTO);
			List<DeviceDealOrders> arr = (List<DeviceDealOrders>) page
					.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (DeviceDealOrders a : arr) {
				JSONObject obj = JSONObject.fromObject(a);
				Date date = a.getCreatorDate();
				String dString = DateUtils.formatDate(date,
						"yyyy-MM-dd HH:mm:ss");
				obj.remove("creatorDate");
				obj.put("creatorDate", dString);

				Date mdate = a.getModifyDate();
				if (mdate != null) {
					String mString = DateUtils.formatDate(mdate,
							"yyyy-MM-dd HH:mm:ss");
					obj.remove("modifyDate");
					obj.put("modifyDate", mString);
				}

				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 根据订单ID查询可绑定的SN
	 *
	 * @param oid
	 * @return
	 */
	public List<DeviceDealOrders> getsnbyoid(String oid) {
		try {
			return getSqlSession().selectList(NAMESPACE + "getsnbyoid", oid);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
			// TODO: handle exception
		}

	}

	public int isReturnForDeviceInfoDao(String sn) {
		return getSqlSession().selectOne(
				NAMESPACE + "isReturnForDeviceInfosql", sn);
	}

	public DeviceDealOrders getbysn(String sn) {
		try {
			return getSqlSession().selectOne(NAMESPACE + "getbysn", sn);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
			// TODO: handle exception
		}

	}

	/**
	 * 查询总订单下的所有有效设备订单sysstatus=1;
	 *
	 * @param devicedeal
	 * @return
	 */
	public int getdealcount(DeviceDealOrders devicedeal) {
		try {
			int count = getSqlSession().selectOne(NAMESPACE + "getdealcount",
					devicedeal);
			return count;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	public String geDeviceBydealtype(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPage1", "getcount1",
					searchDTO);
			List<OrdersInfo> arr = (List<OrdersInfo>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (OrdersInfo a : arr) {
				JSONObject obj = JSONObject.fromObject(a);
				Date date = a.getCreatorDate();
				String dString = DateUtils.formatDate(date,
						"yyyy-MM-dd HH:mm:ss");
				obj.remove("creatorDate");
				obj.put("creatorDate", dString);

				Date mdate = a.getModifyDate();
				if (mdate != null) {
					String mString = DateUtils.formatDate(mdate,
							"yyyy-MM-dd HH:mm:ss");
					obj.remove("modifyDate");
					obj.put("modifyDate", mString);
				}

				ja.add(obj);
				// System.out.println(ja.toString());
			}
			object.put("data", ja);
			return object.toString();
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 *
	 * @deprecated 获取设备订单详细信息
	 * @author jiangxuecheng
	 * @date 2015-11-20
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getdevicedealinfo(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "getdevicedealinfo","getdevicedealinfoCount", searchDTO);
			List<DeviceDealOrders> arr = (List<DeviceDealOrders>)page.getRows();
			System.out.println("arr" + arr.size());
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (DeviceDealOrders a : arr) {
				JSONObject obj = JSONObject.fromObject(a);
				Date date = a.getCreatorDate();
				String dString = DateUtils.formatDate(date,
						"yyyy-MM-dd HH:mm:ss");
				obj.remove("creatorDate");
				obj.put("creatorDate", dString);

				Date mdate = a.getModifyDate();
				if (mdate != null) {
					String mString = DateUtils.formatDate(mdate,
							"yyyy-MM-dd HH:mm:ss");
					obj.remove("modifyDate");
					obj.put("modifyDate", mString);
				}
				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 取消标3星且全部未发货的总订单, 和其他的流量订单/设备订单设置为不可用
	 *
	 * @param ordersInfo 注意参数类型使用总订单
	 * @return
	 */
	public int cancelYouzanStar3DeviceOrder(OrdersInfo ordersInfo) {
		try {
			return getSqlSession().update(NAMESPACE + "cancelYouzanStar3DeviceOrder", ordersInfo);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	public List<DeviceDealOrders> getdealOrderbyOrderID(DeviceDealOrders dealOrders){
		try
		{
			return getSqlSession().selectList(NAMESPACE + "getdealOrderbyOrderID", dealOrders);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	public DeviceDealOrders isbindPlan(DeviceDealOrders dealOrders){
		try
		{
			return getSqlSession().selectOne(NAMESPACE+"isbindPlan",dealOrders);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 取消订单
	 * @param orderID
	 * @return
	 */
	public int cancelOrder(String orderID){
		try
		{
			return getSqlSession().update(NAMESPACE+"cancelOrder",orderID);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

    public List<DeviceDealOrders> getGWOrdData(String cusid){
    	try {
            return getSqlSession().selectList(NAMESPACE + "getGWOrdData",
            		cusid);
        } catch (Exception e) {
            throw new BmException(Constants.common_errors_1004, e);
        }
    }
}
