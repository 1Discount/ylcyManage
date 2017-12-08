package com.Manage.entity;

public class MockCountryInfo {
	public String countryName;
	public int countryCode;
	public double flowPrice;
	public String continent;
	
	/**
	 * 正式版本时, CountrInfo 表可能增加一个字段, 通常值为空, 但可以作用一个有用的对象属性在后端与jsp间传递
	 */
	public boolean selected;
	
	public MockCountryInfo(String countryName, int countryCode,
			double flowPrice, String continent) {
		super();
		this.countryName = countryName;
		this.countryCode = countryCode;
		this.flowPrice = flowPrice;
		this.continent = continent;
	}
	
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public int getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(int countryCode) {
		this.countryCode = countryCode;
	}
	public double getFlowPrice() {
		return flowPrice;
	}
	public void setFlowPrice(double flowPrice) {
		this.flowPrice = flowPrice;
	}
	public String getContinent() {
		return continent;
	}
	public void setContinent(String continent) {
		this.continent = continent;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}