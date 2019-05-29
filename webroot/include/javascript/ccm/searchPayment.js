var pageNo = 1;
var recPerPage = 10;
var totalPageNo;
var totalResultNo;
var currentSortingFieldName = "payment_transaction_number";
var currentSortingDirection = "asc";
var getPaymentTotalPageNoUri = "doGetPaymentSearchTotalPageNo.action";
var searchPaymentUri = "doSearchPayment.action";

function composePostDataFromUi(){
	var postData = ("searchPaymentVO.sortField="+currentSortingFieldName+"&searchPaymentVO.sortingDirection="+currentSortingDirection+"&searchPaymentVO.pageNo="+pageNo+"&searchPaymentVO.recPerPage="+recPerPage);
	
    if(YAHOO.util.Dom.get('searchPaymentVO_vendorId').value!="all") postData += ("&searchPaymentVO.vendorId="+YAHOO.util.Dom.get('searchPaymentVO_vendorId').value); 
	if(YAHOO.util.Dom.get('searchPaymentVO_banId').value!="all") postData += ("&searchPaymentVO.banId="+YAHOO.util.Dom.get('searchPaymentVO_banId').value);
	if(YAHOO.util.Dom.get('searchPaymentVO_leastAmount').value!="") postData += ("&searchPaymentVO.leastAmount=" + YAHOO.util.Dom.get('searchPaymentVO_leastAmount').value);
	if(YAHOO.util.Dom.get('searchPaymentVO_greatestAmount').value!="") postData += ("&searchPaymentVO.greatestAmount=" + YAHOO.util.Dom.get('searchPaymentVO_greatestAmount').value);
	if(YAHOO.util.Dom.get('searchPaymentVO_currencyId').value!="all") postData += ("&searchPaymentVO.currencyId=" + YAHOO.util.Dom.get('searchPaymentVO_currencyId').value);
	if(YAHOO.util.Dom.get('searchPaymentVO_statusId').value!="all") postData += ("&searchPaymentVO.statusId=" + YAHOO.util.Dom.get('searchPaymentVO_statusId').value);
	if(YAHOO.util.Dom.get('searchPaymentVO_paidDateStartsOn').value!="") postData += ("&searchPaymentVO.paidDateStartsOn=" + YAHOO.util.Dom.get('searchPaymentVO_paidDateStartsOn').value);
	if(YAHOO.util.Dom.get('searchPaymentVO_paidDateEndsOn').value!="") postData += ("&searchPaymentVO.paidDateEndsOn=" + YAHOO.util.Dom.get('searchPaymentVO_paidDateEndsOn').value);
	if(YAHOO.util.Dom.get('searchPaymentVO_paidWithinNumOfDays').value!="") postData += ("&searchPaymentVO.paidWithinNumOfDays=" + YAHOO.util.Dom.get('searchPaymentVO_paidWithinNumOfDays').value);
	if(YAHOO.util.Dom.get('searchPaymentVO_paymentDateStartsOn').value!="") postData += ("&searchPaymentVO.paymentDateStartsOn=" + YAHOO.util.Dom.get('searchPaymentVO_paymentDateStartsOn').value);
	if(YAHOO.util.Dom.get('searchPaymentVO_paymentDateEndsOn').value!="") postData += ("&searchPaymentVO.paymentDateEndsOn=" + YAHOO.util.Dom.get('searchPaymentVO_paymentDateEndsOn').value);
	if(YAHOO.util.Dom.get('searchPaymentVO_paymentWithinPastNumOfDays').value!="") postData += ("&searchPaymentVO.paymentWithinPastNumOfDays=" + YAHOO.util.Dom.get('searchPaymentVO_paymentWithinPastNumOfDays').value);
	if(YAHOO.util.Dom.get('searchPaymentVO_paymentWithinNextNumOfDays').value!="") postData += ("&searchPaymentVO.paymentWithinNextNumOfDays=" + YAHOO.util.Dom.get('searchPaymentVO_paymentWithinNextNumOfDays').value);
	if(YAHOO.util.Dom.get('searchPaymentVO_paymentTransactionNumber').value!="") postData += ("&searchPaymentVO.paymentTransactionNumber=" + YAHOO.util.Dom.get('searchPaymentVO_paymentTransactionNumber').value);
	if(YAHOO.util.Dom.get('searchPaymentVO_referenceNumber').value!="") postData += ("&searchPaymentVO.referenceNumber=" + YAHOO.util.Dom.get('searchPaymentVO_referenceNumber').value);
	if(YAHOO.util.Dom.get('searchPaymentVO_paymentApproverId').value!="all") postData += ("&searchPaymentVO.paymentApproverId=" + YAHOO.util.Dom.get('searchPaymentVO_paymentApproverId').value);
	if(filter.getCause()!="") postData += ("&searchPaymentVO.filter=" + filter.getCause());
	return postData;
}

