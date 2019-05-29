//@author xinyu.Liu
var pageNo = 1;
var pageNoThree = 1;
var recPerPage = 4;
var recPerPageThree = 5;
var totalPageNo;
var totalResultNo;
var totalPageNoThree;
var totalResultNoThree;
var currentSortingFieldName = "p.amount";
var currentSortingFieldNameThree = "ph.created_timestamp";
var currentSortingDirection = "desc";
var currentSortingDirectionThree = "desc";
var getPaymentDetailTotalPageNoUri = "doGetPaymentDetailsTotalPageNo.action";
var getPaymentHistoryTotalPageNoUri = "doGetPaymentHistoryTotalPageNo.action";
var searchPaymentDetailsUri = "doGetPaymentDetails.action";
var searchPaymentHistoryUri = "doGetPaymentHistory.action";
var savePaymentHistoryUri = "savePaymentHistory.action";
var searchDataUri = "searchPayment.action";

// Modified By Chao.Liu to 10/07/22 AM
YAHOO.util.Event.onDOMReady(function(){
    initSaveId();
    initSaveNoId();
    
    filter = new SANINCO.Filter();
    filter.addEditeEvent(getPaymentHistoryTotalNo);
    filter.add('user_name', 'String');
    filter.add('payment_action_name', 'String');
    filter.add('ps1.payment_status_name', 'String');
    filter.add('ps2.payment_status_name', 'String');
    filter.add('notes', 'String');
    filter.add('ph.created_timestamp', 'Date');
    
    filter1 = new SANINCO.Filter();
    filter1.addEditeEvent(getPaymentDetailsTotalNo);
    filter1.add('p.amount', 'Number');
    filter1.add('a.account_code_name', 'String');
    filter1.add('p.description', 'String');
    
    getPaymentDetailsTotalNo();
    //	getPaymentHistoryTotalNo();
    
    var tabView = new YAHOO.widget.TabView('demo');
	$("#demo li a:eq(0)").click();
	tabView.selectTab(0);
    
    var handleSubmit = function(){
        YAHOO.ccm.container.reconcileDialog.hide();
        creditForReconcile();
    };
    var handleCancel = function(){
        this.cancel();
    };
    YAHOO.util.Dom.removeClass("reconcileDialog", "yui-pe-content");
    YAHOO.ccm.container.reconcileDialog = new YAHOO.widget.Dialog("reconcileDialog", {
        width: "30em",
        fixedcenter: true,
        visible: false,
        constraintoviewport: true,
        buttons: [{
            text: "Submit",
            handler: handleSubmit
        }, {
            text: "Cancel",
            handler: handleCancel
        }]
    });
    YAHOO.ccm.container.reconcileDialog.render();
});

//get Payment History Total Number
function getPaymentHistoryTotalNo(){
    pageNo = 1;
    pageNoThree = 1;
    if (document.getElementById("_recPerPageSelectThree")) 
        recPerPageThree = document.getElementById("_recPerPageSelectThree").value;
    var callback = {
        success: renderPageReconcileTableThree,
        failure: showError
    };
    previousPostedData = composePostDataFromUi();
    if (filter.getCause() != "") 
        previousPostedData += ("&viewPaymentDetailVO.filter=" + filter.getCause());
    YAHOO.util.Connect.asyncRequest('POST', getPaymentHistoryTotalPageNoUri, callback, previousPostedData);
}

//get Payment Details Total Number
function getPaymentDetailsTotalNo(){
    pageNo = 1;
    pageNoThree = 1;
    if (document.getElementById("_recPerPageSelect")) 
        recPerPage = document.getElementById("_recPerPageSelect").value;
    var callback = {
        success: renderPageReconcileTable,
        failure: showError
    };
    previousPostedData = composePostDataFromUi();
    if (filter1.getCause() != "") 
        previousPostedData += ("&viewPaymentDetailVO.filter=" + filter1.getCause());
    YAHOO.util.Connect.asyncRequest('POST', getPaymentDetailTotalPageNoUri, callback, previousPostedData);
}

