//@author su,wei
var pageNo = 1;
var recPerPage = 10;
var totalPageNo;
var totalResultNo;
var currentSortingFieldName = "dispute_number";
var currentSortingDirection = "asc";
var getReconcileTotalPageNoUri = CONTEXT_PATH+"doGetReconcileSearchTotalPageNo.action";
var searchReconcileUri = CONTEXT_PATH+"doSearchReconcile.action";
var previousPostedData = "";
//below is save search as quick link//
var saveQuicklinkUri = CONTEXT_PATH+"doSaveReconcileSearch.action";

//judgDate by xinyu.Liu beijing time 2010.5.12
function judgDate(){
	var timePast = YAHOO.util.Dom.get('form0_searchReconcileVO_reconcileDueWiPastNumOfDays').value;
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
		removeClassName('form0_searchReconcileVO_reconcileDueWiPastNumOfDays','validation-passed');
		addClassName('form0_searchReconcileVO_reconcileDueWiPastNumOfDays','validation-failed');
		bo = false;
	}
	return bo;
}

//delete reconcile
function deleteReconcile(id,creditid,creditnumber,disputeid,creditnumber,amount){
	if(!confirm("Please confirm to delete.")) return;
	var actionUri = CONTEXT_PATH + "doUpdateCreditOrDisputebalanceRollback.action";
	alert(amount);
	var str=amount;
	var str1;
	str=str.substring(1,str.length);
    
	var callback = {
		success: deleteReconcileCallback,
		failure: showError
	};
	var postData="reconcileId="+id+"&creditId="+creditid+"&creditNumber="+creditnumber+"&disputeId="+disputeid+"&disputeNumber="+creditnumber+"&amount="+str;

	YAHOO.util.Connect.asyncRequest('POST' , actionUri , callback , postData );
	startSearch();
}

function deleteReconcileCallback(o){
	if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
		window.location.reload();
		return;
	}
	var data = eval("("+o.responseText+")");
	if(data.error){
		alert("Error: " + data.error);
		hideLoadingModalLayer();
		return;
	}
	var nd = YAHOO.util.Dom.get('ql_'+data.id);
	nd.parentNode.removeChild(nd);
	hideLoadingModalLayer();
}

function resetRadioButton(){
	//form0.selectReconcileDateRadio[0].checked=true;
	//form0.selectReconcileDateRadio[0].checked=true;
	//form0.selectReconcileDueRadio[0].checked=true;
}

//update radio button after get saved quicklink.
/**
 * Modified by Chao.Liu date to 2010/05/12 PM UPDATE
 * This is agin method error
 */
function updateRadioButton(){
		if(get("form0_searchReconcileVO_reconcileDueWiPastNumOfDays").value!=""){
			form0.selectReconcileDueRadio[1].checked=true;
		}
		if(get("searchReconcileVO_reconcileDueStartsOn").value!=""||("searchReconcileVO_reconcileDueEndsOn").value!=""){
			form0.selectReconcileDueRadio[0].checked=true;
		}
}

//strart query
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
	if(YAHOO.util.Dom.get('searchReconcileVO_reconcileDueStartsOn').value!="") previousPostedData += ("&searchReconcileVO_reconcileDueStartsOn=" + YAHOO.util.Dom.get('searchReconcileVO_reconcileDueStartsOn').value);
	if(YAHOO.util.Dom.get('searchReconcileVO_reconcileDueEndsOn').value!="") previousPostedData += ("&searchReconcileVO_reconcileDueEndsOn=" + YAHOO.util.Dom.get('searchReconcileVO_reconcileDueEndsOn').value);
	showLoadingModalLayer();//commom.js
	YAHOO.util.Connect.asyncRequest('POST' , getReconcileTotalPageNoUri , callback , previousPostedData ); 
	//Empty form elements
	//YAHOO.util.Event.onDOMReady(function(){cleanFormElementValue();});
}

