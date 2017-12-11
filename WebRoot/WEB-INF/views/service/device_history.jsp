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
    <title>全部客户-客户管理-EASY2GO ADMIN</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
     <link href="<%=basePath %>static/css/authority/common_style.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/authority/jquery.fancybox-1.3.4.css"></link>
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
<DIV class="panel-body" style="margin:0px;padding:0px;">
<DIV class="panel-heading"><b>历史在线设备: <span style="color: red;" id="snCount"><input type="hidden"  value="" id="snCountInput"></span></b></DIV>

<!-- ======================================================================================== -->
<!--  <SECTION id="main-content"> -->
 <SECTION class="wrapper" style="margin-top:20px;">
<DIV class="col-md-12">
<DIV class="panel">
<DIV class="panel-body" style="padding-top:5px;padding-bottom:5px;">
<%--    <FORM class="form-inline"  method="get" action="<%=basePath %>customer/customerInfolist/getSearchCustomerinfolist"> --%>
   <FORM class="form-inline" id="searchForm"  method="get" action="#">
   <input type="hidden" value="1" id="pagenum"/><input type="hidden" id="pagesize" value="20" />
         <DIV class="form-group">
             <LABEL class="inline-label">设备机身码：</LABEL>
             <INPUT class="form-control" name="SN" id="SN" type="text" placeholder="机身码" value="${SN==''?'':SN }">
         </DIV>
         <div class="form-group">
          <label class="inline-label">国&nbsp;家：</label>
          <select name="mcc" style="width: 130px;"
          class="form-control">
            <option value="">全部国家</option>
            <c:forEach items="${Countries}" var="country" varStatus="status"><option value="${country.countryCode}">${country.countryName}</option></c:forEach>
          </select>
        </div><br/>
         <DIV class="form-group">
             <LABEL class="inline-label">&nbsp;&nbsp;&nbsp;在线日期：</LABEL>
             <INPUT id="order_creatorDateend" class="form-control form_datetime" name="lastTime" type="text" value="${lastUpdateDate==''?'':lastUpdateDate }">
         </DIV>
         <!--  <div class="form-group">
         	<label class="checkbox-items"><INPUT class="" name="ifOnlyString" value="1" type="checkbox" >无流量上传设备</label>
         </div> -->
        <!--  <div class="form-group">
         	<label class="checkbox-items"><INPUT class="" name="ifExcOut" value="是" type="checkbox" >异常退出</label>
         </div> -->
         <DIV class="form-group">
             <BUTTON class="btn btn-primary" type="button" onclick="$('#snCount').html(0);gridObj.options.otherParames =$('#searchForm').serializeArray();gridObj.page(1);">搜索</BUTTON>
         </DIV>
   </FORM>
</DIV>
</DIV>

<DIV class="panel">
<!-- <DIV class="panel-heading">历史在线设备</DIV> -->
<DIV class="panel-body">
<DIV class="table-responsive">
<TABLE id="searchTable">
  <TR>
    <TH w_render="SN"><b>设备机身码</b></TH>
    <th w_render="IMSI"><b>IMSI</b></th>
    <th w_index="simAlias"><b>SIM卡代号</b></th>
    <th w_render="sim_speedType"><b>高/低速</b></th>
    <th w_render="customerName"><b>客户姓名</b></th>
    
    <th w_render="customerType" width="5%" w_hidden="true"><b>客户类型</b></th>
    <th w_index="customerID" w_hidden="true"><b>客户id</b></th>
    <th w_render="jizhan" w_sort="mcc,asc" width="10%"><b>基站信息</b></th>
    <th w_index="lastTime02" w_sort="lastTime02,ase" width="10%"><b>最近活动时间</b></th>
    <th w_index="battery" w_sort="battery,ase" width="5%"><b>电量</b></th>
    <th w_index="wifiCount"  w_sort="wifiCount,ase" width="5%"><b>wifi数</b></th>
