package com.Manage.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Manage.common.constants.Constants;
import com.Manage.common.shiro.CacheSessionDAO;
import com.Manage.common.util.DES;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.CustomerInfo;
import com.Manage.entity.DeviceDealOrders;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.MenuGroupInfo;
import com.Manage.entity.RoleInfo;
import com.Manage.entity.common.SearchDTO;

@Controller
@RequestMapping("/admin/adminuserinfo")
public class AdminUserInfoControl extends BaseController {
	private Logger logger = LogUtil.getInstance(AdminUserInfoControl.class);
	public static Set<String> set=new HashSet<String>();
    
	/**
	 * 登陆验证入口
	 * @param auinfo
	 * @param model
	 * @return
	 */
	@RequestMapping("/tologin")
	public String loginEntry(Model model,HttpServletRequest req) {
		return "login";
	}

	/**
	 * 登陆验证
	 * @param auinfo
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	public String login(AdminUserInfo auinfo, Model model,HttpServletRequest req,HttpServletResponse rsp) {
		rsp.setCharacterEncoding("utf-8");
//		System.out.println(auinfo.password + "---------------" + auinfo.email
//				+ "--------"); // Ft
		String pwd = auinfo.getpassword();
		String pwd1 = auinfo.getpassword();
		try {
			pwd = DES.toHexString(DES.encrypt(pwd, Constants.DES_KEY));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		auinfo.setpassword(pwd);
		AdminUserInfo dto = adminuserinfoser.login(auinfo);
		
		// 判断是否登陆成功.
		if (dto != null && dto.getUserID() != null) {
			Pattern p = Pattern.compile("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,30}"); 
	        Matcher m = p.matcher(pwd1); 
	        if(!m.matches()){
	        	model.addAttribute("loginmes", "您的密码过于简单,请点击忘记密码进行密码重置.!");
	        	return "login";
	        }
			//动态加载菜单的部分,这里先注释.
			List<MenuGroupInfo> mgList = adminuserinfoser.getmenubyuser(dto);
			if (mgList == null) {
				model.addAttribute("loginmes", "该用户没有任何权限!");
				return "login";
			}
			// model.addAttribute("mgList",mgList);
			getSession().setAttribute("mgList", mgList);
			model.addAttribute("welcome", "ok"); // 作为参数传递给 index 表示登录成功, 只进行初次登录提示欢迎
			getSession().setAttribute("User", dto);

			try {
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());//id
				// admin.setCreatorDate(date);//创建时间
				admin.setCreatorUserID(dto.getUserID());//创建人ID
				admin.setCreatorUserName(dto.getUserName());//创建人姓名
				//admin.setOperateDate(date);//操作时间
				//admin.setSysStatus(1);
				admin.setOperateContent("用户登录, 用户ID为: " + dto.getUserID() + " 名称: " + dto.getUserName());
				admin.setOperateMenu("主页>登录");
				admin.setOperateType("登录");
				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return "redirect:index"; //"WEB-INF/views/index";
			/*// 判断是否为弱密码 小于10位 11位手机 常见弱密码等
			boolean isWeak = false;
			if (checkPwd.length() < 10
					|| (checkPwd.length() == 11 && (checkPwd.startsWith("13")||checkPwd.startsWith("15")||checkPwd.startsWith("17")||checkPwd.startsWith("18")))
					|| checkPwd.equals("1234567890")|| checkPwd.equals("0987654321")|| checkPwd.equals("1234554321")|| checkPwd.equals("1234512345")
					) {
				isWeak = true;
			}
			if (!isWeak) {
				Pattern p = Pattern.compile("^((0{10,20})|(1{10,20})|(2{10,20})|(3{10,20})|(4{10,20})|(5{10,20})|(6{10,20})|(7{10,20})|(8{10,20})|(9{10,20}))$");
				Matcher m = p.matcher(checkPwd);
				isWeak = m.matches();
			}

