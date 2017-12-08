package com.Manage.service;

import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.PushMessageDetails;
import com.Manage.entity.common.SearchDTO;

@Service
public class PushMessageDetailsSer extends BaseService{

	private Logger logger = LogUtil.getInstance(PushMessageDetailsSer.class);
	
	public int insertAll(String pushMessageInfoId,String sn,String deviceType,Map<String,Object> result){
		PushMessageDetails p=new PushMessageDetails();
		p.setId(UUID.randomUUID().toString());
		p.setPushMessageInfoId(pushMessageInfoId);
		p.setSn(sn);
		p.setDeviceType(deviceType);
		p.setPushStatus(Integer.parseInt(result.get("error").toString()));
		p.setPushResult(result.get("pushResult")+"");
		if(result.get("error").toString()=="1"){
			p.setPushMsgid(result.get("msgId").toString());
			p.setPushMsgNo(result.get("sendno").toString());
		}
		return pushMessageDetailsDao.insertAll(p);
	}
	public int delByPushMessageInfoId(String pushMessageInfoId){
		return pushMessageDetailsDao.delByPushMessageInfoId(pushMessageInfoId);
	}
	
	/**
	 * 分页，排序，条件查询.
	 * @param searchDTO
	 * @return
	 */
  public String getpageString(SearchDTO searchDTO){
	  logger.debug("分页server开始");
	  try {
		  String jsonString=pushMessageDetailsDao.getpage(searchDTO);
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
