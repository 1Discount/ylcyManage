<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>特殊订单管理-设备管理-EASY2GO ADMIN</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
  content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet"
  href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet"
  href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
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
        <header class="panel-heading tab-bg-dark-navy-blue">
            <ul class="nav nav-tabs nav-justified ">
                <li class="main-menu-tab" id="tabMenuOne"><a data-toggle="tab" href="#tabList">特殊订单列表</a></li>
                <li class="main-menu-tab" id="tabMenuTwo"><a data-toggle="tab" href="#tabCreate">特殊订单创建</a></li>
            </ul>
        </header>

        <div class="panel-body">
        <div class="tab-content">
          <div id="tabList" class="tab-pane">
          <div class="panel">
            <div class="panel-body">
              <form class="form-inline" id="searchForm" role="form"
                method="get" action="#">
                <input type="hidden" value="1" id="pagenum" /> <input
                  type="hidden" id="pagesize" value="15" />
                <div class="form-group">
                  <label class="inline-label">特殊订单ID：</label> <select
                    id="query_orderID" name="orderID"
                    class="form-control">
                    <option value="10087">10087-北京银行</option>
                    <option value="10086">10086-设备测试单</option>
                    <option value="special">10086-10999-全部</option>
                  </select>
                </div>
                <div class="form-group">
                  <LABEL class="inline-label">订单状态：</LABEL> <select
                    id="slect_mg" name="orderStatus"
                    class="form-control">
                    <option value="">所有</option>
                    <c:forEach var="dic" items="${OrderStatusDict}">
                      <option value="${dic.label }">${dic.label }</option>
                    </c:forEach>
                  </select>
                </div>
                <div class="form-group">
                  <LABEL class="inline-label">机身码：</LABEL><INPUT
                    class="form-control" id="SN" name="SN" type="text"
                    placeholder="设备机身码">
                </div>
                <br />
                <div class="form-group">
                  <LABEL class="inline-label">时间段：</LABEL> <INPUT
                    id="order_creatorDatebegin"
                    class="form-control form_datetime"
                    name="begindateForQuery" type="text"
                    data-date-format="YYYY-MM-DD HH:mm:ss">
                </div>

                <div class="form-group">
                  <LABEL class="inline-label">到</LABEL> <INPUT
                    id="order_creatorDateend"
                    class="form-control form_datetime" name="enddate"
                    type="text" data-date-format="YYYY-MM-DD HH:mm:ss">
                </div>

                <div class="form-group">
                  <BUTTON class="btn btn-primary" type="button"
                    onclick="gridObj.options.otherParames =$('#searchForm').serializeArray();gridObj.page(1);">搜索</BUTTON>
                  <BUTTON class="btn btn-primary" onclick="re();"
                    type="button">刷新</BUTTON>
                </div>
              </form>
            </div>
          </div>
          <div class="panel">
            <div class="panel-heading">
              <H3 class="panel-title">所有特殊订单</H3>
            </div>
            <div class="panel-body">
              <div class="table-responsive"><table id="searchTable">
                <tr>
                  <th w_render="orderidOP" width="10%;">交易ID</th>
                  <th w_index="orderID">总ID</th>
                  <th w_index="SN" width="10%;">设备机身码</th>
                  <th w_render="render_bindStatus">是否绑定</th>
                  <th w_index="customerName" width="10%;">客户</th>
                  <th w_index="userCountry" width="10%;">国家</th>
                  <!-- <th w_index="orderSource" width="10%;">订单来源</th> -->
                  <th w_index="orderAmount" width="5%;">总金额</th>
                  <th w_index="flowDays" width="3%;">天数</th>
                  <th w_index="ifActivate" width="3%;">是否激活</th>
