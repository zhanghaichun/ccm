/**
 * @author Jian.Dong
 */

// 下面四个链接分别用于获取左边栏中的不同
// 模块， 包括 【My Workspace】, 【Back up】, 【Delegation】
// 【My Team】
var searchMyWorkspaceAction = "getMyWorkspace.action";
var searchBackupAction = "getBackupAction.action";
var searchDelegationAction = "getDelegation.action";
var searchMyTeamAction = "getMyTeam.action";
var updateInvoiceNotesAction = "updateInvoiceNotes.action";	// Add By Xin.Huang on 11/08/31

// Get invoice count label for disputes bucket section.
var getDisputesBucketAction = "getDisputesBucketCount.action"; 

//Declare a name-space for global variables.
var tems = {};

// Manipulate icon sets.
tems.icon = {};

// Actions.
tems.actions = {};

// Util actions.
tems.utils = {
	/**
	 * A recursive method.
	 * Show the quantity of invoice according to the response data from back.
	 * Must be guarantee array element and key of data set (from the back) have the same name. 
	 * @param { Object|Array } arraySet (get array directly by property or traverse deeply) or an array.
	 * @param { Object } data (from the back). response data.
	 * */
	setInvoiceNumberTokens: function setInvoiceNumberTokens ( arraySet/* Object or array */, data ) {
		
		if ( arraySet == undefined || typeof arraySet === 'undefined' ) throw new TypeError();
		
		// If arraySet is an array.
		if ( Array.isArray( arraySet ) ) {
			// Loop through the array and add number token.
			$.each( arraySet, function( index, value ) {
				// Set the token.
				$('#' + value).text( data[ value ] );
			} );
		}
		
		// Enumerate the property of arraySet.
		for (var prop in arraySet) {
			
			// Value of property
			var valueOfProp = arraySet[prop];
			
			if ( Array.isArray( valueOfProp ) ) { // Property is an array.
				
				// Loop through the array and add number token.
				$.each( valueOfProp, function( index, value ) {
					// Set the token.
					$('#' + value).text( data[ value ] );
				} );
				
			} else if ( typeof valueOfProp === 'object' && !Array.isArray( valueOfProp ) ) { // Property is an object.
				
				// Take advantage of recursive algorithm to get deeply array.
				return setInvoiceNumberTokens( valueOfProp, data );
			}
			
			
		}
	},
	
	/**
	 * Reload the page when loading time out (a flag will be returned from back).
	 * @param { String } responseText.
	 * */
	ifReloadPage: function ifReloadPage ( responseText ) {
		
		// Detecting if time out flag is exist.
		if ( responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") !== -1 ) {
			
			// Reload the page.
			window.location.reload();
			return true;
		}
	},
	
	/**
	 * Show the error message.
	 * @param { Object } dataObj.
	 * */
	ifShowError: function ifShowError ( data ) {
		
		if ( data.error ) {
			
			alert("Left Page MyWorkspace Error:" + data.error);
			return true;
		}
		
	},
	
	/**
	 * Reload the page if the time is out and show error message when there is a error.
	 * @param { String } options.responseText.
	 * @param { Object } options.data.
	 * */
	showErrorOrReloadPage: function showErrorOrReloadPage ( options ) {
		
		options || ( options = {} );
		
		var responseText = options.responseText || 'no response data';
		
		var data = options.data || {};
		
		// If reload the page.
		var isReloadPage = this.ifReloadPage( responseText );
		
		// If show error message.
		var isShowError = this.ifShowError( data );
		
		// Any one return true the method return true.
		if ( isReloadPage === true || isShowError === true ) return true;
		
	}
};

/**
 * DOM ready function (when the DOM tree has been built).
 * */
YAHOO.util.Event.onDOMReady(function () {
	
	// Do fold or unfold my-workspace sections menu.
	simpleSlide("Left_Menu","ML_Head");
	
	// Create left panel and populate relative information.
	searchLeftPageOnLoad(); 
	editNotesWindow();	// Add By Xin.Huang on 11/08/31
	$("#removeMissingInvoice").css("display", "block");
	YAHOO.ccm.container.removeMissingInvoice = new YAHOO.widget.Dialog("removeMissingInvoice", 
		{ width : "40em",
		  fixedcenter : true,
		  visible : false, 
		  modal : true,
		  constraintoviewport : true,
		  buttons : [
		  { 
		  	text:"Remove", 
		  	handler:function(){
//			    if ($.trim($("#missingInvoiceNotes").val()) == "") {
//			       alert("Notes can't be empty!");	
//			       return;
//			    }
		  		showLoadingModalLayer();
				YAHOO.util.Connect.asyncRequest("POST", "doRemoveMissingInvoice.action", {
	                success: function(o){
	                    hideLoadingModalLayer();
	                    
	                    // Response data, the statement return an object.
	                	// '()' represents constraint to execute.
	                	var data = eval("("+o.responseText+")"); 
	                	
	                	var options = {
	                		responseText: o.responseText,
	                		data: data
	                	};
	                	
	                	// If the page have reloaded or the data is error.
	                	var isShowErrorOrReladPage = tems.utils.showErrorOrReloadPage( options );
	                	
	                	// Special conditions, quit the method.
	                	// The page loading time out or there is a error when get data from back.
	                	if ( isShowErrorOrReladPage === true ) return;
	                	
						YAHOO.ccm.container.removeMissingInvoice.hide();
						tems.actions.invoicesSearch.missingInvoices('-1');
//						searchLeftPageOnLoad();
	                },
	                failure: showError
	            }, "invoice.id=" + $("#removeMissingInvoiceId").val() + "&invoice.notes=" + ccmEscape($("#missingInvoiceNotes").val()) + 
	            		"&missingEmailflag=" + $("#missingEmailflag").val());
		  	}
		  },{
	        text:"Cancel", 
	        handler:function(){
	        	this.cancel();
	        }
		  }]
		});
	YAHOO.ccm.container.removeMissingInvoice.render();
	
	
	$("#bulkActionRequestDisputeWindow").css("display", "block");
	YAHOO.ccm.container.bulkActionRequestDisputeWindow = new YAHOO.widget.Dialog("bulkActionRequestDisputeWindow", {
	        width: "37em",
	        fixedcenter: true,
	        visible: false,
	    	modal : true,
	        constraintoviewport: true
	    });
		
	YAHOO.ccm.container.bulkActionRequestDisputeWindow.render();

	
	
	
	$("#removeUnknownInvoice").css("display", "block");
	YAHOO.ccm.container.removeUnknownInvoice = new YAHOO.widget.Dialog("removeUnknownInvoice", 
			{ width : "40em",
		fixedcenter : true,
		visible : false, 
		modal : true,
		constraintoviewport : true,
		buttons : [
		           { 
		        	   text:"Yes", 
		        	   handler:function(){
//		        	   if ($.trim($("#unknownInvoiceNotes").val()) == "") {
//					       alert("Notes can't be empty!");	
//					       return;
//					    }
		        	   showLoadingModalLayer();
		        	   YAHOO.util.Connect.asyncRequest("POST", "doRemoveUnknownInvoice.action", {
		        		   success: function(o){
		        		   hideLoadingModalLayer();
		        		   
							// Response data, the statement return an object.
							// '()' represents constraint to execute.
							var data = eval("("+o.responseText+")"); 
							
							var options = {
								responseText: o.responseText,
								data: data
							};
							
							// If the page have reloaded or the data is error.
							var isShowErrorOrReladPage = tems.utils.showErrorOrReloadPage( options );
							
							// Special conditions, quit the method.
							// The page loading time out or there is a error when get data from back.
							if ( isShowErrorOrReladPage === true ) return;
		        			
		        		   YAHOO.ccm.container.removeUnknownInvoice.hide();
		        		   tems.actions.invoicesSearch.unknownInvoices('-1');
//		        		   searchLeftPageOnLoad();
		        	   },
		        	   failure: showError
		        	   }, "invoice.id=" + $("#removeUnknownInvoiceId").val() + "&invoice.notes=" + $("#unknownInvoiceNotes").val());
		           }
		           },{
		        	   text:"Cancel", 
		        	   handler:function(){
		        	   this.cancel();
		           }
		           }]
			});
	YAHOO.ccm.container.removeUnknownInvoice.render();
});
/**
 * new view
 */
function searchLeftPageOnLoad(){
	
	// My workspace module.
	searchMyWorkspace();
	
	//修改为点击某个用户查询某个用户,原来这种批量刷新不好使了.
	searchBackup();
	searchMyTeam();
	
	searchDelegation();
	
	// Disputes bucket section ( out my workspace module ).
	searchDisputesBucket();
}
/**
 * Search dispute,credit and pyment count in the My Workspace 
 */
