<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>
  <head>
    <title><c:if test="${initCategory eq null or initCategory eq 0}">全部本地SIM卡充值记录</c:if><c:if test="${initCategory eq 1}">全部漫游SIM卡充值记录</c:if>-SIM卡充值管理-流量运营中心</title>
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
        <div class="row">
            <div class="col-md-3">
                  <div class="mini-stat clearfix"><a href="<%=basePath %>sim/simrechargebill/summary"><span class="mini-stat-icon pink"><i class="fa fa-bar-chart-o"></i></span>
                      <div class="mini-stat-info"><span style="font-size: 20px;">SIM卡充值账单统计</span></div></a></div>
            </div>
        </div>
		<div class="panel">
		<div class="panel-body">
		<form class="form-inline" id="searchForm" role="form" method="get" action="#">
		<div class="form-group">
			<label class="inline-label">IMSI：</label><input class="form-control" id="IMSI" name="IMSI" type="text" placeholder="IMSI" ></div>
        <div class="form-group">
          <label class="inline-label">国&nbsp;家：</label>
          <select name="MCC" style="width: 130px;"
          class="form-control">
            <option value="">全部国家</option>
            <c:forEach items="${Countries}" var="country" varStatus="status"><option value="${country.countryCode}">${country.countryName}</option></c:forEach>
          </select>
        </div>
		<div class="form-group">
		<button class="btn btn-primary" 
		type="button" onclick="$('input').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.page(1);"><c:if test="${initCategory eq null or initCategory eq 0}">搜索本地卡</c:if><c:if test="${initCategory eq 1}">搜索种子卡</c:if></button></div></form></div></div>
		<div class="panel">
		<div class="panel-heading">
		
		<h3 class="panel-title"><c:if test="${initCategory eq null or initCategory eq 0}">全部本地SIM卡充值记录</c:if><c:if test="${initCategory eq 1}">全部漫游SIM卡充值记录</c:if></h3></div>
		<div class="panel-body">
		<div class="table-responsive"><table id="searchTable">
		        <tr>
		    <th w_index="rechargeBillID" w_hidden="true">充值记录ID</th>
            <th w_index="countryName">国家</th>
            <th w_index="operatorName">运营商</th>
            <th w_render="render_imsi">IMSI</th>
            <th w_render="render_SIMinfoID">SIM卡ID</th>
            <th w_render="render_rechargeAmount">充值金额</th>
            <th w_index="creatorUserName" width="5%;">创建者</th>
            <th w_index="creatorDate">充值日期</th>
            <th w_index="rechargedValidDate">充值后有效期</th>
            <th w_render="operate" width="10%;">操作</th>
		        </tr>
		</table></div>
		</div></div></div>
          
        </section>
      </section>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <script src="<%=basePath %>static/js/accounting/accounting.min.js"></script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
    <script type="text/javascript">
    accounting.settings = { // 参数的默认设置:
        currency: {
            symbol : "￥",   //默认的货币符号 '$'
            format: "%s%v", // 输出控制: %s = 符号, %v = 值或数字 (can be object: see below)
            decimal : ".",  // 小数点分隔符
            thousand: ",",  // 千位分隔符
            precision : 2   // 小数位数
        },
        number: {
            precision : 0,  // 精度，默认的精度值为0
            thousand: ",",
            decimal : "."
        }
    };
    </script>
<c:if test="${IsIndexView}"><%-- 区分回收站记录和正常记录的操作 --%>
    <script type="text/javascript">
    	  var gridObj;
          $(function(){
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath %>sim/simrechargebill/datapage<c:if test="${initCategory eq null or initCategory eq 0}">?initCategory=0</c:if><c:if test="${initCategory eq 1}">?initCategory=1</c:if>',
                 // autoLoad: false,
                 pageSizeSelect: true,
                 pageSize: 10,
                 pageSizeForGrid:[10,20,30,50,100],
                 multiSort:true
             });
         });
         
         function render_imsi(record, rowIndex, colIndex, options) {
            return '<a href="<%=basePath %>sim/simrechargebill/new?SIMinfoID=' + record.SIMinfoID + '&SIMCategory=' + record.SIMCategory + '" title="点击查看该卡充值记录"><span>' + record.IMSI + '</span></a>';
         }
         function render_SIMinfoID(record, rowIndex, colIndex, options) {
            return '<a href="<%=basePath %>sim/siminfo/view/' + record.SIMinfoID + '" title="点击查看该卡详情"><span>' + record.SIMinfoID.substring(0,8) + '</span></a>';
         }
	    function render_rechargeAmount(record, rowIndex, colIndex, options) {
	       return accounting.formatMoney(record.rechargeAmount);
	    }
         function operate(record, rowIndex, colIndex, options) {
<%--			//'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>'; --%>
			return '<div class="btn-toolbar">'
+ '<a class="btn btn-primary btn-xs" href="<%=basePath %>sim/simrechargebill/edit/' + record.rechargeBillID + '"><span class="glyphicon glyphicon-edit">编辑</span></a>'
+ '</div>'; <%-- + '<a class="btn btn-primary btn-xs" href="<%=basePath %>sim/simrechargebill/view/' + record.rechargeBillID + '"><span class="glyphicon glyphicon-info-sign">详情</span></a>' --%>
         }
         
	</script>
</c:if>
<c:if test="${IsTrashView}"><%-- 区分回收站记录和正常记录的操作 --%>
    <script type="text/javascript">
    	  var gridObj;
          $(function(){
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath %>sim/simrechargebill/trashdatapage',
                 // autoLoad: false,
                 pageSizeSelect: true,
                 pageSize: 10,
                 pageSizeForGrid:[10,20,30,50,100],
                 multiSort:true
             });
         });
         
         function operate(record, rowIndex, colIndex, options) {
<%--			//'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>'; --%>
			return '<div class="btn-toolbar">'
+ '<a class="btn btn-primary btn-xs" href="<%=basePath %>sim/simrechargebill/edit/' + record.rechargeBillID + '"><span class="glyphicon glyphicon-edit">编辑</span></a>'
+ '<a class="btn btn-danger btn-xs" href="#" onclick="op_restore(\'' + record.rechargeBillID + '\')"><span class="glyphicon glyphicon-remove">恢复</span></a>'
+ '</div>'; <%-- + '<a class="btn btn-primary btn-xs" href="<%=basePath %>sim/simrechargebill/view/' + record.rechargeBillID + '"><span class="glyphicon glyphicon-info-sign">详情</span></a>' --%>
         }
         
         function op_restore(id) {
            if(confirm("确定恢复吗?")) {
				$.ajax({
					type:"POST",
					url:"<%=basePath %>sim/simrechargebill/restore/" + id,
					// dataType:"html",
					// data:$('#plan_form').serialize(),
					success:function(data){
						easy2go.toast('info', data);
						gridObj.refreshPage();
					}

				});
			}
         }
         
	</script>
</c:if>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
<jsp:param name="matchType" value="backend" />
</jsp:include>
  </body>
</html>