package com.Manage.entity;

import java.sql.Timestamp;

import com.Manage.common.util.DateUtil;

/**
 * 短信记录
 * @author zwh
 *
 */
public class MSMRecord {

	private int id;
	private String phone;
	private String sn;
	private String templateId;
	private String templateParm;
	private String adminid;
	private String adminName;
	private int status;
	private Timestamp timestamp=DateUtil.getTimestamp();
	private String datetime;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getTemplateParm() {
		return templateParm;
	}
	public void setTemplateParm(String templateParm) {
		this.templateParm = templateParm;
	}
	public String getAdminid() {
		return adminid;
	}
	public void setAdminid(String adminid) {
		this.adminid = adminid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	
	
}
