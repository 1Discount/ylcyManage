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
<title>s升级上传-EASY2GO ADMIN</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet" href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/jquery-ui.css" />
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
					<%-- <DIV class="panel panel-body">
						<form id="import_form" class="form-horizontal" action="<%=basePath%>remote/uploadding" method="post" enctype="multipart/form-data">


							<div class="form-group">
							<div class="col-sm-6">

									<input type="file" id="file" name="file"
										 required class="form-control">

								</div>

									<div class="btn-toolbar">
										<button type="submit" id="batchsubmit"
											onclick="unlockScreen();" class="btn btn-primary">上传</button>
										<button type="button" onclick="javascript:history.go(-1);"
											class="btn btn-default">返回</button>
											<LABEL class="inline-label">当前文件类型：<span style="color: red;" id="fileTypeName">暂无</span></LABEL>
										<LABEL class="inline-label">版本号：<span style="color: red;" id="fileVersion">暂无</span></LABEL>

									</div>
									<DIV class="form-group">

									</DIV>

							</div>
						</form>
					</DIV> --%>

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
             <LABEL class="inline-label">设备序列号：</LABEL>
             <INPUT class="form-control" name="SN" id="SN" type="text" placeholder="sn">
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





    	<form class='form-inline' action='<%=basePath %>remote2/remoteUpgrade'>
		 <div class="col-md-4">
				<span   style="display: block;">
					<select  id="fileTypeString" class="form-control" style="width:200px;">
						<option value="漫游">xmclient</option>
			            <option value="本地">local_client</option>
			            <option value="漫游APK">CellDataUpdaterRoam.apk</option>
			            <option value="本地APK">CellDataUpdater.apk</option>
			            <option value="MIP">MIP_List.ini</option>
			            <option value="Phone.apk">Phone.apk</option>
			            <option value="本地Settings">Settings.apk</option>
					</select>
				</span>
		</div>

	    	<input type='text' name='sn' value='172150210000229' />
	    	<button type='button' onclick="os(this)" class='btn btn-primary'>升级</button>
	 </form>







<DIV class="panel">
<DIV class="panel-heading">当前在线设备</DIV>
<DIV class="panel-body">
<DIV class="table-responsive">
<TABLE id="searchTable">
  <TR>
    <th w_render="IMSI" width="15%"><b>IMSI</b></th>
    <TH w_render="SN" width="15%"><b>设备序列号</b></TH>
    <th w_render="customerName" width="10%"><b>客户姓名</b></th>
    <th w_index="lastTime" width="15%"><b>最近活动时间</b></th>
    <th w_render="remotesj"><b>远程升级</b></th>
  </TR>
</TABLE>
   </DIV>
