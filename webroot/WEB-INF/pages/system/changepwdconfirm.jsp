<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title><s:text name="common.changePassword"/></title>
<link REL="stylesheet" href="css/styles.css" type="text/css">
<script type="text/javascript">
	function showChangePwdMassage(){
		if(parent && parent.hideLoadingModalLayer)parent.hideLoadingModalLayer();
		if(parent && parent.showChangePwdMassage){
			parent.showChangePwdMassage(document.getElementById('hasActionErrors_div').innerHTML);
		}
	}
</script>
</head>
<body onload="showChangePwdMassage();">
<div id="wrap">
	<jsp:include page="../header.jsp"/>
  
<!-- content-wrap starts here -->
	<div id="content-wrap"><div id="content">
	

					<!-- main starts here -->
					<div id="main">	
					
					<h1><s:text name="common.changePassword"/></h1>	
					<p id="hasActionErrors_div" style="padding-left: 25px;"><font color="green"><s:text name="page.change.password.text.change.success"/></font></p>
					<p>&nbsp;</p>
					<p><a href="home.action"><s:text name="page.change.password.link.back"/></a></p>
					
					<!-- main ends here -->	
					</div>	
				
	<!-- content-wrap ends here -->		
	</div></div>

<!-- wrap ends here -->

<div id="spacer"></div>
<div id="spacer"></div>


<jsp:include page="../footer.jsp"></jsp:include>
</div>
</body>
</html>



