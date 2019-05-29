/**
 * 主要是 invoice 选项卡下的 credit 子选项卡中的内容。
 * @auchor Jian.Dong
 * @Code Review Chao.Liu 2010/10/22 AM
 */ 
detailActionUri = {
  searchTransactionHistoryListUri : "searchTransactionHistoryList.action",
  searchDisputeNotesListUri : "searchDisputeNotesList.action",
  getDisputeNotesListPageNoUri :"getDisputeNotesListPageNo.action",
  searchDisputeActionRequestListUri : "searchDisputeActionRequestList.action",
  getDisputeActionRequestListPageNoUri : "getDisputeActionRequestListPageNo.action",
  searchDisputeReplyListUri : "searchDisputeReplyList.action",
  deleteDisputeReplyUri : "deleteDisputeReply.action",
  addReplyNoteUri : "addReplyNote.action",
  updateDisputeActionRequestStatusUri : "updateDisputeActionRequestStatus.action",
  downloadTransactionExcelUri : "downloadTransactionExcel.action"
};

function clearClose_dispute(){
	 $("#Close_dispute").html("");
	var cdd=1;
	
	for(cdd = 1 ; cdd < 4 ; cdd++ ){
		var string ="<div id =\"__up_loads_text_dispute1_Number"+cdd+"\" style=\"PADDING-TOP: 3px;height: 19px;\"><div style=\"float:left;\">"
		+"<input style=\"height:19px;width:165px;\" type='text' id=\"__up_loads_text_dispute"+cdd+"\" disabled=\"disabled\">"		                        
		+"</div><div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"
		+"<input id=\"__loads_up_two"+cdd+"\" onchange=\"document.getElementById('__up_loads_text_dispute"+cdd+"').value=this.value;\" class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
		+"<input style=\"height:19px;width:75px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/></span></div>"
		+"<div class=\"approveicon\" onclick=\"deletefile('__up_loads_text_dispute1_Number"+cdd+"');\"></div>"
		+"<div class=\"clear\" ></div></div>";
							
		$("#Close_dispute").append(string);

	}
}
function clearClose_reconcile(itemId){
	 $("#"+itemId).html("");
		for(var rff=10000;rff<10004;rff++){
		var string = "<div id =\"__up_loads_text_reconcile_Number"+rff+"\" style=\"PADDING-TOP: 3px;height: 19px;\">"
	    +"<div style=\"float:left;\">"
	    +"<input style=\"height:19px;width:275px;\" type='text' id=\"__up_loads_text_reconcile"+rff+"\" disabled=\"disabled\">"
	    +"</div><div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"
	    +"<input id=\"\" onchange=\"document.getElementById('__up_loads_text_reconcile"+rff+"').value=this.value;\" class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
	    +"<input style=\"height:19px;width:75px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/></span></div>"
	    +"<div class=\"approveicon\" onclick=\"deletefile('__up_loads_text_reconcile_Number"+rff+"');\"></div>"
	    +"<div class=\"clear\" ></div></div>"
	    $("#"+itemId).append(string);
	 }
}
function clearsplitAndCloseDisputeFiles(){
    var scc=1;
    $("#Split_Close").html("");
    
    for(scc = 1 ; scc < 4 ; scc++ ){
    	var string = "<div id =\"__up_loads_text_Split_Close_Number"+scc+"\" style=\"PADDING-TOP: 3px;height: 19px;\">"
        +"<div style=\"float:left;\">"
        +"<input style=\"height:19px;width:165px;\" type='text' id=\"__up_loads_text_Split_Close"+scc+"\" disabled=\"disabled\">"
        +"</div><div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"
        +"<input id=\"\" onchange=\"document.getElementById('__up_loads_text_Split_Close"+scc+"').value=this.value;\" class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
        +"<input style=\"height:19px;width:75px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/></span></div>"
        +"<div class=\"approveicon\" onclick=\"deletefile('__up_loads_text_Split_Close_Number"+scc+"');\"></div>"
        +"<div class=\"clear\" ></div></div>"
        $("#Split_Close").append(string);
	}
	
}

function numAdd(num1, num2) {
	var baseNum, baseNum1, baseNum2;
	try {
		baseNum1 = num1.toString().split(".")[1].length;
	} catch (e) {
		baseNum1 = 0;
	}
	try {
		baseNum2 = num2.toString().split(".")[1].length;
	} catch (e) {
		baseNum2 = 0;
	}
	baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
	return (num1 * baseNum + num2 * baseNum) / baseNum;
};

function accSub(arg1, arg2) { 
    var r1, r2, m, n; 
    try { r1 = arg1.toString().split(".")[1].length } catch (e) { r1 = 0 } 
    try { r2 = arg2.toString().split(".")[1].length } catch (e) { r2 = 0 } 
    m = Math.pow(10, Math.max(r1, r2)); 
    n = (r1 >= r2) ? r1 : r2; 
    return Number(((arg1 * m - arg2 * m) / m).toFixed(n)); 
} 

var cd=100;
var rf=300;
var sc=200;

function addCloseAsDisputeLoseFiles(){
	     cd++;
	  
		 var string ="<div id =\"__up_loads_text_dispute1_Number"+cd+"\" style=\"PADDING-TOP: 3px;height: 19px;\"><div style=\"float:left;\">"
		+"<input style=\"height:19px;width:165px;\" type='text' id=\"__up_loads_text_dispute"+cd+"\" disabled=\"disabled\">"		                        
		+"</div><div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"
 	    +"<input id=\"__loads_up_two"+cd+"\" onchange=\"document.getElementById('__up_loads_text_dispute"+cd+"').value=this.value;\" class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
        +"<input style=\"height:19px;width:75px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/></span></div>"
 	    +"<div class=\"approveicon\" onclick=\"deletefile('__up_loads_text_dispute1_Number"+cd+"');\"></div>"
		+"<div class=\"clear\" ></div></div>";
								
		$("#Close_dispute").append(string);
	
}
function addsplitCloseDisputeFile(){							  
    sc++;
	var string = "<div id =\"__up_loads_text_Split_Close_Number"+sc+"\" style=\"PADDING-TOP: 3px;height: 19px;\">"
	             +"<div style=\"float:left;\">"
	             +"<input style=\"height:19px;width:165px;\" type='text' id=\"__up_loads_text_Split_Close"+sc+"\" disabled=\"disabled\">"
                 +"</div><div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"
                 +"<input id=\"\" onchange=\"document.getElementById('__up_loads_text_Split_Close"+sc+"').value=this.value;\" class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
	             +"<input style=\"height:19px;width:75px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/></span></div>"
                 +"<div class=\"approveicon\" onclick=\"deletefile('__up_loads_text_Split_Close_Number"+sc+"');\"></div>"
                 +"<div class=\"clear\" ></div></div>"
                 $("#Split_Close").append(string);
}

$(function(){

	document.getElementById("closeAsDisputeLoseWindow").style.display = "block";
       YAHOO.ccm.container.closeAsDisputeLose = new YAHOO.widget.Dialog("closeAsDisputeLoseWindow", 
						{ width : "34em",
						  fixedcenter : true,
						  visible : false, 
						  modal : true,
						  constraintoviewport : true,
						  buttons : [{
							   text:"Confirm", 
							   handler:function(){
							      var form = document.getElementById("closeDisputeLoseForm");
//							      if (form["searchDisputeVO.notes"].value.length < 10) {
//				                        $(form["searchDisputeVO.notes"]).removeClass("validation-passed");
//				                        $(form["searchDisputeVO.notes"]).addClass("validation-failed");
//				                        return false;
//				                    } else {
//				                        $(form["searchDisputeVO.notes"]).removeClass("validation-failed");
//				                        $(form["searchDisputeVO.notes"]).addClass("validation-passed");
//				                    }
				                    showLoadingModalLayer();
				                    form.submit();
							   }
						   },{ 
							   text:"Cancel", 
							   handler:function(){
							    //  $("#__up_load_text_dispute1").val("");
							      $("#searchDisputeVO_notes").val("");
							      this.cancel();
							   } 
						   } ]
						});
	YAHOO.ccm.container.closeAsDisputeLose.render();
		
	$("#updateRefileTheDispute").css("display", "block");
	YAHOO.ccm.container.updateRefileTheDispute = new YAHOO.widget.Dialog("updateRefileTheDispute", 
		{ width : "40em",
		  fixedcenter : true,
		  visible : false, 
		  modal : true,
		  constraintoviewport : true,
		  buttons : [
		  { 
		  	text:"Yes", 
		  	handler:function(){
		  		showLoadingModalLayer();
				YAHOO.util.Connect.asyncRequest("POST", "updateRefileDisputeByProposal.action", {
                    success: function(o){
                        hideLoadingModalLayer();
                        if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                            window.location.reload();
                            return;
                        }
                        var data = eval("(" + o.responseText + ")");
                        if (data.error) {
                            alert(data.error);
                            return;
                        }
						YAHOO.ccm.container.updateRefileTheDispute.hide();
					    CREDIT_TAB.ods = {
					        "length": 0,
					        "totalBalance": 0,
					        "target": false
					    };
                        notReconcileDisputePage.reload();
                        renderCreditTabMessage("Refile dispute successfully!", 10000);
                    },
                    failure: showError
                }, "ids=" + YAHOO.ccm.container.updateRefileTheDispute.proposalIds);
		  	}
		  },{
	        text:"Cancel", 
	        handler:function(){
	        	this.cancel();
	        }
		  }]
		});
	YAHOO.ccm.container.updateRefileTheDispute.render();
	
    $("#reconcileByCurrentCreditDialogM1").css("display", "block");
    reconcileByCurrentCredit_dialog_m1 = new YAHOO.widget.Dialog("reconcileByCurrentCreditDialogM1", {
        width: "490px",
        fixedcenter: true,
        visible: false,
        constraintoviewport: true,
        modal: true,
        buttons: [{
        	text: "Add",
            handler: function(){
        	rf++;
        	var string = "<div id =\"__up_loads_text_reconcile_Number"+rf+"\" style=\"PADDING-TOP: 3px;height: 19px;\">"
			             +"<div style=\"float:left;\">"
			             +"<input style=\"height:19px;width:275px;\" type='text' id=\"__up_loads_text_reconcile"+rf+"\" disabled=\"disabled\">"
                         +"</div><div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"
		                 +"<input id=\"\" onchange=\"document.getElementById('__up_loads_text_reconcile"+rf+"').value=this.value;\" class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
    		             +"<input style=\"height:19px;width:75px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/></span></div>"
		                 +"<div class=\"approveicon\" onclick=\"deletefile('__up_loads_text_reconcile_Number"+rf+"');\"></div>"
	                     +"<div class=\"clear\" ></div></div>"
	                     $("#moreReconcileFile").append(string);
	 
        }
        	
        },{
            text: "Submit",
            handler: function(){
                var form = document.getElementById("doReconcileByCurrentCreditM1Form");
//                if (form["uploadsMessage"].value.length < 10) {
//                    $(form["uploadsMessage"]).removeClass("validation-passed");
//                    $(form["uploadsMessage"]).addClass("validation-failed");
//                    return false;
//                } else {
//                    $(form["uploadsMessage"]).removeClass("validation-failed");
//                    $(form["uploadsMessage"]).addClass("validation-passed");
//					showLoadingModalLayer();
//	                form.submit();
//                }
                form.submit();
            }
        }, {
          text: "Cancel",
          handler: function(){
//	          var form = document.getElementById("doReconcileByCurrentCreditM1Form");
//	          $(form["uploadsMessage"]).val("");
//	          $(form["inProposalIds"]).val("");
//	          $(form["disputeIds"]).val("");
//	          clearClose_reconcile("moreReconcileFile");
//	   		  clearFormUploadFiles(form);
//	   	      $("#doReconcileByCurrentCreditM1FormConfirm").html("");
//	   	      accountCodeCreditTab4.setValue("");
	          this.cancel();
          }
        }]
    });
    reconcileByCurrentCredit_dialog_m1.render();
    
    $("#reconcileByCurrentCreditDialog11").css("display", "block");
    reconcileByCurrentCredit_dialog_11 = new YAHOO.widget.Dialog("reconcileByCurrentCreditDialog11", {
        width: "490px",
        fixedcenter: true,
        visible: false,
        constraintoviewport: true,
        modal: true,
        buttons: [{
        	text: "Add",
            handler: function(){
        	rf++;
        	var string = "<div id =\"__up_loads_text_reconcile_Number"+rf+"\" style=\"PADDING-TOP: 3px;height: 19px;\">"
			             +"<div style=\"float:left;\">"
			             +"<input style=\"height:19px;width:275px;\" type='text' id=\"__up_loads_text_reconcile"+rf+"\" disabled=\"disabled\">"
                         +"</div><div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"
		                 +"<input id=\"\" onchange=\"document.getElementById('__up_loads_text_reconcile"+rf+"').value=this.value;\" class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
    		             +"<input style=\"height:19px;width:75px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/></span></div>"
		                 +"<div class=\"approveicon\" onclick=\"deletefile('__up_loads_text_reconcile_Number"+rf+"');\"></div>"
	                     +"<div class=\"clear\" ></div></div>"
	                     $("#reconcileFile").append(string);
	 
        }
        	
        },{
            text: "Submit",
            handler: function(){
                var form = document.getElementById("doReconcileByCurrentCredit11Form");
    /*            if (form["uploadsMessage"].value.length < 10) {
                    $(form["uploadsMessage"]).removeClass("validation-passed");
                    $(form["uploadsMessage"]).addClass("validation-failed");
                    return false;
                } else {
                    $(form["uploadsMessage"]).removeClass("validation-failed");
                    $(form["uploadsMessage"]).addClass("validation-passed");
                }*/
                var ba = form["balanceAmount"];
                if (!FIC_checkForm(ba)) {
                    return false;
                }
                var Coc = CREDIT_TAB.ocs;
                var Cod = CREDIT_TAB.ods;
                var bav = parseFloat(ba.value);
                if ((Coc.totalBalance < 0 && bav < 0 && bav >= Coc.totalBalance) || (Coc.totalBalance > 0 && bav > 0 && bav <= Coc.totalBalance)) {
                    if (Cod.totalBalance > 0 && (bav * -1) > 0 && (bav * -1) <= Cod.totalBalance || (Cod.totalBalance < 0 && (bav * -1) < 0 && (bav * -1) >= Cod.totalBalance)) {
                        $(form["balanceAmount"]).removeClass("validation-failed");
                        $(form["balanceAmount"]).addClass("validation-passed");
						showLoadingModalLayer();
                        form.submit();
                    } else {
                        $(form["balanceAmount"]).removeClass("validation-passed");
                        $(form["balanceAmount"]).addClass("validation-failed");
                        return false;
                    }
                } else {
                    $(form["balanceAmount"]).removeClass("validation-passed");
                    $(form["balanceAmount"]).addClass("validation-failed");
                    return false;
                }
            }
        }, 
        {
          text: "Cancel",
          handler: function(){
//	          var form = document.getElementById("doReconcileByCurrentCredit11Form");
//	          $(form["uploadsMessage"]).val("");
//	          $(form["inProposalIds"]).val("");
//		      $(form["balanceAmount"]).val("");
//	          $(form["disputeIds"]).val("");
//	        //  $("#__up_load_text_invoiceDetail_23").val('');
//	        //  $("#__up_load_text_invoiceDetail_24").val('');
//	        //  $("#__up_load_text_invoiceDetail_25").val('');
//	          clearFormUploadFiles(form);
//	          accountCodeCreditTab5.setValue("");
//	          $("#doReconcileByCurrentCredit11FormConfirm").html("");
              this.cancel();
          }
        }]
    });
    reconcileByCurrentCredit_dialog_11.render();
	$("#splitCloseDisputeWindow").css("display", "block");
	
	YAHOO.ccm.container.splitCloseDisputeWindow = new YAHOO.widget.Dialog("splitCloseDisputeWindow", 
							{ width : "34em",
							  fixedcenter : true,
							  visible : false, 
							  modal : true,
							  constraintoviewport : true,
							  buttons : [{
								 text:"Close as Dispute Lose", 
								 handler:function(){
								 var form = document.getElementById('doSplitCloseDisputeForm');
						            $(form["searchDisputeVO.bo"]).val("Lose");
//									if (form["searchDisputeVO.notes"].value.length < 10) {
//										$(form["searchDisputeVO.notes"]).removeClass('validation-passed');
//										$(form["searchDisputeVO.notes"]).addClass('validation-failed');
//										return false;
//									}else {
									$(form["searchDisputeVO.notes"]).removeClass('validation-failed');
									$(form["searchDisputeVO.notes"]).addClass('validation-passed');
									var ba = form["searchDisputeVO.balanceAmount"];
				                    if (!FIC_checkForm(ba)) {
				                        return false;
				                    }
				                    
				                    var amount = parseFloat(ba.value);
									if( amount*CREDIT_TAB.ods.totalBalance<0 || Math.abs(amount) > Math.abs(CREDIT_TAB.ods.totalBalance)){
										$(ba).removeClass('validation-passed');
										$(ba).addClass('validation-failed');
										return false;
									}else{
										$(ba).removeClass('validation-failed');
										$(ba).addClass('validation-passed');
										showLoadingModalLayer();
										form.submit();
									}
//									}
								 }
							  },
							  {
								 text:"Cancel", 
								 handler:function(){
					                this.cancel();
					             } 
							  }]
							});
	YAHOO.ccm.container.splitCloseDisputeWindow.render();
	
	$("#addOutstandingDisputeSCOADialog").css("display", "block");
	YAHOO.addOutstandingDisputeSCOADialog = new YAHOO.widget.Dialog("addOutstandingDisputeSCOADialog", {
        width: "405px",
        fixedcenter: true,
        visible: false,
        constraintoviewport: true,
        modal: true,
        buttons: [{
            text: "Submit",
            handler: function(){
				if (!accountCodeCreditTab2.requiredValidate()) {
		            return false;
		        }
				if(YAHOO.addOutstandingDisputeSCOADialog.proposalIds == null){
					YAHOO.util.Connect.asyncRequest("POST", "addOutstandingDisputeSCOA.action", {
	                    success: function(o){
	                        if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
	                            window.location.reload();
	                            return;
	                        }
	                        var data = eval("(" + o.responseText + ")");
	                        if (data.error) {
	                            alert(data.error);
	                            return;
	                        }
							YAHOO.addOutstandingDisputeSCOADialog.hide();
	                        outstandingCreditPage.reload();
							notReconcileDisputePage.reload();
							queryIsShowDueDate();
							if(CREDIT_TAB.ocs && CREDIT_TAB.ocs[("id_"+YAHOO.addOutstandingDisputeSCOADialog.proposalId)]){
								CREDIT_TAB.ocs[("id_"+YAHOO.addOutstandingDisputeSCOADialog.proposalId)].accountCodeId = accountCodeCreditTab2.getValue();
							}
							if(CREDIT_TAB.ods && CREDIT_TAB.ods[("id_"+YAHOO.addOutstandingDisputeSCOADialog.proposalId)]){
								CREDIT_TAB.ods[("id_"+YAHOO.addOutstandingDisputeSCOADialog.proposalId)].accountCodeId = accountCodeCreditTab2.getValue();
							}
							if(CREDIT_TAB.ods && CREDIT_TAB.ods.target && CREDIT_TAB.ods.target.id == YAHOO.addOutstandingDisputeSCOADialog.proposalId) {
								CREDIT_TAB.ods.target.accountCodeId = accountCodeCreditTab2.getValue();
							}
	                    },
	                    failure: showError
	                }, "inProposalIds=" + YAHOO.addOutstandingDisputeSCOADialog.proposalId + "&sCOA="+accountCodeCreditTab2.getValue()+ "&note="+ccmEscape($('#addOutstandingDisputeNotes').val()));
				}else{
					YAHOO.util.Connect.asyncRequest("POST", "addOutstandingDisputeSCOAAll.action", {
	                    success: function(o){
	                        if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
	                            window.location.reload();
	                            return;
	                        }
	                        var data = eval("(" + o.responseText + ")");
	                        if (data.error) {
	                            alert(data.error);
	                            return;
	                        }
							YAHOO.addOutstandingDisputeSCOADialog.hide();
						    CREDIT_TAB.ocs = {
						        "length": 0,
						        "totalBalance": 0
						    };
							outstandingReconcileWithDispute.cancel();
	                        outstandingCreditPage.reload();
							notReconcileDisputePage.reload();
							queryIsShowDueDate();
	                    },
	                    failure: showError
	                }, "inProposalIds=" + YAHOO.addOutstandingDisputeSCOADialog.proposalIds + "&sCOA="+accountCodeCreditTab2.getValue()+ "&note="+ccmEscape($('#addOutstandingDisputeNotes').val()));
				}
            }
        }, {
            text: "Cancel",
            handler: function(){
                this.cancel();
            }
        }]
    });
	YAHOO.addOutstandingDisputeSCOADialog.render();
	
	// creditTab 下面的五个子list tab。
	
	/**
	 * Outstanding Credit.
	 * @return void
	 */
    saveOutstandingCreditPageExcel = function(){
        var titles = "titles=Credit Amount&titles=Outstanding Amount&titles=Item&titles=SCOA&titles=Circuit Number&titles=Billing Number" + "&titles=Description&titles=Address&titles=Service Type&titles=Charge Type&titles=USOC&titles=USOC Description" + "&titles=Quantity&titles=Rate&titles=Minutes&titles=Number of Calls&titles=Cellular&titles=Country&titles=Mileage" + "&titles=ASG&titles=Jurisdiction&titles=Direction&titles=Notes";
//        window.location.href = "saveOutstandingCreditPageExcel.action" + "?" + titles + "&" + outstandingCreditPage.paramStr;
        var uri = "saveOutstandingCreditPageExcel.action" + "?" + titles + "&" + outstandingCreditPage.paramStr;
        windowOpen(uri);
    };

    /**
	 * Outstanding Dispute.
	 * @return void
	 */
    saveNotReconcileDisputePageExcel = function(){
        var titles = "titles=Dispute Amount&titles=Outstanding Amount&titles=Claim Number&titles=Dispute Number&titles=Short-paid&titles=Dispute Notes&titles=Notes &titles=Dispute Date" + "&titles=Dispute Category&titles=Item&titles=SCOA&titles=Circuit Number&titles=Billing Number&titles=Description&titles=Address" + "&titles=Service Type&titles=Charge Type&titles=USOC&titles=USOC Description&titles=Quantity&titles=Rate&titles=Minutes" + "&titles=Number of Calls&titles=Cellular&titles=Country&titles=Mileage&titles=ASG&titles=Jurisdiction&titles=Direction";
        var uri = "saveNotReconcileDisputePageExcel.action" + "?" + titles + "&" + notReconcileDisputePage.paramStr;
        windowOpen(uri);
    };

    /**
	 * Reconciliation.
	 * @return void
	 */
