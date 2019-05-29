<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" type="text/css" href="include/javascript/flexbox/css/jquery.flexbox.css"><link>
<script type="text/javascript" src="include/javascript/flexbox/js/jquery.flexbox.js"></script>
<script type="text/javascript" src="include/javascript/yui2/tabview-min.js"></script>
<script src="include/javascript/ccm/ban/common.js" type="text/javascript" language="javascript"></script>
<script src="include/javascript/ccm/ban/banDetailPage.js" type="text/javascript" language="javascript"></script>


<script type="text/javascript">
var VL_Array = {
	"results": [
		<c:forEach items="${vendorList}" var="v">
		{
		    "id": "${v.key}",
		    "name": "${v.value}"
		},
		</c:forEach>
		{
		    "id": "",
		    "name": ""
		}
	],
	"total": ${fn:length(vendorList)}
};
$(function(){
	bdpVendorFlexbox = $('#bdpVendorListDiv').flexbox(VL_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 257,
		onSelect : function(){
			var vid = bdpVendorFlexbox.getValue();
			$("#banDetail_frm_b_vendor_id").val(vid);
			if(vid){
				banDetailPage_Json.selectVendorContact(vid);
			}
		}
	});
	bdpVendorFlexbox.setValue("${b.vendor.id}");
	$("#bdpVendorListDiv_input").attr("style","float:left;color:#666666;width:240px;height:19px;");
	
	$("#banDetail_frm_b_invoiceFrequency").val("${b.invoiceFrequency}");
	$("input").attr("disabled","true");
	$("textarea").attr("disabled","true");
	$("select").attr("disabled","true");
	$("select").attr("style","background-color:#EBEBE4;");
	$(".subTabs select").removeAttr("disabled");
	$(".subTabs select").attr("style","background-color:#ffffff;");
	$("textarea").attr("style","background-color:#EBEBE4;");
	$(".zhankai002 input").removeAttr("disabled");
	$(".zhankai002 textarea").removeAttr("disabled").removeAttr("style");
	$("#bdpVendorListDiv_arrow").hide();
	
});
</script>
<style type="text/css">
.CD_P{
	font-size:11px;
	width:390px;
	margin:10px 10px;
}
.cp_p_top{
	line-height:27px;
	padding-left:10px;
	color:#4e4d4d;
}
.cp_p_down{
	padding:10px 0 0px;
	line-height:23px;
	color:#4e4d4d;
}

.cp_p_down .lable{
	padding-top:1px;
}
.cp_p_down .lable .left{
	width:80px;
	padding-left:20px;
	float:left;
}

.cp_p_down .lable .right{
	float:left;
	padding-left:10px;
}

