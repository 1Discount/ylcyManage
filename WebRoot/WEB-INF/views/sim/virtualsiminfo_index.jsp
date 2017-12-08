<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title><c:if test="${IsIndexView}">全部SIM卡-本地SIM卡管理-EASY2GO ADMIN</c:if>
	<c:if test="${IsTrashView}">本地SIM卡管理-全部已删除SIM卡-EASY2GO ADMIN</c:if></title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet"
	href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet"
	href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
<meta name="csrf_token">
<%@include file="/WEB-INF/views/common/_ie8support.html"%>
<%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
</head>
<body>
	<section id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
		<section id="main-content">
			<section class="wrapper">
				<div class="panel">
		<div class="panel-body">
		<form class="form-inline" id="searchForm" role="form" method="get" action="#">
		<input type="hidden" id="pagenum" value="1" />
		<input type="hidden" id="pagesize" value="15" />		
		
		<div class="form-group">
          <label class="inline-label">IMSI：</label>
          <input type="text" name="IMSI" style="width: 173px;" placeholder="IMSI" class="form-control" id="IMSI">
        </div>
        <div class="form-group">
          <label class="inline-label">SN：</label>
          <input type="text" name="lastDeviceSN" style="width: 173px;" placeholder="设备序列号" class="form-control">
        </div>
          
      
        <div class="form-group">
          <label class="inline-label">国&nbsp;家：</label>
          <select name="MCC" style="width: 130px;" id="MCC"
          class="form-control">
            <option value="">全部国家</option>
            <c:forEach items="${Countries}" var="country" varStatus="status"><option  value="${country.countryCode}">${country.countryName}</option></c:forEach>
          </select>
        </div>
       
		<div class="form-group">
		<button class="btn btn-primary" 
		type="button" onclick="$('input').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.page(1);">搜索</button><button type="reset" class="btn btn-default">重置</button></div></form></div></div>

				<div class="col-md-12">
					<div class="panel">
						<div class="panel-heading">
								<div class="btn-toolbar btn-header-right">
									<a class="btn btn-success btn-xs"
										href="<%=basePath%>sim/virtualsiminfo/addvirtualsiminfo"><span
										class="glyphicon glyphicon-plus"></span> 添加虚拟卡</a>
								</div>
								<!-- <div style="margin-right: 30px;"
									class="btn-toolbar btn-header-right">
									<a class="btn btn-success btn-xs" onclick="updateStatus()"
										href="javascript:;"><span class="glyphicon glyphicon-plus"></span>批量操作</a>
								</div> -->
								<h3 class="panel-title">全部本地SIM卡</h3>		</div>
								<div class="panel-body">
								<div class="table-responsive">
									<table id="searchTable">
										<tr>
											<th w_index="SIMinfoID" w_hidden="true">SIM卡ID</th>
											<th w_index="IMSI">IMSI</th>
											<th w_index="countryName">可用国家</th>
											<th w_index="lastDeviceSN">最近设备SN</th>
										    <th w_index="simAlias">代号</th>
											<th w_render="render_cardStatus">卡状态</th>
											<th w_index="historyUsedFlow">已使用流量</th>
											<th w_index="creatorDate">创建时间</th>
											<th w_index="creatorUserName">创建人</th>
											<th w_index="remark">备注</th>
											<th w_render="operate">操作</th>

											<!-- 		
										<th w_check="true" width="3%;"></th>
										<th w_index="SIMinfoID" w_hidden="true">SIM卡ID</th>
										<th w_render="render_IMSI">IMSI号</th>
										<th w_index="simAlias">代号</th>
										<th w_index="slotNumber">卡位</th>
										<th w_index="countryName">可用国家</th>
										<th w_render="ifr">漫游</th>
										<th w_render="render_cardStatus" width="5%;">状态</th>
										<th w_index="IPAndPort" width="8%;">服务器</th>
										<th w_index="lastDeviceSN" w_sort="lastDeviceSN,unorder">最近设备SN</th>
										<th w_render="render_speedLimit" width="5%;">限速</th>
										<th w_render="render_planData" width="5%;">包含流量</th>
										<th w_render="render_planRemainData" width="5%;">剩余流量</th>
										<th w_render="render_historyUsedFlow" width="5%;">累计使用流量</th>
										<th w_render="render_speedType">高/低速</th>
										<th w_index="subscribeTag">预约标识</th>
										<th w_index="SIMActivateDate" width="5%;">卡开启日期</th>
										<th w_index="SIMEndDate" width="5%;">到期日期</th>
										<th w_render="operate">操作</th> -->
										</tr>
									</table>
								</div>
						</div>
						</div>
			
				</div>
			</section>
		</section>
	</section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/accounting/accounting.min.js"></script>
	<script src="<%=basePath%>static/js/byteunit.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script src="<%=basePath%>static/js/tooltips.js"></script>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
	<script type="text/javascript">
	  var gridObj;
	  var pagesize=parseInt($("#pagesize").val());
      $(function(){
          gridObj = $.fn.bsgrid.init('searchTable',{
	          url:'<%=basePath%>sim/virtualsiminfo/datapage',
	          pageSizeSelect: true,
	          pageSize: pagesize,
	          pageSizeForGrid:[15,30,50,100],
	          multiSort:true,
	          rowHoverColor:true,
	          additionalAfterRenderGrid: function(parseSuccess, gridData, options) {
	        	
	         	  if(parseSuccess){
	         		 $("#pagenum").val(options.curPage);
	         		 var a = $("#searchTable_pt_pageSize").val();
	         		 $("#pagesize").val(a);
	         		 $(".ahover-tips").each(function(){$(this).hoverTipsEx({isEscape:true,isSmallWidth:true});});
	         	 }
	          }
         });
     });
      function operate(record, rowIndex, colIndex, options) {
				return result =
			    '<a class="btn btn-primary btn-xs" href="<%=basePath%>sim/virtualsiminfo/edit/' + record.SIMinfoID + '"><span class="glyphicon glyphicon-edit">编辑</span></a>' 
              + '<a class="btn btn-danger btn-xs" onclick="op_delete(\'' + record.SIMinfoID + '\')"><span class="glyphicon glyphicon-remove">删除</span></a>';
       }  
      function op_delete(id) {
          if(confirm("确认删除SIM卡?")) {
              $.ajax({
                  type:"POST",
                  url:"<%=basePath%>sim/virtualsiminfo/delete/" + id,
					success : function(data) {
						if (data == 1) {
							easy2go.toast('info', "删除成功");
						} else {
							easy2go.toast('error', "删除失败");
						}
						gridObj.refreshPage();
					}

				});
			}
		}
		function render_cardStatus(record, rowIndex, colIndex, options) {
			if (record.cardStatus == "可用") {
				return '<a class="btn btn-success btn-xs" onclick="return;"><span>&nbsp;可用&nbsp;&nbsp;</span></a>';
			} else if (record.cardStatus == "使用中") {
				return '<a class="btn btn-info btn-xs" onclick="return;"><span>使用中</span></a>';
			} else if (record.cardStatus == "不可用") {
				return '<a class="btn btn-danger btn-xs" onclick="return;"><span>'
						+ record.cardStatus + '</span></a>';
			} else if (record.cardStatus == "停用") {
				return '<a class="btn btn-warning btn-xs" onclick="return;"><span>'
						+ record.cardStatus + '</span></a>';
			} else if (record.cardStatus == "下架") {
				return '<a class="btn btn-default btn-xs" onclick="return;"><span>'
						+ record.cardStatus + '</span></a>';
			}
		}
	</script>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>