<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link rel="stylesheet" type="text/css" href="include/javascript/Calendar/styles/calendar.css">
<link rel="stylesheet" type="text/css" href="include/javascript/flexbox/css/jquery.flexbox.css"><link>

<script type="text/javascript" src="include/javascript/flexbox/js/jquery.flexbox.js"></script>
<script type="text/javascript" src="include/javascript/Calendar/simplecalendar.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/ccm/searchInvoice.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/ccm/quicklink.js" language="javascript"></script>
<SCRIPT type="text/javascript" src="include/javascript/ccm/common/simpleValidation.js"></SCRIPT>
<SCRIPT type="text/javascript" src="include/javascript/ccm/common/common.js"></SCRIPT>
<style>
	.aright{text-align:right;}
	
	.tabDetailView {
        border-width: 1px;
    }
    
    .tabDetailViewDL.left-part {
        border-right: none;
    }
</style>
<script type="text/javascript"> 
var VL_Array = {
	"results": [
		{
		    "id": "all",
		    "name": ""
		}
		<c:forEach items="${vendorList}" var="v">
		,{
		    "id": "${v.key}",
		    "name": "${v.value}"
		}
		</c:forEach>
	],
	"total": ${fn:length(vendorList)}
};
var BLD_Array = {
	"results": [
		{
		    "id": "all",
		    "name": ""
		}
		<c:forEach items="${banList}" var="b">
		,{
		    "id": "${b.key}",
		    "name": "${b.value}"
		}
		</c:forEach>
	],
	"total": ${fn:length(banList)}
};

$(function(){
	$('#form0_searchInvoiceVO_banId').change(function(){
		form0_searchInvoiceVO_banId.setValue("all");
	});
});