function searchMyWorkspace(){
	var callback = {
		success: searchMyWorkspaceIsSuccess,
		failure: showError
	};
	YAHOO.util.Connect.asyncRequest('POST' , searchMyWorkspaceAction , callback ); 
}

/**
 * Search disputes in disputes bucket section.
 * Show the count label for every range.
 * */
function searchDisputesBucket() {
	var callback = {
			success: searchDisputesBucketIsSuccess,
			failure: showError
		};
	YAHOO.util.Connect.asyncRequest('POST' , getDisputesBucketAction , callback );
}

/**
 * 	Core action to get label number ( quantity of disputes ).
 * */
function searchDisputesBucketIsSuccess( o ) {
	
	// Response data, the statement return an object.
	// '()' represents constraint to execute.
	var data = eval("("+o.responseText+")"); 

	var options = {
		responseText: o.responseText,
		data: data
	};

	// If the page have reloaded or the data is error.
	var isShowErrorOrReladPage = tems.utils.showErrorOrReloadPage( options );
	
	// Array to store every count label name for dispute number.
	var disputesBucketSections;
	
	// Special conditions, quit the method.
	// The page loading time out or there is a error when get data from back.
	if ( isShowErrorOrReladPage === true ) return;
	
	// Values of array.
	disputesBucketSections = ['totalDisputesBucket', 'first30Disputes', 'range31To60Disputes', 'range61To90Disputes',
	                          'range91To120Disputes', 'range121To180Disputes', 'over180Disputes'];
	
	// Add the dispute number token.
	tems.utils.setInvoiceNumberTokens( disputesBucketSections, data );
	
	
}

/**
 * Show quantity of invoice for specified section in My Workspace part.
 * */
function searchMyWorkspaceIsSuccess(o){
	// Response data, the statement return an object.
	// '()' represents constraint to execute.
	var data = eval("("+o.responseText+")"); 

	var options = {
		responseText: o.responseText,
		data: data
	};
	
	// If the page have reloaded or the data is error.
	var isShowErrorOrReladPage = tems.utils.showErrorOrReloadPage( options );
	
	// A set includes invoice record numbers' container id.
	var invoiceNumberContainerIds; 
	
	// All sections.
	var sections;
	
	// All submenu sections.
	var submenuSections;
	
	// Due in 7 days submenu sections.
	var dueIn7DaysSubmenuSections;
	
	// Due in 15 days submenu sections.
	var dueIn15DaysSubmenuSections;

	// Special conditions, quit the method.
	// The page loading time out or there is a error when get data from back.
	if ( isShowErrorOrReladPage === true ) return;


	// Populate sections.
	sections = ['redPastDue', 'redInvoiceCount', 'yellowInvoiceCount', 'greenInvoiceCount',
                'missingInvoicesCount', 'unknownInvoicesCount', 'paymentInProcessCount', 'disputesInProcessCount',
                'paymentInExceptionCount', 'preloadInvoicesCount', 'invoiceRejectCount', 'externalApproveInvoiceCount'];
	
	// Populate due in 7 days submenu sections.
	dueIn7DaysSubmenuSections = ['redInvoiceCountr0', 'redInvoiceCountr1', 'redInvoiceCountr2', 'redInvoiceCountr3',
	                             'redInvoiceCountr4', 'redInvoiceCountr5', 'redInvoiceCountr6', 'redInvoiceCounty7'];
	
	// Populate due in 15 days submenu sections.
	dueIn15DaysSubmenuSections = [  'redInvoiceCounty8', 'redInvoiceCounty9', 'redInvoiceCounty10', 'redInvoiceCounty11',
	                                'redInvoiceCounty12', 'redInvoiceCounty13', 'redInvoiceCounty14', 'redInvoiceCounty15'];
	
	// A set (the value of property is Array type).
	submenuSections = {
		dueIn7Days: dueIn7DaysSubmenuSections,
		dueIn15Days: dueIn15DaysSubmenuSections
	};
	
	// Populate all invoice number containers.
	invoiceNumberContainerIds = {
		sections: sections,
		submenuSections: submenuSections
	};
	
	// Add the invoice number token.
	tems.utils.setInvoiceNumberTokens( invoiceNumberContainerIds, data );
	
	simpleSlide("My_Workspace_Menu","second_head");
}


/**
 * Search dispute,credit and pyment count in the Backup
 */
function searchBackup(){
	var callback = {
		success: searchBackupIsSuccess,
		failure: showError
	};
	YAHOO.util.Connect.asyncRequest('POST' , searchBackupAction , callback ) 
}
function searchBackupIsSuccess(o){
	
	// Response data, the statement return an object.
	// '()' represents constraint to execute.
	var data = eval("("+o.responseText+")"); 
	
	var options = {
		responseText: o.responseText,
		data: data
	};
	
	// If the page have reloaded or the data is error.
	var isShowErrorOrReladPage = tems.utils.showErrorOrReloadPage( options );
	
	// Special conditions, quit the method.
	// The page loading time out or there is a error when get data from back.
	if ( isShowErrorOrReladPage === true ) return;
	
	
	makeTableString(data,"backupLi","Backup","Backup_Menu","backupDiv");
	
}	
/**
 * Search dispute,credit and pyment count in the Delegation
 */
function searchDelegation(){
	var callback = {
		success: searchDelegationIsSuccess,
		failure: showError
	};
	YAHOO.util.Connect.asyncRequest('POST' , searchDelegationAction , callback ) 
}
function searchDelegationIsSuccess(o){
	
	// Response data, the statement return an object.
	// '()' represents constraint to execute.
	var data = eval("("+o.responseText+")"); 
	
	var options = {
		responseText: o.responseText,
		data: data
	};
	
	// If the page have reloaded or the data is error.
	var isShowErrorOrReladPage = tems.utils.showErrorOrReloadPage( options );
	
	// Special conditions, quit the method.
	// The page loading time out or there is a error when get data from back.
	if ( isShowErrorOrReladPage === true ) return;
	
	
	makeTableString(data,"delegationLi","Delegation","Delegation_Menu","delegationDiv");
	
}



