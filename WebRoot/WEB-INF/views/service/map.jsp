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
<title>全部客户-设备日志-EASY2GO ADMIN</title>
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
</script>

</head>
<body>
<div id="checkCodeDiv" style="z-index:10000;text-align: center;display:none;position: absolute;left:25%;top:2%;">
	<input id="checkCodeInput" width="200px" placeholder="这里输入验证码">
	<img id="checkCodeImg" onclick="changeCheckCodeImg()" alt="来自www.minigps.net的验证码" title="来自www.minigps.net的验证码" src="<%=basePath%>static/images/YZMLoading.png">
	<p>提示：当输入四位验证码时会自动提交查询</p>
</div>
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
         	<input  id="jizhanlist"  style="width:400px;" class="form-control"  type="text" placeholder="多个基站用英文逗号隔开 " />
         	
         	</DIV>
         	<DIV class="form-group">
         <input class="btn btn-primary" onclick="serachJz();" style="margin-left: 10px;" type="button" value="查询"/>
			</DIV>
			<DIV class="form-group">
			<LABEL style="margin-left: 15px;">机身码:</LABEL>
         	<input  id="SN" class="form-control" type="text" placeholder="输入15位机身码显示最近10个轨迹" />
         	</DIV>
         	<DIV class="form-group">
         <input class="btn btn-primary" onclick="getJZBySN();" style="margin-left: 10px;" type="button" value="显示轨迹"/>
         </DIV>
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
        </FORM>
			</div><div class="panel-body">
			<pre>
			<div id="mapView" style="width:100%; height:800px;"></div>
			</pre></div></div></div></div></section>
		</SECTION>
	</section>
	<script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script>
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
	      
	      //机身码基站轨迹
	      function getJZBySN(){
	    	  var sn=$("#SN").val().trim();
	    	  if(sn.trim()==''){
	    		  easy2go.toast('warn', "请输入机身码!");
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
	     
	     var jizhan="${jizhanBak}";
	     
		//监听验证码输入
		$("#checkCodeInput").keyup(function(){
   			if($("#checkCodeInput").val().length>=4){
   				easy2go.toast('warn', "已自动提交查询！");
   				$("#checkCodeInput").attr("disabled",true);
   				findByCheckCode();
   			}
		});
	    function changeCheckCodeImg(){
	    	document.getElementById("checkCodeImg").src="<%=basePath%>static/images/YZMLoading.png";
	        document.getElementById("checkCodeImg").src="<%=basePath%>remote/getCheckCodeImg?a="+Math.random();
	        $("#checkCodeInput").removeAttr("disabled");
  			$("#checkCodeInput").val("");
  			$("#checkCodeInput").focus();
	     }
	     function D_displayLoading(a){
	    	 if(a){
	    		 $("#D_display").css("display", "block");
	    	 }else{
	    		 $("#D_display").css("display", "none");
	    	 }
	     }
	     jizhantojw();
	   	//获取基站信息
	     function jizhantojw(){
	    	 $.ajax({
	    		type:"GET",
		     	url:"<%=basePath%>remote/jizhantojw?jizhan="+jizhan,
		     		beforeSend:D_displayLoading(true),
		     		success:function(data){
		     			if(data.status=="OK"){
		     				$("#checkCodeDiv").css("display","none");
		     				drawMap(data.lat,data.lon,data.address);
		     			}else{
		     				easy2go.toast('warn', "自动获取基站信息失败，请输入验证码进行查询");
		     		    	$("#checkCodeDiv").css("display","block");
		     		    	$("#checkCodeInput").focus();
		     		    	changeCheckCodeImg();
		     			}
		     			D_displayLoading(false);
		     		}
	    	 });
	     }
			    
	     function serachJz(){
	    	 jizhan=$("#jizhanlist").val();
				if(jizhan==''){
					easy2go.toast('warn', "请输入基站进行搜索!");
					return;
				}
				if(jizhan.indexOf(",")>-1){
					var jzrr=jz.split(",");
					jizhan=jzrr[0];
				}
				jizhantojw();
	     }
	   //画地图
	     function drawMap(lat,lon,address){
		    	deleteOverlays();
				closeWindows();
				map=null;
				latlng=new google.maps.LatLng(lat,lon);
				var mapOptions={zoom: 6,center:latlng,mapTypeId: google.maps.MapTypeId.ROADMAP};
				map = new google.maps.Map(document.getElementById('mapView'),mapOptions);
				marker= new google.maps.Marker({
				    map:map,
				    position:latlng,
				    title:lat+","+lon
				});
				var infowindow =  new google.maps.InfoWindow({ 
				   	content:address
				   });
				markersArray.push(marker);
				windowArray.push(infowindow);
				infowindow.open(map,marker);
		  }
	   
	    function findByCheckCode(){
		     	var checkCode=$("#checkCodeInput").val();
		     	$.ajax({
		     		type:"GET",
		     		url:"<%=basePath%>remote/getLatlngByCheckCode?jizhan="+jizhan+"&checkCode="+checkCode,
		     		beforeSend:D_displayLoading(true),
		     		success:function(data){
		     			switch(data){
		     			case 0 :{
		     				easy2go.toast('warn', "基站位置信息获取失败！");break;	
		     			}
		     			case 1 :{
		     				jizhantojw();break;	
		     			}
		     			case 2 :{
		     				easy2go.toast('warn', "验证码输入有误!请重新输入");break;	
		     			}
		     			case 3 :{
		     				easy2go.toast('warn', "基站信息不存在，请从手机上读取正确的基站信息");break;	
		     			}
		     			default:changeCheckCodeImg();
		     			}
		     			changeCheckCodeImg();
		     		}
		     	}); 
		      }
	    
	</script>
	
	
	<%-- 这里某些页面有内容, 见带  block script 的那些 jade 文件--%>
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true" />
</body>
</html>