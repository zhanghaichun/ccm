var disputeItemBoxIdStr = "";
var balanceAmountSum = 0;

reconciliationActionUri = {
	getDisputeItemViewListPageNoUri : "getDisputeItemViewListPageNo.action",
	searchDisputeItemListUri : "searchDisputeItemList.action",
	getDisputeReconciliationListPageNoUri : "getDisputeReconciliationListPageNo.action",
	searchDisputeReconciliationListUri : "searchDisputeReconciliationList.action",
	deleteReconciliationUri : "deleteReconciliation.action",
	closeDisputeWinUri : "closeDisputeWin.action",
	closeDisputeLoseUri : "closeDisputeLose.action",
	downloadDisputeItemExcelUri : "downloadDisputeItemExcel.action",
	addOutstandingDisputeItemsSCOAUri : "addOutstandingDisputeItemsSCOA.action",
	getAccessoriesListPageNoUri : "getAccessoriesListPageNo.action",
	searchAccessoriesListUri : "searchAccessoriesList.action",
	delDisputeAttachmentFileUri : "delDisputeAttachmentFile.action",
	updateRefileDisputeUri : "updateRefileDispute.action",
	updateCancelRefileDisputeUri : "updateCancelRefileDispute.action"
};

disputeItemBoxId = {};

YAHOO.util.Event.onDOMReady(function () {
	deleteReconciliation();
	editDisputeItem();
	splitDisputeItem();
	addSCOAWindows();
	updateRefileDispute();
	updateCancelRefileDispute();
	showRefileDisputeButton();
	$("#updateRefileDispute_c").css("display","none");
});

function updateSCOACoing(){
	if(!judgementRights('__show_verify_rrompt')) return;
	if(!verifySCOAInBox()) return;
	accountCodeCreditTab.setValue("");
	YAHOO.ccm.container.addDisputeSCOACoding.show();
}

//Initialization Refile Dispute Button 
function showRefileDisputeButton(){
	if(Number(disputeBalance) == 0 || (disputeStatusId != 30 && disputeStatusId != 31)) document.getElementById('__refile_dispute').style.display = "none";
	if(disputeStatusId != 34) document.getElementById('__cancel_refile_dispute').style.display = "none";
	document.getElementById('__attachment_point_id').value = attachmentPointId;
	document.getElementById('__dispute_id').value = disputeId;
}

//downLoad Attachment Point
function down(name,path){
	var df = document.getElementById('__from_01');
	df.fileName.value = name;
	df.filePath.value = path;
	df.submit();
};

//update Cancel Refile Dispute
function upCancelRefileDispute(){
	var callback = {
		success: function(o){
			if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
				window.location.reload();
				return;
			}
			var data = eval("("+o.responseText+")");
			if(data.error){
				hideLoadingImg();
				alert("Error: " + data.error);
				return;
			}
			showRefileDisputeButton();
			hideLoadingModalLayer();
			window.location.reload();
		},
		failure:showError
	};
	previousProposalData = updateDisputeDetailFromUi();
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest("POST", reconciliationActionUri.updateCancelRefileDisputeUri, callback, previousProposalData);
}

//update Refile Dispute
function upRefileDispute(){
	var notes = document.getElementById('__refile_dispute_notes').value;
//	if(!authenticationNotes(notes)) return;
	var callback = {
		success: function(o){
			if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
				window.location.reload();
				return;
			}
			var data = eval("("+o.responseText+")");
			if(data.error){
				hideLoadingImg();
				alert("Error: " + data.error);
				return;
			}
			showRefileDisputeButton();
			hideLoadingModalLayer();
			window.location.reload();
		},
		failure:showError
	};
	previousProposalData = updateDisputeDetailFromUi();
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest("POST", reconciliationActionUri.updateRefileDisputeUri, callback, previousProposalData);
}

function updateDisputeDetailFromUi(){
	var postData = "searchDisputeVO.notes=" + document.getElementById('__refile_dispute_notes').value;
	postData += "&searchDisputeVO.disputeId=" + disputeId;
	return postData;
}

//authentication Notes  -- 20180720 方法不在调用,暂时保留  by lmy
function authenticationNotes(notes){
	if (notes.length < 10) {
		removeClassName('__refile_dispute_notes', 'validation-passed');
		addClassName('__refile_dispute_notes', 'validation-failed');
		return false;
	}
	else {
		removeClassName('__refile_dispute_notes', 'validation-failed');
		addClassName('__refile_dispute_notes', 'validation-passed');
		return true;
	}
}

function splitCloseDisputeItems(){
	clearClose_splitAndClose();
	if(!judgementRights('__show_verify_rrompt')) return;
	if(!verifyBoxId(mpAccountCodeId)) return;
	if(!verifyDisputeNumber()) return;
	emptyInputStr();
	YAHOO.ccm.container.splitDisputeItemWin.show();
}

