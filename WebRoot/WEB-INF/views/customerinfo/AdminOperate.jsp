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
    <title>用户操作日志-用户管理-EASY2GO ADMIN</title>
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
<DIV class="panel-heading"><b>用户操作日志</b></DIV>
<DIV class="panel-body" style="margin:0px;padding:0px;">
<!-- ======================================================================================== -->
<!--  <SECTION id="main-content"> -->
 <SECTION class="wrapper" style="margin-top:20px;">
<DIV class="col-md-12">
<DIV class="panel">
<DIV class="panel-body" style="padding-top:5px;padding-bottom:5px;">
   <FORM class="form-inline" id="searchForm"  method="get" action="#">
         <DIV class="form-group">
             <LABEL class="inline-label">操作人姓名：</LABEL>
             <INPUT class="form-control" name="creatorUserName" id="creatorUserName" type="text" placeholder="用户姓名">
         </DIV>
         <DIV class="form-group">
             <LABEL class="inline-label">操作时间：</LABEL>
             <INPUT id="order_creatorDateend" class="form-control form_datetime" name="creatorDate" type="text" data-date-format="YYYY-MM-DD">
         </DIV>
         <DIV class="form-group">
             <LABEL class="inline-label">操作类型：</LABEL>
<!--              <INPUT class="form-control" name="operateType" id="operateType" type="text" placeholder="操作类型"> -->
				<select class="form-control" name="operateType" id="operateType">
				     <option></option>
                     <c:forEach var="op" items="${operateType}">
                          <option>${op.label}</option>
                     </c:forEach>
                </select>
         </DIV>
         <DIV class="form-group">
             <BUTTON class="btn btn-primary" type="button" onclick="gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.refreshPage();">搜索</BUTTON>
         </DIV>
   </FORM>
</DIV>
</DIV>

<DIV class="panel">
<!-- <DIV class="panel-heading">用户操作日志</DIV> -->
<DIV class="panel-body">
<DIV class="table-responsive">
<TABLE id="searchTable"> 
  <TR>
    <TH w_index="operateContent"><b>操作内容</b></TH>
    <TH w_index="creatorDate" width="12%"><b>操作时间</b></TH>
    <th w_index="creatorUserName" width="8%"><b>操作人</b></th>
    <TH w_index="operateType" width="8%"><b>操作类型</b></TH>
    <th w_index="operateMenu" width="15%"><b>操作菜单</b></th>
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
    var gridObj;
    $(function(){
       gridObj = $.fn.bsgrid.init('searchTable',{
           url:'<%=basePath %>adminoperate/listall',
           // autoLoad: false,
           pageSizeSelect: true,
           pageSize: 10,
           pageSizeForGrid:[10,20,30,50,100],
           multiSort:true,
           longLengthAotoSubAndTip:false //不截取长度
       });
   });
    
    function deleteCK(){
    	if(confirm("确定要删除数据吗？"))
        {
    	return true;	
    	}return false;
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
  
  
//     function operateContent(record, rowIndex, colIndex, options){
//     	return '<A style="">'+record.operateContent+'</A>';
//        }
    
 
//    function operate(record, rowIndex, colIndex, options) {
<%--            return '<A class="btn btn-success btn-xs" href="<%=path %>/orders/ordersinfo/gotwo?customerID='+record.customerID+'">'+ --%>
//            '<SPAN class="glyphicon glyphicon-plus">添加订单</SPAN>'+ '</A>&nbsp;&nbsp;&nbsp;&nbsp;'+
<%--            '<A class="btn btn-primary btn-xs" href="<%=path %>/customer/customerInfolist/update?uid='+record.customerID+'">'+ --%>
//                  '<SPAN class="glyphicon glyphicon-edit">编辑</SPAN>'+
//              '</A>&nbsp;&nbsp;&nbsp;&nbsp;'+
<%--              '<A class="btn btn-primary btn-xs" href="<%=basePath %>customer/customerInfolist/deleteCustomerInfo?sysStatus_id='+record.customerID+'">'+ --%>
//                  '<SPAN class="glyphicon glyphicon-remove" onclick="return deleteCK()">删除</SPAN>'+
//              '</A>';
//    }
    </script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>