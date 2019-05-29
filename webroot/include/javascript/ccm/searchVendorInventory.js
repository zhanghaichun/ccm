var saveQuicklinkUri = "doSaveVendorInventorySearch.action";
var vendorInventoryPage = null;
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
       	YAHOO.ccm.container.canStopWait.setBody("<img src=\"include/images/loadingbar.gif\"/><input type='button' value='Stop' style='float:right;' onclick='stop()'/>");
        YAHOO.ccm.container.canStopWait.render(document.body);
    }
	YAHOO.ccm.container.canStopWait.show();
}
var hideCanStopLoadingModalLayer = function(){
	if (YAHOO.ccm.container.canStopWait) YAHOO.ccm.container.canStopWait.hide();
}
function stop() {
	hideCanStopLoadingModalLayer();
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

//Change the state of the input box and color
function clearDisabled(id,number){
	if ($('#'+ id)[0].checked) {
		$(".Clear" + number).css("background","").attr("disabled","").val("");
	}else{
		$(".Clear" + number).css("background","#ccc").attr("disabled","disabled").val("");;
	}
}

//YAHOO.util.Event.onDOMReady(function(){
//	
//});
//
//$(document).ready(function(){
//	debugger;
//	vendorInventoryPage = createVendorInventoryPage();
//});

function createVendorInventoryPage() {
	var newPage = new SANINCO.Page("_vendorInventory_dataTable","vendorInventoryPage",{
	    sortingField : "",
	    sortingDirection : "",
		vo : "searchVendorInventoryVO",
		pageTable : "block",
	    totalPageUri : "doGetVendorInventorySearchTotalPageNo.action",
	    dataUri : "doSearchVendorInventory.action",
		paginationDiv : "_vendorInventory_dataTable_page",
		recPerArray : [10,20,30,40,50,70,90,100],
	    cols : [
				{   title : "Inventory Id", dataField: "id",sort : "vi.id", filtration : {name:"vi.id",alias:"Inventory Id"}
			    },{ title : "Quote Number",dataField: "quoteNumber",sort : "vi.quote_number", filtration : {name:"vi.quote_number",alias:"Quote Number"}
			    },{ title : "Vendor Name",dataField: "vendorName",sort : "v.vendor_name", filtration : {name:"v.vendor_name",alias:"Vendor Name"}
			    },{ title : "Product",dataField: "productName",sort : "vp.product_name", filtration : {name:"vp.product_name",alias:"Product"}
			    },{ title : "Access",dataField: "accessName",sort : "va.access_name", filtration : {name:"va.access_name",alias:"Access Name"}
			    },{ title : "Bandwidth",dataField: "bandwidthName",sort : "vb.bandwidth_name", filtration : {name:"vb.bandwidth_name",alias:"Bandwidth"}
			    },{ title : "QoS",dataField: "qosName",sort : "vq.qos_name", filtration : {name:"vq.qos_name",alias:"QoS"}
			    },{ title : "MRC",dataField: "mrc",sort : "vi.mrc", filtration : {name:"vi.mrc",alias:""}
			    },{ title : "NRC",dataField: "nrc",sort : "vi.nrc", filtration : {name:"vi.nrc",alias:""}
			    },{ title : "Construction Cost",dataField: "constructionCost",sort : "vi.construction_cost", filtration : {name:"vi.construction_cost",alias:"Vendor Account "}
			    },{ title : "Date Issued",dataField: "dataIssued",sort : "vi.date_issued", filtration : {name:"vi.date_issued",alias:""}
			    },{ title : "Expiry Date",dataField: "expiryDate",sort : "vi.expiry_date", filtration : {name:"vi.expiry_date",alias:""}
			    },{ title : "Term in Month",dataField: "termName",sort : "CONVERT(vt.term_name,SIGNED)", filtration : {name:"CONVERT(vt.term_name,SIGNED)",alias:"Term in Month"}
			    },{ title : "Unit",dataField: "unit",sort : "vi.unit", filtration : {name:"vi.unit",alias:""}
			    },{ title : "Street Number",dataField: "streetNumber",sort : "CONVERT(vi.street_number,SIGNED)", filtration : {name:"CONVERT(vi.street_number,SIGNED)",alias:""}
			    },{ title : "Street Name",dataField: "streetName",sort : "vi.street_name", filtration : {name:"vi.street_name",alias:""}
			    },{ title : "City",dataField: "city",sort : "vi.city", filtration : {name:"vi.city",alias:""}
			    },{ title : "Province/State",dataField: "province",sort : "vi.province", filtration : {name:"vi.province",alias:""}
			    },{ title : "Postal Code",dataField: "postalCode",sort : "vi.postal_code", filtration : {name:"vi.postal_code",alias:""}
			    },{ title : "Country",dataField: "country",sort : "vi.country", filtration : {name:"vi.country",alias:""}
			    },{ title : "Latitude",dataField: "latitude",sort : "vi.latitude", filtration : {name:"vi.latitude",alias:""}
			    },{ title : "Longitude",dataField: "logitude",sort : "vi.longitude", filtration : {name:"vi.longitude",alias:""}
			    },{ title : "Opportunity Name",dataField: "opportunityName",sort : "vi.opportunity_name", filtration : {name:"vi.opportunity_name",alias:""}
			    }
			]
	});
	var filter = new SANINCO.Filter();
	    filter.addEditeEvent(function(){newPage.start();});
	filter.add('vi.id', 'Integer');
	filter.add("vi.quote_number", 'String');
	filter.add("v.vendor_name", 'String');                
	filter.add("vp.product_name", 'String');
	filter.add('va.access_name', 'String');
	filter.add('vb.bandwidth_name', 'String');          
	filter.add('vq.qos_name', 'String');
	filter.add('vi.mrc', 'Double');
	filter.add('vi.nrc', 'Double');
	filter.add('vi.construction_cost', 'Double');
	filter.add('vi.date_issued', 'Date');
	filter.add('vi.expiry_date', 'Date');
	filter.add('CONVERT(vt.term_name,SIGNED)','Integer');
	filter.add('vi.date_issued', 'Date');
	filter.add('vi.unit', 'String');
	filter.add('CONVERT(vi.street_number,SIGNED)', 'String');
	filter.add('vi.street_name', 'String');
	filter.add('vi.city', 'String');
	filter.add('vi.province', 'String');
	filter.add('vi.postal_code', 'String');
	filter.add('vi.country', 'String');
	filter.add('vi.latitude', 'Double');
	filter.add('vi.longitude', 'Double');
	filter.add('vi.opportunity_name', 'String');
	newPage.setFilter(filter);
	newPage.showCustomLoadingModalLayer = showCanStopLoadingModalLayer;
	newPage.hideCustomLoadingModalLayer = hideCanStopLoadingModalLayer;
	newPage.addTotalSuccessEvent(function(data){
		document.getElementById('_vendoryInventoryDiv').style.display = "block";
	});
	return newPage;	
}

function getVendorInventoryList(){
//	if((!validateFields()) || (!judgDate())) return;
//	if((!judgDate())) return;
	var composePostData = composePostDataFromUi();
	vendorInventoryPage = createVendorInventoryPage();
	vendorInventoryPage.myParam = composePostData;
	vendorInventoryPage.filter.clearAll();
	vendorInventoryPage.start();
}

function startSearch() {
	getVendorInventoryList();
}

function composePostDataFromUi(){
	var postData = "";
	if($("#Term_in_Month").val()!= "all"){
		postData += "&searchVendorInventoryVO.termId=" + $("#Term_in_Month").val();
	}
	if(form0_searchVendorInventory_vendorId.getValue()!="all"){
		postData += "&searchVendorInventoryVO.vendorId="+form0_searchVendorInventory_vendorId.getValue();
	}
	if(form0_searchVendorInventory_productId.getValue()!="all"){
		postData += "&searchVendorInventoryVO.productId=" + form0_searchVendorInventory_productId.getValue();
	}
	if(form0_searchVendorInventory_accessId.getValue()!="all"){
		postData += "&searchVendorInventoryVO.accessId=" + form0_searchVendorInventory_accessId.getValue();
	}
	if(form0_searchVendorInventory_bandwidthId.getValue()!="all"){
		postData += "&searchVendorInventoryVO.bandwidthId=" + form0_searchVendorInventory_bandwidthId.getValue();
	}
	if(form0_searchVendorInventory_qosId.getValue()!="all"){
		postData += "&searchVendorInventoryVO.qosId=" + form0_searchVendorInventory_qosId.getValue();
	}
	if($.trim($("#Inventory_Id").val()).length>0){
		postData += "&searchVendorInventoryVO.inventoryId=" + circuitEscape($("#Inventory_Id").val());
	}
	if($.trim($("#Quote_Number").val()).length>0){
		postData += "&searchVendorInventoryVO.quoteNumber=" + circuitEscape($("#Quote_Number").val());
	}
	if($.trim($("#Opportunity_Name").val()).length>0){
		postData += "&searchVendorInventoryVO.opportunityName=" + circuitEscape($("#Opportunity_Name").val());
	}
	if(form0_searchVendorInventory_country.getValue() != "all"){
		postData += "&searchVendorInventoryVO.country=" + form0_searchVendorInventory_country.getName();
	}
	if(form0_searchVendorInventory_province.getValue() != "all"){
		postData += "&searchVendorInventoryVO.province=" + form0_searchVendorInventory_province.getName();
	}
	if(form0_searchVendorInventory_city.getValue() != "all"){
		postData += "&searchVendorInventoryVO.city=" + form0_searchVendorInventory_city.getName();
	}
	if($.trim($("#Inventory_Street_Name").val()).length>0){
		postData += "&searchVendorInventoryVO.streetName=" + circuitEscape($("#Inventory_Street_Name").val());
	}
	if($.trim($("#Inventory_Street_Number").val()).length>0){
		postData += "&searchVendorInventoryVO.streetNumber=" + circuitEscape($("#Inventory_Street_Number").val());
	}
	if($.trim($("#Inventory_Unit").val()).length>0){
		postData += "&searchVendorInventoryVO.unit=" + circuitEscape($("#Inventory_Unit").val());
	}
	if($.trim($("#Inventory_Postal_Code").val()).length>0){
		postData += "&searchVendorInventoryVO.postalCode=" + circuitEscape($("#Inventory_Postal_Code").val());
	}
	if($.trim($("#Inventory_Latitude_From").val()).length>0){
		postData += "&searchVendorInventoryVO.latitudeFrom=" + $("#Inventory_Latitude_From").val();
	}
	if($.trim($("#Inventory_Latitude_To").val()).length>0){
		postData += "&searchVendorInventoryVO.latitudeTo=" + $("#Inventory_Latitude_To").val();
	}
	if($.trim($("#Inventory_Longitude_From").val()).length>0){
		postData += "&searchVendorInventoryVO.longitudeFrom=" + $("#Inventory_Longitude_From").val();
	}
	if($.trim($("#Inventory_Longitude_To").val()).length>0){
		postData += "&searchVendorInventoryVO.longitudeTo=" + $("#Inventory_Longitude_To").val();
	}
	if($.trim($("#Inventory_MRC_From").val()).length>0){
		postData += "&searchVendorInventoryVO.mrcFrom=" + $("#Inventory_MRC_From").val();
	}
	if($.trim($("#Inventory_MRC_To").val()).length>0){
		postData += "&searchVendorInventoryVO.mrcTo=" + $("#Inventory_MRC_To").val();
	}
	if($.trim($("#Inventory_NRC_From").val()).length>0){
		postData += "&searchVendorInventoryVO.nrcFrom=" + $("#Inventory_NRC_From").val();
	}
	if($.trim($("#Inventory_NRC_To").val()).length>0){
		postData += "&searchVendorInventoryVO.nrcTo=" + $("#Inventory_NRC_To").val();
	}
	if($.trim($("#Inventory_Construction_Cost_From").val()).length>0){
		postData += "&searchVendorInventoryVO.constructionCostFrom=" + $("#Inventory_Construction_Cost_From").val();
	}
	if($.trim($("#Inventory_Construction_Cost_To").val()).length>0){
		postData += "&searchVendorInventoryVO.constructionCostTo=" + $("#Inventory_Construction_Cost_To").val();
	}
	if($.trim($("#searchVendorInventoryVO_IssuedDateStartsOn").val()).length>0){
		postData += "&searchVendorInventoryVO.dateIssuedFrom=" + $("#searchVendorInventoryVO_IssuedDateStartsOn").val();
	}
	if($.trim($("#searchVendorInventoryVO_IssuedDateEndsOn").val()).length>0){
		postData += "&searchVendorInventoryVO.dateIssuedTo=" + $("#searchVendorInventoryVO_IssuedDateEndsOn").val();
	}
	if($.trim($("#searchVendorInventoryVO_expiryDateStartsOn").val()).length>0){
		postData += "&searchVendorInventoryVO.expiryDateFrom=" + $("#searchVendorInventoryVO_expiryDateStartsOn").val();
	}
	if($.trim($("#searchVendorInventoryVO_expiryDateEndsOn").val()).length>0){
		postData += "&searchVendorInventoryVO.expiryDateTo=" + $("#searchVendorInventoryVO_expiryDateEndsOn").val();
	}
	return postData;
}

function resetFormElementValue(){
	cleanFormElementValue();
	FIC_checkForm(form0);
	$("#VL_vendorList_1_input").attr('disabled', false);
	$("#VL_vendorList_1_arrow").attr('disabled', false);
	$("#PL_productList_s_input").attr('disabled', false);
	$("#PL_productList_s_arrow").attr('disabled', false);
	$("#AL_accessList_s_input").attr('disabled', false);
	$("#AL_accessList_s_arrow").attr('disabled', false);
	$("#BWL_productList_s_input").attr('disabled', false);
	$("#BWL_productList_s_arrow").attr('disabled', false);
	$("#QL_productList_s_input").attr('disabled', false);
	$("#QL_productList_s_arrow").attr('disabled', false);
	$("#Inventory_Country_input").attr('disabled', false);
	$("#Inventory_Country_arrow").attr('disabled', false);
	$("#Inventory_City_input").attr('disabled', false);
	$("#Inventory_City_arrow").attr('disabled', false);
	$("#Inventory_Province_input").attr('disabled', false);
	$("#Inventory_Province_arrow").attr('disabled', false);
	form0_searchVendorInventory_vendorId.setValue("all");
	form0_searchVendorInventory_productId.setValue("all");
	form0_searchVendorInventory_accessId.setValue("all");
	form0_searchVendorInventory_bandwidthId.setValue("all");
	form0_searchVendorInventory_qosId.setValue("all");
	form0_searchVendorInventory_country.setValue("all");
	form0_searchVendorInventory_province.setValue("all");
	form0_searchVendorInventory_city.setValue("all");
	clearCheckbox();
}

function resetRadioButton(){
	//form0.selectSCOARadio[0].checked=true;
	form0.selectIssuedDateRadio.checked=true;
	form0.selectExpiryDateRadio.checked=true;
}

function clearCheckbox(){
	$(".ClearCheckbox").removeAttr("checked");
	$(".Date").css("background","#ccc").attr("disabled","disabled").val("");
}

function circuitEscape(s){
	if(typeof s == 'string'){
		s=s.replace(/\'/g,"'\'");
		return escape(s).replace(/\+/g, '%2B');
	}else{
		return s;
	}
}
function validateFields(){
	var formatValid = FIC_checkForm("form0");
	if(formatValid){
//		if(getCheckedValue(form0.selectIssuedDateRadio)=="DATEFRAME"){
//			//form0.searchCircuitVO_invoiceDateWiPastNumOfDays.value="";
//		}else{
//			form0.searchCircuitVO_invoiceDateStartsOn.value="";
//			form0.searchCircuitVO_invoiceDateEndsOn.value="";
//		}
//		if(getCheckedValue(form0.selectStartDateRadio)=="DATEFRAME"){
//			form0.searchCircuitVO_startDateWiPastNumOfDays.value="";
//		}else{
//			form0.searchCircuitVO_startDateStartsOn.value="";
//			form0.searchCircuitVO_startDateEndsOn.value="";
//		}
//		if(getCheckedValue(form0.selectEndDateRadio)=="DATEFRAME"){
//			form0.searchCircuitVO_endDateWiPastNumOfDays.value="";
//			form0.searchCircuitVO_endDateWiNextNumOfDays.value="";
//		}else if(getCheckedValue(form0.selectEndDateRadio)=="WITHINPAST"){
//			form0.searchCircuitVO_endDateStartsOn.value="";
//			form0.searchCircuitVO_endDateEndsOn.value="";
//			form0.searchCircuitVO_endDateWiNextNumOfDays.value="";
//		}else{
//			form0.searchCircuitVO_endDateStartsOn.value="";
//			form0.searchCircuitVO_endDateEndsOn.value="";
//			form0.searchCircuitVO_endDateWiPastNumOfDays.value="";
//		}
		/*if(getCheckedValue(form0.selectSCOARadio)=="FULLSCOA"){
			form0.SCOA_Company.value="";
			form0.SCOA_Location.value="";
			form0.SCOA_Departement.value="";
			form0.SCOA_Production.value="";
			form0.SCOA_Account.value="";
			form0.SCOA_Channel.value="";
		}else{
			form0.Full_SCOA_Code.value="";
		}*/
	}
	return formatValid;
}

function checkTheDisputeAmount(e){
	if(e.value.length > 0){
		var req = /^([\d]{1,}([\.]{1}[\d]{1,})|([\d]{0,}))$/;
		if ((!req.exec(e.value))||(e.value == "")||(e.value < 0)) {
			alert("Only allowed to enter the Numbers.");
			e.value = "";
		}
		if(e.value.length>15){
			alert("Maixmum 15 digits!");
		}
	}
}


function judgDate(){
	var bo = true;
    var InventoryId = $.trim($("#Inventory_Id").val());
    if(!InventoryId.match(/^[1-9]+[0-9]*]*$/)&&InventoryId!=''){
		removeClassName('Inventory_Id','validation-passed');
		addClassName('Inventory_Id','validation-failed');
		bo = false;   	
    }else{
    	removeClassName('Inventory_Id','validation-failed');
		addClassName('Inventory_Id','validation-passed');
    }
    var InventoryLatitudeFrom = $.trim($("#Inventory_Latitude_From").val());
    if(!InventoryLatitudeFrom.match(/^(\d+\.\d{1,2}|\d+)$/)&&InventoryLatitudeFrom!=''){
		removeClassName('Inventory_Latitude_From','validation-passed');
		addClassName('Inventory_Latitude_From','validation-failed');
		bo = false;   	
    }else{
    	removeClassName('Inventory_Latitude_From','validation-failed');
		addClassName('Inventory_Latitude_From','validation-passed');
    }
    var InventoryLatitudeTo = $.trim($("#Inventory_Latitude_To").val());
    if(!InventoryLatitudeTo.match(/^(\d+\.\d{1,2}|\d+)$/)&&InventoryLatitudeTo!=''){
		removeClassName('Inventory_Latitude_To','validation-passed');
		addClassName('Inventory_Latitude_To','validation-failed');
		bo = false;   	
    }else{
    	removeClassName('Inventory_Latitude_To','validation-failed');
		addClassName('Inventory_Latitude_To','validation-passed');
    }
    var InventoryLongitudeFrom = $.trim($("#Inventory_Longitude_From").val());
    if(!InventoryLongitudeFrom.match(/^(\d+\.\d{1,2}|\d+)$/)&&InventoryLongitudeFrom!=''){
		removeClassName('Inventory_Longitude_From','validation-passed');
		addClassName('Inventory_Longitude_From','validation-failed');
		bo = false;   	
    }else{
    	removeClassName('Inventory_Longitude_From','validation-failed');
		addClassName('Inventory_Longitude_From','validation-passed');
    }
    var InventoryLongitudeTo = $.trim($("#Inventory_Longitude_To").val());
    if(!InventoryLongitudeTo.match(/^(\d+\.\d{1,2}|\d+)$/)&&InventoryLongitudeTo!=''){
		removeClassName('Inventory_Longitude_To','validation-passed');
		addClassName('Inventory_Longitude_To','validation-failed');
		bo = false;   	
    }else{
    	removeClassName('Inventory_Longitude_To','validation-failed');
		addClassName('Inventory_Longitude_To','validation-passed');
    }
    var InventoryMRCFrom = $.trim($("#Inventory_MRC_From").val());
    if(!InventoryMRCFrom.match(/^(\d+\.\d{1,2}|\d+)$/)&&InventoryMRCFrom!=''){
		removeClassName('Inventory_MRC_From','validation-passed');
		addClassName('Inventory_MRC_From','validation-failed');
		bo = false;   	
    }else{
    	removeClassName('Inventory_MRC_From','validation-failed');
		addClassName('Inventory_MRC_From','validation-passed');
    }
    var InventoryMRCTo = $.trim($("#Inventory_MRC_To").val());
    if(!InventoryMRCTo.match(/^(\d+\.\d{1,2}|\d+)$/)&&InventoryMRCTo!=''){
		removeClassName('Inventory_MRC_To','validation-passed');
		addClassName('Inventory_MRC_To','validation-failed');
		bo = false;   	
    }else{
    	removeClassName('Inventory_MRC_To','validation-failed');
		addClassName('Inventory_MRC_To','validation-passed');
    }
    var InventoryNRCFrom = $.trim($("#Inventory_NRC_From").val());
    if(!InventoryNRCFrom.match(/^(\d+\.\d{1,2}|\d+)$/)&&InventoryNRCFrom!=''){
		removeClassName('Inventory_NRC_From','validation-passed');
		addClassName('Inventory_NRC_From','validation-failed');
		bo = false;   	
    }else{
    	removeClassName('Inventory_NRC_From','validation-failed');
		addClassName('Inventory_NRC_From','validation-passed');
    }
    var InventoryNRCTo = $.trim($("#Inventory_NRC_To").val());
    if(!InventoryNRCTo.match(/^(\d+\.\d{1,2}|\d+)$/)&&InventoryNRCTo!=''){
		removeClassName('Inventory_NRC_To','validation-passed');
		addClassName('Inventory_NRC_To','validation-failed');
		bo = false;   	
    }else{
    	removeClassName('Inventory_NRC_To','validation-failed');
		addClassName('Inventory_NRC_To','validation-passed');
    }
    var InventoryConstructionCostFrom = $.trim($("#Inventory_Construction_Cost_From").val());
    if(!InventoryConstructionCostFrom.match(/^(\d+\.\d{1,2}|\d+)$/)&&InventoryConstructionCostFrom!=''){
		removeClassName('Inventory_Construction_Cost_From','validation-passed');
		addClassName('Inventory_Construction_Cost_From','validation-failed');
		bo = false;   	
    }else{
    	removeClassName('Inventory_Construction_Cost_From','validation-failed');
		addClassName('Inventory_Construction_Cost_From','validation-passed');
    }
    var InventoryConstructionCostTo = $.trim($("#Inventory_Construction_Cost_To").val());
    if(!InventoryConstructionCostTo.match(/^(\d+\.\d{1,2}|\d+)$/)&&InventoryConstructionCostTo!=''){
		removeClassName('Inventory_Construction_Cost_To','validation-passed');
		addClassName('Inventory_Construction_Cost_To','validation-failed');
		bo = false;   	
    }else{
    	removeClassName('Inventory_Construction_Cost_To','validation-failed');
		addClassName('Inventory_Construction_Cost_To','validation-passed');
    }

    
//	var timePast = $('#searchCircuitVO_invoiceDateWiPastNumOfDays').val();
//	var timePastTwo = $('#searchCircuitVO_startDateWiPastNumOfDays').val();
//	var timePastThree = $('#searchCircuitVO_endDateWiPastNumOfDays').val();
//	var timeNextFour = $('#searchCircuitVO_endDateWiNextNumOfDays').val();
	var nodes = YAHOO.util.Selector.query('input[cssClass=validate-date-ca]');
	
	for(var d = 0; d<nodes.length; d++){
		removeClassName(nodes[d].id,'validation-failed');
		addClassName(nodes[d].id,'validation-passed');
		if(!isValidDate(nodes[d].value)) {
			removeClassName(nodes[d].id,'validation-passed');
			addClassName(nodes[d].id,'validation-failed');
			bo = false;
		}
	}
//	for(var d = 0; d<nodes.length; d=d+2){
//		if(d+1 < nodes.length){
//			if((nodes[d].value!="") && (nodes[d+1].value!="")){
//				if(!correct(nodes[d].value,nodes[d+1].value)){
//					removeClassName(nodes[d].id,'validation-passed');
//					addClassName(nodes[d].id,'validation-failed');
//					removeClassName(nodes[d+1].id,'validation-passed');
//					addClassName(nodes[d+1].id,'validation-failed');
//					bo = false;
//				}
//			}
//		}
//	}
//	
//	if(timePast < 0 || timePast > 10000 || timePast.match(/[^0-9]/)) {
//		removeClassName('searchCircuitVO_invoiceDateWiPastNumOfDays','validation-passed');
//		addClassName('searchCircuitVO_invoiceDateWiPastNumOfDays','validation-failed');
//		bo = false;
//	}
//	if(timePastTwo < 0 || timePastTwo > 10000 || timePastTwo.match(/[^0-9]/)) {
//		removeClassName('searchCircuitVO_startDateWiPastNumOfDays','validation-passed');
//		addClassName('searchCircuitVO_startDateWiPastNumOfDays','validation-failed');
//		bo = false;
//	}
//	if(timePastThree < 0 || timePastThree > 10000 || timePastThree.match(/[^0-9]/)) {
//		removeClassName('searchCircuitVO_endDateWiPastNumOfDays','validation-passed');
//		addClassName('searchCircuitVO_endDateWiPastNumOfDays','validation-failed');
//		bo = false;
//	}
//	if(timeNextFour < 0 || timeNextFour > 10000 || timeNextFour.match(/[^0-9]/)) {
//		removeClassName('searchCircuitVO_endDateWiNextNumOfDays','validation-passed');
//		addClassName('searchCircuitVO_endDateWiNextNumOfDays','validation-failed');
//		bo = false;
//	}
//	
	return bo;
}
function updateRadioButton(){

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

function downloadVendorInventoryCsv(){
	var titles = vendorInventoryPage.getTitlesParam("titles");
	var uri =  "doDownloadVendorInventoryCsv.action?"+ titles +"&"+composePostDataFromUi();
	windowOpen(uri);
}

function downloadVendorInventoryPDF(){
	var uri =  "doDownloadVendorInventoryPDF.action?"+composePostDataFromUi();
	windowOpen(uri);
}

function changRadio(type){
//	if (type == 2) {
//		form0.selectInvoiceDateRadio[0].checked = true;
//		$('#searchCircuitVO_invoiceDateWiPastNumOfDays').val("");
//	}
//	if (type == 3) {
//		form0.selectInvoiceDateRadio[1].checked = true;
//		$('#searchCircuitVO_invoiceDateStartsOn').val("");
//		$('#searchCircuitVO_invoiceDateEndsOn').val("");
//	}
//	if (type == 4) {
//		form0.selectStartDateRadio[0].checked = true;
//		$('#searchCircuitVO_startDateWiPastNumOfDays').val("");
//	}
//	if (type == 5) {
//		form0.selectStartDateRadio[1].checked = true;
//		$('#searchCircuitVO_startDateStartsOn').val("");
//		$('#searchCircuitVO_startDateEndsOn').val("");
//	}
//	if (type == 6) {
//		form0.selectEndDateRadio[0].checked = true;
//		$('#searchCircuitVO_endDateWiPastNumOfDays').val("");
//		$('#searchCircuitVO_endDateWiNextNumOfDays').val("");
//	}
//	if (type == 7) {
//		form0.selectEndDateRadio[1].checked = true;
//		$('#searchCircuitVO_endDateStartsOn').val("");
//		$('#searchCircuitVO_endDateEndsOn').val("");
//		$('#searchCircuitVO_endDateWiNextNumOfDays').val("");
//	}
//	if (type == 8) {
//		form0.selectEndDateRadio[2].checked = true;
//		$('#searchCircuitVO_endDateStartsOn').val("");
//		$('#searchCircuitVO_endDateEndsOn').val("");
//		$('#searchCircuitVO_endDateWiPastNumOfDays').val("");
//	}
}
var Province_Array={"results":[{"id":"all","name":""}],"total":1};
var City_Array={"results":[{"id":"all","name":""}],"total":1};
function getProvince(arr){
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
				updateDropdownList(data);
				delete data;
			},
			failure: showError
		};

		var pdata = "countryId="+ arr;
		if(arr!="all"){
		YAHOO.util.Connect.asyncRequest('POST' , "getListByProvinceId.action" , callback , pdata); 
		}else{
			$("#Inventory_Province").empty();
			$("#Inventory_City").empty();
			changeProvince({"results":[{"id":"all","name":""}],"total":1});
			changeCity({"results":[{"id":"all","name":""}],"total":1});
		}
}
function getCity(arr){
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
				updateCityList(data);
				delete data;
			},
			failure: showError
		};

		var pdata = "provinceId="+ arr;
		if(arr!="all"){
		YAHOO.util.Connect.asyncRequest('POST' , "getListByCityId.action" , callback , pdata); 
		}else{
			$("#Inventory_City").empty();
			changeCity({"results":[{"id":"all","name":""}],"total":1});
		}
}
function updateCityList(data){

	var a={"id":"all","name":""},b=[];
	 b.push(a);
    for(var i=0;i<data.length;i++){
        a={
        		"id":data[i].id,
        		"name":data[i].no
        };
        b.push(a);
    }
    City_Array = {"City":{"results":b,"total":data.length}};
    $("#Inventory_City").empty();
		if (City_Array["City"]) {			
			changeCity(City_Array["City"]);
		} else {
			changeCity({"results":[{"id":"all","name":""}],"total":1});
		}
		form0_searchVendorInventory_city.setValue("all");
}
function updateDropdownList(data){

	var a={"id":"all","name":""},b=[];
	 b.push(a);
    for(var i=0;i<data.length;i++){
        a={
        		"id":data[i].id,
        		"name":data[i].no
        };
        b.push(a);
    }
     Province_Array = {"Province":{"results":b,"total":data.length}};
     $("#Inventory_Province").empty();
		if (Province_Array["Province"]) {		
			changeProvince(Province_Array["Province"]);
		} else {
			changeProvince({"results":[{"id":"all","name":""}],"total":1});
		}
		$("#Inventory_City").empty();
		changeCity({"results":[{"id":"all","name":""}],"total":1});
		form0_searchVendorInventory_province.setValue("all");
}

