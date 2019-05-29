var getInvoiceTotalPageNoUri = "doGetInvoiceSearchTotalPageNo.action";
var searchInvoiceUri = "doSearchInvoice.action";
var saveExcelBySearchInvoiceUri = "saveExcelBySearchInvoice.action";
var searchInvoiceLabel = "doSearchInvoiceLabel.action"

//Change the state of the input box and color
function clearDisabled(id,number){
	if ($('#'+ id)[0].checked) {
		$(".Clear" + number).css("background","").attr("disabled","").val("");
	}else{
		$(".Clear" + number).css("background","#ccc").attr("disabled","disabled").val("");;
	}
}

//Remove the check box and input box associated
function clearCheckbox(){
	$(".ClearCheckbox").removeAttr("checked");
	$(".Date").css("background","#ccc").attr("disabled","disabled").val("");
}

function composePostDataFromUi(){
	var postData = "searchInvoiceVO.historicalInvoice=" + $('#__historical_invoice')[0].checked;
	
	if(form0_searchInvoiceVO_vendorId.getValue()!="all"){
		postData += ("&searchInvoiceVO.vendorId="+form0_searchInvoiceVO_vendorId.getValue()); 
		if($('#form0_searchInvoiceVO_banId').val()!="all") postData += ("&searchInvoiceVO.banId="+$('#form0_searchInvoiceVO_banId').val());
	}else{
		if(form0_searchInvoiceVO_banId.getValue()!="all") postData += ("&searchInvoiceVO.banId="+form0_searchInvoiceVO_banId.getValue()); 
	}
	if($('#form0_searchInvoiceVO_lineOfBusiness').val()!="all") postData += ("&searchInvoiceVO.lineOfBusiness="+$('#form0_searchInvoiceVO_lineOfBusiness').val());
	if(Math.floor(trim($('#form0_searchInvoiceVO_daysInStatus').val()))!="") postData += ("&searchInvoiceVO.daysInStatus="+Math.floor(trim($('#form0_searchInvoiceVO_daysInStatus').val())));
	if($('#form0_searchInvoiceVO_invoiceNumber').val()!= "") postData += ("&searchInvoiceVO.invoiceNumber="+ccmEscape($('#form0_searchInvoiceVO_invoiceNumber').val()));
	if($('#form0_searchInvoiceVO_paymentReference').val()!="") postData += ("&searchInvoiceVO.paymentReference="+ccmEscape($('#form0_searchInvoiceVO_paymentReference').val()));
	
	if($('#form0_searchInvoiceVO_analystId').val()!="all") postData += ("&searchInvoiceVO.analystId=" + $('#form0_searchInvoiceVO_analystId').val());
	if($('#form0_searchInvoiceVO_paymentStatusId').val()!="all") postData += ("&searchInvoiceVO.paymentStatusId=" + $('#form0_searchInvoiceVO_paymentStatusId').val());
	if($('#form0_searchInvoiceVO_statusId').val()!="all") postData += ("&searchInvoiceVO.statusId=" + $('#form0_searchInvoiceVO_statusId').val());
	
	if($('#searchInvoiceVO_invoiceDateStartsOn').val()!="") postData += ("&searchInvoiceVO.invoiceDateStartsOn=" + $('#searchInvoiceVO_invoiceDateStartsOn').val());
	if($('#searchInvoiceVO_invoiceDateEndsOn').val()!="") postData += ("&searchInvoiceVO.invoiceDateEndsOn=" + $('#searchInvoiceVO_invoiceDateEndsOn').val());
	if(trim($('#form0_searchInvoiceVO_invoiceDateWiPastNumOfDays').val())!="") postData += ("&searchInvoiceVO.invoiceDateWiPastNumOfDays=" + trim($('#form0_searchInvoiceVO_invoiceDateWiPastNumOfDays').val()));
	
	if($('#searchInvoiceVO_invoiceDueStartsOn').val()!="") postData += ("&searchInvoiceVO.invoiceDueStartsOn=" + $('#searchInvoiceVO_invoiceDueStartsOn').val());
	if($('#searchInvoiceVO_invoiceDueEndsOn').val()!="") postData += ("&searchInvoiceVO.invoiceDueEndsOn=" + $('#searchInvoiceVO_invoiceDueEndsOn').val());
	if(trim($('#form0_searchInvoiceVO_invoiceDueWiPastNumOfDays').val())!="") postData += ("&searchInvoiceVO.invoiceDueWiPastNumOfDays=" + trim($('#form0_searchInvoiceVO_invoiceDueWiPastNumOfDays').val()));
	if(trim($('#form0_searchInvoiceVO_invoiceDueWiNextNumOfDays').val())!="") postData += ("&searchInvoiceVO.invoiceDueWiNextNumOfDays=" + trim($('#form0_searchInvoiceVO_invoiceDueWiNextNumOfDays').val()));
	
	if($('#searchInvoiceVO_paymentDateStartsOn').val()!="") postData += ("&searchInvoiceVO.paymentDateStartsOn=" + $('#searchInvoiceVO_paymentDateStartsOn').val());
	if($('#searchInvoiceVO_paymentEndsOn').val()!="") postData += ("&searchInvoiceVO.paymentDateEndsOn=" + $('#searchInvoiceVO_paymentEndsOn').val());
	if(trim($('#form0_searchInvoiceVO_paymentDateWiPastNumOfDays').val())!="") postData += ("&searchInvoiceVO.paymentDateWiPastNumOfDays=" + trim($('#form0_searchInvoiceVO_paymentDateWiPastNumOfDays').val()));
	
	if(filter1.getCause()!="") postData += ("&searchInvoiceVO.filter=" + filter1.getCause());
	return postData;
}

