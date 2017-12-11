<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <title><c:if test="${IsIndexView}">全部套餐</c:if><c:if test="${IsTrashView}">全部已删除套餐</c:if>-套餐管理-EASY2GO ADMIN</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="<%=basePath %>static/css/app.min.css?20150209">
    <link rel="stylesheet" href="<%=basePath %>static/css/grid/bsgrid.all.min.css">
    <meta name="csrf_token">
    <%@include file="/WEB-INF/views/common/_ie8support.html" %>
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
			<label class="inline-label">套餐名：</label><input class="form-control" name="flowPlanName" type="text" placeholder="" ></div>
		<div class="form-group">
			<label class="inline-label">创建者：</label><input class="form-control" name="creatorUserName" type="text" placeholder="" ></div>
        <div class="form-group">
          <label class="inline-label">国&nbsp;家：</label>
          <select name="countryList" style="width: 179px;" class="form-control">
            <option value="">全部国家</option>
            <c:forEach items="${Countries}" var="country" varStatus="status"><option value="${country.countryCode}">${country.countryName}</option></c:forEach>
          </select>
        </div>
		<div class="form-group">
		<button class="btn btn-primary" 
		type="button" onclick="$('input').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.page(1);">搜索</button></div></form></div></div>
		<div class="panel">
		<div class="panel-heading">
		<div class="btn-toolbar btn-header-right"><a class="btn btn-success btn-xs" href="<%=basePath %>flowplan/flowplaninfo/new"><span class="glyphicon glyphicon-plus"></span> 添加套餐</a></div>
		<h3 class="panel-title">套餐列表</h3></div>
		<div class="panel-body">
		<div class="table-responsive"><table id="searchTable">
		        <tr>
		        	<th w_index="flowPlanID" w_hidden="true" width="10%;">套餐ID</th>
		            <th w_render="render_flowPlanName" width="10%;">套餐名</th>
		            <th w_render="render_planPrice" width="10%;">价格(元)</th>
		            <th w_index="countryList" width="10%;">国家</th>
		            <th w_index="planType" width="10%;">套餐类型</th>		            
		            <th w_render="render_planDays" width="10%;">天数</th>
		            <th w_render="render_validPeriod" width="10%;">有效天数</th>
		            <th w_index="creatorUserName" width="10%;">创建者</th>
		            <th w_index="creatorDate" width="10%;">创建时间</th>
		            <th w_render="operate" width="10%;">操作</th>
		        </tr>
		</table></div>
		<div class="panel-body">
			<div class="row">
				<div class="well">
					<span id="msg"
						style="border: 0px solid pink; font-size: 14px; color: red;"></span>
				</div>
			</div>
		</div>
		</div></div></div>
          
        </section>
      </section>
    </section>
<script src="<%=basePath %>static/js/app.min.js?20150209"></script>
<script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
<script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
<script src="<%=basePath %>static/js/accounting/accounting.min.js"></script>
<script src="<%=basePath %>static/js/byteunit.js"></script>
<script src="<%=basePath %>static/js/bootbox.min.js"></script>
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
	
	function render_flowPlanName(record, rowIndex, colIndex, options) {
       return '<a href="<%=basePath %>flowplan/flowplaninfo/view/' + record.flowPlanID + '"><span>' + record.flowPlanName + '</span></a>';
    }
	
    function render_planPrice(record, rowIndex, colIndex, options) {
       return accounting.formatMoney(record.planPrice); //'￥' + record.planPrice;
    }
    
    function render_flowSum(record, rowIndex, colIndex, options) {
       if (record.planType == "流量") {
        return '<a class="btn btn-success btn-xs" onclick="return;" title="' + record.flowSum + '"><span>' + prettyByteUnitSize(record.flowSum,2,2,true) + '</span></a>';
       } else {
        return '0';
       }       
    }
    
    function render_flowAlert(record, rowIndex, colIndex, options) {
       return record.flowAlert + 'MB';
    }
    
    function render_speedLimit(record, rowIndex, colIndex, options) {
       return record.speedLimit + 'KB/s';
    }
    
    function render_planDays(record, rowIndex, colIndex, options) {       
       if (record.planType == "天数") {
        return '<a class="btn btn-success btn-xs" onclick="return;"><span>' + record.userDayList + '天' + '</span></a>';
       } else {
        return '0';
       }    
    }
    
    function render_validPeriod(record, rowIndex, colIndex, options) {
       return record.validPeriod + '天';
    }
    
