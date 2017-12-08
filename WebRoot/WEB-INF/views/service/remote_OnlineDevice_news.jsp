<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <!-- 格式化时间 -->
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int Spagesize=20;
if(request.getSession().getAttribute("nowpagesize")==null){

}else{
	Spagesize=Integer.parseInt(request.getSession().getAttribute("nowpagesize").toString());
}
%>
<!DOCTYPE html>
<html>
  <head>
    <title>全部客户-设备日志-EASY2GO ADMIN</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="<%=basePath %>static/css/app.min.css?20150209">
    <link rel="stylesheet" href="<%=basePath %>static/css/grid/bsgrid.all.min.css">
    <meta name="csrf_token">
    <%@include file="/WEB-INF/views/common/_ie8support.html" %>
    <%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
    <style type="text/css">
        #searchTable tr{height:40px;}
    </style>
  </head>
 <body>

    <section id="container">
      <jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
      <jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />

 <SECTION id="main-content">
<SECTION class="wrapper">
<DIV class="col-md-12">
<DIV class="panel">
<!-- <DIV class="panel-heading">当前时间设备日志</DIV> -->
<DIV class="panel-body" style="margin:0px;padding:0px;">
 <SECTION class="wrapper" style="margin-top:20px;">
<DIV class="col-md-12">
<DIV class="panel">
<DIV class="panel-body" style="padding-top:5px;padding-bottom:5px;">
   <FORM class="form-inline" id="searchForm"  method="get" action="#">
   <input type="hidden" value="" id="pagenum"/><input type="hidden" id="pagesize" value="<%=Spagesize %>" />

		<input type="hidden" value="20" id="pageSize"/>
         <DIV class="form-group">
             <LABEL class="inline-label">设备序列号：</LABEL>
             <INPUT class="form-control" name="SN" id="SN" type="text" placeholder="sn">
         </DIV>
         <div class="form-group">
          <label class="inline-label">国&nbsp;家：</label>
          <select name="mcc" style="width: 130px;"
          class="form-control">
            <option value="">全部国家</option>
            <c:forEach items="${Countries}" var="country" varStatus="status">
                <option value="${country.countryCode}">${country.countryName}</option>
            </c:forEach>
          </select>
        </div>
        <!--  <DIV class="form-group">
             <LABEL class="inline-label">在线日期：</LABEL>
             <INPUT id="order_creatorDateend" class="form-control form_datetime" name="lastTime" type="text" data-date-format="YYYY-MM-DD">
         </DIV> -->
         <DIV class="form-group">
             <BUTTON class="btn btn-primary" type="button" onclick="gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.page(1);">搜索</BUTTON>
         </DIV>
   </FORM>
</DIV>
</DIV>



<DIV class="panel">
<DIV class="panel-heading">当前时间设备日志（当期测试使用历史记录）</DIV>
<DIV class="panel-body">
<DIV class="table-responsive">



     <form class='form-inline' action='<%=basePath %>remote2/getDevLogs?ps="+ps+"'>"+
    	sn:<input type='text' name='sn' value='' />
    	<select class='form-control' name='bm' style='width:80px;'>
    	<option value='1'>漫游</option><option value='0'>本地</option></select>
    	<input class='form-control' name='num' placeholder='num' style='width:80px;' />
    	<button type='button' onclick='os(this)' class='btn btn-primary'> 发送(命令下发成功，未收到日志) </button></form>

<br/>
 	<form class='form-inline' action='<%=basePath %>remote2/limitspeed?ps="+ps+"'>
    sn:<input type='text' name='sn' value='' />
    <input class='form-control' name='limitValve' type='Number'placeholder='阀值' style='width:80px;' />
    <input class='form-control' name='limitSpeed' type='Number'placeholder='限速值'  style='width:100px;' />
    <button type='button' onclick='os(this)'  class='btn btn-primary'> 发送 (命令下发成功，未修改限速)</button>
    </form>
