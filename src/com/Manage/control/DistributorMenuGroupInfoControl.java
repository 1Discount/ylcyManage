package com.Manage.control;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Manage.common.util.LogUtil;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.MenuGroupInfo;
import com.Manage.entity.common.SearchDTO;
import com.Manage.service.InvitationSer;

@Controller
@RequestMapping("/distributor/menuGroupinfo")
/** * @author  wangbo: * @date 创建时间：2015-5-23 下午2:43:14 * @version 1.0 * @parameter  * @since  * @return  */
public class DistributorMenuGroupInfoControl extends BaseController {

	private final static String RETURNROOT_STRING = "WEB-INF/views/distributor/";
	private Logger logger = LogUtil.getInstance(DistributorMenuGroupInfoControl.class);

	/**
	 * 跳转到添加菜单页面
	 * 
	 * @return
	 */
	@RequestMapping("/info")
	public String toadd() {
		return RETURNROOT_STRING + "menugroup_info";
	}

	/**
	 * 添加菜单组
	 * @param menuGroupInfo
	 * @param model
	 * @param response
	 */
	@RequestMapping("/insertinfo")
	public void insertinfo(MenuGroupInfo menuGroup, Model model,
			HttpServletResponse response) {
		logger.debug("添加菜单组得到请求");
		MenuGroupInfo menuGroupInfo = new MenuGroupInfo();
		menuGroupInfo.setMenuGroupID(getUUID());
		menuGroupInfo.setMenuGroupName(menuGroup.getMenuGroupName());
		menuGroupInfo.setRemark(menuGroup.getRemark());
		menuGroupInfo.setSortCode(menuGroup.getSortCode());
		menuGroupInfo.setMenuIcon(menuGroup.getMenuIcon());
		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");
		if (adminUserInfo != null) {
			menuGroupInfo.setCreatorUserID(adminUserInfo.getUserID());
		}

		if (distributorMenuGroupInfoSer.insertinfo(menuGroupInfo)) {
			logger.debug("添加菜单组成功");
			try {
				response.getWriter().print("1");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		} else {
			logger.debug("添加菜单组失败");
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
	 * 修改菜单组信息
	 * 
	 * @param menuGroup
	 * @param response
	 */
	@RequestMapping("/editinfo")
	public void editinfo(MenuGroupInfo menuGroup, HttpServletResponse response) {
		logger.debug("修改菜单组得到请求");
		try {
			
			if (distributorMenuGroupInfoSer.editinfo(menuGroup)) {
				logger.debug("修改菜单组成功");
				response.getWriter().print("3");
			} else {
				logger.debug("修改菜单组失败");
				response.getWriter().print("2");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 删除菜单组信息
	 * 
	 * @param menuGroup
	 * @param response
	 */
	@RequestMapping("/deleteinfo")
	public void deleteinfo(String menuGroupID, HttpServletResponse response) {
		logger.debug("删除菜单组得到请求");
		try {
			if (distributorMenuGroupInfoSer.deleteinfo(menuGroupID)) {
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
	public void getpage(SearchDTO searchDTO, MenuGroupInfo menuGroupInfo,
			HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		System.out.println("---" + searchDTO.getCurPage() + "------"
				+ searchDTO.getPageSize());
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), menuGroupInfo);
		String pagesString = distributorMenuGroupInfoSer.getpageString(seDto);
		try {
			response.getWriter().println(pagesString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
