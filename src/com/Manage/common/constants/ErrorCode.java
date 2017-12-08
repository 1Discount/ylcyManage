package com.Manage.common.constants;
/** * @author  wangbo: * @date 创建时间：2015-6-19 下午2:13:20 * @version 1.0 * @parameter  * @since  * @return  */
public class ErrorCode {
	
	/**正常处理完成返回结果**/
	public final static int ERROR_SUCCESS=0;
	/**输入参数校验失败**/
	public final static int ERROR_VERIFYFAIL=1;
	/** 查询结果为空**/
	public final static int ERROR_DATAACCESSFAIL=2;
	/**操作失败**/
	public final static int ERROR_NULLRESULT=3;
	
	
	/**[描述]正常处理完成返回结果**/
	public final static String ERROR_SUCCESS_MSG="操作成功";
	/**[描述]输入参数校验失败或者校验中发生异常**/
	public final static String ERROR_VERIFYFAIL_MSG="输入参数校验失败";
	/**[描述]操作失败**/
	public final static String ERROR_DATAACCESSFAIL_MSG="查询结果为空";
	/**[描述]查询结果为空**/
	public final static String ERROR_NULLRESULT_MSG="操作失败";
	
}
