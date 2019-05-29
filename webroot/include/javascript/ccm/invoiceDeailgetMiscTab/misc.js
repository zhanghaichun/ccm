//@author pengfei.wang
var micsid;
var payVendorid;
var editPaymentPanel;
var addPaymentPanel;
var invoiceMiscActionRole = SANINCO.ifAllGranted(Constants.FUNCTION.invoiceMiscAdjustmentAction);

//Page loading
YAHOO.util.Event.onDOMReady(function(){
    micsid = currentBanId;
    payVendorid = invoiceId;
    editPayment();
    addPayment();
});
function initMicsTabSearch(){
    searchdPayment(payVendorid);
    searchdMisc(micsid);
    
}

//dispute table
function searchdMisc(micsid){
	if (!window.ReportMiscPage) {
	    ReportMiscPage = new SANINCO.Page("msicList", "ReportMiscPage", {
	        sortingField: "account_code.account_code_name",
	        sortingDirection: "asc",
	        vo: "svo",
	        recordText: '',
			paginationDiv: "MiscList_pageTable",
	        recPerArray: [5, 10, 15, 20, 30],
	        totalPageUri: "doGetMiscTotalPageNo.action",
	        dataUri: "doMisc.action",
	        cols: [{
	            title: "Account Code",
	            dataField: "account_code_id",
	            sort: "account_code.account_code_name"
	        }, {
	            title: "Reconcile Amount ",
	            dataField: "reconcile_amount",
	            sort: "reconcile.reconcile_amount"
	        }, {
	            title: "Notes",
	            dataField: "notes",
				className : "table_td_br",
				width : '260px',
	            sort: "reconcile.notes"
	        }, {
	            title: "Reconcile Date",
	            dataField: "reconcile_date", 
	            sort: "reconcile.reconcile_date"
	        }, {
	            title: "Created By",
	            dataField: "created_by",
	            sort: "`user`.user_name"
	        }, {
	            title: "Attachment",
	            dataField: function(obj){
	                return (obj.attachment_point_id_down ? "<img  src=\"include/images/download1.gif\"  style=\"cursor: pointer;\" onclick=\"SANINCO.Download(" + obj.attachment_point_id_down + ");\">" : "");
	            }
        }]
    });
			
		ReportMiscPage.addTotalSuccessEvent(function(data){
			if(data.totalResultNo <= 0 || data.error){
				document.getElementById('__save_excel_dispute_payback_payments_misc').style.display = "none";
			}else{
				document.getElementById('__save_excel_dispute_payback_payments_misc').style.display = "block";
			}
		});
	}
	
    ReportMiscPage.voParam = {
        banId : currentBanId,
        invoiceId : invoiceId,
        banId1 : currentBanId,
        invoiceId1 : invoiceId
    };
    
    ReportMiscPage.start();
}
function miscMoveToCredit(){
	var cs = $("input:checked[name='miscMoveCheckBox']");
	if(cs.length){
		if(judgementRightsDispute("")) return;
		if(window.confirm('You are going to move the checked to credit, please confirm')){
			var proposalBoxId = "";
			for(var i=0;i<cs.length;i++){
				if(proposalBoxId == ""){
					proposalBoxId = proposalBoxId + $(cs[i]).val();
				}else{
					proposalBoxId = proposalBoxId + ","  + $(cs[i]).val();
				}
			}
			var callback = {
				success: function(o){
					if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
		                window.location.reload();
		                return;
		            }
					var data = eval("("+o.responseText+")");
					if(data.error){
						alert("Error: " + data.error);
						return;
					}
					ReportPaymentPage.reload();
				},
				failure:showError
			};
			var dd = "svo.boxInId=" + proposalBoxId;
			YAHOO.util.Connect.asyncRequest("POST", actionUri.updateProposalToCreditUri, callback ,dd);
		}
	}else{
		$('#divMiscId').html('You have not selected any item !');
		setTimeout(function(){
			$('#divMiscId').html(' ');
		},3000);
	}
}
//payment table
function searchdPayment(payVendorid){
	if (!window.ReportPaymentPage) {
	    ReportPaymentPage = new SANINCO.Page("paymentList", "ReportPaymentPage", {
	        sortingField: "a.account_code_name",
	        sortingDirection: "asc",
	        vo: "svo",
	        recordText: '',
	        paginationDiv: "paymentList_pageTable",
	        recPerArray: [5, 10, 15, 20, 30],
	        totalPageUri: "doGetPaymenttablaTotalPageNo.action",
	        dataUri: "doPaymenttabla.action",
	        cols: [{
	            title: "",
	            dataField: function(obj){
					  return "<input type='checkbox' name='miscMoveCheckBox' value='"+obj.id+"'/>";
				}
	        },{
	            title: "SCOA",
	            dataField: "account_code_name",
	            sort: "a.account_code_name"
	        }, {
	            title: "Amount",
	            dataField: "payment_amount",
	            sort: "proposal.payment_amount"
	        }, {
	            title: "Credit",
	            dataField: "creditAmount",
	            sort: "proposal.credit_amount"
	        }, {
	            title: "Notes",
	            dataField: "description",
				className : "table_td_br",
				width : '260px',
	            sort: "proposal.notes"
	        }, {
	            title: "Delete",
	            dataField: function(obj){
					if(invoiceMiscActionRole){
	                return "<img src='include/images/reject16.png' onclick=\"deleteId('" + obj.id + "')\"/>";
					}
	            }
	        }, {
	            title: "Edit",
	            dataField: function(obj){
					if (invoiceMiscActionRole) { // Modified By Chao.Liu On The 2010/09/11 AM
						return "<img src='include/images/Edit-icon-s.png' onclick=\"editPaymentShow(" + "'" + obj.id + "','" + obj.account_code_id + "','" + obj.payment_amount + "','" + ccmEscape(obj.description) + "'" + ")\" >";
					}
			    }
	            
	        }, {
	            title: "Attachment",
	            dataField: function(obj){
	                return (obj.attachment_point_id_down_payment ? "<img  src=\"include/images/download1.gif\"  style=\"cursor: pointer;\" onclick=\"SANINCO.Download(" + obj.attachment_point_id_down_payment + ");\">" : "");
	            }
	            
	        }]
	    });
		
		ReportPaymentPage.addTotalSuccessEvent(function(data){
			if(data.totalResultNo <= 0 || data.error){
				document.getElementById('__download_excel_misc_payment').style.display = "none";
			}else{
				document.getElementById('__download_excel_misc_payment').style.display = "block";
			}
		});
	}
	
    ReportPaymentPage.voParam = {
        banId : currentBanId,
        invoiceId : invoiceId,
        banId1 : currentBanId,
        invoiceId1 : invoiceId
    };
	if (!invoiceMiscActionRole) {
		ReportPaymentPage.cols[3].display = "none";
		ReportPaymentPage.cols[4].display = "none";
	}
    ReportPaymentPage.start();
}

