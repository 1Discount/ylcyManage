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
<title>添加套餐-套餐管理-流量运营中心</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet"
	href="<%=basePath%>static/css/app.min.css?20150209">
	<style>
		.country .countryName{
			display: inline-block;
		}
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
				<div class="col-md-9">
					<div class="panel">
						<div class="panel-heading">
							<h4 class="panel-title">添加套餐信息</h4>
						</div>
						<div class="panel-body">
							<form id="plan_form" role="form" action="" method="post"
								autocomplete="off" class="form-horizontal">
								<div class="form-group">
									<label class="col-md-3 control-label">套餐名称：</label>
									<div class="col-md-6">
										<input type="text" name="flowPlanName"
											data-popover-offset="0,8" required maxlength="32"
											class="form-control">
									</div>
									<div class="col-md-3">
										<p class="form-control-static">
											<span class="red">*</span>
										</p>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">计费模式：</label>
									<div class="col-md-6">
										  <label for="orderType1" class="radio-inline">
						                  <input type="radio" name="orderType" id="orderType1" value="1" checked>M1</label>
						               	  <label for="orderType2" class="radio-inline">
						                  <input type="radio" name="orderType" id="orderType2" value="2" >M2</label>
						               	  <label for="orderType3" class="radio-inline">
						                  <input type="radio" name="orderType" id="orderType3" value="3" >M3</label>
						               	  <label for="orderType4" class="radio-inline">
						                  <input type="radio" name="orderType" id="orderType4" value="4" >M4</label>
									</div>
									<div class="col-md-3">
									<label class="col-md-3 control-label"></label>
										<p class="form-control-static">
											<span class="red"></span>
										</p>
									</div>
								</div>
								
									<div class="form-group">
									<label class="col-md-3 control-label"></label>
									<div class="col-md-6">
										<span class="red">
											M1：有效期内包连续天（24小时），从下单开始计时，有效期等于可用天，每天限速不断网<br />
											M2：有效期内包流量，每天不限速不限流量，总流量用完即断网<br />
											M3：有效期内包不连续天（24小时），用一天计一天，有效期大于可用天，每天限速不断网<br />
											M4：有效期内包连续天（24小时），从接入开始计时，有效期大于可用天，每天限速不断网<br />
										</span>
									</div>
								</div>
								<div id="countries" class="form-group">
									<label class="col-md-3 control-label">国家：</label>
									<div class="col-md-9">
										<div class="checkbox">
											<c:set var="preivousContinent" value="" />
											<c:set var="contryListCheckboxGroupIndex" value="0" />
										
											
											<c:forEach items="${CountryList}" var="country"
												varStatus="status">
												
													<c:if test="${preivousContinent ne country.continent}">
													
														<div class="checkbox-group">
															<strong>${country.continent}</strong> &nbsp;&nbsp
																<label class="checkbox-items">
																	<input type="checkbox" class="selectAllCheckbox" value="${contryListCheckboxGroupIndex + 1}" />
																	<span>全选</span>
																</label>
														</div>
														<c:set var="preivousContinent" value="${country.continent}" />
														<c:set var="contryListCheckboxGroupIndex" value="${contryListCheckboxGroupIndex + 1}" />
															
													</c:if>
													<label class="checkbox-items" style="display: inline-block; padding-right: 0px;" >
														<input type="checkbox" name="countryList" class="countryListCheckboxGroup${contryListCheckboxGroupIndex}" 
														 value="${country.countryCode}">${country.countryName}（${country.flowPrice}/天）
														
													</label>
													
											</c:forEach>
										</div>
										<p class="help-block">
											<span class="red">* 请谨慎选择国家, 全选/清空仅供辅助使用</span>
										</p>
									</div>
								</div>
								
							 	<div id="days" class="form-group">
									<label class="col-md-3 control-label">天数：</label>
									<div class="col-md-6">
										<input type="text" name="planDays" id="planDays" value="" min="0" max="999" data-popover-offset="0,8"  class="form-control" style="display: inline-block; width:90%">
										<input type="button" style="margin-left: 10px;" onclick="addcountryday()" value="确定">
										
									</div>
									<div class="col-md-3">
										<p class="form-control-static">
											<span class="red"></span>
										</p>
									</div>
								</div>
								 <div id="days" class="form-group">
									<label class="col-md-3 control-label">套餐使用国家预览：</label>
									<div class="col-md-9">
										<input type="text"  readonly ="readonly"  name="countryList1" id="yulan"  class="form-control" >
										
										
									</div>
									<div class="col-md-3">
										<p class="form-control-static">
											<span class="red"></span>
										</p>	
									</div>
								</div>
								
								 <div id="days" class="form-group">
									<label class="col-md-3 control-label">套餐天数预览：</label>
									<div class="col-md-9">
										<input type="text" readonly ="readonly" name="userDayList" id="yulan2" value=""  class="form-control" >
									</div>
									<div class="col-md-3">
										<p class="form-control-static">
											<span class="red"></span>
										</p>	
									</div>
								</div>
								
								 <div id="days" class="form-group">
									<label class="col-md-3 control-label">设备数：</label>
									<div class="col-md-6">
										<input type="text"  name="deviceDealCount"  value="1"  class="form-control" >
									</div>
									<div class="col-md-3">
										<p class="form-control-static">
											<span class="red"></span>
										</p>	
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-3 control-label">金额：</label>
									<div class="col-md-6">
										<input type="text" name="planPrice" data-popover-offset="0,8"
											required class="form-control">
									</div>
									<div class="col-md-3">
										<p class="form-control-static">
											<span class="red">* 请参考国家基础价格和天数或流量值来确认套餐金额</span>
										</p>
									</div>
								</div>

								
								<div id="max_data" class="form-group" style="display:none;">
									<label class="col-md-3 control-label">流量值(MB)：</label>
									<div class="col-md-6">
										<div class="input-group">
											<div id="flowSum_pretty_byte_unit" class="input-group-addon">&nbsp;</div>
												<input type="number" readonly="readonly" name="flowSum" value="5120" min="0" max="999999" data-popover-offset="0,58" required class="form-control" />
											<div class="input-group-addon">MB</div>
										</div>
									</div>
									<div class="col-md-3">
										<p class="form-control-static">
											<a class="btn btn-success btn-xs"
												onclick="showByteUnitPicker('input[name=\'flowSum\']');"><span>计算器</span></a>
											<span class="red">* 套餐的流量大小 1GB = 1024MB,1MB = 1024KB</span>
										</p>
									</div>
								</div>
								<div id="max_data" class="form-group">
									<label class="col-md-3 control-label">限速策略:</label>
									<div class="col-md-6">
										<INPUT value="" class="form-control" name="speedLimit" type="text" >
									</div>
									<div class="col-md-3">
										<p class="form-control-static">
											<span class="red">例如：0-2000|50-150|150-24|200-16|250-8</span>
										</p>
									</div>
								</div>
								<div id="days" class="form-group">
									<label class="col-md-3 control-label">有效期天数：</label>
									<div class="col-md-6">
										<input type="number" name="validPeriod" value="100" min="1" max="999" data-popover-offset="0,8" required class="form-control">
									</div>
									<div class="col-md-3">
										<p class="form-control-static">	
											<span class="red">* 购买后允许激活使用的最长天数</span>
										</p>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">备注：</label>
									<div class="col-md-6">
										<textarea rows="3" name="note" data-popover-offset="0,8" maxlength="99" class="form-control"></textarea>
									</div>
								</div>
								<div class="form-group">
									<div class="col-md-9 col-md-offset-3">
										<div class="btn-toolbar">
											<button type="submit" class="btn btn-primary">保存</button>
											<button type="button" onclick="javascript:history.go(-1);"
												class="btn btn-default">取消</button>
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
	<script src="<%=basePath%>static/js/byteunit.js"></script>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
	<script type="text/javascript">
	function addcountryday(){
		 var day = $("#planDays").val();
		 if(day==""){
			 easy2go.toast('warn','请输入天数');
			 return false;
		 } 
		  var inner=$("#yulan").val()+"-";
			$('input[name="countryList"]:checked').each(function(){ 
				var countryCode=$(this).val();
   				var countryName = $.trim($(this).parent().text());
   				inner = inner+countryName.replace('（',','+countryCode+',').replace('）','').replace('/天','')+"\|";
   			}); 
			if(inner.substring(0,1)=="-"){
				inner=inner.substring(1,inner.length-1);
			}else{
				inner=inner.substring(0,inner.length-1);
			}
			$("#yulan").val(inner);
			var yulan2=$("#yulan2").val();
			if($("#yulan2").val()==""){
				$("#yulan2").val(day);
			}else{
				$("#yulan2").val(yulan2+"-"+day);
			}
			//将选中的复选框全部取消
			$('input[name="countryList"]').attr("checked",false);
			$("#planDays").val("");
			 
			
			/*   var inner=$("#yulan").val()+"|";
				$('input[name="countryList"]:checked').each(function(){ 
					var countryCode=$(this).val();
	   				var countryName = $.trim($(this).parent().text());
	   				inner = inner+countryName.replace('（',','+countryCode+',').replace('）','').replace('/天','')+"\-";
	   			}); 
				if(inner.substring(0,1)=="|"){
					inner=inner.substring(1,inner.length-1);
				}else{
					inner=inner.substring(0,inner.length-1);
				}
				$("#yulan").val(inner);
				var yulan2=$("#yulan2").val();
				if($("#yulan2").val()==""){
					$("#yulan2").val(day);
				}else{
					$("#yulan2").val(yulan2+"-"+day);
				}
				//将选中的复选框全部取消
				$('input[name="countryList"]').attr("checked",false);
				$("#planDays").val("");  */
			
	}
	$(function(){
    	bootbox.setDefaults("locale","zh_CN");
    	$("input[name=orderType]").click(function(){
    		if($(this).val()=="2"){
    			$("#max_data").css({'display':'block'});
    		}else{
    			$("#max_data").css({'display':'none'});
    		}
    	});
    	
	    $('select[name="planType"]').change(function(){
	     if($(this).val()=='天数'){
	        $('input[name="flowSum"]').attr("readonly","readonly");
	        $('input[name="planDays"]').removeAttr("readonly");
	      } else if($(this).val()=='流量'){
	        $('input[name="planDays"]').attr("readonly","readonly");
	        $('input[name="flowSum"]').removeAttr("readonly");
	      }
	    });
    $('.selectAllCheckbox').click(function(){
        if($(this).prop("checked")) {
            $(this).next().empty().append("清空"); // 确定把文字放到一个node内,目前即指span
            $('.countryListCheckboxGroup'+$(this).attr("value")).prop("checked", true);
        } else {
            $(this).next().empty().append("全选");
            $('.countryListCheckboxGroup'+$(this).attr("value")).removeProp("checked");
        }
    });    
    $('input[name="flowSum"]').keyup(function(){
    	var size = $('input[name="flowSum"]').val();
    	if(size.length > 0) {
    		$('#flowSum_pretty_byte_unit').empty().append(prettyByteUnitSize(size,2,2,true));
    	} else {
    		$('#flowSum_pretty_byte_unit').empty().append("&nbsp;");
    	}
    });
    $('#flowSum_pretty_byte_unit').empty().append(prettyByteUnitSize($('input[name="flowSum"]').val(),2,2,true));
});</script>
	<script type="text/javascript">
	$(function(){
	    $("#plan_form").validate_popover({
	   		rules:{
	   			'countryList1':{ required:true },
	   			'planPrice':{required:true,number:true},
	   			'days':{required:true},
	   		},
	   		messages:{
	   			'countryList1':{required:"请填写套餐使用国家信息"},
	            'planPrice':{required:"必填字段",number:"请输入数字"},
	            'days':{required:"请输入天数"},
	   		},
	   		submitHandler: function(form){
		        $.ajax({
		            type:"POST",
		            url:"<%=basePath%>flowplan/flowplaninfo/addFlowPlan",
		            dataType:"html",
		            data:$('#plan_form').serialize(),
		            success:function(data){
		                result = jQuery.parseJSON(data);
		                if (result.code == 0) {
		                	  easy2go.toast('info',"保存成功, 正在跳转到列表页面", false, null, {
				  					onClose: function() { history.go(-1); }
				  				});
						} else {
								easy2go.toast('error',result.msg);
						}
					}
				});
			  }
		});
	});

		function showByteUnitPicker(selector) {
			if (selector == null || selector.lenght == 0) {
				alert("打开出错, 需要提供选择器, 请联系管理员");
				return;
			}
			bootbox
					.dialog({
						title : "请输入需要大小, 最终结果会折算成M字节(MB)",
						message : '<div class="row">  '
								+ '<div class="col-md-12"> '
								+ '<form class="form-horizontal" id="byte-unit-picker-form"> '
								+ '<div class="form-group"> '
								+ '<label class="col-md-4 control-label" for="name">GB:</label> '
								+ '<div class="col-md-4"> <div class="input-group">'
								+ '<input id="gb" name="gb" type="text" value="0" placeholder="" class="form-control input-md" data-popover-offset="0,58"> '
								+ '<div class="input-group-addon">G</div></div><span>吉字节 1G = 1024M</div> '
								+ '</div> '
								+ '<div class="form-group"> '
								+ '<label class="col-md-4 control-label" for="name">MB:</label> '
								+ '<div class="col-md-4"> <div class="input-group">'
								+ '<input id="mb" name="mb" type="text" value="0" placeholder="" class="form-control input-md" data-popover-offset="0,58"> '
								+ '<div class="input-group-addon">M</div></div><span>兆字节 1M = 1024K</div> '
								+ '</div> '
								+ '<div class="form-group"> '
								+ '<label class="col-md-4 control-label" for="name">最终结果:</label> '
								+ '<div class="col-md-4"> <div class="input-group">'
								+ '<input id="kb-result" readonly="readonly" name="kb-result" type="text" value="0" class="form-control input-md"> '
								+ '<div class="input-group-addon">M</div></div></div><div class="col-md-4">以上可以组合输入, 此处为相加的结果</div> '
								+ '</div> ' + '</form> </div>  </div>',
						buttons : {
							cancel : {
								label : "取消",
								className : "btn-default",
								callback : function() {
								}
							},
							success : {
								label : "设定",
								className : "btn-success byte-unit-picker-button-ok",
								callback : function() {
									$(selector).val($("#kb-result").val());
	<%-- 目前设定输入框前缀的addon div与input框总相邻 --%>
		$(selector).prev().empty().append(
											prettyByteUnitSize($("#kb-result")
													.val(), 2, 2, true));
								}
							}
						}
					});

			$("button.byte-unit-picker-button-ok").prop("disabled", true); // 初始时禁止

			$("#byte-unit-picker-form").validate_popover({
				rules : {
					'gb' : {
						number : true
					},
					'mb' : {
						number : true
					},
				},
				messages : {
					'gb' : {
						number : "请输入数字"
					},
					'mb' : {
						number : "请输入数字"
					},
				}
			});

			$("#gb")
					.keyup(
							function() {
								if ($("#gb").val() >= 0 && $("#mb").val() >= 0) {
									$("#kb-result").val(
											$("#gb").val() * 1024
													+ $("#mb").val() * 1);
									$("button.byte-unit-picker-button-ok")
											.prop("disabled", false);
								} else {
									$("button.byte-unit-picker-button-ok")
											.prop("disabled", true);
									$("#kb-result").val(0);
								}
							});
			$("#mb")
					.keyup(
							function() {
								if ($("#gb").val() >= 0 && $("#mb").val() >= 0) {
									$("#kb-result").val(
											$("#gb").val() * 1024
													+ $("#mb").val() * 1);
									$("button.byte-unit-picker-button-ok")
											.prop("disabled", false);
								} else {
									$("button.byte-unit-picker-button-ok")
											.prop("disabled", true);
									$("#kb-result").val(0);
								}
							});

		}
	</script>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>