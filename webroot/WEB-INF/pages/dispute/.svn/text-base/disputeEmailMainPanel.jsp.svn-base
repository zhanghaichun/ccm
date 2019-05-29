<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<link rel="stylesheet" type="text/css" href="include/javascript/flexbox/css/disputeDetail.css">
<link rel="stylesheet" type="text/css" href="include/css/dispute/disputeForward.css">
<SCRIPT type="text/javascript"
	src="include/javascript/ccm/common/common.js"></SCRIPT>
<script src="include/javascript/saninco/downLoad.js" type="text/javascript"></script>
<script src="include/javascript/ccm/disputeTab.js" type="text/javascript"></script>
<script src="include/javascript/ccm/disputeEmailForward.js" type="text/javascript"></script>
<script src="include/javascript/ccm/common/workpace.js" type="text/javascript"></script>
<div id="pageContainer">
<form id="downloadForm" action="download.action" method="post" target="__downloadIframe"
	style="display: none;">
	<input type="hidden" name="filePath" />
	<input type="hidden" name="fileName" />
</form>
<iframe id="__downloadIframe" style="display: none;"></iframe>
<c:forEach var="item" items="${disputeList}" varStatus="status">
<div class="tabForm" id = "tabForm_${status.index}" style = "display:block;">

	<div class="wide">
		<form id="dispute_form_${status.index}" action="forwordDispute.action" method="post" target="rfFrame_${status.index}">
		
			<table class="disputeTab"  width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td class="disputeNumberLab" width="10%">
					Dispute Number:
				</td>
				<td>
					<input type="text" name="disputeMessage.disputeNumber" value="${item.dispute.disputeNumber}" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td class="forwardToLab" width="10%">
					Forward to:
				</td>
				<td>
					<input id="forwardTo_${status.index}" class="forward-to" type="text" name="disputeMessage.forwardTo" value="${item.defaultEmailTo}" placeholder="Write a new contact here" />
				</td>
			</tr>
			<tr>
				<td class="replyToLab" width="10%">
					Reply to:
				</td>
				<td>
					<input id="replyTo_${status.index}" class="reply-to" type="text" name="disputeMessage.replyTo" value="${item.user.email}" placeholder="Write a new contact here"/>
				</td>
			</tr>
			
			</table>
			<table class="disputeTab2" border="0" width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10%" class="message-label">
						Message:
					</td>
					<td>
						<div >
						<textarea id="textMessage" name="disputeMessage.message" height=80px  autofocus="true" rows="5">${item.defaultEmailMessage}</textarea>
						</div>
					</td>
				</tr>
				<c:if test="${(item.disputeMsgList)!= null && fn:length(item.disputeMsgList) > 0}">
					<tr>
						<td width="10%">
						</td>
						<td width="90%">
								<div>
									<div id="originalWide">
										<div class="original"></div>
										<div class="originalTittle">the original message</div>
										<div class="line"></div>
									</div>
									<div class="originalEmail">
										<table class="disputeTab3"  width="100%" cellpadding="0" cellspacing="0">
											<tr class="from sender">
												<td width="5%">
													<div class="originalFrom">From</div>
												</td>
												<td >
												<div class="originalFromInfo">
													<div class="originalFromSender">${item.disputeMsgList[0].from_address}</div>
													<div class="originalFromData">${item.disputeMsgList[0].sent_datetime}</div>
												</div>
												</td>
											</tr>
											<tr class="to receiver">
												<td width="5%">
													<div class="originalTo">To</div>
												</td>
												<td width="95%">
													<div class="originalToReceive">${item.disputeMsgList[0].to_address}</div>
												</td>
											</tr>
											<tr class="cc receiver">
												<td width="5%">
													<div class="originalTo">Cc</div>
												</td>
												<td width="95%">
													<div class="originalToReceive">${item.disputeMsgList[0].cc_address}</div>
												</td>
											</tr>
											<tr>
												<td width="5%">
												</td>
												<td width="95%">
													<div class="originalCentent">${item.disputeMsgList[0].content}</div>
												</td>
											</tr>
											<tr>
											<td width="5%">
											</td>
											<td id="dispute_msg_${status.index}_0">
											</td>
											</tr>
										</table>
									</div>
								</div>
						</td>
					</tr>
				</c:if>
				<tr>
				<td colspan="2">
				<div class="underline" ></div>
				</td>
				</tr>
			</table >
			<table class="disputeCheckbox" border="0" width="100%" cellpadding="0" cellspacing="0">
			<div class="checkbox1">
				<tr>
					<td width="10%" class="attachment-label">
						<div class="checkbox-left">
							Attachments:
						</div>
					</td>
					<td width="90%">
						<c:forEach var="attachmentItem" items="${item.attachmentFileList}" varStatus="attachmentStatus">

						<div class="attachments-container">
							<input id="${attachmentStatus.index}" type="checkbox" name="disputeMessage.attachment" checked="true" value="${attachmentItem.id}"/>
							<label onclick="Download('${attachmentItem.file_name}','${attachmentItem.file_path}');" style="cursor:hand">
								<u>${attachmentItem.file_name}</u>
							</label>
						</div>
						</c:forEach>

					</td>
				</tr>
				</div>
				
				<tr>
				<td colspan="2">
				<div class="underline" ></div>
				</td>
				</tr>
				<tr>
				<td width="10%">
				</td>
				<td>
				<input type="button" value="Forward Dispute" onclick="forwordDispute('${status.index}')"></input>
				</td>
				</tr>
			</table>
			
		</form>
		<iframe id="rfFrame_${status.index}" name="rfFrame_${status.index}" src="about:blank" style="display:none;"></iframe>
		<c:if test="${status.last}" >
             <input type="hidden" id = "_index_last" value = ${status.index}>
         </c:if>
	</div>