function requestData(){
	if(!validateFields()) return;
	var callback = {
		success: renderPage,
		failure: showError
	};

	previousPostedData = composePostDataFromUi();
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest('POST' , searchPaymentUri , callback , previousPostedData ) 
}

function validateFields(){
	var formatValid = FIC_checkForm("form0");
	if(formatValid){
		if(getCheckedValue(form0.selectPaymentDateRadio)=="DATEFRAME"){
			form0.searchPaymentVO_paymentWithinPastNumOfDays.value="";
			form0.searchPaymentVO_paymentWithinNextNumOfDays.value="";
		}else{
		if(getCheckedValue(form0.selectPaymentDateRadio)=="WITHINPAST"){
			form0.searchPaymentVO_paymentDateStartsOn.value="";
			form0.searchPaymentVO_paymentDateEndsOn.value="";
			form0.searchPaymentVO_paymentWithinNextNumOfDays.value="";
			}		
		else {
			form0.searchPaymentVO_paymentDateStartsOn.value="";
			form0.searchPaymentVO_paymentDateEndsOn.value="";
			form0.searchPaymentVO_paymentWithinPastNumOfDays.value="";
		}	
		}
		
		if(getCheckedValue(form0.selectPaidDateRadio)=="DATEFRAME"){
			form0.searchPaymentVO_paidWithinNumOfDays.value="";
		}else{
			form0.searchPaymentVO_paidDateStartsOn.value="";
			form0.searchPaymentVO_paidDateEndsOn.value="";
		}

	}
	return formatValid;
}

function getCheckedValue(radioObj) {
	if(!radioObj)
		return "";
	var radioLength = radioObj.length;
	if(radioLength == undefined)
		if(radioObj.checked)
			return radioObj.value;
		else
			return "";
	for(var i = 0; i < radioLength; i++) {
		if(radioObj[i].checked) {
			return radioObj[i].value;
		}
	}
	return "";
}

function showLoadingImg(){
	if(loadingImgDiv) loadingImgDiv.style.display="block";
}

function hideLoadingImg(){
	if(loadingImgDiv) loadingImgDiv.style.display="none";
}

