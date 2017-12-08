package com.Manage.control;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Manage.common.util.LogUtil;
import com.Manage.common.util.TokenProcessor;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.Invitation;
import com.alibaba.druid.Constants;


/** * @author  wangbo: * @date 创建时间：2015-5-20 下午5:44:22 * @version 1.0 * @parameter  * @since  * @return  */
@Controller
@RequestMapping("/admin/invitation")
public class InvitationControl extends BaseController {
	private Logger logger = LogUtil.getInstance(InvitationControl.class);
	/**
	 * 发送邀请注册邮件
	 * @param email
	 * @param model
	 * @return
	 */
	@RequestMapping("/insertinfo")
	public void insertinfo(String email,HttpServletResponse response,Model model,HttpServletRequest request){
		response.setCharacterEncoding("utf-8");
		logger.debug("邀请注册得到请求");
		Invitation invitation =new Invitation();
		invitation.setInviteID(getUUID());
		invitation.setIfUserd("否");
		AdminUserInfo adminUserInfo=(AdminUserInfo)getSession().getAttribute("User");
		if(adminUserInfo!=null){
			invitation.setCreatorUserID(adminUserInfo.getUserID());
		}
 		String codeString=TokenProcessor.getInstance().generateTokeCode().replace("/","");
		logger.debug("生成邀请码:"+codeString);
		invitation.setInviteCode(codeString);
		String basePathString=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
		//发送邮件前插入邀请记录.
		if(invitationSer.insertinfo(invitation)){
			logger.debug("邀请记录插入成功");
			StringBuffer stringBuffer=new StringBuffer();
			stringBuffer.append(adminUserInfo.getUserName());
			stringBuffer.append(" easy2go邀请您注册,请复制一下链接到浏览器打开进行注册</br><a href='"+basePathString+"admin/invitation/register/"+codeString+"'>");
			stringBuffer.append(basePathString+"admin/invitation/register/"+codeString+"</a>");
			//邀请记录插入成功开始发送邮件.
			boolean b= invitationSer.sendEmail(email,"easy2go邀请您注册",stringBuffer.toString());
			if(b){
				logger.debug("发送邮件成功");
				try {
					response.getWriter().println("邮件发送成功,邀请码有效期3天!");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.debug(e.getMessage());
					e.printStackTrace();
				}
			}else{
				logger.debug("发送邮件失败");
				try {
					response.getWriter().println("抱歉,邀请邮件发送失败!");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.debug(e.getMessage());
					e.printStackTrace();
				}
			}
		}else{
			logger.debug("邀请记录插入失败");
			try {
				response.getWriter().println("抱歉,邀请邮件发送失败!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 发送修改密码邮件
	 * @param email
	 * @param model
	 * @return
	 */
	@RequestMapping("/sendemail")
	public void sendemail(String email,HttpServletResponse response,Model model){
		response.setCharacterEncoding("utf-8");
		logger.debug("找回密码发邮件得到请求");
		AdminUserInfo adminUserInfo=adminuserinfoser.getemail(email);
		if(adminUserInfo==null || adminUserInfo.getUserID()==null){
			try {
				response.getWriter().println("该邮箱未注册!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		Invitation invitation =new Invitation();
		invitation.setInviteID(getUUID());
		invitation.setIfUserd("否");
 		String codeString=TokenProcessor.getInstance().generateTokeCode().replace("/","").replace("+","");
		logger.debug("生成令牌:"+codeString);
		invitation.setInviteCode(codeString);
		String basePathString=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
		//发送邮件前插入邀请记录.
		if(invitationSer.insertinfo(invitation)){
			logger.debug("邀请记录插入成功");
			StringBuffer stringBuffer=new StringBuffer();
			//stringBuffer.append(adminUserInfo.getUserName());
			stringBuffer.append(" easy2go提示您找回密码,请复制一下链接到浏览器打开进行修改密码</br><a href='"+basePathString+"admin/adminuserinfo/updatepassword/"+email+"/"+codeString+"'>");
			stringBuffer.append(basePathString+"admin/adminuserinfo/updatepassword/"+email+"/"+codeString+"</a>");
			//邀请记录插入成功开始发送邮件.
			boolean b= invitationSer.sendEmail(email,"easy2go提示您找回密码",stringBuffer.toString());
			if(b){
				logger.debug("发送邮件成功");
				try {
					response.getWriter().println("邮件发送成功!");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.debug(e.getMessage());
					e.printStackTrace();
				}
			}else{
				logger.debug("发送邮件失败");
				try {
					response.getWriter().println("发送失败,请检查邮箱是否存在!");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.debug(e.getMessage());
					e.printStackTrace();
				}
			}
		}else{
			logger.debug("邀请记录插入失败");
			try {
				response.getWriter().println("抱歉,邀请邮件发送失败!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 *跳转到邀请注册界面.
	 * @return
	 */
	@RequestMapping("/toinvita")
	public String toinvita(){
		return "WEB-INF/views/admin/send_email";
	}
	
	
	/**
	 * 邀请链接入口
	 * @param codeString
	 * @param model
	 * @return
	 */
	@RequestMapping("/register/{codeString}")
	public String register(@PathVariable String codeString,Model model){
		logger.debug("注册链接跳转入口");
		//首先判断邀请码是否有效
		int temp=invitationSer.getinvitabycode(codeString);
		//邀请码有效
		if(temp==2){
			model.addAttribute("code",codeString);
			model.addAttribute("codestatus","邀请码可用");
		}else if(temp==1){
			model.addAttribute("codestatus","抱歉,邀请码已经过期");
		}else if(temp==0){
			model.addAttribute("codestatus","抱歉，邀请码无效");
		}
		return "login_up";
	}
	
	
	
}
