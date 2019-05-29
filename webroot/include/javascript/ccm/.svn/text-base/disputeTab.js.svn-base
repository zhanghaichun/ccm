/***
 * @author wei.su
 */
var invoicetabId;
var originatorListData;
var proposalDateLeft;
var proposalDateRight;
var pageNo = 1;
var recPerPage = 5;
var totalPageNo;
var totalResultNo;
var currentSortingFieldName = "created_timestamp";
var currentSortingDirection = "desc";
var previousPostedData = "";
var disputeListData = "";
var attachmentPointId = "";
var editProposalData = "";
leftState = {};
rightState = {};
// add by yinghe.fu
checkedata = {};
//Pageloading 
YAHOO.util.Event.onDOMReady(function(){
	
    YAHOO.util.Dom.removeClass("EditProposalLeftId", "EditProposalLeft-Id");
    YAHOO.ccm.container.EditProposalLeftId = new YAHOO.widget.Dialog("EditProposalLeftId", {
        width: "60em",
        fixedcenter: true,
        visible: false,
		modal: true,
        constraintoviewport: true
    });
    YAHOO.ccm.container.EditProposalLeftId.render();
    YAHOO.util.Dom.removeClass("UpLoadFileForDisputeTabRightDivId", "UpLoadFileForDisputeTabRightDiv-Id");
    YAHOO.ccm.container.UpLoadFileForDisputeTabRightDivId = new YAHOO.widget.Dialog("UpLoadFileForDisputeTabRightDivId", {
        width: "45em",
        fixedcenter: true,
		modal: true,
        visible: false,
        constraintoviewport: true
    });
    YAHOO.ccm.container.UpLoadFileForDisputeTabRightDivId.render();
	YAHOO.util.Dom.removeClass("scoaInfo", "scoaInfo");
    YAHOO.ccm.container.scoaInfo = new YAHOO.widget.Dialog("scoaInfo", {
        width: "20em",
        fixedcenter: true,
		modal: true,
        visible: false,
        constraintoviewport: true
    });
    YAHOO.ccm.container.scoaInfo.render();
	YAHOO.util.Dom.removeClass("deleteProposalPanelDiv", "deleteProposalPanelDiv");
    YAHOO.ccm.container.deleteProposalPanelDiv = new YAHOO.widget.Dialog("deleteProposalPanelDiv", {
        width: "35em",
        fixedcenter: true,
		modal: true,
        visible: false,
        constraintoviewport: true
    });
    YAHOO.ccm.container.deleteProposalPanelDiv.render();
	
	//alert(invoiceId);
    invoicetabId = invoiceId;//document.getElementById("disputeTabinvoiceId").value;
  
});

$(window).resize(function(){
  disputeSlideInit(false);
});

// Upload Method Star
function uploadClearAllValue(){
	clearFormUploadFiles('uploadFileForDisputeTabRightFrom');
}

var andf=3;
function addnewdisputeFile(){
	andf++;
	var string = "<div id =\"__up_load_text_invoiceDetail_AttachFile_Number"+andf+"\" style=\"PADDING-TOP: 3px;height: 19px;\"><div style=\"float:left;\">"
    + "<input style=\"height:19px;width:175px;\" type='text' id=\"__up_load_text_invoiceDetail_AttachFile"+andf+"\" disabled=\"disabled\">"
     +"</div><div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"
     +"<input id=\"__up_load_text_input_AttachFile"+andf+"\" onchange=\"document.getElementById('__up_load_text_invoiceDetail_AttachFile"+andf+"').value=this.value;\" class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
	+ "<input style=\"height:19px;width:60px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/></span></div>"
    + "<div class=\"approveicon\" onclick=\"deletefile('__up_load_text_invoiceDetail_AttachFile_Number"+andf+"');\"></div>"
    + "<div class=\"clear\" ></div></div>"
    
    $("#invoiceDetail_AttachFile").append(string);
}
function clearnewdisputeFile(){
	var length = $("#invoiceDetail_AttachFile :input").length/3;
	 $("#invoiceDetail_AttachFile").html("");
    for(var i =1;i<length+1;i++){
    	var string = "<div id =\"__up_load_text_invoiceDetail_AttachFile_Number"+i+"\" style=\"PADDING-TOP: 3px;height: 19px;\"><div style=\"float:left;\">"
        + "<input style=\"height:19px;width:175px;\" type='text' id=\"__up_load_text_invoiceDetail_AttachFile"+i+"\" disabled=\"disabled\">"
         +"</div><div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"
         +"<input id=\"__up_load_text_input_AttachFile"+i+"\" onchange=\"document.getElementById('__up_load_text_invoiceDetail_AttachFile"+i+"').value=this.value;\" class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
    	+ "<input style=\"height:19px;width:60px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/></span></div>"
        + "<div class=\"approveicon\" onclick=\"deletefile('__up_load_text_invoiceDetail_AttachFile_Number"+i+"');\"></div>"
        + "<div class=\"clear\" ></div></div>"
         
    		$("#invoiceDetail_AttachFile").append(string);
    }
}
//wenbo.zhang 15-04-20
var amf=3;
function addAttachmentManagementFile(){
	amf++;
	var string = "<div id =\"AttachmentManagementNumber"+amf+"\" style=\"padding:3px 0 0 0;height: 19px;\"><div style=\"float:left;\">"
                + "<input style=\"height:19px;width:190px;\" type='text' id=\"__up_load_text_invoiceDetail_AttachmentManagement_"+amf+"\" class=\"ccm_clear\" disabled=\"disabled\">"
	            + "</div><div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"	
                + "<input id=\"\" onchange=\"document.getElementById('__up_load_text_invoiceDetail_AttachmentManagement_"+amf+"').value=this.value;\" class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
		        + "<input style=\"height:19px;width:60px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/></span></div>"	   						
		        + "<div class=\"approveicon\" onclick=\"deletefile('AttachmentManagementNumber"+amf+"');\"></div><div class=\"clear\" ></div></div>";
	$("#AttachmentManagementFileId").append(string);
  
}
function clearUploadFiles(){
	
	var amfinput=$("#AttachmentManagementFileId :input").length/3;
	$("#AttachmentManagementFileId").html("");
	for(var i =1;i<amfinput+1;i++){
		var string = "<div id =\"AttachmentManagementNumber"+i+"\" style=\"padding:3px 0 0 0;height: 19px;\"><div style=\"float:left;\">"
        + "<input style=\"height:19px;width:190px;\" type='text' id=\"__up_load_text_invoiceDetail_AttachmentManagement_"+i+"\" class=\"ccm_clear\" disabled=\"disabled\">"
        + "</div><div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"	
        + "<input id=\"\" onchange=\"document.getElementById('__up_load_text_invoiceDetail_AttachmentManagement_"+i+"').value=this.value;\" class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
        + "<input style=\"height:19px;width:60px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/></span></div>"	   						
        + "<div class=\"approveicon\" onclick=\"deletefile('AttachmentManagementNumber"+i+"');\"></div><div class=\"clear\" ></div></div>";
$("#AttachmentManagementFileId").append(string);
	}
}
function AllclearUploadFiles(){
	$("#AttachmentManagementFileId").html("");
	for(var i =1;i<4;i++){
		var string = "<div id =\"AttachmentManagementNumber"+i+"\" style=\"padding:3px 0 0 0;height: 19px;\"><div style=\"float:left;\">"
        + "<input style=\"height:19px;width:190px;\" type='text' id=\"__up_load_text_invoiceDetail_AttachmentManagement_"+i+"\" class=\"ccm_clear\" disabled=\"disabled\">"
        + "</div><div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"	
        + "<input id=\"\" onchange=\"document.getElementById('__up_load_text_invoiceDetail_AttachmentManagement_"+i+"').value=this.value;\" class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
        + "<input style=\"height:19px;width:60px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/></span></div>"	   						
        + "<div class=\"approveicon\" onclick=\"deletefile('AttachmentManagementNumber"+i+"');\"></div><div class=\"clear\" ></div></div>";
$("#AttachmentManagementFileId").append(string);
	}
}

function validateIsTrue(){
	if (!invoiceDisputeActionRole) {
		alert('Invoice is not granted for change!');
		return false;
	}
	return true;
}
function validateTheSCOA(){
	if(account_code.getValue()==""){
		alert('Please choose SCOA!');
		return false;
	}
	return true;
}
function validateTheNotes(){
		var proposalDiscription = document.getElementById("proposalNote").value;
/*		if(proposalDiscription.replace(/[^\x00-\xff]/g,"xx").length<=10){
			alert("Your notes have to be more than 10 characters!");
			return false;
		} */
        if (proposalDiscription.length > 255) {
            alert("Notes may not have more than 255 chars!");
            return false;
        }
		return true;
}

$(function(){
	var slideWidth_dispute = YAHOO.util.Region.getRegion(document.body).width - 295;
    $("#slidesContainer_dispute").width(slideWidth_dispute);
    var slides = $(".slide_dispute");
	currentPosition_dispute = 0;
    $("#slidesContainer_dispute").css("overflow", "hidden");
    slides.wrapAll("<div id=\"slideInner_dispute\"></div>").css({
        "float": "left",
        "width": slideWidth_dispute
    });
    $("#slideInner_dispute").css("width", slideWidth_dispute * slides.length);
    $("#slideshow_dispute").prepend("<span class=\"control_dispute\" id=\"leftControl_dispute\">Clicking moves left</span>").append("<span class=\"control_dispute\" id=\"rightControl_dispute\">Clicking moves right</span>");
    function manageControls(position){
        if (position == 0) {
            $("#leftControl_dispute").hide();
        } else {
            $("#leftControl_dispute").show();
        }
        if (position == slides.length - 1) {
            $("#rightControl_dispute").hide();
        } else {
            $("#rightControl_dispute").show();
        }
    }
    manageControls(currentPosition_dispute);
    $(".control_dispute").bind("click", function(){
        // Determine new position
        currentPosition_dispute = ($(this).attr("id") == "rightControl_dispute") ? currentPosition_dispute + 1 : currentPosition_dispute - 1;
        // Hide / show controls
        manageControls(currentPosition_dispute);
        var slideWidth_dispute = $("#slidesContainer_dispute").width();
        $("#slideInner_dispute").animate({
            "marginLeft": slideWidth_dispute * (-currentPosition_dispute)
        });
    });
});

function disputeSlideInit(){
	if (window.currentPosition_dispute || window.currentPosition_dispute == 0) {
		var slideWidth_dispute = YAHOO.util.Region.getRegion(document.body).width - 295;
		$("#slidesContainer_dispute").width(slideWidth_dispute);
		var slides = $(".slide_dispute");
		slides.css("width", slideWidth_dispute);
	    $("#slideInner_dispute").css("width", slideWidth_dispute * slides.length);
		$("#slideInner_dispute").animate({
	        "marginLeft": slideWidth_dispute * (-currentPosition_dispute)
	    });
	} 
}

// Add By Chao.Liu to 10/07/21 PM
function initDisputeTabSearch(){
	invoiceDisputeActionRole = (SANINCO.ifAllGranted(Constants.FUNCTION.invoiceDisputeAction) && !judgementRights());
	initDisputeTab();
    getDisputeListByInvoiceIdLeft();
	checkRoleForAddProposal();
    disputeSlideInit(true);
	if(!invoiceDisputeActionRole){
		document.getElementById("leftDivOfDisputeClim").style.display="none";
		//document.getElementById('rightControl_dispute').style.display="none";
		var disputeDataTable = document.getElementById("_disputesDataTable").innerHTML;
		if(disputeDataTable==""){
			document.getElementById("leftControl_dispute").style.display="none";
		}else{
			document.getElementById("leftControl_dispute").style.display="block";
		}
	}else{
		document.getElementById("leftDivOfDisputeClim").style.display="block";
	}
}
function checkRoleForAddProposal(){
	if(!invoiceDisputeActionRole){
		document.getElementById("formSaveProposal").style.display="none";
		if(document.getElementById("updateProposalLeft")) {
			document.getElementById("updateProposalLeft").disabled=true;
		}
		var disputeSelects = document.getElementsByName("invoiceDisputeTabLeftRadio");
		for(var i=0;i<disputeSelects.length;i++){
			disputeSelects[i].disabled=true;
		}	
		document.getElementById("disputeSelectDiv").style.display="none";	
	}else{
		document.getElementById("formSaveProposal").style.display="block";
	}
}
function checkRoleForDisputeFileMannage(){
	if(!invoiceDisputeActionRole){
		document.getElementById("uploadFileForDisputeTabRightFrom").style.display="none";
		
	}else{
		document.getElementById("uploadFileForDisputeTabRightFrom").style.display="block";
	}
}
function checkRoleForDisputeClaim(disputeId){
    if (!invoiceDisputeActionRole) {
        document.getElementById("originatorListSelect" + disputeId + "").disabled=true;
        var shortPaidSelects = document.getElementsByName("invoiceDisputeTabRightRadioShortPaid" + disputeId + "");
		for(var i=0;i<shortPaidSelects.length;i++){
			shortPaidSelects[i].disabled=true;
		}
        var recurringSelects = document.getElementsByName("invoiceDisputeTabRightRadioRecurring" + disputeId + "");
        	for(var j=0;j<recurringSelects.length;j++){
			recurringSelects[j].disabled=true;
		}
    }
}
// Add By Chao.Liu to 10/07/21 PM
function initDisputeTab(){
    searchdDisputeTab();
    getDisputeAmountTotal();
	getDisputeListByInvoiceId_ready();
}
//commit data
function composePostDataFromUiDisputeTab(){
    var postData = ("svo.sortField=" + currentSortingFieldName + "&svo.sortingDirection=" + currentSortingDirection + "&svo.pageNo=" + pageNo + "&svo.recPerPage=" + recPerPage + "&svo.invoiceId=" + invoiceId);
    //var postData = ("svo.sortField=" + currentSortingFieldName + "&svo.sortingDirection=" + currentSortingDirection + "&svo.invoiceId=" + invoiceId);
    var str = postData;
    return str;
}

