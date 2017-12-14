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
<title>添加TDM-TDM管理-流量运营中心</title>
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
						<DIV class="panel-heading">添加TDM</DIV>
						<DIV class="panel-body">
							<FORM id="tdm_form" class="form-horizontal" role="form" method="post" action="">
								<DIV class="form-group">
									<LABEL class="col-md-2 control-label">名称:</LABEL>
									<DIV class="col-md-6">
										<INPUT id="tdmName" class="form-control" name="tdmName" type="text" value="${tdm.tdmName}">
									</DIV>
									<DIV class="col-md-4">
										<P class="form-control-static"><span class="red">* tdm服务器名称</span></P>
									</DIV>
								</DIV>
								<DIV class="form-group">
									<LABEL class="col-md-2 control-label">地址:</LABEL>
									<DIV class="col-md-6">
										<INPUT id="tdmAddress" class="form-control" name="tdmAddress" type="text" value="${tdm.tdmAddress}">
									</DIV>
									<DIV class="col-md-4">
										<P class="form-control-static"><span class="red">tdm服务器所在的物理地址</span></P>
									</DIV>
								</DIV>
								<DIV class="form-group">
									<LABEL class="col-md-2 control-label">URL:</LABEL>
									<DIV class="col-md-6">
										<INPUT id="tdmUrl" class="form-control" name="tdmUrl" type="text" value="${tdm.tdmUrl}">
									</DIV>
									<DIV class="col-md-4">
										<P class="form-control-static"><span class="red">* 访问tdm服务器地址</span></P>
									</DIV>
								</DIV>
								<DIV class="form-group">
									<LABEL class="col-md-2 control-label">内网URL:</LABEL>
									<DIV class="col-md-6">
										<INPUT id="intranetUrl" class="form-control" name="intranetUrl" type="text" value="${tdm.intranetUrl}">
									</DIV>
									<DIV class="col-md-4">
										<P class="form-control-static"><span class="red">* tdm服务器的内网地址</span></P>
									</DIV>
								</DIV>
								<DIV class="form-group">
									<LABEL class="col-md-2 control-label">服务器编号:</LABEL>
									<DIV class="col-md-6">
										<INPUT id="serverCode" class="form-control" name="serverCode" type="text" value="${tdm.serverCode}">
									</DIV>
									<DIV class="col-md-4">
										<P class="form-control-static"><span class="red">* tdm服务器的ServerCode，前五位和对应渠道商的serverCode一致</span></P>
									</DIV>
								</DIV>
								
								<DIV class="form-group">
									<LABEL class="col-md-2 control-label">排序:</LABEL>
									<DIV class="col-md-6">
										<INPUT id="sort" class="form-control" name="sort" type="text"  value="${tdm.sort}">
									</DIV>
									<DIV class="col-md-4">
										<P class="form-control-static"><span class="red">tdm服务器排序</span></P>
									</DIV>
								</DIV>
								<DIV class="form-group">
									<LABEL class="col-md-2 control-label">软件版本:</LABEL>
									<DIV class="col-md-6">
										<INPUT id="sort" class="form-control" name="softwareVersion" type="text"  value="${tdm.softwareVersion}">
									</DIV>
									<DIV class="col-md-4">
										<P class="form-control-static"><span class="red">tdm服务器软件版本</span></P>
									</DIV>
								</DIV>
								
								<DIV class="form-group">
									<LABEL class="col-md-2 control-label">状态:</LABEL>
									<DIV class="col-md-6">
										<INPUT id="tdmStatus" class="radio-inline" name="tdmStatus" type="radio" value="0" <c:if test="${tdm.tdmStatus eq 0}">checked="checked"</c:if>>&nbsp;创建中
										<INPUT id="tdmStatus" class="radio-inline" name="tdmStatus" type="radio" value="1" <c:if test="${tdm.tdmStatus eq 1 || tdm.tdmStatus eq '' || tdm.tdmStatus eq null}">checked="checked"</c:if>>&nbsp;正常
										<INPUT id="tdmStatus" class="radio-inline" name="tdmStatus" type="radio" value="2" <c:if test="${tdm.tdmStatus eq 2}">checked="checked"</c:if>>&nbsp;异常
										<INPUT id="tdmStatus" class="radio-inline" name="tdmStatus" type="radio" value="3" <c:if test="${tdm.tdmStatus eq 3}">checked="checked"</c:if>>&nbsp;暂停
										<INPUT id="tdmStatus" class="radio-inline" name="tdmStatus" type="radio" value="4" <c:if test="${tdm.tdmStatus eq 4}">checked="checked"</c:if>>&nbsp;停止
									</DIV>
									<DIV class="col-md-4">
										<P class="form-control-static"><span class="red">* tdm服务器的状态</span></P>
									</DIV>
								</DIV>
								
								<DIV class="form-group">
									<LABEL class="col-md-2 control-label">拥有的渠道商:</LABEL>
									<DIV class="col-md-6">
										<select id="ownerId" name="ownerId">
											<c:forEach items="${disList}" var="dis">
												<option value="${dis.distributorID}" <c:if test="${tdm.ownerId eq dis.distributorID }">selected="selected"</c:if> >${dis.company}</option>
											</c:forEach>
										</select>
									</DIV>
									<DIV class="col-md-4">
										<P class="form-control-static"><span class="red">* tdm服务器属于的渠道商</span></P>
									</DIV>
								</DIV>
								 <div class="form-group">
	          						<label for="sim_desc" class="col-sm-3 control-label">备注：</label>
	          						<div class="col-sm-6">
	            						<textarea id="sim_desc" rows="3" name="remarks" maxlength="499" data-popover-offset="0,8"class="form-control"></textarea>
	          						</div>
	          						<div class="col-md-3">
                    					<p class="form-control-static">
                        					<span class="red"><span class="text-info"></span></span>
                    					</p>
                					</div>
	        					</div>
								<DIV class="form-group">
									<DIV class="col-md-7 col-md-offset-2">
										<input type="hidden" name="TDMId" value="${tdm.TDMId}">
										<DIV class="btn-toolbar">
											<BUTTON class="btn btn-primary" type="submit">保存</BUTTON>
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
	</section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<SCRIPT type="text/javascript">
