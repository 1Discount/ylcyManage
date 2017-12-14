<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">

<head>

<title>全部流量订单-订单管理-流量运营中心</title>

<meta charset="utf-8">

<meta http-equiv="X-UA-Compatible" content="IE=edge">

<meta name="viewport"content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	
<!--这个css影响到了下面，在拖动的时候  -->
<link rel="stylesheet" href="<%=basePath%>static/css/app.min.css?20150209"> 
 
<link rel="stylesheet" href="<%=basePath%>static/css/gooflow/default.css">

<link rel="stylesheet" href="<%=basePath%>static/css/gooflow/GooFlow2.css">

<style>
.myForm {
	display: block;
	margin: 0px;
	padding: 0px;
	line-height: 1.5;
	border: #ccc 1px solid;
	font: 12px Arial, Helvetica, sans-serif;
	margin: 5px 5px 0px 0px;
	border-radius: 4px;
}
   .GooFlow_tool,.GooFlow_head,.myForm,.export{
	 display:none; 
	 position: absolute;
	 top:800px;
}
.export{
	display:none;
}   
.myForm .form_title {
	background: #428bca;
	padding: 4px;
	color: #fff;
	border-radius: 3px 3px 0px 0px;
}

.myForm .form_content {
	padding: 4px;
	background: #fff;
}

.myForm .form_content table {
	border: 0px
}

.myForm .form_content table td {
	border: 0px
}

.myForm .form_content table .th {
	text-align: right;
	font-weight: bold
}

.myForm .form_btn_div {
	text-align: center;
	border-top: #ccc 1px solid;
	background: #f5f5f5;
	padding: 4px;
	border-radius: 0px 0px 3px 3px;
}

#propertyForm {
	float: right;
	width: 260px
}
</style>

<meta name="csrf_token">

<%@include file="/WEB-INF/views/common/_ie8support.html"%>

