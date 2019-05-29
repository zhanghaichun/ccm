<!-- Create By Donghao.guo 2011/09/22 -->
<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<link rel="stylesheet" type="text/css" href="include/javascript/Calendar/styles/calendar.css">
<link rel="stylesheet" type="text/css" href="include/javascript/flexbox/css/jquery.flexbox.css"><link>
<link rel="stylesheet" href="include/javascript/ccm/colortip/colortip-1.0-jquery.css" type="text/css"></link>
<script type="text/javascript" src="include/javascript/ccm/colortip/colortip-1.0-jquery.js"></script>
<script type="text/javascript" src="include/javascript/flexbox/js/jquery.flexbox.js"></script>
<script type="text/javascript" src="include/javascript/Calendar/simplecalendar.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/ccm/quicklink.js" language="javascript"></script>
<SCRIPT type="text/javascript" src="include/javascript/ccm/common/simpleValidation.js"></SCRIPT>
<SCRIPT type="text/javascript" src="include/javascript/ccm/common/common.js"></SCRIPT>
<script type="text/javascript" src="include/javascript/ccm/searchCircuit.js"></script>
<script type="text/javascript" src="include/javascript/yui2/tabview-min.js"></script>
<script type="text/javascript" src="include/javascript/jquery/jquery.ui.core.js"></script>
<script type="text/javascript" src="include/javascript/jquery/jquery.ui.widget.js"></script>
<script type="text/javascript" src="include/javascript/jquery/jquery.ui.accordion.js"></script>

