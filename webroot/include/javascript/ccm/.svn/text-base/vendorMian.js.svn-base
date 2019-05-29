var vendorMianUpdateId="";

YAHOO.util.Event.onDOMReady(function () {
	deletedisabled();
	searchdVendorMain(0);

//	searchdContactMain(0);
//	addConcate();
//	addVendorConcate();
//	editdConcate();
	$("#vendorContactManagementDiv").hide();
	
});

function deleteTariff(tariffLinkId) {
	if(!confirm("Are you sure you want to delete this attachment file?")){return false;}
	var callback = {
		success: function(o){
			if(tiffPage != null && typeof(tiffPage) != "undefined"){
				tiffPage.reload();
			}
			if(createVendorTraffPage != null && typeof(createVendorTraffPage) != "undefine")
				createVendorTraffPage.reload();
		},
		failure:showError
	};
	var data = "tariffLink.id="+tariffLinkId;
	YAHOO.util.Connect.asyncRequest("POST","deleteTariffOfVendorCircuit.action", callback, data);
};

function downloadFile(name,path){
	var df = document.getElementById("downloadForm");
	df.filePath.value = path;
	df.fileName.value = name;
	df.submit();
}
function verifyContact(){
	if(!validateemail()) return;
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
			if(data.count != 0){
				alert("This contact is already exist!");
				return;
			}
			document.getElementById("doAddContact_action_from").submit();
		},
        failure: showError
    };
    var getVerifyContactData = getVerifyContactValue();
    YAHOO.util.Connect.asyncRequest("POST", "doVerifyContact.action", callback, getVerifyContactData);
}

function getVerifyContactValue(){
	return contactdata = "cvo.address1=" + $('#cvoaddress1').val() +
					 "&cvo.city=" + $('#cvocity').val() +
					 "&cvo.province=" + $('#cvoprovincee').val() +
					 "&cvo.country=" + $('#cvocountry').val() +
					 "&cvo.postalCode=" + $('#cvopostalCode').val() +
					 "&cvo.primaryPhone=" + $('#cvoprimaryPhone').val() +
					 "&cvo.vendor=" +form0_vendorMain_vendorId.getValue()+
					 "&cvo.email=" + $('#cvoemail').val();
}

//Layer switch function
function createNewVendorPage(){
	$('#vendorSearch').fadeOut(0);
	$('#createNewVendor').fadeIn(0);
	clearVendorTxt();
	clearVendor();
}

function toSearchVendor(){
	$('#vendorSearch').fadeIn(0);
	$('#createNewVendor').fadeOut(0);
	//clearVendorTxt();
	searchdVendorMain(0);
	searchdTraff(0);
	//searchdContactMain(0);
	$("#vendorContactManagementDiv").hide();
}

function canVendor(){
	var url = window.location.toString();
	url = url.substring(0,url.indexOf("action")+6) + "?adminTabActiveIndex=5";
	window.location = url;
}

