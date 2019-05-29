var actionUri = {
	getPricelistViewListPageNoUri: 'getPricelistViewListPageNo.action',
	searchPricelistListUri: 'searchPricelistList.action',
	downloadPricelistToExcelUri: 'downloadPricelistToExcel.action'
};

$(function() {
	getPricelistList();
	$('form').bind('keydown',contractPricelistEnterToStartSearch);
})
function contractPricelistEnterToStartSearch() {
	
	// key code => 13 的按键是Enter键， 系统中的ssearch方法应该
	// 定义为startSearch.
	if( window.event.keyCode == 13) {
		
		if (window.searchContract) {
			searchPricelist();

		} 
		
	}
}

/**
 * 将pricelist 页面中上半部分表单中的信息重置。
 * @return {[type]} [description]
 */
function resetPricelistFormElementValue() {

	// 清空dropdown List搜索条件。
	form0_searchPricelistVO_band.setValue('all'); // Band dropdown list.
	form0_searchPricelistVO_serviceType.setValue('all'); // Service Type dropdown list. 
	

	// 清除form表单中所有的文本输入框或者是select复选框中的值。
	cleanFormElementValue();
}

function resetRadioButton() {}

/**
 * 渲染 price list 列表
 * @return {[type]} [description]
 */
function getPricelistList() {

	PricelistViewListPage = new SANINCO.Page('pricelist-data-table',"PricelistViewListPage",{
		sortingField : "pl.id",
	    sortingDirection : "asc",
	    vo : "searchPricelistVO",
		autoToTop : true,
		recordText: 'Pricelist Search Results:',
	    totalPageUri : actionUri.getPricelistViewListPageNoUri,
	    dataUri : actionUri.searchPricelistListUri ,
		paginationDiv : "pricelist-dataTable-page",
		recPerArray : [10,20,30,50,100,500,1000],
		cols : [{title: "File Name", dataField: "fileName", sort : "plf.price_list_name", filtration : {name:"plf.price_list_name", alias:"File Name"}   },
		        {title: "Service Type", dataField: "serviceType", sort : "pl.service_type", filtration : {name:"pl.service_type", alias:"Service Type"   }},
		        {title: "Band", dataField: "band", sort : "pl.band", filtration : {name:"pl.band", alias:"Band"   }},
			   {title: "Effective Date", dataField: "effectiveDate", sort : "pl.effective_date", filtration : {name:"pl.effective_date", alias:"Effective Date"   }}
			]
	});

	// 列表中的操作按钮和列表的显示控制逻辑
	PricelistViewListPage.addTotalSuccessEvent(function(data){
		if(data.totalResultNo < 0 || data.error){

			$('#downloadPricelistRecordsToExcel').css('display', 'none');
			$('#pricelist-wrapper').css('display', 'none');
		} else {

			if(data.totalResultNo == 0 ){
				$('#downloadPricelistRecordsToExcel').css('display', 'none');
			}else{
				$('#downloadPricelistRecordsToExcel').css('display', 'block');
			}

			$('#pricelist-wrapper').css('display', 'block');
		}
	});

	// 给 Pricelist 列表中的字段添加过滤条件。
	addPricelistFilter(PricelistViewListPage);

}


/**
 * Pricelist 的过滤条件的添加动作。
 */
function addPricelistFilter(startPage) {

	// 声明Filter 条件。
	PricelistViewListFilter = new SANINCO.Filter();

	// 添加Filter条件的编辑事件。
	PricelistViewListFilter.addEditeEvent( function(){startPage.start();} );

	PricelistViewListFilter.add("plf.price_list_name", "String"); // File Name
	PricelistViewListFilter.add("pl.service_type", "String"); // Service  Type
	PricelistViewListFilter.add("pl.band", "String"); // Band
	PricelistViewListFilter.add("pl.effective_date", "String"); // Effective Date.
	

	PricelistViewListPage.addCompleteEvent(function(o){
		$(window).scrollTop(0);
	});

	// 将Filter 条件应用到列表中。
	startPage.setFilter(PricelistViewListFilter);
}


/**
 * 点击search按钮查询pricelist列表。
 * @return {[type]} [description]
 */
function searchPricelist() {

	// 验证表单中的内容是否有效。
	if( !validateFields() || !validateDateFields() ) return;

	// 重置过滤条件。
	PricelistViewListFilter.clearAll();

	PricelistViewListPage.myParam = composePostDataFromUi();
	
	// Pricelist 列表的搜索动作。（开启）
	PricelistViewListPage.start();
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
 * 组合列表搜索过滤条件
 * @return {[type]} [description]
 */
function composePostDataFromUi() {

	var postData = '';

	var fileName = encodeURIComponent( $('.file-name').val().trim() );

	var serviceType = form0_searchPricelistVO_serviceType.getValue();
	var band = form0_searchPricelistVO_band.getValue();

	var effectiveDate = $('#searchPricelistVO_effectiveDate').val();

	var pricelistFilterCause = PricelistViewListFilter.getCause(); // Pricelist Filter

	if ( fileName !== '' ) {
		postData += 'searchPricelistVO.fileName=' + fileName + '&';
	}

	if ( serviceType !== 'all' ) {
		postData += 'searchPricelistVO.serviceType=' + serviceType + '&';
	}

	if ( band !== 'all' ) {
		postData += 'searchPricelistVO.modeOfBand=' + band + '&';
	}

	if ( effectiveDate !== '' ) {
		postData += 'searchPricelistVO.effectiveDate=' + effectiveDate + '&';
	}


	if ( pricelistFilterCause !== '' ) {
		postData += 'searchPricelistVO.filter=' + pricelistFilterCause + '&';
	}

	return postData;
}

/**
 * 下载 price list.
 */
function downloadPricelistRecordsToExcel() {

	// 使用substring 方法截取字符串是因为字符串的前面会多出一个
	// '&', 因为checkbox 复选框并不会下载到 Excel文件中
	// cols 属性中的 notDownload: 'Y', 就不会添加到titles中，
	// 这样就多出一个 '&',  因此需要这样的截取。
	var titles = PricelistViewListPage.getTitlesParam("titles"); // 获取所有的列头。
	var listPageParams = "";
	var paramStr = PricelistViewListPage.paramStr;

	// 去掉字符串结尾的 '&' 符号。
	listPageParams = paramStr.substring(0, paramStr.length - 1 );

	
	windowOpen(actionUri.downloadPricelistToExcelUri + "?"+ titles + "&" + listPageParams);
}