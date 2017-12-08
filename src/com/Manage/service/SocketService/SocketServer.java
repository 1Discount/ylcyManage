package com.Manage.service.SocketService;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.Manage.common.cache.CacheUtils;
import com.Manage.common.constants.ErrorCode;
import com.Manage.common.exception.SCException;
import com.Manage.common.util.FileWrite;
import com.Manage.entity.DevMessage;
import com.Manage.entity.EquipLogs;
import com.Manage.entity.FactoryTestInfo;
import com.Manage.entity.common.ApplicationContext2;
import com.Manage.service.DeviceInfoSer;
import com.Manage.service.DeviceLogsSer;
import com.Manage.service.EquipLogsSer;
import com.cloopen.rest.sdk.utils.LoggerUtil;

/**
 * * @author wangbo: * @date 创建时间：2015-6-17 上午9:26:43 * @version 1.0 * @parameter
 * * @since * @return
 */
public class SocketServer extends AbstractBusinessService
{

	Logger logger = Logger.getLogger(SocketServer.class);

	EquipLogsSer equipLogsSer = ApplicationContext2.getBean(EquipLogsSer.class);

	DeviceLogsSer deviceLogsSer = ApplicationContext2.getBean(DeviceLogsSer.class);

	DeviceInfoSer deviceInfoSer = ApplicationContext2.getBean(DeviceInfoSer.class);

	// 消息返回
	private String MsgResult = "";



	@Override
	public void initCommon()
	{
		// TODO Auto-generated method stub

	}



	@Override
	public void doValidate() throws SCException
	{
		// TODO Auto-generated method stub
		if (messageHead == null || "".equals(messageHead))
		{
			throw new SCException(ErrorCode.ERROR_VERIFYFAIL, ErrorCode.ERROR_VERIFYFAIL_MSG);
		}
		if (messageHead.getType() == null || messageHead.getSn() == null || messageHead.getNum() == null)
		{
			throw new SCException(ErrorCode.ERROR_VERIFYFAIL, ErrorCode.ERROR_VERIFYFAIL_MSG);
		}
	}



