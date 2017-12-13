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
<title>设备短信查看-客服查询中心-EASY2GO ADMIN</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet" href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
<meta name="csrf_token">
<%@include file="/WEB-INF/views/common/_ie8support.html"%>
<!-- 这里某些页面有内容, 见带  block head 的那些 jade 文件 -->
</head>
<body>
	<section id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
		<section id="main-content">
			<section class="wrapper">
				<DIV class="col-md-12">
					<DIV class="panel">
						<DIV class="panel-body">

							<FORM class="form-inline" id="searchForm" role="form" method="get" action="#">
								<DIV class="form-group">
									<LABEL class="inline-label">机身码：</LABEL>
									<INPUT class="form-control" id="SN" name="SN" type="text" placeholder="机身码">
								</DIV>

								<DIV class="form-group">
									<LABEL class="inline-label">IMSI：</LABEL>
									<INPUT class="form-control" id="IMSI" name="IMSI" type="text" placeholder="IMSI">
								</DIV>


								<div class="form-group">
									<label class="inline-label">类型：</label>
									<select name="type" class="form-control">
										<option value="">全部</option>
										<option value="本地">本地</option>
										<option value="漫游">漫游</option>
									</select>
								</div>

								<DIV class="form-group">
									<BUTTON class="btn btn-primary" type="button" onclick="gridObj.options.otherParames =$('#searchForm').serializeArray();gridObj.page(1);">搜索</BUTTON>
									<BUTTON class="btn btn-primary" onclick="re();" type="button">刷新</BUTTON>
								</DIV>
							</FORM>
						</DIV>
					</DIV>
					<DIV class="panel">
						<DIV class="panel-heading">
							<H3 class="panel-title">所有设备短信</H3>
						</DIV>
						<DIV class="panel-body">
							<table id="searchTable">
								<tr>
									<th w_index="SN" width="5%;">机身码</th>
									<th w_index="type" width="3%;">类型</th>
									<th w_render="imsi" width="15%;">IMSI</th>
									<th w_index="content" w_align="left" w_length="90" width="65%">短信内容</th>
									<th w_index="creatorDate" width="2%;">创建时间</th>
									<th w_render="operate" width="10%;">操作</th>
								</tr>
							</table>
						</DIV>
					</DIV>
				</DIV>
			</section>
		</section>
	</section>





	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script src="<%=basePath%>static/js/jquery.zclip.min.js"></script>



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
          	    url:'<%=basePath%>device/devmessage/getpage',
				pageSizeSelect : true,
				pageSize : 15,

				pageSizeForGrid : [ 5, 30, 50, 100 ],
				autoLoad : true,
				otherParames : $('#searchForm').serializeArray(),
				additionalAfterRenderGrid : function(parseSuccess, gridData,
						options) {
					if (parseSuccess) {

					}
				}
			});
         var buttonHtml = '<td style="text-align: left;">';
	     buttonHtml += '<table><tr>';
	     buttonHtml += '<td><input type="button" value="导出数据" onclick="exprotexcel()" /></td>';
	     buttonHtml += '</tr></table>';
	     buttonHtml += '</td>';
	     $('#' + gridObj.options.pagingOutTabId + ' tr:eq(0)').prepend(buttonHtml);
		});
	  
     	 //数据导出
	     function exprotexcel(){
	    	 window.location.href="<%=basePath%>device/devmessage/exportexcel?"+$('#searchForm').serialize();
	     }
	     	
	    //自定义列
	     function operate(record, rowIndex, colIndex, options) {
			  var content=record.content;
		      return '<a href="#" id="'+rowIndex+'" onmousemove="copy('+rowIndex+',\''+content+'\')" class="glyphicon btn btn-primary btn-xs">复制</a>';
         }
	    //copy
	 
		function copy(t,content){
			 $("#"+t).zclip({
				path:'<%=basePath%>static/js/ZeroClipboard.swf',
		 		copy:content+'',
		 		afterCopy: function(){
		 			easy2go.toast('info', "复制成功!");
		 		}
		 	});
		} 
	 
	    function imsi(record, rowIndex, colIndex, options){
	    	if(record.type=="漫游"){
		    	 return '<A  href="<%=basePath%>sim/roamingsiminfo/index?SN='+record.SN+'">'+record.IMSI+'</A>';
	    	}else if(record.type="本地"){
		    	 return record.IMSI;

	    	}
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