<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- 格式化时间 -->
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	int Spagesize=20;
	if(request.getSession().getAttribute("nowpagesize")==null){

	}else{
		Spagesize=Integer.parseInt(request.getSession().getAttribute("nowpagesize").toString());
	}
%>
<!DOCTYPE html>
<html>
<head>
<title>升级上传-流量运营中心</title>
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
<style type="text/css">
.form {
	width: 300px;
	margin-top: 20px;
}
</style>
</head>
<body>

	<section id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
		<SECTION id="main-content">
			<SECTION class="wrapper">
				<DIV class="col-md-12">
					<DIV class="panel panel-body">
						<form id="import_form" class="form-horizontal" action="<%=basePath%>remote/uploadding" method="post" enctype="multipart/form-data">


							<div class="form-group">
							<div class="col-sm-6">

									<input type="file" id="file" name="file"
										 required class="form-control">
								</div>
									<div class="btn-toolbar">
										<INPUT id="firmWareversion" placeholder="版本号，必填"  class="client form-control" name="firmWareversion" maxLength="16" type="text" data-popover-offset="0,8" style="width:12%;">
										&nbsp;&nbsp;&nbsp;&nbsp;<button type="submit" id="batchsubmit" onclick="unlockScreen();" class="btn btn-primary">上传</button>
										<button type="button" onclick="javascript:history.go(-1);"
											class="btn btn-default">返回</button>
											<LABEL class="inline-label">当前文件类型：<span style="color: red;" id="fileTypeName">暂无</span></LABEL>
										<LABEL class="inline-label">版本号：<span style="color: red;" id="fileVersion">暂无</span></LABEL>
									</div>
									<DIV>
										&nbsp;&nbsp;&nbsp;&nbsp;wifidog.conf或者wifidog_4G.conf 请修改为wifidog_conf.conf或者wifidog_conf_4G.conf后上传
									</DIV>
							</div>
						</form>
					</DIV>

				</DIV>
				<DIV class="col-md-12">
<DIV class="panel">
<!-- <DIV class="panel-heading">当前在线设备</DIV> -->
<DIV class="panel-body" style="margin:0px;padding:0px;">
 <SECTION class="wrapper" style="margin-top:20px;">
<DIV class="col-md-12">
<DIV class="panel">
<DIV class="panel-body" style="padding-top:5px;padding-bottom:5px;">
   <FORM class="form-inline" id="searchForm"  method="get" action="#">
   <input type="hidden" value="" id="pagenum"/><input type="hidden" id="pagesize" value="<%=Spagesize %>" />

		<input type="hidden" value="20" id="pageSize"/>
         <DIV class="form-group">
             <LABEL class="inline-label">设备机身码：</LABEL>
             <INPUT class="form-control" name="SN" id="SN" type="text" placeholder="机身码">
         </DIV>
         <div class="form-group">
          <label class="inline-label">国&nbsp;家：</label>
          <select name="mcc" style="width: 130px;"
          class="form-control">
            <option value="">全部国家</option>
            <c:forEach items="${Countries}" var="country" varStatus="status"><option value="${country.countryCode}">${country.countryName}</option></c:forEach>
          </select>
        </div>
        <!--  <DIV class="form-group">
             <LABEL class="inline-label">在线日期：</LABEL>
             <INPUT id="order_creatorDateend" class="form-control form_datetime" name="lastTime" type="text" data-date-format="YYYY-MM-DD">
         </DIV> -->
         <DIV class="form-group">
             <BUTTON class="btn btn-primary" type="button" onclick="gridObj.options.otherParames = $('#searchForm').serializeArray();gridObj.page(1);">搜索</BUTTON>
         </DIV>
   </FORM>
</DIV>
</DIV>

