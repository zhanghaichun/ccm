<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" type="text/css" href="include/javascript/Calendar/styles/calendar.css">

<!-- 控制dispute details 的四个单元格的样式。 -->
<link rel="stylesheet" href="include/css/dispute/disputeDetails.css">
<link rel="stylesheet" href="include/css/dispute/disputeActionRequest.css">
<link rel="stylesheet" href="include/css/hint.min.css">

<script type="text/javascript"
	src="include/javascript/Calendar/simplecalendar.js"></script>
<script type="text/javascript" src="include/javascript/ccm/quicklink.js"
	language="javascript"></script>

<script type="text/javascript"
	src="include/javascript/yui2/tabview-min.js"></script>
<SCRIPT type="text/javascript"
	src="include/javascript/ccm/common/common.js"></SCRIPT>
<script type="text/javascript"
	src="include/javascript/ccm/viewDisputeDetails.js"
	language="javascript"></script>
<script type="text/javascript"
	src="include/javascript/ccm/viewDisputeReconciliation.js"
	language="javascript"></script>
<link rel="stylesheet" type="text/css" href="include/javascript/flexbox/css/disputeDetail.css">
<link rel="stylesheet" type="text/css" href="include/javascript/flexbox/css/jquery.flexbox.css"><link>
<script type="text/javascript" src="include/javascript/flexbox/js/jquery.flexbox.js"></script>
<script type="text/javascript" src="include/javascript/saninco/downLoad.js" language="javascript"></script>
<!--
<script type="text/javascript" src="include/javascript/ccm/saninco/disputeDownLoad.js" language="javascript"></script>
-->
<div id="pageContainer" class="tabForm">
	<h3>Dispute Details</h3>
	<!-- Disputed details 共有四个类似表格的显示面板， left-top, right-top, left-bottom, right-bottom. -->
	<div id="ctl00_MainContent_SearchInvoiceControl1_UpdatePanel1">
		<table width=100% border=0 cellpadding="0" cellspacing="0"
			class="tabDetailView2 fixed-table">
			<tr>
				<td width="32%" class="tabDetailViewDL">
					<table class="left-top details-table">
						<tr>
							<td>Claim Number:</td>
							<td id = "_claimNumber">${dispute.claimNumber}</td>
						</tr>
						<tr>
							<td>Dispute Number:</td>
							<td id = "_disputeNumber">${dispute.disputeNumber}</td>
						</tr>
						<tr>
							<td>Vendor Tracking Number:</td>
							<td>${dispute.ticketNumber}</td>
						</tr>
						<tr>
							<td>Vendor:</td>
							<td>${dispute.invoice.vendor.vendorName}</td>
						</tr>
						<tr>
							<td>BAN:								
							</td>	
							<td>${dispute.invoice.ban.accountNumber}</td>
						</tr>
						<tr>
							<td>Invoice Number:</td>
							<td>
								<a href="viewInvoiceDetails.action?invoiceId=${dispute.invoice.id}">${dispute.invoice.invoiceNumber}</a>
							</td>
						</tr>
						<tr>
							<td>Dispute Amount:</td>
							<td id="theDisputeAmount-td"><fmt:formatNumber
									value="${dispute.disputeAmount}" type="currency"
									pattern="#,##0.00" /></td>
						</tr>
						<tr>
							<td>Confidence Level:</td>
							<td>${dispute.confidenceLevel}</td>
						</tr>
						<tr>
							<td colspan=2 class="view-invoice-detail--button"><input type="button"
								value="View BAN Invoice Detail"
								onclick="viewVendorDetails(${dispute.invoice.ban.vendor.id},${dispute.invoice.ban.id})" /></td>
						</tr>
					</table>
				</td>
				<td width="36%" class="tabDetailViewDL">
					<table class="right-top  details-table">
						<tr>
							<td>Is Short-paid:</td>
							<td>${dispute.flagShortpaid}</td>
						</tr>
						<tr>
							<td>Is Recurring:</td>
							<td>${dispute.flagRecurring}</td>
						</tr>
						<tr>
							<td>Created Date/Time:</td>
							<td><s:date format="yyyy-MM-dd"
									name="dispute.createdTimestamp" /></td>
						</tr>
						<tr>
							<td>Created By:</td>
							<td>${dispute.createdUser.firstName}&nbsp;${dispute.createdUser.lastName}</td>
						</tr>
						<tr>
							<td>Close Date:</td>
							<td><s:date format="yyyy-MM-dd" name="dispute.closeDate" /></td>
						</tr>
						<tr>
							<td nowrap="nowrap">Last Modified Date/Time:</td>
							<td><s:date format="yyyy-MM-dd"
									name="dispute.modifiedTimestamp" /></td>
						</tr>
						<tr>
							<td width="160px">Outstanding Reserve Amount:</td>
							<td id="disputeOutstandingReserveAmount1">${dispute.outstandingReserveAmount}</td>
						</tr>
						<tr>
							<td class="ban-manipulation-buttons"><input style="margin-bottom:5px;"
								onclick='doIncreaseOrDecrease("Increase","+");' type='button'
								value='Increase' />&nbsp;&nbsp; <input
								onclick='doIncreaseOrDecrease("Decrease","-");' type='button'
								value='Decrease' />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td width="32%" class="tabDetailViewDL">
					<table class="left-bottom  details-table">
						<tr>
							<td>Reserve Amount:</td>
							<td><fmt:formatNumber value="${dispute.reserveAmount}"
									type="currency" pattern="#,##0.00" /></td>
						</tr>
						<tr>
							<td>Status:</td>
							<td>${dispute.disputeStatus.disputeStatusName}</td>
						</tr>
						<tr>
							<td>Status Time:</td>
							<td><s:date format="yyyy-MM-dd"
									name="dispute.statusTimestamp" /></td>
						</tr>
						<tr>
							<td>Dispute Category:</td>
							<td>${dispute.disputeReason.disputeReasonName}</td>
						</tr>
						<tr>
							<td>Originator:</td>
							<td>${dispute.originator.originatorName}</td>
						</tr>
						<tr>
							<td>Attachment:</td>
							<td><c:if test="${disputeAttachmentDownloadFlag=='Y'}">
									<img src='include/images/download1.gif' style="margin-top:6px;"
										onclick='SANINCO.disputeDownLoad(${dispute.id})'>
								</c:if></td>
						</tr>
						<tr class="dispute-notes-row">
							<td class="notes-label">Notes:</td>
							<td class="notes-content"><div class="content-wrapper">${dispute.notes}</div></td>
						</tr>
					</table>
				</td>
				<td width="32%" height=100% class="tabDetailViewDL email-center">
					<h3 class="dispute-message-title">Dispute Message Center</h3>
					<!-- 当Message center 中有数据的时候。 -->
					<c:if test="${not empty disputeList}">
						<c:forEach var="item" items="${disputeList}">
							
							<c:if test="${not empty item.disputeMsgList}">
								<div class="exist-message-container">
									<div class="exist-message-wrapper-overflow">
										
										<c:forEach var="disputeMsg" items="${item.disputeMsgList}">
											<div class="inner-wrapper">
											<div class="subject-container">
											    <h6 class="subject-address-input">Subject</h6>
												<div class="subject">  ${disputeMsg.subject}</div>
											</div>	
												<div class="address-container">
													<h3 class="from-address address-person hint--bottom-right" data-hint="">${disputeMsg.from_address}</h3>
													<i class="send-to"></i>
													<c:choose>
														<c:when test="${not empty disputeMsg.to_address}">
															<h3 class="to-address address-person hint--bottom-right" data-hint="">${disputeMsg.to_address}</h3>
														</c:when>
														<c:otherwise>
															<h3 class="to-address address-person hint--bottom-right" data-hint="">${disputeMsg.cc_address}</h3>
														</c:otherwise>
													</c:choose>
												</div>
												<div class="ccAddress-container">
													<h6 class="to-address address-person hint--bottom-right">Cc : </h6>
													<input class="cc-address-input" value="${disputeMsg.cc_address}" readonly="readonly"></input>