function setJSelectQuicklink(k,v,d){

	if(k == "searchVendorInventoryVO.vendorId"){
		form0_searchVendorInventory_vendorId.setValue(v);
		return true;
	}
	if(k == "searchVendorInventoryVO.termId"){
		$("#Term_in_Month").val(v);
		return true;
	}
	if(k == "searchVendorInventoryVO.productId"){
		form0_searchVendorInventory_productId.setValue(v);
		return true;
	}
	if(k == "searchVendorInventoryVO.accessId"){
		form0_searchVendorInventory_accessId.setValue(v);
		return true;
	}
	if(k == "searchVendorInventoryVO.bandwidthId"){
		form0_searchVendorInventory_bandwidthId.setValue(v);
		return true;
	}
	if(k == "searchVendorInventoryVO.qosId"){
		form0_searchVendorInventory_qosId.setValue(v);
		return true;
	}
	if(k == "searchVendorInventoryVO.country"){
		form0_searchVendorInventory_country.setName(v);

		return true;
	}
	if(k == "searchVendorInventoryVO.province"){
		form0_searchVendorInventory_province.setName(v);
		return true;
	}
	if(k == "searchVendorInventoryVO.city"){
		form0_searchVendorInventory_city.setName(v);
		return true;
	}

	return false;
}
function clearJSelectQuicklink(){
	$("#Term_in_Month").val("all");
	form0_searchVendorInventory_country.setValue("all");
	form0_searchVendorInventory_province.setValue("all");
	form0_searchVendorInventory_city.setValue("all");
	form0_searchVendorInventory_vendorId.setValue("all");
	form0_searchVendorInventory_productId.setValue("all");
	form0_searchVendorInventory_accessId.setValue("all");
	form0_searchVendorInventory_bandwidthId.setValue("all");
	form0_searchVendorInventory_qosId.setValue("all");
}