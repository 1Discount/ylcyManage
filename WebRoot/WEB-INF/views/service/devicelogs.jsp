<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <!-- 格式化时间 -->
<%@ page import="java.net.URLEncoder" %>
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

<!--  <SECTION id="main-content"> -->
<!-- <SECTION class="wrapper"> -->
<!-- <DIV class="col-md-12"> -->
<!-- <DIV class="panel"> -->
<!-- <DIV class="panel-heading">当前时间设备日志</DIV> -->
<DIV class="panel-body" style="margin:0px;padding:0px;">
<!-- ======================================================================================== -->


  <SECTION
id="main-content"><SECTION class="wrapper">
<DIV class="row">
<DIV class="col-md-12">
<DIV class="panel">
<DIV class="panel-heading"> <b>设备登录记录</b>
  <LABEL class="label label-info label-xs" id='sbid'></LABEL>
 <b>漫游卡ICCID:</b><b>${ICCID }</b>
  <b>日期:</b><b>${sj }</b>
</DIV>
<DIV class="panel-body">
<DIV class="table-responsive">
<!-- <TABLE class="table table-bordered table-hover">
  <THEAD>
  <TR>
    <TH>基站信息</TH>
    <TH>imsi</TH>
    <TH>创建时间</TH>
    <TH>登录结果</TH></TR></THEAD>
  <TBODY>
  <TR>
    <TD><LABEL>502.19.41502.41153</LABEL></TD>
    <TD><LABEL>809502191325479689</LABEL></TD>
    <TD><LABEL>2015-7-2 19:38:06</LABEL></TD>
    <TD><SPAN class="label label-success label-xs">成功</SPAN></TD>
  </TR>
   </TBODY>
 </TABLE> -->
 <TABLE id="searchTable1">
  <TR>
    <th w_index="jizhan"><b>基站信息</b></th>
    <th w_index="IMSI"><b>IMSI</b></th>
    <th w_render="batteryCount"><b>剩余电量</b></th>
    <th w_index="minsRemaining"><b>开机时间(秒)</b></th>
    <th w_index="lastTime"><b>记录时间</b></th>
    <th w_render=newStart><b>周期类型</b></th>
     <th w_render="loginr"><b>登录结果</b></th>
<!--     <th w_index="roamDayUsedFlow"><b>累积流量</b></th> -->
  </TR>
</TABLE>

 </DIV>
 </DIV>
 </DIV>
 </DIV>
 </DIV>

<DIV class="row">
<DIV class="col-md-12">
<DIV class="panel">
<DIV class="panel-heading"><LABEL>VPN分配记录</LABEL></DIV>
<DIV class="panel-body">
<DIV class="table-responsive">
<!-- <TABLE class="table table-bordered table-hover">
  <THEAD>
  <TR>
    <TH>imsi</TH>
    <TH style="width: 8%;"> 限速状态</TH>
    <TH style="width: 6%;">信号强度</TH>
    <TH style="width: 6%;">上传流量</TH>
    <TH style="width: 6%;"> 下载流量</TH>
    <TH style="width: 6%;"> 累积流量</TH>
    <TH>记录时间</TH></TR></THEAD>
  <TBODY>
  <TR>
    <TD><LABEL>809502191325479689</LABEL></TD>
    <TD><SPAN class="label label-success label-xs">正常</SPAN></TD>
    <TD><LABEL>11</LABEL></TD>
    <TD><LABEL>27.59 MB</LABEL></TD>
    <TD><LABEL>25.24 MB</LABEL></TD>
    <TD><LABEL>220.95 MB</LABEL></TD>
    <TD><LABEL>2015-7-2 20:43:03</LABEL></TD></TR>
    </TBODY>
  </TABLE> -->

<TABLE id="searchTable5">
  <TR>
    <th w_index="SN"><b>SN</b></th>
    <th w_render="ContextLen"><b>分发/释放</b></th>
    <th w_index="TTContext"><b>VPN</b></th>
    <th w_index="gSta"><b>VPN编号</b></th>
    <th w_render="battery"><b>结果</b></th>
    <th w_index="lastTime"><b>记录时间</b></th>
  </TR>
</TABLE>

    </DIV>
    </DIV>
    </DIV>
   </DIV>