<DIV>
</DIV></DIV></DIV></DIV>
			</SECTION></DIV></DIV></DIV>
		</SECTION>
	</section></section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<script src="<%=basePath%>static/js/bootbox.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>static/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>static/js/jquery.multiselect.js"></script>
	<script type="text/javascript">
    $("#example").multiselect({
	  	    header: false,
	  	    height: 200,
	  	    minWidth: 160,
	  		selectedList: 10,//预设值最多显示10被选中项
	  	    hide: ["explode", 500],
	  	    noneSelectedText: '请选择要更新的文件',
	  	    close: function(){
	  	      var values= $("#example").val();
	  		  $("#hfexample").val(values);

	  		}
	   });
    var flowNum=1;
    //选中填值
    function pullvalue(data){
    	$("#orderID").val(data.orderID);
    	$("#client_name").val(data.customerName);
    	$("#client_phone").val(data.customerPhone);
    	$("#client_address").val(data.address);
        $("#example").multiselect({
	 	  	    header: false,
	 	  	    height: 200,
	 	  	    minWidth: 160,
	 	  		selectedList: 10,//预设值最多显示10被选中项
	 	  	    hide: ["explode", 500],
	 	  	    noneSelectedText: data.countryName,
	 	  	    close: function(){
	 	  	      var values= $("#example").val();
	 	  	      if(values!=null){
	 	  	    	$("#pullcountry").val("");
	 	  	   		$("#hfexample").val(values);
	 	  	      }else{
	 	  	    	$("#pullcountry").val(data.countryList);
	 	  	   		$("#hfexample").val("");
	 	  	      }
	 	  		}
	 	   });
    	$("#panlUser_Date").val(data.startTime);
    	$("#deal_days").val(data.days);
    	$("#deal_amount").val(data.price);
    	$("#flowExpireDate").val();
    	$("#deal_journey").val(data.trip);
    	$("#orderSource").val(data.orderSource);
    	$("#deal_desc").val(data.remark);
    	$("#aoID").val(data.aoID);
    	$("#pickUpWay").val(data.pickUpWay);
    	//清空选择的值
    	$("#hfexample").val("");
    	$("#pullcountry").val(data.countryList);
    	$("#userCountry").val(data.countryName);
    	//流量个数
    	flowNum=data.total;
    	if(flowNum==1){
    		$("#devicedeal_sn").attr("placeholder","请输入"+flowNum+"个SN后6位");
    	}else if(flowNum>1){
    		$("#devicedeal_sn").attr("placeholder","请输入"+flowNum+"个SN后6位,用/隔开");
    	}
    }

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

		       $("#order_creatorDateend").datetimepicker({
		     		pickDate: true,                 //en/disables the date picker
		     		pickTime: true,                 //en/disables the time picker
		     		showToday: true,                 //shows the today indicator
		     		language:'zh-CN',                  //sets language locale
//		      		defaultDate: moment().add(0, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
		     	});
		 	 $("#batchsubmit").click(function(){

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


		// 远程升级
<%-- 	    function remotesj(record,rowIndex,colIndex,options){
	      	return "<form class='form-inline' action='<%=basePath %>remote/remoteUpgrade'>"+
	    	"<span class='devFileType'>"+filetip+"</span>"+
	    	"<input type='hidden' name='sn' value='"+record.SN+"' /><button type='button' onclick='os(this)' class='btn btn-primary'>升级</button></form>";
	    } --%>

<%--     	<form class='form-inline' action='<%=basePath %>remote2/remoteUpgrade'>
		 <div class="col-md-4">
				<span   style="display: block;">
					<select  id="fileTypeString" class="form-control" style="width:200px;">
						<option value="漫游">xmclient</option>
			            <option value="本地">local_client</option>
			            <option value="漫游APK">CellDataUpdaterRoam.apk</option>
			            <option value="本地APK">CellDataUpdater.apk</option>
			            <option value="MIP">MIP_List.ini</option>
			            <option value="Phone.apk">Phone.apk</option>
			            <option value="本地Settings">Settings.apk</option>
					</select>
				</span>
		</div>

	    	<input type='text' name='sn' value='172150126367490' />
	    	<button type='button' onclick="os(this)" class='btn btn-primary'>升级</button>
	 </form>--%>


		function remotesj(record,rowIndex,colIndex,options){
	      	return "<form class='form-inline' action='<%=basePath %>remote/remoteUpgrade'>"+
	 		"<div class='col-md-4'>"+
				"<span id='userCountry1' style='display: block;'>"+
					"<select id='fileTypeString' class='form-control' style='width:200px;'>"+
						"<option value='3'>xmclient</option>"+
			            "<option value='4'>local_client</option>"+
			            "<option value='5'>CellDataUpdaterRoam.apk</option>"+
			            "<option value='6'>CellDataUpdater.apk</option>"+
			            "<option value='7'>MIP_List.ini</option>"+
			            "<option value='18'>Phone.apk</option>"+
			            "<option value='17'>Settings.apk</option>"+
					"</select>"+
				"</span>"+
		"</div>"+
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
	    	var fileTypeString = document.getElementById("fileTypeString").value;

	    	alert(fileTypeString);
	    	if(fileTypeString=="暂无"){
	    		easy2go.toast('warn', "请先上传升级文件!");
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
	    		easy2go.toast('warn', "文件类型不正确!");
	    		return false;
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
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
		<jsp:param name="matchType" value="exactly" />
	</jsp:include>
</body>
</html>