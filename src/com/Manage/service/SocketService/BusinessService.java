package com.Manage.service.SocketService;
import com.Manage.common.exception.SCException;
import com.Manage.entity.common.SocketMessage;


/**
 * @author wangbo
 */
public interface BusinessService
{
	/**
	 * 设置业务数据消息实体，在接入层调用。
	 *
	 * @param SocketMessage 业务消息
	 */
	void setMessageHead(SocketMessage message);

	/**
	 * 执行业务，在接入层调用。
	 *
	 * @param service
	 * @throws SCException
	 */
	void processBusiness(BusinessService service) throws SCException;

	/**
	 * 初始化公共数据。
	 */
	void initCommon();

	/**
	 * 业务数据校验。
	 *
	 * @throws SCException
	 */
	void doValidate() throws SCException;

	/**
	 * 执行业务(无事务)。
	 *
	 * @param businessInfo
	 * @throws SCException
	 */
	void doBusiness() throws SCException;

	/**
	 * 执行业务(事务)。
	 *
	 * @param businessInfo
	 * @throws SCException
	 */
	void doBusinessWithTrans() throws SCException;

	/**
	 * 业务后处理。
	 *
	 * @param businessInfo
	 */
	void doPostBusiness();

	/**
	 * 获取业务执行结果。在接入层调用。
	 *
	 * @return Object 由具体业务进行转型
	 */
	Object getResult();

}
