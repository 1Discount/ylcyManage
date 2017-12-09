<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <!-- 格式化时间 -->
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Date d=new Date();
long nt=d.getTime();
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
<!-- <DIV class="panel-heading">当前时间设备日志</DIV> -->
<DIV class="panel-body" style="margin:0px;padding:0px;">
 <SECTION class="wrapper" style="margin-top:20px;">
<DIV class="col-md-12">
<DIV class="panel">
<DIV class="panel-body" style="padding-top:5px;padding-bottom:5px;">
   <FORM class="form-inline" id="searchForm"  method="get" action="#">
         <DIV class="form-group"><input type="hidden" id="pagenum" value="1" /><input type="hidden" id="pagesize" value="<%=Spagesize %>" />
             <LABEL class="inline-label">设备序列号：</LABEL>
             <INPUT class="form-control" name="SN" id="SN" type="text" placeholder="SN" value="${SN }">
         </DIV>
         <div class="form-group">
          <label class="inline-label">国&nbsp;家：</label>
          <select name="mcc" style="width: 130px;" id="mcc"
          class="form-control">
            <option value="">全部国家</option>
            <c:forEach items="${Countries}" var="country" varStatus="status"><option value="${country.countryCode}">${country.countryName}</option></c:forEach>
          </select>
        </div>
         <div class="form-group">
         <label class="checkbox-items"><INPUT class="" name="ifOnlyString" value="1" type="checkbox" >只显示飘红</label>
          <label class="checkbox-items"><INPUT class="" name="roamDayUsedFlow" value="102400" type="checkbox" >显示飘黄</label>
           <label class="checkbox-items"><INPUT class="" name="TTCnt" value="80" type="checkbox" >显示飘蓝</label>
		</div>		
         <DIV class="form-group">
             <BUTTON class="btn btn-primary"  type="button" onclick="$('#snCount').html(0);gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.page(1);searchGrabCard();">搜索</BUTTON>
         </DIV>
         <DIV class="form-group">
         	<BUTTON class="btn btn-primary" id="f5btn" type="button" onclick="f5but()">开启10秒自动刷新</BUTTON>
         </DIV>
   </FORM>
</DIV>
</DIV>

<DIV class="panel">
<DIV class="panel-heading">10分钟内活动设备：<span   style="color: red;font-weight:bold;" id="snCount"></span><input type="hidden"  id="snCountInput"><span style="color: orange;margin-left: 20px;">提示：有黄色叹号的表示有标记过的，注意查看。红色vip表示重要客户，绿色vip表示合作伙伴</span><span style="color: red;margin-left: 20px;">飘黄为种子卡累计流量大于100KB,飘蓝为透传次数大于80次</span><span id="grabCard" style="color: red;margin-left: 20px;"></span></DIV>
<DIV class="panel-body">
<DIV class="table-responsive">
<TABLE id="searchTable">
  <TR  classs="extend_render_per_row">
    <TH w_render="SN" width="5%"><b>设备序列号</b></TH>
    <th w_render="IMSI" width="10%"><b>IMSI</b></th>
    <th w_index="simAlias" width="5%"><b>SIM卡代号</b></th>
    <th w_render="sim_speedType" width="5%"><b>高/低速</b></th>
    <th w_render="customerName" width="5%"><b>客户姓名</b></th>
