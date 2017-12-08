package com.Manage.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.CRC32;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.log4j.chainsaw.Main;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.Manage.common.constants.Constants;
import com.Manage.common.util.DownLoadUtil;
import com.Manage.common.util.Easy2goUtil;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.SMSUltis;
import com.Manage.common.util.StringUtils;
import com.Manage.common.util.ByteUtils.Bytes;
import com.Manage.common.util.ByteUtils.StringValueConversionException;
import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.CustomerInfo;
import com.Manage.entity.DeviceLogs;
import com.Manage.entity.Dictionary;
import com.Manage.entity.ExchangeKey;
import com.Manage.entity.common.Page;
import com.Manage.entity.common.SearchDTO;

@Controller
@RequestMapping("/exchange/exchangeKey")
public class ExchangeKeyControl extends BaseController {
	private Logger logger = LogUtil.getInstance(ExchangeKeyControl.class);

	/**
	 * 分页查询兑换码列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");
		return "WEB-INF/views/exchange/exchange_key";

	}

	/**
	 * 分页查询
	 * @param searchDTO
	 * @param info
	 * @param response
	 */
	@RequestMapping("/datapage")
	public void datapage(SearchDTO searchDTO, ExchangeKey info,
			HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);
		String jsonString = exchangeKeySer.getPageString(seDto);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 兑换码详情 by ID
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/view/{id}")
	public String view(@PathVariable String id, Model model) {
		ExchangeKey info = exchangeKeySer.getById(id);
		if (info != null && info.getKeyId() != null) {

			model.addAttribute("Model", info);
		} else {
			model.addAttribute("info","此兑换码不存在或已无效!");
		}
		return "WEB-INF/views/exchange/exchange_key"; // TODO
	}

