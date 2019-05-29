YAHOO.util.Event.onDOMReady(function () {
	YAHOO.ccm.container.contactVendorWindow = new YAHOO.widget.Dialog("rateWindow", { 
		width: "460px",
	    fixedcenter: true,
	    visible: false,
	    constraintoviewport: true,
	    modal: true
	});
	YAHOO.ccm.container.contactVendorWindow.render();
});
var actionUri = {
    countRateSearchViewListPageNoUri: 'countRateSearchPageNo.action',
    listRateSearchUri: 'listRateSearch.action',
    downloadRateSearchToExcelUri: 'downloadRateSearch.action',
    downloadRateTemplateToExcelUri: 'downloadRateTemplate.action',

    countContractSummaryViewListPageNoUri: 'countContractSummaryPageNo.action',
    listContractSummaryUri: 'listContractSummary.action',
    downloadContractSummaryToExcelUri: 'downloadContractSummary.action',
};

$(function() {

    listRateSearchTable();

    listContractSummaryTable();
    
    switchDetailsAndSummary();

    /**
     * Init rate blank template notes window.
     */
    rateBlankTemplateObj.initNotesWindow();

})

function enterToStartSearch() {

    if( window.event.keyCode == 13) {

        // 不可输入的下拉框失去焦点
        $('#reference-type-list').blur();

        $('#charge-type-list').blur();
        $('#rate-view-type-list').blur();
        $('#rate-status-filter-list').blur();

        $('#expiration-period-list').blur();

        startSearch();
    }

}


