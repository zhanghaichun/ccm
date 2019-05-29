//@author li,zhiguo
var pageNo = 1;
var recPerPage = 10;
var totalPageNo;
var totalResultNo;
var currentSortingFieldName = "i.claim_number";
var currentSortingDirection = "asc";
var getDisputeTotalPageNoUri = "doGetDisputeSearchTotalPageNo.action";
var searchDisputeUri = "doSearchDispute.action";

YAHOO.util.Event.onDOMReady(function(){
	filter = new SANINCO.Filter();
	filter.addEditeEvent(function(){
		startSearchFilter();
	});
	filter.add('i.dispute_number','String');
	filter.add('i.invoice_number','String');
	filter.add('i.dispute_amount','number');
	filter.add('i.dispute_balance','number');
	filter.add('i.vendor_name','String');
	filter.add('i.account_number','String');
	filter.add('i.created_timestamp','Date');
	filter.add('i.dispute_status_name','String');
	filter.add('i.dispute_reason_name','String');
	filter.add('i.claim_number','String');
	filter.add('i.analyst_user','String');
	filter.add('i.ticket_number','String');	
	filter.add('i.flag_shortpaid','String');	
	filter.add('i.flag_recurring','String');	
	//cleanFormElementValue();
	
});


//judgDate by xinyu.Liu beijing time 2010.5.12
function judgDate(){

	var timePast = YAHOO.util.Dom.get('form0_searchDisputeVO_disputeDateWiPastNumOfDays').value;
	var nodes = YAHOO.util.Selector.query(".validate-date-ca",'form0');
	var bo = true;
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
		removeClassName('form0_searchDisputeVO_disputeDateWiPastNumOfDays','validation-passed');
		addClassName('form0_searchDisputeVO_disputeDateWiPastNumOfDays','validation-failed');
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
	filter.clearAll();
	previousPostedData = composePostDataFromUi();
		//	var formObject = document.getElementById('form0');
//	YAHOO.util.Connect.setForm(formObject);
	showLoadingModalLayer();//commom.js
	YAHOO.util.Connect.asyncRequest('POST' , getDisputeTotalPageNoUri , callback , previousPostedData ); 
}
function startSearchFilter(){
	// Validation Date
	if((!validateFields()) | (!judgDate())) return;
	pageNo = 1;
	if(_recPerPageSelect) recPerPage = _recPerPageSelect.value;
	var callback = {
		success: renderTable,
		failure: showError
	};

	previousPostedData = composePostDataFromUi();
		//	var formObject = document.getElementById('form0');
//	YAHOO.util.Connect.setForm(formObject);
	showLoadingModalLayer();//commom.js
	YAHOO.util.Connect.asyncRequest('POST' , getDisputeTotalPageNoUri , callback , previousPostedData ); 
}

///validate data
function validateFields(){
	if(getCheckedValue(form0.selectDisputeDateRadio)=="DATEFRAME"){
		form0.form0_searchDisputeVO_disputeDateWiPastNumOfDays.value="";
	}else if(getCheckedValue(form0.selectDisputeDateRadio)=="WITHINPAST"){
		form0.searchDisputeVO_disputeDateStartsOn.value="";
		form0.searchDisputeVO_disputeDateEndsOn.value="";
	}
	var formatValid = FIC_checkForm("form0");
	var pastdate=YAHOO.util.Dom.get('form0_searchDisputeVO_disputeDateWiPastNumOfDays').value;
		if(pastdate<0||pastdate>10000)
		{
			formatValid = 0;
			alert("The number you have input is invalid")
		}
		if(pastdate != ""){
			var re = /^[0-9]{1,}$/;
			if(!re.exec(pastdate)){
			alert("Past days must be Integer!");
			return false;
		}
	}
	return formatValid;
}

///check radio
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

