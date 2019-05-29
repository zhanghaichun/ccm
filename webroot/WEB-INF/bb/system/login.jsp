<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String loginError = request.getParameter("login_error");
	String errMsg = request
			.getParameter("SPRING_SECURITY_LAST_EXCEPTION");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>CCM Login Page</title> 
		<link rel="stylesheet" type="text/css"
			href="<s:url value="/include/css/bbclient.css" />">
	</head>
	<body>
		<form method="post" action="j_spring_security_check" id="form1"
			name="form1">
			<table cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td colspan="2">
						<img src="<s:url value="/include/images/company_logo.png" />"
							width="198" height="40" alt="Company Logo" border="0">
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<b>Please sign in</b>
					</td>
				</tr>
				<tr class="normalTable">
					<td>
						<s:text name="page.login.label.username" />
					</td>
					<td>
						<INPUT id="j_username" type="text" name="j_username" />
					</td>
				</tr>
				<tr class="normalTable">
					<td>
						<s:text name="page.login.label.password" />
					</td>
					<td>
						<INPUT id="j_password" type="password" name="j_password" />
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<INPUT class="approveButton" type="submit"
							value="<s:text name='button.submit.value'/>" />
					</td>
				</tr>
				<tr class="normalTable">
					<td>
						<FONT id="errorInfo" color="red"> <%
 	if (loginError != null) {
 		Exception ex = (Exception) session
 				.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
 		if (ex != null
 				&& ex.getClass().getName().endsWith("LockedException")) {
 %> <s:text name="page.login.error.account.locked" /> <%
 	} else {
 %> <s:text name="page.login.error.invalid.login" /> <%
 	}
 	}
 %> </FONT>
					</td>
				</tr>
			</table>
			</form>
			<script>
			function elementVisible(jselement)
			{ 
				do
				{
					if (jselement.style.display.toUpperCase() == 'NONE')
						return false;
					jselement=jselement.parentNode; 
				}
				while (jselement.tagName.toUpperCase() != 'BODY'); 
				return true;
			}
			if(elementVisible(document.forms[0].elements['j_username']))
				document.forms[0].elements['j_username'].focus();
		</script>
	</body>
</html>
