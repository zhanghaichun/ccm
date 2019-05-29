var oQuickLink = {
	url : {
		saveLocation : "saveLocation.action",
		getLocation : "getLocation.action"
	}
}
function saveSearch(){
	if(!validateFields()) return;
	var callback = {
		success: renderQuickLink,
		failure: showError
	};
	var postData = "queryString="+composePostDataFromUi().replace(/&/g, "%26")+"&quicklinkName=" + ccmEscape(queryName.value);
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest('POST' , saveQuicklinkUri , callback , postData );
}

function renderQuickLink(o){
	if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
		window.location.reload();
		return;
	}
	var data = eval("("+o.responseText+")");
	if(data.error){
		alert("Sorry: " + data.error);
		hideLoadingModalLayer();
		return;
	}
	var s = '<li id="ql_'+data.id+'">'
			+ '<table  width="100%" border=0 cellpadding=0 cellspacing=0><tr><td valign="middle"><a href="#" onclick="clickQuickLinkHandler('+ data.id +',\'' + data.quicklinkType  + '\');">'+data.name
			+'</a></td><td><img style="width:16px;height:16px;" src="' + CONTEXT_PATH + 'include/images/reject160.png" href="#" onclick="deleteQuicklink(\''+data.id+'\');" alt="Delete"></td></tr></table>'
			+ '</li>';
	_quicklink.innerHTML += s;
	hideLoadingModalLayer();
	
	queryName.value = ""; // Add By Chao.Liu
}

// Created By Chao.Liu to 2010/08/21 Night
function getThisPageName(quicklinkId){
	var callback = {
		success: getThisPageNameCallback,
		failure: showError
	};
	
	var postData = "quicklinkId=" + quicklinkId;
	YAHOO.util.Connect.asyncRequest('POST' , "getThisPageName.action" , callback , postData );
}
function getThisPageNameCallback(o){
	var data = eval("("+o.responseText+")");
	if(data.error){
		alert("Error: " + data.error);
		return;
	}
	
	var wl = window.location.pathname;
	if(wl.indexOf(data.type)>-1){
		// 这个判断是针对渲染Circuit Tab页面下的子tab内容。
		if(data.quick_type && (data.quick_type == "Circuit" // Child tab => Circuit Inventory
					|| data.quick_type == "VendorInventory" // Child tab => Vendor Inventory
					|| data.quick_type == "OldCircuit" // Child tab => Circuit Cost
					|| data.quick_type == "MasterInventory" )) { // Child tab => Inventory Dashboard
			// @see searchCircuitPageAsMainPanel.js 此方法暂时注释掉 暂时都用populateQuicklinkOnUI方法，后期最终确定再行整理代码
//			changeCircuitPageTabByQuicklinkType(data); 
			populateQuicklinkOnUI(data.id);
		} else {
			populateQuicklinkOnUI(data.id)
		}
	}else{
		window.location = data.type+"?isQuicklink="+data.id;
	}
}

// Modified By Chao.Liu to 2010/08/21 Night
function populateQuicklinkOnUI(quicklinkId){
	
	if(BUSINESS && BUSINESS.hide)BUSINESS.hide();
	
	var actionUri = "getQuicklink.action";
	var callback = {
		success: populateQuicklinkOnUICallback,
		failure: showError
	};

	var postData = "quicklinkId=" + quicklinkId;
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest('POST' , actionUri , callback , postData );
}

function cleanFormElementValue(){
	var f0 = form0.getElementsByTagName("input");
	for(var i = 0; i<f0.length; i++){
		if((new YAHOO.util.Element(f0[i])).get("type")=="text")	f0[i].value = "";
	}
	var f1 = form0.getElementsByTagName("select");
	for(var i = 0; i<f1.length; i++){
		f1[i].selectedIndex = 0;
	}
	resetRadioButton();
}

function clickQuickLinkHandler(quickId,quickType){
	
//	if(quickType == "Circuit" 
//	|| quickType == "VendorInventory" 
//	|| quickType == "MasterInventory" 
//	|| quickType == "OldCircuit") {
//		var data = {"id":quickId,"quick_type":quickType};
//		changeCircuitPageTabByQuicklinkType(data);
//	} else {
//		populateQuicklinkOnUI(quickId);
//	}
	populateQuicklinkOnUI(quickId);
}