<DIV class="panel">
<DIV class="panel-heading">当前在线设备</DIV>
<DIV class="panel-body">
<DIV class="table-responsive">
<TABLE id="searchTable">
  <TR>
    <TH w_render="SN" width="15%"><b>设备机身码</b></TH>
    <th w_render="IMSI" width="15%"><b>IMSI</b></th>
    <th w_render="customerName" width="10%"><b>客户姓名</b></th>
    <th w_index="lastTime" width="15%"><b>最近活动时间</b></th>
    <th w_render="remotesj"><b>远程升级</b></th>
  </TR>
</TABLE>
   </DIV>
<DIV>
</DIV></DIV></DIV></DIV>

<!-- 设备升级记录 -->
<DIV class="panel">
                       <DIV class="panel-body">
                           <FORM class="form-inline" id="leibie" method="get" action="#">
                           <input type="hidden" id="pagesizeUpload" value="10" />
                           <input type="hidden" value="1" id="pagenumUpload" />
                           <input type="hidden" value="1" name="updateSource" id="updateSource" />
                               <DIV class="form-group">
                                   <LABEL class="inline-label">机身码：</LABEL> 
                                   <input class="form-control" name="SN" id="sn_select" />
                               </DIV>
                               <DIV class="form-group">
                                   <LABEL class="inline-label">是否已升级：</LABEL> 
                                   <select class="form-control" name="ifUpdated" id="urgency_select" style="width: 150px;">
                                       <option value="">请选择</option>
                                       <option value="1">是</option>
                                       <option value="0">否</option>
                                   </select>
                               </DIV>
                               <div class="form-group">
                                    <LABEL class="inline-label">时间：</LABEL>
                                    <input type="text" id="begainTime" name="begainTime" data-popover-offset="0,8" class="form_datetime form-control"/>-
                                    <input type="text" id="endTime" name="endTime" data-popover-offset="0,8" class="form_datetime form-control"/>
                               </div>
                               <div class="form-group">
                                    <LABEL class="inline-label">创建人：</LABEL>
                                    <input type="text" id="creatorUserName" name="creatorUserName" data-popover-offset="0,8" class="form-control"/>
                               </div>
                               <DIV class="form-group">
                                   <BUTTON class="btn btn-primary" type="button"
                                       onclick="gridObjUpload.options.otherParames =$('#leibie').serializeArray();gridObjUpload.page(1);">搜索</BUTTON>
                               </DIV>
                           </FORM>
                           <form id="testc"></form>
                       </DIV>
                   </DIV>
					<DIV class="panel">
						<DIV class="panel-heading">
							<H3 class="panel-title">远程升级信息</H3>
						</DIV>
						<DIV class="panel-body">
							<table id="searchTableUpload">
								<tr>
									<th w_render="deviceUpgradingID" width="4%;"><input id="selectAll" type="checkbox" value="全选" />全选</th>
									<th w_render="render_SN" width="16%;">机身码</th>
									<th w_index="upgradeFileType" width="16%;">文件升级类型</th>
									<th w_render="ifForcedToUpgrade" width="16%;">是否下发成功</th>
									<th w_render="ifUpdated" width="16%;">是否已升级</th>
									<th w_index="updateDate" width="16%;">升级时间</th>
                                    <th w_index="creatorUserName" width="16%;">创建人</th>
                                    <th w_render="creatorDate" width="16%;">创建时间</th>
									<th w_index="remark" width="16%;">备注</th>
								</tr>
							</table>
						</DIV>
					</DIV>


