package com.Manage.entity;


/**
 * ExchangeKey.java
 *
 * 兑换码生成规则：
 *     第一次CRC算出值不足10位前面补0得到10位数字字符串,然后后面加上时间戳（1448604125145)[使用字符串加]，
 *     再进行crc32计算得出不足10位后面补0，最终是10位的数字型的字符串
 *
 * @author tangming@easy2go.cn 2015-11-27
 *
 */
public class ExchangeKey {

	/**流水(自增长) **/
	Integer keyId;
	/**手机号 **/
	String phone;
	/**兑换码 **/
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
	Double amount;
	/**类型(基本，表示充入基本帐户/流量，表示充入流量帐户) **/
	String type;
	/** 发短信的次数 **/
	Integer sendSmsCount;

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

	/**兑换状态 是(已兑换),否(未兑换) 表中没字段使用join查询记录表**/
	public String useStatus;
	/**兑换时间**/
	public String useDateTime;

	/**表单批量输入手机号保持&传递 **/
	Integer ifBatch; // 单个手机号还是批量多个手机号
	String phoneBatch;

	// 批量处理时结果保存
	Boolean ifInsertOK;
	Integer ifSendSmsOK;
	Boolean ifNotExist;

	public Integer getKeyId() {
		return keyId;
	}
	public void setKeyId(Integer keyId) {
		this.keyId = keyId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getSendSmsCount() {
		return sendSmsCount;
	}
	public void setSendSmsCount(Integer sendSmsCount) {
		this.sendSmsCount = sendSmsCount;
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
	public String getPhoneBatch() {
		return phoneBatch;
	}
	public void setPhoneBatch(String phoneBatch) {
		this.phoneBatch = phoneBatch;
	}
	public Boolean getIfInsertOK() {
		return ifInsertOK;
	}
	public void setIfInsertOK(Boolean ifInsertOK) {
		this.ifInsertOK = ifInsertOK;
	}
	public Integer getIfSendSmsOK() {
		return ifSendSmsOK;
	}
	public void setIfSendSmsOK(Integer ifSendSmsOK) {
		this.ifSendSmsOK = ifSendSmsOK;
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