</style>
<s:hidden id="maxApprovalLevel" name="maxApprovalLevel"/>
<div id="pageContainer" class="tabForm" >
	<div id="adminTab" class="yui-navset">
		 <div class="yui-content">
			<div id="banTariff" style="display: none;">
				<table>
					<tr>
						<td>
							<table style="table-layout: fixed; width: 100%;">
								<tr>
									<td>
										<div align="left"
											style="width: 100%; overflow-x: auto; padding-bottom: 20px;">
											<div id="BanTarrfTable"></div>
										</div>

									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>

				<table border=0>
					<tr>
						<td width="240px" id="BanTarrf_pageTable" align="left"></td>
                                  <td width="150px" align="left"></td>
					</tr>
				</table>
				<input type="button" value="Upload Tariff & Contract" onclick="banDetailPage_Json.popupUploadBanTariffWindow();" />
			</div>
			
			<div id="banTab" >
				<h3 id="saveOrUpdateMessageH3"></h3>
				<s:form id="banDetail_frm" action="saveBanToBanDetailPage.action" theme="simple" 
						 enctype="multipart/form-data" method="POST" target="banDetailPageFrame">
					<table class="tabDetailView" border="0" width=100% cellpadding="5" cellspacing="0">
						<tr valign="top">
							<td class="tabDetailViewDL" width="34%">
								<div class="searchItemPanel">
									<table width="100%" cellspacing="3" cellpadding="0" border="0">
										<tr>
											<td width="36%"></td>
											<td>
												<s:hidden name="b.id"></s:hidden>
											</td>
										</tr>
										<tr>
											<td>Vendor Name</td>
											<td>
											 	<s:hidden name="b.vendor.id" cssClass="mustWrite"></s:hidden>
											 	<div id="bdpVendorListDiv"></div>&nbsp;<span class="iShowStar" style="font:15px;color:red;vertical-align:middle;"></span>	
											</td>
										</tr> 
										<tr>
											<td>Account Number</td>
											<td>
												<s:textfield name="b.accountNumber" cssClass="mustWrite" cssStyle="width:147px" ></s:textfield>&nbsp;&nbsp;<span class="iShowStar" style="font:15px;color:red;vertical-align:middle;"></span>	
												&nbsp;&nbsp;&nbsp;&nbsp;
												<font id="doValidationAccountExisResultDiv"></font>
											</td>
										</tr>
										<tr>
											<td width="35%">Original Account Number</td>
											<td>
												<s:textfield name="b.originalAccountNumber" cssStyle="width:147px"></s:textfield>
											</td>
										</tr>
									</table>
								</div>
							</td>
							<td class="tabDetailViewDL" width="33%" >
								<div class="searchItemPanel">
									<table width="100%" cellspacing="4" cellpadding="0" border="0">
										<tr>
											<td width="35%">BAN Status</td>
											<td>
												<span class="select1"><s:select name="b.banStatus.id" list="banStatus" headerKey="" cssClass="mustWrite" oninput="banDetailPage_Json.onDOMValidate();"
													headerValue="" listKey="key" listValue="value" cssStyle="width:150px"/></span>&nbsp;&nbsp;<span class="iShowStar" style="font:15px;color:red;vertical-align:middle;"></span>	
											</td>
										</tr> 
										<tr>
											<td>Line of Business </td>
											<td>
												<span class="select1"><s:select name="b.lineOfBusiness" list="lineofBbusiness" headerKey="" cssClass="mustWrite"
													headerValue="" listKey="key" listValue="value" cssStyle="width:150px"/></span>&nbsp;&nbsp;<span class="iShowStar" style="font:15px;color:red;vertical-align:middle;"></span>
												<s:label id="lineOfBusiness_value" name="b.lineOfBusiness" cssStyle="display:none"></s:label>
											</td>
										</tr>
										<tr>
											<td width="35%">Invoice Channel</td>
											<td>
												<span class="select1">
													<s:select id="banDetail_frm_b_invoiceChannel_id" name="b.invoiceChannel.id"  list="invoiceChannel" headerKey=""
													headerValue="" listKey="key" listValue="value" cssStyle="width:150px;" cssClass="mustWrite" />
													
													
												</span>&nbsp;&nbsp;<span class="iShowStar" style="font:15px;color:red;vertical-align:middle;"></span>	
												<s:label id="invoiceChannel_value" name="b.invoiceChannel.id" cssStyle="display:none"></s:label>
											</td>
										</tr>
										<tr>
											<td>Invoice Format</td>
											<td>
												<span class="select1">
												
<%--													<select id="banDetail_frm_b_invoiceFormat_id" name="b.invoiceFormat.id" style="width:150px;" class="mustWrite" disabled="disabled"></select>--%>
													<s:select id="banDetail_frm_b_invoiceFormat_id" name="b.invoiceFormat.id"  list="invoiceFormat" headerKey=""
													headerValue="" listKey="key" listValue="value" cssStyle="width:150px;" cssClass="mustWrite" />
													
												</span>&nbsp;&nbsp;<span class="iShowStar" style="font:15px;color:red;vertical-align:middle;"></span>	
												<s:label id="invoiceFormat_value" name="b.invoiceFormat.id" cssStyle="display:none"></s:label>
											</td>
										</tr>
										<tr>
											<td>Missing Invoice Email</td>
											<td>
												<span class="select1">
												    <s:select id="banDetail_frm_b_missingEmailflag_id" name="b.missingEmailflag" cssStyle="width:150px;" cssClass="mustWrite" list="#{'Y':'Y','N':'N'}" listKey="key" listValue="value"></s:select>
												</span>&nbsp;&nbsp;<span class="iShowStar" style="font:15px;color:red;vertical-align:middle;"></span>	
											</td>
										</tr>
										<tr>
											<td>Ban Inactive Reason</td>
											<td>
												<span class="select1">
												    <s:select name="b.banInactiveReason.id" list="banInactiveReason" headerKey="" 
												    headerValue="" listKey="key" listValue="value" cssStyle="width:150px"/>
												</span>
