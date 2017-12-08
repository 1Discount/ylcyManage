package com.Manage.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.exception.BmException;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.SubmitOrders;
import com.Manage.entity.common.SearchDTO;

@Service
public class SubmitOrdersSer extends BaseService{
	
	private Logger logger = LogUtil.getInstance(SubmitOrders.class);
	
	/**
	 * 分页，排序，条件查询
	 *
	 * @param searchDTO
	 * @return
	 */
	public String getpage(SearchDTO searchDTO) {
		logger.debug("提工单分页开始");
		try {
			String jsonString = submitOrdersDao.getPage(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}
	
	public boolean insert(SubmitOrders submitOrders){
		logger.debug("开始执行插入提工单信息");
		try {
			if (submitOrdersDao.insert(submitOrders) > 0) {
				logger.debug("插入提工单信息成功");
				return true;
			} else {
				logger.debug("插入提工单信息失败");
				return false;
			}
		} catch (BmException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 根据ID修改接单信息
	 * @param acceptOrder
	 * @return
	 */
	public boolean update(SubmitOrders submitOrders){
		logger.debug("开始执行修改提工单信息");
		try {
			if (submitOrdersDao.update(submitOrders) > 0) {
				logger.debug("修改提工单信息成功");
				return true;
			} else {
				logger.debug("修改提工单信息失败");
				return false;
			}
		} catch (BmException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteById(String submitOrdersID){
		logger.debug("开始执行删除提工单信息");
		try {
			if (submitOrdersDao.deleteById(submitOrdersID) > 0) {
				logger.debug("删除提工单信息成功");
				return true;
			} else {
				logger.debug("删除提工单信息失败");
				return false;
			}
		} catch (BmException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
}