	/**
	 * 更新入口
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		ExchangeKey info = exchangeKeySer.getById(id);
		if (info != null && info.getKeyId() != null) {
			info.setStartDate(StringUtils.substring(info.getStartDate(), 0, 10));
			info.setEndDate(StringUtils.substring(info.getEndDate(), 0, 10));

//	        List<Dictionary> serverStatus = dictionarySer.getAllList(Constants.DICT_SIMSERVER_STATUS);
//	        model.addAttribute("ServerStatusDict", serverStatus);
			model.addAttribute("Model", info);
			model.addAttribute("OP", "edit");
		} else {
			model.addAttribute("info","此兑换码不存在或已无效!");
		}
		return "WEB-INF/views/exchange/exchange_key";
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
        model.addAttribute("OP", "edit");
		return "WEB-INF/views/exchange/exchange_key";
	}

	/**
	 * 保存记录 新增new或编辑edit提交时统一使用此接口
	 * 通过 boolean isInsert 来相应处理
	 * @param info
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void saveAction(ExchangeKey info, HttpServletRequest request,
			HttpServletResponse response, Model model) {

		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonResult = new JSONObject();

		if (info.getIfBatch() != 0) {
			try {
				jsonResult.put("code", -2);
				jsonResult.put("msg", "只能在新建发货时批量输入手机");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}

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
				jsonResult.put("code", -1);
				jsonResult.put("msg", "请重新登录!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}

			return;
		}

		// 检查客户是否存在
		if (customerInfoSer.searchCKphone(info.getPhone()) == 0) {
			try {
				jsonResult.put("code", -4);
				jsonResult.put("msg", "查无此客户手机! " + info.getPhone());
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
			return;
		}

		boolean result;
		boolean needSendSms = false;

		// try {
		if (isInsert) {
			//info.setKeyId(getUUID()); // 自增
			info.setSysStatus(1);

			if (StringUtils.isBlank(info.getKey())) { // 单个时若key空则自动生成
				Map<String, String> keyResult = Easy2goUtil.generateExchangeKey(info.getPhone());
				Boolean getKeyOk = false;
				if (null != keyResult && keyResult.size() == 1) {
						String keyTimestamp = "", key = "";
						for (Iterator<String> iterator = keyResult.keySet().iterator(); iterator.hasNext();) {
							keyTimestamp = (String) iterator.next();
							key = keyResult.get(keyTimestamp);
							break;
						}
						if (StringUtils.isNotBlank(key)) {
							info.setKey(key);
							info.setKeyTimestamp(keyTimestamp);
							getKeyOk = true;
							logger.info("getKey: OK");
						}
				}
				if (!getKeyOk) {
					try {
						jsonResult.put("code", -2);
						jsonResult.put("msg", "自动生成兑换码时出错，请重试!");
						response.getWriter().println(jsonResult.toString());
					} catch (IOException e) {
						logger.info(e.getMessage());
						e.printStackTrace();
					}
					return;
				}
			}

			// 新增时，初次必须发短信，暂录入，若后面发短信出错时，再update回次数. 注意只有“正常”兑换码时才考虑发短信
			if ("正常".equals(info.getStatus())) {
				info.setSendSmsCount(1);
				needSendSms = true;
			}

			result = exchangeKeySer.insertInfo(info);
		} else {
			// 修改时，先判断是否为零，若是则上次发短信失败，也暂录入，若后面发短信出错时，再update回次数
			if (0 == info.getSendSmsCount() && "正常".equals(info.getStatus())) {
				info.setSendSmsCount(1);
				needSendSms = true;
			}

			result = exchangeKeySer.updateInfo(info);
		}
		// } catch (Exception e) {
		// result = false;
		// e.printStackTrace();
		// }

		if (result) {
			logger.info("兑换码保存成功");

			// 给客户发短信，同时注意，在列表页面“允许兑换”操作完成时是否亦需要自动发短信？还是在操作中
			// 提供多一个手动发短信的按钮？对于发短信是否需要增加字段或一些策略？
			// -->新增了字段，新插入时初次必须发短信，同时若初次发短信失败，次数被重置回零，编辑保存时判断
			// 发次数为零也尝试再发。而由“禁用”改为“正常”的状态，建议新增一个单独发短信的按钮
			Boolean sendSmsOK = true;
			if (needSendSms) { // 前面已判断 && "正常".equals(info.getStatus())
				if (!SMSUltis.sendTemplateSmsExchangeCodeSend(info.getPhone(), info.getKey())) {
					sendSmsOK = false;

					// 未发送成功，要把之前加的次数减1
					ExchangeKey updateKey = new ExchangeKey();
					updateKey.setKeyId(info.getKeyId());
					updateKey.setSendSmsCount(-1); // 减1
					if (!exchangeKeySer.updateSendSmsCount(updateKey)) {
						// 提醒前端管理员注意处理，未发送成功且更新短信发送次数失败，务必手动发送一次
						try {
							jsonResult.put("code", 10);
							jsonResult.put("msg", "成功保存兑换码, 但发送短信失败，请务必补发短信一次! " + info.getPhone() + " 兑换码：" + info.getKey());
							response.getWriter().println(jsonResult.toString());
						} catch (IOException e) {
							logger.info(e.getMessage());
							e.printStackTrace();
						}
					} else {
						// 这种情况提醒管理员再编辑一次或手动发送短信
						try {
							jsonResult.put("code", 11); // 暂区分一下，但仅待用
							jsonResult.put("msg", "成功保存兑换码, 但发送短信失败，请务必补发短信一次!" + info.getPhone() + " 兑换码：" + info.getKey());
							response.getWriter().println(jsonResult.toString());
						} catch (IOException e) {
							logger.info(e.getMessage());
							e.printStackTrace();
						}
					}
				}
			}

			if (sendSmsOK) { // 如果不成功，在上面已输出 response.getWriter()
				try {
					jsonResult.put("code", 0);
					jsonResult.put("msg", "成功保存兑换码!");
					response.getWriter().println(jsonResult.toString());
				} catch (IOException e) {
					logger.info(e.getMessage());
					e.printStackTrace();
				}
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
					admin.setOperateContent("添加了兑换码, 记录ID为: " + info.getKeyId()
							+ ((sendSmsOK) ? "" : "但短信发送未成功，请补发")); //操作内容
					admin.setOperateMenu("兑换码管理>添加兑换码"); //操作菜单
					admin.setOperateType("添加");//操作类型
				} else {
					admin.setOperateContent("修改了兑换码, 记录ID为: " + info.getKeyId()
							+ ((sendSmsOK) ? "" : "但短信发送未成功，请补发"));
					admin.setOperateMenu("兑换码管理>修改兑换码");
					admin.setOperateType("修改");
				}

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		} else {
			logger.info("兑换码保存失败");
			try {
				jsonResult.put("code", 1);
				jsonResult.put("msg", "保存兑换码出错, 请重试!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	/**
	 * 发短信, 若发送失败, 则要还原原来添加的次数
	 * @param phone
	 * @param key
	 * @param keyId
	 * @param ifRestoreSendSmsCount 若要还原之前添加的次数
	 * @return 0 成功 -1 缺少参数 -2 发送失败且更新次数失败 -3发送失败但还原次数成功, 仍然需要提醒去手动发短信
	 */
	private Integer sendSms(String phone, String key, Integer keyId, Boolean ifRestoreSendSmsCount) {
		if (StringUtils.isBlank(phone) || StringUtils.isBlank(key)) {
			return -1;
		}
		if (!SMSUltis.sendTemplateSmsExchangeCodeSend(phone, key)) {
			if (ifRestoreSendSmsCount) {
				// 未发送成功，要把之前加的次数减1
				ExchangeKey updateKey = new ExchangeKey();
				updateKey.setKeyId(keyId);
				updateKey.setSendSmsCount(-1); // 减1
				if (!exchangeKeySer.updateSendSmsCount(updateKey)) {
					return -2;
				} else {
					return -3;
				}
			}
		}
		return 0;
	}