//judgDate by xinyu.Liu beijing time 2010.5.12
function judgDate(){
	var bo = true;
	var timePast = YAHOO.util.Dom.get('searchPaymentVO_paidWithinNumOfDays').value;
	var timePastTwo = YAHOO.util.Dom.get('searchPaymentVO_paymentWithinPastNumOfDays').value;
	var nodes = YAHOO.util.Selector.query(".validate-date-ca",'form0');
	
	for(var d = 0; d<nodes.length; d++){
		if(d+1 < nodes.length){
			if((nodes[d].value!="") && (nodes[d+1].value!="")){
				if(!correct(nodes[d].value,nodes[d+1].value)){
					removeClassName(nodes[d].id,'validation-passed');
					addClassName(nodes[d].id,'validation-failed');
					removeClassName(nodes[d+1].id,'validation-passed');
					addClassName(nodes[d+1].id,'validation-failed');
					bo = false;
				}
			}
		}
		if(!isValidDate(nodes[d].value)) {
			removeClassName(nodes[d].id,'validation-passed');
			addClassName(nodes[d].id,'validation-failed');
			bo = false;
		}
	}
	
	if(timePast < 0 || timePast > 10000) {
		removeClassName('searchPaymentVO_paidWithinNumOfDays','validation-passed');
		addClassName('searchPaymentVO_paidWithinNumOfDays','validation-failed');
		bo = false;
	}
	
	if(timePastTwo < 0 || timePastTwo > 10000) {
		removeClassName('searchPaymentVO_paymentWithinPastNumOfDays','validation-passed');
		addClassName('searchPaymentVO_paymentWithinPastNumOfDays','validation-failed');
		bo = false;
	}
	return bo;
}

function startSearch(){
	// Validation Date
	if((!validateFields()) | (!judgDate())) return;
	pageNo = 1;
	if(_recPerPageSelect) recPerPage = _recPerPageSelect.value;
	var callback = {
		success: renderTable,
		failure: showError
	};

	previousPostedData = composePostDataFromUi();
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest('POST' , getPaymentTotalPageNoUri , callback , previousPostedData ) 
}

function renderTable(o){
	if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
		window.location.reload();
		return;
	}
	var data = eval("("+o.responseText+")");
	hideLoadingModalLayer();
	if(data.error){
		_dataTable.innerHTML = "Error: " + data.error;
		_paginationTable.style.display="none";
		return;
	}
	if(data.totalResultNo==0){
		totalResultNo=0;
		totalPageNo = data.totalPageNo;
		_dataTable.innerHTML = "No records found.";
		__curPageNo.value = 0;
		__totalPageNo.innerHTML = "0";
		_paginationTable.style.display="none";
	}else{
		totalResultNo=data.totalResultNo;
		totalPageNo = data.totalPageNo;
		__curPageNo.value = 1;
		__totalPageNo.innerHTML = totalPageNo;
		_paginationTable.style.display="block";
		requestData();
	}
}

function sort(e, sortingFieldName){
	if(evaluateQueyChange()) return;
	if(currentSortingFieldName!=sortingFieldName){
		currentSortingFieldName = sortingFieldName;
		currentSortingDirection="asc";
	}else{
		if(currentSortingDirection=="asc") currentSortingDirection="desc";
		else currentSortingDirection="asc";
	}
	requestData();
}

function getSortingDirectionString(fieldName){
	if(currentSortingFieldName==fieldName){
		if(currentSortingDirection=='asc') return '<img src="include/images/upsort.gif">';
		else return '<img src="include/images/downsort.gif">';
	}else return '';
}

