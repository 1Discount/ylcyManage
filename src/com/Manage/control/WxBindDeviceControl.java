package com.Manage.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
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
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.Dictionary;
import com.Manage.entity.WxBindDevice;
import com.Manage.entity.common.SearchDTO;

@Controller
@RequestMapping("/wx/bindDevice")
public class WxBindDeviceControl extends BaseController {
	private Logger logger = LogUtil.getInstance(WxBindDeviceControl.class);

	/**
	 * 分页查询微信绑定SN关系列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");
		return "WEB-INF/views/service/wxbinddevice_index";

	}

	/**
	 * 分页查询
	 * @param searchDTO
	 * @param info
	 * @param response
	 */
	@RequestMapping("/datapage")
	public void datapage(SearchDTO searchDTO, WxBindDevice info,
			HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);
		String jsonString = wxBindDeviceSer.getPageString(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 微信绑定SN关系详情 by ID
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/view/{id}")
	public String view(@PathVariable String id, Model model) {
		WxBindDevice info = wxBindDeviceSer.getById(id);
		if (info != null && info.getBindID() != null) {

			model.addAttribute("Model", info);
		} else {
			model.addAttribute("info","此微信绑定SN关系不存在或已无效!");
		}
		return "WEB-INF/views/service/wxbinddevice_view";
	}

	/**
	 * 更新入口
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		WxBindDevice info = wxBindDeviceSer.getById(id);
		if (info != null && info.getBindID() != null) {
	        List<Dictionary> serverStatus = dictionarySer.getAllList(Constants.DICT_SIMSERVER_STATUS);
	        model.addAttribute("ServerStatusDict", serverStatus);
			model.addAttribute("Model", info);
		} else {
			model.addAttribute("info","此微信绑定SN关系不存在或已无效!");
		}
		return "WEB-INF/views/service/wxbinddevice_edit";
	}

	/**
	 * 新增记录入口
	 * @param model
	 * @return
	 */
	@RequestMapping("/new")
	public String newEntity(HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");
        List<Dictionary> serverStatus = dictionarySer.getAllList(Constants.DICT_SIMSERVER_STATUS);
        model.addAttribute("ServerStatusDict", serverStatus);
		return "WEB-INF/views/service/wxbinddevice_edit";
	}

	/**
	 * 保存记录 新增new或编辑edit提交时统一使用此接口
	 * 通过 boolean isInsert 来相应处理
	 * @param info
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void saveAction(WxBindDevice info, HttpServletRequest request,
			HttpServletResponse response, Model model) {

		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonResult = new JSONObject();

		boolean isInsert = false;
		if (StringUtils.isBlank(info.getBindID())) {
			isInsert = true;
		}

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");

		if (adminUserInfo != null) {
			if (isInsert) {
				info.setCreatorUserID(adminUserInfo.getUserID());
				info.setCreatorUserName(adminUserInfo.getUserName());
			} else {
				info.setModifyUserID(adminUserInfo.getUserID());
				// info.setModifyUserName(adminUserInfo.getUserName());
			}
		} else {
			try {
				jsonResult.put("code", 2);
				jsonResult.put("msg", "请重新登录!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}

			return;
		}

		boolean result;

		// try {
		if (isInsert) {
			info.setBindID(getUUID());
			info.setSysStatus(1);

			result = wxBindDeviceSer.insertInfo(info);
		} else {
			result = wxBindDeviceSer.updateInfo(info);
		}
		// } catch (Exception e) {
		// result = false;
		// e.printStackTrace();
		// }

		if (result) {
			logger.info("微信绑定SN关系保存成功");
			try {
				jsonResult.put("code", 0);
				jsonResult.put("msg", "成功保存微信绑定SN关系!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}

			try {
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());//id
				// admin.setCreatorDate(date);//创建时间
				admin.setCreatorUserID(adminUserInfo.getUserID());//创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());//创建人姓名
				//admin.setOperateDate(date);//操作时间
				//admin.setSysStatus(1);

				if (isInsert) {
					admin.setOperateContent("添加了微信绑定SN关系, 记录ID为: " + info.getBindID()); //操作内容
					admin.setOperateMenu("微信绑定SN关系管理>添加微信绑定SN关系"); //操作菜单
					admin.setOperateType("添加");//操作类型
				} else {
					admin.setOperateContent("修改了微信绑定SN关系, 记录ID为: " + info.getBindID());
					admin.setOperateMenu("微信绑定SN关系管理>修改微信绑定SN关系");
					admin.setOperateType("修改");
				}

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		} else {
			logger.info("微信绑定SN关系保存失败");
			try {
				jsonResult.put("code", 1);
				jsonResult.put("msg", "保存微信绑定SN关系出错, 请重试!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	/**
	 * 删除记录
	 * @param model
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	public void delete(@PathVariable String id, HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");

		if(StringUtils.isBlank(id)) {
			try {
				response.getWriter().println("请求参数无效!");
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		}

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");

		if (adminUserInfo == null) {
			try {
				response.getWriter().println("请重新登录!");
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}

			return;
		}

		WxBindDevice info = new WxBindDevice();
		info.setBindID(id);
		info.setSysStatus(0);

		if(wxBindDeviceSer.updateInfoSysStatus(info)){
			try {
				response.getWriter().println("微信绑定SN关系删除成功!");
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}

			try {
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());//id
				// admin.setCreatorDate(date);//创建时间
				admin.setCreatorUserID(adminUserInfo.getUserID());//创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());//创建人姓名
				//admin.setOperateDate(date);//操作时间
				//admin.setSysStatus(1);

				admin.setOperateContent("删除了微信绑定SN关系, 记录ID为: " + info.getBindID());
				admin.setOperateMenu("微信绑定SN关系管理>删除微信绑定SN关系");
				admin.setOperateType("删除");

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		}else{
			try {
				response.getWriter().println("微信绑定SN关系删除出错!");
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	/**
	 * 恢复记录
	 * @param model
	 * @return
	 */
	@RequestMapping("/restore/{id}")
	public void restorePlan(@PathVariable String id, HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");

		if(StringUtils.isBlank(id)) {
			try {
				response.getWriter().println("请求参数无效!");
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		}

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");

		if (adminUserInfo == null) {
			try {
				response.getWriter().println("请重新登录!");
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}

			return;
		}

		WxBindDevice info = new WxBindDevice();
		info.setBindID(id);
		info.setSysStatus(1);

		if(wxBindDeviceSer.updateInfoSysStatus(info)){
			try {
				response.getWriter().println("微信绑定SN关系恢复成功!");
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}

			try {
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());//id
				// admin.setCreatorDate(date);//创建时间
				admin.setCreatorUserID(adminUserInfo.getUserID());//创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());//创建人姓名
				//admin.setOperateDate(date);//操作时间
				//admin.setSysStatus(1);

				admin.setOperateContent("恢复了微信绑定SN关系, 记录ID为: " + info.getBindID());
				admin.setOperateMenu("微信绑定SN关系管理>恢复微信绑定SN关系");
				admin.setOperateType("恢复");

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		}else{
			try {
				response.getWriter().println("微信绑定SN关系恢复出错!");
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	/**
	 * 解绑消息
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/unbind/{id}")
	public void unbind(@PathVariable String id, HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");

		if(StringUtils.isBlank(id)) {
			try {
				response.getWriter().print("-1"); //response.getWriter().println("请提供绑定id!");
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
			return;
		}

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");

		if (adminUserInfo == null) {
			try {
				response.getWriter().print("-5"); //response.getWriter().println("请重新登录!");
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
			return;
		}

		WxBindDevice info = new WxBindDevice();
		info.setBindID(id);
		info.setBindStatus("解绑");

		if(wxBindDeviceSer.updateBindStatus(info)){
			try {
				response.getWriter().print("0"); //response.getWriter().println("微信绑定SN关系解绑成功!");
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}

			try {
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());//id
				// admin.setCreatorDate(date);//创建时间
				admin.setCreatorUserID(adminUserInfo.getUserID());//创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());//创建人姓名
				//admin.setOperateDate(date);//操作时间
				//admin.setSysStatus(1);

				admin.setOperateContent("解绑了微信绑定SN关系, 记录ID为: " + info.getBindID());
				admin.setOperateMenu("微信绑定SN关系管理>解绑微信绑定SN关系");
				admin.setOperateType("更新");

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		}else{
			try {
				response.getWriter().print("-2"); //response.getWriter().println("微信绑定SN关系解绑出错!");
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	/**
	 * 按绑定关系参数发送模板消息 详细可参考相关文档或运营后台"客服中心""微信设备绑定关系管理"页
	 *
	 * 参数:
	 * touser 必需 发送给的open_id
	 * template_id 必需 模板消息ID
	 * params 必需 模板填充参数, 需要按特定格式, 如 params=keyname1=valau1, params=keyname2=value2 类似的多个同名参数
	 * (若的确有无需填充参数的情况, 可改为可选)
	 * url 可选
	 *
	 * REST接口参数请查看官网 WxRes.java
	 *
	 * @param toUserInfo
	 * @param response
	 * @param model
	 * @return 输出与REST接口格式相同的json string, 其中正常返回error为"00", 其他出错或情况为负数; 若系执行rest接口
	 * 之后的结果, 直接返回结果
	 */
	@RequestMapping("/sendTemplateMsg")
	public void sendTemplateMsg(WxBindDevice toUserInfo, HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonResult = new JSONObject();

		if (StringUtils.isBlank(toUserInfo.getTouser())
				|| StringUtils.isBlank(toUserInfo.getTemplate_id())
				|| null == toUserInfo.getParams() || toUserInfo.getParams().size() == 0 ) {
			try {
				jsonResult.put("error", "-1");
				jsonResult.put("msg", "请提供模板消息的必要参数");
				response.getWriter().print(jsonResult.toString()); // 请提供必要参数 -1
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
			return;
		}

		Form form = new Form();
		form.add("touser", toUserInfo.getTouser());
		form.add("template_id", toUserInfo.getTemplate_id());
		// 测试 NaAvMeYjOC6_JrHIkW2Czq-dL6O8KzOWZO1ga5ro44k
		// {{first.DATA}} 系统名称：{{system.DATA}} 截止时间：{{time.DATA}} 报警次数：{{account.DATA}} {{remark.DATA}}

		form.add("url", toUserInfo.getUrl()); // http://www.easy2go.cn
//		form.add("params", "first=请留意低流量预警");  // TODO: 按照实际填充参数值 详情参见后台"客服中心" "微信设备绑定关系管理"
//		form.add("params", "system=Wifi流量");
//		form.add("params", "time=2015-10-30 15:30:20");
//		form.add("params", "account=9");
//		form.add("params", "remark=请及时通过微信平台，途狗官网和手机应用充值！(测试消息)");
		for (String parameter : toUserInfo.getParams()) {
			form.add("params", parameter);
		}

		String resStr;
		try {
			resStr = postRequestForm(getRestUrlRoot()+RestConstants.URL_WX_TEMPLATE_SEND_MSG, form);
			JSONObject result = getResultJsonObject(resStr);
    		if("00".equals(result.getString("error"))){
    			logger.info("微信模板消息发送成功！");

    			try {
    				response.getWriter().print(resStr); // 发送结束 0 "发送成功"
    			} catch (IOException e) {
    				logger.info(e.getMessage());
    				e.printStackTrace();
    			}
    		}else{
    			logger.info("微信模板消息发送失败! error=" + result.getString("error") + " msg=" + result.getString("msg"));

    			try {
					response.getWriter().print(resStr); // 发送模板消息失败 -4 // result.getString("msg")
				} catch (IOException e) {
					logger.info(e.getMessage());
					e.printStackTrace();
				}
    			return;
    		}
		} catch (Exception e) {
			logger.info("微信模板消息发送失败！");
			logger.info(e.getMessage());
			e.printStackTrace();

			try {
				jsonResult.put("error", "-2");
				jsonResult.put("msg", "发送模板消息出错, 请重试");
				response.getWriter().print(jsonResult.toString()); // 发送模板消息失败 -4 // "发送模板消息失败"
			} catch (IOException e1) {
				logger.info(e1.getMessage());
				e.printStackTrace();
			}
			return;
		}

	}

	/**
	 * 按绑定关系参数发送普通消息 详细可参考相关文档或运营后台"客服中心""微信设备绑定关系管理"页
	 *
	 * 参数:
	 * touser 必需 发送给的open_id
	 * [已去掉, 不再需要] msgtype 可选 固定为 "text"
	 * content 必需 发送消息的内容
	 *
	 * @param toUserInfo
	 * @param response
	 * @param model
	 */
	@RequestMapping("/sendTextMsg")
	public void sendTextMsg(WxBindDevice toUserInfo, HttpServletResponse response, Model model) {
		logger.info("微信发送普通消息开始");
		JSONObject jsonResult = new JSONObject();

		if (StringUtils.isBlank(toUserInfo.getTouser())
				|| StringUtils.isBlank(toUserInfo.getContent())) {
			try {
				jsonResult.put("error", "-1");
				jsonResult.put("msg", "请提供发送普通消息必要参数");
				response.getWriter().print(jsonResult.toString()); // 请提供必要参数 -1
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
			return;
		}

		Form form = new Form();
		form.add("touser", toUserInfo.getTouser());
		form.add("msgtype", "text");
		form.add("content", toUserInfo.getContent());
		try {
			String resStr = postRequestForm(getRestUrlRoot()+RestConstants.URL_WX_SEND_MSG, form);
			JSONObject result = getResultJsonObject(resStr);
    		if("00".equals(result.getString("error"))){
    			logger.info("微信发送普通消息成功！");

    			try {
    				response.getWriter().print(resStr); // 发送结束 0 "发送成功"
    			} catch (IOException e) {
    				logger.info(e.getMessage());
    				e.printStackTrace();
    			}
    		}else{
    			logger.info("微信发送普通消息失败! error=" + result.getString("error") + " msg=" + result.getString("msg"));

    			try {
					response.getWriter().print(resStr); // 发送普通消息失败 -2 result.getString("msg")
				} catch (IOException e) {
					logger.info(e.getMessage());
					e.printStackTrace();
				}
    			return;
    		}
		} catch (Exception e) {
			logger.info("微信发送普通消息失败！");
			logger.info(e.getMessage());
			e.printStackTrace();

			try {
				jsonResult.put("error", "-2");
				jsonResult.put("msg", "发送普通消息出错, 请重试");
				response.getWriter().print(jsonResult.toString()); // 发送普通消息失败 -2
			} catch (IOException e1) {
				logger.info(e1.getMessage());
				e.printStackTrace();
			}
			return;
		}

	}

	/**
	 * 这个测试接口请直接调用
	 *
	 * 参数: 普通消息:
	 *  openid 必需
	 *  bindStatus 必需 固定为 "bind" 或者考虑根据 openid 在绑定关系表里查询, 为"绑定"的才可发送
	 *  pushType 必需 消息类型 为 text 是普通消息
	 *	content 必需 消息内容 为普通文本, 可带链接 链接可参考 /easy2go/src/com/easy2go/control/WxControl.java #receive() 中的
	 *				 这段 if (WxConfig.CUSTOM_MENU_KEY_BIND.equals(eventMessage.getEventKey())) { // 绑定
	 *
	 * 模板消息:
	 *
	 *
	 * REST接口参数请查看官网 WxRes.java
	 *
	 * @param toUserInfo
	 * @param response
	 * @param model
	 */
	@RequestMapping("/testSendMsgOld")
	public void testSendMsg(WxBindDevice toUserInfo, HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");

		// 测试时暂使用 "bind" 代表绑定, "text" 代表文本消息 "template" 代表模板消息

		// 使用 lastTemplateMsgId 来传递模板 id
		if (StringUtils.isBlank(toUserInfo.getOpenid())
				|| !"bind".equals(toUserInfo.getBindStatus())) {
			try {
				response.getWriter().print("请提供必要参数"); // 请提供必要参数 -1
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
			return;
		}

		// 尝试使用 pushType 来区分消息类型 如 文本或普通 对应 普通消息, 模板 来对应 模板消息
		if ("text".equals(toUserInfo.getPushType())) {
			Form form = new Form();
    		form.add("touser", toUserInfo.getOpenid());
    		form.add("msgtype", "text");
    		form.add("content", toUserInfo.getContent());
    		try {
				String resStr = postRequestForm(getRestUrlRoot()+RestConstants.URL_WX_SEND_MSG, form);
				JSONObject result = getResultJsonObject(resStr);
        		if("00".equals(result.getString("error"))){
        			logger.info("微信发消息成功！");
        		}else{
        			logger.info("微信发消息失败! error=" + result.getString("error") + " msg=" + result.getString("msg"));

        			try {
    					response.getWriter().print(result.getString("msg")); // 发送普通消息失败 -2
    				} catch (IOException e) {
    					logger.info(e.getMessage());
    					e.printStackTrace();
    				}
        			return;
        		}
			} catch (Exception e) {
				logger.info("微信发消息失败！");
				logger.info(e.getMessage());
				e.printStackTrace();

				try {
					response.getWriter().print("发送普通消息失败"); // 发送普通消息失败 -2
				} catch (IOException e1) {
					logger.info(e1.getMessage());
					e.printStackTrace();
				}
				return;
			}

		} else if ("template".equals(toUserInfo.getPushType())) {

			if (StringUtils.isBlank(toUserInfo.getTemplate_id())) {
				try {
					response.getWriter().print("请提供必要参数模板id"); // 请提供必要参数模板id -3
				} catch (IOException e) {
					logger.info(e.getMessage());
					e.printStackTrace();
				}
				return;
			}

			Form form = new Form();
    		form.add("touser", toUserInfo.getOpenid());
    		form.add("template_id", toUserInfo.getTemplate_id());
    		// 测试 NaAvMeYjOC6_JrHIkW2Czq-dL6O8KzOWZO1ga5ro44k
    		// {{first.DATA}} 系统名称：{{system.DATA}} 截止时间：{{time.DATA}} 报警次数：{{account.DATA}} {{remark.DATA}}

    		form.add("url", toUserInfo.getUrl()); // http://www.easy2go.cn
    		form.add("params", "first=请留意低流量预警");  // TODO: 按照实际填充参数值 详情参见后台"客服中心" "微信设备绑定关系管理"
    		form.add("params", "system=Wifi流量");
    		form.add("params", "time=2015-10-30 15:30:20");
    		form.add("params", "account=9");
    		form.add("params", "remark=请及时通过微信平台，途狗官网和手机应用充值！(测试消息)");

    		try {
				String resStr = postRequestForm(getRestUrlRoot()+RestConstants.URL_WX_TEMPLATE_SEND_MSG, form);
				JSONObject result = getResultJsonObject(resStr);
        		if("00".equals(result.getString("error"))){
        			logger.info("微信模板消息发送成功！");
        		}else{
        			logger.info("微信模板消息发送失败! error=" + result.getString("error") + " msg=" + result.getString("msg"));

        			try {
    					response.getWriter().print(result.getString("msg")); // 发送模板消息失败 -4
    				} catch (IOException e) {
    					logger.info(e.getMessage());
    					e.printStackTrace();
    				}
        			return;
        		}
			} catch (Exception e) {
				logger.info("微信模板消息发送失败！");
				logger.info(e.getMessage());
				e.printStackTrace();

				try {
					response.getWriter().print("发送模板消息失败"); // 发送模板消息失败 -4
				} catch (IOException e1) {
					logger.info(e1.getMessage());
					e.printStackTrace();
				}
				return;
			}
		} else {

			try {
				response.getWriter().print("未知的消息类型"); // 未知的消息类型 -5
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
			return;
		}

		// 指示测试结束
		try {
			response.getWriter().print("发送成功"); // 发送结束 0
		} catch (IOException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}

	}
}