<%--													<h6 class="to-address address-person hint--bottom-right" data-hint="">${disputeMsg.cc_address}</h6>--%>
												</div>
												<div class="datatime">
													<h4>${disputeMsg.sent_datetime}</h4>
													<c:if test="${ not empty disputeMsg.attachment_point_id}">
														<img src="include/images/download1.gif" class="download-img" onclick="SANINCO.Download(${disputeMsg.attachment_point_id})">
													</c:if>
													
												</div>
												<hr class="seperator" />
												<div class="content">
													<h4>${disputeMsg.content}</h4>
												</div>
											</div>
										</c:forEach>
										
									</div>
								</div>
							</c:if>
							<!-- 当Message center 中没有数据的时候。 -->
							<c:if test="${empty item.disputeMsgList}">
								<div class="empty-message-container">... No Message ...</div>
							</c:if>
						
						</c:forEach>
					</c:if>

					
					<!-- 联系供应商按钮。 -->
					<c:if test="${dispute.disputeStatus.id == 30 || dispute.disputeStatus.id == 34 || dispute.disputeStatus.id == 35 || dispute.disputeStatus.id == 36 }">
								<div class="contact-vandor-button">
					              	<input type='button' value="Contact Vendor" onclick="getContactVendor();"/>
					           </div>
					</c:if>
					
				</td>
			</tr>
		</table>
	</div>
	<div>
		<br />
		<br />
		<div id="demo" class="yui-navset">
			<ul class="yui-nav">
				<sec:authorize ifAllGranted="FUNCTION_2110">
					<li class="selected"><a href="#tab2"
						onclick="getTransactionHistoryList();"><em>Transaction
								Update</em> </a></li>
				</sec:authorize>
				<sec:authorize ifAllGranted="FUNCTION_2120">
					<li><a href="#tab3"
						onclick="getDisputeItemViewList();getDisputeReconciliationList();"><em>Reconciliation</em></a>
					</li>
				</sec:authorize>
				<sec:authorize ifAllGranted="FUNCTION_2120">
					<li>
						<a href="#tab4" onclick="getDisputeNotesList();"><em>Dispute Notes</em></a>
					</li>
				</sec:authorize>
				<sec:authorize ifAllGranted="FUNCTION_2120">
					<li>
						<a href="#tab5" onclick="getDisputeActionRequest();"><em>Dispute Action Request</em></a>
					</li>
				</sec:authorize>
			</ul>
			<div class="yui-content">
				<sec:authorize ifAllGranted="FUNCTION_2110">
					<div id="tab2">
						<table style="width:100%;table-layout: fixed;">
							<tr>
								<td>
									<div align="left"
										style="width:100%;overflow-x:auto;border: 0px solid;padding-bottom: 20px;">
										<div id="_transaction_history_list"></div>
									</div>
								</td>
							</tr>
						</table>
						<input type="button" value="Download to Excel"
							onclick="saveExcelByTransaction();" />
						<sec:authorize ifAllGranted="FUNCTION_2111,FUNCTION_2110">
							<input type="button" value="Update Dispute"
								onclick="emptyInput();" />
						</sec:authorize>
						<div id="__user_access_control" style="color: red;"></div>
					</div>
				</sec:authorize>
				<sec:authorize ifAllGranted="FUNCTION_2120">
					<div id="tab3">
						<br />
						<h3>Outstanding Dispute Items</h3>
						<table style="width:100%;table-layout: fixed;">
							<tr>
								<td>
									<div align="left"
										style="width:100%;overflow-x:auto;border: 0px solid;padding-bottom: 20px;">
										<div class="demo" id="__dispute_item_list"></div>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div class="demo" id="__dispute_item_page_list"></div>
								</td>
							</tr>
						</table>
						<table border=0>
							<tr>
								<sec:authorize ifAllGranted="FUNCTION_2121,FUNCTION_2120">
									<td width=166px id="__close_dispute_win_td"><input
										id="__close_dispute_win" type="button"
										value="Close as Dispute Win"
										onclick="editDisputeItems('win',true);" /></td>
									<td width=177px><input id="__close_dispute_lose"
										type="button" value="Close as Dispute Lost"
										onclick="editDisputeItems('lose',false);" /></td>
									<td width=120px><input id="__split_and_close"
										type="button" value="Split and Close"
										onclick="splitCloseDisputeItems();" /></td>
									<td width=120px><input id="__scoa_coding" type="button"
										value="SCOA Coding" onclick="updateSCOACoing();" /></td>
								</sec:authorize>
								<td width=140px><input id="__dispute_download_excel"
									type="button" value="Download to Excel"
									onclick="downloadExcel();" /></td>
								<td style="color: red;">
									<div id="__show_verify_rrompt"></div>
								</td>
							</tr>
						</table>
						<br>
						<br>
						<h3>Reconciliation</h3>
						<table style="width:100%;table-layout: fixed;">
							<tr>
								<td>
									<div align="left"
										style="width:100%;overflow-x:auto;border: 0px solid;padding-bottom: 20px;">
										<div class="demo" id="__dispute_reconciliation_list"></div>
									</div>
								</td>
							</tr>
						</table>
						<table style="width:100%;">
							<tr>
								<td>
									<div class="demo" id="__dispute_reconciliation_page_list"></div>
								</td>
							</tr>
							<tr>
								<td><input id="__refile_dispute" type="button"
									value="Refile Dispute"
									onclick="findAccessoriesList();$('#updateRefileDispute_c').css('display','block');YAHOO.ccm.container.updateRefileDispute.show();"
									style="display: block;" /></td>
								<td><input id="__cancel_refile_dispute" type="button"
									value="Cancel Refile Dispute"
									onclick="YAHOO.ccm.container.updateCancelRefileDispute.show();"
									style="display: block;" /></td>
								<td style="color: red;">
									<div id="__show_verify_reconciliation_page_rrompt"></div>
								</td>
							</tr>
						</table>
					</div>
				</sec:authorize>
				<sec:authorize ifAllGranted="FUNCTION_2110">
									<div id="tab4">
										<table style="width:100%;table-layout: fixed;">
											<tr>
												<td>
												<div align="left"  style="width:100%;overflow-x:auto;border: 0px solid;padding-bottom: 20px;">
													<div id="_dispute_notes"></div>
												</div>
												</td>
											</tr>
										</table>
										<table style="width:100%;">
											<sec:authorize ifAllGranted="FUNCTION_2110">
											<tr>
												<td>
													<div class="demo" id="__dispute_notes_page_list"></div>	
													<div id="_dispute_add_notes_btn" style="display:none; margin-top: -25px; margin-left: 240px; padding-left:15px">
														<input type="button" height="18px" value="Add Notes" onclick="addDisputeNote();"/>	
													</div>
												</td>
											<tr>
											</sec:authorize>
										</table>
									</div>
				</sec:authorize>
				<sec:authorize ifAllGranted="FUNCTION_2110">
									<div id="tab5">
										<table style="width:100%;table-layout: fixed;">
											<tr>
												<td>
												<div align="left"  style="width:100%;overflow-x:auto;border: 0px solid;padding-bottom: 20px;">
													<div id="_dispute_action_request"></div>
												</div>
												</td>
											</tr>
										</table>
										<table style="width:100%;">
											<sec:authorize ifAllGranted="FUNCTION_2110">
											<tr>
												<td>
													<div class="demo" id="__dispute_action_request_page_list"></div>
													
													<c:if test="${dispute.disputeStatus.id == 30 || dispute.disputeStatus.id == 34 || dispute.disputeStatus.id == 35 || dispute.disputeStatus.id == 36 }">
								                        <div class="add-action-request-button">
														     <input type="button" value="Add Action Request" onclick="addDisputeActionRequest();" />
												    	</div>	
				                                	</c:if>
													
												</td>
											<tr>												
											</sec:authorize>
										</table>
									</div>
				</sec:authorize>
			</div>
		</div>
	</div>
