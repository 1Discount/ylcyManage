package com.Manage.common.constants;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.Manage.common.util.PropertiesLoader;
import com.Manage.common.util.StringUtils;

/**
 * @desc 系统常量
 */
public class Constants {
    /**
     * 属性文件加载对象
     */
    private static PropertiesLoader loader = new PropertiesLoader(
	    "g.properties");

    /**
     * 参数为空!
     */
    public static final String common_errors_1000 = "1000";
    /**
     * 新增失败，请稍后再试或与管理员联系!
     */
    public static final String common_errors_1001 = "1001";
    /**
     * 删除失败，请稍后再试或与管理员联系!
     */
    public static final String common_errors_1002 = "1002";
    /**
     * 修改失败，请稍后再试或与管理员联系!
     */
    public static final String common_errors_1003 = "1003";
    /**
     * 查询失败，请稍后再试或与管理员联系!
     */
    public static final String common_errors_1004 = "1004";
    /**
     * 数据库中已存在此记录!
     */
    public static final String common_errors_1005 = "1005";
    /**
     * 数据库中不存在此记录!
     */
    public static final String common_errors_1006 = "1006";
    /**
     * 网络问题，请稍后再试或与管理员联系!
     */
    public static final String common_errors_1007 = "1007";
    /**
     * 统一返回信息: 通常的成功
     */
    public static final String common_errors_general_ok_100 = "100";
    /**
     * 统一返回信息: 通常的失败, 有别于前面的 common_errors
     */
    public static final String common_errors_general_failed_101 = "101";
    /**
     * 返回结果 success：正确 fail：失败
     */
    public static final String RETURN_RESULT = "return_result";
    /**
     * 返回结果的message
     */
    public static final String RETURN_MESSAGE = "return_message";

    // 发邮件相关常量
    public static final String JAVAMAIL_HOSTNAME = "smtp.exmail.qq.com";
    public static final boolean JAVAMAIL_IFSSL = true;
    public static final int JAVAMAIL_SMTPPORT = 456;
    public static final String JAVAMAIL_SENDNAME = "easy2go@easy2go.cn";
    public static final String JAVAMAIL_SENDPASSWORD_U = "\u0061\u0031\u0032\u0033\u0034\u0035\u0036\u0041";

    public static final String BACKGROUND_WARN_FILE_URL = "/log/Warn.txt";

    public static String JAVAMAIL_SENDPASSWORD() {
	return JAVAMAIL_SENDPASSWORD_U.substring(0,
		JAVAMAIL_SENDPASSWORD_U.length());
    }

    /**
     * VIP 优惠卡控制符
     */
    public static final String CONTROLOPERATOR = "QWERASDFG";

    /**
     * 验证码有效天数
     */
    public static final int INVITATION_VALIDDAYS = 3;

    /**
     * 加密密钥
     */

    public static final String DES_KEY = "YlcYbusQ"; // 运营后台管理员表密码加密密钥
    public static final String DES_KEYWEB = "YlcYWaPp";// 官网,app用户密码加密密钥
    public static final String DEFAULT_CUSTOMER_PWD = "88888888";// 客户默认密码

    /*
     * 数据字典相关类别名常量
     */
    /** 流量订单状态 **/
    public static final String DICT_FLOWORDER_STATUS = "流量订单状态";
    /** 客户来源 **/
    public static final String DICT_CUSTOMER_SOURCE = "客户来源";
    /** ahming 现在可能可简化处理: 因为客户来源和订单来源的值目前相同 **/
    public static final String DICT_ORDERINFO_ORDERSOURCE = "客户来源";
    /** 订单状态 ordersInfo表 orderStatus **/
    public static final String DICT_ORDERINFO_ORDERSTATUS = "订单状态";

    public static final String DICT_ROAMINGSIMINFO_CARD_STATUS = "漫游卡使用状态"; // 与本地卡不同
									    // 20160116
    public static final String DICT_SIMINFO_CARD_STATUS = "SIM卡使用状态";
    public static final String DICT_SIMINFO_SIM_TYPE = "SIM卡类型";
    public static final String DICT_SIMINFO_SIM_BILL_METHOD = "SIM卡结算方式";
    public static final String DICT_COUNTRYINFO_CONTINENT = "大洲";
    public static final String DICT_FLOWPLANINFO_PLAN_TYPE = "流量套餐类型";
    public static final String DICT_SIMSERVER_STATUS = "SIM服务器状态";

    public static final String DICT_DISTRIBUTOR_TYPE = "渠道商类型";
    public static final String DICT_DISTRIBUTOR_PAYMENT_METHOD = "渠道商结算方式";

    public static final String DICT_DEVICE_COLOR = "设备颜色";
    public static final String DICT_DEPOSIT_RECORD_STATUS = "退押金申请状态";

    public static final String DICT_AFTER_SALE = "售后问题类型";

    public static final String SUBMITORDERS_TYPE = "工单问题类型";

    public static final String SIMINFO_HANDLE_TYPE = "SIM卡处理类型";

    /* SOCKET返回码 */
    public static final String SOCKET_RESULT_CODE_SUCCESS = "00"; // 操作成功
    public static final String SOCKET_RESULT_CODE_PARERROR = "01"; // 参数校验失败
    public static final String SOCKET_RESULT_CODE_RESNULL = "02"; // 查询结果为空
    public static final String SOCKET_RESULT_CODE_FAIL = "03"; // 操作失败
    public static final String SOCKET_RESULT_CODE_DATACONNFAIL = "04"; // 数据库连接失败