</script>
<c:if test="${IsIndexView}">
<%-- 区分套餐回收站和正常套餐的操作 --%>
<script type="text/javascript">
    	  var gridObj;
          $(function(){
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath %>flowplan/flowplaninfo/datapage',
                 pageSizeSelect: true,
                 pageSize: 10,
                 pageSizeForGrid:[10,20,30,50,100],
                 multiSort:true
             });
         });
         
         function operate(record, rowIndex, colIndex, options) {
			return '<div class="btn-toolbar">'
				+ '<a class="btn btn-primary btn-xs" href="<%=basePath %>flowplan/flowplaninfo/view/' + record.flowPlanID + '"><span class="glyphicon glyphicon-info-sign">详情</span></a>'
				+ '<a class="btn btn-primary btn-xs" href="<%=basePath %>flowplan/flowplaninfo/edit/' + record.flowPlanID + '"><span class="glyphicon glyphicon-edit">编辑</span></a>'
				+ '<a class="btn btn-primary btn-xs" href="#" onclick="bindSN(\'' + record.flowPlanID + '\') "><span class="glyphicon glyphicon-lock">绑定设备</span></a>'				
				+ '<a class="btn btn-danger btn-xs" href="#" onclick="op_delete(\'' + record.flowPlanID + '\')"><span class="glyphicon glyphicon-remove">删除</span></a>'
				+ '</div>';
            }
		function bindSN(id){
			bootbox.dialog({
	             title: "绑定设备",
	             message:'<div class="row">  ' +
	                '<div class="col-md-12"> ' +
	                '<form class="form-horizontal form-inline"  id="form" mothod="post" style="text"> ' +
	                '<label class="col-md-3 control-label">请输入机身码:</label> ' +
	                '<input name="SN" type="text" value="" class="form-control"  style="width:350px;" placeholder="请输入机身码后六位，多个用/隔开"  > ' +
	                '</form></div></div>',
	             buttons: {
	                 cancel: {
	                     label: "取消",
	                     className: "btn-default",
	                     callback: function () {}
	                 },
	                 success: {
	                     label: "确定",
	                     className: "btn-success edit-button-ok",
	                     callback: function () {
	                    	 $.ajax({
	         					type:"POST",
	         					url:"<%=basePath %>flowplan/flowplaninfo/bindSN?flowPlanID="+id,
	         					dataType:"JSON",
	         					data:$('#form').serialize(),
	         					success:function(data){
	         						if(data.Code=="00"){
	         							easy2go.toast('info',data.Msg);
	         						}else if(data.Code=="02"){
	         							easy2go.toast('warn',data.Msg);
	         						}else if(data.Code="01"){
	         							$("#msg").html(data.Msg);
	         						}
	         					},
	         					error:function(data){
	         						alert(1);
	         					}
	         				});
	                     }
	                 },
	             }
			});
		}
         function op_delete(id) {
            if(confirm("确认删除套餐?")) {
				$.ajax({
					type:"POST",
					url:"<%=basePath %>flowplan/flowplaninfo/delete/" + id,
					success:function(data){
						easy2go.toast('info', data);
						gridObj.refreshPage();
					}
				});
			}
         }
</script>
</c:if>
<c:if test="${IsTrashView}"><%-- 区分套餐回收站和正常套餐的操作 --%>
    <script type="text/javascript">
    	  var gridObj;
          $(function(){
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath %>flowplan/flowplaninfo/trashdatapage',
                 pageSizeSelect: true,
                 pageSize: 10,
                 pageSizeForGrid:[10,20,30,50,100],
                 multiSort:true
             });
         });
         
         function operate(record, rowIndex, colIndex, options) {
			return '<div class="btn-toolbar">'
					+ '<a class="btn btn-primary btn-xs" href="<%=basePath %>flowplan/flowplaninfo/view/' + record.flowPlanID + '"><span class="glyphicon glyphicon-info-sign">详情</span></a>'
					+ '<a class="btn btn-primary btn-xs" href="<%=basePath %>flowplan/flowplaninfo/edit/' + record.flowPlanID + '"><span class="glyphicon glyphicon-edit">编辑</span></a>'
					+ '<a class="btn btn-danger btn-xs" href="#" onclick="op_restore(\'' + record.flowPlanID + '\')"><span class="glyphicon glyphicon-remove">恢复</span></a>'
					+ '</div>';
         }
         
         function op_restore(id) {
            if(confirm("确认恢复套餐?")) {
				$.ajax({
					type:"POST",
					url:"<%=basePath %>flowplan/flowplaninfo/restore/" + id,
					success:function(data){
						easy2go.toast('info', data);
						gridObj.refreshPage();
					}

				});
			}
         }
</script>
</c:if>

<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>