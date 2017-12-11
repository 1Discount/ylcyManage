<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- 格式化时间 -->
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>推送信息-推送信息详情-EASY2GO ADMIN</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet" href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
<meta name="csrf_token">
<%@include file="/WEB-INF/views/common/_ie8support.html" %>
<%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
<style type="text/css">
#searchTable2 tr {
	height: 36px;
}
</style>
</head>
<body>
<div id="messdele">${messagedevicedele}</div>
	<section id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
		<SECTION id="main-content">
			<SECTION class="wrapper">
				<DIV class="col-md-12">
					<DIV class="panel">
					<DIV class="panel-heading">推送信息详情</DIV>
						<table class="bsgrid">
							<tr>
								<td width="8%">标题</td><td>${p.title}</td>
								<td width="8%">平台类型</td><td>${p.deviceTypes}</td>
								<td width="8%">推送来源</td>
								<td>
									<c:if test="${p.source == 1}">后台</c:if>
									<c:if test="${p.source == 2}">APP</c:if>
								</td>
								<td width="8%">消息类型</td><td>
										<c:if test="${p.type == 1}">文本消息</c:if>
										<c:if test="${p.type == 2}">富文本消息</c:if>
								</td>
							</tr>
							<tr>
								<td>内容</td>
								<td colspan="7" style="text-align: left;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${p.content}</td>
							</tr>
							<tr>
								<td>备注</td>
								<td colspan="7" style="text-align: left;">${p.remark}</td>
							</tr>
						</table>
					</DIV>

					<DIV class="panel">
						<DIV class="panel-heading">推送信息状态</DIV>
						<DIV class="panel-body">
							<FORM class="form-inline" role="form" id="searchForm" method="get" action="" >
								<DIV class="form-group">
									<LABEL class="inline-label">机身码：</LABEL>
									<INPUT class="form-control" name="sn" id="sn" type="text" placeholder="机身码">
								</DIV>
								<DIV class="form-group">
									<LABEL class="inline-label">是否成功：</LABEL>
									<select class="form-control" name="pushStatus" id="pushStatus">
									     <option value="">--所有状态--</option>
									     <option value=1>成功</option>
									     <option value=0>失败</option>
                                    </select>
								</DIV>
								<DIV class="form-group">
									<LABEL class="inline-label">是否到达：</LABEL>
									<select class="form-control" name="pushArriveTime" id="pushArriveTime">
									     <option value="">--所有状态--</option>
									     <option value="1">已到达</option>
									     <option value="0">未到达</option>
                                    </select>
								</DIV>
								<DIV class="form-group">
									<LABEL class="inline-label">是否阅读：</LABEL>
									<select class="form-control" name="isRead" id="isRead">
									     <option value="">--所有状态--</option>
									     <option value="1">已阅读</option>
									     <option value="0">未读</option>
                                    </select>
								</DIV>
								
								<DIV class="form-group">
									<BUTTON class="btn btn-primary" id="devFind"
									onclick="gridObj.options.otherParames =$('#searchForm').serializeArray();gridObj.page(1);"
									type="button">搜索</BUTTON>
								</DIV>
							</FORM>
						</DIV>
					</DIV>
					
					<DIV class="panel">
						<DIV class="panel-body">
							<DIV class="table-responsive">
								<TABLE id="searchTable">
									<TR>
										<TH w_index="sn"  width="10%"><b>机身码</b></TH>
										<TH w_index="pushStartTime"  width="10%"><b>开始推送时间</b></TH>
										<TH w_index="pushArriveTime"  width="10%"><b>推送到达时间</b></TH>
										<TH w_render="render_pushStatus" width="10%"><b>是否成功</b></TH>
										<TH w_render="render_isRead"  width="10%"><b>是否阅读</b></TH>
										<TH w_index="readTime"  width="10%"><b>阅读时间</b></TH>
										<TH w_index="deviceType"  width="10%"><b>设备类型</b></TH>
										<TH w_render="render_revoke"  width="10%"><b>是否撤销</b></TH>
										<TH w_index="revokeTime"  width="10%"><b>撤销时间</b></TH>
									</TR>
								</TABLE>
							</DIV>
						</DIV>
					</DIV>
					
				</DIV>
			</SECTION>
		</SECTION>
	</section>
	<form id="testform"  method="get"></form>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
<script>
function messageload(){
	var mess = document.getElementById('messdele').innerHTML;
	if(mess!=""){
		mess=mess+"";
		if(mess.length>35){
			 easy2go.toast('error', mess);
	    	 document.getElementById('messdele').innerHTML="";
		}else{
	     easy2go.toast('info', mess);
    	 document.getElementById('messdele').innerHTML="";
		}
	 }
	}
window.onload=messageload;
    var gridObj;
    $(function(){
       gridObj = $.fn.bsgrid.init('searchTable',{
           url:'<%=basePath%>pushMessage/detailPage?pushMessageInfoId=${p.id}',
           // autoLoad: false,
           pageSizeSelect: true,
           pageSize: 10,
           pageSizeForGrid:[10,20,30,50,100],
           multiSort:true
       });
   });
   function render_pushStatus(record, rowIndex, colIndex, options){
	   return converStatus(record.pushStatus);
   }
   function render_isRead(record, rowIndex, colIndex, options){
	   return converStatus(record.isRead);
   }
   function render_revoke(record, rowIndex, colIndex, options){
	   return converStatus(record.revoke);
   }
   function converStatus(status){
	   if(status=="1"){
		   return "是";
	   }else{
		   return "否";   
	   }
   }
</script>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
<jsp:param name="matchType" value="exactly" />
</jsp:include>
</body>
</html>