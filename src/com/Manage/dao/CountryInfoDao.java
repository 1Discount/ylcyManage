package com.Manage.dao;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.SIMInfo;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;
import com.Manage.service.FlowDealOrdersSer;
import com.Manage.service.SIMInfoSer;

@Repository
public class CountryInfoDao extends BaseDao {
	
	private static final String NAMESPACE = CountryInfoDao.class.getName() + ".";
	@Autowired
	private FlowDealOrdersSer flowDealOrdersSer;
	@Autowired
	private SIMInfoSer simInfoSer;

	/**
	 * 分页查询国家列表 返回json
	 * @param searchDTO
	 * @return
	 */
	public String getPage(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPage", "getcount", searchDTO);

			List<CountryInfo> arr = (List<CountryInfo>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (CountryInfo a : arr) {
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
	 * 分页查询已删除国家 返回json
	 * @param searchDTO
	 * @return
	 */
	public String getPageDeleted(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPageDeleted", "getcountDeleted", searchDTO);

			List<CountryInfo> arr = (List<CountryInfo>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (CountryInfo a : arr) {
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
	 * 全部国家列表
	 */
	public List<CountryInfo> getAll(String arg) 
	{
		try {
			return getSqlSession().selectList(NAMESPACE + "queryAll", arg);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 详情查询（根据国家id）
	 */
	public CountryInfo getById(String id) 
	{
		try {
			return (CountryInfo)getSqlSession().selectOne(NAMESPACE + "queryById", id);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 详情查询（根据国家MCC）
	 */
	public CountryInfo getByMCC(String id) 
	{
		try {
			return (CountryInfo)getSqlSession().selectOne(NAMESPACE + "queryCountryByMcc", id);
			
			
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
	public int insertInfo(CountryInfo info) {
		try {
			return getSqlSession().insert(NAMESPACE + "insertInfo", info);
		} catch (Exception e) {
			// TODO: handle exception
			if (StringUtils.indexOf(e.getMessage(), "Duplicate entry") >= 0) {
				throw new BmException(Constants.common_errors_1005, e);
			} else {
				throw new BmException(Constants.common_errors_1001, e);
			}
		}
	}
	/**
	 * 插入temp
	 * @param info
	 * @return
	 */
	public int insertInfoTemp(@Param("insertStr")String insertStr) {
		try {
		    int count=getSqlSession().insert(NAMESPACE + "insertTemp", insertStr);
		    if(count>0) {
			getSqlSession().commit();
			return 1;
		    }else{
			return 0;
		    }
			
		} catch (Exception e) {
			// TODO: handle exception
			if (StringUtils.indexOf(e.getMessage(), "Duplicate entry") >= 0) {
				throw new BmException(Constants.common_errors_1005, e);
			} else {
				throw new BmException(Constants.common_errors_1001, e);
			}
		}
	}
	
	/**
	 * 更新
	 * @param info
	 * @return
	 */
	public int updateInfo(CountryInfo info) {
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
	public int updateInfoSysStatus(CountryInfo info) {
		try {
			return getSqlSession().update(NAMESPACE + "updateInfoSysStatus", info);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
	/*<!-- 根据国家名称查询国家信息表 -->*/
	public List<CountryInfo> getcountryinfoBycountryname(CountryInfo countryInfo){
		try {
			return getSqlSession().selectList(NAMESPACE+"getcountryinfoBycountryname",countryInfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	/**
	 * 根据国家名称分组
	 * @return
	 */
	public List<CountryInfo> groupBycountryName(){
		try {
			return getSqlSession().selectList(NAMESPACE+"groupBycountryName");
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
		
	}
	/**
	 * 查询国家的数量
	 * @return
	 */
	public int groupCount(){
		try {
				return getSqlSession().selectOne("com.Manage.dao.CountryInfoDao.groupCount");
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}


	/**
	 * 将国家编码转换为国家名称
	 */
	public CountryInfo convertcountry(CountryInfo countryiInfo){
		try {
			return	getSqlSession().selectOne(NAMESPACE+"convertcountry",countryiInfo);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	public CountryInfo selectCountryInfoBycname(CountryInfo info){
		try {
			return getSqlSession().selectOne(NAMESPACE+"selectCountryInfoBycname",info);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	public int updateCountryByName(CountryInfo countryInfo){
		try {
			return getSqlSession().update(NAMESPACE+"updateCountryByName",countryInfo);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
	
	public List<CountryInfo> exportexcel(CountryInfo countryInfo){
		try
		{
			return getSqlSession().selectList(NAMESPACE+"exportexcel",countryInfo);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
	
	
	/**
	 * 修改
	 * @param countryInfo
	 * @return
	 */
	public int updateinfotwo(CountryInfo countryInfo){
		try
		{
			return getSqlSession().update(NAMESPACE+"updateinfotwo",countryInfo);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
	
	/**
	 * 根据大洲对国家分组查询
	 * @return
	 */
	public List<CountryInfo> selectCountryByContinent(){
		try
		{
			return getSqlSession().selectList(NAMESPACE+"selectCountryByContinent");
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
}
