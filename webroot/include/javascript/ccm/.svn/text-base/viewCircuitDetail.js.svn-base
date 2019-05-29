var getSCOATotalPageNoUri = "doSearchSCOATotalPageNo.action";
var searchSCOAUri = "doSearchSCOAList.action";
var getCostTotalPageNoUri = "doSearchCostTotalPageNo.action";
var searchCostUri = "doSearchCostList.action";
var getTariffTotalPageNoUri = "doSearchTariffTotalPageNo.action";
var searchTariffUri = "doSearchTariffList.action";
var getLineItemMappingCount ="doSearchMappingPageNo.action";
var getLineItemMapping ="doSearchMappingList.action";

var pVendorCircuitId;
var tabView;
var scoaPage;
var costPage;
var tariffPage;
var agreementContractPage;
var LineItem;


YAHOO.util.Event.onDOMReady(function(){
	pVendorCircuitId = document.getElementById("vendorCircuitId").value;
	tabView = new YAHOO.widget.TabView("demo");
	$("#demo li a:eq(0)").click();
	tabView.selectTab(0);
	createSCOAPage();
	getHeight();
	showCircuitDetailWindow();
	$("#uploadTariffWindow").css("display", "block");
	YAHOO.ccm.container.uploadTariffWindow = new YAHOO.widget.Dialog("uploadTariffWindow", 
		{ width : "450px",
		  fixedcenter : true,
		  visible : false, 
		  modal : true,
		  constraintoviewport : true
		});
	YAHOO.ccm.container.uploadTariffWindow.render();
});