// Add By wei.su to 10/07/21 PM
function uploadSuccessCallbackLeft(){
/*	document.getElementById('__up_load_text_invoiceDetail_AttachFile1').value = "";
	document.getElementById('__up_load_text_invoiceDetail_AttachFile2').value = "";
	document.getElementById('__up_load_text_invoiceDetail_AttachFile3').value = "";*/
	clearnewdisputeFile()
	clearFormUploadFiles('formSaveProposal');
	account_code.setValue('');
    getDisputeAmountTotal();
    disputeTabPage.start();
	var f = document.getElementById('formSaveProposal');
	$(f['disputeAmount']).val('');
	$(f['cricuitNumber']).val('');
	$(f['serviceType']).val('');
	$(f['proposalDiscription']).val('');
	f['categoryId'].selectedIndex = 0;
	f['originatorId'].selectedIndex = 0;
}

// Add By wei.su to 10/07/21 PM
function uploadSuccessCallbackRight(){
    disputeTabPage.start();
	uploadClearAllValue();
}

// Add By wei.su to 10/07/21 PM
function uploadSuccessCallbackForEditProposal(){
	document.getElementById('__up_load_text_input_4').outerHTML += "";
	attachmentFileOfProposalTablePage.start();
	uploadClearAllValue();
}
// Add By wei.su to 10/07/21 PM
function uploadSuccessCallbackForEditDispute(){
     attachmentFileOfDisputeTablePage.start();
	 uploadClearAllValue();
}


//edit proposals
function updateEditProposal(o,id){
	clearTips();
	recordProposalBoxId = {};
	if(judgementRightsDispute("")) return;
	var proposalBoxId = id;

	//document.getElementById('__payment_radio').checked = false;
	document.getElementById('__dispute_radio').checked = true;
	document.getElementById('__notes').value = "";
	document.getElementById('__txt_proposalId').value = proposalBoxId;
	document.getElementById('__txt_proposalId_boxId').value = proposalBoxId;
	
	recordProposalBoxId[""+o.value] = [true,o.getAttribute('amount').replace(/,/g, ""),o.getAttribute('disputeAmount').replace(/,/g, ""),
	   								o.getAttribute('itemTypeId'),o.getAttribute('creditAmount').replace(/,/g, ""),
	   								o.getAttribute('originatorId'),o.getAttribute('SCOAId'),o.getAttribute('disputeCategoryId')];
	
	determineNumber();
	targetFun='updateProposalByTab("'+proposalBoxId+'","showList()")';
	
	YAHOO.ccm.container.editProposalWindow.show();
}

function searchdDisputeTab(){
    disputeTabPage = new SANINCO.Page("_disputeTable", "disputeTabPage", {
        sortingField: "p.created_timestamp",
        sortingDirection: "desc",
        recordText: "",
        height:"100%",
        vo: "svo",
        recPerArray: [5, 10, 15, 20, 30],
        totalPageUri: "getDisputeTabTotalNoOfInoiceDetail.action",//?invoiceDisputeTabId=" + invoiceId + "
        dataUri: "searchDisputeTabOfInoiceDetail.action",//?invoiceDisputeTabId=" + invoiceId + "
        cols: [{
            title: function(obj){
                return "<input type=\"checkbox\" class='noBorderRadioButton' name=\"leftbox\" onclick=\"chooseleftall()\"/>";
            },
            dataField: function(obj){
            	// mod by yinghe.fu
                return "<input type=\"checkbox\" class='noBorderRadioButton' name=\"leftbox\" value=\"'" + obj.id + "','" + obj.invoiceitemid + "','" + obj.originator_id + "'\" onclick=\"calibrationCheckboxLeft(this,{id:'" + obj.id + "',invoiceitemid:'" + obj.invoiceitemid + "',originator_id:'" + obj.originator_id + "'});\"/>";
            }
        }, {
            title: "Edit",
            dataField: function(obj){
                if (obj.invoiceitemid == "") {
                    return "<img  src='include/images/Edit-icon-s.png' onclick=\"editRecodeFromProposalListLeft(" + obj.id + ")\"/>";
                } else {
                	return "<img  src='include/images/Edit-icon-s.png' amount=\""+obj.amount+"\"  disputeAmount=\""+obj.disputeAmount+"\" "
            	           + "itemTypeId=\""+obj.itemTypeId+"\" creditAmount=\""+obj.creditAmount+"\" "
            		       + "SCOAId=\""+obj.SCOAId+"\" originatorId=\""+obj.originator_id+"\" disputeCategoryId=\""+obj.disputeCategoryId+"\" "
            			   +"onclick=\"updateEditProposal(this,"+ obj.id +")\"/>";
                }
            }
        }
        // mod by yinghe.fu
//        , {
//            title: "Delete",
//            dataField: function(obj){
//                return "<img  src='include/images/reject16.png' onclick=\"deleteRecodeFromProposalListDivShow('" + obj.id + "','" + obj.invoiceitemid + "','" + obj.originator_id + "')\"/>";
//            	
//            }
//        }
        , {
            title: "Amount",
            dataField: function(obj){
                if ((obj.disputeAmount == "") || (obj.disputeAmount == 0) || (obj.disputeAmount == 0.00)) {
                    return "";
                } else {
                    return obj.disputeAmount;
                }
            },
            sort: "p.dispute_amount",
            filtration: {
                name: "p.dispute_amount",
                alias: "Amount"
            }
        }, {
            title: "Category",
            dataField: "category",
            sort: "dr.dispute_reason_name",
            filtration: {
                name: "dr.dispute_reason_name",
                alias: "Category"
            }
        }, {
            title: "Service Type",
            dataField: "service_type",
            sort: "p.service_type",
            filtration: {
                name: "p.service_type",
                alias: "ServiceType"
            }
        }, {
            title: "SCOA",
            dataField: "account_code_name",
            sort: "ac.account_code_name",
            filtration: {
                name: "ac.account_code_name",
                alias: "SCOA"
            }
        }, {
            title: "Circuit Number",
            dataField: "circuit_number",
            sort: "p.circuit_number",
            filtration: {
                name: "p.circuit_number",
                alias: "CircuitNumber"
            }
        }, {
            title: "Notes",
            dataField: function(o){return "<div style=\"width:260px;white-space: pre-wrap;\" >"+o.notes+"<div>"},
            sort: "p.notes",
			className : "table_td_br",
			width : '260px',
            filtration: {
                name: "p.notes",
                alias: "Notes"
            }
        }, {
            title: "Description",
            dataField: "description",
            sort: "p.description",
			className : "table_td_br",
			width : '260px',
            filtration: {
                name: "p.description",
                alias: "Description"
            }         
        },	{
            title: "File",
            dataField:function(o){
			return (o.attachmentPoint == "" ? "" : "<img src=\"include/images/download1.gif\" style=\"cursor: pointer;\" onclick=\"SANINCO.Download(" + o.attachmentPoint + ");\"/>");}
            }

        ]
    });
    disputeTabPage.addBeforeSearch(function(t, m){
        if (m == "start") {
            leftState = {};
        }
    });
    disputeTabPage.addCompleteEvent(function(o){
        echoLeftProposalIdLeft();
    });
    disputeTabPage.param = {
        "invoiceDisputeTabId": invoiceId
    };
	if(!invoiceDisputeActionRole){
		disputeTabPage.cols[0].display = "none";
		disputeTabPage.cols[1].display = "none";
		disputeTabPage.cols[2].display = "none";
	}
    filter1 = new SANINCO.Filter();
    filter1.addEditeEvent(function(){
        disputeTabPage.start();
    });
    filter1.add("p.dispute_amount", "String");
    filter1.add("p.description", "String");
	filter1.add("p.notes", "String");
    filter1.add("dr.dispute_reason_name", "String");
    filter1.add("p.service_type", "String");
    filter1.add("p.circuit_number", "String");
    filter1.add("ac.account_code_name", "String");
    disputeTabPage.setFilter(filter1);
    disputeTabPage.addSuccessEvent(function(data,TO){
    });
    disputeTabPage.start();
}
// add by yinghe.fu
function deleteProposalDataLeft(){
	var j = 0;
	for (var i in checkedata){
		j+=1;
	}
	
    if(j != 0){
    	if (!confirm("Please confirm to delete.")) {
        return;
    	}
	    var proposalIds = "";
	    var invoiceItemIds = "";
	    var originatorIds = "";
	    
		for (var i in checkedata){
			if(checkedata[i].id != null && checkedata[i].id != ""){
			 	var proposalId = checkedata[i].id;
			 	var invoiceItemId = checkedata[i].invoiceitemid;
			 	var originatorId = checkedata[i].originator_id;
			 	delete checkedata[i];
			 	proposalIds +=  (proposalId+",");
			 	if(invoiceItemId == ""){		 		
			 		invoiceItemIds +=  (" ,");
				}else{
					invoiceItemIds +=  (invoiceItemId+",");
				}
				originatorIds += (originatorId+",");
		 	}
		 }
		 proposalIds = proposalIds.substr(str,proposalIds.length-1);
		 invoiceItemIds = invoiceItemIds.substr(str,invoiceItemIds.length-1);
		 originatorIds = originatorIds.substr(str,originatorIds.length-1);
	     deleteRecodeFromProposalListDivShow(proposalIds, invoiceItemIds, originatorIds);  
	}else{
		alert("Please select dispute item(s)!");
	}
}
//getProposalDataLeft
function getProposalDataLeft(){
	
	if(getProposalIdsLeft().length == 0){
		alert("Please select dispute item(s)!");
		return;
	}
	showLoadingModalLayer();
    var callback = {
        success: getProposalDataLeftcallback,
        failure: showError
    };
    var getProposalLeftData = "invoiceDisputeTabId=" + invoiceId + "&" + "proposalIds="+getProposalIdsLeft().toString();
    YAHOO.util.Connect.asyncRequest("POST", "getProposalDataLeftByInvoiceId.action", callback, getProposalLeftData);
    
}
//getProposalDataLeftcallback
function getProposalDataLeftcallback(o){
    if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
        window.location.reload();
        return;
    }
    var data = eval("(" + o.responseText + ")");
    proposalDateLeft = data.data;
    checkedata = {};
	saveDisputeTab();
	hideLoadingModalLayer();
 }
 //getProposalDataLeftForGetDisputeList
 function getProposalDataLeftDL(){
     if (verifyWhetherChooseLeft()) {
         var callback = {
             success: getProposalDataLeftDLcallback,
             failure: showError
         };
         var getProposalLeftData = "invoiceDisputeTabId=" + invoiceId + "&" + "proposalIds=" + getProposalIdsLeft().toString();
         YAHOO.util.Connect.asyncRequest("POST", "getProposalDataLeftByInvoiceId.action", callback, getProposalLeftData);
     }
 }
//getProposalDataLeftDLcallback
function getProposalDataLeftDLcallback(o){
    if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
        window.location.reload();
        return;
    }
    var data = eval("(" + o.responseText + ")");
    proposalDateLeft = data.data;
	if(!checkDisputeTabCategory()){
		return;
	}
	getDisputeListByInvoiceId_SameCategory();
}
//Get dispute drop-down list of data
function getDisputeListByInvoiceId_SameCategory(){
    var callback = {
        success: getDisputeListByInvoiceIdcallback_SameCategory,
        failure: showError
    };
    var previousPostedData = composePostDataFromUiDisputeTab();
    YAHOO.util.Connect.asyncRequest("POST", "getDisputeListByInvoiceId.action", callback, previousPostedData);
}

