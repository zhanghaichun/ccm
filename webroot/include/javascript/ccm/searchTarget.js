var getInvoiceTotalPageNoUri = "doGetTragetSearchTotalPageNo.action";
var searchInvoiceUri = "searchTarget.action";
var saveExcelBySearchTargetUri = "saveExcelBySearchTarget.action";
var searchInvoiceLabel = "doSearchInvoiceLabel.action"
var allOptions="";
var lastOptions ="";
var targetListNo=0;
targetLeftState = {};
rightState = {};
var allVender = {};
var allBan={};
var targetIdsForResultDown={};

YAHOO.util.Event.onDOMReady(function(){
	if (!window.invoicePage) {
		invoicePage = new SANINCO.Page('_dataTable',"invoicePage",{
		    sortingField : "t.id",
		    sortingDirection : "desc",
			vo : "searchTargetVO",
			pageTable : "block",
		    totalPageUri : getInvoiceTotalPageNoUri,
		    dataUri : searchInvoiceUri,
			paginationDiv : "_dataTable_page",
			recPerArray : [10,20,30,40,50,70,90,100],
		    cols : [
					{   title : function(o){return "<input type=\"checkbox\" class='noBorderRadioButton' name=\"leftbox\" onclick=\"chooseleftall()\"/>";},dataField : function(o){return "<input type=\"checkbox\" class='noBorderRadioButton' name=\"leftbox\" value=\"" + o.id + "\"   onclick=\"calibrationCheckboxLeft(this);\"/>";}
			//	    },{ title : "Invoice Number",dataField: function(o){return "<a href=\"javascript:mark(\'viewInvoiceDetails.action?invoiceId="+o.id+"\')\">"+o.no+"</a>";},sort : "i.invoice_number", filtration : {name:"i.invoice_number",alias:"Invoice Number"}
					},{ title : "Vendor",dataField: "vendor",sort : "v.vendor_name", filtration : {name:"v.vendor_name",alias:"Vendor"}
					},{ title : "BAN",dataField: "ban",sort : "b.account_number", filtration : {name:"b.account_number",alias:"BAN"}
					},{ title : "Circuit Number",dataField: "circuit_number",sort : "t.stripped_circuit_number", filtration : {name:"t.stripped_circuit_number",alias:"Circuit Number"}				
				    },{ title : "Charge Type",dataField: "charge_type_name",sort : "tc.charge_type_name",filtration : {name:"tc.charge_type_name",alias:"Charge Type"}
					},{ title : "Target Amount",dataField: "target_amount",sort : "t.target_amount", filtration : {name:"t.target_amount",alias:"Target Amount"},className:"aright"
				    },{ title : "Change Amount",dataField: "change_amount",sort : "t.change_amount", filtration : {name:"t.change_amount",alias:"Change Amount"},className:"aright"    
				    },{ title : "Change Percentage",dataField: "change_percentage",sort : "t.change_percentage", filtration : {name:"t.change_percentage",alias:"Change percentage"}
				    },{ title : "Variation Percentage",dataField: "variation_percentage",sort : "t.allowed_variation_percentage", filtration : {name:"t.allowed_variation_percentage",alias:"Variation Percentage"}			    
				    },{ title : "Target Period",dataField: "targetPeriod",sort : "concat(t.period_from,t.period_to)", filtration : {name:"concat(t.period_from,t.period_to)",alias:"Target Period"}
				    },{ title : "Allowed Period Shift",dataField: "allowed_period_shift",sort : "t.allowed_period_shift", filtration : {name:"t.allowed_period_shift",alias:"Allowed Period Shift"}
				    },{ title : "Created By",dataField: "created_by",sort : "t.created_by", filtration : {name:"t.created_by",alias:"Create By"}
				    },{ title : "Created Date",dataField: "created_timestamp",sort : "t.created_timestamp", filtration : {name:"t.created_timestamp",alias:"Created Date"}
				    }
				]
		}); 
		
		invoicePage.addSuccessEvent(function(data){
			targetLeftState={};
		})

		invoicePage.addTotalSuccessEvent(function(data){
			if(data.totalResultNo < 0 || data.error){
				document.getElementById('saveExcel').style.display = "none";
				document.getElementById('saveExcel2').style.display = "none";
				document.getElementById('saveExcel3').style.display = "none";
//				document.getElementById('saveExcel4').style.display = "none";
				document.getElementById('_gridDiv').style.display = "none";
				
			}else{
				document.getElementById('saveExcel').style.display = "block";
				document.getElementById('_gridDiv').style.display = "block";
				document.getElementById('saveExcel2').style.display = "block";
				document.getElementById('saveExcel3').style.display = "block";
//				document.getElementById('saveExcel4').style.display = "block";
			}
		});
	}
	filter1 = new SANINCO.Filter();
    filter1.addEditeEvent(function(){invoicePage.start();});
    filter1.add('i.invoice_number', 'String');
    filter1.add('v.vendor_name', 'String');
	filter1.add('b.account_number', 'String');
	filter1.add('t.stripped_circuit_number', 'String');
	filter1.add('concat(t.period_from,t.period_to)', 'String');
	filter1.add('tc.charge_type_name', 'String');
	filter1.add('t.allowed_variation_percentage', 'number');
	filter1.add('t.change_percentage', 'number');
	filter1.add('t.target_amount', 'number');
	filter1.add('pt.change_amount', 'number');

	filter1.add('t.created_by', 'String');
	filter1.add('t.created_timestamp', 'String');
	filter1.add('t.change_amount','String');
	filter1.add('t.allowed_period_shift','number');
	
    invoicePage.setFilter(filter1);
    lastOptions = getlastOptions();
    initResultWindow();
		
	
});



function getBanListByVendorId(selIndex,v){
	if(form0_searchTargetVO_vendorId.getValue()=='all'){
		updateDropdownList([], "id", "no", 'form0_searchTargetVO_banId',selIndex, {key:"all",value:"All"});
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
			updateDropdownList(data, "id", "no", 'form0_searchTargetVO_banId',selIndex, {key:"all",value:"All"});
			delete data;
		},
		failure: showError
	};

	var pdata = "selVendorId=" + form0_searchTargetVO_vendorId.getValue();
	YAHOO.util.Connect.asyncRequest('POST' , "getBanListByVendorId.action" , callback , pdata); 
}

function getBanListByVendorId(selIndex,v){
	if(form0_searchTargetVO_vendorId.getValue()=='all'){
		updateDropdownList([], "id", "no", 'form0_searchTargetVO_banId',selIndex, {key:"all",value:"All"});
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
			updateDropdownList(data, "id", "no", 'form0_searchTargetVO_banId',selIndex, {key:"all",value:"All"});
			delete data;
		},
		failure: showError
	};

	var pdata = "selVendorId=" + form0_searchTargetVO_vendorId.getValue();
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
	return true;
}