function createSCOAPage(){
	if (!scoaPage) {
		scoaPage = new SANINCO.Page("_SCOA_dataTable","scoaPage",{
		    sortingField : "",
		    sortingDirection : "",
			vo : "searchCircuitVO",
			pageTable : "block",
		    totalPageUri : getSCOATotalPageNoUri,
		    dataUri : searchSCOAUri,
			paginationDiv : "_SCOA_dataTable_page",
			recPerArray : [10,20,30,40,50,70,90,100],
		    cols : [
					{   title : "Charge Type",dataField: "chargeType", width: "15%", sort : "c.charge_type", filtration : {name:"c.charge_type",alias:"Charge Type"}
					},{ title : "Invoice Date",dataField: "invoiceDate", width: "15%", sort : "c.invoice_date", filtration : {name:"c.invoice_date",alias:"Invoice Date"}
				    },{ title : "Expected Amount",dataField: "expectedCost", width: "14%", sort : "c.expected_cost", filtration : {name:"c.expected_cost",alias:"Expected Amount"}
				    },{ title : "Company",dataField: "company", width: "19%", sort : "ac.company", filtration : {name:"ac.company",alias:"Company"}
				    },{ title : "Location",dataField: "location", width: "17%", sort : "ac.location", filtration : {name:"ac.location",alias:"Location"}
				    },{ title : "Department",dataField: "department", width: "17%", sort : "ac.department", filtration : {name:"ac.department",alias:"Department"}
				    },{ title : "Product",dataField: "product", width: "18%", sort : "ac.product", filtration : {name:"ac.product",alias:"Product"}
				    },{ title : "GL Account",dataField: "account", width: "18%", sort : "ac.account", filtration : {name:"ac.account",alias:"GL Account"}
				    },{ title : "Channel",dataField: "channel", width: "18%", sort : "ac.channel", filtration : {name:"ac.channel",alias:"Channel"}
				    }
				]
		});
			
		var filter = new SANINCO.Filter();
	    filter.addEditeEvent(function(){scoaPage.start();});
	  	filter.add('c.charge_type', 'String');
	  	filter.add('c.invoice_date', 'String');
		filter.add("c.expected_cost", 'String');
		filter.add("ac.company", 'String');
		filter.add("ac.location", 'String');
		filter.add("ac.department", 'String');
		filter.add("ac.product", 'String');
		filter.add("ac.account", 'String');
		filter.add("ac.channel", 'String');
	  	scoaPage.setFilter(filter);
	  	scoaPage.addTotalSuccessEvent(function(data){
			// document.getElementById('_SCOA').style.display = "none";
			// document.getElementById('_SCOA').style.display = "block";
		});
	}
	scoaPage.myParam = "searchCircuitVO.vendorCircuitId=" + pVendorCircuitId;
	scoaPage.filter.clearAll();
	scoaPage.start();
}
function createCostPage(){
	if (!costPage) {
		costPage = new SANINCO.Page("_cost_dataTable","costPage",{
		    sortingField : "",
		    sortingDirection : "",
			vo : "searchCircuitVO",
			pageTable : "block",
		    totalPageUri : getCostTotalPageNoUri,
		    dataUri : searchCostUri,
			paginationDiv : "_cost_dataTable_page",
			recPerArray : [10,20,30,40,50,70,90,100],
		    cols : [
					{   title : "Invoice Number",dataField: "invoiceNumber",sort : "i.invoice_number", filtration : {name:"i.invoice_number",alias:"Invoice Numbe"}
				    },{ title : "Invoice Date",dataField: "invoiceDate",sort : "i.invoice_date", filtration : {name:"i.invoice_date",alias:"Invoice Date"}
				    },{ title : "Charge Type",dataField: "chargeType",sort : "it.item_type_summary", filtration : {name:"it.item_type_summary",alias:"Charge Type"}
				    },{ title : "Item Amount",dataField: "itemAmount",sort : "sum(ii.item_amount)"
				    }
				]
		});
			
		var filter = new SANINCO.Filter();
	    filter.addEditeEvent(function(){costPage.start();});
	  	filter.add('i.invoice_number', 'String');
		filter.add("i.invoice_date", 'date');
		filter.add("it.item_type_summary", 'String');
	  	costPage.setFilter(filter);
	  	costPage.addTotalSuccessEvent(function(data){
			// document.getElementById('_SCOA').style.display = "none";
			// document.getElementById('_SCOA').style.display = "block";
		});
	}
	costPage.myParam = "searchCircuitVO.vendorCircuitId=" + pVendorCircuitId;
	costPage.filter.clearAll();
	costPage.start();
}
function closeCircuitDetail(){
	YAHOO.ccm.container.circuitDetail.hide();
}
function Download(name,path){
	var df = document.getElementById("Download");
	df.fileName.value = name;
	df.filePath.value = path;
	df.submit();
	
}
function createLineItemAndAttachedFile(){
	createLineItem();
	createTariffPage();	
	//createAgreementContract();
}
function showCircuitDetailWindow(){
	YAHOO.util.Dom.removeClass("Tariff_or_Contract_Detail", "update-ReportAdminTags");
    YAHOO.ccm.container.circuitDetail = new YAHOO.widget.Dialog("Tariff_or_Contract_Detail", {
        fixedcenter: true,
        visible: false,
        constraintoviewport: true
    });
    
    YAHOO.ccm.container.circuitDetail.render();
}
function showTariffDetail(e,tfID){
	var callback = {
		success: function(o){
            hideLoadingModalLayer();

			var data = o.responseText;
			$("#Tariff_or_Contract_DetailPanel").html(data);
            YAHOO.ccm.container.circuitDetail.show();	
       },
		failure:showError
	};
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest("POST","doSearchTariff.action" + "?tfID=" + tfID, callback);
}
function showContractDetail(e,tfID){
	var callback = {
		success: function(o){
            hideLoadingModalLayer();

			var data = o.responseText;
			$("#Tariff_or_Contract_DetailPanel").html(data);
            YAHOO.ccm.container.circuitDetail.show();	
        },
		failure:showError
	};
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest("POST","doSearchContract.action" + "?tfID=" + tfID, callback);
}
function createLineItem(){
	if (!LineItem) {
		LineItem = new SANINCO.Page("_Line_Item","LineItem",{
			sortingField : "",
		    sortingDirection : "",
			vo : "searchCircuitVO",
			pageTable : "block",
		    totalPageUri : getLineItemMappingCount,
		    dataUri : getLineItemMapping,
			paginationDiv : "_Line_Item_page",
			recPerArray : [10,20,30,40,50,70,90,100],
		    cols : [
					{   title : "Tariff_or_Contract",dataField: function(o){ if(o.tc_flag=="T"){return "<a href=\"javascript:showTariffDetail(event,"+o.tariff_id+")\">Tariff</a>";}else if(o.tc_flag=="C"){return"<a href=\"javascript:showContractDetail(event,"+o.contract_id+")\">Contract</a>";}else{return "None"} },filtration : {name:"tc_flag",alias:"Tariff_or_Contract<br>Tariff=T<br>Contract=C<br>None=N"}
				    },{ title : "Item Number",dataField: "item_number",sort : "item_number", filtration : {name:"item_number",alias:"Item Number"}
				    },{ title : "Agreement Number",dataField: "agreement_number",sort : "agreement_number", filtration : {name:"agreement_number",alias:"Agreement Number"}
				    },{ title : "Description",dataField: "description",sort : "description", filtration : {name:"description",alias:"Description"}
				    },{ title : "Description Source",dataField: "description_source",sort : "description_source",filtration : {name:"description_source",alias:"Description Source"}
				    }
				]
		});
			
		var filter = new SANINCO.Filter();
	    filter.addEditeEvent(function(){LineItem.start();});
	    filter.add('tc_flag', 'String');
	  	filter.add('item_number', 'String');
		filter.add("agreement_number", 'String');
		filter.add("description_source", 'String');
		LineItem.setFilter(filter);
		LineItem.addTotalSuccessEvent(function(data){
			// document.getElementById('_SCOA').style.display = "none";
			// document.getElementById('_SCOA').style.display = "block";
		});
	}
	LineItem.myParam = "searchCircuitVO.vendorCircuitId=" + pVendorCircuitId;
	LineItem.filter.clearAll();
	LineItem.start();
}
function createTariffPage(){
	if (!tariffPage) {
		tariffPage = new SANINCO.Page("_tariff_dataTable","tariffPage",{
		    sortingField : "",
		    sortingDirection : "",
			vo : "searchCircuitVO",
			pageTable : "block",
		    totalPageUri : getTariffTotalPageNoUri,
		    dataUri : searchTariffUri,
			paginationDiv : "_tariff_dataTable_page",
			recPerArray : [10,20,30,40,50,70,90,100],
		    cols : [
		      {
		          title : "File Name",
		          dataField: "tariff_name" ,
		          sort : "tariff_name",
		          width: "25%", 
		          filtration : {name:"tariff_name",alias:"File Name"}
		      },{
		          title : "Upload Date",
		          dataField: "created_timestamp",
		          sort : "created_timestamp",
		          width: "17%", 
		          filtration : {name:"created_timestamp",alias:"Upload Date"}
		      },{
		          title : "Notes",
		          dataField: "notes" ,
		          sort : "notes",
		          width: "42%", 
		          filtration : {name:"notes",alias:"Notes"}
		      },{
		          title : "Delete",
		          dataField: function(obj){
	                return "<img  src=\"include/images/delete.png\"  style=\"cursor: pointer;\"  onClick=\"deleteTariff('" + obj.id + "');\">";
	              },
		          width: "5%",
		          textAlign: "center"
		      },{
		          title : "File",
		          dataField:function(obj){
	               // return "<img  src=\"include/images/download1.gif\"  style=\"cursor: pointer;\"  onClick=\"downloadFile(" + "'" + obj.tariff_name + "','" + obj.tariff_path + "'" + ");\">";
	                return "<input value=\"Download\" type=\"button\" style=\"cursor: pointer;\"  onClick=\"downloadFile(" + "'" + obj.tariff_name + "','" + obj.tariff_path + "'" + ");\">";
	              },
		          width: "11%",
		          textAlign: "center"
		      }
	      ]
		});
			
		var filter = new SANINCO.Filter();
	    filter.addEditeEvent(function(){tariffPage.start();});
	  	filter.add('tariff_name', 'String');
		filter.add("created_timestamp", 'date');
	  	filter.add('notes', 'String');
	  	tariffPage.setFilter(filter);
	  	tariffPage.addTotalSuccessEvent(function(data){
			// document.getElementById('_SCOA').style.display = "none";
			// document.getElementById('_SCOA').style.display = "block";
		});
	}
	tariffPage.myParam = "searchCircuitVO.vendorCircuitId=" + pVendorCircuitId;
	tariffPage.filter.clearAll();
	tariffPage.start();
}
function createAgreementContract(){
	if (!agreementContractPage) {
		agreementContractPage = new SANINCO.Page("_agreement_contract","agreementContractPage",{
			sortingField : "",
			sortingDirection : "",
			vo : "searchCircuitVO",
			pageTable : "block",
			totalPageUri : getTariffTotalPageNoUri,
			dataUri : searchTariffUri,
			paginationDiv : "_agreement_contract_page",
			recPerArray : [10,20,30,40,50,70,90,100],
			cols : [
			        {
			        	title : "Term (months)",
			        	dataField: "tariff_name" ,
			        	sort : "tariff_name",
			        	width: "25%", 
			        	filtration : {name:"tariff_name",alias:"Tariff Name"}
			        },{
			        	title : "Auto Renewal",
			        	dataField: "created_timestamp",
			        	sort : "created_timestamp",
			        	width: "17%", 
			        	filtration : {name:"created_timestamp",alias:"Upload Date"}
			        },{
			        	title : "MSA Agreement No.",
			        	dataField: "notes" ,
			        	sort : "notes",
			        	width: "42%", 
			        	filtration : {name:"notes",alias:"Notes"}
			        },{
			        	title : "ETF",
			        	dataField: "notes" ,
			        	sort : "notes",
			        	width: "42%", 
			        	filtration : {name:"notes",alias:"Notes"},
			        	textAlign: "center"
			        }
			        ]
		});
		
		var filter = new SANINCO.Filter();
		filter.addEditeEvent(function(){agreementContractPage.start();});
		filter.add('tariff_name', 'String');
		filter.add("created_timestamp", 'date');
		filter.add('notes', 'String');
		agreementContractPage.setFilter(filter);
		agreementContractPage.addTotalSuccessEvent(function(data){
			// document.getElementById('_SCOA').style.display = "none";
			// document.getElementById('_SCOA').style.display = "block";
		});
	}
	agreementContractPage.myParam = "searchCircuitVO.vendorCircuitId=-1000";
	agreementContractPage.filter.clearAll();
	agreementContractPage.start();
}
var auf=3;
function addUploadForm(){
	auf++;
	var string = "<div id =\"ContractFile"+auf+"\" style=\"padding:3px 0 0 0;height: 19px;\" ><div style=\"float:left;\">"		
                  +"<input style=\"height:19px;width:215px;\" type='text' id=\"_upload_text_attachment_0"+auf+"\" disabled=\"disabled\" class=\"upload_text\">" 
	               +"</div><div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"		
	              + "<input onchange=\"document.getElementById('_upload_text_attachment_0"+auf+"').value=this.value;\" class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
	              + "<input style=\"height:19px;width:65px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/></span></div>"	   						
                  + "<div class=\"approveicon\" onclick=\"deletefile('ContractFile"+auf+"');\"></div><div class=\"clear\" ></div></div>";
	

	$("#addFile").append(string);
}

