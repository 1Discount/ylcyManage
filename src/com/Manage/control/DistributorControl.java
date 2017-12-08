package com.Manage.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.Manage.common.constants.Constants;
import com.Manage.common.util.DES;
import com.Manage.common.util.ExcelUtils;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.Dictionary;
import com.Manage.entity.Distributor;
import com.Manage.entity.RoleInfo;
import com.Manage.entity.SIMInfo;
import com.Manage.entity.common.SearchDTO;
import com.alibaba.druid.stat.TableStat.Mode;

@Controller
@RequestMapping("/customer/distributor")
public class DistributorControl extends BaseController
{
	private Logger logger = LogUtil.getInstance(DistributorControl.class);



	/**
	 * 分页查询渠道商列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletResponse response, Model model)
	{
		response.setContentType("text/html;charset=utf-8");
		List<Dictionary> distributorType = dictionarySer.getAllList(Constants.DICT_DISTRIBUTOR_TYPE);
		List<Distributor> distributors = distributorSer.getAll("");

		model.addAttribute("DistributorTypeDict", distributorType);
		model.addAttribute("IsIndexView", true);
		model.addAttribute("status", "status");
		model.addAttribute("distributors", distributors);
		return "WEB-INF/views/customerinfo/distributor_index";

	}



	/**
	 * 分页查询
	 * 
	 * 
	 * @param searchDTO
	 * @param info
	 * @param response
	 */
	@RequestMapping("/datapage")
	public void datapage(SearchDTO searchDTO, Distributor info, HttpServletResponse response)
	{
		response.setContentType("application/json;charset=UTF-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), info);
		String jsonString = distributorSer.getPageString(seDto);
		try
		{
			response.getWriter().println(jsonString);
		}
		catch (IOException e)
		{
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
	public String trash(HttpServletResponse response, Model model)
	{
		response.setContentType("text/html;charset=utf-8");
		List<Dictionary> distributorType = dictionarySer.getAllList(Constants.DICT_DISTRIBUTOR_TYPE);
		model.addAttribute("DistributorTypeDict", distributorType);
		model.addAttribute("IsTrashView", true);
		return "WEB-INF/views/customerinfo/distributor_index";

	}



	/**
	 * 分页查询 已删除
	 * 
	 * @param searchDTO
	 * @param info
	 * @param response
	 */
	@RequestMapping("/trashdatapage")
	public void dataPageDeleted(SearchDTO searchDTO, Distributor info, HttpServletResponse response)
	{
		response.setContentType("application/json;charset=UTF-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), info);
		String jsonString = distributorSer.getPageDeletedString(seDto);
		try
		{
			response.getWriter().println(jsonString);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	/**
	 * 渠道商详情 by ID
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/view/{id}")
	public String view(@PathVariable String id, Model model)
	{
		Distributor info = distributorSer.getById(id);
		if (info != null && info.getDistributorID() != null)
		{

			model.addAttribute("Model", info);
			try
			{
				model.addAttribute("Model_company_urlencoded", URLEncoder.encode(info.getCompany(), "utf-8"));
			}
			catch (UnsupportedEncodingException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addAttribute("Model_company_urlencoded", info.getCompany());
			}
		}
		else
		{
			model.addAttribute("info", "此渠道商不存在或已无效!");
		}
		return "WEB-INF/views/customerinfo/distributor_view";
	}



	/**
	 * 更新入口
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable String id, Model model)
	{
		Distributor info = distributorSer.getById(id);
		if (info != null && info.getDistributorID() != null)
		{
			List<Dictionary> distributorType = dictionarySer.getAllList(Constants.DICT_DISTRIBUTOR_TYPE);
			model.addAttribute("DistributorTypeDict", distributorType);
			List<Dictionary> distributorPaymentMethod = dictionarySer.getAllList(Constants.DICT_DISTRIBUTOR_PAYMENT_METHOD);
			model.addAttribute("PaymentMethodDict", distributorPaymentMethod);
			model.addAttribute("Model", info);
		}
		else
		{
			model.addAttribute("info", "此渠道商不存在或已无效!");
		}
		
//		SIMInfo sinfo = simInfoSer.getById(id);
//		if (StringUtils.isNotBlank(sinfo.getSlotNumber()))
//		{
//			String[] strings = sinfo.getSlotNumber().split("-");
//			sinfo.setSlotNumber(strings[0] + "-" + (Integer.parseInt(strings[1]) + 1));
//		}
		List<CountryInfo> countries = countryInfoSer.getAll("");
		// 标记选中 selected 的项
//		String countryString = sinfo.getCountryList();
//		if (!StringUtils.isBlank(countryString))
//		{
//			for (CountryInfo country : countries)
//			{
//				// MCC 都系3位, 所以不需要前后补',' --> 因为带有价格数字, 所以必须前后补','来判断
//				// --> 因为 SIMInfo 的值形如 455|456 , 所以不必要去前后补','
//				// String matchString = "," +
//				// String.valueOf(country.getCountryCode()) + ",";
//				String matchString = String.valueOf(country.getCountryCode());
//				if (StringUtils.contains(countryString, matchString))
//				{
//					country.setSelected(true);
//				}
//			}
//		}
		model.addAttribute("Countries", countries);
		
		
		
		
		
		return "WEB-INF/views/customerinfo/distributor_edit";
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
	// public String editTrash(@PathVariable String id, Model model) {
	// Distributor info = distributorSer.getById(id);
	// if (info != null && info.getDistributorID() != null) {
	// List<Dictionary> distributorType =
	// dictionarySer.getAllList(Constants.DICT_DISTRIBUTOR_TYPE);
	// model.addAttribute("DistributorTypeDict", distributorType);
	// List<Dictionary> distributorPaymentMethod =
	// dictionarySer.getAllList(Constants.DICT_DISTRIBUTOR_PAYMENT_METHOD);
	// model.addAttribute("PaymentMethodDict", distributorPaymentMethod);
	// model.addAttribute("Model", info);
	// model.addAttribute("IsTrashView", true);
	// } else {
	// model.addAttribute("info","此记录不存在或已无效!");
	// }
	// return "WEB-INF/views/customerinfo/distributor_edit";
	// }

	/**
	 * 新增记录入口
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/new")
	public String newEntity(HttpServletResponse response, Model model)
	{
		response.setContentType("text/html;charset=utf-8");
		List<Dictionary> distributorType = dictionarySer.getAllList(Constants.DICT_DISTRIBUTOR_TYPE);
		model.addAttribute("DistributorTypeDict", distributorType);
		List<Dictionary> distributorPaymentMethod = dictionarySer.getAllList(Constants.DICT_DISTRIBUTOR_PAYMENT_METHOD);
		model.addAttribute("PaymentMethodDict", distributorPaymentMethod);
		return "WEB-INF/views/customerinfo/distributor_new";
	}



	/**
	 * 保存记录 新增new或编辑edit提交时统一使用此接口 通过 boolean isInsert 来相应处理
	 * 
	 * @param info
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void saveAction(Distributor info, HttpServletRequest request, HttpServletResponse response, Model model)
	{
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

		info.setCompany(info.getCompany().trim());

		boolean isInsert = false;
		if (StringUtils.isBlank(info.getDistributorID()))
		{
			isInsert = true;
		}

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");

		if (adminUserInfo != null)
		{
			if (isInsert)
			{
				info.setCreatorUserID(adminUserInfo.getUserID());
				info.setCreatorUserName(adminUserInfo.getUserName());
			}
			else
			{
				info.setModifyUserID(adminUserInfo.getUserID());
			}
		}
		else
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
		if (isInsert)
		{
			info.setSysStatus(1);
			info.setGrade("1");
			info.setParentsDisID("000");
			info.setParentsDisName("ylcy");
			result = distributorSer.insertInfo(info);
		}
		else
		{
			result = distributorSer.updateInfo(info);
		}

		if (result)
		{
			logger.debug("渠道商信息保存成功");
			try
			{
				jsonResult.put("code", 0);
				jsonResult.put("msg", "成功保存渠道商信息!");
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
				admin.setOperateID(UUID.randomUUID().toString());
				admin.setCreatorUserID(adminUserInfo.getUserID());
				admin.setCreatorUserName(adminUserInfo.getUserName());

				if (isInsert)
				{
					admin.setOperateContent("添加了渠道商, 记录ID为: " + info.getDistributorID() + " 名称: " + info.getCompany());
					admin.setOperateMenu("渠道商管理>添加渠道商");
					admin.setOperateType("添加");
				}
				else
				{
					admin.setOperateContent("修改了渠道商, 记录ID为: " + info.getDistributorID() + " 名称: " + info.getCompany());
					admin.setOperateMenu("渠道商管理>修改渠道商");
					admin.setOperateType("修改");
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
			logger.debug("渠道商信息保存失败");
			try
			{
				jsonResult.put("code", 1);
				jsonResult.put("msg", "保存渠道商信息出错, 请重试!");
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
	 * 保存记录 新增new或编辑edit提交时统一使用此接口 通过 boolean isInsert 来相应处理
	 * 
	 * @param info
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/saveChild", method = RequestMethod.POST)
	public void saveChild(Distributor info, String preCompany, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException
	{

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");

		info.setCompany(info.getCompany().trim());

		JSONObject jsonResult = new JSONObject();

		info.setCreatorUserID(adminUserInfo.getUserID());
		info.setCreatorUserName(adminUserInfo.getUserName());
		info.setSysStatus(1);
		info.setGrade((Integer.parseInt(info.getGrade()) + 1) + "");
		info.setParentsDisID(info.getDistributorID());
		info.setParentsDisName(preCompany);

		boolean result = distributorSer.insertInfo(info);

		if (result)
		{
			logger.debug("渠道商信息保存成功");
			try
			{
				jsonResult.put("code", 0);
				jsonResult.put("msg", "成功保存渠道商信息!");
				response.getWriter().println(jsonResult.toString());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			try
			{
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());
				admin.setCreatorUserID(adminUserInfo.getUserID());
				admin.setCreatorUserName(adminUserInfo.getUserName());
				admin.setOperateContent("添加了渠道商, 记录ID为: " + info.getDistributorID() + " 名称: " + info.getCompany());
				admin.setOperateMenu("渠道商管理>添加渠道商");
				admin.setOperateType("添加");

				adminOperateSer.insertdata(admin);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			logger.debug("渠道商信息保存失败");
			try
			{
				jsonResult.put("code", 1);
				jsonResult.put("msg", "保存渠道商信息出错, 请重试!");
				response.getWriter().println(jsonResult.toString());
			}
			catch (IOException e)
			{
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
	public void delete(@PathVariable String id, HttpServletResponse response, Model model)
	{
		response.setContentType("text/html;charset=utf-8");

		if (StringUtils.isBlank(id))
		{
			try
			{
				response.getWriter().println("请求参数无效!");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			return;
		}

		Distributor info = new Distributor();
		info.setDistributorID(id);
		info.setSysStatus(0);

		if (distributorSer.updateInfoSysStatus(info))
		{
			try
			{
				response.getWriter().println("渠道商删除成功!");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			try
			{
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());// id
				// admin.setCreatorDate(date);//创建时间
				admin.setCreatorUserID(adminUserInfo.getUserID());// 创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());// 创建人姓名
				// admin.setOperateDate(date);//操作时间
				// admin.setSysStatus(1);

				admin.setOperateContent("删除了渠道商, 记录ID为: " + info.getDistributorID() + " 名称: " + info.getCompany());
				admin.setOperateMenu("渠道商管理>删除渠道商");
				admin.setOperateType("删除");

				adminOperateSer.insertdata(admin);
			}
			catch (Exception e)
			{
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
				response.getWriter().println("渠道商删除出错!");
			}
			catch (IOException e)
			{
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
	public void restorePlan(@PathVariable String id, HttpServletResponse response, Model model)
	{
		response.setContentType("text/html;charset=utf-8");

		if (StringUtils.isBlank(id))
		{
			try
			{
				response.getWriter().println("请求参数无效!");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			return;
		}

		Distributor info = new Distributor();
		info.setDistributorID(id);
		info.setSysStatus(1);

		if (distributorSer.updateInfoSysStatus(info))
		{
			try
			{
				response.getWriter().println("渠道商恢复成功!");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			try
			{
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());// id
				// admin.setCreatorDate(date);//创建时间
				admin.setCreatorUserID(adminUserInfo.getUserID());// 创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());// 创建人姓名
				// admin.setOperateDate(date);//操作时间
				// admin.setSysStatus(1);

				admin.setOperateContent("恢复了渠道商, 记录ID为: " + info.getDistributorID() + " 名称: " + info.getCompany());
				admin.setOperateMenu("渠道商管理>恢复渠道商");
				admin.setOperateType("恢复");

				adminOperateSer.insertdata(admin);
			}
			catch (Exception e)
			{
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
				response.getWriter().println("渠道商恢复出错!");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}

	}



	/***
	 * 
	 * @给渠道商分发设备 批量分发
	 * @author jiangxuecheng
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @date 2015-12-01
	 */
	@RequestMapping(value = "/deviceDistribute", method = RequestMethod.POST)
	public String deviceDistribute(@RequestParam("file") MultipartFile file, DeviceInfo deviceInfo, HttpServletRequest req, HttpServletResponse resp, HttpSession session, Model model) throws FileNotFoundException, IOException
	{
		IdentityHashMap<String, String> snMap = new IdentityHashMap<String, String>();
		try
		{
			CommonsMultipartFile cf = (CommonsMultipartFile) file;
			DiskFileItem fi = (DiskFileItem) cf.getFileItem();
			File files = fi.getStoreLocation();
			String[][] result = ExcelUtils.getData(files, 1); // 此模板开始一行为标题
			logger.info("成功获取到execl");
			int allColomn = result[0].length;
			int allRow = result.length;
			logger.info("成功获取到execl，行：" + allRow + "列：" + allColomn + "");

			for (int i = 0; i < result.length; i++)
			{
				for (int j = 0; j < result[i].length - 1; j++)
				{
					if (result[i][0].equals(""))
					{
						allRow = i;
						i = result.length;
						break;
					}
				}
			}
			int success = 0;
			for (int i = 0; i < allRow; i++)
			{
				String SN = result[i][0].trim();
				deviceInfo.setSN(Constants.SNformat(SN));
				DeviceInfo deviceInfo2 = deviceInfoSer.selectsnDistributor(deviceInfo);
				if (deviceInfo2 == null)
				{
					snMap.put(new String(SN), "此设备不存在");
					continue;
				}
				else if (deviceInfo2.getDistributorID() != null && !"".equals(deviceInfo2.getDistributorID()))
				{
					snMap.put(new String(SN), "此设备忆有渠道商，渠道商名称为：" + deviceInfo2.getDistributorName());
					continue;
				}
				else if (!"可使用".equals(deviceInfo2.getDeviceStatus()))
				{
					snMap.put(new String(SN), "此设备状态为使用中");
					continue;
				}

				int count = deviceInfoSer.deviceDistribute(deviceInfo);

				if (count > 0)
				{
					success++;
				}
			}
			String mess = "<p>需要导入" + allRow + "条，导入成功" + success + "条，导入失败" + snMap.size() + "条，以下为导入失败原因</p>";
			for (String key : snMap.keySet())
			{

				mess = mess + "<p>" + key + ":　" + snMap.get(key) + "</p>";
			}
			req.getSession().setAttribute("msg", mess);
			return "redirect:dis";
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		req.getSession().setAttribute("msg", "未知错误");
		return "redirect:dis";
	}



	/***
	 * 
	 * @给渠道商分发设备 输入SN分发
	 * @author jiangxuecheng
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @date 2015-12-01
	 */
	@RequestMapping("/deviceDistributeBySN")
	public void deviceDistributeBySN(String SN, HttpServletRequest req, HttpServletResponse resp, DeviceInfo deviceInfo, HttpSession session, Model model) throws IOException
	{
		if (StringUtils.isNotBlank(deviceInfo.getDistributorName()))
		{
			deviceInfo.setDistributorName(new String(deviceInfo.getDistributorName().getBytes("ISO-8859-1"), "UTF-8"));
		}
		Map<String, String> snMap = new HashMap<String, String>();
		JSONObject object = new JSONObject();
		String[] arraySN = SN.split("/");
		int success = 0;
		try
		{
			for (int i = 0; i < arraySN.length; i++)
			{
				SN = arraySN[i].trim();
				deviceInfo.setSN(Constants.SNformat(SN));
				DeviceInfo deviceInfo2 = deviceInfoSer.selectsnDistributor(deviceInfo);
				if (deviceInfo2 == null)
				{
					snMap.put(arraySN[i], "此设备不存在");
					continue;
				}
				else if (deviceInfo2.getDistributorID() != null && !"".equals(deviceInfo2.getDistributorID()))
				{
					snMap.put(arraySN[i], "此设备忆有渠道商，渠道商ID为：" + deviceInfo2.getDistributorID());
					continue;
				}
				else if (!"可使用".equals(deviceInfo2.getDeviceStatus()))
				{
					snMap.put(arraySN[i], "此设备状态使用中");
					continue;
				}

				int successCount = deviceInfoSer.deviceDistribute(deviceInfo);
				if (successCount > 0)
				{
					success++;
				}
			}
			String mess = "<p>需要导入" + arraySN.length + "条，导入成功" + success + "条，导入失败" + snMap.size() + "条，以下为导入失败原因</p>";
			for (String key : snMap.keySet())
			{
				mess = mess + "<p>" + key + ":　" + snMap.get(key) + "</p>";
			}
			if (arraySN.length == success)
			{
				object.put("Code", "00");
			}
			else
			{
				object.put("Code", "01");
			}
			object.put("msg", mess);
			resp.getWriter().print(object.toString());
			return;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		object.put("msg", "未知错误");
		resp.getWriter().print(object.toString());
	}



	@RequestMapping("/dis")
	public String distributor(HttpServletRequest request, Model model)
	{
		String msg = (String) request.getSession().getAttribute("msg");
		request.getSession().setAttribute("msg", "");
		List<Dictionary> distributorType = dictionarySer.getAllList(Constants.DICT_DISTRIBUTOR_TYPE);
		model.addAttribute("DistributorTypeDict", distributorType);
		model.addAttribute("IsIndexView", true);
		model.addAttribute("msg", msg);
		return "WEB-INF/views/customerinfo/distributor_index";
	}



	/**
	 * 
	 * @deprecated 点击创建帐户将密码888888加密码，并增加用户名
	 * @author jiangxuecheng
	 * @date 2015-12-01
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/createaccount")
	public void createaccount(Distributor distributor, HttpServletResponse response) throws Exception
	{
		// customerInfo.setPassword(DES.toHexString(DES.encrypt("88888888",
		// Constants.DES_KEYWEB)));
		distributor.setPassword(DES.toHexString(DES.encrypt("88888888", Constants.DES_KEY)));
		int hang = distributorSer.updateloginInfo(distributor);
		JSONObject jsonResult = new JSONObject();
		if (hang > 0)
		{
			jsonResult.put("code", 1);
			jsonResult.put("msg", "创建成功");
			response.getWriter().println(jsonResult.toString());
		}
		else
		{
			jsonResult.put("code", -1);
			jsonResult.put("msg", "创建失败");
			response.getWriter().println(jsonResult.toString());
		}
	}



	@RequestMapping("/checkUser")
	public void checkUser(Distributor distributor, HttpServletResponse response) throws IOException
	{
		if (distributorSer.checkUser(distributor) == null)
		{
			response.getWriter().print("true");
		}
		else
		{
			response.getWriter().print("false");
		}
	}



	@RequestMapping("/getdevicebydistributorID")
	public void getdevicebydistributorID(Distributor distributor, HttpServletResponse response) throws IOException
	{
		Distributor info = distributorSer.getDistributorbydistributorID(distributor);
		if (info == null)
		{
			response.getWriter().print("0");
		}
		else
		{
			if (info.getUserName() == null || "".equals(info.getUserName()) || "".equals(info.getPassword()))
			{
				response.getWriter().print("0");
			}
			else
			{
				response.getWriter().print("1");
			}
		}
	}



	@RequestMapping("/resetpassword")
	public void resetpassword(Distributor distributor, HttpServletResponse response) throws Exception
	{
		distributor.setPassword(DES.toHexString(DES.encrypt("88888888", Constants.DES_KEY)));
		int hang = distributorSer.resetpassword(distributor);
		if (hang > 0)
		{
			response.getWriter().println("1");
		}
		else
		{
			response.getWriter().println("0");
		}
	}



	@RequestMapping("checkEmail")
	public void checkEmail(Distributor distributor, HttpServletResponse response) throws IOException
	{
		if (distributorSer.checkEmail(distributor) == null) response.getWriter().print("true");
		else response.getWriter().print("false");
	}



	@RequestMapping("checkPhone")
	public void checkPhone(Distributor distributor, HttpServletResponse response) throws IOException
	{
		if (distributorSer.checkPhone(distributor) == null) response.getWriter().print("true");
		else response.getWriter().print("false");
	}



	@RequestMapping("checkCompany")
	public void checkCompany(Distributor distributor, HttpServletResponse response) throws IOException
	{
		if (distributorSer.checkCompany(distributor) == null) response.getWriter().print("true");
		else response.getWriter().print("false");
	}



	@RequestMapping("/selectpassword")
	public void selectpassword(Distributor distributor, HttpServletResponse response) throws Exception
	{
		Distributor dis = distributorSer.getDistributorbydistributorID(distributor);
		String password = dis.getPassword();
		password = DES.decrypt(password, Constants.DES_KEY);
		response.getWriter().print(password);
	}



	@RequestMapping("/skipAnnouncement")
	public String skipAnnouncement(Model model)
	{
		return "WEB-INF/views/customerinfo/Announcement";
	}



	@RequestMapping("/updateAnnouncement")
	public void updateAnnouncement(Distributor distributor, HttpServletResponse response) throws IOException
	{
		distributor.setAnnouncement(new String(distributor.getAnnouncement().getBytes("ISO-8859-1"), "utf-8"));
		int hang = distributorSer.updateAnnouncement(distributor);
		if (hang > 0)
		{
			response.getWriter().print("1");
		}
		else
		{
			response.getWriter().print("0");
		}
	}



	@RequestMapping("/writepriceConfig")
	public void writepriceConfig(Distributor distributor, HttpServletResponse response) throws IOException
	{
		distributor.setPriceConfiguration(new String(distributor.getPriceConfiguration().trim().getBytes("iso-8859-1"),"utf-8"));
		int hang = distributorSer.updatepriceconfig(distributor);

		if (hang > 0)
		{
			response.getWriter().print("0");
		}
		else
		{
			response.getWriter().print("-1");
		}
	}
	
	@RequestMapping("/importpriceConfig")
	public String importpriceConfig(@RequestParam("file") MultipartFile file,Distributor distributor,HttpServletRequest req, HttpServletResponse resp) throws FileNotFoundException, IOException{
		logger.info("..导入订单得到请求..");
		CommonsMultipartFile cf = (CommonsMultipartFile) file;
		DiskFileItem fi = (DiskFileItem) cf.getFileItem();
		File files = fi.getStoreLocation();
		String[][] result = ExcelUtils.getData(files, 1); // 此模板开始一行为标题
		logger.info("成功获取到execl");
		String pirceConfig = result[0][0];
		
		distributor.setPriceConfiguration(new String(pirceConfig.trim().getBytes("iso-8859-1"),"utf-8"));
		int hang = distributorSer.updatepriceconfig(distributor);
		String message = "";
		if (hang > 0)
		{
			message="0";
		}
		else
		{
			message="-1";
		}
		
		req.getSession().setAttribute("excelmessage", message);
		return "redirect:returnbatchorder";
	}
	
	@RequestMapping("/returnbatchorder")
	public String returnbatchorder(HttpServletRequest req, HttpSession session, Model model)
	{
		System.out.println("1111111111111111111========");
		model.addAttribute("LogMessage", req.getSession().getAttribute("excelmessage"));
		req.getSession().setAttribute("excelmessage", ""); // 及时清空, 避免污染其他页面
		
		List<Dictionary> distributorType = dictionarySer.getAllList(Constants.DICT_DISTRIBUTOR_TYPE);
		List<Distributor> distributors = distributorSer.getAll("");

		model.addAttribute("DistributorTypeDict", distributorType);
		model.addAttribute("IsIndexView", true);
		model.addAttribute("status", "status");
		model.addAttribute("distributors", distributors);
		
		return "WEB-INF/views/customerinfo/distributor_index";
	}

	/**
	 * 跳转到分配角色界面
	 * @param adminUserInfo
	 * @param model
	 * @return
	 */
	@RequestMapping("/usertorole")
	public String usertorole(Distributor adminUserInfo, Model model) {
		logger.debug("分配角色校验参数!");
		if (adminUserInfo.getDistributorID() == null
				|| adminUserInfo.getUserName() == null
				|| adminUserInfo.getOperatorEmail() == null) {
			logger.debug("分配角色校验参数未通过!");
			return "WEB-INF/views/customerinfo/distributor_index";
		}

		String uname = adminUserInfo.getUserName();
		try {
			uname = new String(uname.getBytes("iso-8859-1"), "utf-8");
			adminUserInfo.setUserName(uname);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 查询所有角色.
		List<RoleInfo> list = distributorRoleInfoSer.getalList();

		if (list != null) {
			logger.debug("角色列表查询成功!");
			model.addAttribute("rlist", list);
		} else {
			logger.debug("角色列表查询失败!");
			// 避免前端直接报错
			model.addAttribute("rlist", "");
		}
		model.addAttribute("user", adminUserInfo);
		return "WEB-INF/views/distributor/user_role";
	}
	/**
	 * 分配用户角色保存
	 */
	@RequestMapping("/adduserrole")
	public void adduserrole(Distributor adminUserInfo,
			HttpServletResponse response) {
		logger.debug("参数校验!");

		AdminUserInfo loginAdminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");
		if (loginAdminUserInfo == null) {
			try {
				response.getWriter().println("请重新登录!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
			return;
		}

		try {
			if (adminUserInfo.getDistributorID() != null
					&& adminUserInfo.getRoleID() != null
					&& adminUserInfo.getRoleName() != null) {
				if (distributorSer.usertorole(adminUserInfo)) {
					logger.debug("角色分配成功");

					try {
						AdminOperate admin = new AdminOperate();
						admin.setOperateID(UUID.randomUUID().toString());//id
						// admin.setCreatorDate(date);//创建时间
						admin.setCreatorUserID(loginAdminUserInfo.getUserID());//创建人ID
						admin.setCreatorUserName(loginAdminUserInfo.getUserName());//创建人姓名
						//admin.setOperateDate(date);//操作时间
						//admin.setSysStatus(1);

						admin.setOperateContent("角色分配成功, 记录ID为: " + adminUserInfo.getDistributorID() + " 名称: " + adminUserInfo.getUserName());
						admin.setOperateMenu("用户管理>分配角色");
						admin.setOperateType("修改");

						adminOperateSer.insertdata(admin);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}

					response.getWriter().print("1");
				} else {
					logger.debug("角色分配失败");
					response.getWriter().print("0");
				}
			} else {
				logger.debug("参数校验失败" + adminUserInfo.getDistributorID() + "-"
						+ adminUserInfo.getRoleID() + "-"
						+ adminUserInfo.getRoleName());
				response.getWriter().print("-1");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}
}
