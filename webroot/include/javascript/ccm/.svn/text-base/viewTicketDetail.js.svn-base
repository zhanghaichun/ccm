//@auchor xinyu.Liu (Optimization of complete by xinyu.Liu)
var isOneSubmit = true;

actionUri = {
	saveTicketUri : "doSaveTicket.action",
	updateTicketUri : "doUpdateTicket.action",
	searchTicketUri : "doSearchTicketDetail.action",
	toSearchTicketUri : "searchTicket.action",
	getTicketDetailTotalPageNoUri : "doGetTicketDetailSearchTotalPageNo.action"
};

YAHOO.util.Event.onDOMReady(function () {
	initSaveTicket();
	if(ticketId == null){
		initializeList(['pid','sid','tsid']);
	}else{
		startSearchTicketDetail();
	} 
});

//Verification can not upload empty
function verificationNull(){
	if($('#__up_load').val() == "" ){
		alert('Please select upload file!');
		return false;
	}else{
		return true;
	}
	
}

//Initialize the drop-down list
function initializeList(obj){
	for(var i = 0; i < obj.length; i++){
		var opt = document.getElementById(obj[i]).options;
		for(var j = 0; j < opt.length; j++){
			if (opt[j].innerHTML == "Middium" || opt[j].innerHTML == "Regular" || opt[j].innerHTML == "New") 
			opt[j].selected = true;
		}
	}
}

function composePostDataFromUi(){
	var postData = "searchTicketVO.ticketId=" + ticketId
	+ "&searchTicketVO.priorityId=" + $('#pid').val()
	+ "&searchTicketVO.severityId=" + $('#sid').val()
	+ "&searchTicketVO.statusId=" + $('#tsid').val();
	if($('#__comment').val() != "") postData += ("&searchTicketVO.comment=" + ccmEscape($('#__comment').val())); 
	if($('#__title').val() != "") postData += ("&searchTicketVO.title=" + ccmEscape($('#__title').val()));
	if($('#__content').val() != "") postData += ("&searchTicketVO.content=" + ccmEscape($('#__content').val()));
	if($('#__attachment_point_id').val() != "") postData += ("&searchTicketVO.attachmentPointId=" + ccmEscape($('#__attachment_point_id').val())); 
	return postData;
}

//Title input box validation
function verificationText(){
	var title = $('#__title').val();
	if (title == "") {
		removeClassName('__title', 'validation-passed');
		addClassName('__title', 'validation-failed');
		return;
	} else {
		removeClassName('__title', 'validation-failed');
		addClassName('__title', 'validation-passed');
		YAHOO.ccm.container.saveTicket.show();
	}
}

//save Ticket
function saveTicket(){
	if(ticketId != null) {
		updateTicket();
	} else {
		addTicket();
	} 
}

function startSearchTicketDetail(){
	if (!window.ticketHistoryPage) {
		ticketHistoryPage = new SANINCO.Page('_dataTable', 'ticketHistoryPage', {
			sortingField: 'th.created_timestamp',
			sortingDirection: 'desc',
			vo: 'searchTicketVO',
			pageTable: 'block',
			height : '100%',
			totalPageUri: actionUri.getTicketDetailTotalPageNoUri,
			dataUri: actionUri.searchTicketUri,
			paginationDiv: '_dataTable_page',
			recPerArray: [10, 20, 30, 40, 50, 70, 90, 100],
			cols: [
				  {title: "Created Time", dataField: "timestamp"
				},{title: "Created By", dataField: "createdName"
				},{title: "Content", className : "table_td_br",width:"260px", dataField: "content"
				},{title: "Comment",className : "table_td_br",width:"260px", dataField: "comment"
				},{title: "Action", dataField: function(o){
						return o.pid_action + "<span style='padding-left:30px;'>" + o.pid_action2 +"</span><br>"
						+ o.sid_action + "<span style='padding-left:30px;'>" + o.sid_action2 + "</span><br>"
						+ o.tsid_action + "<span style='padding-left:30px;'>" + o.tsid_action2 + "</span>"; 
					}
				},{title: "Download",dataField:function(o){
							if (o.attachmentPointId != "") {return "<img src=\"include/images/download1.gif\" style=\"cursor: pointer;\" onclick=\"SANINCO.Download(" + o.attachmentPointId + ");\"/>";}
							else{return "";}
						}
				}
			]
		});
	}
	
	ticketHistoryPage.myParam = composePostDataFromUi();
	
    ticketHistoryPage.start();
}

//clear data 
function dataClear(){
	emptyValue(['__attachment_point_id','__title','__content','__comment']);
	initializeList(['pid','sid','tsid']);
}

function dataReset(){
	window.location.reload();
}

//update Ticket Data
function updateTicket(){
	var callback = {
		success: function(o){
			if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
				window.location.reload();
				return;
			}
			var data = eval("("+o.responseText+")");
			if(data.error){
				hideLoadingModalLayer();
				alert("Error: " + data.error);
				window.location.reload();
				return;
			}
			isOneSubmit = true;
			window.location.reload();
		},
		failure: showError
	};
	
	previousPostedData = composePostDataFromUi();
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest('POST' , actionUri.updateTicketUri , callback, previousPostedData);
}

//New Ticket Data
function addTicket(){
	var callback = {
		success: function(o){
			if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
				window.location.reload();
				return;
			}
			var data = eval("("+o.responseText+")");
			if(data.error){
				hideLoadingModalLayer();
				alert("Error: " + data.error);
				window.location.reload();
				return;
			}
			isOneSubmit = true;
			window.location = actionUri.toSearchTicketUri;
		},
		failure: showError
	};
	
	previousPostedData = composePostDataFromUi();
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest('POST' , actionUri.saveTicketUri , callback, previousPostedData);
}

function upLoadTicket(attachmentPointId){
	document.getElementById('__up_load').outerHTML += "";
	$('#__up_load_text').val('');
	$('#__attachment_point_id').val(attachmentPointId);
}

function initSaveTicket() {
	var handleSubmit = function() {
		if(!isOneSubmit){return false;}
		isOneSubmit = false;
		saveTicket();
	};
	var handleCancel = function() {
		this.cancel();
	};
	YAHOO.util.Dom.removeClass('saveTicket', 'save-ticket');
	
	YAHOO.ccm.container.saveTicket = new YAHOO.widget.Dialog('saveTicket', 
							{ width : "30em",
							  fixedcenter : true,
							  visible : false, 
							  modal : true,
							  constraintoviewport : true,
							  buttons : [ { text:"Submit", handler:handleSubmit},
								      { text:"Cancel", handler:handleCancel } ]
							});
	YAHOO.ccm.container.saveTicket.render();
};