// Actions to search invoice records for work-space sections.
// Include Disputes Bucket section.
tems.actions.invoicesSearch = {
	
		/**
		 * Past due section.
		 * */
	searchWorkspaceByUser: function (uid) {
		var callback = {
			success: function backUserAllCount(o) {
			            var data = eval("("+o.responseText+")");
			 	        $(".redPastDue_"+uid).html(data.redPastDue);
			 	        $(".redInvoiceCount_"+uid).html(data.redInvoiceCount);
			 	        $(".yellowInvoiceCount_"+uid).html(data.yellowInvoiceCount);
			 	        $(".greenInvoiceCount_"+uid).html(data.greenInvoiceCount);
			 	        $(".unknownInvoicesCount_"+uid).html(data.unknownInvoicesCount);
			 	        $(".missingInvoicesCount_"+uid).html(data.missingInvoicesCount);
			 	        $(".paymentInProcessCount_"+uid).html(data.paymentInProcessCount);
			 	        $(".disputesInProcessCount_"+uid).html(data.disputesInProcessCount);
			 	        $(".paymentInExceptionCount_"+uid).html(data.paymentInExceptionCount);
			 	        $(".banApproveCount_"+uid).html(data.banApproveCount);
			 	        $(".banRejectCount_"+uid).html(data.banRejectCount);
			 	        $(".redInvoiceCountr0_"+uid).html(data.redInvoiceCountr0);
			 	        $(".redInvoiceCountr1_"+uid).html(data.redInvoiceCountr1);
			 	        $(".redInvoiceCountr2_"+uid).html(data.redInvoiceCountr2);
			 	        $(".redInvoiceCountr3_"+uid).html(data.redInvoiceCountr3);
			 	        $(".redInvoiceCountr4_"+uid).html(data.redInvoiceCountr4);
			 	        $(".redInvoiceCountr5_"+uid).html(data.redInvoiceCountr5);
			 	        $(".redInvoiceCountr6_"+uid).html(data.redInvoiceCountr6);
			 	        $(".redInvoiceCounty7_"+uid).html(data.redInvoiceCounty7);
			 	        $(".redInvoiceCounty8_"+uid).html(data.redInvoiceCounty8);
			 	        $(".redInvoiceCounty9_"+uid).html(data.redInvoiceCounty9);
			 	        $(".redInvoiceCounty10_"+uid).html(data.redInvoiceCounty10);
			 	        $(".redInvoiceCounty11_"+uid).html(data.redInvoiceCounty11);
			 	        $(".redInvoiceCounty12_"+uid).html(data.redInvoiceCounty12);
			 	        $(".redInvoiceCounty13_"+uid).html(data.redInvoiceCounty13);
			 	        $(".redInvoiceCounty14_"+uid).html(data.redInvoiceCounty14);
			 	        $(".redInvoiceCounty15_"+uid).html(data.redInvoiceCounty15);
			 	        $(".preloadInvoicesCount_"+uid).html(data.preloadInvoicesCount);
			 	        $(".invoiceRejectCount_"+uid).html(data.invoiceRejectCount);
			 	       $(".externalApproveInvoiceCount_"+uid).html(data.externalApproveInvoiceCount);
			 	       $(".totalDisputesBucket_"+uid).html(data.totalDisputesBucket);
			 	       $(".first30Disputes_"+uid).html(data.first30Disputes);
			 	       $(".range31To60Disputes_"+uid).html(data.range31To60Disputes);
			 	       $(".range61To90Disputes_"+uid).html(data.range61To90Disputes);
			      	   $(".range91To120Disputes_"+uid).html(data.range91To120Disputes);
			           $(".range121To180Disputes_"+uid).html(data.range121To180Disputes);
			           $(".over180Disputes_"+uid).html(data.over180Disputes);
 
		      },
			failure: showError
		};
		YAHOO.util.Connect.asyncRequest('POST' , "getMyTeamUser.action" , callback ,"wvo.uid="+uid) ;
	},
	
	
	
	
	/**
	 * Past due section.
	 * */
	searchPastDue: function (uid, dueDay) {
		$(".zlin").hide();
		BUSINESS.service.startSearch('PastDue',uid,dueDay);
	},
	
	/**
	 * Invoice section (for Due in 7 days section, Due in 15 days section,
	 * All My Invoices section etc...).
	 * */
	searchInvoice: function (uid, dueDay, titleName) {
		// When due day is -1, it will search all invoices.
		$(".zlin").hide();
		BUSINESS.service.startSearch('Invoice',uid,dueDay, 0, titleName);
	},
	
	/**
	 * Unknown invoices section.
	 * */
	unknownInvoices: function (uid) {
		BUSINESS.service.startSearch('UnknownInvoices',uid,0);
	},
	
	/**
	 * Missing invoices section.
	 * */
	missingInvoices: function (uid) {
		$(".zlin").hide();
		BUSINESS.service.startSearch('MissingInvoices',uid,0);
	},
	
	/**
	 * Preload invoices section.
	 * */
	preloadInvoices: function (uid) {
		$(".zlin").hide();
		BUSINESS.service.startSearch('PreloadInvoices',uid,0);
	},
	
	/**
	 * Invoice in Progress section (for Payment in Progress section, Disputes in Progress section,
	 * Payment in Exception section etc...).
	 * */
	invoiceInProcess: function (uid, who) {
		$(".zlin").hide();
		BUSINESS.service.startSearch('InvoiceInProcess',uid,who);
	},

	/**
	 * Invoice Reject section.
	 * */
	searchInvoiceReject: function (uid) {
		$(".zlin").hide();
		BUSINESS.service.startSearch('InvoiceReject',uid,0);
	},
	
	/**
	 * Disputes bucket section.
	 * @param { Number } uid. Current user.
	 * @param { Number } disputeDay. Disputes at the range.
	 * @param { String } titleName. Table caption.
	 * */
	searchDisputesBucket: function (uid, disputeDay, titleName) {
		$(".zlin").hide();
		BUSINESS.service.startSearch('DisputesBucket', uid, NaN ,disputeDay, titleName);
	},

	/*
	查询 External Approve invoice list.
	 */
	listExternalApproveInvoices: function(uid) {
		$(".zlin").hide();
		BUSINESS.service.startSearch('ExternalApproveInvoices', uid ,0);
	}
};

// Actions to compand submenu lists of sections.
tems.actions.compandSubmenu = {

	/**
	 * Action to fold or unfold disputes bucket fold menu.
	 * */
	compandDisputesBucket: function (uid) {

		var foldMenu;
		var foldMenuIcon;
		if(uid > 0){
			// Get disputes bucket fold menu.
			foldMenu = $('.disputes-buckets-fold-menu-'+uid);
			// Get disputes bucket fold menu icon.
			foldMenuIcon = $('.db-fold-menu-icon-'+uid);
		}else{
			foldMenu = $('.disputes-buckets-fold-menu');
			foldMenuIcon = $('.db-fold-menu-icon');
		}
		
		// Get fold menu 'display' css rule.
		var foldMenuDisplay = $(foldMenu).css('display');
		
		
		// A flag to identify the fold menu is fold or not.
		var isFold = ( foldMenuDisplay === 'none');
		
		// Fold or unfold the menu list.
		if ( isFold ) {
			
			$(foldMenu).css('display', 'block');
			$(foldMenuIcon).attr( 'src', tems.icon.paths.openIcon );
			
		} else {

			$(foldMenu).css('display', 'none');
			$(foldMenuIcon).attr( 'src', tems.icon.paths.closeIcon );
			
		}
	},

	/**
	 * Add by wenbo.zhang 2015-3-18
	 * Compand Due in 7 days fold menu
	 * */
	searchInvoice7: function (id) {
		var style = document.getElementById("xialai"+id).style.display;
		
		if(style=="none"){
			document.getElementById("xialai"+id).style.display ="block";
			document.getElementById(id).src= tems.icon.paths.openIcon;	 
		}else{
			document.getElementById("xialai"+id).style.display ="none";
			document.getElementById(id).src= tems.icon.paths.closeIcon;
		}
	},

	/**
	 * Compand Due in 15 days fold menu
	 * */
	searchInvoice15: function (id) {
		var style = document.getElementById("xialaisw"+id).style.display;
		if(style=="none"){
			document.getElementById("xialaisw"+id).style.display ="block";
			document.getElementById(id).src= tems.icon.paths.openIcon;	 
		}else{
			document.getElementById("xialaisw"+id).style.display ="none";
			document.getElementById(id).src= tems.icon.paths.closeIcon;
		}
	}
};

