<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>
  <head><c:if test="${Model.ICCID eq null}"><%-- 通过必选字符串字段为null判断为添加记录 --%>
    <title>添加种子卡-漫游SIM卡管理-流量运营中心</title></c:if><c:if test="${Model.ICCID ne null}">
    <title>编辑种子卡-种子卡管理-流量运营中心</title></c:if>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="<%=basePath %>static/css/app.min.css?20150209">
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
      <div class="panel">
        <div class="panel-heading">
        <c:if test="${Model.ICCID eq null}"><%-- 通过必选字符串字段为null判断为添加记录 --%>
    添加SIM卡信息</c:if><c:if test="${Model.ICCID ne null}">
    更新SIM卡信息</c:if>
        </div>
        <div class="panel-body">
          <form id="edit_form" role="form" action="" method="post" autocomplete="off" class="form-horizontal">
            <input type="hidden" name="SIMinfoID" value="${Model.SIMinfoID}">

            <input type="hidden" name="SIMServerID" value="">
            <input type="hidden" name="serverIP" value="">

	        <div class="form-group">
	          <label for="sim_code" class="col-sm-3 control-label">ICCID：</label>
	          <div class="col-sm-6">
	            <input id="sim_code" type="text" value="${Model.ICCID}" name="ICCID" data-popover-offset="0,8"
	            required class="form-control">
	          </div>
	          <div class="col-sm-3">
	            <p class="form-control-static"><span class="red"> *  ICCID号码</span>
	            </p>
	          </div>
	        </div>
	        <div class="form-group">
                <label class="col-md-3 control-label">SIM使用类型：</label>
                <div class="col-md-6">
                    <select name="SIMCategory" required class="form-control" readonly="readonly">
                      <c:if test="${Model.SIMCategory eq '漫游卡'}"><option value="漫游卡" selected="selected">种子卡</option></c:if>
                      <c:if test="${Model.SIMCategory ne '漫游卡'}"><option value=""></option></c:if>
<%--                      <c:if test="${Model.SIMCategory eq '本地卡'}"><option value="本地卡" selected="selected">本地卡</option></c:if>
                      <c:if test="${Model.SIMCategory ne '本地卡'}"><option value="本地卡">本地卡</option></c:if> --%>
                    </select>
                </div>
                <div class="col-md-3">
                    <p class="form-control-static">
                        <c:if test="${Model.SIMCategory eq '漫游卡'}"><span class="red">* 本地卡或种子卡</span></c:if><c:if test="${Model.SIMCategory ne '漫游卡'}"><span class="yellow-b">* 出错了, 这不是种子卡, 请返回检查</span></c:if></span>
                    </p>
                </div>
            </div>
<%--	        <div class="form-group"><!-- 开发中,临时使用MCC, 觉得应该系 countryList 那则多选 -->--%>
<%--	          <label for="sim_country" class="col-sm-3 control-label">发行国家：</label>--%>
<%--	          <div class="col-sm-6">--%>
<%--	            <select id="sim_country" name="MCC" required class="form-control"><option value="">请选择国家</option>--%>
<%--	            <c:forEach items="${Countries}" var="country" varStatus="status"><c:if test="${country.countryCode eq Model.MCC}"><option value="${country.countryCode}" selected>${country.countryName}</option></c:if><c:if test="${country.countryCode ne Model.MCC}"><option value="${country.countryCode}">${country.countryName}</option></c:if></c:forEach>--%>
<%--	            </select>--%>
<%--	          </div>--%>
<%--	          <div class="col-sm-3">--%>
<%--	            <p class="form-control-static"><span class="red">* SIM卡出售发行的国家. <span class="text-info">若更改了此项,请重新检查可使用国家</span></span>--%>
<%--	            </p>--%>
<%--	          </div>--%>
<%--	        </div>--%>
	        <div class="form-group">
	          <label class="col-sm-3 control-label">使用状态：</label>
	          <div class="col-sm-6">
    <c:forEach items="${CardStatusDict}" var="item" varStatus="status">
    <label for="simStatus${status.index}" class="radio-inline">
