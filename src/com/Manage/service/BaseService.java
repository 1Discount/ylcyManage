package com.Manage.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.Manage.dao.AcceptOrderDao;
import com.Manage.dao.AdminOperateDao;
import com.Manage.dao.AdminUserInfoDao;
import com.Manage.dao.AfterSaleInfoDao;
import com.Manage.dao.BackgroundCheckDao;
import com.Manage.dao.CountryInfoDao;
import com.Manage.dao.CouponDao;
import com.Manage.dao.CouponRelationshipDao;
import com.Manage.dao.CustomerInfoDao;
import com.Manage.dao.DepositRecordDao;
import com.Manage.dao.DevMessageDao;
import com.Manage.dao.DeviceDealOrdersDao;
import com.Manage.dao.DeviceFlowDao;
import com.Manage.dao.DeviceInfoDao;
import com.Manage.dao.DeviceJumpInfoDao;
import com.Manage.dao.DeviceLogsDao;
import com.Manage.dao.DeviceLogsTempDao;
import com.Manage.dao.DictionaryDao;
import com.Manage.dao.DistributorDao;
import com.Manage.dao.DistributorMenuGroupInfoDao;
import com.Manage.dao.DistributorMenuInfoDao;
import com.Manage.dao.DistributorRoleInfoDao;
import com.Manage.dao.DistributorRoleToMenuDao;
import com.Manage.dao.EnterpriseDao;
import com.Manage.dao.EquipLogsDao;
import com.Manage.dao.ExchangeKeyDao;
import com.Manage.dao.ExchangeRecordDao;
import com.Manage.dao.FlowDealOrdersDao;
import com.Manage.dao.FlowPlanInfoDao;
import com.Manage.dao.GStrenthDataDao;
import com.Manage.dao.InvitationDao;
import com.Manage.dao.IpPhoneClientDao;
import com.Manage.dao.JizhanInfoDao;
import com.Manage.dao.MenuGroupInfoDao;
import com.Manage.dao.MenuInfoDao;
import com.Manage.dao.OperatorInfoDao;
import com.Manage.dao.OrdersInfoDao;
import com.Manage.dao.PrepareCardTempDao;
import com.Manage.dao.PushMessageDetailsDao;
import com.Manage.dao.PushMessageInfoDao;
import com.Manage.dao.RefundDao;
import com.Manage.dao.RoamingSIMInfoDao;
import com.Manage.dao.RoleInfoDao;
import com.Manage.dao.RoleToMenuDao;
import com.Manage.dao.SIMInfoCountryTempDao;
import com.Manage.dao.SIMInfoDao;
import com.Manage.dao.SIMRechargeBillDao;
import com.Manage.dao.SIMServerDao;
import com.Manage.dao.SubmitOrdersDao;
import com.Manage.dao.TDMInfoDao;
import com.Manage.dao.UpgradeFileInfoDao;
import com.Manage.dao.VIPCardInfoDao;
import com.Manage.dao.VendorDeviceBindKeyDao;
import com.Manage.dao.VirtualSIMInfoDao;
import com.Manage.dao.VpnInfoDao;
import com.Manage.dao.WxBindDeviceDao;


public class BaseService {

	@Autowired
	protected AdminUserInfoDao adminUserInfoDao;//用户信息表

	@Autowired
	protected InvitationDao invitationDao;//邀请注册表

	@Autowired
	protected FlowPlanInfoDao flowPlanInfoDao;	//流量套餐表

	@Autowired
	protected CountryInfoDao countryInfoDao;	//国家信息表

	@Autowired
	protected SIMServerDao simServerDao;	//SIM服务器表

	@Autowired
	protected VpnInfoDao vpnInfoDao;    //VPN信息表

	@Autowired
	protected SIMInfoDao simInfoDao;	//SIM卡表
	@Autowired
	protected RoamingSIMInfoDao roamingSimInfoDao; // 漫游SIM卡表

	@Autowired
	protected SIMRechargeBillDao simRechargeBillDao;	//SIM卡充值记录表

	@Autowired
	protected OperatorInfoDao operatorInfoDao;	//运营商表

	@Autowired
	protected MenuGroupInfoDao menuGroupInfoDao;   //菜单组表

	@Autowired
	protected MenuInfoDao menuInfoDao;   //菜单表

	@Autowired
	protected RoleInfoDao roleInfoDao;   //角色表

