<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet" type="text/css"
	href="include/javascript/Calendar/styles/calendar.css">
<link rel="stylesheet" type="text/css"
	href="include/javascript/Calendar/styles/calendar.css">
<link rel="stylesheet" type="text/css"
	href="include/javascript/flexbox/css/jquery.flexbox.css">
<link>
<link rel="stylesheet" type="text/css"
	href="include/javascript/yui/build/slider.css" />
<link rel="stylesheet" type="text/css"
	href="include/javascript/yui/build/colorpicker.css" />

<script type="text/javascript"
	src="include/javascript/flexbox/js/jquery.flexbox.js"></script>
<script type="text/javascript"
	src="include/javascript/Calendar/simplecalendar.js"></script>
<script type="text/javascript"
	src="include/javascript/yui2/tabview-min.js"></script>
<script type="text/javascript" src="include/javascript/ccm/quicklink.js"
	language="javascript"></script>
<script type="text/javascript"
	src="include/javascript/ccm/searchAdmin.js"></script>
<script type="text/javascript"
	src="include/javascript/ccm/searchUserList.js"></script>
<script type="text/javascript"
	src="include/javascript/ccm/adminTransactionTab/transactionTab.js"></script>
<script type="text/javascript"
	src="include/javascript/ccm/searchRole.js"></script>
<script type="text/javascript"
	src="include/javascript/ccm/vendorMian.js"></script>
<script type="text/javascript" src="include/javascript/ccm/common.js"
	language="javascript"></script>
<script type="text/javascript"
	src="include/javascript/ccm/viewReportAdmin.js" language="javascript"></script>
<script type="text/javascript"
	src="include/javascript/ccm/viewRtagReportAdmin.js"></script>
<script type="text/javascript"
	src="include/javascript/ccm/adminScoaCodeTab/scoa.js"></script>
<script type="text/javascript"
	src="include/javascript/ccm/ban/common.js" language="javascript"></script>
<script type="text/javascript"
	src="include/javascript/ccm/ban/banAdminTab.js" language="javascript"></script>
<script type="text/javascript"
	src="include/javascript/ccm/viewTeamTab.js" language="javascript"></script>
<script type="text/javascript"
	src="include/javascript/ccm/modifyAdjustmentMian.js" language="javascript"></script>
<script type="text/javascript"
	src="include/javascript/ccm/modifyAdjustment.js" language="javascript"></script>
<script type="text/javascript"
	src="include/javascript/ccm/viewSecurityIpTab.js" language="javascript"></script>