<!--                   <th w_index="panlUserDate" width="10%;">预约开始时间</th> -->
                  <th w_render="render_beginDate" width="10%;">开始可用时间</th><!-- 事实系 panlUserDate -->
                  <th w_render="render_flowExpireDate" width="10%;">到期时间</th>
                  <th w_index="lastUpdateDate" width="10%;">上次接入时间</th>
                  <th w_index="orderStatus" width="5%;">订单状态</th>
                  <th w_index="creatorUserName" width="6%;">创建人</th>
                  <th w_index="creatorDate" width="10%;">创建时间</th>

                  <th w_render="operate" width="30%;">操作</th>
                </tr>
              </table></div>


            </div>
          </div>
          <!-- 				<div class="panel"> -->
          <!-- 				<div class="paomadeng"> -->
          <!-- 								<span style="font-size: 14px; font-weight: bolder;" onclick="return execl()" > -->
          <%-- 								<a id="a" href="<%=basePath %>orders/flowdealorders/flowexportexecl?" --%>
          <%-- 									><img src="<%=basePath%>static/images/excel.jpg" --%>
          <!-- 										style="float: left; margin-top: -5px;"  width="30" height="30" -->
          <!-- 										title="" /> 导出EXCEL请点我</a></span> -->
          <!-- 							</div> -->

        </div>
        <div id="tabCreate" class="tab-pane">
            <div class="panel" style="margin-bottom:0;">
            <div class="panel-heading">
              <H3 class="panel-title">第一步: 先导入设备机身码 Excel 档</H3>
            </div>
<!--             <h5>第一步: 先导入设备机身码 Excel 档</h5> -->
            <div class="panel-body">
                <form id="uploadForm" action="<%=basePath%>device/special/upload" method="post" enctype="multipart/form-data">
                <p>导入 机身码 的 Excel 模板文档与批量发货使用的模板系同一个。注意只有该设备正常“可使用”才能成功创建相应订单。</p>
                <div class="form-group">
                        <label for="upload_orderID" class="col-md-2 control-label">特殊总订单ID:</label>
                        <div class="col-md-6">
                            <INPUT id="upload_orderID" value="${floworder.orderID}" class="form-control" name="orderID" type="text" >
                        </div>
                        <div class="col-md-3">
                        <p class="red">* 目前特殊订单有：10087为北京银行100台设备; 10086为出厂测试的测试单ID，流量订单查询界面会屏蔽掉这两种类型的订单</p>
                        </div>
                    </div>
                <div class="form-group">
                  <div class="col-sm-2">
                  <label style="display: inline-block; float: left; line-height: 34px; clear: left; max-width: 100%; font-size: 14px; font-weight: 700; margin-bottom: 5px; max-width: 100%;">
                                                      导入 机身码 列表：</label></div>
                  <div class="col-sm-10">
                    <input type="file" name="file" id="file" data-popover-offset="0,8" required
                      class="form-control">
                  </div>
                </div>
                <div class="form-group">
                <div class="col-md-6">
                <button type="button" class="btn btn-primary" style="margin-top:10px;margin-bottom:10px;"
                                onclick="uploadForCheck();">上传</button>
                </div></div>
                </form>
                <div class="col-sm-12">
                <div class="well" style="margin-bottom:0;">
                <span style="border:0px solid pink; font-size:14px; color:red;">
                ${excelmessage}</span><br />
                <span id="snList" style="border:0px solid pink; font-size:14px; color:red;">
                ${validSnString}</span>
                </div>
                </div>

              </div>
            </div>

            <div class="panel">
            <div class="panel-heading">
              <H3 class="panel-title">第二步: 配置对应的流量订单内容 <span style="font-size:12px;">(目前默认为北京银行那批订单对应值, 以后改为更通用的默认值)</span></H3>
            </div>
            <div class="panel-body">
                <form id="deal_form" role="form" action="" method="post" autocomplete="off" class="form-horizontal">
