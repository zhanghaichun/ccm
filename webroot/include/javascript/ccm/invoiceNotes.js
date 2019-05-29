var getInvoiceNoteTotalPageNoUri="doGetInvoiceNoteTotalPageNoUri.action";
var searchInvoiceNoteUri="doGetInvoiceNoteUri.action";
var doSaveInvoiceNotesUri="doSaveInvoiceNotes.action";
var runRepoetUri="runRepoet.action";
var maxNotesBackup="";

YAHOO.util.Event.onDOMReady(function(){
	
		invoiceNote = new SANINCO.Page('_dataTable',"invoiceNote",{
		    sortingField : "t.created_timestamp",
		    sortingDirection : "desc",
		    recordText: "",
			vo : "invoiceNoteVO",
			pageTable : "block",
		    totalPageUri : getInvoiceNoteTotalPageNoUri,
		    dataUri : searchInvoiceNoteUri,
			paginationDiv : "_dataTable_page",
			recPerArray : [5,10,20,40,60,80,100],
		    cols : [
					{ title : "Notes",dataField:function(o){
						return {less_sign:"<div class=\"td-note-wrap\" style=\"white-space: pre-wrap;\">"+(o.notes).replace(/\</g,"&lt;")+"</div>"}
						},sort : "t.notes", filtration : {name:"t.notes",alias:"Notes"},className:"td-note-wrap"
					},
					{ title : "Analyst Name",dataField: "analyst",sort : "concat(u.first_name,u.last_name)", filtration : {name:"concat(u.first_name,blank_space(),u.last_name)",alias:"Analyst Name"}
					},
					{ title : "Created Date",dataField: "created_timestamp",sort : "t.created_timestamp", filtration : {name:"t.created_timestamp",alias:"Created Date"}
				    },
					{ title : "File",dataField:function(o){
						return (o.attachmentPoint == "" ? "" : "<img src=\"include/images/download1.gif\" style=\"cursor: pointer;\" onclick=\"SANINCO.Download(" + o.attachmentPoint + ");\"/>");}
				    }
				]
		}); 
		

			
	filter1 = new SANINCO.Filter();
	filter1.addEditeEvent(function(){invoiceNote.start();});
	filter1.add('t.created_timestamp', 'String');
	filter1.add("concat(u.first_name,blank_space(),u.last_name)", "String");
	filter1.add('t.notes', 'String');
	invoiceNote.setFilter(filter1);
	
	invoiceNote.myParam=composePostDataFromUi();
	invoiceNote.start();
	initInvoiceNoteWindow();
	YAHOO.ccm.container.sendInvoiceWindow = new YAHOO.widget.Dialog("sendInvoiceWindow", { 
		width: "60em",
        fixedcenter: true,
        visible: false,
        constraintoviewport: true,
        modal: true
	});
	
	YAHOO.ccm.container.sendInvoiceWindow.render();
	//-------------------------------
});

function initInvoiceNoteWindow(){
	YAHOO.ccm.container.InvoiceNoteWindow = new YAHOO.widget.Dialog("InvoiceNoteWindow", {
        width: "37em",
        fixedcenter: true,
        visible: false,
    	modal : true,
        constraintoviewport: true
    });
    YAHOO.ccm.container.InvoiceNoteWindow.render();
    selectUserDiv = new USERLIST.Page("selectUserDiv",{width:"97%"});
}
function filesProcess(files,type){
	for( var i = 0; i < files.length; i++) {
		var file = files[i];
		//1:invoice detail notes window ; 2:approve notes window
		if(type == 1){
			var d ='<div class="bannw23" ><div class="att234">'+
						'<span class="bzhx mr2"></span>'+
						'<span class="atane">'+file.name+'</span>'+
						'<span class="czgd mr2" onclick="removeItemFile(this,'+type+')"></span>'+
						'<input name="effectiveFile" type="hidden" value="'+file.name+':'+file.size+'"/>'+
					'</div></div>';
			$("#addNoteFile").append(d); 
		}else if(type == 2){
			var d ='<div class="bannw2367" ><div class="att23467">'+
						'<span class="bzhx mr2"></span>'+
						'<span class="atane">'+file.name+'</span>'+
						'<span class="czgd mr2" onclick="removeItemFile(this,'+type+')"></span>'+
						'<input name="effectiveFile" type="hidden" value="'+file.name+':'+file.size+'"/>'+
					'</div></div>';
			$("#addFile").append(d); 
		}
	}
	
	
	
	//1:invoice detail notes window ; 2:approve notes window
	if(type == 1){
		var newAttach = '<input style="text-align:center;width:70px;height: 19px;position: absolute;top: 0;left: 0;z-index: 100;opacity: 0;cursor: pointer;" ' +
		'type="file" name="uploads" multiple="multiple" onchange="filesProcess(this.files,'+type+')" />';
		
		$("#attachDiv :input").each(function () {
			var this_name = $(this).attr("name");
			$(this).hide();
		});
		$("#attachDiv").append(newAttach);
	}else if(type == 2){
		
		var newAttach = '<input style="text-align:center;width:70px;height: 19px;position: absolute;top: 10;left: 27px;z-index: 100;opacity: 0;cursor: pointer;" ' +
		'type="file" name="uploads" multiple="multiple" onchange="filesProcess(this.files,'+type+')" />';
		
		$("#approveAttachDiv :input").each(function () {
			var this_name = $(this).attr("name");
			$(this).hide();
		});
		$("#approveAttachDiv").append(newAttach);
	}
	
}

