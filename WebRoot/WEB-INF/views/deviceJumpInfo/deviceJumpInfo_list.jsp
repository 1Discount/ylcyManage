<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
<title>全部跳转记录-跳转记录管理-EASY2GO ADMIN</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet" href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
<style>
a {cursor: pointer;}
</style>
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
				<%-- 这里添加页面主要内容  block content --%>
					<div class="col-md-3">
						<div class="panel">
							<div class="panel-heading">
								<h4 class="panel-title">添加跳转记录</h4>
							</div>
							<div class="panel-body">
									<div class="form-group">
										<label class="col-md-3 control-label">可跳转平台</label>
										<div class="col-md-9">
											<select id="tdmUrl" name="tdmUrl" required class="form-control">
												<option value="">--请选择跳转平台--</option>
												<c:forEach items="${tdmList}" var="item" varStatus="status">
													<option value="${item.tdmUrl}">${item.tdmName}</option>
												</c:forEach>
											</select>
											<p class="help-block">
												<span class="red"></span>
											</p>
										</div>
									</div>
							<div class="form-group">
								<label class="col-md-3 control-label">设备序列号SN</label>
								<div class="col-md-9">
									<input type="text" id="SN" name="SN" data-popover-offset="0,8" class="form-control">
									<p class="help-block">
										<span class="red"></span>
									</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">是否跳回：</label>
								<div>
									<label class="radio-inline">
										&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="ifNeedBack" id="ifNeedBack0" value="0" checked>不跳回
									</label>
									<label class="radio-inline">
										&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="ifNeedBack" id="ifNeedBack1" value="1">跳回
									</label>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label">跳回方式：</label>
								<div >
									<label class="radio-inline">
										&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="backType" id="backType1" value="1" checked>下次开机
									</label>
									<label class="radio-inline">
										&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="backType" id="backType2" value="2">周期重启
									</label>
								</div>
							</div>
							
							<div class="form-group">
								<label for="sim_desc" class="col-sm-3 control-label">备注：</label>
								<div class="col-sm-6">
									<textarea id="remarks" rows="3" cols="100" name="remarks" maxlength="499" data-popover-offset="0,8" class="form-control"></textarea>
								</div>
								<div class="col-md-3">
									<p class="form-control-static">
										<span class="red">
											<span class="text-info"></span>
										</span>
									</p>
								</div>
							</div> 

							<div class="form-group">
										<div class="col-md-9 col-md-offset-3">
											<div class="btn-toolbar">
											<br/>
												<button type="button" class="btn btn-primary" onclick="jump();">确定跳转</button>
											</div>
										</div>
									</div>
							</div>
						</div>
					</div>

				<div class="col-md-9">

				<div class="panel">
					<div class="panel-body">
						<form class="form-inline" id="searchForm" role="form" method="get" action="#">
							<div class="form-group">
								<label class="inline-label">设备序列号(SN)：</label>
								<input class="form-control" name="countryName" type="text" placeholder="">
							</div>
							<div class="form-group">
								<label class="inline-label">TDM服务器：</label>
								<select name="continent" style="width: 179px;" class="form-control">
									<%-- <option value="">全部大洲</option>
									<c:forEach items="${Continents}" var="item" varStatus="status">
										<option value="${item.label}">${item.label}</option>
									</c:forEach> --%>
								</select>
							</div>
							<div class="form-group">
								<button class="btn btn-primary" type="button" onclick="$('input, textarea').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.page(1);">搜索</button>
							</div>
						</form>
					</div>
				</div>
				<div class="panel">
					<div class="panel-heading">
						<h3 class="panel-title">跳转列表</h3>
					</div>
					<div class="panel-body">
						<div class="table-responsive">
							<table id="searchTable">
								<tr>
									<th w_index="deviceJumpId" w_hidden="true">记录id</th>
									<th w_index="flowOrderId" width="15%;">订单号</th>
									<th w_index="sn" width="15%;">设备SN</th>
									<th w_index="currentTDMName" width="15%;">当前TDM</th>
									<th w_index="newTDMName" width="10%;">目的TDM</th>
									<th w_index="newTDMIfSet" width="10%;">设置跳转</th>
									<th w_index="newTDMIfOk" width="15%;">跳转结果</th>
									<th w_index="newMcc" width="5%;">跳转国家</th>
									<th w_index="newTime" width="5%;">跳转时间</th>
									
									<th w_index="ifNeedBack" width="10%;">是否需要调回</th>
									<th w_index="backType" width="10%;">跳回类型</th>
									<th w_index="backTDMName" width="10%;">跳回TDM</th>
									<th w_index="backTDMIfSet" width="10%;">设置跳回</th>
									<th w_index="backTDMIfOk" width="15%;">跳回结果</th>
									<th w_index="backMcc" width="15%;">跳回国家</th>
									<th w_index="backTime" width="5%;">跳回时间</th>
									
									<th w_index="createUserName" width="20%;">创建者</th>
									<th w_index="creatorDate" width="5%;">创建时间</th>
									<th w_index="modifyUserName" width="30%;">更新者</th>
									<th w_index="modifyDate" width="30%;">更新时间</th>
									<th w_index="remarks" width="30%;">备注</th>
									<!-- <th w_render="operate" width="30%;">操作</th> -->
								</tr>
							</table>
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
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script type="text/javascript">
	$(function(){
		gridObj = $.fn.bsgrid.init('searchTable',{
	           url:'<%=basePath%>deviceJumpInfo/list',
	           pageSizeSelect: true,
	           pageSize: 10,
	           pageSizeForGrid:[10,20,30],
	           multiSort:true
	           //pageAll:true
	       });
	});
	function jump(){
		var sn = $("#SN").val();
		var tdmUrl = $("#tdmUrl").val();
		var remarks=$("#remarks").val();
		var ifNeedBack=$("input[name='ifNeedBack']:checked").val();
		var backType=$("input[name='backType']:checked").val();
		alert(sn+";"+tdmUrl+";"+remarks+";"+ifNeedBack+";"+backType);
		if(sn==''){
			easy2go.toast('warn',"请输入SN");
			return;
		}
		if(tdmUrl==''){
			easy2go.toast('warn',"请选择平台");
			return;
		}
		  $.ajax({
	  		 type:"POST",
	  		 url:'<%=basePath%>/deviceJumpInfo/addJump',		  		
	  		 dataType:'html',
	  		 data:{sn:sn,newTDM:tdmUrl,remarks:remarks,ifNeedBack:ifNeedBack,backType:backType},
	  		 success:function(data){
	  			if(data=="0"){
	  				easy2go.toast('warn',"没有该设备订单");
	  			}else if(data=="1"){
	  				easy2go.toast('warn',"跳转成功");
	  			}else if(data=="-1"){
	  				easy2go.toast('warn',"请选择平台");
	  			}else if(data=="-2"){
	  				easy2go.toast('warn',"请输入SN");
	  			}
	  		 }
		   }); 
	}
	<%-- function operate(record, rowIndex, colIndex, options) {
	       return '<A class="btn btn-primary btn-xs" href="<%=basePath %>admin/roleinfo/eidt?roleID='+record.roleID+'&roleName='+record.roleName+'"><SPAN class="glyphicon glyphicon-edit">编辑</SPAN></A>&nbsp;&nbsp;&nbsp;&nbsp;<A class="btn btn-primary btn-xs" onclick="deleteinfo('+rowIndex+')" href="#"><SPAN class="glyphicon glyphicon-remove">删除</SPAN></A>'
	} --%>
	</script>
   	
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>