</div>

<!-- 添加 Dispute Action Request 到数据库中。这是 Dispute Action Request 弹出框。 -->
<div id="dispute-action-request">
	<!-- 显示Dispute Action Request 的 modal -->
	<div id="dispute-action-request-modal" />
		<div class="modal-header">Add Action Request</div>
		<a class="dispute-action-request-modal-close"></a>

		<div id="dispute-action-request-modal-content">

			<div class="dispute-action-request-notes-wrapper">
				<label class="notes-label">Notes:</label>
				<textarea name="dispute-action-request-notes" class="action-request-notes-textarea" ="" maxlength="500"></textarea>
				<i class="word-required">*</i>
			</div>

			<div class="word-limit clearfix">
				<strong class="word-number">500</strong>
				<strong>characters left.</strong>
			</div>

			<div class="button-container clearfix">
<%--				<input type="button" class="save-dispute-action-request" value="Save" onclick="saveDisputeActionRequest()">--%>
				<input type="button" class="save-dispute-action-request" value="Pending" onclick="saveDisputeActionRequest('2')">
				<input type="button" class="save-dispute-action-request" value="Complete" onclick="saveDisputeActionRequest('3')">
				<input type="button" class="clear-dispute-action-request-notes" value="Clear" onclick="clearDisputeActionRequestNotes()">
			</div>
		</div>
	</div>
