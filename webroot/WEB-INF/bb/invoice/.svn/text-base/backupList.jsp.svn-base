<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.saninco.ccm.util.SystemUtil"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>CCM Select User</title> 
		<link rel="stylesheet" type="text/css"
			href="<s:url value="/include/css/bbclient.css" />">
	</head>
	<body>
		<table cellspacing="0" cellpadding="0" border="0" width="100%">
			<tr>
				<td colspan="2">
					<img src="<s:url value="/include/images/company_logo.png" />"
						width="198" height="40" alt="Company Logo" border="0">
				</td>
			</tr>
			<tr class="normalTable" align="right">
			<td>
				Welcome,<strong><span id="ctl00_LoginName1"><%=SystemUtil.getCurrentUser().getFirstName() %>&nbsp;<%=SystemUtil.getCurrentUser().getLastName() %></span> </strong>
				<a href="securityLogout.action"><font style="color:000;">Logout</font></a>
			</td></tr>
			<tr><td>
			<div class="TableTR">&nbsp;&nbsp;<a href="<s:url value="listInvoice4Approval.action" />?svo.userId=<%=SystemUtil.getCurrentUserId()%>">My Invoices for Approval</a></div></td></tr>
			<tr><td>
				<s:if test="backUpUsers.size() > 0">
					<div class="TableTR">&nbsp;&nbsp;Backup </div>
					<div id="backupDiv" style="border:0;">
						<s:iterator value="backUpUsers" var="item">
								<div class="TableTR">&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="<s:url value="listInvoice4Approval.action" />?svo.userId=<s:property value="#item[0]" />">
									<s:property value="#item[1]" />
								</a>
								</div>
						</s:iterator>
					</div>
				</s:if>
			</td></tr>
		</table>
	</body>
</html>
