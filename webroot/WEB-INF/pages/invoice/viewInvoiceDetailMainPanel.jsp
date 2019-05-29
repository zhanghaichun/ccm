<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.saninco.ccm.util.SystemUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" type="text/css" href="include/css/viewInvoiceDetailMainPanel.css">
<link rel="stylesheet" type="text/css" href="include/css/addnotes.css">
<link rel="stylesheet" type="text/css" href="include/javascript/Calendar/styles/calendar.css">
<link rel="stylesheet" href="include/css/validationResultOfMainPanel.css">
<!-- 控制dispute details 的四个单元格的样式。 -->
<link rel="stylesheet" href="include/css/dispute/disputeDetails.css">
<link rel="stylesheet" href="include/css/dispute/disputeActionRequest.css">
<link rel="stylesheet" href="include/css/hint.min.css">
<link rel="stylesheet" href="include/css/validation/mrc-variance.css">

<link rel="stylesheet" type="text/css" href="include/javascript/flexbox/css/jquery.flexbox.css"><link>
<link rel="stylesheet" type="text/css" href="include/css/validation_result_common.css">
<style>
    .td-note-wrap{
    	width:260px;
    	word-wrap:break-word;
    }


    /* 调整该页面中的日期选择控件的适应。 */
    #leftControl {
    	left: 0;
    }

    #slideshow #slidesContainer {
    	margin-left: 27px;
    }

    .tariff-download-td-note-wrap{
    	white-space: normal!important;
    	word-wrap:break-word;
    }


</style>
<script type="text/javascript" src="include/javascript/Calendar/simplecalendar.js"></script>
<script type="text/javascript" src="include/javascript/yui2/tabview-min.js"></script>
<script type="text/javascript" src="include/javascript/jquery/jquery.ui.core.js"></script>
<script type="text/javascript" src="include/javascript/jquery/jquery.ui.widget.js"></script>
<script type="text/javascript" src="include/javascript/jquery/jquery.ui.accordion.js"></script>
<script type="text/javascript" src="include/javascript/ccm/quicklink.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/saninco/user_select.js"></script>
<script type="text/javascript" src="include/javascript/ccm/invoice_credit/initInvoiceTab.js"></script>
<script type="text/javascript" src="include/javascript/ccm/original.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/ccm/drag.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/ccm/validation/mrc-variance.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/ccm/viewSocaDispute.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/ccm/invoice_credit/creditTab.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/ccm/invoiceDeailgetMiscTab/misc.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/ccm/notesAttachment.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/ccm/customMotiScoa.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/ccm/disputeTab.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/ccm/transactionApprovalTab.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/ccm/tarifflink.js" language="javascript"></script>
<SCRIPT type="text/javascript" src="include/javascript/ccm/common/common.js"></SCRIPT>
<script type="text/javascript" src="include/javascript/ccm/tariffDownload.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/flexbox/js/jquery.flexbox.js"></script>
<script type="text/javascript" src="include/javascript/ccm/invoiceNotes.js" language="javascript"></script>
<script src="include/javascript/validationResult/viewInvoiceValidationResult.js"></script>



<%pageContext.setAttribute("currentUserId", SystemUtil.getCurrentUserId());%>

<div id="pageContainer" class="tabForm">
	<form id="downloadForm" action="download.action" class="variance-hidden" method="post" target="_blank"
		style="display: none;">
		<input type="hidden" name="filePath" />
		<input type="hidden" name="fileName" />
	</form>
	<h3 style="padding-top:0px; padding-bottom:4px;" class="variance-hidden">
		Invoice Detail
	</h3>
	<div style="border-top: 0px none;" class="invoice-details-main-content variance-hidden">
		<div id="user_dashlet">
			<div>
				<table class="tabDetailView2"  width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
<!--						<td class="tabDetailViewDL" width=34%>-->
						<td class="tabDetailViewDL" style="border-right:none;" width=255>
							<table width="100%" cellpadding="0" cellspacing="8" style="text-align:right; table-layout: fixed;" border=0>
								<tr>
									<td  width="100" valign="middle" align="left">Invoice Number </td>
									<td valign="middle" align="left">${invoice.invoiceNumber}</td>
<%--									<td valign="middle" align="right"><s:text name="invoice.invoiceNumber" /></td>--%>
<!--									<td  width="100px">Invoice Number </td>-->
<!--									<td><s:text name="invoice.invoiceNumber" /></td>-->
<!--									<td width=20px rowspan="11"></td>-->
								</tr>
								<tr>
									<td valign="middle" align="left">Vendor </td>
									<td valign="middle" align="left">${invoice.vendor.vendorName}</td>
<!--									<td>Vendor </td>-->
<!--									<td >${invoice.vendor.vendorName}</td>-->
								</tr>
								<tr>
									<td colspan="2"><input type="hidden" value="${invoice.id}" id="invoiceNoteInvoiceIdByinvoice"/></td>
								</tr>
								<tr>
									<td valign="middle" align="left" style="width:200px">BAN</td>
									<td valign="middle" align="left">${invoice.ban.accountNumber}</td>
<!--									<td>Ban</td>-->
<!--									<td>${invoice.ban.accountNumber}</td>-->
								</tr>
								<tr>
									<td colspan="2"><input type="hidden" value="${invoice.ban.id}" id="invoiceNoteBanIdByinvoice"/></td>
								</tr>
								<tr>
									<td valign="middle" align="left">Line of Business</td>
									<td valign="middle" align="left">${invoice.ban.lineOfBusiness}</td>
<!--									<td>Line of Business</td>-->
<!--									<td>${invoice.ban.lineOfBusiness}</td>-->
								</tr>
								<tr>
									<td valign="middle" align="left">Invoice Date</td>
									<td valign="middle" align="left"  id = "_invoiceDate"><s:date format="yyyy-MM-dd" name="invoice.invoiceDate" /></td>
<!--									<td>Invoice Date</td>-->
<!--									<td><s:date format="yyyy-MM-dd" name="invoice.invoiceDate"/></td>-->
								</tr>
								<tr>
									<td valign="middle" align="left">Due Date</td>
									<td valign="middle" align="left"><s:date format="yyyy-MM-dd" name="invoice.invoiceDueDate" /></td>
<!--									<td>Due Date</td>-->
<!--									<td><s:date format="yyyy-MM-dd" name="invoice.invoiceDueDate" /></td>-->
								</tr>
								<tr>
									<td valign="middle" align="left">Received Date</td>
									<td valign="middle" align="left"><s:date format="yyyy-MM-dd" name="invoice.invoiceReceiveDate" /></td>
<!--									<td>Received Date</td>-->
<!--									<td><s:date format="yyyy-MM-dd" name="invoice.invoiceReceiveDate" /></td>-->
								</tr>
								<tr>
									<td valign="middle" align="left">From Date</td>
									<td valign="middle" align="left"><s:date format="yyyy-MM-dd" name="invoice.invoiceStartDate" /></td>
<!--									<td>From Date</td>-->
<!--									<td><s:date format="yyyy-MM-dd" name="invoice.invoiceStartDate" /></td>-->
								</tr>
								<tr>
									<td valign="middle" align="left">To Date</td>
									<td valign="middle" align="left"><s:date format="yyyy-MM-dd" name="invoice.invoiceEndDate" /></td>
<!--									<td>To Date</td>-->
<!--									<td><s:date format="yyyy-MM-dd" name="invoice.invoiceEndDate" /></td>-->
								</tr>
								<!--add by yinghe.fu on 20110830-->
								<tr>
									<td valign="middle" align="left">Load Date</td>
									<td valign="middle" align="left"><s:date format="yyyy-MM-dd" name="invoice.invoiceLoadDate" /></td>
<!--									<td>Load Date</td>-->
<!--									<td><s:date format="yyyy-MM-dd" name="invoice.invoiceLoadDate" /></td>-->
								</tr>
								<tr>
									<td width="100" valign="middle" align="left">Currency</td>
									<td valign="middle" align="left">${invoice.ban.currency.currencyName}</td>
