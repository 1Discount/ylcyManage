package com.Manage.entity.common;

/**
 * @desc: 用户封装分页的json
 */
public class QueryCondition {
	private Integer curPage; // 当前页
	private Integer pageSize;// 页大小
	private String sortName; // 排序字段
	private String sortOrder; // 排序方式
	
	public QueryCondition() {
		super();
	}
	/**
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 */
	public QueryCondition(Integer page, Integer rows, String sort, String order) {
		super();
		this.curPage = page;
		this.pageSize = rows;
		this.sortName = sort;
		this.sortOrder = order;
	}
	public Integer getCurPage() {
		return curPage;
	}
	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getSortName() {
		return sortName;
	}
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	
}