// Actions to download invoice records for specified sections (.xlsx file).
tems.actions.downloadInvoiceRecordsTable = {

	/**
	 * Missing invoices records table download action
	 * */
	missingInvoicesDownload: function () {
		showLoadingModalLayer();
		var titles = "titles=BAN&titles=Vendor&titles=Original&titles=Analyst&titles=Invoice Status&titles=Days in Status&" +
				"titles=Invoice Expecting Date&titles=Last Dispute&titles=Last Payment&titles=Last Total Due";
		
//		window.location.href = "doSaveMissingInvoicesExcel.action" + "?"+ titles +"&"+ BUSINESS.base.previousPostedData;
		var uri = "doSaveMissingInvoicesExcel.action" + "?"+ titles +"&"+ BUSINESS.base.previousPostedData;
		windowOpen(uri);
		
		setTimeout(function(){
			hideLoadingModalLayer();
		},5000);
	},

	/**
	 * Unknown invoices records table download action
	 * */
	unknownInvoicesDownload: function () {
		var plusParam = "";
		var checkboxCount = $("#ctl00_MainContent_SearchInvoiceControl1_GridView1 input[type=checkbox]:checked").length;
		if (checkboxCount > 0) {
			for (var i=0;i<checkboxCount;i++) {
				if ($("#ctl00_MainContent_SearchInvoiceControl1_GridView1 input[type=checkbox][checked=true]:eq(" + i + ")").val() != "on") {
					plusParam+= "ids=" + 
						$("#ctl00_MainContent_SearchInvoiceControl1_GridView1 input[type=checkbox][checked=true]:eq(" + i + ")")
							.val() + "&";
				}
			}
			showLoadingModalLayer();
			var titles = "titles=Bar Code&titles=BAN&titles=Vendor&titles=Invoice Date&titles=Due Date&titles=Current Due&" +
					"titles=Total Due&titles=Analyst&titles=Received Date&titles=Invoice Status&titles=Days in Status&" +
					"titles=Original File&titles=Notes";
//			window.location.href = "doSaveUnknownInvoicesExcel.action" + "?"+ titles +"&"+ 
//						plusParam.substring(0, plusParam.length-1) + "&" + BUSINESS.base.previousPostedData;
			
			var uri = "doSaveUnknownInvoicesExcel.action" + "?"+ titles +"&"+ 
						plusParam.substring(0, plusParam.length-1) + "&" + BUSINESS.base.previousPostedData;
			windowOpen(uri);
			
			setTimeout(function(){
				hideLoadingModalLayer();
			},5000);
		} else {
		    alert("Please select at least one to download UnknownInvoice!");	
		}
	},

	/**
	 * Preload invoices records table download action
	 * */
	preloadInvoicesDownload: function () {
		showLoadingModalLayer();
		var titles = "titles=Invoice Number&titles=Bar Code&titles=BAN&titles=Vendor&titles=Invoice Date&" +
				"titles=Due Date&titles=Received Date&titles=Invoice Current Due&titles=Total Due&titles=Analyst&" +
				"titles=Invoice Status&titles=Days in Status";
//		window.location.href = "doSavePreloadInvoicesExcel.action" + "?"+ titles +"&"+ BUSINESS.base.previousPostedData;
		
		var uri = "doSavePreloadInvoicesExcel.action" + "?"+ titles +"&"+ BUSINESS.base.previousPostedData;
		windowOpen(uri);
		
		setTimeout(function(){
			hideLoadingModalLayer();
		},5000);
	},

	/**
	 * Due day invoices records table download action
	 * */
	dueDaysInvoicesDownload: function (){
		showLoadingModalLayer();
		var titles = "titles=Invoice Number&titles=BAN&titles=Vendor&titles=Invoice Date&" +
				"titles=Invoice Current Due&titles=Original&titles=Analyst&titles=Due Date&titles=Total Due&" +
				"titles=Payment Amount&titles=Dispute Amount&titles=Misc Adjustment Amount&titles=Invoice Status&" +
				"titles=Days in Status&titles=Payment Status&titles=Payment Date&titles=Payment Transaction Number &" +
				"titles=Payment Reference Number";
//		window.location.href = "doSaveDueDaysInvoicesExcel.action" + "?"+ titles +"&"+ BUSINESS.base.previousPostedData;
		var uri = "doSaveDueDaysInvoicesExcel.action" + "?"+ titles +"&"+ BUSINESS.base.previousPostedData;
		
		windowOpen(uri);
		
		setTimeout(function(){
			hideLoadingModalLayer();
		},5000);
	},
	/* 
		下载 External Approve Bucket Invoice items
		内部使用拼接字符串的方式组合成将要生成的
		Excel 文件的列头。
		访问的后台接口是 downloadExternalApproveInvoices.action
	 */
	downloadExternalApproveInvoices: function () {

		showLoadingModalLayer();

		var titles = 'titles=Invoice Number';
				titles += '&titles=Workflow Action';
				titles += '&titles=Notes';
				titles += '&titles=BAN';
				titles += '&titles=Vendor';
				titles += '&titles=Invoice Date';
				titles += '&titles=Invoice Current Due';
				titles += '&titles=Original';
				titles += '&titles=Analyst';
				titles += '&titles=Due Date';
				titles += '&titles=Total Due';
				titles += '&titles=Payment Amount';
				titles += '&titles=Dispute Amount';
				titles += '&titles=Misc Adjustment Amount';
				titles += '&titles=Invoice Status';
				titles += '&titles=Days in Status';
				titles += '&titles=Payment Status';
				titles += '&titles=Payment Date';
				titles += '&titles=Payment Transaction Number';
				titles += '&titles=Payment Reference Number';
				

		var uri = "downloadExternalApproveInvoices.action" + "?"+ titles +"&"+ BUSINESS.base.previousPostedData;
		// 在新标签页中打开 URL 地址， 相当于 HTML 中的
		// target = "_blank" 属性。
		windowOpen(uri); 
		
		setTimeout(function(){
			hideLoadingModalLayer();
		},5000);

	},
	forwardSelectedDisputeDownload: function (){
		var plusParam = "";
		var titles = "titles=Dispute Number&titles=Claim Number&titles=Vendor&titles=BAN&titles=Invoice Number&titles=Dispute Amount&" +
		"titles=Outstanding Dispute Balance&titles=Dispute Date&titles=Dispute Category&titles=Days in Status&titles=Dispute Owner&titles=Dispute Created by&titles=Assigned To&" +
		"titles=Is Short-paid&titles=Is Recurring";
		var checkboxCount = $("#ctl00_MainContent_SearchInvoiceControl1_GridView1 input[type=checkbox]:checked").length;
		if (checkboxCount > 0) {
			for (var i=0;i<checkboxCount;i++) {
				if ($("#ctl00_MainContent_SearchInvoiceControl1_GridView1 input[type=checkbox][checked=true]:eq(" + i + ")").val() != "on") {
					plusParam+= "ids=" + 
						$("#ctl00_MainContent_SearchInvoiceControl1_GridView1 input[type=checkbox][checked=true]:eq(" + i + ")")
							.val() + "&";
				}
			}
			showLoadingModalLayer();
			
//			window.location.href = "doSaveDisputeExcel.action" + "?"+ titles +"&"+ plusParam.substring(0, plusParam.length-1) + "&" + BUSINESS.base.previousPostedData;
			var uri = "doSaveDisputeExcel.action" + "?"+ titles +"&"+ plusParam.substring(0, plusParam.length-1) + "&" + BUSINESS.base.previousPostedData;
			windowOpen(uri);
		} else {
			showLoadingModalLayer();
//			window.location.href = "doSaveDisputeExcel.action" + "?"+ titles +"&" + BUSINESS.base.previousPostedData;
			var uri  = "doSaveDisputeExcel.action" + "?"+ titles +"&" + BUSINESS.base.previousPostedData;
			windowOpen(uri);
		}
		setTimeout(function(){
			hideLoadingModalLayer();
		},5000);
		
	},
	forwardSelectedDispute: function () {
		var plusParam = "";
		var checkboxCount = $("#ctl00_MainContent_SearchInvoiceControl1_GridView1 input[type=checkbox]:checked").length;
		if (checkboxCount > 0) {
			for (var i=0;i<checkboxCount;i++) {
				if ($("#ctl00_MainContent_SearchInvoiceControl1_GridView1 input[type=checkbox][checked=true]:eq(" + i + ")").val() != "on") {
					plusParam+= "ids=" + 
						$("#ctl00_MainContent_SearchInvoiceControl1_GridView1 input[type=checkbox][checked=true]:eq(" + i + ")")
							.val() + "&";
				}
			}
			showLoadingModalLayer();
//			window.location.href = "searchDisputeMessageByDisputeIds.action" + "?"+ plusParam.substring(0, plusParam.length-1);
			var uri = "searchDisputeMessageByDisputeIds.action" + "?"+ plusParam.substring(0, plusParam.length-1);
			windowLocationHref(uri);
			setTimeout(function(){
				hideLoadingModalLayer();
			},5000);
		} else {
		    alert("Please select at least one dispute.");	
		}
		
//		var callback = {
//				success: function(o){
//			
//				},
//				failure: showError
//			};
//		var pdata = plusParam.substring(0, plusParam.length-1);
//		YAHOO.util.Connect.asyncRequest('POST' , "searchDisputeMessageByDisputeIds.action" , callback , pdata);
		
	},
	createBulkActionRequestDispute: function () {
		var plusParam = "";
		var checkboxCount = $("#ctl00_MainContent_SearchInvoiceControl1_GridView1 input[type=checkbox]:checked").length;
		if (checkboxCount > 0) {
			for (var i=0;i<checkboxCount;i++) {
				if ($("#ctl00_MainContent_SearchInvoiceControl1_GridView1 input[type=checkbox][checked=true]:eq(" + i + ")").val() != "on") {
					plusParam+= 
						$("#ctl00_MainContent_SearchInvoiceControl1_GridView1 input[type=checkbox][checked=true]:eq(" + i + ")")
							.val() + "&";
				}
			}
			
			
			YAHOO.ccm.container.bulkActionRequestDisputeWindow.show();
			clearBulkActionRequestDisputeNotes();
			var ps = $("#note_Notes");
			var num = $("#num_characters");

			calculateWordLimit(ps, num);

			$("#disputeNoteByDisputeIdId").val(plusParam.substring(0, plusParam.length-1));

		} else {
		    alert("Please select at least one dispute.");	
		}
	
	}
};

// Sets to store icon paths.
tems.icon.paths = {
	closeIcon: 'include/images/light_bulb/closeicon.png',
	openIcon : 'include/images/light_bulb/openicon.png'
};


/**
 * Search dispute,credit and pyment count in the MyTeam
 */
