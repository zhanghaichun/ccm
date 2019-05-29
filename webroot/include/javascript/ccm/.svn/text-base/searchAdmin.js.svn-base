// @Auchor chao.Liu (Optimization of complete by xinyu.Liu)
var adminTabInit = {
	rolePageIsInit : "searchRole()",
	reportAdminIsInit : "getTags();",
	vendorTabIsInit : "VendorMainPage.start();",
	
	get : function (key){
		if(key == "roleTab"){
			eval(adminTabInit.rolePageIsInit);
		}
		else if(key == "vendorTab"){
			clearVendorTxt();
			toSearchVendor();
			eval(adminTabInit.vendorTabIsInit);
		}else if(key == "reportAdminTab"){
			$("#demo li a:eq(0)").click();
			eval(adminTabInit.reportAdminIsInit);
		} else if (key == "wikiManagement") {
			resetResult();
		}
	},
	$ : function (id){
		return typeof (id) == "string" ? document.getElementById(id) : id;
//	},
//	showPanel : function (){
//		$("#roleTab").css("display","block");
//		$("#reportAdminTab").css("display","block");
//		$("#delReportAdminTags").css("display","block");
//		$("#updateReportAdminTags").css("display","block");
//		$("#updateTeams").css("display","block");
//		$("#updateReportAdminTagsAndRoles").css("display","block");
//		$("#yui-picker-panel").css("display","block");
//		$("#replaceSupervisorDiv").css("display","block");
//		$("#transactionChildTab").css("display","block");
//		$("#newScoaCodeTab").css("display","block");
//		$("#newVendorConcate").css("display","block");
//		$("#updateSecurityIp").css("display","block");
	}
};

YAHOO.util.Event.onDOMReady(function () {
	
//	setTimeout(function(){
//		adminTabInit.showPanel();
//	},500); 
//以下是pop窗口初始化代码块	
	//Vendor Maintenance tab pop窗口初始化
	addVendorConcate();
	editdConcate();
	addConcate();
	uploadTariff();
	updateTeamWindow();
	updateSecurityIp();
	delReportAdminTagsWindows();
	updateReportAdminTagsAndRolesWindows();
	updateReportAdminTagsWindows();
	$("#vendorContactManagementDiv").hide();
	
	adminPageTabView = new YAHOO.widget.TabView('adminTab');
	var inx = getUrlParms("adminTabActiveIndex");
	var selector;
	if(isNaN(inx)){
		for(inx = 0 ; inx < 11 ; inx++ ){
			selector = "#a"+inx;
			if($(selector).length>0){
				$(selector).click();
				YAHOO.util.Dom.addClass($(selector), 'selected');
				break;
			}
		}
	}else{
		selector = "#a"+inx;
		$(selector).click();
		YAHOO.util.Dom.addClass($(selector), 'selected');
	};
	
	if($('#demo').length>0){
		var reportAdminTabView = new YAHOO.widget.TabView('demo'); // Report Admin Tab
		if(reportAdminTabView)reportAdminTabView.selectTab(0);
	}
	if($('#transactionChildTab').length>0){
		transactionChildTabView = new YAHOO.widget.TabView('transactionChildTab'); // Transaction Child Tab
		$("#transactionChildTab li a:eq(0)").click();
		transactionChildTabView.selectTab(0);
	}
	
	transactionTabLC.downloadFile.initPage(); 
	
});

