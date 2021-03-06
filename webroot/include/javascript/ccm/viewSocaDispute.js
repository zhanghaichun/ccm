var poposalSumAmount = 0;
var boxName = "";
var itemTypeIdTab = null;
var parentProposalId = null;
var invoiceItemIdAcc = "";

// Error uploaded scoa data excel file path;
var uploadedSCOADataErrorFilePath;

var mrcVariance = null;

actionUri = {
	updateProposalObjectUri : "updateProposalObject.action",
	getInvoiceHyperlinkUri : "getInvoiceHyperlink.action",
	// Browse Invoice 下的 all tab 中的 proposal list 数据条数查询。
	getProposalViewListPageNoUri : "getProposalViewListPageNo.action",
	// Browse Invoice 下的 all tab 中的 proposal list 数据查询。
	searchProposalListUri : "searchProposalList.action",
	updateGroupProposalObjectUri : "updateGroupProposalObject.action",
	getAccordionViewUri : "getAccordionView.action",
	getEmptySCOAListPageNoUri : "getEmptySCOAListPageNo.action",
	searchEmptySCOAListUri : "searchEmptySCOAList.action",
	getDisputeItemsPageNoUri : "getDisputeItemsPageNo.action",
	searchDisputeItemsListUri : "searchDisputeItemsList.action",
	getItemTypePageNoUri : "getItemTypePageNo.action",
	searchItemTypeListUri : "searchItemTypeList.action",
	// Invoice item detail 这个页面中的SCOA and Dispute tab下的所有下载功能。
	downloadSOCADisputeExcelUri : "downloadSOCADisputeExcel.action",
	saveExcelByInvoiceUri : "downloadInvoiceExcel.action",
	saveExcelByInvoiceValidationUri : "downloadInvoiceValidationExcel.action",
	saveExcelByTelephoneNumberValidationUri : "downloadTelephoneNumberValidationExcel.action",
	downloadUsageReportUri : "downloadUsageReport.action",
	auditInvoiceUri : "auditInvoice.action",
	initializedStoredDataUri : "initializedStoredData.action",
	updateProposalToCreditUri : "updateProposalToCredit.action",
	updateSCOACodingUri : "updateSCOACoding.action",
	searchCountListUri : "searchCountList.action",
	getAccordionTabViewUri : "getAccordionTabView.action",
	getEmptySCOAListTabPageNoUri : "getEmptySCOAListTabPageNo.action",
	searchEmptySCOAListTabUri : "searchEmptySCOAListTab.action",
	// 搜索 SCOA Coding tab下的列表中的信息。
	searchSCOACodingTabViewStrUri : "searchSCOACodingTabViewStr.action",
	getDisputeItemsListTabPageNoUri : "getDisputeItemsListTabPageNo.action",
	searchDisputeItemsListTabUri : "searchDisputeItemsListTab.action",
	// Browse Invoice 下的 除了all tab，其他invoice item type tab 中的 proposal list 数据条数查询。
	getBrowseInvoiceListTabPageNoUri : "getBrowseInvoiceListTabPageNo.action",
	// Browse Invoice 下的 除了all tab，其他invoice item type tab 中的 proposal list 数据查询。
	searchBrowseInvoiceListTabUri : "searchBrowseInvoiceListTab.action",
	getItemTypeListTabPageNoUri : "getItemTypeListTabPageNo.action",
	searchItemTypeListTabUri : "searchItemTypeListTab.action",
	searchListTabDataUri : "searchListTabData.action",
	updateSCOACodingSingleUri : "updateSCOACodingSingle.action",
	recodeTaxInvoiceUrl : "recodeTaxInvoice.action",
	doFindCircuitDetail : "doFindCircuitDetail.action",
	searchValidationResult : "searchValidationResult.action",
	deleteInvoiceNote : "deleteInvoiceNote.action",
	updateBanAutoPayUri:'updateBanAutoPay.action'
		
};

tips = {
	choiceSCOATips : "There is no choice of SCOA, please check, if not please delete.",
	enterAmountTips : "Enter the amount is not correct.",
	enterAmountLargeTips : "Enter the total amount is greater than the amount of the original !",
	notBecauseDisputeTips : "Can not move because there is dispute.",
	outOfRangeTips : "Can not move because there is item not in OCC/Adjustment/Credit Section.",
	creditNotZeroTips : "Can not group dispute because there is credit.",
	notSelectItemTips : "You have not selected any item !",
	slectItemTooTips : "Can be modified to select a data !",
	notHaveAmountTips : "Selected data have value in disputeAmount and creditAmount.",
	amountCanZeroTips : "Either dispute amount or credit amount has to be 0.",
	permissionDeniedTips : "Invoice is not grant approval.",
	noPrivilegeTips : "No Privilege"
};

recordProposalBoxId = {};
pageListName = {};

YAHOO.util.Event.onDOMReady(function () {
	var tabView = new YAHOO.widget.TabView("demo");
	showCircuitDetailWindow();
	$("#demo li a:eq(0)").click();
	tabView.selectTab(0);
	editProposalWindow();
	editSCOACodingWindow();
	editMoveToCreditWindow();
	editSCOACodingSingleWindow();

	// Render upload SCOA failed message window. 
	renderUploadSCOAFailedMessageWindow();

	getHyperlink();
	exemptionNotes();

	window.mrcVariance = new MRCVariance();
	window.mrcVariance.initMasterInventoryDetailsListPopupContainerControl();

});
function getHeight() {
	var height1 = document.getElementById("heightID").offsetHeight;
	var height2 = document.getElementById("topheight").offsetHeight;
	document.getElementById("lefth").style.height=height1-height2-57; 
}

/**
 * Brower Invoice Accordion.
 * @param  {[type]} accordionName [description]
 * @param  {[type]} invoiceItemId [description]
 * @param  {[type]} labelName     [description]
 * @param  {[type]} label         [description]
 * @return {[type]}               [description]
 */
initializationTabName = function(accordionName, invoiceItemId, labelName, label) { 

	var postDataText = "svo.invoiceId=" + invoiceId; 
	postDataText += "&svo.accordionName=" + accordionName;
	postDataText += "&svo.invoiceItemId=" + invoiceItemId;

	showLoadingModalLayer();
	
	$.ajax({
        url: actionUri.getAccordionTabViewUri,
        type: "POST",
        dataType: "text",
        async: false,
        cache: false,
        data: postDataText,
        success: function(o){

            if (o.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                window.location.reload();
                return;
            }

			var data = eval("(" + o + ")");
			var rows = data.data;
			var bo = false;
			var tabId = 'tab_' + accordionName;
			if(labelName != null) tabId += '_' + space(labelName);
			document.getElementById(tabId).firstChild.innerHTML = "";
			
			if (data.error) {

				itemTypeIdTab = null;

			} else {

				if(rows != undefined){

					for (var i = 0; i < rows.length; i++) {
						if (rows[i].id == itemTypeIdTab) bo = true;
					}

					if(!bo) itemTypeIdTab = null;
				}

			} // /.data.error
			
			if(itemTypeIdTab == null){

				document.getElementById(tabId).firstChild.innerHTML += '<li title="active" class="selected" ><a href="#" onclick="tabStyle(this.parentNode);refreshListTab(\''+accordionName+'\',null,\''+invoiceItemId+'\',\''+labelName+'\',\''+label+'\');"><em>All</em></a></li>';
			} else {

				if(bo){
					document.getElementById(tabId).firstChild.innerHTML += '<li title="" class="" ><a href="#" onclick="tabStyle(this.parentNode);refreshListTab(\''+accordionName+'\',null,\''+invoiceItemId+'\',\''+labelName+'\',\''+label+'\');"><em>All</em></a></li>';
				}else{
					document.getElementById(tabId).firstChild.innerHTML += '<li title="active" class="selected" ><a href="#" onclick="tabStyle(this.parentNode);refreshListTab(\''+accordionName+'\',null,\''+invoiceItemId+'\',\''+labelName+'\',\''+label+'\');"><em>All</em></a></li>';
				}

			} // /.itemTypeIdTab
			
			if(data.error){

				return;

			} else {

				if(rows != undefined){

					for (var i = 0; i < rows.length; i++) {

						var row = rows[i];
						if (row.id == itemTypeIdTab) {

							var tabStr = '<li title="active" class="selected" id=\'__' + accordionName + '_' + row.id + '\' ><a href="#" onclick="tabStyle(this.parentNode);refreshListTab(\''+accordionName+'\','+row.id+',\''+invoiceItemId+'\',\''+labelName+'\',\''+label+'\');"><em>'+replacement(row.itemTypeName)+' / '+row.numberLine+' / $'+row.totalAmount+'</em></a></li>';

						} else {

							var tabStr = '<li title="" class="" id=\'__' + accordionName + '_' + row.id + '\' ><a href="#" onclick="tabStyle(this.parentNode);refreshListTab(\''+accordionName+'\','+row.id+',\''+invoiceItemId+'\',\''+labelName+'\',\''+label+'\');"><em>'+replacement(row.itemTypeName)+' / '+row.numberLine+' / $'+row.totalAmount+'</em></a></li>';

						}

						document.getElementById(tabId).firstChild.innerHTML += tabStr;
					}

				}

			} // /.data.error

			delete rows;
			delete data;

			// Add 'Variance' Tab.
			if ( accordionName == 'Browse' ) {

				window.mrcVariance.addVarianceTabButtonToBrowseAccordion(tabId);
				
			}

			hideLoadingModalLayer();

        },

		error : showError
     });
	 return "";
};

function searchCountListBrowseInvoice(){
	showLoadingModalLayer();
	var callback = {
			success: function(o){
		if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
			window.location.reload();
			return;
		}
		var data = eval("("+o.responseText+")");
		if(data.error){
			alert("Error: " + data.error);
			return;
		}
		
		var strBrowseInvoice = " Browse Invoice / count: " + data.count + " / Payment Amount: " + data.paymentSum + " / Dispute Amount: " + data.disputeSum + " / Credit Amount: " + data.creditSum ;
		document.getElementById('Browse_Invoice_name').innerHTML = strBrowseInvoice;
		delete data;
		hideLoadingModalLayer();
	},
	failure:showError
	};
	previousProposalData = "svo.invoiceId=" + invoiceId + "&svo.accordionName=BrowseInvoice";
	YAHOO.util.Connect.asyncRequest("POST", actionUri.searchCountListUri, callback, previousProposalData);
}
function searchCountListSCOA(){
	showLoadingModalLayer();
	var callback = {
		success: function(o){
			if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                window.location.reload();
                return;
            }
			var data = eval("("+o.responseText+")");
			if(data.error){
				alert("Error: " + data.error);
				return;
			}
			var strEmptySCOA = " SCOA Coding / count: " + data.count + " / Payment Amount: " + data.paymentSum + " / Dispute Amount: " + data.disputeSum + " / Credit Amount: " + data.creditSum ;
			document.getElementById('SCOA_Coding_name').innerHTML = strEmptySCOA;
			delete data;
			hideLoadingModalLayer();
		},
		failure:showError
	};
	previousProposalData = "svo.invoiceId=" + invoiceId + "&svo.accordionName=EmptySCOA";
	YAHOO.util.Connect.asyncRequest("POST", actionUri.searchCountListUri, callback, previousProposalData);
}
 
function searchCountListItem(instance){
	showLoadingModalLayer();
	var callback = {
		success: function(o){
			if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                window.location.reload();
                return;
            }
			var data = eval("("+o.responseText+")");
			if(data.error){
				alert("Error: " + data.error);
				return;
			}
			var strItem = underline(data.labelName) + " / count: " + data.count + " / Payment Amount: " + data.paymentSum + " / Dispute Amount: " + data.disputeSum + " / Credit Amount: " + data.creditSum ;
			document.getElementById(space(data.labelName) + '_name').innerHTML = strItem;
			delete data;
			hideLoadingModalLayer();
		},
		failure:showError
	};
	previousProposalData = window[instance].paramStr + "&svo.accordionName=ItemLabelList";
	YAHOO.util.Connect.asyncRequest("POST", actionUri.searchCountListUri, callback, previousProposalData);
}

function searchCountListDispute(){
	showLoadingModalLayer();
	var callback = {
		success: function(o){
			if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                window.location.reload();
                return;
            }
			var data = eval("("+o.responseText+")");
			if(data.error){
				alert("Error: " + data.error);
				return;
			}
			var strDisputeItems = " Dispute Items / count: " + data.count + " / Payment Amount: " + data.paymentSum + " / Dispute Amount: " + data.disputeSum + " / Credit Amount: " + data.creditSum ;
			document.getElementById('Disputed_Items_name').innerHTML = strDisputeItems;
			delete data;
			hideLoadingModalLayer();
		},
		failure:showError
	};
	previousProposalData = "svo.invoiceId=" + invoiceId + "&svo.accordionName=DisputeItems";
	YAHOO.util.Connect.asyncRequest("POST", actionUri.searchCountListUri, callback, previousProposalData);
}

//Stored procedure is initialized
function initializedStored(){
	YAHOO.util.Connect.asyncRequest("POST", actionUri.initializedStoredDataUri + "?svo.invoiceId="+invoiceId, "");
}

function clearTips(){
	document.getElementById('__show_move_to_credit_type_invoice').innerHTML = "";
	document.getElementById('__show_move_to_credit_amount_invoice').innerHTML = "";
	document.getElementById('__show_move_to_credit_type_SCOA').innerHTML = "";
	document.getElementById('__show_move_to_credit_amount_SCOA').innerHTML = "";
	document.getElementById('__show_move_to_credit_type_disputed').innerHTML = "";
	document.getElementById('__show_move_to_credit_amount_disputed').innerHTML = "";
	document.getElementById('__show_group_dispute_invoice').innerHTML = "";
	document.getElementById('__show_group_dispute_SCOA').innerHTML = "";
	document.getElementById('__show_group_dispute_disputed').innerHTML = "";
	document.getElementById('__show_scoa_coding_single_prompt').innerHTML = "";
	document.getElementById('__show_scoa_coding_single_prompt_amount').innerHTML = "";
	
	document.getElementById('__notes').value="";
	$("#addDisputeFile").html("");
	var string ="<div id =\"DisputeFile1\" style=\"padding:3px 0 0 0;height: 19px;\" ><div style=\"float:left;\">" 	 
	+"<input style=\"height:19px;width:150px;\" type='text' id=\"__up_load_text_invoiceDetail_DisputeFile1_1\" disabled=\"disabled\"></div>" 
	+"<div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"		
	+"<input id=\"__upload_invoice_1\" onchange=\"document.getElementById('__up_load_text_invoiceDetail_DisputeFile1_1').value=this.value;\"  class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
	+"<input style=\"height:19px;width:60px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/>"
	+"</span></div><div class=\"approveicon\" onclick=\"deletefile('DisputeFile1');\"></div><div class=\"clear\" ></div></div>"
	
	+"<div id =\"DisputeFile2\" style=\"padding:3px 0 0 0;height: 19px;\" ><div style=\"float:left;\">" 	 
	+"<input style=\"height:19px;width:150px;\" type='text' id=\"__up_load_text_invoiceDetail_DisputeFile1_2\" disabled=\"disabled\"></div>" 
	+"<div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"		
	+"<input id=\"__upload_invoice_2\" onchange=\"document.getElementById('__up_load_text_invoiceDetail_DisputeFile1_2').value=this.value;\"  class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
	+"<input style=\"height:19px;width:60px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/>"
	+"</span></div><div class=\"approveicon\" onclick=\"deletefile('DisputeFile2');\"></div><div class=\"clear\" ></div></div>"
	
	+"<div id =\"DisputeFile3\" style=\"padding:3px 0 0 0;height: 19px;\" ><div style=\"float:left;\">" 	 
	+"<input style=\"height:19px;width:150px;\" type='text' id=\"__up_load_text_invoiceDetail_DisputeFile1_3\" disabled=\"disabled\"></div>" 
	+"<div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"		
	+"<input id=\"__upload_invoice_3\" onchange=\"document.getElementById('__up_load_text_invoiceDetail_DisputeFile1_3').value=this.value;\"  class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
	+"<input style=\"height:19px;width:60px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/>"
	+"</span></div><div class=\"approveicon\" onclick=\"deletefile('DisputeFile3');\"></div><div class=\"clear\" ></div></div>";
	
	$("#addDisputeFile").append(string);
	
	document.getElementById('__upload_invoice_1').outerHTML += "";
	document.getElementById('__upload_invoice_2').outerHTML += "";
	document.getElementById('__upload_invoice_3').outerHTML += "";
	
	document.getElementById('_show_scoa_coding_prompt').innerHTML = "";
	document.getElementById('__notes_SCOA_coding').value = "";
	SCOACodeing.setValue("");
}

//Open browse invoice
function refreshList(bo){
	itemTypeIdTab = null;
	initializedStored();
	if(bo != true) {
		if (document.getElementById('__browse_invoice').style.display == "block") {
			deleteAllPageList();
			return;
		}
	}
	YAHOO.ccm.container.editProposalWindow.hide();
	clearTips();
	initializationTabName("Browse",null,null,null);
	getProposalViewList();
}

//Open Empty SCOA
function refreshEmptySCOAList(){
	itemTypeIdTab = null;
	initializedStored();
	if (document.getElementById('__empty_SCOA').style.display == "block"){
		deleteAllPageList();
		return;
	} 
	YAHOO.ccm.container.editProposalWindow.hide();
	clearTips();
	initializationTabName("SCOA",null,null,null);
	getEmptySCOAList();
}

//Open Dispute Items
function refreshDisputeItemsList(){
	itemTypeIdTab = null;
	initializedStored();
	if (document.getElementById('__disputed_items').style.display == "block") {
		deleteAllPageList();
		return;
	}
	YAHOO.ccm.container.editProposalWindow.hide();
	clearTips();
	initializationTabName("Disputed",null,null,null);
	getDisputeItemsList();
}

//Open Item Type
function getItemTypeOpenAcc(invoiceItemId,labelName,label,bo,box){
	if(invoiceItemId != "" )invoiceItemIdAcc = invoiceItemId;
	initializedStored();
	boxName = box;
	var divId = '__'+space(labelName)+'_list_acc';
	if(bo){
		if (document.getElementById(divId).style.display == "block") {
			return;
		}
	}
	YAHOO.ccm.container.editMoveToCredit.hide();
	YAHOO.ccm.container.editProposalWindow.hide();
	clearTips();
	var instance = space(labelName + label);
	
	if(invoiceItemId != ""){
		window[instance].voParam = {
			invoiceId:invoiceId,
			itemTypeId:itemTypeIdTab,
			invoiceItemId:invoiceItemId,
			labelName:labelName
		};
	}
	
	if (!SANINCO.ifAllGranted(Constants.FUNCTION.invoiceReviewAndPropsal, Constants.FUNCTION.invoiceReviewAndPropsalAction)) {
		 window[instance].cols[0].display = "none";
	}
	initializationTabName("ItemType",invoiceItemIdAcc,labelName,label);
	if(itemTypeIdTab == null){
		window[instance].start();
	}else{
		window[instance].reload();
	}
	searchCountListItem(instance);
	recordProposalBoxId = {};
}

//Refresh
function showList(){
	initializedStored();
	clearTips();
	YAHOO.ccm.container.editMoveToCredit.hide();
	YAHOO.ccm.container.editProposalWindow.hide();
	searchCountListSCOA();
	searchCountListDispute();
	if(document.getElementById('__browse_invoice').style.display == "block"){
		initializationTabName("Browse",null,null,null);
//		if(itemTypeIdTab == null){
//			getProposalViewList();
//		}else{
		document.getElementsByName('box1')[0].checked = false;
     	chooseAll('box1','box_1');
		ProposalViewListPage.reload();
//		}
	}
	if (document.getElementById('__empty_SCOA').style.display == "block") {
		initializationTabName("SCOA",null,null,null);
//		if(itemTypeIdTab == null){
//			getEmptySCOAList();
//		}else{
		document.getElementsByName('box2')[0].checked = false;
	    chooseAll('box2','box_2');
		EmptySCOAListPage.reload();
//		}
	}
	if(document.getElementById('__disputed_items').style.display == "block"){
		initializationTabName("Disputed",null,null,null);
//		if(itemTypeIdTab == null){
//			getDisputeItemsList();
//		}else{
		document.getElementsByName('box3')[0].checked = false;
		chooseAll('box3','box_3');
		DisputeItemsListPage.reload();
//		}
	} 
	if (typeof  disputeTabPage !="undefined") {
		disputeTabPage.reload();
		getDisputeAmountTotal();
	}
	recordProposalBoxId = {};
}

function deleteAllPageList(){
	if(window.ProposalViewListPage)ProposalViewListPage.clean();
	if(window.EmptySCOAListPage)EmptySCOAListPage.clean();
	if(window.DisputeItemsListPage)DisputeItemsListPage.clean();
	for(var i in pageListName){
		if(window.window[pageListName[i]])window[pageListName[i]].clean();
	}
}

//edit ProposalData by SCOACode and note
function editSCOACodingData(proposalBoxId,fun){
	if((!verificationSCOACodingId()) | (!verificationNotes2('__notes_SCOA_coding'))) return;
	var callback = {
		success: function(o){
			if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                window.location.reload();
                return;
            }
			var data = eval("("+o.responseText+")");
			if(data.error){
				alert("Error: " + data.error);
				return;
			}
			eval(fun);
			
		},
		failure:showError
	};
	YAHOO.ccm.container.editSCOACoding.hide();
	var previousProposalData = editSCOACodingDataFromUi(proposalBoxId,'__notes_SCOA_coding');
	YAHOO.util.Connect.asyncRequest("POST", actionUri.updateSCOACodingUri, callback, previousProposalData );
}