function searchMyTeam(){
	var callback = {
		success: searchMyTeamIsSuccess,
		failure: showError
	};
	YAHOO.util.Connect.asyncRequest('POST' , searchMyTeamAction , callback ) 
}
function searchMyTeamIsSuccess(o){
	
	var data = eval("("+o.responseText+")");
	
	var isReloadPage = tems.utils.ifReloadPage( o.responseText );
	
	var isError = tems.utils.ifShowError( data );
	
	if ( isReloadPage === false || isError === false ) return;
		
	makeTableString(data,"myTeamLi","My Team","My_Team_Menu","myTeamDiv");
}

function makeTableString(data,sli,divName,ulId,divId){
	if(data.length == 0){return;} // first paramer
	document.getElementById(sli).style.display = ""; //  second paramer
	
	//var divStr = '<div class="ML_Head">&nbsp;&nbsp;'+divName+' </div>'; // third paramer
	var divStr = '';
	divStr += '<ul id="'+ulId+'">'; // fourth paramer
	var childrenId = new Object();
	for(var i=0;i<data.length;i++){
		divStr += '<li >'
				+ '<div class="second_head" onclick="tems.actions.invoicesSearch.searchWorkspaceByUser('+data[i].uid+');">&nbsp;&nbsp;&nbsp;&nbsp;'+data[i].uname+'</div>'
				+ '<ul  id="'+data[i].uid+'_'+ulId+'">'
				+ 	'<li  style="width:100%;">'
					+ 	'<div style="cursor: default;height:25px;" onclick="tems.actions.invoicesSearch.searchPastDue('+data[i].uid+',7);">&nbsp;&nbsp;'
					+ 		'<img src="include/images/light_bulb/red.png" width="16" height="16" align="bottom" />'
					+		'&nbsp;&nbsp;<span style="color:#FF0000; font-weight:bold;font-size:12;">Past Due(<label class="redPastDue_'+data[i].uid+'"></label>)</span>'
					+ 	'</div>'
				+ 	'</li>'
				/*+ 	'<li  style="width:100%;">'
					+ 	'<div style="cursor: default;height:25px;" onclick="tems.actions.invoicesSearch.searchInvoice('+data[i].uid+',7);">&nbsp;&nbsp;'
					+ 		'<img src="include/images/light_bulb/red.png" width=12 height="12" align="bottom" />'
					+		'&nbsp;&nbsp;Due in 7 days('+data[i].redInvoiceCount+')'
					+ 	'</div>'
				+ 	'</li>'*/
			+	'<li>'
		    +		'<div style="cursor: pointer; height:25px; " >'
			+   		'<div style=" float:left; border:none; padding:4px 0 0 5px;margin-left:5px">'
			+   		'<img src="include/images/light_bulb/closeicon.png" width="12" height="12" align="bottom" id="menu'+data[i].uid+divName+'" onclick="tems.actions.compandSubmenu.searchInvoice7(this.id);"/>'	
			+   		'</div>'
      		+	'<div style="float:left; border:none; height:25px; line-height:25px;" onclick="tems.actions.invoicesSearch.searchInvoice('+data[i].uid+',97,\'Due in 7 days\');">'	
      		+		'&nbsp;&nbsp;Due in 7 days (<label class="redInvoiceCount_'+data[i].uid+'"></label>)'	
      		+		'</div>'
      		+'</div>'	
      		+'<ul id="xialaimenu'+data[i].uid+divName+'" style="display:none">'	
      		+	'<li><div style="cursor: pointer; height:25px;" onclick="tems.actions.invoicesSearch.searchInvoice('+data[i].uid+',90,\'Today to Due\');">'
      		+	'<div style=" float:left; border:none; padding:4px 0 0 5px;margin-left:5px">'     
			+   	'<img src="include/images/light_bulb/red.png" width="12" height="12" align="bottom" />'		
			+   	'</div>'	
      		+	'<div style="float:left; border:none; height:25px; line-height:25px;">'	
      		+	'&nbsp;&nbsp;&nbsp;&nbsp;Today to Due (<label class="redInvoiceCountr0_'+data[i].uid+'"></label>)'		
      		+	'</div>'	
      		+	'</div>'	
      		+	'</li>'
      		+	'<li><div style="cursor: pointer; height:25px;" onclick="tems.actions.invoicesSearch.searchInvoice('+data[i].uid+',1,\'1 Day to Due\');">'
      		+	 '<div style=" float:left; border:none; padding:4px 0 0 5px;margin-left:5px">'    
			+   	'<img src="include/images/light_bulb/red.png" width="12" height="12" align="bottom" />'		
			+   	'</div>'	
      		+	'<div style="float:left; border:none; height:25px; line-height:25px;">'	
      		+	'&nbsp;&nbsp;&nbsp;&nbsp;1 Day to Due (<label class="redInvoiceCountr1_'+data[i].uid+'"></label>)'		
      		+	'</div>'	
      		+	'</div>'	
      		+	'</li>'
      		+	'<li><div style="cursor: pointer; height:25px;" onclick="tems.actions.invoicesSearch.searchInvoice('+data[i].uid+',2,\'2 Days to Due\');">'
      		+	'<div style=" float:left; border:none; padding:4px 0 0 5px;margin-left:5px">'    
	 	    +	   	'<img src="include/images/light_bulb/red.png" width="12" height="12" align="bottom" />'		
			+   	'</div>'	
      	    +		'<div style="float:left; border:none; height:25px; line-height:25px;">'	
      	    +		'&nbsp;&nbsp;&nbsp;&nbsp;2 Days to Due (<label class="redInvoiceCountr2_'+data[i].uid+'"></label>)'		
      	    +	'</div>'	
      		+	'</div>'	
      		+	'</li>'
      		+	'<li><div style="cursor: pointer; height:25px;" onclick="tems.actions.invoicesSearch.searchInvoice('+data[i].uid+',3,\'3 Days to Due\');">'
      		+	'<div style=" float:left; border:none; padding:4px 0 0 5px;margin-left:5px">'    
			+   	'<img src="include/images/light_bulb/red.png" width="12" height="12" align="bottom" />'		
			+   	'</div>'	
      		+	'<div style="float:left; border:none; height:25px; line-height:25px;">'	
      		+	'&nbsp;&nbsp;&nbsp;&nbsp;3 Days to Due (<label class="redInvoiceCountr3_'+data[i].uid+'"></label>)'		
      		+	'</div>'	    
      		+	'</div>'	             			
      		+	'</li>'
      		+	'<li><div style="cursor: pointer; height:25px;" onclick="tems.actions.invoicesSearch.searchInvoice('+data[i].uid+',4,\'4 Days to Due\');">'
      		+	'<div style=" float:left; border:none; padding:4px 0 0 5px;margin-left:5px">'   
			+   	'<img src="include/images/light_bulb/red.png" width="12" height="12" align="bottom" />'		
			+   	'</div>'	
      		+	'<div style="float:left; border:none; height:25px; line-height:25px;">'	
      		+	'&nbsp;&nbsp;&nbsp;&nbsp;4 Days to Due (<label class="redInvoiceCountr4_'+data[i].uid+'"></label>)'		
      		+	'</div>'	    
      		+	'</div>'	             			
      		+	'</li>'
      		+	'<li><div style="cursor: pointer; height:25px;" onclick="tems.actions.invoicesSearch.searchInvoice('+data[i].uid+',5,\'5 Days to Due\');">'
      		+	 '<div style=" float:left; border:none; padding:4px 0 0 5px;margin-left:5px">'   
			+   	'<img src="include/images/light_bulb/red.png" width="12" height="12" align="bottom" />'		
			+   	'</div>'	
      		+	'<div style="float:left; border:none; height:25px; line-height:25px;">'	
      		+	'&nbsp;&nbsp;&nbsp;&nbsp;5 Days to Due (<label class="redInvoiceCountr5_'+data[i].uid+'"></label>)'		
      		+	'</div>'	    
      		+	'</div>'	             			
      		+	'</li>'
      		+	'<li><div style="cursor: pointer; height:25px;" onclick="tems.actions.invoicesSearch.searchInvoice('+data[i].uid+',6,\'6 Days to Due\');">'
      		+	'<div style=" float:left; border:none; padding:4px 0 0 5px;margin-left:5px">'    
			+   	'<img src="include/images/light_bulb/red.png" width="12" height="12" align="bottom" />'		
			+   	'</div>'	
      		+	'<div style="float:left; border:none; height:25px; line-height:25px;">'	
      		+	'&nbsp;&nbsp;&nbsp;&nbsp;6 Days to Due (<label class="redInvoiceCountr6_'+data[i].uid+'"></label>)'		
      		+	'</div>'	    
      		+	'</div>'	             			
      		+	'</li>'
            +    '<li><div style="cursor: pointer; height:25px;" onclick="tems.actions.invoicesSearch.searchInvoice('+data[i].uid+',7,\'7 Days to Due\');">'
      		+	'<div style=" float:left; border:none; padding:4px 0 0 5px;margin-left:5px">'    
			+  	'<img src="include/images/light_bulb/red.png" width="12" height="12" align="bottom" />'		
			+  	'</div>'	
      		+	'<div style="float:left; border:none; height:25px; line-height:25px;">'	
      		+	'&nbsp;&nbsp;&nbsp;&nbsp;7 Days to Due (<label class="redInvoiceCounty7_'+data[i].uid+'"></label>)'		
      		+	'</div>'	    
      		+	'</div>'	             			
      		+	'</li>'
      		+	'</ul>'
		    +  '</li>'
		      
		      
		      
		+     '<li>' 
		+   		'<div style="cursor: pointer;height:25px;">'
        +      '<div style=" float:left; border:none; padding:4px 0 0 10px;"><img src="include/images/light_bulb/closeicon.png"width=12 height="12" align="bottom" id ="menusw'+data[i].uid+divName+'" onclick="tems.actions.compandSubmenu.searchInvoice15(this.id);"/></div>'  
        +       '<div style="float:left; border:none; height:25px; line-height:25px;" onclick="tems.actions.invoicesSearch.searchInvoice('+data[i].uid+',915,\'Due in 15 days\');"> &nbsp;&nbsp;Due in 15 days (<label class="yellowInvoiceCount_'+data[i].uid+'"></label>) </div>' 
        +     '</div>' 
    	+	'<ul id="xialaiswmenusw'+data[i].uid+divName+'" style="display:none">'	                  		   
    	+	'<li><div style="cursor: pointer; height:25px;" onclick="tems.actions.invoicesSearch.searchInvoice('+data[i].uid+',8,\'8 Days to Due\');">'	
    	+	'<div style=" float:left; border:none; padding:4px 0 0 5px;margin-left:5px">'	     
		+	 '<img src="include/images/light_bulb/yellow.png" width="12" height="12" align="bottom"/>'  			
		+	 '</div>'  		
    	+	'<div style="float:left; border:none; height:25px; line-height:25px;">'		
    	+	'&nbsp&nbsp;&nbsp;&nbsp;8 Days to Due (<label class="redInvoiceCounty8_'+data[i].uid+'"></label>)'			
    	+	'</div>'		
    	+	'</div>'		
    	+	'</li>'	
    	+	'<li><div style="cursor: pointer; height:25px;" onclick="tems.actions.invoicesSearch.searchInvoice('+data[i].uid+',9,\'9 Days to Due\');">'	
    	+	' <div style=" float:left; border:none; padding:4px 0 0 5px;margin-left:5px">'	   
		+	 '<img src="include/images/light_bulb/yellow.png" width="12" height="12" align="bottom" />'  			
		+	 '</div>'  		
    	+	'<div style="float:left; border:none; height:25px; line-height:25px;">'		
    	+	'&nbsp;&nbsp;&nbsp;&nbsp;9 Days to Due (<label class="redInvoiceCounty9_'+data[i].uid+'"></label>)'			
    	+	'</div>'		
    	+	'</div>'		
    	+	'</li>'	
    	+	'<li><div style="cursor: pointer; height:25px;" onclick="tems.actions.invoicesSearch.searchInvoice('+data[i].uid+',10,\'10 Days to Due\');">'	
    	+	'<div style=" float:left; border:none; padding:4px 0 0 5px;margin-left:5px">'	    
		+	'<img src="include/images/light_bulb/yellow.png" width="12" height="12" align="bottom" />'   			
		+	'</div>'   		
    	+	'<div style="float:left; border:none; height:25px; line-height:25px;">'		
    	+	'&nbsp;&nbsp;&nbsp;&nbsp;10 Days to Due (<label class="redInvoiceCounty10_'+data[i].uid+'"></label>)'			
    	+	'</div>'		    
    	+	'</div>'		             			
    	+	'</li>'	
    	+	'<li><div style="cursor: pointer; height:25px;" onclick="tems.actions.invoicesSearch.searchInvoice('+data[i].uid+',11,\'11 Days to Due\');">'	
    	+	'<div style=" float:left; border:none; padding:4px 0 0 5px;margin-left:5px">'	    
		+	'<img src="include/images/light_bulb/yellow.png" width="12" height="12" align="bottom" />'   			
		+	'</div>'   		
    	+	'<div style="float:left; border:none; height:25px; line-height:25px;">'		
    	+	'&nbsp;&nbsp;&nbsp;&nbsp;11 Days to Due (<label class="redInvoiceCounty11_'+data[i].uid+'"></label>)'			
    	+	'</div> '		   
    	+	'</div>'		             			
    	+	'</li>'	
    	+	'<li><div style="cursor: pointer; height:25px;" onclick="tems.actions.invoicesSearch.searchInvoice('+data[i].uid+',12,\'12 Days to Due\');">'	
    	+	'<div style=" float:left; border:none; padding:4px 0 0 5px;margin-left:5px">'	    
		+	 '<img src="include/images/light_bulb/yellow.png" width="12" height="12" align="bottom" />'  			
		+	 '</div>'  		
    	+	'<div style="float:left; border:none; height:25px; line-height:25px;">'		
    	+	'&nbsp;&nbsp;&nbsp;&nbsp;12 Days to Due (<label class="redInvoiceCounty12_'+data[i].uid+'"></label>)'			
    	+	'</div>'		    
    	+	'</div>'		             			
    	+	'</li>'	
    	+	'<li><div style="cursor: pointer; height:25px;" onclick="tems.actions.invoicesSearch.searchInvoice('+data[i].uid+',13,\'13 Days to Due\');">'	
    	+	'<div style=" float:left; border:none; padding:4px 0 0 5px;margin-left:5px">'	    
		+	'<img src="include/images/light_bulb/yellow.png" width="12" height="12" align="bottom" />'   			
		+	'</div>'   		
    	+	'<div style="float:left; border:none; height:25px; line-height:25px;">'		
    	+	'&nbsp;&nbsp;&nbsp;&nbsp;13 Days to Due (<label class="redInvoiceCounty13_'+data[i].uid+'"></label>)'			
    	+	'</div>'		    
    	+	'</div>'		             			
    	+	'</li>'	
    	+	'<li><div style="cursor: pointer; height:25px;" onclick="tems.actions.invoicesSearch.searchInvoice('+data[i].uid+',14,\'14 Days to Due\');">'	
    	+	'<div style=" float:left; border:none; padding:4px 0 0 5px;margin-left:5px">'	    
		+	'<img src="include/images/light_bulb/yellow.png" width="12" height="12" align="bottom" />'   			
		+	'</div>'   		
    	+	'<div style="float:left; border:none; height:25px; line-height:25px;">'		
    	+	'&nbsp;&nbsp;&nbsp;&nbsp;14 Days to Due (<label class="redInvoiceCounty14_'+data[i].uid+'"></label>)'			
    	+	'</div>'		    
    	+	'</div> '		            			
    	+	'</li>'	
    	+	'<li><div style="cursor: pointer; height:25px;" onclick="tems.actions.invoicesSearch.searchInvoice('+data[i].uid+',15,\'15 Days to Due\');">'	
    	+	'<div style=" float:left; border:none; padding:4px 0 0 5px;margin-left:5px">'	    
		+	'<img src="include/images/light_bulb/yellow.png" width="12" height="12" align="bottom" />'   			
		+	'</div>'   		
    	+	'<div style="float:left; border:none; height:25px; line-height:25px;">'		
    	+	'&nbsp;&nbsp;&nbsp;&nbsp;15 Days to Due (<label class="redInvoiceCounty15_'+data[i].uid+'"></label>)'			
    	+	'</div>'		    
    	+	'</div>'		             			
    	+	'</li>'	
    	+	'</ul>'	
		+  '</li>' 
              
				+ 	'<li style="width:100%;">'
					+ 	'<div style="cursor: default;height:25px;" onclick="tems.actions.invoicesSearch.searchInvoice('+data[i].uid+','+data[i].uid+',\'All my invoices\');">&nbsp;&nbsp;'
					+ 		'<img src="include/images/light_bulb/green.png" width=12 height="12" align="bottom" />'
					+		'&nbsp;&nbsp;All my invoices(<label class="greenInvoiceCount_'+data[i].uid+'"></label>)'
					+ 	'</div>'
				+ 	'</li>'
				// External Approve
				+ 	'<li style="width:100%;">'
					+ 	'<div style="cursor: default;height:25px;" onclick="tems.actions.invoicesSearch.listExternalApproveInvoices('+data[i].uid+')">&nbsp;&nbsp;'
					+ 		'<img src="include/images/light_bulb/green.png" width=12 height="12" align="bottom" />'
					+		'&nbsp;&nbsp;External Approve(<label class="externalApproveInvoiceCount_'+data[i].uid+'"></label>)'
					+ 	'</div>'
				+ 	'</li>'
				+ 	'<li style="width:100%;">'//Add by xin.huang 2011-09-29
					+ 	'<div style="cursor: default;height:25px;" onclick="tems.actions.invoicesSearch.unknownInvoices('+data[i].uid+')">&nbsp;&nbsp;'
					+ 		'<img src="include/images/light_bulb/yellow.png" width=12 height="12" align="bottom" />'
					+		'&nbsp;&nbsp;Unknown Invoices (<label class="unknownInvoicesCount_'+data[i].uid+'"></label>)'
					+ 	'</div>'
				+ 	'</li>'
				+ 	'<li style="width:100%;">'
					+ 	'<div style="cursor: default;height:25px;" onclick="tems.actions.invoicesSearch.missingInvoices('+data[i].uid+')">&nbsp;&nbsp;'
					+ 		'<img src="include/images/light_bulb/red.png" width=12 height="12" align="bottom" />'
					+		'&nbsp;&nbsp;Missing Invoices (<label class="missingInvoicesCount_'+data[i].uid+'"></label>)'
					+ 	'</div>'
				+ 	'</li>'
				+ 	'<li style="width:100%;">'//Add by donghao.guo 2011-09-19
					+ 	'<div style="cursor: default;height:25px;" onclick="tems.actions.invoicesSearch.preloadInvoices('+data[i].uid+')">&nbsp;&nbsp;'
					+ 		'<img src="include/images/light_bulb/yellow.png" width=12 height="12" align="bottom" />'
					+		'&nbsp;&nbsp;Preload Invoices (<label class="preloadInvoicesCount_'+data[i].uid+'"></label>)'
					+ 	'</div>'
				+ 	'</li>'
				+ 	'<li style="width:100%;">'
					+ 	'<div style="cursor: default;height:25px;" onclick="tems.actions.invoicesSearch.invoiceInProcess('+data[i].uid+',\'-1\')">&nbsp;&nbsp;'
					+'<img src="include/images/light_bulb/yellow.png" width=12 height="12" align="bottom" />'
					+		'&nbsp;&nbsp;Payment in Process (<label class="paymentInProcessCount_'+data[i].uid+'"></label>)'
					+ 	'</div>'
				+ 	'</li>'
/*				+ 	'<li style="width:100%;">'
					+ 	'<div style="cursor: default;height:25px;" onclick="tems.actions.invoicesSearch.invoiceInProcess('+data[i].uid+',\'-2\')">&nbsp;&nbsp;'
					+'<img src="include/images/light_bulb/yellow.png" width=12 height="12" align="bottom" />'
					+		'&nbsp;&nbsp;Disputes in Process 2('+data[i].disputesInProcessCount+')'
					+ 	'</div>'
				+ 	'</li>'*/
				+ 	'<li style="width:100%;">'
					+ 	'<div style="cursor: default;height:25px;" onclick="tems.actions.invoicesSearch.invoiceInProcess('+data[i].uid+',\'-3\')">&nbsp;&nbsp;'
					+'<img src="include/images/light_bulb/yellow.png" width=12 height="12" align="bottom" />'
					+		'&nbsp;&nbsp;Payment in Exception (<label class="paymentInExceptionCount_'+data[i].uid+'"></label>)'
					+ 	'</div>'
				+ 	'</li>'
				
					//Add by minyang.li 2012-03-10
				+ 	'<li style="width:100%;">'
					+ 	'<div style="cursor: default;height:25px;" onclick="tems.actions.invoicesSearch.searchInvoiceReject('+data[i].uid+');">&nbsp;&nbsp;'
					+ 		'<img src="include/images/light_bulb/green.png" width=12 height="12" align="bottom" />'
					+		'&nbsp;&nbsp;Invoice Reject(<label class="invoiceRejectCount_'+data[i].uid+'"></label>)'
					+ 	'</div>'
				+ 	'</li>';
				//Dispute Buckets
				divStr += '<li style="width:100%;">'
				+ 	'	<div style="cursor: default;height:25px;">'
				+ 	'		<div style=" float:left; border:none; padding:4px 0 0 10px;">'
				+	'			<img src="include/images/light_bulb/closeicon.png" class="db-fold-menu-icon-'+data[i].uid+'" width="12" height="12"' 
				+ 	'			onclick="tems.actions.compandSubmenu.compandDisputesBucket('+data[i].uid+');"/></div>'
				+ 	'		<div style="float:left; border:none; height:25px; line-height:25px;" onclick="tems.actions.invoicesSearch.searchDisputesBucket('+data[i].uid+', -1, \'Dispute Buckets\');">'
				+ 	'			&nbsp;&nbsp;Dispute Buckets (<label class="totalDisputesBucket_'+data[i].uid+'"></label>)'
				+ 	'		</div>'
				+ 	'	</div>'
				+ 	'<!-- Sub menu items of Disputes Bucket Section -->'
				+ 	'<ul class="disputes-buckets-fold-menu-'+data[i].uid+' submenu-section">'
				+ 	'		<li>'
				+ 	'			<div onclick="tems.actions.invoicesSearch.searchDisputesBucket('+data[i].uid+',30,\'0 - 30 Days\');">'
				+ 	'				<div><img src="include/images/light_bulb/red.png" /></div>'
				+ 	'				<div>0 - 30 Days (<label class="first30Disputes_'+data[i].uid+'"></label>)</div>'
				+ 	'			</div>'
				+ 	'		</li>'
				+ 	'		<li>'
				+ 	'			<div onclick="tems.actions.invoicesSearch.searchDisputesBucket('+data[i].uid+',60,\'31 - 60 Days\');">'
				+ 	'				<div><img src="include/images/light_bulb/red.png" /></div>'
				+ 	'				<div>31 - 60 Days (<label class="range31To60Disputes_'+data[i].uid+'"></label>)</div>'
				+ 	'			</div>'
				+ 	'		</li>'
				+ 	'		<li>'
				+ 	'			<div onclick="tems.actions.invoicesSearch.searchDisputesBucket('+data[i].uid+',90,\'61 - 90 Days\');">'
				+ 	'				<div><img src="include/images/light_bulb/red.png" /></div>'
				+ 	'				<div>61 - 90 Days (<label class="range61To90Disputes_'+data[i].uid+'"></label>)</div>'
				+ 	'			</div>'
				+ 	'		</li>'
				+ 	'		<li>'
				+ 	'			<div onclick="tems.actions.invoicesSearch.searchDisputesBucket('+data[i].uid+',120,\'91 - 120 Days\');">'
				+ 	'				<div><img src="include/images/light_bulb/red.png"/></div>'
				+ 	'				<div>91 - 120 Days (<label class="range91To120Disputes_'+data[i].uid+'"></label>)</div>'    
				+ 	'			</div>'             			
				+ 	'		</li>'
				+ 	'		<li>'
				+ 	'			<div onclick="tems.actions.invoicesSearch.searchDisputesBucket('+data[i].uid+',180,\'121 - 180 Days\');">'
				+ 	'				<div><img src="include/images/light_bulb/red.png" /></div>'
				+ 	'				<div>121 - 180 Days (<label class="range121To180Disputes_'+data[i].uid+'"></label>)</div>'    
				+ 	'			</div>'             			
				+ 	'		</li>'
				+ 	'		<li>'
				+ 	'			<div class="divopen" onclick="tems.actions.invoicesSearch.searchDisputesBucket('+data[i].uid+', 0, \'180+ Days\');">'
				+ 	'				<div><img src="include/images/light_bulb/red.png" /></div>'
				+ 	'				<div>180+ Days (<label class="over180Disputes_'+data[i].uid+'"></label>)</div>'    
				+ 	'			</div>'             			
				+ 	'		</li>'
				+ 	'</ul>'
				+ 	'</li>'
				
				+ '</ul>'
				+ '</li>'
				
	}
	divStr += '</ul>';
	document.getElementById(divId).innerHTML = divStr;
	
	simpleSlide(ulId,"second_head");
}

