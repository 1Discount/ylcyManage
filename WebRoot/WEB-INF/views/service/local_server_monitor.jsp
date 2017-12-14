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
<title>本地服务监控-流量运营中心</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
    content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet"
    href="<%=basePath%>static/css/app.min.css?20150209">
<link rel="stylesheet"
    href="<%=basePath%>static/css/grid/bsgrid.all.min.css">
    <!-- Custom styles for this template -->
    <link href="<%=basePath%>static/css/style.css" rel="stylesheet">
    
    <style type="text/css">
        
        .table1 {
            width: 100%;
            max-width: 100%;
            margin-bottom: 20px
        }
        
        .table1>thead>tr>th,.table1>tbody>tr>th,.table1>tfoot>tr>th,.table1>thead>tr>td,.table1>tbody>tr>td,.table1>tfoot>tr>td {
            padding: 8px;
            line-height: 1.5;
            vertical-align: top;
            border-bottom: 1px solid #ddd
        }
    </style>
    
<%@include file="/WEB-INF/views/common/_ie8support.html"%>
</head>
<body>
    
    <section id="container">
        <jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
        <jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />

        <SECTION id="main-content">
            <SECTION class="wrapper">
                <div class="col-md-12">
                   <section class="panel">
                       <header class="panel-heading" style="background-color:#ddd;">
                           <span style="font-size: 14px;"><strong>IP:192.168.0.110</strong></span>
                           <span class="tools pull-right">
                               <a href="javascript:;" class="fa fa-chevron-down"></a>
                               <a href="javascript:;" class="fa fa-times"></a>
                            </span>
                       </header>
                       <div class="panel-body">
                           <table class="table1" id="table1">
                               <thead>
                                   <tr>
                                       <th width="17%">#</th>
                                       <th width="17%">CPU使用率</th>
                                       <th width="17%">线程数</th>
                                       <th width="17%">占用内存</th>
                                       <th width="17%">PID</th>
                                       <th width="15%">操作</th>
                                   </tr>
                               </thead>
                               <tbody>
                                   <tr>
                                       <td width="17%">TDM</td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="15%"></td>
                                   </tr>
                                   <tr>
                                       <td width="17%">SPM</td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="14%"></td>
                                   </tr>
                                   <tr>
                                       <td width="17%">TOMCAT</td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="14%"></td>
                                   </tr>
                               </tbody>
                           </table>
                       </div>
                   </section>
                 </div>
                <div class="col-md-12">
                   <section class="panel">
                       <header class="panel-heading" style="background-color:#ddd;">
                           <span style="font-size: 14px;"><strong>IP:192.168.0.160</strong></span>
                           <span class="tools pull-right">
                               <a href="javascript:;" class="fa fa-chevron-down"></a>
                               <a href="javascript:;" class="fa fa-times"></a>
                            </span>
                       </header>
                       <div class="panel-body">
                           <table class="table1" id="table2">
                               <thead>
                                   <tr>
                                       <th width="17%">#</th>
                                       <th width="17%">CPU使用率</th>
                                       <th width="17%">线程数</th>
                                       <th width="17%">占用内存</th>
                                       <th width="17%">PID</th>
                                       <th width="15%">操作</th>
                                   </tr>
                               </thead>
                               <tbody>
                                   <tr>
                                       <td width="17%">TDM</td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="15%"></td>
                                   </tr>
                                   <tr>
                                       <td width="17%">SPM</td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="14%"></td>
                                   </tr>
                                   <tr>
                                       <td width="17%">TOMCAT</td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="14%"></td>
                                   </tr>
                               </tbody>
                           </table>
                       </div>
                   </section>
                 </div>
                <div class="col-md-12">
                   <section class="panel">
                       <header class="panel-heading" style="background-color:#ddd;">
                           <span style="font-size: 14px;"><strong>IP:192.168.0.161</strong></span>
                           <span class="tools pull-right">
                               <a href="javascript:;" class="fa fa-chevron-down"></a>
                               <a href="javascript:;" class="fa fa-times"></a>
                            </span>
                       </header>
                       <div class="panel-body">
                           <table class="table1" id="table3">
                               <thead>
                                   <tr>
                                       <th width="17%">#</th>
                                       <th width="17%">CPU使用率</th>
                                       <th width="17%">线程数</th>
                                       <th width="17%">占用内存</th>
                                       <th width="17%">PID</th>
                                       <th width="15%">操作</th>
                                   </tr>
                               </thead>
                               <tbody>
                                   <tr>
                                       <td width="17%">TDM</td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="15%"></td>
                                   </tr>
                                   <tr>
                                       <td width="17%">SPM</td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="14%"></td>
                                   </tr>
                                   <tr>
                                       <td width="17%">TOMCAT</td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="14%"></td>
                                   </tr>
                               </tbody>
                           </table>
                       </div>
                   </section>
                 </div>
                <div class="col-md-12">
                   <section class="panel">
                       <header class="panel-heading" style="background-color:#ddd;">
                           <span style="font-size: 14px;"><strong>IP:192.168.0.162</strong></span>
                           <span class="tools pull-right">
                               <a href="javascript:;" class="fa fa-chevron-down"></a>
                               <a href="javascript:;" class="fa fa-times"></a>
                            </span>
                       </header>
                       <div class="panel-body">
                           <table class="table1" id="table4">
                               <thead>
                                   <tr>
                                       <th width="17%">#</th>
                                       <th width="17%">CPU使用率</th>
                                       <th width="17%">线程数</th>
                                       <th width="17%">占用内存</th>
                                       <th width="17%">PID</th>
                                       <th width="15%">操作</th>
                                   </tr>
                               </thead>
                               <tbody>
                                   <tr>
                                       <td width="17%">TDM</td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="15%"></td>
                                   </tr>
                                   <tr>
                                       <td width="17%">SPM</td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="14%"></td>
                                   </tr>
                                   <tr>
                                       <td width="17%">TOMCAT</td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="14%"></td>
                                   </tr>
                               </tbody>
                           </table>
                       </div>
                   </section>
                 </div>
                <div class="col-md-12">
                   <section class="panel">
                       <header class="panel-heading" style="background-color:#ddd;">
                           <span style="font-size: 14px;"><strong>IP:192.168.0.163</strong></span>
                           <span class="tools pull-right">
                               <a href="javascript:;" class="fa fa-chevron-down"></a>
                               <a href="javascript:;" class="fa fa-times"></a>
                            </span>
                       </header>
                       <div class="panel-body">
                           <table class="table1" id="table5">
                               <thead>
                                   <tr>
                                       <th width="17%">#</th>
                                       <th width="17%">CPU使用率</th>
                                       <th width="17%">线程数</th>
                                       <th width="17%">占用内存</th>
                                       <th width="17%">PID</th>
                                       <th width="15%">操作</th>
                                   </tr>
                               </thead>
                               <tbody>
                                   <tr>
                                       <td width="17%">TDM</td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="15%"></td>
                                   </tr>
                                   <tr>
                                       <td width="17%">SPM</td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="14%"></td>
                                   </tr>
                                   <tr>
                                       <td width="17%">TOMCAT</td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="14%"></td>
                                   </tr>
                               </tbody>
                           </table>
                       </div>
                   </section>
                 </div>
                <div class="col-md-12">
                   <section class="panel">
                       <header class="panel-heading" style="background-color:#ddd;">
                           <span style="font-size: 14px;"><strong>IP:192.168.0.164</strong></span>
                           <span class="tools pull-right">
                               <a href="javascript:;" class="fa fa-chevron-down"></a>
                               <a href="javascript:;" class="fa fa-times"></a>
                            </span>
                       </header>
                       <div class="panel-body">
                           <table class="table1" id="table6">
                               <thead>
                                   <tr>
                                       <th width="17%">#</th>
                                       <th width="17%">CPU使用率</th>
                                       <th width="17%">线程数</th>
                                       <th width="17%">占用内存</th>
                                       <th width="17%">PID</th>
                                       <th width="15%">操作</th>
                                   </tr>
                               </thead>
                               <tbody>
                                   <tr>
                                       <td width="17%">TDM</td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="15%"></td>
                                   </tr>
                                   <tr>
                                       <td width="17%">SPM</td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="14%"></td>
                                   </tr>
                                   <tr>
                                       <td width="17%">TOMCAT</td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="14%"></td>
                                   </tr>
                               </tbody>
                           </table>
                       </div>
                   </section>
                 </div>
                 <div class="col-md-12">
                   <section class="panel">
                       <header class="panel-heading" style="background-color:#ddd;">
                           <span style="font-size: 14px;"><strong>IP:192.168.0.165</strong></span>
                           <span class="tools pull-right">
                               <a href="javascript:;" class="fa fa-chevron-down"></a>
                               <a href="javascript:;" class="fa fa-times"></a>
                            </span>
                       </header>
                       <div class="panel-body">
                           <table class="table1" id="table7">
                               <thead>
                                   <tr>
                                       <th width="17%">#</th>
                                       <th width="17%">CPU使用率</th>
                                       <th width="17%">线程数</th>
                                       <th width="17%">占用内存</th>
                                       <th width="17%">PID</th>
                                       <th width="15%">操作</th>
                                   </tr>
                               </thead>
                               <tbody>
                                   <tr>
                                       <td width="17%">TDM</td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="15%"></td>
                                   </tr>
                                   <tr>
                                       <td width="17%">SPM</td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="14%"></td>
                                   </tr>
                                   <tr>
                                       <td width="17%">TOMCAT</td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="17%"></td>
                                       <td width="14%"></td>
                                   </tr>
                               </tbody>
                           </table>
                       </div>
                   </section>
                 </div>
            </SECTION>
        </SECTION>
    </section>
    
    
    <script src="<%=basePath%>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath%>static/js/bootbox.min.js"></script>
    
    <script type="text/javascript">
        
        $(function(){
        	load(false,'');
        });
        
        function load(flag,comm)
        {
        	$.ajax({
                type:"POST",
                url:"<%=basePath%>backgroundcheck/getCPUForTS?flag="+flag,
                success:function(data){
                    for(var i=1;i<=7;i++)
                    {
                        for(var j=1;j<=3;j++)
                        {
                            for(var k=1;k<=5;k++)
                            {
                                $("#table"+i+" tr:eq("+j+")"+" td:eq("+k+")").html("");
                            }
                        }
                    }
                    for(var i = 0;i<data.length;i++)
                    {
                        var id="";
                        if(data[i].IP=="192.168.0.110")
                        {
                            id="table1";
                        }
                        else if(data[i].IP=="192.168.0.160")
                        {
                            id="table2";
                        }
                        else if(data[i].IP=="192.168.0.161")
                        {
                            id="table3";
                        }
                        else if(data[i].IP=="192.168.0.162")
                        {
                            id="table4";
                        }
                        else if(data[i].IP=="192.168.0.163")
                        {
                            id="table5";
                        }
                        else if(data[i].IP=="192.168.0.164")
                        {
                            id="table6";
                        }
                        else if(data[i].IP=="192.168.0.165")
                        {
                            id="table7";
                        }
                        
                        if(typeof(data[i].TDM)!="undefined")
                        {
                            $("#"+id+" tr:eq(1) td:eq(1)").text(data[i].TDM.CPU+"%");
                            $("#"+id+" tr:eq(1) td:eq(2)").text(data[i].TDM.TC);
                            $("#"+id+" tr:eq(1) td:eq(3)").text(data[i].TDM.NC+"MB");
                            $("#"+id+" tr:eq(1) td:eq(4)").text(data[i].TDM.PID);
                            if(data[i].IP=="192.168.0.165")
                            {
	                            var str = '<a class="btn btn-primary btn-xs" href="javascript:void(0)"><span onclick="restart(\''+data[i].IP+'\',\'TDM\')" class="glyphicon glyphicon-edit">重启</span></a>&nbsp;&nbsp;'
	                            +'<a class="btn btn-primary btn-xs" href="javascript:void(0)"><span onclick="shutdown(\''+data[i].IP+'\',\'TDM\')" class="glyphicon glyphicon-edit">关闭</span></a>';
	                            $("#"+id+" tr:eq(1) td:eq(5)").html(str);
                            }
                            
                        }
                        if(typeof(data[i].SPM)!="undefined")
                        {
                            $("#"+id+" tr:eq(2) td:eq(1)").text(data[i].SPM.CPU+"%");
                            $("#"+id+" tr:eq(2) td:eq(2)").text(data[i].SPM.TC);
                            $("#"+id+" tr:eq(2) td:eq(3)").text(data[i].SPM.NC+"MB");
                            $("#"+id+" tr:eq(2) td:eq(4)").text(data[i].SPM.PID);
                            if(data[i].IP=="192.168.0.165")
                            {
                                var str = '<a class="btn btn-primary btn-xs" href="javascript:void(0)"><span onclick="restart(\''+data[i].IP+'\',\'SPM\')" class="glyphicon glyphicon-edit">重启</span></a>&nbsp;&nbsp;'
                                +'<a class="btn btn-primary btn-xs" href="javascript:void(0)"><span onclick="shutdown(\''+data[i].IP+'\',\'SPM\')" class="glyphicon glyphicon-edit">关闭</span></a>';
                                $("#"+id+" tr:eq(2) td:eq(5)").html(str);
                            }
                        }
                        if(typeof(data[i].TOMCAT)!="undefined")
                        {
                            $("#"+id+" tr:eq(3) td:eq(1)").text(data[i].TOMCAT.CPU+"%");
                            $("#"+id+" tr:eq(3) td:eq(2)").text(data[i].TOMCAT.TC);
                            $("#"+id+" tr:eq(3) td:eq(3)").text(data[i].TOMCAT.NC+"MB");
                            $("#"+id+" tr:eq(3) td:eq(4)").text(data[i].TOMCAT.PID);
                            if(data[i].IP=="192.168.0.165")
                            {
	                            var str = '<a class="btn btn-primary btn-xs" href="javascript:void(0)"><span onclick="restart(\''+data[i].IP+'\',\'TOMCAT\')" class="glyphicon glyphicon-edit">重启</span></a>&nbsp;&nbsp;'
	                            +'<a class="btn btn-primary btn-xs" href="javascript:void(0)"><span onclick="shutdown(\''+data[i].IP+'\',\'TOMCAT\')" class="glyphicon glyphicon-edit">关闭</span></a>';
	                            $("#"+id+" tr:eq(3) td:eq(5)").html(str);
                            }
                        }
                    }
                    if(flag)
                    {
                        if(comm=='关闭')
                        {
                            easy2go.toast('info', '关闭成功!');
                        }
                        else if(comm=='重启')
                        {
                            easy2go.toast('info', '重启成功!');
                        }
                    }
                }
            }); 
        }
        
        window.setInterval(function(){
            load(false,'');
        },60000);
        
        //重启
        function restart(ip,pname)
        {
        	$.ajax({
                type:"POST",
                url:"<%=basePath%>backgroundcheck/restart?ip="+ip+"&pname="+pname,
                success:function(data){
                	
                	if(data.result=="SUCCESS")
                	{
                        load(true,'重启');
                	}
                	else
                	{
                        easy2go.toast('warn', '重启失败!');
                	}
                }
        	});
        }

        //关闭
        function shutdown(ip,pname)
        {
        	$.ajax({
                type:"POST",
                url:"<%=basePath%>backgroundcheck/shutdown?ip="+ip+"&pname="+pname,
                success:function(data){

                	if(data.result=="SUCCESS")
                    {
                        load(true,'关闭');
                    }
                    else
                    {
                        easy2go.toast('warn', '关闭失败!');
                    }
                }
            });
        }
    
    </script>
    
    <jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
        <jsp:param name="matchType" value="level2" />
    </jsp:include>
</body>
</html>