//edit Dispute Items
function editDisputeItems(methodName,bo){
	clearClose_dispute();
	if(!judgementRights('__show_verify_rrompt')) return;
	if (methodName == "win") {
		if(disputeFlagShortpaid!='Y'){
			renderTimeErrorMessage('__show_verify_rrompt','Paid dispute can\'t be closed as dispute win.',3000);
//			document.getElementById('__show_verify_rrompt').innerHTML = "Paid dispute can't be closed as dispute win.";
			return;
		}
		document.getElementById('doDisputeItemWinAndLoseForm').action = reconciliationActionUri.closeDisputeWinUri;
		document.getElementById('__div_name_windows').innerHTML = "Close as dispute win";
	}
	if(methodName == "lose"){
		document.getElementById('doDisputeItemWinAndLoseForm').action = reconciliationActionUri.closeDisputeLoseUri;
		document.getElementById('__div_name_windows').innerHTML = "Close as dispute lose";
	} 
	if(!verifyBoxId(accountCodeAtDispute1)) return;
 	if (bo) {
		document.getElementById('__show_total_amount_tips').innerHTML = balanceAmountSum + " as dispute win";
	}
	else {
		document.getElementById('__show_total_amount_tips').innerHTML = balanceAmountSum + " as dispute lose";
	}
	emptyInputStr();
	
	YAHOO.ccm.container.editDisputeItem.show();
}

function emptyInputStr(){
	document.getElementById("__notes").value = "";
	//document.getElementById('__load_up_two').outerHTML += "";
	document.getElementById("__balance_amount").value = "";
	//document.getElementById('__load_up_three').outerHTML += "";
	document.getElementById("__notes_two").value = "";
	
	//document.getElementById("__up_load_text_dispute1").value = "";
	//document.getElementById("__up_load_text_dispute2").value = "";
}

//verify Dispute Number
function verifyDisputeNumber(){
	var num = disputeItemBoxIdStr.split(",");
	if(num.length > 2){
		document.getElementById('__show_verify_rrompt').innerHTML = "Cannot do split and close to multiple dispute !";
		return false;
	}
	if(num.length == 2){
		document.getElementById('__show_verify_rrompt').innerHTML = "";
		return true;
	}
}

function rmoney(amount)  {  
   return parseFloat(amount.replace(/[^\d\.-]/g, ""));  
}

//Verify that the id
function verifyBoxId(aaaa){
	disputeItemBoxIdStr = "";
	balanceAmountSum = 0;
	var scoaId = "";
	for(var i in disputeItemBoxId){
		if(disputeItemBoxId[i][0] == true){
			disputeItemBoxIdStr = disputeItemBoxIdStr + i + ",";
			balanceAmountSum = Number(balanceAmountSum) +  rmoney(disputeItemBoxId[i][1]);
			scoaId = disputeItemBoxId[i][2];
			if((scoaId == "") | (scoaId == null) | (scoaId == undefined)){
				document.getElementById('__show_verify_rrompt').innerHTML = "The data you choose some of them did not SCOA Coding, please add during the operation !";
				return false;
			}
		}
	}
	if(disputeItemBoxIdStr == ""){
		document.getElementById('__show_verify_rrompt').innerHTML = "You have not selected any item !";
		return false;
	}else{
		if(aaaa)aaaa.setValue(scoaId);
		document.getElementById('__show_verify_rrompt').innerHTML = "";
		document.getElementById('__box_id_2').value = disputeItemBoxIdStr;
		document.getElementById('__box_id').value = disputeItemBoxIdStr;
		return true;
	}
}

//Verify that the selected data
function verifySCOAInBox(){
	disputeItemBoxIdStr = "";
	for(var i in disputeItemBoxId){
		if(disputeItemBoxId[i][0] == true) disputeItemBoxIdStr = disputeItemBoxIdStr + i + ",";
	}
	if(disputeItemBoxIdStr == ""){
		document.getElementById('__show_verify_rrompt').innerHTML = "You have not selected any item !";
		return false;
	}
	document.getElementById('__show_verify_rrompt').innerHTML = "";
	return true;
}

//verify Amount
function verifyAmount(amount){
	var amount = document.getElementById('__balance_amount').value;
	//amount = parseFloat(amount ? amount : 0).toFixed(5);
	//document.getElementById('__balance_amount').value = amount;
	if(Math.abs(Number(amount)) > 0 && Math.abs(Number(amount)) <= Math.abs(Number(balanceAmountSum)) && amount.match(/^(-?\d+)(\.\d+)?$/)){
		removeClassName('__balance_amount','validation-failed');
		addClassName('__balance_amount','validation-passed');
		return true;
	}else{
		removeClassName('__balance_amount','validation-passed');
		addClassName('__balance_amount','validation-failed');
		return false;
	}
}

