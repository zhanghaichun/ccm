/**
 * @author mingyang.Li
 * */

var param = {};
YAHOO.util.Event.onDOMReady(function () {
	initDescriptionWindow();
	$("#VL_vendorMainList_1_input").attr("disabled","disabled");
	$("#BL_banMainList_1_input").attr("disabled","disabled");
	
});
function searchrecurringChargeTable(){
	recurringChargePage = new SANINCO.Page("recurringChargeTable","recurringChargePage",{
	      sortingField : "c.stripped_circuit_number",
	      recordText: '', 
	      sortingDirection : "asc",
	      vo : "svvo",
	      paginationDiv: "recurringCharge_pageTable",
	      paginationCustomDiv:"recurringCharge_pageTable_download",
	      recPerArray : [5,10,15,20,30],
	      dataUri : "doRecurringCharges.action",
	      totalPageUri: "doRecurringChargeTotalPageNo.action",
	      cols : [
		      {
		          title : "Stripped Circuit Number",
		          dataField: function(o){
		    	  	return "<a href=\"javascript:void(0)\" onclick=\"popUpDescriptionWindow('"+o.stripped_circuit_number+"')\">"+o.stripped_circuit_number+"</a>";
		    	  } ,
		          sort : "c.stripped_circuit_number",
		          filtration : {name:"c.stripped_circuit_number",alias:"Circuit Number"}
		      },{
		          title : "Description",
		          dataField: "line_item_desc",
		          sort : "c.line_item_desc",
		          filtration : {name:"c.line_item_desc",alias:"Line Item Desc"}
		      },{
		          title : "Total Amount",
		          dataField: function(o){
		          	return o.total_cost_amount != "" ? parseFloat(o.total_cost_amount).toFixed(2) : "";
		      	  },
		          sort : "c.total_cost_amount",
		          filtration : {name:"c.total_cost_amount",alias:"Total Cost Amount"},
		          className:"aright"
		      },{
		          title : "Number of Occurrence",
		          dataField: "counts" ,
		          sort : "c.counts",
		          filtration : {name:"c.counts",alias:"Number of Occurrence"}
		      },{
		          title : "Average Amount",
		          dataField: function(o){
		    	  	return o.numberOfOccurrence != "" ? parseFloat(o.numberOfOccurrence).toFixed(2) : "";
		      	  },
		          sort : "(c.total_cost_amount/c.counts)",
		          filtration : {name:"(c.total_cost_amount/c.counts)",alias:"Average Amount"},
		          className:"aright"
		      }
	      ]
	  });
	
	recurringChargePage.addTotalSuccessEvent(function(data){
		if(data.totalResultNo < 0 || data.error){
			document.getElementById('saveExcel_tab1').style.display = "none";
		}else{
			document.getElementById('saveExcel_tab1').style.display = "block";
		}
	});
	
	recurringChargePage.voParam = {
			vendorId : param.vendorId,
			banId : param.banId
	};
	filter1 = new SANINCO.Filter();
    filter1.addEditeEvent(function(){recurringChargePage.start();});
    filter1.add('c.stripped_circuit_number', 'String');
    filter1.add('c.line_item_desc', 'String');
	filter1.add('c.total_cost_amount', 'String');
	filter1.add('c.counts', 'String');
	filter1.add('(c.total_cost_amount/c.counts)', 'String');
	
	recurringChargePage.setFilter(filter1);
	recurringChargePage.start();
}
function searchAllChargeTable(){
	
	allChargePage = new SANINCO.Page("allChargeTable","allChargePage",{
		sortingField : "i.invoice_number",
		recordText: '', 
		sortingDirection : "asc",
		vo : "svvo",
		paginationDiv: "allCharge_pageTable",
		paginationCustomDiv:"allCharge_pageTable_download",
		recPerArray : [5,10,15,20,30],
		dataUri : "doAllCharges.action",
		totalPageUri: "doAllChargeTotalPageNo.action",
		cols : [
		        {
		        	title : "Invoice Number",
		        	dataField: "invoice_number" ,
		        	sort : "i.invoice_number",
		        	filtration : {name:"i.invoice_number",alias:"Invoice Number"}
		        },{
		        	title : "Invoice Date",
		        	dataField: "invoice_date",
		        	sort : "i.invoice_date",
		        	filtration : {name:"i.invoice_date",alias:"Invoice Date"}
		        },{
		        	title : "Recurring Amount",
		        	dataField: function(o){
		        		return o.invoice_mrc != "" ? parseFloat(o.invoice_mrc).toFixed(2) : "";
		      	    },
		        	sort : "i.invoice_mrc",
		        	filtration : {name:"i.invoice_mrc",alias:"Invoice MRC"},
		        	className:"aright"
		        },{
		        	title : "Non Recurring Amount",
		        	dataField: function(o){
		        		return o.invoice_occ != "" ? parseFloat(o.invoice_occ).toFixed(2) : "";
		      	    },
		        	sort : "i.invoice_occ",
		        	filtration : {name:"i.invoice_occ",alias:"Invoice OCC"},
		        	className:"aright"
		        },{
		        	title : "Usage Amount",
		        	dataField: function(o){
		        		return o.invoice_usage != "" ? parseFloat(o.invoice_usage).toFixed(2) : "";
		      	    },
		        	sort : "i.invoice_usage",
		        	filtration : {name:"i.invoice_usage",alias:"Invoice Usage"},
		        	className:"aright"
		        },{
		        	title : "LPC Amount",
		        	dataField: function(o){
		        		return o.invoice_late_payment_charge != "" ? parseFloat(o.invoice_late_payment_charge).toFixed(2) : "";
		      	    },
		        	sort : "i.invoice_late_payment_charge",
		        	filtration : {name:"i.invoice_late_payment_charge",alias:"Invoice LPC"},
		        	className:"aright"
		        },{
		        	title : "Credit and Adjustment Amount",
		        	dataField: function(o){
		        		return o.credit_adjustment_amount != "" ? parseFloat(o.credit_adjustment_amount).toFixed(2) : "";
		      	    },
		        	sort : encodeURI("(i.invoice_credit+i.invoice_adjustment)").replace(/\+/g,'%2B'),
		        	filtration : {name:"(i.invoice_credit+i.invoice_adjustment)",alias:"Invoice Credit and Adjustment Amount"},
		        	className:"aright"
		        },{
		        	title : "Payment Amount",
		        	dataField: function(o){
		        		return o.payment_amount != "" ? parseFloat(o.payment_amount).toFixed(2) : "";
		      	    },
		        	sort : "i.payment_amount",
		        	filtration : {name:"i.payment_amount",alias:"Invoice Payment"},
		        	className:"aright"
		        },{
		        	title : "Invoice Status",
		        	dataField: "invoice_status_name" ,
		        	sort : "ins.invoice_status_name",
		        	filtration : {name:"ins.invoice_status_name",alias:"Invoice Status"}
		        }
		        ]
	});
	
	allChargePage.addTotalSuccessEvent(function(data){
		if(data.totalResultNo < 0 || data.error){
			document.getElementById('saveExcel_tab0').style.display = "none";
		}else{
			document.getElementById('saveExcel_tab0').style.display = "block";
		}
	});
	
	filter1 = new SANINCO.Filter();
    filter1.addEditeEvent(function(){allChargePage.start();});
    filter1.add('i.invoice_number', 'String');
	filter1.add('i.invoice_date', 'date');
	filter1.add('i.invoice_mrc', 'number');
	filter1.add('i.invoice_occ', 'number');
	filter1.add('i.invoice_usage', 'number');
	filter1.add('i.invoice_late_payment_charge', 'number');
	filter1.add('(i.invoice_credit+i.invoice_adjustment)', 'number');
	filter1.add('i.payment_amount', 'number');
	filter1.add('ins.invoice_status_name', 'String');
    allChargePage.setFilter(filter1);
	
	allChargePage.voParam = {
			vendorId : param.vendorId,
			banId : param.banId
	};
	allChargePage.start();
}