//Show Payment Details Total
function renderPageReconcileTable(o){
    if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
        window.location.reload();
        return;
    }
    var data = eval("(" + o.responseText + ")");
    if (data.error) {
        _paymentDetailsTable.innerHTML = "Error: " + data.error;
        _paginationTable.style.display = "none";
        return;
    }
    if (data.totalResultNo == 0) {
        totalResultNo = 0;
        totalPageNo = data.totalPageNo;
        _paymentDetailsTable.innerHTML = "No available credits.";
        document.getElementById('__curPageNo').value = '0';
        __totalPageNo.innerHTML = "0";
        _paginationTable.style.display = "none";
    }
    else {
        totalResultNo = data.totalResultNo;
        totalPageNo = data.totalPageNo;
        document.getElementById('__curPageNo').value = '1';
        __totalPageNo.innerHTML = totalPageNo;
        _paginationTable.style.display = "block";
        getPaymentDetails();
    }
}

//Show Payment History Total
function renderPageReconcileTableThree(o){
	if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
		window.location.reload();
		return;
	}
    var data = eval("(" + o.responseText + ")");
    if (data.error) {
        _paymentHistoryTable.innerHTML = "Error: " + data.error;
        _paginationTableThree.style.display = "none";
        return;
    }
    if (data.totalResultNoThree == 0) {
        totalResultNoThree = 0;
        totalPageNoThree = data.totalPageNoThree;
        _paymentHistoryTable.innerHTML = "No available credits.";
        document.getElementById('__curPageNoThree').value = '0';
        __totalPageNoThree.innerHTML = "0";
        _paginationTableThree.style.display = "none";
    }
    else {
        totalResultNoThree = data.totalResultNoThree;
        totalPageNoThree = data.totalPageNoThree;
        document.getElementById('__curPageNoThree').value = '1';
        __totalPageNoThree.innerHTML = totalPageNoThree;
        _paginationTableThree.style.display = "block";
        getPaymentHistory();
    }
}

//get Payment Details data 
function getPaymentDetails(){
    if (document.getElementById('_recPerPageSelect')) 
        recPerPage = document.getElementById('_recPerPageSelect').value;
    var callback = {
        success: renderPagePaymentDetails,
        failure: showError
    };
    previousPostedData = composePostDataFromUi();
    if (filter1.getCause() != "") 
        previousPostedData += ("&viewPaymentDetailVO.filter=" + filter1.getCause());
    YAHOO.util.Connect.asyncRequest('POST', searchPaymentDetailsUri, callback, previousPostedData);
}

//get Payment History data 
function getPaymentHistory(){
    if (document.getElementById('_recPerPageSelectThree')) 
        recPerPage = document.getElementById('_recPerPageSelectThree').value;
    var callback = {
        success: renderPagePaymentHistory,
        failure: showError
    };
    previousPostedData = composePostDataFromUi();
    if (filter.getCause() != "") 
        previousPostedData += ("&viewPaymentDetailVO.filter=" + filter.getCause());
    YAHOO.util.Connect.asyncRequest('POST', searchPaymentHistoryUri, callback, previousPostedData);
}