/*    saveTheInvoiceReconciliationPageExcel = function(){
        var titles = "titles=Item&titles=SCOA&titles=Dispute Claim Number&titles=Reconciliation Date&titles=Reconciliation Amount" + "&titles=Reconciliation Status&titles=Reconciled By&titles=Notes";
        var uri = "saveTheInvoiceReconciliationPageExcel.action" + "?" + titles + "&" + theInvoiceReconciliationPage.paramStr;
        windowOpen(uri);
    };*/

    /**
	 * Close as Dispute Lost.
	 * @return void
	 */
    saveCloseAsDisputeLosePageExcel = function(){
        var titles = "titles=Item&titles=SCOA&titles=Dispute Claim Number&titles=Reconciliation Date&titles=Reconciliation Amount" + "&titles=Reconciliation Status&titles=Close By&titles=Dispute Notes&titles=Reconcile Notes";      
        var uri = "saveCloseAsDisputeLosePageExcel.action" + "?" + titles + "&" + closeAsDisputeLosePage.paramStr;
        windowOpen(uri);
    };
    
    saveCloseAsDisputeWinPageExcel = function(){
        var titles = "titles=Item&titles=SCOA&titles=Dispute Claim Number&titles=Reconciliation Date&titles=Reconciliation Amount" + "&titles=Reconciliation Status&titles=Close By&titles=Dispute Notes&titles=Reconcile Notes"; 
        var uri = "saveCloseAsDisputeWinPageExcel.action" + "?" + titles + "&" + closeAsDisputeWinPage.paramStr;
        windowOpen(uri);
    };
    
    $("#outstandingCreditSplitApplyDialog").css("display", "block");
    YAHOO.outstandingCreditSplitApplyDialog = new YAHOO.widget.Dialog("outstandingCreditSplitApplyDialog", {
        width: "470px",
        fixedcenter: true,
        visible: false,
        constraintoviewport: true,
        modal: true,
        buttons: [
//                  del by mingyang.li 2014-01-20 start
//                  {
//            text: "Apply to Payment as Dispute Win",
//            handler: function(){
//                if (!this.checkInput()) {
//                    return;
//                }
//                var proposalIds = "";
//                var Co = CREDIT_TAB.ocs;
//                for (var a in Co) {
//                    if (a != "length" && a != "totalBalance" && Co[a]) {
//                        var proposalId = a.substring(3, a.length);
//                        proposalIds += "," + proposalId;
//                    }
//                }
//                proposalIds = proposalIds.substring(1, proposalIds.length);
//                var form = document.getElementById("doOutstandingCreditSplitApplyForm");
//                form.action = "doOutstandingCreditSplitApply1.action";
//                $(form["inProposalIds"]).val(proposalIds);
//				showLoadingModalLayer();
//                form.submit();
//            }
//        }, 
//                  del by mingyang.li 2014-01-20 end
        {
            text: "Apply to Payment",
            handler: function(){
                if (!this.checkInput()) {
                    return;
                }
                var proposalIds = "";
                var Co = CREDIT_TAB.ocs;
                for (var a in Co) {
                    if (a != "length" && a != "totalBalance" && a != "target" && Co[a]) {
                        var proposalId = a.substring(3, a.length);
                        proposalIds += "," + proposalId;
                    }
                }
                proposalIds = proposalIds.substring(1, proposalIds.length);
                var form = document.getElementById("doOutstandingCreditSplitApplyForm");
                document.getElementById("__up_load_text_invoiceDetail_17").value = "";
                document.getElementById("__up_load_text_invoiceDetail_18").value = "";
                document.getElementById("__up_load_text_invoiceDetail_19").value = "";
                form.action = "doOutstandingCreditSplitApply2.action";
                $(form["inProposalIds"]).val(proposalIds);
				showLoadingModalLayer();
                form.submit();
            }
        }, {
            text: "Cancel",
            handler: function(){
                this.cancel();
            }
        }]
    });
    YAHOO.outstandingCreditSplitApplyDialog.checkInput = function(){
        if (!accountCodeCreditTab1.requiredValidate()) {
            return false;
        }
        var form = document.getElementById("doOutstandingCreditSplitApplyForm");
        if (form["uploadsMessage"].value.length < 10) {
            $(form["uploadsMessage"]).removeClass("validation-passed");
            $(form["uploadsMessage"]).addClass("validation-failed");
            return false;
        } else {
            $(form["uploadsMessage"]).removeClass("validation-failed");
            $(form["uploadsMessage"]).addClass("validation-passed");
        }
        var ba = form["balanceAmount"];
        var tba = CREDIT_TAB.ocs.totalBalance;
        if (!FIC_checkForm(ba)) {
            return false;
        }
        var bav = parseFloat(ba.value);
        if ((tba < 0 && bav < 0 && bav >= tba) || (tba > 0 && bav > 0 && bav <= tba)) {
            $(ba).removeClass("validation-failed");
            $(ba).addClass("validation-passed");
            return true;
        } else {
            $(ba).removeClass("validation-passed");
            $(ba).addClass("validation-failed");
            return false;
        }
    };
    YAHOO.outstandingCreditSplitApplyDialog.render();
});
CREDIT_TAB = null;
initializationCredit = function(){
    var VLApprove = judgementRights();

    validateCreditTabActionRole = function(){
        if (!VLApprove && SANINCO.ifAllGranted(Constants.FUNCTION.invoiceCreditAction)) {
            return true;
        } else {
            return false;
        }
    };
	validateCreditTabActionRole = validateCreditTabActionRole();
	//validateCreditTabActionRoleNew = (validateCreditTabActionRole && invoiceStatusId<=20);
	
    if (!CREDIT_TAB) {
		addOutstandingDisputeSCOA = function(id){
			YAHOO.addOutstandingDisputeSCOADialog.proposalId = id;
			YAHOO.addOutstandingDisputeSCOADialog.proposalIds = null;
			accountCodeCreditTab2.setValue("");
			$("#addOutstandingDisputeNotes").val("");
			YAHOO.addOutstandingDisputeSCOADialog.show();
		};
		addOutstandingCreditSCOAAll = function(){
			
			var Co = CREDIT_TAB.ocs;
			if (Co.length == 0) {
                renderCreditTabMessage("Please select credit!", 6000, "error");
                return false;
            }
            var proposalIds = "";
            for (var a in Co) {
                if (a != "length" && a != "totalBalance" && a != "target" && Co[a]) {
                    var proposalId = a.substring(3, a.length);
                    proposalIds += "," + proposalId;
                }
            }
            proposalIds = proposalIds.substring(1, proposalIds.length);
			if (Co.length == 1) {
				YAHOO.addOutstandingDisputeSCOADialog.proposalIds = null;
				YAHOO.addOutstandingDisputeSCOADialog.proposalId = proposalIds;
			}else{
				YAHOO.addOutstandingDisputeSCOADialog.proposalIds = proposalIds;
				YAHOO.addOutstandingDisputeSCOADialog.proposalId = null;
			}
			accountCodeCreditTab2.setValue("");
			$("#addOutstandingDisputeNotes").val("");
			YAHOO.addOutstandingDisputeSCOADialog.show();
		};
		addOutstandingDisputeSCOAAll = function(id){
			var Cod = CREDIT_TAB.ods;
			$("#addOutstandingDisputeNotes").val("");
            if (Cod.length < 1) {
                renderCreditTabMessage("Please select one or more Dispute(s).", 6000, "error");
                return false;
            }
			if (Cod.length == 1 && CREDIT_TAB.ocs.length > 1) {
				YAHOO.addOutstandingDisputeSCOADialog.proposalIds = null;
				YAHOO.addOutstandingDisputeSCOADialog.proposalId = Cod["target"].id;
			}else{
				var disputeIds = "";
	            for (var b in Cod) {
	                if (b != "length" && b != "totalBalance" && b != "target" && Cod[b]) {
	                    var disputeId = b.substring(3, b.length);
	                    disputeIds += "," + disputeId;
	                }
	            }
	            disputeIds = disputeIds.substring(1, disputeIds.length);
				YAHOO.addOutstandingDisputeSCOADialog.proposalIds = disputeIds;
				YAHOO.addOutstandingDisputeSCOADialog.proposalId = null;
			}
			accountCodeCreditTab2.setValue("");
			YAHOO.addOutstandingDisputeSCOADialog.show();
		};
        var C = [];
        C.push({
            title: function(){
                return "<input value=\"\" class=\"noBorderRadioButton\" type=\"checkbox\" name=\"outstandingCreditCheckbox\" onclick=\"checkAllOutCredit(this)\"/>";
            },
            dataField: function(row){
                var str = "<input " + (CREDIT_TAB.ocs["id_" + row.id] ? "checked=\"checked\"" : "") + " name=\"outstandingCreditCheckbox\"" + "class=\"noBorderRadioButton\" type=\"checkbox\"" + " value=\"" + row.id + "\"" + " onclick=\"outstandingCreditCheckboxClick(this," + row.balance_amount + ",'" + row.account_code_id + "');\" balanceAmount=\"" + row.balance_amount + "\" accountCodeId=\"" + row.account_code_id + "\"/>";
                return str;
            }
        });
        C.push({
            title: "Credit Amount",
            dataField: "creditAmount",
            sort: "p.credit_amount",
            filtration : {
	            name:"p.credit_amount",
	            alias:"Credit Amount"
            }
        });
        C.push({
            title: "Outstanding Amount",
            dataField: "outstandingAmount",
            sort: "p.balance_amount",
            filtration : {
	            name:"p.balance_amount",
	            alias:"Outstanding Amount"
            }
        });
        C.push({
            title: "Item",
            dataField: "item",
			className : "table_td_br",
			width : '260px',
            sort: "p.item_name"
        });
		C.push({
			title : "SCOA",
			dataField : function(row){
				if(row["scoa"]){
					return row["scoa"];
				}else{
					return (validateCreditTabActionRole ? ("<input type='button' value='SCOA Coding' onclick='addOutstandingDisputeSCOA("+row.id+")'/>") : '');
				}
			},
			sort : "ac.account_code_name"
		});
        C.push({
            title: "Circuit Number",
            dataField: "circuitNumber",
            sort: "p.circuit_number"
        });
        C.push({
            title: "Billing Number",
            dataField: "billingNumber",
            sort: "p.billing_number"
        });
        C.push({
            title: "Description",
            dataField: "description",
			className : "table_td_br",
			width : '260px',
            sort: "p.description"
        });
        C.push({
            title: "Address",
            dataField: "address",
            sort: "p.address"
        });
        C.push({
            title: "Service Type",
            dataField: "serviceType",
            sort: "p.service_type"
        });
        C.push({
            title: "Charge Type",
            dataField: "chargeType",
            sort: "p.charge_type"
        });
        C.push({
            title: "USOC",
            dataField: "usoc",
            sort: "p.usoc"
        });
        C.push({
            title: "USOC Description",
            dataField: "usocDescription",
			className : "table_td_br",
			width : '260px',
            sort: "p.usoc_description"
        });
        C.push({
            title: "Quantity",
            dataField: "quantity",
            sort: "p.quantity"
        });
        C.push({
            title: "Rate",
            dataField: "rate",
            sort: "p.rate"
        });
        C.push({
            title: "Minutes",
            dataField: "minutes",
            sort: "p.minutes"
        });
        C.push({
            title: "Number of Calls",
            dataField: "numberOfCalls",
            sort: "p.number_of_calls"
        });
        C.push({
            title: "Cellular",
            dataField: "cellularIndicator",
            sort: "p.cellular_indicator"
        });
        C.push({
            title: "Country",
            dataField: "country",
            sort: "p.country"
        });
        C.push({
            title: "Mileage",
            dataField: "mileage",
            sort: "p.mileage"
        });
        C.push({
            title: "ASG",
            dataField: "asg",
            sort: "p.asg"
        });
        C.push({
            title: "Jurisdiction",
            dataField: "jurisdiction",
            sort: "p.jurisdiction"
        });
        C.push({
            title: "Direction",
            dataField: "direction",
            sort: "p.direction"
        });
        C.push({
            title: "Notes",
            dataField: "notes",
            sort: "p.notes"
        });
        C.push({
            title: "Attachment",
            dataField: function(obj){
                return (obj.files ? "<img  src=\"include/images/download1.gif\"  style=\"cursor: pointer;\" onclick=\"SANINCO.Download(" + obj.files + ");\">" +"<input type=\"hidden\" id =\"id"+obj.id+"\" value = '" + obj.files + "'/>" : "");
            },
            sort: "p.attachment_point_id"
        });
        outstandingCreditPage = new SANINCO.Page("outstandingCreditPageDiv", "outstandingCreditPage", {
            sortingField: "p.id",
            sortingDirection: "asc",
            vo: "svo",
            recPerArray: [5, 10, 20, 50, 100, 500, 1000],
            totalPageUri: "getOutstandingCreditPageTotalNo.action",
            dataUri: "getOutstandingCreditPageData.action",
            paginationDiv: "outstandingCreditPagePaginationDiv",
            recordText: "",
            cols: C
        });
        var filter = new SANINCO.Filter();
	    filter.addEditeEvent(function(){outstandingCreditPage.start();});
	    filter.add('p.credit_amount', 'number');
	    filter.add('p.balance_amount', 'number');
	    outstandingCreditPage.setFilter(filter);
	    
/*       wenbo.zhang 20180814 - CreditAndDispute.  
 *      outstandingCreditPage.hideCol1 = function(){
            var as = $(("#" + outstandingCreditPage.did + " input[name='outstandingCreditCheckbox']"));
            $(as).parent().hide();
        };*/
        outstandingCreditPage.voParam = {
            "invoiceId1": invoiceId
        };
        outstandingCreditPage.addTotalSuccessEvent(function(data, TO){
            if (data.error || data.totalResultNo == 0) {
                $("#saveOutstandingCreditPageExcelButton").hide();
                //$("#outstandingCredit_A_button").hide();
            } else {
                $("#saveOutstandingCreditPageExcelButton").show();
                if (validateCreditTabActionRole) {
                    $("#outstandingCredit_A_button").show();
                } else {
                    $("#outstandingCredit_A_button").hide();
                }
            }
        });
        outstandingCreditPage.addCompleteEvent(function(data, TO){
            if (CREDIT_TAB.outstandingReconcileWithDisputeFlag) {
                TO.hideCol1();
            }
            var as = $("#" + TO.did + " input[name='outstandingCreditCheckbox']");
            var unCheck = 0;
            var allCheckbox = null;
            for (var i = 0; i < as.length; i++) {
                if (!as[i].value) {
                    allCheckbox = as[i];
                } else {
                    if (as[i].checked == true) {
                        $(as[i]).parent().parent().addClass("hightlight");
                    } else {
                        unCheck++;
                    }
                }
            }
            if (unCheck <= 0 && as.length > 1) {
                if (allCheckbox) {
                    allCheckbox.checked = true;
                }
            }
        });
        renderCreditTabMessage = function(msg, m, type){
            if (renderCreditTabMessage.timeout) {
                clearTimeout(renderCreditTabMessage.timeout);
                $("#_tab6_msg_div").html("");
                $("#_tab6_msg_error_div").html("");
            }
            if (type == "error") {
                $("#_tab6_msg_error_div").html(msg);
            } else {
                $("#_tab6_msg_div").html(msg);
            }
            if (msg != "") {
                renderCreditTabMessage.timeout = setTimeout(function(){
                    $("#_tab6_msg_div").html("");
                    $("#_tab6_msg_error_div").html("");
                }, m);
            }
        };
        renderCreditTabMessage.timeout = false;
        outstandingCreditCheckboxClick = function(checkbox, ba, acid){
            var Co = CREDIT_TAB.ocs, filed = "id_" + checkbox.value;
            if (checkbox.checked == true) {
                if (!Co[filed]) {
                    Co.length++;
                    Co.totalBalance = numAdd(Co.totalBalance,ba);
                    Co[filed] = {
                    	"id": checkbox.value,
                        "balanceAmount": ba,
                        "accountCodeId": acid
                    };
                    $(checkbox).parent().parent().addClass("hightlight");
                }
            } else {
                if (Co[filed]) {
                    Co.length--;
                    Co.totalBalance = accSub(Co.totalBalance,ba);
                    Co[filed] = false;
                    $(checkbox).parent().parent().removeClass("hightlight");
                }
                $(("#" + outstandingCreditPage.did + " input[name='" + checkbox.name + "']"))[0].checked = false;
            }
        };
        notReconcileDisputeCheckboxClick = function(checkbox, ba, acid){
            var Co = CREDIT_TAB.ods, filed = "id_" + checkbox.value;
//            if(CREDIT_TAB.outstandingReconcileWithDisputeFlag){    wenbo.zhang 20180814 - CreditAndDispute.
	            if (CREDIT_TAB.ocs.length == 1) {   
	                Co["target"] = false;
	                if (checkbox.checked == true) {
	                    if (!Co[filed]) {
	                        Co.length++;
	                        Co.totalBalance  = numAdd(Co.totalBalance,ba)
	                        Co[filed] = {	                      		
	                            "balanceAmount": ba,
	                            "accountCodeId": acid
	                        };
	                        $(checkbox).parent().parent().addClass("hightlight");
	                    }
	                } else {
	                    if (Co[filed]) {
	                        Co.length--;
	                        Co.totalBalance = accSub(Co.totalBalance,ba);
	                        Co[filed] = false;
	                        $(checkbox).parent().parent().removeClass("hightlight");
	                    }
	                    $(("#" + notReconcileDisputePage.did + " input[name='" + checkbox.name + "']"))[0].checked = false;
	                }
	           } else {
	        	   if (checkbox.checked == true) {
	                    if (!Co[filed]) {
	                        Co.length++;
	                        Co.totalBalance += ba;
	                        Co[filed] = {
	                            "balanceAmount": ba,
	                            "accountCodeId": acid
	                        };
	                        $(checkbox).parent().parent().addClass("hightlight");
	                    }
	                } else {
	                    if (Co[filed]) {
	                        Co.length--;
	                        Co.totalBalance -= ba;
	                        Co[filed] = false;
	                        $(checkbox).parent().parent().removeClass("hightlight");
	                    }
	                    $(("#" + notReconcileDisputePage.did + " input[name='" + checkbox.name + "']"))[0].checked = false;
	                }
//	                Co.length = 1;
//	                Co.totalBalance = ba;
	                Co["target"] = {
	                    "id": checkbox.value,
	                    "balanceAmount": ba,
	                    "accountCodeId": acid
	                };
	            }  
           /*   wenbo.zhang 20180814 - CreditAndDispute.}else{
            	Co["target"] = false;
                if (checkbox.checked == true) {
                    if (!Co[filed]) {
                        Co.length++;
                        Co.totalBalance += ba;
                        Co[filed] = {
                            "balanceAmount": ba,
                            "accountCodeId": acid
                        };
                        $(checkbox).parent().parent().addClass("hightlight");
                    }
                } else {
                    if (Co[filed]) {
                        Co.length--;
                        Co.totalBalance -= ba;
                        Co[filed] = false;
                        $(checkbox).parent().parent().removeClass("hightlight");
                    }
                    $(("#" + notReconcileDisputePage.did + " input[name='" + checkbox.name + "']"))[0].checked = false;
                }
            }  wenbo.zhang 20180814 - CreditAndDispute.*/
        };
        
        outCreditAndDisputeCalculation = function(name){
        	CREDIT_TAB.ocs = {
                "length": 0,
                "totalBalance": 0
            };
            CREDIT_TAB.ods = {
		        "length": 0,
		        "totalBalance": 0,
		        "target": CREDIT_TAB.ods.target
		    };
        	var Co = CREDIT_TAB.ocs;
        	var as = $(("#" + outstandingCreditPage.did + " input[name='outstandingCreditCheckbox']"));
        	for (var i = 0; i < as.length; i++) {
                var a = $(as[i]);
                var ba = (parseFloat(a.attr("balanceAmount")));
                var filed = "id_" + a.val();
                if (as[i].checked) {
                    if (!Co[filed]) {
                        Co.length++;
                        Co.totalBalance  = numAdd(Co.totalBalance,ba)
                        Co[filed] = {
                            "balanceAmount": ba,
                            "accountCodeId": a.attr("accountCodeId")
                        };
                    }
                }
            }
        	
        	var Co = CREDIT_TAB.ods;
            var as = $(("#" + notReconcileDisputePage.did + " input[name='notReconcileDisputeCheckbox']"));
            for (var i = 0; i < as.length; i++) {
                var a = $(as[i]);
                var ba = (parseFloat(a.attr("balanceAmount")));
                var filed = "id_" + a.val();
                if (as[i].checked) {
                    if (!Co[filed]) {
                        Co.length++;
                        Co.totalBalance  = numAdd(Co.totalBalance,ba)
                        Co[filed] = {
                            "balanceAmount": ba,
                            "accountCodeId": a.attr("accountCodeId")
                        };
                    }
                }
            }
        };
        
        checkAllOutCredit = function(checkbox){
            var Co = CREDIT_TAB.ocs;
            var as = $(("#" + outstandingCreditPage.did + " input[name='" + checkbox.name + "']"));
            for (var i = 0; i < as.length; i++) {
                var a = $(as[i]);
                if (a.val() != checkbox.value) {
                    var ba = (parseFloat(a.attr("balanceAmount")));
                    var filed = "id_" + a.val();
                    if (checkbox.checked) {
                        if (!Co[filed]) {
                            Co.length++;
                            Co.totalBalance  = numAdd(Co.totalBalance,ba)
                            Co[filed] = {
                                "balanceAmount": ba,
                                "accountCodeId": a.attr("accountCodeId")
                            };
                            a.parent().parent().addClass("hightlight");
                        }
                        as[i].checked = true;
                    } else {
                        if (Co[filed]) {
                            Co.length--;
                            Co.totalBalance = accSub(Co.totalBalance,ba);
                            Co[filed] = false;
                            a.parent().parent().removeClass("hightlight");
                        }
                        as[i].checked = false;
                    }
                }
            }
        };
        checkAllNotDispute = function(checkbox){
            var Co = CREDIT_TAB.ods;
            var as = $(("#" + notReconcileDisputePage.did + " input[name='" + checkbox.name + "']"));
            for (var i = 0; i < as.length; i++) {
                var a = $(as[i]);
                if (a.val() != checkbox.value) {
                    var ba = (parseFloat(a.attr("balanceAmount")));
                    var filed = "id_" + a.val();
                    if (checkbox.checked) {
                        if (!Co[filed]) {
                            Co.length++;
                            Co.totalBalance  = numAdd(Co.totalBalance,ba)
                            Co[filed] = {
                                "balanceAmount": ba,
                                "accountCodeId": a.attr("accountCodeId")
                            };
                            a.parent().parent().addClass("hightlight");
                        }
                        as[i].checked = true;
                    } else {
                        if (Co[filed]) {
                            Co.length--;
                            Co.totalBalance = accSub(Co.totalBalance,ba);
                            Co[filed] = false;
                            a.parent().parent().removeClass("hightlight");
                        }
                        as[i].checked = false;
                    }
                }
            }
        };
        closeAsDisputeClick = function(checkbox){
        	var Co = CREDIT_TAB.loses, filed = "id_" + checkbox.value;
            if (checkbox.checked == true) {
                if (!Co[filed]) {
                    Co.length++;
                    Co[filed] = true;
                    $(checkbox).parent().parent().addClass("hightlight");
                }
            } else {
                if (Co[filed]) {
                    Co.length--;
                    Co[filed] = false;
                    $(checkbox).parent().parent().removeClass("hightlight");
                }
                $(("#" + closeAsDisputeLosePage.did + " input[name='" + checkbox.name + "']"))[0].checked = false;
            }
        };
        checkAllCloseAsDispute = function(checkbox){
        	var Co = CREDIT_TAB.loses;
            var as = $(("#" + closeAsDisputeLosePage.did + " input[name='" + checkbox.name + "']"));
            for (var i = 0; i < as.length; i++) {
                var a = $(as[i]);
                if (a.val() != checkbox.value) {
                	var filed = "id_" + a.val();
                	if (checkbox.checked) {
                        if (!Co[filed]) {
                            Co.length++;
                            Co[filed] = true;
                            a.parent().parent().addClass("hightlight");
                        }
                        as[i].checked = true;
                    } else {
                        if (Co[filed]) {
                            Co.length--;
                            Co[filed] = false;
                            a.parent().parent().removeClass("hightlight");
                        }
                        as[i].checked = false;
                    }
                }
            }
        };
        closeAsDisputeWinClick = function(checkbox){
        	var Co = CREDIT_TAB.loses, filed = "id_" + checkbox.value;
            if (checkbox.checked == true) {
                if (!Co[filed]) {
                    Co.length++;
                    Co[filed] = true;
                    $(checkbox).parent().parent().addClass("hightlight");
                }
            } else {
                if (Co[filed]) {
                    Co.length--;
                    Co[filed] = false;
                    $(checkbox).parent().parent().removeClass("hightlight");
                }
                $(("#" + closeAsDisputeWinPage.did + " input[name='" + checkbox.name + "']"))[0].checked = false;
            }
        };
        checkAllCloseAsDisputeWin = function(checkbox){
        	var Co = CREDIT_TAB.loses;
            var as = $(("#" + closeAsDisputeWinPage.did + " input[name='" + checkbox.name + "']"));
            for (var i = 0; i < as.length; i++) {
                var a = $(as[i]);
                if (a.val() != checkbox.value) {
                	var filed = "id_" + a.val();
                	if (checkbox.checked) {
                        if (!Co[filed]) {
                            Co.length++;
                            Co[filed] = true;
                            a.parent().parent().addClass("hightlight");
                        }
                        as[i].checked = true;
                    } else {
                        if (Co[filed]) {
                            Co.length--;
                            Co[filed] = false;
                            a.parent().parent().removeClass("hightlight");
                        }
                        as[i].checked = false;
                    }
                }
            }
        };
    /*    reconciliationClick = function(checkbox){
        	var Co = CREDIT_TAB.rs, filed = "id_" + checkbox.value;
            if (checkbox.checked == true) {
                if (!Co[filed]) {
                    Co.length++;
                    Co[filed] = true;
                    $(checkbox).parent().parent().addClass("hightlight");
                }
            } else {
                if (Co[filed]) {
                    Co.length--;
                    Co[filed] = false;
                    $(checkbox).parent().parent().removeClass("hightlight");
                }
                $(("#" + theInvoiceReconciliationPage.did + " input[name='" + checkbox.name + "']"))[0].checked = false;
            }
        };
        checkAllReconciliation = function(checkbox){
        	var Co = CREDIT_TAB.rs;
            var as = $(("#" + theInvoiceReconciliationPage.did + " input[name='" + checkbox.name + "']"));
            for (var i = 0; i < as.length; i++) {
                var a = $(as[i]);
                if (a.val() != checkbox.value) {
                	var filed = "id_" + a.val();
                	if (checkbox.checked) {
                        if (!Co[filed]) {
                            Co.length++;
                            Co[filed] = true;
                            a.parent().parent().addClass("hightlight");
                        }
                        as[i].checked = true;
                    } else {
                        if (Co[filed]) {
                            Co.length--;
                            Co[filed] = false;
                            a.parent().parent().removeClass("hightlight");
                        }
                        as[i].checked = false;
                    }
                }
            }
        };*/
        applyCreditToPayment = function(){
            var Co = CREDIT_TAB.ocs;
            if (Co.length == 0) {
                renderCreditTabMessage("Please select credit!", 6000, "error");
                return false;
            }
            var proposalIds = "";
            for (var a in Co) {
                if (a != "length" && a != "totalBalance" && a != "target" && Co[a]) {
                    //if (Co[a]) totalBalance += Co[a].balanceAmount;
                    var proposalId = a.substring(3, a.length);
                    proposalIds += "," + proposalId;
                }
            }
            proposalIds = proposalIds.substring(1, proposalIds.length);
            if (confirm("You are going to apply " + Co.totalBalance.toFixed(2) + " to current payment, please confirm!")) {
                showLoadingModalLayer();
                YAHOO.util.Connect.asyncRequest("POST", "doApplyCreditToCurrentPayment.action", {
                    success: function(o){
                        hideLoadingModalLayer();
                        if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                            window.location.reload();
                            return;
                        }
                        var data = eval("(" + o.responseText + ")");
                        if (data.error) {
                            alert(data.error);
                            return;
                        }
                        CREDIT_TAB.ocs = {
                            "length": 0,
                            "totalBalance": 0
                        };
                        outstandingCreditPage.start();
                        queryIsShowDueDate();
                        renderCreditTabMessage("Applied " + Co.totalBalance.toFixed(2) + " to current payment successfully!", 10000);
                    },
                    failure: showError
                }, "inProposalIds=" + proposalIds);
            }
        };
        doManualReconciliation = function(reconcileId){
            if (confirm("Click OK to confirm cancel reconciliation!")) {
                showLoadingModalLayer();
                YAHOO.util.Connect.asyncRequest("POST", "doManualReconciliation.action", {
                    success: function(o){
                        hideLoadingModalLayer();
                        if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                            window.location.reload();
                            return;
                        }
                        var data = eval("(" + o.responseText + ")");
                        if (data.error) {
                            alert(data.error);
                            return;
                        }
                        outstandingCreditPage.reload();
                        notReconcileDisputePage.reload();
              //          theInvoiceReconciliationPage.start();
                 	    closeAsDisputeLosePage.reload();
   				        closeAsDisputeWinPage.reload();
                        queryIsShowDueDate();
                        renderCreditTabMessage("Canceled the reconciliation successfully!", 10000);
                    },
                    failure: showError
                }, "reconcileId=" + reconcileId);
            }
        };
        recordDisputeAndApplyToPayment = function(){
            var Co = CREDIT_TAB.ocs;
            if (Co.length == 0) {
                renderCreditTabMessage("Please select credit!", 6000, "error");
                return false;
            }
            for (var a in Co) {
                if (a != "length" && a != "totalBalance" && a != "target" && Co[a]) {
                    if (!Co[a].accountCodeId) {
                        CREDIT_TAB.ocs = {
                            "length": 1,
                            "totalBalance": Co[a].balanceAmount
                        };
                        CREDIT_TAB.ocs[a] = Co[a];
                        outstandingCreditPage.start();
                        queryIsShowDueDate();
                        renderCreditTabMessage("Missing SCOA please code it.", 10000, "error");
                        return false;
                    }
                }
            }
            $("#recordDisputeAndApplyToPaymentConfirm").html("You are going to apply " + Co.totalBalance.toFixed(2) + " to current payment and record as dispute win,please confirm!");
            YAHOO.recordDisputeAndApplyToPaymentDialog.show();
        };
        YAHOO.recordDisputeAndApplyToPaymentDialog = new YAHOO.widget.Dialog("recordDisputeAndApplyToPaymentDialog", {
            width: "490px",
            fixedcenter: true,
            visible: false,
            constraintoviewport: true,
            modal: true,
            buttons: [{
                text: "Submit",
                handler: function(){
                    var form = document.getElementById("doRecordDisputeAndApplyToPaymentForm");
                    document.getElementById("__up_load_text_invoiceDetail_14").value = "";
                    document.getElementById("__up_load_text_invoiceDetail_15").value = "";
                    document.getElementById("__up_load_text_invoiceDetail_16").value = "";
                    if (form["uploadsMessage"].value.length < 10) {
                        $(form["uploadsMessage"]).removeClass("validation-passed");
                        $(form["uploadsMessage"]).addClass("validation-failed");
                        return false;
                    } else {
                        $(form["uploadsMessage"]).removeClass("validation-failed");
                        $(form["uploadsMessage"]).addClass("validation-passed");
                        var proposalIds = "";
                        var Co = CREDIT_TAB.ocs;
                        for (var a in Co) {
                            if (a != "length" && a != "totalBalance" && a != "target" && Co[a]) {
                                var proposalId = a.substring(3, a.length);
                                proposalIds += "," + proposalId;
                            }
                        }
                        proposalIds = proposalIds.substring(1, proposalIds.length);
                        $(form["inProposalIds"]).val(proposalIds);
						showLoadingModalLayer();
                        form.submit();
                    }
                }
            }, {
                text: "Cancel",
                handler: function(){
                    this.cancel();
                }
            }]
        });
        $("#recordDisputeAndApplyToPaymentDialog").css("display", "block");
        YAHOO.recordDisputeAndApplyToPaymentDialog.render();
        recordDisputeAndApplyToPaymentCall = function(text){
            hideLoadingModalLayer();
            var totalBalance = CREDIT_TAB.ocs.totalBalance;
            var data = eval("(" + text + ")");
            if (data.error) {
                alert(data.error);
                return;
            }
            CREDIT_TAB.ocs = {
                "length": 0,
                "totalBalance": 0
            };
            outstandingCreditPage.start();
      //      theInvoiceReconciliationPage.start();
         	  closeAsDisputeLosePage.reload();
   			  closeAsDisputeWinPage.reload();
            queryIsShowDueDate();
            YAHOO.recordDisputeAndApplyToPaymentDialog.hide();
            renderCreditTabMessage("Applied " + totalBalance.toFixed(2) + " to current payment recorded as dispute win successfully!", 10000);
            var form = document.getElementById("doRecordDisputeAndApplyToPaymentForm");
            $(form["uploadsMessage"]).val("");
            $(form["inProposalIds"]).val("");
            clearFormUploadFiles(form);
        };
        outstandingCreditSplitApply = function(){
            var Co = CREDIT_TAB.ocs;
            if (Co.length < 1) {
                renderCreditTabMessage("Please select credit!", 6000, "error");
                return false;
            }
            if (Co.length > 1) {
                renderCreditTabMessage("Cannot do split apply to multiple credit!", 6000, "error");
                return false;
            }
            var obj = null;
            for (var a in Co) {
                if (a != "length" && a != "totalBalance" && a != "target" && Co[a]) {
                    obj = Co[a];
                    break;
                }
            }
            YAHOO.outstandingCreditSplitApplyDialog.show();
            accountCodeCreditTab1.setValue(obj.accountCodeId);
            if(typeof($("#id"+obj.id).val()) == "undefined"){
             $("#Split_attachmentPoint_id").val("");
            }else{
            $("#Split_attachmentPoint_id").val($("#id"+obj.id).val());
            }
        };
        outstandingCreditSplitApplyCall1 = function(text){
            hideLoadingModalLayer();
            var totalBalance = CREDIT_TAB.ocs.totalBalance;
            var data = eval("(" + text + ")");
            if (data.error) {
                alert(data.error);
                return;
            }
            CREDIT_TAB.ocs = {
                "length": 0,
                "totalBalance": 0
            };
            outstandingCreditPage.reload();
     //       theInvoiceReconciliationPage.start();
            queryIsShowDueDate();
            YAHOO.outstandingCreditSplitApplyDialog.hide();
            var form = document.getElementById("doOutstandingCreditSplitApplyForm");
            renderCreditTabMessage("Applied " + $(form["balanceAmount"]).val() + " to payment as dispute win successfully!", 10000);
            $(form["uploadsMessage"]).val("");
            $(form["balanceAmount"]).val("");
            $(form["inProposalIds"]).val("");
            clearFormUploadFiles(form);
        };
        outstandingCreditSplitApplyCall2 = function(text){
            hideLoadingModalLayer();
            var totalBalance = CREDIT_TAB.ocs.totalBalance;
            var data = eval("(" + text + ")");
            if (data.error) {
                alert(data.error);
                return;
            }
            CREDIT_TAB.ocs = {
                "length": 0,
                "totalBalance": 0
            };
            outstandingCreditPage.reload();
       //     theInvoiceReconciliationPage.start();
            queryIsShowDueDate();
            YAHOO.outstandingCreditSplitApplyDialog.hide();
            var form = document.getElementById("doOutstandingCreditSplitApplyForm");
            renderCreditTabMessage("Applied " + $(form["balanceAmount"]).val() + " to payment successfully!", 10000);
            $(form["uploadsMessage"]).val("");
            $(form["balanceAmount"]).val("");
            $(form["inProposalIds"]).val("");
            clearFormUploadFiles(form);
        };
        
        // Outstanding Dispute 字段列表。
        var D = [];
        D.push({
            title: function(){
/*	  wenbo.zhang 20180814 - CreditAndDispute.            if(CREDIT_TAB.outstandingReconcileWithDisputeFlag){
	                if (CREDIT_TAB.ocs.length == 1) {
	                    return "<input value=\"\" class=\"noBorderRadioButton\" type=\"checkbox\" name=\"notReconcileDisputeCheckbox\" onclick=\"checkAllNotDispute(this)\"/>";
	                } else {
	                    return "";
	                }
	            }else{
	                return "<input value=\"\" class=\"noBorderRadioButton\" type=\"checkbox\" name=\"notReconcileDisputeCheckbox\" onclick=\"checkAllNotDispute(this)\"/>";
	            }  wenbo.zhang 20180814 - CreditAndDispute. */
        	return "<input value=\"\" class=\"noBorderRadioButton\" type=\"checkbox\" name=\"notReconcileDisputeCheckbox\" onclick=\"checkAllNotDispute(this)\"/>"  //  wenbo.zhang 20180814 - CreditAndDispute.;
            },
            dataField: function(row){
            	/*  wenbo.zhang 20180814 - CreditAndDispute. if(CREDIT_TAB.outstandingReconcileWithDisputeFlag){
	                if (CREDIT_TAB.ocs.length == 1) {
	                    return "<input " + (CREDIT_TAB.ods["id_" + row.id] ? "checked=\"checked\"" : "") + " name=\"notReconcileDisputeCheckbox\"" + " class=\"noBorderRadioButton\"type=\"checkbox\"" + " value=\"" + row.id + "\"" + " onclick=\"notReconcileDisputeCheckboxClick(this," + row.balance_amount + ",'" + row.account_code_id + "');\" balanceAmount=\"" + row.balance_amount + "\" accountCodeId=\"" + row.account_code_id + "\"/>";
	                } else {
	                    return "<input " + ((CREDIT_TAB.ods["target"] && CREDIT_TAB.ods["target"].id == row.id) ? "checked=\"checked\"" : "") + " name=\"notReconcileDisputeCheckbox\"" + " type=\"radio\"" + " value=\"" + row.id + "\"" + " onclick=\"notReconcileDisputeCheckboxClick(this," + row.balance_amount + ",'" + row.account_code_id + "');\" balanceAmount=\"" + row.balance_amount + "\" accountCodeId=\"" + row.account_code_id + "\"/>";
	                }
            	}else{
	                return "<input " + (CREDIT_TAB.ods["id_" + row.id] ? "checked=\"checked\"" : "") + " name=\"notReconcileDisputeCheckbox\"" + " class=\"noBorderRadioButton\"type=\"checkbox\"" + " value=\"" + row.id + "\"" + " onclick=\"notReconcileDisputeCheckboxClick(this," + row.balance_amount + ",'" + row.account_code_id + "');\" balanceAmount=\"" + row.balance_amount + "\" accountCodeId=\"" + row.account_code_id + "\"/>";
            	}  wenbo.zhang 20180814 - CreditAndDispute.*/
            	return "<input " + (CREDIT_TAB.ods["id_" + row.id] ? "checked=\"checked\"" : "") + " name=\"notReconcileDisputeCheckbox\"" + " class=\"noBorderRadioButton\"type=\"checkbox\"" + " value=\"" + row.id + "\"" + " onclick=\"notReconcileDisputeCheckboxClick(this," + row.balance_amount + ",'" + row.account_code_id + "');\" balanceAmount=\"" + row.balance_amount + "\" accountCodeId=\"" + row.account_code_id + "\"/>" //  wenbo.zhang 20180814 - CreditAndDispute.;
            }
        });
        D.push({
            title: "Flag",
            dataField: function(o){
        	                       if(o.flag=="Y"){
        	                    	   return "<img src=\"include/images/outstanding_1.png\">"
                                   }
        	},
            sort: "flag",
        });
        D.push({
            title: "Dispute Amount",
            dataField: "disputeAmount",
            sort: "p.dispute_amount",
            filtration : {
	            name:"p.dispute_amount",
	            alias:"Dispute Amount"
            }
        });
        D.push({
            title: "Outstanding Amount",
            dataField: "outstandingAmount",
            sort: "p.balance_amount",
            filtration : {
	            name:"p.balance_amount",
	            alias:"Outstanding Amount"
            }
        });
        D.push({
            title: "Claim Number",
            dataField: "claimNumber",
            sort: "d.claim_number",
            filtration : {
	            name:"d.claim_number",
	            alias:"Claim Number"
            }
        });

        // Dispute Number.
        // 这是一个还没有完成的字段。。。
        D.push({
            title: "Dispute Number",
            dataField: function(o){
            return "<a href=\"javascript:mark('viewDisputeDetails.action?disputeId="+o.disputeId+"','_blank');\">"+o.disputeNumber+"</a>";
            },
            sort: "d.dispute_number",
            filtration : {
	            name:"d.dispute_number",
	            alias:"Dispute Number"
            }
        });


        D.push({
            title: "Short-paid",
            dataField: "flagShortpaid",
            sort: "d.flag_shortpaid",
            filtration : {
	            name:"d.flag_shortpaid",
	            alias:"Short-paid"
            }
        });
        D.push({
            title: "Dispute Notes",
            dataField: "disputeNotes",
            sort: "d.notes"
        });
        D.push({
            title: "Notes",
            dataField: "proposalNotes",
            sort: "p.notes"
        });
        D.push({
            title: "Dispute Date",
            dataField: "createdTimestamp",
            sort: "d.created_timestamp"
        });
        D.push({
            title: "Dispute Category",
            dataField: "disputeCategory",
            sort: "dr.dispute_reason_name"
        });
        D.push({
            title: "Item",
            dataField: "item",
			className : "table_td_br",
			width : '260px',
            sort: "p.item_name"
        });
		D.push({
			title : "SCOA",
			dataField : function(row){
				if(row["scoa"]){
					return row["scoa"];
				}else{
					return (validateCreditTabActionRole ? ("<input type='button' value='SCOA Coding' onclick='addOutstandingDisputeSCOA("+row.id+")'/>") : '');
				}
			},
			sort : "ac.account_code_name"
		});
        D.push({
            title: "Circuit Number",
            dataField: "circuitNumber",
            sort: "p.circuit_number"
        });
        D.push({
            title: "Billing Number",
            dataField: "billingNumber",
            sort: "p.billing_number",
            width: "210px"
        });
        D.push({
            title: "Description",
            dataField: "description",
			className : "table_td_br",
			width : '260px',
            sort: "p.description"
        });
        D.push({
            title: "Address",
            dataField: "address",
            sort: "p.address"
        });
        D.push({
            title: "Service Type",
            dataField: "serviceType",
            sort: "p.service_type"
        });
        D.push({
            title: "Charge Type",
            dataField: "chargeType",
            sort: "p.charge_type"
        });
        D.push({
            title: "USOC",
            dataField: "usoc",
            sort: "p.usoc"
        });
        D.push({
            title: "USOC Description",
            dataField: "usocDescription",
			className : "table_td_br",
			width : '260px',
            sort: "p.usoc_description"
        });
        D.push({
            title: "Quantity",
            dataField: "quantity",
            sort: "p.quantity"
        });
        D.push({
            title: "Rate",
            dataField: "rate",
            sort: "p.rate"
        });
        D.push({
            title: "Minutes",
            dataField: "minutes",
            sort: "p.minutes"
        });
        D.push({
            title: "Number of Calls",
            dataField: "numberOfCalls",
            sort: "p.number_of_calls"
        });
        D.push({
            title: "Cellular",
            dataField: "cellularIndicator",
            sort: "p.cellular_indicator"
        });
        D.push({
            title: "Country",
            dataField: "country",
            sort: "p.country"
        });
        D.push({
            title: "Mileage",
            dataField: "mileage",
            sort: "p.mileage"
        });
        D.push({
            title: "ASG",
            dataField: "asg",
            sort: "p.asg"
        });
        D.push({
            title: "Jurisdiction",
            dataField: "jurisdiction",
            sort: "p.jurisdiction"
        });
        D.push({
            title: "Direction",
            dataField: "direction",
            sort: "p.direction"
        });
        D.push({
            title: "Attachment",
            dataField: function(obj){
                return (obj.files ? "<img  src=\"include/images/download1.gif\"  style=\"cursor: pointer;\" onclick=\"SANINCO.Download(" + obj.files + ");\">" : "");
            },
            sort: "p.attachment_point_id"
        });
        notReconcileDisputePage = new SANINCO.Page("notReconcileDisputePageDiv", "notReconcileDisputePage", {
            sortingField: "p.id",
            sortingDirection: "asc",
            vo: "svo",
            recPerArray: [5, 10, 20, 50, 100, 500, 1000],
            totalPageUri: "getNotReconcileDisputePageTotalNo.action",
            dataUri: "getNotReconcileDisputePageData.action",
            paginationDiv: "notReconcileDisputePagePaginationDiv",
            recordText: "",
            cols: D
        });
        var filter1 = new SANINCO.Filter();
	    filter1.addEditeEvent(function(){notReconcileDisputePage.start();});
	    filter1.add('p.dispute_amount', 'number');
	    filter1.add('p.balance_amount', 'number');
	    filter1.add('d.claim_number', 'string');

	    // Dispute Number.
	    filter1.add('d.dispute_number', 'string');
	    filter1.add('d.flag_shortpaid', 'string');
	    notReconcileDisputePage.setFilter(filter1);
	    
        notReconcileDisputePage.voParam = {
            "invoiceId1": invoiceId,
            "banId1": currentBanId
        };
		notReconcileDisputePage.addTotalSuccessEvent(function (data) {
			if (data.error || data.totalResultNo == 0) {
				$("#saveNotReconcileDisputePageExcelButton").hide();
				$("#dispute-handle-div").hide();
			} else {
				$("#saveNotReconcileDisputePageExcelButton").show();
				if(validateCreditTabActionRole && !CREDIT_TAB.outstandingReconcileWithDisputeFlag){
					$("#dispute-handle-div").show();
				}else{
					$("#dispute-handle-div").hide();
				}
			}
			if(!validateCreditTabActionRole) $("#addOutstandingDisputeSCOAButton").hide();
		});
        notReconcileDisputePage.addCompleteEvent(function(data, TO){
            var as = $("#" + TO.did + " input[name='notReconcileDisputeCheckbox']");
            var unCheck = 0;
            var allCheckbox = null;
            for (var i = 0; i < as.length; i++) {
                if (!as[i].value) {
                    allCheckbox = as[i];
                } else {
                    if (as[i].checked == true) {
                        $(as[i]).parent().parent().addClass("hightlight");
                    } else {
                        unCheck++;
                    }
                }
            }
            if (unCheck <= 0 && allCheckbox) {
                if (allCheckbox) {
                    allCheckbox.checked = true;
                }
            }
        });
        
        outstandingReconcileWithDispute = function(){
            var Co = CREDIT_TAB.ocs;
            if (Co.length < 1) {
                renderCreditTabMessage("Please select credit!", 6000, "error");
                return false;
            }
            /*  wenbo.zhang 20180814 - CreditAndDispute. 
            CREDIT_TAB.ods = {
                "length": 0,
                "totalBalance": 0,
                "target": false
            };
            $("#dispute-handle-div").hide();*/      
         //   CREDIT_TAB.outstandingReconcileWithDisputeFlag = true;
            
           //  outstandingCreditPage.hideCol1();    wenbo.zhang 20180814 - CreditAndDispute. 
            //$("#NotReconcileDisputeSCOACodingButton").show();
         //   $("#outstandingReconcileWithDisputeCancelButton").show(); wenbo.zhang 20180814 - CreditAndDispute.
         //    $("#reconcileByCurrentCreditButton").show();  wenbo.zhang 20180814 - CreditAndDispute.
           // notReconcileDisputePage.start();     wenbo.zhang 20180814 - CreditAndDispute. 
          //  $("#outstandingCredit_A_button").hide();  wenbo.zhang 20180814 - CreditAndDispute. 
            //			$("#outstandingCredit_B").show();
            return true;    //wenbo.zhang 20180814 - CreditAndDispute. 
        };
        outstandingReconcileWithDispute.cancel = function(){
            /*  wenbo.zhang 20180814 - CreditAndDispute. 
               CREDIT_TAB.ods = {
		        "length": 0,
		        "totalBalance": 0,
		        "target": false
		    };*/
            $("#outstandingCredit_A_button").show();
            //$("#NotReconcileDisputeSCOACodingButton").hide();
            $("#outstandingReconcileWithDisputeCancelButton").hide();
        //    $("#reconcileByCurrentCreditButton").hide();  wenbo.zhang 20180814 - CreditAndDispute. 
            $("#dispute-handle-div").show();
    	//	CREDIT_TAB.outstandingReconcileWithDisputeFlag = false; wenbo.zhang 20180814 - CreditAndDispute. 
            notReconcileDisputePage.start();
            $(("#" + outstandingCreditPage.did + " input[name='outstandingCreditCheckbox']")).parent().show();
        };
        reconcileByCurrentCredit = function(){
        	outCreditAndDisputeCalculation();
        	// start  wenbo.zhang 20180814 - CreditAndDispute. 
        	  if (!outstandingReconcileWithDispute()) {
        	  		return false;
        	  }
        	  //end wenbo.zhang 20180814 - CreditAndDispute. 
        	
            var Coc = CREDIT_TAB.ocs;
            var Cod = CREDIT_TAB.ods;
            if (Cod.length < 1) {
                renderCreditTabMessage("Please select one or more Dispute(s).", 6000, "error");
                return false;
            }
            if (Coc.length < 1) {
                renderCreditTabMessage("System error!", 6000, "error");
                return false;
            }
            if (Coc.length > 1) {
                if (Cod.length == 1) {
					/*  wenbo.zhang 20180814 - CreditAndDispute. 
					if(!Cod.target.accountCodeId){
						renderCreditTabMessage("Missing SCOA please code it.", 6000, "error");
						return false;
					}*/    
					// start wenbo.zhang 20180814 - CreditAndDispute. 
                	for (var cd in Cod) {
                		
                		if (cd != "length" && cd != "totalBalance" && cd != "target" && Cod[cd]) {
                			if(!Cod[cd].accountCodeId) {
                				renderCreditTabMessage("Missing SCOA please code it.", 6000, "error");
        						return false;
                    		}
                        }	
                	}
                	// end wenbo.zhang 20180814 - CreditAndDispute. 
                    var cta = Math.abs(Coc.totalBalance).toFixed(2);
                    var dta = Math.abs(Cod.totalBalance).toFixed(2);
                    
/*                    if (eval(cta) > eval(dta)) {
                        renderCreditTabMessage("Total balance amount of multiple credits is greater than the selected dispute, can not reconcile!", 6000, "error");
                        return false;
                    } else {
                        reconcileByCurrentCredit.show_m1();
                    }*/
                    if (Cod.totalBalance > 0) {
                    	for (var cc in Coc) {
                    		
                    		if (cc != "length" && cc != "totalBalance" && cc != "target" && Coc[cc]) {
                    			if(Coc[cc].balanceAmount > 0) {
                        			renderCreditTabMessage("Selected Credit(s) can not close the Dispute(s).", 6000, "error");
                        			 return false;
                        		}
                            }	
                    	}
                    } else {
                    	for (var cc in Coc) {
                    		if (cc != "length" && cc != "totalBalance" && cc != "target" && Coc[cc]) {
                    			if(Coc[cc].balanceAmount < 0) {
                        			renderCreditTabMessage("Selected Credit(s) can not close the Dispute(s).", 6000, "error");
                        			 return false;
                        		}
                            }	
                    	}
                    }
                    
                    reconcileByCurrentCredit.show_m1();
                } else {
                    renderCreditTabMessage("Selected Credit(s) can not close the Dispute(s).", 6000, "error");
                    return false;
                }
            }
            if (Coc.length == 1) {
				for (var aa in Cod) {
	                if (aa != "length" && aa != "totalBalance" && aa != "target" && Cod[aa]) {
	                    if (!Cod[aa].accountCodeId) {
	                        renderCreditTabMessage("Missing SCOA please code it.", 10000, "error");
	                        return false;
	                    }
	                }
	            }
                if (Cod.length == 1) {
                	
                	if (Coc.totalBalance > 0) {
                    	for (var cd in Cod) {
                    		
                    		if (cd != "length" && cd != "totalBalance" && cd != "target" && Cod[cd]) {
                    			if(Cod[cd].balanceAmount > 0) {
                        			renderCreditTabMessage("Selected Credit(s) can not close the Dispute(s).", 6000, "error");
                        			 return false;
                        		}
                            }	
                    	}
                    } else {
                    	for (var cd in Cod) {
                    		if (cd != "length" && cd != "totalBalance" && cd != "target" && Cod[cd]) {
                    			if(Cod[cd].balanceAmount < 0) {
                        			renderCreditTabMessage("Selected Credit(s) can not close the Dispute(s).", 6000, "error");
                        			 return false;
                        		}
                            }	
                    	}
                    }
                	
                    reconcileByCurrentCredit.show_11();
                } else {
                	
                    if (Coc.totalBalance > 0) {
                    	for (var cd in Cod) {
                    		
                    		if (cd != "length" && cd != "totalBalance" && cd != "target" && Cod[cd]) {
                    			if(Cod[cd].balanceAmount > 0) {
                        			renderCreditTabMessage("Selected Credit(s) can not close the Dispute(s).", 6000, "error");
                        			 return false;
                        		}
                            }	
                    	}
                    } else {
                    	for (var cd in Cod) {
                    		if (cd != "length" && cd != "totalBalance" && cd != "target" && Cod[cd]) {
                    			if(Cod[cd].balanceAmount < 0) {
                        			renderCreditTabMessage("Selected Credit(s) can not close the Dispute(s).", 6000, "error");
                        			 return false;
                        		}
                            }	
                    	}
                    }
                    
                    var cta = Math.abs(Coc.totalBalance).toFixed(2);
                    var dta = Math.abs(Cod.totalBalance).toFixed(2);
//                    if (eval(dta) > eval(cta)) {
//                        renderCreditTabMessage("Total balance amount of multiple disputes is greater than selected credit, can not reconcile!", 6000, "error");
//                        return false;
//                    } else {
                        reconcileByCurrentCredit.show_1m();
//                    }
                }
            }
        };

        reconcileByCurrentCredit.call_m1 = function(text){
        	hideLoadingModalLayer();
            var totalBalance = CREDIT_TAB.ocs.totalBalance;
            var data = eval("(" + text + ")");
            if (data.error) {
                alert(data.error);
                return;
            }
            CREDIT_TAB.ocs = {
                "length": 0,
                "totalBalance": 0
            };
            CREDIT_TAB.ods = {
		        "length": 0,
		        "totalBalance": 0,
		        "target": false
		    };
            $("#outstandingCredit_A_button").show();
            //			$("#outstandingCredit_B").hide();
			$("#dispute-handle-div").show();
    	//	CREDIT_TAB.outstandingReconcileWithDisputeFlag = false;  wenbo.zhang 20180814 - CreditAndDispute. 
		//    $("#reconcileByCurrentCreditButton").hide();   wenbo.zhang 20180814 - CreditAndDispute. 
		    //$("#NotReconcileDisputeSCOACodingButton").hide();
		    $("#outstandingReconcileWithDisputeCancelButton").hide();
            outstandingCreditPage.start();
			notReconcileDisputePage.start();
   //         theInvoiceReconciliationPage.start();
      		closeAsDisputeLosePage.reload();
   			closeAsDisputeWinPage.reload();
            queryIsShowDueDate();
            reconcileByCurrentCredit_dialog_m1.hide();
            renderCreditTabMessage("Reconcile success,Amount is " + totalBalance.toFixed(2), 10000);
        };
        reconcileByCurrentCredit.show_m1 = function(s){
            var Coc = CREDIT_TAB.ocs;
            var Cdc = CREDIT_TAB.ods;
            //M1 Clean
            var form = document.getElementById("doReconcileByCurrentCreditM1Form");
            $(form["uploadsMessage"]).val("");
            $(form["inProposalIds"]).val("");
            $(form["disputeIds"]).val("");
            $("#reconcileFile").html("");
            clearClose_reconcile("moreReconcileFile");
   		    clearFormUploadFiles(form);
   	        $("#doReconcileByCurrentCreditM1FormConfirm").html("");
   	        accountCodeCreditTab4.setValue("");
            form.action = "doReconcileByCurrentCreditM1.action";
            var proposalIds = "";
            for (var a in Coc) {
                if (a != "length" && a != "totalBalance" && a != "target" && Coc[a]) {
                    var proposalId = a.substring(3, a.length);
                    proposalIds += "," + proposalId;
                }
            }
            proposalIds = proposalIds.substring(1, proposalIds.length);
            $(form["inProposalIds"]).val(proposalIds);
           // $(form["disputeIds"]).val(Cdc["target"].id);       wenbo.zhang 20180814 - CreditAndDispute.
            var cocTotalBalance = Math.abs(Coc.totalBalance).toFixed(2);
            var cdcTotalBalance = Math.abs(Cdc.totalBalance).toFixed(2);
            if (eval(cocTotalBalance) > eval(cdcTotalBalance)) {
            	$("#doReconcileByCurrentCreditM1FormConfirm").html("Reconcile Amount: " + cdcTotalBalance);
            } else {
            	$("#doReconcileByCurrentCreditM1FormConfirm").html("Reconcile Amount: " + cocTotalBalance);
            }
            
           // accountCodeCreditTab4.setValue(Cdc.target.accountCodeId);    wenbo.zhang 20180814 - CreditAndDispute.
            
            //  start wenbo.zhang 20180814 - CreditAndDispute.
            for (var cd in Cdc) {
        		
        		if (cd != "length" && cd != "totalBalance" && cd != "target"  && Cdc[cd]) {
        			if(Cdc[cd].accountCodeId) {
        				 $(form["disputeIds"]).val(cd.substring(3, a.length));
        				 accountCodeCreditTab4.setValue(Cdc[cd].accountCodeId);
            		}
                }	
        	}
            // end  wenbo.zhang 20180814 - CreditAndDispute.
            reconcileByCurrentCredit_dialog_m1.show();
        };
        reconcileByCurrentCredit.call_1m = function(text){
        	hideLoadingModalLayer();
            var totalBalance = CREDIT_TAB.ods.totalBalance;
            var data = eval("(" + text + ")");
            if (data.error) {
                alert(data.error);
                return;
            }
            CREDIT_TAB.ocs = {
                "length": 0,
                "totalBalance": 0
            };
            CREDIT_TAB.ods = {
		        "length": 0,
		        "totalBalance": 0,
		        "target": false
		    };
            $("#outstandingCredit_A_button").show();
            //$("#outstandingCredit_B").hide();
			//if(!validateCreditTabActionRole)notReconcileDisputePage.cols[0].display = "none";
			$("#dispute-handle-div").show();
		//	CREDIT_TAB.outstandingReconcileWithDisputeFlag = false; wenbo.zhang 20180814 - CreditAndDispute. 
		//    $("#reconcileByCurrentCreditButton").hide();   wenbo.zhang 20180814 - CreditAndDispute. 
		    //$("#NotReconcileDisputeSCOACodingButton").hide();
		    $("#outstandingReconcileWithDisputeCancelButton").hide();
            outstandingCreditPage.start();
			notReconcileDisputePage.start();
      //      theInvoiceReconciliationPage.start();
         	closeAsDisputeLosePage.reload();
   			closeAsDisputeWinPage.reload();
            queryIsShowDueDate();
            reconcileByCurrentCredit_dialog_m1.hide();
            renderCreditTabMessage("Reconciled " + totalBalance.toFixed(2) + " successfully!", 10000);
        };
        reconcileByCurrentCredit.show_1m = function(s){
            var Coc = CREDIT_TAB.ocs;
            var Cod = CREDIT_TAB.ods;
            //1M Clean
            var form = document.getElementById("doReconcileByCurrentCreditM1Form");
            $(form["uploadsMessage"]).val("");
            $(form["inProposalIds"]).val("");
            $(form["disputeIds"]).val("");
            $("#reconcileFile").html("");
            clearClose_reconcile("moreReconcileFile");
   		    clearFormUploadFiles(form);
   	        $("#doReconcileByCurrentCreditM1FormConfirm").html("");
   	        accountCodeCreditTab4.setValue("");
            form.action = "doReconcileByCurrentCredit1M.action";
            var proposalIds = "";
            for (var a in Coc) {
                if (a != "length" && a != "totalBalance" && a != "target" && Coc[a]) {
                    var proposalId = a.substring(3, a.length);
                    proposalIds += "," + proposalId;
                }
            }
            proposalIds = proposalIds.substring(1, proposalIds.length);
            $(form["inProposalIds"]).val(proposalIds);
            var disputeIds = "";
            for (var b in Cod) {
                if (b != "length" && b != "totalBalance" && b != "target" && Cod[b]) {
                    var disputeId = b.substring(3, b.length);
                    disputeIds += "," + disputeId;
                }
            }
            disputeIds = disputeIds.substring(1, disputeIds.length);
            $(form["disputeIds"]).val(disputeIds);
            
            var cocTotalBalance = Math.abs(Coc.totalBalance).toFixed(2);
            var codTotalBalance = Math.abs(Cod.totalBalance).toFixed(2);
            if (eval(cocTotalBalance) > eval(codTotalBalance)) {
            	$("#doReconcileByCurrentCreditM1FormConfirm").html("Reconcile Amount: " + codTotalBalance * -1);
            } else {
            	$("#doReconcileByCurrentCreditM1FormConfirm").html("Reconcile Amount: " + cocTotalBalance * -1);
            }
//            $("#doReconcileByCurrentCreditM1FormConfirm").html("Reconcile Amount: " + (Math.abs(Cod.totalBalance) * -1).toFixed(2));
            accountCodeCreditTab4.setValue("");
            reconcileByCurrentCredit_dialog_m1.show();
        };

        reconcileByCurrentCredit.call_11 = function(text){
        	hideLoadingModalLayer();
            var data = eval("(" + text + ")");
            if (data.error) {
                alert(data.error);
                return;
            }
            CREDIT_TAB.ocs = {
                "length": 0,
                "totalBalance": 0
            };
            CREDIT_TAB.ods = {
		        "length": 0,
		        "totalBalance": 0,
		        "target": false
		    };
			//notReconcileDisputePage.cols[0].display = "none";
			$("#dispute-handle-div").show();
		//	CREDIT_TAB.outstandingReconcileWithDisputeFlag = false;  wenbo.zhang 20180814 - CreditAndDispute. 
		//    $("#reconcileByCurrentCreditButton").hide();   wenbo.zhang 20180814 - CreditAndDispute. 
		    //$("#NotReconcileDisputeSCOACodingButton").hide();
		    $("#outstandingReconcileWithDisputeCancelButton").hide();
            var form = document.getElementById("doReconcileByCurrentCredit11Form");
            var totalBalance = form["balanceAmount"].value;
            $("#outstandingCredit_A_button").show();
            //$("#outstandingCredit_B").hide();
            outstandingCreditPage.start();
			notReconcileDisputePage.start();
    //        theInvoiceReconciliationPage.start();
       		closeAsDisputeLosePage.reload();
   			closeAsDisputeWinPage.reload();
            reconcileByCurrentCredit_dialog_11.hide();
            renderCreditTabMessage("Reconciled " + totalBalance + " successfully!", 10000);
        };
        reconcileByCurrentCredit.show_11 = function(s){
            var Coc = CREDIT_TAB.ocs;
            var Cod = CREDIT_TAB.ods;
            
            //11 Clean
      	    var form = document.getElementById("doReconcileByCurrentCredit11Form");
	        $(form["uploadsMessage"]).val("");
	        $(form["inProposalIds"]).val("");
		    $(form["balanceAmount"]).val("");
	        $(form["disputeIds"]).val("");
	        clearFormUploadFiles(form);
	        
	        accountCodeCreditTab5.setValue("");
	        $("#doReconcileByCurrentCredit11FormConfirm").html("");
	        $("#moreReconcileFile").html("");
	        clearClose_reconcile("reconcileFile");
            
            var proposalIds = "";
            for (var a in Coc) {
                if (a != "length" && a != "totalBalance" && a != "target" && Coc[a]) {
                    var proposalId = a.substring(3, a.length);
                    proposalIds += "," + proposalId;
                }
            }
            proposalIds = proposalIds.substring(1, proposalIds.length);
            $(form["inProposalIds"]).val(proposalIds);
            var disputeIds = "";
            var acid = "";
            for (var b in Cod) {
                if (b != "length" && b != "totalBalance" && b != "target" && Cod[b]) {
                    var disputeId = b.substring(3, b.length);
                    disputeIds += "," + disputeId;
                    acid = Cod[b].accountCodeId;
                    break;
                }
            }
            disputeIds = disputeIds.substring(1, disputeIds.length);
            $(form["disputeIds"]).val(disputeIds);
            $("#doReconcileByCurrentCredit11FormConfirm").html("Credit Amount: " + Coc.totalBalance.toFixed(2) + "<br/>" + "Dispute Amount: " + Cod.totalBalance.toFixed(2));
            
            var creditAmountABS = Math.abs(Coc.totalBalance.toFixed(2));
            var disputeAmountABS = Math.abs(Cod.totalBalance.toFixed(2));
            if (creditAmountABS < disputeAmountABS) {
            	 $("#doReconcileByCurrentCredit11FormAmount").val(Coc.totalBalance.toFixed(2));
            }else {
            	if (Coc.totalBalance.toFixed(2) > 0) {
            		$("#doReconcileByCurrentCredit11FormAmount").val(disputeAmountABS.toFixed(2));
            	}else {
            		$("#doReconcileByCurrentCredit11FormAmount").val(disputeAmountABS.toFixed(2) * -1);
            	}
            	 
            }
           
            accountCodeCreditTab5.setValue(acid);
            reconcileByCurrentCredit_dialog_11.show();
        };
        ///////////
        var E = [];
        E.push({
            title: "<input type='checkbox' name='creditTabCloseAsDispute' value='0' onclick='checkAllCloseAsDispute(this)'/>",
            dataField: function(row){
                var str = "<input type='checkbox' name='creditTabCloseAsDispute' value='"+row.id+"' onclick='closeAsDisputeClick(this)'/>";
                return str;
            }
        });
        E.push({
            title: "Item",
            dataField: "item",
			className : "table_td_br",
			width : '260px',
            sort: "toJSON(dp.item_name is null,blank_space(),dp.item_name)"
        });
        E.push({
            title: "SCOA",
			dataField : "scoa",
			sort : "ac.account_code_name"
        });
        E.push({
            title: "Dispute Claim Number",
            dataField: function(o){
            return "<a href=\"javascript:mark('viewDisputeDetails.action?disputeId="+o.disputeId+"','_blank');\">"+o.claimNumber+"</a>";
            },
            sort: "d.claim_number"
        });
        E.push({
            title: "Reconciliation Date",
            dataField: "reconcileDate",
            sort: "r.reconcile_date",
            filtration : {
	            name:"r.reconcile_date",
	            alias:"Reconciliation Date"
            }
        });
        E.push({
            title: "Reconciliation Amount",
            dataField: "reconcileAmount",
            sort: "r.reconcile_amount"
        });
        E.push({
            title: "Reconciliation Status",
            dataField: "reconcileStatus",
            sort: "rs.reconcile_status_name"
        });
        E.push({
            title: "Close By",
            dataField: function(row){
                return row.firstName + " " + row.lastName;
            },
            sort: "u.first_name asc,u.last_name"
        });
        E.push({
            title: "Dispute Notes",
            dataField: "disputeNotes",
			className : "table_td_br",
			width : '260px',
            sort: "d.notes"
        });
        E.push({
            title: "Reconcile Notes",
            dataField: "reconcileNotes",
			className : "table_td_br",
			width : '260px',
            sort: "r.notes"
        });
        E.push({
            title: "Attachment",
            dataField: function(obj){
                return (obj.files ? "<img  src=\"include/images/download1.gif\"  style=\"cursor: pointer;\" onclick=\"SANINCO.Download(" + obj.files + ");\">" : "");
            },
            sort: "r.attachment_point_id"
        });
        closeAsDisputeLosePage = new SANINCO.Page("closeAsDisputeLosePageDiv", "closeAsDisputeLosePage", {
            sortingField: "r.id",
            sortingDirection: "desc",
            vo: "svo",
            recPerArray: [5, 10, 20, 50, 100, 500, 1000],
            totalPageUri: "getCloseAsDisputeLosePageTotalNo.action",
            dataUri: "getCloseAsDisputeLosePageData.action",
            paginationDiv: "closeAsDisputeLosePagePaginationDiv",
            recordText: "",
            cols: E
        });
        
        var filter1 = new SANINCO.Filter();
	    filter1.addEditeEvent(function(){closeAsDisputeLosePage.start();});
	    filter1.add('r.reconcile_date', 'string');	  
	    closeAsDisputeLosePage.setFilter(filter1);
	    
        closeAsDisputeLosePage.voParam = {
            "invoiceId1": invoiceId,
            "banId1": currentBanId
        };
        closeAsDisputeLosePage.addTotalSuccessEvent(function(data){
            if (data.error || data.totalResultNo == 0) {
                $("#saveCloseAsDisputeLosePageExcelButton").hide();
                $("#deleteCloseAsDisputeLoseButton").hide();
            } else {
                $("#saveCloseAsDisputeLosePageExcelButton").show();
                $("#deleteCloseAsDisputeLoseButton").show();
            }
            if(!validateCreditTabActionRole) $("#deleteCloseAsDisputeLoseButton").hide();
        });
        deleteCloseAsDisputeLose = function(){
        	var Coc = CREDIT_TAB.loses;
        	if(Coc.length <= 0){
        		renderCreditTabMessage("Please select Reconciliation.", 6000, "error");
        		return false;
        	}
        	var proposalIds = "";
            for (var a in Coc) {
                if (a != "length" && Coc[a]) {
                    var proposalId = a.substring(3, a.length);
                    proposalIds += "," + proposalId;
                }
            }
            proposalIds = proposalIds.substring(1, proposalIds.length);
            if (confirm("Click OK to confirm cancel Close!")) {
                showLoadingModalLayer();
                YAHOO.util.Connect.asyncRequest("POST", "deleteReconciliationMany.action", {
                    success: function(o){
                        hideLoadingModalLayer();
                        if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                            window.location.reload();
                            return;
                        }
                        var data = eval("(" + o.responseText + ")");
                        if (data.message) {
                            alert(data.message);
//                            return;
                        }
					    CREDIT_TAB.loses = {
				        "length": 0
					    };
					    
                        outstandingCreditPage.reload();
         //               theInvoiceReconciliationPage.start();
                        notReconcileDisputePage.reload();
                        closeAsDisputeLosePage.reload();
                        closeAsDisputeWinPage.reload();
                        queryIsShowDueDate();
                        renderCreditTabMessage("Canceled the Close successfully!", 10000);
                    },
                    failure: showError
                }, "ids=" + proposalIds);
            }
        };
        
        
        
        
        var F = [];
        F.push({
            title: "<input type='checkbox' name='creditTabCloseAsDispute' value='0' onclick='checkAllCloseAsDisputeWin(this)'/>",
            dataField: function(row){
                var str = "<input type='checkbox' name='creditTabCloseAsDispute' value='"+row.id+"' onclick='closeAsDisputeWinClick(this)'/>";
                return str;
            }
        });
        F.push({
            title: "Item",
            dataField: "item",
			className : "table_td_br",
			width : '260px',
            sort: "toJSON(dp.item_name is null,blank_space(),dp.item_name)"
        });
        F.push({
            title: "SCOA",
			dataField : "scoa",
			sort : "ac.account_code_name"
        });
        F.push({
            title: "Dispute Claim Number",
            dataField: function(o){
            return "<a href=\"javascript:mark('viewDisputeDetails.action?disputeId="+o.disputeId+"','_blank');\">"+o.claimNumber+"</a>";
            },
            sort: "d.claim_number"
        });
        F.push({
            title: "Reconciliation Date",
            dataField: "reconcileDate",
            sort: "r.reconcile_date",
            filtration : {
	            name:"r.reconcile_date",
	            alias:"Dispute Amount"
            }
        });
        F.push({
            title: "Reconciliation Amount",
            dataField: "reconcileAmount",
            sort: "r.reconcile_amount"
        });
        F.push({
            title: "Reconciliation Status",
            dataField: "reconcileStatus",
            sort: "rs.reconcile_status_name"
        });
        F.push({
            title: "Close By",
            dataField: function(row){
                return row.firstName + " " + row.lastName;
            },
            sort: "u.first_name asc,u.last_name"
        });
        F.push({
            title: "Dispute Notes",
            dataField: "disputeNotes",
			className : "table_td_br",
			width : '260px',
            sort: "d.notes"
        });
        F.push({
            title: "Reconcile Notes",
            dataField: "reconcileNotes",
			className : "table_td_br",
			width : '260px',
            sort: "r.notes"
        });
        F.push({
            title: "Attachment",
            dataField: function(obj){
                return (obj.files ? "<img  src=\"include/images/download1.gif\"  style=\"cursor: pointer;\" onclick=\"SANINCO.Download(" + obj.files + ");\">" : "");
            },
            sort: "r.attachment_point_id"
        });
        
        
        
        
        closeAsDisputeWinPage = new SANINCO.Page("closeAsDisputeWinPageDiv", "closeAsDisputeWinPage", {
            sortingField: "r.id",
            sortingDirection: "desc",
            vo: "svo",
            recPerArray: [5, 10, 20, 50, 100, 500, 1000],
            totalPageUri: "getCloseAsDisputeWinPageTotalNo.action",
            dataUri: "getCloseAsDisputeWinPageData.action",
            paginationDiv: "closeAsDisputeWinPagePaginationDiv",
            recordText: "",
            cols: F
        });
        
        var filter1 = new SANINCO.Filter();
	    filter1.addEditeEvent(function(){closeAsDisputeWinPage.start();});
	    filter1.add('r.reconcile_date', 'string');	  
	    closeAsDisputeWinPage.setFilter(filter1);
	    
        closeAsDisputeWinPage.voParam = {
            "invoiceId1": invoiceId,
            "banId1": currentBanId
        };
        closeAsDisputeWinPage.addTotalSuccessEvent(function(data){
            if (data.error || data.totalResultNo == 0) {
                $("#saveCloseAsDisputeWinPageExcelButton").hide();
                $("#deleteCloseAsDisputeWinButton").hide();
            } else {
                $("#saveCloseAsDisputeWinPageExcelButton").show();
                $("#deleteCloseAsDisputeWinButton").show();
            }
            if(!validateCreditTabActionRole) $("#deleteCloseAsDisputeWinButton").hide();
        });
        
        deleteCloseAsDisputeWin = function(){
        	var Coc = CREDIT_TAB.loses;
        	if(Coc.length <= 0){
        		renderCreditTabMessage("Please select Reconciliation.", 6000, "error");
        		return false;
        	}
        	var proposalIds = "";
            for (var a in Coc) {
                if (a != "length" && Coc[a]) {
                    var proposalId = a.substring(3, a.length);
                    proposalIds += "," + proposalId;
                }
            }
            proposalIds = proposalIds.substring(1, proposalIds.length);
            if (confirm("Click OK to confirm cancel Close!")) {
                showLoadingModalLayer();
                YAHOO.util.Connect.asyncRequest("POST", "deleteReconciliationMany.action", {
                    success: function(o){
                        hideLoadingModalLayer();
                        if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                            window.location.reload();
                            return;
                        }
                        var data = eval("(" + o.responseText + ")");
                        if (data.message) {
                            alert(data.message);
//                            return;
                        }
					    CREDIT_TAB.loses = {
				        "length": 0
					    };
					    
                        outstandingCreditPage.reload();
         //               theInvoiceReconciliationPage.start();
                        notReconcileDisputePage.reload();
                        closeAsDisputeLosePage.reload();
                        closeAsDisputeWinPage.reload();
                        queryIsShowDueDate();
                        renderCreditTabMessage("Canceled the Close successfully!", 10000);
                    },
                    failure: showError
                }, "ids=" + proposalIds);
            }
        };
        

        getDisputeActionRequest();
        ////////
        var B = [];
        B.push({
            title: "<input type='checkbox' name='creditTabReconciliation' value='0' onclick='checkAllReconciliation(this)'/>",
            dataField: function(row){
                var str = "<input type='checkbox' name='creditTabReconciliation' value='"+row.id+"' onclick='reconciliationClick(this)'/>";
                return str;
            }
        });
        B.push({
            title: "Item",
            dataField: "item",
			className : "table_td_br",
			width : '260px',
            sort: "toJSON(dp.item_name is null,blank_space(),dp.item_name)"
        });
        B.push({
            title: "SCOA",
			dataField : "scoa",
			sort : "ac.account_code_name"
        });
        B.push({
            title: "Dispute Claim Number",
            dataField: function(o){
            return "<a href=\"javascript:mark('viewDisputeDetails.action?disputeId="+o.disputeId+"','_blank');\">"+o.claimNumber+"</a>";
            },
            sort: "d.claim_number"
        });
        B.push({
            title: "Reconciliation Date",
            dataField: "reconcileDate",
            sort: "r.reconcile_date"
        });
        B.push({
            title: "Reconciliation Amount",
            dataField: "reconcileAmount",
            sort: "r.reconcile_amount"
        });
        B.push({
            title: "Reconciliation Status",
            dataField: "reconcileStatus",
            sort: "rs.reconcile_status_name"
        });
        B.push({
            title: "Reconciled By",
            dataField: function(row){
                return row.firstName + " " + row.lastName;
            },
            sort: "u.first_name asc,u.last_name"
        });
        B.push({
            title: "Dispute Notes",
            dataField: "disputeNotes",
			className : "table_td_br",
			width : '260px',
            sort: "	d.notes"
        });
        B.push({
            title: "Reconcile Notes",
            dataField: "reconcileNotes",
			className : "table_td_br",
			width : '260px',
            sort: "	r.notes"
        });
        B.push({
            title: "Attachment",
            dataField: function(obj){
                return (obj.files ? "<img  src=\"include/images/download1.gif\"  style=\"cursor: pointer;\" onclick=\"SANINCO.Download(" + obj.files + ");\">" : "");
            },
            sort: "r.attachment_point_id"
        });
