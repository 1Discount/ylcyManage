package com.Manage.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.WxBindDevice;
import com.Manage.entity.CustomerInfo;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.OrdersInfo;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

@Repository
public class WxBindDeviceDao extends BaseDao {

	private static final String NAMESPACE = WxBindDeviceDao.class.getName() + ".";

	/**
	 * 分页查询列表 返回JSON String
	 * @param searchDTO
	 * @return
	 */
	public String getPage(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPage", "getcount", searchDTO);

			List<WxBindDevice> arr = (List<WxBindDevice>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (WxBindDevice a : arr) {
				// 设置是否可推送普通消息以反馈到前端 48小时内可任意推送普通回复消息
				Date now = new Date();
				// 20151122 因为交由有赞后无法使用自己的服务器配置, 所以得不到最近用户的交互时间, 除了
				// 绑定之前可以确定一个大概的时间. 那目前暂按绑定的两天内可确定任意发消息
				// 目前创建时间和绑定时间相同
//				if ("绑定".equals(a.getBindStatus()) && StringUtils.isNotBlank(a.getUserMsgDate()) &&
//						(now.before(DateUtils.beforeNDate(DateUtils.parseDate(a.getUserMsgDate()), 2))) ) {
//				Date debugDate = DateUtils.parseDate(a.getCreatorDate());
//				logger.debug("debugDate: " + DateUtils.DateToStr(debugDate));
//				logger.debug("debugTargetDate: " + DateUtils.beforeNDateToString(DateUtils.parseDate(a.getCreatorDate()), 2, "yyyy-MM-dd HH:mm:ss"));
				if ("绑定".equals(a.getBindStatus()) &&
						(now.before(DateUtils.beforeNDate(DateUtils.parseDate(a.getCreatorDate()), 2))) ) {
					a.setPushable("是");
				} else {
					a.setPushable("否");
				}

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
	 * 全部列表
	 */
	public List<WxBindDevice> getAll(WxBindDevice info)
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
	public WxBindDevice getById(String id)
	{
		try {
			return (WxBindDevice)getSqlSession().selectOne(NAMESPACE + "queryById", id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}

	/**
	 * 详情查询（根据 sn和openid 或bindStatus 查看是否已存在绑定关系）
	 */
	public WxBindDevice getBySnOpenid(WxBindDevice info)
	{
		try {
			return (WxBindDevice)getSqlSession().selectOne(NAMESPACE + "queryBySnOpenid", info);
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
	public int insertInfo(WxBindDevice info) {
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
	public int updateInfo(WxBindDevice info) {
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
	public int updateInfoSysStatus(WxBindDevice info) {
		try {
			return getSqlSession().update(NAMESPACE + "updateInfoSysStatus", info);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 更新绑定状态
	 * @param info
	 * @return
	 */
	public int updateBindStatus(WxBindDevice info) {
		try {
			return getSqlSession().update(NAMESPACE + "updateBindStatus", info);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

	/**
	 * 微信解除绑定SN
	 * @return
	 */
	public int wxJieBangDeviceDao(String id){
		try {
			return getSqlSession().update(NAMESPACE+"wxJieBangDeviceSql",id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1003, e);
		}

	}

}
