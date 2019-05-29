var addInvoiceStVUri = "addInvoiceStV.action";
var deleteInvoiceStVUri = "deleteInvoiceStV.action";
var findBarCodeUri = "findBarCode.action";

var getBanListByVendorId = function(selIndex){
	var sVendorId = form0_searchOperationsVO_vendorId.getValue();
	if(sVendorId == ''){
		updateDropdownList([], "id", "no", 'form0_banId',selIndex, {key:"all",value:"All"});
		return;
	}
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
			updateDropdownList(data, "id", "no", 'form0_banId',selIndex, {key:"all",value:"All"});
		},
		failure: showError
	};
	var pdata = "selVendorId=" + sVendorId;
	YAHOO.util.Connect.asyncRequest('POST' , "getBanListByVendorId.action" , callback , pdata); 
};


var updateDropdownList = function(data, keyName, valueName, ddlId, selIndex, header){
	var selo = YAHOO.util.Dom.get(ddlId);
	while(selo.length>0) 
		selo.remove(0);
	if(header!=null) selo.options[selo.options.length] = new Option(header.value, header.key);
	for(var i = 0; i<data.length; i++){
		selo.options[selo.options.length]=new Option(eval('data[i].'+valueName),eval('data[i].'+keyName));
	}
	selo.selectedIndex = selIndex;
};


YAHOO.util.Event.onDOMReady(function () {
	var tabView = new YAHOO.widget.TabView('demoTabs1');
	$("#demoTabs1 li a:eq(0)").click();
	tabView.selectTab(0);
	tabView.addListener('activeIndexChange', function(e){
		if (createEmailsTableStateTimer && e.prevValue == 1) {
			clearTimeout(createEmailsTableStateTimer)
		};
	});

	var handleSubmit = function() {
		if(!FIC_checkForm('form0')) return;
		YAHOO.util.Connect.setForm("form0");
		YAHOO.util.Connect.asyncRequest('POST' , "saveOperations.action" , {
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
				YAHOO.ccm.container.registerInvoiceDialog.hide();
				document.getElementById("registerInvoiceDialogSuccess").style.display = "block";
			},
			failure: showError
		}); 
	};
	var handleCancel = function() {
		this.cancel();
	};
	YAHOO.util.Dom.removeClass("register_invoice_dialog", "yui-pe-content");
	
	YAHOO.ccm.container.registerInvoiceDialog = new YAHOO.widget.Dialog("register_invoice_dialog", 
							{ width : "36em",
							  fixedcenter : true,
							  visible : false, 
							  constraintoviewport : true,
							  buttons : [ { text:"Submit", handler:handleSubmit },
								      { text:"Cancel", handler:handleCancel } ]
							});
	YAHOO.ccm.container.registerInvoiceDialog.render();
});

YAHOO.util.Event.onDOMReady(function () {
	var handleSubmit = function() {
		eval(targetFun);
	};
	var handleCancel = function() {
		this.cancel();
	};
	YAHOO.util.Dom.removeClass("invoice_clear_window", "yui-pe-content");
	
	YAHOO.ccm.container.invoiceClearWindow = new YAHOO.widget.Dialog("invoice_clear_window", 
							{ width : "30em",
							  fixedcenter : true,
							  visible : false, 
							  constraintoviewport : true,
							  modal : true,
							  buttons : [ { text:"Delete", handler:handleSubmit },
								      { text:"Cancel", handler:handleCancel } ]
							});
	YAHOO.ccm.container.invoiceClearWindow.render();
});

