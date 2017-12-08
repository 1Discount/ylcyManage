package com.kdt.api.entity;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * https://open.koudaitong.com/doc/api/struct?name=TradeBuyerMessage
 * 交易明细中买家留言的数据结构
 *
 * TradeBuyerMessage.java
 * @author tangming@easy2go.cn 2015-11-11
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeBuyerMessage implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1248023709570502999L;

	String title;
	String content;

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