function splitValue(){
	var value = "";
	var array = customMotiScoa.getParam("custom_moti_scoa_1");
	for (var i = 0; i < array.length; i++) {
		if(i != 0) value += ",";
		value = value + array[i][0] + "," + array[i][1];
	}
	return value;
}

function editSCOACodingDataFromUi(proposalBoxId,noteDiv){
	var postData = "svo.boxInId=" + proposalBoxId 
		 + "&svo.scoaCodeId=" + SCOACodeing.getValue()
		 + "&svo.single=" + splitValue()
		 + "&svo.note=" + ccmEscape(YAHOO.util.Dom.get(noteDiv).value);
	return postData;
}

//verification SCOACodingId not null
function verificationSCOACodingId(){
	if(SCOACodeing.getValue() == ""){
		document.getElementById('_show_scoa_coding_prompt').innerHTML = "Must choose the SCOA!";
		return false;
	}else{
		document.getElementById('_show_scoa_coding_prompt').innerHTML = "";
		return true;
	}
}

//show edit SCOA Coding window
function editSCOACoding(labelName,label){
	clearTips();
	if(judgementRightsDispute("")) return;
	var proposalBoxId = traversalId();
	var divId = "";
	if(labelName != undefined && label != undefined) divId = space(labelName + label);	
	if(!groupDisputeBoxView(proposalBoxId,divId)) return;
	clearTips();
	YAHOO.ccm.container.editSCOACoding.show();
	if(divId == ""){
		targetFun='editSCOACodingData("'+proposalBoxId+'","showList()")';
	}else{
		targetFun='editSCOACodingData("'+proposalBoxId+'","getItemTypeOpenAcc(\'\',\''+labelName+'\',\''+label+'\',false,\''+boxName+'\')")';
	}
}

//show edit Single SCOA Coding window
function showEditSCOACodingSingle(labelName,label){
	clearTips();
	if(judgementRightsDispute("")) return;
	var proposalBoxId = traversalId();
	var divId = "";
	if(labelName != undefined && label != undefined) divId = space(labelName + label);	
	if(!groupDisputeOneBoxView(proposalBoxId,divId)) return;
	clearTips();
	customMotiScoa.clear("custom_moti_scoa_1");
	customMotiScoa.reset("custom_moti_scoa_1");
	document.getElementById('__notes_SCOA_coding_single').value = "";
	YAHOO.ccm.container.editSCOACodingSingle.show();
	if(divId == ""){
		targetFun='editSCOACodingSingleData("'+proposalBoxId+'","showList()")';
	}else{
		targetFun='editSCOACodingSingleData("'+proposalBoxId+'","getItemTypeOpenAcc(\'\',\''+labelName+'\',\''+label+'\',false,\''+boxName+'\')")';
	}
}

function editSCOACodingSingleData(proposalBoxId,fun){
	if((!editSCOACodingSingle()) | (!verificationNotes2('__notes_SCOA_coding_single'))) return;
	var callback = {
		success: function(o){
			if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                window.location.reload();
                return;
            }
			var data = eval("("+o.responseText+")");
			if(data.error){
				alert("Error: " + data.error);
				return;
			}
			eval(fun)
		},
		failure:showError
	};
	YAHOO.ccm.container.editSCOACodingSingle.hide();
	previousProposalData = editSCOACodingDataFromUi(proposalBoxId,'__notes_SCOA_coding_single');
	YAHOO.util.Connect.asyncRequest("POST", actionUri.updateSCOACodingSingleUri, callback, previousProposalData );
}

function editSCOACodingSingle(){
	clearTips();
	var amountSun = 0;
	var scoaBo = true;
	var amountBo = true;
	var array = customMotiScoa.getParam("custom_moti_scoa_1");
	var proposalAmount =  traversalAmount();
	for (var i = 0; i < array.length; i++) {
		if(array[i][0] == "" || array[i][0] == undefined || array[i][0] == null){
			if (scoaBo) {
				document.getElementById('__show_scoa_coding_single_prompt').innerHTML = tips.choiceSCOATips;
				scoaBo = false;
			}
		}
		if(array[i][1].match(/^(-?\d+)(\.\d+)?$/)){
			amountSun = Number(amountSun) + Number(array[i][1]);
		}else{
			if(amountBo){
				document.getElementById('__show_scoa_coding_single_prompt_amount').innerHTML = tips.enterAmountTips;
				amountBo = false;
			}
		}
	}
//	if(amountBo && (Number(amountSun) > Number(proposalAmount))){
//		document.getElementById('__show_scoa_coding_single_prompt_amount').innerHTML = tips.enterAmountLargeTips;
//		amountBo = false;
//	}
	if(scoaBo && amountBo) return true;
	return false;
}

//edit Move to Credit 
function editMoveCredit(labelName,label){
	clearTips();
	if(judgementRightsDispute("")) return;
	var proposalBoxId = traversalIdAndAmount();
	var divId = "";
	if(labelName != undefined && label != undefined) divId = space(labelName + label);	
	if(!groupDisputeBoxView(proposalBoxId,divId)) return;
	if(!verificationMoveCredit(divId)) return;
	clearTips();
	YAHOO.ccm.container.editMoveToCredit.show();
	if(divId == ""){
		targetFun='updateProposalToCredit("showList()")';
	}else{
		targetFun='updateProposalToCredit("getItemTypeOpenAcc(\'\',\''+labelName+'\',\''+label+'\',false,\''+boxName+'\')")';
	}
}

//update Proposal
function updateProposalToCredit(fun){
	var callback = {
		success: function(o){
			if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                window.location.reload();
                return;
            }
			var data = eval("("+o.responseText+")");
			if(data.error){
				alert("Error: " + data.error);
				return;
			}
			eval(fun)
		},
		failure:showError
	};
	previousProposalData = "svo.boxInId=" + traversalId();
	YAHOO.util.Connect.asyncRequest("POST", actionUri.updateProposalToCreditUri, callback ,previousProposalData);
}

//Verify text entry box
function verificationNotes(id){
	var notes = document.getElementById(id).value;
	if (notes.length > 10 && notes.length < 255) {
		removeClassName(id,'validation-failed');
		addClassName(id,'validation-passed');
		return true;
	}else{
		removeClassName(id,'validation-passed');
		addClassName(id,'validation-failed');
		return false;
	}
}


function verificationNotes2(id){
	var notes = document.getElementById(id).value;
	if ( notes.length < 255) {
		removeClassName(id,'validation-failed');
		addClassName(id,'validation-passed');
		return true;
	}else{
		removeClassName(id,'validation-passed');
		addClassName(id,'validation-failed');
		return false;
	}
}

//Format amount
function formatAmount(str, median) {  
	median = median > 0 && median <= 20 ? median : 2;  
	str = parseFloat((str + "").replace(/[^\d\.-]/g, "")).toFixed(median) + "";  
	var l = str.split(".")[0].split("").reverse(),  
	r = str.split(".")[1];  
	t = "";  
	for(i = 0; i < l.length; i ++ )  {  
		t += l[i] + ((i + 1) % 3 == 0 && ((i + 1) != l.length) && (str < 0 && (i + 2) != l.length) ? "," : "");  
	}  
	return t.split("").reverse().join("") + "." + r;  
}  

//verification Move to Credit
function verificationMoveCredit(divId){
	var bo = true;
	for(var i in recordProposalBoxId){
		if(recordProposalBoxId[i][0] == true){
			if(Number(recordProposalBoxId[i][2]) != 0){
				if(divId == ""){
					document.getElementById('__show_move_to_credit_amount_invoice').innerHTML = tips.notBecauseDisputeTips;
					document.getElementById('__show_move_to_credit_amount_SCOA').innerHTML = tips.notBecauseDisputeTips;
					document.getElementById('__show_move_to_credit_amount_disputed').innerHTML = tips.notBecauseDisputeTips;
				}else{
					document.getElementById('__show_move_to_credit_amount_'+divId).innerHTML = tips.notBecauseDisputeTips;
				}
				bo = false;
			}
			if (15 <= recordProposalBoxId[i][3] <= 17 || 500000 < recordProposalBoxId[i][3] < 800000) {
				//true	
			}else{
				if(divId == ""){
					document.getElementById('__show_move_to_credit_type_invoice').innerHTML = tips.outOfRangeTips;
					document.getElementById('__show_move_to_credit_type_SCOA').innerHTML = tips.outOfRangeTips;
					document.getElementById('__show_move_to_credit_type_disputed').innerHTML = tips.outOfRangeTips;
				}else{
					document.getElementById('__show_move_to_credit_type_'+divId).innerHTML = tips.outOfRangeTips;
				}
				bo = false;
			}
		}
	}
	return bo;
}

//verification credit Amount not zero
function verificationCreditAmountNot(divId){
	for(var i in recordProposalBoxId){
		if(recordProposalBoxId[i][0] == true){
			if(Number(recordProposalBoxId[i][4]) != 0){
				showInnerHTML("",tips.creditNotZeroTips);
				if(divId != "") document.getElementById('__show_group_dispute_' + divId).innerHTML = tips.creditNotZeroTips;
				return false;
			}
		}
	}
	return true;
}

//get group Dispute Box View
function groupDisputeBoxView(boxId,divId){
	if(boxId != ""){
		showInnerHTML("","");
		if(divId != "") document.getElementById('__show_group_dispute_' + divId).innerHTML = "";
		return true;
	}else{
		showInnerHTML("",tips.notSelectItemTips);
		if(divId != "") document.getElementById('__show_group_dispute_' + divId).innerHTML = tips.notSelectItemTips;
		return false;
	}
}

function groupDisputeOneBoxView(boxId,divId){
	if(boxId != ""){
		if(boxId.split(",").length != 2){
			showInnerHTML("",tips.slectItemTooTips);
			if(divId != "") document.getElementById('__show_group_dispute_' + divId).innerHTML = tips.slectItemTooTips;
			return false;
		}else{
			for(var i in recordProposalBoxId){
				if(recordProposalBoxId[i][0] == true){
					if((Number(recordProposalBoxId[i][2]) == 0) && (Number(recordProposalBoxId[i][4]) == 0)){
						showInnerHTML("","");
						if(divId != "") document.getElementById('__show_group_dispute_' + divId).innerHTML = "";
						return true;
					}else{
						showInnerHTML("",tips.notHaveAmountTips);
						if(divId != "") document.getElementById('__show_group_dispute_' + divId).innerHTML = tips.notHaveAmountTips;
						return false;
					}
				}
			}
		}
	}else{
		showInnerHTML("",tips.notSelectItemTips);
		if(divId != "") document.getElementById('__show_group_dispute_' + divId).innerHTML = tips.notSelectItemTips;
		return false;
	}
}

//verification Dispute != 0 And Credit != 0
function verificationDisputeAndCredit(disputeAmount,creditAmount,labelName){
	if(Number(disputeAmount) != 0 && Number(creditAmount) != 0){
		showInnerHTML("",tips.amountCanZeroTips);
		if(labelName != "") document.getElementById('__show_group_dispute_'+space(labelName)+'').innerHTML = tips.amountCanZeroTips;
		return true;
	}else{
		showInnerHTML("","");
		if(labelName != "") document.getElementById('__show_group_dispute_'+space(labelName)+'').innerHTML = "";
	}
	return false;
}

//edit proposals
function editProposals(labelName,label){
	clearTips();
	if(judgementRightsDispute("")) return;
	var proposalBoxId = traversalId();
	var divId = "";
	if(labelName != undefined && label != undefined) divId = space(labelName + label);
	if(!groupDisputeBoxView(proposalBoxId,divId)) return;
	if(!verificationCreditAmountNot(divId)) return;
	
	//document.getElementById('__payment_radio').checked = false;
	document.getElementById('__dispute_radio').checked = true;
	document.getElementById('__notes').value = "";
	document.getElementById('__txt_proposalId').value = proposalBoxId;
	document.getElementById('__txt_proposalId_boxId').value = proposalBoxId;
	if(determineNumber()){
		var id = proposalBoxId.split(",")[0];
		if(divId == ""){
			targetFun='updateProposalByTab("'+id+'","showList()")';
		}else{
			targetFun='updateProposalByTab("'+id+'","getItemTypeOpenAcc(\'\',\''+labelName+'\',\''+label+'\',false,\''+boxName+'\')")';
		}
	}else{
		if(divId == ""){
			targetFun='updateGroupProposalByTab("showList()")';
		}else{
			targetFun='updateGroupProposalByTab("getItemTypeOpenAcc(\'\',\''+labelName+'\',\''+label+'\',false,\''+boxName+'\')")';
		}
	}
	YAHOO.ccm.container.editProposalWindow.show();
}

//update Proposal
function updateProposalByTab(id,fun){
	if((!verificationAmount()) || (!verificationNotes2('__notes'))) return;
	if(!scoa_code.requiredValidate()) return;
	var callback = {
		success: function(o){
			if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                window.location.reload();
                return;
            }
			var data = eval("("+o.responseText+")");
			if(data.error){
				alert("Error: " + data.error);
				return;
			}
			eval(fun)
		},
		failure:showError
	};
	previousProposalData = updateProposalByTabDataFromUi(id);
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest("POST", actionUri.updateProposalObjectUri, callback, previousProposalData);
}

//validation Invoice 
function auditInvoice(){
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
		             alert("Validate Successfully!");
		             window.location.reload();
		             hideLoadingModalLayer();
		      }, 
			failure: showError
		};
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest("POST", actionUri.auditInvoiceUri, callback, "svo.invoiceId=" + invoiceId);
}

//update group Proposal
function updateGroupProposalByTab(fun){
	if(!verificationNotes2('__notes')) return;
	var callback = {
		success: function(o){
			if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                window.location.reload();
                return;
            }
			var data = eval("("+o.responseText+")");
			if(data.error){
				alert("Error: " + data.error);
				return;
			}
			eval(fun)
		}, 
		failure:showError
	};
	previousProposalData = updateProposalByTabDataFromUi();
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest("POST", actionUri.updateGroupProposalObjectUri, callback, previousProposalData);
}

function updateProposalByTabDataFromUi(proposalId){
	var disputeAmount = parseFloat(YAHOO.util.Dom.get('__dispute_amount').value.replace(/[^\d\.-]/g, "")) + "";
	var paymentAmount = poposalSumAmount - disputeAmount;
	var postData = "svo.proposalId=" + proposalId 
		 + "&svo.invoiceId=" + invoiceId 
		 + "&svo.boxInId=" + traversalId()
		 + "&svo.paymentAmount=" + paymentAmount
		 + "&svo.disputeAmount=" + disputeAmount
		 + "&svo.description=" + encodeURIComponent(YAHOO.util.Dom.get('__notes').value)
		 + "&svo.scoaCodeId=" + scoa_code.getValue()
		 + "&svo.originatorId=" + YAHOO.util.Dom.get('__originator').value;
/*	if(document.getElementById('__payment_radio').checked){
		postData += "&svo.radio=" + "payment" 
			 + "&svo.disputeReasonId=" + "";
	}else{
		postData += "&svo.radio=" + "dispute"
			 + "&svo.disputeReasonId=" + YAHOO.util.Dom.get('__dispute_category').value;
	}*/
	postData += "&svo.radio=" + "dispute"
	 + "&svo.disputeReasonId=" + YAHOO.util.Dom.get('__dispute_category').value;
	return postData;
}

function traversalIdAndAmount(){
	var proposalBoxId = "";
	var amountSum = 0;
	for(var i in recordProposalBoxId){
		if(recordProposalBoxId[i][0] == true){
			proposalBoxId = proposalBoxId + i + ",";
			amountSum = Number(amountSum) + Number(recordProposalBoxId[i][1]);
		}
	}
	document.getElementById('__move_to_credit_div').innerHTML = "You are going to move " + formatAmount(amountSum,2) + " to credit, please confirm.";
	return proposalBoxId;
}

//Traversal id
function traversalId(){
	var proposalBoxId = "";
	for(var i in recordProposalBoxId){
		if(recordProposalBoxId[i][0] == true){
			proposalBoxId = proposalBoxId + i + ",";
		}
	}
	return proposalBoxId;
}

function traversalAmount(){
	var proposalBoxAmount = 0;
	for(var i in recordProposalBoxId){
		if(recordProposalBoxId[i][0] == true){
			proposalBoxAmount = Number(proposalBoxAmount) + Number(recordProposalBoxId[i][1]);
		}
	}
	return proposalBoxAmount;
}

//Determine the number of
function determineNumber(){
	var a = 0;
	var number = 0;
	var poposalAmount = 0;
	poposalSumAmount = 0;
	for(var i in recordProposalBoxId){
		if(recordProposalBoxId[i][0] == true){
			number++;
			poposalSumAmount = Number(poposalSumAmount) + Number(recordProposalBoxId[i][1]);
			poposalAmount = Number(recordProposalBoxId[i][1]) - Number(recordProposalBoxId[i][2]);
			a = i;
		}
	}
	if(number == 1){
		document.getElementById('__group_dispute').style.display="none";
		document.getElementById('__edit_proposal').style.display="block";
		document.getElementById('__payment_amount').innerHTML = formatAmount(poposalAmount,2);
		document.getElementById('__dispute_amount').value = formatAmount((Number(poposalSumAmount) - Number(poposalAmount)),2);
		scoa_code.setValue(recordProposalBoxId[a][6]);
		echoDropDownListByValue('__dispute_category',recordProposalBoxId[a][7]);
		
		if(recordProposalBoxId[a][5] == "" || recordProposalBoxId[a][5] == undefined || recordProposalBoxId[a][5] == null){
			echoDropDownListByValue("__originator",2);
		}else{
			echoDropDownListByValue("__originator",recordProposalBoxId[a][5]);
		}
		
		return true;
	}else{
	//	document.getElementById('__payment_radio').checked = true;
		document.getElementById('__group_dispute').style.display="block";
		document.getElementById('__edit_proposal').style.display="none";
		document.getElementById('__total_amount').innerHTML = formatAmount(poposalSumAmount,2);
		scoa_code.setValue("");
		document.getElementById('__dispute_category').options[0].selected = true;
		return false;
	}
}


//edit Proposal Amount
function editProposalAmount(){
	if(!verificationAmount()) return;
	var disputeAmount = parseFloat(document.getElementById('__dispute_amount').value.replace(/[^\d\.-]/g, "")) + "";
//	if (disputeAmount < 0) {
//		alert("Disput amount can not be negative value.");
//		document.getElementById('__dispute_amount').value = "";
//		return
//	}
	document.getElementById('__dispute_amount').value = formatAmount(disputeAmount,2);
	var paymentAmount =  poposalSumAmount - disputeAmount;
	if(formatAmount(paymentAmount,2) < 0){
		removeClassName('__dispute_amount','validation-passed');
		addClassName('__dispute_amount','validation-failed');
		return;
	}
	paymentAmount = formatAmount(paymentAmount,2);
	document.getElementById('__payment_amount').innerHTML = paymentAmount;
}

//Verify text entry box
function verificationAmount(){
	var disputeAmount = parseFloat(document.getElementById('__dispute_amount').value.replace(/[^\d\.-]/g, "")) + "";
	if (disputeAmount.match(/^(-?\d+)(\.\d+)?$/)) {
		removeClassName('__dispute_amount','validation-failed');
		addClassName('__dispute_amount','validation-passed');
		return true;
	}else{
		removeClassName('__dispute_amount','validation-passed');
		addClassName('__dispute_amount','validation-failed');
		return false;
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
			recordProposalBoxId[""+selects[j].value] = [true,selects[j].getAttribute('amount'),selects[j].getAttribute('disputeAmount'),
								selects[j].getAttribute('itemTypeId'),selects[j].getAttribute('creditAmount'),
								selects[j].getAttribute('originatorId'),selects[j].getAttribute('SCOAId'),selects[j].getAttribute('disputeCategoryId')];
		}
		if(checked == false){
			removeClassName(selects[j].parentNode.parentNode,'hightlight');
			recordProposalBoxId[""+selects[j].value] = [false];
		}
	}
} 

//Access check box
function str(id,boxName,amount,disputeAmount,itemTypeId,creditAmount,originatorId,SCOAId,disputeCategoryId){
	var sb = "<input type=\"checkbox\" class='noBorderRadioButton' name=\""+boxName+"\"  amount=\""+amount+"\"  disputeAmount=\""+disputeAmount+"\" "
	       + "itemTypeId=\""+itemTypeId+"\" creditAmount=\""+creditAmount+"\" "
		   + "SCOAId=\""+SCOAId+"\" originatorId=\""+originatorId+"\" disputeCategoryId=\""+disputeCategoryId+"\" "
		   + "value=\""+id+"\" onclick=\"recordBoxId(this);\"/>";
	return sb;
}

//Record the id of
function recordBoxId(o){
	if (o.checked == true) {
		addClassName(o.parentNode.parentNode,'hightlight');
		recordProposalBoxId[""+o.value] = [true,o.getAttribute('amount'),o.getAttribute('disputeAmount'),
								o.getAttribute('itemTypeId'),o.getAttribute('creditAmount'),
								o.getAttribute('originatorId'),o.getAttribute('SCOAId'),o.getAttribute('disputeCategoryId')];
	}
	if (o.checked == false) {
		removeClassName(o.parentNode.parentNode,'hightlight');
		recordProposalBoxId[""+o.value] = [false];
	}
}