<!--                     <input type="hidden" name="flowDealID" -->
<%--                         value="${floworder.flowDealID }" /> --%>
                    <input type="hidden" name="uploadFileName" value="${uploadFileName}" />
                    <div class="form-group">
                        <label for="deal_orderID" class="col-md-2 control-label">特殊总订单ID:</label>
                        <div class="col-md-6">
                            <INPUT readonly="readonly" id="deal_orderID" value="${floworder.orderID}" class="form-control" name="orderID" type="text" >
                        </div>
                        <div class="col-md-3">
                        <p class="red">* 目前特殊订单有：10087为北京银行100台设备; 10086为出厂测试的测试单ID，流量订单查询界面会屏蔽掉这两种类型的订单</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="deal_customerName" class="col-md-2 control-label">客户名称:</label>
                        <div class="col-md-6">
                            <input id="deal_customerName" type="text" name="customerName"
                                 value="${floworder.customerName}"
                                data-popover-offset="0,8" required class="form-control">
                        </div>
                        <div class="col-md-3">
                        <p class="red">注意, 客户名称前面暂统一设置为一个名称作标记 , 后面流量订单正式消费时会设置为实际客户的手机号等</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="deal_country" class="col-md-2 control-label">可用国家:</label>
                        <div class="col-md-10">
                            <c:forEach items="${countrys }" var="cs">
                                <LABEL class="checkbox-items"> <c:if
                                        test="${fn:contains(floworder.userCountry,cs.countryName)}">
                                        <INPUT class="countryitem" name="userCountry"
                                            value="${cs.countryName }-${cs.countryCode}-${cs.flowPrice }"
                                            type="checkbox" checked="checked"
                                            data-price="${cs.flowPrice }">${cs.countryName }（${cs.flowPrice }/天）
</c:if> <c:if test="${!fn:contains(floworder.userCountry,cs.countryName)}">
                                        <INPUT class="countryitem" name="userCountry"
                                            value="${cs.countryName }-${cs.countryCode}-${cs.flowPrice }"
                                            type="checkbox" data-price="${cs.flowPrice }">${cs.countryName }（${cs.flowPrice }/天）
</c:if>
                                </LABEL>
                            </c:forEach>
                        </div>
                    </div>
