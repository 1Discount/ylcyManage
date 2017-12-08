package com.Manage.entity.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class SearchDTO {
	/**
	 * 当前页
	 */
	private int curPage;
	/**
	 * 页大小
	 */
	private int pageSize;
	/**
	 * 排序字段名
	 */
	private String sortName = "";
	/**
	 * 排序方式
	 */
	private String sortOrder = "asc";
	
	private Object obj;//查询条件对象
	private Map<String, Object> filters = new HashMap<String, Object>();//查询条件
	private int total = 0; //总记录数
	private int startIndex;//起始索引(sql中用到)
	private int endIndex;//结束索引
	private String ifseach="0";
	public SearchDTO() {}
	
//	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//	Date date = new Date();
//	String times =format.format(date);
//  private String tablename="DeviceLogs_"+times;
//	public SearchDTO(String tablename,int curPage, int pageSize, String sortName, String sortOrder, Object obj) {
//		this.curPage = curPage;
//		this.pageSize = pageSize;
//		this.sortName = sortName;
//		this.sortOrder = sortOrder;
//		this.tablename = tablename;
//		this.obj = obj;
//	}
	public SearchDTO(int curPage, int pageSize, String sortName, String sortOrder, Object obj) {
		this.curPage = curPage;
		this.pageSize = pageSize;
		this.sortName = sortName;
		this.sortOrder = sortOrder;
		this.obj = obj;
		
		
 	}
	
	public SearchDTO(int curPage, int pageSize, String sortName, String sortOrder, Map<String, Object> filters) {
		this.curPage = curPage;
		this.pageSize = pageSize;
		this.sortName = sortName;
		this.sortOrder = sortOrder;
		this.filters = filters;
	}
	
	
	
	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	

	public String getIfseach() {
		return ifseach;
	}

	public void setIfseach(String ifseach) {
		this.ifseach = ifseach;
	}

	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public Map<String, Object> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public int getStartIndex() {
		return startIndex;
	}
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
		int startIndextemp;
		if (curPage < 1 || pageSize < 1){
			startIndextemp = 0;
		} else {
			startIndextemp = ((curPage - 1) * pageSize) < total? ((curPage - 1) * pageSize) : total;
		}
		this.startIndex = startIndextemp;
		this.endIndex=pageSize;
	}
	
	/**
	 * 根据currentPage和pageSize计算当前页第一条记录在总结果集中的索引位置.
	 * @return
	 */
	public void setStartIndex(int startIndex) {
		if (curPage < 1 || pageSize < 1){
			startIndex = 0;
		} else {
			startIndex = ((curPage - 1) * pageSize) < total? ((curPage - 1) * pageSize) : total;
		}
		this.startIndex = startIndex;
	}

	public Object getObj() {
		return obj;
	}
	
	public void setObj(Object obj) {
		this.obj = obj;
	}

//	public String getTablename() {
//		return tablename;
//	}
//
//	public void setTablename(String tablename) {
//		this.tablename = tablename;
//	}

}
