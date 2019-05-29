detailActionUri = {
  searchTransactionHistoryListUri : "searchTransactionHistoryList.action",
  searchDisputeNotesListUri : "searchDisputeNotesList.action",
  getDisputeNotesListPageNoUri :"getDisputeNotesListPageNo.action",
  searchDisputeActionRequestListUri : "searchDisputeActionRequestList.action",
  getDisputeActionRequestListPageNoUri : "getDisputeActionRequestListPageNo.action",
  searchDisputeReplyListUri : "searchDisputeReplyList.action",
  deleteDisputeReplyUri : "deleteDisputeReply.action",
  addReplyNoteUri : "addReplyNote.action",
  updateDisputeActionRequestStatusUri : "updateDisputeActionRequestStatus.action",
  downloadTransactionExcelUri : "downloadTransactionExcel.action"
};

doIncreaseOrDecrease = function(v,s){
	$('#increase-decrease-string').text(s);
	$('#increase-decrease-title').text(v + " Reserve");
	$('#increase-decrease-name').text(v + " Reserve Amount");
	$('#updateIncreaseAndDecrease-reserveType').val(v);
    var form = document.getElementById("updateIncreaseAndDecreaseForm");
    $(form["dispute.outstandingReserveAmount"]).val("");
    $(form["dispute.notes"]).val("");
    document.getElementById("_up_load_text_disputeDetail_01").value = "";
    document.getElementById("_up_load_text_disputeDetail_02").value = "";
    document.getElementById("_up_load_text_disputeDetail_03").value = "";
 	clearFormUploadFiles(form);
 	updateIncreaseAndDecreaseScoa.setValue("");
 	$('#updateIncreaseAndDecreaseSCOA-id').val('');
 	var f  = document.getElementById("");
	$("#updateIncreaseAndDecreaseForm input[name='accountCodeId']").first().attr("checked", true);
    YAHOO.ccm.container.updateIncreaseAndDecreaseWindow.show();
}
doIncreaseOrDecrease.call = function(v,amount){
	hideLoadingModalLayer();
	if(v){
		YAHOO.ccm.container.updateIncreaseAndDecreaseWindow.hide();
		refreshOutstandingReserveAmount();
		refreshDisputeAmount();
		DisputeReconciliationListPage.reload();
	}else{
		alert($('#updateIncreaseAndDecrease-reserveType').val() + " Reserve error!");
	}
}

refreshOutstandingReserveAmount = function(){
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
			outstandingReserveAmount = data.amount;
			$("#disputeOutstandingReserveAmount1").html(outstandingReserveAmount ? outstandingReserveAmount.toFixed(5) : "0.00");
			$("#disputeOutstandingReserveAmount2").html(outstandingReserveAmount ? outstandingReserveAmount.toFixed(5) : "0.00");
		},
		failure:showError
	};
	var data = "disputeId="+disputeId;
	YAHOO.util.Connect.asyncRequest("POST","getOutstandingReserveAmount.action", callback, data);
}
viewVendorDetails = function(vendorId,banId){
	
	showLoadingModalLayer();
	var uri = "viewVendorDetails.action?vendorId=" + vendorId+"&banId="+banId;
	windowLocationHref(uri);
			
}

refreshDisputeAmount = function(){
/***
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
			disputeBalance = data.amount;
			$("#theDisputeAmount-td").html(data.amountString ? data.amountString : "0.00");
		},
		failure:showError
	};
	var data = "disputeId="+disputeId;
	YAHOO.util.Connect.asyncRequest("POST","getDisputeAmount.action", callback, data);
	*/
}

updateDisputeBalance = function(disputeId){
//	var callback = {
//		success: function(){},
//		failure: function(){}
//	};
//	YAHOO.util.Connect.asyncRequest("POST","updateDisputeBalance.action",callback, "disputeId="+disputeId);
}
refreshDisputeSCOA = function(){
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
			var s = data.scoa;
			var t = $("#updateIncreaseAndDecrease-scoa-table");
			t.empty();
			for(var i=0;i<s.length;i++){
				var str = "";
				str += "<tr><td>";
				str += "<input type='radio' name='accountCodeId' value='"+s[i].id+"'>";
				str += "</td><td>";
				str += s[i].name;
				str += "</td></tr>";
				t.append(str);
			}
		},
		failure:showError
	};
	var data = "disputeId="+disputeId;
	YAHOO.util.Connect.asyncRequest("POST","getDisputeSCOA.action", callback, data);
}

