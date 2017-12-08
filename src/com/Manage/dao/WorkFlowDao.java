package com.Manage.dao;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.WorkFlow;

@Repository
public class WorkFlowDao extends BaseDao{
	
	private static final String NAMESPACE = WorkFlowDao.class.getName() + ".";
	
	/***
	 * 新增
	 * @param workFlow
	 * @return
	 */
	public int insert(WorkFlow workFlow){
		try {
			return getSqlSession().insert(NAMESPACE+"insert",workFlow);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1001, e);
		}
	}
	
	
	public WorkFlow getInfoByOrderID(WorkFlow workFlow){
		try {
			return getSqlSession().selectOne(NAMESPACE+"getInfoByOrderID",workFlow);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1004, e);
		}
	}
	
	/**
	 * 更新物流信息
	 * @param workFlow
	 * @return
	 */
	public int updateLogisticsInfo(WorkFlow workFlow){
		try {
			return getSqlSession().update(NAMESPACE+"updateLogisticsInfo",workFlow);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
	
	/**
	 * 更新接单信息
	 * @param workFlow
	 * @return
	 */
	public int updateAcceptOrder(WorkFlow workFlow){
		try {
			return getSqlSession().update(NAMESPACE+"updateAcceptOrder",workFlow);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
	
	/**
	 * 更新售后信息
	 * @param workFlow
	 * @return
	 */
	public int updatAfterSaleInfo(WorkFlow workFlow){
		try {
			return getSqlSession().update(NAMESPACE+"updatAfterSaleInfo",workFlow);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
	
	/**
	 * 选择性更新
	 * @param workFlow
	 * @return
	 */
	public int updateSelective(WorkFlow workFlow){
		try {
			return getSqlSession().update(NAMESPACE+"updateSelective",workFlow);
		} catch (Exception e) {
			throw new BmException(Constants.common_errors_1003, e);
		}
	}
}
