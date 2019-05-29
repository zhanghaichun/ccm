<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
<title><s:text name="page.home.title"/></title>
	<link rel="stylesheet" href="css/styles.css" type="text/css" />
    <link href="css/corp.css" type="text/css" rel="stylesheet" />
</head>

<body>
<div id="wrap">

<jsp:include page="../header.jsp">
<jsp:param name="__currentPage" value="Home" />	
</jsp:include>
	<div id="content-wrap">
		<div id="content">&nbsp;
			<div id="main">
			
			<table width="90%" align="left" border="0">
				<tr>
					<td width="175">&nbsp;</td>
					<td colspan="2">
					  <s:text name="common.welcome">
						<s:param><sec:authentication property="principal.firstName"/></s:param>
						<s:param><sec:authentication property="principal.lastName"/></s:param>
					  </s:text>. <br/><br/>
					  <s:text name="page.home.text.instruction"/><br/><br/>			
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>
						<table id="homeInfoTable">
							<tr class="homeInfoTableHeading">
								<td><s:text name="page.home.text.shortcuts"/></td>
							</tr>
							<tr height="250" valign="top">
								<td style="padding-top: 10px;">
							<s:text name="page.home.text.whatToDo"/><br/>
					
				  		<sec:authorize ifNotGranted="ROLE_Business Admin, ROLE_Guest User">
							<img src="images/left.gif"/>&nbsp;<a href="orderCreation.action"><s:text name="page.home.text.createSor"/></a><br/>
				  		</sec:authorize>
				  
						<img src="images/left.gif"/>&nbsp;<a href="SorSearch.action"><s:text name="page.home.text.viewSor"/></a><br/>
				  
				  		<sec:authorize ifNotGranted="ROLE_Guest User">
							<img src="images/left.gif"/>&nbsp;<a href="ForwardToCDIList.action"><s:text name="page.home.text.viewCdi"/></a><br/>				  
							<img src="images/left.gif"/>&nbsp;<a href="ForwardToReportList.action"><s:text name="page.home.text.viewDownloadReport"/></a><br/>
				  		</sec:authorize>
						</td></tr>
						</table>
					</td>
					<td width="540" valign="top">
						<img src="<s:text name="page.home.banner" />"/>							
					</td>
				</tr>
			</table>
			
			<br clear="all" />
			<br clear="all" />
			
			</div>
		</div>
	</div>	
<jsp:include page="../footer.jsp"></jsp:include>

</div>
</body>

</html>
