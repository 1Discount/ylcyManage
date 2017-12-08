<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<c:if test="${Model.ICCID eq null}">
	<%-- 通过必选字符串字段为null判断为添加记录 --%>
	<title>添加本地卡-本地SIM卡管理-EASY2GO ADMIN</title>
</c:if>
<c:if test="${Model.ICCID ne null}">
	<title>编辑本地卡-本地SIM卡管理-EASY2GO ADMIN</title>
</c:if>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet"
	href="<%=basePath%>static/css/app.min.css?20150209">
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
				<div class="col-md-12">
					<div class="panel">
						<div class="panel-heading">
							<c:if test="${Model.IMSI eq null}">
								<%-- 通过必选字符串字段为null判断为添加记录 --%>
    添加SIM卡信息</c:if>
							<c:if test="${Model.IMSI ne null}">
    更新虚拟卡信息</c:if>
						</div>
						<div class="panel-body">
							<form id="edit_form" role="form" action="" method="post"
								autocomplete="off" class="form-horizontal">
								<input type="hidden" name="SIMinfoID" value="${Model.SIMinfoID} ">
								<div class="form-group">
									<label for="sim_code" class="col-sm-3 control-label">IMSI：</label>
									<div class="col-sm-6">
										<input id="sim_code" type="text" value="${Model.IMSI}"
											name="IMSI" data-popover-offset="0,8" required
											class="form-control">
									</div>
									<div class="col-sm-3">
										<p class="form-control-static">
											<span class="red"> * IMSI号码</span>
										</p>
									</div>
								</div>

								<div id="countries" class="form-group">
									<label class="col-sm-3 control-label">可使用国家：</label>
									<div class="col-sm-6">
										<div class="checkbox">
											<c:set var="preivousContinent" value="" />
											<%-- !! 以下约定国家已按大洲的顺序排列 --%>
											<c:forEach items="${Countries}" var="country"
												varStatus="status">
												<c:if test="${preivousContinent ne country.continent}">
													<div class="checkbox-group">
														<strong>${country.continent}</strong>
													</div>
													<c:set var="preivousContinent" value="${country.continent}" />
												</c:if>
												<c:if test="${country.selected}">
													<label class="checkbox-items"><input
														type="checkbox" name="countryList"
														value="${country.countryCode}" checked>${country.countryName}</label>
												</c:if>
												<c:if test="${!country.selected}">
													<label class="checkbox-items"><input
														type="checkbox" name="countryList"
														value="${country.countryCode}">${country.countryName}</label>
												</c:if>
											</c:forEach>
										</div>
									</div>
									<div class="col-sm-3">
										<p class="form-control-static">
											<span class="red">* SIM卡可使用的国家, 此卡可多国漫游使用, 由 Sim
												Server 匹配使用</span>
										</p>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-3 control-label">上次绑定SN：</label>
									<div class="col-sm-6">
										<input type="text" name="lastDeviceSN" maxlength="20"
											value="${Model.lastDeviceSN}" data-popover-offset="0,8"
											class="form-control">
									</div>
									<div class="col-sm-3">
										<p class="form-control-static">
											<span class="red">(空字符串表示不手动选网)</span>
										</p>
									</div>
								</div>
								 <div class="form-group">
						              <label class="col-sm-3 control-label">SIM卡代号：</label>
						              <div class="col-sm-6">
						                <input type="text" name="simAlias" maxlength="10" value="${Model.simAlias}" data-popover-offset="0,8" class="form-control">
						              </div>
						              <div class="col-sm-3">
						                <p class="form-control-static"><span class="red">内部使用代号, 如 AN-5 等</span>
						                </p>
						              </div>
						            </div>
						

								<div class="form-group">
									<label class="col-sm-3 control-label">使用状态：</label>
									<div class="col-sm-6">
										<c:forEach items="${CardStatusDict}" var="item"
											varStatus="status">
											<c:if test="${item.label ne '停用' && item.label ne '下架'}">
											<label for="simStatus${status.index}" class="radio-inline">
												<c:if test="${Model.cardStatus eq item.label }">
													<input type="radio" name="cardStatus"
														id="simStatus${status.index}" value="${item.label}"
														checked>${item.label}</c:if> <c:if
													test="${Model.cardStatus ne item.label && Model.cardStatus ne '停用' && Model.cardStatus ne '下架'}">
													<input type="radio" name="cardStatus"
														id="simStatus${status.index}" value="${item.label}">${item.label}</c:if>
											</label>
											</c:if>
										</c:forEach>
									</div>
									<div class="col-sm-3">
										<p class="form-control-static">
											<span class="red"> </span>
										</p>
									</div>
								</div>


								<div class="form-group">
									<div class="col-sm-6 col-sm-offset-3">
										<div class="btn-toolbar">
											<button type="submit" class="btn btn-primary">保存</button>
											<button type="button" onclick="javascript:history.go(-1);"
												class="btn btn-default">返回</button>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>

			</section>
		</section>
	</section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script
		src="<%=basePath%>static/js/bootstrap-treeview/js/bootstrap-treeview.js"></script>
	<script src="<%=basePath%>static/js/byteunit.js"></script>
	<script type="text/javascript">
	    $(function(){ 
		    $("#edit_form").validate_popover({
		        rules:{
		            'countryList':{ required:true },
		            'IMSI':{ required:true,number:true,minlength:18,maxlength:18 },
		            'speedLimit':{ number:true,max:9999 },
		            'PIN':{ number:true },
		            'PUK':{ number:true },
		            'planPrice':{required:true,number:true,min:0}, // 最小值为零,因为有可能系免费套餐
		            'planData':{ number:true },
		            'planRemainData':{ number:true },
		            'cardInitialBalance':{ number:true },
		            'cardBalance':{ number:true },
		            'selnet':{ maxlength:20 },
		            'IMEI':{ minlength:0,maxlength:15,number:true},
		            'cardStatus':{required:true}
		        },
		        messages:{
		            'countryList':{required:"请选择国家"},
		            'IMSI':{required:"IMSI是18位数字",number:"IMSI是18位数字",minlength:"IMSI是18位数字",maxlength:"IMSI是18位数字"},
		            'speedLimit':{number:"请输入数字",max:"最大值 9999 KB/S"},
		            'PIN':{number:"请输入数字"},
		            'PUK':{number:"请输入数字"},
		            'planPrice':{required:"必填字段",number:"请输入数字"},
		            'planData':{number:"请输入数字"},
		            'planRemainData':{number:"请输入数字"},
		            'cardInitialBalance':{number:"请输入数字"},
		            'cardBalance':{number:"请输入数字"},
		            'selnet':{ max:"最大长度20" },
		            'IMEI':{maxlength:"15位数字,不填或填0都表示空",number:"IMEI是15位数字"},
		            'cardStatus':{required:"请选择卡状态"}
		        },
		        submitHandler: function(form){
			    
			        $.ajax({
			            type:"POST",
			            url:"<%=basePath %>sim/virtualsiminfo/save",
			            dataType:"html",
			            data:$('#edit_form').serialize(),
			            success:function(data){
			            	if(data==1){
			             		  easy2go.toast('info',"保存成功");
			             	}else{
			             		  easy2go.toast('error',"保存失败");
			             	}
			            }
			        });
		        }
		    });
	    });

    </script>

	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>