function populateQuicklinkOnUICallback(o){
	hideLoadingModalLayer();
	if(!o.responseText){return false;}
	if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
		window.location.reload();
		return;
	}
	var data = eval("("+o.responseText+")");
	if(data.error){
		alert("Error: " + data.error);
		return;
	}
	
	//if(data.mark){return false;}
	cleanFormElementValue();
	try{ clearJSelectQuicklink(); }catch(e){ }
	var dataQuery = data.query;
	var circuitPageOne=dataQuery.circuitPageOne;
	var f0 = form0.getElementsByTagName("input");
	var f1 = form0.getElementsByTagName("select");
	for(var k in dataQuery){
		var f_ = false;
		var v = dataQuery[k];
		try{ 
			if(setJSelectQuicklink(k,v,dataQuery)){
				continue;
			} 
		}catch(e){}
		
		for(var m = 0; m<f0.length; m++){
			if(f0[m].name==k){
				$(f0[m]).val(v);
				var f_ = true;
				break;
			}
		}
		if(f_){
			continue;
		}
		for(var n = 0; n<f1.length; n++){
			if(f1[n].name==k){
				$(f1[n]).val(v);
				break;
			}
		}
	}
	updateRadioButton();
	if(circuitPageOne=="2"||circuitPageOne=="1"){
		getCircuitList();
	}else{
		startSearch();
	}
}

function deleteQuicklink(quicklinkId){
	if(!confirm("Please confirm to delete.")) return;
	var actionUri = CONTEXT_PATH + "deleteQuicklink.action";
	var callback = {
		success: deleteQuicklinkCallback,
		failure: showError
	};

	var postData = "quicklinkId=" + quicklinkId;
//	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest('POST' , actionUri , callback , postData );
}
function deleteQuicklinkCallback(o){
	if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
		window.location.reload();
		return;
	}
	var data = eval("("+o.responseText+")");
	if(data.error){
		alert("Error: " + data.error);
//		hideLoadingModalLayer();
		return;
	}
	var nd = YAHOO.util.Dom.get('ql_'+data.id);
	nd.parentNode.removeChild(nd);
//	hideLoadingModalLayer();
}

YAHOO.util.Event.onDOMReady(function() {
	// Add By Chao.Liu
	if(getUrlParms("isQuicklink")){
		populateQuicklinkOnUI(getUrlParms("isQuicklink"));	
	}
	getMark();
	
//	var handleSubmit = function() {
//		// Add By Chao.Liu
//		var qn = queryName.value;
//		if(qn.length > 30){
//			alert("The name is too long. ");
//			return false;
//		}
//		if(!FIC_checkForm('queryName')) return;
//		YAHOO.ccm.container.saveQueryDialog.hide();
//		saveSearch();
//	};
//	var handleCancel = function() {
//		queryName.value = ""; // Add By Chao.Liu
//		this.cancel();
//	};
//	YAHOO.util.Dom.removeClass("saveQueryDialog", "yui-pe-content");
//	
//	YAHOO.ccm.container.saveQueryDialog = new YAHOO.widget.Dialog("saveQueryDialog", 
//							{ width : "30em",
//							  fixedcenter : true,
//							  visible : false, 
//							  constraintoviewport : true,
//							  buttons : [ { text:"Submit", handler:handleSubmit},
//								      { text:"Cancel", handler:handleCancel } ]
//							});
//	YAHOO.ccm.container.saveQueryDialog.render();
});

function mark(url,name){
	var callback = {
		success: function (url){
			if(name){
				hideLoadingModalLayer();
				window.open(url.responseText,name);
			}else{
				window.location = url.responseText;
			}
		},
		failure: showError
	};

	var postData = "queryString="+composePostDataFromUi().replace(/&/g, "%26")+"&quicklinkName="+ccmEscape(window.location.pathname)+"&url="+ccmEscape(url);
	showLoadingModalLayer();
	YAHOO.util.Connect.asyncRequest('POST' , oQuickLink.url.saveLocation , callback , postData );
	
}
function getMark(){
	var callback = {
		success: populateQuicklinkOnUICallback,
		failure: showError
	};

	var postData = "quicklinkName="+ccmEscape(window.location.pathname);
	YAHOO.util.Connect.asyncRequest('POST' , oQuickLink.url.getLocation , callback , postData );
}