//commit data
function composePostDataFromUi(){
	var postData = ("searchDisputeVO.sortField="+currentSortingFieldName
	+"&searchDisputeVO.sortingDirection="+currentSortingDirection
	+"&searchDisputeVO.pageNo="+pageNo
	+"&searchDisputeVO.recPerPage="+recPerPage);
	
	if(form0_searchDisputeVO_vendorId.getValue()!="all"){
		postData += ("&searchDisputeVO.vendorId="+form0_searchDisputeVO_vendorId.getValue()); 
		if(YAHOO.util.Dom.get('form0_searchDisputeVO_banId').value!="all") postData += ("&searchDisputeVO.banId="+YAHOO.util.Dom.get('form0_searchDisputeVO_banId').value);	
	}else{
		if(form0_searchDisputeVO_banId.getValue()!="all") postData += ("&searchDisputeVO.banId="+form0_searchDisputeVO_banId.getValue()); 
	}
	if(YAHOO.util.Dom.get('form0_searchDisputeVO_disputeReasonId').value!="all") postData += ("&searchDisputeVO.disputeReasonId="+YAHOO.util.Dom.get('form0_searchDisputeVO_disputeReasonId').value); 
	if(YAHOO.util.Dom.get('form0_searchDisputeVO_disputeStatusId').value!="all") postData += ("&searchDisputeVO.disputeStatusId="+YAHOO.util.Dom.get('form0_searchDisputeVO_disputeStatusId').value);	
	if(YAHOO.util.Dom.get('searchDisputeVO_disputeDateStartsOn').value!="") postData += ("&searchDisputeVO.disputeDateStartsOn=" + YAHOO.util.Dom.get('searchDisputeVO_disputeDateStartsOn').value);
	if(YAHOO.util.Dom.get('searchDisputeVO_disputeDateEndsOn').value!="") postData += ("&searchDisputeVO.disputeDateEndsOn=" + YAHOO.util.Dom.get('searchDisputeVO_disputeDateEndsOn').value);
	if(YAHOO.util.Dom.get('form0_searchDisputeVO_disputeDateWiPastNumOfDays').value!="") postData += ("&searchDisputeVO.disputeDateWiPastNumOfDays=" + YAHOO.util.Dom.get('form0_searchDisputeVO_disputeDateWiPastNumOfDays').value);
	if(YAHOO.util.Dom.get('form0_searchDisputeVO_analyst').value!="all") postData += ("&searchDisputeVO.analyst="+YAHOO.util.Dom.get('form0_searchDisputeVO_analyst').value); 
	if(YAHOO.util.Dom.get('form0_searchDisputeVO_ticketNumber').value!="") postData += ("&searchDisputeVO.ticketNumber=" + YAHOO.util.Dom.get('form0_searchDisputeVO_ticketNumber').value);	
	if(YAHOO.util.Dom.get('form0_searchDisputeVO_claimNumber').value!="") postData += ("&searchDisputeVO.claimNumber=" + YAHOO.util.Dom.get('form0_searchDisputeVO_claimNumber').value);
	if(YAHOO.util.Dom.get('form0_searchDisputeVO_disputeNumber').value!="") postData += ("&searchDisputeVO.disputeNumber=" + YAHOO.util.Dom.get('form0_searchDisputeVO_disputeNumber').value);
	if(YAHOO.util.Dom.get('form0_searchDisputeVO_isShortPaid').value!="All") postData += ("&searchDisputeVO.isShortPaid=" + YAHOO.util.Dom.get('form0_searchDisputeVO_isShortPaid').value);	
	if(YAHOO.util.Dom.get('form0_searchDisputeVO_isRecurring').value!="All") postData += ("&searchDisputeVO.isRecurring=" + YAHOO.util.Dom.get('form0_searchDisputeVO_isRecurring').value);

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
		
			var tdds = 'Search Results: '+totalResultNo
			+ '<table class="gridview" cellspacing="0" rules="all" border="1"'
			+ 'id="ctl00_MainContent_SearchDisputeControl1_GridView1"'
			+ 'style="width: 100%; border-collapse: collapse;">'
			+ '<tr class="listViewThLinkS1">'
			+ '<th nowrap="nowrap" scope="col">'
			+ 'Dispute Number'
			+ '&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("dispute_number")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.dispute_number\',\'Dispute Number\');">'+'</th>'
			+ '<th nowrap="nowrap" scope="col">'
			+ 'Invoice Number'
			+ '&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("invoice_number")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.invoice_number\',\'Invoice Number\');">'+'</th>'
			+ '<th nowrap="nowrap" scope="col">'
			+ 'Claim Number'
			+ '&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("claim_number")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.claim_number\',\'Claim Number\');">'+'</th>'
			+ '<th nowrap="nowrap" scope="col">'
			+ 'Vendor'
			+ '&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("vendor_name")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.vendor_name\',\'Vendor\');">'+'</th>'
			+ '<th nowrap="nowrap" scope="col">'
			+ 'BAN'
			+ '&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("account_number")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.account_number\',\'Ban\');">'+'</th>'
			+ '<th nowrap="nowrap" scope="col">'
			+ 'Dispute Amount'
			+ '&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("dispute_amount")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.dispute_amount\',\'Dispute Amount\');">'+'</th>'
			+ '<th nowrap="nowrap" scope="col">'
			+ 'Outstanding Dispute Amount'
			+ '&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("dispute_balance")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.dispute_balance\',\'Outstanding Dispute Amount\');">'+'</th>'
			+ '<th nowrap="nowrap" scope="col">'
			+ 'Dispute Date'
			+ '&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("created_timestamp")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.created_timestamp\',\'Dispute Date\');">'+'</th>'
			+ '<th nowrap="nowrap" scope="col">'
			+ 'Dispute Status'
			+ '&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("dispute_status_name")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.dispute_status_name\',\'Dispute Status\');">'+'</th>'
			+ '<th nowrap="nowrap" scope="col">'
			+ 'Dispute Category'
			+ '&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("dispute_reason_name")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.dispute_reason_name\',\'Dispute Category\');">'+'</th>'
			+ '<th nowrap="nowrap" scope="col">'
			+ 'Dispute Analyst'
			+ '&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("user_name")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.user_name\',\'Dispute Analyst\');">'+'</th>'
			+ '<th nowrap="nowrap" scope="col">'
			+ 'Ticket Number'
			+ '&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("ticket_number")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.ticket_number\',\'Ticket Number\');">'+'</th>'
			+ '<th nowrap="nowrap" scope="col">'
			+ 'Is Short-paid'
			+ '&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("flag_shortpaid")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.flag_shortpaid\',\'Is Short-paid\');">'+'</th>'
			+ '<th nowrap="nowrap" scope="col">'
			+ 'Is Recurring'
			+ '&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("flag_recurring")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.flag_recurring\',\'Is Recurring\');">'+'</th>'
			+ '</tr>'
			+ '<tr><td nowrap="nowrap" colspan="15" align="center">No records found.</td></tr>'
			+ '</table>';
		_dataTable.innerHTML = tdds;
		__curPageNo.value = 0;
		__totalPageNo.innerHTML = "0";
		_paginationTable.style.display="block";
	}else{
		totalResultNo=data.totalResultNo;
		totalPageNo = data.totalPageNo;
		__curPageNo.value = 1;
		__totalPageNo.innerHTML = totalPageNo;
		_paginationTable.style.display="block";
		if(document.getElementById('saveExcel').style.display != "block") document.getElementById('saveExcel').style.display = "block";
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
	_dataTable.innerHTML = "";
//	var formObject = document.getElementById('form0');
//	YAHOO.util.Connect.setForm(formObject);
	previousPostedData = composePostDataFromUi();
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest('POST' , searchDisputeUri , callback , previousPostedData ); 
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
//var ff={begin:1,end:1,data:[{id:4, ticket_number:"GYUS332404",claim_number:"HAAD8769",vendor_name:"Bell Aliant",account_number:"10000252750",created_timestamp:"2010-05-01 00:00:00",dispute_status_name:"Partial_Approved",dispute_reason_name:"Backbilled Usage ",user_name:"system",flag_shortpaid:"",flag_recurring:""}]};

function getSortingDirectionString(fieldName){
	if(currentSortingFieldName==fieldName){
		if(currentSortingDirection=='asc') return '<img src="include/images/upsort.gif">';
		else return '<img src="include/images/downsort.gif">';
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
			+ 'style="width:100%; border-collapse: collapse;">'
			+ '<tr class="listViewThLinkS1" height="30px">'
			+ '<th nowrap="nowrap" scope="col" >'
			+ '<a href="javascript:void(0)" onclick="javascript:sort(event,\'i.dispute_number\');">Dispute Number'
			+ getSortingDirectionString('i.dispute_number')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("dispute_number")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.dispute_number\',\'Dispute Number\');">'+'</th>'
			+ '<th nowrap="nowrap" scope="col" >'
			+ '<a href="javascript:void(0)" onclick="javascript:sort(event,\'i.invoice_number\');">Invoice Number'
			+ getSortingDirectionString('i.invoice_number')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("invoice_number")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.invoice_number\',\'Invoice Number\');">'+'</th>'
			+ '<th nowrap="nowrap" scope="col">'
			+ '<a href="javascript:void(0)" onclick="javascript:sort(event,\'i.claim_number\');">Claim Number'
			+ getSortingDirectionString('i.claim_number')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("claim_number")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.claim_number\',\'Claim Number\');">'+'</th>'
			+ '<th nowrap="nowrap"  scope="col">'
			+ '<a href="javascript:void(0)" onclick="javascript:sort(event,\'i.vendor_name\');">Vendor'
			+ getSortingDirectionString('i.vendor_name')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("vendor_name")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.vendor_name\',\'Vendor\');">'+'</th>'
			+ '<th nowrap="nowrap"  scope="col">'
			+ '<a href="javascript:void(0)" onclick="javascript:sort(event,\'i.account_number\');">BAN'
			+ getSortingDirectionString('i.account_number')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("account_number")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.account_number\',\'Account\');">'+'</th>'
			+ '<th nowrap="nowrap" scope="col" >'
			+ '<a href="javascript:void(0)" onclick="javascript:sort(event,\'i.dispute_amount\');">Dispute Amount'
			+ getSortingDirectionString('i.dispute_amount')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("dispute_amount")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.dispute_amount\',\'Dispute Amount\');">'+'</th>'
			+ '<th nowrap="nowrap" scope="col" >'
			+ '<a href="javascript:void(0)" onclick="javascript:sort(event,\'i.dispute_balance\');">Outstanding Dispute Amount'
			+ getSortingDirectionString('i.dispute_balance')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("dispute_balance")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.dispute_balance\',\'Outstanding Dispute Amount\');">'+'</th>'
			+ '<th nowrap="nowrap"  scope="col">'
			+ '<a href="javascript:void(0)" onclick="javascript:sort(event,\'i.created_timestamp\');">Dispute Date'
			+ getSortingDirectionString('i.created_timestamp')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("created_timestamp")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.created_timestamp\',\'Dispute Date\');">'+'</th>'
			+ '<th nowrap="nowrap" scope="col">'
			+ '<a href="javascript:void(0)" onclick="javascript:sort(event,\'i.dispute_status_name\');">Dispute Status'
			+ getSortingDirectionString('i.dispute_status_name')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("dispute_status_name")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.dispute_status_name\',\'Dispute Status\');">'+'</th>'
			+ '<th nowrap="nowrap" scope="col">'
			+ '<a href="javascript:void(0)" onclick="javascript:sort(event,\'i.dispute_reason_name\');">Dispute Category'
			+ getSortingDirectionString('i.dispute_reason_name')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("dispute_reason_name")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.dispute_reason_name\',\'Dispute Reason Description\');">'+'</th>'
			+ '<th nowrap="nowrap" scope="col">'
			+ '<a href="javascript:void(0)" onclick="javascript:sort(event,\'i.analyst_user\');">Dispute Analyst'
			+ getSortingDirectionString('i.analyst_user')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("user_name")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.analyst_user\',\'Dispute Analyst\');">'+'</th>'
			+ '<th nowrap="nowrap" scope="col">'
			+ '<a href="javascript:void(0)" onclick="javascript:sort(event,\'i.ticket_number\');">Ticket Number'
			+ getSortingDirectionString('i.ticket_number')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("ticket_number")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.ticket_number\',\'Ticket Number\');">'+'</th>'
			+ '<th nowrap="nowrap" scope="col">'
			+ '<a href="javascript:void(0)" onclick="javascript:sort(event,\'i.flag_shortpaid\');">Is Short-paid'
			+ getSortingDirectionString('i.flag_shortpaid')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("flag_shortpaid")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.flag_shortpaid\',\'Is Short-paid\');">'+'</th>'
			+ '<th nowrap="nowrap" scope="col">'
			+ '<a href="javascript:void(0)" onclick="javascript:sort(event,\'i.flag_recurring\');">Is Recurring'
			+ getSortingDirectionString('i.flag_recurring')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("flag_recurring")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'i.flag_recurring\',\'Is Recurring\');">'+'</th>'
			+ '</tr>';
	 var rows = data.data;
	 for(var i = 0; i<rows.length; i++){
	 	var row = rows[i];
	 	t += ('<tr class="'+ (i%2==0?'evenListRowS1':'even')+'" style="height:30px">'
			+ '<td nowrap="nowrap"><a href="javascript:mark(\'viewDisputeDetails.action?disputeId='+row.id+'\');">'+row.dispute_number+'</a></td>'
			+ '<td nowrap="nowrap"><a href="javascript:mark(\'viewInvoiceDetails.action?invoiceId='+row.iid+'\');">'+row.invoice_number+'</a></td>'
			+ '<td nowrap="nowrap" align="left">'+row.claim_number+'</td>'	
			+ '<td nowrap="nowrap" align="left">'+row.vendor_name+'</td>'			
			+ '<td nowrap="nowrap" align="left">'+row.account_number+'</td>'
			+ '<td nowrap="nowrap" align="left">'+row.dispute_amount+'</td>'	
			+ '<td nowrap="nowrap" align="left">'+row.outstanding_dispute_amount+'</td>'	
			+ '<td nowrap="nowrap" align="left">'+row.created_timestamp+'</td>'
			+ '<td nowrap="nowrap" align="left">'+row.dispute_status_name+'</td>'	
			+ '<td nowrap="nowrap" align="left">'+row.dispute_reason_name+'</td>'	
			+ '<td nowrap="nowrap" align="left">'+row.analyst+'</td>'	
			+ '<td nowrap="nowrap" align="left">'+row.ticket_number+'</td>'	
			+ '<td nowrap="nowrap" align="left">'+row.flag_shortpaid+'</td>'	
			+ '<td nowrap="nowrap" align="left">'+row.flag_recurring+'</td>'	
			+ '</tr>');
	 }
	 t += '</table>';
    _dataTable.innerHTML = t;
    
    // Author Chao.Liu
    // Format money
//    for(var i = 0; i<rows.length; i++){
 //   	var row = rows[i];
//    	simFormat(get("db"+row.id), "$")
 //   	simFormat(get("da"+row.id), "$")
//	}
    
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
//fenye
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
		var re = /^[1-9]+[0-9]*]*$/;
		if(!re.test(__curPageNo.value)){
			alert("Please enter the correct number of pages"); 
			return ;
		}
        var totalvalue = document.getElementById('__totalPageNo').innerHTML;
        var curPageNovalue = __curPageNo.value;
		if(parseInt(curPageNovalue)>parseInt(totalvalue)){
			alert("Please enter the correct number of pages"); 
			return;
		}
		pageNo = __curPageNo.value;
		requestData();
	}
}
function showLoadingImg(){
	if(loadingImgDiv) loadingImgDiv.style.display="block";
}

