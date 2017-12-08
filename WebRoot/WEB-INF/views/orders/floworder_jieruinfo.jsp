<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" +

			request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 

"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-

scale=1.0, user-scalable=no">
<link rel="stylesheet" href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet" href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
<style type="text/css">
a.ahover-tips {
	background: url(/ylcyManage/static/images/question2.png) no-repeat;
	margin-left: -50px;
}
/*  #searchTable tr{height:40px;} */
</style>
<meta name="csrf_token">
<%@include file="/WEB-INF/views/common/_ie8support.html"%>
<%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
	<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />

	<SECTION id="main-content"> <SECTION class="wrapper">
	<div class="col-md-12">
		<div class="panel hidden">
			<div class="panel-body ">
				<form class="form-inline " id="searchForm" role="form" method="get" action="#">
									<input type="hidden" value="1" id="pagenum" />
									<input type="hidden" id="pagesize" value="10" />

								
								<div class="form-group ">
									<label class="inline-label">国 家：</label><input
										class="form-control" name="countryName"  id="countryName" type="text" value="${countryName}"/>
								</div>
								
								<div class="form-group ">
									<label class="inline-label">MCC：</label><input
										class="form-control" name="mcc"  id="mcc" type="text" value="${mcc}"/>
								</div>
								
								<DIV class="form-group ">
									<LABEL class="inline-label">时间段：</LABEL> <INPUT
										id="order_creatorDatebegin" class="form-control form_datetime"
										name="beginDate" type="text" data-date-format="YYYY-MM-DD HH:mm:ss" value="${beginDate}">
								</DIV>

								<DIV class="form-group ">
									<LABEL class="inline-label">到</LABEL> <INPUT
										id="order_creatorDateend" class="form-control form_datetime"
										name="endDate" type="text"
										data-date-format="YYYY-MM-DD HH:mm:ss" value="${endDate}">
								</DIV>

								<DIV class="form-group hidden">
									<BUTTON class="btn btn-primary" type="button"
										onclick="gridObj.options.otherParames =$('#searchForm').serializeArray();gridObj.page(1);">搜索</BUTTON>
									<BUTTON class="btn btn-primary" onclick="re();" type="button">刷新</BUTTON>
								</DIV>
							</FORM>
				</form>
			</div>
		</div>
		<div class="panel">
			<div class="panel-heading">

				<h3 class="panel-title">
					接入详情
					<span style="color: red;"></span>
				</h3>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table id="searchTable">
						<tr>
							<th w_index="logsDate" width="5%;" ><b>接入时间</b></th>
							<th w_index="inCount" width="5%;" ><b>接入数量</b></th>
							<th w_index="mcc" width="5%;" ><b>接入国家</b></th>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	</SECTION></SECTION>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script src="<%=basePath%>static/js/jquery.zclip.min.js"></script>
	<SCRIPT type="text/javascript">
   		bootbox.setDefaults("locale","zh_CN");
   	  	var gridObj;
        $(function(){
        	$("#order_creatorDatebegin").datetimepicker({
           		pickDate: true,
           		pickTime: true,
           		showToday: true,
           		language:'zh-CN',
           		//defaultDate: moment(),
           	});	
        	$("#order_creatorDateend").datetimepicker({
           		pickDate: true,
           		pickTime: true,
           		showToday: true,
           		language:'zh-CN',
           		//defaultDate: moment(),
           	});

            gridObj = $.fn.bsgrid.init('searchTable',{
	            url:'<%=basePath%>devicelogs/gitJieruInfoPage',
				//pageSizeSelect : true,
                autoLoad:true,
				otherParames : $('#searchForm').serializeArray(),
				pageAll : true,
			});
		});
	</script>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
		<jsp:param name="matchType" value="exactly" />
	</jsp:include>
</body>
</html>