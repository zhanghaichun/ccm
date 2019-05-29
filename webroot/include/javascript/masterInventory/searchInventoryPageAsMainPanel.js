

var INVENTORY_TAB = 'Inventory';
var PRICELIST_TAB = 'Pricelist';
var INVENTORY_RATE_TAB = 'Inventory-Rate';


var currentFocusTab = '';

$(function() {

	showInventoryContent();
    switchContractAndTariffRulesAndRequestData();
})


/**
 * 点击标签切换不同类型的标签，
 * 并请求相应 Tab Type 的页面和
 * 数据显示。
 */
function switchContractAndTariffRulesAndRequestData() {

    $('.contract-and-tariff-rules-nav li').click(function() {

        // 去掉所有标签的选中效果。
        $('.contract-and-tariff-rules-nav li').each(function() {
            $(this).removeClass('selected');
        });

        // 添加当前点击标签的选中效果。
        $(this).addClass('selected');

        if ( $(this).attr('id') == 'inventory' ) {


            // 显示 Contract 这个标签页下的内容。
        	showInventoryContent();
            

        } else if ( $(this).attr('id') == 'inventoryRate' ) {

            // 显示 pricelis 这个标签页下的内容。
        	showInventoryRateContent();

        }
        
    });
}

/**
 * 显示 Contract rules 这个标签页下的内容。
 */
function showInventoryContent() {
	showLoadingModalLayer();
    $.ajax({
        url: 'searchInventoryDashboard.action',
        type: 'POST',
        dataType: 'text',
        success: function(result) {
            $('#inventoryAndRateContent').html(result);
            currentFocusTab = INVENTORY_TAB;
            hideLoadingModalLayer();
        }
    });
}

/**
 * 显示 tariff rules 这个标签页下的内容。
 */
function showInventoryRateContent() {
	showLoadingModalLayer();
    $.ajax({
        url: 'searchInventoryRateDashboard.action',
        type: 'POST',
        dataType: 'text',
        success: function(result) {
            $('#inventoryAndRateContent').html(result);
            currentFocusTab = INVENTORY_RATE_TAB;
            hideLoadingModalLayer();
        }
    });
}