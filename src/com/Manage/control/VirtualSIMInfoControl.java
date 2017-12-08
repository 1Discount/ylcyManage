package com.Manage.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.log4j.Logger;

import sun.misc.UUDecoder;

import com.Manage.common.constants.Constants;
import com.Manage.common.util.LogUtil;
import com.Manage.common.util.StringUtils;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.CountryInfo;
import com.Manage.entity.Dictionary;
import com.Manage.entity.SIMInfo;
import com.Manage.entity.SIMServer;
import com.Manage.entity.VirtualSIMInfo;
import com.Manage.entity.common.SearchDTO;
import com.Manage.service.VirtualSIMInfoSer;
import com.sun.org.apache.bcel.internal.generic.NEW;

@Controller
@RequestMapping("/sim/virtualsiminfo")
public class VirtualSIMInfoControl extends BaseController {
	private Logger logger = LogUtil.getInstance(VirtualSIMInfoControl.class);
	@RequestMapping("/index")
	public String index(HttpServletResponse response,HttpServletRequest request, Model model){
		List<CountryInfo> countries = countryInfoSer.getAll("");
		model.addAttribute("Countries", countries);
		return "WEB-INF/views/sim/virtualsiminfo_index";
	}
	
	@RequestMapping("/addvirtualsiminfo")
	public String addvirtualsiminfo(HttpServletResponse response,HttpServletRequest request, Model model){
		List<CountryInfo> countries = countryInfoSer.getAll("");//获取到了所有有国家
            for (CountryInfo country : countries) {
                    country.setSelected(false);
            }
        model.addAttribute("Countries", countries);
        List<Dictionary> cardStatus = dictionarySer.getAllList(Constants.DICT_SIMINFO_CARD_STATUS);
        model.addAttribute("CardStatusDict", cardStatus);
		return "WEB-INF/views/sim/virtualsiminfo_edit";
	}
	
	@RequestMapping("/datapage")
	public void datepage(SearchDTO searchDTO, VirtualSIMInfo info,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		SearchDTO seDto = new SearchDTO(searchDTO.getCurPage(),
				searchDTO.getPageSize(), searchDTO.getSortName(),
				searchDTO.getSortOrder(), info);
		String jsonString = virtualSIMInfoSer.getVirtualAll(seDto);
		System.out.println(jsonString);
		try {
			response.getWriter().println(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 更新入口
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		
		
		VirtualSIMInfo info = virtualSIMInfoSer.getvirtualbyid(id);
		
		if (info != null && info.getSIMinfoID() != null) {
		/*	if (StringUtils.isNotBlank(info.getSlotNumber())) {
				String []strings= info.getSlotNumber().split("-");
				info.setSlotNumber(strings[0]+"-"+(Integer.parseInt(strings[1])+1));
			}*/
			List<CountryInfo> countries = countryInfoSer.getAll("");//获取到了所有有国家
			String countryString = info.getCountryList();
            if  (!StringUtils.isBlank(countryString)) {//不是null
                for (CountryInfo country : countries) {
                    String matchString = String.valueOf(country.getCountryCode());
                    if  (StringUtils.contains(countryString, matchString)) {
                        country.setSelected(true);
                    }
                }
            }
            model.addAttribute("Countries", countries);

			/*List<SIMServer> simServers = simServerSer.getAll("");
			model.addAttribute("SIMServers", simServers);
*/
	        List<Dictionary> cardStatus = dictionarySer.getAllList(Constants.DICT_SIMINFO_CARD_STATUS);
	        model.addAttribute("CardStatusDict", cardStatus);
	      /*  List<Dictionary> simBillMethods = dictionarySer.getAllList(Constants.DICT_SIMINFO_SIM_BILL_METHOD);
	        model.addAttribute("SimBillMethodDict", simBillMethods);
			model.addAttribute("Model", info);*/
		} else {
			model.addAttribute("info","此SIM卡不存在或已无效!");
		}
		model.addAttribute("Model", info);
		return "WEB-INF/views/sim/virtualsiminfo_edit";
	}
	@RequestMapping("/save")
	public void save(VirtualSIMInfo virtualSIMInfo,HttpServletResponse response,Model mode ) throws IOException{
		AdminUserInfo adminUserInfo = (AdminUserInfo) getSession()
				.getAttribute("User");
		//保存
		if(virtualSIMInfo!=null){
			virtualSIMInfo.setCountryList(virtualSIMInfo.getCountryList().replace(",", "|"));
		}
		int count=0;
		if(StringUtils.isBlank(virtualSIMInfo.getSIMinfoID())){
			virtualSIMInfo.setSIMinfoID(UUID.randomUUID().toString());
			virtualSIMInfo.setCreatorDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			virtualSIMInfo.setCreatorUserName(adminUserInfo.getUserName());
			count = virtualSIMInfoSer.insert(virtualSIMInfo);
		}else{
			virtualSIMInfo.setModifyDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			virtualSIMInfo.setModifyUserID(adminUserInfo.getUserID());
			count =  virtualSIMInfoSer.save(virtualSIMInfo);;
		}
		if(count==1){
			response.getWriter().print("1");//修改成功
		}else {
			response.getWriter().print("0");//修改失败
		}
	}
	@RequestMapping("/delete/{id}")
	public void delete(@PathVariable String id,HttpServletResponse response, Model model) throws IOException{
		int count = virtualSIMInfoSer.delete(id);
		if(count==1){
			response.getWriter().print("1");//修改成功
		}else {
			response.getWriter().print("0");//修改失败
		}
	}
}