<script type="text/javascript"
	src="include/javascript/ccm/wiki/wiki.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="include/javascript/ckeditor/lang/_languages.js"></script>
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
<script type="text/javascript">
var VL_ArrayVendor = {
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
var VL_ScoaCodeList = {
	"results": [
   		{
   		    "id": "null",
   		    "name": ""
   		}
   		<c:forEach items="${scoaCodeList}" var="sc">
   		,{
   		    "id": "${sc.key}",
   		    "name": "${sc.value}"
   		}
   		</c:forEach>
   	],
   	"total": ${fn:length(scoaCodeList)}
};

var VL_TaxCodeList = {
	"results": [
   		{
   		    "id": "null",
   		    "name": ""
   		}
   		<c:forEach items="${taxCodeList}" var="sc">
   		,{
   		    "id": "${sc.key}",
   		    "name": "${sc.value}"
   		}
   		</c:forEach>
   	],
   	"total": ${fn:length(taxCodeList)}
};
$(function(){
	form0_vendorMain_vendorId = $('#VL_vendorMainList_1').flexbox(VL_ArrayVendor, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 260,
		onSelect : function(){
			$("#vendorCircuitId").val(form0_vendorMain_vendorId.getValue());
			vendorNumberValue(form0_vendorMain_vendorId.getValue());
			getVendorMainPageList(form0_vendorMain_vendorId.getValue());
		}
	});
	form0_vendorMain_vendorId.setValue("all");

	banPageVendorFlexbox = $('#banPageVendorListDiv').flexbox(VL_ArrayVendor, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 260,
		onSelect : function(){
			$("#banPage_frm_bvo_vendorId").val(banPageVendorFlexbox.getValue());
		}
	});
	
	SCOACodeing = $('#VL_SCOAList').flexbox(VL_ScoaCodeList, {
			maxCacheBytes : 200000,
			highlightMatches : true,
			autoCompleteFirstMatch : false,
			paging: false,
			width : 260,
			onSelect : function(){
	
			}
	});

});
</script>
<div id="pageContainer" class="tabForm"
	onMouseDown="if(g_Calendar && g_Calendar.hide)g_Calendar.hide();"
	style="padding-bottom: 25px;">


	<h3 style="padding-top: 0px; padding-bottom: 4px;">
		<!--<div id="pageContainer" class="tabForm" onmouseup="if(g_Calendar && g_Calendar.hide)g_Calendar.hide();">-->
		<!--	<h3>-->
		<b>Admin</b>
	</h3>
	<div id="adminTab" class="yui-navset">
		<ul class="yui-nav">
			<sec:authorize ifAllGranted="FUNCTION_4100">
				<li id="a0" class="selected">
					<a href="#userListTab"
						onclick="resetFormElementValue();initialization();"><em>User</em>
					</a>
				</li>
			</sec:authorize>
			<sec:authorize ifAllGranted="FUNCTION_4200">
				<li id="a1" >
					<a href="#roleTab"
						onclick="disable();adminTabInit.get('roleTab');initialization();"><em>Role</em>
					</a>
				</li>
			</sec:authorize>
			<sec:authorize ifAllGranted="FUNCTION_4500">
				<li id="a2" >
					<a href="#reportAdminTab"
						onclick="adminTabInit.get('reportAdminTab');initialization();"><em>Report Admin</em> </a>
				</li>
			</sec:authorize>
			<sec:authorize ifAllGranted="FUNCTION_4400">
				<li id="a3" >
					<a href="#transactionTab"
						onclick="transactionTabLC.clearValueTab();initialization();"><em>Transaction</em>
					</a>
				</li>
			</sec:authorize>
			<sec:authorize ifAllGranted="FUNCTION_4700">
			<li id="a4" >
				<a href="#newScoaCodeTab"
					onclick="adminScoaTab.init();initialization();"><em>SCOA
						Code</em> </a>
			</li>
			</sec:authorize>
			<sec:authorize ifAllGranted="FUNCTION_4800">
				<li id="a5" >
					<a href="#vendorTab"
						onclick="adminTabInit.get('vendorTab');initialization('vendorTab');"><em>Vendor Maintenance</em> </a>
				</li>
			</sec:authorize>
			<sec:authorize ifAnyGranted="FUNCTION_4900">
			<li id="a6" >
				<a href="#banTab"
					onclick="banMaintenanceTab.tabClearl();initialization();"><em>BAN Maintenance</em> </a>
			</li>
			</sec:authorize>
			<sec:authorize ifAllGranted="FUNCTION_4110">
			<li id="a7" >
				<a href="#teamTab" onclick="getTeam();initialization();"><em>Team Maintenance</em> </a>
			</li>
			</sec:authorize>
			<sec:authorize ifAllGranted="FUNCTION_4120">
			<li id="a8" >
				<a href="#modifyAdjustmentTab" onclick="initialization();initModifyAdjustment();"><em>Modify Adjustment</em> </a>
			</li>
			</sec:authorize>
			<sec:authorize ifAllGranted="FUNCTION_4130">
			<li id="a9" >
				<a href="#securityIp" onclick="getSecurity();initialization();"><em>Team Security IP</em> </a>
			</li>
			</sec:authorize>
			<sec:authorize ifAllGranted="FUNCTION_4140">
			<li id="a10" >
				<a href="#wikiManagement" onclick="adminTabInit.get('wikiManagement');initialization();"><em>Wiki Management</em> </a>
			</li>
			</sec:authorize>
		</ul>
		<div class="yui-content">
			<sec:authorize ifAllGranted="FUNCTION_4100">
				<div id="userListTab">
					<div id="pageContainerUserList">
						<s:hidden name="uvo.currentUserId"></s:hidden>
						<div id="searchUserList">
							<h3>
								User Search
							</h3>
							<table width="100%" cellspacing="0" cellpadding="0" border="0"
								style="border-top: 0px none; margin-bottom: 4px">
								<tr>
									<td>
										<div id="ctl00_MainContent_SearchTicketControl1_UpdatePanel1">
											<s:form id="form123" action="" theme="simple"
												onsubmit="startSearch();return false;">
												<table class="tabDetailView" border="0" width="100%"
													cellpadding="5" cellspacing="0">
													<tr valign="top">
														<td class="tabDetailViewDL" width="50%">
															<div class="searchItemPanel">
																<table width="100%" cellspacing="2" cellpadding="0"
																	border="0">
																	<tr>
																		<td colspan="2">
																			<b>Search Name</b>
																		</td>
																	</tr>
																	<tr>
																		<td width="30%">
																			Name
																		</td>
																		<td>
																			<s:textfield name="uvo.name"></s:textfield>
																		</td>
																	</tr>
																	<tr>
																		<td>
																			User Name
																		</td>
																		<td>
																			<s:textfield name="uvo.userName"></s:textfield>
																		</td>
																	</tr>

																</table>
															</div>
														</td>
														<td class="tabDetailViewDL">
															<div class="searchItemPanel">
																<table width="100%" cellpadding="0" cellspacing="2"
																	border="0">
																	<tr>
																		<td colspan="3" height="25">
																			<b>Modified Date</b>
																		</td>
																	</tr>
																	<tr>
																		<td width="10%">
																			<input id="selectModifiedUserListRadio1" type="radio"
																				class="noBorderRadioButton"
																				name="selectModifiedUserListRadio" checked="checked"
																				value="MODIFIEDDATEFRAME">
																		</td>
																		<td width="15%">
																			From
																		</td>
																		<td nowrap="nowrap">
																			<s:textfield name="uvo.modifiedBeginDate"
																				cssClass="validate-date-ca" onfocus="changRadio(2)"></s:textfield>
																			<a
																				onClick="g_Calendar.show(event,'form123_uvo_modifiedBeginDate',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;"
																				href="javascript:%20void(0);"><img
																					src="include/images/cal.gif"
																					id="ticketDueStartsOnImg" border="0" align="middle">
																			</a>yyyy/mm/dd
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
																			<s:textfield name="uvo.modifiedEndDate"
																				cssClass="validate-date-ca" onfocus="changRadio(2)"></s:textfield>
																			<a
																				onClick="g_Calendar.show(event,'form123_uvo_modifiedEndDate',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;"
																				href="javascript:%20void(0);"><img
																					src="include/images/cal.gif"
																					id="ticketDueEndsOnImg" border="0" align="middle">
																			</a>yyyy/mm/dd
																		</td>
																	</tr>
																	<tr>
																		<td>
																			<input id="selectModifiedUserListRadio2" type="radio"
																				class="noBorderRadioButton"
																				name="selectModifiedUserListRadio"
																				value="MODIFIEDWITHINPAST">
																		</td>
																		<td nowrap="nowrap">
																			Past
																		</td>
																		<td nowrap="nowrap">
																			<s:textfield name="uvo.userModifiedWiPastNumOfDays"
																				cssClass="validate-number" onfocus="changRadio(3)"></s:textfield>
																			days(0-10000)
																		</td>
																	</tr>
																</table>
															</div>
														</td>
													</tr>
													<tr>
														<td class="tabDetailViewDL">
															<div class="searchItemPanel">
																<table width="100%" cellpadding="0" cellspacing="2"
																	border="0">
																	<tr>
																		<td colspan="2">
																			<b>Other Check</b>
																		</td>
																	</tr>
																	<tr>
																		<td width="30%">
																			Initials
																		</td>
																		<td>
																			<s:textfield name="uvo.initials"></s:textfield>
																		</td>
																	</tr>
																	<tr>
																		<td>
																			Email
																		</td>
																		<td>
																			<s:textfield name="uvo.email"></s:textfield>
																		</td>
																	</tr>
																	<tr>
																		<td>
																			Phone Number
																		</td>
																		<td>
																			<s:textfield name="uvo.phone"
																				cssClass="validate-phone"></s:textfield>
																		</td>
																	</tr>
																</table>
															</div>
														</td>
														<td class="tabDetailViewDL">
															<div class="searchItemPanel">
																<table width="100%" cellpadding="0" cellspacing="2"
																	border="0">
																	<tr>
																		<td colspan="3">
																			<b>Created Date</b>
																		</td>
																	</tr>
																	<tr>
																		<td width="10%">
																			<input id="selectCreatedUserListRadio1" type="radio"
																				class="noBorderRadioButton"
																				name="selectCreatedUserListRadio" checked="checked"
																				value="CREATEDDATEFRAME">
																		</td>
																		<td width="15%">
																			From
																		</td>
																		<td nowrap="nowrap">
																			<s:textfield name="uvo.createBeginDate"
																				cssClass="validate-date-ca" onfocus="changRadio(0)"></s:textfield>
																			<a
																				onClick="g_Calendar.show(event,'form123_uvo_createBeginDate',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;"
																				href="javascript:%20void(0);"><img
																					src="include/images/cal.gif"
																					id="ticketDueStartsOnImg" border="0" align="middle">
																			</a>yyyy/mm/dd
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
																			<s:textfield name="uvo.createdEndDate"
																				cssClass="validate-date-ca" onfocus="changRadio(0)"></s:textfield>
																			<a
																				onClick="g_Calendar.show(event,'form123_uvo_createdEndDate',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;"
																				href="javascript:%20void(0);"><img
																					src="include/images/cal.gif"
																					id="ticketDueEndsOnImg" border="0" align="middle">
																			</a>yyyy/mm/dd
																		</td>
																	</tr>
																	<tr>
																		<td>
																			<input id="selectCreatedUserListRadio2" type="radio"
																				class="noBorderRadioButton"
																				name="selectCreatedUserListRadio"
																				value="CREATEDWITHINPAST">
																		</td>
																		<td nowrap="nowrap">
																			Past
																		</td>
																		<td nowrap="nowrap">
																			<s:textfield name="uvo.userCreateWiPastNumOfDays"
																				cssClass="validate-number" onfocus="changRadio(1)"></s:textfield>
																			days(0-10000)
																		</td>
																	</tr>
																</table>
															</div>
														</td>
													</tr>
													<tr>
														<td class="tabDetailViewDL">
															<div class="searchItemPanel" />
																<table width="100%" cellpadding="0" cellspacing="4"
																	border="0">
																	<tr>
																		<td width="30%">
																			Backup User
																		</td>
																		<td>
																			<span class="select1"> <s:select
																					name="uvo.backupUserId" list="backupUserList"
																					headerKey="all" headerValue="All" listKey="key"
																					listValue="value" cssStyle="width:127px" /> </span>
																		</td>
																	</tr>
																	<tr>
																		<td>
																			Supervisor
																		</td>
																		<td>
																			<span class="select1"><s:select
																					name="uvo.supervisorId" list="supervisorUserList"
																					headerKey="all" headerValue="All" listKey="key"
																					listValue="value" cssStyle="width:127px" /> </span>
																		</td>
																	</tr>
																</table>
																<br />
																<input type="submit" value="ok" style="display: none;" />
														</td>
														<td class="tabDetailViewDL">
															<div class="searchItemPanel" />
																<input type="submit" value="ok" style="display: none;" />
														</td>
													</tr>
												</table>
											</s:form>
										</div>
									</td>
								</tr>
								<tr>
									<td style="padding-right: 50px;">
										<div style="float: left;">
											<br />
											<input type="button" value="Search" onclick="startSearch();" />
											&nbsp;&nbsp;&nbsp;
											<input type="button" value="Clear"
												onclick="javascript:resetFormElementValue();" />
											&nbsp;&nbsp;&nbsp;
											<input type="button" value="Add User"
												onclick="javascript:window.location.href = 'securityManagement.action'" />
											&nbsp;&nbsp;&nbsp;
										</div>
										<div id="loadingImgDiv" style="display: none; float: right;">
											<img src="include/images/loading.gif" />
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<br />
										<div id="_userListDataTable"></div>
										<div id="_dataTable">
										</div>
										<table border=0 width=100% id="_paginationTable"
											style="display: none;">
											<tr>
												<td valign="top" style="padding-right: 10px;">
													<img src="include/images/button_previous_first.gif"
														title="First page" onclick="getFirstPage();">
													<img src="include/images/button_previous.gif"
														title="Previous page" onclick="getPrevPage();">
													Page&nbsp;
													<input id="__curPageNo" type="text" style="width: 30px;"
														value="1" onkeydown="getPage();">
													&nbsp;of&nbsp;
													<span id="__totalPageNo"></span>
													<img src="include/images/button_next.gif" title="Next page"
														onclick="getNextPage();">
													<img src="include/images/button_next_last.gif"
														title="Last page" onclick="getLastPage();">
													&nbsp;&nbsp;
													<span class="select1"><select id="_recPerPageSelect"
															onchange="recPerPage=this.value;startSearch();">
															<option value="5">
																5
															</option>
															<option value="10">
																10
															</option>
															<option value="20">
																20
															</option>
															<option value="30">
																30
															</option>
															<option value="40">
																40
															</option>
															<option value="50">
																50
															</option>
															<option value="100">
																100
															</option>
														</select> </span>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</div>
						<div class="yui-pe-content" id="saveQueryDialog"
							style="display: none;">
							<div class="hd">
								Please type in query name
							</div>
							<div class="bd">
								<table cellspacing=10 cellpadding=0 border=0 width=100%>
									<tr>
										<td>
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
					</div>
				</div>
			</sec:authorize>
			<sec:authorize ifAllGranted="FUNCTION_4200">
				<div id="roleTab">
					<div id="pageContainerRole">
						<h3>
							<b>Role</b>
						</h3>
						<table class="tabForm" width="100%" border="0">
							<tr>
								<td rowspan="2" width="30%" id="leftEidt">
									<div id="roleListDiv" style="width: 100%;"></div>
								</td>
								<td width="70%" name="rightEidt">
									<div id="roleAndFunctionDiv"
										style="width: 100%; text-align: center;">
										<div style="float: left; width: 43%; margin-left: 2%;">
											<h3>
												Unassigned Functions
											</h3>
											<br />
											<span class="select1"><select id="s1" 
											multiple="multiple" style="width: 220px; height: 250px;overflow: auto;"
													disabled="disabled"></select> </span>
										</div>
										<div style="float: left; width: 10%;">
											<b>
												<div style="margin-top: 80px;">
													<br />
													<input type="button" value=" >" style="width: 40px;"
														id="add">
													<br />
													<br />
													<input type="button" disabled="disabled" value=">>"
														style="width: 40px;" id="add_all">
												</div>
												<div style="margin-top: 40px;">
													<input type="button" value="< " style="width: 40px;"
														id="remove">
													<br />
													<br />
													<input type="button" disabled="disabled" value="<<" style="
														width:40px" id="remove_all">
												</div> </b>
										</div>
										<div style="float: left; width: 43%;">
											<h3>
												Assigned Functions
											</h3>
											<br />
											<span class="select1"><select id="s2"
													multiple="multiple" style="width: 220px; height: 250px;overflow: auto;"
													disabled="disabled"></select> </span>
										</div>
									</div>
								</td>
							</tr>
							<tr name="rightEidt">
								<td nowrap="nowrap" align="center">
									<div style="padding-top: 5px; height: 30px;">
										Role Name:
										<input id="rname" disabled="disabled">
										<input id="btnName" type="button" value="Save Name"
											onclick="saveName();" disabled="disabled">
										<input type="button" value="New Role" onclick="newRole();">
									</div>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</sec:authorize>
			<sec:authorize ifAllGranted="FUNCTION_4500">
				<div id="reportAdminTab">
					<div id="reportAdminPageContainer">
						<table width="100%" cellspacing="0" cellpadding="0" border="0"
							style="border-top: 0px none; margin-bottom: 4px">
							<tr>
								<td>
									<div id="ctl00_MainContent_SearchInvoiceControl1_UpdatePanel1">
										<s:form id="form0" action="" theme="simple"
											onsubmit="getPage(event);return false;">
											<table border="0" width="100%" cellpadding="5"
												cellspacing="0">
												<tr valign="top">
													<td width="34%">
														<div align="left">
															<h4>
																Report Admin
															</h4>
														</div>
													</td>
												</tr>
												<tr valign="top">
													<td width="34%">
														<div class="searchItemPanel" align="left">
															<table width="100%" cellspacing="0" cellpadding="0"
																border="0">
																<tr>
																	<td>
																		<div id="demo" class="yui-navset">
																			<ul class="yui-nav">
																				<li class="selected">
																					<a href="#tab1"
																						onclick="getTags();initialization();"><em>Tags</em>
																					</a>
																				</li>
																				<li>
																					<a href="#tab2"
																						onclick="searchTagsSelect();initialization();"><em>Tags
																							and Reports</em> </a>
																				</li>
																				<li>
																					<a href="#tab3"
																						onclick="getTagsAndRoles();initialization();"><em>Tags
																							and Roles</em> </a>
																				</li>
																			</ul>
																			<div class="yui-content">
																				<div id="tab1">
																					<div id="__report_tags" style="width: 100%"></div>
																					<table width="100%" border="0">
																						<tr>
																							<td width="250px">
																								<div id="__report_tags_page" align="left"></div>
																							</td>
																							<td>
																								<input type="button" value="New Tag"
																									onclick="newTag();" align="right" />
																							</td>
																							<td>
																								<div id="__validation_tag_innetHTML"
																									style="color: red;"></div>
																							</td>
																						</tr>
																					</table>
																				</div>
																				<div id="tab2">
																					<div id="">
																						<table border="0" width="100%"
																							style="text-align: center;">
																							<tr>
																								<td colspan="3">
																									<h2>
																										Please choose one tag from the list
																									</h2>
																								</td>
																							</tr>
																							<tr>
																								<td colspan="3">
																									<br />
																									Tags:
																									<span id="tagsSpan" class="select1"> <select
																											id="sTagsNameList" style="width: 250px"
																											onchange="searchReports(this.value);"></select>
																									</span>
																								</td>
																							</tr>
																							<tr>
																								<td colspan="3">
																									<br />
																									<br />
																								</td>
																							</tr>
																							<tr>
																								<td>
																									<div id="rtagAndReportDivLC">
																										<div style="">
																											<h3>
																												Unassigned Reports
																											</h3>
																											<br />
																											<span class="select1"><select
																													id="sReportAdmin1" multiple="multiple"
																													style="width: 375px; height: 300px;"></select>
																											</span>
																										</div>
																									</div>
																								</td>
																								<td>
																									<div style="">
																										<b>
																											<div style="margin-top: 60px;">
																												<input type="button" value=" >"
																													style="width: 50px;" id="addTR">
																												<br />
																												<br />
																												<input type="button" value=">>"
																													style="width: 50px;" id="add_allTR">
																											</div>
																											<div style="margin-top: 30px;">
																												<input type="button" value="< "
																													style="width: 50px;" id="removeTR">
																												<br />
																												<br />
																												<input type="button" value="<<" style="
																													width:50px;" id="remove_allTR">
																											</div> </b>
																									</div>
																								</td>
																								<td>
																									<div style="">
																										<h3>
																											Assigned Reports
																										</h3>
																										<br />
																										<span class="select1"><select
																												id="sReportAdmin2" multiple="multiple"
																												style="width: 375px; height: 300px;"></select>
																										</span>
																									</div>
																								</td>
																							</tr>
																							<tr>
																								<td colspan="3">
																									&nbsp;&nbsp;&nbsp;
																									<input id="btnSave" style="display: none;"
																										type="button" disabled="disabled" value="Save"
																										onclick="saveReports();">
																									<script>
																										document.getElementById("btnSave").style.display = "";
																									</script>
																									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																									<input id="btnCancel" type="button"
																										disabled="disabled" value="Cancel"
																										onclick="cancelReports();">
																								</td>
																							</tr>
																						</table>

																					</div>
																				</div>
																				<div id="tab3">
																					<div id="__report_tagsAndroles"
																						style="width: 100%;"></div>

																					<table width="350" border="0">
																						<tr>
																							<td width="250">
																								<div id="__report_tagsAndroles_page"
																									align="left"></div>
																							</td>
																							<td>
																								<input type="button" value="Add New"
																									onclick="newTagsAndRoles();" align="right" />
																							</td>
																						</tr>
																					</table>
																				</div>
																			</div>
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
								</td>
							</tr>
						</table>
					</div>
				</div>
			</sec:authorize>
			<sec:authorize ifAllGranted="FUNCTION_4400">
				<div id="transactionTab">
					<div id="transactionChildTab" >
						<h3>
							<b>Transaction</b>
						</h3>
						<div id="demo" class="yui-navset">
							<ul class="yui-nav">
								<li class="selected">
									<a id="paymentTransactionTabA" href="#paymentTransactionTab"
										onclick="transactionTabLC.clearValueTab();"><em>Payment
											Transaction</em> </a>
								</li>
								<li>
									<a href="#invoiceRejectionTab"
										onclick="transactionTabLC.clearValueTab();"><em>Invoice
											Rejection</em> </a>
								</li>
								<li>
									<a href="#paidThroughOtherWaysTab"
										onclick="transactionTabLC.clearValueTab();"><em>Invoice
											Reconcile</em> </a>
								</li>
								<li>
									<a href="#paymentRejectionTab"
										onclick="transactionTabLC.clearValueTab();"><em>Payment
											Rejection</em> </a>
								</li>
								<li>
									<a href="#pamentResendTab"
										onclick="transactionTabLC.clearValueTab();"><em>Payment
											Resend</em> </a>
								</li>
							</ul>
							<div class="yui-content">
								<div id="paymentTransactionTab">
									<center>
										<table border="0" cellspacing="3" cellpadding="0" width="100%">
											<tr>
												<td colspan="6">
													<table>
														<tr>
															<td>
																Payment Transaction Number:
															</td>
															<td>
																<input id="txtPaymentTransactionNumber">
																<input type="button" value="Search"
																	onclick="transactionTabLC.searchPT();">
															</td>
														</tr>
													</table>
													<br />
													<br />
												</td>
											</tr>
											<tr>

												<td width="10%">
													Vendor :
												</td>
												<td width="21%">
													<input name="txtPaymentTransactionValue"
														style="width: 99%;" readonly="readonly" />
												</td>
												<td width="10%">
													BAN :
												</td>
												<td width="22%">
													<input name="txtPaymentTransactionValue"
														style="width: 99%;" readonly="readonly" />
												</td>
												<td width="15%" nowrap="nowrap">
													Payment Transaction Number :
												</td>
												<td width="22%">
													<input name="txtPaymentTransactionValue"
														style="width: 99%;" readonly="readonly" />
												</td>
											</tr>
											<tr>
												<td nowrap="nowrap">
													Payment Amount :
												</td>
												<td>
													<input name="txtPaymentTransactionValue"
														style="width: 99%;" readonly="readonly" />
												</td>
												<td>
													Payment Date :
												</td>
												<td>
													<input name="txtPaymentTransactionValue"
														style="width: 99%;" readonly="readonly" />
												</td>
											</tr>
											<tr>
												<td>
													Payment Status :
												</td>
												<td>
													<input name="txtPaymentTransactionValue"
														style="width: 99%;" readonly="readonly" />
												</td>
												<td>
													<span style="display: none">Description :</span>
												</td>
												<td>
													<input name="txtPaymentTransactionValue"
														style="width: 99%; display: none;"
														readonly="readonly" />
													<br />
												</td>
											</tr>
											<tr>
												<td colspan="6">
													<br />
													<div id="paymentTransactionDFDiv"></div>
													<form id="downloadPaymentTransactionForm"
														action="download.action" method="post"
														style="display: none;">
														<input type="hidden" name="filePath" />
														<input type="hidden" name="fileName" />
													</form>
												</td>
											</tr>
											<tr id="paymentTransactionTR" style="display: none;">
												<br />
												<td class="tabForm" colspan="6">
													<form id="frmPaymentTransaction"
														name="frmPaymentTransaction"
														action="doPaymentPTConfirm.action"
														enctype="multipart/form-data" method="POST"
														style="position:relative;"
														target="uploadFrame_lc001">
														<table border=0 cellspacing="2" cellpadding="0" width=100%>
															<tr>
																<td colspan="4">
																	<input type="hidden" name="txtPaymentTransactionValue"
																		style="border: 0; width: 99%;" readonly="readonly" />
																</td>
															</tr>
															<tr>
																<td nowrap="nowrap" width=13%>
																	Payment Reference Code:
																</td>
																<td width=38%>
																	<input name="searchPaymentVO.paymentReferenceCode" />
																</td>
																<td width=9%>
																	Load Up:
																</td>

																<td width=40%>
																<div style="float:left;">
																	<input style="height: 19px; width: 100%;" type='text'
																		id="__up_load_text_admin1" disabled="disabled">
																</div><div style="float:left; margin:0 5 0 5px">
																	<span class="ccm_upload_img" style=" width:75px;"> <input
																			id="uploadsPT001"
																			onchange="document.getElementById('__up_load_text_admin1').value=this.value;"
																			class="ccm_file" type="file" name="uploads" size="1" />
																		<input style="height:19px;position:absolute;right:0px;top:0px;" type="button"
																			value="Browse..." /> </span></div>

																	<img src="include/images/reject16.png"
																		onclick="transactionTabLC.clearFileById('uploadsPT001','__up_load_text_admin1');" />
																</td>
															</tr>
															<tr>
																<td>
																	Paid Date:
																</td>
																<td>
																	<input id="txtPaidDate" name="searchPaymentVO.paidDate"
																		readonly="readonly"
																		onClick="g_Calendar.show(event,'txtPaidDate',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;" />
																</td>
																<td>
																	Load Up:
																</td>
																<td>
                                                                    <div style="float:left;">
																	<input style="height: 19px; width: 100%;" type='text'
																		id="__up_load_text_admin2" disabled="disabled">
																		</div>
	                                                            <div style="float:left; margin:0 5 0 5px">
																	<span class="ccm_upload_img" style=" width:75px;"> <input
																			id="uploadsPT002"
																			onchange="document.getElementById('__up_load_text_admin2').value=this.value;"
																			class="ccm_file" type="file" name="uploads" size="1" />
																		<input style="height:19px;position:absolute;right:0px;top:0px;" type="button"
																			value="Browse..." /> </span></div>

																	<img src="include/images/reject16.png"
																		onclick="transactionTabLC.clearFileById('uploadsPT002','__up_load_text_admin2');" />
																</td>
															</tr>
															<tr>
																<td colspan="2">
																	Notes
																</td>
																<td>
																	Load Up:
																</td>
																<td>
																<div style="float:left;">
																	<input style="height: 19px; width: 100%;" type='text'
																		id="__up_load_text_admin3" disabled="disabled">
																		</div>
	                                                            <div style="float:left; margin:0 5 0 5px">
																	<span class="ccm_upload_img" style=" width:75px;"> <input
																			id="uploadsPT003"
																			onchange="document.getElementById('__up_load_text_admin3').value=this.value;"
																			class="ccm_file" type="file" name="uploads" size="1" />
																		<input style="height:19px;position:absolute;right:0px;top:0px;" type="button"
																			value="Browse..." /> </span></div>

																	<img src="include/images/reject16.png"
																		onclick="transactionTabLC.clearFileById('uploadsPT003','__up_load_text_admin3');" />
																</td>
															</tr>
															<tr>
																<td colspan="2" rowspan="4">
																	<textarea id="txtPaymentNotes" style="overflow: auto;"
																		name="searchPaymentVO.notes" rows="4" cols="70"></textarea>
																</td>
															</tr>
															<tr>
																<td>
																	Load Up:
																</td>
																<td>
																<div style="float:left;">
																	<input style="height: 19px; width: 100%;" type='text'
																		id="__up_load_text_admin4" disabled="disabled">
																</div>
	                                                            <div style="float:left; margin:0 5 0 5px">
																	<span class="ccm_upload_img" style=" width:75px;"> <input
																			id="uploadsPT004"
																			onchange="document.getElementById('__up_load_text_admin4').value=this.value;"
																			class="ccm_file" type="file" name="uploads" size="1" />
																		<input style="height:19px;position:absolute;right:0px;top:0px;" type="button"
																			value="Browse..." /> </span></div>

																	<img src="include/images/reject16.png"
																		onclick="transactionTabLC.clearFileById('uploadsPT004','__up_load_text_admin4');" />
																</td>
															</tr>
															<tr>
																<td>
																	Load Up:
																</td>
																<td>
																<div style="float:left;">
																	<input style="height: 19px; width: 100%;" type='text'
																		id="__up_load_text_admin5" disabled="disabled">
																</div>
	                                                            <div style="float:left; margin:0 5 0 5px">
																	<span class="ccm_upload_img" style=" width:75px;"> <input
																			id="uploadsPT005"
																			onchange="document.getElementById('__up_load_text_admin5').value=this.value;"
																			class="ccm_file" type="file" name="uploads" size="1" />
																		<input style="height:19px;position:absolute;right:0px;top:0px;" type="button"
																			value="Browse..." /> </span></div>
																	<img src="include/images/reject16.png"
																		onclick="transactionTabLC.clearFileById('uploadsPT005','__up_load_text_admin5');" />
																</td>
															</tr>
															<tr>
																<td></td>
																<td colspan="3">
																	<input type="button" value=" Clear All Load Up "
																		onclick="transactionTabLC.clearFileAll();" />
																</td>
															</tr>
															<tr>
																<td colspan="2">
																	<input type="button" value="Update"
																		onclick="transactionTabLC.paymentTraSubmit(4);" />
																	<input type="button" value="Close"
																		onclick="transactionTabLC.paymentTraSubmit(5);" />
																	<input id="txtWorkflowUserId"
																		name="searchPaymentVO.workflowUserId" type="hidden">
																	<input id="txtPymentIdPT" name="searchPaymentVO.pid"
																		type="hidden" />
																</td>
															</tr>
														</table>
													</form>
												</td>
											</tr>
										</table>
									</center>
								</div>
								<div id="invoiceRejectionTab">
									<center>
										<table cellspacing="3" cellpadding="0" style="width: 100%;">
											<tr>
												<td colspan="6">
													<table>
														<tr>
															<td>
																Invoice Number:
															</td>
															<td>
																<input id="txtInvoiceNumberIR">
																<input type="button" value="Search"
																	onclick="transactionTabLC.searchIR();">
															</td>
														</tr>
													</table>
													<br />
													<br />
												</td>
											</tr>
											<tr>
												<td width="100px;">
													Vendor Name :
												</td>
												<td>
													<input name="txtInvoiceIRValue"
														style="border: 0; width: 99%;" readonly="readonly" />
												</td>
												<td width="100px;">
													BAN :
												</td>
												<td>
													<input name="txtInvoiceIRValue"
														style="border: 0; width: 99%;" readonly="readonly" />
												</td>
												<td width="150px;">
													Invoice Number :
												</td>
												<td>
													<input name="txtInvoiceIRValue"
														style="border: 0; width: 99%;" readonly="readonly" />
												</td>
											</tr>
											<tr>
												<td>
													Status :
												</td>
												<td>
													<input name="txtInvoiceIRValue"
														style="border: 0; width: 99%;" readonly="readonly" />
												</td>
												<td>
													Invoice Date :
												</td>
												<td>
													<input name="txtInvoiceIRValue"
														style="border: 0; width: 99%;" readonly="readonly" />
												</td>
											</tr>
											<tr>
												<td colspan="6">
													<br />
												</td>
											</tr>
											<tr id="invoiceIRTR" style="display: none;">
												<td colspan="6">
													<div>
														<h3>
															Notes:
														</h3>
													</div>
													<div>
														<textarea id="txtInvoiceRejectNotes"
															onkeyup="transactionTabLC.invoiceReject.checkNotes();"
															rows="5" cols="50"></textarea>
													</div>
													<div>
														<input id="btnRejectIR"
															onclick="transactionTabLC.invoiceReject.submit();"
															type="button" value="Reject" />
													</div>
												</td>
											</tr>
										</table>
									</center>
								</div>
								<div id="#paidThroughOtherWaysTab">
									<center>
										<table cellspacing="3" cellpadding="0" style="width: 100%;">
											<tr>
												<td colspan="6">
													<table>
														<tr>
															<td>
																Invoice Number:
															</td>
															<td>
																<input id="txtInvoiceNumberPTOW">
																<input type="button" value="Search"
																	onclick="transactionTabLC.searchPTOW();">
															</td>
														</tr>
													</table>
													<br />
													<br />
												</td>
											</tr>
										</table>
									</center>
									<div style="width: 100%;">
										<br />
										<div id="_dataTable2"></div>
										<div id="_dataTable_page"></div>
									</div>
								</div>

								<div id="paymentRejectionTab">
									<center>
										<table cellspacing="3" cellpadding="0" style="width: 100%;">
											<tr>
												<td colspan="6">
													<table>
														<tr>
															<td>
																Invoice Number:
															</td>
															<td>
																<input id="txtInvoiceNumberPRJ">
																<input type="button" value="Reject"
																	onclick="transactionTabLC.searchInvoiceNumberPRJ();">
															</td>
														</tr>
													</table>
													<br />
													<br />
												</td>
											</tr>
										</table>
									</center>
								</div>

								<div id="paymentResendTab">
									<center>
										<table cellspacing="3" cellpadding="0" style="width: 100%;">
											<tr>
												<td colspan="6">
													<table>
														<tr>
															<h3>
																<b>Site Error</b>
															</h3>
														</tr>
														<tr>
															<td>
																Invoice Number:
															</td>
															<td>
																<input id="txtInvoiceNumberPRS">
															</td>
														</tr>
														<tr>
															<td>
																AP Supplier Site:
															</td>
															<td>
																<input id="txtSite">
																<input type="button" value="OK"
																	onclick="transactionTabLC.searchForPRS();">
															</td>
														</tr>
													</table>
													<br />
													<br />
												</td>
											</tr>

											<tr>
												<td colspan="6">
													<table>
														<tr>
															<h3>
																<b>SCOA Error</b>
															</h3>
														</tr>
														<tr>
															<td>
																Invoice Number:
															</td>
															<td>
																<input id="txtInvoiceNumberPRS1">
															</td>
														</tr>
														<tr>
															<td>
																Line Number:
															</td>
															<td>
																<input id="txtLineNumber"
																	onkeyup="this.value=this.value.replace(/\D/g, '') ">
															</td>
														</tr>
														<tr>
															<td>
																SCOA:
															</td>
															<td>
																<input id="txtSCOA">
																<input type="button" value="OK"
																	onclick="transactionTabLC.searchForScoa();">
															</td>
														</tr>
													</table>
													<br />
													<br />
												</td>
											</tr>

										</table>
									</center>
								</div>

							</div>
						</div>
					</div>
				</div>
			</sec:authorize>
			<sec:authorize ifAllGranted="FUNCTION_4700">
			<div id="newScoaCodeTab" >
				<h3>
					<b>New SCOA Code</b>
				</h3>
				<table>
					<tr>
						<td width="50">
							SCOA:
						</td>
						<td>
							<input id="txtScoaCodeName_NSCT" size="56" maxlength="42"
								onkeyup="adminScoaTab.req.onkeyupReq(this.value);" />
						</td>
						<td>
							<input id="btnNewScoaCode_NSCT" type="button"
								value="Create a new SCOA code"
								onclick="adminScoaTab.newScoaCode();" />
						</td>
					</tr>
				</table>
				<h3>
					<b>Inactive SCOA Code</b>
				</h3>
				<table>
					<tr>
						<td width="50">
							SCOA:
						</td>
						<td>
							<input id="inactiveScoaCodeName_NSCT" size="56" maxlength="42"
								onkeyup="adminScoaTab.req.onkeyupInactiveReq(this.value);" />
						</td>
						<td>
							<input id="btnInactiveScoaCode_NSCT" type="button"
								value="Inactive a SCOA Code"
								onclick="adminScoaTab.inactiveScoaCode();" />
						</td>
					</tr>
				</table>
			</div>
			</sec:authorize>
			<sec:authorize ifAllGranted="FUNCTION_4800">
			<div id="vendorTab">
				<div style="display: block" id="vendorSearch">
					<br />
					<div class="tabForm">
