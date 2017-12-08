<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE html>
<html>
  <head>
    <title>EASY2GO ADMIN</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="<%=basePath %>static/css/app.min.css">
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
  </head>
  <body class="body-404">
    <div class="error-head"></div>
    <div class="container">
      <section class="error-wrapper text-center">
        <h1><img src="<%=basePath %>static/images/404.png" alt="页面不存在"></h1>
        <div class="error-desk">
          <h2>页面不存在</h2>
          <p class="nrml-txt">页面不存在或者已经被删除</p>
        </div><a  onclick="javascript:history.go(-1);"  class="back-btn"><i class="fa fa-home"></i>返回</a>
      </section>
    </div>
  </body>
</html>