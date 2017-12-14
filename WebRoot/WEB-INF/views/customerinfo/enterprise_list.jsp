<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
<title>设备短信查看-客服查询中心-流量运营中心</title>
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
		<section id="main-content">
			<DIV class="col-md-12">
				<DIV class="panel">
					<DIV class="panel-body">
						<section class="wrapper">
							<DIV class="col-md-12">
								<DIV class="panel">
									<DIV class="panel-body">
										<FORM class="form-inline" id="searchForm" role="form" method="get" action="#">
											<DIV class="form-group">
												<LABEL class="inline-label">名称：</LABEL>
												<INPUT class="form-control" id="enterpriseName" name="enterpriseName" type="text" placeholder="企业名称">
											</DIV>

											<DIV class="form-group">
												<LABEL class="inline-label">电话：</LABEL>
												<INPUT class="form-control" id="enterprisePhone" name="enterprisePhone" type="text" placeholder="联系电话">
											</DIV>

											<DIV class="form-group">
												<LABEL class="inline-label">时间段：</LABEL>
												<INPUT id="order_creatorDatebegin" class="form-control form_datetime" name="begindateForQuery" type="text" data-date-format="YYYY-MM-DD HH:mm:ss">
											</DIV>

											<DIV class="form-group">
												<LABEL class="inline-label">到</LABEL>
												<INPUT id="order_creatorDateend" class="form-control form_datetime" name="enddate" type="text" data-date-format="YYYY-MM-DD HH:mm:ss">
											</DIV>

											<DIV class="form-group">
												<BUTTON class="btn btn-primary" type="button" onclick="gridObj.options.otherParames =$('#searchForm').serializeArray();gridObj.page(1);">搜索</BUTTON>
												<BUTTON class="btn btn-primary" onclick="re();" type="button">刷新</BUTTON>
											</DIV>
										</FORM>
									</DIV>
								</DIV>
								<DIV class="panel">
									<DIV class="panel-heading">
										<div class="btn-toolbar btn-header-right" style="margin-right: 30px;">
											<a class="btn btn-success btn-xs" onclick="fenfasebei();" href="#">
												<span class="glyphicon glyphicon-plus"></span>
												分发设备
											</a>
										</div>

										<div class="btn-toolbar btn-header-right" style="margin-right: 30px;">
											<a class="btn btn-success btn-xs" href="<%=basePath%>enterprise/add">
												<span class="glyphicon glyphicon-plus"></span>
												添加用户
											</a>
										</div>

										<H3 class="panel-title">所有企业用户</H3>

									</DIV>

									<DIV class="panel-body">
									<DIV class="table-responsive">
										<table id="searchTable">
											<tr>
												<th w_render="redio" width="2%;"></th>
												<th w_index="enterpriseID" width="10%;" w_hidden="true">ID</th>
												<th w_index="enterpriseName" width="10%;">名称</th>
												<th w_index="enterprisePhone" width="10%;">电话</th>
												<th w_index="enterpriseAddress" width="10%;">地址</th>
												<th w_index="score" width="10%;">帐户</th>
												<th w_index="balance" width="10%;">帐户余额</th>
												<th w_index="priceConfiguration" width="10%;" w_length="30">价格配置</th>
												<th w_index="creatorDate" width="10%;">创建时间</th>
												<th w_index="modifyUserName" width="10%;">修改人</th>
												<th w_index="remark" width="10%;">备注</th>
												<th w_render="operate" width="10%;">操作</th>
											</tr>
										</table>
										</DIV>
									</DIV>
								</DIV>
								<div class="panel-body">
									<div class="row">
										<div class="well">
											<span style="border: 0px solid pink; font-size: 14px; color: red;">${Msg }</span>
										</div>
									</div>
								</div>
							</DIV>
						</section>
					</DIV>
				</DIV>
			</DIV>
		</section>
	</section>



	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script src="<%=basePath%>static/js/accounting/accounting.min.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min1.js"></script>



	<script type="text/javascript">
	
		 bootbox.setDefaults("locale","zh_CN");
		 
		 var gridObj;
		  
	     $(function(){
	    	 
	        $("#order_creatorDatebegin").datetimepicker({
	    		pickDate: true,                 //en/disables the date picker
	    		pickTime: true,                 //en/disables the time picker
	    		showToday: true,                 //shows the today indicator
	    		language:'zh-CN',                  //sets language locale
	    		defaultDate: moment().add(-3, 'months'),                 //sets a default date, accepts js dates, strings and moment objects
	    	});
        
        
	        $("#order_creatorDateend").datetimepicker({
	     		pickDate: true,                 //en/disables the date picker
	     		pickTime: true,                 //en/disables the time picker
	     		showToday: true,                 //shows the today indicator
	     		language:'zh-CN',                  //sets language locale
	     		defaultDate: moment().add(0, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
	     	});
        
        	gridObj = $.fn.bsgrid.init('searchTable',{
          	    url:'<%=basePath%>enterprise/getpage',
				pageSizeSelect : true,
				pageSize : 15,
				pageSizeForGrid : [ 5, 30, 50, 100 ],
				autoLoad : true,
				otherParames : $('#searchForm').serializeArray(),
				event: {
 	                selectRowEvent: function (record, rowIndex, trObj, options) {
 	                	//这里是行选中事件
 	                	trObj.children('td').eq(0).children('input').eq(0).prop('checked',true);
 	                },
 	                unselectRowEvent: function (record, rowIndex, trObj, options) {
 	                	//这里是行取消选中事件
 	                	trObj.children('td').eq(0).children('input').eq(0).removeAttr('checked');
 	                }
	 	         },
				additionalAfterRenderGrid : function(parseSuccess, gridData,
						options) {
					if (parseSuccess) {

					}
				}
			});
		});
     
	    //自定义列
	     function operate(record, rowIndex, colIndex, options) {
             return '<A class="btn btn-primary btn-xs" href="<%=basePath%>enterprise/toenterpriseorderview?enterpriseID='+record.enterpriseID+'"><SPAN class="glyphicon glyphicon-info-sign">数据详情</SPAN></A>&nbsp;&nbsp;'
            	   +'<A class="btn btn-primary btn-xs" href="<%=basePath%>enterprise/edit?enterpriseID='+record.enterpriseID+'"><SPAN class="glyphicon glyphicon-edit">编辑</SPAN></A>&nbsp;&nbsp;'
            	   +'<A class="btn btn-danger btn-xs"  onclick="deletebyid('+rowIndex+')"><SPAN class="glyphicon glyphicon-remove">删除</SPAN></A>';
     
         }
	    
	     function redio(record, rowIndex, colIndex, options){
       	 	 return '<input type="radio" />';
         }
         
	     function priceConfiguration(record, rowIndex, colIndex, options){
	    	 return "<span>"+record.priceConfiguration+"</span>";
	     }
	    
	   	 function fenfasebei(record, rowIndex, colIndex, options){
	    	 if(gridObj.getSelectedRowIndex()==-1){
      			 easy2go.toast('warn', "请选择企业用户");
      			 return ;
      	  	 }
	    	 var enterpriseID = gridObj.getRecordIndexValue(gridObj.getRowRecord(gridObj.getSelectedRow()), 'enterpriseID');
	    	 var enterpriseName = gridObj.getRecordIndexValue(gridObj.getRowRecord(gridObj.getSelectedRow()), 'enterpriseName');
	    	 window.location.href="<%=basePath%>enterprise/tofenfasebei?enterpriseID="+enterpriseID+"&enterpriseName="+enterpriseName;
	   	 }
	    
	     //根据ID删除 
         function deletebyid(index){
        	 var record= gridObj.getRecord(index);
        	 bootbox.confirm("确定删除吗?", function(result) {
        		 if(result){
        			 $.ajax({
                		 type:"POST",
                		 url:"<%=basePath%>enterprise/delenterprise?enterpriseID=" + record.enterpriseID,
					dataType : 'html',
					success : function(data) {
						if (data == '00') {
							easy2go.toast('info', '删除成功！！！');
							gridObj.refreshPage();
						}
						else if (data == '01') {
							easy2go.toast('error', '删除失败');
						}
					}
					});
				}
			});
		}
		//刷新
		function re() {
			$("#order_ID").val('');
			$("#order_customerName").val('');
			gridObj.options.otherParames = $('#searchForm').serializeArray();
			gridObj.refreshPage();
		}
	</script>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
		<jsp:param name="matchType" value="exactly" />
	</jsp:include>
</body>
</html>