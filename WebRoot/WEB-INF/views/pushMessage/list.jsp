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
<title>推送信息-信息管理-EASY2GO ADMIN</title>
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
						<DIV class="panel-body">
							<FORM class="form-inline" role="form" id="searchForm" method="get" action="" >
								
								
								<DIV class="form-group">
									<LABEL class="inline-label">序列号：</LABEL>
									<INPUT class="form-control" name="sn" id="sn" type="text" placeholder="序列号">
								</DIV>
								<DIV class="form-group">
									<LABEL class="inline-label">標題：</LABEL>
									<INPUT class="form-control" name="title" id="title" type="text" placeholder="標題">
								</DIV>
								<DIV class="form-group">
									<LABEL class="inline-label">內容：</LABEL>
									<INPUT class="form-control" name="content" id="content" type="text" placeholder="內容">
								</DIV>
							<DIV class="form-group">
									<LABEL class="inline-label">推送來源：</LABEL>
									<select class="form-control" name="source" id="source">
									     <option value="">--所有状态--</option>
									     <option value="1">後台</option>
									     <option value="2">APP</option>
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
						<DIV class="panel-heading">所有推送信息</DIV>
						<DIV class="panel-body">
							<DIV class="table-responsive">
								<TABLE id="searchTable">
									<TR>
										<TH w_index="sn"  width="10%"><b>序列号</b></TH>
										<TH w_index="title"  width="10%"><b>标题</b></TH>
										<TH w_index="content"  width="10%"><b>内容</b></TH>
										<TH w_index="deviceTypes" width="10%"><b>设备类型</b></TH>
										<TH w_index="pushMessagecount"  width="10%"><b>推送数量</b></TH>
										<TH w_index="arriveCount"  width="10%"><b>到达数量</b></TH>
										<TH w_index="readCount"  width="10%"><b>阅读量</b></TH>
										<TH w_render="render_source"  width="10%"><b>推送来源</b></TH>
										<TH w_render="renderSysStatus"  width="10%"><b>状态</b></TH>
										<th w_index="creatorName" width="10%"><b>创建人</b></th>
										<th w_index="creatorDate" width="10%"><b>创建时间</b></th>
										<TH w_render="operate" width="10%"><b>操作</b></TH>
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
           url:'<%=basePath%>pushMessage/page',
           // autoLoad: false,
           pageSizeSelect: true,
           pageSize: 10,
           pageSizeForGrid:[10,20,30,50,100],
           multiSort:true
       });
   });
   function renderSysStatus(record, rowIndex, colIndex, options){
	   if(record.pushStatus=='1'){
		   return "推送中"; 
	   }else if(record.pushStatus=='2'){
		   return "已到达:"+record.arriveCount+"/"+record.pushMessagecount; 
	   }else if(record.pushStatus=='3'){
		   return "已读:"+record.readCount+"/"+record.pushMessagecount; 
	   }else if(record.pushStatus=='0'){
		   return "推送失败";
	   }
   }
   function render_source(record, rowIndex, colIndex, options){
	   if(record.source=='1'){
		   return "后台"; 
	   }else if(record.source=='2'){
		   return "APP"; 
	   }else{
		   return record.source; 
	   }
   }
   function operate(record, rowIndex, colIndex, options){
	   var str='<A class="btn btn-primary btn-xs" href="<%=basePath%>pushMessage/detail?id='+record.id+'"> <SPAN class=" glyphicon glyphicon-circle-arrow-left">详情</SPAN></A>&nbsp;&nbsp;&nbsp;&nbsp;';
       
		/* if(record.pushStatus=="0"){
       	str+='<A class="btn btn-warning btn-xs" onclick="rebackDevice('+rowIndex+')"  >'+
          		 '<SPAN class=" glyphicon glyphicon-circle-arrow-left">重推</SPAN></A>&nbsp;&nbsp;&nbsp;&nbsp;';
       } */
       return str;
   }
</script>

<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
<jsp:param name="matchType" value="exactly" />
</jsp:include>
</body>
</html>