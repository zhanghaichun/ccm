// @author xinyu.Liu (Optimization of complete by xinyu.Liu)
var da;

userSecurityActionUri = {
	savePreviledgeVendorBanUri : "savePreviledgeVendorBan.action",
	saveDelegationUri : "saveDelegation.action",
	upSaveDelegationUri : "upSaveDelegation.action",
	deletePreviledgeVendorBanUri : "daletePreviledgeVendorBan.action",
	deleteDelegationUri : "daleteDelegation.action",
	searchDelegationUri : "searchDelegation.action",
	searchPreviledgeVendorBanUri : "searchPreviledgeVendorBan.action",
	getDelegationTotalPageNoUri : "getDelegationTotalPageNo.action",
	getPreviledgeVendorBanTotalPageNoUri : "getPreviledgeVendorBanTotalPageNo.action",
	resetFailureCountUri : "resetFailureCount.action",
	upRoleAndUserPsUri : "updateUserRoleAndPassword.action",
	addUpRoleAndUserPsUri : "addUserRoleAndPassword.action",
	modifyRadioUri : "updateRadio.action",
	judgeUserNameUri : "verifyUserNameExists.action",
	verifyPreviledgeDataUri : "verifyPreviledgeData.action",
	getSearchAdminActionUri : "searchAdminAction.action"
};
 
// Modified By Chao.Liu to 10/07/22 AM
YAHOO.util.Event.onDOMReady(function(){
	var adminPageTabView = new YAHOO.widget.TabView('adminTab');
	initSaveConfirm();
	buttonConfirm();
	if(user){
		getDelegation();
		getPreviledgeVendorBan();
		$('#viewSecurityVO_delegationDateStartsOn').val('');
		$('#viewSecurityVO_delegationDateEndsOn').val('');
		resetFormElementPreviledge();
	}else{
		document.getElementById("pswReadStar").style.display = "";
		document.getElementById("pswaReadStar").style.display = "";
	}
	$('#password').val('');
	
});

activeIndexClick = function(index){
	showLoadingModalLayer();
	var uri = userSecurityActionUri.getSearchAdminActionUri + "?adminTabActiveIndex=" + index;
	windowLocationHref(uri);
};

//Paging and demonstration tabulation  (### Delegation)
function  getDelegation (){
	if(!window.delegationPage){
		delegationPage = new SANINCO.Page('_dataTableDelegation','delegationPage',{
		    sortingField : 'ud.start_date',
		    sortingDirection : 'desc',
		    vo : 'viewSecurityVO',
		    totalPageUri : userSecurityActionUri.getDelegationTotalPageNoUri,
		    dataUri : userSecurityActionUri.searchDelegationUri,
			paginationDiv : '_dataTableDelegation_page',
			recPerArray : [5,10,15,20,30,50,80,100],
		    cols : [
				   { title : "Name", width : "25%", dataField : "toUser", sort : "ud.to_user_id"
			    },{ title : "Start Date", width : "22%", dataField : "startTime", sort : "ud.start_date"
			    },{ title : "End Date", width : "20%", dataField : "endTime", sort : "ud.end_date"
			    },{ title : "Delete", width : "16%", dataField : function(o){return "<img src=\"include/images/reject16.png\" border=\"0\"  onclick=\"invoke('\delDelegation("+ o.id +"\)');\"/>";}
			    },{ title : "Edit", width : "17%", dataField : function(o){return "<img src=\"include/images/Edit-icon-s.png\" border=\"0\" onclick=\"upDelegation("+ o.id +");\"/>";}
			    }
			]
		});
		
		delegationPage.addTotalSuccessEvent(function(m,t){
			if(m.totalResultNo == 0) da = undefined; 
		});
		
		delegationPage.addSuccessEvent(function(data){
			da = data.data;
		});
	}

	delegationPage.voParam = {
		"userId":userId
	};
	
	document.getElementById('_buttonDelegation').innerHTML = "<input type=\"button\" value=\" Add \" onclick=\"saveDelegation();\">";
	$('#viewSecurityVO_delegationDateStartsOn').val('');
    $('#viewSecurityVO_delegationDateEndsOn').val('');
	 
	delegationPage.start();
}