/*        theInvoiceReconciliationPage = new SANINCO.Page("theInvoiceReconciliationPageDiv", "theInvoiceReconciliationPage", {
            sortingField: "r.id",
            sortingDirection: "desc",
            vo: "svo",
            recPerArray: [5, 10, 20, 50, 100, 500, 1000],
            totalPageUri: "getTheInvoiceReconciliationPageTotalNo.action",
            dataUri: "getTheInvoiceReconciliationPageData.action",
            paginationDiv: "theInvoiceReconciliationPagePaginationDiv",
            recordText: "",
            cols: B
        });*/
/*        theInvoiceReconciliationPage.voParam = {
            "invoiceId1": invoiceId
        };
        theInvoiceReconciliationPage.addTotalSuccessEvent(function(data){
            if (data.error || data.totalResultNo == 0) {
                $("#saveTheInvoiceReconciliationPageExcelButton").hide();
                $("#deleteReconciliationButton").hide();
            } else {
                $("#saveTheInvoiceReconciliationPageExcelButton").show();
                $("#deleteReconciliationButton").show();
            }
        });*/
        deleteReconciliation = function(){
        	var Coc = CREDIT_TAB.rs;
        	if(Coc.length <= 0){
        		renderCreditTabMessage("Please select Reconciliation.", 6000, "error");
        		return false;
        	}
        	var proposalIds = "";
            for (var a in Coc) {
                if (a != "length" && Coc[a]) {
                    var proposalId = a.substring(3, a.length);
                    proposalIds += "," + proposalId;
                }
            }
            proposalIds = proposalIds.substring(1, proposalIds.length);
            if (confirm("Click OK to confirm cancel reconciliation!")) {
                showLoadingModalLayer();
                YAHOO.util.Connect.asyncRequest("POST", "doManualReconciliationMany.action", {
                    success: function(o){
                        hideLoadingModalLayer();
                        if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                            window.location.reload();
                            return;
                        }
                        var data = eval("(" + o.responseText + ")");
                        if (data.message) {
                            alert(data.message);
                        }
                        if (data.error) {
                            alert(data.error);
                            return;
                        }
                        CREDIT_TAB.ocs = {
                            "length": 0,
                            "totalBalance": 0
                        };
                        CREDIT_TAB.ods = {
            		        "length": 0,
            		        "totalBalance": 0,
            		        "target": false
            		    };
                        CREDIT_TAB.loses = {
					        "length": 0
					    };
					    CREDIT_TAB.rs = {
				        "length": 0
					    };
                        outstandingCreditPage.reload();
                        notReconcileDisputePage.reload();
                        disputeActionRequestListPage.reload();
                //        theInvoiceReconciliationPage.start();
                        closeAsDisputeLosePage.start();
                        closeAsDisputeWinPage.start();
                        outstandingReconcileWithDispute.cancel();
                        queryIsShowDueDate();
                        renderCreditTabMessage("Canceled the reconciliation successfully!", 10000);
                    },
                    failure: showError
                }, "inIds=" + proposalIds);
            }
        };
        
        refileTheDispute = function(){
        	var Coc = CREDIT_TAB.ods;
        	if(Coc.length <= 0){
        		renderCreditTabMessage("Please select one or more Dispute(s).", 6000, "error");
        		return false;
        	}
        	for (var aa in Coc) {
                if (aa != "length" && aa != "totalBalance" && aa != "target" && Coc[aa]) {
                    if (!Coc[aa].accountCodeId) {
                        renderCreditTabMessage("Missing SCOA please code it.", 10000, "error");
                        return false;
                    }
                }
            }
        	var proposalIds = "";
            for (var a in Coc) {
                if (a != "length" && a != "totalBalance" && a != "target" && Coc[a]) {
                    var proposalId = a.substring(3, a.length);
                    proposalIds += "," + proposalId;
                }
            }
            proposalIds = proposalIds.substring(1, proposalIds.length);
            YAHOO.ccm.container.updateRefileTheDispute.proposalIds = proposalIds;
            YAHOO.ccm.container.updateRefileTheDispute.show();
        };
        splitAndCloseDispute = function(){
        	clearsplitAndCloseDisputeFiles();
        	var Coc = CREDIT_TAB.ods;
        	if(Coc.length < 1){
        		renderCreditTabMessage("Please select a Dispute.", 6000, "error");
        		return false;
        	}
        	if(Coc.length > 1){
        		renderCreditTabMessage("Only one Dispute can Split and Close at a time.", 6000, "error");
        		return false;
        	}
        	for (var aa in Coc) {
                if (aa != "length" && aa != "totalBalance" && aa != "target" && Coc[aa]) {
                    if (!Coc[aa].accountCodeId) {
                        renderCreditTabMessage("Missing SCOA please code it.", 10000, "error");
                        return false;
                    }else{
                    	accountCodeCreditTab3.setValue(Coc[aa].accountCodeId);
                    	break;
                    }
                }
            }
        	var proposalIds = "";
            for (var a in Coc) {
                if (a != "length" && a != "totalBalance" && a != "target" && Coc[a]) {
                    var proposalId = a.substring(3, a.length);
                    proposalIds += "," + proposalId;
                }
            }
            proposalIds = proposalIds.substring(1, proposalIds.length);
            
            var form = document.getElementById('doSplitCloseDisputeForm');
            $(form["searchDisputeVO.balanceAmount"]).val('');
            $(form["searchDisputeVO.notes"]).val('');
            $('#__up_load_text_dispute2').val('');
			clearFormUploadFiles(form);
            $(form["searchDisputeVO.boxId"]).val(proposalIds);
            YAHOO.ccm.container.splitCloseDisputeWindow.show();
        };
        closeAsDisputeLose = function(){
        	clearClose_dispute();
        	var Coc = CREDIT_TAB.ods;
        	if(Coc.length < 1){
        		renderCreditTabMessage("Please select one or more Dispute(s).", 6000, "error");
        		return false;
        	}
        	for (var aa in Coc) {
                if (aa != "length" && aa != "totalBalance" && aa != "target" && Coc[aa]) {
                    if (!Coc[aa].accountCodeId) {
                        renderCreditTabMessage("Missing SCOA please code it.", 10000, "error");
                        return false;
                    }
                    if(Coc.length == 1){
		            	accountCodeCreditTab6.setValue(Coc[aa].accountCodeId);
		            }
                }
            }
            if(Coc.length > 1){
            	accountCodeCreditTab6.setValue("");
            }
        	var proposalIds = "";
            for (var a in Coc) {
                if (a != "length" && a != "totalBalance" && a != "target" && Coc[a]) {
                    var proposalId = a.substring(3, a.length);
                    proposalIds += "," + proposalId;
                }
            }
            proposalIds = proposalIds.substring(1, proposalIds.length);

            $("#__box_id").val(proposalIds);
            YAHOO.ccm.container.closeAsDisputeLose.show();
        };
        
		editDisputeItemCall = function(flag,errorMessage){
			hideLoadingModalLayer();
			if(flag){
				var form = document.getElementById("closeDisputeLoseForm");
				CREDIT_TAB.ods = {
			        "length": 0,
			        "totalBalance": 0,
			        "target": false
			    };
			    YAHOO.ccm.container.closeAsDisputeLose.hide();
			    notReconcileDisputePage.reload();
   				closeAsDisputeLosePage.reload();
   				closeAsDisputeWinPage.reload();
   				queryIsShowDueDate();
			    renderCreditTabMessage("Close As Dispute Lose successfully!", 10000);
            	$(form["searchDisputeVO.notes"]).val("");
            	$(form["searchDisputeVO.boxId"]).val("");
			//	$(form["__up_load_text_dispute1"]).val("");
				clearFormUploadFiles(form);
			}else{
				var data = eval("(" + errorMessage + ")");
	            if (data.error) {
	                alert(data.error);
	                return;
	            }
			}
		};
		splitCloseDisputeCall = function(flag,errorMessage){
           	hideLoadingModalLayer();
			if(flag){
				var form = document.getElementById("splitCloseDisputeWindow");
				CREDIT_TAB.ods = {
			        "length": 0,
			        "totalBalance": 0,
			        "target": false
			    };
				queryIsShowDueDate();
			    YAHOO.ccm.container.splitCloseDisputeWindow.hide();
			    notReconcileDisputePage.reload();
  // 				theInvoiceReconciliationPage.reload();
   				closeAsDisputeLosePage.reload();
   				closeAsDisputeWinPage.reload();
			    renderCreditTabMessage("Split close Dispute successfully!", 10000);
			    clearFormUploadFiles(form);
			}else{
				var data = eval("(" + errorMessage + ")");
	            if (data.error) {
	                alert(data.error);
	                return;
	            }
			}
		};
		
        CREDIT_TAB = {
            "ocs": {
                "length": 0,
                "totalBalance": 0
            },
            "ods": {
                "length": 0,
                "totalBalance": 0,
                "target": false
            },
            "rs": {
            	"length" : 0
            }
        };
    }
    
    if (validateCreditTabActionRole) {
        $("#outstandingCredit_A_button").show();
  //      theInvoiceReconciliationPage.cols[0].display = "table-cell";
        outstandingCreditPage.cols[0].display = "block";
    	notReconcileDisputePage.cols[0].display = "block";
    	$("#dispute-handle-div").show();
		$('#addOutstandingCreditSCOAAllButton').show();
    } else {
    //    theInvoiceReconciliationPage.cols[0].display = "none";
        outstandingCreditPage.cols[0].display = "none";
        $("#outstandingCredit_A_button").hide();
    	notReconcileDisputePage.cols[0].display = "none";
    	$("#dispute-handle-div").hide();
		$('#addOutstandingCreditSCOAAllButton').hide();
    }

   // CREDIT_TAB.outstandingReconcileWithDisputeFlag = false; wenbo.zhang 20180814 - CreditAndDispute. 
   // $("#reconcileByCurrentCreditButton").hide();    wenbo.zhang 20180814 - CreditAndDispute. 
    //$("#NotReconcileDisputeSCOACodingButton").hide();
    $("#outstandingReconcileWithDisputeCancelButton").hide();
    $("#dispute-handle-div-refile-dispute").hide();
    CREDIT_TAB.ocs = {
        "length": 0,
        "totalBalance": 0
    };
    CREDIT_TAB.ods = {
        "length": 0,
        "totalBalance": 0,
        "target": false
    };
    CREDIT_TAB.loses = {
        "length": 0
    };
    CREDIT_TAB.rs = {
        "length": 0
    };
    queryIsShowDueDate();
    outstandingCreditPage.start();
    notReconcileDisputePage.start();