//Get the ID check
function checkbox(){
	var str = document.getElementsByName("box");
	var chestrId = "";
	for (i = 0; i < str.length ;i++){
		if(str[i].checked == true){
			chestrId = chestrId + str[i].value + ",";
		}
	}
	return chestrId;
} 

//Traversal amount
function traversalAmount(){
	var groupPoposalAmount = 0;
	for(var i in recordProposalBoxId){
		if(recordProposalBoxId[i][0] == true){
			groupPoposalAmount = Number(groupPoposalAmount) + Number(recordProposalBoxId[i][1]);
		}
	}
	return groupPoposalAmount;
}

//Echo proposalId
function echoProposalId(boxId){
	var str=document.getElementsByName(boxId);
	for(var i in recordProposalBoxId){
		if(recordProposalBoxId[i][0] == true){
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

/**
 * download Invoice Excel
 * 'Download Invoice' 按钮。
 * @return {[type]} [description]
 */
function downloadInvoiceExcel(){
	showLoadingModalLayer();
	var uri = actionUri.saveExcelByInvoiceUri + "?svo.invoiceId="+ invoiceId;
	windowOpen(uri);
	hideLoadingModalLayer();
}

/**
 * download Invoice csv
 * 'Download Validation' 按钮。
 * @return {[type]} [description]
 */
function downloadInvoiceValidationExcel(){
	showLoadingModalLayer();
	var uri = actionUri.saveExcelByInvoiceValidationUri + "?svo.invoiceId="+ invoiceId;
	windowOpen(uri);
	hideLoadingModalLayer();
}

/**
 * Invoice detail 中的 SCOA and Dispute 标签页中
 * 三个手风琴菜单中的 'Download to Excel' 按钮。
 * @param  {[type]} accordionName [description]
 * @return {[type]}               [description]
 */
function getDownloadExcelValue(accordionName){
	document.getElementById('__hiddens').innerHTML = "";
	addInputeHidden('__hiddens','svo.accordionName','hidden',accordionName);
	if (accordionName == "Browse Invoice") {
		splitTitles(ProposalViewListPage.getTitlesParam("titles"));
		splitVo(ProposalViewListPage.paramStr);
	}else if (accordionName == "SCOA Coding") {
		splitTitles(EmptySCOAListPage.getTitlesParam("titles"));
		splitVo(EmptySCOAListPage.paramStr);
	}else if (accordionName == "Disputed Items") {
		splitTitles(DisputeItemsListPage.getTitlesParam("titles"));
		splitVo(DisputeItemsListPage.paramStr);
	}else{
		splitTitles(window[accordionName].getTitlesParam("titles"));
		splitVo(window[accordionName].paramStr);	
	}
	document.getElementById('__from_download').submit();
}

/**
 * 显示 ‘Scoa and dispute ’ 中的下面三个手风琴菜单中的
 * 列表的表头。
 * @param  {[type]} titles [description]
 * @return {[type]}        [description]
 */
function splitTitles(titles){
	//&titles=Item&titles=Payment&titles=Dispute&titles=Credit&titles=Circuit N.....
	var split = titles.split("&titles=");
	for(var i = 0; i < split.length; i++){
		if(split[i] != "") addInputeHidden('__hiddens','titles','hidden',split[i]);
	}
}

function splitVo(paramStr){
	//svo.pageNo=1 svo.recPerPage=10 svo.sortField=ii.id svo.sortingDirection=asc svo.invoiceId=11067 svo.itemTypeId=14
	var split = paramStr.split("&");
	for(var i = 0; i < split.length; i++){
		var nameAndValue = split[i].split("="); //svo.pageNo=1
		if(nameAndValue.length == 2) addInputeHidden('__hiddens',nameAndValue[0],'hidden',nameAndValue[1]);
	}
}

//add Inpute Hidden
function addInputeHidden(divId,name,type,value){
	var div = document.getElementById(divId);
	var input = document.createElement("input");
	input.name = name;
	input.type = type;
	input.value = unescape(value);
	div.appendChild(input);
}

//Replace spaces to underscores
function space(str){
	return str.replace(/( )/g,'_');
}

function underline(str){
	return str.replace(/(_)/g,' ');
}

function replacement(str){
	return str.replace(/(Detail)/g,'');
}

//build Accordion HTML
function buildAccordionTop(name,idName,methodName,accDivId){
	var html = '<h5><a id="'+space(idName)+'_name" href="javascript:void(0)" onclick="'+methodName+'">'+name+'</a></h5>'
			 + '<div id="'+accDivId+'" style="height:100%; display: none;">';
	
	return html;
}

/**
 * Build 'SCOA and Dispute' submenu (Accordion drop-down menu)
 */
function buildAccordionLower(invoiceRole,invoiceActionRole,viewListName,listName,pageListName,pageListName2,buttonName,showName,buttonValue,downloadName){


	var invoiceStatusId; // Invoice status id.

	// Get invoice status id 
	// when render 'Browse Invoice' accordion pull-down menu.
	if ( downloadName == 'Browse Invoice' ) { 

		// Get invoice status id.
		invoiceStatusId = $('#invoiceStatus_divId').attr('data-invoice-status-id');

		// If invoice status id is null or undefined, set default value.
		invoiceStatusId = invoiceStatusId == null ? 21 : invoiceStatusId;

	}

	var html = '<table width="100%" border="0"><tr><td>'
		 + '<table id="'+viewListName+'" style="table-layout:fixed;width:100%;" border="0">'
	     + '<tr><td colspan="4"><div align="left"  style="width:100%;overflow-x:auto;padding-bottom: 20px;">'	
	     + '<div id="'+pageListName2+'"></div><div id="'+listName+'"></div></div></td></tr></table><table><tr><td ><div id="'+pageListName+'"></div>'
	     + '</td>'
		 + '<td >'
		 + '<form id="__from_download" action="'+actionUri.downloadSOCADisputeExcelUri+'?svo.invoiceId='+ invoiceId +'" enctype="multipart/form-data" method="POST" target="_blank">'
		 + '<div id="__hiddens"></div>'
		 + '</form>'
		 + '</td></tr></table></td></tr></table>';


	/* -----------
	Button Groups.
	------------*/
	html += '<div class="scoa-dispute-btnGroups">';

	// 'Download to Excel' button
	html += '<input id="__'+buttonName+'_download_excel_button" class="scoa-dispute--btn" type="button" value="Download to Excel" onclick="getDownloadExcelValue(\''+downloadName+'\');"/>';

	// 权限判断
	if(invoiceRole && invoiceActionRole){

		// 'Dispute' button
		html += '<input id="__'+buttonName+'_group_dispute_button" class="scoa-dispute--btn" type="button" value="Dispute" onclick="editProposals('+buttonValue+');"/>';

		// 'Move to Credit' button
		html += '<input id="__'+buttonName+'_move_credit_button" class="scoa-dispute--btn" type="button" value="Move to Credit" onclick="editMoveCredit('+buttonValue+');"/>';
		 	
		// 'SCOA Coding' button
		html += '<input id="__'+buttonName+'_SCOA_coding_button" class="scoa-dispute--btn" type="button" value="SCOA Coding" onclick="editSCOACoding('+buttonValue+');"/>';

		// 'Cost to Multiple SCOA' button
		html += '<input id="__'+buttonName+'_SCOA_coding_button_single" class="scoa-dispute--btn" type="button" value="Cost to Multiple SCOA" onclick="showEditSCOACodingSingle('+buttonValue+');"/>';


	 	// Draw 'Upload SCOA' form 
	 	// when render 'Browse Invoice' accordion pull-down menu,
	 	// and invoice status id less than 21.
	 	if ( downloadName == 'Browse Invoice' && invoiceStatusId < 21) { 

	 		// 'Upload SCOA' button
	 		html += '<div id="uploadSCOABtnTd" style="display: inline-block;"  class="scoa-dispute--btn">';
	 		html += '<form name="uploadSCOADataForm" id="uploadSCOADataForm" method="post" enctype="multipart/form-data" target="uploadSCOADataPageFrame" action="uploadSCOAData.action">';

	 		html += '<input type="file" class="upload-scoa-file-input" name="uploadSCOAs" multiple onchange="uploadSCOAFilesProcess(this.files)" onclick="judgeUploadSCOARights()">';

	 		html += '<input type="button" value="Upload SCOA" class="scoa-dispute--btn">';
	 		html += '<input type="hidden" name="invoiceId" value='+ invoiceId +'>';
	 		html += '</form>';

	 		html += '<iframe  name="uploadSCOADataPageFrame" id="uploadSCOADataPageFrame" style="display: none; "></iframe>';

	 		html += '</div>'; // /#uploadSCOABtnTd

	 		// 'Download SCOA Template' button
	 		html += '<div id="creatExcelTemplate" style="display: inline-block; margin-left: 0; margin-right: 0;">';

	 		html += '<input type="button" class="scoa-dispute--btn" value="Download SCOA Template"  onclick="creatExcelByTemplate()">';

	 		html += '</div>'; // /#creatExcelTemplate


	 	}

	 	// 'Recode tax' button.
	 	if(buttonName == 'proposal' && !judgementRights()) 
	 		html += '<input id="__'+buttonName+'_recode_tax" class="scoa-dispute--btn" type="button" value="Recode tax" onclick="recodeTaxInvoice();"/>';	

	 }else{

	 	// 'Dispute' button
	 	html += '<input id="__'+buttonName+'_group_dispute_button" type="button" class="scoa-dispute--btn" value="Dispute" onclick="editProposals('+buttonValue+');"/>';

	 	// 'Move to Credit' button
	 	html += '<input id="__'+buttonName+'_move_credit_button" type="button" class="scoa-dispute--btn" value="Move to Credit" onclick="editMoveCredit('+buttonValue+');"/>';
	 	 	
	 	// 'SCOA Coding' button
	 	html += '<input id="__'+buttonName+'_SCOA_coding_button" type="button" class="scoa-dispute--btn" value="SCOA Coding" onclick="editSCOACoding('+buttonValue+');"/>';

	 	// 'Cost to Multiple SCOA' button
	 	html += '<input id="__'+buttonName+'_SCOA_coding_button_single" type="button" class="scoa-dispute--btn" value="Cost to Multiple SCOA" onclick="showEditSCOACodingSingle('+buttonValue+');"/>';

	 

	 	// Draw 'Upload SCOA' form 
	 	// when render 'Browse Invoice' accordion pull-down menu,
	 	// and invoice status id less than 21.
	 	if ( downloadName == 'Browse Invoice' && invoiceStatusId < 21) { 

	 		// 'Upload SCOA' button
	 		html += '<div id="uploadSCOABtnTd" style="display: inline-block;"  class="scoa-dispute--btn">';
	 		html += '<form name="uploadSCOADataForm" id="uploadSCOADataForm" method="post" enctype="multipart/form-data" target="uploadSCOADataPageFrame" action="uploadSCOAData.action">';

	 		html += '<input type="file" class="upload-scoa-file-input" name="uploadSCOAs" multiple onchange="uploadSCOAFilesProcess(this.files)" onclick="judgeUploadSCOARights()">';

	 		html += '<input type="button" value="Upload SCOA" class="scoa-dispute--btn">';
	 		html += '<input type="hidden" name="invoiceId" value='+ invoiceId +'>';
	 		html += '</form>';

	 		html += '<iframe  name="uploadSCOADataPageFrame" id="uploadSCOADataPageFrame" style="display: none; "></iframe>';

	 		html += '</div>'; // /#uploadSCOABtnTd

	 		// 'Download SCOA Template' button
	 		html += '<div id="creatExcelTemplate"  style="display: inline-block; margin-left: 0; margin-right: 0;">';

	 		html += '<input type="button" class="scoa-dispute--btn" value="Download SCOA Template"  onclick="creatExcelByTemplate()">';

	 		html += '</div>'; // /#creatExcelTemplate


	 	}

	 	if(buttonName == 'proposal') 
	 		html += '<input id="__'+buttonName+'_recode_tax" type="button" class="scoa-dispute--btn" value="Recode tax" onclick="recodeTaxInvoice();"/>';	
	}
	
	// Rights hint notes.
	html += '<div class="rights-notes" style="color: red;">';

	html += '<div id="__show_group_dispute_'+showName+'"></div>';
	html += '<div id="__show_move_to_credit_type_'+showName+'"></div>';
	html += '<div id="__show_move_to_credit_amount_'+showName+'"></div>';

	html += '</div>'; // /.rights-notes


	html += '</div>'; // /.scoa-dispute-btnGroups

	html += '</div>'; // /#__tab100

		 
	html += '</div>'; // /.yui-content
	html += '</div>'; // /#tab_Browse | /#tab_SCOA | /#tab_Disputed
	html += '</div>'; // /#__browse_invoice | /#__empty_SCOA | /#__disputed_items

	
	return html;
}

/**
 * 当用户点击 Upload SCOA 按钮的时候，判断权限来决定当前用户
 * 是否可以使用此功能。
 * @return {[type]} [description]
 */
function judgeUploadSCOARights() {

	// Clear rights hint.
	clearTips();

	if(judgementRightsDispute("")) {
		window.event.preventDefault();
		return false;
	}
}

/**
 * Dispose with uploaded SCOA files.
 * @param  {[type]} SCOAFiles [description]
 * @return {[type]}           [description]
 */
function uploadSCOAFilesProcess(SCOAFiles) {
	
	// Show lazy loading control.
	showLoadingModalLayer();
	
	// Submit the form.
	$('#uploadSCOADataForm').submit();

	$('.upload-scoa-file-input').val(''); // 清空 input[file]框中的值。

	// Hide lazy loading control.
	hideLoadingModalLayer();
}

/**
 * 创建 SCOA and Dispute 模板。
 * @return {[type]} [description]
 */
function creatExcelByTemplate(){
	showLoadingModalLayer();

	clearTips();
	
	
	var titles= "titles=Proposal ID&" +
				"titles=SCOA";
	var url = "creatExcelByTemplate.action?"+ titles;
	windowOpen(url);
	hideLoadingModalLayer();
	
}

/**
 * Render upload SCOA failed message box
 * using YAHOO window control
 */
function renderUploadSCOAFailedMessageWindow() {

	YAHOO.ccm.container.uploadSCOAFailedMessageWindow = new YAHOO.widget.Dialog("uploadSCOAFailedMessageBox", { 
		width: "460px",
	    fixedcenter: true,
	    visible: false,
	    constraintoviewport: true,
	    modal: true
	});

	YAHOO.ccm.container.uploadSCOAFailedMessageWindow.render();

	

}

/**
 * Display upload failed message box.
 * @return {[type]} [description]
 */
function showUploadSCOADataFailedMessageBox(errorFilePath) {

	uploadedSCOADataErrorFilePath = errorFilePath;

	// Show failed message box.
	YAHOO.ccm.container.uploadSCOAFailedMessageWindow.show();
}

/**
 * Download uploaded SCOA data error file.
 * @return {[type]} [description]
 */
function downloadSCOADataErrorFile() {

	showLoadingModalLayer();


	window.open("downloadSCOADataErrorFile.action?errorFileName="+ uploadedSCOADataErrorFilePath);

	hideLoadingModalLayer();

}

//find Accordion view
function findAccordionView(){
	
	if(window.DisputeItemsListPage)DisputeItemsListPage = null;
	if(window.EmptySCOAListPage)EmptySCOAListPage = null;
	if(window.ProposalViewListPage) ProposalViewListPage = null;
	var callback = {
		success:rendorAccordionView, 
		failure:showError
	};
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest("POST", actionUri.getAccordionViewUri+"?svo.invoiceId="+invoiceId, callback);
}

/**
 * 执行 ‘SCOA and Dispute’ 选项卡下的三个手风琴选项卡的渲染动作。
 * 1, Browse Invoice
 * 2, SCOA Coding
 * 3, Disputed Items
 * @param  {[type]} o [description]
 * @return {[type]}   [description]
 */
function rendorAccordionView(o){

	
	if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
		window.location.reload();
		return;
	}
	var invoiceRole = SANINCO.ifAnyGranted(Constants.FUNCTION.invoiceReviewAndPropsal);
	var invoiceActionRole = SANINCO.ifAllGranted(Constants.FUNCTION.invoiceReviewAndPropsalAction);
	if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
        window.location.reload();
        return;
    }
	var data = eval("("+o.responseText+")");
	var t = "";
	t += '<div id="accordion" >';
	
	//Browse Invoice Accordion
	t += buildAccordionTop("Browse Invoice","Browse Invoice","refreshList();","__browse_invoice");
	t += '<div id="tab_Browse" class="yui-navset"><ul class="yui-nav"></ul><div class="yui-content"><div id="__tab00">';
	t += buildAccordionLower(invoiceRole,invoiceActionRole,"__proposal_list_view","__proposalList",
						"__proposalList_page","__proposalList_page2","proposal","invoice","","Browse Invoice");
		
	//SCOA Coding Accordion
	t += buildAccordionTop("SCOA Coding","SCOA Coding","refreshEmptySCOAList();","__empty_SCOA");
	t += '<div id="tab_SCOA" class="yui-navset"><ul class="yui-nav"></ul><div class="yui-content"><div id="__tab00">';
	t += buildAccordionLower(invoiceRole,invoiceActionRole,"__empty_SCOA_view","__empty_SCOA_list",
							"__empty_SCOA_list_page","__empty_SCOA_list_page2","emptySCOA","SCOA","","SCOA Coding");
	
	//Disputed Items Accordion					
	t += buildAccordionTop("Disputed Items","Disputed Items","refreshDisputeItemsList();","__disputed_items");
	t += '<div id="tab_Disputed" class="yui-navset"><ul class="yui-nav"></ul><div class="yui-content"><div id="__tab00">';
	t += buildAccordionLower(invoiceRole,invoiceActionRole,"__disputed_items_view","__disputed_items_list",
				"__disputed_items_list_page","__disputed_items_list_page2","disputeItems","disputed","","Disputed Items");
						
	if(data.error){
		t += "</div>";
		document.getElementById('__accordion_proposal').innerHTML = t;
	}else{
		var rows = data.data;
		for(var i = 0; i < rows.length; i++){
			var row = rows[i];
			t += buildAccordionTop(row.labelName + " / count: " + row.count + " / Payment Amount: " + formatAmount(row.paymentSum,2) + " / Dispute Amount: " + formatAmount(row.disputeSum,2) + " / CreditAmount: " + formatAmount(row.creditSum,2),
					row.labelName,'getItemTypeOpenAcc(\''+row.invoiceItemId+'\',\''+space(row.labelName)+'\',\''+row.label+'\',true,\'box_'+i+2+'\');',
					'__'+space(row.labelName)+'_list_acc');
			t += '<div id="tab_ItemType_'+space(row.labelName)+'" class="yui-navset"><ul class="yui-nav"></ul><div class="yui-content"><div id="__tab00">';
			t += buildAccordionLower(invoiceRole,invoiceActionRole,'__'+space(row.labelName)+'_view',
					'__'+space(row.labelName)+'','__itemLabel_page'+space(row.labelName)+'','__itemLabel_page'+space(row.labelName)+'2',
					space(row.labelName + row.label),space(row.labelName + row.label),"'"+row.labelName+"','"+row.label+"'",space(row.labelName + row.label));
		 }

		t += "</div>";
		document.getElementById('__accordion_proposal').innerHTML = t;
		delete t;
		
		for(var i = 0; i < rows.length; i++){
			
			var row = rows[i];
			var instance = space(row.labelName + row.label);
			pageListName[""+i] = [instance];
			var pageInstance = window[instance] = new SANINCO.Page('__'+space(row.labelName),instance,{
			    'sortingField' : "p.item_name",
			    'sortingDirection' : "asc",
			    'vo' : "svo",
				'recordText' : '',
				'autoToTop' : true,
				'paginationDiv' : "__itemLabel_page"+space(row.labelName),
				'otherPaginationDivs' : ['__itemLabel_page'+space(row.labelName)+'2'],
			    'totalPageUri' : actionUri.getItemTypePageNoUri,
			    'dataUri' : actionUri.searchItemTypeListUri,
				'recPerArray' : [10,15,20,25,30,40,50,100],
	  			'cols' : [{ title : "<input type=\"checkbox\" class='noBorderRadioButton' name='box"+i+2+"' onclick=\"chooseAll('box"+i+2+"','box_"+i+2+"')\"/>",
							dataField: function(o,t){
								return (o.proposalFlag != 1 ? "" : str(''+o.id+'','box_'+t.i+2+'',''+o.amount+'',''+o.disputeAmount+'',''+o.itemTypeId+'',''+o.creditAmount+'',''+o.originatorId+'',''+o.SCOAId+'',''+o.disputeCategoryId+''));
							}
					    },{ className : "table_td_br",width:"260px", title : "Item Type",dataField: "itemTypeName",sort : "it.item_type_name"
					    },{ className : "table_td_br",width:"200px", title : "Item",dataField:"item",sort : "p.item_name", filtration : {name:"p.item_name",alias:"Item"}
					    },{ title : "Payment",dataField: "payment",sort : "p.payment_amount", filtration : {name:"p.payment_amount",alias:"Payment"}
					    },{ title : "Dispute",dataField:"dispute",sort : "p.dispute_amount"
						},{ title : "Credit",dataField:"credit",sort : "p.credit_amount"
						},{ title : "Circuit Number",dataField:"circuitNumber",sort : "p.circuit_number", filtration : {name:"p.circuit_number",alias:"Circuit Number"}
					    },{ title : "Billing Number",dataField:"billingNumber",sort : "p.billing_number", filtration : {name:"p.billing_number",alias:"Billing Number"}
					    },{ title : "SCOA",dataField:"SCOA",sort : "ac.account_code_name", filtration : {name:"ac.account_code_name",alias:"SCOA"}
						},{ title : "Dispute Category",dataField:"disputeCategory",sort : "dr.dispute_reason_name", filtration : {name:"dr.dispute_reason_name",alias:"Dispute Category"}
					    },{ className : "table_td_br",width:"260px", title : "Notes",dataField:"notes",sort : "p.notes", filtration : {name:"p.notes",alias:"Notes"}
					    },{ className : "table_td_br",width:"260px", title : "Description",dataField:"description",sort : "p.description", filtration : {name:"p.description",alias:"Description"}
					    },{ title : "Address",dataField:"address",sort : "p.address"
					    },{ title : "Service Type",dataField:"serviceType",sort : "p.service_type", filtration : {name:"p.service_type",alias:"Service Type"}
					    },{ title : "Charge Type",dataField:"chargeType",sort : "p.charge_type", filtration : {name:"p.charge_type",alias:"Charge Type"}
					    },{ title : "USOC",dataField:"usoc",sort : "p.usoc", filtration : {name:"p.usoc",alias:"USOC"}
					    },{ className : "table_td_br",width:"260px", title : "USOC Description",dataField:"usocDescription",sort : "p.usoc_description"
					    },{ title : "Quantity",dataField:"quantity",sort : "p.quantity"
						},{ title : "Rate",dataField:"rate",sort : "p.rate"
						},{ title : "Minutes",dataField:"minutes",sort : "p.minutes"
						},{ title : "Number of Calls",dataField:"numberCalls",sort : "p.number_of_calls"
						},{ title : "Cellular",dataField:"cellularIndicator",sort : "p.cellular_indicator"
					    },{ title : "Country",dataField:"country",sort : "p.country"
					    },{ title : "Mileage",dataField:"mileage",sort : "p.mileage"
						},{ title : "ASG",dataField:"asg",sort : "p.asg"
					    },{ title : "Jurisdiction",dataField:"jurisdiction",sort : "p.jurisdiction"
					    },{ title : "Direction",dataField:"direction",sort : "p.direction"
					    },{ title : "Download",dataField:function(o){
								return (o.attachmentPoint == "" ? "" : "<img src=\"include/images/download1.gif\" style=\"cursor: pointer;\" onclick=\"SANINCO.Download(" + o.attachmentPoint + ");\"/>");
							}
					    }]
			});
			
			pageInstance.row = row;
			pageInstance.i = i;
			
			pageInstance.addBeforeSearch(function(m,t){
				if(m == "start")recordProposalBoxId = {};
				delete m;
				delete t;
			});
			
			pageInstance.addCompleteEvent(function(o){
				echoProposalId(boxName);
				delete o;
			});
			
			pageInstance.addTotalSuccessEvent(function(m,t){
				var styleShow = "inline-block";
				if(m.totalResultNo == 0) styleShow = "none";
				document.getElementById('__'+t.gn+'_download_excel_button').style.display = styleShow;
				document.getElementById('__'+t.gn+'_group_dispute_button').style.display = styleShow;
				document.getElementById('__'+t.gn+'_group_dispute_button').style.display = styleShow;
				document.getElementById('__'+t.gn+'_SCOA_coding_button').style.display = styleShow;
				document.getElementById('__'+t.gn+'_SCOA_coding_button_single').style.display = styleShow;
				delete m;
				delete t;
			});
			
			filterAccordion = new SANINCO.Filter();
			    filterAccordion.addEditeEvent(function(){pageInstance.start();});
			    filterAccordion.add('p.item_name', 'String');
			    filterAccordion.add('p.payment_amount', 'String');
			    filterAccordion.add('ac.account_code_name', 'String');
				filterAccordion.add('dr.dispute_reason_name', 'String');
				filterAccordion.add('p.circuit_number', 'String');
				filterAccordion.add('p.service_type', 'String');
				filterAccordion.add('p.charge_type', 'String');
				filterAccordion.add('p.billing_number', 'String');
				filterAccordion.add('p.notes', 'String');
				filterAccordion.add('p.description', 'String');
				filterAccordion.add('p.usoc', 'String');
				
			pageInstance.setFilter(filterAccordion);
		
		}
	}
	delete rows;
	delete data;
	accordion();
	hideLoadingModalLayer();
	searchCountListBrowseInvoice();
	searchCountListSCOA();
	searchCountListDispute();
}

/**
 * 根据 Invoice 的明细，显示该 proposal 的validation result
 * 状态，通过不同的提示icon, 来凸显 validation result 状态.
 * 借助于 proposal id 来显示 validation result。
 * @author haichun.zhang.
 * @param  {[type]} o [description]
 * @return {Element}   [description]
 */
function validationStatus( o ) {

	// 需要返回的页面元素
	var returnedElement = "";

	if ( o.audit_status_id == 1 ) { // Passed.

		returnedElement = "<div class='validation-status' onclick=\"showValidationResult(" + o.id + ", null, null, this);\"  style=\"cursor: pointer;\"><span class='icon pass'></span> " + o.audit_status_name + "</div>";
	} else if (o.audit_status_id == 2) { // Failed.

		returnedElement = "<div class='validation-status' onclick=\"showValidationResult(" + o.id + ", null, null, this);\" style=\"cursor: pointer;\"><span class='icon failed'></span> " + o.audit_status_name + "</div>";
	} else if (o.audit_status_id == 3) { // Can not validate.

		returnedElement = "<div class='validation-status' onclick=\"showValidationResult(" + o.id + ", null, null, this);\" style=\"cursor: pointer;\"><span class='icon can-not-validate'></span> " + o.audit_status_name + "</div>";
	} else if (o.audit_status_id == 4){ // No reference

		returnedElement = "<div class='validation-status hint--bottom-right' data-hint='There is no reference to validate.' style=\"cursor: pointer;\"><span class='icon no-reference'></span> " + o.audit_status_name + "</div>";
	} else {}

	return returnedElement;
}

/**
 * 根据 Invoice 的明细，显示该 proposal 的validation result
 * 状态，通过不同的提示icon, 来凸显 validation result 状态.
 * 借助于 proposal id 来显示 validation result。
 * @author haichun.zhang.
 * @param  {[type]} o [description]
 * @return {Element}   [description]
 */
function validationExistsStatus( o ) {
	
	// 需要返回的页面元素
	var returnedElement = "";
	
	if ( o.audit_exists_status_id == 1 ) { // Passed.
		
		returnedElement = "<div class='validation-status' onclick=\"showValidationResult(" + o.id + ", null, 'Exists', this);\"  style=\"cursor: pointer;\"><span class='icon pass'></span> " + o.audit_exists_status_name + "</div>";
	} else if (o.audit_exists_status_id == 2) { // Failed.
		
		returnedElement = "<div class='validation-status' onclick=\"showValidationResult(" + o.id + ", null, 'Exists', this);\" style=\"cursor: pointer;\"><span class='icon failed'></span> " + o.audit_exists_status_name + "</div>";
	} else if (o.audit_exists_status_id == 3) { // Can not validate.
		
		returnedElement = "<div class='validation-status' onclick=\"showValidationResult(" + o.id + ", null, 'Exists', this);\" style=\"cursor: pointer;\"><span class='icon can-not-validate'></span> " + o.audit_exists_status_name + "</div>";
	} else if (o.audit_exists_status_id == 4){ // No reference
		
		returnedElement = "<div class='validation-status hint--bottom-right' data-hint='There is no reference to validate.' style=\"cursor: pointer;\"><span class='icon no-reference'></span> " + o.audit_exists_status_name + "</div>";
	} else {}
	
	return returnedElement;
}

/**
 * 对于invoice proposal list 列表中的 validation 字段的后面的
 * 过滤条件中的别名。

 * @return {[type]} [description]
 */
function filtrationAlias () {
	return " Rate Validation<br/><div style=color:gray><i>Please use the \\'=\\' in front of the letter filter<br/>1=Passed<br/>2=Failed<br/>3=Cannot Validate</i></div>";
}


/**
 * get Invoice Item Total
 * 'SCOA and Dispute' 主选项卡中 'Browser Invoice'
 * 选项卡中的数据列表的渲染。
 * @return {[type]} [description]
 */
function getProposalViewList(){
	ProposalViewListPage = new SANINCO.Page('__proposalList',"ProposalViewListPage",{
		sortingField : "ii.id",
	    sortingDirection : "asc",
	    vo : "svo",
		recordText : '',
		autoToTop : true,
	    totalPageUri : actionUri.getProposalViewListPageNoUri,
	    dataUri : actionUri.searchProposalListUri ,
		paginationDiv : "__proposalList_page",
		recPerArray : [10,20,30,50,100,500,1000],
	    cols : [{ title : "<input type=\"checkbox\" class='noBorderRadioButton' name=\"box1\" onclick=\"chooseAll('box1','box_1')\"/>",
					dataField: function(o){
						return (o.proposalFlag != 1 ? "" : str(''+o.id+'','box_1',''+o.amount+'',''+o.disputeAmount+'',''+o.itemTypeId+'',''+o.creditAmount+'',''+o.originatorId+'',''+o.SCOAId+'',''+o.disputeCategoryId+''));
					}
			    },
			     // Proposal Id is for download
			    { title : "Proposal Id",dataField: "Proposal Id",sort : "p.id",display:"none"},

			    // 显示 validation status.
				{ notDownload : 'Y',title : "Rate Validation",dataField:function(o){	return validationStatus(o); },sort :"p.audit_status_id" , filtration : {name:"p.audit_status_id",alias: filtrationAlias() }
			    },{ notDownload : 'Y',title : "Status Validation",dataField:function(o){ return validationExistsStatus(o); },sort :"p.audit_exists_status_id" , filtration : {name:"p.audit_exists_status_id",alias: filtrationAlias() }
			    },{ className : "table_td_br",width:"200px",title : "Item",dataField : "item",sort : "p.item_name", filtration : {name:"p.item_name",alias:"Item"}
			    },{ title : "Payment",dataField: "payment",sort : "p.payment_amount",filtration : {name:"p.payment_amount",alias:"Payment"}
			    },{ title : "Dispute",dataField:"dispute",sort : "p.dispute_amount"
				},{ title : "Credit",dataField:"credit",sort : "p.credit_amount"
			    },/*{ title : "PON",dataField:"pon",sort : "ii.purchase_order_number",filtration : {name:"ii.purchase_order_number",alias:"PON"}
				},*/{ title : "PON",dataField:"pon",sort : "ii.purchase_order_number"
//			    },{ title : "Province Source",dataField:"province_source",sort : "ps.province_source_name", filtration : {name:"ps.province_source_name",alias:"Province Source"}	
				},{ title : "Province",dataField:"province",sort : "pp.province_acronym", filtration : {name:"pp.province_acronym",alias:"Province"}	
				},{ title : "Circuit Number",dataField:function(o){
					return "<a href=\"javascript:showCircuitDetailForMasterInventory(" + "\'" + o.stripped_circuit_number + "\'" + ")\">"+o.circuitNumber+"</a>";
				},sort : "p.circuit_number", filtration : {name:"p.circuit_number",alias:"Circuit Number"}
				},{ title : "Stripped Circuit Number",dataField: "stripped_circuit_number",sort : "ii.stripped_circuit_number",filtration : {name:"ii.stripped_circuit_number",alias:"Stripped Circuit Number"}
				},{ title : "Product",dataField: "product_name",sort : "pt.product_name",filtration : {name:"pt.product_name",alias:"Product"}
			    },{ title : "Component",dataField: "component_name",sort : "pc.component_name",filtration : {name:"pc.component_name",alias:"Component"}
			    },{ title : "Billing Number",dataField:"billingNumber",sort : "p.billing_number", filtration : {name:"p.billing_number",alias:"Billing Number"}
			    },{ title : "SCOA",dataField:"SCOA",sort : "ac.account_code_name", filtration : {name:"ac.account_code_name",alias:"SCOA"}
				},{ title : "Dispute Category",dataField:"disputeCategory",sort : "dr.dispute_reason_name", filtration : {name:"dr.dispute_reason_name",alias:"Dispute Category"}
			    },{ className : "table_td_br",width:"260px", title : "Notes",dataField:"notes",sort : "p.notes", filtration : {name:"p.notes",alias:"Notes"}
			    },{ className : "table_td_br",width:"260px", title : "Description",dataField:"description",sort : "p.description", filtration : {name:"p.description",alias:"Description"}
			    },{ title : "Address",dataField:"address",sort : "p.address"
			    },{ title : "Service Type",dataField:"serviceType",sort : "p.service_type", filtration : {name:"p.service_type",alias:"Service Type"}
			    },{ title : "Charge Type",dataField:"chargeType",sort : "p.charge_type", filtration : {name:"p.charge_type",alias:"Charge Type"}
			    },{ title : "USOC",dataField:"usoc",sort : "p.usoc", filtration : {name:"p.usoc",alias:"USOC"}
			    },{ className : "table_td_br",width:"260px", title : "USOC Description",dataField:"usocDescription",sort : "p.usoc_description"
			    },{ title : "EO Direct Quantity",dataField:"driect_quantity",sort : "p.driect_quantity"
			    },{ title : "Quantity",dataField:"quantity",sort : "p.quantity"
				},{ title : "Rate",dataField:"rate",sort : "p.rate"
				},{ title : "Minutes",dataField:"minutes",sort : "p.minutes"
				},{ title : "Number of Calls",dataField:"numberCalls",sort : "p.number_of_calls"
				},{ title : "Cellular",dataField:"cellularIndicator",sort : "p.cellular_indicator"
			    },{ title : "Country",dataField:"country",sort : "p.country"
			    },{ title : "Mileage",dataField:"mileage",sort : "p.mileage"
				},{ title : "ASG",dataField:"asg",sort : "p.asg"
			    },{ title : "Jurisdiction",dataField:"jurisdiction",sort : "p.jurisdiction"
			    },{ title : "Direction",dataField:"direction",sort : "p.direction"
			    },{ title : "From Date",dataField:"fromDate",sort : "ii.start_date", filtration : {name:"ii.start_date",alias:"From Date"}
			    },{ title : "To Date",dataField:"toDate",sort : "ii.end_date", filtration : {name:"ii.end_date",alias:"To Date"}
			    },{ title : "Dispute File", notDownload: 'Y', dataField:function(o){
							return (o.attachmentPoint == "" ? "" : "<img src=\"include/images/download1.gif\" style=\"cursor: pointer;\" onclick=\"SANINCO.Download(" + o.attachmentPoint + ");\"/>");
						}
			    },{ title : "Dispute Number",dataField:function(o){
			              return "<a href=\"javascript:mark('viewDisputeDetails.action?disputeId="+o.disputeId+"','_blank');\">"+o.disputeNumber+"</a>";
	            },sort : "d.dispute_number", filtration : {name:"d.dispute_number",alias:"Dispute Number"}
			    },{ title : "Dispute Notes",dataField:"disputeNotes",sort : "d.notes", filtration : {name:"d.notes",alias:"Dispute Notes"}
			    },{ title : "Tariff&Contract", notDownload: 'Y', dataField:function(o){
							return (o.tariffCount == "0" ? "" : "<img src=\"include/images/download1.gif\" style=\"cursor: pointer;\" onclick=\"SANINCO.TariffDownload(" + o.circuitId + ");\"/>");
						}
			    }]
		});

	// Browse Invoice tab 下的多个tab的显示。(除了 all tab)
	// 不同的invoice item type 列表中显示不同的字段。
	// 这些字段的中间有一些数据是动态的从后台中加载过来的。
	// 渲染列表所需的属性来自 item_display 这个表。
	// 根据不同的invoice_structure_id 来查询。
	if(itemTypeIdTab != null){ 

		// MRC
		if(itemTypeIdTab == 13 || itemTypeIdTab.toString().indexOf('3') == 0){

			var pastArray = [{ title : "<input type=\"checkbox\" class='noBorderRadioButton' name=\"box1\" onclick=\"chooseAll('box1','box_1')\"/>",
				dataField: function(o){
					return (o.proposalFlag != 1 ? "" : str(''+o.id+'','box_1',''+o.amount+'',''+o.disputeAmount+'',''+o.itemTypeId+'',''+o.creditAmount+'',''+o.originatorId+'',''+o.SCOAId+'',''+o.disputeCategoryId+''));
				}
			},{ title : "Proposal Id",dataField: "Proposal Id",sort : "p.id",display:"none"

			// 显示 validation status.
		    },{ notDownload : 'Y',title : " Rate Validation",dataField:function(o){ return validationStatus(o); },sort :"p.audit_status_id" , filtration : {name:"p.audit_status_id",alias: filtrationAlias() }
			},{ notDownload : 'Y',title : "Status Validation",dataField:function(o){ return validationExistsStatus(o); },sort :"p.audit_exists_status_id" , filtration : {name:"p.audit_exists_status_id",alias: filtrationAlias() }
		    },{ className : "table_td_br",width:"180px",title : "Item",dataField:"itemName",sort : "ii.item_name", filtration : {name:"ii.item_name",alias:"Item"}
			},{ title : "Payment Amount",dataField: "payment",sort : "p.payment_amount", filtration : {name:"p.payment_amount",alias:"Payment"}
		    },{ title : "Dispute Amount",dataField:"dispute",sort : "p.dispute_amount"
			},{ title : "Expected MRC",dataField:"expectedMRC",sort : "ci.expected_total_mrc"
			},{ title : "Credit Amount",dataField:"credit",sort : "p.credit_amount"
			},{ title : "SCOA",dataField:"SCOA",sort : "ac.account_code_name" , filtration : {name:"ac.account_code_name",alias:"SCOA"}
			},{ title : "Service Type",dataField:"serviceType",sort : "p.service_type"
		    },{ title : "Service Id",dataField:"serviceId",sort : "p.service_id", filtration : {name:"p.service_id",alias:"Service Id"}
		    },{ title : "Charge Type",dataField:"chargeType",sort : "p.charge_type", filtration : {name:"p.charge_type",alias:"Charge Type"}
//		    },{ title : "Province Source",dataField:"province_source",sort : "ps.province_source_name", filtration : {name:"ps.province_source_name",alias:"Province Source"}	
			},{ title : "Province",dataField:"province",sort : "pp.province_acronym", filtration : {name:"pp.province_acronym",alias:"Province"}
		    },{ title : "Circuit Number",dataField:function(o){
		    	//return "<a href=\"javascript:showCircuitDetail(event,"+o.id+")\">"+o.circuitNumber+"</a>";
		    	return "<a href=\"javascript:showCircuitDetailForMasterInventory(" + "\'" + o.stripped_circuit_number + "\'" + ")\">"+o.circuitNumber+"</a>";
		    	},sort : "p.circuit_number", filtration : {name:"p.circuit_number",alias:"Circuit Number"}
		    },{ title : "Stripped Circuit Number",dataField: "stripped_circuit_number",sort : "ii.stripped_circuit_number",filtration : {name:"ii.stripped_circuit_number",alias:"Stripped Circuit Number"}
		    },{ title : "Product",dataField: "product_name",sort : "pt.product_name",filtration : {name:"pt.product_name",alias:"Product"}
			},{ title : "Component",dataField: "component_name",sort : "pc.component_name",filtration : {name:"pc.component_name",alias:"Component"}
			},{ title : "PON",dataField:"pon",sort : "ii.purchase_order_number",filtration : {name:"ii.purchase_order_number",alias:"PON"}
		    },{ title : "From Date",dataField:"fromDate",sort : "ii.start_date", filtration : {name:"ii.start_date",alias:"From Date"}
			},{ title : "To Date",dataField:"toDate",sort : "ii.end_date", filtration : {name:"ii.end_date",alias:"To Date"}
			}];
		
			ProposalViewListPage.sortingField =  "ii.id";
			ProposalViewListPage.totalPageUri = actionUri.getBrowseInvoiceListTabPageNoUri;
			ProposalViewListPage.dataUri = actionUri.searchBrowseInvoiceListTabUri;
			// #searchSCOACodingTabHtml 方法动态从后台获取一些字段添加到
			// 不同invoice item type tab的proposal 列表中。
			ProposalViewListPage.cols = traversalArray(pastArray,searchSCOACodingTabHtml(),"browseInvoice");
			
		}else if (itemTypeIdTab == 14 || itemTypeIdTab.toString().indexOf('4') == 0){
			// Usage.
			
			var pastArray = [{ title : "<input type=\"checkbox\" class='noBorderRadioButton' name=\"box1\" onclick=\"chooseAll('box1','box_1')\"/>",
				dataField: function(o){
					return (o.proposalFlag != 1 ? "" : str(''+o.id+'','box_1',''+o.amount+'',''+o.disputeAmount+'',''+o.itemTypeId+'',''+o.creditAmount+'',''+o.originatorId+'',''+o.SCOAId+'',''+o.disputeCategoryId+''));
				}
			},{ title : "Proposal Id",dataField: "Proposal Id",sort : "p.id",display:"none"
	
			// 显示 validation status.
		    },{ notDownload : 'Y',title : "Rate Validation",dataField:function(o){ return validationStatus(o); },sort :"p.audit_status_id" , filtration : {name:"p.audit_status_id",alias: filtrationAlias() }
			},{ notDownload : 'Y',title : "Status Validation",dataField:function(o){ return validationExistsStatus(o); },sort :"p.audit_exists_status_id" , filtration : {name:"p.audit_exists_status_id",alias: filtrationAlias() }
		    },{ className : "table_td_br",width:"180px",title : "Item",dataField:"itemName",sort : "ii.item_name" , filtration : {name:"ii.item_name",alias:"Item"}
			},{ title : "Payment Amount",dataField: "payment",sort : "p.payment_amount" ,filtration : {name:"p.payment_amount",alias:"Payment"}
		    },{ title : "Dispute Amount",dataField:"dispute",sort : "p.dispute_amount"
			},{ title : "Credit Amount",dataField:"credit",sort : "p.credit_amount"
			},{ title : "SCOA",dataField:"SCOA",sort : "ac.account_code_name", filtration : {name:"ac.account_code_name",alias:"SCOA"}
			},{ title : "Service Type",dataField:"serviceType",sort : "p.service_type"
		    },{ title : "Charge Type",dataField:"chargeType",sort : "p.charge_type", filtration : {name:"p.charge_type",alias:"Charge Type"}
//		    },{ title : "Province Source",dataField:"province_source",sort : "ps.province_source_name", filtration : {name:"ps.province_source_name",alias:"Province Source"}	
			},{ title : "Province",dataField:"province",sort : "pp.province_acronym", filtration : {name:"pp.province_acronym",alias:"Province"} 
			},{ title : "Terminating Province",dataField:"terminating_province",sort : "ii.terminating_province", filtration : {name:"ii.terminating_province",alias:"Terminating Province"} 
		    },{ title : "Circuit Number",dataField:function(o){
		    	//return "<a href=\"javascript:showCircuitDetail(event,"+o.id+")\">"+o.circuitNumber+"</a>";
		    	return "<a href=\"javascript:showCircuitDetailForMasterInventory(" + "\'" + o.stripped_circuit_number + "\'" + ")\">"+o.circuitNumber+"</a>";
		    	},sort : "p.circuit_number", filtration : {name:"p.circuit_number",alias:"Circuit Number"}
		    },{ title : "Stripped Circuit Number",dataField: "stripped_circuit_number",sort : "ii.stripped_circuit_number",filtration : {name:"ii.stripped_circuit_number",alias:"Stripped Circuit Number"}
		    },{ title : "Product",dataField: "product_name",sort : "pt.product_name",filtration : {name:"pt.product_name",alias:"Product"}
			},{ title : "Component",dataField: "component_name",sort : "pc.component_name",filtration : {name:"pc.component_name",alias:"Component"}
			},{ title : "PON",dataField:"pon",sort : "ii.purchase_order_number" ,filtration : {name:"ii.purchase_order_number",alias:"PON"}
		    },{ title : "From Date",dataField:"fromDate",sort : "ii.start_date", filtration : {name:"ii.start_date",alias:"From Date"}
			},{ title : "To Date",dataField:"toDate",sort : "ii.end_date", filtration : {name:"ii.end_date",alias:"To Date"}
			}];
			
			ProposalViewListPage.sortingField =  "ii.id";
			ProposalViewListPage.totalPageUri = actionUri.getBrowseInvoiceListTabPageNoUri;
			ProposalViewListPage.dataUri = actionUri.searchBrowseInvoiceListTabUri;
			ProposalViewListPage.cols = traversalArray(pastArray,searchSCOACodingTabHtml(),"browseInvoice");
			
			
		} else if (itemTypeIdTab == 18 || itemTypeIdTab.toString().indexOf('8') == 0) { // Tax

				var pastArray = [{ title : "<input type=\"checkbox\" class='noBorderRadioButton' name=\"box1\" onclick=\"chooseAll('box1','box_1')\"/>",
						dataField: function(o){
							return (o.proposalFlag != 1 ? "" : str(''+o.id+'','box_1',''+o.amount+'',''+o.disputeAmount+'',''+o.itemTypeId+'',''+o.creditAmount+'',''+o.originatorId+'',''+o.SCOAId+'',''+o.disputeCategoryId+''));
						}
				},{ title : "Proposal Id",dataField: "Proposal Id",sort : "p.id",display:"none"

				// 显示 validation status.
			    },{ notDownload : 'Y',title : "Rate Validation",dataField:function(o){ return validationStatus(o); },sort :"p.audit_status_id" , filtration : {name:"p.audit_status_id",alias: filtrationAlias() }
				},{ notDownload : 'Y',title : "Status Validation",dataField:function(o){ return validationExistsStatus(o); },sort :"p.audit_exists_status_id" , filtration : {name:"p.audit_exists_status_id",alias: filtrationAlias() }
			    },{ className : "table_td_br",width:"180px",title : "Item",dataField:"itemName",sort : "ii.item_name" , filtration : {name:"ii.item_name",alias:"Item"}
				},{ title : "Payment Amount",dataField: "payment",sort : "p.payment_amount" ,filtration : {name:"p.payment_amount",alias:"Payment"}
			    },{ title : "Dispute Amount",dataField:"dispute",sort : "p.dispute_amount"
				},{ title : "Credit Amount",dataField:"credit",sort : "p.credit_amount"
				},{ title : "SCOA",dataField:"SCOA",sort : "ac.account_code_name", filtration : {name:"ac.account_code_name",alias:"SCOA"}
				},{ title : "Service Type",dataField:"serviceType",sort : "p.service_type"
			    },{ title : "Charge Type",dataField:"chargeType",sort : "p.charge_type", filtration : {name:"p.charge_type",alias:"Charge Type"}
//			    },{ title : "Province Source",dataField:"province_source",sort : "ps.province_source_name", filtration : {name:"ps.province_source_name",alias:"Province Source"}	
				},{ title : "Province",dataField:"province",sort : "pp.province_acronym", filtration : {name:"pp.province_acronym",alias:"Province"} 
			    },{ title : "Circuit Number",dataField:function(o){
			    	//return "<a href=\"javascript:showCircuitDetail(event,"+o.id+")\">"+o.circuitNumber+"</a>";
			    	return "<a href=\"javascript:showCircuitDetailForMasterInventory(" + "\'" + o.stripped_circuit_number + "\'" + ")\">"+o.circuitNumber+"</a>";
			    	},sort : "p.circuit_number", filtration : {name:"p.circuit_number",alias:"Circuit Number"}
			    },{ title : "Stripped Circuit Number",dataField: "stripped_circuit_number",sort : "ii.stripped_circuit_number",filtration : {name:"ii.stripped_circuit_number",alias:"Stripped Circuit Number"}
			    },{ title : "Product",dataField: "product_name",sort : "pt.product_name",filtration : {name:"pt.product_name",alias:"Product"}
				},{ title : "Component",dataField: "component_name",sort : "pc.component_name",filtration : {name:"pc.component_name",alias:"Component"}
				},{ title : "PON",dataField:"pon",sort : "ii.purchase_order_number" ,filtration : {name:"ii.purchase_order_number",alias:"PON"}
			    }];
				
				ProposalViewListPage.sortingField =  "ii.id";
				ProposalViewListPage.totalPageUri = actionUri.getBrowseInvoiceListTabPageNoUri;
				ProposalViewListPage.dataUri = actionUri.searchBrowseInvoiceListTabUri;
				ProposalViewListPage.cols = traversalArray(pastArray,searchSCOACodingTabHtml(),"browseInvoice");
				
		}else{ //  Occ, Lpc 等等...
			
			var pastArray = [{ title : "<input type=\"checkbox\" class='noBorderRadioButton' name=\"box1\" onclick=\"chooseAll('box1','box_1')\"/>",
					dataField: function(o){
						return (o.proposalFlag != 1 ? "" : str(''+o.id+'','box_1',''+o.amount+'',''+o.disputeAmount+'',''+o.itemTypeId+'',''+o.creditAmount+'',''+o.originatorId+'',''+o.SCOAId+'',''+o.disputeCategoryId+''));
					}
			},{ title : "Proposal Id",dataField: "Proposal Id",sort : "p.id",display:"none"

			// 显示 validation status.
		    },{ notDownload : 'Y',title : "Rate Validation",dataField:function(o){ return validationStatus(o); },sort :"p.audit_status_id" , filtration : {name:"p.audit_status_id",alias: filtrationAlias() }
			},{ notDownload : 'Y',title : "Status Validation",dataField:function(o){ return validationExistsStatus(o); },sort :"p.audit_exists_status_id" , filtration : {name:"p.audit_exists_status_id",alias: filtrationAlias() }
		    },{ className : "table_td_br",width:"180px",title : "Item",dataField:"itemName",sort : "ii.item_name" , filtration : {name:"ii.item_name",alias:"Item"}
			},{ title : "Payment Amount",dataField: "payment",sort : "p.payment_amount" ,filtration : {name:"p.payment_amount",alias:"Payment"}
		    },{ title : "Dispute Amount",dataField:"dispute",sort : "p.dispute_amount"
			},{ title : "Credit Amount",dataField:"credit",sort : "p.credit_amount"
			},{ title : "SCOA",dataField:"SCOA",sort : "ac.account_code_name", filtration : {name:"ac.account_code_name",alias:"SCOA"}
			},{ title : "Service Type",dataField:"serviceType",sort : "p.service_type"
		    },{ title : "Service Id",dataField:"serviceId",sort : "p.service_id", filtration : {name:"p.service_id",alias:"Service Id"}
		    },{ title : "Charge Type",dataField:"chargeType",sort : "p.charge_type", filtration : {name:"p.charge_type",alias:"Charge Type"}
//		    },{ title : "Province Source",dataField:"province_source",sort : "ps.province_source_name", filtration : {name:"ps.province_source_name",alias:"Province Source"}	
			},{ title : "Province",dataField:"province",sort : "pp.province_acronym", filtration : {name:"pp.province_acronym",alias:"Province"} 
		    },{ title : "Circuit Number",dataField:function(o){
		    	//return "<a href=\"javascript:showCircuitDetail(event,"+o.id+")\">"+o.circuitNumber+"</a>";
		    	return "<a href=\"javascript:showCircuitDetailForMasterInventory(" + "\'" + o.stripped_circuit_number + "\'" + ")\">"+o.circuitNumber+"</a>";
		    	},sort : "p.circuit_number", filtration : {name:"p.circuit_number",alias:"Circuit Number"
		    }
		    },{ title : "Stripped Circuit Number",dataField: "stripped_circuit_number",sort : "ii.stripped_circuit_number",filtration : {name:"ii.stripped_circuit_number",alias:"Stripped Circuit Number"}
		    },{ title : "Product",dataField: "product_name",sort : "pt.product_name",filtration : {name:"pt.product_name",alias:"Product"}
			},{ title : "Component",dataField: "component_name",sort : "pc.component_name",filtration : {name:"pc.component_name",alias:"Component"}
			},{ title : "PON",dataField:"pon",sort : "ii.purchase_order_number" ,filtration : {name:"ii.purchase_order_number",alias:"PON"}
		    },{ title : "From Date",dataField:"fromDate",sort : "ii.start_date", filtration : {name:"ii.start_date",alias:"From Date"}
			},{ title : "To Date",dataField:"toDate",sort : "ii.end_date", filtration : {name:"ii.end_date",alias:"To Date"}
			}];
			
			ProposalViewListPage.sortingField =  "ii.id";
			ProposalViewListPage.totalPageUri = actionUri.getBrowseInvoiceListTabPageNoUri;
			ProposalViewListPage.dataUri = actionUri.searchBrowseInvoiceListTabUri;
			ProposalViewListPage.cols = traversalArray(pastArray,searchSCOACodingTabHtml(),"browseInvoice");
		}
	}
	
	ProposalViewListPage.addBeforeSearch(function(m,t){
		if(m == "start")recordProposalBoxId = {};
		delete m;
		delete t;
	});
	
	ProposalViewListPage.addCompleteEvent(function(o){
		echoProposalId('box_1');
		delete o;
	});
	
	ProposalViewListPage.addTotalSuccessEvent(function(data){
		var styleShow = "inline-block";
		if(data.totalResultNo == 0) styleShow = "none";
		document.getElementById('__proposal_download_excel_button').style.display = styleShow;
		document.getElementById('__proposal_group_dispute_button').style.display = styleShow;
		document.getElementById('__proposal_move_credit_button').style.display = styleShow;
		document.getElementById('__proposal_SCOA_coding_button').style.display = styleShow;
		document.getElementById('__proposal_SCOA_coding_button_single').style.display = styleShow;
		delete data;
	});
	
	ProposalViewListPage.voParam = {
		invoiceId : invoiceId,
		itemTypeId : itemTypeIdTab,
		parentProposalId : parentProposalId
	};
	ProposalViewListFilter = new SANINCO.Filter();
	    ProposalViewListFilter.addEditeEvent(function(){ProposalViewListPage.start();});
	    ProposalViewListFilter.add('p.audit_status_id', 'Number');
	    ProposalViewListFilter.add('p.audit_exists_status_id', 'Number');
	    ProposalViewListFilter.add('p.item_name', 'String');
	    ProposalViewListFilter.add('ii.item_name', 'String');
	    ProposalViewListFilter.add('pt.product_name', 'String'); // Product.
	    ProposalViewListFilter.add('pc.component_name', 'String'); // Component.
	    ProposalViewListFilter.add('p.payment_amount', 'String');
	    ProposalViewListFilter.add('ii.stripped_circuit_number', 'String');
	    ProposalViewListFilter.add('ac.account_code_name', 'String');
		ProposalViewListFilter.add('dr.dispute_reason_name', 'String');
		ProposalViewListFilter.add('p.circuit_number', 'String');
		ProposalViewListFilter.add('pp.province_acronym', 'String');	
		ProposalViewListFilter.add('ps.province_source_name', 'String'); // Province source	
		ProposalViewListFilter.add('ii.terminating_province', 'String'); 
		ProposalViewListFilter.add('p.billing_number', 'String');
		ProposalViewListFilter.add('p.service_type', 'String');
		ProposalViewListFilter.add('p.service_id', 'Number');
		ProposalViewListFilter.add('p.charge_type', 'String');
		ProposalViewListFilter.add('p.notes', 'String');
		ProposalViewListFilter.add('p.description', 'String');
		ProposalViewListFilter.add('p.usoc', 'String');
		ProposalViewListFilter.add('ii.purchase_order_number', 'String');  // fix bug by xin.huang on 2014-10-03
		ProposalViewListFilter.add('ii.start_date', 'String'); // From Date
		ProposalViewListFilter.add('ii.end_date', 'String'); // To Date
		
	ProposalViewListPage.setFilter(ProposalViewListFilter);
	
	if (!SANINCO.ifAllGranted(Constants.FUNCTION.invoiceReviewAndPropsal, Constants.FUNCTION.invoiceReviewAndPropsalAction)) {
		 ProposalViewListPage.cols[0].display = "none";
	}
	
	ProposalViewListPage.start();
}

//Tab to change the style
function tabStyle(th){
	var ul = th.parentNode.childNodes;
	for(var i = 0; i < ul.length; i++){
		ul[i].className = "";
		ul[i].title = "";
	}
	th.className = "selected";
	th.title = "active";
}

function refreshListTab(accordionName,itemTypeId,invoiceItemId,labelName,label){

	clearTips();

	window.mrcVariance.actionsForTabButtonEventExceptVariance(accordionName);

	itemTypeIdTab = itemTypeId;
	if(accordionName == "SCOA") getEmptySCOAList();
	if(accordionName == "Disputed") getDisputeItemsList();
	if(accordionName == "Browse") getProposalViewList();
	if(accordionName == "ItemType") {
		var instance = space(labelName + label);
		if(invoiceItemId != ""){
			window[instance].voParam = {
				invoiceId:invoiceId,
				itemTypeId:itemTypeIdTab,
				invoiceItemId:invoiceItemId,
				labelName:labelName
			};
		}
		
		if(itemTypeIdTab != null){ // yinghe.fu
				var pastArray = [{ title : "<input type=\"checkbox\" class='noBorderRadioButton' name='box"+i+2+"' onclick=\"chooseAll('box"+i+2+"','box_"+i+2+"')\"/>",
							dataField: function(o,t){
								return (o.proposalFlag != 1 ? "" : str(''+o.id+'','box_'+t.i+2+'',''+o.amount+'',''+o.disputeAmount+'',''+o.itemTypeId+'',''+o.creditAmount+'',''+o.originatorId+'',''+o.SCOAId+'',''+o.disputeCategoryId+''));
							}
					    },{ title : "Item",dataField:"itemName",sort : "ii.item_name"
				},{ title : "Payment Amount",dataField: "payment",sort : "p.payment_amount"
			    },{ title : "Dispute Amount",dataField:"dispute",sort : "p.dispute_amount"
				},{ title : "Credit Amount",dataField:"credit",sort : "p.credit_amount"
				},{ title : "SCOA",dataField:"SCOA",sort : "ac.account_code_name"
				},{ title : "Service Type",dataField:"serviceType",sort : "p.service_type"
			    },{ title : "Charge Type",dataField:"chargeType",sort : "p.charge_type"
			    },{ title : "Circuit Number",dataField:"circuitNumber",sort : "p.circuit_number"
			    },{ title : "PON",dataField:"pon",sort : "ii.purchase_order_number"	
			    }];
				
				window[instance].sortingField =  "ii.id";
				window[instance].totalPageUri = actionUri.getItemTypeListTabPageNoUri;
				window[instance].dataUri = actionUri.searchItemTypeListTabUri;
				window[instance].cols = traversalArray(pastArray,searchSCOACodingTabHtml());
		}else{
			window[instance].sortingField =  "p.item_name";
			window[instance].totalPageUri = actionUri.getItemTypePageNoUri;
			window[instance].dataUri = actionUri.searchItemTypeListUri;
			window[instance].cols = [{ title : "<input type=\"checkbox\" class='noBorderRadioButton' name='box"+i+2+"' onclick=\"chooseAll('box"+i+2+"','box_"+i+2+"')\"/>",
							dataField: function(o,t){
								return (o.proposalFlag != 1 ? "" : str(''+o.id+'','box_'+t.i+2+'',''+o.amount+'',''+o.disputeAmount+'',''+o.itemTypeId+'',''+o.creditAmount+'',''+o.originatorId+'',''+o.SCOAId+'',''+o.disputeCategoryId+''));
							}
					    },{ title : "Item Type",dataField: "itemTypeName",sort : "it.item_type_name"
					    },{ className : "table_td_br",width:"200px",title : "Item",dataField:"item",sort : "p.item_name", filtration : {name:"p.item_name",alias:"Item"}
					    },{ title : "Payment",dataField: "payment",sort : "p.payment_amount",filtration : {name:"p.payment_amount",alias:"Payment"}
					    },{ title : "Dispute",dataField:"dispute",sort : "p.dispute_amount"
						},{ title : "Credit",dataField:"credit",sort : "p.credit_amount"
						},{ title : "PON",dataField:"pon",sort : "ii.purchase_order_number"
						},{ title : "Circuit Number",dataField:"circuitNumber",sort : "p.circuit_number", filtration : {name:"p.circuit_number",alias:"Circuit Number"}
					    },{ title : "Billing Number",dataField:"billingNumber",sort : "p.billing_number", filtration : {name:"p.billing_number",alias:"Billing Number"}
					    },{ title : "SCOA",dataField:"SCOA",sort : "ac.account_code_name", filtration : {name:"ac.account_code_name",alias:"SCOA"}
						},{ title : "Dispute Category",dataField:"disputeCategory",sort : "dr.dispute_reason_name", filtration : {name:"dr.dispute_reason_name",alias:"Dispute Category"}
					    },{ title : "Notes",dataField:"notes",sort : "p.notes", filtration : {name:"p.notes",alias:"Notes"}
					    },{ title : "Description",dataField:"description",sort : "p.description", filtration : {name:"p.description",alias:"Description"}
					    },{ title : "Address",dataField:"address",sort : "p.address"
					    },{ title : "Service Type",dataField:"serviceType",sort : "p.service_type", filtration : {name:"p.service_type",alias:"Service Type"}
					    },{ title : "Charge Type",dataField:"chargeType",sort : "p.charge_type", filtration : {name:"p.charge_type",alias:"Charge Type"}
					    },{ title : "USOC",dataField:"usoc",sort : "p.usoc", filtration : {name:"p.usoc",alias:"USOC"}
					    },{ title : "USOC Description",dataField:"usocDescription",sort : "p.usoc_description"
					    },{ title : "Quantity",dataField:"quantity",sort : "p.quantity"
						},{ title : "Rate",dataField:"rate",sort : "p.rate"
						},{ title : "Minutes",dataField:"minutes",sort : "p.minutes"
						},{ title : "Number of Calls",dataField:"numberCalls",sort : "p.number_of_calls"
						},{ title : "Cellular",dataField:"cellularIndicator",sort : "p.cellular_indicator"
					    },{ title : "Country",dataField:"country",sort : "p.country"
					    },{ title : "Mileage",dataField:"mileage",sort : "p.mileage"
						},{ title : "ASG",dataField:"asg",sort : "p.asg"
					    },{ title : "Jurisdiction",dataField:"jurisdiction",sort : "p.jurisdiction"
					    },{ title : "Direction",dataField:"direction",sort : "p.direction"
					    },{ title : "Download",dataField:function(o){
									return (o.attachmentPoint == "" ? "" : "<img src=\"include/images/download1.gif\" style=\"cursor: pointer;\" onclick=\"SANINCO.Download(" + o.attachmentPoint + ");\"/>");
								}
					    }]
		}
		
		window[instance].start();
	}
}

/**
 * SCOA and Dispute tab 下动态从后台中加载出来的列
 * 数据来自 item_display 表。
 * @return {[type]} [description]
 */
searchSCOACodingTabHtml = function(){
	var str = '';
	$.ajax({
		url: actionUri.searchSCOACodingTabViewStrUri,
        type: "POST",
        dataType: "text",
        async: false,
        cache: false,
        data: ("svo.invoiceId=" + invoiceId + "&svo.itemTypeId=" + itemTypeIdTab),
        success: function(o){
            if (o.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                window.location.reload();
                return;
            }
			var data = eval("(" + o + ")");
			if(data.error){
				alert("Error: " + data.error);
				return;
			}
			// 渲染列
			str = getInstCols(data.cols);
        },
		error : showError
     });
	 return str;
};

/**
 * 根据从后台中获取到的数据动态的
 * 渲染表格中的列和数据。
 * @param  {[type]} cols [description]
 * @return {[type]}      [description]
 */
function getInstCols(cols){
	var colsArray = [];
	for (var i = 0; i < cols.length; i++) {
		var row = cols[i];
		var item = {
			"title" : row.title,
			"dataField" : row.filed,
			"sort" : row.filed,
			"width" : row.width
			//"filtration" : {name:row.filed,alias:row.format}
		};
		colsArray.push(item);
	}
	return colsArray;
}

/**
 * Traversal Array
 * 将arrayTwo中的数据渲染为列表的形式，
 * 并添加到arrayOne中。
 * @param  {ArrayObject} arrayOne 原始的列表中需要显示的列。
 * @param  {ArrayObject} arrayTwo 需要添加的列表中的列。
 * @return {ArrayObject}          在这个列表中需要渲染的列和列值。
 */
function traversalArray(arrayOne,arrayTwo,arrayThree){
	for(var c = 0; c < arrayTwo.length; c++){
		var row = arrayTwo[c];
		
		if(row.title == "Item" ||row.title =="Information" ||row.title =="USOC Long Description" ||row.title =="Item Name"){
			var item = {
				"title" : row.title,
				"dataField" : row.dataField,
				"sort" : row.sort,
				"width" : row.width,
				"className" : "table_td_br",
				"width" : "280px"
				//"filtration" : {name:row.filed,alias:row.format}
			};
		}else{
			var item = {
				"title" : row.title,
				"dataField" : row.dataField,
				"sort" : row.sort,
				"width" : row.width
				//"filtration" : {name:row.filed,alias:row.format}
			};
		}
		arrayOne.push(item);
	}
	// modify by xin.huang on 2012-9-27 start
	var disputeDownload = {
		"title" : "Dispute File",
		notDownload: 'Y',
		"dataField" : function(o){
			return (o.attachmentPoint == "" ? "" : "<img src=\"include/images/download1.gif\" style=\"cursor: pointer;\" onclick=\"SANINCO.Download(" + o.attachmentPoint + ");\"/>");
		}
	};
	arrayOne.push(disputeDownload);
	
	if (arrayThree == "browseInvoice") {
		var disputeNumber = { 
				"title" : "Dispute Number",
				"dataField":function(o){
                 return "<a href=\"javascript:mark('viewDisputeDetails.action?disputeId="+o.disputeId+"','_blank');\">"+o.disputeNumber+"</a>";
                 },
				"sort" : "d.dispute_number", 
				"filtration" : {name:"d.dispute_number",alias:"Dispute Number"}
		};
		var disputeNotes ={ 
				"title" : "Dispute Notes",
				"dataField":"disputeNotes",
				"sort" : "d.notes", 
				"filtration" : {name:"d.notes",alias:"Dispute Notes"}
		};
		arrayOne.push(disputeNumber);
		arrayOne.push(disputeNotes);
	}
	var traiffDownload = {
		"title" : "Tariff&Contract",
		notDownload: 'Y',
		"dataField" : function(o){
			return (o.tariffCount == "0" ? "" : "<img src=\"include/images/download1.gif\" style=\"cursor: pointer;\" onclick=\"SANINCO.TariffDownload(" + o.circuitId + ");\"/>");
		}
	};
	
	arrayOne.push(traiffDownload);
	// modify by xin.huang on 2012-9-27 end
	return arrayOne;
}

//get Empty SCOA List
function getEmptySCOAList(){
	EmptySCOAListPage = new SANINCO.Page('__empty_SCOA_list',"EmptySCOAListPage",{
		sortingField : "p.item_name",
	    sortingDirection : "asc",
	    vo : "svo",
		recordText : '',
		autoToTop : true,
	    totalPageUri : actionUri.getEmptySCOAListPageNoUri,
	    dataUri : actionUri.searchEmptySCOAListUri ,
		paginationDiv : "__empty_SCOA_list_page",
		//otherPaginationDivs : ['__empty_SCOA_list_page2'],
		recPerArray : [10,20,30,50,100,500,1000],
		    cols : [{ title : "<input type=\"checkbox\" class='noBorderRadioButton' name=\"box2\" onclick=\"chooseAll('box2','box_2')\"/>",
					dataField: function(o){
						return (o.proposalFlag != 1 ? "" : str(''+o.id+'','box_2',''+o.amount+'',''+o.disputeAmount+'',''+o.itemTypeId+'',''+o.creditAmount+'',''+o.originatorId+'',''+o.SCOAId+'',''+o.disputeCategoryId+''));
					}
			    },
			    { title : "Proposal Id",dataField: "Proposal Id",sort : "p.id",display:"none"
		},{ title : "Item Type",dataField: "itemTypeName",sort : "it.item_type_name"
			    },{className : "table_td_br",width:"200px",  title : "Item",dataField:"item",sort : "p.item_name", filtration : {name:"p.item_name",alias:"Item"}
			    },{ title : "Payment",dataField: "payment",sort : "p.payment_amount", filtration : {name:"p.payment_amount",alias:"Payment"}
			    },{ title : "Dispute",dataField:"dispute",sort : "p.dispute_amount"
				},{ title : "Credit",dataField:"credit",sort : "p.credit_amount"
				},{ title : "PON",dataField:"pon",sort : "ii.purchase_order_number"	
				},{ title : "Circuit Number",dataField:"circuitNumber",sort : "p.circuit_number", filtration : {name:"p.circuit_number",alias:"Circuit Number"}
			    },{ title : "Billing Number",dataField:"billingNumber",sort : "p.billing_number", filtration : {name:"p.billing_number",alias:"Billing Number"}
			    },{ title : "SCOA",dataField:"SCOA",sort : "ac.account_code_name", filtration : {name:"ac.account_code_name",alias:"SCOA"}
				},{ title : "Dispute Category",dataField:"disputeCategory",sort : "dr.dispute_reason_name", filtration : {name:"dr.dispute_reason_name",alias:"Dispute Category"}
			    },{ className : "table_td_br",width:"260px", title : "Notes",dataField:"notes",sort : "p.notes", filtration : {name:"p.notes",alias:"Notes"}
			    },{ className : "table_td_br",width:"260px", title : "Description",dataField:"description",sort : "p.description", filtration : {name:"p.description",alias:"Description"}
			    },{ title : "Address",dataField:"address",sort : "p.address"
			    },{ title : "Service Type",dataField:"serviceType",sort : "p.service_type", filtration : {name:"p.service_type",alias:"Service Type"}
			    },{ title : "Charge Type",dataField:"chargeType",sort : "p.charge_type", filtration : {name:"p.charge_type",alias:"Charge Type"}
			    },{ title : "USOC",dataField:"usoc",sort : "p.usoc", filtration : {name:"p.usoc",alias:"USOC"}
			    },{ className : "table_td_br",width:"260px", title : "USOC Description",dataField:"usocDescription",sort : "p.usoc_description"
			    },{ title : "Quantity",dataField:"quantity",sort : "p.quantity"
				},{ title : "Rate",dataField:"rate",sort : "p.rate"
				},{ title : "Minutes",dataField:"minutes",sort : "p.minutes"
				},{ title : "Number of Calls",dataField:"numberCalls",sort : "p.number_of_calls"
				},{ title : "Cellular",dataField:"cellularIndicator",sort : "p.cellular_indicator"
			    },{ title : "Country",dataField:"country",sort : "p.country"
			    },{ title : "Mileage",dataField:"mileage",sort : "p.mileage"
				},{ title : "ASG",dataField:"asg",sort : "p.asg"
			    },{ title : "Jurisdiction",dataField:"jurisdiction",sort : "p.jurisdiction"
			    },{ title : "Direction",dataField:"direction",sort : "p.direction"
			    },{ title : "Download",dataField:function(o){
						return (o.attachmentPoint == "" ? "" : "<img src=\"include/images/download1.gif\" style=\"cursor: pointer;\" onclick=\"SANINCO.Download(" + o.attachmentPoint + ");\"/>");
					}
			    }]
		});
	if(itemTypeIdTab != null){
		var pastArray = [{ title : "<input type=\"checkbox\" class='noBorderRadioButton' name=\"box2\" onclick=\"chooseAll('box2','box_2')\"/>",
				dataField: function(o){
					return (o.proposalFlag != 1 ? "" : str(''+o.id+'','box_2',''+o.amount+'',''+o.disputeAmount+'',''+o.itemTypeId+'',''+o.creditAmount+'',''+o.originatorId+'',''+o.SCOAId+'',''+o.disputeCategoryId+''));
				}
		},
		{ title : "Proposal Id",dataField: "Proposal Id",sort : "p.id",display:"none"
		},{ className : "table_td_br",width:"180px",title : "Item",dataField: "itemName",sort : "ii.item_name" , filtration : {name:"ii.item_name",alias:"Item"}
		},{ title : "Payment Amount",dataField: "payment",sort : "p.payment_amount",filtration : {name:"p.payment_amount",alias:"Payment"}
	    },{ title : "Dispute Amount",dataField:"dispute",sort : "p.dispute_amount"
		},{ title : "Credit Amount",dataField:"credit",sort : "p.credit_amount"
		},{ title : "SCOA",dataField:"SCOA",sort : "ac.account_code_name" , filtration : {name:"ac.account_code_name",alias:"SCOA"}
		},{ title : "Service Type",dataField:"serviceType",sort : "p.service_type"
	    },{ title : "Charge Type",dataField:"chargeType",sort : "p.charge_type" , filtration : {name:"p.charge_type",alias:"Charge Type"}
	    },{ title : "Circuit Number",dataField:"circuitNumber",sort : "p.circuit_number" , filtration : {name:"p.circuit_number",alias:"Circuit Number"}
	    },{ title : "PON",dataField:"pon",sort : "ii.purchase_order_number"	, filtration : {name:"ii.purchase_order_number",alias:"PON"}
	    }];
		
		EmptySCOAListPage.sortingField =  "ii.id";
		EmptySCOAListPage.totalPageUri = actionUri.getEmptySCOAListTabPageNoUri;
		EmptySCOAListPage.dataUri = actionUri.searchEmptySCOAListTabUri;
		EmptySCOAListPage.cols = traversalArray(pastArray,searchSCOACodingTabHtml());
	}
	
	EmptySCOAListPage.addBeforeSearch(function(m,t){
		if(m == "start")recordProposalBoxId = {};
		delete m;
		delete t;
	});
	
	EmptySCOAListPage.addCompleteEvent(function(m,t){
		echoProposalId('box_2');
		delete m;
		delete t;
	});
	
	EmptySCOAListPage.addTotalSuccessEvent(function(data){
		var styleShow = "inline-block";
		if(data.totalResultNo == 0) styleShow = "none";
		document.getElementById('__emptySCOA_download_excel_button').style.display = styleShow;
		document.getElementById('__emptySCOA_group_dispute_button').style.display = styleShow;
		document.getElementById('__emptySCOA_move_credit_button').style.display = styleShow;
		document.getElementById('__emptySCOA_SCOA_coding_button').style.display = styleShow;
		document.getElementById('__emptySCOA_SCOA_coding_button_single').style.display = styleShow;
		delete data;
	});
		
	EmptySCOAListPage.voParam = {
		invoiceId : invoiceId,
		itemTypeId : itemTypeIdTab,
		parentProposalId : parentProposalId
	};

	EmptySCOAListFilter = new SANINCO.Filter();
	    EmptySCOAListFilter.addEditeEvent(function(){EmptySCOAListPage.start();});
	    EmptySCOAListFilter.add('p.item_name', 'String');
	    EmptySCOAListFilter.add('p.payment_amount', 'String');
	    EmptySCOAListFilter.add('ac.account_code_name', 'String');
		EmptySCOAListFilter.add('dr.dispute_reason_name', 'String');
		EmptySCOAListFilter.add('p.circuit_number', 'String');
		EmptySCOAListFilter.add('p.service_type', 'String');
		EmptySCOAListFilter.add('p.charge_type', 'String');
		EmptySCOAListFilter.add('p.billing_number', 'String');
		EmptySCOAListFilter.add('p.notes', 'String');
		EmptySCOAListFilter.add('p.description', 'String');
		EmptySCOAListFilter.add('p.usoc', 'String');
		EmptySCOAListFilter.add('ii.purchase_order_number', 'String');
		EmptySCOAListFilter.add('ii.item_name', 'String');
	EmptySCOAListPage.setFilter(EmptySCOAListFilter);

	if (!SANINCO.ifAllGranted(Constants.FUNCTION.invoiceReviewAndPropsal, Constants.FUNCTION.invoiceReviewAndPropsalAction)) {
		 EmptySCOAListPage.cols[0].display = "none";
	}
	
	EmptySCOAListPage.start();
	
}

//get Dispute Items List
function getDisputeItemsList(bo){
	DisputeItemsListPage = new SANINCO.Page('__disputed_items_list',"DisputeItemsListPage",{
		sortingField : "p.item_name",
	    sortingDirection : "asc",
	    vo : "svo",
		recordText : '',
		autoToTop : true,
	    totalPageUri : actionUri.getDisputeItemsPageNoUri,
	    dataUri : actionUri.searchDisputeItemsListUri ,
		paginationDiv : "__disputed_items_list_page",
		//otherPaginationDivs : ['__disputed_items_list_page2'],
		recPerArray : [10,20,30,50,100,500,1000],
		    cols : [{ title : "<input type=\"checkbox\" class='noBorderRadioButton' name=\"box3\" onclick=\"chooseAll('box3','box_3')\"/>",
					dataField: function(o){
						return (o.proposalFlag != 1 ? "" : str(''+o.id+'','box_3',''+o.amount+'',''+o.disputeAmount+'',''+o.itemTypeId+'',''+o.creditAmount+'',''+o.originatorId+'',''+o.SCOAId+'',''+o.disputeCategoryId+''));
					}
			    }
			    ,{ title : "Proposal Id",dataField: "Proposal Id",sort : "p.id",display:"none"
				},{ title : "Item Type",dataField: "itemTypeName",sort : "it.item_type_name"
			    },{ className : "table_td_br",width:"200px", title : "Item",dataField:"item",sort : "p.item_name", filtration : {name:"p.item_name",alias:"Item"}
			    },{ title : "Payment",dataField: "payment",sort : "p.payment_amount", filtration : {name:"p.payment_amount",alias:"Payment"}
			    },{ title : "Dispute",dataField:"dispute",sort : "p.dispute_amount"
				},{ title : "Credit",dataField:"credit",sort : "p.credit_amount"
				},{ title : "PON",dataField:"pon",sort : "ii.purchase_order_number"	
				},{ title : "Circuit Number",dataField:"circuitNumber",sort : "p.circuit_number", filtration : {name:"p.circuit_number",alias:"Circuit Number"}
			    },{ title : "Billing Number",dataField:"billingNumber",sort : "p.billing_number", filtration : {name:"p.billing_number",alias:"Billing Number"}
			    },{ title : "SCOA",dataField:"SCOA",sort : "ac.account_code_name", filtration : {name:"ac.account_code_name",alias:"SCOA"}
				},{ title : "Dispute Category",dataField:"disputeCategory",sort : "dr.dispute_reason_name", filtration : {name:"dr.dispute_reason_name",alias:"Dispute Category"}
			    },{ className : "table_td_br",width:"260px",  title : "Notes",dataField:"notes",sort : "p.notes", filtration : {name:"p.notes",alias:"Notes"}
			    },{ className : "table_td_br",width:"260px", title : "Description",dataField:"description",sort : "p.description", filtration : {name:"p.description",alias:"Description"}
			    },{ title : "Address",dataField:"address",sort : "p.address"
			    },{ title : "Service Type",dataField:"serviceType",sort : "p.service_type", filtration : {name:"p.service_type",alias:"Service Type"}
			    },{ title : "Charge Type",dataField:"chargeType",sort : "p.charge_type", filtration : {name:"p.charge_type",alias:"Charge Type"}
			    },{ title : "USOC",dataField:"usoc",sort : "p.usoc", filtration : {name:"p.usoc",alias:"USOC"}
			    },{ className : "table_td_br",width:"260px",  title : "USOC Description",dataField:"usocDescription",sort : "p.usoc_description"
			    },{ title : "Quantity",dataField:"quantity",sort : "p.quantity"
				},{ title : "Rate",dataField:"rate",sort : "p.rate"
				},{ title : "Minutes",dataField:"minutes",sort : "p.minutes"
				},{ title : "Number of Calls",dataField:"numberCalls",sort : "p.number_of_calls"
				},{ title : "Cellular",dataField:"cellularIndicator",sort : "p.cellular_indicator"
			    },{ title : "Country",dataField:"country",sort : "p.country"
			    },{ title : "Mileage",dataField:"mileage",sort : "p.mileage"
				},{ title : "ASG",dataField:"asg",sort : "p.asg"
			    },{ title : "Jurisdiction",dataField:"jurisdiction",sort : "p.jurisdiction"
			    },{ title : "Direction",dataField:"direction",sort : "p.direction"
			    },{ title : "Download",dataField:function(o){
						return (o.attachmentPoint == "" ? "" : "<img src=\"include/images/download1.gif\" style=\"cursor: pointer;\" onclick=\"SANINCO.Download(" + o.attachmentPoint + ");\"/>");
					}
			    }]
		});
		
	if(itemTypeIdTab != null){
		var pastArray = [{ title : "<input type=\"checkbox\" class='noBorderRadioButton' name=\"box3\" onclick=\"chooseAll('box3','box_3')\"/>",
				dataField: function(o){
					return (o.proposalFlag != 1 ? "" : str(''+o.id+'','box_3',''+o.amount+'',''+o.disputeAmount+'',''+o.itemTypeId+'',''+o.creditAmount+'',''+o.originatorId+'',''+o.SCOAId+'',''+o.disputeCategoryId+''));
				}
		},{ title : "Proposal Id",dataField: "Proposal Id",sort : "p.id",display:"none"
		},{ className : "table_td_br",width:"180px",title : "Item",dataField: "itemName",sort : "ii.item_name", filtration : {name:"ii.item_name",alias:"Item"}
		},{ title : "Payment Amount",dataField: "payment",sort : "p.payment_amount", filtration : {name:"p.payment_amount",alias:"Payment"}
	    },{ title : "Dispute Amount",dataField:"dispute",sort : "p.dispute_amount"
		},{ title : "Credit Amount",dataField:"credit",sort : "p.credit_amount"
		},{ title : "SCOA",dataField:"SCOA",sort : "ac.account_code_name", filtration : {name:"ac.account_code_name",alias:"SCOA"}
		},{ title : "Service Type",dataField:"serviceType",sort : "p.service_type"
	    },{ title : "Charge Type",dataField:"chargeType",sort : "p.charge_type", filtration : {name:"p.charge_type",alias:"Charge Type"}
	    },{ title : "Circuit Number",dataField:"circuitNumber",sort : "p.circuit_number", filtration : {name:"p.circuit_number",alias:"Circuit Number"}
	    },{ title : "PON",dataField:"pon",sort : "ii.purchase_order_number"	, filtration : {name:"ii.purchase_order_number",alias:"PON"}
	    }];
		
		DisputeItemsListPage.sortingField =  "ii.id";
		DisputeItemsListPage.totalPageUri = actionUri.getDisputeItemsListTabPageNoUri;
		DisputeItemsListPage.dataUri = actionUri.searchDisputeItemsListTabUri;
		DisputeItemsListPage.cols = traversalArray(pastArray,searchSCOACodingTabHtml());
	}
	
	DisputeItemsListPage.addBeforeSearch(function(m,t){
		if(m == "start")recordProposalBoxId = {};
		delete m;
		delete t;
	});
	
	DisputeItemsListPage.addCompleteEvent(function(o){
		echoProposalId('box_3');
		delete o;
	});
	
	DisputeItemsListPage.addTotalSuccessEvent(function(data){
		var styleShow = "inline-block";
		if(data.totalResultNo == 0) styleShow = "none";
		document.getElementById('__disputeItems_download_excel_button').style.display = styleShow;
		document.getElementById('__disputeItems_group_dispute_button').style.display = styleShow;
		document.getElementById('__disputeItems_move_credit_button').style.display = styleShow;
		document.getElementById('__disputeItems_SCOA_coding_button').style.display = styleShow;
		document.getElementById('__disputeItems_SCOA_coding_button_single').style.display = styleShow;
		delete data;
	});
	
	DisputeItemsListPage.voParam = {
		invoiceId : invoiceId,
		itemTypeId : itemTypeIdTab,
		parentProposalId : parentProposalId
	};
	
	DisputeItemsListFilter = new SANINCO.Filter();
	    DisputeItemsListFilter.addEditeEvent(function(){DisputeItemsListPage.start();});
	    DisputeItemsListFilter.add('p.item_name', 'String');
	    DisputeItemsListFilter.add('ac.account_code_name', 'String');
	    DisputeItemsListFilter.add('p.payment_amount', 'String');
		DisputeItemsListFilter.add('dr.dispute_reason_name', 'String');
		DisputeItemsListFilter.add('p.circuit_number', 'String');
		DisputeItemsListFilter.add('p.service_type', 'String');
		DisputeItemsListFilter.add('p.charge_type', 'String');
		DisputeItemsListFilter.add('p.billing_number', 'String');
		DisputeItemsListFilter.add('p.notes', 'String');
		DisputeItemsListFilter.add('p.description', 'String');
		DisputeItemsListFilter.add('p.usoc', 'String');
		DisputeItemsListFilter.add('ii.purchase_order_number', 'String');
		DisputeItemsListFilter.add('ii.item_name', 'String');
	DisputeItemsListPage.setFilter(DisputeItemsListFilter);
	
	if (!SANINCO.ifAllGranted(Constants.FUNCTION.invoiceReviewAndPropsal, Constants.FUNCTION.invoiceReviewAndPropsalAction)) {
		 DisputeItemsListPage.cols[0].display = "none";
	}
	
	DisputeItemsListPage.start();

}

//get Invoice Hyperlink no itemId
function getHyperlink() {
	var callback = {
		success:renderHyperlink, 
		failure:showError
	};
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest("POST", actionUri.getInvoiceHyperlinkUri + "?invoiceId=" + invoiceId, callback);
}

function renderHyperlink(o) {
	if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
		window.location.reload();
		return;
	}
	var data = eval("(" + o.responseText + ")");
	if (data.error) {
		document.getElementById("__hyperlink").innerHTML = "Error: " + data.error;
		hideLoadingModalLayer();
		return;
	}
	var t = "";
	var pageNumber=5;
	var beforeNumber=parseInt(pageNumber/2); //2
	var currentPage=0;
	var location;
	var rows = data.data;

	//The currentPage page you want to start from the first location 
	for (var i=0; i < rows.length; i++) {
		var row = rows[i];
		if(row.id == invoiceId){
			currentPage = parseInt(i/pageNumber);

			// 需要缺省显示的当前的 invoice date 的索引值。
			location = i;  
		}
	}
	
	var begin = 5;

	
	if(begin > rows.length) begin = rows.length;
	
	for (var i=0; i < begin ; i++) {
		var row = rows[i];
		if(i == 0){
			t += ("<div class=\"slide\" style=\"text-align:left\">");
		}

		if( i == location) { // 缺省显示 invoice date.
			t += (row.date + "&nbsp;&nbsp;&nbsp;&nbsp;");
		}else{
			t += ("<a href=\"viewInvoiceDetails.action?invoiceId=" + row.id + "\">" + row.date + "</a>&nbsp;&nbsp;&nbsp;&nbsp;");
		}

		if(i == (begin-1)){
			t += ("</div>");
		}
	}
	
	for (var i=begin,j=1; i < rows.length; i++,j++) {
		var row = rows[i];

		if((j+pageNumber-1)%pageNumber==0){
			t += ("<div class=\"slide\" >");
		}

		if( i == location ) { // 缺省显示 invoice date.
			t += (row.date + "&nbsp;&nbsp;&nbsp;&nbsp;");
		}else{
			t += ("<a href=\"viewInvoiceDetails.action?invoiceId=" + row.id + "\">" + row.date + "</a>&nbsp;&nbsp;&nbsp;&nbsp;");
		} 

		if(j%pageNumber==0){
			t += ("</div>");
		}
	}
	
	document.getElementById("slidesContainer").innerHTML = t;
	slideShow();
	//JQuery slideShow
	pagesNumber(currentPage);
	delete data;
	delete rows;
	delete t;
	hideLoadingModalLayer();
}

