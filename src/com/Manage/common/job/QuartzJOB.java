package com.Manage.common.job;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.Manage.common.cache.CacheUtils;
import com.Manage.common.constants.Constants;
import com.Manage.common.latlng.LocTest;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.SMSUltisNews;
import com.Manage.common.util.StringUtils;
import com.Manage.dao.DeviceLogsDao;
import com.Manage.dao.GStrenthDataDao;
import com.Manage.dao.SIMInfoDao;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.DeviceFlow;
import com.Manage.entity.DeviceLogs;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.GStrenthData;
import com.Manage.entity.LocTemp;
import com.Manage.entity.PrepareCardTemp;
import com.Manage.entity.SIMInfo;
import com.Manage.entity.SIMRecord;
import com.Manage.entity.SIMcostStatistics;
import com.Manage.entity.VPNWarning;
import com.Manage.entity.common.ApplicationContext2;
import com.Manage.service.BackgroundCheckSer;
import com.Manage.service.CountryInfoSer;
import com.Manage.service.DeviceFlowSer;
import com.Manage.service.DeviceLogsSer;
import com.Manage.service.FlowDealOrdersSer;
import com.Manage.service.OrdersInfoSer;
import com.Manage.service.PrepareCardTempSer;
import com.Manage.service.SIMInfoSer;
import com.Manage.service.VpnInfoSer;
import com.Manage.service.SocketClient.CommandSer;

/**
 * * @author wangbo: * @date 创建时间：2015-10-20 下午1:44:10 * @version 1.0 * @parameter
 * * @since * @return
 */
public class QuartzJOB
{
	private Logger logger = LogUtil.getInstance(QuartzJOB.class);
	@Autowired
	private PrepareCardTempSer prepareCardTempSer;
	@Autowired
	private CountryInfoSer countryInfoSer;
	@Autowired
	private FlowDealOrdersSer flowDealOrdersSer;
	@Autowired
	private DeviceFlowSer deviceFlowSer;
	@Autowired
	private DeviceLogsSer deviceLogsSer;
	@Autowired
	private SIMInfoSer simInfoSer;

