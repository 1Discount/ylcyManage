package com.Manage.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.exception.BmException;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.OperatorInfo;
import com.Manage.entity.common.SearchDTO;

@Service
public class OperatorInfoSer extends BaseService{
	private Logger logger = LogUtil.getInstance(OperatorInfoSer.class);

	/**
	 * 分页，排序，条件查询
	 *
	 * @param searchDTO
	 * @return
	 */
	public String getPageString(SearchDTO searchDTO) {
		logger.debug("运营商分页开始");
		try {
			String jsonString = operatorInfoDao.getPage(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 分页，排序，条件查询 已删除记录
	 *
	 * @param searchDTO
	 * @return
	 */
	public String getPageDeletedString(SearchDTO searchDTO) {
		logger.debug("运营商分页开始");
		try {
			String jsonString = operatorInfoDao.getPageDeleted(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 查询全部记录列表
	 * @param arg
	 * @return
	 */
	public List<OperatorInfo> getAll(String arg){
		logger.debug("查询全部运营商开始");
		return operatorInfoDao.getAll(arg);
	}

	/**
	 * 查询全部记录列表 返回 bootstrap-treeview JSON串
	 * @param arg
	 * @return
	 */
	public String getAllTreeviewJson(String arg){
		logger.debug("查询全部运营商treeview json数据开始");
		try {
			return operatorInfoDao.getAllTreeviewJson(arg);
		} catch (Exception e) {
			logger.debug(e.getMessage());

			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 查询运营商详情 by ID
	 * @param id
	 * @return
	 */
	public OperatorInfo getById(String id){
		logger.debug("根据ID查询运营商详情开始");
		return operatorInfoDao.getById(id);
	}

	/**
	 * 插入
	 * @param info
	 * @return
	 */
	public boolean insertInfo(OperatorInfo info) {
		logger.debug("开始执行插入运营商信息");
		try {
			if (operatorInfoDao.insertInfo(info) > 0) {
				logger.debug("插入运营商成功");
				return true;
			} else {
				logger.debug("插入运营商失败");
				return false;
			}
		} catch (BmException e) {
			// TODO: handle exception
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
	public boolean updateInfo(OperatorInfo info) {
		logger.debug("开始执行更新运营商信息");
		try {
			if (operatorInfoDao.updateInfo(info) > 0) {
				logger.debug("更新运营商成功");
				return true;
			} else {
				logger.debug("更新运营商失败");
				return false;
			}
		} catch (BmException e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * 更新 sysStatus
	 * @param info
	 * @return
	 */
	public boolean updateInfoSysStatus(OperatorInfo info) {
		logger.debug("开始执行更新运营商信息sysStatus");
		try {
			if (operatorInfoDao.updateInfoSysStatus(info) > 0) {
				logger.debug("更新运营商sysStatus成功");
				return true;
			} else {
				logger.debug("更新运营商sysStatus失败");
				return false;
			}
		} catch (BmException e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;

	}
}