//Verify text entry box
function verificationAmount(){
	
	var amount = document.getElementById('__balance_amount').value;
	//Number(balanceAmountSum)*Number(amount) > 0 为了判断同正同负
	if(amount.match(/^(-?\d+)(\.\d+)?$/) && (Math.abs(Number(balanceAmountSum)) - Math.abs(Number(amount))) >= 0 && Number(balanceAmountSum)*Number(amount) > 0){
//	if((0 < Number(amount) && Number(amount) <= Number(balanceAmountSum)) && (amount.match(/^(-?\d+)(\.\d+)?$/))){
		removeClassName('__balance_amount','validation-failed');
		addClassName('__balance_amount','validation-passed');
		return true;
	}else{
		removeClassName('__balance_amount','validation-passed');
		addClassName('__balance_amount','validation-failed');
		return false;
	}
}

//Access check box
function str(id,balanceAmount,accountCodeId){
	var sb = "<input type=\"checkbox\" name=\"box\" class='noBorderRadioButton' accountCodeId=\""+accountCodeId+"\" balanceAmount=\""+balanceAmount+"\" value=\""+id+"\" onclick=\"recordBoxId(this);\"/>";
	return sb;
}

//Record the id of
function recordBoxId(o){
	if (o.checked == true) {
		addClassName(o.parentNode.parentNode,'hightlight');
		disputeItemBoxId[""+o.value] = [true,o.getAttribute('balanceAmount'),o.getAttribute('accountCodeId')];
	}
	if (o.checked == false) {
		removeClassName(o.parentNode.parentNode,'hightlight');
		disputeItemBoxId[""+o.value] = [false];
	}
}

//Select All check box
function chooseAll(higherId,lowerId){
	var checked = document.getElementsByName(higherId)[0].checked;
	var selects = document.getElementsByName(lowerId);
	for(var j = 0; j < selects.length; j++){
		selects[j].checked = checked;
		if(checked == true){
			addClassName(selects[j].parentNode.parentNode,'hightlight');
			disputeItemBoxId[""+selects[j].value] = [true,selects[j].getAttribute('balanceAmount'),selects[j].getAttribute('accountCodeId')];
		}
		if(checked == false){
			removeClassName(selects[j].parentNode.parentNode,'hightlight');
			disputeItemBoxId[""+selects[j].value] = [false];
		}
	}
} 

//find Dispute Item List
function getDisputeItemViewList(){
	if(!window.DisputeItemViewListPage){
		DisputeItemViewListPage = new SANINCO.Page('__dispute_item_list',"DisputeItemViewListPage",{
			sortingField : "p.id",
		    sortingDirection : "asc",
		    vo : "searchDisputeVO",
			recordText : '',
		    totalPageUri : reconciliationActionUri.getDisputeItemViewListPageNoUri,
		    dataUri :reconciliationActionUri.searchDisputeItemListUri ,
			paginationDiv : "__dispute_item_page_list",
			recPerArray : [10,20,30,50,80,100,300],
		    cols : [{ title : "<input type=\"checkbox\" class='noBorderRadioButton' name=\"box1\" onclick=\"chooseAll('box1','box')\"/>",
					dataField: function(o){return str(''+o.id+'',''+o.balanceAmount+'',''+o.accountCodeId+'');}
			    },{ title : "Dispute Amount",dataField: "disputeAmount",sort : "p.dispute_amount"
			    },{ title : "Outstanding Amount",dataField:"balanceAmount",sort : "p.balance_amount"
				},{ title : "Item",dataField:"itemName",sort : "p.item_name"
				},{ title : "SCOA",dataField:"SCOA",sort : "ac.account_code_name"
				},{ title : "Circuit Number",dataField:"circuitNumber",sort : "p.circuit_number"
				},{ title : "Billing Number",dataField:"billingNumber",sort : "p.billing_number"
				},{ title : "Description",dataField:"description",sort : "p.description",className : "table_td_br",width:"260px"
				},{ title : "Address",dataField:"address",sort : "p.address"
				},{ title : "Service Type",dataField:"serviceType",sort : "p.service_type"
				},{ title : "Charge Type",dataField:"chargeType",sort : "p.charge_type"
				},{ title : "USOC",dataField:"usoc",sort : "p.usoc"
				},{ title : "USOC Description",dataField:"usocDescription",sort : "p.usoc_description"
				},{ title : "Quantity",dataField:"quantity",sort : "p.quantity"
				},{ title : "Rate",dataField:"rate",sort : "p.rate"
				},{ title : "Minutes",dataField:"minutes",sort : "p.minutes"
				},{ title : "Number of Calls",dataField:"numberCalls",sort : "p.number_of_calls"
				},{ title : "Cellular",dataField:"cellularIndicator",sort : "p.cellular_indicator"
				},{ title : "Country",dataField:"country",sort : "p.country"
				},{ title : "Mileage",dataField:"mileage",sort : "p.mileage"
				},{ title : "Asg",dataField:"asg",sort : "p.asg"
				},{ title : "Jurisdiction",dataField:"jurisdiction",sort : "p.jurisdiction"
				},{ title : "Direction",dataField:"direction",sort : "p.direction"
				}]
		});
		
		DisputeItemViewListPage.addBeforeSearch(function(m,t){
			if(m == "start")disputeItemBoxId = {};
		});
		
		DisputeItemViewListPage.addCompleteEvent(function(o){
			echodisputeItemId();
		});
		
		DisputeItemViewListPage.addCompleteEvent(function(m,t){
			if (!SANINCO.ifAllGranted(Constants.FUNCTION.reconciliationTabAction, Constants.FUNCTION.reconciliationTab)) {
				var s = $("#"+DisputeItemViewListPage.did+" th:eq(0)").hide();
				var ss = $("#"+DisputeItemViewListPage.did+" input[type='checkbox']").parent().hide();
			}
		});
		
		DisputeItemViewListPage.addTotalSuccessEvent(function(data){
			var styleShow = "block";
			if(data.totalResultNo==0 || data.error){
				styleShow = "none";
			}
			document.getElementById('__close_dispute_win_td').style.display = styleShow;
//			if(!SANINCO.ifAllGranted(Constants.FUNCTION.admin)){
			if(!SANINCO.ifAnyGranted(Constants.FUNCTION.admin)){
				document.getElementById('__close_dispute_win_td').style.display = "none";
			}
			document.getElementById('__split_and_close').style.display = styleShow;
			document.getElementById('__scoa_coding').style.display = styleShow;
			document.getElementById('__dispute_download_excel').style.display = styleShow;
			//document.getElementById('__refile_dispute').style.display = styleShow;
			delete data;
		});
		
	}
	
	DisputeItemViewListPage.voParam = {
		disputeId : disputeId
	};
	
	DisputeItemViewListPage.start();
}

