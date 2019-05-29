<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<link rel="stylesheet" type="text/css" href="include/javascript/Calendar/styles/calendar.css">
<script type="text/javascript" src="include/javascript/ccm/common.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/Calendar/simplecalendar.js" language="javascript"></script>
<SCRIPT src="include/javascript/ccm/common/simpleValidation.js" type=text/javascript></SCRIPT>
<script type="text/javascript" src="include/javascript/ccm/quicklink.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/ccm/searchTicket.js" language="javascript"></script>
<script type="text/javascript">
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
<div id="pageContainer" class="tabForm" onmousedown="if(g_Calendar && g_Calendar.hide)g_Calendar.hide();" >
	<h3>
		Ticket Search
	</h3>
	<div style="border-top: 0px none; margin-bottom: 4px" >
		<div id="ctl00_MainContent_SearchTicketControl1_UpdatePanel1">
					<s:form id="form0" action="" theme="simple" onsubmit="startSearch();return false;">
						<table class="tabDetailView" border="0" width=100% height="100" cellpadding="5"
							cellspacing="0">
							<tr valign="top">
								<td class="tabDetailViewDL" width="34%">
									<div class="searchItemPanel">
										<table width="100%"  cellspacing="4" cellpadding="0" border="0">
											<tr>
												<td width="35%">Ticket Number</td>
												<td>
													<s:textfield name="searchTicketVO.ticketNumber" 
														cssClass="validate-number" cssStyle="width:148px"></s:textfield>
												</td>
											</tr>
											<tr>
												<td width="30%">Created by</td>
												<td>
													<span class="select1"><s:select name="searchTicketVO.createdById" list="ticketCreatedByList" headerKey="all"
														headerValue="All" listKey="key" listValue="value" cssStyle="width:150px"/></span>
												</td>
											</tr>
											<tr>
												<td >Modified by</td>
												<td ><span class="select1">
													<s:select name="searchTicketVO.modifiedById" list="ticketCreatedByList" headerKey="all"
														headerValue="All" listKey="key" listValue="value" cssStyle="width:150px"/></span>
												</td>
											</tr>
											<tr>
												<td >Priority</td>
												<td><span class="select1">
													<s:select name="searchTicketVO.priorityId" list="ticketPriorityList" headerKey="all"
														headerValue="All" listKey="key" listValue="value" cssStyle="width:150px"/></span>
												</td>
											</tr>
											<tr>
												<td>Severity</td>
												<td><span class="select1">
													<s:select name="searchTicketVO.severityId" list="ticketSeverityList" headerKey="all"
														headerValue="All" listKey="key" listValue="value" cssStyle="width:150px"/></span>
												</td>
											</tr>
											<tr>
												<td >Status</td>
												<td ><span class="select1">
													<s:select name="searchTicketVO.statusId" headerKey="all"
														headerValue="All" list="ticketStatusList" listKey="key"
														listValue="value" required="true" cssStyle="width:150px"/></span>
													<input type="submit" value="ok" style="display: none;"/>
												</td>
											</tr>
										</table>
										
									</div>
								</td>
								<td class="tabDetailViewDL" width="33%" >
									<div class="searchItemPanel">
										<table width="100%" cellspacing="2"  cellpadding="0"  border="0">
											<tr>
												<td colspan="3">
													Ticket Date
												</td>
											</tr>
											<tr>
												<td width="10%" rowspan="2" valign="top">
													<input type="radio" class="noBorderRadioButton"
														name="selectTicketDateRadio" checked="checked"
														value="DATEFRAME" onclick="changRadio(0)">
												</td>
												<td width="15%">
													From
												</td>
												<td>
													<s:textfield name="searchTicketVO.ticketDueStartsOn"
														cssClass="validate-date-ca" onfocus="changRadio(0);"></s:textfield>
													<a
														onClick="changRadio(0);g_Calendar.show(event,'form0_searchTicketVO_ticketDueStartsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;"
														href="javascript:%20void(0);"><img
															src="include/images/cal.gif" id="ticketDueStartsOnImg"
															border="0" align="middle"></a>
															yyyy/MM/dd
												</td>
											</tr>
											<tr>
												<td>
													To
												</td>
												<td>
													<s:textfield name="searchTicketVO.ticketDueEndsOn"
														cssClass="validate-date-ca" onfocus="changRadio(0);"></s:textfield>
													<a
														onClick="changRadio(0);g_Calendar.show(event,'form0_searchTicketVO_ticketDueEndsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;"
														href="javascript:%20void(0);"><img
															src="include/images/cal.gif" id="ticketDueEndsOnImg"
															border="0" align="middle"></a>
															yyyy/MM/dd
												</td>
											</tr>
											<tr>
												<td>
													<input type="radio" class="noBorderRadioButton"
														name="selectTicketDateRadio" value="WITHINPAST" onclick="changRadio(1)">
												</td>
												<td nowrap="nowrap">
													Past
												</td>
												<td nowrap="nowrap">
													<s:textfield
														name="searchTicketVO.ticketDueWiPastNumOfDays" cssClass="validate-number" onfocus="changRadio(1);"></s:textfield>
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
		</div>
		<div style="width:100%;height:30px;">
			<div style="float:left;"><br/>
				<input type="button" value="Search" onclick="startSearch();" />
				&nbsp;
				<input type="button" id="saveQueryButton" value="Save" onclick="YAHOO.ccm.container.saveQueryDialog.show();" />
				&nbsp;
				<input type="button" value="Clear" onclick="javascript:cleanFormElementValue();" />
				&nbsp;
				<input type="button" value=" Create New " onclick="javascript:window.location.href = 'viewTicketDetail.action'" />
				</div>
				<div id="loadingImgDiv" style="display:none; float: right;"> <img src="include/images/loading.gif" /></div>
		</div>
		<div style="width:100%;"><br />
			<table border="0" style="table-layout:fixed;width:100%;">
				<tr>
					<td>
						<div align="left"  style="width:100%;overflow-x:auto;padding-bottom: 10px;">
							<div id="_dataTable"></div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div id="_dataTable_page" style="float: left;"></div>
						<div style="float:left;padding-top:1px;">
							<input type="button" id="saveTicketExcel" value="Download to Excel" onclick="saveTicketExcel();" style="display: none;"/>
						</div>
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
		<table cellspacing=5 cellpadding=0 border=0 width=100%>
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
