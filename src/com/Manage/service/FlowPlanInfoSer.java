package com.Manage.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.exception.BmException;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.FlowPlanInfo;
import com.Manage.entity.common.SearchDTO;

@Service
public class FlowPlanInfoSer extends BaseService{
	private Logger logger = LogUtil.getInstance(FlowPlanInfoSer.class);

	/**
	 * 分页，排序，条件查询 有效套餐.
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getPageString(SearchDTO searchDTO) {
		logger.debug("套餐分页server开始");
		try {
			String jsonString = flowPlanInfoDao.getPage(searchDTO);
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
	 * 分页，排序，条件查询已 删除套餐.
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getPageDeletedString(SearchDTO searchDTO) {
		logger.debug("套餐分页server开始");
		try {
			String jsonString = flowPlanInfoDao.getPageDeleted(searchDTO);
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
	 * !目前未使用, 详情见controller端"/datapage/{MCC}", 保留待参考! 
	 * 
	 * 分页，排序，条件查询 某个国家下的有效套餐, 通过MCC.
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getPageStringByMCC(SearchDTO searchDTO) {
		logger.debug("通过MCC查询套餐分页开始");
		try {
			String jsonString = flowPlanInfoDao.getPageByMCC(searchDTO);
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
	 * 查询套餐列表
	 * @param arg
	 * @return
	 */
	public List<FlowPlanInfo> getPlans(String arg){
	 return flowPlanInfoDao.getPlans(arg);
	}
	
	/**
	 * 查询套餐详情
	 * @param id
	 * @return
	 */
	public FlowPlanInfo getPlanById(String id){
	 return flowPlanInfoDao.getPlanById(id);
	}
	
	/**
	 * 插入
	 * @param info
	 * @return
	 */
	public boolean insertInfo(FlowPlanInfo info) {
		logger.debug("开始执行插入套餐信息");
		try {
			if (flowPlanInfoDao.insertInfo(info) > 0) {
				logger.debug("插入套餐成功");
				return true;
			} else {
				logger.debug("插入套餐失败");
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
	public boolean updateInfo(FlowPlanInfo info) {
		logger.debug("开始执行更新套餐信息");
		try {
			if (flowPlanInfoDao.updateInfo(info) > 0) {
				logger.debug("更新套餐成功");
				return true;
			} else {
				logger.debug("更新套餐失败");
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
	public boolean updateInfoSysStatus(FlowPlanInfo info) {
		logger.debug("开始执行更新套餐信息sysStatus");
		try {
			if (flowPlanInfoDao.updateInfoSysStatus(info) > 0) {
				logger.debug("更新套餐sysStatus成功");
				return true;
			} else {
				logger.debug("更新套餐sysStatus失败");
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
	 * 获取所有套餐列表
	 * @return
	 */
	public List<FlowPlanInfo> getall(){
		logger.debug("查询所有套餐service");
		try {
			List<FlowPlanInfo> flowPlanInfos=flowPlanInfoDao.getall();
			if(flowPlanInfos==null){
				return null;
			}else{
				return flowPlanInfos;
			}
		} catch (BmException e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
}	