			if (isWeak) {
				getSession().setAttribute("isWeakPwd", "1");
				model.addAttribute("IsWeakPwd", "1");
				// userID=1002&userName=唐明&phone=13691639673
				model.addAttribute("userID", dto.getUserID());
				model.addAttribute("userName", dto.getUserName());
				model.addAttribute("phone", dto.getPhone());
				return "redirect:touseredit";
			} else {
				return "redirect:index"; //"WEB-INF/views/index";
			}*/
		} else {
			model.addAttribute("loginmes", "用户名或密码错误,登陆失败!");
			return "login";
		}

	}

	/**
	 * 跳转到首页
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model, HttpServletRequest req) {

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");
		if (adminUserInfo == null) {
			return "redirect:tologin";
		}

		// 流量订单统计数据
		FlowDealOrders flowDealOrders = new FlowDealOrders();
		Map<String, String> flowmap = flowDealOrdersSer.statistics(flowDealOrders);
		Object pObject = flowmap.get("count");
		double efficient = Double.parseDouble(pObject.toString());
		if (efficient == 0) {
			flowmap.put("activateP", "0");
			flowmap.put("unactivateP", "0");
			flowmap.put("unfinishedP", "0");
		} else {
			Object object1 = flowmap.get("activate");
			Object object2 = flowmap.get("unactivate");
			Object object3 = flowmap.get("unfinished");
			double activateP = Double.parseDouble(object1.toString());
			double unactivateP = Double.parseDouble(object2.toString());
			double unfinishedP = Double.parseDouble(object3.toString());
			flowmap.put("activateP", activateP / efficient * 100 + "");
			flowmap.put("unactivateP", unactivateP / efficient * 100 + "");
			flowmap.put("unfinishedP", unfinishedP / efficient * 100 + "");
		}

		// 设备订单统计数据
		DeviceDealOrders deviceDealOrders = new DeviceDealOrders();
		Map<String, String> devmap = deviceDealOrdersSer
				.statistics(deviceDealOrders);
		Object devmappObject = flowmap.get("count");
		double devmapefficient = Double.parseDouble(devmappObject
				.toString());
		if (devmapefficient == 0) {
			devmap.put("buycountP", "0");
			devmap.put("rentcountp", "0");
			devmap.put("unfinishedP", "0");
		} else {
			Object object1 = devmap.get("buycount");
			Object object2 = devmap.get("rentcount");
			Object object3 = devmap.get("unfinished");
			double buycountP = Double.parseDouble(object1.toString());
			double rentcountp = Double.parseDouble(object2.toString());
			double unfinishedP = Double.parseDouble(object3.toString());
			devmap.put("buycountP", buycountP / devmapefficient * 100 + "");
			devmap.put("rentcountp", rentcountp / devmapefficient * 100 + "");
			devmap.put("unfinishedP", unfinishedP / devmapefficient * 100 + "");
		}

		//设备统计数据
		DeviceInfo device = new DeviceInfo();
		Map<String, String> devicemap = deviceInfoSer.statistics(device);
		Object devicemapObject = devicemap.get("allDevice");
		double devicemapefficient = Double.parseDouble(devicemapObject.toString());
		if (devicemapefficient == 0) {
			devicemap.put("rkDevicep", "0");
			devicemap.put("chDevicep", "0");
			devicemap.put("wxDevicep", "0");
		} else {
			Object object1 = devicemap.get("rkDevice");
			Object object2 = devicemap.get("chDevice");
			Object object3 = devicemap.get("wxDevice");
			double ruku = Double.parseDouble(object1.toString());
			double chuku = Double.parseDouble(object2.toString());
			double weixiu = Double.parseDouble(object3.toString());
			devicemap.put("rkDevicep", ruku /devicemapefficient *100 + "");
			devicemap.put("chDevicep", chuku  /devicemapefficient *100 + "");
			devicemap.put("wxDevicep", weixiu /devicemapefficient *100  + "");
		}

		//客户统计数据
		CustomerInfo customer = new CustomerInfo();
		Map<String, String> customermap = customerInfoSer.statistics(customer);
		Object customermapObject = customermap.get("allCount");
		double customermapefficient = Double.parseDouble(customermapObject.toString());
		if (customermapefficient == 0) {
			customermap.put("resourceWebp", "0");
			customermap.put("resourceLurup", "0");
			customermap.put("resourceAppp", "0");
		} else {
			Object object1 = customermap.get("resourceWeb");
			Object object2 = customermap.get("resourceLuru");
			Object object3 = customermap.get("resourceApp");

			double guanwang = Double.parseDouble(object1.toString());
			double luru = Double.parseDouble(object2.toString());
			double app = Double.parseDouble(object3.toString());

			customermap.put("resourceWebp", guanwang/ customermapefficient*100+ "");
			customermap.put("resourceLurup", luru/customermapefficient*100+"");
			customermap.put("resourceAppp", app / customermapefficient*100 + "");
		}

		model.addAttribute("flowmap", flowmap);
		model.addAttribute("devmap", devmap);

		model.addAttribute("devicemap",devicemap);
		model.addAttribute("customermap",customermap);

		if (null != req.getParameter("welcome")) {
			model.addAttribute("info","欢迎进入");
		}
		if(adminUserInfo.getEmail().equals("362331359@qq.com")){
			model.addAttribute("ifopen", Constants.TIMING_ADDJZTEMP);
			return "WEB-INF/views/service/map_pos";
		}else{
			return "WEB-INF/views/index";
		}
		
	}

	/**
	 * 退出
	 *
	 * @return
	 */
	@RequestMapping("/loginout")
	public String loginout(HttpServletRequest request,HttpServletResponse response) {
		String flag=request.getParameter("flag");
		if(flag!=null){if("0".equals(flag.trim())){set.remove("key");
		}else{set.add("key");}
		}
		if (getSession().getAttribute("User") != null) {
			getSession().removeAttribute("User");
		}
		return "redirect:tologin";
	}

	/**
	 * 注册插入记录
	 *
	 * @param auinfo
	 * @param inviteCode
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/register")
	public String register(AdminUserInfo auinfo, String inviteCode,
			HttpServletResponse response, Model model) {
		response.setCharacterEncoding("utf-8");
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("spring收到注册请求");
		String pwd = auinfo.getpassword();
		Pattern p = Pattern.compile("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,30}"); 
        Matcher m = p.matcher(pwd); 
        if(!m.matches()){
        	model.addAttribute("rest_password_log", "新密码不满足密码复杂度要求，请重新输入密码");
        	model.addAttribute("email",auinfo.getEmail());
			return "login_up";
        } 
		
        
		AdminUserInfo user = new AdminUserInfo();
		user.setUserID(getUUID());
		user.setEmail(auinfo.getEmail());
		user.setUserName(auinfo.getUserName());
		//String pwd = auinfo.getpassword();
		try {
			pwd = DES.toHexString(DES.encrypt(pwd, Constants.DES_KEY));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.setpassword(pwd);
		user.setIfLock("否");
		user.setIfAdmin("否");
		user.setKeyCode(inviteCode);
		user.setCreatorUserID(user.getUserID());
		// 判断是否注册成功.
		if (adminuserinfoser.register(user)) {
			logger.debug("插入用户信息成功");

			try {
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());//id
				// admin.setCreatorDate(date);//创建时间
				admin.setCreatorUserID(user.getUserID());//创建人ID
				admin.setCreatorUserName(user.getUserName());//创建人姓名
				//admin.setOperateDate(date);//操作时间
				//admin.setSysStatus(1);

				admin.setOperateContent("用户注册成功, 用户ID为: " + user.getUserID() + " 名称: " + user.getUserName());
				admin.setOperateMenu("主页>注册");
				admin.setOperateType("新增");

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			// 注册成功，后更新邀请码状态
			if (invitationSer.updatestauts(inviteCode)) {
				logger.debug("更新邀请码信息成功");
				model.addAttribute("registerlog", "注册成功!");
				model.addAttribute("email", auinfo.getEmail());
				return "login";
			} else {
				logger.debug("更新邀请码信息失败");
				model.addAttribute("registerlog", "注册成功!");
				model.addAttribute("email", auinfo.getEmail());
				return "login";
			}
		} else {
			logger.debug("插入用户信息失败");
			model.addAttribute("registerlog", "注册失败!");
			return "login_up";
		}
	}

	/**
	 * 找回密码跳转
	 *
	 * @return
	 */
	@RequestMapping("/tofindpassword")
	public String tofindpassword() {
		return "WEB-INF/views/admin/find_password";
	}

	/**
	 * 找回密码URL入口
	 *
	 * @return
	 */
	@RequestMapping("/updatepassword/{email}/{codeString}")
	public String tofindpassword(@PathVariable String email,
			@PathVariable String codeString, Model model) {
		// 首先判断邀请码是否有效
		int temp = invitationSer.getinvitabycode(codeString);
		// 邀请码有效.
		if (temp == 2) {
			model.addAttribute("email", email);
			model.addAttribute("codestatus", "令牌可用");
		} else if (temp == 1) {
			model.addAttribute("codestatus", "抱歉,令牌已经过期,请重新发送邮件");
		} else if (temp == 0) {
			model.addAttribute("codestatus", "抱歉，令牌无效,请重新发送邮件");
		}
		return "WEB-INF/views/admin/update_password";
	}

	/**
	 * 重置密码
	 *
	 * @param auinfo
	 * @param codeString
	 * @param model
	 * @return
	 */
	@RequestMapping("/restPassword")
	public String restPassword(AdminUserInfo auinfo, String codeString,
			Model model) {
		logger.debug("spring收到修改请求");
		String pwd = auinfo.getpassword();
		Pattern p = Pattern.compile("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,30}"); 
        Matcher m = p.matcher(pwd); 
        if(!m.matches()){
        	model.addAttribute("rest_password_log", "新密码不满足密码复杂度要求，请重新输入密码");
        	model.addAttribute("email",auinfo.getEmail());
			return "WEB-INF/views/admin/update_password";
        } 
		try {
			pwd = DES.toHexString(DES.encrypt(pwd, Constants.DES_KEY));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		auinfo.setpassword(pwd);
		// 修改密码

		// ahming marks: 这个管理员重置用户密码的入口可能不用了, 若检查仍有用, 到时要检查这里有没有设置
		// userID/email等 ---> 已检查, 这个接口通用用户email去找回密码, 应该无需处理
		if (adminuserinfoser.restpassword(auinfo)) {
			logger.debug("密码修改数据库操作成功");

			try {
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());//id
				// admin.setCreatorDate(date);//创建时间
				admin.setCreatorUserID(auinfo.getUserID());//创建人ID
				admin.setCreatorUserName(auinfo.getUserName());//创建人姓名
				//admin.setOperateDate(date);//操作时间
				//admin.setSysStatus(1);

				admin.setOperateContent("用户重置密码成功, 用户ID为: " + auinfo.getUserID() + " 名称: " + auinfo.getUserName());
				admin.setOperateMenu("主页>重置密码");
				admin.setOperateType("修改");

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			// 如果密码修改成功,更新令牌状态
			if (invitationSer.updatestauts(codeString)) {
				logger.debug("令牌状态更新成功");
				model.addAttribute("email", auinfo.getEmail());
				model.addAttribute("registerlog", "密码修改成功");
				return "login";
			} else {
				logger.debug("令牌状态更新失败,请记录:" + codeString);
				model.addAttribute("email", auinfo.getEmail());
				model.addAttribute("registerlog", "密码修改成功");
				return "login";
			}
		} else {
			logger.debug("密码修改数据库操作失败");
			model.addAttribute("rest_password_log", "抱歉,密码修改失败");
			return "WEB-INF/views/admin/update_password";
		}
	}

	/**
	 * 用户列表跳转
	 *
	 * @return
	 */
	@RequestMapping("/userlist")
	public String userlist() {
		return "WEB-INF/views/admin/user_list";
	}

	/**
	 * 分页查询
	 *
	 * @param searchDTO
	 * @param adminUserInfo
	 * @param response
	 */
	@RequestMapping("/datapage")
	public void datapage(SearchDTO searchDTO, AdminUserInfo adminUserInfo,
			HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		System.out.println("---" + searchDTO.getCurPage() + "------"
				+ searchDTO.getPageSize());
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), adminUserInfo);
		String jsonString = adminuserinfoser.getpageString(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 删除用户
	 *
	 * @param userID
	 * @param response
	 */
	@RequestMapping("/datetebyid")
	public void deletebyid(String userID, HttpServletResponse response) {
		try {
			if (userID == null) {
				response.getWriter().print("参数为空");
				return;
			}

			AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
					.getAttribute("User");

			if (adminUserInfo == null) {
				try {
					response.getWriter().println("请重新登录!");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.debug(e.getMessage());
					e.printStackTrace();
				}

				return;
			}

			if (adminuserinfoser.delete(userID)) {

				try {
					AdminOperate admin = new AdminOperate();
					admin.setOperateID(UUID.randomUUID().toString());//id
					// admin.setCreatorDate(date);//创建时间
					admin.setCreatorUserID(adminUserInfo.getUserID());//创建人ID
					admin.setCreatorUserName(adminUserInfo.getUserName());//创建人姓名
					//admin.setOperateDate(date);//操作时间
					//admin.setSysStatus(1);

					admin.setOperateContent("已删除用户, 用户ID为: " + userID);
					admin.setOperateMenu("用户管理>删除用户");
					admin.setOperateType("删除");

					adminOperateSer.insertdata(admin);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

				response.getWriter().print("删除成功");
			} else {
				response.getWriter().print("删除失败");
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 用户编辑入口
	 *
	 * @param adminUserInfo
	 * @param model
	 * @return
	 */
	@RequestMapping("/touseredit")
	public String touseredit(AdminUserInfo adminUserInfo, Model model,
			HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		// 对汉字进行转码
		String name = adminUserInfo.getUserName();
		try {
			name = new String(name.getBytes("ISO-8859-1"), "utf-8");
			adminUserInfo.setUserName(name);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//test only //model.addAttribute("userinfo", adminUserInfo);model.addAttribute("IsWeakPwd", "1");
		return "WEB-INF/views/admin/user_edit";
	}

	/**
	 * 修改用户信息
	 *
	 * @param adminUserInfo
	 * @param response
	 */
	@RequestMapping("/useredit")
	public void useredit(AdminUserInfo adminUserInfo,
			HttpServletResponse response) {

		AdminUserInfo loginAdminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");

		if (loginAdminUserInfo == null) {
			try {
				response.getWriter().println("请重新登录!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			return;
		}

		try {
			if (adminuserinfoser.useredit(adminUserInfo)) {

				try {
					AdminOperate admin = new AdminOperate();
					admin.setOperateID(UUID.randomUUID().toString());//id
					// admin.setCreatorDate(date);//创建时间
					admin.setCreatorUserID(loginAdminUserInfo.getUserID());//创建人ID
					admin.setCreatorUserName(loginAdminUserInfo.getUserName());//创建人姓名
					//admin.setOperateDate(date);//操作时间
					//admin.setSysStatus(1);

					admin.setOperateContent("修改了用户, 记录ID为: " + adminUserInfo.getUserID() + " 名称: " + adminUserInfo.getUserName());
					admin.setOperateMenu("用户管理>修改用户");
					admin.setOperateType("修改");

					adminOperateSer.insertdata(admin);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

				response.getWriter().print("修改成功");
			} else {
				logger.debug("修改失败!");
				response.getWriter().print("修改失败");
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 修改密码
	 *
	 * @param adminUserInfo
	 * @param response
	 */
	@RequestMapping("/pwdedit")
	public void pwdedit(AdminUserInfo adminUserInfo,
			HttpServletResponse response) {

		AdminUserInfo loginAdminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");
		if (loginAdminUserInfo == null) {
			try {
				response.getWriter().println("请重新登录!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			return;
		}

		try {
			String oldPwd = adminUserInfo.getCheckPassword();
			oldPwd = DES.toHexString(DES.encrypt(oldPwd, Constants.DES_KEY));
			adminUserInfo.setCheckPassword(oldPwd);

			String pwd = adminUserInfo.getpassword();
			pwd = DES.toHexString(DES.encrypt(pwd, Constants.DES_KEY));
			adminUserInfo.setpassword(pwd);
			String idString = loginAdminUserInfo.getUserID();
			adminUserInfo.setUserID(idString); // FT 为什么不能直接使用: loginAdminUserInfo.getUserID() 而需要使用一个临时变量 ? 直接用为空!
			if (adminuserinfoser.restpassword(adminUserInfo)) {

				try {
					AdminOperate admin = new AdminOperate();
					admin.setOperateID(UUID.randomUUID().toString());//id
					// admin.setCreatorDate(date);//创建时间
					admin.setCreatorUserID(loginAdminUserInfo.getUserID());//创建人ID
					admin.setCreatorUserName(loginAdminUserInfo.getUserName());//创建人姓名
					//admin.setOperateDate(date);//操作时间
					//admin.setSysStatus(1);

					admin.setOperateContent("修改了用户密码, 记录ID为: " + adminUserInfo.getUserID() + " 名称: " + adminUserInfo.getUserName());
					admin.setOperateMenu("用户管理>修改用户密码");
					admin.setOperateType("修改");

					adminOperateSer.insertdata(admin);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

				// 去掉弱密码session标记
				getSession().removeAttribute("isWeakPwd");

				response.getWriter().print("修改密码成功");
			} else {
				logger.debug("密码修改失败!");
				response.getWriter().print("修改密码失败, 请检查旧密码是否正确");
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 跳转到分配角色界面
	 * @param adminUserInfo
	 * @param model
	 * @return
	 */
	@RequestMapping("/usertorole")
	public String usertorole(AdminUserInfo adminUserInfo, Model model) {
		logger.debug("分配角色校验参数!");
		if (adminUserInfo.getUserID() == null
				|| adminUserInfo.getUserName() == null
				|| adminUserInfo.getEmail() == null) {
			logger.debug("分配角色校验参数未通过!");
			return "WEB-INF/views/admin/user_list";
		}

		String uname = adminUserInfo.getUserName();
		try {
			uname = new String(uname.getBytes("iso-8859-1"), "utf-8");
			adminUserInfo.setUserName(uname);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 查询所有角色.
		List<RoleInfo> list = roleInfoSer.getalList();

		if (list != null) {
			logger.debug("角色列表查询成功!");
			model.addAttribute("rlist", list);
		} else {
			logger.debug("角色列表查询失败!");
			// 避免前端直接报错
			model.addAttribute("rlist", "");
		}
		model.addAttribute("user", adminUserInfo);
		return "WEB-INF/views/admin/user_role";
	}

	/**
	 * 分配用户角色保存
	 */
	@RequestMapping("/adduserrole")
	public void adduserrole(AdminUserInfo adminUserInfo,
			HttpServletResponse response) {
		logger.debug("参数校验!");

		AdminUserInfo loginAdminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");
		if (loginAdminUserInfo == null) {
			try {
				response.getWriter().println("请重新登录!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
			return;
		}

		try {
			if (adminUserInfo.getUserID() != null
					&& adminUserInfo.getRoleID() != null
					&& adminUserInfo.getRoleName() != null) {
				if (adminuserinfoser.usertorole(adminUserInfo)) {
					logger.debug("角色分配成功");

					try {
						AdminOperate admin = new AdminOperate();
						admin.setOperateID(UUID.randomUUID().toString());//id
						// admin.setCreatorDate(date);//创建时间
						admin.setCreatorUserID(loginAdminUserInfo.getUserID());//创建人ID
						admin.setCreatorUserName(loginAdminUserInfo.getUserName());//创建人姓名
						//admin.setOperateDate(date);//操作时间
						//admin.setSysStatus(1);

						admin.setOperateContent("角色分配成功, 记录ID为: " + adminUserInfo.getUserID() + " 名称: " + adminUserInfo.getUserName());
						admin.setOperateMenu("用户管理>分配角色");
						admin.setOperateType("修改");

						adminOperateSer.insertdata(admin);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}

					response.getWriter().print("1");
				} else {
					logger.debug("角色分配失败");
					response.getWriter().print("0");
				}
			} else {
				logger.debug("参数校验失败" + adminUserInfo.getUserID() + "-"
						+ adminUserInfo.getRoleID() + "-"
						+ adminUserInfo.getRoleName());
				response.getWriter().print("-1");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}
	@RequestMapping("/checkPassword")
	public void checkPassword(AdminUserInfo auinfo,HttpServletResponse response) throws Exception{
		AdminUserInfo user = (AdminUserInfo) getSession().getAttribute("User");
		auinfo.setEmail(user.getEmail());
		auinfo.setpassword(DES.toHexString(DES.encrypt(auinfo.getpassword(), Constants.DES_KEY)));
		AdminUserInfo info = adminuserinfoser.login(auinfo);
		if(info!=null){
			response.getWriter().print("true");
		}else{
			response.getWriter().print("false");
		}
	}
	
}