function renderPage(o){
	if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
		window.location.reload();
		return;
	}
	var data = eval("("+o.responseText+")");
	if(data.error){
		_dataTable.innerHTML = "Error: " + data.error;
		_paginationTable.style.display="none";
		hideLoadingModalLayer();
		return;
	}
	var t = 'Search Results: Items '+data.begin+' - '+data.end+' of '+totalResultNo
			+ '<table class="gridview" cellspacing="0" rules="all" border="1"'
			+ 'id="ctl00_MainContent_SearchPaymentControl1_GridView1"'
			+ 'style="width: 100%; border-collapse: collapse;">'
			+ '<tr class="listViewThLinkS1">'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'payment_transaction_number\');">Transaction Number'
			+ getSortingDirectionString('payment_transaction_number')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("payment_transaction_number")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'payment_transaction_number\',\'Transaction Number\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'invoice_number\');">Invoice Number'
			+ getSortingDirectionString('invoice_number')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("invoice_number")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'invoice_number\',\'Invoice Number\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'invoice_due_date\');">Due Date'
			+ getSortingDirectionString('invoice_due_date')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("invoice_due_date")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'invoice_due_date\',\'Due Date\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'vendor_name\');">Vendor'
			+ getSortingDirectionString('vendor_name')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("vendor_name")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'vendor_name\',\'Vendor\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'account_number\');">BAN'
			+ getSortingDirectionString('account_number')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("account_number")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'account_number\',\'BAN\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'payment_status_name\');">Status'
			+ getSortingDirectionString('payment_status_name')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("payment_status_name")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'payment_status_name\',\'Status\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'payment_amount\');">Payment Amount'
			+ getSortingDirectionString('payment_amount')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("payment_amount")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'payment_amount\',\'Payment Amount\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'payment_date\');">Payment Date'
			+ getSortingDirectionString('payment_date')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("payment_date")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'payment_date\',\'Payment Date\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'paid_date\');">Paid Date'
			+ getSortingDirectionString('paid_date')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("paid_date")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'paid_date\',\'Paid Date\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'payment_reference_code\');">Reference Number'
			+ getSortingDirectionString('payment_reference_code')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("payment_reference_code")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'payment_reference_code\',\'Reference Number\');">'+'</th>'
			+ '</tr>';
	 var rows = data.data;
	 for(var i = 0; i<rows.length; i++){
	 	var row = rows[i];
	 	t += ('<tr class="'+ (i%2==0?'evenListRowS1':'even')+'" style="height: 30px;">'
	 	    + '<td><a target=\"_blank\" href="viewPaymentDetails.action?paymentId='+row.id+'">'+row.tx+'</a></td>'
			+ '<td><a target=\"_blank\" href="viewInvoiceDetails.action?invoiceId='+row.iid+'">'+row.no+'</a></td>'
			+ '<td align="left">'+row.due+'</td>'
			+ '<td align="left">'+row.vendor+'</td>'
			+ '<td align="left">'+row.ban+'</td>'
			+ '<td align="left">'+row.status+'</td>'
			+ '<td align="left">'+row.amount+'</td>'
			+ '<td align="left">'+row.pdate+'</td>'
			+ '<td align="left">'+row.pidate+'</td>'
			+ '<td align="left">'+row.ref+'</td>'
			+ '</tr>');
	 }
	 t += '</table>';
    _dataTable.innerHTML = t;
    __curPageNo.value = pageNo;
	 hideLoadingModalLayer();
}

function isQueryChanged(){
	if(!validateFields()) return false;
	else{
		return (previousPostedData!=composePostDataFromUi())
	}
}

function evaluateQueyChange(){
	if(isQueryChanged()) {
		startSearch();
		return true;
	}else return false;
}

function getNextPage(){
	if(evaluateQueyChange()) return;
	if((pageNo+1)>totalPageNo) return;
	pageNo++;
	requestData();
}

function getPrevPage(){
	if(evaluateQueyChange()) return;
	if((pageNo-1)<1) return;
	pageNo--;
	requestData();
}

function getFirstPage(){
	if(evaluateQueyChange()) return;
	pageNo = 1;
	requestData();
}

function getLastPage(){
	if(evaluateQueyChange()) return;
	pageNo = totalPageNo;
	requestData();
}

function getPage(){
	if(window.event.keyCode == 13){
		if(evaluateQueyChange()) return;
		if(__curPageNo.value==pageNo) return;
		pageNo = __curPageNo.value;
		requestData();
	}
}

////Get BAN list by Vendor id ////
function getBanListByVendorId(selIndex){
	if(YAHOO.util.Dom.get('searchPaymentVO_vendorId').value=='all'){
		updateDropdownList([], "id", "no", 'searchPaymentVO_banId',selIndex, {key:"all",value:"All"});
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
				hideLoadingImg();
				alert("Error: " + data.error);
				return;
			}
			updateDropdownList(data, "id", "no", 'searchPaymentVO_banId',selIndex, {key:"all",value:"All"});
			hideLoadingImg();
		},
		failure: showError
	};

	var pdata = "selVendorId=" + YAHOO.util.Dom.get('searchPaymentVO_vendorId').value;
	showLoadingImg();
	YAHOO.util.Connect.asyncRequest('POST' , "getBanListByVendorId.action" , callback , pdata); 
}