</head>
<body>
	<section id="container">
		<jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
		<jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />
		<SECTION id="main-content">
			<SECTION class="wrapper">
				<DIV class="col-md-12">
					<DIV class="panel">
						<div id="demo"></div>
						<form class="myForm" id="propertyForm">
							<div class="form_title">属性设置</div>
							<div class="form_content">
								<table>
									<tr>
										<td class="th">Id：</td>
										<td><input type="text" style="width: 120px" id="ele_id" /></td>
									</tr>
									<tr>
										<td class="th">Name：</td>
										<td><input type="text" style="width: 120px" id="ele_name" /></td>
									</tr>
									<tr>
										<td class="th">Type：</td>
										<td><input type="text" style="width: 120px" id="ele_type" /></td>
									</tr>
									<tr>
										<td class="th">Model：</td>
										<td><input type="text" style="width: 120px"
											id="ele_model" /></td>
									</tr>
									<tr>
										<td class="th">Left-r：</td>
										<td><input type="text" style="width: 120px" id="ele_left" /></td>
									</tr>
									<tr>
										<td class="th">Top-r：</td>
										<td><input type="text" style="width: 120px" id="ele_top" /></td>
									</tr>
									<tr>
										<td class="th">Width：</td>
										<td><input type="text" style="width: 120px"
											id="ele_width" /></td>
									</tr>
									<tr>
										<td class="th">Height：</td>
										<td><input type="text" style="width: 120px"
											id="ele_height" /></td>
									</tr>
									<tr>
										<td class="th">From：</td>
										<td><input type="text" style="width: 120px" id="ele_from" /></td>
									</tr>
									<tr>
										<td class="th">To：</td>
										<td><input type="text" style="width: 120px" id="ele_to" /></td>
									</tr>
								</table>
							</div>
							<div class="form_btn_div" >
								<input type="reset" value="重置" /> <input type="button"
									value="确定" onclick="update();" />
							</div>
						</form>
						<div style="clear: both" class="export">
							<input id="submit" type="button" value='导出结果' onclick="Export()" />
							<textarea id="result" row="6"></textarea>
						</div>

					</DIV>
				</DIV>
			</SECTION>
		</SECTION>
	</section>
	
	
	<script src="<%=basePath%>static/js/gooflow/child.js"></script>
	<%-- <script src="<%=basePath%>static/js/gooflow/jquery.min.js"></script> --%>
	<script src="<%=basePath%>static/js/gooflow/GooFunc.js"></script>
	<script src="<%=basePath%>static/js/gooflow/json2.js"></script>
	<script src="<%=basePath%>static/js/gooflow/GooFlow.js"></script>
	<script src="<%=basePath%>static/js/gooflow/GooFlow_color.js"></script>
    
    <%--<script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
	<script src="<%=basePath%>static/js/grid/bsgrid.all.min1.js"></script> --%>
	<%-- <script type="text/javascript" src="<%=basePath%>static/js/jquery-ui.min.js"></script> --%>
    <script src="<%=basePath%>static/js/app.min.js?20150209"></script>
	<script type="text/javascript">
		var property = {
			width : 1300,
			height : 700,
			toolBtns : [ "start round", "end round", "task round", "node","chat", "state", "plug", "join", "fork", "complex mix" ],
			haveHead : true,
			headBtns : [ "new", "open", "save", "undo", "redo", "reload" ],//如果haveHead=true，则定义HEAD区的按钮
			haveTool :true,
			haveGroup : true,
			useOperStack : true
		};
		
		var remark = {
			cursor : "选择指针",
			direct : "结点连线",
			start : "入口结点",
			"end" : "结束结点",
			"task" : "任务结点",
			node : "自动结点",
			chat : "决策结点",
			state : "状态结点",
			plug : "附加插件",
			fork : "分支结点",
			"join" : "联合结点",
			"complex mix" : "复合结点",
			group : "组织划分框编辑开关"
		};
		
		GooFlow.prototype.color={
				  main:"#A8C508",
				  node:"#AEEEEE",
				  line:"#A8C508",
				  mark:"#F00",
				  mix:"#B6F700",
				  font:"#357425"
				}; 
		
		var demo;
		var json ;
		
		$(document).ready(function(){
			
			demo = $.createGooFlow($("#demo"),property);
			
			demo.setNodeRemarks(remark);
			
			/** 禁止删除**/
			demo.onItemDel = function(id, type) {
				
				return true;
				
			};
			
			  json ={
					    "title": "订单流程",
					    "nodes": {
					        "demo_node_1": {
					            "name": "客服接单",
					            "left": 301,
					            "top": 132,
					            "type": "join",
					            "width": 100,
					            "height": 54,
					            "alt": true,
					        },
					        "demo_node_2": {
					            "name": "根据接单信息分配设备创建订单",
					            "left": 481,
					            "top": 130,
					            "type": "join",
					            "width": 100,
					            "height": 57,
					            "alt": true,
					        },
					        "demo_node_3": {
					            "name": "取货方式（自取/快递）",
					            "left": 666,
					            "top": 269,
					            "type": "join round",
					            "width": 24,
					            "height": 24,
					            "alt": true,
					        },
					        "demo_node_4": {
					            "name": "设备出库/物流",
					            "left": 796,
					            "top": 127,
					            "type": "join",
					            "width": 122,
					            "height": 61,
					            "alt": true
					        },
					        "demo_node_5": {
					            "name": "售后服务记录/退款/退货",
					            "left": 1017,
					            "top": 129,
					            "type": "join",
					            "width": 102,
					            "height": 62,
					            "alt": true
					        },
					        "demo_node_6": {
					            "name": "归还设备/入库",
					            "left": 1231,
					            "top": 133,
					            "type": "join",
					            "width": 112,
					            "height": 60,
					            "alt": true
					        },
					        "demo_node_7": {
					            "name": "结束",
					            "left": 1436,
					            "top": 157,
					            "type": "end round",
					            "width": 24,
					            "height": 24,
					            "alt": true
					        },
					        "demo_node_8": {
					            "name": "线上网店",
					            "left": 199,
					            "top": 145,
					            "type": "task round",
					            "width": 24,
					            "height": 24,
					            "alt": true
					        },
					        "demo_node_9": {
					            "name": "",
					            "left": 91,
					            "top": 260,
					            "type": "start round",
					            "width": 24,
					            "height": 24,
					            "alt": true,
					        },
					        "demo_node_10": {
					            "name": "后台订单",
					            "left": 198,
					            "top": 384,
					            "type": "task round",
					            "width": 24,
					            "height": 24,
					            "alt": true
					        },
					        "demo_node_57": {
					            "name": "自取",
					            "left": 802,
					            "top": 246,
					            "type": "join",
					            "width": 116,
					            "height": 64,
					            "alt": true
					        }
					    },
					    "lines": {
					        "demo_line_3": {
					            "type": "sl",
					            "from": "demo_node_5",
					            "to": "demo_node_6",
					            "name": "快递",
					        },
					        "demo_line_4": {
					            "type": "sl",
					            "from": "demo_node_6",
					            "to": "demo_node_7",
					            "name": "",
					        },
					        "demo_line_47": {
					            "type": "sl",
					            "from": "demo_node_9",
					            "to": "demo_node_8",
					            "name": "",
					        },
					        "demo_line_48": {
					            "type": "sl",
					            "from": "demo_node_9",
					            "to": "demo_node_10",
					            "name": "",
					        },
					        "demo_line_49": {
					            "type": "sl",
					            "from": "demo_node_8",
					            "to": "demo_node_1",
					            "name": "",
					        },
					        "demo_line_50": {
					            "type": "sl",
					            "from": "demo_node_1",
					            "to": "demo_node_2",
					            "name": "",
					        },
					        "demo_line_53": {
					            "type": "tb",
					            "M": 398.5,
					            "from": "demo_node_10",
					            "to": "demo_node_3",
					            "name": "",
					        },
					        "demo_line_51": {
					            "type": "tb",
					            "M": 155.75,
					            "from": "demo_node_3",
					            "to": "demo_node_4",
					            "name": "快递",
					        },
					        "demo_line_56": {
					            "type": "tb",
					            "M": 281.75,
					            "from": "demo_node_2",
					            "to": "demo_node_3",
					            "name": "",
					        },
					        "demo_line_58": {
					            "type": "sl",
					            "from": "demo_node_3",
					            "to": "demo_node_57",
					            "name": "",
					        },
					        "demo_line_59": {
					            "type": "tb",
					            "from": "demo_node_57",
					            "to": "demo_node_5",
					            "name": "",
					            "alt": true,
					            "M": 279,
					        },
					        "demo_line_60": {
					            "type": "sl",
					            "from": "demo_node_4",
					            "to": "demo_node_5",
					            "name": "",
					            "alt": true,
					        }
					    },
					    "areas": {},
					    "initNum": 61
					};
			 
			 demo.onItemFocus = function(id, model) {
				 
				var obj;
				
				$("#ele_model").val(model);
				
				$("#ele_id").val(id);
				
				if (model == "line") {
					
					obj = this.$lineData[id];
					
					$("#ele_type").val(obj.M);
					
					$("#ele_left").val("");
					
					$("#ele_top").val("");
					
					$("#ele_width").val("");
					
					$("#ele_height").val("");
					
					$("#ele_from").val(obj.from);
					
					$("#ele_to").val(obj.to);
					
				} else if (model == "node"){
					
					obj = this.$nodeData[id];
					
					$("#ele_type").val(obj.type);
					
					$("#ele_left").val(obj.left);
					
					$("#ele_top").val(obj.top);
					
					$("#ele_width").val(obj.width);
					
					$("#ele_height").val(obj.height);
					
					$("#ele_from").val("");
					
					$("#ele_to").val("");
					
				}
				
				$("#ele_name").val(obj.name); 
				
				//window.location.href='http://www.baidu.com';
				
				return true;
				
			}; 
			
			
			demo.onItemBlur = function(id, model) {
				
				document.getElementById("propertyForm").reset();
				return true;
				
			};
			demo.onItemResize=function(id,type,width,height){
				return true;
			};
		});
		
		var out;
		
		function Export() {
			
			document.getElementById("result").value = JSON.stringify(demo.exportData());
			
		}
		
		function update(){

			//var workFlowStatus='${workFlowStatus}';
			var workFlowStatus="发货";
			
			//var orderSource="${orderSource}";
			var orderSource="后台";
			
			//var shipmentType="${shipmentType}";
			var shipmentType="自提自取";
			
			if(orderSource=="线上网店"){
				
				if(workFlowStatus=="接单"){

					json.nodes.demo_node_1.marked=true;
					json.nodes.demo_node_8.marked=true;
					json.nodes.demo_node_9.marked=true;
					
					json.lines.demo_line_47.marked=true;
					json.lines.demo_line_49.marked=true;
					
				}else if(workFlowStatus=="下单"){

					json.nodes.demo_node_1.marked=true;
					json.nodes.demo_node_8.marked=true;
					json.nodes.demo_node_9.marked=true;
					json.nodes.demo_node_2.marked=true;
					
					json.lines.demo_line_47.marked=true;
					json.lines.demo_line_49.marked=true;
					json.lines.demo_line_50.marked=true;
					
				}else if(workFlowStatus=="发货"){
					
					if(shipmentType=="快递"){
						
						kdInit();
						
					}else if(shipmentType="自提自取"){
						
						zitiInit();
						
					}
					
					json.nodes.demo_node_8.marked=true;
					
					
				}else if(workFlowStatus=="归还"){
					
					if(shipmentType=="快递"){
						
						kdInit();
						
					}else if(shipmentType="自提自取"){
						
						zitiInit();
						
					}
					
					json.nodes.demo_node_6.marked=true;
					json.nodes.demo_node_7.marked=true;
					
					json.lines.demo_line_3.marked=true;
					json.lines.demo_line_4.marked=true;
					
				}else if(workFlowStatus=="结束"){
					
					if(shipmentType=="快递"){
						
						kdInit();
						
					}else if(shipmentType="自提自取"){
						
						zitiInit();
						
					}
					
					json.nodes.demo_node_6.marked=true;
					json.nodes.demo_node_7.marked=true;
					
					json.lines.demo_line_3.marked=true;
					json.lines.demo_line_4.marked=true;
					
				}
				
			}else if(orderSource=="后台"){
				
				if(workFlowStatus=="发货"){
					
					if(shipmentType=="快递"){
						
						htKdInit();
						
					}else if(shipmentType="自提自取"){

						htZitiInit();
						
					}
					
				}else if(workFlowStatus=="归还"){
					
					if(shipmentType=="快递"){
						
						htKdInit();
						
					}else if(shipmentType="自提自取"){
						
						htZitiInit();
						
					}

					json.nodes.demo_node_6.marked=true;
					json.nodes.demo_node_7.marked=true;
					
					json.lines.demo_line_3.marked=true;
					json.lines.demo_line_4.marked=true;
					
				}else if(wordFlowStatus=="结束"){
					
					if(shipmentType=="快递"){
						
						htKdInit();
						
					}else if(shipmentType="自提自取"){
						
						htZitiInit();
						
					}
					
					json.nodes.demo_node_6.marked=true;
					json.nodes.demo_node_7.marked=true;
					
					json.lines.demo_line_3.marked=true;
					json.lines.demo_line_4.marked=true;
					
				}
			}
			
			demo.loadData(json);
		}
		
		function htKdInit(){
			
			json.nodes.demo_node_3.marked=true;
			json.nodes.demo_node_4.marked=true;
			json.nodes.demo_node_10.marked=true;
			json.nodes.demo_node_9.marked=true;
			json.nodes.demo_node_5.marked=true;

			json.lines.demo_line_48.marked=true;
			json.lines.demo_line_53.marked=true;
			json.lines.demo_line_51.marked=true;
			json.lines.demo_line_60.marked=true;
			
		}
		
		function htZitiInit(){

			json.nodes.demo_node_57.marked=true;
			json.nodes.demo_node_10.marked=true;
			json.nodes.demo_node_9.marked=true;
			json.nodes.demo_node_5.marked=true;
			json.nodes.demo_node_3.marked=true;

			json.lines.demo_line_48.marked=true;
			json.lines.demo_line_53.marked=true;
			json.lines.demo_line_48.marked=true;
			json.lines.demo_line_53.marked=true;
			json.lines.demo_line_58.marked=true;
			json.lines.demo_line_59.marked=true;
			
		}
		
		function kdInit(){

			json.nodes.demo_node_1.marked=true;
			json.nodes.demo_node_8.marked=true;
			json.nodes.demo_node_9.marked=true;
			json.nodes.demo_node_2.marked=true;
			json.nodes.demo_node_3.marked=true;
			json.nodes.demo_node_4.marked=true;
			json.nodes.demo_node_5.marked=true;
			
		    json.lines.demo_line_47.marked=true;
			json.lines.demo_line_49.marked=true;
			json.lines.demo_line_50.marked=true;
			json.lines.demo_line_56.marked=true;
			json.lines.demo_line_51.marked=true;
			json.lines.demo_line_60.marked=true;
			
		}
		
		function zitiInit(){
			
			json.nodes.demo_node_1.marked=true;
			json.nodes.demo_node_8.marked=true;
			json.nodes.demo_node_9.marked=true;
			json.nodes.demo_node_2.marked=true;
			json.nodes.demo_node_3.marked=true;
			json.nodes.demo_node_57.marked=true;
			json.nodes.demo_node_5.marked=true;
			
			json.lines.demo_line_47.marked=true;
			json.lines.demo_line_49.marked=true;
			json.lines.demo_line_50.marked=true;
			json.lines.demo_line_56.marked=true;
			json.lines.demo_line_55.marked=true;
			json.lines.demo_line_58.marked=true;
			json.lines.demo_line_59.marked=true;
			
		}
	 	 $(function(){
	 		 
 		    $("#demo").css({'width':'100%','height':'770px'});
 
			$("#draw_demo").css({'width':'100%','height':'100%','float':'left'});
 
			$(".GooFlow_work").css({'width':'100%','height':'100%','float':'none','overflow':'hidden'});
			 
			 
			/*初始化*/
			update();
			
		});  
	</script>
	
	 <jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
	
		<jsp:param name="matchType" value="exactly" />
		
	</jsp:include>
	
</body>

</html>

