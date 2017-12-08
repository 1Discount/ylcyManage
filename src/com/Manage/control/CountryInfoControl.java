package com.Manage.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import com.Manage.common.constants.Constants;
import com.Manage.common.util.DES;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.DownLoadUtil;
import com.Manage.common.util.ExcelUtils;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.CustomerInfo;
import com.Manage.entity.DeviceDealOrders;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.DeviceLogs;
import com.Manage.entity.Dictionary;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.OrdersInfo;
import com.Manage.entity.SIMInfoCountryTemp;
import com.Manage.entity.common.SearchDTO;

@Controller
@RequestMapping("/flowplan/countryinfo")
public class CountryInfoControl extends BaseController {
	private Logger logger = LogUtil.getInstance(CountryInfoControl.class);

	@Resource(name = "transactionManager")
	private PlatformTransactionManager pm;

	private TransactionStatus ts = null;
	
	/**
	 * 分页国家列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");
		List<Dictionary> continents = dictionarySer
				.getAllList(Constants.DICT_COUNTRYINFO_CONTINENT);
		model.addAttribute("Continents", continents);
		model.addAttribute("IsIndexView", true);

		// 目前统一使用JSP中的request直接引用
		// HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE 的值
		// HandlerMapping.class.getName() + ".pathWithinHandlerMapping" 即:
		// org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping
		// 来实现菜单路径显示. 此处保留作参考, 类似还有一处, {@link SIMRechargeBillControl#index()}
		model.addAttribute("MenuPath",(String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));

		return "WEB-INF/views/flowplan/countryinfo_index";

	}

	/**
	 * 分页查询
	 * 
	 * @param searchDTO
	 * @param info
	 * @param response
	 */
	@RequestMapping("/datapage")
	public void datapage(SearchDTO searchDTO, CountryInfo info,
			HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);
		String jsonString = countryInfoSer.getPageString(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 全部已删除记录入口
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/trash")
	public String trash(HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");
		List<Dictionary> continents = dictionarySer
				.getAllList(Constants.DICT_COUNTRYINFO_CONTINENT);
		model.addAttribute("Continents", continents);
		model.addAttribute("IsTrashView", true);
		return "WEB-INF/views/flowplan/countryinfo_index";

	}

	/**
	 * 分页查询 已删除
	 * 
	 * @param searchDTO
	 * @param info
	 * @param response
	 */
	@RequestMapping("/trashdatapage")
	public void dataPageDeleted(SearchDTO searchDTO, CountryInfo info,
			HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);
		String jsonString = countryInfoSer.getPageDeletedString(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 国家详情 by ID
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/view/{id}")
	public String view(@PathVariable String id, Model model) {
		CountryInfo info = countryInfoSer.getById(id);
		if (info != null && info.getCountryID() != null) {
			List<Dictionary> continents = dictionarySer
					.getAllList(Constants.DICT_COUNTRYINFO_CONTINENT);
			model.addAttribute("Continents", continents);
			model.addAttribute("Model", info);
		} else {
			model.addAttribute("info", "此国家不存在或已无效!");
		}
		return "WEB-INF/views/flowplan/countryinfo_view";
	}

	/**
	 * 国家详情 by MCC 国家编辑
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/view/mcc/{id}")
	public String viewPlan(@PathVariable String id, Model model) {
		CountryInfo info = countryInfoSer.getByMCC(id);
		if (info != null && info.getCountryID() != null) {
			List<Dictionary> continents = dictionarySer
					.getAllList(Constants.DICT_COUNTRYINFO_CONTINENT);
			model.addAttribute("Continents", continents);
			model.addAttribute("Model", info);
		} else {
			model.addAttribute("info", "此国家不存在或已无效!");
		}

		return "WEB-INF/views/flowplan/countryinfo_view";
	}

	/**
	 * 更新入口
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit/{id}")
	public String editPlan(@PathVariable String id, Model model) {
		CountryInfo info = countryInfoSer.getById(id);
		if (info != null && info.getCountryID() != null) {
			List<Dictionary> continents = dictionarySer
					.getAllList(Constants.DICT_COUNTRYINFO_CONTINENT);
			model.addAttribute("Continents", continents);
			model.addAttribute("Model", info);
		} else {
			model.addAttribute("info", "此国家不存在或已无效!");
		}
		return "WEB-INF/views/flowplan/countryinfo_edit";
	}

	// --> 目前回收站与正常全部列表里的编辑已统一, 所以不再区分. 对应编辑完毕后的返回页面, 使用 response:Referer 去跳转
	// /**
	// * 回收站中更新记录
	// *
	// * 鉴于正常记录和回收站中的记录编辑有所不同， 所以区分两个接口
	// *
	// * @param id
	// * @param model
	// * @return
	// */
	// @RequestMapping("/trash/edit/{id}")
	// public String editTrashPlan(@PathVariable String id, Model model) {
	// CountryInfo info = countryInfoSer.getById(id);
	// if (info != null && info.getCountryID() != null) {
	// List<Dictionary> continents =
	// dictionarySer.getAllList(Constants.DICT_COUNTRYINFO_CONTINENT);
	// model.addAttribute("Continents", continents);
	// model.addAttribute("Model", info);
	// model.addAttribute("IsTrashView", true);
	// } else {
	// model.addAttribute("info","此记录不存在或已无效!");
	// }
	// return "WEB-INF/views/flowplan/countryinfo_edit";
	// }

	/**
	 * 新增记录入口
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/new")
	public String newPlan(HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");
		List<Dictionary> continents = dictionarySer
				.getAllList(Constants.DICT_COUNTRYINFO_CONTINENT);
		model.addAttribute("Continents", continents);
		return "WEB-INF/views/flowplan/countryinfo_new";
	}

	/**
	 * 保存记录 新增new或编辑edit提交时统一使用此接口 通过 boolean isInsert 来相应处理
	 * 
	 * @param info
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void saveAction(CountryInfo info, HttpServletRequest request,
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
		if (StringUtils.isBlank(info.getCountryID())) {
			isInsert = true;
		}

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");

		if (adminUserInfo != null) {
			if (isInsert) {
				info.setCreatorUserID(adminUserInfo.getUserID());
				// info.setCreatorUserName(adminUserInfo.getUserName());
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

		String result;

		// try {
		if (isInsert) {
			info.setCountryID(getUUID());
			info.setSysStatus(1);

			if (StringUtils.isBlank(info.getImgsrc())) {
				info.setImgsrc("static/images/wifi/default.png");
			}

			result = countryInfoSer.insertInfo(info);
		} else {
			if (StringUtils.isBlank(info.getImgsrc())) {
				info.setImgsrc("static/images/wifi/default.png");
			}

			result = countryInfoSer.updateInfo(info);
		}
		// } catch (Exception e) {
		// result = false;
		// e.printStackTrace();
		// }

		if (Constants.common_errors_general_ok_100 == result) {
			logger.debug("国家信息保存成功");
			try {
				// response.getWriter().println("成功保存国家信息!");
				jsonResult.put("code", 0);
				jsonResult.put("msg", "成功保存国家信息!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			try {
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());// id
				// admin.setCreatorDate(date);//创建时间
				admin.setCreatorUserID(adminUserInfo.getUserID());// 创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());// 创建人姓名
				// admin.setOperateDate(date);//操作时间
				// admin.setSysStatus(1);

				if (isInsert) {
					admin.setOperateContent("添加了国家, 记录ID为: "
							+ info.getCountryID() + " 名称: "
							+ info.getCountryName()); // 操作内容
					admin.setOperateMenu("国家管理>添加国家"); // 操作菜单
					admin.setOperateType("添加");// 操作类型
				} else {
					admin.setOperateContent("修改了国家, 记录ID为: "
							+ info.getCountryID() + " 名称: "
							+ info.getCountryName());
					admin.setOperateMenu("国家管理>修改国家");
					admin.setOperateType("修改");
				}

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else {
			logger.debug("国家信息保存失败");
			try {
				// response.getWriter().println("保存国家信息出错, 请重试!");

				if (Constants.common_errors_1005.equals(result)) {
					jsonResult.put("code", 2);
					jsonResult.put("msg", "此国家编号MMC已使用, 请检查!");
				} else {
					jsonResult.put("code", 1);
					jsonResult.put("msg", "保存国家信息出错, 请重试!");
				}
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
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	public void deletePlan(@PathVariable String id,
			HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");

		if (StringUtils.isBlank(id)) {
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

		CountryInfo info = new CountryInfo();
		info.setCountryID(id);
		info.setSysStatus(0);

		if (countryInfoSer.updateInfoSysStatus(info)) {
			try {
				response.getWriter().println("国家删除成功!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			try {
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());// id
				// admin.setCreatorDate(date);//创建时间
				admin.setCreatorUserID(adminUserInfo.getUserID());// 创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());// 创建人姓名
				// admin.setOperateDate(date);//操作时间
				// admin.setSysStatus(1);

				admin.setOperateContent("删除了国家, 记录ID为: " + info.getCountryID()
						+ " 名称: " + info.getCountryName());
				admin.setOperateMenu("国家管理>删除国家");
				admin.setOperateType("删除");

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else {
			try {
				response.getWriter().println("国家删除出错!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	/**
	 * 恢复记录
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/restore/{id}")
	public void restorePlan(@PathVariable String id,
			HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");

		if (StringUtils.isBlank(id)) {
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

		CountryInfo info = new CountryInfo();
		info.setCountryID(id);
		info.setSysStatus(1);

		if (countryInfoSer.updateInfoSysStatus(info)) {
			try {
				response.getWriter().println("国家恢复成功!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			try {
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());// id
				// admin.setCreatorDate(date);//创建时间
				admin.setCreatorUserID(adminUserInfo.getUserID());// 创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());// 创建人姓名
				// admin.setOperateDate(date);//操作时间
				// admin.setSysStatus(1);

				admin.setOperateContent("恢复了国家, 记录ID为: " + info.getCountryID()
						+ " 名称: " + info.getCountryName());
				admin.setOperateMenu("国家管理>恢复国家");
				admin.setOperateType("恢复");

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else {
			try {
				response.getWriter().println("国家恢复出错!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	@RequestMapping("/getpage")
	public void getpage(SearchDTO searchDTO, CountryInfo info,String countryName, String date,String serverCode,HttpServletResponse response) {
		try {
			String SortName = searchDTO.getSortName();
			String SortOrder = searchDTO.getSortOrder();
			response.setContentType("application/json;charset=UTF-8");
			if (countryName == null || countryName.equals("")) {
				countryName = "";
			}

			/*String temp = flowDealOrdersSer.beika(searchDTO, countryName, date);
			response.getWriter().println(temp);*/
			List<SIMInfoCountryTemp> temp = flowDealOrdersSer.beika(countryName, date,serverCode);
			JSONObject object = new JSONObject();
			object.put("success", true);
			object.put("totalRows", temp.size());
			object.put("curPage",1);
			object.put("data",temp.toArray());

			response.getWriter().println(object);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@RequestMapping("/vailableorder")
	public void getVailableorder(SearchDTO searchDTO, CountryInfo info,
			String countryName, String endtime, String begintime,
			HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (endtime == null || endtime.equals("")) {
			String time = format.format(new Date());
			endtime = time + " 00:00:00";
			long currentTime = System.currentTimeMillis() + 24 * 60 * 60 * 1000;
			Date date = new Date(currentTime);
			begintime = format.format(date) + " 23:59:59";
		} else {
			endtime = endtime + " 00:00:00";
			begintime = begintime + " 23:59:59";
		}
		if (countryName == null || countryName.equals("")) {
			countryName = "";
		}
		String temp = flowDealOrdersSer.getVailable(searchDTO, countryName,
				begintime, endtime);
		try {
			response.getWriter().println(temp);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@RequestMapping("/updateCountryByName")
	public void updateCountryByName(CountryInfo countryInfo,
			HttpServletResponse response) throws IOException {
		countryInfo.setCountryName(new String(countryInfo.getCountryName()
				.getBytes("ISO-8859-1"), "UTF-8"));
		if (countryInfoSer.updateCountryByName(countryInfo) > 0) {
			response.getWriter().print("00");
		} else {
			response.getWriter().print("01");
		}
	}

	@RequestMapping("/exportexcel")
	public void exportexcel(HttpServletRequest request, String countryName,
			String continent, HttpServletResponse response)
			throws UnsupportedEncodingException {

		continent = new String(continent.getBytes("iso-8859-1"), "utf-8");
		countryName = new String(countryName.getBytes("iso-8859-1"), "utf-8");

		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("Sheet1");
		sheet.setColumnWidth((short) 0, (short) 2000);
		sheet.setColumnWidth((short) 1, (short) 3000);
		sheet.setColumnWidth((short) 2, (short) 4000);
		sheet.setColumnWidth((short) 3, (short) 3000);
		sheet.setColumnWidth((short) 4, (short) 3000);
		sheet.setColumnWidth((short) 5, (short) 3000);
		sheet.setColumnWidth((short) 6, (short) 8000);
		// sheet.setDefaultRowHeightInPoints(5000);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		row.setHeight((short) 600);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		// 设置字体
		HSSFFont headfont = wb.createFont();
		headfont.setFontName("宋体");
		headfont.setFontHeightInPoints((short) 11);// 字体大小
		headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
		HSSFCellStyle style = wb.createCellStyle();
		style.setFont(headfont);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 创建一个居中格式

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("序号");
		cell.setCellStyle(style);

		cell = row.createCell((short) 1);
		cell.setCellValue("国家码");
		cell.setCellStyle(style);

		cell = row.createCell((short) 2);
		cell.setCellValue("国家名");
		cell.setCellStyle(style);

		cell = row.createCell((short) 3);
		cell.setCellValue("大洲");
		cell.setCellStyle(style);

		cell = row.createCell((short) 4);
		cell.setCellValue("按天价格");
		cell.setCellStyle(style);

		cell = row.createCell((short) 5);
		cell.setCellValue("按流量价格");
		cell.setCellStyle(style);

		cell = row.createCell((short) 6);
		cell.setCellValue("限速策略");
		cell.setCellStyle(style);

		CountryInfo countryInfo = new CountryInfo();
		countryInfo.setCountryName(countryName);
		countryInfo.setContinent(continent);

		List<CountryInfo> countrList = countryInfoSer.exportexcel(countryInfo);

		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 创建一个居中格式
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		for (int i = 0; i < countrList.size(); i++) {
			// 第四步，创建单元格，并设置值
			row = sheet.createRow((int) i + 1);
			row.setHeight((short) 450);

			HSSFCell cell1 = row.createCell((short) 0);
			cell1.setCellValue(countrList.get(i).getSortCode());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 1);
			cell1.setCellValue(countrList.get(i).getCountryCode());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 2);
			cell1.setCellValue(countrList.get(i).getCountryName());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 3);
			cell1.setCellValue(countrList.get(i).getContinent());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 4);
			cell1.setCellValue(countrList.get(i).getFlowPrice());
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 5);
			if (StringUtils.isNotBlank(countrList.get(i).getPressFlowPrice())) {
				cell1.setCellValue(countrList.get(i).getPressFlowPrice());
			} else {
				cell1.setCellValue("");
			}
			cell1.setCellStyle(style1);

			cell1 = row.createCell((short) 6);
			cell1.setCellValue(countrList.get(i).getLimitSpeedStr());
			cell1.setCellStyle(style1);

		}
		try {
			DownLoadUtil
					.execlExpoprtDownload("国家信息.xls", wb, request, response);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 批量修改国家限速策略
	 * 
	 * @param limitSpeedStr
	 * @param countryID
	 * @throws IOException
	 */
	@RequestMapping("/batchupdate")
	public void batchupdate(String limitSpeedStr, String countryID,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		if (StringUtils.isBlank(limitSpeedStr)
				|| StringUtils.isBlank(countryID)) {
			response.getWriter().print("-1");
			logger.info("参数为空");
			return;
		}

		// 开始更改
		int successCount = 0;
		int errorCount = 0;
		String[] countryIDarray = countryID.split(",");

		for (int i = 0; i < countryIDarray.length; i++) {
			CountryInfo info = new CountryInfo();
			info.setCountryID(countryIDarray[i]);
			info.setLimitSpeedStr(limitSpeedStr);
			boolean success = countryInfoSer.updateinfotwo(info);
			if (success) {
				successCount++;
			} else {
				logger.info("修改限速策略失败，国家ID为：" + countryIDarray[i]);
				errorCount++;
			}
		}
		if (errorCount == 0 && successCount == countryIDarray.length) {
			response.getWriter().print("00");
		} else {
			response.getWriter().print("-2");
		}
	}
	
	/**
	 * 国家导入
	 *
	 * @param file
	 * @param req
	 * @param resp
	 * @param session
	 * @param model
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@RequestMapping(value = "/batchaddCountry", method = RequestMethod.POST)
	public String batchaddCountry(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse resp, HttpSession session, Model model) throws FileNotFoundException, IOException
	{

		logger.info("..导入国家得到请求..");
		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (adminUserInfo == null)
		{
			req.getSession().setAttribute("excelmessage", "请重新登录！");
			return "redirect:insert";
		}
		CommonsMultipartFile cf = (CommonsMultipartFile) file;
		DiskFileItem fi = (DiskFileItem) cf.getFileItem();
		File files = fi.getStoreLocation();
		String[][] result = null;
		try
		{
			result = ExcelUtils.getData(files, 1);
		}
		catch (OfficeXmlFileException e1)
		{
			logger.info("解析excel出错:" + e1.getMessage());
			req.getSession().setAttribute("excelmessage", "请使用execl2003表格导入数据!");
			return "redirect:index";

		}
		int allDatacount = result.length;
		if (allDatacount == 0)
		{
			req.getSession().setAttribute("excelmessage", "输入的表格除标题外无数据, 或错误去掉了标题. 请按要求只填充数据.");
			logger.info("输入的表格除标题外无数据, 或错误去掉了标题. 请按要求只填充数据.");
			return "redirect:index";
		}
		int insertOKCount = 0; // 成功插入条数
		IdentityHashMap<String, String> snMap = new IdentityHashMap<String, String>();
		String countryName = "";
		int execlrow = result.length;//
		logger.info("读取到数据行数:" + execlrow);
		//获取当前已有的国家
		List<CountryInfo> haedInfos=countryInfoSer.getAll("");
		Map<String,String> haedInfosMap=new HashMap<String,String>();
		for(CountryInfo c:haedInfos){
		    haedInfosMap.put(c.getCountryCode()+"",c.getCountryName());
		}
		int headCount=0;
		int newCount=0;
		String headStr="";
		String newStr="";
		try
		{
			logger.info("excel解析完毕.实际数据行数:" + execlrow);
			List<CountryInfo> infos=new ArrayList<CountryInfo>();
			String insertStr="";
			JSONObject object=new JSONObject();
			for (int i = 0; i < execlrow; i++)
			{
				//ts = pm.getTransaction(null);
				String mcc = result[i][0].trim();// 国家名英文简称
				String countryNameEnShort  = result[i][1].trim().trim();// 国家名
				countryName= result[i][2].trim();// mcc
				String continent = result[i][3].trim();// 大洲
				String timeDifference = result[i][4].trim().trim();// 时差
				//insertStr+="INSERT INTO countryinfotemp VALUES ('"+UUID.randomUUID().toString()+"', '"+countryName+"', '"+mcc+"', '"+continent+"', '0', '0', '0.00', '', '', '0.00', '', '0', '批量导入', '', now(), '', now(), '1', '', '', '', '', '', '', '"+countryNameEnShort+"', '', '', '"+timeDifference+"');";
				
				
				if(haedInfosMap.get(mcc)!=null && !haedInfosMap.get(mcc).isEmpty()){
				    headCount+=1;
				    headStr+=(i+1)+":"+countryName+",";
				}else{
				    newCount+=1;
				    newStr+=(i+1)+":"+countryName+",";
				   // System.out.println("INSERT INTO countryinfotemp VALUES ('"+UUID.randomUUID().toString()+"', '"+countryName+"', '"+mcc+"', '"+continent+"', '0', '0', '0.00', '', '', '0.00', '', '0', '批量导入', '', now(), '', now(), '1', '', '', '', '', '', '', '"+countryNameEnShort+"', '', '', '"+timeDifference+"');");
				    //System.out.println("country_name_"+countryNameEnShort+"="+countryNameEnShort);
				    System.out.println("INSERT INTO `countryinfo` VALUES ('"+UUID.randomUUID().toString()+"', '"+countryName+"', '"+mcc+"', '"+continent+"', '0', '0', '0.00', 'static/images/wifi/default.png', '', null, '0-2000|50-150|150-24|200-16|250-8', '0', '批量导入', '"+adminUserInfo.getUserID()+"', now(), null, null, '1', '', '', '', '', '', '', '"+countryNameEnShort+"', 'country_name_"+countryNameEnShort+"', '');");
				}
				
				object.put(mcc,timeDifference);
				
				/*CountryInfo c=new CountryInfo();
				c.setCountryID(UUID.randomUUID().toString());
				c.setCountryName(countryName);
				c.setContinent(continent);
				c.setTimeDifference(timeDifference);
				c.setCountryNameEnShort(countryNameEnShort);
				c.setCountryCode(Integer.parseInt(mcc));
				infos.add(c);*/
			}
			System.out.println("现有的国家有："+headCount+";分别是："+headStr);
			System.out.println("即将添加的国家有："+newCount+";分别是："+newStr);
			System.out.println(object.toString());
			/*countryInfoSer.insertInfoTemp(insertStr);*/
		}
		catch (Exception e)
		{
		    	e.printStackTrace();
			pm.rollback(ts);
			req.getSession().setAttribute("excelmessage", "操作数据库出错！请重试或联系管理员");
			LOGGER.info(e.getMessage());
			return "redirect:index";
		}
		// 遍历map中的键 拿出导入失败的SN;
		String message = "需要导入" + execlrow + "条， 　导入成功" + insertOKCount + "条，　导入失败" + snMap.size() + "条,　　以下为导入失败的<br/>";
		String mess = "";
		LOGGER.info(message);
		for (String key : snMap.keySet())
		{
			mess = mess + key + ":　" + snMap.get(key) + "<br/>";
		}
		message = message + mess;
		req.getSession().setAttribute("excelmessage", message);
		return "redirect:index";
	}
}