YAHOO.util.Event.onDOMReady(function () {
	var handleSubmit = function() {
		verificationBarCode('__bar_code','__show_bar_code',false);
	};
	var handleCancel = function() {
		this.cancel();
	};
	YAHOO.util.Dom.removeClass("invoice_entry_window", "yui-pe-content");
	
	YAHOO.ccm.container.invoiceEntryWindow = new YAHOO.widget.Dialog("invoice_entry_window", 
							{ width : "35em",
							  fixedcenter : true,
							  visible : false, 
							  constraintoviewport : true,
							  modal : true,
							  buttons : [ { text:"Submit", handler:handleSubmit },
								      { text:"Cancel", handler:handleCancel } ]
							});
	YAHOO.ccm.container.invoiceEntryWindow.render();
});

function showClearWindow(fun,bo){
	targetFun = fun;
	if(bo){
		document.getElementById("registerInvoiceDialogSuccess").style.display = "none";
		document.getElementById('__bar_code_clear').value = "";
		document.getElementById('__show_invoice_st_data').innerHTML = "";
		document.getElementById('__show_invoice_st_data_prompt').innerHTML = "";
	}
	YAHOO.ccm.container.invoiceClearWindow.show();
}

function findData(){
	if(!FIC_checkForm('form2')) return;
	verificationBarCode('__bar_code_clear','__show_invoice_st_data_prompt',true);
}

function deleteInvoiceSt(barCode){
	var callback = {
		success: function(){
			document.getElementById("registerInvoiceDialogSuccess").style.display = "block";
			YAHOO.ccm.container.invoiceClearWindow.hide();
		},
		failure: showError
	};
	
	YAHOO.util.Connect.asyncRequest('POST', deleteInvoiceStVUri + "?operationsVO.barCode=" + barCode, callback);
}

function showEntryWindow(){
	document.getElementById("registerInvoiceDialogSuccess").style.display = "none";
	document.getElementById('__bar_code').value = "";
	document.getElementById('__invoice_balance_forward').value = "";
	document.getElementById('__invoice_current_due').value = "";
	document.getElementById('__invoice_total_due').value = "";
	document.getElementById('__invoice_due_date').value = "";
	document.getElementById('__invoice_previous_payment').value = "";
	YAHOO.ccm.container.invoiceEntryWindow.show();
}

function updateInvoiceEntry(){
	if((!FIC_checkForm('form1')) | (document.getElementById('__bar_code').innerHTML == "The Invoice is not Registered.")) return;
	
	var callback = {
		success: function(){
			document.getElementById("registerInvoiceDialogSuccess").style.display = "block";
			YAHOO.ccm.container.invoiceEntryWindow.hide();
		},
		failure: showError
	};

	previousPostedData = composePostDataFromUi();
	YAHOO.util.Connect.asyncRequest('POST' , addInvoiceStVUri , callback , previousPostedData ); 
}

function composePostDataFromUi(){
	var postData = "";
	
	if(YAHOO.util.Dom.get('__bar_code').value!="") postData += ("operationsVO.barCode="+YAHOO.util.Dom.get('__bar_code').value); 
	if(YAHOO.util.Dom.get('__invoice_balance_forward').value!="") postData += ("&operationsVO.invoiceBalanceForward="+YAHOO.util.Dom.get('__invoice_balance_forward').value);	
	if(YAHOO.util.Dom.get('__invoice_current_due').value!="") postData += ("&operationsVO.invoiceCurrentDue="+YAHOO.util.Dom.get('__invoice_current_due').value); 
	if(YAHOO.util.Dom.get('__invoice_total_due').value!="") postData += ("&operationsVO.invoiceTotalDue=" + YAHOO.util.Dom.get('__invoice_total_due').value);
	
	if(YAHOO.util.Dom.get('__invoice_previous_payment').value!="") postData += ("&operationsVO.invoicePreviousPayment=" + YAHOO.util.Dom.get('__invoice_previous_payment').value);
	if(YAHOO.util.Dom.get('__invoice_due_date').value!="") postData += ("&operationsVO.invoiceDueDate=" + YAHOO.util.Dom.get('__invoice_due_date').value);

	return postData;
}

//Send to AP

