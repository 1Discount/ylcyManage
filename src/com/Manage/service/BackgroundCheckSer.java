package com.Manage.service;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.restlet.data.Form;
import org.restlet.resource.ClientResource;
import org.springframework.stereotype.Service;

import com.Manage.common.latlng.RemoteUtil;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.SSHClient;
import com.Manage.common.util.StringUtils;
import com.Manage.service.SocketClient.SocketClient;
import com.Manage.service.SocketClient.SocketLongClient;

/**
 *
 * @author 沈超
 *
 */
@Service
public class BackgroundCheckSer extends BaseService
{
	//private static Logger logger = LogUtil.getInstance(BackgroundCheckSer.class);
	/**
	 * 检测数据库连接是否正常以及延时
	 *
	 * @return
	 */
	public String connectionDelayed()
	{
		return backgroundCheckDao.connectionDelayed();
	}



	/**
	 * 查询当前数据库连接数
	 *
	 * @return
	 */
	public String connectionNum()
	{
		return backgroundCheckDao.connectionNum();
	}



	/**
	 * 数据库定时任务是否打开正常运行
	 *
	 * @return
	 */
	public String timingTaskOpen()
	{
		return backgroundCheckDao.timingTaskOpen();
	}



	/**
	 * 查询是否有锁表超时现象
	 *
	 * @return
	 */
	public String lockTable()
	{
		return backgroundCheckDao.lockTable();
	}



	/**
	 * 数据同步（备份）是否正常运行
	 *
	 * @return
	 */
	public String dataSynchronization()
	{
		return backgroundCheckDao.dataSynchronization();
	}



	/**
	 * 表结构是否完整，设备日志表是否正常
	 *
	 * @return
	 */
	public String tableIntegrity()
	{
		return backgroundCheckDao.tableIntegrity();
	}



	/**
	 * 检测是否有异常设备日志记录影响逻辑
	 *
	 * @return
	 */
	public String testDeviceLogs()
	{
		return backgroundCheckDao.testDeviceLogs();
	}



	/**
	 * 检测是否能够连上TDM进行远程命令下发以及延时
	 *
	 * @return
	 */
	public JSONObject checkConnectionTDM()
	{
		JSONObject result = new JSONObject();
		JSONObject object = new JSONObject();
		object.put("fd1", 31);
		object.put("fd2", 0);
		object.put("type", 10);
		object.put("sn", "172150210009999");
		object.put("num", "1,1");
		try
		{
			long t1 = (new Date()).getTime();
			SocketClient.send(object.toString(), "218.17.107.11", "10160");
			long t2 = (new Date()).getTime();
			result.put("success", "连接成功");
			result.put("data", t2 - t1);
		}
		catch (IOException e)
		{
			result.put("success", "连接失败");
			e.printStackTrace();
		}
		return result;
	}



	/**
	 * 查看8070端口是否打开
	 *
	 * @return
	 * @throws IOException
	 */
	public String checkPort()
	{
		String hostname = "120.24.221.252";
		String username = "cs1";
		String password = "easy2go@cs1";
		int port=22;
		String commString="netstat -ntpl";
		String commre=SSHClient.LinuxComm(hostname, port, username, password, commString);
		boolean b1=false;
		boolean b2=false;
		if(commre.contains(":80")){
			b1=true;
		}
		if(commre.contains(":8070")){
			b2=true;
		}
		return b1+"|"+b2;
	}



