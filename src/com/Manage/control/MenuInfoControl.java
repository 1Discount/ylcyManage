package com.Manage.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Manage.common.util.LogUtil;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.MenuGroupInfo;
import com.Manage.entity.MenuInfo;
import com.Manage.entity.common.SearchDTO;

/** * @author  wangbo: * @date 创建时间：2015-5-25 下午4:46:55 * @version 1.0 * @parameter  * @since  * @return  */
@Controller
@RequestMapping("/admin/menuinfo")
public class MenuInfoControl extends BaseController {
	
	private final static String RETURNROOT_STRING = "WEB-INF/views/admin/";
	private Logger logger = LogUtil.getInstance(MenuInfoControl.class);
	
	/**
	 * 跳转到添加菜单页面
	 * @return
	 */
	@RequestMapping("/info")
	public String toadd(Model model) {
		List<MenuGroupInfo> mglist=menuInfoSer.selectgroup();
		if(mglist!=null){
			model.addAttribute("mglist",mglist);
		}else{
			logger.debug("菜单组查询结果为空");
		}
		return RETURNROOT_STRING + "menu_info";
	}

	/**
	 * 添加菜单
	 * @param menuGroupInfo
	 * @param model
	 * @param response
	 */
	@RequestMapping("/insertinfo")
	public void insertinfo(MenuInfo menu, Model model,
			HttpServletResponse response) {
		logger.debug("添加菜单组得到请求");
		MenuInfo menuInfo =new MenuInfo();
		menuInfo.setMenuInfoID(getUUID());
		menuInfo.setMenuName(menu.getMenuName());
		menuInfo.setMenuPath(menu.getMenuPath());
		menuInfo.setMenuGroupID(menu.getMenuGroupID());
		menuInfo.setSortCode(menu.getSortCode());
		menuInfo.setRemark(menu.getRemark());
		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");
		if (adminUserInfo != null) {
			menuInfo.setCreatorUserID(adminUserInfo.getUserID());
		}

		if (menuInfoSer.insertinfo(menuInfo)) {
			logger.debug("添加菜单成功");
			try {
				response.getWriter().print("1");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		} else {
			logger.debug("添加菜单失败");
			try {
				response.getWriter().print("0");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * 修改菜单信息
	 * @param menuGroup
	 * @param response
	 */
	@RequestMapping("/editinfo")
	public void editinfo(MenuInfo menu, HttpServletResponse response) {
		logger.debug("修改菜单组得到请求");
		try {
			if (menuInfoSer.editinfo(menu)) {
				logger.debug("修改菜单成功");
				response.getWriter().print("3");
			} else {
				logger.debug("修改菜单失败");
				response.getWriter().print("2");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 删除菜单信息
	 * 
	 * @param menuGroup
	 * @param response
	 */
	@RequestMapping("/deleteinfo")
	public void deleteinfo(String menuInfoID, HttpServletResponse response) {
		logger.debug("删除菜单组得到请求");
		try {
			if (menuInfoSer.deleteinfo(menuInfoID)) {
				logger.debug("删除菜单组成功");
				response.getWriter().print("1");
			} else {
				logger.debug("删除菜单组失败");
				response.getWriter().print("0");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 分页查询
	 * @param searchDTO
	 * @param menuGroupInfo
	 * @param response
	 */
	@RequestMapping("/getpage")
	public void getpage(SearchDTO searchDTO, MenuInfo menu,
			HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		System.out.println("---" + searchDTO.getCurPage() + "------"
				+ searchDTO.getPageSize());
		SearchDTO seDto= new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), menu);
		String pagesString = menuInfoSer.getpageString(seDto);
		try {
			response.getWriter().println(pagesString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
