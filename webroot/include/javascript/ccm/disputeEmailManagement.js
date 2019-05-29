/**
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
createEmailsTableState = false;
createEmailsTableStateTimer = null;
//Pageloading 
YAHOO.util.Event.onDOMReady(function(){
    createEmailsAndAddEmailsTable();
    YAHOO.util.Dom.removeClass("EditTheDisputeEmail", "EditTheDisputeEmail");
    YAHOO.ccm.container.EditTheDisputeEmail = new YAHOO.widget.Dialog("EditTheDisputeEmail", {
        width: "60em",
        fixedcenter: true,
        visible: false,
        constraintoviewport: true
    });
    YAHOO.ccm.container.EditTheDisputeEmail.render();
    YAHOO.util.Dom.removeClass("email_attachfilelist_window", "email_attachfilelist_window");
    YAHOO.ccm.container.email_attachfilelist_window = new YAHOO.widget.Dialog("email_attachfilelist_window", {
        width: "60em",
        fixedcenter: true,
        visible: false,
        constraintoviewport: true
    });
    YAHOO.ccm.container.email_attachfilelist_window.render();
});

function EditTheDisputeEmailCommit(){
	YAHOO.util.Connect.setForm("form100");
	YAHOO.util.Connect.asyncRequest('POST' , "updateEmailByEmail.action" , {
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
			YAHOO.ccm.container.EditTheDisputeEmail.hide();
			saveEditCallBack();
		},
		failure: showError
	});
}

function createEmailsAndAddEmailsTable(){
    var callback = {
        success: function(){
            createEmailsTableState = true;
        },
        failure: function(o){
            createEmailsTableState = true;
            showError(o);
        }
    };
    YAHOO.util.Connect.asyncRequest("POST", "CreateEmialsAddEmailTable.action", callback, "");
}

function searchPreSendEmail(){
    if (!createEmailsTableState) {
        if (createEmailsTableStateTimer) {
            clearTimeout(createEmailsTableStateTimer);
        }
        createEmailsTableStateTimer = setTimeout(function(){
            searchPreSendEmail();
        }, 200);
        return;
    }
    if (!window.preEmailPage) {
        preEmailPage = new SANINCO.Page("_preEmailPage", "preEmailPage", {
            sortingField: "e.created_timestamp",
            sortingDirection: "desc",
            recordText: "",
            vo: "operationsVO",
            recPerArray: [5, 10, 15, 20, 30],
            totalPageUri: "getPreEmailsTotalPageNoLC.action",
            dataUri: "searchPreEmailsOfDispute.action",
            cols: [{
                title: function(obj){
                    return "<input type=\"checkbox\" class='noBorderRadioButton' name=\"leftbox\" onclick=\"chooseleftall()\"/>";
                },
                dataField: function(obj){
                    return "<input type=\"checkbox\" class='noBorderRadioButton' name=\"leftbox\" value=\"" + obj.id + "\" onclick=\"calibrationCheckboxLeft(this);\"/>";
                }
            }, {
                title: "Edit",
                dataField: function(obj){
                    return "<img src='include/images/Edit-icon-s.png' onclick=\"editRecodeFromProposalListLeft(" + obj.id + ");\"/>";
                }
            }, {
                title: "Email Status",
                dataField: function(obj){
                    if (obj.email_status == 0) {
                        return "Pretreatment";
                    }
                    if (obj.email_status == 1) {
                        return "Ready to send";
                    }
                    if (obj.email_status == 2) {
                        return "Send success";
                    }
                    if (obj.email_status == 3) {
                        return "Failed to send";
                    }
                },
                sort: "e.email_status"
            }, {
                title: "Subject",
                dataField: "subject",
                sort: "e.subject",
                filtration: {
                    name: "e.subject",
                    alias: "Subject"
                }
            }, {
                title: "To Address",
                dataField: "to_address",
                sort: "e.to_address",
                filtration: {
                    name: "e.to_address",
                    alias: "ToAddress"
                }
            }, {
                title: "Sent DateTime",
                dataField: "sent_datetime",
                sort: "e.sent_datetime"
            }, {
                title: "Attchment Point Id",
                dataField: function(obj){
					if(obj.attachment_point_id!=""){
			     		return "<img src='include/images/download1.gif' onclick=\"showTheAnnexsList(" + obj.attachment_point_id + ");\"/>";
					}else{
						return "";
					}
                },
                sort: "e.attachment_point_id"
            }]
        });

        preEmailPage.addCompleteEvent(function(o){
            echoLeftProposalIdLeft();
        });
        filter1 = new SANINCO.Filter();
        filter1.addEditeEvent(function(){
            preEmailPage.start();
        });
        filter1.add("e.email_type", "String");
        filter1.add("e.email_status", "String");
        filter1.add("e.subject", "String");
        filter1.add("e.to_address", "String");
        preEmailPage.setFilter(filter1);

    }
    preEmailPage.start();
}

//Echo proposalId
function echoLeftProposalIdLeft() {
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

function promptColor(e){
	if(e.checked){
		$(e).parent().parent().addClass("hightlight");
	}else{
		$(e).parent().parent().removeClass("hightlight");
	}
}

function calibrationCheckboxLeft(o){
    if (o.checked == true) {
        leftState["" + o.value] = true;
        $(o).parent().parent().addClass("hightlight");
    }
    else {
        leftState["" + o.value] = false;
        $(o).parent().parent().removeClass("hightlight");
    }
}

//get Recode(proposal) From ProposalList for edit
function editRecodeFromProposalListLeft(id) {
	if (!confirm("Please confirm to Edit.")) {
		return;
	}
	var callback = {success:editRecodeFromProposalListLeftcallback, failure:showError};
	var getEmailData = "emailId=" + id;
	YAHOO.util.Connect.asyncRequest("POST", "getEmailByEmailId.action", callback, getEmailData);
}

function editRecodeFromProposalListLeftcallback(o){
    var data = eval("(" + o.responseText + ")");
    editProposalData = data;
    var form = document.getElementById("form100");
    form["email.id"].value = data.id;
    form["email.recActiveFlag"].value = data.recActiveFlag;
    form["email.subject"].value = data.subject;
    form["email.toAddress"].value = data.toAddress;
    form["email.ccAddress"].value = data.ccAddress;
    form["email.bccAddress"].value = data.bccAddress;
    form["email.content"].value = data.content;
    form["email.attachmentPoint.id"].value = data.attachmentPoint;
    attachmentPointId = data.attachmentPoint;
    form["email.emailStatus"].value = getEmailStatusForEdit(data.emailStatus);
    form["email.sendDateTime"].value = data.sendTime;
    form["email.createdTimestamp"].value = data.createdTime;
    form["email.createdBy"].value = data.createdBy;
    form["email.modifiedTimestamp"].value = data.modifiedTime;

    form["email.referenceId"].value = data.referenceId;
    //	form["email.emailType"].value = data.emailType;
    form["email.notes"].value = data.notes;

    YAHOO.ccm.container.EditTheDisputeEmail.show();

}

function getEmailStatusForEdit(emailstatus){
    if (emailstatus == 0) {
        return "Pretreatment";
    }
    if (emailstatus == 1) {
        return "Ready to send";
    }
    if (emailstatus == 2) {
        return "Send success";
    }
    if (emailstatus == 3) {
        return "Failed to send";
    }
}

function saveEditCallBack(){
    delete leftState;
    leftState = {};
    preEmailPage.start();
}

function showTheAnnexsList(ATPId){
    searchAnnexsOfDisputeByATPId(ATPId);
    YAHOO.ccm.container.email_attachfilelist_window.show();
}

function searchAnnexsOfDisputeByATPId(ATPId){
    attachmentFileOfEmailPage = new SANINCO.Page("_attachmentFileOfEmail", "attachmentFileOfEmailPage", {
        sortingField: "attachment_file.created_timestamp",
        sortingDirection: "desc",
        recordText: "",
        vo: "searchInvoiceVO",
        recPerArray: [5, 10, 15, 20, 30],
        totalPageUri: "getAnnexsTotalPageNo.action?attachmentPointId=" + ATPId + "",
        dataUri: "searchAnnexsOfDispute.action?attachmentPointId=" + ATPId + "",
        cols: [{
            title: "Down Load",
            width: "10%",
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
        attachmentFileOfEmailPage.start();
    });
    filter1.add("attachment_file.id", "Integer");
    filter1.add("attachment_file.file_name", "String");

    attachmentFileOfEmailPage.setFilter(filter1);
    attachmentFileOfEmailPage.start();
}

function downloadFileO(name, path){
    var df = document.getElementById("downloadForm");
    df.filePath.value = path;
    df.fileName.value = name + ".xls";
    df.submit();
}

//Select All check box
function chooseleftall(){
    var checked = document.getElementsByName("leftbox")[0].checked;
    var selects = document.getElementsByName("leftbox");
    for (var i = 1; i < selects.length; i++) {
        selects[i].checked = checked;
        if (selects[i].checked == true) {
            leftState["" + selects[i].value] = true;
            $(selects[i]).parent().parent().addClass("hightlight");
        }
        else {
            leftState["" + selects[i].value] = false;
            $(selects[i]).parent().parent().removeClass("hightlight");
        }
    }
}

function sendSelectedEmail(){
    var callback = {
        success: sendSelectedEmailcallback,
        failure: showError
    };
    if (getEmailIdsLeft().length < 1) {
        alert("Please choose to send mail!");
        return;
    }
    var sendSelectEmailData = "emailIds=" + getEmailIdsLeft();
	if(sendSelectEmailData.length < 10){
		alert('please select!');
		return false;
	}
	showLoadingModalLayer();
    YAHOO.util.Connect.asyncRequest("POST", "sendCheckedEmailsAndChangeEmailStatus.action", callback, sendSelectEmailData);
}

//After sendSelectedEmail callback
function sendSelectedEmailcallback(){
    delete leftState;
    leftState = {};
    preEmailPage.start();
}

//Get proposalids
function getEmailIdsLeft() {
	var EmailIds = new Array();
	for (var i in leftState) {
		if (leftState[i] == true) {
			EmailIds.push(i);
		}
	}
	return EmailIds;
}

function showUploadForDisputeTabRightDiv(disputeId){
    YAHOO.ccm.container.UpLoadFileForDisputeTabRightDivId.show();
    document.getElementById("disputeIdRight").value = disputeId;
    searchAnnexsOfDisputeByDisputeId(disputeId);
}

function deleteRecodeFromAnnexsListOfDispute(Id){
    if (!confirm("Please confirm to delete.")) {
        return;
    }
    if (!validateIsTrue()) {
        return;
    }
    var callback = {
        success: "",
        failure: showError
    };
    var data = "attachmentPointId=" + Id;
    YAHOO.util.Connect.asyncRequest("POST", "deleteAnnexsOfDisputeById.action", "", data);
    searchAnnexsOfDisputeByDisputeId(document.getElementById("disputeIdRight").value);
}