</div>

<!-- 添加 Dispute Notes 到数据库中。这是 Dispute Notes 弹出框。 -->
<div id="DisputeNoteWindow">
	<div class="hd" id = "addTitle">Add Notes</div>
	<div class="bd" id="reg">
		<form id="handleDisputeNoteActionForm" action="doDisputeNote.action" enctype="multipart/form-data" method="POST" target="uploadFrame_0">
			<table width="95%" align="center" style="margin: auto;" cellspacing="5">
				<tr>
					<td colspan="2">
						<input name="invoiceId" type="hidden"  id="DisputeNoteByInvoiceId"/>
						<input name="disputeId" type="hidden"  id="DisputeNoteByDisputeIdId"/>
					</td>
				</tr>		
				<tr>
					<td style="vertical-align: text-top;" width='20%'>Notes:</td>
					<td width='80%'>
						<div>
							<div style="float:left;width:95%;">
								<textarea id="ps" name = "noteNotes"
									style="overflow:auto ; width:100%; height:60px;max-width:380px;resize: none;"
									class="validate-text" maxlength="500">
								</textarea>
							</div>
							<div  style="font-size: 15px; margin-left: 5px; color: red; float:left; ">*</div>
							<div class="clear"></div>
						</div>
					</td>
				</tr>
				<tr>
				<td></td>
				<td>
				<div style="text-align:right; padding:0 15px 0 0 ;margin-right:15px">
				<strong id="num">500</strong>
				<strong> characters left</strong>
				</div>
				</td>
				</tr>			    
				<tr>
				<td></td>
					<td>
						
						<div style="float:right;padding-right: 15px;">
							<input style="text-align:center; padding:1px 4px;border-color: #808080;" type="button" value="Save" id="sub" onclick="saveNotes()"/>
							<input style="text-align:center; padding:1px 4px;border-color: #808080;" type="button" value="Clear" id="clear" onclick="clearNotes()"/>
							<input style="text-align:center; padding:1px 4px;border-color: #808080;" type="button" value="Cancel" id="cancel" onclick="YAHOO.ccm.container.DisputeNoteWindow.hide();"/>
						</div>
						
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>

