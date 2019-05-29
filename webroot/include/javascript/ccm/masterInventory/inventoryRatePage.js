function startSearch() {
	getMasterInventoryRateList();
	searchChartData();
}
$(function() {
	$('form').bind('keydown',contractEnterToStartSearch);
})
function contractEnterToStartSearch() {
	
	// key code => 13 的按键是Enter键， 系统中的search方法应该
	// 定义为startSearch.
	if( window.event.keyCode == 13) {
		startSearch();
	}
}
YAHOO.util.Event.onDOMReady(function () {

	YAHOO.ccm.container.miDetailWindow = new YAHOO.widget.Dialog("miDetailWindow", 
			{ 
				width: "1000px",
				height: "620px",
			    fixedcenter: true,
			    visible: false,
			    constraintoviewport: true,
			    modal: true
			});
	YAHOO.ccm.container.miDetailWindow.render();

});

//Search MI Detail
function searchMIDetail(miId) {
	$.ajax({
		url: 'getMIDetail.action',
		type: 'POST',
		data: '&searchInventoryDashboardVO.mid=' + miId,
		dataType: 'text',
		success: function(o) {
			var miObject = eval("("+o+")").data[0];
			$("#detail_id").val(miObject.id);
			$("#detail_vendor_name").val(miObject.vendorName);
			$("#detail_summary_vendor_name").val(miObject.summaryVendorName);
			$("#detail_ban_name").val(miObject.banName);
			$("#detail_invoice_number").val(miObject.invoiceNumber);
			$("#detail_line_of_business").val(miObject.lineOfBusiness);
			$("#detail_invoice_date").val(miObject.invoiceDate.replace(new RegExp("-","gm"),"/"));
			$("#detail_stripped_circuit_number").val(miObject.strippedCircuitNumber);
			$("#detail_unique_circuit_id").val(miObject.uniqueCircuitId);
			$("#detail_service_id").val(miObject.serviceId);
			$("#detail_service_id_mrr").val(miObject.serviceIdMrr);
			
			$("#detail_revenue_match_date").val(miObject.revenueMatchDate.replace(new RegExp("-","gm"),"/"));
			$("#detail_circuit_id_status").val(miObject.circuitStatus);
			$("#detail_service_id_match_status").val(miObject.serviceIdMatchStatus);
			$("#detail_access_type").val(miObject.accessType);
			$("#detail_install_date").val(miObject.installDate.replace(new RegExp("-","gm"),"/"));
			$("#detail_first_invoice_date").val(miObject.firstInvoiceDate.replace(new RegExp("-","gm"),"/"));
			$("#detail_first_invoice_number").val(miObject.firstInvoiceNumber);
			$("#detail_order_number").val(miObject.orderNumber);
			$("#detail_order_type").val(miObject.orderType);
			$("#detail_quote_number").val(miObject.quoteNumber);
			
			$("#detail_disconnection_date").val(miObject.disconnectionDate.replace(new RegExp("-","gm"),"/"));
			$("#detail_validation_source_system").val(miObject.validationSourceSystem);
			form0_searchInventoryDashboardVO_costType.setValue(miObject.costType);
			$("#detail_service_description").val(miObject.serviceDescription);
			$("#detail_product_category").val(miObject.productCategory);
			$("#detail_sub_product_category").val(miObject.subProductCategory);
			$("#detail_project").val(miObject.project);
			$("#detail_project_category_status").val(miObject.projectCategoryStatus);
			$("#detail_a_address_street_number").val(miObject.aStreetNumber);
			$("#detail_a_address_street_name").val(miObject.aStreetName);
			
			$("#detail_a_address_unit").val(miObject.aUnit);
			$("#detail_a_address_city").val(miObject.aCity);
			$("#detail_a_address_province").val(miObject.aProvince);
			$("#detail_a_address_postal_code").val(miObject.aPostalCode);
			$("#detail_a_country").val(miObject.aCountry);
			$("#detail_z_street_number").val(miObject.zStreetNumber);
			$("#detail_z_street_name").val(miObject.zStreetName);
			$("#detail_z_unit").val(miObject.zUnit);
			$("#detail_z_city").val(miObject.zCity);
			$("#detail_z_province").val(miObject.zProvince);
			
			$("#detail_z_postal_code").val(miObject.zPostalCode);
			$("#detail_z_country").val(miObject.zCountry);
			$("#detail_tariff_page").val(miObject.tariffPage);
			$("#detail_serving_wire_centre").val(miObject.servingWireCentre);
			$("#detail_aggregator_circuit_id").val(miObject.aggregatorCid);
			$("#detail_time_slot_vlan_assignment").val(miObject.timeSlotVlanAssignment);
			$("#detail_comments").val(miObject.comments);
			$("#detail_trunk_group_clli").val(miObject.trunkGroupClli);
			$("#detail_customer_billing_account").val(miObject.customerBillingAccount);
			$("#detail_business_segment").val(miObject.businessSegment);
			$("#detail_end_user").val(miObject.endUser);
			account_code.setName(miObject.scoa);
			$("#detail_owner").val(miObject.owner);
			$("#detail_owner_email").val(miObject.ownerEmail);
			$("#detail_last_signoff_date").val(miObject.lastSignoffDate.replace(new RegExp("-","gm"),"/"));
			$("#detail_multiplier").val(miObject.multiplier);
			$("#detail_usoc").val(miObject.usoc);
			$("#detail_rate").val(miObject.rate);
			$("#detail_rate_effective_date").val(miObject.rateEffectiveDate.replace(new RegExp("-","gm"),"/"));
			$("#detail_circuit_term").val(miObject.circuitTerm);
			$("#detail_contract").val(miObject.contractNumber);
			
			$("#detail_tariff").val(miObject.tariffName);
			$("#detail_expiry_date").val(miObject.expiryDate.replace(new RegExp("-","gm"),"/"));
			$("#detail_rate_status").val(miObject.rateStatus);
			$("#detail_agreement_type").val(miObject.agreementType);
			$("#detail_intercompany_business_unit").val(miObject.intercompanyBusinessUnit);
			$("#detail_intercompany_channel").val(miObject.intercompanyChannel);
			$("#detail_modified_date").val(miObject.modifiedTimestamp.replace(new RegExp("-","gm"),"/"));
			$("#detail_modified_user").val(miObject.modifiedUser);
			$("#detail_rate_discrepancy").val(miObject.rateDiscrepancyFlag);
			$("#detail_termination_penalty").val(miObject.terminationPenaltyPercentage);
		}
	});
}