function clearDisabled(id,number){
	if ($('#'+ id)[0].checked) {
		$(".Clear" + number).css("background","#fff").attr("disabled","").val("");
	}else{
		$(".Clear" + number).css("background","#ccc").attr("disabled","disabled").val("");
	}
}

function changRadio(type){
	
	if (type == 2) {
		form0.selectInvoiceDueRadio[0].checked = true;
		$('#form0_searchTargetVO_invoiceDueWiPastNumOfDays').val("");
		$('#form0_searchTargetVO_invoiceDueWiNextNumOfDays').val("");
	}
	if (type == 3) {
		form0.selectInvoiceDueRadio[1].checked = true;
		$('#searchTargetVO_invoiceDueStartsOn').val("");
		$('#searchTargetVO_invoiceDueEndsOn').val("");
		$('#form0_searchTargetVO_invoiceDueWiNextNumOfDays').val("");
	}
	if (type == 4) {
		form0.selectInvoiceDueRadio[2].checked = true;
		$('#searchTargetVO_invoiceDueStartsOn').val("");
		$('#searchTargetVO_invoiceDueEndsOn').val("");
		$('#form0_searchTargetVO_invoiceDueWiPastNumOfDays').val("");
	}
}




function startSearch(){
	
	if(composePostDataFromUi()=="searchTargetVO.historicalInvoice=false"){
		alert("You must enter search criteria!");
		return;
	}else if (!validateTargetvariables()){
		return;
	}
	
	targetLeftState = {};
	clearNewTargetList();
	document.getElementById('inputDateTable').style.display = "none";
	filter1.clearAll();
	invoicePage.myParam = composePostDataFromUi();
	invoicePage.start();
}

function validateTargetvariables(){
	var variablesflag=true;
	if(!validateFields("form0")){
		return false;
	}
	
	if($.trim($("#searchTargetVO_targetAmount").val()) != ""){
		if($.trim($("#searchTargetVO_targetAmount").val())<0){
			alert("Target amount field can't enter negative number");
			variablesflag=false;
		} else if(!/^\d{0,20}(\.\d{1,2})?$/g.test($.trim($("#searchTargetVO_targetAmount").val()))){
			alert("Target amount fields can only enter less than 20 digits 2 decimal places");
			variablesflag=false;
		}
	}
	
	if($.trim($("#searchTargetVO_changeAmount").val()) != ""){
		if($.trim($("#searchTargetVO_changeAmount").val())<0){
			alert("Change  amount field can't enter negative number");
			variablesflag=false;
		}else if(!/^\d{0,20}(\.\d{1,2})?$/g.test($.trim($("#searchTargetVO_changeAmount").val()))){
			alert("Change  amount fields can only enter less than 20 digits 2 decimal places");
			variablesflag=false;
		}
	}
	
	if($.trim($("#searchTargetVO_changePercentage").val()) != ""){
		if(!/^\d{0,20}(\.\d{1,2})?$/g.test($.trim($("#searchTargetVO_changePercentage").val()))){
			alert("Change Percentage(%)  Field Only allowed to input 2 decimal places");
			variablesflag=false;
		}
	}
	
	return variablesflag;
}

function composePostDataFromUi(){
	
	var postData = "searchTargetVO.historicalInvoice=false";
	
	if(form0_searchTargetVO_vendorId.getValue()!="all"){
		postData += ("&searchTargetVO.vendorId="+form0_searchTargetVO_vendorId.getValue()); 
		if($('#form0_searchTargetVO_banId').val()!="all") postData += ("&searchTargetVO.banId="+$('#form0_searchTargetVO_banId').val());
	}else{
		if(form0_searchTargetVO_banId.getValue()!="all") postData += ("&searchTargetVO.banId="+form0_searchTargetVO_banId.getValue()); 
	}
	if($("#form0_searchTargetVO_circuitNumber").val() != "all"){
		postData += "&searchTargetVO.circuitNumber=" + $("#form0_searchTargetVO_circuitNumber").find("option:selected").text();
	}
	
	if($.trim($("#VPercent").val()) != ""){
//		postData += "&searchTargetVO.vPercent=" + (parseFloat($("#VPercent").val())/100).toFixed(2);
		postData += "&searchTargetVO.vPercent=" + (parseFloat($("#VPercent").val())).toFixed(2);
	}
	
	if($("#chargeTypeSelect").val() != "all"){
		postData += "&searchTargetVO.chargeTypeSelect=" + $("#chargeTypeSelect").val();
	}
	
	if($("#createdBy").val() != "all"){
		postData += "&searchTargetVO.createdBy=" + $("#createdBy").val();
	}
	
	if($.trim($("#searchTargetVO_targetAmount").val()) != ""){
		postData += "&searchTargetVO.targetAmount=" + trim($("#searchTargetVO_targetAmount").val());
	}
	
	if($.trim($("#searchTargetVO_changeAmount").val()) != ""){
		postData += "&searchTargetVO.changeAmount=" + trim($("#searchTargetVO_changeAmount").val());
	}
	
	if($.trim($("#searchTargetVO_changePercentage").val()) != ""){
//		postData += "&searchTargetVO.changePercentage=" + ($("#searchTargetVO_changePercentage").val()/100).toFixed(5);
		postData += "&searchTargetVO.changePercentage=" + trim($("#searchTargetVO_changePercentage").val());
	}
	
	if($.trim($("#searchTargetVO_targetPeriodStartsOn").val()) != ""){
		postData += "&searchTargetVO.targetPeriodStartsOn=" + $("#searchTargetVO_targetPeriodStartsOn").val().replace("/","").substring(0,6);
	}
	
	if($.trim($("#searchTargetVO_targetPeriodEndsOn").val()) != ""){
		postData += "&searchTargetVO.targetPeriodEndsOn=" + $("#searchTargetVO_targetPeriodEndsOn").val().replace("/","").substring(0,6);
	}
	
	if($.trim($("#searchTargetVO_createdDateStartsOn").val()) != ""){
		postData += "&searchTargetVO.createdDateStartsOn=" + $("#searchTargetVO_createdDateStartsOn").val();
	}
	
	if($.trim($("#searchTargetVO_createdEndsOn").val()) != ""){
		postData += "&searchTargetVO.createdEndsOn=" + $("#searchTargetVO_createdEndsOn").val();
	}
	
	if(filter1.getCause()!="") postData += ("&searchTargetVO.filter=" + filter1.getCause());
	
	return postData;
}

function trim(str){
	return (str != "" ? str.replace(/(^\s+)|(\s+$)/g,"") : "");
}


