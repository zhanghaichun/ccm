var QUOTE_INVENTORY_TYPE = "Quote Inventory";

var isCompareFilter = false; // Compare 搜索条件的标记。 

var saveQuicklinkUri = "saveAllQuoteInventory.action";

// 记录quote inventory 表中的选中项，
// 用于记录选中项，即使是在分页加载之后。
var selectedItemArray = [];

// Quote Inventory 列表分页的查询信息。
actionUri.getQuoteInventoryViewListPageNoUri = "getQuoteInventoryViewListPageNo.action";
//  Quote Inventory 列表的查询信息。
actionUri.searchQuoteInventoryListUri = "searchQuoteInventoryList.action";
// Quote Inventory 列表下载成Excel文件。
actionUri.downloadQuoteInventoryToExcelUri = "downloadQuoteInventoryToExcel.action";

// 将Quote Inventory 列表中的title下载为模板Excel文件。
actionUri.downloadQuoteInventoryTemplateToExcelUri = "downloadQuoteInventoryTemplateToExcel.action";

YAHOO.util.Event.onDOMReady(function () {
	getQuoteInventoryList(); // Quote list.
	YAHOO.ccm.container.contactVendorWindow = new YAHOO.widget.Dialog("quoteWindow", { 
		width: "460px",
	    fixedcenter: true,
	    visible: false,
	    constraintoviewport: true,
	    modal: true
	});
	YAHOO.ccm.container.contactVendorWindow.render();

	getContractList(); // Contract List.

	getTariffList(); // Tariff List.

	getPriceListList(); // [Price List] List.

});


/**
 * 获取 Quote Inventory 列表。
 */
