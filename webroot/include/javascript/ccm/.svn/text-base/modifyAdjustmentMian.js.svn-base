YAHOO.util.Event.onDOMReady(function () {
	 YAHOO.ccm.container.movePreviousAdjustmentPanel = new YAHOO.widget.Dialog("movePreviousAdjustmentPanel", {
	        width: "60em",
	        fixedcenter: true,
	        visible: false,
	        modal: true,
	        constraintoviewport: true
	    });
	    YAHOO.ccm.container.movePreviousAdjustmentPanel.render();
	 YAHOO.ccm.container.moveCurrentToAdjustmentPanel = new YAHOO.widget.Dialog("moveCurrentToAdjustmentPanel", {
	    	width: "55em",
	    	fixedcenter: true,
	    	visible: false,
	    	modal: true,
	    	constraintoviewport: true
	    });
	    YAHOO.ccm.container.moveCurrentToAdjustmentPanel.render();
	
});
var style = {
	ok : function (id){
		$("#"+id).addClass("validation-passed").removeClass("validation-failed");
	},
	error : function (id){
		$("#"+id).addClass("validation-failed").removeClass("validation-passed");
	}
}
function initModifyAdjustment(){
	clearDetail();
};
var invoiceNumber;
function searchInvoiceCharge(invoiceNumber_txt){
	invoiceNumber = invoiceNumber_txt == null ? $('#invoiceNumber').val() : invoiceNumber_txt;
	if(invoiceNumber == null || invoiceNumber == ""){
		alert('Invoice Number is required!');
		return;
	}
	
	var callback = {
		success: function(o){
			var data = eval("("+o.responseText+")");
			hideLoadingModalLayer();
			clearDetail(data);
			if(data.error){
				$("#invoiceDetailDiv").css("display","none");
				alert(data.error);
				return;
			}else{
				searchAdjustmentAndTaxTable();
			}
		},
		failure: showError
	};
	var starParamer = "searchInvoiceVO.invoiceNumber=" + ccmEscape(invoiceNumber);
	YAHOO.util.Connect.asyncRequest('POST','doSearchInvoiceByInvoiceNumber.action', callback, starParamer);
	showLoadingModalLayer();
	
};
function clearDetail(data){
	if(data == null || data.error){
		$("#invoiceDetailDiv").css("display","none");
		$("#invoiceItemDetailDiv").css("display","none");
		if(data == null){
			$("#invoiceNumber").val("");
		}
	}else{
		$("#invoiceDetailDiv").css("display","block");
		$("#txt_invoiceId").val(data.iid);
		$("#txt_vendor").val(data.vendorName);
		$("#txt_ban").val(data.ban);
		$("#txt_invoiceNumber").val(data.invoiceNumber);
		$("#txt_invoiceDate").val(data.invoiceDate);
		$("#txt_invoicePreviousBalance").val(data.invoicePreviousBalance);
		$("#txt_invoicePreviousPayment").val(data.invoicePreviousPayment);
		$("#txt_invoicePreviousAdjustment").val(data.invoicePreviousAdjustment);
		$("#txt_previousAdjustment").val(data.invoicePreviousAdjustment);
		$("#txt_invoiceBalanceForward").val(data.invoiceBalanceForward);
		$("#txt_invoiceAdjustment").val(data.invoiceAdjustment);
		$("#txt_invoiceLPC").val(data.invoiceLPC);
		$("#txt_invoiceCredit").val(data.invoiceCredit);
		$("#txt_invoiceMrc").val(data.invoiceMrc);
		$("#txt_invoiceOcc").val(data.invoiceOcc);
		$("#txt_invoiceUsage").val(data.invoiceUsage);
		$("#txt_invoiceTax").val(data.invoiceTax);
		$("#txt_invoiceCurrentDue").val(data.invoiceCurrentDue);
		$("#txt_invoiceTotalDue").val(data.invoiceTotalDue);
	}
};
function onMovePreviousAdjustmentPanel(){
	YAHOO.ccm.container.movePreviousAdjustmentPanel.show();
	cancel();
};
function cancel(){
	$("#txt_currentInvoice").val("");
	$("#VL_SCOAList_input").val("");
	$("#txt_notes").val("");
	$("#modify_Adjustment_tax_1").html("");
	$('#txt_currentInvoice_1').val("");
	$('#txt_notes_1').val("");
	 
	style.ok("VL_SCOAList_input");
	style.ok("txt_currentInvoice_1");
 	style.ok("txt_notes_1");
	$.each($(".mustWrite"),function (i,n){
		if(n.id){
			style.ok(n.id);
		}
	});
 
	 
};
function addTax(){
	taxTable = modifyAdjustment("modify_Adjustment_tax_1",VL_ScoaCodeList,VL_TaxCodeList,"");
};

