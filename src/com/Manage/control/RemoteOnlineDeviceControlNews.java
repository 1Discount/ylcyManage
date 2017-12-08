package com.Manage.control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.ibatis.annotations.Case;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.Manage.common.cache.CacheUtils;
import com.Manage.common.constants.Constants;
import com.Manage.common.exception.BmException;
import com.Manage.common.latlng.LocTest;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.PropertiesLoader;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.DeviceLogs;
import com.Manage.entity.EquipLogs;
import com.Manage.entity.GStrenthData;
import com.Manage.entity.LocTemp;
import com.Manage.entity.OrdersInfo;
import com.Manage.entity.common.Page;
import com.kdt.api.YouzanConfig;
import com.sun.corba.se.spi.orb.StringPair;

@Controller
@RequestMapping("/remote2")
public class RemoteOnlineDeviceControlNews extends BaseController
{
	private Logger logger = LogUtil.getInstance(RemoteOnlineDeviceControlNews.class);



	@RequestMapping("/index")
	public String remoteindex(Model model)
	{
		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (adminUserInfo == null)
		{

			return "login";
		}
		List<CountryInfo> countries = countryInfoSer.getAll("");
		model.addAttribute("Countries", countries);
		return "WEB-INF/views/service/remote_OnlineDevice";
	}



	@RequestMapping("/Serverindex")
	public String Serverindex()
	{

		return "WEB-INF/views/service/remote_Server";
	}



	/**
	 * 跳转到设备日志历史记录
	 * 
	 * @return
	 */
	@RequestMapping("/toSNLogs")
	public String toSNLogs()
	{
		return "WEB-INF/views/service/equip_sn_logs";
	}