<!-- 设备升级记录 -->


			</SECTION></DIV></DIV></DIV>
		</SECTION>
	</section></section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
	<script type="text/javascript">
	var fileTypeString="暂无";
	var newVersion="暂无";
	var filetip="";
	var temp=${temp==""?"-2":""+temp};
	if (temp == "1") {
		easy2go.toast('info', "上传成功");
		  fileTypeString='${fileTypeString}';
		 newVersion='${newVersion}';
		$("#fileTypeName").html(fileTypeString);
		$("#fileVersion").html(newVersion);
		filetip="升级类型:"+fileTypeString+",版本号:"+newVersion;
	}
	if (temp == "0") {
		easy2go.toast('warn', "上传失败");
	}
	if (temp == "-1") {
		easy2go.toast('warn', "选择的文件不正确，请重新选择");
	}
	 bootbox.setDefaults("locale","zh_CN");
		var v;
		var Name;
		var ps='';
	    window.setInterval(function(){
	    	pull();
	    },1000);
	    var pathurl='<%=basePath %>service/center/nowlistlogs';
	    if('${sn}'!=null &&'${sn}'!=''){
	    	pathurl+='?SN=${sn}';
	    }
	    var gridObj;
		$(function(){
			   var pagesize=parseInt($("#pagesize").val());
		       gridObj = $.fn.bsgrid.init('searchTable',{
		           url:pathurl,
		           pageSizeSelect: true,
		           pageSize:pagesize,
		           pageSizeForGrid:[10,20,30,50,100],
		           isProcessLockScreen:false,
		           multiSort:true,
		           autoLoad:false,
		           otherParames:$('#searchForm').serializeArray(),
		           additionalAfterRenderGrid: function(parseSuccess, gridData, options) {
		          	 if(parseSuccess){
		          		 $("#pagenum").val(options.curPage);
		        		 $("#pageSize").val($("#searchTable_pt_pageSize").val());

		          	 }
		           }
		       });
		       if($("#pagenum").val()!=""){
		    	   gridObj.page($("#pagenum").val());
		       }else{
		    	   gridObj.page(1);
		       }
			//设备升级
		       var pagesizeUpload=parseInt($("#pagesizeUpload").val());

		    	gridObjUpload = $.fn.bsgrid.init('searchTableUpload',{
		       		url:'<%=basePath%>device/deviceupgradelist',
		       	    pageSizeSelect: true,
		            pageSize:pagesizeUpload,
		            autoLoad:false,
		            otherParames:$('#leibie').serializeArray(),
		            pageSizeForGrid:[15,30,50,100],
		            displayPagingToolbarOnlyMultiPages:true,
				    additionalAfterRenderGrid: function(parseSuccess, gridData, options) {
						 if(parseSuccess){
			        		 $("#pagenumUpload").val(options.curPage);
			        		 $("#pagesizeUpload").val( $("#searchTable_pt_pageSize").val());
			        	 }
		      		 },
			    });
		    	
		    	if($("#pagenumUpload").val()!=""){
		     	   gridObjUpload.page($("#pagenumUpload").val());
		        }else{
		     	   gridObjUpload.page(1);
		        };
		        
		    //设备升级
		       
		       $("#order_creatorDateend").datetimepicker({
		     		pickDate: true,                 //en/disables the date picker
		     		pickTime: true,                 //en/disables the time picker
		     		showToday: true,                 //shows the today indicator
		     		language:'zh-CN',                  //sets language locale
//		      		defaultDate: moment().add(0, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
		     	});
		 	 $("#batchsubmit").click(function(){
				if($("#firmWareversion").val() == null || $("#firmWareversion").val()==""){
					easy2go.toast('warn', "请填写设备号!");
		    		return false;
				}
			});
			$("#logistics").change(function(){

				var apk=$("#logistics").find("option:selected").val();
				$.ajax({
					type : "GET",
					url : "<%=basePath%>remote/selectchange?apkName="+apk,
					dataType : "text",
					success : function(data) {
						var txt=  $("#versionnn").val();
						$("#versionnn").val(data);
						v=$("#versionnn").val();
						Name=$("#logistics  option:selected").val();
					},
					error:function(){
					}
				});
			});

		});
	//设备升级记录
	function creatorDate(record, rowIndex, colIndex, options){
		return record.creatorDate.substr(0,19);
	}
	
   function deviceUpgradingID(record, rowIndex, colIndex, options){
	   return "<input type='checkbox' name='deviceUpgradingID' value='"+record.deviceUpgradingID+"'>";
   }

   function ifForcedToUpgrade(record, rowIndex, colIndex, options){
	   if(record.ifForcedToUpgrade==true){
		   return "是";
	   }else{
		   return "否";
	   }
   }

   function ifUpdated(record, rowIndex, colIndex, options){
       if(record.ifUpdated==1){
           return "是";
       }else{
           return "否";
       }
   }

   function render_SN(record, rowIndex, colIndex, options){
	   return '<A  href="<%=basePath%>service/center/device/today?SN='+record.SN+'">'+record.SN+'</A>';;
   }
	
	//设备升级记录
	

		// 远程升级
	    function remotesj(record,rowIndex,colIndex,options){
	      	return "<form class='form-inline' action='<%=basePath %>remote/remoteUpgrade'>"+
	    	"<span class='devFileType'>"+filetip+"</span>"+
	    	"<input type='hidden' name='sn' value='"+record.SN+"' /><button type='button' onclick='os(this)' class='btn btn-primary'>升级</button></form>";
	    }
	    function SN(record, rowIndex, colIndex, options){

	    	return '<A style="color:#1FB5AD;" href="<%=basePath %>service/center/devicelogs?sbid='+record.SN+'&sj=">'+record.SN+'</A>';
	   	}

	    function customerName(record, rowIndex, colIndex, options){
	    	var cusType = record.customerType;
	    	if(cusType=="重要客户"){
	        	return '<A style="color:#1FB5AD;" href="<%=basePath %>customer/customerInfolist/customerInfoDetail?cusid='+record.customerID+'">'+record.customerName+'</A> <img style="width:16px; height:16 px;" src="<%=basePath %>static/css/images/vip.jpg"/>';

	    	}else if(cusType=="合作伙伴"){
	        	return '<A style="color:#1FB5AD;" href="<%=basePath %>customer/customerInfolist/customerInfoDetail?cusid='+record.customerID+'">'+record.customerName+'</A> <img style="width:16px; height:16px;" src="<%=basePath %>static/css/images/gj.jpg"/>';
	    	} else{//普通客户
	        	return '<A style="color:#1FB5AD;" href="<%=basePath %>customer/customerInfolist/customerInfoDetail?cusid='+record.customerID+'">'+record.customerName+'</A>';
	    	}
	    }
	    function IMSI(record, rowIndex, colIndex, options){
	    	if(record.type=='00'){
	    		if(record.SIMAllot==0){
	    			return '<A style="color:#1FB5AD;" href="<%=basePath %>sim/siminfo/view/' + record.SIMID + '">'+record.IMSI+'</A>';
	    		}else if(record.SIMAllot==3){
	        		return '登陆失败,<a  class="label label-danger label-xs" href="<%=basePath %>orders/flowdealorders/list?SN='+record.SN+'"><span>没有有效订单</span></a>';
	        	}else if(record.SIMAllot==4){
	        		return '登陆失败, <a  class="label label-danger label-xs" href="<%=basePath %>sim/siminfo/index?MCC='+record.mcc+'"><span>没有可用卡位</span></a>';
	        	}else if(record.SIMAllot==6){
	        		return '<span class="label label-danger label-xs">已发生跳转</span>';
	        	}
	    	}else{
	    		return '<A style="color:#1FB5AD;" href="<%=basePath %>sim/siminfo/view/' + record.SIMID + '">'+record.IMSI+'</A>';
	    	}
	       }

	    function pull(){
	  	  $.ajax({
	  		  type:"POST",
	  		  url:"<%=basePath %>remote/mesPush",
	  		  dataType:"html",
	  		  success:function(data){
	  			  if(data=="00"){
	  				  return;
	  			  }
	  			  if(data=="10"){
	  				  easy2go.toast('info', "日志获取成功完成!");
	  			  }else if(data=="20"){
	  				  easy2go.toast('warn', "日志获取失败!");
	  			  }else if(data=="01"){
	  				  easy2go.toast('info', "远程升级成功完成!");
	  			  }else if(data=="02"){
	  				  easy2go.toast('warn', "远程升级失败!");
	  			  }
	  		  }
	  	  });
	    }

	    function os(t){
	    	if(fileTypeString=="暂无"){
	    		easy2go.toast('warn', "请先上传升级文件!");
	    		return false;
	    	}
	    	if(newVersion=="暂无"){
	    		easy2go.toast('warn', "请填写版本号!");
	    		return false;
	    	}
	    	var type=-1;
	    	if(fileTypeString=="本地"){
	    		type=4;
	    	}else if(fileTypeString=="漫游"){
	    		type=3;
	    	}else if(fileTypeString=="漫游APK"){
	    		type=5;
	    	}else if(fileTypeString=="本地APK"){
	    		type=6;
	    	}else if(fileTypeString=="MIP"){
	    		type=7;
	    	}else if(fileTypeString=="本地Settings"){
	    		type=17;
	    	}else if(fileTypeString=="Phone.apk"){
	    		type=18;
	    	}else{
	    		switch (fileTypeString) {
					case "wifidog":type=218;break;
					case "protal":type=217;break;
					case "wifidog_conf":type=219;break;
					case "wifidog-msg":type=220;break;
					case "i-jetty-3.2-SNAPSHOT":type=221;break;
					case "ImeiTool.apk":type=222;break;
					case "gdlocationserver.apk":type=223;break;
					
					case "漫游APK_4G":type=110;break;
					case "漫游_4G":type=111;break;
					case "MIP_4G":type=112;break;
					case "Phone_4G.apk":type=113;break;
					case "本地APK_4G":type=114;break;
					case "本地Settings_4G":type=115;break;
					case "本地4G":type=116;break;
					case "wifidog_4G":type=118;break;
					case "wifidog_4G.conf":type=119;break;
					case "wifidog-msg_4G":type=120;break;
					case "i-jetty-3.2-SNAPSHOT_4G":type=121;break;
					case "ImeiTool_4G.apk":type=122;break;
					case "gdlocationserver_4G.apk":type=123;break;
					case "protal_4G":type=117;break;
					
					default:
						easy2go.toast('warn', "文件类型不正确!");
		    			return false;
				}
	    	}

	  	  //easy2go.toast('info', "命令下发");
	  	 var sn= urltovalue(0,$(t).parent().serialize());
	  	 var sn4=sn.substring(sn.length-4,sn.length);

	  	bootbox.confirm("确定给"+sn+"设备升级版本号为"+newVersion+"的"+fileTypeString+"吗?", function(result) {
	  		//alert("fileTypeString:"+fileTypeString+",newVersion:"+newVersion);
	  		if(result){
	  			$.ajax({
	  	  		  type:"POST",
	  	  		  url:$(t).parent().attr("action"),
	  	  		  dataType:"html",
	  	  		  data:{sn:sn,type:type,fileTypeString:fileTypeString,newVersion:newVersion},
	  	  		  success:function(data){
	  	  			  if(data=="1"){
	  	  				  easy2go.toast('info', sn4+" 命令下发成功");
	  	  				  gridObjUpload.options.otherParames =$('#leibie').serializeArray();
	  	  				  gridObjUpload.page(1);
	  	  			  }else if(data=="0"){
	  	  				  easy2go.toast('warn', sn4+" 命令下发失败");
	  	  			  }else if(data=="-1"){
	  	  				  easy2go.toast('warn', "参数为空");
	  	  			  }else if(data=="-2"){
	  	  				  easy2go.toast('warn', "设备与服务器链路不存在!");
	  	  			  }else if(data=="-3"){
	  	  				  easy2go.toast('warn', "设备与服务器本地链路不存在,不允许提取日志或升级!");
	  	  			  }
	  	  		  }
	  	  	  });
	  		}
	  	});

	  	  //$(t).parent().submit();

	    }

	    function urltovalue(index,urlstring){
	  	  var d=urlstring.split("&");
	  	  var c= d[index].split("=");
	  	  return  c[1];

	    }
	</script>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
		<jsp:param name="matchType" value="exactly" />
	</jsp:include>
</body>
</html>