//find Dispute Reconciliation List
function getDisputeReconciliationList(){
	if(!window.DisputeReconciliationListPage){
		DisputeReconciliationListPage = new SANINCO.Page('__dispute_reconciliation_list',"DisputeReconciliationListPage",{
			sortingField : "r.id",
		    sortingDirection : "desc",
			vo : "searchDisputeVO",
			recordText : '',
		    totalPageUri : reconciliationActionUri.getDisputeReconciliationListPageNoUri,
		    dataUri :reconciliationActionUri.searchDisputeReconciliationListUri ,
			paginationDiv : "__dispute_reconciliation_page_list",
			recPerArray : [10,20,30,50,80,100,300],
		    cols : [{ title : "Item",dataField: "itemName",sort : "p.item_name"
			    },{ title : "SCOA",dataField: "SCOA",sort : "ac.account_code_name"
			    },{	title : "Circuit Number",dataField: "circuitNumber",sort : "p.circuit_number"
			    },{ title : "Reconciliation Date",dataField:"reconcileDate",sort : "r.reconcile_date"
				},{ title : "Reconciliation Amount",dataField:"reconcileAmount",sort : "r.reconcile_amount"
				},{ title : "Release Reserve Amount",dataField:"releaseReserveAmount",sort : "r.released_reserve_amount"
				},{ title : "Reconciliation Invoice Number",dataField:"invoiceNumber",sort : "i.invoice_number"
				},{ title : "Reconciliation Status",dataField:"reconcileStatus",sort : "r.reconcile_status_id"
				},{ title : "Reconciled By",dataField:"createdBy",sort : "r.created_by"
				},{ title : "Notes",dataField:"notes",sort : "r.notes",className : "table_td_br",width:"260px"
				},{ title : "Download",dataField:function(o){if (o.attachmentPointId != "") {return "<img src=\"include/images/download1.gif\" style=\"cursor: pointer;\" onclick=\"SANINCO.Download(" + o.attachmentPointId + ");\"/>";}else{return "";}}
				},{ title : "delete",dataField:function(o){return "<img src=\"include/images/reject16.png\" border=\"0\" height=\"16\" onclick=\"delDispute('"+o.id+"');\"/>";}
				}]
		});
		
		DisputeReconciliationListPage.addCompleteEvent(function(m,t){
			if (!SANINCO.ifAllGranted(Constants.FUNCTION.reconciliationTabAction, Constants.FUNCTION.reconciliationTab)) {
				var s = $("#"+DisputeReconciliationListPage.did+" th:eq(6)").hide();
				var ss = $("#"+DisputeReconciliationListPage.did+" img[src='include/images/reject16.png']").parent().hide();
			}
		});
	
	}
	
	DisputeReconciliationListPage.voParam = {
		'disputeId' : disputeId
	};
	
	DisputeReconciliationListPage.start();
	
}

