package com.Manage.dao;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.DepositRecord;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

@Repository
public class DepositRecordDao extends BaseDao {

	private static final String NAMESPACE = DepositRecordDao.class.getName() + ".";

	/**
	 * 分页查询记录列表 返回JSON String
	 * @param searchDTO
	 * @return
	 */
	public String getPage(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPage", "getcount", searchDTO);

			List<DepositRecord> arr = (List<DepositRecord>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (DepositRecord a : arr) {
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
	 * 全部记录列表
	 */
	public List<DepositRecord> getAll(DepositRecord info)
	{
		try {
			return getSqlSession().selectList(NAMESPACE + "queryAll", info);

		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 详情查询（根据记录id）
	 */
	public DepositRecord getById(String id)
	{
		try {
			return (DepositRecord)getSqlSession().selectOne(NAMESPACE + "queryById", id);


		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	public int updateRefundRemarks(DepositRecord info) {
		try {
			return getSqlSession().update(NAMESPACE + "updateRefundRemarks", info);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1001, e);
		}
	}
	
	public int updateRefundbackEnd(DepositRecord id){
		try {
			return getSqlSession().update(NAMESPACE + "updateRefundbackEnd", id);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1001, e);
		}
	}
	

	/**
	 * 插入
	 * @param info
	 * @return
	 */
	public int insertInfo(DepositRecord info) {
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
	public int updateInfo(DepositRecord info) {
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
	public int updateInfoSysStatus(DepositRecord info) {
		try {
			return getSqlSession().update(NAMESPACE + "updateInfoSysStatus", info);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

}
