package com.Manage.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.log4j.Logger;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import com.Manage.common.constants.Constants;
import com.Manage.common.util.DES;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.ExcelUtils;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.common.util.ExcelUtils.DeviceInfoImportConstants;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.CustomerInfo;
import com.Manage.entity.DeviceDealOrders;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.Dictionary;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.OrdersInfo;
import com.Manage.entity.Shipment;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

@Controller
@RequestMapping("/device/data")
public class ExcelIntoData extends BaseController
{
	private Logger logger = LogUtil.getInstance(ExcelIntoData.class);

	@Resource(name = "transactionManager")
	private PlatformTransactionManager pm;

	private TransactionStatus ts = null;



	@RequestMapping("/insert")
	public String insertcus(HttpServletRequest req, HttpSession session, Model model)
	{

		model.addAttribute("LogMessage", req.getSession().getAttribute("excelmessage"));
		req.getSession().setAttribute("excelmessage", ""); // 及时清空, 避免污染其他页面
		return "WEB-INF/views/deviceinfo/deviceInfo_insertdata";
	}



	@RequestMapping("/update")
	public String update(HttpServletRequest req, HttpSession session, Model model)
	{

		model.addAttribute("LogMessage", req.getSession().getAttribute("excelmessage"));
		String orderID = (String) req.getSession().getAttribute("orderID");
		OrdersInfo order = ordersInfoSer.getdetail(orderID);
		CustomerInfo customerInfo = customerInfoSer.getOneCustomerDetail(order.getCustomerID());
		order.setCustomerInfo(customerInfo);
		model.addAttribute("order", order);
		req.getSession().setAttribute("order", "");
		req.getSession().setAttribute("excelmessage", ""); // 及时清空, 避免污染其他页面

		// 获取到快递公司名称
		List<Dictionary> dictionaries = ordersInfoSer.getExpress();
		model.addAttribute("dictionaries", dictionaries);

		return "WEB-INF/views/orders/orderinfo";
	}



	@RequestMapping("/returnbatchorder")
	public String returnbatchorder(HttpServletRequest req, HttpSession session, Model model)
	{
		model.addAttribute("LogMessage", req.getSession().getAttribute("excelmessage"));
		req.getSession().setAttribute("excelmessage", ""); // 及时清空, 避免污染其他页面
		return "WEB-INF/views/orders/batchorders";
	}



	@RequestMapping(value = "/startInsert", method = RequestMethod.POST)
	public String insert(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse resp, HttpSession session, Model model) throws SQLException, FileNotFoundException, IOException, InterruptedException
	{

		System.out.println("得到上传excel请求");

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (adminUserInfo == null)
		{
			req.getSession().setAttribute("excelmessage", "请重新登录！");
			return "redirect:insert";
		}

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH");
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
			req.getSession().setAttribute("excelmessage", "上传出错！请重试或联系管理员");
			return "redirect:insert";
		}
		catch (IOException e)
		{
			e.printStackTrace();
			req.getSession().setAttribute("excelmessage", "上传出错！请重试或联系管理员");
			return "redirect:insert";
		}
		System.out.println("上传完毕.");
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SS");
		TimeZone t = sdf.getTimeZone();
		t.setRawOffset(0);
		sdf.setTimeZone(t);
		Long startTime = System.currentTimeMillis();

		String[][] result = ExcelUtils.getData(files, 1);
		int allDatacount = result.length;
		if (allDatacount == 0)
		{
			req.getSession().setAttribute("excelmessage", "输入的表格除标题外无数据, 或错误去掉了标题. 请按要求只填充数据.(序号不能为空！)");
			return "redirect:insert";
		}
		int keyEmptyCount = 0;
		int requiredInvalidCount = 0;

		int insertFailedCount = 0;
		int insertOKCount = 0;
		int updateOKCount = 0;
		List<String> keyEmptyIDs = new ArrayList<String>();
		List<String> requiredInvalidIDs = new ArrayList<String>();
		List<String> insertFailedIDs = new ArrayList<String>();

		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pstupdate = null;
		try
		{
			conn = getDBConn();
		}
		catch (ClassNotFoundException e1)
		{
			e1.printStackTrace();
			req.getSession().setAttribute("excelmessage", "无法连接数据库！请重试或联系管理员");
			return "redirect:insert";
		}

