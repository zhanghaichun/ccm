

var CONTRACT_TAB = 'Contract';
var PRICELIST_TAB = 'Pricelist';
var TARIFF_TAB = 'Tariff';


var currentFocusTab = '';

$(function() {

	showContractContent();
	switchContractTabNavAndRequestData();
})


/**
 * 点击标签切换不同类型的标签，
 * 并请求相应 Tab Type 的页面和
 * 数据显示。
 */
function switchContractTabNavAndRequestData() {

	$('.contract-tab-nav li').click(function() {

		// 去掉所有标签的选中效果。
		$('.contract-tab-nav li').each(function() {
			$(this).removeClass('selected');
		});

		// 添加当前点击标签的选中效果。
		$(this).addClass('selected');

		if ( $(this).attr('id') == 'contract' ) {


			// 显示 Contract 这个标签页下的内容。
			showContractContent();
			

		} else if ( $(this).attr('id') == 'pricelist' ) {

			// 显示 pricelis 这个标签页下的内容。
			showPricelistContent();

		}
		
	});
}

/**
 * 显示 Contract 这个标签页下的内容。
 */
function showContractContent() {

	$.ajax({
		url: 'showContractContent.action',
		type: 'POST',
		dataType: 'text',
		success: function(result) {
			$('#contractTabContent').html(result);
			currentFocusTab = CONTRACT_TAB;
		}
	});
}

/**
 * 显示 pricelis 这个标签页下的内容。
 */
function showPricelistContent() {

	$.ajax({
		url: 'showPricelistContent.action',
		type: 'POST',
		dataType: 'text',
		success: function(result) {
			$('#contractTabContent').html(result);
			currentFocusTab = PRICELIST_TAB;
		}
	});
}