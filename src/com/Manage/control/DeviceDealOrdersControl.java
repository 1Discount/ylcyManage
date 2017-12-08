package com.Manage.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Manage.common.util.DateUtils;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.DeviceDealOrders;
import com.Manage.entity.Distributor;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.common.SearchDTO;

/**
 * * @author wangbo: * @date 创建时间：2015-5-28 下午6:37:44 * @version 1.0 * @parameter
 * * @since * @return
 */
@Controller
@RequestMapping("/orders/devicedealorders")
public class DeviceDealOrdersControl extends BaseController {
	private final static String RETURNROOT_STRING = "WEB-INF/views/orders/";

	/**
	 * 跳转到设备交易查询界面
	 *
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model) {
		List<Distributor> disAll = distributorSer.getAll("");
		model.addAttribute("disAll", disAll);
		return RETURNROOT_STRING + "deviceorder_list";
	}

	/**
	 * 编辑入口
	 *
	 * @param SN
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit")
	public String eidt(String deviceDealID, Model model) {
		DeviceDealOrders dealOrders = deviceDealOrdersSer.getbyid(deviceDealID);
		if (dealOrders == null) {
			return RETURNROOT_STRING + "deviceorder_list";
		}
		model.addAttribute("devorder", dealOrders);
		return RETURNROOT_STRING + "deviceorder_edit";
	}

	/**
	 * 统计查询界面入口
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/statistics")
	public String statistics(Model model) {
		return RETURNROOT_STRING + "deviceorder_statistics";
	}

	/**
	 * 分页查询
	 * @param searchDTO
	 * @param menu
	 * @param response
	 */
	@RequestMapping("/getpage")
	public void getpage(SearchDTO searchDTO, DeviceDealOrders dealOrders,String limitdays,Distributor distributor,
			HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		String id = "";
		if(distributor.getCompany()!=null && !"".equals(distributor.getCompany())){
			Distributor distributor2 = distributorSer.getdisInofbycompany(distributor);
			if(distributor2!=null){
			    id = distributor2.getDistributorID();
			    dealOrders.setDeviceDealID("QD"+id);
			}
		}
		dealOrders.setSysStatus(true);
		if(limitdays!=null && !"".equals(limitdays)){
			dealOrders.setEnddate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
		    dealOrders.setBegindate(DateUtils.beforeNDateToString(null,Integer.parseInt(limitdays),"yyyy-MM-dd HH:mm:ss"));
		}
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), dealOrders);
		String pagesString = deviceDealOrdersSer.getpageString(seDto);
		try {
			response.getWriter().println(pagesString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存编辑
	 */
	@RequestMapping("/editsave")
	public void editsave(DeviceDealOrders deviceDealOrders,
			HttpServletResponse response) {
		AdminUserInfo loginAdminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");
		if (loginAdminUserInfo == null) {
			try {
				response.getWriter().print("请重新登录!"); // println
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//logger.debug(e.getMessage());
				e.printStackTrace();
			}
			return;
		}

		try {
			if (deviceDealOrders == null
					|| deviceDealOrders.getDeviceDealID() == null
					|| "".equals(deviceDealOrders.getDeviceDealID())) {
				response.getWriter().print("-1");
				return;
			}
//			if (getSession().getAttribute("User") != null) {
//				AdminUserInfo userInfo = (AdminUserInfo) getSession()
//						.getAttribute("User");
				deviceDealOrders.setModifyUserID(loginAdminUserInfo.getUserID());
//			}
				boolean flage=false;
			//如果是修改已经归还的话,将对应的流量订单状态改为不可用,并且将该设备状态改为可用.
			if ("是".equals(deviceDealOrders.getIfReturn())) {
				deviceDealOrders.setReturnDate("");
				deviceDealOrders.setOrderStatus("已过期");
				flage=true;
			}

			if (deviceDealOrdersSer.editsave(deviceDealOrders)) {
				if(flage){
					FlowDealOrders fdo=new FlowDealOrders();
					fdo.setOrderStatus("不可用");
					fdo.setSN(deviceDealOrders.getSN());
					flowDealOrdersSer.updatebysn(fdo);

					deviceInfoSer.updateReturn(deviceDealOrders.getSN());
				}

				response.getWriter().print("1");
			} else {
				response.getWriter().print("0");
			}

			try {
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());//id
				// admin.setCreatorDate(date);//创建时间
				admin.setCreatorUserID(loginAdminUserInfo.getUserID());//创建人ID
				admin.setCreatorUserName(loginAdminUserInfo.getUserName());//创建人姓名
				//admin.setOperateDate(date);//操作时间
				//admin.setSysStatus(1);

				admin.setOperateContent("已修改设备交易订单, 记录ID为: " + deviceDealOrders.getDeviceDealID() + " 设备SN: " + deviceDealOrders.getSN() + " 客户: " + deviceDealOrders.getCustomerName());
				admin.setOperateMenu("订单管理>设备交易订单修改");
				admin.setOperateType("修改");

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * 设备详细
	 *
	 * @param deviceDealID
	 * @param model
	 * @return
	 */
	@RequestMapping("/info")
	public String info(String deviceDealID, Model model) {
		DeviceDealOrders dealOrders = deviceDealOrdersSer.getbyid(deviceDealID);
		if (dealOrders == null) {
			return RETURNROOT_STRING + "deviceorder_list";
		}
		dealOrders.setCreatorDateString(DateUtils.formatDate(
				dealOrders.getCreatorDate(), "yyyy-MM-dd HH:mm:ss"));
		if (dealOrders.getModifyDate() != null)
			dealOrders.setModifyDateString(DateUtils.formatDate(
					dealOrders.getModifyDate(), "yyyy-MM-dd HH:mm:ss"));
		model.addAttribute("devorder", dealOrders);
		return RETURNROOT_STRING + "deviceorder_view";
	}

	/**
	 * 统计查询
	 *
	 * @param dealOrders
	 * @param response
	 */
	@RequestMapping("/getstatistics")
	public void getstatistics(DeviceDealOrders dealOrders,
			HttpServletResponse response) {
		try {
			Map<String, String> map = deviceDealOrdersSer
					.statistics(dealOrders);
			JSONObject object = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			JSONObject obj = new JSONObject();
			if (map != null) {
				obj.put("count", map.get("count"));
				obj.put("efficient", map.get("efficient"));
				obj.put("buycount", map.get("buycount"));
				obj.put("unfinished", map.get("unfinished"));
				obj.put("rentcount", map.get("rentcount"));
				obj.put("delcount", map.get("delcount"));
				obj.put("amount", map.get("amount"));
			}
			jsonArray.add(obj);
			object.put("data", jsonArray);
			object.put("success", true);
			object.put("totalRows", 1);
			response.getWriter().print(object.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	@RequestMapping("/checkreturndevice")
	public void isReturnForDeviceInfo(HttpServletResponse response,HttpServletRequest request){
		String sn = request.getParameter("sn");
		System.out.println("查询的sn是："+sn);

		int count = deviceDealOrdersSer.isReturnForDeviceInfoSer(sn);
		try {
			response.getWriter().print(count);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/devicedealinfo")
	public String devicedealinfo(String status, HttpServletResponse response,HttpServletRequest request,Model model){
		model.addAttribute("status", status);
		return RETURNROOT_STRING+"devicedealinfo";
	}
	@RequestMapping("/getdevicedealinfo")
	public void getdevicedealinfo(String status,DeviceDealOrders deviceDealOrders,SearchDTO searchDTO,
			HttpServletResponse response,HttpServletRequest request){
		SearchDTO seDto= new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(),status);

		String pagesString = "";
		//设备订单总数orderTote   // 正常订单数normalOrder //正常订单总金额amount //购买buy // 租用rent//已删除订单数deleted // 未完成订单数notFinsh
		if("orderTote".equals(status)){
			// pagesString =
		}else if("normalOrder".equals(status)){
			// pagesString =
		}else if("buy".equals(status)){
			 pagesString =deviceDealOrdersSer.getdevicedealinfo(seDto);
		 }else if("rent".equals(status)){
			 pagesString =deviceDealOrdersSer.getdevicedealinfo(seDto);
		 }else if("deleted".equals(status)){
			 //pagesString =
		 }else if("notFinsh".equals(status)){
			 //pagesString =
		 }
		try {
			response.getWriter().println(pagesString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
