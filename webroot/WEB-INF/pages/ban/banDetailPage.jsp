<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.saninco.ccm.util.SystemUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<meta name="description" content="BAN Info 维护页面">
<link rel="stylesheet" type="text/css"
	href="include/javascript/Calendar/styles/calendar.css">
<link rel="stylesheet" type="text/css"
	href="include/javascript/Calendar/styles/calendar.css">
<script type="text/javascript"
	src="include/javascript/Calendar/simplecalendar.js"></script>
<link rel="stylesheet" type="text/css" href="include/javascript/flexbox/css/jquery.flexbox.css"><link>
<script type="text/javascript" src="include/javascript/flexbox/js/jquery.flexbox.js"></script>
<script type="text/javascript" src="include/javascript/yui2/tabview-min.js"></script>
<script src="include/javascript/ccm/ban/common.js" type="text/javascript" language="javascript"></script>
<script src="include/javascript/ccm/ban/banDetailPage.js" type="text/javascript" language="javascript"></script>
<style type="text/css"> 
.word-required {
	vertical-align: top;
	margin-left: 5px;
	color: red;
	font-size: 16px;
}
</style> 

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
var infoData;
var currentUserId = '<%=SystemUtil.getCurrentUserId()%>';
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
	
	$("#banDetail_frm_b_invoiceFrequency").val("${b.invoiceFrequency}");
	infoData = $("#banDetail_frm").serializeArray();
	banDetailPage_Json.onDOMValidate();
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
<s:hidden id="isApprove" name="isApprove"/>
<div id="pageContainer" class="tabForm" >
	<h3 id="banTitle"><b>Admin</b></h3>
	<div id="adminTab" class="yui-navset"">
		<ul class="yui-nav" id="adminTabMenu" style="display:none">
		<sec:authorize ifAllGranted="FUNCTION_4100">
			<li onclick="activeIndexClick(0)">
				<a href="#userListTab"><em>User</em> </a>
			</li>
		</sec:authorize>
		<sec:authorize ifAllGranted="FUNCTION_4200">
			<li onclick="activeIndexClick(1)">
				<a href="#roleTab"><em>Role</em> </a>
			</li>
		</sec:authorize>
		<sec:authorize ifAllGranted="FUNCTION_4500">
			<li onclick="activeIndexClick(2)">
				<a href="#reportAdminTab"><em>Report Admin</em> </a>
			</li>
		</sec:authorize>
		<sec:authorize ifAllGranted="FUNCTION_4400">
			<li onclick="activeIndexClick(3)">
				<a href="#transactionTab"><em>Transaction</em> </a>
			</li>
		</sec:authorize>
		<sec:authorize ifAllGranted="FUNCTION_4700">
			<li onclick="activeIndexClick(4)">
				<a href="#newScoaCodeTab"><em>SCOA Code</em> </a>
			</li>
		</sec:authorize>
		<sec:authorize ifAllGranted="FUNCTION_4800">
			<li onclick="activeIndexClick(5)">
				<a href="#vendorTab"><em>Vendor Maintenance</em> </a>
			</li>
		</sec:authorize>
		<sec:authorize ifAnyGranted="FUNCTION_4900">
			<li onclick="activeIndexClick(6)" class="selected">
				<a href="#banTab"><em>BAN Maintenance</em> </a>
			</li>
		</sec:authorize>
		<sec:authorize ifAllGranted="FUNCTION_4110">
			<li onclick="activeIndexClick(7)">
				<a href="#teamTab"><em>Team Maintenance</em> </a>
			</li>
		</sec:authorize>	
		<sec:authorize ifAllGranted="FUNCTION_4120">
			<li onclick="activeIndexClick(8)">
				<a href="#modifyAdjustmentTab"><em>Modify Adjustment</em> </a>
			</li>
		</sec:authorize>
		<sec:authorize ifAllGranted="FUNCTION_4130">
			<li onclick="activeIndexClick(9)" >
				<a href="#securityIp"><em>Team Security IP</em> </a>
			</li>
		</sec:authorize>
		<sec:authorize ifAllGranted="FUNCTION_4140">
			<li onclick="activeIndexClick(10)" >
				<a href="#wikiManagement"><em>Wiki Management</em> </a>
			</li>
		</sec:authorize>
		</ul>
		 <div class="yui-content">
		 <div style="display:none;"></div>
		 <div style="display:none;"></div>
		 <div style="display:none;"></div>
		 <div style="display:none;"></div>
		 <div style="display:none;"></div>
		 <div style="display:none;"></div>
			<div>
				<ul class="yui-nav" style="display:none">
					<li id="banInfoLi" title="active" Class="selected">
						<a id="banInfoLiTabA" href="#banInfoLiTabA"
							onclick="banDetailPage_Json.banInfoLiTab('A');"><em>Ban Info</em> </a>
					</li>
					<sec:authorize ifAllGranted="FUNCTION_4900">
						<li id="banTariffLi">
							<a id="banInfoLiTabB" href="#banInfoLiTabB" onclick="banDetailPage_Json.banInfoLiTab('B');"><em>Tariff & Contract</em> </a>
						</li>
						<li id="banExemptionCertificateLi">
							<a id="banInfoLiTabC" href="#banInfoLiTabC" onclick="banDetailPage_Json.banInfoLiTab('C');"><em>Exemption Certificate</em></a>
						</li>
					</sec:authorize>
				</ul>
			</div>
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
			
			<div id="banExemptionCertificate" style="display: none;">
				<table>
					<tr>
						<td>
							<table style="table-layout: fixed; width: 100%;">
								<tr>
									<td>
										<div align="left"
											style="width: 100%; overflow-x: auto; padding-bottom: 20px;">
											<div id="banExemptionCertificateTable"></div>
										</div>

									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>

				<table border=0>
					<tr>
						<td width="240px" id="BanExemptionCertificate_pageTable" align="left"></td>
                                  <td width="150px" align="left"></td>
					</tr>
				</table>
				<input type="button" value="Upload Certificate" onclick="banDetailPage_Json.popupUploadBanExemptionCertificateWindow();" />
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
												<s:hidden name="b.createdBy"></s:hidden>
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
												<button onclick="doValidationAccountExis($('#banDetail_frm_b_accountNumber').val()); return false;">Validation</button>
												<font id="doValidationAccountExisResultDiv"></font>
											</td>
										</tr>
										<tr>
											<td width="35%">Original Account Number</td>
											<td>
												<s:textfield name="b.originalAccountNumber" cssStyle="width:147px"></s:textfield>
											</td>
										</tr>										
										<tr>
											<td width="35%">Paper Account Number</td>
											<td>
												<s:textfield name="b.paperAccountNumber" cssStyle="width:147px"></s:textfield>
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
												<span class="select1">
													<s:select name="b.banStatus.id" list="banStatus" headerKey="" cssClass="mustWrite" onchange="banDetailPage_Json.onDOMValidate();"
													headerValue="" listKey="key" listValue="value" cssStyle="width:150px"/>
												</span>&nbsp;&nbsp;<span class="iShowStar" style="font:15px;color:red;vertical-align:middle;"></span>	
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
										<!-- External Approve Flag -->
										<!-- <select id="banDetail_frm_b_invoiceFrequency" name="b.invoiceFrequency" style="width:150px" class="mustWrite"> -->
										<tr>
											<td>External Approval Flag</td>
											<td>
												<span class="select1"><s:select name="b.externalApproveFlag" list="nyList" cssClass="mustWrite"
												 listKey="key" listValue="value" cssStyle="width:150px"/></span>
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
							<td class="tabDetailViewDL" width="34%" >
								<div class="searchItemPanel">
									<table width="99%" align="left" cellspacing="4" cellpadding="0" border="0">
										<tr>
											<td width="36%">BAN Primary Contact</td>
											<td>
											 <span class="select1" style="width: 85%;">
											 	<select class="mustWrite Contact" id="sBanPrimiaryContactId" name="b.banPrimiaryContactId" style="width:100%;"></select>
											 </span>&nbsp;&nbsp;<span class="iShowStar" style="font:15px;color:red;vertical-align:middle;"></span>	
											<script type="text/javascript">
												banDetailPage_Json.contactArr.push({id:"sBanPrimiaryContactId",value:"${b.banPrimiaryContactId}"});
											</script>
											</td>
										</tr>
										<tr>
											<td nowrap="nowrap">BAN Payment Contact</td>
											<td>
											<span class="select1" style="width: 85%;"><select class="Contact" id="sBanPaymentContactId" name="b.banPaymentContactId" style="width:100%;"></select></span>
											<script type="text/javascript">
												banDetailPage_Json.contactArr.push({id:"sBanPaymentContactId",value:"${b.banPaymentContactId}"});
											</script>
											</td>
										</tr>
										<tr>
											<td>BAN Dispute Contact</td>
											<td>
											 <span class="select1" style="width: 85%;"><select id="sBanDisputeContactId" name="b.banDisputeContactId" class="mustWrite Contact" style="width:100%;"></select>
											 </span>&nbsp;&nbsp;<span class="iShowStar" style="font:15px;color:red;vertical-align:middle;"></span>	
											<script type="text/javascript">
												banDetailPage_Json.contactArr.push({id:"sBanDisputeContactId",value:"${b.banDisputeContactId}"});
											</script>
											</td>
										</tr>
										<tr>
											<td>BAN Tariff Contact</td>
											<td>
											<span class="select1" style="width: 85%;"><select class="Contact" id="sBanTariffContactId" name="b.banTariffContactId"style="width:100%;"></select></span>
											<script type="text/javascript">
												banDetailPage_Json.contactArr.push({id:"sBanTariffContactId",value:"${b.banTariffContactId}"});
											</script>
											</td>
										</tr>
										<tr>
											<td>BAN Billing Contact</td>
											<td>
											 <span class="select1" style="width: 85%;"><select class="Contact" id="sBanBillingContactId" name="b.banBillingContactId"style="width:100%;"></select></span>
											<script type="text/javascript">
												banDetailPage_Json.contactArr.push({id:"sBanBillingContactId",value:"${b.banBillingContactId}"});
											</script>
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
											<td valign="top" width="35%">Bill To</td>
											<td>
												<s:textarea name="b.billTo" cssStyle="overflow:auto ;resize: none;" rows="5" cols="40"></s:textarea>
											</td>
										</tr>
										<tr>
											<td valign="top" width="35%">Tax Registration Number</td>
											<td>
												<s:textarea name="b.taxRegistrationNumber" cssStyle="overflow:auto ;resize: none;" rows="2" cols="40"></s:textarea>
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
												<s:textarea name="b.notes" cssStyle="overflow:auto ;resize: none;" rows="5" cols="50"></s:textarea>
											</td>
										</tr>
										<tr>
											<td valign="top" width="36%">Notice (For SANINCO use only)</td>
											<td>
												<s:textarea cssStyle="overflow:auto ;resize: none;" rows="5" cols="50" name="b.notice"></s:textarea>
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
											<td>BAN Inactive Date </td>
											<td>
												<c:if test="${b.id != null && b.banStatus.id == 2}">
													<fmt:formatDate value="${b.statusTimestamp}" pattern="yyyy/MM/dd"/>  
												</c:if>
											</td>
										</tr>
										<c:if test="${b.id != null && b.banStatus.id == 5}">
											<tr>
												<td valign="top" width="35%">Reject Notes </td>
												<td>
												    <s:textarea name="b.rejectNotes" cols="40" rows="5"
														style="width: 260px; height: 50px;"></s:textarea>
											    </td>
											</tr>
										</c:if>
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td height="55">
								<input id="banDetail_frm_0" type="button" value="Save" onclick="banDetailPage_Json.serachAccountNumber();">
								<input id="btnClearOrCancle" type="button" value="" onclick="banDetailPage_Json.cancel();">
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
				<td style="float:left;margin-top:3px;">
							Attach File:
				</td>
					<td>
						<div id = "addFile" style="overflow-y:auto;max-height:100px;position:relative;vertical-align: top;">
						<div id ="ContractFile1" style="padding:3px 0 0 0;height: 19px;" >	
							<div style="float:left;">
						    <input style="height:19px;width:170px;" type='text' id="_upload_text_attachment_01" disabled="disabled" class="upload_text">
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
						    <input style="height:19px;width:170px;" type='text' id="_upload_text_attachment_02" disabled="disabled" class="upload_text">
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
						    <input style="height:19px;width:170px;" type='text' id="_upload_text_attachment_03" disabled="disabled" class="upload_text">
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
		        		</td>
		        		<td>
					    <td>
					    <div class="lable"
								style="padding-top: 10px; text-align: right; padding-right: 16%; padding-bottom :10px">
		        		 <input style="height: 20px;text-align:center;" type="button" value="Add More" onclick="banDetailPage_Json.addUploadForm()"/>
		        		</div>
		        		</td>
					    </tr>
		        		<tr>
					    <td>
							Notes:
					    </td>
					    <td>
					    <textarea id="uploadNotes" name="uploadNotes" cols="" rows=""
							style="width: 260px; height: 50px;"></textarea>
					    </td>
					    </tr>
		        		<tr>
					    <td>
							&nbsp;
					    </td>
					    <td>
					    <div class="lable"
					    style="padding-top: 10px; text-align: right; padding-right: 16%;">
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
			
			
			<div id="uploadExemptionCertificateWindow">
				<div class="hd">
					<h2>
						Attach Exemption Certificate
					</h2>
				</div>
				<div class="bd">
					<div>
						<div class="cp_p_down">
						<form id="exemptionCertificateUploadForm" action="doUploadAttachFileByBanExemption.action" enctype="multipart/form-data" method="POST" target="banDetailPageFrame">
							<s:hidden name="banId"></s:hidden>
							<table width="100%" align="center" style="margin: auto;">
								<tr>
								<td style="float:left;margin-top:3px;">
											Attach File:
								</td>
									<td>
										<div id = "addExemptionCertificateFile" style="overflow-y:auto;max-height:110px;;position:relative;vertical-align: top;">
										<div id ="exemptionFile1" style="padding:3px 0 0 0;height: 19px;" >	
											<div style="float:left;">
										    <input style="height:19px;width:200px;" type='text' id="_upload_exemption_attachment_01" disabled="disabled" class="upload_exemption_certificate_text">
				       					    </div>	
											<div style="float:left; margin:0 0 0 2px">
								        		<span class="ccm_upload_img">
				        					<input onchange="document.getElementById('_upload_exemption_attachment_01').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
				        					<input style="height:19px;width:65px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>	   						
								        		</span>
							        		</div>
							        		<span class="approveicon" onclick="deletefile('exemptionFile1');"></span>
							        		<div class="clear" ></div>
						        		</div>
						        		<div id ="exemptionFile2" style="padding:3px 0 0 0;height: 19px;" >	
											<div style="float:left;">
										    <input style="height:19px;width:200px;" type='text' id="_upload_exemption_attachment_02" disabled="disabled" class="upload_exemption_certificate_text">
				       					    </div>	
											<div style="float:left; margin:0 0 0 2px">
								        		<span class="ccm_upload_img">
				        					<input onchange="document.getElementById('_upload_exemption_attachment_02').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
				        					<input style="height:19px;width:65px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>	   						
								        		</span>
							        		</div>
							        		<span class="approveicon" onclick="deletefile('exemptionFile2');"></span>
							        		<div class="clear" ></div>
						        		</div>
						        		<div id ="exemptionFile3" style="padding:3px 0 0 0;height: 19px;" >	
											<div style="float:left;">
										    <input style="height:19px;width:200px;" type='text' id="_upload_exemption_attachment_03" disabled="disabled" class="upload_exemption_certificate_text">
				       					    </div>	
											<div style="float:left; margin:0 0 0 2px">
								        		<span class="ccm_upload_img">
				        					<input onchange="document.getElementById('_upload_exemption_attachment_03').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
				        					<input style="height:19px;width:65px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>	   						
								        		</span>
							        		</div>
							        		<div class="approveicon" onclick="deletefile('exemptionFile3');"></div>
							        		<div class="clear" ></div>
						        		</div>
						        		</div>
						        		</td>
						        		</tr>
						        		<tr>
						        		<td>
						        		&nbsp;
						        		</td>	        	
						        		</tr>
						        		<tr>
						        		<td>
						        		&nbsp;
						        		</td>
						        		<td style="text-align: right;padding-right:  20px;">
						        		
						        		<input style="height: 20px;" type="button" value="Add" onclick="banDetailPage_Json.addUploadExemption()"/>
												&nbsp;
												&nbsp;
						        		</td>
						        		</tr>
						        		<tr>
						        		<td>
						        		&nbsp;
						        		</td>	        	
						        		</tr>
						        		<tr>
						        		    <td width="25%">
											     Term Start Date:
										    </td>
											<td nowrap="nowrap">
												<s:textfield name="termStartDate" id = "banExemption_DateStartsOn" style="width:190px;" 
													cssClass="validate-date-ca"></s:textfield>
												<a onClick="g_Calendar.show(event,'banExemption_DateStartsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;"
													href="javascript:%20void(0);"><img src="include/images/cal.gif" id="ticketDueStartsOnImg" border="0" align="middle"></a>yyyy/mm/dd
											</td>
						        		</tr>
						        		<tr>
						        		    <td width="25%">
										    	Term End Date:
											</td>
											<td>
												<s:textfield name="termEndDate" id = "banExemption_DateEndsOn" style="width:190px;" 
													cssClass="validate-date-ca"></s:textfield>
												<a onClick="g_Calendar.show(event,'banExemption_DateEndsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;" href="javascript:%20void(0);"><img
													src="include/images/cal.gif" id="ticketDueEndsOnImg" border="0" align="middle"></a>yyyy/mm/dd
											</td>
						        		</tr> 
						        		<tr>
						        		<td>
						        		&nbsp;
						        		</td>
						        		</tr>
                                        <tr>
									    <td>
											Notes:
									    </td>
									    <td>
									    <textarea id="uploadExemptionCertificateNotes" name="uploadNotes" cols="" rows=""
											style="width: 270px; height: 50px;resize:none;" maxlength="500";></textarea>
									    </td>
									    </tr>
									    <tr>
									    <td>
									    </td>
									    <td>
									    <div class="word-limit clearfix" style="padding-left:160px">
				                            <strong class="word-number">500</strong>
				                            <strong>characters left.</strong>
			                                </div>
									    </td>
									    </tr>
						        		<tr>
									    <td>
											&nbsp;
									    </td>
									    <td>
									    <div class="lable" style="padding-top: 10px; text-align: right; padding-right: 38px;">
									            
												<input style="height: 20px;" type="button" value="Submit" onclick="banDetailPage_Json.submitUploadExemption()"/>
												&nbsp;
												<input style="height: 20px;" type="button" value="Cancel" onclick="banDetailPage_Json.cancelUploadExemption()"/>
								        </div>
									    </td>
									   </tr>
									
								</table>
							</form>
						</div>
					</div>
				</div>
			</div>
			
			<div id="uploadExemptionTimeWindow">
				<div class="hd">
					<h2>
						Attach Exemption Certificate
					</h2>
				</div>
				<s:hidden id="updateBanExemptionId"></s:hidden>
				<div class="bd">
					<div>
						<div class="cp_p_down">

							<table width="100%" align="center" style="margin: auto;">


						        		<tr>
						        		    <td width="25%">
											     Term Start Date:
										    </td>
											<td nowrap="nowrap">
												<s:textfield name="updateTermStartDate" id = "updateBanExemption_DateStartsOn" style="width:190px;" 
													cssClass="validate-date-ca"></s:textfield>
												<a onClick="g_Calendar.show(event,'updateBanExemption_DateStartsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;"
													href="javascript:%20void(0);"><img src="include/images/cal.gif" id="updateTicketDueStartsOnImg" border="0" align="middle"></a>yyyy/mm/dd
											</td>
						        		</tr>
						        		<tr>
						        		    <td width="25%">
										    	Term End Date:
											</td>
											<td>
												<s:textfield name="updateTermEndDate" id = "updateBanExemption_DateEndsOn" style="width:190px;" 
													cssClass="validate-date-ca"></s:textfield>
												<a onClick="g_Calendar.show(event,'updateBanExemption_DateEndsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;" href= "javascript:%20void(0);"><img
													src="include/images/cal.gif" id="updateTicketDueEndsOnImg" border="0" align="middle"></a>yyyy/mm/dd
											</td>
						        		</tr> 

						        		<tr>
									    <td>
											&nbsp;
									    </td>
									    <td>
									    <div class="lable" style="padding-top: 10px; text-align: right; padding-right: 38px;">
												<input style="height: 20px;" type="button" value="Submit" onclick="banDetailPage_Json.saveUploadExemptionTime()"/>
												&nbsp;
												<input style="height: 20px;" type="button" value="Cancel" onclick="banDetailPage_Json.cancelUploadExemptionTime()"/>
								        </div>
									    </td>
									   </tr>
									
								</table>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<iframe id="__downloadIframe" style="display: none;"></iframe>