//modified by xinyu.Liu on 2010.7.27 for CCMUAT-28 
function trim(str){
	return (str != "" ? str.replace(/(^\s+)|(\s+$)/g,"") : "");
}

function validateFields(){
	var formatValid = FIC_checkForm("form0");
	if(formatValid){
		if(getCheckedValue(form0.selectInvoiceDateRadio)=="DATEFRAME"){
			form0.form0_searchInvoiceVO_invoiceDateWiPastNumOfDays.value="";
		}else{
			form0.searchInvoiceVO_invoiceDateStartsOn.value="";
			form0.searchInvoiceVO_invoiceDateEndsOn.value="";
		}
		if(getCheckedValue(form0.selectPaymentDateRadio)=="DATEFRAME"){
			form0.form0_searchInvoiceVO_paymentDateWiPastNumOfDays.value="";
		}else{
			form0.searchInvoiceVO_paymentDateStartsOn.value="";
			form0.searchInvoiceVO_paymentEndsOn.value="";
		}
		if(getCheckedValue(form0.selectInvoiceDueRadio)=="DATEFRAME"){
			form0.form0_searchInvoiceVO_invoiceDueWiPastNumOfDays.value="";
			form0.form0_searchInvoiceVO_invoiceDueWiNextNumOfDays.value="";
		}else if(getCheckedValue(form0.selectInvoiceDueRadio)=="WITHINPAST"){
			form0.searchInvoiceVO_invoiceDueStartsOn.value="";
			form0.searchInvoiceVO_invoiceDueEndsOn.value="";
			form0.form0_searchInvoiceVO_invoiceDueWiNextNumOfDays.value="";
		}else{
			form0.searchInvoiceVO_invoiceDueStartsOn.value="";
			form0.searchInvoiceVO_invoiceDueEndsOn.value="";
			form0.form0_searchInvoiceVO_invoiceDueWiPastNumOfDays.value="";
		}
	}
	return formatValid;
}

function getCheckedValue(radioObj) {
	if(!radioObj)
		return "";
	var radioLength = radioObj.length;
	if(radioLength == undefined)
		if(radioObj.checked)
			return radioObj.value;
		else
			return "";
	for(var i = 0; i < radioLength; i++) {
		if(radioObj[i].checked) {
			return radioObj[i].value;
		}
	}
	return "";
}

function showLoadingImg(){
	if(loadingImgDiv) loadingImgDiv.style.display="block";
}

function hideLoadingImg(){
	if(loadingImgDiv) loadingImgDiv.style.display="none";
}