function getQuoteInventoryList() {

	QuoteInventoryViewListPage = new SANINCO.Page('_dataTable',"QuoteInventoryViewListPage",{
		sortingField : "q.id",
	    sortingDirection : "asc",
	    vo : "searchQuoteVO",
		autoToTop : true,
		recordText: 'Quote Search Results:',
	    totalPageUri : actionUri.getQuoteInventoryViewListPageNoUri,
	    dataUri : actionUri.searchQuoteInventoryListUri ,
		paginationDiv : "_dataTable_page",
		recPerArray : [10,20,30,50,100,500,1000],
		cols : [{title: "", notDownload: 'Y', dataField: function(o){ return "<input type='checkbox' class='noBorderRadioButton quote-list-checkbox-item' data-id='"+ o.id +"' onclick='markItem(this)' style='cursor: pointer;' />"; } },
		{title: "Key", dataField: "id", sort : "q.id", filtration : {name:"q.id", alias:"Key"} },
		{title: "Record Id", dataField: "recordId", sort : "q.record_id", filtration : {name:"q.record_id", alias:"Record Id"} },
		{title: "Status", dataField: "status", sort : "q.status", filtration : {name:"q.status", alias:"Status"} },
		{title: "Date Recvd", dataField: "dateRecvd", sort : "q.date_recvd", filtration : {name:"q.date_recvd", alias:"Date Recvd"} },
		{title: "Date Issued", dataField: "dateIssued", sort : "q.date_issued", filtration : {name:"q.date_issued", alias:"Date Issued"} },
		{title: "No. of Days To Quote", dataField: "daysToQuote", sort : "q.days_to_quote", filtration : {name:"q.days_to_quote", alias:"No. of Days To Quote"} },
		{title: "Type", dataField: "type", sort : "q.type", filtration : {name:"q.type", alias:"Type"} },
		{title: "Zendesk Quote Id", dataField: "zendeskQuoteId", sort : "q.zendesk_quote_id", filtration : {name:"q.zendesk_quote_id", alias:"Zendesk Quote Id"} },
		{title: "Opportunity (# sites)", dataField: "opportunitySites", sort : "q.opportunity_sites", filtration : {name:"q.opportunity_sites", alias:"Opportunity (# sites)"} },
		{title: "NSA", dataField: "nsa", sort : "q.nsa", filtration : {name:"q.nsa", alias:"NSA"} },
		{title: "Other Requester", dataField: "otherRequester", sort : "q.other_requester", filtration : {name:"q.other_requester", alias:"Other Requester"} },
		{title: "Enterprise Customer", dataField: "enterpriseCustomer", sort : "q.enterprise_customer", filtration : {name:"q.enterprise_customer", alias:"Enterprise Customer"} },
		{title: "Customer Type", dataField: "customerType", sort : "q.customer_type", filtration : {name:"q.customer_type", alias:"Customer Type"} },
		{title: "Customer Segment", dataField: "customerSegment", sort : "q.customer_segment", filtration : {name:"q.customer_segment", alias:"Customer Segment"} },
		{title: "Product Group", dataField: "productGroup", sort : "q.product_group", filtration : {name:"q.product_group", alias:"Product Group"} },
		{title: "Product Sub Group", dataField: "productSubGroup", sort : "q.product_sub_group", filtration : {name:"q.product_sub_group", alias:"Product Sub Group"} },
		{title: "Product Requested", dataField: "productRequested", sort : "q.product_requested", filtration : {name:"q.product_requested", alias:"Product Requested"} },
		{title: "Product Internal", dataField: "productInternal", sort : "q.product_internal", filtration : {name:"q.product_internal", alias:"Product Internal"} },
		{title: "Site Per Product", dataField: "sitePerProduct", sort : "q.site_per_product", filtration : {name:"q.site_per_product", alias:"Site Per Product"} },
		{title: "Provider", dataField: "provider", sort : "q.provider", filtration : {name:"q.provider", alias:"Provider"} },
		{title: "Quantity", dataField: "quantity", sort : "q.quantity", filtration : {name:"q.quantity", alias:"Quantity"} },
		{title: "Product External", dataField: "productExternal", sort : "q.product_external", filtration : {name:"q.product_external", alias:"Product External"} },
		{title: "Access (MB)", dataField: "accessMB", sort : "q.access_mb", filtration : {name:"q.access_mb", alias:"Access (MB)"} },
		{title: "EVC1", dataField: "EVC1", sort : "q.evc_1", filtration : {name:"q.evc_1", alias:"EVC1"} },
		{title: "EVC1 Class of Service", dataField: "classOfService1", sort : "q.class_of_service_1", filtration : {name:"q.class_of_service_1", alias:"EVC1 Class of Service"} },
		{title: "EVC2", dataField: "EVC2", sort : "q.evc_2", filtration : {name:"q.evc_2", alias:"EVC2"} },
		{title: "EVC2 Class of Service", dataField: "classOfService2", sort : "q.class_of_service_2", filtration : {name:"q.class_of_service_2", alias:"EVC2 Class of Service"} },
		{title: "# of Port(s)", dataField: "numberOfPorts", sort : "q.no_of_port", filtration : {name:"q.no_of_port", alias:"# of Port(s)"} },
		{title: "Addn EVCs", dataField: "addnEVCs", sort : "q.addn_evcs", filtration : {name:"q.addn_evcs", alias:"Addn EVCs"} },
		{title: "3RD Party Date Requested", dataField: "partyDateRequested", sort : "q.party_date_requested", filtration : {name:"q.party_date_requested", alias:"3RD Party Date Requested"} },
		{title: "3RD Party Date Received", dataField: "partyDateReceived", sort : "q.party_date_received", filtration : {name:"q.party_date_received", alias:"3RD Party Date Received"} },
		{title: "No. of Days To Quote Provider", dataField: "quoteProviderDays", sort : "q.quote_provider_days", filtration : {name:"q.quote_provider_days", alias:"No. of Days To Quote Provider"} },
		{title: "On-Net/Near-Net", dataField: "onNearNet", sort : "q.on_near_net", filtration : {name:"q.on_near_net", alias:"On-Net/Near-Net"} },
		{title: "Currency", dataField: "currency", sort : "q.currency", filtration : {name:"q.currency", alias:"Currency"} },
		{title: "Access", dataField: "access", sort : "q.access", filtration : {name:"q.access", alias:"Access"} },
		{title: "EVC1 Price", dataField: "evcNumber1", sort : "q.evc_number_1", filtration : {name:"q.evc_number_1", alias:"EVC1 Price"} },
		{title: "EVC2 Price", dataField: "evcNumber2", sort : "q.evc_number_2", filtration : {name:"q.evc_number_2", alias:"EVC2 Price"} },
		{title: "EVC3 Price", dataField: "evcNumber3", sort : "q.evc_number_3", filtration : {name:"q.evc_number_3", alias:"EVC3 Price"} },
		{title: "Port Charge", dataField: "portCharge", sort : "q.port_charge", filtration : {name:"q.port_charge", alias:"Port Charge"} },
		{title: "MRC 1Year", dataField: "mrcYear1", sort : "q.mrc_year_1", filtration : {name:"q.mrc_year_1", alias:"MRC 1Year"} },
		{title: "MRC 2Year", dataField: "mrcYear2", sort : "q.mrc_year_2", filtration : {name:"q.mrc_year_2", alias:"MRC 2Year"} },
		{title: "MRC 3Year", dataField: "mrcYear3", sort : "q.mrc_year_3", filtration : {name:"q.mrc_year_3", alias:"MRC 3Year"} },
		{title: "MRC 5Year", dataField: "mrcYear5", sort : "q.mrc_year_5", filtration : {name:"q.mrc_year_5", alias:"MRC 5Year"} },
		{title: "MRC 7Year", dataField: "mrcYear7", sort : "q.mrc_year_7", filtration : {name:"q.mrc_year_7", alias:"MRC 7Year"} },
		{title: "MRC 10Year", dataField: "mrcYear10", sort : "q.mrc_year_10", filtration : {name:"q.mrc_year_10", alias:"MRC 10Year"} },
		{title: "MRC 15Year", dataField: "mrcYear15", sort : "q.mrc_year_15", filtration : {name:"q.mrc_year_15", alias:"MRC 15Year"} },
		{title: "NRC", dataField: "nrc", sort : "q.nrc", filtration : {name:"q.nrc", alias:"NRC"} },
		{title: "Construction Costs", dataField: "constructionCosts", sort : "q.construction_costs", filtration : {name:"q.construction_costs", alias:"Construction Costs"} },
		{title: "A-Suite/Unit", dataField: "aSuiteOrUnit", sort : "q.a_unit", filtration : {name:"q.a_unit", alias:"A-Suite/Unit"} },
		{title: "A-Street Number", dataField: "aStreetNumber", sort : "q.a_street_number", filtration : {name:"q.a_street_number", alias:"A-Street Number"} },
		{title: "A-Street Name", dataField: "aStreetName", sort : "q.a_street_name", filtration : {name:"q.a_street_name", alias:"A-Street Name"} },
		{title: "A-City", dataField: "aCity", sort : "q.a_city", filtration : {name:"q.a_city", alias:"A-City"} },
		{title: "A-Prov", dataField: "aProvince", sort : "q.a_province", filtration : {name:"q.a_province", alias:"A-Prov"} },
		{title: "A-Postal Code", dataField: "aPostalCode", sort : "q.a_postal_code", filtration : {name:"q.a_postal_code", alias:"A-Postal Code"} },
		{title: "A-Country", dataField: "aCountry", sort : "q.a_country", filtration : {name:"q.a_country", alias:"A-Country"} },
		{title: "Z-Suite/Unit", dataField: "ZSuiteOrUnit", sort : "q.z_unit", filtration : {name:"q.z_unit", alias:"Z-Suite/Unit"} },
		{title: "Z-Street Number", dataField: "zStreetNumber", sort : "q.z_street_number", filtration : {name:"q.z_street_number", alias:"Z-Street Number"} },
		{title: "Z-Street Name", dataField: "zStreetName", sort : "q.z_street_name", filtration : {name:"q.z_street_name", alias:"Z-Street Name"} },
		{title: "Z-City", dataField: "zCity", sort : "q.z_city", filtration : {name:"q.z_city", alias:"Z-City"} },
		{title: "Z-Prov", dataField: "zProvince", sort : "q.z_province", filtration : {name:"q.z_province", alias:"Z-Prov"} },
		{title: "Z-Postal Code", dataField: "zPostalCode", sort : "q.z_postal_code", filtration : {name:"q.z_postal_code", alias:"Z-Postal Code"} },
		{title: "Z-Country", dataField: "zCountry", sort : "q.z_country", filtration : {name:"q.z_country", alias:"Z-Country"} },
		{title: "Notes", dataField: "notes", sort : "q.notes", filtration : {name:"q.notes", alias:"Notes"} },
		{title: "Presale\\FOXX # - Carrier Quote #", dataField: "carrierQuote", sort : "q.carrier_quote", filtration : {name:"q.carrier_quote", alias:"Presale\\FOXX # - Carrier Quote #"} },
		{title: "CBS - MRC (Original)", dataField: "cbsMrcOriginal", sort : "q.cbs_mrc_original", filtration : {name:"q.cbs_mrc_original", alias:"CBS - MRC (Original)"} },
		{title: "CBS - NRC + Construction (Original)", dataField: "cbsNrcConstruction", sort : "q.cbs_nrc_construction_original", filtration : {name:"q.cbs_nrc_construction_original", alias:"CBS - NRC + Construction (Original)"} },
		{title: "Quote Desk Rep", dataField: "quoteDeskRep", sort : "q.quote_desk_rep", filtration : {name:"q.quote_desk_rep", alias:"Quote Desk Rep"} },
		{title: "Firm\\Won Bid", dataField: "firmWonBid", sort : "q.firm_won_bid", filtration : {name:"q.firm_won_bid", alias:"Firm\\Won Bid"} },
		{title: "Cost Savings", dataField: "costSavings", sort : "q.cost_savings", filtration : {name:"q.cost_savings", alias:"Cost Savings"} }] 
	});

	QuoteInventoryViewListPage.addBeforeSearch(function(m,t){
		if(m == "start") selectedItemArray = [];
		delete m;
		delete t;
	});
	
	QuoteInventoryViewListPage.addCompleteEvent(function(o){
		markSelectedListItem();
		delete o;
	});

	// 列表中的操作按钮和列表的显示控制逻辑
	QuoteInventoryViewListPage.addTotalSuccessEvent(function(data){
		if(data.totalResultNo < 0 || data.error){

			$('#downloadToExcel').css('display', 'none');
			$('#_gridDiv').css('display', 'none');
		} else {

			if(data.totalResultNo == 0 ){
				$('#downloadToExcel').css('display', 'none');
			}else{
				$('#downloadToExcel').css('display', 'block');
			}

			$('#_gridDiv').css('display', 'block');
		}
	});

	// 给quote inventory 列表中的字段添加过滤条件。
	addQuoteInventoryFilter(QuoteInventoryViewListPage);
	
}

