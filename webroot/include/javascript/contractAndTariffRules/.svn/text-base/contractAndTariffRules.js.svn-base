

var CONTRACT_TAB = 'Contract';
var PRICELIST_TAB = 'Pricelist';
var TARIFF_TAB = 'Tariff';
var MTM_TAB = 'MtM';


var currentFocusTab = '';

$(function() {

    showContractRulesContent();
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

        if ( $(this).attr('id') == 'contractRules' ) {


            // 显示 Contract 这个标签页下的内容。
            showContractRulesContent();
            

        } else if ( $(this).attr('id') == 'tariffRules' ) {

            // 显示 pricelis 这个标签页下的内容。
            showTariffRulesContent();

        }else if ( $(this).attr('id') == 'mtmRules' ) {
    	
        	showMtMRulesContent();
	    	
	    }
        
    });
}

/**
 * 显示 Contract rules 这个标签页下的内容。
 */
function showContractRulesContent() {

    $.ajax({
        url: 'showContractRulesContent.action',
        type: 'POST',
        dataType: 'text',
        success: function(result) {
            $('#contractAndTariffRulesContent').html(result);
            currentFocusTab = CONTRACT_TAB;
        }
    });
}

/**
 * 显示 tariff rules 这个标签页下的内容。
 */
function showTariffRulesContent() {

    $.ajax({
        url: 'showTariffRulesContent.action',
        type: 'GET',
        dataType: 'text',
        success: function(result) {
            $('#contractAndTariffRulesContent').html(result);
            currentFocusTab = PRICELIST_TAB;
        }
    });
}
/**
 * 显示 mtm rules 这个标签页下的内容。
 */
function showMtMRulesContent() {
	
	$.ajax({
		url: 'showMtMRulesContent.action',
		type: 'GET',
		dataType: 'text',
		success: function(result) {
		$('#contractAndTariffRulesContent').html(result);
		currentFocusTab = MTM_TAB;
	}
	});
}