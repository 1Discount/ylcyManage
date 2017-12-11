<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>
  <head>
    <title>全部流量预警SIM卡-种子卡管理-EASY2GO ADMIN</title>
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
<%--        <div class="form-group">--%>
<%--          <label class="inline-label">IMSI：</label>--%>
<%--          <input type="text" name="IMSI" style="width: 173px;" placeholder="IMSI" class="form-control">--%>
<%--        </div>--%>
        <div class="form-group">
          <label class="inline-label">卡编号：</label>
          <input type="text" name="simAlias" style="width: 173px;" placeholder="" class="form-control">
        </div>
        <div class="form-group">
          <label class="inline-label">设备号：</label>
          <input type="text" name="lastDeviceSN" style="width: 173px;" placeholder="设备机身码" class="form-control">
        </div>
        <div class="form-group">
          <label class="inline-label">ICCID：</label>
          <input type="text" name="ICCID" placeholder="ICCID" style="width: 173px;" class="form-control">
        </div>
<%--        <div class="form-group">--%>
<%--          <label class="inline-label">服务器：</label>--%>
<%--          <select name="serverIP" style="width: 135px;" class="form-control">--%>
<%--            <option value="">全部服务器</option>--%>
<%--            <c:forEach items="${SIMServers}" var="server" varStatus="status"><option value="${server.IP}">${server.IP}</option></c:forEach>--%>
<%--          </select>--%>
<%--        </div>--%>
<%--        <div class="form-group">--%>
<%--          <label class="inline-label">国&nbsp;家：</label>--%>
<%--          <select name="MCC" style="width: 130px;"--%>
<%--          class="form-control">--%>
<%--            <option value="">全部国家</option>--%>
<%--            <c:forEach items="${Countries}" var="country" varStatus="status"><option value="${country.countryCode}">${country.countryName}</option></c:forEach>--%>
<%--          </select>--%>
<%--        </div>--%>
        <div class="form-group">
          <label class="inline-label">状&nbsp;态：</label>
          <select name="cardStatus" style="width: 130px;" class="form-control">
            <option value="">全部状态</option>
    <c:forEach items="${CardStatusDict}" var="item" varStatus="status"><option value="${item.label}">${item.label}</option></c:forEach>
          </select>
        </div><br />
		<div class="form-group">
		<button class="btn btn-primary" style="width: 130px;"
		type="button" onclick="$('input').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.page(1);">搜索</button><button type="reset" class="btn btn-default">重置</button></div></form></div></div>

        </div>

<div class="col-md-12">
		<div class="panel">
		<div class="panel-heading orange">
<!-- 		<div class="btn-toolbar btn-header-right"><a class="btn btn-success btn-xs" href="<%=basePath %>sim/roamingsiminfo/new"><span class="glyphicon glyphicon-plus"></span> 添加SIM卡</a></div> -->
		<h3 class="panel-title"><span>全部预警状态的漫游SIM卡</span></h3></div>
		<div class="panel-body">
		<div class="table-responsive">
		<table id="searchTable">
		        <tr>
                    <th w_index="SIMinfoID" w_hidden="true">SIM卡ID</th>
                    <th w_index="lastDeviceSN">相关设备号</th>
                    <th w_index="simAlias">卡编号</th>
                    <th w_index="phone">手机号</th>
                    <th w_index="PUK">PUK</th>
                    <th w_index="PIN">PIN</th>
                    <th w_render="render_ICCID" width="10%;">ICCID</th>

                    <th w_index="SIMActivateDate">购卡时间</th><%-- 目前使用SIM卡激活时间去保存为购卡时间 --%>
                    <th w_render="render_cardBalance">卡内余额</th>
                    <th w_render="render_cardStatus" width="5%;">状态</th> <!-- w_sort="userID,desc" -->
                    <th w_index="trademark">运营商</th>
                    <th w_index="remark">备注</th>

