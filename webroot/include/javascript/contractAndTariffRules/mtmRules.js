



var actionUri = {
    countMtMRulesViewListPageNoUri: 'countMtMRulesViewListPageNo.action',
    listMtMRulesUri: 'listMtMRules.action',
    downloadMtMRulesToExcelUri: 'downloadMtMRulesToExcel.action'
};

$(function() {

    listMtMRules();
    // $('form').bind('keydown',contractEnterToStartSearch);

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
 * 将MtM Rules 页面中上半部分表单中的信息重置。
 * @return {[type]} [description]
 */
function resetContractFormElementValue() {

    // 清空dropdown List搜索条件。
    form0_keyField_dropdownList.setValue('all'); 
    form0_subProduct_dropdownList.setValue('all'); 
    
    form0_summaryVendorName_dropdownList.setValue('all'); 
    form0_vendorName_dropdownList.setValue('all'); 
    $('#charge-type-filter-list').val('all');

    // 清除form表单中所有的文本输入框或者是select复选框中的值。
    cleanFormElementValue();
}

function resetRadioButton() {}

/**
 * 获取 MtM Rules 列表。
 */
function listMtMRules() {

    MtMRulesViewListPage = new SANINCO.Page('mtm-rules-data-table',"MtMRulesViewListPage",{
        sortingField : "ruleID",
        sortingDirection : "asc",
        vo : "searchMtMRulesVO",
        autoToTop : true,
        recordText: 'MtM Search Results:',
        totalPageUri : actionUri.countMtMRulesViewListPageNoUri,
        dataUri : actionUri.listMtMRulesUri ,
        paginationDiv : "mtmRules-dataTable-page",
        recPerArray : [10,20,30,50,100,500,1000],
        cols : [ 
                {title: "Status", dataField: "rateStatus", sort : "rateStatus", filtration : {name:"rateStatus", alias:"Status"} },
                {title: "Effective Date", dataField: "rateEffectiveDate", sort : "rateEffectiveDate", filtration : {name:"rateEffectiveDate", alias:"Effective Date"} },
                {title: "Term", dataField: "term", sort : "term", filtration : {name:"term", alias:"Term"} },
                {title: "Rate", dataField: function(o) { return eliminateZeros(o, 'rate') }, sort : "rate", className: 'align-right' },
                {title: "Stripped Circuit Number", dataField: "strippedCircuitNumber", sort : "strippedCircuitNumber", filtration : {name:"strippedCircuitNumber", alias:"Stripped Circuit Number"} },
                {title: "USOC", dataField: "usoc", sort : "usoc", filtration : {name:"usoc", alias:"USOC"} },
                {title: "USOC Long Description", dataField: "usocDescription", sort : "usocDescription", filtration : {name:"usocDescription", alias:"USOC Description"} },
                {title: "Line Item Code", dataField: "lineItemCode", sort : "lineItemCode", filtration : {name:"lineItemCode", alias:"Line Item Code"} },
                {title: "Line Item Code Description", dataField: "lineItemCodeDescription", sort : "lineItemCodeDescription", filtration : {name:"lineItemCodeDescription", alias:"Line Item Code Description"} },
                {title: "Item Description", dataField: "itemDescription", sort : "itemDescription", filtration : {name:"itemDescription", alias:"Item Description"} },
                {title: "Summary Vendor Name", dataField: "summaryVendorName", sort : "summaryVendorName", filtration : {name:"summaryVendorName", alias:"Summary Vendor Name"} },
                {title: "Charge Type", dataField: "chargeType", sort : "chargeType", filtration : {name:"chargeType", alias:"Charge Type"} },
                {title: "Sub Product", dataField: "subProduct", sort : "subProduct", filtration : {name:"subProduct", alias:"Sub Product"} },
                {title: "Key Field", dataField: "keyField", sort : "keyField", filtration : {name:"keyField", alias:"Key Field"} }
            ] 
    });

    // 列表中的操作按钮和列表的显示控制逻辑
    MtMRulesViewListPage.addTotalSuccessEvent(function(data){
        if(data.totalResultNo < 0 || data.error){

            $('#downloadMtMRulesRecordsToExcel').css('display', 'none');
            $('#mtmRulesSearchResultTableWrapper').css('display', 'none');
        } else {

            if(data.totalResultNo == 0 ){
                $('#downloadMtMRulesRecordsToExcel').css('display', 'none');
            }else{
                $('#downloadMtMRulesRecordsToExcel').css('display', 'block');
            }

            $('#mtmRulesSearchResultTableWrapper').css('display', 'block');
        }
    });

    // 给 MtM Rules 列表中的字段添加过滤条件。
    addMtMRulesFilter(MtMRulesViewListPage);
    
}

/**
 * 添加 MtM Rules 过滤条件。
 */
function addMtMRulesFilter(startPage) {

    // 声明Filter 条件。
    MtMRulesViewListFilter = new SANINCO.Filter();

    // 添加Filter条件的编辑事件。
    MtMRulesViewListFilter.addEditeEvent( function(){startPage.start();} );

    MtMRulesViewListFilter.add("rateStatus", "String");
    MtMRulesViewListFilter.add("rateEffectiveDate", "String"); 
    MtMRulesViewListFilter.add("term", "String"); 
    MtMRulesViewListFilter.add("rate", "String"); 
    MtMRulesViewListFilter.add("strippedCircuitNumber", "String"); 
    MtMRulesViewListFilter.add("usoc", "String"); 
    MtMRulesViewListFilter.add("usocDescription", "String"); 
    MtMRulesViewListFilter.add("lineItemCode", "String");
    MtMRulesViewListFilter.add("lineItemCodeDescription", "String");
    MtMRulesViewListFilter.add("itemDescription", "String"); 
    MtMRulesViewListFilter.add("summaryVendorName", "String");
    MtMRulesViewListFilter.add("chargeType", "String");
    MtMRulesViewListFilter.add("subProduct", "String"); 
    MtMRulesViewListFilter.add("keyField", "String");

    MtMRulesViewListPage.addCompleteEvent(function(o){
        $(window).scrollTop(0);
    });

    // 将Filter 条件应用到列表中。
    startPage.setFilter(MtMRulesViewListFilter);
}

/**
 * 查询并显示列表
 * @return {[type]} [description]
 */
function searchMtMRulesList() {
    // 验证表单中的内容是否有效。
    if( !validateFields() ) return;

    // 重置过滤条件。
    MtMRulesViewListFilter.clearAll();

    MtMRulesViewListPage.myParam = composePostDataFromUi();

    $('.search-module select').css('visibility', 'visible');
   
    // MtM Rules 列表的搜索动作。（开启）
    MtMRulesViewListPage.start();

}

/**
 * 组合查询条件的功能。
 * @return {[type]} [description]
 */
function composePostDataFromUi() {

    var postData = '';

    // Dropdown list filters.
    
    // Key Field
    var keyField = encodeURIComponent(form0_keyField_dropdownList.getValue());

    // Sub Product
    var subProduct = encodeURIComponent(form0_subProduct_dropdownList.getValue());

    // Charge Type
    var chargeType = encodeURIComponent($('#charge-type-filter-list').val().trim());


    // Summary Vendor Name.
    var summaryVendorName = encodeURIComponent( form0_summaryVendorName_dropdownList.getValue());
    
    /*Text field filters*/

    // USOC.
    var usoc = encodeURIComponent( $('.usoc').val().trim() );

    // USOC Description.
    var usocDescription = encodeURIComponent( $('.usoc-description').val().trim() );

    // Item Description
    var itemDescription = encodeURIComponent( $('.item-description').val().trim() );

    // Line item code
    var lineItemCode = encodeURIComponent( $('.line-item-code').val().trim() );

    // Line item code description
    var lineItemCodeDescription = encodeURIComponent( $('.line-item-code-description').val().trim() );
    
    // Stripped Circuit Number
    var strippedCircuitNumber = encodeURIComponent( $('.stripped-circuit-number').val().trim() );
    // Rate
    var rate = encodeURIComponent( $('.rate').val().trim().replace(',', '') );
    // Rate Effective Date
    var rateEffectiveDate = encodeURIComponent( $('.rate-effective-date').val().trim() );
    

    // rate Status
    var rateStatus = encodeURIComponent($('#rate-status-filter-list').val().trim());


    var tariffRulesFilterCause = MtMRulesViewListFilter.getCause();

    if ( !isEmptyStringValue(usoc) ) { 
        postData += 'searchMtMRulesVO.usoc=' + usoc + '&';
    }

    if ( !isEmptyStringValue(usocDescription) ) { 
        postData += 'searchMtMRulesVO.usocDescription=' + usocDescription + '&';
    }
    
    if ( !isDropdownListAllValue(rateStatus) ) { 
    	postData += 'searchMtMRulesVO.rateStatus=' + rateStatus + '&';
    }

    if ( !isEmptyStringValue(itemDescription) ) {
        postData += 'searchMtMRulesVO.itemDescription=' + itemDescription + '&';
    }

    if ( !isEmptyStringValue(lineItemCode) ) {
        postData += 'searchMtMRulesVO.lineItemCode=' + lineItemCode + '&';
    }

    

    if ( !isEmptyStringValue(lineItemCodeDescription) ) {
        postData += 'searchMtMRulesVO.lineItemCodeDescription=' + lineItemCodeDescription + '&';
    }

    /* Dropdown list value vertification. */
    
    if ( !isDropdownListAllValue(summaryVendorName) ) {
        postData += 'searchMtMRulesVO.summaryVendorName=' + summaryVendorName + '&';
    }

    if ( !isDropdownListAllValue(keyField) ) {
        postData += 'searchMtMRulesVO.keyField=' + keyField + '&';
    }

    if ( !isDropdownListAllValue(subProduct) ) {
        postData += 'searchMtMRulesVO.subProduct=' + subProduct + '&';
    }

    if ( !isDropdownListAllValue(chargeType) ) {
        postData += 'searchMtMRulesVO.chargeType=' + chargeType + '&';
    }
    
    if ( !isEmptyStringValue(strippedCircuitNumber) ) {
    	postData += 'searchMtMRulesVO.strippedCircuitNumber=' + strippedCircuitNumber + '&';
    }
    
    if ( !isEmptyStringValue(rate) ) {
    	postData += 'searchMtMRulesVO.rate=' + rate + '&';
    }
    
    if ( !isEmptyStringValue(rateEffectiveDate) ) {
    	postData += 'searchMtMRulesVO.rateEffectiveDate=' + rateEffectiveDate + '&';
    }



    if ( !isEmptyStringValue(tariffRulesFilterCause) ) {
        postData += 'searchMtMRulesVO.filter=' + tariffRulesFilterCause + '&';
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
 * 下载MtM Rules list
 */
function downloadMtMRulesRecordsToExcel() {

    // 使用substring 方法截取字符串是因为字符串的前面会多出一个
    // '&', 因为checkbox 复选框并不会下载到 Excel文件中
    // cols 属性中的 notDownload: 'Y', 就不会添加到titles中，
    // 这样就多出一个 '&',  因此需要这样的截取。
    var titles = MtMRulesViewListPage.getTitlesParam("titles"); // 获取所有的列头。
    var listPageParams = "";
    var paramStr = MtMRulesViewListPage.paramStr;

    // 去掉字符串结尾的 '&' 符号。
    listPageParams = paramStr.substring(0, paramStr.length - 1 );

    // 如果有 '%' 需要首先对其进行转义。
    windowOpen(actionUri.downloadMtMRulesToExcelUri + "?"+ titles.replace(/%/g, "%25").replace(/#/g, "%23") + "&" + listPageParams);
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
