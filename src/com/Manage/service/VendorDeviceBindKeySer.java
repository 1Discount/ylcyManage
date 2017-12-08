package com.Manage.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.exception.BmException;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.VendorDeviceBindKey;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;


@Service
public class VendorDeviceBindKeySer extends BaseService{
	private Logger logger = LogUtil.getInstance(VendorDeviceBindKeySer.class);

	/**
	 * 分页，排序，条件查询
	 *
	 * @param searchDTO
	 * @return
	 */
	public String getPageString(SearchDTO searchDTO) {
		logger.debug("绑定码分页开始");
		try {
			String jsonString = VendorDeviceBindKeyDao.getPage(searchDTO);
			return jsonString;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.debug(e.getMessage());
			return "";
		}
	}

	/**
	 * 分页，排序，条件查询 返回列表信息
	 *
	 * @param searchDTO
	 * @return
	 */
	public Page getPageInfo(SearchDTO searchDTO) {
		logger.debug("绑定码分页信息开始");
		try {
			return VendorDeviceBindKeyDao.getPageInfo(searchDTO);
			//return jsonString;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.debug(e.getMessage());
			return null;
		}
	}

	/**
	 * 查询全部记录列表
	 * @param arg
	 * @return
	 */
	public List<VendorDeviceBindKey> getAll(VendorDeviceBindKey info){
		logger.debug("绑定码查询全部记录");
		try {
			return VendorDeviceBindKeyDao.getAll(info);
		} catch (BmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
		return null;
	}

	/**
	 * 查询详情 by ID
	 * @param id
	 * @return
	 */
	public VendorDeviceBindKey getById(String id){
		logger.debug("绑定码查询详情By ID");
		try {
			return VendorDeviceBindKeyDao.getById(id);
		} catch (BmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
		return null;
	}

	/**
	 * 插入
	 *
	 * @param info
	 * @return String // old: boolean
	 */
	public boolean insertInfo(VendorDeviceBindKey info) {
		logger.debug("绑定码插入");
		try {
			if (VendorDeviceBindKeyDao.insertInfo(info) > 0) {
				return true;
			} else {
				return false;
			}
		} catch (BmException e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
		return false;
	}

	/**
	 * 更新
	 * @param info
	 * @return
	 */
	public boolean updateInfo(VendorDeviceBindKey info) {
		logger.debug("绑定码更新");
		try {
			if (VendorDeviceBindKeyDao.updateInfo(info) > 0) {
				return true;
			} else {
				return false;
			}
		} catch (BmException e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
		return false;
	}

	/**
	 * 更新 sysStatus
	 * @param info
	 * @return
	 */
	public boolean updateInfoSysStatus(VendorDeviceBindKey info) {
		logger.debug("绑定码更新sysStatus");
		try {
			if (VendorDeviceBindKeyDao.updateInfoSysStatus(info) > 0) {
				return true;
			} else {
				return false;
			}
		} catch (BmException e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
		return false;
	}

	/**
	 * 更新可用状态 禁止或正常
	 * @param info
	 * @return
	 */
	public boolean updateInfoStatus(VendorDeviceBindKey info) {
		logger.debug("绑定码更新可用状态：" + info.getStatus());
		try {
			if (VendorDeviceBindKeyDao.updateInfoStatus(info) > 0) {
				return true;
			} else {
				return false;
			}
		} catch (BmException e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
		return false;
	}

}