<%--         <c:if test="${item.label ne '不可用'}"> --%>
                <c:if test="${Model.cardStatus eq item.label}"><input type="radio" name="cardStatus" id="simStatus${status.index}" value="${item.label}" checked>${item.label}</c:if>
                <c:if test="${Model.cardStatus ne item.label}"><input type="radio" name="cardStatus" id="simStatus${status.index}" value="${item.label}">${item.label}</c:if></label>
<%--         </c:if> --%>
    </c:forEach>
	          </div>
	          <div class="col-sm-3">
	            <p class="form-control-static"><span class="red"> </span>
	            </p>
	          </div>
	        </div>
            <div class="form-group">
              <label class="col-sm-3 control-label">相关设备号：</label>
              <div class="col-sm-6">
                <input type="text" name="lastDeviceSN" maxlength="20" value="${Model.lastDeviceSN}" data-popover-offset="0,8" class="form-control">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red">设备机身码，15位值</span>
                </p>
              </div>
            </div>
	        <div class="form-group">
              <label class="col-sm-3 control-label">SIM卡代号：</label>
              <div class="col-sm-6">
                <input type="text" name="simAlias" maxlength="10" value="${Model.simAlias}" data-popover-offset="0,8" class="form-control">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red">内部使用代号, 如 AN-5 等</span>
                </p>
              </div>
            </div>
	        <div class="form-group">
              <label class="col-sm-3 control-label">运营商：</label>
              <div class="col-sm-6">
                <input type="text" readonly="readonly" name="trademark" maxlength="20" value="${Model.trademark}" data-popover-offset="0,8" class="form-control">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><a class="btn btn-success btn-xs" onclick="showOperatorPicker('input[name=\'trademark\']');"><span>选择运营商</span></a>
                &nbsp;新窗口打开&gt;<a href="<%=basePath %>sim/operatorinfo/index" target="_blank">运营商管理</a></p>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3 control-label">套餐名称：</label>
              <div class="col-md-6">
                <input type="text" name="planType" value="${Model.planType}" data-popover-offset="0,8" maxlength="50"
                class="form-control">
              </div>
              <div class="col-md-3">
                <p class="form-control-static"><span class="red">* SIM卡流量套餐, 格式形如: 3G/30Days/189当地货币</span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-3 control-label">SIM卡结算方式：</label>
              <div class="col-sm-6">
    <c:forEach items="${SimBillMethodDict}" var="item" varStatus="status">
    <label for="simBillMethod${status.index}" class="radio-inline">
                <c:if test="${Model.simBillMethod eq item.label}"><input type="radio" name="simBillMethod" id="simBillMethod${status.index}" value="${item.label}" checked>${item.label}</c:if>
                <c:if test="${Model.simBillMethod ne item.label}"><input type="radio" name="simBillMethod" id="simBillMethod${status.index}" value="${item.label}">${item.label}</c:if></label>
    </c:forEach>
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red">前者多用于公测买散卡, 后者用于直接向运营卡批量买卡</span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3 control-label">购卡时间：</label>
              <div class="col-md-6">
                <div class="inputgroup">
                  <input type="text" name="planEndDate" value="${Model.planEndDate}" data-popover-offset="0,8"
                  class="form_datetime form-control">
                </div>
              </div>
              <div class="col-md-3">
                <p class="form-control-static"><span class="red">开发者提示：目前使用 planEndDate 来保存这个时间</span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3 control-label">激活时间：</label>
              <div class="col-md-6">
                <div class="inputgroup">
                  <input type="text" name="SIMActivateDate" value="${Model.SIMActivateDate}" data-popover-offset="0,8"
                  class="form_datetime form-control">
                </div>
              </div>
              <div class="col-md-3">
                <p class="form-control-static"><span class="red">20160116新增激活时间</span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-3 control-label">卡手机号码：</label>
              <div class="col-sm-6">
                <input type="text" name="phone" maxlength="20" value="${Model.phone}" data-popover-offset="0,8" class="form-control">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red"></span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-3 control-label">PIN：</label>
              <div class="col-sm-6">
                <input type="text" name="PIN" value="${Model.PIN}" data-popover-offset="0,8" class="form-control">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red"></span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-3 control-label">PUK：</label>
              <div class="col-sm-6">
                <input type="text" name="PUK" value="${Model.PUK}" data-popover-offset="0,8" class="form-control">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red"></span>
                </p>
              </div>
            </div>
            <div class="form-group">
                <label class="col-md-3 control-label">卡内余额：</label>
                <div class="col-md-6">
                    <input type="text" name="cardBalance" value="${Model.cardBalance}" data-popover-offset="0,8" class="form-control">
                </div>
                <div class="col-md-3">
                    <p class="form-control-static">
                        <span class="red">单位: 元 以RMB人民币值填写 <span class="text-info">请输入准确的余额, 充值相关功能才正常使用</span></span>
                    </p>
                </div>
            </div>
