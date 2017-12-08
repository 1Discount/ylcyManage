<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <!-- 格式化时间 -->
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <title>客服中心-设备登陆记录-EASY2GO ADMIN</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="<%=basePath %>static/css/app.min.css?20150209">
    <link rel="stylesheet" href="<%=basePath %>static/css/grid/bsgrid.all.min.css">
    <meta name="csrf_token">
    <%@include file="/WEB-INF/views/common/_ie8support.html" %>
    <%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
    <style type="text/css">
        #searchTable tr{height:40px;}
        #histTable tr{height:40px;}
    </style>
  </head>
 <body>
   
    <section id="container">
      <jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
      <jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
     
 <SECTION id="main-content">
<SECTION class="wrapper">
<div class="col-md-12">
					<section class="panel">
						<header class="panel-heading tab-bg-dark-navy-blue">
							<ul class="nav nav-tabs nav-justified ">
								<li class="active main-menu-tab" id="notuserMenu"><a data-toggle="tab" href="#EXClogin">登陆异常</a></li>
								<li class="main-menu-tab" id="onlineMenu"><a data-toggle="tab" href="#EXChis">流量异常</a></li>
							</ul>
						</header>
						<div class="panel-body">
							<div class="tab-content tasi-tab">
							<!-- 第一个选项卡 -->
							<div id="EXClogin" class="active tab-pane">
									<div class="row">
									<DIV class="col-md-12">
<DIV class="panel">
<DIV class="panel-body" style="padding-top:5px;padding-bottom:5px;">
<%--    <FORM class="form-inline"  method="get" action="<%=basePath %>customer/customerInfolist/getSearchCustomerinfolist"> --%>
   <FORM class="form-inline" id="searchForm"  method="get" action="#">
         <DIV class="form-group">
             <LABEL class="inline-label">设备序列号：</LABEL>
             <INPUT class="form-control" name="SN" id="SN" type="text" placeholder="sn">
         </DIV>
         <DIV class="form-group">
             <LABEL class="inline-label">日期：</LABEL>
             <INPUT id="order_creatorDateend1" class="form-control form_datetime" name="sj" type="text" data-date-format="YYYY-MM-DD">
         </DIV>
         <div class="form-group">
          <label class="inline-label">国&nbsp;家：</label>
          <select name="mcc" style="width: 130px;"
          class="form-control">
            <option value="" selected="selected">全部国家</option>
            <c:forEach items="${Countries}" var="country" varStatus="status"><option value="${country.countryCode}">${country.countryName}</option></c:forEach>
          </select>
        </div>
        <!-- <div class="form-group">
          <label class="inline-label">异常类型：</label>
          <select name="SIMAllot" style="width: 130px;"
          class="form-control">
            <option value="-1" selected="selected">全部异常</option>
           <option value="3">没有有效订单</option>
           <option value="4">没有分配到SIM卡</option>
           <option value="9">IMSI为空</option>
          </select>
          
          
        </div> -->
        
         <div class="form-group">
         	<label class="checkbox-items"><INPUT onclick="gridObj1.options.otherParames = $('#searchForm').serializeArray();gridObj1.page(1);" class="" name="SIMAllot" value="3" type="checkbox" >没有有效订单</label>
         </div>
         <div class="form-group">
         	<label class="checkbox-items"><INPUT onclick="gridObj1.options.otherParames = $('#searchForm').serializeArray();gridObj1.page(1);" class="" name="SIMAllot" value="4" type="checkbox" >没有分配到SIM卡</label>
         </div>
         <DIV class="form-group">
             <BUTTON class="btn btn-primary" type="button" onclick="gridObj1.options.otherParames = $('#searchForm').serializeArray();gridObj1.page(1);">搜索</BUTTON>
         </DIV>
   </FORM>
</DIV>
</DIV>

<DIV class="panel">
<!-- <DIV class="panel-heading">历史在线设备</DIV> -->
<DIV class="panel-body">
<DIV class="table-responsive">
  <TABLE id="searchTable">
  <TR>
   	<th w_render="SN1"><b>设备SN</b></th>
    <th w_render="IMSI"><b>IMSI</b></th>
    <th w_render="jizhan"><b>基站信息</b></th>
    <th w_index="lastTime"><b>记录时间</b></th>
    <th w_render="loginr"><b>登录结果</b></th>
    <th w_render="chuliresult"><b>处理结果</b></th>
    <th w_render="tag1" width="5%"><b>标记</b></th>
    <!-- <th w_index="roamDayUsedFlow"><b>累积流量</b></th> -->
  </TR>
