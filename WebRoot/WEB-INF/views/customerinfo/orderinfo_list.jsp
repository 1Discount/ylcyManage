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
    <title>查看用户所有订单-客户管理-EASY2GO ADMIN</title>
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
     
 
<!-- ======================================================================================== -->
 
   <SECTION 
id="main-content"><SECTION class="wrapper">
<DIV class="col-md-3">
<DIV class="panel">
<DIV class="panel-heading">
<H3 class="panel-title">客户</H3></DIV>
<DIV class="panel-body">
<UL class="nav nav-pills nav-stacked" role="tablist">

    <LI role="presentation"><A href="<%=path %>/customer/customerInfolist/all">全部客户</A></LI>
    <LI role="presentation"><A href="<%=path %>/customer/customerInfolist/insert">添加客户信息</A></LI>
    <LI role="presentation"><A href="<%=path %>/customer/customerInfolist/OrdersInfo">他/她的所有订单</A></LI>
<%--     <LI role="presentation"><A href="<%=path %>/customer/customerInfolist/customerInfoDetail?cusid=">客户信息</A></LI> --%>
    <LI role="presentation"><A href="#">他/她的所有交易</A></LI>
    <LI role="presentation"><A href="#">他/她的所有设备</A></LI>
  
  </UL></DIV></DIV>
<DIV class="panel">
<DIV class="panel-heading">
<H3 class="panel-title">搜索</H3></DIV>
<DIV class="panel-body">
<FORM class="form" role="form" method="get" action="#">
<DIV class="form-group"><INPUT class="form-control" name="q" type="text"></DIV>
<DIV class="form-group"><BUTTON class="btn btn-primary" 
type="submit">搜索</BUTTON></DIV></FORM></DIV></DIV></DIV>
<DIV class="col-md-9">
<DIV class="panel">
<DIV class="panel-heading"> </DIV>
<DIV class="panel-body">
<DIV class="table-responsive">
<TABLE class="table table-bordered table-hover">
  <THEAD>
  <TR>
    <TH>ID</TH>
    <TH>总额</TH>
    <TH>创建者</TH>
    <TH>创建时间</TH>
    <TH>最后更新</TH>
    <TH>操作</TH>
    </TR>
  </THEAD>
  <TBODY>
  
  <c:forEach var="ordersinfo" items="${ordersinfo}" varStatus="count">
  
  <TR>
    <TD><A href="#">${ordersinfo.orderID}</A></TD>
    <TD><SPAN class="num">${ordersinfo.orderAmount}</SPAN></TD>
    <TD><A href="#">${ordersinfo.customerName}</A></TD>
    <TD>${ordersinfo.creatorDate}</TD>
    <TD></TD>
    <TD>
      <DIV class="btn-toolbar">
        <A class="btn btn-success btn-xs" href="#"> 
           <SPAN class="glyphicon glyphicon-eye-open"></SPAN>查看
        </A>
        <A class="btn btn-primary btn-xs"  href=""> 
           <SPAN class="glyphicon glyphicon-edit"></SPAN>编辑
        </A>
        <A class="btn btn-danger btn-xs" href=""> 
           <SPAN class="glyphicon glyphicon-remove"></SPAN>删除
        </A>
      </DIV>
    </TD>
   </TR>
  
  </c:forEach>
  
      </TBODY></TABLE></DIV></DIV></DIV></DIV></SECTION></SECTION>
 
<!-- ======================================================================================== -->
 
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <script type="text/javascript">
    var gridObj;
    $(function(){
       gridObj = $.fn.bsgrid.init('searchTable',{
           url:'<%=basePath %>customer/customerInfolist/customerinfodatapage',
           // autoLoad: false,
           pageSizeSelect: true,
           pageSize: 10,
           pageSizeForGrid:[10,20,30,50,100],
           multiSort:true
       });
   });
    
    function deleteCK(){
    	if(confirm("确定要删除数据吗？"))
        {
    	return true;	
    	}return false;
    }
   
   function operate(record, rowIndex, colIndex, options) {
       //'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>';
       return '<A class="btn btn-primary btn-xs" href="<%=basePath %>customer/customerInfolist/customerInfoDetail?cusid='+record.customerID+'">'+
                  '<SPAN class="glyphicon glyphicon-certificate">查看详情</SPAN>'+
             '</A>&nbsp;&nbsp;&nbsp;&nbsp;'+
             '<A class="btn btn-primary btn-xs" href="<%=path %>/customer/customerInfolist/update?uid='+record.customerID+'">'+
                 '<SPAN class="glyphicon glyphicon-edit">编辑</SPAN>'+
             '</A>&nbsp;&nbsp;&nbsp;&nbsp;'+
             '<A class="btn btn-primary btn-xs" href="<%=basePath %>customer/customerInfolist/deleteCustomerInfo?sysStatus_id='+record.customerID+'">'+
                 '<SPAN class="glyphicon glyphicon-remove" onclick="return deleteCK()">删除</SPAN>'+
             '</A>'
   }
    </script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>