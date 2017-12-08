package com.Manage.common.exception;

/**
 * 系统自定义异常类。
 * 
 * @author wangbo
 * 
 */
public class SCException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 错误码。
	 */
	private Integer errorCode;
	
	/**
	 * 问题解决措施。
	 */
	private String solution;
	
	/**
	 * 指定错误码与错误描述的异常。
	 * 
	 * @param errorCode 错误码
	 * @param msg 异常信息
	 */
	public SCException(Integer errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}

	/**
	 * 指定错误码与错误描述的异常。
	 * @param errorCode 错误码
	 * @param msg 异常信息
	 * @param args 异常信息中的动态参数列表
	 */
	/*public SCException(Integer errorCode, Object[] args) {
		super(ResourceUtils.getResultCodeDesc(errorCode, args));
		this.errorCode = errorCode;
		this.solution = ResourceUtils.getSolutionInfo(errorCode);
	}*/

	/**
	 * 指定错误码与错误描述的异常。
	 * @param errorCode 错误码
	 * @param msg 异常信息
	 */
	/*public SCException(Integer errorCode) {
		this(errorCode, new Object[0]);
	}*/

	/**
	 * 指定错误码与错误描述的异常。
	 * 
	 * @param errorCode 错误码
	 * @param msg 异常信息
	 * @param args 异常信息中的动态参数列表
	 */
	/*public SCException(Integer errorCode, Object arg) {
		this(errorCode, new Object[]{ arg });
	}*/

	/**
	 * 未定义异常。
	 *//*
	public SCException() {
		this(ResultCode4Common.ERRORCODE_IS_UNDEFINITION);
	}*/

	/**
	 * @return the errorCode
	 */
	public Integer getErrorCode()
	{
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(Integer errorCode)
	{
		this.errorCode = errorCode;
	}

	/**
	 * @return the solution
	 */
	public String getSolution()
	{
		return solution;
	}

	/**
	 * @param solution the solution to set
	 */
	public void setSolution(String solution)
	{
		this.solution = solution;
	}
}
