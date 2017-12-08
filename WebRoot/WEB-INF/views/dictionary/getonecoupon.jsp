<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>免费领取途狗优惠券</title>
  <link rel="stylesheet" media="screen"   href="<%=basePath %>/static/css/moblieDevice.css">
  <script type="text/Javascript" src="<%=basePath %>static/js/jquery-1.8.0.min.js"></script>
  <script type="text/Javascript" src="<%=basePath %>static/js/tgjs.js"></script>
  <link rel="stylesheet" href="<%=basePath %>static/css/toastr.css">
  <script type="text/Javascript" src="<%=basePath %>static/js/easy2gotime/My97DatePicker/WdatePicker.js"></script>  
   
  <script src="<%=basePath %>static/js/xiala/MultiSelectDropList.js"></script>
  <link href="<%=basePath %>static/js/xiala/multi.css" rel="stylesheet" type="text/css" />

  <script type="text/Javascript" src="<%=basePath %>static/js/toastr.min.js"></script>
  <script type="text/javascript">window.NREUM||(NREUM={});NREUM.info={"beacon":"bam.nr-data.net","errorBeacon":"bam.nr-data.net","licenseKey":"383240907d","applicationID":"4830918","transactionName":"dQlXRRFcDQ9SEE1DQFQKUEILVgU8UQ0QXkYZFVFeFA==","queueTime":1,"applicationTime":55,"agent":"js-agent.newrelic.com/nr-768.min.js"}</script>
  <script type="text/javascript">window.NREUM||(NREUM={}),__nr_require=function(e,n,t){function r(t){if(!n[t]){var o=n[t]={exports:{}};e[t][0].call(o.exports,function(n){var o=e[t][1][n];return r(o||n)},o,o.exports)}return n[t].exports}if("function"==typeof __nr_require)return __nr_require;for(var o=0;o<t.length;o++)r(t[o]);return r}({QJf3ax:[function(e,n){function t(e){function n(n,t,a){e&&e(n,t,a),a||(a={});for(var u=c(n),f=u.length,s=i(a,o,r),p=0;f>p;p++)u[p].apply(s,t);return s}function a(e,n){f[e]=c(e).concat(n)}function c(e){return f[e]||[]}function u(){return t(n)}var f={};return{on:a,emit:n,create:u,listeners:c,_events:f}}function r(){return{}}var o="nr@context",i=e("gos");n.exports=t()},{gos:"7eSDFh"}],ee:[function(e,n){n.exports=e("QJf3ax")},{}],3:[function(e,n){function t(e){return function(){r(e,[(new Date).getTime()].concat(i(arguments)))}}var r=e("handle"),o=e(1),i=e(2);"undefined"==typeof window.newrelic&&(newrelic=window.NREUM);var a=["setPageViewName","addPageAction","setCustomAttribute","finished","addToTrace","inlineHit","noticeError"];o(a,function(e,n){window.NREUM[n]=t("api-"+n)}),n.exports=window.NREUM},{1:12,2:13,handle:"D5DuLP"}],gos:[function(e,n){n.exports=e("7eSDFh")},{}],"7eSDFh":[function(e,n){function t(e,n,t){if(r.call(e,n))return e[n];var o=t();if(Object.defineProperty&&Object.keys)try{return Object.defineProperty(e,n,{value:o,writable:!0,enumerable:!1}),o}catch(i){}return e[n]=o,o}var r=Object.prototype.hasOwnProperty;n.exports=t},{}],D5DuLP:[function(e,n){function t(e,n,t){return r.listeners(e).length?r.emit(e,n,t):void(r.q&&(r.q[e]||(r.q[e]=[]),r.q[e].push(n)))}var r=e("ee").create();n.exports=t,t.ee=r,r.q={}},{ee:"QJf3ax"}],handle:[function(e,n){n.exports=e("D5DuLP")},{}],XL7HBI:[function(e,n){function t(e){var n=typeof e;return!e||"object"!==n&&"function"!==n?-1:e===window?0:i(e,o,function(){return r++})}var r=1,o="nr@id",i=e("gos");n.exports=t},{gos:"7eSDFh"}],id:[function(e,n){n.exports=e("XL7HBI")},{}],G9z0Bl:[function(e,n){function t(){var e=d.info=NREUM.info,n=f.getElementsByTagName("script")[0];if(e&&e.licenseKey&&e.applicationID&&n){c(p,function(n,t){n in e||(e[n]=t)});var t="https"===s.split(":")[0]||e.sslForHttp;d.proto=t?"https://":"http://",a("mark",["onload",i()]);var r=f.createElement("script");r.src=d.proto+e.agent,n.parentNode.insertBefore(r,n)}}function r(){"complete"===f.readyState&&o()}function o(){a("mark",["domContent",i()])}function i(){return(new Date).getTime()}var a=e("handle"),c=e(1),u=window,f=u.document;e(2);var s=(""+location).split("?")[0],p={beacon:"bam.nr-data.net",errorBeacon:"bam.nr-data.net",agent:"js-agent.newrelic.com/nr-768.min.js"},d=n.exports={offset:i(),origin:s,features:{}};f.addEventListener?(f.addEventListener("DOMContentLoaded",o,!1),u.addEventListener("load",t,!1)):(f.attachEvent("onreadystatechange",r),u.attachEvent("onload",t)),a("mark",["firstbyte",i()])},{1:12,2:3,handle:"D5DuLP"}],loader:[function(e,n){n.exports=e("G9z0Bl")},{}],12:[function(e,n){function t(e,n){var t=[],o="",i=0;for(o in e)r.call(e,o)&&(t[i]=n(o,e[o]),i+=1);return t}var r=Object.prototype.hasOwnProperty;n.exports=t},{}],13:[function(e,n){function t(e,n,t){n||(n=0),"undefined"==typeof t&&(t=e?e.length:0);for(var r=-1,o=t-n||0,i=Array(0>o?0:o);++r<o;)i[r]=e[n+r];return i}n.exports=t},{}]},{},["G9z0Bl"]);</script>
