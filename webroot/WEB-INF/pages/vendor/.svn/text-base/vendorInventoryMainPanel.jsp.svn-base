<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!---->
<link rel="stylesheet" type="text/css" href="include/javascript/Calendar/styles/calendar.css">
<link rel="stylesheet" type="text/css" href="include/javascript/flexbox/css/jquery.flexbox.css"><link>
<link rel="stylesheet" href="include/javascript/ccm/colortip/colortip-1.0-jquery.css" type="text/css"></link>
<script type="text/javascript" src="include/javascript/ccm/colortip/colortip-1.0-jquery.js"></script>
<script type="text/javascript" src="include/javascript/flexbox/js/jquery.flexbox.js"></script>
<script type="text/javascript" src="include/javascript/Calendar/simplecalendar.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/ccm/quicklink.js" language="javascript"></script>
<SCRIPT type="text/javascript" src="include/javascript/ccm/common/simpleValidation.js"></SCRIPT>
<SCRIPT type="text/javascript" src="include/javascript/ccm/common/common.js"></SCRIPT>
<script type="text/javascript" src="include/javascript/ccm/searchVendorInventory.js"></script>
<script type="text/javascript" src="include/javascript/yui2/tabview-min.js"></script>
<script type="text/javascript" src="include/javascript/jquery/jquery.ui.core.js"></script>
<script type="text/javascript" src="include/javascript/jquery/jquery.ui.widget.js"></script>
<script type="text/javascript" src="include/javascript/jquery/jquery.ui.accordion.js"></script>
<script type="text/javascript" src="include/javascript/ccm/searchVendorInventory.js"></script>

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

var PL_Array = {
	"results": [
		{
		    "id": "all",
		    "name": ""
		}
		<c:forEach items="${productList}" var="v">
		,{
		    "id": "${v.key}",
		    "name": "${v.value}"
		}
		</c:forEach>
	],
	"total": ${fn:length(productList)}
};

var AL_Array = {
	"results": [
		{
		    "id": "all",
		    "name": ""
		}
		<c:forEach items="${accessList}" var="v">
		,{
		    "id": "${v.key}",
		    "name": "${v.value}"
		}
		</c:forEach>
	],
	"total": ${fn:length(accessList)}
};

var BWL_Array = {
	"results": [
		{
		    "id": "all",
		    "name": ""
		}
		<c:forEach items="${bandwidthList}" var="v">
		,{
		    "id": "${v.key}",
		    "name": "${v.value}"
		}
		</c:forEach>
	],
	"total": ${fn:length(bandwidthList)}
};

var QL_Array = {
	"results": [
		{
		    "id": "all",
		    "name": ""
		}
		<c:forEach items="${qodList}" var="v">
		,{
		    "id": "${v.key}",
		    "name": "${v.value}"
		}
		</c:forEach>
	],
	"total": ${fn:length(qodList)}
};

$(function(){
	form0_searchVendorInventory_vendorId = $('#VL_vendorList_1').flexbox(VL_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 250,
		onSelect : function(){
		}
	});
	form0_searchVendorInventory_vendorId.setValue("all");
});

$(function(){
	form0_searchVendorInventory_productId = $('#PL_productList_s').flexbox(PL_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 250,
		onSelect : function(){
		}
	});
	form0_searchVendorInventory_productId.setValue("all");
});

$(function(){
	form0_searchVendorInventory_accessId = $('#AL_accessList_s').flexbox(AL_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 250,
		onSelect : function(){
		}
	});
	form0_searchVendorInventory_accessId.setValue("all");
});

$(function(){
	form0_searchVendorInventory_bandwidthId = $('#BWL_productList_s').flexbox(BWL_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 250,
		onSelect : function(){
		}
	});
	form0_searchVendorInventory_bandwidthId.setValue("all");
});

$(function(){
	form0_searchVendorInventory_qosId = $('#QL_productList_s').flexbox(QL_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 250,
		onSelect : function(){
		}
	});
	form0_searchVendorInventory_qosId.setValue("all");
});

