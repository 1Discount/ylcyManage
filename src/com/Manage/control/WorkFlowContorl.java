package com.Manage.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.Manage.common.util.LogUtil;

@Controller

@RequestMapping("/workflow")

public class WorkFlowContorl extends BaseController
{

	private Logger logger = LogUtil.getInstance(WorkFlowContorl.class);

	@RequestMapping("/gooflow")
	public String index(String orderID, HttpServletResponse response,
			HttpServletRequest request, Model model)
	{

		String orderSource = "线上网店";

		String workFlowStatus = "设备出库";

		String shipmentType = "快递";

		model.addAttribute("orderSource", orderSource);

		model.addAttribute("workFlowStatus", workFlowStatus);
		
		model.addAttribute("shipmentType", shipmentType);

		return "WEB-INF/views/orders/gooflow";
	}
}
