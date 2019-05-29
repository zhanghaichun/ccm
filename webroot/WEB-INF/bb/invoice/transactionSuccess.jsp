<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.saninco.ccm.util.SystemUtil"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>CCM Transaction Success</title> 
		<link rel="stylesheet" type="text/css"
			href="<s:url value="/include/css/bbclient.css" />">
	</head>

	<body>
		<table cellspacing="0" cellpadding="0" border="0" width="100%">
			<tr>
				<td>
					<img src="<s:url value="/include/images/company_logo.png" />"
						width="198" height="40" alt="Company Logo" border="0">
				</td>
			</tr>
			<tr class="normalTable" align="right"><td>
				Welcome,<strong><span id="ctl00_LoginName1"><%=SystemUtil.getCurrentUser().getFirstName() %>&nbsp;<%=SystemUtil.getCurrentUser().getLastName() %></span> </strong>
				<a href="securityLogout.action"><font style="color:000;">Logout</font></a></td></tr>
			<tr class="normalTable">
				<td align="left">
					<b><s:text name="successMessage" /> <br> 
						<s:text name="failureMessage" /> 
						<br> Go back to <a
						href="<s:url value="/bb/listInvoice4Approval.action" />?svo.pageNo=<s:property value="svo.pageNo"/>&svo.userId=<s:property value="svo.userId"/>">list
							invoices page</a>.
						<br> Go back to <a
						href="<s:url value="/bb/listUserBackup.action"/>">list
							users page</a>.</b>
				</td>
			</tr>
		</table>
	</body>
</html>
