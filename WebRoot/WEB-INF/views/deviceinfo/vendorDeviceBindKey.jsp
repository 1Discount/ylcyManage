<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>合作方设备绑定码管理-设备管理-流量运营中心</title>
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
		<SECTION id="main-content">
			<SECTION class="wrapper">
				<DIV class="col-md-12">

					<DIV class="panel">
					   <header class="panel-heading tab-bg-dark-navy-blue">
                            <ul class="nav nav-tabs nav-justified "><%-- 由接口分配的OP去延迟激活菜单 --%>
                                <li class="main-menu-tab " id="listKey"><!-- active --><a data-toggle="tab" href="#tabKey">绑定码列表</a></li>
                                <li class="main-menu-tab" id="editKey"><a data-toggle="tab" href="#tabGenKey">生成绑定码</a></li>
<!--                                 <li class="main-menu-tab" id="listRecord"><a data-toggle="tab" href="#tabRecord">客户绑定记录</a></li> -->
                            </ul>
                        </header>

						<DIV class="panel-body">
						      <div class="tab-content">

						      <div id="tabKey" class="tab-pane "><!-- active -->

                    <DIV class="panel">
                        <DIV class="panel-body">

                            <FORM class="form-inline" id="searchForm" role="form" method="get" action="#">
                                <input type="hidden" value="1" id="pagenum"/><input type="hidden" id="pagesize" value="20" />


                                <DIV class="form-group">
                                    <LABEL class="inline-label">设备机身码：</LABEL><INPUT style="width: 137px;" class="form-control" id="SN" name="SN" type="text" placeholder="设备机身码">
                                </DIV>

                                <DIV class="form-group">
                                    <LABEL class="inline-label">绑定码：</LABEL><INPUT style="width: 137px;" class="form-control" id="key" name="key" type="text" placeholder="绑定码">
                                </DIV>

                                <DIV class="form-group">
                                    <LABEL class="inline-label">时间段：</LABEL> <INPUT
                                        class="form-control form_datetime"
                                        id="query_startDate_key"
                                        name="startDate" type="text"
                                        data-date-format="YYYY-MM-DD HH:mm:ss">
                                </DIV>

                                <DIV class="form-group">
                                    <LABEL class="inline-label">到</LABEL> <INPUT
                                        class="form-control form_datetime"
                                        id="query_endDate_key"
                                        name="endDate" type="text"
                                        data-date-format="YYYY-MM-DD HH:mm:ss">
                                </DIV>

<!--                                 <div class="form-group"> -->
<!--                                   <label class="inline-label">是否绑定：</label> -->
<!--                                   <select name="useStatus" style="width: 137px;" class="form-control"> -->
<!--                                     <option value="">全部</option> -->
<!--                                     <option value="是">是</option> -->
<!--                                     <option value="否">否</option> -->
<!--                                   </select> -->
<!--                                 </div> -->

<!--                                 <div class="form-group"> -->
<!--                                   <label class="inline-label">可用状态：</label> -->
<!--                                   <select name="status" style="width: 137px;" class="form-control"> -->
<!--                                     <option value="">全部状态</option> -->
<!--                                     <option value="正常">正常</option> -->
<!--                                     <option value="禁用">禁用</option> -->
<!--                                   </select> -->
<!--                                 </div> -->

                                <DIV class="form-group">
                                    <BUTTON class="btn btn-primary" type="button"
                                        onclick="if(!isBeginBeforeEndDate()){alert('请保证开始时间不大于结束时间');return;};gridObj.options.otherParames =$('#searchForm').serializeArray();gridObj.page(1);">搜索</BUTTON>
                                    <BUTTON class="btn btn-primary" onclick="re();" type="button">刷新</BUTTON>
                                </DIV>

