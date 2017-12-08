package com.Manage.dao;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.VPNWarning;
import com.Manage.entity.VpnInfo;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

@Repository
public class VpnInfoDao extends BaseDao<VpnInfo>{

	private static final String NAMESPACE = VpnInfoDao.class.getName() + ".";
	
	/**
	 * 分页查询VPN列表 返回json
	 * @param searchDTO
	 * @param mccNameMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getPage(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPage", "getcount", searchDTO);
			List<VpnInfo> arr = (List<VpnInfo>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (VpnInfo a : arr) {
				JSONObject obj = JSONObject.fromObject(a);

				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		}catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 根据id查询vpn信息
	 * @param vpnid
	 * @return
	 */
	public VpnInfo getVpnInfoById(String vpnid){
		try {
			
			return getSqlSession().selectOne(NAMESPACE + "selectByPrimaryKey", vpnid);
			
		}catch (Exception e) {
			
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 插入
	 * @param info
	 * @return
	 */
	public int insertInfo(VpnInfo info) {
		try {
			return getSqlSession().insert(NAMESPACE + "insertSelective", info);
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
	public int updateInfo(VpnInfo info) {
		try {
			return getSqlSession().update(NAMESPACE + "updateByPrimaryKeySelective", info);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
	
	/**
	 * 根据VPN编号查询VPN信息
	 * @param code
	 * @return
	 */
	public VpnInfo getVpnInfoByCode(String code){

		try {
		
			return getSqlSession().selectOne(NAMESPACE + "getVpnInfoByCode", code);
		
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 根据VPN总账号查询集合
	 * @param vpn
	 * @return
	 */
	public List<VpnInfo> getByVpn(String vpn){
		
		try {
			
			return getSqlSession().selectList(NAMESPACE + "selectByVpn", vpn);
		
		} catch (Exception e) {
			
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 分页查询VPN列表 返回json
	 * @param searchDTO
	 * @param mccNameMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getPage1(SearchDTO searchDTO) {
		try {
			Page page = queryPage(NAMESPACE, "queryPage1", "getcount1", searchDTO);
			List<VPNWarning> arr = (List<VPNWarning>) page.getRows();
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", page.getTotal());
			object.put("curPage", page.getCurrentPage());
			JSONArray ja = new JSONArray();
			for (VPNWarning a : arr) {
				JSONObject obj = JSONObject.fromObject(a);

				ja.add(obj);
			}
			object.put("data", ja);
			return object.toString();
		}catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 插入
	 * @param info
	 * @return
	 */
	public int insertWarning(VPNWarning info) {
		try {
			return getSqlSession().insert(NAMESPACE + "insertWarningSelective", info);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1001, e);
		}
	}
	
	/**
	 * 更新
	 * @param info
	 * @return
	 */
	public int updateWarning(VPNWarning info) {
		try {
			return getSqlSession().update(NAMESPACE + "updateWarningByPrimaryKeySelective", info);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
	public List<VPNWarning> getVPNWarning(){
		try {
			return getSqlSession().selectList(NAMESPACE + "getVPNWarning");
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 删除
	 * @param info
	 * @return
	 */
	public int deleteWarningById(String vpnWarningID) {
		try {
			return getSqlSession().update(NAMESPACE + "deleteWarningByID", vpnWarningID);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
	 
	 /*		
	 * 修改状态
	 * @param vpnw
	 * @return
	 */
	public int updateVPNStatus(VPNWarning vpnw){
		try {
			return getSqlSession().update(NAMESPACE + "updateVPNStatus",vpnw);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1003, e);
		}
	}

}
