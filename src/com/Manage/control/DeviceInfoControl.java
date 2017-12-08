package com.Manage.control;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.Manage.common.constants.Constants;
import com.Manage.common.enumEntity.UploadFilepathEnum;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.DownLoadUtil;
import com.Manage.common.util.ExcelUtils;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.AcceptOrder;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.DeviceDealOrders;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.DeviceUpgrading;
import com.Manage.entity.Dictionary;
import com.Manage.entity.Distributor;
import com.Manage.entity.FactoryTestInfo;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.OrdersInfo;
import com.Manage.entity.Shipment;
import com.Manage.entity.common.SearchDTO;

@Controller
@RequestMapping("/device")
public class DeviceInfoControl extends BaseController
{
	private final Logger logger = LogUtil.getInstance(DeviceInfoControl.class);



	@RequestMapping("/list")
	// 设备列表
	public String devicelist(HttpServletRequest req)
	{

		List<DeviceInfo> dev = deviceInfoSer.getdeviceType();
		req.setAttribute("types", dev);
		List<Distributor> disAll = distributorSer.getAll("");
		req.setAttribute("disAll", disAll);
		return "WEB-INF/views/deviceinfo/deviceInfo_list";
	}



	@RequestMapping("/insert")
	// 新增设备
	public String insertdevice(HttpServletRequest req)
	{

		// List<Distributor> distributor = distributorSer.getAll(null);
		// 渠道商
		Distributor dis = new Distributor();
		dis.setType("设备");
		List<Distributor> distributor = distributorSer.getbytype(dis);
		List<CountryInfo> countrys = countryInfoSer.getAll("");
		List<Dictionary> dictionary = dictionarySer.getAllList(Constants.DICT_DEVICE_COLOR);

		req.setAttribute("distributor", distributor);
		req.setAttribute("countrys", countrys);
		req.setAttribute("dictionary", dictionary);

		return "WEB-INF/views/deviceinfo/deviceInfo_add";
	}