<%--                                <div class="form-group"><label class="inline-label" style="margin-left:30px;font-size:14px;">默认显示全部有效订单 <a href="#note1"><em>说明(注1)</em></a></label></div>--%>
                            </FORM>
                        </DIV>
                    </DIV>

                              <div class="table-responsive">
								<table id="searchTable">
									<tr>
										<th w_index="keyId" width="10%;" w_sort="keyId,desc">流水ID</th>
										<th w_index="SN" width="10%;">设备机身码</th>
										<th w_index="key" width="10%;">绑定码</th>
                                        <th w_index="vendorId" width="10%;">合作方ID</th>
<!-- 										<th w_render="render_startDate" width="10%;">开始日期</th> -->
<!-- 										<th w_render="render_endDate" width="10%;">结束日期</th> -->
<!-- 										<th w_render="render_status" width="5%;">可用状态</th> -->
<!-- 										<th w_render="render_type" width="5%;">充值类型</th> -->
<!-- 										<th w_render="render_amount" width="5%;">金额</th> -->
										<th w_render="render_useDateTime" width="10%;">绑定时间</th>
										<th w_index="remark" width="10%;">备注</th>
										<th w_index="creatorUserName" width="5%;">创建人</th>
                                        <th w_index="creatorDate" width="10%;">创建时间</th>
<%--										<th w_index="modifyDate" width="10%;">最后更新</th>--%>
										<th w_render="operate" width="30%;">操作</th>
									</tr>
								</table>

<!-- 	                            <div style="padding:15px;"><p>备注:</p><p id="note1">1. “时间段”目前系指绑定码的开始时间的范围，表示该时间段内可绑定。</p> -->
<!-- 	                            <p id="note1">2. 新添加绑定码保存成功会自动发送绑定码到该手机，若发送失败的注意及时手动补发。 -->
<!-- 	                            只有还未绑定的才可考虑重发短信，以提醒客户。</p> -->
<!-- 	                            </div> -->
                            </div>

                    <DIV class="panel">
                        <DIV class="panel-body">
		                范围：<label class="checkbox-items" style="padding-right:10px;"><INPUT name="exportRange" value="1" id="exportRangeAll" type="radio" checked="checked">全部搜索结果</label>
		                <label class="checkbox-items" style="padding-right:10px;"><INPUT name="exportRange" value="0" id="exportRangeCurPage" type="radio" >当前页</label>
                        <br />选项：<label class="checkbox-items" style="padding-right:10px;"><INPUT name="optionSnKey" value="1" id="option-only-sn-key" type="checkbox" >仅导出机身码和绑定码列</label>
		                <br /><button id="exportExcelButton"><a href="javascript:void(0);">导出到 Excel</a></button><br /><br />
                        </DIV>
                    </DIV>

                            </div>

                            <div id="tabGenKey" class="tab-pane">
                               <c:if test="${not empty info}">
                               <div><p class="well red">${info}</p></div>
                               </c:if>

                                <form id="edit_form" role="form" action="" method="post"
						          autocomplete="off" class="form-horizontal">
						<%--            <input type="hidden" name="_csrf" value="QLaKdKJy-zJ2Cd9BWFfWknPv6_QqFwXns5rg"> --%>
						            <input type="hidden" name="keyId" value="${Model.keyId}" />
						            <input type="hidden" name="keyTimestamp" value="${Model.keyTimestamp}" />
