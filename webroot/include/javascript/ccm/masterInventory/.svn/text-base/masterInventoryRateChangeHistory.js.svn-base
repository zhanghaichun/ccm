var changeHistory = {
	_inventoryId:"",
	_searchChangeHistoryList: function(inventoryId,strippedCircuitNumber){
		
		changeHistory._inventoryId = inventoryId;
		$('#tab5').hide();
		$('#change_history').show();
		if(!strippedCircuitNumber || typeof(strippedCircuitNumber) == "undefined") {
			strippedCircuitNumber = "";
		}
		$('#circuit_number_title')[0].innerHTML = "Update History of Circuit  " + strippedCircuitNumber;
		changeHistory._getMasterInventoryRateChangeHistoryList(inventoryId);
	},
	closeChangeHistoryListWindow: function(){
		$('#tab5').show();
		$('#change_history').hide();
	},
	
	_getMasterInventoryRateChangeHistoryList: function(inventoryId) {
		//!window.masterInventoryListPage
		if(1==1){
			masterInventoryRateChangeHistoryListPage = new SANINCO.Page("_master_inventory_change_history_list","masterInventoryRateChangeHistoryListPage",{
				sortingField : "history_modified_timestamp",
				sortingDirection : "desc",
			    vo : "searchInventoryDashboardVO",
				recordText : '',
				totalPageUri : "getInventoryRateChangeHistoryListPageNo.action",
			    dataUri :"getInventoryRateChangeHistoryList.action" ,
			    paginationDiv : "_master_inventory_change_history_page_list",
				recPerArray : [10,20,30,40,50,70,90,100],
			    cols : [
			            // Modifier
						{ title : "Modifier",
						  dataField:"userName",
						  filtration : {
				            name:"user_name",
				            alias:"Modifier"
			              },
						  sort : "user_name"
						},
						// Date
						{ title : "Modified Time",
						  dataField:"historyModifiedTimestamp",
						  filtration : {
				            name:"history_modified_timestamp",
				            alias:"Modified Time"
			              },
						  sort : "history_modified_timestamp"
						},
						//1//			KEY
						{ title : "Key",
						  dataField:"id",
						  filtration : {
				            name:"mid",
				            alias:"Key"
			              },
						  sort : "mid"
						},
						//2
						{ title : "Ban",
							dataField:"banName",
							filtration : {
							name:"ban_name",
							alias:"Ban"
						},
						sort : "ban_name"
						},
						//3
						{ title : "Vendor Name",
							  dataField:"vendorName",
							  filtration : {
					            name:"vendor_name",
					            alias:"Vendor Name"
				              },
							  sort : "vendor_name"
						},
						//4//			SUMMARY VENDOR NAME
						{ title : "Summary Vendor Name",
							  dataField:"summaryVendorName",
							  filtration : {
					            name:"summary_vendor_name",
					            alias:"Summary Vendor Name"
				              },
							  sort : "summary_vendor_name"
						},
						//5//			INVOICE NUMBER
						{ title : "Invoice Number",
							  dataField:"invoiceNumber",
							  filtration : {
					            name:"invoice_number",
					            alias:"Invoice Number"
				              },
							  sort : "invoice_number"
						},
						//6//			LINE OF BUSINESS
						{ title : "Line of Business",
							  dataField:"lineOfBusiness",
							  filtration : {
					            name:"line_of_business",
					            alias:"Line of Business"
				              },
							  sort : "line_of_business"
						},
						//7//			STRIPPED CIRCUIT NUMBER
						{ title : "Stripped Circuit Number",
							  dataField:"strippedCircuitNumber",
//							  dataField : function(o){ 
//									var htmlContent = "<a href=\"#\" onclick=\"_searchMIDetail(" + o.id + ",'" + o.strippedCircuitNumber + "')\">" + o.strippedCircuitNumber + "</a>";
//									return htmlContent;
//							  },
							  filtration : {
					            name:"stripped_circuit_number",
					            alias:"Stripped Circuit Number"
				              },
							  sort : "stripped_circuit_number"
						},
						//10//			SERVICE ID
						{ title : "Service ID",
							  dataField:"serviceId",
							  filtration : {
					            name:"service_id",
					            alias:"Service ID"
				              },
							  sort : "service_id"
						},
						//23
						{ title : "Validation Source System",
							  dataField:"validationSourceSystem",
							  filtration : {
					            name:"validation_source_system",
					            alias:"Validation Source System"
				              },
							  sort : "validation_source_system"
						},
						//13//			CIRCUIT ID STATUS
						{ title : "Circuit Id Status",
							  dataField:"circuitStatus",
							  filtration : {
					            name:"circuit_status",
					            alias:"Circuit Id Status"
				              },
							  sort : "circuit_status"
						},
						//16//			INSTALL DATE
						{ title : "Install Date",
							  dataField:"installDate",
							  filtration : {
					            name:"installation_date",
					            alias:"Install Date"
				              },
							  sort : "installation_date"
						},
						//17//			FIRST INVOICE DATE
						{ title : "First Invoice Date",
							  dataField:"firstInvoiceDate",
							  filtration : {
					            name:"first_invoice_date",
					            alias:"First Invoice Date"
				              },
							  sort : "first_invoice_date"
						},
						//18//			FIRST INVOICE NUMBER
						{ title : "First Invoice Number",
							  dataField:"firstInvoiceNumber",
							  filtration : {
					            name:"first_invoice_number",
					            alias:"First Invoice Number"
				              },
							  sort : "first_invoice_number"
						},
						//22//			DISCONNECTION DATE
						{ title : "Disconnection Date",
							  dataField:"disconnectionDate",
							  filtration : {
					            name:"disconnection_date",
					            alias:"Disconnection Date"
				              },
							  sort : "disconnection_date"
						},
						//25//			SERVICE DESCRIPTION
						{ title : "Service Description",
							  dataField:"serviceDescription",
							  filtration : {
					            name:"service_description",
					            alias:"Service Description"
				              },
							  sort : "service_description"
						},
						//15//			TYPE
						{ title : "Access Type",
							  dataField:"accessType",
							  filtration : {
					            name:"access_type",
					            alias:"Access Type"
				              },
							  sort : "access_type"
						},
						//26//					PRODUCTCATEGORY
						{ title : "Product Category",
							  dataField:"productCategory",
							  filtration : {
					            name:"product_category",
					            alias:"Product Category"
				              },
							  sort : "product_category"
						},
						//27//					SUBPRODUCTCATEGORY
						{ title : "Sub Product Category",
							  dataField:"subProductCategory",
							  filtration : {
					            name:"sub_product_category",
					            alias:"Sub Product Category"
				              },
							  sort : "sub_product_category"
						},
						//28//					PROJECT
						{ title : "Project",
							  dataField:"project",
							  filtration : {
					            name:"project",
					            alias:"Project"
				              },
							  sort : "project"
						},
						//29//					PROJECT CATEGORY STATUS
						{ title : "Project Category Status",
							  dataField:"projectCategoryStatus",
							  filtration : {
					            name:"project_category_status",
					            alias:"Project Category Status"
				              },
							  sort : "project_category_status"
						},
						//30//					A ADDRESS STREET NUMBER
						{ title : "A Address Street Number",
							  dataField:"aStreetNumber",
							  filtration : {
					            name:"a_street_number",
					            alias:"A Address Street Number"
				              },
							  sort : "a_street_number"
						},
						//31//					A ADDRESS STREET NAME
						{ title : "A Address Street Name",
							  dataField:"aStreetName",
							  filtration : {
					            name:"a_street_name",
					            alias:"A Address Street Name"
				              },
							  sort : "a_street_name"
						},
						//32//					A ADDRESS SUITE
						{ title : "A Address Unit",
							  dataField:"aUnit",
							  filtration : {
					            name:"a_unit",
					            alias:"A Address Unit"
				              },
							  sort : "a_unit"
						},
						//33//					A ADDRESS CITY
						{ title : "A Address City",
							  dataField:"aCity",
							  filtration : {
					            name:"a_city",
					            alias:"A Address City"
				              },
							  sort : "a_city"
						},
						//34//					A ADDRESS PROVINCE
						{ title : "A Address Province",
							  dataField:"aProvince",
							  filtration : {
					            name:"a_province",
					            alias:"A Address Province"
				              },
							  sort : "a_province"
						},
						//35//					A ADDRESS POSTAL CODE
						{ title : "A Address Postal Code",
							  dataField:"aPostalCode",
							  filtration : {
					            name:"a_postal_code",
					            alias:"A Address Postal Code"
				              },
							  sort : "a_postal_code"
						},
						//45//					AGGREGATOR CIRCUITID
						{ title : "Aggregator CircuitID",
							  dataField:"aggregatorCid",
							  filtration : {
					            name:"aggregator_cid",
					            alias:"Aggregator CircuitID"
				              },
							  sort : "aggregator_cid"
						},
						//49//					CUSTOMER BILLING ACCOUNT #
						{ title : "Customer Billing Account #",
							  dataField:"customerBillingAccount",
							  filtration : {
					            name:"customer_billing_account",
					            alias:"Customer Billing Account #"
				              },
							  sort : "customer_billing_account"
						},
						//50//					BUSINESS SEGMENT
						{ title : "Business Segment",
							  dataField:"businessSegment",
							  filtration : {
					            name:"business_segment",
					            alias:"Business Segment"
				              },
							  sort : "business_segment"
						},
						//51//					END USER
						{ title : "End User",
							  dataField:"endUser",
							  filtration : {
					            name:"end_user",
					            alias:"End User"
				              },
							  sort : "end_user"
						},
						//52//					SCOA 
						{ title : "SCOA",
							  dataField:"scoa",
							  filtration : {
					            name:"scoa",
					            alias:"SCOA"
				              },
							  sort : "scoa"
						},
						//53//					OWNER
						{ title : "Owner",
							  dataField:"owner",
							  filtration : {
					            name:"owner",
					            alias:"Owner"
				              },
							  sort : "owner"
						},
						//65//					INTERCOMPANY BUSINESS UNIT
						{ title : "Intercompany Business Unit",
							  dataField:"intercompanyBusinessUnit",
							  filtration : {
					            name:"intercompany_business_unit",
					            alias:"Intercompany Business Unit"
				              },
							  sort : "intercompany_business_unit"
						},
						//66//					INTERCOMPANY CHANNEL
						{ title : "Intercompany Channel",
							  dataField:"intercompanyChannel",
							  filtration : {
					            name:"intercompany_channel",
					            alias:"Intercompany Channel"
				              },
							  sort : "intercompany_channel"
						},
						//+ 新加入字段			USOC
						{ title : "USOC",
							  dataField:"usoc",
							  filtration : {
					            name:"usoc",
					            alias:"USOC"
				              },
							  sort : "usoc"
						},
						//+ 新加入字段			USOC
						{ title : "Quantity",
							dataField:"quantity",
							filtration : {
								name:"quantity",
								alias:"Quantity"
							},
							sort : "quantity",
							className:"aright"
						},
						//57//					RATE
						{ title : "Billing Rate",
							  dataField:"billingRate",
							  filtration : {
					            name:"billing_rate",
					            alias:"Billing Rate"
				              },
							  sort : "billing_rate",
							  className:"aright"
						},
						{ title : "Billing Amount",
							dataField:"itemAmount",
							filtration : {
								name:"item_amount",
								alias:"Billing Amount"
							},
							sort : "item_amount",
							className:"aright"
						},
						//64//					AGREEMENT TYPE
						{ title : "Agreement Type",
							  dataField:"agreementType",
							  filtration : {
					            name:"agreement_type",
					            alias:"Agreement Type"
				              },
							  sort : "agreement_type"
						},
						//59//					CONTRACT
						{ title : "Contract",
							  dataField:"contract",
							  filtration : {
					            name:"contract",
					            alias:"Contract"
				              },
							  sort : "contract_number"
						},
						{ title : "Contract Term",
							dataField:"contractTerm",
							filtration : {
							name:"contract_term",
							alias:"Contract Term"
						},
						sort : "contract_term"
						},
						{ title : "Termination Fee",
							dataField:"terminationFee",
							filtration : {
							name:"termination_fee",
							alias:"Termination Fee"
						},
						sort : "termination_fee"
						},
						//60//					TARIFF
						{ title : "Tariff",
							  dataField:"tariff",
							  filtration : {
					            name:"tariff",
					            alias:"Tariff"
				              },
							  sort : "tariff"
						},
						{ title : "Tariff Page",
							  dataField:"tariffPage",
							  filtration : {
					            name:"tariff_page",
					            alias:"Tariff Page"
				              },
							  sort : "tariff_page"
						},
						{ title : "Rate",
							dataField:"rate",
							filtration : {
								name:"rate",
								alias:"Rate"
							},
							sort : "rate",
							className:"aright"
						},
						{ title : "Rate Effective Date",
							  dataField:"rateEffectiveDate",
							  filtration : {
					            name:"rate_effective_date",
					            alias:"Rate Effective Date"
				              },
							  sort : "rate_effective_date"
						},
						{ title : "Base Amount",
							dataField:"baseAmount",
							filtration : {
								name:"base_amount",
								alias:"Base Amount"
							},
							sort : "base_amount",
							className:"aright"
						},
						{ title : "Rate Multiplier",
							dataField:"rateMultiplier",
							filtration : {
							name:"rate_multiplier",
							alias:"Rate Multiplier"
						},
							sort : "rate_multiplier",
							className:"aright"
						},
						{ title : "Discount",
							dataField:"discount",
							filtration : {
							name:"discount",
							alias:"Discount"
						},
						sort : "discount"
						},
						//63//					RATE STATUS
						{ title : "Rate Status",
							  dataField:"rateStatus",
							  filtration : {
					            name:"rate_status",
					            alias:"Rate Status"
				              },
							  sort : "rate_status"
						},
						//69//					RATE DISCREPANCY
						{ title : "Rate Discrepancy",
							  dataField:"rateDiscrepancyFlag",
							  filtration : {
					            name:"rate_discrepancy_flag",
					            alias:"Rate Discrepancy"
				              },
							  sort : "rate_discrepancy_flag"
						},
						//62//					EXPIRY DATE
						{ title : "Expiry Date",
							  dataField:"expiryDate",
							  filtration : {
					            name:"expiry_date",
					            alias:"Expiry Date"
				              },
							  sort : "expiry_date"
						}
					]
			});
		}
		
		masterInventoryRateChangeHistoryListPage.voParam = {
				inventoryId : inventoryId
		};
		
		masterInventoryRateChangeHistoryListPage.addTotalSuccessEvent(function(data){
			if(data.totalResultNo > 0) {
				$('#download_button').show();
			}
		});
		
		filter1 = new SANINCO.Filter();
	    filter1.add('user_name', 'string');
	    filter1.add('history_modified_timestamp', 'string');
	    filter1.add('mid', 'number');
		filter1.add('vendor_name', 'string');
		filter1.add('summary_vendor_name', 'string');
		filter1.add('ban_name', 'string');
		filter1.add('invoice_number', 'string')
		filter1.add('line_of_business', 'string');
		filter1.add('stripped_circuit_number', 'string');
		filter1.add('service_id', 'string');
		filter1.add('validation_source_system', 'string');
		filter1.add('circuit_status', 'string');
		filter1.add('installation_date', 'string');
		filter1.add('first_invoice_date', 'string');
		filter1.add('first_invoice_number', 'string');
		filter1.add('disconnection_date', 'string');
		filter1.add('service_description', 'string');
		filter1.add('access_type', 'string');
		filter1.add('product_category', 'string');
		filter1.add('project', 'string');
		filter1.add('project_category_status', 'string');
		filter1.add('a_street_number', 'string');
		filter1.add('a_street_name', 'string');
		filter1.add('a_unit', 'string');
		filter1.add('a_city', 'string');
		filter1.add('a_province', 'string');
		filter1.add('a_postal_code', 'string');
		filter1.add('aggregator_cid', 'string');
		filter1.add('customer_billing_account', 'string');
		filter1.add('business_segment', 'string');
		filter1.add('end_user', 'string');
		filter1.add('scoa', 'string');
		filter1.add('owner', 'string');
		filter1.add('intercompany_business_unit', 'string');
		filter1.add('intercompany_channel', 'string');
		filter1.add('usoc', 'string');
		filter1.add('quantity', 'string');
		filter1.add('billing_rate', 'string');
		filter1.add('item_amount', 'string');
		filter1.add('agreement_type', 'string');
		filter1.add('contract', 'string');
		filter1.add('contract_term', 'string');
		filter1.add('termination_fee', 'string');
		filter1.add('tariff', 'string');
		filter1.add('tariff_page', 'string');
		filter1.add('rate', 'string');
		filter1.add('rate_effective_date', 'string');
		filter1.add('base_amount', 'string');
		filter1.add('rate_multiplier', 'string');
		filter1.add('discount', 'string');
		filter1.add('rate_status', 'string');
		filter1.add('rate_discrepancy_flag', 'string');
		filter1.add('expiry_date', 'string');
	    
	    
		filter1.addEditeEvent(function(){masterInventoryRateChangeHistoryListPage.start();});
		masterInventoryRateChangeHistoryListPage.setFilter(filter1);
		

		masterInventoryRateChangeHistoryListPage.start();
	},
	_composePostDataFromUi: function(){
		var postData = "";
		
//		postData += "&searchInventoryDashboardVO.inventoryId=" + changeHistory._inventoryId;
		postData += "&searchInventoryDashboardVO.vendorId=483";
		
		return postData;
	},
	saveInventoryRateChangeHistoryToExcel: function(){
		showLoadingModalLayer();
		var titles= "titles=Modifier&" +
					"titles=Modified Time&" +
					"titles=Key&" +
					"titles=BAN&" +
					"titles=Vendor Name&" +
					"titles=Summary Vendor Name&" +
					"titles=Invoice Number&" +
					"titles=Line of Business&" +
					"titles=Stripped Circuit Number&" +
					"titles=Service ID&" +
					"titles=Validation Source System&" +
					"titles=Circuit ID Status&" +
					"titles=Install Date&" +
					"titles=First Invoice Date&" +
					"titles=First Invoice Number&" +
					"titles=Disconnection Date&" +
					"titles=Service Description&" +
					"titles=Access Type&" +
					"titles=Product Category&" +
					"titles=Sub Product&" +
					"titles=Project&" +
					"titles=Project Category Status&" +
					"titles=A Address Street Number&" +
					"titles=A Address Street Name&" +
					"titles=A Address Suite&" +
					"titles=A Address City&" +
					"titles=A Address Province&" +
					"titles=A Address Postal Code&" +
					"titles=Aggregator CircuitID&" +
					"titles=Customer Billing Account&" +
					"titles=Business Segment&" +
					"titles=End User&" +
					"titles=SCOA&" +
					"titles=Owner&" +
					"titles=Intercompany Business Unit&" +
					"titles=Intercompany Channel&" +
					"titles=USOC&" +
					"titles=Quantity&" +
					"titles=Billing Rate&" +
					"titles=Billing Amount&" +
					"titles=Agreement Type&" +
					"titles=Contract&" +
					"titles=Circuit Term&" +
					"titles=Termination Fee&" +
					"titles=Tariff&" +
					"titles=Tariff Page&" +
					"titles=Rate&" +
					"titles=Rate Effective Date&" +
					"titles=Base Amount&" +
					"titles=Rate Multiplier&" +
					"titles=Discount&" +
					"titles=Rate Status&" +
					"titles=Rate Discrepancy&" +
					"titles=Expiry Date";
			titles = titles.replace(/#/g, "%23");
//		var titles = masterInventoryChangeHistoryListPage.getTitlesParam("titles"); // 获取所有的列头。
		var params = "searchInventoryDashboardVO.inventoryId = " + changeHistory._inventoryId;
		if(filter1.getCause()!="") {
			params += ("&searchInventoryDashboardVO.filter=" + filter1.getCause());
		} 
		var url = "saveExcelBySearchInventoryRateChangeHistory.action?"+ titles +"&"+ params;
		windowOpen(url);
		hideLoadingModalLayer();
	}
};