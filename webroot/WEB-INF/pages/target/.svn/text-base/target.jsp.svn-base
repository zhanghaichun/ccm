<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link rel="stylesheet" type="text/css" href="include/javascript/Calendar/styles/calendar.css">
<link rel="stylesheet" type="text/css" href="include/javascript/flexbox/css/jquery.flexbox.css"><link>

<script type="text/javascript" src="include/javascript/flexbox/js/jquery.flexbox.js"></script>
<script type="text/javascript" src="include/javascript/Calendar/simplecalendar.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/ccm/common.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/ccm/target/targetVerficationValidate.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/ccm/searchTarget.js" language="javascript"></script>

<script type="text/javascript" src="include/javascript/ccm/quicklink.js" language="javascript"></script>
<SCRIPT type="text/javascript" src="include/javascript/ccm/common/simpleValidation.js"></SCRIPT>
<SCRIPT type="text/javascript" src="include/javascript/ccm/common/common.js"></SCRIPT>
<SCRIPT type="text/javascript" src="include/javascript/jquery/jquery.form.js"></SCRIPT>

<style>
	.aright{text-align:right;}
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

var BLD_Array0 = {
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
		]
	};

$(function(){
	$('#form0_searchTargetVO_banId').change(function(){
		form0_searchTargetVO_banId.setValue("all");
		getCircuitList();
	});
});

$(function(){
	form0_searchTargetVO_vendorId = $('#VL_vendorList_1').flexbox(VL_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 260,
		onSelect : function(){
			getBanListByVendorId(0);
			form0_searchTargetVO_banId.setValue("all");
			getCircuitList();
		}
	});
	form0_searchTargetVO_vendorId.setValue("all");
});

$(function(){
	form0_searchTargetVO_banId = $('#VL_banList_Invoice').flexbox(BLD_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 260,
		onSelect : function(){
			form0_searchTargetVO_vendorId.setValue("all");
			getBanListByVendorId(0);
			getCircuitList();
		}
	});
	form0_searchTargetVO_banId.setValue("all");
});
</script>
<div id="pageContainer" class="tabForm" onMouseDown="if(g_Calendar && g_Calendar.hide)g_Calendar.hide();" style="padding-bottom:25px;" >
	<h3 style="padding-top:0px; padding-bottom:4px;">
<!--<div id="pageContainer" class="tabForm" onmousedown="if(g_Calendar && g_Calendar.hide)g_Calendar.hide();" >-->
<!--	<h3>-->
		Target Search
	</h3>
	<div style="border-top: 0px none;">
