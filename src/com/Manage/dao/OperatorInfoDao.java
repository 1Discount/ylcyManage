package com.Manage.dao;

import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;


import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.OperatorInfo;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;
import com.sun.org.apache.bcel.internal.generic.NEW;

@Repository
public class OperatorInfoDao extends BaseDao {

	private static final String NAMESPACE = OperatorInfoDao.class.getName() + ".";

	/**
	 * 分页查询运营商列表 返回json
	 * @param searchDTO
	 * @return
	 */
	public String getPage(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPage", "getcount", searchDTO);

			List<OperatorInfo> arr = (List<OperatorInfo>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (OperatorInfo a : arr) {
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
	 * 分页查询已删除运营商 返回json
	 * @param searchDTO
	 * @return
	 */
	public String getPageDeleted(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPageDeleted", "getcountDeleted", searchDTO);

			List<OperatorInfo> arr = (List<OperatorInfo>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (OperatorInfo a : arr) {
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
	 * 全部运营商列表
	 */
	public List<OperatorInfo> getAll(String arg)
	{
		try {
			return getSqlSession().selectList(NAMESPACE + "queryAll", arg);


		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 查询全部记录列表 返回 bootstrap-treeview JSON串
	 * @param arg
	 * @return
	 */
	public String getAllTreeviewJson(String arg){
		try {
			List<OperatorInfo> arr = getSqlSession().selectList(NAMESPACE + "queryAll", arg);

			JSONArray result = new JSONArray();

			// 结果已按国家名排序
			String oldCountryName = "";
			JSONArray operatorsNode = new JSONArray();
			for (OperatorInfo a : arr) {
				if (StringUtils.isNotBlank(oldCountryName) && !oldCountryName.equals(a.getCountryName())) {
					JSONObject country = new JSONObject();
					country.put("text", oldCountryName);
					country.put("nodes", operatorsNode);
					country.put("selectable", false);
					result.add(country);

					operatorsNode.clear();
				}

				JSONObject op =new JSONObject();
				op.put("text", a.getOperatorName());
				operatorsNode.add(op);
				oldCountryName = a.getCountryName();
			}
			// 补上最后的, operatorsNode肯定非空且必定系新的国家
			JSONObject country = new JSONObject();
			country.put("text", oldCountryName);
			country.put("nodes", operatorsNode);
			country.put("selectable", false);
			result.add(country);

			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 详情查询（根据运营商id）
	 */
	public OperatorInfo getById(String id)
	{
		try {
			return (OperatorInfo)getSqlSession().selectOne(NAMESPACE + "queryById", id);


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
	public int insertInfo(OperatorInfo info) {
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
	public int updateInfo(OperatorInfo info) {
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
	public int updateInfoSysStatus(OperatorInfo info) {
		try {
			return getSqlSession().update(NAMESPACE + "updateInfoSysStatus", info);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

}
