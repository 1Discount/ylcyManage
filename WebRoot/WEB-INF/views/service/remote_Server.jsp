<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <!-- 格式化时间 -->
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
         <DIV class="form-group">
             <LABEL class="inline-label">设备机身码：</LABEL>
             <INPUT class="form-control" name="SN" id="SN" type="text" placeholder="机身码">
         </DIV>
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
<TABLE id="searchTable">
  <TR>
    <TH w_render="SN"><b>设备机身码</b></TH>
    <!-- <th w_render="getLogs"><b>获取日志</b></th>
    <th w_render="remoteSearch"><b>远程查询</b></th>
    <th w_render="remotesj"><b>远程升级</b></th> -->
    <th w_render="VPN"><b>VPN设置</b></th>
     <th w_render="looklogs"><b>操作</b></th>
    <!-- <th w_render="looklogs"><b>查看日志</b></th> -->
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
    window.setInterval(function(){
    	pull();
    },1000);
    var pathurl='<%=basePath %>service/center/nowlistlogs';
    if('${sn}'!=null){

    }
    var gridObj;
    $(function(){
       gridObj = $.fn.bsgrid.init('searchTable',{
           url:pathurl,
           pageSizeSelect: true,
           pageSize: 20,
           pageSizeForGrid:[10,20,30,50,100],
           isProcessLockScreen:false,
           multiSort:true
       });
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
    	return "<form class='form-inline' action='<%=basePath %>remote/getDevLogs'>"+
    	"<input type='hidden' name='sn' value='"+record.SN+"' /><select class='form-control' name='bm' style='width:80px;'><option value='1'>漫游</option><option value='0'>本地</option></select>"+
    	"<input class='form-control' name='num' placeholder='num' style='width:80px;' />"+
    	"<button type='submit' onclick='os()' class='btn btn-primary'> 发送 </button></form>";
    }
    // 远程查询
    function remoteSearch(record,rowIndex,colIndex,options){
      	return "<form class='form-inline' action='<%=basePath %>remote/remoteQuery'>"+
    	"<input type='hidden' name='sn' value='"+record.SN+"' /><input class='form-control' name='mes' placeholder='content' style='width:120px;' />"+
    	"<button type='submit' onclick='os()' class='btn btn-primary'> 发送 </button></form>";
    }
    // 远程升级
    function remotesj(record,rowIndex,colIndex,options){
      	return "<form class='form-inline'  action='<%=basePath %>remote/remoteUpgrade'>"+
    	"<input type='hidden' name='sn' value='"+record.SN+"' /><select class='form-control' name='type' style='width:80px;'><option value='3'>漫游</option><option value='4'>本地</option><option value='5'>漫游apk</option><option value='6'>本地apk</option><option value='7'>MIP</option></select>"+
    	"<button type='submit' onclick='os()' class='btn btn-primary'> 发送 </button></form>";
    }
    // VPN
    function VPN(record,rowIndex,colIndex,options){
      	return "<form class='form-inline' action='<%=basePath %>remote/modifyVPN'>"+
    	"<input type='hidden' name='sn' value='"+record.SN+"' /><input class='form-control' name='vpn' placeholder='VPN' style='width:160px;' />"+
    	"<button type='button' onclick='os(this)' class='btn btn-primary'> 发送 </button></form>";
    }
    // 远程限速
    function remoteSpeed(record,rowIndex,colIndex,options){
      	return "<form class='form-inline' action='<%=basePath %>remote/limitspeed'>"+
    	"<input type='hidden' name='sn' value='"+record.SN+"' /><input class='form-control' name='limitValve' type='Number'placeholder='阀值' style='width:80px;' />MB"+
    	"<input class='form-control' name='limitSpeed' type='Number'placeholder='限速值'  style='width:100px;' />KB"+
    	"<button type='submit' onclick='os()'  class='btn btn-primary'> 发送 </button></form>";
    }
    // 操作
    function looklogs(record,rowIndex,colIndex,options){
      	return "<button onclick='restPwd("+record.SN+")' class='btn btn-primary'> 重置wifi密码</button>";
      	/* "&nbsp;&nbsp;<button onclick='remoteShutdown("+record.SN+")' class='btn btn-primary'>远程关机</button>"+
      	"&nbsp;&nbsp;<button onclick='rePlugSIM("+record.SN+")' class='btn btn-primary'>重插卡</button>"; */

      	/* return  '<div role="group" class="btn-group"><button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="btn btn-primary dropdown-toggle">操作<span class="caret"></span></button>'+
      	'<ul class="dropdown-menu"><li><a href="/remote/loglist/172150210001603">查看日志</a>'+
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
	  window.location="<%=basePath %>remote/tologs?SN="+sn;
  }

  function pull(){
	  $.ajax({
		  type:"POST",
		  url:"<%=basePath %>remote/mesPush",
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
	  })
  }

  function os(t){
	  $.ajax({
		  type:"POST",
		  url:$(t).parent().attr("action"),
		  dataType:"html",
		  data:$(t).parent().serialize(),
		  success:function(data){

			  if(data=="1"){
				  easy2go.toast('info', "命令下发成功");
			  }else if(data=="0"){
				  easy2go.toast('info', "命令下发失败");
			  }else if(data=="-1"){
				  easy2go.toast('info', "参数为空");
			  }else if(data=="-2"){
				  easy2go.toast('warn', "设备与服务器链路不存在!");
			  }
		  }

	  });
  }


  function rePlugSIM(sn){
	  $.ajax({
		  type:"POST",
		  url:"<%=basePath %>remote/rePlugSIM",
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

  function remoteShutdown(sn){
	  $.ajax({
		  type:"POST",
		  url:"<%=basePath %>remote/remoteShutdown",
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

  /**重置密码 **/
  function restPwd(sn){
	  $.ajax({
		  type:"POST",
		  url:"<%=basePath %>remote/restPwd",
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
    </script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>