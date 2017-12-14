<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html>
<html>
  <head><c:if test="${Model.ICCID eq null}"><%-- 通过必选字符串字段为null判断为添加记录 --%>
    <title>添加本地卡-本地SIM卡管理-流量运营中心</title></c:if><c:if test="${Model.ICCID ne null}">
    <title>编辑本地卡-本地SIM卡管理-流量运营中心</title></c:if>
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
        <c:if test="${Model.IMSI eq null}"><%-- 通过必选字符串字段为null判断为添加记录 --%>
    添加SIM卡信息</c:if><c:if test="${Model.IMSI ne null}">
    更新SIM卡信息</c:if>          
        </div>
        <div class="panel-body">
          <form id="edit_form" role="form" action="" method="post" autocomplete="off" class="form-horizontal">
            <input type="hidden" name="SIMinfoID" value="${Model.SIMinfoID}">
	        <div class="form-group">
	          <label for="sim_code" class="col-sm-3 control-label">ICCID：</label>
	          <div class="col-sm-6">
	            <input id="sim_code" type="text" value="${Model.ICCID}" name="ICCID"
	            class="form-control">
	          </div>
	          <div class="col-sm-3">
	            <p class="form-control-static"><span class="red">ICCID号码,可不填</span>
	            </p>
	          </div>
	        </div>
	        <div class="form-group">
	          <label for="sim_code" class="col-sm-3 control-label">IMSI：</label>
	          <div class="col-sm-6">
	            <input id="sim_code" type="text" value="${Model.IMSI}" name="IMSI" data-popover-offset="0,8"
	            required class="form-control">
	          </div>
	          <div class="col-sm-3">
	            <p class="form-control-static"><span class="red"> *  IMSI号码</span>
	            </p>
	          </div>
	        </div>
