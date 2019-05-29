
// 字符串常量
var CONTRACT_FILTER_TYPE = "Contract";
var TARIFF_TILTER_TYPE = "Tariff";
var PRICE_LIST_FILTER_TYPE = "Price List";

var _auditReferenceTypeId = "";
var _auditReferenceId = "";

// 向后台请求的多个actions.
actionUri = {
	/**
	 * Contract
	 */
	// 获取Contract列表
	getContractListUri: "getContractList.action",
	// 获取contract列表的分页信息
	getContractListPageNoUri: "getContractListPageNo.action",

	// 下载 Contract List 列表到Excel文件中。
	downloadContractListUri: "downloadContractList.action",


	/**
	 * Tariff
	 */
	// 获取Tariff 列表
	getTariffListUri: "getTariffList.action",
	// 获取Tariff 列表的分页信息
	getTariffListPageNoUri: "getTariffListPageNo.action",

	// 下载 Tariff List 列表到Excel文件中 
	downloadTariffListUri: "downloadTariffList.action",

	/**
	 * Price List
	 */
	// 获取 Price List 列表
	getPriceListListUri: "getPriceListList.action",

	// 获取Price List 列表的分页信息
	getPriceListListPageNoUri: "getPriceListListPageNo.action",

	// 下载 Price List List 列表到Excel文件中 
	downloadPriceListListUri: "downloadPriceListList.action",
	
	/**
	 * Rate Discrepancy List
	 */
	getRateDiscrepancyList: "getRateDiscrepancyList.action",
	
	getRateDiscrepancyListPageNo: "getRateDiscrepancyListPageNo.action",
	
	saveExcelBySearchRateDiscrepancy: "saveExcelBySearchRateDiscrepancy.action"
};

/**
 * Termination penalty 在鼠标点击的时候，
 * 弹出系统penalty notes弹出框 的动作。
 */
function showPenaltyNotes(domElement, penaltyNotes) {


	$('#penalty-notes-wrapper .penalty-notes-content').html(penaltyNotes);
	YAHOO.ccm.container.penaltyNotesWindow = new YAHOO.widget.Dialog("penalty-notes-wrapper", { 
		width: "420px",
	    fixedcenter: true,
	    visible: false,
	    constraintoviewport: true,
	    modal: true
	});
	YAHOO.ccm.container.penaltyNotesWindow.render();
	YAHOO.ccm.container.penaltyNotesWindow.show();

}


/**
 * 渲染 Contract 列表。
 */
function getContractList() {

	ContractViewListPage = new SANINCO.Page("contract-data-table","ContractViewListPage",{
		sortingField : "fileName",
	    sortingDirection : "asc",
	    vo : "searchContractTariffPriceListVO",
		autoToTop : true,
		recordText: 'Contract Search Results:',
	    totalPageUri : actionUri.getContractListPageNoUri,
	    dataUri : actionUri.getContractListUri,
		paginationDiv : "contract-dataTable-page",
		recPerArray : [10,20,30,50,100,500,1000],
		cols: [{title: "File Name", dataField: "fileName", sort : "fileName", filtration : {name:"cf.contract_number", alias:"File Name"} },
		{title: "Term Expiry", dataField: "expiryDate", sort : "expiryDate", filtration : {name:"cf.expiry_date", alias:"Term Expiry"} },
		{title: "Service Type", dataField: "serviceType", sort : "serviceType", filtration : {name:"c.service_type", alias:"Service Type"} },
		{title: "USOC Description", dataField: "usoc", sort : "usoc", filtration : {name:"c.usoc_description", alias:"USOC Description"} },
		{title: "Circuit Number", dataField: "circuitNumber", sort : "circuitNumber", filtration : {name:"c.circuit_number", alias:"Circuit Number"} },
		{title: "Rate Description", dataField: "rate", sort : "rate" },
		{title: "Amendment", dataField: "amendment", sort : "amendment", filtration : {name:"c.amendment", alias:"Amendment"} },
		{title: "Schedule", dataField: "schedule", sort : "schedule", filtration : {name:"c.schedule", alias:"Schedule"} },
		{title: "Termination Penalty", display: 'none', dataField: function(o) {	return buildPenaltyStructure(o);}, sort : "penaltyAmount", filtration : {name:"c.penalty_amount", alias:"Termination Penalty"} },
		{title: "Termination Penalty Notes", dataField: "penaltyNotes", display: 'none' },
//		{title: "Rate Discrepancy Flag", dataField: function(o) {
//			var htmlContent = "";
//			if(o.rateDiscrepancyFlag=="Y"){
//				var htmlContent = "<img src=\"include/images/circuitMasterInventory/master_inventory_change_history.png\" " +
//				   "style=\"margin-left:60px\" onclick=\"showRateDiscrepancyList(3," + o.id + ")\"/>"
//			}
//			return htmlContent;
//		}, sort: "rateDiscrepancyFlag"}, // End of Rate Discrepancy Flag (Contract)
		{title: "Download", notDownload: 'Y', dataField:function(o){
			return (o.attachmentPointId == "" ? "" : "<img src=\"include/images/download1.gif\" style=\"cursor: pointer;\" onclick=\"SANINCO.Download(" + o.attachmentPointId + ");\"/>");} }]
	});

	// 列表中的操作按钮和列表的显示控制逻辑
	ContractViewListPage.addTotalSuccessEvent(function(data){
		$('#contract-wrapper').show();

		if(data.totalResultNo < 0 || data.error){

			$('#downloadContractRecordsToExcel').css('display', 'none');
			
		} else {

			if(data.totalResultNo == 0 ){
				$('#downloadContractRecordsToExcel').css('display', 'none');
			}else{
				$('#downloadContractRecordsToExcel').css('display', 'block');
				
			}

		}
	});

	// 给Contract 列表中的字段添加过滤条件。
	addContractFilter(ContractViewListPage);
	
}