//After getDisputeListByInvoiceId callback
function getDisputeListByInvoiceIdcallback_SameCategory(o){
    if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
        window.location.reload();
        return;
    }
    var data = eval("(" + o.responseText + ")");
    var c = document.getElementById("disputeSelectDiv");
    var selectHTML = "<span class='select1'><select id='__dispute'>";
    if (!(data.error || (data.count == 0))) {
        data = data.data;
        for (var i = 0; i < data.length; i++) {
            if (proposalDateLeft[0].categoryId == data[i].dispute_reason_id) {
                selectHTML += "<option value=\"{id:'" + data[i].id + "',category:'" + data[i].dispute_reason_id + "'}\" >" + data[i].claim_number + "</option>";
            }
        }
        selectHTML += "</select></span>";
		c.innerHTML = selectHTML;
    }else{
		c.style.display="false";
	}
    
}
//Get dispute drop-down list of data
function getDisputeListByInvoiceId_Reset(){
    var callback = {
        success: getDisputeListByInvoiceIdcallback_Reset,
        failure: showError
    };
    var previousPostedData = composePostDataFromUiDisputeTab();
    YAHOO.util.Connect.asyncRequest("POST", "getDisputeListByInvoiceId.action", callback, previousPostedData);
}

//After getDisputeListByInvoiceId callback
function getDisputeListByInvoiceIdcallback_Reset(o){
    if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
        window.location.reload();
        return;
    }
    var data = eval("(" + o.responseText + ")");
    var c = document.getElementById("disputeSelectDiv");
    var selectHTML = "<span class='select1'><select id='__dispute'>";
    if (!(data.error || (data.count == 0))) {
        data = data.data;
        for (var i = 0; i < data.length; i++) {
            selectHTML += "<option value=\"{id:'" + data[i].id + "',category:'" + data[i].dispute_reason_id + "'}\" >" + data[i].claim_number + "</option>";
        }
        selectHTML += "</select></span>";
    }
    c.innerHTML = selectHTML;
}
//Echo proposalId
function echoLeftProposalIdLeft(){
    var str = document.getElementsByName("leftbox");
    for (var i in leftState) {
        if (leftState[i] == true) {
            for (j = 0; j < str.length; j++) {
                if (str[j].value == i) {
                    str[j].checked = true;
                    promptColor(str[j]);
                    break;
                }
            }
        }
    }
}

function calibrationCheckboxLeft(o,obj){
	var str = o.value.split(',');
    var id = str[0].replace("'","").replace("'","");
    if (o.checked == true) {
    	// add by yinghe.fu  	
        leftState["" + id] = true;
        checkedata["" + o.value] = obj;
		 $(o).parent().parent().addClass("hightlight");
    }else{
        leftState["" + id] = false;
        if(o.value in checkedata){
        	delete checkedata["" + o.value];
        }
		$(o).parent().parent().removeClass("hightlight");
    }
}
function validateTheNotesEdit(){
		var proposalDiscription = document.getElementById("eProposalNote").value;
		if(proposalDiscription.replace(/[^\x00-\xff]/g,"xx").length<=10){
			alert("Your notes have to be more than 10 characters!");
			return false;
		}
		return true;
}
function saveEditRecodeFromProposalListLeft(){
	if (!confirm("Please confirm to saveEdit.")) {
        return;
    }
/*	if (!validateTheNotesEdit()) {
		document.getElementById("eProposalNote").focus();
        return;
    }*/
	if(!validateIsTrue()) return;
    if (editProposalData.disputeId != "") {
        var proposalId = document.getElementById("eproposalId").value;
        var disputeAmount = document.getElementById("eProposalAmount").value;
		var disputeAmountFromDispute = document.getElementById("spandisputeAmount"+editProposalData.disputeId).innerHTML;
		var arry=disputeAmountFromDispute.split('/');
        if (arry.length > 1) {
            for (var k = 0; k < arry.length; k++) {
				var arryn=arry[1].split(',');
				if (arryn.length > 1) {
					var disputeAmountn = "";
					for (var l = 0; l < arryn.length; l++) {
						disputeAmountn = disputeAmountn + arryn[l];
					}
					disputeAmountFromDispute = parseFloat(disputeAmountn);
				} else {
					disputeAmountFromDispute = parseFloat(arry[1]);
				}
            }
        }
//		if((parseFloat(disputeAmount)-parseFloat(editProposalData.disputeAmount))>0){
//			document.getElementById("spandisputeAmount"+editProposalData.disputeId).innerHTML='/'+(((parseFloat(disputeAmount)-parseFloat(editProposalData.disputeAmount)) + parseFloat(disputeAmountFromDispute)).toFixed(2));
//			document.getElementById("dSpanDisputeAmount"+editProposalData.disputeId).innerHTML=(((parseFloat(disputeAmount)-parseFloat(editProposalData.disputeAmount)) + parseFloat(disputeAmountFromDispute)).toFixed(2));
//			var getDisputeData = "disputeIdRight=" + editProposalData.disputeId + "&" + "disputeAmount=" + ((parseFloat(disputeAmount)-parseFloat(editProposalData.disputeAmount)) + parseFloat(disputeAmountFromDispute)) ;
//        	YAHOO.util.Connect.asyncRequest("POST", "updateDisputeAmountByDiputeIdAndDisputeAmount.action", "", getDisputeData);
//		}else if((parseFloat(disputeAmount)-parseFloat(editProposalData.disputeAmount))<0){
//			document.getElementById("spandisputeAmount"+editProposalData.disputeId).innerHTML='/'+((parseFloat(disputeAmountFromDispute)-(parseFloat(editProposalData.disputeAmount)-parseFloat(disputeAmount))).toFixed(2));
//			document.getElementById("dSpanDisputeAmount"+editProposalData.disputeId).innerHTML=(parseFloat(disputeAmountFromDispute)-(parseFloat(editProposalData.disputeAmount)-parseFloat(disputeAmount)).toFixed(2));
//			var getDisputeData = "disputeIdRight=" + editProposalData.disputeId + "&" + "disputeAmount=" + (parseFloat(disputeAmountFromDispute)-(parseFloat(editProposalData.disputeAmount)-parseFloat(disputeAmount)));
//        	YAHOO.util.Connect.asyncRequest("POST", "updateDisputeAmountByDiputeIdAndDisputeAmount.action", "", getDisputeData);
//		}
        var cricuitNumber = document.getElementById("eCricuitNumber").value;
        var categoryId = document.getElementById("edispute_reason_proposal").value;
        var serviceType = ccmEscape(document.getElementById("eServiceType").value);
        var originatorId = document.getElementById("eOriginator").value;
        var proposalDiscription = encodeURIComponent(document.getElementById("eProposalNote").value);
        var proposalNote = encodeURIComponent(document.getElementById("eProposalNote").value);
        
        var sCOA = eAccount_code.getValue();
        var callback = {
            success: saveEditRecodeFromProposalListLeftcallback,
            failure: showError
        };
        var getProposalData = "proposalId=" + proposalId + "&" + "disputeAmount=" + disputeAmount + "&" + "cricuitNumber=" + cricuitNumber + "&" + "categoryId=" + categoryId + "&" + "serviceType=" + serviceType + "&" + "originatorId=" + originatorId + "&" + "proposalNote=" + proposalNote + "&" + "sCOA=" + sCOA;
        YAHOO.util.Connect.asyncRequest("POST", "updateProposalByProposalId.action", callback, getProposalData);
        getDisputeAmountTotal();
		var inst = "proposalTableOfDisputeTabPage" + editProposalData.disputeId;
    	window[inst].start();
		YAHOO.ccm.container.EditProposalLeftId.hide();
		document.getElementById("edispute_reason_proposal").disabled="";
    }
	var proposalId = document.getElementById("eproposalId").value;
    var disputeAmount = document.getElementById("eProposalAmount").value;
    var cricuitNumber = ccmEscape(document.getElementById("eCricuitNumber").value);
    var categoryId = document.getElementById("edispute_reason_proposal").value;
    var serviceType = ccmEscape(document.getElementById("eServiceType").value);
    var originatorId = document.getElementById("eOriginator").value;
    var proposalNote = encodeURIComponent(document.getElementById("eProposalNote").value);

    var sCOA = eAccount_code.getValue();
    var callback = {
        success: saveEditRecodeFromProposalListLeftcallback,
        failure: showError
    };
    	var getProposalData = "proposalId=" + proposalId+"&"+ "disputeAmount=" + disputeAmount +"&"+"cricuitNumber="+cricuitNumber+"&"+"categoryId="+categoryId+"&"+"serviceType="+serviceType+"&"+"originatorId="+originatorId+"&"+"proposalNote="+proposalNote+"&"+"sCOA="+sCOA;
    YAHOO.util.Connect.asyncRequest("POST", "updateProposalByProposalId.action", callback, getProposalData);

}
function saveEditRecodeFromProposalListLeftcallback(){
	YAHOO.ccm.container.EditProposalLeftId.hide();
	getDisputeAmountTotal();
	disputeTabPage.start();
	searchDisputesByInvoiceIdInDisputeTab();
}
//get Recode(proposal) From ProposalList for edit
function editRecodeFromProposalListLeft(id,disputeStatus){

  if (!confirm("Please confirm to Edit.")) { return; }
  if (disputeStatus) {
	  if (disputeStatus <= 99 ) {
		  if (disputeStatus >= 30) {
			 alert("This dispute can't edit");				
             return;  
		  }
		  
	  }
  }
	if(!validateIsTrue()) return;
    var callback = {
        success: editRecodeFromProposalListLeftcallback,
        failure: showError
    };
    var getProposalData = "proposalId=" + id;
    YAHOO.util.Connect.asyncRequest("POST", "getProposalByProposalId.action", callback, getProposalData);
}

function editRecodeFromProposalListLeftcallback(o){
    var data = eval("(" + o.responseText + ")");
	editProposalData = data;
    document.getElementById("eproposalId").value = data.proposalId;
    document.getElementById("eProposalAmount").value = data.disputeAmount;
    document.getElementById("eCricuitNumber").value = data.cricuitNumber;
	if(editProposalData.disputeId != ""){
		document.getElementById("edispute_reason_proposal").value = data.categoryId;
		document.getElementById("edispute_reason_proposal").disabled = "true";
	}else{
		document.getElementById("edispute_reason_proposal").disabled = "";
		document.getElementById("edispute_reason_proposal").value = data.categoryId;
	}
    document.getElementById("eServiceType").value = data.serviceType;
    document.getElementById("eOriginator").value = data.originatorId;
    document.getElementById("eProposalNote").value = data.notes;
	eAccount_code.setValue(data.sCOA);
    YAHOO.ccm.container.EditProposalLeftId.show();
	searchAnnexsOfProposalByProposalId(data.proposalId);
	attachmentPointId = data.proposalId;
}
function searchAnnexsOfProposalByProposalId(proposalId){
    attachmentFileOfProposalTablePage = new SANINCO.Page("_attachmentFileOfProposalTable", "attachmentFileOfProposalTablePage", {
    	paginationDiv: "_attachmentFileOfProposalTable_pageTable",
    	sortingField: "attachment_file.created_timestamp",
        sortingDirection: "desc",
        recordText: "",
        vo: "svo",
        recPerArray: [5, 10, 15, 20, 30],
        totalPageUri: "getAnnexsTotalOfProposalByProposalId.action?ProposalId=" + proposalId + "",
        dataUri: "searchAnnexsOfProposalByProposalId.action?ProposalId=" + proposalId + "",
        cols: [{
            width: "10%",
            title: "Delete",
            dataField: function(obj){
                return "<img src='include/images/reject16.png' onclick=\"deleteRecodeFromAnnexsList(" + obj.id + ");\"/>";
            }
        }, {
            title: "Down Load",
            width: "15%",
            dataField: function(obj){
                return "<img src='include/images/download1.gif' onclick=\"downloadFileO(" + "'" + obj.file_name + "','" + obj.file_path + "'" + ");\"/>";
            }
        }, {
            title: "File Name",
            width: "60%",
            dataField: function(obj){
                return obj.file_name;
            },
            sort: "attachment_file.file_name",
            filtration: {
                name: "attachment_file.file_name",
                alias: "FileName"
            }
        }]
    });
    filter1 = new SANINCO.Filter();
    filter1.addEditeEvent(function(){
        attachmentFileOfProposalTablePage.start();
    });
    filter1.add("attachment_file.id", "Integer");
    filter1.add("attachment_file.file_name", "String");
    attachmentFileOfProposalTablePage.setFilter(filter1);
    attachmentFileOfProposalTablePage.addSuccessEvent(function(data){
        //proposalDateLeft = data.data;
    });
    attachmentFileOfProposalTablePage.start();
}
function deleteRecodeFromAnnexsList(Id){
	if (!confirm("Please confirm to delete.")) {
        return;
    }
	if(!validateIsTrue()) return;
    var callback = {
        success: deleteRecodeFromAnnexsListcallback,
        failure: showError
    };
    var data = "attachmentPointId=" + Id;
    YAHOO.util.Connect.asyncRequest("POST", "deleteAnnexsOfProposalById.action",callback, data);
}
function deleteRecodeFromAnnexsListcallback(){
	searchAnnexsOfProposalByProposalId(attachmentPointId);
}

