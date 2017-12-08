<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>
  <head>
    <title>运营商列表-数据字典管理-EASY2GO ADMIN</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="<%=basePath %>static/css/app.min.css?20150209">
    <link rel="stylesheet" href="<%=basePath %>static/css/grid/bsgrid.all.min.css">
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
<div class="col-md-3">
<div class="panel">
                    <div class="panel-heading">
                        <h4 class="panel-title">添加运营商</h4>
                    </div>
                    <div class="panel-body">
                        <form id="edit_form" role="form" action="" method="post" autocomplete="off" class="form-horizontal">

                            <div class="form-group">
                                <label class="col-md-3 control-label">名称</label>
                                <div class="col-md-9">
                                    <input type="text" name="operatorName" data-popover-offset="0,8" required maxlength="20" class="form-control">
                                    <p class="help-block"><span class="red">*</span></p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">国家</label>
                                <div class="col-md-9">
                <select id="select_country" name="countryID" required class="form-control">
                <c:forEach items="${Countries}" var="country" varStatus="status"><option id="${country.countryCode}" value="${country.countryID}">${country.countryName}</option></c:forEach>
                </select>
                <input type="hidden" id="countryName" name="countryName"><!-- TODO: 根据前面select值设置 -->
                <input type="hidden" id="MCC" name="MCC">
                               </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 control-label">网址</label>
                                <div class="col-md-9">
                                    <input type="text" name="operatorSite" data-popover-offset="0,8" maxlength="100" class="form-control">
                                </div>
                            </div>
                           <div class="form-group">
                               <label class="col-md-3 control-label">备注</label>
                               <div class="col-md-9">
                                   <textarea rows="3" name="remark" data-popover-offset="0,8" maxlength="99" class="form-control"></textarea>
                               </div>
                           </div>
                            <div class="form-group">
                                <div class="col-md-9 col-md-offset-3">
                                    <div class="btn-toolbar">
                                        <button type="submit" class="btn btn-primary">保存</button>
<!--                                         <button type="button" onclick="javascript:history.go(-1);" class="btn btn-default">取消</button> -->
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
</div>
		<div class="col-md-9">

		
		<div class="panel">
		<div class="panel-body">
		<form class="form-inline" id="searchForm" role="form" method="get" action="#">
		<div class="form-group">
			<label class="inline-label">运营商名称：</label><input class="form-control" name="operatorName" type="text" placeholder="" ></div>
        <div class="form-group">
          <label class="inline-label">国&nbsp;家：</label>
          <select name="countryName" style="width: 130px;"
          class="form-control">
            <option value="">全部国家</option>
            <c:forEach items="${Countries}" var="country" varStatus="status"><option value="${country.countryName}">${country.countryName}</option></c:forEach>
          </select>
        </div>
		<div class="form-group">
		<button class="btn btn-primary" 
		type="button" onclick="$('input').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.page(1);">搜索</button></div></form></div></div>
		<div class="panel">
		<div class="panel-heading">
		<h3 class="panel-title">运营商列表</h3></div>
		<div class="panel-body">
		<div class="table-responsive"><table id="searchTable">
		        <tr>
		        	<th w_index="operatorID" w_hidden="true">运营商ID</th>
                    <th w_index="countryName" width="10%;">国家</th>
                    <th w_index="operatorName">名称</th>
                    <th w_index="MCC">国家代号</th>
		            <th w_index="operatorSite">网址</th>
		            <th w_index="remark">备注</th>
		            <th w_index="creatorDate">创建时间</th>
		            <th w_render="operate" width="20%;">操作</th>
		        </tr>
		</table></div>
		</div></div></div>
          
        </section>
      </section>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <script src="<%=basePath %>static/js/accounting/accounting.min.js"></script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
    <script type="text/javascript">$(function(){
    $("#edit_form").submit(function() {
        $("#MCC").val($("#select_country").find("option:selected").attr("id"));
        $("#countryName").val($("#select_country").find("option:selected").text());
        $.ajax({
            type:"POST",
            url:"<%=basePath %>sim/operatorinfo/save",
            dataType:"html",
            data:$('#edit_form').serialize(),
            success:function(data){
                result = jQuery.parseJSON(data);
                if (result.code == 0) { // 成功保存
                    easy2go.toast('info', result.msg);
                    window.location.href="<%=basePath %>sim/operatorinfo/index";                    
                } else {
                    easy2go.toast('error', result.msg);
                }
            }
        });
        return false;
    });
    });</script>
<c:if test="${IsIndexView}"><%-- 区分回收站记录和正常记录的操作 --%>
    <script type="text/javascript">
    	  var gridObj;
          $(function(){
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath %>sim/operatorinfo/datapage',
                 // autoLoad: false,
                 pageSizeSelect: true,
                 pageSize: 30,
                 pageSizeForGrid:[10,20,30,50,100],
                 multiSort:true
             });
         });
         
         function operate(record, rowIndex, colIndex, options) {
			return '<div class="btn-toolbar">'
+ '<a class="btn btn-primary btn-xs" href="<%=basePath %>sim/operatorinfo/edit/' + record.operatorID + '"><span class="glyphicon glyphicon-edit">编辑</span></a>'
+ '<a class="btn btn-danger btn-xs" href="#" onclick="op_delete(\'' + record.operatorID + '\')"><span class="glyphicon glyphicon-remove">删除</span></a>'
+ '</div>';
         }
        // + '<a class="btn btn-primary btn-xs" href="<%=basePath %>sim/operatorinfo/view/' + record.operatorID + '"><span class="glyphicon glyphicon-info-sign">详情</span></a>'

         function op_delete(id) {
            if(confirm("确认删除此运营商?")) {
				$.ajax({
					type:"POST",
					url:"<%=basePath %>sim/operatorinfo/delete/" + id,
					// dataType:"html",
					// data:$('#plan_form').serialize(),
					success:function(data){
						easy2go.toast('info', data);
						gridObj.refreshPage();
					}

				});
			}
         }
	</script>
</c:if>

<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>