YAHOO.util.Event.onDOMReady(function () {
	$(window).unload( function () { updateDisputeBalance(disputeId); } );
	var tabView = new YAHOO.widget.TabView("demo");
	$("#demo li a:eq(0)").click();
	tabView.selectTab(0);
	addTransactionHistoryData();
	refreshDisputeSCOA();
	initDisputeNoteWindow();

	// Dispute Details 的 email center.
	showAddressPerson();
	
    YAHOO.ccm.container.updateIncreaseAndDecreaseWindow = new YAHOO.widget.Dialog("updateIncreaseAndDecreaseWindow", {
        width: "570px",
        fixedcenter: true,
        visible: false,
        constraintoviewport: true,
        modal: true,
        buttons: [{
            text: "Submit",
            handler: function(){
                var form = document.getElementById("updateIncreaseAndDecreaseForm");
                if($('#updateIncreaseAndDecreaseSCOA-id').attr('checked') && !updateIncreaseAndDecreaseScoa.requiredValidate()){
                	return false;
                }
                if (form["dispute.notes"].value.length < 10) {
                    $(form["dispute.notes"]).removeClass("validation-passed");
                    $(form["dispute.notes"]).addClass("validation-failed");
                    return false;
                } else {
                    $(form["dispute.notes"]).removeClass("validation-failed");
                    $(form["dispute.notes"]).addClass("validation-passed");
					var ba = form["dispute.outstandingReserveAmount"];
                    if (!FIC_checkForm(ba)) {
                        return false;
                    }
                    var amount = parseFloat(ba.value);
                    var t_p = $('#updateIncreaseAndDecrease-reserveType').val();
                    if(t_p == "Increase"){
						if(0 < amount && amount<=(disputeBalance-outstandingReserveAmount)){
							$(ba).removeClass('validation-failed');
							$(ba).addClass('validation-passed');
							showLoadingModalLayer();
							form.submit();
						}else{
							$(ba).removeClass('validation-passed');
							$(ba).addClass('validation-failed');
							if(0 == amount){
								alert('Increase Reserve Amount can\'t be zero.');
							}else{
								alert('Outstanding reserve amount can\'t be set larger than outstanding dispute amount.');
							}
							
							return false;
						}
                    }else{
						if(0 < amount && amount<=outstandingReserveAmount){
							$(ba).removeClass('validation-failed');
							$(ba).addClass('validation-passed');
							showLoadingModalLayer();
							form.submit();
						}else{
							$(ba).removeClass('validation-passed');
							$(ba).addClass('validation-failed');
							alert('Outstanding reserve amount can\'t be negative.');
							return false;
						}
                    }
                }
            }
        }, {
          text: "Cancel",
          handler: function(){
	          var form = document.getElementById("updateIncreaseAndDecreaseForm");
	          $(form["dispute.outstandingReserveAmount"]).val("");
	          $(form["dispute.notes"]).val("");
	          document.getElementById("_up_load_text_disputeDetail_01").value = "";
	          document.getElementById("_up_load_text_disputeDetail_02").value = "";
	          document.getElementById("_up_load_text_disputeDetail_03").value = "";
	   		  clearFormUploadFiles(form);
	          this.cancel();
          }
        }]
    });
	YAHOO.ccm.container.updateIncreaseAndDecreaseWindow.render();
	
	
	YAHOO.ccm.container.contactVendorWindow = new YAHOO.widget.Dialog("contactVendorWindow", { 
		width: "900px",
        fixedcenter: true,
        visible: false,
        constraintoviewport: true,
        modal: true
	});
	
	YAHOO.ccm.container.contactVendorWindow.render();
});

/**
 * 处理 Dispute Details 页面中的 Email Center 中的信息，
 * 只显示发件人和收件人的姓名，通过字符串截取来实现。
 * 当鼠标悬浮的时候显示 Person 的 Email Address.
 * (Tooltip 提示， 使用插件 hint.min.css).
 */
function showAddressPerson() {
	// Person name 可能是从括号外取出来的。
	// mingyang.li@xinketechnology.com(mingyang.li@xinketechnology.com) 括号外有值的情况。
	// (tems.test@saninco.com) 括号外没有值的情况。

	// 括号内的值的正则表达式匹配。
	var bracketInnerValueRegExp = /\((.+?)\)/;

	// 括号外的值的正则表达式匹配。
	var bracketOuterValueRegExp = /(^.*?)\(/;

	// 正则表达式的test方法来测试是否有@符号。
	var atSymbolRegExp = /@/; 

	// 存储 Address Persons 的数组。
	var addressPersonArray = [];

	// 所需要的 Address Person.
	var neededAddressPerson = '';

	// 括号前面的字符串。
	var bracketOuterValue = '';

	// Address Person Name.
	var personName = ''; 

	// Person 的 Email Address.
	var personEmailAddress = '';

	// 取出所有的 Address Person，之后进行处理。
	$('.email-center .address-container .address-person').each(function() {

		// 查询出来的数据可能有多个 Address Person 的信息，
		// 但是都是使用逗号分隔， 切割字符串并取得
		// 第一个 Address Person.
		addressPersonArray = $(this).text().split('),');

		if( addressPersonArray ) {
			
			// 数组中第一个元素。
			neededAddressPerson = addressPersonArray[0]+")";
			personEmailAddress = neededAddressPerson.match( bracketInnerValueRegExp )!= null ? neededAddressPerson.match( bracketInnerValueRegExp )[1] : "";
			$(this).attr('data-hint', personEmailAddress)

			bracketOuterValue = neededAddressPerson.match( bracketOuterValueRegExp )!=null ? neededAddressPerson.match( bracketOuterValueRegExp )[1] : "";

			// 括号之外是否有值
			if ( bracketOuterValue !== "" ) {
				personName = bracketOuterValue.split('@')[0];
				
			} else {
				personName = personEmailAddress.split('@')[0];
			}

			$(this).text( personName );
		}
	});

}

function getContactVendor () {
	
	var callback = {
			success: function(o){
				if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
					window.location.reload();
					return;
				}

				$(".contactVendorWide").html(o.responseText);
				YAHOO.ccm.container.contactVendorWindow.show();
				$("#DisputeSendtoVendorByDisputeSubject").val($("#_disputeNumber").html());
			},
			failure:showError
		};
		var data = "ids="+disputeId;
		YAHOO.util.Connect.asyncRequest("POST","searchContactVendorByDisputeIds.action", callback, data);
	
}

