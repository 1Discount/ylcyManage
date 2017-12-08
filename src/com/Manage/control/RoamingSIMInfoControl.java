package com.Manage.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.Manage.common.constants.Constants;
import com.Manage.common.util.CountryUtils;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.DownLoadUtil;
import com.Manage.common.util.ExcelUtils;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.common.util.ByteUtils.Bytes;
import com.Manage.common.util.ByteUtils.StringValueConversionException;
import com.Manage.common.util.ExcelUtils.RoamingSIMInfoImportConstants;
import com.Manage.common.util.ExcelUtils.SIMInfoImportConstants;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.Dictionary;
import com.Manage.entity.ExchangeKey;
import com.Manage.entity.SIMInfo;
import com.Manage.entity.SIMServer;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;
import com.Manage.entity.report.SimStatusStatByCountry;
import com.Manage.entity.report.SimStatusStatByOperator;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

@Controller
@RequestMapping("/sim/roamingsiminfo")
public class RoamingSIMInfoControl extends BaseController {
	private Logger logger = LogUtil.getInstance(RoamingSIMInfoControl.class);

	/**
	 * 分页查询SIM卡列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(String SN, HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");

		List<CountryInfo> countries = countryInfoSer.getAll("");
		List<SIMServer> simServers = simServerSer.getAll("");

		model.addAttribute("SN",SN);
		model.addAttribute("Countries", countries);
		model.addAttribute("SIMServers", simServers);
        List<Dictionary> cardStatus = dictionarySer.getAllList(Constants.DICT_ROAMINGSIMINFO_CARD_STATUS);
        model.addAttribute("CardStatusDict", cardStatus);

		model.addAttribute("IsIndexView", true);
		return "WEB-INF/views/sim/roamingsiminfo_index";

	}

	/**
	 * 分页查询流量或余额预警SIM卡列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/index/alert")
	public String indexOfAlert(HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");

		List<CountryInfo> countries = countryInfoSer.getAll("");
		List<SIMServer> simServers = simServerSer.getAll("");

		model.addAttribute("Countries", countries);
		model.addAttribute("SIMServers", simServers);
        List<Dictionary> cardStatus = dictionarySer.getAllList(Constants.DICT_ROAMINGSIMINFO_CARD_STATUS);
        model.addAttribute("CardStatusDict", cardStatus);

		model.addAttribute("IsIndexView", true);
		return "WEB-INF/views/sim/roamingsiminfo_index_alert";

	}

	/**
	 * 分页查询
	 * @param searchDTO
	 * @param info
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/datapage")
	public void datapage(SearchDTO searchDTO, SIMInfo info,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("utf-8");

		// 可通过参数获取IP地址过滤
		String serverIP = request.getParameter("serverIP");
		if	(!StringUtils.isBlank(serverIP)) {
			info.setServerIP(serverIP);
		}

		// 可通过参数进行流量或余额预警过滤
		// 目前预警大小系 余额 低于或等于 50 , 剩余流量 低于或等于 200MB
		String op = request.getParameter("op");
		if ("getAlert".equals(op)) {
			info.setCardBalance(50.0);
			info.setPlanRemainData(200 * 1024); // 单位KB
		}

//		// 可通过参数国家ID过滤 待添加
//		String MCC = request.getParameter("mcc");
//		if	(!StringUtils.isBlank(MCC)) {
//			info.setMCC(MCC);
//		}

		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);
		String jsonString = roamingSimInfoSer.getPageString(seDto);
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

		List<CountryInfo> countries = countryInfoSer.getAll("");
		List<SIMServer> simServers = simServerSer.getAll("");

		model.addAttribute("Countries", countries);
		model.addAttribute("SIMServers", simServers);
        List<Dictionary> cardStatus = dictionarySer.getAllList(Constants.DICT_ROAMINGSIMINFO_CARD_STATUS);
        model.addAttribute("CardStatusDict", cardStatus);

		model.addAttribute("IsTrashView", true);
		return "WEB-INF/views/sim/roamingsiminfo_index";

	}

	/**
	 * 分页查询 已删除
	 * @param searchDTO
	 * @param info
	 * @param response
	 */
	@RequestMapping("/trashdatapage")
	public void dataPageDeleted(SearchDTO searchDTO, SIMInfo info,
			HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);
		String jsonString = roamingSimInfoSer.getPageDeletedString(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * SIM卡详情 by ID
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/view/{id}")
	public String view(@PathVariable String id, Model model) {
		SIMInfo info = roamingSimInfoSer.getById(id);
		if (info != null && info.getSIMinfoID() != null) {
			if	(StringUtils.isBlank(info.getMCC())) {
				model.addAttribute("Model_MCCCountryName", "");
			} else {
				CountryInfo country = countryInfoSer.getByMCC(info.getMCC());
				if (null != country) {
					model.addAttribute("Model_MCCCountryName", country.getCountryName());
				}
			}
			if	(StringUtils.isBlank(info.getServerIP())) {
				model.addAttribute("Model_serverCode", "");
			} else {
				model.addAttribute("Model_serverCode", StringUtils.right(info.getServerIP(), 3));
			}

			List<CountryInfo> countries = countryInfoSer.getAll("");
			List<SIMServer> simServers = simServerSer.getAll("");

			if	(!StringUtils.isBlank(info.getCountryList())) {
				info.setCountryList(CountryUtils.getCountryNamesFromSiminfoCountryList(info.getCountryList(), countries));
			}

			model.addAttribute("Model", info);
			model.addAttribute("Countries", countries);
			model.addAttribute("SIMServers", simServers);
		} else {
			model.addAttribute("info","此SIM卡不存在或已无效!");
		}
		return "WEB-INF/views/sim/roamingsiminfo_view";
	}

	/**
	 * 更新入口
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		SIMInfo info = roamingSimInfoSer.getById(id);
		if (info != null && info.getSIMinfoID() != null) {

			List<CountryInfo> countries = countryInfoSer.getAll("");
			// 标记选中 selected 的项
			String countryString = info.getCountryList();
            if  (!StringUtils.isBlank(countryString)) {
                for (CountryInfo country : countries) {
                    // MCC 都系3位, 所以不需要前后补',' --> 因为带有价格数字, 所以必须前后补','来判断
                	// --> 因为 SIMInfo 的值形如 455|456 , 所以不必要去前后补','
                    // String matchString = "," + String.valueOf(country.getCountryCode()) + ",";
                    String matchString = String.valueOf(country.getCountryCode());
                    if  (StringUtils.contains(countryString, matchString)) {
                        country.setSelected(true);
                    }
                }
                // 如果以减号开头则为排除，要区分一下
                if(StringUtils.startsWith(countryString, "-")) {
                    model.addAttribute("ExcludeCountries", "1");
                }
            }
            model.addAttribute("Countries", countries);


			List<SIMServer> simServers = simServerSer.getAll("");
			model.addAttribute("SIMServers", simServers);

	        List<Dictionary> cardStatus = dictionarySer.getAllList(Constants.DICT_ROAMINGSIMINFO_CARD_STATUS);
	        model.addAttribute("CardStatusDict", cardStatus);
//	        List<Dictionary> simTypes = dictionarySer.getAllList(Constants.DICT_SIMINFO_SIM_TYPE);
//	        model.addAttribute("SIMTypeDict", simTypes);
	        List<Dictionary> simBillMethods = dictionarySer.getAllList(Constants.DICT_SIMINFO_SIM_BILL_METHOD);
	        model.addAttribute("SimBillMethodDict", simBillMethods);

			model.addAttribute("Model", info);
		} else {
			model.addAttribute("info","此SIM卡不存在或已无效!");
		}
		return "WEB-INF/views/sim/roamingsiminfo_edit";
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
//		SIMInfo info = roamingSimInfoSer.getById(id);
//		if (info != null && info.getSIMinfoID() != null) {
//			List<CountryInfo> countries = countryInfoSer.getAll("");
//			List<SIMServer> simServers = simServerSer.getAll("");
//
//			// 标记选中 selected 的项
//			String countryString = info.getCountryList();
//            if  (!StringUtils.isBlank(countryString)) {
//                for (CountryInfo country : countries) {
//                    // MCC 都系3位, 所以不需要前后补',' --> 因为带有价格数字, 所以必须前后补','来判断
//                	// --> 因为 SIMInfo 的值形如 455|456 , 所以不必要去前后补','
//                    // String matchString = "," + String.valueOf(country.getCountryCode()) + ",";
//                    String matchString = String.valueOf(country.getCountryCode());
//                    if  (StringUtils.contains(countryString, matchString)) {
//                        country.setSelected(true);
//                    }
//                }
//            }
//
//			model.addAttribute("Countries", countries);
//			model.addAttribute("SIMServers", simServers);
//	        List<Dictionary> cardStatus = dictionarySer.getAllList(Constants.DICT_ROAMINGSIMINFO_CARD_STATUS);
//	        model.addAttribute("CardStatusDict", cardStatus);
//	        List<Dictionary> simTypes = dictionarySer.getAllList(Constants.DICT_SIMINFO_SIM_TYPE);
//	        model.addAttribute("SIMTypeDict", simTypes);
//	        List<Dictionary> simBillMethods = dictionarySer.getAllList(Constants.DICT_SIMINFO_SIM_BILL_METHOD);
//	        model.addAttribute("SimBillMethodDict", simBillMethods);
//
//			model.addAttribute("Model", info);
//			model.addAttribute("IsTrashView", true);
//		} else {
//			model.addAttribute("info","此记录不存在或已无效!");
//		}
//		return "WEB-INF/views/sim/roamingsiminfo_edit";
//	}

	/**
	 * 新增记录入口
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/new")
	public String newEntity(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");

        List<Dictionary> cardStatus = dictionarySer.getAllList(Constants.DICT_ROAMINGSIMINFO_CARD_STATUS);
        model.addAttribute("CardStatusDict", cardStatus);
//        List<Dictionary> simTypes = dictionarySer.getAllList(Constants.DICT_SIMINFO_SIM_TYPE);
//        model.addAttribute("SIMTypeDict", simTypes);
        List<Dictionary> simBillMethods = dictionarySer.getAllList(Constants.DICT_SIMINFO_SIM_BILL_METHOD);
        model.addAttribute("SimBillMethodDict", simBillMethods);

		// 可设定缺省值, 但同时要在前端做好处理. 目前仅完成了按服务器IP
		SIMInfo info = new SIMInfo();
		String serverIP = request.getParameter("serverIP");
		String simServerID = request.getParameter("SIMServerID");
		if	(!StringUtils.isBlank(serverIP) && !StringUtils.isBlank(simServerID)) {
			info.setServerIP(serverIP);
			info.setSIMServerID(simServerID);
		}
		info.setSIMCategory("漫游卡");
		info.setCardStatus(cardStatus.get(0).getLabel()); // 1 - 可用
		info.setSimBillMethod(simBillMethods.get(1).getLabel()); // 0 - 预付费 1-后付费 // 现默认后付费
//		try {
//			info.setPlanData((int)Bytes.valueOf("1G").kilobytes());
//		} catch (StringValueConversionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
			info.setPlanData(100 * 1024); // 1G = 1024 * 1024 KB = 1048576 KB 单位 KB 漫游卡默认100MB
//		}
		//info.setPlanPrice(1.0); // 为了避免创建套餐金额为零, 使用一个更适合的默认值 或者前端使用空白
		info.setSIMIfActivated("否");
		info.setPlanIfActivated("否");
		model.addAttribute("Model", info);

		List<CountryInfo> countries = countryInfoSer.getAll("");
		List<SIMServer> simServers = simServerSer.getAll("");
		model.addAttribute("Countries", countries);
		model.addAttribute("SIMServers", simServers);

		//return "WEB-INF/views/sim/roamingsiminfo_new";
        return "WEB-INF/views/sim/roamingsiminfo_edit"; // 统一使用roamingsiminfo_edit
	}

	/**
	 * 保存记录 新增new或编辑edit提交时统一使用此接口
	 * 通过 boolean isInsert 来相应处理
	 * @param info
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void saveAction(SIMInfo info, HttpServletRequest request,
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
		if (StringUtils.isBlank(info.getSIMinfoID())) {
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

		String imsiString = info.getIMSI();
		if (null == imsiString) {
			info.setIMSI("");
			info.setMNC("");
		} else {
			// 截取 MNC 等
			if (imsiString.length() == 18) { // 验证长度
				info.setMNC(StringUtils.substring(imsiString, 6, 8));
			}
		}
		if (null == info.getLastDeviceSN()) {
			info.setLastDeviceSN("");
		}
		if (StringUtils.isBlank(info.getPlanRemainData())) {
			info.setPlanRemainData(info.getPlanData());
		}

		// 从表单传过来的 MCC 列表值, 结合存在的国家列表 输出需要的字段值
        List<CountryInfo> countries = countryInfoSer.getAll("");
        //info.setCountryList(CountryUtils.getCountryStringFromMCCListForSiminfo(info.getCountryList(), countries));
        // 20160116 支持减号时，直接字符串替换即可
        if(null == info.getCountryList()) {
            info.setCountryList("");
        } else {
            info.setCountryList(StringUtils.replaceChars(info.getCountryList(), ",", "|"));
        }

		if (StringUtils.isBlank(info.getPlanActivateDate())) {
			info.setPlanActivateDate(null);
			info.setPlanIfActivated("否");
		} else {
		    info.setPlanIfActivated("是");
		}
		if (StringUtils.isBlank(info.getPlanEndDate())) {
			info.setPlanEndDate(null);
		}
		if (StringUtils.isBlank(info.getSIMActivateDate())) {
			info.setSIMActivateDate(null);
			info.setSIMIfActivated("否");
		} else {
		    info.setSIMIfActivated("是");
		}
		if (StringUtils.isBlank(info.getSIMEndDate())) {
			info.setSIMEndDate(null);
		}

		boolean result;

		// try {
		if (isInsert) {
			info.setSIMinfoID(getUUID());
			info.setSysStatus(1);

			result = roamingSimInfoSer.insertInfo(info);
		} else {
			result = roamingSimInfoSer.updateInfo(info);
		}
		// } catch (Exception e) {
		// result = false;
		// e.printStackTrace();
		// }

		if (result) {
			logger.debug("SIM卡信息保存成功");
			try {
				// response.getWriter().println("成功保存SIM卡信息!");
				jsonResult.put("code", 0);
				jsonResult.put("msg", "成功保存SIM卡信息!");
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
					admin.setOperateContent("添加了漫游SIM卡, 记录ID为: " + info.getSIMinfoID() + " ICCID: " + info.getICCID()); //操作内容
					admin.setOperateMenu("漫游SIM卡管理>添加SIM卡"); //操作菜单
					admin.setOperateType("添加");//操作类型
				} else {
					admin.setOperateContent("修改了漫游SIM卡, 记录ID为: " + info.getSIMinfoID() + " ICCID: " + info.getICCID());
					admin.setOperateMenu("漫游SIM卡管理>修改SIM卡");
					admin.setOperateType("修改");
				}

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else {
			logger.debug("SIM卡信息保存失败");
			try {
				// response.getWriter().println("保存SIM卡信息出错, 请重试!");
				jsonResult.put("code", 1);
				jsonResult.put("msg", "保存SIM卡信息出错, 请重试!");
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

		SIMInfo info = new SIMInfo();
		info.setSIMinfoID(id);
		info.setSysStatus(0);

		if(roamingSimInfoSer.updateInfoSysStatus(info)){
			try {
				response.getWriter().println("SIM卡删除成功!");
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

				admin.setOperateContent("删除了漫游SIM卡, 记录ID为: " + info.getSIMinfoID() + " ICCID: " + info.getICCID());
				admin.setOperateMenu("漫游IM卡管理>删除SIM卡");
				admin.setOperateType("删除");

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else{
			try {
				response.getWriter().println("SIM卡删除出错!");
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

		SIMInfo info = new SIMInfo();
		info.setSIMinfoID(id);
		info.setSysStatus(1);

		if(roamingSimInfoSer.updateInfoSysStatus(info)){
			try {
				response.getWriter().println("SIM卡恢复成功!");
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

				admin.setOperateContent("恢复了漫游SIM卡, 记录ID为: " + info.getSIMinfoID() + " ICCID: " + info.getICCID());
				admin.setOperateMenu("漫游SIM卡管理>恢复SIM卡");
				admin.setOperateType("恢复");

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else{
			try {
				response.getWriter().println("SIM卡恢复出错!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	/**
	 * SIM卡状态统计图表
	 * @param model
	 * @return
	 */
	@RequestMapping("/status/chart")
	public String statusChart(HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");

		List<SimStatusStatByCountry> result;
		boolean checkResultFailed = false;
		// 统计SIM卡总量
		SIMInfo info = new SIMInfo();
		result = roamingSimInfoSer.getSimStatusStatByCountry(info);

		if (null != result) {
			// 统计各种状态SIM卡数量, 注意合并结果时要求SQL的查询结果时已确保排序
			List<SimStatusStatByCountry> temp;

			List<Dictionary> cardStatus = dictionarySer.getAllList(Constants.DICT_ROAMINGSIMINFO_CARD_STATUS);

			// "可用"卡数量
			info.setCardStatus(cardStatus.get(0).getLabel()); // 可用
			temp = roamingSimInfoSer.getSimStatusStatByCountry(info);
			if (null != temp && temp.size() == result.size()) { // 检查总数系为了前后统计的数据保持一致 可重新考虑
				for (int i = 0; i < temp.size(); i++) {
					result.get(i).setAvalableSimsCount(temp.get(i).getSimsTotalCount());
				}
			} else {
				checkResultFailed = true;
			}

			// "使用中"卡数量
			if (!checkResultFailed) {
				info.setCardStatus(cardStatus.get(1).getLabel()); //("使用中");
				temp = roamingSimInfoSer.getSimStatusStatByCountry(info);
				if (null != temp && temp.size() == result.size()) {
					for (int i = 0; i < temp.size(); i++) {
						result.get(i).setUsingSimsCount(temp.get(i).getSimsTotalCount());
					}
				} else {
					checkResultFailed = true;
				}
			}

			// "不可用"卡数量
			if (!checkResultFailed) {
				info.setCardStatus(cardStatus.get(2).getLabel()); //("不可用");
				temp = roamingSimInfoSer.getSimStatusStatByCountry(info);
				if (null != temp && temp.size() == result.size()) {
					for (int i = 0; i < temp.size(); i++) {
						result.get(i).setUnavalableSimsCount(temp.get(i).getSimsTotalCount());
					}
				} else {
					checkResultFailed = true;
				}
			}

//			// "停用"卡数量 --> 不使用停用状态
//			if (!checkResultFailed) {
//				info.setCardStatus("停用");
//				temp = roamingSimInfoSer.getSimStatusStatByCountry(info);
//				if (null != temp && temp.size() == result.size()) {
//					for (int i = 0; i < temp.size(); i++) {
//						result.get(i).setDeadSimsCount(temp.get(i).getSimsTotalCount());
//					}
//				} else {
//					checkResultFailed = true;
//				}
//			}

			// 流量/余额预警卡数量
			// 目前预警大小系 余额 低于或等于 50 , 剩余流量 低于或等于 200MB
			if (!checkResultFailed) {
				info.setCardStatus("");
				info.setCardBalance(50.0);
				info.setPlanRemainData(200 * 1024); // 单位KB
				temp = roamingSimInfoSer.getSimStatusStatByCountry(info);
				if (null != temp && temp.size() == result.size()) {
					for (int i = 0; i < temp.size(); i++) {
						result.get(i).setAlertSimsCount(temp.get(i).getSimsTotalCount());
					}
				} else {
					checkResultFailed = true;
				}
			}

		} else { // 统计总数已出错
			checkResultFailed = true;
		}

		if (checkResultFailed) {
			model.addAttribute("info","统计出错, 请重试!");
		} else {
			//List<CountryInfo> countries = countryInfoSer.getAll("");
			//model.addAttribute("Countries", countries);
			//List<SIMServer> simServers = simServerSer.getAll("");
			//model.addAttribute("SIMServers", simServers);

			for (SimStatusStatByCountry country : result) {
				country.caculatePercents(true);
			}
			model.addAttribute("SimStatusStatByCountry", result);
		}

		return "WEB-INF/views/sim/roamingsiminfo_status";

	}

	/**
	 * SIM卡统计报表入口页 --> 改为 AJAX
	 *
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/statistics")
	public String statisticsIndex(HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");
		return "WEB-INF/views/sim/roamingsiminfo_statistics";

// 以下为非 ajax 情形, 可暂保留
//		List<SimStatusStatByCountry> result;
//		boolean checkResultFailed = false;
//		// 统计SIM卡总量
//		SIMInfo info = new SIMInfo();
//		result = roamingSimInfoSer.getSimStatusStatByCountry(info);
//
//		if (null != result) {
//			// 统计各种状态SIM卡数量, 注意合并结果时要求SQL的查询结果时已确保排序
//			List<SimStatusStatByCountry> temp;
//
//			// "可用"卡数量
//			info.setCardStatus("可用");
//			temp = roamingSimInfoSer.getSimStatusStatByCountry(info);
//			if (null != temp && temp.size() == result.size()) { // 检查总数系为了前后统计的数据保持一致 可重新考虑
//				for (int i = 0; i < temp.size(); i++) {
//					result.get(i).setAvalableSimsCount(temp.get(i).getSimsTotalCount());
//				}
//			} else {
//				checkResultFailed = true;
//			}
//
//			// "使用中"卡数量
//			if (!checkResultFailed) {
//				info.setCardStatus("使用中");
//				temp = roamingSimInfoSer.getSimStatusStatByCountry(info);
//				if (null != temp && temp.size() == result.size()) {
//					for (int i = 0; i < temp.size(); i++) {
//						result.get(i).setUsingSimsCount(temp.get(i).getSimsTotalCount());
//					}
//				} else {
//					checkResultFailed = true;
//				}
//			}
//
//			// "不可用"卡数量
//			if (!checkResultFailed) {
//				info.setCardStatus("不可用");
//				temp = roamingSimInfoSer.getSimStatusStatByCountry(info);
//				if (null != temp && temp.size() == result.size()) {
//					for (int i = 0; i < temp.size(); i++) {
//						result.get(i).setUnavalableSimsCount(temp.get(i).getSimsTotalCount());
//					}
//				} else {
//					checkResultFailed = true;
//				}
//			}
//
////			// "停用"卡数量 --> 不使用停用状态
////			if (!checkResultFailed) {
////				info.setCardStatus("停用");
////				temp = roamingSimInfoSer.getSimStatusStatByCountry(info);
////				if (null != temp && temp.size() == result.size()) {
////					for (int i = 0; i < temp.size(); i++) {
////						result.get(i).setDeadSimsCount(temp.get(i).getSimsTotalCount());
////					}
////				} else {
////					checkResultFailed = true;
////				}
////			}
//
//			// 流量/余额预警卡数量
//			// 目前预警大小系 余额 低于或等于 50 , 剩余流量 低于或等于 200MB
//			if (!checkResultFailed) {
//				info.setCardStatus("");
//				info.setCardBalance(50.0);
//				info.setPlanRemainData(200 * 1024); // 单位KB
//				temp = roamingSimInfoSer.getSimStatusStatByCountry(info);
//				if (null != temp && temp.size() == result.size()) {
//					for (int i = 0; i < temp.size(); i++) {
//						result.get(i).setAlertSimsCount(temp.get(i).getSimsTotalCount());
//					}
//				} else {
//					checkResultFailed = true;
//				}
//			}
//
//		} else { // 统计总数已出错
//			checkResultFailed = true;
//		}
//
//		if (checkResultFailed) {
//			model.addAttribute("info","统计出错, 请重试!");
//		} else {
//			//List<CountryInfo> countries = countryInfoSer.getAll("");
//			//model.addAttribute("Countries", countries);
//			//List<SIMServer> simServers = simServerSer.getAll("");
//			//model.addAttribute("SIMServers", simServers);
//
//			for (SimStatusStatByCountry country : result) {
//				country.caculatePercents(true);
//			}
//
//
//			model.addAttribute("SimStatusStatByCountry", result);
//		}
//
//		return "WEB-INF/views/sim/roamingsiminfo_statistics";
	}

	/**
	 * 全部国家各种状态SIM卡数 AJAX 接口
	 *
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/statistics/count")
	public void statisticsAllCount(SearchDTO searchDTO, SIMInfo info, HttpServletResponse response) {
		try {

			// 目前预警大小系 余额 低于或等于 50 , 剩余流量 低于或等于 200MB
			info.setCardBalance(50.0);
			info.setPlanRemainData(200 * 1024); // 单位KB

			Map<String, String> map = roamingSimInfoSer
					.allCount(info);
			JSONObject object = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			JSONObject obj = new JSONObject();
			if (map != null) {
				obj.put("count", map.get("count"));		// SIM卡总数
				obj.put("alert", map.get("alert"));		// 充值预警
				obj.put("status_available", map.get("status_available")); // 可用 -> 正常
				obj.put("status_using", map.get("status_using"));			// 使用中 -> 欠费
				obj.put("status_unavailable", map.get("status_unavailable")); // 不可用 -> 损失或欠费
			}
			jsonArray.add(obj);
			object.put("data", jsonArray);
			object.put("success", true);
			object.put("totalRows", 1);
			response.getWriter().print(object.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 按国家统计各种状态SIM卡数 AJAX 接口
	 *
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/statistics/getpagebycountry")
	public void statisticsGetPageByCountry(SearchDTO searchDTO, SIMInfo info, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");

		List<SimStatusStatByCountry> result = null;
		boolean checkResultFailed = false;

		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);

		// 统计SIM卡总量
		Page page = roamingSimInfoSer.getPageOfSimStatusStatByCountry(seDto);
		if (null != page) {
		result = (List<SimStatusStatByCountry>) page.getRows();
		}

		if (null != result) {
			// 统计各种状态SIM卡数量, 注意合并结果时要求SQL的查询结果时已确保排序
			List<SimStatusStatByCountry> temp;

			List<Dictionary> cardStatus = dictionarySer.getAllList(Constants.DICT_ROAMINGSIMINFO_CARD_STATUS);

            // "可用"卡数量
            info.setCardStatus(cardStatus.get(0).getLabel()); // 可用
			seDto.setObj(info);
			temp = (List<SimStatusStatByCountry>) roamingSimInfoSer.getPageOfSimStatusStatByCountry(seDto).getRows();
			if (null != temp && temp.size() == result.size()) { // 检查总数系为了前后统计的数据保持一致 可重新考虑
				for (int i = 0; i < temp.size(); i++) {
					result.get(i).setAvalableSimsCount(temp.get(i).getSimsTotalCount());
				}
			} else {
				checkResultFailed = true;
			}

			// "使用中"卡数量
			if (!checkResultFailed) {
				info.setCardStatus(cardStatus.get(1).getLabel()); //("使用中");
				seDto.setObj(info);
				temp = (List<SimStatusStatByCountry>) roamingSimInfoSer.getPageOfSimStatusStatByCountry(seDto).getRows();
				if (null != temp && temp.size() == result.size()) {
					for (int i = 0; i < temp.size(); i++) {
						result.get(i).setUsingSimsCount(temp.get(i).getSimsTotalCount());
					}
				} else {
					checkResultFailed = true;
				}
			}

			// "不可用"卡数量
			if (!checkResultFailed) {
				info.setCardStatus(cardStatus.get(2).getLabel()); //("不可用");
				seDto.setObj(info);
				temp = (List<SimStatusStatByCountry>) roamingSimInfoSer.getPageOfSimStatusStatByCountry(seDto).getRows();
				if (null != temp && temp.size() == result.size()) {
					for (int i = 0; i < temp.size(); i++) {
						result.get(i).setUnavalableSimsCount(temp.get(i).getSimsTotalCount());
					}
				} else {
					checkResultFailed = true;
				}
			}

//			// "停用"卡数量 --> 不使用停用状态
//			if (!checkResultFailed) {
//				info.setCardStatus("停用");
//				seDto.setObj(info);
//				temp = (List<SimStatusStatByCountry>) roamingSimInfoSer.getPageOfSimStatusStatByCountry(seDto).getRows();
//				if (null != temp && temp.size() == result.size()) {
//					for (int i = 0; i < temp.size(); i++) {
//						result.get(i).setDeadSimsCount(temp.get(i).getSimsTotalCount());
//					}
//				} else {
//					checkResultFailed = true;
//				}
//			}

			// 流量/余额预警卡数量
			// 目前预警大小系 余额 低于或等于 50 , 剩余流量 低于或等于 200MB
			if (!checkResultFailed) {
				info.setCardStatus("");
				info.setCardBalance(50.0);
				info.setPlanRemainData(200 * 1024); // 单位KB
				seDto.setObj(info);
				temp = (List<SimStatusStatByCountry>) roamingSimInfoSer.getPageOfSimStatusStatByCountry(seDto).getRows();
				if (null != temp && temp.size() == result.size()) {
					for (int i = 0; i < temp.size(); i++) {
						result.get(i).setAlertSimsCount(temp.get(i).getSimsTotalCount());
					}
				} else {
					checkResultFailed = true;
				}
			}
		} else { // 统计总数已出错
			checkResultFailed = true;
		}

		try {
			if (checkResultFailed) {
				//model.addAttribute("info","统计出错, 请重试!");
				response.getWriter().println("");
			} else {
				//List<CountryInfo> countries = countryInfoSer.getAll("");
				//model.addAttribute("Countries", countries);
				//List<SIMServer> simServers = simServerSer.getAll("");
				//model.addAttribute("SIMServers", simServers);

				for (SimStatusStatByCountry country : result) {
					country.caculatePercents(false); // true
				}

				JSONObject object = new JSONObject();
				object.put("success", true);
				object.put("totalRows", page.getTotal());
				object.put("curPage", page.getCurrentPage());
				JSONArray ja = new JSONArray();
				for (SimStatusStatByCountry a : result) {
					JSONObject obj = JSONObject.fromObject(a);
//					obj.put("countryName", mccNameMap.get(a.getMCC())); // 不做 left join, 手动设置国家名称, 或者设法交由前端去处理
					ja.add(obj);
				}
				object.put("data", ja);

				//model.addAttribute("SimStatusStatByCountry", result);
				response.getWriter().println(object.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 按运营商SIM卡数统计 AJAX 接口
	 *
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/statistics/getpagebyoperator")
	public void statisticsGetPageByOperator(SearchDTO searchDTO, SIMInfo info, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");

		List<SimStatusStatByOperator> result = null;
		boolean checkResultFailed = false;

		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);

		// 统计SIM卡总量
		Page page = roamingSimInfoSer.getPageOfSimStatusStatByOperator(seDto);
		if (null != page) {
			result = (List<SimStatusStatByOperator>) page.getRows();
		}

		if (null != result) {
			// 统计各种状态SIM卡数量, 注意合并结果时要求SQL的查询结果时已确保排序
			List<SimStatusStatByOperator> temp;

			List<Dictionary> cardStatus = dictionarySer.getAllList(Constants.DICT_ROAMINGSIMINFO_CARD_STATUS);

			// "可用"卡数量
			info.setCardStatus(cardStatus.get(0).getLabel()); //("可用");
			seDto.setObj(info);
			temp = (List<SimStatusStatByOperator>) roamingSimInfoSer.getPageOfSimStatusStatByOperator(seDto).getRows();
			if (null != temp && temp.size() == result.size()) { // 检查总数系为了前后统计的数据保持一致 可重新考虑
				for (int i = 0; i < temp.size(); i++) {
					result.get(i).setAvalableSimsCount(temp.get(i).getSimsTotalCount());
				}
			} else {
				checkResultFailed = true;
			}

			// "使用中"卡数量
			if (!checkResultFailed) {
				info.setCardStatus(cardStatus.get(1).getLabel()); //("使用中");
				seDto.setObj(info);
				temp = (List<SimStatusStatByOperator>) roamingSimInfoSer.getPageOfSimStatusStatByOperator(seDto).getRows();
				if (null != temp && temp.size() == result.size()) {
					for (int i = 0; i < temp.size(); i++) {
						result.get(i).setUsingSimsCount(temp.get(i).getSimsTotalCount());
					}
				} else {
					checkResultFailed = true;
				}
			}

			// "不可用"卡数量
			if (!checkResultFailed) {
				info.setCardStatus(cardStatus.get(2).getLabel()); //("不可用");
				seDto.setObj(info);
				temp = (List<SimStatusStatByOperator>) roamingSimInfoSer.getPageOfSimStatusStatByOperator(seDto).getRows();
				if (null != temp && temp.size() == result.size()) {
					for (int i = 0; i < temp.size(); i++) {
						result.get(i).setUnavalableSimsCount(temp.get(i).getSimsTotalCount());
					}
				} else {
					checkResultFailed = true;
				}
			}

//			// "停用"卡数量 --> 不使用停用状态
//			if (!checkResultFailed) {
//				info.setCardStatus("停用");
//				seDto.setObj(info);
//				temp = (List<SimStatusStatByOperator>) roamingSimInfoSer.getPageOfSimStatusStatByOperator(seDto).getRows();
//				if (null != temp && temp.size() == result.size()) {
//					for (int i = 0; i < temp.size(); i++) {
//						result.get(i).setDeadSimsCount(temp.get(i).getSimsTotalCount());
//					}
//				} else {
//					checkResultFailed = true;
//				}
//			}

			// 流量/余额预警卡数量
			// 目前预警大小系 余额 低于或等于 50 , 剩余流量 低于或等于 200MB
			if (!checkResultFailed) {
				info.setCardStatus("");
				info.setCardBalance(50.0);
				info.setPlanRemainData(200 * 1024); // 单位KB
				seDto.setObj(info);
				temp = (List<SimStatusStatByOperator>) roamingSimInfoSer.getPageOfSimStatusStatByOperator(seDto).getRows();
				if (null != temp && temp.size() == result.size()) {
					for (int i = 0; i < temp.size(); i++) {
						result.get(i).setAlertSimsCount(temp.get(i).getSimsTotalCount());
					}
				} else {
					checkResultFailed = true;
				}
			}
		} else { // 统计总数已出错
			checkResultFailed = true;
		}

		try {
			if (checkResultFailed) {
				//model.addAttribute("info","统计出错, 请重试!");
				response.getWriter().println("");
			} else {
				//List<CountryInfo> countries = countryInfoSer.getAll("");
				//model.addAttribute("Countries", countries);
				//List<SIMServer> simServers = simServerSer.getAll("");
				//model.addAttribute("SIMServers", simServers);

//				int unknownOperatorCount = 0; // 用来保存未指定运营商的数目 未完成
// 或许, 应该考虑使用 distinct 去统计

				for (SimStatusStatByOperator item : result) {
					item.caculatePercents(false); // true
//					unknownOperatorCount += item.getSimsTotalCount();
				}

//				// 增加一行显示那些“未指定运营商”的数量，但运营商管理、添加SIM卡时选择运营商等必须加强 未完成
//				SimStatusStatByOperator unkownOperator = new SimStatusStatByOperator(
//						"", "未指定运营商",
//						"", "", "", "未指定运营商",
//						0, 0, 0,
//						0, 0,
//						0.0, 0.0,
//						0.0, 0.0,
//						0, false
//						);

				JSONObject object = new JSONObject();
				object.put("success", true);
				object.put("totalRows", page.getTotal());
				object.put("curPage", page.getCurrentPage());
				JSONArray ja = new JSONArray();
				for (SimStatusStatByOperator a : result) {
					JSONObject obj = JSONObject.fromObject(a);
//					obj.put("countryName", mccNameMap.get(a.getMCC())); // 不做 left join, 手动设置国家名称, 或者设法交由前端去处理
					ja.add(obj);
				}
				object.put("data", ja);

				//model.addAttribute("SimStatusStatByCountry", result);
				response.getWriter().println(object.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 批量导入SIM卡信息入口 -> 漫游SIM卡
	 */
    @RequestMapping("/importRoamingSim")
    public String importEntryRoamingSim(HttpServletRequest req, HttpSession session, Model model){
		model.addAttribute("LogMessage", req.getSession().getAttribute("excelmessage"));
    	req.getSession().setAttribute("excelmessage", ""); // 及时清空, 避免污染其他页面
        return "WEB-INF/views/sim/siminfo_batch_import_roaming";
    }

    /**
     * 批量导入SIM卡 AJAX 接口 -> 漫游SIM卡
     *
     * @param file
     * @param req
     * @param resp
     * @param session
     * @param model
     * @return
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws IOException
     * @throws InterruptedException
     */
	@RequestMapping(value = "/startImportRoamingSim", method = RequestMethod.POST)
	public String startImportRoamingSim(@RequestParam("file") MultipartFile file,
			HttpServletRequest req, HttpServletResponse resp,
			HttpSession session, Model model) throws SQLException, FileNotFoundException,
			IOException, InterruptedException {
		System.out.println("得到上传excel请求");

		// 20160106 按广超同吴工需求打开支持更新
		boolean usingUpadte = true; //false; //true; // 是否使用更新 初步实现不使用, 若需要, 则可打开. 已经过测试, 但可能需要更多小心的处理要补充

		String lastDeviceSNSuffix = Constants.DICT_DEVICE_SN; // "17215021000"; // 目前实现支持SN号为4位省略SN或15位完整SN, 前者的前部固定为 "17215021000" // 若要支持5位的类似做法, 可继续类似添加判
		String redirectString = "redirect:importRoamingSim";

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (adminUserInfo == null) {
			req.getSession().setAttribute("excelmessage", "请重新登录！");
			return redirectString;
		}

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH");
		String logoPathDir = "/files" + dateformat.format(new Date());
		// 得到excel保存目录的真实路径
		String temp = request.getSession().getServletContext()
				.getRealPath(logoPathDir);
		// 创建文件保存路径文件夹
		File tempFile = new File(temp);

		System.out.println("这个是上传文件的真实路径temp：" + temp);
		// 这个是上传文件的真实路径temp：E:\.......\tomcat\webapps\ylcyManage\files2015\09\10\15

		MultipartFile multipartFile = multipartRequest.getFile("file");

		if (!tempFile.exists()) {
			tempFile.mkdirs();
		}
		// 获取文件后缀名
		String suffix = multipartFile.getOriginalFilename().substring(
				multipartFile.getOriginalFilename().lastIndexOf("."));
		// 构建文件名称
		String logExcelName = multipartFile.getOriginalFilename();

		/** 拼成完整的文件保存路径加文件 **/
		String fileName = temp + File.separator + logExcelName;
		File files = new File(fileName);
		try {
			multipartFile.transferTo(files);
		} catch (IllegalStateException e) {
			e.printStackTrace();

			req.getSession().setAttribute("excelmessage", "上传出错！请重试或联系管理员");
			return redirectString;
		} catch (IOException e) {
			e.printStackTrace();

			req.getSession().setAttribute("excelmessage", "上传出错！请重试或联系管理员");
			return redirectString;
		}
		System.out.println("上传完毕.");

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SS");
		TimeZone t = sdf.getTimeZone();
		t.setRawOffset(0);
		sdf.setTimeZone(t);

		// 开始时间 // <del>应该提前, 包括上传时间</del>
		Long startTime = System.currentTimeMillis();

		String[][] result = ExcelUtils.getData(files, 2); // 此模板开始两行均为标题
		int allDatacount = result.length;		// 原始数据的记录总数
		// 大概判定这个模板不是所需要的模板 或者可以把这个差值再加大一点, 例如差值大于3, 等
		// 另外, POI库读出的列最后总有一个空白列, 所以下面至少需要 +1 . 目前允许差值扩大到 2
		if (allDatacount == 0) {
			req.getSession().setAttribute("excelmessage", "输入的表格除标题外无数据, 或错误去掉了标题. 请按要求只填充数据.");
			return redirectString;
		} else if (result[0].length > RoamingSIMInfoImportConstants.TEMPLAGE_FILE_CHECK_COL_NUMBERS + 1 + 2 ||
				result[0].length < RoamingSIMInfoImportConstants.TEMPLAGE_FILE_CHECK_COL_NUMBERS) {
			req.getSession().setAttribute("excelmessage", "目前读出数据表的列数量与目标模板的列数量差距大, 请确认是否输错模板?");
			return redirectString;
		}

		// 统计条数
		// 原则上, 下面出错的全部记录, 需要记录到日志信息, 反馈给前端用户, 参考记录号系模板中的"序号"
		// 目前, 只作插入新记录, 其他情况如SIM卡ICCID/设备的SN已存在, 不会更新该记录 -->
		//
		// ---> 20150828 根据ICCID不唯一的要求修改：
		/**
			<p>导入要求、规则更改历史： (2015-08-27) ICCID 可能不唯一，所以改用 IMSI 号作必要字段。并且：（1）一般情况是这样，先插入一系列SIM卡后服务器
			会读取出 IMSI 和 ICCID 号，然后该系列卡需要从这里批量导入必要的 SIM 卡信息之后，才能正常使用。（2） 少数情况，可到SIM卡编辑手动添加SIM卡
			或者，插入 SIM 卡后，从全部SIM卡列表查询到该卡，再进行手动编辑。（3）这样，批量导入模板需要以 IMSI 号作必要字段，即必须提供 IMSI 才能正常
			导入。</p>
		 */

		int keyEmptyCount = 0;		// 关键的字段空白, 这种不能作为记录添加. 这即系"无效数据", 如设备SN缺失
		int requiredInvalidCount = 0;	// 必需的字段缺少(没有默认值的)或格式不准确, 这种可根据设定的策略先添加到数据库,
										// 但必须设备一些状态使其暂不可用, 如"SIM卡状态"设置为不可用, 等等
		int insertFailedCount = 0;	// 数据库插入出现错误, 最好能够按各种出错原因细分, 如ICCID/设备SN重复, 则原始那条数据不插入, 及其他各种情况
		int insertOKCount = 0;	// 成功插入条数
		int insertRecordCount = 0;	// 原始记录中需要插入的记录总数: 应该等于 insertFailedCount + insertOKCount

		int updateFailedCount = 0;	// 更新失败数量
		int updateOKCount = 0; // 更新成功数量
		//int updateRecordCount; // 原始记录中相同IMSI的记录已存在, 需要更新的记录总数: 应该等于 updateFailedCount + updateOKCount,
			// 也应该等于 allDatacount - keyEmptyCount - insertRecordCount

		// 下列的值对应导入模板中的首列: 序号列 的值
		List<Integer> keyEmptyIDs = new ArrayList<Integer>();
		List<Integer> keyInvalidIDs = new ArrayList<Integer>(); // 细分关键字段是缺失还是格式不对,但数量目前仍记录到 keyEmptyCount
		List<Integer> requiredInvalidIDs = new ArrayList<Integer>(); // 必需字段缺少有效值, 可继续插入但可能作一些特别处理
		List<Integer> insertFailedIDs = new ArrayList<Integer>(); // 插入失败的序号ID
		List<Integer> updateFailedIDs = new ArrayList<Integer>(); // 更新失败的序号ID

		// ------连接数据库-------
		Connection conn = null;
		PreparedStatement pst = null;

		// ahming marks: 因为认为 update 时应该较好支持那种若填入值空白时则不更新该字段的需求, 这样的话, 参数不固定,
		// 所以不适宜使用 PreparedStatement , 所以使用 Statement
		Statement stmtUpdate = null;
		String sqlUpdateString, sqlUpdateFields;

			try {
				conn = getDBConn();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				req.getSession().setAttribute("excelmessage", "无法连接数据库！请重试或联系管理员");
				return redirectString;
			}
//			conn.setAutoCommit(false); // !IMPORTANT 若改为逐条提交后, 这个必须去掉

			/** importType 区分更新系涉及全部列（值为0）还是只是 ICCID/phone 两列（值为1） */
			String importType = request.getParameter("importType");
			if (StringUtils.isBlank(importType)) {
                importType = "0";
            }

			String category = request.getParameter("simcategory");
			String tableString;
			boolean isSIMInfoNotRoamingSIMInfo;
//			if	(StringUtils.isBlank(category) || "0".equals(category)) {
//				//category = "0";
//				tableString = " SIMInfo ";
//				isSIMInfoNotRoamingSIMInfo = true;
//			} else if ("1".equals(category)) {
				tableString = " RoamingSIMInfo ";
				isSIMInfoNotRoamingSIMInfo = false;
//			} else {
//				tableString = " SIMInfo ";
//				isSIMInfoNotRoamingSIMInfo = true;
//			}

			// TODO: 在插入或更新之前，先判断好记录的 IMSI 号是否已在表中
			List<Boolean> isMainKeyExits = new ArrayList<Boolean>();
			if (usingUpadte) {
				for (int i = 0; i < allDatacount; i++) {
					// 是否适合检查ICCID的长度?
					if (result[i][RoamingSIMInfoImportConstants.COL_ICCID] == ""
							/*|| result[i][RoamingRoamingSIMInfoImportConstants.COL_ICCID].length() != 19*/) {
						keyEmptyCount ++;
						keyEmptyIDs.add(Integer.valueOf(result[i][0]));

						isMainKeyExits.add(i, null); // 这是空记录
					} else if (result[i][RoamingSIMInfoImportConstants.COL_ICCID].length() != 19) {
						keyEmptyCount ++;
						keyInvalidIDs.add(Integer.valueOf(result[i][0]));

						isMainKeyExits.add(i, null); // 这是空记录
					} else {
						// 可以优化吗？
						if (roamingSimInfoSer.isExistPrimaryKey(result[i][RoamingSIMInfoImportConstants.COL_ICCID])) {
							isMainKeyExits.add(i, true);
						} else {
							isMainKeyExits.add(i, false);
							insertRecordCount++;
						}
					}
				}
			} else {
				for (int i = 0; i < allDatacount; i++) {
					// 是否适合检查ICCID的长度？
					if (result[i][RoamingSIMInfoImportConstants.COL_ICCID] == ""
							/*|| result[i][RoamingSIMInfoImportConstants.COL_ICCID].length() != 19*/) {
						keyEmptyCount ++;
						keyEmptyIDs.add(Integer.valueOf(result[i][0]));

						isMainKeyExits.add(i, null); // 这是空记录
					} else if (result[i][RoamingSIMInfoImportConstants.COL_ICCID].length() != 19) {
						keyEmptyCount ++;
						keyInvalidIDs.add(Integer.valueOf(result[i][0]));

						isMainKeyExits.add(i, null); // 这是空记录
					} else {
						isMainKeyExits.add(i, false); // 设置为false都假定为插入
						insertRecordCount++;
					}
				}
			}

			try {
			// 此处插入语句的拼合次序与Excel导入模板的列次序基本一致, 其他则需要手动加入的放后部
		    // --> 要保证这里的顺序与 ExcelUtils 中 RoamingSIMInfoImportConstants 类中的顺序"严格一致" 因为下面引用时系按那里的值填充 pst
			// --> 20160116 增加可选国家和激活时间两个字段 +两个问号
			pst = (PreparedStatement) conn
					.prepareStatement("insert into " + tableString + " (simAlias,lastDeviceSN,ICCID,phone,PUK,PIN,planEndDate,SIMActivateDate,remark,countryList," + // 8(8)
							"cardStatus,trademark,planType,cardBalance,planData,planRemainData," + // 6(14)
							"SIMInfoID,SIMServerID,serverIP,SIMCategory,SIMIfActivated,creatorUserID,creatorUserName,creatorDate,sysStatus) " + // 8(22)
							" values (?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?, ?,?,?,?,1)"); // 23

//			String[][] result = ExcelUtils.getData(files, 2); // 此模板开始两行均为标题
//			allDatacount = result.length; // 原始数据的记录总数

			SimpleDateFormat sdfdate = new SimpleDateFormat(); // 格式化时间
			sdfdate.applyPattern("yyyy-MM-dd HH:mm:ss"); // a为AM/PM的标记

//			String MNCSegment; // 从IMSI中截取
//			String MCCSegment;
			Boolean cardStatusAvailable = true;
			long parseBytesValue;
			double parseDoubleValue;
			int parseIntValue;
			Boolean requiredInvalidFlag = false;

			// 可判断使用状态等
	        List<Dictionary> cardStatus = dictionarySer.getAllList(Constants.DICT_ROAMINGSIMINFO_CARD_STATUS);

			// TODO:目前未检查某些字段的值是否在可允许的值之路, 例如 SIMCategory 只能为"本地卡"或"漫游卡" .
			// 后面应该加好.

			// 注意: pst.setString 的索引从 1 开始
			for (int i = 0; i < allDatacount; i++) {
				if (null == isMainKeyExits.get(i)) {
					continue; // 无效
				} else if (isMainKeyExits.get(i)) {
					// 更新的记录

					//sqlUpdateString = "UPDATE "+ tableString + "SET ";
					sqlUpdateFields = "";

					if (!"1".equals(importType)) { // 为1时只更新 ICCID和phone两列
    					if (StringUtils.isNotBlank(result[i][RoamingSIMInfoImportConstants.COL_simAlias])) {
    						sqlUpdateFields += " simAlias='" + result[i][RoamingSIMInfoImportConstants.COL_simAlias] + "', ";
    					}
    					if (StringUtils.isNotBlank(result[i][RoamingSIMInfoImportConstants.COL_lastDeviceSN])) {
    						if (4 == result[i][RoamingSIMInfoImportConstants.COL_lastDeviceSN].length()) {
    							result[i][RoamingSIMInfoImportConstants.COL_lastDeviceSN] = lastDeviceSNSuffix + result[i][RoamingSIMInfoImportConstants.COL_lastDeviceSN];
    						} // 若要支持5位以上的类似做法, 可继续类似添加判断. 或做成更通用的

    						// TODO: 可能有必要检查SN是否系15位
    						sqlUpdateFields += " lastDeviceSN='" + result[i][RoamingSIMInfoImportConstants.COL_lastDeviceSN] + "', ";
    					}
					}

					if (StringUtils.isNotBlank(result[i][RoamingSIMInfoImportConstants.COL_phone])) {
						sqlUpdateFields += " phone='" + result[i][RoamingSIMInfoImportConstants.COL_phone] + "', ";
					}

					if (!"1".equals(importType)) { // 为1时只更新 ICCID和phone两列

    					if (StringUtils.isNotBlank(result[i][RoamingSIMInfoImportConstants.COL_PUK])) {
    						sqlUpdateFields += " PUK='" + result[i][RoamingSIMInfoImportConstants.COL_PUK] + "', ";
    					}
    					if (StringUtils.isNotBlank(result[i][RoamingSIMInfoImportConstants.COL_PIN])) {
    						sqlUpdateFields += " PIN='" + result[i][RoamingSIMInfoImportConstants.COL_PIN] + "', ";
    					}
    					// --> 20160118 现在激活时间需要使用了，所以改用 planEndDate 去保存, 同时这里 SIMActivateDate 保留
    					if (StringUtils.isNotBlank(result[i][RoamingSIMInfoImportConstants.COL_planEndDate])) {
                            sqlUpdateFields += " planEndDate='" + result[i][RoamingSIMInfoImportConstants.COL_planEndDate] + "', ";
                        }
    					if (StringUtils.isNotBlank(result[i][RoamingSIMInfoImportConstants.COL_SIMActivateDate])) {
    						sqlUpdateFields += " SIMActivateDate='" + result[i][RoamingSIMInfoImportConstants.COL_SIMActivateDate] + "', ";
    						//sqlUpdateFields += " SIMIfActivated='是', "; // 导入的都默认已激活，后面
    					}
    					if (StringUtils.isNotBlank(result[i][RoamingSIMInfoImportConstants.COL_remark])) {
    						sqlUpdateFields += " remark='" + result[i][RoamingSIMInfoImportConstants.COL_remark] + "', ";
    					}
    					// --> 20160118 增加可使用国家字段（可否支持识别 all/ALL/*等指示全部国家的简易符号？为空则默认为所有国家？->为空则表示全球通用，直接送值即可）
                        if (StringUtils.isNotBlank(result[i][RoamingSIMInfoImportConstants.COL_countryList])) {
                            sqlUpdateFields += " countryList='" + result[i][RoamingSIMInfoImportConstants.COL_countryList] + "', ";
                        }

    					if (StringUtils.isNotBlank(result[i][RoamingSIMInfoImportConstants.COL_cardStatus])) {
    						sqlUpdateFields += " cardStatus='" + result[i][RoamingSIMInfoImportConstants.COL_cardStatus] + "', ";
    					}
    //					if (StringUtils.isNotBlank(result[i][RoamingSIMInfoImportConstants.COL_APN])) {
    //						sqlUpdateFields += " APN='" + result[i][RoamingSIMInfoImportConstants.COL_APN] + "', ";
    //					}
    //					if (StringUtils.isNotBlank(result[i][RoamingSIMInfoImportConstants.COL_MCC])) {
    //						sqlUpdateFields += " MCC='" + result[i][RoamingSIMInfoImportConstants.COL_MCC] + "', ";
    //					}
    					if (StringUtils.isNotBlank(result[i][RoamingSIMInfoImportConstants.COL_trademark])) {
    						sqlUpdateFields += " trademark='" + result[i][RoamingSIMInfoImportConstants.COL_trademark] + "', ";
    					}
    					if (StringUtils.isNotBlank(result[i][RoamingSIMInfoImportConstants.COL_cardBalance])) {
    						try {
    							parseDoubleValue = Double.valueOf(result[i][RoamingSIMInfoImportConstants.COL_cardBalance]);
    							sqlUpdateFields += " cardBalance=" + parseDoubleValue + ", ";
    						} catch (NumberFormatException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();

    							// TODO: 与相关人员协定处理 -> 是不改变旧的值还是设置为零?
    							//parseDoubleValue = 0.0;
    							//sqlUpdateFields += " cardBalance=" + parseDoubleValue + ", ";
    						}
    					}
    					if (StringUtils.isNotBlank(result[i][RoamingSIMInfoImportConstants.COL_planType])) {
    						sqlUpdateFields += " planType='" + result[i][RoamingSIMInfoImportConstants.COL_planType] + "', ";
    					}
    					if (StringUtils.isNotBlank(result[i][RoamingSIMInfoImportConstants.COL_planData])) {
    						try {
    							parseBytesValue = (long)Bytes.valueOf(result[i][RoamingSIMInfoImportConstants.COL_planData]).kilobytes();
    							sqlUpdateFields += " planData=" + parseBytesValue + ", ";
    						} catch (StringValueConversionException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();

    							// TODO: 与相关人员协定处理
    							// continue; // 不适宜! --> 目前若格式不对, 不处理这个字段, 但后期应该记录并反馈给用户
    						}
    					}
    					if (StringUtils.isNotBlank(result[i][RoamingSIMInfoImportConstants.COL_planRemainData])) {
    						try {
    							parseBytesValue = (long)Bytes.valueOf(result[i][RoamingSIMInfoImportConstants.COL_planRemainData]).kilobytes();
    							sqlUpdateFields += " planRemainData=" + parseBytesValue + ", ";
    						} catch (StringValueConversionException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();

    							// TODO: 与相关人员协定处理
    							// continue; // 不适宜! --> 目前若格式不对, 不处理这个字段, 但后期应该记录并反馈给用户
    						}
    					}
    				}

					if (StringUtils.isBlank(sqlUpdateFields)) {
						continue; // 全部空白的行
					}

					sqlUpdateFields += " modifyUserID='" + adminUserInfo.getUserID() + "', modifyDate='" + sdfdate.format(new Date()).toString() + "'";

					sqlUpdateString = "UPDATE "+ tableString + "SET " + sqlUpdateFields + " WHERE ICCID='"
							+ result[i][RoamingSIMInfoImportConstants.COL_ICCID] + "' LIMIT 1";

					stmtUpdate = conn.createStatement();

					try {
						stmtUpdate.executeUpdate(sqlUpdateString);
						updateOKCount ++;
					} catch (SQLException e) {
						// TODO: handle exception
						e.printStackTrace();
						updateFailedCount ++;
						updateFailedIDs.add(Integer.valueOf(result[i][0]));
					}

					// 每行记录需要关闭?
					if (null != stmtUpdate) {
						try {
							stmtUpdate.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				} else { // 插入的记录
					// 根据数据库表字段的不同可以做修改
					// ahming notes: ExcelUtils.getData() 中所有需要trim的值已trim, 所以这里不需要重复trim

// --> 前面已作了处理, 这里不再需要, 优化去掉
//				// ICCID 对漫游卡来说系主键
//				if (result[i][RoamingSIMInfoImportConstants.COL_ICCID] == "") {
//					keyEmptyCount ++;
//					keyEmptyIDs.add(result[i][0]);
//					continue; // ICCID 系关键数据, 若空则此记录无效, 跳过
//				}
				pst.setString(RoamingSIMInfoImportConstants.COL_ICCID, result[i][RoamingSIMInfoImportConstants.COL_ICCID]); // ICCID

				pst.setString(RoamingSIMInfoImportConstants.COL_simAlias, result[i][RoamingSIMInfoImportConstants.COL_simAlias]); // simAlias

				if (4 == result[i][RoamingSIMInfoImportConstants.COL_lastDeviceSN].length()) {
					result[i][RoamingSIMInfoImportConstants.COL_lastDeviceSN] = lastDeviceSNSuffix + result[i][RoamingSIMInfoImportConstants.COL_lastDeviceSN];
				} // 若要支持5位以上的类似做法, 可继续类似添加判断. 或做成更通用的
				pst.setString(RoamingSIMInfoImportConstants.COL_lastDeviceSN, result[i][RoamingSIMInfoImportConstants.COL_lastDeviceSN]);

				pst.setString(RoamingSIMInfoImportConstants.COL_phone, result[i][RoamingSIMInfoImportConstants.COL_phone]); // phone
				pst.setString(RoamingSIMInfoImportConstants.COL_PUK, result[i][RoamingSIMInfoImportConstants.COL_PUK]); // PUK
				pst.setString(RoamingSIMInfoImportConstants.COL_PIN, result[i][RoamingSIMInfoImportConstants.COL_PIN]); // PIN

				// 注意: 此字段对应 购卡时间 : 暂确定使用 SIMActivateDate SIM卡激活时间来保存这个日期, 所以,
				// 凡是导入的数据, 都应该按已激活为"是"去处理
				// 目前未检测它的格式是否有效...
				// --> 20160118 现在激活时间需要使用了，所以改用 planEndDate 去保存
                if (StringUtils.isBlank(result[i][RoamingSIMInfoImportConstants.COL_planEndDate])) {
                    pst.setString(RoamingSIMInfoImportConstants.COL_planEndDate, null); // SIMActivateDate
                } else {
                    pst.setString(RoamingSIMInfoImportConstants.COL_planEndDate, result[i][RoamingSIMInfoImportConstants.COL_planEndDate]); // planEndDate
                }
				if (StringUtils.isBlank(result[i][RoamingSIMInfoImportConstants.COL_SIMActivateDate])) {
					pst.setString(RoamingSIMInfoImportConstants.COL_SIMActivateDate, null); // SIMActivateDate
				} else {
					pst.setString(RoamingSIMInfoImportConstants.COL_SIMActivateDate, result[i][RoamingSIMInfoImportConstants.COL_SIMActivateDate]); // SIMActivateDate
				}
				pst.setString(RoamingSIMInfoImportConstants.COL_remark, result[i][RoamingSIMInfoImportConstants.COL_remark]); // remark
				pst.setString(RoamingSIMInfoImportConstants.COL_countryList, result[i][RoamingSIMInfoImportConstants.COL_countryList]); // remark

////				if (cardStatusAvailable) {
				if (result[i][RoamingSIMInfoImportConstants.COL_cardStatus] == "") {
				    // (cardStatus.get(0).getLabel()); //
					result[i][RoamingSIMInfoImportConstants.COL_cardStatus] = cardStatus.get(0).getLabel(); //"可用"; // 约定的默认值都为可用 TODO: 待与何广超等确认
				}
////				} else {
////					result[i][RoamingSIMInfoImportConstants.COL_cardStatus] = "不可用";
////				}
				pst.setString(RoamingSIMInfoImportConstants.COL_cardStatus, result[i][RoamingSIMInfoImportConstants.COL_cardStatus]);

//				pst.setString(RoamingSIMInfoImportConstants.COL_APN, result[i][RoamingSIMInfoImportConstants.COL_APN]); // APN
//				pst.setString(RoamingSIMInfoImportConstants.COL_MCC, result[i][RoamingSIMInfoImportConstants.COL_MCC]); // MCC
				pst.setString(RoamingSIMInfoImportConstants.COL_trademark, result[i][RoamingSIMInfoImportConstants.COL_trademark]); // trademark

				parseDoubleValue = 0.0;
				if (result[i][RoamingSIMInfoImportConstants.COL_cardBalance] != "") {
					try {
						parseDoubleValue = Double.valueOf(result[i][RoamingSIMInfoImportConstants.COL_cardBalance]);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						parseDoubleValue = 0.0;
					}
				}
				pst.setString(RoamingSIMInfoImportConstants.COL_cardBalance, String.valueOf(parseDoubleValue)); // cardBalance

				// 这个"套餐名称"若为空, 可不可按照其他字段拼合?
				pst.setString(RoamingSIMInfoImportConstants.COL_planType, result[i][RoamingSIMInfoImportConstants.COL_planType]); // planType

				if (result[i][RoamingSIMInfoImportConstants.COL_planData] == "") {
					parseBytesValue = 0L;
					//requiredInvalidFlag = true; // 现在仅为可选
					//cardStatusAvailable = false; // 现在仅为可选
				} else {
					try {
						parseBytesValue = (long)Bytes.valueOf(result[i][RoamingSIMInfoImportConstants.COL_planData]).kilobytes();
					} catch (StringValueConversionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						parseBytesValue = 0L;
						//requiredInvalidFlag = true; // 现在仅为可选
						//cardStatusAvailable = false; // 现在仅为可选
					}
				}
				pst.setString(RoamingSIMInfoImportConstants.COL_planData, String.valueOf(parseBytesValue)); // planData

				if (result[i][RoamingSIMInfoImportConstants.COL_planRemainData] == "") {
					//requiredInvalidFlag = true; // 现在仅为可选
					//cardStatusAvailable = false; // 现在仅为可选
					parseBytesValue = 0L;
				} else {
					try {
						parseBytesValue = (long)Bytes.valueOf(result[i][RoamingSIMInfoImportConstants.COL_planRemainData]).kilobytes();
					} catch (StringValueConversionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						parseBytesValue = 0L;
						//requiredInvalidFlag = true; // 现在仅为可选
						//cardStatusAvailable = false; // 现在仅为可选
					}
				}
				pst.setString(RoamingSIMInfoImportConstants.COL_planRemainData, String.valueOf(parseBytesValue)); // planRemainData

				if (requiredInvalidFlag) {
					requiredInvalidCount ++; // 这种情况仍然尝试数据库插入
					requiredInvalidIDs.add(Integer.valueOf(result[i][0])); // 这个最后还要过滤, 期望它只包含统计已成功添加的记录
				}

				pst.setString(RoamingSIMInfoImportConstants.COL_SIMInfoID, UUID.randomUUID().toString());
				pst.setString(RoamingSIMInfoImportConstants.COL_SIMServerID, "");
				pst.setString(RoamingSIMInfoImportConstants.COL_serverIP, "");

				// SIMCategory 因为目前限定不能混合导入本地卡和漫游卡, 所以这个当由表单直接提供即可
				// 但目前未实现表单输入, 要先测试导入的情况 --> 已可通过下拉选择“本地”或“漫游卡”
				// 若模板的值为空，则相应填入类型
				if (isSIMInfoNotRoamingSIMInfo) {
					pst.setString(RoamingSIMInfoImportConstants.COL_SIMCategory, "本地卡"); // SIMCategory
				} else {
					pst.setString(RoamingSIMInfoImportConstants.COL_SIMCategory, "漫游卡"); // SIMCategory
				}

				// 要确认若不填入激活日期时是否肯定系未激活? ---> 20151020 fixed 漫游卡导入的数据都默认为已激活
//				if (result[i][RoamingSIMInfoImportConstants.COL_SIMActivateDate] == "") {
//					pst.setString(RoamingSIMInfoImportConstants.COL_SIMIfActivated, "否");
//				} else {
					pst.setString(RoamingSIMInfoImportConstants.COL_SIMIfActivated, "是");
//				}

				pst.setString(RoamingSIMInfoImportConstants.COL_creatorUserID, adminUserInfo.userID);
				pst.setString(RoamingSIMInfoImportConstants.COL_creatorUserName, adminUserInfo.userName);
				pst.setString(RoamingSIMInfoImportConstants.COL_creatorDate, sdfdate.format(new Date()).toString());

				// pst.addBatch(); //事务整体添加 -->
				// 开始数据库插入. 若用 addBatch + executeBatch 则发现若有任何一条失败, 则全部不会添加, 故不能使用
				try {
					pst.execute();
					insertOKCount ++;
				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
					insertFailedCount ++;
					insertFailedIDs.add(Integer.valueOf(result[i][0]));
				}

			  } // if (isMainKeyExits.get(i))
			}

			// 过滤因缺失关键字段的记录统计, 期望它只包含统计已成功添加的记录
//			for (String id : requiredInvalidIDs) { // java.util.ConcurrentModificationException
//				if (keyEmptyIDs.contains(id) || insertFailedIDs.contains(id) ) {
//					requiredInvalidIDs.remove(id);
//					requiredInvalidCount--;
//				}
//			}
			for (Iterator<Integer> iterator = requiredInvalidIDs.iterator(); iterator
					.hasNext();) {
				Integer id = (Integer) iterator.next();
				if (insertFailedIDs.contains(id) || keyEmptyIDs.contains(id) || keyInvalidIDs.contains(id)) {
					iterator.remove();
					requiredInvalidCount--;
				}
			}

			AdminOperate admin = new AdminOperate();
			admin.setOperateID(UUID.randomUUID().toString());	// id
			admin.setCreatorUserID(adminUserInfo.userID);		// 创建人ID
			admin.setCreatorUserName(adminUserInfo.userName);	// 创建人姓名
			if (isSIMInfoNotRoamingSIMInfo) {
				admin.setOperateContent(multipartFile.getOriginalFilename() + ", 批量导入本地SIM卡, 成功导入 " + insertOKCount + " 条, 插入失败 " + insertFailedCount +
						" 条, 无效数据 " + keyEmptyCount + " 条, 原始记录总计 " + allDatacount + " 条."); // 操作内容
			} else {
				admin.setOperateContent(multipartFile.getOriginalFilename() + ", 批量导入漫游SIM卡, 成功导入 " + insertOKCount + " 条, 插入失败 " + insertFailedCount +
						" 条, 无效数据 " + keyEmptyCount + " 条, 原始记录总计 " + allDatacount + " 条."); // 操作内容
			}

			admin.setOperateMenu("SIM卡管理>批量导入漫游SIM卡");	// 操作菜单
			admin.setOperateType("新增");	// 操作类型
//			admin.setSysStatus(1);
//			SimpleDateFormat sdfdate = new SimpleDateFormat();// 格式化时间
//			sdfdate.applyPattern("yyyy-MM-dd HH:mm:ss");// a为AM/PM的标记
//			String createtime = sdfdate.format(new Date()).toString();// 创建时间
//			admin.setCreatorDate(createtime);// 创建时间
//			admin.setOperateDate(createtime);// 操作时间
			adminOperateSer.insertdata(admin);

			System.out.println("数据库写入结束");

			} catch (SQLException e) {
				e.printStackTrace();
				req.getSession().setAttribute("excelmessage", "操作数据库出错！请重试或联系管理员");
				return redirectString;
			} finally {
				// 关闭PreparedStatement
				if (pst != null) {
					pst.close();
					pst = null;
				}
				// 关闭Connection
				if (conn != null) {
					conn.close();
					pst = null;
				}
			}

		// -----结束时间------
		Long endTime = System.currentTimeMillis();
		String usetime = sdf.format(new Date(endTime - startTime));

		String requiredInvalidIDsString = "";
		String insertFailedIDsString = "";
		String keyEmptyIDsString = "";
		if (requiredInvalidCount > 0) { // ?为什么不直接使用 requiredInvalidIDs.size()?
			Collections.sort(requiredInvalidIDs);
			requiredInvalidIDsString = "<br />关键字段缺失的记录序号: ";
			for (Integer id : requiredInvalidIDs) {
				requiredInvalidIDsString += id + ", ";
			}
		}
		if (insertFailedCount > 0) {
			Collections.sort(insertFailedIDs);
			insertFailedIDsString = "<br />插入失败的记录序号: ";
			for (Integer id : insertFailedIDs) {
				insertFailedIDsString += id + ", ";
			}
		}
		if (keyEmptyCount > 0) {
			Collections.sort(keyEmptyIDs);
			keyEmptyIDsString = "<br />无效数据的记录序号: ";
			keyEmptyIDsString += "<br />　　缺少ICCID: ";
			if (keyEmptyIDs.size() > 0) {
				for (Integer id : keyEmptyIDs) {
					keyEmptyIDsString += id + ", ";
				}
			} else {
				keyEmptyIDsString += "(无)";
			}
			keyEmptyIDsString += "<br />　　格式不对: ";
			if (keyInvalidIDs.size() > 0) {
				for (Integer id : keyInvalidIDs) {
					keyEmptyIDsString += id + ", ";
				}
			} else {
				keyEmptyIDsString += "(无)";
			}
		}

		requiredInvalidIDsString += keyEmptyIDsString + insertFailedIDsString;
		if (StringUtils.isNotBlank(requiredInvalidIDsString)) {
			requiredInvalidIDsString = "<br />" + requiredInvalidIDsString;
		}

		req.getSession().setAttribute("excelmessage", "《" +multipartFile.getOriginalFilename() + "》<br /><br />成功新插入了 <b>" + insertOKCount + "</b> 条, 并更新了 <b>" + updateOKCount + "</b> 条" +
				((isSIMInfoNotRoamingSIMInfo) ? "本地":"漫游" ) + "SIM卡数据, 共用时：" + usetime
//				+ "<br />其中, 有 <b>" + requiredInvalidCount + "</b> 条数据, 因关键字段缺失被强制设置'SIM卡状态'为'不可用'"
				+ "<br /><br />原始记录总数：<b>" + allDatacount + "</b><br />无效数据条数：<b>" + keyEmptyCount
				+ "</b><br /><br />插入记录总条数：<b>" + insertRecordCount
				+ "</b><br />插入失败条数：<b>" + insertFailedCount
				+ "</b><br /><br />更新记录总条数：<b>" + (allDatacount - keyEmptyCount - insertRecordCount)
				+ "</b><br />更新失败条数：<b>" + updateFailedCount
				+ "</b>" + requiredInvalidIDsString);

//		System.out.println("用时：" + usetime);
//		System.out.println("共导入" + allDatacount + "条数据！");

		return redirectString;
	}

	private Connection getDBConn() throws ClassNotFoundException, SQLException, IOException {
		// 读取链接数据库的内容
		InputStream in = getClass().getClassLoader().getResourceAsStream(
				"jdbc.properties");
		Properties myProperty = new Properties();
		myProperty.load(in);

		String driverClass = myProperty.getProperty("jdbc.driverClassName");
		String url = myProperty.getProperty("jdbc.url");
		String user = myProperty.getProperty("jdbc.username");
		String password = myProperty.getProperty("jdbc.password");

		//System.out.println(in + " driverClass:" + driverClass + " url:" + url
		//		+ " user:" + user + " password:" + password);

		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("数据库连接成功");
		return (Connection) java.sql.DriverManager.getConnection(url, user,
				password);
		}

    /**
     * ahming notes: 这个功能系何广超根据漫游卡管理实际情况提出的需求，在导入前手动执行将清除全部漫游卡信息！请只有在清楚问题情况下再继续清除
     * (因为漫游卡管理不同于本地SIM卡管理例如涉及在线使用等高危情况，所以可以考虑提供本接口）
     * TODO： 注意提醒做好备份! 并且必须知道，目前漫游卡表与其他表没有任何关联（按初步了解），也只有这样没有关联情况下才能这样直接
     * 清除。如果后面有开发需求进行与其他表关联，就必须相应处理，例如不要清除，或者清除后保证 SIMInfoID不变等
     *
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping("/clearAll")
    public void clearAll(/*@PathVariable String id,*/ HttpServletResponse response, Model model) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        JSONObject jsonResult = new JSONObject();

//        if(StringUtils.isBlank(id)) {
//            try {
//                response.getWriter().println("请求参数无效!");
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                logger.debug(e.getMessage());
//                e.printStackTrace();
//            }
//        }

        AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
                .getAttribute("User");

        if (adminUserInfo == null) {
            //response.getWriter().println("请重新登录!");
            jsonResult.put("code", -1);
            jsonResult.put("msg", "请重新登录!");
            response.getWriter().println(jsonResult.toString());
            return;
        }

        int result;
        if((result = roamingSimInfoSer.deleteAll()) >= 0){
            jsonResult.put("code", 0);
            jsonResult.put("msg", "成功清除了 " + result + " 条漫游卡记录!");
            response.getWriter().println(jsonResult.toString());

            try {
                AdminOperate admin = new AdminOperate();
                admin.setOperateID(UUID.randomUUID().toString());//id
                // admin.setCreatorDate(date);//创建时间
                admin.setCreatorUserID(adminUserInfo.getUserID());//创建人ID
                admin.setCreatorUserName(adminUserInfo.getUserName());//创建人姓名
                //admin.setOperateDate(date);//操作时间
                //admin.setSysStatus(1);

                admin.setOperateContent("导入前手动清除了全部漫游SIM卡, 共有漫游卡数量：" + result);
                admin.setOperateMenu("漫游SIM卡管理>漫游卡批量导入");
                admin.setOperateType("删除");

                adminOperateSer.insertdata(admin);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }else{
            jsonResult.put("code", -2);
            jsonResult.put("msg", "清除发生了错误，请重试!");
            response.getWriter().println(jsonResult.toString());
        }

    }

    /**
     * 漫游卡导出 选项有：导出全部，导出全部搜索结果
     *
     * @param phone
     * @param key
     * @param useStatus
     * @param status
     * @param type
     * @param begindate
     * @param enddate
     * @param all
     * @param sta
     * @param end
     * @param cur
     * @param pagesize
     * @param total
     * @param request
     * @param response
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/exportExcel")
    public void exportExcelRoamingSim(String simAlias,String lastDeviceSN,String ICCID,String cardStatus,String begindate,String enddate,
            String all,Integer sta,Integer end,Integer cur,Integer pagesize,Integer total,
            HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{

//        if (StringUtils.isBlank(begindate) || StringUtils.isBlank(enddate)) {
//            try {
//                response.getWriter().println("请提供开始时间和结束时间");
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            return;
//        }

        // 第一步，创建一个workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("漫游卡表一");
        sheet.setDefaultRowHeightInPoints(5000);

        sheet.setColumnWidth((short)0, (short)2000);
        sheet.setColumnWidth((short)1, (short)2500);
        sheet.setColumnWidth((short)2, (short)4200);
        sheet.setColumnWidth((short)3, (short)6000);
        sheet.setColumnWidth((short)4, (short)4000);
        sheet.setColumnWidth((short)5, (short)2800);
        sheet.setColumnWidth((short)6, (short)2800);
        sheet.setColumnWidth((short)7, (short)3500);
        sheet.setColumnWidth((short)8, (short)3500);
        sheet.setColumnWidth((short)9, (short)5000);
        sheet.setColumnWidth((short)10, (short)5000);
        sheet.setColumnWidth((short)11, (short)3000);

        sheet.setColumnWidth((short)12, (short)2500);
        sheet.setColumnWidth((short)13, (short)2500);
        sheet.setColumnWidth((short)14, (short)2500);
        sheet.setColumnWidth((short)15, (short)2500);
        sheet.setColumnWidth((short)16, (short)2500);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        int headerRows = 2; // 表头的行数, 当前有两行
        HSSFRow row = sheet.createRow((int) 0);
        // short c = 500;
        row.setHeight((short) 750);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        // 第一行表头 列明搜索参数如 开始时间 结束时间 等
        // 设置字体
        HSSFFont headfont = wb.createFont();
        headfont.setFontName("宋体");
        headfont.setFontHeightInPoints((short) 11);// 字体大小
        headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗

        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        style.setFont(headfont);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 创建一个居中格式
        style.setWrapText(true);

//        HSSFCell cell = row.createCell((short) 0);
//        cell.setCellValue("结果参数：");
//        cell.setCellStyle(style);
//        cell = row.createCell((short) 1);
//        cell.setCellValue("");
//        cell.setCellStyle(style);
//        cell = row.createCell((short) 2);
//        cell.setCellValue("时间段："); // 开始时间：
//        cell.setCellStyle(style);
//        cell = row.createCell((short) 3);
//        cell.setCellValue(begindate);
//        cell.setCellStyle(style);
//        cell = row.createCell((short) 4);
//        cell.setCellValue("到");
//        cell.setCellStyle(style);
//        cell = row.createCell((short) 5);
//        cell.setCellValue(enddate);
//        cell.setCellStyle(style);
//
//        cell = row.createCell((short) 6);
//        cell.setCellValue("手机号:");
//        cell.setCellStyle(style);
//        cell = row.createCell((short) 7);
//        cell.setCellValue(phone);
//        cell.setCellStyle(style);
//
//        cell = row.createCell((short) 8);
//        cell.setCellValue("兑换码:");
//        cell.setCellStyle(style);
//        cell = row.createCell((short) 9);
//        cell.setCellValue(key);
//        cell.setCellStyle(style);
//
//        cell = row.createCell((short) 10);
//        cell.setCellValue("状态:");
//        cell.setCellStyle(style);
//        String statusString = "";

//        if ("正常".equals(status)) {
//            statusString += "可兑换-";
//        } else if ("禁用".equals(status)) {
//            statusString += "禁用兑换-";
//        } else {
//            //
//        }
//        if ("流量账户".equals(type) || "基本账户".equals(type)
//                || "话费账户".equals(type) || "流量".equals(type)
//                || "基本".equals(type) || "话费".equals(type)) {
//            statusString += type + "-";
//        } else {
//            statusString += "全部账户-";
//        }

// 为区分全部搜索结果和全部漫游卡，移后
//        if ("1".equals(all)) {
//            statusString += "全部页";
//        } else {
//            statusString += "第" + cur + "页";
//        }
//        cell = row.createCell((short) 11);
//        cell.setCellValue(statusString);
//        cell.setCellStyle(style);

        // 第二行系表头
        row = sheet.createRow((int) 0);
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("序号");
        cell.setCellStyle(style);
        cell = row.createCell((short) 1);
        cell.setCellValue("卡编号(SIM代号)");
        cell.setCellStyle(style);
        cell = row.createCell((short) 2);
        cell.setCellValue("关联设备SN");
        cell.setCellStyle(style);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell = row.createCell((short) 3);
        cell.setCellValue("ICCID");
        cell.setCellStyle(style);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell = row.createCell((short) 4);
        cell.setCellValue("电话号码");
        cell.setCellStyle(style);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell = row.createCell((short) 5);
        cell.setCellValue("PUK");
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellStyle(style);
        cell = row.createCell((short) 6);
        cell.setCellValue("PIN");
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellStyle(style);
        cell = row.createCell((short) 7);
        cell.setCellValue("购卡时间");
        cell.setCellStyle(style);
        cell = row.createCell((short) 8);
        cell.setCellValue("激活时间");
        cell.setCellStyle(style);
        cell = row.createCell((short) 9);
        cell.setCellValue("备注(包括不限于:等)");
        cell.setCellStyle(style);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell = row.createCell((short) 10);
        cell.setCellValue("可用国家列表");
        cell.setCellStyle(style);
        cell = row.createCell((short) 11);
        cell.setCellValue("卡状态");
        cell.setCellStyle(style);
        cell = row.createCell((short) 12);
        cell.setCellValue("运营商");
        cell.setCellStyle(style);
        cell = row.createCell((short) 13);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue("当前套餐类型(套餐名称)");
        cell.setCellStyle(style);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell = row.createCell((short) 14);
        cell.setCellValue("卡内余额");
        cell.setCellStyle(style);
        cell = row.createCell((short) 15);
        cell.setCellValue("套餐流量大小");
        cell.setCellStyle(style);
        cell = row.createCell((short) 16);
        cell.setCellValue("剩余流量大小");
        cell.setCellStyle(style);

        // 第二行说明
        HSSFFont headfont1 = wb.createFont();
        headfont1.setFontHeightInPoints((short) 8);// 字体大小
        headfont1.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        style2.setFont(headfont1);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 创建一个居中格式
        style2.setWrapText(true);

        row = sheet.createRow((int) 1);
        cell = row.createCell((short) 0);
        cell.setCellValue("字段要求:序号必需! 很重要,并保证序号不重复");
        cell.setCellStyle(style2);
        cell = row.createCell((short) 1);
        cell.setCellValue("可选,最大长度10");
        cell.setCellStyle(style2);
        cell = row.createCell((short) 2);
        cell.setCellValue("可选,最大长度20.正常SN目前长度为15。可使用四位数字或十五位完整SN。若为四位，前缀为“17215021000”");
        cell.setCellStyle(style2);
        cell = row.createCell((short) 3);
        cell.setCellValue("必需,关键字段,若缺失则该行记录无效");
        cell.setCellStyle(style2);
        cell = row.createCell((short) 4);
        cell.setCellValue("可选,最大长度20");
        cell.setCellStyle(style2);
        cell = row.createCell((short) 5);
        cell.setCellValue("可选,最大长度20");
        cell.setCellStyle(style2);
        cell = row.createCell((short) 6);
        cell.setCellValue("可选,最大长度20");
        cell.setCellStyle(style2);
        cell = row.createCell((short) 7);
        cell.setCellValue("可选,严格按照下列格式,用斜杆 2015/9/2 或者其他可支持的格式");
        cell.setCellStyle(style2);
        cell = row.createCell((short) 8);
        cell.setCellValue("可选,严格按照下列格式,用斜杆 2015/9/2 或者其他可支持的格式");
        cell.setCellStyle(style2);
        cell = row.createCell((short) 9);
        cell.setCellValue("可选,最大长度100");
        cell.setCellStyle(style2);
        cell = row.createCell((short) 10);
        cell.setCellValue("可选,留空默认为全球通用，值格式形如：455|456|457 等等，以|分隔");
        cell.setCellStyle(style2);
        cell = row.createCell((short) 11);
        cell.setCellValue("可选,以下值之一:正常,欠费,损坏,丢失 （旧为：可用,使用中,停用,下架）");
        cell.setCellStyle(style2);
        cell = row.createCell((short) 12);
        cell.setCellValue("可选");
        cell.setCellStyle(style2);
        cell = row.createCell((short) 13);
        cell.setCellValue("可选,最大长度50，可以为空");
        cell.setCellStyle(style2);
        cell = row.createCell((short) 14);
        cell.setCellValue("可选,数字.这余额的更新目前只能靠手动");
        cell.setCellStyle(style2);
        cell = row.createCell((short) 15);
        cell.setCellValue("可选,严格按照带G或M或K分别对应的单位数值,导入时自动识别.目前待用,若使用则留空不理会");
        cell.setCellStyle(style2);
        cell = row.createCell((short) 16);
        cell.setCellValue("可选,严格按照带G或M或K分别对应的单位数值,导入时自动识别.目前待用,若使用则留空不理会");
        cell.setCellStyle(style2);

        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，
        //  List list = CreateSimpleExcelToDisk.getStudent();

        HSSFCellStyle style3 = wb.createCellStyle();
        style2.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 创建一个居左格式

        SIMInfo info = new SIMInfo();
        if (StringUtils.isNotBlank(simAlias)) {
            info.setSimAlias(simAlias);
        }
        if (StringUtils.isNotBlank(lastDeviceSN)) {
            info.setLastDeviceSN(lastDeviceSN);
        }
        if (StringUtils.isNotBlank(ICCID)) {
            info.setICCID(ICCID);
        }
        if (StringUtils.isNotBlank(cardStatus)) {
            info.setCardStatus(cardStatus);
        }
//        info.setStartDate(begindate);
//        info.setEndDate(enddate);

        if ("1".equals(all)) { // 全部时,则设定为第1页,页数设为一个较大的值
            cur = 1;
            pagesize = 50000;
        }
        SearchDTO seDto = new SearchDTO(cur,
                pagesize, "",
                "", info);
        List<SIMInfo> result = new ArrayList<SIMInfo>();
        Page infoPage = roamingSimInfoSer.getPageInfo(seDto);
        if (null != infoPage) {
            result = (List<SIMInfo>) infoPage.getRows();
        }
        int count = result.size();
        for (int i = 0; i < count; i++)
        {
            row = sheet.createRow((int) i + headerRows); // 1 // marks:! 这是标题行的行数, 当前有两行
            SIMInfo rowInfo = result.get(i);
            // 第四步，创建单元格，并设置值
            cell = row.createCell((short) 0);
            cell.setCellValue(i+1);
            //cell.setCellStyle(style2);
            row.createCell((short) 1).setCellValue(rowInfo.getSimAlias());
            cell = row.createCell((short) 2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(rowInfo.getLastDeviceSN());
            cell = row.createCell((short) 3);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(rowInfo.getICCID());
            cell = row.createCell((short) 4);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(rowInfo.getPhone());
            cell = row.createCell((short) 5);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(rowInfo.getPUK());
            cell = row.createCell((short) 6);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(rowInfo.getPIN());
            row.createCell((short) 7).setCellValue(rowInfo.getPlanEndDate());

            row.createCell((short) 8).setCellValue(rowInfo.getSIMActivateDate());
            row.createCell((short) 9).setCellValue(rowInfo.getRemark());

            row.createCell((short) 10).setCellValue(rowInfo.getCountryList());
            row.createCell((short) 11).setCellValue(rowInfo.getCardStatus());
            row.createCell((short) 12).setCellValue(rowInfo.getTrademark());
            row.createCell((short) 13).setCellValue(rowInfo.getPlanType());
            row.createCell((short) 14).setCellValue(rowInfo.getCardBalance());
            row.createCell((short) 15).setCellValue(rowInfo.getPlanData());
            row.createCell((short) 16).setCellValue(rowInfo.getPlanRemainData());
        }

        //download("flow-" + sn + "-" + begindate + "-" + enddate + ".xls", wb, request, response);
        try {
            String filenameString = "漫游卡导出" + DateUtils.getDate("yyyyMMddHHmmss"); // = begindate+"到"+enddate+"兑换码";
            if (StringUtils.isNotBlank(simAlias)) {
                filenameString += "-" + simAlias;
            }
            if (StringUtils.isNotBlank(lastDeviceSN)) {
                filenameString += "-" + lastDeviceSN;
            }
            if (StringUtils.isNotBlank(ICCID)) {
                filenameString += "-" + ICCID;
            }
            if (StringUtils.isNotBlank(cardStatus)) {
                filenameString += "-" + cardStatus;
            }
          if ("1".equals(all)) {
              if (count == infoPage.getTotal()) {
                  filenameString += "-全部";
              } else {
                  filenameString += "-全部搜索结果";
              }
          } else {
              filenameString += "-第" + cur + "页";
          }
            //filenameString += statusString;
            DownLoadUtil.execlExpoprtDownload(filenameString+".xls",wb, request, response);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
