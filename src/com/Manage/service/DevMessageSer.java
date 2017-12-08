package com.Manage.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.DevMessage;
import com.Manage.entity.common.SearchDTO;

/**
 * * @author wangbo: * @date 创建时间：2015-5-28 下午6:35:57 * @version 1.0 * @parameter
 * * @since * @return
 */
@Service
public class DevMessageSer extends BaseService
{
	private Logger logger = LogUtil.getInstance(DevMessageSer.class);



	/**
	 * 分页查询
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpageString(SearchDTO searchDTO)
	{
		logger.debug("分页server开始");
		try
		{
			String jsonString = devMessageDao.getpageString(searchDTO);
			return jsonString;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}



	/**
	 * 根据ID删除DevMessage
	 * 
	 * @param devMessage
	 * @return
	 */
	public boolean deldevmessage(DevMessage devMessage)
	{
		try
		{
			 
			if (devMessageDao.deldevmessage(devMessage) > 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * excel导出
	 * @param devMessage
	 * @return
	 */
	public List<DevMessage> exprotexcel(DevMessage devMessage){
		try
		{
			 
			return devMessageDao.exprotexcel(devMessage);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
