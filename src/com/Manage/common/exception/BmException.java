package com.Manage.common.exception;

import org.springframework.dao.DataAccessException;

import com.Manage.common.util.StringUtils;

/**
 * @Desc  自定义DAO层的异常类
 */
public class BmException extends DataAccessException {

	private static final long serialVersionUID = -2824485298409169849L;
	
	private String messageCode;
	
	public String getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
	/**
	 * 普通异常信息
	 * @param message
	 */
	public BmException(String message) {
		super(StringUtils.getResourceMessage(message));
		messageCode = message;
	}
	/**
	 * 带替换参数的异常信息
	 * @param message
	 * @param args
	 */
	public BmException(String message,String[] args) {
		super(StringUtils.getResourceMessage(message, args));
		messageCode = message;
	}
	
	/**
	 * 普通异常与引发这个异常的类
	 * @param message
	 * @param e
	 */
	public BmException(String message,Exception e){
		
		super( StringUtils.getResourceMessage(message) + " " + e.getMessage());
		//修改失败，请稍后再试或与管理员联系!
		// String s = StringUtils.getResourceMessage(message);
		 // null
		 //Throwable eString = e.getCause();
		messageCode = message;
	}
	/**
	 * 带替换参数的异常信息与引发这个异常的类
	 * @param message
	 * @param args
	 * @param e
	 */
	public BmException(String message,String[] args,Exception e){
		super(StringUtils.getResourceMessage(message, args) + " " + e.getCause());
		messageCode = message;
	}
}