function sendtoVendor () {
	
	var filesSize = 0;
	var Regex = /^(?:\w+\.?)*\w+@(?:\w+\.)*\w+$/; 
	var toAddress = document.getElementById('_contactVendor_to').value;
	var ccAddress = document.getElementById('_contactVendor_cc').value;

	$(".czgdIcon").each(function(index){   
		filesSize = filesSize + parseInt($(this).attr("value"));
	});

	
    if(filesSize>5242880) {
    	alert("Please send attachments is less than 5M.");
    	return;
    }
    
	if (toAddress == null || toAddress == '') {
		alert("To address cannot be empty.");
		return;
	}
	
	if(toAddress.split(";").length > 1) {
    	var toAddressList = toAddress.split(";");
    }else if(toAddress.split(",").length > 1){
    	var toAddressList = toAddress.split(",");
    }
    
	if (toAddressList != null && toAddressList.length > 1) {
		for(var i=0;i<toAddressList.length;i++){
			if (!Regex.test(toAddressList[i])){                
		　　		alert("Please input the correct email address.");
				return;
		　　}
		}
	}else{
		if (!Regex.test(toAddress)){                
	　　		alert("Please input the correct email address.");
			return;
	　　}
	}
	
	
　　      
	if (ccAddress != null && ccAddress != '') {
		if(ccAddress.split(";").length > 1) {
	    	var ccAddressList = ccAddress.split(";");
	    }else if(ccAddress.split(",").length > 1){
	    	var ccAddressList = ccAddress.split(",");
	    }
		if (ccAddressList != null && ccAddressList.length > 1) {
			for(var i=0;i<ccAddressList.length;i++){
				if (!Regex.test(ccAddressList[i])){                
			　　		alert("Please input the correct email address.");
					return;
			　　}
			}
		}else{
			if (!Regex.test(ccAddress)){                
		　　		alert("Please input the correct email address.");
				return;
		　　}
		}
	}
	
	$("#DisputeSendtoVendorByDisputeIdId").val(disputeId);
	showLoadingModalLayer();
	document.getElementById('sendContactVendorEmailId').submit();
	
}
function hideContactVendorWindow(result){
	hideLoadingModalLayer();
	if(result == "success"){
		YAHOO.ccm.container.contactVendorWindow.hide();
	}
}
function filesProcess(files){
	 
	for( var i = 0; i < files.length; i++) {
		var file = files[i];
			var d ='<div class="bannw23" class="clearfix"><div>'+
						'<div class="left"><span class="bzhx"></span>'+
						'<span class="ataneFileName">'+file.name+'</span></div>'+
						'<span class="czgdIcon" onclick="removeItemFile(this)" value ="'+file.size+'"></span>'+
						'<input name="effectiveFile" type="hidden" value="'+file.name+':'+file.size+'"/>'+
					'<div style="clear: both;"></div></div></div>';
			$("#addDisputeEmailFile").append(d); 
			
	}
	var newAttach = '<input style="text-align:center;width:70px;height: 19px;position: absolute;top: 0;left: 0;z-index: 100;opacity: 0;cursor: pointer;" ' +
	'type="file" name="uploads" multiple="multiple" onchange="filesProcess(this.files)" />';
	
	$("#attachDiv :input").each(function () {
		var this_name = $(this).attr("name");
		$(this).hide();
	});
	$('#attachmentTitleId').show();
	$("#attachDiv").append(newAttach);
}
function removeItemFile(item){
	    $(item).parent().parent().remove();
		if($("#addDisputeEmailFile").children().length == 0 ){
			$('#attachmentTitleId').hide();
			cleanHiddenAtach();
		}	
}

function cleanHiddenAtach(){

		var newAttach = '<input style="text-align:center;width:70px;height: 19px;position: absolute;top: 0;left: 0;z-index: 100;opacity: 0;cursor: pointer;" ' +
		'type="file" name="uploads" multiple="multiple" onchange="filesProcess(this.files)" />';
		newAttach += '<div class="attachBorder butata">Attach</div>';
		
		$("#attachDiv").empty();
		$("#attachDiv").append(newAttach);

}

