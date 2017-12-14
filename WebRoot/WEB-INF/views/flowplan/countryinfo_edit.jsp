<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>
  <head>
    <title>编辑国家-国家管理-流量运营中心</title>
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
						<h4 class="panel-title">更新国家信息</h4>
					</div>
					<div class="panel-body">
						<form id="edit_form" role="form" action="" method="post" autocomplete="off" class="form-horizontal">
							<input type="hidden" name="_csrf" value="pgByHDFK-RkGITAHnf3oznJXIqxTdn3Y2z8o">
							<input type="hidden" name="countryID" value="${Model.countryID}">
							<input type="hidden" name="imgsrc" value="${Model.imgsrc}">
							<div class="form-group">
								<label class="col-md-3 control-label">国家名称：</label>
								<div class="col-md-6">
									<input type="text" name="countryName" value="${Model.countryName}" data-popover-offset="0,8" required maxlength="32" class="form-control">
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
									<input type="number" name="countryCode" value="${Model.countryCode}" min="0" max="999" data-popover-offset="0,8" required maxlength="3" class="form-control">
								</div>
								<div class="col-md-3">
									<p class="form-control-static">
										<span class="red">*</span>
									</p>
								</div>
							</div>
                            <div class="form-group">
                               <label class="col-md-3 control-label">所属大洲：</label>
                               <div class="col-md-6">
                                   <select name="continent" required class="form-control">
    <c:forEach items="${Continents}" var="item" varStatus="status">
                                   <c:if test="${Model.continent eq item.label}">
					                  <option value="${item.label}" selected="selected">${item.label}</option>
					                </c:if>
					                <c:if test="${Model.continent ne item.label}">
					                  <option value="${item.label}">${item.label}</option>
					                </c:if>
    </c:forEach>
                                   </select>
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
									<input type="number" name="flowPrice" value="${Model.flowPrice}"  data-popover-offset="0,8" required class="form-control">
								</div>
								<div class="col-md-3">
                                    <p class="form-control-static">
                                        <span class="red">*</span>
                                    </p>
                                </div>
							</div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">本地卡代号说明：</label>
                                <div class="col-md-6">
                                    <input type="text" name="localCodeExplain" value="${Model.localCodeExplain}"  data-popover-offset="0,8" class="form-control">
                                </div>
                                <div class="col-md-3">
                                    <p class="form-control-static">
                                        <span class="red"></span>
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">按流量价格：</label>
                                <div class="col-md-6">
                                    <input type="number" name="pressFlowPrice" value="${Model.pressFlowPrice}"  data-popover-offset="0,8" class="form-control">
                                </div>
                                <div class="col-md-3">
                                    <p class="form-control-static">
                                        <span class="red">及时补充上这个价格</span>
                                    </p>
                                </div>
                            </div>
							<div class="form-group">
								<label class="col-md-3 control-label">限速策略：</label>
								<div class="col-md-6">
									<input type="text" name="limitSpeedStr" value="${Model.limitSpeedStr}" required class="form-control">
								</div>
								<div class="col-md-3">
                                    <p class="form-control-static">
                                        <span class="red">*只支持5个阶梯限速</span>
                                    </p>
                                </div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">序号：</label>
								<div class="col-md-6">
									<input type="text" name="sortCode" value="${Model.sortCode}" required class="form-control">
								</div>
								<div class="col-md-3">
                                    <p class="form-control-static">
                                        <span class="red">*</span>
                                    </p>
                                </div>
							</div>
                            <div class="form-group">
                              <label for="sim_server_desc" class="col-md-3 control-label">备注:</label>
                              <div class="col-md-6">
                                <textarea rows="3" name="remark" maxlength="99" data-popover-offset="0,8"
                                class="form-control">${Model.remark}</textarea>
                              </div>
                              <div class="col-sm-3">备注部分：添加国家中文英文拼音等,便于官网搜索!(例如:&nbsp;&nbsp;china中国zhongguo)格式自定</div>
                            </div>
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
                'countryCode':{required:true, range:[100,999]},
                'flowPrice':{required:true,number:true},
                'pressFlowPrice':{number:true},
                'remark':{maxlength:99},
            },
            messages:{
                'countryName':{required:"必填字段",maxlength:"少于32个字符"},
                'countryCode':{required:"MCC为3位数字, 不足前面补零",range:"MCC为3位数字, 不足前面补零"},
                'flowPrice':{required:"必填字段",number:"需要为数字"},
                'pressFlowPrice':{number:"需要为数字"},
                'remark':{maxlength:"最大字符数为100"},
            },
            submitHandler: function(form){
            	if($('input[name="pressFlowPrice"]').val() == ''){
            		if(!confirm('“按流量价格”应该及时补充上，现在未提供，确定继续吗？')){
            			  return false;
            		}
            	}
		        $.ajax({
		            type:"POST",
		            url:"<%=basePath %>flowplan/countryinfo/save",
		            dataType:"html",
		            data:$('#edit_form').serialize(),
		            success:function(data){
		                result = jQuery.parseJSON(data);
		                if (result.code == 0) { // 成功保存
		                    easy2go.toast('info', result.msg);
		                    window.location.href = redirect;
		                    var redirect = "<%=request.getHeader("Referer") %>";
		                   if(redirect==null) {
		                     window.location.href="<%=basePath %>flowplan/countryinfo/index";
		                   } else {
		                     window.location.href=redirect;
		                   }
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