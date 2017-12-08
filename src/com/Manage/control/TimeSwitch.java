package com.Manage.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Manage.common.constants.Constants;

@Controller
@RequestMapping("/timeswitch")
public class TimeSwitch  extends BaseController{
	/***
	 * @deprecated更变SIM卡开关
	 * @date 2015-11-18
	 *
	 * @author jiangxuecheng
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/simwitch")
	public void simswitch(String status, HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException  {
		request.setCharacterEncoding("utf-8");
		if("on".equals(status)){
			Constants.TIMING_SIMMANAGEJOB=true;
		}if("off".equals(status)){
			Constants.TIMING_SIMMANAGEJOB=false;
		}
	}


	/***
	 * @deprecated更变有赞开关
	 * @date 2015-11-18
	 *
	 * @author jiangxuecheng
	 * @throws UnsupportedEncodingException
	 */
	/*@RequestMapping("/youzhan")
	public void youzhan(String status, HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		request.setCharacterEncoding("utf-8");
		if("on".equals(status)){
			Constants.TIMING_SIMMANAGEJOB=true;
		}if("off".equals(status)){
			Constants.TIMING_SIMMANAGEJOB=false;
		}
	}*/


	/***
	 * @deprecated更变基站开关
	 * @date 2015-11-18
	 *
	 * @author jiangxuecheng
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/jizhan")
	public void jizhan(String status, HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		request.setCharacterEncoding("utf-8");
		if("on".equals(status)){
			Constants.TIMING_LATLONJOB=true;
		}if("off".equals(status)){
			Constants.TIMING_LATLONJOB=false;
		}
	}
}
