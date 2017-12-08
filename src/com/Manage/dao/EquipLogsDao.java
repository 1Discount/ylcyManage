package com.Manage.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.entity.EquipLogs;
@Repository
/** * @author  wangbo: * @date 创建时间：2015-7-9 下午4:50:46 * @version 1.0 * @parameter  * @since  * @return  */
public class EquipLogsDao extends BaseDao<EquipLogsDao> {
	private static final String NAMESPACE = EquipLogsDao.class.getName() + ".";
	
	/**
	 * 插入
	 * @param equiplogs
	 * @return
	 */
	public int insert(EquipLogs equiplogs){
		
		try {
			return getSqlSession().insert(NAMESPACE+"insert",equiplogs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1001,e);	
		}
	}
	
	public List<EquipLogs> getbysn(EquipLogs equiplogs){
		try {
			return getSqlSession().selectList(NAMESPACE+"getbysn",equiplogs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004,e);	
		}
	}
	
	public int delinfo(String id){
		try {
			return getSqlSession().update(NAMESPACE+"delinfo",id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1002,e);	
		}
	}
	
	/**
	 * 根据编号删除信息
	 * @param equiplogs
	 * @return
	 */
	public int updatebyno(EquipLogs equiplogs){
		try {
			return getSqlSession().update(NAMESPACE+"updatebyno",equiplogs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1003,e);	
		}
	}
	/**
	 * 根据ID获取
	 */
	public List<EquipLogs> getbyid(EquipLogs equiplogs){
		try {
			return getSqlSession().selectList(NAMESPACE+"getbyid",equiplogs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004,e);	
		}
	}
	//getcontent
	public EquipLogs getcontent(EquipLogs equiplogs){ 
		try {
			return getSqlSession().selectOne(NAMESPACE+"getcontent",equiplogs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BmException(Constants.common_errors_1004,e);	
		}
	}
}
