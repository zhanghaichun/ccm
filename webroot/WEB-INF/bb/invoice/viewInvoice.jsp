<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.saninco.ccm.util.SystemUtil"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>CCM View Invoice</title> 
		<link rel="stylesheet" type="text/css"
			href="<s:url value="/include/css/bbclient.css" />">
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
						<b>Invoice to approve</b>
					</td>
				</tr>
				<tr>
					<td colspan="2">
							<table cellpadding="0" width="100%" cellspacing="0" border="1">
								<s:iterator value="invoicesForApproval" var="iElement"
									status="status">
									<tr
										class="<s:if test="#status.odd == true ">TRHeader</s:if><s:else>TableTR</s:else>">
										<td>
											<table cellpadding="0" width="100%" cellspacing="0"
												border="0">
												<tr
													class="<s:if test="#status.odd == true ">TRHeader</s:if><s:else>TableTR</s:else>">
													<td class="flen">
														Invoice#
													</td>
													<td>
															<s:property value="#iElement[0]" default=" " />
													</td>
												</tr>
												<tr
													class="<s:if test="#status.odd == true ">TRHeader</s:if><s:else>TableTR</s:else>">
													<td class="flen">
														Vendor
													</td>
													<td>
														<s:property value="#iElement[1]" default=" " />
													</td>
												</tr>
												<tr
													class="<s:if test="#status.odd == true ">TRHeader</s:if><s:else>TableTR</s:else>">
													<td class="flen">
														BAN
													</td>
													<td>
														<s:property value="#iElement[2]" default=" " />
													</td>
												</tr>
												<tr
													class="<s:if test="#status.odd == true ">TRHeader</s:if><s:else>TableTR</s:else>">
													<td class="flen">
														Date
													</td>
													<td>
														<s:property value="#iElement[3]" default=" " />
													</td>
												</tr>
												<tr
													class="<s:if test="#status.odd == true ">TRHeader</s:if><s:else>TableTR</s:else>">
													<td class="flen">
														Payment
													</td>
													<td>
														<s:property value="#iElement[4]" default=" " />
													</td>
												</tr>
												<tr
													class="<s:if test="#status.odd == true ">TRHeader</s:if><s:else>TableTR</s:else>">
													<td class="flen">
														Dispute
													</td>
													<td>
														<s:property value="#iElement[5]" default=" " />
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</s:iterator>
							</table>
					</td>
				</tr>
				<s:if test="invoicesForReference!=null">
					<tr>
						<td colspan="2">
							<b>Last <s:text name="invoicesForReference.size" /> invoices to reference</b>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<table cellpadding="0" width="100%" cellspacing="0" border="1">
								<s:iterator value="invoicesForReference" var="iElement"
									status="status">
									<tr
										class="<s:if test="#status.odd == true ">TRHeader</s:if><s:else>TableTR</s:else>">
										<td>
											<table cellpadding="0" width="100%" cellspacing="0"
												border="0">
												<tr
													class="<s:if test="#status.odd == true ">TRHeader</s:if><s:else>TableTR</s:else>">
													<td class="flen">
														Invoice#
													</td>
													<td>
															<s:property value="#iElement[0]" default=" " />
													</td>
												</tr>
												<tr
													class="<s:if test="#status.odd == true ">TRHeader</s:if><s:else>TableTR</s:else>">
													<td class="flen">
														Vendor
													</td>
													<td>
														<s:property value="#iElement[1]" default=" " />
													</td>
												</tr>
												<tr
													class="<s:if test="#status.odd == true ">TRHeader</s:if><s:else>TableTR</s:else>">
													<td class="flen">
														BAN
													</td>
													<td>
														<s:property value="#iElement[2]" default=" " />
													</td>
												</tr>
												<tr
													class="<s:if test="#status.odd == true ">TRHeader</s:if><s:else>TableTR</s:else>">
													<td class="flen">
														Date
													</td>
													<td>
														<s:property value="#iElement[3]" default=" " />
													</td>
												</tr>
												<tr
													class="<s:if test="#status.odd == true ">TRHeader</s:if><s:else>TableTR</s:else>">
													<td class="flen">
														Payment
													</td>
													<td>
														<s:property value="#iElement[4]" default=" " />
													</td>
												</tr>
												<tr
													class="<s:if test="#status.odd == true ">TRHeader</s:if><s:else>TableTR</s:else>">
													<td class="flen">
														Dispute
													</td>
													<td>
														<s:property value="#iElement[5]" default=" " />
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</s:iterator>
							</table>
						</td>
					</tr>
				</s:if>
				
				<s:if test="svo.paymentAmount!=null">
				
				<tr class="normalTable" style="height:60px;">
					<td align="left" valign="top" class="flen">
						Notes:
					</td>
					<td>
						<s:textarea name="svo.note"
							cssStyle="width:170px;height:50px; overflow:scroll;" />
					</td>
				</tr>
				<tr class="normalTable">
					<td colspan="2">Waiting  for <s:property value="user.firstName" /> <s:property value="user.lastName" /> approval</td>
				</tr>
				<tr>
					<td colspan="2" align="left">
						<s:hidden name="svo.invoiceId" />
						<s:hidden name="svo.pageNo" />
						<s:hidden name="svo.invoiceNumber" />
						<s:hidden name="svo.statusId" />
						<s:hidden name="svo.userId" />
						<input type="button" class="approveButton" name="submitApprove"
							value="Approve"
							onclick="document.forms[0].action='<s:url value="/bb/confirmApproveInvoice.action"/>';document.forms[0].submit();" />
						<input type="button" class="approveButton" name="rejectApprove"
							value="Reject"
							onclick="document.forms[0].action='<s:url value="/bb/confirmRejectInvoice.action"/>';document.forms[0].submit();" />
						<input type="button" class="approveButton" name="cancelApprove"
							value="Cancel"
							onclick="document.forms[0].action='<s:url value="/bb/listInvoice4Approval.action"/>';document.forms[0].submit();" />
					</td>
				</tr>
				</s:if>
				<tr class="normalTable">
					<td colspan="2" align="left" valign="top">
						<s:actionerror />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
