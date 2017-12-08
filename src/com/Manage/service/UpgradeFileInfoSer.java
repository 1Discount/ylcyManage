package com.Manage.service;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.UpgradeFileInfo;

@Service
public class UpgradeFileInfoSer extends BaseService{

	private Logger logger = LogUtil.getInstance(UpgradeFileInfoSer.class);
	/**
	 * 添加升级文件
	 */
	public int add(String fileType,String fileName,int fileSize,String version,String realPath,String remark,String creatorUserID){
	    UpgradeFileInfo u=new UpgradeFileInfo();
	    u.setId(UUID.randomUUID().toString());
	    u.setSysStatus(1);
	    u.setFileType(fileType);
	    u.setFileName(fileName);
	    u.setFileSize(fileSize);
	    u.setRealPath(realPath);
	    u.setRemark(remark);
	    u.setCreatorUserID(creatorUserID);
	    u.setVersion(version);
	    return upgradeFileInfoDao.insert(u);
	}
	
	
	/**
	 * 根据文件名称查询最新的文件记录
	 */
	public UpgradeFileInfo selectOneByFileName(String name){
	   Map<String,Object> params=new HashMap<String, Object>();
	   params.put("fileName", name);
	   return upgradeFileInfoDao.selectOne(params);
	}
}