<!--                     <th w_index="MCC">国家</th> -->
<%--                    <th w_index="countryName">国家</th>--%>
<%--                    <th w_index="IMSI">IMSI号</th>--%>
<%--                    <th w_index="serverIP" width="8%;">服务器</th>--%>
<%--                    <th w_index="lastDeviceSN">最近设备SN</th>--%>
<%--                    <th w_render="render_speedLimit" width="5%;">限速</th>--%>
<%--                    <th w_render="render_planData" width="5%;">包含流量</th>--%>
<%--                    <th w_render="render_planRemainData" width="5%;">剩余流量</th>--%>
<%--                    <th w_index="cardBalance">余额</th>--%>
<%--                    <th w_index="planEndDate" width="10%;">到期日期</th>--%>

                    <th w_index="trademark">运营商</th>
                    <th w_render="operate">操作</th>
		        </tr>
		</table></div>
		</div></div>
<div id="special-note">备注：<br><br>
<p>1. “包含流量”和“剩余流量”的精确数据表字段数值，以KB为单位，可以在表格相应位置上鼠标悬停显示。</p>
<p></p>
</div>
		</div>

        </section>
      </section>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <script src="<%=basePath %>static/js/byteunit.js"></script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
    <script type="text/javascript">
    	  var gridObj;
          $(function(){
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath %>sim/roamingsiminfo/datapage?op=getAlert',
                 // autoLoad: false,
                 pageSizeSelect: true,
                 pageSize: 10,
                 pageSizeForGrid:[10,20,30,50,100],
                 multiSort:true
             });
         });
         function render_ICCID(record, rowIndex, colIndex, options) {
            return '<a href="<%=basePath %>sim/roamingsiminfo/view/' + record.SIMinfoID + '"><span>' + record.ICCID + '</span></a>';
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
          function render_cardBalance(record, rowIndex, colIndex, options) {
              return accounting.formatMoney(record.cardBalance);
           }
     function render_planData(record, rowIndex, colIndex, options) {
         return '<a title="' + record.planData + '">' + prettyByteUnitSize(record.planData,2,1,true) + '</a>';
      }
     function render_planRemainData(record, rowIndex, colIndex, options) {
         if(record.planRemainData < 20480) {<%-- 少于20MB时醒目显示 --%>
	         return '<a class="btn btn-danger btn-xs" title="' + record.planRemainData + '">' + prettyByteUnitSize(record.planRemainData,2,1,true) + '</a>';
	     } else {
	         return '<a title="' + record.planRemainData + '">' + prettyByteUnitSize(record.planRemainData,2,1,true) + '</a>';
	     }
      }
<%--     function render_speedLimit(record, rowIndex, colIndex, options) {--%>
<%--        if (record.speedLimit == 0) {--%>
<%--            return '<a class="btn btn-success btn-xs" onclick="return;"><span>不限速</span></a>';--%>
<%--        } else {--%>
<%--            return '<a class="btn btn-danger btn-xs" onclick="return;"><span>限速' + record.speedLimit + 'KB</span></a>';--%>
<%--        }--%>
<%--     }--%>
         function operate(record, rowIndex, colIndex, options) {
			var result = '<div class="btn-toolbar">';
			if(record.cardStatus == "使用中") {
			    result += '<a class="btn btn-primary btn-xs" onclick="op_confirm_modify(\'' + record.SIMinfoID + '\')"><span class="glyphicon glyphicon-edit">编辑</span></a>';
			} else {
			    result += '<a class="btn btn-primary btn-xs" href="<%=basePath %>sim/roamingsiminfo/edit/' + record.SIMinfoID + '"><span class="glyphicon glyphicon-edit">编辑</span></a>'
			    + '<a class="btn btn-danger btn-xs" onclick="op_delete(\'' + record.SIMinfoID + '\')"><span class="glyphicon glyphicon-remove">删除</span></a>';
			}

			return result;
         }
         function op_confirm_modify(id) {
             if(confirm("该SIM卡现在使用中，是否确认编辑?")) {
                 window.location.href = '<%=basePath %>sim/roamingsiminfo/edit/' + id;
             }
         }
         function op_delete(id) {
            if(confirm("确认删除SIM卡?")) {
                $.ajax({
                    type:"POST",
                    url:"<%=basePath %>sim/roamingsiminfo/delete/" + id,
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

<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>