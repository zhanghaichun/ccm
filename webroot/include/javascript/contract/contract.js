var actionUri = {
	getContractViewListPageNoUri: 'getContractViewListPageNo.action',
	searchContractListUri: 'searchContractList.action',
	downloadContractToExcelUri: 'downloadContractToExcel.action'
};

$(function() {
	// debugger;
	getContractList();
	$('form').bind('keydown',contractEnterToStartSearch);

})
function contractEnterToStartSearch() {
	
	// key code => 13 的按键是Enter键， 系统中的search方法应该
	// 定义为startSearch.
	if( window.event.keyCode == 13) {
		
		if ( currentFocusTab == CONTRACT_TAB &&  window.searchContract) {
			searchContract();

		} else if(currentFocusTab == PRICELIST_TAB && window.searchPricelist) {
			searchPricelist();
		}
		
	}
}


/**
 * 显示 simple canlendar 控件。
 * @param  {[type]} textboxId      calendar textbox 的 id 属性值。
 */
function showCalendarController(textboxId) {

	g_Calendar.show(event, textboxId, false, 'yyyy/mm/dd', new Date(1990,1,1));
	return false;
}

/**
 * 显示Termination penalty 弹窗。
 * @return {[type]} [description]
 */
function showTerminationPenaltyPopup() {

	// 将之前添加的内容清除
	cancelTerminationPenalty();

	$('.termination-penalty-popup').show();

	hideTerminationPenaltyPopup();

}

/**
 * 隐藏Termination penalty 弹窗。
 * @return {[type]} [description]
 */
function hideTerminationPenaltyPopup() {

	$('.close-penalty-popup-icon').click(function() {
		$('.termination-penalty-popup').hide();
	});
}

/**
 * 将contract 页面中上半部分表单中的信息重置。
 * @return {[type]} [description]
 */
function resetContractFormElementValue() {

	// 清空dropdown List搜索条件。
	form0_searchContractVO_carrierEntityName.setValue('all'); // carrier entity name
	form0_searchContractVO_carrierType.setValue('all'); // carrier type 
	form0_searchContractVO_agreementType.setValue('all'); // agreement type 
	form0_searchContractVO_productsServices.setValue('all'); // products/services 
	form0_searchContractVO_term.setValue('all'); // term 

	// 清除form表单中所有的文本输入框或者是select复选框中的值。
	cleanFormElementValue();
}

function resetRadioButton() {}

/**
 * 验证表单中的日期类型的input框输入的内容是否是
 * 合法的。
 * @return {[type]} [description]
 */
function validateDateFields(){

	//日期类型的文本框都包含了一个CSS类， validate-date-ca
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
	
	
	
	return bo;
}

/**
 * 获取 contract 列表。
 */