//edit payment div
function editPayment(){
    editPaymentPanel = new YAHOO.widget.Panel("eidtPaymentMiscPanelDiv", {
        width: "400px",
        fixedcenter: true,
        visible: false,
        constraintoviewport: true,
        close: true,
        modal: false
    });
    editPaymentPanel.render();
}
//Edit layer
function editPaymentShow(id, account_code_id, payment_amount, description){
	if (!confirm("Please confirm to Edit.")) {
        return;
    }
    if (judgementRights("divMiscId")) 
        return;
    for (var i = 0; i < payment_amount.length; i++) {
        payment_amount = payment_amount.replace(",", "");
    }
    editePayment_id = id;
    document.getElementById("editPaymentDivPopalse").value = id;
	editAccountCodeId.setValue(account_code_id);
    document.getElementById("editPaymentmAmount").value = payment_amount;
    document.getElementById("edittxtRDescription").value =unescape(description);
	document.getElementById('editUploadsid').outerHTML += "";
	//document.getElementById('__up_load_text_invoiceDetail_6').value = "";
    editPaymentPanel.show();
}

//Bind the select method. Realizing
function jsSelectIsExitItem(objSelect, objItemValue){
    var ops = objSelect.options;
    for (var i = 0; i < ops.length; i++) {
        if (ops[i].innerHTML == objItemValue) {
            ops[i].selected = true;
            break;
        }
    }
}

