package com.Manage.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Manage.common.util.LogUtil;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.MenuGroupInfo;
import com.Manage.entity.MenuInfo;
import com.Manage.entity.RoleInfo;
import com.Manage.entity.RoleToMenu;
import com.Manage.entity.common.SearchDTO;

/** * @author  wangbo: * @date 创建时间：2015-5-26 上午10:56:08 * @version 1.0 * @parameter  * @since  * @return  */
@Controller
@RequestMapping("/distributor/roleinfo")
public class DistributorRoleInfoControl extends BaseController {
	private final static String RETURNROOT_STRING = "WEB-INF/views/distributor/";
	private Logger logger = LogUtil.getInstance(DistributorRoleInfoControl.class);
	/**
	 * 跳转到添加角色界面
	 * @return
	 */
	@RequestMapping("/add")
	public String toadd(Model model) {
		List<MenuGroupInfo> mglist=distributorMenuInfoSer.getGroupAndMenu();
		if(mglist!=null){
			model.addAttribute("mglist",mglist);
		}else{
			logger.debug("菜单组查询结果为空");
		}
		return RETURNROOT_STRING + "role_add";
	}

	/**
	 * 编辑角色入口
	 * @param model
	 * @return
	 */
	@RequestMapping("/eidt")
	public String edit(RoleInfo roleInfo,Model model) {
		if("".equals(roleInfo.getRoleID()) || roleInfo.getRoleName()==null){
			model.addAttribute("mes","参数为空");
			return RETURNROOT_STRING + "role_list";
		}
		String rnameString=roleInfo.getRoleName();
		try {
			rnameString=new String(rnameString.getBytes("iso-8859-1"),"utf-8");
			roleInfo.setRoleName(rnameString);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<MenuGroupInfo> mglist=distributorMenuInfoSer.getGroupAndMenu();
		if(mglist!=null){
			model.addAttribute("mglist",mglist);
		}else{
			logger.debug("菜单组查询结果为空");
		}
		model.addAttribute("roleInfo",roleInfo);
		List<RoleToMenu> rList=distributorRoleToMenuSer.getbyrid(roleInfo.getRoleID());
		if(rList==null){
			return RETURNROOT_STRING + "role_edit";
		}
		String midString="";
		for(RoleToMenu rom:rList){
			midString+=rom.getMenuID();
		}
		model.addAttribute("romid",midString);

		return RETURNROOT_STRING + "role_edit";

	}

	/**
	 * 添加角色菜单并且添加角色
	 * @param roleInfo
	 * @param menulist
	 * @param response
	 */
	@RequestMapping("/rolemenuadd")
	public void rolemenuadd(RoleInfo roleInfo,HttpServletResponse response){
		logger.debug("添加角色和角色菜单得到请求");

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
			if(roleInfo.getMenulist()==null){
				response.getWriter().print("参数为空!");
			}
			String uidString=getUUID();
			RoleInfo rInfo=new RoleInfo();
			rInfo.setRoleID(uidString);
			rInfo.setRoleName(roleInfo.getRoleName());
			rInfo.setRemark(roleInfo.getRemark());
//			AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
//					.getAttribute("User");
//			if (adminUserInfo != null) {
				rInfo.setCreatorUserID(loginAdminUserInfo.getUserID());
//			}
			logger.debug("开始添加角色表");
			if(distributorRoleInfoSer.insertinfo(rInfo)){
				logger.debug("角色表添加成功");
				List<RoleToMenu> menus=new ArrayList<RoleToMenu>();
				for(int i=0;i<roleInfo.getMenulist().length;i++){
					RoleToMenu toMenu=new RoleToMenu();
					toMenu.setRoleToMenuID(getUUID());
					toMenu.setRoleID(uidString);
					toMenu.setRoleName(roleInfo.getRoleName());
					toMenu.setMenuID(roleInfo.getMenulist()[i]);
					toMenu.setMenuName("");
					toMenu.setCreatorUserID(loginAdminUserInfo.getUserID());
					menus.add(toMenu);
				}
				logger.debug("开始批量添加角色菜单映射表");
				if(distributorRoleToMenuSer.batchinsert(menus)){
					logger.debug("角色菜单映射表添加完成");
					response.getWriter().print("1"); //添加成功
				}else{
					logger.debug("角色菜单映射表添加未完成");
					response.getWriter().print("0"); //角色表添加成功,权限添加未完成，请联系管理员
				}

				try {
					AdminOperate admin = new AdminOperate();
					admin.setOperateID(UUID.randomUUID().toString());//id
					// admin.setCreatorDate(date);//创建时间
					admin.setCreatorUserID(loginAdminUserInfo.getUserID());//创建人ID
					admin.setCreatorUserName(loginAdminUserInfo.getUserName());//创建人姓名
					//admin.setOperateDate(date);//操作时间
					//admin.setSysStatus(1);

					admin.setOperateContent("已添加角色, 记录ID为: " + rInfo.getRoleID() + " 名称: " + rInfo.getRoleName());
					admin.setOperateMenu("权限管理>添加角色");
					admin.setOperateType("新增");

					adminOperateSer.insertdata(admin);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}else{
				logger.debug("角色表添加失败");
				response.getWriter().print("-1"); //添加失败
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}


	}


	/**
	 * 编辑保存
	 * @param roleInfo
	 * @param response
	 */
	@RequestMapping("/editsave")
	public void editsave(RoleInfo roleInfo,HttpServletResponse response){
		logger.debug("编辑角色得到请求");

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
			if(roleInfo.getMenulist()==null || "".equals((roleInfo.getRoleID()))){
				response.getWriter().print("参数为空!");
			}
			//修改角色信息
			//先删除原有角色菜单映射
			if(distributorRoleInfoSer.editinfo(roleInfo) && distributorRoleToMenuSer.deletebyid(roleInfo.getRoleID())){
//				AdminUserInfo adminUserInfo=(AdminUserInfo)getSession().getAttribute("User");
				logger.debug("原有记录删除成功");
				List<RoleToMenu> menus=new ArrayList<RoleToMenu>();
				for(int i=0;i<roleInfo.getMenulist().length;i++){
					RoleToMenu toMenu=new RoleToMenu();
					toMenu.setRoleToMenuID(getUUID());
					toMenu.setRoleID(roleInfo.getRoleID());
					toMenu.setRoleName(roleInfo.getRoleName());
					toMenu.setMenuID(roleInfo.getMenulist()[i]);
					toMenu.setMenuName("");
//					if(adminUserInfo!=null){
						toMenu.setCreatorUserID(loginAdminUserInfo.getUserID());
//					}
					menus.add(toMenu);
				}
				logger.debug("开始批量添加角色菜单映射表");
				if(distributorRoleToMenuSer.batchinsert(menus)){
					logger.debug("角色菜单映射表添加完成");
					response.getWriter().print("1"); //添加成功
				}else{
					logger.debug("角色菜单映射表添加未完成");
					response.getWriter().print("0"); //角色表添加成功,权限添加未完成，请联系管理员
				}

				try {
					AdminOperate admin = new AdminOperate();
					admin.setOperateID(UUID.randomUUID().toString());//id
					// admin.setCreatorDate(date);//创建时间
					admin.setCreatorUserID(loginAdminUserInfo.getUserID());//创建人ID
					admin.setCreatorUserName(loginAdminUserInfo.getUserName());//创建人姓名
					//admin.setOperateDate(date);//操作时间
					//admin.setSysStatus(1);

					admin.setOperateContent("已修改角色, 记录ID为: " + roleInfo.getRoleID() + " 名称: " + roleInfo.getRoleName());
					admin.setOperateMenu("权限管理>编辑角色");
					admin.setOperateType("修改");

					adminOperateSer.insertdata(admin);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}else{
				logger.debug("角色表修改失败");
				response.getWriter().print("-1"); //添加失败
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}


	}


	/**
	 * 跳转到角色列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/rolelist")
	public String rolelist(Model model){
		return RETURNROOT_STRING+"role_list";
	}

	/**
	 * 分页查询
	 * @param searchDTO
	 * @param roleInfo
	 * @param response
	 */
	@RequestMapping("/pagelist")
	public void pagelist(SearchDTO searchDTO,RoleInfo roleInfo,
			HttpServletResponse response){
		logger.debug("分页查询角色信息control");
		response.setContentType("application/json;charset=UTF-8");
		SearchDTO seDto= new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), roleInfo);

		String pagesString = distributorRoleInfoSer.getpageString(seDto);
		logger.debug("分页查询角色结果:"+pagesString);
		try {
			response.getWriter().println(pagesString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 删除角色
	 * @param rid
	 * @param response
	 */
	@RequestMapping("/deletebyid")
	public void deletebyid(String rid,HttpServletResponse response){
		logger.debug("删除角色control");
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
			if(distributorRoleInfoSer.deleteinfo(rid)){
				logger.debug("删除角色成功");
				if(distributorRoleToMenuSer.deletebyid(rid)){
					logger.debug("删除角色下菜单成功");
					response.getWriter().print("1");
				}else{
					logger.debug("删除角色下菜单失败");
					response.getWriter().print("0");
				}

				try {
					AdminOperate admin = new AdminOperate();
					admin.setOperateID(UUID.randomUUID().toString());//id
					// admin.setCreatorDate(date);//创建时间
					admin.setCreatorUserID(loginAdminUserInfo.getUserID());//创建人ID
					admin.setCreatorUserName(loginAdminUserInfo.getUserName());//创建人姓名
					//admin.setOperateDate(date);//操作时间
					//admin.setSysStatus(1);

					admin.setOperateContent("已删除角色, 角色ID为: " + rid);
					admin.setOperateMenu("权限管理>删除角色");
					admin.setOperateType("删除");

					adminOperateSer.insertdata(admin);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}else{
				logger.debug("删除角色失败");
				response.getWriter().print("-1");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}

	}
}