//Model layer
function uploadTariff(){
	YAHOO.ccm.container.uploadTariffWindow = new YAHOO.widget.Dialog("uploadTariffWindow", 
			{ width : "35em",
			  fixedcenter : true,
			  visible : false, 
//			  modal : true,
			  constraintoviewport : true
			});
	YAHOO.ccm.container.uploadTariffWindow.render();
}
//Model layer
function addConcate(){
	YAHOO.ccm.container.addConcatePanel = new YAHOO.widget.Panel("newConcate", {
        width: "450px",
        fixedcenter: true,
        visible: false, 
        constraintoviewport: true,
        close: true,
        modal: true
    });
	YAHOO.ccm.container.addConcatePanel.render();
	YAHOO.ccm.container.addConcatePanel.hide();
}
//Model layer
function addVendorConcate(){
	YAHOO.ccm.container.addVendorConcatePanel = new YAHOO.widget.Panel("newVendorConcate", {
        width: "450px",
        fixedcenter: true,
        visible: false,
        constraintoviewport: true,
        close: true,
        modal: true
    });
	YAHOO.ccm.container.addVendorConcatePanel.render();
	YAHOO.ccm.container.addVendorConcatePanel.hide();
}
//Model layer
function editdConcate(){
	YAHOO.ccm.container.editConcatePanel = new YAHOO.widget.Panel("editConcate", {
        width: "450px",
        fixedcenter: true,
        visible: false,
        constraintoviewport: true,
        close: true,
        modal: true
    });
	YAHOO.ccm.container.editConcatePanel.render();
	YAHOO.ccm.container.editConcatePanel.hide();
}
//Validation load list
function vendorNumberValue(vendorValues){
	document.getElementById("deletedisplay").disabled=false;
	document.getElementById("btnSaveUpdateVendorId").disabled=false;
	document.getElementById("btnCancelByVendor").disabled=false;
	document.getElementById("btnClear").disabled=false;
	document.getElementById("saveExcel").disabled=false;
	document.getElementById("addvendor").disabled=false;
	if(vendorValues=="null"){
		alert("Please select a correct Vendor!");
		return;
	}
	vendorMianUpdateId = vendorValues;
	searchdTraff(vendorValues);
	searchdVendorMain(vendorValues);
	document.getElementById("vendorTxtUpdate").style.display="block";
	document.getElementById("VL_vendorMainList_1").style.display="none";
	document.getElementById("vendorTxtUpdate").value=form0_vendorMain_vendorId.getName();
}
//
function showContact(){
	document.getElementById("tariffLi").title="";
	document.getElementById("tariffLi").className="";
	document.getElementById("contactLi").title="active";
	document.getElementById("contactLi").className="selected";
	document.getElementById("Tariff").style.display="none";
	document.getElementById("contact").style.display="block";
	
}
function showTariff(){
	

	document.getElementById("contactLi").title="";
	document.getElementById("contactLi").className="";
	document.getElementById("tariffLi").title="active";
	document.getElementById("tariffLi").className="selected";
	document.getElementById("contact").style.display="none";
	document.getElementById("Tariff").style.display="block";
	setTimeout(function(){
		document.body.scrollTop = 0;
		$("#main").scrollTop(0);
	},500);
	
}
function showCreateVendorContact(){
	document.getElementById("createVendorTariffLi").title="";
	document.getElementById("createVendorTariffLi").className="";
	document.getElementById("createVendorContactLi").title="active";
	document.getElementById("createVendorContactLi").className="selected";
	document.getElementById("createVendorTariff").style.display="none";
	document.getElementById("createVendorToContact").style.display="block";
	
}
function showCreateVendorTariff(){
	
	document.getElementById("createVendorContactLi").title="";
	document.getElementById("createVendorContactLi").className="";
	document.getElementById("createVendorTariffLi").title="active";
	document.getElementById("createVendorTariffLi").className="selected";
	document.getElementById("createVendorToContact").style.display="none";
	document.getElementById("createVendorTariff").style.display="block";
	
}
//Search Contact
function searchdVendorMain(theValue){
    VendorMainPage = new SANINCO.Page("vendorMainTable", "VendorMainPage", {
        sortingField: "contact.first_name",
        sortingDirection: "asc",
        vo: "cvo",
        recordText: '',
		paginationDiv: "vendoMainr_pageTable",
        recPerArray: [5, 10, 15, 20, 30],
        totalPageUri: "doVendorMainTotalPageNo.action",
        dataUri: "doSearchVendorMain.action",
        scrollTop:true,
        cols: [ 
				{title: "Delete",dataField: function(obj){
             				    return "<img src='include/images/reject16.png' onclick=\"deleteContact('" + obj.id + "')\"/>";	
            			}
      		  },{title: "Edit",dataField: function(obj){		
								return "<img src='include/images/Edit-icon-s.png' onclick=\"editContactShow(" + "'" + obj.id + "','" + ccmEscape(obj.first_name) + "','" + ccmEscape(obj.last_name) + "','" + obj.attention_name + "','" + obj.co_name + "','" + ccmEscape(obj.address_1) + "','" + ccmEscape(obj.address_2) + "','" + ccmEscape(obj.city) + "','" + obj.country + "','" + obj.postal_code + "','" + obj.primary_phone + "','" + obj.other_phone + "','" + obj.office_phone + "','" + obj.cell_phone + "','" + obj.fax_number + "','" + obj.email + "'" + ")\" >";
						}
      		  },{title: "First Name",dataField: "first_name",sort: "contact.first_name"
      		  },{title: "Last Name ",dataField: "last_name",sort: "contact.last_name"
      		  },{title: "Attention Name",dataField: "attention_name",sort: "contact.attention_name"
      		  },{title: "Co Name",dataField: "co_name",sort: "contact.co_name"
      		  },{title: "Address_1",dataField: "address_1",sort: "contact.address_1"
      		  },{title: "Address_2",dataField: "address_2",sort: "contact.address_2"
      		  },{title: "City",dataField: "city",sort: "contact.city"
      		  },{title: "Province",dataField: "province",sort: "contact.province"
      		  },{title: "Country",dataField: "country",sort: "contact.country"
      		  },{title: "Postal Code",dataField: "postal_code",sort: "contact.postal_code"
      		  },{title: "Primary Phone",dataField: "primary_phone",sort: "contact.primary_phone"
      		  },{title: "Other Phone",dataField: "other_phone",sort: "contact.other_phone"
      		  },{title: "Office Phone",dataField: "office_phone",sort: "contact.office_phone"
      		  },{title: "Cell Phone",dataField: "cell_phone",sort: "contact.cell_phone"
      		  },{title: "Fax Number",dataField: "fax_number",sort: "contact.fax_number"
      		  },{title: "Email", dataField: "email",sort: "contact.email"
        }]
    });
    VendorMainPage.voParam = {
        vendor : theValue
    };
    VendorMainPage.start();
}

