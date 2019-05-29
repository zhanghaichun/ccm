<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" type="text/css"
	href="include/javascript/flexbox/css/jquery.flexbox.css"></link>
<script type="text/javascript"
	src="include/javascript/flexbox/js/jquery.flexbox.js"></script>
<link rel="stylesheet" type="text/css"
	href="include/javascript/Calendar/styles/calendar.css">
<script type="text/javascript"
	src="include/javascript/Calendar/simplecalendar.js"></script>
<script type="text/javascript"
	src="include/javascript/yui2/tabview-min.js"></script>
<script type="text/javascript"
	src="include/javascript/jquery/jquery.ui.core.js"></script>
<script type="text/javascript"
	src="include/javascript/jquery/jquery.ui.widget.js"></script>
<script type="text/javascript"
	src="include/javascript/jquery/jquery.ui.accordion.js"></script>
<SCRIPT src="include/javascript/ccm/common/simpleValidation.js"
	type=text/javascript></SCRIPT>
<script type="text/javascript"
	src="include/javascript/ccm/operations.js"></script>
<script type="text/javascript" src="include/javascript/ccm/disputeEmailManagement.js"></script>
<script type="text/javascript" src="include/javascript/ccm/apManagement.js"></script>
<SCRIPT type="text/javascript" src="include/javascript/ccm/common/common.js"></SCRIPT>
	
