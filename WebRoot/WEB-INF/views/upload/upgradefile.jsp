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
<title><c:if test="${not IsOtherOrderStatus}">全部有效订单-订单管理-流量运营中心</c:if> <c:if test="${IsOtherOrderStatus}">全部其他订单-订单管理-流量运营中心</c:if></title>
<meta charset="utf-8">
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
						<div class="panel-body">
							<form class="form-inline" id="uploadForm" role="form" method="post" enctype="multipart/form-data" action="<%=basePath%>upload/uploadding">
								<div class="form-group">
									<label class="inline-label">文件类型 ：</label>
									<select name="upgradeFile" class="form-control" id="upgradeFile" change="type">
										<option value="xmclient">漫游 xmclient</option>
										<option value="CellDataUpdaterRoam.apk">漫游APK CellDataUpdaterRoam.apk</option>
										<option value="MIP_List.ini">MIP配置 MIP_List.ini</option>
										<option value="local_client">本地 local_client</option>
										<option value="CellDataUpdater.apk">本地APK CellDataUpdater.apk</option>
										<option value="Phone.apk">Phone.apk</option>
										<option value="Settings.apk">Settings.apk</option>
                                        <option value="i-jetty-3.2-SNAPSHOT.apk">Portal i-jetty-3.2-SNAPSHOT.apk</option>
                                        <option value="wifidog">Portal wifidog</option>
                                        <option value="wifidog.conf">Portal wifidog.conf</option>
                                        <option value="wifidog-msg.html">Portal wifidog-msg.html</option>
                                        <option value="root.war">Portal root.war</option>
                                        <option value="console.war">Portal console.war</option>
                                        
                                        <option value="xmclient_4G">漫游 xmclient_4G</option>
										<option value="CellDataUpdaterRoam_4G.apk">漫游APK CellDataUpdaterRoam_4G.apk</option>
										<option value="MIP_List_4G.ini">MIP配置 MIP_List_4G.ini</option>
										<option value="local_client_4G">本地 local_client_4G</option>
										<option value="CellDataUpdater_4G.apk">本地APK CellDataUpdater_4G.apk</option>
										<option value="Phone_4G.apk">Phone_4G.apk</option>
										<option value="Settings_4G.apk">Settings_4G.apk</option>
                                        <option value="i-jetty-3.2-SNAPSHOT_4G.apk">Portal i-jetty-3.2-SNAPSHOT_4G.apk</option>
                                        <option value="wifidog_4G">Portal wifidog_4G</option>
                                        <option value="wifidog_4G.conf">Portal wifidog_4G.conf</option>
                                        <option value="wifidog-msg_4G.html">Portal wifidog-msg_4G.html</option>
                                        <option value="root_4G.war">Portal root_4G.war</option>
                                        <option value="console_4G.war">Portal console_4G.war</option>
									</select>
								</div>
								<div class="form-group">
									<div class="col-sm-3">
										<input type="file" id="file" name="file" data-popover-offset="0,8" required class="form-control" enctype="multipart/form-data">
									</div>
								</div>
								<div class="form-group">
									版本：
									<input id="version" name="version" class="form-control">
								</div>
								<div class="form-group">
									<button class="btn btn-primary" type="button" onclick="upload();">上传</button>
								</div>
							</form>
						</div>
					</DIV>
					<DIV class="panel">
						<DIV class="panel-heading">
							<H3 class="panel-title">文件详情</H3>
						</DIV>
						<DIV class="panel-body">
							<table id="searchTable">
								<tr>
									<th w_index="name" width="10%;">文件名</th>
									<th w_index="version" width="10%;">版本</th>
									<th w_index="size" width="10%;">大小</th>
									<th w_index="time" width="10%;">更新上传时间</th>

								</tr>
							</table>
						</DIV>
					</DIV>
				</DIV>
			</SECTION>
		</SECTION>
	</section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script src="<%=basePath%>static/js/accounting/accounting.min.js"></script>
	<script type="text/javascript">
		$(function(){
			var Msg='${Msg}';
			if(Msg=='00'){
				easy2go.toast('info','上传成功');
			}else if(Msg=='01'){
				easy2go.toast('error','上传失败');
			}
		});
		function upload(){
			 var selectValue=$("select[name='upgradeFile'] option:checked").val();
			 var file=$("#file").val();
			 var fileName = getFileName(file);
			 if(selectValue!=fileName){
				 easy2go.toast('warn','选择的文件不正确');
			 }else{
				 $("#uploadForm").submit();
			 }
		}
		function getFileName(o){
		    var pos=o.lastIndexOf("\\");
		    return o.substring(pos+1);
		}
	   	var gridObj;
	         $(function(){
	            gridObj = $.fn.bsgrid.init('searchTable',{
	               url:'<%=basePath%>upload/queryPage',
			pageAll : true,
			autoLoad : true,
			});
		});
	</script>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
    <jsp:param name="matchType" value="exactly" />
    </jsp:include>
</body>
</html>