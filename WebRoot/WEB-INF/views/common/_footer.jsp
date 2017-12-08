<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- level2: 默认匹配前两级路径, 这是按之前的接口路径规范的普遍情形, 如 /sim/siminfo/action 匹配 sim/siminfo, 这样
则不管路径是否在菜单项上, 基本能展开相关的菜单组. 如编辑动作/详情页面等; 这个系默认类型, 应付大多数情况
leve1: 目前设备和客户有个别不按上面的规范做, 那只匹配开头一个路径即好;
exactly: 像统计报表里的就适合使用精确匹配; 只完全匹配路径,不包含像level2等还模糊匹配相关/相似的路径.或者考虑除了level2或backend, 其他情形都可以使用这个, 
例子见/WEB-INF/views/deviceinfo/deviceInfo_list.jsp
	<jsp:include page="/WEB-INF/views/common/_footer.jsp" flush="true">
	<jsp:param name="matchType" value="exactly" />
	</jsp:include>
backend: 像 /sim/simrechargebill/index?initCategory=0 这种带参数的, 适合从后端分配值 然后精确匹配. 或者像
流量订单详情页 orders/flowdealorders/info?flowDealID=9cd90c81-b47e-4306-a991-348175f6220b 若要它对应展开"数据交易列表"
orders/flowdealorders/list 也可以通过 backend 去指定后者 --%>
<c:if test="${empty param.matchType or param.matchType eq 'level2'}"><script>
    var curMenu="<%=request.getAttribute("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping")%>".substring(1);
    var parentPathBase = curMenu.match(/^\w+\/\w+/);
    var menuGroup = $("li[id^=\"" + parentPathBase[0].replace(/\//g, "\\\/") + "\"]").filter(":first").parent().siblings().filter(":first").attr("id");
    curMenu = curMenu.replace(/\//g, "\\\/").replace(/\?/g, "\\?").replace(/\=/g, "\\\=");
    var menu = {};
    menu[menuGroup] = true;
    menu[curMenu] = true;
    activeMenus();
</script></c:if>
<c:if test="${param.matchType eq 'level1'}"><script>
    var curMenu="<%=request.getAttribute("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping")%>".substring(1);
    var parentPathBase = curMenu.match(/^\w+\//);
    var menuGroup = $("li[id^=\"" + parentPathBase[0].replace(/\//g, "\\\/") + "\"]").filter(":first").parent().siblings().filter(":first").attr("id");
    curMenu = curMenu.replace(/\//g, "\\\/").replace(/\?/g, "\\?").replace(/\=/g, "\\\=");
    var menu = {};
    menu[menuGroup] = true;
    menu[curMenu] = true;
    activeMenus();
</script></c:if>
<c:if test="${param.matchType eq 'exactly'}"><script>
	var curMenu="<%=request.getAttribute("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping")%>".substring(1);
	curMenu = curMenu.replace(/\//g, "\\\/").replace(/\?/g, "\\?").replace(/\=/g, "\\\=");
	var menuGroup = $("#" + curMenu).parent().siblings().filter(":first").attr("id");
	var menu = {};
	menu[menuGroup] = true;
	menu[curMenu] = true;
	activeMenus();
</script></c:if>
<c:if test="${param.matchType eq 'backend'}"><script>
    var curMenu = "${MenuPath}".substring(1).replace(/\//g, "\\\/").replace(/\?/g, "\\?").replace(/\=/g, "\\\=");
    var menuGroup = $("#" + curMenu).parent().siblings().filter(":first").attr("id");
    var menu = {};
    menu[menuGroup] = true;
    menu[curMenu] = true;
    activeMenus();
</script></c:if>
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();
    a=s.createElement(o),m=s.getElementsByTagName(o)[0];a.async=1;
    a.src=g;m.parentNode.insertBefore(a,m)})(window,document,'script','//www.google-analytics.com/analytics.js','ga');
  ga('create','UA-58096909-2','auto');
  ga('send','pageview');
</script>