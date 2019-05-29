//@author chao.Liu (Optimization of complete by xinyu.Liu)
rtagReportAdminAction = {
	searchReportsUri : "doSearchReportsSelect.action",
	saveReportsUri : "dosaveReports.action",
	searchTagsSelectUri : "doSearchTagsSelect.action"
};

function initialization(o){
//	YAHOO.ccm.container.delReportAdminTags.hide();
	YAHOO.ccm.container.updateReportAdminTagsAndRoles.hide();
	YAHOO.ccm.container.updateReportAdminTags.hide();
	YAHOO.ccm.container.updateTeams.hide();
	YAHOO.ccm.container.updateSecurityIp.hide();
	YAHOO.example.colorpicker.inDialog.dialog.hide();
	if(document.getElementById('__validation_tag_innetHTML')){
		document.getElementById('__validation_tag_innetHTML').innerHTML = "";
	}
	YAHOO.ccm.container.movePreviousAdjustmentPanel.hide();
	YAHOO.ccm.container.moveCurrentToAdjustmentPanel.hide();
	if($("#wikiPublish")){
		$("#wikiPublish").hide();
	}
}

function getDOM(id){
	return typeof (id) == "string" ? document.getElementById(id) : id;
}

function saveReports(){
	var callback = {
		success: function(o){
			if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
				window.location.reload();
				return;
			}
			var data = eval("("+o.responseText+")");
			if(data.error){
				alert("Search Reports Error:" + data.error);
				return;
			}
		},
		failure: showError
	};
	var saveReportsPramer = "viewReportAdminVO.rtagId=" + $('#sTagsNameList').val() +
							"&viewReportAdminVO.optionParamer=" + saveOptionParamer();
	YAHOO.util.Connect.asyncRequest('POST' , rtagReportAdminAction.saveReportsUri , callback ,saveReportsPramer) 
}

function cancelReports(){
	searchReports($('#sTagsNameList').val());
}

//Copy Left Value To Right 
function selectMoveOptionTR(){
	$("#addTR").click(
		function (){
			var $options = $("#sReportAdmin1 option:selected");
			$options.appendTo("#sReportAdmin2");
		}			
	);
	$("#add_allTR").click(
		function (){
			var $options = $("#sReportAdmin1 option");
			$options.appendTo("#sReportAdmin2");
		}			
	);
	$("#sReportAdmin1").dblclick(
		function (){
			var $options = $("option:selected",this);
			$options.appendTo("#sReportAdmin2");
		}			
	);
	$("#removeTR").click(
		function (){
			var $options = $("#sReportAdmin2 option:selected");
			$options.appendTo("#sReportAdmin1");
		}			
	);
	$("#remove_allTR").click(
		function (){
			var $options = $("#sReportAdmin2 option");
			$options.appendTo("#sReportAdmin1");
		}			
	);
	$("#sReportAdmin2").dblclick(
		function (){
			var $options = $("option:selected",this);
			$options.appendTo("#sReportAdmin1");
		}			
	);
}

//Make Save Report Id Array
function saveOptionParamer(){
	var o = $("#sReportAdmin2 option");
	var saveArray = "";
	for(var i=0;i<o.length;i++){
		saveArray += o[i].value;
		if((i+1)<o.length) saveArray += ",";
	}
	if(o.length == 0) saveArray = "DelAll";
	return saveArray;
}

//Clear Selete
function clearSelect(){
	var $options = $("#sReportAdmin1 option");
	$options.remove();
	$options = $("#sReportAdmin2 option");
	$options.remove();
}

//Search Selete Value
function searchReports(rtagId){
	if(rtagId == "isNull"){
		clearSelect();
		getDOM("btnSave").disabled = "disabled";
		getDOM("btnCancel").disabled = "disabled";
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
				alert("Search Reports Error:" + data.error);
				return;
			}
			clearSelect();
			var s1data = data.s1;
			for(var i=0;i<s1data.length;i++){
				$("#sReportAdmin1").append('<option value="'+s1data[i].key+'">' + s1data[i].value+'</option>');
			}
			var s2data = data.s2;
			for(var i=0;i<s2data.length;i++){
				$("#sReportAdmin2").append('<option value="'+s2data[i].key+'">' + s2data[i].value+'</option>');
			}
			selectMoveOptionTR();
			getDOM("btnSave").disabled = "";
			getDOM("btnCancel").disabled = "";
		},
		failure: showError
	};
	var searchReportsPramer = "viewReportAdminVO.rtagId=" + rtagId;
	YAHOO.util.Connect.asyncRequest('POST' , rtagReportAdminAction.searchReportsUri , callback ,searchReportsPramer) 
}

function searchTagsSelect(){
	var callback = {
		success: function(o){
			if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
				window.location.reload();
				return;
			}
			var data = eval("("+o.responseText+")");
			if(data.error){
				alert("Search Tags Error:" + data.error);
				return;
			}
			clearSelect();
			getDOM("btnSave").disabled = "disabled";
			getDOM("btnCancel").disabled = "disabled";
			
			var $options = $("#sTagsNameList option");
			$options.remove();
			
			for(var i=0;i<data.length;i++){
				$("#sTagsNameList").append('<option value="'+data[i].key+'">'+data[i].value+'</option>');
			}
		},
		failure: function(o){
			alert("Selete Tags Wrong!");
		}
	};
	YAHOO.util.Connect.asyncRequest('POST' , rtagReportAdminAction.searchTagsSelectUri , callback ) 
}