function hideLoadingImg(){
	if(loadingImgDiv) loadingImgDiv.style.display="none";
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
			//updateDropdownList(data, "id", "name", 'form0_searchDisputeVO_vendorId',selIndex,{key:"all",value:"All"});
			hideLoadingImg();
		},
		failure: showError
	};

	showLoadingImg();
	YAHOO.util.Connect.asyncRequest('POST' , "getUserPreviledgedVendorList.action" , callback , "" ); 
}

////Get BAN list by Vendor id ////
function getBanListByVendorId(selIndex,v){
	if(form0_searchDisputeVO_vendorId.getValue()=='all'){
		updateDropdownList([], "id", "no", 'form0_searchDisputeVO_banId',selIndex, {key:"all",value:"All"});
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
			updateDropdownList(data, "id", "no", 'form0_searchDisputeVO_banId',selIndex, {key:"all",value:"All"});
			if(v)$("#form0_searchDisputeVO_banId").val(v);
			delete data;
		},
		failure: showError
	};

	var pdata = "selVendorId=" + form0_searchDisputeVO_vendorId.getValue();
	YAHOO.util.Connect.asyncRequest('POST' , "getBanListByVendorId.action" , callback , pdata); 
}
////below is save search as quick link/////
var saveQuicklinkUri = "doSaveDisputeSearch.action";

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
// To restore by Chao.Liu Date:2010/08/21 AM 
//reset form element
function resetFormElementValue(){
	cleanFormElementValue();
	FIC_checkForm(form0);
	//update Vendor and Ban drop down list
	getVendorListByVendorId(0);
	updateDropdownList([], "id", "no", 'form0_searchDisputeVO_banId',0, {key:"all",value:"All"});
	form0_searchDisputeVO_vendorId.setValue("all");
}