<!--									<td>Currency</td>-->
<!--									<td>${invoice.ban.currency.currencyName}</td>-->
								</tr>
								<tr>
									<td valign="middle" align="left">Status</td>
									<td valign="middle" align="left" id="invoiceStatus_divId" data-invoice-status-id="${invoice.invoiceStatus.id}"><s:text name="invoice.invoiceStatus.invoiceStatusName" /></td>
								</tr>
								<tr>
									<td valign="middle" align="left">Analyst Name</td>
									<td valign="middle" align="left">${assignedAnalys.firstName}&nbsp;${assignedAnalys.lastName}</td>
								</tr>
								<tr>
		                          <td width="100" align="left" valign="middle">Invoice Autopay Flag</td>
									
									<td valign="middle" align="left" id="invoiceAutoPay_divId">
                                        <c:choose>
                                         	<c:when test="${autopaySwitch=='on'}">
                                             	ON
                                            </c:when>
                                            <c:otherwise>
                                                OFF
                                            </c:otherwise>
                                        </c:choose>

                                        <!-- If there are error notes, show them. -->
                                        <c:if test="${not empty autopayErrorNotes}">
                                            <div class="autopay-error-notes-box  validation-status">
                                                
                                                <span class='icon failed autopay-error-notes-trigger'></span>

                                                <ol class="autopay-error-notes-content">
                                                    <c:forEach items="${autopayErrorNotes}" var="autopayErrorNote">
                                                        <li class="autopayErrorNote">${autopayErrorNote.errorNote}</li>
                                                    </c:forEach>
                                                </ol>

                                            </div>

                                        </c:if>
									</td>
								</tr>
								<tr>
								 <td width="100" align="left" valign="middle"> 
								 <c:if test="${openAutoPay}">
								<input id = "autoPay_B" type="button" value="BAN Autopay Flag"
												onclick="openAutoPay(${invoice.ban.id});" />
								 </c:if>
								 </td>
								</tr>
							</table>
						</td>
						<td class="tabDetailViewDL" style="border-right:none;" width=255>
							<table width="100%" cellpadding="0" cellspacing="8" style="text-align:right;" border=0>
								<tr>
								
			                        <td width="106" valign="middle" align="left" class = "validation-status"> MRC
			                        <div class="validation-status hint--bottom-right" data-hint="${auditIcon.mrc_audit_notes}" style="display:inline-block; cursor: pointer;">
		                       		  <c:choose>
                               			 <c:when test="${auditIcon.mrc_audit_status_id == 1}">
                                 			<span class='icon pass'></span>
                               			 </c:when>
                               			 <c:when test="${auditIcon.mrc_audit_status_id == 2}">
                               				 <span class='icon failed'></span>
                               			 </c:when>
                                		 <c:when test="${auditIcon.mrc_audit_status_id == 3}">
                                			 <span class='icon can-not-validate'></span>
                               			 </c:when>
                               			 <c:when test="${auditIcon.mrc_audit_status_id == 4}">
                                			  <span class='icon no-reference'></span>
                               			 </c:when>

                               		  </c:choose>
                                     </div>
			                        
			                        </td>
			                        <td valign="middle" align="right">
				                        <fmt:formatNumber
													value="${(invoice.invoiceMrc != null) ? invoice.invoiceMrc : 0.00}"
													type="currency" pattern="#,##0.00" />
									</td>
		                      </tr>
		                      <tr>
		                        <td width="106" valign="middle" align="left" class = "validation-status">Usage
								<!--<c:if test="${auditIcon.usage_bill_keep_status_id != 1}">onclick = "showValidationResult(null,${invoice.id},'Usage')"</c:if>-->
		                         <div  style="display:inline-block; cursor: pointer;" class="hint--bottom-right" data-hint="${auditIcon.usage_audit_notes}">
		                            <!-- <c:choose>
                                       <c:when test="${auditIcon.usage_audit_status_id == 1}">
                                          <span class='icon pass' <c:if test="${auditIcon.usage_bill_keep_status_id == 1}">data-hint=" Pass: For more information, see the invoice item tab below."</c:if>></span>
                                       </c:when>
                                       <c:when test="${auditIcon.usage_audit_status_id == 2}">
                                          <span class='icon failed' <c:if test="${auditIcon.usage_bill_keep_status_id == 1}">data-hint=" Failed: For more information, see the invoice item tab below."</c:if>></span>
                                      </c:when>
                                       <c:when test="${auditIcon.usage_audit_status_id == 3}">
                                          <span class='icon can-not-validate' <c:if test="${auditIcon.usage_bill_keep_status_id == 1}">data-hint=" Can not Validate: For more information, see the invoice item tab below."</c:if>></span>
                                       </c:when>
                                       <c:when test="${auditIcon.usage_audit_status_id == 4}">
                                          <span class='icon no-reference' <c:if test="${auditIcon.usage_bill_keep_status_id == 1}">data-hint=" No Reference: For more information, see the invoice item tab below."</c:if>></span>
                                       </c:when>
                                     </c:choose> -->
                                      <c:choose>
                                       <c:when test="${auditIcon.usage_audit_status_id == 1}">
                                          <span class='icon pass'></span>
                                       </c:when>
                                       <c:when test="${auditIcon.usage_audit_status_id == 2}">
                                          <span class='icon failed'></span>
                                      </c:when>
                                       <c:when test="${auditIcon.usage_audit_status_id == 3}">
                                          <span class='icon can-not-validate'></span>
                                       </c:when>
                                       <c:when test="${auditIcon.usage_audit_status_id == 4}">
                                          <span class='icon no-reference'></span>
                                       </c:when>
                                     </c:choose>
                                </div>
		                        
		                        </td>
		                        <td valign="middle" align="right">
		                        	<fmt:formatNumber
												value="${(invoice.invoiceUsage != null) ? invoice.invoiceUsage : 0.00}"
												type="currency" pattern="#,##0.00" />
								</td>
		                      </tr>
		                      <tr>
		                        <td width="106" valign="middle" align="left" class = "validation-status">OCC 
		                        <c:choose>  
									<c:when test="${invoice.ban.accountNumber == 'B1E79A1616135'}">
								   		<div class="validation-status" style="display:inline-block; cursor: pointer;" onclick="showValidationResult(null,${invoice.id},'OCC')">
								   		<c:choose>
											<c:when test="${auditIcon.occ_audit_status_id == 1}">
                                 				<span class='icon pass'></span>
                               			 	</c:when>
                               			 	<c:when test="${auditIcon.occ_audit_status_id == 2}">
                               				 	<span class='icon failed'></span>
                               			 	</c:when>
                                		 	<c:when test="${auditIcon.occ_audit_status_id == 3}">
                                			 	<span class='icon can-not-validate'></span>
                               			 	</c:when>
                               			 	<c:when test="${auditIcon.occ_audit_status_id == 4}">
                                			 	<span class='icon no-reference'></span>
                               			 	</c:when>
                               			</c:choose>
								   	</c:when>  
									<c:when test="${invoice.ban.accountNumber == 'B1F70Q3001165'}">
								   		<div class="validation-status" style="display:inline-block; cursor: pointer;" onclick="showValidationResult(null,${invoice.id},'OCC')">
								   			<c:choose>
												<c:when test="${auditIcon.occ_audit_status_id == 1}">
	                                 				<span class='icon pass' ></span>
	                               			 	</c:when>
	                               			 	<c:when test="${auditIcon.occ_audit_status_id == 2}">
	                               				 	<span class='icon failed' ></span>
	                               			 	</c:when>
	                                		 	<c:when test="${auditIcon.occ_audit_status_id == 3}">
	                                			 	<span class='icon can-not-validate' ></span>
	                               			 	</c:when>
	                               			 	<c:when test="${auditIcon.occ_audit_status_id == 4}">
	                                			 	<span class='icon no-reference' ></span>
	                               			 	</c:when>
	                               			</c:choose>
								   	</c:when>  
									<c:when test="${invoice.ban.accountNumber == 'B1EM9A8501304'}">
								   		<div class="validation-status " style="display:inline-block; cursor: pointer;" onclick="showValidationResult(null,${invoice.id},'OCC')">
								   			<c:choose>
												<c:when test="${auditIcon.occ_audit_status_id == 1}">
	                                 				<span class='icon pass' ></span>
	                               			 	</c:when>
	                               			 	<c:when test="${auditIcon.occ_audit_status_id == 2}">
	                               				 	<span class='icon failed' ></span>
	                               			 	</c:when>
	                                		 	<c:when test="${auditIcon.occ_audit_status_id == 3}">
	                                			 	<span class='icon can-not-validate' ></span>
	                               			 	</c:when>
	                               			 	<c:when test="${auditIcon.occ_audit_status_id == 4}">
	                                			 	<span class='icon no-reference' ></span>
	                               			 	</c:when>
	                               			</c:choose>
								   	</c:when>  
									<c:when test="${invoice.ban.accountNumber == 'B1E74A1013102'}">
								   		<div class="validation-status " style="display:inline-block; cursor: pointer;" onclick="showValidationResult(null,${invoice.id},'OCC')">
									   		<c:choose>
												<c:when test="${auditIcon.occ_audit_status_id == 1}">
	                                 				<span class='icon pass' ></span>
	                               			 	</c:when>
	                               			 	<c:when test="${auditIcon.occ_audit_status_id == 2}">
	                               				 	<span class='icon failed' ></span>
	                               			 	</c:when>
	                                		 	<c:when test="${auditIcon.occ_audit_status_id == 3}">
	                                			 	<span class='icon can-not-validate' ></span>
	                               			 	</c:when>
	                               			 	<c:when test="${auditIcon.occ_audit_status_id == 4}">
	                                			 	<span class='icon no-reference' ></span>
	                               			 	</c:when>
	                               			</c:choose>
								   	</c:when>  
								   	<c:otherwise>
								   		<div class="validation-status hint--bottom-right" data-hint=" ${auditIcon.occ_audit_notes}" style="display:inline-block; cursor: pointer;">
									   		<c:choose>
												<c:when test="${auditIcon.occ_audit_status_id == 1}">
	                                 				<span class='icon pass'></span>
	                               			 	</c:when>
	                               			 	<c:when test="${auditIcon.occ_audit_status_id == 2}">
	                               				 	<span class='icon failed' ></span>
	                               			 	</c:when>
	                                		 	<c:when test="${auditIcon.occ_audit_status_id == 3}">
	                                			 	<span class='icon can-not-validate'></span>
	                               			 	</c:when>
	                               			 	<c:when test="${auditIcon.occ_audit_status_id == 4}">
	                                			 	<span class='icon no-reference'></span>
	                               			 	</c:when>
	                               			</c:choose>
								   	</c:otherwise>  
								</c:choose>  
		                        
		                       		  <!-- <c:choose>
                               			 <c:when test="${auditIcon.occ_audit_status_id == 1}">
                                 			<span class='icon pass' data-hint=" Pass: For more information, see the invoice item tab below."></span>
                               			 </c:when>
                               			 <c:when test="${auditIcon.occ_audit_status_id == 2}">
                               				 <span class='icon failed' data-hint=" Failed: For more information, see the invoice item tab below."></span>
                               			 </c:when>
                                		 <c:when test="${auditIcon.occ_audit_status_id == 3}">
                                			 <span class='icon can-not-validate' data-hint=" Can not Validate: For more information, see the invoice item tab below."></span>
                               			 </c:when>
                               			 <c:when test="${auditIcon.occ_audit_status_id == 4}">
                                			  <span class='icon no-reference' data-hint=" No Reference: For more information, see the invoice item tab below."></span>
                               			 </c:when>

                               		  </c:choose> -->
                               		  
                             		</div>
		                        </td>
		                        <td valign="middle" align="right">
		                        	<fmt:formatNumber
												value="${(invoice.invoiceOcc != null) ? invoice.invoiceOcc : 0.00}"
												type="currency" pattern="#,##0.00" />
								</td>
		                      </tr>
		                      <tr>
		                        <td width="106" valign="middle" align="left" class = "validation-status">Adjustment
		                        <div class="validation-status hint--bottom-right" data-hint=" ${auditIcon.adjustment_audit_notes}" style="display:inline-block; cursor: pointer;">
		                       		  <!-- <c:choose>
                               			 <c:when test="${auditIcon.adjustment_audit_status_id == 1}">
                                 			<span class='icon pass' data-hint=" Pass: For more information, see the invoice item tab below."></span>
                               			 </c:when>
                               			 <c:when test="${auditIcon.adjustment_audit_status_id == 2}">
                               				 <span class='icon failed' data-hint=" Failed: For more information, see the invoice item tab below."></span>
                               			 </c:when>
                                		 <c:when test="${auditIcon.adjustment_audit_status_id == 3}">
                                			<span class='icon can-not-validate' data-hint=" Can not Validate: For more information, see the invoice item tab below."></span>
                               			 </c:when>
                               			 <c:when test="${auditIcon.adjustment_audit_status_id == 4}">
                                			 <span class='icon no-reference' data-hint=" No Reference: For more information, see the invoice item tab below."></span>
                               			 </c:when>

                               		  </c:choose> -->
                               		  
                               		  <c:choose>
                               			 <c:when test="${auditIcon.adjustment_audit_status_id == 1}">
                                 			<span class='icon pass'></span>
                               			 </c:when>
                               			 <c:when test="${auditIcon.adjustment_audit_status_id == 2}">
                               				 <span class='icon failed'></span>
                               			 </c:when>
                                		 <c:when test="${auditIcon.adjustment_audit_status_id == 3}">
                                			<span class='icon can-not-validate'></span>
                               			 </c:when>
                               			 <c:when test="${auditIcon.adjustment_audit_status_id == 4}">
                                			 <span class='icon no-reference'></span>
                               			 </c:when>

                               		  </c:choose>
                                     </div>
		                        </td>
		                        <td valign="middle" align="right">
		                        	<fmt:formatNumber
												value="${(invoice.invoiceAdjustment != null) ? invoice.invoiceAdjustment : 0.00}"
												type="currency" pattern="#,##0.00" />
								</td>
		                      </tr>
		                      <tr>
		                        <td width="106" valign="middle" align="left"> Credit </td>
		                        <td valign="middle" align="right">
		                        	<fmt:formatNumber
												value="${(invoice.invoiceCredit != null) ? invoice.invoiceCredit : 0.00}"
												type="currency" pattern="#,##0.00" />
								</td>
		                      </tr>
		                      <tr>
		                        <td width="106" valign="middle" align="left" class = "validation-status"> LPC 
		                        <div  style="display:inline-block; cursor: pointer;" onclick = "showValidationResult(null,${invoice.id},'LPC')">
		                        <c:choose>
                                <c:when test="${auditIcon.lpc_audit_status_id == 1}">
                                 <span class='icon pass' ></span>
                                </c:when>
                                <c:when test="${auditIcon.lpc_audit_status_id == 2}">
                                <span class='icon failed' ></span>
                                </c:when>
                                <c:when test="${auditIcon.lpc_audit_status_id == 3}">
                                <span class='icon can-not-validate' ></span>
                                </c:when>
                                <c:when test="${auditIcon.lpc_audit_status_id == 4}">
                                 <span class='icon no-reference'></span>
                                </c:when>

                                </c:choose>
                                </div>
		                        
		                        </td>
		                        <td valign="middle" align="right">
		                        	<fmt:formatNumber
												value="${(invoice.invoiceLatePaymentCharge != null) ? invoice.invoiceLatePaymentCharge : 0.00}"
												type="currency" pattern="#,##0.00" />
								</td>
		                      </tr>
		                      <tr>
		                        <td width="150" valign="middle" align="left" class = "validation-status"> Tax and Other Fees
		                        
		                        <div  style="display:inline-block; cursor: pointer;" onclick = "showValidationResult(null,${invoice.id},'Tax')">
		                        <c:choose>
                                <c:when test="${auditIcon.tax_audit_status_id == 1}">
                                 <span class='icon pass' ></span>
                                </c:when>
                                <c:when test="${auditIcon.tax_audit_status_id == 2}">
                                <span class='icon failed' ></span>
                                </c:when>
                                <c:when test="${auditIcon.tax_audit_status_id == 3}">
                                <span class='icon can-not-validate' ></span>
                                </c:when>
                                <c:when test="${auditIcon.tax_audit_status_id == 4}">
                                 <span class='icon no-reference'></span>
                                </c:when>

                                </c:choose>
                                </div>
		                        
		                        
		                        </td>
		                        <td valign="middle"  align="right">
		                        	<fmt:formatNumber
												value="${(invoiceTaxAndOtherFees != null) ? invoiceTaxAndOtherFees : 0.00}"
												type="currency" pattern="#,##0.00" />
								</td>
		                      </tr>
		                      <tr>
		                        <td width="106" valign="middle" align="left" class = "validation-status"> Current Due
		                        
		                        <div  style="display:inline-block; cursor: pointer;" onclick = "showValidationResult(null,${invoice.id},'CurrentDue')">
		                        <c:choose>
                                <c:when test="${auditIcon.current_due_audit_status_id == 1}">
                                 <span class='icon pass' ></span>
                                </c:when>
                                <c:when test="${auditIcon.current_due_audit_status_id == 2}">
                                <span class='icon failed' ></span>
                                </c:when>
                                <c:when test="${auditIcon.current_due_audit_status_id == 3}">
                                <span class='icon can-not-validate' ></span>
                                </c:when>
                                <c:when test="${auditIcon.current_due_audit_status_id == 4}">
                                 <span class='icon no-reference'></span>
                                </c:when>

                                </c:choose>
                                </div>
		                        
		                        
		                        </td>
		                        <td valign="middle" align="right">
		                        	<fmt:formatNumber
												value="${(invoice.invoiceCurrentDue != null) ? invoice.invoiceCurrentDue : 0.00}"
												type="currency" pattern="#,##0.00" />
								</td>
		                      </tr>
		                      <tr>
		                        <td width="106" valign="middle" align="left">Face Page Difference</td>
		                        <td valign="middle" align="right">
		                        	<fmt:formatNumber
												value="${(invoice.facepageDifference != null) ? invoice.facepageDifference : 0.00}"
												type="currency" pattern="#,##0.00" />
								</td>
		                      </tr>  
		                     
	                          <c:forEach items="${toleranceRateList}" var="toleranceRate">
	                             <c:if test="${not empty toleranceRate}">
                                   <tr>
									   <td width="106" valign="middle" align="left">${toleranceRate.key}</td>
	                      			   <td align="right" valign="middle">${toleranceRate.value}</td>
								   </tr>
							     </c:if>
							  </c:forEach>
							  
		                  </table>
		                  
		                  <table width="100%" cellpadding="0" style="text-align:right;" border=0>
		                      <tr>
		                        <c:if test="${banExemptionFlag == 'isShow'}">
		                          <td  valign="middle" align="left" > 
		                             <span style="padding-left:6px; vertical-align:top;" >  Exemption Certificate </span>
		                             <img src="include/images/exemptionCertificate.png" onclick="showExemptionNotes()" id = "exemption_png">		                             		                            	
		                         </td>
		                        </c:if>
		                      </tr>
		                  </table>
	                  </td>
	                  
	<!-- 
						<td class="tabDetailViewDL" width=33%>
							<div>
								<div style="float:left;">
									<ul class="yui-nav"
										style="width:110px;">
										<li>
											Usage
										</li>
										<li>
											Tax and Other Fees
										</li>
										<li>
											LPC
										</li>
										<li>
											MRC
										</li>
										<li>
											OCC
										</li>
										<li>
											Adjustment
										</li>
										<li>
											Credit
										</li>
										<li>
											Current Due
										</li>
										<li>
											Face Page Difference
										</li>
									</ul>
								</div>
								<div style="float:right;">
									<ul class="yui-nav" style="width:105px;margin-right:20px;">
										<li>
											<fmt:formatNumber
												value="${(invoice.invoiceUsage != null) ? invoice.invoiceUsage : 0.00}"
												type="currency" pattern="#,##0.00" />
										</li>
										<li>
											<fmt:formatNumber
												value="${(invoiceTaxAndOtherFees != null) ? invoiceTaxAndOtherFees : 0.00}"
												type="currency" pattern="#,##0.00" />
										</li>
										<li>
											<fmt:formatNumber
												value="${(invoice.invoiceLatePaymentCharge != null) ? invoice.invoiceLatePaymentCharge : 0.00}"
												type="currency" pattern="#,##0.00" />
										</li>
										<li>
											<fmt:formatNumber
												value="${(invoice.invoiceMrc != null) ? invoice.invoiceMrc : 0.00}"
												type="currency" pattern="#,##0.00" />
										</li>
										<li>
											<fmt:formatNumber
												value="${(invoice.invoiceOcc != null) ? invoice.invoiceOcc : 0.00}"
												type="currency" pattern="#,##0.00" />
										</li>
										<li>
											<fmt:formatNumber
												value="${(invoice.invoiceAdjustment != null) ? invoice.invoiceAdjustment : 0.00}"
												type="currency" pattern="#,##0.00" />
										</li>
										<li>
											<fmt:formatNumber
												value="${(invoice.invoiceCredit != null) ? invoice.invoiceCredit : 0.00}"
												type="currency" pattern="#,##0.00" />
										</li>
										<li>
											<fmt:formatNumber
												value="${(invoice.calculatedCurrentDue != null) ? invoice.calculatedCurrentDue : 0.00}"
												type="currency" pattern="#,##0.00" />
										</li>
										<li>
											<fmt:formatNumber
												value="${(invoice.facepageDifference != null) ? invoice.facepageDifference : 0.00}"
												type="currency" pattern="#,##0.00" />
										</li>
									</ul>
								</div>
							</div>
						</td>
	-->
						<td class="tabDetailViewDL" style="border-right:none;" width=255>
							<table width="100%" cellpadding="0" cellspacing="8" style="text-align:right; border-bottom:#d2d2d4 solid 1px" border=0>
		                    	<tr>
			                        <td width="100" valign="middle" align="left"> Previous Balance</td>
			                        <td valign="middle" align="right">
			                        	<fmt:formatNumber
												value="${(invoice.invoicePreviousBalance != null) ? invoice.invoicePreviousBalance : 0.00}"
												type="currency" pattern="#,##0.00" />
									</td>
		                      	</tr>
		                      	<tr>
			                        <td width="150px" valign="middle" align="left" class = "validation-status"> Previous Payment
			                        <div  style="display:inline-block; cursor: pointer;" onclick = "showValidationResult(null,${invoice.id},'Payment')">
		                       		  <c:choose>
                               			 <c:when test="${auditIcon.payment_audit_status_id == 1}">
                                 			<span class='icon pass' ></span>
                               			 </c:when>
                               			 <c:when test="${auditIcon.payment_audit_status_id == 2}">
                               				 <span class='icon failed' ></span>
                               			 </c:when>
                                		 <c:when test="${auditIcon.payment_audit_status_id == 3}">
                                			 <span class='icon can-not-validate' ></span>
                               			 </c:when>
                               			 <c:when test="${auditIcon.payment_audit_status_id == 4}">
                                			  <span class='icon no-reference'></span>
                               			 </c:when>

                               		  </c:choose>
                                     </div>
			                        		                        
			                        </td>
			                        <td valign="middle" align="right">			                        
			                        	<fmt:formatNumber
												value="${(invoice.invoicePreviousPayment != null) ? invoice.invoicePreviousPayment : 0.00}"
												type="currency" pattern="#,##0.00" />
									</td>
		                      	</tr>
		                      	<tr>
			                        <td width="100" valign="middle" align="left"> Previous Adjustment </td>
			                        <td valign="middle" align="right">
			                        	<fmt:formatNumber
												value="${(invoice.invoicePreviousAdjustment != null) ? invoice.invoicePreviousAdjustment : 0.00}"
												type="currency" pattern="#,##0.00" />
									</td>
		                      	</tr>
		                      	<tr>
			                        <td width="100" valign="middle" align="left"> Balance Forward </td>
			                        <td valign="middle" align="right">
			                        	<fmt:formatNumber
												value="${(invoice.invoiceBalanceForward != null) ? invoice.invoiceBalanceForward : 0.00}"
												type="currency" pattern="#,##0.00" />
									</td>
		                      	</tr>
		                      	<tr>
			                        <td width="100" valign="middle" style="padding-bottom:8px;" align="left"> Total Due</td>
			                        <td valign="middle" style="padding-bottom:8px;" align="right">
			                        	<fmt:formatNumber
												value="${(invoice.invoiceTotalDue != null) ? invoice.invoiceTotalDue : 0.00}"
												type="currency" pattern="#,##0.00" />
									</td>
		                      	</tr>
		                    </table>
		                    <table width="100%" cellpadding="0" cellspacing="8" style="margin-top:6px;" border=0>
		                        <tr>
		                          <td width="100" align="left" valign="middle" >Payment Amount</td>
		                          <td align="right" valign="middle" id="approveCallForPaymentAmount">
		                          	<fmt:formatNumber
												value="${(invoice.paymentAmount != null) ? invoice.paymentAmount : 0.00}"
												type="currency" pattern="#,##0.00" />
								  </td>
		                        </tr>
		                        <tr>
		                          <td width="100" align="left" valign="middle">Dispute Amount</td>
		                          <td align="right" valign="middle" id="approveCallForDisputeAmount">
		                          	<fmt:formatNumber
												value="${(invoice.disputeAmount != null) ? invoice.disputeAmount : 0.00}"
												type="currency" pattern="#,##0.00" />
								  </td>
		                        </tr>
		                        <tr>
		                          <td width="100" align="left" valign="middle"> Adjustment Amount</td>
		                          <td align="right" valign="middle" id="approveCallForMiscAdjustmentAmount">
		                          	<fmt:formatNumber
												value="${(invoice.miscAdjustmentAmount != null) ? invoice.miscAdjustmentAmount : 0.00}"
												type="currency" pattern="#,##0.00" />
								  </td>
		                        </tr>
		                        
		                    </table>	                   
	                    </td>
	
	<!-- 
						<td rowspan="2" width=33%>
							<div style="height:140px;" class="tabDetailViewDL">
								<div style="float:left;text-align:right;">
									<ul class="yui-nav" style="width:110px;">
										<li>
											Previous Balance
										</li>
										<li>
											Previous Payment
										</li>
										<li>
											Previous Adjustment
										</li>
										<li>
											Balance Forward
										</li>
										<li>
											Total Due
										</li>
									</ul>
								</div>
								<div style="float:right;">
									<ul class="yui-nav" style="width:105px;margin-right:20px;">
										<li>
											<fmt:formatNumber
												value="${(invoice.invoicePreviousBalance != null) ? invoice.invoicePreviousBalance : 0.00}"
												type="currency" pattern="#,##0.00" />
										</li>
										<li>
											<fmt:formatNumber
												value="${(invoice.invoicePreviousPayment != null) ? invoice.invoicePreviousPayment : 0.00}"
												type="currency" pattern="#,##0.00" />
										</li>
										<li>
											<fmt:formatNumber
												value="${(invoice.invoicePreviousAdjustment != null) ? invoice.invoicePreviousAdjustment : 0.00}"
												type="currency" pattern="#,##0.00" />
										</li>
										<li>
											<fmt:formatNumber
												value="${(invoice.invoiceBalanceForward != null) ? invoice.invoiceBalanceForward : 0.00}"
												type="currency" pattern="#,##0.00" />
										</li>
										<li>
											<fmt:formatNumber
												value="${(invoice.invoiceTotalDue != null) ? invoice.invoiceTotalDue : 0.00}"
												type="currency" pattern="#,##0.00" />
										</li>
									</ul>
								</div>
							</div>
							<div style="height:135px;" class="tabDetailViewDL">
								<div style="float:left;text-align:right;">
									<ul class="yui-nav" style="width:110px;">
										<li>
											Payment Amount
										</li>
										<li>
											Dispute Amount
										</li>
										<li>
											Adjustment Amount
										</li>
									</ul>
								</div>
								<div style="float:right;">
									<ul class="yui-nav" style="width:105px;margin-right:20px;">
										<li id="approveCallForPaymentAmount">
											<fmt:formatNumber
												value="${(invoice.paymentAmount != null) ? invoice.paymentAmount : 0.00}"
												type="currency" pattern="#,##0.00" />
										</li>
										<li id="approveCallForDisputeAmount">
											<fmt:formatNumber
												value="${(invoice.disputeAmount != null) ? invoice.disputeAmount : 0.00}"
												type="currency" pattern="#,##0.00" />
										</li>
										<li id="approveCallForMiscAdjustmentAmount">
											<fmt:formatNumber
												value="${(invoice.miscAdjustmentAmount != null) ? invoice.miscAdjustmentAmount : 0.00}"
												type="currency" pattern="#,##0.00" />
										</li>
									</ul>
								</div>
							</div>
						</td>
	-->
						<td class="tabDetailViewDL">
							<table width="100%" cellpadding="0" cellspacing="6" border=0>
								<c:forEach items="${invoiceLabelList}" var="invoiceLabel">
									<tr>
										<td width="22" align="left" valign="middle">
											<img name="${invoiceLabel.description}"
												id="${invoiceLabel.labelName}"
												src="${invoiceLabel.iconFilePath}"
												onClick="showLabel(event,name,id)"
												onMouseOut="disableItemLabel()">
										</td>
										<td align="left" valign="middle">
											${invoiceLabel.labelName}
										</td>
									</tr>
								</c:forEach>
							</table>
							<table border="0" cellpadding="0" cellspacing="0"
								style="table-layout: fixed; width: 100%;">
								<tr>
									<td colspan="4">
										<div align="left"
											style="width: 100%; -webkit-overflow-scrolling: touch; overflow-x: auto; overflow-y: hidden; padding-bottom: 20px;">
											<div id="_dataTable"></div>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="4">
										<div style="width: 1000px" id="_dataTable_page"></div>
										<%--										<div style="padding: 2px,0px,0px,160px;width:100%;">--%>
										<div style="padding: 2px, 0px, 0px, 2px; width: 100%;">
											<input type="button" value="Add Notes"
												onclick="serachInvoiceNote(true);" />
										</div>
									</td>
									<%--									<td align="right" style="padding-top: 8px;">--%>
									<%--										<input type="button" value="Add Notes" onclick="serachInvoiceNote();"/>--%>
									<%--									</td>--%>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="penalty-notes-wrapper-exemption">
		 'Tariff & Contract' tab below. <br>
		 <c:forEach items="${banExemptionDateList}" var="datelist">
         ${datelist.name}: ${datelist.term_start_date} -- ${datelist.term_end_date}<br>
         </c:forEach>
		</div>
		
		<div id="InvoiceNoteWindow">
		   <div class="hd" id = "addTitle">Add Notes</div>
			<div class="bd" id="reg">
			<form id="handleInvoiceNoteActionForm" action="doInvoiceNote.action" enctype="multipart/form-data" method="POST" target="uploadFrame_0">
				<table width="95%" align="center" style="margin: auto;" cellspacing="5">
					<tr>
						<td colspan="2">
						
							<input name="noteInvoiceId" type="hidden" value="${invoice.id}" id="invoiceNoteInvoiceIdByinvoice"/>
							<input name="starFlag" type="hidden" value="N"/>
							<input name="noteUserId" type="hidden"/>
							<input name="noteRoleId" type="hidden"/>
							<input name="noteBanId" type="hidden" value="${invoice.ban.id}" id="invoiceNoteBanIdByinvoice"/>
							<input name="addBanNoteFlag" type="hidden" value="" id="addBanNoteFlag"/>
						</td>
					</tr>
					<tr>
						<td>
							BAN:
						</td>
						<td>
							<div>
								<div style="float:left;width:92%;">
									<input type="text" style="width:100%;height:19px;max-width:380px;" value="${invoice.ban.accountNumber}" id="invoiceNoteBan" disabled="disabled"/>
								</div>
								<div class="star xingnw1" style="background-size: cover;" id="starDiv" onclick="starOnclick()"></div>
								<div class="clear"></div>
							</div>

							<div>
								
								
							</div>
						</td>
					</tr>
					<tr id ="invoiceNumberFlag"><td>
							Invoice Number:
						</td>
						<td>
								<div style="float:left;width:92%;">
									<input type="text" style="width:100%;height:19px;max-width:380px;" value="${invoice.invoiceNumber}" id="invoiceNoteBan" disabled="disabled"/>
								</div>
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;
						</td>
						<td style="height:19px;">
							<div style="float:left;width:100%;vertical-align:middle;">
								<input type="checkbox" id="privateCheckbox" name="privateFlag" onclick="privateOnclick()" 
									   style="vertical-align: middle;"/>
									Private 
					   		</div>
						</td>
					</tr>
					<tr id="selectUserRow">
						<td>
							@
						</td>
						<td style="height:19px;">
					   		<div id="selectUserDiv"></div>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: text-top;">Notes:</td>
						<td >
							<div>
								<div style="float:left;width:98%;">
									<textarea id="ps" name = "noteNotes"
										style="overflow:auto ; width:100%; height:60px;max-width:380px;resize: none;"
										class="validate-text">
									</textarea>
								</div>
								<div  style="font: 15px; color: red; float:left; ">*</div>
								<div class="clear"></div>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<div style="text-align:right; padding:0 15px 0 0 ;margin-right:15px"><strong id="num">255</strong><strong> characters left</strong></div>
						</td>
					</tr>
					<tr>
					<td>
							&nbsp;
					</td>
					<td>
					<div id = "addNoteFile" style="overflow-y:auto;overflow-x: hidden;max-height:100px;vertical-align: top;position:relative;">
		        	</div>
	        		</td>
		        	</tr>			    
					<tr>
					    <td>
							&nbsp;
					    </td>
						<td>
							<div  id="attachDiv" style="float:left;width:180px; position:relative;">
								<input style="text-align:center;width:70px;height: 19px;position: absolute;top: 0;
											  left: 0;z-index: 100;opacity: 0;cursor: pointer;" type="file" 
										name="uploads" multiple="multiple" onchange="filesProcess(this.files,1)" />
								<div class="hjdpdiy3 butatc">Attach</div>
							</div>
							<div style="float:right; position:relative;right: 6px;" >
								<input style="text-align:center; padding:1px 4px;border-color: #808080;" type="button" value="Save" id="sub" onclick="saveNotes()"/>
								<input style="text-align:center; padding:1px 4px;border-color: #808080;" type="button" value="Clear" id="clear" onclick="clearNotes()"/>
								<input style="text-align:center; padding:1px 4px;border-color: #808080;" type="button" value="Cancel" id="cancel" onclick="YAHOO.ccm.container.InvoiceNoteWindow.hide();"/>
							</div>
							
						</td>
					</tr>
				</table>
				</form>
			</div>
	    </div>
		
		<div id="head_2_1" style="padding:3px 10px 10px 10px;  border:1 solid;">
