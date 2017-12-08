package com.Manage.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Manage.entity.AdminOperate;
import com.Manage.entity.Dictionary;
import com.Manage.entity.common.SearchDTO;

@Controller
@RequestMapping("/adminoperate")
public class AdminOperateControl extends BaseController {

	@RequestMapping("/list")
	public String listindex(HttpServletRequest req){
		List<Dictionary> operateType = dictionarySer.getAllList("用户操作类型");
		req.setAttribute("operateType", operateType);
		return "WEB-INF/views/customerinfo/AdminOperate";
	}

	@RequestMapping("/listall")
	public void datapage(SearchDTO searchDTO,AdminOperate adminoperate,HttpServletResponse response){
		response.setContentType("application/json;charset=UTF-8");
		System.out.println("---"+searchDTO.getCurPage()+"------"+searchDTO.getPageSize());
		SearchDTO seDto=new SearchDTO(searchDTO.getCurPage(),searchDTO.getPageSize(),searchDTO.getSortName(),searchDTO.getSortOrder(),adminoperate);
		String jsonString=adminOperateSer.getpageString(seDto);
			try {
				response.getWriter().println(jsonString);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	@RequestMapping("/insert")
	public void insertdata(){
		response.setContentType("application/json;charset=UTF-8");
		AdminOperate admin= new AdminOperate();
		adminOperateSer.insertdata(admin);

	}



}