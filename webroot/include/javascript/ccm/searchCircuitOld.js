//Create by Donghao.guo 2011/09/22
var saveQuicklinkUri = "doSaveCircuitSearchOld.action";
var getCircuitTotalPageNoUri = "doGetCircuitSearchTotalPageNoOld.action";
var searchCircuitUri = "doSearchCircuitOld.action";
var saveExcelBySearchCircuitUri = "doSaveExcelOld.action";
var getCircuitHyperlinkUri = "dogetCircuitDateHyperlinkOld.action";
var tabView;
var voInvoiceYearMonth = null;
var searchCondition;
var downAllCondition;
var connection = null;
var pageList = new Array();


var showCanStopLoadingModalLayer = function(){
    if (!YAHOO.ccm.container.canStopWait) {
        YAHOO.ccm.container.canStopWait = new YAHOO.widget.Panel("wait", {
        	width: "240px", 
            fixedcenter: true, 
            close: false, 
            draggable: false, 
            zindex:4,
            modal: true,
            visible: false
       	});
        YAHOO.ccm.container.canStopWait.setHeader("Loading, please wait...");
       	YAHOO.ccm.container.canStopWait.setBody("<img src=\"include/images/loadingbar.gif\"/><input type='button' value='Stop' style='float:right;' onclick='stopCircuitOld()'/>");
        YAHOO.ccm.container.canStopWait.render(document.body);
    }
	YAHOO.ccm.container.canStopWait.show();
}
var hideCanStopLoadingModalLayer = function(){
	if (YAHOO.ccm.container.canStopWait) YAHOO.ccm.container.canStopWait.hide();
}
function stop() {
	hideCanStopLoadingModalLayer();
	invoiceTabInit.searchMRC = "searchMRC()";
	invoiceTabInit.searchOCC = "searchOCC()";
	invoiceTabInit.searchUsage = "searchUsage()";
	invoiceTabInit.searchTax = "searchTax()";
	invoiceTabInit.searchPrevious ="searchPrevious()";
	invoiceTabInit.searchLPC = "searchLPC()";
	invoiceTabInit.searchCredit = "searchCredit()";
	invoiceTabInit.searchPayment = "searchPayment()";
	invoiceTabInit.searchAdjustment = "searchAdjustment()";
	if (connection != null && connection != undefined) {
		YAHOO.util.Connect.abort(connection, null, true);
	}
	if (pageList != null && pageList != undefined) {
		for (var i = 0; i < pageList.length; i++) {
			var page = pageList[i];
			page.abortSending();
		}
	}
}

