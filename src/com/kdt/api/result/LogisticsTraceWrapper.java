package com.kdt.api.result;

import com.kdt.api.entity.LogisticsTrace;

public class LogisticsTraceWrapper extends BaseResult {
	/**
	 *
	 */
	private static final long serialVersionUID = 6899909441839121997L;

	LogisticsTrace response;

	public LogisticsTrace getResponse() {
		return response;
	}

	public void setResponse(LogisticsTrace response) {
		this.response = response;
	}

}
