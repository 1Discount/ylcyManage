<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
<title>修改设备出入库记录-设备管理-EASY2GO ADMIN</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet" href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
<meta name="csrf_token">
<%@include file="/WEB-INF/views/common/_ie8support.html" %>
<%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
<style type="text/css">
#searchTable2 tr {
	height: 40px;
}
</style>
</head>
<body>

	<section id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />

		<!-- ============================================================ -->

		<SECTION id="main-content">
			<SECTION class="wrapper">
				<DIV class="col-md-9">
					<DIV class="panel">
						<DIV class="panel-heading">编辑设备信息</DIV>
						<DIV class="panel-body">
							<FORM id="device_form" class="form-horizontal" role="form" method="post" action="<%=path %>/device/updateOutboundDevicelogs" autocomplete="off">
								<INPUT name="_csrf" value="2w5HyXLN-PpEHUAtXe26G2sWBVeB1rG1G-kw"type="hidden">
								<INPUT name="_method" value="put" type="hidden">
															 <DIV  class="form-group">
									<LABEL class="col-md-3 control-label" for="device_sn">设备号：</LABEL>
									<DIV class="col-md-6">
										<INPUT  value="${shipment.SN}"  disabled="true"  class="form-control" name="SN" type="text"  >
									</DIV>
								</DIV>
								<DIV class="form-group">
									<LABEL class="col-md-3 control-label" for="device_sn">领用人姓名：</LABEL>
									<DIV class="col-md-6">
										<INPUT id="device_sn" value="${shipment.recipientName}" class="form-control" name="recipientName" type="text"  >
									</DIV>
								</DIV>
								<DIV class="form-group">
									<LABEL class="col-md-3 control-label" for="device_sn">设备出入库状态：</LABEL>
									<DIV class="col-md-6">
										<INPUT value="${shipment.deviceStatus}"  disabled="true"  class="form-control" name="deviceStatus" type="text"  >
									</DIV>
								</DIV>
                <DIV class="form-group">
                   <LABEL class="col-md-3 control-label" for="device_desc">出入库时间：</LABEL>
                   <DIV class="col-md-6">
                       <input id="device_cardid" class="form-control" value="${shipment.shipmentDate}" name="shipmentDate"  placeholder="型号"/>
                   </DIV>
               </DIV>

								<DIV  class="form-group">
									<LABEL class="col-md-3 control-label" for="device_sn">快递名称：</LABEL>
									<DIV class="col-md-6">
										<INPUT  value="${shipment.logisticsName}" class="form-control" name="logisticsName" type="text">
									</DIV>
								</DIV>

								<DIV  class="form-group">
									<LABEL class="col-md-3 control-label" for="device_sn">快递单号：</LABEL>
									<DIV class="col-md-6">
										<INPUT  value="${shipment.expressNO}" class="form-control" name="expressNO" type="text"  >
										<INPUT  value="${shipment.shipmentID}" class="form-control" name="shipmentID" type="hidden"  >
									</DIV>
								</DIV>
								<DIV class="form-group">
									<LABEL class="col-md-3 control-label" for="device_desc">备注：</LABEL>
									<DIV class="col-md-6">
										<TEXTAREA id="device_desc" class="form-control" rows="3"
											name="remark">${shipment.remark}
										</TEXTAREA>
									</DIV>
								</DIV>
								<DIV class="form-group">
									<DIV class="col-md-9 col-md-offset-3">
										<DIV class="btn-toolbar">
											<BUTTON class="btn btn-primary" type="submit" onclick="ckusercountry();">保存</BUTTON>
											<BUTTON class="btn btn-default" onclick="javascript:history.go(-1);" type="button">返回</BUTTON>
										</DIV>
									</DIV>
								</DIV>
							</FORM>
						</DIV>
					</DIV>
				</DIV>
			</SECTION>
		</SECTION>

		<!-- ============================================================ -->

	</section>
	<script>

	</script>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>

<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
<jsp:param name="matchType" value="level1" />
</jsp:include>
</body>
</html>