//judgement Rights 
function judgementRightsDispute(divId){
	var bo = false;
	
	Common.valiBanLock(invoiceId,function(isId,ps){
		if (isId >= 21 || ps == "L") {
			showInnerHTML(divId,tips.permissionDeniedTips);
			bo = false;
		}
		else {
			bo = true;
		}
	});
	if(!bo) return true;
	
	bo = false;
	Common.valiUserPrivilegeBanLock(invoiceId,function(data){
		if (data != true) {
			showInnerHTML(divId,tips.noPrivilegeTips);
			bo = false;
		}else{
			bo = true;
		}
	});

	if(!bo){
		Common.valiUserPrivilegeByUserId(currentAnalystId,function(data){
			if(data != true){
				showInnerHTML(divId,tips.noPrivilegeTips);
				bo = false;
			}else{
				bo = true;
			}
		});
	}

	return !bo;
}

function showInnerHTML(divId,html){
	if (divId == "") {
		document.getElementById('__show_group_dispute_invoice').innerHTML = html;
		document.getElementById('__show_group_dispute_SCOA').innerHTML = html;
		document.getElementById('__show_group_dispute_disputed').innerHTML = html;
	}else{
		document.getElementById(divId).innerHTML = html;
	}
}

//judgement Rights 
judgementRightsResult = null;
function judgementRights(divId){
	if(judgementRightsResult == null){
		judgementRightsResult = judgementRightsCode(divId);
		return judgementRightsResult;
	}else{
		if(judgementRightsResult && divId)document.getElementById(divId).innerHTML = tips.permissionDeniedTips;
		return judgementRightsResult;
	}
}
function judgementRightsCode(divId){
	var bo = true;
	Common.valiBanLock(invoiceId,function(isId,ps){
		if (isId >= 21 || ps == "L") {
			if(divId)document.getElementById(divId).innerHTML = tips.permissionDeniedTips;
			bo = false;
		} else {
			bo = true;
		}
	});
	if(!bo) return true;
	
	bo = false;
	Common.valiUserPrivilegeBanLock(invoiceId,function(data){
		if (data != true) {
			if(divId)document.getElementById(divId).innerHTML = tips.noPrivilegeTips;
			bo = false;
		}else{
			bo = true;
		}
	});
	
	if(!bo){
		Common.valiUserPrivilegeByUserId(currentAssignmentId,function(data){
			if(data != true){
				if(divId)document.getElementById(divId).innerHTML = tips.noPrivilegeTips;
				bo = false;
			}else{
				bo = true;
			}
		});
	}
	
	return !bo;
}

