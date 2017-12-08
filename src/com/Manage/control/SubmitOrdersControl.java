package com.Manage.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Manage.common.constants.Constants;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.Dictionary;
import com.Manage.entity.SubmitOrders;
import com.Manage.entity.common.SearchDTO;

/**
 * 
 * @author 沈超
 *
 */
@Controller
@RequestMapping("/submitorders")
public class SubmitOrdersControl extends BaseController{
	
	private Logger logger = LogUtil.getInstance(SubmitOrdersControl.class);
	
	/**
	 * 跳转到提工单页面
	 * @param request
	 * @param response
	 * @param SN
	 * @param customerName
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request,HttpServletResponse response,String SN,String customerName,String j,String IMSI, Model model){
		
		//工单问题类型
		List<Dictionary> typeList = dictionarySer.getAllList(Constants.SUBMITORDERS_TYPE);
		//指派人列表
		//List<AdminUserInfo> designeeList = adminuserinfoser.getDesignee();
		model.addAttribute("SN", SN);
		model.addAttribute("customerName", customerName);
		model.addAttribute("baseStation", j);
		model.addAttribute("IMSI", IMSI);
		//model.addAttribute("designeeList", designeeList);
		model.addAttribute("typeList", typeList);
		
		return "WEB-INF/views/orders/submitorders_list";
	}
	
	/**
	 * 跳转到处理工单页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/handle")
	public String handle(HttpServletRequest request,HttpServletResponse response,Model model){
		//工单问题类型
		List<Dictionary> typeList = dictionarySer.getAllList(Constants.SUBMITORDERS_TYPE);
		model.addAttribute("typeList", typeList);
		return "WEB-INF/views/orders/submitorders_handle";
	}
	
	/**
	 * 提工单页面列表
	 * @param request
	 * @param response
	 * @param searchDTO
	 * @param submitOrders
	 */
	@RequestMapping("/getpage")
	public void getpage(HttpServletRequest request,HttpServletResponse response,SearchDTO searchDTO,SubmitOrders submitOrders){
		
		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		submitOrders.setIntroducerID(adminUserInfo.getUserID());
		
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(),submitOrders);
		
		String resultString = submitOrdersSer.getpage(seDto);
		