<%--												&nbsp;&nbsp;<span class="iShowStar" style="font:15px;color:red;vertical-align:middle;"></span>	--%>
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr valign="top">
							<td class="tabDetailViewDL" width="34%">
								<div class="searchItemPanel">
									<table width="100%" cellspacing="4" cellpadding="0" border="0">
										<tr>
											<td>Analyst</td>
											<td>
												<span class="select1"><s:select name="b.analystId" list="analyst" headerKey="" cssClass="mustWrite"
													headerValue="" listKey="key" listValue="value" cssStyle="width:150px"/></span>&nbsp;&nbsp;<span class="iShowStar" style="font:15px;color:red;vertical-align:middle;"></span>	
											</td>
										</tr>
										<tr>
											<td  width="36%">Approver1</td>
											<td>
												<span class="select1"><s:select name="b.approver1Id" list="approver1" headerKey="" cssClass="mustWrite"
													headerValue="" listKey="key" listValue="value" cssStyle="width:150px"/></span>&nbsp;&nbsp;<span id="approver1_star" class="iShowStar" style="font:15px;color:red;vertical-align:middle;"></span>	
											</td>
										</tr>
										<tr>
											<td>Approver2</td>
											<td>
												<span class="select1"><s:select name="b.approver2Id" list="approver2" headerKey="" cssClass="mustWrite"
													headerValue="" listKey="key" listValue="value" cssStyle="width:150px"/></span>&nbsp;&nbsp;<span id="approver2_star" class="iShowStar" style="font:15px;color:red;vertical-align:middle;"></span>	
											</td>
										</tr>
										<tr>
											<td>Approver3</td>
											<td>
												<span class="select1"><s:select name="b.approver3Id" list="approver3" headerKey="" cssClass="mustWrite"
													headerValue="" listKey="key" listValue="value" cssStyle="width:150px"/></span>&nbsp;&nbsp;<span id="approver3_star" class="iShowStar" style="font:15px;color:red;vertical-align:middle;"></span>	
											</td>
										</tr>
										<tr>
											<td>Approver4</td>
											<td>
												<span class="select1"><s:select name="b.approver4Id" list="approver4" headerKey="" cssClass="mustWrite"
													headerValue="" listKey="key" listValue="value" cssStyle="width:150px"/></span>&nbsp;&nbsp;<span id="approver4_star" class="iShowStar" style="font:15px;color:red;vertical-align:middle;"></span>	
											</td>
										</tr>
										<tr>
											<td>Level 1 Approval Flag</td>
											<td>
												<span class="select1"><s:select name="b.level1ApprovalFlag" list="#{'N':'N','Y':'Y'}"  cssClass=""
													 listKey="key" listValue="value" cssStyle="width:150px"/>
												</span>
											</td>
										</tr>
										<tr>
											<td>Max Approval Level</td>
											<td>
												<s:textfield name="b.maxApprovalLevel" onkeyup="banDetailPage_Json.itemValidate(this,2);" maxlength="2" cssStyle="width:150px"></s:textfield>
												Example:1-4
											</td>
										</tr>
									</table>
								</div>
							</td>
							<td class="tabDetailViewDL" width="33%" >
								<div class="searchItemPanel">
									<table width="100%" cellspacing="4" cellpadding="0" border="0">
										<tr>
											<td width="35%">Invoice Frequency</td>
											<td>
												<span class="select1">
													<select id="banDetail_frm_b_invoiceFrequency" name="b.invoiceFrequency" style="width:150px" class="mustWrite">
														<option value=""></option>
														<option value="W">Weekly</option>
														<option value="S">Semi-monthly</option>
														<option value="M">Monthly</option>
														<option value="B">Bi-monthly</option>
														<option value="Q">Quarterly</option>
														<option value="Y">Yearly</option>
														<option value="H">Half-yearly</option>
														<option value="V">Various</option>
													</select>
												</span>&nbsp;&nbsp;<span class="iShowStar" style="font:15px;color:red;vertical-align:middle;"></span>
											</td>
										</tr>
										<tr>
											<td>Currency</td>
											<td>
												<span class="select1"><s:select name="b.currency.id" list="currency" headerKey="" cssClass="mustWrite"
													headerValue="" listKey="key" listValue="value" cssStyle="width:150px"/></span>&nbsp;&nbsp;<span class="iShowStar" style="font:15px;color:red;vertical-align:middle;"></span>	
											</td>
										</tr>
										<tr>
											<td>Mib Flag</td>
											<td>
												<span class="select1"><s:select name="b.mibFlag" list="nyList" cssClass="mustWrite"
												 listKey="key" listValue="value" cssStyle="width:150px"/></span>
											</td>
										</tr>
										<tr>
											<td>Created Ban Flag</td>
											<td>
												<span class="select1"><s:select name="b.createdBanFlag" list="nyList" cssClass="mustWrite"
												 listKey="key" listValue="value" cssStyle="width:150px"/></span>
											</td>
					
										</tr>
										<tr>
											<td>Autopay Flag</td>
											<td>
												<span class="select1"><s:select name="b.autopayFlag" list="nyList" cssClass="mustWrite"
												 listKey="key" listValue="value" cssStyle="width:150px"/></span>