function editProposalWindow() {

	var handleSubmit = function() {
		if(!uploadFileValidate("addDisputeFile")){
			return;
		}
		if(targetFun.indexOf('updateProposalByTab', 0) != -1){
			if((!verificationAmount()) || (!verificationNotes2('__notes')) || !scoa_code.requiredValidate()){
				return;
			} 
		}
		if(targetFun.indexOf('updateGroupProposalByTab', 0) != -1){
			if(!verificationNotes2('__notes')){
				return;
			} 
		}
		$("#disputeUploadForm").submit();
	};
	var handleCancel = function() {	
		this.cancel();
	};
	
	var handleAddInvoiceDisputeFile = function(){
		AddInvoiceDisputeFile();
	}
	
	YAHOO.util.Dom.removeClass("editProposalWindow", "edit-proposal-window");
	
	YAHOO.ccm.container.editProposalWindow = new YAHOO.widget.Dialog("editProposalWindow", 
							{ width : "34em",
							  fixedcenter : true,
							  visible : false, 
							  modal : true,
							  constraintoviewport : true,
							  buttons : [//{ text:"Add", handler:handleAddInvoiceDisputeFile},
							             { text:"Save", handler:handleSubmit},
								      { text:"Cancel", handler:handleCancel } ]
							});
	YAHOO.ccm.container.editProposalWindow.render();
};