function showItemTab(tabNum,isParam){
	
	YAHOO.ccm.container.recurringChargeDetail.hide();
	if(isParam){
		param = {
				vendorId : $("#VL_vendorMainList_1_hidden").val(),
				banId : $("#BL_banMainList_1_hidden").val()
		}
	}

	for(var i=0;i<7;i++){
		if(i == tabNum){
			$("#a"+i).addClass('selected'); 
			$("#tab"+i).css('display', 'block'); 
			
			switch(i){
			case 0:
				searchAllChargeTable();
				break;
			case 1:
				searchrecurringChargeTable();
				break;
			case 2:
				searchNonRecurringChargeTable();
				break;
			case 3:
				searchUsageChargeTable();
				break;
			case 4:
				searchLPCChargeTable();
				break;
			case 5:
				searchCreditAndAdjustmentTable();
				break;
			case 6:
				searchPaymentTable();
				break;
			}
			
		}else{
			$("#a"+i).removeClass('selected'); 
			$("#tab"+i).css('display', 'none'); 
		}
	}
}

function initDescriptionWindow(){
    YAHOO.ccm.container.recurringChargeDetail = new YAHOO.widget.Dialog("recurringChargeDetail", {
        width: "70em",
        fixedcenter: true,
        visible: false,
        constraintoviewport: true
    });
    YAHOO.ccm.container.recurringChargeDetail.render();
    
}

