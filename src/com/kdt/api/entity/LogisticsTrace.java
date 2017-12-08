package com.kdt.api.entity;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * https://open.koudaitong.com/doc/api?method=kdt.logistics.trace.search
 * kdt.logistics.trace.search 物流流转信息查询
 * 用户根据交易编号查询物流流转信息，如：2010-8-10 15:23:00 到达杭州集散地。
 *
 * LogisticsTrace.java
 * @author tangming@easy2go.cn 2015-11-17
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LogisticsTrace implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -8327417679064863433L;

	String out_sid;
	String company_name;
	String tid;
	String status;
	List<trace_list_item> trace_list;

	public String getOut_sid() {
		return out_sid;
	}

	public void setOut_sid(String out_sid) {
		this.out_sid = out_sid;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<trace_list_item> getTrace_list() {
		return trace_list;
	}

	public void setTrace_list(List<trace_list_item> trace_list) {
		this.trace_list = trace_list;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class trace_list_item {
		String status_time;
		String status_desc;
		public String getStatus_time() {
			return status_time;
		}
		public void setStatus_time(String status_time) {
			this.status_time = status_time;
		}
		public String getStatus_desc() {
			return status_desc;
		}
		public void setStatus_desc(String status_desc) {
			this.status_desc = status_desc;
		}

	}
}