function getContractList() {

	ContractViewListPage = new SANINCO.Page('contract-data-table',"ContractViewListPage",{
		sortingField : "cf.id",
	    sortingDirection : "asc",
	    vo : "searchContractVO",
		autoToTop : true,
		recordText: 'Contract Search Results:',
	    totalPageUri : actionUri.getContractViewListPageNoUri,
	    dataUri : actionUri.searchContractListUri ,
		paginationDiv : "contract-dataTable-page",
		recPerArray : [10,20,30,50,100,500,1000],
		cols : [{title: "Internal Id #", dataField: "internalId", sort : "cf.internal_id", filtration : {name:"cf.internal_id", alias:"Internal Id #"}    },
				{title: "Supplier Master Contract #", dataField: "supplierContractNumber", sort : "cf.supplier_contract_number", filtration : {name:"cf.supplier_contract_number", alias:"Supplier Master Contract #"} },
				{title: "Name of Agreement", dataField: "nameOfAgreement", sort : "cf.name_of_agreement", filtration : {name:"cf.name_of_agreement", alias:"Name of Agreement"} },
				{title: "Contract Amendment", dataField: "contractAmendment", sort : "cf.contract_amendment", filtration : {name:"cf.contract_amendment", alias:"Contract Amendment"} },
				{title: "Amendment Date", dataField: "amendmentDate", sort : "cf.amendment_date", filtration : {name:"cf.amendment_date", alias:"Amendment Date"} },
				{title: "Carrier Entity Name", dataField: "carrierEntityName", sort : "cf.carrier_entity_name", filtration : {name:"cf.carrier_entity_name", alias:"Carrier Entity Name"} },
				{title: "Carrier Address", dataField: "carrierAddress", sort : "cf.carrier_address", filtration : {name:"cf.carrier_address", alias:"Carrier Address"} },
				{title: "Rogers Legal Entity Name", dataField: "rogersLegalName", sort : "cf.rogers_legal_name", filtration : {name:"cf.rogers_legal_name", alias:"Rogers Legal Entity Name"} },
				{title: "Carrier Type", dataField: "carrierType", sort : "cf.carrier_type", filtration : {name:"cf.carrier_type", alias:"Carrier Type"} },
				{title: "Agreement Type", dataField: "agreementType", sort : "cf.agreement_type", filtration : {name:"cf.agreement_type", alias:"Agreement Type"} },
				{title: "Products/Services", dataField: "productsServices", sort : "cf.products_services", filtration : {name:"cf.products_services", alias:"Products/Services"} },
				{title: "Schedules", dataField: "schedules", sort : "cf.schedules", filtration : {name:"cf.schedules", alias:"Schedules"} },
				{title: "Term - Commence", dataField: "termCommence", sort : "cf.term_commence", filtration : {name:"cf.term_commence", alias:"Term - Commence"} },
				{title: "Term - Expiry", dataField: "termExpiry", sort : "cf.term_expiry", filtration : {name:"cf.term_expiry", alias:"Term - Expiry"} },
				{title: "Term", dataField: "term", sort : "cf.term_combined", filtration : {name:"cf.term_combined", alias:"Term"} },
				{title: "Renewal Options", dataField: "renewalOptions", sort : "cf.renewal_options", filtration : {name:"cf.renewal_options", alias:"Renewal Options"} },
				{title: "Renewal Notice Period (days)", dataField: "renewalNoticePeriod", sort : "cf.renewal_notice_period", filtration : {name:"cf.renewal_notice_period", alias:"Renewal Notice Period (days)"} },
				{title: "Termination Notice Period (days)", dataField: "terminationNoticePeriod", sort : "cf.termination_notice_period", filtration : {name:"cf.termination_notice_period", alias:"Termination Notice Period (days)"} },
				{title: "Dispute Policy (days)", dataField: "disputePolicy", sort : "cf.dispute_policy", filtration : {name:"cf.dispute_policy", alias:"Dispute Policy (days)"} },
				{title: "Back Billing Policy (days)", dataField: "backBillingPolicy", sort : "cf.back_billing_policy", filtration : {name:"cf.back_billing_policy", alias:"Back Billing Policy (days)"} },
				{title: "Volume/Spend Commitment", dataField: "volumeSpendCommitment", sort : "cf.volume_spend_commitment", filtration : {name:"cf.volume_spend_commitment", alias:"Volume/Spend Commitment"} },
				{title: "Early Cancellation Penalty", dataField: "earlyCancellationPenalty", sort : "cf.early_cancellation_penalty", filtration : {name:"cf.early_cancellation_penalty", alias:"Early Cancellation Penalty"} },
				{title: "Contract Signed Date", dataField: "contractSignedDate", sort : "cf.contract_signed_date", filtration : {name:"cf.contract_signed_date", alias:"Contract Signed Date"} },
				{title: "SLA (Latency, MTTR, Service Availability/Network Availability)", dataField: "sla", sort : "cf.sla", filtration : {name:"cf.sla", alias:"SLA (Latency, MTTR, Service Availability/Network Availability)"} },
				{title: "Notes", dataField: "notes", sort : "cf.notes", filtration : {name:"cf.notes", alias:"Notes"} },
				{title: "Payment Terms (days)", dataField: "paymentTerms", sort : "cf.payment_terms", filtration : {name:"cf.payment_terms", alias:"Payment Terms (days)"} },
				{title: "Currency", dataField: "currency", sort : "cf.currency", filtration : {name:"cf.currency", alias:"Currency"} },
				{title: "GST/HST/QST Numbers", dataField: "taxNumber", sort : "cf.tax_number", filtration : {name:"cf.tax_number", alias:"GST/HST/QST Numbers"} },
				{title: "Growth Incentive Clauses (tied to Spend)", dataField: "growthIncentiveClauses", sort : "cf.growth_incentive_clauses", filtration : {name:"cf.growth_incentive_clauses", alias:"Growth Incentive Clauses (tied to Spend)"} },
				{title: "Credit %/Discount", dataField: "creditDiscount", sort : "cf.credit_discount", filtration : {name:"cf.credit_discount", alias:"Credit %/Discount"} },
				{title: "Order Placement Details (Business Practices)", dataField: "orderPlacementDetails", sort : "cf.order_placement_details", filtration : {name:"cf.order_placement_details", alias:"Order Placement Details (Business Practices)"} },
				{title: "Price Increase Notice", dataField: "priceIncreaseNotice", sort : "cf.price_increase_notice", filtration : {name:"cf.price_increase_notice", alias:"Price Increase Notice"} },
				{title: "NDA (pre-requisite to MSA)", dataField: "nda", sort : "cf.nda", filtration : {name:"cf.nda", alias:"NDA (pre-requisite to MSA)"} }
			] 
	});

	// 列表中的操作按钮和列表的显示控制逻辑
	ContractViewListPage.addTotalSuccessEvent(function(data){
		if(data.totalResultNo < 0 || data.error){

			$('#downloadContractRecordsToExcel').css('display', 'none');
			$('#contract-wrapper').css('display', 'none');
		} else {

			if(data.totalResultNo == 0 ){
				$('#downloadContractRecordsToExcel').css('display', 'none');
			}else{
				$('#downloadContractRecordsToExcel').css('display', 'block');
			}

			$('#contract-wrapper').css('display', 'block');
		}
	});

	// 给Contract 列表中的字段添加过滤条件。
	addContractFilter(ContractViewListPage);
	
}