function getCircuitList(){
	
	var banId = 0;
	if(form0_searchTargetVO_banId.getValue() != "all"){
		banId = form0_searchTargetVO_banId.getValue();
	}else if ($('#form0_searchTargetVO_banId').val() != "all"){
		banId = $('#form0_searchTargetVO_banId').val();
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
			return updateDropdownList(data, "id", "no", 'form0_searchTargetVO_circuitNumber',0, {key:"all",value:"All"});
			delete data;
		},
		failure: showError
	};

	var pdata = "banId=" +banId;
	YAHOO.util.Connect.asyncRequest('POST' , "getCircuitList.action" , callback , pdata); 
}

function onCircuitSelect(){
	
	if($("#form0_searchTargetVO_circuitNumber").val() != "all"){
		var options = $("#chargeTypeSelect").html().split("</OPTION>");
		for(var i=0;i< options.length; i++){
			if(i>8 && options[i] !=""){
				$("#chargeTypeSelect option[value=1]").remove();
				$("#chargeTypeSelect option[value="+i+"]").remove();
			}
		}
	}else{
		$("#chargeTypeSelect").append(lastOptions);
	}
}

function getlastOptions(){
	allOptions = $("#chargeTypeSelect option[value=all]").parent().html();
	var option="";
	var options = $("#chargeTypeSelect option[value=all]").parent().html().split("</OPTION>");
	for(var i=0;i< options.length; i++){
		if(i==1 && options[i] !=""){
			option += options[1]+"</OPTION>";
		}
		if(i>8 && options[i] !=""){
			option += options[i]+"</OPTION>";
		}
	}
	return option;
}

function disabledRadio(conditionNo){
	for(var i=1; i<4; i++){
		$(".condition"+i).css("background","#ccc").attr("disabled","disabled").val("");
	}

	$(".condition"+conditionNo).css("background","#fff").attr("disabled","").val("");
}

function chooseleftall(){
    var checked = document.getElementsByName("leftbox")[0].checked;
    var selects = document.getElementsByName("leftbox");
    for (var i = 1; i < selects.length; i++) {
        selects[i].checked = checked;
        if (selects[i].checked == true) {
            targetLeftState["" + selects[i].value] = true;
            $(selects[i]).parent().parent().addClass("hightlight");
        }
        else {
        	delete targetLeftState["" + selects[i].value];
//        	targetLeftState = {};
            $(selects[i]).parent().parent().removeClass("hightlight");
        }
    }
}

function calibrationCheckboxLeft(o){
    if (o.checked == true) {
        targetLeftState["" + o.value] = true;
        $(o).parent().parent().addClass("hightlight");
    }
    else {
    	delete targetLeftState["" + o.value] ;
        $(o).parent().parent().removeClass("hightlight");
    }
}

function saveExcel(){
	var targetIds="";
	for(var id in targetLeftState){
		targetIds+="&targetIds="+id;
	}
	targetIds = targetIds.replace("&", "");
    if("" == targetIds)
    {return alert('Please select one or more records')}
    else{
	showLoadingModalLayer();
	var titles = invoicePage.getTitlesParam("titles");
	var uri = saveExcelBySearchTargetUri + "?"+ titles +"&"+ invoicePage.paramStr+"&"+targetIds;
	windowOpen(uri);
	hideLoadingModalLayer();
    }
}

function resetFormElementValue(){


	//update Vendor and Ban drop down list
	//getVendorListByVendorId(0);
	updateDropdownList([], "id", "no", 'form0_searchTargetVO_banId',0, {key:"all",value:"All"});
	updateDropdownList([], "id", "no", 'form0_searchTargetVO_circuitNumber',0, {key:"all",value:"All"});
	form0_searchTargetVO_vendorId.setValue("all");
	form0_searchTargetVO_banId.setValue("all");
	$("#chargeTypeSelect").val("all");
	$("#VPercent").val("");
	$("#createdBy").val("all");
	$("#searchTargetVO_targetAmount").val("");
	$("#searchTargetVO_changeAmount").val("");
	$("#searchTargetVO_changePercentage").val("");

	clearCheckbox();
}

function clearCheckbox(){
	$(".ClearCheckbox").removeAttr("checked");
	$(".Date").css("background","#ccc").attr("disabled","disabled").val("");
	targetLeftState = {};
}

function showLoadingImg(){
	if(loadingImgDiv) loadingImgDiv.style.display="block";
}

function hideLoadingImg(){
	if(loadingImgDiv) loadingImgDiv.style.display="none";
}

function creatNewTarget(s){
	clearNewTargetList();
	resetFormElementValue();
	document.getElementById('_gridDiv').style.display = "none";
	document.getElementById('inputDateTable').style.display = "block";
}

/*
 * �ֶ����target
 * @param source=(target��ӷ�ʽ)
 * @return
 */
function addNewTarget(source){    
	var html=""+
	'<TR style="HEIGHT: 30px" class="newTargetTR" id="targetTr'+targetListNo+'">'+ 
	"<td><input type=\"checkbox\" class='noBorderRadioButton' name=\"rightbox\" value=\"" + targetListNo + "\" onclick=\"calibrationCheckboxRight(this);\"/></td>"+
	'<td><div style="width:320px;"><div id="venderList'+targetListNo+'"></div></div></td>'+
	'<TD><div style="width:270px;" id="banByVendor'+targetListNo+'"><div id="banList'+targetListNo+'"></div></div> </TD>'+
	'<TD><select id="circuitList'+targetListNo+'" style="width:100px" onChange="onCircuitListChange('+targetListNo+')"><option value="all">All</option></select></TD>'+
	'<TD><input  id="periodFrom'+targetListNo+'"  maxlength="6" onkeyup="this.value=this.value.replace(/\\D/g,\'\')" /></TD>'+
	'<TD><input  id="periodTo'+targetListNo+'"  maxlength="6" onkeyup="this.value=this.value.replace(/\\D/g,\'\')" /></TD>'+
	'<TD><input  id="aps'+targetListNo+'" maxlength="9" onkeyup="this.value=this.value.replace(/\\D/g,\'\')"/></TD>'+
	'<TD><select id="chargetTypeList'+targetListNo+'" style="width:120px"  /></TD>'+
	'<TD><input id="ta'+targetListNo+'" onfocus="disInput('+targetListNo+',this)" maxlength="15" style=\"background:#ccc\"/></TD>'+
	'<TD class=aright><input id="avp'+targetListNo+'" maxlength="3" onfocus="disInput('+targetListNo+',this)" onkeyup="inputValidate(this);" maxlength="3" style=\"width:100%;background:#ccc\"/></TD>'+
	'<TD class=aright><input id="ca'+targetListNo+'" maxlength="15" onfocus="disInput('+targetListNo+',this)" style=\"background:#ccc\"/></TD>'+
	'<TD class=aright><input id="cp'+targetListNo+'" maxlength="3"  onfocus="disInput('+targetListNo+',this)" onkeyup="inputValidate(this);" style=\"background:#ccc\"/></TD>'+
	'<input id="source'+targetListNo+'" value="'+source+'" class="validate-percent" maxlength="6" style="display:none;"/>'+	'</TR>';
	$("#newTargetList").append(html);
	initVenderList(targetListNo);         //��ʼ������Vendor
	initBanListFlex(targetListNo);       //��ʼ������Ban
	initChargeTypeList(targetListNo);    //��ʼ������ChargeType
	targetListNo++;                      //target��ݵ�����,һ�����һ��target����
}