</div>
</c:forEach>
<input type="hidden" id = "_index">
</div>
<script type="text/javascript">
window.onload = function() {
	
	<c:forEach var="item" items="${disputeList}" varStatus="status">
		<c:forEach var="msgItem" items="${item.disputeMsgList}" varStatus="msgStatus">
		if (${msgStatus.index} != 0) {
			var divStr = "";
			var msgItemContent = "${msgItem.rp_content}".replace(/\+/g, '%2B').replace(/\'/g, "\'");
			divStr += '<div>'
						+'<div class="underline"></div>'
						+'<div class="originalEmail">'
							+'<table class="disputeTab3"  width="100%" cellpadding="0" cellspacing="0">'
								+'<tr>'
									+'<td width="5%">'
										+'<div class="originalFrom">From</div>'
									+'</td>'
									+'<td >'
										+'<div class="originalFromInfo">'
											+'<div class="originalFromSender">${msgItem.from_address}</div>'
											+'<div class="originalFromData">${msgItem.sent_datetime}</div>'
										+'</div>'
									+'</td>'
								+'</tr>'
								+'<tr>'
									+'<td width="5%">'
										+'<div class="originalTo">To</div>'
									+'</td>'
									+'<td width="95%">'
										+'<div class="originalToReceive">${msgItem.to_address}</div>'
									+'</td>'
								+'</tr>'
								+'<tr>'
									+'<td width="5%">'
										+'<div class="originalCC">Cc</div>'
									+'</td>'
									+'<td width="95%">'
										+'<div class="originalToReceive">${msgItem.cc_address}</div>'
									+'</td>'
								+'</tr>'
								+'<tr>'
									+'<td width="5%">'
									+'</td>'
									+'<td width="95%">'
										+'<div class="originalCentent">'+msgItemContent+'</div>'
									+'</td>'
								+'</tr>'
								+'<tr>'
									+'<td width="5%">'
									+'</td>'
									+'<td id="dispute_msg_${status.index}_${msgStatus.index}">'
									+'</td>'
								+'</tr>'
							+'</table>'
						+'</div>'
					+'</div>';
			document.getElementById('dispute_msg_${status.index}_${msgStatus.index-1}').innerHTML = divStr;
		}
		</c:forEach>
	</c:forEach>
}
</script>