//download Transaction Excel
function saveExcelByTransaction(){
	showLoadingModalLayer();
	titles = TransactionHistoryListPage.getTitlesParam("titles");
	paramStr = TransactionHistoryListPage.paramStr;
	var uri= detailActionUri.downloadTransactionExcelUri + "?"+ titles +"&"+ paramStr;
	windowOpen(uri);
	hideLoadingModalLayer();
}

//search Transaction History List
function getTransactionHistoryList(){
	if(!window.TransactionHistoryListPage){
		TransactionHistoryListPage = new SANINCO.Page("_transaction_history_list","TransactionHistoryListPage",{
		    vo : "searchDisputeVO",
			recordText : '',
		    dataUri :detailActionUri.searchTransactionHistoryListUri ,
			recPerArray : [10,20,30,50,80,100,200],
		    cols : [{ title : "Workflow Action",dataField: "workflowAction"
			    },{ title : "Workflow User",dataField:"workflowUser"
				},{ title : "Dispute Status",dataField:"disputeStatus"
				},{ title : "Invoice Status",dataField:"invoiceStatus"
				},{ title : "Refile Dispute Amount",dataField:"disputeAmount"
				},{ title : "Notes",dataField:"notes",className : "table_td_br",width:"260px"
				},{ title : "Created Timestamp",dataField:"createdTimestamp"
				},{ title : "Download",dataField:function(o){
						if (o.attachmentPoint != "") {return "<img src=\"include/images/download1.gif\" style=\"cursor: pointer;\" onclick=\"SANINCO.Download(" + o.attachmentPoint + ");\"/>";}
						else{return "";}
					}
				}]
		});
	}
	
	TransactionHistoryListPage.voParam = {
		disputeId : disputeId,
		invoiceId : invoiceId
	};
	
	TransactionHistoryListPage.start();
	
}
//search Dispute Notes List
function getDisputeNotesList(){
	if(!window.DisputeNotesListPage){
		DisputeNotesListPage = new SANINCO.Page("_dispute_notes","DisputeNotesListPage",{
		    vo : "searchDisputeVO",
			recordText : '',
			totalPageUri : detailActionUri.getDisputeNotesListPageNoUri,
		    dataUri :detailActionUri.searchDisputeNotesListUri ,
		    paginationDiv : "__dispute_notes_page_list",
			recPerArray : [10,20,30,50,80,100,200],
		    cols : [
				    { title : "Notes",dataField:function(o){
						return {less_sign:"<div style=\"white-space: pre-wrap;\">"+(o.note).replace(/\</g,"&lt;")+"</div>"}
						},className : "table_td_dispute",filtration : {name:"aa.note",alias:"Notes"},sort : "aa.note"
					}
				    ,{ title : "Analyst Name",dataField:"name", filtration : {name:"aa.name",alias:"Analyst Name"},sort : "aa.name"
					},{ title : "Created Timestamp",dataField:"date", filtration : {name:"aa.date",alias:"Created Date"},sort : "aa.date"
					}]
		});
	}
	
	DisputeNotesListPage.addBeforeSearch(function(){
		$("#_dispute_add_notes_btn").hide();
    });
	
	
	DisputeNotesListPage.addSuccessEvent(function(data){
        if (data.totalResultNo != 0 && !data.error) {
        	$("#_dispute_add_notes_btn").show();
        }
    });
	
	DisputeNotesListPage.voParam = {
			disputeId : disputeId,
			invoiceId : invoiceId
		};
	filter1 = new SANINCO.Filter();
	filter1.addEditeEvent(function(){DisputeNotesListPage.start();});
	filter1.add('aa.date', 'String');
	filter1.add('aa.name', "String");
	filter1.add('aa.note', 'String');
	DisputeNotesListPage.setFilter(filter1);

	DisputeNotesListPage.start();
	
	$("#DisputeNoteByInvoiceId").val(invoiceId);
	$("#DisputeNoteByDisputeIdId").val(disputeId);
	
}

function initDisputeNoteWindow(){
	YAHOO.ccm.container.DisputeNoteWindow = new YAHOO.widget.Dialog("DisputeNoteWindow", {
        width: "37em",
        fixedcenter: true,
        visible: false,
    	modal : true,
        constraintoviewport: true
    });
	
    YAHOO.ccm.container.DisputeNoteWindow.render();
}
function addDisputeNote(addBanNoteFlag){
	YAHOO.ccm.container.DisputeNoteWindow.show();
	clearNotes();
	var ps = $("#ps");
	var num = $("#num");

	calculateWordLimit(ps, num);
}

/**
 * 相对于 textarea 的一个小功能，当 textarea 的内容改变的时候，
 * 在这个文本框的下面会动态的显示，剩余多少文字可输入。
 */
function calculateWordLimit( textElement, counterElement ) {

	textElement.keyup(check_keyup).bind('paste', function () {
		setTimeout(check_keyup, 50);
	});
	
	function check_keyup() {
		var num = 500 - textElement.val().length;
		counterElement.html(num);
	}
}