<div class="yui-pe-content" id="deleteReconciliation">
	<div class="hd">
		<h2>Please confirm</h2>
	</div>
	<div class="bd">
		<form id="formr">
			<table width="95%" align="center" border=0>
				<tr>
					<td colspan="2" style="color: red;">Please confirm!</td>
				</tr>
			</table>
		</form>
	</div>
</div>

<div class="yui-pe-content" id="editDisputeItem">
	<div class="hd">
		<h2 id="__div_name_windows"></h2>
	</div>
	<div class="bd">
		<form id="doDisputeItemWinAndLoseForm" action="closeDisputeWin.action"
			style="position:relative;" enctype="multipart/form-data"
			method="POST" target="uploadFrame_0">
			<table height=100% width="100%" align="center" border=0>
				<tr height="15px">
					<td colspan="2">You are going to force <span
						id="__show_total_amount_tips"></span>, please confirm.
					</td>
				</tr>
				<tr>
					<td>SCOA</td>
					<td><span id="VL_vendorList_at_dispute1"></span></td>
				</tr>
				<tr>
					<td width=100px style="float:left;margin-top:3px;">Attach File: <input type="hidden"
						id="__box_id" name="searchDisputeVO.boxId" value="" />
					</td>
					<!--
					<td>
		
							<input style="height:19px;width:220px;" type='text' id="__up_load_text_dispute1" disabled="disabled">
		        			<span class="ccm_upload_img">
				        		<input id="__load_up_two" onchange="document.getElementById('__up_load_text_dispute1').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
				        		<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
		        			</span>
						
						
					</td>
				-->
					<td>
						<div style="VERTICAL-ALIGN: top; OVERFLOW-Y: auto; max-height: 110px"
							id="Close_dispute"></div>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>	        	
				</tr>

				<tr>
					<td>&nbsp;</td>
					<td style="text-align: right;padding-right:10px">
						  <input style="height: 20px;" type="button" value="Add" onclick="handleAdd()"/>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>	        	
				</tr>
				<tr>
				<tr>
					<td valign="top">Notes:</td>
					<td><s:textarea id="__notes" name="searchDisputeVO.notes"
							cssStyle="padding:0px 1px;overflow:auto ;width:327px;height:60px;"
							cssClass="validate-text"></s:textarea></td>
				</tr>
<%--				<tr>--%>
<%--					<td colspan="2">Your notes have to be more than 10 characters.</td>--%>
<%--				</tr>--%>
			</table>
		</form>
	</div>
</div>

<div class="yui-pe-content" id="splitCloseDisputeItem">
	<div class="hd">
		<h2>Split and close</h2>
	</div>
	<div class="bd">
		<form id="doSplitCloseDisputeItemForm"
			action="splitCloseDispute.action" style="position:relative;"
			enctype="multipart/form-data" method="POST" target="uploadFrame_0">
			<table height="100%" width="100%" align="center" border="0">
				<tr height="15px">
					<td>Amount:</td>
					<td><s:textfield id="__balance_amount"
							name="searchDisputeVO.balanceAmount" cssClass="validate-number"
							onblur="verifyAmount(this.value);" style="width:312px"></s:textfield></td>
				</tr>
				<tr>
					<td>SCOA</td>
					<td>
						<div id="VL_vendorList_eaccount_code"></div>
					</td>
				</tr>
				<tr>
					<td style="float:left;margin-top:3px;">Attach File: <input type="hidden" id="__box_id_2"
						name="searchDisputeVO.boxId" value="" /> <input type="hidden"
						id="__button_name" name="searchDisputeVO.bo" value="" />
					</td>
					<td>
						<!--  <input id="__load_up_three" type="file" name="uploads" style="width:245px;height:19px;"/>-->
						<!--
						
							<input style="height:19px;width:50%;" type='text' id="__up_load_text_dispute2" disabled="disabled">
		        			<span class="ccm_upload_img">
				        		<input id="__load_up_three" onchange="document.getElementById('__up_load_text_dispute2').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
				        		<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
		        			</span>
		        	-->
						<div
							style="VERTICAL-ALIGN: top; OVERFLOW-Y: auto; max-height: 110px;width:100%;"
							id="splitAndClose"></div>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>	        	
				</tr>

				<tr>
					<td>&nbsp;</td>
					<td style="text-align: right;padding-right:10px;">
						  <input style="height: 20px;" type="button" value="Add" onclick="handleAdd2()"/>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>	        	
				</tr>				
				<tr>
					<td valign="top">Notes:</td>
					<td><s:textarea id="__notes_two" name="searchDisputeVO.notes"
							cssStyle="padding:0px 1px;overflow:auto ;width:100%;height:60px;"
							cssClass="validate-text"></s:textarea></td>
				</tr>
