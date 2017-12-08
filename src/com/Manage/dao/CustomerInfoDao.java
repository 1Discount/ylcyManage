package com.Manage.dao;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.CustomerInfo;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.OrdersInfo;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

@Repository
public class CustomerInfoDao extends BaseDao{

	private static final String NAMESPACE = CustomerInfoDao.class.getName() + ".";

	/**
	 * 分页查询返回json
	 * @param searchDTO
	 * @return
	 */
	public String getpage(SearchDTO searchDTO){
		try {
			Page page=queryPage(NAMESPACE,"queryPage","getcount",searchDTO);

			List<CustomerInfo> arr=(List<CustomerInfo>)page.getRows();
			JSONObject object=new JSONObject();
			object.put("success",true);
			object.put("totalRows",page.getTotal());
			object.put("curPage",page.getCurrentPage());
			JSONArray ja=new JSONArray();
			for(CustomerInfo a :arr){
				JSONObject obj=JSONObject.fromObject(a);
//                object.put("creatorDate", DateUtils.formatDateTime((Date)obj.get("creatorDate")));
				ja.add(obj);
			}
			object.put("data",ja);
			return object.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}

	}

	/**
	 * 查询所有客户信息
	 * @param page
	 * @param pageCount
	 * @return
	 */
	public List<CustomerInfo> getAllCustomerInfo(Integer page, Integer pageCount)
	{
		try {
			return getSqlSession().selectList(NAMESPACE+"querycustomerlist",page);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	public String selectAdmin(String admin)
	{
		try {
    		return getSqlSession().selectOne(NAMESPACE+"getUserName",admin);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 插入客户信息数据
	 * @param cus
	 * @return
	 */
	public int insertCustomerInfo(CustomerInfo cus)
	{
		try {
			return getSqlSession().insert(NAMESPACE+"insertCustomer",cus);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1001, e);
		}
	}

	/**
	 * 获取需修改的数据
	 * @param uid
	 * @return
	 */
	public CustomerInfo getOneCustomerUpdate(String uid)
	{
		try {

			return getSqlSession().selectOne(NAMESPACE+"updateCustomer", uid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1003,e);
		}
	}


	//查看所有訂單
//	public List<OrdersInfo> getOrdersInfo(String uid)
//	{
//		try {
//			return getSqlSession().selectList(NAMESPACE+"test2", uid);
//		} catch (Exception e) {
//           e.printStackTrace();
//           throw new BmException(Constants.common_errors_1004,e);
//		}
//	}

	/**
	 * 获取单个客户信息详情
	 * @param uid
	 * @return
	 */
	public CustomerInfo getOneCustomerDetail(String uid)
	{
		try {
			return getSqlSession().selectOne(NAMESPACE+"selectOneCustomerinfo", uid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004,e);
		}
	}

	public int getOneCustomerphone(String uid)//根据电话号码进行查询
	{
		try {
			return getSqlSession().selectOne(NAMESPACE+"selectOneCustomerinfoPhone", uid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004,e);
		}
	}

	public List<CustomerInfo> getSearchCustomerInfoList(CustomerInfo cus)
	{
		try {
		return getSqlSession().selectList(NAMESPACE+"searchCustomerInfoList", cus);
	} catch (Exception e) {
		e.printStackTrace();
		throw new BmException(Constants.common_errors_1004,e);
	}
	}

	/**
	 * 修改客户信息
	 * @param id
	 * @return
	 */
	public int updateCus(CustomerInfo id)
	{
		try {
			return getSqlSession().update(NAMESPACE+"ToupdateCustomer",id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1003,e);
		}
	}
	/**
	 * 删除客户信息
	 * @param cus
	 * @return
	 */
	public int deleteCus(String cus)
	{
		try {
			return getSqlSession().update(NAMESPACE+"deleteCustomerInfo", cus);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1002,e);
		}
	}

	/**
	 * 根据用户名或手机号搜索
	 * @param p
	 * @return
	 */
	public List<CustomerInfo> getbynameorphone(String p){
		try {
			return  getSqlSession().selectList(NAMESPACE+"selectbynameorphone",p);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
			// TODO: handle exception
		}
	}

	/**
	 * 判断此号码是否已注册
	 * @param cus
	 * @return
	 */
	public int searchCKphone(String phone)
	{
		try {
			return getSqlSession().selectOne(NAMESPACE+"selectOneCustomerinfoPhone",phone);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1001, e);
		}
	}
	  /**
	   * 客户统计index.jsp
	   * @param CustomerInfo
	   * @return
	   */
	  public Map<String,String> statistics(CustomerInfo customer){
		  try {
			return getSqlSession().selectOne(NAMESPACE+"statistics",customer);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004,e);
			// TODO: handle exception
		}
	  }

		/**
		 * 统计
		 * @param CustomerInfo
		 * @return
		 */
		public int statisticscountdao(CustomerInfo customer){
			try {
				return getSqlSession().selectOne(NAMESPACE+"statisticscount",customer);
			} catch (Exception e) {
				e.printStackTrace();
				throw new BmException(Constants.common_errors_1004, e);
			}
		}

		public int getcountcus(CustomerInfo cus){
			return getSqlSession().selectOne(NAMESPACE+"getcountcus",cus);
		}

		public CustomerInfo searchOnePersonDataDao(CustomerInfo str){
			return getSqlSession().selectOne(NAMESPACE+"searchOnePersonDatasql",str);
		}
		/**
		 * 返回一个客户，根据电话号码查询
		 * @return
		 */
		public List<CustomerInfo> selectOneCustomerinfoPhonetwo(String phone){
			try {
				return getSqlSession().selectList(NAMESPACE+"selectOneCustomerinfoPhonetwo",phone);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new BmException(Constants.common_errors_1004, e);
			}

		}
		public List<CustomerInfo> getCusByID(String cusIDs){
			try {
				return getSqlSession().selectList(NAMESPACE+"getCusByID",cusIDs);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new BmException(Constants.common_errors_1004, e);
			}

		}

	/**
	 * 一次过查询已存在手机号的客户列表 在同步有赞订单时使用
	 *
	 * @param cus
	 * @return
	 */
	public List<CustomerInfo> getSearchCustomerInfoListInPhoneList(CustomerInfo cus) {
		try {
			return getSqlSession().selectList(NAMESPACE + "searchCustomerInfoListInPhoneList", cus);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
}
