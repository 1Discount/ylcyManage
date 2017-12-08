package com.Manage.dao;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.ExchangeKey;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

@Repository
public class ExchangeKeyDao extends BaseDao {

	private static final String NAMESPACE = ExchangeKeyDao.class.getName() + ".";

	/**
	 * 分页查询列表 返回JSON String
	 * @param searchDTO
	 * @return
	 */
	public String getPage(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPage", "getcount", searchDTO);

			List<ExchangeKey> arr = (List<ExchangeKey>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (ExchangeKey a : arr) {
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
	 * 使用 SearchDTO 分页查询列表 返回列表
	 * @param searchDTO
	 * @return
	 */
	public Page getPageInfo(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPage", "getcount", searchDTO);
			return page;

//			List<ExchangeKey> arr = (List<ExchangeKey>) page.getRows();
//			JSONObject object = new JSONObject();
//			object.put("success", true);
//			object.put("totalRows", page.getTotal());
//			object.put("curPage", page.getCurrentPage());
//			JSONArray ja = new JSONArray();
//			for (ExchangeKey a : arr) {
//				JSONObject obj = JSONObject.fromObject(a);
//				ja.add(obj);
//			}
//			object.put("data", ja);
//			return object.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}

	}

	/**
	 * 全部列表
	 */
	public List<ExchangeKey> getAll(ExchangeKey info)
	{
		try {
			return getSqlSession().selectList(NAMESPACE + "queryAll", info);

		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 详情查询（根据id）
	 */
	public ExchangeKey getById(String id)
	{
		try {
			return (ExchangeKey)getSqlSession().selectOne(NAMESPACE + "queryById", id);
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
	public int insertInfo(ExchangeKey info) {
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
	public int updateInfo(ExchangeKey info) {
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
	public int updateInfoSysStatus(ExchangeKey info) {
		try {
			return getSqlSession().update(NAMESPACE + "updateInfoSysStatus", info);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 更新可用状态 禁止或正常
	 * @param info
	 * @return
	 */
	public int updateInfoStatus(ExchangeKey info) {
		try {
			return getSqlSession().update(NAMESPACE + "updateInfoStatus", info);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 更新短信发送次数，注意使用的系增量更改, 若要减少，使用一个负数相加
	 *
	 * @param info
	 * @return
	 */
	public int updateSendSmsCount(ExchangeKey info) {
		try {
			return getSqlSession().update(NAMESPACE + "updateSendSmsCount", info);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}


}
