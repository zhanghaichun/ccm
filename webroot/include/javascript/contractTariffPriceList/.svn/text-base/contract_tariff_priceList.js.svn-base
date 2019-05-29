

// 标记是否是erpiry date contract 的查询。
// 当点击 See details 链接的时候， 此flag置为true,
// 当点击 Search 按钮之后，此 flag 设置为 false
var isExpiryDateContract = false;

// Save query.
var saveQuicklinkUri = "saveContractTariffPriceListSearch.action";

// 根据 expiry date 条件来查询 contracts 的个数
actionUri.getContractCountsByExpiryDateUri ="getContractCountsByExpiryDate.action";

YAHOO.util.Event.onDOMReady(function () {
	getExpiryDateContractCount(); // 获取过期的合同的个数。

	getContractList();

	getTariffList();

	getPriceListList();

	
});

/**
 * 开始查询 Contract / Tariff / Price List 列表。
 * (页面上Search 按钮的功能)
 * @return {[type]} [description]
 */
function startSearch() {
	if( !validateFields() ) return;

	isExpiryDateContract = false;
	
	// 重置过滤条件。
	ContractViewListFilter.clearAll();
	TariffViewListFilter.clearAll();
	PriceListViewListFilter.clearAll();

	// 这里的搜索是非 expiry date contract 的搜索。
	ContractViewListPage.myParam = composePostDataFromUi(CONTRACT_FILTER_TYPE, false);
	TariffViewListPage.myParam = composePostDataFromUi(TARIFF_TILTER_TYPE, false);
	PriceListViewListPage.myParam = composePostDataFromUi(PRICE_LIST_FILTER_TYPE, false);
	
	/*Contract*/
	ContractViewListPage.start();

	/*Tariff*/
	TariffViewListPage.start();

	/* Price List */
	PriceListViewListPage.start();
}

/**
 * 保存查询的结构到左侧的quick link 标签中。
 * (页面中搜索条件表单中 Save 按钮的功能。)
 * @override
 * @see  quicklink.js
 * @return {[type]} [description]
 */
function saveSearch(){
	if(!validateFields()) return;
	var callback = {
		success: renderQuickLink,
		failure: showError
	};

	var postData = "";
	var composePostData = "";

	if (!isExpiryDateContract) {
		composePostData += composePostDataFromUi(null, false);
	} else {
		composePostData += composePostDataFromUi(null, true);
	}

	postData = "queryString="+composePostData.replace(/&/g, "%26")+"&quicklinkName=" + ccmEscape(queryName.value);
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
	
	//if(data.mark){return false;}
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
				if (v != "true") { // 这个判断是重写的逻辑
					$(f0[m]).val(v);
					var f_ = true;
					break;
				}
				
			}
		}

		if(f_){
			continue;
		}

	}
	updateRadioButton();
	if ( v == "true" ){ // 这个判断是重写的逻辑
		getExpiringContracts();
		
	} else {
		startSearch();
	}
}


/**
 * 将Contract/Tariff/Price List 页面中上半部分表单中的信息重置。
 * (页面上Clear 按钮的功能。)
 * @return {[type]} [description]
 */
function resetFormElementValue() {

	// 重置搜索条件。
	form0_contractTariffPriceList_vendorId.setValue('all'); // Vendor
	form0_contractTariffPriceList_banId.setValue('all'); // Ban
	form0_contractTariffPriceList_productName.setValue('all'); // Product
	form0_contractTariffPriceList_productComponentName.setValue('all'); // Product Component

	// 清除form表单中所有的文本输入框或者是select复选框中的值。
	cleanFormElementValue();

}

/**
 * 拼接各个搜索条件。
 */
