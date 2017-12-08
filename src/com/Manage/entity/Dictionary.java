package com.Manage.entity;

/**
 * 数据字典 bean
 * @author lipeng
 *
 */
public class Dictionary {
	
	private String dictID;//ID
	private String description;//字典类别
	private String label;//类型下的具体属性
	private String value;//属性值
	private String parentId;//自身引用上下级属性ID
	private String sort;//序号（排序用）
	private String remark;//备注
	private String creatorUserID;//创建人ID
	private String creatorDate;//创建时间
	private String modifyUserID;//修改人ID
	private String modifyDate;//修改时间
	private int sysStatus;//系统状态
	

	public Dictionary() {
		super();
	}

	public Dictionary(String dictID, String value, String label,
			String description, String sort, String remark, String modifyUserID,String modifyDate) {
		super();
		this.dictID = dictID;
		this.description = description;
		this.label = label;
		this.value = value;
		this.sort = sort;
		this.remark = remark;
		this.modifyUserID = modifyUserID;
		this.modifyDate = modifyDate;
	}
	

	public Dictionary(String dictID, String value, String label,
			String description, String sort, String remark, String creatorUserID,int sysStatus,String creatorDate) {
		super();
		this.dictID = dictID;
		this.description = description;
		this.label = label;
		this.value = value;
		this.sort = sort;
		this.remark = remark;
		this.creatorUserID = creatorUserID;
		this.sysStatus = sysStatus;
		this.creatorDate = creatorDate;

	}
	public String getDictID() {
		return dictID;
	}
	public void setDictID(String dictID) {
		this.dictID = dictID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCreatorDate() {
		return creatorDate;
	}

	public void setCreatorDate(String creatorDate) {
		this.creatorDate = creatorDate;
	}

	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
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
	public String getCreatetorDate() {
		return creatorDate;
	}
	public void setCreatetorDate(String createtorDate) {
		this.creatorDate = createtorDate;
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
	@Override
	public String toString() {
		return "dictionary [dictID=" + dictID + ", description=" + description
				+ ", label=" + label + ", value=" + value + ", parentId="
				+ parentId + ", sort=" + sort + ", remark=" + remark
				+ ", creatorUserID=" + creatorUserID + ", createtorDate="
				+ creatorDate + ", modifyUserID=" + modifyUserID
				+ ", modifyDate=" + modifyDate + ", sysStatus=" + sysStatus
				+ "]";
	}
	
	
	

}