//Paging and demonstration tabulation  (### Previledge)
function  getPreviledgeVendorBan (){
	if(!window.previledgeVendorBanPage){
		previledgeVendorBanPage = new SANINCO.Page('_dataTablePreviledge',"previledgeVendorBanPage",{
		    sortingField : "u.created_timestamp",
		    sortingDirection : "desc",
		    vo : "viewSecurityVO",
		    totalPageUri : userSecurityActionUri.getPreviledgeVendorBanTotalPageNoUri,
		    dataUri : userSecurityActionUri.searchPreviledgeVendorBanUri,
			paginationDiv : "_dataTablePreviledge_page",
			recPerArray : [5,10,15,20,30,50,80,100],
		    cols : [{ title : "Vendor", dataField : "vendor", sort : "v.vendor_name", filtration : {name : "v.vendor_name", alias : "Vendor"}
			    }, { title : "BAN", dataField : "ban", sort : "b.account_number", filtration : {name : "b.account_number", alias : "BAN"}
			    }, { title : "Delete", dataField :  function(o){return "<img src=\"include/images/reject16.png\" border=\"0\"  onclick=\"invoke('delPreviledge\("+ o.id +"\)');\"/>";}
			    }
			]
		});
	}
	
	filter1 = new SANINCO.Filter();
    filter1.addEditeEvent(function(){previledgeVendorBanPage.start();});
    filter1.add('v.vendor_name', 'String');
	filter1.add('b.account_number', 'String');
	
    previledgeVendorBanPage.setFilter(filter1);
	
	previledgeVendorBanPage.voParam = {
		"userId":userId
	};

	previledgeVendorBanPage.start();
}

function composePostDataFromUi(){
	var postData = "viewSecurityVO.userId=" + userId
		+ "&viewSecurityVO.toUserId="+$('#viewSecurityVO_delegationId').val();
	
	if($('#viewSecurityVO_delegationDateStartsOn').val()!="") postData += ("&viewSecurityVO.delegationDateStartsOn=" + $('#viewSecurityVO_delegationDateStartsOn').val());
	if($('#viewSecurityVO_delegationDateEndsOn').val()!="") postData += ("&viewSecurityVO.delegationDateEndsOn=" + $('#viewSecurityVO_delegationDateEndsOn').val());
	
	if(securityPreviledgeVendorId.getValue()!="all"){
		postData += ("&viewSecurityVO.previledgeVendorId="+securityPreviledgeVendorId.getValue());
	}else{
		postData += ("&viewSecurityVO.previledgeVendorId=0");
	}
	if($('#security_previledge_banId').val()!="all"){
		postData += ("&viewSecurityVO.previledgeBanId="+$('#security_previledge_banId').val());
	}else{
		postData += ("&viewSecurityVO.previledgeBanId=0"); 
	}
	return postData;
}

//modify active_flag
function modifyRadio(){
	var radio = $('#__active_flag').val();
	var modifyStr = "";
	if(radio == "De-active") modifyStr = "N";
	if(radio == "Active") modifyStr = "Y";
	
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
			if(modifyStr == "Y") $('#__active_flag').val('De-active');
			if(modifyStr == "N") $('#__active_flag').val('Active');
		},
		failure: showError
	};
	var pdata = "viewSecurityVO.userId=" + userId + "&viewSecurityVO.activeFlag=" + modifyStr;
	YAHOO.util.Connect.asyncRequest('POST' , userSecurityActionUri.modifyRadioUri, callback, pdata); 
}

//reset Failure Count
function resetFailureCount(){
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
			document.getElementById('__login_Failure_Count').innerHTML = "0";
		},
		failure: showError
	};
	YAHOO.util.Connect.asyncRequest('POST' , userSecurityActionUri.resetFailureCountUri+"?userId="+userId , callback ); 
}

//update Dropdown List
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