<%-- 						            <input type="hidden" name="sendSmsCount" value="${Model.sendSmsCount}" /> --%>

						            <div class="form-group"  id="ifBatchDiv">
						              <label class="col-sm-3 control-label">是否批量发放：</label>
						              <div class="col-sm-6">
						                  <label for="ifBatchFalse" class="radio-inline"><input type="radio" name="ifBatch" id="ifBatchFalse" value="0" checked>单个</label>
						                  <label for="ifBatchTrue" class="radio-inline"><input type="radio" name="ifBatch" id="ifBatchTrue" value="1">批量</label>
						              </div>
                                    </div>
                                    <div class="form-group" id="batchListDiv">
                                      <label for="batchList" class="col-md-3 control-label">批量导入设备机身码:</label>
                                      <div class="col-md-6">
                                        <textarea id="batchList" rows="5" name="batchList" maxlength="2000" data-popover-offset="0,8"
                                        class="form-control"></textarea>
                                      </div>
                                      <div class="col-sm-3"><span class="red">* <em>以逗号分隔的机身码列表</em></span></div><!-- 批量发放在后台成功创建后, 会导出 Excel 档并提示是否保存 -->
                                    </div>

						            <div class="form-group" id="singleDiv">
						              <label for="SN" class="col-md-3 control-label">单个设备机身码:</label>
						              <div class="col-md-6">
						                <input id="SN" type="text" name="SN" value="${Model.SN}" data-popover-offset="0,8"
						                minlength="5" maxlength="20" class="form-control">
						              </div>
						              <div class="col-sm-3">
						                <p class="form-control-static"><span class="red">* 15位完整机身码或者机身码末尾6位部分，后者在提交后会自动判断补上前缀部分</span>
						                </p>
						              </div>
						            </div>
						            <div class="form-group" id="keyDiv">
						              <label for="key" class="col-md-3 control-label">绑定码:</label>
						              <div class="col-md-6">
						                <input id="key" type="text" name="key" value="${Model.key}" data-popover-offset="0,8" readonly='readonly'
						                maxlength="50" class="form-control">
						              </div>
						              <div class="col-sm-3">
						                <p class="form-control-static"><span class="red"> </span><a class="btn btn-success btn-xs" onclick="getKey();return;"><span>刷新/预览绑定码</span></a>
						                如果不提供或批量发放时, 则后台保存时自动生成
						                </p>
						              </div>
						            </div>
                                    <div class="form-group">
                                      <label for="status" class="col-md-3 control-label">可用状态:</label>
                                      <div class="col-md-6">
                                        <select id="status" name="status" required class="form-control">
                                        <c:if test="${empty Model.status or Model.status eq '正常'}">
                                          <option value="正常" selected="selected">正常</option>
                                          <option value="禁用">禁用</option>
                                        </c:if>
                                        <c:if test="${Model.status eq '禁用'}">
                                          <option value="正常">正常</option>
                                          <option value="禁用" selected="selected">禁用</option>
                                        </c:if>
