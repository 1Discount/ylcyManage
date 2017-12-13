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
<title>提工单管理-EASY2GO ADMIN</title>
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
		.light-green {
		    color: #1f8bb5;
		}
		.red {
		    color: #E63C0C;
		}
		
		.processing {
		  display: block;
		  width: 16px;
		  height: 16px;
		  background: url("<%=basePath%>static/css/images/loading.gif") center no-repeat;
		  background-size: contain;
		}
    </style>
<%@include file="/WEB-INF/views/common/_ie8support.html"%>
<%-- 这里某些页面有内容, 见带  block head 的那些 jade 文件--%>
<style type="text/css">
/*         #searchTable tr{height:40px;} */
</style>
</head>
<body>

    <section id="container">
        <jsp:include page="/WEB-INF/views/common/_top_menu.jsp" flush="true" />
        <jsp:include page="/WEB-INF/views/common/_sidebar.jsp" flush="true" />

        <SECTION id="main-content">
            <SECTION class="wrapper">
                <DIV class="row">
	                <div class="col-md-12">
                        <section class="panel">
                           <header class="panel-heading">后台自检</header>
	                       <DIV class="panel-body">
	                          <form >
		                          <DIV class="form-group">
		                            <LABEL class="inline-label">检测项:</LABEL>
	                                <label class="checkbox-items">
								        <input type="checkbox" name="test" value="数据库">数据库
								    </label>
                                    <label class="checkbox-items">
                                        <input type="checkbox" name="test" value="后台">后台
                                    </label>
                                    <label class="checkbox-items">
                                        <input type="checkbox" name="test" value="通讯接口程序">通讯接口程序
                                    </label>
                                    <label class="checkbox-items">
                                        <input type="checkbox" name="test" value="官网">官网
                                    </label>