function saveModifyPreviousAdjustment(){
	
	if(!saveValidate()){
		return;
	}
	var callback = {
		success: function(o){
			var data = eval("("+o.responseText+")");
			hideLoadingModalLayer();
			clearDetail(data);
			YAHOO.ccm.container.moveCurrentToAdjustmentPanel.hide();
			YAHOO.ccm.container.movePreviousAdjustmentPanel.hide();
			if(data.error){
				alert(data.error);
				return;
			}
			if(invoiceNumber != null && invoiceNumber != ""){
				searchInvoiceCharge(invoiceNumber);
				return;
			}
		},
		failure: showError
	};
	var starParamer = getSaveParamers();
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest('POST','doSaveModifyPreviousAdjustment.action', callback, starParamer);
	
};

function getSaveParamers(){
	var postData = "searchInvoiceVO.invoiceId="+$('#txt_invoiceId').val()
				 + "&searchInvoiceVO.invoiceNumber="+ccmEscape(invoiceNumber)
				 + "&searchInvoiceVO.invoiceAdjustment="+$('#txt_currentInvoice').val()
				 + "&searchInvoiceVO.scoaCodeId="+SCOACodeing.getValue()
				 + "&searchInvoiceVO.note=" + ccmEscape($('#txt_notes').val())
				 + "&searchInvoiceVO.single=" + splitValue();
	return postData;
}

function splitValue(){
	var value = "";
	var html = $("#modify_Adjustment_tax_1").html();
	if(html != ""){
		var array = modifyAdjustment.getParam("modify_Adjustment_tax_1");
		for (var i = 0; i < array.length; i++) {
			if(i != 0) value += ",";
			value = value + array[i][0] + "," + array[i][1]+","+array[i][2]+","+array[i][3];
		}
	}
	return value;
}
function saveValidate(){
	var boo = true;
	if($('#txt_invoiceId').val() == null || $('#txt_invoiceId').val() == ""){
		alert("invoice id is null!");
		boo = false;
	}
	if(!$("#VL_SCOAList_input").val() || $("#VL_SCOAList_input").val() == ""){
		style.error("VL_SCOAList_input");
		boo = false;
	}else{
		style.ok("VL_SCOAList_input");
	}
	$.each($(".mustWrite"),function (i,n){
		if(!n.value && n.id){
			style.error(n.id);
			boo = false;
		}else{
			style.ok(n.id);
		}
	});
	
	var html = $("#modify_Adjustment_tax_1").html();
	if(html != ""){
		var array = modifyAdjustment.getParam("modify_Adjustment_tax_1");
		for (var i = 0; i < array.length; i++) {
			if(array[i][0] == "" || array[i][1] == "" || array[i][2] == "" || array[i][3] == ""){
				if (boo) {
					boo = false;
					alert("tax is required!");
				}
			}
			if(isNaN(array[i][0])){
				boo = false;
				alert("tax amount is number!");
			}
		}
	}
	
	return boo;
};

function searchAdjustmentAndTaxTable(){
	showAdjustmentAndTaxPage = new SANINCO.Page("showAdjustmentAndTaxTable","showAdjustmentAndTaxPage",{
		sortingField : "ii.item_name",
		recordText: '', 
		sortingDirection : "asc",
		vo : "searchInvoiceVO",
		paginationDiv: "showAdjustmentAndTax_pageTable",
		recPerArray : [5,10,15,20,30],
		dataUri : "doSearchInvoiceDetailByInvoiceNumber.action",
		totalPageUri: "doSearchInvoiceDetailByInvoiceNumberTotalPageNo.action",
		cols : [
		        { title : "",dataField: function(o){ return "<input type=\"radio\" class='noBorderRadioButton' name=\"selectBox\" onclick=\"selectItem("+o.proposal_id+","+o.invoice_item_id+","+o.item_type_id+",'"+o.payment_amount+"')\"/>";}
			    },{ title : "Proposal Id",dataField: "proposal_id",sort : "p.id",display:"none"
			    },{ title : "Invoice Item Id",dataField: "invoice_item_id",sort : "ii.id",display:"none"
				},{ title : "item type id",dataField: "item_type_id",sort : "ii.item_type_id",display:"none"
				},{ title : "Item Type Name",dataField: "item_type_name",sort : "it.item_type_name"
				},{ className : "table_td_br",width:"180px",title : "Item",dataField:"itemName",sort : "ii.item_name",display:"none"
				},{ title : "Amount",dataField: "item_amount",sort : "ii.item_amount",display:"none"
			    },{ title : "Payment Amount",dataField: "payment_amount",sort : "p.payment_amount"
			    },{ title : "Dispute Amount",dataField:"dispute_amount",sort : "p.dispute_amount"
			    },{ title : "Credit Amount",dataField:"credit_amount",sort : "p.credit_amount"
				},{ title : "SCOA",dataField:"scoa"
				},{ title : "Service Type",dataField:"service_type"
			    },{ title : "Charge Type",dataField:"charge_type"
			    },{ title : "Notes",dataField:"notes"
			    }
		        ]
	});
	
	showAdjustmentAndTaxPage.addBeforeSearch(function(m,t){
		clearSelectItem();
	});
	showAdjustmentAndTaxPage.addTotalSuccessEvent(function(data){
		if(data.totalResultNo >= 0 && !data.error){
			$("#invoiceItemDetailDiv").css("display","block");
		}
	});
	
	filter = new SANINCO.Filter();
    filter.addEditeEvent(function(){showAdjustmentAndTaxPage.start();});
    filter.add('it.item_type_name', 'String');
    filter.add('ii.item_name', 'String');
    filter.add('ii.item_amount', 'Number');
    filter.add('p.payment_amount', 'Number');
    filter.add('p.dispute_amount', 'Number');
    filter.add('p.credit_amount', 'Number');
    showAdjustmentAndTaxPage.setFilter(filter);
	
	showAdjustmentAndTaxPage.voParam = {
			invoiceNumber : ccmEscape($('#invoiceNumber').val())
	};
	showAdjustmentAndTaxPage.start();
}

