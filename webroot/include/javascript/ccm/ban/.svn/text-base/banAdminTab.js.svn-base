// @Auchor chao.Liu (Optimization of complete by xinyu.Liu)
YAHOO.util.Event.onDOMReady(function () {
	banMaintenanceTab.initBanPage.initPage();
	common_Json.clearForm_YUI("banPage_frm");
});

var banMaintenanceTab = {
	url : {
		deleteBanByIdUri : "deleteBanById.action",
		getBanTotalPageNoUri : "doGetBanTotalNO.action",
		searchBanUri : "doSearchBanList.action",
		downloadExcelBySearchBanUri : "saveDownloadBanToExcel.action"
	},
	initBanPage : {
		searchPostData : function (){
			var postData = "";
			if(banPageVendorFlexbox.getValue()!="null") postData += ("&bvo.vendorId=" + banPageVendorFlexbox.getValue());
			if($('#banPage_frm_bvo_banStatusId').val()!="all") postData += ("&bvo.banStatusId=" + $('#banPage_frm_bvo_banStatusId').val());
			if($('#banPage_frm_bvo_invoiceChannelId').val()!="all") postData += ("&bvo.invoiceChannelId=" + $('#banPage_frm_bvo_invoiceChannelId').val());
			if($('#banPage_frm_bvo_invoiceFormatId').val()!="all") postData += ("&bvo.invoiceFormatId=" + $('#banPage_frm_bvo_invoiceFormatId').val());
			if($('#banPage_frm_bvo_paymentMethodId').val()!="all") postData += ("&bvo.paymentMethodId=" + $('#banPage_frm_bvo_paymentMethodId').val());
			if($('#banPage_frm_bvo_analystId').val()!="all") postData += ("&bvo.analystId=" + $('#banPage_frm_bvo_analystId').val());
			if($('#banPage_frm_bvo_lineofBbusiness').val()!="all") postData += ("&bvo.lineofBbusiness=" + $('#banPage_frm_bvo_lineofBbusiness').val());
			if($('#banPage_frm_bvo_banInactiveReasonId').val()!="all") postData += ("&bvo.banInactiveReasonId=" + $('#banPage_frm_bvo_banInactiveReasonId').val());
			if($('#banPage_frm_bvo_accountNumber').val()!="") postData += ("&bvo.accountNumber=" + $('#banPage_frm_bvo_accountNumber').val());
			if($('#banPage_frm_bvo_aPSupplierNumber').val()!="") postData += ("&bvo.aPSupplierNumber=" + $('#banPage_frm_bvo_aPSupplierNumber').val());
			if($('#banPage_frm_bvo_aPSupplierName').val()!="") postData += ("&bvo.aPSupplierName=" + $('#banPage_frm_bvo_aPSupplierName').val());
			if($('#banPage_frm_bvo_aPSupplierSite').val()!="") postData += ("&bvo.aPSupplierSite=" + $('#banPage_frm_bvo_aPSupplierSite').val());
			if($('#banPage_frm_bvo_company').val()!="") postData += ("&bvo.company=" + $('#banPage_frm_bvo_company').val());
			if($('#banPage_frm_bvo_location').val()!="") postData += ("&bvo.location=" + $('#banPage_frm_bvo_location').val());
			if($('#banPage_frm_bvo_channel').val()!="") postData += ("&bvo.channel=" + $('#banPage_frm_bvo_channel').val());
			
			return postData;
		},
		initPage : function(){
			banMaintenanceTab.banPage = new SANINCO.Page('showInfo_div', 'banMaintenanceTab.banPage', {
				sortingField: 'b.account_number',
				sortingDirection: 'asc',
				vo: 'bvo',
				pageTable: 'block',
				totalPageUri: banMaintenanceTab.url.getBanTotalPageNoUri,
				dataUri: banMaintenanceTab.url.searchBanUri,
				recPerArray: [10, 20, 30, 40, 50, 70, 90, 100],
				cols: [
					{title: "Delete",dataField: function (row){
							return '<img src="include/images/reject16.png" onclick="banMaintenanceTab.deleteBanByBanId(\''+row.banId+'\')" />';
						}
			  	 },{title: "Account Number",dataField: function(o){
							return "<a href=\"banDetailAction.action?bvo.banId=" + o.banId + "\">" + o.accountNumber + "</a>";
						 },sort: "b.account_number",filtration: {name: "b.account_number",alias: "Account Number"}
				 },{title: "Vendor Name",dataField: "vendorName",sort: "v.vendor_name",filtration: {name: "v.vendor_name",alias: "Vendor Name"}
				 },{title: "Original Account Number",dataField: "originalAccountNumber",sort: "b.original_account_number",filtration: {name: "b.original_account_number",alias: "Original Account Number"}
				 },{title: "AP Supplier Number",dataField: "apSupplierNumber",sort: "b.ap_supplier_number",filtration: {name: "b.ap_supplier_number",alias: "AP Supplier Number"}
				 },{title: "AP Supplier Name",dataField: "apSupplierName",sort: "b.ap_supplier_name",filtration: {name: "b.ap_supplier_name",alias: "AP Supplier Name"}
				 },{title: "AP Supplier Site*",dataField: "apSupplierSite",sort: "b.ap_supplier_site",filtration: {name: "b.ap_supplier_site",alias: "AP Supplier Site*"}
				 },{title: "AP Customer Name",dataField: "apCustomerName",sort: "b.ap_customer_name",filtration: {name: "b.ap_customer_name",alias: "AP Customer Name"}
				 },{title: "Ban Create Date",dataField: "banCreateDate",sort: "b.ban_create_date",filtration: {name: "b.ban_create_date",alias: "Ban Create Date"}
				 },{title: "Ban Close Date",dataField: "banCloseDate",sort: "b.ban_close_date",filtration: {name: "b.ban_close_date",alias: "Ban Close Date"}
				 },{title: "Usage Backbill Months",dataField: "usageBackbillMonths",sort: "b.usage_backbill_months",filtration: {name: "b.usage_backbill_months",alias: "Usage Backbill Months"}
				 },{title: "Bill Day",dataField: "billDay",sort: "b.bill_day",filtration: {name: "b.bill_day",alias: "Bill Day"}
				 },{title: "Ban Status",dataField: "banStatus",sort: "bs.ban_status_name",filtration: {name: "bs.ban_status_name",alias: "Ban Status"}
				 },{title: "Account Type",dataField: "accountType",sort: "a_t.account_type_name",filtration: {name: "a_t.account_type_name",alias: "Account Type"}
				 },{title: "Invoice Frequency ",dataField: "invoiceFrequency",sort: "b.invoice_frequency",filtration: {name: "b.invoice_frequency",alias: "Invoice Frequency"}
				 },{title: "Invoice Channel",dataField: "invoiceChannel",sort: "ic.invoice_channel_name",filtration: {name: "ic.invoice_channel_name",alias: "Invoice Channel"}
				 },{title: "Invoice Format",dataField: "invoiceFormat",sort: "i_f.invoice_format_code",filtration: {name: "i_f.invoice_format_code",alias: "Invoice Format"}
				 },{title: "Missing Invoice Email",dataField: "missingInvoiceEmail",sort: "b.Missing_Email_flag",filtration: {name: "b.Missing_Email_flag",alias: "Missing Invoice Email"}
				 },{title: "Payment Method ",dataField: "paymentMethod",sort: "pm.payment_method_code",filtration: {name: "pm.payment_method_code",alias: "Payment Method "}
				 },{title: "Payment Term",dataField: "paymentTerm",sort: "pt.payment_term_code",filtration: {name: "pt.payment_term_code",alias: "Payment Term"}
				 },{title: "Payment Group ",dataField: "paymentGroup",sort: "pg.payment_group_code",filtration: {name: "pg.payment_group_code",alias: "Payment Group "}
				 },{title: "Company",dataField: "company",sort: "b.company",filtration: {name: "b.company",alias: "Company"}
				 },{title: "Location ",dataField: "location",sort: "b.location",filtration: {name: "b.location",alias: "Location "}
				 },{title: "Department",dataField: "department",sort: "b.department",filtration: {name: "b.department",alias: "Department"}
				 },{title: "Channel ",dataField: "channel",sort: "b.channel",filtration: {name: "b.channel",alias: "Channel "}
				 },{title: "Analyst",dataField: "analyst",sort: "concat(ua.first_name,blank_space(),ua.last_name)",filtration: {name: "concat(ua.first_name,blank_space(),ua.last_name)",alias: "Analyst"}
				 },{title: "Approver1",dataField: "approver1",sort: "concat(ua1.first_name,blank_space(),ua1.last_name)",filtration: {name: "concat(ua1.first_name,blank_space(),ua1.last_name)",alias: "Approver1"}
				 },{title: "approver2 ",dataField: "approver2",sort: "ua2.user_name",filtration: {name: "ua2.user_name",alias: "approver2 "}
				 },{title: "approver3",dataField: "approver3",sort: "v.vendor_name",filtration: {name: "v.vendor_name",alias: "approver3"}
				 },{title: "Line of business",dataField: "lineOfBusiness",sort: "b.line_of_business",filtration: {name: "b.line_of_business",alias: "Line of business"}
				 },{title: "Ban Inactive Reason",dataField: "description",sort: "b.ban_inactive_reason_id",filtration: {name: "b.ban_inactive_reason_id",alias: "Ban Inactive Reason"}
				 },{title: "Notes",className : "table_td_br",width:"300px",dataField: "notes",sort: "b.notes",filtration: {name: "b.notes",alias: "Notes"}
				 },{title: "Currency",dataField: "currency",sort: "c.currency_name",filtration: {name: "c.currency_name",alias: "Currency"}
				 },{title: "Status Time",dataField: "statusTimestamp",sort: "b.status_timestamp",filtration: {name: "b.status_timestamp",alias: "Status Time"}
				 },{title: "Ban Primary Contact",dataField: "banPrimiaryContact",sort: "concat(toJSON(cpric.first_name is null,blank_space(),cpric.first_name),blank_space(),toJSON(cpric.last_name is null,blank_space(),cpric.last_name),blank_space(),toJSON(cpric.email is null,blank_space(),cpric.email))",filtration: {name: "concat(toJSON(cpric.first_name is null,blank_space(),cpric.first_name),blank_space(),toJSON(cpric.last_name is null,blank_space(),cpric.last_name),blank_space(),toJSON(cpric.email is null,blank_space(),cpric.email))",alias: "Ban Primary Contact"}
				 },{title: "Ban Payment Contact",dataField: "banPaymentContact",sort: "concat(toJSON(cpayc.first_name is null,blank_space(),cpayc.first_name),blank_space(),toJSON(cpayc.last_name is null,blank_space(),cpayc.last_name),blank_space(),toJSON(cpayc.email is null,blank_space(),cpayc.email))",filtration: {name: "concat(toJSON(cpayc.first_name is null,blank_space(),cpayc.first_name),blank_space(),toJSON(cpayc.last_name is null,blank_space(),cpayc.last_name),blank_space(),toJSON(cpayc.email is null,blank_space(),cpayc.email))",alias: "Ban Payment Contact"}
				 },{title: "Ban Dispute Contact",dataField: "banDisputeContact",sort: "concat(toJSON(cdisc.first_name is null,blank_space(),cdisc.first_name),blank_space(),toJSON(cdisc.last_name is null,blank_space(),cdisc.last_name),blank_space(),toJSON(cdisc.email is null,blank_space(),cdisc.email))",filtration: {name: "concat(toJSON(cdisc.first_name is null,blank_space(),cdisc.first_name),blank_space(),toJSON(cdisc.last_name is null,blank_space(),cdisc.last_name),blank_space(),toJSON(cdisc.email is null,blank_space(),cdisc.email))",alias: "Ban Dispute Contact"}
				 },{title: "Ban Tariff Contact",dataField: "banTariffContact",sort: "concat(toJSON(ctarc.first_name is null,blank_space(),ctarc.first_name),blank_space(),toJSON(ctarc.last_name is null,blank_space(),ctarc.last_name),blank_space(),toJSON(ctarc.email is null,blank_space(),ctarc.email))",filtration: {name: "concat(toJSON(ctarc.first_name is null,blank_space(),ctarc.first_name),blank_space(),toJSON(ctarc.last_name is null,blank_space(),ctarc.last_name),blank_space(),toJSON(ctarc.email is null,blank_space(),ctarc.email))",alias: "Ban Tariff Contact"}
				 },{title: "Ban Billing Contact",dataField: "banBillingContact",sort: "concat(toJSON(cbilc.first_name is null,blank_space(),cbilc.first_name),blank_space(),toJSON(cbilc.last_name is null,blank_space(),cbilc.last_name),blank_space(),toJSON(cbilc.email is null,blank_space(),cbilc.email))",filtration: {name: "concat(toJSON(cbilc.first_name is null,blank_space(),cbilc.first_name),blank_space(),toJSON(cbilc.last_name is null,blank_space(),cbilc.last_name),blank_space(),toJSON(cbilc.email is null,blank_space(),cbilc.email))",alias: "Ban Billing Contact"}
				 },{title: "Created By ",dataField: "createdBy",sort: "concat(uc.first_name,blank_space(),uc.last_name)",filtration: {name: "concat(uc.first_name,blank_space(),uc.last_name)",alias: "Created By "}
				 },{title: "Created Time",dataField: "createdTimestamp",sort: "b.created_timestamp",filtration: {name: "b.created_timestamp",alias: "Created Time"}
				 },{title: "Modified By ",dataField: "modifiedBy",sort: "concat(um.first_name,blank_space(),um.last_name)",filtration: {name: "concat(um.first_name,blank_space(),um.last_name)",alias: "Modified By "}
				 },{title: "Modified Time ",dataField: "modifiedTimestamp",sort: "b.modified_timestamp",filtration: {name: "b.modified_timestamp",alias: "Modified Time "}
				}]
			});
			
			banFilter = new SANINCO.Filter();
			banFilter.addEditeEvent(function(){
				banMaintenanceTab.banPage.start();
			});
			banFilter.add('b.account_number', 'String');
			banFilter.add('v.vendor_name', 'String');
			banFilter.add('b.original_account_number', 'Number');
			banFilter.add('b.ap_supplier_number', 'Number');
			banFilter.add('b.ap_supplier_name', 'String');
			banFilter.add('b.ap_supplier_site', 'String');
			banFilter.add('b.ap_customer_name', 'String');
			banFilter.add('b.ban_create_date', 'Date');
			banFilter.add('b.ban_close_date', 'Date');
			banFilter.add('b.usage_backbill_months', 'String');
			banFilter.add('b.bill_day', 'String');
			banFilter.add('b.invoice_frequency', 'String');
			banFilter.add('b.company', 'String');
			banFilter.add('b.location', 'String');
			banFilter.add('b.department', 'String');
			banFilter.add('b.channel', 'String');
			banFilter.add('b.line_of_business', 'String');
			banFilter.add('b.ban_inactive_reason_id', 'Number');
			banFilter.add('b.status_timestamp', 'String');
			banFilter.add('b.notes', 'String');
			banFilter.add('b.created_timestamp', 'Date');
			banFilter.add('b.modified_timestamp', 'Date');
			banFilter.add("concat(uc.first_name,blank_space(),uc.last_name)", 'String');
			banFilter.add('concat(um.first_name,blank_space(),um.last_name)', 'String');
			banFilter.add('bs.ban_status_name', 'String');
			banFilter.add('a_t.account_type_name', 'String');
			banFilter.add('ic.invoice_channel_name', 'String');
			banFilter.add('i_f.invoice_format_code', 'String');
			banFilter.add('b.Missing_Email_flag', 'String');
			banFilter.add('pm.payment_method_code', 'String');
			banFilter.add('pt.payment_term_code', 'String');
			banFilter.add('pg.payment_group_code', 'String');
			banFilter.add('c.currency_name', 'String');
			banFilter.add('concat(ua.first_name,blank_space(),ua.last_name)', 'String');
			banFilter.add('concat(ua1.first_name,blank_space(),ua1.last_name)', 'String');
			//banFilter.add('concat(ua2.first_name,blank_space(),ua2.last_name)', 'String');
			//modify by donghao.guo
			banFilter.add('ua2.user_name', 'String');
			banFilter.add('concat(ua3.first_name,blank_space(),ua3.last_name)', 'String');
			banFilter.add('concat(toJSON(cpric.first_name is null,blank_space(),cpric.first_name),blank_space(),toJSON(cpric.last_name is null,blank_space(),cpric.last_name),blank_space(),toJSON(cpric.email is null,blank_space(),cpric.email))', 'String');
			banFilter.add('concat(toJSON(cpayc.first_name is null,blank_space(),cpayc.first_name),blank_space(),toJSON(cpayc.last_name is null,blank_space(),cpayc.last_name),blank_space(),toJSON(cpayc.email is null,blank_space(),cpayc.email))', 'String');
			banFilter.add('concat(toJSON(cdisc.first_name is null,blank_space(),cdisc.first_name),blank_space(),toJSON(cdisc.last_name is null,blank_space(),cdisc.last_name),blank_space(),toJSON(cdisc.email is null,blank_space(),cdisc.email))', 'String');
			banFilter.add('concat(toJSON(ctarc.first_name is null,blank_space(),ctarc.first_name),blank_space(),toJSON(ctarc.last_name is null,blank_space(),ctarc.last_name),blank_space(),toJSON(ctarc.email is null,blank_space(),ctarc.email))', 'String');
			banFilter.add('concat(toJSON(cbilc.first_name is null,blank_space(),cbilc.first_name),blank_space(),toJSON(cbilc.last_name is null,blank_space(),cbilc.last_name),blank_space(),toJSON(cbilc.email is null,blank_space(),cbilc.email))', 'String');
			banMaintenanceTab.banPage.setFilter(banFilter);
			
			banMaintenanceTab.banPage.addTotalSuccessEvent(function(data){
				var disabled = "";
	            if (data.totalResultNo == 0 || data.error) disabled = "disabled";
				common_Json.getDOM('btnBanPageDownExcel').disabled = disabled;
	
	        });

		}
	},
	searchBan : function (){
		banFilter.clearAll();
		banMaintenanceTab.banPage.myParam = banMaintenanceTab.initBanPage.searchPostData();
		banMaintenanceTab.banPage.start();
		$("#showInfo_div").show();
	},
	newBanHref : function (){
		window.location = "banDetailAction.action";
	},
	downloadToExcel : function (){
		showLoadingModalLayer();
		var titles = banMaintenanceTab.banPage.getTitlesParam("titles");
		window.location = banMaintenanceTab.url.downloadExcelBySearchBanUri + "?" + titles + "&" + banMaintenanceTab.banPage.paramStr;
		hideLoadingModalLayer();
	},
	tabClearl : function (){
		$("#showInfo_div").hide();
		banPageVendorFlexbox.setValue('null');
		common_Json.clearForm_YUI('banPage_frm');
		common_Json.getDOM("btnBanPageDownExcel").disabled = "disabled";
	},
	deleteBanByBanId : function (id){
		if (window.confirm("Click OK to delete!")) {
			var callback = {
				success: function(o){
					if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
						window.location.reload();
						return;
					}
					var data = eval("("+o.responseText+")");
					hideLoadingModalLayer();
					if(data.error){
						alert(data.error);
						return;
					}
					banMaintenanceTab.searchBan();
				},
				failure: showError
			};
			var starParamer = "bvo.banId=" + ccmEscape(id);
			showLoadingModalLayer();
			YAHOO.util.Connect.asyncRequest('POST', banMaintenanceTab.url.deleteBanByIdUri, callback, starParamer);
		} 
	}
}