		try
		{
			pst = (PreparedStatement) conn.prepareStatement("insert into DeviceInfo(SN,CardID,deviceColour,distributorName,remark,"
		+ "supportCountry,modelNumber,frequencyRange,repertoryStatus,"
					+ "deviceID,creatorUserID,creatorUserName,creatorDate)"
		+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?)");

			SimpleDateFormat sdfdate = new SimpleDateFormat();
			sdfdate.applyPattern("yyyy-MM-dd HH:mm:ss");
			int pstIndex;
			for (int i = 0; i < allDatacount; i++)
			{

				if (result[i][DeviceInfoImportConstants.COL_SN] == "")
				{
					keyEmptyCount++;
					keyEmptyIDs.add(result[i][0]);
					continue;
				}
				pstIndex = 0;
				pst.setString(++pstIndex, result[i][DeviceInfoImportConstants.COL_SN]);
				pst.setString(++pstIndex, result[i][DeviceInfoImportConstants.COL_CardID]);
				pst.setString(++pstIndex, result[i][DeviceInfoImportConstants.COL_deviceColour]);
				pst.setString(++pstIndex, result[i][DeviceInfoImportConstants.COL_distributorName]);
				pst.setString(++pstIndex, result[i][DeviceInfoImportConstants.COL_remark]);
				pst.setString(++pstIndex, result[i][DeviceInfoImportConstants.COL_supportCountry]);
				pst.setString(++pstIndex, result[i][DeviceInfoImportConstants.COL_modelNumber]);
				pst.setString(++pstIndex, result[i][DeviceInfoImportConstants.COL_frequencyRange]);
				pst.setString(++pstIndex, result[i][DeviceInfoImportConstants.COL_repertoryStatus]);

				pst.setString(++pstIndex, UUID.randomUUID().toString());
				pst.setString(++pstIndex, adminUserInfo.userID);
				pst.setString(++pstIndex, adminUserInfo.userName);
				pst.setString(++pstIndex, sdfdate.format(new Date()).toString());

				try
				{
					pst.execute();
					insertOKCount++;
				}
				catch (SQLException e)
				{
				  if(e.getErrorCode()==1062){
					  String updatesql ="UPDATE DeviceInfo SET ";

					  String cardid="";String devicecolour="";String distributorname="";String remark="";
					  String supportcountry="";String modelnumber="";String frequencyrange="";String repertoryStatus = "";
					  String modifyUserID="modifyUserID='"+adminUserInfo.getUserID()+"',";
					  String modifyDate="modifyDate='"+sdfdate.format(new Date()).toString()+"'";

					  if(result[i][2].toString()!="")
						  cardid = "CardID='"+result[i][2].toString()+"',";
					  if(result[i][3].toString()!="")
						  devicecolour = "deviceColour='"+result[i][3].toString()+"',";
					  if(result[i][4].toString()!="")
						  distributorname = "distributorName='"+result[i][4].toString()+"',";
					  if(result[i][5].toString()!="")
						  remark = "remark='"+result[i][5].toString()+"',";
					  if(result[i][6].toString()!="")
						  supportcountry = "supportCountry='"+result[i][6].toString()+"',";
					  if(result[i][7].toString()!="")
						  modelnumber = "modelNumber='"+result[i][7].toString()+"',";
					  if(result[i][8].toString()!="")
						  frequencyrange = "frequencyRange='"+result[i][8].toString()+"',";
					  if(result[i][9].toString()!="")
						  repertoryStatus = "repertoryStatus='"+result[i][9].toString()+"',";

					  updatesql = updatesql+cardid+devicecolour+distributorname+remark+supportcountry+modelnumber+frequencyrange+repertoryStatus+modifyUserID+modifyDate+ " WHERE SN='"+result[i][1].toString()+"'";
					pstupdate = (PreparedStatement) conn.prepareStatement(updatesql);
					int puCount = 0;
					try {
						puCount = pstupdate.executeUpdate();
						if(puCount>0){
							updateOKCount++;//覆盖掉的总数
							insertOKCount++;//记录到总数

							Shipment shipm = null;
							shipm = DeviceLogShipment(result[i][1].toString(), "", "0", "", "", "入库", "新增设备", "");
							try {
								ShipmentSer.insert(shipm);//写入出入库记录
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
						logger.info("需要覆盖的ＳＮ："+result[i][1].toString());
					} catch (Exception e1) {
						logger.info("覆盖出错！");
						e1.printStackTrace();
						insertFailedCount++;
						insertFailedIDs.add(result[i][0]);
					}
				  }else{
						e.printStackTrace();
						insertFailedCount++;
						insertFailedIDs.add(result[i][0]);
				  }
				}

			}


			AdminOperate admin = new AdminOperate();
			admin.setOperateID(UUID.randomUUID().toString());
			admin.setCreatorUserID(adminUserInfo.userID);
			admin.setCreatorUserName(adminUserInfo.userName);
			admin.setOperateContent(multipartFile.getOriginalFilename() + ", 批量导入设备, 成功导入 " + insertOKCount + " 条, 插入失败 " + insertFailedCount + " 条, 无效数据 " + keyEmptyCount + " 条, 原始记录总计 " + allDatacount + " 条."); // 操作内容
			admin.setOperateMenu("设备管理>批量导入设备");
			admin.setOperateType("新增");
			adminOperateSer.insertdata(admin);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			req.getSession().setAttribute("excelmessage", "操作数据库出错！请重试或联系管理员");
			return "redirect:insert";
		}
		finally
		{
			if (pst != null)
			{
				pst.close();
				pst = null;
			}
			if (conn != null)
			{
				conn.close();
				pst = null;
			}
		}
		Long endTime = System.currentTimeMillis();
		String usetime = sdf.format(new Date(endTime - startTime));

		String requiredInvalidIDsString = "";
		String insertFailedIDsString = "";
		String keyEmptyIDsString = "";
		if (requiredInvalidCount > 0)
		{
			Collections.sort(requiredInvalidIDs);
			requiredInvalidIDsString = "<br />关键字段缺失的记录序号: ";
			for (String id : requiredInvalidIDs)
			{
				requiredInvalidIDsString += id + ", ";
			}
		}
		if (insertFailedCount > 0)
		{
			Collections.sort(insertFailedIDs);
			insertFailedIDsString = "<br />插入失败的记录序号: ";
			for (String id : insertFailedIDs)
			{
				insertFailedIDsString += id + ", ";
			}
		}
		if (keyEmptyCount > 0)
		{
			Collections.sort(keyEmptyIDs);
			keyEmptyIDsString = "<br />无效数据的记录序号(缺少设备SN): ";
			for (String id : keyEmptyIDs)
			{
				keyEmptyIDsString += id + ", ";
			}
		}
		requiredInvalidIDsString += keyEmptyIDsString + insertFailedIDsString;
		if (StringUtils.isNotBlank(requiredInvalidIDsString))
		{
			requiredInvalidIDsString = "<br />" + requiredInvalidIDsString;
		}

		req.getSession().setAttribute("excelmessage", "《" + multipartFile.getOriginalFilename() + "》<br /><br />成功导入 <b>" + insertOKCount + "</b> 条数据, 共用时：" + usetime

				+ "<br />原始记录总数：<b>" + allDatacount + "</b><br />无效数据条数：<b>" + keyEmptyCount + "</b><br />插入失败条数：<b>" + insertFailedCount + "</b>"
				+  "<br />被覆盖数据条数：<b>" + updateOKCount + "</b><br />"+requiredInvalidIDsString);

		System.out.println("用时：" + usetime);
		System.out.println("共导入" + allDatacount + "条数据！");

		return "redirect:insert";
	}



	private Connection getDBConn() throws ClassNotFoundException, SQLException, IOException
	{

		// 读取链接数据库的内容
		InputStream in = getClass().getClassLoader().getResourceAsStream("jdbc.properties");
		Properties myProperty = new Properties();
		myProperty.load(in);

		String url = myProperty.getProperty("jdbc.url");
		String user = myProperty.getProperty("jdbc.username");
		String password = myProperty.getProperty("jdbc.password");

		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("数据库连接成功");
		return (Connection) java.sql.DriverManager.getConnection(url, user, password);
	}



	/**
	 * 批量发货
	 *
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	/*@RequestMapping(value = "/batchUpdateSN", method = RequestMethod.POST)
	public String batchUpdateSN(@RequestParam("file") MultipartFile file, Shipment shipment, String logisticsCost, String orderID, String expressNO, String logistics, HttpServletRequest req, HttpServletResponse resp, HttpSession session, Model model) throws FileNotFoundException, IOException
	{

		logger.info("得到上传excel请求");
		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		if (adminUserInfo == null)
		{
			req.getSession().setAttribute("excelmessage", "请重新登录！");
			return "redirect:insert";
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH");
		String logoPathDir = "/files" + dateformat.format(new Date());
		String temp = request.getSession().getServletContext().getRealPath(logoPathDir);
		File tempFile = new File(temp);
		logger.info("这个是上传文件的真实路径temp：" + temp);
		MultipartFile multipartFile = multipartRequest.getFile("file");
		if (!tempFile.exists())
		{
			tempFile.mkdirs();
		}
		String logExcelName = multipartFile.getOriginalFilename();
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
			req.getSession().setAttribute("orderID", orderID);
			return "redirect:update";
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
		int deviceDealCount = 0;// 该总订单下设备订单个数
		int SNCount = 0;
		String youzanHandleErrorString = "";
		// ahming notes: 确定多余
		try
		{
			// 获取到一共有多少个sn
			for (int i = 0; i < result.length; i++)
			{
				if ("".equals(result[i][0]) || i == result.length - 1)
				{
					SNCount = i + 1;
					break;
				}
			}
			// 获取到该订单下的所有设备订单
			List<DeviceDealOrders> deviceDealOrders = ordersInfoSer.getDeviceDealOrdersIDByorderID(orderID);
			// 获取到该总订单下有多少个设备订单
			deviceDealCount = deviceDealOrders.size();
			String[] SN = new String[deviceDealCount];
			if (SNCount < deviceDealCount)
			{
				req.getSession().setAttribute("excelmessage", "发货失败！SN个数小于设备订单个数，SN个数为：" + SNCount + ",设备个数为：" + deviceDealCount + "");
				req.getSession().setAttribute("orderID", orderID);
				return "redirect:update";
			}
			// 若个数大于该订单下设备的数量，就使用前面几个
			// 检查SN的长度
			for (int i = 0; i < deviceDealCount; i++)
			{
				result[i][0] = Constants.DICT_DEVICE_SN + result[i][0];
				if (result[i][0].length() != 15)
				{
					req.getSession().setAttribute("excelmessage", "发货失败！SN" + result[i][0] + "的长度应为15");
					req.getSession().setAttribute("orderID", orderID);
					return "redirect:update";
				}
			}
			// 检查是否有重复的SN
			String tempResult = "";
			for (int i = 0; i < deviceDealCount; i++)
			{
				String SN1 = result[i][0];
				for (int j = i; j < deviceDealCount - 1; j++)
				{
					if (SN1.equals(result[j + 1][0]))
					{
						if (tempResult.indexOf(SN1) < 0)
						{
							tempResult += "SN:" + SN1 + "(重复) ";
						}
					}
				}
			}
			// j是行
			for (int j = 0; j < deviceDealCount; j++)
			{
				// 拿到表格里第j行第一列的单元格里值
				SN[j] = result[j][0];
				// 这里检查设备有没有在使用中
				DeviceInfo deviceInfo = deviceInfoSer.getbysn(SN[j]);
				if (null == deviceInfo)
				{
					tempResult += "SN:" + SN[j] + "(查无此设备) ";
				}
				else if ("使用中".equals(deviceInfo.getDeviceStatus()))
				{
					tempResult += "SN:" + SN[j] + "(设备使用中) ";
				}
			}
			if (!"".equals(tempResult))
			{ // 包括重复和设备使用中和查无此设备的
				req.getSession().setAttribute("excelmessage", "发货失败！" + tempResult);
				req.getSession().setAttribute("orderID", orderID);
				return "redirect:update";
			}
			insertOKCount = ordersInfoSer.saveSN(SN, orderID, deviceDealOrders);
			// 更新快递信息（总订单表物流信息）
			if (insertOKCount == deviceDealCount)
			{
				int hang = ordersInfoSer.updateLogisticsInfo(expressNO, logistics, orderID, null, deviceDealOrders);
				// 一般来说, 有赞对应的总订单下总是只有一条流量或设备订单, 所以在这里处理是适合的, 不需要在前面
				// ordersInfoSer.saveSN 里处理
				// 如果系有赞的订单, 则通过接口同步状态到有赞 : 若它所在的有赞购物车订单宝贝已全部完毕
				// --> 应该制作成一个 service 多处去调用!
				if (hang > 0)
				{ // 添加检查, 因为前面若更新物流不成功, 则判断有赞的不会得到期望的结果
					YouzanSyncLogisticsResult logisticsResult = ordersInfoSer.syncYouzanShipmentStatus(orderID, deviceDealOrders.get(0), null, null, null, SN[0]);
					if (0 == logisticsResult.getErrorCode())
					{
						// 不改变任何内容
					}
					else
					{
						youzanHandleErrorString = logisticsResult.getMsg(); // 系有赞的订单
					}
				}
			}

			// 更新工作流记录表物流信息
			WorkFlow workFlow = new WorkFlow();
			workFlow.setOrderID(orderID);
			if (workFlowSer.getInfoByOrderID(workFlow) != null)
			{
				workFlow.setLogisticsCompany(logistics);// 物流公司
				workFlow.setExpressNO(expressNO);// 快递单号
				workFlow.setWorkFlowStatus(Constants.workFlowStatus_creatorOrder);
				if (workFlowSer.updateLogisticsInfo(workFlow))
				{
					logger.info("更新工作流记录表物流信息成功！！！");
				}
				else
				{
					logger.info("更新工作流记录表物流信息失败");
				}
			}
			else
			{
				// 没有找到数据不用更新物流信息
				logger.info("工作流记录表中没有找到订单ID为：" + orderID + "的订单记录");
			}
			// 到这里说明发送成功，同时将发货的信息增加到出库记录表里
			int success = 0;
			String batchNO = DateUtils.getDate("yyyyMMddHHmmss");
			for (int j = 0; j < deviceDealCount; j++)
			{
				AdminUserInfo user = (AdminUserInfo) getSession().getAttribute("User");
				shipment.setLogisticsCost(logisticsCost);
				Dictionary dict = new Dictionary();
				dict.setDescription("快递公司");
				dict.setValue(logistics);
				Dictionary dictionary = dictionarySer.getDictByValue(dict);
				shipment.setLogisticsName(dictionary.getLabel());
				shipment.setShipmentDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
				shipment.setCreatorDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
				shipment.setCreatorUserID(user.getUserID());
				shipment.setCreatorUserName(user.getUserName());
				shipment.setExpressNO(expressNO);
				shipment.setLogisticsJC(logistics);
				shipment.setModifyDate(null);
				shipment.setModifyUserID(null);
				shipment.setOrderID(orderID);
				shipment.setRemark("");
				shipment.setShipmentID(UUID.randomUUID().toString());
				shipment.setSN(SN[j].trim());
				shipment.setSysStatus("1");
				shipment.setBatchNO(batchNO);
				if (ShipmentSer.insert(shipment))
				{
					success++;
				}
			}
			if (success == SN.length)
			{
				logger.info("出货记录表数据插入成功！！！");
			}
			else
			{
				logger.info("出货记录表数据插入成功！！！");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			req.getSession().setAttribute("excelmessage", "操作数据库出错！请重试或联系管理员");
			req.getSession().setAttribute("orderID", orderID);
			return "redirect:update";
		}
		AdminOperate admin = new AdminOperate();
		admin.setOperateID(UUID.randomUUID().toString()); // id
		admin.setCreatorUserID(adminUserInfo.userID); // 创建人ID
		admin.setCreatorUserName(adminUserInfo.userName); // 创建人姓名
		admin.setOperateContent(""); // 操作内容
		admin.setOperateMenu("设备出入库管理>发货"); // 操作菜单
		admin.setOperateType("更新SN"); // 操作类型
		adminOperateSer.insertdata(admin);
		// 添加有赞的处理信息到后面, 若不是有赞订单, 则 youzanHandleErrorString 前面为空,
		// 这里也不会有任何有赞相关的内容
		youzanHandleErrorString = "请检查结果：需要导入" + deviceDealCount + "条，导入成功：" + insertOKCount + "条，导入失败" + (deviceDealCount - insertOKCount) + "条" + "<br />" + youzanHandleErrorString;
		req.getSession().setAttribute("excelmessage", youzanHandleErrorString);
		req.getSession().setAttribute("orderID", orderID);
		return "redirect:update";
	}*/



	/**
	 * 订单导入 （按天数限流量）
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
	@RequestMapping(value = "/batchaddorders2", method = RequestMethod.POST)
	public String batchaddorders2(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse resp, HttpSession session, Model model) throws FileNotFoundException, IOException
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
		int insertOKCount = 0; // 成功插入条数
		IdentityHashMap<String, String> snMap = new IdentityHashMap<String, String>();// 将不能下单的设备添加到这个数组中
		String phone = "";
		int execlrow = result.length;//
		logger.info("读取到数据行数:" + execlrow);
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
							execlrow = i;
							i = result.length - 1;
							break;
						}
						if (j != 3 && j < 12)
						{
							logger.info("关健字段不能为空:" + (i + 1) + "行," + (j + 1) + "列");
							req.getSession().setAttribute("excelmessage", "关健字段不能为空");
							return "redirect:returnbatchorder";
						}

					}
				}
			}
			logger.info("excel解析完毕.实际数据行数:" + execlrow);
			boolean success = false;
			for (int i = 0; i < execlrow; i++)
			{
				ts = pm.getTransaction(null);
				String customerName = result[i][0].trim();// 客户姓名
				phone = result[i][1].trim();// 电话
				String address = result[i][2];// 地址
				String email = result[i][3];// email
				String SN = result[i][4].trim();// 设备编号
				String deallType = result[i][5];// 交易类型
				String countryName = result[i][6];// 国家编号列表
				String oldPanlUserDate = result[i][7];// 预约时间
				String str = "";
				String panlUserDate = "20";
				for (int j = 0; j < oldPanlUserDate.length(); j++)
				{
					str = str + oldPanlUserDate.substring(j, j + 2) + ",";
					j++;
				}
				String[] panl = str.split(",");
				// 拼接预约时间
				for (int j = 0; j < panl.length; j++)
				{
					if (j < 2)
					{
						panlUserDate = panlUserDate + panl[j] + "-";
					}
					else if (j == 2)
					{
						panlUserDate = panlUserDate + panl[j] + " ";
					}
					else if (j >= 3)
					{
						if (j == 4)
						{
							panlUserDate = panlUserDate + panl[j] + ":00";
							break;
						}
						panlUserDate = panlUserDate + panl[j] + ":";
					}
				}
				String flowDealCount = result[i][8];// 流量订单 个数
				String flowDays = result[i][9];// 天数
				String flowTotal = result[i][10];// 流量（M）
				String orderAmount = result[i][11];// 总金额
				String journey = result[i][12];// 用户行程
				String remark = result[i][13];// 备注
				String[] SN1 = SN.split("/");
				if (SN1.length != Integer.parseInt(flowDealCount))
				{
					logger.info("手机号:" + phone + "-记录出错,原因:该SN个数不等于流量订单个数");
					snMap.put(new String(phone), "该SN个数小于流量订单个数");
					pm.rollback(ts);

					continue;
				}
				// 如果以上关健字段都不为空，就开始进行数据库操作
				// 首先查询客户表里的电
				List<CustomerInfo> customerinfo = customerInfoSer.selectOneCustomerinfoPhonetwo(phone);
				boolean customer = true;
				// ==========
				// 客户
				String customerID = UUID.randomUUID().toString();
				if (customerinfo == null || customerinfo.size() <= 0)
				{
					customer = false;
					// 没有查到手机号，插入客户数据后再下单
					CustomerInfo customerInfo = new CustomerInfo();
					customerInfo.setCustomerID(customerID);
					customerInfo.setCustomerName(customerName);
					customerInfo.setPhone(phone);
					customerInfo.setPassword(DES.toHexString(DES.encrypt("88888888", Constants.DES_KEYWEB)));
					customerInfo.setEmail(email);
					customerInfo.setUsername("");
					customerInfo.setCreatorUserID(adminUserInfo.getUserID());
					customerInfo.setCreatorDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
					customerInfo.setRemark("");
					customerInfo.setSysStatus(1);// 默认就是 1
					// 插入客户信息
					customerInfoSer.insertCustomerInfo(customerInfo);
				}
				else if (!customerinfo.get(0).getCustomerName().trim().equals(customerName.trim()))
				{
					logger.info("手机号:" + phone + "-记录出错,原因:该手机号码重复,请核对姓名");
					snMap.put(new String(phone), "该手机号码重复,请核对姓名");
					pm.rollback(ts);
					continue;
				}
				else if (customerinfo.get(0).getCustomerName().trim().equals(customerName.trim()))
				{
					customerID = customerinfo.get(0).getCustomerID();
				}
				logger.info("客户信息增加成功");
				Boolean jx = true;

				// 国家
				String[] country = countryName.split("/");
				String UserCountry = "";// 拼接使用国家信息
				for (int j = 0; j < country.length; j++)
				{
					// 查询国家是否存在，如何不存在提示国家名称不存在
					String c_Name = country[j];
					CountryInfo countryInfo = new CountryInfo();
					countryInfo.setCountryName(c_Name);
					List<CountryInfo> contryInfos = countryInfoSer.getcountryinfoBycountryname(countryInfo);
					if (contryInfos == null || contryInfos.size() <= 0)
					{
						// 国家名称不存在
						logger.info("手机号:" + phone + "记录出错,原因:该用户填写的使用国家名称不存在");
						snMap.put(new String(phone), "该用户填写的使用国家名称不存在");
						pm.rollback(ts);
						jx = false;
						break;
					}
					else
					{
						if (j == country.length - 1)
						{
							UserCountry = UserCountry + contryInfos.get(0).getCountryName() + "," + contryInfos.get(0).getCountryCode() + "," + contryInfos.get(0).getFlowPrice();
						}
						else
						{
							UserCountry = UserCountry + contryInfos.get(0).getCountryName() + "," + contryInfos.get(0).getCountryCode() + "," + contryInfos.get(0).getFlowPrice() + "|";
						}
					}
				}
				if (jx == false)
				{
					continue;
				}
				for (int j = 0; j < SN1.length; j++)
				{
					String deviceID = UUID.randomUUID().toString();// 设备id
					int status = deviceInfoSer.verifySN(Constants.SNformat(SN1[j]), UserCountry);
					// -1表示SN不存在。-2表示SN使用中,0表示SN或漫游卡不支持国家，1表示可以被下单.
					if (status == -2)
					{
						logger.info("SN使用中");
						snMap.put(new String(phone), SN1[j] + "设备使用中");
						pm.rollback(ts);
						jx = false;
						break;
					}
					else if (status == 0)
					{
						logger.info("SN或漫游卡不支持国家");
						snMap.put(new String(phone), SN1[j] + "设备或漫游卡不支持国家");
						pm.rollback(ts);
						jx = false;
						break;
					}
					else if (status == 1)
					{
						logger.info("可以被下单");
					}
					else if (status == -1)
					{
						if(Constants.SNformat(SN1[j])==null){
							snMap.put(new String(phone), SN1[j] + "输入有误，请输入后六位");
							pm.rollback(ts);
							jx = false;
							break;
						}
						logger.info("SN不存在");
						// 检查设备不存在，就新增一个设备
						DeviceInfo device = new DeviceInfo();
						device.setDeviceID(deviceID);
						device.setSN(Constants.SNformat(SN1[j]));
						// 漫游卡编号
						device.setCardID("");
						device.setCreatorUserID(adminUserInfo.getCreatorUserID());
						device.setSysStatus("1");
						device.setCreatorDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
						device.setCreatorUserName(adminUserInfo.getUserName());
						int count = deviceInfoSer.insertDeviceInfo(device);
						if (count <= 0)
						{
							logger.info("手机号:" + phone + "记录出错,原因:" + SN1[j] + "该设备增加失败");
							snMap.put(new String(phone), SN1[j] + " 该设备增加失败");
							pm.rollback(ts);
							jx = false;
							break;
						}
					}
				}
				if (jx == false)
				{
					continue;
				}
				logger.info("设备信息增加成功");
				// 总订单 插入一条记录
				OrdersInfo orders = new OrdersInfo();
				String orderID = UUID.randomUUID().toString();
				orders.setOrderID(orderID);
				orders.setCustomerID(customerID);
				orders.setCustomerName(customerName);
				orders.setDeviceDealCount(SN1.length);
				orders.setIfFinish("是");
				orders.setOrderStatus("已付款");
				orders.setOrderSource("后台");
				orders.setShipmentsStatus("已发货");
				orders.setFlowDealCount(Integer.parseInt(flowDealCount));
				orders.setOrderAmount(Double.parseDouble(orderAmount) + 500);
				orders.setOrderStatus("");
				orders.setAddress(address);
				orders.setCreatorUserID(adminUserInfo.getUserID());
				orders.setCreatorUserName(adminUserInfo.getUserName());
				orders.setSysStatus(true);
				Boolean count = ordersInfoSer.insertinfo(orders);
				if (!count)
				{
					logger.info("手机号:" + phone + "记录出错,原因:总订单插入失败");
					snMap.put(new String(phone), "总订单插入失败");
					pm.rollback(ts);
					continue;
				}
				logger.info("总订单增加成功");
				// 设备订单
				for (int j = 0; j < SN1.length; j++)
				{
					DeviceDealOrders deviceDeal = new DeviceDealOrders();
					String deviceDealID = UUID.randomUUID().toString();
					deviceDeal.setDeviceDealID(deviceDealID);
					deviceDeal.setOrderID(orderID);
					deviceDeal.setSN(Constants.SNformat(SN1[j]));
					deviceDeal.setCustomerName(customerName);
					DeviceInfo info = deviceInfoSer.getdevicetwo(Constants.SNformat(SN1[j]));
					deviceDeal.setDeviceID(info.getDeviceID());
					if (customer == false)
					{
						deviceDeal.setCustomerID(customerID);
					}
					else
					{
						deviceDeal.setCustomerID(customerinfo.get(0).getCustomerID());
					}
					deviceDeal.setDeallType(deallType);
					deviceDeal.setDealAmount(500);
					deviceDeal.setIfFinish("是");
					deviceDeal.setIfReturn("否");
					deviceDeal.setOrderStatus("使用中");
					deviceDeal.setCreatorUserID(adminUserInfo.getUserID());
					deviceDeal.setCreatorUserName(adminUserInfo.getUserName());
					deviceDeal.setSysStatus(true);
					count = deviceDealOrdersSer.insertinfo(deviceDeal);
					if (!count)
					{
						logger.info("手机号:" + phone + "记录出错,原因:设备交易订单插入失败");
						snMap.put(new String(phone), "设备交易订单插入失败");
						pm.rollback(ts);
						jx = false;
						break;
					}
					// 将设备信息表中设备状态改为使用中
					count = deviceInfoSer.updateDeviceOrder(Constants.SNformat(SN1[j]));
					if (!count)
					{
						logger.info("手机号:" + phone + "记录出错,原因:修改设备状态为使用中失败");
						snMap.put(new String(phone), "修改设备状态为使用中失败");
						pm.rollback(ts);
						jx = false;
						break;
					}
				}
				if (jx == false)
				{
					continue;
				}
				logger.info("设备订单增加成功");
				// 流量订单
				String flowDealOrderID = "";
				for (int j = 0; j < Integer.parseInt(flowDealCount); j++)
				{
					flowDealOrderID = UUID.randomUUID().toString();
					FlowDealOrders flowDeal = new FlowDealOrders();
					flowDeal.setFlowDealID(flowDealOrderID);
					flowDeal.setOrderID(orderID);
					flowDeal.setCustomerID(customerID);
					flowDeal.setCustomerName(customerName);
					flowDeal.setOrderCreateDate(DateUtils.StrToDate(DateUtils.DateToStr(new Date())));
					flowDeal.setUserCountry(UserCountry);
					flowDeal.setPanlUserDate(panlUserDate);
					flowDeal.setOrderStatus("可使用");
					flowDeal.setIfFinish("是");
					flowDeal.setRemark(remark);
					flowDeal.setFlowDays(Integer.parseInt(flowDays));
					flowDeal.setDaysRemaining(Integer.parseInt(flowDays));
					flowDeal.setOrderAmount(Double.parseDouble(orderAmount));
					flowDeal.setFlowExpireDate(DateUtils.getcurDate(DateUtils.beforeNDateToString(DateUtils.parseDate(panlUserDate), Integer.parseInt(flowDays), "yyyy-MM-dd HH:mm:ss"), 1));// 流量到期时间
					flowDeal.setSN(Constants.SNformat(SN1[j]));
					flowDeal.setIfActivate("是");
					flowDeal.setActivateDate(DateUtils.DateToStr(new Date()));
					flowDeal.setLimitValve(Constants.LIMITVALUE);// 限速阀值
					flowDeal.setLimitSpeed(Constants.LIMITSPEED);// 限速多少
					flowDeal.setFlowUsed(0);
					flowDeal.setOrderType("2");
					flowDeal.setFlowTotal(flowTotal);
					flowDeal.setFactoryFlag(0);
					flowDeal.setCreatorUserID(adminUserInfo.getUserID());
					flowDeal.setCreatorUserName(adminUserInfo.getUserName());
					flowDeal.setSysStatus(true);
					flowDeal.setJourney(flowDealOrdersSer.getJourney(journey));
					flowDeal.setIfVPN("0");
					success = flowDealOrdersSer.insertinfo(flowDeal);
					if (!success)
					{
						logger.info("手机号:" + phone + "记录出错,原因:增加流量订单失败");
						snMap.put(new String(phone), "增加流量订单失败");
						pm.rollback(ts);
						jx = false;
						break;
					}
				}
				if (jx == false)
				{
					continue;
				}
				if (success)
				{
					insertOKCount++;
				}
				LOGGER.info("流量订单增加成功");

				pm.commit(ts);
			}
		}
		catch (NumberFormatException ee)
		{
			pm.rollback(ts);
			req.getSession().setAttribute("excelmessage", "数值转换出错");
			LOGGER.info("数值转换出错-" + ee.getMessage());
			return "redirect:returnbatchorder";
		}
		catch (Exception e)
		{
			pm.rollback(ts);
			req.getSession().setAttribute("excelmessage", "操作数据库出错！请重试或联系管理员");
			LOGGER.info(e.getMessage());
			return "redirect:returnbatchorder";
		}
		String message = "需要导入" + execlrow + "条， 　导入成功" + insertOKCount + "条，　导入失败" + snMap.size() + "条,　　以下为导入失败的订单客户手机号<br/>";
		String mess = "";
		LOGGER.info(message);
		for (String key : snMap.keySet())
		{
			mess = mess + key + ":　" + snMap.get(key) + "<br/>";
		}
		message = message + mess;
		req.getSession().setAttribute("excelmessage", message);
		return "redirect:returnbatchorder";
	}



	/**
	 * 订单导入 （按时间段使用天数，不限流量）
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
	@RequestMapping(value = "/batchaddorders3", method = RequestMethod.POST)
	public String batchaddorders3(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse resp, HttpSession session, Model model) throws FileNotFoundException, IOException
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
		int insertOKCount = 0; // 成功插入条数
		IdentityHashMap<String, String> snMap = new IdentityHashMap<String, String>();
		String phone = "";
		int execlrow = result.length;//
		logger.info("读取到数据行数:" + execlrow);
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
							execlrow = i;
							i = result.length - 1;
							break;
						}
						if (j != 3 && j < 12)
						{
							logger.info("关健字段不能为空:" + (i + 1) + "行," + (j + 1) + "列");
							req.getSession().setAttribute("excelmessage", "关健字段不能为空");
							return "redirect:returnbatchorder";
						}

					}
				}
			}
			logger.info("excel解析完毕.实际数据行数:" + execlrow);
			boolean success = false;
			for (int i = 0; i < execlrow; i++)
			{
				ts = pm.getTransaction(null);
				String customerName = result[i][0].trim();// 客户姓名
				phone = result[i][1].trim().trim();// 电话
				String address = result[i][2].trim();// 地址
				String email = result[i][3].trim();// email
				String SN = result[i][4].trim().trim();// 设备编号
				String deallType = result[i][5].trim();// 交易类型
				String countryName = result[i][6].trim();// 国家编号列表
				String oldPanlUserDate = result[i][7].trim();// 预约时间
				String flowDealCount = result[i][8].trim();// 流量订单 个数
				String oldEndDate = result[i][9].trim();// 结束时间
				String daysRemaining = result[i][10].trim();// 剩余天数
				String orderAmount = result[i][11].trim();// 总金额
				String journey = result[i][12].trim();// 用户行程
				String remark = result[i][13].trim();// 备注

				// 拼接预约时间
				String str = "";
				String panlUserDate = "20";
				for (int j = 0; j < oldPanlUserDate.length(); j++)
				{
					str = str + oldPanlUserDate.substring(j, j + 2) + ",";
					j++;
				}
				String[] panl = str.split(",");

				for (int j = 0; j < panl.length; j++)
				{
					if (j < 2)
					{
						panlUserDate = panlUserDate + panl[j] + "-";
					}
					else if (j == 2)
					{
						panlUserDate = panlUserDate + panl[j] + " ";
					}
					else if (j >= 3)
					{
						if (j == 4)
						{
							panlUserDate = panlUserDate + panl[j] + ":00";
							break;
						}
						panlUserDate = panlUserDate + panl[j] + ":";
					}
				}
				// 拼接结束时间
				String end = "";
				String endDate = "20";
				for (int j = 0; j < oldEndDate.length(); j++)
				{
					end = end + oldEndDate.substring(j, j + 2) + ",";
					j++;
				}
				String[] endD = end.split(",");

				for (int j = 0; j < endD.length; j++)
				{
					if (j < 2)
					{
						endDate = endDate + endD[j] + "-";
					}
					else if (j == 2)
					{
						endDate = endDate + endD[j] + " ";
					}
					else if (j >= 3)
					{
						if (j == 4)
						{
							endDate = endDate + endD[j] + ":00";
							break;
						}
						endDate = endDate + endD[j] + ":";
					}
				}

				String[] SN1 = SN.split("/");
				if (SN1.length != Integer.parseInt(flowDealCount))
				{
					logger.info("手机号:" + phone + "-记录出错,原因:该SN个数不等于流量订单个数");
					snMap.put(new String(phone), "该SN个数小于流量订单个数");
					pm.rollback(ts);

					continue;
				}
				List<CustomerInfo> customerinfo = customerInfoSer.selectOneCustomerinfoPhonetwo(phone);
				boolean customer = true;
				// 客户
				String customerID = UUID.randomUUID().toString();
				if (customerinfo == null || customerinfo.size() <= 0)
				{
					customer = false;
					CustomerInfo customerInfo = new CustomerInfo();
					customerInfo.setCustomerID(customerID);
					customerInfo.setCustomerName(customerName);
					customerInfo.setPhone(phone);
					customerInfo.setPassword(DES.toHexString(DES.encrypt("88888888", Constants.DES_KEYWEB)));
					customerInfo.setEmail(email);
					customerInfo.setUsername("");
					customerInfo.setCreatorUserID(adminUserInfo.getUserID());
					customerInfo.setCreatorDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
					customerInfo.setRemark("");
					customerInfo.setSysStatus(1);
					// 插入客户信息
					customerInfoSer.insertCustomerInfo(customerInfo);
				}
				else if (!customerinfo.get(0).getCustomerName().trim().equals(customerName.trim()))
				{
					logger.info("手机号:" + phone + "-记录出错,原因:该手机号码重复,请核对姓名");
					snMap.put(new String(phone), "该手机号码重复,请核对姓名");
					pm.rollback(ts);
					continue;
				}
				else if (customerinfo.get(0).getCustomerName().trim().equals(customerName.trim()))
				{
					customerID = customerinfo.get(0).getCustomerID();
				}
				logger.info("客户信息增加成功");
				Boolean jx = true;
				// 国家
				String[] country = countryName.split("/");
				String UserCountry = "";// 拼接使用国家信息
				for (int j = 0; j < country.length; j++)
				{
					// 查询国家是否存在，如何不存在提示国家名称不存在
					String c_Name = country[j];
					CountryInfo countryInfo = new CountryInfo();
					countryInfo.setCountryName(c_Name);
					List<CountryInfo> contryInfos = countryInfoSer.getcountryinfoBycountryname(countryInfo);
					if (contryInfos == null || contryInfos.size() <= 0)
					{
						// 国家名称不存在
						logger.info("手机号:" + phone + "记录出错,原因:该用户填写的使用国家名称不存在");
						snMap.put(new String(phone), "该用户填写的使用国家名称不存在");
						pm.rollback(ts);
						jx = false;
						break;
					}
					else
					{
						if (j == country.length - 1)
						{
							UserCountry = UserCountry + contryInfos.get(0).getCountryName() + "," + contryInfos.get(0).getCountryCode() + "," + contryInfos.get(0).getFlowPrice();
						}
						else
						{
							UserCountry = UserCountry + contryInfos.get(0).getCountryName() + "," + contryInfos.get(0).getCountryCode() + "," + contryInfos.get(0).getFlowPrice() + "|";
						}
					}
				}
				if (jx == false)
				{
					continue;
				}

				for (int j = 0; j < SN1.length; j++)
				{
					String deviceID = UUID.randomUUID().toString();// 设备id
					int status = deviceInfoSer.verifySN(Constants.SNformat(SN1[j]), UserCountry);
					// -1表示SN不存在。-2表示SN使用中,0表示SN或漫游卡不支持国家，1表示可以被下单.
					if (status == -2)
					{
						logger.info("SN使用中");
						snMap.put(new String(phone), SN1[j] + "设备使用中");
						pm.rollback(ts);
						jx = false;
						break;
					}
					else if (status == 0)
					{
						logger.info("SN或漫游卡不支持国家");
						snMap.put(new String(phone), SN1[j] + "设备或漫游卡不支持国家");
						pm.rollback(ts);
						jx = false;
						break;
					}
					else if (status == 1)
					{
						logger.info("可以被下单");
					}
					else if (status == -1)
					{
						if(Constants.SNformat(SN1[j])==null){
							snMap.put(new String(phone), SN1[j] + "输入有误，请输入后六位");
							pm.rollback(ts);
							jx = false;
							break;
						}
						logger.info("SN不存在");
						// 检查设备不存在，就新增一个设备
						DeviceInfo device = new DeviceInfo();
						device.setDeviceID(deviceID);
						device.setSN(Constants.SNformat(SN1[j]));
						// 漫游卡编号
						device.setCardID("");
						device.setCreatorUserID(adminUserInfo.getCreatorUserID());
						device.setSysStatus("1");
						device.setCreatorDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
						device.setCreatorUserName(adminUserInfo.getUserName());
						int count = deviceInfoSer.insertDeviceInfo(device);
						if (count <= 0)
						{
							logger.info("手机号:" + phone + "记录出错,原因:" + SN1[j] + "该设备增加失败");
							snMap.put(new String(phone), SN1[j] + " 该设备增加失败");
							pm.rollback(ts);
							jx = false;
							break;
						}
					}
				}
				if (jx == false)
				{
					continue;
				}
				logger.info("设备信息增加成功");

				// 总订单 插入一条记录
				OrdersInfo orders = new OrdersInfo();
				String orderID = UUID.randomUUID().toString();
				orders.setOrderID(orderID);
				orders.setCustomerID(customerID);
				orders.setCustomerName(customerName);
				orders.setDeviceDealCount(SN1.length);
				orders.setIfFinish("是");
				orders.setOrderStatus("已付款");
				orders.setOrderSource("后台");
				orders.setShipmentsStatus("已发货");
				orders.setFlowDealCount(Integer.parseInt(flowDealCount));
				orders.setOrderAmount(Double.parseDouble(orderAmount) + 500);
				orders.setOrderStatus("");
				orders.setAddress(address);
				orders.setCreatorUserID(adminUserInfo.getUserID());
				// 创建时间
				// orders.setCreatorDate(new Date());
				orders.setCreatorUserName(adminUserInfo.getUserName());
				orders.setSysStatus(true);
				Boolean count = ordersInfoSer.insertinfo(orders);
				if (!count)
				{
					logger.info("手机号:" + phone + "记录出错,原因:总订单插入失败");
					snMap.put(new String(phone), "总订单插入失败");
					pm.rollback(ts);
					continue;
				}
				logger.info("总订单增加成功");
				// 设备订单
				for (int j = 0; j < SN1.length; j++)
				{
					DeviceDealOrders deviceDeal = new DeviceDealOrders();
					String deviceDealID = UUID.randomUUID().toString();
					deviceDeal.setDeviceDealID(deviceDealID);
					deviceDeal.setOrderID(orderID);
					deviceDeal.setSN(Constants.SNformat(SN1[j]));
					deviceDeal.setCustomerName(customerName);
					DeviceInfo info = deviceInfoSer.getdevicetwo(Constants.SNformat(SN1[j]));
					deviceDeal.setDeviceID(info.getDeviceID());
					if (customer == false)
					{
						deviceDeal.setCustomerID(customerID);
					}
					else
					{
						deviceDeal.setCustomerID(customerinfo.get(0).getCustomerID());
					}
					deviceDeal.setDeallType(deallType);
					deviceDeal.setDealAmount(500);
					deviceDeal.setIfFinish("是");
					deviceDeal.setIfReturn("否");
					deviceDeal.setOrderStatus("使用中");
					deviceDeal.setCreatorUserID(adminUserInfo.getUserID());
					deviceDeal.setCreatorUserName(adminUserInfo.getUserName());
					// 创建时间
					// deviceDeal.setCreatorDate(new Date());
					deviceDeal.setSysStatus(true);
					count = deviceDealOrdersSer.insertinfo(deviceDeal);
					if (!count)
					{
						logger.info("手机号:" + phone + "记录出错,原因:设备交易订单插入失败");
						snMap.put(new String(phone), "设备交易订单插入失败");
						pm.rollback(ts);
						jx = false;
						break;
					}
					// 将设备信息表中设备状态改为使用中
					count = deviceInfoSer.updateDeviceOrder(Constants.SNformat(SN1[j]));
					if (!count)
					{
						logger.info("手机号:" + phone + "记录出错,原因:修改设备状态为使用中失败");
						snMap.put(new String(phone), "修改设备状态为使用中失败");
						pm.rollback(ts);
						jx = false;
						break;
					}
				}
				if (jx == false)
				{
					continue;
				}
				logger.info("设备订单增加成功");
				// 流量订单
				String flowDealOrderID = "";
				for (int j = 0; j < Integer.parseInt(flowDealCount); j++)
				{
					flowDealOrderID = UUID.randomUUID().toString();
					FlowDealOrders flowDeal = new FlowDealOrders();
					flowDeal.setFlowDealID(flowDealOrderID);
					flowDeal.setOrderID(orderID);
					flowDeal.setCustomerID(customerID);
					flowDeal.setCustomerName(customerName);
					flowDeal.setOrderCreateDate(DateUtils.StrToDate(DateUtils.DateToStr(new Date())));
					flowDeal.setUserCountry(UserCountry);
					flowDeal.setPanlUserDate(panlUserDate); // 预约时间
					flowDeal.setOrderStatus("可使用");
					flowDeal.setIfFinish("是");
					flowDeal.setRemark(remark);
					flowDeal.setFlowDays(Integer.parseInt(daysRemaining));// 流量天数
					flowDeal.setDaysRemaining(Integer.parseInt(daysRemaining));// 剩余天数
					flowDeal.setOrderAmount(Double.parseDouble(orderAmount));// 订单金额
					flowDeal.setFlowExpireDate(endDate);// 流量到期时间
					flowDeal.setSN(Constants.SNformat(SN1[j]));
					flowDeal.setIfActivate("是");// 是否激活
					flowDeal.setActivateDate(DateUtils.DateToStr(new Date()));// 激活时间为当前时间
					flowDeal.setLimitValve(Constants.LIMITVALUE);// 限速阀值
					flowDeal.setLimitSpeed(Constants.LIMITSPEED);// 限速多少
					flowDeal.setFlowUsed(0);
					flowDeal.setFactoryFlag(0);// 工厂标识
					flowDeal.setOrderType("3");
					flowDeal.setCreatorUserID(adminUserInfo.getUserID());
					flowDeal.setCreatorUserName(adminUserInfo.getUserName());
					flowDeal.setSysStatus(true);
					flowDeal.setJourney(flowDealOrdersSer.getJourney(journey));
					flowDeal.setIfVPN("0");
					success = flowDealOrdersSer.insertinfo(flowDeal);
					if (!success)
					{
						logger.info("手机号:" + phone + "记录出错,原因:增加流量订单失败");
						snMap.put(new String(phone), "增加流量订单失败");
						pm.rollback(ts);
						jx = false;
						break;
					}
				}
				if (jx == false)
				{
					continue;
				}
				if (success)
				{
					insertOKCount++;
				}
				LOGGER.info("流量订单增加成功");

				pm.commit(ts);
			}
		}
		catch (NumberFormatException ee)
		{
			pm.rollback(ts);
			req.getSession().setAttribute("excelmessage", "数值转换出错");
			LOGGER.info("数值转换出错-" + ee.getMessage());
			return "redirect:returnbatchorder";
		}
		catch (Exception e)
		{
			pm.rollback(ts);
			req.getSession().setAttribute("excelmessage", "操作数据库出错！请重试或联系管理员");
			LOGGER.info(e.getMessage());
			return "redirect:returnbatchorder";
		}
		// 遍历map中的键 拿出导入失败的SN;
		String message = "需要导入" + execlrow + "条， 　导入成功" + insertOKCount + "条，　导入失败" + snMap.size() + "条,　　以下为导入失败的订单客户手机号<br/>";
		String mess = "";
		LOGGER.info(message);
		for (String key : snMap.keySet())
		{
			mess = mess + key + ":　" + snMap.get(key) + "<br/>";
		}
		message = message + mess;
		req.getSession().setAttribute("excelmessage", message);
		return "redirect:returnbatchorder";
	}



	/**
	 * 订单导入 （按天数天限流量）
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
	@RequestMapping(value = "/batchaddorders1", method = RequestMethod.POST)
	public String batchaddorders1(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse resp, String orderType, HttpSession session, Model model) throws FileNotFoundException, IOException
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
		int insertOKCount = 0;
		IdentityHashMap<String, String> snMap = new IdentityHashMap<String, String>();
		String phone = "";
		int execlrow = result.length;
		logger.info("读取到数据行数:" + execlrow);
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
							execlrow = i;
							i = result.length - 1;
							break;
						}
						if (j != 3 && j < 11)
						{
							logger.info("关健字段不能为空:" + (i + 1) + "行," + (j + 1) + "列");
							req.getSession().setAttribute("excelmessage", "关健字段不能为空");
							return "redirect:returnbatchorder";
						}

					}
				}
			}
			logger.info("excel解析完毕.实际数据行数:" + execlrow);
			boolean success = false;
			for (int i = 0; i < execlrow; i++)
			{
				ts = pm.getTransaction(null);



				String customerName = result[i][0].trim();// 客户姓名
				phone = result[i][1].trim();// 电话
				String address = result[i][2];// 地址
				String email = result[i][3];// email
				String SN = result[i][4].trim();// 设备编号
				String deallType = result[i][5];// 交易类型
				String countryName = result[i][6];// 国家编号列表
				String oldPanlUserDate = result[i][7];// 预约时间
				String flowDealCount = result[i][8];// 流量订单 个数
				String flowDays = result[i][9];// 天数
				String orderAmount = result[i][10];// 总金额
				String journey = result[i][11];// 用户行程
				String remark = result[i][12];// 备注




				// 拼接预约时间
				String str = "";
				String panlUserDate = "20";
				for (int j = 0; j < oldPanlUserDate.length(); j++)
				{
					str = str + oldPanlUserDate.substring(j, j + 2) + ",";
					j++;
				}
				String[] panl = str.split(",");
				for (int j = 0; j < panl.length; j++)
				{
					if (j < 2)
					{
						panlUserDate = panlUserDate + panl[j] + "-";
					}
					else if (j == 2)
					{
						panlUserDate = panlUserDate + panl[j] + " ";
					}
					else if (j >= 3)
					{
						if (j == 4)
						{
							panlUserDate = panlUserDate + panl[j] + ":00";
							break;
						}
						panlUserDate = panlUserDate + panl[j] + ":";
					}
				}

				String[] SN1 = SN.split("/");
				if (SN1.length != Integer.parseInt(flowDealCount))
				{
					logger.info("手机号:" + phone + "-记录出错,原因:该SN个数不等于流量订单个数");
					snMap.put(new String(phone), "该SN个数小于流量订单个数");
					pm.rollback(ts);

					continue;
				}
				List<CustomerInfo> customerinfo = customerInfoSer.selectOneCustomerinfoPhonetwo(phone);
				boolean customer = true;
				// 客户
				String customerID = UUID.randomUUID().toString();
				if (customerinfo == null || customerinfo.size() <= 0)
				{
					customer = false;
					CustomerInfo customerInfo = new CustomerInfo();
					customerInfo.setCustomerID(customerID);
					customerInfo.setCustomerName(customerName);
					customerInfo.setPhone(phone);
					customerInfo.setPassword(DES.toHexString(DES.encrypt("88888888", Constants.DES_KEYWEB)));
					customerInfo.setEmail(email);
					customerInfo.setUsername("");
					customerInfo.setCreatorUserID(adminUserInfo.getUserID());
					customerInfo.setCreatorDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
					customerInfo.setRemark("");
					customerInfo.setSysStatus(1);
					// 插入客户信息
					customerInfoSer.insertCustomerInfo(customerInfo);
				}
				else if (!customerinfo.get(0).getCustomerName().trim().equals(customerName.trim()))
				{
					logger.info("手机号:" + phone + "-记录出错,原因:该手机号码重复,请核对姓名");
					snMap.put(new String(phone), "该手机号码重复,请核对姓名");
					pm.rollback(ts);
					continue;
				}
				else if (customerinfo.get(0).getCustomerName().trim().equals(customerName.trim()))
				{
					customerID = customerinfo.get(0).getCustomerID();
				}
				logger.info("客户信息增加成功");
				// 国家
				Boolean jx = true;
				String[] country = countryName.split("/");
				String UserCountry = "";
				// 拼接使用国家信息
				for (int j = 0; j < country.length; j++)
				{
					String c_Name = country[j];
					CountryInfo countryInfo = new CountryInfo();
					countryInfo.setCountryName(c_Name);
					List<CountryInfo> contryInfos = countryInfoSer.getcountryinfoBycountryname(countryInfo);
					if (contryInfos == null || contryInfos.size() <= 0)
					{
						logger.info("手机号:" + phone + "记录出错,原因:该用户填写的使用国家名称不存在");
						snMap.put(new String(phone), "该用户填写的使用国家名称不存在");
						pm.rollback(ts);
						jx = false;
						break;
					}
					else
					{
						if (j == country.length - 1)
						{
							UserCountry = UserCountry + contryInfos.get(0).getCountryName() + "," + contryInfos.get(0).getCountryCode() + "," + contryInfos.get(0).getFlowPrice();
						}
						else
						{
							UserCountry = UserCountry + contryInfos.get(0).getCountryName() + "," + contryInfos.get(0).getCountryCode() + "," + contryInfos.get(0).getFlowPrice() + "|";
						}
					}
				}
				if (jx == false)
				{
					continue;
				}
				for (int j = 0; j < SN1.length; j++)
				{
					String deviceID = UUID.randomUUID().toString();// 设备id

					int status = deviceInfoSer.verifySN(Constants.SNformat(SN1[j]), UserCountry);
					// -1表示SN不存在。-2表示SN使用中,0表示SN或漫游卡不支持国家，1表示可以被下单.
					if (status == -2)
					{
						logger.info("SN使用中");
						snMap.put(new String(phone), SN1[j] + "设备使用中");
						pm.rollback(ts);
						jx = false;
						break;
					}
					else if (status == 0)
					{
						logger.info("SN或漫游卡不支持国家");
						snMap.put(new String(phone), SN1[j] + "设备或漫游卡不支持国家");
						pm.rollback(ts);
						jx = false;
						break;
					}
					else if (status == 1)
					{
						logger.info("可以被下单");
					}
					else if (status == -1)
					{
						if(Constants.SNformat(SN1[j])==null){
							snMap.put(new String(phone), SN1[j] + "输入有误，请输入后六位");
							pm.rollback(ts);
							jx = false;
							break;
						}
						logger.info("SN不存在");
						// 检查设备不存在，就新增一个设备
						DeviceInfo device = new DeviceInfo();
						device.setDeviceID(deviceID);
						device.setSN(Constants.SNformat(SN1[j]));
						device.setCardID("");
						device.setCreatorUserID(adminUserInfo.getCreatorUserID());
						device.setSysStatus("1");
						device.setCreatorDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
						device.setCreatorUserName(adminUserInfo.getUserName());
						int count = deviceInfoSer.insertDeviceInfo(device);
						if (count <= 0)
						{
							logger.info("手机号:" + phone + "记录出错,原因:" + SN1[j] + "该设备增加失败");
							snMap.put(new String(phone), SN1[j] + " 该设备增加失败");
							pm.rollback(ts);
							jx = false;
							break;
						}
					}
				}
				if (jx == false)
				{
					continue;
				}
				logger.info("设备信息增加成功");
				// 总订单 插入一条记录
				OrdersInfo orders = new OrdersInfo();
				String orderID = UUID.randomUUID().toString();
				orders.setOrderID(orderID);
				orders.setCustomerID(customerID);
				orders.setCustomerName(customerName);
				orders.setDeviceDealCount(SN1.length);
				orders.setIfFinish("是");
				orders.setOrderStatus("已付款");
				orders.setOrderSource("后台");
				orders.setShipmentsStatus("已发货");
				orders.setFlowDealCount(Integer.parseInt(flowDealCount));
				orders.setOrderAmount(Double.parseDouble(orderAmount) + 500);
				orders.setOrderStatus("");
				orders.setAddress(address);
				orders.setCreatorUserID(adminUserInfo.getUserID());
				orders.setCreatorUserName(adminUserInfo.getUserName());
				orders.setSysStatus(true);
				Boolean count = ordersInfoSer.insertinfo(orders);
				if (!count)
				{
					logger.info("手机号:" + phone + "记录出错,原因:总订单插入失败");
					snMap.put(new String(phone), "总订单插入失败");
					pm.rollback(ts);
					continue;
				}
				logger.info("总订单增加成功");
				// 设备订单
				for (int j = 0; j < SN1.length; j++)
				{
					DeviceDealOrders deviceDeal = new DeviceDealOrders();
					String deviceDealID = UUID.randomUUID().toString();
					deviceDeal.setDeviceDealID(deviceDealID);
					deviceDeal.setOrderID(orderID);
					deviceDeal.setSN(Constants.SNformat(SN1[j]));
					deviceDeal.setCustomerName(customerName);
					DeviceInfo info = deviceInfoSer.getdevicetwo(Constants.SNformat(SN1[j]));
					deviceDeal.setDeviceID(info.getDeviceID());
					if (customer == false)
					{
						deviceDeal.setCustomerID(customerID);
					}
					else
					{
						deviceDeal.setCustomerID(customerinfo.get(0).getCustomerID());
					}
					deviceDeal.setDeallType(deallType);
					deviceDeal.setDealAmount(500);
					deviceDeal.setIfFinish("是");
					deviceDeal.setIfReturn("否");
					deviceDeal.setOrderStatus("使用中");
					deviceDeal.setCreatorUserID(adminUserInfo.getUserID());
					deviceDeal.setCreatorUserName(adminUserInfo.getUserName());
					deviceDeal.setSysStatus(true);
					count = deviceDealOrdersSer.insertinfo(deviceDeal);
					if (!count)
					{
						logger.info("手机号:" + phone + "记录出错,原因:设备交易订单插入失败");
						snMap.put(new String(phone), "设备交易订单插入失败");
						pm.rollback(ts);
						jx = false;
						break;
					}
					// 将设备信息表中设备状态改为使用中
					count = deviceInfoSer.updateDeviceOrder(Constants.SNformat(SN1[j]));
					if (!count)
					{
						logger.info("手机号:" + phone + "记录出错,原因:修改设备状态为使用中失败");
						snMap.put(new String(phone), Constants.SNformat(SN1[j]) + "修改设备状态为使用中失败");
						pm.rollback(ts);
						jx = false;
						break;
					}
				}
				if (jx == false)
				{
					continue;
				}
				logger.info("设备订单增加成功");
				// 流量订单
				String flowDealOrderID = "";
				for (int j = 0; j < Integer.parseInt(flowDealCount); j++)
				{
					flowDealOrderID = UUID.randomUUID().toString();
					FlowDealOrders flowDeal = new FlowDealOrders();
					flowDeal.setFlowDealID(flowDealOrderID);
					flowDeal.setOrderID(orderID);
					flowDeal.setCustomerID(customerID);
					flowDeal.setCustomerName(customerName);
					flowDeal.setOrderCreateDate(DateUtils.StrToDate(DateUtils.DateToStr(new Date())));
					flowDeal.setUserCountry(UserCountry);
					flowDeal.setPanlUserDate(panlUserDate); // 预约时间
					flowDeal.setOrderStatus("可使用");
					flowDeal.setIfFinish("是");
					flowDeal.setRemark(remark);
					flowDeal.setFlowDays(Integer.parseInt(flowDays));// 流量天数
					flowDeal.setDaysRemaining(Integer.parseInt(flowDays));// 剩余天数
					flowDeal.setOrderAmount(Double.parseDouble(orderAmount));// 订单金额
					flowDeal.setFlowExpireDate(DateUtils.getcurDate(DateUtils.beforeNDateToString(DateUtils.parseDate(panlUserDate), Integer.parseInt(flowDays), "yyyy-MM-dd HH:mm:ss"), 1));// 流量到期时间
					flowDeal.setSN(Constants.SNformat(SN1[j]));
					flowDeal.setIfActivate("是");// 是否激活
					flowDeal.setOrderType("1");
					flowDeal.setActivateDate(DateUtils.DateToStr(new Date()));// 激活时间为当前时间
					flowDeal.setLimitValve(Constants.LIMITVALUE);// 限速阀值
					flowDeal.setLimitSpeed(Constants.LIMITSPEED);// 限速多少
					flowDeal.setFlowUsed(0);
					flowDeal.setFactoryFlag(0);// 工厂标识
					flowDeal.setCreatorUserID(adminUserInfo.getUserID());
					flowDeal.setCreatorUserName(adminUserInfo.getUserName());
					flowDeal.setSysStatus(true);
					flowDeal.setJourney(flowDealOrdersSer.getJourney(journey));
					flowDeal.setIfVPN("0");
					success = flowDealOrdersSer.insertinfo(flowDeal);
					if (!success)
					{
						logger.info("手机号:" + phone + "记录出错,原因:增加流量订单失败");
						snMap.put(new String(phone), "增加流量订单失败");
						pm.rollback(ts);
						jx = false;
						break;
					}
				}
				if (jx == false)
				{
					continue;
				}
				if (success)
				{
					insertOKCount++;
				}
				LOGGER.info("流量订单增加成功");

				pm.commit(ts);
			}
		}
		catch (NumberFormatException ee)
		{
			pm.rollback(ts);
			req.getSession().setAttribute("excelmessage", "数值转换出错");
			LOGGER.info("数值转换出错-" + ee.getMessage());
			return "redirect:returnbatchorder";
		}
		catch (Exception e)
		{
			pm.rollback(ts);
			req.getSession().setAttribute("excelmessage", "操作数据库出错！请重试或联系管理员");
			LOGGER.info(e.getMessage());
			return "redirect:returnbatchorder";
		}
		String message = "需要导入" + execlrow + "条， 　导入成功" + insertOKCount + "条，　导入失败" + snMap.size() + "条,　　以下为导入失败的订单客户手机号<br/>";
		String mess = "";
		LOGGER.info(message);
		for (String key : snMap.keySet())
		{
			mess = mess + key + ":　" + snMap.get(key) + "<br/>";
		}
		message = message + mess;
		req.getSession().setAttribute("excelmessage", message);
		return "redirect:returnbatchorder";
	}

	/**
	 * address 地址，logisticsCost 运费， LogisticsJC 快递简称， expressNo
	 * 快递单号，deviceStatus 设备库存状态 设备入库出库记录
	 *
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public Shipment DeviceLogShipment(String SN, String address, String logisticsCost, String LogisticsJC, String expressNo, String deviceStatus, String remark, String recipientName) throws UnsupportedEncodingException
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
		return shipm;
	}

}