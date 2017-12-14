<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <title>订单统计-统计报表-流量运营中心</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="<%=basePath %>static/css/app.min.css?20150209">
    <link rel="stylesheet" href="<%=basePath %>static/css/grid/bsgrid.all.min.css">
    <meta name="csrf_token">
    <%@include file="/WEB-INF/views/common/_ie8support.html" %>
    <%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
  </head>
  <body>
    <section id="container">
      <jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
      <jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
      <SECTION id="main-content">
      <SECTION class="wrapper">
<DIV class="col-md-12">
<DIV class="panel">
<DIV class="panel-body">
<FORM class="form-inline" id="searchForm" role="form" method="get" action="#">
<DIV class="form-group"><LABEL class="inline-label">客户名称：</LABEL><INPUT 
class="form-control" id="order_customerName" name="customerName" type="text" 
placeholder="客户名称"></DIV>
<DIV class="form-group"><LABEL class="inline-label">订单来源：</LABEL>
<select id="slect_mg" name="orderSource"class="form-control">
	<option value="">所有</option>
	<c:forEach var="dic" items="${diclist}">
		<option value="${dic.label }">${dic.label }</option>
	</c:forEach>
	
		<option value="渠道商">渠道商</option>
</select>
</DIV>

<DIV class="form-group"><LABEL class="inline-label">渠道商：</LABEL>

<select id="distributorName" name="company" class="form-control form_datetime">
		<option value="">全部</option>
	<c:forEach items="${distributors}" var="dis">
		<option value="${dis.company }">${dis.company}</option>
	</c:forEach>
</select></DIV>
<DIV class="form-group"><LABEL class="inline-label">时间段：</LABEL>
<INPUT id="order_creatorDatebegin" class="form-control form_datetime" 
name="begindate" type="text" 
data-date-format="YYYY-MM-DD HH:mm:ss"></DIV>

<DIV class="form-group"><LABEL class="inline-label">到</LABEL>
<INPUT id="order_creatorDateend" class="form-control form_datetime" 
name="enddate" type="text" 
data-date-format="YYYY-MM-DD HH:mm:ss"></DIV>

<DIV class="form-group"><BUTTON class="btn btn-primary" 
type="button" onclick="gridObj.options.otherParames =$('#searchForm').serializeArray();gridObj.refreshPage();">搜索</BUTTON>
<BUTTON class="btn btn-primary" onclick="re();" type="button">刷新</BUTTON>
</DIV></FORM></DIV></DIV>
<DIV class="panel">
<DIV class="panel-heading">
<H3 class="panel-title">所有订单</H3></DIV>
<DIV class="panel-body">
<table id="searchTable">
        <tr>
            <th w_index="sum" width="10%;">订单总数</th>
            <th w_index="deviceDealCount" width="10%;">设备交易数</th>
            <th w_index="flowDealCount" width="10%;">流量交易数</th>
<!--             <th w_index="del" width="10%;">已删除订单数</th> -->
            <th w_index="sumnormal" width="10%;">正常订单总金额</th>
        </tr>
</table>
</DIV></DIV></DIV></SECTION></SECTION></section>

<div id="myModal" class="modal fade" >
<div class="modal-dialog">
<div class="modal-content">
<!-- dialog body -->
<div class="modal-body">
<button type="button" class="close" data-dismiss="modal">&times;</button>
Hello world!
</div>
<!-- dialog buttons -->
<div class="modal-footer"><button type="button" class="btn btn-primary">保存</button><button type="button" class="btn btn-primary">取消</button></div>
</div>
</div>
</div>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <script src="<%=basePath %>static/js/bootbox.min.js"></script>
    <SCRIPT type="text/javascript">
    bootbox.setDefaults("locale","zh_CN");
    	  var gridObj;
          $(function(){
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath %>orders/ordersinfo/ordercount',
                 pageAll:true
             });
             
             $("#order_creatorDatebegin").datetimepicker({
         		pickDate: true,                 //en/disables the date picker
         		pickTime: true,                 //en/disables the time picker
         		showToday: true,                 //shows the today indicator
         		language:'zh-CN',                  //sets language locale
         		defaultDate: moment().add(-3, 'months'),                 //sets a default date, accepts js dates, strings and moment objects
         	});
             
             $("#order_creatorDateend").datetimepicker({
          		pickDate: true,                 //en/disables the date picker
          		pickTime: true,                 //en/disables the time picker
          		showToday: true,                 //shows the today indicator
          		language:'zh-CN',                  //sets language locale
          		defaultDate: moment().add(0, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
          	});
         });
         
          
          //自定义列
         function operate(record, rowIndex, colIndex, options) {
             //'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>';
              return '<A class="btn btn-primary btn-xs"   href="<%=basePath %>orders/ordersinfo/edit?orderID='+record.orderID+'"><SPAN class="glyphicon glyphicon-edit">编辑</SPAN></A>&nbsp;&nbsp;&nbsp;&nbsp;<A class="btn btn-primary btn-xs"  onclick="deletebyid('+rowIndex+')"><SPAN class="glyphicon glyphicon-remove">删除</SPAN></A>';
         }
         
         function orderidOP(record, rowIndex, colIndex, options) {
        	 return '<A  href="<%=basePath %>orders/ordersinfo/orderinfo?ordersID='+record.orderID+'">'+record.orderID+'</A>';
         }
         
         function re(){
         	$("#order_customerName").val('');
         	gridObj.options.otherParames =$('#searchForm').serializeArray();
         	gridObj.refreshPage();
         }
        
         
</SCRIPT>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
<jsp:param name="matchType" value="exactly" />
</jsp:include>
  </body>
</html>