<%--				<tr>--%>
<%--					<td colspan="2">Your notes have to be more than 10 characters.</td>--%>
<%--				</tr>--%>
			</table>
		</form>
	</div>
</div>

<div class="yui-pe-content" id="addTransactionHistory">
	<div class="hd">
		<h2>Add Transaction History</h2>
	</div>
	<div class="bd">
		<form id="doAddTransactionHistoryForm"
			action="addTransactionHistoryData.action"
			enctype="multipart/form-data" method="POST" target="uploadFrame_0">
			<table height="100%" width="100%" align="center" border="0">
				<tr height="15px">
					<td><input type="hidden" name="searchDisputeVO.disputeId"
						value="${param.disputeId}" /> <input type="hidden"
						name="searchDisputeVO.invoiceId" value="${dispute.invoice.id}" />
						Vendor Tracking Number:</td>
					<td><s:textfield id="__ticket_number"
							name="searchDisputeVO.ticketNumber" cssClass="validate-number" style="width:260px;"></s:textfield>
					</td>
				</tr>
				<tr height="15px">
					<td  style="float:left;margin-top:3px;">Attach File:</td>
					<td>
						<div id="addTransactionHistory_AddFile"
							style="overflow-y:auto;max-height:110px;position:relative;"></div>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>	        	
				</tr>

				<tr>
					<td>&nbsp;</td>
					<td style="text-align: right;">
						  <input style="height: 20px;" type="button" value="Add" onclick="transactionHistoryAddFile()"/>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>	        	
				</tr>
				<tr>
					<td valign="top">Notes:</td>
					<td><s:textarea id="__notes_three"
							name="searchDisputeVO.notes"
							cssStyle="padding:0px 1px;overflow:auto ;width:260px;height:60px;"
							cssClass="validate-text"></s:textarea></td>
				</tr>
<%--				<tr>--%>
<%--					<td colspan="2">Your notes have to be more than 10 characters.</td>--%>
<%--				</tr>--%>
			</table>
		</form>
	</div>
</div>

<div class="yui-pe-content" id="updateCancelRefileDispute">
	<div class="hd">
		<h2>Cancel Refile Dispute</h2>
	</div>
	<div class="bd">
		<table width="97%" border=0 align="center" style="display: block;">
			<tr>
				<td colspan="2">Are you sure your want to cancel this refile
					dispute?</td>
			</tr>
		</table>
	</div>
</div>

<!-- Reply list of dispute action request. -->
<div class="yui-pe-content" id="disputeAction" style="display: none;">

	<div class="reply-list-container">
	    <a class="delete-list-container button-finger" onclick="dismissDisputeReplyList()"></a>
	    <div class="reply-list">
	        
	    </div>
	</div>	

</div>

<!-- Add reply of dispute action request. -->
<div class="yui-pe-content" id="disputeAddReply" style="display: none;">

	<div class="reply-container">
	  <div class="edit-area">
	    <s:textarea id="__addReply_notes"
			name="searchDisputeVO.notes"
			cssClass="validate-text"></s:textarea>
	    <div class="break-buttons">
	      <input id="__add_Reply" class="reply button-finger" type="button" value="Reply" onclick="addReplyNote();">
	      <input id="__Cancel_Reply" class="cancel button-finger"  type="button" value="Cancel" onclick="cancelAddReply();">
	      <input id="__dispute_Action_Request_Id" class="id-value" type="hidden" value="">
	    </div>
	  </div>
	</div>	

</div>