		try {
			
			response.getWriter().write(resultString);
			
		} catch (IOException e) {
			
			e.printStackTrace();
 		}
	}
	
	/**
	 * 处理工单页面列表
	 * @param request
	 * @param response
	 * @param searchDTO
	 * @param submitOrders
	 */
	@RequestMapping("/getpage1")
	public void getpage1(HttpServletRequest request,HttpServletResponse response,SearchDTO searchDTO,SubmitOrders submitOrders){
		
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(),submitOrders);
		
		String resultString = submitOrdersSer.getpage(seDto);
		
		try {
			
			response.getWriter().write(resultString);
			
		} catch (IOException e) {
			
			e.printStackTrace();
 		}
	}
	
	/**
	 * 新增修改提工单信息
	 * @param submitOrders
	 * @param request
	 * @param response
	 */
	@RequestMapping("/save")
	public void save(SubmitOrders submitOrders,HttpServletRequest request,HttpServletResponse response){
		try
		{
			request.setCharacterEncoding("utf-8");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonResult = new JSONObject();

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (adminUserInfo == null)
		{
			try
			{
				jsonResult.put("code", 2);
				jsonResult.put("msg", "请重新登录!");
				response.getWriter().println(jsonResult.toString());
			}
			catch (IOException e)
			{
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			return;
		}
		
		boolean result;
		boolean isInsert = StringUtils.isBlank(submitOrders.getSubmitOrdersID());
		
		Format format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		if(isInsert)
		{
			if(submitOrders.getSN()!=null && submitOrders.getSN().length()==6){
				submitOrders.setSN(Constants.SNformat(submitOrders.getSN()));
			}
			submitOrders.setSubmitOrdersID(getUUID());
			submitOrders.setIntroducerID(adminUserInfo.getUserID());
			submitOrders.setIntroducerName(adminUserInfo.getUserName());
			submitOrders.setCreatorUserID(adminUserInfo.getUserID());
			submitOrders.setCreatorDate(format.format(new Date()));
			submitOrders.setCreatorUserName(adminUserInfo.getUserName());
			submitOrders.setOrderStatus("待解决");
			submitOrders.setSysStatus(true);
			result = submitOrdersSer.insert(submitOrders);
			
		}
		else
		{
			if(submitOrders.getSN()!=null && submitOrders.getSN().length()==4){
				submitOrders.setSN("17215021000"+submitOrders.getSN());
			}
			if(submitOrders.getSolveResultDesc()!=null||submitOrders.getCorrectiveMeasure()!=null||submitOrders.getPreventiveMeasure()!=null||submitOrders.getSolveRemark()!=null){
				submitOrders.setOrderStatus("已解决");
				submitOrders.setSolveTime(format.format(new Date()));
				submitOrders.setHandleID(adminUserInfo.getUserID());
				submitOrders.setHandleName(adminUserInfo.getUserName());
			}
			submitOrders.setModifyUserID(adminUserInfo.getUserID());
			submitOrders.setModifyDate(format.format(new Date()));
			submitOrders.setSysStatus(true);
			result = submitOrdersSer.update(submitOrders);
		}
		
		if (result)
		{
			logger.debug("提工单信息保存成功");
			try
			{
				jsonResult.put("code", 0);
				jsonResult.put("msg", "成功保存提工单信息!");
				response.getWriter().println(jsonResult.toString());
			}
			catch (IOException e)
			{
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			try
			{
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());// id
				admin.setCreatorDate(format.format(new Date()));// 创建时间
				admin.setCreatorUserID(adminUserInfo.getUserID());// 创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());// 创建人姓名
				admin.setOperateDate(format.format(new Date()));// 操作时间
				admin.setSysStatus(1);
				if(isInsert)
				{
					admin.setOperateContent("添加了提工单信息, 记录ID为: " + submitOrders.getSubmitOrdersID()); // 操作内容
					admin.setOperateMenu("提工单"); // 操作菜单
					admin.setOperateType("添加");// 操作类型
				}
				else
				{
					admin.setOperateContent("修改了提工单信息, 记录ID为: " + submitOrders.getSubmitOrdersID()); // 操作内容
					admin.setOperateMenu("提工单"); // 操作菜单
					admin.setOperateType("修改");// 操作类型
				}
				adminOperateSer.insertdata(admin);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			logger.debug("提工单信息保存失败");
			try
			{
				jsonResult.put("code", 1);
				jsonResult.put("msg", "保存提工单信息出错, 请重试!");
				response.getWriter().println(jsonResult.toString());
			}
			catch (IOException e)
			{
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 删除提工单信息
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/deletebyid")
	public void deletebyid(String id, HttpServletRequest request, HttpServletResponse response){
		
		response.setContentType("text/html;charset=utf-8");

		if (StringUtils.isBlank(id))
		{
			try
			{
				response.getWriter().println("请求参数无效!");
			}
			catch (IOException e)
			{
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}
		
		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");

		if (adminUserInfo == null)
		{
			try
			{
				response.getWriter().println("请重新登录!");
			}
			catch (IOException e)
			{
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			return;
		}

		Format format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		if (submitOrdersSer.deleteById(id))
		{

			try
			{
				response.getWriter().println("提工单信息删除成功!");
			}
			catch (IOException e)
			{
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
			try
			{
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());// id
				admin.setCreatorUserID(adminUserInfo.getUserID());// 创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());// 创建人姓名
				admin.setCreatorDate(format.format(new Date()));// 创建时间
				admin.setOperateDate(format.format(new Date()));// 操作时间
				admin.setSysStatus(1);

				admin.setOperateContent("删除了提工单信息, 记录ID为: " + id);
				admin.setOperateMenu("删除提工单");
				admin.setOperateType("删除");

				adminOperateSer.insertdata(admin);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{

			try
			{
				response.getWriter().println("提工单信息删除出错!");
			}
			catch (IOException e)
			{
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping("/backgroundCheck")
	public String backgroundCheck(){
		return "WEB-INF/views/orders/background_check";
	}
}