<!--	<div style="border-top: 0px none; margin-bottom: 4px">-->
		<div id="ctl00_MainContent_SearchInvoiceControl1_UpdatePanel1">
					<s:form id="form0" action="" theme="simple" onsubmit="startSearch();return false;">
						<table class="tabDetailView" border=" 0" width="100%" cellpadding="5" cellspacing="0">
							<tr valign="top">
								<td class="tabDetailViewDL" width="52%">
									<div class="searchItemPanel">
										<table width="100%"  cellspacing="3" cellpadding="0" border="0">
											<tr>
												<td width="30%">
													Vendor
												</td>
												<td align="left" style="position:relative;"><div id="VL_vendorList_1"></div></td>
											</tr>
											<tr>
												<td>
													BAN
												</td>
												<td>
													<span class="select1"> <select id="form0_searchTargetVO_banId" name="searchTargetVO.banId" style="width:150px">
														<option value="all">All</option>
													</select> </span>
												</td>
											</tr>
											<tr>
												<td>
													All BAN
												</td>
												<td>
													<div id="VL_banList_Invoice"></div>
												</td>
											</tr>
											
											<tr>
												<td width="30%">
													Circuit Number
												</td>
												<td >
													<span class="select1"> <select id="form0_searchTargetVO_circuitNumber" name="searchTargetVO.circuitNumber" style="width:150px" onChange="onCircuitSelect()">
														<option value="all">All</option>
													</select> </span>												</td>
											</tr>
											
										</table>
									</div>
								</td>
								<td class="tabDetailViewDL">
									<div class="searchItemPanel">
										<table width="100%"  cellspacing="2" cellpadding="0" border="0">
											<tr>
												<td colspan="3" height="25">
													Target Period &nbsp;&nbsp;<input type="checkbox" id="_target_period" name="lockBox" class="ClearCheckbox" onclick="clearDisabled('_target_period',1);"/>
												</td>
											</tr>
											<tr>
											
												<td width="17%">
													From
												</td>
												<td nowrap="nowrap">
													<input id="searchTargetVO_targetPeriodStartsOn" name="searchTargetVO.invoiceDateStartsOn" cssClass="validate-search-yyyymm" class="validate-search-yyyymm Clear1 Date" disabled="disabled" style="background:#ccc;width:152px;" />
													<a>yyyymm</a>
													
													<%--<a onClick="if($('#_target_period')[0].checked) g_Calendar.show(event,'searchTargetVO_targetPeriodStartsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;" href="javascript:%20void(0);"><img
															src="include/images/cal.gif"  border="0" align="middle">
													</a> yyyy/mm/dd
												    
												   --%></td>
											</tr>
											<tr>
												
												<td>
													To
												</td>
												<td>
													<input id="searchTargetVO_targetPeriodEndsOn" name="searchTargetVO.invoiceDateEndsOn" cssClass="validate-search-yyyymm" class="validate-search-yyyymm Clear1 Date" disabled="disabled" style="background:#ccc;width:152px;" />
													<a>yyyymm</a>
													<%--<a onClick="if($('#_target_period')[0].checked) g_Calendar.show(event,'searchTargetVO_targetPeriodEndsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;" href="javascript:%20void(0);"><img
															src="include/images/cal.gif"  border="0" align="middle" id="invoiceDateEndsOnImg" >
													</a> yyyy/mm/dd
												     --%>
												</td>
											</tr>
								
										</table>
									</div>
								</td>

							</tr>
							<tr>
								<td class="tabDetailViewDL">
									<div class="searchItemPanel">
										<table width="100%"  cellspacing="4" cellpadding="0" border="0">
											
											<tr>
												<td>
													Charge Type
												</td>
												<td>
													<span class="select1"> <s:select name="searchTargetVO.paymentStatusId" headerKey="all" headerValue="All" list="chargeTypeList" listKey="key"
														id="chargeTypeSelect"	listValue="value" required="true" cssStyle="width:150px" />
													</span>
												</td>
											</tr>
											<tr>
												<td>
													Variation Percentage(%)
												</td>
												<td>
													 <s:textfield  id="VPercent"  cssStyle="width:150px" cssClass="validate-percent" onkeyup="inputValidate(this);"/>
												</td>
											</tr>
											<tr>
												<td width="30%">
													Created By
												</td>
												<td>
													<span class="select1"> <s:select name="searchTargetVO.analystId" headerKey="all" headerValue="All" list="analystList" listKey="key" listValue="value"
														id="createdBy"	required="true" cssStyle="width:150px" />
													</span>
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
													Created Date &nbsp;&nbsp;<input type="checkbox" id="__created_date" name="lockBox" class="ClearCheckbox" onclick="clearDisabled('__created_date',2);"/>
												</td>
											</tr>
											<tr>
							
												<td width="17%">
													From
												</td>
												<td nowrap="nowrap">
													<input id="searchTargetVO_createdDateStartsOn" name="searchTargetVO.createdDateStartsOn" cssClass="validate-date-ca" class="validate-date Clear2 Date" disabled="disabled" style="background:#ccc;width:152px;" />
													<a onClick="if($('#__created_date')[0].checked) g_Calendar.show(event,'searchTargetVO_createdDateStartsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;" href="javascript:%20void(0);"><img
															src="include/images/cal.gif" id="createdDateStartsOnImg" border="0" align="middle">
													</a> yyyy/mm/dd
												</td>
											</tr>
											<tr>
												<td>
													To
												</td>
												<td nowrap="nowrap">
													<input id="searchTargetVO_createdEndsOn" name="searchTargetVO.createdDateEndsOn" cssClass="validate-date-ca" class="validate-date Clear2 Date" disabled="disabled" style="background:#ccc;width:152px;" />	
													<a onClick="if($('#__created_date')[0].checked) g_Calendar.show(event,'searchTargetVO_createdEndsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;" href="javascript:%20void(0);"><img
															src="include/images/cal.gif" id="createdEndsOnImg" border="0" align="middle">
													</a> yyyy/mm/dd
												</td>
											</tr>
											
										</table>
									</div>
								</td>
							</tr>
							<tr>
								<td class="tabDetailViewDL">
									<div class="searchItemPanel">
										<table width="100%"  cellspacing="2" cellpadding="0" border="0">
											<tr>
												<td width="5%">
													<input type="radio" name="conditionChoose" id="radio1"
	     												value="1"  onclick="disabledRadio(this.value)" checked="checked"/>
												</td>
												
												<td width="25%" nowrap="nowrap">
													Target Amount
												</td>
												<td>
													<s:textfield id="searchTargetVO_targetAmount" name="searchTargetVO.targetAmount" cssClass="validate-percent conditions condition1" cssStyle="width:150px;margin-left:1px;" ></s:textfield>
												</td>
											</tr>
											<tr>
												<td>
													<input type="radio" name="conditionChoose" id="radio2"
	     												value="2"  onclick="disabledRadio(this.value)"/>
												</td>
												<td>
													Change Amount
												</td>
												<td>
													<s:textfield id="searchTargetVO_changeAmount" name="searchTargetVO.changeAmount" cssClass="validate-percent conditions condition2" cssStyle="width:150px;background:#ccc;margin-left:1px;" disabled="true"></s:textfield>
												</td>
											</tr>
											<tr>
												<td>
													<input type="radio" name="conditionChoose" id="radio3"
	     												value="3"  onclick="disabledRadio(this.value)"/>
												</td>
												<td>
													Change Percentage(%)
												</td>
												<td>
													<s:textfield id="searchTargetVO_changePercentage" name="searchTargetVO.changePercentage" cssClass="validate-percent  conditions condition3" cssStyle="width:150px;background:#ccc;margin-left:1px;" disabled="true" onkeyup="inputValidate(this);"></s:textfield>
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
		<div style="width:100%; height:35px; overflow:hidden;">
				<div style="float: left; margin-top:10px;">
					<input type="button" value="Search" onclick="startSearch();" />
					
