package com.kdt.api.result;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IsSuccessResponse implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 5872625293372826952L;

	private Boolean is_success;

	public Boolean getIs_success() {
		return is_success;
	}

	public void setIs_success(Boolean is_success) {
		this.is_success = is_success;
	}
}