/**
 * 创建contract 列表中 Termination Penalty 列中
 * 数据显示的基本结构。
 * @return {[type]} [description]
 */
function buildPenaltyStructure(contractDataObj) {

	var penaltyString = '';
	penaltyString += '<div class="penalty clearfix">';
	penaltyString += '<span class="penalty-amount">'+ contractDataObj.penaltyAmount +'</span>';
	penaltyString += '<i class="penalty-notes-icon" onclick="showPenaltyNotes(this, \''+ contractDataObj.penaltyNotes +'\')" ></i>';
	penaltyString += '</div>';

	return penaltyString;
}

/**
 * Contract 列表的过滤条件。
 */
function addContractFilter(startPage) {

	// 声明Filter 条件。
	ContractViewListFilter = new SANINCO.Filter();
	// 添加Filter条件的编辑事件。
	ContractViewListFilter.addEditeEvent( function(){startPage.start();} );

	ContractViewListFilter.add("cf.contract_number", "String"); // File Name
	ContractViewListFilter.add("cf.expiry_date", "String"); // Expiry Date
	ContractViewListFilter.add("c.service_type", "String"); // Service Type
	ContractViewListFilter.add("c.usoc_description", "String"); // USOC Description
	ContractViewListFilter.add("c.circuit_number", "String"); // Circuit Number
	ContractViewListFilter.add("c.amendment", "String"); // Amendment
	ContractViewListFilter.add("c.schedule", "String"); // Schedule
	ContractViewListFilter.add("c.penalty_amount", "String"); // Termination Penalty


	// 将Filter 条件应用到列表中。
	startPage.setFilter(ContractViewListFilter);

	ContractViewListPage.addCompleteEvent(function(o){
		$(window).scrollTop(0);
	});
}

/**
 * 下载Contract Excel 文件。
 * @return {[type]} [description]
 */
function downloadContractRecordsToExcel() {
	showLoadingModalLayer();
	var titles = ContractViewListPage.getTitlesParam("titles"); // 获取所有的列头。
	var paramStr = ContractViewListPage.paramStr;
	paramStr = paramStr.substring(0, paramStr.length - 1 );
	
	windowOpen(actionUri.downloadContractListUri + "?" + titles + "&" + paramStr);

	hideLoadingModalLayer();

}

/**
 * 渲染 Tariff 列表。
 */