<DIV class="col-md-12">
<DIV class="panel">
<DIV class="panel-heading"><LABEL>本地卡流量记录（本地软件版本：<span>${version}</span>,本地APK软件版本：<span>${versionAPK}</span>）</LABEL></DIV>
<DIV class="panel-body">
<DIV class="table-responsive">
<!-- <TABLE class="table table-bordered table-hover">
  <THEAD>
  <TR>
    <TH>imsi</TH>
    <TH style="width: 8%;"> 限速状态</TH>
    <TH style="width: 6%;">信号强度</TH>
    <TH style="width: 6%;">上传流量</TH>
    <TH style="width: 6%;"> 下载流量</TH>
    <TH style="width: 6%;"> 累积流量</TH>
    <TH>记录时间</TH></TR></THEAD>
  <TBODY>
  <TR>
    <TD><LABEL>809502191325479689</LABEL></TD>
    <TD><SPAN class="label label-success label-xs">正常</SPAN></TD>
    <TD><LABEL>11</LABEL></TD>
    <TD><LABEL>27.59 MB</LABEL></TD>
    <TD><LABEL>25.24 MB</LABEL></TD>
    <TD><LABEL>220.95 MB</LABEL></TD>
    <TD><LABEL>2015-7-2 20:43:03</LABEL></TD></TR>
    </TBODY>
  </TABLE> -->

<TABLE id="searchTable2">
  <TR>
    <th w_index="jizhan"><b>基站</b></th>
    <th w_index="battery"><b>剩余电量</b></th>
    <th w_index="wifiCount"><b>连接数</b></th>
    <th w_index="wifiState"><b>限速值</b></th>
    <th w_render="gStrenth"><b>信号强度</b></th>
    <th w_index="TTContext"><b>网络类型</b></th>
    <th w_render="upFlowAll"><b>上传流量</b></th>
    <th w_render="downFlowAll"><b>下载流量</b></th>
    <th w_render="dayUsedFlow"><b>当前周期累积流量</b></th>
    <th w_render="roamGStrenth"><b>信号强度(漫游)</b></th>
    <th w_render="roamUpFlowAll"><b>上传流量(漫游)</b></th>
    <th w_render="roamDownFlowAll"><b>下载流量(漫游)</b></th>
    <th w_render="roamDayUsedFlow"><b>累积流量(漫游)</b></th>
    <th w_index="minsRemaining"><b>剩余分钟数</b></th>
    <th w_index="lastTime"><b>记录时间</b></th>
  </TR>
</TABLE>

    </DIV>
    </DIV>
    </DIV>
   </DIV>
<DIV class="col-md-12">
<DIV class="panel">
<DIV class="panel-heading"><LABEL>漫游卡流量记录（漫游软件版本：<span>${res}</span>,漫游APK版本:<span>${resAPK}</span>）</LABEL></DIV>
<DIV class="panel-body">
<DIV class="table-responsive">
<!-- <TABLE class="table table-bordered table-hover">
  <THEAD>
  <TR>
    <TH>imsi</TH>
    <TH style="width: 8%;"> 限速状态</TH>
    <TH style="width: 6%;">信号强度</TH>
    <TH style="width: 6%;">上传流量</TH>
    <TH style="width: 6%;"> 下载流量</TH>
    <TH style="width: 6%;"> 累积流量</TH>
    <TH>记录时间</TH></TR></THEAD>
  <TBODY>
  <TR>
    <TD><LABEL>809502191325479689</LABEL></TD>
    <TD><SPAN class="label label-success label-xs">正常</SPAN></TD>
    <TD><LABEL>14</LABEL></TD>
    <TD><LABEL>27.69 MB</LABEL></TD>
    <TD><LABEL>25.78 MB</LABEL></TD>
    <TD><LABEL>221.16 MB</LABEL></TD>
    <TD><LABEL>2015-7-2 20:46:48</LABEL></TD></TR>
</TBODY>
</TABLE> -->

<TABLE id="searchTable3">
  <TR>
    <th w_index="jizhan"><b>基站信息</b></th>
    <th w_index="roamGStrenth"><b>信号强度</b></th>
    <th w_render="dayUsedFlow"><b>流量</b></th>
    <th w_index="lastTime"><b>时间</b></th>
  </TR>
</TABLE>

</DIV></DIV></DIV></DIV>
<DIV class="col-md-12">
<DIV class="panel">
<DIV class="panel-heading"> <LABEL>透传记录</LABEL></DIV>
<DIV class="panel-body">
<DIV class="table-responsive">

<TABLE id="searchTable4">
  <TR>
    <th w_index="SN"><b>设备号</b></th>
    <th w_index="IMSI"><b>IMSI</b></th>
    <th w_index="contextLen"><b>数据长度</b></th>
    <th w_index="TTCnt"><b>透传序号</b></th>
    <th w_index="lastTime"><b>接入时间</b></th>
  </TR>
</TABLE>