	/**
	 * 限速
	 * 
	 * @param limitValve
	 * @param limitSpeed
	 * @param sn
	 * @param response
	 */
	@RequestMapping("/limitspeed")
	public void limitspeed(String limitValve, String limitSpeed, String sn, String ps, Model model, HttpServletResponse response)
	{
		try
		{
			if (StringUtils.isBlank(limitValve) || StringUtils.isBlank(limitSpeed))
			{
				response.getWriter().print("-1");
			}
			else
			{
				int temp = commandSer2.limitspeed(limitValve, limitSpeed, sn);
				if (temp == 1)
				{
					response.getWriter().print("1");
				}
				else if (temp == 0)
				{
					response.getWriter().print("0");
				}
				else if (temp == -1)
				{
					response.getWriter().print("-2");
				}
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.info(e.getMessage());
		}
		if (StringUtils.isNotBlank(ps))
		{
			// model.addAttribute("sn",sn);
		}
		// return "WEB-INF/views/service/remote_OnlineDevice";
	}



	@RequestMapping("/remoteQuery")
	public void remoteQuery(String mes, String sn, String ps, Model model, HttpServletResponse response)
	{
		try
		{
			if (StringUtils.isBlank(mes))
			{
				response.getWriter().print("-1");
			}
			else
			{
				int temp = commandSer2.remoteQuery(mes, sn);
				if (temp == 1)
				{
					response.getWriter().print("1");
				}
				else if (temp == 0)
				{
					response.getWriter().print("0");
				}
				else if (temp == -1)
				{
					response.getWriter().print("-2");
				}
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.info(e.getMessage());
		}
		if (StringUtils.isNotBlank(ps))
		{
			// model.addAttribute("sn",sn);
		}
		// return "WEB-INF/views/service/remote_OnlineDevice";
	}



	@RequestMapping("/modificationAPN")
	public void modificationAPN(String apn, String sn, String ps, Model model, HttpServletResponse response)
	{
		try
		{
			if (StringUtils.isBlank(apn))
			{
				response.getWriter().print("-1");
			}
			else
			{
				int temp = commandSer2.modificationAPN(apn, sn);
				if (temp == 1)
				{
					response.getWriter().print("1");
				}
				else if (temp == 0)
				{
					response.getWriter().print("0");
				}
				else if (temp == -1)
				{
					response.getWriter().print("-2");
				}
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.info(e.getMessage());
		}
		if (StringUtils.isNotBlank(ps))
		{
			// model.addAttribute("sn",sn);
		}
		// return "WEB-INF/views/service/remote_OnlineDevice";

	}



	/**
	 * 提取日志
	 * 
	 * @param bm
	 * @param num
	 * @param sn
	 * @param ps
	 * @param model
	 * @param response
	 */
	@RequestMapping("/getDevLogs")
	public void getDevLogs(String bm, String num, String sn, String ps, Model model, HttpServletResponse response)
	{
		try
		{
			if (StringUtils.isBlank(num))
			{
				num = "0";
			}
			int temp = commandSer2.getDevLogs(Integer.parseInt(bm), num, sn);
			if (temp == 1)
			{
				response.getWriter().print("1");
			}
			else if (temp == 0)
			{
				response.getWriter().print("0");
			}
			else if (temp == -1)
			{
				response.getWriter().print("-2");
			}
			else if (temp == -2)
			{
				response.getWriter().print("-3");
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.info(e.getMessage());
		}
		if (StringUtils.isNotBlank(ps))
		{

		}
		// return "WEB-INF/views/service/remote_OnlineDevice";
	}



	/**
	 * 远程升级
	 * 
	 * @param type
	 * @param sn
	 * @param ps
	 * @param newVersion
	 * @param fileTypeString
	 * @param model
	 * @param response
	 */
	@RequestMapping("/remoteUpgrade")
	public void remoteUpgrade(String type, String sn, String ps, String newVersion, String fileTypeString, Model model, HttpServletResponse response)
	{
		AdminUserInfo user = (AdminUserInfo) getSession().getAttribute("User");
		try
		{
			int temp = commandSer2.remoteUpgrade(type, sn);
			if (temp == 1)
			{
				// 在这里插入用户操作日志
				// 1.获取到文件类型 版本号、操作类型、
				String versionNO = newVersion;
				String fileType = fileTypeString;
				try
				{
					AdminOperate admin = new AdminOperate();
					admin.setOperateID(UUID.randomUUID().toString());// id
					admin.setCreatorDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));// 创建时间
					admin.setCreatorUserID(user.getUserID());// 创建人ID
					admin.setCreatorUserName(user.getUserName());// 创建人姓名
					admin.setOperateDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));// 操作时间
					admin.setSysStatus(1);
					admin.setOperateContent("远程升级, 用户ID为: " + user.getUserID() + " 名称: " + user.getUserName() + "版本号：" + versionNO + "文件类型：" + fileType + "设备序列号：" + sn);
					admin.setOperateMenu("远程服务>远程升级");
					admin.setOperateType("升级");
					adminOperateSer.insertdata(admin);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				response.getWriter().print("1");
			}
			else if (temp == 0)
			{
				response.getWriter().print("0");
			}
			else if (temp == -1)
			{
				response.getWriter().print("-2");
			}
			else if (temp == -2)
			{
				response.getWriter().print("-3");
			}
		}
		catch (Exception e)
		{
			logger.info(e.getMessage());
		}
	}



	/**
	 * 查看日志入口
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/tologs")
	public String tologs(String SN, Model model)
	{
		model.addAttribute("SN", SN);
		return "WEB-INF/views/service/equip_logs_list";
	}



	/**
	 * 查看日志列表
	 * 
	 * @param sn
	 * @param type
	 * @param response
	 */
	@RequestMapping("/getlogs")
	public void getlogs(String sn, String type, HttpServletResponse response)
	{
		try
		{

			EquipLogs equipLogs = new EquipLogs();
			if (StringUtils.isBlank(sn))
			{
				equipLogs.setSN("000");
			}
			else
			{
				equipLogs.setSN(sn);
			}

			equipLogs.setType(Integer.parseInt(type));
			List<EquipLogs> ls = equipLogsSer.getbysn(equipLogs);
			JSONObject object = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			if (ls != null && ls.size() > 0)
			{
				for (EquipLogs equipLogs2 : ls)
				{
					JSONObject object2 = JSONObject.fromObject(equipLogs2);
					jsonArray.add(object2);
				}
				object.put("data", jsonArray);
			}
			else
			{
				object.put("data", "[]");
			}

			object.put("success", true);
			object.put("totalRows", ls.size());
			response.getWriter().print(object.toString());
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.info(e.getMessage());
		}
	}



	/**
	 * 删除日志
	 * 
	 * @param id
	 * @param response
	 */
	@RequestMapping("/delinfo")
	public void delinfo(String id, HttpServletResponse response)
	{
		try
		{
			if (id == null || "".equals(id))
			{
				response.getWriter().print("-1");
				return;
			}
			if (equipLogsSer.delinfo(id))
			{
				response.getWriter().print("1");
			}
			else
			{
				response.getWriter().print("0");
			}

		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.info(e.getMessage());
		}
	}



	/**
	 * 查看日志
	 * 
	 * @param ID
	 * @return
	 */
	@RequestMapping("/logsview")
	public String logsview(String ID, Model model)
	{
		if (!StringUtils.isNotBlank(ID))
		{
			return "WEB-INF/views/service/equip_logs_list";
		}
		try
		{
			EquipLogs equipLogs = new EquipLogs();
			equipLogs.setID(ID);
			List<EquipLogs> list = equipLogsSer.getbyid(equipLogs);
			if (list != null && list.size() == 1)
			{
				EquipLogs equipLogs2 = list.get(0);
				if (equipLogs2.getType() == 1)
				{
					model.addAttribute("title", "设备" + equipLogs2.getSN() + "的漫游日志:");
					model.addAttribute("content", equipLogs2.getContent());
				}
				else if (equipLogs2.getType() == 2)
				{
					model.addAttribute("title", "设备" + equipLogs2.getSN() + "的本地日志:");
					model.addAttribute("content", equipLogs2.getContent());
				}
				return "WEB-INF/views/service/equip_logs_content";
			}
			else
			{
				model.addAttribute("err", "日志读取失败");
				return "WEB-INF/views/service/equip_logs_list";
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.info(e.getMessage());
			model.addAttribute("err", "日志读取失败");
			return "WEB-INF/views/service/equip_logs_list";

		}

	}



	/**
	 * 消息推送 00 表示都没数据 01表示升级成功 02表示升级失败 10表示日志成 20表示日志失败
	 */
	@RequestMapping("/mesPush")
	public void mesPush(HttpServletResponse response)
	{
		try
		{
			if (StringUtils.isNotBlank(CacheUtils.get("logsuccess")))
			{
				String logsuccess = CacheUtils.get("logsuccess").toString();
				if ("1".equals(logsuccess))
				{
					response.getWriter().print("1");
				}
				else
				{
					response.getWriter().print("2");
				}
				CacheUtils.remove("logsuccess");
			}
			else
			{
				response.getWriter().print("0");
			}
			if (StringUtils.isNotBlank(CacheUtils.get("upgradesuccess")))
			{
				String logsuccess = CacheUtils.get("upgradesuccess").toString();
				if ("1".equals(logsuccess))
				{
					response.getWriter().print("1");
				}
				else
				{
					response.getWriter().print("2");
				}
				CacheUtils.remove("upgradesuccess");
			}
			else
			{
				response.getWriter().print("0");
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}



	/**
	 * 远程重插卡
	 */
	@RequestMapping("/rePlugSIM")
	public void rePlugSIM(String SN, HttpServletResponse response)
	{
		try
		{
			if (StringUtils.isNotBlank(SN))
			{
				int temp = commandSer2.rePlugSIM(SN);
				if (temp == 1)
				{
					response.getWriter().print("1");
				}
				else if (temp == 0)
				{
					response.getWriter().print("0");
				}
				else if (temp == -1)
				{
					response.getWriter().print("-2");
				}
			}
			else
			{
				response.getWriter().print("-1");
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception

		}
	}



	/**
	 * 远程换卡
	 * 
	 * @param SN
	 * @param response
	 */
	@RequestMapping("/changeCard")
	public void changeCard(String SN, HttpServletResponse response)
	{
		try
		{
			if (StringUtils.isNotBlank(SN))
			{
				int temp = commandSer2.changeCard(SN);
				if (temp == 1)
				{
					response.getWriter().print("1");
				}
				else if (temp == 0)
				{
					response.getWriter().print("0");
				}
				else if (temp == -1)
				{
					response.getWriter().print("-2");
				}
			}
			else
			{
				response.getWriter().print("-1");
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception

		}
	}



	/**
	 * 远程关机
	 * 
	 * @param SN
	 * @param response
	 */
	@RequestMapping("/remoteShutdown")
	public void remoteShutdown(String SN, HttpServletResponse response)
	{
		try
		{
			if (StringUtils.isNotBlank(SN))
			{
				int temp = commandSer2.remoteShutdown(SN);
				if (temp == 1)
				{
					response.getWriter().print("1");
				}
				else if (temp == 0)
				{
					response.getWriter().print("0");
				}
				else if (temp == -1)
				{
					response.getWriter().print("-2");
				}
			}
			else
			{
				response.getWriter().print("-1");
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception

		}
	}



	@RequestMapping("/modificationAPNServer")
	public String modificationAPNServer(String apn, String sn, Model model)
	{
		try
		{
			if (StringUtils.isBlank(apn))
			{
				return "WEB-INF/views/service/remote_Server";
			}
			int temp = commandSer2.modificationAPN(apn, sn);
			if (temp == 1)
			{
				response.getWriter().print("1");
			}
			else if (temp == 0)
			{
				response.getWriter().print("0");
			}
			else if (temp == -1)
			{
				response.getWriter().print("-2");
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.info(e.getMessage());
		}
		return "WEB-INF/views/service/remote_Server";
	}



	@RequestMapping("/restPwd")
	public void restPwd(String SN, HttpServletResponse response)
	{
		try
		{
			if (StringUtils.isNotBlank(SN))
			{
				int temp = commandSer2.restPwd(SN);
				if (temp == 1)
				{
					response.getWriter().print("1");
				}
				else if (temp == 0)
				{
					response.getWriter().print("0");
				}
				else if (temp == -1)
				{
					response.getWriter().print("-2");
				}
			}
			else
			{
				response.getWriter().print("-1");
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.info(e.getMessage());
		}
	}



	@RequestMapping("/modifyVPN")
	public void modifyVPN(String sn, String vpn, HttpServletResponse response, Model model)
	{
		try
		{
			if (StringUtils.isNotBlank(sn))
			{
				int temp = commandSer2.modifyVPN(sn, vpn);
				if (temp == 1)
				{
					response.getWriter().print("1");
				}
				else if (temp == 0)
				{
					response.getWriter().print("0");
				}
				else if (temp == -1)
				{
					response.getWriter().print("-2");
				}
			}
			else
			{
				response.getWriter().print("-1");
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.info(e.getMessage());
		}
		// return "WEB-INF/views/service/remote_Server";
	}



	@RequestMapping("/upload")
	public String upload(HttpServletRequest req, HttpSession session, Model model)
	{

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (adminUserInfo == null)
		{

			return "login";
		}
		List<CountryInfo> countries = countryInfoSer.getAll("");
		model.addAttribute("Countries", countries);
		model.addAttribute("temp", req.getSession().getAttribute("temp"));
		req.getSession().setAttribute("temp", ""); // 及时清空, 避免污染其他页面
		return "WEB-INF/views/service/remote_upload";
	}



	/**
	 * 升级文件上传
	 * 
	 * @param file
	 * @param localhost
	 * @param newVersion
	 * @param req
	 * @param resp
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/uploadding")
	public String uploadding(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse resp, HttpSession session, Model model)
	{
		logger.info("开始上传");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
		String logoPathDir = "/upload/";
		// 得到保存目录的真实路径
		String temp = request.getSession().getServletContext().getRealPath(logoPathDir);
		// 创建文件保存路径文件夹
		File tempFile = new File(temp);
		if (!tempFile.exists())
		{
			tempFile.mkdirs();
		}
		// 获取到文件
		MultipartFile multipartFile = multipartRequest.getFile("file");
		logger.info("成功获取文件");

		String fName = multipartFile.getOriginalFilename();
		String logExcelName = ""; // 原始升级文件名
		String newVersion = ""; // 版本号
		String fileTypeString = ""; // 文件类型
		if (fName.contains("local_client"))
		{
			fileTypeString = "本地";
			logExcelName = "local_client";
			if (fName.indexOf("-") > 0)
			{
				newVersion = fName.split("-")[1];
			}
		}
		else if (fName.contains("xmclient"))
		{
			fileTypeString = "漫游";
			logExcelName = "xmclient";
			if (fName.indexOf("-") > 0)
			{
				newVersion = fName.split("-")[1];
			}
		}
		else if (fName.contains("CellDataUpdaterRoam"))
		{
			fileTypeString = "漫游APK";
			logExcelName = "CellDataUpdaterRoam.apk";
			if (fName.indexOf("-") > 0)
			{
				String temps = fName.split("-")[1];
				newVersion = temps.split("\\.")[0];
			}
		}
		else if (fName.contains("CellDataUpdater"))
		{
			fileTypeString = "本地APK";
			logExcelName = "CellDataUpdater.apk";
			if (fName.indexOf("-") > 0)
			{
				String temps = fName.split("-")[1];
				String[] tt = temps.split("\\.");
				newVersion = tt[0];
			}
		}
		else if (fName.contains("MIP"))
		{
			fileTypeString = "MIP";
			logExcelName = "MIP.ini";
			if (fName.indexOf("-") > 0)
			{
				String temps = fName.split("-")[1];
				newVersion = temps.split("\\.")[0];
			}
		}
		else if (fName.contains("Settings"))
		{
			fileTypeString = "本地Settings";
			logExcelName = "Settings.apk";
			if (fName.indexOf("-") > 0)
			{
				String temps = fName.split("-")[1];
				newVersion = temps.split("\\.")[0];
			}
		}
		else if (fName.contains("Phone"))
		{
			fileTypeString = "Phone.apk";
			logExcelName = "Phone.apk";
			if (fName.indexOf("-") > 0)
			{
				String temps = fName.split("-")[1];
				newVersion = temps.split("\\.")[0];
			}
		}
		else
		{
			logger.info("上传文件格式不正确");
			req.setAttribute("temp", "-1");
			return "WEB-INF/views/service/remote_upload";
		}
		/** 拼成完整的文件保存路径加文件 **/
		String fileName = temp + File.separator + logExcelName;
		File files = new File(fileName);
		logger.info("升级文件的上传路径" + files);
		if (files.exists())
		{
			files.delete();
		}
		
		// 获取到文件的最后更新时间
		long time = files.lastModified();// lastSaveTime
		String lastSaveTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
		
		
		try
		{
			multipartFile.transferTo(files);
			// 在这里存入缓存
			Object object = CacheUtils.get("fileCache", "version");
			JSONObject json = null;
			
			if (object == null || "".equals(object.toString()))
			{
				json = new JSONObject();
			}
			else
			{
				// 进来说明从缓存里获取到了数据
				json = JSONObject.fromObject(object.toString());
				CacheUtils.remove("fileCache", "version");
			}
			
			if (json.containsKey(logExcelName))
			{
				json.remove(logExcelName);
			}
			
			json.put(logExcelName, newVersion + "," + lastSaveTime);
			CacheUtils.put("fileCache", "version", json.toString());
			req.setAttribute("temp", "1");
			req.setAttribute("fileTypeString", fileTypeString);
			req.setAttribute("newVersion", newVersion);
			logger.info("上传成功");
			return "WEB-INF/views/service/remote_upload";
		}
		catch (IllegalStateException e)
		{
			req.setAttribute("temp", "0");
			e.printStackTrace();
			logger.info("上传失败");
			return "WEB-INF/views/service/remote_upload";
		}
		catch (IOException e)
		{
			req.setAttribute("temp", "0");
			e.printStackTrace();
			logger.info("上传失败");
			return "WEB-INF/views/service/remote_upload";
		}
	}



	/* 拿到文件对应的版 本号 */
	public String getversion(String path, int index)
	{
		try
		{
			File file = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String string = br.readLine();
			String[] apk = string.split("/");
			br.close();
			return apk[index];
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}



	/* 写 */
	public void writeversion(String txtString, String path) throws IOException
	{
		FileWriter fw = new FileWriter(path);
		BufferedWriter writer = new BufferedWriter(fw);
		writer.write(txtString);
		writer.flush();
		writer.close();
	}



	/* 读 */
	public String replaceversion(String path, int index, String newversion)
	{
		try
		{
			File file = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String[] string = br.readLine().split("/");
			string[index] = newversion;
			String txt = "";
			for (int i = 0; i < string.length; i++)
			{
				if (i == string.length - 1)
				{
					txt = txt + string[i];
					break;
				}
				txt = txt + string[i] + "/";
			}
			br.close();
			System.out.println(txt);
			return txt;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}



	/* 下拉框的值改变 */
	@RequestMapping("/selectchange")
	public void selectchange(String apkName, HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException
	{
		resp.setCharacterEncoding("utf-8");
		String version = gettagString(apkName);
		if (StringUtils.isNotBlank(version))
		{
			version = version.split(",")[0];
		}
		PrintWriter writer = resp.getWriter();
		writer.print(version);
		writer.flush();
		writer.close();
	}



	public String gettagString(String sn)
	{
		Object object = CacheUtils.get("fileCache", "version");
		JSONObject json = null;
		if (object == null || "".equals(object.toString()))
		{
			return "";
		}
		else
		{
			json = JSONObject.fromObject(object.toString());
			if (json.containsKey(sn))
			{
				return json.getString(sn);
			}
			else
			{
				return "";
			}
		}
	}



	/* 下载日志 */
	@RequestMapping("/savelog")
	public String savelog(String fileName, String id, HttpServletResponse response, HttpServletRequest request)
	{
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");// inline attachment
		response.setHeader("Content-Disposition", "inline;fileName=" + fileName);
		try
		{
			EquipLogs equiplogs = new EquipLogs();
			equiplogs.setID(id);
			String log = equipLogsSer.getcontent(equiplogs).getContent();
			InputStream inputStream = getStringStream(log);

			OutputStream os = response.getOutputStream();
			byte[] b = new byte[1024];
			int length;
			while ((length = inputStream.read(b)) > 0)
			{
				os.write(b, 0, length);
				byte[] newLine = "/t".getBytes();
				// 写入换行
				os.write(newLine);
			}
			os.close();
			inputStream.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}



	public static InputStream getStringStream(String sInputString)
	{
		if (sInputString != null && !sInputString.trim().equals(""))
		{
			try
			{
				ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
				return tInputStringStream;
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return null;
	}



	/**
	 * 获取基站信息
	 * 
	 * @return
	 */
	@RequestMapping("/mapView")
	public String getLatlng(String jizhan, Model model, HttpServletResponse response)
	{
		logger.info("基站信息转换");
		if (StringUtils.isBlank(jizhan))
		{
			logger.info("基站信息为空");
			model.addAttribute("lat", "22.5445376939");
			model.addAttribute("lon", "113.9329587977");
			model.addAttribute("address", "比克大厦附近");
		}
		else
		{
			JSONObject object = LocTest.jizhantojw(jizhan, "50");
			if ("OK".equals(object.getString("cause")))
			{
				logger.info("基站转经纬度成功!");
				model.addAttribute("lat", object.getString("lat"));
				model.addAttribute("lon", object.getString("lon"));
				model.addAttribute("address", object.getString("address"));
				// model.addAttribute("radius",object.getString("radius"));
				return "WEB-INF/views/service/map";
			}
			else
			{
				logger.info("基站转经纬度失败:" + object.getString("cause"));
			}
		}
		List<CountryInfo> countries = countryInfoSer.getAll("");
		model.addAttribute("Countries", countries);
		return "WEB-INF/views/service/map";
	}



	/**
	 * 获取基站信息
	 * 
	 * @return
	 */
	@RequestMapping("/PosmapView")
	public String PosmapView(String jizhan, Model model, HttpServletResponse response)
	{
		logger.info("基站信息转换");
		if (StringUtils.isBlank(jizhan))
		{
			logger.info("基站信息为空");
			model.addAttribute("lat", "22.5414506852");
			model.addAttribute("lon", "113.9330059977");
			model.addAttribute("address", "比克大厦附近");
		}
		else
		{
			JSONObject object = LocTest.jizhantojw(jizhan, "50");
			if ("OK".equals(object.getString("cause")))
			{
				logger.info("基站转经纬度成功!");
				model.addAttribute("lat", object.getString("lat"));
				model.addAttribute("lon", object.getString("lon"));
				model.addAttribute("address", object.getString("address"));
				// model.addAttribute("radius",object.getString("radius"));
				return "WEB-INF/views/service/map_pos";
			}
			else
			{
				logger.info("基站转经纬度失败:" + object.getString("cause"));
			}
		}
		List<CountryInfo> countries = countryInfoSer.getAll("");
		model.addAttribute("ifopen", Constants.TIMING_ADDJZTEMP);
		model.addAttribute("Countries", countries);
		return "WEB-INF/views/service/map_pos";
	}



	/**
	 * 异步获取基站信息
	 * 
	 * @return
	 */
	@RequestMapping("/getLatlng")
	public void getLatlngAjax(String jizhan, Model model, HttpServletResponse response)
	{
		logger.info("基站信息转换");
		if (StringUtils.isBlank(jizhan))
		{
			logger.info("基站信息为空");
		}
		else
		{
			JSONObject object = LocTest.jizhantojw(jizhan, "50");
			if ("OK".equals(object.getString("cause")))
			{
				try
				{
					response.getWriter().print(object.toString());
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				logger.info("基站转经纬度失败:" + object.getString("cause"));
				try
				{
					response.getWriter().print(object.toString());
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}



	/**
	 * 异步获取基站信息
	 * 
	 * @return
	 */
	@RequestMapping("/getLatlngBySN")
	public void getLatlngBySN(String SN, Model model, HttpServletResponse response)
	{
		logger.info("基站信息转换");
		if (StringUtils.isBlank(SN))
		{
			logger.info("SN为空!");
		}
		else
		{
			// 查询该SN最近10条不重复的基站信息
			DeviceLogs dLogs = new DeviceLogs();
			dLogs.setSN(SN);
			dLogs.setTableName(Constants.DEVTABLEROOT_STRING + DateUtils.getDate().replace("-", ""));
			List<DeviceLogs> dList = deviceLogsSer.getJZ(dLogs);

			if (dList != null && dList.size() > 0)
			{
				JSONArray jArray = new JSONArray();
				// 由后到前排序.
				for (int i = dList.size() - 1; i > -1; i--)
				{
					JSONObject object = LocTest.jizhantojw(dList.get(i).getJizhan(), "50");
					if ("OK".equals(object.getString("cause")))
					{
						jArray.add(object);
					}
				}
				try
				{
					response.getWriter().print(jArray.toString());
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
	}



	/**
	 * 信号强度分析
	 * 
	 * @param SN
	 * @param model
	 * @param response
	 */
	@RequestMapping("/GStrenth")
	public void GStrenth(GStrenthData gd, Model model, HttpServletResponse response)
	{

		JSONArray jaArray = gStrenthDataSer.getbyMCC(gd);
		if (StringUtils.isNotBlank(jaArray))
		{
			try
			{
				response.getWriter().print(jaArray.toString());
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
				response.getWriter().print("0");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}



	@RequestMapping("/getversioninfo")
	public void getversioninfo(HttpServletResponse resp)
	{
		JSONObject object = new JSONObject();
		object.put("success", true);
		object.put("totalRows", 5);
		object.put("curPage", 1);
		JSONArray ja = new JSONArray();
		List<String> filesList = new ArrayList<>();
		filesList.add("manyou");
		filesList.add("localhost");
		filesList.add("mapk");
		filesList.add("lapk");
		filesList.add("MIP");
		for (String fileName : filesList)
		{
			String version = gettagString(fileName);
			JSONObject obj = new JSONObject();
			switch (fileName)
			{
				case "manyou":
					fileName = "漫游";
					break;
				case "localhost":

					fileName = "本地";
					break;
				case "mapk":
					fileName = "漫游apk";

					break;
				case "lapk":
					fileName = "本地apk";
					break;
				case "MIP":
					fileName = "MIP";

					break;

			}

			obj.put("fileName", fileName);
			if (StringUtils.isNotBlank(version))
			{
				obj.put("version", version.split(",")[0]);
				obj.put("lastUpdateTime", version.split(",")[1]);
			}
			else
			{
				obj.put("version", "");
				obj.put("lastUpdateTime", "");
			}
			ja.add(obj);
		}

		object.put("data", ja);
		try
		{
			System.out.println(object.toString());
			resp.getWriter().print(object.toString());
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * String version=gettagString(apkName);
		 * if(StringUtils.isNotBlank(version)){ version = version.split(",")[0];
		 * }
		 */

	}



	/**
	 * 异步获取基站信息
	 * 
	 * @return
	 */
	@RequestMapping("/getLatlngArr")
	public void getLatlngAjaxArr(String jizhan, Model model, HttpServletResponse response)
	{
		logger.info("多基站信息转换");
		if (StringUtils.isBlank(jizhan))
		{
			logger.info("基站信息为空");
		}
		else
		{
			String[] jizhanStrings = null;
			if (jizhan.indexOf(",") > -1)
			{
				String[] ar = jizhan.split(",");
				jizhanStrings = new String[ar.length];
				for (int i = 0; i < ar.length; i++)
				{
					jizhanStrings[i] = ar[i];
				}
			}
			else
			{
				jizhanStrings = new String[1];
				jizhanStrings[0] = jizhan;
			}
			JSONObject object = LocTest.jizhantojwArray(jizhanStrings);
			if ("OK".equals(object.getString("cause")))
			{
				try
				{
					response.getWriter().print(object.toString());
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				logger.info("基站转经纬度失败:" + object.getString("cause"));
				try
				{
					response.getWriter().print(object.toString());
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}



	/**
	 * 进去POS机查询界面
	 * 
	 * @param model
	 * @param response
	 */
	@RequestMapping("/toPosMap")
	public void toPosMap(Model model, HttpServletResponse response)
	{
		// 查询POS机最新基站列表
		DeviceLogs de = new DeviceLogs();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String times = format.format(date);
		String tname = "DeviceLogs_" + times;
		de.setTableName(tname);
		de.setSN(Constants.getConfig("POS.sn"));
		List<DeviceLogs> list = deviceLogsSer.getPOSjz(de);
		if (list != null && list.size() > 0)
		{
			String snarrString = Constants.getConfig("POS.sn");
			String jdmString = "";
			if (snarrString.indexOf(",") > -1)
			{
				jdmString = deviceInfoSer.getbysn((snarrString.split(",")[1])).getDeviceColour();
			}
			else
			{
				jdmString = deviceInfoSer.getbysn(snarrString).getDeviceColour();
			}
			JSONArray jaArray = new JSONArray();
			for (DeviceLogs d : list)
			{
				String jz = d.getJizhan();
				String[] jarr = null;
				if (jz.indexOf(",") > -1)
				{
					jarr = new String[] { jz.split(",")[0], jz.split(",")[1] };

				}
				else
				{
					jarr = new String[] { jz };
				}
				String[] arrtemp1 = jarr[0].split("\\.");
				String[] arrtemp2 = jarr[1].split("\\.");
				// 查询是否在基站组内.
				LocTemp LT = new LocTemp();
				LT.setLocalJZ(arrtemp1[0] + "." + arrtemp1[1] + "." + arrtemp1[2] + "." + arrtemp1[3]);
				LT.setRoamJZ(arrtemp2[0] + "." + arrtemp2[1] + "." + arrtemp2[2] + "." + arrtemp2[3]);
				List<LocTemp> lc = deviceLogsSer.getJWbyLike(LT);
				if (lc != null && lc.size() > 0)
				{
					JSONObject object = new JSONObject();
					object.put("lat", lc.get(0).getJW().split(",")[0]);
					object.put("lon", lc.get(0).getJW().split(",")[1]);
					object.put("SN", d.getSN());
					DeviceInfo ddDeviceInfo = deviceInfoSer.getbysn(d.getSN());
					object.put("jd", ddDeviceInfo.getRemark());
					object.put("jdm", jdmString);
					if (StringUtils.isBlank(ddDeviceInfo.getRemark()))
					{
						object.put("jdmjl", "未设置基点");
					}
					else
					{
						String[] re = ddDeviceInfo.getRemark().split(",");
						Double double1 = LocTest.Distance(Double.parseDouble(object.getString("lon")), Double.parseDouble(object.getString("lat")), Double.parseDouble(re[1]), Double.parseDouble(re[0]));
						String jlstrString = double1.toString().substring(0, double1.toString().indexOf("."));
						logger.info(d.getSN() + "距基点:" + jlstrString + "米");
						object.put("jdmjl", jlstrString);
					}
					jaArray.add(object);
				}
				else
				{
					// 过滤较远的基站
					if (arrtemp1[3].contains("106293") || arrtemp1[3].contains("106493") || arrtemp1[3].contains("106619"))
					{
						if (jz.split(",").length >= 3)
						{
							jarr[0] = jz.split(",")[2];
						}
					}
					if (arrtemp2[3].contains("106293") || arrtemp2[3].contains("106493") || arrtemp2[3].contains("106619"))
					{
						if (jz.split(",").length >= 4)
						{
							jarr[1] = jz.split(",")[3];
						}
						else if (jz.split(",").length >= 3)
						{
							jarr[1] = jz.split(",")[2];
						}
					}

					JSONObject object = LocTest.jizhantojwArray(jarr);
					if ("OK".equals(object.getString("cause")))
					{
						object.put("SN", d.getSN());
						DeviceInfo ddDeviceInfo = deviceInfoSer.getbysn(d.getSN());
						object.put("jd", ddDeviceInfo.getRemark());
						object.put("jdm", jdmString);
						if (StringUtils.isBlank(ddDeviceInfo.getRemark()))
						{
							object.put("jdmjl", "未设置基点");
						}
						else
						{
							String[] re = ddDeviceInfo.getRemark().split(",");
							Double double1 = LocTest.Distance(Double.parseDouble(object.getString("lon")), Double.parseDouble(object.getString("lat")), Double.parseDouble(re[1]), Double.parseDouble(re[0]));
							String jlstrString = double1.toString().substring(0, double1.toString().indexOf("."));
							logger.info(d.getSN() + "距基点:" + jlstrString + "米");
							object.put("jdmjl", jlstrString);
						}
						jaArray.add(object);
					}
				}

			}
			try
			{
				response.getWriter().print(jaArray.toString());
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
				response.getWriter().print("-1");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}



	/**
	 * 设置基点
	 * 
	 * @param deviceInfo
	 * @param response
	 */
	@RequestMapping("/setLoc")
	public void setLoc(DeviceInfo deviceInfo, HttpServletResponse response)
	{
		if (!StringUtils.isBlank(deviceInfo.getRemark()) || !StringUtils.isBlank(deviceInfo.getDeviceColour()))
		{
			if (StringUtils.isBlank(deviceInfo.getSN()))
			{
				String snarrString = Constants.getConfig("POS.sn");
				if (snarrString.indexOf(",") > -1)
				{
					deviceInfo.setSN(snarrString.split(",")[1]);
				}
				else
				{
					deviceInfo.setSN(snarrString);
				}
			}
			int temp = deviceInfoSer.updateRemark(deviceInfo);
			if (temp > 0)
			{
				try
				{
					response.getWriter().print("1");
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else
		{
			try
			{
				response.getWriter().print("-1");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}



	/**
	 * 导入样本数据
	 * 
	 * @param deviceLogs
	 * @param dw
	 * @param response
	 */
	@RequestMapping("/batchAddJZTemp")
	public void batchAddJZTemp(DeviceLogs deviceLogs, String dw, HttpServletResponse response)
	{
		// 查询原始数据
		List<DeviceLogs> lds = deviceLogsSer.getjzlist(deviceLogs);
		// 遍历组装,批量插入
		if (lds != null && lds.size() > 0)
		{
			List<LocTemp> listtemp = new ArrayList<LocTemp>();
			for (DeviceLogs d : lds)
			{
				String[] arr1 = d.getJizhan().split(",");
				String[] arr2 = arr1[0].split("\\.");
				String[] arr3 = arr1[1].split("\\.");
				LocTemp LT = new LocTemp();
				LT.setJW(dw);
				LT.setLocalJZ(arr2[0] + "." + arr2[1] + "." + arr2[2]);
				LT.setRoamJZ(arr3[0] + "." + arr3[1] + "." + arr3[2]);
				LT.setLocalXH(arr2[3]);
				LT.setRoamXH(arr3[3]);
				listtemp.add(LT);
			}
			// 批量插入基站组数据.
			int temp = deviceLogsSer.batchAddJZTemp(listtemp);
			if (temp > 0)
			{
				try
				{
					response.getWriter().print(temp);
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				try
				{
					response.getWriter().print("-1");
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else
		{
			try
			{
				response.getWriter().print("-2");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}



	/**
	 * 
	 * @param ifopen
	 * @param dw
	 * @param response
	 */
	@RequestMapping("/batchAddJZTempBtn")
	public void batchAddJZTempBtn(String ifopen, String dw, HttpServletResponse response)
	{
		if ("1".equals(ifopen))
		{
			Constants.TIMING_ADDJZTEMP = true;
			Constants.TIMING_ADDJZTEMP_JW = dw;
			try
			{
				response.getWriter().print("1");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			Constants.TIMING_ADDJZTEMP = false;
			try
			{
				response.getWriter().print("2");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}



	@RequestMapping("/index2")
	public String remoteindex2(Model model)
	{
		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (adminUserInfo == null)
		{

			return "login";
		}
		List<CountryInfo> countries = countryInfoSer.getAll("");
		model.addAttribute("Countries", countries);
		return "WEB-INF/views/service/remote_OnlineDevice_news";
	}



	@RequestMapping("/upload2")
	public String upload2(HttpServletRequest req, HttpSession session, Model model)
	{

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (adminUserInfo == null)
		{
			return "login";
		}
		List<CountryInfo> countries = countryInfoSer.getAll("");
		model.addAttribute("Countries", countries);
		model.addAttribute("temp", req.getSession().getAttribute("temp"));
		req.getSession().setAttribute("temp", ""); // 及时清空, 避免污染其他页面
		return "WEB-INF/views/service/remote_upload_news";
	}

}
