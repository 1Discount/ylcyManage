<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" +

			request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 

"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>备卡查询</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-

scale=1.0, user-scalable=no">
<link rel="stylesheet"
	href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet"
	href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
	<style type="text/css">
		a.ahover-tips{ background: url(/ylcyManage/static/images/question2.png) no-repeat; margin-left: -50px;}
		/*  #searchTable tr{height:40px;} */
	</style>
<meta name="csrf_token">
<%@include file="/WEB-INF/views/common/_ie8support.html"%>
<%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
	<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />

	<SECTION id="main-content"> <SECTION class="wrapper">
	<DIV class="col-md-12">
		<DIV class="panel">
			<DIV class="panel-body">
				<form class="form-inline" id="searchForm" role="form" method="get"
					action="#">
					<div class="form-group">
						<label class="inline-label">国 家：</label><input
							class="form-control" name="countryName" type="text"/>
					</div>
					<div class="form-group">
						<label class="inline-label">日&nbsp;期：</label> 
						<INPUT id="order_creatorDatebegin" class="form-control form_datetime" name="date" type="text" data-date-format="YYYY-MM-DD HH:mm">
					</div>
								 <div class="form-group">     　
         <label class="checkbox-items"><INPUT class="" name="ifOnlyString" value="1" type="checkbox" id="ifOnlyString" >    只显示飘红</label>
					<button class="btn btn-primary" type="button"
							onclick="$('input').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.refreshPage();">搜索</button>					
		
         
		</div>	
				</form>
				</DIV></div>
					<DIV class="panel" style="border-top:  ">
			<DIV class="panel-body">
               <div class="row" style="margin-top: -20px;">
					<div class="alert alert-info clearfix">
						<span class="alert-icon"><i class="fa fa-bell-o"></i></span>
						<div class="notification-info">
							  <p style=" float:left;position: relative;color:#000; top:10px; margin-top: -10px;"> 1. 表格中红色的数据代表(可使用　+　使用中　&lt;　用户订单数) 即需要准备SIM卡  　　　　　　　　　　2.SIM卡总数不包含多国卡、已下架、过期停用的卡　 , 　绿色的字体表示该设备当天可以在多国使用</p>
							 <br />
							  <p style=" float:left;position: relative;color:#000;  "> 3.目前暂时将官网、app下的订单已过滤掉　　　　　　　　　　　　　　　　　　　　　　　　　　　　4.过期停用表示卡状态为可使用、使用中，但不在有效期（即为不可用的卡）</p>
							  <p style=" float:left;position: relative;color:#000; "> </p><br />
						</div>
					</div>
				</div>
               </DIV>
				<table id="searchTable"  class="bsgrid" style="margin-top:-30px; border:  1px solid #000; ">
					<tr classs="extend_render_per_row">
						 <TH w_render="countrName" width="5%;"><b style="font-weight: 700px;">国家</b></TH>
					 	 <th w_index="flowdealCount" w_sort="flowdealCount,unorder" id="flowCount" width="5%;"><b>订单数</b></th> 
						 <th maxLength="" w_index="SN" w_length="999999999999999999999999"  id="SN" width="61%;"><b>订单SN</b></th> 
						 <th w_index="SIMCount"  id="SIMCount"  width="4%;"><b>卡总数</b></th>
						 <th w_render="useing"   width="4%;"><b>使用中</b></th>
						 <th w_render="available"   width="4%;"><b>可使用</b></th>
						 <th w_render="notavailable"   width="4%;"><b>不可用</b></th>
						 <th w_index="stock"   width="4%;"><b>库存</b></th>
						 <th w_render="disabled"   width="4%;"><b>备用</b></th>
						 <th w_index="disabledTwo"   width="4%;"><b>过期停用</b></th>
						 <th w_render="offtheshelf"   width="4%;"><b>下架</b></th>
					</tr>
				</table>
			</DIV>
		</DIV>
	</DIV>
	</SECTION></SECTION>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script src="<%=basePath%>static/js/jquery.zclip.min.js"></script>
	<SCRIPT type="text/javascript">
     function operate(record, rowIndex, colIndex, options) {
		  var SN=record.SN;
		   var j=(record.SN).split("/").length;
		    for(var i=0;i<j-1;i++){
				SN=SN.replace("/","w");
		   	}   
         return '<a href="#" id="'+rowIndex+'" onmousemove="copy('+rowIndex+',\''+SN+'\')" class="glyphicon  btn-primary btn-xs">复制SN</a>';
	  }
	 $.bsgrid.forcePushPropertyInObject($.fn.bsgrid.defaults.extend.renderPerRowMethods, 'extend_render_per_row', function(record, rowIndex, trObj, options){
 		var c=parseInt(record.useing)+parseInt(record.available);
		 trObj.children('td:nth-child(3)').css({'text-align':'left'});
		 var b=parseInt(record.flowdealCount);
		 if(c<b){
			 trObj.children('td').css({'background-color':'pink'});
		 }
		 if($("#ifOnlyString").is(':checked'))
		 {
			if( trObj.children('td').attr('style').indexOf("pink")==-1){
				trObj.css({'display':'none'});
				var total = $("#searchTable_no_pagination").text();
				$("#searchTable_no_pagination").text(total-1);
			}
		 }
   });
	 
    bootbox.setDefaults("locale","zh_CN");
    
			$("#order_creatorDatebegin").datetimepicker({
				pickDate : true, 
				pickTime : true, 
				showToday : true, 
				language : 'zh-CN',
				defaultDate : moment(),
			});
    	  
    	  var gridObj;
          $(function(){
             gridObj = $.fn.bsgrid.init('searchTable',{
                url:'<%=basePath%>flowplan/countryinfo/getpage',
                otherParames:$('#searchForm').serializeArray(),
				pageSizeSelect : true,
				pageAll:true,
				lineWrap:true,
				additionalAfterRenderGrid: function(parseSuccess, gridData, options) {
                    if(parseSuccess){
                	}
	            },
				extend: {
	                settings:{
	                    supportColumnMove: true 
	                }
		        }
			});

			
		});
          function copy(t,sn){
      			   var j=sn.split("w").length;
      			    for(var i=0;i<j-1;i++){
      					sn=sn.replace("w","/");
      			   	}   
      		 $("#"+t).zclip({
      			path:'<%=basePath%>static/js/ZeroClipboard.swf',
      	 		copy:sn+'',
      	 		afterCopy: function(){
      	 			easy2go.toast('info', "复制成功!");
      	 		}
      	 	});
      	}
		  function countrName(record, rowIndex, colIndex, options){
       	   	  var countryName = record.countrName;
	          return '<A style="color:#1FB5AD" href="<%=basePath%>sim/siminfo/index?countryName='+countryName+'">'+record.countrName+'</A>';
          } 
		  function useing(record, rowIndex, colIndex, options){
	       	 	 var countryName = record.countrName;
	         	 return '<A style="color:#1FB5AD;" href="<%=basePath%>sim/siminfo/index?cardZT=使用中&countryName='+countryName+'"> '+record.useing+' </A>';
	      	 }
			 function available(record, rowIndex, colIndex, options){
	       	 	 var countryName = record.countrName;
	         	 return '<A  style="color:#1FB5AD;" href="<%=basePath%>sim/siminfo/index?cardZT=可用&countryName='+countryName+'"> '+record.available+' </A>';
	      	 }
			 function notavailable(record, rowIndex, colIndex, options){
	       	 	 var countryName = record.countrName;
	         	 return '<A  style="color:#1FB5AD;" href="<%=basePath%>sim/siminfo/index?cardZT=不可用&countryName='+countryName+'"> '+record.notavailable+' </A>';
	      	 }
			 function disabled(record, rowIndex, colIndex, options){
	       	 	 var countryName = record.countrName;
	         	 return '<A  style="color:#1FB5AD;" href="<%=basePath%>sim/siminfo/index?cardZT=停用&countryName='+countryName+'"> '+record.disabled+' </A>';
	      	 } 
			 function offtheshelf(record, rowIndex, colIndex, options){
	       	 	 var countryName = record.countrName;
	         	 return '<A style="color:#1FB5AD;" href="<%=basePath%>sim/siminfo/index?cardZT=下架&countryName='+countryName+'"> '+record.offtheshelf+' </A>';
	      	 }
	</script>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
<jsp:param name="matchType" value="exactly" />
</jsp:include>
</body>
</html>