function getTariffList() {
	TariffViewListPage = new SANINCO.Page("tariff-data-table","TariffViewListPage",{
		sortingField : "fileName",
	    sortingDirection : "asc",
	    vo : "searchContractTariffPriceListVO",
		autoToTop : true,
		recordText: 'Tariff Search Results:',
	    totalPageUri : actionUri.getTariffListPageNoUri,
	    dataUri : actionUri.getTariffListUri,
		paginationDiv : "tariff-dataTable-page",
		recPerArray : [10,20,30,50,100,500,1000],
		cols: [{title: "File Name", dataField: "fileName", sort : "fileName", filtration : {name:"tf.tariff_name", alias:"File Name"} },
		{title: "Page", dataField: "page", sort : "page", filtration : {name:"t.page", alias:"Page"} },
		{title: "Part Section", dataField: "partSection", sort : "partSection", filtration : {name:"t.part_section", alias:"Part Section"} },
		{title: "Item #", dataField: "itemNumber", sort : "itemNumber", filtration : {name:"t.item_number", alias:"Item #"} },
		{title: "Paragraph", dataField: "paragraph", sort : "paragraph", filtration : {name:"t.paragraph", alias:"Paragraph"} },
		{title: "Tariff Last Revised", dataField: "tariffLastRevised", sort : "tariffLastRevised", filtration : {name:"t.tariff_last_revised", alias:"Tariff Last Revised"} },
		{title: "Service Type", dataField: "serviceType", sort : "serviceType", filtration : {name:"t.service_type", alias:"Service Type"} },
		{title: "Band", dataField: "band", sort : "band", filtration : {name:"t.band", alias:"Band"} },
		{title: "Provider", dataField: "provider", sort : "provider", filtration : {name:"t.provider", alias:"Provider"} },
		{title: "Rate Mode", dataField: "rateMode", sort : "rateMode" },
		{title: "Rate Description", dataField: "rate", sort : "rate" },
//		{title: "Rate Discrepancy Flag", dataField: function(o) {
//			var htmlContent = "";
//			if(o.rateDiscrepancyFlag=="Y"){
//				var htmlContent = "<img src=\"include/images/circuitMasterInventory/master_inventory_change_history.png\" " +
//				   "style=\"margin-left:60px\" onclick=\"showRateDiscrepancyList(2," + o.id + ")\"/>"
//			}
//			return htmlContent;
//		}, sort: "rateDiscrepancyFlag"},
		// End of Rate Discrepancy Flag (Tariff)
		{title: "Download", notDownload: 'Y', dataField:function(o){
			return (o.attachmentPointId == "" ? "" : "<img src=\"include/images/download1.gif\" style=\"cursor: pointer;\" onclick=\"SANINCO.Download(" + o.attachmentPointId + ");\"/>");} }]
	});

	// Tariff列表中的操作按钮和列表的显示控制逻辑
	TariffViewListPage.addTotalSuccessEvent(function(data){
		$('#tariff-wrapper').show();

		if(data.totalResultNo < 0 || data.error){

			$('#downloadTariffRecordsToExcel').css('display', 'none');
			
		} else {

			if(data.totalResultNo == 0 ){
				$('#downloadTariffRecordsToExcel').css('display', 'none');
			}else{
				$('#downloadTariffRecordsToExcel').css('display', 'block');
				
			}

		}
	});

	// 给Tariff列表中的字段添加过滤条件。
	addTariffFilter(TariffViewListPage);
	
	
}

/**
 * Tariff 列表的过滤条件。
 */
function addTariffFilter(startPage) {

	// 声明Filter 条件。
	TariffViewListFilter = new SANINCO.Filter();
	// 添加Filter条件的编辑事件。
	TariffViewListFilter.addEditeEvent( function(){startPage.start();} );

	TariffViewListFilter.add("tf.tariff_name", "String"); // File Name
	TariffViewListFilter.add("t.page", "String"); // Page
	TariffViewListFilter.add("t.part_section", "String"); // Part Section
	TariffViewListFilter.add("t.item_number", "String"); // Item #
	TariffViewListFilter.add("t.paragraph", "String"); // Paragraph
	TariffViewListFilter.add("t.tariff_last_revised", "String"); // Tariff Last Revised
	TariffViewListFilter.add("t.service_type", "String"); // Service Type
	TariffViewListFilter.add("t.band", "String"); // Band
	TariffViewListFilter.add("t.provider", "String"); // Provider


	// 将Filter 条件应用到列表中。
	startPage.setFilter(TariffViewListFilter);

	TariffViewListPage.addCompleteEvent(function(o){
		$(window).scrollTop(0);
	});
	
}