function popUpDescriptionWindow(costItemId){
//	$("#recurringChargeDetailTitle").html("Description");
	descriptionPage = new SANINCO.Page("recurringChargeDetailTabel","descriptionPage",{
		sortingField : "i.invoice_number",
		recordText: '', 
		sortingDirection : "asc",
		vo : "svvo",
		paginationDiv: "recurringChargeDetail_pageTable",
		paginationCustomDiv:"recurringChargeDetail_pageTable_download",
		recPerArray : [5,10,15,20,30],
		dataUri : "doSearchSortProposalTable.action",
		totalPageUri: "doSearchSortProposalTotalPageNo.action",
		cols : [
		        {
		        	title : "Invoice Number",
		        	dataField: "invoice_number" ,
		        	sort : "i.invoice_number"
		        },{
		        	title : "Invoice Date",
		        	dataField: "invoice_date",
		        	sort : "i.invoice_date"
		        },{
		        	title : "Description",
		        	dataField: "item_name" ,
		        	sort : "p.item_name"
		        },{
		        	title : "Invoice Amount",
		        	dataField: function(o){
			    		return o.item_amount != "" ? parseFloat(o.item_amount).toFixed(2) : "";
			      	},
		        	sort : "ii.item_amount",
			        className:"aright"
		        },{
		        	title : "Payment Amount",
		        	dataField: function(o){
			    		return o.payment_amount != "" ? parseFloat(o.payment_amount).toFixed(2) : "";
			      	},
		        	sort : "p.payment_amount",
			        className:"aright"
		        },{
		        	title : "Dispute Amount",
		        	dataField: function(o){
			    		return o.dispute_amount != "" ? parseFloat(o.dispute_amount).toFixed(2) : "";
			      	},
		        	sort : "p.dispute_amount",
			        className:"aright"
		        }
		        ]
	});
	
	descriptionPage.addTotalSuccessEvent(function(data){
		if(data.totalResultNo < 0 || data.error){
			document.getElementById('saveExcel_tab1_detail').style.display = "none";
		}else{
			// ��ݷ��ؼ�¼��������pop����С.
			var recPerPage = descriptionPage.recPerPage;
			if(recPerPage > 10 && data.totalResultNo > 15){
				$('#recurringChargeDetailTabel').parent().height("40em");
			}else{
				$('#recurringChargeDetailTabel').parent().height("");
			}
			YAHOO.ccm.container.recurringChargeDetail.show();
			document.getElementById('saveExcel_tab1_detail').style.display = "block";
			
		}
	});
	
	
	descriptionPage.voParam = {
			costItemId : costItemId,
			vendorId : param.vendorId,
			banId : param.banId,
			itemTypeId : "3"
	};
	descriptionPage.start();
}