function removeItemFile(item,type){
	$(item).parent().parent().remove();
	//1:invoice detail notes window ; 2:approve notes window
	if(type == 1){
		if($("#addNoteFile").children().length == 0 ){
			cleanHiddenAtach(type);
		}
	}else if(type == 2){
		if($("#addFile").children().length == 0 ){
			cleanHiddenAtach(type);
		}
	}
	
}

function uploadSuccessNotes(messageId,channelId){
	 invoiceNote.reload();
     if("undefined" != typeof invoiceNote2)
     invoiceNote2.reload();
     if(messageId != "" && channelId != ""){
    	 jQuery.get("http://" + window.location.host + "/ccmApp/message.do?method=pushWebMessage&channelId="+channelId+"&messageId="+messageId+"",null);
     }
     invoiceTabInit.get('tab9');
}
function saveNotes(){
	
	var flag=true;
	if($.trim($("#ps").val()) == ""){
		flag=false;
		alert('The notes is required!');
		return;
	}
	if($.trim($("#ps").val()).length>255){
		flag=false;
		alert('The notes more than 255 words');
		return;
	}
	// 文件格式验证
/*	if(!uploadFileValidate()){
		flag=false;
		return;
	}*/
	$("[name='noteUserId']").val(selectUserDiv.getSelectUserList());
	$("[name='noteRoleId']").val(selectUserDiv.getSelectRoleList());
	
	showLoadingModalLayer();
	document.getElementById("handleInvoiceNoteActionForm").submit();
	//hideLoadingModalLayer();
	YAHOO.ccm.container.InvoiceNoteWindow.hide();
}

//function pushWebMessage(){
//	
//}

function clearNotes(){
	$('#ps').val('');
	$('#num').html(255);
	$("#addNoteFile").html(""); 
	cleanHiddenAtach(1);
	selectUserDiv.cleanSelect();
	$("#privateCheckbox").attr("checked",'');
	$("#selectUserRow").show();
	
	var imageName = $("#starDiv").css("background-image");
		imageName = imageName.replace("message-icon_54","message-icon_53");
		$("[name='starFlag']").val("N");
		$("#starDiv").css("background-image",imageName);
	
}

function cleanHiddenAtach(type){
	//clean hidden attach
	
	//1:invoice detail notes window ; 2:approve notes window
	if(type == 1){
		var newAttach = '<input style="text-align:center;width:70px;height: 19px;position: absolute;top: 0;left: 0;z-index: 100;opacity: 0;cursor: pointer;" ' +
		'type="file" name="uploads" multiple="multiple" onchange="filesProcess(this.files,'+type+')" />';
		newAttach += '<div class="hjdpdiy3 butatc">Attach</div>';
		
		$("#attachDiv").empty();
		$("#attachDiv").append(newAttach);
	}else if(type == 2){
		var newAttach = '<input style="text-align:center;width:70px;height: 19px;position: absolute;top: 10;left: 27px;z-index: 100;opacity: 0;cursor: pointer;" ' +
		'type="file" name="uploads" multiple="multiple" onchange="filesProcess(this.files,'+type+')" />';
		newAttach += '<div class="arpbutatc">Attach</div>';
		
		$("#approveAttachDiv").empty();
		$("#approveAttachDiv").append(newAttach);
	}
}

function htmlEncode(value){
	value = value.replace(/\\/g,"\\\\");
	return value;
}

function privateOnclick(){
	if($("#privateCheckbox").attr("checked")){
		$("#selectUserRow").hide(); 
		selectUserDiv.enableFlag = false;
	}else{
		$("#selectUserRow").show();
		selectUserDiv.enableFlag = true;
	}
}

function starOnclick(){
	var imageName = $("#starDiv").css("background-image");
	if($("[name='starFlag']").val() == "N"){
		imageName = imageName.replace("message-icon_53","message-icon_54");
		$("[name='starFlag']").val("Y");
	}else{
		imageName = imageName.replace("message-icon_54","message-icon_53");
		$("[name='starFlag']").val("N");
	}
	$("#starDiv").css("background-image",imageName);
}