///validate data
function validateFields(){
	var formatValid = FIC_checkForm("form0");
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
	var postData = ("searchReconcileVO.sortField="+currentSortingFieldName
	+"&searchReconcileVO.sortingDirection="+currentSortingDirection
	+"&searchReconcileVO.pageNo="+pageNo
	+"&searchReconcileVO.recPerPage="+recPerPage);
	
	var str = postData + "&" + aotoPostFormDataUi('form0');	
	if(filter.getCause()!="") str += ("&searchReconcileVO.filter=" + filter.getCause());
	//alert(str);
	return str;
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
		//if(totalPageNo==1)
			//_paginationTable.style.display="none";
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
//	var formObject = document.getElementById('form0');
//	YAHOO.util.Connect.setForm(formObject);
	previousPostedData = composePostDataFromUi();
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest('POST' , searchReconcileUri , callback , previousPostedData ); 
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
			+ 'id="ctl00_MainContent_SearchReconcileControl1_GridView1"'
			+ 'style="width: 100%; border-collapse: collapse;">'
			+ '<tr class="listViewThLinkS1">'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'id\');">Id'
			+ getSortingDirectionString('id')+'</a></th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'dispute_number\');">Dispute Number'
			+ getSortingDirectionString('dispute')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("dispute_number")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'dispute_number\',\'Dispute Number\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'invoice_number\');">Invoice'
			+ getSortingDirectionString('invoiceNumber')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("invoice_number")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'invoice_number\',\'Invoice\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'user_name\');">Analyst'
			+ getSortingDirectionString('analyst')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("user_name")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'user_name\',\'Analyst\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'account_number\');">Ban'
			+ getSortingDirectionString('ban')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("account_number")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'account_number\',\'Ban\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'vendor_name\');">Vendor'
			+ getSortingDirectionString('vendor')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("vendor_name")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'vendor_name\',\'Vendor\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'reconcile_amount\');">Reconcile Amount'
			+ getSortingDirectionString('amount')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("reconcile_amount")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'reconcile_amount\',\'Reconcile Amount\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'reconcile_date \');">Reconcile Due Date'
			+ getSortingDirectionString('date')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("reconcile_date")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'reconcile_date\',\'Reconcile Due Date\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'ticket_number\');">Ticket Number'
			+ getSortingDirectionString('ticketNumber')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("ticket_number")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'ticket_number\',\'Ticket Number\');">'+'</th>'
			+ '<th scope="col">'
			+ '<a href="#" onclick="javascript:sort(event,\'claim_number\');">Claim Number'
			+ getSortingDirectionString('claimNumber')+'</a>'+'&nbsp;&nbsp;&nbsp;<img src="'+filter.getImg("claim_number")+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="filter.edite(\'claim_number\',\'Claim Number\');">'+'</th>'
			+ '<th scope="col">'
			+ '&nbsp;&nbsp;&nbsp;Delete <th/>'
			+ '</tr>';
	 var rows = data.data;
	 for(var i = 0; i<rows.length; i++){
	 	var row = rows[i];
	 	t += ('<tr class="'+ (i%2==0?'evenListRowS1':'even')+'" style="height: 30px;">'
			+ '<td align="left">'+row.id+'</td>'
			//,'+row.creditid+','+row.creditnumber+','+row.dispute+','+row.dispute+','+row.amount+'
			//+ '<td><a href="#" onclick="a()">'+row.id+'</a></td>'id,creditid,creditnumber,disputeid,creditnumber,amount
			//+ '<td><input type="button" value="Delete" onclick="deleteReconcile('+row.id+','+row.creditid+',\''+row.creditnumber+'\','+row.disputeid+',\''+row.dispute+'\',\''+row.amount+'\')"/></td>'
			+ '<td align="left">'+row.dispute+'</td>'
			+ '<td align="left">'+row.invoiceNumber+'</td>'
			+ '<td align="left">'+row.analyst+'</td>'		
			+ '<td align="left">'+row.ban+'</td>'			
			+ '<td align="left">'+row.vendor+'</td>'
			+ '<td align="left">'+row.amount+'</td>'
			+ '<td align="left">'+row.date+'</td>'	
			+ '<td align="left">'+row.ticketNumber+'</td>'	
			+ '<td align="left">'+row.claimNumber+'</td>'
			+ '<td align="left"><input type="button" value="Delete" onclick="deleteReconcile('+row.id+','+row.creditid+',\''+row.creditnumber+'\','+row.disputeid+',\''+row.dispute+'\',\''+row.amount+'\')"/></td>'	
			+ '</tr>');
			
	 }
	 t += '</table>';
    _dataTable.innerHTML = t;
    __curPageNo.value = pageNo;
//	if(totalPageNo==1)
		//_paginationTable.style.display="none";
	 _paginationTable.style.display="block";
	 hideLoadingModalLayer();
}

//function a(){
//var postData = ("id="+id
//	+"&creditid="+id);
	