//find Accessories List
function findAccessoriesList(){
	if(!window.AccessoriesListPage){
		AccessoriesListPage = new SANINCO.Page('__show_accessories_list',"AccessoriesListPage",{
			sortingField : "af.created_timestamp",
		    sortingDirection : "asc",
			vo : "searchDisputeVO",
			recordText : '',
		    totalPageUri : reconciliationActionUri.getAccessoriesListPageNoUri,
		    dataUri :reconciliationActionUri.searchAccessoriesListUri ,
			paginationDiv : "__show_accessories_page_list",
			recPerArray : [5,10,15,20,25,30],
		    cols : [{ title : "File Name",dataField: "fileName",sort : "ad.file_name"
			    },{ title : "Download",dataField:function(o){return "<img src='include/images/download1.gif' onClick=\"down(" + "'" + o.fileName + "','" + o.filePath + "'" + ");\">";}
				},{ title : "delete",dataField:function(o){return "<img src=\"include/images/reject16.png\"  onclick=\"delDisputeAttachmentFile('"+o.id+"');\"/>";}
				}]
		});
	
	}
	
	AccessoriesListPage.voParam = {
		'disputeId' : disputeId
	};
	
	AccessoriesListPage.start();
	
}

//add Refile Dispute AttachFile
function addRefileDisputeAttachFile(attachmentPointId){
	document.getElementById('__up_load_text_invoiceDetail_5').outerHTML += "";
	document.getElementById('__attachment_point_id').value = attachmentPointId;
	findAccessoriesList();
}

hideLoadingImg = function(){};
showLoadingImg = function(){};

//delete Dispute AttachmentFile
function delDisputeAttachmentFile(attachmentFileId){
	var callback = {
		success: function(o){
			if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
				window.location.reload();
				return;
			}
			var data = eval("("+o.responseText+")");
			if(data.error){
				hideLoadingImg();
				alert("Error: " + data.error);
				return;
			}
			findAccessoriesList();
			hideLoadingModalLayer();
		},
		failure:showError
	};
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest("POST", reconciliationActionUri.delDisputeAttachmentFileUri + "?searchDisputeVO.attachmentFileId=" + attachmentFileId, callback);
}

//Echo proposalId
function echodisputeItemId(){
	var str=document.getElementsByName("box");
	for(var i in disputeItemBoxId){
		if(disputeItemBoxId[i][0] == true){
			for (j = 0; j < str.length; j++) {
				if(str[j].value == i){
					str[j].checked = true;
					promptColor(str[j]);
					break;
				}
			}
		}
	}
}

//Change color
function promptColor(o){
	if(o.checked == true) addClassName(o.parentNode.parentNode,'hightlight');
	if(o.checked == false) removeClassName(o.parentNode.parentNode,'hightlight');
}

//show delDispute window 
function delDispute(reconcileId){
	if(!judgementRights('__show_verify_reconciliation_page_rrompt')) return;
	targetFun = "deleteDisputeReconciliation(\""+reconcileId+"\")";
	YAHOO.ccm.container.deleteReconciliation.show();
}

//delete Dispute Reconciliation data
function deleteDisputeReconciliation(reconcileId){
	var callback = {
		success: function(o){
			if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
				window.location.reload();
				return;
			}
			var data = eval("("+o.responseText+")");
			if(data.error){
				hideLoadingModalLayer();
				alert("Error: " + data.error);
				return;
			}else if(data.validError){
				hideLoadingModalLayer();
				alert(unescape(data.validError));
				return;
			}
			getDisputeReconciliationList();
			getDisputeItemViewList();
			refreshOutstandingReserveAmount();
			refreshDisputeAmount();
			hideLoadingModalLayer();
		},
		failure:showError
	};
	previousProposalData = disputeDetailFromUi(reconcileId);
	showLoadingModalLayer();
	YAHOO.ccm.container.deleteReconciliation.hide();
	YAHOO.util.Connect.asyncRequest("POST", reconciliationActionUri.deleteReconciliationUri, callback, previousProposalData);
}

function disputeDetailFromUi(id){
	return "searchDisputeVO.reconcileId=" + id + "&searchDisputeVO.disputeId=" + disputeId;
}

//download Excel
function downloadExcel(){
	showLoadingModalLayer();
	titles = DisputeItemViewListPage.getTitlesParam("titles");
	paramStr = DisputeItemViewListPage.paramStr;
	window.location.href= reconciliationActionUri.downloadDisputeItemExcelUri + "?"+ titles +"&"+ paramStr;
	hideLoadingModalLayer();
}

//User Access Control
function judgementUserRights(divId){
	var bo = true;
	Common.valiUserPrivilegeBanLockDispute($('input[name="searchDisputeVO.invoiceId"]').val(),function(data){
		if (data != true) {
			if(divId)document.getElementById(divId).innerHTML = "No Privilege.";
			bo = false;
		}else{
			bo = true;
		}
	});
	return bo;
}

