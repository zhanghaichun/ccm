<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Contract Tab</title>

	<meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="Contract Tab, 整个页面中头部的contract 标签页。">

    <!-- simpleCalendar.css -->
    <link rel="stylesheet" type="text/css" href="include/javascript/Calendar/styles/calendar.css">
    <!-- 前台过滤条件中下拉列表的样式 -->
    <link rel="stylesheet" type="text/css" href="include/javascript/flexbox/css/jquery.flexbox.css">

    <link rel="stylesheet" href="include\css\contractTab\contractTab.css">
	
	<!-- SimpleCalendar 日期插件-->
    <script type="text/javascript" src="include/javascript/Calendar/simplecalendar.js"></script>
    <script type="text/javascript" src="include/javascript/yui2/tabview-min.js"></script>
    <script type="text/javascript" src="include/javascript/flexbox/js/jquery.flexbox.js"></script>
    <script type="text/javascript" src="include/javascript/jquery/jquery.ui.core.js"></script>
    <script type="text/javascript" src="include/javascript/jquery/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="include/javascript/jquery/jquery.ui.accordion.js"></script>
    <script type="text/javascript" src="include/javascript/ccm/common/common.js"></script>
    <script type="text/javascript" src="include/javascript/ccm/common/simpleValidation.js"></script>
    <script type="text/javascript" src="include/javascript/ccm/quicklink.js"></script>
    <script type="text/javascript" src="include/javascript/contractTab/contractTab.js"></script>

</head>
<body>
	<div id="pageContainer" class="tabForm">
		<div id="contractTab" class="yui-navset">
			<!-- 导航 -->
			<ul class="yui-nav contract-tab-nav">
				<li id="contract" class="selected">
					<a href="javascript: void(0)"><em>Contract</em></a>
				</li>
				<li id="pricelist">
					<a href="javascript: void(0)"><em>Price List</em></a>
				</li>

			</ul>
			
			<!-- 导航内容 -->
			<div class="yui-content">
				<div id="contractTabContent"></div>
			</div>
		</div>
	</div>
</body>
</html>