/**
 * Contract 的过滤条件的添加动作。
 */
function addContractFilter(startPage) {
	// 声明Filter 条件。
	ContractViewListFilter = new SANINCO.Filter();
	// 添加Filter条件的编辑事件。
	ContractViewListFilter.addEditeEvent( function(){startPage.start();} );

	ContractViewListFilter.add("cf.internal_id", "String"); // Internal Id #
	ContractViewListFilter.add("cf.supplier_contract_number", "String"); // Supplier Master Contract #
	ContractViewListFilter.add("cf.name_of_agreement", "String"); // Name of Agreement
	ContractViewListFilter.add("cf.contract_amendment", "String"); // Contract Amendment
	ContractViewListFilter.add("cf.amendment_date", "String"); // Amendment Date
	ContractViewListFilter.add("cf.carrier_entity_name", "String"); // Carrier Entity Name
	ContractViewListFilter.add("cf.carrier_address", "String"); // Carrier Address
	ContractViewListFilter.add("cf.rogers_legal_name", "String"); // Rogers Legal Entity Name
	ContractViewListFilter.add("cf.carrier_type", "String"); // Carrier Type
	ContractViewListFilter.add("cf.agreement_type", "String"); // Agreement Type
	ContractViewListFilter.add("cf.products_services", "String"); // Products/Services
	ContractViewListFilter.add("cf.schedules", "String"); // Schedules
	ContractViewListFilter.add("cf.term_commence", "String"); // Term - Commence
	ContractViewListFilter.add("cf.term_expiry", "String"); // Term - Expiry
	ContractViewListFilter.add("cf.term_combined", "String"); // Term
	ContractViewListFilter.add("cf.renewal_options", "String"); // Renewal Options
	ContractViewListFilter.add("cf.renewal_notice_period", "Integer"); // Renewal Notice Period (days)
	ContractViewListFilter.add("cf.termination_notice_period", "Integer"); // Termination Notice Period (days)
	ContractViewListFilter.add("cf.dispute_policy", "Integer"); // Dispute Policy (days)
	ContractViewListFilter.add("cf.back_billing_policy", "Integer"); // Back Billing Policy (days)
	ContractViewListFilter.add("cf.volume_spend_commitment", "Double"); // Volume/Spend Commitment
	ContractViewListFilter.add("cf.early_cancellation_penalty", "String"); // Early Cancellation Penalty
	ContractViewListFilter.add("cf.contract_signed_date", "String"); // Contract Signed Date
	ContractViewListFilter.add("cf.sla", "String"); // SLA (Latency, MTTR, Service Availability/Network Availability)
	ContractViewListFilter.add("cf.notes", "String"); // Notes 
	ContractViewListFilter.add("cf.payment_terms", "Integer"); // Payment Terms (days)
	ContractViewListFilter.add("cf.currency", "String"); // Currency 
	ContractViewListFilter.add("cf.tax_number", "String"); // GST/HST/QST Numbers
	ContractViewListFilter.add("cf.growth_incentive_clauses", "String"); // Growth Incentive Clauses (tied to Spend)
	ContractViewListFilter.add("cf.credit_discount", "String"); // Credit %/Discount
	ContractViewListFilter.add("cf.order_placement_details", "String"); // Order Placement Details (Business Practices)
	ContractViewListFilter.add("cf.price_increase_notice", "String"); // Price Increase Notice
	ContractViewListFilter.add("cf.nda", "String"); // NDA (pre-requisite to MSA)

	ContractViewListPage.addCompleteEvent(function(o){
		$(window).scrollTop(0);
	});

	// 将Filter 条件应用到列表中。
	startPage.setFilter(ContractViewListFilter);
}