function clearNotes(){
	$('#ps').val('');
	$('#num').html(500);
}
function saveNotes(){
	
	var flag=true;
	if($.trim($("#ps").val()) == ""){
		alert('The notes is required!');
		return;
	}
	if($.trim($("#ps").val()).length>500){
		alert('Cannot be more than 500 characters!');
		return;
	}
	
	showLoadingModalLayer();
	document.getElementById("handleDisputeNoteActionForm").submit();
	hideLoadingModalLayer();
	YAHOO.ccm.container.DisputeNoteWindow.hide();
}
// Dispute Action Request
function getDisputeActionRequest() {
	if(!window.DisputeActionRequestListPage){
		DisputeActionRequestListPage = new SANINCO.Page("_dispute_action_request","DisputeActionRequestListPage",{
			sortingField : "dar.created_timestamp",
		    sortingDirection : "desc",
		    vo : "searchDisputeVO",
			recordText : '',
			totalPageUri : detailActionUri.getDisputeActionRequestListPageNoUri,
		    dataUri :detailActionUri.searchDisputeActionRequestListUri ,
		    paginationDiv : "__dispute_action_request_page_list",
			recPerArray : [10,20,30,50,80,100,200],
		    cols : [
                    { title : "Created By",dataField:"name", filtration : {name:"concat(u.first_name,u.last_name)",alias:"Created By"},sort : "concat(u.first_name,u.last_name)"
                    },{ title : "Created Date",dataField:"createdTimestamp", filtration : {name:"dar.created_timestamp",alias:"Created Date"},sort : "dar.created_timestamp"
                    },{ title : "Status",dataField:"status", filtration : {name:"dars.dispute_action_request_status",alias:"Status"},sort : "dars.dispute_action_request_status"
					},
                    { title : "Note",dataField:function(o){
						return {less_sign:"<div style=\"white-space: pre-wrap;\">"+(o.notes).replace(/\</g,"&lt;")+"</div>"}
						},className : "table_td_dispute",filtration : {name:"dar.notes",alias:"Notes"},sort : "dar.notes"
					}
					,{ title : "Reply",dataField:function(o){
						return "<img  src=\"include/images/message-icon_26.png\"  style=\"cursor: pointer;\"  onClick=\"disputeReplyList(" + o.id  +", this);\"> <div style=\"float:right;padding-top:4px;padding-left:1px;\" id = \"replyCountId"+o.id+"\">"+o.replyCount+"</div>";
					    }		
					}
					,{ title : "Action",dataField:function(o){
						return "<input class='action-button' type=\"button\" value=\"Pending\" onClick=\"disputeActionRequestStatus(" + o.id + ", 2);\"/><input class='action-button' type=\"button\" value=\"Complete\" onClick=\"disputeActionRequestStatus(" + o.id + ", 3);\"/><input class='action-button' type=\"button\" value=\"Reply\" onClick=\"addReply(" + o.id + ", this);\"/>";
//						return "<input class='action-button' type=\"button\" value=\"Temporary Done\" onClick=\"disputeActionRequestStatus(" + o.id + ", 2);\"/><input class='action-button' type=\"button\" value=\"Done\" onClick=\"disputeActionRequestStatus(" + o.id + ", 3);\"/><input class='action-button' type=\"button\" value=\"Reply\" onClick=\"addReply(" + o.id + ", this);\"/>";
					    }
					}
					]
		});
	}
	
	DisputeActionRequestListPage.voParam = {
			disputeId : disputeId
		};

	filter1 = new SANINCO.Filter();
	filter1.addEditeEvent(function(){DisputeActionRequestListPage.start();});
	filter1.add("concat(u.first_name,u.last_name)", 'String');
	filter1.add('dar.created_timestamp', "String");
	filter1.add('dars.dispute_action_request_status', 'String');
	filter1.add('dar.notes', 'String');
	DisputeActionRequestListPage.setFilter(filter1);

	DisputeActionRequestListPage.start();
}

/**
 * 添加 Dispute Action Request.
 * 这个方法就是弹出modal和做的一些前台处理。
 */
function addDisputeActionRequest() {
	var textarea = $('.action-request-notes-textarea');
	var numberElement = $('#dispute-action-request-modal-content .word-number');

	$('#dispute-action-request').show();

	$('.action-request-notes-textarea').val('');
	$('.action-request-notes-textarea').focus();

	calculateWordLimit(textarea, numberElement);
	closeDisputeActionRequestModal();
}

function disputeActionRequestStatus(id,status) {
	$.ajax({
		url: detailActionUri.updateDisputeActionRequestStatusUri,
        type: "POST",
        dataType: "text",
        async: false,
        cache: false,
        data: ("searchDisputeVO.disputeActionRequestId="+id+"&disputeActionRequestStatus="+status),
        success: function(o){
		    hideLoadingModalLayer();
            if (o.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                window.location.reload();
                return;
            }

			var value = eval("(" + o + ")");
			
			if(value.error){
				alert(value.error);
				return;
			}	
			if(value.success) {
				getDisputeActionRequest();
			}
			

        },
		error : showError
     });
}

/**
 * 将 Dispute Action Request 中的Notes弹出框中的，文本域中的内容清除，
 * 并将剩余文字数的大小重置。
 * @return {[type]} [description]
 */