function submitUploadForm() {
	if(inputValue()){
	if (validateUploadForm()) {
		var uf = document.getElementById("uploadForm");
		uf.submit();
		YAHOO.ccm.container.uploadTariffWindow.hide();
	}
	}
}

function cancelUploadForm() {
	YAHOO.ccm.container.uploadTariffWindow.hide();
}

function validateUploadForm() {
	if ($("#_upload_text_attachment_01").val() == "" &&
		$("#_upload_text_attachment_02").val() == "" &&
		$("#_upload_text_attachment_03").val() == "" &&
		$("#_upload_text_attachment_04").val() == "" &&
		$("#_upload_text_attachment_05").val() == "") {
		
		alert("Please at least upload a file!");
		return false;
	}
	return true;
}

function downloadFile(name,path){
	var df = document.getElementById("downloadForm");
	df.filePath.value = path;
	df.fileName.value = name;
	df.submit();
}

function deleteTariff(tariffLinkId) {
	if(!confirm("Are you sure you want to delete this attachment file?")){return false;}
	var callback = {
		success: function(o){
			tariffPage.reload();
		},
		failure:showError
	};
	var data = "tariffLink.id="+tariffLinkId;
	YAHOO.util.Connect.asyncRequest("POST","deleteTariffOfVendorCircuit.action", callback, data);
}

