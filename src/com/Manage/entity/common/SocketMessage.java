package com.Manage.entity.common;
/** * @author  wangbo: * @date 创建时间：2015-6-17 上午9:54:31 * @version 1.0 * @parameter  * @since  * @return  */
public class SocketMessage {
	
	//定义设备发过来的数据包解析为对象
	/**
	 * 类型
	 */
	public String type;
	/**
	 * 设备SN
	 */
	public String sn;
	/**
	 * 日志片段编号
	 */
	public String num;
	/**
	 * 日志内容
	 */
	public String content;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
