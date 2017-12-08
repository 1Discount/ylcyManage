<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>
  <head>
    <title><c:if test="${IsIndexView}">全部漫游SIM卡-漫游卡管理-EASY2GO ADMIN</c:if><c:if test="${IsTrashView}">全部已删除SIM卡-漫游卡管理-EASY2GO ADMIN</c:if></title>
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
		<div class="col-md-12">
<c:if test="${IsIndexView}"><%-- 只首页显示上部大按钮 --%>
<%--            <div class="row">--%>
<%--            <div class="col-md-3">--%>
<%--                  <div class="mini-stat clearfix"><a href="<%=basePath %>sim/roamingsiminfo/statistics"><span class="mini-stat-icon pink"><i class="fa fa-bar-chart-o"></i></span>--%>
<%--                      <div class="mini-stat-info"><span style="font-size: 20px;">漫游SIM卡状态统计</span></div></a></div>--%>
<%--            </div>--%>
<%--            <div class="col-md-3">--%>
<%--              <div class="mini-stat clearfix"><a href="<%=basePath %>sim/roamingsiminfo/index/alert"><span class="mini-stat-icon orange"><i class="fa fa-th-list"></i></span>--%>
<%--                  <div class="mini-stat-info"><span style="font-size: 20px;">漫游SIM卡充值预警列表</span></div></a></div>--%>
<%--            </div>--%>
<%--            </div>--%>
</c:if>
		<div class="panel">
		<div class="panel-body">
		<form class="form-inline" id="searchForm" role="form" method="get" action="#">
        <input type="hidden" id="pagenum" value="1" />
        <input type="hidden" id="pagesize" value="30" />

<%--		<div class="form-group">--%>
<%--          <label class="inline-label">IMSI：</label>--%>
<%--          <input type="text" name="IMSI" style="width: 173px;" placeholder="IMSI" class="form-control">--%>
<%--        </div>--%>
        <div class="form-group">
          <label class="inline-label">卡编号：</label>
          <input type="text" name="simAlias" style="width: 173px;" placeholder="" class="form-control">
        </div>
        <div class="form-group">
          <label class="inline-label">设备号：</label>
          <input type="text" name="lastDeviceSN" style="width: 173px;" placeholder="设备序列号" class="form-control" value="${SN}">
        </div>
        <div class="form-group">
          <label class="inline-label">ICCID：</label>
          <input type="text" name="ICCID" placeholder="ICCID" style="width: 173px;" class="form-control">
        </div>
<%--        <div class="form-group">--%>
<%--          <label class="inline-label">服务器：</label>--%>
<%--          <select name="serverIP" style="width: 135px;" class="form-control">--%>
<%--            <option value="">全部服务器</option>--%>
<%--            <c:forEach items="${SIMServers}" var="server" varStatus="status"><option value="${server.IP}">${server.IP}</option></c:forEach>--%>
<%--          </select>--%>
<%--        </div>--%>
<%--        <div class="form-group">--%>
<%--          <label class="inline-label">国&nbsp;家：</label>--%>
<%--          <select name="MCC" style="width: 130px;"--%>
<%--          class="form-control">--%>
<%--            <option value="">全部国家</option>--%>
<%--            <c:forEach items="${Countries}" var="country" varStatus="status"><option value="${country.countryCode}">${country.countryName}</option></c:forEach>--%>
<%--          </select>--%>
<%--        </div>--%>
        <div class="form-group">
          <label class="inline-label">状&nbsp;态：</label>
          <select name="cardStatus" style="width: 130px;" class="form-control">
            <option value="">全部状态</option>
    <c:forEach items="${CardStatusDict}" var="item" varStatus="status">
<%--     <c:if test="${item.label ne '不可用'}"> --%>
    <option value="${item.label}">${item.label}</option>
<%--     </c:if> --%>
    </c:forEach>
          </select>
        </div><br />
		<div class="form-group"><%--改为type=submit实现回车可提交搜索表单，注意submit函数中总是返回false--%>
		<button class="btn btn-primary" style="width: 130px;"
		type="submit" onclick="">搜索</button><button type="reset" class="btn btn-default">重置</button></div></form></div></div>

        </div>

<div class="col-md-12">
		<div class="panel">
		<div class="panel-heading">