//judgDate by xinyu.Liu beijing time 2010.5.12
function judgDate(){
	var timePast = $('#form0_searchInvoiceVO_invoiceDateWiPastNumOfDays').val();
	var timePastTwo = $('#form0_searchInvoiceVO_paymentDateWiPastNumOfDays').val();
	var timePastThree = $('#form0_searchInvoiceVO_invoiceDueWiPastNumOfDays').val();
	var timeNextFour = $('#form0_searchInvoiceVO_invoiceDueWiNextNumOfDays').val();
	//update by donghao.guo 2011/10/10
	//var nodes = YAHOO.util.Selector.query(".validate-date-ca",'form0');
	var nodes = YAHOO.util.Selector.query('input[cssClass=validate-date-ca]');
	var bo = true;
	
	for(var d = 0; d<nodes.length; d++){
		if(!isValidDate(nodes[d].value)) {
			removeClassName(nodes[d].id,'validation-passed');
			addClassName(nodes[d].id,'validation-failed');
			bo = false;
		}
	}
	for(var d = 0; d<nodes.length; d=d+2){
		if(d+1 < nodes.length){
			if((nodes[d].value!="") && (nodes[d+1].value!="")){
				if(!correct(nodes[d].value,nodes[d+1].value)){
					removeClassName(nodes[d].id,'validation-passed');
					addClassName(nodes[d].id,'validation-failed');
					removeClassName(nodes[d+1].id,'validation-passed');
					addClassName(nodes[d+1].id,'validation-failed');
					bo = false;
				}
			}
		}
	}
	
	if(timePast < 0 || timePast > 10000 || timePast.match(/[^0-9]/)) {
		removeClassName('form0_searchInvoiceVO_invoiceDateWiPastNumOfDays','validation-passed');
		addClassName('form0_searchInvoiceVO_invoiceDateWiPastNumOfDays','validation-failed');
		bo = false;
	}
	if(timePastTwo < 0 || timePastTwo > 10000 || timePastTwo.match(/[^0-9]/)) {
		removeClassName('form0_searchInvoiceVO_paymentDateWiPastNumOfDays','validation-passed');
		addClassName('form0_searchInvoiceVO_paymentDateWiPastNumOfDays','validation-failed');
		bo = false;
	}
	if(timePastThree < 0 || timePastThree > 10000 || timePastThree.match(/[^0-9]/)) {
		removeClassName('form0_searchInvoiceVO_invoiceDueWiPastNumOfDays','validation-passed');
		addClassName('form0_searchInvoiceVO_invoiceDueWiPastNumOfDays','validation-failed');
		bo = false;
	}
	if(timeNextFour < 0 || timeNextFour > 10000 || timeNextFour.match(/[^0-9]/)) {
		removeClassName('form0_searchInvoiceVO_invoiceDueWiNextNumOfDays','validation-passed');
		addClassName('form0_searchInvoiceVO_invoiceDueWiNextNumOfDays','validation-failed');
		bo = false;
	}
	
	return bo;
}

function saveExcel(){
	showLoadingModalLayer();
	var titles = invoicePage.getTitlesParam("titles");
	var uri = saveExcelBySearchInvoiceUri + "?"+ titles +"&"+ invoicePage.paramStr;
	windowOpen(uri);
	hideLoadingModalLayer();
}

function startSearch(){
	if((!validateFields()) || (!judgDate())) return;
	invoicePage.myParam = composePostDataFromUi();
	filter1.clearAll();
	invoicePage.start();
}

////Get BAN list by Vendor id ////
function getBanListByVendorId(selIndex,v){
	if(form0_searchInvoiceVO_vendorId.getValue()=='all'){
		updateDropdownList([], "id", "no", 'form0_searchInvoiceVO_banId',selIndex, {key:"all",value:"All"});
		return;
	}
	var callback = {
		success: function(o){
			if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
				window.location.reload();
				return;
			}
			var data = eval("("+o.responseText+")");
			if(data.error){
				alert("Error: " + data.error);
				return;
			}
			updateDropdownList(data, "id", "no", 'form0_searchInvoiceVO_banId',selIndex, {key:"all",value:"All"});
			if(v)$("#form0_searchDisputeVO_banId").val(v);
			delete data;
		},
		failure: showError
	};

	var pdata = "selVendorId=" + form0_searchInvoiceVO_vendorId.getValue();
	YAHOO.util.Connect.asyncRequest('POST' , "getBanListByVendorId.action" , callback , pdata); 
}

function updateDropdownList(data, keyName, valueName, ddlId, selIndex, header){
	var selo = YAHOO.util.Dom.get(ddlId);
	while(selo.length>0) 
		selo.remove(0);
	if(header!=null) selo.options[selo.options.length] = new Option(header.value, header.key);
	for(var i = 0; i<data.length; i++){
		selo.options[selo.options.length]=new Option(eval('data[i].'+valueName),eval('data[i].'+keyName));
	}
	selo.selectedIndex = selIndex;
}