<%--                            <c:forEach items="${DistributorTypeDict}" var="item" varStatus="status">--%>
<%--                                        <c:if test="${Model.type eq item.label}">--%>
<%--                                          <option value="${item.label}" selected="selected">${item.label}</option>--%>
<%--                                        </c:if>--%>
<%--                                        <c:if test="${Model.type ne item.label}">--%>
<%--                                          <option value="${item.label}">${item.label}</option>--%>
<%--                                        </c:if>--%>
<%--                            </c:forEach>--%>
                                        </select>
                                      </div>
                                      <div class="col-sm-3">
                                        <p class="form-control-static"><span class="red">* 正常或禁用。可以控制是否可允许绑定, 要禁用时慎重考虑</span>
                                        </p>
                                      </div>
                                    </div>
						            <div class="form-group">
						              <label for="remark" class="col-md-3 control-label">备注:</label>
						              <div class="col-md-6">
						                <textarea id="remark" rows="3" name="remark" maxlength="99" data-popover-offset="0,8"
						                class="form-control">${Model.remark}</textarea>
						              </div>
						              <div class="col-sm-3"></div>
						            </div>
						            <div class="form-group">
						              <div class="col-md-6 col-md-offset-3">
						                <div class="btn-toolbar">
						                  <button type="submit" class="btn btn-primary">保存</button>
						                  <button type="button" onclick="window.location.href='<%=basePath %>device/bindkey/index';"
						                  class="btn btn-default">取消</button><!-- javascript:history.go(-1); -->
						                </div>
						              </div>
						            </div>
						          </form>

                            </div>

                            </div><!-- tab-content -->
						</DIV>
						</DIV>
				</DIV>
			</SECTION>
		</SECTION>
	</section>

	<div id="myModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- dialog body -->
				<div class="modal-body">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					Hello world!
				</div>
				<!-- dialog buttons -->
				<div class="modal-footer">
					<button type="button" class="btn btn-primary">保存</button>
					<button type="button" class="btn btn-primary">取消</button>
				</div>
			</div>
		</div>
	</div>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script src="<%=basePath %>static/js/accounting/accounting.min.js"></script>
	<SCRIPT type="text/javascript">
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
    bootbox.setDefaults("locale","zh_CN");
    	  var gridObj;
          $(function(){
              $("#query_startDate_key").datetimepicker({
                  format: 'YYYY-MM-DD HH:mm:ss',
                  pickDate: true,                 //en/disables the date picker
                  pickTime: true,                 //en/disables the time picker
                  showToday: true,                 //shows the today indicator
                  language:'zh-CN',                  //sets language locale
                  defaultDate: moment().add(-1, 'months'),                 //sets a default date, accepts js dates, strings and moment objects
              });
               $("#query_endDate_key").datetimepicker({
                   format: 'YYYY-MM-DD HH:mm:ss',
                  pickDate: true,                 //en/disables the date picker
                  pickTime: true,                 //en/disables the time picker
                  showToday: true,                 //shows the today indicator
                  language:'zh-CN',                  //sets language locale
                  defaultDate: moment().add(0, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
              });
//              $(".query_startDate").datetimepicker({
//             	format: 'YYYY-MM-DD',
//          		pickDate: true,                 //en/disables the date picker
//          		pickTime: false,                 //en/disables the time picker
//          		showToday: true,                 //shows the today indicator
//          		language:'zh-CN',                  //sets language locale
//          		defaultDate: moment().add(-3, 'months'),                 //sets a default date, accepts js dates, strings and moment objects
//          	});
//              $(".query_endDate").datetimepicker({
//                  format: 'YYYY-MM-DD',
//           		pickDate: true,                 //en/disables the date picker
//           		pickTime: false,                 //en/disables the time picker
//           		showToday: true,                 //shows the today indicator
//           		language:'zh-CN',                  //sets language locale
//           		defaultDate: moment().add(0, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
//           	});
//              $("#edit_startDate").datetimepicker({
//                  format: 'YYYY-MM-DD',
//                  pickDate: true,                 //en/disables the date picker
//                  pickTime: false,                 //en/disables the time picker
//                  showToday: true,                 //shows the today indicator
//                  language:'zh-CN',                  //sets language locale
//                  defaultDate: moment().add(0, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
//              });
//               $("#edit_endDate").datetimepicker({
//                   format: 'YYYY-MM-DD',
//                  pickDate: true,                 //en/disables the date picker
//                  pickTime: false,                 //en/disables the time picker
//                  showToday: true,                 //shows the today indicator
//                  language:'zh-CN',                  //sets language locale
//                  defaultDate: moment().add(30, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
//              });

             var pagesize=parseInt($("#pagesize").val());
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath%>device/bindkey/datapage',
                 pageSizeSelect: true,
                 pageSize:pagesize,
                 pageSizeForGrid:[5,30,50,100],
                 displayPagingToolbarOnlyMultiPages:true,
                 multiSort:true,
                 autoLoad:false,
                 otherParames:$('#searchForm').serializeArray(),
                 rowHoverColor:true,
                 additionalAfterRenderGrid: function(parseSuccess, gridData, options) {
                	 if(parseSuccess){
                		 $("#pagenum").val(options.curPage);
                		 var a = $("#searchTable_pt_pageSize").val();
                		 $("#pagesize").val(a);
                	 }

                	 // 导出用
                	 if(parseSuccess){
                         //var a = $("#searchTable_pt_pageSize").val();
                           var sta= $("#searchTable_pt_startRow").html();
                           var end= $("#searchTable_pt_endRow").html();
                           var cur = $("#searchTable_pt_curPage").html();
                           var total = $("#searchTable_pt_totalRows").html();
                           var exportString = "exportExcel?SN=" + $('#searchForm input[name="SN"]').val() +
                           "&key=" + $('#searchForm input[name="key"]').val() + "&vendorID=" + $('#searchForm select[name="vendorId"]').val() +
                           //"&status=" + $('#searchForm select[name="status"]').val() + //"&type=" + $('#searchForm select[name="type"]').val() +
                           "&begindate=" + $('#searchForm input[name="startDate"]').val() + "&enddate=" + $('#searchForm input[name="endDate"]').val() +
                           "&sta="+sta+"&end="+end+"&cur="+cur+"&pagesize="+pagesize+"&total="+total;
                           var all;
                           if($('#exportRangeCurPage').is(':checked')){
                        	   all = 0;
                           } else {
                        	   all = 1; // 默认使用else
                           }
                           var optionSnKey;
                           if($('#option-only-sn-key').is(':checked')){
                             optionSnKey = 1;
                           } else {
                             optionSnKey = 0;
                           }
                           // 暂不要用这值 alert($('input[name="exportRange"]').val());
                           $("#exportExcelButton a").attr("href", exportString + "&all=" + all + "&optionSnKey=" + optionSnKey); // 默认全部 all=1 若当前页则使用 all=0 或不提供 all
                           $('#exportRangeAll').click(function(){
                             //if($(this).is(':checked')){
                               all = 1;
                            	   $("#exportExcelButton a").attr("href",exportString + "&all=1" + "&optionSnKey=" + optionSnKey);
                                 //}
                           });
                           $('#exportRangeCurPage').click(function(){
                               //if($(this).is(':checked')){
                                 all = 0;
                                     $("#exportExcelButton a").attr("href",exportString + "&all=0" + "&optionSnKey=" + optionSnKey);
                                   //}
                             });

                           $('#option-only-sn-key').click(function(){
                             if($(this).is(':checked')){
                               optionSnKey = 1;
                               $("#exportExcelButton a").attr("href",exportString + "&all=" + all + "&optionSnKey=1");
                             } else {
                               optionSnKey = 0;
                               $("#exportExcelButton a").attr("href",exportString + "&all=" + all + "&optionSnKey=0");
                             }
                           });
                       } else {
                           $("#exportExcelButton a").attr("href","javascript:void(0);");
                       }
                 }
             });

             //var href = $("#b a").attr("href");

             	if($("#pagenum").val()!=""){
	        	   gridObj.page($("#pagenum").val());
	           }else{
	        	   gridObj.page(1);
	           }

         });

          //自定义列
         function operate(record, rowIndex, colIndex, options) {
             //'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>';

             if(record.useDateTime == null || record.useDateTime == ''){ // 未绑定才可以编辑
	             var opString = '<a class="btn btn-primary btn-xs" onclick="edit('+rowIndex+')"><span class="glyphicon glyphicon-edit">编辑</span></a>';
	             if(record.status == '正常'){
	                 opString += '&nbsp;&nbsp;<a class="btn btn-danger btn-xs" onclick="disablebyid('+rowIndex+')"><span class="glyphicon glyphicon-remove">禁用绑定</span></a>';
// 	                 opString += '&nbsp;&nbsp;<a class="btn btn-primary btn-xs" onclick="sendSms('+rowIndex+')"><span class="glyphicon glyphicon-share-alt">发短信</span></a>';
	             }else{
	            	 opString += '&nbsp;&nbsp;<a class="btn btn-primary btn-xs" onclick="enablebyid('+rowIndex+')"><span class="glyphicon glyphicon-plus">允许绑定</span></a>';
	            	 //opString += '&nbsp;&nbsp;&nbsp;&nbsp;　　　　';
	             }
	             return opString;
             }
             return ""; // 其他情况不可编辑
         }

         function render_startDate(record, rowIndex, colIndex, options) {
             return record.startDate.substr(0, 10);
          }
         function render_endDate(record, rowIndex, colIndex, options) {
             return record.endDate.substr(0, 10);
          }
         function render_useDateTime(record, rowIndex, colIndex, options) {
        	 if(record.useDateTime == null || record.useDateTime == ''){
        		 return '未绑定';
        	 } else {
        		 return '<a class="btn btn-success btn-xs" onclick="return;"><span>' + record.useDateTime + '</span></a>';
        	 }
          }