//Search Chart
function searchChartData() {
	$.ajax({
		url: 'searchMasterInvenrotyRateCompleteByFilter.action',
		type: 'POST',
		data: composePostDataFromUi(),
		dataType: 'text',
		success: function(o) {
			var jsonData  = JSON.parse(o);
			var cp_array = [];
			var sum = 0;
			for (var i = 0; i < jsonData.data.length; i+=1){
				var data = jsonData.data[i];
				cp_array.push([data.completeFlag,parseInt(data.completeCount)]);
				sum += parseInt(data.completeCount);
			}
			if(sum > 0){
				p.complete_Array = {"results":cp_array};
			} else {
				p.complete_Array = null;
			}
			p.init(false);
			
		}
	});
}

//Master Inventory List
function getMasterInventoryRateList() {
	//!window.masterInventoryRateListPage
	if(1==1){
		masterInventoryRateListPage = new SANINCO.Page("_master_inventory","masterInventoryRateListPage",{
		    vo : "searchInventoryDashboardVO",
			recordText : '',
			totalPageUri : "getMasterInventoryRateListPageNo.action",
		    dataUri :"searchMasterInventoryRateList.action" ,
		    paginationDiv : "__master_inventory_page_list",
			recPerArray : [10,20,30,40,50,70,90,100],

			
		    cols : [
		            //1//			KEY
					{ title : "Key",
					  dataField:"id",
					  filtration : {
			            name:"mid",
			            alias:"Key"
		              },
					  sort : "mid"
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
					{ title : "Ban",
						dataField:"banName",
						filtration : {
						name:"ban_name",
						alias:"Ban"
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
					//7//			STRIPPED CIRCUIT NUMBER
					{ title : "Stripped Circuit Number",
						  dataField:"strippedCircuitNumber",
//						  dataField : function(o){ 
//								var htmlContent = "<a href=\"#\" onclick=\"_searchMIDetail(" + o.id + ",'" + o.strippedCircuitNumber + "')\">" + o.strippedCircuitNumber + "</a>";
//								return htmlContent;
//						  },
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
					//+ 新加入字段			
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
						sort : "discount",
						className:"aright"
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
					},
					{ title : "Update History",
					  dataField : function(o){ 
							var htmlContent = "<img src=\"include/images/contractTariffPriceList/penalty-notes.png\" " +
												   "style=\"margin-right:5px\" " +
												   "onclick=\"changeHistory._searchChangeHistoryList(" + o.id + ",'" + o.strippedCircuitNumber + "')\"/>" +
												   "<a href=\"#\" onclick=\"changeHistory._searchChangeHistoryList(" + o.id + ",'" + o.strippedCircuitNumber + "')\">Update History</a>";
							return htmlContent;
						}
					}
				]
		});
	}

	masterInventoryRateListPage.voParam = {
			serviceId : encodeURIComponent($("#service_id_text")[0].value),
			banId: form0_searchInventoryDashboardVO_banList.getValue(),
			circuitId: encodeURIComponent(stripCircuitNumber($("#circuit_id_text")[0].value)),
			summaryVendorName: form0_searchInventoryDashboardVO_vendorId.getValue(),
			completeFlag: form0_searchInventoryDashboardVO_CompleteFlag.getValue(),
//			completeFlag: ($('#__not_completed')[0].checked)? 'Y':null,
			customerBillingAccount: encodeURIComponent($("#customer_billing_account")[0].value),
			endUser:encodeURIComponent($("#end_user_text")[0].value),
			aStreetNumber:encodeURIComponent($("#a_street_number")[0].value),
			aStreetName:encodeURIComponent($("#a_street_name")[0].value),
			aUnit:encodeURIComponent($("#a_unit")[0].value),
			aCity:encodeURIComponent($("#a_city")[0].value),
			aProvince:encodeURIComponent($("#a_province")[0].value),
			aCountry:encodeURIComponent($("#a_country")[0].value),
			intercompanyBusinessUnit:encodeURIComponent($("#intercompany_business_unit_text").val()),
			scoaCompany:encodeURIComponent($("#SCOA_Company").val()),
			scoaLocation:encodeURIComponent($("#SCOA_Location").val()),
			scoaDepartment:encodeURIComponent($("#SCOA_Department").val()),
			scoaProduct:encodeURIComponent($("#SCOA_Production").val()),
			scoaAccount:encodeURIComponent($("#SCOA_Account").val()),
			scoaChannel:encodeURIComponent($("#SCOA_Channel").val())
	};
	
	masterInventoryRateListPage.addTotalSuccessEvent(function(data){
		if(data.totalResultNo > 0) {
			$('#download_button').show();
		}
	});
	
	filter1 = new SANINCO.Filter();
	
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

	filter1.addEditeEvent(function(){masterInventoryRateListPage.start();});
	masterInventoryRateListPage.setFilter(filter1);
	

	masterInventoryRateListPage.start();
}

function resetRadioButton(){
	
}

function updateRadioButton(){
	
}

function updateVendorAndBanTextfield(){
	
}
/**
 * circuit number remove ( ) _ - \ : . ' ' /
 */
function stripCircuitNumber(circuitNumber) {
	return circuitNumber.replace(/[\(\)\.\:_\\\/\s-]/g, '');
}

function composePostDataFromUi() {
	
	var isDownloadTemplete = arguments[0] ? arguments[0] : false;
	
	var postData = "";
	
	if(isDownloadTemplete){
		postData += "&searchInventoryDashboardVO.inventoryId=empty";
	} else {
		if($.trim($("#service_id_text").val()).length>0) {
			postData += "&searchInventoryDashboardVO.serviceId=" + encodeURIComponent($("#service_id_text").val());
		}
		
		if($.trim($("#customer_billing_account").val()).length>0) {
			postData += "&searchInventoryDashboardVO.customerBillingAccount=" + encodeURIComponent($("#customer_billing_account").val());
		}
		
		if($.trim($("#end_user_text").val()).length>0) {
			postData += "&searchInventoryDashboardVO.endUser=" + encodeURIComponent($("#end_user_text").val());
		}
		
		if($.trim($("#a_street_number").val()).length>0) {
			postData += "&searchInventoryDashboardVO.aStreetNumber=" + encodeURIComponent($("#a_street_number").val());
		}
		
		if($.trim($("#a_street_name").val()).length>0) {
			postData += "&searchInventoryDashboardVO.aStreetName=" + encodeURIComponent($("#a_street_name").val());
		}
		
		if($.trim($("#a_unit").val()).length>0) {
			postData += "&searchInventoryDashboardVO.aUnit=" + encodeURIComponent($("#a_unit").val());
		}
		
		if($.trim($("#a_city").val()).length>0) {
			postData += "&searchInventoryDashboardVO.aCity=" + encodeURIComponent($("#a_city").val());
		}
		
		if($.trim($("#a_province").val()).length>0) {
			postData += "&searchInventoryDashboardVO.aProvince=" + encodeURIComponent($("#a_province").val());
		}
		
		if($.trim($("#a_country").val()).length>0) {
			postData += "&searchInventoryDashboardVO.aCountry=" + encodeURIComponent($("#a_country").val());
		}
		
		if($.trim($("#circuit_id_text").val()).length>0) {
			postData += "&searchInventoryDashboardVO.circuitId=" + encodeURIComponent(stripCircuitNumber($("#circuit_id_text").val()));
		}
		
		if($.trim($("#intercompany_business_unit_text").val()).length>0) {
			postData += "&searchInventoryDashboardVO.intercompanyBusinessUnit=" + encodeURIComponent(stripCircuitNumber($("#intercompany_business_unit_text").val()));
		}
		
		if($.trim($("#SCOA_Company").val()).length>0) {
			postData += "&searchInventoryDashboardVO.scoaCompany=" + encodeURIComponent($("#SCOA_Company").val());
		}
		if($.trim($("#SCOA_Location").val()).length>0) {
			postData += "&searchInventoryDashboardVO.scoaLocation=" + encodeURIComponent($("#SCOA_Location").val());
		}
		if($.trim($("#SCOA_Departement").val()).length>0) {
			postData += "&searchInventoryDashboardVO.scoaDepartment=" + encodeURIComponent($("#SCOA_Department").val());
		}
		if($.trim($("#SCOA_Production").val()).length>0) {
			postData += "&searchInventoryDashboardVO.scoaProduct=" + encodeURIComponent($("#SCOA_Production").val());
		}
		if($.trim($("#SCOA_Account").val()).length>0) {
			postData += "&searchInventoryDashboardVO.scoaAccount=" + encodeURIComponent($("#SCOA_Account").val());
		}
		if($.trim($("#SCOA_Channel").val()).length>0) {
			postData += "&searchInventoryDashboardVO.scoaChannel=" + encodeURIComponent($("#SCOA_Channel").val());
		}
		
		postData += "&searchInventoryDashboardVO.banId=" + form0_searchInventoryDashboardVO_banList.getValue();
		
		postData += "&searchInventoryDashboardVO.summaryVendorName=" + form0_searchInventoryDashboardVO_vendorId.getValue();
		
		postData += "&searchInventoryDashboardVO.completeFlag=" + form0_searchInventoryDashboardVO_CompleteFlag.getValue();
		
//		if((($('#__not_completed')[0].checked)? 'Y':'N') == 'Y') {
//			postData += "&searchInventoryDashboardVO.completeFlag=Y";
//		}
		
		if(typeof(filter1) != "undefined" && filter1.getCause()!="") {
			postData += ("&searchInventoryDashboardVO.filter=" + filter1.getCause());
		}
	}
	return postData;
}

function setJSelectQuicklink(k,v,d){
//	if(k == "searchInventoryDashboardVO.product"){
//		form0_searchInventoryDashboardVO_product.setValue(v);
//		return true;
//	}
	
	if(k == "searchInventoryDashboardVO.banId"){
		form0_searchInventoryDashboardVO_banList.setValue(v);
		return true;
	}
	if(k == "searchInventoryDashboardVO.summaryVendorName"){
		form0_searchInventoryDashboardVO_vendorId.setValue(v);
		return true;
	}
	return false;
}

function saveMasterInventoryRateToExcel(){
	showLoadingModalLayer();
	
	var isDownloadTemplete = arguments[0] ? arguments[0] : false;
	
	var postDate = composePostDataFromUi(isDownloadTemplete);
	
	var titles= "titles=Key&" +
				"titles=Vendor Name&" +
				"titles=Summary Vendor Name&" +
				"titles=BAN&" +
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
				"titles=Expiry Date&";
	titles = titles.replace(/#/g, "%23");
	var url = "saveExcelBySearchMasterInventoryRate.action?"+ titles +"&" + postDate;
	windowOpen(url);
	hideLoadingModalLayer();
}

function _searchMIDetail(id, circuitNumber){
	YAHOO.ccm.container.miDetailWindow.show();
	clearValidationStyle();
	searchMIDetail(id);
}

function _updateMasterInventory() {
//	$("#detail_stripped_circuit_number").attr("readonly",false);
	
	if(_updateCheck() == false){
		return;
	}
	
	var updateMasterInventoryVO = {
			"updateMasterInventoryVO.id": $("#detail_id").val(),
			"updateMasterInventoryVO.strippedCircuitNumber": $("#detail_stripped_circuit_number").val(),
			"updateMasterInventoryVO.uniqueCircuitId": $("#detail_unique_circuit_id").val(),
			"updateMasterInventoryVO.serviceId": $("#detail_service_id").val(),
			"updateMasterInventoryVO.serviceIdMrr": $("#detail_service_id_mrr").val(),
			"updateMasterInventoryVO.circuitStatus": $("#detail_circuit_id_status").val(),
			"updateMasterInventoryVO.accessType": $("#detail_access_type").val(),
			"updateMasterInventoryVO.installDate": $("#detail_install_date").val(),
			"updateMasterInventoryVO.orderNumber": $("#detail_order_number").val(),
			"updateMasterInventoryVO.orderType": $("#detail_order_type").val(),
			"updateMasterInventoryVO.quoteNumber": $("#detail_quote_number").val(),
			"updateMasterInventoryVO.disconnectionDate": $("#detail_disconnection_date").val(),
			"updateMasterInventoryVO.validationSourceSystem": $("#detail_validation_source_system").val(),
			"updateMasterInventoryVO.costType": form0_searchInventoryDashboardVO_costType.getValue(),
			"updateMasterInventoryVO.serviceDescription": $("#detail_service_description").val(),
			"updateMasterInventoryVO.productCategory": $("#detail_product_category").val(),
			"updateMasterInventoryVO.subProductCategory": $("#detail_sub_product_category").val(),
			"updateMasterInventoryVO.project": $("#detail_project").val(),
			"updateMasterInventoryVO.projectCategoryStatus": $("#detail_project_category_status").val(),
			"updateMasterInventoryVO.aStreetNumber": $("#detail_a_address_street_number").val(),
			"updateMasterInventoryVO.aStreetName": $("#detail_a_address_street_name").val(),
			"updateMasterInventoryVO.aUnit": $("#detail_a_address_unit").val(),
			"updateMasterInventoryVO.aCity": $("#detail_a_address_city").val(),
			"updateMasterInventoryVO.aProvince": $("#detail_a_address_province").val(),
			"updateMasterInventoryVO.aPostalCode": $("#detail_a_address_postal_code").val(),
			"updateMasterInventoryVO.aCountry": $("#detail_a_country").val(),
			"updateMasterInventoryVO.zStreetNumber": $("#detail_z_street_number").val(),
			"updateMasterInventoryVO.zStreetName": $("#detail_z_street_name").val(),
			"updateMasterInventoryVO.zUnit": $("#detail_z_unit").val(),
			"updateMasterInventoryVO.zCity": $("#detail_z_city").val(),
			"updateMasterInventoryVO.zProvince": $("#detail_z_province").val(),
			"updateMasterInventoryVO.zPostalCode": $("#detail_z_postal_code").val(),
			"updateMasterInventoryVO.zCountry": $("#detail_z_country").val(),
			"updateMasterInventoryVO.tariffPage": $("#detail_tariff_page").val(),
			"updateMasterInventoryVO.servingWireCentre": $("#detail_serving_wire_centre").val(),
			"updateMasterInventoryVO.aggregatorCid": $("#detail_aggregator_circuit_id").val(),
			"updateMasterInventoryVO.timeSlotVlanAssignment": $("#detail_time_slot_vlan_assignment").val(),
			"updateMasterInventoryVO.comments": $("#detail_comments").val(),
			"updateMasterInventoryVO.trunkGroupClli": $("#detail_trunk_group_clli").val(),
			"updateMasterInventoryVO.customerBillingAccount": $("#detail_customer_billing_account").val(),
			"updateMasterInventoryVO.businessSegment": $("#detail_business_segment").val(),
			"updateMasterInventoryVO.endUser": $("#detail_end_user").val(),
			"updateMasterInventoryVO.scoa": account_code.getName(),
			"updateMasterInventoryVO.owner": $("#detail_owner").val(),
			"updateMasterInventoryVO.ownerEmail": $("#detail_owner_email").val(),
			"updateMasterInventoryVO.lastSignoffDate": $("#detail_last_signoff_date").val(),
			"updateMasterInventoryVO.multiplier": $("#detail_multiplier").val(),
			"updateMasterInventoryVO.usoc": $("#detail_usoc").val(),
			"updateMasterInventoryVO.rate": $("#detail_rate").val(),
			"updateMasterInventoryVO.rateEffectiveDate": $("#detail_rate_effective_date").val(),
			"updateMasterInventoryVO.circuitTerm": $("#detail_circuit_term").val(),
			"updateMasterInventoryVO.contractNumber": $("#detail_contract").val(),
			"updateMasterInventoryVO.expiryDate":$("#detail_expiry_date").val(),
			"updateMasterInventoryVO.rateStatus": $("#detail_rate_status").val(),
			"updateMasterInventoryVO.intercompanyBusinessUnit": $("#detail_intercompany_business_unit").val(),
			"updateMasterInventoryVO.intercompanyChannel": $("#detail_intercompany_channel").val()
	};
	$.ajax({
		url: 'updateMIDetail.action',
		type: 'POST',
		data: updateMasterInventoryVO,
		dataType: 'text',
		success: function(o) {
			YAHOO.ccm.container.miDetailWindow.hide();
			startSearch();
		}
	});
}

function _updateCheck() {
	var bo = true;
	var nodes = YAHOO.util.Selector.query('input[cssClass=validate-date-ca]');
	var numText = YAHOO.util.Selector.query('input[cssClass=validate-number]');
	
	removeClassName('detail_owner_email','validation-failed');
	addClassName('detail_owner_email','validation-passed');
	
	removeClassName('VL_vendorList_account_code_input','validation-failed');
	addClassName('VL_vendorList_account_code_input','validation-passed');
	
	removeClassName('detail_cost_type_input','validation-failed');
	addClassName('detail_cost_type_input','validation-passed');
	
	for(var i = 0; i<nodes.length; i++){
		removeClassName(nodes[i].id,'validation-failed');
		addClassName(nodes[i].id,'validation-passed');
	}
	
	for(var z = 0; z<numText.length; z++){
		removeClassName(numText[z].id,'validation-failed');
		addClassName(numText[z].id,'validation-passed');
	}
	
	for(var d = 0; d<nodes.length; d++){
		if(!isValidDate(nodes[d].value)) {
			removeClassName(nodes[d].id,'validation-passed');
			addClassName(nodes[d].id,'validation-failed');
			bo = false;
		}
	}
	
	for(var j = 0; j<numText.length; j++){
		if(isNaN(numText[j].value)) {
			removeClassName(numText[j].id,'validation-passed');
			addClassName(numText[j].id,'validation-failed');
			bo = false;
		}
	}
	var email = $("#detail_owner_email")[0].value.trim();
	if(email!=null && email != "" && (!email.match(/\w{1,}[@][\w\-]{1,}([.]([\w\-]{1,})){1,3}$/) || email.length >128)){
		removeClassName('detail_owner_email','validation-passed');
		addClassName('detail_owner_email','validation-failed');
		bo = false;
	}
	/*if(!FIC_checkField(" validate-email ",$("#detail_owner_email")[0])) {
		removeClassName('detail_owner_email','validation-passed');
		addClassName('detail_owner_email','validation-failed');
		bo = false;
	}*/
	
	if(account_code.getName() != null && account_code.getName().length > 0){
		if(isInArray() == false){
			removeClassName('VL_vendorList_account_code_input','validation-passed');
			addClassName('VL_vendorList_account_code_input','validation-failed');
			bo = false;
		}
	}
	
	var serviceId = $("#detail_service_id").val();
	if(trim(serviceId) != null && trim(serviceId) != "" && trim(serviceId) != "UNKNOWN"){
		if(form0_searchInventoryDashboardVO_costType.getName() == null || form0_searchInventoryDashboardVO_costType.getName() == ""){
			removeClassName('detail_cost_type_input','validation-passed');
			addClassName('detail_cost_type_input','validation-failed');
			bo = false;
		}
	}
	
	return bo;
}

//清楚验证效果再页面打开时
function clearValidationStyle() {
	var nodes = YAHOO.util.Selector.query('input[cssClass=validate-date-ca]');
	var numText = YAHOO.util.Selector.query('input[cssClass=validate-number]');
	
	removeClassName('detail_owner_email','validation-failed');
	addClassName('detail_owner_email','validation-passed');
	
	removeClassName('VL_vendorList_account_code_input','validation-failed');
	addClassName('VL_vendorList_account_code_input','validation-passed');
	
	removeClassName('detail_cost_type_input','validation-failed');
	addClassName('detail_cost_type_input','validation-passed');
	
	for(var i = 0; i<nodes.length; i++){
		removeClassName(nodes[i].id,'validation-failed');
		addClassName(nodes[i].id,'validation-passed');
	}
	
	for(var z = 0; z<numText.length; z++){
		removeClassName(numText[z].id,'validation-failed');
		addClassName(numText[z].id,'validation-passed');
	}
}

//去左右空格;
function trim(s){
    return s.replace(/(^\s*)|(\s*$)/g, "");
}

/**
 * 使用循环的方式判断一个元素是否存在于一个数组中
 */
function isInArray(){
	var results = SC_Array.results;
	var scoaName = account_code.getName();
    for(var i = 0; i < results.length; i++){
        if(scoaName === results[i].name){
            return true;
        }
    }
    return false;
}

function clearNoNum(obj){   
	obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
	obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是. 
	obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.   
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}

