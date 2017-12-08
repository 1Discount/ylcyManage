package com.Manage.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Manage.entity.AdminOperate;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.DeviceInfo;
import com.Manage.entity.Dictionary;
import com.Manage.entity.common.SearchDTO;
import com.Manage.service.DictionarySer;

@Controller
@RequestMapping("/dictionary")
public class DictionaryControl extends BaseController{
	
	
	@RequestMapping("/list")
	public String dictonaryList(HttpServletRequest req){
		List<Dictionary> dic = dictionarySer.getAlldescription();
		req.getSession().setAttribute("dic", dic);
		return "WEB-INF/views/dictionary/dictionary_list";
	}
	/**
	 * 查询所有  
	 * @param searchDTO
	 * @param dictionary
	 * @param response
	 * @param req
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/all")
	public void datapage(SearchDTO searchDTO,Dictionary dictionary,HttpServletResponse response,HttpServletRequest req) throws UnsupportedEncodingException{
		response.setContentType("application/json;charset=UTF-8");
		req.setCharacterEncoding("utf-8"); 
		response.setContentType("text/html;charset=utf-8");
		System.out.println("---"+searchDTO.getCurPage()+"------"+searchDTO.getPageSize());
		SearchDTO seDto=new SearchDTO(searchDTO.getCurPage(),searchDTO.getPageSize(),searchDTO.getSortName(),searchDTO.getSortOrder(),dictionary);
		String jsonString=dictionarySer.getpageString(seDto);
		try {
				response.getWriter().println(jsonString);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * 修改
	 * @return
	 */
	@RequestMapping("/update")
	public String updateDic(@RequestParam String dictID, HttpServletRequest req){
			Dictionary dictionary = dictionarySer.getDicEdit(dictID);
			req.setAttribute("dictionary", dictionary);
		return "WEB-INF/views/dictionary/dictionary_list";
	}
	/**
	 * update To
	 * @param req
	 * @param resp
	 * @param session
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="updateToDic", method=RequestMethod.POST)
	public String updateToDic(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws UnsupportedEncodingException
	{
		req.setCharacterEncoding("utf-8"); 
		resp.setContentType("text/html;charset=utf-8");
		
		String dictID = req.getParameter("testdictID");//ID
		
		String value =req.getParameter("values");
		String label = req.getParameter("label");
		String description = req.getParameter("description");
		String remark = req.getParameter("remark");
		String sort = req.getParameter("sort");
		
		SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间  
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记  
	    String date = sdf.format(new Date());
	    
		AdminUserInfo adminUserInfo=(AdminUserInfo)getSession().getAttribute("User");
		String modifyid = adminUserInfo.userID;//创建人ID
		String modifyname = adminUserInfo.userName;
		Dictionary dictionary = new Dictionary(dictID,value,label,description,sort,remark,modifyid,date);
  
		dictionarySer.updateDicToEnd(dictionary);
		req.setAttribute("dictionary", dictionary);
		
		AdminOperate admin = new AdminOperate();
		admin.setOperateID(UUID.randomUUID().toString());//id
		admin.setCreatorDate(date);//创建时间
		admin.setCreatorUserID(modifyid);//创建人ID
		admin.setCreatorUserName(modifyname);//创建人姓名
		admin.setOperateContent("修改数据类型信息："+dictID);//操作内容
		admin.setOperateDate(date);//操作时间
		admin.setOperateMenu("数据字典>修改类型");//操作菜单
		admin.setOperateType("修改");//操作类型
		admin.setSysStatus(1);
		adminOperateSer.insertdata(admin);
		
		return "redirect:list";
 	 
	}
	
	@RequestMapping("/save")
	public String saveDic(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws UnsupportedEncodingException{
		
		req.setCharacterEncoding("utf-8"); 
		resp.setContentType("text/html;charset=utf-8");
		
		String dictID = UUID.randomUUID().toString();//ID
		
		String value =req.getParameter("values");
		String label = req.getParameter("label");
		String description = req.getParameter("description");
		String remark = req.getParameter("remark");
		String sort = req.getParameter("sort");
		
		AdminUserInfo adminUserInfo=(AdminUserInfo)getSession().getAttribute("User");
		String creatorUserID = adminUserInfo.userID;//创建人ID
		String creatorUserName = adminUserInfo.userName;
		
		System.out.println("============================="+creatorUserID+"========================================================");
		System.out.println("dictID:"+dictID+" value:"+value+" label:"+label+" description:"+description+" sort:"+sort+" remark:"+remark+" creatorUserID:"+creatorUserID);
		System.out.println("============================="+creatorUserID+"==================================================");
 
		
		SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间  
	        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记  
		    String creatorDate = sdf.format(new Date());
			int sysStatus=1;

		Dictionary dictionary = new Dictionary(dictID,value,label,description,sort,remark,creatorUserID,sysStatus,creatorDate);
		req.getSession().setAttribute("dictionary", dictionary);
		dictionarySer.getSaveDic(dictionary);
		session.removeAttribute("dictionary");
		
		AdminOperate admin = new AdminOperate();
		admin.setOperateID(UUID.randomUUID().toString());//id
		admin.setCreatorDate(creatorDate);//创建时间
		admin.setCreatorUserID(creatorUserID);//创建人ID
		admin.setCreatorUserName(creatorUserName);//创建人姓名
		admin.setOperateContent("新增数据类型信息："+dictID);//操作内容
		admin.setOperateDate(creatorDate);//操作时间
		admin.setOperateMenu("数据字典>新增类型");//操作菜单
		admin.setOperateType("新增");//操作类型
		admin.setSysStatus(1);
		adminOperateSer.insertdata(admin);

		return "redirect:list";
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("/deletedic")
	public String deletedicInfo(@RequestParam String uid, HttpServletRequest req)
	{
		Dictionary dictionary = dictionarySer.getDicDelete(uid);
		req.setAttribute("dictionary", dictionary);
		
		SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间  
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记  
	    String creatorDate = sdf.format(new Date());
	    
		AdminUserInfo adminUserInfo=(AdminUserInfo)getSession().getAttribute("User");
		String creatorUserID = adminUserInfo.userID;
		String creatorUserName = adminUserInfo.userName;
		
		AdminOperate admin = new AdminOperate();
		admin.setOperateID(UUID.randomUUID().toString());//id
		admin.setCreatorDate(creatorDate);//创建时间
		admin.setCreatorUserID(creatorUserID);//创建人ID
		admin.setCreatorUserName(creatorUserName);//创建人姓名
		admin.setOperateContent("删除数据类型信息："+uid);//操作内容
		admin.setOperateDate(creatorDate);//操作时间
		admin.setOperateMenu("数据字典>删除类型");//操作菜单
		admin.setOperateType("删除");//操作类型
		admin.setSysStatus(1);
		adminOperateSer.insertdata(admin);
		
		return "redirect:list";
	}
	
	/**
	 * getallDescription
	 * 类别分组
	 */
//	@RequestMapping("/getdescription")
//	public String getAllDesc(HttpServletRequest req){
//		List<Dictionary> dic = dictionarySer.getAlldescription();
//		req.getSession().setAttribute("dic", dic);
//		return "WEB-INF/views/dictionary/dictionary_list";
//	}
//	@RequestMapping("/list2")//通过类别查询，属性值
//	public String getlis(){
//		return "WEB-INF/views/dictionary/dictionary_list2";
//	}
	@RequestMapping("/getlabel")
	public String getleib(HttpServletRequest req){
		String lb = req.getParameter("description");
		List<Dictionary> listdic = dictionarySer.getAllList(lb);
		req.setAttribute("dict", listdic);
		return "WEB-INF/views/dictionary/dictionary_list2";
	}
 

	
	//-------------

}
