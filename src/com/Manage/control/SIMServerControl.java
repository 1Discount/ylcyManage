package com.Manage.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Manage.common.constants.Constants;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.Dictionary;
import com.Manage.entity.SIMInfo;
import com.Manage.entity.SIMServer;
import com.Manage.entity.VpnInfo;
import com.Manage.entity.common.SearchDTO;

@Controller
@RequestMapping("/sim/simserver")
public class SIMServerControl extends BaseController {
	private Logger logger = LogUtil.getInstance(SIMServerControl.class);

	/**
	 * 分页查询SIM卡服务器列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");
        List<Dictionary> serverStatus = dictionarySer.getAllList(Constants.DICT_SIMSERVER_STATUS);
        model.addAttribute("ServerStatusDict", serverStatus);
		model.addAttribute("IsIndexView", true);
		return "WEB-INF/views/sim/simserver_index";

	}

	/**
	 * 分页查询
	 * @param searchDTO
	 * @param info
	 * @param response
	 */
	@RequestMapping("/datapage")
	public void datapage(SearchDTO searchDTO, SIMServer info,
			HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);
		String jsonString = simServerSer.getPageString(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 全部已删除记录入口
	 * @param model
	 * @return
	 */
	@RequestMapping("/trash")
	public String trash(HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");
        List<Dictionary> serverStatus = dictionarySer.getAllList(Constants.DICT_SIMSERVER_STATUS);
        model.addAttribute("ServerStatusDict", serverStatus);
		model.addAttribute("IsTrashView", true);
		return "WEB-INF/views/sim/simserver_index";

	}



	/**
	 * 分页查询 已删除
	 * @param searchDTO
	 * @param info
	 * @param response
	 */
	@RequestMapping("/trashdatapage")
	public void dataPageDeleted(SearchDTO searchDTO, SIMServer info,
			HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);
		String jsonString = simServerSer.getPageDeletedString(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * SIM卡服务器详情 by ID
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/view/{id}")
	public String view(@PathVariable String id, Model model) {
		SIMServer info = simServerSer.getById(id);
		if (info != null && info.getSIMServerID() != null) {

			List<CountryInfo> countries = countryInfoSer.getAll("");
			List<SIMServer> simServers = simServerSer.getAll("");

			model.addAttribute("Countries", countries);
			model.addAttribute("SIMServers", simServers);

	        List<Dictionary> serverStatus = dictionarySer.getAllList(Constants.DICT_SIMSERVER_STATUS);
	        model.addAttribute("ServerStatusDict", serverStatus);

			model.addAttribute("Model", info);
		} else {
			model.addAttribute("info","此SIM卡服务器不存在或已无效!");
		}
		return "WEB-INF/views/sim/simserver_view";
	}

	/**
	 * 更新入口
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		SIMServer info = simServerSer.getById(id);
		if (info != null && info.getSIMServerID() != null) {
	        List<Dictionary> serverStatus = dictionarySer.getAllList(Constants.DICT_SIMSERVER_STATUS);
	        model.addAttribute("ServerStatusDict", serverStatus);
			model.addAttribute("Model", info);
		} else {
			model.addAttribute("info","此SIM卡服务器不存在或已无效!");
		}
		return "WEB-INF/views/sim/simserver_edit";
	}

// --> 目前回收站与正常全部列表里的编辑已统一, 所以不再区分. 对应编辑完毕后的返回页面, 使用 response:Referer 去跳转
//	/**
//	 * 回收站中更新记录
//	 *
//	 * 鉴于正常记录和回收站中的记录编辑有所不同， 所以区分两个接口
//	 *
//	 * @param id
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping("/trash/edit/{id}")
//	public String editTrash(@PathVariable String id, Model model) {
//		SIMServer info = simServerSer.getById(id);
//		if (info != null && info.getSIMServerID() != null) {
//	        List<Dictionary> serverStatus = dictionarySer.getAllList(Constants.DICT_SIMSERVER_STATUS);
//	        model.addAttribute("ServerStatusDict", serverStatus);
//			model.addAttribute("Model", info);
//			model.addAttribute("IsTrashView", true);
//		} else {
//			model.addAttribute("info","此记录不存在或已无效!");
//		}
//		return "WEB-INF/views/sim/simserver_edit";
//	}

	/**
	 * 新增记录入口
	 * @param model
	 * @return
	 */
	@RequestMapping("/new")
	public String newEntity(HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");
        List<Dictionary> serverStatus = dictionarySer.getAllList(Constants.DICT_SIMSERVER_STATUS);
        model.addAttribute("ServerStatusDict", serverStatus);
		return "WEB-INF/views/sim/simserver_new";
	}

	/**
	 * 保存记录 新增new或编辑edit提交时统一使用此接口
	 * 通过 boolean isInsert 来相应处理
	 * @param info
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void saveAction(SIMServer info, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// TODO: 服务器端数据验证

		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonResult = new JSONObject();

		boolean isInsert = false;
		if (StringUtils.isBlank(info.getSIMServerID())) {
			isInsert = true;
		}

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");

		if (adminUserInfo != null) {
			if (isInsert) {
				info.setCreatorUserID(adminUserInfo.getUserID());
				info.setCreatorUserName(adminUserInfo.getUserName());
			} else {
				info.setModifyUserID(adminUserInfo.getUserID());
				// info.setModifyUserName(adminUserInfo.getUserName());
			}
		} else {
			try {
				// response.getWriter().println("请重新登录!");
				jsonResult.put("code", 2);
				jsonResult.put("msg", "请重新登录!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			return;
		}

		boolean result;

		// try {
		if (isInsert) {
			info.setSIMServerID(getUUID());
			info.setSysStatus(1);

			result = simServerSer.insertInfo(info);
		} else {
			result = simServerSer.updateInfo(info);
		}
		// } catch (Exception e) {
		// result = false;
		// e.printStackTrace();
		// }

		if (result) {
			logger.debug("SIM卡服务器信息保存成功");
			try {
				// response.getWriter().println("成功保存SIM卡服务器信息!");
				jsonResult.put("code", 0);
				jsonResult.put("msg", "成功保存SIM卡服务器信息!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			try {
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());//id
				// admin.setCreatorDate(date);//创建时间
				admin.setCreatorUserID(adminUserInfo.getUserID());//创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());//创建人姓名
				//admin.setOperateDate(date);//操作时间
				//admin.setSysStatus(1);

				if (isInsert) {
					admin.setOperateContent("添加了SIM卡服务器, 记录ID为: " + info.getSIMServerID() + " 名称: " + info.getIP()); //操作内容
					admin.setOperateMenu("SIM卡服务器管理>添加SIM卡服务器"); //操作菜单
					admin.setOperateType("添加");//操作类型
				} else {
					admin.setOperateContent("修改了SIM卡服务器, 记录ID为: " + info.getSIMServerID() + " 名称: " + info.getIP());
					admin.setOperateMenu("SIM卡服务器管理>修改SIM卡服务器");
					admin.setOperateType("修改");
				}

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else {
			logger.debug("SIM卡服务器信息保存失败");
			try {
				// response.getWriter().println("保存SIM卡服务器信息出错, 请重试!");
				jsonResult.put("code", 1);
				jsonResult.put("msg", "保存SIM卡服务器信息出错, 请重试!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	/**
	 * 删除记录
	 * @param model
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	public void delete(@PathVariable String id, HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");

		if(StringUtils.isBlank(id)) {
			try {
				response.getWriter().println("请求参数无效!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
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

		SIMServer info = new SIMServer();
		info.setSIMServerID(id);
		info.setSysStatus(0);

		if(simServerSer.updateInfoSysStatus(info)){
			try {
				response.getWriter().println("SIM卡服务器删除成功!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			try {
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());//id
				// admin.setCreatorDate(date);//创建时间
				admin.setCreatorUserID(adminUserInfo.getUserID());//创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());//创建人姓名
				//admin.setOperateDate(date);//操作时间
				//admin.setSysStatus(1);

				admin.setOperateContent("删除了SIM卡服务器, 记录ID为: " + info.getSIMServerID() + " 名称: " + info.getIP());
				admin.setOperateMenu("SIM卡服务器管理>删除SIM卡服务器");
				admin.setOperateType("删除");

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else{
			try {
				response.getWriter().println("SIM卡服务器删除出错!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	/**
	 * 恢复记录
	 * @param model
	 * @return
	 */
	@RequestMapping("/restore/{id}")
	public void restorePlan(@PathVariable String id, HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");

		if(StringUtils.isBlank(id)) {
			try {
				response.getWriter().println("请求参数无效!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
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

		SIMServer info = new SIMServer();
		info.setSIMServerID(id);
		info.setSysStatus(1);

		if(simServerSer.updateInfoSysStatus(info)){
			try {
				response.getWriter().println("SIM卡服务器恢复成功!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			try {
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());//id
				// admin.setCreatorDate(date);//创建时间
				admin.setCreatorUserID(adminUserInfo.getUserID());//创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());//创建人姓名
				//admin.setOperateDate(date);//操作时间
				//admin.setSysStatus(1);

				admin.setOperateContent("恢复了SIM卡服务器, 记录ID为: " + info.getSIMServerID() + " 名称: " + info.getIP());
				admin.setOperateMenu("SIM卡服务器管理>恢复SIM卡服务器");
				admin.setOperateType("恢复");

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else{
			try {
				response.getWriter().println("SIM卡服务器恢复出错!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	/**
	 * SIM服务器统计入口 AJAX
	 *
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/statistics")
	public String statisticsIndex(HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");
		return "WEB-INF/views/sim/simserver_statistics";
	}

	/**
	 * 统计SIM卡数量
	 * @param searchDTO
	 * @param info
	 * @param response
	 */
	@RequestMapping("/count")
	public void getSimCount(SearchDTO searchDTO, SIMServer info, HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
//		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
//				searchDTO.getPageSize(), searchDTO.getSortName(),
//				searchDTO.getSortOrder(), info);
		String jsonString = simServerSer.getSimCountString();
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping("/simLocation")
	public String simLocation(String serverIP,String SIMBankNO,Model model){
		List<SIMServer> servers = simServerSer.getSIMServerAll();
		model.addAttribute("servers", servers);
		model.addAttribute("SIMBankNO", SIMBankNO);
		if(serverIP==null ||serverIP.equals("")){
			serverIP="192.168.0.100";
			model.addAttribute("serverIP", "192.168.0.100");
		}else{
			model.addAttribute("serverIP", serverIP);
		}

		if(serverIP.equals("192.168.1000.160") || serverIP.equals("192.168.1000.162")){
			if(SIMBankNO!=null && !("").equals(SIMBankNO)){
				int length = SIMBankNO.length();
				String str = "";
				if(SIMBankNO.length()==12){
					 str = SIMBankNO.substring(7, 9);
				}else{
					 str = SIMBankNO.substring(7, 8);
				}
				model.addAttribute("a", Integer.parseInt(str));
				model.addAttribute("b", Integer.parseInt(str)+1);
				model.addAttribute("c", Integer.parseInt(str)+2);
				model.addAttribute("d", Integer.parseInt(str)+3);
				model.addAttribute("e", Integer.parseInt(str)+4);
				model.addAttribute("f", Integer.parseInt(str)+5);
				model.addAttribute("g", Integer.parseInt(str)+6);
				model.addAttribute("h", Integer.parseInt(str)+7);
				model.addAttribute("i", Integer.parseInt(str)+8);
				model.addAttribute("j", Integer.parseInt(str)+9);
				model.addAttribute("k", Integer.parseInt(str)+10);
				model.addAttribute("l", Integer.parseInt(str)+11);
				model.addAttribute("m", Integer.parseInt(str)+12);
				model.addAttribute("n", Integer.parseInt(str)+13);
				model.addAttribute("o", Integer.parseInt(str)+14);
				model.addAttribute("p", Integer.parseInt(str)+15);
			}else{
				model.addAttribute("a", 1);
				model.addAttribute("b", 2);
				model.addAttribute("c", 3);
				model.addAttribute("d", 4);
				model.addAttribute("e", 5);
				model.addAttribute("f", 6);
				model.addAttribute("g", 7);
				model.addAttribute("h", 8);
				model.addAttribute("i", 9);
				model.addAttribute("j", 10);
				model.addAttribute("k", 11);
				model.addAttribute("l", 12);
				model.addAttribute("m", 13);
				model.addAttribute("n", 14);
				model.addAttribute("o", 15);
				model.addAttribute("p", 16);
			}
		}else{
			if(SIMBankNO!=null && !("").equals(SIMBankNO)){
				int length = SIMBankNO.length();
				String str = "";
				if(SIMBankNO.length()==12){
					 str = SIMBankNO.substring(7, 9);
				}else{
					 str = SIMBankNO.substring(7, 8);
				}
				model.addAttribute("a", Integer.parseInt(str));
				model.addAttribute("b", Integer.parseInt(str)+1);
				model.addAttribute("c", Integer.parseInt(str)+2);
				model.addAttribute("d", Integer.parseInt(str)+3);
			}else{
				model.addAttribute("a", 1);
				model.addAttribute("b", 2);
				model.addAttribute("c", 3);
				model.addAttribute("d", 4);
			}
		}







		return "WEB-INF/views/sim/simlocaltion";
	}
	@RequestMapping("/getpageinfo")
	public @ResponseBody List<SIMInfo> getpageinfo(String serverIP, Model model){
		//List<SIMServer> servers = simServerSer.getSIMServerAll();
		SIMInfo simInfo = new SIMInfo();
		if(serverIP!=null && serverIP!=""){
			simInfo.setServerIP(serverIP);
		}
		List<SIMInfo> simInfos = simInfoSer.getSIMbyserverID(simInfo);
		List<SIMInfo> newSimInfos = new ArrayList<SIMInfo>();
		for (SIMInfo simInfo2 : simInfos) {
			String countryList = simInfo2.getCountryList();
			String[] country =  countryList.split("\\|");
			String countryName="";
			if(country.length>1){
				for(int i=0;i<country.length;i++){
					CountryInfo countryInfo  = new CountryInfo();
					countryInfo.setCountryCode(Integer.parseInt(country[i]));
					CountryInfo ct  = countryInfoSer.convertcountry(countryInfo);
					if(i==country.length-1){
						countryName =countryName+ ct.getCountryName();
					}else{
						countryName =countryName+ ct.getCountryName()+"|";
					}
				}
				simInfo2.setCountryName(countryName);
			}else{
				//找到
				if(country[0].indexOf("待修改")!=-1){
					simInfo2.setCountryName(country[0]);
					newSimInfos.add(simInfo2);
					continue;
				}
			}

			newSimInfos.add(simInfo2);
		}
		return newSimInfos;
	}




	/*@RequestMapping("/simLocation")
	public String simLocation(String serverIP,String SIMBankNO,Model model){
		List<SIMServer> servers = simServerSer.getSIMServerAll();
		model.addAttribute("servers", servers);
		model.addAttribute("SIMBankNO", SIMBankNO);
		if(serverIP==null ||serverIP.equals("")){
			model.addAttribute("serverIP", "192.168.0.100");
		}else{
			model.addAttribute("serverIP", serverIP);
		}
		if(SIMBankNO!=null && !("").equals(SIMBankNO)){
			int length = SIMBankNO.length();
			String str = "";
			if(SIMBankNO.length()==12){
				 str = SIMBankNO.substring(7, 9);
			}else{
				 str = SIMBankNO.substring(7, 8);
			}
			model.addAttribute("a", Integer.parseInt(str));
			model.addAttribute("b", Integer.parseInt(str)+1);
			model.addAttribute("c", Integer.parseInt(str)+2);
			model.addAttribute("d", Integer.parseInt(str)+3);
		}else{
			model.addAttribute("a", 1);
			model.addAttribute("b", 2);
			model.addAttribute("c", 3);
			model.addAttribute("d", 4);
		}
		return "WEB-INF/views/sim/simlocaltion";
	}
	@RequestMapping("/getpageinfo")
	public @ResponseBody List<SIMInfo> getpageinfo(String serverIP, Model model){
		List<SIMServer> servers = simServerSer.getSIMServerAll();
		SIMInfo simInfo = new SIMInfo();
		if(serverIP!=null && serverIP!=""){
			simInfo.setServerIP(serverIP);
		}
		List<SIMInfo> simInfos = simInfoSer.getSIMbyserverID(simInfo);
		List<SIMInfo> newSimInfos = new ArrayList<>();
		for (SIMInfo simInfo2 : simInfos) {
			String countryList = simInfo2.getCountryList();
			String[] country =  countryList.split("\\|");
			String countryName="";
			if(country.length>1){
				for(int i=0;i<country.length;i++){
					CountryInfo countryInfo  = new CountryInfo();
					countryInfo.setCountryCode(Integer.parseInt(country[i]));
					CountryInfo ct  = countryInfoSer.convertcountry(countryInfo);
					if(i==country.length-1){
						countryName =countryName+ ct.getCountryName();
					}else{
						countryName =countryName+ ct.getCountryName()+"|";
					}
				}
			}else{
				//找到
				if(country[0].indexOf("待修改")!=-1){
					simInfo2.setCountryList(country[0]);
					newSimInfos.add(simInfo2);
					continue;
				}else{
					String couString = country[0];
					//如果没有找到
					CountryInfo countryInfo  = new CountryInfo();
					countryInfo.setCountryCode(Integer.parseInt(country[0]));
					CountryInfo ct  = countryInfoSer.convertcountry(countryInfo);
					countryName = ct.getCountryName();
				}
			}
			simInfo2.setCountryList(countryName);
			newSimInfos.add(simInfo2);
		}
		return newSimInfos;
	}*/
}