<br/>
      <button onclick='logsview("860172008141745")' class='btn btn-primary'> 查看日志 </button>
      <button onclick='remoteShutdown("860172008141745")' class='btn btn-primary'>远程关机（√）</button>
      <button onclick='rePlugSIM("860172008141745")' class='btn btn-primary'>重插卡（命令下发成功）</button>
      <button onclick='changeCard("860172008141745")' class='btn btn-primary'>换卡（命令下发成功，未修改限速值）</button>
<br/>
  	 <form class='form-inline' action='<%=basePath %>remote2/modificationAPN?ps="+ps+"'>
    	 <input type='text' name='sn' value='860172008141745' /><input class='form-control' name='apn' placeholder='APN' style='width:160px;' />
     <button type='button' onclick='os(this)' class='btn btn-primary'> 发送 </button></form>

<!-- 结束  e -->
<TABLE id="searchTable">


  <TR>
    <TH w_render="SN"><b>设备序列号</b></TH>
    <th w_render="getLogs"><b>获取日志</b></th>
    <th w_render="remoteSearch"><b>远程查询</b></th>

    <th w_render="APN"><b>APN设置</b></th>
    <th w_render="remoteSpeed"><b>远程限速</b></th>
    <th w_render="looklogs"><b>查看日志</b></th>
  </TR>
</TABLE>
   </DIV>
<DIV>
</DIV></DIV></DIV></DIV>
</SECTION>
<!-- ======================================================================================== -->
</DIV>