//Select All check box
function chooseleftall(){
    var checked = document.getElementsByName("leftbox")[0].checked;
    var selects = document.getElementsByName("leftbox");
    for (var i = 1; i < selects.length; i++) {
        selects[i].checked = checked;
        var str = selects[i].value.split(',');
        var id = str[0].replace("'","").replace("'","");
        var invoiceitemid = str[1].replace("'","").replace("'","");
        var originator_id = str[2].replace("'","").replace("'","");
        if (selects[i].checked == true) {
            // add by yinghe.fu
            leftState["" + id] = true;
            checkedata["" + selects[i].value]= {'id':id,'invoiceitemid':invoiceitemid,'originator_id':originator_id}; 
			$(selects[i]).parent().parent().addClass("hightlight");
        } else {
            leftState["" + id] = false;
			$(selects[i]).parent().parent().removeClass("hightlight");
        	delete checkedata["" + selects[i].value];
        }
        
    }
}
//getDisputeAmountTotal
function getDisputeAmountTotal(){
    var callback = {
        success: getDisputeAmountTotalcallback,
        failure: showError
    };
    var invoiceDisputeTabId = "invoiceDisputeTabId=" + invoiceId;
    YAHOO.util.Connect.asyncRequest("POST", "getDisputeTotalAmountByInvoiceId.action", callback, invoiceDisputeTabId);
}
//After getDisputeAmountTotal callback
function getDisputeAmountTotalcallback(o){
    if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
        window.location.reload();
        return;
    }
    var data = o.responseText;
    if (data.error) {
        alert(" getDisputeAmountTotalcallback Error:" + data.error);
        return;
    }
    document.getElementById("disputeTotalAmount").value = data;
}

//Get proposalids
function getProposalIdsLeft(){
    var ProposalIds = new Array();
    for (var i in leftState) {
        if (leftState[i] == true) {
            ProposalIds.push(i);
        }
    }
    return ProposalIds;
}

function getProposalDataForGreateNewGroupForEachSelected(){
    var proposalData = "";
    var chkBoxId = getProposalIdsLeft();
    for (var i = 0; i < chkBoxId.length; i++) {
        var id = chkBoxId[i];
        for (var j = 0; j < proposalDateLeft.length; j++) {
            if (proposalDateLeft[j].id == id) {
                proposalData = proposalData + proposalDateLeft[j].id + ",";
                proposalData = proposalData + proposalDateLeft[j].categoryId + ",";
                //proposalData = proposalData + proposalDateLeft[j].amount;
				var arry=(proposalDateLeft[j].amount).split(',');
				if (arry.length > 1) {
					var disputeAmount = "";
					for (var k = 0; k < arry.length; k++) {
						disputeAmount = disputeAmount + arry[k];
					}
					proposalData = proposalData  + parseFloat(disputeAmount);
				} else {
					proposalData = proposalData  + parseFloat(proposalDateLeft[j].amount);
				}
            }
        }
        if (i >= chkBoxId.length - 1) {
            return proposalData;
        } else {
            proposalData = proposalData + "$";
        }
    }
}

//When the proposal will be used to move the proposal disputeamount update to the table disputeamount dispute
function getTotalDisputeAmontOfSelectedProposalLeft(){
    var totalDisputeAmount = 0;
    var chkBoxId = getProposalIdsLeft();
    for (var i = 0; i < chkBoxId.length; i++) {
        var id = chkBoxId[i];
        for (var j = 0; j < proposalDateLeft.length; j++) {
            if (proposalDateLeft[j].id == id) {
				var arry=(proposalDateLeft[j].amount).split(',');
				if (arry.length > 1) {
					var disputeAmount = "";
					for (var k = 0; k < arry.length; k++) {
						disputeAmount = disputeAmount + arry[k];
					}
					totalDisputeAmount = parseFloat(totalDisputeAmount) + parseFloat(disputeAmount);
				} else {
					totalDisputeAmount = parseFloat(totalDisputeAmount) + parseFloat(proposalDateLeft[j].amount);
				}
			}
        }
    }
    return parseFloat(totalDisputeAmount);
}

//check category
function checkDisputeTabCategory(){
    var chkBoxId = getProposalIdsLeft();
    var categoryOne = "";
    for (var i = 0; i < chkBoxId.length; i++) {
        var id = chkBoxId[i];
        for (var j = 0; j < proposalDateLeft.length; j++) {
            if (proposalDateLeft[j].id == id) {
                if (i == 0) {
                    categoryOne = proposalDateLeft[j].category;
                }
                if (categoryOne == "") {
                    alert("Selected Dispute Items are null in category, can not create claim!");
                    return false;
                }
                if (proposalDateLeft[j].category != categoryOne) {
                    alert("Selected dispute items do not have same category, can not create claim!");
                    //chkBoxId.clear();
                    return false;
                }
            }
        }
    }
    return true;
}

// move selected same category validation
function moveSelectedSameCategoryValidationLeft(){
    var chkBoxId = getProposalIdsLeft();
    if ((document.getElementById("__dispute") == null)||(document.getElementById("__dispute").value=="")) {
        alert("There is no existing group!");
        return;
    }
    var ele = document.getElementById("__dispute").value;
    ele = eval("(" + ele + ")");
    var toCategoryOne = ele.category;
    var categoryOne = "";
    for (var i = 0; i < chkBoxId.length; i++) {
        var id = chkBoxId[i];
        for (var j = 0; j < proposalDateLeft.length; j++) {
            if (proposalDateLeft[j].id == id) {
                if (i == 0) {
                    categoryOne = proposalDateLeft[j].categoryId;
                }
                if (categoryOne == "") {
                    alert("Selected Dispute Items are null in category, can not create claim!");
                    return false;
                }
                if (toCategoryOne != categoryOne) {
                    alert("Selected dispute item can not move to group with different category!");
                    return false;
                }
            }
        }
    }
    return true;
}

function verifyWhetherChooseLeft(){
    if (getProposalIdsLeft().length == 0) {
        alert("Please select dispute item(s)!");
        return false;
    }
    return true;
}

//saveDisputeTabLeftPage
function saveDisputeTab(){
	if(!validateIsTrue()) return;
    var radio = document.getElementsByName("invoiceDisputeTabLeftRadio");
    var index = 0;
    for (var i = 0; i < radio.length; i++) {
        if (radio[i].checked == true) {
            index = i;
        }
    }
    if (index == 0 && verifyWhetherChooseLeft() && checkDisputeTabCategory() && moveSelectedSameCategoryValidationLeft()) {
        var ele = document.getElementById("__dispute").value;
        ele = eval("(" + ele + ")");
        var ToDisputeId = ele.id;
        var data = "disputeId=" + ToDisputeId + "&" + "proposalIds=" + getProposalIdsLeft().toString() + "&" + "totalDisputeAmount=" + getTotalDisputeAmontOfSelectedProposalLeft();
        YAHOO.util.Connect.asyncRequest("POST", "updateDisputeTabMoveSelectGroupOfInvoiceDetail.action", {
            success: function(){
                searchdDisputeTab(invoiceId);
                getDisputeListByInvoiceId_ready();
					delete leftState;
    				leftState = {};
            },
            failure: showError
        }, data);
    } else {
        if ( index == 1 && verifyWhetherChooseLeft() && checkDisputeTabCategory()) {
            var chkBoxId = getProposalIdsLeft();
            var disputeReasonId = "";
            for (var i = 0; i < chkBoxId.length; i++) {
                var id = chkBoxId[i];
                for (var j = 0; j < proposalDateLeft.length; j++) {
                    if (proposalDateLeft[j].id == id) {
                        if (i == 0) {
                            disputeReasonId = proposalDateLeft[j].categoryId;
                        }
                    }
                }
            }
            var data = "proposalIds=" + getProposalIdsLeft().toString() + "&" + "disputeReasonId=" + disputeReasonId + "&" + "disputeStatusId=" + "1" + "&" + "invoiceId=" + invoiceId + "&" + "totalDisputeAmount=" + getTotalDisputeAmontOfSelectedProposalLeft();
            YAHOO.util.Connect.asyncRequest("POST", "updateDisputeTabGreateOneGroupOfInvoiceDetail.action", {
                success: function(){
                    searchdDisputeTab(invoiceId);
                    getDisputeListByInvoiceId_ready();
                    getDisputeListByInvoiceIdLeft();
					delete leftState;
    				leftState = {};
                },
                failure: showError
            }, data);
        } else {
            if ( index == 2 && verifyWhetherChooseLeft()) {
                var data = "proposalData=" + getProposalDataForGreateNewGroupForEachSelected() + "&" + "invoiceId=" + invoiceId;
                YAHOO.util.Connect.asyncRequest("POST", "updateDisputeTabGreateNewGroupForEachSelectedOfInvoiceDetail.action", {
                    success: function(){
                        searchdDisputeTab(invoiceId);
                        getDisputeListByInvoiceId_ready();
                        getDisputeListByInvoiceIdLeft();
						delete leftState;
    					leftState = {};
                    },
                    failure: showError
                }, data);
            }
        }
    }
}
function validateDisputeReasonTextMoreThanTenChar(disputeReasonText){
	if((disputeReasonText.value.length<=10)||(disputeReasonText.value=="")){
		alert('Please enter a greater than 10 character string!');
		disputeReasonText.focus();
		return false;
	}else{
		YAHOO.ccm.container.deleteProposalPanelDiv.hide();
		return true;
	}
}
function deleteRecodeFromProposalListDivShow(proposalId, invoiceItemId, originatorId){
	document.getElementById('proposalId').value = proposalId;
	document.getElementById('invoiceItemId').value = invoiceItemId;
//    if (originatorId == 5) {
//        YAHOO.ccm.container.deleteProposalPanelDiv.show();
//    }else{
		deleteRecodeFromProposalList(proposalId, invoiceItemId, "");
	//}
}
//delete Recode From ProposalList
function deleteRecodeFromProposalList(proposalId, invoiceItemId, disputeReasonText){
	if(!validateIsTrue()) return;
    var callback = {
        success: deleteRecodeFromProposalListcallback,
        failure: showError
    };
	var data = "proposalId=" + proposalId + "&" + "invoiceItemId=" + invoiceItemId + "&" + "disputeReasonText=" + disputeReasonText + "&"+"svo.invoiceId=" + invoiceId ;
    YAHOO.util.Connect.asyncRequest("POST", "deleteProposalFromProposalList.action", callback, data);
	leftState[proposalId+""] = false;
}

//The text box empty brings proposal
function emptyTheTextForSaveProposal(){
    document.getElementById("proposalAmount").value = "";
    document.getElementById("proposalNote").value = "";
    document.getElementById("cricuitNumber").value = "";
    document.getElementById("serviceType").value = "";
	account_code.setValue("");
}

//After deleteRecodeFromProposalListcallback callback
function deleteRecodeFromProposalListcallback(o){
    if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
        window.location.reload();
        return;
    }
    var data = eval("(" + o.responseText + ")");
    if (data.error) {
        alert(" deleteRecodeFromProposalListcallback Error:" + data.error);
        return;
    }
    searchdDisputeTab(invoiceId);
    getDisputeAmountTotal();
}
function searchAnnexsOfDisputeByDisputeId(disputeId){
    attachmentFileOfDisputeTablePage = new SANINCO.Page("_attachmentFileOfDisputeTablePage", "attachmentFileOfDisputeTablePage", {
        sortingField: "attachment_file.created_timestamp",
        sortingDirection: "desc",
        recordText: "",
        vo: "svo",
        recPerArray: [5, 10, 15, 20, 30],
        totalPageUri: "getAnnexsTotalOfDisputeByDisputeId.action?disputeId=" + disputeId + "",
        dataUri: "searchAnnexsOfDisputeByDisputeId.action?disputeId=" + disputeId + "",
        cols: [{
            width: "10%",
            title: "Delete",
            dataField: function(obj){
                return "<img src='include/images/reject16.png' onclick=\"deleteRecodeFromAnnexsListOfDispute(" + obj.id + ");\"/>";
            }
        }, {
            title: "File",
            width: "15%",
            dataField: function(obj){
                return "<img src='include/images/download1.gif' onclick=\"downloadFileO(" + "'" + obj.file_name + "','" + obj.file_path + "'" + ");\"/>";
            }
        }, {
            title: "File Name",
            width: "40%",
            dataField: function(obj){
                return obj.file_name;
            },
            sort: "attachment_file.file_name",
            filtration: {
                name: "attachment_file.file_name",
                alias: "FileName"
            }
        }]
    });
	if(!invoiceDisputeActionRole){
		attachmentFileOfDisputeTablePage.cols[0].display = "none";
	}
    filter1 = new SANINCO.Filter();
    filter1.addEditeEvent(function(){
        attachmentFileOfDisputeTablePage.start();
    });
    filter1.add("attachment_file.id", "Integer");
    filter1.add("attachment_file.file_name", "String");
    filter1.add("attachment_file.file_path", "String");
    attachmentFileOfDisputeTablePage.setFilter(filter1);
    attachmentFileOfDisputeTablePage.addSuccessEvent(function(data){
        //proposalDateLeft = data.data;
    });
    attachmentFileOfDisputeTablePage.start();
}
function showUploadForDisputeTabRightDiv(disputeId){
    YAHOO.ccm.container.UpLoadFileForDisputeTabRightDivId.show();
    document.getElementById("disputeIdRight").value = disputeId;
	checkRoleForDisputeFileMannage();
	searchAnnexsOfDisputeByDisputeId(disputeId);
}
function deleteRecodeFromAnnexsListOfDispute(Id){
	if (!confirm("Please confirm to delete.")) {
        return;
    }
	if(!validateIsTrue()) return;
    var callback = {
        success: deleteRecodeOfDisputecallback,
        failure: showError
    };
    var data = "attachmentPointId=" + Id;
    YAHOO.util.Connect.asyncRequest("POST", "deleteAnnexsOfDisputeById.action", callback, data);
}
function deleteRecodeOfDisputecallback(){
	searchAnnexsOfDisputeByDisputeId(document.getElementById("disputeIdRight").value);
}
//strart query
function searchDisputesByInvoiceIdInDisputeTab(){
    var _recPerPageSelect = document.getElementById("_recPerPageSelect");
    if (_recPerPageSelect.value) {
        recPerPage = _recPerPageSelect.value;
    }
    var callback = {
        success: renderDisputesByInvoiceIdTable,
        failure: showError
    };
    var previousPostedData = composePostDataFromUiDisputeTab();
    YAHOO.util.Connect.asyncRequest("POST", "getDisputesTotalNoByInvoiceId.action", callback, previousPostedData);
}

