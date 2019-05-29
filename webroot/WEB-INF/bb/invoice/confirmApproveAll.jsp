<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.saninco.ccm.util.SystemUtil"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>CCM Confirm Approve All</title> 
		<link rel="stylesheet" type="text/css"
			href="<s:url value="/include/css/bbclient.css" />">
		<script type="text/javascript">
			var onSubmitApproveClicked = false;
			function onSubmitApproveClick() {
				if (!onSubmitApproveClicked) {
					onSubmitApproveClicked = true;
					document.forms[0].action='<s:url value="/bb/approveAllInvoices.action"/>';
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
		<form id="form0" name="form0" action="" method="post">
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
						<b>Summary of Invoices to approve</b>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table cellpadding="0" width="100%" cellspacing="0" border="1">
							<tr class="TRHeader">
								<td align="center">
									No of Invoices
								</td>
								<td align="center">
									Total Payment
								</td>
								<td align="center">
									Total Dispute
								</td>
							</tr>
							<s:iterator value="summaryForApproval" var="sElement">
								<tr class="TableTR">
									<td>
										<s:property value="#sElement[0]" />
									</td>
									<td>
										<s:property value="#sElement[1]" />
									</td>
									<td>
										<s:property value="#sElement[2]" />
									</td>
								</tr>
							</s:iterator>
						</table>
					</td>
				</tr>
				<tr class="normalTable" style="padding-top:3px;">
					<td align="left" valign="top" class="flen">
						Notes:
					</td>
					<td align="left" valign="top">
						<s:textarea name="svo.note"
							cssStyle="width:170px;height:50px; overflow:scroll;" />
					</td>
				</tr>
				<tr class="normalTable">
					<td colspan="2">Waiting  for <s:property value="user.firstName" /> <s:property value="user.lastName" /> approval</td>
				</tr>
				<tr style="padding-top:3px;">
					<td colspan="2" align="left">
						<s:hidden name="invoiceIds" />
						<s:hidden name="oldStatusIds" />
						<s:hidden name="svo.pageNo" />
						<s:hidden name="svo.userId" />
						<input type="button" class="approveButton" name="submitApprove"
							value="Approve"
							onclick="onSubmitApproveClick()" />
						<input type="button" class="approveButton" name="cancelApprove"
							value="Cancel"
							onclick="onCancelApproveClick()" />
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
