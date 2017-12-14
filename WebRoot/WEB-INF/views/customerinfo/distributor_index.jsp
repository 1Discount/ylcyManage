<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String pathRoot = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort() + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title><c:if test="${IsIndexView}">全部渠道商</c:if> <c:if test="${IsTrashView}">全部已删除渠道商</c:if>-渠道商管理-流量运营中心</title>
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
	<div id="D_display" style="display: none">
		<div style="background: #666; width: 100%; height: 100%; position: fixed; z-index: 1; opacity: 0.2;"></div>
		<div style="border: 1px solid #999; width: 240px; background: #FFF; color: #000; height: 35px; padding: 0px 15px; line-height: 35px; text-align: center; opacity: 1; position: absolute; left: 50%; top: 50%; z-index: 888;">
			<img src="../../static/images/spinner.gif" style="float: left; margin-top: 7px; width: 20px; height: 20px;" />正在加载数据...
		</div>
	</div>
	<section id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
		<section id="main-content">
			<section class="wrapper">
				<%-- 这里添加页面主要内容  block content --%>
				<div class="col-md-12">
					<div class="panel">
						<div class="panel-body">
							<form class="form-inline" id="searchForm" role="form" method="get" action="#">
								<div class="form-group">
									<label class="inline-label">单位名称：</label>
									<input class="form-control" name="company" type="text" placeholder="单位名称">
								</div>
								<div class="form-group">
									<label class="inline-label">所属渠道：</label>
									<select name="parentsDisID"  class="form-control" id="parentsDisName">
										 <option value="000">ylcy</option>
										 <option value="">全部</option>
										<c:forEach items="${distributors}" var="item" varStatus="status">
		                                    <option value="${item.distributorID}">${item.company}</option>
	                                    </c:forEach>
                                    </select>
								</div>
								<div class="form-group">
									<label class="inline-label">联系人：</label>
									<input class="form-control" name=operatorName type="text" placeholder="姓名">
								</div>
								<div class="form-group">
									<label class="inline-label">联系电话：</label>
									<input class="form-control" name="operatorTel" type="text" placeholder="电话">
								</div>
								<div class="form-group">
									<label class="inline-label">联系邮箱：</label>
									<input class="form-control" name="operatorEmail" type="text" placeholder="email">
								</div>
								<div class="form-group">
									<button class="btn btn-primary" type="button" onclick="$('.tixing').text($('#parentsDisName option:selected').text());$('input').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.refreshPage();">搜索</button>
								</div>
							</form>
						</div>
					</div>
					<div class="panel">

						<div class="panel-heading">
							<c:if test="${IsIndexView}">
								
								<div class="btn-toolbar btn-header-right" style="margin-right: 30px;">
									<ul style="padding-left:0px;">
										<li class="dropdown btn btn-success btn-xs">
											<a data-toggle="dropdown" href="#"  style="background:none; border: none; font-size: 12px; color: #FFF">
												<span class="" >操作<i class="glyphicon glyphicon-chevron-down" style="font-size: 1px; padding-left:10px;"></i></span>
											</a>
											<ul class="dropdown-menu extended logout">
												<li>
													<a href="#" onclick="creatorDisOrder()">
														<i class="glyphicon glyphicon-plus"></i>新建订单
													</a>
													
													<a href="#"  onclick="updateannouncement();">
														<i class="glyphicon glyphicon-edit"></i>公告信息修改
													</a>
													
													<a href="#"  onclick="selectpassword();">
														<i class="glyphicon glyphicon-eye-open"></i>查看密码
													</a>
													
													<a href="#"  onclick="resetpassword();">
														<i class="glyphicon glyphicon-cog"></i>重置密码
													</a>
													
													<a href="#"  onclick="addchildDis();">
														<i class="glyphicon glyphicon-plus"></i>增加子渠道
													</a>
												</li>
											</ul>
										</li>
									</ul>
								</div>
								
								<div class="btn-toolbar btn-header-right" style="margin-right: 30px;">
									<a class="btn btn-success btn-xs" href="#" onclick="importsebei();">
										<span class="glyphicon glyphicon-plus"></span>
										导入设备
									</a>
								</div>
								<div class="btn-toolbar btn-header-right" style="margin-right: 30px;">
									<a class="btn btn-success btn-xs" href="#" onclick="fenfasebei();">
										<span class="glyphicon glyphicon-plus"></span>
										分发设备
									</a>
								</div>
								<div class="btn-toolbar btn-header-right" style="margin-right: 30px;">
									<a class="btn btn-success btn-xs" href="#" onclick="pirceConfig();">
										<span class="glyphicon glyphicon-plus"></span>
										录入价格
									</a>
								</div>
								
								<div class="btn-toolbar btn-header-right" style="margin-right: 30px;">
									<a class="btn btn-success btn-xs" href="#" onclick="importpirceConfig();">
										<span class="glyphicon glyphicon-plus"></span>
										导入价格
									</a>
								</div>
								
								
								
								
							</c:if>
							<h3 class="panel-title">
							<!-- class="btn btn-warning btn-xs" -->
								<c:if test="${IsIndexView}"><b><span class="tixing"  style="font-size: 14px;">ylcy</span></b>子渠道商</c:if>
								<c:if test="${IsTrashView}">全部已删除渠道商</c:if>
							</h3>
						</div>

						<div class="panel-body">
							<div class="table-responsive">
								<table id="searchTable" class="bsgrid">
									<tr>
										<th w_render="redio" width="2%;"></th>
										<th w_index="grade" w_hidden="true">ID</th>
										<th w_index="distributorID" width="5%;" w_sort="distributorID,unorder">ID</th>
										<th w_render="render_company" width="5%;">单位名称</th>
										<th w_index="roleName" width="10%;">角色</th>
										<th w_index="userName" width="10%;">用户名</th>
										<th w_index="serverCode" width="10%;">sim服务器编号</th>
										<th w_index="operatorName" width="5%;">联系人</th>
										<th w_index="operatorTel" width="10%;">电话</th>
										<th w_index="standbyOperator" width="10%;">备用电话</th>
										<th w_index="operatorEmail" width="10%;">EMail</th>
										<th w_index="type" width="10%;">类型</th>
										<th w_index="paymentMethod" width="10%;">结算方式</th>
										<th w_index="score" width="10%;">帐户</th>
										<th w_index="balance" width="10%;">帐户余额</th>
										<th w_index="creatorDate" width="10%;">创建时间</th>
										<th w_index="modifyDate" width="10%;">修改时间</th>
										<th w_index="password" w_hidden="true" width="10%;">密码</th>
										<th w_render="operate" width="10%;">操作</th>
									</tr>
								</table>
							</div>
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="well">
									<span id="msg" style="border: 0px solid pink; font-size: 14px; color: red;"></span>

								</div>
							</div>
						</div>



						<form id="dmanageForm" action="" method="get"  target="_blank" > 
                        	<input type="hidden" name="authority" value="true">   
                        	<input type="hidden" id="distributorID" name="distributorID" value="">                         
      				  </form> 
					</div>
				</div>
			</section>
		</section>
	</section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min1.js"></script>
	
	<c:if test="${IsIndexView}">
	<script type="text/javascript">
		$(function(){
			var msg= '${msg}';
			$("#msg").html(msg);
			var status = '${status}';
			if(msg=="" && status!='status'){
				easy2go.toast('info','导入成功');
				$("#D_display").css({'display':'none'});
			}
			
			var message = '${LogMessage}';
			if(message=="0"){
				easy2go.toast("info","导入成功");
			}else if(message=="-1"){
				easy2go.toast("error","导入失败");
			}
		});
		  var distributorID;
		  var operatorName;
		  var priceConfiguration;
		  var grade;
    	  var gridObj;
          $(function(){
        	  gridObj = $.fn.bsgrid.init('searchTable', {
                 url:'<%=basePath%>customer/distributor/datapage',
                 pageSizeSelect: true,
                 pageSize: 20,
                 otherParames:$('#searchForm').serializeArray(),
                 pageSizeForGrid:[10,20,30,50,100],
                 multiSort:false,
	 	         event: {
 	                selectRowEvent: function (record, rowIndex, trObj, options) {
 	                	//行选中事件
 	                	trObj.children('td').eq(0).children('input').eq(0).prop('checked',true);
 	                	// 获取渠道商ID
 	                	distributorID = gridObj.getRecordIndexValue(gridObj.getRowRecord(gridObj.getSelectedRow()), 'distributorID');
 	                	// 获取单位名称
 	                    operatorName =gridObj.getColumnValue(gridObj.getSelectedRowIndex(), 'company');
 	                	// 获取价格配置
 	                    priceConfiguration =gridObj.getColumnValue(gridObj.getSelectedRowIndex(), 'priceConfiguration');
 	                	// 获取渠道级别
 	                    grade =gridObj.getColumnValue(gridObj.getSelectedRowIndex(), 'grade');
 	                },
 	                unselectRowEvent: function (record, rowIndex, trObj, options) {
 	                	//行取消选中事件
 	                	trObj.children('td').eq(0).children('input').eq(0).removeAttr('checked');
 	                }
	 	          }
             });
          });
          
          
        
          
          function updateannouncement(record, rowIndex, colIndex, options){
        	  bootbox.dialog({
 	             title: "公告信息修改",
 	             message:'<div><textarea class="form-control"  id="gonggao" rows="5" style="font-size:16px; width:566px;"></textarea></div>',
 	             buttons: {
 	                 cancel: {
 	                     label: "取消",
 	                     className: "btn-default",
 	                     callback: function (){}
 	                 },
 	                 success: {
 	                     label: "确认",
 	                     className: "btn-success edit-button-ok",
 	                     callback: function () {
 	                    	 var gonggao  = $("#gonggao").val();
 	                    	 if(gonggao==''){
 	                    		 easy2go.toast('warn','请输入修改信息');
 	                    		 return false;
 	                    	 }
 	                    	 $.ajax({
	                   			type:"get",
	                   			url:"<%=basePath%>customer/distributor/updateAnnouncement?announcement="+gonggao,
	                   			dataType:"json",
	                   			success:function(data){
	                   				if(data==1){
	                   					easy2go.toast('info','修改成功');
	                   				}else{
	                   					esasy2go.toast('error','修改失败');
	                   				}
	                   			}
 		                    });
 	                     }
 	                 }
 	             }
 	         });
          }
          
         /**导入价格配置**/
         function importpirceConfig(){
        	 if(gridObj.getSelectedRowIndex()==-1){  easy2go.toast('warn', "请选择渠道商"); return ; }
             bootbox.dialog({
	             title: "导入设备",
	             message:'<form id="importFrom" class="form-horizontal" action="<%=basePath%>customer/distributor/importpriceConfig" method="post" enctype="multipart/form-data">'+
						 '<div class="form-group">'+
						 '<label class="col-sm-3 control-label">批量导入：</label>'+
						 '<div class="col-sm-9">'+
						 '<input type="file" name="file" id="file" data-popover-offset="0,8" required class="form-control">'+
						 '<input type="hidden" name="distributorID" id="" class="form-control" value="'+distributorID+'">'+
						 '<input type="hidden" name="distributorName" id="" class="form-control" value="'+operatorName+'"> <span style="color:red;">使用excel2003，内容例如：中国-460-30.0|香港-454-30.0</span>'+
						 '</div>'+
						 '</div>'+
						 '</form>',
	             buttons: {
	                 cancel: {
	                     label: "取消",
	                     className: "btn-default",
	                     callback: function(){}
	                 },
	                 success: {
	                     label: "确定",
	                     className: "btn-success edit-button-ok",
	                     callback: function () {
	                    	 $("#importFrom").submit();
	                     }
	                 }
	             }
	         });
          }
          
          /**录入价格配置**/
          function pirceConfig(){
        	  if(gridObj.getSelectedRowIndex()==-1){ easy2go.toast('warn', "请选择渠道商"); return ; }
        	  bootbox.dialog({
 	             title: "输入价格配置",
 	             message:'<div style="padding:15px;"><textarea rows="10" cols="70%" id="priceConfig" class="form-control">'+priceConfiguration+'</textarea>'+
 	            	 	 '<input type="hidden" name="distributorID" id="" class="form-control" value="'+distributorID+'">'+
                  		 '<input type="hidden" name="distributorName" id="" class="form-control" value="'+operatorName+'">',
 	             buttons: {
 	                 cancel: {
 	                     label: "取消",
 	                     className: "btn-default",
 	                     callback: function () {}
 	                 },
 	                 success: {
 	                     label: "确定",
 	                     className: "btn-success edit-button-ok",
 	                     callback: function () {
 	                    	 var priceConfig  = $("#priceConfig").val();
	                    	 if(priceConfig==''){
	                    		 easy2go.toast('warn','请输入价格配置');
	                    		 return false;
	                    	 }
 	                    	 $.ajax({
	                   			type:"POST",
	                   			url:"<%=basePath%>customer/distributor/writepriceConfig?priceConfiguration="+priceConfig+"&distributorID="+distributorID+"&distributorName="+operatorName,
	                   			dataType:"json",
	                   			success:function(data){
	                   				if(data=="0"){
	                   					easy2go.toast('info','价格配置添加成功');
	                   				}else{
	                   					easy2go.toast('warn','价格配置添加失败');
	                   				}
	                   			}
 		                    });
 	                     }
 	                 }
 	             }
 	         });
          }
          
          function fenfasebei(){
        	 if(gridObj.getSelectedRowIndex()==-1){ easy2go.toast('warn', "请选择渠道商"); return ; }
           	 if(grade!='1'){  easy2go.toast('warn', "你不能跳级分发设备");  return ; }
         	 bootbox.dialog({
	             title: "分发设备",
	             message:'<div><input class="form-control" id="SN" name="SN" type="text" placeholder="请输入机身码后六位, 多个机身码用 / 隔开" /></div>'+
	            	 	 '<input type="hidden" name="distributorID" id="" class="form-control" value="'+distributorID+'">'+
                 		 '<input type="hidden" name="distributorName" id="" class="form-control" value="'+operatorName+'">',
	             buttons: {
	                 cancel: {
	                     label: "取消",
	                     className: "btn-default",
	                     callback: function () { }
	                 },
	                 success: {
	                     label: "开始分发",
	                     className: "btn-success edit-button-ok",
	                     callback: function () {
	                    	 var SN = $("#SN").val();
	                    	 if(SN==''){
	                    		 easy2go.toast('warn','请输入设备机身码');
	                    		 return;
	                    	 }
	                    	 $.ajax({
	                   			type:"POST",
	                   			url:"<%=basePath%>customer/distributor/deviceDistributeBySN?SN="+SN+"&distributorID="+distributorID+"&distributorName="+operatorName,
	                   			dataType:"json",
	                   			success:function(data){
		                   			if(data.Code=="00"){
		                				easy2go.toast('info','分发设备成功');
		                			}else{
		                				$("#msg").html(data.msg);
		                			}
	                   			}
		                    });
	                     }
	                 }
	             }
	         });
          }
          
          
          //进入渠道商后台下单
          function creatorDisOrder(){
        	  if(gridObj.getSelectedRowIndex()==-1){  easy2go.toast('warn', "请选择渠道商");  return ;  }
        	  var url = "<%=pathRoot%>DManage/orders/ordersinfo/goone";
        	  $("#distributorID").val(distributorID);
        	  $("#dmanageForm").attr("action", url).submit();
          }
          
          function importsebei(record, rowIndex, colIndex, options){
        	 if(gridObj.getSelectedRowIndex()==-1){  easy2go.toast('warn', "请选择渠道商");  return ; }
             if(grade!='1'){ easy2go.toast('warn', "你不能跳级导入设备"); return ; }
         	 bootbox.dialog({
	             title: "导入设备",
	             message:'<form id="import_form" class="form-horizontal" action="<%=basePath%>customer/distributor/deviceDistribute" method="post" enctype="multipart/form-data">'+
			             '<div class="form-group">'+
			             '<label class="col-sm-3 control-label">批量导入：</label>'+
			             '<div class="col-sm-9">'+
			             '<input type="file" name="file" id="file" data-popover-offset="0,8" required class="form-control">'+
			             '<input type="hidden" name="distributorID" id="" class="form-control" value="'+distributorID+'">'+
			             '<input type="hidden" name="distributorName" id="" class="form-control" value="'+operatorName+'"> <span style="color:red;">使用excel2003,并确认机身码为后六位</span>'+
			             '</div>'+
			             '</div>'+
			             '</form>',
	             buttons: {
	                 cancel: {
	                     label: "取消",
	                     className: "btn-default",
	                     callback: function () {}
	                 },
	                 success: {
	                     label: "确定",
	                     className: "btn-success edit-button-ok",
	                     callback: function () {
	                    	 $("#D_display").css({'display':'block'});
	                    	 $("#import_form").submit();
	                     }
	                 }
	             }
	         });
          }
          
          function render_company(record, rowIndex, colIndex, options) {
              return '<a title="查看渠道商详情" href="<%=basePath%>customer/distributor/view/' + record.distributorID + '"><span>' + record.company + '</span></a>';
          }
          
          function redio(record, rowIndex, colIndex, options){
        	  return '<input type="radio" />';
          }
         function operate(record, rowIndex, colIndex, options) {
			if(record.password=='' || record.password==null){
				return '<div class="btn-toolbar">'
						+ '<a class="btn btn-primary btn-xs" href="#" onclick="createaccount(\''+record.distributorID+'\');"><span class="glyphicon glyphicon-briefcase">创建帐户</span></a>'
						+ '<a class="btn btn-primary btn-xs" href="<%=basePath%>customer/distributor/view/' + record.distributorID + '"><span class="glyphicon glyphicon-info-sign">详情</span></a>'
						+ '<a class="btn btn-primary btn-xs" href="<%=basePath%>customer/distributor/edit/' + record.distributorID + '"><span class="glyphicon glyphicon-edit">编辑</span></a>'
						+ '<a class="btn btn-danger btn-xs" href="#" onclick="op_delete(\'' + record.distributorID + '\')"><span class="glyphicon glyphicon-remove">删除</span></a>'
						+ '</div>';
			}else{
				return '<div class="btn-toolbar">'
						+ '<A class="btn btn-primary  btn-xs" href="<%=basePath %>customer/distributor/usertorole?distributorID='+record.distributorID+'&userName='+record.userName+'&operatorEmail='+record.operatorTel+'"><SPAN class="glyphicon glyphicon-certificate">分配角色</SPAN></A>'
						+ '<a class="btn btn-primary btn-xs" href="<%=basePath%>customer/distributor/view/' + record.distributorID + '"><span class="glyphicon glyphicon-info-sign">详情</span></a>'
						+ '<a class="btn btn-primary btn-xs" href="<%=basePath%>customer/distributor/edit/' + record.distributorID + '"><span class="glyphicon glyphicon-edit">编辑</span></a>'
						+ '<a class="btn btn-danger btn-xs" href="#" onclick="op_delete(\'' + record.distributorID + '\')"><span class="glyphicon glyphicon-remove">删除</span></a>'
						+ '</div>';
			}
         }
         function selectpassword(){
        	 if(gridObj.getSelectedRowIndex()==-1){ easy2go.toast('warn', "请选择渠道商"); return ; }
        	 bootbox.dialog({
		             title: "查看密码",
		             message:'<div style="text-align: center; "><form id="passwordForm" role="form" method="get" action="#"><INPUT class="form-control" name="password" id="password" type="password" placeholder="请输入登录密码"></div>',
		          	 buttons: {
	                 cancel: {
	                     label: "取消",
	                     className: "btn-default",
	                     callback: function (){}
	                 },
	                 success: {
	                     label: "确定",
	                     className: "btn-success edit-button-ok",
	                     callback: function () {
	                    	 var status=false;
	                    	 $("#passwordForm").validate_popover({
		                 			rules : {
		                 				'password' : {
		                 					required : true,
		                 					 remote: {
		                 	                    type: "post",
		                 	                    url: "<%=basePath%>admin/adminuserinfo/checkPassword",
		                 	                    data: {
		                 	                        username: function() {
		                 	                            return $("#password").val();
		                 	                        }
		                 	                    },
		                 	                    dataType: "html",
		                 	                    dataFilter: function(data, type) {
		                 	                        if (data == "true")
		                 	                            return true;
		                 	                        else
		                 	                            return false;
		                 	                    }
		                 	                }
		                 				},
		                 			},
		                 			messages : {
		                 				'password' : {
		                 					required:function(){
		                 						easy2go.toast('warn','请输入密码');
		                 					},
		                 					remote:function(){
		                 						easy2go.toast('warn','密码错误');
		                 					}
		                 				},
		                 			},
		                 			submitHandler: function(form){
		                 				$.ajax({
	                            			type:"POST",
	                            			url:"<%=basePath%>customer/distributor/selectpassword?distributorID="+distributorID,
	                            			dataType:"html",
	                            			success:function(data){
		                            			bootbox.dialog({
		                         	             	message:'<div style="text-align: center; font-size:16px;">'+data+'</div>',
		                         	       		});
	                            			}
		                             	});
		                 			},
	                    	 });
	                    	  $("#passwordForm").submit();
	                     }
	                 },
	    		 }   
	    	 });
         }
         function resetpassword(){
          	 if(gridObj.getSelectedRowIndex()==-1){ easy2go.toast('warn', "请选择渠道商");  return ; }
			 bootbox.dialog({
		             title: "重置密码",
		             message:'<div style="text-align: center;"><h4>确定将密码重置为 88888888 吗?</h4></div>',
		          	 buttons: {
	                 cancel: {
	                     label: "取消",
	                     className: "btn-default",
	                     callback: function (){}
	                 },
	                 success: {
	                     label: "确定",
	                     className: "btn-success edit-button-ok",
	                     callback: function () {
	                    	 $.ajax({
	                   			type:"POST",
	                   			url:"<%=basePath%>customer/distributor/resetpassword/?distributorID="+distributorID,
	                   			dataType:"html",
	                   			success:function(data){
		                   			if (data == 1) {
                 	                	easy2go.toast('info', "重置密码成功，欢迎使用");
                 	                	gridObj.refreshPage();
                 	                } else {
                 	                    easy2go.toast('error', "重置密码失败，请稍后再试");
                 	                }
	                   			}
	                    	 });
	                     }
	                 },
	    		 }   
        	 });
         }
         function createaccount(id){
        	  $.ajax({
      			type:"POST",
      			url:"<%=basePath%>customer/distributor/getdevicebydistributorID/?distributorID="+id,
      			dataType:"html",
      			success:function(data){
      				 if(data!=0){
      					 easy2go.toast('warn',"帐户已创建");
      				 }else{
      					 bootbox.dialog({
      			             title: "创建帐户",
      			             message:'<form id="sim-update-form" role="form" action="" method="post" autocomplete="off" class="form-horizontal">'+
      			             		 '<div class="form-group">'+
      							     '<label class="col-md-3 control-label">手机号：</label>'+
      							     '<div class="col-md-6">'+
      								 '<div class="inputgroup">'+
      								 '<input type="text" name="userName" id="userName" placeholder="请输入手机号"'+
      							     'class="form-control">'+
      								'</div>'+
      							    '</div>'+
      						        '</div>'+
      						        '</form>',
      			             buttons: {
      			                 cancel: {
      			                     label: "取消",
      			                     className: "btn-default",
      			                     callback: function (){}
      			                 },
      			                 success: {
      			                     label: "确定",
      			                     className: "btn-success edit-button-ok",
      			                     callback: function () {
      			                    		var status = true;
      			                    	 $("#sim-update-form").validate_popover({
      			                    			 rules:{
      			            			        	'userName':{
      			            			        		required:true,
      			            			        		 remote: {
      			            			                    type: "post",
      			            			                    url: "<%=basePath%>customer/distributor/checkUser",
      			            			                    data: {
      			            			                        userName: function() {
      			            			                            return $("#userName").val();
      			            			                        }
      			            			                    },
      			            			                    dataType: "html",
      			            			                    dataFilter: function(data, type) {
      			            			                        if (data == "true"){
      			            			                            return true;
      			            			                        }else{
      			            			                            return false;
      			            			                        }
      			            			                    }
      			            			                }
      			            			        	},
      			            			        	
      			            			        },
      			            			        messages:{
      			            			        	'userName':{required:"请输入用户名",remote:"此用户名已被使用"},
      			            			        },
      			            			        submitHandler: function(form){
      			            			        	$.ajax({
      			    	                     			type:"POST",
      			    	                     			url:"<%=basePath%>customer/distributor/createaccount?distributorID="+id+"&"+$("#sim-update-form").serialize(),
      			    	                     			dataType:"html",
      			    	                     			success:function(data){
      			    	                     				 result = jQuery.parseJSON(data);
      			    	                 	                if (result.code == 1){
      			    	                 	                	 easy2go.toast('info', result.msg);
      			    	                 	                	gridObj.refreshPage();
      			    	                 	                } else {
      			    	                 	                    easy2go.toast('error', result.msg);
      			    	                 	                }
      			    	                     			}
      			    	                     		});
      			            			        }
      			                    	 }); 
      			                    	 $("#sim-update-form").submit();
      			                    	 if($("#userName").val()==""){
      			                    		 status=false;
      			                    	 }
      			                    	 if(status==false){
      		                     			 status=true;
      		                     			 return false;
      		                     		 }
      			                     }
      			                 }
      			             }
      			         });
      				 }
      			}
      		}); 
         }
         var formISsubmit=false;
         function addchildDissubmit(){
        	 $("#addChildDis").validate_popover({
        	      rules: {
        	        'company': {
        	          required: true,
        	          maxlength: 50,
        	          remote: {
        	                type: "post",
        	                url: "<%=basePath%>customer/distributor/checkCompany",
        	                data: {
        	                    company: function() {
        	                        return $("#company").val();
        	                    }
        	                },
        	                dataType: "html",
        	                dataFilter: function(data, type) {
        	                    if (data == "true"){
        	                        return true;
        	                    }else{
        	                        return false;
        	                    }
        	                }
        	            }
        	        },
        	        'operatorName': {
        	          required: true,
        	          maxlength: 50
        	        },
        	        'operatorTel': {
        	          required: true,
        	          maxlength: 50,
        	          remote: {
        	              type: "post",
        	              url: "<%=basePath %>customer/distributor/checkPhone",
        	              data: {
        	            	  operatorTel: function() {
        	                      return $("#operatorTel").val();
        	                  }
        	              },
        	              dataType: "html",
        	              dataFilter: function(data, type) {
        	                  if (data == "true")
        	                      return true;
        	                  else
        	                      return false;
        	              }
        	          }
        	        },
        	        'operatorEmail': {
        	          required: false,
        	          email: true,
        	          maxlength: 50,
        	          remote: {
        	              type: "post",
        	              url: "<%=basePath %>customer/distributor/checkEmail",
        	              data: {
        	            	  operatorEmail: function() {
        	                      return $("#operatorEmail").val();
        	                  }
        	              },
        	              dataType: "html",
        	              dataFilter: function(data, type) {
        	                  if (data == "true")
        	                      return true;
        	                  else
        	                      return false;
        	              }
        	          }
        	        },
        	        'address': {
        	          required: false,
        	          maxlength: 100
        	        },
        	        'remark': {
        	          required: false,
        	          maxlength: 99
        	        }
        	        
        	      },
        	      'standbyOperator': {
        	          required: true,
        	          phone:true,
        	        },
        	      messages: {
        	         'company': {
        	            required: "请输入单位名称.",
        	            maxlength: "最多不超过50个字符.",
        	            remote:"此单位名称已使用"
        	         },
        			'operatorEmail': {
        	          remote: "邮箱已注册",
        	        },
        	        'operatorTel': {
        	            remote: "该手机号已注册",
        	          },
        	        'remark': {
        	          maxlength: "最多不超过100字"
        	        },
        	        'standbyOperator': {
        	            maxlength: "备用联系人不能为空",
        	            phone:"请输入正确的电话格式",
        	         }
        	      },
        	        submitHandler: function(form){
        	        	formISsubmit=true;
        		        $.ajax({
        		            type:"POST",
        		            url:"<%=basePath %>customer/distributor/saveChild",
        		            dataType:"html",
        		            data:$('#addChildDis').serialize(),
        		            success:function(data){
        		                result = jQuery.parseJSON(data);
        		                if (result.code == 0) { // 成功保存
        		                    easy2go.toast('info',"添加成功, 正在跳转到列表页面", false, 1000, {
					  					onClose: function() { 
					  						window.location.href="<%=basePath %>customer/distributor/index"; 
					  					}
					  				});
        		                } else {
        		                    easy2go.toast('error', result.msg);
        		                }
        		            }
        		        });
        	        }
        	    });
         }
         function addchildDis(){
        	 if(gridObj.getSelectedRowIndex()==-1){ easy2go.toast('warn', "请选择渠道商"); return ; }
    	
        	 bootbox.dialog({
		             title: "创建帐户",
		             message:'<form id="addChildDis" role="form" action="" method="post" autocomplete="off" class="form-horizontal">'+
				             '<input type="hidden" name="distributorID" value="'+distributorID+'"/>'+
				             '<input type="hidden" name="preCompany" value="'+operatorName+'"/>'+
				             '<input type="hidden" name="grade"value="'+grade+'"/>'+
				             '<div class="form-group">'+
				             '<label for="company" class="col-md-3 control-label">单位名称:</label>'+
				             '<div class="col-md-6">'+
				             '<input id="company" type="text" name="company" data-popover-offset="0,8"'+
				             'required maxlength="50" class="form-control">'+
				             '</div>'+
				             '<div class="col-sm-3">'+
				             '<p class="form-control-static"><span class="red">* 经营单位, 如公司名等</span>'+
				             '</p>'+
				             '</div>'+
				             '</div>'+
				             '<div class="form-group">'+
				             '<label for="operatorName" class="col-md-3 control-label">联系人姓名:</label>'+
				             '<div class="col-md-6">'+
				             '<input id="operatorName" type="text" name="operatorName" data-popover-offset="0,8"'+
				             'required maxlength="50" class="form-control">'+
				             '</div>'+
				             '<div class="col-sm-3">'+
				             '<p class="form-control-static"><span class="red">* </span>'+
				             '</p>'+
				             '</div>'+
				             '</div>'+
				             '<div class="form-group">'+
				             '<label for="operatorTel" class="col-md-3 control-label">联系电话:</label>'+
				             '<div class="col-md-6">'+
				             '<input id="operatorTel" type="text" name="operatorTel" data-popover-offset="0,8"'+
				             'required maxlength="50" class="form-control">'+
				             '</div>'+
				             '<div class="col-sm-3">'+
				             '<p class="form-control-static"><span class="red">* </span>'+
				             '</p>'+
				             '</div>'+
				             '</div>'+
				             '<div class="form-group">'+
				             '<label for="operatorTel" class="col-md-3 control-label">备用联系电话:</label>'+
				             '<div class="col-md-6">'+
				             '<input id="standbyOperator" type="text" name="standbyOperator" data-popover-offset="0,8"'+
				             'required maxlength="50" class="form-control">'+
				             '</div>'+
				             '<div class="col-sm-3">'+
				             '<p class="form-control-static"><span class="red">* </span>'+
				             '</p>'+
				             '</div>'+
				             '</div>'+
				             '<div class="form-group">'+
				             '<label for="operatorEmail" class="col-md-3 control-label">联系邮箱email:</label>'+
				             '<div class="col-md-6">'+
				             '<input id="operatorEmail" type="text" name="operatorEmail" data-popover-offset="0,8"'+
				             'maxlength="50" class="form-control">'+
				             '</div>'+
				             '<div class="col-sm-3">'+
				             '<p class="form-control-static"><span class="red"> </span>'+
				             '</p>'+
				             '</div>'+
				             '</div>'+
				             '<div class="form-group">'+
				             '<label for="address" class="col-md-3 control-label">地址:</label>'+
				             '<div class="col-md-6">'+
				             '<input id="address" type="text" name="address" data-popover-offset="0,8"'+
				             'maxlength="100" class="form-control">'+
				             '</div>'+
				             '<div class="col-sm-3">'+
				             '<p class="form-control-static"><span class="red"> </span>'+
				             '</p>'+
				             '</div>'+
				             '</div>'+
				             '<div class="form-group">'+
				             '<label for="type" class="col-md-3 control-label">渠道商类型:</label>'+
				             '<div class="col-md-6">'+
				             '<select id="type" name="type" required class="form-control">'+
				             '<option value="设备">设备</option>'+
				             '</select>'+
				             '</div>'+
				             '<div class="col-sm-3">'+
				             '<p class="form-control-static"><span class="red">*</span>'+
				             '</p>'+
				             '</div>'+
				             '</div>'+
				             '<div class="form-group">'+
				             '<label for="paymentMethod" class="col-md-3 control-label">结算方式:</label>'+
				             '<div class="col-md-6">'+
				             '<select id="paymentMethod" name="paymentMethod" required class="form-control">'+
				             '<option value="预付费">预付费</option>'+
				             '<option value="预付费">后付费</option>'+
				             '</select>'+
				             '</div>'+
				             '<div class="col-sm-3">'+
				             '<p class="form-control-static"><span class="red">*</span>'+
				             '</p>'+
				             '</div>'+
				             '</div>'+
				             '<div class="form-group">'+
				             '<label for="remark" class="col-md-3 control-label">备注:</label>'+
				             '<div class="col-md-6">'+
				             '<textarea id="remark" rows="3" name="remark" maxlength="99" data-popover-offset="0,8"'+
				             'class="form-control"></textarea>'+
				             '</div>'+
				             '<div class="col-sm-3"></div>'+
				             '</div>'+
				             '</form>',
		             buttons: {
		                 cancel: {
		                     label: "取消",
		                     className: "btn-default",
		                     callback: function (){}
		                 },
		                 success: {
		                     label: "确定",
		                     className: "btn-success edit-button-ok",
		                     callback: function () {
		                    	 addchildDissubmit();
		                    	 $("#addChildDis").submit();
		                    	 if(!formISsubmit){
		                    		 return false;
		                    	 }
		                     }
		                 }
		             }
        	 });
         }
         function op_delete(id) {
           if(confirm("确认删除此渠道商?")) {
               $.ajax({
                   type:"POST",
                   url:"<%=basePath%>customer/distributor/delete/" + id,
                   success:function(data){
                       easy2go.toast('info', data);
                       gridObj.refreshPage();
                   }
               });
           }
        }
	</script>
	</c:if>
	
	<c:if test="${IsTrashView}">
	<script type="text/javascript">
   	  var gridObj;
         $(function(){
            gridObj = $.fn.bsgrid.init('searchTable',{
                url:'<%=basePath%>customer/distributor/trashdatapage',
                pageSizeSelect: true,
                pageSize: 10,
                pageSizeForGrid:[10,20,30,50,100],
                multiSort:true
            });
        }); 
         
        function redio(record, rowIndex, colIndex, options){
       	 return '';
        }
        function render_company(record, rowIndex, colIndex, options) {
          	 return '<a href="<%=basePath%>customer/distributor/view/' + record.distributorID + '"><span>' + record.company + '</span></a>';
        }
        function operate(record, rowIndex, colIndex, options) {
		return '<div class="btn-toolbar">'
			+ '<a class="btn btn-primary btn-xs" href="<%=basePath%>customer/distributor/view/' + record.distributorID + '"><span class="glyphicon glyphicon-info-sign">详情</span></a>'
			+ '<a class="btn btn-primary btn-xs" href="<%=basePath%>customer/distributor/edit/' + record.distributorID + '"><span class="glyphicon glyphicon-edit">编辑</span></a>'
			+ '<a class="btn btn-danger btn-xs" href="#" onclick="op_restore(\'' + record.distributorID + '\')"><span class="glyphicon glyphicon-remove">恢复</span></a>'
			+ '</div>';
        }
        function render_company(record, rowIndex, colIndex, options) {
            return '<a title="查看渠道商详情" href="<%=basePath%>customer/distributor/view/' + record.distributorID + '"><span>' + record.company + '</span></a>';
        }
        function op_restore(id) {
            if(confirm("确认恢复此渠道商?")) {
				$.ajax({
					type:"POST",
					url:"<%=basePath%>customer/distributor/restore/" + id,
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