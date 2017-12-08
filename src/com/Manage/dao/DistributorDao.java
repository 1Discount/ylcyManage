package com.Manage.dao;

import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;


import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.Distributor;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

@Repository
public class DistributorDao extends BaseDao {

	private static final String NAMESPACE = DistributorDao.class.getName() + ".";

	/**
	 * 分页查询Distributor列表 返回json
	 * @param searchDTO
	 * @return
	 */
	public String getPage(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPage", "getcount", searchDTO);

			List<Distributor> arr = (List<Distributor>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (Distributor a : arr) {
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
	 * 分页查询已删除Distributor 返回json
	 * @param searchDTO
	 * @return
	 */
	public String getPageDeleted(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPageDeleted", "getcountDeleted", searchDTO);

			List<Distributor> arr = (List<Distributor>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (Distributor a : arr) {
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
	 * 全部Distributor列表
	 */
	public List<Distributor> getAll(String arg)
	{
		try {
			return getSqlSession().selectList(NAMESPACE + "queryAll", arg);


		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 详情查询（根据Distributor id）
	 */
	public Distributor getById(String id)
	{
		try {
			return (Distributor)getSqlSession().selectOne(NAMESPACE + "queryById", id);


		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 插入
	 * @param info
	 * @return
	 */
	public int insertInfo(Distributor info) {
		try {
			return getSqlSession().insert(NAMESPACE + "insertInfo", info);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1001, e);
		}
	}

	/**
	 * 更新
	 * @param info
	 * @return
	 */
	public int updateInfo(Distributor info) {
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
	public int updateInfoSysStatus(Distributor info) {
		try {
			return getSqlSession().update(NAMESPACE + "updateInfoSysStatus", info);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
	
	/**
	 * 根据渠道商类别查询渠道商列表
	 * @param type
	 * @return
	 */
	public List<Distributor> getbytype(Distributor type){
		try {
			return getSqlSession().selectList(NAMESPACE + "getbytype", type);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	  public int updateloginInfo(Distributor distributor){
		  try {
			  return getSqlSession().update(NAMESPACE+"updateloginInfo",distributor);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	  }
	  
	  public Distributor getDistributorbydistributorID(Distributor distributor){
			try {
				return getSqlSession().selectOne(NAMESPACE+"getDistributorbydistributorID",distributor);
			} catch (Exception e) {
				// TODO: handle exception
				throw new BmException(Constants.common_errors_1004, e);
			}
		}
  
	 public int resetpassword(Distributor distributor){
		 try {
			return getSqlSession().update(NAMESPACE+"resetpassword",distributor);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	 }
	 
	 public Distributor checkEmail(Distributor distributor){
		 try {
			return getSqlSession().selectOne(NAMESPACE+"checkEmail",distributor);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	 }
	 
	 public Distributor checkPhone(Distributor distributor){
		 try {
			return getSqlSession().selectOne(NAMESPACE+"checkPhone",distributor);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	 }
	 public Distributor checkCompany(Distributor distributor){
		 try {
			return getSqlSession().selectOne(NAMESPACE+"checkCompany",distributor);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	 }
	 public Distributor getdisInofbycompany(Distributor distributor){
		try {
			return getSqlSession().selectOne(NAMESPACE+"getdisInofbycompany",distributor);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		} 
	 }
	 
	 public Distributor checkUser(Distributor distributor){
		 try {
			return getSqlSession().selectOne(NAMESPACE+"checkUser",distributor);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	 }
	 
	 
	 public int updateAnnouncement(Distributor distributor){
		 try {
			 return getSqlSession().update(NAMESPACE+"updateAnnouncement",distributor);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1003, e);
	}
	 }
	 public int checkdispwd(Distributor distributor){
		 try {
			return getSqlSession().selectOne(NAMESPACE+"checkdispwd",distributor);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004,e);
		}
	 }
	 
	 public Distributor checkDistributor(Distributor distributor){
		 try {
			 return getSqlSession().selectOne(NAMESPACE+"checkDistributor",distributor);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004,e);
		}
	 }
	 
		public String getDistributorpage(SearchDTO searchDTO){
			try {
				Page page=queryPage(NAMESPACE,"queryPage2distr","getcount2distr",searchDTO);

				List<Distributor> arr = (List<Distributor>) page.getRows();
				JSONObject object = new JSONObject();
				object.put("success", true);
				object.put("totalRows", page.getTotal());
				object.put("curPage", page.getCurrentPage());
				JSONArray ja = new JSONArray();
				for (Distributor a : arr) {
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
		
	public Integer updatepriceconfig(Distributor distributor){
		try
		{
			return  getSqlSession().update(NAMESPACE+"updatepriceconfig",distributor);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
	
	/**
	 * 用户分配角色
	 * @param map
	 * @return
	 */
	public int usertorole(Distributor adminUserInfo){
		try {
			return  getSqlSession().update(NAMESPACE+"userrole",adminUserInfo);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1003, e);
			// TODO: handle exception
		}
	}
		
}
