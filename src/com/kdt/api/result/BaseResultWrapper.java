package com.kdt.api.result;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 现在可以使用这个类去实例化各种返回结果的wrapper了
 * 例子见: 测试物流标记签收 {@link com.kdt.api.KDTApi#main(String[])}
 * 和 {@link com.kdt.api.KDTApi#logisticsOnlineMarksign(java.util.HashMap)}
 * Jaskson解释一个泛型的例子见: http://codego.net/591508/
 *
 * BaseResultWrapper.java
 * @author tangming@easy2go.cn 2015-11-17
 *
 * @param <T>
 */
public class BaseResultWrapper<T> extends BaseResult {
	/**
	 *
	 */
	private static final long serialVersionUID = -6389721009734154015L;

	T response;

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}

	@JsonCreator
	public BaseResultWrapper(@JsonProperty("response") T response) {
		super();
		this.response = response;
	}
}