function resetRadioButton(){
	form0.selectDisputeDateRadio[0].checked=true;
}

//re-render vendor list and ban list after getting back quicklink from database
function rerenderVendorAndBanList(vendors, bans){
//	updateDropdownList(vendors, "id", "name", 'form0_searchDisputeVO_vendorId',0,{key:"all",value:"All"});
//	updateDropdownList(bans, "id", "no", 'form0_searchDisputeVO_banId',0, {key:"all",value:"All"});
}

//update radio button after get saved quicklink.
function updateRadioButton(){
		if(form0.form0_searchDisputeVO_disputeDateWiPastNumOfDays.value!=""){
			form0.selectDisputeDateRadio[1].checked=true;
		}
		if(form0.searchDisputeVO_disputeDateStartsOn.value!=""||form0.searchDisputeVO_disputeDateEndsOn.value!=""){
			form0.selectDisputeDateRadio[0].checked=true;
		}
}
//chang radio
// Modified By Chao.Liu
function changdisputeDateRadio(type){
	if(type==0){
		document.getElementById("form0_searchDisputeVO_disputeDateWiPastNumOfDays").value = "";
		
		form0.selectDisputeDateRadio[0].checked=true;
	}
 	if(type==1){
 		document.getElementById("searchDisputeVO_disputeDateStartsOn").value = "";
 		document.getElementById("searchDisputeVO_disputeDateEndsOn").value = "";
 		
		form0.selectDisputeDateRadio[1].checked=true;
	}
	/* 
	if(type==2)
		form0.selectClaimDateRadio[0].checked=true;
 	if(type==3)
		form0.selectClaimDateRadio[1].checked=true;
	*/
}
/**
 * Add By Chao.Liu
 * @param {Object} k
 * @param {Object} v
 * @param {Object} bans
 */
