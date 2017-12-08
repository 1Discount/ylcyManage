package com.Manage.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.exception.BmException;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.OrdersInfo;
import com.Manage.entity.SIMRechargeBill;
import com.Manage.entity.common.SearchDTO;

@Service
public class SIMRechargeBillSer extends BaseService{
	private Logger logger = LogUtil.getInstance(SIMRechargeBillSer.class);

	/**
	 * 分页，排序，条件查询
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getPageString(SearchDTO searchDTO) {
		logger.debug("SIM卡充值记录分页开始");
		try {
			String jsonString = simRechargeBillDao.getPage(searchDTO);
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
		logger.debug("SIM卡充值记录分页开始");
		try {
			String jsonString = simRechargeBillDao.getPageDeleted(searchDTO);
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
	public List<SIMRechargeBill> getAll(String arg){
		logger.debug("查询全部SIM卡充值记录开始");
		return simRechargeBillDao.getAll(arg);
	}
	
	/**
	 * 查询SIM卡充值记录详情 by ID
	 * @param id
	 * @return
	 */
	public SIMRechargeBill getById(String id){
		logger.debug("根据ID查询SIM卡充值记录详情开始");
		return simRechargeBillDao.getById(id);
	}

	/**
	 * 插入
	 * @param info
	 * @return
	 */
	public boolean insertInfo(SIMRechargeBill info) {
		logger.debug("开始执行插入SIM卡充值记录信息");
		try {
			if (simRechargeBillDao.insertInfo(info) > 0) {
				logger.debug("插入SIM卡充值记录成功");
				return true;
			} else {
				logger.debug("插入SIM卡充值记录失败");
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
	public boolean updateInfo(SIMRechargeBill info) {
		logger.debug("开始执行更新SIM卡充值记录信息");
		try {
			if (simRechargeBillDao.updateInfo(info) > 0) {
				logger.debug("更新SIM卡充值记录成功");
				return true;
			} else {
				logger.debug("更新SIM卡充值记录失败");
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
	public boolean updateInfoSysStatus(SIMRechargeBill info) {
		logger.debug("开始执行更新SIM卡充值记录信息sysStatus");
		try {
			if (simRechargeBillDao.updateInfoSysStatus(info) > 0) {
				logger.debug("更新SIM卡充值记录sysStatus成功");
				return true;
			} else {
				logger.debug("更新SIM卡充值记录sysStatus失败");
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
	 * 统计充值SIM卡数
	 * @param info
	 * @return
	 */
	public int countRechargedSim(SIMRechargeBill info){
		try {
			return simRechargeBillDao.countRechargedSim(info);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 统计充值笔数
	 * @param info
	 * @return
	 */
	public int countRechargedBill(SIMRechargeBill info){
		try {
			return simRechargeBillDao.countRechargedBill(info);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 合计充值总金额
	 * @param info
	 * @return
	 */
	public double sumRechargedBill(SIMRechargeBill info){
		try {
			return simRechargeBillDao.sumRechargedBill(info);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
}	
