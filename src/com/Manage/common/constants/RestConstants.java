package com.Manage.common.constants;

/**
 * Bootstrap constants
 */
public final class RestConstants {
    private RestConstants() {}

    /**
     * a REST style API
     */
//    public static final class Http {
//        private Http() {}

        /**
         * Base URL for all requests
         *
         * 仅待使用! 在 Spring 端使用其他途径获取 EndPoint URL
         */
        public static final String URL_BASE = "http://www.easy2go.cn/api/v3/";

    	//public static final String URL_BASE = "http://127.0.0.1:8070/api/v3/"; // test only!

        /**
         * 登录/验证 URL
         *
         * FRAG = fragment
         */
        /** 登录 */
        public static final String URL_LOGIN_FRAG = "customers/login";
        /** 注销 */
        public static final String URL_LOGOUT_FRAG = "customers/logout";
        /** 注册 */
        public static final String URL_REGISTER_FRAG = "customers/register";
        /** 修改密码 */
        public static final String URL_UPDATE_PASSWORD_FRAG = "customers/updatePwd";
        /** 重置密码(找回密码) */
        public static final String URL_RESET_PASSWORD_FRAG = "customers/resetPwd";
        /** 查询用户信息 */
        public static final String URL_GET_PROFILE_FRAG = "customers/profile";
        /** 修改用户信息 */
        public static final String URL_UPDATE_PROFILE_FRAG = "customers/updateProfile";
        /** 验证用户密码 */
        public static final String URL_CHECK_PASSWORD_FRAG = "customers/checkPassword";
        /** 获取用户全部设备订单 DeviceOrder */
        public static final String URL_GET_DEVICE_ORDERS_FRAG = "customers/deviceorders";
        /** 获取用户全部流量订单 包括运营后台,官网,APP的全部 */
        public static final String URL_GET_FLOW_ORDERS_FRAG = "customers/floworders";
        /** 获取用户全部订单 OrdersInfo, 包含其下的设备订单和流量订单列表 */
        public static final String URL_GET_ORDERS_FRAG = "customers/orders";
        /** 获取用户全部收货地址 */
        public static final String URL_GET_ADDRESSES_FRAG = "customers/addresses";

        /** 更改预约时间 */
        public static final String URL_UPDATE_PLAN_USE_DATE = "customers/updatePlanUseDate";
        /** 退订 */
        public static final String URL_CANCEL_ORDER = "customers/cancelOrder";
        /** 取消未付款订单 */
        public static final String URL_CANCEL_UNFINISHED_ORDER = "customers/cancelUnfinishedOrder";
        /** 申请退押金 */
        public static final String URL_APPLY_DEPOSIT = "customers/applyDeposit";

        /** */
        public static final String URL_SEND_SUM_FRAG = "sys/sendSum";
        /** */
        public static final String URL_CHECK_SUM_FRAG = "sys/checkSum";
      //与旧版本对比, 已不用 ////public static final String URL_CHECK_SUM_FOR_PSW_FRAG = "sys/checkSumForPwd";
        //与旧版本对比, 已不用 //public static final String URL_CHECK_REGISTER_NUM = "sys/checkReg";


        // ====================  WIFI商城  >>>开始
        /**一次获取全部国家信息,前端自行处理去使用*/
        public static final String URL_COUNTRY_ALL = "wifires/countryAll";
        /*** 洲*/
        public static final String URL_WIFIzhou = "wifires/wifizhou";
        /**根据洲名搜索国家*/
        public static final String URL_WIFICOUNTRY = "wifires/wifiguojia";
        /**全部国家分组*/
        public static final String URL_WIFICOUNTRYGROUP = "wifires/wifiguojiagroup";
        /**根据国家名搜索国家信息*/
        public static final String URL_WIFISearchCountry = "wifires/searchCountry";
        /**查询国家价格*/
        public static final String URL_WIFISearchCountrytwo = "wifires/searchCountrytwo";
        /**通过国家id查询国家数据*/
        public static final String URL_WIFISearchCountryforID = "wifires/searchCountryforID";
        /**根据MccList查询多个国家信息*/
        public static final String URL_SEARCH_COUNTRY_BY_MCC_LIST = "wifires/searchCountryByMccList";

