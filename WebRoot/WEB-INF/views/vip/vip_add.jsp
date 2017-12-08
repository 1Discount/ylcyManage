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
	<title>添加VIP卡-VIP卡管理-EASY2GO ADMIN</title>
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
   								添加VIP卡
						</div> 
						<div class="panel-body">
							<form id="edit_form" role="form" action="" method="post" autocomplete="off" class="form-horizontal">
								
								<div id="countries" class="form-group">
									<label class="col-sm-3 control-label" style="margin-top:7px;">优惠方式：</label>
									<div class="col-sm-6">
										<div class="checkbox">
												<label class="checkbox-items" style="padding:6px 12px;">
												 	<input type="checkbox" name="preferentialType" value="身份" id="shenfencheckbox">VIP身份
												</label>
												<label class="checkbox-items" style="padding:6px 12px;">
												 	<input type="checkbox" name="preferentialType" value="设备" id="shebeicheckbox">赠送设备
												</label><br />
												<label class="checkbox-items" id="chongzhi" onclick="chongzhi(this)" style="padding:6px 12px;">
												 	<input type="checkbox" id="czcheckbox" name="preferentialType" value="流量费用" id="">流量费用
												 	<input  id="jine" name="liuliang" type="text"  class="form-control" style="display:none; width:auto;" placeholder="请输入充值金额">
												</label><br />
												<label class="checkbox-items" id="chongzhi" onclick="chongzhi(this)" style="padding:6px 12px;">
												 	<input type="checkbox" id="czcheckbox" name="preferentialType" value="话费" id="">话　　费
												 	<input  id="jine" name="huafei" type="text" class="form-control" style="display:none; width:auto;" placeholder="请输入充值金额">
												</label><br />
												<label class="checkbox-items" id="chongzhi" onclick="chongzhi(this)" style="padding:6px 12px;">
												 	<input type="checkbox" id="czcheckbox" name="preferentialType" value="基本帐户" id="">基本帐户
												 	<input  id="jine" name="jiben" type="text" class="form-control" style="display:none; width:auto;" placeholder="请输入充值金额">
												</label>
												
												
										</div>
									</div>
									<div class="col-sm-3">
										<p class="form-control-static">
											<span class="red"></span>
										</p>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">有效期开始时间：</label>
									<div class="col-md-6">
										<div class="inputgroup">
											<input type="text" name="startTime"
												value="${Model.startTime}" data-popover-offset="0,8"
												class="form_datetime form-control">
										</div>
									</div>
									<div class="col-md-3">
										<p class="form-control-static">
											<span class="red"></span>
										</p>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">有效期结束时间：</label>
									<div class="col-md-6">
										<div class="inputgroup">
											<input type="text" name="endTime"
												value="${Model.endTime}" data-popover-offset="0,8"
												class="form_datetime form-control">
										</div>
									</div>
									<div class="col-md-3">
										<p class="form-control-static">
											<span class="red"></span>
										</p>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">数量：</label>
									<div class="col-md-6">
										<div class="inputgroup">
											<input type="text" name="vipcardCount" id="vipcardCount"	class=" form-control">
										</div>
									</div>
									<div class="col-md-3">
										<p class="form-control-static">
											<span class="red"></span>
										</p>
									</div>
								</div>
								 <div class="form-group">
						          <label for="sim_desc" class="col-sm-3 control-label">备注：</label>
						          <div class="col-sm-6">
						            <textarea id="sim_desc" rows="3" name="remark" maxlength="499" data-popover-offset="0,8"
						            class="form-control">${Model.remark}</textarea>
						          </div>
						          <div class="col-md-3">
					                    <p class="form-control-static">
					                        <span class="red"><span class="text-info"></span></span>
					                    </p>
					                </div>
						        </div>
						        <div class="form-group">
									<div class="col-sm-6 col-sm-offset-3">
										<div class="btn-toolbar">
											<button type="submit" class="btn btn-primary">添加</button>
											<button type="button" onclick="javascript:history.go(-1);"
												class="btn btn-default">返回</button>
										</div>
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
	<script src="<%=basePath%>static/js/bootstrap-treeview/js/bootstrap-treeview.js"></script>
	<script src="<%=basePath%>static/js/byteunit.js"></script>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
	<script>$(function () {
    	bootbox.setDefaults("locale","zh_CN");
    $(".form_datetime").datetimepicker({
        format: 'YYYY-MM-DD HH:00',
        pickDate: true,     //en/disables the date picker
        pickTime: true,     //en/disables the time picker
        showToday: true,    //shows the today indicator
        language:'zh-CN',   //sets language locale
        defaultDate: null, // moment().add(7, 'days'), //sets a default date, accepts js dates, strings and moment objects
    });
});</script>
	<script type="text/javascript">
    $(function(){    
    $("#edit_form").validate_popover({
        rules:{
        	'liuliang':{ required:true,number:true},
        	'huafei':{ required:true,number:true},
        	'jiben':{ required:true,number:true},
        	'preferentialType':{required:true},
        	'startTime':{required:true},
        	'endTime':{required:true},
        	'vipcardCount':{required:true},
        },
	        messages:{
	       	 'liuliang':{required:"请输入金额"},
	    	 'huafei':{required:"请输入金额"},
	    	 'jiben':{required:"请输入金额"},
        	 'startTime':{required:"开始时间不能为空"},
        	 'endTime':{required:"结否时间能为空"},
        	 'vipcardCount':{required:"添加VIP卡的数量不能为空"},
        },
        submitHandler: function(form){
	        $.ajax({
	            type:"POST",
	            url:"<%=basePath%>VIPCardInfo/saveadd",
	            dataType:"html",
	            data:$('#edit_form').serialize(),
	            success:function(data){
	               var result = jQuery.parseJSON(data);
	               if (result.code == 1) { 
	                	 easy2go.toast('info',"添加成功,正在跳转 . . .", false, null, {
	                         onClose: function() { history.go(-1); }
	                     });
	                } else {
		                    easy2go.toast('error', result.msg);
	                }
	            }
	        });
        }
    });
	});
    function chongzhi(check){
    	
    	if($(check).children("#czcheckbox").is(':checked')){
    		$(check).children("#jine").css({'display':'inline-block'});
    		//$("#jine").css({'display':'inline-block'});
    	}else{
    		$(check).children("#jine").css({'display':'none'});
    		//$("#jine").css({'display':'none'});
    	}
    }
    </script>

	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>