package com.Manage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Manage.common.exception.BmException;
import com.Manage.entity.DepositRecord;
import com.Manage.entity.common.SearchDTO;

@Service
public class DepositRecordSer extends BaseService{

	/**
	 * 分页，排序，条件查询
	 *
	 * @param searchDTO
	 * @return
	 */
	public String getPageString(SearchDTO searchDTO) {
		try {
			String jsonString = depositRecordDao.getPage(searchDTO);
			return jsonString;
		} catch (Exception e) {
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
	public List<DepositRecord> getAll(DepositRecord info){
		return depositRecordDao.getAll(info);
	}

	/**
	 * 查询详情 by ID
	 * @param id
	 * @return
	 */
	public DepositRecord getById(String id){
		return depositRecordDao.getById(id);
	}

	/**
	 * 插入
	 *
	 * 针对区分各种错误, 可使用string返回 BmException 的错误码
	 * @param info
	 * @return boolean
	 */
	public boolean insertInfo(DepositRecord info) {
		try {
			if (depositRecordDao.insertInfo(info) > 0) {
				return true;
			} else {
				return false;
			}
		} catch (BmException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * 更新
	 * @param info
	 * @return
	 */
	public boolean updateInfo(DepositRecord info) {
		try {
			if (depositRecordDao.updateInfo(info) > 0) {
				return true;
			} else {
				return false;
			}
		} catch (BmException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * 更新 sysStatus
	 * @param info
	 * @return
	 */
	public boolean updateInfoSysStatus(DepositRecord info) {
		try {
			if (depositRecordDao.updateInfoSysStatus(info) > 0) {
				return true;
			} else {
				return false;
			}
		} catch (BmException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;

	}
	
	public int updateRefundRemarks(DepositRecord id){
		return depositRecordDao.updateRefundRemarks(id);
	}
	
	public int updateRefundbackEnd(DepositRecord id){
		return depositRecordDao.updateRefundbackEnd(id);
	}

}