function editMoveToCreditWindow() {

	var handleSubmit = function() {
		eval(targetFun);
	};
	var handleCancel = function() {	
		this.cancel();
	};
	
	YAHOO.util.Dom.removeClass("editMoveToCredit", "yui-pe-content");
	
	YAHOO.ccm.container.editMoveToCredit = new YAHOO.widget.Dialog("editMoveToCredit", 
							{ width : "25em",
							  fixedcenter : true,
							  visible : false, 
							  modal : true,
							  constraintoviewport : true,
							  buttons : [{ text:"Confirm", handler:handleSubmit},
								      { text:"Cancel", handler:handleCancel } ]
							});
	YAHOO.ccm.container.editMoveToCredit.render();
};

function editSCOACodingWindow() {

	var handleSubmit = function() {
		eval(targetFun);
	};
	var handleCancel = function() {	
		this.cancel();
	};
	
	YAHOO.util.Dom.removeClass("editSCOACodingWindow", "edit-SCOA-coding-window");
	
	YAHOO.ccm.container.editSCOACoding = new YAHOO.widget.Dialog("editSCOACodingWindow", 
							{ width : "30em",
							  fixedcenter : true,
							  visible : false, 
							  modal : true,
							  constraintoviewport : true,
							  buttons : [{ text:"Save", handler:handleSubmit},
								      { text:"Cancel", handler:handleCancel } ]
							});
	YAHOO.ccm.container.editSCOACoding.render();
};