	/**
	 * 定时任务开启和关闭SIM卡经纬度转换
	 */
	public void SIMManageJOB() 
	{

		SIMInfoDao simInfoDao = ApplicationContext2.getBean(SIMInfoDao.class);
		CommandSer commandSer = ApplicationContext2.getBean(CommandSer.class);
		GStrenthDataDao gStrenthDataDao = ApplicationContext2.getBean(GStrenthDataDao.class);
		DeviceLogsDao deviceLogsDao = ApplicationContext2.getBean(DeviceLogsDao.class);
		if (Constants.TIMING_SIMMANAGEJOB)
		{
			logger.info("----------SIM定时任务开始----------");
			// 定时开启的SIM卡
			List<SIMInfo> simList1 = simInfoDao.selectTimeOpenCard();
			if (simList1 != null && simList1.size() > 0)
			{
				logger.info("----------开始处理需要打开的卡----------");
				String imsiString = "";
				for (SIMInfo s : simList1)
				{
					imsiString += "," + s.getIMSI();
				}
				imsiString = imsiString.substring(1, imsiString.length());
				imsiString = "'" + imsiString.replaceAll(",", "','") + "'";
				logger.info("需要开启的IMSI:" + imsiString);
				SIMInfo si1 = new SIMInfo();
				si1.setIMSI(imsiString);
				int temp = simInfoDao.updateTimeOpenCard(si1);
				if (temp > 0)
				{
					logger.info("打开成功,开启的数量:" + temp);
				}
				else
				{
					logger.info("------打开失败------");
				}
				logger.info("----------处理需要打开的卡完毕----------");
			}
			// 定时关闭SIM卡
			List<SIMInfo> simList2 = simInfoDao.selectTimeShutdownCard();
			if (simList2 != null && simList2.size() > 0)
			{
				logger.info("----------开始处理需要关闭的卡----------");
				String imsiString = "";
				for (SIMInfo s : simList2)
				{
					imsiString += "," + s.getIMSI();
					// 使用中的卡发换卡命令。
					if ("使用中".equals(s.getCardStatus()))
					{
						commandSer.changeCard(s.getLastDeviceSN());
						logger.info("发送换卡命令的IMSI:" + s.getIMSI());
					}
				}
				imsiString = imsiString.substring(1, imsiString.length());
				imsiString = "'" + imsiString.replaceAll(",", "','") + "'";
				logger.info("需要关闭的IMSI:" + imsiString);
				SIMInfo si2 = new SIMInfo();
				si2.setIMSI(imsiString);
				int temp = simInfoDao.updateTimeShutdownCard(si2);
				if (temp > 0)
				{
					logger.info("关闭成功,关闭的数量:" + temp);
				}
				else
				{
					logger.info("------关闭失败------");
				}
				logger.info("----------处理需要关闭的卡完毕----------");
			}
			logger.info("----------SIM定时任务结束----------");
		}
		if (Constants.TIMING_LATLONJOB)
		{
			logger.info("----------基站转经纬度开始----------");
			List<GStrenthData> list = gStrenthDataDao.getTop10();
			if (list.size() > 0)
			{
				for (GStrenthData g : list)
				{
					JSONObject object = LocTest.jizhantojw(g.getJizhan(), "50");
					if ("OK".equals(object.getString("cause")))
					{
						g.setLat(object.getString("lat"));
						g.setLon(object.getString("lon"));
					}
				}
				// 批量更新到数据库
				int temp = gStrenthDataDao.update(list);
				logger.info("更新数据库返回:" + temp);
			}
			else
			{
				logger.info("----------没有要转换的基站----------");
			}
			logger.info("----------基站转经纬度结束----------");
		}

		// 自动加入基站组。
		if (Constants.TIMING_ADDJZTEMP)
		{
			logger.info("----------自动收集基站开始----------");
			DeviceLogs dlDeviceLogs = new DeviceLogs();
			dlDeviceLogs.setSN(Constants.getConfig("POS.sn").split(",")[1]);
			dlDeviceLogs.setTableName(Constants.DEVTABLEROOT_STRING + DateUtils.getDate("yyyyMMdd"));
			dlDeviceLogs.setLastTime(DateUtils.formatDate(new Date((new Date()).getTime() - 5 * 60 * 1000), "yyyy-MM-dd HH:mm:ss"));
			dlDeviceLogs.setStatus(DateUtils.getDateTime());
			List<DeviceLogs> dlist = deviceLogsDao.getjzlist(dlDeviceLogs);
			if (dlist != null && dlist.size() > 0)
			{
				List<LocTemp> listtemp = new ArrayList<LocTemp>();
				for (DeviceLogs d : dlist)
				{
					String[] arr1 = d.getJizhan().split(",");
					String[] arr2 = arr1[0].split("\\.");
					String[] arr3 = arr1[1].split("\\.");
					LocTemp LT = new LocTemp();
					LT.setJW(Constants.TIMING_ADDJZTEMP_JW);
					LT.setLocalJZ(arr2[0] + "." + arr2[1] + "." + arr2[2] + "." + arr2[3]);
					LT.setRoamJZ(arr3[0] + "." + arr3[1] + "." + arr3[2] + "." + arr3[3]);
					LT.setLocalXH(arr2[4]);
					LT.setRoamXH(arr3[4]);
					listtemp.add(LT);
				}
				// 批量插入基站组数据.
				int temp = deviceLogsDao.batchAddJZTemp(listtemp);
				logger.info("----------影响行数:" + temp);
			}
		}

	}



	/**
	 * 同步有赞商城订单 job
	 *
	 */
	public void YouzanSyncOrderJOB()
	{
		OrdersInfoSer ordersInfoSer = ApplicationContext2.getBean(OrdersInfoSer.class);

		AdminUserInfo adminUserInfo = new AdminUserInfo();
		adminUserInfo.setUserID(Constants.QuartzJOB_USER_ID);
		adminUserInfo.setUserName(Constants.QuartzJOB_USER_NAME);

		ordersInfoSer.syncYouzanOrder(adminUserInfo);
	}

