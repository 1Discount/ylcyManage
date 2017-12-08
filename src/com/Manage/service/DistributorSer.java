package com.Manage.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.LogUtil;
import com.Manage.dao.DistributorDao;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.Dictionary;
import com.Manage.entity.Distributor;
import com.Manage.entity.common.SearchDTO;

@Service
public class DistributorSer extends BaseService{
	private Logger logger = LogUtil.getInstance(DistributorSer.class);

	/**
	 * 分页，排序，条件查询
	 *
	 * @param searchDTO
	 * @return
	 */
	public String getPageString(SearchDTO searchDTO) {
		logger.debug("Distributor分页开始");
		try {
			String jsonString = distributorDao.getPage(searchDTO);
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
	 * 分页，排序，条件查询 已删除记录
	 *
	 * @param searchDTO
	 * @return
	 */
	public String getPageDeletedString(SearchDTO searchDTO) {
		logger.debug("Distributor分页开始");
		try {
			String jsonString = distributorDao.getPageDeleted(searchDTO);
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
	 * 查询全部记录列表
	 * @param arg
	 * @return
	 */
	public List<Distributor> getAll(String arg){
		logger.debug("查询全部Distributor开始");
		return distributorDao.getAll(arg);
	}

	/**
	 * 查询Distributor详情 by ID
	 * @param id
	 * @return
	 */
	public Distributor getById(String id){
		logger.debug("根据ID查询Distributor详情开始");
		return distributorDao.getById(id);
	}

	/**
	 * 插入
	 * @param info
	 * @return
	 */
	public boolean insertInfo(Distributor info) {
		logger.debug("开始执行插入Distributor信息");
		try {
			if (distributorDao.insertInfo(info) > 0) {
				logger.debug("插入Distributor成功");
				return true;
			} else {
				logger.debug("插入Distributor失败");
				return false;
			}
		} catch (BmException e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * 更新
	 * @param info
	 * @return
	 */
	public boolean updateInfo(Distributor info) {
		logger.debug("开始执行更新Distributor信息");
		try {
			if (distributorDao.updateInfo(info) > 0) {
				logger.debug("更新Distributor成功");
				return true;
			} else {
				logger.debug("更新Distributor失败");
				return false;
			}
		} catch (BmException e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * 更新 sysStatus
	 * @param info
	 * @return
	 */
	public boolean updateInfoSysStatus(Distributor info) {
		logger.debug("开始执行更新Distributor sysStatus");
		try {
			if (distributorDao.updateInfoSysStatus(info) > 0) {
				logger.debug("更新Distributor sysStatus成功");
				return true;
			} else {
				logger.debug("更新Distributor sysStatus失败");
				return false;
			}
		} catch (BmException e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 根据渠道商类型查询列表
	 * @param type
	 * @return
	 */
	public List<Distributor> getbytype(Distributor type){
			return distributorDao.getbytype(type);
	}

	public int updateloginInfo(Distributor distributor){
		try {
			return distributorDao.updateloginInfo(distributor);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}


	public Distributor getDistributorbydistributorID(Distributor distributor){
		try {
			return distributorDao.getDistributorbydistributorID(distributor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	 public int resetpassword(Distributor distributor){
		 try {
				return distributorDao.resetpassword(distributor);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;
	 }

	 public Distributor checkPhone(Distributor distributor){
		 try {
			return distributorDao.checkPhone(distributor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
	 }
	 public Distributor checkCompany(Distributor distributor){
		 try {
			return distributorDao.checkCompany(distributor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
	 }
	 public Distributor checkEmail(Distributor distributor){
		 try {
			return distributorDao.checkEmail(distributor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
	 }

	 public Distributor getdisInofbycompany(Distributor distributor){
		 try {
			return distributorDao.getdisInofbycompany(distributor);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		 return null;
	 }

	 public Distributor checkUser(Distributor distributor){
		 try {
			 return distributorDao.checkUser(distributor);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		 return null;
	 }


	 public int updateAnnouncement(Distributor distributor){
		 try {
			return distributorDao.updateAnnouncement(distributor);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		 return 0;
	 }

	 public int checkdispwd(Distributor distributor){
		 return distributorDao.checkdispwd(distributor);
	 }


	 public Distributor checkDistributor(Distributor distributor){
		 try {
			return distributorDao.checkDistributor(distributor);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		 return null;
	 }

	  public String getpageStringDistributor(SearchDTO searchDTO){
		  logger.debug("分页server开始");
		  try {
			  String jsonString=distributorDao.getDistributorpage(searchDTO);
			  logger.debug("分页查询结果:"+jsonString);
			  return jsonString;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	  }

	  public Integer updatepriceconfig(Distributor distributor){
		  logger.info("修改价格配置开始");
		 try
		{
			return distributorDao.updatepriceconfig(distributor);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		  return 0;
	  }
	  
	  /**
	   * 分配角色
	   * 
	   * @param map
	   * @return
	   */
	  public boolean usertorole(Distributor adminUserInfo) {
	      int temp = distributorDao.usertorole(adminUserInfo);
	      if (temp > 0) {
		  return true;
	      } else {
		  return false;
	      }
	  }
}