/**
 * 查询并显示列表
 * @return {[type]} [description]
 */
function searchContract() {

	// 验证表单中的内容是否有效。
	if( !validateFields() || !validateDateFields() ) return;

	// 重置过滤条件。
	ContractViewListFilter.clearAll();

	ContractViewListPage.myParam = composePostDataFromUi();
	
	// Contract 列表的搜索动作。（开启）
	ContractViewListPage.start();

}

/**
 * 组合查询条件的功能。
 * @return {[type]} [description]
 */
function composePostDataFromUi() {

	var postData = '';

	// Dropdown list filters.
	var carrierEntityName = form0_searchContractVO_carrierEntityName.getValue(); // Carrier Entity Name
	var carrierType = form0_searchContractVO_carrierType.getValue(); // Carrier Type
	var agreementType = form0_searchContractVO_agreementType.getValue(); // Agreement Type
	var productsServices = form0_searchContractVO_productsServices.getValue(); // Products/Services
	var term = form0_searchContractVO_term.getValue();

	// Text field filters.
	var internalIdNumber = encodeURIComponent( $('.internal-id-number').val().trim() );
	var supplierMasterContractNumber = encodeURIComponent( $('.supplier-master-contract-number').val().trim() );
	var nameOfAgreement = encodeURIComponent( $('.name-of-agreement').val().trim() );
	var carrierAddress = encodeURIComponent( $('.carrier-address').val().trim() );
	var rogersLegalName = encodeURIComponent( $('.rogers_legal_entity_name').val().trim() );
	var schedules = encodeURIComponent( $('.schedules').val().trim() );
	

	// Date field filters
	var amendmentDate = $('#searchContractVO_amendmentDate').val();
	var contractSignedDate = $('#searchContractVO_contractSignedDate').val();
	var termExpiry = $('#searchContractVO_termExpiry').val();


	var contractFilterCause = ContractViewListFilter.getCause(); // Contract Filter

	if ( !isEmptyStringValue(internalIdNumber) ) { // Internal Id Number
		postData += 'searchContractVO.internalId=' + internalIdNumber + '&';
	}

	if ( !isEmptyStringValue(supplierMasterContractNumber) ) { // Suppplier Master Contract Number
		postData += 'searchContractVO.supplierContractNumber=' + supplierMasterContractNumber + '&';
	}

	if ( !isEmptyStringValue(nameOfAgreement) ) { // Name of Agreement
		postData += 'searchContractVO.nameOfAgreement=' + nameOfAgreement + '&';
	}

	if ( !isEmptyStringValue(amendmentDate) ) { // Amendment Date
		postData += 'searchContractVO.amendmentDate=' + amendmentDate + '&';
	}

	if ( !isDropdownListAllValue(carrierEntityName) ) { // Carrier Entity Name
		postData += 'searchContractVO.carrierEntityName=' + carrierEntityName + '&';
	}

	if ( !isEmptyStringValue(carrierAddress) ) { // Carrier Address
		postData += 'searchContractVO.carrierAddress=' + carrierAddress + '&';
	}

	if ( !isEmptyStringValue(rogersLegalName) ) { // Rogers Legal Entity Name.
		postData += 'searchContractVO.rogersLegalEntityName=' + rogersLegalName + '&';
	}

	if ( !isDropdownListAllValue(carrierType) ) { // Carrier Type.
		postData += 'searchContractVO.carrierType=' + carrierType + '&';
	}

	if ( !isDropdownListAllValue(agreementType) ) { // Agreement Type.
		postData += 'searchContractVO.agreementType=' + agreementType + '&';
	}

	if ( !isDropdownListAllValue(productsServices) ) { // Products/Services.
		postData += 'searchContractVO.productsOrServices=' + productsServices + '&';
	}

	if ( !isEmptyStringValue(schedules) ) { // schedules.
		postData += 'searchContractVO.schedules=' + schedules + '&';
	}

	if ( !isDropdownListAllValue(term) ) { // TERM.
		postData += 'searchContractVO.termCombined=' + term + '&';
	}

	if ( !isEmptyStringValue(contractSignedDate) ) { // Contract Signed Date.
		postData += 'searchContractVO.contractSignedDate=' + contractSignedDate + '&';
	}

	if ( !isEmptyStringValue(termExpiry) ) { // Term Expiry.
		postData += 'searchContractVO.termExpiry=' + termExpiry + '&';
	}

	if ( !isEmptyStringValue(contractFilterCause) ) { // Contract Filter
		postData += 'searchContractVO.filter=' + contractFilterCause + '&';
	}

	return postData;
}