function clearDisputeActionRequestNotes() {
	$('.action-request-notes-textarea').val('');
	$('#dispute-action-request-modal-content .word-number').html(500);
}

/**
 * 保存 Dispute Action Request, 并且刷新该列表。
 */
function saveDisputeActionRequest(disputeActionRequestStatus) {


	if($.trim($(".action-request-notes-textarea").val()) == ""){
		alert('The notes is required!');
		return;
	}

	$.ajax({
		url: 'saveDisputeActionRequest.action',
		type: 'POST',
		dataType: 'text',
		data: {actionRequestNotes: $('.action-request-notes-textarea').val().trim(), disputeId: disputeId ,disputeActionRequestStatus: disputeActionRequestStatus},
		success: function(o) {
			var obj = eval("(" + o + ")");

			if(obj.success === 'success') {
				$('#dispute-action-request').hide();
				getDisputeActionRequest();
			}
		},
		error: function() {
			alert('Save the dispute action request failed!');
		}
	});
}

/**
 * 关闭 Dispute Action Request Modal.
 */
function closeDisputeActionRequestModal() {

	$('.dispute-action-request-modal-close').click(function() {

		$(this).parents('#dispute-action-request').hide();
	});
}

/**
 * 添加reply动作。（ add reply 弹出窗口。 ）
 * 在列表中的 Action列中，点击reply按钮即可执行此动作。
 * @param {[type]} id         [description]
 * @param {[type]} DOMElement [description]
 */
function addReply(id, DOMElement) {

	// 此DOM元素的offset top值 和 left 值。
	var offsetTop = $(DOMElement).offset().top;
	var offsetLeft = $(DOMElement).offset().left;

	 // 按钮的宽度和高度。
    var buttonHeight = $(DOMElement).height();
    var buttonWidth = $(DOMElement).width();
	
	$("#__dispute_Action_Request_Id").val(id);

	// 显示弹窗容器， 之后计算高度值和宽度值。
	$("#disputeAddReply").css("display","block");

	// 弹出框的宽度和高度
	var replyHeight = $('.reply-container').height();
	var replyWidth = $('.reply-container').width();

	$("#disputeAddReply").css({ // 计算添加reply弹出窗口的位置。   
		"position": "absolute",   
		"top": (offsetTop - replyHeight - buttonHeight - 16)+'px',   
		"left": (offsetLeft - replyWidth + buttonWidth) +'px'
	});

	// 隐藏点击列表中 reply 字段中按钮产生的弹出框。
	$("#disputeAction").css("display","none");
}

/**
 * 隐藏 add reply 弹出窗口。
 * @return {[type]} [description]
 */
function cancelAddReply () {
	$("#disputeAddReply").css("display","none");
}

/**
 * 点击 add reply 弹出窗口中的reply 按钮所触发的动作，
 * 此动作旨在将用户输入的reply内容存储到后台中。 
 */
function addReplyNote() {
	if ($.trim($("#__addReply_notes").val()) != "") {
		$.ajax({
			url: detailActionUri.addReplyNoteUri,
	        type: "POST",
	        dataType: "text",
	        async: false,
	        cache: false,
	        data: ("searchDisputeVO.disputeActionRequestId=" + $("#__dispute_Action_Request_Id").val()+"&noteNotes="+$("#__addReply_notes").val().trim() ),
	        success: function(o){
			    hideLoadingModalLayer();
	            if (o.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
	                window.location.reload();
	                return;
	            }

				var value = eval("(" + o + ")");
				
				if(value.error){
					alert("Error: " + value.error);
					return;
				}	
				if(value.success) {
					var replyCountId =  "#replyCountId"+$("#__dispute_Action_Request_Id").val();
					$(replyCountId).html(parseInt($(replyCountId).html())+1);
					$("#disputeAddReply").css("display","none");
					//刷新reply
					getDisputeReplyList($("#__dispute_Action_Request_Id").val());
					$("#__addReply_notes").val("");
				}
				

	        },
			error : showError
	     });
	}else {
		alert("No input the content.");
	}
		
	
}

/**
 * 显示  reply list 列表。
 * 点击列表中 reply 字段中按钮产生的弹出框。
 * @param  {[type]} e                      [description]
 * @param  {[type]} disputeActionRequestId [description]
 * @param  {[type]} name                   [description]
 * @return {[type]}                        [description]
 */
function disputeReplyList(disputeActionRequestId, DOMElement) {

	// Reply List icon 的宽度和高度。
	var iconnHeight = $(DOMElement).height();
	var iconWidth = $(DOMElement).width();

	var offsetLeft = $(DOMElement).offset().left;
	var offsetTop = $(DOMElement).offset().top;

	if ($("#replyCountId"+disputeActionRequestId).html() != 0) {
		getDisputeReplyList(disputeActionRequestId);
	}else {

		// 没有 reply list 记录。
		noReplyListRecord();
	}
	
	$("#disputeAction").css("display","block");
	// 显示回复信息列表的容器。
	var replyHeight = $('.reply-list-container').height();
	var replyWidth = $('.reply-list-container').width();

	$("#disputeAction").css({   
		"position": "absolute",   
		"top": (offsetTop - replyHeight - iconnHeight - 18) +'px',   
		"left":(offsetLeft - (replyWidth/2) + iconWidth/2)+'px'
	});

	$("#disputeAddReply").css("display","none");
}