function sendToAP(){
	if((!FIC_checkForm('form1')) | (document.getElementById('__bar_code').innerHTML == "The Invoice is not Registered.")) return;
	
	var callback = {
		success: function(){
			document.getElementById("registerInvoiceDialogSuccess").style.display = "block";
			YAHOO.ccm.container.invoiceEntryWindow.hide();
		},
		failure: showError
	};

	previousPostedData = composePostDataFromUi();
	YAHOO.util.Connect.asyncRequest('POST' , addInvoiceStVUri , callback , previousPostedData ); 
}

//Load Remittance From AP


function loadAPRemittance(){
	if((!FIC_checkForm('form1')) | (document.getElementById('__bar_code').innerHTML == "The Invoice is not Registered.")) return;
	
	var callback = {
		success: function(){
			document.getElementById("registerInvoiceDialogSuccess").style.display = "block";
			YAHOO.ccm.container.invoiceEntryWindow.hide();
		},
		failure: showError
	};

	previousPostedData = composePostDataFromUi();
	YAHOO.util.Connect.asyncRequest('POST' , addInvoiceStVUri , callback , previousPostedData ); 
}




//Verification Bar Code
function verificationBarCode(barId,showDivId,bo){
	var barCode = document.getElementById(barId).value;
	var callback = {
		success: function(o){
			if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
				window.location.reload();
				return;
			}
			var data = eval("("+o.responseText+")");
			if(data.error){
				document.getElementById(showDivId).innerHTML = "The Invoice is not Registered.";
				return;
			}
			var rows = data.data;
			document.getElementById(showDivId).innerHTML = "";
			if(bo){
				var str = "";
				for (var i = 0; i < rows.length; i++) {
					var row = rows[i];
					str += "<br>Vendor Name : "; 
					str += row.vendor == "null" ? "" : row.vendor;
					str += "<br>BAN : ";
					str += row.ban == "null" ? "" : row.ban;
					str += "<br>Invoice Date : ";
					str += row.invoiceDate == "null" ? "" : row.invoiceDate;
					str += "<br>Invoice Due Date : ";
					str += row.invoiceDueDate == "null" ? "" : row.invoiceDueDate;
					str += "<br>Current Due : ";
					str += row.currentDue == "null" ? "" : row.currentDue;
					str += "<br>Total Due : ";
					str += row.totalDue == "null" ? "" : row.totalDue;
					str += "<br><br>";
				}
				document.getElementById('__show_invoice_st_data').innerHTML = str;
				document.getElementById('__show_invoice_st_data_prompt').innerHTML = "Make sure to delete data";
				document.getElementById('yui-gen2-button').innerHTML = "Confirm";
				showClearWindow('deleteInvoiceSt(\''+barCode+'\')',false);
			}else{
				updateInvoiceEntry();
			}
		},
		failure: showError
	};

	YAHOO.util.Connect.asyncRequest('POST' , findBarCodeUri + "?operationsVO.barCode=" + barCode, callback )
}



//Send AP Sending Request:
function sendAPSendingRequest(){
    var callback = {
        success: showSentAlert,
        failure: showError
    };
    
    var sendAPSendingRequestData = "sendRequest=" + 1;
	
    YAHOO.util.Connect.asyncRequest("POST", "sendAPSendingRequest.action", callback, sendAPSendingRequestData);
}


//Send AP Loading Request:
function sendAPLoadingRequest(){
    var callback = {
        success: showLoadedAlert,
        failure: showError
    };
    
    var sendAPLoadingRequestData = "loadRequest=" + 1;
    
    YAHOO.util.Connect.asyncRequest("POST", "sendAPLoadingRequest.action", callback, sendAPLoadingRequestData);
}


//Show Sent Alert:
function showSentAlert(){
 alert("AP feed file has been generated successfully!");
 return;
}

//Show Load Alert:
function showLoadedAlert(){
 alert("AP remittance file has been loaded successfully!");
 return;
}
