package com.Manage.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.restlet.data.Form;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.Manage.common.constants.Constants;
import com.Manage.common.constants.RestConstants;
import com.Manage.common.util.CountryUtils;
import com.Manage.common.util.DES;
import com.Manage.common.util.DateUtils;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.CustomerInfo;
import com.Manage.entity.DeviceDealOrders;
import com.Manage.entity.Dictionary;
import com.Manage.entity.FlowDealOrders;
import com.Manage.entity.OrdersInfo;
import com.Manage.entity.common.SearchDTO;
import com.Manage.entity.report.YouzanSyncOrderResult;
import com.kdt.api.KDTApi;
import com.kdt.api.YouzanConfig;
import com.kdt.api.entity.LogisticsTrace.trace_list_item;
import com.kdt.api.entity.TradeBuyerMessage;
import com.kdt.api.entity.TradeDetail;
import com.kdt.api.entity.TradeOrder;
import com.kdt.api.result.LogisticsTraceWrapper;
import com.kdt.api.result.TradesSoldListWrapper;
import com.sun.org.apache.xpath.internal.operations.And;

@Controller
@RequestMapping("/orders/youzan")
public class OrdersYouzanControl extends BaseController {
	private Logger logger = LogUtil.getInstance(OrdersYouzanControl.class);

	/**
	 * 分页查询已同步的有赞订单列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");

		List<Dictionary> orderStatus = dictionarySer.getAllList(Constants.DICT_ORDERINFO_ORDERSTATUS);
//		List<Dictionary> orderSources = dictionarySer.getAllList(Constants.DICT_ORDERINFO_ORDERSOURCE);
		model.addAttribute("OrderStatusDict", orderStatus);
//		model.addAttribute("OrderSourceDict", orderSources);
		return "WEB-INF/views/orders/youzan_orderlist";

	}

	/**
	 * 分页查询
	 * @param searchDTO
	 * @param queryInfo
	 * @param response
	 */
	@RequestMapping("/getpage")
	public void datapage(SearchDTO searchDTO, OrdersInfo queryInfo,
			HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");

		queryInfo.setSysStatus(true);
		queryInfo.setIfFinish("是");
		queryInfo.setOrderSource("有赞");

		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), queryInfo);
		String jsonString = ordersInfoSer.getpageString(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 同步有赞平台订单
	 *
	 * @param queryInfo
	 * @param response
	 * @throw IOException
	 */
	@RequestMapping("/sync")
	public void sync(OrdersInfo paramInfo, HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		JSONObject jsonResult = new JSONObject();

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");
		YouzanSyncOrderResult result = ordersInfoSer.syncYouzanOrder(adminUserInfo);

		jsonResult.put("code", result.getErrorCode());
		jsonResult.put("msg", result.getMsg());
		response.getWriter().println(jsonResult.toString());
	}

	@RequestMapping("/logistics")
	public void cancelPaidOrder(String name, String num, String tid, HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		JSONObject result = new JSONObject();

		AdminUserInfo loginAdminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");
		if (loginAdminUserInfo == null) {
			try {
				result.put("code", "-5");
				result.put("msg", "请重新登录");
				response.getWriter().print(result.toString());
			} catch (IOException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
			return;
		}
		if (StringUtils.isBlank(name) || StringUtils.isBlank(num) || StringUtils.isBlank(tid)) {
			try {
				result.put("code", "-1");
				result.put("msg", "参数不完整");
				response.getWriter().print(result.toString());
			} catch (IOException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
			return;
		}
		logger.info("查询有赞物流开始");

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("tid", tid); // 有赞同步后的设备订单ID格式为tid-oid
		LogisticsTraceWrapper wrapper = KDTApi.logisticsTraceSearch(params);
		if (null != wrapper && wrapper.isSuccess()) {
			//logger.info(wrapper.getResponse().getCompany_name());
			List<trace_list_item> info = wrapper.getResponse().getTrace_list();
			String infoString = "";
			for (trace_list_item item : info) {
				infoString += item.getStatus_time() + ": " + item.getStatus_desc() + "<br />";
			}
			logger.info(infoString);

			try {
				logger.info("查询有赞物流OK");
				result.put("code", "0");
				result.put("msg", "ok");
				result.put("data", infoString);
				response.getWriter().print(result.toString());
			} catch (IOException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		} else {
			logger.info("error_response code=" + wrapper.getError_response().getCode()
			+ " msg=" + wrapper.getError_response().getMsg());
			try {
				result.put("code", "-2");
				result.put("msg", "查询有赞接口失败");
				result.put("data", "error_response code=" + wrapper.getError_response().getCode()
						+ "<br />msg=" + wrapper.getError_response().getMsg());
				response.getWriter().print(result.toString());
			} catch (IOException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}

	}

}