/**
 * 验证搜索表单中的各个字段的输入内容
 * 这个方法用来验证表单中的 input , textarea, select
 * 是否符合要求
 */
function validateFields() {
	var formatValid = FIC_checkForm("form0");
	return formatValid;
}

/**
 * 验证表单中的日期类型的input框输入的内容是否是
 * 合法的。
 * @return {[type]} [description]
 */
function validateDateFields(){

	//日期类型的文本框都包含了一个CSS类， validate-date-ca
	var nodes = YAHOO.util.Selector.query('input[cssClass=validate-date-ca]');
	var bo = true;
	
	for(var d = 0; d<nodes.length; d++){
		if(!isValidDate(nodes[d].value)) {
			removeClassName(nodes[d].id,'validation-passed');
			addClassName(nodes[d].id,'validation-failed');
			bo = false;
		}
	}
	
	
	
	return bo;
}

/**
 * 下载Contract list
 */
function downloadContractRecordsToExcel() {

	// 使用substring 方法截取字符串是因为字符串的前面会多出一个
	// '&', 因为checkbox 复选框并不会下载到 Excel文件中
	// cols 属性中的 notDownload: 'Y', 就不会添加到titles中，
	// 这样就多出一个 '&',  因此需要这样的截取。
	var titles = ContractViewListPage.getTitlesParam("titles"); // 获取所有的列头。
	var listPageParams = "";
	var paramStr = ContractViewListPage.paramStr;

	// 去掉字符串结尾的 '&' 符号。
	listPageParams = paramStr.substring(0, paramStr.length - 1 );

	// 如果有 '%' 需要首先对其进行转义。
	windowOpen(actionUri.downloadContractToExcelUri + "?"+ titles.replace(/%/g, "%25").replace(/#/g, "%23") + "&" + listPageParams);
}

/**
 * 查询penalty信息。
 * @return {[type]} [description]
 */
function searchTerminationPenalty() {

	$('#terminationDate').removeClass('validation-failed').addClass('validation-passed');
	// 验证表单中的内容是否有效。
	if( !validateFields() || !validateDateFields() ) return;
	
	showTerminationPenaltyList();
	

	//console.log('...');
}

function showTerminationPenaltyList(){
	terminationPenaltyListPage = new SANINCO.Page("_penalty_list","terminationPenaltyListPage",{
	    vo : "searchContractVO",
		recordText : '',
		totalPageUri : "getTerminationPenaltyListNo.action",
	    dataUri :"searchTerminationPenaltyList.action" ,
	    paginationDiv : "_penalty_page_list",
		recPerArray : [10,20,30,40,50,70,90,100],

		
	    cols : [
	            //1 Contract Name
				{ title : "Contract Name",
				  dataField:"contractName",
//				  filtration : {
//		            name:"contract_name",
//		            alias:"Contract Name"
//	              },
				  sort : "contract_name"
				},
				//2	(Stripped) Circuit Number
				{ title : "(Stripped) Circuit Number",
					  dataField:"strippedCircuitNumber",
//					  filtration : {
//			            name:"stripped_circuit_number",
//			            alias:"(Stripped) Circuit Number"
//		              },
					  sort : "stripped_circuit_number"
				},
//				MI.Rate不维护了,所以前台显示去掉.
//				//3 Rate
//				{ title : "Rate",
//					  dataField:"rate",
////					  filtration : {
////			            name:"rate",
////			            alias:"Rate"
////		              },
//					  sort : "rate"
//				},
				//4 Term
				{ title : "Term",
					  dataField:"term",
//					  filtration : {
//			            name:"term",
//			            alias:"Term"
//		              },
					  sort : "term"
				},
				//5 Penalty
				{ title : "Penalty",
					  dataField:"penaltyAmount",
//					  filtration : {
//			            name:"penalty_amount",
//			            alias:"Penalty"
//		              },
					  sort : "penalty_amount"
				}
			]
	});

	terminationPenaltyListPage.voParam = {
		circuitNumber:$('.circuit-number')[0].value,
		terminationDate:$('#terminationDate')[0].value
	};
	
	terminationPenaltyListPage.addTotalSuccessEvent(function(data){
		
	});
	
	filter1 = new SANINCO.Filter();
	
//	filter1.add('mid', 'number');
//	filter1.add('vendor_name', 'string');
//	filter1.add('summary_vendor_name', 'string');
//	filter1.add('ban_name', 'string');
//	filter1.add('invoice_number', 'string')
//	filter1.add('line_of_business', 'string');
//	filter1.add('invoice_date', 'string');
	

//	filter1.addEditeEvent(function(){terminationPenaltyListPage.start();});
	terminationPenaltyListPage.setFilter(filter1);
	

	terminationPenaltyListPage.start();
}

/**
 * 撤销 penalty 表单中的内容。
 */
function cancelTerminationPenalty() {

	$('.circuit-number').val('');
	$('#terminationDate').val('');

	if ( $('#terminationDate').hasClass('validation-failed') ) {
		$('#terminationDate').removeClass('validation-failed');
	}
	
	$('#_penalty_list').empty();
	$('#_penalty_page_list').empty();
	
	
}

/**
 * 判断字符串值是否为空
 * @param  {[type]}  stringValue [description]
 * @return {Boolean}             [description]
 */
function isEmptyStringValue(stringValue) {
	return (stringValue === '') ? true : false;
}

/**
 * 判断下拉列表中的选值是否
 * 为'all',这个值不传入后台中。
 */
function isDropdownListAllValue(dropdownListValue) {
	return (dropdownListValue === 'all') ? true : false;
}
