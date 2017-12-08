package com.kdt.api.entity;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * https://open.koudaitong.com/doc/api/struct?name=TradeFetch
 * 到店自提详情
 *
 * TradeFetch.java
 * @author tangming@easy2go.cn 2015-11-11
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeFetch implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 2230238009283194904L;

	String fetcher_name;
	String fetcher_mobile;
	String fetch_time; // Date 2014-08-20 12:08:00
	String shop_name;
	String shop_mobile;
	String shop_state;
	String shop_city;
	String shop_district;
	String shop_address;

	public String getFetcher_name() {
		return fetcher_name;
	}
	public void setFetcher_name(String fetcher_name) {
		this.fetcher_name = fetcher_name;
	}
	public String getFetcher_mobile() {
		return fetcher_mobile;
	}
	public void setFetcher_mobile(String fetcher_mobile) {
		this.fetcher_mobile = fetcher_mobile;
	}
	public String getFetch_time() {
		return fetch_time;
	}
	public void setFetch_time(String fetch_time) {
		this.fetch_time = fetch_time;
	}
	public String getShop_name() {
		return shop_name;
	}
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	public String getShop_mobile() {
		return shop_mobile;
	}
	public void setShop_mobile(String shop_mobile) {
		this.shop_mobile = shop_mobile;
	}
	public String getShop_state() {
		return shop_state;
	}
	public void setShop_state(String shop_state) {
		this.shop_state = shop_state;
	}
	public String getShop_city() {
		return shop_city;
	}
	public void setShop_city(String shop_city) {
		this.shop_city = shop_city;
	}
	public String getShop_district() {
		return shop_district;
	}
	public void setShop_district(String shop_district) {
		this.shop_district = shop_district;
	}
	public String getShop_address() {
		return shop_address;
	}
	public void setShop_address(String shop_address) {
		this.shop_address = shop_address;
	}

}
