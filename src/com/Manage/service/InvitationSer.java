package com.Manage.service;

import java.util.Date;

import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.Invitation;

/**
 * * @author wangbo: * @date 创建时间：2015-5-20 下午5:15:16 * @version 1.0 * @parameter
 * * @since * @return
 */
@Service
public class InvitationSer extends BaseService {

	private Logger logger = LogUtil.getInstance(InvitationSer.class);
	
	/**
	 * 插入邀请表记录
	 * @param invi
	 * @return
	 */
	public boolean insertinfo(Invitation invi) {
		logger.debug("开始执行插入邀请表");
		try {
			if (invitationDao.insertinfo(invi) > 0) {
				logger.debug("插入邀请表成功");
				return true;
			} else {
				logger.debug("插入邀请表失败");
				return false;
			}
		} catch (BmException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return false;

	}
	
	/**
	 * 发邮件
	 * @param form 收件人邮箱
	 * @param sendmes 邮件信息
	 * @param subject 主题
	 * @return 是否发送成功
	 */
	public boolean sendEmail(String form,String subject, String sendmes){
		logger.debug("开始发送邮件");
		try{
			HtmlEmail email = new HtmlEmail(); 
	        email.setHostName(Constants.JAVAMAIL_HOSTNAME);
	        email.setSSL(Constants.JAVAMAIL_IFSSL);
	        email.setSmtpPort(Constants.JAVAMAIL_SMTPPORT);

	        email.setCharset("UTF-8");    
	        email.addTo(form);   
	        email.setFrom(Constants.JAVAMAIL_SENDNAME,Constants.JAVAMAIL_SENDNAME);  
	        email.setAuthentication(Constants.JAVAMAIL_SENDNAME,Constants.JAVAMAIL_SENDPASSWORD());  
	        email.setSubject(subject);  
	        email.setMsg(sendmes);  
	        email.send();  
			}catch(Exception e){
				logger.debug(e.getMessage());
				e.printStackTrace();
				return false;
			}
			return true;	
	}
	
	/**
	 * 检验邀请码是否有效或者过期
	 * @param code
	 * @return 0表示无效 1表示过期  2表示正常可用
	 */
	@SuppressWarnings("unused")
	public int getinvitabycode(String code){
		logger.debug("校验邀请码是否有效");
		try {
			Invitation invitation=invitationDao.getinvitabycode(code);
			double days= DateUtils.getDistanceOfTwoDate(invitation.getValidDate(),new Date());
			double viliddays=Double.parseDouble(Constants.INVITATION_VALIDDAYS+"");
			//判断邀请码是否被用或不存在.
			if(invitation==null || "是".equals(invitation.ifUserd)){
				logger.debug("校验邀请码无效");
				return 0;
			}else if(days>viliddays){
				logger.debug("校验邀请码已经过期");
				return 1;	
			}else{
				logger.debug("校验邀请码正常可用");
				return 2;
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 更新邀请码状态
	 * @param inviteID
	 * @return
	 */
	public boolean updatestauts(String inviteID){
		logger.debug("根据Id更新邀请表状态begin");
		try {
			int temp=invitationDao.updatestatus(inviteID);
			if(temp>0)return true;
			else return false;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

}
