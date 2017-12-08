package com.Manage.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.exception.BmException;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.common.SearchDTO;

/**
 * @author lipeng
 */
@Service
public class AdminOperateSer extends BaseService {

	private Logger logger = LogUtil.getInstance(AdminOperateSer.class);

	public String getpageString(SearchDTO searchDTO) {
		logger.debug("分页server开始");
		try {
			String jsonString = adminOperateDao.getpage(searchDTO);
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
	 * 新增操作日志记录
	 *
	 * @param admin
	 * @return
	 */
	public boolean insertdata(AdminOperate admin) {
		// return adminOperateDao.insertinto(admin);

		logger.debug("开始执行插入操作记录");
		try {
			if (adminOperateDao.insertinto(admin) > 0) {
				logger.debug("插入操作记录成功");
				return true;
			} else {
				logger.debug("插入操作记录失败");
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