	/**
	 * 保存批量手机记录 批量时只系插入
	 *
	 * @param info
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/saveBatch", method = RequestMethod.POST)
	public void saveBatchAction(ExchangeKey info, HttpServletRequest request,
 HttpServletResponse response, Model model)
			throws IOException {

		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonResult = new JSONObject();

		if (info.getIfBatch() != 1 || StringUtils.isNotBlank(info.getKeyId())) {
			jsonResult.put("code", -2);
			jsonResult.put("msg", "只能在新建发货时批量输入手机");
			response.getWriter().println(jsonResult.toString());
			return;
		}

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession().getAttribute("User");

		if (adminUserInfo != null) {
			info.setCreatorUserID(adminUserInfo.getUserID());
			info.setCreatorUserName(adminUserInfo.getUserName());
		} else {
			jsonResult.put("code", -1);
			jsonResult.put("msg", "请重新登录!");
			response.getWriter().println(jsonResult.toString());
			return;
		}

		List<String> batchPhoneList = new ArrayList<String>();
		String batchPhone[] = null;

		String phoneString = info.getPhoneBatch();
		if (StringUtils.isBlank(phoneString)) {
			jsonResult.put("code", -3);
			jsonResult.put("msg", "请提供手机号码");
			response.getWriter().println(jsonResult.toString());
			return;
		}

		batchPhone = phoneString.split(",");
		// 必要时检验手机号
		String errorString = "";
		for (int i = 0; i < batchPhone.length; i++) {
			batchPhone[i] = StringUtils.trim(batchPhone[i]);
			if (batchPhone[i] == "") {
				continue; // 有空白
			} else if (batchPhone[i].length() != 11) {
				errorString += batchPhone[i] + " ";
			}

			batchPhoneList.add(batchPhone[i]);
		}
		if (errorString.length() > 0 || batchPhoneList.size() == 0) {
			try {
				jsonResult.put("code", -3);
				jsonResult.put("msg", "手机号码有误: " + errorString);
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
			return;
		}

		// info.setKeyId(getUUID()); // 自增
		info.setSysStatus(1);

		// 新增时，初次必须发短信，暂录入，若后面发短信出错时，再update回次数. 注意只有“正常”兑换码时才考虑发短信
		boolean needSendSms = false;
		if ("正常".equals(info.getStatus())) {
			info.setSendSmsCount(1);
			needSendSms = true;
		}

		// 批量插入
		Boolean result;
		List<ExchangeKey> keys = new ArrayList<ExchangeKey>();
//		Map<Integer, ExchangeKey> keyMap= new HashMap<Integer, ExchangeKey>();
//		Integer count = 0;
		for (String phone : batchPhoneList) {
			info.setPhone(phone);

			// 检查客户是否存在
			if (customerInfoSer.searchCKphone(phone) < 1) {
				info.setIfInsertOK(false);
				info.setIfSendSmsOK(0);
				info.setIfNotExist(true);

				ExchangeKey newInfo = new ExchangeKey();
				newInfo.setPhone(phone);
				newInfo.setIfInsertOK(false);
				newInfo.setIfSendSmsOK(0);
				newInfo.setIfNotExist(true);
				continue;
			}

			Map<String, String> keyResult = Easy2goUtil.generateExchangeKey(phone);
			Boolean getKeyOk = false;
			if (null != keyResult && keyResult.size() == 1) {
					String keyTimestamp = "", key = "";
					for (Iterator<String> iterator = keyResult.keySet().iterator(); iterator.hasNext();) {
						keyTimestamp = (String) iterator.next();
						key = keyResult.get(keyTimestamp);
						break;
					}
					if (StringUtils.isNotBlank(key)) {
						info.setKey(key);
						info.setKeyTimestamp(keyTimestamp);
						getKeyOk = true;
						logger.info("getKey: OK");
					}
			}
			if (!getKeyOk) {
				info.setIfInsertOK(false);
				info.setIfSendSmsOK(0);
				info.setIfNotExist(false);

				ExchangeKey newInfo = new ExchangeKey();
				newInfo.setPhone(phone);
				newInfo.setIfInsertOK(false);
				newInfo.setIfSendSmsOK(0);
				newInfo.setIfNotExist(false);

				keys.add(newInfo); // failed info
//				keyMap.put(count, info);
//				count++;

				continue;
			}

			result = exchangeKeySer.insertInfo(info);
			info.setIfInsertOK(result);

			// 给客户发短信，同时注意，在列表页面“允许兑换”操作完成时是否亦需要自动发短信？还是在操作中
			// 提供多一个手动发短信的按钮？对于发短信是否需要增加字段或一些策略？
			// -->新增了字段，新插入时初次必须发短信，同时若初次发短信失败，次数被重置回零，编辑保存时判断
			// 发次数为零也尝试再发。而由“禁用”改为“正常”的状态，建议新增一个单独发短信的按钮
			Integer sendSmsOK = 0;
			if (needSendSms) { // 前面已判断 && "正常".equals(info.getStatus())
				if (!SMSUltis.sendTemplateSmsExchangeCodeSend(info.getPhone(), info.getKey())) {
					// 未发送成功，要把之前加的次数减1
					ExchangeKey updateKey = new ExchangeKey();
					updateKey.setKeyId(info.getKeyId());
					updateKey.setSendSmsCount(-1); // 减1
					if (!exchangeKeySer.updateSendSmsCount(updateKey)) {
						sendSmsOK = -1;
					} else {
						sendSmsOK = -2;
					}
				}
			}
			info.setIfSendSmsOK(sendSmsOK);

			// 注意,现在 ExchangeKey 已添加了 equals hashCode 的覆盖, 在这里为了避免作为相同的元素
			// 添加了, 所以把 keyId 在这里随机生成, 但后面不应该引用这个 keyId
//			String uuidPart = StringUtils.right(UUID.randomUUID().toString().replaceAll("\\D+",""), 8);
//			info.setKeyId(Integer.parseInt(uuidPart));

			ExchangeKey newInfo = new ExchangeKey();
			newInfo.setPhone(info.getPhone());
			newInfo.setIfInsertOK(info.getIfInsertOK());
			newInfo.setIfSendSmsOK(info.getIfSendSmsOK());

			keys.add(newInfo); // failed info

//			info.setKeyId(null); // !restore
//			keyMap.put(count, info);
//			count++;
		}

		// 检索各种处理结果
		String okInsertedPhones = "";
		String errorFailInsertedPhones = "";
		String errorFailSentSmsPhones = "";
		String errorNotExistsPhones = "";
		Integer insertOKCount = 0;
		for (ExchangeKey key : keys) {
			if (key.getIfInsertOK()) {
				if (key.getIfSendSmsOK() == 0) {
					insertOKCount++;
					okInsertedPhones += key.getPhone() + " ";
				} else if (key.getIfSendSmsOK() == -1) {
					errorFailSentSmsPhones += key.getPhone() + " ";
				} else if (key.getIfSendSmsOK() == -2) {
					errorFailSentSmsPhones += key.getPhone() + " ";
				}
			} else {
				if (key.getIfNotExist()) {
					errorNotExistsPhones += key.getPhone() + " ";
				} else {
					errorFailInsertedPhones += key.getPhone() + " ";
				}
			}
		}

//		ExchangeKey key;
//		for (Iterator<Integer> iterator = keyMap.keySet().iterator(); iterator.hasNext();) {
//			count = (Integer) iterator.next();
//			key = keyMap.get(count);
//			if (key.getIfInsertOK()) {
//				if (key.getIfSendSmsOK() == 0) {
//					insertOKCount++;
//					okInsertedPhones += key.getPhone() + " ";
//				} else if (key.getIfSendSmsOK() == -1) {
//					errorFailSentSmsPhones += key.getPhone() + " ";
//				} else if (key.getIfSendSmsOK() == -2) {
//					errorFailSentSmsPhones += key.getPhone() + " ";
//				}
//			} else {
//				errorFailInsertedPhones += key.getPhone() + " ";
//			}
//		}
		String resultString;
		if (insertOKCount != keys.size()) { //  keys.size() keyMap.size()
			// 统一错误信息到 errorFailInsertedPhones 中
			if (errorFailInsertedPhones.length() > 0) {
				errorFailInsertedPhones = "发放失败手机: " + errorFailInsertedPhones;
			}
			if (errorFailSentSmsPhones.length() > 0) {
				errorFailInsertedPhones = errorFailInsertedPhones +
						" 以下手机生成了兑换码但发送短信失败, 请务必手动补发短信: " + errorFailSentSmsPhones;
			}
			if (errorNotExistsPhones.length() > 0) {
				errorFailInsertedPhones = errorFailInsertedPhones + "以下号码无对应客户: " + errorNotExistsPhones;
			}
			if (okInsertedPhones.length() > 0) {
				okInsertedPhones = " 发放成功: " + okInsertedPhones;
			}

			resultString = "批量发放兑换码, 但部分号码需跟进处理: " + errorFailInsertedPhones + okInsertedPhones;

			jsonResult.put("code", -4);
			jsonResult.put("msg", resultString);
			response.getWriter().println(jsonResult.toString());
		} else {
			resultString = "成功批量发放全部兑换码 " + okInsertedPhones;
			jsonResult.put("code", 0);
			jsonResult.put("msg", resultString);
			response.getWriter().println(jsonResult.toString());
		}

		try {
			AdminOperate admin = new AdminOperate();
			admin.setOperateID(UUID.randomUUID().toString());// id
			// admin.setCreatorDate(date);//创建时间
			admin.setCreatorUserID(adminUserInfo.getUserID());// 创建人ID
			admin.setCreatorUserName(adminUserInfo.getUserName());// 创建人姓名
			// admin.setOperateDate(date);//操作时间
			// admin.setSysStatus(1);

//			if (errorFailSentSmsPhones.length() == 0) {
				admin.setOperateContent(StringUtils.substring(resultString, 0, 149)); // 操作内容
//			} else {
//				admin.setOperateContent(StringUtils.substring(resultString, 0, 149)); // 操作内容
//			}

			admin.setOperateMenu("兑换码管理>添加兑换码"); // 操作菜单
			admin.setOperateType("添加");// 操作类型

			adminOperateSer.insertdata(admin);
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 删除记录
	 * @param model
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	public void delete(@PathVariable Integer id, HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");

		if(StringUtils.isBlank(id)) {
			try {
				response.getWriter().println("请求参数无效!");
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
				response.getWriter().println("请重新登录!");
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}

			return;
		}

		ExchangeKey info = new ExchangeKey();
		info.setKeyId(id);
		info.setSysStatus(0);

		if(exchangeKeySer.updateInfoSysStatus(info)){
			try {
				response.getWriter().println("兑换码删除成功!");
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

				admin.setOperateContent("删除了兑换码, 记录ID为: " + info.getKeyId());
				admin.setOperateMenu("兑换码管理>删除兑换码");
				admin.setOperateType("删除");

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		}else{
			try {
				response.getWriter().println("兑换码删除出错!");
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
	public void restorePlan(@PathVariable Integer id, HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");

		if(StringUtils.isBlank(id)) {
			try {
				response.getWriter().println("请求参数无效!");
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
				response.getWriter().println("请重新登录!");
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}

			return;
		}

		ExchangeKey info = new ExchangeKey();
		info.setKeyId(id);
		info.setSysStatus(1);

		if(exchangeKeySer.updateInfoSysStatus(info)){
			try {
				response.getWriter().println("兑换码恢复成功!");
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

				admin.setOperateContent("恢复了兑换码, 记录ID为: " + info.getKeyId());
				admin.setOperateMenu("兑换码管理>恢复兑换码");
				admin.setOperateType("恢复");

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		}else{
			try {
				response.getWriter().println("兑换码恢复出错!");
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	/**
	 * 禁用或允许可兑换
	 *
	 * @param id
	 * @param enabled 为0时，设置为禁用， 为1时，设置为正常
	 * @param response
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping("/enable/{id}")
	public void enable(@PathVariable Integer id, String enabled, HttpServletResponse response, Model model) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonResult = new JSONObject();

		if(StringUtils.isBlank(id) || StringUtils.isBlank(enabled)) {
			jsonResult.put("code", -1);
			jsonResult.put("msg", "请求参数无效!");
			response.getWriter().println(jsonResult.toString());
			return;
		}

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");

		if (adminUserInfo == null) {
			jsonResult.put("code", -5);
			jsonResult.put("msg", "请重新登录!");
			response.getWriter().println(jsonResult.toString());
			return;
		}

		ExchangeKey info = new ExchangeKey();
		info.setKeyId(id);
		String resultDesc = null;
		if ("0".equals(enabled)) {
			info.setStatus("禁用");
			resultDesc = "兑换码禁止兑换";
		} else if ("1".equals(enabled)) {
			info.setStatus("正常");
			resultDesc = "兑换码允许兑换";
		} else {
			jsonResult.put("code", -1);
			jsonResult.put("msg", "请求参数无效!");
			response.getWriter().println(jsonResult.toString());
			return;
		}

		if(exchangeKeySer.updateInfoStatus(info)){
			jsonResult.put("code", 0);
			jsonResult.put("msg", resultDesc + "成功!");
			response.getWriter().println(jsonResult.toString());

			try {
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());//id
				// admin.setCreatorDate(date);//创建时间
				admin.setCreatorUserID(adminUserInfo.getUserID());//创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());//创建人姓名
				//admin.setOperateDate(date);//操作时间
				//admin.setSysStatus(1);

				admin.setOperateContent("更新了" + resultDesc + ", 记录ID为: " + info.getKeyId());
				admin.setOperateMenu("兑换码管理>兑换码列表");
				admin.setOperateType("更新");

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		}else{
			jsonResult.put("code", -2);
			jsonResult.put("msg", resultDesc + "出错!");
			response.getWriter().println(jsonResult.toString());
		}

	}

	/**
	 * AJAX 返回自动生成的兑换码供前端编辑兑换码时使用
	 *
	 * @param phone
	 * @param response
	 * @param model
	 */
	@RequestMapping("/getKey/{phone}")
	public void getKey(@PathVariable String phone, HttpServletResponse response, Model model) {
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonResult = new JSONObject();

		if(StringUtils.isBlank(phone)) {
			try {
				jsonResult.put("code", -1);
				jsonResult.put("msg", "需要提供手机号码!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		}
		logger.info("getKey: phone=" + phone);

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");

		if (adminUserInfo == null) {
			try {
				jsonResult.put("code", -5);
				jsonResult.put("msg", "请重新登录!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}

			return;
		}

		Map<String, String> result = Easy2goUtil.generateExchangeKey(phone);
		logger.info("getKey: generateExchangeKey ok"); // 上线时禁止日志显示手机和兑换码

		if (null != result && result.size() == 1) {
			try {
				String keyTimestamp = "", key = "";
				for (Iterator<String> iterator = result.keySet().iterator(); iterator.hasNext();) {
					keyTimestamp = (String) iterator.next();
					key = result.get(keyTimestamp);
					break;
				}
				jsonResult.put("code", 0);
				jsonResult.put("msg", "ok");
				jsonResult.put("keyTimestamp", keyTimestamp);
				jsonResult.put("key", key);
				response.getWriter().println(jsonResult.toString());

				logger.info("getKey: OK");
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		} else {
			try {
				jsonResult.put("code", -2);
				jsonResult.put("msg", "系统出错，请重试!");
				response.getWriter().println(jsonResult.toString());
			} catch (IOException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	/**
	 * 给客户重发短信
	 *
	 * @param id
	 * @param response
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping("/sendSms/{id}")
	public void sendSms(@PathVariable Integer id, HttpServletResponse response, Model model) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonResult = new JSONObject();

		if(StringUtils.isBlank(id)) {
			jsonResult.put("code", -1);
			jsonResult.put("msg", "请求参数无效!");
			response.getWriter().println(jsonResult.toString());
			return;
		}

		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");
		if (adminUserInfo == null) {
			jsonResult.put("code", -5);
			jsonResult.put("msg", "请重新登录!");
			response.getWriter().println(jsonResult.toString());
			return;
		}

		ExchangeKey info = new ExchangeKey();
		info.setKeyId(id);

		ExchangeKey key = exchangeKeySer.getById(String.valueOf(id));
		if (null == key) {
			jsonResult.put("code", -3);
			jsonResult.put("msg", "查无此记录！");
			response.getWriter().println(jsonResult.toString());
			return;
		} else if ("禁用".equals(key.getStatus())) {
			jsonResult.put("code", -4);
			jsonResult.put("msg", "这个兑换码被禁用了，不能发送短信！");
			response.getWriter().println(jsonResult.toString());
			return;
		}

		if(SMSUltis.sendTemplateSmsExchangeCodeSend(key.getPhone(), key.getKey())){
			// 修改发送次数
			ExchangeKey updateKey = new ExchangeKey();
			updateKey.setKeyId(id);
			updateKey.setSendSmsCount(1); // 加1
			if (!exchangeKeySer.updateSendSmsCount(updateKey)) {
				// 提醒前端管理员注意处理，已发送成功但更新短信发送次数失败
				jsonResult.put("code", 10);
				jsonResult.put("msg", "成功发送短信, 但更新发送次数失败，一般情况无需处理。 " + key.getPhone() + " 兑换码：" + key.getKey());
				response.getWriter().println(jsonResult.toString());
			} else {
				jsonResult.put("code", 0);
				jsonResult.put("msg", "发送成功!");
				response.getWriter().println(jsonResult.toString());
			}

			try {
				AdminOperate admin = new AdminOperate();
				admin.setOperateID(UUID.randomUUID().toString());//id
				// admin.setCreatorDate(date);//创建时间
				admin.setCreatorUserID(adminUserInfo.getUserID());//创建人ID
				admin.setCreatorUserName(adminUserInfo.getUserName());//创建人姓名
				//admin.setOperateDate(date);//操作时间
				//admin.setSysStatus(1);

				admin.setOperateContent("发送了短信" + ", 记录ID为: " + info.getKeyId());
				admin.setOperateMenu("兑换码管理>兑换码列表");
				admin.setOperateType("更新");

				adminOperateSer.insertdata(admin);
			} catch (Exception e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		}else{
			jsonResult.put("code", -2);
			jsonResult.put("msg", "发送短信出错! 请重试");
			response.getWriter().println(jsonResult.toString());
		}

	}

    @RequestMapping("/exportExcel")
	public void exportExcel(String phone,String key,String useStatus,String status,String type,String begindate,String enddate,
			String all,Integer sta,Integer end,Integer cur,Integer pagesize,Integer total,
			HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{

    	if (StringUtils.isBlank(begindate) || StringUtils.isBlank(enddate)) {
			try {
				response.getWriter().println("请提供开始时间和结束时间");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}

    	// 第一步，创建一个workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("兑换码表一");
        sheet.setDefaultRowHeightInPoints(5000);

        sheet.setColumnWidth((short)0, (short)3000); // 9000
        sheet.setColumnWidth((short)1, (short)4000);
        sheet.setColumnWidth((short)2, (short)4000);
        sheet.setColumnWidth((short)3, (short)3000); // 5500
        sheet.setColumnWidth((short)4, (short)3000);
        sheet.setColumnWidth((short)5, (short)3000);
        sheet.setColumnWidth((short)6, (short)2500);
        sheet.setColumnWidth((short)7, (short)3000);
        sheet.setColumnWidth((short)8, (short)5000);
        sheet.setColumnWidth((short)9, (short)3000);
        sheet.setColumnWidth((short)10, (short)5000);
        sheet.setColumnWidth((short)11, (short)9000);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        int headerRows = 2; // 表头的行数, 当前有两行
        HSSFRow row = sheet.createRow((int) 0);
        // short c = 500;
        // row.setHeight(c);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        // 第一行表头 列明搜索参数如 开始时间 结束时间 等
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("结果参数：");
        cell.setCellStyle(style);
        cell = row.createCell((short) 1);
        cell.setCellValue("");
        cell.setCellStyle(style);
        cell = row.createCell((short) 2);
        cell.setCellValue("时间段："); // 开始时间：
        cell.setCellStyle(style);
        cell = row.createCell((short) 3);
        cell.setCellValue(begindate);
        cell.setCellStyle(style);
        cell = row.createCell((short) 4);
        cell.setCellValue("到");
        cell.setCellStyle(style);
        cell = row.createCell((short) 5);
        cell.setCellValue(enddate);
        cell.setCellStyle(style);

        cell = row.createCell((short) 6);
        cell.setCellValue("手机号:");
        cell.setCellStyle(style);
        cell = row.createCell((short) 7);
        cell.setCellValue(phone);
        cell.setCellStyle(style);

        cell = row.createCell((short) 8);
        cell.setCellValue("兑换码:");
        cell.setCellStyle(style);
        cell = row.createCell((short) 9);
        cell.setCellValue(key);
        cell.setCellStyle(style);

        cell = row.createCell((short) 10);
        cell.setCellValue("状态:");
        cell.setCellStyle(style);
        String statusString = "";
        if ("正常".equals(status)) {
        	statusString += "可兑换-";
		} else if ("禁用".equals(status)) {
			statusString += "禁用兑换-";
		} else {
			//
		}
        if ("是".equals(useStatus)) {
        	statusString += "已兑换-";
		} else if ("否".equals(useStatus)) {
			statusString += "未兑换-";
		} else {
			//
		}
        if ("流量账户".equals(type) || "基本账户".equals(type)
        		|| "话费账户".equals(type) || "流量".equals(type)
        		|| "基本".equals(type) || "话费".equals(type)) {
        	statusString += type + "-";
		} else {
			statusString += "全部账户-";
		}
        if ("1".equals(all)) {
        	statusString += "全部页";
		} else {
			statusString += "第" + cur + "页";
		}
        cell = row.createCell((short) 11);
        cell.setCellValue(statusString);
        cell.setCellStyle(style);

        // 第二行系表头
        row = sheet.createRow((int) 1);
        cell = row.createCell((short) 0);
        cell.setCellValue("流水ID");
        cell.setCellStyle(style);
        cell = row.createCell((short) 1);
        cell.setCellValue("手机");
        cell.setCellStyle(style);
        cell = row.createCell((short) 2);
        cell.setCellValue("兑换码");
        cell.setCellStyle(style);
        cell = row.createCell((short) 3);
        cell.setCellValue("开始日期");
        cell.setCellStyle(style);
        cell = row.createCell((short) 4);
        cell.setCellValue("结束日期");
        cell.setCellStyle(style);
        cell = row.createCell((short) 5);
        cell.setCellValue("可用状态");
        cell.setCellStyle(style);
        cell = row.createCell((short) 6);
        cell.setCellValue("充值类型");
        cell.setCellStyle(style);
        cell = row.createCell((short) 7);
        cell.setCellValue("金额");
        cell.setCellStyle(style);
        cell = row.createCell((short) 8);
        cell.setCellValue("兑换时间");
        cell.setCellStyle(style);
        cell = row.createCell((short) 9);
        cell.setCellValue("创建人");
        cell.setCellStyle(style);
        cell = row.createCell((short) 10);
        cell.setCellValue("创建时间");
        cell.setCellStyle(style);
        cell = row.createCell((short) 11);
        cell.setCellValue("备注");
        cell.setCellStyle(style);

        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，
        //  List list = CreateSimpleExcelToDisk.getStudent();

        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 创建一个居左格式

        ExchangeKey info = new ExchangeKey();
        if (StringUtils.isNotBlank(phone)) {
			info.setPhone(phone);
		}
        if (StringUtils.isNotBlank(key)) {
			info.setKey(key);
		}
        info.setStartDate(begindate);
        info.setEndDate(enddate);
        info.setUseStatus(useStatus);
        info.setStatus(status);
        info.setType(type);
        if ("1".equals(all)) { // 全部时,则设定为第1页,页数设为一个较大的值
			cur = 1;
			pagesize = 2000; //10000;
		}
        SearchDTO seDto = new SearchDTO(cur,
				pagesize, "",
				"", info);
        List<ExchangeKey> result = new ArrayList<ExchangeKey>();
        Page infoPage = exchangeKeySer.getPageInfo(seDto);
        if (null != infoPage) {
        	result = (List<ExchangeKey>) infoPage.getRows();
		}
        for (int i = 0; i < result.size(); i++)
        {
        	row = sheet.createRow((int) i + headerRows); // 1 // marks:! 这是标题行的行数, 当前有两行
        	ExchangeKey rowInfo = result.get(i);
        	// 第四步，创建单元格，并设置值
			cell = row.createCell((short) 0);
			cell.setCellValue(rowInfo.getKeyId());
			cell.setCellStyle(style2); // 居中风格
            row.createCell((short) 1).setCellValue(rowInfo.getPhone());
            row.createCell((short) 2).setCellValue(rowInfo.getKey());
            row.createCell((short) 3).setCellValue(rowInfo.getStartDate());
            row.createCell((short) 4).setCellValue(rowInfo.getEndDate());
            row.createCell((short) 5).setCellValue(rowInfo.getStatus());
//            if ("流量账户".equals(rowInfo.getType()) || "流量".equals(rowInfo.getType())) {
//            	row.createCell((short) 6).setCellValue("流量账户");
//			} else if ("基本账户".equals(rowInfo.getType()) || "基本".equals(rowInfo.getType())) {
//				row.createCell((short) 6).setCellValue("基本账户");
//			} else
			{
				row.createCell((short) 6).setCellValue(rowInfo.getType());
			}
            row.createCell((short) 7).setCellValue(rowInfo.getAmount());
            if (StringUtils.isBlank(rowInfo.getUseDateTime())) {
            	row.createCell((short) 8).setCellValue("未兑换");
			} else {
				row.createCell((short) 8).setCellValue(rowInfo.getUseDateTime());
			}
            row.createCell((short) 9).setCellValue(rowInfo.getCreatorUserName());
            row.createCell((short) 10).setCellValue(rowInfo.getCreatorDate());
            row.createCell((short) 11).setCellValue(rowInfo.getRemark());

//            cell = row.createCell((short) 3);
//            cell.setCellStyle(style2); // 居中风格
        }

        //download("flow-" + sn + "-" + begindate + "-" + enddate + ".xls", wb, request, response);
        try {
        	String filenameString = begindate+"到"+enddate+"兑换码";
        	if (StringUtils.isNotBlank(phone)) {
        		filenameString += "-" + phone;
			}
        	if (StringUtils.isNotBlank(key)) {
        		filenameString += "-" + key;
			}
//        	if ("1".equals(all)) {
//        		filenameString += "-全部";
//    		} else {
//    			filenameString += "-第" + cur + "页";
//    		}
        	filenameString += statusString;
			DownLoadUtil.execlExpoprtDownload(filenameString+".xls",wb, request, response);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    }

}