/*
 *�ϴ�Target Excel�󷵻ص�ǰ̨��ʾ 
 * @param o       Excel������Target���
 * @param a       Excel�����в�ͬ��vendor�µ�ban:ע 1��vendor��Ӧ�ö�ban
 * @param b	      Excel�����в�ͬ��ban�µ�curcuit 
 * @param c       Excel�����в�ͬ��vendor��ban��curcuit
 * @return
 */
function uploadCallbackSuccess(o,a,b,c) {
	showLoadingModalLayer();
	var arr = new Array();
	arr=eval("("+o+")")
	for(var i=0;i<arr.length;i++){
		var html=""+		
		'<TR style="HEIGHT: 30px" class="newTargetTR" id="targetTr'+targetListNo+'">'+ 
		"<td><input type=\"checkbox\" class='noBorderRadioButton' name=\"rightbox\" value=\"" + targetListNo + "\" onclick=\"calibrationCheckboxRight(this);\"/></td>"+
		'<td><div style="width:320px;"><div id="venderList'+targetListNo+'"></div></div></td>'+
		'<TD><div style="width:270px;" id="banByVendor'+targetListNo+'"><div id="banList'+targetListNo+'"></div></div> </TD>'+
		'<TD><select id="circuitList'+targetListNo+'" style="width:100px;" onChange="onCircuitListChange('+targetListNo+')"></select></TD>'+
		'<TD><input  id="periodFrom'+targetListNo+'"  maxlength="6" value="'+arr[i].PeriodFrom+'" /></TD>'+
		'<TD><input  id="periodTo'+targetListNo+'" maxlength="6" value="'+arr[i].PeriodTo+'" /></TD>'+
		'<TD><input  id="aps'+targetListNo+'" maxlength="9" value="'+arr[i].AllowedPeriodShift+'" /></TD>'+
		'<TD><select id="chargetTypeList'+targetListNo+'" style="width:120px"></select></TD>'+
		'<TD><input id="ta'+targetListNo+'"  value="'+arr[i].TargetAmount+'" class="validate-percent" maxlength="21"/></TD>'+
		'<TD class=aright><input id="avp'+targetListNo+'" value="'+arr[i].AllowedVariationPercentage+'"  maxlength="3" onkeyup="inputValidate(this);" style=\"width:100%\"/></TD>'+
		'<TD class=aright><input id="ca'+targetListNo+'" value="'+arr[i].ChangeAmount+'"  maxlength="21" /></TD>'+
		'<TD class=aright><input id="cp'+targetListNo+'" value="'+arr[i].ChangePercentage+'"  maxlength="3" onkeyup="inputValidate(this);"/></TD>'+
		'<input id="source'+targetListNo+'" value="'+arr[i].Source+'"  maxlength="6" style="display:none;"/>'+
		'</TR>';
		$("#newTargetList").append(html);
		var no = targetListNo;
		initVenderList2(no,arr[i].vendorName,arr[i].accountNumber);
		var arr0 = new Array();             
		arr0 =eval("("+a+")");     //Banlistbyvendor
		getBanLists0(no,arr0,arr[i].vendorId,arr[i].banId,arr[i].accountNumber);
		
		var arrb = new Array();
		arrb =eval("("+b+")");       //circuitByBan
		
		var arrc=new Array();         //circuitByBanAndVendor
		arrc =eval("("+c+")");
		getCircutLists0(no,arrb,arrc,arr[i].banId,arr[i].CircuitNumber,arr[i].CircuitNumber); //load circutList data(circuitByBan or circuitByBanAndVendor)
		
		initChargeTypeList0(targetListNo,arr[i].chargeTypeName);
		targetListNo++;
		hideLoadingModalLayer();
	}
}



function getBanLists0(no,data,vendorId,b,b1){
	var obj1 = data[0];
	var initBanVendorIsEmpty=0;
		for (var key0 in obj1) {
			initBanVendorIsEmpty=1;
			if(vendorId==key0){
				var count=0;
				var BanList0=obj1[key0];
				var results = BanList0;
				var total = BanList0.length;
				var banList = {};
				banList["results"]=results;
				banList["total"]=total;
				for(var i=0;i<banList["results"].length;i++){
					if(banList["results"][i].name==b1){
						initBanListFlex0(no,banList);
						allBan[no].setValue(b1);
						count=1;
						break;
					}
				}
				if(count!=1){
					initBanListFlex0(no,banList);
//					allBan[no].setName("all");
					allBan[no].setName(b1);
					break;
				}
				if(count==1){
					break;
				}
			}else{
				var banList = {
						"results":[
						          {
						        	  "id":"all",
						        	  "name":""
						          }
						          
						          ]
						
				}
				initBanListFlex0(no,banList);
				allBan[no].setName(b1);
			}
		}
		if(initBanVendorIsEmpty==0){
			var banList = {
					"results":[
					          {
					        	  "id":"all",
					        	  "name":""
					          }
					          
					          ]
					
			}
			initBanListFlex0(no,banList);
			allBan[no].setName(b1);
		}
}

function updateDropdownList0(data, keyName, valueName, ddlId, selIndex, header){
	var selo = YAHOO.util.Dom.get(ddlId);
	while(selo.length>0) 
		selo.remove(0);
	if(header!=null) selo.options[selo.options.length] = new Option("All", "all");
	selo.options[selo.options.length] = new Option(header.value, header.key);
	for(var i = 0; i<data.length; i++){
		if(eval('data[i].'+valueName)==header.value&&eval('data[i].'+keyName)==header.key){
			continue;
		}
		selo.options[selo.options.length]=new Option(eval('data[i].'+valueName),eval('data[i].'+keyName));
	}
	selo.selectedIndex = 1;
	return true;
}

