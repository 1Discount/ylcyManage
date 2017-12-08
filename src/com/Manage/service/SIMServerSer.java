package com.Manage.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.exception.BmException;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.SIMInfo;
import com.Manage.entity.SIMServer;
import com.Manage.entity.common.SearchDTO;

@Service
public class SIMServerSer extends BaseService{
	private Logger logger = LogUtil.getInstance(SIMServerSer.class);

	/**
	 * 分页，排序，条件查询
	 *
	 * @param searchDTO
	 * @return
	 */
	public String getPageString(SearchDTO searchDTO) {
		logger.debug("SIMServer分页开始");
		try {
			String jsonString = simServerDao.getPage(searchDTO);
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
		logger.debug("SIMServer分页开始");
		try {
			String jsonString = simServerDao.getPageDeleted(searchDTO);
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
	public List<SIMServer> getAll(String arg){
		logger.debug("查询全部SIMServer开始");
		return simServerDao.getAll(arg);
	}

	/**
	 * 查询SIMServer详情 by ID
	 * @param id
	 * @return
	 */
	public SIMServer getById(String id){
		logger.debug("根据ID查询SIMServer详情开始");
		return simServerDao.getById(id);
	}

	/**
	 * 插入
	 * @param info
	 * @return
	 */
	public boolean insertInfo(SIMServer info) {
		logger.debug("开始执行插入SIMServer信息");
		try {
			if (simServerDao.insertInfo(info) > 0) {
				logger.debug("插入SIMServer成功");
				return true;
			} else {
				logger.debug("插入SIMServer失败");
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
	public boolean updateInfo(SIMServer info) {
		logger.debug("开始执行更新SIMServer信息");
		try {
			if (simServerDao.updateInfo(info) > 0) {
				logger.debug("更新SIMServer成功");
				return true;
			} else {
				logger.debug("更新SIMServer失败");
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
	public boolean updateInfoSysStatus(SIMServer info) {
		logger.debug("开始执行更新SIMServer sysStatus");
		try {
			if (simServerDao.updateInfoSysStatus(info) > 0) {
				logger.debug("更新SIMServer sysStatus成功");
				return true;
			} else {
				logger.debug("更新SIMServer sysStatus失败");
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
	 * 更新SIM卡数量 输入增量的值(可能系负数或正数或零)
	 * @param info
	 * @return
	 */
	public boolean updateSimCount(SIMServer info) {
		logger.debug("开始更新SIMServer卡数量");
		try {
			if (simServerDao.updateSimCount(info) > 0) {
				logger.debug("更新SIMServer卡数量成功");
				return true;
			} else {
				logger.debug("更新SIMServer卡数量失败");
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
	 * 统计SIM卡数量
	 *
	 * @return
	 */
	public String getSimCountString() {
		logger.debug("SIMServer统计SIM数量开始");
		try {
			String jsonString = simServerDao.getSimCountString();
			//logger.debug("查询结果:" + jsonString);
			return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}
	/**
	 * 返回所有的服务器
	 * @return
	 */
	public List<SIMServer> getSIMServerAll(){
		try {
			return simServerDao.getSIMServerAll();
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

}