//genernate table item
function renderDisputesByInvoiceIdTable(o){
    if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
        window.location.reload();
        return;
    }
    var data = eval("(" + o.responseText + ")");
    hideLoadingModalLayer();
    if (data.error) {
        _disputesDataTable.innerHTML = "Error: " + data.error;
        _paginationDisputesTable.style.display = "none";
		document.getElementById("__dispute").innerHTML = "";
		document.getElementById("__dispute").style.display = "none";
        return;
    }
    var __curPageNo = document.getElementById("__curPageNo");
    if (data.totalResultNo == 0) {
        totalResultNo = 0;
        totalPageNo = data.totalPageNo;
        _disputesDataTable.innerHTML = "";
        __curPageNo.value = 0;
        __totalPageNo.innerHTML = "0";
        _paginationDisputesTable.style.display = "none";
		document.getElementById("__dispute").innerHTML = "";
		document.getElementById("__dispute").style.display = "none";
    } else {
        totalResultNo = data.totalResultNo;
        totalPageNo = data.totalPageNo;
        __curPageNo.value = 1;
        __totalPageNo.innerHTML = totalPageNo;
        _paginationDisputesTable.style.display = "block";
        requestDisputesData();
    }
}

//query data
function requestDisputesData(){
    var callback = {
        success: renderDisputesPage,
        failure: showError
    };
    previousPostedData = composePostDataFromUiDisputeTab();
    showLoadingModalLayer();
    YAHOO.util.Connect.asyncRequest("POST", "searchDisputesByInvoiceId.action", callback, previousPostedData);
}

//generate table
function renderDisputesPage(o){
    if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
        window.location.reload();
        return;
    }
    var data = eval("(" + o.responseText + ")");
    if (data.error) {
        _disputesDataTable.innerHTML = "Error: " + data.error;
        _paginationDisputesTable.style.display = "none";
        hideLoadingModalLayer();
        return;
    }
    var t = "Items " + data.begin + " - " + data.end + " of " + totalResultNo + "<div " + "id=\"diputesTab\">";
    var rows = data.data;
    for (var i = 0; i < rows.length; i++) {
        var row = rows[i];
        t += ("<div style='width:99%;'><div onclick=\"searchdProposalByInvoiceIdAndDisputeId(" + row.id + ");clearRightData();checkRoleForDisputeClaim(" + row.id + ")\" style='float:left;width:97%;'><h5><a href=\"javascript:void(0)\" id=\"" + row.id + "\" >" + row.claim_number + " " + "<span id=\"spandisputeAmount" + row.id + "\">" + getTheCorrectDisputeAmount(row.dispute_amount) + "</span>" + "<span id=\"spanshortpaid" + row.id + "\">" + getTheCorrectShortPaid(row.flag_shortpaid) + "</span>" + "" + "<span id=\"spanrecurring" + row.id + "\">" + getTheCorrectRecurring(row.flag_recurring) + "</span>" + " </a></h5></div><div style='float:right;margin-top:2px;margin-right:2px;'><img style='width:16px;height:16px;' src=\"include/images/reject160.png\" alt=\"Delete\" id=\"Delete" + row.id + "\" onclick=\"return deleteDisputeForRight(" + row.id +","+row.dispute_status + ");\"/></div></div>" + "<div style=\"height:100%;width:99%;\">" + "<table width=100% height=150px border=0>" + "<tr><td>Claim Number</td><td><span id=\"claimNumberHead" + row.id + "\">"+getUpdateClaimNumber(row.claim_number,false)+"</span><input type=\"text\" value=\"" +getUpdateClaimNumber(row.claim_number,true)+"\" id=\"claimNumber" + row.id + "\"/></td> <td width='15%' valign='top' align='right'>Dispute Notes :&nbsp; </td> <td width='20%' align=\"left\" valign='top' rowspan=\"5\">  <textarea id=\"disputeNote" + row.id + "\" name=\"\"  style='width:260px;overflow:auto ;height:75px;'>" + row.notes + "</textarea></td></tr>"+ "<tr>" + "<input type=hidden name=\"disputeId\" id=\"disputeId" + row.id + "\" value=\"" + row.id + "\"> " + "<td width='15%' align=\"left\">DisputeNumber:</td>" + "<td colspan='3' width='20%'>" + row.dispute_number + "</td>" + "</tr>" + "<tr>" + "<td width='3%' align=\"left\">Vendor Tracking Number:</td>" + "<td colspan='3' width='20%'>" + "<input id=\"ticketNumber"+row.id+"\" name=\"ticketNumber"+row.id+"\"  type=\"text\" value='"+row.ticket_number+"'>"
	 +"</td>" + "</tr>" + "<td align=\"left\">Amount: </td>" + "<td colspan='2'><span id=\"dSpanDisputeAmount" + row.id + "\">" + row.dispute_amount + "</span></td>" + "</tr>" + "<tr>" + "<td align=\"left\">Category: </td>" + "<td colspan='2'> " + row.dispute_reason_name + "</td>" + "</tr>" + "<tr>" + "<td>" + "Originator:" + "</td>" + "<td colspan='2'>" + "<span class='select1'><select id=\"originatorListSelect" + row.id + "\" >" + OriginatorListBindingByOriginatorListData(row.id,row.originator_id) + "</select></span>" + "</td>" + "</tr>" + "<tr>" + "<td >Confidence Level:</td>" + "<td>" + "<input style='width:40px' type=\"text\" id=\"confidenceLevel" + row.id + "\" value=\"" + (row.confidence_level?row.confidence_level:"0") + "\" onchange=\"\" onkeypress='return event.keyCode>=48&&event.keyCode<=57' onkeyup=\"changeShortPaidState(" + row.id + ");checkAndLess(this);\"  >" + "%" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"button\" id=\"uploadfile" + row.id + "\" value=\"Attachment Management\" onclick=\"showUploadForDisputeTabRightDiv(" + row.id + ");AllclearUploadFiles();\"/>" + "</td>" + "<td valign='top' align='right'>Email Copy To :</td>" + "<td rowspan='3'>" + "<textarea id=\"emailCopyTo" + row.id + "\" name=\"\"  style='width:260px;overflow:auto ;height:75px;'>" + row.email_copy_address + "</textarea>" + "<p>Please use ';' to separate multiple email addresses. </p>" + "</td>" + "</tr>" + "<tr><td>Reserved Amount</td><td colspan=2><input type=\"text\"  onkeypress='return event.keyCode>=48&&event.keyCode<=57||event.keyCode==46' value=\"" +(row.reserve_amount?row.reserve_amount:"0.00")+"\" id=\"reservedAmount" + row.id + "\"/></td></tr>"+"<tr>" + "<td rowspan=\"2\">" + "</td>" + "<td colspan='2' align=\"left\"><div><div><div style='float:left;width:100px;border-right:1px solid #000;'>" + "<input type=\"radio\" class=\"noBorderRadioButton\" name=\"invoiceDisputeTabRightRadioRecurring" + row.id + "\" value=\"DATEFRAME\" onclick=\"\" " + (row.flag_recurring == "Y" ? "checked=\"checked\"" : "") + ">Recurring" + "</div><div style='float:left;margin-left:8px;width:100px;'>" + "<input type=\"radio\" class=\"noBorderRadioButton\" name=\"invoiceDisputeTabRightRadioShortPaid" + row.id + "\" id=\"invoiceDisputeTabRightRadioShortPaid" + row.id + "\" value=\"DATEFRAME\" onclick=\"changeTheReservedAmountByShortPaid(this,"+row.id+")\" onchange=\"\" " + (row.flag_shortpaid == "Y" ? "checked=\"checked\"" : "") + ">Short Paid" + "</div></div>" +
	 		"</td></tr><tr><td colspan='2'><div><div style='float:left;width:100px;border-right:1px solid #000;'>" + "<input type=\"radio\" class=\"noBorderRadioButton\" name=\"invoiceDisputeTabRightRadioRecurring" + row.id + "\" value=\"DATEFRAME\" onclick=\"\" " + (row.flag_recurring == "N" ? "checked=\"checked\"" : "") + ">Non-recurring" + "</div><div style='float:left;margin-left:8px;width:100px;'><input type=\"radio\" class=\"noBorderRadioButton\" name=\"invoiceDisputeTabRightRadioShortPaid" + row.id + "\" value=\"DATEFRAME\" onclick=\"changeTheReservedAmountByPaidDispute(this,"+row.id+")\" " + (row.flag_shortpaid == "N" ? "checked=\"checked\"" : "") + ">Paid Dispute</div></div></div></td>" + "</tr><tr><td><td><td valign='top' align='right'>Send dispute email to vendor:</td><td><select name='emailFlag' id=\"emailFlag" + row.id + "\"><option " + (row.email_flag == "Y" ? "selected=\"selected\"" : "") + " value='Y'>Y</option><option " + (row.email_flag == "N" ? "selected=\"selected\"" : "") + " value='N'>N</option></select></td></tr>" + "</table>" + "<table border=0 style='table-layout:fixed;width:100%;'>" + "<tr>" + "<td colspan=\"2\">" + "<br /><div align='left'  style='width:100%;overflow-x:auto;padding-bottom: 20px;'><div id=\"_proposalTableOfDispute" + row.id + "\" style=\"width:100%;height:100%\"></div></div>" + "<br /><div>" + "&nbsp;&nbsp;<input type=\"submit\" onclick=\"saveRightProposalListExcelByDisputeTabByInvoiceIdAndDisputeId(" + row.id + ");\" id=\"" + row.id + "\" value=\"Download to Excel\">" + "</div>" + "</td>" + "</tr>" + "<tr>" + "<td width='60%' align=\"left\">" + "<input type=\"radio\" onclick=\"getProposalDataRightDL("+row.id+")\" class=\"noBorderRadioButton\" class=\"noBorderRadioButton\" name=\"invoiceDisputeTabRightRadio" + row.id + "\" value=\"DATEFRAME\">" + "Move selected to an existing group" + "</td>" + "<td>" + "<span class='select1'><select id=\"disputeListSelect" + row.id + "\" class=\"remove\" style=\"visibility: visible;\" >" + returnDisputeListForRight(disputeListData) + "</select></span>" + "</td>" + "</tr>" +  "<tr>" + "<td align=\"left\">" + "<input type=\"radio\" class=\"noBorderRadioButton\" class=\"noBorderRadioButton\" onclick=\"getDisputeListRightByInvoiceId_Reset();\" name=\"invoiceDisputeTabRightRadio" + row.id + "\" value=\"DATEFRAME\" >" + "Remove record from this group" + "</td>" + "<td>"+ getSaveButton(row.id) + "&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"button\" onclick=\"clearRadioButtonOfRight("+row.id+");\" id=\"row.id\" value=\"Cancel\"></td>" + "</tr>" + "</table></div>");
    }//onkeypress='return event.keyCode>=48&&event.keyCode<=57||event.keyCode==46'
    t += "</div>";
    _disputesDataTable.innerHTML = t;
    __curPageNo.value = pageNo;
    _paginationDisputesTable.style.display = "block";
    hideLoadingModalLayer();
    jQuery("#diputesTab").accordion({
        collapsible: true
    });
}
function getUpdateClaimNumber(number,flag){
	if(!number){
		return "";
	}
	var ns = number.split("-");
	if(flag){
		var str = "";
		for(var i=2;i<ns.length;i++){
			if(i==2){
				str += ns[i];
			}else{
				str += ("-"+ns[i]);
			}
		}
		return str;
	}else{
		return (ns[0]?ns[0]:"") + "-" + (ns[1]?ns[1]:"") + "-";
	}
}
function clearRadioButtonOfRight(disputeId){
	var selects = document.getElementsByName("invoiceDisputeTabRightRadio" + disputeId + "");
    for(var i=0;i<selects.length;i++){
		selects[i].checked=false;
	}
}
function checkReservedAmount(disputeId){
	var totalDisputeAmount=0;
	var disputeAmount = document.getElementById("spandisputeAmount"+disputeId).innerHTML;
	var reservedAmount = document.getElementById("reservedAmount"+disputeId); 
	var arry = disputeAmount.split('/');
				if (arry.length > 1) {
					var pdisputeAmount = "";
					for (var k = 0; k < arry.length; k++) {
						pdisputeAmount = pdisputeAmount + arry[k];
					}
					totalDisputeAmount = parseFloat(totalDisputeAmount) + parseFloat(pdisputeAmount);
				} else {
					totalDisputeAmount = parseFloat(totalDisputeAmount) + parseFloat(disputeAmount);
				}
	if(parseFloat(reservedAmount.value)>totalDisputeAmount){
		reservedAmount.focus();
		reservedAmount.value=0;
		alert("Please enter a reservedAmount not greater than the disputeAmount!");
	}
}
function getSaveButton(disputeId){
	if(invoiceDisputeActionRole){
		return "<input type=\"submit\" onclick=\"getProposalDataRight("+disputeId+");\" id=\"" + disputeId + "\" value=\"Save\">"
	}else{
		return "";
	}
}
//Only allowed to enter the Numbers (e.value <= 0) || (e.value == "") ||
function checkTheDisputeAmount(e){
	if(e.value.length > 0){
		
		var req = /^(-?\d+)(\.\d+)?$/;
//		var req = /^-?([\d]{1,}([\.]{1}[\d]{1,})|([+-\d]{0,}))$/;
		
//		if (e.value < 0) {
//			alert("Disput amount can not be negative value.");
//			return
//		}
		if ((!req.exec(e.value))||(e.value == "")) {
			alert("Only allowed to enter the Numbers.");
			e.value = "";
			return
		}		
		if(e.value.length>15){
			alert("Maixmum 15 digits!");
			return
		}
	}
	
}
function checkProposalAmountIsNotNull(){
	var amount= document.getElementById("proposalAmount").value;
	if(amount==""||amount==0.00||amount==0.0||amount==0){
		alert('Missing dispute amount, SCOA and  notes!');
//		alert('ProposalAmount is not null and ProposalAmount is not 0.00,0.0,0!');
		return false;
	}
	else{
		return true;
	}
}
function checkTheCricuitNumber(e){
	if(e.value.length>=63){
		alert("Please enter the CricuitNumber have not more than 64 char!");
	}
}
function checkTheServiceType(e){
	if(e.value.length>=127){
		alert("Please enter the ServiceType have not more than 128 char!");
	}
}
//Only allow input digital and the CricuitNumber is not greater than 100
function checkAndLess(e){
    if (e.value > 100) {
        alert("Confidence level must be from 0 to 100 !");
        e.value = "100";
    }
}
function deleteDisputeForRight(disputeId,disputeStatus){
	if(!validateIsTrue()) return;
	  if (disputeStatus) {
		  if (disputeStatus <=99 ) {
			  if (disputeStatus >= 30) {
				  alert("This dispute can't be deleted");
				  return;  
			  }
			  
		  }
	  }
    if (confirm("Please confirm to Delete!")) {
        var data = "proposalIds=" + getRightProposalIdsForDeleteDisputeOfDisputeTab(disputeId) + "&" + "disputeId=" + disputeId + "&" + "totalDisputeAmount=" + getTotalDisputeAmontOfRightAllProposalRight(disputeId);
        YAHOO.util.Connect.asyncRequest("POST", "deleteDisputeOfInvoiceDetail.action", {
            success: function(){
                searchdDisputeTab(invoiceId);
                getDisputeListByInvoiceIdLeft();
				getDisputeListByInvoiceId_ready();
            },
            failure: showError
        }, data);
    }
    return false;
}