<c:if test="${IsIndexView}"><div class="btn-toolbar btn-header-right"><a class="btn btn-success btn-xs" href="<%=basePath %>sim/roamingsiminfo/new"><span class="glyphicon glyphicon-plus"></span>添加SIM卡</a></div></c:if>
		<h3 class="panel-title"><c:if test="${IsIndexView}">全部漫游SIM卡</c:if><c:if test="${IsTrashView}">全部已删除漫游SIM卡</c:if></h3></div>
		<div class="panel-body">
		<div class="table-responsive">
		<table id="searchTable">
		        <tr>
		            <th w_index="SIMinfoID" w_hidden="true">SIM卡ID</th>
		            <th w_index="lastDeviceSN">相关设备号</th>
		            <th w_index="simAlias">卡编号</th>
		            <th w_index="phone">手机号</th>
		            <th w_index="PUK">PUK</th>
                    <th w_index="PIN">PIN</th>
		        	<th w_render="render_ICCID" width="10%;">ICCID</th>
                    <th w_index="planEndDate">购卡时间</th><%-- 目前使用SIM卡激活时间去保存为购卡时间 > 改用planEndDate --%>
                    <th w_index="SIMActivateDate">激活时间</th>
                    <th w_render="render_cardBalance">卡内余额</th>
                    <th w_render="render_cardStatus" width="5%;">状态</th> <!-- w_sort="userID,desc" -->
		            <th w_index="trademark">运营商</th>
		            <th w_index="remark">备注</th>

<!--                     <th w_index="MCC">国家</th> -->
<%--                    <th w_index="countryName">国家</th>--%>
<%--                    <th w_index="IMSI">IMSI号</th>--%>
<%--		            <th w_index="serverIP" width="8%;">服务器</th>--%>
<%--                    <th w_index="lastDeviceSN">最近设备SN</th>--%>
<%-- 		            <th w_render="render_speedLimit" width="5%;">限速</th>--%>
<%-- 		            <th w_render="render_planData" width="5%;">包含流量</th>--%>
<%-- 		            <th w_render="render_planRemainData" width="5%;">剩余流量</th>--%>
<%--                    <th w_index="cardBalance">余额</th>--%>
<%-- 		            <th w_index="planEndDate" width="10%;">到期日期</th>--%>

		            <th w_render="operate">操作</th>
		        </tr>
		</table></div>

        <c:if test="${IsIndexView}">

            <DIV class="panel" style="margin-top:20px;">
                        <DIV class="panel-body">
                        <label class="checkbox-items" style="padding-right:10px;"><INPUT name="exportRange" value="1" id="exportRangeAll" checked type="radio">导出全部搜索结果(默认即为全部漫游卡)</label>
                        <label class="checkbox-items" style="padding-right:10px;"><INPUT name="exportRange" value="0" id="exportRangeCurPage" type="radio" >导出当前页</label>
                        <br /><button id="exportExcelButton"><a href="javascript:void(0);">导出到 Excel</a></button><br /><br />
                        <p>导出后，如果要修改下列字段：关联设备SN，ICCID，电话号码，PUK，PIN等几个，那开始修改前手动把
                        该列的“单元格格式”设定数字分类为“文本”。</p>
                        </DIV>
                    </DIV>

<!--             <DIV class="panel-body"> -->
<!--                 <a onclick="excelExport()" id="exprot" href="#"> -->
<%--                     <img src="<%=basePath %>static/images/excel.jpg" width="30" height="30" />导出EXCEL请点我<span style="color:red;"> --%>
<!--                     </span> -->
<!--                 </a> -->
<!--             </DIV> -->
        </c:if>

		</div></div>
<div id="special-note">备注：<br><br>
<p>1. 开发者提示：“购卡时间”目前使用的系卡激活时间的字段。</p>
<p>2. “包含流量”和“剩余流量”的精确数据表字段数值，以KB为单位，可以在表格相应位置上鼠标悬停显示。</p>
<p></p>
</div>
		</div>

        </section>
      </section>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <script src="<%=basePath %>static/js/byteunit.js"></script>
    <script src="<%=basePath %>static/js/accounting/accounting.min.js"></script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
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


    function render_ICCID(record, rowIndex, colIndex, options) {
        return '<a href="<%=basePath %>sim/roamingsiminfo/view/' + record.SIMinfoID + '"><span>' + record.ICCID + '</span></a>';
     }
     function render_cardStatus(record, rowIndex, colIndex, options) {
        if (record.cardStatus == "正常") { // 可用
            return '<a class="btn btn-success btn-xs" onclick="return;"><span>' + record.cardStatus + '</span></a>';
        } else if (record.cardStatus == "欠费") { // 使用中
            return '<a class="btn btn-info btn-xs" onclick="return;"><span>' + record.cardStatus + '</span></a>';
        } else {
            return '<a class="btn btn-danger btn-xs" onclick="return;"><span>' + record.cardStatus + '</span></a>';
        }
     }
     function render_cardBalance(record, rowIndex, colIndex, options) {
         return accounting.formatMoney(record.cardBalance);
      }
     function render_planData(record, rowIndex, colIndex, options) {
         return '<a title="' + record.planData + '">' + prettyByteUnitSize(record.planData,2,1,true) + '</a>';
      }
     function render_planRemainData(record, rowIndex, colIndex, options) {
    	 if(record.planRemainData < 20480) {<%-- 少于20MB时醒目显示 --%>
             return '<a class="btn btn-danger btn-xs" title="' + record.planRemainData + '">' + prettyByteUnitSize(record.planRemainData,2,1,true) + '</a>';
    	 } else {
             return '<a title="' + record.planRemainData + '">' + prettyByteUnitSize(record.planRemainData,2,1,true) + '</a>';
    	 }
      }
