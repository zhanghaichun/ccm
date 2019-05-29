//@author wangpengfei
var pageNo = 1;
var recPerPage = 10;
var totalPageNo;
var totalResultNo;
var currentSortingFieldName = "claim_number";
var currentSortingDirection = "asc";
var getDisputeTotalPageNoUri = "dogetPendingTotalPageNo.action";
var searchDisputeUri = "doPendingDispute.action";

//*****Click page automatic loading******
YAHOO.util.Event.onDOMReady(function(){
	filter = new SANINCO.Filter();
	filter.add('dispute_number','String',startSearch);
	filter.add('invoice_number','String',startSearch);
	filter.add('dispute_balance','Number',startSearch);
	filter.add('dispute_amount','Number',startSearch);
	filter.add('vendor_name','String',startSearch);
	filter.add('account_number','String',startSearch);
	filter.add('created_timestamp','Date',startSearch);
	filter.add('claim_number','String',startSearch);
	filter.add('ticket_number','String',startSearch);
	
    startSearch();
});

//strart query
function startSearch(){
	if(!validateFields()) return;
	pageNo = 1;
	if(_recPerPageSelect) recPerPage = _recPerPageSelect.value;
	var callback = {
		success: renderTable,
		failure: showError
	};
	previousPostedData = composePostDataFromUi();
	showLoadingModalLayer();//commom.js
	YAHOO.util.Connect.asyncRequest('POST' , getDisputeTotalPageNoUri , callback , 

previousPostedData ); 
}
///validate data
function validateFields(){
	var formatValid = FIC_checkForm("form0");
	return formatValid;
}
//commit data
function composePostDataFromUi(){
	var postData = ("searchDisputeVO.sortField="+currentSortingFieldName
	+"&searchDisputeVO.sortingDirection="+currentSortingDirection
	+"&searchDisputeVO.pageNo="+pageNo
	+"&searchDisputeVO.recPerPage="+recPerPage);
	if(filter.getCause()!="") postData += ("&searchDisputeVO.filter=" + filter.getCause());
	return postData;
}
//genernate table item
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

//query data
function requestData(){
	if(!validateFields()) return;
	var callback = {
		success: renderPage,
		failure: showError
	};
	previousPostedData = composePostDataFromUi();
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest('POST' , searchDisputeUri , callback , previousPostedData 

); 
}
//sort
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

function evaluateQueyChange(){
	if(isQueryChanged()) {
		startSearch();
		return true;
	}else return false;
}
function isQueryChanged(){
	if(!validateFields()) return false;
	else{
		return (previousPostedData!=composePostDataFromUi())
	}
}

function getSortingDirectionString(fieldName){
	if(currentSortingFieldName==fieldName){
		if(currentSortingDirection=='asc') return '<img src="' + CONTEXT_PATH + 'include/images/upsort.gif">';
		else return '<img src="' + CONTEXT_PATH + 'include/images/downsort.gif">';
	}else return '';
}

//generate table
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
			+ 'id="ctl00_MainContent_SearchDisputeControl1_GridView1"'
			+ 'style="width: 100%; border-collapse: collapse;">'
			+ '<tr class="listViewThLinkS1">'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'dispute_number\');">Dispute Number'
			+ getSortingDirectionString('dispute_number')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("dispute_number")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'dispute_number\',\'Dispute Number\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'invoice_number\');">Invoice Number'
			+ getSortingDirectionString('invoice_number')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("invoice_number")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'invoice_number\',\'Invoice Number\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'dispute_balance\');">Dispute Balance'
			+ getSortingDirectionString('dispute_balance')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("dispute_balance")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'dispute_balance\',\'Dispute Balance\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'dispute_amount\');">Dispute Amount'
			+ getSortingDirectionString('dispute_amount')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("dispute_amount")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'dispute_amount\',\'Dispute Amount\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'vendor_name\');">Vendor'
			+ getSortingDirectionString('vendor_name')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("vendor_name")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'vendor_name\',\'Vendor\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'account_number\');">Ban Account'
			+ getSortingDirectionString('account_number')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("account_number")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'account_number\',\'Ban Account\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'created_timestamp\');">Dispute Date'
			+ getSortingDirectionString('created_timestamp')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("created_timestamp")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'created_timestamp\',\'Dispute Date\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'claim_number\');">Claim Number'
			+ getSortingDirectionString('claim_number')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("claim_number")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'claim_number\',\'Claim Number\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'ticket_number\');">Ticket Number'
			+ getSortingDirectionString('ticket_number')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("ticket_number")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'ticket_number\',\'Ticket Number\');">'+'</th>'
			+ '</tr>';
	 var rows = data.data;
	 for(var i = 0; i<rows.length; i++){
	 	var row = rows[i];
	 	t += ('<tr class="'+ (i%2==0?'evenListRowS1':'even')+'" style="height: 30px;">'
			+ '<td><a target=\"_blank\" href="viewDisputeDetails.action?dispute.id='+row.id+'">'+row.dispute_number+'</a></td>'
			+ '<td align="left">'+row.invoice_number+'</td>'
			+ '<td align="left">'+row.dispute_balance+'</td>'
			+ '<td align="left">'+row.dispute_amount+'</td>'		
			+ '<td align="left">'+row.vendor_name+'</td>'			
			+ '<td align="left">'+row.account_number+'</td>'
			+ '<td align="left">'+row.created_timestamp+'</td>'	
			+ '<td align="left">'+row.claim_number+'</td>'	
			+ '<td align="left">'+row.ticket_number+'</td>'	
			+ '</tr>');
	 }
	 t += '</table>';
    _dataTable.innerHTML = t;
    __curPageNo.value = pageNo;
	 hideLoadingModalLayer();
}

//paging
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

function showLoadingImg(){
	if(loadingImgDiv) loadingImgDiv.style.display="block";
}

function hideLoadingImg(){
	if(loadingImgDiv) loadingImgDiv.style.display="none";
}