//    theInvoiceReconciliationPage.start();
    closeAsDisputeLosePage.start();
    closeAsDisputeWinPage.start();
    disputeActionRequestListPage.start();
};

function queryIsShowDueDate(){
	var callback = {
		success: function(o){
			var data = eval("("+o.responseText+")");
			if(data.error){
				alert("Error: " + data.error);
				return;
			}else{
				if(data){
					$("#credit_img").show();
				}else{
					$("#credit_img").hide();
				}
			}
			delete data;
		},
		failure: showError
	};

	var pdata = "invoiceId=" + invoiceId;
	YAHOO.util.Connect.asyncRequest('POST' , "queryIsShowDueDate.action" , callback , pdata); 
}

function getDisputeActionRequest() {
///////////
    
    disputeActionRequestListPage = new SANINCO.Page("disputeActionRequestListPageDiv", "disputeActionRequestListPage", {
        vo: "searchDisputeVO",
        recPerArray: [5, 10, 20, 50, 100, 500, 1000],
        totalPageUri: "getDisputeActionRequestListPageNoByBanId.action",
        dataUri: "searchDisputeActionRequestListByBanId.action",
        paginationDiv: "__dispute_action_request_page_list",
        recordText: "",
        cols : [{ title : "Dispute Number",dataField: function(o){
			              return "<a href=\"javascript:mark('viewDisputeDetails.action?disputeId="+o.disputeId+"','_blank');\">"+o.disputeNumber+"</a>";
	            }, filtration : {name:"d.dispute_number",alias:"Dispute Number"},sort : "d.dispute_number"
        		},{ title : "Created By",dataField:"name", filtration : {name:"concat(u.first_name,u.last_name)",alias:"Created By"},sort : "concat(u.first_name,u.last_name)"
                },{ title : "Created Date",dataField:"createdTimestamp", filtration : {name:"dar.created_timestamp",alias:"Created Date"},sort : "dar.created_timestamp"
                },{ title : "Status",dataField:"status", filtration : {name:"dars.dispute_action_request_status",alias:"Status"},sort : "dars.dispute_action_request_status"
				
                },{ title : "Notes",dataField:function(o){
					return {less_sign:"<div style=\"white-space: pre-wrap;\">"+(o.notes).replace(/\</g,"&lt;")+"</div>"}
					},className : "table_td_dispute",filtration : {name:"dar.notes",alias:"Notes"},sort : "dar.notes"
				
                },{ title : "Dispute Notes",dataField:function(o){
					return {less_sign:"<div style=\"white-space: pre-wrap;\">"+(o.disputeNotes).replace(/\</g,"&lt;")+"</div>"}
				},className : "table_td_dispute",filtration : {name:"d.notes",alias:"disputeNotes"},sort : "d.notes"
			    },
				{ title : "Reply",dataField:function(o){
					return "<img  src=\"include/images/message-icon_26.png\"  style=\"cursor: pointer;\"  onClick=\"disputeReplyList(" + o.id  +", this);\"> <div style=\"float:right;padding-top:4px;padding-left:1px;\" id = \"replyCountId"+o.id+"\">"+o.replyCount+"</div>";
				    }		
				}
				,{ title : "Action",dataField:function(o){
					return "<input class='action-button' type=\"button\" value=\"Pending\" onClick=\"disputeActionRequestStatus(" + o.id + ", 2);\"/><input class='action-button' type=\"button\" value=\"Complete\" onClick=\"disputeActionRequestStatus(" + o.id + ", 3);\"/><input class='action-button' type=\"button\" value=\"Reply\" onClick=\"addReply(" + o.id + ", this);\"/>";
//					return "<input class='action-button' type=\"button\" value=\"Temporary Done\" onClick=\"disputeActionRequestStatus(" + o.id + ", 2);\"/><input class='action-button' type=\"button\" value=\"Done\" onClick=\"disputeActionRequestStatus(" + o.id + ", 3);\"/><input class='action-button' type=\"button\" value=\"Reply\" onClick=\"addReply(" + o.id + ", this);\"/>";
				    }
				}
				]
    });
    
    disputeActionRequestListPage.voParam = {
        "banId": currentBanId
    };
    
    filter2 = new SANINCO.Filter();
	filter2.addEditeEvent(function(){disputeActionRequestListPage.start();});
	filter2.add("d.dispute_number", 'String');
	filter2.add("concat(u.first_name,u.last_name)", 'String');
	filter2.add('dar.created_timestamp', "String");
	filter2.add('dars.dispute_action_request_status', 'String');
	filter2.add('dar.notes', 'String');
	filter2.add('d.notes', 'String');
	disputeActionRequestListPage.setFilter(filter2);
}

