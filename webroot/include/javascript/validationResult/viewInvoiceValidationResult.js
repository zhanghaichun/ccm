$(function() {
	 
})

/**
 * 开始根据 memory modal 中的搜索条件进行请求，
 * 开始搜索动作。
 */
function searchMemory() {
	
	$("#_rate").removeClass("validation-failed");
	$("#_rate").addClass("validation-passed");
	if ($("#_rate").val() != ""){
		if (isNaN($("#_rate").val())) {
			$("#_rate").removeClass("validation-passed");
			$("#_rate").addClass("validation-failed");			
	        return false;
	    }
	}
    var type = $("#_confirmType").val();   
    var callback = {
			success: function(o){
				if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
					window.location.reload();
					return;
				}
				var data = eval("("+o.responseText+")");
				if(data.error){
					alert("Error: " + data.error);
					return;
				}
				hideLoadingModalLayer();
				$("#_confirmType").val("update");	
				if(data.auditMemoryId) {
					$("#_memoryId").val(data.auditMemoryId);
				}
				
				alert("save successfully!");
			},
			failure:showError
		};
		showLoadingModalLayer();
		
    if (type == "add") {
    		YAHOO.util.Connect.asyncRequest("POST", "saveMemory.action", callback, addMemoryDate());
    }else {   	
    		YAHOO.util.Connect.asyncRequest("POST", "updateMemory.action", callback, updateMemoryDate());
    }
}

function addMemoryDate(){
	var postData = "auditMemory.banId=" + $("#_banId").val();
	postData += "&auditMemory.chargeType=" + $("#_chargeType").html();
	postData += "&auditMemory.circuitNumber=" + $("#_circuitNumber").html();
	postData += "&auditMemory.usoc=" + $("#_USOC").html();
	postData += "&auditMemory.itemName=" + $("#_itemName").html();
	postData += "&auditMemory.lineItemCode=" + $("#_lineItemCode").html();
	postData += "&auditMemory.description=" + $("#_description").html();
	postData += "&auditMemory.rate=" + $("#_rate").val();
	if (search_filters_productId.getValue() != "all"){
		postData += "&auditMemory.productId=" + search_filters_productId.getValue();
	}else{
		postData += "&auditMemory.productId=";
	}
	if (search_filters_productComponentId.getValue() != "all"){
		postData += "&auditMemory.productComponentId=" + search_filters_productComponentId.getValue();
	}else{
		postData += "&auditMemory.productComponentId=";
	}
	if (search_filters_provinceId.getValue() != "all"){
		postData += "&auditMemory.provinceId=" + search_filters_provinceId.getValue();
	}else{
		postData += "&auditMemory.provinceId=";
	}
	
	return postData;
}
function updateMemoryDate(){
	var postData = "auditMemory.id=" + $("#_memoryId").val();
	postData += "&auditMemory.rate=" + $("#_rate").val();
	if (search_filters_productId.getValue() != "all"){
		postData += "&auditMemory.productId=" + search_filters_productId.getValue();
	}else{
		postData += "&auditMemory.productId=";
	}
	if (search_filters_productComponentId.getValue() != "all"){
		postData += "&auditMemory.productComponentId=" + search_filters_productComponentId.getValue();
	}else{
		postData += "&auditMemory.productComponentId=";
	}
	if (search_filters_provinceId.getValue() != "all"){
		postData += "&auditMemory.provinceId=" + search_filters_provinceId.getValue();
	}else{
		postData += "&auditMemory.provinceId=";
	}
	return postData;
}

/**
 * 关闭 memory modal 弹出层。
 * 通过checkbox 来控制。
 */
function closeMemoryModal() {
	$('#memory-container-checkbox').attr('checked', '');
}

/**
 * 清除所有的已经添加之后的过滤条件。
 * @return {[type]} [description]
 */
