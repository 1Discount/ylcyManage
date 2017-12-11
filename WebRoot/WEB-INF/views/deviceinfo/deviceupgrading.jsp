<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>添加权限菜单-权限管理-EASY2GO ADMIN</title>
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
						<DIV class="panel-heading">
							<H3 class="panel-title">添加设备升级配置</H3>
						</DIV>
						<DIV class="panel-body">
							<FORM id="menu_form" class="form-horizontal" action="javascript:return;" role="form" enctype="multipart/form-data" method="post">
								<input type="hidden" id="menu_ID" name="menuInfoID" value="" />
								<DIV class="form-group">
									<LABEL class="col-md-2 control-label" for="SN" >机身码:</LABEL>
									<DIV class="col-md-6">
										<INPUT id="sn_list" class="form-control" placeholder="请输入机身码后6位,多个用/隔开,例如123456/123457" name="SN" type="text" data-popover-offset="0,8">
									</DIV>
									<DIV class="col-md-4">
										<P class="form-control-static"></P>
									</DIV>
								</DIV>
								<DIV class="form-group">
                                    <LABEL class="col-md-2 control-label" for="">批量导入:</LABEL>
                                    <DIV class="col-md-6">
                                        <input type="file" id="file" name="file" id="file"
                                            data-popover-offset="0,8" class="form-control">
                                    </DIV>
                                    <DIV class="col-md-4">
                                        <P class="form-control-static"></P>
                                    </DIV>
                                </DIV>
								<DIV class="form-group">
									<LABEL class="col-md-2 control-label">升级文件类型:</LABEL>
									<DIV class="col-md-6">
										<label class="checkbox-items">
											<input type="checkbox" name="upgradeFileType" value="xmclient">
											xmclient(漫游)
										</label>
										<label class="checkbox-items">
											<input type="checkbox" name="upgradeFileType" value="CellDataUpdaterRoam.apk">
											CellDataUpdaterRoam.apk(漫游APK)
										</label>
                                        <label class="checkbox-items">
                                            <input type="checkbox" name="upgradeFileType" value="local_client">
                                            local_client(本地)
                                        </label>
                                        <label class="checkbox-items">
                                            <input type="checkbox" name="upgradeFileType" value="CellDataUpdater.apk">
                                            CellDataUpdater.apk(本地APK)
                                        </label>
                                        <label class="checkbox-items">
                                            <input type="checkbox" name="upgradeFileType" value="Phone.apk">
                                            Phone.apk
                                        </label>
                                        <c:if test="${sessionScope.User.userName != '何广超'}">
											<label class="checkbox-items">
												<input type="checkbox" name="upgradeFileType" value="MIP_List.ini">
												MIP_List.ini(MIP配置)
											</label>
											<label class="checkbox-items">
												<input type="checkbox" name="upgradeFileType" value="Settings.apk">
												Settings.apk(Setting文件)
											</label>
											<label class="checkbox-items">
												<input type="checkbox" name="upgradeFileType" value="i-jetty-3.2-SNAPSHOT.apk">
												i-jetty-3.2-SNAPSHOT.apk(Portal相关)
											</label>
											<label class="checkbox-items">
												<input type="checkbox" name="upgradeFileType" value="wifidog">
												wifidog(Portal相关)
											</label>
											<label class="checkbox-items">
												<input type="checkbox" name="upgradeFileType" value="wifidog.conf">
												wifidog.conf(Portal相关)
											</label>
											<label class="checkbox-items">
												<input type="checkbox" name="upgradeFileType" value="wifidog-msg.html">
												wifidog-msg.html(Portal相关)
											</label>
											<label class="checkbox-items">
												<input type="checkbox" name="upgradeFileType" value="root.war">
												root.war(Portal相关,仅单独升级时使用,后期 console.war 被 root.war 代替)
											</label>
											<label class="checkbox-items">
												<input type="checkbox" name="upgradeFileType" value="console.war">
											console.war(Portal相关,仅单独升级时使用)
											</label>
										</c:if>
										<!-- 4G -->
										<br>
										<label class="checkbox-items">
											<input type="checkbox" name="upgradeFileType" value="xmclient">
											xmclient_4G(漫游)
										</label>
										<label class="checkbox-items">
											<input type="checkbox" name="upgradeFileType" value="CellDataUpdaterRoam.apk">
											CellDataUpdaterRoam_4G.apk(漫游APK)
										</label>
                                        <label class="checkbox-items">
                                            <input type="checkbox" name="upgradeFileType" value="local_client">
                                            local_client_4G(本地)
                                        </label>
                                        <label class="checkbox-items">
                                            <input type="checkbox" name="upgradeFileType" value="CellDataUpdater.apk">
                                            CellDataUpdater_4G.apk(本地APK)
                                        </label>
                                        <label class="checkbox-items">
                                            <input type="checkbox" name="upgradeFileType" value="Phone.apk">
                                            Phone_4G.apk
                                        </label>
                                        <c:if test="${sessionScope.User.userName != '何广超'}">
											<label class="checkbox-items">
												<input type="checkbox" name="upgradeFileType" value="MIP_List.ini">
												MIP_List_4G.ini(MIP配置)
											</label>
											<label class="checkbox-items">
												<input type="checkbox" name="upgradeFileType" value="Settings.apk">
												Settings_4G.apk(Setting文件)
											</label>
											<label class="checkbox-items">
												<input type="checkbox" name="upgradeFileType" value="i-jetty-3.2-SNAPSHOT.apk">
												i-jetty-3.2-SNAPSHOT_4G.apk(Portal相关)
											</label>
											<label class="checkbox-items">
												<input type="checkbox" name="upgradeFileType" value="wifidog">
												wifidog_4G(Portal相关)
											</label>
											<label class="checkbox-items">
												<input type="checkbox" name="upgradeFileType" value="wifidog.conf">
												wifidog_4G.conf(Portal相关)
											</label>
											<label class="checkbox-items">
												<input type="checkbox" name="upgradeFileType" value="wifidog-msg.html">
												wifidog-msg_4G.html(Portal相关)
											</label>
											<label class="checkbox-items">
												<input type="checkbox" name="upgradeFileType" value="root.war">
												root_4G.war(Portal相关,仅单独升级时使用,后期 console.war 被 root.war 代替)
											</label>
											<label class="checkbox-items">
												<input type="checkbox" name="upgradeFileType" value="console.war">
											console_4G.war(Portal相关,仅单独升级时使用)
											</label>
										</c:if>
									</DIV>
									<DIV class="col-md-4">
										<P class="form-control-static red">1. portal相关的 console.war 或 root.war 一般不与 jetty 同时配置升级，因为 jetty 总内置最新的 console。只需要单独升级 console 时才单独选择 console.war 或 root.war。</P>
									</DIV>
								</DIV>
								<DIV class="form-group">
									<LABEL class="col-md-2 control-label" for="ifForcedToUpgrade">是否强制升级:</LABEL>
									<DIV class="col-md-6" style="padding-top: 7px;">
										<label class="checkbox-items">
											<input type="radio" name="ifForcedToUpgrade" checked="checked" value="1">
											是
										</label>
										<label class="checkbox-items">
											<input type="radio" name="ifForcedToUpgrade" value="0">
											否
										</label>
									</DIV>
									<DIV class="col-md-4">
										<P class="form-control-static"></P>
									</DIV>
								</DIV>
								<DIV class="form-group">
									<LABEL class="col-md-2 control-label" for="remark">备注:</LABEL>
									<DIV class="col-md-6">
										<textarea id="sim_desc" rows="3" name="remark" maxlength="199" data-popover-offset="0,8" class="form-control">${Model.remark}</textarea>
										<!-- <INPUT id="remark" class="form-control" name="remark" -->
										<!-- type="text"  data-popover-offset="0,8"> -->
									</DIV>
									<DIV class="col-md-4">
										<span class="red">如升级要点等信息</span>
									</DIV>
								</DIV>

								<DIV class="form-group">
									<DIV class="col-md-7 col-md-offset-2">
										<DIV class="btn-toolbar">
											<BUTTON class="btn btn-primary" type="button" onclick="addsub()">添加</BUTTON>
											<BUTTON class="btn btn-primary" type="button" onclick="addsub2()">批量导入</BUTTON>
											<BUTTON class="btn btn-default" type="button" onclick="deleteinfo()">删除</BUTTON>
											<BUTTON class="btn btn-default" onclick="javascript:history.go(-1);" type="button">返回</BUTTON>
										</DIV>
									</DIV>
								</DIV>
							</FORM>
						</DIV>
					</DIV>
                    <DIV class="panel">
                       <DIV class="panel-body">
                           <FORM class="form-inline" id="leibie" method="get" action="#">
                           <input type="hidden" id="pagesize" value="10" />
                           <input type="hidden" value="1" id="pagenum" />
                           <input type="hidden" value="0" id="updateSource" />
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
                                       onclick="gridObj.options.otherParames =$('#leibie').serializeArray();gridObj.page(1);">搜索</BUTTON>
                               </DIV>
                           </FORM>
                           <form id="testc"></form>
                       </DIV>
                   </DIV>
					<DIV class="panel">
						<DIV class="panel-heading">
							<H3 class="panel-title">设备升级配置信息</H3>
						</DIV>
						<DIV class="panel-body">
							<table id="searchTable">
								<tr>
									<th w_render="deviceUpgradingID" width="4%;"><input id="selectAll" type="checkbox" value="全选" />全选</th>
									<th w_render="render_SN" width="16%;">机身码</th>
									<th w_index="upgradeFileType" width="16%;">文件升级类型</th>
									<th w_render="ifForcedToUpgrade" width="16%;">是否强制升级</th>
									<th w_render="ifUpdated" width="16%;">是否已升级</th>
									<th w_index="updateDate" width="16%;">升级时间</th>
                                    <th w_index="creatorUserName" width="16%;">创建人</th>
                                    <th w_render="creatorDate" width="16%;">创建时间</th>
									<th w_index="remark" width="16%;">备注</th>
								</tr>
							</table>
						</DIV>
					</DIV>
	                <DIV class="panel">
	                    <div class="paomadeng">
	                         <span style="font-size: 14px; font-weight: bolder;" onclick="return execl('no')" >
	                         <a id="a" href="<%=basePath %>device/exportdeviceupgrade?">
	                         <img src="<%=basePath%>static/images/excel.jpg" style="float: left; margin-top: -5px;"  width="30" height="30" title="" /> 导出全部</a></span>
	                    </div>
	                </DIV>
				</DIV>
			</SECTION>
		</SECTION>
	</section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
	<SCRIPT type="text/javascript">
    var gridObj;
    $(function(){
    	var insertOKCount = "${insertOKCount}";
    	if(insertOKCount != '')
   		{
    		easy2go.toast('warn',insertOKCount+"条设备更新配置批量导入成功!");
   		}
    	$(".form_datetime").datetimepicker({
            format: 'YYYY-MM-DD HH:mm:ss',
            pickDate: true,     //en/disables the date picker
            pickTime: true,     //en/disables the time picker
            showToday: true,    //shows the today indicator
            language:'zh-CN',   //sets language locale
            defaultDate: null, // moment().add(7, 'days'), //sets a default date, accepts js dates, strings and moment objects
        });
    	
    	var pagesize=parseInt($("#pagesize").val());

    	gridObj = $.fn.bsgrid.init('searchTable',{
       		url:'<%=basePath%>device/deviceupgradelist',
       	    pageSizeSelect: true,
            pageSize:pagesize,
            autoLoad:false,
            otherParames:$('#leibie').serializeArray(),
            pageSizeForGrid:[15,30,50,100],
            displayPagingToolbarOnlyMultiPages:true,
		    additionalAfterRenderGrid: function(parseSuccess, gridData, options) {
				 if(parseSuccess){
	        		 $("#pagenum").val(options.curPage);
	        		 $("#pagesize").val( $("#searchTable_pt_pageSize").val());
	        	 }
      		 },
	    });
    	
    	if($("#pagenum").val()!=""){
     	   gridObj.page($("#pagenum").val());
        }else{
     	   gridObj.page(1);
        };
    	
		$("#selectAll").click(function() {
			var selectAll = document.getElementById("selectAll");
			if (selectAll.checked) {
				$("input[name=deviceUpgradingID]").prop("checked", true);
			}
			else {
				$("input[name=deviceUpgradingID]").prop("checked", false);
			}
		});
	});
	function addsub() {
		if ($("#sn_list").val() == '') {
			easy2go.toast('warn', "请输入机身码");
			return;
		}
		
		var count = 0;
        var checkArry = document.getElementsByName("upgradeFileType");
        for (var i = 0; i < checkArry.length; i++) { 
            if(checkArry[i].checked == true){
                //选中的操作
                count++; 
            }
        }
        if( count == 0 ){
            easy2go.toast('warn', "请选择升级文件类型");
            return;
        }
		
		$.ajax({
			type : "POST",
			url : "<%=basePath%>device/insertdeviceupgrade",
            dataType:"html",
            data:$('#menu_form').serialize(),
            success:function(data){
            	 data=parseInt(data);
                 if(data<0){
                     easy2go.toast('warn',"生成失败!");
                 }else if(data>=0){
                     easy2go.toast('warn',data+"条设备更新配置生成成功!");
                 }else if(data==-1){
                     easy2go.toast('warn',"机身码为空");
                 }
                 $("#sn_list").val("");
                 $("input[type='checkbox']").prop("checked",false);
                 $("#remark").val("");
                 gridObj.refreshPage();
            }
      	});
   }

	function addsub2() {
		if ($.trim($("#file").val()) == '') {
            easy2go.toast('warn', "请选择导入所需的excel文件");
            return;
        }
		
		var obj=document.getElementsByName('upgradeFileType'); //选择所有name="'test'"的对象，返回数组 
		//取到对象数组后，我们来循环检测它是不是被选中 
		var s=''; 
		for(var i=0; i<obj.length; i++){ 
			  if(obj[i].checked) s+=obj[i].value+','; //如果选中，将value添加到变量s中 
		} 
		if(s=='')
		{
			easy2go.toast('warn', "请选择升级文件类型");
            return;
		}
		$('#menu_form').attr("action","<%=basePath%>device/insertdeviceupgrade2");
		$('#menu_form').submit();
	}
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
   function editinit(index){
       var record= gridObj.getRecord(index);
        $("#menu_ID").val(record.menuInfoID);
        $("#menu_name").val(record.menuName);
        $("#menu_path").val(record.menuPath);
        $("#menu_remark").val(record.remark);
        $("#menu_no").val(record.sortCode);
   }

   //删除.
   function deleteinfo(){

	   var l=gridObj.getCheckedRowsRecords().length;
       if(l==0){
         easy2go.toast('warn',"请选择你要删除的记录");
         return;
       }
       //获取到选中的订单ID
       var deviceUpgradingID="";
       for(var i=0;i<l;i++){
         if(i==0){
        	 deviceUpgradingID+=gridObj.getCheckedRowsRecords()[i].deviceUpgradingID;
         }else{
        	 deviceUpgradingID+=","+gridObj.getCheckedRowsRecords()[i].deviceUpgradingID;
         }
       }

       $.ajax({
            type:"POST",
            url:"<%=basePath%>device/deletedeviceupgrade?deviceUpgradingID="+deviceUpgradingID,
            dataType:"html",
            success:function(data){
            	result = jQuery.parseJSON(data);
                if (result.code == 0) { // 成功删除
                   easy2go.toast('info', result.msg);
                   gridObj.refreshPage();
                } else {
                   easy2go.toast('error', result.msg);
                }
            }
       });
   }
   
   function execl(){
	   var par = $('#leibie').serialize();
       $("#a").attr('href','<%=basePath %>device/exportdeviceupgrade?'+par);
   }
   
   $(document).keydown(function(event){
       if(event.keyCode == 13){//绑定回车
           $("#sn_select").blur();
           $("#begainTime").blur();
           $("#endTime").blur();
           $("#creatorUserName").blur();
           gridObj.options.otherParames =$('#leibie').serializeArray();
           gridObj.page(1);
           event.returnValue = false;
       }
   });

</SCRIPT>
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>