/**
 * 关闭 Dispute Action Request Modal.
 */
function closeDisputeActionRequestModal() {

	$('.dispute-action-request-modal-close').click(function() {

		$(this).parents('#dispute-action-request').hide();
	});
}

/**
 * Temporary Done -- button handler
 * @param id
 * @param status
 * @return
 */
function disputeActionRequestStatus(id,status) {
	
	$.ajax({
		url: detailActionUri.updateDisputeActionRequestStatusUri,
        type: "POST",
        dataType: "text",
        async: false,
        cache: false,
        data: ("searchDisputeVO.disputeActionRequestId="+id+"&disputeActionRequestStatus="+status),
        success: function(o){
		    hideLoadingModalLayer();
            if (o.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                window.location.reload();
                return;
            }

			var value = eval("(" + o + ")");
			
			if(value.error){
				alert(value.error);
				return;
			}	
			if(value.success) {
				queryIsShowDueDate();
				disputeActionRequestListPage.reload();
			}
			

        },
		error : showError
     });
}

/**
 * 添加reply动作。（ add reply 弹出窗口。 ）
 * 在列表中的 Action列中，点击reply按钮即可执行此动作。
 * @param {[type]} id         [description]
 * @param {[type]} DOMElement [description]
 */
function addReply(id, DOMElement) {

	// 此DOM元素的offset top值 和 left 值。
	var offsetTop = $(DOMElement).offset().top;
	var offsetLeft = $(DOMElement).offset().left;

	 // 按钮的宽度和高度。
    var buttonHeight = $(DOMElement).height();
    var buttonWidth = $(DOMElement).width();
	
	$("#__dispute_Action_Request_Id").val(id);

	// 显示弹窗容器， 之后计算高度值和宽度值。
	$("#disputeAddReply").css("display","block");

	// 弹出框的宽度和高度
	var replyHeight = $('.reply-container').height();
	var replyWidth = $('.reply-container').width();

	$("#disputeAddReply").css({ // 计算添加reply弹出窗口的位置。   
		"position": "absolute",   
		"top": (offsetTop - replyHeight - buttonHeight - 16)+'px',   
		"left": (offsetLeft - replyWidth + buttonWidth) +'px'
	});

	// 隐藏点击列表中 reply 字段中按钮产生的弹出框。
	$("#disputeAction").css("display","none");
}

