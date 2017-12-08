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

import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;

import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.OperatorInfo;
import com.Manage.entity.common.SearchDTO;

@Controller
@RequestMapping("/sim/operatorinfo")
public class OperatorInfoControl extends BaseController {
	private Logger logger = LogUtil.getInstance(OperatorInfoControl.class);

	/**
	 * 分页查询全部运营商列表 入口
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");

		List<CountryInfo> countries = countryInfoSer.getAll("");
		model.addAttribute("Countries", countries);

		model.addAttribute("IsIndexView", true);
		return "WEB-INF/views/sim/operatorinfo_index";

	}

	/**
	 * 分页查询全部运营商 ajax
	 * @param searchDTO
	 * @param info
	 * @param response
	 */
	@RequestMapping("/datapage")
	public void datapage(SearchDTO searchDTO, OperatorInfo info,
			HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);
		String jsonString = operatorInfoSer.getPageString(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	/**
//	 * 全部已删除记录入口
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping("/trash")
//	public String trash(HttpServletResponse response, Model model) {
//		response.setContentType("text/html;charset=utf-8");
//		model.addAttribute("IsTrashView", true);
//		return "WEB-INF/views/sim/operatorinfo_index";
//
//	}
//
//	/**
//	 * 分页查询 已删除 ajax
//	 * @param searchDTO
//	 * @param info
//	 * @param response
//	 */
//	@RequestMapping("/trashdatapage")
//	public void dataPageDeleted(SearchDTO searchDTO, OperatorInfo info,
//			HttpServletResponse response) {
//		response.setContentType("application/json;charset=UTF-8");
//		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
//				searchDTO.getPageSize(), searchDTO.getSortName(),
//				searchDTO.getSortOrder(), info);
//		String jsonString = operatorInfoSer.getPageDeletedString(seDto);
//		try {
//			response.getWriter().println(jsonString);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	/**
	 * 运营商详情 by ID
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/view/{id}")
	public String view(@PathVariable String id, Model model) {
		OperatorInfo info = operatorInfoSer.getById(id);
		if (info != null && info.getOperatorID() != null) {
			model.addAttribute("Model", info);
		} else {
			model.addAttribute("info","此运营商不存在或已无效!");
		}
		return "WEB-INF/views/sim/operatorinfo_view";
	}

	/**
	 * 更新入口
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		OperatorInfo info = operatorInfoSer.getById(id);
		if (info != null && info.getOperatorID() != null) {
			List<CountryInfo> countries = countryInfoSer.getAll("");
			model.addAttribute("Countries", countries);

			model.addAttribute("Model", info);
		} else {
			model.addAttribute("info","此运营商不存在或已无效!");
		}
		return "WEB-INF/views/sim/operatorinfo_edit";
	}

//	/**
//	 * 回收站中更新记录
//	 *
//	 * 鉴于正常记录和回收站中的记录编辑有所不同， 所以区分两个接口
//	 *
//	 * @param id
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping("/trash/edit/{id}")
//	public String editTrash(@PathVariable String id, Model model) {
//		OperatorInfo info = operatorInfoSer.getById(id);
//		if (info != null && info.getOperatorID() != null) {
//			model.addAttribute("Model", info);
//			model.addAttribute("IsTrashView", true);
//		} else {
//			model.addAttribute("info","此记录不存在或已无效!");
//		}
//		return "WEB-INF/views/sim/operatorinfo_edit";
//	}

//	/**
//	 * 新增记录入口
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping("/new")
//	public String newEntity(HttpServletResponse response, Model model) {
//		response.setContentType("text/html;charset=utf-8");
//
//		return "WEB-INF/views/sim/operatorinfo_new";
//	}

	/**
	 * 保存记录 新增new或编辑edit提交时统一使用此接口
	 * 通过 boolean isInsert 来相应处理
	 * @param info
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void saveAction(OperatorInfo info, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// TODO: 服务器端数据验证

		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonResult = new JSONObject();

		boolean isInsert = false;
		if (StringUtils.isBlank(info.getOperatorID())) {
			isInsert = true;
		}

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");

		if (adminUserInfo != null) {
			if (isInsert) {
				info.setCreatorUserID(adminUserInfo.getUserID());
				// info.setCreatorUserName(adminUserInfo.getUserName());
			} else {
				info.setModifyUserID(adminUserInfo.getUserID());
			}
		} else {
			try {
				// response.getWriter().println("请重新登录!");
				jsonResult.put("code", 2);
				jsonResult.put("msg", "请重新登录!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			return;
		}

		boolean result;

		// try {
		if (isInsert) {
			info.setOperatorID(getUUID());
			info.setSysStatus(1);

			result = operatorInfoSer.insertInfo(info);
		} else {
			result = operatorInfoSer.updateInfo(info);
		}
		// } catch (Exception e) {
		// result = false;
		// e.printStackTrace();
		// }

		if (result) {
			logger.debug("运营商保存成功");
			try {
				// response.getWriter().println("成功保存运营商!");
				jsonResult.put("code", 0);
				jsonResult.put("msg", "成功保存运营商!");

				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
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
					admin.setOperateContent("添加了运营商, 记录ID为: " + info.getOperatorID() + " 名称: " + info.getOperatorName()); //操作内容
					admin.setOperateMenu("运营商管理>添加运营商"); //操作菜单
					admin.setOperateType("添加");//操作类型
				} else {
					admin.setOperateContent("修改了运营商, 记录ID为: " + info.getOperatorID() + " 名称: " + info.getOperatorName());
					admin.setOperateMenu("运营商管理>修改运营商");
					admin.setOperateType("修改");
				}

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else {
			logger.debug("运营商保存失败");
			try {
				// response.getWriter().println("保存运营商出错, 请重试!");
				jsonResult.put("code", 1);
				jsonResult.put("msg", "保存运营商出错, 请重试!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
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
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");

		if (adminUserInfo == null) {
			try {
				response.getWriter().println("请重新登录!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			return;
		}

		OperatorInfo info = new OperatorInfo();
		info.setOperatorID(id);
		info.setSysStatus(0);

		if(operatorInfoSer.updateInfoSysStatus(info)){
			try {
				response.getWriter().println("运营商删除成功!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
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

				admin.setOperateContent("删除了运营商, 记录ID为: " + info.getOperatorID() + " 名称: " + info.getOperatorName());
				admin.setOperateMenu("运营商管理>删除运营商");
				admin.setOperateType("删除");

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else{
			try {
				response.getWriter().println("运营商删除出错!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
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
	public void restore(@PathVariable String id, HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");

		if(StringUtils.isBlank(id)) {
			try {
				response.getWriter().println("请求参数无效!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");

		if (adminUserInfo == null) {
			try {
				response.getWriter().println("请重新登录!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}

			return;
		}

		OperatorInfo info = new OperatorInfo();
		info.setOperatorID(id);
		info.setSysStatus(1);

		if(operatorInfoSer.updateInfoSysStatus(info)){
			try {
				response.getWriter().println("运营商恢复成功!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
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

				admin.setOperateContent("恢复了运营商, 记录ID为: " + info.getOperatorID() + " 名称: " + info.getOperatorName());
				admin.setOperateMenu("运营商管理>恢复运营商");
				admin.setOperateType("恢复");

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else{
			try {
				response.getWriter().println("运营商恢复出错!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	/**
	 * 查询全部记录列表 返回 bootstrap-treeview JSON串
	 * @param response
	 */
	@RequestMapping("/getTreeviewString")
	public void getTreeViewString(HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");

		String jsonString = operatorInfoSer.getAllTreeviewJson("");
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
