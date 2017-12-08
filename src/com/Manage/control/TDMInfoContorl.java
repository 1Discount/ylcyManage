 package com.Manage.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Manage.common.util.DateUtils;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.Distributor;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.TDMInfo;
import com.Manage.entity.common.SearchDTO;



@Controller
@RequestMapping("/tdm")
public class TDMInfoContorl extends BaseController
{

	private Logger logger = LogUtil.getInstance(TDMInfoContorl.class);

	@RequestMapping("/index")
	public String index(HttpServletResponse response, HttpServletRequest request, Model model){
	    List<Distributor> disList=distributorSer.getAll("");
	    model.addAttribute("disList",disList);
	    return "WEB-INF/views/tdm/tdm_list";
	}
	@RequestMapping("/deletedTDMList")
	public String deletedTDMList(HttpServletResponse response, HttpServletRequest request, Model model){
	    List<Distributor> disList=distributorSer.getAll("");
	    model.addAttribute("disList",disList);
	    return "WEB-INF/views/tdm/tdm_list_del";
	}
	
	
	@RequestMapping("/toAdd")
	public String toAdd(HttpServletResponse response, HttpServletRequest request, Model model){
	    List<Distributor> disList=distributorSer.getAll("");
	    model.addAttribute("disList",disList);
	    return "WEB-INF/views/tdm/tdm_add";
	}
	@RequestMapping("/toEdit")
	public String toEdit(HttpServletResponse response, HttpServletRequest request, Model model,String TDMId){
	    List<Distributor> disList=distributorSer.getAll("");
	    model.addAttribute("disList",disList);
	    TDMInfo tdm=tdmInfoSer.getById(TDMId);
	    if(tdm!=null){
		model.addAttribute("tdm", tdm);
		return "WEB-INF/views/tdm/tdm_add";
	    }
	    return "WEB-INF/views/tdm/tdm_list";
	}
	
	
	@RequestMapping("/list")
	public void datapage(SearchDTO searchDTO,TDMInfo tdm,HttpServletResponse response,String sysStatus){
	    response.setContentType("application/json;charset=UTF-8");
	    if(sysStatus!=null && !StringUtils.isEmpty(sysStatus)){
		tdm.setSysStatus(0);
	    }else{
		tdm.setSysStatus(1);
	    }
	    SearchDTO seDto=new SearchDTO(searchDTO.getCurPage(),searchDTO.getPageSize(),searchDTO.getSortName(),searchDTO.getSortOrder(),tdm);
	    String jsonString=tdmInfoSer.getpageString(seDto);
	    try {
		response.getWriter().println(jsonString);
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	
	@RequestMapping("/addTDM")
	public void addTDM(TDMInfo tdm, HttpServletResponse response,HttpServletRequest request) throws IOException{
	    response.setContentType("application/json;charset=UTF-8");
	    AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
	    if (adminUserInfo == null) {
		response.getWriter().print("2");
		return;
	    }
	    int addResult=0;
	    if(StringUtils.isBlank(tdm.getTDMId())){
		tdm.setCreatorUserId(adminUserInfo.getUserID());
		tdm.setCreatorUserName(adminUserInfo.getUserName());
		tdm.setCreatorDate(DateUtils.getDateTime());
		addResult=tdmInfoSer.addTDM(tdm);
	    }else{
		tdm.setModifyUserId(adminUserInfo.getUserID());
		tdm.setModifyUserName(adminUserInfo.getUserName());
		tdm.setModifyDate(DateUtils.getDateTime());
		addResult=tdmInfoSer.updateTDM(tdm);
	    }
	    if(addResult!=1){
		//添加失败
		response.getWriter().print(addResult);
	    }else{
		//添加成功
		response.getWriter().print("1");
	    }
	} 
	@RequestMapping("/deletebyid")
	public void deletebyid(String TDMId,int sysStatus,HttpServletResponse response,HttpServletRequest request) throws IOException{
	    AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
	    if (adminUserInfo == null) {
		response.getWriter().print("2");
		return;
	    }
	    if(StringUtils.isEmpty(TDMId)){
		response.getWriter().print("3");
		return;
	    }else{
		response.getWriter().print(tdmInfoSer.deleteOrBack(TDMId, sysStatus));
	    }
	}
}