//judgement Rights 
function judgementRights(divId){
	var bo = true;
	if(!judgementUserRights(divId)) return;
	
	Common.valiDisputeBanLock(disputeId,function(dsId,ps){
		if (dsId <= 22 || ps == "L") {
			if(divId)document.getElementById(divId).innerHTML = "Invoice is not grant approval.";
			bo = false;
		}
		else {
			bo = true;
		}
	});
	return bo;
}

//add Outstanding Dispute Items SCOA
function addOutstandingDisputeItemsSCOA(){
	var accountCodeId = accountCodeCreditTab.getValue(); 
	if (accountCodeId == "") {
		alert("Please select the SCOA !");
		return;
	}
	var callback = {
		success: function(o){
            if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                window.location.reload();
                return;
            }
            var data = eval("(" + o.responseText + ")");
			if(data.error){
				hideLoadingImg();
				alert("Error: " + data.error);
				return;
			}
			getDisputeItemViewList();
			refreshDisputeSCOA();
			hideLoadingModalLayer();
		},
		failure:showError
	};
	YAHOO.ccm.container.addDisputeSCOACoding.hide();
	showLoadingModalLayer();
	var previousProposalData = "searchDisputeVO.boxId="+disputeItemBoxIdStr + "&searchDisputeVO.accountCodeId="+accountCodeCreditTab.getValue()+"&searchDisputeVO.disputeId="+disputeId;
	YAHOO.util.Connect.asyncRequest("POST", reconciliationActionUri.addOutstandingDisputeItemsSCOAUri, callback, previousProposalData);
}

//add SCOA
function addSCOAWindows() {
	var handleSubmit = function() {
		addOutstandingDisputeItemsSCOA();
	};
	var handleCancel = function() {	
		this.cancel();
	};
	YAHOO.ccm.container.addDisputeSCOACoding = new YAHOO.widget.Dialog("addDisputeSCOADialog", 
							{ width : "470px",
							  fixedcenter : true,
							  visible : false, 
							  modal : true,
							  constraintoviewport : true,
							  buttons : [{ text:"Confirm", handler:handleSubmit},
								      { text:"Cancel", handler:handleCancel } ]
							});
	YAHOO.ccm.container.addDisputeSCOACoding.render();
};

//update Refile Dispute
function updateRefileDispute() {
	var handleSubmit = function() {
		var thisWindow = this;
		if(disputeStatusId == 31 || disputeStatusId == 30){
			var callback = {
				success: function(o){
		            if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
		                window.location.reload();
		                return;
		            }
		            var data = eval("(" + o.responseText + ")");
					if(data.error){
						alert("Error: " + data.error);
						return;
					}
					if(!data.result){
						alert("Partial reconciliation or payback of this dispute has not been fully completed. This dispute can not be refiled currently.");
						thisWindow.cancel();
						return;
					}
					if(disputeStatusId == 31){
						if(window.confirm("This dispute is reconciled. Please comfirm refile action.")){
							upRefileDispute();
						}else{
							thisWindow.cancel();
						}
					}else{
						upRefileDispute();
					}
				},
				failure:showError
			};
			var pData = "disputeId="+disputeId;
			YAHOO.util.Connect.asyncRequest("POST","validateReconcileByDispute.action", callback, pData);
		}else{
			alert("Partial reconciliation or payback of this dispute has not been fully completed. This dispute can not be refiled currently.");
			thisWindow.cancel();
			return;
			//upRefileDispute();
		}
		$("#updateRefileDispute_c").css("display","none");
	};
	var handleCancel = function() {	
		this.cancel();
		$("#updateRefileDispute_c").css("display","none");
	};
	YAHOO.util.Dom.removeClass("updateRefileDispute", "yui-pe-content");
	YAHOO.ccm.container.updateRefileDispute = new YAHOO.widget.Dialog("updateRefileDispute", 
							{ fixedcenter : true,
							  visible : false, 
							  modal : true,
							  constraintoviewport : true,
							  buttons : [{ text:"Yes", handler:handleSubmit},
								      { text:"No", handler:handleCancel } ]
							});
	YAHOO.ccm.container.updateRefileDispute.render();
	
	$("#updateRefileDispute_c .container-close").bind("click",function(){
		$("#updateRefileDispute_c").css("display","none");
	})
};

//update Cancel Refile Dispute
function updateCancelRefileDispute() {
	var handleSubmit = function() {
		upCancelRefileDispute();
	};
	var handleCancel = function() {	
		this.cancel();
	};
	YAHOO.util.Dom.removeClass("updateCancelRefileDispute", "yui-pe-content");
	YAHOO.ccm.container.updateCancelRefileDispute = new YAHOO.widget.Dialog("updateCancelRefileDispute", 
							{ width : "40em",
							  fixedcenter : true,
							  visible : false, 
							  modal : true,
							  constraintoviewport : true,
							  buttons : [{ text:"Yes", handler:handleSubmit},
								      { text:"No", handler:handleCancel } ]
							});
	YAHOO.ccm.container.updateCancelRefileDispute.render();
};