function setJSelectQuicklink(k,v,d){
	if(k == "searchDisputeVO.vendorId"){
		form0_searchDisputeVO_vendorId.setValue(v);
		if(!d["searchDisputeVO.banId"])getBanListByVendorId(0);
		return true;
	}
	if(k == "searchDisputeVO.banId"){
		if(d["searchDisputeVO.vendorId"]){
			getBanListByVendorId(0,v);
		}else{
			form0_searchDisputeVO_banId.setValue(v);
		}
		return true;
	}
	return false;
}
function clearJSelectQuicklink(){
	form0_searchDisputeVO_vendorId.setValue("all");
	form0_searchDisputeVO_banId.setValue("all");
}


function saveExcel(){
	showLoadingModalLayer();
	var titles = "titles=Dispute Number&titles=Invoice Number&titles=Claim Number&titles=Vendor&titles=Ban&titles=Dispute Amount&titles=Outstanding Dispute Amount&titles=Dispute Date&titles=Dispute Status&titles=Dispute Category&titles=Dispute Analyst&titles=Ticket Number&titles=Is Short-paid&titles=Is Recurring";
	var uri ="saveExcelBySearchDispute.action?"+ titles +"&"+ composePostDataFromUi();
	windowOpen(uri);
	hideLoadingModalLayer();

}