</TABLE> 
   </DIV>
<DIV>
</DIV></DIV></DIV>

</DIV>
									</div>
							
							</div>
							<!-- 第二个选项卡 -->
								<div id="EXChis" class="tab-pane ">
									<div class="row">
										<DIV class="col-md-12">
											<DIV class="panel">
												<DIV class="panel-body"
													style="padding-top: 5px; padding-bottom: 5px;">
													<%--    <FORM class="form-inline"  method="get" action="<%=basePath %>customer/customerInfolist/getSearchCustomerinfolist"> --%>
													<FORM class="form-inline" id="histsearchForm" method="get"
														action="#">
														<input type="hidden" value="1" id="pagenum" /><input
															type="hidden" id="pagesize" value="20" />
														<DIV class="form-group">
															<LABEL class="inline-label">设备序列号：</LABEL> <INPUT
																class="form-control" name="SN" id="SN" type="text"
																placeholder="sn">
														</DIV>
														<div class="form-group">
															<label class="inline-label">国&nbsp;家：</label> <select
																name="mcc" style="width: 130px;" class="form-control">
																<option value="">全部国家</option>
																<c:forEach items="${Countries}" var="country"
																	varStatus="status">
																	<option value="${country.countryCode}">${country.countryName}</option>
																</c:forEach>
															</select>
														</div>
														<DIV class="form-group">
															<LABEL class="inline-label">在线日期：</LABEL> <INPUT
																id="order_creatorDateend"
																class="form-control form_datetime" name="lastTime"
																type="text" data-date-format="YYYY-MM-DD">
														</DIV>
														<div class="form-group">
															<label class="checkbox-items"><INPUT class=""
																onclick="hisbtn()" name="ifOnlyString" value="1"
																type="checkbox">无流量上传设备</label>
														</div>
														<div class="form-group">
															<label class="checkbox-items"><INPUT class=""
																onclick="hisbtn()" name="ifExcOut" value="是"
																type="checkbox">流量上传中断</label>
														</div>
														<!--  <div class="form-group">
         	<label class="checkbox-items"><INPUT class="" name="ifChunString" value="是" type="checkbox" >10分钟无活动记录</label>
         </div> -->
														<DIV class="form-group">
															<BUTTON class="btn btn-primary" type="button"
																onclick="hisbtn()">搜索</BUTTON>
														</DIV>
													</FORM>
												</DIV>
											</DIV>

											<DIV class="panel">
												<!-- <DIV class="panel-heading">历史在线设备</DIV> -->
												<DIV class="panel-body">
													<DIV class="table-responsive">
													 	<TABLE id="histTable">
															<TR>
																<TH w_render="SN"><b>设备序列号</b></TH>
																<th w_render="HIMSI"><b>IMSI</b></th>
																<th w_index="simAlias"><b>SIM卡代号</b></th>
																<th w_render="sim_speedType"><b>高/低速</b></th>
																<th w_render="customerName"><b>客户姓名</b></th>
																<th w_index="customerID" w_hidden="true"><b>客户id</b></th>
																<th w_render="jizhan" w_sort="mcc,asc" width="10%"><b>基站信息</b></th>
																<th w_index="lastTime02" w_sort="lastTime02,ase"
																	width="10%"><b>最近活动时间</b></th>
																<th w_index="battery" w_sort="battery,ase" width="5%"><b>电量</b></th>
																<th w_index="wifiCount" w_sort="wifiCount,ase"
																	width="5%"><b>wifi数</b></th>
																    <th w_index="roamGStrenth"><b>实时流量</b></th>
																<th w_render="ssll"><b>实时流量</b></th>
																<th w_render="dayUsedFlow" width="5%"
																	w_sort="dayUsedFlow,ase"><b>累积流量</b></th>
																<th w_index="orderExplain" w_length="20" width="1%"><b>订单描述</b></th>
																<th w_index="orderRemark" w_length="10" width="1%"><b>交易备注</b></th>
    															<th w_render="tag" width="5%"><b>标记</b></th>
															</TR>
														</TABLE>  
													</DIV>
													<DIV></DIV>
												</DIV>
											</DIV>
										</DIV>
									</div>
								</div>
							</div>
							</div>