/**
 * 点击 add reply 弹出窗口中的reply 按钮所触发的动作，
 * 此动作旨在将用户输入的reply内容存储到后台中。 
 */
function addReplyNote() {
	if ($.trim($("#__addReply_notes").val()) != "") {
		$.ajax({
			url: detailActionUri.addReplyNoteUri,
	        type: "POST",
	        dataType: "text",
	        async: false,
	        cache: false,
	        data: ("searchDisputeVO.disputeActionRequestId=" + $("#__dispute_Action_Request_Id").val()+"&noteNotes="+$("#__addReply_notes").val().trim() ),
	        success: function(o){
			    hideLoadingModalLayer();
	            if (o.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
	                window.location.reload();
	                return;
	            }

				var value = eval("(" + o + ")");
				
				if(value.error){
					alert("Error: " + value.error);
					return;
				}	
				if(value.success) {
					var replyCountId =  "#replyCountId"+$("#__dispute_Action_Request_Id").val();
					$(replyCountId).html(parseInt($(replyCountId).html())+1);
					$("#disputeAddReply").css("display","none");
					//刷新reply
					getDisputeReplyList($("#__dispute_Action_Request_Id").val());
					$("#__addReply_notes").val("");
				}
				

	        },
			error : showError
	     });
	}else {
		alert("No input the content.");
	}
}