<meta name="description" content="途狗wifi是一款依托于高科技技术的全球漫游产品，产品集小巧，待机时间长，安全等性能于一身，专为有随身wifi需求的人士打造。&lt;p&gt;&lt;strong&gt;&lt;/strong&gt;&lt;/p&gt;
&lt;section class=&quot;tn-Powered-by-XIUMI&quot;&gt;
&lt;...">
<script type="text/Javascript" src="<%=basePath %>static/js/pop.js"></script>
 
  <script src="https://dn-jinshuju-assets.qbox.me/assets/published_forms/application-7f18d880ebc88ee4569448c499c45e10.js"></script>
</head>
<body class="entry-container" style="">
<form class="center" id="new_entry" action="" accept-charset="UTF-8" method="post">
<%--    style="background-image: url('<%=basePath %>static/images/yhqbk01.jpg');" --%>
  <div style="padding-top:0px;margin-top:0px;margin-right:auto;margin-left:auto;max-width:700px;font-family:sans-serif;padding-bottom:20px;">
<!--     <div style="background-color:#2ec5bb"> -->
<!--           <span> -->
<!--              <img src="http://www.easy2go.cn/static/images/logo2.png"/> -->
<!--           </span> -->
<!--     <div class="qrcode-box gd-hide"></div> -->
<!--     </div>  -->
    
<!--     <div style="font-size:24px;margin-left:30px;margin-top:20px;margin-bottom:10px;"> -->
    <div style="width:100%;height:60px;margin-bottom:10px;">
      <img src="<%=basePath %>static/images/lingqu.jpg"  style="width:100%;height:60px;"/>
    </div>
    
  <c:forEach var="listc" items="${listCoupon}" varStatus="status">
    <div class="listyjq" onclick="return checkPhone('${listc.couponVesion}')" id="${listc.couponVesion}" style="margin:0 auto;width:95%;text-align:center;height:90px;margin-bottom:20px;background-color:#FBB20E;margin-bottom:10px;">
      <div style="width:100%;text-align:center;height:70px;">
             <img src="<%=basePath %>static/images/yhuiquan02.jpg" style="width:100%;height:70px;"/>       
      </div>
      <div style="font-size:12px;float:left;height:20px;line-height:20px;padding-left:20px;">${listc.couponTitle}</div>
      <div style="font-size:12px;float:right;height:20px;line-height:20px;padding-right:45px;">仅剩 【${listc.couponStatus}】个！</div>
    </div>
  </c:forEach>
    
  <script type="text/javascript">
     function checkPhone(versioncou){
    	 bootbox.dialog({
    		   message:'<input id="ckphone" style="font-size:24px;border:1px solid gray;width:100%;text-align:center;height:40px;line-height:40px;"></input><br/>',
    		   title: "请输入您的手机号码",
    		   buttons: {
    		      main: {
    		    label: "&nbsp;&nbsp;立&nbsp;&nbsp;即&nbsp;&nbsp;领&nbsp;&nbsp; 取&nbsp;&nbsp;",
    		    className: "btn-primary",
    		    callback: function() {
    		    	      
    		    	var phone = /^1([38]\d|4[57]|5[0-35-9]|7[06-8]|8[89])\d{8}$/;
    		    	if(!phone.test($("#ckphone").val()))
    		    	{
    		    		easy2go.toast('info', "请输入正确的手机号码！");
    		    		document.getElementById('ckphone').focus();
    		    		return false;
    		    	}else{
    		    	     $.ajax({
    						async: false, 
    						type : "POST",
    						url : "<%=basePath %>coupon/getthisonecoupon?phone="+document.getElementById('ckphone').value+"&couponVesion="+versioncou,
    						dataType : "html",
    						success : function(data) {
    						    msg = data;
    						    if(data=="1"){//提交退款申请
//     						    	alert("领取成功！");
    						    	bootbox.alert("恭喜您,领取成功！", function() {
    						    	  location.reload();
    						    	});
    						    }
    						    else{
    						    	easy2go.toast('info', msg);
    						    }
    						}});
    		    	 }
    		        }
    		      }
    		   }
    		});
     }
  </script>   
    
  </div>    
 
 
 
</form>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath%>static/js/bootbox.min.js"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <script src="<%=basePath %>static/js/accounting/accounting.min.js"></script>
  </body>
</html>