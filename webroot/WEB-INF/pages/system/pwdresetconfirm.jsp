<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html >
<head>
<title><s:text name="page.passwordreminder.title"/></title>
<link REL="stylesheet" href="css/styles.css" type="text/css">
</head>
<body class="loginBody">
<table align="center" border="0" width="850px">
		<tr>
			<td height=100 class="logo" valign="middle">
				<div class="login"></div>
			</td>
			<td></td>
		</tr>
		<tr>
			<td colspan="2">
				<h1><s:text name="page.passwordreminder.title"/></h1>
			</td>			
		 </tr>
		 <tr>
		 	<td colspan="2">
		 	<!-- start form table -->
		 	
		 	<table  id="passwordTable">
		 		<tr>
		 			<td id="contents_block">
						<s:text name="page.passwordreminder.text.password.sent"/><br><br>
						<a href="login.action"><s:text name="page.passwordreminder.link.proceed.to.login"/></a>
					</td>
				</tr>
			</table>
		 	<!-- end form table -->
		 	
		 	</td>
		 </tr>
</table>




<div id="spacer"></div>
<jsp:include page="../footer.jsp"></jsp:include>

</body>
</html>