function editSCOACodingSingleWindow() {

	var handleSubmit = function() {
		eval(targetFun);
	};
	var handleCancel = function() {	
		this.cancel();
	};
	
	YAHOO.util.Dom.removeClass("editSCOACodingSingleWindow", "edit-SCOA-coding-window-single");
	
	YAHOO.ccm.container.editSCOACodingSingle = new YAHOO.widget.Dialog("editSCOACodingSingleWindow", 
							{ width : "41em",
							  fixedcenter : true,
							  visible : false, 
							  modal : true,
							  constraintoviewport : true,
							  buttons : [{ text:"Save", handler:handleSubmit},
								      { text:"Cancel", handler:handleCancel } ]
							});
	YAHOO.ccm.container.editSCOACodingSingle.render();
};



//slideShow Hyperlink
slideShow = function(){
	  var currentPosition = 0;
	  var slideWidth = 590;
	  var slides = $('.slide'); 
	  var numberOfSlides = slides.length; // 获取页面中的silde元素的总数。
	
	  // Remove scrollbar in JS
	  $('#slidesContainer').css('overflow', 'hidden');
	
	  // Wrap all .slides with #slideInner div
	  slides
	    .wrapAll('<div id="slideInner"></div>')
	    // Float left to display horizontally, readjust .slides width
		.css({
	      'float' : 'left',
	      'width' : slideWidth
	    });
	
	  // Set #slideInner width equal to total width of all slides
	  $('#slideInner').css('width', slideWidth * numberOfSlides);
	
	  // Insert controls in the DOM
	  $('#slideshow')
	    .prepend('<span class="control" id="leftControl"></span>')
	    .append('<span class="control" id="rightControl"></span>');
	
	  // Hide left arrow control on first load
	  manageControls(currentPosition);
	
	  // Create event listeners for .controls clicks
	  $('.control')
	    .bind('click', function(){
	    // Determine new position
		currentPosition = ($(this).attr('id')=='rightControl') ? currentPosition+1 : currentPosition-1;
	
		// Hide / show controls
	    manageControls(currentPosition);
	    // Move slideInner using margin-left
	    $('#slideInner').animate({
	      'marginLeft' : slideWidth*(-currentPosition)
	    });
	  });
	
	  // manageControls: Hides and Shows controls depending on currentPosition
	  function manageControls(position){
	    // Hide left arrow if position is first slide
		if(position==0){ $('#leftControl').hide() } else{ $('#leftControl').show() }
		// Hide right arrow if position is last slide
	    if(position==numberOfSlides-1){ $('#rightControl').hide() } else{ $('#rightControl').show() }
	  }	
	  
	  // 页面加载时显示该账单的缺省日期。
	  pagesNumber= function(currentPage){
		currentPosition = currentPage;
	
		$('#slideInner').animate({
			'marginLeft' : -( slideWidth*(currentPage) )
		});

		manageControls(currentPage);
	  }
};

//accordion
accordion=function() {
	$("#accordion").accordion({
		collapsible: true
	});
};

function upLoadSCOADispute(attachmentPointId){
	$("#__attachment_point_id").val(attachmentPointId);
	eval(targetFun);
}

function recodeTaxInvoice(){

	clearTips();

	if (confirm("Are you sure you want to Recode tax?")) {
		var callback = {
			success: function(o){
	            hideLoadingModalLayer();
	            if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
	                window.location.reload();
	                return;
	            }
				var data = eval("(" + o.responseText + ")");
				if(data.error){
					alert("Error: " + data.error);
					return;
				}
	            getProposalViewList();
	        },
			failure:showError
		};
		showLoadingModalLayer();
		YAHOO.util.Connect.asyncRequest("POST", actionUri.recodeTaxInvoiceUrl + "?invoiceId=" + invoiceId, callback);
    }
}

function showLabel(e,m,t){
	var html = '<h3>'+t+':</h3><p>'+m+'</p>';
	var x=document.body.scrollLeft+e.clientX + 5;
	var y=document.body.scrollTop+e.clientY - 50;
	
	$("#LabelItem").css({   
		"position": "absolute",   
		"top": y+'px',   
		"left":x+'px'
	});
	
	$(".IDPright").html(html);
	$("#LabelItem").css("display","block");

}

function disableItemLabel(){
	$("#LabelItem").css("display","none");
}


function showCircuitDetailWindow(){
	YAHOO.util.Dom.removeClass("circuitDetail", "update-ReportAdminTags");
    YAHOO.ccm.container.circuitDetail = new YAHOO.widget.Dialog("circuitDetail", {
        fixedcenter: true,
        visible: false,
        constraintoviewport: true
    });
    
    YAHOO.ccm.container.circuitDetail.render();
}

function showCircuitDetail(e,proposalId){
	//原有逻辑保留
	var callback = {
		success: function(o){
            hideLoadingModalLayer();
			var data = o.responseText;
			$("#circuitDetailPanel").html(data);
            YAHOO.ccm.container.circuitDetail.show();	
            getHeight();
        },
		failure:showError
	};
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest("POST", actionUri.doFindCircuitDetail + "?proposalId=" + proposalId, callback);
}

/**
 * circuit number remove ( ) _ - \ : . ' ' /
 */
function stripCircuitNumber(circuitNumber) {
	return circuitNumber.replace(/[\(\)\.\:_\\\/\s-]/g, '');
}
/**
 * "Invoice Detials" 页面中点击列表中 "Circuit Number" 列值弹出的
 * "Mastet Inventory popup" 列表。
 */
function showCircuitDetailForMasterInventory(circuitNumber){
	if(1==1){
		masterInventoryListPage = new SANINCO.Page("_master_inventory","masterInventoryListPage",{
		    vo : "searchInventoryDashboardVO",
			recordText : '',
			totalPageUri : "getMasterInventoryListPageNo.action",
		    dataUri :"searchMasterInventoryList.action" ,
		    paginationDiv : "__master_inventory_page_list",
			recPerArray : [10,20,30,40,50,70,90,100],
		    cols : [
						//1//			KEY
						{ title : "Key",
						  dataField:"id",
						  filtration : {
						    name:"mid",
						    alias:"Key"
						  },
						  sort : "mid"
						},
						//3//			SUMMARY VENDOR NAME
						{ title : "Summary Vendor Name",
							  dataField:"summaryVendorName",
							  filtration : {
						        name:"summary_vendor_name",
						        alias:"Summary Vendor Name"
						      },
							  sort : "summary_vendor_name"
						},
						//4//			BAN
						{ title : "BAN",
							  dataField:"banName",
							  filtration : {
						        name:"ban_name",
						        alias:"BAN"
						      },
							  sort : "ban_name"
						},
						//5//			INVOICE NUMBER
						{ title : "Invoice Number",
							  dataField:"invoiceNumber",
							  filtration : {
						        name:"invoice_number",
						        alias:"Invoice Number"
						      },
							  sort : "invoice_number"
						},
						//6//			LINE OF BUSINESS
						{ title : "Line of Business",
							  dataField:"lineOfBusiness",
							  filtration : {
						        name:"line_of_business",
						        alias:"Line of Business"
						      },
							  sort : "line_of_business"
						},
						//7//			INVOICE DATE
						{ title : "Invoice Date",
							  dataField:"invoiceDate",
							  filtration : {
						        name:"invoice_date",
						        alias:"Invoice Date"
						      },
							  sort : "invoice_date"
						},
						//8//			STRIPPED CIRCUIT NUMBER
						{ title : "Stripped Circuit Number",
							  dataField:"strippedCircuitNumber",
							  filtration : {
						        name:"stripped_circuit_number",
						        alias:"Stripped Circuit Number"
						      },
							  sort : "stripped_circuit_number"
						},
						//10//			SERVICE ID
						{ title : "Service ID",
							  dataField:"serviceId",
							  filtration : {
						        name:"service_id",
						        alias:"Service ID"
						      },
							  sort : "service_id"
						},
						//13//			CIRCUIT ID STATUS
						{ title : "Circuit Id Status",
							  dataField:"circuitStatus",
							  filtration : {
					            name:"circuit_status",
					            alias:"Circuit Id Status"
				              },
							  sort : "circuit_status"
						},
						//15//			TYPE
						{ title : "Access Type",
							  dataField:"accessType",
							  filtration : {
						        name:"access_type",
						        alias:"Access Type"
						      },
							  sort : "access_type"
						},
						//16//			INSTALL DATE
						{ title : "Install Date",
							  dataField:"installDate",
							  filtration : {
						        name:"installation_date",
						        alias:"Install Date"
						      },
							  sort : "installation_date"
						},
						//17//			FIRST INVOICE DATE
						{ title : "First Invoice Date",
							  dataField:"firstInvoiceDate",
							  filtration : {
						        name:"first_invoice_date",
						        alias:"First Invoice Date"
						      },
							  sort : "first_invoice_date"
						},
						//18//			FIRST INVOICE NUMBER
						{ title : "First Invoice Number",
							  dataField:"firstInvoiceNumber",
							  filtration : {
						        name:"first_invoice_number",
						        alias:"First Invoice Number"
						      },
							  sort : "first_invoice_number"
						},
						//22//			DISCONNECTION DATE
						{ title : "Disconnection Date",
							  dataField:"disconnectionDate",
							  filtration : {
						        name:"disconnection_date",
						        alias:"Disconnection Date"
						      },
							  sort : "disconnection_date"
						},
						//25//			SERVICE DESCRIPTION
						{ title : "Service Description",
							  dataField:"serviceDescription",
							  filtration : {
						        name:"service_description",
						        alias:"Service Description"
						      },
							  sort : "service_description"
						},
						//26//					PRODUCTCATEGORY
						{ title : "Product Category",
							  dataField:"productCategory",
							  filtration : {
						        name:"product_category",
						        alias:"Product Category"
						      },
							  sort : "product_category"
						},
						//27//					SUBPRODUCTCATEGORY
						{ title : "Sub Product Category",
							  dataField:"subProductCategory",
							  filtration : {
						        name:"sub_product_category",
						        alias:"Sub Product Category"
						      },
							  sort : "sub_product_category"
						},
						//28//					PROJECT
						{ title : "Project",
							  dataField:"project",
							  filtration : {
						        name:"project",
						        alias:"Project"
						      },
							  sort : "project"
						},
						//29//					PROJECT CATEGORY STATUS
						{ title : "Project Category Status",
							  dataField:"projectCategoryStatus",
							  filtration : {
						        name:"project_category_status",
						        alias:"Project Category Status"
						      },
							  sort : "project_category_status"
						},
						//30//					A ADDRESS STREET NUMBER
						{ title : "A Address Street Number",
							  dataField:"aStreetNumber",
							  filtration : {
						        name:"a_street_number",
						        alias:"A Address Street Number"
						      },
							  sort : "a_street_number"
						},
						//31//					A ADDRESS STREET NAME
						{ title : "A Address Street Name",
							  dataField:"aStreetName",
							  filtration : {
						        name:"a_street_name",
						        alias:"A Address Street Name"
						      },
							  sort : "a_street_name"
						},
						//32//					A ADDRESS SUITE
						{ title : "A Address Unit",
							  dataField:"aUnit",
							  filtration : {
						        name:"a_unit",
						        alias:"A Address Unit"
						      },
							  sort : "a_unit"
						},
						//33//					A ADDRESS CITY
						{ title : "A Address City",
							  dataField:"aCity",
							  filtration : {
						        name:"a_city",
						        alias:"A Address City"
						      },
							  sort : "a_city"
						},
						//34//					A ADDRESS PROVINCE
						{ title : "A Address Province",
							  dataField:"aProvince",
							  filtration : {
						        name:"a_province",
						        alias:"A Address Province"
						      },
							  sort : "a_province"
						},
						//35//					A ADDRESS POSTAL CODE
						{ title : "A Address Postal Code",
							  dataField:"aPostalCode",
							  filtration : {
						        name:"a_postal_code",
						        alias:"A Address Postal Code"
						      },
							  sort : "a_postal_code"
						},
						//45//					AGGREGATOR CIRCUITID
						{ title : "Aggregator CircuitID",
							  dataField:"aggregatorCid",
							  filtration : {
						        name:"aggregator_cid",
						        alias:"Aggregator CircuitID"
						      },
							  sort : "aggregator_cid"
						},
						//49//					CUSTOMER BILLING ACCOUNT #
						{ title : "Customer Billing Account #",
							  dataField:"customerBillingAccount",
							  filtration : {
						        name:"customer_billing_account",
						        alias:"Customer Billing Account #"
						      },
							  sort : "customer_billing_account"
						},
						//50//					BUSINESS SEGMENT
						{ title : "Business Segment",
							  dataField:"businessSegment",
							  filtration : {
						        name:"business_segment",
						        alias:"Business Segment"
						      },
							  sort : "business_segment"
						},
						//51//					END USER
						{ title : "End User",
							  dataField:"endUser",
							  filtration : {
						        name:"end_user",
						        alias:"End User"
						      },
							  sort : "end_user"
						},
						//52//					SCOA 
						{ title : "SCOA",
							  dataField:"scoa",
							  filtration : {
						        name:"scoa",
						        alias:"SCOA"
						      },
							  sort : "scoa"
						},
						//53//					OWNER
						{ title : "Owner",
							  dataField:"owner",
							  filtration : {
						        name:"owner",
						        alias:"Owner"
						      },
							  sort : "owner"
						},
						//+ 新加入字段			USOC
						{ title : "USOC",
							  dataField:"usoc",
							  filtration : {
					            name:"usoc",
					            alias:"USOC"
				              },
							  sort : "usoc"
						},
						//65//					INTERCOMPANY BUSINESS UNIT
						{ title : "Intercompany Business Unit",
							  dataField:"intercompanyBusinessUnit",
							  filtration : {
						        name:"intercompany_business_unit",
						        alias:"Intercompany Business Unit"
						      },
							  sort : "intercompany_business_unit"
						},
						//66//					INTERCOMPANY CHANNEL
						{ title : "Intercompany Channel",
							  dataField:"intercompanyChannel",
							  filtration : {
						        name:"intercompany_channel",
						        alias:"Intercompany Channel"
						      },
							  sort : "intercompany_channel"
						},
						//67//					MODIFIED DATE
						{ title : "Modified Date",
							  dataField:"modifiedTimestamp",
							  filtration : {
						        name:"modified_timestamp",
						        alias:"Modified Date"
						      },
							  sort : "modified_timestamp"
						},
						//68//					MODIFIED USER
						{ title : "Modified User",
							  dataField:"modifiedUser",
							  filtration : {
						        name:"modified_user",
						        alias:"Modified User"
						      },
							  sort : "modified_user"
						}
					
				]
		});
	}
    if (circuitNumber == "") {
	    circuitNumber = "-1";
    }
	masterInventoryListPage.voParam = {
			banId: GLOBAL_BAN_ID,
			stripCircuitNumber: encodeURIComponent(circuitNumber)
	};
	masterInventoryListPage.addTotalSuccessEvent(function(data){
//		YAHOO.ccm.container.circuitDetail.setHeader("Master Inventory");
//		setTimeout("YAHOO.ccm.container.circuitDetail.show();", 300 );
		if(data.totalResultNo > 0) {
			$('#download_button').show();
		}
	});
	
	masterInventoryListPage.addCompleteEvent(function(data){
		YAHOO.ccm.container.circuitDetail.setHeader("Master Inventory");
		YAHOO.ccm.container.circuitDetail.show();
	});
	
	filter1 = new SANINCO.Filter();
	
	filter1.add('mid', 'number');
	filter1.add('vendor_name', 'string');
	filter1.add('summary_vendor_name', 'string');
	filter1.add('ban_name', 'string');
	filter1.add('invoice_number', 'string')
	filter1.add('line_of_business', 'string');
	filter1.add('invoice_date', 'string');
	filter1.add('stripped_circuit_number', 'string');
	filter1.add('unique_circuit_id','string');
	filter1.add('service_id','string');
	filter1.add('service_id_mrr','string');
	filter1.add('revenue_match_date','string');
	filter1.add('circuit_status','string');
	filter1.add('service_id_match_status','string');
	filter1.add('access_type','string');
	filter1.add('installation_date','string');
	filter1.add('first_invoice_date','string');
	filter1.add('first_invoice_number','string');
	filter1.add('order_number','string');
	filter1.add('order_type','string');
	filter1.add('quote_number','string');
	filter1.add('disconnection_date','string');
	filter1.add('validation_source_system','string');
	filter1.add('cost_type','string');
	filter1.add('service_description','string');
	filter1.add('product_category','string');
	filter1.add('sub_product_category','string');
	filter1.add('project','string');
	filter1.add('project_category_status','string');
	filter1.add('a_street_number','string');
	filter1.add('a_street_name','string');
	filter1.add('a_unit','string');
	filter1.add('a_city','string');
	filter1.add('a_province','string');
	filter1.add('a_postal_code','string');
	filter1.add('a_country','string');
	filter1.add('z_street_number','string');
	filter1.add('z_street_name','string');
	filter1.add('z_unit','string');
	filter1.add('z_city','string');
	filter1.add('z_province','string');
	filter1.add('z_postal_code','string');
	filter1.add('z_country','string');
	filter1.add('serving_wire_centre','string');
	filter1.add('aggregator_cid','string');
	filter1.add('time_slot_vlan_assignment','string');
	filter1.add('comments','string');
	filter1.add('trunk_group_clli','string');
	filter1.add('customer_billing_account','string');
	filter1.add('business_segment','string');
	filter1.add('end_user','string');
	filter1.add('scoa','string');
	filter1.add('owner','string');
	filter1.add('owner_email','string');
	filter1.add('last_signoff_date','string');
	filter1.add('usoc','string');
	filter1.add('intercompany_business_unit','string');
	filter1.add('intercompany_channel','string');
	filter1.add('modified_timestamp','string');
	filter1.add('modified_user','string');

	filter1.addEditeEvent(function(){masterInventoryListPage.start();});
	masterInventoryListPage.setFilter(filter1);
	

	masterInventoryListPage.start();
}
/**
 * "Invoice Detials" 页面中点击列表中 "Circuit Number" 列值弹出的
 * "Mastet Inventory popup" 列表中的 download 功能。
 */