<!--                     <div class="form-group"> -->
<!--                         <label for="country_code" class="col-md-2 control-label">预约使用时间:</label> -->
<!--                         <div class="col-md-6"> -->
<!--                             <INPUT id="forder_panlUserDate" onchange="timefor()" -->
<!--                                 class="form-control form_datetime" name="panlUserDate" type="text" -->
<!--                                 data-date-format="YYYY-MM-DD HH:mm:ss"> -->
<!--                         </div> -->
<!--                     </div> -->
<!--                     <div class="form-group"> -->
<!--                         <label for="country_code" class="col-md-2 control-label">到期时间:</label> -->
<!--                         <div class="col-md-6"> -->
<!--                             <input readonly="readonly" id="deal_flowExpireDate" type="text" name="flowExpireDate" -->
<%--                                  value="${floworder.flowExpireDate }" --%>
<!--                                 data-popover-offset="0,8" required class="form-control"> -->
<!--                         </div> -->
<!--                     </div> -->
                        <div class="form-group">
                          <label for="deal_orderType" class="col-md-2 control-label">计费模式:</label>
                          <div class="col-md-6">
                          <select
                            id="deal_orderType" name="orderType"
                            class="form-control"><%-- FLOWDEALTYPE_1 2 3 4 --%>
                            <c:if test="${floworder.orderType eq '1'}"><option value="1" selected>1按时间不限流量</option></c:if>
                            <c:if test="${floworder.orderType ne '1'}"><option value="1">1按时间不限流量</option></c:if>
                            <c:if test="${floworder.orderType eq '2'}"><option value="2" selected>2按时间限流量</option></c:if>
                            <c:if test="${floworder.orderType ne '2'}"><option value="2">2按时间限流量</option></c:if>
                            <c:if test="${floworder.orderType eq '3'}"><option value="3" selected>3按实际使用天数</option></c:if>
                            <c:if test="${floworder.orderType ne '3'}"><option value="3">3按实际使用天数</option></c:if>
                            <c:if test="${floworder.orderType eq '4'}"><option value="4" selected>4按开机时间连续</option></c:if>
                            <c:if test="${floworder.orderType ne '4'}"><option value="4">4按开机时间连续</option></c:if>
                          </select>
                          </div>
                          <div class="col-md-3">
                          <p class="red">* 10087特殊订单默认为 4按开机时间连续 的情形</p>
                          </div>
                        </div>
                        <div class="form-group">
                        <label for="deal_days" class="col-md-2 control-label">可用天数:</label>
                        <div class="col-md-6">
                            <input id="deal_days" onchange="timefor()" type="number" name="flowDays"
                                min="1" max="365" value="${floworder.flowDays }"
                                data-popover-offset="0,8" required class="form-control">
                        </div>
                        <div class="col-md-3">
                        <p class="red">*</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="deal_beginDate" class="col-md-2 control-label">开始可用时间:</label>
                        <div class="col-md-6"><!-- 勿错用 beginDate -->
                            <input id="deal_beginDate" type="text" name="panlUserDate"
                                 value=""
                                data-popover-offset="0,8" required class="form-control">
                        </div>
                        <div class="col-md-3">
                        <p class="red">* <!-- 注意, 这个对应流量订单中的开始使用时间,特殊订单前期临时使用 --></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="deal_flowExpireDate" class="col-md-2 control-label">最晚可用时间:</label>
                        <div class="col-md-6">
                            <input id="deal_flowExpireDate" type="text" name="flowExpireDate"
                                 value=""
                                data-popover-offset="0,8" required class="form-control"><!-- ${floworder.flowExpireDate } -->
                        </div>
                        <div class="col-md-3">
                        <p class="red">* 北京银行这批请设置为开始可用时间之后半年时间<!-- 注意, 这个对应流量订单中的过期时间: flowExpireDate --></p>
                        </div>
                    </div>
<!--                     <div class="form-group"> -->
<!--                         <label for="deal_amount" class="col-md-2 control-label">金额:</label> -->
<!--                         <div class="col-md-6"> -->
<!--                             <input id="deal_amount" type="number" name="orderAmount" -->
<%--                                 value="${floworder.orderAmount }" min="1" --%>
<!--                                 data-popover-offset="0,8" required class="form-control"> -->
<!--                         </div> -->
<!--                     </div> -->
                    <div class="form-group">
                        <label for="deal_desc" class="col-md-2 control-label">备注:</label>
                        <div class="col-md-6">
                            <textarea id="deal_desc" rows="3" name="remark"
                                data-popover-offset="0,8" class="form-control">${floworder.remark }</textarea>
                        </div>
                    </div>
<!--                     <div class="form-group"> -->
<!--                         <label for="deal_desc" class="col-md-2 control-label">行程:</label> -->
<!--                         <div class="col-md-6"> -->
<%--                             <INPUT id="deal_journey" value="${floworder.journey }" class="form-control" name="journey" type="text" > --%>
<!--                             <span style="color:red;">例如(可复制修改): 香港（151016-151018，151020）|台湾（151021-151024）</span> -->
<!--                         </div> -->
<!--                     </div> -->
<!--                     <div class="form-group"> -->
<!--                         <label for="deal_desc" class="col-md-2 control-label">限速策略:</label> -->
<!--                         <div class="col-md-6"> -->
<%--                             <INPUT id="deal_journey" value="${floworder.speedStr }" class="form-control" name="speedStr" type="text" >例如：0-2000|50-150|150-24|200-16|250-8 --%>
<!--                         </div> -->
<!--                     </div> -->

                    <div class="form-group">
                        <label for="deal_limitValve" class="col-md-2 control-label">限速阀值:</label>
                        <div class="col-md-6">
                            <INPUT id="deal_limitValve" value="${floworder.limitValve }" class="form-control" name="limitValve" type="number" >
                            一般默认200M
                        </div>
                        <div class="col-md-3">
                        <p class="red"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="deal_limitSpeed" class="col-md-2 control-label">限速大小:</label>
                        <div class="col-md-6">
                            <INPUT id="deal_limitSpeed" value="${floworder.limitSpeed }" class="form-control" name="limitSpeed" type="number" >
                            一般默认150Kb
                        </div>
                        <div class="col-md-3">
                        <p class="red"></p>
                        </div>
                    </div>