	/**
	 * 获取CPU使用情况
	 *
	 * @return
	 * @throws IOException
	 */
	public String checkCPU()
	{
		String hostname = "120.24.221.252";
		String username = "cs1";
		String password = "easy2go@cs1";
		int port=22;
		String commString="top -i -b -n 1";
		String commre=SSHClient.LinuxComm(hostname, port, username, password, commString);
		double cpu=0.0;
		double javacpu=0.0;
		//解析
		String [] arr = commre.split("@");
		if(arr.length<=7){
			String []s1=arr[2].split(" ");
			cpu= Double.parseDouble(s1[2].trim().replaceAll("us,","").trim());
		}else{
			for(int i=7;i<arr.length;i++){
				String []cpuDomearr=arr[i].split(" ");
				String []cputemp=new String[12];
				int temp=0;
				for(int j=0;j<cpuDomearr.length;j++){
					if(StringUtils.isNotBlank(cpuDomearr[j])){
						cputemp[temp]=cpuDomearr[j];
						temp++;
					}

				}
				String cpuDome=cputemp[8];
				cpu+=Double.parseDouble(cpuDome);
				if(arr[i].contains("java")){
					javacpu=Double.parseDouble(cpuDome);
				}
			}
			System.out.println("没读到东西了");
			return "192.168.0.110" + "|" + cpu + "|" + javacpu;
		}

		return "192.168.0.110|"+cpu+"|"+javacpu;
	}

	//-----------------业务通讯接口部分--------------------------




	/**
	 * 获取CPU情况
	 *
	 * @return
	 */
	public String checkCPUForTX()
	{
		String hostname = "test.easy2go.cn";
		String username = "cs1";
		String password = "ylcy.cn@appcs";
		int port=12198;
		String commString="top -i -b -n 1";
		String commre=SSHClient.LinuxComm(hostname, port, username, password, commString);
		double cpu=0.0;
		double javacpu=0.0;
		//解析
		String [] arr = commre.split("@");
		if(arr.length<=7){
			String []s1=arr[2].split(" ");
			cpu= Double.parseDouble(s1[2].trim().replaceAll("us,","").trim());
		}else{
			for(int i=7;i<arr.length;i++){
				String []cpuDomearr=arr[i].split(" ");

				String []cputemp=new String[12];
				int temp=0;
				for(int j=0;j<cpuDomearr.length;j++){
					if(StringUtils.isNotBlank(cpuDomearr[j])){
						cputemp[temp]=cpuDomearr[j];
						temp++;
					}

				}
				String cpuDome=cputemp[8];
				cpu+=Double.parseDouble(cpuDome);
				if(arr[i].contains("java")){
					javacpu=Double.parseDouble(cpuDome);
				}
			}
		}

		return "192.168.0.110|" + cpu + "|" + javacpu;
	}

	public static void main(String[] args)
	{
		BackgroundCheckSer b = new BackgroundCheckSer();
		System.out.println(b.checkCPUForTX());
		System.out.println(b.checkGWnormal());
	}


	/**
	 * 8090端口是否正常
	 *
	 * @return
	 */
	public String checkPortForTX()
	{
		String hostname = "test.easy2go.cn";
		String username = "cs1";
		String password = "ylcy.cn@appcs";
		int port = 12198;
		String commString = "netstat -ntpl";
		String commre = SSHClient.LinuxComm(hostname, port, username, password, commString);
		boolean b1 = false;
		boolean b2 = false;
		if (commre.contains(":8080"))
		{
			b1 = true;
		}
		if (commre.contains(":8090"))
		{
			b2 = true;
		}
		return b1 + "|" + b2;
	}



	/**
	 * 检测socket线程数
	 *
	 * @return
	 */
	public String checkSocketNumForTX()
	{
		String hostname = "test.easy2go.cn";
		String username = "cs1";
		String password = "ylcy.cn@appcs";
		int port = 12198;
		String commString = "netstat -n | grep tcp | grep 8090  | wc -l";
		String commre = SSHClient.LinuxComm(hostname, port, username, password, commString);
		return commre;
	}


