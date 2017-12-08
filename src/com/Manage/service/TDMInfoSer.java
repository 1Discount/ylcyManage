package com.Manage.service;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.exception.BmException;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.dao.TDMInfoDao;
import com.Manage.entity.AcceptOrder;
import com.Manage.entity.TDMInfo;
import com.Manage.entity.common.SearchDTO;

@Service
public class TDMInfoSer extends BaseService{

	private Logger logger = LogUtil.getInstance(TDMInfoSer.class);
	
	/**
	 * 添加tdm
	 * @param tdm
	 * @return
	 */
	public int addTDM(TDMInfo tdm){
	    //验证参数
	    //必填参数为空或者已经存在
	    if(StringUtils.isBlank(tdm.getServerCode()) || StringUtils.isBlank(tdm.getTdmUrl())
		    || StringUtils.isBlank(tdm.getTdmName()) || StringUtils.isBlank(tdm.getIntranetUrl())
		    || StringUtils.isBlank(tdm.getOwnerId()) || StringUtils.isBlank(tdm.getTdmStatus())
		    ){
		return 3;
	    }
	    if(this.checkTDM(tdm.getServerCode(),tdm.getTdmName(),tdm.getTdmUrl(),tdm.getIntranetUrl())>=1){
		return 4;
	    }
	    tdm.setTDMId(UUID.randomUUID().toString());
	    tdm.setCreatorDate(DateUtils.getDateTime());
	    tdm.setSysStatus(1);
	    return tdmInfoDao.addTDM(tdm);
	}
	
	public int updateTDM(TDMInfo tdm){
	  //验证参数
	    //必填参数为空或者已经存在
	    if(StringUtils.isEmpty(tdm.getTDMId())){
		return 5;
	    }
	    if(StringUtils.isBlank(tdm.getServerCode()) || StringUtils.isBlank(tdm.getTdmUrl())
		    || StringUtils.isBlank(tdm.getTdmName()) || StringUtils.isBlank(tdm.getIntranetUrl())
		    || StringUtils.isBlank(tdm.getOwnerId()) || StringUtils.isBlank(tdm.getTdmStatus())
		    ){
		return 3;
	    }
	    List<TDMInfo> tdmListTemp=tdmInfoDao.getListNoItSelf(tdm);
	    if(tdmListTemp!=null && tdmListTemp.size()>=1){
		return 4;
	    }
	    return tdmInfoDao.updateTDMByID(tdm);
	}
	
	/**
	 * 不分页获取TDMInfo列表
	 * @param tdm
	 * @return
	 */
	public List<TDMInfo> getList(TDMInfo tdm){
	    return tdmInfoDao.getList(tdm);
	}
	
	/**
	 * 根据关键不可重复字段，查询改tdm是否存在
	 * @param serverCode
	 * @param tdmName
	 * @param tdmUrl
	 * @param intranetUrl
	 * @return
	 */
	public int checkTDM(String serverCode,String tdmName,String tdmUrl,String intranetUrl){
	    TDMInfo tdm=new TDMInfo();
	    tdm.setServerCode(serverCode);
	    tdm.setTdmName(tdmName);
	    tdm.setTdmUrl(tdmUrl);
	    //tdm.setIntranetUrl(intranetUrl);
	    List<TDMInfo> tdmListTemp=tdmInfoDao.getList(tdm);
	    if(tdmListTemp==null || tdmListTemp.isEmpty()){
		return 0; //不存在
	    }else{
		return tdmListTemp.size(); //存在
	    }
	}
	public String getpageString(SearchDTO searchDTO) {
		logger.debug("分页server开始");
		try {
			String jsonString = tdmInfoDao.getpage(searchDTO);
			logger.debug("分页查询结果:" + jsonString);
			return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}
	public TDMInfo getById(String TDMId){
	    TDMInfo tdm=new TDMInfo();
	    tdm.setTDMId(TDMId);
	    List<TDMInfo> tdms=tdmInfoDao.getList(tdm);
	    if(tdms!=null && !tdms.isEmpty()){
		return tdms.get(0);
	    }
	    return null;
	}
	public int deleteOrBack(String TDMId,int sysStatus){
	    TDMInfo tdm=new TDMInfo();
	    tdm.setTDMId(TDMId);
	    tdm.setSysStatus(sysStatus);
	    return tdmInfoDao.deleteOrBack(tdm);
	}
}