/**
 * 从后台中获取reply list 中主要内容的方法。
 * @param  {[type]} disputeActionRequestId [description]
 * @return {[type]}                        [description]
 */
function getDisputeReplyList(disputeActionRequestId) {
	$.ajax({
		//url: actionUri.auditInvoiceUri,
		url: detailActionUri.searchDisputeReplyListUri,
        type: "POST",
        dataType: "text",
        async: false,
        cache: false,
        data: ("searchDisputeVO.disputeActionRequestId=" + disputeActionRequestId),
        success: function(o){
		    hideLoadingModalLayer();
            if (o.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                window.location.reload();
                return;
            }

			var value = eval("(" + o + ")");
			
			if(value.error){
				alert("Error: " + value.error);
				return;
			}	

			if(value.data && value.data.length > 0) { // 如果后台中返回至少一条数据。
				var str = "";
				var data = value.data;
				for(var i=0; i<data.length; i++){

					str += '<div class="reply-list-item">';
					str += '<a class="delete-item button-finger" onclick="deleteDisputeReply(' + data[i].id + ',' + disputeActionRequestId + ', this)"></a>';
					str += '<div class="top-view clearfix">';
					str += '<h4 class="reply-user">'+ data[i].name +'</h4>';
					str += '<div class="reply-date">'+ data[i].date +'</div>';
					str += '</div>';
					str += '<p class="reply-content">'+ data[i].notes +'</p>';
					str += '</div>';
				}

				$(".reply-list").html(str);
			} else { // 如果后台中返回的数据的条数为0.

				// 没有 reply list 记录。
				noReplyListRecord();
			}

        },
		error : showError
     });	
}

/**
 * 删除Reply
 * @param  {[type]} disputeActionRequestReplyId [description]
 * @param  {[type]} disputeActionRequestId      [description]
 * @return {[type]}                             [description]
 */
function deleteDisputeReply (disputeActionRequestReplyId, disputeActionRequestId, DOMElement) {

	$.ajax({
		//url: actionUri.auditInvoiceUri,
		url: detailActionUri.deleteDisputeReplyUri,
        type: "POST",
        dataType: "text",
        async: false,
        cache: false,
        data: ("searchDisputeVO.disputeActionRequestReplyId=" + disputeActionRequestReplyId),
        success: function(o){
		    hideLoadingModalLayer();
            if (o.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                window.location.reload();
                return;
            }

			var value = eval("(" + o + ")");

			// 获得当前的 reply item 的个数。
			var replyNumber = $("#replyCountId"+disputeActionRequestId).html();


			// 当列表中没有元素的时候， dispute action的位置将不会重新计算。
			if ( replyNumber > 1 ) {

				/*从新计算dispute action 的位置。*/
				calcNewPositionOfAction(DOMElement);
			}
			
			
			// 重新渲染reply list.
			getDisputeReplyList(disputeActionRequestId);

			// 限制数值 （当item的个数为零的时候就不能往下减少了）。
			if ( value.success === "success" && replyNumber !== 0 ) {

				$("#replyCountId"+disputeActionRequestId).html(replyNumber-1);
			}
			
			if(value.error){
				alert("Error: " + value.error);
				return;
			}		
        },
		error : showError
     });
	
}

/**
 * 但删除了一个reply item 之后，重新计算dispute action 的位置.
 * @param  {[type]} DOMElement [description]
 * @return {[type]}            [description]
 */
function calcNewPositionOfAction(DOMElement) {

	// Dispute action 删除了一个元素之后的位置。
	var actionPosition = $('#disputeAction').css('top').replace("px", "");

	// 所删除元素的高度。
	var deletedItemHeight = $(DOMElement).parent().outerHeight();

	// Dispute action 元素的最新位置。
	var newPosition = actionPosition*1 + deletedItemHeight + 10; // 10px 是每一个 reply list item 的margin值。

	$("#disputeAction").css({      
		"top": newPosition +'px'  
	});
}

/**
 * 当没有reply 记录的时候，应该显示的页面结构。
 * @return {[type]} [description]
 */
function noReplyListRecord() {

	var noRecordsWrapper = "<div class='no-records'>... No Reply Records ...</div>";
	$(".reply-list").html(noRecordsWrapper);
}

/**
 * 销毁 reply list 弹出框。
 * @return {[type]} [description]
 */
function dismissDisputeReplyList() {

	$('.delete-list-container').click(function() {
		$(this).parents('#disputeAction').hide();
	});

}