//Get disputeAmount
function getTheCorrectDisputeAmount(dispute_amount){
    if ((dispute_amount == "")) {
        return "";
    } else {
        return "/" + dispute_amount + "";
    }
}
//Get disputeShortpaid state
function getTheCorrectShortPaid(flag_shortpaid){
    if ((flag_shortpaid == "y") || (flag_shortpaid == "Y")) {
        return "/Short-Paid";
    } else {
        if ((flag_shortpaid == "n") || (flag_shortpaid == "N")) {
            return "/Paid";
        } else {
            return "";
        }
    }
}
//Get disputeRecurring state
function getTheCorrectRecurring(flag_recurring){
    if ((flag_recurring == "Y") || (flag_recurring == "y")) {
        return "/Recuring";
    } else {
        if ((flag_recurring == "N") || (flag_recurring == "n")) {
            return "/Non-Recuring";
        } else {
            return "";
        }
    }
}

//Get disputeAmount state
function getTheCorrectAmount(proposal_amount){
    if ((flag_recurring == "Y") || (flag_recurring == "y")) {
        return "/Recuring";
    } else {
        if ((flag_recurring == "N") || (flag_recurring == "n")) {
            return "/No-Recuring";
        } else {
            return "";
        }
    }
}
//Get dispute drop-down list of data
function getDisputeListByInvoiceId_ready(){
	getDisputeListByInvoiceId_Reset();
    var callback = {
        success: getDisputeListByInvoiceIdcallback_ready,
        failure: showError
    };
    var previousPostedData = composePostDataFromUiDisputeTab();
    YAHOO.util.Connect.asyncRequest("POST", "getDisputeListByInvoiceId.action", callback, previousPostedData);
}

//After getDisputeListByInvoiceId callback
function getDisputeListByInvoiceIdcallback_ready(o){
    if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
        window.location.reload();
        return;
    }
    var data = eval("(" + o.responseText + ")");//||(data.count==0)
    if (data.error) {
        //	alert(" getDisputeListByInvoiceIdcallback_ready Error:" + data.error);
        return;
    }
    if (data.count!=0) {
        var html = "";
        for (var i = 0; i < data.length; i++) {
            html += "<option value=\"{id:'" + data[i].id + "',category:'" + data[i].dispute_reason_id + "'}\" >" + data[i].claim_number + "</option>";
        }
        disputeListSelectHTML = html;
    }
    data = data.data;
    disputeListData = data;
    getOriginatorList();
}

//After getDisputeListByInvoiceId callback
function returnDisputeListForRight(disputeListData){
    var selectHTML = "";
    for (var i = 0; i < disputeListData.length; i++) {
        selectHTML += "<option value=\"{id:'" + disputeListData[i].id + "',category:'" + disputeListData[i].dispute_reason_id + "'}\" >" + disputeListData[i].claim_number + "</option>";
    }
    return selectHTML;
}

//getProposalDataLeftForGetDisputeList
function getProposalDataRightDL(disputeId){
	
	$(".remove").show();
    var callback = {
        success: getProposalDataRightDLcallback,
        failure: showError
    };
    var getProposalLeftData = "svo.invoiceId=" + invoiceId + "&svo.disputeId=" + disputeId + "&svo.proposalsId=" + getRightProposalIdsOfDisputeTab().toString();
    YAHOO.util.Connect.asyncRequest("POST", "getProposalDataRightByInvoiceIdAndDisputeId.action", callback, getProposalLeftData);
   
}
//getProposalDataLeftDLcallback
function getProposalDataRightDLcallback(o){
    if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
        window.location.reload();
        return;
    }
    var data = eval("(" + o.responseText + ")");
    proposalDateRight = data.data;
	var $options = $(".remove option");
	$options.remove();
	var categoryId = proposalDateRight[0].categoryId;
	var newDisputeListData = [];
	$.each(disputeListData,function (i,o){ // o Is Object
		if(categoryId == o.dispute_reason_id){
			newDisputeListData.push(o);
		}
	});
	$.each(newDisputeListData,function (i,n){
		$(".remove").append("<option value=\"{id:'" +n.id + "',category:'" + n.dispute_reason_id + "'}\" >" + n.claim_number + "</option>");
	})
}

//Get dispute drop-down list of data
function getDisputeListRightByInvoiceId_Reset(){
	
	$(".remove").hide();
	var $options = $(".remove option");
	$options.remove();
	$.each(disputeListData,function (i,n){
		$(".remove").append("<option value=\"{id:'" +n.id + "',category:'" + n.dispute_reason_id + "'}\" >" + n.claim_number + "</option>");
	})
}
//Get dispute drop-down list of data
function getDisputeListByInvoiceId(){
    var callback = {
        success: getDisputeListByInvoiceIdcallback,
        failure: showError
    };
    var data = composePostDataFromUiDisputeTab();
    YAHOO.util.Connect.asyncRequest("POST", "getDisputeListByInvoiceId.action", callback, data);
}

//After getDisputeListByInvoiceId callback
function getDisputeListByInvoiceIdcallback(o){
    if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
        window.location.reload();
        return;
    }
    var data = eval("(" + o.responseText + ")");
    if (data.error||data.count!=0) {
        document.getElementById("_disputesDataTable").innerHTML = "";
        document.getElementById("_paginationDisputesTable").style.display = "none";
        document.getElementById("__dispute").innerHTML = "";
		document.getElementById("__dispute").style.display = "none";
    } else {
        data = data.data;
        var html = "";
        for (var i = 0; i < data.length; i++) {
            html += "<option value=\"{id:'" + data[i].id + "',category:'" + data[i].dispute_reason_id + "'}\" >" + data[i].claim_number + "</option>";
        }
        disputeListSelectHTML = html;
        searchDisputesByInvoiceIdInDisputeTab();
    }
}

//Get dispute drop-down list of data for Left
function getDisputeListByInvoiceIdLeft(){
    var callback = {
        success: getDisputeListByInvoiceIdLeftcallback,
        failure: showError
    };
    var data = composePostDataFromUiDisputeTab();
    YAHOO.util.Connect.asyncRequest("POST", "getDisputeListByInvoiceId.action", callback, data);
}

//After getDisputeListByInvoiceId callback
function getDisputeListByInvoiceIdLeftcallback(o){
    if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
        window.location.reload();
        return;
    }
    var data = eval("(" + o.responseText + ")");
	var c = document.getElementById("disputeSelectDiv");
    var selectHTML = "<span class='select1'><select id='__dispute'>";
    if (!(data.error||(data.count==0))) {
        data = data.data;
        for (var i = 0; i < data.length; i++) {
            selectHTML += "<option value=\"{id:'" + data[i].id + "',category:'" + data[i].dispute_reason_id + "'}\" >" + data[i].claim_number + "</option>";
        }
        selectHTML += "</select></span>";
    }
    c.innerHTML = selectHTML;
}
//Get originator drop-down list of data
function getOriginatorList(){
    var callback = {
        success: getOriginatorListcallback,
        failure: showError
    };
    var data = composePostDataFromUiDisputeTab();
    YAHOO.util.Connect.asyncRequest("POST", "getOriginatorListAndReturnJSON.action", callback, data);
}
//After getDisputeListByInvoiceId callback
function getOriginatorListcallback(o){
    if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
        window.location.reload();
        return;
    }
	var data = eval("(" + o.responseText + ")");
    if (data.error) {
        document.getElementById("_disputesDataTable").innerHTML = "";
        document.getElementById("_paginationDisputesTable").style.display = "none";
        document.getElementById("__dispute").innerHTML = "";
		document.getElementById("__dispute").style.display = "none";
    } else {
        data = data.data;
        var html = "";
        for (var i = 0; i < data.length; i++) {
            html += "<option value=\"{id:'" + data[i].id + "',category:'" + data[i].dispute_reason_id + "'}\" >" + data[i].claim_number + "</option>";
        }
        disputeListSelectHTML = html;
        searchDisputesByInvoiceIdInDisputeTab();
    }
	
	
    var data = eval("(" + o.responseText + ")");
    if (data.error) {
        alert(" getOriginatorListcallback Error:" + data.error);
        return;
    }
    originatorListData = data.data;
    searchDisputesByInvoiceIdInDisputeTab();
}
function OriginatorListBindingByOriginatorListData(disputeId,originator_id){
    var html = "";
    for (var i = 0; i < originatorListData.length; i++) {
    	if(originatorListData[i].id == originator_id){
	       html += "<option selected=\"selected\" value=\"" + originatorListData[i].id + "\">" + originatorListData[i].originator_name + "</option>";
    	}else{
     	   html += "<option value=\"" + originatorListData[i].id + "\">" + originatorListData[i].originator_name + "</option>";
    	}
    }
    return html;
}