$(function(){
	form0_searchInvoiceVO_vendorId = $('#VL_vendorList_1').flexbox(VL_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 260,
		onSelect : function(){
			getBanListByVendorId(0);
			form0_searchInvoiceVO_banId.setValue("all");
		}
	});
	form0_searchInvoiceVO_vendorId.setValue("all");
});
$(function(){
	form0_searchInvoiceVO_banId = $('#VL_banList_Invoice').flexbox(BLD_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 260,
		onSelect : function(){
			getBanListByVendorId(0);
			form0_searchInvoiceVO_vendorId.setValue("all");
		}
	});
	form0_searchInvoiceVO_banId.setValue("all");
});
$().ready(function(){
	// Add By donghao.guo
	var handleSubmit = function() {
		var qn = queryName.value;
		if(qn.length > 30){
			alert("The name is too long. ");
			return false;
		}
		if(!FIC_checkForm('queryName')) return;
		YAHOO.ccm.container.saveQueryDialog.hide();
		saveSearch();
	};
	var handleCancel = function() {
		queryName.value = ""; 
		this.cancel();
	};
	YAHOO.util.Dom.removeClass("saveQueryDialog", "yui-pe-content");
	
	YAHOO.ccm.container.saveQueryDialog = new YAHOO.widget.Dialog("saveQueryDialog", 
							{ width : "30em",
							  fixedcenter : true,
							  visible : false, 
							  constraintoviewport : true,
							  buttons : [ { text:"Submit", handler:handleSubmit},
								      { text:"Cancel", handler:handleCancel } ]
							});
	YAHOO.ccm.container.saveQueryDialog.render();
});
</script>
<div id="pageContainer" class="tabForm" onMouseDown="if(g_Calendar && g_Calendar.hide)g_Calendar.hide();" style="padding-bottom:25px;" >
	<h3 style="padding-top:0px; padding-bottom:4px;">
		Invoice Search
	</h3>
	<div id="ctl00_MainContent_SearchInvoiceControl1_UpdatePanel1">
		<s:form id="form0" action="" theme="simple" onsubmit="startSearch();return false;">
			<table class="tabDetailView" border=" 0" width="100%" cellpadding="5" cellspacing="0">
				<tr valign="top">
					<td class="tabDetailViewDL left-part" width="52%">
						<div class="searchItemPanel">
							<table width="100%"  cellspacing="3" cellpadding="0" border="0">
								<tr>
									<td width="30%">
										Vendor
									</td>
									<td align="left" style="position:relative;">
										<div id="VL_vendorList_1"></div>
									</td>
								</tr>
								<tr>
									<td>
										BAN
									</td>
									<td>
										<span class="select1"> <select id="form0_searchInvoiceVO_banId" name="searchInvoiceVO.banId" style="width:150px">
											<option value="all">All</option>
										</select> </span>
									</td>
								</tr>
								<tr>
									<td>
										All BAN
									</td>
									<td align="left" style="position:relative;">
										<div id="VL_banList_Invoice"></div>
									</td>
								</tr>
								
								<tr>
									<td width="30%">
										Line Of Business
									</td>
									<td>
										<div class="select1">
											<s:select id="form0_searchInvoiceVO_lineOfBusiness" name="searchInvoiceVO.lineOfBusiness" list="lineOfBusiness" headerKey="all"
													headerValue="All" listKey="key" listValue="value" cssStyle="width:150px"/>
										</div>
									</td>
								</tr>
								
							</table>
						</div>
					</td>
					<td class="tabDetailViewDL">
						<div class="searchItemPanel">
							<table width="100%"  cellspacing="2" cellpadding="0" border="0">
								<tr>
									<td colspan="3" height="25">
										Invoice Date &nbsp;&nbsp;<input type="checkbox" id="__invoice_date" name="lockBox" class="ClearCheckbox" onclick="clearDisabled('__invoice_date',1);"/>
									</td>
								</tr>
								<tr>
									<td width="11%">
										<input type="radio" class="noBorderRadioButton" name="selectInvoiceDateRadio" checked="checked" value="DATEFRAME">
									</td>
									<td width="17%">
										From
									</td>
									<td nowrap="nowrap">
										<input id="searchInvoiceVO_invoiceDateStartsOn" name="searchInvoiceVO.invoiceDateStartsOn" cssClass="validate-date-ca" class="Clear1 Date" disabled="disabled" style="background:#ccc;width:152px;" onfocus="changRadio(0)"/>
										<a onClick="if($('#__invoice_date')[0].checked) g_Calendar.show(event,'searchInvoiceVO_invoiceDateStartsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;" href="javascript:%20void(0);"><img
												src="include/images/cal.gif" id="invoiceDateStartsOnImg" border="0" align="middle">
										</a> yyyy/mm/dd
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td>
										To
									</td>
									<td>
										<input id="searchInvoiceVO_invoiceDateEndsOn" name="searchInvoiceVO.invoiceDateEndsOn" cssClass="validate-date-ca" class="Clear1 Date" disabled="disabled" style="background:#ccc;width:152px;" onfocus="changRadio(0)"/>
										<a onClick="if($('#__invoice_date')[0].checked) g_Calendar.show(event,'searchInvoiceVO_invoiceDateEndsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;" href="javascript:%20void(0);"><img
												src="include/images/cal.gif" id="invoiceDateEndsOnImg" border="0" align="middle" >
										</a> yyyy/mm/dd
									</td>
								</tr>
								<tr>
									<td>
										<input type="radio" class="noBorderRadioButton" name="selectInvoiceDateRadio" value="WITHINPAST">
									</td>

									<td nowrap="nowrap">
										Past
									</td>
									<td nowrap="nowrap">
										<input id="form0_searchInvoiceVO_invoiceDateWiPastNumOfDays" name="searchInvoiceVO.invoiceDateWiPastNumOfDays" cssClass="validate-number" class="Clear1 Date" disabled="disabled" style="background:#ccc;width:152px;" onfocus="changRadio(1)"/>
										days(0-10000)
									</td>
								</tr>
							</table>
						</div>
					</td>

				</tr>
				<tr>
					<td class="tabDetailViewDL left-part">
						<div class="searchItemPanel">
							<table width="100%"  cellspacing="4" cellpadding="0" border="0">
								<tr>
									<td width="30%">
										Analyst
									</td>
									<td>
										<span class="select1"> <s:select name="searchInvoiceVO.analystId" headerKey="all" headerValue="All" list="analystList" listKey="key" listValue="value"
												required="true" cssStyle="width:150px" />
										</span>
									</td>
								</tr>
								<tr>
									<td>
										Payment Status
									</td>
									<td>
										<span class="select1"> <s:select name="searchInvoiceVO.paymentStatusId" headerKey="all" headerValue="All" list="paymentStatusList" listKey="key"
												listValue="value" required="true" cssStyle="width:150px" />
										</span>
									</td>
								</tr>
								<tr>
									<td>
										Invoice Status
									</td>
									<td>
										<span class="select1"> <s:select name="searchInvoiceVO.statusId" headerKey="all" headerValue="All" list="invoiceStatusList" listKey="key" listValue="value"
												required="true" cssStyle="width:150px" /> </span>
									</td>
								</tr>
							</table>
						</div>
					</td>
					<td class="tabDetailViewDL">
						<div class="searchItemPanel">

							<table width="100%" cellpadding="0" cellspacing="2" border="0">
								<tr>
									<td colspan="3" height="25">
										Payment Date &nbsp;&nbsp;<input type="checkbox" id="__payment_date" name="lockBox" class="ClearCheckbox" onclick="clearDisabled('__payment_date',2);"/>
									</td>
								</tr>
								<tr>
									<td width="11%">
										<input type="radio" class="noBorderRadioButton" name="selectPaymentDateRadio" checked="checked" value="DATEFRAME">
									</td>
									<td width="17%">
										From
									</td>
									<td nowrap="nowrap">
										<input id="searchInvoiceVO_paymentDateStartsOn" name="searchInvoiceVO.paymentDateStartsOn" cssClass="validate-date-ca" class="Clear2 Date" disabled="disabled" style="background:#ccc;width:152px;" onfocus="changRadio(5)"/>
										<a onClick="if($('#__payment_date')[0].checked) g_Calendar.show(event,'searchInvoiceVO_paymentDateStartsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;" href="javascript:%20void(0);"><img
												src="include/images/cal.gif" id="paymentDateStartsOnImg" border="0" align="middle">
										</a> yyyy/mm/dd
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td>
										To
									</td>
									<td nowrap="nowrap">
										<input id="searchInvoiceVO_paymentEndsOn" name="searchInvoiceVO.paymentDateEndsOn" cssClass="validate-date-ca" class="Clear2 Date" disabled="disabled" style="background:#ccc;width:152px;" onfocus="changRadio(5)"/>	
										<a onClick="if($('#__payment_date')[0].checked) g_Calendar.show(event,'searchInvoiceVO_paymentEndsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;" href="javascript:%20void(0);"><img
												src="include/images/cal.gif" id="paymentEndsOnImg" border="0" align="middle">
										</a> yyyy/mm/dd
									</td>
								</tr>
								<tr>
									<td>
										<input type="radio" class="noBorderRadioButton" name="selectPaymentDateRadio" value="WITHINPAST">
									</td>
									<td nowrap="nowrap">
										Past
									</td>
									<td nowrap="nowrap">
										<input id="form0_searchInvoiceVO_paymentDateWiPastNumOfDays" name="searchInvoiceVO.paymentDateWiPastNumOfDays" cssClass="validate-number" class="Clear2 Date" disabled="disabled" style="background:#ccc;width:152px;" onfocus="changRadio(6)"/>	
										days(0-10000)
										<input type="submit" value="ok" style="display: none;" />
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td class="tabDetailViewDL left-part">
						<div class="searchItemPanel">
							<table width="100%"  cellspacing="2" cellpadding="0" border="0">
								<tr>
									<td width="30%" nowrap="nowrap">
										Days in Status More Than
									</td>
									<td>
										<s:textfield name="searchInvoiceVO.daysInStatus" cssClass="validate-number" cssStyle="width:150px"></s:textfield>
									</td>
								</tr>
								<tr>
									<td>
										Invoice Number
									</td>
									<td>
										<s:textfield name="searchInvoiceVO.invoiceNumber" cssClass="validate-alp" cssStyle="width:150px"></s:textfield>
									</td>
								</tr>
								<tr>
									<td>
										Payment Reference
									</td>
									<td>
										<s:textfield name="searchInvoiceVO.paymentReference" cssClass="validate-alp" cssStyle="width:150px"></s:textfield>
									</td>
								</tr>
								<tr>
									<td>
										Historical Invoice
									</td>
									<td>
										<input type="checkbox" id="__historical_invoice" name="lockBox" class="ClearCheckbox" />
									</td>
								</tr>
							</table>

						</div>
					</td>
					<td class="tabDetailViewDL">
						<div class="searchItemPanel" style="visibility:hidden">

							<table width="100%" cellpadding="0" cellspacing="2" border="0">
								<tr>
									<td colspan="3" height="25">
										Invoice Due Date &nbsp;&nbsp;<input type="checkbox" id="__invoice_due_date" name="lockBox" class="ClearCheckbox"  onclick="clearDisabled('__invoice_due_date',3);"/>
									</td>
								</tr>
								<tr>
									<td width="11%">
										<input type="radio" class="noBorderRadioButton" name="selectInvoiceDueRadio" checked="checked" value="DATEFRAME">
									</td>
									<td width="17%">
										From
									</td>
									<td nowrap="nowrap">
										<input id="searchInvoiceVO_invoiceDueStartsOn" name="searchInvoiceVO.invoiceDueStartsOn" cssClass="validate-date-ca" class="Clear3 Date" disabled="disabled" style="background:#ccc;width:152px;" onfocus="changRadio(2)"/>	
										<a onClick="if($('#__invoice_due_date')[0].checked) g_Calendar.show(event,'searchInvoiceVO_invoiceDueStartsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;" href="javascript:%20void(0);"><img
												src="include/images/cal.gif" id="invoiceDueStartsOnImg" border="0" align="middle">
										</a> yyyy/mm/dd
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td>
										To
									</td>
									<td nowrap="nowrap">
										<input id="searchInvoiceVO_invoiceDueEndsOn" name="searchInvoiceVO.invoiceDueEndsOn" cssClass="validate-date-ca" class="Clear3 Date" disabled="disabled" style="background:#ccc;width:152px;" onfocus="changRadio(2)"/>	
										<a onClick="if($('#__invoice_due_date')[0].checked) g_Calendar.show(event,'searchInvoiceVO_invoiceDueEndsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;" href="javascript:%20void(0);"><img
												src="include/images/cal.gif" id="invoiceDueEndsOnImg" border="0" align="middle">
										</a> yyyy/mm/dd
									</td>

								</tr>
								<tr>
									<td>
										<input type="radio" class="noBorderRadioButton" name="selectInvoiceDueRadio" value="WITHINPAST">
									</td>
									<td nowrap="nowrap">
										Past
									</td>
									<td nowrap="nowrap">
										<input id="form0_searchInvoiceVO_invoiceDueWiPastNumOfDays" name="searchInvoiceVO.invoiceDueWiPastNumOfDays" cssClass="validate-number" class="Clear3 Date" disabled="disabled" style="background:#ccc;width:152px;" onfocus="changRadio(3)"/>	
										days(0-10000)
									</td>
								</tr>
								<tr>
									<td>
										<input type="radio" class="noBorderRadioButton" name="selectInvoiceDueRadio" value="WITHINNEXT">
									</td>
									<td nowrap="nowrap">
										Next
									</td>
									<td nowrap="nowrap">
										<input id="form0_searchInvoiceVO_invoiceDueWiNextNumOfDays" name="searchInvoiceVO.invoiceDueWiNextNumOfDays" cssClass="validate-number" class="Clear3 Date" disabled="disabled" style="background:#ccc;width:152px;" onfocus="changRadio(4)"/>	
										days(0-10000)
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</s:form>
	</div>
	<div style="width:100%; height:35px; overflow:hidden;">
		<div style="float: left; margin-top:10px;">
			<input type="button" value="Search" onclick="startSearch();" />
			&nbsp;
			<input type="button" id="saveQueryButton" value="Save" onclick="YAHOO.ccm.container.saveQueryDialog.show();" />
			&nbsp;
			<input type="button" value="Clear" onclick="javascript:resetFormElementValue();" />
		</div>
		<div id="loadingImgDiv" style="display: none; float: right;">
			<img src="include/images/loading.gif" />
		</div>
	</div>
	<div id="_gridDiv" style="width:100%;display:none"><br/>
		<table border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed; width: 100%;">
			<tr>
				<td>
					<div align="left" style="width:100%; overflow-x: auto; padding-bottom: 20px;">
						<div id="_dataTable"></div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="_dataTable_page"></div>
					<div style="margin-top: -21px; margin-left: 250px; padding-left: 30px;">
						<input type="button" id="saveExcel" value="Download to Excel" onclick="saveExcel();" style="display: none;" />
					</div>
				</td>
			</tr>
		</table>
	</div>
</div>
<div class="InSePoput" style="display: none;" >
	<jsp:include page="invoiceLabelMainPanel.jsp"/> 
</div>

<div class="yui-pe-content" id="saveQueryDialog">
	<div class="hd">
		Please type in query name
	</div>
	<div class="bd">
		<table width="95%" align="center" border=0>
			<tr>
				<td style="color: 000">
					Query Name:
				</td>
				<td>
					<input type="text" name="queryName" id="queryName" class="required">
				</td>
			</tr>
		</table>
	</div>
</div>