function searchdTraff(theValue){

	tiffPage = new SANINCO.Page("TarrfTable","tiffPage",{
	      sortingField : "tariff_link.tariff_name",
	      recordText: '', 
	      sortingDirection : "asc",
	      vo : "cvo",
//	      paginationDiv: "Tarrf_pageTable",
	      recPerArray : [5,10,15,20,30],
	      dataUri : "doSearchTarrf.action",
	      totalPageUri: "doTariffsTotalPageNo.action",
	      scrollTop: true,
	      cols : [
		      {
		          title : "Tariff Name",
		          dataField: "tariff_name" ,
		          sort : "tariff_link.tariff_name"
		      },{
		          title : "Created Time",
		          dataField: "created_timestamp",
		          sort : "tariff_link.created_timestamp"
		      },{
		          title : "Modified Time",
		          dataField: "modified_timestamp" ,
		          sort : "tariff_link.modified_timestamp"
		      },{
		          title : "Notes",
		          dataField: "notes" ,
		          sort : "tariff_link.notes"
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
	                return "<img  src=\"include/images/download1.gif\"  style=\"cursor: pointer;\"  onClick=\"downloadFile(" + "'" + obj.tariff_name + "','" + obj.tariff_path + "'" + ");\">";
	            }
		      }
	      ]
	  });
	
	  tiffPage.voParam = {
		        vendor : theValue
		    };
	  tiffPage.start();
}

function searchCreateVendorTraff(theValue){
	
	createVendorTraffPage = new SANINCO.Page("createVendorTarrfTable","createVendorTraffPage",{
		sortingField : "tariff_link.tariff_name",
		recordText: '', 
		sortingDirection : "asc",
		vo : "cvo",
//	      paginationDiv: "Tarrf_pageTable",
		recPerArray : [5,10,15,20,30],
		dataUri : "doSearchTarrf.action",
		totalPageUri: "doTariffsTotalPageNo.action",
		cols : [
		        {
		        	title : "Tariff Name",
		        	dataField: "tariff_name" ,
		        	sort : "tariff_link.tariff_name"
		        },{
		        	title : "Created Time",
		        	dataField: "created_timestamp",
		        	sort : "tariff_link.created_timestamp"
		        },{
		        	title : "Modified Time",
		        	dataField: "modified_timestamp" ,
		        	sort : "tariff_link.modified_timestamp"
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
		        	return "<img  src=\"include/images/download1.gif\"  style=\"cursor: pointer;\"  onClick=\"downloadFile(" + "'" + obj.tariff_name + "','" + obj.tariff_path + "'" + ");\">";
		        }
		        }
		        ]
	});
	
	createVendorTraffPage.voParam = {
			vendor : theValue
	};
	createVendorTraffPage.start();
}

function getVendorMainPageList(id){
	if(id=="null"){
		return;
	}
    var callback = {
        success: vendorMainPageListcallback,
        failure: showError
    };
    var getVendorData = "vendorMainId=" + id;
    YAHOO.util.Connect.asyncRequest("POST", "doVendorSearchList.action", callback, getVendorData);
}