//          function render_status(record, rowIndex, colIndex, options) {
//              if (record.status == "禁用") {
//             	 return '<a class="btn btn-danger btn-xs" onclick="return;"><span>' + record.status + '</span></a>';
//              } else {
//             	 return record.status;
//              }
//           }

         var op = '${OP}';
         var isKeyListInited = false; // 默认不自动加载绑定码
        var isRecordListInited = false; // 默认不自动加载绑定码记录列表
        var isBatch = false;
        var batchListArray = new Array; // 保存批量输入的内容
        //alert('|' + '   ddd fdfdf ' + '|' + '   ddd fdfdf '.trim());
        jQuery.validator.addMethod("snCustom", function(value, element) {
        	if(isBatch) {//批量时不检查
        		//alert('aaaa');
        		return true;
        	} else {
        		value = value.trim();//alert('1 value.substring(0,10) =' + value.substring(0,10))
	             if(value == '' || !(value.length == 15 || value.length == 6)
	                 /*|| (value.length == 15 && value.substring(0,10) != '1721502100')*/) {
	               return false;
	              }else{
	                  return true;
	              }
        	}
          }, "单个发放必需填写15位完整机身码或6位末尾部分");
       jQuery.validator.addMethod("batchListCheck", function(value, element) {
    	   if(!isBatch) {//单个时不检查
    		   //alert('bbbb');
               return true;
           } else {
        	   value = value.trim();
              if(value==''){
                  return false;
              }else{
            	  var lists = value.split(",");
            	  batchListArray = [];
            	  for(x in lists) {
            		  tvalue = lists[x];
            		  tvalue = tvalue.trim();//alert('2 value.substring(0,10) =' + value.substring(0,10))
            		  if(!(tvalue == '' || tvalue.length == 15 || tvalue.length == 6)
            		      /*|| (tvalue.length == 15 && tvalue.substring(0,10) != '1721502100')*/) {
                          return false;
                      }
            		  batchListArray.push(tvalue);
            	  }
            	  $('#batchList').val(batchListArray.join(","));
                  return true;
              }
           }
          }, "批量发放输入框最大长度不超过2000且使用逗号分隔,全部机身码必须有效,至少有一个合法机身码");

         $(function(){
        	 activateMenu(op);

        	 // 编辑时是否批量的选择. 注意只有新建时可选批量,编辑时都系单个编辑
        	 if($('input[name="keyId"]').val() == '' && $('input[name="ifBatch"]').val() == 1) {
        		 isBatch = true;
        		 $('#singleDiv').hide();
        		 $('#keyDiv').hide();
                 $('#batchListDiv').show();
        	 } else {
        		 isBatch = false;
        		 $('#batchListDiv').hide();
                 $('#singleDiv').show();
                 $('#keyDiv').show();
                 if($('input[name="keyId"]').val() != ''){
                	 $('#ifBatchDiv').hide();
                 }
        	 }
        	 $('#ifBatchFalse').click(function(){
        		 isBatch = false;
        		 $('#batchListDiv').hide();
        		 $('#singleDiv').show();
        		 $('#keyDiv').show();
        		 $(".error-popover").hide(); // 隐藏错误信息
        	 });
        	 $('#ifBatchTrue').click(function(){
                 isBatch = true;
                 $('#singleDiv').hide();
                 $('#keyDiv').hide();
                 $('#batchListDiv').show();
                 $(".error-popover").hide();
             });

             $("#edit_form").validate_popover({
                 rules: {
                   'SN': { //支持批量导入后手动判断
                     snCustom: true,
                     minlength: 5,
                     maxlength: 15
                   },
                   'batchList': { //支持批量导入后手动判断
                   //    maxlength: 1999,
                	   batchListCheck: true
                   },
                   //'key': { //支持批量导入后手动判断
                     //required: true,
                     //maxlength: 50
                   //},
                   'remark': {
                     required: false,
                     maxlength: 99
                   }
                 },
                 messages: {
                   'SN': { //支持批量导入后手动判断
                     snCustom: "请输入15位完整机身码或者机身码末尾6位部分，后者在提交后会自动判断补上前缀部分",
                     maxlength: '请输入15位完整机身码或者机身码末尾6位部分，后者在提交后会自动判断补上前缀部分',
                     minlength: '请输入15位完整机身码或者机身码末尾6位部分，后者在提交后会自动判断补上前缀部分'
                   },
                   'remark': {
                     maxlength: "最多不超过100字"
                   }

                 },
                   submitHandler: function(form){
                       $.ajax({
                           type:"POST",
                           url:"<%=basePath %>device/bindkey/" + ((isBatch)?'saveBatch':'save'),
                           dataType:"html",
                           data:$('#edit_form').serialize(),
                           success:function(data){
                               result = jQuery.parseJSON(data);
                               if (result.code == 0) { // 成功保存
                                   //easy2go.toast('info', result.msg);
                                   //window.location.href="<%=basePath %>device/bindkey/index"; <%-- 应该刷新页面url --%>
                                   easy2go.toast('info', result.msg, false, null, {
                                       onClose: function() { window.location.href="<%=basePath %>device/bindkey/index"; }
                                   });
                               } else if(result.code == 10 || result.code == 11 /*|| result.code == -4*/) {
                            	   // 保存成功但发送短信失败
                            	   easy2go.toast('warn', result.msg, false, 7200000, {
                                       onClose: function() { window.location.href="<%=basePath %>device/bindkey/index"; }
                                   });
                               } else {
                                   easy2go.toast('error', result.msg);
                               }
                           }
                       });
                   }
               });
         });

         function activateMenu(op) {
             if(op == 'edit'){
                 $('#listKey').removeClass('active');
//                  $('#listRecord').removeClass('active');
                 $('#editKey').addClass('active');
                 $('#tabKey').removeClass('active');
//                  $('#tabRecord').removeClass('active');
                 $('#tabGenKey').addClass('active');
             } else if(op == 'record') {
                 if(!isRecordListInited){
                     gridObjRecord.refreshPage();
                     isRecordListInited = true;
                 }

                 $('#listKey').removeClass('active');
                 $('#editKey').removeClass('active');
//                  $('#listRecord').addClass('active');
                 $('#tabKey').removeClass('active');
                 $('#tabGenKey').removeClass('active');
//                  $('#tabRecord').addClass('active');
             } else {
                 if(!isKeyListInited){
                     if($("#pagenum").val()!=""){
                         gridObj.page($("#pagenum").val());
                     }else{
                         gridObj.page(1);
                     }
                     isKeyListInited = true;
                 }

                 $('#editKey').removeClass('active');
//                  $('#listRecord').removeClass('active');
                 $('#listKey').addClass('active');
                 $('#tabGenKey').removeClass('active');
//                  $('#tabRecord').removeClass('active');
                 $('#tabKey').addClass('active');
             }
         }

         function edit(index){
        	 var record= gridObj.getRecord(index);
        	 var tips;
        	 if(record.useStatus == '是'){
        		 tips = '这个绑定码已绑定，不可编辑'; // TODO: 亦或改为允许某些字段编辑如备注？
        		 easy2go.toast('info', tips);
        		 return false;
        	 }
        	 window.location.href = "<%=basePath%>device/bindkey/edit/" + record.keyId;
		}

         function disablebyid(index){
             var record= gridObj.getRecord(index);
             bootbox.confirm("确定禁用此绑定码吗?", function(result) {
                 if(result){
                     $.ajax({
                         type:"GET",
                         url:"<%=basePath%>device/bindkey/enable/" + record.keyId + '?enabled=0',
                        dataType : 'html',
                        success : function(data) {
                        	try{
                                result = jQuery.parseJSON(data);
                            } catch(e) {
                                result = {code:-1000}; // SessionFilter未登录
                            }
                            if (result.code == 0) {
                            	easy2go.toast('success', result.msg);
                            	if($("#pagenum").val()!=""){
                                    gridObj.page($("#pagenum").val());
                                }else{
                                    gridObj.page(1);
                                }
                             } else if (result.code == -1000) {
                                 window.location.href = data;
                             } else {
                                 easy2go.toast('error', result.msg);
                             }
                        }
                    });
                }
            });
        }
         function enablebyid(index){
             var record= gridObj.getRecord(index);
             bootbox.confirm("确定允许此绑定码可绑定吗?", function(result) {
                 if(result){
                     $.ajax({
                         type:"GET",
                         url:"<%=basePath%>device/bindkey/enable/" + record.keyId + '?enabled=1',
                        dataType : 'html',
                        success : function(data) {
                        	try{
                                result = jQuery.parseJSON(data);
                            } catch(e) {
                                result = {code:-1000}; // SessionFilter未登录
                            }
                            if (result.code == 0) {
                                easy2go.toast('success', result.msg);
                                if($("#pagenum").val()!=""){
                                    gridObj.page($("#pagenum").val());
                                }else{
                                    gridObj.page(1);
                                }
                             } else if (result.code == -1000) {
                                 window.location.href = data;
                             } else {
                                 easy2go.toast('error', result.msg);
                             }
                        }
                    });
                }
            });
        }
		function re() {
			//$("#order_ID").val('');
			//$("#order_customerName").val('');
			gridObj.options.otherParames = $('#searchForm').serializeArray();
			gridObj.refreshPage();
		}
