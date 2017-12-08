package com.Manage.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.exception.BmException;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.CustomerInfo;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.OrdersInfo;
import com.Manage.entity.common.SearchDTO;
/**
 * @author lipeng
 */
@Service
public class CustomerInfoSer extends BaseService{

	private Logger logger = LogUtil.getInstance(CustomerInfoSer.class);

	/**
	 * 查询所有客户信息
	 * @param page
	 * @param pageCount
	 * @return
	 */
	public List<CustomerInfo> getAllCustomerInfo(Integer page, Integer pageCount)
	{
	   return customerInfoDao.getAllCustomerInfo(page,pageCount);
	}

	public String selectAdmin(String admin)
	{
	   return customerInfoDao.selectAdmin(admin);
	}

	/**
	 * 插入客户信息
	 * @param cus
	 * @return
	 */
	public int insertCustomerInfo(CustomerInfo cus){
		return customerInfoDao.insertCustomerInfo(cus);
	}

	/**
	 * 获取需修改的客户信息
	 * @param uid
	 * @return
	 */
	public CustomerInfo getOneCustomerUpdate(String uid){
		return customerInfoDao.getOneCustomerUpdate(uid);
	}

	//查看客戶所有的訂單
//	public List<OrdersInfo> getOrdersInfo(String uid){
//		return customerInfoDao.getOrdersInfo(uid);
//	}


	/**
	 * 获取单个客户信息详情
	 * @param uid
	 * @return
	 */
	public CustomerInfo getOneCustomerDetail(String uid){
		return customerInfoDao.getOneCustomerDetail(uid);
	}
	//根据电话查询
	public int getOneCustomerPhone(String uid){
		return customerInfoDao.getOneCustomerphone(uid);
	}

	public List<CustomerInfo> getSearchCustomerInfoList(CustomerInfo cus){
		return customerInfoDao.getSearchCustomerInfoList(cus);
	}

	/**
	 * 一次过查询已存在手机号的客户列表 在同步有赞订单时使用
	 * @param cus
	 * @return
	 */
	public List<CustomerInfo> getSearchCustomerInfoListInPhoneList(CustomerInfo cus){
		try {
			return customerInfoDao.getSearchCustomerInfoListInPhoneList(cus);
		} catch (BmException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 修改客户信息
	 * @param cus
	 * @return
	 */
	public int updateCustomerInfo(CustomerInfo cus){
		return customerInfoDao.updateCus(cus);
	}
	/**
	 * 根据ID删除客户信息
	 * @param cus
	 * @return
	 */
	public int deleteCustomerInfo(String cus){
		return customerInfoDao.deleteCus(cus);
	}


	/**
	 * 分页，排序，条件查询.
	 * @param searchDTO
	 * @return
	 */
  public String getpageString(SearchDTO searchDTO){
	  logger.debug("分页server开始");
	  try {
//		  String jsonString=adminUserInfoDao.getpage(searchDTO);
		  String jsonString=customerInfoDao.getpage(searchDTO);
		  logger.debug("分页查询结果:"+jsonString);
		  return jsonString;
	} catch (Exception e) {
		logger.debug(e.getMessage());
		// TODO: handle exception
		e.printStackTrace();
		return "";
	}
  }


  /***
   * 根据用户名或者手机号查询.
   * @param p
   * @return
   */
  public List<CustomerInfo> getbynameorphone(String p){
	  logger.debug("分页server开始");
	  try {
		  return customerInfoDao.getbynameorphone(p);
	} catch (Exception e) {
		logger.debug(e.getMessage());
		// TODO: handle exception
		e.printStackTrace();
	}
	 return null;
  }
	/**
	 * 根据phone判断此号码是否已注册
	 * @param cus
	 * @return
	 */
	public int searchCKphone(String phone){
		try {
			return customerInfoDao.searchCKphone(phone);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 客户统计
	 * @param deviceinfo
	 * @return
	 */
	public Map<String,String> statistics(CustomerInfo customer){
		try {
			logger.debug("客户统计查询");
			return customerInfoDao.statistics(customer);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 统计
	 * @param ordersInfo
	 * @return
	 */
	public int statisticscountser(CustomerInfo customer){
		try {
			return customerInfoDao.statisticscountdao(customer);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}

	public CustomerInfo searchOnePersonDataSer(CustomerInfo str){
		return customerInfoDao.searchOnePersonDataDao(str);
	}
	/**
	 * 返回一个客户，根据电话号码查询
	 * @return
	 */
	public List<CustomerInfo> selectOneCustomerinfoPhonetwo(String phone){
		 try {
			return customerInfoDao.selectOneCustomerinfoPhonetwo( phone);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	}
}
