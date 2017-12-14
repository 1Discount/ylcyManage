<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <title>查看VIP卡-VIP卡管理-流量运营中心</title>
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
      <section id="main-content">
        <section class="wrapper">
          <%-- 这里添加页面主要内容  block content --%>
          
      <div class="col-md-12">

	  <div class="panel">
	    <div class="panel-heading">
          <div class="btn-toolbar btn-header-right"><a href="<%=basePath %>VIPCardInfo/edit?cardID=${Model.cardID}" class="btn btn-primary btn-xs"><span class="glyphicon glyphicon-edit"></span>编辑</a>
         </div>
	    
	      <h4 class="panel-title">VIP卡详细信息</h4>
	    </div>
	    <div class="panel-body">
	      <div class="table-responsive">
	        <table class="table table-bordered table-hover">
              <tr>
                <td width="10%">VIP卡号：</td>
                <td width="40%"><span class="label label-success label-xs">${Model.cardID}</span></td>
                <td width="10%">优惠方式：</td>
                <td width="40%">${Model.preferentialType}</td>
              </tr>
              
              <tr>
                <td width="10%">批次号：</td>
                <td width="40%"><span class="">${Model.batchNumber}</span></td>
                <td width="10%">有效期开始时间：</td>
                <td width="40%"><span class="">${Model.startTime}</span></td>
              </tr>
	           <tr>
                <td width="10%">是否兑换：</td>
                <td width="40%"><span class="label label-success label-xs">${Model.isExchange}</span></td>
             	 <td width="10%">有效期结束时间：</td>
                <td width="40%"><span class="">${Model.endTime}</span></td>
              </tr>
              
              <tr>
                <td width="10%">兑换时间：</td>
                <td width="40%"><span class="">${Model.exchangeTime}</span></td>
                <td width="10%">兑换手机号：</td>
                <td width="40%"><span class="">${Model.exchangeIphone}</span></td>
              </tr>  
              <tr>
                <td width="10%">卡状态：</td>
                <td width="40%"><span class="label label-success label-xs">${Model.cardStatus}</span></td>
                <td width="10%">是否已制卡：</td>
                <td width="40%"><span class="label label-success label-xs">${Model.isMakeCard}</span></td>
              </tr>  
               <tr>
                <td width="10%">备注：</td>
                <td width="40%"  colspan="3"><span class="">${Model.remark}</span></td>
                
              </tr> 
           
	        </table>
	      </div>
	      <div class="btn-toolbar"><a href="<%=basePath %>VIPCardInfo/edit?cardID=${Model.cardID}" class="btn btn-primary"><span class="glyphicon glyphicon-edit"></span>编辑</a>
	      <a href="" onclick="javascript:history.go(-1);"  class="btn btn-primary">返回</a></div>
	    </div>
	  </div>
     </div>
        </section>
      </section>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/byteunit.js"></script>


<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>