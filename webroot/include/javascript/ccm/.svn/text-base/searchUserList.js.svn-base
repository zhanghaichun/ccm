// @author Chao.Liu (Optimization of complete by xinyu.Liu)
var replaceSupervisorPanel;

// Action Url
userActionUri = {
	doStarSearchUserListTotalNumberUri : "doStarSearchUserListTotalNumber.action",
	doSearchUserListUri : "doSearchUserList.action",
	doDeleteUserUri : "doDeleteUser.action",
	doIsShowOrDelUri : "doIsShowOrDel.action"
};

YAHOO.util.Event.onDOMReady(function(){
    replaceSupervisorPanel = new YAHOO.widget.Panel("replaceSupervisorDiv", {
        width: "320px",
        fixedcenter: true,
        visible: false,
        constraintoviewport: true,
        close: true,
        modal: true
    });
    replaceSupervisorPanel.render();
    
    pageAdminUserList1 = new SANINCO.Page("_userListDataTable", "pageAdminUserList1", {
        sortingField: "u.first_name",
        sortingDirection: "asc",
        vo: "uvo",
        autoToTop : true,
        recPerArray: [10, 15, 20, 30, 50, 80, 100, 200],
        totalPageUri: userActionUri.doStarSearchUserListTotalNumberUri,
        dataUri: userActionUri.doSearchUserListUri,
        cols: [
			{title : "First Name", dataField : "fname", sort : "u.first_name", filtration : {name : "u.first_name", alias : "First Name"}	    
		 }, {title : "Last Name", dataField : "lname", sort : "u.last_name", filtration : {name : "u.last_name", alias : "Last Name"}	    
		 }, {title : "Initials", dataField : "initials", sort : "u.initials", filtration : {name : "u.initials", alias : "Initials"}	    
		 }, {title : "User Name", dataField : "uname", sort : "u.user_name", filtration : {name : "u.user_name", alias : "User Name"}	    
		 }, {title : "Backup User", dataField : "backup", sort : "concat(bu.first_name,blank_space(),bu.last_name)", filtration : {name : "concat(bu.first_name,blank_space(),bu.last_name)", alias : "Backup User"}	    
		 }, {title : "Supervisor", dataField : "supervisor", sort : "concat(su.first_name,blank_space(),su.last_name)", filtration : {name : "concat(su.first_name,blank_space(),su.last_name)", alias : "Supervisor"}	    
		 }, {title : "Edit &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Delete",
        			dataField: function(row){
            			return '<img src="include/images/Edit-icon-s.png" onclick="eidtUser(' + row.id + ')"; />  '
							 + '<span style="padding-left:30px;"></span><img src="include/images/reject16.png"  onclick="isShowOrDel(' + row.id + ')"/>';
			        }
    	 }]
	});
	
	filterUL = new SANINCO.Filter();
    filterUL.addEditeEvent(function(){pageAdminUserList1.start();});
    filterUL.add('u.first_name', 'String');
    filterUL.add('u.last_name', 'String');
	filterUL.add('u.initials', 'String');
	filterUL.add('u.user_name', 'String');
	filterUL.add('concat(bu.first_name,blank_space(),bu.last_name)', 'String');
	filterUL.add('concat(su.first_name,blank_space(),su.last_name)', 'String');
	
    pageAdminUserList1.setFilter(filterUL);
    
});

// Get document.getElementById
function get(id){
    return (typeof(id) == "string" ? document.getElementById(id) : id);
}