	/**
	 * VPN预警定时任务
	 */
	public void VPNPing(){
//		System.out.println(".......开始定时ping.......");
		VpnInfoSer vpnInfoSer = ApplicationContext2.getBean(VpnInfoSer.class);
		//查询所有需要Ping的ip
		List<VPNWarning> vpnWarnings=vpnInfoSer.getVPNWarning();
		if(vpnWarnings!=null && vpnWarnings.size()>0){
			for(VPNWarning vw:vpnWarnings){
				if(vw.getIP()!=null){
					if(ping(vw.getIP())){
						if("异常".equals(vw.getWarningStatus()) || vw.getWarningStatus()==null){
							//更新状态
							VPNWarning vpnWarning=new VPNWarning();
							vpnWarning.setWarningStatus("正常");
							vpnWarning.setIP(vw.getIP());
							int temp=vpnInfoSer.updateVPNStatus(vpnWarning);
							if(temp>0){
//								System.out.println("IP:"+vw.getIP()+" 的状态更新为正常");
							}
						}
					}else{
						if("正常".equals(vw.getWarningStatus()) || vw.getWarningStatus()==null){
							//更新状态
							VPNWarning vpnWarning=new VPNWarning();
							vpnWarning.setWarningStatus("异常");
							vpnWarning.setIP(vw.getIP());
							int temp=vpnInfoSer.updateVPNStatus(vpnWarning);
							if(temp>0){
//								System.out.println("IP:"+vw.getIP()+" 的状态更新为异常");
							}
							if(vw.getIfMsgAlter()){
								//发送短信
								String phoneString=Constants.getConfig("VPNWARN_Phone");
								SMSUltisNews smsNews = new SMSUltisNews();
								boolean success = smsNews.sendTemplateSMSCloopen(phoneString,"25958","","","","",vw.getIP());//vpn预警
								System.out.println("VPN预警短信发送："+success);
//								SMSUltis.sendTemplateSmsVPNWARN(phoneString,vw.getIP());
							}

						}
					}
				}
			}
		}else{
//			System.out.println("没有需要ping的IP");
		}
	}


	/**
	 * ping IP
	 * @param ip
	 * @return
	 */
	public boolean ping(String ip){
		Runtime runtime = Runtime.getRuntime(); // 获取当前程序的运行进对象
		  Process process = null; // 声明处理类对象
		  String line = null; // 返回行信息
		  InputStream is = null; // 输入流
		  InputStreamReader isr = null; // 字节流
		  BufferedReader br = null;
		  boolean res = false;// 结果
		  try {
		   process = runtime.exec("ping -c 3 " + ip); // PING
		   is = process.getInputStream(); // 实例化输入流
		   isr = new InputStreamReader(is);// 把输入流转换成字节流
		   br = new BufferedReader(isr);// 从字节中读取文本
		   while ((line = br.readLine()) != null) {
		    if (line.contains("icmp_seq")) {
		     res = true;
		     break;
		    }
		   }
		   is.close();
		   isr.close();
		   br.close();
		   if (res) {
//		    System.out.println("IP:"+ip+" ping 通  ...");
		   } else {
//		    System.out.println("IP:"+ip+" ping 不通...");
		   }
		  } catch (IOException e) {
//		   System.out.println(e);
			  e.printStackTrace();
		   runtime.exit(1);
		  }
		  return res;
	}

