package com.Manage.dao;



import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.Distributor;
import com.Manage.entity.Enterprise;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

@SuppressWarnings("rawtypes")
@Repository
public class EnterpriseDao extends BaseDao
{

	private static final String NAMESPACE = EnterpriseDao.class.getName() + ".";


	/**
	 * 分页查询Enterprise列表 返回json
	 * @param searchDTO
	 * @return
	 */
	public String getPage(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPage", "getcount", searchDTO);
			List<Enterprise> arr = (List<Enterprise>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (Enterprise a : arr) {
				String priceConfiguration = a.getPriceConfiguration().replace("{", "").replace("}", "");
				a.setPriceConfiguration(priceConfiguration);
				JSONObject obj = JSONObject.fromObject(a);
				
				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}

	}
	
	/**
	 * 根据ID修改
	 * @param enterprise
	 * @return
	 */
	public int updatebyid(Enterprise enterprise){
		try
		{
			return getSqlSession().update(NAMESPACE+"updatebyid",enterprise);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
	
	
	/**
	 * 获取信息
	 * @param enterprise
	 * @return
	 */
	public Enterprise getenterprise(Enterprise enterprise){
		try
		{
			return getSqlSession().selectOne(NAMESPACE+"getenterprise",enterprise);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	

	/**
	 * 获取全部信息
	 * @param enterprise
	 * @return
	 */
	public List<Enterprise> getAll(){
		try
		{
			return getSqlSession().selectList(NAMESPACE+"getenterprise");
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 新增
	 * @return
	 */
	public int insertInfo(Enterprise enterprise){
		try
		{
			return getSqlSession().insert(NAMESPACE+"insertInfo",enterprise);
		}
		catch (Exception e)
		{
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
}