<%--		2017-06-22		update by mingyang.li list incomplete display
						<table width=100% height=100% border=0 cellpadding="0" style="overflow:hidden" cellspacing="3">--%>
						<table width=100% height=100% border=0 cellpadding="0" cellspacing="3">
							<tr>
								<td width="20%">
									Vendor Name
								</td>
								<td width="35%">
									<div id="VL_vendorMainList_1"></div>
									<input type="text" style="display: none; width: 150px;"
										id="vendorTxtUpdate" />
								</td>
								<td width="20%">
									Status Time
								</td>
								<td width="25%">
									<s:label id="statusTimeTxt" />
								</td>
							</tr>
							<tr>
								<td>
									Vendor Acronym
								</td>
								<td>
									<s:textfield id="vendorAcronymTxt" cssClass="validate-alp" maxlength="32"
										cssStyle="width:150px"></s:textfield>
									&nbsp;&nbsp;
									<span style="font: 15px; color: red; vertical-align: middle;">*</span>
								</td>
								<td>
									Created Time
								</td>
								<td>
									<s:label id="createTimeTxt" />
								</td>
							</tr>
							<tr>
								<td>
									Summary Vendor Name
								</td>
								<td>
									<s:textfield id="summaryVendorNameTxt" cssClass="validate-alp" maxlength="128"
										cssStyle="width:150px"></s:textfield>
									&nbsp;&nbsp;
									<span style="font: 15px; color: red; vertical-align: middle;">*</span>
								</td>
								<td>
									Created By
								</td>
								<td>
									<s:label id="createdByTxt" />
								</td>
							</tr>
							<tr>
								<td>
									Channel
								</td>
								<td>
									<s:textfield id="channelTxt" cssClass="validate-alp" maxlength="6"
										cssStyle="width:150px"></s:textfield>
								</td>
								<td>
									Modified Time
								</td>
								<td>
									<s:label id="modifiedTimeTex" />
								</td>
							</tr>
							<tr>
								<td>
									Lpc Rate
								</td>
								<td>
									<s:textfield id="lpcRateTxt" cssClass="validate-alp" 
										cssStyle="width:150px"></s:textfield>
								</td>
								<td>
									Modified By
								</td>
								<td>
									<s:label id="modifiedByTxt" />
								</td>
							</tr>
							<tr>
								<td>
									Vendor Status
								</td>
								<td>
									<span class="select1"> <s:select id="vendorStatusSelect"
											list="vendorStatusList" listKey="key" listValue="value"
											cssStyle="width:150px" /> </span>&nbsp;&nbsp;
									<span style="font: 15px; color: red; vertical-align: middle;">*</span>
								</td>
							</tr>
							<tr>
								<td colspan="2">
								</td>
							</tr>
						</table>
						<input type="button" id="btnSaveUpdateVendorId" value="Save"
							onclick="updateVendorMain();" />
						&nbsp;&nbsp;&nbsp;
						<input type="button" id="deletedisplay" value="Delete"
							onclick="deleteVendor();" />
						&nbsp;&nbsp;&nbsp;
						<input type="button" id="btnCancelByVendor" value="Cancel"
							onclick="cancelVendorMain();" />
						&nbsp;&nbsp;&nbsp;
						<input type="button" id="btnClear" value="Clear"
							onclick="clearVendorTxt();" />
						&nbsp;&nbsp;&nbsp;
						<input type="button" value="Create New Vendor"
							onclick="createNewVendorPage();" />
					</div>
					<br />
					<div id="vendorContactManagementDiv">
						<div>
							<ul class="yui-nav">
								<li id="contactLi" title="active" Class="selected">
									<a id="paymentTransactionTabA" href="#paymentTransactionTab"
										onclick="showContact();"><em>Contact</em> </a>
								</li>
								<li id="tariffLi">
									<a id="abc" href="#abc" onclick="showTariff();"><em>Tariff
											& Contract</em> </a>
								</li>
							</ul>
						</div>
						<form id="downloadForm" action="download.action" method="post" target="__downloadIframe"
							style="display: none;">
							<input type="hidden" name="filePath" />
							<input type="hidden" name="fileName" />
						</form>
						<div id="contact">
							<table>
								<tr>
									<td>
										<table style="table-layout: fixed; width: 100%;">
											<tr>
												<td>
													<div align="left"
														style="width: 100%; overflow-x: auto; padding-bottom: 20px;">
														<div id="vendorMainTable"></div>
													</div>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>


							<table border=0>

								<tr>
									<td width="240px" id="vendoMainr_pageTable" align="left">
								</td>
								<td align="left" style="padding-top: 10px;">
								<input type="button" id="saveExcel" value="Download to Excel"
											onclick="downloadExcelUpVendorContact();" />
									
										<input type="button" id="addvendor" value="Add Vendor Contact"
											onclick="addContactShow();" />
											</td>
								</tr>
			

							</table>

						</div>

						<div id="Tariff" style="display: none;">
							<table>
								<tr>
									<td>
										<table style="table-layout: fixed; width: 100%;">
											<tr>
												<td>
													<div align="left"
														style="width: 100%; overflow-x: auto; padding-bottom: 20px;">
														<div id="TarrfTable"></div>
													</div>

												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>

							<table border=0>
								<tr>
									<td width="240px" id="Tarrf_pageTable" align="left"></td>
                                     <td width="150px" align="left"></td>
								</tr>
							</table>
							<input type="button" value="Upload Tariff & Contract" onclick="popupUploadTariffWindow();" />
						</div>
					</div>
				</div>
				<div id="uploadTariffWindow">
					<div class="hd">
						<h2>
							Attach Tariff & Contract
						</h2>
					</div>
					<div class="bd">
						<div class="CD_P">
							<div class="cp_p_down">
							<form id="uploadForm" action="doUploadAttachFileByVendor.action" style="position:relative;"
							enctype="multipart/form-data" method="POST" target="uploadFrame_lc001">
								<s:hidden name="vendorCircuitId"></s:hidden>
								<div class="lable">
									<Div class="left">
										Attach File:
									</Div>
									<div class="right">
                    					<div style="padding:3px 0 0 0;height:19px;">
										<div style="float:left;">
										<input style="height:19px;width:195px;" type='text' id="_upload_text_attachment_01" disabled="disabled" class="upload_text">
				       					</div>
       									<div style="float:left; margin:0 0 0 2px">
				       					<span class="ccm_upload_img">
				        					<input onchange="document.getElementById('_upload_text_attachment_01').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
				        					<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
				       					</span>
				       					</div>
       									</div>
									</div>
								</div>
								<div class="lable">
									<Div class="left">
										Attach File:
									</Div>
									<div class="right">
										<div style="padding:3px 0 0 0;height:19px;">
										<div style="float:left;">
										<input style="height:19px;width:195px;" type='text' id="_upload_text_attachment_02" disabled="disabled" class="upload_text">
				       					</div>
       									<div style="float:left; margin:0 0 0 2px">
				       					<span class="ccm_upload_img">
				        					<input onchange="document.getElementById('_upload_text_attachment_02').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
				        					<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
				       					</span>
				       					</div>
       									</div>
									</div>
								</div>
								<div class="lable">
									<Div class="left">
										Attach File:
									</Div>
									<div class="right">
										<div style="padding:3px 0 0 0;height:19px;">
										<div style="float:left;">
										<input style="height:19px;width:195px;" type='text' id="_upload_text_attachment_03" disabled="disabled" class="upload_text">
				       					</div>
       									<div style="float:left; margin:0 0 0 2px">
				       					<span class="ccm_upload_img">
				        					<input onchange="document.getElementById('_upload_text_attachment_03').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
				        					<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
				       					</span>
				       					</div>
       									</div>
									</div>
								</div>
								<div class="lable">
									<Div class="left">
										Attach File:
									</Div>
									<div class="right">
										<div style="padding:3px 0 0 0;height:19px;">
										<div style="float:left;">
										<input style="height:19px;width:195px;" type='text' id="_upload_text_attachment_04" disabled="disabled" class="upload_text">
				       					</div>
       									<div style="float:left; margin:0 0 0 2px">
				       					<span class="ccm_upload_img">
				        					<input onchange="document.getElementById('_upload_text_attachment_04').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
				        					<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
				       					</span>
				       					</div>
       									</div>
									</div>
								</div>
								<div class="lable">
									<Div class="left">
										Attach File:
									</Div>
									<div class="right">
										<div style="padding:3px 0 0 0;height:19px;">
										<div style="float:left;">
										<input style="height:19px;width:195px;" type='text' id="_upload_text_attachment_05" disabled="disabled" class="upload_text">
				       					</div>
       									<div style="float:left; margin:0 0 0 2px">
				       					<span class="ccm_upload_img">
				        					<input onchange="document.getElementById('_upload_text_attachment_05').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
				        					<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
				       					</span>
				       					</div>
       									</div>
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
									<input style="height: 20px;" type="button" value="Submit" onclick="submitUploadForm()"/>
									&nbsp;
									<input style="height: 20px;" type="button" value="Cancel" onclick="cancelUploadForm()"/>
								</div>
							</form>
							</div>
						</div>
					</div>
				</div>
				<div style="display: none" id="createNewVendor">
					<div>
						<h3>
							Create New Vendor
						</h3>
					</div>
					<div class="tabForm">
						<table width=100% height=100% border=0 cellpadding="0"
							cellspacing="3">
							<tr>
								<td width="20%">
									Vendor Name
								</td>
								<td width="35%">
									<s:textfield cssClass="validate-alp" cssStyle="width:150px"
										id="eVendorNameTxt"></s:textfield>
									&nbsp;&nbsp;
									<span style="font: 15px; color: red; vertical-align: middle;">*</span>
								</td>
								<td width="20%">
									Status Time
								</td>
								<td width="25%">
									<s:label id="statusTimeTxtNew" />
								</td>
							</tr>
							<tr>
								<td>
									Vendor Acronym
								</td>
								<td>
									<s:textfield cssClass="validate-alp" cssStyle="width:150px" maxlength="32"
										id="eVendorAcronymTxt"></s:textfield>
									&nbsp;&nbsp;
									<span style="font: 15px; color: red; vertical-align: middle;">*</span>
								</td>
								<td>
									Created Time
								</td>
								<td>
									<s:label id="createTimeTxtNew" />
								</td>
							</tr>
							<tr>
								<td>
									Summary Vendor Name
								</td>
								<td>
									<s:textfield cssClass="validate-alp" cssStyle="width:150px" maxlength="128"
										id="eSummartVendorNameTxt"></s:textfield>
									&nbsp;&nbsp;
									<span style="font: 15px; color: red; vertical-align: middle;">*</span>
								</td>
								<td>
									Created By
								</td>
								<td>
									<s:label id="createdByTxtNew" />
								</td>
							</tr>
							<tr>
								<td>
									Channel
								</td>
								<td>
									<s:textfield cssClass="validate-alp" cssStyle="width:150px" maxlength="6"
										id="eChannelTxt"></s:textfield>
								</td>
								<td>
									Modified Time
								</td>
								<td>
									<s:label id="modifiedTimeTexNew" />
								</td>
							</tr>
							<tr>
								<td>
									Lpc Rate
								</td>
								<td>
									<s:textfield cssClass="validate-alp" cssStyle="width:150px" 
										id="eLpcRateTxt"></s:textfield>
								</td>
								<td>
									Modified By
								</td>
								<td>
									<s:label id="modifiedByTxtNew" />
								</td>
							</tr>
							<tr>
								<td>
									Vendor Status
								</td>
								<td>
									<span class="select1"> <s:select id="eVendorStatusTxt"
											list="vendorStatusList" listKey="key" listValue="value"
											cssStyle="width:150px" /> </span>&nbsp;&nbsp;
									<span style="font: 15px; color: red; vertical-align: middle;">*</span>
								</td>
								<td>
									<s:label id="modifiedByTxtNew" />
								</td>
							</tr>
							<tr>
								<td colspan="2">
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<br />
									<input type="button" id="saveButton" value="Save" onclick="saveVendorMain();" />
									&nbsp;&nbsp;&nbsp;
									<input type="button" id="saveQueryButton" value="Clear"
										onclick="clearVendor();" />
									&nbsp;&nbsp;&nbsp;
									<input type="button" value="Cancel" onclick="canVendor();" />
									&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
						</table>
					</div>
					
					<div id="CreateVendorContactManagementDiv" style="display: none;">
						<div>
							<ul class="yui-nav">
								<li id="createVendorContactLi" title="active" Class="selected">
									<a id="vendorTabC" href="#vendorTabC"
										onclick="showCreateVendorContact();"><em>Contact</em> </a>
								</li>
								<li id="createVendorTariffLi">
									<a id="vendorTabD" href="#vendorTabD" onclick="showCreateVendorTariff();"><em>Tariff
											& Contract</em> </a>
								</li>
							</ul>
						</div>
					</div>
					
					<div style="display: none;" id="createVendorToContact">
						<table>
							<tr>
								<td>
									<table style="table-layout: fixed; width: 100%;">
										<tr>
											<td>
												<div align="left"
													style="width: 100%; overflow-x: auto; padding-bottom: 20px;">
													<br />
													<div id="contactTable"></div>
												</div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<table border=0>
							<tr>
								<td width="240px" id="contact_pageTable" align="left"></td>
								<td width="150px" align="left">
								</td>
							</tr>
							<tr>
								<td width="240px">
									<input type="button" id="saveExcel" value="Download to Excel"
										onclick="downloadExcelNewVendorContact();" />
								</td>
								<td width="150px">
									<input type="button" id="addvendor" value="Add Vendor Contact"
										onclick="addVendorContactShow();" />
								</td>
							</tr>
						</table>
					</div>
					
					<div id="createVendorTariff" style="display: none;">
						<table>
							<tr>
								<td>
									<table style="table-layout: fixed; width: 100%;">
										<tr>
											<td>
												<div align="left"
													style="width: 100%; overflow-x: auto; padding-bottom: 20px;">
													<div id="createVendorTarrfTable"></div>
												</div>

											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>

						<table border=0>
							<tr>
								<td width="240px" id="Tarrf_pageTable" align="left"></td>
                                    <td width="150px" align="left"></td>
							</tr>
						</table>
						<input type="button" value="Upload Tariff & Contract" onclick="popupUploadTariffWindow();" />
					</div>
					
				</div>
			</div>
			</sec:authorize>
			<sec:authorize ifAnyGranted="FUNCTION_4900">
			<div id="banTab">
				<div id="banTabPageContainer">
					<br />
					<div id="search_div" class="tabDetailView">
						<s:form id="banPage_frm" action="" theme="simple">
							<table border="0" width="100%" cellpadding="5" cellspacing="0">
								<tr>
									<td class="tabDetailViewDL" width="50%">
										<div class="searchItemPanel">
											<table width="100%" cellspacing="3" cellpadding="0"
												border="0">
												<tr>
													<td width="30%">
														Vendor Name
													</td>
													<td>
														<s:hidden name="bvo.vendorId"></s:hidden>
														<div id="banPageVendorListDiv"></div>
													</td>
												</tr>
												<tr>
													<td>
														Account Number
													</td>
													<td>
														<s:textfield name="bvo.accountNumber" />
													</td>
												</tr>
												<tr>
													<td>
														AP Supplier Number
													</td>
													<td>
														<s:textfield name="bvo.aPSupplierNumber" />
													</td>
												</tr>
												<tr>
													<td>
														AP Supplier Name
													</td>
													<td>
														<s:textfield name="bvo.aPSupplierName" />
													</td>
												</tr>
											</table>
										</div>
									</td>
									<td class="tabDetailViewDL">
										<div class="searchItemPanel">
											<table width="100%" cellspacing="4" cellpadding="0"
												border="0">
												<tr>
													<td width="30%">
														Ban Status
													</td>
													<td>
														<span class="select1"><s:select
																name="bvo.banStatusId" list="banStatus" headerKey="all"
																headerValue="All" listKey="key" listValue="value"
																cssStyle="width:150px" /> </span>
													</td>
												</tr>
												<tr>
													<td>
														Invoice Channel
													</td>
													<td>
														<span class="select1"><s:select
																name="bvo.invoiceChannelId" list="invoiceChannel"
																headerKey="all" headerValue="All" listKey="key"
																listValue="value" cssStyle="width:150px" /> </span>
													</td>
												</tr>
												<tr>
													<td>
														Invoice Format
													</td>
													<td>
														<span class="select1"><s:select
																name="bvo.invoiceFormatId" list="invoiceFormat"
																headerKey="all" headerValue="All" listKey="key"
																listValue="value" cssStyle="width:150px" /> </span>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
								<tr valign="top">
									<td class="tabDetailViewDL">
										<div class="searchItemPanel">
											<table width="100%" cellspacing="3" cellpadding="0"
												border="0">
												<tr>
													<td width="30%">
														AP Supplier Site
													</td>
													<td>
														<s:textfield name="bvo.aPSupplierSite" />
													</td>
												</tr>
												<tr>
													<td>
														Company
													</td>
													<td>
														<s:textfield name="bvo.company" />
													</td>
												</tr>
												<tr>
													<td>
														Location
													</td>
													<td>
														<s:textfield name="bvo.location" />
													</td>
												</tr>
												<tr>
													<td>
														Channel
													</td>
													<td>
														<s:textfield name="bvo.channel" />
													</td>
												</tr>
											</table>
										</div>
									</td>
									<td class="tabDetailViewDL">
										<div class="searchItemPanel">
											<table width="100%" cellspacing="4" cellpadding="0"
												border="0">
												<tr>
													<td width="30%">
														Payment Method
													</td>
													<td>
														<span class="select1"><s:select
																name="bvo.paymentMethodId" list="paymentMethod"
																headerKey="all" headerValue="All" listKey="key"
																listValue="value" cssStyle="width:150px" /> </span>
													</td>
												</tr>
												<tr>
													<td>
														Analyst
													</td>
													<td>
														<span class="select1"><s:select
																name="bvo.analystId" list="analyst" headerKey="all"
																headerValue="All" listKey="key" listValue="value"
																cssStyle="width:150px" /> </span>
													</td>
												</tr>
												<tr>
													<td>
														Line of Business
													</td>
													<td>
														<span class="select1"><s:select
																name="bvo.lineofBbusiness" list="lineofBbusiness"
																headerKey="all" headerValue="All" listKey="key"
																listValue="value" cssStyle="width:150px" /> </span>
													</td>
												</tr>
												<tr>
													<td>Ban Inactive Reason</td>
													<td>
														<span class="select1">
														    <s:select name="bvo.banInactiveReasonId" list="banInactiveReason" headerKey="all" 
														    headerValue="All" listKey="key" listValue="value" cssStyle="width:150px"/>
														</span>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2" height="35">
										<input onclick="banMaintenanceTab.searchBan();" type="button"
											value="Search">
										<input id="btnCancelBanSearch"
											onclick="common_Json.clearForm_YUI('banPage_frm');banPageVendorFlexbox.setValue('null');$('#banPage_frm_bvo_vendorId').val('');"
											type="button" value="Clear">
										<input type="button" value="Create New BAN"
											onclick="banMaintenanceTab.newBanHref();">
										<input id="btnBanPageDownExcel" disabled="disabled"
											type="button" value="Download To Excel"
											onclick="banMaintenanceTab.downloadToExcel();">
									</td>
								</tr>
							</table>
						</s:form>
					</div>
					<table>
						<tr>
							<td>
								<table style="table-layout: fixed; width: 100%;">
									<tr>
										<td>
											<br />
											<div align="left"
												style="width: 100%; overflow-x: auto; padding-bottom: 20px;">
												<div id="showInfo_div"></div>
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<!-- yinghe.fu-->
            </sec:authorize>
			<sec:authorize ifAllGranted="FUNCTION_4110">
			<div id="tab1">
				<div id="__report_tags1" style="width: 100%"></div>
				<table width="100%" border="0">
					<tr>
						<td width="250px">
							<div id="__report_tags_page1" align="left"></div>
						</td>
						<td>
							<input type="button" value="New Team" onclick="newTeam();"
								align="right" />
						</td>
						<td>
							<div id="__validation_tag_innetHTML" style="color: red;"></div>
						</td>
					</tr>
				</table>
			</div>
			</sec:authorize>
			<sec:authorize ifAllGranted="FUNCTION_4120">
			
			<div id="modifyAdjustmentTab">
				<br />
				<div class="tabForm">
					<table width=100% height=100% border=0 cellpadding="0"
						cellspacing="3">
						<tr>
							<td width="12%">
								Invoice Number
							</td>
							<td width="17%">
								<input type="text" style="width: 150px;" id="invoiceNumber" />
							</td>
							<td><input type="button"value="Search" onclick="searchInvoiceCharge(null)" /></td>
							<td width="35%"></td>
						</tr>
					</table>
				</div>
				<br/>
				<div id="invoiceDetailDiv" class="tabForm" style="display:none">
					<table width=100% height=100% border=0 cellpadding="0"
						cellspacing="2">
						<input type="hidden" id="txt_invoiceId"/>
						<tr>
							<td width="8%">
								Vendor
							</td>
							<td width="17%">
								<input type="text" style="width: 220px;" id="txt_vendor" readonly="readonly" />
							</td>
							<td width="8%">
								BAN
							</td>
							<td width="25%">
								<input type="text" style="width: 220px;" id="txt_ban" readonly="readonly" />
							</td>
						</tr>
						
						<tr>
							<td width="8%">
								Invoice Number
							</td>
							<td width="17%">
								<input type="text" style="width: 220px;" id="txt_invoiceNumber" readonly="readonly" />
							</td>
							<td width="8%">
								Invoice Date
							</td>
							<td width="25%">
								<input type="text" style="width: 220px;" id="txt_invoiceDate" readonly="readonly" />
							</td>
						</tr>
						
						<tr>
							<td width="8%">
								Invoice Previous Balance
							</td>
							<td width="17%">
								<input type="text" style="width: 220px;" id="txt_invoicePreviousBalance" readonly="readonly" />
							</td>
							<td width="8%">
								Invoice Previous Payment
							</td>
							<td width="25%">
								<input type="text" style="width: 220px;" id="txt_invoicePreviousPayment" readonly="readonly" />
							</td>
						</tr>
						
						<tr>
							<td width="8%">
								Invoice Previous Adjustment
							</td>
							<td width="17%">
								<input type="text" style="width: 220px;" id="txt_invoicePreviousAdjustment" readonly="readonly" />
							</td>
							<td width="8%">
								Invoice Balance Forward
							</td>
							<td width="25%">
								<input type="text" style="width: 220px;" id="txt_invoiceBalanceForward" readonly="readonly" />
							</td>
						</tr>
						
						<tr>
							<td width="8%">
								Invoice Adjustment
							</td>
							<td width="17%">
								<input type="text" style="width: 220px;" id="txt_invoiceAdjustment" readonly="readonly" />
							</td>
							<td width="8%">
								LPC
							</td>
							<td width="25%">
								<input type="text" style="width: 220px;" id="txt_invoiceLPC" readonly="readonly" />
							</td>
						</tr>
						
						<tr>
							<td width="8%">
								Invoice Credit
							</td>
							<td width="17%">
								<input type="text" style="width: 220px;" id="txt_invoiceCredit" readonly="readonly" />
							</td>
							<td width="8%">
								Invoice MRC
							</td>
							<td width="25%">
								<input type="text" style="width: 220px;" id="txt_invoiceMrc" readonly="readonly" />
							</td>
						</tr>
						
						<tr>
							<td width="8%">
								Invoice OCC
							</td>
							<td width="17%">
								<input type="text" style="width: 220px;" id="txt_invoiceOcc" readonly="readonly" />
							</td>
							<td width="8%">
								Invoice Usage
							</td>
							<td width="25%">
								<input type="text" style="width: 220px;" id="txt_invoiceUsage" readonly="readonly" />
							</td>
						</tr>
						
						<tr>
							<td width="8%">
								Invoice Tax
							</td>
							<td width="17%">
								<input type="text" style="width: 220px;" id="txt_invoiceTax" readonly="readonly" />
							</td>
							<td width="8%">
								Invoice Current Due
							</td>
							<td width="25%">
								<input type="text" style="width: 220px;" id="txt_invoiceCurrentDue" readonly="readonly" />
							</td>
						</tr>
						
						<tr>
							<td width="8%">
								Invoice Total Due
							</td>
							<td width="17%">
								<input type="text" style="width: 220px;" id="txt_invoiceTotalDue" readonly="readonly" />
							</td>
							<td width="8%">
							</td>
							<td width="25%">
							</td>
						</tr>
						<tr>
							<td colspan="4" height="35">
								<input onclick="onMovePreviousAdjustmentPanel()" type="button"
									value="Move Previous Adjustment  to Current Invoice Adjustment">
								&nbsp;&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div id="invoiceItemDetailDiv">
					<table>
						<tr>
							<td>
								<table style="table-layout: fixed; width: 100%;">
									<tr>
										<td>
											<div align="left"
												style="width: 100%; overflow-x: auto; padding-bottom: 20px;">
												<div id="showAdjustmentAndTaxTable"></div>
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
								<div style="margin-top: -5px;" id="showAdjustmentAndTax_pageTable"></div>
								<div style="margin-top: -23px; margin-left: 260px;">
									<input type="button" onclick="onMoveCurrentToAdjustmentPanel()"
										value="Move Current Invoice Adjustment to Previous Adjustment"/>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			</sec:authorize>
			<sec:authorize ifAllGranted="FUNCTION_4130">
			<div id="securityIp">
				<div id="__report_tags2" style="width: 100%"></div>
				<table width="100%" border="0">
					<tr>
						<td width="250px">
							<div id="__report_tags_page2" align="left"></div>
						</td>
						<td>
							<input  type="button" id="newSecurityIpBlock" style="display:block;" value="New Security IP" onclick="newSecurityIp()"
								align="right" />
						</td>
						<td>
							<div id="__validation_tag_innetHTML" style="color: red;"></div>
						</td>
					</tr>
				</table>
			</div>
            </sec:authorize>
			<sec:authorize ifAllGranted="FUNCTION_4140">
			<div id="wikiManagement">
			<div class="yui-content" id="wikiMangementList" style="padding:10px;">
	           	<div class="wikiTit">Wiki Management</div>
	           	<div class="wikiInput">
	           	  <table border=0 cellspacing=3 cellpadding=0 width="100%" height="100%">
                	<tr>
	                  <td width="30">Title:</td>
	                  <td width="160"><input id="searchTitle" style="width: 150px"></td>
	                  <td><input value="Search" type="button" onclick="searchWiki()" style="padding:0 5px;">&nbsp;&nbsp;&nbsp;<input value="Reset" onclick="resetResult()" type="button" style="padding:0 5px;">&nbsp;&nbsp;&nbsp;<input value="Create Wiki" onclick="publishView()" type="button" style="padding:0 5px;"></td>
	                </tr>
	               </table>
	           	</div>
	           	<div id="wikitab1">
				<div id="wiki_dataTable" style="width: 100%"></div>
				<table width="100%" border="0">
					<tr>
						<td width="250px">
							<div id="wiki_dataTable_page" align="left"></div>
						</td>
					</tr>
				</table>
			</div>
		    </div></div>
			
				<div class="yui-content" id="wikiPublish" style="padding:10px">
                          	<div class="wikiTit">Wiki Management</div>
                          	<div class="wikiInput">
                          	<table border=0 cellspacing=3 cellpadding=0 width="100%" height="100%">
                                <tr>
                                  <td width="60">Title:</td>
                                  <td width="160"><input id="title" maxlength="68" style="width: 150px"></td>
                                  <td>&nbsp;&nbsp;&nbsp;<font color="red">(Title length can't be more than 68 characters)</font></td>
                                </tr>
                                <tr>
                                  <td width="60">On Top:</td>
                                  <td width="160"><input type="checkbox" id="lististop" style="border:none;"/></td>
                                  <td>&nbsp;&nbsp;&nbsp;</td>
                                </tr>
                            </table>
                          	</div>
                          	<div style="font-size: 11;margin-left: 10px">
                          		<font color="red">(The image size uploaded can't be more than 5 MB,Content length can't be more than 3000 characters)</font>
                          	</div>
                          	<div class="wikiTbox" style="width: 100%">
                          		<s:hidden id="wikiId"/>
								<textarea cols="50" name="wiki.content" id="wikiContent">${wiki.content}</textarea>
					        	<script type="text/javascript">
					        		function getContentCkeditorValue(){
					        			if (CKEDITOR.instances.wikiContent) {
					        				return CKEDITOR.instances.wikiContent.getData();
					        			} else {
					        				return "";
					        			}
					        		}
					        		
					        		CKEDITOR.editorConfig = function( config ) {
									    config.language = "en";
										config.filebrowserBrowseUrl = "food_imageBrowser.action";
										config.filebrowserUploadUrl = "food_ckEditorUpload.action";
										config.toolbar = "[{ name: 'document', items : [ 'Source','-','NewPage','DocProps','Preview','Print','-','Templates' ] },"+
										"{ name: 'clipboard', items : [ 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo' ] },"+
										"{ name: 'editing', items : [ 'Find','Replace','-','SelectAll','-','SpellChecker', 'Scayt' ] },"+
										"{ name: 'forms', items : [ 'Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton','HiddenField' ] },"+
										"'/',"+
										"{ name: 'basicstyles', items : [ 'Bold','Italic','Underline','Strike','Subscript','Superscript','-','RemoveFormat' ] },"+
										"{ name: 'paragraph', items : [ 'NumberedList','BulletedList','-','Outdent','Indent','-','Blockquote','CreateDiv',"+
										"'-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','BidiLtr','BidiRtl' ] },"+
										"{ name: 'links', items : [ 'Link','Unlink','Anchor' ] },"+
										"{ name: 'insert', items : [ 'Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak','Iframe' ] },"+
										"'/',"+
										"{ name: 'styles', items : [ 'Styles','Format','Font','FontSize' ] },"+
										"{ name: 'colors', items : [ 'TextColor','BGColor' ] },"+
										"{ name: 'tools', items : [ 'Maximize', 'ShowBlocks','-','About' ] }]";
									};
					        	</script>
                          	</div>
                          	<div class="wikiInput"><input value="Publish" onclick="publishWiki()" type="button" style="padding:0 5px;">&nbsp;&nbsp;&nbsp;<input value="Cancel" onclick="cancelPublish()" type="button" style="padding:0 5px;"></div>
                          </div>
                          </div>
                          </sec:authorize>

			<div class="del-reportAdminTags" id="delReportAdminTags">
				<div class="hd">
					Please confirm
				</div>
				<div class="bd">
					<table cellspacing=10 cellpadding=0 border=0 width=100%>
						<tr>
							<td id="__del_rport_admin_tags_innerHTML">
								Please confirm whether to delete !
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="update-ReportAdminTags" id="updateReportAdminTags" >
				<div class="hd">
					Please increase the data
				</div>
				<div id="update-report-admin-tags"></div>
			</div>

			<div class="update-ReportAdminTags" id="updateTeams">
				<div class="hd">
					Please choose user
					<!--						 Please increase the data  -->
				</div>
				<div id="update-report-admin-tags1"></div>
			</div>
			
			<div class="update-ReportAdminTags" id="updateSecurityIp">
				<div class="hd">
					Security IP Rule Setup
					<!--						 Please increase the data  -->
				</div>
				<div id="update-report-admin-tags2"></div>
			</div>

			<div class="update-ReportAdminTagsAndRoles"
				id="updateReportAdminTagsAndRoles" >
				<div class="hd">
					Edit Tags and Roles
				</div>
				<div id="update-report-admin-tagsandroles"></div>
			</div>

			<div id="reportAdminTags" style="display: none;">
				<table border="0" width=300px cellpadding="5" cellspacing="0">
					<tr valign="top">
						<td width="34%">
							<div class="searchItemPanel" align="left">
								<table width="300px" border="0">
									<tr>
										<td width="120" style="color: 000">
											Tag Name
										</td>
										<td colspan="2">
											<input id="__tag_name" class="validate-address" value=""
												style="width: 80" />
										</td>
									</tr>
									<tr>
										<td style="color: 000">
											Color
										</td>
										<td>
											<input id="__tag_color" class="validate-color" value=""
												style="width: 80" />
										</td>
										<td>
											<div id="colorShow"></div>
										</td>
									</tr>
									<tr>
										<td colspan="3">
											<input id="_save_button" type="button" value="Save" />
											&nbsp;&nbsp;
											<input id="_restore_button" type="button" value="restore" />
											&nbsp;&nbsp;
											<input id="_cancel_button" type="button" value="Cancel" />
											&nbsp;&nbsp;
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</div>

			<div id="adminTeam" style="display: none;">
				<table border="0" width=300px cellpadding="5" cellspacing="0">
					<tr valign="top">
						<td width="34%">
							<div class="searchItemPanel" align="left">
								<table width="300px" border="0">
									<tr>
										<td width="120" style="color: 000">
											From User
										</td>
										<td colspan="2">
											<s:select list="supervisorUserList" id="_fromuser"
												listKey="key" listValue="value" />
										</td>
									</tr>
									<tr>
										<td style="color: 000">
											To User
										</td>
										<td>
											<s:select list="supervisorUserList" id="_touser"
												listKey="key" listValue="value" />
										</td>
									</tr>
									<tr>
										<td colspan="3">
											<input id="_save_button1" type="button" value="Save" />
											&nbsp;&nbsp;
											<input id="_cancel_button1" type="button" value="Cancel" />
											&nbsp;&nbsp;
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</div>
			
			<div id="AdminSecuritIp" style="display: none;">
				<table border="0" width=380px cellpadding="5" cellspacing="0">
					<tr valign="top">
						<td width="34%">
							<div class="searchItemPanel" align="left">
								<table width="380px" border="0">
									<tr>
									    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									    IP Range     <input id="ipRangeRadio" type="radio" name="ipRadio" checked="true" onclick="$('#textIpTo').show();$('#textIpFrom').html('IP From');"/>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										IP           <input id="ipRadio" type="radio" name="ipRadio" onclick="$('#textIpTo').hide();$('#textIpFrom').html('IP');"/>
									</tr>
									<tr>
										<td id="textIpFrom" width="120" style="color: 000">
											IP From
										</td>
										<td colspan="2">
											<s:textfield id="textIpA"/>
										</td>
									</tr>
									<tr id="textIpTo">
										<td width="120" style="color: 000">
											IP To
										</td>
										<td colspan="2">
											<s:textfield id="textIpB"/>
										</td>
									</tr>
									<tr>
										<td style="color: 000">
											Start Date
										</td>
										<td nowrap="nowrap">
											<input id="textStartDate" class="validate-date-caaa"/>
											<a
													onClick="g_Calendar.show(event,'textStartDate',false, 'yyyy-mm-dd', new Date(1990,1,1)); return false;"
													href="javascript:%20void(0);"><img
													src="include/images/cal.gif"
													id="ticketDueStartsOnImg" border="0" align="middle">
													</a>yyyy-mm-dd
										</td>
									</tr>
									<tr>
										<td style="color: 000">
											End Date
										</td>
										<td nowrap="nowrap">
											<input id="textEndDate" class="validate-date-caaa"/>
											<a onClick="g_Calendar.show(event,'textEndDate',false, 'yyyy-mm-dd', new Date(1990,1,1)); return false;"
													href="javascript:%20void(0);"><img
													src="include/images/cal.gif"
													id="ticketDueStartsOnImg" border="0" align="middle">
													</a>yyyy-mm-dd
										</td>
									</tr>
									<tr>
											<td width="30%">
												User Name
											</td>
											<td>
												<span class="select1"> <s:select
														id="textsecuirtyuser" list="secuirtyuserList"
														headerKey="all" headerValue="All" listKey="key"
														listValue="value" cssStyle="width:127px" /> </span>
											</td>
										</tr>
									<tr>
									<tr>
											<td width="30%">
												Active Flag
											</td>
											<td>
												<span class="select1">
												<s:select id="textActiveFlag" list="#{'Y':'Y','N':'N'}" listKey="key"
														listValue="value" cssStyle="width:127px" /> 
												</span>
											</td>
										</tr>
									<tr>
										<td colspan="3">
											<input id="_save_button2" type="button" value="Save" />
											&nbsp;&nbsp;
											<input id="_cancel_button2" type="button" value="Cancel" />
											&nbsp;&nbsp;
										</td>
									</tr>
								</table>

							</div>
						</td>
					</tr>
				</table>
			</div>
			
			<div id="reportAdminTagsAndRoles" style="display: none;">
				<table border="0" width=400px cellpadding="5" cellspacing="0">
					<tr valign="top">
						<td width="34%">
							<div class="searchItemPanel">
								<table width="400px" height="80" border="0">
									<tr>
										<td style="color: 000">
											Tags
										</td>
										<td>
											<span class="select1"><s:select
													id="__viewReportAdminVO_rtagId" list="rtagsList"
													listKey="key" listValue="value" style="width:130px;" /> </span>
										</td>
										<td style="color: 000">
											Roles
										</td>
										<td>
											<span class="select1"><s:select
													id="__viewReportAdminVO_roleId" list="rolesList"
													listKey="key" listValue="value" style="width:130px;" /> </span>
										</td>
									</tr>
									<tr>
<%--										<td width="150" style="color: 000">--%>
<%--											Access Level--%>
<%--										</td>--%>
<%--										<td>--%>
<%--											<span class="select1"><select id="__access_level"--%>
<%--													style="width: 130px;">--%>
<%--													<option value="1">--%>
<%--														Read--%>
<%--													</option>--%>
<%--													<option value="2">--%>
<%--														Read/Run--%>
<%--													</option>--%>
<%--													<option value="3">--%>
<%--														Read/Run/Share--%>
<%--													</option>--%>
<%--												</select> </span>--%>
<%--										</td>--%>
<%--										<td width="150" style="color: 000">--%>
<%--											Email--%>
<%--										</td>--%>
<%--										<td>--%>
<%--											<span class="select1"><select id="__email"--%>
<%--													style="width: 130px;">--%>
<%--													<option value="1">--%>
<%--														Attachment--%>
<%--													</option>--%>
<%--													<option value="2">--%>
<%--														Link--%>
<%--													</option>--%>
<%--													<option value="3">--%>
<%--														No--%>
<%--													</option>--%>
<%--												</select> </span>--%>
<%--										</td>--%>
									</tr>
									<tr>
										<td colspan="4">
											<input id="_save_button_two" type="button" value="Save" />
											&nbsp;&nbsp;
											<input id="_restore_button_two" type="button" value="restore" />
											&nbsp;&nbsp;
											<input id="_cancel_button_two" type="button" value="Cancel" />
											&nbsp;&nbsp;
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div id="yui-picker-panel" class="yui-picker-panel" >
				<div class="hd">
					Please choose a color:
				</div>
				<iframe name="__color_picker_iframe" src="colorPickerControl.action"
					style="height: 215px; width: 490px; margin: 0px; padding: 0px;">
				</iframe>
				Choose the color by clicking on the it.
			</div>
		</div>
	</div>
</div>
<%--  lmy --%>
<div id="movePreviousAdjustmentPanel">
	<div class="hd">
		<h2>
			Move Previous Adjustment to Current Invoice Adjustment
		</h2>
	</div>
	<div class="bd">
		<table width="95%" align="center">
			<tr>
				<td width="10%">
					Previous Adjustment Amount:
				</td>
				<td width="17%">
					<input type="text" style="width: 220px;" id="txt_previousAdjustment" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td width="10%">
					Current Invoice Adjustment:
				</td>
				<td width="17%">
					<input type="text" style="width: 220px;" id="txt_currentInvoice" class="mustWrite"
					onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
					onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
					onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}"
					/><span style="font:15px;color:red;vertical-align:middle;">*</span>
				</td>
			</tr>
			<tr>
				<td width="10%">
					SCOA for Current Invoice Adjustment:
				</td>
				<td width="17%">
					<div id="VL_SCOAList"></div><span style="font:15px;color:red;vertical-align:middle;">*</span>
				</td>
			</tr>
			<tr>
				<td valign="top">
					Notes:
				</td>
				<td>
					<s:textarea id="txt_notes"
						cssStyle="padding:0px 1px;overflow:auto ;width:260px;height:60px;"
						cssClass="validate-text mustWrite"></s:textarea><span style="font:15px;color:red;vertical-align:middle;">*</span>
				</td>
			</tr>
			</table>
			<div id="modify_Adjustment_tax_1"></div>
			<table width="95%" align="center">
				<tr>
					<td colspan="2" height="35">
						<input onclick="addTax();" type="button"
							value="Add Tax">
						&nbsp;&nbsp;
						<input onclick=" YAHOO.ccm.container.movePreviousAdjustmentPanel.hide();" type="button"
							value="Cancel">
						&nbsp;&nbsp;
						<input onclick="saveModifyPreviousAdjustment()" type="button"
							value="Save">
						&nbsp;&nbsp;
					</td>
				</tr>
			</table>
	</div>
