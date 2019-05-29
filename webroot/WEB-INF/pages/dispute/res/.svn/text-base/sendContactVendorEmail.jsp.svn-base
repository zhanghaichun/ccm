
<html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <head>
 	<meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="include/javascript/flexbox/css/disputeDetail.css">    
    <link rel="stylesheet" href="include/css/hint.min.css">
    <script src="include/javascript/jquery/jquery-1.4.2.js"></script>
 </head>
 <body > 
<div id="pageContainer" style=" max-height:580px;">
 <form  action="contactVendorDispute.action" method="post" id = "sendContactVendorEmailId" enctype="multipart/form-data" target="uploadFrame_0">
			<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td class="disputeTab" width="10%">
					From:
				</td>
				<td class="disputeTab">
					<input type="text" name="disputeMessage.From" value = "${disputeContactVendorEmail.emailFrom}" id = "_contactVendor_from" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td class="disputeTab" width="10%">
					To:
				</td>
				<td class="disputeTab">
					<input type="text" name="disputeMessage.To" value = "${disputeContactVendorEmail.defaultEmailTo}" id = "_contactVendor_to"/>
				</td>
			</tr>
			<tr>
				<td class="disputeTab" width="10%">
					Cc:
				</td>
				<td class="disputeTab">
					<input  type="text" name="disputeMessage.CC" id = "_contactVendor_cc"/>
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
						<textarea id="_contactVendor_textMessage" style="border:0px;resize: none;" name="disputeMessage.message" height=80px  border="0" autofocus="true" rows="5">${disputeContactVendorEmail.defaultEmailMessage}</textarea>
						</div>
					</td>
			</tr>	
			
			</table>
			<div style="padding-top:10px"">
			<table  border="0" width="100%" cellpadding="0" cellspacing="0">
			  <tr>
			  <td width="10%">
			  </td>
			  <td>
			  <div id="originalWide">
						<div class="original"></div>
						<div class="originalTittle">the original message</div>
						<div class="line"></div>
				  </div>
			  </td>
			      
			</tr>
			</table>
            </div>
			<div style=" max-height:200px; overflow-y:scroll; padding-top:10px"">
			<table class="disputeTab3" border="0" width="100%" cellpadding="0" cellspacing="0">
			
				<c:if test="${(disputeContactVendorEmail.disputeMsgList)!= null && fn:length(disputeContactVendorEmail.disputeMsgList) > 0}">
                     <c:forEach var="item" items="${disputeContactVendorEmail.disputeMsgList}" varStatus="status">
                      <tr>
						<td width="10%">
						</td>
						<td width="90%"">
								<div>
									
									<div class="originalEmail">
										<table class="disputeTab3"  width="100%" cellpadding="0" cellspacing="0">
											<tr>
												<td width="5%">
													<div class="originalFrom" style="color:#999">From</div>
												</td>
												<td >
												<div class="originalFromInfo" style="color:#999">
													<div class="originalFromSender">${item.from_address}</div>
													<div class="originalFromData">${item.sent_datetime}</div>
												</div>
												</td>
											</tr>
											<tr>
												<td width="5%">
													<div class="originalTo" style="color:#999">To</div>
												</td>
												<td width="95%"">
													<div class="originalToReceive" style="color:#999">${item.to_address}</div>
												</td>
											</tr>
											<tr>
												<td width="5%">
													<div class="originalCC" style="color:#999">Cc</div>
												</td>
												<td width="95%"">
													<div class="originalCcReceive" style="color:#999">${item.cc_address}</div>
												</td>
											</tr>
											<tr>
												<td width="5%">
												</td>
												<td width="95%"">
													<div class="originalCentent" style="color:#999">${item.content}</div>
												</td>
											</tr>
											<tr>
											    <td width="5%">
												</td>
												<td>
												<div class="line"></div>
												</td>
											</tr>
										</table>
									</div>
								</div>
						</td>
					</tr>
					</c:forEach>
				</c:if>
		
			</table >
			</div>
			<table class="disputeCheckbox" border="0" width="100%" cellpadding="0" cellspacing="0" height="120px";>	
			    <tr>
					<td width="50%">
					    <div style="float:left;padding-right: 15px; display:none;" id = "attachmentTitleId">Attachments:</div>
						<div id = "addDisputeEmailFile" style="overflow-y:auto;overflow-x: hidden;max-height:70px;vertical-align: top;position:relative;">
		        	    </div>
					</td>
					<td>
					    
	        		</td>
		        </tr>		
                <tr>
                <td>
						<div  id="attachDiv" style="float:left;width:80px; position:relative;">
								<input style="text-align:center;width:70px;height: 19px;position: absolute;top: 0;
											  left: 0;z-index: 100;opacity: 0;cursor: pointer;" type="file" 
										name="uploads" multiple="multiple" onchange="filesProcess(this.files)" />
								<div class="attachBorder butata">Attach</div>
				        </div>
				<div style="float:left; padding-left:20px">
				<input type="button" value="Send to Vendor" onclick="sendtoVendor()">
				<input name="disputeMessage.disputeId" type="hidden"  id="DisputeSendtoVendorByDisputeIdId"/>
				<input name="disputeMessage.disputeNumber" type="hidden"  id="DisputeSendtoVendorByDisputeSubject"/>
				</div>
				</td>			
				</tr>
			</table>
		</form>

 </div>
  
</body>
</html>