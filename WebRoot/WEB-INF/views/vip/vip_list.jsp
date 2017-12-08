<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
<title><c:if test="${IsIndexView}">全部SIM卡-本地SIM卡管理-EASY2GO ADMIN</c:if>
	<c:if test="${IsTrashView}">本地SIM卡管理-全部已删除SIM卡-EASY2GO ADMIN</c:if></title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet"
	href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet"
	href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
<style type="text/css">
/*  #searchTable tr{height:40px;} */
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
				<div class="col-md-12">
					<div class="panel">
						<div class="panel-body">
							<form class="form-inline" id="searchForm" role="form"
								method="get" action="#">
								<input type="hidden" id="pagenum" value="1" /> <input
									type="hidden" id="pagesize" value="15" />
								<div class="form-group">
									<label class="inline-label">卡号：</label> <input type="text"
										name="cardID" style="width: 173px;" placeholder="卡号"
										class="form-control" id="IMSI">
								</div>
								<div class="form-group">
									<label class="inline-label">卡状态：</label> <select
										name="cardStatus" class="form-control">
										<option value="">全部</option>
										<option value="可用">可用</option>
										<option value="禁用">禁用</option>
									</select>
								</div>

								<div class="form-group">
									<label class="inline-label">是否兑换：</label> <select
										name="isExchange" class="form-control">
										<option value="">全部</option>
										<option value="是">是</option>
										<option value="否">否</option>
									</select>




								</div>
								<div class="form-group">
									<button class="btn btn-primary" type="button"
										onclick="$('input').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.page(1);">搜索</button>
									<button type="reset" class="btn btn-default">重置</button>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="col-md-12">
					<div class="panel">
						<div class="panel-heading">
							<h4>全部VIP卡</h4>
							<div class="btn-toolbar btn-header-right">
								<a class="btn btn-success btn-xs"
									href="<%=basePath%>VIPCardInfo/add"><span
									class="glyphicon glyphicon-plus"></span> 添加VIP卡</a>
							</div>
							<div style="margin-right: 30px;"
								class="btn-toolbar btn-header-right">
								<a class="btn btn-success btn-xs" onclick="updateStatus()"
									href="javascript:;"><span class="glyphicon glyphicon-plus"></span>批量操作</a>
							</div>
							<h3 class="panel-title">
								<c:if test="${IsIndexView}">全部本地SIM卡</c:if>
								<c:if test="${IsTrashView}">全部已删除本地SIM卡</c:if>
							</h3>
						</div>
						<div class="panel-body">
							<div class="table-responsive">
								<table id="searchTable">
									<tr>
										<th w_check="true" width="3%;"></th>
										<th w_render="cardID" width="5%;" w_sort="cardID,unorder">VIP卡号</th>
										<th w_index="preferentialType" width="5%;">优惠方式</th>
										<th w_index="batchNumber" width="5%;"
											w_sort="batchNumber,unorder">批次号</th>
										<th w_index="startTime" width="5%;">有效期开始时间</th> 
										<th w_index="endTime" width="5%;">有效期结束时间</th>
										<th w_index="isExchange" width="5%;">是否兑换</th>
										<th w_index="exchangeTime" width="5%;">兑换时间</th>
										<th w_index="exchangeIphone" width="5%;">对兑手机号</th>
										<th w_index="cardStatus" width="5%;">卡状态</th>
										<th w_index="isMakeCard" width="5%;">是否已制卡</th>
										<th w_index="creatorUserName" width="5%;">创建人</th>
										<th w_index="creatorDate" width="5%;">创建时间</th>
										<th w_render="operate" width="5%;">操作</th> 
									</tr>
								</table>

							</div>

						</div>
						<div class="panel-body">
							<div class="paomadeng">
								<span style="font-size: 14px; font-weight: bolder;"><a
									href="#" onclick="seturl();"><img
										src="<%=basePath%>static/images/excel.jpg"
										style="float: left; margin-top: -5px;" width="30" height="30"
										title="" /> 导出EXCEL请点我</a></span>
							</div>

						</div>

					</div>

					<div id="special-note">
						<!-- 						此处正常,在grid中不正常 <a href="javascript:void(0);" class="ahover-tips notes-tips-' + rowIndex + '-' + colIndex + '" tooltips="' + escape(record.notes) + '">
 -->
						<p>1.EXECL导出只要是满足搜索条件的数据都会被导出，并不是只导出当前页，如果搜索条件全部为空，则导出全部数据</p>

					</div>
				</div>
			</section>
		</section>
	</section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min1.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/accounting/accounting.min.js"></script>
	<script src="<%=basePath%>static/js/byteunit.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script src="<%=basePath%>static/js/tooltips.js"></script>
	<script type="text/javascript">
    	  var gridObj;
          $(function(){
        	  var pagesize=parseInt($("#pagesize").val());
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath%>VIPCardInfo/getpage',
                 autoLoad: false,
                 pageSizeSelect: true,
                 pageSize: pagesize,
                 pageSizeForGrid:[15,30,50,100],
                 multiSort:true,
                 rowHoverColor:true,
                 otherParames:$('#searchForm').serializeArray(),
                 additionalAfterRenderGrid: function(parseSuccess, gridData, options) {
                	 if(parseSuccess){
                		 $("#pagenum").val(options.curPage);
                		 var a = $("#searchTable_pt_pageSize").val();
                		 $("#pagesize").val(a);
                	 }
                	 
                	// 只能等待表格数据渲染完毕再初始化提示框
                     $(".ahover-tips").each(function(){$(this).hoverTipsEx({isEscape:true,isSmallWidth:true});});
                 },
                
                  
             });
             if($("#pagenum").val()!=""){
          	   gridObj.page($("#pagenum").val());
             }else{
          	   gridObj.page(1);
             }
             
             
             
         });
         function seturl(){
        	//alert( $('#searchForm').serialize());
        	//alert(this.attr('href'));
        	 window.location.href="<%=basePath%>VIPCardInfo/excelExport?"+$('#searchForm').serialize();
        	 
         }
         function cardID(record, rowIndex, colIndex, options){
        	   return '<A style="color:#1FB5AD" href="<%=basePath%>VIPCardInfo/skipVIPview?cardID='+record.cardID+'">'+record.cardID+'</A>';
         }
         function operate(record, rowIndex, colIndex, options) {
			return '<div class="btn-toolbar">'
					+ '<a class="btn btn-primary btn-xs" href="<%=basePath%>VIPCardInfo/edit?cardID='+record.cardID+'"><span class="glyphicon glyphicon-edit">编辑</span></a>'
					+ '<a class="btn btn-danger btn-xs" onclick="op_delete(' + record.cardID + ')"><span class="glyphicon glyphicon-remove">删除</span></a>'
					+ '</div>'; 
         }   
			//批量操作
	         function updateStatus(){
	      	  var l=gridObj.getCheckedRowsRecords().length;
	      	  if(l==0){
	      		easy2go.toast('warn',"请选择要操作的记录!");
	      		return;
	      	  }
	      	  var imsistr="";
	      	  for(var i=0;i<l;i++){
	      	    if(i==0){
	      	    	imsistr+=gridObj.getCheckedRowsRecords()[i].cardID;
	      	    }else{
	      	    	imsistr+=","+gridObj.getCheckedRowsRecords()[i].cardID;
	      	    }
	      	  }
	      	   bootbox.dialog({
	             title: "批量编辑SIM卡",
	             message:'<div class="panel-body">'+
						'<form id="sim-update-form" role="form" action="" method="post" autocomplete="off" class="form-horizontal"><div id="countries" class="form-group">'+
						'<label class="col-sm-3 control-label" style="margin-top:7px;">优惠方式：</label>'+
						'<div class="col-sm-6" style="width:70%">'+
							'<div class="checkbox" >'+
									'<label class="checkbox-items" style="padding:6px 12px;">'+
									 	'<input type="checkbox" name="preferentialType" value="身份" id="shenfencheckbox">身份'+
									'</label>'+
									'<label class="checkbox-items" style="padding:6px 12px;">'+
									 	'<input type="checkbox" name="preferentialType" value="设备" id="shebeicheckbox">设备'+
									'</label><br />'+
									'<label class="checkbox-items" id="chongzhi" onclick="chongzhi(this)" style="padding:6px 12px;">'+
								 	'<input type="checkbox" id="liuliang" name="preferentialType" value="流量费用" id="">流量费用'+
								 	'<input  id="liuliangtext" name="liuliang" type="text"  class="form-control" style="display:none; width:auto;" placeholder="请输入充值金额">'+
								'</label><br />'+
								'<label class="checkbox-items" id="chongzhi" onclick="chongzhi(this)" style="padding:6px 12px;">'+
								 	'<input type="checkbox" id="huafei" name="preferentialType" value="话费">话　　费'+
								 	'<input  id="huafeitext" name="huafei" type="text" class="form-control" style="display:none; width:auto;" placeholder="请输入充值金额">'+
								'</label><br />'+
								'<label class="checkbox-items" id="chongzhi" onclick="chongzhi(this)" style="padding:6px 12px;">'+
								 	'<input type="checkbox" id="jiben" name="preferentialType" value="基本帐户" id="">基本帐户'+
								 	'<input  id="jibentext" name="jiben" type="text" class="form-control" style="display:none; width:auto;" placeholder="请输入充值金额">'+
								'</label>'+
									'<input  id="jine" name="jine" type="text" class="form-control" style="display:none; width:200px;;" placeholder="请输入充值金额">'+
							'</div>'+
						'</div>'+
						'<div class="col-sm-3">'+
							'<p class="form-control-static">'+
								'<span class="red"></span>'+
							'</p>'+
						'</div>'+
					'</div>'+
					'<div class="form-group">'+
						'<label class="col-md-3 control-label">有效期开始时间：</label>'+
						'<div class="col-md-6">'+
							'<div class="inputgroup">'+
								'<input type="text" name="startTime" id="beginDate" data-date-format="YYYY-MM-DD HH:ss:mm"'+
									'data-popover-offset="0,8"'+
									'class="form_datetime form-control">'+
							'</div>'+
						'</div>'+
						'<div class="col-md-3">'+
							'<p class="form-control-static">'+
								'<span class="red"></span>'+
							'</p>'+
						'</div>'+
					'</div>'+
					'<div class="form-group">'+
						'<label class="col-md-3 control-label">有效期结束时间：</label>'+
						'<div class="col-md-6">'+
							'<div class="inputgroup">'+
								'<input type="text" name="endTime" id="endDate" data-date-format="YYYY-MM-DD HH:ss:mm"'+
									'data-popover-offset="0,8"'+
									'class="form_datetime form-control">'+
							'</div>'+
						'</div>'+
						'<div class="col-md-3">'+
							'<p class="form-control-static">'+
								'<span class="red"></span>'+
							'</p>'+
						'</div>'+
					'</div>'+
					'<div class="form-group">'+
					'<label class="col-sm-3 control-label">是否已制卡：</label>'+
					'<div class="col-sm-6">'+
							'<label for="ifboundsn0" class="radio-inline"> <input type="radio" name="isMakeCard" id="ifboundsn0" value="否" checked>否'+
							'</label>'+
							'<label for="ifboundsn1" class="radio-inline"> <input type="radio" name="isMakeCard" id="ifboundsn1" value="是">是'+
							'</label>'+
					'</div>'+
					'<div class="col-sm-3">'+
						'<p class="form-control-static">'+
							'<span class="red"></span>'+
						'</p>'+
					'</div>'+
				'</div>'+
				'<div class="form-group">'+
					'<label class="col-sm-3 control-label">卡状态：</label>'+
					'<div class="col-sm-6">'+
							'<label for="ifboundsn11" class="radio-inline">'+
								'<input type="radio" name="cardStatus" id="ifboundsn11" value="禁用" checked>禁用'+
							'</label>'+
							'<label for="ifboundsn00" class="radio-inline"> '+
								'<input type="radio" name="cardStatus" id="ifboundsn00" value="可用">可用'+
							'</label>'+
					'</div>'+
					'<div class="col-sm-3">'+
						'<p class="form-control-static">'+
							'<span class="red"></span>'+
						'</p>'+
					'</div>'+
				'</div>'+
					 '<div class="form-group">'+
			          '<label for="sim_desc" class="col-sm-3 control-label">备注：</label>'+
			         ' <div class="col-sm-6">'+
			           ' <textarea id="sim_desc" rows="3" name="remark" maxlength="499" data-popover-offset="0,8"'+
			            'class="form-control">${Model.remark}</textarea>'+
			          '</div>'+
			         ' <div class="col-md-3">'+
		                    '<p class="form-control-static">'+
		                       ' <span class="red"><span class="text-info"></span></span>'+
		                   ' </p>'+
		               ' </div>'+
			       ' </div>'+
					'</div>'+
				'</form>'+
			'</div>',
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
	                    	//alert(111);
	                     	var se=$("#sim-update-form").serialize();
	                     	//alert(se);
	                     	if(se!=''){
	                     	var status = true;
	                     		 $("#sim-update-form").validate_popover({
	               				  rules:{
		               		        	'liuliang':{ required:true,number:true},
		               		        	'huafei':{ required:true,number:true},
		               		        	'jiben':{ required:true,number:true},
	               			        	'preferentialType':{required:true},
	               			        	'cardStatus':{required:true},
	               			        	'startTime':{required:true},
	               			        	'endTime':{required:true},
	               			        	'isMakeCard':{required:true},
	               			        	'jine':{required:true},
	               			        },
	               			        messages:{
		               			     	'liuliang':{required:"请输入金额"},
		               		       		'huafei':{required:"请输入金额"},
		               		       	 	'jiben':{required:"请输入金额"},
	               			        	'preferentialType':{required:"请选择优惠方式"},
	               			        	'cardStatus':{required:"请选择卡状态"},
	               			        	'startTime':{required:"开始时间不能为空"},
	               			        	'endTime':{required:"结束时间不能为空"},
	               			        	'isMakeCard':{required:"请选择是否制卡"},
	               			        },
	               			        submitHandler: function(form){
	               			        	$.ajax({
	               	             			type:"POST",
	               	             			url:"<%=basePath%>VIPCardInfo/batchedit?cardID="+imsistr+"&"+$("#sim-update-form").serialize(),
	               	             			dataType:"html",
	               	             			success:function(data){
	               	             				 result = jQuery.parseJSON(data);
	               	         	                if (result.code == 1) { // 成功保存
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
	                     		  $(".popover-content").each(function(){
	                     		   if($(this).text()!=""){
	                     			   status=false;
	                     		   }
	                     		 });
	                     		 if(status==false){
	                     			 status=true;
	                     			 return false;
	                     		 }
	                     	   <%-- $.ajax({
	                     			type:"POST",
	                     			url:"<%=basePath%>VIPCardInfo/batchedit?cardID="+imsistr+"&"+se,
	                     			dataType:"html",
	                     			success:function(data){
	                     				 result = jQuery.parseJSON(data);
	                 	                if (result.code == 1) { // 成功保存
	                 	                	 easy2go.toast('warn', result.msg);
	                 	                	gridObj.refreshPage();
	                 	                } else {
	                 	                    easy2go.toast('error', result.msg);
	                 	                }
	                     			}
	                     		}); --%>
	                     	}else{
	                     		alert("请选择卡状态!");
	                     		return false;
	                     	}
	                     }
	                 }
	             }
	         });
	      	 $("#beginDate").datetimepicker({
					pickDate : true, //en/disables the date picker
					pickTime : true, //en/disables the time picker
					showToday : true, //shows the today indicator
					language : 'zh-CN', //sets language locale
					defaultDate : moment(), //sets a default date, accepts js dates, strings and moment objects
				});
	      	 $("#endDate").datetimepicker({
					pickDate : true, //en/disables the date picker
					pickTime : true, //en/disables the time picker
					showToday : true, //shows the today indicator
					language : 'zh-CN', //sets language locale
					defaultDate : moment(), //sets a default date, accepts js dates, strings and moment objects
				});
	         }
			 function op_delete(id) {
	            if(confirm("确认删除SIM卡?")) {
	                $.ajax({
	                    type:"POST",
	                    url:"<%=basePath%>VIPCardInfo/del?cardID=" + id,
					success : function(data) {
						easy2go.toast('info', data);
						gridObj.refreshPage();
					}

				});
			}
		}
			   function chongzhi(check){
			    	if($(check).children().eq(0).is(':checked')){
			    		$(check).children().eq(1).css({'display':'inline-block'});
			    	}else{
			    		$(check).children().eq(1).css({'display':'none'});
			    	}
			    }
	<%-- 	$(function(){
			alert(123);
			 $("#sim-update-form").validate_popover({
				  rules:{
			        	'preferentialType':{required:true},
			        	'cardStatus':{required:true},
			        	'startTime':{required:true},
			        	'endTime':{required:true},
			        	'isMakeCard':{required:true},
			        },
			        messages:{
			        	'preferentialType':{required:"请选择优惠方式"},
			        	'cardStatus':{required:"请选择卡状态"},
			        	'startTime':{required:"开始时间不能为空"},
			        	'endTime':{required:"结束时间不能为空"},
			        	'isMakeCard':{required:"请选择是否制卡"},
			        },
			        submitHandler: function(form){
			        	$.ajax({
	             			type:"POST",
	             			url:"<%=basePath%>VIPCardInfo/batchedit?cardID="+imsistr+"&"+se,
	             			dataType:"html",
	             			success:function(data){
	             				 result = jQuery.parseJSON(data);
	         	                if (result.code == 1) { // 成功保存
	         	                	 easy2go.toast('warn', result.msg);
	         	                	gridObj.refreshPage();
	         	                } else {
	         	                    easy2go.toast('error', result.msg);
	         	                }
	             			}
	             		});
			        }
			    });
		}) --%>
	</script>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>