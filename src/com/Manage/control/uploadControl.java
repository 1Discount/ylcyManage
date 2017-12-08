package com.Manage.control;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.Manage.common.constants.Constants;
import com.Manage.common.enumEntity.UpgradeFilepathEnum;
import com.Manage.common.enumEntity.UploadFilepathEnum;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.DeviceLogs;
import com.Manage.entity.DeviceUpgrading;
import com.Manage.entity.UpgradeFileInfo;
import com.Manage.entity.common.SearchDTO;

@Controller
@RequestMapping("/upload")
public class uploadControl extends BaseController
{
	private final Logger logger = LogUtil.getInstance(uploadControl.class);



	@RequestMapping("/skipUpgradefile")
	public String skipUpgradefile(HttpServletRequest req, Model model)
	{

		AdminUserInfo user = (AdminUserInfo)getSession().getAttribute("User");
		if(user==null){
			return 	"login";
		}

		model.addAttribute("Msg", req.getSession().getAttribute("Msg"));
        req.getSession().setAttribute("Msg", "");
		return "WEB-INF/views/upload/upgradefile";
	}



	@RequestMapping("/queryPage")
	public void queryPage(HttpServletRequest request, HttpServletResponse response)
	{

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String[] files = new String[] { "xmclient", "CellDataUpdaterRoam.apk", "MIP_List.ini",
		        "local_client", "CellDataUpdater.apk", "Settings.apk","Phone.apk",
		        "console.war", "i-jetty-3.2-SNAPSHOT.apk", "wifidog", "wifidog.conf",
		        "wifidog-msg.html","root.war",
		        "xmclient_4G", "CellDataUpdaterRoam_4G.apk", "MIP_List_4G.ini",
		        "local_client_4G", "CellDataUpdater_4G.apk", "Settings_4G.apk","Phone_4G.apk",
		        "console_4G.war", "i-jetty-3.2-SNAPSHOT_4G.apk", "wifidog_4G", "wifidog_4G.conf",
		        "wifidog-msg_4G.html","root_4G.war"};

		JSONObject object = new JSONObject();
		JSONObject obj = new JSONObject();
		JSONArray ja = new JSONArray();
		object.put("success", true);
		object.put("totalRows", files.length);
		object.put("curPage", 1);
		
		UpgradeFileInfo upgradeFileInfo=null;
		for (int i = 0; i < files.length; i++)
		{
			String temp = request.getSession().getServletContext().getRealPath(files[i].indexOf("_4G")>-1 ? "/upgradeFile4G/":"/upgradeFile/" + files[i].replace("_4G",""));
			temp = request.getSession().getServletContext().getRealPath("/upgradeFile/"+files[i]);
			File tempFile = new File(temp);
			long size = tempFile.length();
			long time = tempFile.lastModified();
			String date = sdf.format(time);

			obj.put("name", files[i]);
			obj.put("size", size / 1024 + "KB");
			obj.put("time", date);
			upgradeFileInfo=upgradeFileInfoSer.selectOneByFileName(files[i]);
			obj.put("version",upgradeFileInfo==null ? "": upgradeFileInfo.getVersion());
			ja.add(obj);
		}

		object.put("data", ja.toString());
		try
		{
			response.getWriter().print(object.toString());

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}



	@RequestMapping("/uploadding")
	public String uploadding(@RequestParam("file") MultipartFile file,String upgradeFile,String version, HttpServletRequest req, HttpServletResponse resp, HttpSession session, Model model)
	{
	    	AdminUserInfo user = (AdminUserInfo)getSession().getAttribute("User");
		if(user==null){
			return 	"login";
		}
		logger.info("开始上传");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
		MultipartFile multipartFile = multipartRequest.getFile("file");
		logger.info("成功获取到文件");
		String temp = request.getSession().getServletContext().getRealPath(file.getOriginalFilename().indexOf("_4G")>-1 ? "/upgradeFile4G/" : "/upgradeFile/");
		temp = request.getSession().getServletContext().getRealPath("/upgradeFile/");
		File tempFile = new File(temp);
		if (!tempFile.exists())
		{
			tempFile.mkdirs();
		}
		// 组装上传路径
		String fileName = temp + File.separator + multipartFile.getOriginalFilename().replace("_4G", "");
		fileName = temp + File.separator + multipartFile.getOriginalFilename();
		File files = new File(fileName);
		logger.info("升级文件的上传路径" + files);
		if (files.exists())
		{
			files.delete();
		}
		try
		{
			multipartFile.transferTo(files);
			upgradeFileInfoSer.add(upgradeFile, files.getName(), Integer.parseInt(files.length()+""), version, Constants.FILEPATHHEAD+files.getName(), "备注",user.getUserID());
			logger.info("上传成功");
			req.getSession().setAttribute("Msg", "00");
		}
		catch (IllegalStateException | IOException e)
		{
			e.printStackTrace();
			logger.info("上传失败");
			req.getSession().setAttribute("Msg", "01");
		}
		return "redirect:skipUpgradefile"; // fixed 成功上传后跳转的左侧菜单激活问题 returnUpgradeFile
	}



	@RequestMapping("/returnUpgradeFile")
	public String returnbatchorder(HttpServletRequest req, HttpSession session, Model model)
	{
		AdminUserInfo user = (AdminUserInfo)getSession().getAttribute("User");
		if(user==null){
			return 	"login";
		}

		model.addAttribute("Msg", req.getSession().getAttribute("Msg"));
		req.getSession().setAttribute("Msg", "");
		return "WEB-INF/views/upload/upgradefile";
	}
	
	/**
	 * portal下载升级文件，将portal传过的值转换得到下载地址并返回
	 * @param pathStr
	 * @param req
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/getUpgradeFilepath")
	@ResponseBody
	public Object getUpgradeFilepath(String pathStr,HttpServletRequest req, HttpSession session, Model model)
	{
		String[] filepath=null;
		try{
			if(pathStr.indexOf(",")>-1){
				filepath=pathStr.split(",");
			}else{
				filepath=new String[]{pathStr};
			}
//			EnumMap<UpgradeFilepathEnum, String> upgradeFilepathEnumMap = new EnumMap(UpgradeFilepathEnum.class);
			for(int i=0;i<filepath.length;i++){
				filepath[i]=Constants.FILEPATHHEAD+UpgradeFilepathEnum.getName(filepath[i]);
			}
			return filepath;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 *app获取最新升级文件信息 
	 * @param deviceType
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("/getUpgradeFileVersions")
	@ResponseBody
	public Object getUpgradeFileVersions(String deviceType,HttpServletRequest req, HttpServletResponse resp){
	    Map<String,Object> result=new HashMap<String,Object>();
	    try {
		if(StringUtils.isEmpty(deviceType)){
		    deviceType=req.getParameter("deviceType");
		}
		if(StringUtils.isEmpty(deviceType)){
		    result.put("error",0);
		    result.put("msg","关键参数为空");
		    return result;
		}else{
		    String[] files =null;
		    if(deviceType.equals("3G")){
			files=new String[] { "xmclient", "CellDataUpdaterRoam.apk", "MIP_List.ini",
			        "local_client", "CellDataUpdater.apk", "Settings.apk","Phone.apk",
			        "console.war", "i-jetty-3.2-SNAPSHOT.apk", "wifidog", "wifidog.conf",
			        "wifidog-msg.html","root.war"};
		    }else if(deviceType.equals("4G")){
			files=new String[] {
			        "xmclient_4G", "CellDataUpdaterRoam_4G.apk", "MIP_List_4G.ini",
			        "local_client_4G", "CellDataUpdater_4G.apk", "Settings_4G.apk","Phone_4G.apk",
			        "console_4G.war", "i-jetty-3.2-SNAPSHOT_4G.apk", "wifidog_4G", "wifidog_4G.conf",
			        "wifidog-msg_4G.html","root_4G.war"};
		    }else{
			result.put("error",0);
			result.put("msg","关键参数值不正确");
			return result;
		    }
		    
		    UpgradeFileInfo upgradeFileInfo=null;
		    List<UpgradeFileInfo> upgradeFileInfos=new ArrayList<UpgradeFileInfo>();
		    for (int i = 0; i < files.length; i++)
		    {
			upgradeFileInfo=upgradeFileInfoSer.selectOneByFileName(files[i]);
			if(upgradeFileInfo==null){
			    continue;
			}else{
			    upgradeFileInfos.add(upgradeFileInfo);
			}
		    }
		    result.put("fileList", upgradeFileInfos);
		    result.put("error",1);
		    result.put("msg","获取成功");
		}
	    } catch (Exception e) {
		 result.put("error",0);
		 result.put("msg","出现异常");
		 return result;
	    }
	    return result;
	}
	
	/**
	 * app请求升级
	 */
	@RequestMapping("/getUpgrade")
	@ResponseBody
	public Object getUpgrade(String sn,String fileList,HttpServletRequest req, HttpServletResponse resp){
	    Map<String,Object> result=new HashMap<String,Object>();
	    result.put("error",0);
	    result.put("msg","参数错误");
	    try {
		if(StringUtils.isEmpty(sn) || StringUtils.isEmpty(fileList)){
		    return result;
		}else{
		    //将需要升级的文件加入到配置升级记录中
		    DeviceUpgrading upgrading=new DeviceUpgrading();
		    upgrading.setSN(sn);
		    upgrading.setUpgradeFileType(fileList);
		    upgrading.setUpdateSource(2);
		    upgrading.setIfForcedToUpgrade(true);
		    upgrading.setRemark("app");
		    int count=0;
		    if (upgrading.getSN().indexOf("/") >= 0)
			{
				String[] snarr = upgrading.getSN().split("/");
				for (int i = 0; i < snarr.length; i++)
				{
					// 查询是否有未升级的（而不是之前不分是否已升级）
					DeviceUpgrading upgrading1 = deviceInfoSer.getNotUpdatedBySN(Constants.SNformat(snarr[i]));
					if (upgrading1 == null)
					{
						upgrading.setDeviceUpgradingID(getUUID());
						upgrading.setSysStatus(true);
						upgrading.setIfUpdated(false);
						upgrading.setCreatorUserID(Constants.SNformat(snarr[i]));
						upgrading.setCreatorUserName(Constants.SNformat(snarr[i]));
						upgrading.setCreatorDate(DateUtils.formatDate(new Date()));
						upgrading.setSN(Constants.SNformat(snarr[i]));
						if (deviceInfoSer.insert(upgrading))
						{
							count++;
						}
					}
					// else if
					// (deviceInfoSer.getBySN2(Constants.SNformat(snarr[i])) !=
					// null)
					else
					{
						// 升级文件类型处理
						String upGradeFileType = upgrading.getUpgradeFileType()+','+upgrading1.getUpgradeFileType();
						String[] upGradeFileTypeArr =  upGradeFileType.split(",");
						Map<String, String> map = new HashMap<>();
						for (int k = 0; k < upGradeFileTypeArr.length; k++)
						{
							map.put(upGradeFileTypeArr[k], "");
						}
						
						String fileType = "";
						for (String key : map.keySet())
						{
							fileType = fileType+key+",";
						}
						fileType = fileType.substring(0,fileType.length()-1);
						
						upgrading1.setSysStatus(true);
						upgrading1.setIfUpdated(false);
						upgrading1.setSN(Constants.SNformat(upgrading.getSN()));
						upgrading1.setIfForcedToUpgrade(upgrading.getIfForcedToUpgrade());
						upgrading1.setUpgradeFileType(fileType);
						upgrading1.setRemark(upgrading.getRemark());
						upgrading1.setModifyUserID(Constants.SNformat(upgrading.getSN()));
						upgrading1.setModifyDate(DateUtils.formatDate(new Date()));
						if (deviceInfoSer.updateUpgrade(upgrading1))
						{
							count++;
						}
					}
				}

			}
			else
			{
				// 查询是否有未升级的（而不是之前不分是否已升级）
				DeviceUpgrading upgrading1 = deviceInfoSer.getNotUpdatedBySN(Constants.SNformat(upgrading.getSN()));
				if (upgrading1 == null)
				{
					upgrading.setDeviceUpgradingID(getUUID());
					upgrading.setSysStatus(true);
					upgrading.setIfUpdated(false);
					upgrading.setCreatorUserID(Constants.SNformat(upgrading.getSN()));
					upgrading.setCreatorUserName(Constants.SNformat(upgrading.getSN()));
					upgrading.setCreatorDate(DateUtils.formatDate(new Date()));
					upgrading.setSN(Constants.SNformat(upgrading.getSN()));
					if (deviceInfoSer.insert(upgrading))
					{
						count++;
					}
				}
				else
				{
					// 升级文件类型处理
					String upGradeFileType = upgrading.getUpgradeFileType()+','+upgrading1.getUpgradeFileType();
					String[] upGradeFileTypeArr =  upGradeFileType.split(",");
					Map<String, String> map = new HashMap<>();
					for (int k = 0; k < upGradeFileTypeArr.length; k++)
					{
						map.put(upGradeFileTypeArr[k], "");
					}
					
					String fileType = "";
					for (String key : map.keySet())
					{
						fileType = fileType+key+",";
					}
					fileType = fileType.substring(0,fileType.length()-1);
					
					upgrading1.setSysStatus(true);
					upgrading1.setIfUpdated(false);
					upgrading1.setSN(Constants.SNformat(upgrading.getSN()));
					upgrading1.setIfForcedToUpgrade(upgrading.getIfForcedToUpgrade());
					upgrading1.setUpgradeFileType(fileType);
					upgrading1.setRemark(upgrading.getRemark());
					upgrading1.setModifyUserID(Constants.SNformat(upgrading.getSN()));
					upgrading1.setModifyDate(DateUtils.formatDate(new Date()));
					if (deviceInfoSer.updateUpgrade(upgrading1))
					{
						count++;
					}
				}
			}
		    
		    if(count!=0){
			result.put("error",1);
			result.put("msg","请求升级成功");
		    }
		}
		
	    } catch (Exception e) {
		 result.put("error",0);
		 result.put("msg","出现异常");
		 return result;
	    }
	    return result;
	}
}