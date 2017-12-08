<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>
  <head>
    <title>添加国家-国家管理-EASY2GO ADMIN</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="<%=basePath %>static/css/app.min.css?20150209">
    <meta name="csrf_token">
    <%@include file="/WEB-INF/views/common/_ie8support.html" %>
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
						<h4 class="panel-title">添加国家信息</h4>
					</div>
					<div class="panel-body">
						<form id="edit_form" role="form" action="" method="post" autocomplete="off" class="form-horizontal">
							<input type="hidden" name="_csrf" value="pgByHDFK-RkGITAHnf3oznJXIqxTdn3Y2z8o">
							<div class="form-group">
								<label class="col-md-3 control-label">国家名称：</label>
								<div class="col-md-6">
									<input type="text" name="countryName" data-popover-offset="0,8" required maxlength="32" class="form-control">
								</div>
								<div class="col-md-3">
									<p class="form-control-static">
										<span class="red">*</span>
									</p>
								</div>
							</div>
							<div id="days" class="form-group">
								<label class="col-md-3 control-label">MCC国家编号：</label>
								<div class="col-md-6">
									<input type="number" name="countryCode" min="0" max="999" data-popover-offset="0,8" required maxlength="3" class="form-control">
								</div>
								<div class="col-md-3">
                                    <p class="form-control-static">
                                        <span class="red">* 三位MCC, 不足三位前面补零</span>
                                    </p>
                                </div>
							</div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">所属大洲：</label>
                                <div class="col-md-6">
                                    <select name="continent" required class="form-control">
    <c:forEach items="${Continents}" var="item" varStatus="status"><option value="${item.label}">${item.label}</option></c:forEach>	                                  
	                                </select>
<!--                                     <input type="text" name="continent" data-popover-offset="0,8" required maxlength="10" class="form-control"> -->
                                </div>
                                <div class="col-md-3">
                                    <p class="form-control-static">
                                        <span class="red">*</span>
                                    </p>
                                </div>
                            </div>
							<div class="form-group">
								<label class="col-md-3 control-label">数据服务价格(元/天)：</label>
								<div class="col-md-6">
									<input type="text" name="flowPrice" data-popover-offset="0,8" required class="form-control">
								</div>
								<div class="col-md-3">
                                    <p class="form-control-static">
                                        <span class="red">*</span>
                                    </p>
                                </div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">限速策略：</label>
								<div class="col-md-6">
									<input type="text" name="limitSpeedStr" value="0-2000|50-150|150-24|200-16|250-8" required class="form-control">
								</div>
								<div class="col-md-3">
                                    <p class="form-control-static">
                                        <span class="red">*</span>
                                    </p>
                                </div>
							</div>
<!-- 							<div class="form-group"> -->
<!-- 								<label class="col-md-3 control-label">备注：</label> -->
<!-- 								<div class="col-md-6"> -->
<!-- 									<textarea rows="3" name="note" data-popover-offset="0,8" maxlength="255" class="form-control"></textarea> -->
<!-- 								</div> -->
<!-- 							</div> -->
							<div class="form-group">
								<div class="col-md-9 col-md-offset-3">
									<div class="btn-toolbar">
										<button type="submit" class="btn btn-primary">保存</button><button type="button" onclick="javascript:history.go(-1);" class="btn btn-default">取消</button>
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
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
    <script type="text/javascript">$(function(){
        $("#edit_form").validate_popover({
            rules:{
                'countryName':{required:true,maxlength:32},
                'countryCode':{required:true, range:[0,999]},
                'flowPrice':{required:true,number:true},
            },
            messages:{
                'countryName':{required:"必填字段",maxlength:"少于32个字符"},
                'countryCode':{required:"MCC为3位数字, 不足前面补零",range:"MCC为3位数字, 不足前面补零"},
                'flowPrice':{required:"必填字段",number:"需要为数字"},
            },
            submitHandler: function(form){
                $.ajax({
                    type:"POST",
                    url:"<%=basePath %>flowplan/countryinfo/save",
                    dataType:"html",
                    data:$('#edit_form').serialize(),
                    success:function(data){
                        result = jQuery.parseJSON(data);
                        if (result.code == 0) { // 成功保存
                            easy2go.toast('info', result.msg);
                            window.location.href="<%=basePath %>flowplan/countryinfo/index";                    
                        } else {
                            easy2go.toast('error', result.msg);
                        }
                    }
                });
            }
            });
	});</script>

<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>