</section>
</div>





</SECTION>
</SECTION>
    </section>
    <script src="<%=basePath %>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath %>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath %>static/js/grid/bsgrid.all.min.js"></script>
    <script src="<%=basePath %>static/js/bootbox.min.js"></script>
    <script type="text/javascript">
    var gridObj1;
    var gridObj;
    $(function(){
	      gridObj1 = $.fn.bsgrid.init('searchTable',{
           url:'<%=basePath %>service/center/loginlogsEXC',
           pageSizeSelect: true,
           pageSize: 20,
           isProcessLockScreen:false,
           pageSizeForGrid:[10,20,30,50,100]
       }); 
       
       $("#order_creatorDateend1").datetimepicker({
   		pickDate: true,                 //en/disables the date picker
   		pickTime: true,                 //en/disables the time picker
   		showToday: true,                 //shows the today indicator
   		language:'zh-CN',                  //sets language locale
//    		defaultDate: moment().add(0, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
   	});
       $("#order_creatorDateend").datetimepicker({
      		pickDate: true,                 //en/disables the date picker
      		pickTime: true,                 //en/disables the time picker
      		showToday: true,                 //shows the today indicator
      		language:'zh-CN',                  //sets language locale
//       		defaultDate: moment().add(0, 'days'),                 //sets a default date, accepts js dates, strings and moment objects
      	});
       
       
       var pagesize=parseInt($("#pagesize").val());
       gridObj= $.fn.bsgrid.init('histTable',{
          url:'<%=basePath %>service/center/listlogs?temp=123',
          
          pageSizeSelect: true,
          pageSize:pagesize,
          pageSizeForGrid:[10,20,30,50,100],
          isProcessLockScreen:false,
          additionalAfterRenderGrid:test,
          //autoLoad:false,
          otherParames:$('#histsearchForm').serializeArray(),
          additionalAfterRenderGrid: function(parseSuccess, gridData, options) {
         	 if(parseSuccess){
         		 $("#pagenum").val(options.curPage);
          		 $("#snCount").html(options.totalRows);
        	     var a = $("#searchTable_pt_pageSize").val();
        	    $("#pagesize").val(a);
         	 }
          }
      });
      if($("#pagenum").val()!=""){
   	   gridObj.page($("#pagenum").val());
      }else{
   	   gridObj.page(1);
      }
   });
    
    
    bootbox.setDefaults("locale","zh_CN");
	  
   
    function tag(record, rowIndex, colIndex, options){
    	return '<a class="btn btn-primary btn-xs"><span onclick="addtag('+rowIndex+')" class="glyphicon glyphicon-edit">标记</span></a>';
    }
    
    
    function tag1(record, rowIndex, colIndex, options){
    	return '<a class="btn btn-primary btn-xs"><span onclick="addtag1('+rowIndex+')" class="glyphicon glyphicon-edit">标记</span></a>';
    }
    
	  function customerName(record, rowIndex, colIndex, options){
	    	return '<A style="color:#1FB5AD;" href="<%=basePath %>customer/customerInfolist/customerInfoDetail?cusid='+record.customerID+'">'+record.customerName+'</A>';
	       }
    function IMSI(record, rowIndex, colIndex, options){
    	return record.IMSI;
       }
    function HIMSI(record, rowIndex, colIndex, options){
    	//alert(record.type+"--"+record.SIMAllot);
    	if(record.type=='00'){
    		if(record.SIMAllot==0){
    			return '<A style="color:#1FB5AD;" href="<%=basePath %>sim/siminfo/view/' + record.SIMID + '">'+record.IMSI+'</A>';
    		}else if(record.SIMAllot==3){
        		return '登陆失败,<span class="label label-danger label-xs">没有有效订单</span>';
        	}else if(record.SIMAllot==4){
        		return '登陆失败,<span class="label label-danger label-xs">没有分配到SIM卡</span>';
        	}else if(record.SIMAllot==6){
        		return '<span class="label label-danger label-xs">已发生跳转</span>';
        	}
    	}else{
    		return '<A style="color:#1FB5AD;" href="<%=basePath %>sim/siminfo/view/' + record.SIMID + '">'+record.IMSI+'</A>';
    	}
       }
    function SN(record, rowIndex, colIndex, options){
    	var time = record.lastTime;
  	  	time = time.substr(0,10);
     	  if($("#order_creatorDateend").val()==''){
     		  
     	  }else{
     		 time = $("#order_creatorDateend").val();
     	  }	
 	    //snCount++;
    	if(record.tag==''){
    		return  '<A style="color:#1FB5AD;" href="<%=basePath %>service/center/devicelogs?sbid='+record.SN+'&sj='+time+'">'+record.SN+'</A>';
    	}else{
    		return '<A style="color:#1FB5AD;" href="<%=basePath %>service/center/devicelogs?sbid='+record.SN+'&sj='+time+'">'+record.SN+'</A><a href="javascript:return;"><img  title="'+record.tag+'" src="<%=basePath %>static/images/050.png" /></a>';
    	}
       }
    function SN1(record, rowIndex, colIndex, options){
    	var time = record.lastTime;
  	  	time = time.substr(0,10);
     	  if($("#order_creatorDateend").val()==''){
     		  
     	  }else{
     		 time = $("#order_creatorDateend").val();
     	  }	
    		return  '<A style="color:#1FB5AD;" href="<%=basePath %>service/center/devicelogs?sbid='+record.SN+'&sj='+time+'">'+record.SN+'</A>';
    	
       }
    function chuliresult(record, rowIndex, colIndex, options){
    	if(record.tag!=""){
    		return record.tag;
    	}else{
    		return record.tag;
    	}
    }
    function ssll(record, rowIndex, colIndex, options){//实时流量
    	if(record.upFlowAll==''){
    		return '暂无数据';
    	}
    	var result=parseInt(record.upFlowAll)+parseInt(record.downFlowAll);
    	if(result>1024){
    		result=result/1024;
    		result=result+"";
    		result = result.substr(0,result.indexOf(".")+3)+"MB";   
    	}else{
    		result = result + "KB";
    	}
    	return result;
    }
    function dayUsedFlow(record, rowIndex, colIndex, options){//累计流量
    	if(record.dayUsedFlow==''){
    		return '暂无数据';
    	}
    	var result = record.dayUsedFlow;
    	if(result>1024){
    		result=result/1024;
    		result=result+"";
    		result = result.substr(0,result.indexOf(".")+3)+"MB";   
    	}else{
    		result = result + "KB";
    	}
    	return result;
    }
    function jizhan(record, rowIndex, colIndex, options){
    	var j=record.jizhan;
    	if(j.indexOf(",")>-1){
    		j=j.split(",")[0];
    	}
    	return '<A style="color:#1FB5AD;" target="_blank" href="<%=basePath %>remote/mapView?jizhan='+j+'">'+j+'</A>';
    }
    function sim_speedType(record, rowIndex, colIndex, options){
    	if(record.speedType==0) {
            return '<a class="btn btn-info btn-xs">高速</a>';
        	} else if(record.speedType==1) {
             return '<a class="btn btn-danger btn-xs">低速</a>';
        	}else {
        		return '';
        	}
    }
    
    function  loginr(record, rowIndex, colIndex, options){
    	if(record.type=='06'){
    		return '<span class="label label-danger label-xs">退出</span>';
    	}
    	if(record.SIMAllot==0){
    		return "<span class='label label-success label-xs'>成功</span>";
    	}else if(record.SIMAllot==3){
    		return '<a  class="label label-danger label-xs" href="<%=basePath %>orders/flowdealorders/list?SN='+record.SN+'"><span>没有有效订单</span></a>';
    	}else if(record.SIMAllot==4){
    		return '<a  class="label label-danger label-xs" href="<%=basePath %>sim/siminfo/index?MCC='+record.mcc+'"><span>没有可用卡位</span></a>';
    	}
    	else if(record.SIMAllot==6){
    		return '<span class="label label-danger label-xs">已发生跳转</span>';
    	}
    	
    }
    function test(){
    	/* var t= $('#searchTable tbody').children().eq(1);
    	t.addClass("selected selected_color"); */
    	
    }
    
    function hisbtn(){
    	$('#snCount').html(0);
    	gridObj.options.otherParames=$('#histsearchForm').serializeArray();
    	gridObj.page(1);
    }
    //打开弹出框
    function addtag(index){
    	var record= gridObj.getRecord(index);
    	var tags=record.tag.split("|")[0];
    	bootbox.dialog({
            title: "请输入/修改标注内容 SN:"+record.SN,
            message: '<div class="row">  ' +
                '<div class="col-md-12"> ' +
                '<form class="form-horizontal" id="sim-notes-form" mothod="post"> ' +
                '<div class="form-group"> ' +
                '<label class="col-md-3 control-label" for="name">标注:</label> ' +
                '<div class="col-md-8">' +
                '<textarea id="notes" rows="3" name="notes" maxlength="254" data-popover-offset="0,8" class="form-control">' + 
                tags+ '</textarea>' + //unescape(notes)
                '</div> ' +
                '</form> </div>  </div>',
            buttons: {
                cancel: {
                    label: "去掉标记",
                    className: "btn-default",
                    callback: function () {
                    	$.ajax({
                    		type:"POST",
                    		dataType:"html",
                    		url:"<%=basePath %>service/center/SNtag",
                    		data:{sn:record.SN,tag:""},
                    		success:function(data){
                    			if(data=='1'){
                    				gridObj.refreshPage();
                    			}else{
                    				easy2go.toast('warn',"标记保存出错");
                    			}
                    		}
                    		
                    	});
                    }
                },
                success: {
                    label: "保存",
                    className: "btn-success edit-button-ok",
                    callback: function () {
                    	$.ajax({
                    		type:"POST",
                    		dataType:"html",
                    		url:"<%=basePath %>service/center/SNtag",
                    		data:{sn:record.SN,tag:$("#notes").val()},
                    		success:function(data){
                    			if(data=='1'){
                    				gridObj.refreshPage();
                    			}else{
                    				easy2go.toast('warn',"标记保存出错");
                    			}
                    			
                    		}
                    		
                    	});
                    }
                }
            }
        });
    	
    }

    //打开弹出框
    function addtag1(index){
    	
    	var record= gridObj1.getRecord(index);
    	var tags=record.tag.split("|")[0];
    	bootbox.dialog({
            title: "请输入/修改标注内容 SN:"+record.SN,
            message: '<div class="row">  ' +
                '<div class="col-md-12"> ' +
                '<form class="form-horizontal" id="sim-notes-form" mothod="post"> ' +
                '<div class="form-group"> ' +
                '<label class="col-md-3 control-label" for="name">标注:</label> ' +
                '<div class="col-md-8">' +
                '<textarea id="notes" rows="3" name="notes" maxlength="254" data-popover-offset="0,8" class="form-control">' + 
                tags+ '</textarea>' + //unescape(notes)
                '</div> ' +
                '</form> </div>  </div>',
            buttons: {
                cancel: {
                    label: "去掉标记",
                    className: "btn-default",
                    callback: function () {
                    	$.ajax({
                    		type:"POST",
                    		dataType:"html",
                    		url:"<%=basePath %>service/center/SNtagIMSI",
                    		data:{sn:record.SN,tag:""},
                    		success:function(data){
                    			if(data=='1'){
                    				gridObj1.refreshPage();
                    			}else{
                    				easy2go.toast('warn',"标记保存出错");
                    			}
                    		}
                    	});
                    }
                },
                success: {
                    label: "保存",
                    className: "btn-success edit-button-ok",
                    callback: function () {
                    	$.ajax({
                    		type:"POST",
                    		dataType:"html",
                    		url:"<%=basePath %>service/center/SNtagIMSI",
                    		data:{sn:record.SN,tag:$("#notes").val()},
                    		success:function(data){
                    			if(data=='1'){
                    				gridObj1.refreshPage();
                    			}else{
                    				easy2go.toast('warn',"标记保存出错");
                    			}
                    			
                    		}
                    		
                    	});
                    }
                }
            }
        });
    	
    }
    </script>
    <%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
  </body>
</html>