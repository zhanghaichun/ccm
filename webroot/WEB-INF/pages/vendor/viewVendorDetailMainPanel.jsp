<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" type="text/css" href="include/javascript/Calendar/styles/calendar.css">
<script type="text/javascript" src="include/javascript/Calendar/simplecalendar.js"></script>
<script type="text/javascript" src="include/javascript/ccm/quicklink.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/yui2/tabview-min.js"></script>
<SCRIPT type="text/javascript" src="include/javascript/ccm/common/common.js"></SCRIPT>
<script type="text/javascript" src="include/javascript/ccm/vendor/viewVendorDetails.js" language="javascript"></script>
<link rel="stylesheet" type="text/css" href="include/javascript/flexbox/css/jquery.flexbox.css"><link>
<script type="text/javascript" src="include/javascript/flexbox/js/jquery.flexbox.js"></script>
<script type="text/javascript" src="include/javascript/ccm/saninco/downLoad.js" language="javascript"></script>
<style>
	.aright{text-align:right;}
</style>
<script type="text/javascript">
var VL_Array = {
	"results": [
		{
		    "id": "null",
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
var BL_Array = {
	"results": [
		{
		    "id": "null",
		    "name": ""
		}
		<c:forEach items="${banList}" var="v">
		,{
		    "id": "${v.key}",
		    "name": "${v.value}"
		}
		</c:forEach>
	],
	"total": ${fn:length(banList)}
};
$(function(){
	form0_vendorMain_vendorId = $('#VL_vendorMainList_1').flexbox(VL_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		showResults:false,
		width : 260,
		onSelect : function(){

		}
	});
	form0_vendorMain_vendorId.setValue("${vendorId}");
	
	form0_banMain_banId = $('#BL_banMainList_1').flexbox(BL_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		showResults:false,
		width : 260,
		onSelect : function(){

		}
	});
	form0_banMain_banId.setValue("${banId}");
	showItemTab(0,true);
});
</script>
<div id="pageContainer" class="tabForm" style="padding-bottom:25px;">
	<h3 style="padding-top:0px; padding-bottom:4px;"><b>BAN Invoice Detail</b></h3>
	<div style="display: block" id="vendorSearch">
		<br />
		<div class="tabForm">
			<table width=80% height=100% border=0 cellpadding="0"
				cellspacing="3">
				<tr>
					<td width="5%">
						Vendor
					</td>
					<td width="15%">
						<span id="VL_vendorMainList_1"></span>
						<input type="text" style="display: none; width: 243px;"
							id="vendorTxtUpdate" />
					</td>
					<td width="5%">
						BAN
					</td>
					<td width="15%">
						<span id="BL_banMainList_1"></span>
						<input type="text" style="display: none; width: 243px;"
							id="banTxtUpdate" />
					</td>
				</tr>
			</table>
		</div>
<%--		<div style="width:100%; height:35px; overflow:hidden;">--%>
<%--			<div style="float: left; margin-top:10px;">--%>
<%--				<input type="button" style=" overflow:visible; padding:0 10px;" value="Get Cost Detail" onClick="showItemTab(0,true)" />--%>
<%--			</div>--%>
<%--		</div>--%>
		
		<div id="adminTab" class="yui-navset">
			<ul class="yui-nav">
				<li id="a0">
					<a href="#allgChargeTab"
						onclick="showItemTab(0);"><em>All Charge</em>
					</a>
				</li>
				<li id="a1" class="selected">
					<a href="#recurringChargeTab"
						onclick="showItemTab(1);"><em>Recurring Charge</em>
					</a>
				</li>
				<li id="a2" >
					<a href="#nonRecurringChargeTab"
						onclick="showItemTab(2);"><em>Non-Recurring Charge</em>
					</a>
				</li>
				<li id="a3" >
					<a href="#usageChargeTab"
						onclick="showItemTab(3);"><em>Usage Charge</em>
					</a>
				</li>
				<li id="a4" >
					<a href="#LPCChargeTab"
						onclick="showItemTab(4);"><em>LPC Charge</em>
					</a>
				</li>
				<li id="a5" >
					<a href="#creditAndAdjustmentTab"
						onclick="showItemTab(5);"><em>Credit and Adjustment</em>
					</a>
				</li>
				<li id="a6" >
					<a href="#paymentTab"
						onclick="showItemTab(6);"><em>Payment</em>
					</a>
				</li>
			</ul>
		</div>
	</div>
	
	<div id="tab0" style="display: none;">
		<table>
			<tr>
				<td>
					<table style="table-layout: fixed; width: 100%;">
						<tr>
							<td>
								<div align="left"
									style="width: 100%; overflow-x: auto; padding-bottom: 20px;">
									<div id="allChargeTable"></div>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table border=0>
			<tr>
				<td width="240px" align="left">
					<div style="margin-top: -11px;" id="allCharge_pageTable"></div>
					<div style="margin-top: -21px; margin-left: 260px;" id="allCharge_pageTable_download">
						<input type="button" id="saveExcel_tab0" value="Download to Excel" onclick="saveExcel(allChargePage,'saveExcelByAllCharges.action','');" style="display: none;" />
					</div>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="tab1" style="display: none;">
		<table>
			<tr>
				<td>
					<table style="table-layout: fixed; width: 100%;">
						<tr>
							<td>
								<div align="left"
									style="width: 100%; overflow-x: auto; padding-bottom: 20px;">
									<div id="recurringChargeTable"></div>
								</div>

							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table border=0>
			<tr>
				<td width="240px" align="left">
					<div style="margin-top: -11px;" id="recurringCharge_pageTable"></div>
					<div style="margin-top: -21px; margin-left: 260px;" id="recurringCharge_pageTable_download">
						<input type="button" id="saveExcel_tab1" value="Download to Excel" onclick="saveExcel(recurringChargePage,'saveExcelByRecurringCharge.action','');" style="display: none;" />
					</div>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="recurringChargeDetail">
		<div class="hd" id="recurringChargeDetailTitle">Description</div>
		<div class="bd">
			<table>
				<tr>
					<td>
						<table style="table-layout: fixed; width: 100%;">
							<tr>
								<td>
									<div align="left"
										style="width: 100%; overflow: auto; padding-bottom: 5px;">
										<div id="recurringChargeDetailTabel"></div>
									</div>
	
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<table border=0>
				<tr>
					<td width="240px" align="left">
						<div style="margin-top: -11px;" id="recurringChargeDetail_pageTable"></div>
						<div style="margin-top: -21px; margin-left: 260px;" id="recurringChargeDetail_pageTable_download">
							<input type="button" id="saveExcel_tab1_detail" value="Download to Excel" onclick="saveExcel(descriptionPage,'saveExcelBySortProposal.action','searchDescriptionExcel');" style="display: none;" />
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	<div id="tab2" style="display: none;">
		<table>
			<tr>
				<td>
					<table style="table-layout: fixed; width: 100%;">
						<tr>
							<td>
								<div align="left"
									style="width: 100%; overflow-x: auto; padding-bottom: 5px;">
									<div id="nonRecurringChargeTable"></div>
								</div>

							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table border=0>
			<tr>
				<td width="240px" align="left">
					<div style="margin-top: -11px;" id="nonRecurringCharge_pageTable"></div>
					<div style="margin-top: -21px; margin-left: 260px;" id="nonRecurringCharge_pageTable_download">
						<input type="button" id="saveExcel_tab2" value="Download to Excel" onclick="saveExcel(nonRecurringChargePage,'saveExcelBySortProposal.action','searchNonRecurringChargeExcel');" style="display: none;" />
					</div>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="tab3" style="display: none;">
		<table>
			<tr>
				<td>
					<table style="table-layout: fixed; width: 100%;">
						<tr>
							<td>
								<div align="left"
									style="width: 100%; overflow-x: auto; padding-bottom: 5px;">
									<div id="usageChargeTable"></div>
								</div>

							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table border=0>
			<tr>
				<td width="240px" align="left">
					<div style="margin-top: -11px;" id="usageCharge_pageTable"></div>
					<div style="margin-top: -21px; margin-left: 260px;" id="usageCharge_pageTable_download">
						<input type="button" id="saveExcel_tab3" value="Download to Excel" onclick="saveExcel(usageChargePage,'saveExcelBySortProposal.action','searchUsageChargeExcel');" style="display: none;" />
					</div>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="tab4" style="display: none;">
		<table>
			<tr>
				<td>
					<table style="table-layout: fixed; width: 100%;">
						<tr>
							<td>
								<div align="left"
									style="width: 100%; overflow-x: auto; padding-bottom: 5px;">
									<div id="LPCChargeTable"></div>
								</div>

							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table border=0>
			<tr>
				<td width="240px" align="left">
					<div style="margin-top: -11px;" id="LPCCharge_pageTable"></div>
					<div style="margin-top: -21px; margin-left: 260px;" id="LPCCharge_pageTable_download">
						<input type="button" id="saveExcel_tab4" value="Download to Excel" onclick="saveExcel(LPCChargePage,'saveExcelBySortProposal.action','searchLPCChargeExcel');" style="display: none;" />
					</div>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="tab5" style="display: none;">
		<table>
			<tr>
				<td>
					<table style="table-layout: fixed; width: 100%;">
						<tr>
							<td>
								<div align="left"
									style="width: 100%; overflow-x: auto; padding-bottom: 5px;">
									<div id="CreditAndAdjustmentTable"></div>
								</div>

							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table border=0>
			<tr>
				<td width="240px" align="left">
					<div style="margin-top: -11px;" id="CreditAndAdjustment_pageTable"></div>
					<div style="margin-top: -21px; margin-left: 260px;" id="CreditAndAdjustment_pageTable_download">
						<input type="button" id="saveExcel_tab5" value="Download to Excel" onclick="saveExcel(CreditAndAdjustmentPage,'saveExcelBySortProposal.action','searchCreditAndAdjustmentExcel');" style="display: none;" />
					</div>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="tab6" style="display: none;">
		<table>
			<tr>
				<td>
					<table style="table-layout: fixed; width: 100%;">
						<tr>
							<td>
								<div align="left"
									style="width: 100%; overflow-x: auto; padding-bottom: 5px;">
									<div id="PaymentTable"></div>
								</div>

							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table border=0>
			<tr>
				<td width="240px" align="left">
					<div style="margin-top: -11px;" id="Payment_pageTable"></div>
					<div style="margin-top: -21px; margin-left: 260px;" id="Payment_pageTable_download">
						<input type="button" id="saveExcel_tab6" value="Download to Excel" onclick="saveExcel(PaymentPage,'saveExcelByPayment.action','');" style="display: none;" />
					</div>
				</td>
			</tr>
		</table>
	</div>
</div>