	/**
	 * 检测是否有正常设备日志运行。如果5分钟都没有新设备日志。则是异常。返回分钟数，如果返回0表示正常,返回-1表示表中无数据。
	 * @return
	 */
	public String checkLastDevicelogsForTX(){
		String timeString="";
		try {
			timeString= deviceLogsDao.getLast();
		} catch (Exception e) {
			// TODO: handle exception
		}
		if("".equals(timeString)){
			return "-1";
		}
		Date date=DateUtils.parseDate(timeString);
		long t1=date.getTime();
		long t2=(new Date()).getTime();

		if(t2-t1>1000*60*5){
			long t3=(t2-t1)/(1000*60);
			return t3+"";
		}else{
			return "0";
		}

	}





	//--------TDM/SPM部分-----------------------
	/**
	 * 获取各服务器CPU
	 * @return
	 */
	public String getCPUForTS(){
		String tString="";
	   try {
		 tString=SocketLongClient.send("GET");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return tString;
	}

	/**
	 * 重启对应的程序
	 * @param IP
	 * @param pname
	 * @return
	 */
	public String RebootForTS(String IP,String pname)
	{
		String s = "";
		try
		{
			s = SocketLongClient.send("PUT REBOOT "+IP+" "+pname);
			System.out.println(s);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 关闭对应的程序
	 * @param IP
	 * @param pname
	 * @return
	 */
	public String ShutDownForTS(String IP,String pname){
		String s="";
		try
		{
			s=SocketLongClient.send("PUT SHUTDOWN "+IP+" "+pname);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return s;
	}



	/**
	 * 检测官网是否能正常 访问
	 *
	 * @returny
	 * 成功： {"isSuccess":true,"speed":17}
	 *
	 * 失败： {"isSuccess":false,"speed":null}
	 */
	public  String checkGWnormal()
	{
		JSONObject object = new JSONObject();
		try
		{
			Long temp1 = new Date().getTime();
//			URL aURL = new URL("http://www.easy2go.cn");
//			URLConnection c = aURL.openConnection();
			RemoteUtil.request("http://www.easy2go.cn", "GET", "application/json;charset=utf-8", null);

			Long temp2 = new Date().getTime();
			object.put("isSuccess", true);
			object.put("speed", temp2 - temp1);
		}
		catch (Exception e)
		{
			object.put("isSuccess", false);
			object.put("speed", null);
		}
		return object.toString();
	}



	/**
	 * 检测APP接口是否正常访问
	 * @param request
	 * @return true正常访问，  false不能正常访问
	 */
	public boolean checkAPPinterface(HttpServletRequest request)
	{


		String url = "http://www.easy2go.cn/api/v3/customers/login";
		ClientResource client = new ClientResource(url);
		try {
			Form form = new Form();
			form.add("phone", "13266583211");
			form.add("password","");
			String apiresult = client.post(form.getWebRepresentation()).getText();
			System.out.println(apiresult);
			JSONObject object = JSONObject.fromObject(apiresult);
			if ("false".equals(object.getString("isSuccess")) || "true".equals(object.getString("isSuccess")))
			{
				return true;
			}
			else
			{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 硬盘剩余空间
	 *
	 * @return
	 * @throws IOException
	 */
	public String getHardDiskSpace()
	{
		String hostname = "120.24.221.252";
		String username = "cs1";
		String password = "easy2go@cs1";
		int port=22;
		String commString="df -hl";
		String commre=SSHClient.LinuxComm(hostname, port, username, password, commString);
		String [] arr = commre.split("@");
		String[] arr2 = arr[1].split(" ");
		String[] arr3 = new String[6];
		int temp = 0;
		for (int i = 0; i < arr2.length; i++)
		{
			if(StringUtils.isNotBlank(arr2[i]))
			{
				arr3[temp] = arr2[i];
				temp++;
			}
		}
		JSONObject jo = new JSONObject();
		String str = arr3[3].replaceAll("[^.^0-9]", "");

		if(Double.parseDouble(str)<2)
		{
			jo.put("isSuccess", false);
			jo.put("data", arr3[3]);
		}
		else
		{
			jo.put("isSuccess", true);
			jo.put("data", arr3[3]);
		}

		return jo.toString();
	}

}
