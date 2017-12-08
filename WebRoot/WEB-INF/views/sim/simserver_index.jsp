<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>
  <head>
    <title>全部服务器-SIM服务器管理-EASY2GO ADMIN</title>
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
			<label class="inline-label">编号：</label><input class="form-control" name="serverCode" type="text" placeholder="编号" ></div>
		<div class="form-group">
          <label class="inline-label">状&nbsp;态：</label>
          <select name="serverStatus" style="width: 179px;" class="form-control">
            <option value="">全部状态</option>
    <c:forEach items="${ServerStatusDict}" var="item" varStatus="status"><option value="${item.label}">${item.label}</option></c:forEach>                                   
          </select>
        </div>
		<div class="form-group">
		<button class="btn btn-primary" 
		type="button" onclick="$('input').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.page(1);">搜索</button></div></form></div></div>
		<div class="panel">
		<div class="panel-heading">
		<div class="btn-toolbar btn-header-right"><a class="btn btn-success btn-xs" href="<%=basePath %>sim/simserver/new"><span class="glyphicon glyphicon-plus"></span> 添加服务器</a></div>
		<h3 class="panel-title">全部SIM服务器</h3></div>
		<div class="panel-body">
		<div class="table-responsive"><table id="searchTable">
		        <tr>
		            <th w_render="render_IP" width="10%;" >IP</th>
		        	<th w_index="SIMServerID" width="15%;">服务器ID</th>
		            <th w_index="serverCode" width="5%;">编号</th><%-- w_sort="serverCode,asc" --%>
                    <th w_index="serverStatus" width="5%;" >状态</th>
		            <th w_index="SIMCount" width="10%;" >SIM总数<sup><a href="#special-note">(注1)</a></sup></th>
		            <th w_render="render_availableSIMCount" width="10%;" >可用SIM数量<sup><a href="#special-note">(注2)</a></sup></th>
 		            <th w_index="creatorDate" width="10%;">创建时间</th>
 		            <th w_index="modifyDate" width="10%;">修改时间</th>
		            <th w_render="operate" width="20%;">操作</th>
		        </tr>
		</table></div>
		</div></div>
		      <div id="special-note">备注：<br><br>
<p>1. 此处"SIM卡总数"和"可用卡数量"目前需要手动触发统计并更新, 请访问 <a href="<%=basePath %>sim/simserver/statistics">SIM服务器卡数量统计</a> 。</p>
<p>2. "可用卡数量"少于 5 张则会红色醒目标示 。</p>
            </div>
		</div>
          
        </section>
      </section>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<c:if test="${IsIndexView}"><%-- 区分回收站记录和正常记录的操作 --%>
    <script type="text/javascript">
    	  var gridObj;
          $(function(){
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath %>sim/simserver/datapage',
                 // autoLoad: false,
                 pageSizeSelect: true,
                 pageSize: 10,
                 pageSizeForGrid:[10,20,30,50,100],
                 multiSort:false
             });
         });
          function render_availableSIMCount(record, rowIndex, colIndex, options) {
              if(record.availableSIMCount >= 5){ // 小于5则醒目提醒
                  return '<a class="btn btn-success btn-xs" onclick="return;"><span>' + record.availableSIMCount + '</span></a>';
              } else {
                  return '<a class="btn btn-danger btn-xs" onclick="return;"><span>' + record.availableSIMCount + '</span></a>';
              }
          }
         function render_IP(record, rowIndex, colIndex, options) {
            return '<a href="<%=basePath %>sim/simserver/view/' + record.SIMServerID + '"><span>' + record.IP + '</span></a>';
         }
         function operate(record, rowIndex, colIndex, options) {
<%--			//'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>'; --%>
			return '<div class="btn-toolbar">'
+ '<a class="btn btn-primary btn-xs" href="<%=basePath %>sim/simserver/view/' + record.SIMServerID + '"><span class="glyphicon glyphicon-info-sign">详情</span></a>'
+ '<a class="btn btn-primary btn-xs" href="<%=basePath %>sim/simserver/edit/' + record.SIMServerID + '"><span class="glyphicon glyphicon-edit">编辑</span></a>'
+ '</div>';
         }
	</script>
</c:if>
<c:if test="${IsTrashView}"><%-- 区分回收站记录和正常记录的操作 --%>
    <script type="text/javascript">
    	  var gridObj;
          $(function(){
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath %>sim/simserver/trashdatapage',
                 // autoLoad: false,
                 pageSizeSelect: true,
                 pageSize: 10,
                 pageSizeForGrid:[10,20,30,50,100],
                 multiSort:true
             });
         });
         function render_availableSIMCount(record, rowIndex, colIndex, options) {
        	 if(record.availableSIMCount >= 5){ // 小于5则醒目提醒
        		 return '<a class="btn btn-success btn-xs" onclick="return;"><span>record.availableSIMCount</span></a>';
        	 } else {
        		 return '<a class="btn btn-danger btn-xs" onclick="return;"><span>record.availableSIMCount</span></a>';
        	 }
         }
         function render_IP(record, rowIndex, colIndex, options) {
            return '<a href="<%=basePath %>sim/simserver/view/' + record.SIMServerID + '"><span>' + record.IP + '</span></a>';
         }
         function operate(record, rowIndex, colIndex, options) {
<%--			//'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>'; --%>
			return '<div class="btn-toolbar">'
+ '<a class="btn btn-primary btn-xs" href="<%=basePath %>sim/simserver/view/' + record.SIMServerID + '"><span class="glyphicon glyphicon-info-sign">详情</span></a>'
+ '<a class="btn btn-primary btn-xs" href="<%=basePath %>sim/simserver/edit/' + record.SIMServerID + '"><span class="glyphicon glyphicon-edit">编辑</span></a>'
+ '<a class="btn btn-danger btn-xs" href="#" onclick="op_restore(\'' + record.SIMServerID + '\')"><span class="glyphicon glyphicon-remove">恢复</span></a>'
+ '</div>';
         }
         
         function op_restore(id) {
            if(confirm("确认恢复国家?")) {
				$.ajax({
					type:"POST",
					url:"<%=basePath %>sim/simserver/restore/" + id,
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

<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>