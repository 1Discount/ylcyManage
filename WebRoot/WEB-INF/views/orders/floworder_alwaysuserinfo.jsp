<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" +

	request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 

"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
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
a.ahover-tips {
	background: url(/ylcyManage/static/images/question2.png) no-repeat;
	margin-left: -50px;
}
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
		<div class="col-md-12">
		<div class="panel">
		<div class="panel-body">
		<form class="form-inline" id="searchForm" role="form" method="get" action="#">
			<DIV class="form-group">
				<LABEL class="inline-label">国家：</LABEL>
				<INPUT id="countryName" class="form-control" name="countryName" type="text" >
			</DIV>
       		<DIV class="form-group">
				<LABEL class="inline-label">时间：</LABEL>
				<INPUT id="order_creatorDatebegin" class="form-control form_datetime" name="searchDate" type="text" data-date-format="YYYY-MM">
			</DIV>
       		
		<div class="form-group">
		<button class="btn btn-primary" 
		type="button" onclick="gridObj.options.otherParames =$('#searchForm').serializeArray();gridObj.page(1);">搜索</button></div></form></div></div>
		<div class="panel">
		<div class="panel-heading">
		
		<h3 class="panel-title">全部使用信息<span style="color:red;">（订单 卡 已用流量  当天剩余总流量）</span></h3></div>
		<div class="panel-body">
		<div class="table-responsive"><table id="searchTable">
		        <tr>
                   <th w_index="countryName" width="5%;" w_length="999"><b>国家</b></th>
				 	<th w_index="first" width="5%;" w_length="999"><b>1</b></th>
					<th w_index="second" width="5%;" w_length="999"><b>2</b></th>
					<th w_index="third" width="5%;" w_length="999"><b>3</b></th>
					<th w_index="fourth" width="5%;" w_length="999"><b>4</b></th>
					<th w_index="fifth" width="5%;" w_length="999"><b>5</b></th> 
					<th w_index="sixth"  width="5%;"  w_length="999"><b>6</b></th>
					<th w_index="seventh" width="5%;" w_length="999"><b>7</b></th>
					<th w_index="eighth" width="5%;" w_length="999"><b>8</b></th>
					<th w_index="ninth" width="5%;" w_length="999"><b>9</b></th>
					<th w_index="tenth" width="5%;" w_length="999"><b>10</b></th>
					<th w_index="eleventh" width="5%;" w_length="999"><b>11</b></th>
					<th w_index="twelfth" width="5%;" w_length="999"><b>12</b></th>
					<th w_index="thirteenth" width="5%;" w_length="999"><b>13</b></th>
					<th w_index="fourteenth" width="5%;" w_length="999"><b>14</b></th>
					<th w_index="fifteenth" width="5%;" w_length="999"><b>15</b></th>
					<th w_index="sixteenth" width="5%;" w_length="999"><b>16</b></th>
					<th w_index="seventeenth" width="5%;" w_length="999"><b>17</b></th>
					<th w_index="eighteenth" width="5%;" w_length="999"><b>18</b></th>
					<th w_index="nineteeneh" width="5%;" w_length="999"><b>19</b></th>
					<th w_index="twentieth" width="5%;" w_length="999"><b>20</b></th>
					<th w_index="twentyFirst" width="5%;" w_length="999"><b>21</b></th>
					<th w_index="twentySecond" width="5%;" w_length="999"><b>22</b></th>
					<th w_index="twentyThird" width="5%;" w_length="999"><b>23</b></th>
					<th w_index="twentyFourth" width="5%;" w_length="999"><b>24</b></th>
					<th w_index="twentyFifth" width="5%;" w_length="999"><b>25</b></th>
					<th w_index="twentySixth" width="5%;" w_length="999"><b>26</b></th>
					<th w_index="twentySeventh" width="5%;" w_length="999"><b>27</b></th>
					<th w_index="twentyEighth" width="5%;" w_length="999"><b>28</b></th>
					<th w_index="twentyNinth" width="5%;" w_length="999"><b>29</b></th>
					<th w_index="thirtieth" width="5%;" w_length="999"><b>30</b></th>
					<th w_index="thirtyFirst" width="5%;" w_length="999"><b>31</b></th>
		        </tr>
		</table></div>
		</div></div>
		</div>
	</SECTION></SECTION>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
<%-- 	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script> --%>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script src="<%=basePath%>static/js/jquery.zclip.min.js"></script>
	<SCRIPT type="text/javascript">
   		bootbox.setDefaults("locale","zh_CN");
   	  	var gridObj;
        $(function(){
        
        	
        	$("#order_creatorDatebegin").datetimepicker({
           		pickDate: true,
           		pickTime: true,
           		showToday: true,
           		language:'zh-CN',
           		defaultDate: moment(),
           	});
        	
            gridObj = $.fn.bsgrid.init('searchTable',{
	            url:'<%=basePath%>orders/flowdealorders/floworderalwayuserinfo',
				pageSizeSelect : true,
				otherParames:$('#searchForm').serializeArray(),
				pageAll : true,
				additionalBeforeRenderGrid:function(parseSuccess, gridData,options){
				}
			});
		});
        
        
        /**
         * jQuery.bsgrid v1.35 by @Baishui2004
         * Copyright 2014 Apache v2 License
         * https://github.com/baishui2004/jquery.bsgrid
         */
        /**
         * @author Baishui2004
         * @Date August 31, 2014
         */
        (function ($) {

            $.bsgridLanguage = {
                isFirstPage: '已经是第一页！',
                isLastPage: '已经是最后一页！',
                needInteger: '请输入数字！',
                needRange: function (start, end) {
                    return '请输入一个在' + start + '到' + end + '之间的数字！';
                },
                errorForRequestData: '请求数据失败！',
                errorForSendOrRequestData: '发送或请求数据失败！',
                noPagingation: function (noPagingationId) {
                    return '共:&nbsp;<span id="' + noPagingationId + '"></span>';
                },
                pagingToolbar: {
                    pageSizeDisplay: function (pageSizeId, ifLittle) {
                        var html = '';
                        if (!ifLittle) {
                            html += '每页显示:';
                        }
                        return html + '&nbsp;<select id="' + pageSizeId + '"></select>';
                    },
                    currentDisplayRows: function (startRowId, endRowId, ifLittle) {
                        var html = '';
                        if (!ifLittle) {
                            html += '当前显示:';
                        }
                        return html + '&nbsp;<span id="' + startRowId + '"></span>&nbsp;-&nbsp;<span id="' + endRowId + '"></span>';
                    },
                    totalRows: function (totalRowsId) {
                        return '共:&nbsp;<span id="' + totalRowsId + '"></span>';
                    },
                    currentDisplayPageAndTotalPages: function (curPageId, totalPagesId) {
                        return '<div><span id="' + curPageId + '"></span>&nbsp;/&nbsp;<span id="' + totalPagesId + '"></span></div>';
                    },
                    firstPage: '首&nbsp;页',
                    prevPage: '上一页',
                    nextPage: '下一页',
                    lastPage: '末&nbsp;页',
                    gotoPage: '跳&nbsp;转',
                    refreshPage: '刷&nbsp;新'
                },
                loadingDataMessage: '此次加载数据需要1分钟，请您稍等......',
                noDataToDisplay: '没有数据可以用于显示。'
            };

        })(jQuery);
	</script>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
		<jsp:param name="matchType" value="exactly" />
	</jsp:include>
</body>
</html>