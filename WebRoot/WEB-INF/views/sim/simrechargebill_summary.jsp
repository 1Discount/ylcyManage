<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>
  <head>
    <title>SIM卡充值帐单统计-EASY2GO ADMIN</title>
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
		<div class="panel-heading">
		<h3 class="panel-title">请先选取范围</h3></div>
		<div class="panel-body">
		<form class="form-inline" id="searchForm" role="form" method="get" action="#">
        <div class="form-group">
          <label class="inline-label">SIM卡使用类型：</label>
          <select id="SIMCategory" name="SIMCategory" style="width: 130px;" class="form-control">
            <option value="本地卡">本地卡</option>
            <option value="漫游卡">漫游卡</option>
          </select>
        </div>
        <div class="form-group">
          <label class="inline-label">国&nbsp;家：</label>
          <select id="select_country" name="MCC" style="width: 130px;"
          class="form-control">
            <option value="">全部国家</option>
            <c:forEach items="${Countries}" var="country" varStatus="status"><option value="${country.countryCode}">${country.countryName}</option></c:forEach>
          </select>
        </div>
        <div class="form-group">
          <label class="inline-label">运营商：</label>
          <select id="operatorName" name="operatorName" style="width: 179px;" class="form-control">
            <option value="">全部运营商</option>
            <c:forEach items="${Operators}" var="item" varStatus="status"><option value="${item.operatorName}">${item.countryName}-${item.operatorName}</option></c:forEach>
          </select>
        </div>
		<br /><div class="form-group"><label class="inline-label">时间段：</label>
		
		<label for="dateRangeType0" class="radio-inline">
                  <input type="radio" name="dateRangeType" id="dateRangeType0" value="all" checked>全部时间</label>
        <label for="dateRangeType1" class="radio-inline">
                  <input type="radio" name="dateRangeType" id="dateRangeType1" value="selected">选取特定时间: </label>
                  
		<input id="begindate" class="form-control form_datetime" 
		name="begindate" type="text"
		data-date-format="YYYY-MM-DD HH:mm:ss"></div>
		
		<div class="form-group"><label class="inline-label">到</label>
		<input id="enddate" class="form-control form_datetime" 
		name="enddate" type="text"
		data-date-format="YYYY-MM-DD HH:mm:ss"></div>
		<br /><div class="form-group">
		<button class="btn btn-primary" 
		type="button" onclick="query();">查看充值账单</button>
<!-- 		<button class="btn btn-primary" onclick="re();" type="button">刷新</BUTTON> -->
		</div></form></div></div>
        <div class="panel">
        <div class="panel-heading">       
        <h3 class="panel-title">该类别下账单统计</h3></div>
        <div class="panel-body">
            <div class="table-responsive">
<table id="searchTableStatistics">
        <tr>
            <th w_render="render_statistics_SIMCategory" width="10%;">卡类型</th>
            <th w_render="render_statistics_countryName" width="10%;">国家</th>
            <th w_render="render_statistics_operatorName" width="10%;">运营商</th>
            <th w_index=countRechargedSim width="10%;">充值卡总数</th>
            <th w_index="countRechargedBill" width="10%;">充值笔数</th>
            <th w_render="render_statistics_sumRechargedBill" width="10%;">充值总金额</th>
        </tr>
</table>
<%--            <table class="table table-bordered table-hover">
              <tr>
                <td width="10%">卡类型：</td>
                <td width="40%"><span class="label label-success label-xs"></span>
                </td>
                <td width="10%">国家：</td>
                <td width="40%"><span class="label label-success label-xs"></span>
                </td>
              </tr>
              <tr>
                <td>运营商：</td>
                <td>
                </td>
                <td>SIM卡总数：</td>
                <td>
                </td>
              </tr>
              <tr>
                <td width="20%">充值笔数：</td>
                <td width="30%"></td>
                <td width="20%">充值总金额：</td>
                <td width="30%"></td>
              </tr>
            </table>  --%>
          </div>
        </div>
        </div>
		<div class="panel">
		<div class="panel-heading">		
		<h3 class="panel-title">该类别下充值记录</h3></div>
		<div class="panel-body">
		<div class="table-responsive"><table id="searchTable">
		        <tr>
            <th w_index="countryName" width="10%;">国家</th>
            <th w_index="operatorName">运营商</th>
            <th w_index="rechargeBillID" w_hidden="true">充值记录ID</th>
            <th w_render="render_imsi">IMSI</th>
            <th w_render="render_SIMinfoID">SIM卡ID</th>
            <th w_render="render_rechargeAmount">充值金额</th>
            <th w_index="creatorUserName" width="5%;">创建者</th>
            <th w_index="creatorDate">充值日期</th>
