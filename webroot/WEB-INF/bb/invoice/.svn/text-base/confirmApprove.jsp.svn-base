<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.saninco.ccm.util.SystemUtil"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>CCM Confirm Approval</title> 
		<link rel="stylesheet" type="text/css"
			href="<s:url value="/include/css/bbclient.css" />">
		<script type="text/javascript">
		var failureMessage = ${failureMessage}+"";
            window.onload = function() {
              if (failureMessage!="") {
               alert (failureMessage);
              }
		    
		    }
			var onSubmitApproveClicked = false;
			function onSubmitApproveClick() {
				if (!onSubmitApproveClicked) {
					onSubmitApproveClicked = true;
					document.forms[0].action='<s:url value="/bb/approveInvoice.action" />';
					document.forms[0].submit();
				}
			}
			
			function onCancelApproveClick() {
				document.forms[0].action='<s:url value="/bb/listInvoice4Approval.action"/>';
				document.forms[0].submit();
			}
		</script>		
	</head>

	<body>
		<form id="form0" name="form0"
			action="<s:url value="/bb/approveInvoice.action" />" method="post">
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
				<tr>
					<td colspan="2">
						<img src="<s:url value="/include/images/company_logo.png" />"
							width="198" height="40" alt="Company Logo" border="0">
					</td>
				</tr>
				<tr class="normalTable" align="right"><td colspan="2">
				Welcome,<strong><span id="ctl00_LoginName1"><%=SystemUtil.getCurrentUser().getFirstName() %>&nbsp;<%=SystemUtil.getCurrentUser().getLastName() %></span> </strong>
				<a href="securityLogout.action"><font style="color:000;">Logout</font></a></td></tr>
				<tr>
					<td colspan="2">
						<b>Confirmation</b>
					</td>
				</tr>
				<tr class="normalTable">
					<td colspan="2" align="left" valign="top">
						<b>You are about to approve invoice:<br>
						<s:text name="svo.invoiceNumber" /></b>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="left">
						<s:hidden name="svo.invoiceId" />
						<s:hidden name="svo.pageNo" />
						<s:hidden name="svo.invoiceNumber" />
						<s:hidden name="svo.note" />
						<s:hidden name="svo.statusId" />
						<s:hidden name="svo.userId" />
						<input type="button" class="approveButton" name="submitApprove"
							value="Confirm"
							onclick="onSubmitApproveClick();" />
						<input type="button" class="approveButton" name="cancelApprove"
							value="Cancel"
							onclick="onCancelApproveClick();" />
					</td>
				</tr>
				<tr class="normalTable">
					<td colspan="2" align="left" valign="top">
						<s:actionerror />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
