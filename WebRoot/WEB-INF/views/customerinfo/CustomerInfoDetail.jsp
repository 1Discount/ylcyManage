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
    <title>客户详情-客户管理-EASY2GO ADMIN</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="<%=basePath %>static/css/app.min.css?20150209">
    <meta name="csrf_token">
    <%@include file="/WEB-INF/views/common/_ie8support.html" %>
    <%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
  </head>
 <body>
   
    <section id="container">
      <jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
      <jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
     
 <SECTION id="main-content" style="">
<SECTION class="wrapper">
  <DIV class="col-md-3">
    <DIV class="panel">
        <DIV class="panel-heading"><H3 class="panel-title">客户</H3></DIV>
        <DIV class="panel-body">
  <UL class="nav nav-pills nav-stacked" role="tablist">
    <LI role="presentation"><A href="<%=path %>/customer/customerInfolist/all">全部客户</A></LI>
    <LI role="presentation"><A href="<%=path %>/customer/customerInfolist/insert">添加客户信息</A></LI>
<%--     <LI role="presentation"><A href="<%=path %>/customer/customerInfolist/OrdersInfo">他/她的所有订单</A></LI> --%>
<!--     <LI role="presentation"><A href="#">他/她的所有交易</A></LI> -->
<!--     <LI role="presentation"><A href="#">他/她的所有设备</A></LI> -->
  </UL>
       </DIV>

    </DIV>
    <DIV class="panel">
       <DIV class="panel">
            <DIV class="panel-heading"><H3 class="panel-title">搜索用户名称</H3></DIV>
            <DIV class="panel-body">
              <FORM class="form" role="form" id="searchcus" method="post" action="">
                <DIV class="form-group">
                   <INPUT class="form-control" id="customerName" name="customerName" type="text"></DIV>
                <DIV class="form-group">
                   <BUTTON class="btn btn-primary" type="submit" onclick="return searchres();">搜索</BUTTON>
                   <button type="button" onclick="javascript:history.go(-1);" class="btn btn-default">返回</button>
                </DIV>
              </FORM><span style="color:red;font-size:14px;"><b>${searchPerson}</b></span>
              <script type="text/javascript">
              function searchres(){
                var uname = document.getElementById('customerName').value;
                if(uname==""){

          	      easy2go.responseMsg.info = "客户名称不能为空!".trim();
          	      easy2go.responseMsg.warn = "".trim();
          	      easy2go.responseMsg.error = "".trim();

          	      setTimeout(function(){
          	      	easy2go.showResponseMSG();	
          	      },350);
          	      return false;
          	  }else{
                  var end = "<%=basePath %>customer/customerInfolist/SearchCustomerInfo?cusdata="+encodeURI(encodeURI(uname));
//                   alert(end);
                  document.getElementById('searchcus').action = end;
          	  }return true;
          	  
          	  
          	  
          	  

                }
              </script>
           </DIV>
      </DIV>
    </DIV>
  </DIV>
<DIV class="col-md-9">
<DIV class="panel">
<DIV class="panel-heading">查看客户信息详情</DIV>
<DIV class="panel-body">

<!-- ====================================================================== -->
 
<DIV class="col-md-12">
<DIV class="panel">
<%--<DIV class="panel-heading">客户资料</DIV> --%>
<DIV class="panel-body">
<DIV class="table-responsive">
<TABLE class="table table-bordered">
  <TBODY>
  <TR>
    <TD>名称：</TD>
    <TD><B><label readOnly="true" name="customerName" >${cus.customerName}</label></B></TD></TR>
  <TR>
    <TD>ID：</TD>
    <TD><label readOnly="true" name="customerID">${cus.customerID}</label></TD></TR>
  <TR>
    <TD>Email： </TD>
    <TD><label readOnly="true"  name="email">${cus.email}</label></TD></TR>
  <TR>
    <TD> 电话/手机：</TD>
    <TD><label readOnly="true" name="phone">${cus.phone}</label></TD></TR>
  <TR>
    <TR>
    <TD> 客户来源：</TD>
    <TD><label readOnly="true" name="customerSource">${cus.customerSource}</label></TD></TR>
  <TR>
    <TD>创建者：</TD>
    <TD><A href="#"><label readOnly="true" name="creatorUserID">${cus.creatorUserID}</label></A></TD></TR>
  <TR>
    <TD>创建时间：</TD>
    <TD><SPAN><label readOnly="true" name="creatorDate">${cus.creatorDate}</label></SPAN></TD></TR>
  <TR>
    <TD>更新时间：</TD>
    <TD><SPAN><label readOnly="true" name="creatorDate">${cus.modifyDate}</label></SPAN></TD></TR>
  <TR>
    <TD>收货地址：</TD>
    <TD>
      <P>
          <SPAN>${cus.address} </SPAN>
          <SPAN>,</SPAN>
          <SPAN>${cus.customerName}</SPAN>
          <SPAN>,</SPAN>
          <SPAN>${cus.phone}</SPAN>
       </P>
     </TD>
   </TR>
  <TR>
    <TD>备注：</TD>
    <TD><label readOnly="true" name="remark">${cus.remark}</label></TD>
  </TR>
</TBODY>
</TABLE>
</DIV>
<DIV class="btn-toolbar">
<A class="btn btn-primary" href="<%=path %>/customer/customerInfolist/update?uid=${cus.customerID}"> 
    <SPAN class="glyphicon glyphicon-edit"></SPAN>编辑
</A>
<A class="btn btn-success" href="<%=path %>/orders/ordersinfo/gotwo?customerID=${cus.customerID}"><SPAN class="glyphicon glyphicon-plus"></SPAN>添加订单</A>
<A class="btn btn-default" href="<%=path %>/customer/customerInfolist/OrdersInfo?uid=${cus.customerID}">TA的全部订单</A>
<%-- <A class="btn btn-default" href="<%=path %>/customer/customerInfolist/OrdersInfo?uid=${cus.customerID}">TA的全部订单</A> --%>
<%-- <A class="btn btn-default" href="<%=path %>/customer/customerInfolist/OrdersInfo/DeviceInfo">TA的全部设备</A> --%>
<A class="btn btn-default" href="<%=path %>/customer/customerInfolist/ordersfloworder?uid=${cus.customerID}">TA的数据服务交易</A>
<A class="btn btn-default" href="<%=path %>/customer/customerInfolist/DeviceInfo?uid=${cus.customerID}">TA的设备交易</A>
</DIV>
</DIV>
</DIV>
</DIV>
 
<!-- ======================================================================================= -->

</DIV>
</DIV>
</DIV>
</SECTION>
</SECTION>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
    <script type="text/javascript">
      $(function(){
      });
    </script>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>