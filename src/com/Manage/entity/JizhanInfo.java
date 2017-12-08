package com.Manage.entity;

import java.sql.Timestamp;

/**
 * 基站信息表，基站对应的坐标及详细地址名
 * @author Administrator
 *
 */
public class JizhanInfo {

	private int id;
	
	private String jizhan;
	
	private String lat;
	
	private String lon;
	
	private String address;
	
	private String resultJson;
	
	private Timestamp timestamp;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJizhan() {
		return jizhan;
	}

	public void setJizhan(String jizhan) {
		this.jizhan = jizhan;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getResultJson() {
		return resultJson;
	}

	public void setResultJson(String resultJson) {
		this.resultJson = resultJson;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