function getCircutLists0(no,data0,data1,BanId,b,b1){
		var obja= data0[0];
		var circuitBanIsEmpty=0;
		for(var key0 in obja){
			var circuitBanIsEmpty=1;
			if(key0==BanId){
				var count =0;
				var Circuit0=obja[key0];
				for(var i =0;i<Circuit0.length;i++){
					if(b1==eval('Circuit0[i].stripped_circuit_number')){
						updateDropdownList0(Circuit0, "stripped_circuit_number", "stripped_circuit_number", 'circuitList'+no,0, {key:b,value:b1});
						count=1;
						break;
					}
				}
				if(count!=1){
					updateDropdownList0(Circuit0, "stripped_circuit_number", "stripped_circuit_number", 'circuitList'+no,1, {key:-1,value:b1});
				    break;
				}
				if(count==1){
					break;
				}
			}else{
				updateDropdownList0([], "id", "no", 'circuitList'+no,0, {key:"-1",value:b1});
			}
		}
		if(circuitBanIsEmpty==0){
			updateDropdownList0([], "id", "no", 'circuitList'+no,0, {key:b,value:b1});
		}
}

function initChargeTypeList0(no,chargeTypeName){
	if($("#circuitList"+no).val!="all"){
		$("#chargetTypeList"+no).html(allOptions);
			if($("#circuitList"+no).val() != "all"){
				var options = $("#chargetTypeList"+no).html().split("</OPTION>");
				for(var i=0;i< options.length; i++){
					if(i>8 && options[i] !=""){
						$("#chargetTypeList"+no+" option[value="+i+"]").remove();
					}
				}
			}
		
		$("#chargetTypeList"+no+"  option").each(function(i,o){
				if($(o).html()==chargeTypeName){
					o.selected=true;
				}
			});
	}else{
		$("#chargetTypeList"+no).html(allOptions);
		$("#chargetTypeList"+no+"  option").each(function(i,o){
				if($(o).html()==chargeTypeName){
					o.selected=true;
				}
			});
	}
	
}

function SelectValue(objSelect,strValue){
	 if (strValue=="") return;
	 	for(i=0;i<objSelect.options.length;i++){  
		   if(objSelect.options[i].value==strValue){
		       objSelect.options[i].selected=true;   
				 break;  
		    } 
		}
}

function uploadCallbackInput(a) {
	if(""!=a){
		alert(a);
	}else{
		alert('The system suports Excel files only.');
	}
}

function initVenderList2(No,name,banName){
	allVender[No] = $('#venderList'+No).flexbox(VL_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 310,
		customFlag : true,
		onSelect : function(){
			initBanListFlex(No);
		}
	});
	allVender[No].setName(name);
}

function initVenderList(No){
	allVender[No] = $('#venderList'+No).flexbox(VL_Array, {
			maxCacheBytes : 200000,
			highlightMatches : true,
			autoCompleteFirstMatch : false,
			paging: false,
			width : 310,
			customFlag : true,
			onSelect : function(){
				initBanListFlex(No);
			}
		});
	allVender[No].setValue("all");
	
}

function initBanListFlex(No,type,oldIndex){
	if($("#venderList"+No+"_hidden").val()=='all'| $("#venderList"+No+"_hidden").val()=='' ){
		$("#banByVendor"+No).empty();
		$("<div></div>").appendTo($("#banByVendor"+No)).attr("id","banList"+No);
		allBan[No]=$("#banList"+No).flexbox(BLD_Array,{
			maxCacheBytes : 200000,
			highlightMatches : true,
			autoCompleteFirstMatch : false,
			paging: false,
			width : 260,
			customFlag : true,
			onSelect : function(){
				getCircuitLists(No);
				$("#chargetTypeList"+No).empty();
				initChargeTypeList(No);
				
			}
		})
		if(1==type){
			allBan[No].setValue(allBan[oldIndex].getValue());
		}
		return;
	}else{
		var callback = {
				success: function(o){
					if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
						window.location.reload();
						return;
					}
					var data = eval("("+o.responseText+")");
					$("#banByVendor"+No).empty();
					$("<div></div>").appendTo($("#banByVendor"+No)).attr("id","banList"+No);
					allBan[No]=$("#banList"+No).flexbox(data,{
						maxCacheBytes : 200000,
						highlightMatches : true,
						autoCompleteFirstMatch : false,
						paging: false,
						width : 260,
						customFlag : true,
						onSelect : function(){
							getCircuitLists(No);
							$("#chargetTypeList"+No).empty();
							initChargeTypeList(No);
						}
					})
					if(1==type){
						allBan[No].setValue(allBan[oldIndex].getValue());
					}
					delete data;
				},
				failure: showError
			};
			var pdata = "selVendorId=" + $("#venderList"+No+"_hidden").val();
			YAHOO.util.Connect.asyncRequest('POST' , "getBanListByVendorId0.action" , callback , pdata); 
	}
	
	
	
}

function initBanListFlex0(No,banList){
	if($("#venderList"+No+"_input").val()=='' ){
		$("#banByVendor"+No).empty();
		$("<div></div>").appendTo($("#banByVendor"+No)).attr("id","banList"+No);
		allBan[No]=$("#banList"+No).flexbox(BLD_Array,{
			maxCacheBytes : 200000,
			highlightMatches : true,
			autoCompleteFirstMatch : false,
			paging: false,
			width : 260,
			customFlag : true,
			onSelect : function(){
				getCircuitLists(No);
			}
		})
		return;
	}else{
		$("#banByVendor"+No).empty();
		$("<div></div>").appendTo($("#banByVendor"+No)).attr("id","banList"+No);
		allBan[No]=$("#banList"+No).flexbox(banList,{
			maxCacheBytes : 200000,
			highlightMatches : true,
			autoCompleteFirstMatch : false,
			paging: false,
			width : 260,
			customFlag : true,
			onSelect : function(){
				getCircuitLists(No);
			}
		});
	}
}

function getBanLists(selIndex,No,name,callBackfunc){
	if($("#venderList"+No+"_hidden").val()=='all'){
		updateDropdownList([], "id", "no", 'banList'+No,selIndex, {key:"all",value:"All"});
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
			updateDropdownList(data, "id", "no", 'banList'+No,selIndex, {key:"all",value:"All"});
			delete data;
		},
		failure: showError
	};
	var pdata = "selVendorId=" + $("#venderList"+No+"_hidden").val();
	YAHOO.util.Connect.asyncRequest('POST' , "getBanListByVendorId.action" , callback , pdata); 
}

