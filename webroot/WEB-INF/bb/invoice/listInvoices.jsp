<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.saninco.ccm.util.SystemUtil"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>CCM List Invoices for Approval</title> 
		<link rel="stylesheet" type="text/css"
			href="<s:url value="/include/css/bbclient.css" />">
	</head>

	<body>
		<form id="form0" name="form0"
			action="<s:url value="/bb/confirmApproveAllInvoices.action"></s:url>"
			method="post">
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
				<tr>
					<td colspan="2">
						<img src="<s:url value="/include/images/company_logo.png" />"
							width="198" height="40" alt="Company Logo" border="0">
					</td>
				</tr>
				<tr class="normalTable" align="right"><td>
				<a href="<s:url value="/bb/listUserBackup.action" />"><font style="color:000;">Back to select user</font></a>&nbsp;&nbsp;|</td>
				<td>
				Welcome,<strong><span id="ctl00_LoginName1"><%=SystemUtil.getCurrentUser().getFirstName() %>&nbsp;<%=SystemUtil.getCurrentUser().getLastName() %></span> </strong>
				<a href="securityLogout.action"><font style="color:000;">Logout</font></a></td></tr>
				<tr>
					<td colspan="2">
						<b>Invoices to approve</b>
					</td>
				</tr>
				<s:if test="totalPageNumber==0">
					<tr class="normalTable">
						<td colspan="2">
							No records returned.
						</td>
					</tr>
				</s:if>
				<s:else>
					<tr class="normalTable">
						<td colspan="2" align="left">
							Page&nbsp;
							<s:text name="svo.pageNo" />
							&nbsp;of&nbsp;
							<s:text name="totalPageNumber"></s:text>
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
														<a
															href="<s:url value="/bb/viewInvoice.action" />?svo.userId=<s:property value="user.id" />&svo.invoiceId=<s:property value="#iElement[0]" />&svo.pageNo=<s:text name="svo.pageNo" />">
															<s:property value="#iElement[1]" default=" " /> </a>
													</td>
												</tr>
												<tr
													class="<s:if test="#status.odd == true ">TRHeader</s:if><s:else>TableTR</s:else>">
													<td class="flen">
														Vendor
													</td>
													<td>
														<s:property value="#iElement[2]" default=" " />
													</td>
												</tr>
												<tr
													class="<s:if test="#status.odd == true ">TRHeader</s:if><s:else>TableTR</s:else>">
													<td class="flen">
														BAN
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
									<tr class="normalTable">
										<td>Waiting  for <s:property value="user.firstName" /> <s:property value="user.lastName" /> approval</td>
									</tr>
								</s:iterator>
								
							</table>
						</td>
					</tr>
					<tr>
						<td align="left">
							<s:hidden name="invoiceIds" />
							<s:hidden name="oldStatusIds" />
							<s:hidden name="svo.pageNo" />
							<s:hidden name="svo.userId" />
							<input type="submit" class="approveButton" value="Approve Listed"
								onclick="document.forms[0].submit();" />
						</td>
						<td align="right">
							<s:if test="svo.pageNo==1">
								<img
									src="<s:url value="/include/images/button_previous_first.gif"/>">
							</s:if>
							<s:else>
								<a
									href="<s:url value="/bb/listInvoice4Approval.action" />?svo.pageNo=1&svo.userId=<s:property value="user.id" />">
									<img
										src="<s:url value="/include/images/button_previous_first.gif"/>">
								</a>
							</s:else>
							<s:if test="svo.pageNo==1">
								<img src="<s:url value="/include/images/button_previous.gif"/>">
							</s:if>
							<s:else>
								<a
									href="<s:url value="/bb/listInvoice4Approval.action" />?svo.pageNo=<s:text name="svo.pageNo-1" />&svo.userId=<s:property value="user.id" />">
									<img src="<s:url value="/include/images/button_previous.gif"/>">
								</a>
							</s:else>
							<s:if test="svo.pageNo==totalPageNumber">
								<img src="<s:url value="/include/images/button_next.gif"/>">
							</s:if>
							<s:else>
								<a
									href="<s:url value="/bb/listInvoice4Approval.action" />?svo.pageNo=<s:text name="svo.pageNo+1" />&svo.userId=<s:property value="user.id" />">
									<img src="<s:url value="/include/images/button_next.gif"/>">
								</a>
							</s:else>
							<s:if test="svo.pageNo==totalPageNumber">
								<img src="<s:url value="/include/images/button_next_last.gif"/>">
							</s:if>
							<s:else>
								<a
									href="<s:url value="/bb/listInvoice4Approval.action" />?svo.pageNo=<s:text name="totalPageNumber" />&svo.userId=<s:property value="user.id" />">
									<img
										src="<s:url value="/include/images/button_next_last.gif"/>">
								</a>
							</s:else>
						</td>
					</tr>
				</s:else>
			</table>
		</form>
	</body>
</html>