<!--     <th w_index="customerType" width="5%" w_hidden="false"><b>客户类型</b></th> -->
    <th w_index="customerID" w_hidden="true"><b>客户id</b></th>
    <th w_render="jizhan" w_sort="mcc,asc" width="10%"><b>基站信息</b></th>
    <th w_index="lastTime" w_sort="lastTime,ase" width="10%"><b>最近活动时间</b></th>
    <th w_index="battery" w_sort="battery,ase" width="5%"><b>电量</b></th>
    <th w_index="wifiCount"  w_sort="wifiCount,ase" width="5%"><b>wifi数</b></th>
    <th w_render="ssll" width="5%"><b>实时流量</b></th>
    <th w_render="dayUsedFlow" width="5%"  w_sort="dayUsedFlow,ase"><b>累积流量</b></th>
    <th w_render="roamDayUsedFlow" width="5%"  w_sort="roamDayUsedFlow,ase"><b>漫游流量</b></th>
     <th w_index="orderExplain" w_length="20" width="1%"><b>订单描述</b></th>
    <th w_index="orderRemark" w_length="10" width="1%"><b>交易备注</b></th>
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
    <script src="<%=basePath%>static/js/bootbox.min.js"></script>
      <script  src="<%=basePath %>static/js/jquery/jquery-1.7.1.js"></script> 
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
     <script src="<%=basePath %>static/js/fancybox/jquery.fancybox-1.3.4.js"></script> 
    
    
    
    <script type="text/javascript">
    bootbox.setDefaults("locale","zh_CN");
    var f5flag=false;
    $.bsgrid.forcePushPropertyInObject($.fn.bsgrid.defaults.extend.renderPerRowMethods,'extend_render_per_row', function(record, rowIndex, trObj, options){
    	if(record!=null){
          	  if(record.ifRed==1){
          		//alert(1);
          		trObj.find('td').css('background-color', 'pink');
          	  }else if(record.roamDayUsedFlow>102400){
          		//alert(1);
          		trObj.find('td').css('background-color', 'yellow');
             }else if(record.TTCnt>80){
           		//alert(1);
           		trObj.find('td').css('background-color', '#C8ECF4');
             }
    	}
		//console.log('extend render per row.');
	}); 
    var snCount=0;
    var gridObj;
    $(function(){
       var pagesize=parseInt($("#pagesize").val());
       gridObj = $.fn.bsgrid.init('searchTable',{
           url:'<%=basePath %>service/center/nowlistlogs',
           autoLoad: false,
           pageSizeSelect: true,
           pageSize:pagesize,
           pageSizeForGrid:[10,20,30,50,100],
           isProcessLockScreen:false,
           autoLoad:false,
           otherParames:$('#searchForm').serializeArray(),
           additionalAfterRenderGrid: function(parseSuccess, gridData, options) {
          	 if(parseSuccess){
          		 $("#snCount").html(options.totalRows);
          		 $("#pagenum").val(options.curPage);
          	     var a = $("#searchTable_pt_pageSize").val();
       		 	 $("#pagesize").val(a);
          	 }else{
          		
          	 }
           }
       });
       if($("#pagenum").val()!=""){
    	   gridObj.page($("#pagenum").val());
       }else{
    	   gridObj.page(1);
       }
      // searchGrabCard();
   });
    
    /* function test(){
    	alert(snCount);
    	$("#snCount").html(snCount);
    } */
    function customerName(record, rowIndex, colIndex, options){
    	var cusType = record.customerType;
    	if(cusType=="重要客户"){
        	return '<A style="color:#1FB5AD;" href="<%=basePath %>customer/customerInfolist/customerInfoDetail?cusid='+record.customerID+'">'+record.customerName+'</A> <img style="width:16px; height:16 px;" src="<%=basePath %>static/css/images/vip.jpg"/>';

    	}else if(cusType=="合作伙伴"){
        	return '<A style="color:#1FB5AD;" href="<%=basePath %>customer/customerInfolist/customerInfoDetail?cusid='+record.customerID+'">'+record.customerName+'</A> <img style="width:16px; height:16px;" src="<%=basePath %>static/css/images/gj.jpg"/>';
    	} else{//普通客户
        	return '<A style="color:#1FB5AD;" href="<%=basePath %>customer/customerInfolist/customerInfoDetail?cusid='+record.customerID+'">'+record.customerName+'</A>';
    	} 
    }
    function customerType(record, rowIndex, colIndex, options){
    	if(record.customerType=="合作伙伴"){
    		return '<a class="btn btn-success btn-xs" onclick="return;"><span>'+record.customerType+'</span></a>';
    	}else if(record.customerType=="重要客户"){
    		return '<a class="btn btn-danger btn-xs" onclick="return;"><span>'+record.customerType+'</span></a>';
    	}else if(record.customerType=="普通客户"){
    		//return '<a class="btn btn-success btn-xs" onclick="return;"><span>'+record.customerType+'</span></a>';
    	}
    }
    function IMSI(record, rowIndex, colIndex, options){
    	if(record.type=='00'){
    		if(record.SIMAllot==0){
    			return '<A style="color:#1FB5AD;" href="<%=basePath %>sim/siminfo/view/' + record.SIMID + '">'+record.IMSI+'</A>';
    		}else if(record.SIMAllot==3){
        		return '登陆失败,<a  class="label label-danger label-xs" href="<%=basePath %>orders/flowdealorders/list?SN='+record.SN+'"><span>没有有效订单</span></a>';
        	}else if(record.SIMAllot==4){
        		return '登陆失败, <a  class="label label-danger label-xs" href="<%=basePath %>sim/siminfo/index?MCC='+record.location+'"><span>没有可用卡位</span></a>';
        	}else if(record.SIMAllot==6){
        		return '<span class="label label-danger label-xs">已发生跳转</span>';
        	}else if(record.SIMAllot==5){
        		return '<span class="label label-danger label-xs">接入太频繁</span>';
        	}
    	}else{
    		return '<A style="color:#1FB5AD;" href="<%=basePath %>sim/siminfo/view/' + record.SIMID + '">'+record.IMSI+'</A>';
    	}
       }
    function SN(record, rowIndex, colIndex, options){
    	snCount++;
    	if(record.tag==''){
    		return '<A style="color:#1FB5AD;" href="<%=basePath %>service/center/devicelogs?sbid='+record.SN+'&sj=">'+record.SN+'</A>';
    	}else{
    		return '<A style="color:#1FB5AD;" href="<%=basePath %>service/center/devicelogs?sbid='+record.SN+'&sj=">'+record.SN+'</A><a href="javascript:return;"><img  title="'+record.tag+'" src="<%=basePath %>static/images/050.png" /></a>';
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
    	var smsg="";
    	if(record.phone!=null&&record.phone!=""&&record.SN!=null){
    		smsg='<a class="btn btn-primary btn-xs"><span onclick="MSMsend('+record.SN+','+record.phone+')" class="glyphicon glyphicon-edit">发送短信</span></a>&nbsp;&nbsp;';
    	}
    	return smsg+'<a class="btn btn-primary btn-xs"><span onclick="addtag('+rowIndex+')" class="glyphicon glyphicon-edit">标记</span></a>&nbsp;&nbsp;'+
    	'<a class="btn btn-primary btn-xs"><span onclick="addAfterSale(\''+record.SN+'\')" class="glyphicon glyphicon-edit">售后服务</span></a>&nbsp;&nbsp;'+
    	'<a class="btn btn-primary btn-xs"><span onclick="submitOrders('+rowIndex+')" class="glyphicon glyphicon-edit">提工单</span></a>';
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
    function ssll(record, rowIndex, colIndex, options){//实时流量
    	
    	if(record.upFlowAll==''){
    		return '暂无数据';
    	}
    	var result=parseInt(record.upFlowAll)+parseInt(record.downFlowAll);
    	if(result>=1024){
    		result=result/1024;
    		result=result+"";
    		if(result.indexOf(".")>-1){
    			result = result.substr(0,result.indexOf(".")+3)+"MB";
    		}else{
    			result=result+"MB";
    		}
    		   
    	}else{
    		result = result + "KB";
    	}
    	return result;
    }
    function dayUsedFlow(record, rowIndex, colIndex, options){//累计流量
    	var result = record.dayUsedFlow;
    	if(record.dayUsedFlow==''){
    		return '暂无数据';
    	}
    	if(result>1024){
    		result=result/1024;
    		result=result+"";
    		if(result.indexOf(".")>-1){
    			result = result.substr(0,result.indexOf(".")+3)+"MB";
    		}else{
    			result=result+"MB";
    		}   
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
    
    //查询抢卡
    function searchGrabCard()
    {
    	//alert("查询抢卡----");
    	$.ajax({
    		type:"POST",
            url:"<%=basePath %>service/center/searchGrabCard",
            success:function(data){
                $("#grabCard").html(data.data);
            }
    	});
    }
    
    window.setInterval(function(){
    	if(f5flag){
    		gridObj.refreshPage();
    		searchGrabCard();
    	}
    },10000);
    
    function f5but(){
    	if(f5flag){
    		f5flag=false;
    		$("#f5btn").html("开启10秒自动刷新");
    		$("#f5btn").attr("class","btn btn-warning");
    	}else{
    		f5flag=true;
    		$("#f5btn").html("关闭自动刷新");
    		$("#f5btn").attr("class","btn btn-primary");
    	}
    }
    
    //打开弹出框
    function addtag(index){
    	var record= gridObj.getRecord(index);
    	var tags=record.tag.split("|")[0];
    	bootbox.dialog({
            title: "请输入/修改标注内容 SN:"+record.SN,
            message: '<div class="row">  ' +
                '<div class="col-md-12"> ' +
                '<form class="form-horizontal" id="sim-notes-form" mothod="post"> ' +
                '<div class="form-group"> ' +
                '<label class="col-md-3 control-label" for="name">标注:</label> ' +
                '<div class="col-md-8">' +
                '<textarea id="notes" rows="3" name="notes" maxlength="254" data-popover-offset="0,8" class="form-control">' + 
                tags+ '</textarea>' + //unescape(notes)
                '</div> ' +
                '</form> </div>  </div>',
            buttons: {
                cancel: {
                    label: "去掉标记",
                    className: "btn-default",
                    callback: function () {
                    	$.ajax({
                    		type:"POST",
                    		dataType:"html",
                    		url:"<%=basePath %>service/center/SNtag",
                    		data:{sn:record.SN,tag:""},
                    		success:function(data){
                    			if(data=='1'){
                    				gridObj.refreshPage();
                    			}else{
                    				easy2go.toast('warn',"标记保存出错");
                    			}
                    		}
                    		
                    	});
                    }
                },
                success: {
                    label: "保存",
                    className: "btn-success edit-button-ok",
                    callback: function () {
                    	$.ajax({
                    		type:"POST",
                    		dataType:"html",
                    		url:"<%=basePath %>service/center/SNtag",
                    		data:{sn:record.SN,tag:$("#notes").val()},
                    		success:function(data){
                    			if(data=='1'){
                    				gridObj.refreshPage();
                    			}else{
                    				easy2go.toast('warn',"标记保存出错");
                    			}
                    			
                    		}
                    		
                    	});
                    }
                }
            }
        });
    	
    }
    
    function submitOrders(index){
    	var record= gridObj.getRecord(index);
    	var j=record.jizhan;
        /* if(j.indexOf(",")>-1){
            j=j.split(",")[0];
        } */
        <%-- $.fancybox({
	    	'href'  : '<%=basePath %>submitorders/list?SN="+record.SN+"&customerName="+record.customerName+"&location="+j+"&IMSI="+record.IMSI',
	    	'width' : 1200,
	        'height' : 800,
	        'type' : 'iframe',
	        'hideOnOverlayClick' : false,
	        'showCloseButton' : true,
	        'onClosed' : function() { 
	        	 //window.location.href = window.location.href; //关闭弹出框之后自动刷新父页面
	        }
	    });  --%>
        
    	location = "<%=basePath %>submitorders/list?SN="+record.SN+"&customerName="+record.customerName+"&location="+j+"&IMSI="+record.IMSI;
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
    function addAfterSale(sn){
    	location="<%=basePath %>/orders/aftersale/list?SN="+sn;
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
		
		var aa = document.getElementById(sn).value;//$("#sn  option:selected").val();
		
		aa = aa.split(",");
		var value = aa[0];
		var conectionCount = aa[1];
		var dl= aa[2];
		var SMStext =sn;
		var Phone=phone;
		var templateId="";
		if(value=="notificationrestart"){
			templateId="25023";
		}else if(value=="tooManyDevicesConnected"){
			templateId="25019";
		}else if(value=="uploadAndDownloadTraffic"){
			templateId="25020";
		}else if(value=="regionalsignaldifference"){
			templateId="25022";
		}else if(value=="orderend"){
			templateId="28437";
		}else if(value=="batterylow"){
			templateId="25021";
		}else if(value=="customAuto"){
			templateId="35438";
		}
		 
		<%--  $.ajax({
			  type:"POST",
			  url:"<%=basePath%>devicelogs/startsendmsm?phone=" + Phone
						+ "&SMStext=" + SMStext+"&templateId="+templateId+"&conectionCount="+conectionCount+"&dl="+dl,
				dataType : "html",
				success : function(data) {
					if (data == "1") {
						easy2go.toast('info', "短信发送成功");
					} else if (data = "0") {
						easy2go.toast('warn', "短信发送失败!");
					}
				}
			}) --%>
		}
	
    </script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>