//add payment div
function addPayment(){
    addPaymentPanel = new YAHOO.widget.Panel("addPaymentMiscPanelDiv", {
        width: "510px",
        fixedcenter: true,
        visible: false,
        constraintoviewport: true,
        close: true,
        modal: false
    });
    addPaymentPanel.render();
}
//add layer
function addPaymentShow(){
    if (judgementRights("divMiscId")) 
        return;
    YAHOO.util.Dom.get("mpPaymentmAmount").value = "";
    YAHOO.util.Dom.get("mptxtRDescription").value = "";
/*    document.getElementById('uploadsPaymentId_1').outerHTML += "";
    document.getElementById('__up_load_text_invoiceDetail_uploadsPaymentId_1').value = "";*/
    //document.getElementById('mpAccountCodeId').selectedIndex = 0;
    $("#addAdjustmentFile").html(" ");
	var string ="<div id =\"invoiceDetail_uploadsPaymentId_1\" style=\"padding:3px 0 0 0;height: 19px;\"><div style=\"float:left;\">"	 
    +"<input style=\"height:19px;width:220px;\" type='text' id=\"__up_load_text_invoiceDetail_uploadsPaymentId_1\" disabled=\"disabled\">"
    +"</div><div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"
	+"<input id=\"uploadsPaymentId_1\" onchange=\"document.getElementById('__up_load_text_invoiceDetail_uploadsPaymentId_1').value=this.value;\" class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
	+"<input style=\"height:19px;width:60px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/></span></div>"
	+"<div class=\"approveicon\" onclick=\"deletefile('invoiceDetail_uploadsPaymentId_1');\"></div>"
	+"<div class=\"clear\" ></div></div>"
	
	+"<div id =\"invoiceDetail_uploadsPaymentId_2\" style=\"padding:3px 0 0 0;height: 19px;\"><div style=\"float:left;\">"	 
    +"<input style=\"height:19px;width:220px;\" type='text' id=\"__up_load_text_invoiceDetail_uploadsPaymentId_2\" disabled=\"disabled\">"
    + "</div><div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"
 	+"<input id=\"uploadsPaymentId_2\" onchange=\"document.getElementById('__up_load_text_invoiceDetail_uploadsPaymentId_2').value=this.value;\" class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
 	+"<input style=\"height:19px;width:60px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/></span></div>"
 	+"<div class=\"approveicon\" onclick=\"deletefile('invoiceDetail_uploadsPaymentId_2');\"></div>"
 	+"<div class=\"clear\" ></div></div>"
 	
 	+"<div id =\"invoiceDetail_uploadsPaymentId_3\" style=\"padding:3px 0 0 0;height: 19px;\"><div style=\"float:left;\">"	 
    +"<input style=\"height:19px;width:220px;\" type='text' id=\"__up_load_text_invoiceDetail_uploadsPaymentId_3\" disabled=\"disabled\">"
    + "</div><div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"
 	+"<input id=\"uploadsPaymentId_3\" onchange=\"document.getElementById('__up_load_text_invoiceDetail_uploadsPaymentId_3').value=this.value;\" class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
 	+"<input style=\"height:19px;width:60px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/></span></div>"
 	+"<div class=\"approveicon\" onclick=\"deletefile('invoiceDetail_uploadsPaymentId_3');\"></div>"
 	+"<div class=\"clear\" ></div></div>";
	
	$("#addAdjustmentFile").append(string);
	mpAccountCodeId.setValue("");
    addPaymentPanel.show();
}

//After Add callback
function ssignment(o){
    if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
        window.location.reload();
        return;
    }
    var data = eval("(" + o.responseText + ")");
    if (data.error) {
        alert(" Assignment Error:" + data.error);
        return;
    }
}

//edit proposal
function edit(){
    var mpAccountCodeIds = editAccountCodeId.getValue();
    if (mpAccountCodeIds == "") {
        alert('Please select one of the options below');
        return;
    }
    var mpPaymentmAmounts = YAHOO.util.Dom.get("editPaymentmAmount").value;
    var mptxtRDescriptions = YAHOO.util.Dom.get("edittxtRDescription").value;
    var callback = {
        success: function(){
            ReportPaymentPage.reload();
            editPaymentPanel.hide();
        },
        failure: showError
    };
    var addPayPramer = "mAccountCodeId=" + mpAccountCodeIds + "&" + "mPaymentAmount=" + mpPaymentmAmounts + "&" + "mSortingDirection=" + mptxtRDescriptions + "&" + "invoicePayIdd=" + editePayment_id;
    YAHOO.util.Connect.asyncRequest('POST', "doEditMiscProposalPayment.action", callback, addPayPramer);
}