//var Country_Array = {"results":[{"id":"all","name":""},{"id":"Canada","name":"Canada"},{"id":"United States","name":"United States"}],"total":2};
//var Province_Array = {"Canada":{"results":[{"id":"all","name":""},{"id":"Alberta","name":"Alberta"},{"id":"British Columbia","name":"British Columbia"}],"total":2}};
//var City_Array = {"British Columbia":{"results":[{"id":"all","name":""},{"id":"Abbotsford","name":"Abbotsford"},{"id":"Armstrong","name":"Armstrong"}],"total":2}};

var Country_Array1 = {
	"results": [
		{
		    "id": "all",
		    "name": ""
		}
		<c:forEach items="${countryList}" var="v">
		,{
		    "id": "${v.key}",
		    "name": "${v.value}"
		}
		</c:forEach>
	],
	"total": ${fn:length(countryList)}
};
$(function(){
	form0_searchVendorInventory_country = $('#Inventory_Country').flexbox(Country_Array1, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 100,
	//	noMachesKeepValue : true,
		onSelect : function(){
		//	$("#Inventory_Province").empty();
		//	$("#Inventory_City").empty();
			getProvince(form0_searchVendorInventory_country.getValue());
//			if (Province_Array[form0_searchVendorInventory_country.getValue()]) {
//				changeProvince(Province_Array[form0_searchVendorInventory_country.getValue()]);
//			} else {
//				changeProvince({"results":[{"id":"all","name":""}],"total":1});
//			}
//			if (City_Array[form0_searchVendorInventory_province.getValue()]) {
//				changeCity(City_Array[form0_searchVendorInventory_province.getValue()]);
//			} else {
//				changeCity({"results":[{"id":"all","name":""}],"total":1});
//			}
		}
	});
	form0_searchVendorInventory_country.setValue("all");
});

function changeProvince(arr) {
	form0_searchVendorInventory_province = $('#Inventory_Province').flexbox(arr, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 100,
	//	noMachesKeepValue : true,
		onSelect : function(){
		//	$("#Inventory_City").empty();
			getCity(form0_searchVendorInventory_province.getValue());
//			if (City_Array[form0_searchVendorInventory_province.getValue()]) {
//				changeCity(City_Array[form0_searchVendorInventory_province.getValue()]);
//			} else {
//				changeCity({"results":[{"id":"all","name":""}],"total":1});
//			}
		}
	});
	form0_searchVendorInventory_province.setValue("all");
}

function changeCity(arr) {
	form0_searchVendorInventory_city = $('#Inventory_City').flexbox(arr, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 100,
	//	noMachesKeepValue : true,
		onSelect : function(){
		}
	});
	form0_searchVendorInventory_city.setValue("all");
}