//	alert(postData);	
//return postData;
//}

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
			updateDropdownList(data, "id", "name", 'form0_searchReconcileVO_vendorId',selIndex,{key:"all",value:"All"});
			hideLoadingImg();
		},
		failure: showError
	};

	showLoadingImg();
	YAHOO.util.Connect.asyncRequest('POST' , "getUserPreviledgedVendorList.action" , callback , "" ); 
}

////Get BAN list by Vendor id ////
function getBanListByVendorId(selIndex){
	if(YAHOO.util.Dom.get('form0_searchReconcileVO_vendorId').value=='all'){
		updateDropdownList([], "id", "no", 'form0_searchReconcileVO_banId',selIndex, {key:"all",value:"All"});
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
			updateDropdownList(data, "id", "no", 'form0_searchReconcileVO_banId',selIndex, {key:"all",value:"All"});
			hideLoadingImg();
		},
		failure: showError
	};

	var pdata = "selVendorId=" + YAHOO.util.Dom.get('form0_searchReconcileVO_vendorId').value;
	//showLoadingImg();
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
//reset form element
function resetFormElementValue(){
	cleanFormElementValue();
	//modified by xinyu.Liu on 2010.5.12 for CCM-42
	FIC_checkForm(form0);
	//update Vendor and Ban drop down list
	getVendorListByVendorId(0);
	updateDropdownList([], "id", "no", 'form0_searchReconcileVO_banId',0, {key:"all",value:"All"});
	
}


//Empty form elements
YAHOO.util.Event.onDOMReady(function(){
	cleanFormElementValue();
	initDateNotValid();
});

//Automatic gain form elements
var aotoPostFormDataUi = function(formId){
	var postData = ""; 
	var doms = YAHOO.util.Selector.query('*[name]',formId);
	var disableNameArray = [];
	var allValue = "all";
	
	YAHOO.util.Dom.batch(doms,function(o){
		for(var i=0; i<disableNameArray.length; i++){
			if(disableNameArray[i] == o.name) return;
		}
		var tagName = o.tagName.toLowerCase();
		if(tagName == 'input'){
			var type = o.type.toLowerCase();
			if(type == 'checkbox'){
				disableNameArray.push(o.name);
				var checks = YAHOO.util.Selector.query('input[name='+o.name+'][checked]',formId);
				var valueStr = "";
				for(var n=0; n<checks.length; n++){
					valueStr += checks[n].value + ",";
				}
				if(valueStr.length > 0) valueStr = valueStr.substring(0,valueStr.length-1);
				if(valueStr!='')postData += o.name + "=" + valueStr + "&";
			}else if(type == 'radio'){
				disableNameArray.push(o.name);
				var radios = YAHOO.util.Selector.query('input[name='+o.name+']',formId);
				for(var n=0; n<radios.length; n++){
					if(radios[n].checked == true || radios[n].checked == 'true' || radios[n].checked == 'checked'){
						postData += radios[n].name+"="+radios[n].value + "&";
						break;
					} 
				}
			}else{
				if(o.value!='')postData += o.name+"="+o.value + "&";
			}
		}else{
			if(o.value!=allValue)postData += o.name+"="+o.value + "&";
		}
	});
	if(postData.length > 0) postData = postData.substring(0,postData.length-1);
	return postData;
}
/**
 */
function resetRadioButton(){
	form0.selectReconcileDueRadio[0].checked=true;
}
function rerenderVendorAndBanList(vendors, bans){
	updateDropdownList(vendors, "id", "name", 'form0_searchReconcileVO_vendorId',0,{key:"all",value:"All"});
	updateDropdownList(bans, "id", "no", 'form0_searchReconcileVO_banId',0, {key:"all",value:"All"});
}


//Date is not correct boxes
// Moidefied By Chao.Liu to 10/07/22 AM
function initDateNotValid() {

	YAHOO.util.Dom.removeClass("dateNotValid", "date-not-valid");
	
	YAHOO.ccm.container.dateNotValid = new YAHOO.widget.Dialog("dateNotValid", 
							{ width : "30em",
							  fixedcenter : true,
							  visible : false, 
							  constraintoviewport : true
							});
	YAHOO.ccm.container.dateNotValid.render();
};

//changRadio by xinyu.Liu beijing time 2010.5.12
function changRadio(type){
	if(type==0)
		form0.selectReconcileDueRadio[0].checked=true;
 	if(type==1)
		form0.selectReconcileDueRadio[1].checked=true;
}