<!--                     <div class="form-group"> -->
<!--                       <label for="deal_deviceOrderStatus" class="col-md-2 control-label">设备使用类型:</label> -->
<!--                       <div class="col-md-6"> -->
<!--                       <select -->
<!--                         id="deal_deviceOrderStatus" name="deviceOrderStatus" -->
<!--                         class="form-control"> -->
<!--                         <option value="租赁">租赁</option> -->
<!--                         <option value="购买">购买</option> -->
<!--                       </select> -->
<!--                       </div> -->
<!--                       <div class="col-md-3"> -->
<!--                       <p class="red">* 待用</p> -->
<!--                       </div> -->
<!--                     </div> -->
                    <div class="form-group">
                        <div class="col-md-6 col-md-offset-3">
                            <div class="btn-toolbar">
                                <button type="button"  onclick="saveSpecialCreate();"
                                    class="btn btn-primary">保存</button>
                                <button type="button" onclick="javascript:history.go(-1);"
                                    class="btn btn-default">返回</button>
                            </div>
                        </div>
                    </div>
                </form>

              </div>
            </div>

        </div>

        </div>
        </div>
        </div><!-- tab -->

        </div>
        </div>
      </section>
    </section>
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
  <SCRIPT type="text/javascript">
  var active="${active}"; // 初始打开列表 tabList 还是创建页 tabCreate
  if(active == '' || !(active == 'tabList' || active == 'tabCreate')){
	  active = 'tabList';
  }
  var isListInitial = false; // 判断是否系延时加载列表页数据,以带来一点优化.当初始化页为创建页时,延迟加载列表页数据

    $(function(){
    	var SN = "${SN}";
    	if(SN!=""){
    		  $("#SN").val(SN);
    	}

    	$("#deal_flowExpireDate").datetimepicker({
    		format: 'YYYY-MM-DD', //'YYYY-MM-DD HH:mm:ss',
            pickDate: true,                 //en/disables the date picker
            //pickTime: true,                 //en/disables the time picker
            showToday: true,                 //shows the today indicator
            language:'zh-CN',                  //sets language locale
            //手动输入//defaultDate:'${floworder.flowExpireDate }',                 //sets a default date, accepts js dates, strings and moment objects
        });
    	$("#deal_beginDate").datetimepicker({
            format: 'YYYY-MM-DD', //'YYYY-MM-DD HH:mm:ss',
            pickDate: true,                 //en/disables the date picker
            //pickTime: true,                 //en/disables the time picker
            showToday: true,                 //shows the today indicator
            language:'zh-CN',                  //sets language locale
            //手动输入//defaultDate:'${floworder.flowExpireDate }',                 //sets a default date, accepts js dates, strings and moment objects
        });
    });
    function execl(){
    	if($("#userCountry").val()==''){
    		easy2go.toast('warn','只能导出单个国家的数据');
    		return false;
    	}else{
    		var par = $('#searchForm').serialize();
			$("#a").attr('href','<%=basePath %>orders/flowdealorders/flowexportexecl?'+par);
    	}
    }
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

             var pagesize=parseInt($("#pagesize").val());
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath%>orders/flowdealorders/getpage', //后面刷新时已引用到 ?orderID=' + $('#query_orderID').val(),
                 pageSizeSelect: true,
                 pageSize:pagesize,
                 autoLoad:false,
                 otherParames:$('#searchForm').serializeArray(),
                 pageSizeForGrid:[15,30,50,100],
                 displayPagingToolbarOnlyMultiPages:true,
                 additionalAfterRenderGrid: function(parseSuccess, gridData, options) {
                	 if(parseSuccess){
                		 $("#pagenum").val(options.curPage);
                		 var a = $("#searchTable_pt_pageSize").val();
                		 $("#pagesize").val(a);
                	 }
                 }
             });

             if('tabList' == active){ // 列表页
                 if($("#pagenum").val()!=""){
                     gridObj.page($("#pagenum").val());
                 }else{
                     gridObj.page(1);
                 };
                 isListInitial = true;

                 $('#tabMenuOne').addClass('active');
                 $('#tabList').addClass('active');
             } else { // 创建页
                 // 这时延迟加载列表页
            	 isListInitial = false;

                 //$('#tabMenuTwo').click();alert("active=" + active + " tabMenuTwo click");
                 $('#tabMenuOne').removeClass('active');
                 $('#tabMenuTwo').addClass('active');
                 $('#tabList').removeClass('active');
                 $('#tabCreate').addClass('active');
             }

	           //gridObj.options.otherParames =$('#searchForm').serializeArray();

	         $('#tabMenuOne').click(function(){
	        	 if(!isListInitial){
	        		 if($("#pagenum").val()!=""){
	                     gridObj.page($("#pagenum").val());
	                 }else{
	                     gridObj.page(1);
	                 };
	        	 }
	         });
         });


         //自定义列.
         function operate(record, rowIndex, colIndex, options) {
             //'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>';
              if(record.ifActivate=='否' || record.ifActivate==''){
            	  return '<A class="btn btn-primary btn-xs" onclick="toactivate('+rowIndex+')" ><SPAN class="glyphicon glyphicon-brightness-reduce">激活</SPAN></A>&nbsp;&nbsp;<A class="btn btn-primary btn-xs"   href="<%=basePath%>orders/flowdealorders/edit?flowDealID='+record.flowDealID+'"><SPAN class="glyphicon glyphicon-edit">编辑</SPAN></A>&nbsp;&nbsp;<A class="btn btn-danger  btn-xs"  onclick="deletebyid('+rowIndex+')"><SPAN class="glyphicon glyphicon-remove">删除</SPAN></A>';
              }else if(record.orderStatus=='使用中'){
            	  return '<A class="btn btn-primary btn-xs" onclick="activate('+rowIndex+')" ><SPAN class="glyphicon glyphicon-stop">暂停服务</SPAN></A>&nbsp;&nbsp;<A class="btn btn-primary btn-xs"   href="<%=basePath%>orders/flowdealorders/edit?flowDealID='+record.flowDealID+'"><SPAN class="glyphicon glyphicon-edit">编辑</SPAN></A>&nbsp;&nbsp;';
              }else if(record.orderStatus=='已暂停'){
            	  return '<A class="btn btn-primary btn-xs" onclick="activate('+rowIndex+')" ><SPAN class="glyphicon glyphicon-play">启动服务</SPAN></A>&nbsp;&nbsp;<A class="btn btn-primary btn-xs"   href="<%=basePath%>orders/flowdealorders/edit?flowDealID='+record.flowDealID+'"><SPAN class="glyphicon glyphicon-edit">编辑</SPAN></A>&nbsp;&nbsp;<A class="btn btn-danger  btn-xs"  onclick="deletebyid('+rowIndex+')"><SPAN class="glyphicon glyphicon-remove">删除</SPAN></A>';
              }else if(record.orderStatus=='可使用'){
              <%-- <A class="btn btn-primary btn-xs"   href="<%=basePath %>/device/deviceDealUpdate?flowDealID='+record.flowDealID+'"><SPAN class="glyphicon glyphicon-edit">更换设备</SPAN></A> --%>
            	  return '<a class="btn btn-primary btn-xs"  onclick="updateSN('+rowIndex+')"><SPAN class="glyphicon glyphicon-edit">更换设备</SPAN></a>&nbsp;&nbsp;<A class="btn btn-primary btn-xs"   href="<%=basePath%>orders/flowdealorders/edit?flowDealID='+record.flowDealID+'"><SPAN class="glyphicon glyphicon-edit">编辑</SPAN></A>&nbsp;&nbsp;<A class="btn btn-danger  btn-xs"  onclick="deletebyid('+rowIndex+')"><SPAN class="glyphicon glyphicon-remove">删除</SPAN></A>';
              }else if(record.orderStatus=='不可用' &&  record.ifActivate=='是'){
            	  return '<A class="btn btn-danger btn-xs"  onclick="deletebyid('+rowIndex+')"><SPAN class="glyphicon glyphicon-remove">删除</SPAN></A>';
              }
             /*  <A class="btn btn-primary btn-xs"  onclick="deletebyid('+rowIndex+')"><SPAN class="glyphicon glyphicon-remove">删除</SPAN></A> */
         }

         function render_beginDate(record, rowIndex, colIndex, options) {
             return record.panlUserDate.substring(0,10);
         }
         function render_flowExpireDate(record, rowIndex, colIndex, options) {
             return record.flowExpireDate.substring(0,10);
         }
         function render_bindStatus(record, rowIndex, colIndex, options) {
             var customerID = record.customerID;
             if(customerID.length == 5) {
            	 var parse = parseInt(customerID);
            	 if(parse >= 10087 && parse <= 10999) {
            		 return "";
            	 } else {
            		 return '<a class="btn btn-info btn-xs">已绑定</a>';
            	 }
             } else {
            	 return '<a class="btn btn-info btn-xs">已绑定</a>';
             }
         }
         function orderidOP(record, rowIndex, colIndex, options) {
        	 return '<a title="'+record.flowDealID+'" href="<%=basePath%>orders/flowdealorders/info?flowDealID='+record.flowDealID+'">详情</a>';
         }

         //根据ID删除
         function deletebyid(index){
        	 var record= gridObj.getRecord(index);
        	 bootbox.confirm("确定删除吗?", function(result) {
        		 if(result){
        			 $.ajax({
                		 type:"POST",
                		 url:"<%=basePath%>orders/ordersinfo/delfloworder?flowDealID="+record.flowDealID+"&orderAmount="+record.orderAmount,
                		 dataType:'html',
                		 success:function(data){
                            if(data=="0"){
            					easy2go.toast('warn',"删除失败");
            				}else if(data=="-1"){
            					easy2go.toast('warn',"参数为空");
            				}else if(data=="-5"){
	                            easy2go.toast('err',"请重新登录!");
	                        } else { // 这种else为成功应该需要改善
                                easy2go.toast('info',"删除成功");
                                gridObj.refreshPage();
	                        }
                		 }
                	 });
        		 }
        	 });
         }


         //刷新
        function re(){
        	$("#order_ID").val('');
        	$("#order_customerName").val('');
        	gridObj.options.otherParames =$('#searchForm').serializeArray();
        	gridObj.refreshPage();
        }

       //激活，暂停,启动
       function  activate(index){
    	   var record= gridObj.getRecord(index);
    	   var urlpath="";
    	   var OP="";
      	 if(record.orderStatus=='可使用' || record.orderStatus=='使用中'){
      		OP='暂停服务';
      		urlpath='<%=basePath%>orders/flowdealorders/pause?flowOrderID='+record.flowDealID;
      	 }else if(record.orderStatus=='已暂停'){
      		OP='启动服务';
      		urlpath='<%=basePath%>orders/flowdealorders/launch?flowOrderID='+record.flowDealID;
      	 }
      	 bootbox.confirm("确定要"+OP+"吗?", function(result) {
      		 if(result){
      			 $.ajax({
              		 type:"POST",
              		 url:urlpath,
              		 dataType:'html',
              		 success:function(data){
                        if(data=="0"){
          					easy2go.toast('warn',OP+"失败");
          				}else if(data=="-1"){
          					easy2go.toast('warn',"参数为空");
          				}else if(data=="-5"){
	                        easy2go.toast('err',"请重新登录!");
	                    } else if(data=="1"){ // 这种else为成功应该需要改善
                            easy2go.toast('info',OP+"成功");
                            gridObj.refreshPage();
	                    }
              		 }
              	 });
      		 }
      	 });
       }

       //跳转到订单激活界面
       function  toactivate(index){
    	   var record= gridObj.getRecord(index);
    	   window.location="<%=basePath%>orders/ordersinfo/activate?orderID="+record.customerID+"&flowOrderID="+record.flowDealID;
       }
       function updateSN(index){
    	   //拿到这一行的c
    	   	 var record= gridObj.getRecord(index);
    	   	 bootbox.confirm("确定更换吗?", function(result) {
	   	   		 if(result){
	   						 window.location.href="<%=basePath%>orders/flowdealorders/updateSN?flowDealID="+record.flowDealID+"&oldSN="+record.SN;
	   				}
	   		});
    	}

       function uploadForCheck(){
    	   var orderId = $("#upload_orderID").val();
    	   if(orderId == ''){
    		   $("#upload_orderID").focus();
               easy2go.toast('error',"请先输入特殊设备的总订单ID");
    		   return false;
    	   }

           var file = $("#file").val();
           if(file!=""){
        	    $("#deal_orderID").val(orderId);
        	    $("#uploadForm").submit();
        	    return;
           } else {
            easy2go.toast('warn',"请先选择设备机身码列表 Excel 档");
           }
       }

       function saveSpecialCreate(){
    	   var orderId = $("#upload_orderID").val();
    	   var orderIdCheck = $("#deal_orderID").val();
    	   var uploadFileName = $("input[name='uploadFileName']").val();
    	   if(uploadFileName=='' || orderId=='' || orderIdCheck=='' || orderId!=orderIdCheck){
    		   $("#upload_orderID").focus();
    		   easy2go.toast('error',"请先上传设备机身码列表, 确保全部机身码有效");
    		   return false;
    	   }
    	   if(0 == $("input[name='userCountry']:checked").length){
    		   easy2go.toast('info',"请选择可用国家");
    		   $("input[name='userCountry']").focus();
               return false;
    	   }
    	   if($("input[name='flowDays']").val() == ''){
    		   easy2go.toast('info',"请输入可用天数");
    		   $("input[name='flowDays']").focus();
    		   return false;
    	   }
    	   if($("input[name='panlUserDate']").val() == ''){
               easy2go.toast('info',"请输入开始可用时间");
               $("input[name='panlUserDate']").focus();
               return false;
           }
    	   if($("input[name='flowExpireDate']").val() == ''){
               easy2go.toast('info',"请输入最晚可用时间");
               $("input[name='flowExpireDate']").focus();
               return false;
           }
           $.ajax({
               type : "POST",
               url : "<%=basePath%>device/special/create",
               dataType : "html",
               data : $('#deal_form').serialize(),
               success : function(data) {
                   //msg = data;
                   //data = parseInt(data);
                   try{
                        result = jQuery.parseJSON(data);
                    } catch(e) {
                        result = {code:-1000,error:"unkown"};
                    }
                   if (result.code == 0) {
                       bootbox.alert(result.msg,function(){ // 成功创建这批设备的订单
                           window.location="<%=basePath%>device/special";
                       });
                   } else {
                       easy2go.toast('error', result.msg);
                   }
                 },
                error : function() {
                	easy2go.toast('error', "请检查网络!");
                }
           });
       }
</SCRIPT>
  <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
  <jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
    <jsp:param name="matchType" value="exactly" />
  </jsp:include>
</body>
</html>