<!--		<div id="head_2_1" style="padding:4px 2px 2px 4px;border:1 solid;">-->
			<!-- Slideshow HTML -->
			<div id="slideshow">
<!--				<table width="100%" border="0">-->
				<table border="0" width="100%" style="margin-bottom:2px;">
					<tr>
						<td nowrap="nowrap" style="width: 50%;">
							<div style="OVERFLOW: hidden" id="slidesContainer"></div>
						</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td align="right" nowrap="nowrap" style="width: 50%;">
						
							<c:if test="${invoice.invoiceStatus.id == 10 || invoice.invoiceStatus.id == 11 || invoice.invoiceStatus.id == 12 || invoice.invoiceStatus.id == 13 || invoice.invoiceStatus.id == 14 || invoice.invoiceStatus.id == 15}">
							    <input id="externalApprovalBtn" type="button" width="135" value="External Approval" align="right" onclick="eternalApproval();"></input>
							</c:if>
							<c:if test="${fn:substring(invoice.ban.accountNumber,3,4) == 'C' || fn:substring(invoice.ban.accountNumber,3,4) == 'D' || isUsageBan == 'Y' }">
							    <input type="button" width="135" value="Run Validation Report" align="right" onclick="runRepoet();"></input>
							    <input type="button" width="135" value="Run Telarix Report" align="right" onclick="downloadUsageReport();"></input>
							</c:if>
							
						    <!-- <input type="button" width="135" value="Add Attachment" align="right" onclick="serachInvoiceNote(false);"></input> -->
							<span class="select1"> <s:select id="__related_invoices"
									list="relatedInvoicesList" headerKey="all"
									headerValue="Related Invoices" listKey="key" listValue="value"
									onchange="relatedInvoices();" /> </span>
							<input type="button" value="Download Invoice" width="135"
								onclick="downloadInvoiceExcel();" align="right" />		
								
							<c:if test="${banAuditFlag == 'Y'}">
								<input type="button" value="Download Validation" width="135"
									onclick="downloadInvoiceValidationExcel()" align="right" />
					  			<c:if test="${invoice.invoiceStatus.id < 21}">
									<input type="button" value="Validation" width="135" id = "invoiceValidationBtn"
									onclick="auditInvoice();" align="right" />
					 			</c:if>
                            </c:if>
						</td>
					</tr>
				</table>
			</div>
			
		<div id="defcal_section">
			<div id="demo" class="yui-navset">
				<ul class="yui-nav">
					<sec:authorize ifAllGranted="FUNCTION_1110">
						<li>
							<a href="#tab8" onclick="findAccordionView();"><em>SCOA and Dispute</em> </a>
						</li>
					</sec:authorize>
					<sec:authorize ifAllGranted="FUNCTION_1120">
					<li>
						<a href="#tab3" onclick="invoiceTabInit.get('tab3');"><em>Dispute Claim</em>
						</a>
					</li>
					</sec:authorize>
					<sec:authorize ifAllGranted="FUNCTION_1130">					
					<li>
						<a href="#tab6" onclick="invoiceTabInit.get('tab6');"><em>Credit<c:if test="${isShowDueDate}"><img id="credit_img" src="include/images/credit_1.png" style="padding-left:5px;padding-bottom:4px;"></c:if></em></a>
					</li>
					</sec:authorize>
					<sec:authorize ifAllGranted="FUNCTION_1140">
					<li>
						<a href="#tab7" onclick="invoiceTabInit.get('tab7');"><em>Misc Adjustment</em> </a>
					</li>
					</sec:authorize>
					<sec:authorize ifAllGranted="FUNCTION_1150">
					<li>
						<a href="#tab2" onclick="invoiceTabInit.get('tab2');"><em>Transaction
								Approval</em> </a>
					</li>
					</sec:authorize>
					<li>
						<a href="#tab4" onclick="invoiceTabInit.get('tab4');"><em>Original</em>
						</a>
					</li>
					<li>
						<a href="#tab5" onclick="invoiceTabInit.get('tab5');"><em>Tariff & Contract</em> </a>
					</li>
					<li>
						<a href="#tab9" onclick="invoiceTabInit.get('tab9');"><em>Notes & Attachment</em> </a>
					</li>
				</ul>
				<div class="yui-content">
					<sec:authorize ifAllGranted="FUNCTION_1110">
						<div id="__tab8">
							<div class="demo" id="__accordion_proposal"></div>
						</div>
					</sec:authorize>
					
					<sec:authorize ifAllGranted="FUNCTION_1120">
					<div id="__tab3" style="height:100%;">
						
					  <div id="slideshow_dispute">
					    <div id="slidesContainer_dispute">
					    <table border="0"><tr><td>
					      <div class="slide_dispute" style="float: left;" id="leftDivOfDisputeClim">
							<div style=" width: 100%;">
								<table width="100%" height="100%" border="0">
									<tr>
										<td>
												Total Dispute Amount:
												<input type="text" name="disputeTotalAmount" id="disputeTotalAmount" readonly="readonly" class="border_text">
												<h4>
													<br />
													Create a new dispute item:
												</h4>
												<div class="table_margin">
												<div class="tabForm">
													<form onsubmit="return (checkProposalAmountIsNotNull()&& uploadFileValidate('invoiceDetail_AttachFile')&& validateTheNotes()&& validateTheSCOA()&& validateIsTrue());"
														action="doUploadForDisputeTabLeftSave.action"
														enctype="multipart/form-data" method="POST"
														id="formSaveProposal"
														target="uploadFrame_0">
														<table border="0" width="100%" height="190px" cellpadding="0"
															cellspacing="0">
															<tr height="25px">
																<td width="15%">
																	Amount
																</td>
																<td width="40%">
																	<s:textfield name="disputeAmount" onblur="checkTheDisputeAmount(this)" id="proposalAmount" cssStyle="width:180px;"></s:textfield>
																</td>
																<td width="15%">
																	Circuit Number
																</td>
																<td width="30%">
																	<s:textfield name="cricuitNumber" onchange="checkTheCricuitNumber(this);" onkeypress="checkTheCricuitNumber(this);" id="cricuitNumber"></s:textfield>
																</td>
															</tr>
															<tr height="25px">
																<td>
																	Category
																</td>
																<td>
																	<span class="select1"><s:select
																			id="dispute_reason_proposal" list="disputeReasonList"
																			name="categoryId" listKey="key" listValue="value"
																			cssStyle="width:180px" /> </span>
																</td>
																<td >
																	Service Type
																</td>
																<td>
																	<s:textfield name="serviceType" onchange="checkTheServiceType(this);" onkeypress="checkTheServiceType(this);" id="serviceType"></s:textfield>
																</td>
															</tr>
															<tr height="25px">
																<td>
																	Originator
																	<s:hidden name="invoice.id" />
																</td>
																<td>
																	<span class="select1"><s:select id="originator"
																		name="originatorId" list="originatorList" listKey="key"
																			listValue="value" cssStyle="width:180px" /> </span>
		
																</td>
																<td  rowspan="2">
																	Notes&nbsp;
																</td>
																<td  rowspan="4" valign="top" align="left">
																	<textarea id="proposalNote" onkeyup="(this.value.length>255)?alert('Notes may not have more than 255 chars!'):'';" name="proposalDiscription"
																	style='width:250px;overflow:auto ;height:75px;'></textarea>
																</td>
															</tr>
															<tr height="25px">
																<td>
																	SCOA
																</td>
																<td colspan="2">
																	<div id="VL_vendorList_account_code"></div>
																</td>		
															</tr>
															<tr height="25px">
															    <td>
																	Attach File
															    </td>
																<td>
																<div id = "invoiceDetail_AttachFile" style="VERTICAL-ALIGN:top;position:relative;">
																<div id ="__up_load_text_invoiceDetail_AttachFile_Number1" style="PADDING-TOP: 3px;height: 19px;">
																<div style="float:left;">
																	<input style="height:19px;width:175px;" type='text' id="__up_load_text_invoiceDetail_AttachFile1" disabled="disabled">
	                                                            </div>
	                                                            <div style="float:left; margin:0 0 0 2px">
			        													<span class="ccm_upload_img">
				        													<input id="__up_load_text_input_AttachFile1" onchange="document.getElementById('__up_load_text_invoiceDetail_AttachFile1').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
				        													<input style="height:19px;width:60px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
			        													</span>
			        											</div>
		        						                        <div class="approveicon" onclick="deletefile('__up_load_text_invoiceDetail_AttachFile_Number1');"></div>
		        						                        <div class="clear" ></div>
		        				                                </div>
		        				                                <div id ="__up_load_text_invoiceDetail_AttachFile_Number2" style="PADDING-TOP: 3px;height: 19px;">
																<div style="float:left;">
																	<input style="height:19px;width:175px;" type='text' id="__up_load_text_invoiceDetail_AttachFile2" disabled="disabled">
	                                                            </div>
	                                                            <div style="float:left; margin:0 0 0 2px">
			        													<span class="ccm_upload_img">
				        													<input id="__up_load_text_input_AttachFile2" onchange="document.getElementById('__up_load_text_invoiceDetail_AttachFile2').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
				        													<input style="height:19px;width:60px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
			        													</span>
			        											</div>
		        						                        <div class="approveicon" onclick="deletefile('__up_load_text_invoiceDetail_AttachFile_Number2');"></div>
		        						                        <div class="clear" ></div>
		        				                                </div>
		        				                                <div id ="__up_load_text_invoiceDetail_AttachFile_Number3" style="PADDING-TOP: 3px;height: 19px;">
																<div style="float:left;">
																	<input style="height:19px;width:175px;" type='text' id="__up_load_text_invoiceDetail_AttachFile3" disabled="disabled">
	                                                            </div>
	                                                            <div style="float:left; margin:0 0 0 2px">
			        													<span class="ccm_upload_img">
				        													<input id="__up_load_text_input_AttachFile3" onchange="document.getElementById('__up_load_text_invoiceDetail_AttachFile3').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
				        													<input style="height:19px;width:60px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
			        													</span>
			        											</div>
		        						                        <div class="approveicon" onclick="deletefile('__up_load_text_invoiceDetail_AttachFile_Number3');"></div>
		        						                        <div class="clear" ></div>
		        				                                </div>
		        				                                </div>
																</td>
															</tr>
                                                           
															<tr>
																<td>
																</td>
																
																<td colspan="3" valign="bottom" align="left" style="PADDING-TOP: 3px;">
																<input type="button" name="button2"
																		id="btnUploadForDisputeTabLeft"
																		onclick="addnewdisputeFile();" value="Add" />&nbsp;&nbsp;
																	<sec:authorize ifAllGranted="FUNCTION_1121">
																	<input type="submit" name="button2"
																		id="btnUploadForDisputeTabLeft" onclick="" value="Save" /></sec:authorize>&nbsp;&nbsp;<div id="scoaInfo"></div>
																	<input type="button" name="button2"
																		id="btnUploadForDisputeTabLeft"
																		onclick="uploadClearAllValue();emptyTheTextForSaveProposal();clearnewdisputeFile();" value="Cancel" />
																</td>
															</tr>
														</table>
													</form>
												</div></div>
											</td>
										</tr>
										<tr>
										<td>
										<tr>
											<td>
												<br />
												<h4>
													Ungrouped dispute items:
												</h4>
												<div class="table_margin">
												<div class="tabForm" style="height:100%;">
													<table style="table-layout:fixed;width:100%;">
														<tr>
															<td>
																<div align="left"  style="width:100%;overflow-x:auto;padding-bottom: 20px;">
																	<div id="_disputeTable"></div>
																</div>
																<s:hidden name="invoice.id" id="disputeTabinvoiceId" />
															</td>
														</tr>
													</table>
												</div>
												</div>
												<input class="table_margin" type="button" value="Download to Excel"
													onclick="saveLeftProposalListExcelByDisputeTab();" />
												<!-- add by yinghe.fu -->
												<input class="table_margin" type="button" onclick="deleteProposalDataLeft();"
													value="Delete Selected Items"/>
											</td>
										</tr>
									</table>
									<table width="100%">
										<tr>
											<td>
												<input type="radio" class="noBorderRadioButton" onclick="getProposalDataLeftDL();"
													class="noBorderRadioButton" name="invoiceDisputeTabLeftRadio" value="WITHINPAST">
												Move selected to an existing group
											</td>
											<td align="left">
												
													<div id="disputeSelectDiv"></div> 
											</td>
										</tr>
										<tr>
											<td>
												<input type="radio" class="noBorderRadioButton" 
													name="invoiceDisputeTabLeftRadio"  value="WITHINPAST"
													onclick="getDisputeListByInvoiceId_Reset();"/>
												Create one new group for all selected
												<br />
											</td>
										</tr>
										<tr>
											<td>
												<input type="radio" class="noBorderRadioButton"
													name="invoiceDisputeTabLeftRadio" value="WITHINPAST"
													onclick="getDisputeListByInvoiceId_Reset();">
												Create new group for each selected
											</td>
											<td>
												<sec:authorize ifAllGranted="FUNCTION_1121">
												<input type="submit" id="updateProposalLeft" onclick="getProposalDataLeft();"
													value=" Save " /> 
												</sec:authorize>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												
											</td>
										</tr>
									</table>
								</div>
						      </div>
						      </td>
						      <td>
						       <div class="slide_dispute" style="float:left;">
						      	<div style="width:100%;height:100%">
								<div id="_disputesDataTable" style="width:100%;"></div>
								<table border=0 width=100% id="_paginationDisputesTable"
									style="display: none;">
									<tr>
										<td align="left" style="padding-right: 15px;">
											<img src="include/images/button_previous_first.gif"
												title="First page" onclick="getFirstPage();">
											<img src="include/images/button_previous.gif"
												title="Previous page" onclick="getPrevPage();">
											Page&nbsp;
											<input id="__curPageNo" type="text" style="width: 30px;"
												value="1" onkeydown="getPage();">
											&nbsp;of&nbsp;
											<span id="__totalPageNo"></span>
											<img src="include/images/button_next.gif" title="Next page"
												onclick="getNextPage();">
											<img src="include/images/button_next_last.gif"
												title="Last page" onclick="getLastPage();">
											&nbsp;&nbsp;
											<span class="select1"><select id="_recPerPageSelect"
													onchange="recPerPage=this.value;searchDisputesByInvoiceIdInDisputeTab();">
													<option value="5">
														5
													</option>
													<option value="10">
														10
													</option>
													<option value="15">
														15
													</option>
													<option value="20">
														20
													</option>
													<option value="25">
														25
													</option>
													<option value="30">
														30
													</option>
												</select> </span>
										</td>
									</tr>
								</table>
								</div>
						      </div>
						      </td></tr></table>
						    </div> 
						  </div> 
						</div>
						</sec:authorize>
						
						<sec:authorize ifAllGranted="FUNCTION_1130">
						<div id="__tab6">
							<div id="disputeActionRequestListPage_F">
								<h3>
									Dispute Action Request
								</h3>
								<table style="table-layout:fixed;width:100%;">
									<tr>
										<td>
											<div align="left"  style="width:100%;overflow-x:auto;padding-bottom: 20px;">
												<div id="disputeActionRequestListPageDiv"></div>
											</div>
										</td>
									</tr>
								</table>
								<table style="width:100%;">
									<sec:authorize ifAllGranted="FUNCTION_2110">
									<tr>
										<td>
											<div class="demo" id="__dispute_action_request_page_list"></div>
										</td>
									<tr>												
									</sec:authorize>
								</table>
							</div>
							<div>
								<h3>
									Outstanding Credit
								</h3>
								<table style="table-layout:fixed;width:100%;">
									<tr>
										<td colspan="2">
											<div align="left"  style="width:100%;overflow-x:auto;padding-bottom: 20px;">
												<div id="outstandingCreditPageDiv"></div>
											</div>
										</td>
									</tr>
								</table>
								<table>
									<tr>
										<td width="260px">
											<span id="outstandingCreditPagePaginationDiv"></span>
										</td>
										<td>
											<input id="saveOutstandingCreditPageExcelButton" type="button" value="Download to Excel" onclick="saveOutstandingCreditPageExcel();"/>
										</td>
									</tr> 
								</table>
								
								<table id="outstandingCredit_A_button">
									<tr>
										<td>
											<table>
												<tr>
													<td style="padding-left: 5px;">
														<input id='addOutstandingCreditSCOAAllButton' type="button" value="SCOA Coding" onclick="addOutstandingCreditSCOAAll();"/>
													</td>
													<td style="padding-left: 5px;">
														<input type="button" value="Apply to Payment" onclick="applyCreditToPayment();"/>
													</td>