<script type="text/javascript" src="include/javascript/ccm/searchCircuit.js"></script>

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
	form0_searchCircuitVO_vendorId = $('#VL_vendorList_1').flexbox(VL_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 250,
		onSelect : function(){
		}
	});
	form0_searchCircuitVO_vendorId.setValue("all");
});
$(function(){
	form0_searchCircuitVO_banId = $('#VL_banList_Invoice').flexbox(BLD_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 250,
		onSelect : function(){
		}
	});
	form0_searchCircuitVO_banId.setValue("all");
});
/*
var CI_Array = {
		"results": [
			{
			    "id": "all",
			    "name": ""
			}
			<c:forEach items="${customerList}" var="c">
			,{
			    "id": "${c.key}",
			    "name": "${c.value}"
			}
			</c:forEach>
		],
		"total": ${fn:length(customerList)}
	};

$(function(){
	form0_searchCircuitVO_customerId = $('#CI_Customer_Id').flexbox(CI_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 250,
		onSelect : function(){
		}
	});
	form0_searchCircuitVO_customerId.setValue("all");
});
*/
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
		Circuit Inventory
	</h3>
	<div style="border-top: 0px none;">
		<div id="ctl00_MainContent_SearchInvoiceControl1_UpdatePanel1">
					<s:form id="form0" action="searchCircuit.action" theme="simple" onsubmit="startSearch();return false;">
						<table class="tabDetailView" border=" 0" width="100%" cellpadding="5" cellspacing="0">
							<tr valign=top>
                            <td class=tabDetailViewDL width="52%"><div class=searchItemPanel>
                                <table border=0 cellspacing=3 cellpadding=0 width="100%">
                                  <tbody><tr>
                                      <td width="30%">Line Of Business </td>
                                      <td>
                                      	<span class="select1"><s:select id="Line_Of_Business" name="searchCircuitVO.lineOfbusiness" list="lineOfBusiness" headerKey="all"
																headerValue="All" listKey="key" listValue="value" cssStyle="width:250px"/></span>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td width="30%">Vendor Name </td>
                                      <td style="POSITION: relative" align=left>
                                        <div id="VL_vendorList_1"></div>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td>BAN </td>
                                      <td style="POSITION: relative" align=left>
                                      	<div id="VL_banList_Invoice"></div>
                                      </td>
                                    </tr>
                                  </tbody>
                                </table>
                            </div></td>
                            <td class=tabDetailViewDL><div class=searchItemPanel>
                                <table border=0 cellspacing=2 cellpadding=0 width="100%">
                                  <tbody>
                                    <tr>
                                      <td height=25 colspan=3>Order Date &nbsp;&nbsp;
                                        <input type="checkbox" id="__invoice_date" name="lockBox" class="ClearCheckbox" onclick="clearDisabled('__invoice_date',1);"/>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td width="11%"><input type="radio" id="selectInvoiceDateRadio" class="noBorderRadioButton" name="selectInvoiceDateRadio" checked="checked" value="DATEFRAME">
                                      </td>
                                      <td width="17%">From </td>
                                      <td nowrap="nowrap">
										<input id="searchCircuitVO_invoiceDateStartsOn" name="searchCircuitVO.invoiceDateStartsOn" cssClass="validate-date-ca" class="Clear1 Date" disabled="disabled" style="background:#ccc;width:152px;" onfocus="changRadio(2)"/>
										<a onClick="if($('#__invoice_date')[0].checked) g_Calendar.show(event,'searchCircuitVO_invoiceDateStartsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;" href="javascript:%20void(0);"><img
												src="include/images/cal.gif" id="invoiceDateStartsOnImg" border="0" align="middle">
										</a> yyyy/mm/dd
										</td>
                                    </tr>
                                    <tr>
                                      <td>&nbsp;</td>
                                      <td>To </td>
                                      <td>
											<input id="searchCircuitVO_invoiceDateEndsOn" name="searchCircuitVO.invoiceDateEndsOn" cssClass="validate-date-ca" class="Clear1 Date" disabled="disabled" style="background:#ccc;width:152px;" onfocus="changRadio(2)"/>
											<a onClick="if($('#__invoice_date')[0].checked) g_Calendar.show(event,'searchCircuitVO_invoiceDateEndsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;" href="javascript:%20void(0);"><img
													src="include/images/cal.gif" id="invoiceDateEndsOnImg" border="0" align="middle" >
											</a> yyyy/mm/dd
										</td>
									</tr>
                                    <tr>
                                      <td><input type="radio" id="selectInvoiceDateRadio"  class="noBorderRadioButton" name="selectInvoiceDateRadio" value="WITHINPAST">
                                      </td>
                                      <td nowrap>Past </td>
                                      <td nowrap="nowrap">
											<input id="searchCircuitVO_invoiceDateWiPastNumOfDays" name="searchCircuitVO.invoiceDateWiPastNumOfDays" cssClass="validate-number" class="Clear1 Date" disabled="disabled" style="background:#ccc;width:152px;" onfocus="changRadio(3)"/>
											days(0-10000)
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
                                      <td width="30%">Stripped Circuit Number </td>
                                      <td>
                                      	<s:textfield id="VL_Stripped_Circuit_Number" name="searchCircuitVO.strippedCircuit" cssStyle="width:150px"></s:textfield>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td>Service  Type</td>
                                      <td><s:textfield id="Service_Type" name="searchCircuitVO.serviceType" cssStyle="width:150px"></s:textfield></td>
                                    </tr>
                                    <tr>
                                      <td>Circuit  Type</td>
                                      <td><s:textfield id="Circuit_Type" name="searchCircuitVO.circuitType" cssStyle="width:150px"></s:textfield></td>
                                    </tr>
                                    <tr>
                                      <td>Order  Type</td>
                                      <td><s:textfield id="Order_Type" name="searchCircuitVO.orderType" cssStyle="width:150px"></s:textfield></td>
                                    </tr>
                                    <tr>
                                      <td>ASR  Number</td>
                                      <td><s:textfield id="Asr_Number" name="searchCircuitVO.asrNumber" cssStyle="width:150px"></s:textfield></td>
                                    </tr>
                                    <tr>
                                      <td>PON</td>
                                      <td><s:textfield id="PON" name="searchCircuitVO.PON" cssStyle="width:150px"></s:textfield></td>
                                    </tr>
                                    <tr>
                                      <td>End  Customer</td>
                                      <td><s:textfield id="End_Customer" name="searchCircuitVO.endCustomer" cssStyle="width:150px"></s:textfield></td>
                                    </tr>
                                  </tbody>
                                </table>
                            </div></td>
                            <td class=tabDetailViewDL><div class=searchItemPanel>
                                <table width="100%" cellpadding="0" cellspacing="2" border="0">
											<tr>
												<td colspan="3" height="25">
													Circuit Start Date &nbsp;&nbsp;<input type="checkbox" id="__stsrt_date" name="lockBox" class="ClearCheckbox" onclick="clearDisabled('__stsrt_date',2);"/>
												</td>
											</tr>
											<tr>
												<td width="11%">
													<input type="radio" class="noBorderRadioButton"  name="selectStartDateRadio" checked="checked" value="DATEFRAME">
												</td>
												<td width="17%">
													From
												</td>
												<td nowrap="nowrap">
													<input id="searchCircuitVO_startDateStartsOn" name="searchCircuitVO.invoicestartDateStartsOn" cssClass="validate-date-ca" class="Clear2 Date" disabled="disabled" style="background:#ccc;width:152px;" onfocus="changRadio(4)"/>
													<a onClick="if($('#__stsrt_date')[0].checked) g_Calendar.show(event,'searchCircuitVO_startDateStartsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;" href="javascript:%20void(0);"><img
															src="include/images/cal.gif" id="startDateStartsOnImg" border="0" align="middle">
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
													<input id="searchCircuitVO_startDateEndsOn" name="searchCircuitVO.invoicestartDateEndsOn" cssClass="validate-date-ca" class="Clear2 Date" disabled="disabled" style="background:#ccc;width:152px;" onfocus="changRadio(4)"/>	
													<a onClick="if($('#__stsrt_date')[0].checked) g_Calendar.show(event,'searchCircuitVO_startDateEndsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;" href="javascript:%20void(0);"><img
															src="include/images/cal.gif" id="startDateEndsOnImg" border="0" align="middle">
													</a> yyyy/mm/dd
												</td>
											</tr>
											<tr>
												<td>
													<input type="radio" class="noBorderRadioButton" name="selectStartDateRadio" value="WITHINPAST">
												</td>
												<td nowrap="nowrap">
													Past
												</td>
												<td nowrap="nowrap">
													<input id="searchCircuitVO_startDateWiPastNumOfDays" name="searchCircuitVO.invoicestartDateWiPastNumOfDays" cssClass="validate-number" class="Clear2 Date" disabled="disabled" style="background:#ccc;width:152px;" onfocus="changRadio(5)"/>	
													days(0-10000)
												</td>
											</tr>
										</table>
                            </div></td>
                          </tr>
                          <tr>
                            <td class=tabDetailViewDL><div class=searchItemPanel>
                              <table width="100%"  cellspacing="2" cellpadding="0" border="0">
                                    <tr>
										<!--  <td colspan="2" width="30%">Customer Id</td>  -->
										<td colspan="2" width="30%">Service Id</td>
                                    <td>
                                    	<s:textfield id="Customer_Id" name="searchCircuitVO.customerId" cssStyle="width:150px" maxlength="11"></s:textfield>