function searchNonRecurringChargeTable(){
	nonRecurringChargePage = new SANINCO.Page("nonRecurringChargeTable","nonRecurringChargePage",{
		sortingField : "i.invoice_number",
		recordText: '', 
		sortingDirection : "asc",
		vo : "svvo",
		paginationDiv: "nonRecurringCharge_pageTable",
		paginationCustomDiv: "nonRecurringCharge_pageTable_download",
		recPerArray : [5,10,15,20,30],
		dataUri : "doSearchSortProposalTable.action",
		totalPageUri: "doSearchSortProposalTotalPageNo.action",
		cols : [
		        {
		        	title : "Invoice Number",
		        	dataField: "invoice_number" ,
		        	sort : "i.invoice_number",
		        	filtration : {name:"i.invoice_number",alias:"Invoice Number"}
		        },{
		        	title : "Invoice Date",
		        	dataField: "invoice_date",
		        	sort : "i.invoice_date",
		        	filtration : {name:"i.invoice_date",alias:"Invoice Date"}
		        },{
		        	title : "Description",
		        	dataField: "item_name" ,
		        	sort : "p.item_name",
		        	filtration : {name:"p.item_name",alias:"Description"}
		        },{
		        	title : "Invoice Amount",
		        	dataField: function(o){
			    		return o.item_amount != "" ? parseFloat(o.item_amount).toFixed(2) : "";
			      	},
		        	sort : "ii.item_amount",
		        	filtration : {name:"ii.item_amount",alias:"Invoice Amount"},
			        className:"aright"
		        },{
		        	title : "Payment Amount",
		        	dataField: function(o){
			    		return o.payment_amount != "" ? parseFloat(o.payment_amount).toFixed(2) : "";
			      	},
		        	sort : "p.payment_amount",
		        	filtration : {name:"p.payment_amount",alias:"Payment Amount"},
			        className:"aright"
		        },{
		        	title : "Dispute Amount",
		        	dataField: function(o){
			    		return o.dispute_amount != "" ? parseFloat(o.dispute_amount).toFixed(2) : "";
			      	},
		        	sort : "p.dispute_amount",
		        	filtration : {name:"p.dispute_amount",alias:"Dispute Amount"},
			        className:"aright"
		        }
		        ]
	});
	
	nonRecurringChargePage.addTotalSuccessEvent(function(data){
		if(data.totalResultNo < 0 || data.error){
			document.getElementById('saveExcel_tab2').style.display = "none";
		}else{
			document.getElementById('saveExcel_tab2').style.display = "block";
		}
	});
	
	nonRecurringChargePage.voParam = {
			vendorId : param.vendorId,
			banId : param.banId,
			itemTypeId : "5"
	};
	filter1 = new SANINCO.Filter();
    filter1.addEditeEvent(function(){nonRecurringChargePage.start();});
    filter1.add('i.invoice_number', 'String');
    filter1.add('i.invoice_date', 'date');
	filter1.add('p.item_name', 'String');
	filter1.add('ii.item_amount', 'String');
	filter1.add('p.payment_amount', 'String');
	filter1.add('p.dispute_amount', 'String');
	
	nonRecurringChargePage.setFilter(filter1);
	nonRecurringChargePage.start();
}