</div>

<div id="moveCurrentToAdjustmentPanel">
	<div class="hd">
		<h2>
			Move Current Invoice Adjustment to Previous Adjustment
		</h2>
	</div>
	<div class="bd">
		<table width="95%" align="center">
			<tr>
				<td width="10%" id="labelName">
				</td>
				<td width="17%">
					<input type="text" style="width: 220px;" id="txt_adjustmentOrTaxAmount" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td width="10%" id="labelAdjustmentOrTax">
				</td>
				<td width="17%">
					<input type="text" style="width: 220px;" id="txt_currentInvoice_1"
					onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
					onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
					onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}"
					/><span style="font:15px;color:red;vertical-align:middle;">*</span>
				</td>
			</tr>
			<tr>
				<td valign="top">
					Notes:
				</td>
				<td>
					<s:textarea id="txt_notes_1"
						cssStyle="padding:0px 1px;overflow:auto ;width:260px;height:60px;"
						cssClass="validate-text"></s:textarea><span style="font:15px;color:red;vertical-align:middle;">*</span>
				</td>
			</tr>
		</table>
		<table width="95%" align="center">
			<tr>
				<td colspan="2" height="35">
					<input onclick=" YAHOO.ccm.container.moveCurrentToAdjustmentPanel.hide();" type="button"
						value="Cancel">
					&nbsp;&nbsp;
					<input onclick="saveMoveCurrentToAdjustment()" type="button"
						value="Save">
					&nbsp;&nbsp;
				</td>
			</tr>
		</table>
	</div>