////Get BAN list by Vendor id  ###  Previledge////
function getBanListByVendorIdByPreviledge(selIndex){
	if(securityPreviledgeVendorId.getValue()=='all'){
		updateDropdownList([], "id", "no", 'security_previledge_banId',selIndex, {key:"all",value:"All"});
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
			updateDropdownList(data, "id", "no", 'security_previledge_banId',selIndex, {key:"all",value:"All"});
			delete data;
		},
		failure: showError
	};

	var pdata = "selVendorId=" + securityPreviledgeVendorId.getValue();
	YAHOO.util.Connect.asyncRequest('POST' , "getBanListByVendorId.action" , callback , pdata); 
}

//add an Previledge of vendor and ban 
function addPreviledge(){
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
			getPreviledgeVendorBan();
		},
		failure: showError
	};

	var previousPostedData = composePostDataFromUi();
	showLoadingModalLayer();
	resetFormElementPreviledge();
	YAHOO.util.Connect.asyncRequest('POST' , userSecurityActionUri.savePreviledgeVendorBanUri , callback , previousPostedData); 
}

//Verify data exists
function verifyPreviledgeData(){
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
			if(data.data != true){
				alert("The same data already exists.");
				return;
			}
			addPreviledge();
		},
		failure: showError
	};

	var previousPostedData = composePostDataFromUi();
	YAHOO.util.Connect.asyncRequest('POST' , userSecurityActionUri.verifyPreviledgeDataUri , callback , previousPostedData); 
}

//delete an Previledge of vendor and ban 
function delPreviledge(o){
	YAHOO.ccm.container.saveConfirm.hide();
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
			getPreviledgeVendorBan();
		},
		failure: showError
	};

	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest('POST' , userSecurityActionUri.deletePreviledgeVendorBanUri + "?userPreviledgeId="+o , callback); 
}

function resetFormElementPreviledge(){
	//getVendorListByVendorIdByPreviledge(0);
	updateDropdownList([], "id", "no", 'security_previledge_banId',0, {key:"all",value:"All"});
}

//save an Delegation of start date and end date 
function saveDelegation(){
	if((!judgeDelegationUser()) | (!judgeDate()) | (!judgeDateLegality())) return;
	FIC_checkForm('form1');
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
			$('#viewSecurityVO_delegationId').val("");
			getDelegation();
		},
		failure: showError
	};

	var previousPostedData = composePostDataFromUi();
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest('POST' , userSecurityActionUri.saveDelegationUri , callback , previousPostedData); 
}

//delete an Delegation of start date and end date 
function delDelegation(o){
	YAHOO.ccm.container.saveConfirm.hide();
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
			getDelegation();
		},
		failure: showError
	};

	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest('POST' , userSecurityActionUri.deleteDelegationUri + "?userDelegationId="+o , callback); 
}

//update an Delegation of start date and end date 
function upDelegation(o){
	for(var i = 0; i<da.length; i++){
		var row = da[i];
		if(row.id == o){
			 $('#viewSecurityVO_delegationDateStartsOn').val(row.startTime);
			 $('#viewSecurityVO_delegationDateEndsOn').val(row.endTime);
			 
		var ops = document.getElementById('viewSecurityVO_delegationId').options;
		  for(var count=0; count < ops.length; count ++){
		    var value = ops[count].innerHTML;
		    if(value == row.toUser){
		      ops[count].selected = true;
		    }
		  }
		}
	}
	document.getElementById('_buttonDelegation').innerHTML = "<input type=\"button\" value=\" Edit \" onclick=\"upSaveDelegation("+o+");\">";
}

//update User Delegation 
function upSaveDelegation(o){
	if(!judgeDelegationUser()) return;
	if(!judgeDate()) return;
	if(!judgeDateLegality(o)) return;
	FIC_checkForm('form1');
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
			$('#viewSecurityVO_delegationId').val("");
			getDelegation();
		},
		failure: showError
	};

	var previousPostedData = composePostDataFromUi();
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest('POST' , userSecurityActionUri.upSaveDelegationUri+"?upDelegationId="+o , callback , previousPostedData); 
}