<script type="text/javascript">
var VLO_Array = {
	"results": [
		{
		    "id": "",
		    "name": "All"
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
$(function(){
	form0_searchOperationsVO_vendorId = $('#VL_vendorList_operations').flexbox(VLO_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 260,
		name : "vendorId",
		onSelect : function(){
			getBanListByVendorId(0);
		}
	});
	form0_searchOperationsVO_vendorId.setValue("all");
});

</script>

<div id="pageContainer" class="tabForm">
	<form id="downloadForm" action="download.action" method="post" style="display: none;" target="__downloadIframe">
		<input type="hidden" name="filePath" />
		<input type="hidden" name="fileName" />
	</form>
	<h3>
		Operations
	</h3>

	<div id="demoTabs1" class="yui-navset">
		<ul class="yui-nav">
			<li>
				<a href="#tab_1"><em>Operations</em> </a>
			</li>
			<li>
				<a href="#tab_2" onclick="searchPreSendEmail();"><em>Email</em></a>
			</li>			
			<li>
				<a href="#tab_3"><em>AP Operations</em></a>
			</li>
		</ul>
		
		
		
		<div class="yui-content">
			<div id="tab_1">
				<table cellspacing="0" cellpadding="0" border="0"
					style="border-top: 0px none; margin-bottom: 4px">
					<tr>
						<td width="120px;">
							<input type="button" value="Register Invoice"
								onclick="YAHOO.ccm.container.registerInvoiceDialog.show();document.getElementById('registerInvoiceDialogSuccess').style.display='none';" />
						</td>
						<td width="100px;">
							<input type="button" value="Invoice Entry"
								onclick="showEntryWindow();" />
						</td>
						<td width="100px;">
							<input type="button" value="Invoice Clear"
								onclick="showClearWindow('findData()',true);" />
						</td>
					</tr>
					<tr>
						<td>
							<span id="registerInvoiceDialogSuccess" style="display: none;">
								<font color="green"> &nbsp;Add Success! </font> </span>
						</td>
					</tr>
				</table>
			</div>
			<div id="tab_2">
				<h3>
					Dispute Email Management
				</h3>
				<table border="0" style="table-layout: fixed; width: 100%;">
					<tr>
						<td>
							<div align="left" style="width: 100%; overflow-x: auto; padding-bottom: 20px;">
								<div id="_preEmailPage"></div>
							</div>
						</td>
					</tr>
				</table>
				<input type="button" onclick="sendSelectedEmail();" value="Send"/>
			</div>
		    <div id = tab_3>
				<input type="button" onclick="sendAPSendingRequest();" value="AP Send"/>
				<input type="button" onclick="sendAPLoadingRequest();" value="Load Remittance"/>
		    </div>
		</div>
	</div>


	<div id="EditTheDisputeEmail">
		<div class="hd">
			<h2>
				Edit The Email
			</h2>
		</div>
		<div class="bd">
			<form id="form100" action="updateEmailByEmail.action" method="POST" enctype="multipart/form-data" target="uploadFrame_0">
				<table width=97% align=center cellpadding="0" cellspacing="0" height="212px" border=0>
					<tr>
						<td width="13%">
							To Address:
						</td>
						<td width="32%">
							<s:textfield name="email.toAddress" style="width:200px;"
								cssClass="validate-date-ca"></s:textfield>
						</td>
						<td width="15%" valign=top align=right rowspan=8>
							Content:
						</td>
						<td width="40%" align=right valign="top" rowspan=8>
							<s:textarea name="email.content" cssClass="validate-double"
								cssStyle="width:340px;overflow:auto" rows="12"></s:textarea>
						</td>
					</tr>
					<tr>
						<td>
							CC Address:
						</td>
						<td>
							<s:textfield name="email.ccAddress" cssClass="validate-double"
								cssStyle="width:200px"></s:textfield>
						</td>
					</tr>
					<tr>
						
						<td>
							BCC Address:
						</td>
						<td>
							<s:textfield name="email.bccAddress" cssClass="validate-double"
								cssStyle="width:200px"></s:textfield>
						</td>
					</tr>
					<tr>
						<td valign=top>
							Subject:
						</td>
						<td>
							<s:hidden name="email.id" value=""></s:hidden>
							<s:hidden name="email.recActiveFlag" value=""></s:hidden>
							<s:textarea name="email.subject" cssClass="validate-alp"
								cssStyle="width:200px;overflow:auto" rows="3"></s:textarea>
						</td>
					</tr>
					<tr style="display:none;">
						<td>
							Dispute Id:
						</td>
						<td>
							<s:hidden name="email.referenceId" cssClass="validate-double"
								cssStyle="width:200px" disabled="true"></s:hidden>
						</td>
					</tr>
					<tr>
						<td>
							Notes:
						</td>
						<td>
							<s:textfield name="email.notes" cssClass="validate-double"
								cssStyle="width:200px"></s:textfield>
						</td>
					</tr>
					<tr style="display:none;">
						<td>
							Attachment Point Id:
						</td>
						<td>
							<s:hidden name="email.attachmentPoint.id"
								cssClass="validate-double" cssStyle="width:200px" disabled="true"></s:hidden>
						</td>
					</tr>
					<tr style="display:none;">
						<td>
							Created By:
						</td>
						<td>
							<s:hidden name="email.createdBy" cssClass="validate-double"
								cssStyle="width:200px"></s:hidden>
						</td>
					</tr>
					<tr>
						<td>
							Email Status:
						</td>
						<td style="color: red;">
							<s:textfield name="email.emailStatus" cssClass="validate-double"
								cssStyle="width:200px" disabled="true"></s:textfield>
						</td>
					</tr>
					<tr>
						<td>
							Send Time:
						</td>
						<td style="color: red;">
							<s:textfield name="email.sendDateTime"
								cssClass="validate-double" cssStyle="width:200px"></s:textfield>
						</td>
					</tr>
					<tr style="display:none;">
						<td>
							Created Time:
						</td>
						<td style="color: red;">
							<s:hidden name="email.createdTimestamp"
								cssClass="validate-double" cssStyle="width:200px"></s:hidden>
						</td>
					</tr>
					<tr style="display:none;">
						<td>
							Modified Time:
						</td>
						<td style="color: red;">
							<s:hidden name="email.modifiedTimestamp"
								cssClass="validate-double" cssStyle="width:200px"></s:hidden>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
						<!-- 
						document.getElementById('form100').submit();YAHOO.ccm.container.EditTheDisputeEmail.hide();
						 -->
							<input type="button" value="Save" onclick="EditTheDisputeEmailCommit();"/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="Cancel" onclick="YAHOO.ccm.container.EditTheDisputeEmail.hide();"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>

	<div class="yui-pe-content" id="email_attachfilelist_window">
		<div class="hd">
			<h2>
				AttachFiles Of Email
			</h2>
		</div>
		<div id="_attachmentFileOfEmail"></div>
	</div>

	<div class="yui-pe-content" id="invoice_clear_window">
		<div class="hd">
			<h2>
				Invoice Clear
			</h2>
		</div>
		<div class="bd">
			<form id="form2">
				<table width=95% align=center border=0>
					<tr>
						<td style="color: #000;">
							Bar Code:
						</td>
						<td>
							<s:textfield id="__bar_code_clear" cssClass="validate-alp"
								cssStyle="width:150px"></s:textfield>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<div id="__show_invoice_st_data"></div>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="color: red;">
							<div id="__show_invoice_st_data_prompt"></div>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>

	<div class="yui-pe-content" id="invoice_entry_window">
		<div class="hd">
			<h2>
				Invoice Entry
			</h2>
		</div>
		<div class="bd">
			<form id="form1">
				<table width=95% align=center border=0>
					<tr>
						<td style="color: #000;">
							Bar Code:
						</td>
						<td>
							<s:textfield id="__bar_code" cssClass="validate-alp"
								cssStyle="width:150px"></s:textfield>
						</td>
					</tr>
					<tr>
						<td style="color: #000;">
							Invoice Due Date:
						</td>
						<td>
							<s:textfield id="__invoice_due_date" style="width:150px;"
								cssClass="validate-date-ca"></s:textfield>
							<a
								onClick="g_Calendar.show(event,'__invoice_due_date',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;"
								href="javascript:%20void(0);"><img
									src="include/images/cal.gif" id="" border="0" align="middle">
							</a> yyyy/MM/dd
						</td>
					</tr>
					<tr>
						<td style="color: #000;">
							Invoice Previous Payment:
						</td>
						<td>
							<s:textfield id="__invoice_previous_payment"
								cssClass="validate-double" cssStyle="width:150px"></s:textfield>
						</td>
					</tr>
					<tr>
						<td style="color: #000;">
							Invoice Balance Forward:
						</td>
						<td>
							<s:textfield id="__invoice_balance_forward"
								cssClass="validate-double" cssStyle="width:150px"></s:textfield>
						</td>
					</tr>
					<tr>
						<td style="color: #000;">
							Invoice Current Due:
						</td>
						<td>
							<s:textfield id="__invoice_current_due"
								cssClass="validate-double" cssStyle="width:150px"></s:textfield>
						</td>
					</tr>
					<tr>
						<td style="color: #000;">
							Invoice Total Due:
						</td>
						<td>
							<s:textfield id="__invoice_total_due" cssClass="validate-double"
								cssStyle="width:150px"></s:textfield>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="color: red;">
							<div id="__show_bar_code"></div>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>

	<div class="yui-pe-content" id="register_invoice_dialog">
		<div class="hd">
			<h2>
				Please type in
			</h2>
		</div>
		<div class="bd">
			<form id="form0">
				<table width=95% align=center border=0>
					<tr>
						<td style="color: #000;">
							Vendor:
						</td>
						<td>
							<div id="VL_vendorList_operations"></div>
						</td>
					</tr>
					<tr>
						<td style="color: #000;">
							Ban:
						</td>
						<td>
							<span class="select1"><s:select id="form0_banId" name="banId" list="banList"
								headerKey="" headerValue="All" listKey="key" listValue="value"
								cssStyle="width: 200px" /></span>
						</td>
					</tr>
					<tr>
						<td style="color: #000;">
							Barcode:
						</td>
						<td>
							<input type="text" name="barcode"
								class="required validate-digits" style="width: 200px" />
						</td>
					</tr>
					<tr>
						<td style="color: #000;">
							Invoice Date:
						</td>
						<td>
							<input id="form0_Date_1" type="text" name="invoiceDate"
								class="required validate-date-ca" style="width: 200px"
								onClick="g_Calendar.show(event,'form0_Date_1',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;" />
							<a
								onClick="g_Calendar.show(event,'form0_Date_1',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;"
								href="javascript:%20void(0);"> <img
									src="include/images/cal.gif" border="0" align="middle"> </a>
						</td>
					</tr>
				</table>
			</form>
		</div>

	</div>
	
	<iframe id="__downloadIframe" style="display: none;"></iframe>
	<iframe name="uploadFrame_0" id="uploadFrame_0" style="display: none" src="javascript:false"></iframe>