//show generate table Payment Details
function renderPagePaymentDetails(o){
	if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
		window.location.reload();
		return;
	}
    var data = eval("(" + o.responseText + ")");
    if (data.error) {
        _dataTable.innerHTML = "Error: " + data.error;
        _paginationTable.style.display = "none";
        hideLoadingModalLayer();
        return;
    }
    var t = 'Available PaymentDetails: Items ' + data.begin + ' - ' + data.end + ' of ' + totalResultNo +
    '<table class="gridview" cellspacing="0" rules="all" border="1"' +
    'id="ctl00_MainContent_PaymentDetailsControl1_GridView1"' +
    'style="width: 100%; border-collapse: collapse;">' +
    '<tr class="listViewThLinkS1">' +
    '<th scope="col">' +
    '<a href="#" onclick="javascript:sort(event,\'p.amount\');">Amount' +
    getSortingDirectionString('p.amount') +
    '</a>' +
    '&nbsp;&nbsp;&nbsp;<img src="'+filter1.getImg("p.amount")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter1.edite(\'p.amount\',\'Amount\');">' +
    '</th>' +
    '<th scope="col">' +
    '<a href="#" onclick="javascript:sort(event,\'a.account_code_name\');">Account Code' +
    getSortingDirectionString('a.account_code_name') +
    '</a>' +
    '&nbsp;&nbsp;&nbsp;<img src="'+filter1.getImg("a.account_code_name")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter1.edite(\'a.account_code_name\',\'Account Code\');">' +
    '</th>' +
    '<th scope="col">' +
    '<a href="#" onclick="javascript:sort(event,\'p.description\');">Description' +
    getSortingDirectionString('p.description') +
    '</a>' +
    '&nbsp;&nbsp;&nbsp;<img src="'+filter1.getImg("p.description")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter1.edite(\'p.description\',\'Description\');">' +
    '</th>' +
    '</tr>';
    var rows = data.data;
    for (var i = 0; i < rows.length; i++) {
        var row = rows[i];
        t += ('<tr class="' + (i % 2 == 0 ? 'evenListRowS1' : 'even') + '" style="height: 30px;">' +
        '<td align="left">$' +
        row.amount +
        '</td>' +
        '<td align="left">' +
        row.accountCode +
        '</td>' +
        '<td align="left">' +
        row.description +
        '</td>' +
        '</tr>');
    }
    t += '</table>';
    _paymentDetailsTable.innerHTML = t;
    document.getElementById('__curPageNo').value = pageNo;
}

//show generate table Payment History
function renderPagePaymentHistory(o){
	if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
		window.location.reload();
		return;
	}
    var data = eval("(" + o.responseText + ")");
    if (data.error) {
        _dataTableThree.innerHTML = "Error: " + data.error;
        _paginationTableThree.style.display = "none";
        hideLoadingModalLayer();
        return;
    }
    
    var t = 'Available PaymentHistory: Items ' + data.begin + ' - ' + data.end + ' of ' + totalResultNoThree +
    '<table class="gridview" cellspacing="0" rules="all" border="1"' +
    'id="ctl00_MainContent_PaymentHistoryControl1_GridView1"' +
    'style="width: 100%; border-collapse: collapse;">' +
    '<tr class="listViewThLinkS1">' +
    '<th scope="col">' +
    '<a href="#" onclick="javascript:sortThree(event,\'user_name\');">User' +
    getSortingDirectionStringThree('user_name') +
    '</a>' +
    '&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("user_name")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'user_name\',\'User\');">' +
    '</th>' +
    '<th scope="col">' +
    '<a href="#" onclick="javascript:sortThree(event,\'payment_action_name\');">Action' +
    getSortingDirectionStringThree('payment_action_name') +
    '</a>' +
    '&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("payment_action_name")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'payment_action_name\',\'Action\');">' +
    '</th>' +
    '<th scope="col">' +
    '<a href="#" onclick="javascript:sortThree(event,\'ps1.payment_status_name\');">Previous Status' +
    getSortingDirectionStringThree('ps1.payment_status_name') +
    '</a>' +
    '&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("ps1.payment_status_name")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'ps1.payment_status_name\',\'Previous Status\');">' +
    '</th>' +
    '<th scope="col">' +
    '<a href="#" onclick="javascript:sortThree(event,\'ps2.payment_status_name\');">New Status' +
    getSortingDirectionStringThree('ps2.payment_status_name') +
    '</a>' +
    '&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("ps2.payment_status_name")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'ps2.payment_status_name\',\'New Status\');">' +
    '</th>' +
    '<th scope="col">' +
    '<a href="#" onclick="javascript:sortThree(event,\'notes\');">Notes' +
    getSortingDirectionStringThree('notes') +
    '</a>' +
    '&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("notes")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'notes\',\'Notes\');">' +
    '</th>' +
    '<th scope="col">' +
    '<a href="#" onclick="javascript:sortThree(event,\'ph.created_timestamp\');">Time' +
    getSortingDirectionStringThree('ph.created_timestamp') +
    '</a>' +
    '&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("ph.created_timestamp")+'"  style="cwidth:13px;height:13px;ursor: pointer;"  onclick="filter.edite(\'ph.created_timestamp\',\'Time\');">' +
    '</th>' +
    '</tr>';
    var rows = data.data;
    for (var i = 0; i < rows.length; i++) {
        var row = rows[i];
        t += ('<tr class="' + (i % 2 == 0 ? 'evenListRowS1' : 'even') + '" style="height: 30px;">' +
        '<td align="left">' +
        row.user +
        '</td>' +
        '<td align="left">' +
        row.action +
        '</td>' +
        '<td align="left">' +
        row.previousStatus +
        '</td>' +
        '<td align="left">' +
        row.newStatus +
        '</td>' +
        '<td align="left">' +
        row.notes +
        '</td>' +
        '<td align="left">' +
        row.time +
        '</td>' +
        '</tr>');
    }
    
    t += '</table>';
    _paymentHistoryTable.innerHTML = t;
    document.getElementById('__curPageNoThree').value = pageNoThree;
}

function getSortingDirectionString(fieldName){
    if (currentSortingFieldName == fieldName) {
        if (currentSortingDirection == 'asc') 
            return '<img src="include/images/upsort.gif">';
        else 
            return '<img src="include/images/downsort.gif">';
    }
    else 
        return '';
}

function getSortingDirectionStringThree(fieldName){
    if (currentSortingFieldNameThree == fieldName) {
        if (currentSortingDirectionThree == 'asc') 
            return '<img src="include/images/upsort.gif">';
        else 
            return '<img src="include/images/downsort.gif">';
    }
    else 
        return '';
}

//commit data
function composePostDataFromUi(){
    var postData = ("viewPaymentDetailVO.sortField=" + currentSortingFieldName +
    "&viewPaymentDetailVO.sortFieldThree=" +
    currentSortingFieldNameThree +
    "&viewPaymentDetailVO.sortingDirection=" +
    currentSortingDirection +
    "&viewPaymentDetailVO.sortingDirectionThree=" +
    currentSortingDirectionThree +
    "&viewPaymentDetailVO.pageNo=" +
    pageNo +
    "&viewPaymentDetailVO.pageNoThree=" +
    pageNoThree +
    "&viewPaymentDetailVO.recPerPage=" +
    recPerPage +
    "&viewPaymentDetailVO.recPerPageThree=" +
    recPerPageThree +
    "&viewPaymentDetailVO.paymentId=" +
    getValue() +
    "&viewPaymentDetailVO.actionId=" +
    getActionId() +
    "&viewPaymentDetailVO.notes=" +
    getNotes());
    return postData;
}

//get paymentId Value
function getValue(){
    var add = top.location;
    add = add.toString();
    end = add.indexOf("#");
    if (end == -1) {
        return (add.substring(add.indexOf("=") + 1, add.length));
    }
    else {
        return (add.substring(add.indexOf("=") + 1, add.length - 1));
    }
}

//get paymentId Property
function getProperty(){
    var add = top.location;
    add = add.toString();
    return (add.substring(add.indexOf("?") + 1, add.length));
}

//payment details sort
function sort(e, sortingFieldName){
    if (evaluateQueyChange()) 
        return;
    if (currentSortingFieldName != sortingFieldName) {
        currentSortingFieldName = sortingFieldName;
        currentSortingDirection = "asc";
    }
    else {
        if (currentSortingDirection == "asc") 
            currentSortingDirection = "desc";
        else 
            currentSortingDirection = "asc";
    }
    getPaymentDetails();
}

//payment history sort
function sortThree(e, sortingFieldName){
    if (evaluateQueyChange()) 
        return;
    if (currentSortingFieldNameThree != sortingFieldName) {
        currentSortingFieldNameThree = sortingFieldName;
        currentSortingDirectionThree = "asc";
    }
    else {
        if (currentSortingDirectionThree == "asc") 
            currentSortingDirectionThree = "desc";
        else 
            currentSortingDirectionThree = "asc";
    }
    getPaymentHistory();
}

function isQueryChanged(){
    return (previousPostedData != composePostDataFromUi())
}

function evaluateQueyChange(){
    if (isQueryChanged()) {
        getPaymentDetails();
        return true;
    }
    else 
        return false;
}

//payment details page
function getNextPage(){
    if (evaluateQueyChange()) 
        return;
    if ((pageNo + 1) > totalPageNo) 
        return;
    pageNo++;
    getPaymentDetails();
}

function getPrevPage(){
    if (evaluateQueyChange()) 
        return;
    if ((pageNo - 1) < 1) 
        return;
    pageNo--;
    getPaymentDetails();
}

function getFirstPage(){
    if (evaluateQueyChange()) 
        return;
    pageNo = 1;
    getPaymentDetails();
}

function getLastPage(){
    if (evaluateQueyChange()) 
        return;
    pageNo = totalPageNo;
    getPaymentDetails();
}

function isQueryChangedThree(){
    return (previousPostedData != composePostDataFromUi())
}

function evaluateQueyChangeThree(){
    if (isQueryChangedThree()) {
        getPaymentHistory();
        return true;
    }
    else 
        return false;
}

//payment history page
function getNextPageThree(){
    if (evaluateQueyChangeThree()) 
        return;
    if ((pageNoThree + 1) > totalPageNoThree) 
        return;
    pageNoThree++;
    getPaymentHistory();
}

function getPrevPageThree(){
    if (evaluateQueyChangeThree()) 
        return;
    if ((pageNoThree - 1) < 1) 
        return;
    pageNoThree--;
    getPaymentHistory();
}

function getFirstPageThree(){
    if (evaluateQueyChangeThree()) 
        return;
    pageNoThree = 1;
    getPaymentHistory();
}

function getLastPageThree(){
    if (evaluateQueyChangeThree()) 
        return;
    pageNoThree = totalPageNoThree;
    getPaymentHistory();
}



//Empty
function resetRadioButton(){
}

//Save to judge
function saveJudge(){

    var action = document.getElementById('actionId').value;
    if (action != "all") {
    
        var sb = "Please confirm : <br>  Action : " + getActionName() + " <br>Notes : " + getNotes();
        
        document.getElementById("actionList-name").innerHTML = sb;
        
        YAHOO.ccm.container.saveId.show();
    }
    else {
        YAHOO.ccm.container.saveNoId.show();
    }
}

//get action name
function getActionName(){
    return document.getElementById("actionId").options[document.getElementById("actionId").selectedIndex].innerHTML;
}

//get action id
function getActionId(){
    return document.getElementById("actionId").value;
}

//get notes
function getNotes(){
    return document.getElementById("notes").value;
}

//no action id prompt box
// Modified By Chao.Liu to 10/07/22 AM
function initSaveNoId(){

    YAHOO.util.Dom.removeClass("saveNoId", "save-noid");
    YAHOO.ccm.container.saveNoId = new YAHOO.widget.Dialog("saveNoId", {
        width: "30em",
        fixedcenter: true,
        visible: false,
        constraintoviewport: true
    });
    YAHOO.ccm.container.saveNoId.render();
};

//Tips box with action id
// Modified By Chao.Liu to 10/07/22 AM
function initSaveId(){

    var handleSubmit = function(){
        saveAction();
    };
    var handleCancel = function(){
        this.cancel();
    };
    
    YAHOO.util.Dom.removeClass("saveId", "save-id");
    
    YAHOO.ccm.container.saveId = new YAHOO.widget.Dialog("saveId", {
        width: "30em",
        fixedcenter: true,
        visible: false,
        constraintoviewport: true,
        buttons: [{
            text: "Confirm",
            handler: handleSubmit
        }, {
            text: "Cancel",
            handler: handleCancel
        }]
    });
    YAHOO.ccm.container.saveId.render();
};

//action save 
function saveAction(){
    var callback = {
        success: resetFormElementValue,
        failure: showError
    };
    previousPostedData = composePostDataFromUi();
    YAHOO.ccm.container.saveId.hide();
    window.location.reload();
    YAHOO.util.Connect.asyncRequest('POST', savePaymentHistoryUri, callback, previousPostedData);
}

//reset form element
function resetFormElementValue(){
    cleanFormElementValue();
    actionList([], "id", "no", 'ViewPaymentDetailVO.actionId', 0, {
        key: "all",
        value: "All"
    });
    viewPaymentDetailVO.notes = "";
}

