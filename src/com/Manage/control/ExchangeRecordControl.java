package com.Manage.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.Manage.common.constants.Constants;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.Dictionary;
import com.Manage.entity.ExchangeRecord;
import com.Manage.entity.common.SearchDTO;

@Controller
@RequestMapping("/exchange/exchangeRecord")
public class ExchangeRecordControl extends BaseController {
	private Logger logger = LogUtil.getInstance(ExchangeRecordControl.class);

	/**
	 * 分页查询兑换码兑换记录列表
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
	public void datapage(SearchDTO searchDTO, ExchangeRecord info,
			HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);
		String jsonString = exchangeRecordSer.getPageString(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 兑换码兑换记录详情 by ID
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/view/{id}")
	public String view(@PathVariable String id, Model model) {
		ExchangeRecord info = exchangeRecordSer.getById(id);
		if (info != null && info.getKeyId() != null) {

			model.addAttribute("Model", info);
		} else {
			model.addAttribute("info","此兑换码兑换记录不存在或已无效!");
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
		ExchangeRecord info = exchangeRecordSer.getById(id);
		if (info != null && info.getKeyId() != null) {
	        List<Dictionary> serverStatus = dictionarySer.getAllList(Constants.DICT_SIMSERVER_STATUS);
	        model.addAttribute("ServerStatusDict", serverStatus);
			model.addAttribute("Model", info);
		} else {
			model.addAttribute("info","此兑换码兑换记录不存在或已无效!");
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
	public void saveAction(ExchangeRecord info, HttpServletRequest request,
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
		if (StringUtils.isBlank(info.getKeyId())) {
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
			info.setRecordId(getUUID());
			info.setSysStatus(1);

			result = exchangeRecordSer.insertInfo(info);
		} else {
			result = exchangeRecordSer.updateInfo(info);
		}
		// } catch (Exception e) {
		// result = false;
		// e.printStackTrace();
		// }

		if (result) {
			logger.info("兑换码兑换记录保存成功");
			try {
				jsonResult.put("code", 0);
				jsonResult.put("msg", "成功保存兑换码兑换记录!");
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
					admin.setOperateContent("添加了兑换码兑换记录, 记录ID为: " + info.getKeyId()); //操作内容
					admin.setOperateMenu("兑换码兑换记录管理>添加兑换码兑换记录"); //操作菜单
					admin.setOperateType("添加");//操作类型
				} else {
					admin.setOperateContent("修改了兑换码兑换记录, 记录ID为: " + info.getKeyId());
					admin.setOperateMenu("兑换码兑换记录管理>修改兑换码兑换记录");
					admin.setOperateType("修改");
				}

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		} else {
			logger.info("兑换码兑换记录保存失败");
			try {
				jsonResult.put("code", 1);
				jsonResult.put("msg", "保存兑换码兑换记录出错, 请重试!");
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

		ExchangeRecord info = new ExchangeRecord();
		info.setRecordId(id);
		info.setSysStatus(0);

		if(exchangeRecordSer.updateInfoSysStatus(info)){
			try {
				response.getWriter().println("兑换码兑换记录删除成功!");
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

				admin.setOperateContent("删除了兑换码兑换记录, 记录ID为: " + info.getKeyId());
				admin.setOperateMenu("兑换码兑换记录管理>删除兑换码兑换记录");
				admin.setOperateType("删除");

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		}else{
			try {
				response.getWriter().println("兑换码兑换记录删除出错!");
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

		ExchangeRecord info = new ExchangeRecord();
		info.setRecordId(id);
		info.setSysStatus(1);

		if(exchangeRecordSer.updateInfoSysStatus(info)){
			try {
				response.getWriter().println("兑换码兑换记录恢复成功!");
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

				admin.setOperateContent("恢复了兑换码兑换记录, 记录ID为: " + info.getKeyId());
				admin.setOperateMenu("兑换码兑换记录管理>恢复兑换码兑换记录");
				admin.setOperateType("恢复");

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		}else{
			try {
				response.getWriter().println("兑换码兑换记录恢复出错!");
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		}

	}

}