////Get Vendor list////
function getVendorListByVendorId(selIndex){
	var callback = {
		success: function(o){
			if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
				window.location.reload();
				return;
			}
			var data = eval("("+o.responseText+")");
			if(data.error){
				hideLoadingImg();
				alert("Error: " + data.error);
				return;
			}
			hideLoadingImg();
			delete data;
		},
		failure: showError
	};

	showLoadingImg();
	YAHOO.util.Connect.asyncRequest('POST' , "getUserPreviledgedVendorList.action" , callback , "" ); 
}

////below is save search as quick link/////
var saveQuicklinkUri = "doSaveInvoiceSearch.action";

function resetRadioButton(){
	form0.selectInvoiceDateRadio[0].checked=true;
	form0.selectPaymentDateRadio[0].checked=true;
	form0.selectInvoiceDueRadio[0].checked=true;
}

//update radio button after get saved quicklink.
function updateRadioButton(){
		if(form0.form0_searchInvoiceVO_invoiceDateWiPastNumOfDays.value!=""){
			form0.selectInvoiceDateRadio[1].checked=true;
		}
		if(form0.searchInvoiceVO_invoiceDateStartsOn.value!=""||form0.searchInvoiceVO_invoiceDateEndsOn.value!=""){
			form0.selectInvoiceDateRadio[0].checked=true;
		}
		if(form0.form0_searchInvoiceVO_paymentDateWiPastNumOfDays.value!=""){
			form0.selectPaymentDateRadio[1].checked=true;
		}
		if(form0.searchInvoiceVO_paymentDateStartsOn.value!=""||form0.searchInvoiceVO_paymentEndsOn.value!=""){
			form0.selectPaymentDateRadio[0].checked=true;
		}
		if(form0.form0_searchInvoiceVO_invoiceDueWiPastNumOfDays.value!=""){
			form0.selectInvoiceDueRadio[1].checked=true;
		}
		if(form0.form0_searchInvoiceVO_invoiceDueWiNextNumOfDays.value!=""){
			form0.selectInvoiceDueRadio[2].checked=true;
		}
		if(form0.searchInvoiceVO_invoiceDueStartsOn.value!=""||form0.searchInvoiceVO_invoiceDueEndsOn.value!=""){
			form0.selectInvoiceDueRadio[0].checked=true;
		}
}

//reset form element
function resetFormElementValue(){
	cleanFormElementValue();
	//modified by xinyu.Liu on 2010.5.11 for CCM-129
	FIC_checkForm(form0);
	//update Vendor and Ban drop down list
	getVendorListByVendorId(0);
	updateDropdownList([], "id", "no", 'form0_searchInvoiceVO_banId',0, {key:"all",value:"All"});
	form0_searchInvoiceVO_vendorId.setValue("all");
	form0_searchInvoiceVO_banId.setValue("all");
//	form0_searchInvoiceVO_lineOfBusiness.setValue("all");
	clearCheckbox();
}