        /**查询在线账户余额 */
        public static final String URL_SELECT_ACCOUNT_MONEY = "wifires/selectAccountMoney";

        /**验证输入的sn是否可用 **/
        public static final String URL_WIFICHECKCUSTOMERINFOINPUTSN = "wifires/checksn";

        /** 完成付款修改订单表状态 */
        public static final String URL_UpdateOrdersStatus = "wifires/updateorders";

        /** 完成付款修改流量订单交易表状态 */
        public static final String URL_UpdateFlowDealordersStatus = "wifires/updateflowdealorders";

        /** 完成付款修改设备订单交易表状态 */
        public static final String URL_UpdateDeviceDealOrdersStatus = "wifires/updatedevicedealorders";

        /*** 新增一条流量订单记录 [wifi商城] */
        public static final String URL_GET_AddFlowDealOrders = "wifires/AddFlowDealOrders";

        /*** 测试是否系特卖活动订单，若是则更新活动参与表 */
        public static final String URL_UPDATE_ACTIVITIES_ORDER_STATUS = "wifires/updateActivitiesOrderStatus";

        public static final String URL_APP = "wifires/addOrdersForApp";//APP下单
        public static final String URL_APP_Active = "wifires/activateOrders";//APP激活
        public static final String URL_ADDMONEYTOACCOUNT = "wifires/addMoneyToAccount";//在线账户充值
        public static final String URL_COUNTMONEY = "wifires/countMoney";//计算订单总额
        public static final String URL_HOLDGUOJIA = "wifires/hotCountry";//热门国家


        public static final String URLCREATECLIENT = "wifires/createClient";//createClient

        public static final String URLGETCALLCOUNTRYCODE = "wifires/getCallCountryCode";//备注通话记录
        public static final String URLGETCLIENTNUMDATA = "wifires/getClientNumData";//根据客户id获取ip电话数据

        public static final String URLZXZF = "wifires/zxAccountPay";//app在线支付接口
        public static final String URLGETIPPRICES = "wifires/getIpPhonePrice";//ip电话价格列表
        public static final String URLGETCLIENTDATAFORNUM = "wifires/findClientByMobile";


        public static final String URL_ADDMONEYLOGS = "wifires/addmoneylog";//充值记录
        public static final String URL_CALLACCOUNTLOGS = "wifires/callAccountlog";//话费账单记录
        public static final String URL_FLOWDEALORDERSLOGS = "wifires/getFlowDealOrdersLogs";//流量账单接口

        public static final String URL_ADDMONEYFIRST = "wifires/addMoneyFirst";//app充值首次写入
         // ====================  WIFI商城  >>> 结束

        /**
         * PARAMS for auth
         */
        public static final String PARAM_USERNAME = "phone"; //"username";
        public static final String PARAM_PASSWORD = "password";
        public static final String PARAM_NEW_PASSWORD = "newPassword";
        public static final String PARAM_VERIFY_CODE = "code"; //"code"; // 与NodeJS平台不同

        //public static final String PARSE_APP_ID = "zHb2bVia6kgilYRWWdmTiEJooYA17NnkBSUVsr4H";
        //public static final String PARSE_REST_API_KEY = "N2kCY1T3t3Jfhf9zpJ5MCURn3b25UpACILhnf5u9";
        //public static final String HEADER_PARSE_REST_API_KEY = "X-Parse-REST-API-Key";
        //public static final String HEADER_PARSE_APP_ID = "X-Parse-Application-Id";
        public static final String CONTENT_TYPE_JSON = "application/json";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String SESSION_TOKEN = "sessionToken";

        /**
         * System
         */
        /** 获取 API 版本
        public static final String URL_APP_VERSION_FRAG = "sys/apiversion";
        public static final String URL_APP_VERSION_SHORT_FRAG = ""; // 即 http://www.easy2go.cn/api/v3 也系返回 API 版本信息


        /**
         * 新增充值记录
         */
        public static final String URL_ADDMONEY = "wifires/addmoney";
        //新增充值记录后修改可用金额（充值金额+原来的金额）
        public static final String URL_ADDMONEY_UPDATECOUNT = "wifires/addmoneyupdatemoneycount";
        /**
         * Device
         */
        public static final String URL_GET_VALID_DEVICE_DEAL_FRAG = "customers/devices"; //"/getValidDeviceDeal";
        public static final String URL_GET_DEVICE_DEAL_FRAG = "customers/deviceorders"; // "/getDeviceDeal";