<%--	        <div class="form-group">--%>
<%--              <label class="col-sm-3 control-label">IMSI：</label>--%>
<%--              <div class="col-sm-6">--%>
<%--                <input type="text" name="IMSI" value="${Model.IMSI}" data-popover-offset="0,8" class="form-control">--%>
<%--              </div>--%>
<%--              <div class="col-sm-3">--%>
<%--                <p class="form-control-static"><span class="red">IMSI 如果有的话可录入</span>--%>
<%--                </p>--%>
<%--              </div>--%>
<%--            </div>--%>
			<input type="hidden" value="本地卡" name="SIMCategory"/>
	        <%-- <div class="form-group">
                <label class="col-md-3 control-label">SIM使用类型：</label>
                <div class="col-md-6">
                    <select name="SIMCategory" required class="form-control" readonly="readonly">
                     <c:if test="${Model.SIMCategory eq '漫游卡'}"><option value="漫游卡" selected="selected">漫游卡</option></c:if>
                      <c:if test="${Model.SIMCategory ne '漫游卡'}"><option value="漫游卡">漫游卡</option></c:if>
                      <c:if test="${Model.SIMCategory eq '本地卡'}"><option value="本地卡" selected="selected">本地卡</option></c:if>
                      <c:if test="${Model.SIMCategory ne '本地卡'}"><option value=""></option></c:if>
                    </select>
                </div>
                <div class="col-md-3">
                    <p class="form-control-static">
                        <c:if test="${Model.SIMCategory eq '本地卡'}"><span class="red">* 本地卡或漫游卡</span></c:if><c:if test="${Model.SIMCategory ne '本地卡'}"><span class="yellow-b">* 出错了, 这不是本地卡, 请返回检查</span></c:if></span>
                    </p>
                </div>
            </div> --%>
            <%-- <div class="form-group">
	          <label for="SIMServerID" class="col-sm-3 control-label">服务器：</label>
	          <div class="col-sm-6">
	            <select id="SIMServerID" name="SIMServerID" required class="form-control">
                <option value="">请选择服务器</option>添加上保证必须主动选择服务器, 避免缺省服务器可能引起隐藏错误
                <c:forEach items="${SIMServers}" var="server" varStatus="status"><c:if test="${server.SIMServerID eq Model.SIMServerID}"><option value="${server.SIMServerID}" selected>${server.IP}</option></c:if><c:if test="${server.SIMServerID ne Model.SIMServerID}"><option value="${server.SIMServerID}">${server.IP}</option></c:if></c:forEach>
	            </select>
	            <input type="hidden" id="serverIP" name="serverIP" value="${Model.serverIP}"><!-- TODO: 根据前面select值设置IP -->
	          </div>
	          <div class="col-sm-3">
                <p class="form-control-static"><span class="red">* 服务器IP</span>
	            </p>
	          </div>
	        </div> --%>
            <div style="display:none;" class="form-group">
              <label class="col-sm-3 control-label">卡槽编号：</label>
              <div class="col-sm-6">
                <input type="text" name="slotNumber" maxlength="20" value="${Model.slotNumber}" data-popover-offset="0,8" class="form-control">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red">SIMBank编号-卡槽位置：001-A1</span>
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
	        <div class="form-group"><!-- 开发中,临时使用MCC, 觉得应该系 countryList 那则多选 -->
	          <label for="sim_country" class="col-sm-3 control-label">发行国家：</label>
	          <div class="col-sm-6">
	            <select id="sim_country" name="MCC" required class="form-control"><option value="">请选择国家</option>
	            <c:forEach items="${Countries}" var="country" varStatus="status"><c:if test="${country.countryCode eq Model.MCC}"><option value="${country.countryCode}" selected>${country.countryName}</option></c:if><c:if test="${country.countryCode ne Model.MCC}"><option value="${country.countryCode}">${country.countryName}</option></c:if></c:forEach>
	            </select>
	          </div>
	          <div class="col-sm-3">
	            <p class="form-control-static"><span class="red">* SIM卡出售发行的国家. <span class="text-info">若更改了此项,请重新检查可使用国家</span></span>
	            </p>
	          </div>
	        </div>
            <div id="countries" class="form-group">
                <label class="col-sm-3 control-label">可使用国家：</label>
                <div class="col-sm-6">
                    <div class="checkbox"><c:set var="preivousContinent" value="" /><%-- !! 以下约定国家已按大洲的顺序排列 --%>
                       <c:forEach items="${Countries}" var="country" varStatus="status">
	                       <c:if test="${preivousContinent ne country.continent}">    
		                       <div class="checkbox-group">
		                      	 <strong>${country.continent}</strong>
		                       </div>
		                       <c:set var="preivousContinent" value="${country.continent}" />
		                   </c:if>
	                       <c:if test="${country.selected}">    
	                      	 	<label class="checkbox-items">
	                      	 	<input type="checkbox" name="countryList"  value="${country.countryCode}" checked>${country.countryName}</label>
	                       </c:if>
	                       <c:if test="${!country.selected}">   
	                        	<label class="checkbox-items">
	                        		<input type="checkbox" name="countryList" value="${country.countryCode}">${country.countryName}
	                        	</label>
	                       </c:if>
                       </c:forEach>
                    </div>
                </div>
                <div class="col-sm-3">
                <p class="form-control-static"><span class="red">* SIM卡可使用的国家, 此卡可多国漫游使用, 由 Sim Server 匹配使用</span>
                </p>
                </div>
            </div>
            <div class="form-group mnc1" style="display:block;">
              <label class="col-sm-3 control-label">国内/省内：</label>
              <div class="col-sm-6">
              	<%-- <c:if  test="${Model.MNC eq 101}">
               	  <label for="mnc0" class="radio-inline">
                  <input type="radio" name="MNC" id="mnc0" value="101" checked>国内</label>
               	  <label for="mnc1" class="radio-inline">
                  <input type="radio" name="MNC" id="mnc1" value="44">省内</label>
                </c:if >	
                <c:if  test="${Model.MNC eq 44}">
               	  <label for="mnc0" class="radio-inline">
                  <input type="radio" name="MNC" id="mnc0" value="101" >国内</label>
               	  <label for="mnc1" class="radio-inline">
                  <input type="radio" name="MNC" id="mnc1" value="44" checked>省内</label>
                </c:if>
                <c:if  test="${Model.MNC ne 44 && Model.MNC ne 101}">
               	  <label for="mnc0" class="radio-inline">
                  <input type="radio" name="MNC" id="mnc0" value="101" >国内</label>
               	  <label for="mnc1" class="radio-inline">
                  <input type="radio" name="MNC" id="mnc1" value="44" >省内</label>
                </c:if> --%>
                <select name="MNC" class="form-control" >
                    <option value="101">全国</option>
                    <option value="11" <c:if  test="${Model.MNC eq 11}">selected</c:if>>北京市</option>
                    <option value="12" <c:if  test="${Model.MNC eq 12}">selected</c:if>>天津市</option>
                    <option value="13" <c:if  test="${Model.MNC eq 13}">selected</c:if>>河北省</option>
                    <option value="14" <c:if  test="${Model.MNC eq 14}">selected</c:if>>山西省</option>
                    <option value="15" <c:if  test="${Model.MNC eq 15}">selected</c:if>>内蒙古自治区</option>
                    <option value="21" <c:if  test="${Model.MNC eq 21}">selected</c:if>>辽宁省</option>
                    <option value="22" <c:if  test="${Model.MNC eq 22}">selected</c:if>>吉林省</option>
                    <option value="23" <c:if  test="${Model.MNC eq 23}">selected</c:if>>黑龙江省</option>
                    <option value="31" <c:if  test="${Model.MNC eq 31}">selected</c:if>>上海市</option>
                    <option value="32" <c:if  test="${Model.MNC eq 32}">selected</c:if>>江苏省</option>
                    <option value="33" <c:if  test="${Model.MNC eq 33}">selected</c:if>>浙江省</option>
                    <option value="34" <c:if  test="${Model.MNC eq 34}">selected</c:if>>安徽省</option>
                    <option value="35" <c:if  test="${Model.MNC eq 35}">selected</c:if>>福建省</option>
                    <option value="36" <c:if  test="${Model.MNC eq 36}">selected</c:if>>江西省</option>
                    <option value="37" <c:if  test="${Model.MNC eq 37}">selected</c:if>>山东省</option>
                    <option value="41" <c:if  test="${Model.MNC eq 41}">selected</c:if>>河南省</option>
                    <option value="42" <c:if  test="${Model.MNC eq 42}">selected</c:if>>湖北省</option>
                    <option value="43" <c:if  test="${Model.MNC eq 43}">selected</c:if>>湖南省</option>
                    <option value="44" <c:if  test="${Model.MNC eq 44}">selected</c:if>>广东省</option>
                    <option value="45" <c:if  test="${Model.MNC eq 45}">selected</c:if>>广西壮族自治区</option>
                    <option value="46" <c:if  test="${Model.MNC eq 46}">selected</c:if>>海南省</option>
                    <option value="50" <c:if  test="${Model.MNC eq 50}">selected</c:if>>重庆市</option>
                    <option value="51" <c:if  test="${Model.MNC eq 51}">selected</c:if>>四川省</option>
                    <option value="52" <c:if  test="${Model.MNC eq 52}">selected</c:if>>贵州省</option>
                    <option value="53" <c:if  test="${Model.MNC eq 53}">selected</c:if>>云南省</option>
                    <option value="54" <c:if  test="${Model.MNC eq 54}">selected</c:if>>西藏自治区</option>
                    <option value="61" <c:if  test="${Model.MNC eq 61}">selected</c:if>>陕西省</option>
                    <option value="62" <c:if  test="${Model.MNC eq 62}">selected</c:if>>甘肃省</option>
                    <option value="63" <c:if  test="${Model.MNC eq 63}">selected</c:if>>青海省</option>
                    <option value="64" <c:if  test="${Model.MNC eq 64}">selected</c:if>>宁夏回族自治区</option>
                    <option value="65" <c:if  test="${Model.MNC eq 65}">selected</c:if>>新疆维吾尔自治区</option>
                    <option value="66" <c:if  test="${Model.MNC eq 66}">selected</c:if>>新疆生产建设兵团</option>
                </select>
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red"> </span>
                </p>
              </div>
            </div>
            
             <div class="form-group" style="display:block;">
              <label class="col-sm-3 control-label">卡来源：</label>
              <div class="col-sm-6">
				<select name="cardSource" id="cardSource" class="form-control">
					<option value="">ylcy</option>
					<c:forEach items="${distributors}" var="distributor" varStatus="status">
					<c:choose>
						<c:when test="${Model.cardSource eq  distributor.distributorID}">
							<option value="${distributor.distributorID}" selected="selected">${distributor.company}</option>
						</c:when>
						<c:otherwise>
							<option value="${distributor.distributorID}">${distributor.company}</option>
						</c:otherwise>
					</c:choose>
					
					<%-- 	<c:if test="${Model.cardSource eq  distributor.distributorID}">
							<option value="${distributor.distributorID}">${distributor.company}</option>
						</c:if>
						<c:else>
							<option value="${distributor.distributorID}">${distributor.company}</option>
						</c:else>
						 --%>
					</c:forEach>
				</select>
	          </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red"> （如果为空表示本公司卡源）</span>
                </p>
              </div>
            </div>
            
            
            <div class="form-group">
              <label class="col-sm-3 control-label">是否支持漫游：</label>
              <div class="col-sm-6">
                <c:if test="${Model.ifRoam eq 0}">
                <label for="ifRoam0" class="radio-inline">
                  <input type="radio" name="ifRoam" id="ifRoam0" value="0" checked>不支持</label>
                <label for="ifRoam1" class="radio-inline">
                  <input type="radio" name="ifRoam" id="ifRoam1" value="1">支持</label>
                </c:if>
                <c:if test="${Model.ifRoam eq 1}">
	               <label for="ifRoam0" class="radio-inline">
	               <input type="radio" name="ifRoam" id="ifRoam0" value="0">不支持</label>
	               <label for="ifRoam1" class="radio-inline">
	               <input type="radio" name="ifRoam" id="ifRoam1" value="1" checked>支持</label>
                </c:if>
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red"> </span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-3 control-label">高速/低速：</label>
              <div class="col-sm-6">
                <c:if test="${Model.speedType eq 0}"><label for="speedType0" class="radio-inline">
                  <input type="radio" name="speedType" id="speedType0" value="0" checked>0 高速</label>
                <label for="speedType1" class="radio-inline">
                  <input type="radio" name="speedType" id="speedType1" value="1">1 低速</label>
                </c:if>
                <c:if test="${Model.speedType eq 1}"><label for="speedType0" class="radio-inline">
                  <input type="radio" name="speedType" id="speedType0" value="0">0 高速</label>
                <label for="speedType1" class="radio-inline">
                  <input type="radio" name="speedType" id="speedType1" value="1" checked>1 低速</label>
                </c:if>
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red"> </span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-3 control-label">预约标识：</label>
              <div class="col-sm-6">
                <c:if test="${Model.subscribeTag eq 0}"><label for="subscribeTag0" class="radio-inline">
                  <input type="radio" name="subscribeTag" id="subscribeTag0" value="0" checked>0 没预约</label>
                <label for="subscribeTag1" class="radio-inline">
                  <input type="radio" name="subscribeTag" id="subscribeTag1" value="1">1 预约</label>
                </c:if>
                <c:if test="${Model.subscribeTag eq 1}"><label for="subscribeTag0" class="radio-inline">
                  <input type="radio" name="subscribeTag" id="subscribeTag0" value="0">0 没预约</label>
               	 <label for="subscribeTag1" class="radio-inline">
                  <input type="radio" name="subscribeTag" id="subscribeTag1" value="1" checked>1 预约</label>
                </c:if>
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red"> </span>
                </p>
              </div>
            </div>
            
            <c:if test="${Model.cardStatus eq '使用中'}">
			<div  class="form-group">
              <label class="col-sm-3 control-label">上次绑定机身码：</label>
              <div class="col-sm-6">
                <input type="text" name="lastDeviceSN" readonly="readonly" maxlength="20" value="${Model.lastDeviceSN}" data-popover-offset="0,8" class="form-control">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red">(空字符串表示不手动选网)</span>
                </p>
              </div>
            </div>
            </c:if>
            <c:if test="${Model.cardStatus ne '使用中'}">
			<div  class="form-group">
              <label class="col-sm-3 control-label">上次绑定机身码：</label>
              <div class="col-sm-6">
                <input type="text" name="lastDeviceSN"  maxlength="20" value="${Model.lastDeviceSN}" data-popover-offset="0,8" class="form-control">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red">(空字符串表示不手动选网)</span>
                </p>
              </div>
            </div>
            </c:if>
            <div class="form-group">
              <label class="col-sm-3 control-label">是否绑定设备：</label>
              <div class="col-sm-6">
                
                  
                <c:if test="${Model.ifBoundSN eq '0'}">
	                <label for="ifboundsn0" class="radio-inline">
	               		  <input type="radio" name="ifBoundSN" id="ifboundsn0" value="0" checked>否</label>                  
	                <label for="ifboundsn1" class="radio-inline">
	              		  <input type="radio" name="ifBoundSN" id="ifboundsn1" value="1">是</label>
                </c:if>
                <c:if test="${Model.ifBoundSN eq '1'}">
	                <label for="ifboundsn0" class="radio-inline">
	              		  <input type="radio" name="ifBoundSN" id="ifboundsn0" value="0">否</label>                  
	                <label for="ifboundsn1" class="radio-inline">
	               		 <input type="radio" name="ifBoundSN" id="ifboundsn1" value="1" checked>是</label>
                </c:if>
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red"></span>
                </p>
              </div>
            </div>
	        <div class="form-group">
	          <label class="col-sm-3 control-label">使用状态：</label>
	          <div class="col-sm-6">
			    <c:forEach items="${CardStatusDict}" var="item" varStatus="status">
		                <c:if test="${Model.cardStatus eq item.label}">
		                  <label for="simStatus${status.index}" class="radio-inline">
		                 		 <input type="radio" name="cardStatus" id="simStatus${status.index}" value="${item.label}" checked>${item.label}
		                  </label>
		                </c:if>
			            <c:if test="${Model.cardStatus ne item.label && item.label ne '不可用'}">
		                  <label for="simStatus${status.index}" class="radio-inline">
		                  		<input type="radio" name="cardStatus" id="simStatus${status.index}" value="${item.label}">${item.label}
		                  </label>
			            </c:if>
			    </c:forEach>
	          </div>
	          <div class="col-sm-3">
	            <p class="form-control-static"><span class="red">* 不可用的卡请标注为调试不可用，停用表示备用的卡 </span>
	            </p>
	          </div>
	        </div>
	        
	        <%-- <div class="form-group" style="border-bottom: 1px solid #EFF2F7;padding-bottom: 15px;margin-bottom:30px;">
	          <label class="col-sm-3 control-label">是否限速：</label>
	          <div class="col-sm-6">
	            <c:if test="${Model.speedLimit eq 0}"><label for="sim_data_limit0" class="radio-inline">
	              <input type="radio" name="speedLimit" id="sim_data_limit0" value="0" checked>不限速</label>
	            <label for="sim_data_limit1" class="radio-inline">
	              <input type="radio" name="speedLimit" id="sim_data_limit1" value="${Model.speedLimit}">限速</label><!-- TODO: 设置默认限速 -->
	            </c:if>
	            <c:if test="${Model.speedLimit gt 0}"><label for="sim_data_limit0" class="radio-inline">
                  <input type="radio" name="speedLimit" id="sim_data_limit0" value="0">不限速</label>
                <label for="sim_data_limit1" class="radio-inline">
                  <input type="radio" name="speedLimit" id="sim_data_limit1" value="${Model.speedLimit}" checked>限速</label><!-- TODO: 设置默认限速 -->
                </c:if>

                <div class="input-group">
                <input type="text" name="speedLimit" value="${Model.speedLimit}" data-popover-offset="0,58" class="form-control"><div class="input-group-addon">KB</div>
                </div>
	          </div>
	          <div class="col-sm-3">
	            <p class="form-control-static"><span class="red">零则不限速, 否则直接输入限速大小, 单位 KB</span>
	            </p>
	          </div>
	        </div> --%>
