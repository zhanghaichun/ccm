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
		changeHistory._getMasterInventoryChangeHistoryList(inventoryId);
	},
	closeChangeHistoryListWindow: function(){
		$('#tab5').show();
		$('#change_history').hide();
	},
	
	_getMasterInventoryChangeHistoryList: function(inventoryId) {
		//!window.masterInventoryListPage
		if(1==1){
			masterInventoryChangeHistoryListPage = new SANINCO.Page("_master_inventory_change_history_list","masterInventoryChangeHistoryListPage",{
				sortingField : "history_modified_timestamp",
				sortingDirection : "desc",
			    vo : "searchInventoryDashboardVO",
				recordText : '',
				totalPageUri : "getInventoryChangeHistoryListPageNo.action",
			    dataUri :"getInventoryChangeHistoryList.action" ,
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
						//3//			SUMMARY VENDOR NAME
						{ title : "Summary Vendor Name",
							  dataField:"summaryVendorName",
							  filtration : {
					            name:"summary_vendor_name",
					            alias:"Summary Vendor Name"
				              },
							  sort : "summary_vendor_name"
						},
						//4//			BAN
						{ title : "BAN",
							  dataField:"banName",
							  filtration : {
					            name:"ban_name",
					            alias:"BAN"
				              },
							  sort : "ban_name"
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
						//7//			INVOICE DATE
						{ title : "Invoice Date",
							  dataField:"invoiceDate",
							  filtration : {
					            name:"invoice_date",
					            alias:"Invoice Date"
				              },
							  sort : "invoice_date"
						},
						//8//			STRIPPED CIRCUIT NUMBER
						{ title : "Stripped Circuit Number",
							  dataField:"strippedCircuitNumber",
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
						//13//			CIRCUIT ID STATUS
						{ title : "Circuit Id Status",
							  dataField:"circuitStatus",
							  filtration : {
					            name:"circuit_status",
					            alias:"Circuit Id Status"
				              },
							  sort : "circuit_status"
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
						//+ 新加入字段			USOC
						{ title : "USOC",
							  dataField:"usoc",
							  filtration : {
					            name:"usoc",
					            alias:"USOC"
				              },
							  sort : "usoc"
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
						}
					]
			});
		}
		
		masterInventoryChangeHistoryListPage.voParam = {
				inventoryId : inventoryId
		};
		
		masterInventoryChangeHistoryListPage.addTotalSuccessEvent(function(data){
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
		filter1.add('invoice_date', 'string');
		filter1.add('stripped_circuit_number', 'string');
		filter1.add('unique_circuit_id','string');
		filter1.add('service_id','string');
		filter1.add('service_id_mrr','string');
		filter1.add('revenue_match_date','string');
		filter1.add('circuit_status','string');
		filter1.add('service_id_match_status','string');
		filter1.add('access_type','string');
		filter1.add('installation_date','string');
		filter1.add('first_invoice_date','string');
		filter1.add('first_invoice_number','string');
		filter1.add('order_number','string');
		filter1.add('order_type','string');
		filter1.add('quote_number','string');
		filter1.add('disconnection_date','string');
		filter1.add('validation_source_system','string');
		filter1.add('cost_type','string');
		filter1.add('service_description','string');
		filter1.add('product_category','string');
		filter1.add('sub_product_category','string');
		filter1.add('project','string');
		filter1.add('project_category_status','string');
		filter1.add('a_street_number','string');
		filter1.add('a_street_name','string');
		filter1.add('a_unit','string');
		filter1.add('a_city','string');
		filter1.add('a_province','string');
		filter1.add('a_postal_code','string');
		filter1.add('a_country','string');
		filter1.add('z_street_number','string');
		filter1.add('z_street_name','string');
		filter1.add('z_unit','string');
		filter1.add('z_city','string');
		filter1.add('z_province','string');
		filter1.add('z_postal_code','string');
		filter1.add('z_country','string');
		filter1.add('serving_wire_centre','string');
		filter1.add('aggregator_cid','string');
		filter1.add('time_slot_vlan_assignment','string');
		filter1.add('comments','string');
		filter1.add('trunk_group_clli','string');
		filter1.add('customer_billing_account','string');
		filter1.add('business_segment','string');
		filter1.add('end_user','string');
		filter1.add('scoa','string');
		filter1.add('owner','string');
		filter1.add('owner_email','string');
		filter1.add('last_signoff_date','string');
		filter1.add('usoc','string');
		filter1.add('intercompany_business_unit','string');
		filter1.add('intercompany_channel','string');
		filter1.add('modified_timestamp','string');
		filter1.add('modified_user','string');
	    
		filter1.addEditeEvent(function(){masterInventoryChangeHistoryListPage.start();});
		masterInventoryChangeHistoryListPage.setFilter(filter1);
		

		masterInventoryChangeHistoryListPage.start();
	},
	_composePostDataFromUi: function(){
		var postData = "";
		
//		postData += "&searchInventoryDashboardVO.inventoryId=" + changeHistory._inventoryId;
		postData += "&searchInventoryDashboardVO.vendorId=483";
		
		return postData;
	},
	saveInventoryChangeHistoryToExcel: function(){
		showLoadingModalLayer();
		var titles= "titles=Modifier&" +
					"titles=Modified Time&" +
					"titles=Key&" +
					"titles=Vendor Name&" +
					"titles=Summary Vendor Name&" +
					"titles=BAN&" +
					"titles=Invoice Number&" +
					"titles=Line of Business&" +
					"titles=Invoice Date&" +
					"titles=Stripped Circuit Number&" +
					"titles=Unique Circuit ID&" +
					"titles=Service ID&" +
					"titles=Service ID Mrr&" +
					"titles=Service ID MRR Province&" +
					"titles=Revenue Match Date&" +
					"titles=Circuit ID Status&" +
					"titles=Service ID Match Status&" +
					"titles=Access Type&" +
					"titles=Install Date&" +
					"titles=First Invoice Date&" +
					"titles=First Invoice Number&" +
					"titles=Order Number&" +
					"titles=Order Type&" +
					"titles=Quote Number&" +
					"titles=Disconnection Date&" +
					"titles=Validation Source System&" +
					"titles=Cost Type&" +
					"titles=Service Description&" +
					"titles=Product Category&" +
					"titles=Sub Product Category&" +
					"titles=Project&" +
					"titles=Project Category Status&" +
					"titles=A Address Street Number&" +
					"titles=A Address Street Name&" +
					"titles=A Address Unit&" +
					"titles=A Address City&" +
					"titles=A Address Province&" +
					"titles=A Address Postal Code&" +
					"titles=A Address Country&" +
					"titles=Z Address Street Number&" +
					"titles=Z Address Street Name&" +
					"titles=Z Address Unit&" +
					"titles=Z Address City&" +
					"titles=Z Address Province&" +
					"titles=Z Address Postal Code&" +
					"titles=Z Address Country&" +
					"titles=Region&" +
					"titles=Serving Wire Centre&" +
					"titles=Aggregator Circuit ID&" +
					"titles=Time Slot or Vlan Assignment&" +
					"titles=Comments&" +
					"titles=Trunk Group CLLI&" +
					"titles=Customer Billing Account #&" +
					"titles=Business Segment&" +
					"titles=End User&" +
					"titles=SCOA&" +
					"titles=Owner&" +
					"titles=Owner E-mail&" +
					"titles=Last Signoff Date&" +
					"titles=USOC&" +
					"titles=Intercompany Business Unit&" +
					"titles=Intercompany Channel&" +
					"titles=FSA Code&" +
					"titles=Serviceability Fibre&" +
					"titles=Serviceability Cable&";
			titles = titles.replace(/#/g, "%23");
//		var titles = masterInventoryChangeHistoryListPage.getTitlesParam("titles"); // 获取所有的列头。
		var params = "searchInventoryDashboardVO.inventoryId = " + changeHistory._inventoryId;
		if(filter1.getCause()!="") {
			params += ("&searchInventoryDashboardVO.filter=" + filter1.getCause());
		} 
		var url = "saveExcelBySearchInventoryChangeHistory.action?"+ titles +"&"+ params;
		windowOpen(url);
		hideLoadingModalLayer();
	}
};