/**
 * 根据搜索的条件开始查询列表的显示。
 * @return {[type]} [description]
 */
function startSearch() {

	if( !validateFields() || !validateDateFields() ) return;

	

	if(isCompareFilter) { // Compare 条件下的查询

		if ( isAddCertainSearchConditions() ) { // 如果已经添加了任意一个 compare filter 条件

			// 重置过滤条件。
			QuoteInventoryViewListFilter.clearAll();

			// 向后台传入查询条件参数。
			QuoteInventoryViewListPage.myParam = composePostDataFromUi(QUOTE_INVENTORY_TYPE, true);
			
			// Quote Inventory 列表的搜索动作。（开启）
			QuoteInventoryViewListPage.start();

			// 显示 contract / tariff/ price list 列表
			showCTPList();
		} else { // 如果过滤条件一个也没有添加则提示添加条件。

			alert('The search fields are empty. Please enter search terms and try again.');
		}

		
	} else { // 非Compare条件下的查询

		// 重置过滤条件。
		QuoteInventoryViewListFilter.clearAll();

		// 向后台传入查询条件参数。
		QuoteInventoryViewListPage.myParam = composePostDataFromUi(QUOTE_INVENTORY_TYPE, false);
		
		// Quote Inventory 列表的搜索动作。（开启）
		QuoteInventoryViewListPage.start();
	}
}


/**
 * 保存查询的结构到左侧的quick link 标签中。
 * (页面中搜索条件表单中 Save 按钮的功能。)
 * @override
 * @see  quicklink.js
 * @return {[type]} [description]
 */
function saveSearch(){
	if( !validateFields() || !validateDateFields() ) return;
	var callback = {
		success: renderQuickLink,
		failure: showError
	};
	var postData = '';

	if (!isCompareFilter) { // 非compare情况下。

		postData = "queryString="+composePostDataFromUi(QUOTE_INVENTORY_TYPE, false).replace(/&/g, "%26")+"&quicklinkName=" + ccmEscape(queryName.value);
	} else { // compare 情况下。

		postData = "queryString="+composePostDataFromUi(null, true).replace(/&/g, "%26") + composeCompareListFieldFilter() + "&quicklinkName=" + ccmEscape(queryName.value);
	}
	
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest('POST' , saveQuicklinkUri , callback , postData );
}

/**
 * 点击左侧边栏中的quick link， 还原已经存储的信息。
 * 主要是搜索条件表单中的信息。
 * @override
 * @see  quicklink.js
 * @param  {[type]} o [description]
 * @return {[type]}   [description]
 */
function populateQuicklinkOnUICallback(o){
	hideLoadingModalLayer();
	if(!o.responseText){return false;}
	if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
		window.location.reload();
		return;
	}
	var data = eval("("+o.responseText+")");
	if(data.error){
		alert("Error: " + data.error);
		return;
	}
	
	cleanFormElementValue();
	try{ clearJSelectQuicklink(); }catch(e){ }
	var dataQuery = data.query;
	var circuitPageOne=dataQuery.circuitPageOne;
	var f0 = form0.getElementsByTagName("input");
	for(var k in dataQuery){
		var f_ = false;
		var v = dataQuery[k];
		try{ 
			// 还原flexbox类型下拉列表中的信息。
			if(setJSelectQuicklink(k,v,dataQuery)){ 
				continue;
			} 
		}catch(e){}
		
		// 还原input文本框中的信息。
		for(var m = 0; m<f0.length; m++){
			if(f0[m].name==k){
				$(f0[m]).val(v);
				var f_ = true;
				break;
			}
		}

		if(f_){
			continue;
		}

	}
	updateRadioButton();
	if ( 'searchQuoteVO.isCompareFilter' in dataQuery ) { // Compare 状态下。

		$('.compare-checkbox').attr('checked', 'checked'); // 选中复选框
		// 如果quicklink 记录的是 compare状态下的快速查询
		// 将 Compare Flag 置为true.
		isCompareFilter = true; 
		// 将表单中搜索条件的右半部分禁用。
		disabledRightSearchConditions();
		startSearch();
	} else {
		startSearch();
	}
}