<%--													<td style="padding-left: 5px;">--%>
<%--														<input type="button" value="Record as Dispute Win and Apply to Payment" onclick="recordDisputeAndApplyToPayment();"/>--%>
<%--													</td>--%>
													<%-- <td style="padding-left: 5px;">     wenbo.zhang 20180814 - CreditAndDispute. 
														<input type="button" value="Split Accept" onclick="outstandingCreditSplitApply();"/>
													</td> --%>
												 	<td style="padding-left: 5px;">
														<%-- wenbo.zhang 20180814 - CreditAndDispute. 	<input type="button" value="Reconcile with Dispute" onclick="outstandingReconcileWithDispute();"/>--%>
													</td>  
												</tr>
											</table>
										</td>
									</tr> 
								</table>
								
								<div id="outstandingCredit_B">
									<h3>
										Outstanding Dispute
									</h3>
									<table style="table-layout:fixed;width:100%;">
										<tr>
											<td>
												<div align="left"  style="width:100%;overflow-x:auto;padding-bottom: 20px;">
													<div id="notReconcileDisputePageDiv"></div>
												</div>
											</td>
										</tr>
									</table>
									<table>
										<tr>
											<td width="260px">
												<span id="notReconcileDisputePagePaginationDiv"></span>
											</td>
											<td>
												<input id="saveNotReconcileDisputePageExcelButton" type="button" value="Download to Excel" onclick="saveNotReconcileDisputePageExcel();"/>&nbsp;
												<input id="addOutstandingDisputeSCOAButton" type="button" value="SCOA Coding" onclick="addOutstandingDisputeSCOAAll();"/>
											</td>
											<td id="dispute-handle-div">
												<input type="button" value="Close as Dispute Lose" onclick="closeAsDisputeLose();"/>&nbsp;
												<input id="dispute-handle-div-refile-dispute" type="button" value="Refile Dispute" onclick="refileTheDispute();"/>&nbsp;
												<input type="button" value="Split and Close" onclick="splitAndCloseDispute();"/>&nbsp;
											</td>
											<td style="padding-left: 5px;">
												<input id="reconcileByCurrentCreditButton" type="button" value="Reconcile" onclick="reconcileByCurrentCredit();"/>
											</td>
											<td style="padding-left: 5px;">
									<!--	wenbo.zhang 20180814 - CreditAndDispute. 		<input id="outstandingReconcileWithDisputeCancelButton" type="button" value="Cancel" onclick="outstandingReconcileWithDispute.cancel();"/>     -->
											</td>
										</tr> 
									</table>
								</div>
								<table>
									<tr>
										<td style="padding-left: 5px;">
											<b><font id="_tab6_msg_div" color="green"></font><br/></b>
											<b><font id="_tab6_msg_error_div" color="red"></font></b>
										</td>
									</tr> 
								</table>
							 <!--	<div id="outstandingCredit_C">
									<h3>
										Reconciliation
									</h3>
									<table style="table-layout:fixed;width:100%;">
										<tr>
											<td>
												<div align="left"  style="width:100%;overflow-x:auto;padding-bottom: 20px;">
													<div id="theInvoiceReconciliationPageDiv"></div>
												</div>
											</td>
										</tr>
									</table>
									<table>
										<tr>
											<td width="260px">
												<span id="theInvoiceReconciliationPagePaginationDiv"></span>
											</td>
											<td>
												<input id="saveTheInvoiceReconciliationPageExcelButton" type="button" value="Download to Excel" onclick="saveTheInvoiceReconciliationPageExcel();"/>
												<input id="deleteReconciliationButton" type="button" value=" Delete " onclick="deleteReconciliation();"/>
											</td>
										</tr> 
									</table>
								</div>    -->
								
								
								<div id="outstandingCredit_F">
									<h3>
										Close as Dispute Win
									</h3>
									<table style="table-layout:fixed;width:100%;">
										<tr>
											<td>
												<div align="left"  style="width:100%;overflow-x:auto;padding-bottom: 20px;">
													<div id="closeAsDisputeWinPageDiv"></div>
												</div>
											</td>
										</tr>
									</table>
									
									<table>
										<tr>
											<td width="260px">
												<span id="closeAsDisputeWinPagePaginationDiv"></span>
											</td>
											<td>
												<input id="saveCloseAsDisputeWinPageExcelButton" type="button" value="Download to Excel" onclick="saveCloseAsDisputeWinPageExcel();"/>
												<input id="deleteCloseAsDisputeWinButton" type="button" value=" Delete " onclick="deleteCloseAsDisputeWin();"/>
											</td>
										</tr> 
									</table>
								</div>
								
								<div id="outstandingCredit_E">
									<h3>
										Close as Dispute Lost
									</h3>
									<table style="table-layout:fixed;width:100%;">
										<tr>
											<td>
												<div align="left"  style="width:100%;overflow-x:auto;padding-bottom: 20px;">
													<div id="closeAsDisputeLosePageDiv"></div>
												</div>
											</td>
										</tr>
									</table>
									
									<table>
										<tr>
											<td width="260px">
												<span id="closeAsDisputeLosePagePaginationDiv"></span>
											</td>
											<td>
												<input id="saveCloseAsDisputeLosePageExcelButton" type="button" value="Download to Excel" onclick="saveCloseAsDisputeLosePageExcel();"/>
												<input id="deleteCloseAsDisputeLoseButton" type="button" value=" Delete " onclick="deleteCloseAsDisputeLose();"/>
											</td>
										</tr> 
									</table>
								</div>
							</div>
							<br>
						</div>
						</sec:authorize>
						<sec:authorize ifAllGranted="FUNCTION_1140">
						<div id="__tab7">
							<s:hidden name="invoice.ban.id" id="mics" />
							<s:hidden name="invoice.id" id="payVendor" />
							<br />
							<h3>
								Adjustment
							</h3>
							
							<table style="table-layout: fixed; width: 100%;">
								<tr>
									<td>
										<div align="left" style="width: 100%; overflow-x: auto; padding-bottom: 20px;">
											<div id="paymentList"></div>
										</div>
									</td>
								</tr>
							</table>
							<table border=0>
									<tr>
										<td width="240px" id="paymentList_pageTable" align="left"></td>
										<td width="150px"  align="left">
											<input id="__download_excel_misc_payment" type="button" value="Download to Excel" onclick="downloadExcelMiscPayment();"/> 
										</td>
										<td width="120px" >
										<sec:authorize ifAllGranted="FUNCTION_1141">
										<input type="button" value="Create Adjustment" onclick="addPaymentShow();" align="left" />											
										</sec:authorize> 
										</td>
										<td width="120px" align="center">
											<input type="button" value="Move to Credit" onclick="miscMoveToCredit();" align="left" />
										</td>
										<td><div style="color:red;" id="divMiscId"></div></td>
									</tr>
							</table>
							<div align="right">
							</div>
							<br />
							<h3>
								Dispute Payback
							</h3>
							<table style="table-layout: fixed; width: 100%;">
								<tr>
									<td>
										<div align="left" style="width: 100%; overflow-x: auto; padding-bottom: 20px;">
											<div id="msicList"></div>
										</div>
									</td>
								</tr>
							</table>
							<table border=0>
									<tr>
										<td width="240px" id="MiscList_pageTable" align="left"></td>
										<td width="150px"  align="left">
											<input id="__save_excel_dispute_payback_payments_misc" type="button" value="Download to Excel" onclick="saveExcelByDisputePaybackPaymentsMisc();"/> 
										</td>
									</tr>
							</table>
						</div>
						</sec:authorize>
						<sec:authorize ifAllGranted="FUNCTION_1150">
						<div id="__tab2">
							<br />
							<div id="accordion_at_tab2_div">
								<h5 onclick="setTimeout('window.scrollTo(0,480);',600);">
									<a>Payment Summary&nbsp;&nbsp;&nbsp;&nbsp;<span style="padding-top:4px; font-size:11px;" id="total_payment_amount_div_w"></span>&nbsp;&nbsp;<span style="padding-top:4px; font-size:11px;" id="su_miss_scoa_div"></span> </a>
								</h5>
								<div>
									<div>
										<h4> Total Amount&nbsp;&nbsp;&nbsp;&nbsp;<span style="padding-top:4px;font-size:11px;" id="total_payment_amount_div_1"></span></h4>
									</div>
									<div class="table_margin" id="page_payment_scoa_amount_div"></div>
									<div> 
										<div class="table_page" id="page_payment_scoa_amount_pagination_div"></div>
										<div class="table_page_load">
											<input type="button" value="Download to Excel"
														id="saveExcelByAllPaymentsButton"
														onclick="saveExcelByAllPayments();" />
										</div>
									</div>
									<br />
									<div>
										<h4> Payment from Current Invoice&nbsp;&nbsp;&nbsp;&nbsp;<span style="padding-top:4px;font-size:11px;" id="current_invoice_payments_div_w"></span></h4>
									</div>
									<div class="table_margin" id="current_invoice_payment_div"></div>
									<div>
										<div class="table_page" id="current_invoice_payment_pagination_div"></div>
										<div class="table_page_load">
											<input type="button" value="Download to Excel"
													id="downloadExcelPCIPButton" onclick="downloadExcelPCIP();" />
										</div>
									</div>
									<br />
									<div>
										<h4> Paid-dispute from Current Invoice&nbsp;&nbsp;&nbsp;&nbsp;<span style="padding-top:4px; font-size:11px;" id="total_isPaidDispute_amount_div_w"></span></h4>
									</div>
									<div class="table_margin" id="paid_dispute_payment_div"></div>
									<div>
										<div class="table_page" id="paid_dispute_payment_pagination_div"></div>
										<div class="table_page_load">
											<input type="button" value="Download to Excel"
													id="downloadExcelByPaidDisputePaymentButton" onclick="downloadExcelByPaidDisputePayment();" />
										</div>
									</div>
									<br />
									<div>
										<h4> Manually Created Short-paid Dispute&nbsp;&nbsp;&nbsp;&nbsp;<span style="padding-top:4px; font-size:11px;" id="total_isShortPaidDispute_amount_div_w"></span></h4>
									</div>
									<div class="table_margin" id="short_paid_dispute_payment_div"></div>
									<div>
										<div class="table_page" id="short_paid_dispute_payment_pagination_div"></div>
										<div class="table_page_load">
												<input type="button" value="Download to Excel"
													id="downloadExcelByShortPaidDisputePaymentButton" onclick="downloadExcelByShortPaidDisputePayment();" />
										</div>
									</div>
									<br />
									<div>
										<h4> Manually Created Adjustment&nbsp;&nbsp;&nbsp;&nbsp;<span style="padding-top:4px;font-size:11px;" id="manually_created_payments_div_w"></span></h4>
									</div>
									<div class="table_margin" id="manually_created_payment_div"></div>
									<div>
										<div class="table_page" id="manually_created_payment_pagination_div"></div>
										<div class="table_page_load">
												<input type="button" value="Download to Excel"
													id="downloadExcelPMCPButton" onclick="downloadExcelPMCP();" />
										</div>
									</div>
									<br />
									<div>
										<h4> Dispute Payback&nbsp;&nbsp;&nbsp;&nbsp;<span style="padding-top:4px;font-size:11px;" id="dispute_payback_payments_div_w"></span></h4>
									</div>
									<div class="table_margin" id="dispute_payback_payment_div"></div>
									<div>
										<div class="table_page" id="dispute_payback_payment_pagination_div"></div>
										<div class="table_page_load">
											<input type="button" value="Download to Excel"
													id="saveExcelByDisputePaybackPaymentsButton" onclick="saveExcelByDisputePaybackPayments();" />
										</div>
									</div>
									<br />
									<div>
										<h4> Applied Credit&nbsp;&nbsp;&nbsp;&nbsp;<span style="padding-top:4px;font-size:11px;" id="applying_credit_payments_div_w"></span></h4>
									</div>
									<div class="table_margin" id="applying_credit_payment_div"></div>
									<div>
										<div class="table_page" id="applying_credit_payment_pagination_div"></div>
										<div class="table_page_load">
												<input type="button"
													id="saveExcelByApplyingCreditPaymentsButton"
													onclick="saveExcelByApplyingCreditPayments();"
													value="Download to Excel" />
										</div>
									</div>
	
								</div>
								<h5 onclick="setTimeout('window.scrollTo(0,480);',600);">
									<a>Dispute Summary&nbsp;&nbsp;&nbsp;&nbsp;<span style="padding-top:4px; font-size:11px;" id="total_dispute_amount_div_w"></span></a>
								</h5>
								<div>
									<div  style="margin-left:3px;">
										<span style="padding-top:4px; font-size:11px;" id="total_paid_dispute_amount_div_w"></span>
									</div><br/>
	
									<h4>
										Current Disputes
									</h4>
									<div>
										<table style="width: 100%;table-layout:fixed;">
											<tr>
												<td>
													<div align="left"  style="width:100%;overflow-x:auto;padding-bottom: 20px;">
														<div class="table_margin" id="current_dispute_page_div"></div>
													</div>
												</td>
											</tr>
										</table>
										<div>
											<div class="table_page" id="current_dispute_page_pagination_div"></div>
											<div class="table_page_load">
												<input type="button" value="download to excel"
													id="saveExcelByCurrentDisputeButton"
													onclick="saveExcelByCurrentDispute();" />
											</div>
										</div>
									</div>
								</div>
								<h5 onclick="setTimeout('window.scrollTo(0,480);',600);">
									<a>Last 3 months' Invoice History </a>
								</h5>
								<div>
									<div id="past_three_payment_page_div"></div>
								</div>
								<h5 onclick="setTimeout('window.scrollTo(0,480);',600);">
									<a>Invoice Action History</a>
								</h5>
								<div>
									<br />
									<h4>
										Invoice Action History
									</h4>
									<div class="table_margin" id="invoice_history_page_div_dj1"></div>
									<div class="table_margin"><input type="button" id="saveExcelByActionHistoryButton"
													onclick="saveExcelByActionHistory();" value="Download to Excel" />
									</div>
								</div>
							</div>
							<sec:authorize ifAllGranted="FUNCTION_1151">
								<div style="padding-top:10px;padding-bottom:10px;display: none;" id="handle_invoice_div_1">
									<input type="button" value="Approve Action" onclick="handleInvoiceAHR();clearfiles();" />
									<div id="approveResultMessage" style="display:none;"><b><font color="green">Approved successfully!</font></b></div>
								</div>
							</sec:authorize>
						</div>
						</sec:authorize>
						<div id="__tab4">
							<br />
							<s:hidden name="invoice.id" id="Originalidid" />
							<div id="_originalTable"></div>
						</div>
						
						<div id="__tab5">
							<br />
							<s:hidden name="invoice.ban.id" id="Tifflinkidid" />
							<h3>Tariff & Contract</h3>	
                            <!-- 添加标签来固定表格的宽度。 -->
							<table style="table-layout: fixed; width: 100%;">
                                <tr>
                                    <td>
                                        <div id="_tiffTable" style="width: 100%; overflow-x: auto;"></div>
                                    </td>
                                </tr>
                                
                            </table>
						</div>
						<div id="__tab9">
							<br />
						<div class="tabDetailViewDL">
		                  	<table border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed; width: 100%;">
								<tr>
									<td colspan="4">
										<div align="left" style="width:100%; overflow-x: auto;overflow-y: hidden;padding-bottom: 20px;">
											<div id="_dataTable2"></div>
										</div>
									</td>
								</tr>
								<tr>
									
								    <td colspan="1">
								    	<input type="button" width="135" value="Add Attachment" align="right" onclick="serachInvoiceNote(false);"></input>
								    	<!--
										<div style="padding: 2px,0px,0px,2px;width:240px;">
											<input type="button" value="Add Notes" onclick="serachInvoiceNote();"/>
										</div>
									-->&nbsp;</td>
									<td colspan="1">&nbsp;
									</td>
									<td colspan="1">&nbsp;
									</td>
									<td colspan="1" >
										<div style="width:240px;" id="_dataTable_page2" ></div>
									</td>
									

								</tr>
							</table>
	                  	</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="EditProposalLeft" id="EditProposalLeftId">
			<div class="hd">
				<h2>Edit Dispute Item</h2>
			</div>
			<div class="bd">
				<table border="0" width="97%" align="center" height="50px">
					<tr>
						<td width="12%">
							Amount
						</td>
						<td width="40%">
							<s:textfield name="disputeAmount"
								onchange="checkTheDisputeAmount(this);" onkeypress='return event.keyCode>=48&&event.keyCode<=57||event.keyCode==46' id="eProposalAmount"></s:textfield>
						</td>
						<td width="15%">
							Circuit Number
						</td>
						<td width="33%">
							<s:textfield name="cricuitNumber" onchange="" id="eCricuitNumber"></s:textfield>
						</td>
					</tr>
					<tr>
						<td>
							Category
						</td>
						<td>
							<span class="select1">
								<s:select id="edispute_reason_proposal" list="disputeReasonList"
									name="categoryId" listKey="key" listValue="value" cssStyle="width:180px" /> 
							</span>
						</td>
						<td>
							Service Type
						</td>
						<td>
							<s:textfield name="serviceType" onchange="" id="eServiceType"></s:textfield>
						</td>
					</tr>
					<tr>
						<td>
							Originator
							<s:hidden name="invoice.id" />
						</td>
						<td>
							<span class="select1"><s:select id="eOriginator"
									name="originatorId" list="originatorList" listKey="key"
									listValue="value" cssStyle="width:180px" /> </span>
						</td>
						<td valign="top" rowspan="3">
							Notes&nbsp;
						</td>
						<td rowspan="4" valign="top" align="left">
							<textarea id="eProposalNote" name="proposalDiscription"
								style='width: 220px; overflow: auto; height: 55px;'></textarea>
						</td>
					</tr>
					<tr>
						<td>
							SCOA
						</td>
						<td colspan="2">
						<div id="VL_vendorList_eaccount_code"></div>
						</td>
					</tr>
					<tr>
						<td colspan="4">
							<sec:authorize ifAllGranted="FUNCTION_1121">
							<input type="button" name="updateProposal" value="Save" onclick="saveEditRecodeFromProposalListLeft();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" name="cancel" value="Cancel" onclick=" YAHOO.ccm.container.EditProposalLeftId.hide();">
							</sec:authorize>
						</td>
					</tr>
				</table>
				<div style="padding-left:10px;padding-top:10px;width:98%;">
					<span>Attachment File List</span>
					
					<div id="_attachmentFileOfProposalTable"></div>
					<table>
						<tr>
							<td>
								<div id="_attachmentFileOfProposalTable_pageTable"></div>
							</td>
							<td style="text-align: center;">
								<div > 
									<form id="uploadForProposalOfEditProposalActionForm" onsubmit="alert(uploadFileValidate('uploadForProposalOfEditProposalActionForm'));return uploadFileValidate('uploadForProposalOfEditProposalActionForm');"
										action="uploadForProposalOfEditProposal.action" style="position:relative;"
										enctype="multipart/form-data" method="POST"
										target="uploadFrame_0">
										<table>
											<tr>
												<td style="padding-top:8px;">
														Attach File:   
														<input style="height:19px;width:160px;" type='text' id="__up_load_text_invoiceDetail_4" disabled="disabled">
														<s:hidden name="proposalId" id="eproposalId" />
												</td>
												<td style="padding-top:8px;">
													<span class="ccm_upload_img" style="float:right;margin-top:0;">
							        					<input id="__up_load_text_input_4" onchange="document.getElementById('__up_load_text_invoiceDetail_4').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
							        					<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
						        					</span>
												</td>
												<td style="padding-top:8px;">
													<sec:authorize ifAllGranted="FUNCTION_1121">
													<input type="button" style="margin-left:3px;margin-top:0;" value="Upload" onclick="if(uploadFileValidate('uploadForProposalOfEditProposalActionForm'))document.getElementById('uploadForProposalOfEditProposalActionForm').submit();"/>
													</sec:authorize>
												</td>
											</tr>
										</table>
									</form>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		
		<div class="edit-SCOA-coding-window" id="editSCOACodingWindow" >
			<div class="hd">
				<h2>SCOA Coding</h2>
			</div>
			<div class="bd">
				<table height="100%" width="95%" border=0 align="center" >
					<tr>
						<td>
							SCOA:
						</td>
						<td>
						<div id="VL_vendorList__scoa_codeing"></div>
						</td>
					</tr>
				</table>
				<s:form id="form_coding" action="" theme="simple">
					<table height="100%" border="0" width="95%" align="center">
						<tr>
							<td valign="top" >
								Notes:
							</td>
							<td>
								<s:textarea  id="__notes_SCOA_coding" cssStyle="padding:0px 1px;overflow:auto ;width:260px;height:60px;" cssClass="validate-text"></s:textarea>
							</td>
						</tr>
					</table>
				</s:form>
				<table height="100%" border="0" width="95%" align="center">
					<tr>
						<td  style="color: red;"> 
							<div id="_show_scoa_coding_prompt"></div>
						</td>
					</tr>
				</table>
			</div>
		</div>
		
		<div id="editSCOACodingSingleWindow" >
			<div class="hd">
				<h2>Cost to Multiple SCOA</h2>
			</div>
			<div class="bd" style="margin-left:10px;">
				<div id="custom_moti_scoa_1"></div>
			</div>
			<div class="bd" style="margin-left:12px;">
				<div><b>Notes:</b><br />
					<s:textarea  id="__notes_SCOA_coding_single" cssStyle="padding:0px 1px;overflow:auto ;width:465px;height:50px;" cssClass="validate-text"></s:textarea>
				</div>

				<div style="color: red;"> 
					<div id="__show_scoa_coding_single_prompt"></div>
					<div id="__show_scoa_coding_single_prompt_amount"></div>
				</div>
			</div>
		</div>
		
		<div class="edit-proposal-window" id="editProposalWindow" >
			<div class="hd">
				<h2>Edit Dispute</h2>
			</div>
			<div class="bd">
				<table id="__edit_proposal"  width="100%"  border="0" align="center" style="display: block;">
					<tr>
						<td width="100px">Payment Amount:</td>
						<td id="__payment_amount" align="left"></td>
					</tr>
					<tr>
						<td>
							Dispute Amount:
						</td>
						<td style="padding:0 0 0 8px;">
							<s:textfield id="__dispute_amount" cssStyle="width:255px;" cssClass="validate-number" onblur="editProposalAmount(this.value);"></s:textfield>
						</td>
					</tr>
					<tr>
						<td>
							SCOA:
						</td>
						<td align="left" style="position:relative; padding:0 0 0 8px;">
						<div id="VL_vendorList__scoa_code"></div>
						</td>
					</tr>
				</table>
				<table id="__group_dispute" border=0 height=100% width="100%" align="center" style="display: none;">
					<tr height="15">
						<td width=100px>Total Amount:</td>
						<td id="__total_amount" align="left" style="color: #000;"></td>
					</tr >
					<!-- <tr>
						<td >
							<input type="radio" id="__payment_radio" name="paymentAndDisputeSelect" class="noBorderRadioButton" value="DATEFRAME">
						</td>
						<td >
							Payment
						</td>
					</tr> -->
					<tr>
						<td>
							<input type="radio" id="__dispute_radio" name="paymentAndDisputeSelect" class="noBorderRadioButton" value="WITHINPAST">
						</td>
						<td nowrap="nowrap">
							Dispute
						</td>
					</tr>
				</table>
				<table height="100%" border="0" width="100%" align="center">
					<tr>
						<td>
							Dispute Category:
						</td>
						<td>
							<span class="select1"><s:select id="__dispute_category" list="disputeReasonList" 
								listKey="key" listValue="value" cssStyle="width:255px"/></span>
						</td>
					</tr>
					<tr>
						<td>
							Originator:
						</td>
						<td>
							<span class="select1"><s:select id="__originator" list="originatorList"
								listKey="key" listValue="value" cssStyle="width:255px"/></span>
						</td>
					</tr>
					<tr>
						<td style="float:left;margin-top:5px;">
							Attach File:
						</td>
						<td>
							<!-- <form onsubmit="return uploadValidate();" id="disputeUploadForm" -->
							<form id="disputeUploadForm"
								action="doEidtInvoiceProposalSave.action" enctype="multipart/form-data"
								method="POST" target="uploadFrame_0">
								<div id = "addDisputeFile" style="VERTICAL-ALIGN:top;overflow-y:auto;max-height:100px;position:relative; margin-top: 5px;">
								
		        				</div>
									<input id="__txt_proposalId" name="svo.proposalId" type="hidden" />
									<input id="__attachment_point_id" name="svo.attachmentPointId" type="hidden" />
									<input id="__txt_proposalId_boxId" name="svo.boxInId" type="hidden" />
									<span style="width:68px;"></span>
									<br/>