//Determine the legality of the date of
function judgeDateLegality(o){
	var bo = true;
	if(da != undefined){
		var rows = da;
		var timeStarts = $('#viewSecurityVO_delegationDateStartsOn').val(); 
		var timeEnds = $('#viewSecurityVO_delegationDateEndsOn').val(); 
		
		 for(var i = 0; i<rows.length; i++){
		 	var row = rows[i];
		 	if(o!=row.id){
			 	if(row.startTime <= timeStarts && row.endTime >= timeStarts){
			 		removeClassName('viewSecurityVO_delegationDateStartsOn','validation-passed');
					addClassName('viewSecurityVO_delegationDateStartsOn','validation-failed');
					bo = false;
			 	}
			 	if(row.startTime <= timeEnds && row.endTime >= timeEnds){
					removeClassName('viewSecurityVO_delegationDateEndsOn','validation-passed');
					addClassName('viewSecurityVO_delegationDateEndsOn','validation-failed');
					bo = false;
			 	}
			 	if(timeStarts <= row.startTime && timeEnds >= row.startTime){
			 		removeClassName('viewSecurityVO_delegationDateStartsOn','validation-passed');
					addClassName('viewSecurityVO_delegationDateStartsOn','validation-failed');
					bo = false;
			 	}
			 	if(timeStarts <= row.endTime && timeEnds >= row.endTime){
					removeClassName('viewSecurityVO_delegationDateEndsOn','validation-passed');
					addClassName('viewSecurityVO_delegationDateEndsOn','validation-failed');
					bo = false;
			 	}
		 	}
		 }
	 }
	 delete rows;
	 return bo;
}

function cancelPage(){
	window.location.reload();
}

String.prototype.TextFilter=function(){
	var pattern=new RegExp("[`~%!@#^=''?~��@#������&����������'��*()������,��.��]");
	var rs="";
	for(var i=0;i<this.length;i++){
			rs+=this.substr(i,1).replace(pattern,'');
	}
	return rs;
}

function userNameCheck(){
	
	var uname=$('#form0_user_name').val(); //ͨ��IDȡ��texteara��ֵ
	var txt=uname.TextFilter(); //���������ȥ�ַ�
	if(txt!=uname){
		alert("Username contains special characters!");
		return false;
	}
	return true;
	
}

function userNameNotNull(divId){
	if(document.getElementById(divId).value == undefined || document.getElementById(divId).value == ""){
		removeClassName(divId,'validation-passed');
		addClassName(divId,'validation-failed');
		return false;
	}
	return true;
}

//Modify the role List and password status 
function upRoleAndUserPs(){
	document.getElementById('__judge_password').innerHTML= "";
	if(!userNameCheck()) return; // add by mingyang.li 2014-02-07
	if((!validateFields()) | (!judgePassword()) | (!userNameNotNull('form0_user_name')) |
	 (!userNameNotNull('form0_first_name')) | (!userNameNotNull('form0_last_name')) |
	 ((!user) && (!judgePasswordNotNo()))) return;
	FIC_checkForm('form0');
	document.getElementById('__judge_password').innerHTML= "";	
	judgeUserName();
}

function updateUserData(){
	if(!user) userSecurityActionUri.upRoleAndUserPsUri = userSecurityActionUri.addUpRoleAndUserPsUri;
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
			refresh();
		},
		failure: showError
	};
	var previousPostedData = composePostDataUi();
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest('POST' , userSecurityActionUri.upRoleAndUserPsUri , callback , previousPostedData); 
}

