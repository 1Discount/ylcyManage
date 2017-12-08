package com.Manage.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.util.LogUtil;
import com.Manage.entity.VIPCardInfo;
import com.Manage.entity.common.SearchDTO;
/**
 * 
 * @author jiangxuecheng
 * @date 2015-11-24
 *
 */
@Service
public class VIPCardInfoSer  extends BaseService{
	private Logger logger = LogUtil.getInstance(VIPCardInfoSer.class);
	
	
	/**
	 * @deprecated 新增数据
	 * @author jiang
	 * @date 2015-11-24
	 */
	public int insertInfo(VIPCardInfo info){
		try {
			return vipCardInfoDao.insertInfo(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	  /**
	   * 分页查询
	   * @param searchDTO
	   * @author jiangxuecheng
	   * @return
	   */
	  public String getpageString(SearchDTO searchDTO){
		  try {
			return vipCardInfoDao.getpageString(searchDTO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		  return null;
	  }
	  
	  /***
	   * @deprecated根据cardID获取vip卡信息
	   * @param vipCardInfo
	   * @return
	   * @author jiangxuecheng
	   * @date 2015-11-25
	   */
	  public VIPCardInfo getvipcardinfobycardID(VIPCardInfo vipCardInfo){
		  try {
			return vipCardInfoDao.getvipcardinfobycardID(vipCardInfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		  return null;
	  }
	  
	  
	  /**
	   * @deprecated编辑修改
	   * @author jiangxuecheng
	   * @date 2015-11-25
	   * @param vipCardInfo
	   * @return
	   */
	  public int editupdate(VIPCardInfo vipCardInfo){
		  try {
			  return vipCardInfoDao.editupdate(vipCardInfo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		  return 0;
	  }
	  
	  /***
	   * @deprecated根据cardID删除VIP卡
	   * @author jiangxuecheng
	   * @date 2015-11-25
	   * @param cardID
	   * @return
	   */
	  public int del(String cardID){
		  try {
			  return vipCardInfoDao.del(cardID);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		  return 0;
	  }
	  
	  /**
	   * @deprecated  获取excel导出数据
	   * @author jiangxuecheng
	   * @date 2015-11-27
	   * @param vipCardInfo
	   * @return
	   */
	 public List<VIPCardInfo> getexecldata(SearchDTO searchDTO){
		 try {
			 return vipCardInfoDao.getexecldata(searchDTO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		 return null;
	 }
}
