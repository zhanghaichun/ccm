/**
 * @author Jian.Dong
 * Modified By Chao.Liu to 10/06/09 PM
 */
jQuery(function($){
	var Y = YAHOO;
	BUSINESS = new function(){
		
		// Invoices filter fields set.
		var ifs = [
			["i.invoice_number","String"],
			["b.account_number","String"],
			["v.vendor_name","String"],
			["b.line_of_business","String"],
			["i.invoice_date","Date"],
			["if(i.invoice_current_due IS NULL, 0, i.invoice_current_due)","String"],
			["i.original_flag","String"],
			["concat(u.first_name,blank_space(),u.last_name)","String"],
			["i.invoice_due_date","Date"],
			["i.invoice_total_due","String"],
			["i.payment_amount","String"],
			["i.dispute_amount","String"],
			["if(i.misc_adjustment_amount IS NULL, 0,i.misc_adjustment_amount)","String"],
			["s.invoice_status_name","String"],
			["datediff(now(),i.status_timestamp)","String"],
			["ps.payment_status_name","String"],
			["p.payment_date","Date"],
			["p.payment_transaction_number","String"],
			["p.payment_reference_code","String"]			
		];
		
		// Invoices filter.
		var invoiceFilter = new SANINCO.Filter();
		invoiceFilter.addEditeEvent(function(){
			BUSINESS.service.startSearch();
		});
		
		for(var i=0; i<ifs.length; i++){
			invoiceFilter.add(ifs[i][0],ifs[i][1]);
		}

		// External Approve bucket 中的过滤字段，
		// 基于 invoice bucket filters. 
		var externalApproveBucketFilters = [
			["i.invoice_number","String"],
			["w.workflow_action_name","String"],		
			["t.notes","String"],
			["b.account_number","String"],
			["v.vendor_name","String"],
			["b.line_of_business","String"],
			["i.invoice_date","Date"],
			["if(i.invoice_current_due IS NULL, 0, i.invoice_current_due)","String"],
			["i.original_flag","String"],
			["concat(u.first_name,blank_space(),u.last_name)","String"],
			["i.invoice_due_date","Date"],
			["i.invoice_total_due","String"],
			["i.payment_amount","String"],
			["i.dispute_amount","String"],
			["if(i.misc_adjustment_amount IS NULL, 0,i.misc_adjustment_amount)","String"],
			["s.invoice_status_name","String"],
			["datediff(now(),i.status_timestamp)","String"],
			["ps.payment_status_name","String"],
			["p.payment_date","Date"],
			["p.payment_transaction_number","String"],
			["p.payment_reference_code","String"]
					
		];
		
		// External Approve bucket filter 过滤功能实例。
		var externalApproveBucketFilterInstance = new SANINCO.Filter();
		externalApproveBucketFilterInstance.addEditeEvent(function(){
			BUSINESS.service.startSearch();
		});
		
		// 迭代：将所有需要过滤的字段添加到 filter 实例中。
		for(var i = 0; i < externalApproveBucketFilters.length; i++){
			externalApproveBucketFilterInstance.add(externalApproveBucketFilters[i][0], externalApproveBucketFilters[i][1]);
		}
		
		// Unknown invoices filter fields set.
		var ufs = [
			["i.bar_code","String"],
			["b.account_number","String"],
			["v.vendor_name","String"],
			["i.invoice_date","Date"],
			["i.invoice_due_date","Date"],
			["if(i.invoice_current_due IS NULL, 0, i.invoice_current_due)","String"],
			["i.invoice_total_due","String"],
			["concat(u.first_name,blank_space(),u.last_name)","String"],
			["i.invoice_receive_date","Date"],
			["s.invoice_status_name","String"],
			["datediff(now(),i.status_timestamp)","String"],
			["i.notes","String"],
			["o.file_name","String"]
		];
		
		// Unknown invoices filter.
		var unknownInvoicesFilter = new SANINCO.Filter();
		unknownInvoicesFilter.addEditeEvent(function(){
			BUSINESS.service.startSearch();
		});
		
		for(var i=0; i<ufs.length; i++){
			unknownInvoicesFilter.add(ufs[i][0],ufs[i][1]);
		}
		
		// Missing invoices filter fields set.
		var mifs = [
			["b.account_number","String"], 
			["i.original_flag","String"], 
			["v.vendor_name","String"], 
			["concat(u.first_name,blank_space(),u.last_name)","String"],
			["s.invoice_status_name","String"],
			["datediff(now(),i.status_timestamp)","String"],
			["i.invoice_expecting_date","Date"],
			["i.last_dispute","String"],
			["i.last_payment","String"],
			["i.last_total_due","String"],
			
		];
		
		// Missing invoices filter.
		var missingInvoicesFilter = new SANINCO.Filter();
		missingInvoicesFilter.addEditeEvent(function(){
			BUSINESS.service.startSearch();
		});
		
		for(var i=0; i<mifs.length; i++){
			missingInvoicesFilter.add(mifs[i][0],mifs[i][1]);
		}
		
		//Add by donghao.guo 2011-09-19.
		//Preload Invoices Filter fields set.
		var pifs=[
			["i.invoice_number","String"],
			["i.bar_code","String"],
			["b.account_number","String"],
			["v.vendor_name","String"],
			["i.invoice_date","Date"],
			["i.invoice_due_date","Date"],
			["i.invoice_receive_date","Date"],
			["if(i.invoice_current_due IS NULL, 0, i.invoice_current_due)","String"],
			["i.invoice_total_due","String"],
			["concat(u.first_name,blank_space(),u.last_name)","String"],
			["s.invoice_status_name","String"],
			["datediff(now(),i.status_timestamp)","String"]
		];
		
		// Preload invoices filter.
		var preloadInvoicesFilter = new SANINCO.Filter();
		preloadInvoicesFilter.addEditeEvent(function(){
			BUSINESS.service.startSearch();
		});
		
		// Add filter dialog for every field.
		for(var i=0; i<pifs.length; i++){
			preloadInvoicesFilter.add(pifs[i][0],pifs[i][1]);
		}
		
		// Disputes Bucket Filter fields set.
		var dbfs = [
		    ["d.dispute_number", "String"], // Disputes number.
		    ["d.claim_number", "String"], // Claim number
		    ["v.vendor_name", "String"], // Vendor.
		    ["i.account_number","String"], // Ban.
		    ["i.invoice_number", "String"], // Invoice number.
		    ["d.dispute_amount", "String"], // Dispute amount.
		    ["d.dispute_balance", "String"], // Outstanding Dispute Balance.
		    ["Date(d.created_timestamp)", "Date"], // Dispute date.
			["dr.dispute_reason_name", "String"], // Dispute category.
			["datediff(now(), d.status_timestamp)", "Date"], // Days in status.
			["dispute_created_by", "String"], //  Dispute created by.
			["assigned_to", "String"], // Assigned To.
			["concat(u.first_name,blank_space(),u.last_name)", "String"], // Dispute Owner.
			["d.flag_shortpaid", "String"], // Short paid disput.
			["d.flag_recurring", "String"] // flag recurring.
		];
		
		// Dispute  bucket filter.
		var disputesBucketFilter = new SANINCO.Filter();
		disputesBucketFilter.addEditeEvent(function(){
			BUSINESS.service.startSearch();
		});
		
		for(var i=0; i<dbfs.length; i++){
			disputesBucketFilter.add(dbfs[i][0],dbfs[i][1]);
		}
		
		
		var banf=[
				  ["b.account_number","String"],
				  ["b.original_account_number","String"],
				  ["b.original_account_number","String"],
				  ["b.ap_supplier_number","String"],
	              ["b.ap_supplier_name","String"],
	              ["b.ap_supplier_site","String"],
	              ["b.ap_customer_name","String"],
	              ["b.ban_create_date","String"],
	              ["b.ban_close_date","String"],
	              ["b.usage_backbill_months","String"],
	              ["b.bill_day","String"],
	              ["bs.ban_status_name","String"],
	              ["a_t.account_type_name","String"],
	              ["b.invoice_frequency","String"],
	              ["ic.invoice_channel_name","String"],
	              ["i_f.invoice_format_code","String"],
	              ["b.Missing_Email_flag","String"],
	              ["pm.payment_method_code","String"],
	              ["pt.payment_term_code","String"],
	              ["pg.payment_group_code","String"],
	              ["b.company","String"],
	              ["b.location","String"],
	              ["b.department","String"],
	              ["b.channel","String"],
	              ["concat(ua.first_name,blank_space(),ua.last_name)","String"],
	              ["concat(ua1.first_name,blank_space(),ua1.last_name)","String"],
	              ["ua2.user_name","String"],
	              ["v.vendor_name","String"],
	              ["b.line_of_business","String"],
	              ["b.ban_inactive_reason_id","String"],
	              ["b.notes","String"],
	              ["c.currency_name","String"],
	              ["b.status_timestamp","String"],
	              ["concat(toJSON(cpric.first_name is null,blank_space(),cpric.first_name),blank_space(),toJSON(cpric.last_name is null,blank_space(),cpric.last_name),blank_space(),toJSON(cpric.email is null,blank_space(),cpric.email))","String"],
	              ["concat(toJSON(cpayc.first_name is null,blank_space(),cpayc.first_name),blank_space(),toJSON(cpayc.last_name is null,blank_space(),cpayc.last_name),blank_space(),toJSON(cpayc.email is null,blank_space(),cpayc.email))","String"],
	              ["concat(toJSON(cdisc.first_name is null,blank_space(),cdisc.first_name),blank_space(),toJSON(cdisc.last_name is null,blank_space(),cdisc.last_name),blank_space(),toJSON(cdisc.email is null,blank_space(),cdisc.email))","String"],
	              ["concat(toJSON(ctarc.first_name is null,blank_space(),ctarc.first_name),blank_space(),toJSON(ctarc.last_name is null,blank_space(),ctarc.last_name),blank_space(),toJSON(ctarc.email is null,blank_space(),ctarc.email))","String"],
	              ["concat(toJSON(cbilc.first_name is null,blank_space(),cbilc.first_name),blank_space(),toJSON(cbilc.last_name is null,blank_space(),cbilc.last_name),blank_space(),toJSON(cbilc.email is null,blank_space(),cbilc.email))","String"],
	              ["concat(uc.first_name,blank_space(),uc.last_name)","String"],
	              ["b.created_timestamp","String"],
	              ["concat(um.first_name,blank_space(),um.last_name)","String"],
	              ["b.modified_timestamp","String"]
				];
				
		var banFilter = new SANINCO.Filter();
		banFilter.addEditeEvent(function(){
			BUSINESS.service.startSearch();
		});
		
		for(var i=0; i<banf.length; i++){
			banFilter.add(banf[i][0],banf[i][1]);
		}
		
		
		
		
		
		/*
		 * Core set.
		 * */
		var C = this.config = {
			base : {
				params : {//search parameters
					"sortField" : "i.id",
					"sortingDirection" : "asc",
					"pageNo" : 1,
					
					// At the beginning, show 10 items in every page. 
					"recPerPage" : 10
				},
			},
			
			// Core page information.
			page : {
				//Rendering table and rendering page configurations.
				tableId : "_dataTable_",
				paginationTableId : "_paginationTable_",
				curPageNoId : "__curPageNo_",
				totalPageNoId : "__totalPageNo_", 
				
				// According to the number of this container
				// to show the data items of every page.
				pageSelectId : "_recPerPageSelect_",
				
				//getSortingDirectionString configurations.
				sorting : {
					fieldNameAtParams : "sortField",
					directionAtParams : "sortingDirection",
					upImg : '<img src="include/images/upsort.gif">',
					downImg : '<img src="include/images/downsort.gif">'
				},
				// Rendering page configurations.
				pageNoNameAtParams : "pageNo", // Page number
				recPerPageNameAtParams : "recPerPage" // Quantity of records in every page. 
			},
			
			// Core request service information and configuration.
			service : {
				mainId : "pageContainer",
				targetId : "pageTargetId",
				templateId : "_mainContentTemplate",
				name : "",
				templateHtml : "",
				Invoice : {
					searchVoStr : "wvo.",
					totalPageNoAction : "doGetInvoiceTotalPageNoInCommonLookup.action",
					searchDataUri : "doSearchInvoiceInCommonLookup.action",
					columnList : ["i.invoice_number","b.account_number","v.vendor_name","b.line_of_business","i.invoice_date",
					              "if(i.invoice_current_due IS NULL, 0, i.invoice_current_due)","i.original_flag",
					              "concat(u.first_name,blank_space(),u.last_name)","i.invoice_due_date","i.invoice_total_due",
					              "i.payment_amount","i.dispute_amount","if(i.misc_adjustment_amount IS NULL, 0,i.misc_adjustment_amount)",
					              "s.invoice_status_name","datediff(now(),i.status_timestamp)","ps.payment_status_name","p.payment_date",
					              "p.payment_transaction_number","p.payment_reference_code"],
					columnNameList : ["Invoice Number","BAN","Vendor ","LOB","Invoice Date","Invoice Current Due","Original",
					                  "Analyst","Due Date","Total Due","Payment Amount","Dispute Amount","Misc Adjustment Amount",
					                  "Invoice Status","Days in Status","Payment Status","Payment Date","Payment Transaction Number",
					                  "Payment Reference Number"],
					dataFieldList : ["no","ban","vendor","lob","date","currentDue","originalFlag","analyst","due","total","paymentAmount",
					                 "disputeAmount","miscAdjustmentAmount","status","daysInStatus","paymentStatus","paymentDate",
					                 "paymentTransactionNumber","paymentReferenceNumber"],
					colRenders : {
						"no" : function(row){
							return '<a href="viewInvoiceDetails.action?invoiceId='+row.id+'">'+row.no+'</a>';
						}
					},
					filter:invoiceFilter
				},
				/**
				 * External Approve bucket invoice list.
				 */
				ExternalApproveInvoices : {
					searchVoStr : "wvo.",
					totalPageNoAction : "countExternalApproveInvoicePageNo.action",
					searchDataUri : "listExternalApproveInvoiceItems.action",
					columnList : ["i.invoice_number", "w.workflow_action_name", "t.notes", "b.account_number", "v.vendor_name", "b.line_of_business", "i.invoice_date", 
					              "if(i.invoice_current_due IS NULL, 0, i.invoice_current_due)", "i.original_flag", 
					              "concat(u.first_name,blank_space(),u.last_name)", "i.invoice_due_date", "i.invoice_total_due", 
					              "i.payment_amount", "i.dispute_amount", "if(i.misc_adjustment_amount IS NULL, 0,i.misc_adjustment_amount)", 
					              "s.invoice_status_name", "datediff(now(),i.status_timestamp)", "ps.payment_status_name", "p.payment_date", 
					              "p.payment_transaction_number", "p.payment_reference_code" ],
	              	// 列名
					columnNameList : ["Invoice Number", "Workflow Action", "Notes", "BAN", "Vendor ", "LOB", "Invoice Date", "Invoice Current Due", "Original", 
					                  "Analyst", "Due Date", "Total Due", "Payment Amount", "Dispute Amount","Misc Adjustment Amount", 
					                  "Invoice Status", "Days in Status", "Payment Status", "Payment Date", "Payment Transaction Number", 
					                  "Payment Reference Number" ],

					dataFieldList : ["no", "workflowAction", "notes", "ban", "vendor", "lob", "date", "currentDue", "originalFlag", 
														"analyst", "due", "total", "paymentAmount", 
														"disputeAmount", "miscAdjustmentAmount", "status", "daysInStatus", "paymentStatus", "paymentDate", 
														"paymentTransactionNumber", "paymentReferenceNumber"],
					colRenders : {
						"no" : function(row){
							return '<a href="viewInvoiceDetails.action?invoiceId='+row.id+'">'+row.no+'</a>';
						}
					},
					filter: externalApproveBucketFilterInstance
				},

				PastDue : {
					searchVoStr : "wvo.",
					totalPageNoAction : "doGetPastDuePageNo.action",
					searchDataUri : "doSearchPastDue.action",
					columnList : ["i.invoice_number","b.account_number","v.vendor_name","b.line_of_business","i.invoice_date",
					              "if(i.invoice_current_due IS NULL, 0, i.invoice_current_due)","i.original_flag",
					              "concat(u.first_name,blank_space(),u.last_name)","i.invoice_due_date","i.invoice_total_due",
					              "i.payment_amount","i.dispute_amount","if(i.misc_adjustment_amount IS NULL, 0,i.misc_adjustment_amount)",
					              "s.invoice_status_name","datediff(now(),i.status_timestamp)"],
					columnNameList : ["Invoice Number","BAN","Vendor ","LOB","Invoice Date","Invoice Current Due","Original",
					                  "Analyst","Due Date","Total Due","Payment Amount","Dispute Amount","Misc Adjustment Amount",
					                  "Invoice Status","Days in Status"],
					dataFieldList : ["no","ban","vendor","lob","date","currentDue","originalFlag","analyst","due","total","paymentAmount",
					                 "disputeAmount","miscAdjustmentAmount","status","daysInStatus"],
					colRenders : {
						"no" : function(row){
							return '<a href="viewInvoiceDetails.action?invoiceId='+row.id+'">'+row.no+'</a>';
						}
					},
					filter:invoiceFilter
				},
				/**
				 * Add by Chao.Liu to 2010/06/22 PM
				 * Search Invoice
				 */
				UnknownInvoices : {
					searchVoStr : "wvo.",
					totalPageNoAction : "doGetUnknownInvoicesTotalPageNoInCommonLookup.action",
					searchDataUri : "doSearchUnknownInvoicesInCommonLookup.action",
					// Modified by Xin Huang 2011-08-30 (change invoice_number column to bar_code column)
					columnList : ["","","i.bar_code","b.account_number","v.vendor_name","i.invoice_date","i.invoice_due_date",
					              "if(i.invoice_current_due IS NULL, 0, i.invoice_current_due)","i.invoice_total_due",
					              "concat(u.first_name,blank_space(),u.last_name)","i.invoice_receive_date","s.invoice_status_name",
					              "datediff(now(),i.status_timestamp)", "o.file_name", "i.notes"],
					columnNameList : ["","Remove","Bar Code","BAN","Vendor ","Invoice Date","Due Date","Current Due","Total Due",
					                  "Analyst","Received Date","Invoice Status","Days in Status", "Original File", "Notes"],
					dataFieldList : ["checkbox","remove","barCode","ban","vendor","date","due","currentDue","total","analyst",
					                 "receiveDate","status","daysInStatus", "originalFileName", "notes"],					
//					columnList : ["","i.bar_code","b.account_number","v.vendor_name","i.invoice_date","i.invoice_due_date","i.invoice_current_due","i.invoice_total_due","concat(u.first_name,blank_space(),u.last_name)","i.invoice_receive_date","s.invoice_status_name","datediff(now(),i.status_timestamp)", "o.file_name", "i.notes"],
//					columnNameList : ["","Bar Code","BAN","Vendor ","Invoice Date","Due Date","Current Due","Total Due","Analyst","Received Date","Invoice Status","Days in Status", "Original File", "Notes"],
//					dataFieldList : ["checkbox","barCode","ban","vendor","date","due","currentDue","total","analyst","receiveDate","status","daysInStatus", "originalFileName", "notes"],					
					//Modified by Qiao Yang 2011-08-29
					colRenders : {				
						"originalFileName" : function(row){
							if (row.originalFlg == "Y") {
								return "<img  src=\"include/images/download1.gif\"  style=\"cursor: pointer;\"  onClick=\"downloadFileO(" + "'" + row.originalFileName + "','" + row.originalFilePath + "'" + ");\">";
							} else {
								return "";
							}
						},
						"notes" : function(row){
							return "<a href='javascript:void(0)' onclick='showEditNotesWindows(\""+row.id+"\",\""+escapeLineBreak(escape(row.notes))+"\")'>View/Add/Edit</a>";
						},
						"remove" : function(row){
							return '<img src="include/images/delete.png" alt="Remove" onclick="removeMissingAndUnknownInvoice('+row.id+',\'' + "unknown" + '\')"></img>';	
						},
						"checkbox" : function(row){
							return '<input type="checkbox" value="' + row.id + '" onclick="checkChecked(this)"/>';	
						}
					},
					filter:unknownInvoicesFilter
				},
				/**
				 * Add by Chao.Liu to 2010/06/22 PM
				 * Search Invoice
				 */
				MissingInvoices : {
					searchVoStr : "wvo.",
					totalPageNoAction : "doGetMissingInvoicesTotalPageNoInCommonLookup.action",
					searchDataUri : "doSearchMissingInvoicesInCommonLookup.action",
					columnList : ["","b.account_number","v.vendor_name","i.original_flag","concat(u.first_name,blank_space(),u.last_name)",
					              "s.invoice_status_name","datediff(now(),i.status_timestamp)","i.invoice_expecting_date","i.last_dispute",
					              "i.last_payment","i.last_total_due"],
					columnNameList : ["Remove","BAN","Vendor ","Original","Analyst","Invoice Status","Days in Status",
					                  "Invoice Expecting Date","Last Dispute","Last Payment","Last Total Due"],
					dataFieldList : ["remove","ban","vendor","originalFlag","analyst","status","daysInStatus","invoiceExpectingDate",
					                 "lastDispute","lastPayment","lastTotalDue"],
//					columnList : ["b.account_number","v.vendor_name","i.original_flag","concat(u.first_name,blank_space(),u.last_name)","s.invoice_status_name","datediff(now(),i.status_timestamp)","i.invoice_expecting_date","i.last_dispute","i.last_payment","i.last_total_due"],
//					columnNameList : ["BAN","Vendor ","Original","Analyst","Invoice Status","Days in Status","Invoice Expecting Date","Last Dispute","Last Payment","Last Total Due"],
//					dataFieldList : ["ban","vendor","originalFlag","analyst","status","daysInStatus","invoiceExpectingDate","lastDispute","lastPayment","lastTotalDue"],
					colRenders : {
						"no" : function(row){
							return '<a href="viewInvoiceDetails.action?invoiceId='+row.id+'">'+row.no+'</a>';
						},
						"remove" : function(row){
							if(SANINCO.ifAllGranted(Constants.FUNCTION.missingInvoiceDelete)){
								return '<img src="include/images/delete.png" alt="Remove" ' +
								'onclick="removeMissingAndUnknownInvoice('+row.id+',\'' + "missing" + '\',\'' + row.missingEmailFlag + '\')"></img>';	
							}else{
								return '';	
							}
						}
					},
					filter:missingInvoicesFilter
				},
				/**
				 * Add by Chao.Liu to 2010/06/22 PM
				 * Search Invoice, there are three types:
				 * 1, Payment in Process.
				 * 2, Disputes in Process.
				 * 3, Payment in Exception.
				 */
				InvoiceInProcess : {
					searchVoStr : "wvo.",
					totalPageNoAction : "doGetInvoiceInProcessTotalPageNoInCommonLookup.action",
					searchDataUri : "doSearchInvoiceInProcessInCommonLookup.action",
					columnList : ["i.invoice_number","b.account_number","v.vendor_name","i.invoice_date",
					              "if(i.invoice_current_due IS NULL, 0, i.invoice_current_due)","i.original_flag",
					              "concat(u.first_name,blank_space(),u.last_name)","i.invoice_due_date","i.invoice_total_due",
					              "i.payment_amount","i.dispute_amount","if(i.misc_adjustment_amount IS NULL, 0,i.misc_adjustment_amount)",
					              "s.invoice_status_name","datediff(now(),i.status_timestamp)","ps.payment_status_name","p.payment_date",
					              "p.payment_transaction_number","p.payment_reference_code"],
					columnNameList : ["Invoice Number","BAN","Vendor ","Invoice Date","Invoice Current Due","Original","Analyst",
					                  "Due Date","Total Due","Payment Amount","Dispute Amount","Misc Adjustment Amount","Invoice Status",
					                  "Days in Status","Payment Status","Payment Date","Payment Transaction Number","Payment Reference Number"],
					dataFieldList : ["no","ban","vendor","date","currentDue","originalFlag","analyst","due","total","paymentAmount",
					                 "disputeAmount","miscAdjustmentAmount","status","daysInStatus","paymentStatus","paymentDate",
					                 "paymentTransactionNumber","paymentReferenceNumber"],
					colRenders : {
						"no" : function(row){
							return '<a href="viewInvoiceDetails.action?invoiceId='+row.id+'">'+row.no+'</a>';
						}
					},
					filter:invoiceFilter
				},
				/**
				 * Add by Donghao.Guo to 2011/09/16 PM
				 * Search Invoice
				 */
				PreloadInvoices : {
					searchVoStr : "wvo.",
					totalPageNoAction : "getPreloadInvoicesTotalPageNoInCommonLookup.action",
					searchDataUri : "doSearchPreloadInvoiceInCommonLookup.action",
					columnList : ["i.invoice_number","i.bar_code","b.account_number","v.vendor_name","i.invoice_date","i.invoice_due_date",
					              "i.invoice_receive_date","if(i.invoice_current_due IS NULL, 0, i.invoice_current_due)","i.invoice_total_due",
					              "concat(u.first_name,blank_space(),u.last_name)","s.invoice_status_name","datediff(now(),i.status_timestamp)"],
					columnNameList : ["Invoice Number","Bar Code","BAN","Vendor ","Invoice Date","Due Date","Received Date",
					                  "Invoice Current Due","Total Due","Analyst","Invoice Status","Days in Status"],
					dataFieldList : ["no","barCode","ban","vendor","date","due","receiveDate","currentDue","total",
					                 "analyst","status","daysInStatus"],
					colRenders : {},
					filter:preloadInvoicesFilter
				},
				/**
				 * Add by mingyang.li to 2012/03/10 PM
				 * Search Invoice
				 */
				InvoiceReject : {
					searchVoStr : "wvo.",
					totalPageNoAction : "getInvoiceRejectTotalPageNoInCommonLookup.action",
					searchDataUri : "doSearchInvoiceRejectInCommonLookup.action",
					
					columnList : ["i.invoice_number","b.account_number","v.vendor_name","i.invoice_date",
					              "if(i.invoice_current_due IS NULL, 0, i.invoice_current_due)","i.original_flag",
					              "concat(u.first_name,blank_space(),u.last_name)","i.invoice_due_date","i.invoice_total_due",
					              "i.payment_amount","i.dispute_amount","if(i.misc_adjustment_amount IS NULL, 0,i.misc_adjustment_amount)",
					              "s.invoice_status_name","datediff(now(),i.status_timestamp)","ps.payment_status_name",
					              "p.payment_date","p.payment_transaction_number","p.payment_reference_code"],
					columnNameList : ["Invoice Number","BAN","Vendor ","Invoice Date","Invoice Current Due","Original","Analyst",
					                  "Due Date","Total Due","Payment Amount","Dispute Amount","Misc Adjustment Amount","Invoice Status",
					                  "Days in Status","Payment Status","Payment Date","Payment Transaction Number","Payment Reference Number"],
					dataFieldList : ["no","ban","vendor","date","currentDue","originalFlag","analyst","due","total","paymentAmount",
					                 "disputeAmount","miscAdjustmentAmount","status","daysInStatus","paymentStatus","paymentDate",
					                 "paymentTransactionNumber","paymentReferenceNumber"],
					colRenders : {
						"no" : function(row){
							return '<a href="viewInvoiceDetails.action?invoiceId='+row.id+'">'+row.no+'</a>';
						}
					},
					filter:invoiceFilter
				},
				
				/**
				 * Search disputes bucket.
				 * */
				DisputesBucket : {
					searchVoStr : "wvo.",
					totalPageNoAction : "doSearchDisputeCountByDays.action",
					searchDataUri : "doSearchDisputeByDays.action",
					columnList : ["","FN_GET_DISPUTE_MESSAGE_STATUS(d.id)","dar.dispute_action_request_status_id", "d.dispute_number","d.claim_number","v.vendor_name","i.account_number","i.invoice_number","d.dispute_amount",
					              "d.dispute_balance","Date(d.created_timestamp)","dr.dispute_reason_name",
					              "datediff(now(), d.status_timestamp)","concat(u.first_name,blank_space(),u.last_name)","dispute_created_by","assigned_to","d.flag_shortpaid","d.flag_recurring"],
					columnNameList : ["","Response Flag","Action Flag", "Dispute Number","Claim Number","Vendor","BAN ","Invoice Number","Dispute Amount","Outstanding Dispute Balance",
					                  "Dispute Date","Dispute Category","Days in Status","Dispute Owner","Dispute Created by","Assigned To","Is Short-paid","Is Recurring"],
					dataFieldList : ["checkbox","dispute_flag","action_flag", "dispute_number","claim_number","vendor_name","account_number","invoice_number","dispute_amount","dispute_balance",
					                 "dispute_date","dispute_category","days_in_status","dispute_owner","dispute_created_by","assigned_to","short_paid_dispute","recurring_dispute"],
					colRenders : {
						"dispute_number" : function(row){
							return '<a href="viewDisputeDetails.action?disputeId='+row.id+'">'+row['dispute_number']+'</a>';
						},
						
						"checkbox" : function(row){
							return '<input type="checkbox" value="' + row.id + '" onclick="checkChecked(this)"/>';	
						},
						
						// 当返回的flag不相同的时候，显示不同的状态
						// 而且提示不同的信息。
						"dispute_flag" : function(row){ 

							var promptMessage = ""; // 不同状态下的提示信息。
							var imgUrl = ""; // 返回表示状态的icon的地址。

							if (row['dispute_flag'] === "A") {

								promptMessage = "\'Vendor response overdue.\'";
								flagStructure = '<img src="include/images/disputeBucket/mail_failed.png"  onMouseOver="showDisputeLabel(event, '+ promptMessage +')" onMouseOut="disableDisputeLabel()">';
							} else if (row['dispute_flag'] === "B") {

								promptMessage = "\'Action Required.\'";
								flagStructure = '<img src="include/images/disputeBucket/mail_warning.png"  onMouseOver="showDisputeLabel(event, '+ promptMessage +')" onMouseOut="disableDisputeLabel()">';
							} else if ( row['dispute_flag'] === "C" ) {

								promptMessage = "\'Waiting for Credit.\'";
								flagStructure = '<img src="include/images/disputeBucket/edit_file.gif"  onMouseOver="showDisputeLabel(event, '+ promptMessage +')" onMouseOut="disableDisputeLabel()">';
							} else {

								flagStructure = "";
							}

							return flagStructure;	
							
						},
						"action_flag" : function(row){ 

							if (row['action_flag'] === "Y") {

								actionFlagStructure = '<img src="include/images/credit_1.png">';
	                        } else {

	                        	actionFlagStructure = "";
							}

							return actionFlagStructure;	
							
						}
					},	
				
					filter: disputesBucketFilter
				}
			}
		};
		
		var B = this.base = {
			previousPostedData : "",
			
			/* Set title of main panel. */
			getTitleHtml : function(name,dueDay){
				if(name == "InvoiceInProcess"){
					if(dueDay == -1){
						name = "Payment in Process";
					}else if(dueDay == -2){
						name = "Disputes in Process";
					}else if(dueDay == -3){
						name = "Payment in Exception";
					}
				}
				if(name == "PastDue"){
					name = "Past Due";
				}
				if(name == "UnknownInvoices"){
					name = "Unknown Invoice";
				}
				if(name == "MissingInvoices"){
					name = "Missing Invoice";
				}
				//Add by donghao.guo 2011-09-19
				if(name == "PreloadInvoices"){
					name = "Preload Invoice";
				}//Add by mingyang.li 2012-03-10
				if(name == "InvoiceReject"){
					name = "Invoice Reject";
				}
				
				// Disputes bucket.
				if(name == "DisputesBucket"){
					name = "Disputes Bucket";
				}

				// External Approve Invoice .
				if(name == "ExternalApproveInvoices"){
					name = "External Approve Invoices";
				}
				
				// Wrap the title name with headline tag.
				return '<h3 id="">'+name+' Assignments</h3>';
			},
			
			/**
			 * Using various parameters to compose the search part of request URL.
			 * @param { String } Section name.
			 * @return { String } Search part string.
			 * */
			composePostBaseDataUi : function(name){
				var cbp = C.base.params;
				var cs = C.service;
				var postData = "";
				var tmp = true;
				
				for(var pname in cbp){
					if(typeof cbp[pname] != 'undefined' && cbp[pname] != null){
						if(tmp){
							if((name == "BanApprove" || name == "BanReject") && cbp[pname] == "i.id"){
								continue;
							}
							postData += cs[name].searchVoStr + pname + "=" + cbp[pname];
							tmp = false;
						}else{
							postData += "&" + cs[name].searchVoStr + pname + "=" + cbp[pname];
						}
					}
				}
				if(name == "BanApprove" || name == "BanReject"){
					if(cs[name].filter.getCause())postData += ("&bvo.filter=" + cs[name].filter.getCause());
				}else{
					if(cs[name].filter.getCause())postData += ("&wvo.filter=" + cs[name].filter.getCause());
				}
				return postData;
			},
			buildPreviousPostedData : function(name){
				return this.composePostBaseDataUi(name);
			},
			isQueryChanged : function(){
				return false;
			},
			evaluateQueyChange : function(){
				if (this.isQueryChanged()) {
					BUSINESS.service.startSearch();
					return true;
				} else {
					return false;
				}
			},
			getSortingDirectionString : function(fieldName){ //renderPage invoke
				var cbp = C.base.params;
				var cps = C.page.sorting;
				var currentSortingFieldName = cbp[cps.fieldNameAtParams];
				var currentSortingDirection = cbp[cps.directionAtParams];
				if(currentSortingFieldName == fieldName){
					if(currentSortingDirection=='asc'){
						return cps.upImg;
					}else{
						return cps.downImg;
					}
				}else{
					return '';
				}
			}
		};
		
		var P = this.page = new function(){
			
			// Set for table invoice records.
			var PC = this.core = {
				totalPageNo : 0, // Total pages for all invoice records.
				totalResultNo : 0 // All invoice records for specified section.
			};
			
			/**
			 * Rendering the main table of records (main panel) .
			 * */
			this.renderTable = function(o){ //startSearch invoke
				// Core page information set.
				var cp = C.page;
				
				// Reload the page when timeout for loading.
				if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
					window.location.reload();
					return;
				}
				
				// { ['totalPageNo'], ['totalResultNo'], (['error'], ['CCM_USER_SESSION_TIME_OUT_AJAX_FLAG']) }
				var data = eval("("+o.responseText+")");

				// Get the element container.
				var _table = document.getElementById(cp.tableId); // Main table.
				var _paginationTable = document.getElementById(cp.paginationTableId); // Pagination table.
				var _curPageNo = document.getElementById(cp.curPageNoId); // Page number.
				var _totalPageNo =  document.getElementById(cp.totalPageNoId); // Total page number.
				
				
				if(data.error){ // If the returned data have error.
					
					_table.innerHTML = "Error: " + data.error; // Error table content.
					_paginationTable.style.display = "none"; // Hide pagination table.
					return;
					
				} else if (data.totalResultNo == 0){ // If invoice records is 0.
					
					PC.totalResultNo = 0;
					PC.totalPageNo = data.totalPageNo;
					_curPageNo.value = 0;
					_table.innerHTML = "No records found."; // Invoice records table content.
					_totalPageNo.innerHTML = "0";
					_paginationTable.style.display="none"; // Hide pagination table.
					hideLoadingModalLayer();
					
				} else { // If there are invoices records.
					
					PC.totalResultNo = data.totalResultNo;
					PC.totalPageNo = data.totalPageNo;
					_curPageNo.value = 1;
					_table.innerHTML = "Loading..."; // Data table content.
					_totalPageNo.innerHTML = PC.totalPageNo;
					
					if(PC.totalPageNo==1){
						_paginationTable.style.display="none";
					}else{
						_paginationTable.style.display="block";
					}
					
					BUSINESS.service.requestData(); // Fetch invoice records data. 
				}
				
			};
			
			/**
			 * Core method for rendering invoice records table.
			 * */
			this.renderPage = function(o){
				
				// Get all core sets content.
				var cp = C.page;
				var cbp = C.base.params;
				var cs = C.service;
				
				// Get the element container.
				var _table = document.getElementById(cp.tableId);
				var _paginationTable = document.getElementById(cp.paginationTableId);
				var _curPageNo = document.getElementById(cp.curPageNoId);
				var _totalPageNo =  document.getElementById(cp.totalPageNoId);
				
				// Reload the page when timeout for loading.
				if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
					window.location.reload();
					return;
				}
				
				// Properties in this set.
				// 1, { Number } begin. Begin index for this page.
				// 2, { Array } data. All invoice records for this page.
				// 3, { Number } end. End index for this page.
				
				// error. The data have a error.
				// "CCM_USER_SESSION_TIME_OUT_AJAX_FLAG". Flag for reloading the page.
				var data = eval("("+o.responseText+")");
				
				if(data.error){ // If the returned data have error.
					_table.innerHTML = "Error: " + data.error;
					_paginationTable.style.display="none";
					return;
				}
				
				/*
				 * Get invoice records table configuration from core service.
				 * */
				
				// Column filter configurations.
				var columnList = cs[cs.name].columnList; // Column list.
				
				// Static table head names.
				var columnNameList = cs[cs.name].columnNameList; // Column name list.
				
				// Corresponding to property names from the back.
				var dataFieldList = cs[cs.name].dataFieldList; // Data field list.
				
				// Render special column, not just show data.
				var colRenders = cs[cs.name].colRenders; // Column renders.
				
				
				
				// Set a flag to jump for rendering the specified data field.
				var jump = 0;
				if(cs.name=="MissingInvoices"){
					
					// Authority judgement of the special FUNCTION.
					if(!SANINCO.ifAllGranted(Constants.FUNCTION.missingInvoiceDelete)){
						jump=1;
					}
				}
				
				// Render entire invoice records table.
				var tableContent = 'Search Results: Items '+data.begin+' - '+data.end+' of '+ PC.totalResultNo 
					+ '<table align="left" border="0" style="width:100%; table-layout:fixed;margin:5px;"><tr><td><div align="left"' 
					+ 'id="_table_scroll_div_" style="width:100%;overflow-x:auto;padding-bottom: 20px;"><table class="gridview"' 
					+ 'cellspacing="0" rules="all" border="1"'
					+ 'id="ctl00_MainContent_SearchInvoiceControl1_GridView1"'
					+ 'style="width:100%;white-space:nowrap;border-collapse: collapse;">'
					+ '<tr class="listViewThLinkS1">';
					// 'listViewThLinkS1' is a table head row.
					for(var m=0;m<columnList.length;m++){
						if(columnNameList[m]=="Remove"){ // Do not render the data field.
							if(jump==1){
								continue;
							}
						}
						tableContent += ('<th nowrap="nowrap" height="30px" scope="col">' 
						+ '<a href="#" onclick="BUSINESS.page.sort(event,\''+columnList[m]+'\');">'
						+ columnNameList[m] + B.getSortingDirectionString(columnList[m]) 
						+ '</a>');
						if(!(cs.name =="DisputesBucket" && (m=="1" || m=="2"))){
							tableContent += '&nbsp;&nbsp;&nbsp;<img src="'+cs[cs.name].filter.getImg(columnList[m])+'"  style="width:13px;height:13px;"  onclick="BUSINESS.config.service.'+cs.name+'.filter.edite(\''+columnList[m]+'\',\''+columnNameList[m]+'\');">'
							+ '</th>';
						}
						
					}
					tableContent += '</tr>'; // Render the table head.

				 var rows = data.data;
				 // Render the table body.
				 for(var i = 0; i<rows.length; i++){ // Every row is a record ( a tuple in database ).
				 	var row = rows[i];
				 	tableContent += ('<tr class="'+ (i%2==0?'evenListRowS1':'even')+'" style="height: 30px;">');
				 	
				 		// Loop the data field list.
				 		for(var n=0; n<dataFieldList.length; n++ ){
				 			
				 			/*
				 			 * Every column name should match the meaning of data field.
				 			 * */
							var tmpField = dataFieldList[n];
							if(columnNameList[n]=="Remove"){
								if(jump==1){
									continue;
								}
							}
							

							if(colRenders[tmpField]){
								if (tmpField == "dispute_flag" || tmpField == "action_flag") {
									// Render special table data cell.
									tableContent += '<td align="center">'+colRenders[tmpField](row)+'</td>';
								}else {
									// Render special table data cell.
									tableContent += '<td align="left">'+colRenders[tmpField](row)+'</td>';
								}
								
							}else{
								
								// Render common table data cell.
					 			tableContent += ('<td align="left">'+row[tmpField]+'</td>');
							}
				 		}
					tableContent += '</tr>';
				 }
				 tableContent += '</table></div></td></tr></table>';
				 
			    _curPageNo.value = cbp[cp.pageNoNameAtParams]; // Current page number.
			    _table.innerHTML = tableContent; // Table content.
			    _totalPageNo.innerHTML = PC.totalPageNo; // Total page number.
				_paginationTable.style.display="block";

				$("#_table_scroll_div_").scrollLeft(BUSINESS.leftScroll);
				
				/*
				 * Add checkbox to the first column of invoice records table.
				 * User can delete invoice records by selected checkbox.
				 * */
				
				// Get the content of first '<a />' table head cell.
			   	var htmlVal = $("#ctl00_MainContent_SearchInvoiceControl1_GridView1 th:eq(0) a").html();
				
				// If value is null character string or 'Remove' ( represents column name is 'Remove' ).
				if (cs.name=="UnknownInvoices"|| htmlVal == "Remove"||cs.name=="DisputesBucket") {
					
					// Remove the filter image for checkbox column.
					$("#ctl00_MainContent_SearchInvoiceControl1_GridView1 th:eq(0) img").remove();
					
					if (cs.name=="UnknownInvoices"||cs.name=="DisputesBucket") {
						if (cs.name=="UnknownInvoices"){
							$("#ctl00_MainContent_SearchInvoiceControl1_GridView1 th:eq(1) img").remove();
						}
										
						// Add checkbox.
						// The rest of actions have been implement inside 'colRenders' object
						// ( for instance, remove records and invoice number link ).
						$("#ctl00_MainContent_SearchInvoiceControl1_GridView1 th:eq(0)")
							.html('<input type="checkbox" id="checkAll" onclick="checkboxAll(this);"/>');
					}
				}
				
				hideLoadingModalLayer(); // Hide loading modal.
			};
			
			this.getFirstPage = function(){
				var cp = C.page;
				var cbp = C.base.params;
				if(B.evaluateQueyChange()){
					return;
				}
				if(cbp[cp.pageNoNameAtParams] <= 1){
					return;
				}
				cbp[cp.pageNoNameAtParams] = 1;
				BUSINESS.service.requestData();
			};
			this.getPrevPage = function(){
				var cp = C.page;
				var cbp = C.base.params;
				if(B.evaluateQueyChange()){
					return;
				}
				if(cbp[cp.pageNoNameAtParams] <= 1){
					return;
				}
				cbp[cp.pageNoNameAtParams]--;
				BUSINESS.service.requestData();
			};
			this.getNextPage = function(){
				var cp = C.page;
				var cbp = C.base.params;
				if(B.evaluateQueyChange()){
					return;
				}
				if(cbp[cp.pageNoNameAtParams] >= PC.totalPageNo){
					return;
				}
				cbp[cp.pageNoNameAtParams]++;
				BUSINESS.service.requestData();
			};
			this.getLastPage = function(){
				var cp = C.page;
				var cbp = C.base.params;
				if(B.evaluateQueyChange()){
					return;
				}
				if(cbp[cp.pageNoNameAtParams] >= PC.totalPageNo){
					return;
				}
				cbp[cp.pageNoNameAtParams] = PC.totalPageNo;
				BUSINESS.service.requestData();
			};
			this.getPage = function(){
				if(window.event && window.event.keyCode == 13){
					var cp = C.page;
					var cbp = C.base.params;
					var _curPageNo = document.getElementById(cp.curPageNoId);
					if(B.evaluateQueyChange()){
						return;
					}
					if(_curPageNo.value == cbp[cp.pageNoNameAtParams]){
						return;
					}
					cbp[cp.pageNoNameAtParams] = _curPageNo.value;
					BUSINESS.service.requestData();
				}
			};
			this.getOnblurPage = function(){
				var cp = C.page;
				var cbp = C.base.params;
				var _curPageNo = document.getElementById(cp.curPageNoId);
				if(B.evaluateQueyChange()){
					return;
				}
				if(_curPageNo.value == cbp[cp.pageNoNameAtParams]){
					return;
				}
				cbp[cp.pageNoNameAtParams] = _curPageNo.value;
				BUSINESS.service.requestData();
			};
			this.sort = function(e, sortingFieldName){
				var cps = C.page.sorting;
				var cbp = C.base.params;
				var nfield = cps.fieldNameAtParams;
				var dfield = cps.directionAtParams;
				if(B.evaluateQueyChange()){
					return;
				}
				if(cbp[nfield] != sortingFieldName){
					cbp[nfield] = sortingFieldName;
					cbp[dfield] = "asc";
				}else{
					if(cbp[dfield] == "asc"){
						cbp[dfield]="desc";
					} else{
						cbp[dfield]="asc";
					}
				}
				BUSINESS.service.requestData();
			};
		};
		
		var S = this.service = {
			doms : null,
			renderShow : function(){
				var bs = BUSINESS.service;
				var d = document.getElementById(C.service.mainId).childNodes;
				if(bs.doms == null){
					bs.doms = [];
					for(var a=0;a<d.length;a++){ 
						if (d[a].style && d[a].style.display != "none") {
							d[a].style.display = "none";
							bs.doms.push(d[a]);
						}
					}
				} else{
					var doms = bs.doms;
					for(var a=0;a<doms.length;a++){
						doms[a].style.display = "none";
					}
				}
				var t = document.getElementById(C.service.targetId);
				if(t)t.style.display = "block";
				document.getElementById(C.service.targetId).style.display = "block";
			},
			renderHide : function(){
				var doms = BUSINESS.service.doms;
				if (doms != null) {
					for(var a=0;a<doms.length;a++){
						doms[a].style.display = "block";
					}
				}
				var t = document.getElementById(C.service.targetId);
				if(t)t.style.display = "none";
			},
			
			/**
			 * Make sure if the parameter is a number type value and 
			 * the value is not NaN.
			 * 
			 * @param { Number } numTypeParam.
			 * @return { Boolean } True or false.  
			 * */
			isNumberType: function ( numTypeParam ) {
				return !isNaN( numTypeParam );
//				return ( typeof numTypeParam === 'number' && !isNaN( numTypeParam ) );
			},
			
			/**
			 * Get and show the data records of records table in specified work-space sections.
			 * @param { String } Section name.
			 * @param { Number } uid.
			 * @param { Number } dueDay.
			 * @param { Number } disputeDay. ( Specified for disputes bucket ).
			 * @param { String } titleName for submenu list.
			 * */
			startSearch : function(name, uid, dueDay, disputeDay, titleName){
				BUSINESS.leftScroll = 0;
				
				// Loading modal.
				showLoadingModalLayer();
				
				// Main service to render main panel content.
				var cs = C.service; 
				
				// Some parameters are passed to the back for dealing with the requests.
				var cbp = C.base.params;
				
				// Sorting configuration set.
				var cps = C.page.sorting;
				
				
				// Initialize the main panel content.
				if(!this.isInit){
					
					// Place holder content.
					var tmpelement = document.getElementById(cs.templateId); // Get the element and it will be hidden.
					cs.templateHtml = tmpelement.innerHTML; // Populate template content.
					tmpelement.innerHTML = ""; // Clear the content.
					if (document.getElementById(cs.mainId).className != "tabForm") {
						document.getElementById(cs.mainId).className += " tabForm";
					}
					
					// Page target which is a container to include the data table 
					// ( append a container to page container ).
					document.getElementById(cs.mainId).innerHTML += "<div id='"+cs.targetId+"' ></div>";
					document.getElementById(cs.mainId).innerHTML += "<div class=\"InSePoput\" style=\"display: none;\" >" +
							"<div class=\"ISPtop\"></div><div class=\"ISPcon\"><div class=\"ISPleft\"></div><div class=\"ISPright\" id=\"labelPanel\"></div>"+
							"</div><div class=\"ISPdown\"></div></div>";
					
					this.isInit = true; // End of initializing.
				}
				
				// C.service.name ( render the title name of data table of main panel ).
				if(name) cs.name = name;
				if(titleName && typeof titleName != 'undefined') {
					cs.titleName = titleName;
				}else {
					cs.titleName = "";
				}
				
				// Parameters are passed to the back for requests.
				// { Number } uid, dueDay, disputeDay. ( 0 == false inside if statements)
				if( this.isNumberType(uid) ) cbp["uid"] = uid;
				if( this.isNumberType(dueDay) ) cbp["dueDay"] = dueDay;
				if ( this.isNumberType(disputeDay) ) cbp["disputeDay"] = disputeDay;
				
				// Clear all filter names.
				if(name) cs[name].filter.clearAll();
				
				// Parameters.
				if(cs.name != "BanApprove" && cs.name != "BanReject"){
					cbp[cps.fieldNameAtParams] = "i.id";
					cbp[cps.directionAtParams] = "asc";
				}
				cbp[C.page.pageNoNameAtParams] = 1;
				
				// Set the title and table content.
				if(typeof titleName != 'undefined' && titleName != null && titleName != ""){
					// Generally, for submenu items.
					document.getElementById(cs.targetId).innerHTML = B.getTitleHtml(titleName,cbp["dueDay"]) + cs.templateHtml;
				}else{
					document.getElementById(cs.targetId).innerHTML = B.getTitleHtml(cs.name,cbp["dueDay"]) + cs.templateHtml;
				}
				
				this.renderShow(); // Display the main content.
				
				// The parameter for records number in every page.
				cbp[C.page.recPerPageNameAtParams] = document.getElementById(C.page.pageSelectId).value;
				
				BUSINESS.service.searchItemLeftPanelCount(cs,uid,dueDay);
				
				var callback = {
					success: P.renderTable,// Action to render records table.
					failure: showError
				};
				
				// Post data URL (search part).
				B.previousPostedData = B.buildPreviousPostedData(cs.name);
			
				// Search data records.
				Y.util.Connect.asyncRequest('POST' ,cs[cs.name].totalPageNoAction , callback , B.previousPostedData );
			},
			
			/*
			 * 查询当前选择项的Left Panel Count
			 * */
			searchItemLeftPanelCount : function (cs,uid,dueDay,disputeDay){
				
				var callback = {
					success: function(o){
					var data = eval("("+o.responseText+")");
					
//					if (cs.name === 'ExternalApproveInvoices') {
////						// 请求 external approve 列表时，回填左侧菜单中的 count 值。
////						// 主要作用： 当通过邮件 Approve / Reject 之后，点击 left menu
////						// 中的 external approve item 同样可改变该 item 中的 count 值.
//						if(uid == -1){
//							$('.externalApproveInvoiceCount_').text(data.totalResultNo);
//						}else{
//							$('.externalApproveInvoiceCount_'+uid).text(data.totalResultNo);
//						}
//						
//					} else if (cs.name === 'BanPaymentNotice'){
//						$('.banPaymentNoticeCount_'+uid).text(data.totalResultNo);
//					}
					
					if(uid == -1) uid = "";
					var nameType = cs.titleName ?  cs.titleName : cs.name;
					
					if (nameType === 'PastDue') {
						$(".redPastDue_"+uid).html(data.totalResultNo);
					} else if (nameType === 'Due in 7 days') {
						$(".redInvoiceCount_"+uid).html(data.totalResultNo);
					} else if (nameType === 'Due in 15 days') { 
						$(".yellowInvoiceCount_"+uid).html(data.totalResultNo);
					} else if (nameType === 'Invoice') { 
						$(".greenInvoiceCount_"+uid).html(data.totalResultNo);
					} else if (nameType === 'UnknownInvoices') { 
						$(".unknownInvoicesCount_"+uid).html(data.totalResultNo);
					} else if (nameType === 'MissingInvoices') { 
						$(".missingInvoicesCount_"+uid).html(data.totalResultNo);
//					} else if (nameType === 'InvoiceInProcess') { 
//						$(".paymentInProcessCount_"+uid).html(data.totalResultNo);
//					} else if (nameType === 'PastDue') { 
//						$(".disputesInProcessCount_"+uid).html(data.totalResultNo);
//					} else if (nameType === 'PastDue') { 
//						$(".paymentInExceptionCount_"+uid).html(data.totalResultNo);
//					} else if (nameType === 'BanPaymentNotice') { 
//						$(".banPaymentNoticeCount_"+uid).html(data.totalResultNo);
//					} else if (nameType === 'BanApprove') { 
//						$(".banApproveCount_"+uid).html(data.totalResultNo);
//					} else if (nameType === 'BanReject') { 
//						$(".banRejectCount_"+uid).html(data.totalResultNo);
					} else if (nameType === 'Today to Due') { 
						$(".redInvoiceCountr0_"+uid).html(data.totalResultNo);
					} else if (nameType === '1 Day to Due') { 
						$(".redInvoiceCountr1_"+uid).html(data.totalResultNo);
					} else if (nameType === '2 Days to Due') { 
						$(".redInvoiceCountr2_"+uid).html(data.totalResultNo);
					} else if (nameType === '3 Days to Due') { 
						$(".redInvoiceCountr3_"+uid).html(data.totalResultNo);
					} else if (nameType === '4 Days to Due') { 
						$(".redInvoiceCountr4_"+uid).html(data.totalResultNo);
					} else if (nameType === '5 Days to Due') { 
						$(".redInvoiceCountr5_"+uid).html(data.totalResultNo);
					} else if (nameType === '6 Days to Due') { 
						$(".redInvoiceCountr6_"+uid).html(data.totalResultNo);
					} else if (nameType === '7 Days to Due') { 
						$(".redInvoiceCounty7_"+uid).html(data.totalResultNo);
					} else if (nameType === '8 Days to Due') { 
						$(".redInvoiceCounty8_"+uid).html(data.totalResultNo);
					} else if (nameType === '9 Days to Due') { 
						$(".redInvoiceCounty9_"+uid).html(data.totalResultNo);
					} else if (nameType === '10 Days to Due') { 
						$(".redInvoiceCounty10_"+uid).html(data.totalResultNo);
					} else if (nameType === '11 Days to Due') { 
						$(".redInvoiceCounty11_"+uid).html(data.totalResultNo);
					} else if (nameType === '12 Days to Due') { 
						$(".redInvoiceCounty12_"+uid).html(data.totalResultNo);
					} else if (nameType === '13 Days to Due') { 
						$(".redInvoiceCounty13_"+uid).html(data.totalResultNo);
					} else if (nameType === '14 Days to Due') { 
						$(".redInvoiceCounty14_"+uid).html(data.totalResultNo);
					} else if (nameType === '15 Days to Due') { 
						$(".redInvoiceCounty15_"+uid).html(data.totalResultNo);
					} else if (nameType === 'PreloadInvoices') { 
						$(".preloadInvoicesCount_"+uid).html(data.totalResultNo);
					} else if (nameType === 'InvoiceReject') { 
						$(".invoiceRejectCount_"+uid).html(data.totalResultNo);
					} else if (nameType === 'ExternalApproveInvoices') { 
						$(".externalApproveInvoiceCount_"+uid).html(data.totalResultNo);
					} else if (nameType === 'Dispute Buckets') { 
						$(".totalDisputesBucket_"+uid).html(data.totalResultNo);
					} else if (nameType === '0 - 30 Days') { 
						$(".first30Disputes_"+uid).html(data.totalResultNo);
					} else if (nameType === '31 - 60 Days') { 
						$(".range31To60Disputes_"+uid).html(data.totalResultNo);
					} else if (nameType === '61 - 90 Days') { 
						$(".range61To90Disputes_"+uid).html(data.totalResultNo);
					} else if (nameType === '91 - 120 Days') { 
						$(".range91To120Disputes_"+uid).html(data.totalResultNo);
					} else if (nameType === '121 - 180 Days') { 
						$(".range121To180Disputes_"+uid).html(data.totalResultNo);
					} else if (nameType === '180+ Days') { 
						$(".over180Disputes_"+uid).html(data.totalResultNo);
					}
					 
				},// Action to render records table.
					failure: showError
				};
				
				var param = "wvo.uid="+uid;
				if( this.isNumberType(dueDay) ) param = param + "&wvo.dueDay="+dueDay;
				if ( this.isNumberType(disputeDay) ) param = param + "&wvo.disputeDay="+disputeDay;
				
				Y.util.Connect.asyncRequest('POST' ,cs[cs.name].totalPageNoAction , callback , param);
				
			},
			
			/**
			 * According the number of selection to show records in every page.
			 * */
			selectSearch : function(){
				BUSINESS.leftScroll = 0;
				showLoadingModalLayer();
				var cs = C.service;
				var cbp = C.base.params;
				var cps = C.page.sorting;
				cbp[C.page.pageNoNameAtParams] = 1;
				cbp[C.page.recPerPageNameAtParams] = document.getElementById(C.page.pageSelectId).value;
				if(cs.name != "BanApprove" && cs.name != "BanReject"){
					cbp[cps.fieldNameAtParams] = "i.id";
					cbp[cps.directionAtParams] = "asc";
				}
				var callback = {
					success: P.renderTable,///
					failure: showError
				};
				B.previousPostedData = B.buildPreviousPostedData(cs.name);
				Y.util.Connect.asyncRequest('POST' ,cs[cs.name].totalPageNoAction , callback , B.previousPostedData );
			},
			
			/**
			 * Executing the action to get the data of invoice records table.
			 * */
			requestData : function(){
				
				// First, hide all 'Download to Excel' button.
				$('#'+C.page.paginationTableId+' .workspace-download').hide();
				
				// Show 'Download to Excel' button which is for specified section.
				$('#workspace-'+C.service.name+'-download').show();
				$('#forward-'+C.service.name+'-download').show();
				$('#forward-'+C.service.name+'-dispute').show();
				$('#forward-'+C.service.name+'-bulkActionRequest').show();
				
				// Get the scrolling value towards the left of main content table.
				BUSINESS.leftScroll = $("#_table_scroll_div_").scrollLeft();
				BUSINESS.leftScroll = BUSINESS.leftScroll ? BUSINESS.leftScroll : 0;
				
				// Get the current page number and make sure it is valid.
				var cp = C.page;
				var _curPageNo = document.getElementById(cp.curPageNoId);
				var re = /^[1-9]+[0-9]*]*$/;
				if(!re.test(_curPageNo.value)){
					alert("Please enter the correct number of pages"); 
					return ;
				}
				
				// Get the total page number and make sure it is valid.
                var totalvalue = document.getElementById(cp.totalPageNoId).innerHTML;
                var curPageNovalue = _curPageNo.value;
				if(parseInt(curPageNovalue)>parseInt(totalvalue)){
					alert("Please enter the correct number of pages"); 
					return;
				}
				
				var cs = C.service;
				var callback = {
					success: P.renderPage,
					failure: showError
				};
				B.previousPostedData = B.buildPreviousPostedData(cs.name); 
				
				
				Y.util.Connect.asyncRequest('POST' , cs[cs.name].searchDataUri , callback , B.previousPostedData ); 
			}
			
		};
	};
	BUSINESS.hide = BUSINESS.service.renderHide;
	BUSINESS.leftScroll = 0;
});