	@Override
	public void doBusiness() throws SCException
	{
		// TODO Auto-generated method stub
		if (messageHead.getContent() != null && Integer.parseInt(messageHead.getType()) < 3)
		{
			// 将日志信息插入到数据库
			FileWrite.printlog("开始将日志插入数据库");
			EquipLogs equipLogs = new EquipLogs();
			if ("1".equals(messageHead.getType()))
			{
				equipLogs.setType(1);
			}
			else if ("2".equals(messageHead.getType()))
			{
				equipLogs.setType(2);
			}
			equipLogs.setSN(messageHead.getSn());
			equipLogs.setNO(Integer.parseInt(messageHead.getNum()));
			equipLogs.setContent(messageHead.getContent());
			equipLogs.setID(UUID.randomUUID().toString());
			int le = 0;
			try
			{
				le = messageHead.getContent().getBytes("utf-8").length;
			}
			catch (UnsupportedEncodingException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			equipLogs.setLogSize(le / 1024);
			equipLogsSer.updatebyno(equipLogs);
			if (equipLogsSer.insert(equipLogs))
			{
				returnCode = ErrorCode.ERROR_SUCCESS + "";
				FileWrite.printlog("日志插入数据库成功!");
				CacheUtils.put("logsuccess", 1);
			}
			else
			{
				returnCode = ErrorCode.ERROR_DATAACCESSFAIL + "";
				CacheUtils.put("logsuccess", 0);
			}
		}
		else if (messageHead.getType() != null && Integer.parseInt(messageHead.getType()) >= 30 && Integer.parseInt(messageHead.getType()) < 50)
		{
			FileWrite.printlog("开始将短信插入数据库");
			DevMessage dm = new DevMessage();
			if ("30".equals(messageHead.getType()))
			{
				dm.setType("本地");
			}

			else if ("31".equals(messageHead.getType()))
			{
				dm.setType("漫游");
			}
			dm.setSN(messageHead.getSn());
			dm.setIMSI(messageHead.getNum());
			dm.setContent(messageHead.getContent());
			dm.setRemark("");
			try
			{
				int temp = deviceLogsSer.insertDevMessage(dm);
				if (temp > 0)
				{
					FileWrite.printlog("短信插入数据库成功");
				}
				else
				{
					FileWrite.printlog("短信插入数据库失败");
				}
			}
			catch (Exception e)
			{
				FileWrite.printlog(e.getMessage());
			}
		}
		else if (messageHead.getType() != null && Integer.parseInt(messageHead.getType()) >= 50)
		{
			try
			{

				FileWrite.printlog("收到工厂测试数据..");
				String[] temp = messageHead.getNum().split("\\|");
				String SN = messageHead.getSn();
				FactoryTestInfo info = new FactoryTestInfo();

				String roam_xmlLientVersion = temp[0].trim();
				String roam_apkVersion = temp[1].trim();
				String roam_imei = temp[2].trim();
				String roam_sn = temp[3].trim();
				String roam_iccid = temp[4].trim();
				String roam_simStatus = temp[5].trim();
				String roam_calibrationFlag = temp[6].trim().replace(" ", "");
				;
				String roam_netType = temp[7].trim();
				String roam_netStrength = temp[8].trim();
				String roam_dataLinkStatus = temp[9].trim();
				String roam_xmclient_step = temp[10].trim();

				String local_clientVersion = temp[11].trim();
				String local_apkVersion = temp[12].trim();
				String local_calibrationFlag = temp[13].trim().replace(" ", "");
				String local_sn = temp[14].trim();
				String local_imsi = temp[15].trim();
				String local_netType = temp[16].trim();
				String local_battery = temp[17].trim();
				String local_iccid = temp[18].trim();
				String local_simStatus = temp[19].trim();
				String local_netStrength = temp[20].trim();
				String local_dataLinkStatus = temp[21].trim();
				String local_WifiPwd = temp[22].trim();
				String local_imei = temp[23].trim();

				info.setSN(SN);
				info.setSSID("0");

				info.setRoam_appVersionNum(roam_xmlLientVersion);
				info.setRoam_apkVersionNum(roam_apkVersion);
				info.setRoam_IMEI(roam_imei);
				info.setRoam_SN(roam_sn);
				info.setRoam_ICCID(roam_iccid);
				info.setRoam_SIMStatus(roam_simStatus);
				info.setRoam_calibrationMark(roam_calibrationFlag);
				info.setRoam_networkStrength(roam_netStrength);
				info.setRoam_networkType(roam_netType);
				info.setRoam_dataConnectionStatus(roam_dataLinkStatus);

				info.setLocal_wifiPwd(local_WifiPwd);
				info.setLocal_appVersionNum(local_clientVersion);
				info.setLocal_apkVersionNum(local_apkVersion);
				info.setLocal_IMEI(local_imei);
				info.setLocal_SN(local_sn);
				info.setLocal_ICCID(local_iccid);
				info.setLocal_SIMStatus(local_simStatus);
				info.setLocal_calibrationMark(local_calibrationFlag);
				info.setLocal_networkType(local_netType);
				info.setLocal_networkStrength(local_netStrength);
				info.setLocal_dataConnectionStatus(local_dataLinkStatus);
				info.setLocal_serialComTest("");
				
				info.setUpload_networkedTestResult("PASS");
				info.setResult("成功");
				info.setCreatorDate("now");
				info.setSysStatus(true);
				
				FileWrite.printlog("效验完毕插入测试数据开始");
				if (deviceInfoSer.insertFactory(info))
				{
					FileWrite.printlog("效验完毕插入测试数据成功");
				}
				else
				{
					FileWrite.printlog("效验完毕插入测试数据失败");
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				FileWrite.printlog(e.getMessage());
			}
		}

	}



	@Override
	public void doPostBusiness()
	{
		// TODO Auto-generated method stub

	}



	@Override
	public Object getResult()
	{
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void buildInteractionLog()
	{
		// TODO Auto-generated method stub

	}



	@Override
	public void exectute() throws SCException
	{
		// TODO Auto-generated method stub

	}



	public void buildresult()
	{

	}

}