	/**
	 * 后台预警定时任务
	 */
	public void warn()
	{
//		System.out.println("开始后台预警定时任务......");
		BackgroundCheckSer backgroundCheckSer = ApplicationContext2.getBean(BackgroundCheckSer.class);

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		boolean flag=true;
		String exceptionStr = "检测模块:所有\r\n检测时间:"+sdf.format(d)+"\r\n检测方式:自动\r\n异常项:";
		int n = 0;

		//检测数据库连接是否正常以及延时
		JSONObject sj1 = JSONObject.fromObject(backgroundCheckSer.connectionDelayed());
		if(sj1==null || !sj1.optString("success").equals("连接正常"))
		{
			n+=1;
			exceptionStr += n+".数据库模块:数据库连接失败  ";
			flag=false;
		}

		//查询当前数据库连接数
		JSONObject sj2 = JSONObject.fromObject(backgroundCheckSer.connectionNum());
		if(sj2==null || sj2.optInt("data")>100)
		{
			n+=1;
			exceptionStr += n+".数据库模块:当前连接数超出预设范围  ";
			flag=false;
		}

		//数据库定时任务是否打开正常运行
		JSONObject sj3 = JSONObject.fromObject(backgroundCheckSer.timingTaskOpen());
		if(sj3==null || !sj3.optString("success").equals("是"))
		{
			n+=1;
			exceptionStr += n+".数据库模块:数据库定时任务未打开  ";
			flag=false;
		}

		//查询是否有锁表超时现象
		JSONObject sj4 = JSONObject.fromObject(backgroundCheckSer.lockTable());
		if(sj4==null || sj4.optString("success").equals("是"))
		{
			n+=1;
			exceptionStr += n+".数据库模块:存在锁表超时现象,线程ID:"+sj4.optString("data")+"  ";
			flag=false;
		}

		//数据同步（备份）是否正常运行
		JSONObject sj5 = JSONObject.fromObject(backgroundCheckSer.dataSynchronization());
		if(sj5==null || !sj5.optString("success").equals("是"))
		{
			n+=1;
			exceptionStr += n+".数据库模块:数据同步(备份)异常  ";
			flag=false;
		}

		//表结构是否完整，设备日志表是否正常
		JSONObject sj6 = JSONObject.fromObject(backgroundCheckSer.tableIntegrity());
		if(sj6==null || !sj6.optString("success").equals("是"))
		{
			n+=1;
			exceptionStr += n+".数据库模块:设备日志表异常  ";
			flag=false;
		}

		//检测后台服务器是否正常访问，访问速度是否正常
		JSONObject ht1 = JSONObject.fromObject(httpGet2());
		if(ht1==null || !ht1.optString("success").equals("是"))
		{
			n+=1;
			exceptionStr += n+".后台模块:后台服务器访问异常  ";
			flag=false;
		}

		//CPU，内存使用情况
		String cpuInfo = backgroundCheckSer.checkCPU();
		if(cpuInfo != null)
		{
			String[] cpu = cpuInfo.split("\\|");
			if(Double.parseDouble(cpu[2])>80)
			{
				n+=1;
                exceptionStr += n+".后台模块:JAVA占用CPU内存过高,JAVA CPU使用率:"+cpu[2]+"%  ";
                flag=false;
			}
		}
		else
		{
			n+=1;
            exceptionStr += n+".后台模块:CPU使用情况异常  ";
            flag=false;
		}

		//检测设备升级文件目录和升级文件是否正常
		JSONObject ht3 = JSONObject.fromObject(httpGet());
		if(ht3==null || ht3.optString("data").equals("文件目录不存在"))
		{
			n+=1;
            exceptionStr += n+".后台模块:文件目录不存在  ";
            flag=false;
		}

		//检测远程服务功能是否正常，获取日志和远程升级端口是否正常
		String ht4 = backgroundCheckSer.checkPort();
		String[] ht4s = ht4.split("\\|");

		if(ht4s[0].equals("false")&&ht4s[1].equals("true"))
		{
			n+=1;
			exceptionStr += n+".后台模块:80端口异常  ";
			flag=false;
		}
		else if(ht4s[0].equals("true")&&ht4s[1].equals("false"))
		{
			n+=1;
			exceptionStr += n+".后台模块:8070端口异常  ";
			flag=false;
		}
		else if(ht4s[0].equals("false")&&ht4s[1].equals("false"))
		{
			n+=1;
			exceptionStr += n+".后台模块:80、8070端口异常  ";
			flag=false;
		}
		if(StringUtils.isBlank(ht4))
		{
			n+=1;
            exceptionStr += n+".后台模块:远程服务功能异常  ";
            flag=false;
		}

		//检测是否能够连上TDM进行远程命令下发以及延时
		JSONObject ht5 = backgroundCheckSer.checkConnectionTDM();
		if(ht5==null || !ht5.optString("success").equals("连接成功"))
		{
			n+=1;
            exceptionStr += n+".后台模块:连接TDM失败  ";
            flag=false;
		}

		//检测是否有异常设备日志记录影响逻辑
		JSONObject ht6 = JSONObject.fromObject(backgroundCheckSer.testDeviceLogs());
		if(ht6==null || !ht6.optString("success").equals("正常"))
		{
			n+=1;
			exceptionStr += n+".后台模块:存在异常设备日志记录，异常记录数:"+ht6.optString("data")+"  ";
			flag=false;
		}

		JSONObject ht7 = JSONObject.fromObject(backgroundCheckSer.getHardDiskSpace());
		if(ht7==null || !ht7.optBoolean("isSuccess"))
		{
			n+=1;
			exceptionStr += n+".后台模块:硬盘剩余空间:"+ht7.optString("data")+"  ";
			flag=false;
		}

		//服务器CPU情况，JAVA所占CPU,以及CPU过高的进程
		String tx1 = backgroundCheckSer.checkCPUForTX();
		if(tx1 != null)
		{
			String[] cpuForTX = tx1.split("\\|");
			if(Double.parseDouble(cpuForTX[2])>80)
			{
				n+=1;
                exceptionStr += n+".通讯模块:JAVA占用CPU内存过高,JAVA CPU使用率:"+cpuForTX[2]+"%  ";
                flag=false;
			}
		}
		else
		{
			n+=1;
            exceptionStr += n+".通讯模块:CPU使用情况异常  ";
            flag=false;
		}

		//8090端口是否正常
		String tx2 = backgroundCheckSer.checkPortForTX();
		String[] result = tx2.split("\\|");

		if(result[0].equals("false")&&result[1].equals("true"))
		{
			n+=1;
			exceptionStr += n+".通讯模块:8080端口异常  ";
			flag=false;
		}
		else if(result[0].equals("true")&&result[1].equals("false"))
		{
			n+=1;
			exceptionStr += n+".通讯模块:8090端口异常  ";
			flag=false;
		}
		else if(result[0].equals("false")&&result[1].equals("false"))
		{
			n+=1;
			exceptionStr += n+".通讯模块:8080、8090端口异常  ";
			flag=false;
		}

		//检测socket线程数
		String tx3 = backgroundCheckSer.checkSocketNumForTX();
		if(Integer.parseInt(tx3)>500)
		{
			n+=1;
			exceptionStr += n+".通讯模块:线程数过多,线程数:"+tx3+"  ";
			flag=false;
		}

		//检测是否有正常设备日志运行
		String tx4 = backgroundCheckSer.checkLastDevicelogsForTX();
		if(tx4.equals("-1"))
		{
			n+=1;
			exceptionStr += n+".通讯模块:表中无数据  ";
			flag=false;
		}
		else if(tx4.equals("0")){}
		else
		{
			n+=1;
			exceptionStr += n+".通讯模块:异常,"+tx4+"分钟都没有新设备日志  ";
			flag=false;
		}

		//是否有黏包现象
		JSONObject tx5 = JSONObject.fromObject(httpGet3());
		if(tx5==null || !tx5.getBoolean("isSuccess"))
		{
			//n+=1;
			//exceptionStr += n+".通讯模块:上次黏包时间:"+tx5.getJSONObject("data").optString("WARN")+"  ";
			//flag=false;
		}

		// 检测官网是否能正常访问
		JSONObject gw1 = JSONObject.fromObject(backgroundCheckSer.checkGWnormal());
		if(gw1.getBoolean("isSuccess")==false)
		{
			n+=1;
			exceptionStr += n+".官网模块:不能正常访问官网  ";
			flag=false;
		}
		else if(Integer.parseInt(gw1.getString("speed"))>30000)
		{
			n+=1;
            exceptionStr += n+".官网模块:访问官网速度太慢,访问速度:"+gw1.getString("speed")+"毫秒  ";
			flag=false;
		}

		//检测关键文件目录和文件是否正常（APP版本文件）
		JSONObject gw2 = JSONObject.fromObject(httpGet4());
		if(gw2.optString("code").equals("00"))
		{
			n+=1;
			exceptionStr += n+".官网模块:没有找到对应的文件夹  ";
			flag=false;
		}
		else if(gw2.optString("code").equals("01"))
		{
			n+=1;
			exceptionStr += n+".官网模块:文件夹下的文件:"+gw2.optString("msg")+"  ";
			flag=false;
		}

		//检测APP接口是否正常访问
		JSONObject gw3 = JSONObject.fromObject(httpGet5());
		if(!gw3.getBoolean("isSuccess"))
		{
			n+=1;
			exceptionStr += n+".官网模块:不能正常访问APP接口  ";
			flag=false;
		}

		//写入文件
		if(!exceptionStr.equals(""))
		{
			try
			{
			   File f = new File(Constants.BACKGROUND_WARN_FILE_URL);
			   if(!f.exists())
			   {
			   	   f.createNewFile();//不存在则创建
			   }

			   BufferedWriter output = new BufferedWriter(new FileWriter(f,true));
			   output.write(exceptionStr+"\r\n"+"-----------------------------------------------------------------------------"+"\r\n");
			   output.close();
			}
			catch (Exception e)
			{
			   e.printStackTrace();
			}
		}

		//发送短信
		if(!flag){
			//发送短信
			String phoneString=Constants.getConfig("SYSWARN_Phone");

			SMSUltisNews smsNews = new SMSUltisNews();
			boolean success = smsNews.sendTemplateSMSCloopen(phoneString,"25958","","","","",exceptionStr);//系统预警
//			System.out.println("系统预警短信发送："+success);

//			SMSUltis.sendTemplateSmsSysWarn(phoneString,exceptionStr);
		}

	}

