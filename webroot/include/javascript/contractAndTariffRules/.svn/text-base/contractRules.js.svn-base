/*
 替换 circuit_number 中的特殊字符串，
 使之变成 stripped_circuit_number => str.replace(/[\(\)\.\:_\\\/\s-]/g, '');
 */
var actionUri = {
    countContractRulesViewListPageNoUri: 'countContractRulesViewListPageNo.action',
    listContractRulesUri: 'listContractRules.action',
    downloadContractRulesToExcelUri: 'downloadContractRulesToExcel.action'
};

$(function() {

    listContractRules();

    listContractRulesSummary();

    searchContractRulesList();

    switchDetailsAndSummary();
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

/* Details Tab 和 Summary Tab 的切换功能。 */
function switchDetailsAndSummary() {


    $('#summary-and-details-nav  li').click(function() {

        $('#summary-and-details-nav  li').each(function() { $(this).removeClass('selected'); }); // 去除选中状态

        $(this).addClass('selected');

        var idAttrValue = $(this).attr('id');

        if ( $(this).attr('id') == 'contractRulesDetails' ) { // Details Tab

            $('.summary-content').hide();
            $('.details-content').show();

        } else if ( $(this).attr('id') == 'contractRulesSummary' ) { // Summary Tab

            $('.details-content').hide();
            $('.summary-content').show();
        }

    });
}



/**
 * 将contract rules页面中上半部分表单中的信息重置。
 * @return {[type]} [description]
 */
function resetContractFormElementValue() {

    // 清空dropdown List搜索条件。
    form0_summaryVendorName_dropdownList.setValue('all'); 
    form0_keyField_dropdownList.setValue('all'); 
    form0_subProduct_dropdownList.setValue('all'); 
    $('#contract-name-filter-list').val('all');

    // 清除form表单中所有的文本输入框或者是select复选框中的值。
    cleanFormElementValue();
}

function resetRadioButton() {}


/**
 * 显示 simple canlendar 控件。
 * @param  {[type]} textboxId      calendar textbox 的 id 属性值。
 */
function showCalendarController(textboxId) {

    g_Calendar.show(event, textboxId, false, 'yyyy/mm/dd', new Date(1990,1,1));
    return false;
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
 * 获取 Contract Rules 列表。
 */
function listContractRules() {

    ContractRulesViewListPage = new SANINCO.Page('contract-rules-data-table',"ContractRulesViewListPage",{
        sortingField : "arm.id",
        sortingDirection : "asc",
        vo : "searchContractRulesVO",
        autoToTop : true,
        recordText: 'Contract Rules Search Results:',
        totalPageUri : actionUri.countContractRulesViewListPageNoUri,
        dataUri : actionUri.listContractRulesUri ,
        paginationDiv : "contract-rules-dataTable-page",
        recPerArray : [10,20,30,50,100,500,1000],
        cols : [
                {title: "Effective Date", dataField: "rateEffectiveDate", sort : "cf.effective_date", filtration : {name:"cf.effective_date", alias:"Effective Date"} },
                {title: "Term (Months)", dataField: "termQuantity", sort : "cf.term_quantity", filtration : {name:"cf.term_quantity", alias:"Term (Months)"} },
                {title: "Expiration Period", dataField: "expirationPeriod" },

                {title: "Early Termination Fee", dataField: "penaltyInitialPercent", sort : "cf.penalty_initial_percent", filtration : {name:"cf.penalty_initial_percent", alias:"Early Termination Fee"} },
                {title: "Rate", dataField: function(o) { return eliminateZeros(o, 'rate') }, sort : "c.rate", className: 'align-right' },

                {title: "MMBC", dataField: 'mmbc' ,sort : "c.mmbc", filtration : {name:"c.mmbc", alias:"MMBC"}},
                {title: "Discount", dataField: function(o) { return eliminateZeros(o, 'discount') } , sort : "c.discount",filtration : {name:"c.discount", alias:"Discount"}, className: 'align-right' },
                {title: "Contract Name", dataField: "contractName", sort : "cf.contract_number", filtration : {name:"cf.contract_number", alias:"Contract Name"} },

                {title: "Contract Service Schedule Name", dataField: "schedule", sort : "c.schedule", filtration : {name:"c.schedule", alias:"Contract Service Schedule Name"} },
                {title: "Stripped Circuit Number", dataField: "circuitNumber", sort : "arm.circuit_number", filtration : {name:"arm.circuit_number", alias:"Stripped Circuit Number"} },
                {title: "USOC", dataField: "usoc", sort : "arm.usoc", filtration : {name:"arm.usoc", alias:"USOC"} },

                {title: "USOC Long Description", dataField: "usocLongDescription", sort : "arm.usoc_description", filtration : {name:"arm.usoc_description", alias:"USOC Long Description"} },
                {title: "Line Item Code", dataField: "lineItemCode", sort : "arm.line_item_code", filtration : {name:"arm.line_item_code", alias:"Line Item Code"} },

                {title: "Line Item Code Description", dataField: "lineItemCodeDescription", sort : "arm.line_item_code_description", filtration : {name:"arm.line_item_code_description", alias:"Line Item Code Description"} },
                {title: "Item Description", dataField: "itemDescription", sort : "arm.item_description", filtration : {name:"arm.item_description", alias:"Item Description"} },
                {title: "Summary Vendor Name", dataField: "summaryVendorName", sort : "arm.summary_vendor_name", filtration : {name:"arm.summary_vendor_name", alias:"Summary Vendor Name"} },

                {title: "Sub Product", dataField: "subProduct", sort : "arm.sub_product", filtration : {name:"arm.sub_product", alias:"Sub Product"} },
                {title: "Key Field", dataField: "keyField", sort : "arm.key_field_original", filtration : {name:"arm.key_field_original", alias:"Key Field"} }

            ] 
    });

    // 列表中的操作按钮和列表的显示控制逻辑
    ContractRulesViewListPage.addTotalSuccessEvent(function(data){
        if(data.totalResultNo < 0 || data.error){

            $('#downloadContractRulesRecordsToExcel').css('display', 'none');
            $('#contractRulesSearchResultTableWrapper').css('display', 'none');
        } else {

            if(data.totalResultNo == 0 ){
                $('#downloadContractRulesRecordsToExcel').css('display', 'none');
            }else{
                $('#downloadContractRulesRecordsToExcel').css('display', 'block');
            }

            $('#contractRulesSearchResultTableWrapper').css('display', 'block');
        }
    });

    // 给Contract Rules 列表中的字段添加过滤条件。
    addContractRulesFilter(ContractRulesViewListPage);
    
}


function listContractRulesSummary() {


}


/**
 * 添加 Contract Rules 列表中的过滤条件
 */
function addContractRulesFilter(startPage) {

    // 声明Filter 条件。
    ContractRulesViewListFilter = new SANINCO.Filter();

    // 添加Filter条件的编辑事件。
    ContractRulesViewListFilter.addEditeEvent( function(){startPage.start();} );


    ContractRulesViewListFilter.add("cf.effective_date", "String");
    ContractRulesViewListFilter.add("cf.term_quantity", "Integer");

    ContractRulesViewListFilter.add("cf.penalty_initial_percent", "Double");
    ContractRulesViewListFilter.add("c.rate", "Double");
    ContractRulesViewListFilter.add("c.mmbc", "String");
    ContractRulesViewListFilter.add("c.discount", "Double");
    ContractRulesViewListFilter.add("cf.contract_number", "String"); // Contract name.
    ContractRulesViewListFilter.add("c.schedule", "String"); // Schedule name.
    ContractRulesViewListFilter.add("arm.circuit_number", "String");
    ContractRulesViewListFilter.add("arm.usoc", "String");

    ContractRulesViewListFilter.add("arm.usoc_description", "String");
    ContractRulesViewListFilter.add("arm.line_item_code", "String");

    ContractRulesViewListFilter.add("arm.line_item_code_description", "String");
    ContractRulesViewListFilter.add("arm.item_description", "String");
    ContractRulesViewListFilter.add("arm.sub_product", "String");
    ContractRulesViewListFilter.add("arm.summary_vendor_name", "String");
    ContractRulesViewListFilter.add("arm.key_field_original", "String");


    ContractRulesViewListPage.addCompleteEvent(function(o){
        $(window).scrollTop(0);
    });

    // 将Filter 条件应用到列表中。
    startPage.setFilter(ContractRulesViewListFilter);
}

/**
 * 查询并显示列表
 * @return {[type]} [description]
 */
function searchContractRulesList() {

    debugger;

    // 验证表单中的内容是否有效。
    if( !validateFields() || !validateDateFields() ) return;

    // 重置过滤条件。
    ContractRulesViewListFilter.clearAll();
    ContractRulesViewListPage.myParam = composePostDataFromUi();

    $('.search-module select').css('visibility', 'visible');

   
    // Contract Rules 列表的搜索动作。（开启）
    ContractRulesViewListPage.start();

}

/**
 * 组合查询条件的功能。
 * @return {[type]} [description]
 */
function composePostDataFromUi() {

    var postData = '';

    /*Dropdown list filters*/
    
    // Summary Vendor Name
    var summaryVendorName = encodeURIComponent(form0_summaryVendorName_dropdownList.getValue()); 
    
    // Key Field
    var keyField = encodeURIComponent( form0_keyField_dropdownList.getValue() );

    // Sub Product
    var subProduct = encodeURIComponent(form0_subProduct_dropdownList.getValue());

    // Contract Name
    var contractName = encodeURIComponent($('#contract-name-filter-list').val().trim());

    /*Text field filters*/

    // USOC.
    var usoc = encodeURIComponent( $('.usoc').val().trim() );

    // USOC long description.
    var usocLongDescription = encodeURIComponent( $('.usoc-long-description').val().trim() );

    // Rate
    var rate = encodeURIComponent( $('.rate').val().trim().replace(',', '') );

    // Effective Date
    var effectiveDate = encodeURIComponent( $('#rateEffectiveDate').val().trim() );

    // Stripped circuit number.
    var strippedCircuitNumber = encodeURIComponent( $('.stripped-circuit-number').val().trim().replace(/[\(\)\.\:_\\\/\s-]/g, '') );

    // Item Description
    var itemDescription = encodeURIComponent( $('.item-description').val().trim() );

    // Line item code
    var lineItemCode = encodeURIComponent( $('.line-item-code').val().trim() );

    // Line item code description
    var lineItemCodeDescription = encodeURIComponent( $('.line-item-code-description').val().trim() );

    // Expiration Period
    var expirationPeriod = encodeURIComponent( $('.expiration-period').val() );
    


    var contractRulesFilterCause = ContractRulesViewListFilter.getCause();

    if ( !isEmptyStringValue(usoc) ) { 
        postData += 'searchContractRulesVO.usoc=' + usoc + '&';
    }

    if ( !isEmptyStringValue(usocLongDescription) ) { 
        postData += 'searchContractRulesVO.usocLongDescription=' + usocLongDescription + '&';
    }

    if ( !isEmptyStringValue(rate) ) { 
        postData += 'searchContractRulesVO.rate=' + rate + '&';
    }

    if ( !isEmptyStringValue(effectiveDate) ) { 
        postData += 'searchContractRulesVO.effectiveDate=' + effectiveDate + '&';
    }

    if ( !isEmptyStringValue(strippedCircuitNumber) ) {
        postData += 'searchContractRulesVO.strippedCircuitNumber=' + strippedCircuitNumber + '&';
    }

    if ( !isEmptyStringValue(itemDescription) ) {
        postData += 'searchContractRulesVO.itemDescription=' + itemDescription + '&';
    }

    if ( !isEmptyStringValue(lineItemCode) ) {
        postData += 'searchContractRulesVO.lineItemCode=' + lineItemCode + '&';
    }

    if ( !isEmptyStringValue(lineItemCodeDescription) ) {
        postData += 'searchContractRulesVO.lineItemCodeDescription=' + lineItemCodeDescription + '&';
    }

    if ( !isEmptyStringValue(expirationPeriod) ) {
        postData += 'searchContractRulesVO.expirationPeriod=' + expirationPeriod + '&';
    }

    /* Dropdown list value vertification. */
    if ( !isDropdownListAllValue(summaryVendorName) ) {
        postData += 'searchContractRulesVO.summaryVendorName=' + summaryVendorName + '&';
    }

    if ( !isDropdownListAllValue(keyField) ) {
        postData += 'searchContractRulesVO.keyField=' + keyField + '&';
    }

    if ( !isDropdownListAllValue(subProduct) ) {
        postData += 'searchContractRulesVO.subProduct=' + subProduct + '&';
    }

    if ( !isDropdownListAllValue(contractName) ) {
        postData += 'searchContractRulesVO.contractName=' + contractName + '&';
    }



    if ( !isEmptyStringValue(contractRulesFilterCause) ) { // Contract Filter
        postData += 'searchContractRulesVO.filter=' + contractRulesFilterCause + '&';
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
 * 下载Contract Rules list
 */
function downloadContractRulesRecordsToExcel() {

    // 使用substring 方法截取字符串是因为字符串的前面会多出一个
    // '&', 因为checkbox 复选框并不会下载到 Excel文件中
    // cols 属性中的 notDownload: 'Y', 就不会添加到titles中，
    // 这样就多出一个 '&',  因此需要这样的截取。
    var titles = ContractRulesViewListPage.getTitlesParam("titles"); // 获取所有的列头。
    var listPageParams = "";
    var paramStr = ContractRulesViewListPage.paramStr;

    // 去掉字符串结尾的 '&' 符号。
    listPageParams = paramStr.substring(0, paramStr.length - 1 );

    // 如果有 '%' 需要首先对其进行转义。
    windowOpen(actionUri.downloadContractRulesToExcelUri + "?"+ titles.replace(/%/g, "%25").replace(/#/g, "%23") + "&" + listPageParams);
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
