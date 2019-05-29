var isQuick = false;
var quickData;

/* 四个子tab下的CSS类常量 */ 
var INVENTORY_DASHBOARD = "inventory-dashboard";
//var CIRCUIT_INVENTORY = "circuit-inventory";
//var CIRCUIT_INVENTORY = "circuit-inventory";
//var VENDOR_INVENTORY = "vendor-inventory";
//var CIRCUIT_COST = "circuit-cost";


$(function(){
	$('.inventory-dashboard a').trigger('click');
});

var circuitTabInit = {
	circuitTabIsInitTab1 : "circuitPageTab1Init();",
//	circuitTabIsInitTab2 : "circuitPageTab2Init();",
//	circuitTabIsInitTab3 : "circuitPageTab3Init();",
//	circuitTabIsInitTab4 : "circuitPageTab4Init();",
	get : function (key){
		if(key == "InventoryDashboard"){
			eval(circuitTabInit.circuitTabIsInitTab1);
		}
//		if(key == "CircuitInventory"){
//			eval(circuitTabInit.circuitTabIsInitTab2);
//		}
//		if(key == "VendorInventory"){
//			eval(circuitTabInit.circuitTabIsInitTab3);
//		}
//		if(key == "CircuitCost"){
//			eval(circuitTabInit.circuitTabIsInitTab4);
//		}
	}
};

/**
 * 点击系统中左侧边栏的quick link 跳转到相应的子tab页下面。
 * 并渲染出相应tab下表单中的搜索条件。
 * @see quicklink.js#getThisPageNameCallback
 * @param  {[type]} data [description]
 * @return {[type]}      [description]
 */
function changeCircuitPageTabByQuicklinkType(data){
	isQuick = true;
	quickData = data;
	eval(circuitTabInit.circuitTabIsInitTab1);
//	if (data.quick_type == "MasterInventory"){ // Inventory Dashboard
//		eval(circuitTabInit.circuitTabIsInitTab1);
//	} else if(data.quick_type == "Circuit") { // Circuit Inventory
//		eval(circuitTabInit.circuitTabIsInitTab2);
//	} else if (data.quick_type == "VendorInventory"){ // Vendor Inventory
//		eval(circuitTabInit.circuitTabIsInitTab3);
//	} else if (data.quick_type == "OldCircuit"){ // Circuit Cost
//		eval(circuitTabInit.circuitTabIsInitTab4);
//	}
};

/* 下面是circuit页面中的四个子tab页面 */

/**
 * Inventory Dashboard 子tab页面.
 * @return {[type]} [description]
 */
function circuitPageTab1Init(){
	$.ajax({
		url: "searchInventoryDashboard.action",
		type: "POST",
		dataType: "text",
		data: {},
		success: function(o) {
			$("#_tab1").empty();
			$("#_tab1").html(o);
			// current tab => Inventory Dashboard
			switchToSelectedTab(INVENTORY_DASHBOARD);
			tabChangeByQuicklink();
		},
		error: function() {}
	});
}

/**
 * Circuit Inventory 子tab页面.
 * @return {[type]} [description]
 */
//function circuitPageTab2Init(){
//	
//	$.ajax({
//		url: "searchCircuit.action",
//		type: "POST",
//		dataType: "text",
//		data: {},
//		success: function(o) {
//			$("#_tab1").empty();
//			$("#_tab1").html(o);
//			// current tab => Circuit Inventory
//			switchToSelectedTab(CIRCUIT_INVENTORY);
//			tabChangeByQuicklink();
//		},
//		error: function() {}
//	});
//}

/**
 * Vendor Inventory 子tab页面.
 * @return {[type]} [description]
 */
//function circuitPageTab3Init(){
//	
//	$.ajax({
//		url: "viewVendorInventory.action",
//		type: "POST",
//		dataType: "text",
//		data: {},
//		success: function(o) {
//			$("#_tab1").empty();
//			$("#_tab1").html(o);
//			// current tab => Vendor Inventory
//			switchToSelectedTab(VENDOR_INVENTORY);
//			tabChangeByQuicklink();
//		},
//		error: function() {}
//	});
//}

/**
 * Circuit Cost 子tab页面.
 * @return {[type]} [description]
 */
//function circuitPageTab4Init(){
//	
//	$.ajax({
//		url: "searchCircuitOld.action",
//		type: "POST",
//		dataType: "text",
//		data: {},
//		success: function(o) {
//			$("#_tab1").empty();
//			$("#_tab1").html(o);
//			// current tab => Circuit Cost
//			switchToSelectedTab(CIRCUIT_COST);
//			tabChangeByQuicklink();
//		},
//		error: function() {}
//	});
//}

/**
 * 切换四个子tab选项的选中效果。
 * @param  {[type]} tabType 当前tab的类型。
 * @return {[type]}         [description]
 */
function switchToSelectedTab( tabType ) {

	$('.circuit-switch-tab li').removeClass('selected');
	$('.' + tabType).addClass('selected');
}

/**
 * 不同的tab还原不同的信息。
 * @return {[type]} [description]
 */
function tabChangeByQuicklink(){
	if(isQuick){
		isQuick = false;
		/* @see quicklink.js */
		populateQuicklinkOnUI(quickData.id);
	}
}