<!--     <th w_index="roamGStrenth"><b>实时流量</b></th> -->
    <th w_render="ssll"><b>实时流量</b></th>
    <th w_render="dayUsedFlow" width="5%"  w_sort="dayUsedFlow,ase"><b>累积流量</b></th>
    <th w_render="roamDayUsedFlow" width="5%"  w_sort="roamDayUsedFlow,ase"><b>漫游流量</b></th>
    <th w_index="orderExplain" w_length="20" width="1%"><b>订单描述</b></th>
    <th w_index="orderRemark" w_length="10" width="1%"><b>交易备注</b></th>
     <th w_index="updownline" w_length="10" w_sort="lastTime,ase" width="3%"><b>是否在线</b></th>
     <th w_render="tag" width="5%"><b>操作</b></th>
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
    <script  src="<%=basePath %>static/js/jquery/jquery-1.7.1.js"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <script src="<%=basePath%>static/js/bootbox.min.js"></script>
     <script src="<%=basePath %>static/js/fancybox/jquery.fancybox-1.3.4.js"></script>
      <script src="<%=basePath %>static/js/jedate/jedate.js"></script> 
      
    <script type="text/javascript">
     jeDate.skin('gray');
     jeDate({
   		dateCell:"#order_creatorDateend",
   		format:"YYYY-MM-DD",
   		isinitVal:true,
   		isTime:false, //isClear:false,
   		minDate:"2015-09-19",
   		okfun:function(val){}
   	}) 
    var gridObj;
    $(function(){
    	var pagesize=parseInt($("#pagesize").val());
       	gridObj = $.fn.bsgrid.init('searchTable',{
           url:'<%=basePath %>service/center/listlogs',
           autoLoad: false,
           pageSizeSelect: true,
           pageSize:pagesize,
           pageSizeForGrid:[10,20,30,50,100],
           isProcessLockScreen:false,
           additionalAfterRenderGrid:test,
           autoLoad:false,
           otherParames:$('#searchForm').serializeArray(),
           additionalAfterRenderGrid: function(parseSuccess, gridData, options) {
          	 if(parseSuccess){
          		 $("#pagenum").val(options.curPage);
           		 $("#snCount").html(options.totalRows);
         	     var a = $("#searchTable_pt_pageSize").val();
         	    $("#pagesize").val(a);
          	 }
           }
       });
       if($("#pagenum").val()!=""){
    	   gridObj.page($("#pagenum").val());
       }else{
    	   gridObj.page(1);
       }
        
   });
   
    
    bootbox.setDefaults("locale","zh_CN");
	  var gridObj;
	  //和弹窗有冲突
	/* $(function(){
       $("#order_creatorDateend").datetimepicker({
    		pickDate: true,                 //en/disables the date picker
    		pickTime: true,                 //en/disables the time picker
    		showToday: true,                 //shows the today indicator
    		language:'zh-CN',                  //sets language locale
     		defaultDate: moment().add(0, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
    	});
   }); */
    function customerType(record, rowIndex, colIndex, options){
    	if(record.customerType=="合作伙伴"){
    		return '<a class="btn btn-success btn-xs" onclick="return;"><span>'+record.customerType+'</span></a>';
    	}else if(record.customerType=="重要客户"){
    		return '<a class="btn btn-danger btn-xs" onclick="return;"><span>'+record.customerType+'</span></a>';
    	}else if(record.customerType=="普通客户"){
    		//return '<a class="btn btn-success btn-xs" onclick="return;"><span>'+record.customerType+'</span></a>';
    	}
    }
    function customerName(record, rowIndex, colIndex, options){
    	var cusType = record.customerType;
    	if(cusType=="重要客户"){
        	return '<A style="color:#1FB5AD;" href="<%=basePath %>customer/customerInfolist/customerInfoDetail?cusid='+record.customerID+'">'+record.customerName+'</A> <img style="width:16px; height:16px;position: relative;top:-2px;" src="<%=basePath %>static/css/images/vip.jpg"/>';

    	}else if(cusType=="合作伙伴"){
        	return '<A style="color:#1FB5AD;" href="<%=basePath %>customer/customerInfolist/customerInfoDetail?cusid='+record.customerID+'">'+record.customerName+'</A> <img style="width:16px; height:16px;position: relative;top:-2px;" src="<%=basePath %>static/css/images/gj.jpg"/>';
    	} else{//普通客户
        	return '<A style="color:#1FB5AD;" href="<%=basePath %>customer/customerInfolist/customerInfoDetail?cusid='+record.customerID+'">'+record.customerName+'</A>';
    	} 
        	//return '<A style="color:#1FB5AD;" href="<%=basePath %>customer/customerInfolist/customerInfoDetail?cusid='+record.customerID+'">'+record.customerName+'</A>';
       }
    function IMSI(record, rowIndex, colIndex, options){
    	//alert(record.type+"--"+record.SIMAllot);
    	if(record.type=='00'){
    		if(record.SIMAllot==0){
    			return '<A style="color:#1FB5AD;" href="<%=basePath %>sim/siminfo/view/' + record.SIMID + '">'+record.IMSI+'</A>';
    		}else if(record.SIMAllot==3){
        		return '登陆失败,<span class="label label-danger label-xs">没有有效订单</span>';
        	}else if(record.SIMAllot==4){
        		return '登陆失败,<span class="label label-danger label-xs">没有分配到SIM卡</span>';
        	}else if(record.SIMAllot==6){
        		return '<span class="label label-danger label-xs">已发生跳转</span>';
        	}
    	}else{
    		return '<A style="color:#1FB5AD;" href="<%=basePath %>sim/siminfo/view/' + record.SIMID + '">'+record.IMSI+'</A>';
    	}
       }		 
    function SN(record, rowIndex, colIndex, options){
    	var time = record.lastTime;"C:/Users/Administrator/Application Data/SSH/temp/upgradeFile"
  	  	time = time.substr(0,10);
     	  if($("#order_creatorDateend").val()==''){
     		  
     	  }else{
     		 time = $("#order_creatorDateend").val();
     	  }	
     	 if(record.tag==''){
     		return '<A style="color:#1FB5AD;" href="<%=basePath %>service/center/devicelogs?sbid='+record.SN+'&sj='+time+'">'+record.SN+'</A>';
     	}else{
     		return '<A style="color:#1FB5AD;" href="<%=basePath %>service/center/devicelogs?sbid='+record.SN+'&sj='+time+'">'+record.SN+'</A><a href="javascript:return;"><img  title="'+record.tag+'" src="<%=basePath %>static/images/050.png" /></a>';
     	}
<%-- 
      	 // return '<A style="color:#1FB5AD;" href="<%=basePath %>service/center/devicelogs?sbid='+record.SN+'&sj='+time+'">'+record.SN+'</A>'; --%>
    	
       }
    function ssll(record, rowIndex, colIndex, options){//实时流量
    	if(record.upFlowAll==''){
    		return '暂无数据';
    	}
    	var result=parseInt(record.upFlowAll)+parseInt(record.downFlowAll);
    	if(result>1024){
    		result=result/1024;
    		result=result+"";
    		result = result.substr(0,result.indexOf(".")+3)+"MB";   
    	}else{
    		result = result + "KB";
    	}
    	return result;
    }
    function dayUsedFlow(record, rowIndex, colIndex, options){//累计流量
    	if(record.dayUsedFlow==''){
    		return '暂无数据';
    	}
    	var result = record.dayUsedFlow;
    	if(result>1024){
    		result=result/1024;
    		result=result+"";
    		result = result.substr(0,result.indexOf(".")+3)+"MB";   
    	}else{
    		result = result + "KB";
    	}
    	return result;
    }
    function roamDayUsedFlow(record, rowIndex, colIndex, options){
    	if(parseInt(record.roamDayUsedFlow)>=1024){
    		var result=parseInt(record.roamDayUsedFlow)/1024;
    		result = result + "";
    		if(result.indexOf(".")>-1){
    			return result.substr(0,result.indexOf(".")+3)+"KB"; 
    		}else{
    			return result+"KB";
    		}
    	}else{
        	return record.roamDayUsedFlow+"byte"; 
    	}
    }
    function jizhan(record, rowIndex, colIndex, options){
    	var j=record.jizhan;
    	if(j.indexOf(",")>-1){
    		j=j.split(",")[0];
    	}
    	return '<A style="color:#1FB5AD;" target="_blank" href="<%=basePath %>remote/mapView?jizhan='+j+'">'+j+'</A>';
    }
    
    function tag(record, rowIndex, colIndex, options){
    	if(record.phone==null||record.phone==""||record.SN==null){
    		return "";
    	}
    	return '<a class="btn btn-primary btn-xs" onclick="MSMsend('+record.SN+','+record.phone+')" class="glyphicon glyphicon-edit">发送短信</a>';
    }
    
    function MSMsend(sn,phone){
    	$.fancybox({
		    	'href'  : '<%=basePath %>/service/center/device/sendmsg?SN='+sn+'&phone='+phone,
		    	'width' : 1200,
		        'height' : 800,
		        'type' : 'iframe',
		        'hideOnOverlayClick' : false,
		        'showCloseButton' : true,
		        'onClosed' : function() { 
		        	 //window.location.href = window.location.href; //关闭弹出框之后自动刷新父页面
		        }
		    }); 
    }
    
    function sim_speedType(record, rowIndex, colIndex, options){
    	if(record.speedType==0) {
            return '<a class="btn btn-info btn-xs">高速</a>';
        	} else if(record.speedType==1) {
             return '<a class="btn btn-danger btn-xs">低速</a>';
        	}else {
        		return '';
        	}
    	}
    
    
    function test(){
    	/* var t= $('#searchTable tbody').children().eq(1);
    	t.addClass("selected selected_color"); */
    	
    }
    
    $(document).keydown(function(event){
    	if(event.keyCode == 13){//绑定回车
    		$("#SN").blur();
    		$("#mcc").blur();
    		gridObj.options.otherParames =$('#searchForm').serializeArray();
    		gridObj.page(1);
    		event.returnValue = false;
    		searchGrabCard();
    	}
   });
    </script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>