<%--					<input type="button" id="saveQueryButton" value="Save" onclick="YAHOO.ccm.container.saveQueryDialog.show();" />--%>
<%--					&nbsp;--%>
					<input type="button" value="Clear" onclick="javascript:resetFormElementValue();" />
				
					<input type="button"  value="Create New Target" onclick="creatNewTarget();" />
						
				</div>
				<div id="loadingImgDiv" style="display: none; float: right;">
					<img src="include/images/loading.gif" />
				</div>
		</div>
		<div id="_gridDiv" style="width:100%;display:none"><br/>
<!--				<table border="0" style="table-layout: fixed; width: 100%;">-->
				<table border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed; width: 100%;">
					<tr>
						<td>
<!--							<br />-->
<!--							<div align="left" style="width: 100%; overflow-x: auto; padding-bottom: 20px;">-->
							<div align="left" style="width:100%; overflow-x: auto; padding-bottom: 20px;">
								<div id="_dataTable"></div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div id="_dataTable_page"></div>
							
						</td>
					</tr>
					</table>
					<table>
					<tr>
						<td>
							<div>
								<input type="button" id="saveExcel" value="Download to Excel" onclick="saveExcel();" style="display: none;" />
							</div>
						</td>
						
						<td>	
							<div>
								<input type="button" id="saveExcel2" value="Copy" onclick="copyOldTargets();" style="display: none;" />
							</div>
						</td>
						<td>	
							<div>
								<input type="button" id="saveExcel3" value=" Actual  Result" onclick="actualResults();" style="display: none;" />
							</div>
						</td>
