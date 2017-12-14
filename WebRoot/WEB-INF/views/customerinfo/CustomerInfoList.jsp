<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- 格式化时间 -->
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>全部客户-客户管理-流量运营中心</title>
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
<style type="text/css">
#searchTable tr {
	height: 40px;
}
</style>
</head>
<body>

	<section id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />

		<SECTION id="main-content">
			<SECTION class="wrapper">
				<DIV class="col-md-12">
					<DIV class="panel">
								<DIV class="col-md-12">
									<DIV class="panel">
									<DIV class="panel-body">
									
											<FORM class="form-inline" id="searchForm" method="get" action="#">
												<DIV class="form-group">
													<LABEL class="inline-label">客户：</LABEL> <INPUT
														class="form-control" name="customerName" id="customerName"
														type="text" placeholder="客户名称">
												</DIV>
												<DIV class="form-group">
													<LABEL class="inline-label">电话：</LABEL> <INPUT
														class="form-control" name="phone" id="phone" type="text"
														placeholder="电话">
												</DIV>
												
												<DIV class="form-group">
													<LABEL class="inline-label">渠道商：</LABEL>
													<select id="distributorCompany" name="distributorCompany" class="form-control" >
													<option value="">全部</option>
													<c:forEach items="${disAll}" var="item" varStatus="status">
														<option value="${item.company}">${item.company}</option>
													</c:forEach>
													</select>
												</DIV>
												
												
												<DIV class="form-group">
													<BUTTON class="btn btn-primary" type="button"
														onclick="gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.page(1);">搜索</BUTTON>
												</DIV>
											</FORM>
								
								</div>
								
								
									</DIV>

									<DIV class="panel">
										<DIV class="panel-heading">全部客户</DIV>
										<DIV class="panel-body">
											<DIV class="table-responsive">
												<TABLE id="searchTable">
													<TR>
														<!--     <th w_index="customerID" type="hidden" width="10%" >creatorid</th> -->
														<TH w_render="persondetail" width="10%"><b>称呼</b></TH>
														<TH w_index="phone" width="10%"><b>电话/手机</b></TH>
														<TH w_index="creatorDate" width="10%"><b>创建时间</b></TH>
														<th w_index="customerSource" width="10%"><b>客户来源</b></th>
														<th w_index="isVIP" width="10%"><b>是否是VIP客户</b></th>
														<th w_index="remark" width="10%"><b>备注</b></th>
														<TH w_render="operate" width="10%"><b>操作</b></TH>
													</TR>
												</TABLE>
											</DIV>
											<DIV></DIV>
										</DIV>
									</DIV>
								</DIV>
							<!-- ======================================================================================== -->
						</DIV>

					</DIV>
				</DIV>
			</SECTION>
		</SECTION>
	</section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script type="text/javascript">
    var gridObj;
    $(function(){
       gridObj = $.fn.bsgrid.init('searchTable',{
           url:'<%=basePath%>customer/customerInfolist/customerinfodatapage',
           // autoLoad: false,
           pageSizeSelect: true,
           pageSize: 10,
           pageSizeForGrid:[10,20,30,50,100],
           multiSort:true
       });
   });
    
    function deleteCK(){
    	if(confirm("确定要删除数据吗？"))
        {
    	return true;	
    	}return false;
    }
   function persondetail(record, rowIndex, colIndex, options){
	return '<A style="color:#1FB5AD;" href="<%=basePath%>customer/customerInfolist/customerInfoDetail?cusid='+record.customerID+'">'+record.customerName+'</A>';
   }
   function operate(record, rowIndex, colIndex, options) {
           return  '<A class="btn btn-primary btn-xs" href="<%=path%>/customer/customerInfolist/update?uid='+record.customerID+'">'+
                 '<SPAN class="glyphicon glyphicon-edit">编辑</SPAN>'+
             '</A>&nbsp;&nbsp;&nbsp;&nbsp;'+
             '<A class="btn btn-danger  btn-xs" href="<%=basePath%>customer/customerInfolist/deleteCustomerInfo?sysStatus_id='+record.customerID+'">'+
                 '<SPAN class="glyphicon glyphicon-remove" onclick="return deleteCK()">删除</SPAN>'+
             '</A>';
   }
   
   /*  添加订单的链接在这里，先去掉 */
   <%-- '<A class="btn btn-success btn-xs" href="<%=path %>/orders/ordersinfo/gotwo?customerID='+record.customerID+'">'+'<SPAN class="glyphicon glyphicon-plus">添加订单</SPAN>'+ '</A>&nbsp;&nbsp;&nbsp;&nbsp;'+ --%>
    </script>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
		<jsp:param name="matchType" value="exactly" />
	</jsp:include>
</body>
</html>