</div>
<%--  lmy --%>
<div id="replaceSupervisorDiv" >
	<div class="hd">
		<h2>
			Replace Supervisor User
		</h2>
	</div>
	<div class="bd">
		<table width="95%" align="center">
			<tr>
				<td colspan="2">
					&nbsp;&nbsp;This user is a supervisor. If delete,please choose
					another user to replace it!
				</td>
			</tr>
			<tr>
				<th>
					Choose User:
				</th>
				<td>
					<span class="select1"><select id="sRepeatUser"></select> </span>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<br />
					<br />
					<div style="margin-left: 25%;">
						<div id="btnDelDiv" style="float: left;"></div>
						<div style="float: left; margin-left: 35px;">
							<input type="button" value="Cancel"
								onclick="YAHOO.ccm.container.replaceSupervisorPanel.hide();" />
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
</div>
<div id="newConcate">
	<div class="hd">
		<h2>
			Create Contact
		</h2>
	</div>
	<div class="bd">
		<form id="doAddContact_action_from" action="doAddContact.action"
			method="POST" theme="simple" enctype="multipart/form-data"
			target="uploadFrame_lc001">
			<table border="0" width="100%" align="center">
				<tr>
					<td width="90px" align="right">
						First Name:
						<s:hidden name="cvo.vendor" id="tovendorid" />
					</td>
					<td>
						<input id="cvofirstName" name="cvo.firstName" type="text"
							size="36">
					</td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td nowrap="nowrap" align="right">
						Last Name :
					</td>
					<td>
						<input id="cvolastName" name="cvo.lastName" type="text" size="36">
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Attention Name:
					</td>
					<td>
						<input id="cvoattentionName" name="cvo.attentionName" type="text"
							size="36">
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Co Name:
					</td>
					<td>
						<input id="cvocoName" name="cvo.coName" type="text" size="36">
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Address_1:
					</td>
					<td>
						<input id="cvoaddress1" name="cvo.address1" type="text" size="36">
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Address_2:
					</td>
					<td>
						<input id="cvoaddress2" name="cvo.address2" type="text" size="36">
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						City:
					</td>
					<td>
						<input id="cvocity" name="cvo.city" type="text" size="36">
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Province:
					</td>
					<td>
						<input id="cvoprovincee" name="cvo.province" type="text" size="36" />
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Country:
					</td>
					<td>
						<input id="cvocountry" name="cvo.country" type="text" size="36">
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Postal Code:
					</td>
					<td>
						<input id="cvopostalCode" name="cvo.postalCode" type="text"
							size="36">
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Primary Phone:
					</td>
					<td>
						<input id="cvoprimaryPhone" name="cvo.primaryPhone" type="text"
							size="36">
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Other Phone:
					</td>
					<td>
						<input id="cvootherPhone" name="cvo.otherPhone" type="text"
							size="36">
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Office Phone:
					</td>
					<td>
						<input id="cvoofficePhone" name="cvo.officePhone" type="text"
							size="36">
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Cell Phone:
					</td>
					<td>
						<input id="cvocellPhone" name="cvo.cellPhone" type="text"
							size="36">
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Fax Number:
					</td>
					<td>
						<input id="cvofaxNumber" name="cvo.faxNumber" type="text"
							size="36">
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Email:
					</td>
					<td>
						<input id="cvoemail" name="cvo.email" type="text" size="36">
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<input id="btnContactSave" type="button" value="Save"
							onclick="verifyContact();" />
						<input type="button" value="Cancel"
							onclick="clearEmailError('cvoemail');YAHOO.ccm.container.addConcatePanel.hide();" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<div id="editConcate">
	<div class="hd">
		<h2>
			Edit Contact
		</h2>
	</div>
	<div class="bd">
		<s:form action="doEditContact.action"
			onsubmit="return editValidateemail();" method="POST" theme="simple"
			enctype="multipart/form-data" target="uploadFrame_lc001">
			<table border="0" width="95%" align="center">
				<tr>
					<td align="right">
						First Name:
						<s:hidden name="cvo.id" id="toContactId" />
					</td>
					<td>
						<input id="ecvofirstName" name="cvo.firstName" type="text"
							size="36">
					</td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td nowrap="nowrap" align="right">
						Last Name :
					</td>
					<td>
						<input id="ecvolastName" name="cvo.lastName" type="text" size="36">
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Attention Name:
					</td>
					<td>
						<input id="ecvoattentionName" name="cvo.attentionName" type="text"
							size="36">
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Co Name:
					</td>
					<td>
						<input id="ecvocoName" name="cvo.coName" type="text" size="36">
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Address_1:
					</td>
					<td>
						<input id="ecvoaddress1" name="cvo.address1" type="text" size="36">
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Address_2:
					</td>
					<td>
						<input id="ecvoaddress2" name="cvo.address2" type="text" size="36">
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						City:
					</td>
					<td>
						<input id="ecvocity" name="cvo.city" type="text" size="36">
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Province:
					</td>
					<td>
						<input id="evoprovince" name="cvo.province" type="text" size="36" />
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Country:
					</td>
					<td>
						<input id="ecvocountry" name="cvo.country" type="text" size="36">
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Postal Code:
					</td>
					<td>
						<input id="ecvopostalCode" name="cvo.postalCode" type="text"
							size="36">
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Primary Phone:
					</td>
					<td>
						<input id="ecvoprimaryPhone" name="cvo.primaryPhone" type="text"
							size="36">
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Other Phone:
					</td>
					<td>
						<input id="ecvootherPhone" name="cvo.otherPhone" type="text"
							size="36">
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Office Phone:
					</td>
					<td>
						<input id="ecvoofficePhone" name="cvo.officePhone" type="text"
							size="36">
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Cell Phone:
					</td>
					<td>
						<input id="ecvocellPhone" name="cvo.cellPhone" type="text"
							size="36">
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Fax Number:
					</td>
					<td>
						<input id="ecvofaxNumber" name="cvo.faxNumber" type="text"
							size="36">
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Email:
					</td>
					<td>
						<input id="ecvoemail" name="cvo.email" type="text" size="36">
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<s:submit id="btnContactSave" type="submit" value="Save" />
						&nbsp;&nbsp;&nbsp;
						<input type="button" value="Cancel"
							onclick="clearEmailError('ecvoemail');YAHOO.ccm.container.editConcatePanel.hide();" />
					</td>
				</tr>
			</table>
		</s:form>
	</div>