function searchUsageChargeTable(){
	usageChargePage = new SANINCO.Page("usageChargeTable","usageChargePage",{
		sortingField : "i.invoice_number",
		recordText: '', 
		sortingDirection : "asc",
		vo : "svvo",
		paginationDiv: "usageCharge_pageTable",
		paginationCustomDiv: "usageCharge_pageTable_download",
		recPerArray : [5,10,15,20,30],
		dataUri : "doSearchSortProposalTable.action",
		totalPageUri: "doSearchSortProposalTotalPageNo.action",
		cols : [
		        {
		        	title : "Invoice Number",
		        	dataField: "invoice_number" ,
		        	sort : "i.invoice_number",
		        	filtration : {name:"i.invoice_number",alias:"Invoice Number"}
		        },{
		        	title : "Invoice Date",
		        	dataField: "invoice_date",
		        	sort : "i.invoice_date",
		        	filtration : {name:"i.invoice_date",alias:"Invoice Date"}
		        },{
		        	title : "Description",
		        	dataField: "item_name" ,
		        	sort : "p.item_name",
		        	filtration : {name:"p.item_name",alias:"Description"}
		        },{
		        	title : "Invoice Amount",
		        	dataField: function(o){
			    		return o.item_amount != "" ? parseFloat(o.item_amount).toFixed(2) : "";
			      	},
		        	sort : "ii.item_amount",
		        	filtration : {name:"ii.item_amount",alias:"Invoice Amount"},
			        className:"aright"
		        },{
		        	title : "Payment Amount",
		        	dataField: function(o){
			    		return o.payment_amount != "" ? parseFloat(o.payment_amount).toFixed(2) : "";
			      	},
		        	sort : "p.payment_amount",
		        	filtration : {name:"p.payment_amount",alias:"Payment Amount"},
			        className:"aright"
		        },{
		        	title : "Dispute Amount",
		        	dataField: function(o){
			    		return o.dispute_amount != "" ? parseFloat(o.dispute_amount).toFixed(2) : "";
			      	},
		        	sort : "p.dispute_amount",
		        	filtration : {name:"p.dispute_amount",alias:"Dispute Amount"},
			        className:"aright"
		        }
		        ]
	});
	
	usageChargePage.addTotalSuccessEvent(function(data){
		if(data.totalResultNo < 0 || data.error){
			document.getElementById('saveExcel_tab3').style.display = "none";
		}else{
			document.getElementById('saveExcel_tab3').style.display = "block";
		}
	});
	
	usageChargePage.voParam = {
			vendorId : param.vendorId,
			banId : param.banId,
			itemTypeId : "4"
	};
	filter1 = new SANINCO.Filter();
    filter1.addEditeEvent(function(){usageChargePage.start();});
    filter1.add('i.invoice_number', 'String');
    filter1.add('i.invoice_date', 'date');
	filter1.add('p.item_name', 'String');
	filter1.add('ii.item_amount', 'String');
	filter1.add('p.payment_amount', 'String');
	filter1.add('p.dispute_amount', 'String');
	usageChargePage.setFilter(filter1);
	
	usageChargePage.start();
}