	/**
	 * 归还设备 入口
	 * 
	 * @param model
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("/revert")
	public String revertdevice(Model model, HttpServletRequest req, HttpServletResponse resp)
	{

		resp.setContentType("text/html;charset=utf-8");

		// 押金申请记录状态
		List<Dictionary> DepositStatusDict = dictionarySer.getAllList(Constants.DICT_DEPOSIT_RECORD_STATUS);
		model.addAttribute("DepositStatusDict", DepositStatusDict);
		return "WEB-INF/views/deviceinfo/deviceInfo_revert";
	}



	@RequestMapping("/delete")
	public String deviceDelete()
	{

		return "WEB-INF/views/deviceinfo/deviceInfo_delete";
	}



	// 查看单个设备详情
	// @RequestMapping("/deviceInfodetail")
	// public String deviceDetail(@RequestParam String deviceid,
	// HttpServletRequest req){
	// DeviceInfo device = deviceInfoSer.getdeviceInfodetail(deviceid);
	// req.setAttribute("device", device);
	// return "WEB-INF/views/deviceinfo/deviceInfo_detail";
	// }

	@RequestMapping("/deviceInfodetail")
	public String deviceDetail(@RequestParam String deviceid, HttpServletRequest req, HttpServletRequest req1)
	{

		DeviceInfo deviceinfo = deviceInfoSer.getdeviceInfodetail(deviceid);
		req1.setAttribute("deviceinfo", deviceinfo);

		List<DeviceDealOrders> device = deviceDealOrdersSer.getdeviceInfodetail(deviceid);
		req.setAttribute("device", device);
		return "WEB-INF/views/deviceinfo/deviceInfo_detail";
	}



	// 获取需修改的设备数据
	@RequestMapping("/edit")
	public String deviceEdit(@RequestParam String xlid, HttpServletRequest req)
	{

		DeviceInfo device = deviceInfoSer.getdeviceinfoEdit(xlid);
		List<String> list = new ArrayList<String>();

		List<CountryInfo> countrys = countryInfoSer.getAll("");
		if (device.getSupportCountry() != null)
		{
			if (device.getSupportCountry().length() > 2)
			{
				String country = device.getSupportCountry();
				String[] arr = country.split("\\|");
				list = Arrays.asList(arr);
			}
		}
		for (int i = 0; i < list.size(); i++)
		{
			for (int j = 0; j < countrys.size(); j++)
			{
				if (list.get(i).contains(countrys.get(j).getCountryCode() + ""))
				{
					countrys.get(j).setSelected(true);
				}
			}
		}
		req.setAttribute("device", device);
		req.setAttribute("countrys", countrys);
		return "WEB-INF/views/deviceinfo/deviceInfo_edit";
	}



	// 修改设备数据
	@RequestMapping(value = "updateToDevice", method = RequestMethod.POST)
	public String updateToDevice(DeviceInfo device, HttpServletRequest req, HttpServletResponse resp, HttpSession session)
	{

		logger.info("Running >>> >>>> [updateToDevice() 修改Device数据]");
		try
		{
			req.setCharacterEncoding("utf-8");
		}
		catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
		}
		resp.setContentType("text/html;charset=utf-8");

		System.out.println("getSupportCountry:" + device.getSupportCountry());

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		String xgr = adminUserInfo.userName;
		String updateid = adminUserInfo.userID;// 修改人ID

		SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
		String date = sdf.format(new Date());

		device.setRemark(device.getRemark().trim());
		deviceInfoSer.updatedeviceToEnd(device);
		try
		{
			AdminOperate admin = new AdminOperate();
			admin.setOperateID(UUID.randomUUID().toString());// id
			admin.setCreatorDate(date);// 创建时间
			admin.setCreatorUserID(updateid);// 创建人ID
			admin.setCreatorUserName(xgr);// 创建人姓名
			admin.setOperateContent("设备信息修改");// 操作内容
			admin.setOperateDate(date);// 操作时间
			admin.setOperateMenu("设备管理>修改设备");// 操作菜单
			admin.setOperateType("修改");// 操作类型
			admin.setSysStatus(1);
			adminOperateSer.insertdata(admin);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}

		return "WEB-INF/views/deviceinfo/deviceInfo_list";

	}



	/**
	 * 设备管理 >>查看全部设备信息
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/deviceinfoPage")
	public void datapage(SearchDTO searchDTO, Distributor distributor, DeviceInfo deviceinfo, HttpServletResponse response)
	{

		response.setContentType("application/json;charset=UTF-8");
		response.setContentType("text/html;charset=utf-8");

		if (StringUtils.isNotBlank(distributor.getCompany()))
		{
			Distributor distributor2 = distributorSer.getdisInofbycompany(distributor);
			if (distributor2 != null)
			{
				deviceinfo.setDistributorID(distributor2.getDistributorID());
			}
		}

		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), deviceinfo);

		String jsonString = null;

		if (deviceinfo.getDeviceStatus() == null)
		{
			jsonString = deviceInfoSer.getpageString(seDto);
		}
		else if (deviceinfo.getDeviceStatus() != null)
		{
			if (deviceinfo.getDeviceStatus().equals("--所有状态--"))
			{
				deviceinfo.setDeviceStatus(null);
				jsonString = deviceInfoSer.getpageString(seDto);
			}
			else
			{
				jsonString = deviceInfoSer.getpageString(seDto);
			}
		}

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
	 * 查看已删除设备信息
	 * 
	 * @param searchDTO
	 * @param DeviceDealOrders
	 * @param response
	 */
	@RequestMapping("/deviceinfoPagedelete")
	public void datapagedelete(SearchDTO searchDTO, DeviceInfo deviceinfo, HttpServletResponse response)
	{

		response.setContentType("application/json;charset=UTF-8");
		System.out.println("---" + searchDTO.getCurPage() + "------" + searchDTO.getPageSize());
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), deviceinfo);
		String jsonString = deviceInfoSer.getpageStringDelete(seDto);
		try
		{
			response.getWriter().println(jsonString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}



	@RequestMapping("/checksn")
	public void checksninput(HttpServletRequest request, HttpServletResponse respone)
	{

		String sn = request.getParameter("sid");// 序列号
		int count = deviceInfoSer.forsncount(sn);
		try
		{
			respone.getWriter().print(count);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	/**
	 * 新增设备信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/add")
	public void deviceinfoAdd(DeviceInfo device1, HttpServletRequest req, HttpServletResponse resp, HttpSession session)
	{

		try
		{
			req.setCharacterEncoding("utf-8");
		}
		catch (UnsupportedEncodingException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		resp.setContentType("text/html;charset=utf-8");
		String uuid = UUID.randomUUID().toString();// ID
		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");

		String userCountry = req.getParameter("userCountry");
		System.out.println("userCountry:" + userCountry);
		System.out.println();
		System.out.println();

		SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
		String date = sdf.format(new Date());

		device1.setDeviceID(uuid);
		device1.setCreatorUserID(adminUserInfo.userID);
		device1.setCreatorUserName(adminUserInfo.userName);
		int num = deviceInfoSer.insertDeviceInfo(device1);
		if (num > 0)
		{
			// 成功入库，新增入库记录
			Shipment shipm = null;
			try
			{
				shipm = DeviceLogShipment(device1.getSN(), "", "0", "", "", "入库", "新增设备", "", "", "");
				ShipmentSer.insert(shipm);// 写入出入库记录
			}
			catch (UnsupportedEncodingException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(num);
		try
		{
			resp.getWriter().print(num);
			req.setAttribute("addmesg", num);
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}

		try
		{
			AdminOperate admin = new AdminOperate();
			admin.setOperateID(UUID.randomUUID().toString());// id
			admin.setCreatorDate(date);// 创建时间
			admin.setCreatorUserID(adminUserInfo.userID);// 创建人ID
			admin.setCreatorUserName(adminUserInfo.userName);// 创建人姓名
			admin.setOperateContent("新增设备,设备序列号：" + device1.getSN());// 操作内容
			admin.setOperateDate(date);// 操作时间
			admin.setOperateMenu("设备管理>新增设备");// 操作菜单
			admin.setOperateType("新增");// 操作类型
			admin.setSysStatus(1);
			adminOperateSer.insertdata(admin);
		}
		catch (Exception e)
		{
			// TODO: handle exception
			logger.debug(e.getMessage());
			e.printStackTrace();
		}

		// return "WEB-INF/views/deviceinfo/deviceInfo_add";
	}



	/**
	 * 归还设备 (在归还设备页面, 区别于下面在全部设备列表页面中的操作那个入口)
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/revertTo")
	// 1修改设备信息表对应的设备为可用，2修改DeviceDealOrders(设备交易订单表)是否归还 为已归还
	public void revertDevice(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws IOException
	{

		try
		{
			req.setCharacterEncoding("utf-8");
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resp.setContentType("text/html;charset=utf-8");
		String sn = req.getParameter("device_sn");
		// String repertoryStatus =
		// java.net.URLDecoder.decode(req.getParameter("repertoryStatus"),
		// "UTF-8");
		System.out.println("sn:" + sn);
		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		String updateid = adminUserInfo.userID;// 修改人ID
		String username = adminUserInfo.userName;
		int issuccesscount = 0;

		// 归还操作判断
		if (sn.indexOf("/") > 0)
		{
			System.out.println("进入多个设备归还...");
			String[] strArray = null;
			strArray = sn.split("/");
			String[] strresult = new String[strArray.length];
			for (int i = 0; i < strArray.length; i++)
			{
				// strresult[i] = "17215021000" + strArray[i];
				strresult[i] = Constants.SNformat(strArray[i]);
			}
			int forresutcount = 0;
			// 多个设备号归还
			for (int i = 0; i < strresult.length; i++)
			{
				String sns = strresult[i];
				// 输入单个设备号归还
				SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
				sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
				String date = sdf.format(new Date());

				int backresult = deviceInfoSer.getdevice(strresult[i]);
				System.out.println("查看设备信息表是否有此设备 的记录:" + backresult);

				int snCount = 0;
				try
				{
					snCount = flowDealOrdersSer.getIsFlowUseOrderForSn(strresult[i]);// 判断此SN是否有未完成的有效流量订单
																						// 有则不允许使用归还操作
					if (snCount > 0)
					{

						System.out.println("有未结束的有效订单，不能进行归还操作！");
						resp.getWriter().print("SN:" + strresult[i] + " 有未结束的有效流量订单！归还失败！");

					}
					else
					{
						System.out.println("没有未结束的有效流量订单，可以进行归还操作!");

						if (backresult > 0)
						{
							System.out.println("查询此设备是否有交易记录！");

							DeviceDealOrders devicedeal = new DeviceDealOrders();
							devicedeal.setSN(strresult[i]);// sn
							// devicedeal.setDeallType("租用");//设备状态为租用
							// 、有可能也是内部人员选错 设备状态为购买 也可归还
							devicedeal.setIfReturn("否");// 还没有归还此设备
							// int devicedealnum =
							// deviceInfoSer.searchDevice(devicedeal);//通过sn查询此设备的交易记录（是否有设备记录）
							// 2015-9-25 11:49:08 （汪博讨论结果） 修改为该设备状态是否为【可使用】
							// ，如果为【可使用】状态不处理，【不是可使用】状态进行下面的归还操作

							int devicedealnum = deviceInfoSer.searchDevicekeshiyong(devicedeal);
							if (devicedealnum > 0)
							{
								System.out.println("有此设备的交易记录，修改交易状态及设备状态！");
								// 1.修改设备信息表设备状态 及库存状态
								DeviceInfo deviceinfodata = new DeviceInfo();
								deviceinfodata.setSN(strresult[i]);
								// if (repertoryStatus.equals("是"))
								// deviceinfodata.setRepertoryStatus("入库");//
								// 库存状态
								// else
								// deviceinfodata.setRepertoryStatus("出库");//
								// 库存状态
								deviceinfodata.setDeviceStatus("可使用");// 设备状态
								deviceinfodata.setModifyUserID(updateid);// 修改人
								deviceinfodata.setModifyDate(date);// 修改时间
								int num = deviceInfoSer.updateDeviceInfo(deviceinfodata);
								if (num > 0)
								{
									System.out.println("第一步：修改设备信息表状态成功！deviceInfo");

									DeviceDealOrders devicedeals = new DeviceDealOrders();// 归还设备后修改设备订单表状态
									devicedeals.setSN(strresult[i]);
									devicedeals.setIfReturn("是");// 是否归还
									devicedeals.setIfFinish("是");// 订单是否完成
									devicedeals.setOrderStatus("已过期");// 订单状态改为已过期
									devicedeals.setModifyUserID(updateid);// 修改人ID
									SimpleDateFormat sdf2 = new SimpleDateFormat();// 格式化时间
									sdf2.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记

									devicedeals.setReturnDate(date);// 归还日期
									devicedeal.setModifyDate(new DateUtils().parseDate(sdf2.format(new Date())));// 修改日期

									int devicedealsnum = deviceInfoSer.updateDeviceDealOrders(devicedeals);// 2.修改设备订单表
																											// 设备状态为
																											// 已归还
									if (devicedealsnum > 0)
									{

										System.out.println("第二步：修改设备订单表 设备状态为 已归还成功！devicedealorders");

										// 3.修改流量订单表（数据服务）
										FlowDealOrders fdo = new FlowDealOrders();
										fdo.setOrderStatus("不可用");
										fdo.setSN(strresult[i]);
										fdo.setModifyDate(new DateUtils().parseDate(date));
										fdo.setModifyUserID(updateid);

										// int three =
										try
										{
											// 流量订单已过期不修改（2016年6月27日10:50:34
											// 应wangbo要求修改，已过期订单不该订单状态,其他改为不可用）
											String orderStatus = flowDealOrdersSer.getorderStatus(strresult[i]);
											if (!orderStatus.equals("已过期"))
											{
												flowDealOrdersSer.updateOrderStatusForSn(fdo);
											}
										}
										catch (Exception e1)
										{
											// TODO Auto-generated catch block
											logger.info("归还设备，操作流量订单失败！" + e1.getMessage());
											e1.printStackTrace();
										}
										try
										{
											wxBindDeviceSer.wxJieBangDevice(strresult[i]);
										}
										catch (Exception e1)
										{
											logger.info("微信解绑设备出错！" + e1.getMessage());
											e1.printStackTrace();
										}
										// if(three>0){
										System.out.println("第三步:修改流量交易订单成功！ ");

										try
										{
											AdminOperate admin = new AdminOperate();
											admin.setOperateID(UUID.randomUUID().toString());// id
											admin.setCreatorDate(date);// 创建时间
											admin.setCreatorUserID(updateid);// 创建人ID
											admin.setCreatorUserName(username);// 创建人姓名
											admin.setOperateContent("归还设备 ,设备序列号：" + strresult[i]);// 操作内容
											admin.setOperateDate(date);// 操作时间
											admin.setOperateMenu("设备管理>归还设备");// 操作菜单
											admin.setOperateType("归还");// 操作类型
											admin.setSysStatus(1);
											adminOperateSer.insertdata(admin);
										}
										catch (Exception e)
										{
											logger.debug(e.getMessage());
											e.printStackTrace();
										}

										// 修改下单表 租赁设备 是否归还状态为 是
										try
										{
											acceptOrderSer.updateAccepOrderBySn(strresult[i]);
										}
										catch (Exception e)
										{
											logger.info("修改下单表状态为是");
											e.printStackTrace();
										}

										// 归还与出入库无关 无需记录2016年5月12日17:05:19
										/*
										 * if (repertoryStatus.equals("是")) {
										 * 
										 * try { Shipment shipm = new
										 * Shipment(); shipm =
										 * DeviceLogShipment(strresult[i], "",
										 * "0", "", "", "入库", "", "");
										 * ShipmentSer.insert(shipm); } catch
										 * (Exception e) { e.printStackTrace();
										 * } }
										 */
										System.out.println("修改设备交易表状态成功，设备归还成功！");
										resp.getWriter().print("设备" + strresult[i] + "归还<span style='color:green'>成功</span>！<br/>");
										// }
										// else
										// {
										// System.out.println("第三步:修改流量交易订单失败！flowdealorders");
										// resp.getWriter().print("归还设备:"+strresult[i]+"<span style='color:red'>失败</span>！03<br/>");
										// }
									}
									else
									{
										System.out.println("第二步：修改设备订单表 设备状态为 已归还失败！devicedealorders");
										resp.getWriter().print("归还设备:" + strresult[i] + "<span style='color:red'>失败</span>！（设备订单表 设备状态为 已归还失败！流量订单修改失败！）<br/>");
									}
								}
								else
								{
									System.out.println("第一步：修改设备信息表状态失败！deviceInfo");
									resp.getWriter().print("归还设备:" + strresult[i] + "<span style='color:red'>失败</span>！（修改设备信息表状态失败，所有状态未改）<br/>");
								}
							}
							else
							{
								// String type =
								// deviceInfoSer.searchDeviceInfoTypeforSn(sn);

								System.out.println("此设备当前还没有交易记录,不存在归还操作！");
								resp.getWriter().print("设备<span style='color:red'>" + strresult[i] + "</span>还没有交易记录,已改为可使用！<br/>");
								DeviceInfo deviceinfodata = new DeviceInfo();
								deviceinfodata.setSN(sns);
								// if (repertoryStatus.equals("是"))
								// deviceinfodata.setRepertoryStatus("入库");//
								// 库存状态
								// else
								// deviceinfodata.setRepertoryStatus("出库");//
								// 库存状态
								deviceinfodata.setDeviceStatus("可使用");// 设备状态
								deviceinfodata.setModifyUserID(updateid);// 修改人
								deviceinfodata.setModifyDate(date);// 修改时间
								int num = deviceInfoSer.updateDeviceInfo(deviceinfodata);
								if (num > 0)
								{
									System.out.println("此设备还没有交易记录，当前为待使用状态，已改为可使用状态！");
								}

							}
						}
						else
						{
							System.out.println("没有搜索到" + sns + "设备记录！");
							resp.getWriter().print("没有搜索到<span style='color:red'>" + sns + "</span>设备记录！<br/>");
						}

					}// 验证是否有未结束的有效流量单，else end。。。
				}
				catch (Exception e2)
				{
					e2.printStackTrace();
					logger.info("验证SN是否有未结束的有效流量订单时出错！");
					resp.getWriter().print("验证SN是否有未结束的有效流量订单时出错！稍后请重试");
				}

			}

		}
		else
		{// 输入单个设备号归还
			System.out.println("进入单个设备归还...");

			SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
			sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
			String date = sdf.format(new Date());
			// sn = "17215021000" + sn;
			sn = Constants.SNformat(sn);

			int backresult = deviceInfoSer.getdevice(sn);
			System.out.println("查看设备信息表是否有此设备 的记录:" + backresult);

			int snCount = 0;
			try
			{
				snCount = flowDealOrdersSer.getIsFlowUseOrderForSn(sn);// 判断此SN是否有未完成的有效流量订单
																		// 有则不允许使用归还操作
				if (snCount > 0)
				{
					System.out.println("此SN有未结束的有效流量订单！不允许归还此设备！");
					resp.getWriter().print("SN:" + sn + " 有未结束的有效流量订单！归还失败！");
				}
				else
				{
					System.out.println("此SN没有有效流量订单，可以进行归还操作");

					if (backresult > 0)
					{
						System.out.println("查询此设备是否有交易记录！");

						DeviceDealOrders devicedeal = new DeviceDealOrders();
						devicedeal.setSN(sn);// sn
						// devicedeal.setIfReturn("否");
						// devicedeal.setDeallType("租用");//设备状态为租用
						// int devicedealnum =
						// deviceInfoSer.searchDevice(devicedeal);//通过sn查询此设备的交易记录（是否有设备记录）
						// 2015-9-25 11:49:08 （汪博讨论结果） 修改为该设备状态是否为【可使用】
						// ，如果为【可使用】状态不处理，【不是可使用】状态进行下面的归还操作

						int devicedealnum = deviceInfoSer.searchDevicekeshiyong(devicedeal);
						if (devicedealnum > 0)
						{
							System.out.println("有此设备的交易记录，修改交易状态及设备状态！");
							// 1.修改设备信息表设备状态 及库存状态
							DeviceInfo deviceinfodata = new DeviceInfo();
							deviceinfodata.setSN(sn);
							// if (repertoryStatus.equals("是"))
							// deviceinfodata.setRepertoryStatus("入库");// 库存状态
							// else deviceinfodata.setRepertoryStatus("出库");//
							// 库存状态
							deviceinfodata.setDeviceStatus("可使用");// 设备状态
							deviceinfodata.setModifyUserID(updateid);// 修改人
							deviceinfodata.setModifyDate(date);// 修改时间
							int num = deviceInfoSer.updateDeviceInfo(deviceinfodata);
							if (num > 0)
							{
								System.out.println("第一步：修改设备信息表状态成功！deviceInfo");
								// 2.修改设备交易表信息
								DeviceDealOrders devicedeals = new DeviceDealOrders();// 归还设备后修改设备订单表状态
								devicedeals.setSN(sn);
								devicedeals.setIfReturn("是");// 是否归还
								devicedeals.setIfFinish("是");// 订单是否完成
								devicedeals.setOrderStatus("已过期");// 订单状态改为已过期

								devicedeals.setModifyUserID(updateid);// 修改人ID
								SimpleDateFormat sdf2 = new SimpleDateFormat();// 格式化时间
								sdf2.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
								devicedeal.setModifyDate(new DateUtils().parseDate(sdf2.format(new Date())));// 修改日期

								int devicedealsnum = deviceInfoSer.updateDeviceDealOrders(devicedeals);// 2.修改设备订单表
																										// 设备状态为
																										// 已归还
								if (devicedealsnum > 0)
								{
									System.out.println("第二步：修改设备订单表 设备状态为 已归还成功！devicedealorders");
									// 3.修改流量订单表（数据服务）
									FlowDealOrders fdo = new FlowDealOrders();
									fdo.setOrderStatus("不可用");
									fdo.setSN(sn);

									// int three =
									try
									{
										// 流量订单已过期不修改（2016年6月27日10:50:34
										// 应wangbo要求修改，已过期订单不该订单状态,其他改为不可用）
										String orderStatus = flowDealOrdersSer.getorderStatus(sn);
										if (!orderStatus.equals("已过期"))
										{
											flowDealOrdersSer.updateOrderStatusForSn(fdo);
										}

										// flowDealOrdersSer.updateOrderStatusForSn(fdo);
									}
									catch (Exception e1)
									{
										logger.info("归还设备，操作流量订单失败！" + e1.getMessage());
										e1.printStackTrace();
									}
									// if(three>0){
									System.out.println("第三步:修改流量交易订单成功！flowdealorders");

									try
									// 操作日志部分
									{
										AdminOperate admin = new AdminOperate();
										admin.setOperateID(UUID.randomUUID().toString());// id
										admin.setCreatorDate(date);// 创建时间
										admin.setCreatorUserID(updateid);// 创建人ID
										admin.setCreatorUserName(username);// 创建人姓名
										admin.setOperateContent("归还设备 ,设备序列号：" + sn);// 操作内容
										admin.setOperateDate(date);// 操作时间
										admin.setOperateMenu("设备管理>归还设备");// 操作菜单
										admin.setOperateType("归还");// 操作类型
										admin.setSysStatus(1);
										adminOperateSer.insertdata(admin);
									}
									catch (Exception e)
									{
										logger.debug(e.getMessage());
										e.printStackTrace();
									}
									System.out.println("修改设备交易表状态成功，设备归还成功！");
									try
									{
										wxBindDeviceSer.wxJieBangDevice(sn);
									}
									catch (Exception e1)
									{
										logger.info("微信解绑设备出错！" + e1.getMessage());
										e1.printStackTrace();
									}

									// 修改下单表 租赁设备 是否归还状态为 是
									try
									{
										acceptOrderSer.updateAccepOrderBySn(sn);
									}
									catch (Exception e)
									{
										logger.info("修改下单表状态为是");
										e.printStackTrace();
									}

									// 写入出库记录(2016年5月12日17:04:28归还与出入库无关)

									/*
									 * if (repertoryStatus.equals("是")) { try {
									 * Shipment shipm = new Shipment(); shipm =
									 * DeviceLogShipment(sn, "", "0", "", "",
									 * "入库", "", ""); ShipmentSer.insert(shipm);
									 * } catch (Exception e) {
									 * e.printStackTrace(); } }
									 */

									resp.getWriter().print("归还设备:" + sn + "<span style='color:green'>成功</span>！");

									// }else{
									// System.out.println("第三步:修改流量交易订单失败！flowdealorders");
									// resp.getWriter().print("归还设备:"+sn+"<span style='color:red'>失败</span>！03<br/>");
									// }

								}
								else
								{
									System.out.println("第二步：修改设备订单表 设备状态为 已归还失败！devicedealorders");
									resp.getWriter().print("归还设备" + sn + "<span style='color:red'>失败</span>!（2修改设备订单表 设备状态为 已归还失败3流量订单状态未改）<br/>");
								}
							}
							else
							{
								System.out.println("第一步：修改设备信息表状态失败！deviceInfo");
								resp.getWriter().print("归还设备" + sn + "<span style='color:red'>失败</span>!（1修改设备信息表状态失败2设备订单状态未改3流量订单状态未改）<br/>");
							}
						}
						else
						{
							// String type =
							// deviceInfoSer.searchDeviceInfoTypeforSn(sn);
							// if(type.equals("可使用")){
							// }else{
							DeviceInfo deviceinfodata = new DeviceInfo();
							deviceinfodata.setSN(sn);
							// if (repertoryStatus.equals("是"))
							// deviceinfodata.setRepertoryStatus("入库");// 库存状态
							// else deviceinfodata.setRepertoryStatus("出库");//
							// 库存状态
							deviceinfodata.setDeviceStatus("可使用");// 设备状态
							deviceinfodata.setModifyUserID(updateid);// 修改人
							deviceinfodata.setModifyDate(date);// 修改时间
							int num = deviceInfoSer.updateDeviceInfo(deviceinfodata);
							if (num > 0)
							{
								wxBindDeviceSer.wxJieBangDevice(sn);
								System.out.println("此设备还没有交易记录，当前为待使用状态，已改为可使用状态！");
								resp.getWriter().print("设备" + sn + "当前还没有交易记录,已改为可使用！");
							}
							// }
						}
					}
					else
					{
						System.out.println("没有搜索到此设备记录，此设备信息或已被他人删除！");
						resp.getWriter().print("没有搜索到设备：<span style='color:red'>" + sn + "</span>的记录！");
					}

				}// 验证SN是否有未结束的有效订单 else end
			}
			catch (Exception e2)
			{
				e2.printStackTrace();
				logger.info("归还设备，验证SN是否有未完成的有效流量订单是出错！");
			}
			// 查询此SN是否有未结束的有效流量订单！

		}

	}



	/**
	 * 归还设备 (在设备列表页面, 区别于上面那个)
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/revertTo2")
	// 1修改设备信息表对应的设备为可用，2修改DeviceDealOrders(设备交易订单表)是否归还 为已归还
	public void revertDevice2(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws IOException
	{

		try
		{
			req.setCharacterEncoding("utf-8");
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resp.setContentType("text/html;charset=utf-8");

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		String updateid = adminUserInfo.userID;// 修改人ID
		String username = adminUserInfo.userName;

		SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
		String date = sdf.format(new Date());
		String sn = req.getParameter("device_sn");

		int backresult = deviceInfoSer.getdevice(sn);
		System.out.println("查看设备信息表是否有此设备 的记录:" + backresult);

		int snCount = 0;
		try
		{
			snCount = flowDealOrdersSer.getIsFlowUseOrderForSn(sn);// 判断此SN是否有未完成的有效流量订单
																	// 有则不允许使用归还操作
			if (snCount > 0)
			{
				System.out.println("此SN有未结束的有效流量订单！不允许归还此设备！");
				resp.getWriter().print("SN:" + sn + " 有未结束的有效流量订单！归还失败！");
			}
			else
			{
				System.out.println("此SN没有有效流量订单，可以进行归还操作");

				if (backresult > 0)
				{
					System.out.println("查询此设备是否有交易记录！");

					DeviceDealOrders devicedeal = new DeviceDealOrders();
					devicedeal.setSN(sn);// sn
					// devicedeal.setDeallType("租用");//设备状态为租用
					devicedeal.setIfReturn("否");// 还没有归还此设备
					int devicedealnum = deviceInfoSer.searchDevice2(devicedeal);// 通过sn查询此设备的交易记录（是否有设备记录）

					if (devicedealnum > 0)
					{
						System.out.println("有此设备的交易记录，修改交易状态及设备状态！");
						// 1.修改设备信息表设备状态 及库存状态
						DeviceInfo deviceinfodata = new DeviceInfo();
						deviceinfodata.setSN(sn);
						deviceinfodata.setRepertoryStatus("入库");// 库存状态
						deviceinfodata.setDeviceStatus("可使用");// 设备状态
						deviceinfodata.setModifyUserID(updateid);// 修改人
						deviceinfodata.setModifyDate(date);// 修改时间
						int num = deviceInfoSer.updateDeviceInfo(deviceinfodata);
						if (num > 0)
						{
							System.out.println("第一步：修改设备信息表状态成功！deviceInfo");
						}
						else
						{
							System.out.println("第一步：修改设备信息表状态失败！deviceInfo");
							req.setAttribute("message", " * 修改设备信息表状态失败,请重试或联系管理员！");

							System.out.println("请求链接：" + request.getHeader("Referer"));
							resp.getWriter().print("0");
							// return
							// "WEB-INF/views/deviceinfo/deviceInfo_list";
						}
						// 2.修改设备交易表信息
						DeviceDealOrders devicedeals = new DeviceDealOrders();// 归还设备后修改设备订单表状态
						devicedeals.setSN(sn);
						devicedeals.setIfReturn("是");// 是否归还
						devicedeals.setIfFinish("是");// 订单是否完成
						devicedeals.setOrderStatus("已过期");// 订单状态改为不可用

						devicedeals.setModifyUserID(updateid);// 修改人ID
						SimpleDateFormat sdf2 = new SimpleDateFormat();// 格式化时间
						sdf2.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
						devicedeal.setModifyDate(new DateUtils().parseDate(sdf2.format(new Date())));// 修改日期

						int devicedealsnum = deviceInfoSer.updateDeviceDealOrders(devicedeals);// 2.修改设备订单表
																								// 设备状态为
																								// 已归还
						if (devicedealsnum > 0)
						{
							System.out.println("第二步：修改设备订单表 设备状态为 已归还成功！devicedealorders");
						}
						else
						{
							System.out.println("第二步：修改设备订单表 设备状态为 已归还失败！devicedealorders");
						}
						// 3.修改流量订单表（数据服务）
						FlowDealOrders fdo = new FlowDealOrders();
						fdo.setOrderStatus("不可用");
						fdo.setSN(sn);

						try
						{
							// flowDealOrdersSer.updateOrderStatusForSn(fdo);
							// 流量订单已过期不修改（2016年6月27日10:50:34
							// 应wangbo要求修改，已过期订单不该订单状态,其他改为不可用）
							String orderStatus = flowDealOrdersSer.getorderStatus(sn);
							if (!orderStatus.equals("已过期"))
							{
								flowDealOrdersSer.updateOrderStatusForSn(fdo);
							}

						}
						catch (Exception e1)
						{
							logger.info("归还设备，操作流量订单失败！" + e1.getMessage());
							e1.printStackTrace();
						}
						// if(three>0){
						// System.out.println("第三步:修改流量交易订单成功！flowdealorders");
						// }else{
						// System.out.println("第三步:修改流量交易订单失败！flowdealorders");
						// }

						if (devicedealsnum > 0)
						{
							// 操作日志部分
							try
							{
								AdminOperate admin = new AdminOperate();
								admin.setOperateID(UUID.randomUUID().toString());// id
								admin.setCreatorDate(date);// 创建时间
								admin.setCreatorUserID(updateid);// 创建人ID
								admin.setCreatorUserName(username);// 创建人姓名
								admin.setOperateContent("归还设备 ,设备序列号：" + sn);// 操作内容
								admin.setOperateDate(date);// 操作时间
								admin.setOperateMenu("设备管理>归还设备");// 操作菜单
								admin.setOperateType("归还");// 操作类型
								admin.setSysStatus(1);
								adminOperateSer.insertdata(admin);
							}
							catch (Exception e)
							{
								logger.debug(e.getMessage());
								e.printStackTrace();
							}
							System.out.println("修改设备交易表状态成功，设备归还成功！");
							// 提示归还成功
							req.setAttribute("message", " * 归还设备成功！");

							System.out.println("请求链接：" + request.getHeader("Referer"));

							// 解除（微信）绑定
							try
							{
								wxBindDeviceSer.wxJieBangDevice(sn);
							}
							catch (Exception e1)
							{
								logger.info("微信解绑设备出错！" + e1.getMessage());
								e1.printStackTrace();
							}

							// 修改下单表 租赁设备 是否归还状态为 是
							try
							{
								acceptOrderSer.updateAccepOrderBySn(sn);
							}
							catch (Exception e)
							{
								logger.info("修改下单表状态为是");
								e.printStackTrace();
							}

							try
							{
								Shipment shipm = new Shipment();
								shipm = DeviceLogShipment(sn, "", "0", "", "", "入库", "", "", "", "");
								ShipmentSer.insert(shipm);
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}

							// return
							// "WEB-INF/views/deviceinfo/deviceInfo_list";
							resp.getWriter().print("设备" + sn + "归还成功！");
						}
						else
						{
							// 此处还可判断是否到归还日期
							System.out.println("设备" + sn + "归还失败！");
							req.setAttribute("message", " * 归还设备失败！");

							System.out.println("请求链接：" + request.getHeader("Referer"));

							// return
							// "WEB-INF/views/deviceinfo/deviceInfo_list";
							resp.getWriter().print("0");
						}
					}
					else
					{
						// String type =
						// deviceInfoSer.searchDeviceInfoTypeforSn(sn);
						// if(type.equals("可使用")){
						// System.out.println("此设备当前还没有交易记录,不存在归还操作！");
						// req.setAttribute("message",
						// " * 此设备还没有任何交易记录,不存在归还操作！");
						//
						// System.out.println("请求链接："+request.getHeader("Referer"));
						//
						// return "WEB-INF/views/deviceinfo/deviceInfo_list";
						// }else{
						DeviceInfo deviceinfodata = new DeviceInfo();
						deviceinfodata.setSN(sn);
						deviceinfodata.setRepertoryStatus("入库");// 库存状态
						deviceinfodata.setDeviceStatus("可使用");// 设备状态
						deviceinfodata.setModifyUserID(updateid);// 修改人
						deviceinfodata.setModifyDate(date);// 修改时间
						int num = deviceInfoSer.updateDeviceInfo(deviceinfodata);
						if (num > 0)
						{
							System.out.println("此设备还没有交易记录，当前为待使用状态，已改为可使用状态！");
							// req.setAttribute("message",
							// " * 此设备还没有交易记录,设备状态已改为可使用！");

							System.out.println("请求链接：" + request.getHeader("Referer"));

							resp.getWriter().print("此设备还没有交易记录，已改为可使用状态！");
							// return
							// "WEB-INF/views/deviceinfo/deviceInfo_list";
						}
						else
						{
							// req.setAttribute("message",
							// " * 此设备还没有交易记录，归还失败！");

							System.out.println("请求链接：" + request.getHeader("Referer"));
							resp.getWriter().print("0");
							// return
							// "WEB-INF/views/deviceinfo/deviceInfo_list";
						}
						// }
					}
				}
				else
				{
					System.out.println("没有搜索到此设备记录，此设备信息或已被他人删除！");
					// req.setAttribute("message",
					// " * 没有搜索到此设备任何记录，sn号输入错误 或 此设备信息或已被他人删除！");

					System.out.println("请求链接：" + request.getHeader("Referer"));
					resp.getWriter().print("没有搜索到此设备记录，此设备信息或已被他人删除！");
					// return "WEB-INF/views/deviceinfo/deviceInfo_list";
				}

			}// 验证SN是否有未结束的有效订单 else end
		}
		catch (Exception e2)
		{
			e2.printStackTrace();
			logger.info("归还设备，验证SN是否有未完成的有效流量订单是出错！");
		}

	}



	@RequestMapping("/deletedevice")
	// 删除设备
	public String deviceDelete(@RequestParam String uid, HttpServletRequest req)
	{

		DeviceDealOrders devicedeal = new DeviceDealOrders();
		devicedeal.setSN(uid);// sn
		devicedeal.setDeallType("租用");// 设备状态为租用
		devicedeal.setIfReturn("否");// 还没有归还此设备
		int devicedealnum = deviceInfoSer.searchDevice(devicedeal);// 通过sn查询此设备的交易记录（是否有设备记录）

		if (devicedealnum > 0)
		{
			System.out.println("设备 [ " + uid + " ]还在使用当中，不允许删除设备，请在归还设备后进行相关操作！");
			req.setAttribute("messagedevicedele", " * 设备 [ " + uid + " ]还在使用当中，不允许删除设备，请在归还设备后进行相关操作！");
			List<DeviceInfo> dev = deviceInfoSer.getdeviceType();
			req.setAttribute("types", dev);
			return "WEB-INF/views/deviceinfo/deviceInfo_list";

		}
		else
		{
			System.out.println("没有此设备的未结束的交易记录，允许删除设备！");
			AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
			String updateid = adminUserInfo.userID;// 修改人ID
			String username = adminUserInfo.userName;

			SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
			sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
			String date = sdf.format(new Date());

			int devicenum = deviceInfoSer.getdeviceinfoDelete(uid);
			if (devicenum > 0)
			{
				try
				{
					AdminOperate admin = new AdminOperate();
					admin.setOperateID(UUID.randomUUID().toString());// id
					admin.setCreatorDate(date);// 创建时间
					admin.setCreatorUserID(updateid);// 创建人ID
					admin.setCreatorUserName(username);// 创建人姓名
					admin.setOperateContent("删除设备" + ",设备序列号：" + uid);// 操作内容
					admin.setOperateDate(date);// 操作时间
					admin.setOperateMenu("设备管理>删除设备");// 操作菜单
					admin.setOperateType("删除");// 操作类型
					admin.setSysStatus(1);
					adminOperateSer.insertdata(admin);
				}
				catch (Exception e2)
				{
					logger.debug(e2.getMessage());
					e2.printStackTrace();
				}
				req.setAttribute("messagedevicedele", " * 删除设备 [" + uid + "] 成功 ！");
				List<DeviceInfo> dev = deviceInfoSer.getdeviceType();
				req.setAttribute("types", dev);
				return "WEB-INF/views/deviceinfo/deviceInfo_list";// 删除后显示主页列表
			}
			else
			{
				req.setAttribute("messagedevicedele", " * 删除设备 [" + uid + "] 失败 ！");
				List<DeviceInfo> dev = deviceInfoSer.getdeviceType();
				req.setAttribute("types", dev);
				return "WEB-INF/views/deviceinfo/deviceInfo_list";// 删除后显示主页列表
			}
		}
	}



	/**
	 * 设备统计
	 */
	@RequestMapping("/deviceCounts")
	public String deviceCounts(HttpServletRequest req, HttpServletResponse resp, HttpSession session)
	{

		return "WEB-INF/views/deviceinfo/deviceInfo_count";
	}



	@RequestMapping("/deviceSearchend")
	public void getCountsTime(HttpServletRequest req, HttpServletResponse resp, HttpSession session)
	{

		try
		{
			resp.setContentType("application/json;charset=UTF-8");
			// 全部设备
			DeviceInfo device = new DeviceInfo();
			device.setBegindate(req.getParameter("begindate"));
			device.setEnddate(req.getParameter("enddate"));
			int allDevice = deviceInfoSer.statisticscount(device);
			// 可用 deviceStatus='可使用' repertoryStatus='入库'
			DeviceInfo kydevices = new DeviceInfo();
			kydevices.setDeviceStatus("可使用");
			kydevices.setRepertoryStatus("入库");
			kydevices.setSysStatus("1");
			kydevices.setBegindate(req.getParameter("begindate"));
			kydevices.setEnddate(req.getParameter("enddate"));
			int kyDevice = deviceInfoSer.statisticscount(kydevices);
			// 出库repertoryStatus='出库'
			DeviceInfo chDevices = new DeviceInfo();
			chDevices.setRepertoryStatus("出库");
			chDevices.setSysStatus("1");
			chDevices.setBegindate(req.getParameter("begindate"));
			chDevices.setEnddate(req.getParameter("enddate"));
			int chDevice = deviceInfoSer.statisticscount(chDevices);// 出货
			// 维修deviceStatus='不可用' AND repertoryStatus='维修' AND sysStatus=1
			DeviceInfo wxDevices = new DeviceInfo();
			wxDevices.setDeviceStatus("不可用");
			wxDevices.setRepertoryStatus("维修");
			wxDevices.setSysStatus("1");
			wxDevices.setBegindate(req.getParameter("begindate"));
			wxDevices.setEnddate(req.getParameter("enddate"));
			int wxDevice = deviceInfoSer.statisticscount(wxDevices);
			// 删除
			DeviceInfo deleDevices = new DeviceInfo();
			deleDevices.setSysStatus("0");
			deleDevices.setBegindate(req.getParameter("begindate"));
			deleDevices.setEnddate(req.getParameter("enddate"));
			int deleDevice = deviceInfoSer.statisticscount(deleDevices);

			// 未完成订单数
			JSONObject object = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			JSONObject obj = new JSONObject();
			obj.put("allDevice", allDevice);
			obj.put("kyDevice", kyDevice);
			obj.put("chDevice", chDevice);
			obj.put("wxDevice", wxDevice);
			obj.put("deleDevice", deleDevice);
			jsonArray.add(obj);
			object.put("data", jsonArray);
			object.put("success", true);
			object.put("totalRows", 1);
			System.out.println(allDevice + " / " + kyDevice + " / " + chDevice + " / " + wxDevice + "  /  " + deleDevice);
			resp.getWriter().print(object.toString());
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
	}



	/**
	 * 修改设备SN
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/updateSN")
	public void updateSN(String oldsn, FlowDealOrders info, HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws IOException
	{

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		info.setModifyUserID(adminUserInfo.getUserID());
		info.setSN(Constants.SNformat(info.getSN().trim()));
		
		DeviceInfo deviceInfo = new DeviceInfo();
		DeviceDealOrders dealOrders = new DeviceDealOrders();

		// 首先判断该设备是否在平台有记录
		DeviceInfo device = deviceInfoSer.getdevicetwo(info.getSN());
		// 没有这个设备
		if (device == null)
		{
			try
			{
				DeviceInfo deviceinfo = new DeviceInfo(UUID.randomUUID().toString(), null, info.getSN(), null, null, null, "更换设备插入", "使用中", adminUserInfo.getUserID(), adminUserInfo.getUserName(),
						DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
				deviceInfoSer.insertDeviceInfotwo(deviceinfo);
				
				// 将流量订单的SN更新输入的SN
				flowDealOrdersSer.updateSN(info);
				
				// 查找流量订单对应的设备订单，设备订单SN更新为输入的SN，
				dealOrders.setModifyUserID(adminUserInfo.getUserID());
				dealOrders.setSN(info.getSN());
				dealOrders.setOrderID(info.getOrderID());
				deviceInfoSer.updateSN(dealOrders);
				
				// 设备状态更新为可使用。
				deviceInfo.setSN(oldsn);
				deviceInfoSer.updateSN_Deviceinfo(deviceInfo);

				// 同步接单记录
				OrdersInfo ordersInfo = ordersInfoSer.getOrdersInfo(info.getOrderID());
				if (info != null && StringUtils.isNotBlank(ordersInfo.getAoID()))
				{
					AcceptOrder a_order = new AcceptOrder();
					a_order.setSN(info.getSN());
					a_order.setAoID(ordersInfo.getAoID());
					if (acceptOrderSer.update(a_order))
					{
						logger.info("同步成功");
					}
					else
					{
						logger.info("同步失败");
					}
				}
				
				try
				{
					AdminOperate admin = new AdminOperate();
					admin.setOperateID(UUID.randomUUID().toString());// id
					admin.setCreatorUserID(adminUserInfo.getUserID());
					admin.setCreatorUserName(adminUserInfo.getUserName());
					admin.setOperateContent("更改设备：已将订单ID为"+info.getFlowDealID()+"的订单，设备修改为"+info.getSN()+"原设备为："+oldsn);
					admin.setOperateMenu("全部流量订单>更改设备");
					admin.setOperateType("修改");
					adminOperateSer.insertdata(admin);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
				resp.getWriter().print("1");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				resp.getWriter().print("0");
				return;
			}
		}
		else
		{
			// 有这个设备
			// 如果这个设备的状态为可使用
			if (!device.getDeviceStatus().equals("可使用"))
			{
				resp.getWriter().print("-1");
				return;
			}
			else
			{
				try
				{
					// 将该设备记录设备状态改为“使用中”
					deviceInfoSer.updateDeviceOrder(info.getSN());
					// 将流量订单的SN更新输入的SN
					flowDealOrdersSer.updateSN(info);
					
					// 查找流量订单对应的设备订单，设备订单SN更新为输入的SN，
					dealOrders.setModifyUserID(adminUserInfo.getUserID());
					dealOrders.setSN(info.getSN());
					dealOrders.setOrderID(info.getOrderID());
					deviceInfoSer.updateSN(dealOrders);
					
					//设备状态更新为可使用
					deviceInfo.setSN(oldsn);
					deviceInfoSer.updateSN_Deviceinfo(deviceInfo);
					
					// 同步接单记录
					OrdersInfo ordersInfo = ordersInfoSer.getOrdersInfo(info.getOrderID());
					if (info != null && StringUtils.isNotBlank(ordersInfo.getAoID()))
					{
						AcceptOrder a_order = new AcceptOrder();
						a_order.setSN(info.getSN());
						a_order.setAoID(ordersInfo.getAoID());
						if (acceptOrderSer.update(a_order))
						{
							logger.info("同步成功");
						}
						else
						{
							logger.info("同步失败");
						}
					}
					
					try
					{
						AdminOperate admin = new AdminOperate();
						admin.setOperateID(UUID.randomUUID().toString());// id
						admin.setCreatorUserID(adminUserInfo.getUserID());
						admin.setCreatorUserName(adminUserInfo.getUserName());
						admin.setOperateContent("更改设备：已将订单ID为"+info.getFlowDealID()+"的订单，设备修改为"+info.getSN()+"原设备为："+oldsn);
						admin.setOperateMenu("全部流量订单>更改设备");
						admin.setOperateType("修改");
						adminOperateSer.insertdata(admin);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					
					resp.getWriter().print("1");
				}
				catch (Exception e)
				{
					e.printStackTrace();
					resp.getWriter().print("0");
				}
			}
		}
	}
	
	/**
	 * @deprecated 获取分页数据
	 * @author jiangxuecheng
	 * @date 2015-12-01
	 * @return
	 */
	@Deprecated
	@RequestMapping("/getpage")
	public void getpage(SearchDTO searchDTO, DeviceInfo deviceInfo, HttpServletResponse response, HttpServletRequest request)
	{

		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), deviceInfo);
		String pagesString = deviceInfoSer.getpageString1(seDto);
		System.out.println(pagesString);
		try
		{
			response.getWriter().println(pagesString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}



	@RequestMapping("/deldistributorID")
	public void deldistributorID(DeviceInfo deviceInfo, HttpServletResponse response) throws IOException
	{

		int hang = deviceInfoSer.deldistributorID(deviceInfo);
		if (hang > 0)
		{
			response.getWriter().print("1");
		}
		else
		{
			response.getWriter().print("0");
		}
	}



	/**
	 * 像北京银行等的特殊客户特殊设备特殊订单
	 * 
	 * @param model
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping("/special")
	public String special(Model model, HttpServletResponse resp, HttpServletRequest req)
	{
		List<Dictionary> orderStatus = dictionarySer.getAllList(Constants.DICT_ORDERINFO_ORDERSTATUS);
		// List<Dictionary> orderSources =
		// dictionarySer.getAllList(Constants.DICT_ORDERINFO_ORDERSOURCE);
		model.addAttribute("OrderStatusDict", orderStatus);
		// model.addAttribute("OrderSourceDict", orderSources);

		// List<Distributor> distributors = distributorSer.getAll("");
		// model.addAttribute("dis", distributors);
		// 查询所有的渠道商

		FlowDealOrders dealOrders = new FlowDealOrders();

		// TODO：暂时默认为北京银行那批的值， 后期扩展时设置为通用的默认值
		dealOrders.setOrderID("10087");
		dealOrders.setUserCountry("中国,460,30.0");
		dealOrders.setFlowDays(3);
		dealOrders.setCustomerName("北京银行");
		dealOrders.setRemark("北京银行赠送大陆3天免费");
		dealOrders.setLimitValve(Constants.LIMITVALUE);
		dealOrders.setLimitSpeed(Constants.LIMITSPEED);
		dealOrders.setOrderType("4");
		model.addAttribute("floworder", dealOrders);

		// 查询所有国家
		List<CountryInfo> countryInfos = countryInfoSer.getAll("");
		model.addAttribute("countrys", countryInfos);

		// 展开菜单设定为全部"数据交易订单"
		model.addAttribute("MenuPath", "/device/special");

		model.addAttribute("excelmessage", req.getSession().getAttribute("excelmessage"));
		model.addAttribute("active", req.getSession().getAttribute("active"));
		model.addAttribute("uploadFileName", req.getSession().getAttribute("uploadFileName"));
		req.getSession().setAttribute("excelmessage", ""); // 及时清空, 避免污染其他页面
		req.getSession().setAttribute("active", ""); // 及时清空, 避免污染其他页面
		req.getSession().setAttribute("uploadFileName", "");

		return "WEB-INF/views/deviceinfo/special";
	}



	/**
	 * 上传特殊设备的SN, 并预先检查使用情况, 是否已创建等等 参考:
	 * {@link com.Manage.control.ExcelIntoData#batchUpdateSN}
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value = "/special/upload", method = RequestMethod.POST)
	public String uploadSnAndCheck(@RequestParam("file") MultipartFile file, String orderID, HttpServletRequest req, HttpServletResponse resp, HttpSession session, Model model)
			throws FileNotFoundException, IOException
	{
		logger.info("得到上传excel请求");
		JSONObject jsonResult = new JSONObject();

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (adminUserInfo == null)
		{
			req.getSession().setAttribute("excelmessage", "请重新登录！");
			req.getSession().setAttribute("active", "tabCreate");
			return "redirect:/device/special";
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH");
		String logoPathDir = "/files" + dateformat.format(new Date());
		// 得到excel保存目录的真实路径
		String temp = request.getSession().getServletContext().getRealPath(logoPathDir);
		// 创建文件保存路径文件夹
		File tempFile = new File(temp);
		logger.info("这个是上传文件的真实路径temp：" + temp);
		MultipartFile multipartFile = multipartRequest.getFile("file");
		if (!tempFile.exists())
		{
			tempFile.mkdirs();
		}
		// 获取文件后缀名
		// String suffix =
		// multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
		// 获取文件的名字 如：SN.xls
		String logExcelName = multipartFile.getOriginalFilename();
		// 拼接文件保存路径+文件名
		String fileName = temp + File.separator + logExcelName;
		File files = new File(fileName);
		try
		{
			multipartFile.transferTo(files);
			logger.info("文件上传成功");
		}
		catch (IOException e)
		{
			logger.info("上传出错：" + e.getMessage());
			req.getSession().setAttribute("excelmessage", "上传出错！请重试或联系管理员");
			req.getSession().setAttribute("active", "tabCreate");
			return "redirect:/device/special";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SS");
		TimeZone t = sdf.getTimeZone();
		t.setRawOffset(0);
		sdf.setTimeZone(t);
		String[][] result = ExcelUtils.getData(files, 1); // 此模板开始一行为标题
		logger.info("成功获取到execl");
		int length = result[0].length;// 表格中有多少列
		int allDatacount = result.length; // 表格中有多少行
		logger.info("成功获取到execl，行：" + allDatacount + "列：" + length + "");
		int insertOKCount = 0; // 成功插入条数
		int invalidSnCount = 0; // 格式不对的SN数目, 例如长度不是15位(可扩展到4位?)
		int orderCount = 0;// 该总订单下订单个数
		int SNCount = 0;
		int SNCountDuplicated = 0; // 已有订单的数量

		String handleErrorString = "";
		String validSnString = "";

		try
		{
			// 获取到一共有多少个sn
			for (int i = 0; i < result.length; i++)
			{
				if (result[i][0].length() == 4)
				{
					result[i][0] = Constants.DICT_DEVICE_SN + result[i][0];
					validSnString += result[i][0] + ",";
					SNCount++;
				}
				else if (result[i][0].length() == 15)
				{ // result[i][0].length() == 15
					validSnString += result[i][0] + ",";
					SNCount++;
				}
				else
				{ // if (result[i][0].length() != 15 && result[i][0].length() !=
					// 4) {

					handleErrorString += result[i][0] + ",";
					invalidSnCount++;
				}
			}

			if (0 == SNCount)
			{
				req.getSession().setAttribute("excelmessage", "Excel中没有数据");
				req.getSession().setAttribute("active", "tabCreate");
				return "/device/special";
			}

			// 获取到该特殊总订单ID下已有的流量订单
			List<FlowDealOrders> orders = flowDealOrdersSer.selectwxOrderSnList(orderID);
			if (null != orders)
			{
				orderCount = orders.size();
			}
			String[] SN = new String[SNCount];

			// 检查是否有重复的SN
			String tempResultDuplicated = "";
			for (int i = 0; i < orderCount; i++)
			{
				for (int j = 0; j < SNCount; j++)
				{
					String SN1 = result[j][0];
					if (SN1.equals(orders.get(i).getSN()))
					{
						// req.getSession().setAttribute("excelmessage",
						// "SN:"+SN1+"重复");
						// req.getSession().setAttribute("orderID", orderID);
						// return "redirect:update";
						if (tempResultDuplicated.indexOf(SN1) < 0)
						{
							tempResultDuplicated += /* "SN:" + */SN1 + "(已有此订单) ";
							SNCountDuplicated++;
						}
					}
				}
			}

			// j是行
			String tempResultDeviceAvailable = "";
			for (int j = 0; j < SNCount; j++)
			{
				// 拿到表格里第j行第一列的单元格里值
				SN[j] = result[j][0];

				// 从前面的“已有此订单”排除
				if (tempResultDuplicated.contains(SN[j]))
				{
					continue;
				}

				// 这里检查设备有没有在使用中
				DeviceInfo deviceInfo = deviceInfoSer.getbysn(SN[j]);
				if (null == deviceInfo)
				{
					tempResultDeviceAvailable += /* "SN:" + */SN[j] + "(查无此设备) ";
				}
				else if ("使用中".equals(deviceInfo.getDeviceStatus()))
				{
					tempResultDeviceAvailable += /* "SN:" + */SN[j] + "(设备使用中) ";
				}
			}

			if (!"".equals(tempResultDeviceAvailable))
			{ // 包括重复和设备使用中和查无此设备的
				req.getSession().setAttribute("excelmessage", "请处理好不符合的设备SN: " + tempResultDeviceAvailable + (StringUtils.isBlank(handleErrorString) ? "" : "<br />SN长度不对: " + handleErrorString));
				req.getSession().setAttribute("active", "tabCreate");
				return "redirect:/device/special";
			}
			else if (!"".equals(handleErrorString))
			{
				req.getSession().setAttribute("excelmessage", "请处理好SN长度不对的设备SN: " + handleErrorString);
				req.getSession().setAttribute("active", "tabCreate");
				return "redirect:/device/special";
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			req.getSession().setAttribute("excelmessage", "操作数据库出错！请重试或联系管理员");
			req.getSession().setAttribute("active", "tabCreate");
			return "redirect:/device/special";
		}

		// AdminOperate admin = new AdminOperate();
		// admin.setOperateID(UUID.randomUUID().toString()); // id
		// admin.setCreatorUserID(adminUserInfo.userID); // 创建人ID
		// admin.setCreatorUserName(adminUserInfo.userName); // 创建人姓名
		// admin.setOperateContent(""); // 操作内容
		// admin.setOperateMenu("设备出入库管理>特殊订单管理"); // 操作菜单
		// admin.setOperateType("查询"); // 操作类型
		// adminOperateSer.insertdata(admin);

		// 全部处理结果
		if (validSnString.length() > 200)
		{
			validSnString = StringUtils.substring(validSnString, 0, 200) + " ..."; // 许多记录时只显示前面一些
		}
		if (SNCount == SNCountDuplicated)
		{
			handleErrorString = "请查看检查结果：Excel档中共有 " + SNCount + " 条有效的SN记录，但全部已有该特殊订单，如下：<br />" + validSnString;
			// 这种情况不需要继续第二步
		}
		else if (SNCountDuplicated > 0)
		{
			handleErrorString = "请查看检查结果：Excel档中共有 " + SNCount + " 条有效的SN记录，如下：<br />" + validSnString + "<br />请继续第二步输入相关创建订单。" + "<br />其中，有 " + SNCountDuplicated + " 条已有该特殊订单，将会被忽略";
			req.getSession().setAttribute("uploadFileName", fileName);
		}
		else
		{
			handleErrorString = "请查看检查结果：Excel档中共有 " + SNCount + " 条有效的SN记录，如下：<br />" + validSnString + "<br />请继续第二步输入相关创建订单";
			req.getSession().setAttribute("uploadFileName", fileName);
		}
		req.getSession().setAttribute("excelmessage", handleErrorString);
		req.getSession().setAttribute("active", "tabCreate");

		return "redirect:/device/special";

	}



	/**
	 * 根据SN列表 excel 档的数据创建订单, 传递入已上传的文档路径
	 * 
	 * @param file
	 * @param orderID
	 * @param req
	 * @param resp
	 * @param session
	 * @param model
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@RequestMapping(value = "/special/create", method = RequestMethod.POST)
	public void createSpecial(String uploadFileName, // @RequestParam("file")
														// String file, String
														// orderID,
			FlowDealOrders flowOrder, HttpServletRequest req, HttpServletResponse resp, HttpSession session, Model model) throws FileNotFoundException, IOException
	{
		logger.info("创建特殊订单");
		JSONObject jsonResult = new JSONObject();

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (adminUserInfo == null)
		{
			jsonResult.put("code", -1);
			jsonResult.put("msg", "请重新登录！");
			resp.getWriter().println(jsonResult.toString());
			return;
		}

		if (StringUtils.isBlank(uploadFileName) || StringUtils.isBlank(flowOrder.getOrderID()) || StringUtils.isBlank(flowOrder.getUserCountry()) // ||
																																					// StringUtils.isBlank(flowOrder.getFlowDays())
				|| StringUtils.isBlank(flowOrder.getPanlUserDate()) || StringUtils.isBlank(flowOrder.getFlowExpireDate()))
		{
			jsonResult.put("code", -2);
			jsonResult.put("msg", "缺少必要参数！");
			resp.getWriter().println(jsonResult.toString());
			return;
		}
		File files = new File(uploadFileName);

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SS");
		TimeZone t = sdf.getTimeZone();
		t.setRawOffset(0);
		sdf.setTimeZone(t);
		String[][] result = ExcelUtils.getData(files, 1); // 此模板开始一行为标题
		logger.info("成功获取到execl");
		int length = result[0].length;// 表格中有多少列
		int allDatacount = result.length; // 表格中有多少行
		logger.info("成功获取到execl，行：" + allDatacount + "列：" + length + "");
		int insertOKCount = 0; // 成功插入条数
		int validSnCount = 0; // 可被正常处理的SN设备个数,那些查不到设备或设备使用中不算作 valid
		int invalidSnCount = 0; // 格式不对的SN数目, 例如长度不是15位(可扩展到支持4位)
		int orderCount = 0;// 该总订单下设备订单个数
		int SNCount = 0; // 表中有效SN的个数

		String handleErrorString = ""; // 记录合法的SN但添加失败
		String validSnString = ""; // 暂不用

		try
		{
			// 获取到一共有多少个sn
			for (int i = 0; i < result.length; i++)
			{
				if (result[i][0].length() == 4)
				{
					result[i][0] = Constants.DICT_DEVICE_SN + result[i][0];
					// validSnString += result[i][0] + ",";
					SNCount++;
				}
				else if (result[i][0].length() == 15)
				{ // result[i][0].length() == 15
					// validSnString += result[i][0] + ",";
					SNCount++;
				}
				else
				{ // if (result[i][0].length() != 15 && result[i][0].length() !=
					// 4) {
					// handleErrorString += result[i][0] + ",";
					invalidSnCount++;
				}
			}

			if (0 == SNCount)
			{
				jsonResult.put("code", -3);
				jsonResult.put("msg", "Excel中没有数据");
				resp.getWriter().println(jsonResult.toString());
				return;
			}

			// 获取到该特殊总订单ID下已有的流量订单
			List<FlowDealOrders> orders = flowDealOrdersSer.selectwxOrderSnList(flowOrder.getOrderID());
			if (null != orders)
			{
				orderCount = orders.size();
			}
			// String[] SN = new String[orderCount];

			String uclist1 = "";
			String[] uc = flowOrder.getUserCountry().split(",");
			for (int i = 0; i < uc.length; i++)
			{
				String[] temp = uc[i].split("-");
				if (i != uc.length - 1)
				{
					uclist1 += temp[0] + "," + temp[1] + "," + temp[2] + "|";
				}
				else
				{
					uclist1 += temp[0] + "," + temp[1] + "," + temp[2];
				}
			}

			for (int j = 0; j < SNCount; j++)
			{
				String SN1 = result[j][0];

				// 检查是否有重复的SN
				Boolean needCreated = true;
				if (null != orders && orderCount > 0)
				{
					for (int i = 0; i < orderCount; i++)
					{
						if (SN1.equals(orders.get(i).getSN()))
						{
							needCreated = false;
							break;
						}
					}
				}

				// 这里检查设备有没有在使用中
				if (needCreated)
				{
					DeviceInfo deviceInfo;
					try
					{
						deviceInfo = deviceInfoSer.getbysn(SN1);
					}
					catch (Exception e)
					{
						logger.info("查询设备的状态出错 SN" + SN1);
						e.printStackTrace();
						deviceInfo = null;
					}
					if (null == deviceInfo)
					{
						needCreated = false;
					}
					else if ("使用中".equals(deviceInfo.getDeviceStatus()))
					{
						needCreated = false;
					}
				}

				// 创建
				if (!needCreated)
				{
					continue; // 显式跳过
				}
				else
				{
					validSnCount++;

					FlowDealOrders insertOrder = new FlowDealOrders();
					insertOrder.setFlowDealID(UUID.randomUUID().toString());
					insertOrder.setOrderID(flowOrder.getOrderID());
					insertOrder.setCustomerID(flowOrder.getOrderID()); // 暂设置为特殊订单总订单ID,后面客户开始使用时设置为实际客户
					if (StringUtils.isBlank(flowOrder.getCustomerName()))
					{
						insertOrder.setCustomerName(flowOrder.getOrderID());
					}
					else
					{
						insertOrder.setCustomerName(flowOrder.getCustomerName());
					}
					insertOrder.setUserCountry(uclist1);
					insertOrder.setOrderStatus("可使用");
					insertOrder.setOrderType(flowOrder.getOrderType());
					insertOrder.setIfFinish("是");
					insertOrder.setFlowDays(flowOrder.getFlowDays());
					insertOrder.setOrderAmount(0);
					// insertOrder.setBeginDate(flowOrder.getBeginDate()); //错！
					insertOrder.setPanlUserDate(flowOrder.getPanlUserDate());
					insertOrder.setFlowExpireDate(StringUtils.substring(flowOrder.getFlowExpireDate(), 0, 10) + " 23:59:59");
					insertOrder.setSN(SN1);
					insertOrder.setIfActivate("是");
					Date nowDate = new Date();
					String nowString = DateUtils.formatDateTime(nowDate);
					insertOrder.setActivateDate(nowString);
					insertOrder.setRemark(flowOrder.getRemark());
					if (flowOrder.getLimitValve() == 0)
					{
						insertOrder.setLimitValve(Constants.LIMITVALUE);
					}
					else
					{
						insertOrder.setLimitValve(flowOrder.getLimitValve());
					}
					if (flowOrder.getLimitSpeed() == 0)
					{
						insertOrder.setLimitSpeed(Constants.LIMITSPEED);
					}
					else
					{
						insertOrder.setLimitSpeed(flowOrder.getLimitSpeed());
					}

					insertOrder.setCreatorUserID(adminUserInfo.getUserID()); // 不使用像10087等id
					insertOrder.setCreatorUserName(adminUserInfo.getUserName());
					insertOrder.setCreatorDate(nowDate);
					insertOrder.setSysStatus(true);

					insertOrder.setOrderCreateDate(nowDate);

					Boolean insertResult = false;
					try
					{
						insertResult = flowDealOrdersSer.insertinfo(insertOrder);
					}
					catch (Exception e)
					{
						logger.info("插入特殊订单出错 SN" + SN1);
						e.printStackTrace();
						insertResult = false;
					}

					if (insertResult)
					{
						// 设备出库 修改为"使用中"，至于是否租赁或购买这里不理会，也不创建设备交易单。
						// TODO： 注意，目前在设备列表中若状态为“使用中”的但没有设备交易单的设备，默认显示“设备交易状态”
						// 为“租赁”，待确认对这种特殊订单的设备是否合适，例如，是否可归还等
						DeviceInfo device = new DeviceInfo();
						device.setDeviceStatus("使用中");
						device.setSN(SN1);
						device.setModifyUserID(adminUserInfo.getUserID());
						device.setModifyDate(DateUtils.getDateTime());

						// TODO: Transactional 处理或者做好这时数据库出错情况处理
						try
						{
							deviceInfoSer.updatedevicestatus(device);
						}
						catch (Exception e)
						{
							logger.info("更新特殊订单设备的状态出错 SN" + SN1);
							e.printStackTrace();
						}

						insertOKCount++;
					}
					else
					{
						handleErrorString += SN1 + ",";
					}
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			jsonResult.put("code", 0);
			jsonResult.put("msg", "操作数据库出错！请重试或联系管理员");
			// jsonResult.put("validSnString", validSnString);
			resp.getWriter().println(jsonResult.toString());
			return;
		}

		AdminOperate admin = new AdminOperate();
		admin.setOperateID(UUID.randomUUID().toString()); // id
		admin.setCreatorUserID(adminUserInfo.userID); // 创建人ID
		admin.setCreatorUserName(adminUserInfo.userName); // 创建人姓名
		admin.setOperateContent(""); // 操作内容
		admin.setOperateMenu("设备出入库管理>特殊订单管理"); // 操作菜单
		admin.setOperateType("查询"); // 操作类型
		adminOperateSer.insertdata(admin);

		String resultString = "处理结束, 表中共有  " + SNCount + " 条设备SN记录, 其中 " + validSnCount + " 个待添加设备" + "\n成功创建了 " + insertOKCount + " 个流量套餐";
		if (validSnCount != insertOKCount)
		{
			resultString += ", 但有 " + (validSnCount - insertOKCount) + " 个SN记录创建套餐时数据库操作失败, 出错SN如下: " + handleErrorString;
		}
		jsonResult.put("code", 0);
		jsonResult.put("msg", resultString);
		resp.getWriter().println(jsonResult.toString());
	}



	/**
	 * 进入设备升级配置页面
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/deviceupgrade")
	public String deviceUpgrade(HttpServletRequest req)
	{
		return "WEB-INF/views/deviceinfo/deviceupgrading";
	}



	/**
	 * AJAX 列表升级配置记录
	 * 
	 * @param searchDTO
	 * @param upgrading
	 * @param req
	 * @param resp
	 */
	@RequestMapping("/deviceupgradelist")
	public void deviceUpgradeList(SearchDTO searchDTO, DeviceUpgrading upgrading, HttpServletRequest req, HttpServletResponse resp)
	{
		SearchDTO dto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), upgrading);
		String pagesString = deviceInfoSer.getUpgradePage(dto);

		System.out.println(pagesString);
		try
		{
			resp.getWriter().println(pagesString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}
	/**
	 * 获取后台设备升级配置记录 文件名集合
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("/deviceupgradeFile")
	@ResponseBody
	public Object deviceupgradeFile(HttpServletRequest req, HttpServletResponse resp){
		String sn=req.getParameter("sn");
		String result="";
		if(StringUtils.isEmpty(sn)){
			return 0;
		}else{
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("sn", sn);
			params.put("ifUpdated",0);
			List<DeviceUpgrading> li=deviceInfoSer.getUpgrade(params);
			if(CollectionUtils.isEmpty(li)){
				return 1;
			}else{
				for(DeviceUpgrading u:li){
					result+=u.getUpgradeFileType()+",";
				}
				//去重
				String[] resultStr=result.substring(0, result.length()-1).split(",");
				result="";
				List<String> resultlist = new ArrayList<>();  
				resultlist.add(resultStr[0]);  
				for(int i=1;i<resultStr.length;i++){  
				    if(resultlist.toString().indexOf(resultStr[i]) == -1){  
				    	resultlist.add(resultStr[i]); 
				    	result+=resultStr[i]+(i==(resultStr.length-1) ? "":",");
				    }  
				}
				return result;
			}
		}
	}
	@RequestMapping("/deviceupgradeFileUpdate")
	@ResponseBody
	public Object deviceupgradeFileUpdate(HttpServletRequest req, HttpServletResponse resp){
		int count=0;
		String sn=req.getParameter("sn");
		if(StringUtils.isBlank(sn)){
			return 0;
		}else{
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("sn", sn);
			params.put("ifUpdated",0);
			List<DeviceUpgrading> li=deviceInfoSer.getUpgrade(params);
			if(CollectionUtils.isEmpty(li)){
				return 0;
			}else{
				for(DeviceUpgrading u:li){
					u.setIfUpdated(true);
					u.setUpdateDate("now()");
					deviceInfoSer.updateUpgrade(u);
					count++;
				}
				return count;
			}
		}
	}
	
	/**
	 * 新增设备更新配置信息
	 * 
	 * ahming notes: 20160415 发现之前的更新规则不明确。现在在这里文档说明并更新： 1. 对于已升级即 ifUpdate
	 * 为1，新增一个SN与这个相同时，则这个记录不会被覆盖，即作为升级历史。 2. 对于未升级ifUpdate 为0的，若当前添加的SN相同，则应该覆盖
	 * 
	 * @param upgrading
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping("/insertdeviceupgrade")
	public void insertDeviceUpgrade(DeviceUpgrading upgrading, HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		try
		{
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			JSONObject jsonResult = new JSONObject();
			AdminUserInfo adminUserInfo =null; 
			//app升级不需要验证用户
			if(upgrading.getUpdateSource()!=2){
			    adminUserInfo= (AdminUserInfo) getSession().getAttribute("User");
				if (adminUserInfo == null)
				{
					jsonResult.put("code", -1);
					jsonResult.put("msg", "请重新登录！");
					response.getWriter().println(jsonResult.toString());
					return;
				}   
			}

			int count = 0;

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
						upgrading.setCreatorUserID(adminUserInfo.getUserID());
						upgrading.setCreatorUserName(adminUserInfo.getUserName());
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
						upgrading1.setModifyUserID(adminUserInfo.getUserID());
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
					upgrading.setCreatorUserID(adminUserInfo.getUserID());
					upgrading.setCreatorUserName(adminUserInfo.getUserName());
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
					upgrading1.setModifyUserID(adminUserInfo.getUserID());
					upgrading1.setModifyDate(DateUtils.formatDate(new Date()));
					if (deviceInfoSer.updateUpgrade(upgrading1))
					{
						count++;
					}
				}
			}
			response.getWriter().print(count + "");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}



	@RequestMapping("/insertdeviceupgrade2")
	public String insertDeviceUpgrade2(@RequestParam("file") MultipartFile file, Model model, DeviceUpgrading upgrading, HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		CommonsMultipartFile cf = (CommonsMultipartFile) file;
		DiskFileItem fi = (DiskFileItem) cf.getFileItem();
		File files = fi.getStoreLocation();
		String[][] result = ExcelUtils.getData(files, 1); // 此模板开始一行为标题
		logger.info("成功获取到execl");
		int allColomn = result[0].length;
		int allRow = result.length;
		logger.info("成功获取到execl，行：" + allRow + "列：" + allColomn + "");
		int insertOKCount = 0; // 成功插入的总行数
		for (int j = 0; j < result[0].length; j++)
		{
			for (int i = 0; i < result.length; i++)
			{
				if ("".equals(result[i][j].trim()) || (result[i][j].trim().length() != 15 && result[i][j].trim().length() != 6))
				{
					continue;
				}
				DeviceUpgrading upgrading1 = deviceInfoSer.getNotUpdatedBySN(Constants.SNformat(result[i][j]));
				if (upgrading1 == null)
				{
					upgrading.setDeviceUpgradingID(getUUID());
					upgrading.setSysStatus(true);
					upgrading.setIfUpdated(false);
					upgrading.setCreatorUserID(adminUserInfo.getUserID());
					upgrading.setCreatorUserName(adminUserInfo.getUserName());
					upgrading.setCreatorDate(DateUtils.formatDate(new Date()));
					upgrading.setSN(Constants.SNformat(result[i][j]));
					if (deviceInfoSer.insert(upgrading))
					{
						insertOKCount++;
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
					upgrading1.setModifyUserID(adminUserInfo.getUserID());
					upgrading1.setModifyDate(DateUtils.formatDate(new Date()));
					if (deviceInfoSer.updateUpgrade(upgrading1))
					{
						insertOKCount++;
					}
					
//					if (upgrading.getUpgradeFileType().equals(upgrading1.getUpgradeFileType()))
//					{
//						upgrading1.setSysStatus(true);
//						upgrading1.setIfUpdated(false);
//						upgrading1.setSN(Constants.SNformat(result[i][j]));
//						upgrading1.setIfForcedToUpgrade(upgrading.getIfForcedToUpgrade());
//						upgrading1.setUpgradeFileType(upgrading.getUpgradeFileType());
//						upgrading1.setRemark(upgrading.getRemark());
//						upgrading1.setModifyUserID(adminUserInfo.getUserID());
//						upgrading1.setModifyDate(DateUtils.formatDate(new Date()));
//						if (deviceInfoSer.updateUpgrade(upgrading1))
//						{
//							insertOKCount++;
//						}
//					}
//					else
//					{
//						upgrading.setDeviceUpgradingID(getUUID());
//						upgrading.setSysStatus(true);
//						upgrading.setIfUpdated(false);
//						upgrading.setCreatorUserID(adminUserInfo.getUserID());
//						upgrading.setCreatorUserName(adminUserInfo.getUserName());
//						upgrading.setCreatorDate(DateUtils.formatDate(new Date()));
//						upgrading.setSN(Constants.SNformat(result[i][j]));
//						if (deviceInfoSer.insert(upgrading))
//						{
//							insertOKCount++;
//						}
//					}
				}
			}
		}
		model.addAttribute("insertOKCount", insertOKCount);
		return "WEB-INF/views/deviceinfo/deviceupgrading";
	}



	/**
	 * 删除设备更新配置信息
	 * 
	 * @param deviceUpgradingID
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/deletedeviceupgrade")
	public void deleteDeviceUpgrade(String deviceUpgradingID, HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonResult = new JSONObject();

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (adminUserInfo == null)
		{
			jsonResult.put("code", -1);
			jsonResult.put("msg", "请重新登录！");
			response.getWriter().println(jsonResult.toString());
			return;
		}

		String[] ids = deviceUpgradingID.split(",");
		List<String> list = new ArrayList<>();
		for (int i = 0; i < ids.length; i++)
		{
			list.add(ids[i]);
		}

		boolean result = deviceInfoSer.updateUpdatingStatus(list);

		if (result)
		{
			logger.debug("设备更新配置信息删除成功");
			try
			{
				jsonResult.put("code", 0);
				jsonResult.put("msg", "成功删除设备更新配置信息!");
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
				admin.setOperateID(UUID.randomUUID().toString());// id
				admin.setCreatorDate(DateUtils.formatDate(new Date()));// 创建时间
				admin.setCreatorUserID(adminUserInfo.getUserID());// 创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());// 创建人姓名
				admin.setOperateDate(DateUtils.formatDate(new Date()));// 操作时间
				admin.setSysStatus(1);
				admin.setOperateContent("删除了设备更新配置信息, 记录ID为: " + deviceUpgradingID); // 操作内容
				admin.setOperateMenu("设备出入库管理>删除设备更新配置"); // 操作菜单
				admin.setOperateType("删除");// 操作类型
				adminOperateSer.insertdata(admin);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			logger.debug("设备更新配置信息删除失败");
			try
			{
				jsonResult.put("code", 1);
				jsonResult.put("msg", "删除设备更新配置信息出错, 请重试!");
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
	 * 根据SN获取设备更新信息
	 * 
	 * @param SN
	 * @param request
	 * @param response
	 */
	@RequestMapping("/upgradeBySN")
	public void getBySN(String SN, HttpServletRequest request, HttpServletResponse response)
	{

		DeviceUpgrading upgrading = deviceInfoSer.getNotUpdatedBySN(SN);

		JSONObject result = new JSONObject();
		JSONObject jo = new JSONObject();

		if (upgrading != null && !upgrading.getIfUpdated())
		{
			// ahming notes: 必须返回ID作为唯一性版本号。因为目前不使用模块自身的版本号，若升级配置的记录发生改变，那么
			// 这个唯一标识必须改变。例如，前后两次要升级 console.war ，若不判断唯一标识，调用者不清楚具体情况，难以判断，
			// 也无法优化，同时，在配置时，要确保一个设备只有一条有效的配置记录，并灵活覆盖，不需要先删除再新建等等
			jo.put("deviceUpgradingID", upgrading.getDeviceUpgradingID());
			jo.put("SN", upgrading.getSN());
			jo.put("ifForcedToUpgrade", upgrading.getIfForcedToUpgrade());
			jo.put("upgradeFileType", upgrading.getUpgradeFileType());
			result.put("code", 1);
			result.put("data", jo.toString());
		}
		else
		{
			result.put("code", 0);
			result.put("msg", "无需升级");
		}

		try
		{
			response.getWriter().write(result.toString());
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			e.printStackTrace();
		}

	}



	/**
	 * 获取所有设备更新信息
	 * 
	 * @param SN
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getUpgradeFileName")
	public void getFileName(HttpServletRequest request, HttpServletResponse response)
	{

		JSONObject jo = new JSONObject();
		jo.put("fileName", Constants.getConfig("upgradeFileName"));
		System.out.println(jo.toString());
		try
		{
			response.getWriter().write(jo.toString());
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}



	/**
	 * 更新设备配置信息(设置更新状态为已更新)
	 * 
	 * @param deviceUpgradingID
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/updateUpgradeSuccess")
	public void updateUpgradeSuccess(String SN,String type,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException
	{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonResult = new JSONObject();
		boolean result=false;
		if(StringUtils.isNotEmpty(type)){
		    type=UploadFilepathEnum.getNameByCode(Integer.parseInt(type));
		    result = deviceInfoSer.updateUpgradeSuccessForPortal(SN,type);
		}else{
		    result = deviceInfoSer.updateUpgradeSuccess(SN);   
		}
		if (result)
		{
			logger.debug("设备更新配置信息更新成功");

			try
			{

				jsonResult.put("code", 0);
				jsonResult.put("msg", "成功更新设备更新配置信息!");
				response.getWriter().println(jsonResult.toString());
			}
			catch (IOException e)
			{
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

		}
		else
		{

			logger.debug("设备更新配置信息更新失败");
			try
			{
				jsonResult.put("code", 1);
				jsonResult.put("msg", "更新设备更新配置信息出错, 请重试!");
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
	 * 添加工厂测试记录
	 * 
	 * @param DeviceTestString
	 * @param request
	 * @param response
	 */
	@RequestMapping("/saveFactoryTestInfo")
	public void saveFactoryTestInfo(String DeviceTestString, HttpServletRequest request, HttpServletResponse response)
	{

		JSONObject jsonResult = new JSONObject();

		if (com.Manage.common.util.StringUtils.isBlank(DeviceTestString))
		{

			jsonResult.put("code", 1);
			jsonResult.put("msg", "参数为空");
			try
			{
				response.getWriter().write(jsonResult.toString());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			return;

		}
		else
		{

			try
			{
				JSONObject jsonInfo = JSONObject.fromObject(DeviceTestString);
				FactoryTestInfo factoryTestInfo = new FactoryTestInfo();

				if (jsonInfo.get("SSID") != null)
				{
					factoryTestInfo.setSSID(jsonInfo.get("SSID").toString());
				}
				if (jsonInfo.get("roam_appVersionNum") != null)
				{
					factoryTestInfo.setRoam_appVersionNum(jsonInfo.get("roam_appVersionNum").toString());
				}
				if (jsonInfo.get("roam_apkVersionNum") != null)
				{
					factoryTestInfo.setRoam_apkVersionNum(jsonInfo.get("roam_apkVersionNum").toString());
				}
				if (jsonInfo.get("roam_IMEI") != null)
				{
					factoryTestInfo.setRoam_IMEI(jsonInfo.get("roam_IMEI").toString());
				}
				if (jsonInfo.get("roam_SN") != null)
				{
					factoryTestInfo.setRoam_SN(jsonInfo.get("roam_SN").toString());
				}
				if (jsonInfo.get("roam_ICCID") != null)
				{
					factoryTestInfo.setRoam_ICCID(jsonInfo.get("roam_ICCID").toString());
				}
				if (jsonInfo.get("roam_SIMStatus") != null)
				{
					factoryTestInfo.setRoam_SIMStatus(jsonInfo.get("roam_SIMStatus").toString());
				}
				if (jsonInfo.get("roam_calibrationMark") != null)
				{
					factoryTestInfo.setRoam_calibrationMark(jsonInfo.get("roam_calibrationMark").toString());
				}
				if (jsonInfo.get("roam_networkType") != null)
				{
					factoryTestInfo.setRoam_networkType(jsonInfo.get("roam_networkType").toString());
				}
				if (jsonInfo.get("roam_networkStrength") != null)
				{
					factoryTestInfo.setRoam_networkStrength(jsonInfo.get("roam_networkStrength").toString());
				}
				if (jsonInfo.get("roam_dataConnectionStatus") != null)
				{
					factoryTestInfo.setRoam_dataConnectionStatus(jsonInfo.get("roam_dataConnectionStatus").toString());
				}
				if (jsonInfo.get("local_wifiPwd") != null)
				{
					factoryTestInfo.setLocal_wifiPwd(jsonInfo.get("local_wifiPwd").toString());
				}
				if (jsonInfo.get("local_appVersionNum") != null)
				{
					factoryTestInfo.setLocal_appVersionNum(jsonInfo.get("local_appVersionNum").toString());
				}
				if (jsonInfo.get("local_apkVersionNum") != null)
				{
					factoryTestInfo.setLocal_apkVersionNum(jsonInfo.get("local_apkVersionNum").toString());
				}
				if (jsonInfo.get("local_IMEI") != null)
				{
					factoryTestInfo.setLocal_IMEI(jsonInfo.get("local_IMEI").toString());
				}
				if (jsonInfo.get("local_SN") != null)
				{
					factoryTestInfo.setLocal_SN(jsonInfo.get("local_SN").toString());
				}
				if (jsonInfo.get("local_ICCID") != null)
				{
					factoryTestInfo.setLocal_ICCID(jsonInfo.get("local_ICCID").toString());
				}
				if (jsonInfo.get("local_SIMStatus") != null)
				{
					factoryTestInfo.setLocal_SIMStatus(jsonInfo.get("local_SIMStatus").toString());
				}
				if (jsonInfo.get("local_calibrationMark") != null)
				{
					factoryTestInfo.setLocal_calibrationMark(jsonInfo.get("local_calibrationMark").toString().replace(" ", ""));
				}
				if (jsonInfo.get("local_networkType") != null)
				{
					factoryTestInfo.setLocal_networkType(jsonInfo.get("local_networkType").toString());
				}
				if (jsonInfo.get("local_networkStrength") != null)
				{
					factoryTestInfo.setLocal_networkStrength(jsonInfo.get("local_networkStrength").toString());
				}
				if (jsonInfo.get("local_dataConnectionStatus") != null)
				{
					factoryTestInfo.setLocal_dataConnectionStatus(jsonInfo.get("local_dataConnectionStatus").toString());
				}
				if (jsonInfo.get("local_serialComTest") != null)
				{
					factoryTestInfo.setLocal_serialComTest(jsonInfo.get("local_serialComTest").toString());
				}
				if (jsonInfo.get("result") != null)
				{
					factoryTestInfo.setResult("成功");
				}
				if (jsonInfo.get("installCard") != null)
				{
					factoryTestInfo.setInstallCard(jsonInfo.get("installCard").toString());
				}
				if (jsonInfo.get("gps") != null)
				{
					factoryTestInfo.setGps(jsonInfo.get("gps").toString());
				}
				if (jsonInfo.get("power") != null)
				{
					factoryTestInfo.setPower(jsonInfo.get("power").toString());
				}
				factoryTestInfo.setUpload_networkedTestResult("PASS");
				factoryTestInfo.setSysStatus(true);
				factoryTestInfo.setCreatorDate("now");

				if (deviceInfoSer.insertFactory(factoryTestInfo))
				{
				    	//修改设备的软件版本
				    try{
					DeviceInfo deviceInfo=new DeviceInfo();
				    	deviceInfo.setSN(factoryTestInfo.getLocal_SN());
				    	deviceInfo.setFirmWareVer(factoryTestInfo.getRoam_appVersionNum());
				    	deviceInfo.setFirmWareAPKVer(factoryTestInfo.getRoam_apkVersionNum());
				    	deviceInfo.setVersion(factoryTestInfo.getLocal_appVersionNum());
				    	deviceInfo.setVersionAPK(factoryTestInfo.getLocal_apkVersionNum());
				    	deviceInfoSer.updateVersionBySN(deviceInfo);
				    }catch (Exception e) {
					e.printStackTrace();
				    }
				    	
					jsonResult.put("code", 0);
					jsonResult.put("msg", "成功");
					try
					{
						response.getWriter().write(jsonResult.toString());
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
			catch (Exception e)
			{
				jsonResult.put("code", 2);
				jsonResult.put("msg", "参数不是JSON格式");
				try
				{
					response.getWriter().write(jsonResult.toString());
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
	}



	/**
	 * 效验测试工厂数据 SN-----> 003621
	 * 
	 * @throws IOException
	 * 
	 *             返回数据格式json
	 */
	@RequestMapping("/verifyFactoryTestInfo")
	public void verifyFactoryTestInfo(String SN, HttpServletResponse response) throws IOException
	{
		PrintWriter out = response.getWriter();
		JSONObject object = new JSONObject();

		// 校验参数
		if (StringUtils.isBlank(SN))
		{
			object.put("Code", "01");
			object.put("Msg", "参数效 验失败,请确认参数");
			out.print(object.toString());
			return;
		}
		else
		{
			if (SN.length() == 6)
			{
				SN = Constants.SNformat(SN);
			}
			else if (SN.length() == 15)
			{

			}
			else
			{
				object.put("Code", "01");
				object.put("Msg", "参数效 验失败,请确认参数");
				out.print(object.toString());
				return;
			}
		}

		// 查询最新的记录
		try
		{
			FactoryTestInfo factoryTestInfo = new FactoryTestInfo();
			factoryTestInfo.setSSID("0");
			factoryTestInfo.setSN(SN);
			SearchDTO seDto = new SearchDTO(1, 1, "creatorDate", "desc", factoryTestInfo);
			String jsonString = deviceInfoSer.getFactoryPage(seDto);

			JSONObject resultJosn = JSONObject.fromObject(jsonString);
			String resultString = resultJosn.get("data").toString();
			if (resultString.length() == 2)
			{
				object.put("Code", "03");
				object.put("Msg", "查询结果为空");
				out.print(object.toString());
				return;
			}

			JSONArray ja = JSONArray.fromObject(resultString);

			String creatorDate = JSONObject.fromObject(ja.getString(0)).getString("creatorDate");

			Long temp1 = Long.parseLong(DateUtils.dateToTimeStamp(creatorDate, "yyyy-MM-dd HH:mm:ss")) / 60;

			Long temp2 = new Date().getTime() / 1000 / 60;

			if (temp2 - temp1 > 60)
			{
				object.put("Code", "04");
				object.put("Msg", "最近1小时无数据需要校验");
				out.print(object.toString());
				return;
			}

			resultString = ja.get(0).toString();

			object.put("Code", "00");
			object.put("Msg", "success");
			object.put("data", resultString);
			out.print(object.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			object.put("Code", "02");
			object.put("Msg", "查询失败");
			out.print(object.toString());
		}

	}



	/*
	 * public static void main(String[] args) { //2016-04-15
	 * 14:23:23.0//1460701403
	 * 
	 * System.out.println();
	 * 
	 * FactoryTestInfo factoryTestInfo = new FactoryTestInfo();
	 * factoryTestInfo.setSSID("0"); factoryTestInfo.setSN(SN); SearchDTO seDto
	 * = new SearchDTO(1, 1, "creatorDate", "desc", factoryTestInfo); String
	 * jsonString = deviceInfoSer.getFactoryPage(seDto);
	 * 
	 * JSONObject resultJosn = JSONObject.fromObject(jsonString); String
	 * resultString = resultJosn.get("data").toString();
	 * 
	 * JSONArray ja = JSONArray.fromObject(resultString);
	 * 
	 * resultString = ja.get(0).toString(); out.print(resultString); }
	 */

	@RequestMapping("/factorytestindex")
	public String factoryTestIndex(HttpServletResponse response, Model model)
	{

		response.setContentType("text/html;charset=utf-8");

		return "WEB-INF/views/deviceinfo/factorytestinfo_list";
	}



	@RequestMapping("/factorypage")
	public void getFactoryPage(SearchDTO searchDTO, FactoryTestInfo factoryTestInfo, HttpServletResponse response)
	{
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), factoryTestInfo);
		String jsonString = deviceInfoSer.getFactoryPage(seDto);
		try
		{
			System.out.println(jsonString);
			response.getWriter().println(jsonString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}



	/**
	 * 导出
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/exportfactory")
	public void exportFactoryTestInfo(FactoryTestInfo factoryTestInfo, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException
	{

		request.setCharacterEncoding("utf-8");

		if (factoryTestInfo.getSN() != null)
		{
			factoryTestInfo.setSN(new String(factoryTestInfo.getSN().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (factoryTestInfo.getResult() != null)
		{
			factoryTestInfo.setResult(new String(factoryTestInfo.getResult().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (factoryTestInfo.getBegainTime() != null)
		{
			factoryTestInfo.setBegainTime(new String(factoryTestInfo.getBegainTime().trim().getBytes("ISO-8859-1"), "utf-8"));
		}
		if (factoryTestInfo.getEndTime() != null)
		{
			factoryTestInfo.setEndTime(new String(factoryTestInfo.getEndTime().trim().getBytes("ISO-8859-1"), "utf-8"));
		}

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		sheet.setColumnWidth((short) 0, (short) 5000);
		sheet.setColumnWidth((short) 1, (short) 5000);
		sheet.setColumnWidth((short) 2, (short) 5000);
		sheet.setColumnWidth((short) 3, (short) 5000);
		sheet.setColumnWidth((short) 4, (short) 5000);
		sheet.setColumnWidth((short) 5, (short) 5000);
		sheet.setColumnWidth((short) 6, (short) 5000);
		sheet.setColumnWidth((short) 7, (short) 5000);
		sheet.setColumnWidth((short) 8, (short) 5000);
		sheet.setColumnWidth((short) 9, (short) 5000);
		sheet.setColumnWidth((short) 10, (short) 5000);
		sheet.setColumnWidth((short) 11, (short) 5000);
		sheet.setColumnWidth((short) 12, (short) 5000);
		sheet.setColumnWidth((short) 13, (short) 5000);
		sheet.setColumnWidth((short) 14, (short) 5000);
		sheet.setColumnWidth((short) 15, (short) 5000);
		sheet.setColumnWidth((short) 16, (short) 5000);
		sheet.setColumnWidth((short) 17, (short) 5000);
		sheet.setColumnWidth((short) 18, (short) 5000);
		sheet.setColumnWidth((short) 19, (short) 5000);
		sheet.setColumnWidth((short) 20, (short) 5000);
		sheet.setColumnWidth((short) 21, (short) 5000);
		sheet.setColumnWidth((short) 22, (short) 5000);
		sheet.setColumnWidth((short) 23, (short) 5000);
		sheet.setColumnWidth((short) 24, (short) 5000);
		sheet.setColumnWidth((short) 25, (short) 5000);
		sheet.setColumnWidth((short) 26, (short) 5000);
		sheet.setColumnWidth((short) 27, (short) 5000);
		sheet.setColumnWidth((short) 28, (short) 5000);

		HSSFRow row = sheet.createRow(0);
		row.setHeight((short) 500);

		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setFont(font);
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCellStyle style = wb.createCellStyle();
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("当前SSID");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 1);
		cell.setCellValue("漫游-应用版本号");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 2);
		cell.setCellValue("漫游-APK版本号");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 3);
		cell.setCellValue("漫游-IMEI");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 4);
		cell.setCellValue("漫游-SN");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 5);
		cell.setCellValue("漫游-ICCID");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 6);
		cell.setCellValue("漫游-SIM卡状态");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 7);
		cell.setCellValue("漫游-校准标志位");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 8);
		cell.setCellValue("漫游-网络类型");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 9);
		cell.setCellValue("漫游-网络强度 ");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 10);
		cell.setCellValue("漫游-数据连接状态");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 11);
		cell.setCellValue("本地-WIFI PWD");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 12);
		cell.setCellValue("本地-应用版本号");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 13);
		cell.setCellValue("本地-APK版本号");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 14);
		cell.setCellValue("本地-IMEI");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 15);
		cell.setCellValue("本地-SN");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 16);
		cell.setCellValue("本地-ICCID");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 17);
		cell.setCellValue("本地-SIM卡状态");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 18);
		cell.setCellValue("本地-校准标志位");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 19);
		cell.setCellValue("本地-网络类型");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 20);
		cell.setCellValue("本地-网络强度 ");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 21);
		cell.setCellValue("本地-数据连接状态");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 22);
		cell.setCellValue("本地-串口通信测试");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 23);
		cell.setCellValue("插卡");
		cell.setCellStyle(style1);
		
		cell = row.createCell((short) 24);
		cell.setCellValue("GPS");
		cell.setCellStyle(style1);
		
		cell = row.createCell((short) 25);
		cell.setCellValue("电量");
		cell.setCellStyle(style1);
		
		cell = row.createCell((short) 26);
		cell.setCellValue("上传-联网测试结果");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 27);
		cell.setCellValue("结果");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 28);
		cell.setCellValue("创建时间");
		cell.setCellStyle(style1);
		
		
		
		List<FactoryTestInfo> list = null;
		if (com.Manage.common.util.StringUtils.isNotBlank(factoryTestInfo.getDistinct()) && factoryTestInfo.getDistinct().equals("yes"))
		{
			list = deviceInfoSer.selectDistinctFactoryExport(factoryTestInfo);
		}
		else
		{
			list = deviceInfoSer.selectFactoryExport(factoryTestInfo);
		}

		for (int i = 0; i < list.size(); i++)
		{

			FactoryTestInfo ft = list.get(i);
			row = sheet.createRow(i + 1);
			row.setHeight((short) 500);

			HSSFCell cell1 = row.createCell((short) 0);
			cell1.setCellValue(ft.getSSID());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 1);
			cell1.setCellValue(ft.getRoam_appVersionNum());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 2);
			cell1.setCellValue(ft.getRoam_apkVersionNum());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 3);
			cell1.setCellValue(ft.getRoam_IMEI());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 4);
			cell1.setCellValue(ft.getRoam_SN());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 5);
			cell1.setCellValue(ft.getRoam_ICCID());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 6);
			cell1.setCellValue(ft.getRoam_SIMStatus());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 7);
			cell1.setCellValue(ft.getRoam_calibrationMark());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 8);
			cell1.setCellValue(ft.getRoam_networkType());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 9);
			cell1.setCellValue(ft.getRoam_networkStrength());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 10);
			cell1.setCellValue(ft.getRoam_dataConnectionStatus());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 11);
			cell1.setCellValue(ft.getLocal_wifiPwd());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 12);
			cell1.setCellValue(ft.getLocal_appVersionNum());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 13);
			cell1.setCellValue(ft.getLocal_apkVersionNum());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 14);
			cell1.setCellValue(ft.getLocal_IMEI());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 15);
			cell1.setCellValue(ft.getLocal_SN());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 16);
			cell1.setCellValue(ft.getLocal_ICCID());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 17);
			cell1.setCellValue(ft.getLocal_SIMStatus());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 18);
			cell1.setCellValue(ft.getLocal_calibrationMark());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 19);
			cell1.setCellValue(ft.getLocal_networkType());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 20);
			cell1.setCellValue(ft.getLocal_networkStrength());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 21);
			cell1.setCellValue(ft.getLocal_dataConnectionStatus());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 22);
			cell1.setCellValue(ft.getLocal_serialComTest());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 23);
			cell1.setCellValue(ft.getInstallCard());
			cell1.setCellStyle(style);
			
			cell1 = row.createCell((short) 24);
			cell1.setCellValue(ft.getGps());
			cell1.setCellStyle(style);
			
			cell1 = row.createCell((short) 25);
			cell1.setCellValue(ft.getPower());
			cell1.setCellStyle(style);
			
			cell1 = row.createCell((short) 26);
			cell1.setCellValue(ft.getUpload_networkedTestResult());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 27);
			cell1.setCellValue(ft.getResult());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 28);
			cell1.setCellValue(ft.getCreatorDate());
			cell1.setCellStyle(style);
			
			
		}

		DownLoadUtil.execlExpoprtDownload("工厂测试记录.xls", wb, request, response);
	}



	// 出库管理
	@RequestMapping("/outboundDevice")
	public String getoutDevice()
	{
		return "WEB-INF/views/deviceinfo/deviceInfo_outboundDevice";
	}



	/**
	 * 可出库的设备查询
	 */
	@RequestMapping("/chukuDevice")
	public void chukuDevice(SearchDTO searchDTO, DeviceInfo deviceinfo, HttpServletResponse response)
	{
		response.setContentType("application/json;charset=UTF-8");

		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), deviceinfo);
		String jsonString = deviceInfoSer.getkeyongdevicepageString(seDto);
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
	 * 可入库的设备列表
	 * 
	 * @param searchDTO
	 * @param deviceinfo
	 * @param response
	 */
	@RequestMapping("/rukuDevice")
	public void rukuDevice(SearchDTO searchDTO, DeviceInfo deviceinfo, HttpServletResponse response)
	{
		response.setContentType("application/json;charset=UTF-8");

		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), deviceinfo);
		String jsonString = deviceInfoSer.getrukudevicepageString(seDto);
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
	 * 选择SN入库
	 * 
	 * @param req
	 * @param rep
	 * @throws IOException
	 */
	@RequestMapping("/getInDevice")
	public void getInDevice(String SN, HttpServletRequest req, HttpServletResponse rep) throws IOException
	{

		IdentityHashMap<String, String> snMap = new IdentityHashMap<String, String>();
		List<String> snlist = new ArrayList<String>();
		String[] arr = SN.trim().split("/");
		snlist = Arrays.asList(arr);
		int rukuOKCount = 0;
		for (int i = 0; i < snlist.size(); i++)
		{

			int count = deviceInfoSer.updateDevice_ruku(Constants.SNformat(snlist.get(i)));
			if (count > 0)
			{
				logger.info("修改设备状态成功");
				Shipment shipm = DeviceLogShipment(Constants.SNformat(snlist.get(i)), "", "0", "", "", "入库", "", "", "", "");
				boolean shipmres = ShipmentSer.insert(shipm);
				if (shipmres)
				{
					logger.info("插入出入库记录成功！！！");
					rukuOKCount++;
				}
				else
				{
					logger.info("插入出入库记录失败！！！");
					snMap.put(new String(snlist.get(i)), " 设备状态修改成功，但插入出入库记录失败");
				}
			}
			else
			{
				logger.info("修改设备状态失败");
				snMap.put(new String(snlist.get(i)), "请确认设备是否存在，设备是否为出库");
			}

		}
		if (snMap.size() == 0)
		{
			rep.getWriter().println("0");
		}
		else
		{
			String message = "需要入库" + snlist.size() + "个， 　成功入库" + rukuOKCount + "条，　入库失败" + snMap.size() + "条,　　以下为操作提示信息<br/>";
			String mess = "";
			for (String key : snMap.keySet())
			{
				mess = mess + key + ":　" + snMap.get(key) + "<br/>";
			}
			message = message + mess;
			rep.getWriter().println(message);

		}
	}



	/**
	 * excel批量入库
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@RequestMapping("/excelInDevice")
	public String excelInDevice(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws FileNotFoundException, IOException
	{

		logger.info("..导入订单得到请求..");
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
			return "redirect:returnbatchorder";

		}
		int allDatacount = result.length;
		if (allDatacount == 0)
		{
			req.getSession().setAttribute("excelmessage", "输入的表格除标题外无数据, 或错误去掉了标题. 请按要求只填充数据.");
			logger.info("输入的表格除标题外无数据, 或错误去掉了标题. 请按要求只填充数据.");
			return "redirect:returnbatchorder";
		}
		int returnOKCount = 0;
		IdentityHashMap<String, String> snMap = new IdentityHashMap<String, String>();
		int execlrow = result.length;
		logger.info("读取到数据行数:" + execlrow);

		for (int i = 0; i < execlrow; i++)
		{
			String SN = result[i][0].trim();
			if (StringUtils.isNotBlank(SN))
			{
				SN = Constants.SNformat(SN);
				int hang = deviceInfoSer.updateDevice_ruku(SN);
				if (hang > 0)
				{
					returnOKCount++;
					// 入库记录
					Shipment shipm = DeviceLogShipment(SN, "", "0", "", "", "入库", "", "", "", "");
					boolean shipmres = ShipmentSer.insert(shipm);
					if (shipmres)
					{
						logger.info("插入出入库记录成功！！！");
					}
					else
					{
						logger.info("插入出入库记录失败！！！");
						snMap.put(new String(SN), " 设备状态修改成功，但插入出入库记录失败");
					}
				}
				else
				{
					snMap.put(new String(SN), "请确认设备是否存在，设备是否为出库");
				}
			}
			else
			{
				execlrow = i;
				break;
			}

		}

		String message = "需要入库" + execlrow + "个， 　成功入库" + returnOKCount + "条，　导入失败" + snMap.size() + "条,　　以下为操作提示信息<br/>";
		String mess = "";
		for (String key : snMap.keySet())
		{
			mess = mess + key + ":　" + snMap.get(key) + "<br/>";
		}
		message = message + mess;
		req.getSession().setAttribute("excelmessage", message);
		return "redirect:returnbatchorder";
	}



	@RequestMapping("/returnbatchorder")
	public String returnbatchorder(HttpServletRequest req, HttpSession session, Model model)
	{

		model.addAttribute("LogMessage", req.getSession().getAttribute("excelmessage"));
		req.getSession().setAttribute("excelmessage", ""); // 及时清空, 避免污染其他页面
		return "WEB-INF/views/deviceinfo/deviceInfo_outboundDevice";
	}



	/**
	 * 出库
	 * 
	 * @param shipment
	 * @param req
	 * @param rep
	 * @throws IOException
	 */
	@RequestMapping("/getOutDevice")
	public void getOutDevice(Shipment shipment, String repertoryStatus, HttpServletRequest req, HttpServletResponse rep) throws IOException
	{
		// 参数 SN(可能有多个)、快递公司、单号、收货地址、运费
		// 修改设备状态
		int deviceCount = 0;
		List<String> list = new ArrayList<String>();
		String[] arr = shipment.getSN().split("\\|");
		list = Arrays.asList(arr);
		Shipment shipm = new Shipment();
		String SN = "";
		for (int i = 0; i < list.size(); i++)
		{
			int count = 0;
			try
			{
				if ("1".equals(repertoryStatus))
				{
					// 修改为出库
					DeviceInfo info = new DeviceInfo();
					info.setSN(list.get(i).toString());
					info.setRepertoryStatus("出库");
					count = deviceInfoSer.updateDeviceStatus_chuku(info);
				}
				else if ("2".equals(repertoryStatus))
				{
					// 修改为待出库
					DeviceInfo info = new DeviceInfo();
					info.setSN(list.get(i).toString());
					info.setRepertoryStatus("待出库");
					count = deviceInfoSer.updateDeviceStatus_chuku(info);
				}
			}
			catch (Exception e)
			{
				logger.info("修改设备状态为出库，失败！" + e.getMessage());
				e.printStackTrace();
			}
			deviceCount = deviceCount + count;
		}
		if (deviceCount == list.size())
		{
			boolean shipmres = false;
			for (int i = 0; i < list.size(); i++)
			{
				// 写入设备出库记录
				shipm = DeviceLogShipment(list.get(i).toString(), shipment.getAddress(), shipment.getLogisticsCost(), shipment.getLogisticsJC(), shipment.getExpressNO(), "出库", shipment.getRemark(),
						shipment.getRecipientName(), shipment.getChuchu(), shipment.getDeviceCardType());
				shipmres = ShipmentSer.insert(shipm);
			}
			if (shipmres)
			{
				rep.getWriter().println("0");
			}
			else
			{
				rep.getWriter().println("1");
			}
		}
		else
		{
			rep.getWriter().println("1");
		}
	}



	@RequestMapping("/getoutDeviceLogs")
	public String getoutLogsDevice()
	{
		return "WEB-INF/views/deviceinfo/deviceInfo_outboundDeviceLogs";
	}



	@RequestMapping("/getoutDeviceLogsEdit")
	public String getoutDeviceLogsEdit(@RequestParam String shipmentID, HttpServletRequest req)
	{

		Shipment shipment = null;
		try
		{
			shipment = ShipmentSer.ShipmentObject(shipmentID);
			req.setAttribute("shipment", shipment);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "WEB-INF/views/deviceinfo/deviceInfo_outboundDeviceLogs_edit";
	}



	@RequestMapping("updateOutboundDevicelogs")
	public String updateOutboundDevicelogs(Shipment shipment)
	{
		try
		{
			ShipmentSer.updateOutboundDevicelogs(shipment);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "WEB-INF/views/deviceinfo/deviceInfo_outboundDeviceLogs";
	}



	/**
	 * address 地址，logisticsCost 运费， LogisticsJC 快递简称， expressNo
	 * 快递单号，deviceStatus 设备库存状态 设备入库出库记录
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public Shipment DeviceLogShipment(String SN, String address, String logisticsCost, String LogisticsJC, String expressNo, String deviceStatus, String remark, String recipientName, String chuchu,
			String deviceCardType) throws UnsupportedEncodingException
	{

		SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
		String date = sdf.format(new Date());

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		Shipment shipm = new Shipment();

		if (LogisticsJC != "")
		{
			Dictionary dict = new Dictionary();
			dict.setDescription("快递公司");
			dict.setValue(LogisticsJC);
			Dictionary dictionary = dictionarySer.getDictByValue(dict);
			shipm.setLogisticsName(dictionary.getLabel());// 快递名称
		}
		address = java.net.URLDecoder.decode(address, "UTF-8");
		shipm.setSN(SN);
		shipm.setShipmentID(UUID.randomUUID().toString());
		shipm.setAddress(address);
		shipm.setShipmentDate(date);// 出货时间
		shipm.setLogisticsCost(logisticsCost);// 运费
		shipm.setLogisticsJC(LogisticsJC);// 快递简称
		shipm.setExpressNO(expressNo);// 快递单号
		shipm.setBatchNO(date.replace("-", "").replace(" ", "").replace(":", ""));// 批次号
		remark = java.net.URLDecoder.decode(remark, "UTF-8");
		shipm.setRemark(remark);
		shipm.setCreatorUserID(adminUserInfo.getUserID());
		shipm.setCreatorUserName(adminUserInfo.getUserName());
		shipm.setCreatorDate(date);
		shipm.setDeviceStatus(deviceStatus);
		recipientName = java.net.URLDecoder.decode(recipientName, "UTF-8");
		shipm.setRecipientName(recipientName);
		shipm.setChuchu(chuchu);
		shipm.setDeviceCardType(deviceCardType);
		return shipm;
	}



	@RequestMapping("/deviceShipmentLogs")
	public void deviceShipmentLogs(SearchDTO searchDTO, Shipment shipment, HttpServletResponse response)
	{

		response.setContentType("application/json;charset=UTF-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(), searchDTO.getPageSize(), searchDTO.getSortName(), searchDTO.getSortOrder(), shipment);
		String jsonString = ShipmentSer.getDeviceShipmentLogs(seDto);
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
	 * 批量出库
	 * 
	 * @param file
	 * @param session
	 * @param req
	 * @param resp
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@RequestMapping(value = "/excelsearch", method = RequestMethod.POST)
	public String excelsearch(@RequestParam("file") MultipartFile file, HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws FileNotFoundException, IOException
	{

		resp.setContentType("application/json;charset=UTF-8");

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SS");
		TimeZone t = sdf.getTimeZone();
		t.setRawOffset(0);
		sdf.setTimeZone(t);
		Long startTime = System.currentTimeMillis();

		Shipment shipm = new Shipment();
		boolean shipmres = false;
		// ------连接数据库-------

		try
		{
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH");
			// File files = new File("excel/new2.xls");
			String logoPathDir = "/files" + dateformat.format(new Date());
			// 得到excel保存目录的真实路径
			String temp = request.getSession().getServletContext().getRealPath(logoPathDir);
			// 创建文件保存路径文件夹
			File tempFile = new File(temp);
			System.out.println("这个是上传文件的真实路径temp：" + temp);

			MultipartFile multipartFile = multipartRequest.getFile("file");

			if (!tempFile.exists())
			{
				tempFile.mkdirs();
			}
			// 构建文件名称
			String logExcelName = multipartFile.getOriginalFilename();

			String fileName = temp + File.separator + logExcelName;
			File files = new File(fileName);
			try
			{
				multipartFile.transferTo(files);
			}
			catch (IllegalStateException e)
			{
				e.printStackTrace();
				req.getSession().setAttribute("chukuexcel", "上传出错！请重试或联系管理员");
				return "redirect:outboundDevice";
			}
			catch (IOException e)
			{
				e.printStackTrace();
				req.getSession().setAttribute("chukuexcel", "上传出错！请重试或联系管理员");
				return "redirect:outboundDevice";
			}
			System.out.println("上传完毕.");
			String[][] result = getData(files, 1);
			int rowLength = result.length;
			System.out.println(result.length);
			String str = "";
			String returnstr = "";
			int successCount = 0;
			int errorCount = 0;
			for (int i = 0; i < rowLength; i++)
			{
				System.out.println("excel所有的SN为：" + result[i][0]);
				DeviceInfo info = new DeviceInfo();
				info.setSN(result[i][0]);
				int count = 0;
				try
				{
					count = deviceInfoSer.updateDeviceStatus_chuku(info);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					System.out.println("修改设备出入库状态失败！");
					returnstr = returnstr + result[i][0] + ",";
				}
				if (count > 0)
				{
					successCount = count + successCount;
					System.out.println("修改设备状态成功！");
					String price = "";
					if (result[i][2].isEmpty()) price = "0";
					else price = result[i][2];
					shipm = DeviceLogShipment(result[i][0], result[i][1], price, result[i][3], result[i][4], "出库", result[i][5], result[i][6], result[i][7], result[i][8]);
					ShipmentSer.insert(shipm);// 写入出入库记录
				}
				else
				{
					errorCount = errorCount + 1;
					System.out.println("修改设备状态失败！");
					returnstr = returnstr + result[i][0] + ",";
				}
			}
			SimpleDateFormat sdf2 = new SimpleDateFormat();// 格式化时间
			sdf2.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
			String date = sdf2.format(new Date());
			if (errorCount > 0)
			{
				req.getSession().setAttribute("chukuexcel", "出库成功：<b>" + successCount + "</b><br/>" + "出库失败：<b>" + errorCount + "</b>| SN:" + returnstr + "<br/>操作时间：" + date);
			}
			else
			{
				req.getSession().setAttribute("chukuexcel", "出库成功：<b>" + successCount + "</b><br/>" + "出库失败：<b>" + errorCount + "</b>" + "<br/>操作时间：" + date);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("数据库连接失败");
		}
		// -----结束时间------
		Long endTime = System.currentTimeMillis();
		System.out.println("用时：" + sdf.format(new Date(endTime - startTime)));

		return "redirect:outboundDevice";
	}



	/**
	 * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	 * 
	 * @param file
	 *            读取数据的源Excel
	 * @param ignoreRows
	 *            读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
	 * @return 读出的Excel中数据的内容
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String[][] getData(File file, int ignoreRows) throws FileNotFoundException, IOException
	{
		List<String[]> result = new ArrayList<String[]>();
		int rowSize = 0;
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		// 打开HSSFWorkbook
		POIFSFileSystem fs = new POIFSFileSystem(in);
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFCell cell = null;
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++)
		{
			HSSFSheet st = wb.getSheetAt(sheetIndex);
			// 第一行为标题，不取
			for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++)
			{
				HSSFRow row = st.getRow(rowIndex);
				if (row == null)
				{
					continue;
				}
				int tempRowSize = row.getLastCellNum() + 1;
				if (tempRowSize > rowSize)
				{
					rowSize = tempRowSize;
				}
				String[] values = new String[rowSize];
				Arrays.fill(values, "");
				boolean hasValue = false;
				for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++)
				{
					String value = "";
					cell = row.getCell(columnIndex);
					if (cell != null)
					{
						// 注意：一定要设成这个，否则可能会出现乱码
						cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						switch (cell.getCellType())
						{
							case HSSFCell.CELL_TYPE_STRING:
								value = cell.getStringCellValue();
								break;
							case HSSFCell.CELL_TYPE_NUMERIC:
								if (HSSFDateUtil.isCellDateFormatted(cell))
								{
									Date date = cell.getDateCellValue();
									if (date != null)
									{
										value = new SimpleDateFormat("yyyy-MM-dd").format(date);
									}
									else
									{
										value = "";
									}
								}
								else
								{
									value = new DecimalFormat("0").format(cell.getNumericCellValue());
								}
								break;
							case HSSFCell.CELL_TYPE_FORMULA:
								// 导入时如果为公式生成的数据则无值
								if (!cell.getStringCellValue().equals(""))
								{
									value = cell.getStringCellValue();
								}
								else
								{
									value = cell.getNumericCellValue() + "";
								}
								break;
							case HSSFCell.CELL_TYPE_BLANK:
								break;
							case HSSFCell.CELL_TYPE_ERROR:
								value = "";
								break;
							case HSSFCell.CELL_TYPE_BOOLEAN:
								value = (cell.getBooleanCellValue() == true ? "Y" : "N");
								break;
							default:
								value = "";
						}
					}
					if (columnIndex == 0 && value.trim().equals(""))
					{
						break;
					}
					values[columnIndex] = rightTrim(value);
					hasValue = true;
				}

				if (hasValue)
				{
					result.add(values);
				}
			}
		}
		in.close();
		String[][] returnArray = new String[result.size()][rowSize];
		for (int i = 0; i < returnArray.length; i++)
		{
			returnArray[i] = result.get(i);
		}
		return returnArray;
	}



	/**
	 * 去掉字符串右边的空格
	 * 
	 * @param str
	 *            要处理的字符串
	 * @return 处理后的字符串
	 */
	public static String rightTrim(String str)
	{
		if (str == null)
		{
			return "";
		}
		int length = str.length();
		for (int i = length - 1; i >= 0; i--)
		{
			if (str.charAt(i) != 0x20)
			{
				break;
			}
			length--;
		}
		return str.substring(0, length);
	}



	@RequestMapping(value = "/exportdeviceupgrade")
	public void exportdeviceupgrade(DeviceUpgrading upgrading, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException
	{
		request.setCharacterEncoding("utf-8");

		if (upgrading.getCreatorUserName() != null)
		{
			upgrading.setCreatorUserName(new String(upgrading.getCreatorUserName().trim().getBytes("ISO-8859-1"), "utf-8"));
		}

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		sheet.setColumnWidth((short) 0, (short) 5000);
		sheet.setColumnWidth((short) 1, (short) 5000);
		sheet.setColumnWidth((short) 2, (short) 5000);
		sheet.setColumnWidth((short) 3, (short) 5000);
		sheet.setColumnWidth((short) 4, (short) 5000);
		sheet.setColumnWidth((short) 5, (short) 5000);
		sheet.setColumnWidth((short) 6, (short) 5000);
		sheet.setColumnWidth((short) 7, (short) 5000);
		sheet.setColumnWidth((short) 8, (short) 5000);

		HSSFRow row = sheet.createRow(0);
		row.setHeight((short) 500);

		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setFont(font);
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCellStyle style = wb.createCellStyle();
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("SN");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 1);
		cell.setCellValue("文件升级类型");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 2);
		cell.setCellValue("是否强制升级");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 3);
		cell.setCellValue("是否已升级");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 4);
		cell.setCellValue("升级时间");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 5);
		cell.setCellValue("创建人");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 6);
		cell.setCellValue("创建时间");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 7);
		cell.setCellValue("备注");
		cell.setCellStyle(style1);

		List<DeviceUpgrading> list = deviceInfoSer.getDeviceUpgradeExport(upgrading);

		for (int i = 0; i < list.size(); i++)
		{

			DeviceUpgrading du = list.get(i);
			row = sheet.createRow(i + 1);
			row.setHeight((short) 500);

			HSSFCell cell1 = row.createCell((short) 0);
			cell1.setCellValue(du.getSN());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 1);
			cell1.setCellValue(du.getUpgradeFileType());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 2);
			cell1.setCellValue(du.getIfForcedToUpgrade() ? "是" : "否");
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 3);
			cell1.setCellValue(du.getIfUpdated() ? "是" : "否");
			cell1.setCellStyle(style);

			cell = row.createCell((short) 4);
			cell.setCellValue(du.getUpdateDate());
			cell.setCellStyle(style);

			cell = row.createCell((short) 5);
			cell.setCellValue(du.getCreatorUserName());
			cell.setCellStyle(style);

			cell = row.createCell((short) 6);
			cell.setCellValue(du.getCreatorDate());
			cell.setCellStyle(style);

			cell = row.createCell((short) 7);
			cell.setCellValue(du.getRemark());
			cell.setCellStyle(style);
		}
		DownLoadUtil.execlExpoprtDownload("设备升级配置.xls", wb, request, response);
	}
	@RequestMapping("/excelExportDevice")
	public void excelExportDevice(HttpServletResponse response, HttpServletRequest request,String deviceIDstr,String distributorID) throws UnsupportedEncodingException{

	    	request.setCharacterEncoding("utf-8");
		System.out.println("开始导出渠道商选中的设备信息列表");
		System.out.println(deviceIDstr);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		sheet.setColumnWidth((short) 0, (short) 5000);
		sheet.setColumnWidth((short) 1, (short) 5000);
		sheet.setColumnWidth((short) 2, (short) 5000);
		sheet.setColumnWidth((short) 3, (short) 5000);
		sheet.setColumnWidth((short) 4, (short) 5000);
		sheet.setColumnWidth((short) 5, (short) 5000);
		sheet.setColumnWidth((short) 6, (short) 5000);
		sheet.setColumnWidth((short) 7, (short) 5000);

		HSSFRow row = sheet.createRow(0);
		row.setHeight((short) 500);

		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setFont(font);
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCellStyle style = wb.createCellStyle();
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("序号");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 1);
		cell.setCellValue("ID");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 2);
		cell.setCellValue("SN");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 3);
		cell.setCellValue("设备颜色");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 4);
		cell.setCellValue("出入库状态");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 5);
		cell.setCellValue("创建人");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 6);
		cell.setCellValue("创建日期");
		cell.setCellStyle(style1);

		cell = row.createCell((short) 7);
		cell.setCellValue("导入用户");
		cell.setCellStyle(style1);

		deviceIDstr="'"+deviceIDstr.replaceAll(",","\',\'")+"'";
		
		DeviceInfo d=new DeviceInfo();
		d.setDeviceID(deviceIDstr);
		d.setDistributorID(distributorID);
		List<DeviceInfo> list=deviceInfoSer.excelExportDevice(d);

		for (int i = 0; i < list.size(); i++)
		{

			DeviceInfo de = list.get(i);
			row = sheet.createRow(i + 1);
			row.setHeight((short) 500);

			HSSFCell cell1 = row.createCell((short) 0);
			cell1.setCellValue(i+1);
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 1);
			cell1.setCellValue(de.getDeviceID());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 2);
			cell1.setCellValue(de.getSN());
			cell1.setCellStyle(style);

			cell1 = row.createCell((short) 3);
			cell1.setCellValue(de.getDeviceColour());
			cell1.setCellStyle(style);

			cell = row.createCell((short) 4);
			cell.setCellValue(de.getRepertoryStatus());
			cell.setCellStyle(style);

			cell = row.createCell((short) 5);
			cell.setCellValue(de.getCreatorUserName());
			cell.setCellStyle(style);

			cell = row.createCell((short) 6);
			cell.setCellValue(de.getCreatorDate());
			cell.setCellStyle(style);

			cell = row.createCell((short) 7);
			cell.setCellValue(de.getCreatorDate());
			cell.setCellStyle(style);
		}
		try {
			DownLoadUtil.execlExpoprtDownload("设备信息（excel导出）.xls", wb,
					request, response);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
	    String ss="DeviceMessage{" +
	                "sn='" + "860172008140853" + '\'' +
	                ", result='" + "成功" + '\'' +
	                ", SSID='" + "EASY2GO-140853" + '\'' +
	                ", roam_appVersionNum='" + "1603181353" + '\'' +
	                ", roam_apkVersionNum='" + "1603160000" + '\'' +
	                ", roam_IMEI='" + "172150210000110" + '\'' +
	                ", roam_SN='" + "860172008140853" + '\'' +
	                ", roam_ICCID='" + "89860115851067805555" + '\'' +
	                ", roam_SIMStatus='" + "正在使用中" + '\'' +
	                ", roam_calibrationMark='" + "[gsm.serial]:[03040110]" + '\'' +
	                ", roam_networkStrength='" + "94" + '\'' +
	                ", roam_networkType='" + "HSPA" + '\'' +
	                ", roam_dataConnectionStatus='" + "已连接" + '\'' +
	                ", local_wifiPwd='" + "13073287" + '\'' +
	                ", local_serialComTest='" + "" + '\'' +
	                ", local_appVersionNum='" + "1603161748" + '\'' +
	                ", local_apkVersionNum='" + "1603160000" + '\'' +
	                ", local_IMEI='" + "172150210000000" + '\'' +
	                ", local_SN='" + "860172008140853" + '\'' +
	                ", local_ICCID='" + "89860114721103139908" + '\'' +
	                ", local_SIMStatus='" + "使用中" + '\'' +
	                ", local_calibrationMark='" + "[gsm.serial]:[01010110]" + '\'' +
	                ", local_networkType='" + "HSPA" + '\'' +
	                ", local_networkStrength='" + "98" + '\'' +
	                ", local_dataConnectionStatus='" + "已连接" + '\'' +
	                ", installCard='" + "1" + '\'' +
	                '}';
	    System.out.println(ss);
	    //saveFactoryTestInfo(ss, request, response);
	}
	@RequestMapping(value = "/importExecl", method = RequestMethod.POST)
	public void batchaddorders1(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse resp, HttpSession session, Model model) throws FileNotFoundException, IOException
	{
		logger.info("..导入订单得到请求..");
		CommonsMultipartFile cf = (CommonsMultipartFile) file;
		DiskFileItem fi = (DiskFileItem) cf.getFileItem();
		File files = fi.getStoreLocation();
		String[][] result = ExcelUtils.getData(files, 1); // 此模板开始一行为标题
		List<String> snList=new ArrayList<>();
		logger.info("成功获取到execl");
		int allColomn = result[0].length;
		int allRow = result.length;
		logger.info("成功获取到execl，行：" + allRow + "列：" + allColomn + "");
		try
		{
			for (int i = 0; i < result.length; i++)
			{
				for (int j = 0; j < result[i].length - 1; j++)
				{
					if (result[i][j].equals(""))
					{
						if (j == 0)
						{
							allRow = i;
							i = result.length - 1;
							break;
						}
					}else{
					    snList.add(result[i][j]); 
					}
				}
			}
			logger.info("excel解析完毕.实际数据行数:" + allRow);
		}catch (Exception e) {
		    
		}
		//开始查询数据，并且导出数据
		if(snList!=null && !snList.isEmpty()){
		    FactoryTestInfo f=new FactoryTestInfo();
		    f.setSnList(snList);
		    f.setDistinct("yes");
		    this.exportFactoryTestInfo(f,resp,req);
		}
	}
}
