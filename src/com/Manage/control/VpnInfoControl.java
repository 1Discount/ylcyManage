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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Manage.common.constants.Constants;
import com.Manage.common.job.QuartzJOB;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.Dictionary;
import com.Manage.entity.VPNWarning;
import com.Manage.entity.VpnInfo;
import com.Manage.entity.common.SearchDTO;

@Controller
@RequestMapping("/vpn/vpninfo")
public class VpnInfoControl extends BaseController{
	
	private Logger logger = LogUtil.getInstance(VpnInfoControl.class);
	
	/**
	 * 分页查询VPN列表
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/vpn")
	public String vpn(HttpServletResponse response, Model model){
		response.setContentType("text/html;charset=utf-8");
        List<Dictionary> serverStatus = dictionarySer.getAllList(Constants.DICT_SIMSERVER_STATUS);
        model.addAttribute("ServerStatusDict", serverStatus);
		model.addAttribute("IsIndexView", true);
		return "WEB-INF/views/vpn/vpninfo_vpn";
	}
	
	
	/**
	 * 分页查询VPN
	 * @param searchDTO
	 * @param info
	 * @param response
	 */
	@RequestMapping("/vpninfopage")
	public void vpnInfoPage(SearchDTO searchDTO, VpnInfo info,
			HttpServletResponse response){
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);
		String jsonString = vpnInfoSer.getPageString(seDto);
		try {
			System.out.println(jsonString);
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 编辑vpn记录入口
	 * @param response
	 * @param model
	 * @param vpnid
	 * @return
	 */
	@RequestMapping("/editvpn")
	public String editVpn(HttpServletResponse response, Model model, String vpnId){
		model.addAttribute("vpnInfo", vpnInfoSer.getVpnInfoById(vpnId));
		return "WEB-INF/views/vpn/vpninfo_newvpn";
	}
	
	/**
	 * 新增vpn记录入口
	 * @param model
	 * @return
	 */
	@RequestMapping("/newvpn")
	public String newVpn(HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");
        List<Dictionary> serverStatus = dictionarySer.getAllList(Constants.DICT_SIMSERVER_STATUS);
        model.addAttribute("ServerStatusDict", serverStatus);
		return "WEB-INF/views/vpn/vpninfo_newvpn";
	}
	
	@RequestMapping("/savevpn")
	public void saveVpn(VpnInfo info, HttpServletRequest request,
			HttpServletResponse response, Model model){
		
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonResult = new JSONObject();

		boolean isInsert = false;
		if (StringUtils.isBlank(info.getVpnId())) {
			isInsert = true;
		}
		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");

		Format format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if (adminUserInfo != null) {
			if (isInsert) {
				info.setCreatorDate(format.format(new Date()));
				info.setCreatorUserId(adminUserInfo.getUserID());
				info.setCreatorUserName(adminUserInfo.getUserName());
			} else {
				info.setModifyUserId(adminUserInfo.getUserID());
				info.setModifyDate(format.format(new Date()));
			}
		} else {
			try {
				jsonResult.put("code", 2);
				jsonResult.put("msg", "请重新登录!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			return;
		}
		boolean result=false;
		
		if (isInsert) {
			
			//代号
			String code = info.getNumber();
			result = true;
			for (int i = 1; i <= info.getChildNum(); i++) {
				info.setVpnId(getUUID());
				info.setVpnc(info.getVpn()+"-"+i);
				info.setNumber(code+"-"+i);
				info.setSysStatus(true);
				if(!vpnInfoSer.insertInfo(info)){
					result = false;
					break;
				}
			}
			
		} else {
			VpnInfo vpnInfo = vpnInfoSer.getVpnInfoById(info.getVpnId());
			
			String[] strs = info.getVpnc().split("\\|");
			
			String vpnc = strs[0] + "|" + info.getVpn() + "|" + strs[2] + "|" + strs[3];
			
			String[] strs1 =  vpnInfo.getVpn().split("\\|");
			
			info.setVpn(strs1[0] + "|" + info.getVpn() + "|" + strs1[2] + "|" + strs1[3]);
			
			info.setVpnc(vpnc);
			
			result = vpnInfoSer.updateInfo(info);
			
			if(result){
				
				if(!vpnInfo.getVpn().equals(info.getVpn()) || info.getIncludeFlow()!=vpnInfo.getIncludeFlow()){
				
					List<VpnInfo> list = vpnInfoSer.getByVpn(vpnInfo.getVpn());
				
					for (VpnInfo vpnInfo2 : list) {
		
						vpnInfo2.setVpn(info.getVpn());
						
						vpnInfo2.setIncludeFlow(info.getIncludeFlow());
						
						if(info.getAvailableNum()==0)
						{
							vpnInfo2.setAvailableNum(info.getAvailableNum());
						}
						
						String[] strs2 = vpnInfo2.getVpnc().split("\\|");
						
						vpnInfo2.setVpnc(strs2[0] + "|" + info.getVpn().split("\\|")[1] + "|" + strs2[2] + "|" + strs2[3]);
						
						if(!vpnInfoSer.updateInfo(vpnInfo2)){
							result = false;
							break;
						}
					}
				}
			}
		}
		
		if (result) {
			logger.debug("VPN信息保存成功");
			try {
				jsonResult.put("code", 0);
				jsonResult.put("msg", "成功保存VPN信息!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
			
			try {
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());//id
				admin.setCreatorUserID(adminUserInfo.getUserID());//创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());//创建人姓名
				admin.setCreatorDate(format.format(new Date()));//创建时间
				admin.setOperateDate(format.format(new Date()));//操作时间
				admin.setSysStatus(1);
	
				if (isInsert) {
					admin.setOperateContent("添加了VPN, 记录ID为: " + info.getVpnId() + " 名称: " + info.getVpn()); //操作内容
					admin.setOperateMenu("VPN管理>添加VPN"); //操作菜单
					admin.setOperateType("添加");//操作类型
				} else {
					admin.setOperateContent("修改了VPN, 记录ID为: " + info.getVpnId() + " 名称: " + info.getVpn());
					admin.setOperateMenu("VPN管理>修改VPN");
					admin.setOperateType("修改");
				}
				
				adminOperateSer.insertdata(admin);
				
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}else{
			
			logger.debug("VPN信息保存失败");
			try {
				jsonResult.put("code", 1);
				jsonResult.put("msg", "保存VPN信息出错, 请重试!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 删除VPN
	 * @param response
	 * @param id
	 */
	@RequestMapping(value="/delete/{id}")
	public void delete(HttpServletResponse response,@PathVariable String id){
		response.setContentType("text/html;charset=utf-8");

		if(StringUtils.isBlank(id)) {
			try {
				response.getWriter().println("请求参数无效!");
			} catch (IOException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}
		
		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		
		if (adminUserInfo == null) {
			try {
				response.getWriter().println("请重新登录!");
			} catch (IOException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			return;
		}
		
		VpnInfo vpninfo = new VpnInfo();
		vpninfo.setVpnId(id);
		vpninfo.setSysStatus(false);
		
		if(vpnInfoSer.updateInfo(vpninfo)){
			
			try {
				response.getWriter().println("VPN删除成功!");
			} catch (IOException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			Format format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			try {
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());//id
				admin.setCreatorUserID(adminUserInfo.getUserID());//创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());//创建人姓名
				admin.setCreatorDate(format.format(new Date()));//创建时间
				admin.setOperateDate(format.format(new Date()));//操作时间
				admin.setSysStatus(1);

				admin.setOperateContent("删除了VPN, 记录ID为: " + vpninfo.getVpnId());
				admin.setOperateMenu("VPN管理>删除VPN");
				admin.setOperateType("删除");

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			
			try {
				response.getWriter().println("VPN删除出错!");
			} catch (IOException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * VPN详情
	 * @param response
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/view")
	public String viewVpn(HttpServletResponse response,String vpnId, Model model){
		VpnInfo vpnInfo = vpnInfoSer.getVpnInfoById(vpnId);
		
		if(vpnInfo!=null){
			model.addAttribute("Model", vpnInfo);
		}else{
			model.addAttribute("info","此VPN不存在或已无效!");
		}
		return "WEB-INF/views/vpn/vpninfo_view";
	}
	
	/**
	 * 
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/vpnwarning")
	public String vpnWarning(HttpServletResponse response, Model model){
		response.setContentType("text/html;charset=utf-8");
		return "WEB-INF/views/vpn/vpnwarning_list";
	}
	
	
	/**
	 * 分页查询VPN预警
	 * @param searchDTO
	 * @param info
	 * @param response
	 */
	@RequestMapping("/vpnwarningpage")
	public void vpnWarningPage(SearchDTO searchDTO, VPNWarning info,
			HttpServletResponse response){
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);
		String jsonString = vpnInfoSer.getPageString1(seDto);
		try {
			System.out.println(jsonString);
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/savewarning")
	public void saveWarning(VPNWarning info,HttpServletResponse response,HttpServletRequest request){
		
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonResult = new JSONObject();

		boolean isInsert = false;
		if (StringUtils.isBlank(info.getVpnWarningID())) {
			isInsert = true;
		}
		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");

		Format format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if (adminUserInfo == null) 
		{
			try {
				jsonResult.put("code", 2);
				jsonResult.put("msg", "请重新登录!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			return;
		}
		boolean result=false;
		
		if(isInsert){
			info.setVpnWarningID(getUUID());
			info.setCreatorDate(format.format(new Date()));
			info.setCreatorUserID(adminUserInfo.getUserID());
			info.setCreatorUserName(adminUserInfo.getUserName());
			info.setSysStatus(true);
			result = vpnInfoSer.insertWarning(info);
		}else{
			info.setModifyUserID(adminUserInfo.getUserID());
			info.setModifyDate(format.format(new Date()));
			info.setSysStatus(true);
			result = vpnInfoSer.updateWarning(info);
		}
		
		if (result) {
			logger.debug("VPN预警信息保存成功");
			try {
				jsonResult.put("code", 0);
				jsonResult.put("msg", "成功保存VPN预警信息!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
			
			try {
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());//id
				admin.setCreatorUserID(adminUserInfo.getUserID());//创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());//创建人姓名
				admin.setCreatorDate(format.format(new Date()));//创建时间
				admin.setOperateDate(format.format(new Date()));//操作时间
				admin.setSysStatus(1);
	
				if (isInsert) {
					admin.setOperateContent("添加了VPN预警, 记录ID为: " + info.getVpnWarningID() + " 名称: " + info.getIP()); //操作内容
					admin.setOperateMenu("VPN管理>添加VPN预警"); //操作菜单
					admin.setOperateType("添加");//操作类型
				} else {
					admin.setOperateContent("修改了VPN预警, 记录ID为: " + info.getVpnWarningID() + " 名称: " + info.getIP());
					admin.setOperateMenu("VPN管理>修改VPN预警");
					admin.setOperateType("修改");
				}
				
				adminOperateSer.insertdata(admin);
				
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}else{
			
			logger.debug("VPN预警信息保存失败");
			try {
				jsonResult.put("code", 1);
				jsonResult.put("msg", "保存VPN预警信息出错, 请重试!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 删除VPN预警
	 * @param response
	 * @param id
	 */
	@RequestMapping("/deletewarning")
	public void deleteWarning(HttpServletResponse response,String id){
		response.setContentType("text/html;charset=utf-8");

		if(StringUtils.isBlank(id)) {
			try {
				response.getWriter().println("请求参数无效!");
			} catch (IOException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}
		
		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		
		if (adminUserInfo == null) {
			try {
				response.getWriter().println("请重新登录!");
			} catch (IOException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			return;
		}
		
		if(vpnInfoSer.deleteWarningById(id)){
			
			try {
				response.getWriter().println("VPN预警删除成功!");
			} catch (IOException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			Format format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			try {
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());//id
				admin.setCreatorUserID(adminUserInfo.getUserID());//创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());//创建人姓名
				admin.setCreatorDate(format.format(new Date()));//创建时间
				admin.setOperateDate(format.format(new Date()));//操作时间
				admin.setSysStatus(1);

				admin.setOperateContent("删除了VPN预警, 记录ID为: " + id);
				admin.setOperateMenu("VPN管理>删除VPN预警");
				admin.setOperateType("删除");

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			
			try {
				response.getWriter().println("VPN删除出错!");
			} catch (IOException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping("/ping")
	public void ping(HttpServletResponse response,HttpServletRequest request,VPNWarning info){
		
		response.setContentType("text/html;charset=utf-8");
		
		QuartzJOB job = new QuartzJOB();
		
		JSONObject jsonResult = new JSONObject();
		
		try{
			
			if(job.ping(info.getIP())){
				
				if(info.getWarningStatus().equals("异常")){
					info.setWarningStatus("正常");
					vpnInfoSer.updateVPNStatus(info);
				}
				
				try{
					
					jsonResult.put("code", 0);
					jsonResult.put("msg", "已ping通");
					response.getWriter().write(jsonResult.toString());
					
				}catch (Exception e) {
					
					e.printStackTrace();
				}
				
			}else{
				
				if(info.getWarningStatus().equals("正常")){
					info.setWarningStatus("异常");
					vpnInfoSer.updateVPNStatus(info);
				}
				
				try{
					
					jsonResult.put("code", 1);
					jsonResult.put("msg", "未ping通");
					response.getWriter().write(jsonResult.toString());
					
				}catch (Exception e) {
					
					e.printStackTrace();
				}
				
			}

		}catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
