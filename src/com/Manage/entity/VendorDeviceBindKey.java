package com.Manage.entity;


/**
 * VendorDeviceBindKey.java
 *
 * 绑定码生成规则：
 *     第一次CRC算出值不足10位前面补0得到10位数字字符串,然后后面加上时间戳（1448604125145)[使用字符串加]，
 *     再进行crc32计算得出不足10位后面补0，最终是10位的数字型的字符串
 * （注：目前绑定码与兑换码的生成相同，前者基于SN同后者手机号码，不会有交集，故可相同规则，互不影响）
 *
 * @author tangming@easy2go.cn 20160225
 *
 */
public class VendorDeviceBindKey {

	/**流水(自增长) **/
	Integer keyId;
	/**第三方的终端用户唯一识别码 这个值由第三方调用接口时作为参数传递过来，目的系同步第三方同终端客户间的登录状态，避免
	 * 终端客户与我司平台需要帐号凭证等
	 */
    String vendorUid;
    /**第三方的email 合作方传递过来，基于它新增本系统的客户信息记录 **/
    String email;
    /**第三方的可能参数值,待用 **/
    String vendorParam;
    /**第三方的ID,目前待用 **/
    String vendorId;
	/**设备SN **/
	String SN;
	/**绑定码 **/
	String key;
	/**key所对应的时间戳 **/
	String keyTimestamp;
	/**开始时间 **/
	String startDate;
	/**结束时间 **/
	String endDate;
	/**是否禁用(正常/禁用) **/
	String status;
	/**金额 **/

	/**备注**/
	public String remark;
	/**创建人ID**/
	public String creatorUserID;
	/**创建时间**/
	public String creatorDate;
	/**创建人姓名**/
	public String creatorUserName;
	/**修改人ID**/
	public String modifyUserID;
	/**修改时间 **/
	public String modifyDate;
	/**系统状态**/
	public int sysStatus;

	/**绑定状态 是(表示已绑定),否(表示未绑定) 表中没字段使用, 使用下面的 useDateTime 绑定时间去决定 **/
	public String useStatus;
	/**绑定时间**/
	public String useDateTime;
	/**绑定中的客户本系统ID **/
    String customerID;

	/**表单批量输入手机号保持&传递 **/
	Integer ifBatch; // 单个手机号还是批量多个手机号
	String batchList;

	// 批量处理时结果保存
	Boolean ifInsertOK;
//	Integer ifSendSmsOK;
	Boolean ifNotExist;
    public Integer getKeyId() {
        return keyId;
    }
    public void setKeyId(Integer keyId) {
        this.keyId = keyId;
    }
    public String getVendorUid() {
        return vendorUid;
    }
    public void setVendorUid(String vendorUid) {
        this.vendorUid = vendorUid;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getVendorParam() {
        return vendorParam;
    }
    public void setVendorParam(String vendorParam) {
        this.vendorParam = vendorParam;
    }
    public String getVendorId() {
        return vendorId;
    }
    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }
    public String getSN() {
        return SN;
    }
    public void setSN(String sN) {
        SN = sN;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getKeyTimestamp() {
        return keyTimestamp;
    }
    public void setKeyTimestamp(String keyTimestamp) {
        this.keyTimestamp = keyTimestamp;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getCustomerID() {
        return customerID;
    }
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
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
    public String getUseStatus() {
        return useStatus;
    }
    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
    }
    public String getUseDateTime() {
        return useDateTime;
    }
    public void setUseDateTime(String useDateTime) {
        this.useDateTime = useDateTime;
    }
    public Integer getIfBatch() {
        return ifBatch;
    }
    public void setIfBatch(Integer ifBatch) {
        this.ifBatch = ifBatch;
    }
    public String getBatchList() {
        return batchList;
    }
    public void setBatchList(String batchList) {
        this.batchList = batchList;
    }
    public Boolean getIfInsertOK() {
        return ifInsertOK;
    }
    public void setIfInsertOK(Boolean ifInsertOK) {
        this.ifInsertOK = ifInsertOK;
    }
    public Boolean getIfNotExist() {
        return ifNotExist;
    }
    public void setIfNotExist(Boolean ifNotExist) {
        this.ifNotExist = ifNotExist;
    }


//	@Override
//	public int hashCode() {
//		//String str = this.keyId.replaceAll("\\D+","");
//		return keyId; //Integer.parseInt(str);
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		boolean retVal = false;
//		if(obj != null && obj.getClass().equals(this.getClass())) {
//			ExchangeKey bean = (ExchangeKey) obj;
//			if(bean.getKeyId()==null && this.getKeyId()==null)
//            {
//                retVal = true;
//            }
//            else
//            {
//                if(bean.getKeyId()!=null && bean.getKeyId().equals(this.getKeyId()))
//                {
//                    retVal = true;
//                }
//            }
//		}
//		return retVal;
//	}

}