YAHOO.util.Event.onDOMReady(function(){
	
	if (!window.invoicePage) {
		invoicePage = new SANINCO.Page('_dataTable',"invoicePage",{
		    sortingField : "i.invoice_date",
		    sortingDirection : "desc",
			vo : "searchInvoiceVO",
			pageTable : "block",
		    totalPageUri : getInvoiceTotalPageNoUri,
		    dataUri : searchInvoiceUri,
			paginationDiv : "_dataTable_page",
			recPerArray : [10,20,30,40,50,70,90,100],
		    cols : [
				/*	{   title : function(o){return '<img src="include/images/focus.png" style="border:0px;"></img>'},dataField : function(o){return showItemLabelImage(o)},sort : "invoice_label"
				    },*/
				    { title : "Invoice Number",dataField: function(o){return "<a href=\"javascript:mark(\'viewInvoiceDetails.action?invoiceId="+o.id+"\')\">"+o.no+"</a>";},sort : "i.invoice_number", filtration : {name:"i.invoice_number",alias:"Invoice Number"}
					},{ title : "BAN",dataField: "ban",sort : "b.account_number", filtration : {name:"b.account_number",alias:"BAN"}
				    },{ title : "Line Of Business",dataField: "lineOfBusiness",sort : "b.line_of_business", filtration : {name:"b.line_of_business",alias:"Line Of Business"}
				    },{ title : "Vendor",dataField: "vendor",sort : "v.vendor_name", filtration : {name:"v.vendor_name",alias:"Vendor"}
					},{ title : "Invoice Date",dataField: "date",sort : "i.invoice_date", filtration : {name:"i.invoice_date",alias:"Invoice Date"}				
				    },{ title : "Invoice Current Due",dataField: "currentDue",sort : "if(i.invoice_current_due IS NULL, 0, i.invoice_current_due)",className:"aright"
					},{ title : "Original",dataField: "originalFlag",sort : "i.original_flag", filtration : {name:"i.original_flag",alias:"Original"}
				    },{ title : "Analyst",dataField: "analyst",sort : "concat(u.first_name,blank_space(),u.last_name)", filtration : {name:"concat(u.first_name,blank_space(),u.last_name)",alias:"Analyst"}	    
				    },{ title : "Due Date",dataField: "due",sort : "i.invoice_due_date", filtration : {name:"i.invoice_due_date",alias:"Due Date"}
				    },{ title : "Total Due",dataField: "total",sort : "i.invoice_total_due", filtration : {name:"i.invoice_total_due",alias:"Total Due"},className:"aright"			    
				    },{ title : "Payment Amount",dataField: "paymentAmount",sort : "i.payment_amount", filtration : {name:"i.payment_amount",alias:"Payment Amount"},className:"aright"
				    },{ title : "Dispute Amount",dataField: "disputeAmount",sort : "i.dispute_amount", filtration : {name:"i.dispute_amount",alias:"Dispute Amount"},className:"aright"
				    },{ title : "Misc Adjustment Amount",dataField: "miscAdjustmentAmount",sort : "i.misc_adjustment_amount", filtration : {name:"i.misc_adjustment_amount",alias:"Misc Adjustment Amount"},className:"aright"
				    },{ title : "Invoice Status",dataField: "invoiceStatus",sort : "s.invoice_status_name", filtration : {name:"s.invoice_status_name",alias:"Invoice Status"}
				    },{ title : "Days in Status",dataField: "daysInStatus",sort : "datediff(now(),i.status_timestamp)"
				    },{ title : "Payment Status",dataField: "paymentStatus",sort : "ps.payment_status_name", filtration : {name:"ps.payment_status_name",alias:"Payment Status"}
				    },{ title : "Payment Date",dataField: "paymentDate",sort : "p.payment_date", filtration : {name:"p.payment_date",alias:"Payment Date"}
					},{ title : "Payment Transaction Number",dataField: "paymentTransactionNumber",sort : "p.payment_transaction_number", filtration : {name:"p.payment_transaction_number",alias:"Payment Transaction Number"}
				    },{ title : "Payment Reference Number",dataField: "paymentReferenceNumber",sort : "p.payment_reference_code", filtration : {name:"p.payment_reference_code",alias:"Payment Reference Number"}
				    }
				]
		}); 
		
		invoicePage.addTotalSuccessEvent(function(data){
			if(data.totalResultNo < 0 || data.error){
				document.getElementById('saveExcel').style.display = "none";
				document.getElementById('_gridDiv').style.display = "none";
				
			}else{
				if(data.totalResultNo == 0 ){
					document.getElementById('saveExcel').style.display = "none";
				}else{
					document.getElementById('saveExcel').style.display = "block";
				}
				document.getElementById('_gridDiv').style.display = "block";
			}
		});
	}
		
	filter1 = new SANINCO.Filter();
    filter1.addEditeEvent(function(){invoicePage.start();});
    filter1.add('i.invoice_number', 'String');
    filter1.add('v.vendor_name', 'String');
	filter1.add('b.account_number', 'String');
	filter1.add('i.original_flag', 'String');
	filter1.add('i.invoice_date', 'date');
	filter1.add('i.invoice_due_date', 'date');
	filter1.add('i.invoice_total_due', 'date');
	filter1.add('concat(u.first_name,blank_space(),u.last_name)', 'String');
	filter1.add('i.payment_amount', 'number');
	filter1.add('i.dispute_amount', 'number');
	filter1.add('i.misc_adjustment_amount', 'number');
	filter1.add('s.invoice_status_name', 'String');
	filter1.add('p.payment_transaction_number', 'String');
	filter1.add('ps.payment_status_name', 'String');
	filter1.add('p.payment_reference_code', 'String');
	filter1.add('p.payment_date', 'date');
	filter1.add('b.line_of_business', 'String');
	
    invoicePage.setFilter(filter1);

});

//re-render vendor list and ban list after getting back quicklink from database
function rerenderVendorAndBanList(vendors, bans){
//	updateDropdownList(vendors, "id", "name", 'form0_searchInvoiceVO_vendorId',0,{key:"all",value:"All"});
//	updateDropdownList(bans, "id", "no", 'form0_searchInvoiceVO_banId',0, {key:"all",value:"All"});
}