/**
 * 显示  reply list 列表。
 * 点击列表中 reply 字段中按钮产生的弹出框。
 * @param  {[type]} e                      [description]
 * @param  {[type]} disputeActionRequestId [description]
 * @param  {[type]} name                   [description]
 * @return {[type]}                        [description]
 */
function disputeReplyList(disputeActionRequestId, DOMElement) {
	
	// Reply List icon 的宽度和高度。
	var iconnHeight = $(DOMElement).height();
	var iconWidth = $(DOMElement).width();

	var offsetLeft = $(DOMElement).offset().left;
	var offsetTop = $(DOMElement).offset().top;

	if ($("#replyCountId"+disputeActionRequestId).html() != 0) {
		getDisputeReplyList(disputeActionRequestId);
	}else {

		// 没有 reply list 记录。
		noReplyListRecord();
	}
	
	$("#disputeAction").css("display","block");
	// 显示回复信息列表的容器。
	var replyHeight = $('.reply-list-container').height();
	var replyWidth = $('.reply-list-container').width();

	$("#disputeAction").css({   
		"position": "absolute",   
		"top": (offsetTop - replyHeight - iconnHeight - 18) +'px',   
		"left":(offsetLeft - (replyWidth/2) + iconWidth/2)+'px'
	});

	$("#disputeAddReply").css("display","none");
}

