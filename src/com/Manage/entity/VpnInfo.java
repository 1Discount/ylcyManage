package com.Manage.entity;

public class VpnInfo {
	
    private String vpnId;

    private String vpn;

    /**
     * VPN子账号
     */
    private String vpnc;
    
    /**
     * 总数
     */
    private Integer total;

    /**
     * VPN状态(0 不可用,1可使用 2表示使用中)
     */
    private Integer availableNum;

    /**
     * VPN套餐类型(流量,包月)
     */
    private String vpnPackageType;

    /**
     * VPN类型：0表示大流量，1表示小流量
     */
	private Integer vpnType;

	/**
	 * 编号
	 */
	private Integer code;
	
	/**
	 * 代号
	 */
    private String number;

    /**
     * 开始时间
     */
    private String begainTime;

    /**
     * 截止时间
     */
    private String endTime;

    /**
     * 包含流量
     */
    private Integer includeFlow;

    /**
     * 已使用流量
     */
    private Integer useFlow;

    /**
     * 上次分配时间
     */
    private String lastTimeAllocation;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人ID
     */
    private String creatorUserId;

    /**
     * 创建时间
     */
    private String creatorDate;

    /**
     * 创建人姓名
     */
    private String creatorUserName;

    /**
     * 修改人ID
     */
    private String modifyUserId;

    /**
     * 修改时间
     */
    private String modifyDate;

    /**
     * 状态
     */
    private Boolean sysStatus;

    /**
     * 最近绑定的SN列表
     */
    private String lastDeviceSN;
    
    //自定义字段
    private String status;
    private Integer childNum;

	public Integer getChildNum() {
		return childNum;
	}

	public void setChildNum(Integer childNum) {
		this.childNum = childNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVpnId() {
		return vpnId;
	}

	public void setVpnId(String vpnId) {
		this.vpnId = vpnId == null ? null : vpnId.trim();
	}

	public String getVpn() {
		return vpn;
	}

	public void setVpn(String vpn) {
		this.vpn = vpn;
	}

	public String getVpnc() {
		return vpnc;
	}

	public void setVpnc(String vpnc) {
		this.vpnc = vpnc;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getAvailableNum() {
		return availableNum;
	}

	public void setAvailableNum(Integer availableNum) {
		this.availableNum = availableNum;
	}

	public String getVpnPackageType() {
		return vpnPackageType;
	}

	public void setVpnPackageType(String vpnPackageType) {
		this.vpnPackageType = vpnPackageType == null ? null : vpnPackageType.trim();
	}
    
    public Integer getVpnType() {
		return vpnType;
	}

	public void setVpnType(Integer vpnType) {
		this.vpnType = vpnType;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getBegainTime() {
		return begainTime;
	}

	public void setBegainTime(String begainTime) {
		this.begainTime = begainTime == null ? null : begainTime.trim();
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime == null ? null : endTime.trim();
	}

	public Integer getIncludeFlow() {
		return includeFlow;
	}

	public void setIncludeFlow(Integer includeFlow) {
		this.includeFlow = includeFlow;
	}

	public Integer getUseFlow() {
		return useFlow;
	}

	public void setUseFlow(Integer useFlow) {
		this.useFlow = useFlow;
	}

	public String getLastTimeAllocation() {
		return lastTimeAllocation;
	}

	public void setLastTimeAllocation(String lastTimeAllocation) {
		this.lastTimeAllocation = lastTimeAllocation == null ? null : lastTimeAllocation.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getCreatorUserId() {
		return creatorUserId;
	}

	public void setCreatorUserId(String creatorUserId) {
		this.creatorUserId = creatorUserId == null ? null : creatorUserId.trim();
	}

	public String getCreatorDate() {
		return creatorDate;
	}

	public void setCreatorDate(String creatorDate) {
		this.creatorDate = creatorDate == null ? null : creatorDate.trim();
	}

	public String getCreatorUserName() {
		return creatorUserName;
	}

	public void setCreatorUserName(String creatorUserName) {
		this.creatorUserName = creatorUserName == null ? null : creatorUserName.trim();
	}

	public String getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId == null ? null : modifyUserId.trim();
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate == null ? null : modifyDate.trim();
	}

	public Boolean getSysStatus() {
		return sysStatus;
	}

	public void setSysStatus(Boolean sysStatus) {
		this.sysStatus = sysStatus;
	}

	public String getLastDeviceSN() {
		return lastDeviceSN;
	}

	public void setLastDeviceSN(String lastDeviceSN) {
		this.lastDeviceSN = lastDeviceSN == null ? null : lastDeviceSN.trim();
	}
}