function composePostDataFromUi(filterType, isExpiryDateContract) {
	postData = "";

	var contractFilterCause = ContractViewListFilter.getCause(); // Contract Filter
	var tariffFilterCause = TariffViewListFilter.getCause(); // Tariff Filter
	var priceListFilterCause = PriceListViewListFilter.getCause(); // Price List Filter

	if (isExpiryDateContract) {
		postData += "searchContractTariffPriceListVO.isExpiryDateContract=true&";
	} else {
		postData += formCommonPostData();
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
 * form 表单中的正常条件的拼接。
 * @return {String} 拼接后的正常的需要传递到后台中的表单中
 * 需要填写值的字符串。（需要正常填写的条件和 expiry date contract的查询条件应该
 * 分开，两种条件是互斥的。）
 */
function formCommonPostData() {

	var commonPostData = "";

	var fileName = $('.file-name').val().trim(); // File Name

	var vendorId = form0_contractTariffPriceList_vendorId.getValue(); // Vendor
	var banId = form0_contractTariffPriceList_banId.getValue(); // Ban

	var circuitNumber = stripCircuitNumber( $('.circuit-number').val().trim() ); // Circuit Number

	var productName = form0_contractTariffPriceList_productName.getValue(); // Product
	var productComponentName = form0_contractTariffPriceList_productComponentName.getValue(); // Product Component

	var usoc = $('.usoc').val().trim(); // USOC


	if ( !isEmptyStringValue(fileName) ) { // File Name
		commonPostData += 'searchContractTariffPriceListVO.fileName=' + fileName + '&';
	}

	if ( vendorId !== 'all' ) { // Vendor
		commonPostData += 'searchContractTariffPriceListVO.vendorId=' + vendorId + '&';
	}

	if ( banId !== 'all' ) { // Ban
		commonPostData += 'searchContractTariffPriceListVO.banId=' + banId + '&';
	}

	if ( !isEmptyStringValue(circuitNumber) ) { // Circuit Number
		commonPostData += 'searchContractTariffPriceListVO.circuitNumber=' + circuitNumber + '&';
	}

	if ( productName !== 'all' ) { // Product
		commonPostData += 'searchContractTariffPriceListVO.productName=' + productName + '&';
	}

	if ( productComponentName !== 'all' ) { // Product Component
		commonPostData += 'searchContractTariffPriceListVO.productComponentName=' + productComponentName + '&';
	}

	if ( !isEmptyStringValue(usoc) ) { // USOC
		commonPostData += 'searchContractTariffPriceListVO.usoc=' + usoc + '&';
	}

	return commonPostData;
}



/**
 * 通过获取过期的contract 的相关信息，
 * 来重新渲染Contract 列表。
 *
 * 因为在点击 See details 链接的时候， 表单中的查询条件是需要全部清除的，
 * 因此如果先通过查询条件查询出来结果，
 * 之后在点击 See details 链接， 之后点击save按钮， 那么就不能将先前输入的条件保存起来了。
 * 因此需要重新查询 Tariff 和 Price List 表中的数据。
 * @return {[type]} [description]
 */
function getExpiringContracts() {
	resetFormElementValue(); // 清除页面中的搜索条件。
	isExpiryDateContract = true;

	ContractViewListFilter.clearAll();

	/* 这里是 expiry date contract 条件的搜索，但是只有contract 列表需要这样的条件。 */

	ContractViewListPage.myParam = composePostDataFromUi(CONTRACT_FILTER_TYPE, true); // Expiry Date Contracts.
	ContractViewListPage.start();

	// 当查询Expiry Date Contract的时候，
	// 禁止显示Tariff和Price List列表。
	$('#tariff-wrapper').hide();
	$('#priceList-wrapper').hide();

}

/**
 * POST 请求，返回已经过期的合同的个数。
 */
function getExpiryDateContractCount() {

	$.ajax({
		url: actionUri.getContractCountsByExpiryDateUri,
		type: 'POST',
		data: "searchContractTariffPriceListVO.isExpiryDateContract=true",
		dataType: 'json',
		success: function(o) {
			if ( o.expiryDateContractCount > 0 ) {
				// 添加过期的合同的数目的html结构。
				addExpiryDateContractStructure(o.expiryDateContractCount);
			}
			
		}
	});
}

/**
 * 添加过期的合同的数目的html 结构。
 */
function addExpiryDateContractStructure(expiryDateContractCount) {

	var countStructure = '';
	countStructure += '<div class="form-prompt-message expiry-contract">';
	countStructure += ' <i class="expiry-contract-icon"></i>';

	if ( expiryDateContractCount !== 1 ) { // 区分单数和复数时候的语法。
		countStructure += '<span class="expiry-contract-count">'+ expiryDateContractCount +'</span> of contracts are expiring!';
	} else {
		countStructure += '<span class="expiry-contract-count">'+ expiryDateContractCount +'</span> of contract is expiring!';
	}
	


	countStructure += '<input value="See details" type="button" name="searchContractTariffPriceListVO.isExpiryDateContract"  class="see-contracts-detail" onclick="getExpiringContracts()">';
	countStructure += '</div>';

	$('#form0').prepend(countStructure);
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
	if(k == "searchContractTariffPriceListVO.vendorId"){ // Vendor
		form0_contractTariffPriceList_vendorId.setValue(v);
		return true;
	}
	
	if(k == "searchContractTariffPriceListVO.banId"){ // Ban
		form0_contractTariffPriceList_banId.setValue(v);
		return true;
	}

	if(k == "searchContractTariffPriceListVO.productName"){ // Product
		form0_contractTariffPriceList_productName.setValue(v);
		return true;
	}

	if(k == "searchContractTariffPriceListVO.productComponentName"){ // Product Component
		form0_contractTariffPriceList_productComponentName.setValue(v);
		return true;
	}
	return false;
}

/**
 *  当点击quick link 链接的时候，清除下拉列表中保存的值。
 * @return {[type]} [description]
 */
function clearJSelectQuicklink(){
	form0_contractTariffPriceList_vendorId.setValue("all");
	form0_contractTariffPriceList_banId.setValue("all");
	form0_contractTariffPriceList_productName.setValue("all");
	form0_contractTariffPriceList_productComponentName.setValue("all");
}


/**
 * 验证搜索表单中的各个字段的输入内容
 * 是否符合要求
 */
function validateFields() {
	var formatValid = FIC_checkForm("form0");
	return formatValid;
}

/**
 * 这个方法的作用就是当使用日期控件(calendar controller)的时候，
 * 当不同的文本框获取焦点的时候，radio button 之间的转换，
 * #cleanFormElementValue() 方法是定义到quicklink.js文件中，
 * 这个方法的内部调用了 resetRadioButton() 这个方法，
 * 所以这里需要定义。
 * @return {[type]} [description]
 */
function resetRadioButton(){}

function updateRadioButton(){
	
}

function updateVendorAndBanTextfield(){
	
}