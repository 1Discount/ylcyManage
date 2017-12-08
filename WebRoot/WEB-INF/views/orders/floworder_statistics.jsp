<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>流量订单统计-统计报表-EASY2GO ADMIN</title>
<meta charset="utf-8">
<meta http-equiv="Expires " content="0 ">
<meta http-equiv="kiben " content="no-cache ">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet" href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
<meta name="csrf_token">
<%@include file="/WEB-INF/views/common/_ie8support.html"%>
<%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
</head>
<body>
	<section id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
		<SECTION id="main-content">
			<SECTION class="wrapper">
				<DIV class="col-md-12">
					<DIV class="panel">
						<DIV class="panel-body">
							<FORM class="form-inline" id="searchForm" role="form" method="get" action="#">
								<div class="form-group">
									<label class="inline-label">国&nbsp;家：</label>
									<select name="userCountry" style="width: 130px;" id="userCountry" class="form-control">
										<option value="">全部国家</option>
										<c:forEach items="${Countries}" var="country" varStatus="status">
											<option value="${country.countryName}">${country.countryName}</option>
										</c:forEach>
									</select>
								</div>
							
								<DIV class="form-group">
									<LABEL class="inline-label">客户名称：</LABEL>
									<INPUT class="form-control" id="order_customerName" name="customerName" type="text" placeholder="客户名称">
								</DIV>
								<DIV class="form-group">
									<LABEL class="inline-label">订单来源：</LABEL>
									<select id="slect_mg" name="orderSource" class="form-control">
										<option value="">所有</option>
										<c:forEach var="dic" items="${diclist}">
											<option value="${dic.label }">${dic.label }</option>
										</c:forEach>
										<option value="渠道商">渠道商</option>
									</select>
								</DIV>

								<DIV class="form-group">
									<LABEL class="inline-label">渠道商：</LABEL>

									<select id="distributorName" name="distributorName" class="form-control form_datetime">
										<option value="">全部</option>
										<c:forEach items="${distributors}" var="dis">
											<option value="${dis.company }">${dis.company}</option>
										</c:forEach>
									</select>
								</DIV>

								<DIV class="form-group">
									<LABEL class="inline-label">有效期：</LABEL>
									<INPUT id="order_creatorDatebegin" class="form-control form_datetime" name="begindateForQuery" type="text" data-date-format="YYYY-MM-DD">
								</DIV>

								<DIV class="form-group">
									<LABEL class="inline-label">到</LABEL>
									<INPUT id="order_creatorDateend" class="form-control form_datetime" name="enddate" type="text" data-date-format="YYYY-MM-DD">
								</DIV>
								
									
							
								
								<DIV class="form-group">
									<BUTTON class="btn btn-primary" type="button" onclick="gridObj.options.otherParames =$('#searchForm').serializeArray();gridObj.refreshPage();">搜索</BUTTON>
									<BUTTON class="btn btn-primary" onclick="re();" type="button">刷新</BUTTON>
								</DIV>
								
							
								
							</FORM>
						</DIV>
					</DIV>
					<DIV class="panel">
						<DIV class="panel-heading">
							<H3 class="panel-title">流量订单统计<span style="color:red;"></span></H3>
						</DIV>
						<DIV class="panel-body">
							<table id="searchTable">
								<tr>
									<th w_render="available" width="9%;">时间段内可用订单总数</th>
									<!-- <th w_render="notdelete" width="9%;">流量订单总数</th> -->
									<th w_render="usered" width="9%;">已使用订单数</th>
									<th w_render="notuser" width="9%;">未使用订单数</th>
								</tr>
							</table>
							
						</DIV>
						
					</DIV>
				<!-- 	
					<div><p>备注:</p>
						<p>1、时间段内可用订单总数是根据订单有效期查询，其它是根据订单创建时间查询</p>
					</div> -->
					
					
				</DIV>
				
			</SECTION>
		</SECTION>
	</section>

	<div id="myModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					Hello world!
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary">保存</button>
					<button type="button" class="btn btn-primary">取消</button>
				</div>
			</div>
		</div>
	</div>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<SCRIPT type="text/javascript">
	    bootbox.setDefaults("locale","zh_CN");
	  	var gridObj;
	    $(function(){
	   	   $("#order_creatorDatebegin").datetimepicker({
	       		pickDate: true,                 
	       		pickTime: true,                 
	       		showToday: true,                 
	       		language:'zh-CN',                  
	       		defaultDate: moment().add(-1, 'months'),                 
	       });
           $("#order_creatorDateend").datetimepicker({
        		pickDate: true,                 
        		pickTime: true,                 
        		showToday: true,                
        		language:'zh-CN',                  
        		defaultDate: moment().add(0, 'days'),                
           });
	       gridObj = $.fn.bsgrid.init('searchTable',{
	            url:'<%=basePath%>orders/flowdealorders/getstatistics',
	            otherParames:$('#searchForm').serializeArray(),
	            pageAll:true
	       });
	    });
	
	    function available(record, rowIndex, colIndex, options) {
	        return '<a href="<%=basePath%>orders/flowdealorders/flowdealinfotwo?status=available&'+$("#searchForm").serialize()+'"><span>' + record.available + '</span></a>';
	    }
    
	    function notdelete(record, rowIndex, colIndex, options) {
	        return '<a href="<%=basePath%>orders/flowdealorders/flowdealinfo?status=notdelete&'+$("#searchForm").serialize()+'"><span>' + record.notdelete + '</span></a>';
	    }
    
	    function usered(record, rowIndex, colIndex, options) {
	        return '<a href="<%=basePath%>orders/flowdealorders/flowdealinfo?status=usered&'+$("#searchForm").serialize()+'"><span>' + record.usered + '</span></a>';
	    }
    
	    function notuser(record, rowIndex, colIndex, options) {
	        return '<a href="<%=basePath%>orders/flowdealorders/flowdealinfo?status=notuser&'+$("#searchForm").serialize()+'"><span>' + record.notuser + '</span></a>';
	    }
        
		function re(){
			$("#order_customerName").val('');
			gridObj.options.otherParames = $('#searchForm').serializeArray();
			gridObj.refreshPage();
	    }
	</SCRIPT>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
		<jsp:param name="matchType" value="exactly" />
	</jsp:include>
</body>
</html>