<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>激活流量订单-订单管理-流量运营中心</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet"
	href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet"
	href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
<meta name="csrf_token">
<%@include file="/WEB-INF/views/common/_ie8support.html" %>
<%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
</head>
<body>
	<section id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
		<SECTION id="main-content">
			<SECTION class="wrapper">
				<div class="col-md-3">
				</div>
				<div class="col-md-6">
					<div class="panel">
						<div class="panel-heading">编辑数据服务交易（${fid }）</div>
						<div class="panel-body">
							<form id="deal_form" role="form"
								action="" method="post"
								autocomplete="off" class="form-horizontal">
								<input type="hidden" name="flowOrderID"
									value="${fid }">
								<input type="hidden" id="deviceDeal_SN" name="SN"
									value="">
								<div class="form-group">
									<label for="deal_amount" class="col-md-3 control-label">选择设备机身码进行激活:</label>
									<div class="col-md-6">
										<select id="slect_sn" onchange="selectid();"  name="deviceDealID" required="" class="form-control"><option value="">选择设备机身码</option>
<c:forEach items="${snlist}" var="dev" varStatus="status">
	<option value="${dev.deviceDealID}">${dev.SN}</option>
</c:forEach>
</select>
									</div>
								</div>
								
								<div class="form-group">
									<div class="col-md-6 col-md-offset-3">
										<div class="btn-toolbar">
											<button type="button" onclick="addsub();" class="btn btn-primary">激活</button>
											<button type="button" onclick="javascript:history.go(-1);"
												class="btn btn-default">返回</button>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</SECTION>
		</SECTION>
	</section>

	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<SCRIPT type="text/javascript">
    bootbox.setDefaults("locale","zh_CN");
   
   /* function operate1(record, rowIndex, colIndex, options) {
       //'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>';
       if(record.remark==""){
    	   return "";
       }
       return '<A class="btn btn-primary btn-xs"  href="#">'+record.remark+'</A>'
   } */
   
   function addsub(){
	   if($("#slect_sn").val()==""){
		   easy2go.toast('warn', "请选择设备机身码");
		   return;
	   }
			$.ajax({
				type : "POST",
				url : "<%=basePath%>orders/flowdealorders/activate",
				dataType : "html",
				data : $('#deal_form').serialize(),
				success : function(data) {
					var result;
                    try{
                        result = jQuery.parseJSON(data);
                    } catch(e) {
                        result = {code:-1000}; // 这个表示未登录
                    }
                    
                    if (result.code == 1) {
                    	bootbox.alert(result.msg,function(){
                            window.location="<%=basePath%>orders/flowdealorders/list";
                                        });
                    } else if (result.code == -1000) {
                        window.location.href = data;
                    } else if (result.code == 0 || result.code == -1 || result.code == -5) {
                    	easy2go.toast('error', result.msg);
                    } else {
                    	//easy2go.toast('error', result.msg, true, 0);
                    	bootbox.alert(result.msg,function(){
                    		window.location="<%=basePath%>orders/flowdealorders/list";
                                        });
                    }

				}
			});
		}
   
   
   function selectid(){
	  //alert(1);
	    var t=$("#slect_sn").find("option:selected").text();
	    $("#deviceDeal_SN").val(t);
	   // alert($("#deviceDeal_ID").val());
   }
   
   $(function(){
	 
   });
	</SCRIPT>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>