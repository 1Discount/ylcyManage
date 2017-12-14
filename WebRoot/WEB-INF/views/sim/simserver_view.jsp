<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <title>服务器详情-SIM服务器管理-流量运营中心</title>
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
          
      <div class="col-md-3">
        <div class="panel">
          <div class="panel-heading">
            <h3 class="panel-title">编号 <em>${Model.serverCode}</em></h3>
          </div>
          <div class="panel-body">
<!--             <p> -->
<!--               <label>ID:</label><span>${Model.SIMServerID}</span> -->
<!--             </p> -->
            <p>
              <label>状态:&nbsp;</label><span class="label label-success label-xs">${Model.serverStatus}</span>
            </p>
            <p>
              <label>服务器IP:&nbsp;</label><span>${Model.IP}</span>
            </p>
            <p>
              <label>可用SIM卡数量:&nbsp;</label><span class="label label-success label-xs">${Model.availableSIMCount}</span>
            </p>
            <p>
              <label>SIM卡数量:&nbsp;</label><span class="label label-success label-xs">${Model.SIMCount}</span>
            </p>
            <p>
              <label>创建时间:&nbsp;</label><span>${Model.creatorDate}</span>
            </p>
            <p>
              <label>更新时间:&nbsp;</label><span>${Model.modifyDate}</span>
            </p>
            <p>
              <label>备注:&nbsp;</label><span>${Model.remark}<!-- TODO --></span>
            </p>
            <div class="btn-toolbar"><a href="<%=basePath %>sim/simserver/edit/${Model.SIMServerID}" class="btn btn-primary btn-xs"> <span class="glyphicon glyphicon-edit"></span>编辑</a>
              <a href="<%=basePath %>sim/siminfo/new?serverIP=${Model.IP}&SIMServerID=${Model.SIMServerID}" class="btn btn-success btn-xs"> <span class="glyphicon glyphicon-plus"></span>添加SIM卡</a>
            </div>
          </div>
        </div>

      </div>
      <div class="col-md-9">
        <div class="panel">
          <div class="panel-heading">
            <h3 class="panel-title">搜索本服务器SIM卡</h3>
          </div>
          <div class="panel-body">
            <form id="searchForm" role="form" action="#" method="get" class="form-inline">
                <input type="hidden" name="serverIP" value="${Model.IP}">
              <div class="form-group">
                  <label class="inline-label">IMSI：</label>
                  <input type="text" name="IMSI" placeholder="IMSI" class="form-control">
                </div>
                <div class="form-group">
                  <label class="inline-label">机身码：</label>
                  <input type="text" name="lastDeviceSN" placeholder="设备机身码" class="form-control">
                </div>
                <div class="form-group">
                  <label class="inline-label">ICCID：</label>
                  <input type="text" name="ICCID" placeholder="ICCID" class="form-control">
                </div>
                <div class="form-group">
                  <label class="inline-label">状&nbsp;态：</label>
                  <select name="cardStatus" style="width: 179px;" class="form-control">
                    <option value="">全部状态</option>
                    <option value="不可用">不可用</option>
                    <option value="可用">可用</option>
                    <option value="使用中">使用中</option>
<%--不使用停用状态                    <option value="停用">停用</option> --%>
                  </select>
                </div>
		        <div class="form-group">
		          <label class="inline-label">国&nbsp;家：</label>
		          <select name="MCC" style="width: 179px;"
		          class="form-control">
		            <option value="">全部国家</option>
		            <c:forEach items="${Countries}" var="country" varStatus="status"><option value="${country.countryCode}">${country.countryName}</option></c:forEach>
		          </select>
		        </div>
              <div class="form-group"><button class="btn btn-primary" 
        type="button" onclick="$('input').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.refreshPage();">搜索</button><button type="reset" class="btn btn-default">重置</button></div>
            </form>
          </div>
        </div>
        <div class="panel">
          <div class="panel-heading">
            <h3 class="panel-title">SIM卡列表</h3>
          </div>
          <div class="panel-body">
            <div class="table-responsive">
	        <table id="searchTable">
	                <tr>
	                    <th w_index="SIMinfoID" w_hidden="true">SIM卡ID</th>  
	                    <th w_render="render_ICCID" width="10%;">ICCID</th>
	                    <th w_index="IMSI">IMSI号</th>
	                    <th w_index="simAlias">代号</th>
                        <th w_index="lastDeviceSN">设备机身码</th>
	                    <th w_index="countryName">国家</th>
	                    <th w_render="render_cardStatus" width="5%;">状态</th> <!-- w_sort="userID,desc" -->
	                    <th w_index="serverIP" width="8%;">服务器</th>
	                    <th w_render="render_speedLimit" width="5%;">限速</th>
	                    <th w_render="render_planData" width="5%;">包含流量KB</th>
	                    <th w_render="render_planRemainData" width="5%;">剩余流量KB</th>
	                    <th w_index="planEndDate" width="10%;">到期日期</th>
	                    <th w_render="operate">操作</th>
	                </tr>
	        </table>
            </div>
          </div>
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
                 url:'<%=basePath %>sim/siminfo/datapage?serverIP=${Model.IP}',
                 // autoLoad: false,
                 pageSizeSelect: true,
                 pageSize: 10,
                 pageSizeForGrid:[10,20,30,50,100],
                 multiSort:true
             });
         });
         function render_ICCID(record, rowIndex, colIndex, options) {
            return '<a href="<%=basePath %>sim/siminfo/view/' + record.SIMinfoID + '"><span>' + record.ICCID + '</span></a>';
         }
         function render_cardStatus(record, rowIndex, colIndex, options) {
             if (record.cardStatus == "可用") {
                 return '<a class="btn btn-success btn-xs" onclick="return;"><span>&nbsp;可用&nbsp;&nbsp;</span></a>';
             } else {
                 return '<a class="btn btn-danger btn-xs" onclick="return;"><span>不可用</span></a>';
             }
          }
          function render_speedLimit(record, rowIndex, colIndex, options) {
             if (record.speedLimit == 0) {
                 return '<a class="btn btn-success btn-xs" onclick="return;"><span>不限速</span></a>';
             } else {
                 return '<a class="btn btn-danger btn-xs" onclick="return;"><span>限速' + record.speedLimit + 'KB</span></a>';
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
            return '<div class="btn-toolbar">'
+ '<a class="btn btn-primary btn-xs" href="<%=basePath %>sim/siminfo/view/' + record.SIMinfoID + '"><span class="glyphicon glyphicon-info-sign">详情</span></a>'
+ '<a class="btn btn-primary btn-xs" href="<%=basePath %>sim/siminfo/edit/' + record.SIMinfoID + '"><span class="glyphicon glyphicon-edit">编辑</span></a>'
+ '<a class="btn btn-primary btn-xs" href="<%=basePath %>sim/simrechargebill/new?SIMinfoID=' + record.SIMinfoID + '&SIMCategory=' + record.SIMCategory + '"><span class="glyphicon glyphicon-plus">充值</span></a>'
+ '</div>';
         } // + '<a class="btn btn-danger btn-xs" href="#" onclick="op_delete(\'' + record.SIMinfoID + '\')"><span class="glyphicon glyphicon-remove">删除</span></a>'
    </script>
    
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>