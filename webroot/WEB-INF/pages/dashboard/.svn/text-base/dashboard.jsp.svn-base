<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    
    <title>Dashboard</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="description" content="Dashboard">
	<link href="include/css/dashboard/css/dashboard_bootstrap.css" rel="stylesheet">
    <link href="include/css/dashboard/css/dashboard_common.css" rel="stylesheet">
    <link rel="stylesheet" href="include/css/dashboard/css/index.css">
	<script type="text/javascript" src="include/javascript/jquery/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="include/javascript/highchart/highcharts.js"></script>
	<script type="text/javascript" src="include/javascript/highchart/no-data-to-display.js"></script>
	<script type="text/javascript" src="include/javascript/dashboard/user-dashboard.js"></script>
	
	<script type="text/javascript">
	$(function() {
		// Create the chart1111
		var options = {element:'dashboard-content',data:${userDashboardJson}};
		options.data.budgetList = ${budgetListJson};
		options.data.budgetOwnerList = ${budgetOwnerListJson};
		options.data.vendorList = ${vendorListJson};
		options.data.timePeriodList = ${timePeriodListJson};
		options.data.timePeriodListQuote = ${timePeriodListQuoteJson};
		options.data.timePeriodListGlobal = ${timePeriodListGlobalJson};
		options.data.productList = ${productListJson};
		options.data.productLabelList = ${productLabelListJson};
		options.data.provinceList = ${provinceListJson};
		options.data.auditReferenceList = ${auditReferenceListJson};
		options.data.productList = ${productListJson};
		options.data.globalDashboardControlList = ${globalDashboardControlListJson};
		options.data.dashboardModuleList = ${dashboardModuleListJson};
		
		options.data.quoteProductList = ${quoteProductListJson};
		options.data.quoteProductLabelList = ${quoteProductLabelListJson};
		options.data.quoteAgingList = ${quoteAgingListJson};
		options.data.quoteIssuedStatusList = ${quoteIssuedStatusListJson};
		options.data.quoteNetTypeList = ${quoteNetTypeListJson};
		options.data.quoteVendorList = ${quoteVendorListJson};
		options.data.quoteVendorResposeStatusList = ${quoteVendorResposeStatusListJson};
		options.data.quoteVendorResposeStatusList = ${quoteVendorResposeStatusListJson};
		var dashboard = new saninco.Dashboard(options);
		
	});
	</script>
  </head>
  
  <body style="background: #f6f6f6; padding-top: 30px;">
  <div id="pageContainer">
  		<div id="dashboard-content"></div>
  </div>
  </body>
</html>