function getCircuitLists(no,circuit){
	var banId = 0;
	if ($('#banList'+no+"_hidden").val() != "all"){
		banId = $('#banList'+no+"_hidden").val();
//		$("#chargetTypeList"+no).append(lastOptions);
	}else{
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
			updateDropdownList(data, "id", "no", 'circuitList'+no,0, {key:"all",value:"All"});
			if(circuit){
			   // $("#circuitList"+no).val(circuit); 
				var countNum = 0;
				var countFlag = true;
				$("#circuitList"+no+"  option").each(function(i,o){
					if($(o).html()==circuit){
						countFlag = false;
						o.selected=true;
					}
					++countNum;
					if((data.length+1) == countNum && countFlag  ){
						updateDropdownList0(data, "stripped_circuit_number", "stripped_circuit_number", 'circuitList'+no,1, {key:-1,value:circuit});
					}
				});
			}
			delete data;
		},
		failure: showError
	};
	var pdata = "banId=" +banId;
	YAHOO.util.Connect.asyncRequest('POST' , "getCircuitList.action" , callback ,pdata ); 
}

function initChargeTypeList(no){
	$("#chargetTypeList"+no).html(allOptions);
}

function onCircuitListChange(no){
	
	if($("#circuitList"+no).val() != "all"){
		var options = $("#chargetTypeList"+no).html().split("</OPTION>");
		for(var i=0;i< options.length; i++){
			if(i==1 && options[i]!=""){
				$("#chargetTypeList"+no+" option[value="+i+"]").remove();
			}
			if(i>8 && options[i] !=""){
				$("#chargetTypeList"+no+" option[value="+i+"]").remove();
			}
		}
	}else{
		$("#chargetTypeList"+no).append(lastOptions);
	}
}

function chooserightall(){
    var checked = document.getElementsByName("rightbox")[0].checked;
    var selects = document.getElementsByName("rightbox");
    for (var i = 1; i < selects.length; i++) {
        selects[i].checked = checked;
        if (selects[i].checked == true) {
        	rightState["" + selects[i].value] = true;
            $(selects[i]).parent().parent().addClass("hightlight");
        }
        else {
        	delete rightState["" + selects[i].value] ;
            $(selects[i]).parent().parent().removeClass("hightlight");
        }
    }
}

function calibrationCheckboxRight(o){
    if (o.checked == true) {
    	rightState["" + o.value] = true;
        $(o).parent().parent().addClass("hightlight");
    }
    else {
    	delete rightState["" + o.value];
        $(o).parent().parent().removeClass("hightlight");
    }
}

function copyTargets(){
	var newIndex = targetListNo;
	for(var a in rightState){
		//targetListNo will +1
		addNewTarget("copy");
		pastTargetValues(a,newIndex);
		newIndex++;
	}
}

function copyOldTargets(){
	var i=0;
	var s = "";
	for(var a in targetLeftState){
		s+="&targetList["+i+"].id="+a;
		i++;
	}
	if(i ==0 ){
		alert("Select target , please !");
		return;
	}
	requestCopyTargetList(s.replace("&",""));
}

function requestCopyTargetList(data){
	var callback = {
			success: function(o){
				if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
					window.location.reload();
					return;
				}
				var data = eval("("+o.responseText+")");
				
				for(var i = 0; i<data.length; i++){
					
					resetFormElementValue();
					document.getElementById('_gridDiv').style.display = "none";
					targetLeftState = {};
					document.getElementById('inputDateTable').style.display = "block";
					addNewTarget("copy from records");
					allVender[targetListNo-1].setValue(data[i].vendorId);
//					allBan[targetListNo-1].setValue(data[i].banId);
					var banId=data[i].banId;
					var circuit=data[i].circuit_number;
					getBanListsForCopy(targetListNo-1,banId,circuit);
					$("#periodFrom"+(targetListNo-1)).val(data[i].period_from);
					$("#periodTo"+(targetListNo-1)).val(data[i].period_to);
					$("#aps"+(targetListNo-1)).val(data[i].allowed_period_shift);
					$("#chargetTypeList"+(targetListNo-1)).val(data[i].charge_type_id);
					$("#ta"+(targetListNo-1)).val(data[i].target_amount);
					$("#ca"+(targetListNo-1)).val(data[i].change_amount);
					$("#cp"+(targetListNo-1)).val(data[i].change_percentage);
					$("#avp"+(targetListNo-1)).val(data[i].variation_percentage);
				}
				if(data.error){
					alert("Error: " + data.error);
					return;
				}
			},
			failure: showError
		};

		if(data == ""){return}
		YAHOO.util.Connect.asyncRequest('POST' , "queryCopyTargetList.action" , callback , data); 
}

function getBanListsForCopy(No,banId,circuit){
	if($("#venderList"+No+"_hidden").val()=='all'| $("#venderList"+No+"_hidden").val()=='' ){
		$("#banByVendor"+No).empty();
		$("<div></div>").appendTo($("#banByVendor"+No)).attr("id","banList"+No);
		allBan[No]=$("#banList"+No).flexbox(BLD_Array,{
			maxCacheBytes : 200000,
			highlightMatches : true,
			autoCompleteFirstMatch : false,
			paging: false,
			width : 260,
			customFlag : true,
			onSelect : function(){
				getCircuitLists(No);
			}
		})
		allBan[No].setValue(banId);
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
			
			$("#banByVendor"+No).empty();
			$("<div></div>").appendTo($("#banByVendor"+No)).attr("id","banList"+No);
			allBan[No]=$("#banList"+No).flexbox(data,{
				maxCacheBytes : 200000,
				highlightMatches : true,
				autoCompleteFirstMatch : false,
				paging: false,
				width : 260,
				customFlag : true,
				onSelect : function(){
					getCircuitLists(No);
				}
			})

			allBan[No].setValue(banId);
			getCircuitLists(No,circuit);
			delete data;
			
		},
		failure: showError
	};
	var pdata = "selVendorId=" + $("#venderList"+No+"_hidden").val();
	YAHOO.util.Connect.asyncRequest('POST' , "getBanListByVendorId0.action" , callback , pdata); 
}


function pastTargetValues(oldIndex,newIndex){
	allVender[newIndex].setValue(allVender[oldIndex].getValue());
	initBanListFlex(newIndex,1,oldIndex);
	$("#circuitList"+newIndex).html($("#circuitList"+oldIndex).html());
	$("#circuitList"+newIndex).val($("#circuitList"+oldIndex).val());
	$("#periodFrom"+newIndex).val($("#periodFrom"+oldIndex).val());
	$("#periodTo"+newIndex).val($("#periodTo"+oldIndex).val());
	$("#aps"+newIndex).val($("#aps"+oldIndex).val());
	$("#chargetTypeList"+newIndex).val($("#chargetTypeList"+oldIndex).val());
	$("#ta"+newIndex).val($("#ta"+oldIndex).val());
	$("#ca"+newIndex).val($("#ca"+oldIndex).val());
	$("#cp"+newIndex).val($("#cp"+oldIndex).val());
	$("#avp"+newIndex).val($("#avp"+oldIndex).val());
	
}