<div class="yui-pe-content" id="updateRefileDispute">
	<div class="hd">
		<h2>Refile Dispute</h2>
	</div>
	<div class="bd">
		<table id="__edit_proposal" width="97%" border=0 align="center"
			style="display: block;">
			<tr>
				<td colspan="2">Are you sure your want to refile this dispute?</br>
					TEMS will send out a refile E-mail to vendor.</br>
				</td>
			</tr>
			<tr>
				<td valign="top">Notes:</td>
				<td><s:textarea id="__refile_dispute_notes"
						name="dispute.notes"
						cssStyle="padding:0px 1px;overflow:auto ;width:360px;height:60px;"
						cssClass="validate-text"></s:textarea></td>
			</tr>
<%--			<tr>--%>
<%--				<td colspan="2">Your notes have to be more than 10 characters.</td>--%>
<%--				</br>--%>
<%--			</tr>--%>
			<tr>
				<td>Attach File:</td>
				<td>
					<form onsubmit="return uploadValidate();"
						style="position:relative;"
						action="doRefileDisputeAttachFile.action"
						enctype="multipart/form-data" method="POST" target="uploadFrame_0">

						<div style="padding:3px 0 0 0;height:19px;">
							<div style="float:left;">
								<input style="height:19px;width:200" type='text'
									id="__up_load_text_invoiceDetail_5" disabled="disabled">
							</div>
							<div style="float:left; margin:0 0 0 2px">
								<span class="ccm_upload_img"> <input
									id="__upload_invoice"
									onchange="document.getElementById('__up_load_text_invoiceDetail_5').value=this.value;"
									class="ccm_file" type="file" name="uploads" size="1" /> <input
									style="height:19px;position:absolute;right:0px;top:0px;"
									type="button" value="Browse..." />
								</span>
							</div>
						</div>
						<input type="button" value="Upload"
							onclick="this.parentNode.submit();"
							style="height:19px;position:absolute;left:270px;top:4px;" /> <input
							id="__dispute_id" name="searchDisputeVO.disputeId" type="hidden" />
						<input id="__attachment_point_id"
							name="searchDisputeVO.attachmentPointId" type="hidden" />
					</form>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div class="demo" id="__show_accessories_list"></div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div class="demo" id="__show_accessories_page_list"></div>
				</td>
			</tr>
		</table>
	</div>
</div>

<form id="__from_01" action="download.action" method="post"
	style="display: none;">
	<input type="hidden" name="filePath" value="" /> <input type="hidden"
		name="fileName" value="" />
</form>

<div id="addDisputeSCOADialog">
	<div class="hd">SCOA Coding</div>
	<div class="bd">
		<form>
			<table width="95%" align="center" border=0>
				<tr>
					<td width="30px"><b>SCOA</b></td>
					<td>
						<div id="VL_vendorList_credit_tab"></div>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>