$().ready(function(){
	changeProvince({"results":[{"id":"all","name":""}],"total":1});
	changeCity({"results":[{"id":"all","name":""}],"total":1});
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
		Vendor Inventory
	</h3>
	<div style="border-top: 0px none;">
		<div id="ctl00_MainContent_SearchInvoiceControl1_UpdatePanel1">
					<s:form id="form0" action="searchCircuit.action" theme="simple" onsubmit="startSearch();return false;">
						<table class="tabDetailView" border=" 0" width="100%" cellpadding="5" cellspacing="0">
							<tr valign=top>
                            <td class=tabDetailViewDL width="52%"><div class=searchItemPanel>
                                <table border=0 cellspacing=3 cellpadding=0 width="100%">
                                  <tbody>
                                    <tr>
                                      <td width="30%">Vendor Name </td>
                                      <td style="POSITION: relative" align=left>
                                        <div id="VL_vendorList_1"></div>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td>Product </td>
                                      <td>
                                      	<div id="PL_productList_s"></div>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td>Access </td>
                                      <td>
                                      	<div id="AL_accessList_s"></div>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td>Bandwidth </td>
                                      <td>
                                      	<div id="BWL_productList_s"></div>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td>QoS </td>
                                      <td>
                                      	<div id="QL_productList_s"></div>
                                      </td>
                                    </tr>
                                  </tbody>
                                </table>
                            </div></td>
                            <td class=tabDetailViewDL><div class=searchItemPanel>
                                <table border=0 cellspacing=2 cellpadding=0 width="100%">
                                  <tbody>
                                    <tr>
                                      <td height=25 colspan=3>Date Issued &nbsp;&nbsp;
                                        <input type="checkbox" id="__issued_date" name="lockBox" class="ClearCheckbox" onclick="clearDisabled('__issued_date',1);"/>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td width="11%"><input type="radio" id="selectIssuedDateRadio" class="noBorderRadioButton" name="selectIssuedDateRadio" checked="checked" value="DATEFRAME">
                                      </td>
                                      <td width="17%">From </td>
                                      <td nowrap="nowrap">
										<input id="searchVendorInventoryVO_IssuedDateStartsOn" name="searchVendorInventoryVO.dateIssuedFrom" cssClass="validate-date-ca" class="Clear1 Date" disabled="disabled" style="background:#ccc;width:152px;" onfocus="changRadio(2)"/>
										<a onClick="if($('#__issued_date')[0].checked) g_Calendar.show(event,'searchVendorInventoryVO_IssuedDateStartsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;" href="javascript:%20void(0);"><img
												src="include/images/cal.gif" id="DateIssuedStartsOnImg" border="0" align="middle">
										</a> yyyy/mm/dd
										</td>
                                    </tr>
                                    <tr>
                                      <td>&nbsp;</td>
                                      <td>To </td>
                                      <td>
											<input id="searchVendorInventoryVO_IssuedDateEndsOn" name="searchVendorInventoryVO.dateIssuedTo" cssClass="validate-date-ca" class="Clear1 Date" disabled="disabled" style="background:#ccc;width:152px;" onfocus="changRadio(2)"/>
											<a onClick="if($('#__issued_date')[0].checked) g_Calendar.show(event,'searchVendorInventoryVO_IssuedDateEndsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;" href="javascript:%20void(0);"><img
													src="include/images/cal.gif" id="DateIssuedEndsOnImg" border="0" align="middle" >
											</a> yyyy/mm/dd
										</td>
									</tr>
                                  </tbody>
                                </table>
                            </div></td>
                          </tr>
                          <tr>
                            <td class=tabDetailViewDL><div class=searchItemPanel>
                                <table border=0 cellspacing=3 cellpadding=0 width="100%">
                                  <tbody>
                                    
                                    <tr>
                                      <td width="30%">Term in Month</td>
                                      <td>
                                      	<span class="select1"><s:select id="Term_in_Month" name="searchVendorInventoryVO.term" list="termList" headerKey="all"
																headerValue="All" listKey="key" listValue="value" cssStyle="width:150px"/></span>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td>Inventory ID</td>
                                      <td><s:textfield id="Inventory_Id" name="searchVendorInventoryVO.inventoryId" onblur="checkTheDisputeAmount(this)" cssStyle="width:150px"></s:textfield></td>
                                    </tr>
                                    <tr>
                                      <td>Quote Number</td>
                                      <td><s:textfield id="Quote_Number" name="searchVendorInventoryVO.quoteNumber" cssStyle="width:150px"></s:textfield></td>
                                    </tr>
                                    <tr>
                                      <td>Opportunity Name </td>
                                      <td><s:textfield id="Opportunity_Name" name="searchVendorInventoryVO.opportunityName" cssStyle="width:150px"></s:textfield></td>
                                    </tr>
                                  </tbody>
                                </table>
                            </div></td>
                            <td class=tabDetailViewDL><div class=searchItemPanel>
                                <table width="100%" cellpadding="0" cellspacing="2" border="0">
											<tr>
												<td colspan="3" height="25">
													Expiry Date &nbsp;&nbsp;<input type="checkbox" id="__expiry_date" name="lockBox" class="ClearCheckbox" onclick="clearDisabled('__expiry_date',2);"/>
												</td>
											</tr>
											<tr>
												<td width="11%">
													<input type="radio" class="noBorderRadioButton" id="selectExpiryDateRadio"  name="selectExpiryDateRadio" checked="checked" value="DATEFRAME">
												</td>
												<td width="17%">
													From
												</td>
												<td nowrap="nowrap">
													<input id="searchVendorInventoryVO_expiryDateStartsOn" name="searchVendorInventoryVO.expiryDateFrom" cssClass="validate-date-ca" class="Clear2 Date" disabled="disabled" style="background:#ccc;width:152px;" onfocus="changRadio(4)"/>
													<a onClick="if($('#__expiry_date')[0].checked) g_Calendar.show(event,'searchVendorInventoryVO_expiryDateStartsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;" href="javascript:%20void(0);"><img
															src="include/images/cal.gif" id="expiryDateStartsOnImg" border="0" align="middle">
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
													<input id="searchVendorInventoryVO_expiryDateEndsOn" name="searchVendorInventoryVO.expiryDateTo" cssClass="validate-date-ca" class="Clear2 Date" disabled="disabled" style="background:#ccc;width:152px;" onfocus="changRadio(4)"/>	
													<a onClick="if($('#__expiry_date')[0].checked) g_Calendar.show(event,'searchVendorInventoryVO_expiryDateEndsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;" href="javascript:%20void(0);"><img
															src="include/images/cal.gif" id="startDateEndsOnImg" border="0" align="middle">
													</a> yyyy/mm/dd
												</td>
											</tr>
										</table>
                            </div></td>
                          </tr>
                          <tr>
                            <td class=tabDetailViewDL><div class=searchItemPanel>
                              <table width="100%"  cellspacing="2" cellpadding="0" border="0">                              
                                <tr>
                                    <td>Unit</td>
									<td>
                          				<s:textfield id="Inventory_Unit" name="searchVendorInventoryVO.unit" cssStyle="width:100px" ></s:textfield>
                            		</td>
                            		<td>Street Number</td>
									<td>
                          				<s:textfield id="Inventory_Street_Number" name="searchVendorInventoryVO.streetNumber" cssStyle="width:100px" ></s:textfield>
                            		</td>                           		
                            		<td>Street Name</td>
									<td>
                          				<s:textfield id="Inventory_Street_Name" name="searchVendorInventoryVO.streetName" cssStyle="width:100px" ></s:textfield>
                            		</td>
                          		</tr>
                  				<tr>
									<td>Country</td>
									<td>
										<div id="Inventory_Country"></div>
                            		</td>
                            		<td>Province/State</td>
									<td>
										<div id="Inventory_Province"></div>
                            		</td>
                            		<td>City</td>
									<td>
										<div id="Inventory_City"></div>
                            		</td>
                          		</tr>
                          		<tr>
                          			<td>Postal Code</td>
									<td colspan="5">
                          				<s:textfield id="Inventory_Postal_Code" name="searchVendorInventoryVO.postalCode" cssStyle="width:100px" ></s:textfield>
                            		</td>
                          		</tr>
                          		<tr>
                          			<td>Latitude</td>
									<td colspan="2">
                          				from <s:textfield id="Inventory_Latitude_From" name="searchVendorInventoryVO.latitudeFrom" onblur="checkTheDisputeAmount(this)" cssStyle="width:150px" ></s:textfield>
                        			</td>
									<td colspan="2">
                          				to <s:textfield id="Inventory_Latitude_To" name="searchVendorInventoryVO.latitudeTo" onblur="checkTheDisputeAmount(this)" cssStyle="width:150px" ></s:textfield>
                        			</td>
                      			</tr>
                      			<tr>
                          			<td>Longitude</td>
									<td colspan="2">
                          				from <s:textfield id="Inventory_Longitude_From" name="searchVendorInventoryVO.longitudeFrom" onblur="checkTheDisputeAmount(this)" cssStyle="width:150px" ></s:textfield>
                        			</td>
									<td colspan="2">
                          				to <s:textfield id="Inventory_Longitude_To" name="searchVendorInventoryVO.longitudeTo" onblur="checkTheDisputeAmount(this)" cssStyle="width:150px" ></s:textfield>
                        			</td>
                      			</tr>
							</table>
                            </div>
                            </td>
                            <td class=tabDetailViewDL><div class=searchItemPanel>
                                <table width="100%" cellpadding="0" cellspacing="2" border="0">
                                	<tr>
                          			<td>MRC</td>
									<td colspan="2">
                          				from <s:textfield id="Inventory_MRC_From" name="searchVendorInventoryVO.mrcFrom" onblur="checkTheDisputeAmount(this)" cssStyle="width:150px" ></s:textfield>
                        			</td>
									<td colspan="2">
                          				to <s:textfield id="Inventory_MRC_To" name="searchVendorInventoryVO.mrcTo" onblur="checkTheDisputeAmount(this)" cssStyle="width:150px" ></s:textfield>
                        			</td>
	                      			</tr>
	                      			<tr>
	                          			<td>NRC</td>
										<td colspan="2">
	                          				from <s:textfield id="Inventory_NRC_From" name="searchVendorInventoryVO.nrcFrom" onblur="checkTheDisputeAmount(this)" cssStyle="width:150px" ></s:textfield>
	                        			</td>
										<td colspan="2">
	                          				to <s:textfield id="Inventory_NRC_To" name="searchVendorInventoryVO.nrcTo" onblur="checkTheDisputeAmount(this)" cssStyle="width:150px" ></s:textfield>
	                        			</td>
	                      			</tr>
	                      			<tr>
	                          			<td>Construction Cost</td>
										<td colspan="2">
	                          				from <s:textfield id="Inventory_Construction_Cost_From" name="searchVendorInventoryVO.constructionCostFrom" onblur="checkTheDisputeAmount(this)" cssStyle="width:150px" ></s:textfield>
	                        			</td>
										<td colspan="2">
	                          				to <s:textfield id="Inventory_Construction_Cost_To" name="searchVendorInventoryVO.constructionCostTo" onblur="checkTheDisputeAmount(this)" cssStyle="width:150px" ></s:textfield>
	                        			</td>
	                      			</tr>
                          		</tr>
								</table>
                            </div></td>
                          </tr>
						</table>
					</s:form>
				</div>
		</div>
		<div style="width:100%;height:30px;">
				<div style="float: left; margin-top:-5px;">
					<br />
					<input type="button" id="" value="Search" onclick="getVendorInventoryList();" />
					&nbsp;&nbsp;&nbsp;
					<input type="button" id="saveQueryButton" value="Save" onclick="YAHOO.ccm.container.saveQueryDialog.show();" />
					&nbsp;&nbsp;&nbsp;
					<input type="button" value="Clear" onclick="javascript:resetFormElementValue();" />
				</div>
				<div id="loadingImgDiv" style="display: none; float: right;">
					<img src="include/images/loading.gif" />
				</div>
		</div>
		<div style="width:100%;"><br />
			<div id="_vendoryInventoryDiv" style="width:100%;display:none"><br />
				<table border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed; width: 100%;">
					<tr>
						<td>
							<div align="left" style="width: 100%; overflow-x: auto;">
								<div id="_vendorInventory_dataTable"></div>
							</div>
						</td>
					</tr>
				</table>
				<table>
					<tr>
						<td>
							<span id="_vendorInventory_dataTable_page"></span>
						</td>
						<td>
<%--							<sec:authorize ifAllGranted="FUNCTION_5400">--%>
								<input value="Download All to CSV" type="button" onclick="downloadVendorInventoryCsv()"
									style="margin-top:1px; cursor: pointer;">
<%--							</sec:authorize>--%>
						</td>
						<td>
<%--							<sec:authorize ifAllGranted="FUNCTION_5500">--%>
								<input value="Download All to PDF" type="button" onclick="downloadVendorInventoryPDF()"
									style="margin-top:1px; cursor: pointer;">
<%--							</sec:authorize>--%>
						</td>
					</tr>
				</table>
			</div>
		</div>
</div>
<div class="yui-pe-content" id="saveQueryDialog" >
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