</div>
<div id="newVendorConcate">
	<div class="hd">
		<h2>
			Create Contact
		</h2>
	</div>
	<div>
		<s:form action="doAddVendorContact.action"
			onsubmit="return newValidateemail();" method="POST" theme="simple"
			enctype="multipart/form-data" target="uploadFrame_lc001">
			<table border="0" width="95%" align="center">
				<tr>
					<td align="right">
						First Name:
						<s:hidden name="cvo.vendor" id="tovendorid" />
					</td>
					<td>
						<input id="cvofirstName1" name="cvo.firstName" type="text"
							size="36" />
					</td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td nowrap="nowrap" align="right">
						Last Name :
					</td>
					<td>
						<input id="cvolastName1" name="cvo.lastName" type="text" size="36" />
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Attention Name:
					</td>
					<td>
						<input id="cvoattentionName1" name="cvo.attentionName" type="text"
							size="36" />
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Co Name:
					</td>
					<td>
						<input id="cvocoName1" name="cvo.coName" type="text" size="36" />
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Address_1:
					</td>
					<td>
						<input id="cvoaddress11" name="cvo.address1" type="text" size="36" />
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Address_2:
					</td>
					<td>
						<input id="cvoaddress21" name="cvo.address2" type="text" size="36" />
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						City:
					</td>
					<td>
						<input id="cvocity1" name="cvo.city" type="text" size="36" />
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Province:
					</td>
					<td>
						<input id="cvoprovince1" name="cvo.province" type="text" size="36" />
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Country:
					</td>
					<td>
						<input id="cvocountry1" name="cvo.country" type="text" size="36" />
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Postal Code:
					</td>
					<td>
						<input id="cvopostalCode1" name="cvo.postalCode" type="text"
							size="36" />
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Primary Phone:
					</td>
					<td>
						<input id="cvoprimaryPhone1" name="cvo.primaryPhone" type="text"
							size="36" />
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Other Phone:
					</td>
					<td>
						<input id="cvootherPhone1" name="cvo.otherPhone" type="text"
							size="36" />
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Office Phone:
					</td>
					<td>
						<input id="cvoofficePhone1" name="cvo.officePhone" type="text"
							size="36" />
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Cell Phone:
					</td>
					<td>
						<input id="cvocellPhone1" name="cvo.cellPhone" type="text"
							size="36" />
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Fax Number:
					</td>
					<td>
						<input id="cvofaxNumber1" name="cvo.faxNumber" type="text"
							size="36" />
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">
						Email:
					</td>
					<td>
						<input id="newcvoemail1" name="cvo.email" type="text" size="36" />
					</td>
				</tr>
				<tr>
				<td></td>
					<td>
						<s:submit id="btnContactSave" type="submit" value="Save" />
						&nbsp;&nbsp;&nbsp;
						<input type="button" value="Cancel"
							onclick="clearEmailError('newcvoemail1');YAHOO.ccm.container.addVendorConcatePanel.hide();" />
					</td>
				</tr>
			</table>
		</s:form>
	</div>
