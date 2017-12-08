package com.Manage.service.SocketService;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.ErrorCode;
import org.springframework.transaction.annotation.Transactional;

import com.Manage.common.exception.SCException;
import com.Manage.common.util.LogUtil;
import com.Manage.entity.common.SocketMessage;
import com.Manage.service.BaseService;



/**
 * 业务流程定义。
 * @author wangbo
 *
 */
public abstract class AbstractBusinessService extends BaseService implements BusinessService
{
	private Logger logger = LogUtil.getInstance(AbstractBusinessService.class);

	protected SocketMessage messageHead = null;

	protected String  returnCode=null;

	private static final String SUCCESS_LOG = "成功";
	private static final String FAIL_LOG = "失败";
	private static final String POST_FAIL_LOG = "后处理失败";
	private static final String INIT_LOG = "初始";

	/**
	 * 防并发控制开关，默认关闭
	 */
	protected boolean concurrentCtrlFlag = false;

	/**
	 * 业务归属群组
	 */
	private String tradeGroup = "";

	/**
	 * 业务处理。
	 *
	 * @param businessService
	 */
	public void processBusiness(BusinessService businessService) throws SCException
	{
		try
		{
			// 初始化公共数据
			logger.info("***** Start to init common info");
			initCommon();
			logger.info("***** End to init common info");

			// 业务逻辑校验
			logger.info("***** Start to do business validate");
			doValidate();
			logger.info("***** End to do validate");

			try
			{
				// 业务处理（无事务）
				logger.info("***** Start to do business");
				doBusiness();
				logger.info("***** End to do business");
				// 业务处理（事务）
				logger.info("***** Start to do business with trans");
				businessService.doBusinessWithTrans();
				logger.info("***** End to do business with trans");
			}
			catch (SCException ex)
			{
				logger.error("业务处理失败.", ex);
				throw ex;
			}

			try
			{
				// 业务后处理
				logger.info("***** Start to do postBusiness");
				doPostBusiness();
				logger.info("***** End to do postBusiness");
			}
			catch (Exception ex)
			{
				logger.error("业务后处理失败.", ex);

				SCException sc = null;

				if (ex instanceof SCException)
				{
					sc = (SCException) ex;
				}
				else
				{

				}
				throw sc;
			}
		}
		catch (Exception ex)
		{
			logger.error(null, ex);

			if (ex instanceof SCException)
			{
				throw (SCException) ex;
			}
			else
			{

			}
		}
		finally
		{

		}
	}


	/**
	 * 构造交互日志
	 */
	abstract public void buildInteractionLog();

	/*
	 *消息转为对象
	 */
	@Override
	public void setMessageHead(SocketMessage messageHeader)
	{
		this.messageHead = messageHeader;
	}

	@Transactional
	public void doBusinessWithTrans()
	{
		this.exectute();
	}

	abstract public void exectute() throws SCException;

}
