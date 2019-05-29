<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link rel="stylesheet" type="text/css" href="include/javascript/Calendar/styles/calendar.css">
<script type="text/javascript" src="include/javascript/Calendar/simplecalendar.js"></script>
<link rel="stylesheet" type="text/css" href="include/javascript/flexbox/css/jquery.flexbox.css"><link>
<script type="text/javascript" src="include/javascript/flexbox/js/jquery.flexbox.js"></script>
<script type="text/javascript" src="include/javascript/ccm/quicklink.js" language="javascript"></script>
<SCRIPT src="include/javascript/ccm/common/simpleValidation.js" type=text/javascript></SCRIPT>
<script type="text/javascript" src="include/javascript/ccm/searchDispute.js"></script>

<div id="pageContainer" class="tabForm" onMouseDown="if(g_Calendar && g_Calendar.hide)g_Calendar.hide();" style="padding-bottom:25px;" >
	<h3 style="padding-top:0px; padding-bottom:4px;">
<!--<div id="pageContainer" class="tabForm" onmousedown="if(g_Calendar && g_Calendar.hide)g_Calendar.hide();" >-->
<!--	<h3>-->
		Dispute Search 
	</h3>
	<div style="border-top: 0px none;">
				<div id="ctl00_MainContent_SearchInvoiceControl1_UpdatePanel1">
					<s:form id="form0" action="" theme="simple" onsubmit="startSearch();return false;">
						<table class="tabDetailView" border="0" width="100%" cellpadding="0" cellspacing="0">
							<tr valign="top">
								<td class="tabDetailViewDL" width="50%">
									<div class="searchItemPanel">
										<table width="100%"  cellspacing="3" cellpadding="0" border="0">
											<tr>
												<td width="25%">
													Vendor
												</td>
												<td>
												<div id="VL_vendorList_Dispute"></div>
												</td>
											</tr>
											<tr>
												<td>
													BAN
												</td>
												<td>
													<span class="select1"> <select id="form0_searchDisputeVO_banId" name="searchDisputeVO.banId" style="width:180px">
														<option value="all">All</option>
													</select> </span>
												</td>
											</tr>
											
											<tr>
												<td>
													All BAN
												</td>
												<td>
													<div id="VL_banList_Dispute"></div>
												</td>
											</tr>
										</table>
									</div>
									</td>
								<td class="tabDetailViewDL">
									<div class="searchItemPanel">
										<table width="100%" cellspacing="2" cellpadding="0" border="0">
											<tr>
												<td width="25%">
													Claim Number
												</td>
												<td>
												<s:textfield name="searchDisputeVO.claimNumber" cssClass="validate-alp" cssStyle="width:180px"></s:textfield>
																			
												</td>
											</tr>				
											<tr>
												<td width="25%">
													Dispute Number
												</td>
												<td>
												<s:textfield name="searchDisputeVO.disputeNumber" cssClass="validate-alp" cssStyle="width:180px"></s:textfield>
																			
												</td>
											</tr>				
											<tr>
												<td>
													Vendor Tracking Number 
												</td>
												<td>
												<s:textfield name="searchDisputeVO.ticketNumber" cssClass="validate-alp" cssStyle="width:180px"></s:textfield>																	
												</td>	
											</tr>	
										</table>
									</div>
								</td>
												
							</tr>
							<tr >
								<td class="tabDetailViewDL">
									<div class="searchItemPanel">
										<table width="100%"  cellspacing="4" cellpadding="0" border="0">							
											<tr>
												<td width="25%">
													Dispute Category
												</td >
												<td><span class="select1">
												<s:select name="searchDisputeVO.disputeReasonId" headerKey="all"
														headerValue="All" list="disputeReasonList" listKey="key"
														listValue="value" required="true" cssStyle="width:180px"/>
													</span>
												</td>
											</tr>
											<tr>
												<td>
													Dispute Status
												</td>
												<td><span class="select1">
													<s:select name="searchDisputeVO.disputeStatusId" headerKey="all"
														headerValue="All" list="disputeStatusList" listKey="key"
														listValue="value" required="true" cssStyle="width:180px"/>
													</span>
												</td>
											</tr>								
										</table>
									</div>
								</td>			
								
								
								<td class="tabDetailViewDL" >
									<div class="searchItemPanel">
										<table width="100%" cellpadding="0" cellspacing="4" border="0">
											<tr>
												<td width="25%">
													Is Short-paid
												</td>
												<td><span class="select1">
													<select style="width:180px;margin-left:-2px;" id="form0_searchDisputeVO_isShortPaid"  name="searchDisputeVO.isShortPaid"  >
														<option value="All" selected="selected">All</option>
														<option value="Yes">Yes</option>
														<option value="No">No</option>
													</select>																				
													</span>
												</td>
											</tr>			 									
											<tr>
												<td>
													Is Recurring
												</td>
												<td><span class="select1">
													<select style="width:180px;margin-left:-2px;" id="form0_searchDisputeVO_isRecurring"  name="searchDisputeVO.isRecurring" >
														<option value="All" selected="selected">All</option>
														<option value="Yes">Yes</option>
														<option value="No">No</option>
													</select>																				
													</span>
												</td>
											</tr>			 		
										
										</table>
									</div>
								</td>
							</tr>
							<tr>	
								<td class="tabDetailViewDL" >
									<div class="searchItemPanel">
									<table width="100%" cellspacing="2" cellpadding="0" border="0">
											<tr>
												<td colspan="3" height="25">
													Dispute Date
												</td>
											</tr>
											<tr>
												<td width="10%">
													<input type="radio" class="noBorderRadioButton"
														class="noBorderRadioButton" name="selectDisputeDateRadio"
														checked="checked" value="DATEFRAME" onclick="changdisputeDateRadio(0)">
												</td>
												<td width="15%">
													From
												</td>
												<td>
													<s:textfield name="searchDisputeVO.disputeDateStartsOn"
														id="searchDisputeVO_disputeDateStartsOn" cssClass="validate-date-ca" onfocus="changdisputeDateRadio(0)"></s:textfield>
													<a
														onClick="g_Calendar.show(event,'searchDisputeVO_disputeDateStartsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;"
														href="javascript:%20void(0);"><img
															src="include/images/cal.gif" id="disputeDateStartsOnImg"
															border="0" align="middle"></a>
															yyyy/mm/dd
												</td>
											</tr>
											<tr>
												<td >
												</td>
												<td >
													To
												</td>
												<td>
													<s:textfield name="searchDisputeVO.disputeDateEndsOn"
														id="searchDisputeVO_disputeDateEndsOn" cssClass="validate-date-ca" onfocus="changdisputeDateRadio(0)"></s:textfield>
													<a
														onClick="g_Calendar.show(event,'searchDisputeVO_disputeDateEndsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;"
														href="javascript:%20void(0);"><img
															src="include/images/cal.gif" id="disputeDateEndsOnImg"
															border="0" align="middle"></a>
															yyyy/mm/dd
												</td>
											</tr>
											<tr>
												<td>
													<input type="radio" class="noBorderRadioButton"
														name="selectDisputeDateRadio" value="WITHINPAST" onclick="changdisputeDateRadio(1)">
												</td>

												<td nowrap="nowrap">
													Past
												</td>
												<td nowrap="nowrap">
													<s:textfield
														name="searchDisputeVO.disputeDateWiPastNumOfDays" cssClass="validate-number" onfocus="changdisputeDateRadio(1)"></s:textfield>
													days(0-10000)
												</td>
											</tr>
											
										</table>
									</div>
								</td>
								<td class="tabDetailViewDL" >
									<div class="searchItemPanel">
										<table width="100%" cellpadding="0" cellspacing="2" border="0">
							
											<tr>
												<td width="25%">
													Analyst
												</td>
												<td><span class="select1">
													<s:select name="searchDisputeVO.analyst" headerKey="all"
														headerValue="All" list="createdByList" listKey="key"
														listValue="value" required="true"  cssStyle="width:180px"/>													
													</span>
												</td>
											</tr>			 
										</table>
									</div>
								</td>
							</tr>
						</table>
					</s:form>
				</div>
		</div>
		<div style="width:100%;height:30px;">
			<div style="float:left;"><br />
				<input type="button" value="Search" onclick="startSearch();" />
				&nbsp;&nbsp;&nbsp;
				<input type="button" id="saveQueryButton" value="Save" onclick="YAHOO.ccm.container.saveQueryDialog.show();" />
				&nbsp;&nbsp;&nbsp;
				<input type="button" value="Clear"
					onclick="javascript:resetFormElementValue();form0_searchDisputeVO_banId.setValue('all');" />
			</div>
			<div id="loadingImgDiv" style="display:none; float: right;"> <img src="include/images/loading.gif" /></div>
		</div>
		<div style="width:100%;display: none;" id="_paginationTable">
				<br />	
					
				<table border=0  style="width:100%;table-layout: fixed;">
					<tr>
						<td >
							<div align="left"  style="width:100%;overflow-x:auto;border: 0px solid;padding-bottom: 20px;">
								<div id="_dataTable" style="width:100%;" ></div>		
							</div>	
						</td>
					</tr>
					<tr height="35px">
						<td align="left" style="padding-right: 15px;" >
							<div style="float:left;width:300px">
							<img src="include/images/button_previous_first.gif" title="First page" onclick="getFirstPage();">
							<img src="include/images/button_previous.gif" title="Previous page" onclick="getPrevPage();">
						Page&nbsp;<input id="__curPageNo" type="text" style="width: 30px;" value="1" onkeydown="getPage();">&nbsp;of&nbsp; 
							<span id="__totalPageNo"></span>
						<img src="include/images/button_next.gif" title="Next page" onclick="getNextPage();">
							<img src="include/images/button_next_last.gif" title="Last page" onclick="getLastPage();">
						&nbsp;&nbsp;<span class="select1"><select id="_recPerPageSelect" onchange="recPerPage=this.value;startSearch();">
								<option value="10">10</option><option value="20">20</option>
								<option value="30">30</option><option value="40">40</option>
								<option value="50">50</option><option value="100">100</option>
							</select></span></div>
						<div style="float:left;padding-top:0px;">
							<input type="button" id="saveExcel" value="Download to Excel"
								onclick="saveExcel();" style="display: none;" /></div>

						</td>
					</tr>
				</table>
	</div>
</div>

<div class="yui-pe-content" id="saveQueryDialog">
	<div class="hd">
		Please type in query name
	</div>
	<div class="bd">
		<table cellspacing=0 cellpadding=0 border=0 width=100%>
			<tr>
				<td>
					Query Name:
				</td>
				<td>
					<input type="text" name="queryName" id="queryName" class="required">
				</td>
			</tr>
		</table>
	</div>
</div>

<script type="text/javascript">
var VLD_Array = {
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
		form0_searchDisputeVO_banId.setValue("all");
	});
});

$(function(){
	form0_searchDisputeVO_vendorId = $('#VL_vendorList_Dispute').flexbox(VLD_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 260,
		onSelect : function(){
			form0_searchDisputeVO_banId.setValue("all");
			getBanListByVendorId(0);
		}
	});
	form0_searchDisputeVO_vendorId.setValue("all");
});

$(function(){
	form0_searchDisputeVO_banId = $('#VL_banList_Dispute').flexbox(BLD_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 260,
		onSelect : function(){
			form0_searchDisputeVO_vendorId.setValue("all");
			getBanListByVendorId(0);
		}
	});
	form0_searchDisputeVO_banId.setValue("all");
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
