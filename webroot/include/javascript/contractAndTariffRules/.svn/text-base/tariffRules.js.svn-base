



var actionUri = {
    countTariffRulesViewListPageNoUri: 'countTariffRulesViewListPageNo.action',
    listTariffRulesUri: 'listTariffRules.action',
    downloadTariffRulesToExcelUri: 'downloadTariffRulesToExcel.action'
};

$(function() {

    listTariffRules();
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
 * 将tariff Rules 页面中上半部分表单中的信息重置。
 * @return {[type]} [description]
 */
function resetContractFormElementValue() {

    // 清空dropdown List搜索条件。
    form0_keyField_dropdownList.setValue('all'); 
    form0_subProduct_dropdownList.setValue('all'); 
    
    form0_summaryVendorName_dropdownList.setValue('all'); 
    form0_vendorName_dropdownList.setValue('all');
    form0_tariffName_dropdownList.setValue('all'); 
    $('#charge-type-filter-list').val('all');

    // 清除form表单中所有的文本输入框或者是select复选框中的值。
    cleanFormElementValue();
}

function resetRadioButton() {}

/**
 * 获取 Tariff Rules 列表。
 */
function listTariffRules() {

    TariffRulesViewListPage = new SANINCO.Page('tariff-rules-data-table',"TariffRulesViewListPage",{
        sortingField : "arm.id",
        sortingDirection : "asc",
        vo : "searchTariffRulesVO",
        autoToTop : true,
        recordText: 'Tariff Search Results:',
        totalPageUri : actionUri.countTariffRulesViewListPageNoUri,
        dataUri : actionUri.listTariffRulesUri ,
        paginationDiv : "tariffRules-dataTable-page",
        recPerArray : [10,20,30,50,100,500,1000],
        cols : [ 

                {title: "Status", dataField: "rateStatus" },

                {title: "Effective Date", dataField: "rateEffectiveDate", sort : "t.rate_effective_date", filtration : {name:"t.rate_effective_date", alias:"Effective Date"} },
                
                {title: "Rate", dataField: function(o) { return eliminateZeros(o, 'rate') }, sort : "arp.rate", className: 'align-right' },
                
                {title: "Base Amount", dataField: "baseAmount", sort : "trbq.base_amount", filtration : {name:"trbq.base_amount", alias:"Base Amount"}, className: 'align-right' },
                {title: "Multiplier", dataField: function(o) { return eliminateZeros(o, 'multiplier') }, sort : "t.multiplier", filtration : {name:"t.multiplier", alias:"Multiplier"} , className: 'align-right'},
                {title: "Qty Begin", dataField: "quantityBegin", sort : "trbq.quantity_begin", filtration : {name:"trbq.quantity_begin", alias:"Qty Begin"} , className: 'align-right'},
                {title: "Qty End", dataField: "quantityEnd", sort : "trbq.quantity_end", filtration : {name:"trbq.quantity_end", alias:"Qty End"}, className: 'align-right' },
                {title: "Discount", dataField: function(o) { return eliminateZeros(o, 'discount') }, sort : "t.discount", filtration : {name:"t.discount", alias:"Discount"} , className: 'align-right'},
                
                {title: "Tariff", dataField: "tariff", sort : "t.name", filtration : {name:"t.name", alias:"Tariff"} },
                
                {title: "Tariff Page", dataField: "tariffPage", sort : "t.page", filtration : {name:"t.page", alias:"Tariff Page"} },
                {title: "PDF Page", dataField: "pdfPage", sort : "t.pdf_page", filtration : {name:"t.pdf_page", alias:"PDF Page"} },
                {title: "USOC", dataField: "usoc", sort : "arm.usoc", filtration : {name:"arm.usoc", alias:"USOC"} },
                {title: "USOC Description", dataField: "usocDescription", sort : "arm.usoc_description", filtration : {name:"arm.usoc_description", alias:"USOC Description"} },
                {title: "Line Item Code", dataField: "lineItemCode", sort : "arm.line_item_code", filtration : {name:"arm.line_item_code", alias:"Line Item Code"} },
                {title: "Line Item Code Description", dataField: "lineItemCodeDescription", sort : "arm.line_item_code_description", filtration : {name:"arm.line_item_code_description", alias:"Line Item Code Description"} },
                {title: "Item Description", dataField: "itemDescription", sort : "arm.item_description", filtration : {name:"arm.item_description", alias:"Item Description"} },
                {title: "Item Type", dataField: "itemType", sort : "arm.usage_item_type", filtration : {name:"arm.usage_item_type", alias:"Item Type"} },
                {title: "Summary Vendor Name", dataField: "summaryVendorName", sort : "arm.summary_vendor_name", filtration : {name:"arm.summary_vendor_name", alias:"Summary Vendor Name"} },
                {title: "Vendor Name", dataField: "vendorName", sort : "arm.vendor_name", filtration : {name:"arm.vendor_name", alias:"Vendor Name"} },
                {title: "Charge Type", dataField: "chargeType", sort : "arm.charge_type", filtration : {name:"arm.charge_type", alias:"Charge Type"} },
                {title: "Sub Product", dataField: "subProduct", sort : "arm.sub_product", filtration : {name:"arm.sub_product", alias:"Sub Product"} },
                {title: "Details From Tariff", dataField: "detailDescription" },
                {title: "Key Field", dataField: "keyField", sort : "arm.key_field_original", filtration : {name:"arm.key_field_original", alias:"Key Field"} }
          
            ] 
    });

    // 列表中的操作按钮和列表的显示控制逻辑
    TariffRulesViewListPage.addTotalSuccessEvent(function(data){
        if(data.totalResultNo < 0 || data.error){

            $('#downloadTariffRulesRecordsToExcel').css('display', 'none');
            $('#tariffRulesSearchResultTableWrapper').css('display', 'none');
        } else {

            if(data.totalResultNo == 0 ){
                $('#downloadTariffRulesRecordsToExcel').css('display', 'none');
            }else{
                $('#downloadTariffRulesRecordsToExcel').css('display', 'block');
            }

            $('#tariffRulesSearchResultTableWrapper').css('display', 'block');
        }
    });

    // 给 Tariff Rules 列表中的字段添加过滤条件。
    addTariffRulesFilter(TariffRulesViewListPage);
    
}

/**
 * 添加 Tariff Rules 过滤条件。
 */
function addTariffRulesFilter(startPage) {

    // 声明Filter 条件。
    TariffRulesViewListFilter = new SANINCO.Filter();

    // 添加Filter条件的编辑事件。
    TariffRulesViewListFilter.addEditeEvent( function(){startPage.start();} );

    TariffRulesViewListFilter.add("arm.charge_type", "String");
    TariffRulesViewListFilter.add("arm.key_field_original", "String");
    TariffRulesViewListFilter.add("t.rate_effective_date", "String"); 
    TariffRulesViewListFilter.add("arm.summary_vendor_name", "String");
    TariffRulesViewListFilter.add("arm.vendor_name", "String"); 
    TariffRulesViewListFilter.add("arm.usoc", "String"); 
    TariffRulesViewListFilter.add("arm.usoc_description", "String"); 
    TariffRulesViewListFilter.add("arm.sub_product", "String"); 
    TariffRulesViewListFilter.add("arm.line_item_code_description", "String");
    TariffRulesViewListFilter.add("arm.line_item_code", "String");
    TariffRulesViewListFilter.add("arm.usage_item_type", "String"); 
    TariffRulesViewListFilter.add("arm.item_description", "String"); 
    TariffRulesViewListFilter.add("trbq.quantity_begin", "String"); 
    TariffRulesViewListFilter.add("trbq.quantity_end", "String"); 
    TariffRulesViewListFilter.add("t.name", "String"); 
    TariffRulesViewListFilter.add("trbq.base_amount", "Integer"); 
    TariffRulesViewListFilter.add("t.multiplier", "Integer");
    // TariffRulesViewListFilter.add("t.rate", "Double");
    // TariffRulesViewListFilter.add("t.detail_description", "String"); 
    TariffRulesViewListFilter.add("t.page", "Double"); 

    TariffRulesViewListFilter.add("t.pdf_page", "String"); 
    TariffRulesViewListFilter.add("t.discount", "Double"); 


    TariffRulesViewListPage.addCompleteEvent(function(o){
        $(window).scrollTop(0);
    });

    // 将Filter 条件应用到列表中。
    startPage.setFilter(TariffRulesViewListFilter);
}

/**
 * 查询并显示列表
 * @return {[type]} [description]
 */
function searchTariffRulesList() {

    // 验证表单中的内容是否有效。
    if( !validateFields() || !validateDateFields()) return;

    // 重置过滤条件。
    TariffRulesViewListFilter.clearAll();

    TariffRulesViewListPage.myParam = composePostDataFromUi();

    // 当页面开始搜素的时候，不要让不可输入的 select 列表消失。
    // 因为在页面搜索的时候， tableCell.js  文件中的js会让select下拉列表
    // 的样式为 visibility: hidden.
    $('.search-module select').css('visibility', 'visible');
   
    // Tariff Rules 列表的搜索动作。（开启）
    TariffRulesViewListPage.start();

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

    // Vendor Name
    var vendorName = encodeURIComponent( form0_vendorName_dropdownList.getValue());

    // Tariff Name.
    var tariffName = encodeURIComponent( form0_tariffName_dropdownList.getValue());
    
    /*Text field filters*/

    // USOC.
    var usoc = encodeURIComponent( $('.usoc').val().trim() );

    // USOC Description.
    var usocDescription = encodeURIComponent( $('.usoc-description').val().trim() );

    // Item Type.
    var itemType = encodeURIComponent( $('.item-type').val().trim() );

    // Rate
    var rate = encodeURIComponent( $('.rate').val().trim().replace(',', '') );

    // Effective Date
    var effectiveDate = encodeURIComponent( $('#rateEffectiveDate').val().trim() );

    // Rate Status
    var rateStatus = encodeURIComponent( $('#rate-status-filter-list').val().trim() );

   

    // Item Description
    var itemDescription = encodeURIComponent( $('.item-description').val().trim() );

    // Line item code
    var lineItemCode = encodeURIComponent( $('.line-item-code').val().trim() );

    // Line item code description
    var lineItemCodeDescription = encodeURIComponent( $('.line-item-code-description').val().trim() );
    


    var tariffRulesFilterCause = TariffRulesViewListFilter.getCause();

    if ( !isEmptyStringValue(usoc) ) { 
        postData += 'searchTariffRulesVO.usoc=' + usoc + '&';
    }

    if ( !isEmptyStringValue(usocDescription) ) { 
        postData += 'searchTariffRulesVO.usocDescription=' + usocDescription + '&';
    }

    if ( !isEmptyStringValue(itemType) ) { 
        postData += 'searchTariffRulesVO.itemType=' + itemType + '&';
    }

    if ( !isEmptyStringValue(rate) ) { 
        postData += 'searchTariffRulesVO.rate=' + rate + '&';
    }

    if ( !isEmptyStringValue(effectiveDate) ) { 
        postData += 'searchTariffRulesVO.effectiveDate=' + effectiveDate + '&';
    }

    if ( !isEmptyStringValue(rateStatus) ) {
        postData += 'searchTariffRulesVO.rateStatus=' + rateStatus + '&';
    }


    if ( !isEmptyStringValue(itemDescription) ) {
        postData += 'searchTariffRulesVO.itemDescription=' + itemDescription + '&';
    }

    if ( !isEmptyStringValue(lineItemCode) ) {
        postData += 'searchTariffRulesVO.lineItemCode=' + lineItemCode + '&';
    }

    

    if ( !isEmptyStringValue(lineItemCodeDescription) ) {
        postData += 'searchTariffRulesVO.lineItemCodeDescription=' + lineItemCodeDescription + '&';
    }

    /* Dropdown list value vertification. */
    
    if ( !isDropdownListAllValue(summaryVendorName) ) {
        postData += 'searchTariffRulesVO.summaryVendorName=' + summaryVendorName + '&';
    }
    
    if ( !isDropdownListAllValue(vendorName) ) {
        postData += 'searchTariffRulesVO.vendorName=' + vendorName + '&';
    }

    if ( !isDropdownListAllValue(tariffName) ) {
        postData += 'searchTariffRulesVO.tariffName=' + tariffName + '&';
    }
   

    if ( !isDropdownListAllValue(keyField) ) {
        postData += 'searchTariffRulesVO.keyField=' + keyField + '&';
    }

    if ( !isDropdownListAllValue(subProduct) ) {
        postData += 'searchTariffRulesVO.subProduct=' + subProduct + '&';
    }

    if ( !isDropdownListAllValue(chargeType) ) {
        postData += 'searchTariffRulesVO.chargeType=' + chargeType + '&';
    }



    if ( !isEmptyStringValue(tariffRulesFilterCause) ) {
        postData += 'searchTariffRulesVO.filter=' + tariffRulesFilterCause + '&';
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
 * 下载Tariff Rules list
 */
function downloadTairffRulesRecordsToExcel() {

    // 使用substring 方法截取字符串是因为字符串的前面会多出一个
    // '&', 因为checkbox 复选框并不会下载到 Excel文件中
    // cols 属性中的 notDownload: 'Y', 就不会添加到titles中，
    // 这样就多出一个 '&',  因此需要这样的截取。
    var titles = TariffRulesViewListPage.getTitlesParam("titles"); // 获取所有的列头。
    var listPageParams = "";
    var paramStr = TariffRulesViewListPage.paramStr;

    // 去掉字符串结尾的 '&' 符号。
    listPageParams = paramStr.substring(0, paramStr.length - 1 );

    // 如果有 '%' 需要首先对其进行转义。
    windowOpen(actionUri.downloadTariffRulesToExcelUri + "?"+ titles.replace(/%/g, "%25").replace(/#/g, "%23") + "&" + listPageParams);
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