	/**
	 * 模拟请求,检查设备升级文件目录和升级文件是否正常
	 * @return
	 */
	public String httpGet()
	{
		CloseableHttpClient httpclient = HttpClients.createDefault();

		try
		{
			HttpGet httpget = new HttpGet(Constants.getConfig("WARN_AppLoginUrl")+"/backgroundcheck/testUpgradeFileDirectory?start=0");

			CloseableHttpResponse response = httpclient.execute(httpget);

			HttpEntity entity = response.getEntity();

			if (entity != null)
			{
                //返回响应内容
                return EntityUtils.toString(entity).trim();
            }
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 模拟请求,检测后台服务器是否正常访问，访问速度是否正常
	 * @return
	 */
	public String httpGet2()
	{
		CloseableHttpClient httpclient = HttpClients.createDefault();

		try
		{
			HttpGet httpget = new HttpGet(Constants.getConfig("WARN_AppLoginUrl")+"/backgroundcheck/testLogin?start=0");

			CloseableHttpResponse response = httpclient.execute(httpget);

			HttpEntity entity = response.getEntity();

			if (entity != null)
			{
                //返回响应内容
                return EntityUtils.toString(entity).trim();
            }
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 模拟请求,是否有黏包现象
	 * @return
	 */
	public String httpGet3()
	{
		CloseableHttpClient httpclient = HttpClients.createDefault();

		try
		{
			HttpGet httpget = new HttpGet(Constants.getConfig("WARN_AppLoginUrl")+"/backgroundcheck/testIfNB?start=0");

			CloseableHttpResponse response = httpclient.execute(httpget);

			HttpEntity entity = response.getEntity();

			if (entity != null)
			{
                //返回响应内容
                return EntityUtils.toString(entity).trim();
            }
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 模拟请求,检测关键文件目录和文件是否正常（APP版本文件）
	 * @return
	 */
	public String httpGet4()
	{
		CloseableHttpClient httpclient = HttpClients.createDefault();

		try
		{
			HttpGet httpget = new HttpGet(Constants.getConfig("WARN_AppLoginUrl")+"/backgroundcheck/testAppFile?start=0");

			CloseableHttpResponse response = httpclient.execute(httpget);

			HttpEntity entity = response.getEntity();

			if (entity != null)
			{
                //返回响应内容
                return EntityUtils.toString(entity).trim();
            }
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 模拟请求,检测APP接口是否正常访问
	 * @return
	 */
	public String httpGet5()
	{
		CloseableHttpClient httpclient = HttpClients.createDefault();

		try
		{
			HttpGet httpget = new HttpGet(Constants.getConfig("WARN_AppLoginUrl")+"/backgroundcheck/testAPPinterface?start=0");

			CloseableHttpResponse response = httpclient.execute(httpget);

			HttpEntity entity = response.getEntity();

			if (entity != null)
			{
                //返回响应内容
                return EntityUtils.toString(entity).trim();
            }
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return "";
	}

	//tdm/spm预警
	public void TDMWarn()
	{
		//logger.debug("本地服务器监控定时任务start");

		BackgroundCheckSer backgroundCheckSer = ApplicationContext2.getBean(BackgroundCheckSer.class);

		String cpuInfo = backgroundCheckSer.getCPUForTS();

		String[] cpus = cpuInfo.split("\\|");

		String excStr = "";
		for (int i = 0; i < cpus.length; i++)
		{
			String[] data = cpus[i].split(",");
			for (int j = 1; j < data.length; j++)
			{
				String[] str = data[j].split(":");
				String[] str2 = str[1].split("-");
				if(Double.parseDouble(str2[0])>80)
				{
					if(excStr.equals(""))
					{
						excStr+="IP为"+data[0]+"的"+str[0]+"程序CPU使用率超过80%";
					}
					else
					{
						excStr+=",IP为"+data[0]+"的"+str[0]+"程序CPU使用率超过80%";
					}
				}
			}
		}

		if(StringUtils.isNotBlank(CacheUtils.get("TDMWarnCache")))
		{
			long TDMWarnCache = new Date().getTime()-(long)CacheUtils.get("TDMWarnCache");

			if(TDMWarnCache>600000)
			{
				//发送短信
				if(!excStr.equals("")){
					//发送短信
					String phoneString=Constants.getConfig("SYSWARN_Phone");
//					SMSUltis.sendTemplateSmsSysWarn(phoneString,excStr);

					SMSUltisNews smsNews = new SMSUltisNews();
					boolean success = smsNews.sendTemplateSMSCloopen(phoneString,"25958","","","","",excStr);//系统预警
//					System.out.println("系统预警短信发送："+success);

					CacheUtils.put("TDMWarnCache", new Date().getTime());
				}

			}
		}
		else
		{
			//发送短信
			if(!excStr.equals("")){
				//发送短信
				String phoneString=Constants.getConfig("SYSWARN_Phone");
//				SMSUltis.sendTemplateSmsSysWarn(phoneString,excStr);

				SMSUltisNews smsNews = new SMSUltisNews();
				boolean success = smsNews.sendTemplateSMSCloopen(phoneString,"25958","","","","",excStr);//系统预警
//				System.out.println("系统预警短信发送："+success);

				//SMSUltis.sendTemplateSmsSysWarn("18825218914",excStr);

				CacheUtils.put("TDMWarnCache", new Date().getTime());
			}
		}

		//logger.debug("本地服务器监控定时任务end");
	}
	
	public void PrepareCardJob(){
//		System.out.println("=========================================jiang================================================");
		String curDate = DateUtils.getDate("yyyy-MM-dd");
		// 查询所有国家
		List<CountryInfo> countryInfos = countryInfoSer.getAll(null);
		
		for (CountryInfo countryInfo : countryInfos)
		{
			String countryName = countryInfo.getCountryName();
			String countryCode = String.valueOf(countryInfo.getCountryCode());
			
			
			
			String ID = UUID.randomUUID().toString();
			String time = DateUtils.getDate("yyyy-MM-dd HH:mm:ss"); //这个要改吗？如果在获取时超过12点，那么就不对了，要保证在23：59：59前获取

			// 查询订单总数
			FlowDealOrders flowDealOrders = new FlowDealOrders();
			flowDealOrders.setPanlUserDate(curDate);  // curDate = 2016-07-01
			flowDealOrders.setUserCountry(countryName);
			List<FlowDealOrders> flowDealOrdList= flowDealOrdersSer.getflowdealbycountry(flowDealOrders);
			String orderTotalNum = String.valueOf(flowDealOrdList.size());
			
			// 查询sim卡总数
			SIMInfo simInfo = new SIMInfo();
			String datetime=curDate+" 00:00"; // datetime=2016-07-01 00:00
			simInfo.setDatetime(datetime);
			simInfo.setCountryCode(countryCode);
			List<SIMInfo> simInfos = flowDealOrdersSer.getSIMCardTotal(simInfo);
			String cardCount = String.valueOf( simInfos.get(0).getSIMCount());
		
			// 查询剩余流量
			String surplusFlow= simInfos.get(0).getSUMplanRemainData();
			
			// 查询已使用流量
			DeviceFlow deviceFlow = new DeviceFlow();
			deviceFlow.setBJTime(curDate);
			deviceFlow.setMCC(countryCode);
			List<DeviceFlow> resultDeviceLogs = deviceFlowSer.getDeviceInByBjtime(deviceFlow);
			
			Long flow = 0L;
			for (DeviceFlow item : resultDeviceLogs)
			{
				// 判断累计流量是否为空
				if ("0".equals(item.getFlowCount()))
				{
					DeviceLogs deviceLogs = new DeviceLogs();
					deviceLogs.setSN(item.getSN());
					deviceLogs = deviceLogsSer.getlastOne(deviceLogs);
				
					flow += Long.parseLong(deviceLogs.getDayUsedFlow());//kb
					
				}
				else
				{
					flow += Long.parseLong(item.getFlowCount());
				}
			}
			String userFlow=String.valueOf(flow);
			
			PrepareCardTemp prepareCardTemp = new PrepareCardTemp(ID, time, countryName, cardCount, orderTotalNum, surplusFlow, userFlow);
			
			if(prepareCardTempSer.insertInfo(prepareCardTemp)){
				logger.info("(prepareCardTemp表新增数据成功");
			}else{
				logger.info("(prepareCardTemp表新增数据失败");
			}
		}
	}
	
	
	public void simPirmeCost()
	{
		// 查询当天有效sim卡
		SIMInfo simInfo = new SIMInfo();
		simInfo.setModifyDate(DateUtils.getDate("yyyy-MM-dd"));
		List<SIMInfo> simInfos = simInfoSer.getDayUserAll(simInfo);

		double totalPrimeCost = 0; // sim卡总成本
		for (SIMInfo sInfo : simInfos)
		{
			double planPrice = sInfo.getPlanPrice(); // 1
			int planData = sInfo.getPlanData(); // 2
			int planDay = 0;
			String endDate = sInfo.getSIMEndDate();
			String activateDate = sInfo.getSIMActivateDate();
			if (StringUtils.isNotBlank(endDate) && StringUtils.isNotBlank(activateDate))
			{
				try
				{
					planDay = (int) DateUtils.getDateSUM(activateDate, endDate, "yyyy-MM-dd HH:mm:ss");
				}
				catch (ParseException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				planDay = 0;
			}

			double singlePrimeCost = 0; // 单个sim卡成本
			SIMRecord sRecord = null;
			if (planData != 0 && planDay != 0)
			{ // 按天 按流量
				double flowPrice = planPrice / planData * 1024; // 按流量单价 （M）
				double dayPirce = planPrice / planDay; // 按天单价
				// 算出用了多少的流量
				SIMRecord simRecord = new SIMRecord();
				simRecord.setIMSI(sInfo.getIMSI());
				simRecord.setCreatorDate(DateUtils.getDate("yyyy-MM-dd"));
				sRecord = simInfoSer.getSIMRecordByIMSI(simRecord);

				if (sRecord == null)
				{
					// 进来说明此卡当天没使用
					singlePrimeCost = planPrice / planDay;
				}
				else
				{
					int useFlow = sRecord.getUseFlow();
					if (dayPirce > useFlow / 1024 * flowPrice)
					{
						singlePrimeCost = dayPirce;
					}
					else
					{
						singlePrimeCost = useFlow / 1024 * flowPrice;
					}
				}
			}
			else if (planData == 1073741824 && planDay != 0)
			{ // 按天
				singlePrimeCost = planPrice / planDay;
			}
			else if (planDay == 0 && planData != 1073741824)
			{ // 按流量
				singlePrimeCost = planPrice / planData * 1024;
			}
			totalPrimeCost += singlePrimeCost;
			SIMcostStatistics siMcostStatistics = null;
			
			if (sRecord == null){
				siMcostStatistics = new SIMcostStatistics(sInfo.getIMSI(), "", "", sRecord.getCountry(), singlePrimeCost, 0,sInfo.getCountryList());
			}else{
				siMcostStatistics = new SIMcostStatistics(sInfo.getIMSI(), DateUtils.getDate("yyyy-MM-dd"),sRecord.getAssignedSN(),sRecord.getCountry(),singlePrimeCost, sRecord.getUseFlow(),sInfo.getCountryList());
			}
			
			simInfoSer.insertSIMcostStatistics(siMcostStatistics);
		}
	}
}