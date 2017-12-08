package com.Manage.entity;

import java.util.List;

import com.sun.corba.se.spi.orb.StringPair;


/**
 * WxBindDevice.java
 * 2015-10-20
 *
 */
public class WxBindDevice {
	/** 绑定关系ID **/
	private String bindID;
	/** 公众号内唯一标识 **/
	private String openid;
	/** 用户唯一标识 **/
	private String unionid;
	/** 设备SN **/
	private String sn;
	/** 上次绑定时间 **/
	private String bindDate;
	/** 上次解绑时间 **/
	private String unbindDate;
	/** 绑定状态（绑定，解绑）针对业务, 可能增加”过期”等, 例如租借的设备归还后 **/
	private String bindStatus;
	/** 是否可推送/回复普通消息 -> 考虑到难以维护也不必要维护这个状态, 所以仅需要根据 userMsgDate 去设定此值再返回到前端即可 **/
	private String pushable;
	/** 推送消息类别（多种类消息会用到） **/
	private String pushType;
	/** 用户最后回复/响应时间（用户回复48小时内才可主动向用户推送消息, 按开发文档, 关注/点击菜单事件等都能重置这个48小时计时） **/
	private String userMsgDate;

	/** 最近推送模板消息的id */
	private String lastTemplateMsgId;
	/** 最近推送模板消息的时间 */
	private String lastTemplateMsgDate;
	/** 最近发送普通消息的时间 */
	private String lastMsgDate;

	/** 发送信息总数 待用 */
	private Integer allMsgCount;

	/**备注**/
	private String remark;
	/**创建人ID**/
	private String creatorUserID;
	/**创建时间**/
	private String creatorDate;
	/**创建人姓名**/
	private String creatorUserName;
	/**修改人ID**/
	private String modifyUserID;
	/**修改时间 **/
	private String modifyDate;
	/**系统状态**/
	private int sysStatus;

	/** 传递 wifi 密码以验证 */
	private String wifiPassword;

	// 不使用js跨域调用www.easy2go.cn REST要特别处理, 所以考虑用controller接口去调用. 同时,
	// 为了减少微信开发SDK类的引进, 添加下列参数字段. 这些字段名直接对应发送消息的参数名.
	/** 传递参数 发送给谁 openid */
	private String touser;
	/** 模板id */
	private String template_id;
	/** 模板消息url */
	private String url;
	/** 模板消息的参数列表, 注意这个为若干的同名字段 */
	private List<String> params;
	/** 普通消息的类型*/
	private String msgtype;
	/** 普通消息的内容 */
	private String content;


	/** 搜索条件 */
	private String beginTime;
	private String endTime;
	public String getBindID() {
		return bindID;
	}
	public void setBindID(String bindID) {
		this.bindID = bindID;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getBindDate() {
		return bindDate;
	}
	public void setBindDate(String bindDate) {
		this.bindDate = bindDate;
	}
	public String getUnbindDate() {
		return unbindDate;
	}
	public void setUnbindDate(String unbindDate) {
		this.unbindDate = unbindDate;
	}
	public String getBindStatus() {
		return bindStatus;
	}
	public void setBindStatus(String bindStatus) {
		this.bindStatus = bindStatus;
	}
	public String getPushable() {
		return pushable;
	}
	public void setPushable(String pushable) {
		this.pushable = pushable;
	}
	public String getPushType() {
		return pushType;
	}
	public void setPushType(String pushType) {
		this.pushType = pushType;
	}
	public String getUserMsgDate() {
		return userMsgDate;
	}
	public void setUserMsgDate(String userMsgDate) {
		this.userMsgDate = userMsgDate;
	}
	public String getLastTemplateMsgId() {
		return lastTemplateMsgId;
	}
	public void setLastTemplateMsgId(String lastTemplateMsgId) {
		this.lastTemplateMsgId = lastTemplateMsgId;
	}
	public String getLastTemplateMsgDate() {
		return lastTemplateMsgDate;
	}
	public void setLastTemplateMsgDate(String lastTemplateMsgDate) {
		this.lastTemplateMsgDate = lastTemplateMsgDate;
	}
	public String getLastMsgDate() {
		return lastMsgDate;
	}
	public void setLastMsgDate(String lastMsgDate) {
		this.lastMsgDate = lastMsgDate;
	}
	public Integer getAllMsgCount() {
		return allMsgCount;
	}
	public void setAllMsgCount(Integer allMsgCount) {
		this.allMsgCount = allMsgCount;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public List<String> getParams() {
		return params;
	}
	public void setParams(List<String> params) {
		this.params = params;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreatorUserID() {
		return creatorUserID;
	}
	public void setCreatorUserID(String creatorUserID) {
		this.creatorUserID = creatorUserID;
	}
	public String getCreatorDate() {
		return creatorDate;
	}
	public void setCreatorDate(String creatorDate) {
		this.creatorDate = creatorDate;
	}
	public String getCreatorUserName() {
		return creatorUserName;
	}
	public void setCreatorUserName(String creatorUserName) {
		this.creatorUserName = creatorUserName;
	}
	public String getModifyUserID() {
		return modifyUserID;
	}
	public void setModifyUserID(String modifyUserID) {
		this.modifyUserID = modifyUserID;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	public int getSysStatus() {
		return sysStatus;
	}
	public void setSysStatus(int sysStatus) {
		this.sysStatus = sysStatus;
	}
	public String getWifiPassword() {
		return wifiPassword;
	}
	public void setWifiPassword(String wifiPassword) {
		this.wifiPassword = wifiPassword;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
