package com.Manage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Manage.common.constants.Constants;
import com.Manage.common.util.JiguangPushUtils;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.PushMessageDetails;
import com.Manage.entity.PushMessageInfo;
import com.Manage.entity.common.SearchDTO;

@Service
public class PushMessageInfoSer extends BaseService{

	private Logger logger = LogUtil.getInstance(PushMessageInfoSer.class);
	@Autowired
	PushMessageDetailsSer pushMessageDetailsSer;//推送信息详情表
	
	public int insertAll(PushMessageInfo p){
		p.setId(UUID.randomUUID().toString());
		//对sn进行处理，将中文的，转换成英文,
		String sn=p.getSn();
		if(sn.indexOf("，")>-1){
			sn=p.getSn().replaceAll("，", ",");
		}
		p.setSn(sn);
		p.setAlias(p.getSn());
		p.setPushMessagecount(sn.indexOf(",")>-1 ? p.getSn().split(",").length:1);
		p.setType(Constants.PUSH_MSSAGE_TYPE1);
		p.setSource(Constants.PUSH_MSSAGE_SOURCE1);
		//推送消息附带参数
		Map<String,Object> extras=new HashMap<String,Object>();
		extras.put("id", p.getId());
		if(pushMessageInfoDao.insertAll(p)==1){
			//由于移动端无法区分设备下所有连接手机，所以消息只推送一次，推送详情表只插入一条记录，设备下所有手机共享该条推送信息的状态
			//开始推送消息
			/*Map<String,Object> msgResult=new HashMap<String, Object>();
			msgResult.put("error",1);
			msgResult.put("msg","成功");
			msgResult.put("pushResult",msgResult);
			msgResult.put("msgId","111111111111");
			msgResult.put("sendno","22222222222");*/
			if(sn.indexOf(",")>-1){
				String[] snStr=sn.split(",");
				for(int i=0;i<snStr.length;i++){
					Map<String,Object> msgResult=JiguangPushUtils.sendPush_fromJSON(p.getDeviceTypes(),p.getSn(),p.getTitle(),p.getContent(),"",null,extras);
					//保存推送结果到推送信息详细表
					if(pushMessageDetailsSer.insertAll(p.getId(),snStr[i],p.getDeviceTypes(),msgResult) !=1){
						//如果添加失敗，刪除推送信息表中剛剛添加的數據
						pushMessageInfoDao.delById(p.getId());
						pushMessageDetailsDao.delByPushMessageInfoId(p.getId());
						return 0;
					}
				}
			}else{
				Map<String,Object> msgResult=JiguangPushUtils.sendPush_fromJSON(p.getDeviceTypes(),p.getSn(),p.getTitle(),p.getContent(),"",null,extras);
				//保存推送结果到推送信息详细表
				PushMessageDetails pDetails=new PushMessageDetails();
				if(pushMessageDetailsSer.insertAll(p.getId(),sn,p.getDeviceTypes(),msgResult) !=1){
					//如果添加失敗，刪除推送信息表中剛剛添加的數據
					pushMessageInfoDao.delById(p.getId());
					pushMessageDetailsDao.delByPushMessageInfoId(p.getId());
					return 0;
				}
			}
			return 1;

		}else{
			return 0;
		}
	}
	public int delById(String id){
		return pushMessageInfoDao.delById(id);
	}
	/**
	 * 分页，排序，条件查询.
	 * @param searchDTO
	 * @return
	 */
  public String getpageString(SearchDTO searchDTO){
	  logger.debug("分页server开始");
	  try {
		  String jsonString=pushMessageInfoDao.getpage(searchDTO);
		  logger.debug("分页查询结果:"+jsonString);
		  return jsonString;
	} catch (Exception e) {
		logger.debug(e.getMessage());
		// TODO: handle exception
		e.printStackTrace();
		return "";
	}
  }
  
  public Map<String,Object> pushMessageDetail(String id){
	  Map<String,Object> params=new HashMap<String,Object>();
	  params.put("id",id);
	  PushMessageInfo p=pushMessageInfoDao.selectByParams(params);
	  if(p==null){
		  params.put("status",-1);
		  params.put("mes","该推送已被删除或者不存在");
	  }
	  params.clear();
	  params.put("status",1);
	  params.put("msg","成功");
	  params.put("p", p);
	  return params;
  }
}