//delete proposal
function deleteId(id){
    if (judgementRights("divMiscId")) 
        return;
    if (!confirm("Are you sure you want to delete this adjustment?")) {
        return false;
    }
    var callback = {
        success: function(){
            ReportPaymentPage.reload();
        },
        failure: showError
    };
    var addPayPramer = "proPaslId=" + id;
    YAHOO.util.Connect.asyncRequest('POST', "doDeleteMiscProposalPayment.action", callback, addPayPramer);
}
var aaf=100;
function addAdjustment(){
	aaf++;
	var string ="<div id =\"invoiceDetail_uploadsPaymentId_"+aaf+"\" style=\"padding:3px 0 0 0;height: 19px;\"><div style=\"float:left;\">"	 
		          +"<input style=\"height:19px;width:220px;\" type='text' id=\"__up_load_text_invoiceDetail_uploadsPaymentId_"+aaf+"\" disabled=\"disabled\">"
                 + "</div><div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"
				+"<input id=\"uploadsPaymentId_"+aaf+"\" onchange=\"document.getElementById('__up_load_text_invoiceDetail_uploadsPaymentId_"+aaf+"').value=this.value;\" class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
				+"<input style=\"height:19px;width:60px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/></span></div>"
				+"<div class=\"approveicon\" onclick=\"deletefile('invoiceDetail_uploadsPaymentId_"+aaf+"');\"></div>"
				+"<div class=\"clear\" ></div></div>";		
	$("#addAdjustmentFile").append(string);
}
//Add validation and methods
function addChecking(){
    var impAccountCodeIds = mpAccountCodeId.getValue();
    var addPaymentmAmount = document.getElementById("mpPaymentmAmount").value;
	var addMedian=addPaymentmAmount.indexOf(".");
	var addPaymentAmountNo=addPaymentmAmount.substring(0,addMedian);
    var imptxtRDescriptions = YAHOO.util.Dom.get("mptxtRDescription").value;
	var aNotes=document.getElementById("mptxtRDescription");
	
	var bo = uploadFileValidate("addAdjustmentFile");
	if(!bo){
		return false;
	}
    if (addPaymentmAmount == "" || addPaymentmAmount == 0) {
        alert('Adjustment Amount should not be zero or empty!');
        return false;
    } else 
        if (impAccountCodeIds == "") {
            alert('Please select one of the options below!');
            return false;
        } else 
//            if (imptxtRDescriptions.length <= 10) {
//				aNotes.className="textarea2";
//                alert('notes,Can not less than 10 characters!');
//                return false;
//            }
//			else 
            if (addPaymentAmountNo.length >=16||parseInt(addPaymentmAmount)>=1000000000000000||parseInt(addPaymentmAmount)<=-1000000000000000) {
                alert('The maximum amount shall not exceed 15!');
                return false;
            }
				aNotes.className='textarea1';
    return true;
}
//Edit validation and methods
function editChecking(){
    var ieditAccountCodeId = editAccountCodeId.getValue;
    var ieditPaymentmAmount = document.getElementById("editPaymentmAmount").value;
	var median=ieditPaymentmAmount.indexOf(".");
	var editPaymentAmountNo=ieditPaymentmAmount.substring(0,median);
    var iedittxtRDescriptions = YAHOO.util.Dom.get("edittxtRDescription").value;
	var eNotes=document.getElementById("edittxtRDescription");
    if (ieditPaymentmAmount == "" || ieditPaymentmAmount == 0) {
        alert('PaymentAmount amount should be greater than zero!');
        return false;
    } else 
        if (ieditAccountCodeId == "") {
            alert('Please select one of the options below');
            return false;
        } else 
	            if (editPaymentAmountNo.length >=16) {
	                alert('The maximum amount shall not exceed 15!');
	                return false;
	            }
				eNotes.className='textarea1';
    return true;
}
//download Excel
downloadExcelMiscPayment = function (){
	var titles = ReportPaymentPage.getTitlesParam("titles");
	//var titles = "titles=SCOA&titles=Amount&titles=Credit&titles=Notes";
	window.location.href="saveExcelByPaymentTabMisc.action?"+ titles +"&"+ ReportPaymentPage.paramStr;
}
saveExcelByDisputePaybackPaymentsMisc = function(){
	var titles = ReportMiscPage.getTitlesParam("titles");
	window.location.href= "saveExcelByDisputeMisc.action" + "?"+ titles +"&"+ ReportMiscPage.paramStr;
}
//PaymentAmount validation
function checkThePaymentAmount(e){
    if (isNaN(e.value) || (e.value <= 0) || (e.value == "")) {
        alert("Only allowed to enter the Numbers and paymentAmount must more than 0");
        e.value = "";
		return;
    }
	if(e.value.length>=15){
		alert("Please enter the number have not more than 16 char!");
		return;
	}
}