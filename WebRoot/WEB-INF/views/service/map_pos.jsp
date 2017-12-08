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
%>
<!DOCTYPE html>
<html>
<head>
<title>地图-POS机位置-EASY2GO ADMIN</title>
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
#searchTable tr {
	height: 40px;
}
</style>
<script type="text/javascript" src="http://ditu.google.cn/maps/api/js?v=3.7&sensor=false&language=zh-CN&libraries=geometry"></script>
<script>
var map;
var marker;
var latlng;
var address="${address}";
var radius=1000;
var markersArray = [];
var windowArray = [];
var myCityC;
function initialize() {
/* latlng=new google.maps.LatLng('${lat}','${lon}');
var mapOptions={zoom: 8,center:latlng,mapTypeId: google.maps.MapTypeId.ROADMAP};
//var icon = new google.maps.MarkerImage("/images/client/o1.png", new google.maps.Size(30, 30));
map = new google.maps.Map(document.getElementById('mapView'),mapOptions); */
}
google.maps.event.addDomListener(window,'load',initialize);



</script>

</head>
<body>
<div id="D_display" style="display:none;">
		<div style="background: #666; width: 100%; height: 100%; position:fixed; z-index: 1; opacity: 0.2;">

		</div>
		<div style=" border: 1px solid #999; width: 240px; background: #FFF; color: #000; height: 35px; padding: 0px 15px; line-height: 35px; text-align: center; opacity: 1; position: absolute; left: 50%; top: 50%; z-index: 888;">
			<img src="<%=basePath%>static/images/spinner.gif" style="float: left; margin-top: 7px; width: 20px; height: 20px;" />正在绘图中,请稍等...
		</div>
	</div>
	<section id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />

		<SECTION id="main-content">
			<section class="wrapper"><div class="row"><div class="col-md-12"><div class="panel"><div class="panel-heading">
			<FORM class="form-inline" id="searchForm"  method="get" action="#">
         	<DIV class="form-group">
         	
         	<LABEL >基站:</LABEL>
         	<input  id="jizhanlist"  style="width:400px;" class="form-control"  type="text" placeholder="多组基站和dbm信号强度用英文逗号隔开 " />
         	
         	</DIV>
         	<DIV class="form-group">
         <input class="btn btn-primary" onclick="find2();" style="margin-left: 10px;" type="button" value="查询"/>
			</DIV>
			<DIV class="form-group">
			<LABEL style="margin-left: 15px;">报警距离间隔:</LABEL>
         	<input name="deviceColour" id="jdm" class="form-control" type="text" placeholder="输入报警距离" />m
         	</DIV>
         	<DIV class="form-group">
         <input class="btn btn-primary" onclick="setJDM();" style="margin-left: 10px;" type="button" value="设置"/>当前报警距离:<span id="bjjl" style="color: red;">未设置</span><span id="tip" style="color: red;margin-left: 20px;">提示:设置基点后请刷新页面</span>
         </DIV>
         	<DIV class="form-group"><label class="inline-label">地址：</label>
         	 <select name="orderSource" id="weizhijw" style="width: 90px;" class="form-control">
         	 <option value="31.2326026994,121.6667369291">银联大厦</option>
         	 </select>
         <input class="btn btn-primary" onclick="batchAddJZTemp();" id="zdbtn" style="margin-left: 10px;" type="button" />
			</DIV>
			<%-- 
         <div class="form-group">
          <label class="inline-label">国&nbsp;家：</label>
          <select name="mcc" id="mcc_id" style="width: 130px;"
          class="form-control">
            <option value="">全部国家</option>
            <c:forEach items="${Countries}" var="country" varStatus="status"><option value="${country.countryCode}">${country.countryName}</option></c:forEach>
          </select>
        </div>
        <div class="form-group">
          <label class="inline-label">本地信号小于：</label>
          <input style="width: 90px;"  id="gStrenth_id" class="form-control"  value=""  type="number" min="1" max="10" placeholder="不限" />
        </div>
        <div class="form-group">
          <label class="inline-label">漫游信号小于：</label>
          <input  style="width: 90px;" id="roamGStrenth_id" class="form-control"  value="" type="number" min="1" max="10" placeholder="不限" />
        </div>
         <input class="btn btn-primary" onclick="GStrenth()" style="margin-left: 10px;" type="button" value="显示信号弱区域"/>
         --%></FORM>
			</div><div class="panel-body">
			<pre>
			<div id="mapView" style="width:100%; height:800px;"></div>
			</pre></div></div></div></div></section>
		</SECTION>
	</section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script>
	getPosJz();
	function find2(){
		var jz=$("#jizhanlist").val();
		if(jz==''){
			easy2go.toast('warn', "请输入基站+信号强度进行搜索!");
			return;
		}
		$.ajax({
			type:"POST",
			url:"<%=basePath%>remote/getLatlngArr",
			dataType:"json",
			async:false,
			data:{jizhan:jz},
			success:function(data){
				if(data.cause=="OK"){
					jw= data.lat+"|"+data.lon;
					if(jw!=null){
						//画地图
						var jrr=jw.split("|");
						var lat=jrr[0];
						var lon=jrr[1];
						var addr=jrr[2];
							deleteOverlays();
							closeWindows();
							map=null;
							latlng=new google.maps.LatLng(lat,lon);
							var mapOptions={zoom: 12,center:latlng,mapTypeId: google.maps.MapTypeId.ROADMAP};
							map = new google.maps.Map(document.getElementById('mapView'),mapOptions);
							marker= new google.maps.Marker({
							    map:map,
							    position:latlng,
							    title:jw
							});
							var infowindow =  new google.maps.InfoWindow({ 
							   	content:addr
							   	});
							markersArray.push(marker);
							windowArray.push(infowindow);
							infowindow.open(map,marker);
					}
				}else{
					easy2go.toast('warn', data.cause);
					
				}
			},error:function(){
				easy2go.toast('warn', "位置获取失败!");
			}
		});
		
	}
	
	//获取POS机位置
	function getPosJz(){
		$.ajax({
			type:"POST",
			url:"<%=basePath%>remote/toPosMap",
			dataType:"json",
			async:false,
			success:function(dataS){
				if(dataS!="-1"){
					for(var i=0;i<dataS.length;i++){
						var data=dataS[i];
					jw= data.lat+"|"+data.lon;
					var sn=data.SN;
					if(jw!=null){
						//画地图
						var jrr=jw.split("|");
						var lat=jrr[0];
						var lon=jrr[1];
						
						if(data.jdm!=null && data.jdm!=''){
							$("#bjjl").html(data.jdm+"米");
						}
						if(i==0){
							deleteOverlays();
							closeWindows();
							map=null;
							latlng=new google.maps.LatLng(lat,lon);
							var mapOptions={zoom: 12,center:latlng,mapTypeId: google.maps.MapTypeId.ROADMAP};
							map = new google.maps.Map(document.getElementById('mapView'),mapOptions);
							if(data.jd!='' && data.jd!=null){
								
								latlng2=new google.maps.LatLng(data.jd.split(",")[0],data.jd.split(",")[1]);
								marker2= new google.maps.Marker({
								    map:map,
								    position:latlng2,
								    title:jw
								});
								var infowindow2 =  new google.maps.InfoWindow({ 
								   	content:"基点：POS机序列号"+sn
								   	});
								markersArray.push(marker2);
								windowArray.push(infowindow2);
								infowindow2.open(map,marker2);
							}
							marker= new google.maps.Marker({
							    map:map,
							    position:latlng,
							    title:jw
							});
							var infowindow =  new google.maps.InfoWindow({ 
							   	content:"POS机序列号"+sn+",距离基点"+data.jdmjl+"米，"+'<a data-sn="'+sn+'" data-lat="'+data.lat+'" data-lon="'+data.lon+'" onclick="setLoc(this)" href="javascript:;" >设为基点</a>'
							   	});
							markersArray.push(marker);
							windowArray.push(infowindow);
							infowindow.open(map,marker);
						}else{
							if(data.jd!='' && data.jd!=null){
								
								latlng2=new google.maps.LatLng(data.jd.split(",")[0],data.jd.split(",")[1]);
								marker2= new google.maps.Marker({
								    map:map,
								    position:latlng2,
								    title:jw
								});
								var infowindow2 =  new google.maps.InfoWindow({ 
								   	content:"基点：POS机序列号"+sn
								   	});
								markersArray.push(marker2);
								windowArray.push(infowindow2);
								infowindow2.open(map,marker2);
							}
							latlng=new google.maps.LatLng(lat,lon);
							marker= new google.maps.Marker({
							    map:map,
							    position:latlng,
							    title:jw
							});
							var infowindow =  new google.maps.InfoWindow({ 
								content:"POS机序列号"+sn+",距离基点"+data.jdmjl+"米，"+'<a data-sn="'+sn+'" data-lat="'+data.lat+'" data-lon="'+data.lon+'" onclick="setLoc(this)" href="javascript:;" >设为基点</a>'
							   	});
							markersArray.push(marker);
							windowArray.push(infowindow);
							infowindow.open(map,marker);
						}
						var  latlng1=new google.maps.LatLng(lat,lon);
						marker1= new google.maps.Marker({
						    map:map,
						    position:latlng1,
						    title:jw
						});
						
					}
					}
				}else{
					easy2go.toast('warn', "今日暂无POS机在线过");
					
				}
			},error:function(){
				easy2go.toast('warn', "位置获取失败!");
			}
		});
	}
	
		function find(){
			var jz=$("#jizhanlist").val();
			if(jz==''){
				easy2go.toast('warn', "请输入基站进行搜索!");
				return;
			}
			
			if(jz.indexOf(",")>-1){
				var jzrr=jz.split(",");
				
				for(var i=0;i<jzrr.length;i++){
					var jw;
					$.ajax({
						type:"POST",
						url:"<%=basePath%>remote/getLatlng",
						dataType:"json",
						async:false,
						data:{jizhan:jzrr[i]},
						success:function(data){
							if(data.cause=="OK"){
								jw= data.lat+"|"+data.lon+"|"+data.address;
								if(jw!=null){
									//画地图
									var jrr=jw.split("|");
									var lat=jrr[0];
									var lon=jrr[1];
									var addr=jrr[2];
									if(i==0){
										deleteOverlays();
										closeWindows();
										map=null;
										latlng=new google.maps.LatLng(lat,lon);
										var mapOptions={zoom: 6,center:latlng,mapTypeId: google.maps.MapTypeId.ROADMAP};
										map = new google.maps.Map(document.getElementById('mapView'),mapOptions);
										marker= new google.maps.Marker({
										    map:map,
										    position:latlng,
										    title:jw
										});
										var infowindow =  new google.maps.InfoWindow({ 
										   	content:addr
										   	});
										markersArray.push(marker);
										windowArray.push(infowindow);
										infowindow.open(map,marker);
									}else{
										latlng=new google.maps.LatLng(lat,lon);
										marker= new google.maps.Marker({
										    map:map,
										    position:latlng,
										    title:jw
										});
										var infowindow =  new google.maps.InfoWindow({ 
										   	content:addr
										   	});
										markersArray.push(marker);
										windowArray.push(infowindow);
										infowindow.open(map,marker);
									}
									var  latlng1=new google.maps.LatLng(lat,lon);
									marker1= new google.maps.Marker({
									    map:map,
									    position:latlng1,
									    title:jw
									});
									
								}
							}else{
								easy2go.toast('warn', data.cause);
								
							}
						},error:function(){
							easy2go.toast('warn', "位置获取失败!");
						}
					});
					
				}
			}else{
				var jw;
				$.ajax({
					type:"POST",
					url:"<%=basePath%>remote/getLatlng",
					dataType:"json",
					async:false,
					data:{jizhan:jz},
					success:function(data){
						if(data.cause=="OK"){
							jw=data.lat+"|"+data.lon+"|"+data.address;
							if(jw!=null){
								//画地图
								var jrr=jw.split("|");
								var lat=jrr[0];
								var lon=jrr[1];
								var addr=jrr[2];
								//alert("lat:"+lat+" lon:"+lon+" addr:"+addr);
								
								deleteOverlays();
								closeWindows();
								map=null;
								latlng=new google.maps.LatLng(lat,lon);
								var mapOptions={zoom: 6,center:latlng,mapTypeId: google.maps.MapTypeId.ROADMAP};
								//var icon = new google.maps.MarkerImage("/images/client/o1.png", new google.maps.Size(30, 30));
								map = new google.maps.Map(document.getElementById('mapView'),mapOptions);
								marker= new google.maps.Marker({
								    map:map,
								    position:latlng,
								    title:jz
								});
								var infowindow =  new google.maps.InfoWindow({ 
								   	content:addr
								   	});
								markersArray.push(marker);
								windowArray.push(infowindow);
								infowindow.open(map,marker);
							}
						}else{
							easy2go.toast('warn', data.cause);
						}
					},error:function(){
						easy2go.toast('warn', "位置获取失败!");
					}
				});
			}
			
		}
		
		function getlat(jizhan){
			$.ajax({
				type:"POST",
				url:"<%=basePath%>remote/getLatlng",
				dataType:"json",
				data:{jizhan:jizhan},
				success:function(data){
					if(data.cause=="OK"){
						return data.lat+"|"+data.lon+"|"+data.address;
					}else{
						easy2go.toast('warn', data.cause);
						return null;
					}
				},error:function(){
					easy2go.toast('warn', "位置获取失败!");
					return null;
				}
			});
			
		}

		//删除标记
	      function deleteOverlays() {
	          if (markersArray) {
	            for (i in markersArray) {
	              markersArray[i].setMap(null);
	            }
	            markersArray.length = 0;
	          }
	      }
	 
	      //关闭信息提示窗口
	      function closeWindows() {
	          if (windowArray) {
	            for (i in windowArray) {
	                windowArray[i].close();
	            }
	            windowArray.length = 0;
	          }
	         }
	      
	      //SN基站轨迹
	      function getJZBySN(){
	    	  var sn=$("#SN").val().trim();
	    	  if(sn.trim()==''){
	    		  easy2go.toast('warn', "请输入SN!");
	    		  return;
	    	  }
	    	  map=null;
			  deleteOverlays();
			  closeWindows();
			  $("#D_display").show();
	    	  $.ajax({
					type:"POST",
					url:"<%=basePath%>remote/getLatlngBySN",
					dataType:"json",
					data:{SN:sn},
					async:false,
					success:function(data){
						
						if(data.length>0){
							var MVCArray=new google.maps.MVCArray(null);
							for(var i=0;i<data.length;i++){
								
								if(i==0){
									latlng=new google.maps.LatLng(data[i].lat,data[i].lon);
									MVCArray.setAt(i,latlng);
									var mapOptions={zoom: 15,center:latlng,mapTypeId: google.maps.MapTypeId.ROADMAP};
									//var icon = new google.maps.MarkerImage("/images/client/o1.png", new google.maps.Size(30, 30));
									map = new google.maps.Map(document.getElementById('mapView'),mapOptions);
									marker= new google.maps.Marker({
									    map:map,
									    position:latlng,
									    title:""+(i+1)
									});
									infowindow = new google.maps.InfoWindow({ 
									   	content:"起始"
									   	});
									infowindow.open(map,marker);
									markersArray.push(marker);
									windowArray.push(infowindow);
									
								}else if(i==data.length-1){
									latlng=new google.maps.LatLng(data[i].lat,data[i].lon);
									MVCArray.setAt(i,latlng);
									marker= new google.maps.Marker({
									    map:map,
									    position:latlng,
									    title:""+(i+1)
									});
									infowindow = new google.maps.InfoWindow({ 
									   	content:"结束"
									   	});
									infowindow.open(map,marker);
									markersArray.push(marker);
									windowArray.push(infowindow);
									
								}else{
									latlng=new google.maps.LatLng(data[i].lat,data[i].lon);
									MVCArray.setAt(i,latlng);
									marker= new google.maps.Marker({
									    map:map,
									    position:latlng,
									    title:""+(i+1)
									});
									markersArray.push(marker);
									
								}
							}
							//画轨迹
							var lineArea = new google.maps.Polyline({
								path:MVCArray,
								strokeColor: '#006400',
							    strokeOpacity: 1.0,
							    strokeWeight: 5,
							    geodesic:false,
							});
							lineArea.setMap(map);
							$("#D_display").css("display", "none");
						}else{
							easy2go.toast('warn', "数据返回错误!");
						}
					},error:function(){
						easy2go.toast('warn', "查询失败!");
						return null;
					}
				});
	      }
	     
	     /* 信号强弱分析 */
	     function GStrenth(){
	    	 var mcc=$("#mcc_id").val();
	    	 var gStrenth=$("#gStrenth_id").val();
	    	 var roamGStrenth=$("#roamGStrenth_id").val();
	    	 $("#D_display").css("display", "");
	    	 $.ajax({
					type:"POST",
					url:"<%=basePath%>remote/GStrenth",
					dataType:"json",
					async:false,
					data:{MCC:mcc,gStrenth:gStrenth,roamGStrenth:roamGStrenth},
					success:function(data){
						if(data!='0'){
							//开始绘图
							 map=null;
							 deleteOverlays();
							 closeWindows();
							 for(var i=0;i<data.length;i++){
								 if(i==0){
									latlng=new google.maps.LatLng(data[i].lat,data[i].lon);
									var mapOptions={zoom: 8,center:latlng,mapTypeId: google.maps.MapTypeId.ROADMAP};
									//var icon = new google.maps.MarkerImage("/images/client/o1.png", new google.maps.Size(30, 30));
									map = new google.maps.Map(document.getElementById('mapView'),mapOptions);
									myCityC = new google.maps.Circle({
										center:latlng,
										  radius:1000,
										  strokeColor:"#575757",
										  strokeOpacity:0.8,
										  strokeWeight:2,
										  fillColor:"#B5B5B5",
										  fillOpacity:0.2
										});
									myCityC.setMap(map);
									markersArray.push(myCityC);
									marker= new google.maps.Marker({
									    map:map,
									    position:latlng,
									    title:""
									});
									markersArray.push(marker);
								 }else{
									 latlng=new google.maps.LatLng(data[i].lat,data[i].lon);
									 myCityC = new google.maps.Circle({
										  center:latlng,
										  radius:1000,
										  strokeColor:"#575757",
										  strokeOpacity:0.8,
										  strokeWeight:2,
										  fillColor:"#B5B5B5",
										  fillOpacity:0.2
										});
									 myCityC.setMap(map);
									markersArray.push(myCityC);
									marker= new google.maps.Marker({
									    map:map,
									    position:latlng,
									    title:""
									});
									markersArray.push(marker);
								 }
							 }
							 $("#D_display").css("display", "none");
						}else{
							easy2go.toast('warn', "查询失败!");
						}
					},error:function(){
						easy2go.toast('warn', "查询失败!");
						
					}	
	    	 });	
	     }
	     
	     
	     function setLoc(t){
	    	 var lat=$(t).attr("data-lat");
	    	 var lon=$(t).attr("data-lon");
	    	 var lt=lat+","+lon;
	    	 $.ajax({
					type:"POST",
					url:"<%=basePath%>remote/setLoc",
					dataType:"html",
					async:false,
					data:{remark:lt},
					success:function(data){
						if(data=='1'){
							alert("设置成功");
						}else{
							alert("设置失败");
						}
					
					}
	    	 });
	     }
	     
	     function setLoc(t){
	    	 var lat=$(t).attr("data-lat");
	    	 var lon=$(t).attr("data-lon");
	    	 var lt=lat+","+lon;
	    	 var sn=$(t).attr("data-sn");
	    	 $.ajax({
					type:"POST",
					url:"<%=basePath%>remote/setLoc",
					dataType:"html",
					async:false,
					data:{remark:lt,SN:sn},
					success:function(data){
						if(data=='1'){
							alert("设置成功");
						}else{
							alert("设置失败");
						}
					}
	    	 });
	     }
	     
	     function setJDM(){
	    	 var jdm=$("#jdm").val();
	    	 $.ajax({
					type:"POST",
					url:"<%=basePath%>remote/setLoc",
					dataType:"html",
					async:false,
					data:{deviceColour:jdm},
					success:function(data){
						if(data=='1'){
							alert("设置成功");
							$("#bjjl").html(jdm+"米");
						}else{
							alert("设置失败");
						}
					}
	    	 });
	     }
	     
	     var ifopen=${ifopen};
	     
	     if(ifopen==true){
	     	JZflag="0";
	     	$("#zdbtn").val("关闭自动收集基站");
	     	
	     }else{
	    	 
	     	JZflag="1";
	     	$("#zdbtn").val("开启自动收集基站");
	     }
	     
	     //导入数据.
	     function batchAddJZTemp(){
	    	 $.ajax({
					type:"POST",
					url:"<%=basePath%>remote/batchAddJZTempBtn",
					dataType:"html",
					async:false,
					data:{ifopen:JZflag,dw:$("#weizhijw").val()},
					success:function(data){
						data=parseInt(data);
						if(data==1){
							JZflag="0";
							$("#zdbtn").val("关闭自动收集基站");
						}else if(data==2){
							$("#zdbtn").val("开启自动收集基站");
						}else{
							alert("操作失败");
						}
					}
	    	 });
	     }
	</script>
	
	
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>