function getShortPaidState(short_paid, disputeId){
    var shortPaid = document.getElementById("rightdisputeshortpaidbox" + disputeId + "");
    if (short_paid == "Y") {
        shortPaid.checked == true;
    }
}
function changeTheReservedAmountByShortPaid(e,disputeId){
	if(e.checked==true){
		var totalDisputeAmount=0;
		var disputeAmount = document.getElementById("dSpanDisputeAmount" + disputeId +"").innerHTML;
		var arry = disputeAmount.split(',');
				if (arry.length > 1) {
					var pdisputeAmount = "";
					for (var k = 0; k < arry.length; k++) {
						pdisputeAmount = pdisputeAmount + arry[k];
					}
					totalDisputeAmount = parseFloat(totalDisputeAmount) + parseFloat(pdisputeAmount);
				} else {
					totalDisputeAmount = parseFloat(totalDisputeAmount) + parseFloat(disputeAmount);
				}
		document.getElementById("reservedAmount" + disputeId + "").disabled = false;
		var confidenceLevel = document.getElementById("confidenceLevel" + disputeId + "").value;
		if(confidenceLevel == "0"){
			document.getElementById("reservedAmount" + disputeId + "").value = (parseFloat(totalDisputeAmount)*1).toFixed(2);
		}else if(confidenceLevel == "100"){
			document.getElementById("reservedAmount" + disputeId + "").value = (parseFloat(totalDisputeAmount)*0).toFixed(2);
		}else{
			document.getElementById("reservedAmount" + disputeId + "").value = (parseFloat(totalDisputeAmount)*0.5).toFixed(2);
		}
	}
}
function changeTheReservedAmountByPaidDispute(e,disputeId){
	if(e.checked==true){
		document.getElementById("reservedAmount" + disputeId + "").value = 0;
		document.getElementById("confidenceLevel" + disputeId + "").value = 0;
		document.getElementById("reservedAmount" + disputeId + "").disabled = true;
	}
}
function changeShortPaidState(disputeId){
    var confidenceLevel = document.getElementById("confidenceLevel" + disputeId + "").value;
    var selects = document.getElementsByName("invoiceDisputeTabRightRadioShortPaid" + disputeId + "");
    
    if (confidenceLevel != "" && confidenceLevel >= 0 && confidenceLevel < 100) {
        selects[0].checked = true;
		var totalDisputeAmount=0;
        var disputeAmount = document.getElementById("dSpanDisputeAmount" + disputeId + "").innerHTML;
        var arry = disputeAmount.split(',');
        if (arry.length > 1) {
            var pdisputeAmount = "";
            for (var k = 0; k < arry.length; k++) {
                pdisputeAmount = pdisputeAmount + arry[k];
            }
            totalDisputeAmount = parseFloat(totalDisputeAmount) + parseFloat(pdisputeAmount);
        } else {
            totalDisputeAmount = parseFloat(totalDisputeAmount) + parseFloat(disputeAmount);
        }
		document.getElementById("reservedAmount" + disputeId + "").disabled = false;
		if(confidenceLevel > 0){
			document.getElementById("reservedAmount" + disputeId + "").value = (parseFloat(totalDisputeAmount)*0.5).toFixed(2);
		}else{
			document.getElementById("reservedAmount" + disputeId + "").value = (parseFloat(totalDisputeAmount)).toFixed(2);
		}
    } else {
    	if(confidenceLevel > 0){
    	 	selects[0].checked = true;
    	}else{
	        selects[1].checked = true;
    	}
		document.getElementById("reservedAmount" + disputeId + "").value = 0;
		document.getElementById("reservedAmount" + disputeId + "").disabled = true;
    }
}

function getRecurringState(recurring, disputeId){
    var recurring = document.getElementById("rightdisputeshortpaidbox" + disputeId + "");
    if (recurring == "Y") {
        recurring.checked = true;
    }
}

//sort
function sort(e, sortingFieldName){
    if (evaluateQueyChange()) {
        return;
    }
    if (currentSortingFieldName != sortingFieldName) {
        currentSortingFieldName = sortingFieldName;
        currentSortingDirection = "asc";
    } else {
        if (currentSortingDirection == "asc") {
            currentSortingDirection = "desc";
        } else {
            currentSortingDirection = "asc";
        }
    }
    requestData();
}

function getSortingDirectionString(fieldName){
    if (currentSortingFieldName == fieldName) {
        if (currentSortingDirection == "asc") {
            return "<img src=\"include/images/upsort.gif\">";
        } else {
            return "<img src=\"include/images/downsort.gif\">";
        }
    } else {
        return "";
    }
}

function isQueryChanged(){
    return (previousPostedData != composePostDataFromUiDisputeTab());
}

function evaluateQueyChange(){
    if (isQueryChanged()) {
        getDisputeListByInvoiceId_ready();
        return true;
    } else {
        return false;
    }
}

//fenye
function getNextPage(){
    if (evaluateQueyChange()) {
        return;
    }
    if ((pageNo + 1) > totalPageNo) {
        return;
    }
    pageNo++;
    requestDisputesData();
}

function getPrevPage(){
    if (evaluateQueyChange()) {
        return;
    }
    if ((pageNo - 1) < 1) {
        return;
    }
    pageNo--;
    requestDisputesData();
}

function getFirstPage(){
    if (evaluateQueyChange()) {
        return;
    }
    pageNo = 1;
    requestDisputesData();
}

function getLastPage(){
    if (evaluateQueyChange()) {
        return;
    }
    pageNo = totalPageNo;
    requestDisputesData();
}

function searchdProposalByInvoiceIdAndDisputeId(disputeId){
    var inst = "proposalTableOfDisputeTabPage" + disputeId;
    var winst = window[inst] = new SANINCO.Page("_proposalTableOfDispute" + disputeId, inst, {
        sortingField: "p.id",
        recordText: "",
        sortingDirection: "asc",
        vo: "svo",
        height:"100%",
        recPerArray: [5, 10, 15, 20, 30],
        totalPageUri: "getProposalsTotalNoOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId.action",
        dataUri: "searchProposalsOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId.action",
        cols: [{
            title: function(obj){
                return "<input type=\"checkbox\" class='noBorderRadioButton' name=\"rightbox" + disputeId + "\" onclick=\"chooseRightAll(" + disputeId + ");\" />";
            },
            dataField: function(obj){
                return "<input type=\"checkbox\" class='noBorderRadioButton' value=\"" + obj.id + "\" name=\"rightbox" + disputeId + "\" onclick=\"calibrationCheckboxRight(this);\"/>";
            }
        }, {
            title: "Edit",
            dataField: function(obj){
                if (obj.invoiceitemid == "") {
                    return "<img src='include/images/Edit-icon-s.png'  onclick=\"editRecodeFromProposalListLeft(" + obj.id+","+obj.dispute_status + ")\"/>";
                } else {
                    return "";
                }
            }
        },{
            title: "Amount",
            dataField: "amount",
            sort: "p.dispute_amount",
            filtration: {
                name: "p.dispute_amount",
                alias: "Amount"
            }
        }, {
            title: "Category",
            dataField: "category",
            sort: "dr.dispute_reason_name",
            filtration: {
                name: "dr.dispute_reason_name",
                alias: "Category"
            }
        }, {
            title: "Service Type",
            dataField: "service_type",
            sort: "p.service_type",
            filtration: {
                name: "p.service_type",
                alias: "ServiceType"
            }
        }, {
            title: "Circuit Number",
            dataField: "circuit_number",
            sort: "p.circuit_number",
            filtration: {
                name: "p.circuit_number",
                alias: "CircuitNumber"
            }
        }, {
            title: "SCOA",
            dataField: "account_code_name",
            sort: "ac.account_code_name",
            filtration: {
                name: "ac.account_code_name",
                alias: "SCOA"
            }
        }, {
            title: "Notes",
            dataField: "notes",
            sort: "p.notes",
			className : "table_td_br",
			width : '260px',
            filtration: {
                name: "p.notes",
                alias: "Notes"
            }
        }, {
            title: "Description",
            dataField: "description",
            sort: "p.description",
			className : "table_td_br",
			width : '260px',
            filtration: {
                name: "p.description",
                alias: "Description"
            }
        },
        {
            title: "File",
            dataField:function(o){
			return (o.attachmentPoint == "" ? "" : "<img src=\"include/images/download1.gif\" style=\"cursor: pointer;\" onclick=\"SANINCO.Download(" + o.attachmentPoint + ");\"/>");}
            }]
    });
    if (!invoiceDisputeActionRole) {
        winst.cols[0].display = "none";
        winst.cols[1].display = "none";
    }
    winst.addBeforeSearch(function(t, m){
        if (m == "start") {
            rightState = {};
        }
    });
    winst.addCompleteEvent(function(o){
        echoLeftProposalIdRight(disputeId);
    });
    winst.param = {
        "invoiceDisputeTabId": invoiceId,
        "disputeId": disputeId
    };
    filter = new SANINCO.Filter();
    filter.addEditeEvent(function(){
        winst.start();
    });
    filter.add("p.dispute_amount", "String");
    filter.add("p.description", "String");
	filter.add("p.notes", "String");
    filter.add("dr.dispute_reason_name", "String");
    filter.add("p.service_type", "String");
    filter.add("p.circuit_number", "String");
    filter.add("ac.account_code_name", "String");
    winst.setFilter(filter);
    winst.addSuccessEvent(function(data){
        proposalDateRight = data.data;
    });
    winst.start();
}