//Data aggregation, and bind to the page
function vendorMainPageListcallback(o){
    var data = eval("(" + o.responseText + ")");
    document.getElementById("statusTimeTxt").innerText = data.statusTimestamp;
    document.getElementById("vendorAcronymTxt").value = data.vendorAcronym;
    document.getElementById("createTimeTxt").innerText = data.createdTime;
    document.getElementById("summaryVendorNameTxt").value = data.summaryVendorName;
    document.getElementById("channelTxt").value = data.channel;
    document.getElementById("lpcRateTxt").value = data.lpcRate;
    document.getElementById("createdByTxt").innerText = data.createdBy;
    document.getElementById("vendorStatusSelect").value = data.vendorStatus;
    document.getElementById("modifiedTimeTex").innerText = data.modifiedTime;
    document.getElementById("modifiedByTxt").innerText = data.modifiedBy;
	vendorMianUpdateId=data.vendorId;
	document.getElementById("saveQueryButton").disabled=false;
	$("#vendorContactManagementDiv").show();
	showContact();
	
//	vendorId = data.vendorId;
//	vendorName = data.vendorName;
	
//	jQuery("#linkToInvoice").show();
//	jQuery("#linkToInvoiceLabel").text("Link to this vendor");
	
}

function cancelVendorMain(){
	getVendorMainPageList(form0_vendorMain_vendorId.getValue());
	document.getElementById("vendorTxtUpdate").value=form0_vendorMain_vendorId.getName();
}

//update vendor
function updateVendorMain(){
	var vendorUpdataNewName=document.getElementById("vendorTxtUpdate").value;
	for(var i=0;i<VL_ArrayVendor.results.length;i++){
		if (form0_vendorMain_vendorId.getName() != vendorUpdataNewName) {
			if ((VL_ArrayVendor.results[i].name) == (vendorUpdataNewName)) {
				alert("Vendor Name is required !");
				return;
			}
		}
	}
	var vendorNameTxt=ccmEscape(document.getElementById("vendorTxtUpdate").value);
    var vendorAcronym=ccmEscape(document.getElementById("vendorAcronymTxt").value);
    var summartVendorName=ccmEscape(document.getElementById("summaryVendorNameTxt").value);
    var channel=ccmEscape(document.getElementById("channelTxt").value);
    var lpcRate=ccmEscape(document.getElementById("lpcRateTxt").value);
    var vendorStatusSelect=ccmEscape(document.getElementById("vendorStatusSelect").value);
	var vendorNameSelect=form0_vendorMain_vendorId.getValue();
	 var callback = {
        success: updateVendorMaincallback,
        failure: showError
    };
    var getUpdateVendorData = "vendorMainId=" + vendorMianUpdateId+"&"+"UpvendorvendorNameTxt="+vendorNameTxt+"&"+"UpvendorAcronym="+vendorAcronym+"&"+"UpsummartVendorName="+summartVendorName+"&"+"UpvendorStatusSelect="+vendorStatusSelect+"&"+"channel="+channel+"&"+"lpcRate="+lpcRate;
    if(vendorNameSelect=="null"){
		alert("Please select a vendor!");
		return;
	}
    var reg=/^[-\+]?\d+(\.\d+)?$/;
    if (lpcRate) {
    	if(!reg.test(lpcRate)) {
    		  alert("Lpc rate only allowed to enter the Numbers!");
       	   return ;
    	}
    	if (lpcRate.split('.')[1]) {
    		if (lpcRate.split('.')[1].length > 5) {
        		alert("Lpc rate maximum input 5 decimal places!");
        		return
        	}
    	}
    	
    }
    
	if((vendorAcronym=="")||(vendorStatusSelect=="")||(summartVendorName=="")){
		alert("VendorAcronym ,SummartVendorName and VendorStatus is required!");
		return;
	}
	YAHOO.util.Connect.asyncRequest("POST", "doUpdateVendor.action", callback, getUpdateVendorData);
	document.getElementById("saveQueryButton").disabled=true;
}

function updateVendorMaincallback(o){
	alert("Modify success!");
	var vendorTowName=document.getElementById("vendorTxtUpdate").value;
	for(var i=0;i<=VL_ArrayVendor.results.length;i++){	
		if(VL_ArrayVendor.results[i].value=form0_vendorMain_vendorId.getValue()){
			VL_ArrayVendor.results[VL_ArrayVendor.results.objectValueOf("id",form0_vendorMain_vendorId.getValue())].name=vendorTowName;
			break;
		}
	}
}

//delete contact
function deleteContact(id){
    if (!confirm("Are you sure you want to delete this contact?")) {
        return false;
    }
    var callback = {
        success: function(){VendorMainPage.reload();
        },
        failure: showError
    };
	var getDeleteContactId = "deleteConcateId=" + id;
	 YAHOO.util.Connect.asyncRequest("POST", "doDeleteContact.action",callback, getDeleteContactId);
}