        /***============================ Device=====================*/

        /*** 设备库存量*/

        public static final String URL_GET_DEVICE_COUNT = "device/deviceCount";
        /*** 获取客户收货地址*/
        public static final String URL_GET_DEVICE_SHIPAddress = "device/deviceShipAddress";
        /*** 新增收货地址*/
        public static final String URL_GET_ADDSHIPADDRESS = "device/adddeviceshipaddress";
        /*** 新增收货地址*/
        public static final String URL_GET_UPDATEdeviceStatus = "device/deviceorders";
        /*** 取一条可用的设备数据*/
        public static final String URL_GET_SearchOneDevice = "device/searchonedevicedata";
        /*** 新增一条设备订单表记录*/
        public static final String URL_GET_ADDDeviceDealOrders = "device/addDeviceDealOrders";
        /*** 新增一条订单记录 [购买设备的订单]*/
        public static final String URL_GET_AddDeviceOrders = "device/AddDeviceOrders";
        /**
         * Bandwidth Plan
         */
        public static final String URL_GET_COUNTRIES_FRAG = "countries";
        public static final String URL_GET_PLANS_FRAG = "plans";
        public static final String URL_ORDER_ACTIVATE_FRAG = "activeDeal";
        public static final String URL_ORDER_WITHDRAW_FRAG = "cancelDeal";
        public static final String URL_ORDER_UPDATE_RESERVE_TIME_FRAG = "updateReserveTime";
        public static final String URL_GET_PLAN_USER_DEAL_FRAG = "getUserDeal";
        public static final String URL_GET_CUSTOM_PRICE = "customPrice";
        public static final String URL_GET_CUSTOM_ORDER = "customDeal";
        public static final String URL_GET_PLAN_ORDER ="planDeal";

        /** 微信消息 微信设备绑定关系 */
        public static final String URL_WX_SEND_MSG ="wx/sendMsg";
        public static final String URL_WX_TEMPLATE_SEND_MSG ="wx/sendTemplateMsg";

	/**
	 * other common constants
	 */
	public static final class Common {
	    private Common() {}

	    /** 登录 cookie 名称 */
	    /**
	     * 部分 REST 接口需要登录验证, 因为目前 Restlet 整合使用了 JAX-RS 的方式, 在熟悉掌握这种方式之前, 为了在
	     * Servlet 端调用 Rest 接口时能正常通过登录验证, 所以使用了这个 Cookie 且此 Cookie 亦只能在内部使用, 即从
	     * Servlet 端到 Rest 接口时. 目前它仅包含用户ID的值.
	     */
	    public static final String REST_FROM_SERVLET_COOKIE_NAME = "easy2go_rest";

	    /** 历史原因, 这个不使用了 **/
	    public static final String REST_SESSION_COOKIE_NAME = "easy2go_session"; // 与 api/v2 保持一致

	    /**
	     * 传递给客户端, 标识已登录的 Cookie 名称. 鉴于快速实现的目的, 简化而不使用严格的 RESTFUL 可用的基于 TOKEN/安全摘要
	     * 的认证方式, 所以使用了传统的 Cookie 机制. 这一点亦系没用 NodeJS 之前的使用, 并且在 Android 端已经良好应用.
	     * 此 Cookie 的值格式请参考下面: REST_SIGN_COOKIE_NAME_FORMAT
	     */
	    public static final String REST_SIGN_COOKIE_NAME = "easy2go_session.sig"; // 与 api/v2 保持一致 目前未实现

	    /** cookie 值的格式 */
	    public static final String REST_SESSION_COOKIE_FORMAT = "##"; //"id=##";
	    //  在本应用中, username 即为 phone, userid 即为 customerID
	    public static final String REST_SIGN_COOKIE_NAME_FORMAT = "#series#,#username#,#token#,#userid#";
	}

}


