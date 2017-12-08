package com.Manage.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.util.LogUtil;
import com.Manage.entity.WorkFlow;

@Service
public class WorkFlowSer extends BaseService{
	
	private Logger logger = LogUtil.getInstance(WorkFlowSer.class);
	
	/**
	 * 新增
	 * @param searchDTO
	 * @return
	 */
	public boolean insert(WorkFlow workFlow) {
		try {
			if(workFlowDao.insert(workFlow)>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public WorkFlow getInfoByOrderID(WorkFlow workFlow){
		try {
			return workFlowDao.getInfoByOrderID(workFlow);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 更新物流信息
	 * @param workFlow
	 * @return
	 */
	public boolean updateLogisticsInfo(WorkFlow workFlow){
		try {
			if(workFlowDao.updateLogisticsInfo(workFlow)>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 更新接单信息
	 * @param workFlow
	 * @return
	 */
	public boolean updateAcceptOrder(WorkFlow workFlow){
		try {
			if(workFlowDao.updateAcceptOrder(workFlow)>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 更新售后信息
	 * @param workFlow
	 * @return
	 */
	public boolean updatAfterSaleInfo(WorkFlow workFlow){
		try {
			if(workFlowDao.updatAfterSaleInfo(workFlow)>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 选择性更新
	 * @param workFlow
	 * @return
	 */
	public boolean updateSelective(WorkFlow workFlow){
		try {
			if(workFlowDao.updateSelective(workFlow)>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
