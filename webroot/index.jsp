<%@page import="com.saninco.ccm.util.SystemUtil"%>
<%@page import="java.util.List"%>
<%
List<String> authorities = SystemUtil.getCurrentUserAuthorities();
if(authorities.contains("FUNCTION_1000")){
	response.sendRedirect("wikiShow.action");
//	response.sendRedirect("searchInvoice.action");
}else if(authorities.contains("FUNCTION_2000")){
	response.sendRedirect("searchDispute.action");
//}else if(authorities.contains("FUNCTION_3000")){
//	response.sendRedirect("reportUser.action");
}else if(authorities.contains("FUNCTION_3100") || authorities.contains("FUNCTION_3200")){
	response.sendRedirect("reportUser.action");
//}else if(authorities.contains("FUNCTION_4000")){
}else if(authorities.contains("FUNCTION_4100") || 
		 authorities.contains("FUNCTION_4110") || 
		 authorities.contains("FUNCTION_4120") || 
		 authorities.contains("FUNCTION_4130") || 
		 authorities.contains("FUNCTION_4140") || 
		 authorities.contains("FUNCTION_4200") || 
		 authorities.contains("FUNCTION_4400") || 
		 authorities.contains("FUNCTION_4500") || 
		 authorities.contains("FUNCTION_4700") || 
		 authorities.contains("FUNCTION_4800") || 
		 authorities.contains("FUNCTION_4900")){
	response.sendRedirect("searchAdminAction.action");
}else if(authorities.contains("FUNCTION_6000")){
	// response.sendRedirect("searchDashboard.action"); // 这个是旧的Dashboard页面的导航。
	response.sendRedirect("showDashboardList.action");
}
%>