<!--									<input style="height:19px;" type="button" value="Add" onclick="AddInvoiceDisputeFile();" />-->
<!--									<input id="disputeUploadFileBtn" style="height:19px;" type="hidden" value="Upload" onclick="this.parentNode.submit();" />-->
							</form>
						</td>
					</tr>
					<tr>
						<td ></td>
						<td ><span class="button-group"  style="float: right;padding-left: 210px; padding-bottom:  10px;">
						<span id="yui-gen6" class="yui-button yui-push-button"><span class="first-child">
						<button type="button" id="yui-gen6-button" onclick = "AddInvoiceDisputeFile();">Add</button></td>
					</tr>
					<tr>
						<td valign="top" >
							Notes:
						</td>
						<td>
							<s:textarea  id="__notes" cssStyle="padding:0px 1px;overflow:auto ;width:100%;height:60px;" cssClass="validate-text"></s:textarea>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<iframe name="uploadFrame_0" id="uploadFrame_0" style="display: none" src="javascript:false"></iframe>
		
<sec:authorize ifAllGranted="FUNCTION_1140">
		<div id="eidtPaymentMiscPanelDiv">
			<div class="hd">
				<h2>Edit Adjustment</h2>
			</div>
			<div class="bd">
				<form onsubmit="return uploadValidate('EditAdjustmentFormId');" id= "EditAdjustmentFormId"
					action="doEditMiscProposalPayment.action" style="overflow-y:auto;max-height:200px;position:relative;"
					enctype="multipart/form-data" method="POST" target="uploadFrame_0">
					<table width="95%" align="center" style="color: #000;">
						<tr>
							<td align="right" style="color: #000;">
								SCOA:
								<input type="text" name="svo.parentItemId" id="editPaymentDivPopalse" style="display: none;">
							</td>
							<td colspan="2">
							<div id="VL_vendorList_editAccountCodeId"></div>
							</td>
						</tr>
						<tr>
							<td></td>
							<td colspan="2">
								Must Choose One SCOA!
							</td>
						</tr>
						<tr>
							<td align="right" nowrap="nowrap" style="color: #000;">
								Adjustment Amount:
							</td>
							<td colspan="2">
								<input id="editPaymentmAmount" name="svo.paymentAmount"
									type="text" t_value="" size="45" o_value=""
									onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
									onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
									onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" />
							</td>
						</tr>
						<tr>
							<td align="right" valign="top" style="color: #000;">
								Notes:
							</td>
							<td colspan="2">
								<textarea id="edittxtRDescription" name="svo.sortingDirection" rows="3" cols="46"></textarea>
							</td>
						</tr>
						<tr>
							<td align="right" nowrap="nowrap" style="color: #000;padding-top:8px;">
								Attach File:
							</td>
							<td style="padding-top:8px;">
								<input style="height:19px;width:160px;" type='text' id="__up_load_text_invoiceDetail_editUploadsid6" disabled="disabled">
                                    
							</td>
							<td style="padding-top:8px;">
								<span class="ccm_upload_img" style="float:right;margin-top:0;margin-right:30px">
			        				<input id="editUploadsid" onchange="document.getElementById('__up_load_text_invoiceDetail_editUploadsid6').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
			        				<input style="height:19px;width:60px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
		        				</span>
							</td>
						</tr>
						<tr>
							<td></td>
							<td colspan="2">
								<input id="btnCreditSave" type="submit" value="Save" onclick="return editChecking();"/>
								&nbsp;&nbsp;&nbsp;
								<input type="button" value="Cancel"
									onclick="editPaymentPanel.hide();" />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>

		<div id="addPaymentMiscPanelDiv">
			<div class="hd">
				<h2>Create Adjustment</h2>
			</div>
			<div class="bd">
				<form onsubmit="return uploadValidate('addAdjustmentFile');" 
					action="doMiscProposal.action" enctype="multipart/form-data"
					method="POST" target="uploadFrame_0">
					<table border="0" width="95%" align="center">
						<tr>
							<td  width="20%">
								SCOA:
								<input type="text" name="svo.invoiceId" value="${invoice.id}" style="display: none;">
							</td>
							<td width="80%">
							<div id="VL_vendorList_mpAccountCodeId"></div>
							</td>
						</tr>
						<tr>
							<td width="20%"></td>
							<td width="80%">
								Must Choose One SCOA!
							</td>
						</tr>
						<tr>
							<td nowrap="nowrap"  width="20%">
								Adjustment Amount:
							</td>
							<td width="80%">
								<input id="mpPaymentmAmount" name="svo.paymentAmount"
								   style="width:340;"
									type="text" t_value="" size="45" o_value=""
									onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
									onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
									onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}">
							</td>
						</tr>
						<tr>
							<td valign="top"  width="20%">
								Notes:
							</td>
							<td width="80%">
								<textarea id="mptxtRDescription" name="svo.sortingDirection" style="width:340;"
									rows="3" cols="46"></textarea>
							</td>
						</tr>
						<tr>
							<td valign="top" style="padding-top:8px;"  width="20%">
								Attach File:
							</td>
		        			<td width="80%">
		        			 <div id="addAdjustmentFile" style="overflow-y:auto;height:110px;position:relative;">
		        			</div>	
							</td>
						</tr>
						<tr>
							<td colspan="2" align="right">
								<input type="button" value="Add" onclick="addAdjustment();" />&nbsp;&nbsp;&nbsp;
								<input id="btnCreditSave" type="submit" value="Save" onclick="return addChecking();"/>&nbsp;&nbsp;&nbsp;
								<input type="button" value="Cancel" onclick="addPaymentPanel.hide();" />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