<!--             <th w_index="rechargedValidDate">充值后有效期</th> -->
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
    
    $("#begindate").datetimepicker({
       pickDate: true,                 //en/disables the date picker
       pickTime: true,                 //en/disables the time picker
       showToday: true,                 //shows the today indicator
       language:'zh-CN',                  //sets language locale
       defaultDate: moment().add(-1, 'months'),                 //sets a default date, accepts js dates, strings and moment objects
   });
    
    $("#enddate").datetimepicker({
       pickDate: true,                 //en/disables the date picker
       pickTime: true,                 //en/disables the time picker
       showToday: true,                 //shows the today indicator
       language:'zh-CN',                  //sets language locale
       defaultDate: moment().add(0, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
   });
   
   $('#dateRangeType0').click(function(){
        $("#begindate").attr("disabled", "disabled");
        $("#enddate").attr("disabled", "disabled");
   });
   $('#dateRangeType1').click(function(){
        $("#begindate").removeAttr("disabled");
        $("#enddate").removeAttr("disabled");
   });
    </script>
    <script type="text/javascript">
    var gridObj;
//     $(function(){ // --> 修改为按需加载
    function initSearchTable() {
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath %>sim/simrechargebill/datapage',
                 // autoLoad: false,
                 pageSizeSelect: true,
                 pageSize: 10,
                 pageSizeForGrid:[10,20,30,50,100],
                 multiSort:true
             });
     }
//         });

// 		function render_bill_countryName(record, rowIndex, colIndex, options) {
// 		   return $("#select_country").find("option:selected").text();
// 		}
		function render_imsi(record, rowIndex, colIndex, options) {
		    return '<a href="<%=basePath %>sim/simrechargebill/new?SIMinfoID=' + record.SIMinfoID + '&SIMCategory=' + record.SIMCategory + '" title="点击查看该卡充值记录"><span>' + record.IMSI + '</span></a>';
		}
		function render_SIMinfoID(record, rowIndex, colIndex, options) {
		   return '<a href="<%=basePath %>sim/siminfo/view/' + record.SIMinfoID + '" title="点击查看该卡详情"><span>' + record.SIMinfoID + '</span></a>';
		}
        function render_rechargeAmount(record, rowIndex, colIndex, options) {
           return accounting.formatMoney(record.rechargeAmount) + '元';
        }
         function operate(record, rowIndex, colIndex, options) {
<%--			//'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>'; --%>
			return '<div class="btn-toolbar">'
+ '<a class="btn btn-primary btn-xs" href="<%=basePath %>sim/simrechargebill/edit/' + record.rechargeBillID + '"><span class="glyphicon glyphicon-edit">编辑</span></a>'
+ '</div>'; <%-- + '<a class="btn btn-primary btn-xs" href="<%=basePath %>sim/simrechargebill/view/' + record.rechargeBillID + '"><span class="glyphicon glyphicon-info-sign">详情</span></a>' --%>
         }
	</script>
<script type="text/javascript">
    var isNotTableInitialized = true; // 修改为按需加载后, 需要此变量控制只进行一次初始化
    var gridObj2;
//     $(function(){ // --> 修改为按需加载
    function initSearchTableStatistics() {
        gridObj2 = $.fn.bsgrid.init('searchTableStatistics',{
            url:'<%=basePath %>sim/simrechargebill/statistics',
            pageAll:true     
        });
      }
//    });

     function render_statistics_SIMCategory(record, rowIndex, colIndex, options) {
        return $("#SIMCategory").find("option:selected").text();
     }
     function render_statistics_countryName(record, rowIndex, colIndex, options) {
        return $("#select_country").find("option:selected").text();
     }
     function render_statistics_operatorName(record, rowIndex, colIndex, options) {
        return $("#operatorName").find("option:selected").text();
     }
     function render_statistics_sumRechargedBill(record, rowIndex, colIndex, options) {
        return accounting.formatMoney(record.sumRechargedBill) + '元';
     }
    function query(){
        // 目前发现下面的条件判断似乎未能阻止重复初始化, 暂使用后面现行的hacked fix
        $("#searchTable_pt_outTab").remove(); // fix 多次显示问题
        $("#searchTableStatistics_pt_outTab").remove(); // fix 多次显示问题
       if (isNotTableInitialized) {
            initSearchTable();
            initSearchTableStatistics();
            isTableInitialized = false;
        }
    
        $('input').each(function(){$(this).val(jQuery.trim($(this).val()));});
        gridObj.options.otherParames = $('#searchForm').serializeArray();
        gridObj.refreshPage();
        gridObj2.options.otherParames =$('#searchForm').serializeArray();
        gridObj2.refreshPage();
    }
    
    
    query();
// 	function re(){
////	    $("#order_ID").val('');
////	    $("#order_customerName").val('');
// 	    gridObj2.options.otherParames =$('#searchForm').serializeArray();
// 	    gridObj2.refreshPage();
// 	}
    </script>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>