//delete contact
function deleteVendor(){
    if (!confirm("Are you sure you want to delete this vendor?")) {
        return false;
    }
    var callback = {
        success: function(){
    
		   searchdVendorMain(0);	
			
        },
        failure: showError
    };
	var id=form0_vendorMain_vendorId.getValue();
	var getDeleteVendor = "vendorTowId=" + id;
	 YAHOO.util.Connect.asyncRequest("POST", "doDeleteVendor.action",callback, getDeleteVendor);
	 VL_ArrayVendor.results = VL_ArrayVendor.results.removeOf(VL_ArrayVendor.results.objectValueOf("id",form0_vendorMain_vendorId.getValue()));
	 document.getElementById("saveQueryButton").disabled=true;
	 clearVendorTxt();
}

//clear vendor page
function clearVendorTxt(){
	document.getElementById("statusTimeTxt").innerText = "";
    document.getElementById("vendorAcronymTxt").value = "";
    document.getElementById("createTimeTxt").innerText = "";
    document.getElementById("summaryVendorNameTxt").value = "";
    document.getElementById("channelTxt").value = "";
    document.getElementById("lpcRateTxt").value = "";
    document.getElementById("createdByTxt").innerText = "";
    document.getElementById("vendorStatusSelect").value = "1";
    document.getElementById("modifiedTimeTex").innerText = "";
    document.getElementById("modifiedByTxt").innerText = "";
	document.getElementById("vendorTxtUpdate").style.display="none";
	document.getElementById("VL_vendorMainList_1").style.display="block";
	form0_vendorMain_vendorId.setValue("null");

	searchdVendorMain(0);
	//searchdContactMain(0);
	$("#vendorContactManagementDiv").hide();
	deletedisabled();
}

//Pop-up model layer
function addContactShow(){
	clearTxtVendorMain();
	document.getElementById("tovendorid").value=vendorMianUpdateId;
	if(vendorMianUpdateId==""){
		alert("Please first choose a vendor!");
		return;
	}
	YAHOO.ccm.container.addConcatePanel.show();
}

function addVendorContactShow(){
	clearTxtNewVendor();
	YAHOO.ccm.container.addVendorConcatePanel.show();
}

//edit contact
function editContactShow(id,firstName,lastName,attentionName,coName,address_1,address_2,city,country,postalCode,primaryPhone,otherPhone,officePhone,cellPhone,faxNumber,email){
	document.getElementById("toContactId").value=id;
	document.getElementById("ecvofirstName").value=unescape(firstName);
	document.getElementById("ecvolastName").value=unescape(lastName);
	document.getElementById("ecvoattentionName").value=attentionName;
	document.getElementById("ecvocoName").value=coName;
	document.getElementById("ecvoaddress1").value=unescape(address_1);
	document.getElementById("ecvoaddress2").value=unescape(address_2);
	document.getElementById("ecvocity").value=unescape(city);
	document.getElementById("ecvocountry").value=country;
	document.getElementById("ecvopostalCode").value=postalCode;
	document.getElementById("ecvoprimaryPhone").value=primaryPhone;
	document.getElementById("ecvootherPhone").value=otherPhone;
	document.getElementById("ecvoofficePhone").value=officePhone;
	document.getElementById("ecvocellPhone").value=cellPhone;
	document.getElementById("ecvofaxNumber").value=faxNumber;
	document.getElementById("ecvoemail").value=email;
	YAHOO.ccm.container.editConcatePanel.show();
}

//save vendor
function saveVendorMain(){
	$('#saveButton').disable=false;
	var aVendorName = ccmEscape($.trim($('#eVendorNameTxt').val()));
    var aVendorAcronym = ccmEscape($.trim($('#eVendorAcronymTxt').val()));
    var aVendorStatus = ccmEscape($('#eVendorStatusTxt').val());
    var aSummartVendorName = ccmEscape($.trim($('#eSummartVendorNameTxt').val()));
    var aChannel = ccmEscape($.trim($('#eChannelTxt').val()));
    var aLpcRate = ccmEscape($.trim($('#eLpcRateTxt').val()));
	if((aVendorName=="")||(aVendorAcronym=="")||(aVendorStatus=="")||(aSummartVendorName=="")){
		alert("Vendor name or Vendor Acronym or Summary Vendor Name is empty!");
		return;
	}
	if(aChannel.length>6){
		alert("Channel too long, Maximum input six letters!");
		return;
	}
	
	var reg=/^[-\+]?\d+(\.\d+)?$/;
	
	if (aLpcRate) {
    	if(!reg.test(aLpcRate)) {
    		  alert("Lpc rate only allowed to enter the Numbers!");
       	   return ;
    	}
    	if (aLpcRate.split('.')[1]) {
    		if (aLpcRate.split('.')[1].length > 5) {
        		alert("Lpc rate maximum input 5 decimal places!");
        		return
        	}
    	}
    	
    }
   
	var vendorNewName=document.getElementById("eVendorNameTxt").value;
	for(var i=0;i<VL_ArrayVendor.results.length;i++){
		if((VL_ArrayVendor.results[i].name)==(vendorNewName)){
			alert("The Vendor Name already exists!");
			return;
		}
	}
	 var callback = {
        success: saveVendorMaincallback,
        failure: showError
    };
    var getSaveVendorData = "aVendorName=" + aVendorName+"&"+"aVendorAcronym="+aVendorAcronym+"&"+"aSummartVendorName="+aSummartVendorName+"&"+"aVendorStatus="+aVendorStatus+"&"+"aChannel="+aChannel+"&"+"aLpcRate="+aLpcRate;
    YAHOO.util.Connect.asyncRequest("POST", "doSaveVendor.action", callback, getSaveVendorData);
    $('#saveButton').disable=true;
}

