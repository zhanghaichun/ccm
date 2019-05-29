<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<link rel="stylesheet" type="text/css" href="include/javascript/Calendar/styles/calendar.css">
<script type="text/javascript" src="include/javascript/Calendar/simplecalendar.js"></script>
<script type="text/javascript" src="include/javascript/ccm/viewPaymentDetails.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/ccm/quicklink.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/yui2/tabview-min.js"></script>

<div id="pageContainer" class="tabForm">
	<table width="100%" cellspacing="0" cellpadding="0" border="0"
		style="border-top: 0px none; margin-bottom: 4px" >
		<tr>
			<td>
				<div id="ctl00_MainContent_SearchInvoiceControl1_UpdatePanel1">
					<s:form id="form0" action="" theme="simple"
						onsubmit="getPage(event);return false;">
						<table class="tabDetailView" border="0" width=100% cellpadding="5"
							cellspacing="0">
							<tr valign="top">
								<td class="tabDetailViewDL" width="34%">
									<div align="left">
										Payment Transaction Summary
									</div>
									<div class="searchItemPanel" align="left">
										<table width="100%" height="70"  border="0">
											<tr>
												<td width="25%">
													Invoice
												</td>
												<td width="25%">
													<a href="viewInvoiceDetails.action?invoiceId=${payment.invoice.id}">
													<s:text name="payment.invoice.invoiceNumber" />
												</td>
												<td width="25%">
													Payment Date
												</td>
												<td width="25%">
													<s:date format="yyyy-MM-dd  HH:mm:ss" name="payment.paymentDate" />
												</td>
											</tr>
											<tr>
												<td>
													Payment Status
												</td>
												<td>
													<s:text name="payment.paymentStatus.paymentStatusName"></s:text>
												</td>
												<td>
													Paid Date
												</td>
												<td>
													<s:date format="yyyy-MM-dd  HH:mm:ss" name="payment.paidDate" />
												</td>
											</tr>
											<tr>
												<td>
													Payment Amount
												</td>
												<td>
													<%--<s:text name="payment.paymentAmount"></s:text>--%>
													<s:text name="global.amount" ><s:param name="value" value="payment.paymentAmount"/></s:text> 
												</td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
							<tr valign="top">
								<td class="tabDetailViewDL" width="34%">
									<div class="searchItemPanel" align="left">
										<table width="100%" height="40"  border="0">
											<tr>
												<td width="40%">
													Payment Transaction Number
												</td>
												<td>
													<s:text name="payment.paymentTransactionNumber"></s:text>
												</td>
											</tr>
											<tr>
												<td>
													Payment Reference Code
												</td>
												<td>
													<s:text name="payment.paymentReferenceCode"></s:text>
												</td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
							<tr valign="top">
								<td class="tabDetailViewDL" width="34%">
									<div class="searchItemPanel" align="left">
										<table width="100%" cellspacing="0" cellpadding="0" border="0">
											<tr>
												<td>
													<div id="demo" class="yui-navset">
														<ul class="yui-nav">
															<li>
																<a href="#tab1"><em>Payment Detail</em> </a>
															</li>
															<li>
																<a href="#tab2""><em>Transaction Control</em> </a>
															</li>
															<li>
																<a href="#tab3"><em>Transaction History</em> </a>
															</li>
														</ul>
														 <div class="yui-content">
															<div id="tab1">
																<div id="_paymentDetailsTable"></div>
																<table border=0 width=100% id="_paginationTable"
																	style="display: none;">
																	<tr>
																		<td align="left" style="padding-right: 15px;"
																			width="50%">
																			<img src="include/images/button_previous_first.gif"
																				title="First page" onclick="getFirstPage();">
																			<img src="include/images/button_previous.gif"
																				title="Previous page" onclick="getPrevPage();">
																		Page&nbsp;
																			<input id="__curPageNo" type="text"
																				style="width: 30px;" value="1"
																				onkeyup="getPage(event);return false;">
																			&nbsp;of&nbsp;
																			<span id="__totalPageNo"></span>
																			<img src="include/images/button_next.gif"
																				title="Next page" onclick="getNextPage();">
																			<img src="include/images/button_next_last.gif"
																				title="Last page" onclick="getLastPage();">
																			&nbsp;&nbsp;
																			<span class="select1"><select id="_recPerPageSelect"
																				name="_recPerPageSelect"
																				onchange="recPerPage=this.value;getPaymentDetailsTotalNo();">
																				<option value="5">5</option>
																				<option value="10">10</option>
																				<option value="20">20</option>
																				<option value="30">30</option>
																				<option value="40">40</option>
																				<option value="50">50</option>
																				<option value="100">100</option>
																			</select></span>
																		</td>
																	</tr>
																</table>
															</div>
															<div id="tab2">
																<div id="_paymentTransactionControl"></div>
																<table width="30%" height="50" cellspacing="0" cellpadding="0"
																	border="0">
																	<tr>
																		<td>
																			Select an action :
																		</td>
																		<td><span class="select1">
																			<s:select name="ViewPaymentDetailVO.actionId" id="actionId"
																				list="actionList" headerKey="all"
																				headerValue="All" listKey="key" listValue="value" />
																			</span>
																		</td>
																	</tr>
																	<tr>
																		<td>
																			Notes :
																		</td>
																		<td>
																			<s:textfield id="notes" name="viewPaymentDetailVO.notes" cssClass="validate-alpnote" ></s:textfield>
																		</td>
																	</tr>

																</table>
																<table width="50%" cellspacing="0" cellpadding="0"
																	border="0">
																	<br/>
																	<tr>
																	<td style="padding-right: 50px;" align="right">
																		<div style="float: left;">
																			<input type="button" id="save"
																				value="Save"
																				onclick="saveJudge()" />
																			&nbsp;&nbsp;&nbsp;
																			<input type="button" value="Clear"
																				onclick="javascript:resetFormElementValue();" />
																			&nbsp;&nbsp;&nbsp;
																		</div>
																		<div id="loadingImgDiv"
																			style="display: none; float: right;">
																			<img src="include/images/loading.gif" />
																		</div>
																	</td>
																	</tr>
																</table>

															</div>
															<div id="tab3">
													        <table border=0 width=100% id="_paginationTableThree"
																	style="display: none;">
																	<tr>
																		<td align="left" style="padding-right: 15px;"
																			width="50%">
																			<img src="include/images/button_previous_first.gif"
																				title="First page" onclick="getFirstPageThree();">
																			<img src="include/images/button_previous.gif"
																				title="Previous page" onclick="getPrevPageThree();">
																			</td>
																			<td>Page&nbsp;
																			<input id="__curPageNoThree" type="text"
																				style="width: 30px;" value="1"
																				onkeyup="getPageThree(event);return false;">
																			&nbsp;of&nbsp;
																			<span id="__totalPageNoThree"></span>
																			</td>
																			<td><img src="include/images/button_next.gif"
																				title="Next page" onclick="getNextPageThree();">
																			<img src="include/images/button_next_last.gif"
																				title="Last page" onclick="getLastPageThree();">
																			</td>
																			<td>&nbsp;&nbsp;
																			<span class="select1"><select id="_recPerPageSelectThree"
																				name="_recPerPageSelectThree"
																				onchange="recPerPage=this.value;getPaymentHistoryTotalNo();">
																				<option value="5">5</option>
																				<option value="10">10</option>
																				<option value="20">20</option>
																				<option value="30">30</option>
																				<option value="40">40</option>
																				<option value="50">50</option>
																				<option value="100">100</option>
																			</select></span>
																		</td>
																	</tr>
																</table>
															</div>
													    </div>
													</div>
												</td>
											</tr>

										</table>
										
									</div>
								</td>
							</tr>
						</table>
					</s:form>
				</div>
			</td>
		</tr>
	</table>
</div>

<div class="save-noid" id="saveNoId">
	<div class="hd">
		Attention
	</div>
	<div class="bd">
		<table cellspacing=8 cellpadding=0 border=0 width=80%>
			<tr>
				<td>
					"Please select an action!"
				</td>
			</tr>
		</table>
	</div>
</div>

<div class="save-id" id="saveId">
	<div class="hd">
		Attention
	</div>
	<div  class="bd" id="actionList-name">
	</div>
</div>