package com.Manage.entity;

/**
 * SIMInfo.java
 * 
 * @author tangming@easy2go.cn 2015-5-27
 * 与数据库无关，备卡查询时使用
 */
public class SIMInfoCountryTemp
{
    //国家编号
    public String countryCode;
    //国家名
    public String countryName;
    //不同状态的卡的数量
    public String cardStatusCountTemp;
    //卡总数
    public int SIMCount;
    //调试不可用
    //public int useing;
    //不可用
    public int notavailable;
    //可用
    public int available;
    //使用中
    public int useing;
    //停用
    public int disabledTwo;
    //下架
    public int offtheshelf;
    //库存
    public int stock;
    //订单sn的集合
    public String snArray;
    //订单总量
    public String orderCount;
    //查询时间
    public String searchTime;
    //服务器编号
    public String serverCode;
    
    public String getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    public String getCountryName() {
        return countryName;
    }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public String getCardStatusCountTemp() {
        return cardStatusCountTemp;
    }
    public void setCardStatusCountTemp(String cardStatusCountTemp) {
        this.cardStatusCountTemp = cardStatusCountTemp;
    }
    public String getSnArray() {
        return snArray;
    }
    public void setSnArray(String snArray) {
        this.snArray = snArray;
    }
    public String getOrderCount() {
        return orderCount;
    }
    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }
    public String getSearchTime() {
        return searchTime;
    }
    public void setSearchTime(String searchTime) {
        this.searchTime = searchTime;
    }
    public int getSIMCount() {
        return SIMCount;
    }
    public void setSIMCount(int sIMCount) {
        SIMCount = sIMCount;
    }
    public int getNotavailable() {
        return notavailable;
    }
    public void setNotavailable(int notavailable) {
        this.notavailable = notavailable;
    }
    public int getAvailable() {
        return available;
    }
    public void setAvailable(int available) {
        this.available = available;
    }
    public int getUseing() {
        return useing;
    }
    public void setUseing(int useing) {
        this.useing = useing;
    }
    public int getDisabledTwo() {
        return disabledTwo;
    }
    public void setDisabledTwo(int disabledTwo) {
        this.disabledTwo = disabledTwo;
    }
    public int getOfftheshelf() {
        return offtheshelf;
    }
    public void setOfftheshelf(int offtheshelf) {
        this.offtheshelf = offtheshelf;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public String getServerCode() {
        return serverCode;
    }
    public void setServerCode(String serverCode) {
        this.serverCode = serverCode;
    }
    
    
}