function saveVendorMaincallback(o){
	
	 var data = eval("(" + o.responseText + ")");
	 getVendorCreateNewVendor(data.vendorId);
	 var callVendorName=document.getElementById("eVendorNameTxt").value;
	 alert("Created New Vendor:  "+callVendorName+"");
//     document.getElementById("createVendorToContact").style.display="block";
     $("#CreateVendorContactManagementDiv").show();
	 searchdContactMain();
//	 $("#vendorContactManagementDiv").show();
//	 toSearchVendor();
	 $("#vendorCircuitId").val(data.vendorId);
	 searchCreateVendorTraff(data.vendorId)
	 showCreateVendorContact();
//	 vendorNumberValue(data.vendorId);
//	 getVendorMainPageList(data.vendorId);
////     document.getElementById("createVendorToContact").style.display="block";
////	 searchdContactMain();
////	 $("#vendorContactManagementDiv").show();
	 var vendorNewName=document.getElementById("eVendorNameTxt").value;
	 VL_ArrayVendor.results.push({
		    "id": data.vendorId,
		    "name": ""+vendorNewName+""
		});
}

function submitUploadForm() {
	if (validateUploadForm()) {
		var uf = document.getElementById("uploadForm");
		uf.submit();
		YAHOO.ccm.container.uploadTariffWindow.hide();
	}
}

function cancelUploadForm() {
	YAHOO.ccm.container.uploadTariffWindow.hide();
}
function popupUploadTariffWindow() {
	// clean upload form
	$(".upload_text").val("");
	$("#uploadNotes").val("");
	clearFormUploadFiles("uploadForm");
	YAHOO.ccm.container.uploadTariffWindow.show();
}
var type = ["gif","jpg","jpeg","bmp","doc","ppt","xls","docx","pptx","xlsx","txt","png","pdf","tif"];

function uploadValidate (){
//	var type = fileType;
//	var uploads = document.getElementsByName("uploads");
	var boo = true;
//	var errorMessage = " Line ";
//	for(var i=0;i<uploads.length;i++){
	for(var i=1;i<6;i++){
		if($("#_upload_text_attachment_0"+i).val() != ""){
			var fileSplit = $("#_upload_text_attachment_0"+i).val().split(".");
			var fileType = fileSplit[fileSplit.length-1].toLowerCase();
			
			var typeBoo = 0;
			for(var j=0;j<type.length;j++){
				if(fileType == type[j]){
					typeBoo += 1;
				}
			}
			if(typeBoo != 1){
//				uploads[i].style.color="red";
				document.getElementById("_upload_text_attachment_0"+i).style.color="red";
				boo = false;
//				errorMessage += "["+(i)+"]";
			}else{
//				uploads[i].style.color="";
				document.getElementById("_upload_text_attachment_0"+i).style.color="";
			}
		}
	}
	if(boo){
		return true;
	}else{
//		errorMessage += " ,invalid file format.";
//		alert(errorMessage);
		alert("Please enter the following file format:.gif;.jpg;.bmp;.doc;.ppt;.xls;.docx;.pptx;.xlsx;.txt;.png;.pdf;.tif;.eml!");
		return false;
	}
}