/**
 * 当点击quick link 链接的时候，将下拉列表中的值复原。
 * @override
 * @see  quicklink.js
 * @param {[type]} k [description]
 * @param {[type]} v [description]
 * @param {[type]} d [description]
 */
function setJSelectQuicklink(k,v,d){

	if(k == "searchQuoteVO.provider"){ // Provider
		form0_searchQuoteVO_providerName.setValue(v);
		return true;
	}

	if(k == "searchQuoteVO.productInternal"){ // Product
		form0_searchQuoteVO_productName.setValue(v);
		return true;
	}
	return false;
}

/**
 *  当点击quick link 链接的时候，清除下拉列表中保存的值。
 * @return {[type]} [description]
 */
function clearJSelectQuicklink(){
	form0_searchQuoteVO_providerName.setValue("all");
	form0_searchQuoteVO_productName.setValue("all");
}

/**
 * 将当前显示的 quote inventory 列表的内容，下载为Excel 文件，
 * 包括选中下载的功能， 就是选中特定的条目进行下载。
 */
function downloadQuoteInventoryToExcel() {
	showLoadingModalLayer();
	// 使用substring 方法截取字符串是因为字符串的前面会多出一个
	// '&', 因为checkbox 复选框并不会下载到 Excel文件中
	// cols 属性中的 notDownload: 'Y', 就不会添加到titles中，
	// 这样就多出一个 '&',  因此需要这样的截取。
	var titles = QuoteInventoryViewListPage.getTitlesParam("titles").substring(1); // 获取所有的列头。
	var idsParams = "";
	var listPageParams = "";
	var paramStr = QuoteInventoryViewListPage.paramStr;
	var len = selectedItemArray.length;

	if ( len > 0 ) {

		for(var i = 0; i < len; i ++) {
			idsParams += "ids=" + selectedItemArray[i] + "&";
		}
	}

	// 去掉字符串结尾的 '&' 符号。
	idsParams = idsParams.substring(0, idsParams.length - 1 ); // 根据id来下载选中的条目。
	listPageParams = paramStr.substring(0, paramStr.length - 1 );

	// 如果配置的地址栏中有特殊的符号(#, = 等)，就需要进行特殊的转义，
	// 否则就会在服务器端报告错误。（使用 replace()方法进行替换。）
	// 这里要替换 titles 中的 '#'
	windowOpen(actionUri.downloadQuoteInventoryToExcelUri + "?"+ idsParams +"&"+ titles.replace(/#/g, "%23").replace(/\+/g, "%2B") + "&" + listPageParams);

	hideLoadingModalLayer();
}

/**
 * 将Quote Inventory 表中信息的title信息当做Excel模板下载下来。
 */
function downloadQuoteInventoryTemplateToExcel() {

	showLoadingModalLayer();

	var titles = QuoteInventoryViewListPage.getTitlesParam("titles").substring(1); // 获取所有的列头。

	windowOpen(actionUri.downloadQuoteInventoryTemplateToExcelUri + "?"+  titles.replace(/#/g, "%23").replace(/\+/g, "%2B") );

	
	hideLoadingModalLayer();

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
 * 组合查询条件，传入到后台中进行查询。
 * @return {[type]} [description]
 */
function composePostDataFromUi( filterType, isCompareFilter ) {
	var postData = '';

	var recordId = encodeURIComponent( $('.record-id').val().trim() ); // Record Id

	var provider = form0_searchQuoteVO_providerName.getValue(); // Provider
	var product = form0_searchQuoteVO_productName.getValue(); // product

	var dateReceivedStartOn = $('#searchQuoteVO_dateReceivedStartOn').val(); // Date Reveived (from)
	var dateReceivedEndOn = $('#searchQuoteVO_dateReceivedEndOn').val(); // Date Reveived (to)

	var zendeskQuoteId = encodeURIComponent( $('.zendesk-quote-id').val().trim() ); // Zendesk Quote Id
	var enterpriseCustomer = encodeURIComponent( $('.enterprise-customer').val().trim() ); // Enterprise Customer
	

	var dateIssuedStartOn = $('#searchQuoteVO_dateIssuedStartOn').val(); // Date Isssued (from)
	var dateIssuedEndOn = $('#searchQuoteVO_dateIssuedEndOn').val(); // Date Isssued (to)

	var aStreetName = encodeURIComponent( $('.a-street-name').val().trim() ); // A-Street-Name
	var aStreetNumber = encodeURIComponent( $('.a-street-number').val().trim() ); // A-Street-Number
	var aProvince = encodeURIComponent( $('.a-province').val().trim() ); // A-Prov
	var aCity = encodeURIComponent( $('.a-city').val().trim() ); // A-City
	var aPostalCode = encodeURIComponent( $('.a-postal-code').val().trim() ); // A-Postal-Code

	var zStreetName = encodeURIComponent( $('.z-street-name').val().trim() ); // Z-Street-Name
	var zStreetNumber = encodeURIComponent( $('.z-street-number').val().trim() ); // Z-Street-Number
	var zProvince = encodeURIComponent( $('.z-province').val().trim() ); // Z-Prov
	var zCity = encodeURIComponent( $('.z-city').val().trim() ); // Z-City
	var zPostalCode = encodeURIComponent( $('.z-postal-code').val().trim() ); // Z-Postal-Code

	var contractFilterCause = ContractViewListFilter.getCause(); // Contract Filter
	var tariffFilterCause = TariffViewListFilter.getCause(); // Tariff Filter
	var priceListFilterCause = PriceListViewListFilter.getCause(); // Price List Filter
	var quoteInventoryFilterCause = QuoteInventoryViewListFilter.getCause(); // Quote Inventory Filter

	// 抽离出compare 条件下需要用到的表单中的
	// 一些搜索条件。
	var compareFilterConditionsObject = {
		provider: provider,
		product: product,
		aStreetName: aStreetName,
		aStreetNumber: aStreetNumber,
		aProvince: aProvince,
		aCity: aCity,
		aPostalCode: aPostalCode,
		zStreetName: zStreetName,
		zStreetNumber: zStreetNumber,
		zProvince: zProvince,
		zCity: zCity,
		zPostalCode: zPostalCode
	};

	if ( isCompareFilter ) { // Compare 状态下的搜索条件。

		// Compare 状态下的标志。
		postData += 'searchContractTariffPriceListVO.isCompareFilter=true&';
		postData += 'searchQuoteVO.isCompareFilter=true&';

		// 组合Compare 搜索条件。
		postData += composeComparePostDataUri(compareFilterConditionsObject);

		
	} else { // 非Compare状态下的搜索条件。

		postData += composeComparePostDataUri(compareFilterConditionsObject);

		if( !isEmptyStringValue(recordId) ) { // Record Id
			postData += 'searchQuoteVO.recordId=' + recordId + '&';
		}

		if( !isEmptyStringValue(dateReceivedStartOn) ) { // Date Reveived From
			postData += 'searchQuoteVO.dateReceivedStartOn=' + dateReceivedStartOn + '&';
		}

		if( !isEmptyStringValue(dateReceivedEndOn) ) { // Date Reveived To
			postData += 'searchQuoteVO.dateReceivedEndOn=' + dateReceivedEndOn + '&';
		}

		if( !isEmptyStringValue(zendeskQuoteId) ) { // Zendesk Quote Id
			postData += 'searchQuoteVO.zendeskQuoteId=' + zendeskQuoteId + '&';
		}

		if( !isEmptyStringValue(enterpriseCustomer) ) { // Enterprise Customer
			postData += 'searchQuoteVO.enterpriseCustomer=' + enterpriseCustomer + '&';
		}

		if( !isEmptyStringValue(dateIssuedStartOn) ) { // Date Issued From
			postData += 'searchQuoteVO.dateIssuedStartOn=' + dateIssuedStartOn + '&';
		}

		if( !isEmptyStringValue(dateIssuedEndOn) ) { // Date Issued To
			postData += 'searchQuoteVO.dateIssuedEndOn=' + dateIssuedEndOn + '&';
		}
		
	}

	/* quote/ contract/ tariff/ price list 四个列表的过滤条件。 */
	if( !isEmptyStringValue(quoteInventoryFilterCause) && filterType === QUOTE_INVENTORY_TYPE) { // Quote Inventory Filter
		postData += ("searchQuoteVO.filter=" + quoteInventoryFilterCause + '&');
	}

	if ( !isEmptyStringValue(contractFilterCause) && filterType === CONTRACT_FILTER_TYPE) { // Contract Filter
		postData += 'searchContractTariffPriceListVO.filter=' + contractFilterCause + '&';
	}

	if ( !isEmptyStringValue(tariffFilterCause) && filterType === TARIFF_TILTER_TYPE) { // Tariff Filter
		postData += 'searchContractTariffPriceListVO.filter=' + tariffFilterCause + '&';
	}

	if ( !isEmptyStringValue(priceListFilterCause) && filterType === PRICE_LIST_FILTER_TYPE ) { // Price List Filter
		postData += 'searchContractTariffPriceListVO.filter=' + priceListFilterCause + '&';
	} 

	return postData;

}

/**
 * 组合compare状态下的搜索条件
 * @param  {[type]} compareFilterConditionsObject compare状态下的搜索条件
 * @return {[type]}                               [description]
 */
function composeComparePostDataUri(compareFilterConditionsObject) {
	var comparePostData = '';

	if(compareFilterConditionsObject.provider !== 'all') { // Provider
		comparePostData += 'searchQuoteVO.provider=' + compareFilterConditionsObject.provider + '&';
	}

	if( compareFilterConditionsObject.product !== 'all' ) { // Product (Product Internal)
		comparePostData += 'searchQuoteVO.productInternal=' + compareFilterConditionsObject.product + '&';
	}

	if( !isEmptyStringValue(compareFilterConditionsObject.aStreetName) ) { // A-Street-Name
		comparePostData += 'searchQuoteVO.aStreetName=' + compareFilterConditionsObject.aStreetName + '&';
	}

	if( !isEmptyStringValue(compareFilterConditionsObject.aStreetNumber) ) { // A-Street-Number
		comparePostData += 'searchQuoteVO.aStreetNumber=' + compareFilterConditionsObject.aStreetNumber + '&';
	}

	if( !isEmptyStringValue(compareFilterConditionsObject.aProvince) ) { // A-Prov
		comparePostData += 'searchQuoteVO.aProvince=' + compareFilterConditionsObject.aProvince + '&';
	}

	if( !isEmptyStringValue(compareFilterConditionsObject.aCity) ) { // A-City
		comparePostData += 'searchQuoteVO.aCity=' + compareFilterConditionsObject.aCity + '&';
	}

	if( !isEmptyStringValue(compareFilterConditionsObject.aPostalCode) ) { // A-Postal-Code
		comparePostData += 'searchQuoteVO.aPostalCode=' + compareFilterConditionsObject.aPostalCode + '&';
	}

	if( !isEmptyStringValue(compareFilterConditionsObject.zStreetName) ) { // Z-Street-Name
		comparePostData += 'searchQuoteVO.zStreetName=' + compareFilterConditionsObject.zStreetName + '&';
	}

	if( !isEmptyStringValue(compareFilterConditionsObject.zStreetNumber) ) { // Z-Street-Number
		comparePostData += 'searchQuoteVO.zStreetNumber=' + compareFilterConditionsObject.zStreetNumber + '&';
	}

	if( !isEmptyStringValue(compareFilterConditionsObject.zProvince) ) { // Z-Prov
		comparePostData += 'searchQuoteVO.zProvince=' + compareFilterConditionsObject.zProvince + '&';
	}

	if( !isEmptyStringValue(compareFilterConditionsObject.zCity) ) { // Z-City
		comparePostData += 'searchQuoteVO.zCity=' + compareFilterConditionsObject.zCity + '&';
	}

	if( !isEmptyStringValue(compareFilterConditionsObject.zPostalCode) ) { // Z-Postal-Code
		comparePostData += 'searchQuoteVO.zPostalCode=' + compareFilterConditionsObject.zPostalCode + '&';
	}

	return comparePostData;
}

/**
 * 当处于compare状态下进行保存快速查询链接 quicklink 时，
 * 需要将列表中已经过滤的字段保存起来。
 * @return {[type]} [description]
 */
function composeCompareListFieldFilter() {

	var compareListFieldFilterPostData = '';
	var contractFilterCause = ContractViewListFilter.getCause(); // Contract Filter
	var tariffFilterCause = TariffViewListFilter.getCause(); // Tariff Filter
	var priceListFilterCause = PriceListViewListFilter.getCause(); // Price List Filter
	var quoteInventoryFilterCause = QuoteInventoryViewListFilter.getCause(); // Quote Inventory Filter

	/* quote/ contract/ tariff/ price list 四个列表的过滤条件。 */
	if( !isEmptyStringValue(quoteInventoryFilterCause) ) { // Quote Inventory Filter
		compareListFieldFilterPostData += ("searchQuoteVO.filter=" + quoteInventoryFilterCause + '&');
	}

	if ( !isEmptyStringValue(contractFilterCause) ) { // Contract Filter
		compareListFieldFilterPostData += 'searchContractTariffPriceListVO.filter=' + contractFilterCause + '&';
	}

	if ( !isEmptyStringValue(tariffFilterCause) ) { // Tariff Filter
		compareListFieldFilterPostData += 'searchContractTariffPriceListVO.filter=' + tariffFilterCause + '&';
	}

	if ( !isEmptyStringValue(priceListFilterCause) ) { // Price List Filter
		compareListFieldFilterPostData += 'searchContractTariffPriceListVO.filter=' + priceListFilterCause + '&';
	} 

	return compareListFieldFilterPostData;
}

/**
 * Quote Inventory 的过滤条件的添加动作。
 */
function addQuoteInventoryFilter(startPage) {
	// 声明Filter 条件。
	QuoteInventoryViewListFilter = new SANINCO.Filter();
	// 添加Filter条件的编辑事件。
	QuoteInventoryViewListFilter.addEditeEvent( function(){startPage.start();} );

	QuoteInventoryViewListFilter.add("q.id", "Integer"); // Record Id
	QuoteInventoryViewListFilter.add("q.record_id", "String"); // Record Id
	QuoteInventoryViewListFilter.add("q.status", "String"); // Status
	QuoteInventoryViewListFilter.add("q.date_recvd", "String"); // Date Recvd
	QuoteInventoryViewListFilter.add("q.date_issued", "String"); // Date Issued
	QuoteInventoryViewListFilter.add("q.days_to_quote", "Integer"); // No. of Days To Quote
	QuoteInventoryViewListFilter.add("q.type", "String"); // Type
	QuoteInventoryViewListFilter.add("q.zendesk_quote_id", "String"); // Zendesk Quote Id
	QuoteInventoryViewListFilter.add("q.opportunity_sites", "Integer"); // Opportunity (# sites)
	QuoteInventoryViewListFilter.add("q.nsa", "String"); // NSA
	QuoteInventoryViewListFilter.add("q.other_requester", "String"); // Other Requester
	QuoteInventoryViewListFilter.add("q.enterprise_customer", "String"); // Enterprise Customer
	QuoteInventoryViewListFilter.add("q.customer_type", "String"); // Customer Type
	QuoteInventoryViewListFilter.add("q.customer_segment", "String"); // Customer Segment
	QuoteInventoryViewListFilter.add("q.product_group", "String"); // Product Group
	QuoteInventoryViewListFilter.add("q.product_sub_group", "String"); // Product Sub Group
	QuoteInventoryViewListFilter.add("q.product_requested", "String"); // Product Requested
	QuoteInventoryViewListFilter.add("q.product_internal", "String"); // Product Internal
	QuoteInventoryViewListFilter.add("q.site_per_product", "Integer"); // Site Per Product
	QuoteInventoryViewListFilter.add("q.provider", "String"); // Provider
	QuoteInventoryViewListFilter.add("q.quantity", "Double"); // Quantity
	QuoteInventoryViewListFilter.add("q.product_external", "String"); // Product External
	QuoteInventoryViewListFilter.add("q.access_mb", "String"); // Access(MB)
	QuoteInventoryViewListFilter.add("q.evc_1", "String"); // EVC1 Price
	QuoteInventoryViewListFilter.add("q.class_of_service_1", "String"); // EVC1 Class of Service 
	QuoteInventoryViewListFilter.add("q.evc_2", "String"); // EVC2 Price
	QuoteInventoryViewListFilter.add("q.class_of_service_2", "String"); // EVC2 Class of Service 
	QuoteInventoryViewListFilter.add("q.no_of_port", "String"); // # of Port(s)
	QuoteInventoryViewListFilter.add("q.addn_evcs", "String"); // Addn EVCs
	QuoteInventoryViewListFilter.add("q.party_date_requested", "String"); // 3RD Party Date Requested
	QuoteInventoryViewListFilter.add("q.party_date_received", "String"); // 3RD Party Date Received
	QuoteInventoryViewListFilter.add("q.quote_provider_days", "Integer"); // No. of Days To Quote Provider
	QuoteInventoryViewListFilter.add("q.on_near_net", "String"); // On-Net/Near-Net
	QuoteInventoryViewListFilter.add("q.currency", "String"); // Currency
	QuoteInventoryViewListFilter.add("q.access", "Double"); // Access
	QuoteInventoryViewListFilter.add("q.evc_number_1", "Double"); // EVC Number 1
	QuoteInventoryViewListFilter.add("q.evc_number_2", "Double"); // EVC Number 2
	QuoteInventoryViewListFilter.add("q.evc_number_3", "Double"); // EVC Number 3
	QuoteInventoryViewListFilter.add("q.port_charge", "Double"); // Port Charge
	QuoteInventoryViewListFilter.add("q.mrc_year_1", "Double"); // MRC Year 1
	QuoteInventoryViewListFilter.add("q.mrc_year_2", "Double"); // MRC Year 2
	QuoteInventoryViewListFilter.add("q.mrc_year_3", "Double"); // MRC Year 3
	QuoteInventoryViewListFilter.add("q.mrc_year_5", "Double"); // MRC Year 5
	QuoteInventoryViewListFilter.add("q.mrc_year_7", "Double"); // MRC Year 7
	QuoteInventoryViewListFilter.add("q.mrc_year_10", "Double"); // MRC Year 10
	QuoteInventoryViewListFilter.add("q.mrc_year_15", "Double"); // MRC Year 15
	QuoteInventoryViewListFilter.add("q.nrc", "Double"); // NRC
	QuoteInventoryViewListFilter.add("q.construction_costs", "Double"); // Construction Costs
	QuoteInventoryViewListFilter.add("q.a_unit", "String"); // A-Suite/Unit
	QuoteInventoryViewListFilter.add("q.a_street_number", "String"); // A-Street Number
	QuoteInventoryViewListFilter.add("q.a_street_name", "String"); // A-Street Name
	QuoteInventoryViewListFilter.add("q.a_city", "String"); // A-City
	QuoteInventoryViewListFilter.add("q.a_province", "String"); // A-Prov
	QuoteInventoryViewListFilter.add("q.a_postal_code", "String"); // A-Postal Code
	QuoteInventoryViewListFilter.add("q.a_country", "String"); // A-Country
	QuoteInventoryViewListFilter.add("q.z_unit", "String"); // Z-Suite/Unit
	QuoteInventoryViewListFilter.add("q.z_street_number", "String"); // Z-Street Number
	QuoteInventoryViewListFilter.add("q.z_street_name", "String"); // Z-Street Name
	QuoteInventoryViewListFilter.add("q.z_city", "String"); // Z-City
	QuoteInventoryViewListFilter.add("q.z_province", "String"); // Z-Prov
	QuoteInventoryViewListFilter.add("q.z_postal_code", "String"); // Z-Postal Code
	QuoteInventoryViewListFilter.add("q.z_country", "String"); // Z-Country
	QuoteInventoryViewListFilter.add("q.notes", "String"); // Notes
	QuoteInventoryViewListFilter.add("q.carrier_quote", "String"); // Presale/FOXX # - Carrier Quote #
	QuoteInventoryViewListFilter.add("q.cbs_mrc_original", "Double"); // CBS - MRC (Original)
	QuoteInventoryViewListFilter.add("q.cbs_nrc_construction_original", "Double"); // CBS - NRC + Construction (Original)
	QuoteInventoryViewListFilter.add("q.quote_desk_rep", "String"); // Quote Desk Rep
	QuoteInventoryViewListFilter.add("q.firm_won_bid", "String"); // Firm/Won Bid
	QuoteInventoryViewListFilter.add("q.cost_savings", "String"); // Cost Savings

	QuoteInventoryViewListPage.addCompleteEvent(function(o){
		$(window).scrollTop(0);
	});

	// 将Filter 条件应用到列表中。
	startPage.setFilter(QuoteInventoryViewListFilter);
}

/**
 * 当列表中的这一行的checkbox被选中的时候，
 * 此行的颜色需要改变。
 * 而且要将一个选中的条目放到数组中。
 */
function markItem(DOMElement) {
	var item = $(DOMElement).parents('tr')[0];

	if( $(DOMElement).is(':checked') ) {

		$(item).addClass('highlight');
		// 将选中的元素的序号添加到数组中。
		selectedItemArray.push( $(DOMElement).attr('data-id') );
	} else {

		$(item).removeClass('highlight');
		// 将取消选中的条目从数组中移除。
		var itemIndex = selectedItemArray.indexOf( $(DOMElement).attr('data-id'));
		selectedItemArray.splice(itemIndex, 1);
	}

}

/**
 * Quote Inventory 列表在切换分页的时候，需要时刻记录选中和取消选中的
 * 状态，即使是在分页加载之后也可正常显示。
 * 这个方法为了记录选中的条数的。
 */
function markSelectedListItem() {

	// 获取当前加载列表中的全部checkbox.
	var listItems = $('.quote-list-checkbox-item');

	var itemIndex = 0;

	var listItem = '';

	$('.quote-list-checkbox-item').each(function() {

		itemIndex = selectedItemArray.indexOf( $(this).attr('data-id'));
		// 选中之前勾选的选项。
		if (itemIndex != -1) {
			$(this).attr('checked', true);
			listItem = $(this).parents('tr')[0];
			$(listItem).addClass('highlight');
		}

	});
}

/**
 * 文本框之间的禁用与启用的切换。
 * @param  {[type]} id     当前的 checkbox 的id属性值。
 * @param  {[type]} number 当前 calendar textbox 的索引值。
 * @return {[type]}        [description]
 */
function clearDisabled(id, number) {

	if ($('#'+ id)[0].checked) {
		$(".Clear" + number).addClass("enabled").attr("disabled","").val("");
	}else{
		$(".Clear" + number).removeClass("enabled").attr("disabled","disabled").val("");;
	}
}


/**
 * 将日期控件绑定到相应的 calendar textbox 上。
 * @param  {[type]} lockCheckboxId 启用或者是禁用 calendar textbox 的复选框的id。
 * @param  {[type]} textboxId      calendar textbox 的 id 属性值。
 * @return {[type]}                [description]
 */
function calendarController(lockCheckboxId, textboxId) {

	if($('#' + lockCheckboxId )[0].checked) {

		g_Calendar.show(event, textboxId, false, 'yyyy/mm/dd', new Date(1990,1,1));
	}
	return false;
}

/**
 * 将Quote Inventory 页面中上半部分表单中的信息重置。
 * @return {[type]} [description]
 */
function resetFormElementValue() {

	// 清空select List搜索条件。
	form0_searchQuoteVO_providerName.setValue('all'); // Provider
	form0_searchQuoteVO_productName.setValue('all'); // product 

	// 清除form表单中所有的文本输入框或者是select复选框中的值。
	cleanFormElementValue();

	// 清除按钮将清除数据错误时的状态。
	if ( $('.record-id').hasClass('validation-failed') ) { // Record ID.
		$('.record-id').removeClass('validation-failed');
	}

	$('.Date').each(function() { // 日期文本框。

		if ( $(this).hasClass('validation-failed') ) {

			$(this).removeClass('validation-failed')
		}
	});
	

	// 重置 calendar textbox 和 calendar checkbox 状态。
	clearCheckbox();

	enabledRightSearchConditions(); // 将右侧条件设置为可用状态。

	isCompareFilter = false;

	hideCTPList(); // 隐藏CTP三个列表。
}

/**
 * 将calendar checkbox 的选中状态清除，而且将所有的 calendar textbox
 * 重置为禁用状态。
 * @return {[type]} [description]
 */
function clearCheckbox(){
	$(".switch-checkbox").removeAttr("checked");
	$(".Date").removeClass("enabled").attr("disabled","disabled").val("");
	$(".compare-checkbox").removeAttr("checked");
}

/**
 * 这个方法的作用就是当使用日期控件(calendar controller)的时候，
 * 当不同的文本框获取焦点的时候，radio button 之间的转换，
 * #cleanFormElementValue() 方法是定义到quicklink.js文件中，
 * 这个方法的内部调用了 resetRadioButton() 这个方法，
 * 所以这里需要定义。
 * @override
 * @see  quicklink.js
 * @return {[type]} [description]
 */
function resetRadioButton(){}

/**
 * @override
 * @see quicklink.js
 * @return {[type]} [description]
 */
function updateRadioButton(){
	
}

quoteErrorFilePath = null;
function showQuoteWindow(errorFilePath){
	quoteErrorFilePath = errorFilePath;
	YAHOO.ccm.container.contactVendorWindow.show();
}

function downLoadQuoteResult(){
	showLoadingModalLayer();
	windowOpen("downLoadQuoteErrorFile.action?errorFileName="+ quoteErrorFilePath);
	hideLoadingModalLayer();
}

filesSize = null ;
function filesProcess(files){
	showLoadingModalLayer();
	var newAttach = '<input style="text-align:center;width:70px;height: 19px;position: absolute;top: 0;left: 0;z-index: 1;opacity: 0;cursor: pointer;" ' +
	'type="file" name="uploads" multiple="multiple" onchange="filesProcess(this.files)" />';
	
	$("#attachDiv :input").each(function () {
		var this_name = $(this).attr("name");
		$(this).hide();
	});
	$("#attachDiv").append(newAttach);
    $("#uploadUqoteForm").submit();
}

/**
 * 当将 Compare 复选框选中或者是取消选中的效果，
 * 右半部分在复选框选中的时候是灰色的，而且会显示 contract/tariff/price list列表
 * 在取消选中的时候，右边部分是
 * 可以操作的。
 */
function compareFilters(compareCheckElement) {

	if ( $(compareCheckElement).is(':checked') ) {

		isCompareFilter = true;

		// $('.compare-checkbox').attr('checked', 'checked'); // 选中compare checkbox.

		// 禁用右半部分的搜索条件.
		disabledRightSearchConditions();


		
	} else {

		isCompareFilter = false;

		// 当取消compare 选中状态的时候，
		// 恢复右侧搜索条件的状态为可用状态。
		enabledRightSearchConditions();

		// 隐藏 contract/tariff/price list 列表。
		hideCTPList();
		
	}
}

/**
 * 禁用表单中右半部分的搜索条件。
 */
function disabledRightSearchConditions() {

	// 清空文本框
	$('.record-id').val('');
	$('.zendesk-quote-id').val('');
	$('.enterprise-customer').val('');

	
	$(".Date").removeClass("enabled").attr("disabled","disabled").val(""); // 禁用日期选择框
	// 禁用操纵日期选择框的复选框
	$('.switch-checkbox').attr('disabled', 'disabled');
	// 取消选中操纵日期选择框的复选框
	$(".switch-checkbox").removeAttr("checked");

	// 鼠标移动到复选框的时候，光标的样式。
	$('.switch-checkbox').addClass('default');

	// 元素容器的背景颜色
	$('.compare-table-td').addClass('invalid');

	// 文本框的禁用。
	$('.compare-filters-table input').attr('disabled', 'disabled').addClass('invalid');
}

/**
 * 启用表单中右半部分的搜索条件。
 */
function enabledRightSearchConditions() {

	$('.switch-checkbox').attr('disabled', '');
	$('.switch-checkbox').removeClass('default');
	$('.compare-table-td').removeClass('invalid');
	$('.compare-filters-table input').attr('disabled', '').removeClass('invalid');
}

/**
 * 显示 contract/tariff/price list 列表。 
 */
function showCTPList() {

	ContractViewListFilter.clearAll();
	TariffViewListFilter.clearAll();
	PriceListViewListFilter.clearAll();	

	ContractViewListPage.myParam = composePostDataFromUi(CONTRACT_FILTER_TYPE, true);
	TariffViewListPage.myParam = composePostDataFromUi(TARIFF_TILTER_TYPE, true);
	PriceListViewListPage.myParam = composePostDataFromUi(PRICE_LIST_FILTER_TYPE, true);

	/*Contract*/
	ContractViewListPage.start();

	/*Tariff*/
	TariffViewListPage.start();

	/* Price List */
	PriceListViewListPage.start();
}

/**
 * 隐藏 contract/tariff/price list 列表。
 */
function hideCTPList() {

	$('#contract-wrapper').hide();
	$('#tariff-wrapper').hide();
	$('#priceList-wrapper').hide();
}

/**
 * 当状态是compare 的复选框被选中的时候，
 * 判断是否填写了表单中的任意一个条件。
 */
function isAddCertainSearchConditions() {

	var isNotAdd = true;

	var provider = form0_searchQuoteVO_providerName.getValue(); // Provider
	var product = form0_searchQuoteVO_productName.getValue(); // product

	var aStreetName = $('.a-street-name').val().trim(); // A-Street-Name
	var aStreetNumber = $('.a-street-number').val().trim(); // A-Street-Number
	var aProvince = $('.a-province').val().trim(); // A-Prov
	var aCity = $('.a-city').val().trim(); // A-City
	var aPostalCode = $('.a-postal-code').val().trim(); // A-Postal-Code

	var zStreetName = $('.z-street-name').val().trim(); // Z-Street-Name
	var zStreetNumber = $('.z-street-number').val().trim(); // Z-Street-Number
	var zProvince = $('.z-province').val().trim(); // Z-Prov
	var zCity = $('.z-city').val().trim(); // Z-City
	var zPostalCode = $('.z-postal-code').val().trim(); // Z-Postal-Code

	isNotAdd = (provider == 'all') && (product == 'all') && isEmptyStringValue(aStreetName) &&
				isEmptyStringValue(aStreetNumber) && isEmptyStringValue(aProvince) && isEmptyStringValue(aCity) &&
				isEmptyStringValue(aPostalCode) && isEmptyStringValue(zStreetName) && isEmptyStringValue(zStreetNumber) &&
				isEmptyStringValue(zProvince) && isEmptyStringValue(zCity) && isEmptyStringValue(zPostalCode);

	return !isNotAdd;

}


