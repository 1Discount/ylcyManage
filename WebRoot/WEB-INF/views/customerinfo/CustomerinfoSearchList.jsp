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
    <title>客户搜索-客户管理-EASY2GO ADMIN</title>
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
   
      <jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
      <jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
     
<!-- <DIV class="panel-heading">客户信息</DIV> -->
<!-- ======================================================================================== -->
<SECTION id="main-content">
<SECTION class="wrapper">
<DIV class="col-md-12">
<DIV class="panel">
<DIV class="panel-body">
<FORM class="form-inline" id="searchForm"  method="post" action="">
<DIV class="form-group">
   <LABEL class="inline-label">用户称呼：</LABEL>
   <INPUT class="form-control" name="customerName" id="customerName" type="text" placeholder="">
<!--    <INPUT class="form-control" name="key" type="text" placeholder="电话/邮箱/昵称"> -->
</DIV>
<DIV class="form-group">
   <BUTTON class="btn btn-primary" onclick="searchurl()" type="submit">搜索</BUTTON>
</DIV>
</FORM>
<script type="text/javascript">

  function searchurl()
  {
    var data = document.getElementById('customerName').value;
//     var data_zh = encodeURI(encodeURI(data));
//     alert(data);
    var searchEndData = "<%=basePath %>customer/customerInfolist/SearchCustomerInfo?cus="+data;
        document.getElementById('searchForm').action=searchEndData;
  }
</script>
</DIV>
</DIV>
<DIV class="panel">
<DIV class="panel-heading">全部客户</DIV>
<DIV class="panel-body">
<DIV class="table-responsive">
<TABLE id="searchTable">
  <TR>
<!--     <th w_index="customerID" type="hidden" width="10%" >creatorid</th> -->
    <TH w_render="persondetail" width="20%"><b>称呼</b></TH>
<!--     <TH w_index="customerName" width="20%"><b>称呼</b></TH> -->
    <TH w_index="phone" width="13%"><b>电话/手机</b></TH>
    <TH w_index="creatorDate" width="12%"><b>创建时间</b></TH>
    <th w_index="customerSource" width="10%" ><b>客户来源</b></th>
    <th w_index="remark" width="10%" ><b>备注</b></th>
    <TH w_render="operate" width="18%"><b>操作</b></TH>
  </TR>
</TABLE>
 </DIV>
 </DIV>
 </DIV>
 </DIV>
      </SECTION>
</SECTION>
	  
<!-- ======================================================================================== -->
 
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
    var otherpage = GetQueryString("cusdata");
    var thispage = GetQueryString("cus");
    var resultdata = "";
    if(otherpage==null){
    	resultdata = encodeURI(encodeURI(thispage));
    }else if(thispage==null){
    	resultdata = encodeURI(encodeURI(otherpage));
    }
    //需将中文通过两次转码后传递到后台转码处理，可解决中文乱码问题
//     var urldata = encodeURI(encodeURI(GetQueryString("cusdata")));
//     alert(urldata);
    var gridObj;
    $(function(){
       gridObj = $.fn.bsgrid.init('searchTable',{
           url:'<%=basePath %>customer/customerInfolist/SearchResult?customerName='+resultdata,
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
   function persondetail(record, rowIndex, colIndex, options){
	return '<A style="color:#1FB5AD;" href="<%=basePath %>customer/customerInfolist/customerInfoDetail?cusid='+record.customerID+'">'+record.customerName+'</A>';
   }
   function operate(record, rowIndex, colIndex, options) {
       //'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>';
<%--        return '<A class="btn btn-primary btn-xs" href="<%=basePath %>customer/customerInfolist/customerInfoDetail?cusid='+record.customerID+'">'+ --%>
//                   '<SPAN class="glyphicon glyphicon-certificate">查看详情</SPAN>'+
//              '</A>&nbsp;&nbsp;&nbsp;&nbsp;'+
           return '<A class="btn btn-success btn-xs" href="<%=path %>/orders/ordersinfo/gotwo?customerID='+record.customerID+'">'+
           '<SPAN class="glyphicon glyphicon-plus">添加订单</SPAN>'+ '</A>&nbsp;&nbsp;&nbsp;&nbsp;'+
           '<A class="btn btn-primary btn-xs" href="<%=path %>/customer/customerInfolist/update?uid='+record.customerID+'">'+
                 '<SPAN class="glyphicon glyphicon-edit">编辑</SPAN>'+
             '</A>&nbsp;&nbsp;&nbsp;&nbsp;'+
             '<A class="btn btn-primary btn-xs" href="<%=basePath %>customer/customerInfolist/deleteCustomerInfo?sysStatus_id='+record.customerID+'">'+
                 '<SPAN class="glyphicon glyphicon-remove" onclick="return deleteCK()">删除</SPAN>'+
             '</A>';
   }
    </script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>