function validateUploadForm() {
	if ($("#_upload_text_attachment_01").val() == "" &&
		$("#_upload_text_attachment_02").val() == "" &&
		$("#_upload_text_attachment_03").val() == "" &&
		$("#_upload_text_attachment_04").val() == "" &&
		$("#_upload_text_attachment_05").val() == "") {
		
		alert("Please at least upload a file!");
		return false;
	}else{
		return uploadValidate();
		
	}
	return true;
}
//search Contact
function searchdContactMain(){
    ContactPage = new SANINCO.Page("contactTable", "ContactPage", {
        sortingField: "contact.first_name",
        sortingDirection: "asc",
        vo: "cvo",
        recordText: '',
		paginationDiv: "contact_pageTable",
        recPerArray: [5, 10, 15, 20, 30],
        totalPageUri: "doContactVendorMainTotalPageNo.action",
        dataUri: "doSearchContactVendorMain.action",
        cols: [
				{title: "First Name",dataField: "first_name",sort: "contact.first_name"
      		  },{title: "Last Name ",dataField: "last_name",sort: "contact.last_name"
      		  },{title: "Attention Name",dataField: "attention_name",sort: "contact.attention_name"
      		  },{title: "Co Name",dataField: "co_name",sort: "contact.co_name"
      		  },{title: "Address_1",dataField: "address_1",sort: "contact.address_1"
      		  },{title: "Address_2",dataField: "address_2",sort: "contact.address_2"
      		  },{title: "City",dataField: "city",sort: "contact.city"
      		  },{title: "Province",dataField: "province",sort: "contact.province"
      		  },{title: "Country",dataField: "country",sort: "contact.country"
      		  },{title: "Postal Code",dataField: "postal_code",sort: "contact.postal_code"
      		  },{title: "Primary Phone",dataField: "primary_phone",sort: "contact.primary_phone"
      		  },{title: "Other Phone",dataField: "other_phone",sort: "contact.other_phone"
      		  },{title: "Office Phone",dataField: "office_phone",sort: "contact.office_phone"
      		  },{title: "Cell Phone",dataField: "cell_phone",sort: "contact.cell_phone"
      		  },{title: "Fax Number",dataField: "fax_number",sort: "contact.fax_number"
      		  },{title: "Email", dataField: "email",sort: "contact.email"
        }]
    });
    ContactPage.start();
}

//Clear form data
function clearVendor(){
	deletedisabled();
	document.getElementById("eVendorNameTxt").value="";
    document.getElementById("eVendorAcronymTxt").value="";
    document.getElementById("eSummartVendorNameTxt").value="";
    document.getElementById("eChannelTxt").value="";
    document.getElementById("eLpcRateTxt").value="";
    document.getElementById("eVendorStatusTxt").value="1";
	document.getElementById("createVendorToContact").style.display="none";
	document.getElementById("statusTimeTxtNew").innerText = "";
    document.getElementById("createTimeTxtNew").innerText = "";
    document.getElementById("createdByTxtNew").innerText = "";
    document.getElementById("modifiedTimeTexNew").innerText = "";
    document.getElementById("modifiedByTxtNew").innerText = "";
}

//delete disabled is ture
function deletedisabled(){
	if (form0_vendorMain_vendorId.getName()== "") {
		document.getElementById("deletedisplay").disabled=true;
		document.getElementById("btnSaveUpdateVendorId").disabled=true;
		document.getElementById("btnCancelByVendor").disabled=true;
		document.getElementById("btnClear").disabled=true;
		document.getElementById("saveExcel").disabled=true;
		document.getElementById("addvendor").disabled=true;
	}
}

function clearTxtVendorMain(){
	document.getElementById("cvofirstName").value="";
	document.getElementById("cvolastName").value="";
	document.getElementById("cvoattentionName").value="";
	document.getElementById("cvocoName").value="";
	document.getElementById("cvoaddress1").value="";
	document.getElementById("cvoaddress2").value="";
	document.getElementById("cvocity").value="";
	document.getElementById("cvocountry").value="";
	document.getElementById("cvopostalCode").value="";
	document.getElementById("cvoprimaryPhone").value="";
	document.getElementById("cvootherPhone").value="";
	document.getElementById("cvoofficePhone").value="";
	document.getElementById("cvocellPhone").value="";
	document.getElementById("cvofaxNumber").value="";
	document.getElementById("cvoprovincee").value="";
	document.getElementById("cvoemail").value="";
}
function clearTxtNewVendor(){
	document.getElementById("cvofirstName1").value="";
	document.getElementById("cvolastName1").value="";
	document.getElementById("cvoattentionName1").value="";
	document.getElementById("cvocoName1").value="";
	document.getElementById("cvoaddress11").value="";
	document.getElementById("cvoaddress21").value="";
	document.getElementById("cvocity1").value="";
	document.getElementById("cvocountry1").value="";
	document.getElementById("cvopostalCode1").value="";
	document.getElementById("cvoprimaryPhone1").value="";
	document.getElementById("cvootherPhone1").value="";
	document.getElementById("cvoofficePhone1").value="";
	document.getElementById("cvocellPhone1").value="";
	document.getElementById("cvofaxNumber1").value="";
	document.getElementById("cvoprovince1").value="";
	document.getElementById("newcvoemail1").value="";
}