function searchLPCChargeTable(){
	LPCChargePage = new SANINCO.Page("LPCChargeTable","LPCChargePage",{
		sortingField : "i.invoice_number",
		recordText: '', 
		sortingDirection : "asc",
		vo : "svvo",
		paginationDiv: "LPCCharge_pageTable",
		paginationCustomDiv: "LPCCharge_pageTable_download",
		recPerArray : [5,10,15,20,30],
		dataUri : "doSearchSortProposalTable.action",
		totalPageUri: "doSearchSortProposalTotalPageNo.action",
		cols : [
		        {
		        	title : "Invoice Number",
		        	dataField: "invoice_number" ,
		        	sort : "i.invoice_number",
		        	filtration : {name:"i.invoice_number",alias:"Invoice Number"}
		        },{
		        	title : "Invoice Date",
		        	dataField: "invoice_date",
		        	sort : "i.invoice_date",
		        	filtration : {name:"i.invoice_date",alias:"Invoice Date"}
		        },{
		        	title : "Description",
		        	dataField: "item_name" ,
		        	sort : "p.item_name",
		        	filtration : {name:"p.item_name",alias:"Description"}
		        },{
		        	title : "Invoice Amount",
		        	dataField: function(o){
			    		return o.item_amount != "" ? parseFloat(o.item_amount).toFixed(2) : "";
			      	},
		        	sort : "ii.item_amount",
		        	filtration : {name:"ii.item_amount",alias:"Invoice Amount"},
			        className:"aright"
		        },{
		        	title : "Payment Amount",
		        	dataField: function(o){
			    		return o.payment_amount != "" ? parseFloat(o.payment_amount).toFixed(2) : "";
			      	},
		        	sort : "p.payment_amount",
		        	filtration : {name:"p.payment_amount",alias:"Payment Amount"},
			        className:"aright"
		        },{
		        	title : "Dispute Amount",
		        	dataField: function(o){
			    		return o.dispute_amount != "" ? parseFloat(o.dispute_amount).toFixed(2) : "";
			      	},
		        	sort : "p.dispute_amount",
		        	filtration : {name:"p.dispute_amount",alias:"Dispute Amount"},
			        className:"aright"
		        }
		        ]
	});
	
	LPCChargePage.addTotalSuccessEvent(function(data){
		if(data.totalResultNo < 0 || data.error){
			document.getElementById('saveExcel_tab4').style.display = "none";
		}else{
			document.getElementById('saveExcel_tab4').style.display = "block";
		}
	});
	
	LPCChargePage.voParam = {
			vendorId : param.vendorId,
			banId : param.banId,
			itemTypeId : "2"
	};
	filter1 = new SANINCO.Filter();
    filter1.addEditeEvent(function(){LPCChargePage.start();});
    filter1.add('i.invoice_number', 'String');
    filter1.add('i.invoice_date', 'date');
	filter1.add('p.item_name', 'String');
	filter1.add('ii.item_amount', 'String');
	filter1.add('p.payment_amount', 'String');
	filter1.add('p.dispute_amount', 'String');
	LPCChargePage.setFilter(filter1);
	LPCChargePage.start();
}
function searchCreditAndAdjustmentTable(){
	CreditAndAdjustmentPage = new SANINCO.Page("CreditAndAdjustmentTable","CreditAndAdjustmentPage",{
		sortingField : "i.invoice_number",
		recordText: '', 
		sortingDirection : "asc",
		vo : "svvo",
		paginationDiv: "CreditAndAdjustment_pageTable",
		paginationCustomDiv: "CreditAndAdjustment_pageTable_download",
		recPerArray : [5,10,15,20,30],
		dataUri : "doSearchSortProposalTable.action",
		totalPageUri: "doSearchSortProposalTotalPageNo.action",
		cols : [
		        {
		        	title : "Invoice Number",
		        	dataField: "invoice_number" ,
		        	sort : "i.invoice_number",
		        	filtration : {name:"i.invoice_number",alias:"Invoice Number"}
		        },{
		        	title : "Invoice Date",
		        	dataField: "invoice_date",
		        	sort : "i.invoice_date",
		        	filtration : {name:"i.invoice_date",alias:"Invoice Date"}
		        },{
		        	title : "Description",
		        	dataField: "item_name" ,
		        	sort : "p.item_name",
		        	filtration : {name:"p.item_name",alias:"Description"}
		        },{
		        	title : "Invoice Amount",
		        	dataField: function(o){
			    		return o.item_amount != "" ? parseFloat(o.item_amount).toFixed(2) : "";
			      	},
		        	sort : "ii.item_amount",
		        	filtration : {name:"ii.item_amount",alias:"Invoice Amount"},
			        className:"aright"
		        },{
		        	title : "Payment Amount",
		        	dataField: function(o){
			    		return o.payment_amount != "" ? parseFloat(o.payment_amount).toFixed(2) : "";
			      	},
		        	sort : "p.payment_amount",
		        	filtration : {name:"p.payment_amount",alias:"Payment Amount"},
			        className:"aright"
		        },{
		        	title : "Dispute Amount",
		        	dataField: function(o){
			    		return o.dispute_amount != "" ? parseFloat(o.dispute_amount).toFixed(2) : "";
			      	},
		        	sort : "p.dispute_amount",
		        	filtration : {name:"p.dispute_amount",alias:"Dispute Amount"},
			        className:"aright"
		        }
		        ]
	});
	
	CreditAndAdjustmentPage.addTotalSuccessEvent(function(data){
		if(data.totalResultNo < 0 || data.error){
			document.getElementById('saveExcel_tab5').style.display = "none";
		}else{
			document.getElementById('saveExcel_tab5').style.display = "block";
		}
	});
	
	CreditAndAdjustmentPage.voParam = {
			vendorId : param.vendorId,
			banId : param.banId,
			itemTypeId : "6,7"
	};
	filter1 = new SANINCO.Filter();
    filter1.addEditeEvent(function(){CreditAndAdjustmentPage.start();});
    filter1.add('i.invoice_number', 'String');
    filter1.add('i.invoice_date', 'date');
	filter1.add('p.item_name', 'String');
	filter1.add('ii.item_amount', 'String');
	filter1.add('p.payment_amount', 'String');
	filter1.add('p.dispute_amount', 'String');
	CreditAndAdjustmentPage.setFilter(filter1);
	
	CreditAndAdjustmentPage.start();
}