function composePostDataUi(){
	var uid = userId;
	var strLock = document.getElementsByName("lockBox")[0].checked == true ? "Y" : "N";
	var postData = "viewSecurityVO.userId=" + uid
		+ "&viewSecurityVO.password=" + $('#password').val()
		+ "&viewSecurityVO.lockOut=" + strLock;
		
	if(getRoleString()!="") postData += ("&viewSecurityVO.roleString=" + getRoleString());
	if($('#form0_first_name').val()!="") postData += ("&viewSecurityVO.firstName=" + ccmEscape($('#form0_first_name').val()));
	if($('#form0_last_name').val()!="") postData += ("&viewSecurityVO.lastName=" + ccmEscape($('#form0_last_name').val()));
	if($('#form0_address').val()!="") postData += ("&viewSecurityVO.address=" + ccmEscape($('#form0_address').val()));
	if($('#form0_primary_phone').val()!="") postData += ("&viewSecurityVO.primaryPhone=" + ccmEscape($('#form0_primary_phone').val()));
	if($('#form0_office_phone').val()!="") postData += ("&viewSecurityVO.officePhone=" + ccmEscape($('#form0_office_phone').val()));
	if($('#form0_cell_plon').val()!="") postData += ("&viewSecurityVO.cellPlon=" + ccmEscape($('#form0_cell_plon').val()));
	if($('#form0_fax_number').val()!="") postData += ("&viewSecurityVO.faxNumber=" + ccmEscape($('#form0_fax_number').val()));
	if($('#form0_email').val()!="") postData += ("&viewSecurityVO.email=" + ccmEscape($('#form0_email').val()));
	if($('#form0_initials').val()!="") postData += ("&viewSecurityVO.initials=" + ccmEscape($('#form0_initials').val()));
	if($('#form0_supervis').val()!="") postData += ("&viewSecurityVO.supervisId=" + ccmEscape($('#form0_supervis').val()));
	if($('#form0_backupUserId').val()!="") postData += ("&viewSecurityVO.backupUserId=" + ccmEscape($('#form0_backupUserId').val()));
	if($('#form0_user_name').val()!="") postData += ("&viewSecurityVO.userName=" + ccmEscape($('#form0_user_name').val()));
//	if($('#form0_arpprove_email_flag').val()) postData += ("&viewSecurityVO.approveEmailFlag=" + ccmEscape($('#form0_arpprove_email_flag').val()));
//	postData = postData.replace("%", "%25");
	return postData;
}

//Refresh the page 
function refresh() {
	if(!user){
		var uri = "searchAdminAction.action";
		windowLocationHref(uri);
	}else{
		hideLoadingModalLayer();
		YAHOO.ccm.container.buttonConfirm.show();
	}
}

//Get a string role List
function getRoleString(){
	var strRole=document.getElementsByName('roleBox');
	var objarray=strRole.length;
	var roleId="";
	for (i=0;i<objarray;i++){
		if(strRole[i].checked == true) roleId += strRole[i].value+",";
	}
	return roleId;
}

//Determine the password are consistent 
function judgePassword(){
	if($('#password').val() != "" && $('#passwordAgain').val() != ""){
		if($('#password').val() == $('#passwordAgain').val()){
			return true;
		}
	}
	if($('#password').val() == "" && $('#passwordAgain').val() == "") return true;
	document.getElementById('__judge_password').innerHTML= "Once the password is not consistent.";	
	removeClassName('password','validation-passed');
	addClassName('password','validation-failed');
	removeClassName('passwordAgain','validation-passed');
	addClassName('passwordAgain','validation-failed');
	return false;
}

function judgePasswordNotNo(){
	if($('#password').val() != "" && $('#passwordAgain').val() != ""){
		if($('#password').val() == $('#passwordAgain').val()) return true;
	}
	if($('#password').val() != $('#passwordAgain').val()){
		document.getElementById('__judge_password').innerHTML= "Once the password is not consistent.";	
	}
	removeClassName('password','validation-passed');
	addClassName('password','validation-failed');
	removeClassName('passwordAgain','validation-passed');
	addClassName('passwordAgain','validation-failed');
	return false;
}

function dateValidity(date){
	var day = new Date();
	var startsDay = new Date(date.replace(/(-)/g,'/'));
	var days = day.getFullYear() + "/" + (day.getMonth() + 1) + "/" + day.getDate(); 
	var startsDays = startsDay.getFullYear() + "/" + (startsDay.getMonth() + 1) + "/" + startsDay.getDate(); 
	if(new Date(days) <= new Date(startsDays)){
		return true;
	}else{
		removeClassName('viewSecurityVO_delegationDateStartsOn','validation-passed');
		addClassName('viewSecurityVO_delegationDateStartsOn','validation-failed');
		return false;
	}
}

//judge DelegationUser
function judgeDelegationUser(){
	if ($('#viewSecurityVO_delegationId').val() == "") {
		removeClassName('viewSecurityVO_delegationId','validation-passed');
		addClassName('viewSecurityVO_delegationId','validation-failed');
		return false;
	}else{
		removeClassName('viewSecurityVO_delegationId','validation-failed');
		addClassName('viewSecurityVO_delegationId','validation-passed');
		return true;
	}
}