/**
 * 下载 Tariff Excel 文件。
 * @return {[type]} [description]
 */
function downloadTariffRecordsToExcel() {

	showLoadingModalLayer();
	var titles = TariffViewListPage.getTitlesParam("titles"); // 获取所有的列头。
	var paramStr = TariffViewListPage.paramStr;
	paramStr = paramStr.substring(0, paramStr.length - 1 );

	// # 这样的特殊符号传递 URL 的时候需要进行转义。
	windowOpen(actionUri.downloadTariffListUri + "?" + titles.replace(/#/g, "%23") + "&" + paramStr);
	
	hideLoadingModalLayer();
}

/**
 * 渲染 Price List 列表。
 */
function getPriceListList() {

	PriceListViewListPage = new SANINCO.Page("priceList-data-table","PriceListViewListPage",{
		sortingField : "fileName",
	    sortingDirection : "asc",
	    vo : "searchContractTariffPriceListVO",
		autoToTop : true,
		recordText: 'Price List Search Results:',
	    totalPageUri : actionUri.getPriceListListPageNoUri,
	    dataUri : actionUri.getPriceListListUri,
		paginationDiv : "priceList-dataTable-page",
		recPerArray : [10,20,30,50,100,500,1000],
		cols: [{title: "File Name", dataField: "fileName", sort : "fileName", filtration : {name:"plf.price_list_name", alias:"File Name"} },
		{title: "Service Type", dataField: "serviceType", sort : "serviceType", filtration : {name:"pl.service_type", alias:"Service Type"} },
		{title: "Band", dataField: "band", sort : "band", filtration : {name:"pl.band", alias:"Band"} },
		
		{title: "Rate Mode", dataField: "rateMode", sort : "rateMode" },
		{title: "Rate Description", dataField: "rate", sort : "rate"},
		{title: "Effective Date", dataField: "effectiveDate", sort : "effectiveDate", filtration : {name:"pl.effective_date", alias:"Effective Date"} },
//		{title: "Rate Discrepancy Flag", dataField: function(o) {
//			var htmlContent = "";
//			if(o.rateDiscrepancyFlag=="Y"){
//				var htmlContent = "<img src=\"include/images/circuitMasterInventory/master_inventory_change_history.png\" " +
//				   "style=\"margin-left:60px\" onclick=\"showRateDiscrepancyList(5," + o.id + ")\"/>"
//			}
//			return htmlContent;
//		}, sort: "rateDiscrepancyFlag" },
		// End of Rate Discrepancy Flag (Price List)
		{title: "Download", notDownload: 'Y', dataField:function(o){
			return (o.attachmentPointId == "" ? "" : "<img src=\"include/images/download1.gif\" style=\"cursor: pointer;\" onclick=\"SANINCO.Download(" + o.attachmentPointId + ");\"/>");} }]
	});

	// Price List列表中的操作按钮和列表的显示控制逻辑
	PriceListViewListPage.addTotalSuccessEvent(function(data){
		$('#priceList-wrapper').show();

		if(data.totalResultNo < 0 || data.error){

			$('#downloadPriceListRecordsToExcel').css('display', 'none');
			
		} else {

			if(data.totalResultNo == 0 ){
				$('#downloadPriceListRecordsToExcel').css('display', 'none');
			}else{
				$('#downloadPriceListRecordsToExcel').css('display', 'block');

			}

		}
	});

	// 给Price List列表中的字段添加过滤条件。
	addPriceListFilter(PriceListViewListPage);

	
}

/**
 * Price List 列表的过滤条件。
 */
function addPriceListFilter(startPage) {

	// 声明Filter 条件。
	PriceListViewListFilter = new SANINCO.Filter();
	// 添加Filter条件的编辑事件。
	PriceListViewListFilter.addEditeEvent( function(){startPage.start();} );

	PriceListViewListFilter.add("plf.price_list_name", "String"); // File Name
	PriceListViewListFilter.add("pl.service_type", "String"); // Service Type
	PriceListViewListFilter.add("pl.band", "String"); // Band
	PriceListViewListFilter.add("pl.effective_date", "String"); // Effective Date


	// 将Filter 条件应用到列表中。
	startPage.setFilter(PriceListViewListFilter);
	
	// 当页面动态的加载完CTP三个列表之后， 
	// 滚动条保持在最顶端。
	PriceListViewListPage.addCompleteEvent(function(o){
		$(window).scrollTop(0);
	});
	
}

/**
 * 下载 Price List Excel 文件。
 * @return {[type]} [description]
 */
function downloadPriceListRecordsToExcel() {
	showLoadingModalLayer();
	var titles = PriceListViewListPage.getTitlesParam("titles"); // 获取所有的列头。
	var paramStr = PriceListViewListPage.paramStr;
	paramStr = paramStr.substring(0, paramStr.length - 1 );

	
	windowOpen(actionUri.downloadPriceListListUri + "?" + titles + "&" + paramStr);
	
	hideLoadingModalLayer();
}


/**
 * 判断传过来的值，是否为空字符串。
 * @return {Boolean} [description]
 */
function isEmptyStringValue(stringValue) {
	return (stringValue === '') ? true : false;
}

/**
 * 将circuit number 格式化去掉9中符号字符串 ( ) _ - \ : . ' ' /
 */
function stripCircuitNumber(circuitNumber) {
	return circuitNumber.replace(/[\(\)\.\:_\\\/\s-]/g, '');
}

/**
 * 点击 Rate Discrepancy Flag 列中某条数据，弹出相应窗口信息
 */
function showRateDiscrepancyList(auditReferenceTypeId, auditReferenceId){
	_auditReferenceTypeId = auditReferenceTypeId;
	_auditReferenceId = auditReferenceId;
	getRateDiscrepancyList(auditReferenceTypeId, auditReferenceId);
}

/**
 * 点击关闭 Rate Discrepancy Flag 窗口
 */
function closeRateDiscrepancyList(){
	$('#rate_discrepancy_flag').hide();
	$('body').css('overflow', 'scroll');
}

/**
 * 查询 Rate Discrepancy List
 */
function getRateDiscrepancyList(auditReferenceTypeId, auditReferenceId) {
	
	if(1==1){
		rateDiscrepancyListPage = new SANINCO.Page("_rate_discrepancy_list","rateDiscrepancyListPage",{
			sortingField : "i.id",
		    sortingDirection : "desc",
		    vo : "searchContractTariffPriceListVO",
			recordText : '',
			totalPageUri : actionUri.getRateDiscrepancyListPageNo,
		    dataUri :actionUri.getRateDiscrepancyList ,
		    paginationDiv : "_rate_discrepancy_page_list",
			recPerArray : [10,20,30,40,50,70,90,100],
		    cols : [
		            // Invoice Number
					{ title : "Invoice Number",
					  dataField:"invoiceNumber",
					  filtration : {
			            name:"i.invoice_number",
			            alias:"Invoice Number"
		              },
					  sort : "i.invoice_number"
					},
					// Account Number
					{ title : "Account Number",
					  dataField:"accountNumber",
					  filtration : {
			            name:"i.account_number",
			            alias:"Account Number"
		              },
					  sort : "i.account_number"
					},
					// Invoice Date
					{ title : "Invoice Date",
					  dataField:"invoiceDate",
					  filtration : {
			            name:"i.invoice_date",
			            alias:"Invoice Date"
		              },
					  sort : "i.invoice_date"
					},
					
					// Circuit Number
					{ title : "Circuit Number",
					  dataField:"circuitNumber",
					  filtration : {
			            name:"p.circuit_number",
			            alias:"Circuit Number"
		              },
					  sort : "p.circuit_number"
					},
					// USOC
					{ title : "USOC",
					  dataField:"usoc",
					  filtration : {
			            name:"p.usoc",
			            alias:"USOC"
		              },
					  sort : "p.usoc"
					},
					// USOC Description
					{ title : "USOC Description",
						  dataField:"usocDescription",
						  filtration : {
				            name:"p.usoc_description",
				            alias:"USOC Description"
			              },
						  sort : "p.usoc_description"
					},
					// Actual Amount
					{ title : "Actual Amount",
						  dataField:"actualAmount",
						  filtration : {
				            name:"r.actual_amount",
				            alias:"Actual Amount"
			              },
						  sort : "r.actual_amount"
					},
					// Expect Amount
					{ title : "Expect Amount",
						  dataField:"expectAmount",
						  filtration : {
				            name:"r.expect_amount",
				            alias:"Expect Amount"
			              },
						  sort : "r.expect_amount"
					},
					// Product Name
					{ title : "Product Name",
						  dataField:"productName",
						  filtration : {
				            name:"pd.product_name",
				            alias:"Product Name"
			              },
						  sort : "pd.product_name"
					},
					// Component Name
					{ title : "Component Name",
						  dataField:"componentName",
						  filtration : {
				            name:"pc.component_name",
				            alias:"Component Name"
			              },
						  sort : "pc.component_name"
					},
					// Item Type Name
					{ title : "Item Type Name",
						  dataField:"itemTypeName",
						  filtration : {
				            name:"it.item_type_name",
				            alias:"Item Type Name"
			              },
						  sort : "it.item_type_name"
					},
					// Item Name
					{ title : "Item Name",
						  dataField:"itemName",
						  filtration : {
				            name:"p.item_name",
				            alias:"Item Name"
			              },
						  sort : "p.item_name"
					},
					// Vendor Name
					{ title : "Vendor Name",
						  dataField:"vendorName",
						  filtration : {
				            name:"v.vendor_name",
				            alias:"Vendor Name"
			              },
						  sort : "v.vendor_name"
					},
					{title: "Download", 
							notDownload: 'Y', 
							dataField:function(o){
								return (o.attachmentPointId == "" ? "" : "<img src=\"include/images/download1.gif\" style=\"cursor: pointer;\" onclick=\"SANINCO.Download(" + o.attachmentPointId + ");\"/>");
							}
					}
			]
		});
	}
	
	rateDiscrepancyListPage.voParam = {
			auditReferenceTypeId : auditReferenceTypeId,
			auditReferenceId: auditReferenceId
	};
	
	rateDiscrepancyListPage.addTotalSuccessEvent(function(data){
		
		var refType = "";
		
		if(auditReferenceTypeId == 2){
			refType = "Tariff";
		} else if(auditReferenceTypeId == 3){
			refType = "Contract";
		} else if(auditReferenceTypeId == 5){
			refType = "Price List";
		}
		
		$('#reference_type_title')[0].innerHTML = "Audit Reference Type  :  " + refType;
		$('#rate_discrepancy_flag').show(); // 显示rate discrepancy 弹出窗口。
		$('body').css('overflow', 'hidden'); // 禁止窗口滚动条。
		
		if(data.totalResultNo > 0) {
			$('#download_button').show();
		}
	});
	
	rateDiscrepancy = new SANINCO.Filter();
	
	rateDiscrepancy.add("i.invoice_number", "String");
	rateDiscrepancy.add("i.account_number", "String"); 
	rateDiscrepancy.add("i.invoice_date", "String"); 
	rateDiscrepancy.add("p.circuit_number", "String");
	rateDiscrepancy.add("p.usoc", "String");
	rateDiscrepancy.add("p.usoc_description", "String"); 
	rateDiscrepancy.add("r.actual_amount", "Number"); 
	rateDiscrepancy.add("r.expect_amount", "Number");
	rateDiscrepancy.add("pd.product_name", "String");
	rateDiscrepancy.add("pc.component_name", "String");
	rateDiscrepancy.add("it.item_type_name", "String");
	rateDiscrepancy.add("p.item_name", "String");
	rateDiscrepancy.add("v.vendor_name", "String");
    
	rateDiscrepancy.addEditeEvent(function(){rateDiscrepancyListPage.start();});
	rateDiscrepancyListPage.setFilter(rateDiscrepancy);
	

	rateDiscrepancyListPage.start();
}

function saveRateDiscrepancyToExcel(){
	showLoadingModalLayer();
	var titles = rateDiscrepancyListPage.getTitlesParam("titles"); // 获取所有的列头。
	var url = actionUri.saveExcelBySearchRateDiscrepancy 
	+ "?" + titles +"&" 
	+ "searchContractTariffPriceListVO.auditReferenceTypeId = " + _auditReferenceTypeId + "&" 
	+ "searchContractTariffPriceListVO.auditReferenceId = " + _auditReferenceId;
	windowOpen(url);
	hideLoadingModalLayer();
}
