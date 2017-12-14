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
<title><c:if test="${IsIndexView}">全部国家</c:if> <c:if test="${IsTrashView}">全部已删除国家</c:if>-国家管理-流量运营中心</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet" href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
<style>
a {
	cursor: pointer;
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
				<c:if test="${IsIndexView}">
					<div class="col-md-3">
						<div class="panel">
							<div class="panel-heading">
								<h4 class="panel-title">添加国家信息</h4>
							</div>
							<div class="panel-body">
								<form id="edit_form" role="form" action="" method="post" autocomplete="off" class="form-horizontal">
									<input type="hidden" name="_csrf" value="pgByHDFK-RkGITAHnf3oznJXIqxTdn3Y2z8o">
									<div class="form-group">
										<label class="col-md-3 control-label">名称</label>
										<div class="col-md-9">
											<input type="text" name="countryName" data-popover-offset="0,8" required maxlength="32" class="form-control">
											<p class="help-block">
												<span class="red">*</span>
											</p>
										</div>
									</div>
									<div id="days" class="form-group">
										<label class="col-md-3 control-label">MCC</label>
										<div class="col-md-9">
											<input type="number" name="countryCode" data-popover-offset="0,8" class="form-control">
											<p class="help-block">
												<span class="red">* 国家编号 三位MCC</span>
											</p>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">大洲</label>
										<div class="col-md-9">
											<select name="continent" required class="form-control">
												<c:forEach items="${Continents}" var="item" varStatus="status">
													<option value="${item.label}">${item.label}</option>
												</c:forEach>
											</select>
											<p class="help-block">
												<span class="red">* </span>
											</p>
											<!--                                     <input type="text" name="continent" data-popover-offset="0,8" required maxlength="10" class="form-control"> -->
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">价格</label>
										<div class="col-md-9">
											<input type="number" name="flowPrice" data-popover-offset="0,8" required class="form-control">
											<p class="help-block">
												<span class="red">* 数据服务价格(元/天)</span>
											</p>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">本地卡代号说明</label>
										<div class="col-md-8">
											<input type="text" name="localCodeExplain" data-popover-offset="0,8" class="form-control">
											<p class="help-block">
												<span class="red"></span>
											</p>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">按流量价格</label>
										<div class="col-md-8">
											<input type="number" name="pressFlowPrice" data-popover-offset="0,8" class="form-control">
											<p class="help-block">
												<span class="red"></span>
											</p>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">限速策略</label>
										<div class="col-md-9">
											<input type="text" name="limitSpeedStr" value="0-2000|50-150|150-24|200-16|250-8" required class="form-control">
										</div>
										<div class="col-md-1">
											<p class="form-control-static">
												<span class="red">*</span>
											</p>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">排序序号</label>
										<div class="col-md-9">
											<input type="text" name="sortCode" value="0" required class="form-control">
										</div>
										<div class="col-md-1">
											<p class="form-control-static">
												<span class="red">*</span>
											</p>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-3 control-label">备注</label>
										<div class="col-md-9">
											<textarea rows="3" name="remark" data-popover-offset="0,8" maxlength="255" class="form-control"></textarea>
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-9 col-md-offset-3">
											<div class="btn-toolbar">
												<button type="submit" class="btn btn-primary">保存</button>
											</div>
										</div>
									</div>
									<span>
										<span style="color: red; font-size: 14px;">* </span>
										备注部分：添加国家中文英文拼音等,便于官网搜索!
										<br />
										(例如:&nbsp;&nbsp;china中国zhongguo)格式自定
									</span>
								</form>
				<DIV class="panel-body">
				  <h3 class="panel-title">批量导入国家</h3>
					<DIV class="panel">
						<DIV class="panel-heading">
							<SMALL>使用Excel文档批量导入</SMALL>
						</DIV>
						<DIV class="panel-body">
							<form id="import_form" class="form-horizontal" action="/flowplan/countryinfo/batchaddCountry" method="post" enctype="multipart/form-data">
								
								<div class="form-group">
									<label class="col-sm-3 control-label">批量导入：</label>
									<div class="col-sm-9">
										<input type="file" id="file" name="file" id="file" data-popover-offset="0,8" required class="form-control">
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-6 col-sm-offset-3">
										<div class="btn-toolbar">
											<button type="submit" class="btn btn-primary">提交</button>
											<button type="button" onclick="javascript:history.go(-1);" class="btn btn-default">返回</button>
										</div>
									</div>
								</div>
							</form>
							<div class="row">
								<div class="well">
									<span id="LogMessage"
										style="border: 0px solid pink; font-size: 14px; color: red;">${LogMessage}</span>
								</div>
							</div>

						</DIV>
					</DIV>
				</DIV>
							</div>
						</div>
					</div>
				</c:if>

				<c:if test="${IsIndexView}">
					<div class="col-md-9">
				</c:if>
				<c:if test="${IsTrashView}">
					<div class="col-md-12">
				</c:if>

				<div class="panel">
					<div class="panel-body">
						<form class="form-inline" id="searchForm" role="form" method="get" action="#">
							<div class="form-group">
								<label class="inline-label">国家名：</label>
								<input class="form-control" name="countryName" type="text" placeholder="">
							</div>
							<div class="form-group">
								<label class="inline-label">大&nbsp;洲：</label>
								<select name="continent" style="width: 179px;" class="form-control">
									<option value="">全部大洲</option>
									<c:forEach items="${Continents}" var="item" varStatus="status">
										<option value="${item.label}">${item.label}</option>
									</c:forEach>
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
						<c:if test="${IsIndexView}">
							<div class="btn-toolbar btn-header-right" style="margin-right: 30px;">
								<a class="btn btn-success btn-xs" onclick="batchupdate();" href="#">
									<span class="glyphicon glyphicon-plus"></span>
									批量修改
								</a>
							</div>
						</c:if>						
						
						<h3 class="panel-title">
							<c:if test="${IsIndexView}">国家列表</c:if>
							<c:if test="${IsTrashView}">已删除的国家列表</c:if>
						</h3>
					</div>
					<div class="panel-body">
						<div class="table-responsive">
							<table id="searchTable">
								<tr>
									<c:if test="${IsIndexView}"><th w_check="true"  width="3%;"></th></c:if>
									
									<th w_index="countryID" w_hidden="true" width="15%;">国家ID</th>
									<th w_render="render_sortCode" width="15%;">序号</th>
									<th w_index="countryName" width="15%;">国家名</th>
									<th w_index="countryCode" width="10%;">MCC编号</th>
									<th w_index="continent" width="10%;">大洲</th>
									<!-- w_sort="userID,desc" -->
									<th w_render="render_flowPrice" width="15%;">数据服务价格（￥）</th>
									<!-- <th w_index="flowCount" width="5%;">套餐总数</th> -->
									<!-- <th w_index="orderCount" width="5%;">订单总数</th> -->
									<th w_index="creatorUserName" width="5%;">创建者</th>
									<th w_index="creatorDate" width="20%;">创建时间</th>
									<th w_render="operate" width="30%;">操作</th>
								</tr>
							</table>
						</div>
						<c:if test="${IsIndexView}">
							<DIV class="panel-body">
							<button id="b" style="border: none;" onclick="exprotexcel()">
								<a href="javascript:void(0);" title="">
									<img src="<%=basePath%>/static/images/excel.jpg" width="30" height="30" />导出EXCEL请点我
								</a>
							</button>
						</DIV>
						</c:if>
						
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
	
	
    accounting.settings = { // 参数的默认设置:
        currency: {
            symbol : "￥",   //默认的货币符号 '$'
            format: "%s%v", // 输出控制: %s = 符号, %v = 值或数字 (can be object: see below)
            decimal : ".",  // 小数点分隔符
            thousand: ",",  // 千位分隔符
            precision : 2   // 小数位数
        },

        number: {
            precision : 0,  // 精度，默认的精度值为0
            thousand: ",",
            decimal : "."
        }
    };
    function batchsubmit(){
		alert("batchsubmit");
		<%-- $("#import_form").attr('action','<%=basePath%>flowplan/countryinfo/batchaddCountry');		
		$("#import_form").submit(); --%>
	}
	
    function batchupdate(){
    	
         	  var l=gridObj.getCheckedRowsRecords().length;
         	  if(l==0){
         		easy2go.toast('warn',"请选择要操作的记录!");
         		return;
         	  }
         	  var imsistr="";
         	  for(var i=0;i<l;i++){
         	    if(i==0){
         	    	imsistr+=gridObj.getCheckedRowsRecords()[i].countryID;
         	    }else{
         	    	imsistr+=","+gridObj.getCheckedRowsRecords()[i].countryID;
         	    }
         	  }
         	   bootbox.dialog({
                title: "批量编辑国家限速策略",
                message: '<div class="row">  ' +
                    '<div class="col-md-12"> ' +
                    '<form class="form-horizontal" id="sim-update-form" mothod="post"> ' +
                    '<div class="form-group"> ' +
                    '<label class="col-md-3 control-label" for="name">限速策略：</label> ' +
                    '<div class="col-md-8"><input class="form-control" name="limitSpeedStr" type="text" placeholder="">' +
                    '</div> ' +
                    '</form></div></div>',
                buttons: {
                    cancel: {
                        label: "取消",
                        className: "btn-default",
                        callback: function () {
                        }
                    },
                    success: {
                        label: "确认修改",
                        className: "btn-success edit-button-ok",
                        callback: function () {
                        	var se=$("#sim-update-form").serialize();
                        	if(se!=''){
                        		$.ajax({
                        			type:"POST",
                        			url:"<%=basePath %>flowplan/countryinfo/batchupdate?countryID="+imsistr,
                        			dataType:"html",
                        			data:$('#sim-update-form').serialize(),
                        			success:function(data){
                        				if(data=='00'){
                        					easy2go.toast('info','修改成功');
                        					gridObj.refreshPage();
                        				}else if(data=='-2'){
                        					alert("修改失败!");
                        				}else if(data=="-1"){
                        					alert("参数为空!");
                        				}
                        			}
                        			
                        			
                        		});
                        		
                        	}else{
                        		alert("请填写限速策略");
                        		return false;
                        	}
                        }
                    }
                }
            });
         	   
    }
    function render_sortCode(record, rowIndex, colIndex, options){
    	  return '<a onclick="updateNumber(\''+record.countryName+'\')">'+record.sortCode+'</a>';


    }
    
    function exprotexcel(){
    	window.location.href="<%=basePath%>flowplan/countryinfo/exportexcel?"+$('#searchForm').serialize();
    }
    function updateNumber(countryName){
    	 bootbox.dialog({
             title: "序号编辑",
             message: '	<FORM class="form-inline" id="edit" role="form" method="" action="#" style="text-align: center;"><DIV class="form-group"><lable class="inline-label"  style="display: inline-block;">请输入序号：</lable><input class="form-control" name="sortCode"  style="display: inline-block;"></form>',
			 buttons: {
                 cancel: {
                     label: "取消",
                     className: "btn-default",
                     callback: function () {
                     }
                 },
                 success: {
                     label: "确认修改",
                     className: "btn-success edit-button-ok",
                     callback: function () {
                    	 $.ajax({
                    		 type:"get",
                    		 url:"<%=basePath%>flowplan/countryinfo/updateCountryByName?countryName="+countryName,
                    		 data:$("#edit").serialize(),
    						 dataType:'html',
    						 success:function(data) {
    							if(data=="00"){
    								easy2go.toast('info','修改成功！！！');
    								history.go(0);
    							}else{
    								easy2go.toast('warn','修改失败！！！');
    							}
    						}
                    	 });
                     }
                 }
             }
         });
    }
    function render_flowPrice(record, rowIndex, colIndex, options) {
       return accounting.formatMoney(record.flowPrice) + "/天";
    }
    
    </script>
	<script type="text/javascript">$(function(){
        $("#edit_form").validate_popover({
            rules:{
                'countryName':{required:true,maxlength:32},
                'countryCode':{required:true, range:[100,999]},
                'flowPrice':{required:true,number:true},
                'pressFlowPrice':{number:true},
            },
            messages:{
                'countryName':{required:"必填字段",maxlength:"少于32个字符"},
                'countryCode':{required:"MCC为3位数字, 不足前面补零",range:"MCC为3位数字, 不足前面补零"},
                'flowPrice':{required:"必填字段",number:"需要为数字"},
                'pressFlowPrice':{number:"需要为数字"},
            },
            submitHandler: function(form){
            	if($('input[name="pressFlowPrice"]').val() == ''){
                    if(!confirm('“按流量价格”现在未提供，确定继续吗？')){
                          return false;
                    }
                }
		        $.ajax({
		            type:"POST",
		            url:"<%=basePath%>flowplan/countryinfo/save",
		            dataType:"html",
		            data:$('#edit_form').serialize(),
		            success:function(data){
		                result = jQuery.parseJSON(data);
		                if (result.code == 0) { // 成功保存
		                    easy2go.toast('info', result.msg);
		                    window.location.href="<%=basePath%>flowplan/countryinfo/index";
		                } else {
		                    easy2go.toast('error', result.msg);
		                }
		            }
		        });
            }
            });
    });</script>
	<c:if test="${IsIndexView}">
		<%-- 区分回收站记录和正常记录的操作 --%>
		<script type="text/javascript">
    	  var gridObj;
          $(function(){
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath%>flowplan/countryinfo/datapage',
                 pageSizeSelect: true,
                 pageSize: 30,
                 pageSizeForGrid:[10,20,30,50,100],
                 multiSort:true,
             });
         });

         function operate(record, rowIndex, colIndex, options) {
			return '<div class="btn-toolbar">'
			+ '<a class="btn btn-info btn-xs" href="<%=basePath%>flowplan/countryinfo/view/' + record.countryID + '"><span class="glyphicon glyphicon-info-sign">套餐或订单</span></a>'
			+ '<a class="btn btn-primary btn-xs" href="<%=basePath%>flowplan/countryinfo/edit/' + record.countryID + '"><span class="glyphicon glyphicon-edit">编辑</span></a>'
			+ '<a class="btn btn-danger btn-xs" href="#" onclick="op_delete(\'' + record.countryID + '\',\'' + record.countryName + '\')"><span class="glyphicon glyphicon-remove">删除</span></a>'
			+ '</div>';
         }
        // + '<a class="btn btn-primary btn-xs" href="<%=basePath%>flowplan/countryinfo/view/' + record.countryID + '"><span class="glyphicon glyphicon-info-sign">详情</span></a>'

         function op_delete(id, name) {
            if(confirm("确认删除此国家 " + name + "? 删除国家可能影响到该国所有包括正在使用的订单, 请仔细确认!")) {
                if(confirm("请再次确认删除此国家 " + name + "?")) {
				$.ajax({
					type:"POST",
					url:"<%=basePath%>flowplan/countryinfo/delete/" + id,
					// dataType:"html",
					// data:$('#plan_form').serialize(),
					success:function(data){
						easy2go.toast('info', data);
						gridObj.refreshPage();
					}

				});
				}
			}
         }
	</script>
	</c:if>
	<c:if test="${IsTrashView}">
		<%-- 区分回收站记录和正常记录的操作 --%>
		<script type="text/javascript">
    	  var gridObj;
          $(function(){
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath%>flowplan/countryinfo/trashdatapage',
                 pageSizeSelect: true,
                 pageSize: 10,
                 pageSizeForGrid:[10,20,30,50,100],
                 multiSort:true
             });
         });

         function operate(record, rowIndex, colIndex, options) {
			return '<div class="btn-toolbar">'
			+ '<a class="btn btn-info btn-xs" href="<%=basePath%>flowplan/countryinfo/view/' + record.countryID + '"><span class="glyphicon glyphicon-info-sign">套餐或订单</span></a>'
			+ '<a class="btn btn-primary btn-xs" href="<%=basePath%>flowplan/countryinfo/edit/' + record.countryID + '"><span class="glyphicon glyphicon-edit">编辑</span></a>'
			+ '<a class="btn btn-danger btn-xs" href="#" onclick="op_restore(\'' + record.countryID + '\',\'' + record.countryName + '\')"><span class="glyphicon glyphicon-remove">恢复</span></a>'
			+ '</div>';
         }
         function op_restore(id, name) {
            if(confirm("确认恢复国家 " + name + "?")) {
				$.ajax({
					type:"POST",
					url:"<%=basePath%>flowplan/countryinfo/restore/" + id,
					success : function(data) {
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