</div>

<div class="yui-pe-content" id="cancelInvoicePanel">
	<div class="hd">
		<h2>
			Close Invoice
		</h2>
	</div>
	<div class="bd">
		<table height="100%" width="95%" align="center" border="0">
			<tr height="15px">
				<td>
					Invoice Number:
				</td>
				<td>
					<s:hidden id="cancelInvoiceId" />
					<span id="cancelInvoiceNumber"></span>
				</td>
			</tr>
			<tr>
				<td>
					Vendor
				</td>
				<td>
					<span id="cancelVendor"></span>
				</td>
			</tr>
			<tr>
				<td>
					Ban
				</td>
				<td>
					<span id="cancelBan"></span>
				</td>
			</tr>
			<tr>
				<td>
					Invoice Status
				</td>
				<td>
					<span id="cancelInvoiceStatus"></span>
				</td>
			</tr>
			<tr>
				<td>
					Invoice Current Amount
				</td>
				<td>
					<span id="cancelInvoiceCurrentAmount"></span>
				</td>
			</tr>
			<tr>
				<td valign="top">
					Notes:
				</td>
				<td>
					<s:textarea id="cancelInvoiceNote"
						cssStyle="padding:0px 1px;overflow:auto ;width:260px;height:60px;"
						cssClass="validate-text"></s:textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					Your notes have to be more than 10 characters.
				</td>
			</tr>
		</table>
	</div>
