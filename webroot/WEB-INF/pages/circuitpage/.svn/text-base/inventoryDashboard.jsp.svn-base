<!-- Create By Donghao.guo 2011/09/22 -->
<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<link rel="stylesheet" href="include/css/hint.min.css">

<link rel="stylesheet"
	href="include/css/quoteInventory/quote_inventory.css">
<link rel="stylesheet"
	href="include/css/circuitPage/inventoryDashboard.css">
<link rel="stylesheet" href="include/css/circuitPage/miDetailWindow.css">


<link rel="stylesheet" type="text/css"
	href="include/javascript/Calendar/styles/calendar.css">
<link rel="stylesheet" type="text/css"
	href="include/javascript/flexbox/css/jquery.flexbox.css">
<link>
<link rel="stylesheet"
	href="include/javascript/ccm/colortip/colortip-1.0-jquery.css"
	type="text/css"></link>
	<script type="text/javascript" src="include/javascript/highchart/no-data-to-display.js"></script>
	<script type="text/javascript" src="include/javascript/ccm/circuitpage/circuitPage.js"></script>
	<script type="text/javascript" src="include/javascript/ccm/circuitpage/masterInventoryChangeHistory.js"></script>
<style>
.aright {
	text-align: right;
}

#change_history {

    position: absolute;
    top: 0;
    left: 0;
    
    display: none;
    
    width: 100%;
    height: 100%;
    padding-right: 0;
    border: none;
    box-sizing: border-box;
    
    overflow: auto;
}
</style>
<script type="text/javascript">
var saveQuicklinkUri = "saveMasterInventorySearch.action";
YAHOO.util.Event.onDOMReady(function () {
	YAHOO.ccm.container.masterInventoryWindow = new YAHOO.widget.Dialog("masterInventoryWindow", { 
		width: "460px",
	    fixedcenter: true,
	    visible: false,
	    constraintoviewport: true,
	    modal: true
	});
	YAHOO.ccm.container.masterInventoryWindow.render();
});

var VL_Array = {
	"results": [
		{
		    "id": "all",
		    "name": ""
		}
		<c:forEach items="${summaryVendorList}" var="v">
		,{
		    "id": "${v.value}",
		    "name": "${v.value}"
		}
		</c:forEach>
	],
	"total": ${fn:length(summaryVendorList)}
};

/* var PRODUCT_Array = {
	"results": [
		{
		    "id": "all",
		    "name": ""
		}
		<c:forEach items="${productList}" var="p">
		,{
		    "id": "${p.key}",
		    "name": "${p.value}"
		}
		</c:forEach>
	],
	"total": ${fn:length(productList)}
};*/

