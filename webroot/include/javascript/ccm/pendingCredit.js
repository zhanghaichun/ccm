var pageNo = 1;
var recPerPage = 10;
var totalPageNo;
var totalResultNo;
var currentSortingDirection = "asc";
var currentSortingFieldName = "id";
var searchCreditUri = "doPendingSearchCredit.action";
var getCreditTotalPageNoUri = "doPendingGetCreditSearchTotalPageNo.action";

YAHOO.util.Event.onDOMReady(function(){
filter = new SANINCO.Filter();
	filter.add('id','String',startSearch);
	filter.add('credit_balance','Number',startSearch);
	filter.add('claim_number','String',startSearch);
	filter.add('vendor_name','String',startSearch);
	filter.add('account_number','String',startSearch);
	filter.add('credit_amount','Number',startSearch);
	filter.add('credit_date','Date',startSearch);
	filter.add('invoice_number','String',startSearch);
	filter.add('ticket_number','String',startSearch);
    startSearch();
	
});

//*****Save data and transmission*****
function requestData(){
	if(!validateFields()) return;
	var callback = {
		success: renderPage,
		failure: showError
	};

	previousPostedData = composePostDataFromUi();
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest('POST' , searchCreditUri , callback , previousPostedData ); 
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


function hideLoadingImg(){
	if(loadingImgDiv) loadingImgDiv.style.display="none";
}

function showLoadingImg(){
	if(loadingImgDiv) loadingImgDiv.style.display="block";
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

function validateFields(){
	var formatValid = FIC_checkForm("form0");

	return formatValid;
}

//// start Search ////
function startSearch(){
	if(!validateFields()) return;
	pageNo = 1;
	if(_recPerPageSelect) recPerPage = _recPerPageSelect.value;
	var callback = {
		success: renderTable,
		failure: showError
	};

	previousPostedData = composePostDataFromUi();
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest('POST' , getCreditTotalPageNoUri , callback , previousPostedData ); 
}

function validateFields(){
	var formatValid = FIC_checkForm("form0");
	
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

function composePostDataFromUi(){
	var postData = ("searchCreditVO.sortField="+currentSortingFieldName
	+"&searchCreditVO.sortingDirection="+currentSortingDirection
	+"&searchCreditVO.pageNo="+pageNo
	+"&searchCreditVO.recPerPage="+recPerPage);
	if(filter.getCause()!="") postData += ("&searchCreditVO.filter=" + filter.getCause());
	return postData;
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
		if(currentSortingDirection=='asc') return '<img src="' + CONTEXT_PATH + 'include/images/upsort.gif">';
		else return '<img src="' + CONTEXT_PATH + 'include/images/downsort.gif">';
	}else return '';
}
//************Page table*************
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
			+ 'id="ctl00_MainContent_SearchCreditControl1_GridView1"'
			+ 'style="width: 100%; border-collapse: collapse;">'
			+ '<tr class="listViewThLinkS1">'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'id\');">Credit Number'
			+ getSortingDirectionString('id')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("id")+'"  style="width:13px;height:13px;cursor: pointer;" " onclick="filter.edite(\'id\',\'Credit Number\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'credit_balance\');">Credit Balance'
			+ getSortingDirectionString('credit_balance')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("credit_balance")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'credit_balance\',\'Credit Balance\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'claim_number\');">Claim Number'
			+ getSortingDirectionString('claim_number')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("claim_number")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'claim_number\',\'Claim Number\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'vendor_name\');">Vendor'
			+ getSortingDirectionString('vendor_name')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("vendor_name")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'vendor_name\',\'Vendor\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'account_number\');">BAN'
			+ getSortingDirectionString('ban_id')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("account_number")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'account_number\',\'BAN\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'credit_amount\');">Credit Amount'
			+ getSortingDirectionString('credit_amount')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("credit_amount")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'credit_amount\',\'Credit Amount\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'credit_date\');">Credit Date'
			+ getSortingDirectionString('credit_date')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("credit_date")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'credit_date\',\'Credit Date\');">'+'</th>'	
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'invoice_number\');">Invoice Number'
			+ getSortingDirectionString('invoice_number')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("invoice_number")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'invoice_number\',\'Invoice Number\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'ticket_number\');">Ticket Number'
			+ getSortingDirectionString('ticket_number')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("ticket_number")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'ticket_number\',\'Ticket Number\');">'+'</th>'	
			+ '</tr>';
	 var rows = data.data;
	 for(var i = 0; i<rows.length; i++){
	 	var row = rows[i];
	 	t += ('<tr class="'+ (i%2==0?'evenListRowS1':'even')+'" style="height: 30px;">'
			+ '<td><a target=\"_blank\" href="viewCreditDetails.action?creditId='+row.id+'">'+row.id+'</a></td>'
			+ '<td align="left">'+row.credit_balance+'</td>'
			+ '<td align="left">'+row.claim_number+'</td>'
			+ '<td align="left">'+row.vendor+'</td>'
			+ '<td align="left">'+row.ban+'</td>'
			+ '<td align="left">'+row.amount+'</td>'
			+ '<td align="left">'+row.date+'</td>'
			+ '<td align="left">'+row.invoice_number+'</td>'
			+ '<td align="left">'+row.ticket_number+'</td>'
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
//*****************paging***************

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