</sec:authorize>
		<div id="UpLoadFileForDisputeTabRightDivId" class="UpLoadFileForDisputeTabRightDiv">
			<div class="hd">
				<h2>Attachment Management</h2>
			</div>
			<div  class="bd">
						<h3>Attachment List</h3>
						<div align="left" style="width: 100%; overflow-x: auto;overflow-y: auto;max-height:300px;">
							<div id="_attachmentFileOfDisputeTablePage"></div>
						</div>
			</div>
			<div class="bd">
				<form onsubmit="return uploadFileValidate('AttachmentManagementFileId');"
					action="doUploadForDisputeTabRightSave.action"
					id="uploadFileForDisputeTabRightFrom"
					enctype="multipart/form-data" method="POST" target="uploadFrame_0">
					<table width="80%" align="center">
						<tr>
							<td colspan="2">
								<input id="disputeIdRight" name="disputeIdRight" type="hidden" />
							</td>
						</tr>
						<tr>
							<td style="float:left;margin-top:3px;" >
								Attach File:
							</td>
							<td >
							<div id="AttachmentManagementFileId" style="position:relative;overflow-y:auto;height:100px;vertical-align: top;">
                                    <div id ="AttachmentManagementNumber1" style="padding:3px 0 0 0;height: 19px;">
						 		    
						 		    <div style="float:left;">
						 			<input style="height:19px;width:190px;" type='text' id="__up_load_text_invoiceDetail_AttachmentManagement_1" class="ccm_clear" disabled="disabled">
		        					</div>	
							        <div style="float:left; margin:0 0 0 2px">
		        					<span class="ccm_upload_img">
			        					<input onchange="document.getElementById('__up_load_text_invoiceDetail_AttachmentManagement_1').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
			        					<input style="height:19px;width:75px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>	   						
		        					</span>
		        					</div>
		        						<div class="approveicon" onclick="deletefile('AttachmentManagementNumber1');"></div>
		        						<div class="clear" ></div>
		        				    </div>
		        				                           <div id ="AttachmentManagementNumber2" style="padding:3px 0 0 0;height: 19px;">
						 		    
						 		    <div style="float:left;">
						 			<input style="height:19px;width:190px;" type='text' id="__up_load_text_invoiceDetail_AttachmentManagement_2" class="ccm_clear" disabled="disabled">
		        					</div>	
							        <div style="float:left; margin:0 0 0 2px">
		        					<span class="ccm_upload_img">
			        					<input onchange="document.getElementById('__up_load_text_invoiceDetail_AttachmentManagement_2').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
			        					<input style="height:19px;width:75px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>	   						
		        					</span>
		        					</div>
		        						<div class="approveicon" onclick="deletefile('AttachmentManagementNumber2');"></div>
		        						<div class="clear" ></div>
		        				    </div>
		        				                           <div id ="AttachmentManagementNumber3" style="padding:3px 0 0 0;height: 19px;">
						 		    
						 		    <div style="float:left;">
						 			<input style="height:19px;width:190px;" type='text' id="__up_load_text_invoiceDetail_AttachmentManagement_3" class="ccm_clear" disabled="disabled">
		        					</div>	
							        <div style="float:left; margin:0 0 0 2px">
		        					<span class="ccm_upload_img">
			        					<input onchange="document.getElementById('__up_load_text_invoiceDetail_AttachmentManagement_3').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
			        					<input style="height:19px;width:75px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>	   						
		        					</span>
		        					</div>
		        						<div class="approveicon" onclick="deletefile('AttachmentManagementNumber3');"></div>
		        						<div class="clear" ></div>
		        				    </div>
		        				    </div>
							</td>
						</tr>
						<!--<tr>
							<td align="right" >
								Attach File:
							</td>
							<td>
								<input style="height:19px;width:172px;" type='text' id="__up_load_text_invoiceDetail_8" disabled="disabled">
		        					<span class="ccm_upload_img">
			        					<input id="" onchange="document.getElementById('__up_load_text_invoiceDetail_8').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
			        					<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
		        					</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								Attach File:
							</td>
							<td>
								<input style="height:19px;width:172px;" type='text' id="__up_load_text_invoiceDetail_9" disabled="disabled">
		        					<span class="ccm_upload_img">
			        					<input id="" onchange="document.getElementById('__up_load_text_invoiceDetail_9').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
			        					<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
		        					</span>
							</td>
						</tr>
						<tr>
							<td align="right">
								Attach File:
							</td>
							<td>
								<input style="height:19px;width:172px;" type='text' id="__up_load_text_invoiceDetail_10" disabled="disabled">
		        					<span class="ccm_upload_img">
			        					<input id="" onchange="document.getElementById('__up_load_text_invoiceDetail_10').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
			        					<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
		        					</span>
							</td>
						</tr>
						-->
						<tr>
							<th colspan="2">
							<input type="button" value="Clear Uploaded Files" style="width:155px;"
									onclick="clearUploadFiles();" />&nbsp;&nbsp;&nbsp;
							    <input type="button" value="Add"
									onclick="addAttachmentManagementFile();" />&nbsp;&nbsp;&nbsp;
								<sec:authorize ifAllGranted="FUNCTION_1121">
								<input id="btnUploadForDispureTabRightSave" type="button" value="Attach File" onclick="if(uploadFileValidate('AttachmentManagementFileId'))document.getElementById('uploadFileForDisputeTabRightFrom').submit();AllclearUploadFiles();" />
								&nbsp;&nbsp;&nbsp;								
								</sec:authorize>
								<input type="button" value="Cancel"
									onclick="YAHOO.ccm.container.UpLoadFileForDisputeTabRightDivId.hide();" />
							</th>
						</tr>
					</table>
				</form>
			</div>
		</div>

<sec:authorize ifAllGranted="FUNCTION_1150">
<div id="handleInvoiceAHRDialog" style="display: none;">
	<div class="hd">
		Approve Action
	</div>
	<div class="bd">
		<form id="handleInvoiceWorkflowActionForm" action="handleInvoiceWorkflow.action" enctype="multipart/form-data" method="POST" target="uploadFrame_0">
			<input type="hidden" id="oldInvoiceStatusId" name="oldInvoiceStatusId" value="${invoice.invoiceStatus.id}"/>
			<input name="approveWinUserId" type="hidden"/>
			<input name="approveWinRoleId" type="hidden"/>
			
			<table align="center" border="0">
				<tr>
					<td colspan="3">
						<input type="text" id="handle_action"  name="workflowActionId" style="display: none;"/>
						<input type="text" value="${invoice.id}" name="invoiceId" style="display: none;"/>
					</td> 
				</tr>
				<tr>
					<td colspan="1" width="30px">&nbsp;</td>
					<td nowrap="nowrap" width="340px" style="word-wrap:break-word;">
						<b>Payment Amount:<span id="workflowActionPaymentAmount">0.00</span></b><br/>
						<b>Miscellaneous Amount:<span id="workflowActionMisceManeousAmount">0.00</span></b><br/>
						<b>Dispute Amount:<span id="workflowActionDisputeAmount">0.00</span></b>
					</td>
				</tr>
				<tr> 
					<td colspan="2">
						 <table cellspacing=0 cellpadding=0 border=0 width=100%>
						 	<tr>
						 		<td width="30px">
						 			<br/>
						 			<b>Notes</b>
						 		</td>
						 		<td width="350px">
						 			<br/>
						 			<textarea name="uploadsMessage" maxlength="768" id="textareaNoteId" style="height:80px;width:340px;resize: none;"></textarea>
						 		</td>
						 	</tr>
						 	<tr>
								<td>
									@
								</td>
								<td style="height:19px;" >
							   		<div id="approveSelectUserDiv"></div>
								</td>
							</tr>
						 	<tr>
						 		<td>
						 		</td>
						 		<td>
						 			<div id="addFile" style="width:100%;overflow-y:auto;overflow-x: hidden;max-height:100px;vertical-align:top;position:relative;">
						 			</div>						 			
						 		</td>
						 	</tr>
						 </table>
					</td>
				</tr>
				<tr>
					<td  colspan="2">
						<div id="approveAttachDiv" style="float:left;width:180px; position:relative;">
							<input style="text-align:center;width:70px;height: 19px;position: absolute;top: 11;
										  left: 27px;z-index: 100;opacity: 0;cursor: pointer;" type="file" 
									name="uploads" multiple="multiple" onchange="filesProcess(this.files,2)" />
							<div class="arpbutatc">Attach</div>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