//Cohntact Excel
downloadExcelUpVendorContact = function (){
	var titles = VendorMainPage.getTitlesParam("titles");
	window.location.href="doExcelByContact.action?"+ titles +"&"+ VendorMainPage.paramStr;
}
downloadExcelNewVendorContact = function (){
	var titles = "titles=First Name&titles=Last Name&titles=Attention Name&titles=Co Name&titles=Address_1&titles=Address_2&titles=City&titles=Country&titles=Postal Code&titles=Primary Phone&titles=Other Phone&titles=Office Phone&titles=Cell Phone&titles=Fax Number&titles=province&titles=Email";
	window.location.href="doExcelByNewContact.action?"+ titles +"&"+ ContactPage.paramStr;
}

function getVendorCreateNewVendor(id){
	if(id=="null"){
		return;
	}
    var callback = {
        success: createNewVendorListcallback,
        failure: showError
    };
    var getVendorData = "vendorMainId=" + id;
    YAHOO.util.Connect.asyncRequest("POST", "doVendorSearchList.action", callback, getVendorData);
}

//Data aggregation, and bind to the page
function createNewVendorListcallback(o){
    var data = eval("(" + o.responseText + ")");
	document.getElementById("statusTimeTxtNew").innerText = data.statusTimestamp;
    document.getElementById("createTimeTxtNew").innerText = data.createdTime;
    document.getElementById("createdByTxtNew").innerText = data.createdBy;
    document.getElementById("modifiedTimeTexNew").innerText = data.modifiedTime;
    document.getElementById("modifiedByTxtNew").innerText = data.modifiedBy;
}

//validation update by hongtao 2011-09-21
function validateemail(){
    var accemail1 = $("#cvoemail").val();
    if (accemail1 && !accemail1.match(/\w{1,}[@][\w\-]{1,}([.]([\w\-]{1,})){1,3}$/)){
		alert("Email, please re-enter format errors!");
		removeClassName('cvoemail','validation-passed');
		addClassName('cvoemail','validation-failed');
        return false;
	}else{
		removeClassName('cvoemail','validation-failed');
		addClassName('cvoemail','validation-passed');
        return true;
	}
}

function editValidateemail(){
	 var accemail1 = $("#ecvoemail").val();
    if (accemail1 && !accemail1.match(/\w{1,}[@][\w\-]{1,}([.]([\w\-]{1,})){1,3}$/)){
		alert("Email, please re-enter format errors!");
		removeClassName('ecvoemail','validation-passed');
		addClassName('ecvoemail','validation-failed');
        return false;
	}else{
		removeClassName('ecvoemail','validation-failed');
		addClassName('ecvoemail','validation-passed');
        return true;
	}
//    var accemail2 = document.getElementById("ecvoemail");
//    if (!accemail2.value == "") {
//        var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
//        var bool = reg.test(accemail2.value);
//        if (bool == false) {
//            alert("Email, please re-enter format errors!");
//            accemail2.focus();
//            return false;
//        }
//    }
}
function clearEmailError(id){
	removeClassName(id,'validation-failed');
	addClassName(id,'validation-passed');

}
function newValidateemail(){
	
	 var accemail1 = $("#newcvoemail1").val();
    if (accemail1 && !accemail1.match(/\w{1,}[@][\w\-]{1,}([.]([\w\-]{1,})){1,3}$/)){
		alert("Email, please re-enter format errors!");
		removeClassName('newcvoemail1','validation-passed');
		addClassName('newcvoemail1','validation-failed');
        return false;
	}else{
		removeClassName('newcvoemail1','validation-failed');
		addClassName('newcvoemail1','validation-passed');
        return true;
	}
//    var accemail3 = document.getElementById("newcvoemail1");
//    if (!accemail3.value == "") {
//        var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
//        var bool = reg.test(accemail3.value);
//        if (bool == false) {
//            alert("Email, please re-enter format errors!");
//            accemail3.focus();
//            return false;
//        }
//    }
}