function saveMasterInventoryToExcel(){
	showLoadingModalLayer();
	
	var postData = "";
	postData += "&searchInventoryDashboardVO.banId=" + masterInventoryListPage.voParam.banId;
	postData += "&searchInventoryDashboardVO.stripCircuitNumber=" + masterInventoryListPage.voParam.stripCircuitNumber;
	
	var titles= "titles=Key&" +
				"titles=Vendor Name&" +
				"titles=Summary Vendor Name&" +
				"titles=BAN&" +
				"titles=Invoice Number&" +
				"titles=Line of Business&" +
				"titles=Invoice Date&" +
				"titles=Stripped Circuit Number&" +
				"titles=Unique Circuit ID&" +
				"titles=Service ID&" +
				"titles=Service ID Mrr&" +
				"titles=Service ID MRR Province&" +
				"titles=Revenue Match Date&" +
				"titles=Circuit ID Status&" +
				"titles=Service ID Match Status&" +
				"titles=Access Type&" +
				"titles=Install Date&" +
				"titles=First Invoice Date&" +
				"titles=First Invoice Number&" +
				"titles=Order Number&" +
				"titles=Order Type&" +
				"titles=Quote Number&" +
				"titles=Disconnection Date&" +
				"titles=Validation Source System&" +
				"titles=Cost Type&" +
				"titles=Service Description&" +
				"titles=Product Category&" +
				"titles=Sub Product Category&" +
				"titles=Project&" +
				"titles=Project Category Status&" +
				"titles=A Address Street Number&" +
				"titles=A Address Street Name&" +
				"titles=A Address Unit&" +
				"titles=A Address City&" +
				"titles=A Address Province&" +
				"titles=A Address Postal Code&" +
				"titles=A Address Country&" +
				"titles=Z Address Street Number&" +
				"titles=Z Address Street Name&" +
				"titles=Z Address Unit&" +
				"titles=Z Address City&" +
				"titles=Z Address Province&" +
				"titles=Z Address Postal Code&" +
				"titles=Z Address Country&" +
				"titles=Region&" +
				"titles=Serving Wire Centre&" +
				"titles=Aggregator Circuit ID&" +
				"titles=Time Slot or Vlan Assignment&" +
				"titles=Comments&" +
				"titles=Trunk Group CLLI&" +
				"titles=Customer Billing Account #&" +
				"titles=Business Segment&" +
				"titles=End User&" +
				"titles=SCOA&" +
				"titles=Owner&" +
				"titles=Owner E-mail&" +
				"titles=Last Signoff Date&" +
				"titles=USOC&" +
				"titles=Intercompany Business Unit&" +
				"titles=Intercompany Channel&" +
				"titles=FSA Code&" +
				"titles=Serviceability Fibre&" +
				"titles=Serviceability Cable&" +
				"titles=Modified Date&" +
				"titles=Modified User&";
	titles = titles.replace(/#/g, "%23");
	var url = "saveExcelBySearchMasterInventory.action?"+ titles +"&" + postData;
	windowOpen(url);
	hideLoadingModalLayer();
	
}


/**
 * 通过点击右上角的关闭按钮来关闭
 * validation result modal.
 * @return {[type]} [description]
 */
function closeValidationResultModal() {
	
	
	$('#validationResultPanel .validation-result-modal-close').click(function() {
		
		// 在弹窗消失之前重新初始化弹窗的位置
		// 为水平和垂直居中。
		$('#validationResultPanel').css({
			top: '50%',
			left: '50%'
		});

		$(this).parents('#validationResult').hide(); 
		// Validation result modal 消失的时候才允许滚动外层滚动条。
		$ ('body').css('overflow', 'auto'); 
	});

	
}
//当浏览器大小变化时
$(window).resize(function () {         

	if ($('#validationResult').css("display")=="block"){
		$('#validationResultPanel').css({
			top: '33%',
			left: '25%'
		});
	}
	
});

/**
 * 关掉 validation method description modal 的方法。
 * @return {[type]} [description]
 */
function closeValidationMethodDescModal() {

	// 当点击 validaiton method 旁边的问号，弹出帮助提示，
	// 之后点击帮助提示右上角的小×,取消弹出框的显示。
	// a 链接如果有 href 属性，默认的样式就是 cursor: pointer;
	$('.desc-popup-container .help-modal-close').click(function() {
		$(this).parents('.desc-popup').hide();
	});
}




/**
 * Invoice validation_result 窗口中内容的获取，包括 Invoice Details
 * 面板中的audit_status和 tab标签列表中的 audit_status. 
 * @param  {[type]} proposalId      audit_result 表中的 proposal_id.
 * @param  {[type]} invoiceId       audit_result 表中的 invoice_id.
 * @param  {[type]} auditSourceType audit_source 表中的 audit_source_name.
 * @param  {[type]} DOMElement      当前点击的DOM元素。
 * @return {[type]}                 [description]
 */
function showValidationResult(proposalId, invoiceId, auditSourceType, DOMElement, strippedCircuitNumber){
	
	var queryString = actionUri.searchValidationResult 
						+ '?proposalId=' + proposalId 
						+ '&invoiceId=' + invoiceId 
						+ '&auditSourceType='+ auditSourceType 
						+ '&cricuitNumber=' + strippedCircuitNumber;

	var callback = {
		success: function(o) {
            hideLoadingModalLayer();
			var data = o.responseText;
			$("#validation-result-panel-content").html(data);
			$("#validationResult").ready(function(){
				// 显示 validation result modal.
				$('#validationResult').show();
		    	moveValidationResultPanel();
		    });
//			$('#validationResult').show(); // 显示 validation result modal.
			// 当validation result modal 出现的时候，禁止外围滚动条滚动。
			$ ('body').css('overflow', 'hidden');

			// 关闭 validation Result 弹出窗口。
			closeValidationResultModal();

			// 关闭 validation Result 弹出窗口中的
			// validation method description 的弹出窗口。
			closeValidationMethodDescModal();	
			
			// Audit source type 为 Tax 的时候重构页面中的notes。
			if ( "Tax" == auditSourceType ) {

				renderNotesWithTax();
			}
	         
			// Audit source type 为 Payment 的时候重构页面中的notes。
			if ( "Payment" == auditSourceType ) {

				renderNotesWithPayment();
			}
			
			// 当页面中validation result 结果中只有一条记录的时候，禁止 overflow:auto
			// 对定位元素显示的影响。
			if ( $('#validation-result-panel-content #pageContainer .table-container').length === 1 ) {
				$('#validation-result-panel-content').css('overflow-y', 'inherit');
			} else {
				$('#validation-result-panel-content').css('overflow-y', 'auto');
			}
        },
		failure:showError
	};
	showLoadingModalLayer();


	// 当查看 validation result 的时候，当前行的选中效果。
	validationResultRowSelected( DOMElement );

	YAHOO.util.Connect.asyncRequest("POST", queryString, callback);
	
	
	
}
/**
 * 为 Validation Result 验证窗口添加 拖拽事件，以及相关拖拽逻辑。
 */
function moveValidationResultPanel() {

	// 这里使用的是一个小插件 drag.js
	var bar = document.getElementById('modalHeader');
	var target = document.getElementById('validationResultPanel');
	startDrag(bar, target); // 插件中定义的方法。
	
}

/**
 * 当audit source type 为 Payment 的时候，Validation result 窗口中的 notes项
 * 的结构有变化， 如果验证不通过的话，有一个表格来显示 actual result 和
 * expect result.
 * @return {[type]} [description]
 */
function renderNotesWithPayment () {

	$('.audit-result-notes').each( function() {

		var notesData = $(this).find('span').html();
		// 将字符串转换为 JSON 数据。
		var jsonNotesData = JSON.parse( notesData );

		// 首先清空原有结构。
		$(this).empty();

		// 绘制表格结构。
		var tableStr = "<table width='100%' class='tax-validation-table'  style='table-layout:fixed;'>";
		tableStr += "<caption>" + jsonNotesData.message + "</caption>"; // Validation 验证信息。

		// 如果payment validation 的验证未通过， 返回的list数组中有值
		// 否则代表 payment validation 通过，那就只显示提示信息。
		if( jsonNotesData.list.length  !== 0 ) {

			// JSON 数据数组中的第一个元素是 tax name.
			for (var i = 0, len = jsonNotesData.list.length; i < len; i ++ ) {

				// List 数组中的第一个元素。
				if ( i == 0 ) {

					tableStr += "<tr>";
					tableStr += "<th>"+ jsonNotesData.list[i][0] +"</th>";
					tableStr += "<th>"+ jsonNotesData.list[i][1] +"</th>";
					tableStr += "<th>"+ jsonNotesData.list[i][2] +"</th>";
					tableStr += "<th>"+ jsonNotesData.list[i][3] +"</th>";
					tableStr += "<th>"+ jsonNotesData.list[i][4] +"</th>";
					tableStr += "<tr>";
				} else {

					tableStr += "<tr>";
					tableStr += "<td>"+ jsonNotesData.list[i][0] +"</td>";
					tableStr += "<td>"+ jsonNotesData.list[i][1] +"</td>";
					tableStr += "<td>"+ jsonNotesData.list[i][2] +"</td>";
					tableStr += "<td>"+ jsonNotesData.list[i][3] +"</td>";
					tableStr += "<td>"+ jsonNotesData.list[i][4] +"</td>";
					tableStr += "<tr>";
				}
			}
		}
		tableStr += "</table>";

		// 将表格结构添加到notes 显示中。
		$(this).html( tableStr );

		
	});
}


/**
 * 当audit source type 为 Tax的时候，Validation result 窗口中的 notes项
 * 的结构有变化， 如果验证不通过的话，有一个表格来显示 actual result 和
 * expect result.
 * @return {[type]} [description]
 */
function renderNotesWithTax () {

	$('.audit-result-notes').each( function() {

		var notesData = $(this).find('span').html();
		// 将字符串转换为 JSON 数据。
		var jsonNotesData = JSON.parse( notesData );

		// 首先清空原有结构。
		$(this).empty();

		// 绘制表格结构。
		var tableStr = "<table width='100%' class='tax-validation-table'>";
		tableStr += "<caption>" + jsonNotesData.message + "</caption>"; // Validation 验证信息。

		// 如果tax validation 的验证未通过， 返回的list数组中有值
		// 否则代表 tax validation 通过，那就只显示提示信息。
		if( jsonNotesData.list.length  !== 0 ) {

			// JSON 数据数组中的第一个元素是 tax name.
			for (var i = 0, len = jsonNotesData.list.length; i < len; i ++ ) {

				// List 数组中的第一个元素。
				if ( i == 0 ) {

					tableStr += "<tr>";
					tableStr += "<th>"+ jsonNotesData.list[i][0] +"</th>";
					tableStr += "<th>"+ jsonNotesData.list[i][1] +"</th>";
					tableStr += "<th>"+ jsonNotesData.list[i][2] +"</th>";
					tableStr += "<tr>";
				} else {

					tableStr += "<tr>";
					tableStr += "<td>"+ jsonNotesData.list[i][0] +"</td>";
					tableStr += "<td>"+ jsonNotesData.list[i][1] +"</td>";
					tableStr += "<td>"+ jsonNotesData.list[i][2] +"</td>";
					tableStr += "<tr>";
				}
			}
		}
		tableStr += "</table>";

		// 将表格结构添加到notes 显示中。
		$(this).html( tableStr );

		
	});
}


/**
 * 当查看 validation result 的时候，为当前行添加选中效果。
 * @param  {[type]} DOMElement [description]
 * @return {[type]}            [description]
 */
function validationResultRowSelected( DOMElement ) {

	// 移除表格中的其他的行的选中颜色。
	$('#_table_tbody_ProposalViewListPage tr').removeClass('itemSelected');

	// 只需要给当前选中行添加颜色。
	var proposalViewList = $(DOMElement).parents('tr')[0];
	$(proposalViewList).addClass('itemSelected');
}


function closeCircuitDetail(){
	YAHOO.ccm.container.circuitDetail.hide();
}

function exemptionNotes() {
	YAHOO.ccm.container.ExemptionNotesWindow = new YAHOO.widget.Dialog("exemptionNotesWindow", {
        width: "100em",
        fixedcenter: true,
        visible: false,
        modal : true,
        constraintoviewport: true
    });
    YAHOO.ccm.container.ExemptionNotesWindow.render();
}

function showExemptionNotes() {
	banId=document.getElementById("Tifflinkidid").value;
	invoiceDate = document.getElementById("_invoiceDate").innerHTML;
	searchBanExemptionCertificate(banId,invoiceDate);	
	YAHOO.ccm.container.ExemptionNotesWindow.show();
	exemptionWindowCss();


}


function openAutoPay (banId) {
	if (confirm("Are you sure you want to turn autopay back on for this BAN?")) {
		var callback = {
			success: function(o){
	            hideLoadingModalLayer();
	            if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
	                window.location.reload();
	                return;
	            }
				var data = eval("(" + o.responseText + ")");
				if(data.error){
					alert("Error: " + data.error);
					return;
				}
	            alert("Update Success.");
	            $("#autoPay_B").css("display","none");
	        },
			failure:showError
		};
		showLoadingModalLayer();
		YAHOO.util.Connect.asyncRequest("POST", actionUri.updateBanAutoPayUri + "?banId=" + banId, callback);
    }
}

function exemptionWindowCss(){
	var top = document.getElementById("exemptionNotesWindow_c").style.top;
	top = parseInt(top.substring(0,top.length-2)) - parseInt(200);
	$('#exemptionNotesWindow_c').css("top",top+"px");
	$('#_recPerPageSelect_banExemptionCertificatePage')
    .bind('change', function(){
    	setTimeout(function(){
    		$("body").scrollTop(0,0 );
    	},500);
    	})
}



function searchBanExemptionCertificate(banId,invoiceDate){
	banExemptionCertificatePage = new SANINCO.Page("banExemptionCertificateTable","banExemptionCertificatePage",{
	      sortingField : "ban_exemption.certificate_name",
	      recordText: '', 
	      sortingDirection : "asc",
	      vo : "cvo",
	//      paginationDiv: "BanExemptionCertificate_pageTable",
	      recPerArray : [5,10,15,20,30],
	      dataUri : "doSearchBanExemptionCertificate.action?invoiceDate="+invoiceDate+"&cvo.ban="+banId+"",
	      totalPageUri: "doBanExemptionCertificateTotalPageNo.action?invoiceDate="+invoiceDate+"&cvo.ban="+banId+"",
	      cols : [
	          {
			          title : "Flag",
			          sort : "ban_exemption.term_start_date",
			          dataField: function(obj){
	        	                                 var startDate = new Date(Date.parse(obj.term_start_date));  
	        	                                 var endDate = new Date(Date.parse(obj.term_end_date));  
	        	                                 var iDate = new Date(Date.parse(invoiceDate));  
	        	  
	        	                                 if(startDate <= iDate){
	        	                                	 if(iDate <= endDate){
	        	                                		 return "<img  src=\"include/images/validation_status/validation_pass.png\">"
	        	                                	 }
		        	                              }
	        	                                 if(iDate < startDate){
		        	                                 return "<img  src=\"include/images/validation_status/validation_failed.png\">"};
		        	                            }
			  },{
		          title : "File Name",
		          dataField: function(obj){
				  return "<div style=\"white-space: pre-wrap;\">"+obj.certificate_name+"</div>"
			     },
		          sort : "ban_exemption.certificate_name"
		      },{
		          title : "Notes",
		          dataField: function(obj){
				  return "<div style=\"white-space: pre-wrap;\">"+obj.notes+"</div>"
			     },
		          sort : "ban_exemption.notes"
		      },{
		    	  title : "Term Start Date",
		          dataField: "term_start_date" ,
		          sort : "ban_exemption.term_start_date"
		      },{
		    	  title : "Term End Date",
		          dataField: "term_end_date" ,
		          sort : "ban_exemption.term_end_date"
		     
		      },{
		          title : "File",
		          dataField:function(obj){
	                return "<img  src=\"include/images/download1.gif\"  style=\"cursor: pointer;\"  onClick=\"downloadFile(" + "'" + obj.certificate_name + "','" + obj.file_path + "'" + ");\">";
	            }
		      }
	      ]
	  });
	
	banExemptionCertificatePage.start();
}

