// @Auchor Chao.Liu 
var invoiceTabInit = {
	transactionApproval : "initTransactionApprovalTabSearch();",
	disputeIsInit : "initDisputeTabSearch();",
	creditIsInit : "initializationCredit();",
	originalIsInit : "initOriginalTabSearch();",
	tariffLinkIsInit : "initDTiffTabSearch();",
	miscAdjustmentIsInit : "initMicsTabSearch();",
	notesAttachmentIsInit : "notesAttachment();",
	get : function (key){
		if(key == "tab2"){
			eval(invoiceTabInit.transactionApproval);
//			invoiceTabInit.transactionApproval = "";
		}else if(key == "tab3"){
			eval(invoiceTabInit.disputeIsInit);
//			invoiceTabInit.disputeIsInit = "";
		}else if(key == "tab6"){
			eval(invoiceTabInit.creditIsInit);
//			invoiceTabInit.creditIsInit = "";
		}else if(key == "tab4"){
			eval(invoiceTabInit.originalIsInit);
			invoiceTabInit.originalIsInit = "";
		}else if(key == "tab5"){
			eval(invoiceTabInit.tariffLinkIsInit);
			invoiceTabInit.tariffLinkIsInit = "";
		}else if(key == "tab7"){
			eval(invoiceTabInit.miscAdjustmentIsInit);
//			invoiceTabInit.miscAdjustmentIsInit = "";
		}else if(key == "tab9"){
			eval(invoiceTabInit.notesAttachmentIsInit);
//			invoiceTabInit.miscAdjustmentIsInit = "";
		}
	}
};

// Upload Method Star
function uploadClearAllValue(){
	var uploads = document.getElementsByName("uploads");
	for(var i=0;i<uploads.length;i++){
		if(uploads[i].value != ""){
			try{ // FF Is Error,But IE Is OK!
				uploads[i].outerHTML = uploads[i].outerHTML.replace(/(value=\").+\"/i,"$1\"");
			}catch(e){
				uploads[i].value = "";
			}
		}
	}
}


// Upload Method End
var m = 10;
function AddInvoiceDisputeFile(){
	m++;
	var string ="<div id =\"DisputeFile"+m+"\" style=\"padding:3px 0 0 0;height: 19px;\" ><div style=\"float:left;\">" 	 
		+"<input style=\"height:19px;width:150px;\" type='text' id=\"__up_load_text_invoiceDetail_DisputeFile1_"+m+"\" disabled=\"disabled\"></div>" 
		+"<div style=\"float:left; margin:0 0 0 2px;\"><span class=\"ccm_upload_img\">"		
			+"<input id=\"__upload_invoice\" onchange=\"document.getElementById('__up_load_text_invoiceDetail_DisputeFile1_"+m+"').value=this.value;\"  class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
			+"<input style=\"height:19px;width:60px;position:absolute ; right:0px; top:0px;\" type=\"button\" value=\"Browse...\"/>"
		+"</span></div><div class=\"approveicon\" onclick=\"deletefile('DisputeFile"+m+"');\"></div><div class=\"clear\" ></div></div>"

		$("#addDisputeFile").append(string);
     
	
}

function updateAutopaySwitch (banId,autopaySwitch){
	var callback = {
			success: function(o){
				if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
					window.location.reload();
					return;
				}
				var data = eval("("+o.responseText+")");
				if(data.error){
					alert("Error: " + data.error);
					return;
				}
				if (data.success){
					if (autopaySwitch == "Y") {
						$("#_autopaySwitch").html("<img src='include/images/autopayOn.png' class=\"middle\"  onclick='updateAutopaySwitch("+banId+",\"N\")'>");
						
					}else {
						$("#_autopaySwitch").html("<img src='include/images/autopayOff.png' class=\"middle\"  onclick='updateAutopaySwitch("+banId+",\"Y\")'>");
					}
				}			
			},
			failure:showError
		};
		var data = "banId="+banId+"&autopaySwitch="+autopaySwitch;
		YAHOO.util.Connect.asyncRequest("POST","updateAutopaySwitch.action", callback, data);
}









