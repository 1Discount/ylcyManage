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
<title>全部客户-设备日志-流量运营中心</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet"
	href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet"
	href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
<meta name="csrf_token">
<%@include file="/WEB-INF/views/common/_ie8support.html"%>
<%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
<style type="text/css">
#searchTable tr {
	height: 40px;
}
</style>
</head>
<body>

	<section id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />

		<SECTION id="main-content">
			<section class="wrapper">
				<div class="row">
				<div class="col-md-12">
						<div class="panel">
							<div class="panel-heading">根据机身码搜索设备日志</div>
							<div class="panel-body">
							<FORM class="form-inline" id="searchForm"  method="get" action="#">
         <DIV class="form-group">
             <LABEL class="inline-label">设备机身码：</LABEL>
             <INPUT class="form-control" name="sn" id="sn" type="text" placeholder="sn">
         </DIV>
        <!--  <DIV class="form-group">
             <LABEL class="inline-label">在线日期：</LABEL>
             <INPUT id="order_creatorDateend" class="form-control form_datetime" name="lastTime" type="text" data-date-format="YYYY-MM-DD">
         </DIV> -->
         <DIV class="form-group">
             <BUTTON class="btn btn-primary" type="button" onclick="gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.refreshPage();gridObj1.options.otherParames = $('#searchForm').serializeArray();gridObj1.refreshPage();">搜索</BUTTON>
         </DIV>
   </FORM>
						</div>
					</div>
					<div class="col-md-6">
						<div class="panel">
							<div class="panel-heading">设备种子卡日志:${SN}</div>
							<div class="panel-body">
								<TABLE id="searchTable">
  <TR>
    <TH w_index="SN" width="10%;"><b>机身码</b></TH>
    <th w_index="NO" width="5%;"><b>编号</b></th>
    <th w_index="logSize" width="5%;"><b>大小KB</b></th>
    <th w_index="creatorDate" width="10%;"><b>获取时间</b></th>
    <th w_render="op1" width="20%;"><b>操作</b></th>
  </TR>
</TABLE>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="panel">
							<div class="panel-heading">设备本地卡日志:${SN}</div>
							<div class="panel-body">
								<div class="table-responsive">
									<TABLE id="searchTable1">
  <TR>
    <TH w_index="SN" width="10%;"><b>机身码</b></TH>
    <th w_index="NO" width="5%;"><b>编号</b></th>
    <th w_index="logSize" width="5%;"><b>大小KB</b></th>
    <th w_index="creatorDate" width="10%;"><b>获取时间</b></th>
    <th w_render="op2" width="20%;"><b>操作</b></th>
  </TR>
</TABLE>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
		</SECTION>
	</section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script type="text/javascript">
    var gridObj;
    var gridObj1;
    $(function(){
       gridObj = $.fn.bsgrid.init('searchTable',{
           url:'<%=basePath%>remote/getlogs?type=1',
           pageAll:true
       });
       
       gridObj1 = $.fn.bsgrid.init('searchTable1',{
           url:'<%=basePath%>remote/getlogs?type=2',
           pageAll:true
       });
   });
    function op1(record, rowIndex, colIndex, options){
    	var SN = record.SN.substring(11,15);
    	var NO  = record.NO;
    	var time = record.creatorDate;
    	var type = record.type;
    	if(type=="1"){
    		type="漫游";
    	}else{
    		type="本地";
    	}
    	return "<button onclick='logsview1("+rowIndex+")' class='btn btn-primary'> 查看 </button>&nbsp;&nbsp;<a href='<%=basePath %>remote/savelog?fileName="+SN+"_"+NO+"_"+type+"_"+time+".log&id="+record.ID+"'><button class='btn btn-primary'> 保存 </button></a>&nbsp;&nbsp;<button onclick='del1("+rowIndex+")' type='button' class='btn btn-primary'>删除</button>";
		}/*onclick='savelog1("+rowIndex+")' class='btn btn-primary'  */
    function op2(record, rowIndex, colIndex, options){
    	var SN = record.SN.substring(11,15);
    	var NO  = record.NO;
    	var time = record.creatorDate;
    	var type = record.type;
    	if(type=="1"){
    		type="漫游";
    	}else{
    		type="本地";
    	}
    	return "<button onclick='logsview2("+rowIndex+")' class='btn btn-primary'> 查看 </button>&nbsp;&nbsp;<a href='<%=basePath %>remote/savelog?fileName="+SN+"_"+NO+"_"+type+"_"+time+".log&id="+record.ID+"'><button class='btn btn-primary'> 保存 </button></a>&nbsp;&nbsp;<button onclick='del2("+rowIndex+")' type='button' class='btn btn-primary'>删除</button>";
		}
    
    function logsview2(index){
    var record= gridObj1.getRecord(index);
  	  window.location="<%=basePath %>remote/logsview?ID="+record.ID;
    }
    function logsview1(index){
        var record= gridObj.getRecord(index);
      	  window.location="<%=basePath %>remote/logsview?ID="+record.ID;
        }
    function savelog1(index){
    	 var record= gridObj.getRecord(index);
    	 var record= gridObj1.getRecord(index);
         var SN = record.SN;
         var NO = record.NO;
         var logSize = record.logSize;
         var creatorDate = record.creatorDate;
    	 $.ajax({
    		 type:"POST",
    		 url:"<%=basePath %>remote/savelog?SN="+SN+"&NO="+NO+"&logSize="+logSize+"&creatorDate="+creatorDate+"",
    		 dataType:"html",
    		 success:function(data){
    			 if(data=='1'){
    				 easy2go.toast('info', "保存成功");
    				 gridObj.refreshPage();
    			 }
    		 }
    		 
    	 });
    }
    function savelog2(index){
   	 var record= gridObj1.getRecord(index);
     var SN = record.SN;
     var NO = record.NO;
     var logSize = record.logSize;
     var creatorDate = record.creatorDate;
   	 $.ajax({
		 type:"POST",
		 url:"<%=basePath %>remote/savelog?SN="+SN+"&NO="+NO+"&logSize="+logSize+"&creatorDate="+creatorDate+"",
		 dataType:"html",
		 success:function(data){
			 if(data=='1'){
				 easy2go.toast('info', "保存成功");
				 gridObj.refreshPage();
			 }else if(date=='0'){
				 easy2go.toast('info', "保存成功");
				 gridObj.refreshPage();
			 }
		 }
		 
	 });
   }
    function del1(index){
    	 var record= gridObj.getRecord(index);
    	 $.ajax({
    		 type:"POST",
    		 url:"<%=basePath %>remote/delinfo?id="+record.ID,
    		 dataType:"html",
    		 success:function(data){
    			 if(data=='1'){
    				 easy2go.toast('info', "删除成功");
    				 gridObj.refreshPage();
    			 }else if(data=='0'){
    				 easy2go.toast('info', "删除失败");
    			 }else if(data=='-1'){
    				 easy2go.toast('info', "参数为空");
    			 }
    		 }
    		 
    	 });
    }
    function del2(index){
   	 var record= gridObj1.getRecord(index);
   	 $.ajax({
   		 type:"POST",
   		 url:"<%=basePath %>remote/delinfo?id="+record.ID,
   		 dataType:"html",
   		 success:function(data){
   			 if(data=='1'){
   				 easy2go.toast('info', "删除成功");
   				 gridObj1.refreshPage();
   			 }else if(data=='0'){
   				 easy2go.toast('info', "删除失败");
   			 }else if(data=='-1'){
   				 easy2go.toast('info', "参数为空");
   			 }
   		 }
   		 
   	 });
   }
	</script>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>