<%--     function render_speedLimit(record, rowIndex, colIndex, options) {--%>
<%--        if (record.speedLimit == 0) {--%>
<%--            return '<a class="btn btn-success btn-xs" onclick="return;"><span>不限速</span></a>';--%>
<%--        } else {--%>
<%--            return '<a class="btn btn-danger btn-xs" onclick="return;"><span>限速' + record.speedLimit + 'KB</span></a>';--%>
<%--        }--%>
<%--     }--%>

// 参考sim卡导出，这个仅系导出当前页选中的项
function excelExport(){

  var l=gridObj.getCheckedRowsRecords().length;
  var imsistr='"';
  for(var i=0;i<l;i++){
    if(i==0){
        imsistr+=gridObj.getCheckedRowsRecords()[i].IMSI;
    }else{
        imsistr+=","+gridObj.getCheckedRowsRecords()[i].IMSI;
    }
  }
    $("#exprot").attr('href','<%=basePath%>sim/siminfo/excelimportSIM?IMSI='+imsistr+'"');
}

$(function(){ // 配合type=submit实现回车可提交搜索表单
	$('#searchForm').submit(function(){
	    $('input').each(function(){$(this).val(jQuery.trim($(this).val()));});gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.page(1);
		return false; //总是返回false
	});
});

</script>
<c:if test="${IsIndexView}"><%-- 区分回收站记录和正常记录的操作 --%>
    <script type="text/javascript">
    	  var gridObj;
    	  // ahming notes: 未知为什么参考siminfo_index.jsp添加了页码/页大小的记忆功能，但测试未生效
    	  $(function(){
    	  var pagesize=parseInt($("#pagesize").val());//alert('1: ' +pagesize);
          gridObj = $.fn.bsgrid.init('searchTable',{
              url:'<%=basePath %>sim/roamingsiminfo/datapage',
              pageSizeSelect: true,
              otherParames:$('#searchForm').serializeArray(),
              pageSize: pagesize,
              pageSizeForGrid:[15,30,50,100],
              multiSort:true,
              rowHoverColor:true,
              autoLoad:false,
              otherParames:$('#searchForm').serializeArray(),
              additionalAfterRenderGrid: function(parseSuccess, gridData, options) {
                  if(parseSuccess){
                      $("#pagenum").val(options.curPage);
                      var a = $("#searchTable_pt_pageSize").val();
                      $("#pagesize").val(a);

                      // 只能等待表格数据渲染完毕再初始化提示框
                      $(".ahover-tips").each(function(){$(this).hoverTipsEx({isEscape:true,isSmallWidth:true});});
                  }

               // 导出用
                  if(parseSuccess){
                      //var a = $("#searchTable_pt_pageSize").val();
                        var sta= $("#searchTable_pt_startRow").html();
                        var end= $("#searchTable_pt_endRow").html();
                        var cur = $("#searchTable_pt_curPage").html();
                        var total = $("#searchTable_pt_totalRows").html();
                        var exportString = "exportExcel?simAlias=" + $('#searchForm input[name="simAlias"]').val() +
                        "&lastDeviceSN=" + $('#searchForm input[name="lastDeviceSN"]').val() + "&ICCID=" + $('#searchForm input[name="ICCID"]').val() +
                        "&cardStatus=" + $('#searchForm select[name="cardStatus"]').val() +
                        //"&begindate=" + $('#searchForm input[name="startDate"]').val() + "&enddate=" + $('#searchForm input[name="endDate"]').val() +
                        "&sta="+sta+"&end="+end+"&cur="+cur+"&pagesize="+pagesize+"&total="+total;
                        var all;
                        if($('#exportRangeCurPage').is(':checked')){
                            all = 0;
                        //} else if($('#exportRangeAll').is(':checked')) {
                        //    all = 1;
                        } else {
                            all = 1; // 默认导出全部
                        }
                        // 暂不要用这值 alert($('input[name="exportRange"]').val());
                        $("#exportExcelButton a").attr("href", exportString + "&all=" + all); // 默认全部 all=1 若当前页则使用 all=0 或不提供 all
                        $('#exportRangeAll').click(function(){
                          //if($(this).is(':checked')){
                                $("#exportExcelButton a").attr("href",exportString + "&all=1");
                              //}
                        });
                        $('#exportRangeCurPage').click(function(){
                            //if($(this).is(':checked')){
                                  $("#exportExcelButton a").attr("href",exportString + "&all=0");
                                //}
                          });

                    } else {
                        $("#exportExcelButton a").attr("href","javascript:void(0);");
                    }

              }
          });
            if($("#pagenum").val()!=""){
                gridObj.page($("#pagenum").val());
            }else{
                gridObj.page(1);
            }

      });