<%--            <div class="form-group">--%>
<%--              <label class="col-sm-3 control-label">APN：</label>--%>
<%--              <div class="col-sm-6">--%>
<%--                <input type="text" name="APN" maxlength="20" value="${Model.APN}" data-popover-offset="0,8" class="form-control">--%>
<%--              </div>--%>
<%--              <div class="col-sm-3">--%>
<%--                <p class="form-control-static"><span class="red"></span>--%>
<%--                </p>--%>
<%--              </div>--%>
<%--            </div>--%>
<%--            <div class="form-group">--%>
<%--              <label class="col-md-3 control-label">套餐包含流量：</label>--%>
<%--              <div class="col-md-6">--%>
<%--                <div class="input-group">--%>
<%--                  <input type="text" name="planData" value="${Model.planData}" data-popover-offset="0,58"--%>
<%--                  required class="form-control">--%>
<%--                  <div class="input-group-addon">KB</div>--%>
<%--                </div>--%>
<%--              </div>--%>
<%--              <div class="col-md-3">--%>
<%--                <p class="form-control-static"><a class="btn btn-success btn-xs" onclick="showByteUnitPicker('input[name=\'planData\']');"><span>计算器</span></a> <span class="red">* 默认1GB 其中1GB = 1024MB,1MB = 1024KB</span>--%>
<%--                </p>--%>
<%--              </div>--%>
<%--            </div>--%>
<%--            <div class="form-group">--%>
<%--              <label class="col-md-3 control-label">套餐剩余流量：</label>--%>
<%--              <div class="col-md-6">--%>
<%--                <div class="input-group">--%>
<%--                <c:if test="${Model.ICCID eq null}"> 通过必选字符串字段为null判断为新记录.新记录则清空值,以符合设定 --%>
<%--                <input type="text" name="planRemainData" data-popover-offset="0,58" value=""--%>
<%--                  class="form-control"></c:if>--%>
<%--                <c:if test="${Model.ICCID ne null}"> 通过必选字符串字段为null判断为新记录.新记录则清空值,以符合设定 --%>
<%--                <input type="text" name="planRemainData" data-popover-offset="0,58" value="${Model.planRemainData}"--%>
<%--                  class="form-control"></c:if>--%>
<%--                  <div class="input-group-addon">KB</div>--%>
<%--                </div>--%>
<%--              </div>--%>
<%--              <div class="col-md-3">--%>
<%--                <p class="form-control-static"><a class="btn btn-success btn-xs" onclick="showByteUnitPicker('input[name=\'planRemainData\']');"><span>计算器</span></a> <span class="red">如果留空, 则默认等于套餐流量大小. <span class="text-info">注意"0"值与留空系不同意义</span></span>--%>
<%--                </p>--%>
<%--              </div>--%>
<%--            </div>--%>
	        <div class="form-group">
	          <label for="sim_desc" class="col-sm-3 control-label">备注：</label>
	          <div class="col-sm-6">
	            <textarea id="sim_desc" rows="3" name="remark" maxlength="99" data-popover-offset="0,8"
	            class="form-control">${Model.remark}</textarea>
	          </div>
	        </div>
            <div id="countries" class="form-group">
                <label class="col-sm-3 control-label">可使用国家：</label>
                <div class="col-sm-6">
                    <div class="checkbox"><c:set var="preivousContinent" value="" /><c:set var="contryListCheckboxGroupIndex" value="0" /><%-- !! 以下约定国家已按大洲的顺序排列 --%>
                        <div><span><strong>组合规则</strong>&nbsp;&nbsp;</span>
                        <label for="rulesType0" class="radio-inline"><input name="rulesType" type="radio" id="rulesType0" value="" checked>包含以下选中国家</label>
                        <label for="rulesType1" class="radio-inline"><input name="rulesType" type="radio" id="rulesType1" value="" >排除以下选中国家</label>
                        <hr />
                        </div>

                       <div class="checkbox-group" id="allCountriesCheckboxGroup"><strong>全球通用</strong>&nbsp;&nbsp;<label class="checkbox-items"><input type="checkbox" id="allCountriesCheckbox" /><span>选择</span></label>
                       <input type="hidden" name="countryList" id="allCountriesCheckboxValue" value="" /><!-- 为空表示全球通用 -->
                       </div>
                       <c:forEach items="${Countries}" var="country" varStatus="status">
                           <c:if test="${preivousContinent ne country.continent}"><!--ahming notes:当前只在添加时提供全选按钮-->
                               <div class="checkbox-group"><strong>${country.continent}</strong>