function updateDropdownList(data, keyName, valueName, ddlId, selIndex, header){
	var selo = YAHOO.util.Dom.get(ddlId);
	while(selo.length>0) 
		selo.remove(0);
	if(header!=null) selo.options[selo.options.length] = new Option(header.value, header.key);
	for(var i = 0; i<data.length; i++){
		selo.options[selo.options.length]=new Option(eval('data[i].'+valueName),eval('data[i].'+keyName));
	}
	selo.selectedIndex = selIndex;
}

////Get Vendor list////
function getVendorListByVendorId(selIndex){
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
			updateDropdownList(data, "id", "name", 'searchPaymentVO_vendorId',selIndex,{key:"all",value:"All"});
			hideLoadingImg();
		},
		failure: showError
	};

	showLoadingImg();
	YAHOO.util.Connect.asyncRequest('POST' , "getUserPreviledgedVendorList.action" , callback , "" ); 
}

////below is save search as quick link/////
var saveQuicklinkUri = "doSavePaymentSearch.action";

function resetRadioButton(){
	form0.selectPaymentDateRadio[0].checked=true;
	form0.selectPaidDateRadio[0].checked=true;
}

//update radio button after get saved quicklink.
function updateRadioButton(){
		if(form0.searchPaymentVO_paidWithinNumOfDays.value!=""){
			form0.selectPaidDateRadio[1].checked=true;
		}
		if(form0.searchPaymentVO_paidDateStartsOn.value!=""||form0.searchPaymentVO_paidDateEndsOn.value!=""){
			form0.selectPaidDateRadio[0].checked=true;
		}
		if(form0.searchPaymentVO_paymentWithinPastNumOfDays.value!=""){
			form0.selectPaymentDateRadio[1].checked=true;
		}
		if(form0.searchPaymentVO_paymentWithinNextNumOfDays.value!=""){
			form0.selectPaymentDateRadio[2].checked=true;
		}
		if(form0.searchPaymentVO_paymentDateStartsOn.value!=""||form0.searchPaymentVO_paymentDateEndsOn.value!=""){
			form0.selectPaymentDateRadio[0].checked=true;
		}
}

//reset form element
function resetFormElementValue(){
	cleanFormElementValue();
	//modified by xinyu.Liu on 2010.5.11 for CCM-115
	FIC_checkForm(form0);
	//update Vendor and Ban drop down list
	getVendorListByVendorId(0);
	updateDropdownList([], "id", "no", 'searchPaymentVO_banId',0, {key:"all",value:"All"});
}

YAHOO.util.Event.onDOMReady(function(){
	cleanFormElementValue();
});

//re-render vendor list and ban list after getting back quicklink from database
function rerenderVendorAndBanList(vendors, bans){
	updateDropdownList(vendors, "id", "name", 'searchPaymentVO_vendorId',0,{key:"all",value:"All"});
	updateDropdownList(bans, "id", "no", 'searchPaymentVO_banId',0, {key:"all",value:"All"});
}


//modified by xinyu.Liu on 2010.5.11 for CCM-49
function changRadioPayment(type){
	if(type==0)
		form0.selectPaymentDateRadio[0].checked=true;
 	if(type==1)
		form0.selectPaymentDateRadio[1].checked=true;
	if(type==2)
		form0.selectPaymentDateRadio[2].checked=true;
}
//modified by xinyu.Liu on 2010.5.11 for CCM-49
function changRadioPaid(type){
	if(type==0)
		form0.selectPaidDateRadio[0].checked=true;
 	if(type==1)
		form0.selectPaidDateRadio[1].checked=true;
}

