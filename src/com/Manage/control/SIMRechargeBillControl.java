package com.Manage.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerMapping;

import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.OperatorInfo;
import com.Manage.entity.OrdersInfo;
import com.Manage.entity.SIMInfo;
import com.Manage.entity.SIMRechargeBill;
import com.Manage.entity.SIMServer;
import com.Manage.entity.common.SearchDTO;

/**
 * 注意本地卡和漫游卡使用同一个充值账单数据表, 通过 SIMCategory 去区分
 * GET 参数带 initCategory 分别对应本地卡(0)或漫游卡(1)
 *
 * SIMRechargeBillControl.java
 * @author tangming@easy2go.cn 2015-6-23
 *
 */
@Controller
@RequestMapping("/sim/simrechargebill")
public class SIMRechargeBillControl extends BaseController {
	private Logger logger = LogUtil.getInstance(SIMRechargeBillControl.class);

	/**
	 * 分页查询全部SIM卡充值记录列表 入口
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");

		// 可通过参数初始化显示为本地卡(0)或漫游卡(1)
		String initCategory = request.getParameter("initCategory");
//		SIMRechargeBill info = new SIMRechargeBill();
		if	(!StringUtils.isBlank(initCategory)) {
			if ("0".equals(initCategory)) {
//				info.setSIMCategory("本地卡");
				model.addAttribute("initCategory", 0);
			} else if ("1".equals(initCategory)) {
//				info.setSIMCategory("漫游卡");
				model.addAttribute("initCategory", 1);
			}
		}

		List<CountryInfo> countries = countryInfoSer.getAll("");
		model.addAttribute("Countries", countries);

		model.addAttribute("IsIndexView", true);

		// 目前统一使用JSP中的request直接引用 HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE 的值
		// HandlerMapping.class.getName() + ".pathWithinHandlerMapping" 即:
		// org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping
		// 来实现菜单路径显示. 此处保留作参考, 类似还有一处, {@link SIMRechargeBillControl#index()}
		// ---> 因本地和漫游充值账单的路径特殊性, 故这种情况仍然使用此接口来分配, 前端也适当修改!
		// 后面有类似情况时类似处理.
		// (20151016 类似情况的简单处理可见: /ylcyManage/src/com/Manage/control/FlowDealOrdersControl.java #info #edit 等
		// model.addAttribute("MenuPath", "/orders/flowdealorders/list"); )
		model.addAttribute("MenuPath", (String) request.getAttribute(
				HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE) + "?initCategory=" + initCategory);

		return "WEB-INF/views/sim/simrechargebill_index";

	}

	/**
	 * 分页查询全部SIM卡充值记录 ajax
	 * @param searchDTO
	 * @param info
	 * @param response
	 */
	@RequestMapping("/datapage")
	public void datapage(SearchDTO searchDTO, SIMRechargeBill info,
			HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");

		// 可通过参数初始化显示为本地卡(0)或漫游卡(1)
		String initCategory = request.getParameter("initCategory");
		if	(!StringUtils.isBlank(initCategory)) {
			if ("0".equals(initCategory)) {
				info.setSIMCategory("本地卡");
			} else if ("1".equals(initCategory)) {
				info.setSIMCategory("漫游卡");
			}
		}

		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);
		String jsonString = simRechargeBillSer.getPageString(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	/**
//	 * 全部已删除记录入口
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping("/trash")
//	public String trash(HttpServletResponse response, Model model) {
//		response.setContentType("text/html;charset=utf-8");
//		model.addAttribute("IsTrashView", true);
//		return "WEB-INF/views/sim/simrechargebill_index";
//
//	}
//
//	/**
//	 * 分页查询 已删除 ajax
//	 * @param searchDTO
//	 * @param info
//	 * @param response
//	 */
//	@RequestMapping("/trashdatapage")
//	public void dataPageDeleted(SearchDTO searchDTO, SIMRechargeBill info,
//			HttpServletResponse response) {
//		response.setContentType("application/json;charset=UTF-8");
//		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
//				searchDTO.getPageSize(), searchDTO.getSortName(),
//				searchDTO.getSortOrder(), info);
//		String jsonString = simRechargeBillSer.getPageDeletedString(seDto);
//		try {
//			response.getWriter().println(jsonString);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	/**
	 * 分页查询某张SIM卡的充值记录列表 ajax
	 * @param searchDTO
	 * @param info
	 * @param response
	 */
	@RequestMapping("/billsbysimid/{simID}")
	public void billsBySimID(@PathVariable String simID, SearchDTO searchDTO, SIMRechargeBill info,
			HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");

		info.setSIMinfoID(simID);

		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);
		String jsonString = simRechargeBillSer.getPageString(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	/**
//	 * SIM卡充值记录详情 by ID --> 目前不需要
//	 * @param id
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping("/view/{id}")
//	public String view(@PathVariable String id, Model model) {
//		SIMRechargeBill info = simRechargeBillSer.getById(id);
//		if (info != null && info.getRechargeBillID() != null) {
//			model.addAttribute("Model", info);
//		} else {
//			model.addAttribute("info","此SIM卡充值记录不存在或已无效!");
//		}
//		return "WEB-INF/views/sim/simrechargebill_view";
//	}

	/**
	 * 更新入口
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		SIMRechargeBill info = simRechargeBillSer.getById(id);
		if (info != null && info.getRechargeBillID() != null) {
			SIMInfo simInfo = simInfoSer.getById(info.getSIMinfoID());
			if	(null == simInfo) {
				model.addAttribute("info","此SIM卡不存在或已无效!");
			} else {
				if	(StringUtils.isBlank(simInfo.getMCC())) {
					model.addAttribute("Model_MCCCountryName", "");
				} else {
					CountryInfo country = countryInfoSer.getByMCC(simInfo.getMCC());
					model.addAttribute("Model_MCCCountryName", country.getCountryName());
				}

				model.addAttribute("Model", info);
				model.addAttribute("Sim", simInfo);
			}

		} else {
			model.addAttribute("info","此SIM卡充值记录不存在或已无效!");
		}
		return "WEB-INF/views/sim/simrechargebill_edit";
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
//		SIMRechargeBill info = simRechargeBillSer.getById(id);
//		if (info != null && info.getRechargeBillID() != null) {
//			model.addAttribute("Model", info);
//			model.addAttribute("IsTrashView", true);
//		} else {
//			model.addAttribute("info","此记录不存在或已无效!");
//		}
//		return "WEB-INF/views/sim/simrechargebill_edit";
//	}

	/**
	 * 新增记录入口
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/new")
	public String newEntity(SIMRechargeBill billInfo, HttpServletRequest request, HttpServletResponse response,
			Model model) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");

		String id = billInfo.getSIMinfoID();
		String category = billInfo.getSIMCategory();
		if (!StringUtils.isBlank(category)) {
			try {
				category = new String(category.getBytes("ISO-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		SIMInfo simInfo = null;
		if (null != id && null != category) {
			if ("本地卡".equals(category)) {
				simInfo = simInfoSer.getById(billInfo.getSIMinfoID());
			} else if ("漫游卡".equals(category)){
				simInfo = roamingSimInfoSer.getById(billInfo.getSIMinfoID());
			}

			if (simInfo != null && simInfo.getSIMinfoID() != null) {
				if	(StringUtils.isBlank(simInfo.getMCC())) {
					model.addAttribute("Model_MCCCountryName", "");
				} else {
					CountryInfo country = countryInfoSer.getByMCC(simInfo.getMCC());
					model.addAttribute("Model_MCCCountryName", country.getCountryName());
				}

				model.addAttribute("Model", simInfo);
			} else {
				model.addAttribute("info","此SIM卡不存在或已无效!");
			}
		} else {
			model.addAttribute("info","未指定SIM卡ID和SIM卡使用类型, 请联系管理员!");
		}


		return "WEB-INF/views/sim/simrechargebill_new";
	}

	/**
	 * 保存记录 新增new或编辑edit提交时统一使用此接口
	 * 通过 boolean isInsert 来相应处理
	 * @param info
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void saveAction(SIMRechargeBill info, HttpServletRequest request,
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
		if (StringUtils.isBlank(info.getRechargeBillID())) {
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
		// SIMInfo simInfo = null; // 改由编辑入口时查询传递

		// try {
		if (isInsert) {
			info.setRechargeBillID(getUUID());
			info.setSysStatus(1);

			result = simRechargeBillSer.insertInfo(info);
		} else {
// 改由编辑入口时查询传递, 且应该改为使用 SIMinfiID 去查询, 因为只有它系非空.
//			simInfo = simInfoSer.getByImsi(info.getIMSI());
//			if	(null == simInfo) {
//				logger.debug("此IMSI对应的SIM卡不存在");
//				result = false;
//			} else {
//				info.setSIMinfoID(simInfo.getSIMinfoID());
				result = simRechargeBillSer.updateInfo(info);
//			}
		}
		// } catch (Exception e) {
		// result = false;
		// e.printStackTrace();
		// }

		if (result) {
			logger.debug("SIM卡充值记录保存成功");
			try {
				// response.getWriter().println("成功保存SIM卡充值记录!");
				jsonResult.put("code", 0);
				jsonResult.put("msg", "成功保存SIM卡充值记录!");

//				if (!isInsert && null != simInfo) { // 改由编辑入口时查询传递
//					jsonResult.put("simid", simInfo.getSIMinfoID()); // 编辑时带上跳转该SIM卡充值记录
//				}
				jsonResult.put("simid", info.getSIMinfoID());

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
					admin.setOperateContent("添加了SIM卡充值记录, 记录ID为: " + info.getRechargeBillID() + " 金额: " + info.getRechargeAmount()); //操作内容
					admin.setOperateMenu("SIM卡管理>添加SIM卡充值记录"); //操作菜单
					admin.setOperateType("添加");//操作类型
				} else {
					admin.setOperateContent("修改了SIM卡充值记录, 记录ID为: " + info.getRechargeBillID() + " 金额: " + info.getRechargeAmount());
					admin.setOperateMenu("SIM卡管理>修改SIM卡充值记录");
					admin.setOperateType("修改");
				}

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else {
			logger.debug("SIM卡充值记录保存失败");
			try {
				// response.getWriter().println("保存SIM卡充值记录出错, 请重试!");
				jsonResult.put("code", 1);
				jsonResult.put("msg", "保存SIM卡充值记录出错, 请重试!");
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

		SIMRechargeBill info = new SIMRechargeBill();
		info.setRechargeBillID(id);
		info.setSysStatus(0);

		if(simRechargeBillSer.updateInfoSysStatus(info)){
			try {
				response.getWriter().println("SIM卡充值记录删除成功!");
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

				admin.setOperateContent("删除了SIM卡充值记录, 记录ID为: " + info.getRechargeBillID() + " 金额: " + info.getRechargeAmount());
				admin.setOperateMenu("SIM卡管理>删除SIM卡充值记录");
				admin.setOperateType("删除");

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else{
			try {
				response.getWriter().println("SIM卡充值记录删除出错!");
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
	public void restore(@PathVariable String id, HttpServletResponse response, Model model) {
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

		SIMRechargeBill info = new SIMRechargeBill();
		info.setRechargeBillID(id);
		info.setSysStatus(1);

		if(simRechargeBillSer.updateInfoSysStatus(info)){
			try {
				response.getWriter().println("SIM卡充值记录恢复成功!");
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

				admin.setOperateContent("恢复了SIM卡充值记录, 记录ID为: " + info.getRechargeBillID() + " 金额: " + info.getRechargeAmount());
				admin.setOperateMenu("SIM卡管理>恢复SIM卡充值记录");
				admin.setOperateType("恢复");

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else{
			try {
				response.getWriter().println("SIM卡充值记录恢复出错!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	/**
	 * SIM卡充值账单入口
	 * @param model
	 * @return
	 */
	@RequestMapping("/summary")
	public String summary(HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");

		List<CountryInfo> countries = countryInfoSer.getAll("");
		model.addAttribute("Countries", countries);

		List<OperatorInfo> operators = operatorInfoSer.getAll("");
		model.addAttribute("Operators", operators);

		model.addAttribute("IsIndexView", true);
		return "WEB-INF/views/sim/simrechargebill_summary";

	}

	/**
	 * 充值账单统计
	 * @param info
	 * @param response
	 */
	@RequestMapping("/statistics")
	public void ordercount(SIMRechargeBill info,HttpServletResponse response){
		try {
			response.setContentType("application/json;charset=UTF-8");

			// 统计充值SIM卡数
			int countRechargedSim =simRechargeBillSer.countRechargedSim(info);
			// 统计充值笔数
			int countRechargedBill=simRechargeBillSer.countRechargedBill(info);
			// 合计充值总金额
			double sumRechargedBill =simRechargeBillSer.sumRechargedBill(info);

			JSONObject object=new JSONObject();
			JSONArray jsonArray=new JSONArray();
			JSONObject obj=new JSONObject();
			obj.put("countRechargedSim", countRechargedSim);
			obj.put("countRechargedBill", countRechargedBill);
			obj.put("sumRechargedBill", sumRechargedBill);
			obj.put("countRechargedSim", countRechargedSim);
			obj.put("countRechargedBill", countRechargedBill);
			obj.put("sumRechargedBill", sumRechargedBill);
			jsonArray.add(obj);
			object.put("data",jsonArray);
			object.put("success",true);
			object.put("totalRows",1);

			response.getWriter().print(object.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
