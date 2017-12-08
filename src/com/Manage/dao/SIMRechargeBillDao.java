package com.Manage.dao;

import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;


import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.OrdersInfo;
import com.Manage.entity.SIMRechargeBill;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

@Repository
public class SIMRechargeBillDao extends BaseDao {
	
	private static final String NAMESPACE = SIMRechargeBillDao.class.getName() + ".";

	/**
	 * 分页查询SIM卡充值记录列表 返回json
	 * @param searchDTO
	 * @return
	 */
	public String getPage(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPage", "getcount", searchDTO);

			List<SIMRechargeBill> arr = (List<SIMRechargeBill>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (SIMRechargeBill a : arr) {
				JSONObject obj = JSONObject.fromObject(a);
				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}

	}
	
	/**
	 * 分页查询已删除SIM卡充值记录 返回json
	 * @param searchDTO
	 * @return
	 */
	public String getPageDeleted(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPageDeleted", "getcountDeleted", searchDTO);

			List<SIMRechargeBill> arr = (List<SIMRechargeBill>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (SIMRechargeBill a : arr) {
				JSONObject obj = JSONObject.fromObject(a);
				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}

	}
	
	/**
	 * 全部SIM卡充值记录列表
	 */
	public List<SIMRechargeBill> getAll(String arg) 
	{
		try {
			return getSqlSession().selectList(NAMESPACE + "queryAll", arg);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 详情查询（根据SIM卡充值记录id）
	 */
	public SIMRechargeBill getById(String id) 
	{
		try {
			return (SIMRechargeBill)getSqlSession().selectOne(NAMESPACE + "queryById", id);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 插入 插入充值记录时, 同时要更新SIM卡余额
	 * 
	 * 目前采取的策略未使用事务, 先更新余额, 再插入记录. 这样做的原因部分系因为这种
	 * 充值记录系先实际充值再作记录, 所以先更新余额尚可. 若更新余额失败, 则不继续
	 * 插入充值记录, 然后用户需要重试. ---> 考虑回退的可操作性, 先插入充值记录, 再
	 * 更新余额
	 * 
	 * 关于SIM卡余额和剩余流量, 尚需要更细节的设计和实现. 在人工录入和通过运营商接口查询系
	 * 二选一需要作出决定的选择. 或者可以考虑先采取人工录入, 逐渐再完善运营商接口查询.
	 * 
	 * @param info
	 * @return
	 */
	public int insertInfo(SIMRechargeBill info) {
//		int updateCardBlanceResult = 0;
		int firstResult = 0;
		try {			
//			try {
//				updateCardBlanceResult = getSqlSession().update(NAMESPACE + "updateRechargeBillThenUpdateSimInfo", info);
//			} catch (Exception e) {
//				// TODO: handle exception
//				updateCardBlanceResult = 0;
//				throw new BmException(Constants.common_errors_1003, e);
//			}
//			
//			if (updateCardBlanceResult > 0) {
//				return getSqlSession().insert(NAMESPACE + "insertInfo", info);
//			} else {
//				return 0;
//			}
			
			firstResult = getSqlSession().insert(NAMESPACE + "insertInfo", info);;
		} catch (Exception e) {
			// TODO: handle exception
//			if (updateCardBlanceResult > 0) {
//				// 需要机制去回退重置已更新的余额信息 或 重试补上充值记录
//			}
			
			firstResult = 0;
			throw new BmException(Constants.common_errors_1001, e);
		}
		
		if (firstResult > 0) {
			int updateCardBlanceResult = 0;
			try {
				updateCardBlanceResult = getSqlSession().update(NAMESPACE + "updateRechargeBillThenUpdateSimInfo", info);
			} catch (Exception e) {
				// TODO: handle exception
				updateCardBlanceResult = 0;
				throw new BmException(Constants.common_errors_1003, e);
			}
			
			if (updateCardBlanceResult <= 0) {
				// 需要机制去回退已插入的充值记录 --> 测试显示这里不再继续
				try {
					getSqlSession().delete(NAMESPACE + "deleteInfo", info);
				} catch (Exception e) {
					// TODO: handle exception
					// 是否需要定义一种新的错误状态?
					throw new BmException(Constants.common_errors_1002, e);
				}
			}
		} 
//		else {
//			// 不需要处理
//		}
		
		return firstResult;
	}
	
	/**
	 * 更新 更新充值记录时, 同时要更新SIM卡余额
	 * 注意如果更新余额失败需要提示用户重试更新余额
	 * 
	 * @see {@link #insertInfo(SIMRechargeBill info)}
	 * 
	 * @param info
	 * @return
	 */
	public int updateInfo(SIMRechargeBill info) {
		try {
			return getSqlSession().update(NAMESPACE + "updateInfo", info);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
	
	/**
	 * 更新 sysStatus
	 * @param info
	 * @return
	 */
	public int updateInfoSysStatus(SIMRechargeBill info) {
		try {
			return getSqlSession().update(NAMESPACE + "updateInfoSysStatus", info);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
	
	/**
	 * 统计充值SIM卡数
	 * 
	 * @param info
	 * @return
	 */
	public int countRechargedSim(SIMRechargeBill info) {
		try {
			return getSqlSession().selectOne(NAMESPACE + "countRechargedBill", info); // countRechargedSim
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 统计充值笔数
	 * 
	 * @param info
	 * @return
	 */
	public int countRechargedBill(SIMRechargeBill info) {
		try {
			return getSqlSession().selectOne(NAMESPACE + "countRechargedBill", info);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 合计充值总金额
	 * 
	 * @param info
	 * @return
	 */
	public double sumRechargedBill(SIMRechargeBill info) {
		try {
			return getSqlSession().selectOne(NAMESPACE + "sumRechargedBill", info);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
}
