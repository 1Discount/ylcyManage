package com.Manage.service;

import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.util.LogUtil;
import com.Manage.entity.GStrenthData;

/** * @author  wangbo: * @date 创建时间：2015-11-12 下午4:08:32 * @version 1.0 * @parameter  * @since  * @return  */
@Service
public class GStrenthDataSer extends BaseService{
	private Logger logger = LogUtil.getInstance(GStrenthDataSer.class);
	
	/**
	 * 条件查询基站经纬度返回json
	 * @param gd
	 * @return
	 */
	public JSONArray getbyMCC(GStrenthData gd){
		List<GStrenthData> lgDatas=gStrenthDataDao.getbyMCC(gd);
		if(lgDatas.size()>0){
			return JSONArray.fromObject(lgDatas);
		}else{
			logger.info("基站查询结果为空");
			return null;
		}
	}
}
