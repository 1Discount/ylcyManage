 package com.Manage.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Manage.common.constants.Constants;
import com.Manage.common.constants.RestConstants;
import com.Manage.common.util.CountryUtils;
import com.Manage.common.util.DownLoadUtil;
import com.Manage.common.util.JiguangPushUtils;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.AcceptOrder;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.Dictionary;
import com.Manage.entity.OrdersInfo;
import com.Manage.entity.PushMessageDetails;
import com.Manage.entity.PushMessageInfo;
import com.Manage.entity.WorkFlow;
import com.Manage.entity.common.SearchDTO;

@Controller
@RequestMapping("/pushMessage")
public class PushMessageContorl extends BaseController
{

	private Logger logger = LogUtil.getInstance(PushMessageContorl.class);
	
	@RequestMapping("/new")
	public String newPushMessage(HttpServletResponse response, HttpServletRequest request, Model model){
		if(checkLogin()!=1){
			return  "WEB-INF/views/"+RestConstants.URL_LOGIN_FRAG;
		}else{
			return "WEB-INF/views/pushMessage/add";
		}
	}
	
	
	@RequestMapping("/addPushMessage")
	@ResponseBody
	public Object addPushMessage(PushMessageInfo pushMessageInfo,HttpServletResponse response, HttpServletRequest request, Model model){
		if(checkLogin()!=1){
			return  "登录后方可操作，请先刷新页面登录！";
		}else{
			AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
			pushMessageInfo.setCreatorUserID(adminUserInfo.getUserID());
			pushMessageInfo.setCreatorName(adminUserInfo.getUserName());
			return pushMessageInfoSer.insertAll(pushMessageInfo);
						
		}
	}
	
	@RequestMapping("/list")
	public String pushMessageList(PushMessageInfo pushMessageInfo,HttpServletResponse response, HttpServletRequest request, Model model){
		if(checkLogin()!=1){
			return  "登录后方可操作，请先刷新页面登录！";
		}else{
			return "WEB-INF/views/pushMessage/list";
		}
	}
	
	@RequestMapping("/page")
	public void pushMessagePage(SearchDTO searchDTO,PushMessageInfo pushMessageInfo,HttpServletResponse response, HttpServletRequest request, Model model) throws IOException{
		response.setContentType("application/json;charset=UTF-8");
		if(checkLogin()!=1){
			response.getWriter().println("登录后方可操作，请先刷新页面登录！");
		}else{
			SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), pushMessageInfo);
			String jsonString = pushMessageInfoSer.getpageString(seDto);
			response.getWriter().println(jsonString);
		}
	}
	
	@RequestMapping("/detail")
	public String pushMessageDetail(String id,HttpServletResponse response, HttpServletRequest request, Model model){
		if(checkLogin()!=1){
			return  "登录后方可操作，请先刷新页面登录！";
		}else{
			Map<String,Object> result=pushMessageInfoSer.pushMessageDetail(id);
			model.addAttribute("p",result.get("p"));
			model.addAttribute("pDetails",result.get("pDetails"));
			return "WEB-INF/views/pushMessage/detail";
		}
	}
	@RequestMapping("/detailPage")
	public void detailPage(SearchDTO searchDTO,PushMessageDetails p,HttpServletResponse response, HttpServletRequest request, Model model) throws IOException{
		response.setContentType("application/json;charset=UTF-8");
		if(checkLogin()!=1){
			response.getWriter().println("登录后方可操作，请先刷新页面登录！");
		}else{
			SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(),p);
			String jsonString = pushMessageDetailsSer.getpageString(seDto);
			response.getWriter().println(jsonString);
		}
	}
	
	public int checkLogin(){
		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (adminUserInfo == null){
			return 2;
		}
		return 1;
	}
}