//delete Reconciliation window
function deleteReconciliation() {
	var handleSubmit = function() {
		eval(targetFun);
	};
	var handleCancel = function() {	
		this.cancel();
	};
	YAHOO.util.Dom.removeClass("deleteReconciliation", "yui-pe-content");
	YAHOO.ccm.container.deleteReconciliation = new YAHOO.widget.Dialog("deleteReconciliation", 
							{ width : "20em",
							  fixedcenter : true,
							  visible : false, 
							  modal : true,
							  constraintoviewport : true,
							  buttons : [{ text:"Confirm", handler:handleSubmit},
								      { text:"Cancel", handler:handleCancel } ]
							});
	YAHOO.ccm.container.deleteReconciliation.render();
};

function editDisputeItem() {
	var handleSubmit = function() {
		var form = document.getElementById("doDisputeItemWinAndLoseForm");
//		if (form["__notes"].value.length < 10) {
//			$(form["__notes"]).removeClass('validation-passed');
//			$(form["__notes"]).addClass('validation-failed');
//			return false;
//		}
//		else {
			$(form["__notes"]).removeClass('validation-failed');
			$(form["__notes"]).addClass('validation-passed');
			if(!uploadFileValidate("Close_dispute")) return false;
			showLoadingModalLayer();
			form.submit();
//		}
	};
	var handleCancel = function() {	
		this.cancel();
	};
	YAHOO.util.Dom.removeClass("editDisputeItem", "yui-pe-content");
	YAHOO.ccm.container.editDisputeItem = new YAHOO.widget.Dialog("editDisputeItem", 
							{ fixedcenter : true,
							  visible : false, 
							  modal : true,
							  constraintoviewport : true,
							  buttons : [/*{ text:"Add", handler:handleAdd},*/
							             { text:"Confirm", handler:handleSubmit},
								      { text:"Cancel", handler:handleCancel } ]
							});
	YAHOO.ccm.container.editDisputeItem.render();
};

function splitDisputeItem() {
	var handleSubmit = function() {
		if(disputeFlagShortpaid!='Y'){
			renderTimeErrorMessage('__show_verify_rrompt','Paid dispute can\'t be closed as dispute win.',3000);
//			document.getElementById('__show_verify_rrompt').innerHTML = "Paid dispute can't be closed as dispute win.";
			return;
		}
		document.getElementById("__button_name").value = "Win";
		if(!uploadFileValidate("splitAndClose")){
			return false;
		}
		var form = document.getElementById("doSplitCloseDisputeItemForm");
//		if (form["__notes_two"].value.length < 10 | (!verificationAmount())) {
//			if(form["__notes_two"].value.length < 10){
//				$(form["__notes_two"]).removeClass('validation-passed');
//				$(form["__notes_two"]).addClass('validation-failed');
//				return false;
//			}
//		}
		if ((!verificationAmount())) {
			return false;
		}
		else {
			$(form["__notes_two"]).removeClass('validation-failed');
			$(form["__notes_two"]).addClass('validation-passed');
			showLoadingModalLayer();
			form.submit();
		}
	};
	var handleSubmit2 = function() {
		document.getElementById("__button_name").value = "Lose";
		var form = document.getElementById("doSplitCloseDisputeItemForm");
		if(!uploadFileValidate("splitAndClose")){
			return false;
		}
//		if (form["__notes_two"].value.length < 10 | (!verificationAmount())) {
//			if (form["__notes_two"].value.length < 10) {
//				$(form["__notes_two"]).removeClass('validation-passed');
//				$(form["__notes_two"]).addClass('validation-failed');
//				return false;
//			}
//		}
		if ((!verificationAmount())) {
			return false;
		}
		else {
			$(form["__notes_two"]).removeClass('validation-failed');
			$(form["__notes_two"]).addClass('validation-passed');
			showLoadingModalLayer();
			form.submit();
		}
	};
	var handleCancel = function() {	
		this.cancel();
	};
	var b_t_s = [];
	/*b_t_s.push({ text:"Add", handler:handleAdd2});*/
//	if(SANINCO.ifAllGranted(Constants.FUNCTION.admin)){
	if(SANINCO.ifAnyGranted(Constants.FUNCTION.admin)){
		b_t_s.push({ text:"Close as Dispute Win", handler:handleSubmit});
	}
	b_t_s.push({ text:"Close as Dispute Lose", handler:handleSubmit2 });
	b_t_s.push({ text:"Cancel", handler:handleCancel });
								      
	YAHOO.util.Dom.removeClass("splitCloseDisputeItem", "yui-pe-content");
	YAHOO.ccm.container.splitDisputeItemWin = new YAHOO.widget.Dialog("splitCloseDisputeItem", 
							{ width : "38em",
							  fixedcenter : true,
							  visible : false, 
							  modal : true,
							  constraintoviewport : true,
							  buttons : b_t_s
							});
	YAHOO.ccm.container.splitDisputeItemWin.render();
};

