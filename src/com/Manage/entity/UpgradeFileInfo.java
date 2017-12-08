package com.Manage.entity;

/**
 * 接单表
 * @author 沈超
 *
 */
public class UpgradeFileInfo {
    String id;	//id
    String fileName;	//文件名
    String fileType;	//文件类型
    int fileSize;	//文件大小
    String realPath;	//真实保存路径
    String version;	//版本
    String remark;	//备注
    int sysStatus;	//系统状态
    String creatorUserID;
    String creatorDate;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFileType() {
        return fileType;
    }
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    public int getFileSize() {
        return fileSize;
    }
    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }
    public String getRealPath() {
        return realPath;
    }
    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public int getSysStatus() {
        return sysStatus;
    }
    public void setSysStatus(int sysStatus) {
        this.sysStatus = sysStatus;
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
    
}
