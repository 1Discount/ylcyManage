package com.Manage.control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Manage.common.constants.Constants;
import com.Manage.common.util.DES;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.AdminUserInfo;

/**
 * 
 * @author 沈超
 *
 */
@Controller
@RequestMapping("/backgroundcheck")
public class BackgroundCheckControl extends BaseController
{
	//rivate static Logger logger = LogUtil.getInstance(BackgroundCheckControl.class);
	
	/**
	 * 检测数据库连接是否正常以及延时
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/connectionDelayed")
	public void connectionDelayed(HttpServletRequest request, HttpServletResponse response, int start) throws UnsupportedEncodingException
	{
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		try
		{
			Thread.sleep(start*1000);
		}
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		String jsonResult = backgroundCheckSer.connectionDelayed();
		try
		{
			response.getWriter().write(jsonResult);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询当前数据库连接数
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/connectionNum")
	public void connectionNum(HttpServletRequest request, HttpServletResponse response, int start) throws UnsupportedEncodingException
	{

		try
		{
			Thread.sleep(start*1000);
		}
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		
		String jsonResult = backgroundCheckSer.connectionNum();
		
		try
		{
			response.getWriter().write(jsonResult);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 数据库定时任务是否打开正常运行
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/timingTaskOpen")
	public void timingTaskOpen(HttpServletRequest request, HttpServletResponse response, int start) throws UnsupportedEncodingException
	{
		try
		{
			Thread.sleep(start*1000);
		}
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		
		String jsonResult = backgroundCheckSer.timingTaskOpen();
		
		try
		{
			response.getWriter().write(jsonResult);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询是否有锁表超时现象
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/lockTable")
	public void lockTable(HttpServletRequest request, HttpServletResponse response, int start) throws UnsupportedEncodingException
	{
		try
		{
			Thread.sleep(start*1000);
		}
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		
		String jsonResult = backgroundCheckSer.lockTable();
		
		try
		{
			response.getWriter().write(jsonResult);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 数据同步（备份）是否正常运行
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/dataSynchronization")
	public void dataSynchronization(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException
	{
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		
		String jsonResult = backgroundCheckSer.dataSynchronization();
		
		try
		{
			response.getWriter().write(jsonResult);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 表结构是否完整，设备日志表是否正常
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/tableIntegrity")
	public void tableIntegrity(HttpServletRequest request, HttpServletResponse response, int start) throws UnsupportedEncodingException
	{
		try
		{
			Thread.sleep(start*1000);
		}
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		
		String jsonResult = backgroundCheckSer.tableIntegrity();
		
		try
		{
			response.getWriter().write(jsonResult);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 检测后台服务器是否正常访问，访问速度是否正常
	 * @param request
	 * @param response
	 */
	@RequestMapping("/testLogin")
	public void testLogin(HttpServletRequest request, HttpServletResponse response, int start)
	{
		try
		{
			Thread.sleep(start*1000);
		}
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		response.setContentType("application/json;charset=UTF-8");
		
		JSONObject result = checkLogin();
		
		try
		{
			response.getWriter().write(result.toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 检查设备升级文件目录和升级文件是否正常
	 * @param req
	 * @param resp
	 */
	@RequestMapping("/testUpgradeFileDirectory")
	public void testUpgradeFileDirectory(HttpServletRequest req, HttpServletResponse resp, int start)
	{
		try
		{
			Thread.sleep(start*1000);
		}
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		resp.setContentType("application/json;charset=UTF-8");
		
		JSONObject result = checkUpgradeFile("upgradeFile");
		JSONObject result4G = checkUpgradeFile("upgradeFile4G");
		try
		{
			resp.getWriter().write("3G:"+result.toString()+";4G:"+result4G.toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 检测是否能够连上TDM进行远程命令下发以及延时
	 * @param req
	 * @param resp
	 */
	@RequestMapping("/testConnectionTDM")
	public void testConnectionTDM(HttpServletRequest req, HttpServletResponse resp, int start)
	{
		try
		{
			Thread.sleep(start*1000);
		}
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		resp.setContentType("application/json;charset=UTF-8");
		
		JSONObject result = backgroundCheckSer.checkConnectionTDM();
		
		try
		{
			resp.getWriter().write(result.toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * CPU，内存使用情况
	 * @param request
	 * @param response
	 */
	@RequestMapping("/testCPU")
	public void testCPU(HttpServletRequest request, HttpServletResponse response, int start)
	{
		try
		{
			Thread.sleep(start*1000);
		}
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		response.setContentType("application/json;charset=UTF-8");
		
		JSONObject result = new JSONObject();
		
		String cpuInfo = backgroundCheckSer.checkCPU();
		
		if(cpuInfo != null)
		{
			String[] cpu = cpuInfo.split("\\|");
			if(Double.parseDouble(cpu[2])>80)
			{
				result.put("success", "异常");
				result.put("data", "CPU使用情况：    IP:"+cpu[0]+",CPU总使用率:"+cpu[1]+"%,JAVA CPU使用率:"+cpu[2]+"%");
				result.put("javacpu", cpu[2]);
			}
			else
			{
				result.put("success", "正常");
				result.put("data", "CPU使用情况：    IP:"+cpu[0]+",CPU总使用率:"+cpu[1]+"%,JAVA CPU使用率:"+cpu[2]+"%");
				result.put("javacpu", cpu[2]);
			}
		}
		else
		{
			result.put("success", "CPU情况异常");
		}
		
		try
		{
			response.getWriter().write(result.toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 检测远程服务功能是否正常，获取日志和远程升级端口是否正常
	 * @param request
	 * @param response
	 */
	@RequestMapping("/testPort")
	public void testPort(HttpServletRequest request, HttpServletResponse response, int start)
	{
		try
		{
			Thread.sleep(start*1000);
		}
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		response.setContentType("application/json;charset=UTF-8");
		
		JSONObject jsonResult = new JSONObject();
		
		String portResult = backgroundCheckSer.checkPort();
		
		String[] result = portResult.split("\\|");
		
		if(result[0].equals("true")&&result[1].equals("true"))
		{
			jsonResult.put("isSuccess", true);
			jsonResult.put("msg", "正常");
		}
		else if(result[0].equals("false")&&result[1].equals("true"))
		{
			jsonResult.put("isSuccess", false);
			jsonResult.put("msg", "80端口异常");
		}
		else if(result[0].equals("true")&&result[1].equals("false"))
		{
			jsonResult.put("isSuccess", false);
			jsonResult.put("msg", "8070端口异常");
		}
		else if(result[0].equals("false")&&result[1].equals("false"))
		{
			jsonResult.put("isSuccess", false);
			jsonResult.put("msg", "80、8070端口异常");
		}
		
		try
		{
			response.getWriter().write(jsonResult.toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 检测是否有异常设备日志记录影响逻辑
	 * @param request
	 * @param response
	 */
	@RequestMapping("/testDeviceLogs")
	public void testDeviceLogs(HttpServletRequest request, HttpServletResponse response, int start)
	{
		try
		{
			Thread.sleep(start*1000);
		}
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		response.setContentType("application/json;charset=UTF-8");
		
		String jsonResult = backgroundCheckSer.testDeviceLogs();
		
		try
		{
			response.getWriter().write(jsonResult);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 服务器CPU情况，JAVA所占CPU,以及CPU过高的进程
	 * @param request
	 * @param response
	 */
	@RequestMapping("/testCPUForTX")
	public void testCPUForTX(HttpServletRequest request, HttpServletResponse response, int start)
	{
		try
		{
			Thread.sleep(start*1000);
		}
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		response.setContentType("application/json;charset=UTF-8");
		
		JSONObject result = new JSONObject();
		
		String cpuInfo = backgroundCheckSer.checkCPUForTX();
		
		if(cpuInfo != null)
		{
			String[] cpu = cpuInfo.split("\\|");
			if(Double.parseDouble(cpu[2])>80)
			{
				result.put("success", "异常");
				result.put("data", "CPU使用情况：    IP:"+cpu[0]+",CPU使用率:"+cpu[1]+"%,JAVA CPU使用率:"+cpu[2]+"%");
				result.put("javacpu", cpu[2]);
			}
			else
			{
				result.put("success", "正常");
				result.put("data", "CPU使用情况：    IP:"+cpu[0]+",CPU使用率:"+cpu[1]+"%,JAVA CPU使用率:"+cpu[2]+"%");
				result.put("javacpu", cpu[2]);
			}
		}
		else
		{
			result.put("success", "CPU情况异常");
		}
		
		try
		{
			response.getWriter().write(result.toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * socket服务是否正常
	 * @param request
	 * @param response
	 */
	@RequestMapping("/testPortForTX")
	public void testPortForTX(HttpServletRequest request, HttpServletResponse response, int start)
	{
		try
		{
			Thread.sleep(start*1000);
		}
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		response.setContentType("application/json;charset=UTF-8");
		
		JSONObject jsonResult = new JSONObject();
		
		String port = backgroundCheckSer.checkPortForTX();
		
		String[] result = port.split("\\|");
		
		if(result[0].equals("true")&&result[1].equals("true"))
		{
			jsonResult.put("msg", "正常");
		}
		else if(result[0].equals("false")&&result[1].equals("true"))
		{
			jsonResult.put("msg", "8080端口异常");
		}
		else if(result[0].equals("true")&&result[1].equals("false"))
		{
			jsonResult.put("msg", "8090端口异常");
		}
		else if(result[0].equals("false")&&result[1].equals("false"))
		{
			jsonResult.put("msg", "8080、8090端口异常");
		}
		
		try
		{
			response.getWriter().write(jsonResult.toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * socket线程数是否过多
	 * @param request
	 * @param response
	 */
	@RequestMapping("/testSocketNumForTX")
	public void testSocketNumForTX(HttpServletRequest request, HttpServletResponse response, int start)
	{
		try
		{
			Thread.sleep(start*1000);
		}
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		response.setContentType("application/json;charset=UTF-8");

		JSONObject jsonResult = new JSONObject();
		
		String result = backgroundCheckSer.checkSocketNumForTX();
		
		if(Integer.parseInt(result)>500)
		{
			jsonResult.put("msg", "线程数过多");
			jsonResult.put("num", result);
		}
		else
		{
			jsonResult.put("msg", "");
			jsonResult.put("num", result);
		}
		
		try
		{
			response.getWriter().write(jsonResult.toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 是否有黏包现象
	 * @param request
	 * @param response
	 */
	@RequestMapping("/testIfNB")
	public void testIfNB(HttpServletRequest request, HttpServletResponse response,int start)
	{
		try
		{
			Thread.sleep(start*1000);
		}
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		response.setContentType("application/json;charset=UTF-8");
		Object[] par = null;
		try
		{
			String jsonResult = getRequest("http://test.easy2go.cn:10080/SocketServer/api/SocketServer/FlowOrderRes/checkifNB", par);
			
			JSONObject jo = JSONObject.fromObject(jsonResult);
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//小写的mm表示的是分钟
			
			Date date=sdf.parse(jo.getJSONObject("data").get("WARN").toString());
			
			Date d = new Date();
			
			if(d.getTime()-date.getTime()>7200000)
			{
				jo.put("isSuccess", true);
			}
			else
			{
				jo.put("isSuccess", false);
			}
			
			response.getWriter().write(jo.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 检测官网是否能正常访问
	 * @param request
	 * @param response
	 */
	@RequestMapping("/testGWnormal")
	public void testGWnormal(HttpServletRequest request, HttpServletResponse response, int start)
	{
		try
		{
			Thread.sleep(start*1000);
		}
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		response.setContentType("application/json;charset=UTF-8");
		
		String jsonResult = backgroundCheckSer.checkGWnormal();
		
		try
		{
			response.getWriter().write(jsonResult);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 检测关键文件目录和文件是否正常（APP版本文件）
	 * @param request
	 * @param response
	 */
	@RequestMapping("/testAppFile")
	public void testAppFile(HttpServletRequest request, HttpServletResponse response, int start)
	{
		try
		{
			Thread.sleep(start*1000);
		}
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		response.setContentType("application/json;charset=UTF-8");
		
		Object[] par = null;
		
		try
		{
			String jsonResult = getRequest("http://www.easy2go.cn/v2/reserve/checkappfile", par);
			
			response.getWriter().write(jsonResult);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 检测APP接口是否正常访问
	 * @param request
	 * @param response
	 */
	@RequestMapping("/testAPPinterface")
	public void testAPPinterface(HttpServletRequest request, HttpServletResponse response, int start)
	{
		try
		{
			Thread.sleep(start*1000);
		}
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		response.setContentType("application/json;charset=UTF-8");
		
		JSONObject jsonResult = new JSONObject();
		
		boolean boolResult = backgroundCheckSer.checkAPPinterface(request);
		
		if(boolResult)
		{
			jsonResult.put("isSuccess", true);
			jsonResult.put("msg", "能正常访问");
		}
		else
		{
			jsonResult.put("isSuccess", false);
			jsonResult.put("msg", "不能正常访问");
		}
		
		try
		{
			response.getWriter().write(jsonResult.toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 写入文件记录
	 * @param request
	 * @param response
	 * @param exceptionStr
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/writeFileRecord")
	public void writeFileRecord(HttpServletRequest request, HttpServletResponse response, String exceptionStr) throws UnsupportedEncodingException
	{
		try
		{
		   File f = new File(Constants.BACKGROUND_WARN_FILE_URL);
		   if(!f.exists())
		   {
		   	   f.createNewFile();//不存在则创建
		   }
		   String[] exceptions = exceptionStr.split("\\|");
		   
		   BufferedWriter output = new BufferedWriter(new FileWriter(f,true));
		   
		   output.write(exceptions[0]+"\r\n"+exceptions[1]+"\r\n"+exceptions[2]+"\r\n"+exceptions[3]+"\r\n"+"-----------------------------------------------------------------------------"+"\r\n");
		   
		   output.close();
		}
		catch (Exception e)
		{
		   e.printStackTrace();
		}
	}
	
	
	/**
	 * 登陆测试
	 * @return
	 */
	public JSONObject checkLogin()
	{
		
		AdminUserInfo admin = new AdminUserInfo();
		
		try
		{
			admin.setEmail("shenchao@easy2go.cn");
			admin.setpassword(DES.toHexString(DES.encrypt("zxc12345..", Constants.DES_KEY)));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		JSONObject result = new JSONObject();
		
		if (admin != null)
		{
			long t1 = (new Date()).getTime();
			
			AdminUserInfo dto = adminuserinfoser.login(admin);
			
			long t2 = (new Date()).getTime();
			
			if (dto != null && dto.getUserID() != null)
			{
				result.put("success", "是");
				result.put("data", t2-t1);
			}
			else
			{
				result.put("success", "否");
			}
		}
		
		return result;
	}
	
	/**
	 * 检查设备升级文件目录和升级文件是否正常
	 * @return
	 */
	public JSONObject checkUpgradeFile()
	{
		JSONObject result = new JSONObject();
		String temp = request.getSession().getServletContext().getRealPath("/upgradeFile/");
		File file = new File(temp);
		if(!file.exists())
		{
			result.put("data", "文件目录不存在");
		}
		else
		{
			File[] tempList = file.listFiles();
			String fileName = "";
			for (int i = 0; i<tempList.length; i++)
			{
				if(tempList[i].isFile())
				{
					if(fileName.equals(""))
					{
						fileName+=tempList[i].getName();
					}
					else
					{
						fileName += ","+tempList[i].getName();
					}
				}
			}
			result.put("data", fileName);
		}
		
		return result;
	}
	/**
	 * 检查设备升级文件目录和升级文件是否正常
	 * @return
	 */
	public JSONObject checkUpgradeFile(String path)
	{
		JSONObject result = new JSONObject();
		String temp = request.getSession().getServletContext().getRealPath("/"+path+"/");
		File file = new File(temp);
		if(!file.exists())
		{
			result.put("data", "文件目录不存在");
		}
		else
		{
			File[] tempList = file.listFiles();
			String fileName = "";
			for (int i = 0; i<tempList.length; i++)
			{
				if(tempList[i].isFile())
				{
					if(fileName.equals(""))
					{
						fileName+=tempList[i].getName();
					}
					else
					{
						fileName += ","+tempList[i].getName();
					}
				}
			}
			result.put("data", fileName);
		}
		
		return result;
	}
	
	/**
	 * 检测是否设备日志正常运行
	 * @param request
	 * @param response
	 * 
	 */
	@RequestMapping("/checkLastDevicelogsForTX")
	public void checkLastDevicelogsForTX(HttpServletRequest request, HttpServletResponse response, int start)
	{
		try
		{
			Thread.sleep(start*1000);
		}
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		String timeString=backgroundCheckSer.checkLastDevicelogsForTX();
		try
		{
			response.getWriter().write(timeString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getCPUForTS")
	public void getCPUForTS(HttpServletRequest request, HttpServletResponse response, boolean flag)
	{
		if(flag)
		{
			try
			{
				Thread.sleep(5000);
			}
			catch (InterruptedException e1)
			{
				e1.printStackTrace();
			}
		}
		
		response.setContentType("application/json;charset=UTF-8");
		JSONArray ja = new JSONArray();
		String cpu = backgroundCheckSer.getCPUForTS();
		String[] cpus = cpu.split("\\|");
		for (int i = 0; i < cpus.length; i++)
		{
			JSONObject jsonObject = new JSONObject();
			String[] data = cpus[i].split(",");
			jsonObject.put("IP", data[0]);
			for (int j = 1; j < data.length; j++)
			{
				JSONObject jo = new JSONObject();
				String[] str = data[j].split(":");
				String[] str2 = str[1].split("-");
				jo.put("CPU", str2[0]);
				jo.put("TC", str2[1]);
				jo.put("NC", str2[2]);
				jo.put("PID", str2[3]);
				jsonObject.put(str[0], jo);
			}
			ja.add(jsonObject);
		}
		System.out.println(ja.toString());
		
		try
		{
			response.getWriter().write(ja.toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/restart")
	public void restart(HttpServletRequest request, HttpServletResponse response, String ip, String pname)
	{
		response.setContentType("application/json;charset=UTF-8");
		
		String result = backgroundCheckSer.RebootForTS(ip, pname);
		
		JSONObject jsonResult = new JSONObject();
		
		if(StringUtils.isNotBlank(result.trim()))
		{
			String[] strs = result.trim().split(" ");
			
			jsonResult.put("command", strs[0]);
			
			jsonResult.put("IP", strs[1]);
			
			jsonResult.put("pname", strs[2]);
			
			jsonResult.put("result", strs[3]);
		}

		try
		{
			response.getWriter().write(jsonResult.toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/shutdown")
	public void shutdown(HttpServletRequest request, HttpServletResponse response, String ip, String pname)
	{
		response.setContentType("application/json;charset=UTF-8");
		
		String result = backgroundCheckSer.ShutDownForTS(ip, pname);
		
		JSONObject jsonResult = new JSONObject();
		
		if(StringUtils.isNotBlank(result.trim()))
		{
			String[] strs = result.trim().split(" ");
			
			jsonResult.put("command", strs[0]);
			
			jsonResult.put("IP", strs[1]);
			
			jsonResult.put("pname", strs[2]);
			
			jsonResult.put("result", strs[3]);
		}
		
		try
		{
			response.getWriter().write(jsonResult.toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 检测硬盘剩余容量
	 * @param request
	 * @param response
	 * @param start
	 */
	@RequestMapping("/testHardDiskSpace")
	public void testHardDiskSpace(HttpServletRequest request, HttpServletResponse response, int start)
	{
		try
		{
			Thread.sleep(start*1000);
		}
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		response.setContentType("application/json;charset=UTF-8");
		
		String result = backgroundCheckSer.getHardDiskSpace();
		
		try
		{
			response.getWriter().write(result);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