/**
 * jQuery Function
 * IE is No Slide.
 * Click to fold sections menu or unfold sections menu.
 * So no set 'Slide The Speed'
 */
(function($){
 	simpleSlide = function (id,css){
	
		// Hide next element for all selected elements.
		$("#" + id + " ." + css).each( function () {
			$(this).next().hide();
		});
		
		// Show next element for the first of selected elements.
		// Loop all elements to hide.
		$("#"+id+" ." + css).eq(0).next().show();
		$("#"+id+" ." + css).eq(4).next().show();
		
		// When the selected element is clicked.
		$("#"+id+" ." + css).bind("click", function () {
			
			// Element which is clicked.
			var k = $(this);
		
			$("#"+k.next().attr("id") + " .second_head").each( function (j,m) {
				
					$(m).next().hide();
	
				}
			);
			
			// Loop all elements.
			$("#"+id+" ." + css).each( function (i,n) {
				
				// Contrast text node value.
				if( k.text() == $(n).text() ){ // Value is equal.
					
					if($(n).next().css("display")=="block"){
						$(n).next().hide();
					}else{
						$(n).next().show();
					}
					
				} else { // Value is not equal.
					
					/*if($(n).next().css("display")=="block"){
						$(n).next().hide();
					}*/
					
				}
				
			} );
		} );
		 	  	   
 	} // #simpleSlide
})(jQuery);






