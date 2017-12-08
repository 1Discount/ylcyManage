package com.Manage.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.VPNWarning;
import com.Manage.entity.VpnInfo;
import com.Manage.entity.common.SearchDTO;

@Service
public class VpnInfoSer extends BaseService{
	
	private Logger logger = LogUtil.getInstance(VpnInfoSer.class);

	/**
	 * 分页，排序，条件查询
	 *
	 * @param searchDTO
	 * @return
	 */
	public String getPageString(SearchDTO searchDTO) {
		
		logger.debug("VPN分页开始");
		try {
			String jsonString = vpnInfoDao.getPage(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 根据id查询vpn信息
	 * @param vpnid
	 * @return
	 */
	public VpnInfo getVpnInfoById(String vpnid){
		return vpnInfoDao.getVpnInfoById(vpnid);
	}
	
	/**
	 * 插入
	 * @param info
	 * @return
	 */
	public boolean insertInfo(VpnInfo info) {
		logger.debug("开始执行插入VPN信息");
		try {
			if (vpnInfoDao.insertInfo(info) > 0) {
				logger.debug("插入VPN成功");
				return true;
			} else {
				logger.debug("插入VPN失败");
				return false;
			}
		} catch (BmException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;

	}
	
	/**
	 * 更新
	 * @param info
	 * @return
	 */
	public boolean updateInfo(VpnInfo info) {
		logger.debug("开始执行更新VPN信息");
		try {
			if (vpnInfoDao.updateInfo(info) > 0) {
				logger.debug("更新VPN成功");
				return true;
			} else {
				logger.debug("更新VPN失败");
				return false;
			}
		} catch (BmException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;

	}
	
	/**
	 * 根据VPN编号查询VPN信息
	 * @param code
	 * @return
	 */
	public VpnInfo getVpnInfoByCode(String code){
		
		return vpnInfoDao.getVpnInfoByCode(code);
	}
	
	/**
	 * 根据VPN总账号查询集合
	 * @param vpn
	 * @return
	 */
	public List<VpnInfo> getByVpn(String vpn){
		
		return vpnInfoDao.getByVpn(vpn);
	}
	
	/**
	 * 分页，排序，条件查询
	 *
	 * @param searchDTO
	 * @return
	 */
	public String getPageString1(SearchDTO searchDTO) {
		
		logger.debug("VPN预警分页开始");
		try {
			String jsonString = vpnInfoDao.getPage1(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 插入
	 * @param info
	 * @return
	 */
	public boolean insertWarning(VPNWarning info) {
		logger.debug("开始执行插入VPN预警信息");
		try {
			if (vpnInfoDao.insertWarning(info) > 0) {
				logger.debug("插入VPN预警成功");
				return true;
			} else {
				logger.debug("插入VPN预警失败");
				return false;
			}
		} catch (BmException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;

	}
	
	/**
	 * 更新
	 * @param info
	 * @return
	 */
	public boolean updateWarning(VPNWarning info) {
		logger.debug("开始执行更新VPN预警信息");
		try {
			if (vpnInfoDao.updateWarning(info) > 0) {
				logger.debug("更新VPN预警成功");
				return true;
			} else {
				logger.debug("更新VPN预警失败");
				return false;
			}
		} catch (BmException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;

	}
	
	/**
	 * 更新
	 * @param info
	 * @return
	 */
	public boolean deleteWarningById(String vpnWarningID) {
		logger.debug("开始执行删除VPN预警信息");
		try {
			if (vpnInfoDao.deleteWarningById(vpnWarningID) > 0) {
				logger.debug("删除VPN预警成功");
				return true;
			} else {
				logger.debug("删除VPN预警失败");
				return false;
			}
		} catch (BmException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	 /**
	 * 查询VPN
	 * @return
	 */
	public List<VPNWarning> getVPNWarning(){
		return vpnInfoDao.getVPNWarning();
	}
	
	/**
	 * 修改VPNW状态
	 * @param vpnw
	 * @return
	 */
	public int updateVPNStatus(VPNWarning vpnw){
		return vpnInfoDao.updateVPNStatus(vpnw);
	}
}