<%--	        <div class="form-group">--%>
<%--              <label class="col-sm-3 control-label">SIM卡类型：</label>--%>
<%--              <div class="col-sm-6">--%>
<%--    <c:forEach items="${SIMTypeDict}" var="item" varStatus="status">--%>
<%--    <label for="simType${status.index}" class="radio-inline">--%>
<%--                <c:if test="${Model.SIMType eq item.label}"><input type="radio" name="SIMType" id="simType${status.index}" value="${item.label}" checked>${item.label}</c:if>--%>
<%--                <c:if test="${Model.SIMType ne item.label}"><input type="radio" name="SIMType" id="simType${status.index}" value="${item.label}">${item.label}</c:if></label>--%>
<%--    </c:forEach>--%>
<%--              </div>--%>
<%--              <div class="col-sm-3">--%>
<%--                <p class="form-control-static"><span class="red"> </span>--%>
<%--                </p>--%>
<%--              </div>--%>
<%--            </div>--%>
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
              <label class="col-sm-3 control-label">卡手机号码：</label>
              <div class="col-sm-6">
                <input type="text" name="phone" maxlength="20" value="${Model.phone}" data-popover-offset="0,8" class="form-control">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red"></span>
                </p>
              </div>
            </div>
            <div class="form-group" style="border-bottom: 1px solid #EFF2F7;padding-bottom: 30px;margin-bottom:30px;">
              <label class="col-sm-3 control-label">APN：</label>
              <div class="col-sm-6">
                <input type="text" name="APN"  value="${Model.APN}" data-popover-offset="0,8" class="form-control">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red"></span>
                </p>
              </div>
            </div>
            <div class="form-group" style="border-bottom: 1px solid #EFF2F7;padding-bottom: 30px;margin-bottom:30px;">
              <label class="col-sm-3 control-label">VPN：</label>
              <div class="col-sm-6">
            <c:if test="${Model.VPN eq '0' || empty Model.VPN || Model.VPN ==null}">
              	  <label for="vpn1" class="radio-inline">
                  <input type="radio" name="VPN" id="vpn1" value="0" checked>不支持</label>
               	  <label for="vpn2" class="radio-inline">
                  <input type="radio" name="VPN" id="vpn2" value="1" >支持大流量</label>
               	  <label for="vpn2" class="radio-inline">
                  <input type="radio" name="VPN" id="vpn2" value="2" >支持小流量</label>
              </c:if>
              <c:if test="${Model.VPN eq '1' }">
              	     <label for="vpn1" class="radio-inline">
                  <input type="radio" name="VPN" id="vpn1" value="0">不支持</label>
               	  <label for="vpn2" class="radio-inline">
                  <input type="radio" name="VPN" id="vpn2" value="1" checked>支持大流量</label>
               	  <label for="vpn2" class="radio-inline">
                  <input type="radio" name="VPN" id="vpn2" value="2">支持小流量</label>
              </c:if>
              <c:if test="${Model.VPN eq '2' }">
              	     <label for="vpn1" class="radio-inline">
                  <input type="radio" name="VPN" id="vpn1" value="0">不支持</label>
               	  <label for="vpn2" class="radio-inline">
                  <input type="radio" name="VPN" id="vpn2" value="1" >支持大流量</label>
               	  <label for="vpn2" class="radio-inline">
                  <input type="radio" name="VPN" id="vpn2" value="1" checked>支持小流量</label>
              </c:if>
                 
              </div>	
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red"></span>
                </p>
              </div>
            </div>
            <div class="form-group" style="border-bottom: 1px solid #EFF2F7;padding-bottom: 30px;margin-bottom:30px;">
              <label class="col-sm-3 control-label">IMEI：</label>
              <div class="col-sm-6">
                <c:if test="${Model.IMEI eq 0}">
                	<input type="text" name="IMEI" value="0" maxlength="15" data-popover-offset="0,8" class="form-control">
                </c:if>
                 <c:if test="${Model.IMEI ne 0}">
                	<input type="text" name="IMEI" value="0" maxlength="15" data-popover-offset="0,8" class="form-control">
                </c:if>
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red">15位数字,0或不填表示空</span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-3 control-label">手动选网：</label>
              <div class="col-sm-6">
                <input type="text" name="selnet" maxlength="20" value="${Model.selnet}" data-popover-offset="0,8" class="form-control">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red">(空字符串表示不手动选网)</span>
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
	          <label class="col-md-3 control-label">套餐名称：</label>
	          <div class="col-md-6">
	            <input type="text" name="planType" value="${Model.planType}" data-popover-offset="0,8" required maxlength="50" 
	            class="form-control">
	          </div>
	          <div class="col-md-3">
	            <p class="form-control-static"><span class="red">* SIM卡流量套餐, 格式形如: 3G/30Days/189当地货币</span>
	            </p>
	          </div>
	        </div>
	        <div class="form-group">
                <label class="col-md-3 control-label">套餐金额：</label>
                <div class="col-md-6">
                <c:if test="${Model.IMSI eq null}"><%-- 通过必选字符串字段为null判断为添加记录 --%>
    <input type="text" name="planPrice" value="" data-popover-offset="0,8" required class="form-control"></c:if><c:if test="${Model.IMSI ne null}">
    <input type="text" name="planPrice" value="${Model.planPrice}" data-popover-offset="0,8" required class="form-control"></c:if>
                    
                </div>
                <div class="col-md-3">
                    <p class="form-control-static">
                        <span class="red">* 单位: 元 以RMB人民币值填写</span>
                    </p>
                </div>
            </div>
	        <div class="form-group">
	          <label class="col-md-3 control-label">套餐包含流量：</label>
	          <div class="col-md-6">
	            <div class="input-group">
	              <div id="planData_pretty" class="input-group-addon">&nbsp;</div><input type="text" name="planData" value="${Model.planData}" data-popover-offset="0,58"
	              required class="form-control">
	              <div class="input-group-addon">KB</div>
	            </div>
	          </div>
	          <div class="col-md-3">
	            <p class="form-control-static"><a class="btn btn-success btn-xs" onclick="showByteUnitPicker('input[name=\'planData\']');"><span>计算器</span></a> <span class="red">* 默认1GB 其中1GB = 1024MB,1MB = 1024KB</span>
	            </p>
	          </div>
	        </div>
	        <div class="form-group">
	          <label class="col-md-3 control-label">套餐剩余流量：</label>
	          <div class="col-md-6">
	            <div class="input-group">
	            <c:if test="${Model.IMSI eq null}"><%-- 通过必选字符串字段为null判断为新记录.新记录则清空值,以符合设定 --%>
	            <div id="planRemainData_pretty" class="input-group-addon">&nbsp;</div><input id="Remain_Data" type="text" name="planRemainData" data-popover-offset="0,58" value=""
                  class="form-control"></c:if>
	            <c:if test="${Model.IMSI ne null}"><%-- 通过必选字符串字段为null判断为新记录.新记录则清空值,以符合设定 --%>
                <div id="planRemainData_pretty" class="input-group-addon">&nbsp;</div><input id="Remain_Data" type="text" name="planRemainData" data-popover-offset="0,58" value="${Model.planRemainData}"
                  class="form-control"></c:if>
	              <div class="input-group-addon">KB</div>
	            </div>
	          </div>
	          <div class="col-md-3">
	            <p class="form-control-static"><a class="btn btn-success btn-xs" onclick="showByteUnitPicker('input[name=\'planRemainData\']');"><span>计算器</span></a> <span class="red">如果留空, 则默认等于套餐流量大小.如果编辑此项小于100M,会自动停用该卡 <span class="text-info">注意"0"值与留空系不同意义</span></span>
	            </p>
	          </div>
	        </div>
	        <div style="display: none;" class="form-group">
	          <label for="plan_activateMob" class="col-md-3 control-label">套餐激活电话/代码：</label>
	          <div class="col-md-6">
	            <input type="text" name="planActivateCode" maxlength="300" value="${Model.planActivateCode}" data-popover-offset="0,8" class="form-control">
	          </div>
	          <div class="col-md-3">
                    <p class="form-control-static">
                        <span class="red">* 填写规范：USSD:*101# ,SMS:中心号码@短信内容 10010@CXLL</span>
                    </p>
                </div>
	        </div>
	        <div style="display: none;" class="form-group">
	          <label class="col-sm-3 control-label">套餐是否已激活：</label>
	          <div class="col-sm-6">
	            <c:if test="${Model.planIfActivated eq '否'}"><label for="plan_activate0" class="radio-inline">
	              <input type="radio" name="planIfActivated" id="plan_activate0" value="否" checked>否</label>
	            <label for="plan_activate1" class="radio-inline">
	              <input type="radio" name="planIfActivated" id="plan_activate1" value="是">是</label>
	            </c:if>
                <c:if test="${Model.planIfActivated eq '是'}">
                <label for="plan_activate0" class="radio-inline">
                  <input type="radio" name="planIfActivated" id="plan_activate0" value="否">否</label>
                <label for="plan_activate1" class="radio-inline">
                  <input type="radio" name="planIfActivated" id="plan_activate1" value="是" checked>是</label>
                </c:if>
	          </div>
	          
	          <div class="col-sm-3">
	            <p class="form-control-static"><span class="red"> </span>
	            </p>
	          </div>
	        </div>    <div style="display: none;" class="form-group">
	          <label class="col-sm-3 control-label">套餐是否已激活：</label>
	          <div class="col-sm-6">
	            <c:if test="${Model.planIfActivated eq '否'}"><label for="plan_activate0" class="radio-inline">
	              <input type="radio" name="planIfActivated" id="plan_activate0" value="否" checked>否</label>
	            <label for="plan_activate1" class="radio-inline">
	              <input type="radio" name="planIfActivated" id="plan_activate1" value="是">是</label>
	            </c:if>
                <c:if test="${Model.planIfActivated eq '是'}"><label for="plan_activate0" class="radio-inline">
                  <input type="radio" name="planIfActivated" id="plan_activate0" value="否">否</label>
                <label for="plan_activate1" class="radio-inline">
                  <input type="radio" name="planIfActivated" id="plan_activate1" value="是" checked>是</label>
                </c:if>
	          </div>
	          
	          <div class="col-sm-3">
	            <p class="form-control-static"><span class="red"> </span>
	            </p>
	          </div>
	        </div>
	        <div style="display: none;" class="form-group">
	          <label class="col-md-3 control-label">套餐激活日期：</label>
	          <div class="col-md-6">
	            <div class="inputgroup">
	              <input type="text" name="planActivateDate" value="${Model.planActivateDate}" data-popover-offset="0,8"
	              class="form_datetime form-control">
	            </div>
	          </div>
	          <div class="col-md-3">
	            <p class="form-control-static"><span class="red">所有日期字段编辑时若需要可先清空</span>
	            </p>
	          </div>
	        </div>
	        <div style="display: none;" class="form-group" style="border-bottom: 1px solid #EFF2F7;padding-bottom: 30px;margin-bottom:30px;">
	          <label class="col-md-3 control-label">套餐到期日期：</label>
	          <div class="col-md-6">
	            <div class="inputgroup">
	              <input type="text" name="planEndDate" value="${Model.planEndDate}" data-popover-offset="0,8"
	              class="form_datetime form-control">
	            </div>
	          </div>
	          <div class="col-md-3">
	            <p class="form-control-static"><span class="red">套餐的有效期随着充值会更新,延后</span>
	            </p>
	          </div>
	        </div>
            <div class="form-group" >
                <label class="col-md-3 control-label">卡内初始余额：</label>
                <div class="col-md-6">
                    <input type="text" name="cardInitialBalance" value="${Model.cardInitialBalance}" data-popover-offset="0,8" class="form-control">
                </div>
                <div class="col-md-3">
                    <p class="form-control-static">
                        <span class="red">单位: 元 以RMB人民币值填写 <span class="text-info">请输入准确的余额, 充值相关功能才正常使用</span></span>
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
	        <div class="form-group">
              <label class="col-sm-3 control-label">SIM卡/套餐激活代码：</label>
              <div class="col-sm-6">
                <input type="text" name="simActivateCode" maxlength="300" value="${Model.simActivateCode}" data-popover-offset="0,8" class="form-control">
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red">* 填写规范：USSD:*101# ,SMS:中心号码@短信内容 10010@CXLL </span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label class="col-sm-3 control-label">SIM卡/套餐是否已激活：</label>
              <div class="col-sm-6">
                
                  
                <c:if test="${Model.SIMIfActivated eq '否'}"><label for="SIMIfActivated0" class="radio-inline">
                  <input type="radio" name="SIMIfActivated" id="SIMIfActivated0" value="否" checked>否</label>                  
                <label for="SIMIfActivated1" class="radio-inline">
                  <input type="radio" name="SIMIfActivated" id="SIMIfActivated1" value="是">是</label>
                </c:if>
                <c:if test="${Model.SIMIfActivated eq '是'}"><label for="SIMIfActivated0" class="radio-inline">
                  <input type="radio" name="SIMIfActivated" id="SIMIfActivated0" value="否">否</label>                  
                <label for="SIMIfActivated1" class="radio-inline">
                  <input type="radio" name="SIMIfActivated" id="SIMIfActivated1" value="是" checked>是</label>
                </c:if>
              </div>
              <div class="col-sm-3">
                <p class="form-control-static"><span class="red"></span>
                </p>
              </div>
            </div>
              
            <div class="form-group">
              <label class="col-md-3 control-label">卡开启日期：</label>
              <div class="col-md-6">
                <div class="inputgroup">
                  <input type="text" name="SIMActivateDate" value="${Model.SIMActivateDate}" data-popover-offset="0,8"
	              class="form_datetime form-control">
	            </div>
	          </div>
	          <div class="col-md-3">
	            <p class="form-control-static"><span class="red">注意：对于停用的卡到开启日期会自动将状态改为可用</span>
	            </p>
	          </div>
	        </div>
	        <div class="form-group">
              <label class="col-md-3 control-label">卡到期日期：</label>
              <div class="col-md-6">
                <div class="inputgroup">
                  <input type="text" name="SIMEndDate" value="${Model.SIMEndDate}" data-popover-offset="0,8"
                  class="form_datetime form-control">
                </div>
              </div>
              <div class="col-md-3">
                <p class="form-control-static"><span class="red">注意：对于可用或使用中的卡到有效日期会自动将状态改为停用</span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3 control-label">卡处理类型：</label>
              <div class="col-md-6">
                <div class="inputgroup">
                  <select name="handleType" id="handleType" class="form-control">
                    <option value="">无</option>
                    <c:forEach items="${SimHandleTypeDict}" var="SimHandleType" varStatus="status">
                        <option value="${SimHandleType.label}" <c:if test="${Model.handleType==SimHandleType.label}">selected="selected"</c:if>>${SimHandleType.label}</option>
                    </c:forEach>
                  </select>
                </div>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3 control-label">卡处理配置：</label>
              <div class="col-md-6">
                <div class="inputgroup">
                  <input value="${Model.handleConfig}" id="handleConfig" name="handleConfig" class="form-control"/>
                </div>
              </div>
              <div class="col-md-3">
                <p class="form-control-static"><span class="red">自激活：例如有效期7天自然日，则输入7-0.有效期10天按小时，则输入10-1
                <br/>自续费：待定</span>
                </p>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3 control-label">限速策略：</label>
              <div class="col-md-6">
                <div class="inputgroup">
                  <input value="${Model.limitSpeedStr}" id="limitSpeedStr" name="limitSpeedStr" class="form-control"/>
                </div>
              </div>