// Add by Xin Huang on 2011-08-31 Start
function downloadFileO(name,path){
	var df = document.getElementById("downloadForm_originalFile");
	df.filePath.value = path;
	df.fileName.value = name;
	df.submit();
}

function editNotesWindow() {

	var handleSubmit = function() {
		eval(targetFun);
	};
	var handleCancel = function() {	
		this.cancel();
	};
	
	YAHOO.util.Dom.removeClass("editNotesWindow", "edit-notes-window");

	YAHOO.ccm.container.editNotes = new YAHOO.widget.Dialog("editNotesWindow",
							{ width : "30em",
							  fixedcenter : true,
							  visible : false, 
							  modal : true,
							  constraintoviewport : true,
							  buttons : [{ text:"Save", handler:handleSubmit},
								      { text:"Cancel", handler:handleCancel } ]
							});
	YAHOO.ccm.container.editNotes.render();
}

function showEditNotesWindows(invoiceId, notes) {
	YAHOO.util.Dom.get('__invoice_notes').value = unescape(notes);
	targetFun = "saveNotesData('" + invoiceId + "')";
	YAHOO.ccm.container.editNotes.show();
}

function saveNotesData(invoiceId) {
	
	var callback = {
		success: function(o){
			var data = eval("("+o.responseText+")");
			if(data.error){
				alert("Error: " + data.error);
				return;
			} else {
				tems.actions.invoicesSearch.unknownInvoices('-1');
			}
		},
		failure:showError
	};
	YAHOO.ccm.container.editNotes.hide();
	var notes = YAHOO.util.Dom.get('__invoice_notes').value;
	var previousNotesData = "svo.invoiceId=" + invoiceId + "&svo.note=" + ccmEscape(notes);
	YAHOO.util.Connect.asyncRequest("POST", updateInvoiceNotesAction, callback , previousNotesData);
}


