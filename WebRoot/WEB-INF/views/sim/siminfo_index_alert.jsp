<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>
  <head>
    <title>全部流量预警SIM卡-本地SIM卡管理-EASY2GO ADMIN</title>
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
        <input type="hidden" id="pagenum" value="1" />
        <input type="hidden" id="pagesize" value="15" />
		<div class="form-group">
          <label class="inline-label">IMSI：</label>
          <input type="text" name="IMSI" style="width: 173px;" placeholder="IMSI" class="form-control">
        </div>
        <div class="form-group">
          <label class="inline-label">序列号：</label>
          <input type="text" name="lastDeviceSN" style="width: 173px;" placeholder="设备序列号" class="form-control">
        </div>
        <div class="form-group">
          <label class="inline-label">ICCID：</label>
          <input type="text" name="ICCID" placeholder="ICCID" style="width: 173px;" class="form-control">
        </div>        
        <div class="form-group">
          <label class="inline-label">服务器：</label>
          <select name="serverIP" style="width: 135px;" class="form-control">
            <option value="">全部服务器</option>
            <c:forEach items="${SIMServers}" var="server" varStatus="status"><option value="${server.IP}">${server.IP}</option></c:forEach>
          </select>
        </div>
        <div class="form-group">
          <label class="inline-label">国&nbsp;家：</label>
          <select name="MCC" style="width: 130px;"
          class="form-control">
            <option value="">全部国家</option>
            <c:forEach items="${Countries}" var="country" varStatus="status"><option value="${country.countryCode}">${country.countryName}</option></c:forEach>
          </select>
        </div>
        <div class="form-group">
          <label class="inline-label">状&nbsp;态：</label>
          <select name="cardStatus" style="width: 130px;" class="form-control">
            <option value="">全部状态</option>
    <c:forEach items="${CardStatusDict}" var="item" varStatus="status"><option value="${item.label}">${item.label}</option></c:forEach>
          </select>
        </div>
		<div class="form-group">
		<button class="btn btn-primary" 
		type="button" onclick="$('input').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.page(1);">搜索</button><button type="reset" class="btn btn-default">重置</button></div></form></div></div>

        </div>
            
<div class="col-md-12">
		<div class="panel">
		<div class="panel-heading orange">
<!-- 		<div class="btn-toolbar btn-header-right"><a class="btn btn-success btn-xs" href="<%=basePath %>sim/siminfo/new"><span class="glyphicon glyphicon-plus"></span> 添加SIM卡</a></div> -->
		<h3 class="panel-title"><span>全部预警状态的SIM卡</span></h3></div>
		<div class="panel-body">
		<div class="table-responsive">
		<table id="searchTable" class="hoverable-table">
		        <tr>
                    <th w_index="SIMinfoID" w_hidden="true">SIM卡ID</th>  
<%--                    <th w_render="render_ICCID" width="10%;">ICCID</th>--%>
                    <th w_render="render_ICCID">IMSI号</th>
                    <th w_index="simAlias">代号</th>
<!--                     <th w_index="MCC">国家</th> -->
                    <th w_index="countryName">国家</th>
                    <th w_render="render_cardStatus" width="5%;">状态</th> <!-- w_sort="userID,desc" -->
                    <th w_index="serverIP" width="8%;">服务器</th>
                    <th w_index="lastDeviceSN">最近设备SN</th>
                    <th w_render="render_speedLimit" width="5%;">限速</th>
                    <th w_render="render_planData" width="5%;">包含流量</th>
                    <th w_render="render_planRemainData" width="5%;">剩余流量</th>
                    <th w_render="render_cardBalance">余额</th>
                    <th w_index="planEndDate" width="10%;">到期日期</th>
                    <th w_render="operate">操作</th>
		        </tr>
		</table></div>
		
		    
		</div></div>
		      <div id="special-note">备注：<br><br>