//verify Number
function verifyNumber(){
	var ticketNumbert = document.getElementById('__ticket_number').value;
	if(ticketNumbert.length > 64){
		removeClassName('__ticket_number','validation-passed');
		addClassName('__ticket_number','validation-failed');
		return false;
	}else{
		removeClassName('__ticket_number','validation-failed');
		addClassName('__ticket_number','validation-passed');
		return true;
	}
}

//Clear pop-up window
function emptyInput(){
	if(!judgementUserRights('__user_access_control')) return;
	document.getElementById("__ticket_number").value = "";
	document.getElementById("__notes_three").value = "";
	//document.getElementById('__load_up_one1').outerHTML += "";
	$("#addTransactionHistory_AddFile").html("");
	var d = "<div id =\"load_up_ath1\" style=\"padding:3px 0 0 0;height: 19px;\" ><div style=\"float:left;\">"		
	+"<input style=\"height:19px;width:150px;\" type='text' id=\"__up_load_text_dispute_ath1\" disabled=\"disabled\"></div>"	
    +"<div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"
	+"<input id=\"__load_up_one1\" onchange=\"document.getElementById('__up_load_text_dispute_ath1').value=this.value;\" class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
	+"<input style=\"height:19px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/></span></div>"
    +"<div class=\"approveicon\" onclick=\"deletefile('load_up_ath1');\"></div><div class=\"clear\" ></div></div>"
    
    +"<div id =\"load_up_ath2\" style=\"padding:3px 0 0 0;height: 19px;\" ><div style=\"float:left;\">"		
	+"<input style=\"height:19px;width:150px;\" type='text' id=\"__up_load_text_dispute_ath2\" disabled=\"disabled\"></div>"	
    +"<div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"
	+"<input id=\"__load_up_one2\" onchange=\"document.getElementById('__up_load_text_dispute_ath2').value=this.value;\" class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
	+"<input style=\"height:19px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/></span></div>"
    +"<div class=\"approveicon\" onclick=\"deletefile('load_up_ath2');\"></div><div class=\"clear\" ></div></div>"
	
	+"<div id =\"load_up_ath3\" style=\"padding:3px 0 0 0;height: 19px;\" ><div style=\"float:left;\">"		
	+"<input style=\"height:19px;width:150px;\" type='text' id=\"__up_load_text_dispute_ath3\" disabled=\"disabled\"></div>"	
    +"<div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"
	+"<input id=\"__load_up_one3\" onchange=\"document.getElementById('__up_load_text_dispute_ath3').value=this.value;\" class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
	+"<input style=\"height:19px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/></span></div>"
    +"<div class=\"approveicon\" onclick=\"deletefile('load_up_ath3');\"></div><div class=\"clear\" ></div></div>";

	$("#addTransactionHistory_AddFile").append(d); 
	YAHOO.ccm.container.addTransactionHistory.show();
}

//add Transaction History Data
function addTransactionHistoryData() {
	var handleSubmit = function() {
		var form = document.getElementById("doAddTransactionHistoryForm");
//		if (form["__notes_three"].value.length < 10) {
//			verifyNumber();
//			$(form["__notes_three"]).removeClass('validation-passed');
//			$(form["__notes_three"]).addClass('validation-failed');
//			return false;
//		}
//		else {
			if(!verifyNumber()) return false;
			if(!uploadFileValidate("addTransactionHistory_AddFile")) return false;
			$(form["__notes_three"]).removeClass('validation-failed');
			$(form["__notes_three"]).addClass('validation-passed');
			form.submit();
//		}
	};
	var handleCancel = function() {	
		this.cancel();
	};
	YAHOO.util.Dom.removeClass("addTransactionHistory", "yui-pe-content");
	YAHOO.ccm.container.addTransactionHistory = new YAHOO.widget.Dialog("addTransactionHistory", 
							{ width : "38em",
							  fixedcenter : true,
							  visible : false, 
							  modal : true,
							  constraintoviewport : true,
							  buttons : [/*{ text:"Add", handler:TransactionHistoryAddFile },*/
							             { text:"Confirm", handler:handleSubmit },
								         { text:"Cancel", handler:handleCancel }
								      ]
							});
	YAHOO.ccm.container.addTransactionHistory.render();
};
var i = 10;
function transactionHistoryAddFile(){
	i++;
	var d = "<div id =\"load_up_ath"+i+"\" style=\"padding:3px 0 0 0;height: 19px;\" ><div style=\"float:left;\">"		
	+"<input style=\"height:19px;width:150px;\" type='text' id=\"__up_load_text_dispute_ath"+i+"\" disabled=\"disabled\"></div>"	
    +"<div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"
	+"<input id=\"__load_up_one"+i+"\" onchange=\"document.getElementById('__up_load_text_dispute_ath"+i+"').value=this.value;\" class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
	+"<input style=\"height:19px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/></span></div>"
    +"<div class=\"approveicon\" onclick=\"deletefile('load_up_ath"+i+"');\"></div><div class=\"clear\" ></div></div>"

		$("#addTransactionHistory_AddFile").append(d); 	
}

function addTransactionHistoryDataCall(){
	YAHOO.ccm.container.addTransactionHistory.hide();
	hideLoadingModalLayer();
	window.location.reload();
};