//Upload complete return to the page
function editDisputeItemCall(flag,errorMessage){
	
	hideLoadingModalLayer();
	if(!flag){
		var data = eval("(" + errorMessage + ")");
        if (data.error) {
            alert(data.error);
        }
	}
	
	refreshOutstandingReserveAmount();
	refreshDisputeAmount();
	YAHOO.ccm.container.splitDisputeItemWin.hide();
	YAHOO.ccm.container.editDisputeItem.hide();
	hideLoadingModalLayer();
	getDisputeItemViewList();
	getDisputeReconciliationList();
};
function clearClose_dispute(){
	 $("#Close_dispute").html("");
	var cdd=1;
	for(cdd = 1 ; cdd < 4 ; cdd ++){
	 var string ="<div id =\"__up_loads_text_dispute1_Number"+cdd+"\" style=\"PADDING-TOP: 3px;height: 19px;\"><div style=\"float:left;\">"
		+"<input style=\"height:19px;width:220px;\" type='text' id=\"__up_loads_text_dispute"+cdd+"\" disabled=\"disabled\">"		                        
		+"</div><div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"
		+"<input id=\"__loads_up_two"+cdd+"\" onchange=\"document.getElementById('__up_loads_text_dispute"+cdd+"').value=this.value;\" class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
		+"<input style=\"height:19px;width:75px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/></span></div>"
		+"<div class=\"approveicon\" onclick=\"deletefile('__up_loads_text_dispute1_Number"+cdd+"');\"></div>"
		+"<div class=\"clear\" ></div></div>";
								
		$("#Close_dispute").append(string);
	}
};
var cd=10;
function handleAdd(){
	  cd++;
	  
	 var string ="<div id =\"__up_loads_text_dispute1_Number"+cd+"\" style=\"PADDING-TOP: 3px;height: 19px;\"><div style=\"float:left;\">"
	+"<input style=\"height:19px;width:220px;\" type='text' id=\"__up_loads_text_dispute"+cd+"\" disabled=\"disabled\">"		                        
	+"</div><div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"
	+"<input id=\"__loads_up_two"+cd+"\" onchange=\"document.getElementById('__up_loads_text_dispute"+cd+"').value=this.value;\" class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
  +"<input style=\"height:19px;width:75px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/></span></div>"
	+"<div class=\"approveicon\" onclick=\"deletefile('__up_loads_text_dispute1_Number"+cd+"');\"></div>"
	+"<div class=\"clear\" ></div></div>";
							
	  $("#Close_dispute").append(string);
	  
}
function clearClose_splitAndClose(){
	$("#splitAndClose").html("");
	var rff=1;
	for (rff = 1 ; rff < 4 ; rff ++){
		var string = "<div id =\"__up_loads_text_splitAndClose_Number"+rff+"\" style=\"PADDING-TOP: 3px;height: 19px;\">"
		   +"<div style=\"float:left;\">"
		   +"<input style=\"height:19px;width:225px;\" type='text' id=\"__up_loads_text_splitAndClose"+rff+"\" disabled=\"disabled\">"
		   +"</div><div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"
		   +"<input id=\"\" onchange=\"document.getElementById('__up_loads_text_splitAndClose"+rff+"').value=this.value;\" class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
		   +"<input style=\"height:19px;width:75px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/></span></div>"
		   +"<div class=\"approveicon\" onclick=\"deletefile('__up_loads_text_splitAndClose_Number"+rff+"');\"></div>"
		   +"<div class=\"clear\" ></div></div>"
		   $("#splitAndClose").append(string);
	}
}
var rf=10;
function handleAdd2(){
	rf++;
	var string = "<div id =\"__up_loads_text_splitAndClose_Number"+rf+"\" style=\"PADDING-TOP: 3px;height: 19px;\">"
	             +"<div style=\"float:left;\">"
	             +"<input style=\"height:19px;width:225px;\" type='text' id=\"__up_loads_text_splitAndClose"+rf+"\" disabled=\"disabled\">"
                 +"</div><div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"
                 +"<input id=\"\" onchange=\"document.getElementById('__up_loads_text_splitAndClose"+rf+"').value=this.value;\" class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
	             +"<input style=\"height:19px;width:75px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/></span></div>"
                 +"<div class=\"approveicon\" onclick=\"deletefile('__up_loads_text_splitAndClose_Number"+rf+"');\"></div>"
                 +"<div class=\"clear\" ></div></div>"
                 $("#splitAndClose").append(string);

}