</div>
<div class="yui-pe-content" id="closeInvoicePanel">
	<div class="hd">
		<h2>
			Close Invoice
		</h2>
	</div>
	<div class="bd">
		<table height="100%" width="95%" align="center" border="0">
			<tr height="15px">
				<td>
					Invoice Number:
				</td>
				<td>
					<s:hidden id="closeInvoiceId" />
					<span id="closeInvoiceNumber"></span>
				</td>
			</tr>
			<tr>
				<td>
					Vendor
				</td>
				<td>
					<span id="closeVendor"></span>
				</td>
			</tr>
			<tr>
				<td>
					Ban
				</td>
				<td>
					<span id="closeBan"></span>
				</td>
			</tr>
			<tr>
				<td>
					Invoice Status
				</td>
				<td>
					<span id="closeInvoiceStatus"></span>
				</td>
			</tr>
			<tr>
				<td>
					Invoice Current Amount
				</td>
				<td>
					<span id="closeInvoiceCurrentAmount"></span>
				</td>
			</tr>
			<tr>
				<td valign="top">
					Notes:
				</td>
				<td>
					<s:textarea id="closeNote" name="searchInvoiceVO.note"
						cssStyle="padding:0px 1px;overflow:auto ;width:260px;height:60px;"
						cssClass="validate-text"></s:textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					Your notes have to be more than 10 characters.
				</td>
			</tr>
		</table>
	</div>
</div>
<div class="yui-pe-content" id="choosePanel">
	<div class="hd">
		<h2>
			<span id="chooseTitle"></span>
		</h2>
	</div>
	<div class="bd">
		<table height="100%" width="95%" align="center" border="0">
			<tr height="15px">
				<td>
					<input type="button" value="Close Payment with Remittance"
						style="cursor: pointer;"
						onclick="YAHOO.ccm.container.choosePanel.hide();YAHOO.ccm.container.throughAPPaymentPanel.show()">
				</td>
				<td>
					<input type="button" value="Close Payment without Remittance"
						style="cursor: pointer;"
						onclick="YAHOO.ccm.container.choosePanel.hide();YAHOO.ccm.container.tRegularCloseInvoicePanel.show()">
				</td>
			</tr>
		</table>
	</div>
</div>
<div class="yui-pe-content" id="throughAPPaymentPanel">
	<div class="hd">
		<h2>
			Close Payment with Remittance
		</h2>
	</div>
	<div class="bd">
		<table height="100%" width="95%" align="center" border="0">
			<tr height="15px">
				<td>
					Invoice Number:
				</td>
				<td>
					<s:hidden id="throughAPPaymentCloseInvoiceId" />
					<span id="throughAPPaymentCloseInvoiceNumber"></span>
				</td>
			</tr>
			<tr>
				<td>
					Vendor
				</td>
				<td>
					<span id="throughAPPaymentCloseVendor"></span>
				</td>
			</tr>
			<tr>
				<td>
					Ban
				</td>
				<td>
					<span id="throughAPPaymentCloseBan"></span>
				</td>
			</tr>
			<tr>
				<td>
					Invoice Status
				</td>
				<td>
					<span id="throughAPPaymentCloseInvoiceStatus"></span>
				</td>
			</tr>
			<tr>
				<td>
					Invoice Current Amount
				</td>
				<td>
					<span id="throughAPPaymentCloseInvoiceCurrentAmount"></span>
				</td>
			</tr>
			<tr>
				<td>
					Remittance(etc.Check No.)
				</td>
				<td>
					<s:textfield id="remittance" />
					(Optional)
				</td>
			</tr>
			<tr>
				<td>
					Paid Date
				</td>
				<td nowrap="nowrap">
					<s:textfield id="paidDate"></s:textfield>
					(Optional)
					<a
						onClick="g_Calendar.show(event,'paidDate',false, 'yyyy-mm-dd', new Date(1990,1,1)); return false;"
						href="javascript:%20void(0);"><img
							src="include/images/cal.gif" id="ticketDueStartsOnImg" border="0"
							align="middle"> </a> yyyy/MM/dd
				</td>
			</tr>
			<tr>
				<td valign="top">
					Notes:
				</td>
				<td>
					<s:textarea id="throughAPPaymentCloseNote"
						name="searchInvoiceVO.note"
						cssStyle="padding:0px 1px;overflow:auto ;width:260px;height:60px;"
						cssClass="validate-text"></s:textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					Your notes have to be more than 10 characters.
				</td>
			</tr>
		</table>
	</div>
</div>
<!-- 
<div class="yui-pe-content" id="tRegularCloseInvoicePanel">
	<div class="hd">
		<h2>Close Payment without Remittance</h2>
	</div>
	<div class="bd">
			<table height="100%" width="95%" align="center" border="0">
				<tr height="15px">
					<td>
						Invoice Number:
					</td>
					<td>
					    <s:hidden id="tRegularCloseInvoiceCloseInvoiceId"/>
						<span id="tRegularCloseInvoiceCloseInvoiceNumber"></span>
					</td>
				</tr>
				<tr>
					<td>
						Vendor
					</td>
					<td>
						<span id="tRegularCloseInvoiceCloseVendor"></span>
					</td>
				</tr>
				<tr>
					<td>
						Ban
					</td>
					<td>
					    <span id="tRegularCloseInvoiceCloseBan"></span>
					</td>
				</tr>
				<tr>
					<td>
						Invoice Status
					</td>
					<td>
					    <span id="tRegularCloseInvoiceCloseInvoiceStatus"></span>
					</td>
				</tr>
				<tr>
					<td>
						Invoice Current Amount
					</td>
					<td>
					    <span id="tRegularCloseInvoiceCloseInvoiceCurrentAmount"></span>
					</td>
				</tr>
				<tr>
					<td valign="top" >
						Notes:
					</td>
					<td>
						<s:textarea  id="tRegularCloseInvoiceCloseNote" name="searchInvoiceVO.note" cssStyle="padding:0px 1px;overflow:auto ;width:260px;height:60px;" cssClass="validate-text"></s:textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2">Your notes have to be more than 10 characters.</td>
				</tr>
			</table>
	</div>
</div>
-->
<iframe id="__downloadIframe" style="display: none;"></iframe>
<iframe name="uploadFrame_lc001" id="uploadFrame_lc001"
	style="display: none" src="javascript:false"></iframe>