</sec:authorize>

<div id="recordDisputeAndApplyToPaymentDialog" style="display: none;">
	<div class="hd">
		Record as Dispute Win and Apply to Payment
	</div>
	<div class="bd">
		<form id="doRecordDisputeAndApplyToPaymentForm" action="doRecordDisputeAndApplyToPayment.action" style="overflow-y:auto;max-height:200px;position:relative;"
					enctype="multipart/form-data" method="POST" target="uploadFrame_0">
			<table width="95%" align="center" border=0>
				<tr>
			 		<td id="recordDisputeAndApplyToPaymentConfirm" colspan="2">
			 			&nbsp;
			 		</td> 
			 	</tr>
				<tr>
			 		<td width="30px">
			 			<b>Notes</b>
			 		</td>
			 		<td>
			 			<input type="hidden" name="invoiceId" value="${invoice.id}"/>
			 			<input type="hidden" name="inProposalIds" value="" id=""/>
			 			<textarea name="uploadsMessage"  style="height:80px;width:390px;"></textarea>
			 		</td>
			 	</tr>
			 	<tr>
			 		<td>&nbsp;</td>
			 		<td>
			 			<input style="height:19px;width:312px;" type='text' id="__up_load_text_invoiceDetail_14" disabled="disabled">
        					<span class="ccm_upload_img">
	        					<input onchange="document.getElementById('__up_load_text_invoiceDetail_14').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
	        					<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
        					</span>
			 				<br/>
						<input style="height:19px;width:312px;" type='text' id="__up_load_text_invoiceDetail_15" disabled="disabled">
        					<span class="ccm_upload_img">
	        					<input onchange="document.getElementById('__up_load_text_invoiceDetail_15').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
	        					<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
        					</span>
        					<br/>
						<input style="height:19px;width:312px;" type='text' id="__up_load_text_invoiceDetail_16" disabled="disabled">
        					<span class="ccm_upload_img">
	        					<input onchange="document.getElementById('__up_load_text_invoiceDetail_16').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
	        					<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
        					</span>
			 		</td>
			 	</tr>
			</table>
		</form>
	</div>
</div>

<div id="outstandingCreditSplitApplyDialog" style="display: none;">
	<div class="hd">
		Split Apply
	</div>
	<div class="bd">
		<form id="doOutstandingCreditSplitApplyForm" action="doOutstandingCreditSplitApply1.action" 
		enctype="multipart/form-data" method="POST" target="uploadFrame_0" style="overflow-y:auto;max-height:200px;position:relative;">
			<table width="95%" align="center" border=0>
				<tr>
			 		<td width="30px">
			 			<b>SCOA</b>
			 		</td>
			 		<td>
			 			<span id="VL_vendorList_credit_tab1"></span>
			 		</td>
			 	</tr>
				<tr>
			 		<td width="30px">
			 			<b>Amount</b>
			 		</td>
			 		<td>
			 		    <input type="hidden" name="attachmentPointId" value="" id="Split_attachmentPoint_id"/>
			 			<input type="hidden" name="invoiceId" value="${invoice.id}"/>
			 			<input type="hidden" name="inProposalIds" value="" id=""/>
			 			<input type="text" name="balanceAmount" class="validate-number"/>
			 		</td>
			 	</tr>
				<tr>
			 		<td width="30px">
			 			<b>Notes</b>
			 		</td>
			 		<td>
			 			<textarea name="uploadsMessage"  style="height:80px;width:390px;"></textarea>
			 		</td>
			 	</tr>
			 	<tr>
			 		<td><b>Attach File:</b></td>
			 		<td>
			 			<div style="position:relative;height:100px;vertical-align:top;">
                    	<div style="padding:3px 0 0 0;height:19px;">
						<div style="float:left;">
			 			<input style="height:19px;width:312px;" type='text' id="__up_load_text_invoiceDetail_17" disabled="disabled">
			 			</div>
       					<div style="float:left; margin:0 0 0 2px">
						<span class="ccm_upload_img">
			        		<input onchange="document.getElementById('__up_load_text_invoiceDetail_17').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
			        		<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
		        		</span>
			 			</div>
       					</div>
       					
       					<div style="padding:3px 0 0 0;height:19px;">
						<div style="float:left;"> 
						<input style="height:19px;width:312px;" type='text' id="__up_load_text_invoiceDetail_18" disabled="disabled">
						</div>
       					<div style="float:left; margin:0 0 0 2px">
       					<span class="ccm_upload_img">
			        		<input onchange="document.getElementById('__up_load_text_invoiceDetail_18').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
			        		<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
		        		</span>
						</div>
       					</div>
       					
       					<div style="padding:3px 0 0 0;height:19px;">
						<div style="float:left;">
						<input style="height:19px;width:312px;" type='text' id="__up_load_text_invoiceDetail_19" disabled="disabled">
						</div>
						<div style="float:left; margin:0 0 0 2px">
		        		<span class="ccm_upload_img">
			        		<input onchange="document.getElementById('__up_load_text_invoiceDetail_19').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
			        		<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
		        		</span>
		        		</div>
       					</div>
       					</div>
			 		</td>
			 	</tr>
			</table>
		</form>
	</div>
</div>

<div id="addOutstandingDisputeSCOADialog" style="display: none;">
	<div class="hd">
		SCOA Coding
	</div>
	<div class="bd">
		<form>
			<table width="100%" align="center" border=0>
				<tr>
			 		<td width="30px">
			 			<b>SCOA</b>
			 		</td>
			 		<td>
			 			<span id="VL_vendorList_credit_tab2"></span>
			 		</td>
			 	</tr>
			 	<tr>
						<td valign="top" style="padding-top:5px;">
							Notes
						</td>
						<td style="padding-top:5px;">
							<textarea name="" cols="" rows="" id="addOutstandingDisputeNotes" class="validate-text" style="padding:0px 1px;overflow:auto ;width:100%;height:100px;"></textarea>
						</td>
					</tr>
			</table>
		</form>
	</div>
</div>

<div class="yui-pe-content" id="reconcileByCurrentCreditDialogM1" style="display: none;">
	<div class="hd">
		Reconcile 
	</div>
	<div class="bd">
		<form id="doReconcileByCurrentCreditM1Form" action="doReconcileByCurrentCreditM1.action"
			enctype="multipart/form-data" method="POST" target="uploadFrame_0">
			<table height="100%" width="95%" align="center" border=0>
				<tr height="15px">
			 		<td id="doReconcileByCurrentCreditM1FormConfirm" colspan="2">
			 			&nbsp;
			 		</td> 
			 	</tr>
			 	<tr>
			 		<td width="30px">
			 			<b>SCOA</b>
			 		</td>
			 		<td>
			 			<span id="VL_vendorList_credit_tab4"></span>
			 		</td>
			 	</tr>
				<tr>
			 		<td width="30px">
			 			<b>Notes</b>
			 		</td>
			 		<td>
			 			<input type="hidden" name="invoiceId" value="${invoice.id}"/>
			 			<input type="hidden" name="disputeIds" value=""/>
			 			<input type="hidden" name="inProposalIds" value="" id=""/>
			 			<textarea name="uploadsMessage"  style="height:80px;width:390px;"></textarea>
			 		</td>
			 	</tr>
			 	<tr>
			 		<td>&nbsp;</td>
			 		<td  >
		        		<div style="VERTICAL-ALIGN: top; overflow-y:auto;height:100px;position:relative;width:395px;" id ="moreReconcileFile">
		        		<div id ="__up_loads_text_reconcile_Number20" style="PADDING-TOP: 3px;height: 19px;">
							<div style="float:left;">
								<input style="height:19px;width:275px;" type='text' id="__up_loads_text_reconcile20" disabled="disabled">
	                        </div>
	                    <div style="float:left; margin:0 0 0 2px">
			        		<span class="ccm_upload_img">
				        		<input onchange="document.getElementById('__up_loads_text_reconcile20').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
				        		<input style="height:19px;width:75px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
			        		</span>
			        	</div>
		        		<div class="approveicon" onclick="deletefile('__up_loads_text_reconcile_Number20');"></div>
		        		<div class="clear" ></div>
		        		</div>	
		        		
		        		<div id ="__up_loads_text_reconcile_Number21" style="PADDING-TOP: 3px;height: 19px;">
							<div style="float:left;">
								<input style="height:19px;width:275px;" type='text' id="__up_loads_text_reconcile21" disabled="disabled">
	                        </div>
	                    <div style="float:left; margin:0 0 0 2px">
			        		<span class="ccm_upload_img">
				        		<input onchange="document.getElementById('__up_loads_text_reconcile21').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
				        		<input style="height:19px;width:75px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
			        		</span>
			        	</div>
		        		<div class="approveicon" onclick="deletefile('__up_loads_text_reconcile_Number21');"></div>
		        		<div class="clear" ></div>
		        		</div>		
		        		
		        		<div id ="__up_loads_text_reconcile_Number22" style="PADDING-TOP: 3px;height: 19px;">
							<div style="float:left;">
								<input style="height:19px;width:275px;" type='text' id="__up_loads_text_reconcile22" disabled="disabled">
	                        </div>
	                    <div style="float:left; margin:0 0 0 2px">
			        		<span class="ccm_upload_img">
				        		<input onchange="document.getElementById('__up_loads_text_reconcile22').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
				        		<input style="height:19px;width:75px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
			        		</span>
			        	</div>
		        		<div class="approveicon" onclick="deletefile('__up_loads_text_reconcile_Number22');"></div>
		        		<div class="clear" ></div>
		        		</div>	
		        		</div>				
			 		</td>
			 	</tr>
			</table>
		</form>
	</div>
</div>

<div class="yui-pe-content" id="reconcileByCurrentCreditDialog11" style="display: none;">
	<div class="hd">
		Reconcile 
	</div>
	<div class="bd">
		<form id="doReconcileByCurrentCredit11Form" action="doReconcileByCurrentCredit11.action"
			enctype="multipart/form-data" method="POST" target="uploadFrame_0">
			<table height="100%" width="95%" align="center" border=0 >
				<tr height="15px">
			 		<td id="doReconcileByCurrentCredit11FormConfirm" colspan="2">
			 			&nbsp;
			 		</td> 
			 	</tr>
				<tr height="15px">
			 		<td width="30px">
			 			<b>Amount</b>
			 		</td>
			 		<td>
			 			<input type="text" name="balanceAmount" value="" class="validate-number" id="doReconcileByCurrentCredit11FormAmount"/>
			 		</td>
			 	</tr>
				<tr>
			 		<td width="30px">
			 			<b>SCOA</b>
			 		</td>
			 		<td>
			 			<span id="VL_vendorList_credit_tab5"></span>
			 		</td>
			 	</tr>
				<tr>
			 		<td width="30px">
			 			<b>Notes</b>
			 		</td>
			 		<td>
			 			<input type="hidden" name="invoiceId" value="${invoice.id}"/>
			 			<input type="hidden" name="disputeIds" value=""/>
			 			<input type="hidden" name="inProposalIds" value="" id=""/>
			 			<textarea name="uploadsMessage"  style="height:80px;width:390px;"></textarea>
			 		</td>
			 	</tr>
			 	<tr>
			 		<td>&nbsp;</td>
			 		<td  >
		        		<div style="VERTICAL-ALIGN: top; overflow-y:auto;height:100px;position:relative;width:395px;" id ="reconcileFile">
		        		<div id ="__up_loads_text_reconcile_Number1" style="PADDING-TOP: 3px;height: 19px;">
							<div style="float:left;">
								<input style="height:19px;width:275px;" type='text' id="__up_loads_text_reconcile1" disabled="disabled">
	                        </div>
	                    <div style="float:left; margin:0 0 0 2px">
			        		<span class="ccm_upload_img">
				        		<input onchange="document.getElementById('__up_loads_text_reconcile1').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
				        		<input style="height:19px;width:75px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
			        		</span>
			        	</div>
		        		<div class="approveicon" onclick="deletefile('__up_loads_text_reconcile_Number1');"></div>
		        		<div class="clear" ></div>
		        		</div>	
		        		
		        		<div id ="__up_loads_text_reconcile_Number2" style="PADDING-TOP: 3px;height: 19px;">
							<div style="float:left;">
								<input style="height:19px;width:275px;" type='text' id="__up_loads_text_reconcile2" disabled="disabled">
	                        </div>
	                    <div style="float:left; margin:0 0 0 2px">
			        		<span class="ccm_upload_img">
				        		<input onchange="document.getElementById('__up_loads_text_reconcile2').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
				        		<input style="height:19px;width:75px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
			        		</span>
			        	</div>
		        		<div class="approveicon" onclick="deletefile('__up_loads_text_reconcile_Number2');"></div>
		        		<div class="clear" ></div>
		        		</div>		
		        		
		        		<div id ="__up_loads_text_reconcile_Number3" style="PADDING-TOP: 3px;height: 19px;">
							<div style="float:left;">
								<input style="height:19px;width:275px;" type='text' id="__up_loads_text_reconcile3" disabled="disabled">
	                        </div>
	                    <div style="float:left; margin:0 0 0 2px">
			        		<span class="ccm_upload_img">
				        		<input onchange="document.getElementById('__up_loads_text_reconcile3').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
				        		<input style="height:19px;width:75px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
			        		</span>
			        	</div>
		        		<div class="approveicon" onclick="deletefile('__up_loads_text_reconcile_Number3');"></div>
		        		<div class="clear" ></div>
		        		</div>	
		        		</div>				
			 		</td>
			 	</tr>
			</table>
		</form>
	</div>
</div>

 	<div id="deleteProposalPanelDiv">
		<div class="hd">
			<h2>
				Delelte Proposal
			</h2>
			</div>
			<div class="bd">
					<table width="95%" align="center">
						<tr>
							<td align="right">
								Dispute Reason:
							</td>
							<td>
								<input type="hidden" id="proposalId" >
								<input type="hidden" id="invoiceItemId" >
								<input type="text" name="dipute_reason"
									   id="dipute_reason" >
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<sec:authorize ifAllGranted="FUNCTION_1121">
								<input id="btnCreditSave" type="button" value="Save"
									onclick="if(validateDisputeReasonTextMoreThanTenChar(document.getElementById('dipute_reason')))deleteRecodeFromProposalList(document.getElementById('proposalId').value,document.getElementById('invoiceItemId').value,document.getElementById('dipute_reason').value);" />
								&nbsp;&nbsp;&nbsp;
								</sec:authorize>
								<input type="button" value="Cancel"
									onclick="YAHOO.ccm.container.deleteProposalPanelDiv.hide();" />
							</td>
						</tr>
					</table>
			</div>
	</div>

	<div class="yui-pe-content" id="editMoveToCredit">
	<div class="hd">
		<h2>Please confirm</h2>
	</div>
	<div class="bd">
		<form id="form2">
		<table width="95%" align="center" border=0 >
			<tr>
				<td colspan="2">
					 <div id="__move_to_credit_div"/> 
				</td>
			</tr>
		</table>
		</form>
	</div>
</div>
<div class="yui-pe-content" id="exemptionNotesWindow" display="none">
	<div class="hd">
		<h2>Exemption Certificate Date Range</h2>
	</div>	
	<div class="bd">
		<table width="100%" align="center" border=0 >
			<tr>
				<td colspan="2">
					 <div id="banExemptionCertificateTable" style="overflow-y: auto;overflow-x: auto;max-height:450px;max-width:1180px;"/> 
				</td>
			</tr>
		</table>
	</div>
</div>
<div id="scoaInfo" style="display:none;">
	<h1 style="color:red">Invoice is not granted for change!</h1>
</div>