function searchPaymentTable(){
	PaymentPage = new SANINCO.Page("PaymentTable","PaymentPage",{
		sortingField : "i.invoice_number",
		recordText: '', 
		sortingDirection : "asc",
		vo : "svvo",
		paginationDiv: "Payment_pageTable",
		paginationCustomDiv: "Payment_pageTable_download",
		recPerArray : [5,10,15,20,30],
		dataUri : "doSearchPaymentTable.action",
		totalPageUri: "doSearchPaymentTotalPageNo.action",
		cols : [
		        {
		        	title : "Invoice Number",
		        	dataField: "invoice_number" ,
		        	sort : "i.invoice_number",
		        	filtration : {name:"i.invoice_number",alias:"Invoice Number"}
		        },{
		        	title : "Invoice Date",
		        	dataField: "invoice_date",
		        	sort : "i.invoice_date",
		        	filtration : {name:"i.invoice_date",alias:"Invoice Date"}
		        },{
		        	title : "Payment Amount",
		        	dataField: function(o){
			    		return o.payment_amount != "" ? parseFloat(o.payment_amount).toFixed(2) : "";
			      	},
		        	sort : "i.payment_amount",
		        	filtration : {name:"i.payment_amount",alias:"Payment Amount"},
			        className:"aright"
		        },{
		        	title : "Reference Number",
		        	dataField: "payment_reference_code" ,
		        	sort : "p.payment_reference_code",
		        	filtration : {name:"p.payment_reference_code",alias:"Reference Number"}
		        }
		        ]
	});
	
	PaymentPage.addTotalSuccessEvent(function(data){
		if(data.totalResultNo < 0 || data.error){
			document.getElementById('saveExcel_tab6').style.display = "none";
		}else{
			document.getElementById('saveExcel_tab6').style.display = "block";
		}
	});
	
	PaymentPage.voParam = {
			vendorId : param.vendorId,
			banId : param.banId
	};
	filter1 = new SANINCO.Filter();
    filter1.addEditeEvent(function(){PaymentPage.start();});
    filter1.add('i.invoice_number', 'String');
    filter1.add('i.invoice_date', 'date');
	filter1.add('i.payment_amount', 'String');
	filter1.add('p.payment_reference_code', 'String');
	PaymentPage.setFilter(filter1);
	
	PaymentPage.start();
}

function saveExcel(grid,uri,excelName){
	if(!grid) return;
	showLoadingModalLayer();
	var titles = grid.getTitlesParam("titles");
	var paramStr = grid.paramStr;
	if(excelName != ""){
//		window.location.href = uri+"?" + titles + "&excelName=" + excelName + "&" + paramStr;
		windowOpen(uri+"?" + titles + "&excelName=" + excelName + "&" + paramStr);
	}else{
//		window.location.href = uri+"?"+ titles +"&"+ paramStr;
		windowOpen(uri+"?"+ titles +"&"+ paramStr);
	}
	hideLoadingModalLayer();
}