function showVendorNameListDialog(){
	if($("#Vendor_Name_List").val()!=""){
		vendor_name_list_value.value=$("#Vendor_Name_List").val();
	}
	$("#editVendorNameListDialog").show();
	var handleSubmit = function() {
		YAHOO.ccm.container.editVendorNameListDialog.hide();
		var a = removeLineOnly(vendor_name_list_value.value);
		$("#Vendor_Name_List").val($.trim(a));
		if( $.trim($("#Vendor_Name_List").val()).length>0){
			form0_searchCircuitVO_vendorId.setValue("all");
			$("#VL_vendorList_1_input").attr('disabled', true);
			$("#VL_vendorList_1_arrow").attr('disabled', true);
		}else{
			$("#VL_vendorList_1_input").attr('disabled', false);
			$("#VL_vendorList_1_arrow").attr('disabled', false);
		}
	};
	var handleCancel = function(){
		vendor_name_list_value.value="";
		this.cancel();
	};
	var handleClear = function(){
		vendor_name_list_value.value="";
	}
	YAHOO.util.Dom.removeClass("editVendorNameListDialog", "yui-pe-content");
	
	YAHOO.ccm.container.editVendorNameListDialog = new YAHOO.widget.Dialog("editVendorNameListDialog", 
							{ width : "350px",
							  fixedcenter : true,
							  visible : false, 
							  constraintoviewport : true,
							  buttons : [ { text:"Submit", handler:handleSubmit},
								      { text:"Clear", handler:handleClear },{ text:"Cancel", handler:handleCancel } ]
							});
	YAHOO.ccm.container.editVendorNameListDialog.render();
	YAHOO.ccm.container.editVendorNameListDialog.show();
}
function showBanListDialog(){
	if($("#Ban_List").val()!=""){
		ban_list_value.value=$("#Ban_List").val();
	}
	$("#editBanListDialog").show();
	var handleSubmit = function() {
		YAHOO.ccm.container.editBanListDialog.hide();
		var a = escapeLineBreak(ban_list_value.value);
		$("#Ban_List").val(a);
		if( $.trim($("#Ban_List").val()).length>0){
			form0_searchCircuitVO_banId.setValue("all");
			$("#VL_banList_Invoice_input").attr('disabled', true);
			$("#VL_banList_Invoice_arrow").attr('disabled', true);
		}else{
			$("#VL_banList_Invoice_input").attr('disabled', false);
			$("#VL_banList_Invoice_arrow").attr('disabled', false);
		}
	};
	var handleClear = function(){
		ban_list_value.value="";
	};
	var handleCancel = function() {
		ban_list_value.value="";
		this.cancel();
	};
	YAHOO.util.Dom.removeClass("editBanListDialog", "yui-pe-content");
	
	YAHOO.ccm.container.editBanListDialog = new YAHOO.widget.Dialog("editBanListDialog", 
							{ width : "350px",
							  fixedcenter : true,
							  visible : false, 
							  constraintoviewport : true,
							  buttons : [ { text:"Submit", handler:handleSubmit},
								      { text:"Clear", handler:handleClear },{ text:"Cancel", handler:handleCancel } ]
							});
	YAHOO.ccm.container.editBanListDialog.render();
	YAHOO.ccm.container.editBanListDialog.show();
}
function showCircuitDialog(){
	if($("#Circuit_Number_List").val()!=""){
		circuit_number_list_value.value=$("#Circuit_Number_List").val();
	}
	$("#editCircuitDialog").show();
	var handleSubmit = function() {
		YAHOO.ccm.container.editCircuitDialog.hide();
		var a = escapeLineBreak(circuit_number_list_value.value);
		$("#Circuit_Number_List").val(a);
		if( $.trim($("#Circuit_Number_List").val()).length>0){
			$("#VL_Stripped_Circuit_Number").val("");
			$("#VL_Stripped_Circuit_Number").attr('disabled', true);
		}else{
			$("#VL_Stripped_Circuit_Number").attr('disabled', false);
		}
	};
	var handleClear = function(){
		circuit_number_list_value.value="";
	};
	var handleCancel = function() {
		circuit_number_list_value.value="";
		this.cancel();
	};
	YAHOO.util.Dom.removeClass("editCircuitDialog", "yui-pe-content");
	
	YAHOO.ccm.container.editCircuitDialog = new YAHOO.widget.Dialog("editCircuitDialog", 
							{ width : "350px",
							  fixedcenter : true,
							  visible : false, 
							  constraintoviewport : true,
							  buttons : [ { text:"Submit", handler:handleSubmit},
								      { text:"Clear", handler:handleClear },{ text:"Cancel", handler:handleCancel } ]
							});
	YAHOO.ccm.container.editCircuitDialog.render();
	YAHOO.ccm.container.editCircuitDialog.show();
}
//Change the state of the input box and color
function clearDisabled(id,number){
	if ($('#'+ id)[0].checked) {
		$(".Clear" + number).css("background","").attr("disabled","").val("");
	}else{
		$(".Clear" + number).css("background","#ccc").attr("disabled","disabled").val("");;
	}
}
function composePostDataFromUi(){
//	var postData = "searchCircuitVO.historicalInvoice="+$('#__historical_invoice')[0].checked;
	var postData = "searchCircuitVO.historicalInvoice=false";
	if(form0_searchCircuitVO_vendorId.getValue()!="all"){
		postData += "&searchCircuitVO.vendorId="+form0_searchCircuitVO_vendorId.getValue();
	}
	if(form0_searchCircuitVO_banId.getValue()!="all"){
		postData += "&searchCircuitVO.banId=" + form0_searchCircuitVO_banId.getValue();
	}
	if($.trim($("#Vendor_Name_List").val()).length>0){
		postData += "&searchCircuitVO.vendorList=" + $("#Vendor_Name_List").val();
	}
	if($.trim($("#Ban_List").val()).length>0){
		postData += "&searchCircuitVO.banList=" + $("#Ban_List").val();
	}
	if($("#Line_Of_Business").val()!= "all"){
		postData += "&searchCircuitVO.lineOfbusiness=" + $("#Line_Of_Business").val();
	}
	if($.trim($("#VL_Stripped_Circuit_Number").val()).length>0){
		postData += "&searchCircuitVO.strippedCircuit=" + $("#VL_Stripped_Circuit_Number").val();
	}
	if($.trim($("#Circuit_Number_List").val()).length>0){
		postData += "&searchCircuitVO.strippedCircuitList=" + $("#Circuit_Number_List").val();
	}
	if($.trim($("#Line_Item_Code").val()).length>0){
		postData += "&searchCircuitVO.lineItemCode=" + $("#Line_Item_Code").val();
	}
	if($.trim($("#Full_SCOA_Code").val()).length>0){
		postData += "&searchCircuitVO.fullScoaCode=" + $("#Full_SCOA_Code").val();
	}
	if($.trim($("#SCOA_Company").val()).length>0){
		postData += "&searchCircuitVO.company=" + $("#SCOA_Company").val();
	}
	if($.trim($("#SCOA_Location").val()).length>0){
		postData += "&searchCircuitVO.location=" + $("#SCOA_Location").val();
	}
	if($.trim($("#SCOA_Departement").val()).length>0){
		postData += "&searchCircuitVO.departement=" + $("#SCOA_Departement").val();
	}
	if($.trim($("#SCOA_Production").val()).length>0){
		postData += "&searchCircuitVO.production=" + $("#SCOA_Production").val();
	}
	if($.trim($("#SCOA_Account").val()).length>0){
		postData += "&searchCircuitVO.account=" + $("#SCOA_Account").val();
	}
	if($.trim($("#SCOA_Channel").val()).length>0){
		postData += "&searchCircuitVO.channel=" + $("#SCOA_Channel").val();
	}
	if($.trim($("#searchCircuitVO_invoiceDateStartsOn").val()).length>0){
		postData += "&searchCircuitVO.invoiceDateStartsOn=" + $("#searchCircuitVO_invoiceDateStartsOn").val();
	}
	if($.trim($("#searchCircuitVO_invoiceDateEndsOn").val()).length>0){
		postData += "&searchCircuitVO.invoiceDateEndsOn=" + $("#searchCircuitVO_invoiceDateEndsOn").val();
	}
	if($.trim($("#searchCircuitVO_invoiceDateWiPastNumOfDays").val()).length>0){
		postData += "&searchCircuitVO.invoiceDateWiPastNumOfDays=" + $("#searchCircuitVO_invoiceDateWiPastNumOfDays").val();
	}
	if($.trim($("#searchCircuitVO_startDateStartsOn").val()).length>0){
		postData += "&searchCircuitVO.invoicestartDateStartsOn=" + $("#searchCircuitVO_startDateStartsOn").val();
	}
	if($.trim($("#searchCircuitVO_startDateEndsOn").val()).length>0){
		postData += "&searchCircuitVO.invoicestartDateEndsOn=" + $("#searchCircuitVO_startDateEndsOn").val();
	}
	if($.trim($("#searchCircuitVO_startDateWiPastNumOfDays").val()).length>0){
		postData += "&searchCircuitVO.invoicestartDateWiPastNumOfDays=" + $("#searchCircuitVO_startDateWiPastNumOfDays").val();
	}
	if($.trim($("#searchCircuitVO_endDateStartsOn").val()).length>0){
		postData += "&searchCircuitVO.invoiceendDateStartsOn=" + $("#searchCircuitVO_endDateStartsOn").val();
	}
	if($.trim($("#searchCircuitVO_endDateEndsOn").val()).length>0){
		postData += "&searchCircuitVO.invoiceendDateEndsOn=" + $("#searchCircuitVO_endDateEndsOn").val();
	}
	if($.trim($("#searchCircuitVO_endDateWiPastNumOfDays").val()).length>0){
		postData += "&searchCircuitVO.invoiceendDateWiPastNumOfDays=" + $("#searchCircuitVO_endDateWiPastNumOfDays").val();
	}
	if($.trim($("#searchCircuitVO_endDateWiNextNumOfDays").val()).length>0){
		postData += "&searchCircuitVO.invoiceendDateWiNextNumOfDays=" + $("#searchCircuitVO_endDateWiNextNumOfDays").val();
	}
	return postData;
}
function validateFields(){
	var formatValid = FIC_checkForm("form0");
	if(formatValid){
		if(getCheckedValue(form0.selectInvoiceDateRadio)=="DATEFRAME"){
			form0.searchCircuitVO_invoiceDateWiPastNumOfDays.value="";
		}else{
			form0.searchCircuitVO_invoiceDateStartsOn.value="";
			form0.searchCircuitVO_invoiceDateEndsOn.value="";
		}
		if(getCheckedValue(form0.selectStartDateRadio)=="DATEFRAME"){
			form0.searchCircuitVO_startDateWiPastNumOfDays.value="";
		}else{
			form0.searchCircuitVO_startDateStartsOn.value="";
			form0.searchCircuitVO_startDateEndsOn.value="";
		}
		if(getCheckedValue(form0.selectEndDateRadio)=="DATEFRAME"){
			form0.searchCircuitVO_endDateWiPastNumOfDays.value="";
			form0.searchCircuitVO_endDateWiNextNumOfDays.value="";
		}else if(getCheckedValue(form0.selectEndDateRadio)=="WITHINPAST"){
			form0.searchCircuitVO_endDateStartsOn.value="";
			form0.searchCircuitVO_endDateEndsOn.value="";
			form0.searchCircuitVO_endDateWiNextNumOfDays.value="";
		}else{
			form0.searchCircuitVO_endDateStartsOn.value="";
			form0.searchCircuitVO_endDateEndsOn.value="";
			form0.searchCircuitVO_endDateWiPastNumOfDays.value="";
		}
		if(getCheckedValue(form0.selectSCOARadio)=="FULLSCOA"){
			form0.SCOA_Company.value="";
			form0.SCOA_Location.value="";
			form0.SCOA_Departement.value="";
			form0.SCOA_Production.value="";
			form0.SCOA_Account.value="";
			form0.SCOA_Channel.value="";
		}else{
			form0.Full_SCOA_Code.value="";
		}
	}
	return formatValid;
}
function judgDate(){
	var timePast = $('#searchCircuitVO_invoiceDateWiPastNumOfDays').val();
	var timePastTwo = $('#searchCircuitVO_startDateWiPastNumOfDays').val();
	var timePastThree = $('#searchCircuitVO_endDateWiPastNumOfDays').val();
	var timeNextFour = $('#searchCircuitVO_endDateWiNextNumOfDays').val();
	var nodes = YAHOO.util.Selector.query('input[cssClass=validate-date-ca]');
	var bo = true;
	
	for(var d = 0; d<nodes.length; d++){
		if(!isValidDate(nodes[d].value)) {
			removeClassName(nodes[d].id,'validation-passed');
			addClassName(nodes[d].id,'validation-failed');
			bo = false;
		}
	}
	for(var d = 0; d<nodes.length; d=d+2){
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
	}
	
	if(timePast < 0 || timePast > 10000 || timePast.match(/[^0-9]/)) {
		removeClassName('searchCircuitVO_invoiceDateWiPastNumOfDays','validation-passed');
		addClassName('searchCircuitVO_invoiceDateWiPastNumOfDays','validation-failed');
		bo = false;
	}
	if(timePastTwo < 0 || timePastTwo > 10000 || timePastTwo.match(/[^0-9]/)) {
		removeClassName('searchCircuitVO_startDateWiPastNumOfDays','validation-passed');
		addClassName('searchCircuitVO_startDateWiPastNumOfDays','validation-failed');
		bo = false;
	}
	if(timePastThree < 0 || timePastThree > 10000 || timePastThree.match(/[^0-9]/)) {
		removeClassName('searchCircuitVO_endDateWiPastNumOfDays','validation-passed');
		addClassName('searchCircuitVO_endDateWiPastNumOfDays','validation-failed');
		bo = false;
	}
	if(timeNextFour < 0 || timeNextFour > 10000 || timeNextFour.match(/[^0-9]/)) {
		removeClassName('searchCircuitVO_endDateWiNextNumOfDays','validation-passed');
		addClassName('searchCircuitVO_endDateWiNextNumOfDays','validation-failed');
		bo = false;
	}
	
	return bo;
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
function changRadio(type){
	if (type == 0) {
		form0.selectSCOARadio[0].checked = true;
		$('#SCOA_Company').val("");
		$('#SCOA_Location').val("");
		$('#SCOA_Departement').val("");
		$('#SCOA_Production').val("");
		$('#SCOA_Account').val("");
		$('#SCOA_Channel').val("");
	}
	if (type == 1) {
		form0.selectSCOARadio[1].checked = true;
		$('#Full_SCOA_Code').val("");
	}
	if (type == 2) {
		form0.selectInvoiceDateRadio[0].checked = true;
		$('#searchCircuitVO_invoiceDateWiPastNumOfDays').val("");
	}
	if (type == 3) {
		form0.selectInvoiceDateRadio[1].checked = true;
		$('#searchCircuitVO_invoiceDateStartsOn').val("");
		$('#searchCircuitVO_invoiceDateEndsOn').val("");
	}
	if (type == 4) {
		form0.selectStartDateRadio[0].checked = true;
		$('#searchCircuitVO_startDateWiPastNumOfDays').val("");
	}
	if (type == 5) {
		form0.selectStartDateRadio[1].checked = true;
		$('#searchCircuitVO_startDateStartsOn').val("");
		$('#searchCircuitVO_startDateEndsOn').val("");
	}
	if (type == 6) {
		form0.selectEndDateRadio[0].checked = true;
		$('#searchCircuitVO_endDateWiPastNumOfDays').val("");
		$('#searchCircuitVO_endDateWiNextNumOfDays').val("");
	}
	if (type == 7) {
		form0.selectEndDateRadio[1].checked = true;
		$('#searchCircuitVO_endDateStartsOn').val("");
		$('#searchCircuitVO_endDateEndsOn').val("");
		$('#searchCircuitVO_endDateWiNextNumOfDays').val("");
	}
	if (type == 8) {
		form0.selectEndDateRadio[2].checked = true;
		$('#searchCircuitVO_endDateStartsOn').val("");
		$('#searchCircuitVO_endDateEndsOn').val("");
		$('#searchCircuitVO_endDateWiPastNumOfDays').val("");
	}
}
function escapeLineBreak(str) {
	if (str == null || str == "") {
		return "";
	}
	str = str.replace(new RegExp("\n","gm"),"");
	str = str.replace(new RegExp("\r","gm"),"");
	str = str.replace(new RegExp(" ","gm"),"");
	return str;
}
function removeLineOnly(str) {
	if (str == null || str == "") {
		return "";
	}
	str = str.replace(new RegExp("\n","gm"),"");
	str = str.replace(new RegExp("\r","gm"),"");
	return str;
}
function resetRadioButton(){
	form0.selectSCOARadio[0].checked=true;
	form0.selectInvoiceDateRadio[0].checked=true;
	form0.selectStartDateRadio[0].checked=true;
	form0.selectEndDateRadio[0].checked=true;
}
function updateRadioButton(){
	if(form0.searchCircuitVO_invoiceDateStartsOn.value!="" || form0.searchCircuitVO_invoiceDateEndsOn.value!=""){
		form0.selectInvoiceDateRadio[0].checked = true;
	}
	if(form0.searchCircuitVO_invoiceDateWiPastNumOfDays.value!=""){
		form0.selectInvoiceDateRadio[1].checked = true;
	}
	if(form0.searchCircuitVO_startDateWiPastNumOfDays.value!=""){
		form0.selectStartDateRadio[1].checked = true;
	}
	if(form0.searchCircuitVO_startDateStartsOn.value!="" || form0.searchCircuitVO_startDateEndsOn.value!=""){
		form0.selectStartDateRadio[0].checked = true;
	}
	if(form0.SCOA_Company.value !="" || form0.SCOA_Location.value !="" || form0.SCOA_Departement.value !="" || form0.SCOA_Production.value !="" || form0.SCOA_Account.value !="" || form0.SCOA_Channel.value !="" ){
		form0.selectSCOARadio[1].checked = true;
	}
	if(form0.Full_SCOA_Code.value != ""){
		form0.selectSCOARadio[0].checked = true;
	}
	if(form0.searchCircuitVO_endDateStartsOn.value!=""||form0.searchCircuitVO_endDateEndsOn.value!=""){
		form0.selectEndDateRadio[0].checked = true;
	}
	if(form0.searchCircuitVO_endDateWiPastNumOfDays.value!=""){
		form0.selectEndDateRadio[1].checked = true;
	}
	if(form0.searchCircuitVO_endDateWiNextNumOfDays.value!=""){
		form0.selectEndDateRadio[2].checked = true;
	}
	updateVendorAndBanTextfield();
}
function setJSelectQuicklink(k,v,d){
	if(k == "searchCircuitVO.vendorId"){
		form0_searchCircuitVO_vendorId.setValue(v);
		return true;
	}
	if(k == "searchCircuitVO.banId"){
		form0_searchCircuitVO_banId.setValue(v);
		return true;
	}
	/*
	if(k == "searchCircuitVO.historicalInvoice"){
		if(v=="true"){
			$('#__historical_invoice')[0].checked = true;
			$('#searchButton').hide();
			$('#downloadExcel').show();
		}else{
			$('#__historical_invoice')[0].checked = false;
			$('#searchButton').show();
			$('#downloadExcel').hide();
		}
		
	}
	 */
	return false;
}
function updateVendorAndBanTextfield(){
	if($.trim($("#Vendor_Name_List").val()).length>0){
		form0_searchCircuitVO_vendorId.setValue("all");
		$("#VL_vendorList_1_input").attr('disabled', true);
		$("#VL_vendorList_1_arrow").attr('disabled', true);
	}else{
		$("#VL_vendorList_1_input").attr('disabled', false);
		$("#VL_vendorList_1_arrow").attr('disabled', false);
	}
	if($.trim($("#Ban_List").val()).length>0){
		form0_searchCircuitVO_banId.setValue("all");
		$("#VL_banList_Invoice_input").attr('disabled', true);
		$("#VL_banList_Invoice_arrow").attr('disabled', true);
	}else{
		$("#VL_banList_Invoice_input").attr('disabled', false);
		$("#VL_banList_Invoice_arrow").attr('disabled', false);
	}
	if($.trim($("#Circuit_Number_List").val()).length>0){
		$("#VL_Stripped_Circuit_Number").val("");
		$("#VL_Stripped_Circuit_Number").attr('disabled', true);
	}else{
		$("#VL_Stripped_Circuit_Number").attr('disabled', false);
		$("#VL_Stripped_Circuit_Number").attr('disabled', false);
	}
}
YAHOO.util.Event.onDOMReady(function(){
});
function resetFormElementValue(){
	cleanFormElementValue();
	FIC_checkForm(form0);
	$("#VL_vendorList_1_input").attr('disabled', false);
	$("#VL_vendorList_1_arrow").attr('disabled', false);
	$("#VL_banList_Invoice_input").attr('disabled', false);
	$("#VL_banList_Invoice_arrow").attr('disabled', false);
	$("#VL_Stripped_Circuit_Number").attr('disabled', false);
	form0_searchCircuitVO_vendorId.setValue("all");
	form0_searchCircuitVO_banId.setValue("all");
	clearCheckbox();
}

function clearCheckbox(){
	$(".ClearCheckbox").removeAttr("checked");
	$(".Date").css("background","#ccc").attr("disabled","disabled").val("");
}
function clearJSelectQuicklink(){
	form0_searchCircuitVO_vendorId.setValue("all");
	form0_searchCircuitVO_banId.setValue("all");
}
function startSearch(){
//	if($('#__historical_invoice')[0].checked){
//		return;
//	}
	if(composePostDataFromUi()=="searchCircuitVO.historicalInvoice=false"){
		alert("You must enter search criteria!");
		return;
	}
	if((!validateFields()) || (!judgDate())) return;
	showCanStopLoadingModalLayer();
	if(voInvoiceYearMonth!=null){voInvoiceYearMonth = null;}
	downAllCondition = composePostDataFromUi();
	getHyperlink();
	invoiceTabInit.searchMRC = "searchMRC()";
	invoiceTabInit.searchOCC = "searchOCC()";
	invoiceTabInit.searchUsage = "searchUsage()";
	invoiceTabInit.searchTax = "searchTax()";
	invoiceTabInit.searchPrevious ="searchPrevious()";
	invoiceTabInit.searchLPC = "searchLPC()";
	invoiceTabInit.searchCredit = "searchCredit()";
	invoiceTabInit.searchPayment = "searchPayment()";
	invoiceTabInit.searchAdjustment = "searchAdjustment()";
}
function searchMRC(){
	mrcPage.myParam = searchCondition+"&searchCircuitVO.itemType=MRC";
	mrcPage.filter.clearAll();
	mrcPage.start();
}
function searchOCC(){
	occPage.myParam = searchCondition+"&searchCircuitVO.itemType=OCC";
	occPage.filter.clearAll();
	occPage.start();
}
function searchUsage(){
	usagePage.myParam = searchCondition+"&searchCircuitVO.itemType=Usage";
	usagePage.filter.clearAll();
	usagePage.start();
}
function searchTax(){
	taxPage.myParam = searchCondition+"&searchCircuitVO.itemType=Tax";
	taxPage.filter.clearAll();
	taxPage.start();
}
function searchPayment(){
	paymentPage.myParam = searchCondition+"&searchCircuitVO.itemType=Payment";
	paymentPage.filter.clearAll();
	paymentPage.start();
}
function searchPrevious(){
	previousPage.myParam = searchCondition+"&searchCircuitVO.itemType=Previous Adjustment";
	previousPage.filter.clearAll();
	previousPage.start();
}
function searchLPC(){
	lpcPage.myParam = searchCondition+"&searchCircuitVO.itemType=LPC";
	lpcPage.filter.clearAll();
	lpcPage.start();
}
function searchCredit(){
	creditPage.myParam = searchCondition+"&searchCircuitVO.itemType=Credit";
	creditPage.filter.clearAll();
	creditPage.start();
}
function searchAdjustment(){
	adjustmentPage.myParam = searchCondition+"&searchCircuitVO.itemType=Adjustment";
	adjustmentPage.filter.clearAll();
	adjustmentPage.start();
}
function searchAll(){
	circuitPage.myParam = searchCondition;
	circuitPage.filter.clearAll();
	circuitPage.start();
}
function searchCircuitBydate(InvoiceYearMonth){
	showCanStopLoadingModalLayer();
	voInvoiceYearMonth = InvoiceYearMonth;
	getHyperlink();
	invoiceTabInit.searchMRC = "searchMRC()";
	invoiceTabInit.searchOCC = "searchOCC()";
	invoiceTabInit.searchUsage = "searchUsage()";
	invoiceTabInit.searchTax = "searchTax()";
	invoiceTabInit.searchPrevious ="searchPrevious()";
	invoiceTabInit.searchLPC = "searchLPC()";
	invoiceTabInit.searchCredit = "searchCredit()";
	invoiceTabInit.searchPayment = "searchPayment()";
	invoiceTabInit.searchAdjustment = "searchAdjustment()";
}
var invoiceTabInit = {
	searchMRC : "searchMRC();",
	searchOCC : "searchOCC();",
	searchUsage : "searchUsage();",
	searchTax : "searchTax();",
	searchPrevious : "searchPrevious();",
	searchLPC : "searchLPC();",
	searchCredit : "searchCredit();",
	searchPayment : "searchPayment();",
	searchAdjustment : "searchAdjustment();",
	searchAll : "searchAll();",
	get : function (key){
		if(key == "MRC"){
			eval(invoiceTabInit.searchMRC);
			invoiceTabInit.searchMRC = "";
		}else if(key == "OCC"){
			eval(invoiceTabInit.searchOCC);
			invoiceTabInit.searchOCC = "";
		}else if(key == "Usage"){
			eval(invoiceTabInit.searchUsage);
			invoiceTabInit.searchUsage = "";
		}else if(key == "Tax"){
			eval(invoiceTabInit.searchTax);
			invoiceTabInit.searchTax = "";
		}else if(key == "tab5"){
			eval(invoiceTabInit.searchAll);
			invoiceTabInit.searchAll = "";
		}else if(key == "LPC"){
			eval(invoiceTabInit.searchLPC);
			invoiceTabInit.searchLPC = "";
		}else if(key == "Credit"){
			eval(invoiceTabInit.searchCredit);
			invoiceTabInit.searchCredit = "";
		}else if(key == "Payment"){
			eval(invoiceTabInit.searchPayment);
			invoiceTabInit.searchPayment = "";
		}else if(key == "Adjustment"){
			eval(invoiceTabInit.searchAdjustment);
			invoiceTabInit.searchAdjustment = "";
		}else if(key == "Previous Adjustment"){
			eval(invoiceTabInit.searchPrevious);
			invoiceTabInit.searchPrevious = "";
		}
	}
};
function createPage(dateTable,dateTablePage,pageName,saveExcelButtonId){
		var newPage = new SANINCO.Page(dateTable,pageName,{
		    sortingField : "",
		    sortingDirection : "",
			vo : "searchCircuitVO",
			pageTable : "block",
		    totalPageUri : getCircuitTotalPageNoUri,
		    dataUri : searchCircuitUri,
			paginationDiv : dateTablePage,
			recPerArray : [10,20,30,40,50,70,90,100],
		    cols : [
					{   title : "Stripped Circuit Number",dataField: "circuitNumber",sort : "stripped_circuit_number", filtration : {name:"ii.stripped_circuit_number",alias:"Stripped Circuit Number"}
					},{ title : "Invoice Number",dataField: "invoice_number",sort : "invoice_number", filtration : {name:"i.invoice_number",alias:"Invoice Number"}
					},{ title : "Bill Date",dataField: "billDate",sort : "invoice_date", filtration : {name:"i.invoice_date",alias:"Bill Date"}
				    },{ title : "Bill Year",dataField: "billYear",sort : "invoice_year", filtration : {name:"year(i.invoice_date)",alias:"Bill Year"}
					},{ title : "Bill Month",dataField: "billMonth",sort : "invoice_month", filtration : {name:"month(i.invoice_date)",alias:"Bill Month"}				
				    },{ title : "BAN",dataField: "ban",sort : "account_number",filtration : {name:"b.account_number",alias:"BAN"}
					},{ title : "Vendor Name",dataField: "vendor",sort : "vendor_name", filtration : {name:"v.vendor_name",alias:"Vendor Name"}
				    },{ title : "Charge Type",dataField: "chargeType",sort : "charge_type", filtration : {name:"it.item_type_name",alias:"Charge Type"}	    
				    },{ title : "Invoice Amount",dataField: function(o){ if(pageName=="mrcPage" && (o.prevInvoiceAmount==null||o.prevInvoiceAmount=="")){return "<a href=\"###\" title=\"Prev. MRC:NULL ;    Curr. MRC:"+o.currInvoiceAmount+"\"><font color=\"green\">"+o.invoiceAmount+"</font></a>";}else if(pageName=="mrcPage" && ((Number(o.currInvoiceAmount)-Number(o.prevInvoiceAmount))>100 || (Number(o.currInvoiceAmount)-Number(o.prevInvoiceAmount)) > (Number(o.prevInvoiceAmount)*0.1))){return "<a href=\"###\" title=\"Prev. MRC:"+o.prevInvoiceAmount+";   Curr. MRC:"+o.currInvoiceAmount+"\"><font color=\"red\">"+o.invoiceAmount+"</font></a>";}else{return o.invoiceAmount;}}/*,sort : "invoiceAmount", filtration : {name:"invoiceAmount",alias:"Invoice Amount"}*/
				    },{ title : "SCOA",dataField: "scoa",sort : "account_code_name", filtration : {name:"account_code_name",alias:"SCOA"}			    
				    },{ title : "Currency",dataField: "currency",sort : "currency_name", filtration : {name:"currency_name",alias:"Currency"}
				    },{ title : "Line of Business",dataField: "lineOfbusiness",sort : "line_of_business", filtration : {name:"b.line_of_business",alias:"Line of Business"}
				    },{ title : "Analyst",dataField: "analyst",sort : "assigned_analyst_name", filtration : {name:"concat(u.first_name,blank_space(),u.last_name)",alias:"Analyst"}
				    },{ title : "Line Item Code",dataField: "lineItemCode"/*,sort : "ii.line_item_code", filtration : {name:"ii.line_item_code",alias:"Line Item Code"}*/
				    },{ title : "Line Item Code Description",dataField: "lineItemCodeDescription"/*,sort : "ii.line_item_code_description", filtration : {name:"ii.line_item_code_description",alias:"Line Item Code Description"}*/
				    },{ title : "Usoc",dataField: "usoc",sort : "usoc", filtration : {name:"ii.usoc",alias:"Usoc"}
				    },{ title : "Usoc Description",dataField: "usoc_description",sort : "usoc_description", filtration : {name:"ii.usoc_description",alias:"Usoc Description"}
				    }
				]
		});
		newPage.addTotalSuccessEvent(function(data){
			if(data.totalResultNo <= 0 || data.error){
				document.getElementById(saveExcelButtonId).style.display = "none";
				if(saveExcelButtonId=="saveAllToExcel"){
					document.getElementById("saveAllOfMonthToExcel").style.display = "none";
				}
			}else{
				document.getElementById(saveExcelButtonId).style.display = "block";
				if(saveExcelButtonId=="saveAllToExcel"){
					document.getElementById("saveAllOfMonthToExcel").style.display = "block";
				}
			}
		});
		if(pageName=="mrcPage"){
			newPage.addCompleteEvent(function(){
					$("#defcal_section a").colorTip({color:'black'});
			});
		}
		var filter = new SANINCO.Filter();
   	    filter.addEditeEvent(function(){newPage.start();});
    	filter.add('ii.stripped_circuit_number', 'String');
    	filter.add('i.invoice_number', 'String');
    	filter.add('i.invoice_date', 'date');
		filter.add("year(i.invoice_date)", 'Number'); // invoice_year
		filter.add("month(i.invoice_date)", 'Number');  //invoice_month
		filter.add('b.account_number', 'String');
		filter.add('v.vendor_name', 'String');
		filter.add('it.item_type_name', 'String'); // charge_type
		//filter.add('invoiceAmount', 'String');
		filter.add('account_code_name', 'String');
		filter.add('currency_name', 'String');
		filter.add('b.line_of_business', 'String');
		filter.add("concat(u.first_name,blank_space(),u.last_name)", 'String'); // assigned_analyst_name
		filter.add("ii.usoc", 'String');
		filter.add("ii.usoc_description", 'String');
	//	filter.add('ii.line_item_code', 'String');
		//filter.add('ii.line_item_code_description', 'String');
    	newPage.setFilter(filter);
    	newPage.showCustomLoadingModalLayer = showCanStopLoadingModalLayer;
    	newPage.hideCustomLoadingModalLayer = hideCanStopLoadingModalLayer;
    	pageList.push(newPage);
    	
    	return newPage;
	
}
function saveMRCExcel(){
	showCanStopLoadingModalLayer();
	var titles = mrcPage.getTitlesParam("titles");
	var uri = saveExcelBySearchCircuitUri + "?"+ titles +"&"+ mrcPage.paramStr;
	windowOpen(uri);
	hideCanStopLoadingModalLayer();
}
function saveOCCExcel(){
	showCanStopLoadingModalLayer();
	var titles = occPage.getTitlesParam("titles");
	var uri = saveExcelBySearchCircuitUri + "?"+ titles +"&"+ occPage.paramStr;
	windowOpen(uri);
	hideCanStopLoadingModalLayer();
}
function saveUsageExcel(){
	showCanStopLoadingModalLayer();
	var titles = usagePage.getTitlesParam("titles");
	var uri = saveExcelBySearchCircuitUri + "?"+ titles +"&"+ usagePage.paramStr;
	windowOpen(uri);
	hideCanStopLoadingModalLayer();
}
function saveTaxExcel(){
	showCanStopLoadingModalLayer();
	var titles = taxPage.getTitlesParam("titles");
	var uri = saveExcelBySearchCircuitUri + "?"+ titles +"&"+ taxPage.paramStr;
	windowOpen(uri);
	hideCanStopLoadingModalLayer();
}
function savePaymentExcel(){
	showCanStopLoadingModalLayer();
	var titles = paymentPage.getTitlesParam("titles");
	var uri = saveExcelBySearchCircuitUri + "?"+ titles +"&"+ paymentPage.paramStr;
	windowOpen(uri);
	hideCanStopLoadingModalLayer();
}
function saveLPCExcel(){
	showCanStopLoadingModalLayer();
	var titles = lpcPage.getTitlesParam("titles");
	var uri = saveExcelBySearchCircuitUri + "?"+ titles +"&"+ lpcPage.paramStr;
	windowOpen(uri);
	hideCanStopLoadingModalLayer();
}
function saveAdjustmentExcel(){
	showCanStopLoadingModalLayer();
	var titles = adjustmentPage.getTitlesParam("titles");
	var uri = saveExcelBySearchCircuitUri + "?"+ titles +"&"+ adjustmentPage.paramStr;
	windowOpen(uri);
	hideCanStopLoadingModalLayer();
}
function saveCreditExcel(){
	showCanStopLoadingModalLayer();
	var titles = creditPage.getTitlesParam("titles");
	var uri = saveExcelBySearchCircuitUri + "?"+ titles +"&"+ creditPage.paramStr;
	windowOpen(uri);
	hideCanStopLoadingModalLayer();
}
function savePreviousAdjustment(){
	showCanStopLoadingModalLayer();
	var titles = previousPage.getTitlesParam("titles");
	var uri = saveExcelBySearchCircuitUri + "?"+ titles +"&"+ previousPage.paramStr;
	windowOpen(uri);
	hideCanStopLoadingModalLayer();
}
function saveAllExcel(){
	showCanStopLoadingModalLayer();
	var titles = circuitPage.getTitlesParam("titles");
	var uri =  "doSaveAllToExcelOld.action?"+ titles +"&"+ circuitPage.paramStr;
	windowOpen(uri);
	hideCanStopLoadingModalLayer();
}
function downAlltoExcel(){
	showCanStopLoadingModalLayer();
	var titles = circuitPage.getTitlesParam("titles");
	var uri =  "doSaveAllToExcelOld.action?"+ titles +"&"+downAllCondition;
	windowOpen(uri);
	hideCanStopLoadingModalLayer();
}
function initializationTabName(){
	$.ajax({
        url: "dogetTabViewOld.action",
        type: "POST",
        dataType: "text",
        async: false,
        cache: false,
        data: (searchCondition),
        success: function(o){
            if (o.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                window.location.reload();
                return;
            }
            document.getElementById("slideshow").innerHTML = "<div id=\"slidesContainer\"></div>";
			var data = eval("(" + o + ")");
			var rows = data.data;
			var tar = "<div id=\"demo\" class=\"yui-navset\">" 
			        + "<ul class=\"yui-nav\">"
		            +"<li>"
					+"<a href=\"#tab5\" onclick=\"invoiceTabInit.get('tab5');\" ><em>All</em> </a>"
					+"</li>";
			if(rows != undefined)
			{
				for(var i = 0; i < rows.length; i++){
					tar +="<li>";
					tar +="<a href=\"#"+escapeLineBreak(rows[i].itemTypeName)+"\" onclick=\"invoiceTabInit.get('"+rows[i].itemTypeName+"');\"><em>"+rows[i].itemTypeName+"</em></a>";
					tar +="</li>";
			}
			}
			    tar +="</ul>"
				    +"<table border=\"0\" style=\"table-layout: fixed; width: 100%;\">"
				    +"<tr> "
					+"<td>"
					+"<div class=\"yui-content\">"
			        +"<div id=\"tab5\" align=\"left\" style=\"width: 100%; overflow-x: auto; padding-bottom: 20px;\">"
					+"<div id=\"_dataTable\"></div><br/>"
					+"<div class=\"allpagecons\">"
					+"<div id=\"_dataTable_page\" style=\"float:left;margin-right: 20px;\"></div>"
					+"<div style=\"float:left; margin-right: 20px;\">"
					+"<input type=\"button\" id=saveAllToExcel value=\"Download to CSV\" onclick=\"saveAllExcel();\" style=\"display: none;\" /><input type=\"button\" id= value=\"Download All to CSV\" onclick=\"saveAllExcel();\" style=\"display: none;\" />"
					+"</div>"
					+"<div style=\"float:left;\">" 
					+"<input type=\"button\" id=saveAllOfMonthToExcel  value=\"Download All to CSV\" onclick=\"downAlltoExcel();\" style=\"display: none;\" />" 
					+"</div>"
					+"</div>"
					+"</div>";
		    if(rows != undefined)
			{
				for(var i = 0; i < rows.length; i++){
					tar +="<div id=\""+escapeLineBreak(rows[i].itemTypeName)+"\" align=\"left\" style=\"width: 100%; overflow-x: auto; padding-bottom: 20px;\">"
						+"<div id=\""+escapeLineBreak(rows[i].itemTypeName)+"_dataTable\"></div><br/>"
						+"<div id=\""+escapeLineBreak(rows[i].itemTypeName)+"_dataTable_page\"></div>"
						+"<div style=\"margin-top: -21px; margin-left: 250px;\">"
						+"<input type=\"button\" id=save"+escapeLineBreak(rows[i].itemTypeName)+"ToExcel value=\"Download to CSV\" onclick=\"save"+escapeLineBreak(rows[i].itemTypeName)+"Excel();\" style=\"display: none;\" />"
						+"</div>"
						+"</div>";
				}
			} 
				tar +="</div>"
					+"</td>"
					+"</tr>"
					+"</table>"
					+"</div>";
				document.getElementById("defcal_section").innerHTML = tar;
   				tabView = new YAHOO.widget.TabView("demo");
				$("#demo li a:eq(0)").click();
				tabView.selectTab(0);
				circuitPage = createPage("_dataTable","_dataTable_page","circuitPage","saveAllToExcel");
			if(rows != undefined)
			{
				for(var i = 0; i < rows.length; i++){
					if(rows[i].itemTypeName=="MRC"){mrcPage = createPage(rows[i].itemTypeName+"_dataTable",rows[i].itemTypeName+"_dataTable_page","mrcPage","saveMRCToExcel");}
					if(rows[i].itemTypeName=="Adjustment"){adjustmentPage = createPage(rows[i].itemTypeName+"_dataTable",rows[i].itemTypeName+"_dataTable_page","adjustmentPage","save"+rows[i].itemTypeName+"ToExcel");}
					if(rows[i].itemTypeName=="LPC"){lpcPage=createPage(rows[i].itemTypeName+"_dataTable",rows[i].itemTypeName+"_dataTable_page","lpcPage","save"+rows[i].itemTypeName+"ToExcel");}
					if(rows[i].itemTypeName=="Usage"){usagePage = createPage(rows[i].itemTypeName+"_dataTable",rows[i].itemTypeName+"_dataTable_page","usagePage","saveUsageToExcel");}
					if(rows[i].itemTypeName=="OCC"){occPage = createPage(rows[i].itemTypeName+"_dataTable",rows[i].itemTypeName+"_dataTable_page","occPage","saveOCCToExcel");}
					if(rows[i].itemTypeName=="Credit"){creditPage=createPage(rows[i].itemTypeName+"_dataTable",rows[i].itemTypeName+"_dataTable_page","creditPage","save"+rows[i].itemTypeName+"ToExcel");}
					if(rows[i].itemTypeName=="Payment"){paymentPage=createPage(rows[i].itemTypeName+"_dataTable",rows[i].itemTypeName+"_dataTable_page","paymentPage","save"+rows[i].itemTypeName+"ToExcel");}
					if(rows[i].itemTypeName=="Tax"){taxPage =createPage(rows[i].itemTypeName+"_dataTable",rows[i].itemTypeName+"_dataTable_page","taxPage","saveTaxToExcel");}
					if(rows[i].itemTypeName=="Previous Adjustment"){previousPage=createPage(escapeLineBreak(rows[i].itemTypeName)+"_dataTable",escapeLineBreak(rows[i].itemTypeName)+"_dataTable_page","creditPage","save"+escapeLineBreak(rows[i].itemTypeName)+"ToExcel");}
				}
			}
			delete rows;
			delete data;
        },
		error : showError
     });
	 return "";
}
function getHyperlink() {
	var callback = {
		success:renderHyperlink, 
		failure:showError
	};
	showCanStopLoadingModalLayer();
	connection = YAHOO.util.Connect.asyncRequest("POST", getCircuitHyperlinkUri + "?" + downAllCondition, callback);
}

function renderHyperlink(o) {
	if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
		window.location.reload();
		return;
	}
	hideCanStopLoadingModalLayer();
	var data = eval("(" + o.responseText + ")");
	if(data.data==undefined){
		searchCondition = downAllCondition;
		initializationTabName();
		circuitPage.myParam = searchCondition;
		circuitPage.filter.clearAll();
		circuitPage.start();
		hideCanStopLoadingModalLayer();
		return;
	}
	if (data.error) {
		//document.getElementById("__hyperlink").innerHTML = "Error: " + data.error;
		hideCanStopLoadingModalLayer();
		return;
	}
	var t = "";
	var pageNumber=7;
	var beforeNumber=parseInt(pageNumber/2);//3
	var currentPage=0;
	var location;
	var rows = data.data;
	if(voInvoiceYearMonth==null){
		for (var i=0; i < rows.length; i++) {
		var row = rows[i];
		if(row.invoiceYearMonth == rows[rows.length-1].invoiceYearMonth){
			location=i-beforeNumber;
			currentPage = parseInt(((location-1)/pageNumber)+1);
		}
		}
		voInvoiceYearMonth = rows[rows.length-1].invoiceYearMonth;
	}
	if(voInvoiceYearMonth!=null){
		for (var i=0; i < rows.length; i++) {
		var row = rows[i];
		if(row.invoiceYearMonth == voInvoiceYearMonth){
			location=i-beforeNumber;
			currentPage = parseInt(((location-1)/pageNumber)+1);
		}
		}
	}
	searchCondition = downAllCondition;
	if(voInvoiceYearMonth!=null){
		searchCondition += "&searchCircuitVO.invoiceYearMonth="+voInvoiceYearMonth;
	}
	initializationTabName();
	circuitPage.myParam = searchCondition;
	circuitPage.filter.clearAll();
	circuitPage.start();
	tabView.selectTab(0);
	var begin = location+pageNumber;

	//The first page has several 
	for(var i=location;  i > 0 ; i=i-pageNumber) {
		begin = i;
	}
	
	if(begin > rows.length) begin = rows.length;
	
	for (var i=0; i < begin ; i++) {
		var row = rows[i];
		if(i == 0){
			t += ("<div class=\"slide\">");
		}
		if(i==(location+beforeNumber)){
			t += (row.invoiceYearMonth + "&nbsp;&nbsp;&nbsp;&nbsp;");
		}else{
			t += ("<a href=\"javascript:searchCircuitBydate('"+row.invoiceYearMonth+"');\">" + row.invoiceYearMonth + "</a>&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		if(i == (begin-1)){
			t += ("</div>");
		}
	}
	
	for (var i=begin,j=1; i < rows.length; i++,j++) {
		var row = rows[i];
		if((j+pageNumber-1)%pageNumber==0){
			t += ("<div class=\"slide\">");
		}
		if(i==(location+beforeNumber)){
			t += (row.invoiceYearMonth + "&nbsp;&nbsp;&nbsp;&nbsp;");
		}else{
			t += ("<a href=\"javascript:searchCircuitBydate('"+row.invoiceYearMonth+"');\">" + row.invoiceYearMonth + "</a>&nbsp;&nbsp;&nbsp;&nbsp;");
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
}
slideShow = function(){
	  var currentPosition = 0;
	  var slideWidth = 480;
	  var slides = $('.slide');
	  var numberOfSlides = slides.length;
	
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
	    .prepend('<span  class="control" id="leftControl"></span>')
	    .append('<span  class="control" id="rightControl"></span>');
	
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
	  
	  pagesNumber= function(o){
	  currentPosition = o;
	   $('#slideInner').animate({
	      'marginLeft' : slideWidth*(-o)
	    });
	    manageControls(o);
	  }
};
/*
function historicalChange(){
	if($('#__historical_invoice')[0].checked){
		$('#searchButton').hide();
		$('#downloadExcel').show();
	}else{
		//$('#__historical_invoice')[0].checked=false;
		$('#searchButton').show();
		$('#downloadExcel').hide();
	}
}
*/
function downloadHistoricalData(){
	showCanStopLoadingModalLayer();
	var titles = "titles=Stripped Circuit Number&titles=Invoice Number&titles=Bill Date&titles=Bill Year&titles=Bill Month&titles=BAN&titles=Vendor Name&titles=Charge Type&titles=Invoice Amount&titles=SCOA&titles=Currency&titles=Line of Business&titles=Analyst&titles=Line Item Code&titles=Line Item Code Description";
	var uri =  "dosaveHistoricalDataToExcelOld.action?"+ titles +"&"+ composePostDataFromUi();
	windowOpen(uri);
	hideCanStopLoadingModalLayer();
}