//Echo proposalId
function echoLeftProposalIdRight(disputeId){
    var str = document.getElementsByName("rightbox" + disputeId + "");
    for (var i in rightState) {
        if (rightState[i] == true) {
            for (j = 0; j < str.length; j++) {
                if (str[j].value == i) {
                    str[j].checked = true;
                    promptColor(str[j]);
                    break;
                }
            }
        }
    }
}
function calibrationCheckboxRight(o){
    if (o.checked == true) {
        rightState["" + o.value] = true;
		$(o).parent().parent().addClass("hightlight");
    }
    if (o.checked == false) {
        rightState["" + o.value] = false;
		$(o).parent().parent().removeClass("hightlight");
    } 
}
//saveDisputeTabLeftPage
function saveDisputeRightTab(disputeId){
	if(!validateIsTrue()) return;
    var radio = document.getElementsByName("invoiceDisputeTabRightRadio" + disputeId + "");
    var index = 4;
    for (var i = 0; i < radio.length; i++) {
        if (radio[i].checked == true) {
            index = i;
			break;
        }
    }
    if ((index == 0) && moveSelectedSameCategoryValidationRight(disputeId)) {
        //select 's value
        var disputeId = document.getElementById("disputeId" + disputeId + "").value;
        var ele = document.getElementById("disputeListSelect" + disputeId).value;
        var shortPaidSelects = document.getElementsByName("invoiceDisputeTabRightRadioShortPaid" + disputeId + "");
        if (shortPaidSelects[0].checked == true) {
            flagShortpaid = "Y";
        } else {
            flagShortpaid = "";
        }
        
        ele = eval("(" + ele + ")");
        var ToDisputeId = ele.id;
        var data = "disputeId=" + ToDisputeId + "&" + "proposalIds=" + getRightProposalIdsOfDisputeTab(disputeId).toString() + "&" + "totalDisputeAmount=" + getTotalDisputeAmontOfSelectedProposalRight(disputeId) + "&" + "fromDisputeId=" + disputeId+ "&" + "flagShortpaid=" + flagShortpaid;
        YAHOO.util.Connect.asyncRequest("POST", "updateDisputeTabMoveSelectGroupOfInvoiceDetailRight.action", {
            success: function(){
				getDisputeListByInvoiceId_ready();
                getDisputeListByInvoiceId();
            },
            failure: showError
        }, data);
        delete rightState;
        rightState = {};
    }else{
		if (index == 1 ) {
	        var chkBoxId = getRightProposalIdsOfDisputeTab(disputeId);
	        var disputeReasonId = "";
	        for (var i = 0; i < chkBoxId.length; i++) {
	            var id = chkBoxId[i];
	            for (var j = 0; j < proposalDateRight.length; j++) {
	                if (proposalDateRight[j].id == id) {
	                    if (i == 0) {
	                        disputeReasonId = proposalDateRight[j].categoryId;
	                    }
	                }
	            }
	        }
	        var disputeId = document.getElementById("disputeId" + disputeId + "").value;
	        var data = "proposalIds=" + getRightProposalIdsOfDisputeTab(disputeId).toString() + "&" + "disputeReasonId=" + disputeReasonId + "&" + "fromDisputeId=" + disputeId + "&" + "disputeStatusId=" + "1" + "&" + "invoiceId=" + invoiceId + "&" + "totalDisputeAmount=" + getTotalDisputeAmontOfSelectedProposalRight(disputeId);
	        YAHOO.util.Connect.asyncRequest("POST", "updateDisputeTabGreateOneGroupOfInvoiceDetailRight.action", {
	            success: function(){
	                getDisputeListByInvoiceId_ready();
	                getDisputeListByInvoiceIdLeft();
	            },
	            failure: showError
	        }, data);
            delete rightState;
            rightState = {};
	    }else{
			if (index == 2) {
		        var disputeId = document.getElementById("disputeId" + disputeId + "").value;
				document.getElementById("edispute_reason_proposal").disabled="";
		        var data = "proposalIds=" + getRightProposalIdsOfDisputeTab(disputeId).toString() + "&" + "disputeId=" + disputeId + "&" + "totalDisputeAmount=" + getTotalDisputeAmontOfSelectedProposalRight(disputeId);
		        YAHOO.util.Connect.asyncRequest("POST", "updateDisputeTabRemoveSelectedForThisGroupOfInvoiceDetail.action", {
		            success: function(){
		                getDisputeListByInvoiceId_ready();
		                searchdDisputeTab(invoiceId);
		            },
		            failure: showError
		        }, data);
                delete rightState;
                rightState = {};
		    }
		}
	}
}
function saveEditDispute(disputeId,index){
		var bo = false;
		
		 //update by mingyang.li 2016.12.09 start
		var ele = document.getElementById("disputeListSelect" + disputeId).value;
	    ele = eval("(" + ele + ")");
		var disputeTo = ele.id;
		var shortPaidSelects = document.getElementsByName("invoiceDisputeTabRightRadioShortPaid" + disputeId + "");
		var disputeToShortPaidSelects = document.getElementsByName("invoiceDisputeTabRightRadioShortPaid" + disputeTo + "");
		
		if(index == 4){
			if (!confirm("Please confirm to Update.")) {
	            return bo;
	        }
//		}else if(index == 1 && shortPaidSelects[0].checked == true){
//			if (!confirm("You select the dispute status is different as before, please select again or select ‘Remove record from this group’.")) {
//	            return bo;
//	        }
		}else{
			if(index == 0 && shortPaidSelects[0].checked != disputeToShortPaidSelects[0].checked){
				if (!confirm("You select the dispute status is different as before, please select again or select ‘Remove record from this group’.")) {
		            return bo;
		        }
			}else{
				if (!confirm("Please confirm to Update.")) {
		            return bo;
		        }
			}
		}
		
		
		 //update by mingyang.li 2016.12.09 end
		
        if (CheckTheEmailCorrectlyFormat(disputeId) && validateNotesConfidenceLevelIsNotNull(disputeId)) {
			bo = true;
            var flagRecurring = "";
            var flagShortpaid = "";
            var originatorId = document.getElementById("originatorListSelect" + disputeId + "").value;
            var disputeNotes = encodeURIComponent(document.getElementById("disputeNote" + disputeId + "").value);
            var confidenceLevel = document.getElementById("confidenceLevel" + disputeId + "").value;
            var emailCopyTo = ccmEscape(document.getElementById("emailCopyTo" + disputeId + "").value);
            var reservedAmount = document.getElementById("reservedAmount" + disputeId + "").value;
            
            // update by mingyang.li 20161101 start
    		var arry = reservedAmount.split(',');
			if (arry.length > 1) {
				var pdisputeAmount = "";
				for (var i = 0; i < arry.length; i++) {
					pdisputeAmount = pdisputeAmount + arry[i];
				}
				reservedAmount = parseFloat(pdisputeAmount);
			} else {
				reservedAmount = parseFloat(reservedAmount);
			}
			// update by mingyang.li 20161101 end
			
            var claimNumber=document.getElementById("claimNumber" + disputeId).value;
            var ticketNumber = document.getElementById("ticketNumber"+disputeId).value;
            var claimNumberHead=document.getElementById("claimNumberHead" + disputeId).innerHTML;
            var shortPaidSelects = document.getElementsByName("invoiceDisputeTabRightRadioShortPaid" + disputeId + "");
            var recurringSelects = document.getElementsByName("invoiceDisputeTabRightRadioRecurring" + disputeId + "");
            if (shortPaidSelects[0].checked == true) {
                flagShortpaid = "Y";
                document.getElementById("spanshortpaid" + disputeId + "").innerHTML = "/Short-Paid";
            } else {
                flagShortpaid = "";
                document.getElementById("spanshortpaid" + disputeId + "").innerHTML = "/Paid";
            }
            if (recurringSelects[0].checked == true) {
                flagRecurring = "Y";
                document.getElementById("spanrecurring" + disputeId + "").innerHTML = "/Recurring";
            } else {
                flagRecurring = "";
                document.getElementById("spanrecurring" + disputeId + "").innerHTML = "/Non-Recurring";
            }
            var callback = {
                success: function(o){
                	if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
				        window.location.reload();
				        return;
				    }
					var data = eval("(" + o.responseText + ")");
				    if (data.error) {
				        alert(data.error);
				    } else {
				    	getDisputeListByInvoiceId_ready();
				    }
                },
                failure: showError
            };
            var data = "disputeId=" + disputeId + "&claimNumber=" +ccmEscape(claimNumberHead) + ccmEscape(claimNumber) + "&originatorId=" + originatorId + "&" + "flagRecurring=" + flagRecurring + "&" + "ticketNumber=" + ticketNumber +"&" + "flagShortpaid=" + flagShortpaid + "&" + "disputeNotes=" + disputeNotes + "&" + "confidenceLevel=" + confidenceLevel + "&" + "emailCopyTo=" + emailCopyTo + "&" + "reservedAmount=" + reservedAmount + "&emailFlag=" + $("#emailFlag" + disputeId).val();
            YAHOO.util.Connect.asyncRequest("POST", "updateDispute.action", callback, data);
        }
		return bo;
}

function clearRightData(){
	    delete rightState;
        rightState = {};
}

//getProposalDataRight
function getProposalDataRight(disputeId){
	var radio = document.getElementsByName("invoiceDisputeTabRightRadio" + disputeId + "");
    var index = 4;
    for (var i = 0; i < radio.length; i++) {
        if (radio[i].checked == true) {
            index = i;
			break;
        }
    }
	
	if((index==0||index==1||index==2)&&(getRightProposalIdsOfDisputeTab()[0]==undefined)){
		alert("Please select dispute item(s)!");
		return;
	}
	if((getRightProposalIdsOfDisputeTab().length > 0) && !(index==0||index==1||index==2)){
		alert("Please select an operation!");
		return;
	}
	
	if(saveEditDispute(disputeId,index) == false) return;
	
	if((getRightProposalIdsOfDisputeTab().length > 0) && (index==0||index==1||index==2)){
        var callback = {
            success: getProposalDataRightcallback,
            failure: showError
        };
        var getProposalLeftData = "svo.invoiceId=" + invoiceId + "&" + "svo.proposalsId=" + getRightProposalIdsOfDisputeTab().toString() + "&" + "svo.disputeId=" + disputeId;
        YAHOO.util.Connect.asyncRequest("POST", "getProposalDataRightByInvoiceIdAndDisputeId.action", callback, getProposalLeftData);
	}
}
//getProposalDataRightcallback
function getProposalDataRightcallback(o){
    if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
        window.location.reload();
        return;
    }
	var data = eval("(" + o.responseText + ")");
	proposalDateRight = data.data;
	var disputeId = data.data[0].dispute_id;
    saveDisputeRightTab(disputeId);
}
function validateNotesConfidenceLevelIsNotNull(disputeId){
	var disputeNotes=document.getElementById("disputeNote" + disputeId);
	var confidenceLevel=document.getElementById("confidenceLevel" + disputeId).value;
	var claimNumber=document.getElementById("claimNumber" + disputeId).value;
	if(!claimNumber){
		alert("Claim Number is null");
		return false;
	}
	if(!confidenceLevel){
		alert("Confidence Level is null");
		return false;
	}
	return true;
/*	if((disputeNotes.value=="")||disputeNotes.value.length<=10){
		disputeNotes.className="textarea2";
		alert('Please enter confidence level (0-100) and notes(more than 10 chars)!');
		return false;
	}else{
		disputeNotes.className='textarea1';
		return true;
	}*/
}
//Please input the correct format of the Email
function CheckTheEmailCorrectlyFormat(disputeId){
    var MyStr = document.getElementById("emailCopyTo" + disputeId + "");
    var email_str = MyStr.value.split(";");
//    var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    var myreg = /^([a-zA-Z0-9]+[-_|\_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    if (MyStr.value != "") {
        for (var i = 0; i < email_str.length; i++) {
            var value = email_str[i];
            if (myreg.test(value) == false) {
                alert("Please input the correct format of the E_mail!");
                MyStr.focus();
                return false;
            }
        }
        return true;
    } else {
        return true;
    }
}
//Get right proposalids
function getRightProposalIdsOfDisputeTab(disputeId){
   var ProposalIds = new Array();
   for (var i in rightState) {
	if(rightState[i] == true){
       ProposalIds.push(i);
	   }
    }
    return ProposalIds;
}
//Get right proposalids for deletedispute
function getRightProposalIdsForDeleteDisputeOfDisputeTab(disputeId){
    var ProposalIds = new Array();
    for (var i in rightState) {
	if(rightState[i] == true){
       ProposalIds.push(i);
	   }
    }
    return ProposalIds;
}
//When the proposal will be used to move the proposal disputeamount update to the table disputeamount dispute
function getTotalDisputeAmontOfSelectedProposalRight(disputeId){
    var totalDisputeAmount = 0;
    var chkBoxId = getRightProposalIdsOfDisputeTab(disputeId);
    for (var i = 0; i < chkBoxId.length; i++) {
        var id = chkBoxId[i];
        for (var j = 0; j < proposalDateRight.length; j++) {
            if (proposalDateRight[j].id == id) {
                var arry=(proposalDateRight[j].amount).split(',');
				if (arry.length > 1) {
					var disputeAmount = "";
					for (var k = 0; k < arry.length; k++) {
						disputeAmount = disputeAmount + arry[k];
					}
					totalDisputeAmount = parseFloat(totalDisputeAmount) + parseFloat(disputeAmount);
				} else {
					totalDisputeAmount = parseFloat(totalDisputeAmount) + parseFloat(proposalDateRight[j].amount);
				}
            }
        }
    }
    return parseFloat(totalDisputeAmount);
}
//When the proposal will be used to move the proposal disputeamount update to the table disputeamount dispute
function getTotalDisputeAmontOfRightAllProposalRight(disputeId){
    var totalDisputeAmount = 0;
    var chkBoxId = getRightProposalIdsForDeleteDisputeOfDisputeTab(disputeId);
    for (var i = 0; i < chkBoxId.length; i++) {
        var id = chkBoxId[i];
        for (var j = 0; j < proposalDateRight.length; j++) {
            if (proposalDateRight[j].id == id) {
                var arry=(proposalDateRight[j].amount).split(',');
				if (arry.length > 1) {
					var disputeAmount = "";
					for (var k = 0; k < arry.length; k++) {
						disputeAmount = disputeAmount + arry[k];
					}
					totalDisputeAmount = parseFloat(totalDisputeAmount) + parseFloat(disputeAmount);
				} else {
					totalDisputeAmount = parseFloat(totalDisputeAmount) + parseFloat(proposalDateRight[j].amount);
				}
            }
        }
    }
    if (totalDisputeAmount == "") {
        return 0;
    }
    return parseFloat(totalDisputeAmount);
}
//Select All check box
function chooseRightAll(disputeId){
    var checked = document.getElementsByName("rightbox" + disputeId + "")[0].checked;
    var selects = document.getElementsByName("rightbox" + disputeId + "");
    for (var i = 1; i < selects.length; i++) {
        selects[i].checked = checked;
        if (selects[i].checked == true) {
            rightState["" + selects[i].value] = true;
			$(selects[i]).parent().parent().addClass("hightlight");
			
        } else {
            rightState["" + selects[i].value] = false;
			$(selects[i]).parent().parent().removeClass("hightlight");
        }
    }
		
}
// move selected same category validation right
function moveSelectedSameCategoryValidationRight(disputeId){
    var chkBoxId = getRightProposalIdsOfDisputeTab(disputeId);
    var ele = document.getElementById("disputeListSelect" + disputeId).value;
    ele = eval("(" + ele + ")");
    var toCategoryOne = ele.category;
    var categoryOne = "";
    for (var i = 0; i < chkBoxId.length; i++) {
        var id = chkBoxId[i];
        for (var j = 0; j < proposalDateRight.length; j++) {
            if (proposalDateRight[j].id == id) {
                if (i == 0) {
                    categoryOne = proposalDateRight[j].categoryId;
                }
                if (categoryOne == "") {
                    alert("Selected Dispute Items are null in category, can not create claim!");
                    return false;
                }
                if (toCategoryOne != categoryOne) {
                    alert("Selected dispute item can not move to group with different category!");
                    return false;
                }
            }
        }
    }
    return true;
}
function verifyWhetherChooseRight(disputeId){
    if (getRightProposalIdsOfDisputeTab(disputeId).length == 0) {
        alert("Please select dispute item(s)!");
        return false;
    }
    return true;
}
// download excel
function saveLeftProposalListExcelByDisputeTab(){
    var invoiceDisputeTabId = invoiceId;
    var titles = "titles=Amount&titles=Category&titles=Service Type&titles=SCOA&titles=Circuit Number&titles=Notes&titles=Description";
    var uri = "saveExcelByProposalListInDisputeTabByInvoiceIdLeft.action" + "?" + titles + "&" + disputeTabPage.paramStr;
    windowOpen(uri);
}
function saveRightProposalListExcelByDisputeTabByInvoiceIdAndDisputeId(disputeId){
    var titles = "titles=Amount&titles=Category&titles=Service Type&titles=Circuit Number&titles=SCOA&titles=Note&titles=Description";
    var inst = "proposalTableOfDisputeTabPage" + disputeId;
    var uri = "saveExcelByProposalListInDisputeTabByInvoiceIdAndDiputeIdRight.action" + "?" + titles + "&" + window[inst].paramStr;
    windowOpen(uri);
}