function removeMissingAndUnknownInvoice(id,type,missingEmailFlag){
	if (type == "missing") {
		$("#missingInvoiceNotes").val("");
		$("#missingEmailflag").val(missingEmailFlag);
		YAHOO.ccm.container.removeMissingInvoice.show();
		$("#removeMissingInvoiceId").val(id);
	} else {
		$("#unknownInvoiceNotes").val("");
		YAHOO.ccm.container.removeUnknownInvoice.show();
		$("#removeUnknownInvoiceId").val(id);
	}
}

function checkboxAll(check) {
	if (check.checked){
		$("#ctl00_MainContent_SearchInvoiceControl1_GridView1 input[type=checkbox]").attr("checked","true");
	} else {
		$("#ctl00_MainContent_SearchInvoiceControl1_GridView1 input[type=checkbox]").attr("checked","");
	}
}

function checkChecked(check) {
	if (!check.checked) {
		$("#checkAll").attr("checked","");
	} else {
		if ($("#ctl00_MainContent_SearchInvoiceControl1_GridView1 input[type=checkbox][value!=on]").length == $("#ctl00_MainContent_SearchInvoiceControl1_GridView1 input[type=checkbox][value!=on][checked=true]").length) {
			$("#checkAll").attr("checked","true");
		}
	}
}

/**
 * Dispute bucket 列表中, flag 这一列的提示信息的弹出。
 * @param  {[type]} e             [description]
 * @param  {[type]} promptMessage 提示信息内容。
 * @return {[type]}               [description]
 */
function showDisputeLabel(e, promptMessage) {
	var x=document.body.scrollLeft+e.clientX+20;
	var y=document.body.scrollTop+e.clientY-45;
	var html = '';

	// 信息提示框的位置。
	$(".InSePoput").css({   
		"position": "absolute",   
		"top": y+'px',   
		"left":x+'px'
	});

	html += '<table border="0" align="center" cellpadding="0" cellspacing="0" style="height:65px">';
	html += '<tr>';
	html += '<td align="center">' + promptMessage + '</td>';
	html += '</tr>';
	html += '</table>';
	document.getElementById('labelPanel').innerHTML = html;
	$(".InSePoput").show("slow");
}

/**
 * 当鼠标移出的时候关闭提示信息弹窗框。
 * @return {[type]} [description]
 */
function disableDisputeLabel(){
	$(".InSePoput").hide("slow");
}
function forwordDispute (index) {
	if ((document.getElementById('forwardTo_'+index).value == null || document.getElementById('forwardTo_'+index).value == '')&&(document.getElementById('replyTo_'+index).value == null || document.getElementById('replyTo_'+index).value == '')){
		alert("Please specify a recipient.");
		return;
	}
	if (document.getElementById('forwardTo_'+index).value == null || document.getElementById('forwardTo_'+index).value == '') {
		alert("Please enter an email address in the Forward To field.");
		return;
	}
	if (document.getElementById('replyTo_'+index).value == null || document.getElementById('replyTo_'+index).value == '') {
		alert("Please enter an email address in the Reply To field.");
		return;
	}
	$("#_index").val(index);
	showLoadingModalLayer();
	//window.location.href = "forwordDispute.action";
	document.getElementById('dispute_form_'+index).submit();
	
}
function sendEmailSuccess() {
    alert("Email has been sent successfully.");
	   
    $("#tabForm_"+$("#_index").val()).css("display","none");
	   
    hideLoadingModalLayer();
    
    var index_last = $("#_index_last").val();
    var isAll = true;
	   
   for(var i = 0; i<=index_last ;i++) {
      if($("#tabForm_"+i).css('display')=='block') {
        isAll = false;
        continue;
      }
   }
    if (isAll) {
    	tems.actions.invoicesSearch.searchDisputesBucket(-1, -1, 'Dispute Buckets');;
    }

}

function sendEmailfail() {
	alert("Send email Failed!");
	hideLoadingModalLayer();
	
}


/**
 * 相对于 textarea 的一个小功能，当 textarea 的内容改变的时候，
 * 在这个文本框的下面会动态的显示，剩余多少文字可输入。
 */
function calculateWordLimit( textElement, counterElement ) {

	textElement.keyup(check_keyup).bind('paste', function () {
		setTimeout(check_keyup, 50);
	});
	
	function check_keyup() {
		var num = 500 - textElement.val().length;
		counterElement.html(num);
	}
}

function clearBulkActionRequestDisputeNotes(){
	$('#note_Notes').val('');
	$('#num_characters').html(500);
}
function saveBulkActionRequestDisputeNotes(disputeActionRequestStatus){
	
	var flag=true;
	if($.trim($("#note_Notes").val()) == ""){
		alert('The notes is required!');
		return;
	}
	if($.trim($("#note_Notes").val()).length>500){
		alert('Cannot be more than 500 characters!');
		return;
	}
	
	showLoadingModalLayer();

	$.ajax({
		url: 'saveDisputeActionRequest.action',
		type: 'POST',
		dataType: 'text',
		data: {actionRequestNotes: $('#note_Notes').val().trim(), disputeId: $('#disputeNoteByDisputeIdId').val(),disputeActionRequestStatus:disputeActionRequestStatus },
		success: function(o) {
			var obj = eval("(" + o + ")");
			hideLoadingModalLayer();
			if(obj.success === 'success') {
				alert('Save successfully.');
				YAHOO.ccm.container.bulkActionRequestDisputeWindow.hide();
			}
			if(obj.error){
				alert(obj.error);
			}
		},
		error: function() {
			alert('Save the dispute action request failed!');
		}
	});
	
}