$(function(){
	$("#tdm_form").validate_popover({
        rules:{
            'tdmName':{ required:true },
            'tdmAddress':{ required:true},
            'tdmUrl':{required:true},
            'serverCode':{ required:true,number:true },
            'sort':{ number:true },
            'tdmStatus':{required:true,number:true,min:0}, // 最小值为零,因为有可能系免费套餐
            'OwnerId':{ required:true }
        },
        messages:{
        	 'tdmName':{ required:"请填写服务器名" },
             'tdmAddress':{ required:"请填写服务器地址"},
             'tdmUrl':{required:"请填写访问服务器的URL"},
             'serverCode':{ required:"请填写服务器的ServerCode",number:"服务器的serverCode必须是数字" },
             'sort':{ number:"排序字段必须是数字"},
             'tdmStatus':{required:"tdm的状态是必选的"}, // 最小值为零,因为有可能系免费套餐
             'OwnerId':{ required:"归属渠道商是必填的" }
        },
        submitHandler: function(form){
	        $.ajax({
	            type:"POST",
	            url:"<%=basePath %>tdm/addTDM",
	            dataType:"html",
	            data:$('#tdm_form').serialize(),
	            success:function(data){
	               
	                if (data == 1) { // 成功保存
	                   easy2go.toast('info', "保存成功");
	                   <%-- history.go(-1);
	                   if(redirect==null) {
	                     window.location.href="<%=basePath%>tdm/index";
	                   } else {
	                     window.location.href=redirect;
	                   } --%>
	                } else {
	                	if(data== 0){
	                		easy2go.toast('error', "保存失败");	
	                	}
	                	if(data== 2){
	                		easy2go.toast('error', "请刷新页面重新登录");	
	                	}
	                	if(data== 3){
	                		easy2go.toast('error', "必填字段为空");	
	                	}
	                	if(data== 4){
	                		easy2go.toast('error', "必填字段与其他TDM冲突");	
	                	}
	                	if(data== 5){
	                		easy2go.toast('error', "ID为空");	
	                	}
	                }
	            }
	        });
        }
    });
});
	</SCRIPT>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>