/**
 * 从后台中获取reply list 中主要内容的方法。
 * @param  {[type]} disputeActionRequestId [description]
 * @return {[type]}                        [description]
 */
function getDisputeReplyList(disputeActionRequestId) {
	$.ajax({
		//url: actionUri.auditInvoiceUri,
		url: detailActionUri.searchDisputeReplyListUri,
        type: "POST",
        dataType: "text",
        async: false,
        cache: false,
        data: ("searchDisputeVO.disputeActionRequestId=" + disputeActionRequestId),
        success: function(o){
		    hideLoadingModalLayer();
            if (o.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                window.location.reload();
                return;
            }

			var value = eval("(" + o + ")");
			
			if(value.error){
				alert("Error: " + value.error);
				return;
			}	

			if(value.data && value.data.length > 0) { // 如果后台中返回至少一条数据。
				var str = "";
				var data = value.data;
				for(var i=0; i<data.length; i++){

					str += '<div class="reply-list-item">';
					str += '<a class="delete-item button-finger" onclick="deleteDisputeReply(' + data[i].id + ',' + disputeActionRequestId + ', this)"></a>';
					str += '<div class="top-view clearfix">';
					str += '<h4 class="reply-user">'+ data[i].name +'</h4>';
					str += '<div class="reply-date">'+ data[i].date +'</div>';
					str += '</div>';
					str += '<p class="reply-content">'+ data[i].notes +'</p>';
					str += '</div>';
				}

				$(".reply-list").html(str);
			} else { // 如果后台中返回的数据的条数为0.

				// 没有 reply list 记录。
				noReplyListRecord();
			}

        },
		error : showError
     });	
}

/**
 * 删除Reply
 * @param  {[type]} disputeActionRequestReplyId [description]
 * @param  {[type]} disputeActionRequestId      [description]
 * @return {[type]}                             [description]
 */
function deleteDisputeReply (disputeActionRequestReplyId, disputeActionRequestId, DOMElement) {

	$.ajax({
		//url: actionUri.auditInvoiceUri,
		url: detailActionUri.deleteDisputeReplyUri,
        type: "POST",
        dataType: "text",
        async: false,
        cache: false,
        data: ("searchDisputeVO.disputeActionRequestReplyId=" + disputeActionRequestReplyId),
        success: function(o){
		    hideLoadingModalLayer();
            if (o.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                window.location.reload();
                return;
            }

			var value = eval("(" + o + ")");

			// 获得当前的 reply item 的个数。
			var replyNumber = $("#replyCountId"+disputeActionRequestId).html();


			// 当列表中没有元素的时候， dispute action的位置将不会重新计算。
			if ( replyNumber > 1 ) {

				/*从新计算dispute action 的位置。*/
				calcNewPositionOfAction(DOMElement);
			}
			
			
			// 重新渲染reply list.
			getDisputeReplyList(disputeActionRequestId);

			// 限制数值 （当item的个数为零的时候就不能往下减少了）。
			if ( value.success === "success" && replyNumber !== 0 ) {

				$("#replyCountId"+disputeActionRequestId).html(replyNumber-1);
			}
			
			if(value.error){
				alert("Error: " + value.error);
				return;
			}		
        },
		error : showError
     });
	
}

/**
 * 但删除了一个reply item 之后，重新计算dispute action 的位置.
 * @param  {[type]} DOMElement [description]
 * @return {[type]}            [description]
 */
function calcNewPositionOfAction(DOMElement) {

	// Dispute action 删除了一个元素之后的位置。
	var actionPosition = $('#disputeAction').css('top').replace("px", "");

	// 所删除元素的高度。
	var deletedItemHeight = $(DOMElement).parent().outerHeight();

	// Dispute action 元素的最新位置。
	var newPosition = actionPosition*1 + deletedItemHeight + 10; // 10px 是每一个 reply list item 的margin值。

	$("#disputeAction").css({      
		"top": newPosition +'px'  
	});
}

/**
 * 当没有reply 记录的时候，应该显示的页面结构。
 * @return {[type]} [description]
 */
function noReplyListRecord() {

	var noRecordsWrapper = "<div class='no-records'>... No Reply Records ...</div>";
	$(".reply-list").html(noRecordsWrapper);
}

/**
 * 销毁 reply list 弹出框。
 * @return {[type]} [description]
 */
function dismissDisputeReplyList() {

	$('.delete-list-container').click(function() {
		$(this).parents('#disputeAction').hide();
	});

}

/**
 * 隐藏 add reply 弹出窗口。
 * @return {[type]} [description]
 */
function cancelAddReply () {
	$("#disputeAddReply").css("display","none");
}

