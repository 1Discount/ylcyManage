package com.Manage.service;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.exception.BmException;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.DeviceJumpInfo;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.TDMInfo;
import com.Manage.entity.common.SearchDTO;

/**
 * @author lipeng
 */
@Service
public class DeviceJumpInfoSer extends BaseService {

    private Logger logger = LogUtil.getInstance(DeviceJumpInfoSer.class);
    
    public int addJump(DeviceJumpInfo d){
	d.setDeviceJumpId(UUID.randomUUID().toString());
	
	TDMInfo tdm=tdmInfoDao.getTDMInfoBySN(d.getSn());
	d.setCurrentTDM(tdm.getTdmUrl());
	d.setCurrentTDMName(tdm.getTdmName());
	//将要跳转的TDM
	TDMInfo newTDM=tdmInfoDao.getTDMInfo(d.getNewTDM());
	d.setNewTDM(newTDM.getTdmUrl());
	d.setNewTDMName(newTDM.getTdmName());
	d.setNewTDMIfSet(1);
	//d.setNewTime(DateUtils.getDateTime());
	//跳回的TDM
	d.setBackTDM(tdm.getTdmUrl());
	d.setBackTDMName(tdm.getTdmName());
	d.setBackTDMIfSet(0);
	d.setBackTDMIfOk(0);
	d.setCreatorDate(DateUtils.getDateTime());
	d.setSysStatus(1);
	return deviceJumpInfoDao.addJump(d);
    }
    
    public String getpageString(SearchDTO searchDTO) {
	logger.debug("分页server开始");
	try {
		String jsonString = deviceJumpInfoDao.getpage(searchDTO);
		logger.debug("分页查询结果:" + jsonString);
		return jsonString;
	} catch (Exception e) {
		logger.debug(e.getMessage());
		// TODO: handle exception
		e.printStackTrace();
		return "";
	}
}
}
