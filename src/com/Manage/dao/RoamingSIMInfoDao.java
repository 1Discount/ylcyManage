package com.Manage.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;


import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.FlowPlanInfo;
import com.Manage.entity.SIMInfo;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;
import com.Manage.entity.report.SimStatusStatByCountry;

@Repository
public class RoamingSIMInfoDao extends BaseDao {

	private static final String NAMESPACE = RoamingSIMInfoDao.class.getName() + ".";

	/**
	 * 分页查询SIMInfo列表 返回json
	 * @param searchDTO
	 * @return
	 */
	public String getPage(SearchDTO searchDTO, HashMap<String, String> mccNameMap) {
		try {
			Page page = queryPage(NAMESPACE, "queryPage", "getcount", searchDTO);

			List<SIMInfo> arr = (List<SIMInfo>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (SIMInfo a : arr) {
				JSONObject obj = JSONObject.fromObject(a);
				obj.put("countryName", mccNameMap.get(a.getMCC())); // 不做 left join, 手动设置国家名称, 或者设法交由前端去处理
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
	 * 分页查询 直接返回 Page 如果要显示国家名，在上层处理
	 *
	 * @param searchDTO
	 * @return
	 */
    public Page getPageInfo(SearchDTO searchDTO) {
        try {
            return queryPage(NAMESPACE, "queryPage", "getcount", searchDTO);
            //return page;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BmException(Constants.common_errors_1004, e);
        }
    }

	/**
	 * 分页查询已删除SIMInfo 返回json
	 * @param searchDTO
	 * @return
	 */
	public String getPageDeleted(SearchDTO searchDTO, HashMap<String, String> mccNameMap) {
		try {
			Page page = queryPage(NAMESPACE, "queryPageDeleted", "getcountDeleted", searchDTO);

			List<SIMInfo> arr = (List<SIMInfo>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (SIMInfo a : arr) {
				JSONObject obj = JSONObject.fromObject(a);
				obj.put("countryName", mccNameMap.get(a.getMCC())); // 不做 left join, 手动设置国家名称, 或者设法交由前端去处理
				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}

	}

//	/**
//	 * 全部SIMInfo列表
//	 */
//	public List<SIMInfo> getAll(String arg)
//	{
//		try {
//			return getSqlSession().selectList(NAMESPACE + "queryAll", arg);
//
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new BmException(Constants.common_errors_1004, e);
//		}
//	}

	/**
	 * 详情查询（根据SIMInfo id）
	 */
	public SIMInfo getById(String id)
	{
		try {
			return (SIMInfo)getSqlSession().selectOne(NAMESPACE + "queryById", id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 详情查询（根据SIMInfo IMSI）
	 */
	public SIMInfo getByImsi(String imsi)
	{
		try {
			return (SIMInfo)getSqlSession().selectOne(NAMESPACE + "queryByImsi", imsi);
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
	public int insertInfo(SIMInfo info) {
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
	public int updateInfo(SIMInfo info) {
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
	public int updateInfoSysStatus(SIMInfo info) {
		try {
			return getSqlSession().update(NAMESPACE + "updateInfoSysStatus", info);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	  /**
	   * 统计查询
	   * @param SIMInfo info
	   * @return
	   */
	  public Map<String,String> allCount(SIMInfo info){
		  try {
			return getSqlSession().selectOne(NAMESPACE+"allCount",info);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004,e);
			// TODO: handle exception
		}
	  }

	/**
	 * 通过SIM卡状态查询各地区SIM卡数量
	 * @param info
	 * @return
	 */
	public List<SimStatusStatByCountry> getSimStatusStatByCountry(SIMInfo info)
	{
		try {
			return getSqlSession().selectList(NAMESPACE + "querySimStatusStatByCountry", info);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 通过SIM卡状态查询各地区SIM卡数量 根据searchDTO 返回 Page 数据, 在前端再考虑数据的格式
	 * 注意目前实际不分页
	 * @param searchDTO
	 * @return
	 */
	public Page getPageOfSimStatusStatByCountry(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPageOfSimStatusStatByCountry", "queryCountOfSimStatusStatByCountry", searchDTO);

//			List<SIMInfo> arr = (List<SIMInfo>) page.getRows();
//			JSONObject object = new JSONObject();
//			object.put("success", true);
//			object.put("totalRows", page.getTotal());
//			object.put("curPage", page.getCurrentPage());
//			JSONArray ja = new JSONArray();
//			for (SIMInfo a : arr) {
//				JSONObject obj = JSONObject.fromObject(a);
//				obj.put("countryName", mccNameMap.get(a.getMCC())); // 不做 left join, 手动设置国家名称, 或者设法交由前端去处理
//				ja.add(obj);
//			}
//			object.put("data", ja);
			return page; // object.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}

	}

	/**
	 * 通过SIM卡状态查询各运营商SIM卡数量 根据searchDTO 返回 Page 数据, 在前端再考虑数据的格式
	 * 注意目前实际不分页
	 * @param searchDTO
	 * @return
	 */
	public Page getPageOfSimStatusStatByOperator(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPageOfSimStatusStatByOperator", "queryCountOfSimStatusStatByOperator", searchDTO);
			return page;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}

	}

	/**
	 * 通过返回 count 数 查询是否存在此 IMSI
	 *
	 * @param keyValue
	 * @return
	 */
	public int getCountByPrimaryKey(String keyValue) {
		try {
			return getSqlSession().selectOne(NAMESPACE + "getCountByPrimaryKey", keyValue);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * ahming notes: 这个功能系何广超根据漫游卡管理实际情况提出的需求，在导入前手动执行将清除全部漫游卡信息！请只有在清楚问题情况下再继续清除
	 *
	 * @return
	 */
    public int deleteAll(){
        try {
               return getSqlSession().delete(NAMESPACE+"deleteAllBeforeImport");
            } catch (Exception e) {
               e.printStackTrace();
               throw new BmException(Constants.common_errors_1002, e);
           }
    }


    public SIMInfo getInfoBySN(String SN) {
		try {
			return getSqlSession().selectOne(NAMESPACE + "getInfoBySN", SN);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

}