function serachInvoiceNote(addBanNoteFlag){
	YAHOO.ccm.container.InvoiceNoteWindow.show();
	clearNotes();
	var ps = $("#ps");
	ps.keyup(check_keyup).bind('paste', function () {
		setTimeout(check_keyup, 50);
	});
	$("#addBanNoteFlag").val(addBanNoteFlag);  
	if(addBanNoteFlag){
		$("#addTitle").html("Add Notes");
		$("#invoiceNumberFlag").hide();	
	}
	else{
		$("#addTitle").html("Add Attachment");	
		$("#invoiceNumberFlag").show();
	}
	
	ps.keydown(check_keydown).bind('paste', function () {
		setTimeout(check_keydown, 50);
	});
	
	function check_keyup() {
		var num = 255 - ps.val().length;
		if (num < 0) {
			ps.val(ps.val().substr(0,255));
		}
		$("#num").html(255 - ps.val().length);
	}
	
	function check_keydown() {
		var num = 255 - ps.val().length;
		if (num < 0) {
			ps.val(ps.val().substr(0,255));
		}
	}
}

function composePostDataFromUi(){
	var postDate ="invoiceNoteVO.banId=" + $('#invoiceNoteBanIdByinvoice').val();
	return postDate;
}

function eternalApproval(){
	var callback = {
		success: function(o){
			if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
				window.location.reload();
				return;
			}
			$(".sendInvoiceWide").html(o.responseText);
			YAHOO.ccm.container.sendInvoiceWindow.show();
		},
		failure:showError
	};
	var data = "invoiceId="+invoiceId;
	YAHOO.util.Connect.asyncRequest("POST","searchInvoiceAttachment.action", callback, data);
}

function sendtoInvoiceEmail () {
	
	var filesSize = 0;
	
	$(".czgdIcon").each(function(index){   
		filesSize = filesSize + parseInt($(this).attr("value"));
	});
	
    if(filesSize>5242880) {
    	alert("Please send attachments is less than 5M.");
    	return;
    }
	
	if (document.getElementById('_contactVendor_to').value == null || document.getElementById('_contactVendor_to').value == '') {
		alert("To address cannot be empty.");
		return;
	}
	showLoadingModalLayer();
	document.getElementById('sendInvoiceEmail').submit();
	
}


function filesSendInvoiceProcess(files){
	 
	for( var i = 0; i < files.length; i++) {
		var file = files[i];
			var d ='<div style= " width : 300px;" class="clearfix"><div>'+
						'<div class="left"><span class="bzhx"></span>'+
						'<span class="ataneFileName" style= "overflow:hidden ; width : 250px;">'+file.name+'</span></div>'+
						'<span class="czgdIcon" onclick="removeSendInvoiceItemFile(this)" value ="'+file.size+'"></span>'+
						'<input name="uploadsFileName" type="hidden" value="'+file.name+':'+file.size+'"/>'+
					'<div style="clear: both;"></div></div></div>';
			$("#addSendInvoiceEmailFile").append(d); 
			
	}
	
	var newAttach = '<input style="text-align:center;width:70px;height: 19px;position: absolute;top: 0;left: 0;z-index: 100;opacity: 0;cursor: pointer;" ' +
	'type="file" name="uploads" multiple="multiple" onchange="filesSendInvoiceProcess(this.files)" />';
	
	$("#attachDiv1 :input").each(function () {
		var this_name = $(this).attr("name");
		$(this).hide();
	});

	$("#attachDiv1").append(newAttach);

}
function removeSendInvoiceItemFile(item){
    $(item).parent().parent().remove();
	if($("#addSendInvoiceEmailFile").children().length == 0 ){

		sendInvoicecleanHiddenAtach();
	}	
}

function sendInvoicecleanHiddenAtach(){

	var newAttach = '<input style="text-align:center;width:70px;height: 19px;position: absolute;top: 0;left: 0;z-index: 100;opacity: 0;cursor: pointer;" ' +
	'type="file" name="uploads" multiple="multiple" onchange="filesSendInvoiceProcess(this.files)" />';
	newAttach += '<div class="attachBorder butata">Attach</div>';
	
	$("#attachDiv1").empty();
	$("#attachDiv1").append(newAttach);

}

function hideInvoiceWindow(){
	hideLoadingModalLayer();
	YAHOO.ccm.container.sendInvoiceWindow.hide();
}

/**
 * 生成 Report
 * @return
 */
function runRepoet(){
	showLoadingModalLayer();
	var uri = runRepoetUri+"?invoiceId="+invoiceId;
	windowOpen(uri);
	hideLoadingModalLayer();
}