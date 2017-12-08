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
<title>
<c:if test="${empty vpnInfo.vpnId}">添加VPN-SIM服务器管理-EASY2GO ADMIN</c:if>
<c:if test="${not empty vpnInfo.vpnId}">更新VPN-SIM服务器管理-EASY2GO ADMIN</c:if>
</title>
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
					<!--       <div class="panel"> -->
					<!--         <div class="panel-heading"> -->
					<!--           <h3 class="panel-title">Search</h3> -->
					<!--         </div> -->
					<!--         <div class="panel-body"> -->
					<!--           <form role="form" action="/simservers" method="get" class="form-inline"> -->
					<!--             <div class="form-group"> -->
					<!--               <input type="text" name="code" placeholder="编号" class="form-control"> -->
					<!--             </div> -->
					<!--             <div class="form-group"> -->
					<!--               <button type="submit" class="btn btn-primary">Search</button> -->
					<!--             </div> -->
					<!--           </form> -->
					<!--         </div> -->
					<!--       </div> -->
					<div class="panel">
						<div class="panel-heading">
							<h4 class="form-title">添加VPN</h4>
						</div>
						<div class="panel-body">
							<form id="edit_form" role="form" action="" method="post"
								autocomplete="off" class="form-horizontal">
								<input type="hidden" id="vpnId" name="vpnId" value="${vpnInfo.vpnId}">
								<div class="form-group">
									<label for="vpn" id="vpn_label" class="col-md-3 control-label">VPN:</label>
									<div class="col-md-6">
										<input id="vpn" type="text" name="vpn" value="${vpnInfo.vpn}"
											data-popover-offset="0,8" required class="form-control">
									</div>
									<div class="col-sm-3" id="vpn_geshi">
										<p class="form-control-static">
											<span class="red">内容格式(yt0005|111.206.133.4|yt0005|yt0005)</span>
										</p>
									</div>
								</div>
								<div class="form-group" id="childNum_div">
									<label for="total" class="col-md-3 control-label">子账号数:</label>
									<div class="col-md-6">
										<input id="childNum" type="number" name="childNum" value="${vpnInfo.childNum}"
											data-popover-offset="0,8" required class="form-control">
									</div>
									<div class="col-sm-3"></div>
								</div>
                                <div class="form-group" id="vpnc_div">
                                    <label for="total" class="col-md-3 control-label">VPN子账号:</label>
                                    <div class="col-md-6">
                                        <input type="text" id="vpnc" readonly="readonly" name="vpnc" value="${vpnInfo.vpnc}"
                                            data-popover-offset="0,8" required class="form-control">
                                    </div>
                                    <div class="col-sm-3"></div>
                                </div>
								<div class="form-group">
									<label for="availableNum" class="col-md-3 control-label">状态:</label>
									<div class="col-md-6">
									    <select id="availableNum" name="availableNum" class="form-control">
									       <option value="">请选择</option>
									       <option value="0" <c:if test="${vpnInfo.availableNum=='0'}">selected="selected"</c:if>>不可用</option>
                                           <option value="1" <c:if test="${vpnInfo.availableNum=='1'}">selected="selected"</c:if>>可使用</option>
                                           <option value="2" <c:if test="${vpnInfo.availableNum=='2'}">selected="selected"</c:if>>使用中</option>
									    </select>
									</div>
									<div class="col-sm-3"></div>
								</div>
                                <div class="form-group">
                                    <label for="vpnPackageType" class="col-md-3 control-label">套餐类型:</label>
                                    <div class="col-md-6">
                                        <select id="vpnPackageType" name="vpnPackageType" required class="form-control">
						                  <option value="">请选择</option>
						                  <option value="包月" <c:if test="${vpnInfo.vpnPackageType=='包月'}">selected="selected"</c:if>>包月</option>
						                  <option value="流量" <c:if test="${vpnInfo.vpnPackageType=='流量'}">selected="selected"</c:if>>流量</option>      
						                </select>
                                    </div>
                                    <div class="col-sm-3"></div>
                                </div>
                                <div class="form-group">
                                    <label for="vpnType" class="col-md-3 control-label">VPN类型:</label>
                                    <div class="col-md-6">
                                        <select id="vpnType" name="vpnType" required class="form-control">
                                          <option value="">请选择</option>
                                          <option value="2" <c:if test="${vpnInfo.vpnType=='2'}">selected="selected"</c:if>>小流量</option>
                                          <option value="1" <c:if test="${vpnInfo.vpnType=='1'}">selected="selected"</c:if>>大流量</option>      
                                        </select>
                                    </div>
                                    <div class="col-sm-3"></div>
                                </div>
                                <div class="form-group">
                                    <label for="lastDeviceSN" class="col-md-3 control-label">SN:</label>
                                    <div class="col-md-6">
                                        <input id="lastDeviceSN" type="text" name="lastDeviceSN" value="${vpnInfo.lastDeviceSN}"
                                            data-popover-offset="0,8" class="form-control">
                                    </div>
                                    <div class="col-sm-3"></div>
                                </div>
                                <div class="form-group">
                                    <label for="number" class="col-md-3 control-label">代号:</label>
                                    <div class="col-md-6">
                                        <div class="inputgroup">
                                        <input id="number" type="text" <c:if test="${not empty vpnInfo.vpnId}">readonly="readonly"</c:if> name="number" value="${vpnInfo.number}" 
                                            data-popover-offset="0,8" class="form-control">
                                        </div>
                                    </div>
                                    <div class="col-sm-3"></div>
                                </div>
                                <div class="form-group">
                                    <label for="begainTime" class="col-md-3 control-label">开始时间:</label>
                                    <div class="col-md-6">
                                        <div class="inputgroup">
                                        <input type="text" id="begainTime" name="begainTime" value="${vpnInfo.begainTime}" data-popover-offset="0,8"
                                            class="form_datetime form-control">
                                        </div>
                                    </div>
                                    <div class="col-sm-3"></div>
                                </div>
                                <div class="form-group">
                                    <label for="endTime" class="col-md-3 control-label">截止时间:</label>
                                    <div class="col-md-6">
                                        <div class="inputgroup">
                                        <input type="text" id="endTime" name="endTime" value="${vpnInfo.endTime}"
                                        data-popover-offset="0,8" class="form_datetime form-control">
                                        </div>
                                    </div>
                                    <div class="col-sm-3"></div>
                                </div>
                                <div class="form-group">
                                    <label for="includeFlow" class="col-md-3 control-label">包含流量:</label>
						              <div class="col-md-6">
						                <div class="input-group">
						                  <div id="includeFlow" class="input-group-addon">&nbsp;</div>
                                              <c:if test="${not empty vpnInfo.includeFlow}">
                                                <input type="text" name="includeFlow" value="${vpnInfo.includeFlow}" data-popover-offset="0,58" class="form-control">
                                              </c:if>
                                              <c:if test="${empty vpnInfo.includeFlow}">
                                                <input type="text" name="includeFlow" value="0" data-popover-offset="0,58" class="form-control">
                                              </c:if>
						                  <div class="input-group-addon">KB</div>
						                </div>
						              </div>
						              <div class="col-md-3">
						                <p class="form-control-static"><a class="btn btn-success btn-xs" onclick="showByteUnitPicker('input[name=\'includeFlow\']');"><span>计算器</span></a> <span class="red">* 默认1GB 其中1GB = 1024MB,1MB = 1024KB</span>
						                </p>
						              </div>
                                </div>
                                <c:if test="${not empty vpnInfo.vpnId}">
                                    <div class="form-group">
                                      <label for="useFlow" class="col-md-3 control-label">已使用流量:</label>
                                      <div class="col-md-6">
                                        <div class="input-group">
                                          <div id="useFlow" class="input-group-addon">&nbsp;</div>
	                                          <c:if test="${not empty vpnInfo.useFlow}">
	                                            <input type="text" name="useFlow" value="${vpnInfo.useFlow}" data-popover-offset="0,58" class="form-control">
	                                          </c:if>
	                                          <c:if test="${empty vpnInfo.useFlow}">
                                                <input type="text" name="useFlow" value="0" data-popover-offset="0,58" class="form-control">
                                              </c:if>
                                          <div class="input-group-addon">KB</div>
                                        </div>
                                      </div>
                                      <div class="col-md-3">
                                        <p class="form-control-static"><a class="btn btn-success btn-xs" onclick="showByteUnitPicker('input[name=\'useFlow\']');"><span>计算器</span></a> <span class="red">* 默认1GB 其中1GB = 1024MB,1MB = 1024KB</span>
                                        </p>
                                      </div>
                                    </div>
                                </c:if>
								<div class="form-group">
									<label for="remark" class="col-md-3 control-label">备注:</label>
									<div class="col-md-6">
										<textarea id="remark" rows="3" name="remark" 
											maxlength="99" data-popover-offset="0,8" class="form-control">${vpnInfo.remark}</textarea>
									</div>
									<div class="col-sm-3"></div>
								</div>
								<div class="form-group">
									<div class="col-md-6 col-md-offset-3">
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
    <script src="<%=basePath %>static/js/bootbox.min.js"></script>
    <script src="<%=basePath %>static/js/byteunit.js"></script>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
	<script type="text/javascript">
	$(function(){
		
		if($("#vpn").val()!=""){
			$("#vpn_geshi").hide();
			var vpn = $("#vpn").val().split("|")[1];
			$("#vpn").val(vpn);
			$("#vpn_label").html("VPN IP:");
		}
		
		$("#vpn").keyup(function(){
			var vpnc = $("#vpnc").val();
			var vpnc_first = vpnc.split("|")[0]+"|"+$("#vpn").val()+"|"+vpnc.split("|")[2]+"|"+vpnc.split("|")[3];
			//var vpnc_last = "-"+$("#vpnc").val().split("-")[1];
			$("#vpnc").val(vpnc_first);
		});
		
		if($("#vpnId").val()!=""){
			$("#childNum_div").hide();
            $("#vpnc_div").show();
		}else{

            $("#childNum_div").show();
            $("#vpnc_div").hide();
		}
		
		$('input[name="includeFlow"]').keyup(function(){
	        var size = $.trim($('input[name="includeFlow"]').val());
	        if(size.length > 0) {
	            $('#includeFlow').empty().append(prettyByteUnitSize(size,2,1,true));
	        } else {
	            $('#includeFlow').empty().append("&nbsp;");
	        }
	    });
	    $('#includeFlow').empty().append(prettyByteUnitSize($('input[name="includeFlow"]').val(),2,1,true));
	    $('input[name="useFlow"]').keyup(function(){
	        var size = $.trim($('input[name="useFlow"]').val());
	        if(size.length > 0) {
	            $('#useFlow').empty().append(prettyByteUnitSize(size,2,1,true));
	        } else {
	            $('#useFlow').empty().append("&nbsp;");
	        }
	    });
	    $('#useFlow').empty().append(prettyByteUnitSize($('input[name="useFlow"]').val(),2,1,true));
	});
	$(function(){
	$(".form_datetime").datetimepicker({
        format: 'YYYY-MM-DD HH:00',
        pickDate: true,     //en/disables the date picker
        pickTime: true,     //en/disables the time picker
        showToday: true,    //shows the today indicator
        language:'zh-CN',   //sets language locale
        defaultDate: null, // moment().add(7, 'days'), //sets a default date, accepts js dates, strings and moment objects
    });
	
	/* 必填验证  */
    $("#edit_form").validate_popover({
      rules: {
        'serverCode': {
          required: true,
          maxlength: 5
        },
        'IP': {
          required: true,
//           minlength: 7,
//           maxlength: 15,
          ip: true
        },
        'remark': {
          required: false,
          maxlength: 99
        }
      },
      messages: {
        'serverCode': {
          required: "请输入服务器编号.",
          maxlength: "最多不超过5个字符."
        },
        'IP': {
          required: "请输入服务器IP地址.",
//           minlength: "至少输入7个字符",
//           maxlength: "最多不超过15个字符."
        },
        'remark': {
          maxlength: "最多不超过100字"
        }

      },
        submitHandler: function(form){
	        $.ajax({
	            type:"POST",
	            url:"<%=basePath%>vpn/vpninfo/savevpn",
	            dataType:"html",
	            data:$('#edit_form').serialize(),
	            success:function(data){
	                result = jQuery.parseJSON(data);
	                if (result.code == 0) { // 成功保存
	                    easy2go.toast('info', result.msg);
	                    window.location.href="<%=basePath%>vpn/vpninfo/vpn";
					} else {
						easy2go.toast('error',result.msg);
					}
				},error:function(){
					alert(888);
				}
			});
			}
		});

		});
	function showByteUnitPicker(selector){
        if(selector == null || selector.lenght == 0) {
            alert("打开出错, 需要提供选择器, 请联系管理员");
            return;
        }
        bootbox.dialog({
            title: "请输入需要大小, 最终结果会折算成K字节(KB)",
            message: '<div class="row">  ' +
                '<div class="col-md-12"> ' +
                '<form class="form-horizontal" id="byte-unit-picker-form"> ' +
                '<div class="form-group"> ' +
                '<label class="col-md-4 control-label" for="name">GB:</label> ' +
                '<div class="col-md-4"> <div class="input-group">' +
                '<input id="gb" name="gb" type="text" value="0" placeholder="" class="form-control input-md" data-popover-offset="0,58"> ' +
                '<div class="input-group-addon">G</div></div><span>吉字节 1G = 1024M</div> ' +
                '</div> ' +
                '<div class="form-group"> ' +
                '<label class="col-md-4 control-label" for="name">MB:</label> ' +
                '<div class="col-md-4"> <div class="input-group">' +
                '<input id="mb" name="mb" type="text" value="0" placeholder="" class="form-control input-md" data-popover-offset="0,58"> ' +
                '<div class="input-group-addon">M</div></div><span>兆字节 1M = 1024K</div> ' +
                '</div> ' +
                '<div class="form-group"> ' +
                '<label class="col-md-4 control-label" for="name">KB:</label> ' +
                '<div class="col-md-4"> <div class="input-group">' +
                '<input id="kb" name="kb" type="text" value="0" placeholder="" class="form-control input-md" data-popover-offset="0,58"> ' +
                '<div class="input-group-addon">K</div></div><span>千字节 1K = 1024B (Byte 字节)</div> ' +
                '</div> ' +
                '<div class="form-group"> ' +
                '<label class="col-md-4 control-label" for="name">最终结果:</label> ' +
                '<div class="col-md-4"> <div class="input-group">' +
                '<input id="kb-result" readonly="readonly" name="kb-result" type="text" value="0" class="form-control input-md"> ' +
                '<div class="input-group-addon">K</div></div></div><div class="col-md-4">以上三个可以组合输入, 此处为相加的结果</div> ' +
                '</div> ' +
                '</form> </div>  </div>',
            buttons: {
                cancel: {
                    label: "取消",
                    className: "btn-default",
                    callback: function () {
                    }
                },
                success: {
                    label: "设定",
                    className: "btn-success byte-unit-picker-button-ok",
                    callback: function () {
                        $(selector).val($("#kb-result").val());
                        <%-- 目前设定输入框前缀的addon div与input框总相邻 --%>
                        $(selector).prev().empty().append(prettyByteUnitSize($("#kb-result").val(),2,1,true));
                    }
                }
            }
        });
        $("button.byte-unit-picker-button-ok").prop("disabled", true); // 初始时禁止
        $("#byte-unit-picker-form").validate_popover({
            rules:{
                'gb':{ number:true },
                'mb':{ number:true },
                'kb':{ number:true }
            },
            messages:{
                'gb':{number:"请输入数字"},
                'mb':{number:"请输入数字"},
                'kb':{number:"请输入数字"}
            }
        });
        
        $("#gb").keyup(function(){
            if($("#gb").val() >= 0 && $("#mb").val() >= 0 && $("#kb").val() >= 0 ) {
                $("#kb-result").val($("#gb").val() * 1024 * 1024 + $("#mb").val() * 1024 + $("#kb").val() * 1  );
                $("button.byte-unit-picker-button-ok").prop("disabled", false);
            } else {
                $("button.byte-unit-picker-button-ok").prop("disabled", true);
                $("#kb-result").val(0);
            }
        });
        $("#mb").keyup(function(){
            if($("#gb").val() >= 0 && $("#mb").val() >= 0 && $("#kb").val() >= 0 ) {
                $("#kb-result").val($("#gb").val() * 1024 * 1024 + $("#mb").val() * 1024 + $("#kb").val() * 1  );
                $("button.byte-unit-picker-button-ok").prop("disabled", false);
            } else {
                $("button.byte-unit-picker-button-ok").prop("disabled", true);
                $("#kb-result").val(0);
            }
        });
        $("#kb").keyup(function(){
            if($("#gb").val() >= 0 && $("#mb").val() >= 0 && $("#kb").val() >= 0 ) {
                $("#kb-result").val($("#gb").val() * 1024 * 1024 + $("#mb").val() * 1024 + $("#kb").val() * 1  );
                $("button.byte-unit-picker-button-ok").prop("disabled", false);
            } else {
                $("button.byte-unit-picker-button-ok").prop("disabled", true);
                $("#kb-result").val(0);
            }
        });
	}
	</script>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>