/// Search Url Paramer
function composePostDataFromUiUL(){
    var postData = "";
    if ($('#form123_uvo_name').val() != "") postData += ("uvo.name=" + ccmEscape($('#form123_uvo_name').val()));
    if ($('#form123_uvo_userName').val() != "") postData += ("&uvo.userName=" + ccmEscape($('#form123_uvo_userName').val()));
    if ($('#form123_uvo_backupUserId').val() != "all") postData += ("&uvo.backupUserId=" + ccmEscape($('#form123_uvo_backupUserId').val()));
    if ($('#form123_uvo_supervisorId').val() != "all") postData += ("&uvo.supervisorId=" + ccmEscape($('#form123_uvo_supervisorId').val()));
    if ($('#form123_uvo_initials').val() != "") postData += ("&uvo.initials=" + ccmEscape($('#form123_uvo_initials').val()));
    if ($('#form123_uvo_email').val() != "") postData += ("&uvo.email=" + ccmEscape($('#form123_uvo_email').val()));
    if ($('#form123_uvo_phone').val() != "") postData += ("&uvo.phone=" + ccmEscape($('#form123_uvo_phone').val()));
    if ($('#form123_uvo_createBeginDate').val() != "") postData += ("&uvo.createBeginDate=" + ccmEscape($('#form123_uvo_createBeginDate').val()));
    if ($('#form123_uvo_createdEndDate').val() != "") postData += ("&uvo.createdEndDate=" + ccmEscape($('#form123_uvo_createdEndDate').val()));
    if ($('#form123_uvo_userCreateWiPastNumOfDays').val() != "") postData += ("&uvo.userCreateWiPastNumOfDays=" + ccmEscape($('#form123_uvo_userCreateWiPastNumOfDays').val()));
    if ($('#form123_uvo_modifiedBeginDate').val() != "") postData += ("&uvo.modifiedBeginDate=" + ccmEscape($('#form123_uvo_modifiedBeginDate').val()));
    if ($('#form123_uvo_modifiedEndDate').val() != "") postData += ("&uvo.modifiedEndDate=" + ccmEscape($('#form123_uvo_modifiedEndDate').val()));
    if ($('#form123_uvo_userModifiedWiPastNumOfDays').val() != "") postData += ("&uvo.userModifiedWiPastNumOfDays=" + ccmEscape($('#form123_uvo_userModifiedWiPastNumOfDays').val()));
    return postData;
}

/// Star Search User List TotalNumber
function startSearch(){
    if((!validateFields()) | (!judgDate())) return;
    filterUL.clearAll();
    pageAdminUserList1.myParam = composePostDataFromUiUL();
    pageAdminUserList1.start();
}

/// Delete One User Star
function deleteUser(uid){
    var callback = {
        success: function(o){
			hideLoadingModalLayer();
			if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
				window.location.reload();
				return;
			}
		    var data = eval("(" + o.responseText + ")");
		    if (data.error) {
		        alert(data.error);
		        return;
		    }
		    startSearch(); // Search User List Star
		    searchLeftPageOnLoad(); // Left Page New View
		    replaceSupervisorPanel.hide();
		},
        failure: showError
    };
    var suid = get("sRepeatUser").value;
    var deleteUserUrlParamer = "uvo.uid=" + uid + "&uvo.suId=" + suid;
    YAHOO.util.Connect.asyncRequest('POST', userActionUri.doDeleteUserUri, callback, deleteUserUrlParamer)
}

/// Eidt One User Star
function eidtUser(uid){
    var uri = "securityManagement.action?userId=" + uid;
    windowLocationHref(uri);
}

function validateFields(){
    var formatValid = FIC_checkForm('form123');
    // Date Radio is clear
    if (formatValid) {
        if (getCheckedValue(document.getElementsByName('selectCreatedUserListRadio')) == "CREATEDDATEFRAME") {
            $('#form123_uvo_userCreateWiPastNumOfDays').val('');
        }else {
            $('#form123_uvo_createBeginDate').val('');
            $('#form123_uvo_createdEndDate').val('');
        }
        
        if (getCheckedValue(document.getElementsByName('selectModifiedUserListRadio')) == "MODIFIEDDATEFRAME") {
            $('#form123_uvo_userModifiedWiPastNumOfDays').val('');
        }else {
            $('#form123_uvo_modifiedBeginDate').val('');
            $('#form123_uvo_modifiedEndDate').val('');
        }
    }
    
    return formatValid;
}

// Select Radio
function changRadio(type){
    if (type == 0) document.getElementById('selectCreatedUserListRadio1').checked = true;
    if (type == 1) document.getElementById('selectCreatedUserListRadio2').checked = true;
    if (type == 2) document.getElementById('selectModifiedUserListRadio1').checked = true;
    if (type == 3) document.getElementById('selectModifiedUserListRadio2').checked = true;
}

function getCheckedValue(radioObj){
    if (!radioObj) return "";
    var radioLength = radioObj.length;
    if (radioLength == undefined) 
        if (radioObj.checked) 
            return radioObj.value;
        else 
            return "";
    for (var i = 0; i < radioLength; i++) {
        if (radioObj[i].checked) {
            return radioObj[i].value;
        }
    }
    return "";
}

// Paging image and URL
function getSortingDirectionString(fieldName){
    if (currentSortingFieldName == fieldName) {
        if (currentSortingDirection == "asc"){
			return '<img src= ' + 'include/images/upsort.gif>';
		}else {
			return '<img src= "' + 'include/images/downsort.gif">';
		}
    }else {
		return '';
	}
}