//         function refreshRecord() {
//             gridObjRecord.options.otherParames = $('#searchFormRecord').serializeArray();
//             gridObjRecord.refreshPage();
//         }
		function getKey() {
			// 校验手机号码
			var value=$('#edit_form input[name="SN"]').val();//alert('3 value.substring(0,10) =' + value.substring(0,10))
			if(value == '' || !(value.length == 15 || value.length == 6)
			    /*|| (value.length == 15 && value.substring(0,10) != '1721502100')*/) {
			  easy2go.toast('error', '请输入正确设备机身码');
               $('input[name="SN"]').focus();
               return;
             }

	        if($('#edit_form input[name="keyId"]').val() != '') { // 若系修改的情况下，着重提醒一下情况
	            bootbox.confirm("确定更新这设备的绑定码吗？若此绑定码已发放到客户手上，请慎重考虑更改绑定码，否则之前的绑定码会失效。",
	            	function(result) {
		            	if(result){
		            		getKeyAjax(value);
		            	}
	            });
	        } else {
	        	getKeyAjax(value);
	        }

		}

		function getKeyAjax(value) {
            $.ajax({
                type:"GET",
                url:"<%=basePath%>device/bindkey/getKey/" + value,
               dataType : 'html',
               success : function(data) {
                   var result;
                   try{
                       result = jQuery.parseJSON(data);
                   } catch(e) {
                       result = {code:-1000}; // SessionFilter未登录
                   }

                   if (result.code == 0) {
                      $('#edit_form input[name="key"]').val(result.key);
                      $('#edit_form input[name="keyTimestamp"]').val(result.keyTimestamp);
                   } else if (result.code == -1000) {
                       window.location.href = data;
                   } else {
                       easy2go.toast('error', result.msg);
                   }
               }
           });
		}

        function isBeginBeforeEndDate() {// 保证开始时间在结束时间前
        	var beginDate = $("#query_startDate_key").val().replaceAll("-",""); //.substring(2,$("#query_startDate_key").val().length);
            var endDate = $("#query_endDate_key").val().replaceAll("-",""); //.substring(2,$("#query_endDate_key").val().length);
            if(beginDate > endDate){
            	return false;
            }
            return true;
        }

	</SCRIPT>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>