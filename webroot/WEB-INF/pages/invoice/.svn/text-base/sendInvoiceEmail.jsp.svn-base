<html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <head>
 	<meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="include/javascript/flexbox/css/disputeDetail.css">    
    <link rel="stylesheet" type="text/css" href="include/css/dispute/disputeDetails.css">    
    <link rel="stylesheet" type="text/css" href="include/css/dispute/disputeForward.css">   
    <link rel="stylesheet" href="include/css/hint.min.css">
    <script src="include/javascript/jquery/jquery-1.4.2.js"></script>
    <script type="text/javascript" src="include/javascript/ccm/sendInvoiceEmail.js" language="javascript"></script>
    <script type="text/javascript" src="include/javascript/flexbox/js/jquery.flexbox.js"></script>
	<script type="text/javascript" src="include/javascript/jquery/jquery.ui.core.js"></script>
	<script type="text/javascript" src="include/javascript/jquery/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="include/javascript/jquery/jquery.ui.accordion.js"></script>
    
 </head>
 <style>
    .disputeTab {
        padding-bottom: 5px;
        border-bottom:1px solid #e6e6e6;
        padding-top:20px;
        margin-bottom:10px;
    }
</style>
 <body > 
 
<form id="downloadForm" action="download.action" method="post" target="__downloadIframe"
	style="display: none;">
	<input type="hidden" name="filePath" />
	<input type="hidden" name="fileName" />
</form>
<iframe id="__downloadIframe" style="display: none;"></iframe>

<div id="pageContainer" style=" max-height:580px;">
 <form  action="sendInvoiceEmail.action" method="post" id = "sendInvoiceEmail" enctype="multipart/form-data" target="uploadFrame_0">
			<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td class="disputeTab" width="10%">
					From:
				</td>
				<td class="disputeTab">
					<input type="text" name="invoiceMessage.From"  value = "${dataResult.emailFrom}" id = "_contactVendor_from" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td class="disputeTab" width="10%">
					To:
				</td>
				<td class="disputeTab">
					<input type="hidden" name="invoiceMessage.workflowUserName" id = "_contactVendor_workflowUserName" value = "${dataResult.workflowUserName}"/>
					<input type="text" name="invoiceMessage.To" id = "_contactVendor_to" value = "${dataResult.emailTo}"/>
				</td>
			</tr>
			<tr>
				<td class="disputeTab" width="10%">
					Cc:
				</td>
				<td class="disputeTab">
					<input  type="text" name="invoiceMessage.Cc" />
				</td>
			</tr>
			
			<tr>
				<td class="disputeTab" width="10%">
					Subject:
				</td>
				<td class="disputeTab">
					<input  type="text" name="invoiceMessage.Subject"  value= "${dataResult.emailSubject}" />
				</td>
			</tr>
			
			</table>
			<table class="disputeTab2" border="0" width="100%" cellpadding="0" cellspacing="0">
				<tr >
					<td width="10%" valign="top" style = "padding-top: 10px;">
						Message:
					</td>
					<td style = "padding-top: 10px;">
						<div >
						<textarea id="_contactVendor_textMessage" style="border:0px;resize: none;" name="invoiceMessage.message" height=80px  border="0" autofocus="true" rows="12" >${dataResult.emailMessage}</textarea>
						</div>
					</td>
			</tr>	
			
			</table>
			<div style="padding-top:10px">
            </div>
            
            <div style="margin:10px 0px 10px 0px; width: 100%;">
              <div style="white-space: nowrap; display: flex; float: left;margin-right: 15px;"> 
	            <input style="margin-top: 3px;margin-right: 5px;" type="checkbox" name="invoiceSummaryUploads" checked="true" value="${dataResult.invoiceSummary.file_path}"/>
				<label onclick="Download('${dataResult.invoiceSummary.file_name}','${dataResult.invoiceSummary.file_path}');" style="cursor:hand">
					<u>${dataResult.invoiceSummary.file_name}</u>
				</label>
			 </div>	
			  <c:forEach items="${dataResult.originalList}"  var="item" varStatus="i">
			    <div style="white-space: nowrap; display: flex; float: left;margin-right: 15px;"> 
					<input style="margin-top: 3px;margin-right: 5px; " id="${item.i}" type="checkbox" name="invoiceSummaryUploads" checked="true" value="${item.file_path}"/>
					<label for="${item.i}" onclick="Download('${item.file_name}','${item.file_path}');" style="cursor:hand">
						<u>${item.file_name}</u>
					</label>
				</div>	
              </c:forEach> 
            </div>
            
            <div id="addSendInvoiceEmailFile" style="margin:10px 0px 0px 0px; overflow-y:auto;overflow-x: hidden;max-height:70px;vertical-align: top;position:relative; float: left; clear: both;">		
			 </div>
			<table class="disputeCheckbox" border="0" width="100%" cellpadding="0" cellspacing="0" height="60px";>	
                <tr>     
                <td>
                <div id="attachDiv1" style="float:left;width:80px; position:relative; margin-right:20px">
								<input style="text-align:center;width:70px;height: 19px;position: absolute;top: 0;
											  left: 0;z-index: 100;opacity: 0;cursor: pointer;" type="file" name="uploads" multiple="multiple" onchange="filesSendInvoiceProcess(this.files)">
				                <div class="attachBorder butata">Attach</div>
				 </div>
				<div class="attachBorder butata" onclick="sendtoInvoiceEmail()" style="width: 100; text-indent: 10px">Send Email</div>
				<input name="invoiceId" type="hidden"  id="invoiceId" value="${invoiceId}"/>
				</td>			
				</tr>
			</table>
		</form>

 </div>
  
</body>
</html>