<%--						<td>	--%>
<%--							<div>--%>
<%--								<input type="button" id="saveExcel4" value="Verification" onclick="verificationOldTargets();" style="display: none;" />--%>
<%--							</div>--%>
<%--						</td>--%>
					
					</tr>
				</table>
		</div>
		
			<div id="inputDateTable" style="width:100%;display:none; padding-top:10px;">
			
			<table style="width: 100%; table-layout: fixed;" border="0" cellSpacing="0" cellPadding="0" >
			<tr>
				<td>
					<div align="left" style="width: 100%; padding-bottom: 20px;position:relative;z-index=2; overflow-x: auto">
						<table class="gridview" style="width: 100%; border-collapse: collapse;" border="1" rules="all" cellSpacing="0" >
					<%--<s:form id="form2">  Click the checkbox after card --%>
					<TBODY id="newTargetList">
					<TR  height=30>
					<TH scope=col noWrap><input type="checkbox" class='noBorderRadioButton' name="rightbox" onclick="chooserightall()"/></TH>
					<TH scope=col noWrap>Vendor</TH>
					<TH scope=col noWrap>BAN</TH>
					<TH scope=col noWrap>Circuit Number</TH>
					<TH scope=col noWrap>Period from(YYYYmm)</TH>
					<TH scope=col noWrap>Period to(YYYYmm)</TH>
					<TH scope=col noWrap>Allowed Period Shift</TH>
					<TH scope=col noWrap>Charge Type</TH>
					<TH scope=col noWrap>Target Amount</TH>
					<TH scope=col noWrap>Allowed Variation Percentage(%)</TH>
					<TH scope=col noWrap>Change Amount</TH>
					<TH scope=col noWrap>Change Percentage(%)</TH>
					</TR>
					</TBODY>
					<%--</s:form>
			           --%></table>
					</div>
				</td>
			 </tr>
			</table>
		
			
			 <table>
					<tr>
						<td>
							<div>
								<input type="button"  value="Add" onclick="addNewTarget('Manually created');" />
							</div>
						</td>
						<td>	
							<div style="margin-left:6px;">
								<input type="button"  value="Copy" onclick="copyTargets();" />
							</div>
						</td>
						<td>	
							<div style="margin-left:6px;">
								<input type="button"  value="Save" onclick="saveTargetList();"  />
							</div>
						</td>
<%--						<td>	--%>
<%--							<div style="margin-left:6px;">--%>
<%--								<input type="button"  value="Verification" onclick="verificationTargets();" />--%>
<%--							</div>--%>
<%--						</td>--%>
						<td>	
							<div style="margin-left:6px;">
								<input type="button" value="Delete" onclick="deleteTarget()"/>
							</div>
						</td>
<%--						<td>	--%>
<%--							<div style="margin-left:6px;">--%>
<%--								<input type="button" value="Upload Excel" onclick="upLoadShow()"/>--%>
<%--							</div>--%>
<%--						</td>--%>
						<td>
							<div style="margin-left:6px;">
								<input type="button"   value="Verification" onclick="verificationNoSave()"/>
							</div>
						</td>
						<%--<td>
							<div style="margin-left:6px;">
								<input type="button" value="VerificationProcedure" onclick="verificationNoSaveProcedure()"/>
							</div>
						</td>
						--%>
					</tr>
				</table>
				
			
			</div>
			
</div>

<div id="resultWindow">
	<div class="hd"> Target Result List</div>
	<div class="bd" style="overflow:auto">
		<table class="gridview" >
				<TBODY id="resultList">
				<TR  height=30>
				<TH scope=col noWrap>Vendor</TH>
				<TH scope=col noWrap>BAN</TH>
				<TH scope=col noWrap>Circuit Number</TH>			
				<TH scope=col noWrap>Charge Type</TH>
				<TH scope=col noWrap>Target Amount</TH>
				<TH scope=col noWrap>Change Amount</TH>
				<TH scope=col noWrap>Change Percentage(%)</TH>
				<TH scope=col noWrap>Target Period</TH>
				<TH scope=col noWrap>Allowed Period Shift</TH>	
				<TH scope=col noWrap>Verification Result</TH>			
				<TH scope=col noWrap>Actual Result</TH>
				</TR>
				</TBODY>
		</table>
	<table>
		<tr>
			<td>
			<input type="button" value="Download to CSV" onclick="DownLoadResult();" />
			<input type="button" value="Close" onclick="closeResultWindow();" />	
			</td>
		</tr>
	</table>
	</div>
</div>
	
	<div id="uploadWindow" >
		<div>
		<s:form id="uploadForm" action="excelUp.action" method="post" enctype="multipart/form-data"  target="uploadFrame_0">
		<div id="upload_div" style="padding:2px,2px,2px,2px;">
	   		<s:file label="upload" theme="simple" id="fileName" name="excelFile"/>
	   		<s:submit/>
		</div>
		</s:form>

		</div>
	</div>
	<iframe name="uploadFrame_0" id="uploadFrame_0" style="display: none"  src="javascript:false"></iframe>