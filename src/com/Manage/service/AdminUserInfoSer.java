package com.Manage.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Manage.common.constants.Constants;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.MenuGroupInfo;
import com.Manage.entity.MenuInfo;
import com.Manage.entity.RoleToMenu;
import com.Manage.entity.common.SearchDTO;

@Service
public class AdminUserInfoSer extends BaseService {

	private Logger logger = LogUtil.getInstance(AdminUserInfoSer.class);
    
	@Transactional
	/**
	 * 登陆验证
	 * 
	 * @param auinfo
	 * @return
	 */
	public AdminUserInfo login(AdminUserInfo auinfo) {
		logger.debug("登陆server开始");
		try {
			return adminUserInfoDao.login(auinfo);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 发邮件
	 * 
	 * @param form
	 *            收件人邮箱
	 * @param sendmes
	 *            邮件信息
	 * @param subject
	 *            主题
	 * @return 是否发送成功
	 */
	public boolean sendEmail(String form, String subject, String sendmes) {
		try {
			HtmlEmail email = new HtmlEmail();
			email.setHostName(Constants.JAVAMAIL_HOSTNAME);
			email.setSSL(Constants.JAVAMAIL_IFSSL);
			email.setSmtpPort(Constants.JAVAMAIL_SMTPPORT);
			email.setCharset("UTF-8");
			email.addTo(form);
			email.setFrom(Constants.JAVAMAIL_SENDNAME,
					Constants.JAVAMAIL_SENDNAME);
			email.setAuthentication(Constants.JAVAMAIL_SENDNAME,
					Constants.JAVAMAIL_SENDPASSWORD());
			email.setSubject(subject);
			email.setMsg(sendmes);
			email.send();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 插入用户信息
	 * 
	 * @param auinfo
	 * @return
	 */
	public boolean register(AdminUserInfo auinfo) {
		logger.debug("注册开始插入信息");
		try {
			int temp = adminUserInfoDao.insertinfo(auinfo);
			if (temp > 0) {
				logger.debug("注册插入信息成功");
				return true;
			} else {
				logger.debug("注册插入信息失败");
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 分页，排序，条件查询.
	 * 
	 * @param searchDTO
	 * @return
	 */
	public String getpageString(SearchDTO searchDTO) {
		logger.debug("分页server开始");
		try {
			String jsonString = adminUserInfoDao.getpage(searchDTO);
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
	 * 重置密码
	 * 
	 * @param adminUserInfo
	 * @return
	 */
	public boolean restpassword(AdminUserInfo adminUserInfo) {
		logger.debug("修改密码server开始");
		try {
			int temp = adminUserInfoDao.restpassword(adminUserInfo);
			logger.debug("数据库结果:" + temp);
			if (temp == 1) {
				logger.debug("密码修改成功");
				return true;
			} else if (temp == 0) {
				logger.debug("密码修改失败");
				return false;
			} else if (temp > 1) {
				logger.debug("密码修改多行,检查用户名是否重复");
				return true;
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 删除用户
	 * 
	 * @param userID
	 * @return
	 */
	public boolean delete(String userID) {
		logger.debug("删除用户server开始");
		try {
			int temp = adminUserInfoDao.deletebyid(userID);
			logger.debug("数据库结果:" + temp);
			if (temp == 1) {
				logger.debug("用户删除成功");
				return true;
			} else {
				logger.debug("用户删除失败");
				return false;
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 编辑用户
	 * 
	 * @param userID
	 * @return
	 */
	public boolean useredit(AdminUserInfo adminUserInfo) {
		logger.debug("修改用户server开始");
		try {
			int temp = adminUserInfoDao.useredit(adminUserInfo);
			logger.debug("数据库结果:" + temp);
			if (temp == 1) {
				logger.debug("用户修改成功");
				return true;
			} else {
				logger.debug("用户修改失败");
				return false;
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 分配角色
	 * 
	 * @param map
	 * @return
	 */
	public boolean usertorole(AdminUserInfo adminUserInfo) {
		int temp = adminUserInfoDao.usertorole(adminUserInfo);
		if (temp > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 查询用户的权限菜单
	 * @param adminUserInfo
	 * @return
	 */
	public List<MenuGroupInfo> getmenubyuser(AdminUserInfo adminUserInfo){
		if(adminUserInfo.getRoleID()==null){
			return null;
		}
		try {
			List<RoleToMenu> rList=roleToMenuDao.getListbyrid(adminUserInfo.getRoleID());
			if(rList!=null){
				List<MenuInfo> mList=menuInfoDao.getlistbyid(rList);
				if(mList==null){
					return null;
				}
				List<MenuGroupInfo> mgList=menuGroupInfoDao.getinid(mList);
				for(MenuGroupInfo mg:mgList){
					for(MenuInfo m:mList){
						if(m.getMenuGroupID().equals(mg.getMenuGroupID())){
							if(mg.getMenuInfos()==null){
								mg.setMenuInfos(new ArrayList<MenuInfo>());
							}
							mg.getMenuInfos().add(m);
						}
					}
				}
				return mgList;
			}else{
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询邮箱是否存在
	 * @param email
	 * @return
	 */
	public AdminUserInfo getemail(String email){
		try {
			AdminUserInfo userInfo=adminUserInfoDao.getemail(email);
			return userInfo;
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取指派人列表
	 * @return
	 */
	public List<AdminUserInfo> getDesignee(){
		try {
			List<AdminUserInfo> userInfo=adminUserInfoDao.getDesignee();
			return userInfo;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

}