<div class="yui-pe-content" id="closeAsDisputeLoseWindow" style="display: none;">
	<div class="hd">
		<h2>Close as dispute lose</h2>
	</div>
	<div class="bd">
		<form id="closeDisputeLoseForm" action="closeDisputeLose.action" 
		enctype="multipart/form-data" method="POST" target="uploadFrame_0">
			<input type="hidden" id="__box_id" name="searchDisputeVO.boxId" value="" />
			<table height='100%' width="100%" align="center" border=0 >
				<tr height="15px">
					<td colspan="2">
						You are going to force as dispute lose, please confirm.
					</td>
				</tr>
				<tr>
					<td>
						SCOA
					</td>
					<td>
						<span id="VL_vendorList_credit_tab6"></span>
					</td>
				</tr>
				<tr>
					<td width=100px valign="top">
						Attach File:
					</td>
					<td >
                    <div style="VERTICAL-ALIGN: top; OVERFLOW-Y: auto; max-height: 110px ;position:relative;width:285px;" id = "Close_dispute">
						
		        	</div>				
					</td>
				</tr>
				<tr>
					<td width=100px>
					</td>
					<td >
                        <span class="button-group" style="float: right;padding-left: 210px;padding-top: 10px; padding-bottom:  10px;">
						<span id="yui-gen6" class="yui-button yui-push-button"><span class="first-child">
						<button type="button" id="yui-gen6-button" onclick="addCloseAsDisputeLoseFiles();">Add</button></span></span></span>
		        	</div>				
					</td>
				</tr>
				
				<tr>
					<td valign="top" >
						Notes:
					</td>
					<td>
						<s:textarea name="searchDisputeVO.notes"  cssStyle="padding:0px 1px;overflow:auto ;width:100%;resize: none;height:60px;" cssClass="validate-text"></s:textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>


<div class="yui-pe-content" id="splitCloseDisputeWindow" style="display: none;">
	<div class="hd">
		<h2>Split and close</h2>
	</div>
	<div class="bd">
		<form id="doSplitCloseDisputeForm" action="splitCloseDispute1.action" 
		enctype="multipart/form-data" method="POST" target="uploadFrame_0">
			<table height="100%" width="100%" align="center" border="0">
				<tr height="15px">
					<td>
						Amount:
					</td>
					<td>
						<s:textfield name="searchDisputeVO.balanceAmount" cssClass="validate-number"></s:textfield>
					</td>
				</tr>
				<tr>
					<td>
						SCOA
					</td>
					<td>
						<span id="VL_vendorList_credit_tab3"></span>
					</td>
				</tr>
				<tr>
					<td valign="top">
						Attach File:
						<input type="hidden" name="searchDisputeVO.boxId" value="" />
						<input type="hidden" name="searchDisputeVO.bo" value="Lose" />
					</td>
					    
					<td>
						<!--<input style="height:19px;width:50%;" type='text' id="__up_load_text_dispute2" disabled="disabled">
	        			<span class="ccm_upload_img">
			        		<input id="__load_up_three" onchange="document.getElementById('__up_load_text_dispute2').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
			        		<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
	        			</span>-->
	        			
	        			<div style="VERTICAL-ALIGN: top; OVERFLOW-Y: auto; MAX-HEIGHT: 110px ;width:285px;position:relative;" id = "Split_Close">
		        		</div>
					</td>
				</tr>
				<tr>
					<td width=100px>
					</td>
					<td >
                        <span class="button-group" style="float: right;padding-left: 210px;padding-top: 10px; padding-bottom:  10px;">
						<span id="yui-gen6" class="yui-button yui-push-button"><span class="first-child">
						<button type="button" id="yui-gen6-button" onclick="addsplitCloseDisputeFile();">Add</button></span></span></span>
		        	</div>				
					</td>
				</tr>
				<tr>
					<td valign="top" >
						Notes:
					</td>
					<td>
						<s:textarea name="searchDisputeVO.notes" cssStyle="padding:0px 1px;overflow:auto ;width:100%;height:60px; resize: none;" cssClass="validate-text"></s:textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>

<div id="LabelItem" style="display: none;">
	<div class="InDePoput">
		<div class="IDPtop"></div>
		<div class="IDPcon">
	  	  <div class="IDPleft"></div>
	    	<div class="IDPright"></div>
	      <div class="clear"></div>
	  	</div>
		<div class="IDPdown"></div>
	</div>
</div>

<div class="update-ReportAdminTags" style=" border:1px #666666 solid;" id="circuitDetail">
	<div id="circuitDetailPanel" style="width: 1100px; box-shadow: 0 0 6px 10px rgba(192,192,192,.4); padding: 10;"/>
		<table style="width:100%;table-layout: fixed;">
			<tr>
				<td>
				<div align="left"  style="width:100%;overflow-x:auto;border: 0px solid;padding-bottom: 20px;">
					<div id="_master_inventory"></div>
				</div>
				</td>
			</tr>
		</table>
		<table style="width:100%;">
			<tr>
				<td>
					<div class="demo" id="__master_inventory_page_list" style="float:left"></div>
					<input id="download_button" type="button" value="Download to Excel" onclick="saveMasterInventoryToExcel();" />
				</td>
			<tr>
		</table>
	</div>
</div>
<div class="yui-pe-content" id="updateRefileTheDispute" style="display: none;">
	<div class="hd">
		<h2>Refile Dispute</h2>
	</div>
	<div class="bd">
		<table width="95%" border=0 align="center">
			<tr>
				<td colspan="2" style="padding-left: 10px">
					Are you sure your want to refile this dispute?</br>
					TEMS will send out a refile E-mail to vendor.</br>
				</td>
			</tr>
		</table>
	</div>
</div>

<!-- Validation result modal. -->
<div id="validationResult">
	<!-- 显示Validation_result 的modal -->
	<div id="validationResultPanel" />
		<div class="modal-header" id="modalHeader"></div>
		<a class="validation-result-modal-close"></a>

		<div id="validation-result-panel-content"></div>
	</div>
</div>

<!-- 2019-01-14检查发现这个弹出框似乎没用.暂时保留. BY Lmy  -->
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
<%--				<input type="button" class="save-dispute-action-request" value="save" onclick="saveDisputeActionRequest()">--%>
				<input type="button" class="save-dispute-action-request" value="Pending" onclick="saveDisputeActionRequest('2')">
				<input type="button" class="save-dispute-action-request" value="Complet" onclick="saveDisputeActionRequest('3')">
				<input type="button" class="clear-dispute-action-request-notes" value="clear" onclick="clearDisputeActionRequestNotes()">
			</div>
		</div>
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
<iframe id="__downloadIframe" style="display: none;"></iframe>

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
<div class="yui-pe-content" id="sendInvoiceWindow"
	style="visibility: hidden;">
	<div class="hd">
		<h2>Send Invoice Email</h2>
	</div>
	<div class="tabForm" style="padding-bottom:25px;  max-height:600px;">
	<div class="sendInvoiceWide"></div>
    </div>
</div>

<!-- Upload SCOA failed message box. -->
<div class="yui-pe-content" id="uploadSCOAFailedMessageBox" style="visibility: hidden;">
    <div class="hd">
        <h2>SCOA Upload</h2>
    </div>
    <div class="tabForm" style="padding-bottom:25px;  height:100px;">
        <div class="message-box-left">
            <div class="failed-icon"></div>
        </div>
        <div class="message-box-right">
            <div class="title">SCOA upload is failed.</div>
            <div class="content">Some data is wrong, please download the error file.</div>
            <div class="link" onclick="downloadSCOADataErrorFile()">SCOA Data Import Errors.xlxs</div>
        </div>
    </div>
</div>

<script type="text/javascript"> 
	var invoiceId = ${invoice.id};
	var invoiceBarCode = '${invoice.barCode}';
	var currentBanId = ${invoice.ban == null ? "null" : invoice.ban.id};
	var invoiceNummbers = '${invoice.invoiceNumber}';
	var invoiceItemId = ${param.invoiceItemId == null ? "null" : param.invoiceItemId};
	var invoiceStatusId = ${invoice.invoiceStatus == null ? "null" : invoice.invoiceStatus.id};
	var currentUserId = '<%=SystemUtil.getCurrentUserId()%>';
	var currentAnalystId = ${invoice.assignedAnalystId == null ? "null" : invoice.assignedAnalystId};
	var currentAssignmentId = ${invoice.userByAssignmentUserId == null ? "null" : invoice.userByAssignmentUserId.id};
	var currentWorkflowUserId = ${invoice.userByWorkflowUserId == null ? "null" : invoice.userByWorkflowUserId.id};
</script>

<script type="text/javascript">
	var SC_Array = {
		"results": [
			{
			    "id": "",
			    "name": "Enter number to search SCOA"
			}
			<c:forEach items="${scoaCodeList}" var="v">
			,{
			    "id": "${v.key}",
			    "name": "${v.value}"
			}
			</c:forEach>
		],
		"total": ${fn:length(scoaCodeList)}
	};
	var GLOBAL_BAN_ID = "${invoice.ban.id}";

	$(function(){
		SCOACodeing = $('#VL_vendorList__scoa_codeing').flexbox(SC_Array, {
			maxCacheBytes : 200000,
			highlightMatches : true,
			autoCompleteFirstMatch : false,
			paging: false,
			width : 260,
			name : "svo.accountCodeId"
		});
		SCOACodeing.setValue("");
	});
	$(function(){
		mpAccountCodeId = $('#VL_vendorList_mpAccountCodeId').flexbox(SC_Array, {
			maxCacheBytes : 200000,
			highlightMatches : true,
			autoCompleteFirstMatch : false,
			paging: false,
			width : 340,
			name : "svo.accountCodeId"
		});
		mpAccountCodeId.setValue("");
	});
	$(function(){
		editAccountCodeId = $('#VL_vendorList_editAccountCodeId').flexbox(SC_Array, {
			maxCacheBytes : 200000,
			highlightMatches : true,
			autoCompleteFirstMatch : false,
			paging: false,
			width : 260,
			name : "svo.accountCodeId"
		});
		editAccountCodeId.setValue("");
	});
	$(function(){
		scoa_code = $('#VL_vendorList__scoa_code').flexbox(SC_Array, {
			maxCacheBytes : 200000,
			highlightMatches : true,
			autoCompleteFirstMatch : false,
			paging: false,
			width: 257,
			name : "svo.accountCodeId"
		});
		scoa_code.setValue("");
	});
	$(function(){
		account_code = $('#VL_vendorList_account_code').flexbox(SC_Array, {
			maxCacheBytes : 200000,
			highlightMatches : true,
			autoCompleteFirstMatch : false,
			paging: false,
			width : 260,
			name : "SCOA"
		});
		account_code.setValue("");
	});
	$(function(){
		eAccount_code = $('#VL_vendorList_eaccount_code').flexbox(SC_Array, {
			maxCacheBytes : 200000,
			highlightMatches : true,
			autoCompleteFirstMatch : false,
			paging: false,
			width : 260,
			name : "SCOA"
		});
		eAccount_code.setValue("");
	});
	$(function(){
		accountCodeCreditTab1 = $('#VL_vendorList_credit_tab1').flexbox(SC_Array, {
			maxCacheBytes : 200000,
			highlightMatches : true,
			autoCompleteFirstMatch : false,
			paging: false,
			width : 280,
			alignWidth : 280,
			name : "accountCodeId"
		});
		accountCodeCreditTab1.setValue("");
	});
	$(function(){
		accountCodeCreditTab2 = $('#VL_vendorList_credit_tab2').flexbox(SC_Array, {
			maxCacheBytes : 200000,
			highlightMatches : true,
			autoCompleteFirstMatch : false,
			paging: false,
			width : 340,
			alignWidth : 340
		});
		accountCodeCreditTab2.setValue("");
	});

	$(function(){
		accountCodeCreditTab3 = $('#VL_vendorList_credit_tab3').flexbox(SC_Array, {
			maxCacheBytes : 200000,
			highlightMatches : true,
			autoCompleteFirstMatch : false,
			paging: false,
			width : 280,
			alignWidth : 280,
			name : "searchDisputeVO.accountCodeId"
		});
		accountCodeCreditTab3.setValue("");
	});

	$(function(){
		accountCodeCreditTab4 = $('#VL_vendorList_credit_tab4').flexbox(SC_Array, {
			maxCacheBytes : 200000,
			highlightMatches : true,
			autoCompleteFirstMatch : false,
			paging: false,
			width : 280,
			alignWidth : 280,
			name : "accountCodeId"
		});
		accountCodeCreditTab4.setValue("");
	});

	$(function(){
		accountCodeCreditTab5 = $('#VL_vendorList_credit_tab5').flexbox(SC_Array, {
			maxCacheBytes : 200000,
			highlightMatches : true,
			autoCompleteFirstMatch : false,
			paging: false,
			width : 280,
			alignWidth : 280,
			name : "accountCodeId"
		});
		accountCodeCreditTab5.setValue("");
	});

	$(function(){
		accountCodeCreditTab6 = $('#VL_vendorList_credit_tab6').flexbox(SC_Array, {
			maxCacheBytes : 200000,
			highlightMatches : true,
			autoCompleteFirstMatch : false,
			paging: false,
			width : 280,
			alignWidth : 280,
			name : "searchDisputeVO.accountCodeId"
		});
		accountCodeCreditTab6.setValue("");
	});

	$(function(){
		customMotiScoa("custom_moti_scoa_1",SC_Array,"");
	});

</script>
</div>
    
    <!-- Circuit Layer: when click MRC variance operate button. -->
    <div class="circuit-layer">
        <div class="title-bar clearfix">
            <h3 class="pull-left">
                <span class="text-label">Stripped Circuit Number: </span>
                <span class="circuit-number-value">01282256247</span>
            </h3>

            <div class="back-to-invoice-details pull-right">
                <input type="button" value="Back" onclick="window.mrcVariance.hideCircuitDetailsLayer()">
            </div>
        </div>

        <div class="attachBorder split-line"></div>

        <div class="validation-info">
            <table class="validation-info-table">
                <tr>
                    <td>Status Validation</td>
                    <td class="status-validation-UI">Failed</td>
                    <td>Variance Reason</td>
                    <td><div id="variance-reason-list"></div></td>
                </tr>
                <tr>
                    <td>Rate Validation</td>
                    <td class="rate-validation-UI">Failed</td>
                    <td>Variance Notes</td>
                    <td>
                        <textarea class="variance-notes-area" cols="50" rows="3"></textarea>
                    </td>
                </tr>
            </table>

            <div class="validation-info-btns">
                <input type="button" value="Re-validate">
                <input type="button" value="Save">
            </div>
        </div>

        <div class="circuit-info attachBorder">
            <h3>Master Inventory</h3>
            <table class="circuit-info-table">
                <tr>
                    <td>Circuit Status</td>
                    <td class="circuit-status"></td>
                    <td>Service ID</td>
                    <td class="service-id"></td>
                </tr>
                <tr>
                    <td>Unique Circuit ID</td>
                    <td class="unique-circuit-id"></td>
                    <td>Product Category</td>
                    <td class="product-category"></td>
                </tr>
                <tr>
                    <td>Installation Date</td>
                    <td class="installation-date"></td>
                    <td>Disconnection Date</td>
                    <td class="disconnection-date"></td>
                </tr>
            </table>
            <div class="circuit-info-btns">
                <input type="button" value="Edit" class="edit" 
                    onclick="window.mrcVariance.popupEditElement(this)">
            </div>
        </div>
        
        <div class="yui-navset variance-tab-switch-control">

            <div class="yui-nav">
                <li onclick="window.mrcVariance.changeVarianceTab(this)" data-bind="item-variance" class="selected">
                    <a href="javascript: void(0)"><em>Item Variance</em></a>
                </li>
                <li onclick="window.mrcVariance.changeVarianceTab(this)" data-bind="two-month-items">
                    <a href="javascript: void(0)"><em>2 Months Items</em></a>
                </li>
            </div>

            <div class="yui-content">
                <div id="item-variance">
                    <table class="list-wrapper-outer-table">
                        <tr>
                            <td>
                                <div class="list-wrapper-container" >
                                    <div id="item-variance-list">this is item variance.</div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div id="item-variance-list-pagination"></div>
                            </td>
                        </tr>
                    </table>
                </div>

                <div id="two-month-items">
                    <table class="list-wrapper-outer-table">
                        <tr>
                            <td>
                                <div class="list-wrapper-container" >
                                    <div id="two-month-items-list"> this is two month items.</div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div id="two-month-items-list-pagination"></div>
                            </td>
                        </tr>
                    </table>
                </div>

            </div>

        </div>

    </div> <!-- /. .circuit-layer -->

    <div class="yahoo-widget-container" id="variance-circuit-details">
        <div id="variance-circuit-details-panel">
            <table class="list-wrapper-outer-table">
                <tr>
                    <td>
                        <div class="list-wrapper-container" >
                            <div id="variance-master-inventory-details-list"></div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div id="variance-master-inventory-details-list-pagination"></div>
                    </td>
                </tr>
            </table>
        </div>

    </div> <!-- /. #variance-circuit-details -->

</div>  <!-- /. pageContainer -->