<!--               <div class="col-md-3"> -->
<!--                 <p class="form-control-static"><span class="red">自激活：例如有效期7天自然日，则输入7-0.有效期10天按小时，则输入10-1 -->
<!--                 <br/>自续费：待定</span> -->
<!--                 </p> -->
<!--               </div> -->
            </div>            
            <div class="form-group">
              <label for="queryMethod" class="col-sm-3 control-label">余额/流量操作方法说明：</label>
              <div class="col-sm-6">
                <textarea id="queryMethod" rows="5" name="queryMethod" maxlength="500" data-popover-offset="0,8"
                class="form-control">${Model.queryMethod}</textarea>
              </div>
              <div class="col-md-3">
                <p class="form-control-static"><span class="red">例如查询的方法, 充值的方法等说明</span>
                </p>
              </div>
            </div>
<%--            <div class="form-group">
              <label for="rechargeMethod" class="col-sm-3 control-label">余额/流量 充值方法说明：</label>
              <div class="col-sm-6">
                <textarea id="rechargeMethod" rows="3" name="rechargeMethod" data-popover-offset="0,8"
                class="form-control">${Model.rechargeMethod}</textarea>
              </div>
            </div> --%>
	        <div class="form-group">
	          <label for="sim_desc" class="col-sm-3 control-label">备注：</label>
	          <div class="col-sm-6">
	            <textarea id="sim_desc" rows="3" name="remark" maxlength="499" data-popover-offset="0,8"
	            class="form-control">${Model.remark}</textarea>
	          </div>
	          <div class="col-md-3">
                    <p class="form-control-static">
                        <span class="red">填写规范：序号、日期+原因+操作--操作人<span class="text-info"></span></span>
                    </p>
                </div>
	        </div>
	        <div class="form-group">
              <label for="notes" class="col-sm-3 control-label">标注：</label>
              <div class="col-sm-6">
                <textarea id="notes" rows="3" name="notes" maxlength="254" data-popover-offset="0,8"
                class="form-control">${Model.notes}</textarea>
              </div>
              <div class="col-md-3">
                    <p class="form-control-static">
                        <span class="red">SIM卡列表首页可添加/修改标注<span class="text-info"></span></span>
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
    <script src="<%=basePath %>static/js/byteunit.js"></script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
    <script>
    $(function () {
    	if($("input[name='countryList']:checked").length <=0){
    		$(".mnc1").css({'display':'none'});
    	}
    	$(".checkbox-items").click(function(){
    		$(".mnc1").css({'display':'block'});
    		if($("input[name='countryList']:checked").length <=0){
        		$(".mnc1").css({'display':'none'});
        	}
    	});
    	bootbox.setDefaults("locale","zh_CN");
    	
    $(".form_datetime").datetimepicker({
        format: 'YYYY-MM-DD HH:00',
        pickDate: true,     //en/disables the date picker
        pickTime: true,     //en/disables the time picker
        showToday: true,    //shows the today indicator
        language:'zh-CN',   //sets language locale
        defaultDate: null, // moment().add(7, 'days'), //sets a default date, accepts js dates, strings and moment objects
    });
    
    $('input[name="planData"]').keyup(function(){
        var size = $.trim($('input[name="planData"]').val());
        if(size.length > 0) {
            $('#planData_pretty').empty().append(prettyByteUnitSize(size,2,1,true));
        } else {
            $('#planData_pretty').empty().append("&nbsp;");
        }
    });
    $('#planData_pretty').empty().append(prettyByteUnitSize($('input[name="planData"]').val(),2,1,true));
    $('input[name="planRemainData"]').keyup(function(){
        var size = $.trim($('input[name="planRemainData"]').val());
        if(size.length > 0) {
            $('#planRemainData_pretty').empty().append(prettyByteUnitSize(size,2,1,true));
        } else {
            $('#planRemainData_pretty').empty().append("&nbsp;");
        }
    });
    $('#planRemainData_pretty').empty().append(prettyByteUnitSize($('input[name="planRemainData"]').val(),2,1,true));
});</script>
    <script type="text/javascript">
    $(function(){    
    $("#edit_form").validate_popover({
        rules:{
            'countryList':{ required:true },
            'IMSI':{ required:true,number:true,minlength:18,maxlength:18 },
            'speedLimit':{ number:true,max:9999 },
            'PIN':{ number:true },
            'PUK':{ number:true },
            'planPrice':{required:true,number:true,min:0}, // 最小值为零,因为有可能系免费套餐
            'planData':{ number:true },
            'planRemainData':{ number:true },
            'cardInitialBalance':{ number:true },
            'cardBalance':{ number:true },
            'selnet':{ maxlength:20 },
            'IMEI':{ minlength:0,maxlength:15,number:true},
            'mnc':{required:true},
        },
        messages:{
            'countryList':{required:"请选择国家"},
            'IMSI':{required:"IMSI是18位数字",number:"IMSI是18位数字",minlength:"IMSI是18位数字",maxlength:"IMSI是18位数字"},
            'speedLimit':{number:"请输入数字",max:"最大值 9999 KB/S"},
            'PIN':{number:"请输入数字"},
            'PUK':{number:"请输入数字"},
            'planPrice':{required:"必填字段",number:"请输入数字"},
            'planData':{number:"请输入数字"},
            'planRemainData':{number:"请输入数字"},
            'cardInitialBalance':{number:"请输入数字"},
            'cardBalance':{number:"请输入数字"},
            'selnet':{ max:"最大长度20" },
            'IMEI':{maxlength:"15位数字,不填或填0都表示空",number:"IMEI是15位数字"},
            'mnc':{required:"请选择使用地区"}
        },
        submitHandler: function(form){
	        $("#serverIP").val($("#SIMServerID").find("option:selected").text());
	        var planRemainDataValue = $.trim($("input[name='planRemainData']").val());
            if(planRemainDataValue == null || planRemainDataValue.length == 0){
               $("input[name='planRemainData']").val($("input[name='planData']").val());
            }
            if(planRemainDataValue.indexOf(".")>=0){
            	 easy2go.toast('warn',"套餐剩余流量的KB值不能出现小数,请将后面的小数去掉");
            	 return;
            }
	        $.ajax({
	            type:"POST",
	            url:"<%=basePath %>sim/siminfo/save",
	            dataType:"html",
	            data:$('#edit_form').serialize(),
	            success:function(data){
	                result = jQuery.parseJSON(data);
	                if (result.code == 0) { // 成功保存
	                    easy2go.toast('info', result.msg);
	                   <%-- var redirect = "<%=request.getHeader("Referer") %>"; --%>
	                   <%--  window.location.href="<%=basePath %>sim/siminfo/index"; --%>
	                   history.go(-1);
	                   if(redirect==null) {
	                     window.location.href="<%=basePath %>sim/siminfo/index";
	                   } else {
	                     window.location.href=redirect;
	                   }
	                } else {
	                    easy2go.toast('error', result.msg);
	                }
	            }
	        });
        }
    });
    $('select[name="MCC"]').change(function(){
        $("input[name='countryList']").removeProp("checked"); // 每当更改发行国家先全部清空
        $("input[name='countryList'][value=" + $(this).val() + "]").prop("checked", true);
    });

	});
    
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
                    	<%-- 目前设定输入框前缀的addon div与input框总相邻 --%>
                        $(selector).prev().empty().append(prettyByteUnitSize($("#kb-result").val(),2,1,true));
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
    </script>
	
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>