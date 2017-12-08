package com.Manage.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.DeviceLogs;
import com.Manage.entity.common.SearchDTO;

/** * @author  wangbo: * @date 创建时间：2015-9-6 下午5:57:23 * @version 1.0 * @parameter  * @since  * @return  */
public class DeviceLogsTempSer extends BaseService {
	private Logger logger = LogUtil.getInstance(DeviceLogsTempSer.class);
	
	public String getNowString(SearchDTO searchDTO){
		  logger.debug("分页server开始");
		  try {
			  String jsonString=deviceLogsDao.getpage(searchDTO);
			  logger.debug("分页查询结果:"+jsonString);
			  return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	  }
	
	
	
	
}