<div class="yui-pe-content" id="updateIncreaseAndDecreaseWindow"
	style="visibility: hidden;">
	<div class="hd">
		<h2 id="increase-decrease-title"></h2>
	</div>
	<div class="bd">
		<form id="updateIncreaseAndDecreaseForm"
			action="updateIncreaseAndDecrease.action" style="position:relative;"
			enctype="multipart/form-data" method="POST" target="uploadFrame_0">
			<input type="hidden" name='dispute.id' value='${dispute.id }' /> <input
				type="hidden" id="updateIncreaseAndDecrease-reserveType"
				name='reserveType' value='' />
			<table height="100%" width="95%" align="center" border="0">
				<tr height="15px">
					<td width="165px">Outstanding Reserve Amount:</td>
					<td id="disputeOutstandingReserveAmount2">
						${dispute.outstandingReserveAmount}</td>
				</tr>
				<tr>
					<td>SCOA:</td>
					<td>
						<table id="updateIncreaseAndDecrease-scoa-table">

						</table>
						<table>
							<tr>
								<td><input type='radio' name="accountCodeId" value=''
									id="updateIncreaseAndDecreaseSCOA-id" /></td>
								<td>
									<div id="updateIncreaseAndDecreaseSCOA"></div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td id="increase-decrease-name">&nbsp;</td>
					<td><span
						style="font-weight:bold; font-size: 15px;padding-right: 5px;"
						id="increase-decrease-string"></span> <s:textfield
							name="dispute.outstandingReserveAmount"
							cssClass="validate-number"></s:textfield></td>
				</tr>
				<tr>
					<td>Attach File:</td>
					<td>
						<div style="position:relative;height:100px;vertical-align:top;">
							<div style="padding:3px 0 0 0;height:19px;">
								<div style="float:left;">
									<input style="height:19px;width:160px;" type='text'
										id="_up_load_text_disputeDetail_01" disabled="disabled">
								</div>
								<div style="float:left; margin:0 0 0 2px">
									<span class="ccm_upload_img"> <input
										onchange="document.getElementById('_up_load_text_disputeDetail_01').value=this.value;"
										class="ccm_file" type="file" name="uploads" size="1" /> <input
										style="height:19px;position:absolute;right:0px;top:0px;"
										type="button" value="Browse..." />
									</span>
								</div>
							</div>

							<div style="padding:3px 0 0 0;height:19px;">
								<div style="float:left;">
									<input style="height:19px;width:160px;" type='text'
										id="_up_load_text_disputeDetail_02" disabled="disabled">
								</div>
								<div style="float:left; margin:0 0 0 2px">
									<span class="ccm_upload_img"> <input
										onchange="document.getElementById('_up_load_text_disputeDetail_02').value=this.value;"
										class="ccm_file" type="file" name="uploads" size="1" /> <input
										style="height:19px;position:absolute;right:0px;top:0px;"
										type="button" value="Browse..." />
									</span>
								</div>
							</div>

							<div style="padding:3px 0 0 0;height:19px;">
								<div style="float:left;">
									<input style="height:19px;width:160px;" type='text'
										id="_up_load_text_disputeDetail_03" disabled="disabled">
								</div>
								<div style="float:left; margin:0 0 0 2px">
									<span class="ccm_upload_img"> <input
										onchange="document.getElementById('_up_load_text_disputeDetail_03').value=this.value;"
										class="ccm_file" type="file" name="uploads" size="1" /> <input
										style="height:19px;position:absolute;right:0px;top:0px;"
										type="button" value="Browse..." />
									</span>
								</div>
							</div>

						</div>
					</td>
				</tr>
				<tr>
					<td valign="top">Notes:</td>
					<td><s:textarea name="dispute.notes"
							cssStyle="padding:0px 1px;overflow:auto ;width:260px;height:60px;"
							cssClass="validate-text"></s:textarea></td>
				</tr>
				<tr>
					<td colspan="2">Your notes have to be more than 10 characters.</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<div class="yui-pe-content" id="contactVendorWindow"
	style="visibility: hidden;">
	<div class="hd">
		<h2>Contact Vendor</h2>
	</div>
	<div class="tabForm" style="padding-bottom:25px;  max-height:600px;">
	<div class="contactVendorWide"/>
    </div>
</div>

<iframe name="uploadFrame_0" id="uploadFrame_0" style="display: none"
	src="javascript:false"></iframe>

<script type="text/javascript"> 
	var disputeId = ${param.disputeId};
	var invoiceId = ${dispute.invoice.id};
	var disputeBalance = ${dispute.disputeBalance == null ? 0 : dispute.disputeBalance};
	var workflowAction = ${dispute.workflowAction == null ? "null" : dispute.workflowAction.id};
	var disputeStatusId  = ${dispute.disputeStatus == null ? "null" : dispute.disputeStatus.id};
	var attachmentPointId = ${dispute.attachmentPoint == null ? "null" : dispute.attachmentPoint.id};
	var disputeFlagShortpaid = '${dispute.flagShortpaid}';
	var outstandingReserveAmount = ${dispute.outstandingReserveAmount == null ? 0 : dispute.outstandingReserveAmount};
</script>

<script type="text/javascript">
var SC_Array = {
	"results": [
		{
		    "id": "",
		    "name": "Enter number to search SCOA"
		}
		<c:forEach items="${accountCodeList}" var="v">
		,{
		    "id": "${v.key}",
		    "name": "${v.value}"
		}
		</c:forEach>
	],
	"total": ${fn:length(accountCodeList)}
};

$(function(){
	mpAccountCodeId = $('#VL_vendorList_eaccount_code').flexbox(SC_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 315,
		name : "searchDisputeVO.accountCodeId"
	});
	mpAccountCodeId.setValue("");
});

$(function(){
	accountCodeCreditTab = $('#VL_vendorList_credit_tab').flexbox(SC_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 260
	});
	accountCodeCreditTab.setValue("");
});

$(function(){
	accountCodeAtDispute1 = $('#VL_vendorList_at_dispute1').flexbox(SC_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 310,
		name : "searchDisputeVO.accountCodeId"
	});
	accountCodeAtDispute1.setValue("");
});

$(function(){
	updateIncreaseAndDecreaseScoa = $('#updateIncreaseAndDecreaseSCOA').flexbox(SC_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 260,
		onSelect : function(){
			var a = $('#updateIncreaseAndDecreaseSCOA-id');
			a.val(updateIncreaseAndDecreaseScoa.getValue());
			if(!a.attr('checked')){
				a.attr('checked',true);
			}
		}
	});
	updateIncreaseAndDecreaseScoa.setValue("");
	
	
	
});

</script>