<!--                                     <span class="processing"></span> -->
                                    <!-- <span class="timeline-icon red">
                                        <i class="fa fa-check"></i>
                                    </span>
                                    <span class="timeline-icon light-green">
                                        <i class="fa fa-check"></i>
                                    </span> -->
                                    <BUTTON class="btn btn-primary" id="btn" style="margin-left:50px;" type="button" onclick="startTesting()">开始检测</BUTTON>
		                          </DIV>
                              </form> 
			                  <div class="progress progress-striped active progress-sm" id="progress">
                                   <div class="progress-bar progress-bar-success" id="progress2" style="width:0%;">
                                       <span id="progressSpan" style="height:11px;line-height:11px;font:18px;"></span>
                                   </div>
                              </div>
                              <div id="result"></div>
	                       </DIV>
                       </section>
                       
	                </div><div class="col-md-12">
                        <section class="panel">
                            <header class="panel-heading" style="background-color:#ddd;">
                                                                                         数据库
                                <span class="tools pull-right">
                                    <a href="javascript:;" class="fa fa-chevron-down"></a>
                                    <a href="javascript:;" class="fa fa-times"></a>
                                 </span>
                            </header>
                            <div class="panel-body">
                                <table class="table1">
                                    <tr>
                                        <td width="50%">数据库是否正常连接和延时</td>
                                        <td id="sj1" align="right" data-url="connectionDelayed"></td>
                                    </tr>
                                    <tr>
                                        <td width="50%">当前连接数，超出预设范围则提示</td>
                                        <td id="sj2" align="right" data-url="connectionNum"></td>
                                    </tr>
                                    <tr>
                                        <td width="50%">数据库定时任务是否打开正常运行</td>
                                        <td id="sj3" align="right" data-url="timingTaskOpen"></td>
                                    </tr>
                                    <tr>
                                        <td width="50%">是否有锁表超时现象</td>
                                        <td id="sj4" align="right" data-url="lockTable"></td>
                                    </tr>
                                    <tr>
                                        <td width="50%">数据同步（备份）是否正常运行</td>
                                        <td id="sj5" align="right" data-url="dataSynchronization"></td>
                                    </tr>
                                    <tr>
                                        <td width="50%">设备日志表是否正常</td>
                                        <td id="sj6" align="right" data-url="tableIntegrity"></td>
                                    </tr>
                                </table>
                            </div>
                        </section>
                      </div>
                      <div class="col-md-12">
                        <section class="panel">
                            <header class="panel-heading" style="background-color:#ddd;">
                                                                                                后台
                                <span class="tools pull-right">
                                    <a href="javascript:;" class="fa fa-chevron-down"></a>
                                    <a href="javascript:;" class="fa fa-times"></a>
                                 </span>
                            </header>
                            <div class="panel-body">
                                <table class="table1">
                                    <tbody>
                                    <tr>
                                        <td width="50%">后台服务器是否正常访问，访问速度是否正常</td>
                                        <td id="ht1" align="right" data-url="testLogin"></td>
                                    </tr>
                                    <tr>
                                        <td width="50%">CPU，内存使用情况</td>
                                        <td id="ht2" align="right" data-url="testCPU"></td>
                                    </tr>
                                    <tr>
                                        <td width="50%">设备升级文件目录和升级文件是否正常</td>
                                        <td id="ht3" align="right" data-url="testUpgradeFileDirectory"></td>
                                    </tr>
                                    <tr>
                                        <td width="50%">远程服务功能是否正常，获取日志和远程升级端口是否正常</td>
                                        <td id="ht4" align="right" data-url="testPort"></td>
                                    </tr>
                                    <tr>
                                        <td width="50%">是否能够连上TDM进行远程命令下发以及延时</td>
                                        <td id="ht5" align="right" data-url="testConnectionTDM"></td>
                                    </tr>
                                    <tr>
                                        <td width="50%">是否有异常设备日志记录影响逻辑</td>
                                        <td id="ht6" align="right" data-url="testDeviceLogs"></td>
                                    </tr>
                                    <tr>
                                        <td width="50%">硬盘剩余容量</td>
                                        <td id="ht7" align="right" data-url="testHardDiskSpace"></td>
                                    </tr>
                                    
                                    </tbody>
                                </table>
                            </div>
                        </section>
                      </div>
                      <div class="col-md-12">
                        <section class="panel">
                            <header class="panel-heading" style="background-color:#ddd;">
                                                                                                通讯接口程序
                                <span class="tools pull-right">
                                    <a href="javascript:;" class="fa fa-chevron-down"></a>
                                    <a href="javascript:;" class="fa fa-times"></a>
                                 </span>
                            </header>
                            <div class="panel-body">
                                <table class="table1">
                                    <tbody>
                                    <tr>
                                        <td width="50%">服务器CPU情况，JAVA所占CPU,以及CPU过高的进程检测</td>
                                        <td id="tx1" align="right" data-url="testCPUForTX"></td>
                                    </tr>
                                    <tr>
                                        <td width="50%">socket服务是否正常</td>
                                        <td id="tx2" align="right" data-url="testPortForTX"></td>
                                    </tr>
                                    <tr>
                                        <td width="50%">socket线程数是否过多</td>
                                        <td id="tx3" align="right" data-url="testSocketNumForTX"></td>
                                    </tr>
                                    <tr>
                                        <td width="50%">是否正常有不断的设备日志接入</td>
                                        <td id="tx4" align="right" data-url="checkLastDevicelogsForTX"></td>
                                    </tr>
                                    <tr>
                                        <td width="50%">是否有黏包现象</td>
                                        <td id="tx5" align="right" data-url="testIfNB"></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </section>
                      </div>
                      <div class="col-md-12">
                        <section class="panel">
                            <header class="panel-heading" style="background-color:#ddd;">
                                                                                               官网
                                <span class="tools pull-right">
                                    <a href="javascript:;" class="fa fa-chevron-down"></a>
                                    <a href="javascript:;" class="fa fa-times"></a>
                                 </span>
                            </header>
                            <div class="panel-body">
                                <table class="table1">
                                    <tr>
                                        <td width="50%">是否正常访问以及访问速度是否正常</td>
                                        <td id="gw1" align="right" data-url="testGWnormal"></td>
                                    </tr>
                                    <tr>
                                        <td width="50%">关键文件目录和文件是否正常（APP版本文件）</td>
                                        <td id="gw2" align="right" data-url="testAppFile"></td>
                                    </tr>
                                    <tr>
                                        <td width="50%">APP接口是否正常访问</td>
                                        <td id="gw3" align="right" data-url="testAPPinterface"></td>
                                    </tr>
                                </table>
                            </div>
                        </section>
                      </div>
                </DIV>
            </SECTION>
        </SECTION>
    </section>
    
    <script src="<%=basePath%>static/js/app.min.js?20150209"></script>
    <script src="<%=basePath%>static/js/grid/grid.zh-CN.js"></script>
    <script src="<%=basePath%>static/js/grid/bsgrid.all.min.js"></script>
    <script src="<%=basePath%>static/js/bootbox.min.js"></script>

    <script type="text/javascript">
    
        $(function(){
        	$("#progress").hide();
        });
        
        var count = 0;
        var testCount = 0;
        var exceptionStr = "";
        var n = 0;
        var sleepNum = 0;
        var exceptionStr1;
        var exceptionStr2;
        var exceptionStr3;
        function startTesting(){
        	n = 0;
        	testCount = 0;
        	count = 0;
        	sleepNum = 0;
            var oDate = new Date();
        	exceptionStr1 = "检测模块:";
            exceptionStr2 = "检测时间:"+oDate.getFullYear()+"-"+(oDate.getMonth()+1)+"-"+oDate.getDate()+" "+oDate.getHours()+":"+oDate.getMinutes()+":"+oDate.getSeconds();
            exceptionStr3 = "检测方式:手动";
        	$("#progress").show();
        	$("#btn").text("检测中...");
        	$("#btn").attr('disabled',true);
        	clean("sj",6);
        	clean("ht",7);
        	clean("tx",5);
        	clean("gw",3);
        	
        	var obj=document.getElementsByName('test');
        	for(var i=0; i<obj.length; i++)
        	{ 
        		if(obj[i].checked)
        		{
        			if(obj[i].value=="数据库")
        			{
        				exceptionStr1+="数据库";
        				count+=6;
        			}
        			else if(obj[i].value=="后台")
        			{
                        if(exceptionStr.length==7)
                        {
                        	exceptionStr1+="后台";
                        }
                        else
                        {
                        	exceptionStr1+=",后台";
                        }
        				count+=7;
        			}
        			else if(obj[i].value=="通讯接口程序")
        			{
                        if(exceptionStr.length==7)
                        {
                        	exceptionStr1+="通讯接口程序";
                        }
                        else
                        {
                        	exceptionStr1+=",通讯接口程序";
                        }
        				count+=5;
                    }
        			else if(obj[i].value=="官网")
                    {
                        if(exceptionStr.length==7)
                        {
                        	exceptionStr1+="官网";
                        }
                        else
                        {
                        	exceptionStr1+=",官网";
                        }
                    	count+=3;
                    }
        		}
        	} 
        	exceptionStr="异常项:";
        	for(var i=0; i<obj.length; i++)
        	{ 
                if(obj[i].checked)
                {
                    if(obj[i].value=="数据库")
                    {
                    	start('sj',6);
                    }
                    else if(obj[i].value=="后台")
                    {
                    	start('ht',7);
                    }
                    else if(obj[i].value=="通讯接口程序")
                    {
                    	start('tx',5);
                    }
                    else if(obj[i].value=="官网")
                    {
                    	start('gw',3);
                    }
                }
            } 
        }
        var width = 0;
        function start(idx,num)
        {
        	for(var i=1;i<=num;i++)
        	{
                testing(idx+i);
                
        	}
        }
        
        function clean(idx,num)
        {
        	for(var i=1;i<=num;i++)
        	{
        		$("#"+idx+i).html('');
            }
        }
        
        function testing(id){

            sleepNum += 1;
            $("#"+id).html('<span class="processing"></span>');
        	var url = '<%=basePath%>backgroundcheck/'+$("#"+id).attr("data-url");
        	
        	$.ajax({
        		type:"POST",
                url:url,
                data:{start:sleepNum},
                async: true,
                success : function(data) {
                    if(id=="sj1")
                    {
                    	if(data.success=="连接正常")
                    	{
                    		$("#"+id).html(data.success+',延时:'+data.data+'毫秒&nbsp;&nbsp;<span class="timeline-icon light-green"><i class="fa fa-check"></i></span>');
                    	}
                    	else
                    	{
                    		$("#"+id).html(data.success+'&nbsp;&nbsp;<span class="timeline-icon red"><i class="fa fa-check"></i></span>');
                    		n+=1;
                    		exceptionStr += n+".数据库模块:数据库连接失败  ";
                    	}
                    }
                    else if(id=="sj2")
                    {
                    	if(data.data<=100)
                    	{
                    		$("#"+id).html('当前连接数:'+data.data+'&nbsp;&nbsp;<span class="timeline-icon light-green"><i class="fa fa-check"></i></span>');
                    	}
                    	else
                    	{
                    		$("#"+id).html('当前连接数:'+data.data+'&nbsp;&nbsp;<span class="timeline-icon red"><i class="fa fa-check"></i></span>');
                            n+=1;
                            exceptionStr += n+".数据库模块:当前连接数:"+data.data+",超出预设范围  ";
                    	}
                    }
                    else if(id=="sj3")
                    {
                    	if(data.success=="是")
                    	{
                    		$("#"+id).html(data.success+'&nbsp;&nbsp;<span class="timeline-icon light-green"><i class="fa fa-check"></i></span>');
                    	}
                    	else
                    	{
                    		$("#"+id).html(data.success+'&nbsp;&nbsp;<span class="timeline-icon red"><i class="fa fa-check"></i></span>');
                            n+=1;
                            exceptionStr += n+".数据库模块:数据库定时任务未打开  ";
                    	}
                    }
                    else if(id=="sj4")
                    {
                    	if(data.success=="是")
                    	{
                    		$("#"+id).html("存在锁表超时现象,线程ID:"+data.data+'&nbsp;&nbsp;<span class="timeline-icon red"><i class="fa fa-check"></i></span>');
                            n+=1;
                            exceptionStr += n+".数据库模块:存在锁表超时现象,线程ID:"+data.data+"  ";
                    	}
                    	else
                   		{
                    		$("#"+id).html('没有锁表超时现象&nbsp;&nbsp;<span class="timeline-icon light-green"><i class="fa fa-check"></i></span>');
                   		}
                    }
                     else if(id=="sj5")
                    {
                    	if(data.success=="是")
                    	{
                    		$("#"+id).html(data.success+'&nbsp;&nbsp;<span class="timeline-icon light-green"><i class="fa fa-check"></i></span>');
                    	}
                    	else
                    	{
                    		$("#"+id).html(data.success+'&nbsp;&nbsp;<span class="timeline-icon red"><i class="fa fa-check"></i></span>');
                            n+=1;
                            exceptionStr += n+".数据库模块:数据同步(备份)异常  ";
                    	}
                    } 
                    else if(id=="sj6")
                    {
                    	if(data.success=="是")
                        {
                            $("#"+id).html(data.success+'&nbsp;&nbsp;<span class="timeline-icon light-green"><i class="fa fa-check"></i></span>');
                        }
                        else
                        {
                            $("#"+id).html(data.success+'&nbsp;&nbsp;<span class="timeline-icon red"><i class="fa fa-check"></i></span>');
                            n+=1;
                            exceptionStr += n+".数据库模块:设备日志表不完整  ";
                        }
                    }
                    else if(id=="ht1")
                    {
                    	if(data.success=="是")
                        {
                            $("#"+id).html('正常,延时:'+data.data+'毫秒&nbsp;&nbsp;<span class="timeline-icon light-green"><i class="fa fa-check"></i></span>');
                        }
                        else
                        {
                            $("#"+id).html(data.success+'&nbsp;&nbsp;<span class="timeline-icon red"><i class="fa fa-check"></i></span>');
                            n+=1;
                            exceptionStr += n+".后台模块:后台服务器访问异常  ";
                        }	
                    }
                    else if(id=="ht2")
                    {
                        if(data.success=="正常")
                        {
                            $("#"+id).html(data.data+'&nbsp;&nbsp;<span class="timeline-icon light-green"><i class="fa fa-check"></i></span>');
                        }
                        else if(data.success=="异常")
                        {
                            $("#"+id).html(data.data+'&nbsp;&nbsp;<span class="timeline-icon red"><i class="fa fa-check"></i></span>');
                            n+=1;
                            exceptionStr += n+".后台模块:JAVA占用CPU内存过高 ,JAVA CPU使用率:"+data.javacpu+" ";
                        }   
                        else
                        {
                        	$("#"+id).html(data.success+'&nbsp;&nbsp;<span class="timeline-icon red"><i class="fa fa-check"></i></span>');
                            n+=1;
                            exceptionStr += n+".后台模块:CPU使用情况异常  ";
                        }
                    }
                    else if(id=="ht3")
                    {
                    	if(data.data=="文件目录不存在")
                        {
                    		$("#"+id).html(data.data+'&nbsp;&nbsp;<span class="timeline-icon red"><i class="fa fa-check"></i></span>');
                            n+=1;
                            exceptionStr += n+".后台模块:文件目录不存在  ";
                        }
                    	else 
                    	{
                    		$("#"+id).html('目录中的文件:'+data.data+'&nbsp;&nbsp;<span class="timeline-icon light-green"><i class="fa fa-check"></i></span>');
                    	}
                    }
                    else if(id=="ht4")
                    {
                    	if(data.isSuccess==true)
                    	{
                    		$("#"+id).html(data.msg+'&nbsp;&nbsp;<span class="timeline-icon light-green"><i class="fa fa-check"></i></span>');
                    	}
                    	else
                    	{
                    		$("#"+id).html(data.msg+'&nbsp;&nbsp;<span class="timeline-icon red"><i class="fa fa-check"></i></span>');
                            n+=1;
                            exceptionStr += n+".后台模块:"+data.msg+"  ";
                    	}
                    }
                    else if(id=="ht5")
                    {
                    	if(data.success=="连接成功")
                   		{
                    		$("#"+id).html(data.success+',延时:'+data.data+'毫秒&nbsp;&nbsp;<span class="timeline-icon light-green"><i class="fa fa-check"></i></span>');
                   		}
                    	else
                    	{
                    		$("#"+id).html(data.success+'&nbsp;&nbsp;<span class="timeline-icon red"><i class="fa fa-check"></i></span>');
                            n+=1;
                            exceptionStr += n+".后台模块:连接TDM失败  ";
                    	}
                    }
                    else if(id=="ht6")
                    {
                    	if(data.success=="正常")
                        {
                            $("#"+id).html(data.success+'&nbsp;&nbsp;<span class="timeline-icon light-green"><i class="fa fa-check"></i></span>');
                        }
                        else
                        {
                            $("#"+id).html("异常记录数:"+data.data+'&nbsp;&nbsp;<span class="timeline-icon red"><i class="fa fa-check"></i></span>');
                            n+=1;
                            exceptionStr += n+".后台模块:存在异常设备日志记录，异常记录数:"+data.data+"  ";
                        }
                    }
                    else if(id=="ht7")
                    {
                        if(data.isSuccess==true)
                        {
                            $("#"+id).html('硬盘剩余空间:'+data.data+'&nbsp;&nbsp;<span class="timeline-icon light-green"><i class="fa fa-check"></i></span>');
                        }
                        else
                        {
                            $("#"+id).html('硬盘剩余空间:'+data.data+'&nbsp;&nbsp;<span class="timeline-icon red"><i class="fa fa-check"></i></span>');
                            n+=1;
                            exceptionStr += n+".后台模块:硬盘剩余空间:"+data.data+"  ";
                        }
                    }
                    else if(id=="tx1")
                   	{
                    	if(data.success=="正常")
                        {
                            $("#"+id).html(data.data+'&nbsp;&nbsp;<span class="timeline-icon light-green"><i class="fa fa-check"></i></span>');
                        }
                        else if(data.success=="异常")
                        {
                            $("#"+id).html(data.data+'&nbsp;&nbsp;<span class="timeline-icon red"><i class="fa fa-check"></i></span>');
                            n+=1;
                            exceptionStr += n+".通讯模块::JAVA占用CPU内存过高 ,JAVA CPU使用率:"+data.javacpu+" ";
                        }   
                        else
                        {
                            $("#"+id).html(data.success+'&nbsp;&nbsp;<span class="timeline-icon red"><i class="fa fa-check"></i></span>');
                            n+=1;
                            exceptionStr += n+".通讯模块:CPU使用情况异常  ";
                        }
                   	}
                    else if(id=="tx2")
                    {
                    	if(data.msg=="正常")
                    	{
                    		$("#"+id).html(data.msg+'&nbsp;&nbsp;<span class="timeline-icon light-green"><i class="fa fa-check"></i></span>');
                    	}
                    	else
                    	{
                    		$("#"+id).html(data.msg+'&nbsp;&nbsp;<span class="timeline-icon red"><i class="fa fa-check"></i></span>');
                            n+=1;
                            exceptionStr += n+".通讯模块:"+data.msg+"  ";
                    	}
                    }
                    else if(id=="tx3")
                   	{
                    	if(data.msg=="")
                        {
                    		$("#"+id).html('正常,线程数:'+data.num+'&nbsp;&nbsp;<span class="timeline-icon light-green"><i class="fa fa-check"></i></span>');
                        }
                        else
                        {
                            $("#"+id).html(data.msg+'&nbsp;&nbsp;<span class="timeline-icon red"><i class="fa fa-check"></i></span>');
                            n+=1;
                            exceptionStr += n+".通讯模块:"+data.msg+",线程数 "+data.num+" ";
                        }
                   	}
                    else if(id=="tx4")
                    {
                    	if(data=="0")
                    	{
                    		$("#"+id).html('正常&nbsp;&nbsp;<span class="timeline-icon light-green"><i class="fa fa-check"></i></span>');
                    	}
                    	else if(data=="-1")
                    	{
                    		$("#"+id).html('表中无数据&nbsp;&nbsp;<span class="timeline-icon red"><i class="fa fa-check"></i></span>');
                            n+=1;
                            exceptionStr += n+".通讯模块:表中无数据  ";
                    	}
                    	else
                    	{
                    		$("#"+id).html('异常,'+data+'分钟都没有新设备日志&nbsp;&nbsp;<span class="timeline-icon red"><i class="fa fa-check"></i></span>');
                            n+=1;
                            exceptionStr += n+".通讯模块:异常,"+data+"分钟都没有新设备日志  ";
                    	}
                    }
                    else if(id=="tx5")
                   	{
                    	/* if(data.isSuccess==true)
                   		{
                    		$("#"+id).html("上次黏包时间:"+data.data.WARN+'&nbsp;&nbsp;<span class="timeline-icon red"><i class="fa fa-check"></i></span>');
                            n+=1;
                            exceptionStr += n+".通讯模块:上次黏包时间:"+data.data.WARN+"  ";
                   		}
                    	else
                    	{ */
                    		$("#"+id).html("上次黏包时间:"+data.data.WARN+'&nbsp;&nbsp;<span class="timeline-icon light-green"><i class="fa fa-check"></i></span>');
                    	/* } */
                   	}
                    else if(id=="gw1")
                   	{
                    	if(data.isSuccess==true)
                        {
                    		if(data.speed>30000)
                    		{
                    			$("#"+id).html("访问速度太慢,访问速度:"+data.speed+'毫秒&nbsp;&nbsp;<span class="timeline-icon light-green"><i class="fa fa-check"></i></span>');
                                n+=1;
                                exceptionStr += n+".官网模块:访问官网速度太慢,访问速度:"+data.speed+"毫秒  ";
                    		}
                    		else
                    		{
                                $("#"+id).html("能正常访问,访问速度:"+data.speed+'毫秒&nbsp;&nbsp;<span class="timeline-icon light-green"><i class="fa fa-check"></i></span>');
                    		}
                        }
                    	else
                   		{
                    		$("#"+id).html('不能能正常访问&nbsp;&nbsp;<span class="timeline-icon red"><i class="fa fa-check"></i></span>');
                            n+=1;
                            exceptionStr += n+".官网模块:不能正常访问官网  ";
                   		}
                   	}
                    else if(id=="gw2")
                    {
                    	if(data.code=="00")
                        {
                    		$("#"+id).html('没有找到对应的文件夹&nbsp;&nbsp;<span class="timeline-icon red"><i class="fa fa-check"></i></span>');
                            n+=1;
                            exceptionStr += n+".官网模块:没有找到对应的文件夹  ";
                        }
                    	else if(data.code=="01")
                    	{
                    		$("#"+id).html('文件夹下的文件:'+data.msg+'&nbsp;&nbsp;<span class="timeline-icon red"><i class="fa fa-check"></i></span>');
                            n+=1;
                            exceptionStr += n+".官网模块:文件夹下的文件:"+data.msg+"  ";
                    	}
                    	else if(data.code=="02")
                    	{
                    		$("#"+id).html('文件夹下的文件:'+data.msg+'&nbsp;&nbsp;<span class="timeline-icon light-green"><i class="fa fa-check"></i></span>');
                    	}
                    }
                    else if(id=="gw3")
                    {
                    	if(data.isSuccess==true)
                   		{
                    		$("#"+id).html(data.msg+'&nbsp;&nbsp;<span class="timeline-icon light-green"><i class="fa fa-check"></i></span>');
                   		}
                    	else
                    	{
                    		$("#"+id).html(data.msg+'&nbsp;&nbsp;<span class="timeline-icon red"><i class="fa fa-check"></i></span>');
                            n+=1;
                            exceptionStr += n+".官网模块:不能正常访问APP接口  ";
                    	}
                    }
                    testCount += 1;
                    width = Math.round(testCount/count * 100 * 100) / 100.0;
                    $("#progress2").attr("style","width:"+width+"%;");
                    $("#progressSpan").text(width+"%");
                    if(width==100)
                    {
                    	n=0;
                        $("#btn").text("重新检测");
                        $("#btn").attr('disabled',false);
                        $.ajax({
                            type:"POST",
                            url:"<%=basePath%>backgroundcheck/writeFileRecord",
                            data:{exceptionStr:exceptionStr1+"|"+exceptionStr2+"|"+exceptionStr3+"|"+exceptionStr} ,
                            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                            success : function(data) {}
                        });
                        if(exceptionStr=="异常项:")
                        {
                        	exceptionStr="异常项:无";
                        }
                        $("#result").html(exceptionStr1+"<br/>"+exceptionStr2+"<br/>"+exceptionStr3+"<br/>"+exceptionStr);
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