function clearFilterConditions() {
	// 清除form表单中所有的文本输入框或者是select复选框中的值。
	cleanFormElementValue();
	search_filters_productId.setValue("");
	search_filters_productComponentId.setValue("");
	 search_filters_provinceId.setValue("");
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

/* 确保显示正确的 rate 数值， 因为如果精确度过高的话
数值类型的值会使用科学计数法表示
例如： 0.00088 => 8.8E-4 */
function ensureCorrectRateValue(referenceTypeId) {

	var rateValue, isContains;

	if( referenceTypeId == 2 ) {

		rateValue = $('#tariffValidationRate').text().trim(); // 取到原有的rate值

		isContains = rateValue.toUpperCase().indexOf('E'); // 是否包含科学计数法中的 E。

		if (isContains != -1) { // 如果值不为 -1 代表其中包含字母 'E'

		    $('#tariffValidationRate').text( Number(rateValue).toFixed(5) );

		}

	} else if( referenceTypeId == 5 ) {

		rateValue = $('#pricelistValidationRate').text().trim(); // 取到原有的rate值

		isContains = rateValue.toUpperCase().indexOf('E'); // 是否包含科学计数法中的 E。

		if (isContains != -1) { // 如果值不为 -1 代表其中包含字母 'E'

		    $('#pricelistValidationRate').text( Number(rateValue).toFixed(5) );

		}

	}

   

}

/**
 * 当验证规则是 Contract, Tariff, Price List 时，显示该窗口
 * @return
 */
function showValidationDetail(referenceTypeId){

	if(referenceTypeId == 2) {
		if($("#show_tariff_validation_detail").is(":hidden")){
			$('#show_tariff_validation_detail').append("<style>#show_tariff_validation_detail::after{top:" 
					+ ($('#show_tariff_validation_detail').height() + 25) + "px}</style>");
			$('#show_tariff_validation_detail').css("top",(0 - ($('#show_tariff_validation_detail').height() + 35)));
			$('#show_tariff_validation_detail').css("left",30);
			$("#show_tariff_validation_detail").show();
		} else {
			$("#show_tariff_validation_detail").hide();
		}

		ensureCorrectRateValue(referenceTypeId);


	} else if(referenceTypeId == 3) {
		if($("#show_contract_validation_detail").is(":hidden")){
			$('#show_contract_validation_detail').append("<style>#show_contract_validation_detail::after{top:" 
					+ ($('#show_contract_validation_detail').height() + 25) + "px}</style>");
			$('#show_contract_validation_detail').css("top",(0 - ($('#show_contract_validation_detail').height() + 35)));
			$('#show_contract_validation_detail').css("left",45);
			$("#show_contract_validation_detail").show();
		} else {
			$("#show_contract_validation_detail").hide();
		}
	} else if(referenceTypeId == 5) {
		if($("#show_price_list_validation_detail").is(":hidden")){
			$('#show_price_list_validation_detail').append("<style>#show_price_list_validation_detail::after{top:" 
					+ ($('#show_price_list_validation_detail').height() + 25) + "px}</style>");
			$('#show_price_list_validation_detail').css("top",(0 - ($('#show_price_list_validation_detail').height() + 35)));
			$('#show_price_list_validation_detail').css("left",55);
			$("#show_price_list_validation_detail").show();
		} else {
			$("#show_price_list_validation_detail").hide();
		}

		ensureCorrectRateValue(referenceTypeId);
	}
}

/**
 * 下载BNS Telephone Number Validation 结果
 * @return
 */
function downloadTelephoneNumberValidationExcel(){
	showLoadingModalLayer();
	var uri = actionUri.saveExcelByTelephoneNumberValidationUri + "?svo.invoiceId="+ invoiceId;
	windowOpen(uri);
	hideLoadingModalLayer();
}

/**
 * 下载Usage Report
 * @return
 */
function downloadUsageReport(){
	showLoadingModalLayer();
	var uri = actionUri.downloadUsageReportUri + "?searchInvoiceVO.invoiceId="+ invoiceId;
	windowOpen(uri);
	hideLoadingModalLayer();
}