function saveTargetList(){
	var i=0;
	for(var a in rightState){
		i++
	}
	if(i==0){
		alert('Please select one or more records');
	}else{
		var callback = {
				success: function(o){
					if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
						window.location.reload();
						return;
					}
					var data = eval("("+o.responseText+")");
					if(data.s){
						alert("Save Success !!!");
					}
					if(data.error){
						alert("Error: " + data.error);
						return;
					}
					delete data;
				},
				failure: showError
			};
		   if(!TargetVerficatoinValidateHint()){return alert('Incorrect input in highlighted area(s).');};
		   if(!TargetVerficatoinTCC()){ return alert('please input target amount or change amount or change percentage.');}
			var pd = getVerficationTargetData();
			YAHOO.util.Connect.asyncRequest('POST' , "saveTarget.action" , callback , pd); 
	}
}



function validateFields(formName){
	var formatValid = FIC_checkForm(formName);
	return formatValid;
}

function inputValidate(input){
	input.value = input.value.replace(/\D/g,'');
	if($(input).val() < 0 || $(input).val() > 100){
		input.value = input.value.substr(0,(input.value.length - 1));
		return;
	}
}

function disInput(no,input){
	
	if(("ta"+no) != input.id && ("avp"+no) != input.id){
		$("#ta"+no).css("background","#ccc").val("");
		$("#avp"+no).css("background","#ccc").val("");
		$("#avp"+no).attr({ readonly: 'true' });
	}
	
	if(("ca"+no) != input.id){
		$("#ca"+no).css("background","#ccc").val("");
	}
	if(("cp"+no) != input.id){
		$("#cp"+no).css("background","#ccc").val("");
	}
	
	if(("ta"+no) == input.id || ("avp"+no) == input.id){
		$("#avp"+no).css("background","#FFFFFF").attr("disabled","");
		$("#ta"+no).css("background","#FFFFFF").attr("disabled","");
		$("#avp"+no).removeAttr("readonly");
	}else{
		$(input).css("background","#FFFFFF").attr("disabled","");
	}
}

function verificationTargets(){
	var callback = {
			success: function(o){
				if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
					window.location.reload();
					return;
				}
				var data = eval("("+o.responseText+")");
				showTargetResult(data);
				clearNewTargetList();
				if(data.error){
					alert("Error: " + data.error);
					return;
				}
				delete data;
			},
			failure: showError
		};
		var pd = getSaveTargetData(true);
		if(!pd){return}
		YAHOO.util.Connect.asyncRequest('POST' , "verificationTargets.action" , callback , pd); 
} 

function clearNewTargetList(){
	targetListNo = 0;
	rightState = {};
	$(".newTargetTR").remove();
}

function initResultWindow(){
	YAHOO.ccm.container.resultWindow = new YAHOO.widget.Dialog("resultWindow", {
        width: "110em",
        height:"50em",
        zIndex:99,
        fixedcenter: true,
        visible: false,
        constraintoviewport: true
    });
    YAHOO.ccm.container.resultWindow.render();
    YAHOO.ccm.container.resultWindow.show();
    YAHOO.ccm.container.resultWindow.hide();
    YAHOO.ccm.container.uploadWindow = new YAHOO.widget.Dialog("uploadWindow", {
        width: "40em",
        fixedcenter: true,
        visible: false,
        constraintoviewport: true
    });
    YAHOO.ccm.container.uploadWindow.render();
    YAHOO.ccm.container.uploadWindow.hide();
    
}

function showTargetResult(data){
	$(".results").remove();
	targetIdsForResultDown={};
	var html=""
	for(var i=0;i<data.length;i++){
		var result = data[i];
		targetIdsForResultDown[result.id]=true;
		html+=""+
		'<TR style="" class="results" >'+ 
		"<td>"+result.vendor+"</td>"+
		"<td>"+result.ban+"</td>"+
		"<td>"+result.circuit_number+"</td>"+
		"<td>"+result.charge_type_name+"</td>"+
		"<td>"+result.target_amount+"</td>"+
		"<td>"+result.change_amount+"</td>"+
		"<td>"+result.change_percentage+"</td>"+
		"<td>"+result.targetPeriod+"</td>"+
		"<td>"+result.allowed_period_shift+"</td>"+
		"<td>";
		var titles = result.titles.split("!");
		var flags = result.flags.split("!");
		var creates=result.create.split("!");
		var createdates=result.createdate.split("!");
		for(var j=0;j<titles.length;j++){
			if(titles[j] != ""){
				html+=titles[j]+":"+flags[j]+"</br>"	
//				html+=titles[j]+":"+flags[j]+":"+creates[j]+":"+createdates[j]+"</br>"	
			}			
		}
		html+="</td>"+
		"<td>"+result.result+"</td>"+
		'</TR>';
	}

	$("#resultList").append(html);
	YAHOO.ccm.container.resultWindow.show();
}

function DownLoadResult(){
	showLoadingModalLayer();
	var postData = "titles=id&titles=target_id&titles=period&titles=allowed_period_shift&titles=actual_amount&titles=actual_change_amount" +
			"&titles=actual_change_percentage&titles=actual_variation_percentage&titles=result_flag&titles=remark&" +
			"titles=created_timestamp&titles=created_by&titles=modified_timestamp&titles=modified_by";
	       
	for(var id in targetIdsForResultDown){
		postData+="&targetIds="+id
	}
	
	var uri = "downLoadResultToExcel.action" + "?"+ postData;
	windowOpen(uri);
	hideLoadingModalLayer();
}

function closeResultWindow(){
	YAHOO.ccm.container.resultWindow.hide();
}

function actualResults(){
	var pd="";
	for(var id in targetLeftState){
		pd+="&targetIds="+id;
	}
	pd = pd.replace("&", "");
	var callback = {
			success: function(o){
				if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
					window.location.reload();
					return;
				}
				var data = eval("("+o.responseText+")");
				showTargetResult(data);
				if(data.error){
					alert("Error: " + data.error);
					return;
				}
				
				delete data;
			},
			failure: showError
		};
		
		if("" == pd){return alert('Please select one or more records')}
		YAHOO.util.Connect.asyncRequest('POST' , "actualResults.action" , callback , pd); 
}

function verificationOldTargets(){
	var pd="";
	for(var id in targetLeftState){
		pd+="&targetIds="+id;
	}
	var callback = {
			success: function(o){
				if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
					window.location.reload();
					return;
				}
				var data = eval("("+o.responseText+")");
				showTargetResult(data);
				if(data.error){
					alert("Error: " + data.error);
					return;
				}
				
				delete data;
			},
			failure: showError
		};
		if("" == pd){return}
		YAHOO.util.Connect.asyncRequest('POST' , "verificationOldTargets.action" , callback , pd); 
}

