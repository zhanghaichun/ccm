<!-- searchCreditMainPanel By xinyu.Liu -->
<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ page import="com.saninco.ccm.util.SystemUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<link rel="stylesheet" type="text/css" href="include/javascript/Calendar/styles/calendar.css">
<script type="text/javascript" src="include/javascript/ccm/common.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/Calendar/simplecalendar.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/ccm/viewTicketDetail.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/ccm/quicklink.js" language="javascript"></script>

<div id="pageContainer" class="tabForm">
	<h3>
	<s:if test='ticketId!=null'>
		Ticket Number <s:label name="ticketId"></s:label>
	</s:if>
	<s:else>
		New Ticket
	</s:else>
	</h3>
	<div style="width:100%;border-top: 0px none; margin-bottom: 4px" >
		<div id="ctl00_MainContent_SearchCreditControl1_UpdatePanel1">
						<table class="tabDetailView" border="0" width=100% cellpadding="5"
							cellspacing="0">
							<tr valign="top">
								<td class="tabDetailViewDL" width="50%">
									<div class="searchItemPanel">
										<table width="100%" cellspacing="0" cellpadding="0" border="0">
											<tr>
												<td valign="top" width="20%">
													Title
												</td>
												<td>
													<s:textarea  id="__title" cssStyle="padding:2px 1px;overflow:auto ;width:400px;height:50px;"  name="ticket.title" cssClass="validate-text"></s:textarea>
												</td>
											</tr>
										</table>
									</div>
								</td>
								<td class="tabDetailViewDL" width="50%">
									<div class="searchItemPanel">
										<table width="100%" height="65" cellspacing="0" cellpadding="0" border="0">
											<tr>
												<td width="35%">
													Ticket Priority
												</td>
												<td><span class="select1">
													<s:select id="pid" name="ticket.priority.id" list="priorityList" listKey="key"
														listValue="value" required="true" cssStyle="width:127px"/></span>
												</td>
											</tr>
											<tr>
												<td>
													Ticket Severity
												</td>
												<td><span class="select1">
													<s:select id="sid" name="ticket.severity.id" list="severityList" listKey="key"
														listValue="value" required="true" cssStyle="width:127px"/></span>
												</td>
											</tr>
											<tr>
												<td>
													Ticket Status
												</td>
												<td><span class="select1">
													<s:select id="tsid" name="ticket.ticketStatus.id" list="ticketStatusList" listKey="key"
														listValue="value" required="true" cssStyle="width:127px"/></span>
												</td>
											</tr>
										</table>
									</div>
								</td>
								
							</tr>
							<tr valign="top">
								<td class="tabDetailViewDL">
									<div class="searchItemPanel">
										<table width="100%" cellspacing="0" cellpadding="0" border="0">
											<tr>
												<td valign="top" width="20%">
													Content
												</td>
												<td>
													<s:textarea  id="__content" cssStyle="padding:2px 1px;width:400px;height:150px;overflow:auto ;"  name="ticket.content" cssClass="validate-text"></s:textarea>
												</td>
											</tr>
										</table>
									</div>
								</td>
								<td class="tabDetailViewDL">
									<div class="searchItemPanel">
										<table width="100%" height="40" cellpadding="0" cellspacing="0" border="0">
										<s:if test='ticketId!=null'>
											<tr>
												<td width="35%">
													Created Time
												</td>
												<td>
													<s:date name="ticket.createdTimestamp" format="yyyy-MM-dd HH:mm:ss"/>
												</td>
											</tr>
											<tr>
												<td>
													Created By
												</td>
												<td>
													<s:text name="user.firstName" />&nbsp;<s:text name="user.lastName" />
												</td>
											</tr>
										</s:if>
										</table>
									</div>
								</td>
							</tr>
							<tr>
								<td class="tabDetailViewDL">
									<div class="searchItemPanel">
										<table width="100%" cellpadding="0" cellspacing="0" border="0">
											<tr>
												<td valign="top" width="20%">
													Comment
												</td>
												<td>
													<s:textarea id="__comment" cssStyle="padding:2px 1px;width:400px;height:150px;overflow:auto;" name="" cssClass="validate-text"></s:textarea>
												</td>
											</tr>
										</table>
									</div>
								</td>
								<td class="tabDetailViewDL">
									<div class="searchItemPanel">
										<table width="100%" height="40" cellpadding="0" cellspacing="0" border="0">
											<tr>
												<td width="60px">
													Attach File:
												</td>
												<td>
													<form id="doAddTicketHistoryFrom" style="position:relative;"
														action="doAddTicketHistory.action" enctype="multipart/form-data"
														method="POST" target="uploadFrame_0" >
															
															<input style="float:left;height:19px;width:50%" type='text' id="__up_load_text" disabled="disabled">
        													<span class="ccm_upload_img" style="float:left;margin:0 0 0 4px;">
	        													<input id="__up_load" onchange="document.getElementById('__up_load_text').value=this.value;" class="ccm_file" type="file" style="height:20px" name="uploads" size="1" />
	        													<input style="height:20px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
        													</span>
		
															<input id="__attachment_point_id" name="searchTicketVO.attachmentPointId" type="hidden" />
															<span style="width:65px;float:left;padding-left:3px;" ><input style="height:20px;padding-right:3px;" type="button" value="Upload" onclick="if(verificationNull())document.getElementById('doAddTicketHistoryFrom').submit();" /></span>
													</form>
												</td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
				</div>
		</div>
		<div style="width:100%;">
			<div style="float:left;"><br />
				<input type="button" id="save" value="Save" onclick="verificationText();" />
				&nbsp;&nbsp;&nbsp;
				<s:if test='ticketId!=null'>
				<input type="button" value="Reset"
					onclick="dataReset();" />
				</s:if>
				<s:else>
					<input type="button" value="Clear"
					onclick="dataClear();" />
				</s:else><br />
				&nbsp;&nbsp;&nbsp;</div>
				<div id="loadingImgDiv" style="display:none; float: right;"> <img src="include/images/loading.gif" /></div>
		</div>
			<table border="0" style="table-layout:fixed;width:100%;">
					<tr>
						<td>
							<div align="left"  style="width:100%;overflow-x:auto;padding-bottom: 20px;">
								<div id="_dataTable"></div>
							</div>
						</td>
					</tr>
				</table>
		<div id="_dataTable_page" ></div>
</div>

<div class="save-ticket" id="saveTicket">
	<div class="hd">
		Please confirm
	</div>
	<div class="bd">
		<table cellspacing=8 cellpadding=0 border=0 width=100%>
			<tr>
				<td>
					Please confirm ?
				</td>
			</tr>
		</table>
	</div>
</div>

<iframe name="uploadFrame_0" id="uploadFrame_0" style="display: none" src="javascript:false"></iframe>

<SCRIPT type="text/javascript"> 
	var ticketId = ${param.ticketId == null ? "null":param.ticketId};
</SCRIPT>
