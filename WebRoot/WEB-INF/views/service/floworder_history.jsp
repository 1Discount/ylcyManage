<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>
  <head>
    <title>历史流量订单-客服监控中心-EASY2GO ADMIN</title>
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
      <section id="main-content">
        <section class="wrapper">
          <%-- 这里添加页面主要内容  block content --%>          
		<div class="col-md-12">
		<div class="panel">
		<div class="panel-body">
		<form class="form-inline" id="searchForm" role="form" method="get" action="#">
        <div class="form-group">
            <label class="inline-label">设备机身码：</label><input class="form-control" name="SN" type="text" placeholder=""  id="SN"></div>
        <div class="form-group">
            <label class="inline-label">客户名称：</label><input class="form-control" name="customerName" type="text" placeholder="" ></div>
        <div class="form-group">
          <label class="inline-label">国&nbsp;家：</label>
          <select name="userCountry" style="width: 179px;" class="form-control">
            <option value="">全部国家</option>
            <c:forEach items="${Countries}" var="country" varStatus="status"><option value="${country.countryCode}">${country.countryName}</option></c:forEach>
          </select>
        </div>
		<div class="form-group">
		<button class="btn btn-primary" 
		type="button" onclick="$('input').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.page(1);">搜索</button>
		<button class="btn btn-default" onclick="refresh();" type="button">重置刷新</button>
		</div></form></div></div>
		
		<div class="panel">
		<div class="panel-heading">
		<h3 class="panel-title">过去七日使用过的流量订单</h3></div>
		<div class="panel-body">
		<div class="table-responsive"><table id="searchTable"><tr>
                <th w_render="render_flowOrderName" width="10%;">订单名称</th>
                <th w_render="render_customerName"  width="10%;">客户名称</th>
                <th w_render="render_orderID"  width="10%;">关联订单</th>
                <th w_index="activateDate"  width="10%;">激活时间</th>
                <th w_index="panlUserDate"  width="10%;">预约开始使用时间</th>
                <th w_index="beginDate"  width="10%;">实际开始使用时间</th>
                <th w_index="flowExpireDate"  width="10%;">订单到期时间</th>
                <th w_index="orderStatus"  width="5%;">订单状态</th>
                
                <th w_render="render_SN"  width="5%;">机身码</th>
<%--                <th w_index="IMSI"  width="5%;">IMSI</th>--%>
<%--                <th w_index="ICCID"  width="5%;">ICCID</th>--%>
<%--                <th w_render="render_flowStatus"  width="5%;">流量状态</th>--%>
<%--                <th w_index="flowUsed"  width="5%;">已使用流量</th> 目前未统计, 先去掉--%>
<%--                <th w_index="limitSpeed"  width="5%;">限速状态</th>--%>
		</tr></table></div>
		</div></div>
<div id="special-note">备注：<br><br>
<p>该界面显示7天内用户设备接入过的流量订单</p>
</div>
		</div>
          
        </section>
      </section>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
    <script type="text/javascript">
   
    	  var gridObj;
          $(function(){
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath %>service/center/pageFlowOrder?range=history',
                 // autoLoad: false,
                 pageSizeSelect: true,
                 pageSize: 20,
                 pageSizeForGrid:[10,20,30,50,100],
                 multiSort:false
             });
         });

          function render_flowOrderName(record, rowIndex, colIndex, options) {
              var appendString;
              if (null != record.flowPlanID && record.flowPlanID.length > 0) {
                  // 若不是自定义套餐, 最好显示原套餐名称
                  appendString = "";
              } else {
                  appendString = ""; //' <span class="label label-success label-xs" style="padding:3px;">自定义</span>';
              }
              return '<a title="查看订单详情" href="<%=basePath %>orders/flowdealorders/info?flowDealID=' + record.flowDealID + '"><span>' + record.userCountry + record.flowDays + '日套餐' + '</span>' + appendString + '</a>';
           }
          function render_SN(record, rowIndex, colIndex, options) {
              return '<a title="查看设备详情" href="<%=basePath %>device/deviceInfodetail?deviceid=' + record.SN + '"><span>' + record.SN + '</span></a>';
           }
          function render_customerName(record, rowIndex, colIndex, options) {
              return '<a title="查看用户详情" href="<%=basePath %>customer/customerInfolist/customerInfoDetail?cusid=' + record.customerID + '"><span>' + record.customerName + '</span></a>';
           }
          function render_flowStatus(record, rowIndex, colIndex, options) {
              return "正常";
          }
          function render_beginDate(record, rowIndex, colIndex, options) {
              return record.beginDate;
          }
          function render_orderID(record, rowIndex, colIndex, options) {
              return '<a href="<%=basePath %>orders/ordersinfo/orderinfo?ordersID=' + record.orderID + '"><span>查看</span></a>';
          }
         function operate(record, rowIndex, colIndex, options) {
<%--			return '<div class="btn-toolbar">'--%>
<%--+ '<a class="btn btn-primary btn-xs" href="#"><span class="glyphicon glyphicon-info-sign">详情</span></a>'--%>
<%--+ '</div>';--%>
         }
         
         function refresh(){
             $("#searchForm input[name='SN']").val('');
             $("#searchForm input[name='customerName']").val('');
             $("#searchForm select option").eq(0).attr("selected","true");
             gridObj.options.otherParames =$('#searchForm').serializeArray();
             gridObj.refreshPage();
          }         
	</script>

<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>