<%--												<span class="select1"><s:select name="b.autopayFlag" list="nyList" cssClass="mustWrite" oninput="banDetailPage_Json.onAutopayChange();"--%>
<%--												 listKey="key" listValue="value" cssStyle="width:150px"/></span>--%>
											</td>
											
										</tr>
<%--										<tr id="AutoPayTR" style="display:display">--%>
<%--											<td>Autopay Max Count</td>--%>
<%--											<td>--%>
<%--											<span class="select1"><s:select name="b.autopayMaxCount" list="autoPayCountList" listKey="key" listValue="value" cssStyle="width:150px"/></span>--%>
<%--											</td>--%>
<%--											--%>
<%--										</tr>--%>
										<tr>
											<td>Manual Adjustment Approval Flag</td>
											<td>
												<span class="select1"><s:select name="b.manualAdjustmentApprovalFlag" list="mnList" listKey="key" listValue="value" cssStyle="width:150px"/></span>
											</td>
											
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr valign="top">
							<td class="tabDetailViewDL">
								<div class="searchItemPanel">
									<table width="100%" cellspacing="3" cellpadding="0" border="0">
										<tr>
											<td><b>SCOA</b></td>
										</tr>
										<tr>
											<td>SCOA Code Type</td>
											<td>
												<%--<select id="banDetail_frm_b_scoaCodeType" class="mustWrite" name="b.scoaCodeType" style="width:150px;">
													
												</select>
												<span class="iShowStar" style="font:15px;color:red;vertical-align:middle;"></span>
												<s:label id="scoaCodeType_value" name="b.scoaCodeType" cssStyle="display:none"></s:label>
											--%>
												<span class="select1"><s:select name="b.scoaCodeType" list="scoaCodeTypeList" cssClass="mustWrite"
													 listKey="key" listValue="value" cssStyle="width:150px"/></span>&nbsp;&nbsp;<span class="iShowStar" style="font:15px;color:red;vertical-align:middle;"></span>
												<s:label id="scoaCodeTypeList_value" name="b.scoaCodeType" cssStyle="display:none"></s:label>
											</td>
										</tr>
										<tr>
											<td  width="36%">Company</td>
											<td>
												<s:textfield name="b.company" onkeyup="banDetailPage_Json.scoaValidate(this,3);" maxlength="3" cssStyle="width:150px"></s:textfield>
												Example:000
											</td>
										</tr>
										<tr>
											<td>Location</td>
											<td>
												<s:textfield name="b.location" onkeyup="banDetailPage_Json.scoaValidate(this,4);" maxlength="4" cssStyle="width:150px"></s:textfield>
												Example:0000
											</td>
										</tr>
										<tr>
											<td>Department</td>
											<td>
												<s:textfield name="b.department" onkeyup="banDetailPage_Json.scoaValidate(this,4);" maxlength="4" cssStyle="width:150px"></s:textfield>
												Example:0000
											</td>
										</tr>
										<tr>
											<td>Channel</td>
											<td>
												<s:textfield name="b.channel" onkeyup="banDetailPage_Json.scoaValidate(this,3);" maxlength="3" cssStyle="width:150px"></s:textfield>
												Example:000
											</td>
										</tr>
									</table>
								</div>
							</td>
							<td class="tabDetailViewDL" width="33%" >
								<div class="searchItemPanel">
									<table width="100%" cellspacing="3" cellpadding="0" border="0">
										<tr>
											<td width="35%" nowrap="nowrap">AP Supplier Number</td>
											<td>
												<s:textfield name="b.apSupplierNumber" cssClass="mustWrite" cssStyle="width:147px"></s:textfield>&nbsp;&nbsp;<span class="iShowStar" style="font:15px;color:red;vertical-align:middle;"></span>	
											</td>
										</tr>
										<tr>
											<td>AP Supplier Name</td>
											<td>
												<s:textfield name="b.apSupplierName" cssStyle="width:147px"></s:textfield>
											</td>
										</tr>
										<tr>
											<td>AP Supplier Site</td>
											<td>
												<s:textfield name="b.apSupplierSite" cssClass="mustWrite" cssStyle="width:147px"></s:textfield>&nbsp;&nbsp;<span class="iShowStar" style="font:15px;color:red;vertical-align:middle;"></span>	
											</td>
										</tr>
										
										<tr>
											<td>AP Customer Name</td>
											<td>
												<s:textfield name="b.apCustomerName" cssStyle="width:147px"></s:textfield>
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td class="tabDetailViewDL" colspan="2" >
								<div class="searchItemPanel">
									<table width="99%" align="left" cellspacing="4" cellpadding="0" border="0">
										<tr>
											<td width="18%">BAN Primary Contact</td>
											<td>
											 <span class="select1">
											 	<select class="mustWrite Contact" id="sBanPrimiaryContactId" name="b.banPrimiaryContactId" style="width:363px;"></select>
											 </span>&nbsp;&nbsp;<span class="iShowStar" style="font:15px;color:red;vertical-align:middle;"></span>	
											<script type="text/javascript">
												banDetailPage_Json.contactArr.push({id:"sBanPrimiaryContactId",value:"${b.banPrimiaryContactId}"});
											</script>
											</td>
										</tr>
										<tr>
											<td nowrap="nowrap">BAN Payment Contact</td>
											<td>
											<span class="select1"><select class="Contact" id="sBanPaymentContactId" name="b.banPaymentContactId" style="width:363px;"></select></span>
											<script type="text/javascript">
												banDetailPage_Json.contactArr.push({id:"sBanPaymentContactId",value:"${b.banPaymentContactId}"});
											</script>
											</td>
										</tr>
										<tr>
											<td>BAN Dispute Contact</td>
											<td>
											 <span class="select1"><select id="sBanDisputeContactId" name="b.banDisputeContactId" class="mustWrite Contact" style="width:363px;"></select>
											 </span>&nbsp;&nbsp;<span class="iShowStar" style="font:15px;color:red;vertical-align:middle;"></span>	
											<script type="text/javascript">
												banDetailPage_Json.contactArr.push({id:"sBanDisputeContactId",value:"${b.banDisputeContactId}"});
											</script>
											</td>
										</tr>
										<tr>
											<td>BAN Tariff Contact</td>
											<td>
											<span class="select1"><select class="Contact" id="sBanTariffContactId" name="b.banTariffContactId"style="width:363px;"></select></span>
											<script type="text/javascript">
												banDetailPage_Json.contactArr.push({id:"sBanTariffContactId",value:"${b.banTariffContactId}"});
											</script>
											</td>
										</tr>
										<tr>
											<td>BAN Billing Contact</td>
											<td>
											 <span class="select1"><select class="Contact" id="sBanBillingContactId" name="b.banBillingContactId"style="width:363px;"></select></span>
											<script type="text/javascript">
												banDetailPage_Json.contactArr.push({id:"sBanBillingContactId",value:"${b.banBillingContactId}"});
											</script>
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td class="tabDetailViewDL" width="34%">
								<div class="searchItemPanel">
									<table width="100%" cellspacing="3" cellpadding="0" border="0">
										<tr>
											<td>Account Type</td>
											<td>
												<span class="select1"><s:select name="b.accountType.id" list="accountType" headerKey=""
													headerValue="" listKey="key" listValue="value"/></span>
											</td>
										</tr>
										<tr>
											<td width="36%">Usage Backbill Months</td>
											<td>
												<s:textfield name="b.usageBackbillMonths" cssStyle="width:147px"></s:textfield>
											</td>
										</tr>
										<tr>
											<td>Bill Day</td>
											<td>
												<s:textfield name="b.billDay" cssStyle="width:147px"></s:textfield>
											</td>
										</tr>
										<tr>
											<td>Days To Due</td>
											<td>
												<s:textfield name="b.daysToDue" cssStyle="width:147px"></s:textfield>
											</td>
										</tr>
									</table>
								</div>
							</td>
							<td class="tabDetailViewDL" width="33%" >
								<div class="searchItemPanel">
									<table width="100%" cellspacing="4" cellpadding="0" border="0">
										<tr>
											<td  width="35%">Payment Method</td>
											<td>
												<span class="select1"><s:select name="b.paymentMethod.id" list="paymentMethod" headerKey=""
													headerValue="" listKey="key" listValue="value" cssStyle="width:150px"/></span>
											</td>
										</tr>
										<tr>
											<td>Payment Term</td>
											<td>
												<span class="select1"><s:select name="b.paymentTerm.id" list="paymentTerm" headerKey=""
													headerValue="" listKey="key" listValue="value" cssStyle="width:150px"/></span>
											</td>
										</tr>
										<tr>
											<td>Payment Group</td>
											<td>
												<span class="select1"><s:select name="b.paymentGroup.id" list="paymentGroup" headerKey=""
													headerValue="" listKey="key" listValue="value" cssStyle="width:150px"/></span>
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr valign="top">
							<td class="tabDetailViewDL">
								<div class="searchItemPanel">
									<table width="100%" cellspacing="3" cellpadding="0" border="0">
										<tr>
											<td valign="top" width="36%">Notes</td>
											<td>
												<s:textarea name="b.notes" cssStyle="overflow:auto ;" rows="5" cols="50"></s:textarea>
											</td>
										</tr>
										<tr>
											<td valign="top" width="36%">Notice</td>
											<td>
												<s:textarea cssStyle="overflow:auto ;" rows="5" cols="50" name="b.notice"></s:textarea>
											</td>
										</tr>
									</table>
								</div>
							</td>
							<td class="tabDetailViewDL">
								<div class="searchItemPanel">
									<table width="100%" cellspacing="4" cellpadding="0" border="0">
										<tr>
											<td>BAN Created Date</td>
											<td>
												<fmt:formatDate value="${b.banCreateDate}" pattern="yyyy/MM/dd"/>  
											</td>
										</tr>
										<tr>
											<td>BAN Close Date </td>
											<td>
												<fmt:formatDate value="${b.banCloseDate}" pattern="yyyy/MM/dd"/>  
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
					</table>
				</s:form>
				<iframe name="banDetailPageFrame" id="banDetailPageFrame" style="display: none" src="javascript:false"></iframe>
			</div>
			
			<form id="downloadForm" action="download.action" method="post" target="__downloadIframe"
				style="display: none;">
				<input type="hidden" name="filePath" />
				<input type="hidden" name="fileName" />
			</form>
			
			<div id="uploadTariffWindow">
				<div class="hd">
					<h2>
						Attach Tariff & Contract
					</h2>
				</div>
				<div class="bd">
					<div class="CD_P">
						<div class="cp_p_down">
						<form id="uploadForm" action="doUploadAttachFileByBan.action" enctype="multipart/form-data" method="POST" target="banDetailPageFrame">
							<s:hidden name="banId"></s:hidden>
							<table width="95%" align="center" style="margin: auto;">
				<tr>
				<td>
							Attach File:
				</td>
					<td>
						<div id = "addFile" style="overflow-y:auto;height:100px;position:relative;vertical-align: top;">
						<div id ="ContractFile1" style="padding:3px 0 0 0;height: 19px;" >	
							<div style="float:left;">
						    <input style="height:19px;width:195px;" type='text' id="_upload_text_attachment_01" disabled="disabled" class="upload_text">
       					    </div>	
							<div style="float:left; margin:0 0 0 2px">
				        		<span class="ccm_upload_img">
        					<input onchange="document.getElementById('_upload_text_attachment_01').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
        					<input style="height:19px;width:65px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>	   						
				        		</span>
			        		</div>
			        		<span class="approveicon" onclick="deletefile('ContractFile1');"></span>
			        		<div class="clear" ></div>
		        		</div>
		        		<div id ="ContractFile2" style="padding:3px 0 0 0;height: 19px;" >	
							<div style="float:left;">
						    <input style="height:19px;width:195px;" type='text' id="_upload_text_attachment_02" disabled="disabled" class="upload_text">
       					    </div>	
							<div style="float:left; margin:0 0 0 2px">
				        		<span class="ccm_upload_img">
        					<input onchange="document.getElementById('_upload_text_attachment_02').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
        					<input style="height:19px;width:65px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>	   						
				        		</span>
			        		</div>
			        		<span class="approveicon" onclick="deletefile('ContractFile2');"></span>
			        		<div class="clear" ></div>
		        		</div>
		        		<div id ="ContractFile3" style="padding:3px 0 0 0;height: 19px;" >	
							<div style="float:left;">
						    <input style="height:19px;width:195px;" type='text' id="_upload_text_attachment_03" disabled="disabled" class="upload_text">
       					    </div>	
							<div style="float:left; margin:0 0 0 2px">
				        		<span class="ccm_upload_img">
        					<input onchange="document.getElementById('_upload_text_attachment_03').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
        					<input style="height:19px;width:65px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>	   						
				        		</span>
			        		</div>
			        		<div class="approveicon" onclick="deletefile('ContractFile3');"></div>
			        		<div class="clear" ></div>
		        		</div>
		        		</div>
		        		</td>
		        		<tr>
					    <td>
							Notes:
					    </td>
					    <td>
					    <textarea id="uploadNotes" name="uploadNotes" cols="" rows=""
							style="width: 268px; height: 50px;"></textarea>
					    </td>
					    </tr>
		        		<tr>
					    <td>
							&nbsp;
					    </td>
					    <td>
					    <div class="lable"
					    style="padding-top: 10px; text-align: right; padding-right: 38px;">
					    <input style="height: 20px;text-align:center; padding:0 5px;" type="button" value="Add" onclick="banDetailPage_Json.addUploadForm()"/>
								<input style="height: 20px;" type="button" value="Submit" onclick="banDetailPage_Json.submitUploadForm()"/>
								&nbsp;
								<input style="height: 20px;" type="button" value="Cancel" onclick="banDetailPage_Json.cancelUploadForm()"/>
				        </div>
					    </td>
					   </tr>
				</tr>
				</table>
							<!--<div class="lable">
								<Div class="left">
									Attach File:
								</Div>
								<div class="right">
									<input style="height:19px;width:195px;" type='text' id="_upload_text_attachment_01" disabled="disabled" class="upload_text">
			       					<span class="ccm_upload_img">
			        					<input onchange="document.getElementById('_upload_text_attachment_01').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
			        					<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
			       					</span>
								</div>
							</div>
							<div class="lable">
								<Div class="left">
									Attach File:
								</Div>
								<div class="right">
									<input style="height:19px;width:195px;" type='text' id="_upload_text_attachment_02" disabled="disabled" class="upload_text">
			       					<span class="ccm_upload_img">
			        					<input onchange="document.getElementById('_upload_text_attachment_02').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
			        					<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
			       					</span>
								</div>
							</div>
							<div class="lable">
								<Div class="left">
									Attach File:
								</Div>
								<div class="right">
									<input style="height:19px;width:195px;" type='text' id="_upload_text_attachment_03" disabled="disabled" class="upload_text">
			       					<span class="ccm_upload_img">
			        					<input onchange="document.getElementById('_upload_text_attachment_03').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
			        					<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
			       					</span>
								</div>
							</div>
							<div class="lable">
								<Div class="left">
									Attach File:
								</Div>
								<div class="right">
									<input style="height:19px;width:195px;" type='text' id="_upload_text_attachment_04" disabled="disabled" class="upload_text">
			       					<span class="ccm_upload_img">
			        					<input onchange="document.getElementById('_upload_text_attachment_04').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
			        					<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
			       					</span>
								</div>
							</div>
							<div class="lable">
								<Div class="left">
									Attach File:
								</Div>
								<div class="right">
									<input style="height:19px;width:195px;" type='text' id="_upload_text_attachment_05" disabled="disabled" class="upload_text">
			       					<span class="ccm_upload_img">
			        					<input onchange="document.getElementById('_upload_text_attachment_05').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
			        					<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
			       					</span>
								</div>
							</div>
							<div class="lable">
								<Div class="left">
									Notes:
								</Div>
								<div class="right">
									<textarea id="uploadNotes" name="uploadNotes" cols="" rows=""
										style="width: 272px; height: 50px;"></textarea>
								</div>
							</div>
							<div class="lable"
								style="padding-top: 10px; text-align: right; padding-right: 38px;">
								<input style="height: 20px;" type="button" value="Submit" onclick="banDetailPage_Json.submitUploadForm()"/>
								&nbsp;
								<input style="height: 20px;" type="button" value="Cancel" onclick="banDetailPage_Json.cancelUploadForm()"/>
							</div>
						--></form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<iframe id="__downloadIframe" style="display: none;"></iframe>