//Determine the Date are consistent 
function judgeDate(){
	var bo = true;
	var timeStarts = $('#viewSecurityVO_delegationDateStartsOn').val(); 
	var timeEnds = $('#viewSecurityVO_delegationDateEndsOn').val(); 
	
	if(!dateValidity(timeStarts)) bo = false;
	
	if(timeStarts != "" && timeEnds != ""){
		if(timeStarts > timeEnds){
			removeClassName('viewSecurityVO_delegationDateStartsOn','validation-passed');
			addClassName('viewSecurityVO_delegationDateStartsOn','validation-failed');
			removeClassName('viewSecurityVO_delegationDateEndsOn','validation-passed');
			addClassName('viewSecurityVO_delegationDateEndsOn','validation-failed');
			bo = false;
		}
		
		if(!isValidDateFormat(timeStarts)){
			removeClassName('viewSecurityVO_delegationDateStartsOn','validation-passed');
			addClassName('viewSecurityVO_delegationDateStartsOn','validation-failed');
			bo = false;
		}
		if(!isValidDateFormat(timeEnds)){
			removeClassName('viewSecurityVO_delegationDateEndsOn','validation-passed');
			addClassName('viewSecurityVO_delegationDateEndsOn','validation-failed');
			bo = false;
		}
	}
	if(timeStarts == ""){
		removeClassName('viewSecurityVO_delegationDateStartsOn','validation-passed');
		addClassName('viewSecurityVO_delegationDateStartsOn','validation-failed');
		bo = false;
	}
	
	if(timeEnds == ""){
		removeClassName('viewSecurityVO_delegationDateEndsOn','validation-passed');
		addClassName('viewSecurityVO_delegationDateEndsOn','validation-failed');
		bo = false;
	}
	return bo;
}

function validateFields(){
	return FIC_checkForm('form0_user_data');
}

// Modified By Chao.Liu to 10/07/22 AM
function initSaveConfirm() {
	var handleSubmit = function() {
		eval(targetFun);
	};
	var handleCancel = function() {
		this.cancel();
	};
	YAHOO.util.Dom.removeClass("saveConfirm", "save-confirm");
	
	YAHOO.ccm.container.saveConfirm = new YAHOO.widget.Dialog("saveConfirm", 
							{ width : "30em",
							  fixedcenter : true,
							  visible : false, 
							  constraintoviewport : true,
							  buttons : [ { text:"Submit", handler:handleSubmit },
								      { text:"Cancel", handler:handleCancel } ]
							});
	YAHOO.ccm.container.saveConfirm.render();
};

function buttonConfirm() {
	var handleSubmit = function() {
		var uri = "searchAdminAction.action";
		windowLocationHref(uri);
	};
	var handleCancel = function() {
		window.location.reload();
	};
	YAHOO.util.Dom.removeClass("buttonConfirm", "yui-pe-content");
	
	YAHOO.ccm.container.buttonConfirm = new YAHOO.widget.Dialog("buttonConfirm", 
							{ width : "30em",
							  fixedcenter : true,
							  visible : false, 
							  constraintoviewport : true,
							  buttons : [ { text:"Go to User Search", handler:handleSubmit },
								      { text:"Refresh the current page", handler:handleCancel } ]
							});
	YAHOO.ccm.container.buttonConfirm.render();
};

//Call the confirmation box 
function invoke(fun){
	targetFun = fun; 
	YAHOO.ccm.container.saveConfirm.show();
}

//Determine the user name can not be the same
function judgeUserName(){
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
				alert("User name already exists!");
				removeClassName('form0_user_name','validation-passed');
				addClassName('form0_user_name','validation-failed');
				return;
			}else{
				updateUserData();
			}
			delete data;
		},
		failure: showError
	};

	var userData = "viewSecurityVO.userName=" + ccmEscape($('#form0_user_name').val());
	if(user)userData += "&viewSecurityVO.userId=" + userId;
	YAHOO.util.Connect.asyncRequest('POST' , userSecurityActionUri.judgeUserNameUri , callback , userData); 
}

