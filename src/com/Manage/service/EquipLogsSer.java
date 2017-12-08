package com.Manage.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.util.LogUtil;
import com.Manage.entity.EquipLogs;

/** * @author  wangbo: * @date 创建时间：2015-7-9 下午5:14:25 * @version 1.0 * @parameter  * @since  * @return  */
@Service
public class EquipLogsSer extends BaseService {
	private Logger logger = LogUtil.getInstance(EquipLogsSer.class);
	
	public boolean insert(EquipLogs equipLogs){
		try {
			int temp=equipLogsDao.insert(equipLogs);
			if(temp>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	
	public List<EquipLogs> getbysn(EquipLogs equipLogs){
		try {
			return  equipLogsDao.getbysn(equipLogs);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean delinfo(String ID){
		try {
			int temp =equipLogsDao.delinfo(ID);
			if(temp>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	
	public boolean updatebyno(EquipLogs equipLogs){
		try {
			int temp =equipLogsDao.updatebyno(equipLogs);
			if(temp>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	public List<EquipLogs> getbyid(EquipLogs equipLogs){
		try {
			return  equipLogsDao.getbyid(equipLogs);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	public EquipLogs getcontent(EquipLogs equiplogs){
		try {
			return  equipLogsDao.getcontent(equiplogs);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