function upLoadShow(){
	var upload_div=document.getElementById("upload_div");
	upload_div.innerHTML=upload_div.innerHTML;
	YAHOO.ccm.container.uploadWindow.show();
}

function deleteTarget(){
	var i=1;
	for(var a in rightState){
		$("#targetTr"+a).remove();
		delete rightState[a];
		i++;
		//--targetListNo;
	}
	if(i==1){
		alert('Please select one or more records');
	}
}


//verficaton��ť
function verificationNoSave(){
	var i=0;
	for(var a in rightState){
		i++
	}
	if(i==0){
		alert('Please select one or more records');
	}else{	
		var callback = {
				success: function(o){
					if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
						hideLoadingModalLayer();
						window.location.reload();
						return;
					}
					var data = eval("("+o.responseText+")");
					hideLoadingModalLayer();
					showTargetResult(data);
					if(data.error){
						alert("Error: " + data.error);
						return;
					}
					delete data;
				},
				failure: showError
			};
			if(!TargetVerficatoinValidateHint()){ return alert('Incorrect input in highlighted area(s).');}
			if(!TargetVerficatoinTCC()){ return alert('please input target amount or change amount or change percentage.');}
			var pd = getVerficationTargetData(true);
			showLoadingModalLayer();
			YAHOO.util.Connect.asyncRequest('POST' , "verificationNoSave.action" , callback , pd); 
	}
}
function TargetVerficatoinTCC(){
	var validataFlag=true;
	for(var i in rightState){
	if($("#ta"+i).val()=='' && $("#ca"+i).val()== '' && $("#cp"+i).val()== ''){
	validataFlag = false;
	}
	}
	return validataFlag;
	}

//verfication��ť����֤��Ϣ
function getVerficationTargetData(verifiFlag){
	var s="";
	var i=0;
	for(var a in rightState){
		if(verifiFlag){
			if(!rightState.hasOwnProperty(a)){
				continue;
			}
		}
		if($("#periodFrom"+a).val() ==undefined){
			continue;
		}
		
		if(allVender[a].getValue() != 'all'){
			s+="&targetList2["+i+"].vendorId="+allVender[a].getValue();
		}
		
		if(allBan[a].getValue() != 'all'){
			s+="&targetList2["+i+"].banId="+allBan[a].getValue();
		}
		
		if($("#circuitList"+a).val() != 'all'){
			s+="&targetList2["+i+"].strippedCircuitNumber="+$("#circuitList"+a).find("option:selected").text();
		}
		
		if($("#periodFrom"+a).val() != ''){
			s+="&targetList2["+i+"].periodFrom="+$("#periodFrom"+a).val();
		}
		if($("#periodTo"+a).val() != ''){
			s+="&targetList2["+i+"].periodTo="+$("#periodTo"+a).val();
		}
		if($("#aps"+a).val() != ''){
			s+="&targetList2["+i+"].allowedPeriodShift="+$("#aps"+a).val();
		}
		if($("#chargetTypeList"+a).val() != 'all'){
			s+="&targetList2["+i+"].chargeTypeId="+$("#chargetTypeList"+a).val();
		}		
		if($("#ta"+a).val() != ''){
			s+="&targetList2["+i+"].targetAmount="+$("#ta"+a).val();
		}
		if($("#ca"+a).val()!= ''){
			s+="&targetList2["+i+"].changeAmount="+$("#ca"+a).val();
		}
		if($("#cp"+a).val() != ''){
			s+="&targetList2["+i+"].changePercentage="+$("#cp"+a).val();
		}
		if($("#avp"+a).val() != ''){
			s+="&targetList2["+i+"].allowedVariationPercentage="+$("#avp"+a).val();
		}
		if($("#source"+a).val() != ''){
			s+="&targetList2["+i+"].source="+$("#source"+a).val();
		}
		i++;
	}
	return s.replace("&", "");
}



//暂时无用
function verificationNoSaveProcedure(){
	var i=0;
	for(var a in rightState){
		i++
	}
	if(i==0){
		alert('Please select one or more records');
	}else{
		var callback = {
				success: function(o){
					if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
						window.location.reload();
						return;
					}
					var data = eval("("+o.responseText+")");
					showTargetResult(data);
					if(data.error){
						alert("Error: " + data.error);
						return;
					}
					delete data;
				},
				failure: showError
			};
			var pd = getSaveTargetData2(true);
			if(!pd){return alert('Please amend the red point and save the content of the style');}
			YAHOO.util.Connect.asyncRequest('POST' , "verificationNoSaveProcedure.action" , callback , pd); 
	}
}

//暂时无用
function getSaveTargetData(verifiFlag){
	var s="";
	for(var i=0;i<targetListNo;i++){
		if(verifiFlag){
			if(!rightState.hasOwnProperty(i)){
				continue;
			}
		}
		if($("#periodFrom"+i).val() ==undefined){
			continue;
		}
		
		if(allVender[i].getValue() != 'all'){
			s+="&targetList["+i+"].vendorId="+allVender[i].getValue();
		}
		
		if(allBan[i].getValue() != 'all'){
			s+="&targetList["+i+"].banId="+allBan[i].getValue();
		}
		
		if($("#circuitList"+i).val() != 'all'){
			s+="&targetList["+i+"].strippedCircuitNumber="+$("#circuitList"+i).find("option:selected").text();
		}

		if($("#periodFrom"+i).val() != ''){
			s+="&targetList["+i+"].periodFrom="+$("#periodFrom"+i).val();
		}
		if($("#periodTo"+i).val() != ''){
			s+="&targetList["+i+"].periodTo="+$("#periodTo"+i).val();
		}
		if($("#aps"+i).val() != ''){
			s+="&targetList["+i+"].allowedPeriodShift="+$("#aps"+i).val();
		}
		if($("#chargetTypeList"+i).val() != 'all'){
			s+="&targetList["+i+"].chargeTypeId="+$("#chargetTypeList"+i).val();
		}		
		if($("#ta"+i).val() != ''){
			s+="&targetList["+i+"].targetAmount="+$("#ta"+i).val();
		}
		if($("#ca"+i).val()!= ''){
			s+="&targetList["+i+"].changeAmount="+$("#ca"+i).val();
		}
		if($("#cp"+i).val() != ''){
			s+="&targetList["+i+"].changePercentage="+$("#cp"+i).val();
		}
		if($("#avp"+i).val() != ''){
			s+="&targetList["+i+"].allowedVariationPercentage="+$("#avp"+i).val();
		}
		if($("#source"+i).val() != ''){
			s+="&targetList["+i+"].source="+$("#source"+i).val();
		}
	}
	return s.replace("&", "");
}