var BAN_Array = {
	"results": [
		{
		    "id": "all",
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

var Complete_Array = {
		"results": [
			{
			    "id": "all",
			    "name": "All"
			}
			,{
			    "id": "Y",
			    "name": "Completed"
			}
			,{
			    "id": "N",
			    "name": "Not Completed"
			}
		],
		"total": 3
};

// Cost Type list.
var cost_type_Array = {
		"results": [
			{
			    "id": "all",
			    "name": ""
			}
			<c:forEach items="${costTypeList}" var="costType">
	        ,{
	            "id": "${costType.key}",
	            "name": "${costType.value}"
	        }
	        </c:forEach>
		],
		"total": ${fn:length(costTypeList)}
};

var SC_Array = {
		"results": [
			{
			    "id": "",
			    "name": ""
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

function validateFields(){
	return FIC_checkForm("form0");
}

var DIV = function(style) {
	return $('<div>').addClass(style);
}

function resetInventoryDashboardValue(){


	$("#VL_banList_Invoice").attr('disabled', false);
	$("#VL_summaryVendorList_1").attr('disabled', false);
	$("#VL_complete_flag").attr('disabled', false);
	
	$("#circuit_id_text")[0].value = "";
	$("#service_id_text")[0].value = "";
	$("#end_user_text")[0].value = "";
	

	$("#customer_billing_account")[0].value = "";
	$("#a_street_number")[0].value = "";
	$("#a_street_name")[0].value = "";
	$("#a_unit")[0].value = "";
	$("#a_city")[0].value = "";
	$("#a_province")[0].value = "";
	$("#a_country")[0].value = "";

	$("#intercompany_business_unit_text")[0].value = "";
	$("#product_category_text")[0].value = "";
	$("#SCOA_Company")[0].value = "";
	$("#SCOA_Location")[0].value = "";
	$("#SCOA_Department")[0].value = "";
	$("#SCOA_Production")[0].value = "";
	$("#SCOA_Account")[0].value = "";
	$("#SCOA_Channel")[0].value = "";
	
	form0_searchInventoryDashboardVO_banList.setValue("all");
	form0_searchInventoryDashboardVO_CompleteFlag.setValue("all");
	form0_searchInventoryDashboardVO_vendorId.setValue("all");
	//clearCheckbox();
}

function clearCheckbox(){
	$(".ClearCheckbox").removeAttr("checked");
}



masterInventoryErrorFilePath = null;
function showMasterInventoryWindow(errorFilePath){
	masterInventoryErrorFilePath = errorFilePath;
	YAHOO.ccm.container.masterInventoryWindow.show();
}

function downLoadMasterInventoryResult(){
	showLoadingModalLayer();
	windowOpen("downLoadMasterInventoryErrorFile.action?errorFileName="+ masterInventoryErrorFilePath);
	hideLoadingModalLayer();
}

filesSize = null ;
function filesProcess(files){
	showLoadingModalLayer();
	var newAttach = '<input style="text-align:center;width:70px;height: 19px;position: absolute;top: 0;left: 0;z-index: 1;opacity: 0;cursor: pointer;" ' +
	'type="file" name="uploads" multiple="multiple" onchange="filesProcess(this.files)" />';
	
	$("#attachDiv :input").each(function () {
		var this_name = $(this).attr("name");
		$(this).hide();
	});
	$("#attachDiv").append(newAttach);
    $("#uploadMasterInventoryForm").submit();
}

function selectDate() {
	g_Calendar.show(event,'detail_expiry_date',false, 'yyyy/mm/dd', new Date());
}
</script>
<!-- style="min-height: 500px;" -->
<%--<div id="pageContainer" class="tabForm"--%>
<%--	onMouseDown="if(g_Calendar && g_Calendar.hide)g_Calendar.hide();"--%>
<%--	style="padding-bottom: 25px; position: relative; min-height: 500px;">--%>
	<div style="border-top: 0px none;">
		<s:form id="form0" action="searchMasterInventoryList.action"
			theme="simple" onsubmit="startSearch();return false;">
			<table class="tabDetailView" border=" 0" width="100%" cellpadding="5"
				cellspacing="0">
				<tr>
					<td class="tabDetailViewDL">
						<div class="searchItemPanel">
							<table width="100%" cellspacing="3" cellpadding="0" border="0">
								<tr style="height: 30px">
									<td style="white-space: nowrap;">
										<b style="font-size: 15">Search</b>
									</td>
								</tr>
								<tr>
									<td width="25%" style="white-space: nowrap;">
										Summary Vendor Name
									</td>
									<td align="left" style="position: relative;" colspan="2">
										<div id="VL_summaryVendorList_1"></div>
									</td>
									<td width="10%">
									</td>
									<td width="25%" style="white-space: nowrap;">
										A Street Number
									</td>
									<td>
										<s:textfield id="a_street_number"
											name="searchInventoryDashboardVO.aStreetNumber" 
											cssClass="validate-alp" cssStyle="width:240px"></s:textfield>
									</td>
								</tr>
								<tr>
									<td>
										BAN
									</td>
									<td colspan="2">
										<div id="VL_banList_Invoice"></div>
									</td>
									<td width="10%">
									</td>
									<td width="25%" style="white-space: nowrap;">
										A Street Name
									</td>
									<td>
										<s:textfield id="a_street_name" 
											name="searchInventoryDashboardVO.aStreetName"
											cssClass="validate-alp" cssStyle="width:240px"></s:textfield>
									</td>
								</tr>
								<tr>
									<td style="white-space: nowrap;">
										Customer Billing Account
									</td>
									<td colspan="2">
										<%--										<div id="VL_productList"></div>--%>
										<s:textfield id="customer_billing_account" 
											name="searchInventoryDashboardVO.customerBillingAccount"
											cssClass="validate-alp" cssStyle="width:240px"></s:textfield>
									</td>
									<td width="10%" />
										<td width="25%">
											A Unit
										</td>
										<td>
											<s:textfield id="a_unit"
												name="searchInventoryDashboardVO.aUnit" 
												cssClass="validate-alp" cssStyle="width:240px"></s:textfield>
										</td>
								</tr>
								<tr>
									<td>
										Circuit Number
									</td>
									<td colspan="2">
										<s:textfield id="circuit_id_text" 
											name="searchInventoryDashboardVO.circuitId"
											cssClass="validate-alp" cssStyle="width:240px"></s:textfield>
									</td>
									<td width="10%">
									</td>
									<td width="25%">
										A City
									</td>
									<td>
										<s:textfield id="a_city" 
											name="searchInventoryDashboardVO.aCity"
											cssClass="validate-alp" cssStyle="width:240px"></s:textfield>
									</td>
								</tr>
								<tr>
									<td>
										Service ID
									</td>
									<td colspan="2">
										<s:textfield id="service_id_text" 
											name="searchInventoryDashboardVO.serviceId"
											cssClass="validate-alp" cssStyle="width:240px"></s:textfield>
									</td>
									<td width="10%">
									</td>
									<td width="25%">
										A Province
									</td>
									<td>
										<s:textfield id="a_province" 
											name="searchInventoryDashboardVO.aProvince"
											cssClass="validate-alp" cssStyle="width:240px"></s:textfield>
									</td>
								</tr>
								<tr>
									<td>
										End User
									</td>
									<td colspan="2">

										<s:textfield id="end_user_text" 
											name="searchInventoryDashboardVO.endUser"
											cssClass="validate-alp" cssStyle="width:240px"></s:textfield>
									</td>
									<td width="10%">
									</td>
									<td width="25%">
										A Country
									</td>
									<td>
										<s:textfield id="a_country" 
											name="searchInventoryDashboardVO.aCountry"
											cssClass="validate-alp" cssStyle="width:240px"></s:textfield>
									</td>
								</tr>
								<tr>
									<td style="white-space:nowrap;">
										Intercompany Business Unit
									</td>
									<td colspan="2">

										<s:textfield id="intercompany_business_unit_text" 
											name="searchInventoryDashboardVO.intercompanyBusinessUnit"
											cssClass="validate-alp" cssStyle="width:240px"></s:textfield>
									</td>
									<td width="10%">
									</td>
									<td width="25%">
										Completed Flag
									</td>
									<td>
										<div id="VL_complete_flag"></div>
									</td>
								</tr>
								<tr>
									<td width="25%">
										Product Category
									</td>
									<td>
										<s:textfield id="product_category_text" 
											name="searchInventoryDashboardVO.productCategory"
											cssClass="validate-alp" cssStyle="width:240px"></s:textfield>
									</td>
								</tr>
								<tr>
									<td>
										SCOA Segments
									</td>
									<td colspan="5">
										<table width="100%">
											<tr>
												<td>
													Company
												</td>
												<td>
													<s:textfield id="SCOA_Company" 
														name="searchInventoryDashboardVO.scoaCompany"
														cssClass="validate-number" cssStyle="width:100px"></s:textfield>
												</td>
												<td>
													Location
												</td>
												<td>
													<s:textfield id="SCOA_Location" 
														name="searchInventoryDashboardVO.scoaLocation"
														cssClass="validate-number" cssStyle="width:100px"></s:textfield>
												</td>
												<td>
													Department
												</td>
												<td>
													<s:textfield id="SCOA_Department" 
														name="searchInventoryDashboardVO.scoaDepartement"
														cssClass="validate-number" cssStyle="width:100px"></s:textfield>
												</td>
											</tr>
											<tr>
												<td>
													Product
												</td>
												<td>
													<s:textfield id="SCOA_Production" 
														name="searchInventoryDashboardVO.scoaProduction"
														cssClass="validate-number" cssStyle="width:100px"></s:textfield>
												</td>
												<td>
													Account
												</td>
												<td>
													<s:textfield id="SCOA_Account" 
														name="searchInventoryDashboardVO.scoaAccount"
														cssClass="validate-number" cssStyle="width:100px"></s:textfield>
												</td>
												<td>
													Channel
												</td>
												<td>
													<s:textfield id="SCOA_Channel" 
														name="searchInventoryDashboardVO.scoaChannel"
														cssClass="validate-number" cssStyle="width:100px"></s:textfield>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</div>
					</td>
					<td class="tabDetailViewDL" width="40%">
						<div class="searchItemPanel">
							<table width="100%" cellspacing="3" cellpadding="0" border="0">
								<tr style="height: 30px">
									<td>
										<div id="div_dashboard_id" style="height: 320px">

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
	<div style="width: 100%; height: 35px; overflow: hidden;">
		<div style="float: left; margin-top: 10px;">
			<input type="button" value="Search" onclick="startSearch()" />
			&nbsp;
			<input type="button" id="saveQueryButton" value="Save"
				onclick="YAHOO.ccm.container.saveQueryDialog.show();" />
			&nbsp;
			<input type="button" value="Clear"
				onclick="resetInventoryDashboardValue();" />
		</div>

		<div style="float: right; margin-top: 10px; width: 235px;">
			<input
				style="cursor: pointer; float: left; height: 23px; line-height: 18px;"
				type="button" value="Download Template"
				onclick="saveMasterInventoryToExcel(true)">
			<form id="uploadMasterInventoryForm" enctype="multipart/form-data"
				method="post" action="uploadMasterInventory.action"
				target="masterInventoryPageFrame">
				<div id="attachDiv" style="margin-left: 15px;">
					<input id="quoteUpload" type="file" name="uploads"
						multiple="multiple" onchange="filesProcess(this.files)" />
					<div class="quote-attachBorder quote-upload-div">
						Upload
					</div>
				</div>
			</form>
			<iframe name="masterInventoryPageFrame" id="masterInventoryPageFrame"
				style="display: none" src="javascript:false"></iframe>

		</div>
		<div id="loadingImgDiv" style="display: none; float: right;">
			<img src="include/images/loading.gif" />
		</div>
	</div>
	<div id="tab5">
		<table style="width: 100%; table-layout: fixed;">
			<tr>
				<td>
					<div align="left"
						style="width: 100%; overflow-x: auto; border: 0px solid; padding-bottom: 20px;">
						<div id="_master_inventory"></div>
					</div>
				</td>
			</tr>
		</table>
		<table style="width: 100%;">
			<tr>
				<td>
					<div class="demo" id="__master_inventory_page_list"
						style="float: left"></div>
					<input id="download_button" type="button" value="Download to Excel"
						onclick="saveMasterInventoryToExcel();" />
				</td>
				<tr>
		</table>
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
						<input type="text" name="queryName" id="queryName" 
							class="required">
					</td>
				</tr>
			</table>
		</div>
	</div>


	<div id="change_history" class="tabForm">
		<div style="width: 100%; height: 60px; top: 0px; left: 0px;">
			<h3 id="circuit_number_title" style="padding-top: 20px; float: left">
			</h3>
			<input type="button" value="Back"
				style="position: absolute; top: 20px; right: 20px; padding-left: 20px; padding-right: 20px;"
				onclick="changeHistory.closeChangeHistoryListWindow()" />
		</div>
		<div id="tab6">
			<table style="width: 100%; table-layout: fixed;">
				<tr>
					<td>
						<div align="left"
							style="width: 100%; overflow-x: auto; border: 0px solid; padding-bottom: 20px;">
							<div id="_master_inventory_change_history_list"></div>
						</div>
					</td>
				</tr>
			</table>
			<table style="width: 100%;">
				<tr>
					<td>
						<div class="demo" id="_master_inventory_change_history_page_list"
							style="float: left"></div>
						<input id="download_button" type="button"
							value="Download to Excel"
							onclick="changeHistory.saveInventoryChangeHistoryToExcel();" />
					</td>
					<tr>
			</table>
		</div>
	</div>
	<div class="yui-pe-content" id="masterInventoryWindow"
		style="visibility: hidden;">
		<div class="hd">
			<h2>
				Master Inventory
			</h2>
		</div>
		<div class="tabForm" style="padding-bottom: 25px; height: 100px;">
			<div class="quoteWindow-left">
				<div class="failed-icon"></div>
			</div>
			<div class="quoteWindow-right">
				<div class="title">
					Master Inventory upload is failed
				</div>
				<div class="content">
					Some data is incorrect,please download the report.
				</div>
				<div class="link" onclick="downLoadMasterInventoryResult()">
					Master Inventory Import Errors.xlsx
				</div>
			</div>
		</div>
	</div>
</div>
<%-- show detail --%>
<div id="miDetailWindow" class="yui-pe-content">
	<div class="hd">
		Master Inventory Detail
	</div>
	<s:form id="miDetailForm" action="#" theme="simple">
		<div class="miDetailDiv" style="overflow: auto; height: 550px">
			<table class="miDetailTable">
				<tr>
					<td style="white-space: nowrap; width: 150px;">
						KEY
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_id" cssClass="disabled-style" 
							cssStyle="width:240px" readonly="true"></s:textfield>
					</td>
					<td style="white-space: nowrap; width: 150px;">
						Vendor Name
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_vendor_name" cssClass="disabled-style" 
							cssStyle="width:240px" readonly="true"></s:textfield>
					</td>
				</tr>
				<tr>
					<td style="white-space: nowrap; width: 150px;">
						Summary Vendor Name
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_summary_vendor_name" 
							cssClass="disabled-style" cssStyle="width:240px" readonly="true"></s:textfield>
					</td>
					<td style="white-space: nowrap; width: 150px;">
						BAN
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_ban_name" cssClass="disabled-style" 
							cssStyle="width:240px" readonly="true"></s:textfield>
					</td>
				</tr>
				<tr>
					<td style="white-space: nowrap; width: 150px;">
						Invoice Number
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_invoice_number" cssClass="disabled-style" 
							cssStyle="width:240px" readonly="true"></s:textfield>
					</td>
					<td style="white-space: nowrap; width: 150px;">
						Line of Business
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_line_of_business" 
							cssClass="disabled-style" cssStyle="width:240px" readonly="true"></s:textfield>
					</td>
				</tr>
				<tr>
					<td style="white-space: nowrap; width: 150px;">
						Invoice Date
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_invoice_date" cssClass="disabled-style" 
							cssStyle="width:240px" readonly="true"></s:textfield>
					</td>
					<td style="white-space: nowrap; width: 150px;">
						Stripped Circuit Number
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_stripped_circuit_number" 
							cssClass="validate-alp" cssStyle="width:240px" maxlength="128"></s:textfield>
					</td>
				</tr>
				<tr>
					<td style="white-space: nowrap; width: 150px;">
						Unique Circuit ID
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_unique_circuit_id" cssClass="validate-alp" 
							cssStyle="width:240px" maxlength="128"></s:textfield>
					</td>
					<td style="white-space: nowrap; width: 150px;">
						Service ID
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_service_id" cssClass="validate-alp" 
							cssStyle="width:240px" maxlength="128"></s:textfield>
					</td>
				</tr>
				<tr>
					<td style="white-space: nowrap; width: 150px;">
						Service ID MRR
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_service_id_mrr" cssClass="validate-alp" 
							cssStyle="width:240px" maxlength="128"></s:textfield>
					</td>
					<td style="white-space: nowrap; width: 150px;">
						Service ID MRR Province
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_service_id_mrr_province" cssClass="validate-alp" 
							cssStyle="width:240px" maxlength="128"></s:textfield>
					</td>
				</tr>
				<tr>
					<td style="white-space: nowrap; width: 150px;">
						Revenue Match Date
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_revenue_match_date" 
							cssClass="disabled-style" cssStyle="width:240px" readonly="true"></s:textfield>
					</td>
					<td style="white-space: nowrap; width: 150px;">
						Circuit Id Status
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_circuit_id_status" cssClass="validate-alp" 
							cssStyle="width:240px" maxlength="64"></s:textfield>
					</td>
				</tr>
				<tr>
					<td style="white-space: nowrap; width: 150px;">
						Service Id Match Status
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_service_id_match_status" 
							cssClass="disabled-style" cssStyle="width:240px" readonly="true"></s:textfield>
					</td>
					<td style="white-space: nowrap; width: 150px;">
						Access Type
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_access_type" cssClass="disabled-style" 
							cssStyle="width:240px" readonly="true"></s:textfield>
					</td>
				</tr>
				<tr>
					<td style="white-space: nowrap; width: 150px;">
						Install Date
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<input id="detail_install_date" cssClass="validate-date-ca" 
							cssStyle="width:165px"></input>
						yyyy/mm/dd
					</td>
					<td style="white-space: nowrap; width: 150px;">
						First Invoice Date
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_first_invoice_date" 
							cssClass="disabled-style" cssStyle="width:240px" readonly="true"></s:textfield>
					</td>
				</tr>
				<tr>
					<td style="white-space: nowrap; width: 150px;">
						First Invoice Number
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_first_invoice_number" 
							cssClass="disabled-style" cssStyle="width:240px" readonly="true"></s:textfield>
					</td>
					<td style="white-space: nowrap; width: 150px;">
						Order Number
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_order_number" cssClass="validate-alp" 
							cssStyle="width:240px" maxlength="128"></s:textfield>
					</td>
				</tr>
				<tr>
					<td style="white-space: nowrap; width: 150px;">
						Order Type
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_order_type" cssClass="validate-alp" 
							cssStyle="width:240px" maxlength="128"></s:textfield>
					</td>
					<td style="white-space: nowrap; width: 150px;">
						Quote Number
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_quote_number" cssClass="validate-alp" 
							cssStyle="width:240px" maxlength="128"></s:textfield>
					</td>
				</tr>
				<tr>
					<td style="white-space: nowrap; width: 150px;">
						Disconnection Date :
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<input id="detail_disconnection_date" cssClass="validate-date-ca" 
							cssStyle="width:165px"></input>
						yyyy/mm/dd
					</td>
					<td style="white-space: nowrap; width: 150px;">
						Validation Source System
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_validation_source_system" 
							cssClass="validate-alp" cssStyle="width:240px" maxlength="128"></s:textfield>
					</td>
				</tr>
				<tr>
					<td style="white-space: nowrap; width: 150px;">
						Cost Type
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<!-- <s:textfield id="detail_cost_type" cssClass="validate-alp" cssStyle="width:240px" ></s:textfield>-->
						<div id="detail_cost_type"></div>
					</td>
					<td style="white-space: nowrap; width: 150px;">
						Service Description :
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_service_description" 
							cssClass="validate-alp" cssStyle="width:240px" maxlength="256"></s:textfield>
					</td>
				</tr>
				<tr>
					<td style="white-space: nowrap; width: 150px;">
						Product Category :
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_product_category" cssClass="validate-alp" 
							cssStyle="width:240px" maxlength="128"></s:textfield>
					</td>
					<td style="white-space: nowrap; width: 150px;">
						Sub Product Category
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_sub_product_category" 
							cssClass="validate-alp" cssStyle="width:240px" maxlength="128"></s:textfield>
					</td>
				</tr>
				<tr>
					<td style="white-space: nowrap; width: 150px;">
						Project
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_project" cssClass="validate-alp" 
							cssStyle="width:240px" maxlength="128"></s:textfield>
					</td>
					<td style="white-space: nowrap; width: 150px;">
						Project Category Status
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_project_category_status" 
							cssClass="validate-alp" cssStyle="width:240px" maxlength="128"></s:textfield>
					</td>
				</tr>
				<tr>
					
					<td style="white-space: nowrap; width: 150px;">
						A Address Street Number
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_a_address_street_number" 
							cssClass="validate-alp" cssStyle="width:240px" maxlength="32"></s:textfield>
					</td>
					
					<td style="white-space: nowrap; width: 150px;">
                        A Address Street Name
                    </td>
                    <td align="left"
                        style="position: relative; height: 25px; width: 260px" colspan="2">
                        <s:textfield id="detail_a_address_street_name" 
                            cssClass="validate-alp" cssStyle="width:240px" maxlength="128"></s:textfield>
                    </td>
				</tr>
				<tr>
					
					<td style="white-space: nowrap; width: 150px;">
						A Address Unit
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_a_address_unit" cssClass="validate-alp" 
							cssStyle="width:240px" maxlength="64"></s:textfield>
					</td>
					
					<td style="white-space: nowrap; width: 150px;">
                        A Address City
                    </td>
                    <td align="left"
                        style="position: relative; height: 25px; width: 260px" colspan="2">
                        <s:textfield id="detail_a_address_city" cssClass="validate-alp" 
                            cssStyle="width:240px" maxlength="64"></s:textfield>
                    </td>
				</tr>
				<tr>
					
					<td style="white-space: nowrap; width: 150px;">
						A Address Province
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_a_address_province" 
							cssClass="validate-alp" cssStyle="width:240px" maxlength="64"></s:textfield>
					</td>
					
					<td style="white-space: nowrap; width: 150px;">
                        A Address Postal Code
                    </td>
                    <td align="left"
                        style="position: relative; height: 25px; width: 260px" colspan="2">
                        <s:textfield id="detail_a_address_postal_code" 
                            cssClass="validate-alp" cssStyle="width:240px" maxlength="16"></s:textfield>
                    </td>
				</tr>
				<tr>
					
					<td style="white-space: nowrap; width: 150px;">
						A Address Country
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_a_country" cssClass="validate-alp" 
							cssStyle="width:240px" maxlength="64"></s:textfield>
					</td>
					
					<td style="white-space: nowrap; width: 150px;">
                        Z Address Street Number
                    </td>
                    <td align="left"
                        style="position: relative; height: 25px; width: 260px" colspan="2">
                        <s:textfield id="detail_z_street_number" cssClass="validate-alp" 
                            cssStyle="width:240px" maxlength="32"></s:textfield>
                    </td>
				</tr>
				<tr>
					
					<td style="white-space: nowrap; width: 150px;">
						Z Address Street Name
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_z_street_name" cssClass="validate-alp" 
							cssStyle="width:240px" maxlength="128"></s:textfield>
					</td>
					
					<td style="white-space: nowrap; width: 150px;">
                        Z Address Unit
                    </td>
                    <td align="left"
                        style="position: relative; height: 25px; width: 260px" colspan="2">
                        <s:textfield id="detail_z_unit" cssClass="validate-alp" 
                            cssStyle="width:240px" maxlength="128"></s:textfield>
                    </td>
				</tr>
				<tr>
					
					<td style="white-space: nowrap; width: 150px;">
						Z Address City
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_z_city" cssClass="validate-alp" 
							cssStyle="width:240px" maxlength="64"></s:textfield>
					</td>
					
					<td style="white-space: nowrap; width: 150px;">
                        Z Address Province
                    </td>
                    <td align="left"
                        style="position: relative; height: 25px; width: 260px" colspan="2">
                        <s:textfield id="detail_z_province" cssClass="validate-alp" 
                            cssStyle="width:240px" maxlength="64"></s:textfield>
                    </td>
				</tr>
				<tr>
					
					<td style="white-space: nowrap; width: 150px;">
						Z Address Postal Code
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_z_postal_code" cssClass="validate-alp" 
							cssStyle="width:240px" maxlength="16"></s:textfield>
					</td>
					
					<td style="white-space: nowrap; width: 150px;">
                        Z Address Country
                    </td>
                    <td align="left"
                        style="position: relative; height: 25px; width: 260px" colspan="2">
                        <s:textfield id="detail_z_country" cssClass="validate-alp" 
                            cssStyle="width:240px" maxlength="64"></s:textfield>
                    </td>
				</tr>
				<tr>
					
					<td style="white-space: nowrap; width: 150px;">
						Region
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_region" cssClass="validate-alp" 
							cssStyle="width:240px" maxlength="64"></s:textfield>
					</td>
					
					<td style="white-space: nowrap; width: 150px;">
                        Aggregator CircuitID
                    </td>
                    <td align="left"
                        style="position: relative; height: 25px; width: 260px" colspan="2">
                        <s:textfield id="detail_aggregator_circuit_id" 
                            cssClass="validate-alp" cssStyle="width:240px" maxlength="128"></s:textfield>
                    </td>
				</tr>
				<tr>
					
					<td style="white-space: nowrap; width: 150px;">
						Time Slot or VLAN Assignment
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_time_slot_vlan_assignment" 
							cssClass="validate-alp" cssStyle="width:240px" maxlength="128"></s:textfield>
					</td>
					
					<td style="white-space: nowrap; width: 150px;">
                        Comments
                    </td>
                    <td align="left"
                        style="position: relative; height: 25px; width: 260px" colspan="2">
                        <s:textfield id="detail_comments" cssClass="validate-alp" 
                            cssStyle="width:240px" maxlength="2048"></s:textfield>
                    </td>
				</tr>
				<tr>
					
					<td style="white-space: nowrap; width: 150px;">
						Trunk Group CLLI
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_trunk_group_clli" cssClass="validate-alp" 
							cssStyle="width:240px" maxlength="255"></s:textfield>
					</td>
					
					<td style="white-space: nowrap; width: 150px;">
                        Customer Billing Account #
                    </td>
                    <td align="left"
                        style="position: relative; height: 25px; width: 260px" colspan="2">
                        <s:textfield id="detail_customer_billing_account" 
                            cssClass="validate-alp" cssStyle="width:240px" maxlength="128"></s:textfield>
                    </td>
				</tr>
				<tr>
					
					<td style="white-space: nowrap; width: 150px;">
						Business Segment
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_business_segment" cssClass="validate-alp" 
							cssStyle="width:240px" maxlength="128"></s:textfield>
					</td>
					
					<td style="white-space: nowrap; width: 150px;">
                        End User
                    </td>
                    <td align="left"
                        style="position: relative; height: 25px; width: 260px" colspan="2">
                        <s:textfield id="detail_end_user" cssClass="validate-alp" 
                            cssStyle="width:240px" maxlength="255"></s:textfield>
                    </td>
				</tr>
				<tr>
					
					<td style="white-space: nowrap; width: 150px;">
						SCOA
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<!-- <s:textfield id="detail_scoa" cssClass="validate-alp"
							cssStyle="width:240px"></s:textfield> -->
						<div id="VL_vendorList_account_code"></div>
					</td>
					
					<td style="white-space: nowrap; width: 150px;">
                        Owner
                    </td>
                    <td align="left"
                        style="position: relative; height: 25px; width: 260px" colspan="2">
                        <s:textfield id="detail_owner" cssClass="validate-alp" 
                            cssStyle="width:240px" maxlength="128"></s:textfield>
                    </td>
				</tr>
				<tr>
					
					<td style="white-space: nowrap; width: 150px;">
						Owner E-Mail
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_owner_email" cssClass="validate-alp" 
							cssStyle="width:240px"></s:textfield>
					</td>
					
					<td style="white-space: nowrap; width: 150px;">
                        Last Signoff Date
                    </td>
                    <td align="left"
                        style="position: relative; height: 25px; width: 260px" colspan="2">
                        <input id="detail_last_signoff_date" class="disabled-style" cssClass="validate-date-ca" 
                            cssStyle="width:165px" readonly="readonly"></input>
                        yyyy/mm/dd
                    </td>
				</tr>
				<tr>
					
					
					<td style="white-space: nowrap; width: 150px;">
                        USOC
                    </td>
                    <td align="left"
                        style="position: relative; height: 25px; width: 260px" colspan="2">
                        <s:textfield id="detail_usoc" cssClass="disabled-style" 
                            cssStyle="width:240px" readonly="true"></s:textfield>
                    </td>
                    
                    <td style="white-space: nowrap; width: 150px;">
                        Serving Wire Centre
                    </td>
                    <td align="left"
                        style="position: relative; height: 25px; width: 260px" colspan="2">
                        <s:textfield id="detail_serving_wire_centre" 
                            cssClass="validate-alp" cssStyle="width:240px" maxlength="128"></s:textfield>
                    </td>
					
				</tr>
				
				<tr>
					<td style="white-space: nowrap; width: 150px;">
						Intercompany Business Unit
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_intercompany_business_unit" 
							cssClass="validate-alp" cssStyle="width:240px" maxlength="128"></s:textfield>
					</td>
					
					<td style="white-space: nowrap; width: 150px;">
						Intercompany Channel
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_intercompany_channel" 
							cssClass="validate-alp" cssStyle="width:240px" maxlength="128"></s:textfield>
					</td>
				</tr>
				<tr>
					<td style="white-space: nowrap; width: 150px;">
						Modified Date
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_modified_date" cssClass="disabled-style" 
							cssStyle="width:240px" readonly="true"></s:textfield>
					</td>
					
					<td style="white-space: nowrap; width: 150px;">
						Modified User
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_modified_user" cssClass="disabled-style" 
							cssStyle="width:240px" readonly="true"></s:textfield>
					</td>
				</tr>
				
				<tr>
					<td style="white-space: nowrap; width: 150px;">
						FSA Code
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_fsa_code" maxlength="128" 
							cssClass="validate-alp" cssStyle="width:240px"></s:textfield>
					</td>
					
					<td style="white-space: nowrap; width: 150px;">
						Serviceability Fibre
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_serviceability_fibre" maxlength="128" 
							cssClass="validate-alp" cssStyle="width:240px"></s:textfield>
					</td>
				</tr>
				<tr>
					<td style="white-space: nowrap; width: 150px;">
						Serviceability Cable
					</td>
					<td align="left"
						style="position: relative; height: 25px; width: 260px" colspan="2">
						<s:textfield id="detail_serviceability_cable" maxlength="128" 
							cssClass="validate-alp" cssStyle="width:240px"></s:textfield>
					</td>
				</tr>
			</table>
		</div>
	</s:form>
	<div style="float: left; margin-top: 5px; margin-left: 10px">
		<sec:authorize ifAllGranted="FUNCTION_5010">
			<input type="button" value=" Save "
				onclick="_updateMasterInventory()" />
		</sec:authorize>
	</div>
<%--</div>--%>



<script type="text/javascript">
$(function(){
	form0_searchInventoryDashboardVO_banList = $('#VL_banList_Invoice').flexbox(BAN_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 240,
		onSelect : function(){
		}
	});
	form0_searchInventoryDashboardVO_banList.setValue("all");
});

$(function(){
	form0_searchInventoryDashboardVO_CompleteFlag = $('#VL_complete_flag').flexbox(Complete_Array, {
		maxCacheBytes : 200000,
		allowInput: false,
		onlyShowMatchResult: false,
		highlightMatches: false,
		paging: true,
		width : 240,
		onSelect : function(){
		}
	});
	form0_searchInventoryDashboardVO_CompleteFlag.setValue("all");
});


$(function(){
	form0_searchInventoryDashboardVO_costType = $('#detail_cost_type').flexbox(cost_type_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		onlyShowMatchResult: false,
		paging: false,
		width : 240,
		onSelect : function(){
			removeClassName('detail_cost_type_input','validation-failed');
			addClassName('detail_cost_type_input','validation-passed');
		}
	});
});

/* $(function(){
	form0_searchInventoryDashboardVO_product = $('#VL_productList').flexbox(PRODUCT_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 240,
		onSelect : function(){
		}
	});
	form0_searchInventoryDashboardVO_product.setValue("all");
}); */

$(function(){
	form0_searchInventoryDashboardVO_vendorId = $('#VL_summaryVendorList_1').flexbox(VL_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 240,
		onSelect : function(){
		}
	});
	form0_searchInventoryDashboardVO_vendorId.setValue("all");
});

$(function(){
	midetailform_searchInventoryDashboardVO_vendorId = $('#MIDetail_summaryVendorList_1').flexbox(VL_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 240,
		onSelect : function(){
		}
	});
	midetailform_searchInventoryDashboardVO_vendorId.setValue("all");
});

$(function(){
	midetailform_searchInventoryDashboardVO_banList = $('#MIDetail_banList_Invoice').flexbox(BAN_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 240,
		onSelect : function(){
		}
	});
	midetailform_searchInventoryDashboardVO_banList.setValue("all");
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

var p = {
		 control:{},
		 complete_Array:{},
		 dataSum:0,
		 init: function(isInit) {
			 $("#div_dashboard_id").empty();
			if(isInit == false && p.complete_Array == null) {
				$("#div_dashboard_id").append('<b style="font-size:15;color:grey;">Dashboard</b>');
				$("#div_dashboard_id").append('<div style="text-align: center; height: 200px; padding-top: 100px;"><b style="font-size:12">No Result</b></div>');
			} else {
				p.control = DIV('dashboard-filter-list border-box').show().appendTo($("#div_dashboard_id"));
				p.dataSum = p.complete_Array.results[0][1] + p.complete_Array.results[1][1];
				p._createChart();
			}

			/*p.control = DIV('dashboard-filter-list border-box').show().appendTo($("#div_dashboard_id"));
			if(isInit == false && p.complete_Array == null) {
				alert("a");
			}
			p.dataSum = p.complete_Array.results[0][1] + p.complete_Array.results[1][1];
			p._createChart();*/
			
		},
		_createChart: function() {
			var chart = {
		       plotBackgroundColor: null,
		       plotBorderWidth: null,
		       plotShadow: false,
		       backgroundColor: "transparent"
		   };
		   var title = {
		      text: 'Dashboard',
		      align: 'left',
		      style: {
		           color: '#000',
		           fontFamily: 'Arial, Verdana, Helvetica, sans-serif',
		           fontSize: '15px',
		           fontWeight: 'bold'
		      }
		        
		   };      
		   var tooltip = {
		      pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		   };
		   
		   var credits = {
	            enabled: false 
	       };
		   
		   var plotOptions = {
		      pie:{
					size:220,
					startAngle: -90,
					endAngle: 270,
					cursor: "pointer"
				},
				series : {
					dataLabels : {
						distance: 20,
						style: {
							'fontSize':'9px',
							'width':'100px',
							'white-space':'pre-wrap'
						},
						enabled: true,
						format: '<span style="color:{point.color};font-size:9px">{point.name}</span>' +
							'<br/><span style="font-size:9px;font-weight:normal;">{point.other}/{point.y:.2f}%</span>'
					}
				}
		   };
		   
		   for(var i = 0; i < 2; i++) {
		   		p.complete_Array.results[i][1] = p.complete_Array.results[i][1]/p.dataSum *100;
		   }  
		   var series= [{
		      type: 'pie',
		      name: 'Percent',
		      data: p.complete_Array.results
		   }];     
		      
		   var json = {};   
		   json.chart = chart; 
		   json.credits = credits;
		   json.title = title;     
		   json.tooltip = tooltip;  
		   json.series = series;
		   json.plotOptions = plotOptions;
		   $('#div_dashboard_id').highcharts(json);
		   
		}
		
	};

	$(function(){
		$('#download_button').hide();
		p.complete_Array = {
			    
				"results": [
					<c:forEach items="${completeList}" var="c">
					[
					    "${c.key}",
					    ${c.value}
					],
					</c:forEach>
				]
		};
		p.init(true);
	});

	$(function(){
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