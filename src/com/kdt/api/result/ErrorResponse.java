package com.kdt.api.result;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * https://open.koudaitong.com/doc/api/errors
 * 全局错误返回码说明
 *
 * ErrorResponse.java
 * @author tangming@easy2go.cn 2015-11-11
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 6568505562681380581L;

	Integer code;
	String msg;
	ErrorParams params; // 注意, 很多时间这个为空, 所以若的确要检查, 请注意 NOP

	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public ErrorParams getParams() {
		return params;
	}
	public void setParams(ErrorParams params) {
		this.params = params;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ErrorParams {
		String app_id;
		String method;
		String timestamp;
		String format;
		String sign_method;
		String v;
		String sign;

		public String getApp_id() {
			return app_id;
		}
		public void setApp_id(String app_id) {
			this.app_id = app_id;
		}
		public String getMethod() {
			return method;
		}
		public void setMethod(String method) {
			this.method = method;
		}
		public String getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}
		public String getFormat() {
			return format;
		}
		public void setFormat(String format) {
			this.format = format;
		}
		public String getSign_method() {
			return sign_method;
		}
		public void setSign_method(String sign_method) {
			this.sign_method = sign_method;
		}
		public String getV() {
			return v;
		}
		public void setV(String v) {
			this.v = v;
		}
		public String getSign() {
			return sign;
		}
		public void setSign(String sign) {
			this.sign = sign;
		}

	}

}