function popupUploadTariffWindow() {
	// clean upload form
	$(".upload_text").val("");
	$("#uploadNotes").val("");
	clearFormUploadFiles("uploadForm");
	YAHOO.ccm.container.uploadTariffWindow.show();
}

function uploadCallbackSuccess() {
	tariffPage.reload();
}

function uploadCallbackFailed() {
	alert("upload attachment failed!");
}

function downloadScoaCsv(){
	showLoadingModalLayer();
	var titles = "titles=Charge Type&titles=Expected Amount&titles=Company&titles=Location&titles=Department&titles=Product&titles=GL Acount&titles=Channel";
	var uri =  "doDownloadScoaCsv.action?"+ titles +"&searchCircuitVO.vendorCircuitId=" + pVendorCircuitId;
	windowOpen(uri);
	hideLoadingModalLayer();
}

function downloadCostCsv(){
	showLoadingModalLayer();
	var titles = "titles=Invoice Number&titles=Invoice Date&titles=Charge Type&titles=Item Amount";
	var uri =  "doDownloadCostCsv.action?"+ titles +"&searchCircuitVO.vendorCircuitId=" + pVendorCircuitId;
	windowOpen(uri);
	hideLoadingModalLayer();
}

function downloadTariffCsv(){
	showLoadingModalLayer();
	var titles = "titles=Tariff Name&titles=Upload Date&titles=Notes";
	var uri =  "doDownloadTariffCsv.action?"+ titles +"&searchCircuitVO.vendorCircuitId=" + pVendorCircuitId;
	windowOpen(uri);
	hideLoadingModalLayer();
}
function inputValue(){
	var els =document.getElementsByName("uploads");
	for(var i=0;i<els.length;i++){
		if(els[i].value!=""){
			return true;
		}
	}
	alert("Please select upload file")
	return false;
	
}
function getHeight() {
	var height1 = document.getElementById("ShowHeightID").offsetHeight;
	var height2 = document.getElementById("showHeightID2").offsetHeight;
	document.getElementById("newHeight").style.height=height1-height2-50; 
	document.getElementById("showHeightIDV").style.height=height1-30; 
}