//changRadio by xinyu.Liu beijing time 2010.5.12
function changRadio(type){
	if (type == 0) {
		form0.selectInvoiceDateRadio[0].checked = true;
		$('#form0_searchInvoiceVO_invoiceDateWiPastNumOfDays').val("");
	}
	if (type == 1) {
		form0.selectInvoiceDateRadio[1].checked = true;
		$('#searchInvoiceVO_invoiceDateStartsOn').val("");
		$('#searchInvoiceVO_invoiceDateEndsOn').val("");
	}
	if (type == 2) {
		form0.selectInvoiceDueRadio[0].checked = true;
		$('#form0_searchInvoiceVO_invoiceDueWiPastNumOfDays').val("");
		$('#form0_searchInvoiceVO_invoiceDueWiNextNumOfDays').val("");
	}
	if (type == 3) {
		form0.selectInvoiceDueRadio[1].checked = true;
		$('#searchInvoiceVO_invoiceDueStartsOn').val("");
		$('#searchInvoiceVO_invoiceDueEndsOn').val("");
		$('#form0_searchInvoiceVO_invoiceDueWiNextNumOfDays').val("");
	}
	if (type == 4) {
		form0.selectInvoiceDueRadio[2].checked = true;
		$('#searchInvoiceVO_invoiceDueStartsOn').val("");
		$('#searchInvoiceVO_invoiceDueEndsOn').val("");
		$('#form0_searchInvoiceVO_invoiceDueWiPastNumOfDays').val("");
	}
	if (type == 5) {
		form0.selectPaymentDateRadio[0].checked = true;
		$('#form0_searchInvoiceVO_paymentDateWiPastNumOfDays').val("");
	}
	if (type == 6) {
		form0.selectPaymentDateRadio[1].checked = true;
		$('#searchInvoiceVO_paymentDateStartsOn').val("");
		$('#searchInvoiceVO_paymentEndsOn').val("");
	}
}
/**
 * Add By Mingyang.Li
 * @param {o} item
 */
function showItemLabelImage(o){
	if(o.invoice_label == ''){
		return "";
	}else{
		return '<img src="include/images/focus.png" id='+o.invoice_label+' onMouseOver="showItemLabel(event,this.id)" onMouseOut="disableItemLabel()"></img>';
	}
}

function showItemLabel(e,invoiceLabelList){
	var x=document.body.scrollLeft+e.clientX;
	var y=document.body.scrollTop+e.clientY-45;
	$(".InSePoput").css({   
		"position": "absolute",   
		"top": y+'px',   
		"left":x+'px'
	});
	var callback = {
		success:function(o){
			
			var data = eval("(" + o.responseText + ")").data;
			var html = '<table border="0" align="center" cellpadding="0" cellspacing="0">' ;
			
			for(var i =0;i<data.length;i++){
				
				var item = data[i];
				html += '<tr">'
					 + 		'<td width="14%" align="center">'
					 + 			'<img src="'+item.labelIcon+'" width="16" height="16" />'
					 + 		'</td>'
					 + 		'<td width="86%">'+item.labelName+'</td>'
					 +	'</tr>';
	        }
			html += '<tr">'
				 + 		'<td width="14%" align="center">'
				 + 		'</td>'
				 + 		'<td width="86%"></td>'
				 +	'</tr>';
			html += '</table>';
			document.getElementById('labelPanel').innerHTML = html;
			$(".InSePoput").show("slow");
		}, 
		failure:showError
		};
		YAHOO.util.Connect.asyncRequest("POST", searchInvoiceLabel + "?labelIds=" + invoiceLabelList, callback);
}

function disableItemLabel(){
	$(".InSePoput").hide("slow");
}

/**
 * Add By Chao.Liu
 * @param {Object} k
 * @param {Object} v
 * @param {Object} bans
 */
function setJSelectQuicklink(k,v,d){
	if(k == "searchInvoiceVO.vendorId"){
		form0_searchInvoiceVO_vendorId.setValue(v);
		if(!d["searchInvoiceVO.banId"])getBanListByVendorId(0);
		return true;
	}
	if(k == "searchInvoiceVO.banId"){
		if(d["searchInvoiceVO.vendorId"]){
			getBanListByVendorId(0,v);
		}else{
			form0_searchInvoiceVO_banId.setValue(v);
		}
		return true;
	}
	return false;
}
function clearJSelectQuicklink(){
	form0_searchInvoiceVO_vendorId.setValue("all");
	form0_searchInvoiceVO_banId.setValue("all");
}