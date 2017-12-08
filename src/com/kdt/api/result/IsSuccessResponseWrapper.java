package com.kdt.api.result;

/**
 * 使用泛型 BaseResultWrapper 就不需要这个了!
 *
 * IsSuccessResponseWrapper.java
 * @author tangming@easy2go.cn 2015-11-17
 *
 */
public class IsSuccessResponseWrapper extends BaseResult {
	/**
	 *
	 */
	private static final long serialVersionUID = -4735872201865528456L;

	IsSuccessResponse response;

	public IsSuccessResponse getResponse() {
		return response;
	}

	public void setResponse(IsSuccessResponse response) {
		this.response = response;
	}

}
