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

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Manage.common.util.LogUtil;
import com.Manage.entity.AdminUserInfo;
import com.Manage.entity.Coupon;
import com.Manage.entity.CouponRelationship;
import com.Manage.entity.Dictionary;
import com.Manage.entity.common.SearchDTO;

@Controller
@RequestMapping("/coupon")
public class CouponControl extends BaseController{
	private Logger logger = LogUtil.getInstance(CouponControl.class);

	@RequestMapping("/list")
	public String couponpage(HttpServletResponse response,HttpServletRequest req){
		List<Coupon> CouponVersionList = couponSer.getcouponVersion();
		req.setAttribute("CouponVersionList", CouponVersionList);
		return "WEB-INF/views/dictionary/coupon_list";
	}

	@RequestMapping("/getlist")
	public void couponListData(SearchDTO searchDTO,Coupon coupon,HttpServletResponse response,HttpServletRequest req) throws UnsupportedEncodingException{
			response.setContentType("application/json;charset=UTF-8");
			req.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			System.out.println("---"+searchDTO.getCurPage()+"------"+searchDTO.getPageSize());
			SearchDTO seDto=new SearchDTO(searchDTO.getCurPage(),searchDTO.getPageSize(),searchDTO.getSortName(),searchDTO.getSortOrder(),coupon);
			String jsonString = couponSer.getpageString(seDto);
			try {
					response.getWriter().println(jsonString);
				} catch (IOException e) {
					e.printStackTrace();
				}
	}

	@RequestMapping("/save")
	public void savecoupon(Coupon coupon,HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws IOException{
		logger.info("进入新增[优惠券]模块。。。。。。");
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");

	    String a="";
		for (int i=0;i<5;i++){a+=(int)(1+Math.random()*9);}
		java.text.DateFormat formataa = new java.text.SimpleDateFormat("yyyyMMddhhmmss");
		String cvesion =  "YHQ"+formataa.format(new Date())+a;

		AdminUserInfo adminUserInfo=(AdminUserInfo)getSession().getAttribute("User");
		coupon.setCreateUserID(adminUserInfo.userID);
		coupon.setCreateUserName(adminUserInfo.userName);
		coupon.setCouponVesion(cvesion);//批次号
		int CouponCount = Integer.parseInt(coupon.getCouponCount());
		int count = 0;
		int num = 0;
		for(int i=0;i<CouponCount;i++){
			coupon.setCouponID(UUID.randomUUID().toString());
			String counumber = UUID.randomUUID().toString();
			coupon.setCouponNumber(counumber.replace("-", ""));//优惠码
			try {
				count = couponSer.saveCoupon(coupon);
				if(count>0){
					num++;
				}
			} catch (Exception e) {
				e.printStackTrace();
				resp.getWriter().append("新增优惠劵时出错，稍后请重试！"+e.getMessage());
			}
		}
		if(num==CouponCount){
			resp.getWriter().append("1");
		}else{
			resp.getWriter().append("0");
		}

	}

	/**
	 * 删除优惠券
	 * @throws IOException
	 */
	@RequestMapping("/deletecoupon")
	public void deletecoupon(Coupon coupon,HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws IOException{
		int delCount = 0;
		try {
			delCount = couponSer.deleteCoupon(coupon.getCouponVesion());
			if(delCount>0){
				resp.getWriter().append("1");
			}else{
				resp.getWriter().append("0");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/getonecoupon")
	public String getOnecoupon(HttpServletRequest req, HttpServletResponse resp){
		 List<Coupon> listCoupon = couponSer.getCouponData();
		 req.setAttribute("listCoupon", listCoupon);
		return "WEB-INF/views/dictionary/getonecoupon";
	}

	/**
	 * 领取优惠券（手机号、批次号）
	 * @throws IOException
	 */
	@RequestMapping("/getthisonecoupon")
	public void getthisonecoupon(CouponRelationship cr,HttpServletRequest req, HttpServletResponse resp) throws IOException{
		//验证手机号是否已经领取
		logger.info("进入领取优惠券，参数手机号码批次号！");
		System.out.println(cr.getPhone()+":"+cr.getCouponVesion());
		int ckphonecount = 0;
		try {
			ckphonecount = couponRelationshipSer.checkphone(cr);
			if(ckphonecount>0){
				resp.getWriter().append("您已经领取了此优惠券，不可重复领取！");
			}else{
				String coupNumber ="";
				try {
					Coupon couponlistone = new Coupon();
					couponlistone = couponSer.getOneCouponData(cr.getCouponVesion());//先查询 获得一个优惠码
					System.out.println("获取到的优惠码："+coupNumber);
					cr.setCouponNumber(couponlistone.getCouponNumber());
					cr.setId(UUID.randomUUID().toString());
					cr.setCouponimg(couponlistone.getCouponimg());
					int crCount = 0;
					try {
						crCount = couponRelationshipSer.inertgetCustomer(cr);//领取
						if(crCount>0){
							int updatecnum = 0;
							logger.info("");
							try {
								updatecnum = couponSer.updateOneCoupon(couponlistone.getCouponNumber());//领取成功 后返回修改Coupon 状态
								if(updatecnum==1){
									resp.getWriter().append("1");
								}else{
									couponRelationshipSer.delegetCustomer(couponlistone.getCouponNumber());
									resp.getWriter().append("领取失败！");
									logger.info("领取成功修改优惠券数据时出错！后面撤销领取记录");
								}
							} catch (Exception e) {
								couponRelationshipSer.delegetCustomer(couponlistone.getCouponNumber());
								e.printStackTrace();
								//修改回去
								logger.info("领取成功修改优惠券数据时出错！后面撤销领取记录"+e.getMessage());
								resp.getWriter().append("领取成功修改优惠券数据时出错！后面撤销领取记录！");
							}
						}else{
							resp.getWriter().append("领取优惠码失败！");
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.info("领取优惠码时出错！"+e.getMessage());
						resp.getWriter().append("领取优惠码时出错！");
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("获取优惠码时出错！"+e.getMessage());
					resp.getWriter().append("获取优惠码时出错！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("验证手机号码时出错！"+e.getMessage());
			resp.getWriter().append("验证手机号码时出错！");
		}
	}







}
