package com.kdt.api.entity;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * https://open.koudaitong.com/doc/api?method=kdt.shop.basic.get
 * kdt.shop.basic.get 获取店铺基本信息
 *
 * ShopBasic.java
 * @author tangming@easy2go.cn 2015-11-11
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopBasic implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -702240480170740864L;

	Integer sid;
	String name;
	String logo;

	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}

}
