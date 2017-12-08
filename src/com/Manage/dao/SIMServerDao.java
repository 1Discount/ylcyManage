package com.Manage.dao;

import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;


import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.SIMInfo;
import com.Manage.entity.SIMServer;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

@Repository
public class SIMServerDao extends BaseDao {

	private static final String NAMESPACE = SIMServerDao.class.getName() + ".";

	/**
	 * 分页查询SIMServer列表 返回json
	 * @param searchDTO
	 * @return
	 */
	public String getPage(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPage", "getcount", searchDTO);

			List<SIMServer> arr = (List<SIMServer>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (SIMServer a : arr) {
				
				Integer simCount = getSqlSession().selectOne("getSIMInfoCount", a.getIP());
				Integer availableSIMCount = getSqlSession().selectOne("getAvailableSIMInfoCount", a.getIP());
				a.setSIMCount(simCount);
				a.setAvailableSIMCount(availableSIMCount);
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
	 * 分页查询已删除SIMServer 返回json
	 * @param searchDTO
	 * @return
	 */
	public String getPageDeleted(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPageDeleted", "getcountDeleted", searchDTO);

			List<SIMServer> arr = (List<SIMServer>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (SIMServer a : arr) {
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
	 * 全部SIMServer列表
	 */
	public List<SIMServer> getAll(String arg)
	{
		try {
			return getSqlSession().selectList(NAMESPACE + "queryAll", arg);


		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 详情查询（根据SIMServer id）
	 */
	public SIMServer getById(String id)
	{
		try {
			return (SIMServer)getSqlSession().selectOne(NAMESPACE + "queryById", id);


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
	public int insertInfo(SIMServer info) {
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
	public int updateInfo(SIMServer info) {
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
	public int updateInfoSysStatus(SIMServer info) {
		try {
			return getSqlSession().update(NAMESPACE + "updateInfoSysStatus", info);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 更新SIM卡数量 输入增量的值(可能系负数或正数或零)
	 * @param info
	 * @return
	 */
	public int updateSimCount(SIMServer info) {
		try {
			return getSqlSession().update(NAMESPACE + "updateSimCount", info);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 统计SIM卡数量 返回json
	 *
	 * @return
	 */
	public String getSimCountString() {
		try {
			// 不使用 select 直接使用 update 去更新, 然后getAll读取更新后的记录
			// 或更新失败则返回空结果
			// List<SIMServer> arr = getSqlSession().selectList(NAMESPACE + "getSimCount");
			if (getSqlSession().update(NAMESPACE + "updateAllSimCount") > 0) {
				List<SIMServer> arr =  getSqlSession().selectList(NAMESPACE + "queryAll", "");

				JSONObject object = new JSONObject();
				object.put("success", true);
				object.put("totalRows", arr.size());
				object.put("curPage", 1); // 仅一页全部返回
				JSONArray ja = new JSONArray();
				for (SIMServer a : arr) {
					JSONObject obj = JSONObject.fromObject(a);
					ja.add(obj);
				}
				object.put("data", ja);
				return object.toString();
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}

	}
	/**
	 * 返回所有的服务器
	 * @return
	 */
	public List<SIMServer> getSIMServerAll(){
		try {
		   return	getSqlSession().selectList(NAMESPACE+"getSIMServerAll");
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

}