    /** 默认限速阀值 M **/
    public static final int LIMITVALUE = 200;
    /** 默认限速大小 kb **/
    public static final int LIMITSPEED = 24;

    /** 设备SN前缀 */
    public static final String DICT_DEVICE_SN = "17215021000"; // DICT_DEVICE_SN_PREFIX_11
    public static final String DICT_DEVICE_SN_PREFIX_10 = "1721502100";

    /** 增加了一个后台虚拟帐户 QuartzJOB 例如在有赞订单同步时使用它作为创建人id等 */
    public static final String QuartzJOB_USER_NAME = "QuartzJOB";
    public static final String QuartzJOB_USER_ID = "10000";

    /** 设备日志表开头 **/
    public static final String DEVTABLEROOT_STRING = "DeviceLogs_";

    /**
     * 定时任务开关常量
     */
    // 是否启动sim管理定时任务
    public static boolean TIMING_SIMMANAGEJOB = true;
    // 是否启动基站经纬度转换任务
    public static boolean TIMING_LATLONJOB = false;
    // 是否打开基站自动积累
    public static boolean TIMING_ADDJZTEMP = false;
    //是否打开设备使用详情添加定时任务
    public static boolean TIMING_DEVICEUSEDDETAILJOB = true;
    
    public static String TIMING_ADDJZTEMP_JW = "";
    
    

    // 计费模式
    public static final String FLOWDEALTYPE_1 = "1"; // 按时间不限流量
    public static final String FLOWDEALTYPE_2 = "2"; // 按时间限流量
    public static final String FLOWDEALTYPE_3 = "3"; // 按实际使用天数不连续
    public static final String FLOWDEALTYPE_4 = "4"; // 按开机开始连续计费

    // 工作流订单当前状态
    public static final String workFlowStatus_acceptOrder = "接单";
    public static final String workFlowStatus_creatorOrder = "下单";
    public static final String workFlowStatus_shipment = "发货";
    public static final String workFlowStatus_returnDevice = "归还";
    public static final String workFlowStatus_finish = "结束";

    // 当订单使用流旦小于1024000kb时，则不导出数据
    public static final int totalFlow = 102400;

    /** 获取累计流量接口 **/
    public static String GET_FLOWCOUNT_API = "http://test.easy2go.cn:10080/SocketServer/api/SocketServer/FlowOrderRes/getSNFile";

    // SN规则配置
    public static final String SN_FORMAT = "{'0':'172150210','1':'860172008'}";

    /**
     * 取配置文件
     * 
     * @param key
     * @return
     */
    public static String getConfig(String key) {
	return loader.getProperty(key);

    }

    /**
     * 设置配置文件
     * 
     * @param key
     * @param value
     */
    public static void setConfig(String key, String value) {
	loader.setValue(key, value);
    }

    // 下订单是否发送邮件开关
    public static final boolean XIAORDESENDRMAIL = false;

    /**
     * 将SN转化为完整的SN
     * 
     * @param sn
     * @return
     */
    public static String SNformat(String sn) {
	JSONObject object = JSONObject.fromObject(SN_FORMAT);
	if (StringUtils.isBlank(sn)) {
	    return null;
	}
	if (sn.length() == 15) {
	    return sn;
	} else if (sn.length() == 6) {
	    String fisrtString = sn.charAt(0) + "";
	    if (object.get(fisrtString) == null) {
		return "" + sn;
	    } else {
		return object.getString(fisrtString) + sn;
	    }
	} else {
	    return null;
	}

    }

    // sim卡余额、 剩余流量预警
    public static final double simInfo_MinCardBalanceWarning = 50.0; // 单位：元
    public static final int simInfo_MinPlanRemainDataWarning = 51200; // 单位： kb

    /** 极光消息推送 **/
    // Tugpou_Help
    public static final String JIGUANG_APPKEY = "143f68b2aeee9286e1751f9a";
    public static final String JIGUANG_MASTERSECRET = "2b79a8fc919199ca4633e469";
    // easy2gobootstrap_user
    /*
     * public static final String JIGUANG_APPKEY="d8d3446b98b1bb4842257008";
     * public static final String
     * JIGUANG_MASTERSECRET="4545b4393f2eabe45de7d17f";
     */
    // 发送到生产环境还是开发环境
    public static final boolean JIGUANG_APNS_PRODUCTION = true;
    // 消息保存在数据库中的类型
    /* public static final String JIGUANG_MSSAGE_TYPE="极光通知"; */
    // 推送消息类型 1:简单文本消息 2：富文本消息(html)
    public static final String PUSH_MSSAGE_TYPE1 = "1";
    public static final String PUSH_MSSAGE_TYPE2 = "2";
    // 推送消息来源 1:后台 2：途狗APP-4G
    public static final String PUSH_MSSAGE_SOURCE1 = "1";
    public static final String PUSH_MSSAGE_SOURCE2 = "2";

    // 获取下载地址
    public static final String FILEPATHHEAD = "http://120.24.221.252/upgradeFile/";
    // public static final String
    // FILEPATHHEAD="http://192.168.0.115/upgradeFile/";
}