</DIV>
</DIV>
</SECTION>
</SECTION>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <script src="<%=basePath %>static/js/bootbox.min.js"></script>
    <script type="text/javascript">
    bootbox.setDefaults("locale","zh_CN");
    var ps='';
    window.setInterval(function(){
    	pull();
    },1000);
    var pathurl='<%=basePath %>service/center/nowlistlogs';
    if('${sn}'!=null &&'${sn}'!=''){
    	pathurl+='?SN=${sn}';
    }
    var gridObj;
    $(function(){
    	var pagesize=parseInt($("#pagesize").val());
       gridObj = $.fn.bsgrid.init('searchTable',{
           url:pathurl,
           pageSizeSelect: true,
           pageSize:pagesize,
           pageSizeForGrid:[10,20,30,50,100],
           isProcessLockScreen:false,
           multiSort:true,
           autoLoad:false,
           otherParames:$('#searchForm').serializeArray(),
           additionalAfterRenderGrid: function(parseSuccess, gridData, options) {
          	 if(parseSuccess){
          		 $("#pagenum").val(options.curPage);
        		 $("#pageSize").val($("#searchTable_pt_pageSize").val());
          	 }
           }
       });
       if($("#pagenum").val()!=""){
    	   gridObj.page($("#pagenum").val());
       }else{
    	   gridObj.page(1);
       }
   });

    /* if("${rcode}"!=null && "${rcode}"!=0){
    	if("${rcode}"=="0"){
    		easy2go.toast('warn', "命令下发失败");
    	}else if("${rcode}"=="1"){
    		easy2go.toast('info', "命令下发成功");
    	}
    } */
    // 获取日志
    function getLogs(record,rowIndex,colIndex,options){
    	if($("#SN").val().trim()!=''){
    		ps=$("#SN").val();
    	}
    	return "<form class='form-inline' action='<%=basePath %>remote2/getDevLogs?ps="+ps+"'>"+
    	"<input type='hidden' name='sn' value='"+record.SN+"' /><select class='form-control' name='bm' style='width:80px;'><option value='1'>漫游</option><option value='0'>本地</option></select>"+
    	"<input class='form-control' name='num' placeholder='num' style='width:80px;' />"+
    	"<button type='button' onclick='os(this)' class='btn btn-primary'> 发送 </button></form>";
    }
    // 远程查询
    function remoteSearch(record,rowIndex,colIndex,options){
      	return "<form class='form-inline' action='<%=basePath %>remote2/remoteQuery?ps="+ps+"'>"+
    	"<input type='hidden' name='sn' value='"+record.SN+"' /><input class='form-control' name='mes' placeholder='content' style='width:120px;' />"+
    	"<button type='button' onclick='os(this)' class='btn btn-primary'> 发送 </button></form>";
    }
    // 远程升级
    function remotesj(record,rowIndex,colIndex,options){
      	return "<form class='form-inline'  action='<%=basePath %>remote2/remoteUpgrade?ps="+ps+"'>"+
    	"<input type='hidden' name='sn' value='"+record.SN+"' /><select class='form-control' name='type' style='width:80px;'><option value='3'>漫游</option><option value='4'>本地</option><option value='5'>漫游apk</option><option value='6'>本地apk</option><option value='7'>MIP</option><option value='17'>本地Setting</option></select>"+
    	"<button type='button' onclick='os(this)' class='btn btn-primary'> 发送 </button></form>";
    }

    // APN
    function APN(record,rowIndex,colIndex,options){
      	return "<form class='form-inline' action='<%=basePath %>remote2/modificationAPN?ps="+ps+"'>"+
    	"<input type='hidden' name='sn' value='"+record.SN+"' /><input class='form-control' name='apn' placeholder='APN' style='width:160px;' />"+
    	"<button type='button' onclick='os(this)' class='btn btn-primary'> 发送 </button></form>";
    }
    // 远程限速
    function remoteSpeed(record,rowIndex,colIndex,options){
      	return "<form class='form-inline' action='<%=basePath %>remote2/limitspeed?ps="+ps+"'>"+
    	"<input type='hidden' name='sn' value='"+record.SN+"' /><input class='form-control' name='limitValve' type='Number'placeholder='阀值' style='width:80px;' />MB"+
    	"<input class='form-control' name='limitSpeed' type='Number'placeholder='限速值'  style='width:100px;' />KB"+
    	"<button type='button' onclick='os(this)'  class='btn btn-primary'> 发送 </button></form>";
    }
    // 查看日志
    function looklogs(record,rowIndex,colIndex,options){
      	return "<button onclick='logsview("+record.SN+")' class='btn btn-primary'> 查看日志 </button>"+
      	"&nbsp;&nbsp;<button onclick='remoteShutdown("+record.SN+")' class='btn btn-primary'>远程关机</button>"+
      	"&nbsp;&nbsp;<button onclick='rePlugSIM("+record.SN+")' class='btn btn-primary'>重插卡</button>"+
      	"&nbsp;&nbsp;<button onclick='changeCard("+record.SN+")' class='btn btn-primary'>换卡</button>";

      	/* return  '<div role="group" class="btn-group"><button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="btn btn-primary dropdown-toggle">操作<span class="caret"></span></button>'+
      	'<ul class="dropdown-menu"><li><a href="/remote2/loglist/172150210001603">查看日志</a>'+
      	'<a onClick="" href="">重插卡</a><a onClick="" href="">远程关机</a></li></ul></div>'; */
    }
    function customerName(record, rowIndex, colIndex, options){
    	return '<A style="color:#1FB5AD;" href="<%=basePath %>customer/customerInfolist/customerInfoDetail?cusid='+record.customerID+'">'+record.customerName+'</A>';
       }
    function IMSI(record, rowIndex, colIndex, options){
    	return '<A style="color:#1FB5AD;" href="<%=basePath %>service/center/listimsi?imsi='+record.IMSI+'">'+record.IMSI+'</A>';
       }
    function SN(record, rowIndex, colIndex, options){

    	return '<A style="color:#1FB5AD;" href="<%=basePath %>service/center/devicelogs?sbid='+record.SN+'&sj=">'+record.SN+'</A>';
       }

    bootbox.setDefaults("locale","zh_CN");
	  var gridObj;
  $(function(){
     $("#order_creatorDateend").datetimepicker({
  		pickDate: true,                 //en/disables the date picker
  		pickTime: true,                 //en/disables the time picker
  		showToday: true,                 //shows the today indicator
  		language:'zh-CN',                  //sets language locale
//   		defaultDate: moment().add(0, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
  	});
 });

  function logsview(sn){
	  window.location="<%=basePath %>remote2/tologs?SN="+sn;
  }

  function pull(){
	  $.ajax({
		  type:"POST",
		  url:"<%=basePath %>remote2/mesPush",
		  dataType:"html",
		  success:function(data){
			  if(data=="00"){
				  return;
			  }
			  if(data=="10"){
				  easy2go.toast('info', "日志获取成功完成!");
			  }else if(data=="20"){
				  easy2go.toast('warn', "日志获取失败!");
			  }else if(data=="01"){
				  easy2go.toast('info', "远程升级成功完成!");
			  }else if(data=="02"){
				  easy2go.toast('warn', "远程升级失败!");
			  }
		  }
	  });
  }

  function os(t){

	 var sn= urltovalue(0,$(t).parent().serialize());
	 var sn4=sn.substring(sn.length-4,sn.length);
	  $.ajax({
		  type:"POST",
		  url:$(t).parent().attr("action"),
		  dataType:"html",
		  data:$(t).parent().serialize(),
		  success:function(data){
			  if(data=="1"){
				  easy2go.toast('info', sn4+" 命令下发成功");
			  }else if(data=="0"){
				  easy2go.toast('warn', sn4+" 命令下发失败");
			  }else if(data=="-1"){
				  easy2go.toast('warn', "参数为空");
			  }else if(data=="-2"){
				  easy2go.toast('warn', "设备与服务器链路不存在!");
			  }else if(data=="-3"){
				  easy2go.toast('warn', "设备与服务器本地链路不存在,不允许提取日志或升级!");
			  }else{
				  easy2go.toast('warn', "命令下发异常!");
 			  }
		  }
	  });
	  //$(t).parent().submit();

  }


  function rePlugSIM(sn){
	  bootbox.confirm("确定给"+sn+"设备重插卡吗?", function(result) {
		  if(result){
			  $.ajax({
				  type:"POST",
				  url:"<%=basePath %>remote2/rePlugSIM",
				  data:{SN:sn},
				  dataType:"html",
				  success:function(data){
					  if(data=='1'){
						  easy2go.toast('info', "命令下发成功");
					  }else if(data=='0'){
						  easy2go.toast('info', "命令下发失败");
					  }else if(data=='-1'){
						  easy2go.toast('info', "参数为空");
					  }else if(data=="-2"){
						  easy2go.toast('warn', "设备与服务器链路不存在!");
					  }
				  }
			  });
		  }

	  });

  }

  function remoteShutdown(sn){
	  bootbox.confirm("确定给"+sn+"设备关机吗?", function(result) {
		  if(result){
			  $.ajax({
				  type:"POST",
				  url:"<%=basePath %>remote2/remoteShutdown",
				  data:{SN:sn},
				  dataType:"html",
				  success:function(data){
					  if(data=='1'){
						  easy2go.toast('info', "命令下发成功");
					  }else if(data=='0'){
						  easy2go.toast('info', "命令下发失败");
					  }else if(data=='-1'){
						  easy2go.toast('info', "参数为空");
					  }else if(data=="-2"){
						  easy2go.toast('warn', "设备与服务器链路不存在!");
					  }
				  }
			  });
		  }
	  });
  }
  //换卡
  function changeCard(sn){
	  bootbox.confirm("确定给"+sn+"设备换卡吗?", function(result) {
		  if(result){
			  $.ajax({
				  type:"POST",
				  url:"<%=basePath %>remote2/changeCard",
				  data:{SN:sn},
				  dataType:"html",
				  success:function(data){
					  if(data=='1'){
						  easy2go.toast('info', "换卡命令下发成功");
					  }else if(data=='0'){
						  easy2go.toast('info', "换卡命令下发失败");
					  }else if(data=='-1'){
						  easy2go.toast('info', "参数为空");
					  }else if(data=="-2"){
						  easy2go.toast('warn', "设备与服务器链路不存在!");
					  }
				  }
			  });
		  }
	  });
  }

  function urltovalue(index,urlstring){

	  alert(index+"|"+urlstring);
	  var d=urlstring.split("&");
	  var c= d[index].split("=");
	  return  c[1];

  }

    </script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>