	@Autowired
	protected RoleToMenuDao roleToMenuDao; //角色菜单映射
	
	
	@Autowired
	protected DistributorMenuGroupInfoDao distributorMenuGroupInfoDao;   //渠道商菜单组表

	@Autowired
	protected DistributorMenuInfoDao distributorMenuInfoDao;   //渠道商菜单表

	@Autowired
	protected DistributorRoleInfoDao distributorRoleInfoDao;   //渠道商角色表

	@Autowired
	protected DistributorRoleToMenuDao distributorRoleToMenuDao; //渠道商角色菜单映射


	@Autowired
	protected CustomerInfoDao customerInfoDao;

	@Autowired
	protected DistributorDao distributorDao;

	//*********************订单部分****************
	@Autowired
	protected OrdersInfoDao ordersInfoDao; //订单表

	@Autowired
	protected FlowDealOrdersDao flowDealOrdersDao; //流量交易表

	@Autowired
	protected DeviceDealOrdersDao deviceDealOrdersDao;//设备交易表

	@Autowired
	protected AcceptOrderDao acceptOrderDao; //接单表

	//*******************************************

	//*************************lipeng*****************
	 @Autowired
	 protected DeviceInfoDao deviceInfoDao;//设备信息

	 @Autowired
	 protected DictionaryDao dictonaryDao;//数据字典

	 @Autowired
	 protected AdminOperateDao adminOperateDao;//用户操作日志

	 @Autowired
	 protected DeviceLogsDao deviceLogsDao;//设备日志
	//*********************************************
	 @Autowired
	 protected EquipLogsDao equipLogsDao;

	 @Autowired
	 protected DeviceLogsTempDao  deviceLogsTempDao;

	 @Autowired
	 protected DepositRecordDao depositRecordDao; // 押金申请记录

	 @Autowired
	 protected WxBindDeviceDao wxBindDeviceDao; // 微信绑定SN关系

	 @Autowired
	 protected ExchangeKeyDao exchangeKeyDao; // 兑换码
	 @Autowired
	 protected ExchangeRecordDao exchangeRecordDao; // 兑换码兑换记录

	 @Autowired
	 protected VirtualSIMInfoDao virtualSIMInfoDao; // 虚拟SIM卡
	 @Autowired
	 protected RefundDao refundDao;//退款

	 @Autowired
	 protected CouponDao couponDao;//优惠劵

	 @Autowired
	 protected GStrenthDataDao gStrenthDataDao;//基站经纬度表

	 @Autowired
	 protected CouponRelationshipDao couponRelationshipDao;//优惠券关系表

	 @Autowired
	 protected VIPCardInfoDao vipCardInfoDao ;//VIP优惠表

	 @Autowired
	 protected com.Manage.dao.ShipmentDao ShipmentDao ;//出货记录表

	 @Autowired
	 protected com.Manage.dao.WorkFlowDao workFlowDao ;//出货记录表

	 @Autowired
	 protected AfterSaleInfoDao afterSaleInfoDao;//售后服务记录表


	 @Autowired
	 protected IpPhoneClientDao iPhoneClientDao;

	 @Autowired
	 protected SubmitOrdersDao submitOrdersDao;//提工单表


	 @Autowired
     protected VendorDeviceBindKeyDao VendorDeviceBindKeyDao; // 设备绑定码


	 
	 @Autowired
	 protected DevMessageDao devMessageDao;//设备短信记录表

	 @Autowired
	 protected DeviceFlowDao deviceFlowDao;//设备短信记录表
	 
	 @Autowired
	 protected EnterpriseDao enterpriseDao;//企业用户表

	 @Autowired
	 protected BackgroundCheckDao backgroundCheckDao;//后台自检Dao
	 
	 @Autowired
	 protected PrepareCardTempDao prepareCardTempDao;//后台自检Dao

	 @Autowired
	 protected JizhanInfoDao jizhanInfoDao;//基站信息表
	 
	 @Autowired
	 protected PushMessageInfoDao pushMessageInfoDao;//推送信息表

	 @Autowired
	 protected PushMessageDetailsDao pushMessageDetailsDao;//推送信息详细表
	 
	 @Autowired
	 protected UpgradeFileInfoDao upgradeFileInfoDao;//固件升级文件
	 
	 @Autowired
	 protected SIMInfoCountryTempDao simInfoCountryTempDao;//备卡查询
	 
	 @Autowired
	 protected TDMInfoDao tdmInfoDao;//tdm
	 
	 @Autowired
	 protected DeviceJumpInfoDao deviceJumpInfoDao;//设备跳转记录
}