//           $(function(){
//         	 //dump_obj($('#searchForm').serialize());
//              gridObj = $.fn.bsgrid.init('searchTable',{
<%--                  url:'<%=basePath %>sim/roamingsiminfo/datapage', --%>
//                  autoLoad: false,
//                  pageSizeSelect: true,
//                  pageSize: pagesize, //10,
//                  pageSizeForGrid:[15,30,50,100],
//                  multiSort:true,
//                  rowHoverColor:true,
//                  otherParames: $('#searchForm').serializeArray(),
//                  additionalAfterRenderGrid: function(parseSuccess, gridData, options) {
//                      if(parseSuccess){
//                          $("#pagenum").val(options.curPage);
//                          var a = $("#searchTable_pt_pageSize").val();//alert('2 a: ' +a);
//                          $("#pagesize").val(a);
//                      }
//                  }
//              });
//              //alert('3 pagenum: ' +$("#pagenum").val());
//              if($("#pagenum").val()!=""){
//                  gridObj.page($("#pagenum").val());
//              }else{
//                  gridObj.page(1);
//              }
//          });
         function operate(record, rowIndex, colIndex, options) {
            var result = '<div class="btn-toolbar">';
//            if(record.cardStatus == "使用中") {
//                result += '<a class="btn btn-primary btn-xs" onclick="op_confirm_modify(\'' + record.SIMinfoID + '\')"><span class="glyphicon glyphicon-edit">编辑</span></a>';
//            } else {
                result += '<a class="btn btn-primary btn-xs" href="<%=basePath %>sim/roamingsiminfo/edit/' + record.SIMinfoID + '"><span class="glyphicon glyphicon-edit">编辑</span></a>'
                + '<a class="btn btn-danger btn-xs" onclick="op_delete(\'' + record.SIMinfoID + '\')"><span class="glyphicon glyphicon-remove">删除</span></a>';
//            }

            return result;
         }
         function op_confirm_modify(id) {
             if(confirm("该SIM卡现在使用中，是否确认编辑?")) {
                 window.location.href = '<%=basePath %>sim/roamingsiminfo/edit/' + id;
             }
         }
         function op_delete(id) {
            if(confirm("确认删除SIM卡?")) {
                $.ajax({
                    type:"POST",
                    url:"<%=basePath %>sim/roamingsiminfo/delete/" + id,
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
<c:if test="${IsTrashView}"><%-- 区分回收站记录和正常记录的操作 --%>
    <script type="text/javascript">
    	  var gridObj;
          $(function(){
             gridObj = $.fn.bsgrid.init('searchTable',{
                 url:'<%=basePath %>sim/roamingsiminfo/trashdatapage',
                 // autoLoad: false,
                 pageSizeSelect: true,
                 pageSize: 10,
                 pageSizeForGrid:[10,20,30,50,100],
                 multiSort:true
             });
         });
         function operate(record, rowIndex, colIndex, options) {
<%--			//'<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">编辑</a>'; --%>
			return '<div class="btn-toolbar">'
+ '<a class="btn btn-primary btn-xs" href="<%=basePath %>sim/roamingsiminfo/edit/' + record.SIMinfoID + '"><span class="glyphicon glyphicon-edit">编辑</span></a>'
+ '<a class="btn btn-danger btn-xs" href="#" onclick="op_restore(\'' + record.SIMinfoID + '\')"><span class="glyphicon glyphicon-remove">恢复</span></a>'
+ '</div>'; // + '<a class="btn btn-primary btn-xs" href="<%=basePath %>sim/roamingsiminfo/view/' + record.SIMinfoID + '"><span class="glyphicon glyphicon-info-sign">详情</span></a>'
         }

         function op_restore(id) {
            if(confirm("确认恢复这张SIM卡?")) {
				$.ajax({
					type:"POST",
					url:"<%=basePath %>sim/roamingsiminfo/restore/" + id,
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