var selectProposalId;
var selectInvoiceItemId;
var selectItemTypeId;
var selectItemAmount;
function selectItem(proposalId,invoiceItemId,itemTypeId,itemAmount){
	selectProposalId = proposalId;
	selectInvoiceItemId = invoiceItemId;
	selectItemTypeId = itemTypeId;
	selectItemAmount = itemAmount;
};
function clearSelectItem() {
	selectProposalId = null;
	selectInvoiceItemId = null;
	selectItemTypeId = null;
	selectItemAmount = null;
}


function onMoveCurrentToAdjustmentPanel(){
	if(selectProposalId == null || selectProposalId == ""){
		alert("no select!");
		return;
	}
	if(selectItemTypeId == 16 || String(selectItemTypeId).substring(0,1) == "6"){
		$("#labelName").html("Adjustment Amount:");
		$("#labelAdjustmentOrTax").html("Previous Invoice Adjustment:");
	}else{
		$("#labelName").html("Tax Amount:");
		$("#labelAdjustmentOrTax").html("Previous Invoice Tax:");
	}
	$("#txt_adjustmentOrTaxAmount").val(selectItemAmount);
	cancel();
	YAHOO.ccm.container.moveCurrentToAdjustmentPanel.show();
	
};
function saveMoveCurrentToAdjustmentValidate(){
	var boo = true;
	if($('#txt_currentInvoice_1').val() == "" || $('#txt_currentInvoice_1').val() == null){
		style.error("txt_currentInvoice_1");
		boo = false;
	}else{
		style.ok("txt_currentInvoice_1");
	}
	if($('#txt_notes_1').val() == "" || $('#txt_notes_1').val() == null){
		style.error("txt_notes_1");
		boo = false;
	}else{
		style.ok("txt_notes_1");
	}
	return boo;
}
function saveMoveCurrentToAdjustment(){
	
	if(!saveMoveCurrentToAdjustmentValidate()){
		return;
	}
	var callback = {
		success: function(o){
			var data = eval("("+o.responseText+")");
			hideLoadingModalLayer();
			YAHOO.ccm.container.moveCurrentToAdjustmentPanel.hide();
			YAHOO.ccm.container.movePreviousAdjustmentPanel.hide();
			clearSelectItem();
//			clearDetail(data);
			if(data.error){
				alert(data.error);
				return;
			}
			if(invoiceNumber != null && invoiceNumber != ""){
				searchInvoiceCharge(invoiceNumber);
				return;
			}
		},
		failure: showError
	};
	var starParamer = "searchInvoiceVO.invoiceId="+$('#txt_invoiceId').val()
	 + "&searchInvoiceVO.proposalId="+selectProposalId
	 + "&searchInvoiceVO.invoiceNumber="+ccmEscape(invoiceNumber)
	 + "&searchInvoiceVO.invoiceItemId="+selectInvoiceItemId
	 + "&searchInvoiceVO.itemTypeId="+selectItemTypeId
	 + "&searchInvoiceVO.amount=" + ($('#txt_currentInvoice_1').val() == ""?0:$('#txt_currentInvoice_1').val())
	 + "&searchInvoiceVO.note=" + ccmEscape($('#txt_notes_1').val());
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest('POST','doSaveMoveCurrentToAdjustment.action', callback, starParamer);
	
};