/* Details Tab 和 Summary Tab 的切换功能。 */
function switchDetailsAndSummary() {

    $('#rate-view-type-list').change(function() {

        var rateViewValue = $(this).val();

        if ( rateViewValue == 1 ) { // Details

            $('#vendorNameFilterList_input').removeAttr('disabled');
            $('#vendorNameFilterList_arrow').css('display', 'block');

            $('.stripped-circuit-number').removeAttr('disabled');

            $('#subProductFilterList_input').removeAttr('disabled');
            $('#subProductFilterList_arrow').css('display', 'block');

            $('#reference-type-list').removeAttr('disabled');

            $('#charge-type-list').removeAttr('disabled');


            $('.crtc-number').removeAttr('disabled');
            $('.part-or-section').removeAttr('disabled');
            $('.item-number').removeAttr('disabled');
            $('.tariff-page').removeAttr('disabled');
            $('.code').removeAttr('disabled');
            $('.description').removeAttr('disabled');
            
            $('.rate').removeAttr('disabled');
            $('#rate-status-filter-list').removeAttr('disabled');
            $('.item-type').removeAttr('disabled');

            $('#expiration-period-list').val('').attr('disabled', 'disabled');
            
            // $('#attachDiv').css('display', 'block');
            $('.buttons-container .right-buttons').css('visibility', 'visible');

        } else if ( rateViewValue == 2 ) { // Contract Summary

            $('#vendorNameFilterList_input').val('').attr('disabled', 'disabled');
            $('#vendorNameFilterList_arrow').css('display', 'none');

            $('.stripped-circuit-number').val('').attr('disabled', 'disabled');

            $('#subProductFilterList_input').val('').attr('disabled', 'disabled');
            $('#subProductFilterList_arrow').css('display', 'none');

            $('#reference-type-list').val('').attr('disabled', 'disabled');

            $('#charge-type-list').val('all').attr('disabled', 'disabled');

            
            $('.crtc-number').val('').attr('disabled', 'disabled');
            $('.part-or-section').val('').attr('disabled', 'disabled');
            $('.item-number').val('').attr('disabled', 'disabled');
            $('.tariff-page').val('').attr('disabled', 'disabled');
            $('.code').val('').attr('disabled', 'disabled');
            $('.description').val('').attr('disabled', 'disabled');
            
            $('.rate').val('').attr('disabled', 'disabled');
            $('#rate-status-filter-list').val('').attr('disabled', 'disabled');

            $('.item-type').val('').attr('disabled', 'disabled');

            $('#expiration-period-list').removeAttr('disabled');
            
            // $('#attachDiv').css('display', 'none');
            $('.buttons-container .right-buttons').css('visibility', 'hidden');
        }


    });
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



function resetRateSearchFormElementValue() {

    // 清空dropdown List搜索条件。
    form0_subProduct_dropdownList.setValue('all'); 
    
    form0_summaryVendorName_dropdownList.setValue('all'); 
    form0_vendorName_dropdownList.setValue('all');
    form0_contractOrTariffName_dropdownList.setValue('all'); 

    $('#charge-type-filter-list').val('all');
    $('#expiration-period-list').val('');
    $('#rate-view-type-list').val('1');
    $('#rate-status-filter-list').val('');

    // 清除form表单中所有的文本输入框或者是select复选框中的值。
    cleanFormElementValue();
}

function resetRadioButton() {}

/*
将结果表格中的文字换行显示。
 */
function wrapTextFn(titleFlag,p1, p2) {
	
	if(titleFlag){
		return p1+' '+p2;
	}else{
		return '<span style="display: inline-block; \ text-decoration: underline; text-align: center; \ line-height: 18px;">'+ p1 +'<br/>'+ p2 +'</span>';
	}
   
}

/**
 * Rate Search List。
 */
function listRateSearchTable() {

    RateSearchViewListPage = new SANINCO.Page('rate-search-data-table',"RateSearchViewListPage",{
        sortingField : "r.effectiveDate",
        sortingDirection : "asc",
        vo : "searchRateSearchVO",
        autoToTop : true,
        recordText: 'Rate Search Results:',
        totalPageUri : actionUri.countRateSearchViewListPageNoUri,
        dataUri : actionUri.listRateSearchUri ,
        paginationDiv : "rateSearch-dataTable-page",
        recPerArray : [10,20,30,50,100,500,1000],
        cols : [ 

                {title: "Status", dataField: "rateStatus" },
 
                {title: function(o,titleFlag){return wrapTextFn(titleFlag,'Effective', 'Date')}, dataField: "effectiveDate", sort : "r.effectiveDate", filtration : {name:"r.effectiveDate", alias:"Effective Date"} },
                
                {title: "Rate", dataField: function(o) { return eliminateZeros(o, 'rate') }, sort : "r.rate", filtration : {name:"r.rate", alias:"Rate"}, className: 'align-right' },
                
                {title: "Stripped Circuit Number", dataField: "strippedCircuitNumber", sort : "r.strippedCircuitNumber", filtration : {name:"r.strippedCircuitNumber", alias:"Stripped Circuit Number"} },
                {title: function(o,titleFlag){return wrapTextFn(titleFlag,'Base', 'Amount')}, dataField: "baseAmount", sort : "r.baseAmount", filtration : {name:"r.baseAmount", alias:"Base Amount"}, className: 'align-right' },
                {title: "Multiplier", dataField: function(o) { return eliminateZeros(o, 'multiplier') }, sort : "r.multiplier", filtration : {name:"r.multiplier", alias:"Multiplier"} , className: 'align-right'},
//                {title: "Province", dataField: 'province', sort : "r.province", filtration : {name:"r.province", alias:"Province"}},
                {title: function(o,titleFlag){return wrapTextFn(titleFlag,'Qty', 'Begin')}, dataField: "quantityBegin", sort : "r.quantityBegin", filtration : {name:"r.quantityBegin", alias:"Qty Begin"} , className: 'align-right'},
                {title: function(o,titleFlag){return wrapTextFn(titleFlag,'Qty', 'End')}, dataField: "quantityEnd", sort : "r.quantityEnd", filtration : {name:"r.quantityEnd", alias:"Qty End"}, className: 'align-right' },
//                {title: function(o,titleFlag){return wrapTextFn(titleFlag,'Imbalance', 'Start')}, dataField: "imbalanceStart", sort : "r.imbalanceStart", filtration : {name:"r.imbalanceStart", alias:"Imbalance Start"}, className: 'align-right' },
//                {title: function(o,titleFlag){return wrapTextFn(titleFlag,'Imbalance', 'End')}, dataField: "imbalanceEnd", sort : "r.imbalanceEnd", filtration : {name:"r.imbalanceEnd", alias:"Imbalance End"}, className: 'align-right' },
                {title: "Discount", dataField: function(o) { return eliminateZeros(o, 'discount') }, sort : "r.discount", filtration : {name:"r.discount", alias:"Discount"} , className: 'align-right'},
                
                {title: function(o,titleFlag){return wrapTextFn(titleFlag,'Contract/', 'Tariff/MtM')}, dataField: "referenceTypeName", sort : "r.referenceTypeName", filtration : {name:"r.referenceTypeName", alias:"Contract/Tariff/MtM"} },

                {title: function(o,titleFlag){return wrapTextFn(titleFlag,'Term', '(Months)')}, dataField: "term", sort : "r.term", filtration : {name:"r.term", alias:"Term (Months)"}, className: 'align-right' },
                {title: "Contract # / Tariff Reference", dataField: "referenceName", sort : "r.contractNumberOrTariffReference", filtration : {name:"r.contractNumberOrTariffReference", alias:"Contract # / Tariff Reference"} },
           
                {title: function(o,titleFlag){return wrapTextFn(titleFlag,'Tariff', 'Page')}, dataField: "tariffPage", sort : "r.tariffPage", filtration : {name:"r.tariffPage", alias:"Tariff Page"} },
//                {title: function(o,titleFlag){return wrapTextFn(titleFlag,'PDF', 'Page')}, dataField: "pdfPage", sort : "r.pdfPage", filtration : {name:"r.pdfPage", alias:"PDF Page"} },
                // {title: "USOC", dataField: "usoc", sort : "r.usoc", filtration : {name:"r.usoc", alias:"USOC"} },
                // {title: "USOC Description", dataField: "usocDescription", sort : "r.usocLongDescription", filtration : {name:"r.usocLongDescription", alias:"USOC Description"} },
                // {title: "Line Item Code", dataField: "lineItemCode", sort : "r.lineItemCode", filtration : {name:"r.lineItemCode", alias:"Line Item Code"} },
                // {title: "Line Item Code Description", dataField: "lineItemCodeDescription", sort : "r.lineItemCodeDescription", filtration : {name:"r.lineItemCodeDescription", alias:"Line Item Code Description"} },
                {title: "Code", dataField: "code", sort : "r.code", filtration : {name:"r.code", alias:"Code"} },
                {title: "Description", dataField: "description", sort : "r.description", filtration : {name:"r.description", alias:"Description"} },
                {title: "Item Description", dataField: "itemDescription", sort : "r.itemDescription", filtration : {name:"r.itemDescription", alias:"Item Description"} },
                {title: "Item Type", dataField: "itemType", sort : "r.itemType", filtration : {name:"r.itemType", alias:"Item Type"} },
                {title: "Summary Vendor Name", dataField: "summaryVendorName", sort : "r.summaryVendorName", filtration : {name:"r.summaryVendorName", alias:"Summary Vendor Name"} },
                {title: "Vendor Name", dataField: "vendorName", sort : "r.vendorName", filtration : {name:"r.vendorName", alias:"Vendor Name"} },
                {title: function(o,titleFlag){return wrapTextFn(titleFlag,'Charge', 'Type')}, dataField: "chargeType", sort : "r.chargeType", filtration : {name:"r.chargeType", alias:"Charge Type"} },
                {title: "Sub Product", dataField: "subProduct", sort : "r.subProduct", filtration : {name:"r.subProduct", alias:"Sub Product"} },
                {title: "Details From Tariff", dataField: "detailDescription", sort: "r.detailsDescription" ,filtration : {name:"r.detailsDescription", alias:"Details From Tariff"} }
                // {title: "Key Field", dataField: "keyField", sort : "r.keyField", filtration : {name:"r.keyField", alias:"Key Field"} }
          
            ] 
    });

    // 列表中的操作按钮和列表的显示控制逻辑
    RateSearchViewListPage.addTotalSuccessEvent(function(data){
        if(data.totalResultNo < 0 || data.error){

            $('#downloadRateSearchRecordsToExcel').css('display', 'none');
            $('#downloadRateTemplateToExcel').css('display', 'none');
            $('#rateSearchSwitchTabContainer').css('display', 'none');
        } else {

            if(data.totalResultNo == 0 ){
                $('#downloadRateSearchRecordsToExcel').css('display', 'none');
                $('#downloadRateTemplateToExcel').css('display', 'none');
            }else{
                $('#downloadRateSearchRecordsToExcel').css('display', 'block');
                $('#downloadRateTemplateToExcel').css('display', 'block');
            }

            $('#rateSearchSwitchTabContainer').css('display', 'block');
        }
    });

  
    addRateSearchFilter(RateSearchViewListPage);
    
}

/* Contract Summary List */
function listContractSummaryTable() {

    ContractSummaryViewListPage = new SANINCO.Page('contract-summary-data-table',"ContractSummaryViewListPage",{
        sortingField : "s.summaryVendorName",
        sortingDirection : "asc",
        vo : "searchRateSearchVO",
        autoToTop : true,
        recordText: 'Contract Summary Results:',
        totalPageUri : actionUri.countContractSummaryViewListPageNoUri,
        dataUri : actionUri.listContractSummaryUri ,
        paginationDiv : "contractSummary-dataTable-page",
        recPerArray : [10,20,30,50,100,500,1000],
        cols : [ 

                {title: "Summary Vendor Name", dataField: "summaryVendorName", sort : "s.summaryVendorName", filtration : {name:"s.summaryVendorName", alias:"Summary Vendor Name"} },
                {title: "Contract Name", dataField: "contractName", sort : "s.contractName", filtration : {name:"s.contractName", alias:"Contract Name"} },

                {title: "Contract Service Schedule Name", dataField: "serviceScheduleName", sort : "s.serviceScheduleName", filtration : {name:"s.serviceScheduleName", alias:"Contract Service Schedule Name"} },

                {title: "Effective Date", dataField: "effectiveDate", sort : "s.effectiveDate", filtration : {name:"s.effectiveDate", alias:"Effective Date"} },

                {title: "Term (Months)", dataField: "termQuantity", sort : "s.termQuantity", filtration : {name:"s.termQuantity", alias:"Term (Months)"} },
                {title: "Renewal Term after Term Expiration", dataField: "renewalTerm", sort : "s.renewalTerm", filtration : {name:"s.renewalTerm", alias:"Renewal Term after Term Expiration"} },

                {title: "Expiration Period", dataField: "expirationPeriod", sort : "s.expirationPeriod", filtration : {name:"s.expirationPeriod", alias:"Expiration Period"} }

            ] 
    });

    // 列表中的操作按钮和列表的显示控制逻辑
    ContractSummaryViewListPage.addTotalSuccessEvent(function(data){
        if(data.totalResultNo < 0 || data.error){

            $('#downloadContractSummaryRecordsToExcel').css('display', 'none');
            $('#rateSearchSwitchTabContainer').css('display', 'none');
        } else {

            if(data.totalResultNo == 0 ){
                $('#downloadContractSummaryRecordsToExcel').css('display', 'none');
            }else{
                $('#downloadContractSummaryRecordsToExcel').css('display', 'block');
            }

            $('#rateSearchSwitchTabContainer').css('display', 'block');
        }
    });

    
    addContractSummaryFilter(ContractSummaryViewListPage);
    
}

/**
 * Rate Search Filter
 */
function addRateSearchFilter(startPage) {

    // 声明Filter 条件。
    RateSearchViewListFilter = new SANINCO.Filter();

    // 添加Filter条件的编辑事件。
    RateSearchViewListFilter.addEditeEvent( function(){startPage.start();} );

    RateSearchViewListFilter.add("r.effectiveDate", "String");
    RateSearchViewListFilter.add("r.rate", "Double");
    RateSearchViewListFilter.add("r.strippedCircuitNumber", "String");
    RateSearchViewListFilter.add("r.baseAmount", "Integer"); 
    RateSearchViewListFilter.add("r.multiplier", "Double");
//    RateSearchViewListFilter.add("r.province", "String");
    RateSearchViewListFilter.add("r.quantityBegin", "Integer"); 
    RateSearchViewListFilter.add("r.quantityEnd", "Integer"); 
//    RateSearchViewListFilter.add("r.imbalanceStart", "String"); 
//    RateSearchViewListFilter.add("r.imbalanceEnd", "String"); 
    RateSearchViewListFilter.add("r.discount", "Double"); 
    RateSearchViewListFilter.add("r.referenceTypeName", "String"); 
    RateSearchViewListFilter.add("r.term", "String"); 
    RateSearchViewListFilter.add("r.contractNumberOrTariffReference", "String");  
    RateSearchViewListFilter.add("r.tariffPage", "String"); 

    RateSearchViewListFilter.add("r.code", "String"); 
    RateSearchViewListFilter.add("r.description", "String"); 
//    RateSearchViewListFilter.add("r.pdfPage", "String"); 
    // RateSearchViewListFilter.add("r.usoc", "String"); 
    // RateSearchViewListFilter.add("r.usocLongDescription", "String"); 
    // RateSearchViewListFilter.add("r.lineItemCode", "String");
    // RateSearchViewListFilter.add("r.lineItemCodeDescription", "String");
    RateSearchViewListFilter.add("r.itemDescription", "String"); 
    RateSearchViewListFilter.add("r.itemType", "String"); 
    RateSearchViewListFilter.add("r.summaryVendorName", "String"); 
    RateSearchViewListFilter.add("r.vendorName", "String"); 
    RateSearchViewListFilter.add("r.chargeType", "String"); 
    RateSearchViewListFilter.add("r.subProduct", "String");  
    RateSearchViewListFilter.add("r.detailsDescription", "String");  
    RateSearchViewListFilter.add("r.keyField", "String"); 


    RateSearchViewListPage.addCompleteEvent(function(o){
        $(window).scrollTop(0);
    });

    // 将Filter 条件应用到列表中。
    startPage.setFilter(RateSearchViewListFilter);
}

/*
Contract Summary Filter
 */
function addContractSummaryFilter(startPage) {

    // 声明Filter 条件。
    ContractSummaryViewListFilter = new SANINCO.Filter();

    // 添加Filter条件的编辑事件。
    ContractSummaryViewListFilter.addEditeEvent( function(){startPage.start();} );

    ContractSummaryViewListFilter.add("s.summaryVendorName", "String");
    ContractSummaryViewListFilter.add("s.contractName", "String");
    ContractSummaryViewListFilter.add("s.serviceScheduleName", "String"); 
    ContractSummaryViewListFilter.add("s.effectiveDate", "String");
    ContractSummaryViewListFilter.add("s.termQuantity", "Integer"); 
    ContractSummaryViewListFilter.add("s.renewalTerm", "String"); 
    ContractSummaryViewListFilter.add("s.expirationPeriod", "String"); 

    ContractSummaryViewListPage.addCompleteEvent(function(o){
        $(window).scrollTop(0);
    });

    // 将Filter 条件应用到列表中。
    startPage.setFilter(ContractSummaryViewListFilter);
}

/**
 * 查询并显示列表
 * @return {[type]} [description]
 */
function startSearch() {
    var rateViewType = $('#rate-view-type-list').val();

    // 验证表单中的内容是否有效。
    if( !validateFields() || !validateDateFields()) return;

    // 当页面开始搜素的时候，不要让不可输入的 select 列表消失。
    // 因为在页面搜索的时候， tableCell.js  文件中的js会让select下拉列表
    // 的样式为 visibility: hidden.
    $('.search-module select').css('visibility', 'visible');
   
    if ( rateViewType == 1 ) { // Rate Search

        $('#contractSummaryContent').hide();
        $('#rateSearchDetailsContent').show();

        // 重置过滤条件。
        
        if (RateSearchViewListFilter != undefined){
        	RateSearchViewListFilter.clearAll();
        }

        RateSearchViewListPage.myParam = composePostDataFromUi();

        
        RateSearchViewListPage.start();

    } else if ( rateViewType == 2 ) { // Contract Summary

        $('#rateSearchDetailsContent').hide();
        $('#contractSummaryContent').show();

        ContractSummaryViewListFilter.clearAll();

        ContractSummaryViewListPage.myParam = composePostDataFromUi();
        
        ContractSummaryViewListPage.start();
    }
    

}

/**
 * 组合查询条件的功能。
 * @return {[type]} [description]
 */
function composePostDataFromUi() {

    var postData = '';

    var rateViewType,
        vendorName,
        subProduct,
        chargeType,
        itemType,
        rate,
        rateStatus,
        referenceType,
        crtcNumber,
        tariffPage,
        partOrSection,
        itemNumber,
        code,
        description,

        summaryVendorName,
        contractNumberOrTariffReference,
        effectiveDate,
        strippedCircuitNumber,
        ContractSummaryFilterCause,
        RateSearchFilterCause;

    var expirationPeriod = '';





    // Rate View
    rateViewType = encodeURIComponent( $('#rate-view-type-list').val().trim() );

    if ( rateViewType == 1 ) { // Rate Search Result List.

        // Vendor Name
        vendorName = encodeURIComponent( form0_vendorName_dropdownList.getValue());

        // Sub Product
        subProduct = encodeURIComponent(form0_subProduct_dropdownList.getValue());

        // Charge Type
        chargeType = encodeURIComponent($('#charge-type-list').val().trim());


        /*Text field filters*/
        
        // CRTC
        crtcNumber = encodeURIComponent( $('.crtc-number').val().trim() );
        
        // Tariff Page
        tariffPage = encodeURIComponent( $('.tariff-page').val().trim() );
        
        // Part/Section
        partOrSection = encodeURIComponent( $('.part-or-section').val().trim() );
        
        // item number
        itemNumber = encodeURIComponent( $('.item-number').val().trim() );

        // Item Type.
        itemType = encodeURIComponent( $('.item-type').val().trim() );
        
        // code => [usoc] OR [line item code]
        code = encodeURIComponent( $('.code').val().trim() );
        
        // description => [usoc description] OR [line item code description] OR [item description]
        description = encodeURIComponent( $('.description').val().trim() );
        
        // Rate
        rate = encodeURIComponent( $('.rate').val().trim().replace(',', '') );

        // Rate Status
        rateStatus = encodeURIComponent( $('#rate-status-filter-list').val().trim() );

        // Reference Type
        referenceType = encodeURIComponent( $('#reference-type-list').val().trim() );

        strippedCircuitNumber = encodeURIComponent( $('.stripped-circuit-number').val().trim().replace(/[\(\)\.\:_\\\/\s-]/g, '') );

    } else if ( rateViewType == 2 ) { // Contract Summary Result List.

        // Expiration Period
        expirationPeriod = encodeURIComponent( $('#expiration-period-list').val().trim() );

    }



    
    // Summary Vendor Name.
    summaryVendorName = encodeURIComponent( form0_summaryVendorName_dropdownList.getValue());

    contractNumberOrTariffReference = encodeURIComponent( form0_contractOrTariffName_dropdownList.getValue());
    
    // Effective Date
    effectiveDate = encodeURIComponent( $('#rateEffectiveDate').val().trim() );

    

    RateSearchFilterCause = RateSearchViewListFilter.getCause();
    ContractSummaryFilterCause = ContractSummaryViewListFilter.getCause();
    
    if ( !isEmptyStringValue(crtcNumber) ) { 
        postData += 'searchRateSearchVO.crtcNumber=' + crtcNumber + '&';
    }
    
    if ( !isEmptyStringValue(tariffPage) ) { 
        postData += 'searchRateSearchVO.tariffPage=' + tariffPage + '&';
    }
    
    if ( !isEmptyStringValue(partOrSection) ) { 
        postData += 'searchRateSearchVO.partOrSection=' + partOrSection + '&';
    }
    
    if ( !isEmptyStringValue(itemNumber) ) { 
        postData += 'searchRateSearchVO.itemNumber=' + itemNumber + '&';
    }
    
    if ( !isEmptyStringValue(code) ) { 
        postData += 'searchRateSearchVO.code=' + code + '&';
    }
    
    if ( !isEmptyStringValue(description) ) { 
        postData += 'searchRateSearchVO.description=' + description + '&';
    }


    if ( !isEmptyStringValue(referenceType) ) { 
        postData += 'searchRateSearchVO.referenceType=' + referenceType + '&';
    }

    if ( !isEmptyStringValue(rate) ) { 
        postData += 'searchRateSearchVO.rate=' + rate + '&';
    }

    if ( !isEmptyStringValue(effectiveDate) ) { 
        postData += 'searchRateSearchVO.effectiveDate=' + effectiveDate + '&';
    }

    if ( !isEmptyStringValue(rateStatus) ) {
        postData += 'searchRateSearchVO.rateStatus=' + rateStatus + '&';
    }



    if ( !isEmptyStringValue(itemType) ) {
        postData += 'searchRateSearchVO.itemType=' + itemType + '&';
    }
    

    if ( !isEmptyStringValue(expirationPeriod) ) {
        postData += 'searchRateSearchVO.expirationPeriod=' + expirationPeriod + '&';
    }

    if ( !isEmptyStringValue(strippedCircuitNumber) ) {
        postData += 'searchRateSearchVO.strippedCircuitNumber=' + strippedCircuitNumber + '&';
    }


    if ( !isEmptyStringValue(rateViewType) ) {
        postData += 'searchRateSearchVO.rateViewType =' + rateViewType + '&';
    }

    /* Dropdown list value vertification. */
    
    if ( !isDropdownListAllValue(summaryVendorName) ) {
        postData += 'searchRateSearchVO.summaryVendorName=' + summaryVendorName + '&';
    }
    
    if ( !isDropdownListAllValue(vendorName) ) {
        postData += 'searchRateSearchVO.vendorName=' + vendorName + '&';
    }

    if ( !isDropdownListAllValue(contractNumberOrTariffReference) ) {
        postData += 'searchRateSearchVO.contractNumberOrTariffReference=' + contractNumberOrTariffReference + '&';
    }
   

    if ( !isDropdownListAllValue(subProduct) ) {
        postData += 'searchRateSearchVO.subProduct=' + subProduct + '&';
    }

    if ( !isDropdownListAllValue(chargeType) ) {
        postData += 'searchRateSearchVO.chargeType=' + chargeType + '&';
    }



    if ( !isEmptyStringValue(RateSearchFilterCause) && rateViewType == 1 ) {
        postData += 'searchRateSearchVO.filter=' + RateSearchFilterCause + '&';
    }

    if ( !isEmptyStringValue(ContractSummaryFilterCause) && rateViewType == 2 ) {
        postData += 'searchRateSearchVO.filter=' + ContractSummaryFilterCause + '&';
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
 * Downlad Rate Search list.
 */
function downloadRateSearchRecordsToExcel() {

    // 使用substring 方法截取字符串是因为字符串的前面会多出一个
    // '&', 因为checkbox 复选框并不会下载到 Excel文件中
    // cols 属性中的 notDownload: 'Y', 就不会添加到titles中，
    // 这样就多出一个 '&',  因此需要这样的截取。
    var titles = RateSearchViewListPage.getTitlesParam("titles"); // 获取所有的列头。
    var listPageParams = "";
    var paramStr = RateSearchViewListPage.paramStr;

    // 去掉字符串结尾的 '&' 符号。
    listPageParams = paramStr.substring(0, paramStr.length - 1 );

    // 如果有 '%' 需要首先对其进行转义。
    windowOpen(actionUri.downloadRateSearchToExcelUri + "?"+ titles.replace(/%/g, "%25").replace(/#/g, "%23") + "&" + listPageParams);
}
function downloadRateTemplateToExcel() {
	var titles = RateSearchViewListPage.getTitlesParam("titles"); // 获取所有的列头。
    var listPageParams = "";
    var paramStr = RateSearchViewListPage.paramStr;
    var paramList = paramStr.split("searchRateSearchVO.referenceType=");
    if(paramList.length > 1){
    	// 去掉字符串结尾的 '&' 符号。
    	listPageParams = paramStr.substring(0, paramStr.length - 1 );
    	
    	// 如果有 '%' 需要首先对其进行转义。
    	windowOpen(actionUri.downloadRateTemplateToExcelUri + "?"+ titles.replace(/%/g, "%25").replace(/#/g, "%23") + "&" + listPageParams);
    }else{
		alert("Please choose one rate type from Contract / Tariff / MtM field, and search data that want to update, then click Download Update Data Template button.");
	}
}

/*
Download contract summary list.
 */
function downloadContractSummaryRecordsToExcel() {

    // 使用substring 方法截取字符串是因为字符串的前面会多出一个
    // '&', 因为checkbox 复选框并不会下载到 Excel文件中
    // cols 属性中的 notDownload: 'Y', 就不会添加到titles中，
    // 这样就多出一个 '&',  因此需要这样的截取。
    var titles = ContractSummaryViewListPage.getTitlesParam("titles"); // 获取所有的列头。
    var listPageParams = "";
    var paramStr = ContractSummaryViewListPage.paramStr;

    // 去掉字符串结尾的 '&' 符号。
    listPageParams = paramStr.substring(0, paramStr.length - 1 );

    // 如果有 '%' 需要首先对其进行转义。
    windowOpen(actionUri.downloadContractSummaryToExcelUri + "?"+ titles.replace(/%/g, "%25").replace(/#/g, "%23") + "&" + listPageParams);
}

/**
 * 判断字符串值是否为空
 * @param  {[type]}  stringValue [description]
 * @return {Boolean}             [description]
 */
function isEmptyStringValue(stringValue) {
    return (stringValue === '' || stringValue == undefined || stringValue == 'undefined') ? true : false;
}

/**
 * 判断下拉列表中的选值是否
 * 为'all',这个值不传入后台中。
 */
function isDropdownListAllValue(dropdownListValue) {
    return (dropdownListValue === 'all') ? true : false;
}

rateErrorFilePath = null;
function showRateWindow(errorFilePath){
	rateErrorFilePath = errorFilePath;
	YAHOO.ccm.container.contactVendorWindow.show();
}

var rateBlankTemplateObj = {

    downloadRateBlankTemplateURL: 'downloadRateBlankTemplate.action',

    initNotesWindow: function() {
        YAHOO.ccm.container.downloadTemplateNotesWindow = new YAHOO.widget.Dialog("download-template-notes-window", { 
            width: "550px",
            fixedcenter: true,
            visible: false,
            constraintoviewport: true,
            modal: true
        });
        YAHOO.ccm.container.downloadTemplateNotesWindow.render();
    },

    /**
     * Popup download template notes window.
     */
    popupDownloadBlankRateTemplateNotes: function() {
        YAHOO.ccm.container.downloadTemplateNotesWindow.show();
    },

    /**
     * Download rate blank template. 
     */
    selectToDownloadBlankRateTemplate: function(rateType) {

        var listPageParams = "referenceType=" + rateType;
       
        windowOpen(this.downloadRateBlankTemplateURL + "?"+ listPageParams);
    }
};

function downLoadRateResult(){
	showLoadingModalLayer();
	windowOpen("downLoadRateErrorFile.action?errorFileName="+ rateErrorFilePath);
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
    $("#uploadRateForm").submit();
}