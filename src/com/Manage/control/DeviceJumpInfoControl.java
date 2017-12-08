package com.Manage.control;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Manage.common.util.DateUtil;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.DeviceJumpInfo;
import com.Manage.entity.Dictionary;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.TDMInfo;
import com.Manage.entity.common.SearchDTO;

@Controller
@RequestMapping("/deviceJumpInfo")
public class DeviceJumpInfoControl extends BaseController {
    
    	@RequestMapping("/index")
    	public String toAdd(HttpServletResponse response, HttpServletRequest request, Model model){
    	    TDMInfo tdm=new TDMInfo();
    	    tdm.setSysStatus(1);
    	    tdm.setTdmStatus(1);
    	    List<TDMInfo> tdmList=tdmInfoSer.getList(tdm);
    	    model.addAttribute("tdmList",tdmList);
    	    return "WEB-INF/views/deviceJumpInfo/deviceJumpInfo_list";
    	}

    	/**
	 * 根据sn添加设备跳转记录
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/addJump")
	public void addJump(DeviceJumpInfo jump, HttpServletResponse response) throws IOException
	{
	    	AdminUserInfo admin=(AdminUserInfo) getSession().getAttribute("User");
	    	if(admin==null){
	    	    response.getWriter().print("2");
	    	    return;
	    	}
		if (jump.getNewTDM().equals(""))
		{
			response.getWriter().print("-1");
			return;
		}
		if (jump.getSn().equals(""))
		{
			response.getWriter().print("-2");
			return;
		}
		FlowDealOrders dealOrders = new FlowDealOrders();
		dealOrders.setIfFinish("是");
		dealOrders.setSysStatus(true);
		dealOrders.setSN(jump.getSn());
		dealOrders.setModifyDateString(DateUtils.getTime());
		List<FlowDealOrders> flowDealOrders = flowDealOrdersSer.selectFlowdeal(dealOrders);
		if (flowDealOrders == null)
		{
			response.getWriter().print("0");
			return;
		}
		else
		{
			String FlowDealID = flowDealOrders.get(0).getFlowDealID();
			// 流量订单信息的factoryFlag改为1，将所选的平台的value更新到serverInfo字段
			FlowDealOrders flowDeal = new FlowDealOrders();
			flowDeal.setFlowDealID(FlowDealID);
			flowDeal.setServerInfo(jump.getNewTDM());
			// 将factoryFlag改为1 //将value更新到serverInfo字段
			int count = flowDealOrdersSer.updateFlowdealByflowDealID(flowDeal);
			if (count > 0)
			{
			    //添加跳转记录
			    jump.setCreateUserId(admin.getUserID());
			    jump.setCreateUserName(admin.getUserName());
			    jump.setFlowOrderId(flowDeal.getFlowDealID());
			    deviceJumpInfoSer.addJump(jump);
			    response.getWriter().print("1");
			}
		}
	}
	
	@RequestMapping("/list")
	public void datapage(SearchDTO searchDTO,DeviceJumpInfo d,HttpServletResponse response){
	    response.setContentType("application/json;charset=UTF-8");
	    d.setSysStatus(1);
	    SearchDTO seDto=new SearchDTO(searchDTO.getCurPage(),searchDTO.getPageSize(),searchDTO.getSortName(),searchDTO.getSortOrder(),d);
	    String jsonString=deviceJumpInfoSer.getpageString(seDto);
	    try {
		response.getWriter().println(jsonString);
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    	
}