<p>1. 充值预警系指满足下面任意一个条件的卡：卡余额少于或等于50元，或卡的可用流量少于或等于50MB。</p>
<p>2. 请及时留意此表数据，及时充值，保证一直正常可用。</p>
<p>3. 若要查看按地区或运营商的统计数量，请转到：“SIM卡状态统计”</p>
<p>4. “包含流量”和“剩余流量”的精确数据表字段数值，以KB为单位，可以在表格相应位置上鼠标悬停显示。</p>
            </div>
		</div>
          
        </section>
      </section>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <script src="<%=basePath %>static/js/accounting/accounting.min.js"></script>
    <script src="<%=basePath %>static/js/byteunit.js"></script>
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
    
    
    	  var gridObj;
          $(function(){
        	  var pagesize=parseInt($("#pagesize").val());
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath %>sim/siminfo/datapage?op=getAlert',
                 // autoLoad: false,
                 pageSizeSelect: true,
                 pageSize: pagesize, //15,
                 pageSizeForGrid:[15,30,50,100],
                 multiSort:true,
                 rowHoverColor:true,
                 otherParames:$('#searchForm').serializeArray(),
                 additionalAfterRenderGrid: function(parseSuccess, gridData, options) {
                     if(parseSuccess){
                         $("#pagenum").val(options.curPage);
                         var a = $("#searchTable_pt_pageSize").val();
                         $("#pagesize").val(a);
                     }
                 }
             });
             //gridObj.options.otherParames = $('#searchForm').serializeArray();
             if($("#pagenum").val()!=""){
                 gridObj.page($("#pagenum").val());
             }else{
                 gridObj.page(1);
             }
         });
         function render_ICCID(record, rowIndex, colIndex, options) {
            return '<a href="<%=basePath %>sim/siminfo/view/' + record.SIMinfoID + '"><span>' + record.IMSI + '</span></a>';
         }
         function render_cardStatus(record, rowIndex, colIndex, options) {
             if (record.cardStatus == "可用") {
                 return '<a class="btn btn-success btn-xs" onclick="return;"><span>&nbsp;可用&nbsp;&nbsp;</span></a>';
             } else if (record.cardStatus == "使用中") {
                 return '<a class="btn btn-info btn-xs" onclick="return;"><span>使用中</span></a>';
             } else {
                 return '<a class="btn btn-danger btn-xs" onclick="return;"><span>' + record.cardStatus + '</span></a>';
             }
          }
          function render_speedLimit(record, rowIndex, colIndex, options) {
             if (record.speedLimit == 0) {
                 return '<a class="btn btn-success btn-xs" onclick="return;"><span>不限速</span></a>';
             } else {
                 return '<a class="btn btn-danger btn-xs" onclick="return;"><span>限速' + record.speedLimit + 'KB</span></a>';
             }
          }
    function render_cardBalance(record, rowIndex, colIndex, options) {
        if(record.cardBalance < 50) {<%-- 少于50元时醒目显示 --%>
        return '<a class="btn btn-danger btn-xs">' + accounting.formatMoney(record.cardBalance) + '</a>';
        } else {
         return accounting.formatMoney(record.cardBalance);
        }
     }
    function render_planData(record, rowIndex, colIndex, options) {
        return '<a title="' + record.planData + '">' + prettyByteUnitSize(record.planData,2,1,true) + '</a>';
     }
    function render_planRemainData(record, rowIndex, colIndex, options) {
        if(record.planRemainData < 204800) {<%-- 少于200MB时醒目显示 --%>
	        return '<a class="btn btn-danger btn-xs" title="' + record.planRemainData + '">' + prettyByteUnitSize(record.planRemainData,2,1,true) + '</a>';
	    } else {
	        return '<a title="' + record.planRemainData + '">' + prettyByteUnitSize(record.planRemainData,2,1,true) + '</a>';
	    }
     }
         function operate(record, rowIndex, colIndex, options) {
            var result = '<div class="btn-toolbar">';
            if(record.cardStatus == "使用中") {
                result += '<a class="btn btn-primary btn-xs" onclick="op_confirm_modify(\'' + record.SIMinfoID + '\')"><span class="glyphicon glyphicon-edit">编辑</span></a>';
            } else {
                result += '<a class="btn btn-primary btn-xs" href="<%=basePath %>sim/siminfo/edit/' + record.SIMinfoID + '"><span class="glyphicon glyphicon-edit">编辑</span></a>' 
                + '<a class="btn btn-danger btn-xs" onclick="op_delete(\'' + record.SIMinfoID + '\')"><span class="glyphicon glyphicon-remove">删除</span></a>';
            }
            result += '<a class="btn btn-primary btn-xs" href="<%=basePath %>sim/simrechargebill/new?SIMinfoID=' + record.SIMinfoID + '&SIMCategory=' + record.SIMCategory + '"><span class="glyphicon glyphicon-plus">充值</span></a></div>';
            
          return result;
         }
         
         function op_delete(id) {
            if(confirm("确认删除SIM卡?")) {
                $.ajax({
                    type:"POST",
                    url:"<%=basePath %>sim/siminfo/delete/" + id,
                   // dataType:"html",
                   // data:$('#plan_form').serialize(),
                    success:function(data){
                        easy2go.toast('info', data);
                        gridObj.refreshPage();
                    }

                });
            }
         }
         
         function op_confirm_modify(id) {
             if(confirm("该SIM卡现在使用中，是否确认编辑?")) {
            	 window.location.href = '<%=basePath %>sim/siminfo/edit/' + id;
             }
         }
	</script>
	
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>