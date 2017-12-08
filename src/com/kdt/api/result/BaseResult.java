package com.kdt.api.result;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;


/**
 * 有赞返回结果基类
 * 参考:
 * https://open.koudaitong.com/doc/api/structs
 * 有赞全部数据结构列表
 * https://open.koudaitong.com/doc
 * 有赞 API 文档
 * https://open.koudaitong.com/doc/api/errors
 * 全局错误返回码说明
 *
 * 此基类即只含有 全局错误返回码 信息
 *
 * 注意: 目前使用类注解 @JsonIgnoreProperties 忽略返回结果中不在POJO中存在的字段
 * 同时注意如果含内部类, 也应该添加 @JsonIgnoreProperties(ignoreUnknown = true)
 * TODO: 悲催..不能全部配置忽略未知字段?
 *
 * BaseResult.java
 * @author tangming@easy2go.cn 2015-11-11
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResult implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -445058533808280073L;

	ErrorResponse error_response;

	public ErrorResponse getError_response() {
		return error_response;
	}
	public void setError_response(ErrorResponse error_response) {
		this.error_response = error_response;
	}

	@JsonIgnore
	public boolean isSuccess() {
		return null == error_response;
	}
}