<!-- <TABLE class="table table-bordered table-hover">
  <THEAD>
  <TR>
    <TH>设备号</TH>
    <TH>imsi</TH>
    <TH>cnt</TH>
    <TH>lastTime</TH></TR></THEAD>
  <TBODY>
  <TR>
    <TD><LABEL>172150210000035</LABEL></TD>
    <TD><LABEL>809502191325479689</LABEL></TD>
    <TD><LABEL>2</LABEL></TD>
    <TD><LABEL>2015-7-2 19:38:47</LABEL></TD>
  </TR>
    </TBODY>
</TABLE> -->

    </DIV></DIV></DIV></DIV>
    </DIV>
    </SECTION>
    </SECTION>



<!-- ======================================================================================== -->
</DIV>

<!-- </DIV> -->
<!-- </DIV> -->
<!-- </SECTION> -->
<!-- </SECTION> -->
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <script type="text/javascript">

    function GetQueryString(name)//获取地址栏参数的值
    {
         var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
         var r = window.location.search.substr(1).match(reg);
         if(r!=null)return  unescape(r[2]); return null;

    }
    var times=GetQueryString('sj');
    document.getElementById('sbid').innerHTML=" SN ： "+GetQueryString('sbid');
    var gridObj1;
    $(function(){
       gridObj1 = $.fn.bsgrid.init('searchTable1',{
           url:'<%=basePath %>service/center/loginlogs?sbid='+GetQueryString("sbid")+'&sj='+times,
           pageSizeSelect: true,
           pageSize: 10,
           pageSizeForGrid:[10,20,30,50,100],
           multiSort:true
       });
   });

    var gridObj2;
    $(function(){
       gridObj2 = $.fn.bsgrid.init('searchTable2',{
           url:'<%=basePath %>service/center/bdcardlogs?sbid='+GetQueryString("sbid")+'&sj='+times,
           pageSizeSelect: true,
           pageSize: 10,
           pageSizeForGrid:[10,20,30,50,100],
           multiSort:true
       });
   });



    var gridObj3;
    $(function(){
       gridObj3 = $.fn.bsgrid.init('searchTable3',{
           url:'<%=basePath %>service/center/manyoulogs?sbid='+GetQueryString("sbid")+'&sj='+times,
           pageSizeSelect: true,
           pageSize: 10,
           pageSizeForGrid:[10,20,30,50,100],
           multiSort:true
       });
   });

    var gridObj4;
    $(function(){
       gridObj4 = $.fn.bsgrid.init('searchTable4',{
           url:'<%=basePath %>service/center/touchuanlogs?sbid='+GetQueryString("sbid")+'&sj='+times,
           pageSizeSelect: true,
           pageSize: 10,
           pageSizeForGrid:[10,20,30,50,100],
           multiSort:true
       });
   });

    var gridObj5;
    $(function(){
       gridObj5 = $.fn.bsgrid.init('searchTable5',{
           url:'<%=basePath %>service/center/VPNlogs?sbid='+GetQueryString("sbid")+'&sj='+times,
           pageSizeSelect: true,
           pageSize:5,
           pageSizeForGrid:[5,10,20],
           multiSort:true
       });
   });

    function batteryCount(record, rowIndex, colIndex, options){
    	//alert(record.battery);
    	if(record.battery=="0"){
    		return "";
    	}else{
    		return record.battery;
    	}
    }

    function gStrenth(record, rowIndex, colIndex, options){
    		if(record.gStrenth>35){
    			return "-"+record.gStrenth;
    		}else{
    			return record.gStrenth;
    		}
    }

    function roamGStrenth(record, rowIndex, colIndex, options){
		if(record.roamGStrenth>35){
			return "-"+record.roamGStrenth;
		}else{
			return record.roamGStrenth;
		}
	}

    function customerName(record, rowIndex, colIndex, options){
    	return '<A style="color:#1FB5AD;" href="<%=basePath %>customer/customerInfolist/customerInfoDetail?cusid='+record.customerID+'">'+record.customerName+'</A>';
       }
    function upFlowAll(record, rowIndex, colIndex, options){
    	if(parseInt(record.upFlowAll)>1024){
    		var result=parseInt(record.upFlowAll)/1024;
    		result = result + "";
    		if(result.indexOf(".")>-1){
    			return result.substr(0,result.indexOf(".")+3)+"MB";
    		}else{
    			return result+"MB";
    		}

    	}else{
        	return record.upFlowAll+"KB";
    	}
    }
    function downFlowAll(record, rowIndex, colIndex, options){
    	if(parseInt(record.downFlowAll)>1024){
    		var result=parseInt(record.downFlowAll)/1024;
    		result = result + "";
    		if(result.indexOf(".")>-1){
    			return result.substr(0,result.indexOf(".")+3)+"MB";
    		}else{
    			return result+"MB";
    		}

    	}else{
        	return record.downFlowAll+"KB";
    	}
    }
    function dayUsedFlow(record, rowIndex, colIndex, options){
    	var flowcount="";
    	if(parseInt(record.dayUsedFlow)>1024){
    		var result=parseInt(record.dayUsedFlow)/1024;
    		result = result + "";
    		if(result.indexOf(".")>-1){
    			flowcount= result.substr(0,result.indexOf(".")+3)+"MB";
    		}else{
    			flowcount= result+"MB";
    		}

    	}else{
    		flowcount= record.dayUsedFlow+"KB";
    	}
    	if(rowIndex==0){
    		return "<span class='label label-danger label-xs'>"+flowcount+"</span>";
    	}else{
    		return flowcount;
    	}
    }

    function FlowDistinction(record, rowIndex, colIndex, options){
    	var flowcount="";
    	if(parseInt(record.FlowDistinction)>1024){
    		var result=parseInt(record.FlowDistinction)/1024;
    		result = result + "";
    		flowcount= result.substr(0,result.indexOf(".")+3)+"MB";
    	}else{
    		flowcount= record.FlowDistinction+"KB";
    	}
    	if(rowIndex==0){
    		return "<span class='label label-danger label-xs'>"+flowcount+"</span>";
    	}else{
    		return flowcount;
    	}
    }

    function roamUpFlowAll(record, rowIndex, colIndex, options){
    	if(parseInt(record.roamUpFlowAll)>=1024){
    		var result=parseInt(record.roamUpFlowAll)/1024;
    		result = result + "";
    		if(result.indexOf(".")>-1){
    			return result.substr(0,result.indexOf(".")+3)+"KB";
    		}else{
    			return result+"KB";
    		}
    	}else{
        	return record.roamUpFlowAll+"byte";
    	}
    }
    function roamDownFlowAll(record, rowIndex, colIndex, options){
    	if(parseInt(record.roamDownFlowAll)>=1024){
    		var result=parseInt(record.roamDownFlowAll)/1024;
    		result = result + "";
    		if(result.indexOf(".")>-1){
    			return result.substr(0,result.indexOf(".")+3)+"KB";
    		}else{
    			return result+"KB";
    		}

    	}else{
        	return record.roamDownFlowAll+"byte";
    	}
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

    function  loginr(record, rowIndex, colIndex, options){
    	if((record.type=='00'||record.type=='0') && record.SIMAllot==0){
    		return "<span class='label label-success label-xs'>成功</span>";
    	}else if((record.type=='00'||record.type=='0') && record.SIMAllot==3){
    		return '<a  class="label label-danger label-xs" href="<%=basePath %>orders/flowdealorders/list?SN='+record.SN+'"><span>没有有效订单</span></a>';
    	}else if((record.type=='00'||record.type=='0') && record.SIMAllot==4){
    		return '<a  class="label label-danger label-xs" href="<%=basePath %>sim/siminfo/index?MCC='+record.location+'"><span>没有可用卡位</span></a>';
    	}else if((record.type=='00'||record.type=='0') && record.SIMAllot==6){
    		return '<span class="label label-danger label-xs">已发生跳转</span>';
    	}else if((record.type=='00'||record.type=='0') && record.SIMAllot==7){
    		return '<span class="label label-danger label-xs">低电量接入</span>';
    	}
    	else if(record.type=='06'){
    		return '<span class="label label-danger label-xs">退出</span>';
    	}

    }
    function newStart(record, rowIndex, colIndex, options){
    	if(record.type=='06'){
    		return '';
    	}
    	//alert(record.newStart);
    	if(record.newStart=='0'){
    		return "<span class='label label-primary label-xs'>周期内</span>";
    	}else if(record.newStart=='1'){
    		return '<span class="label label-danger label-xs">重启周期</span>';
    	}else if(record.newStart=='2'){
    		return '<span class="label label-danger label-xs">限流量重启周期</span>';
    	}
    }
    function battery(record, rowIndex, colIndex, options){
    	if(record.battery==1){
    		return "<span class='label label-primary label-xs'>成功</span>";
    	}else if(record.battery==0){
    		return '<span class="label label-danger label-xs">失败</span>';
    	}
    }


    function ContextLen(record, rowIndex, colIndex, options){
    	if(record.contextLen==0){
    		return "分配";
    	}else if(record.contextLen==1){
    		return '释放';
    	}
    }


    </script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>