<%--                                <c:if test="${empty Model or empty Model.SIMinfoID}"> --%>
                                    &nbsp;&nbsp;<label class="checkbox-items"><input type="checkbox" class="selectAllCheckbox" value="${contryListCheckboxGroupIndex + 1}" /><span>全选</span></label>
<%--                                </c:if> --%>
                               </div>
                               <c:set var="preivousContinent" value="${country.continent}" />
                               <c:set var="contryListCheckboxGroupIndex" value="${contryListCheckboxGroupIndex + 1}" />
                           </c:if>
                           <c:if test="${country.selected}">
                                <label class="checkbox-items">
                                <input type="checkbox" name="countryList" class="countryListCheckboxGroup${contryListCheckboxGroupIndex}" value="${country.countryCode}" checked >${country.countryName}</label>
                           </c:if>
                           <c:if test="${!country.selected}">
                                <label class="checkbox-items">
                                <input type="checkbox" name="countryList" class="countryListCheckboxGroup${contryListCheckboxGroupIndex}" value="${country.countryCode}">${country.countryName}
                                </label>
                           </c:if>
                       </c:forEach>
                    </div>
                </div>
                <div class="col-sm-3">
                <p class="form-control-static"><span class="red">* 漫游SIM卡可使用的国家, 途狗设备绑定的漫游SIM卡可使用的国家范围亦需要管理</span>
                </p>
                </div>
            </div>
	        <div class="form-group">
	          <div class="col-sm-6 col-sm-offset-3">
	            <div class="btn-toolbar">
	              <button type="submit" class="btn btn-primary">保存</button>
	              <button type="button" onclick="javascript:history.go(-1);"
	              class="btn btn-default">返回</button>
	            </div>
	          </div>
	        </div>

          </form>
        </div>
      </div>
    </div>

        </section>
      </section>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/bootbox.min.js"></script>
    <script src="<%=basePath %>static/js/bootstrap-treeview/js/bootstrap-treeview.js"></script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
    <script type="text/javascript">$(function(){
        $(".form_datetime").datetimepicker({
            format: 'YYYY-MM-DD',
            pickDate: true,     //en/disables the date picker
            pickTime: false,     //en/disables the time picker
            showToday: true,    //shows the today indicator
            language:'zh-CN',   //sets language locale
            defaultDate: null, // moment().add(7, 'days'), //sets a default date, accepts js dates, strings and moment objects
        });

        $('.selectAllCheckbox').click(function(){ // 全选按钮
            if($(this).prop("checked")) {
                $(this).next().empty().append("清空"); // 确定把文字放到一个node内,目前即指span
                $('.countryListCheckboxGroup'+$(this).attr("value")).prop("checked", true);
            } else {
                $(this).next().empty().append("全选");
                $('.countryListCheckboxGroup'+$(this).attr("value")).removeProp("checked");
            }
        });
        // 全球通用的特殊处理
        var countryList = "${Model.countryList}";
        var ifExclude = "${ExcludeCountries}";
        if(countryList == ""){
        	var allCountryListObj = $('#allCountriesCheckbox');
        	allCountryListObj.prop('checked',true);
        	allCountryListObj.next().empty().append("取消");
        	$('input[class*="countryListCheckboxGroup"]').prop('disabled',true);
        	$('.selectAllCheckbox').prop('disabled',true);
        	$('#allCountriesCheckboxValue').prop('disabled',false);
        } else {
        	$('#allCountriesCheckboxValue').prop('disabled',true);
        	if('1' == ifExclude) { // 参考 $('#rulesType1').click(function(){
        		$('#allCountriesCheckboxGroup').hide(); // 取消选中全球通用
                $('#allCountriesCheckboxValue').prop('disabled',true);
                $('input[class*="countryListCheckboxGroup"]').prop('disabled',false);
                $('.selectAllCheckbox').prop('disabled',false);

                $('#rulesType1').prop("checked", true);
        	}
        }
        $('#allCountriesCheckbox').click(function(){
        	if($(this).prop("checked")) { // 原来选中
        		$(this).next().empty().append("取消");
        		$('input[class*="countryListCheckboxGroup"]').prop('disabled',true);
        		$('.selectAllCheckbox').prop('disabled',true);
        		$('#allCountriesCheckboxValue').prop('disabled',true);
        	} else { // 原来未选中
        		$(this).next().empty().append("选择");
        		$('input[class*="countryListCheckboxGroup"]').prop('disabled',false);
        		$('.selectAllCheckbox').prop('disabled',false);
        		$('#allCountriesCheckboxValue').prop('disabled',false);
        	}
        });
        $('#rulesType0').click(function(){
        	$('#allCountriesCheckbox').prop("checked", false);
            $('#allCountriesCheckbox').next().empty().append("选择");
            $('#allCountriesCheckboxValue').prop('disabled',true); // 因为此时全球通用都系未选中，所以禁止
        	$('#allCountriesCheckboxGroup').show();
        });
        $('#rulesType1').click(function(){
            $('#allCountriesCheckboxGroup').hide(); // 取消选中全球通用
            $('#allCountriesCheckboxValue').prop('disabled',true);
            $('input[class*="countryListCheckboxGroup"]').prop('disabled',false);
            $('.selectAllCheckbox').prop('disabled',false);
        });

    $("#edit_form").validate_popover({
        rules:{
            'ICCID':{ required:true,number:true,minlength:19,maxlength:19 },
            'PIN':{ number:true },
            'PUK':{ number:true },
            'cardBalance':{ number:true }
        },
        messages:{
            'ICCID':{required:"ICCID是19位数字",number:"ICCID是19位数字",minlength:"ICCID是19位数字",maxlength:"ICCID是19位数字"},
            'PIN':{number:"请输入数字"},
            'PUK':{number:"请输入数字"},
            'cardBalance':{number:"请输入数字"}
        },
        submitHandler: function(form){
            var planRemainDataValue = $.trim($("input[name='planRemainData']").val());
            if(planRemainDataValue == null || planRemainDataValue.length == 0){
               $("input[name='planRemainData']").val($("input[name='planData']").val());
            }
            // 如果系排除国家的添加 -号
            if($('#rulesType1').prop("checked")){
            	//alert('lll'); var counttttt = 0;
            	$('input[class*="countryListCheckboxGroup"]:checked').each(function(){
            		//counttttt ++; alert(counttttt);
            		$(this).val('-' + $(this).val());
            		//alert($(this).val());
            	});
            }
            if($('input[class*="countryListCheckboxGroup"]:checked').length > 0) {
            	$('#allCountriesCheckboxValue').prop('disabled',true);
            }
            //return false;
	        $.ajax({
	            type:"POST",
	            url:"<%=basePath %>sim/roamingsiminfo/save",
	            dataType:"html",
	            data:$('#edit_form').serialize(),
	            success:function(data){
	                result = jQuery.parseJSON(data);
	                if (result.code == 0) { // 成功保存
	                    easy2go.toast('info', result.msg);
	                    history.go(-1); // 按广超需求这里应该系使用这个
//	                    var redirect = "<%=request.getHeader("Referer") %>";
// 	                   if(redirect==null) {
//	                     window.location.href="<%=basePath %>sim/roamingsiminfo/index";
// 	                   } else {
// 	                     window.location.href=redirect;
// 	                   }
	                } else {
	                    easy2go.toast('error', result.msg);
	                }
	            }
	        });
        }
    });

	});

    function showOperatorPicker(selector) {
        bootbox.dialog({
            title: "运营商列表",
            message: '<div class="row">  ' +
                '<div class="col-md-12"><div id="treeview_operator" class=""> </div></div>' +
                '<div class="col-md-12"><p>选择的运营商： <span id="operator-selected"></span></p></div>' +
                '</div> ',
            buttons: {
                cancel: {
                    label: "取消",
                    className: "btn-default",
                    callback: function () {
                       $('#operator-selected').empty();
                    }
                },
                danger: {
                    label: "清空",
                    className: "btn-danger",
                    callback: function() {
                    	if($(selector).val().length > 0) {
	                    	$(selector).val('');
	                    	easy2go.toast('warn', '已清除运营商');
                    	}
                    }
                  },
                success: {
                    label: "设定",
                    className: "btn-success",
                    callback: function () {
                    	if($('#operator-selected').text().length == 0){
                    		easy2go.toast('info', '请选择运营商');
                    		return false; // 注意必须系 false , 若只系 return 则无法阻止关闭
                    	}
                        $(selector).val($('#operator-selected').text());
                        easy2go.toast('success', '已成功设置运营商');
                    }
                }
            }
        });

        $.ajax({
                type:"GET",
                url:"<%=basePath %>sim/operatorinfo/getTreeviewString",
                dataType:"html",
               // data:$('#plan_form').serialize(),
                success:function(data){
                    $('#treeview_operator').treeview({
                      levels: 1, // 初始折叠
                      color: "#428bca",
                      onNodeSelected: function(event, node) {
                            // var $tree = $('#tree').treeview('selectNode', 0); // 有误
                            // var nodes = $tree.treeview('getSelected');
                            // alert(nodes.length); // nodes[0].text

                          // 因为按文档上面的例子无法正确得到选中项的值，所以使用这种替代方法
                          //$('#trademark').val(node.text);
                          $('#operator-selected').empty().append(node.text);
                        },
                      data: data
                    });
                }
            });
    }

    function showByteUnitPicker(selector){
        if(selector == null || selector.lenght == 0) {
            alert("打开出错, 需要提供选择器, 请联系管理员");
            return;
        }
        bootbox.dialog({
            title: "请输入需要大小, 最终结果会折算成K字节(KB)",
            message: '<div class="row">  ' +
                '<div class="col-md-12"> ' +
                '<form class="form-horizontal" id="byte-unit-picker-form"> ' +
                '<div class="form-group"> ' +
                '<label class="col-md-4 control-label" for="name">GB:</label> ' +
                '<div class="col-md-4"> <div class="input-group">' +
                '<input id="gb" name="gb" type="text" value="0" placeholder="" class="form-control input-md" data-popover-offset="0,58"> ' +
                '<div class="input-group-addon">G</div></div><span>吉字节 1G = 1024M</div> ' +
                '</div> ' +
                '<div class="form-group"> ' +
                '<label class="col-md-4 control-label" for="name">MB:</label> ' +
                '<div class="col-md-4"> <div class="input-group">' +
                '<input id="mb" name="mb" type="text" value="0" placeholder="" class="form-control input-md" data-popover-offset="0,58"> ' +
                '<div class="input-group-addon">M</div></div><span>兆字节 1M = 1024K</div> ' +
                '</div> ' +
                '<div class="form-group"> ' +
                '<label class="col-md-4 control-label" for="name">KB:</label> ' +
                '<div class="col-md-4"> <div class="input-group">' +
                '<input id="kb" name="kb" type="text" value="0" placeholder="" class="form-control input-md" data-popover-offset="0,58"> ' +
                '<div class="input-group-addon">K</div></div><span>千字节 1K = 1024B (Byte 字节)</div> ' +
                '</div> ' +
                '<div class="form-group"> ' +
                '<label class="col-md-4 control-label" for="name">最终结果:</label> ' +
                '<div class="col-md-4"> <div class="input-group">' +
                '<input id="kb-result" readonly="readonly" name="kb-result" type="text" value="0" class="form-control input-md"> ' +
                '<div class="input-group-addon">K</div></div></div><div class="col-md-4">以上三个可以组合输入, 此处为相加的结果</div> ' +
                '</div> ' +
                '</form> </div>  </div>',
            buttons: {
                cancel: {
                    label: "取消",
                    className: "btn-default",
                    callback: function () {
                    }
                },
                success: {
                    label: "设定",
                    className: "btn-success byte-unit-picker-button-ok",
                    callback: function () {
                        $(selector).val($("#kb-result").val());
                    }
                }
            }
        }
    );
        $("button.byte-unit-picker-button-ok").prop("disabled", true); // 初始时禁止
        $("#byte-unit-picker-form").validate_popover({
            rules:{
                'gb':{ number:true },
                'mb':{ number:true },
                'kb':{ number:true }
            },
            messages:{
                'gb':{number:"请输入数字"},
                'mb':{number:"请输入数字"},
                'kb':{number:"请输入数字"}
            }
        });

        $("#gb").keyup(function(){
            if($("#gb").val() >= 0 && $("#mb").val() >= 0 && $("#kb").val() >= 0 ) {
                $("#kb-result").val($("#gb").val() * 1024 * 1024 + $("#mb").val() * 1024 + $("#kb").val() * 1  );
                $("button.byte-unit-picker-button-ok").prop("disabled", false);
            } else {
                $("button.byte-unit-picker-button-ok").prop("disabled", true);
                $("#kb-result").val(0);
            }
        });
        $("#mb").keyup(function(){
            if($("#gb").val() >= 0 && $("#mb").val() >= 0 && $("#kb").val() >= 0 ) {
                $("#kb-result").val($("#gb").val() * 1024 * 1024 + $("#mb").val() * 1024 + $("#kb").val() * 1  );
                $("button.byte-unit-picker-button-ok").prop("disabled", false);
            } else {
                $("button.byte-unit-picker-button-ok").prop("disabled", true);
                $("#kb-result").val(0);
            }
        });
        $("#kb").keyup(function(){
            if($("#gb").val() >= 0 && $("#mb").val() >= 0 && $("#kb").val() >= 0 ) {
                $("#kb-result").val($("#gb").val() * 1024 * 1024 + $("#mb").val() * 1024 + $("#kb").val() * 1  );
                $("button.byte-unit-picker-button-ok").prop("disabled", false);
            } else {
                $("button.byte-unit-picker-button-ok").prop("disabled", true);
                $("#kb-result").val(0);
            }
        });

    }
    </script>

<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>