// Error Info
function showError(o){
    var msg = "Error transaction Id: " + o.tId + ", status: " + o.statusText;
    alert(msg); // Show Error message Info 
}

function resetFormElementValue(){
	emptyValue([
		'form123_uvo_name','form123_uvo_userName','form123_uvo_initials',
		'form123_uvo_email','form123_uvo_phone','form123_uvo_createBeginDate',
		'form123_uvo_createdEndDate','form123_uvo_userCreateWiPastNumOfDays',
		'form123_uvo_modifiedBeginDate','form123_uvo_modifiedEndDate','form123_uvo_userModifiedWiPastNumOfDays'
	]);
	$('#form123_uvo_backupUserId').val('all');
    $('#form123_uvo_supervisorId').val('all');
}

function resetRadioButton(){
    document.getElementById('selectCreatedUserListRadio1').checked = true;
    document.getElementById('selectModifiedUserListRadio1').checked = true;
}

//update radio button after get saved quicklink.
function updateRadioButton(){
    if ($('#form123_uvo_userCreateWiPastNumOfDays').val() != "") 
        document.getElementById('selectCreatedUserListRadio2').checked = true;
    if ($('#form123_uvo_createBeginDate').val() != "" || $('#form123_uvo_createdEndDate').val() != "") 
        document.getElementById('selectCreatedUserListRadio1').checked = true;
    if ($('#form123_uvo_userModifiedWiPastNumOfDays').val() != "") 
        document.getElementById('selectModifiedUserListRadio2').checked = true;
    if ($('#form123_uvo_modifiedBeginDate').val() != "" || $('#form123_uvo_modifiedEndDate').val() != "") 
        document.getElementById('selectModifiedUserListRadio1').checked = true;
}

function isShowOrDel(uid){
    // Add By Chao.Liu to 10/06/25 PM
    var isCurrentUID = $('#uvo_currentUserId').val();
    if (isCurrentUID == uid) {
        alert("You can not delete yourself ! ");
        return;
    }
	
    var callback = {
        success: isShowOrDelIsSuccess,
        failure: showError
    };
    var isShowOrDelUrlParamer = "uvo.uid=" + uid;
    YAHOO.util.Connect.asyncRequest('POST', userActionUri.doIsShowOrDelUri, callback, isShowOrDelUrlParamer)
}

/**
 * Belong to isShowOrDel
 */
function isShowOrDelIsSuccess(o){
    hideLoadingModalLayer();
	if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
		window.location.reload();
		return;
	}
    var data = eval("(" + o.responseText + ")");
    if (data.error) {
        alert(data.error);
        return;
    }
    var sru = get("sRepeatUser");
    sru.length = 0;
    if (data.uc == "show") {
        var uns = data.unames;
        for (var i = 0; i < uns.length; i++) {
            sru.options.add(new Option(uns[i].value, uns[i].key));
        }
        replaceSupervisorPanel.show();
        
        get("btnDelDiv").innerHTML = '<input type="button" value="Submit" onclick="deleteUser(' + data.uid + ');"/>';
        
    }
    else {
        if (!confirm("Are you sure to delete the user ? ")) {
            return false;
        }
        deleteUser(data.uid);
    }
}

function judgDate(){
	var creaditDueDay = $('#form123_uvo_userCreateWiPastNumOfDays').val();
	var modifiedDueDay = $('#form123_uvo_userModifiedWiPastNumOfDays').val();
	var nodes = YAHOO.util.Selector.query(".validate-date-ca",'form123');
	var bo = true;
	
	for(var d = 0; d<nodes.length; d++){
		if(!isValidDate(nodes[d].value) && $.trim(nodes[d].value) != "") {
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
	
	if(creaditDueDay < 0 || creaditDueDay > 10000 || creaditDueDay.match(/[^0-9]/)) {
		removeClassName('form123_uvo_userCreateWiPastNumOfDays','validation-passed');
		addClassName('form123_uvo_userCreateWiPastNumOfDays','validation-failed');
		bo = false;
	}
	if(modifiedDueDay < 0 || modifiedDueDay > 10000 || modifiedDueDay.match(/[^0-9]/)) {
		removeClassName('form123_uvo_userModifiedWiPastNumOfDays','validation-passed');
		addClassName('form123_uvo_userModifiedWiPastNumOfDays','validation-failed');
		bo = false;
	}
	
	return bo;
}
