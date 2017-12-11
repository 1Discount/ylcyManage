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
<DIV class="panel-body" style="margin:0px;padding:0px;">
<!-- ======================================================================================== -->
<!--  <SECTION id="main-content"> -->
 <SECTION class="wrapper" style="margin-top:20px;">
<DIV class="col-md-12">
<%-- <DIV class="panel">
<DIV class="panel-body" style="padding-top:5px;padding-bottom:5px;">
   <FORM class="form-inline"  method="get" action="<%=basePath %>customer/customerInfolist/getSearchCustomerinfolist">
   <FORM class="form-inline" id="searchForm"  method="get" action="#">
         <DIV class="form-group">
             <LABEL class="inline-label">设备机身码：</LABEL>
             <INPUT class="form-control" name="SN" id="SN" type="text" placeholder="机身码">
         </DIV>
         <DIV class="form-group">
             <LABEL class="inline-label">在线日期：</LABEL>
             <INPUT id="order_creatorDateend" class="form-control form_datetime" name="lastTime" type="text" data-date-format="YYYY-MM-DD">
         </DIV>
         <DIV class="form-group">
             <BUTTON class="btn btn-primary" type="button" onclick="gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.refreshPage();">搜索</BUTTON>
         </DIV>
   </FORM>
</DIV>
</DIV> --%>

<DIV class="panel">
<DIV class="panel-heading">IMSI分配到的机身码的历史记录</DIV>
<DIV class="panel-body">
<DIV class="table-responsive">
<TABLE id="searchTable">
  <TR>
    <th w_index="IMSI"><b>流量卡号</b></th>
    <th w_index="userCountry"><b>归属地</b></th>
    <th w_index="SN"><b>设备号</b></th>
    <th w_index="lastUpdateDate"><b>分配时间</b></th>
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
    
    function GetQueryString(name)//获取地址栏参数的值
    {
         var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
         var r = window.location.search.substr(1).match(reg);
         if(r!=null)return  unescape(r[2]); return null;
         
    }
    
    var gridObj;
    $(function(){
       gridObj = $.fn.bsgrid.init('searchTable',{
           url:'<%=basePath %>service/center/listimsilogs?imsi='+GetQueryString("imsi"),
           // autoLoad: false,
           pageSizeSelect: true, 
           pageSize: 10,
           pageSizeForGrid:[10,20,30,50,100],
           multiSort:true
       });
   });
    
   
   <%--  function customerName(record, rowIndex, colIndex, options){
    	return '<A style="color:#1FB5AD;" href="<%=basePath %>customer/customerInfolist/customerInfoDetail?cusid='+record.customerID+'">'+record.customerName+'</A>';
       }
    function IMSI(record, rowIndex, colIndex, options){
    	return '<A style="color:#1FB5AD;" href="<%=basePath %>customer/customerInfolist/customerInfoDetail?imsi='+record.IMSI+'">'+record.IMSI+'</A>';
       }
    function SN(record, rowIndex, colIndex, options){
    	return '<A style="color:#1FB5AD;" href="<%=basePath %>service/center/devicelogs?sbid='+record.SN+'">'+record.SN+'</A>';
       } --%>
    </script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>