<%--                                      	<div id="CI_Customer_Id"></div>--%>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td colspan="2" width="30%">A  Address </td>
                                      <td><s:textfield id="A_Address" name="searchCircuitVO.A_address" cssStyle="width:150px"></s:textfield></td>
                                    </tr>
                                    <%--20140820 
                                    <tr>
                                      <td colspan="2">Line Item Code </td>
                                      <td><s:textfield id="Line_Item_Code" name="searchCircuitVO.lineItemCode" cssStyle="width:150px"></s:textfield></td>
                                    </tr>
									<tr>
										<td width="8%"><input type="radio" class="noBorderRadioButton" name="selectSCOARadio" checked="checked" value="FULLSCOA"></td>
										<td width="22%" align="left">
											Full SCOA Code 
										</td>
										<td>
											<s:textfield id="Full_SCOA_Code" name="searchCircuitVO.fullScoaCode"  cssStyle="width:150px" onfocus="changRadio(0)"></s:textfield>
										</td>
									</tr>
									<tr>
									    <td><input type="radio" class="noBorderRadioButton" name="selectSCOARadio" ></td>
									    <td>SCOA Segments</td>
									</tr>
									<tr>
										<td colspan="3">
										<table width="100%">
										<tr>
										<td>Company</td><td><s:textfield id="SCOA_Company" name="searchCircuitVO.company" cssClass="validate-number" cssStyle="width:100px" onfocus="changRadio(1)"></s:textfield></td>
										<td>Location</td><td><s:textfield id="SCOA_Location" name="searchCircuitVO.location" cssClass="validate-number" cssStyle="width:100px" onfocus="changRadio(1)"></s:textfield></td>
										<td>Department</td><td><s:textfield id="SCOA_Departement" name="searchCircuitVO.departement" cssClass="validate-number" cssStyle="width:100px" onfocus="changRadio(1)"></s:textfield></td>
										</tr>
										<tr>
										<td>Product</td><td><s:textfield id="SCOA_Production" name="searchCircuitVO.production" cssClass="validate-number" cssStyle="width:100px" onfocus="changRadio(1)"></s:textfield></td>
										<td>Account</td><td><s:textfield id="SCOA_Account" name="searchCircuitVO.account" cssClass="validate-number" cssStyle="width:100px" onfocus="changRadio(1)"></s:textfield></td>
										<td>Channel</td><td><s:textfield id="SCOA_Channel" name="searchCircuitVO.channel" cssClass="validate-number" cssStyle="width:100px" onfocus="changRadio(1)"></s:textfield></td>
										</tr>
										</table>
										</td>
									</tr>
									--%>
									<!-- 
									<sec:authorize ifAllGranted="FUNCTION_5100">
									<tr>
										<td colspan="2">
											Historical Data(<2010-09-01)
										</td>
										<td>
											<input type="checkbox" id="__historical_invoice" name="lockBox" onClick="historicalChange();" class="ClearCheckbox" />
										</td>
									</tr>
									</sec:authorize>
									<sec:authorize ifNotGranted="FUNCTION_5100">
										<input type="checkbox" id="__historical_invoice" name="lockBox" style="display:none"/>
									</sec:authorize>
									 -->
								</table>
                            </div>
                            </td>
                            <td class=tabDetailViewDL><div class=searchItemPanel>
                                <table width="100%" cellpadding="0" cellspacing="2" border="0">
									<tr>
										<td colspan="3" height="25">
											Circuit End Date &nbsp;&nbsp;<input type="checkbox" id="__end_date" name="lockBox" class="ClearCheckbox"  onclick="clearDisabled('__end_date',3);"/>
										</td>
									</tr>
									<tr>
										<td width="11%">
											<input type="radio" class="noBorderRadioButton" name="selectEndDateRadio" checked="checked" value="DATEFRAME">
										</td>
										<td width="17%">
											From
										</td>
										<td nowrap="nowrap">
											<input id="searchCircuitVO_endDateStartsOn" name="searchCircuitVO.invoiceendDateStartsOn" cssClass="validate-date-ca" class="Clear3 Date" disabled="disabled" style="background:#ccc;width:152px;" onfocus="changRadio(6)"/>	
											<a onClick="if($('#__end_date')[0].checked) g_Calendar.show(event,'searchCircuitVO_endDateStartsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;" href="javascript:%20void(0);"><img
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
											<input id="searchCircuitVO_endDateEndsOn" name="searchCircuitVO.invoiceendDateEndsOn" cssClass="validate-date-ca" class="Clear3 Date" disabled="disabled" style="background:#ccc;width:152px;" onfocus="changRadio(6)"/>	
											<a onClick="if($('#__end_date')[0].checked) g_Calendar.show(event,'searchCircuitVO_endDateEndsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;" href="javascript:%20void(0);"><img
													src="include/images/cal.gif" id="invoiceDueEndsOnImg" border="0" align="middle">
											</a> yyyy/mm/dd
										</td>

									</tr>
									<tr>
										<td>
											<input type="radio" class="noBorderRadioButton" name="selectEndDateRadio" value="WITHINPAST">
										</td>
										<td nowrap="nowrap">
											Past
										</td>
										<td nowrap="nowrap">
											<input id="searchCircuitVO_endDateWiPastNumOfDays" name="searchCircuitVO.invoiceendDateWiPastNumOfDays" cssClass="validate-number" class="Clear3 Date" disabled="disabled" style="background:#ccc;width:152px;" onfocus="changRadio(7)"/>	
											days(0-10000)
										</td>
									</tr>
									<tr>
										<td>
											<input type="radio" class="noBorderRadioButton" name="selectEndDateRadio" value="WITHINNEXT">
										</td>
										<td nowrap="nowrap">
											Next
										</td>
										<td nowrap="nowrap">
											<input id="searchCircuitVO_endDateWiNextNumOfDays" name="searchCircuitVO.invoiceendDateWiNextNumOfDays" cssClass="validate-number" class="Clear3 Date" disabled="disabled" style="background:#ccc;width:152px;" onfocus="changRadio(8)"/>	
											days(0-10000)
										</td>
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
					<!-- 
					<input type="button" id="downloadExcel" value="Search and Download" onclick="downloadHistoricalData();" style="display: none;"/>
					-->
					<%--<input type="button" id="searchButton" value="Get Charge Detail" onclick="startSearch();" />
					&nbsp;&nbsp;&nbsp;
					--%>
					<input type="button" id="circuitButton" value="Get Circuit Detail" onclick="getCircuitList();" />
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
			<table id="_chargeDetail" width="100%" border="0">
				<tr>
					<td nowrap="nowrap" width="100%">
					<div id="slideshow">
						<div id="slidesContainer">
						</div>
					</div>
					</td>
				</tr>
				<tr>
					<td>
					<div id="defcal_section" ></div>
					</td>
				</tr>
			</table>
			<div id="_circuitDetail" style="width:100%;display:none"><br />
				<table border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed; width: 100%;">
					<tr>
						<td>
							<div align="left" style="width: 100%; overflow-x: auto;">
								<div id="_circuitDetail_dataTable"></div>
							</div>
						</td>
					</tr>
				</table>
				<table>
					<tr>
						<td>
							<span id="_circuitDetail_dataTable_page" style="display:block;padding-top:8px;"></span>
						</td>
						<td>
<%--					<input value="Download to CSV" type="button" onclick="downloadVendorCircuitCsv()"
								style="margin-top: 9px; cursor: pointer;">
--%>
							<input value="Download All to CSV" type="button" onclick="downloadAllVendorCircuitCsv()"
								style="margin-top: 9px; cursor: pointer;">
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