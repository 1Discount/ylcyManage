package com.Manage.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.exception.BmException;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.WxBindDevice;
import com.Manage.entity.common.SearchDTO;


@Service
public class WxBindDeviceSer extends BaseService{
	private Logger logger = LogUtil.getInstance(WxBindDeviceSer.class);

	/**
	 * 分页，排序，条件查询
	 *
	 * @param searchDTO
	 * @return
	 */
	public String getPageString(SearchDTO searchDTO) {
		logger.debug("绑定关系分页开始");
		try {
			String jsonString = wxBindDeviceDao.getPage(searchDTO);
			return jsonString;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.debug(e.getMessage());
			return "";
		}
	}

	/**
	 * 查询全部记录列表
	 * @param arg
	 * @return
	 */
	public List<WxBindDevice> getAll(WxBindDevice info){
		logger.debug("绑定关系查询全部记录");
		try {
			return wxBindDeviceDao.getAll(info);
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
	public WxBindDevice getById(String id){
		logger.debug("绑定关系查询详情By ID");
		try {
			return wxBindDeviceDao.getById(id);
		} catch (BmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
		return null;
	}

	/**
	 * 详情查询（根据 sn和openid 或bindStatus 查看是否已存在绑定关系）
	 */
	public WxBindDevice getBySnOpenid(WxBindDevice info){
		logger.debug("绑定关系按条件查询");
		try {
			return wxBindDeviceDao.getBySnOpenid(info);
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
	public boolean insertInfo(WxBindDevice info) {
		logger.debug("绑定关系插入");
		try {
			if (wxBindDeviceDao.insertInfo(info) > 0) {
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
	public boolean updateInfo(WxBindDevice info) {
		logger.debug("绑定关系更新");
		try {
			if (wxBindDeviceDao.updateInfo(info) > 0) {
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
	public boolean updateInfoSysStatus(WxBindDevice info) {
		logger.debug("绑定关系更新sysStatus");
		try {
			if (wxBindDeviceDao.updateInfoSysStatus(info) > 0) {
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
	 * 更新绑定状态
	 * @param info
	 * @return
	 */
	public boolean updateBindStatus(WxBindDevice info) {
		logger.debug("绑定关系更新状态");
		try {
			if (wxBindDeviceDao.updateBindStatus(info) > 0) {
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
	 * 解绑
	 */
	public int wxJieBangDevice(String id){
		return wxBindDeviceDao.wxJieBangDeviceDao(id);
	}

}