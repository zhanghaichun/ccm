// @Auchor chao.Liu (Optimization of complete by xinyu.Liu)
var adminScoaTab = {
	$ : function (id){ // Get DOM
		return typeof (id) == "string" ? document.getElementById(id) : id;
	},
	actionUri : {
		doCheckScoaCodeNameUri : "doCheckScoaCodeName.action",
		doSaveNewScoaCodeUri : "doSaveNewScoaCode.action",
		doInactiveScoaCodeUri : "doInactiveScoaCode.action"
	},
	init : function (){ // The Begain Loading
		adminScoaTab.$("txtScoaCodeName_NSCT").value = "";
		adminScoaTab.$("inactiveScoaCodeName_NSCT").value = "";
		adminScoaTab.$("btnNewScoaCode_NSCT").disabled = "disabled";
		adminScoaTab.$("btnInactiveScoaCode_NSCT").disabled = "disabled";
	},
	req : { // TextBox Key Up Validate
		onkeyupReq : function (sname){
			var req = /^[\d|.]*$/; 
			var cs = sname.split("");
			var str = ""; 
			
			for(var i=0; i<cs.length; i++){ if(req.exec(cs[i])){ str += cs[i]; } }
			
			adminScoaTab.$("txtScoaCodeName_NSCT").value = str;
			if(sname.length == 42){
				adminScoaTab.$("btnNewScoaCode_NSCT").disabled = "";
			}else{
				adminScoaTab.$("btnNewScoaCode_NSCT").disabled = "disabled";
			}
		},
		onkeyupInactiveReq : function (sname){
			var req = /^[\d|.]*$/; 
			var cs = sname.split("");
			var str = ""; 
			
			for(var i=0; i<cs.length; i++){ if(req.exec(cs[i])){ str += cs[i]; } }
			
			adminScoaTab.$("inactiveScoaCodeName_NSCT").value = str;
			if(sname.length == 42){
				adminScoaTab.$("btnInactiveScoaCode_NSCT").disabled = "";
			}else{
				adminScoaTab.$("btnInactiveScoaCode_NSCT").disabled = "disabled";
			}
		},
		submitReq : function (sname){ // Scoa Code Name Validate
			var req = /^([\d]{3}\.)([\d]{4}\.)([\d]{4}\.)([\d]{4}\.)([\d]{5}\.)([\d]{3}\.)([\d]{4}\.)([\d]{4}\.)([\d]{3})$/;
			if(!req.exec(sname)){
				alert("Please enter the right SCOA code, eg. 256.1101.0000.5411.52322.627.0000.0000.000");
				return false;
			}
			return true;
		},
		snameReq : function (sname){ // SCOA Code Name Can't Repeat
			var repeatBoo = false;
			
			var check = {
				star : function (name){
					var callback = {
						success: check.end,
						failure: showError
					};
					var starParamer = "avo.scoaName=" + ccmEscape(name);
					YAHOO.util.Connect.asyncRequest('POST' , adminScoaTab.actionUri.doCheckScoaCodeNameUri , callback , starParamer )
				},
				end : function (o){
					if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
						window.location.reload();
						return;
					}
					var data = eval("("+o.responseText+")");
					if(data.error){
						alert(data.error);
						return;
					}
					if(data.update){
						if(confirm(data.update)){
							adminScoaTab.saveNewScoaCode(sname,"0");
							return;
						}else{
							return;
						}
					}
					
					adminScoaTab.saveNewScoaCode(sname,"1");
					repeatBoo = true;
				}
			}
			check.star(sname);
			return repeatBoo;
		},
		snameInactiveReq : function (sname){
			var repeatInactiveBoo = false;
			
			var inactiveCheck = {
				star : function (name){
					var callback = {
						success: inactiveCheck.end,
						failure: showError
					};
					var starParamer = "avo.scoaName=" + ccmEscape(name);
					YAHOO.util.Connect.asyncRequest('POST' , adminScoaTab.actionUri.doCheckScoaCodeNameUri , callback , starParamer )
				},
				end : function (o){
					if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
						window.location.reload();
						return;
					}
					var data = eval("("+o.responseText+")");
					if(data.update == "This SCOA code was inactive. Do you want to reactive it?"){
						alert("this scoa code is already inactive!");
					}else if(data.error == "This SCOA code exists already!"){
						adminScoaTab.inactiveScoaCodeSubmit(sname);
					} else if (!data.error){
						alert("No this scoa code!");
						return;
					} else {
						alert(data.error);
						return;
					}
					repeatInactiveBoo = true;
				}
			}
			inactiveCheck.star(sname);
			return repeatInactiveBoo;
		}
	},
	inactiveScoaCodeSubmit : function (sname){ //Inactive Scoa Code
		var inactiveBoo = false;
		
		var inactiveSubmit = {
			star : function (name){
				var callback = {
					success: inactiveSubmit.end,
					failure: showError
				};
				var starParamer = "avo.scoaName=" + ccmEscape(name);
				YAHOO.util.Connect.asyncRequest('POST' , adminScoaTab.actionUri.doInactiveScoaCodeUri , callback , starParamer );
				showLoadingModalLayer();
			},
			end : function (o){
				if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
					window.location.reload();
					return;
				}
				var data = eval("("+o.responseText+")");
				if(data.error){
					alert(data.error);
					return;
				}
				hideLoadingModalLayer();
				alert("You inactive "+sname+" from TEMS successfully");
				adminScoaTab.init();
				inactiveBoo = true;
			}
		}
		inactiveSubmit.star(sname);
		return inactiveBoo;
	},
	saveNewScoaCode : function (sname,type){ // Save New Scoa Code
		var boo = false;
		
		var submit = {
			star : function (name,type){
				var callback = {
					success: submit.end,
					failure: showError
				};
				var starParamer = "avo.scoaName=" + ccmEscape(name) +"&type="+type;
				YAHOO.util.Connect.asyncRequest('POST' , adminScoaTab.actionUri.doSaveNewScoaCodeUri , callback , starParamer )
			},
			end : function (o){
				if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
					window.location.reload();
					return;
				}
				var data = eval("("+o.responseText+")");
				if(data.error){
					alert(data.error);
					return;
				}
				alert("You added "+sname+" into TEMS successfully");
				adminScoaTab.init();
				boo = true;
			}
		}
		submit.star(sname,type);
		return boo;
	},
	newScoaCode : function (){ // Submit
		var sname = adminScoaTab.$("txtScoaCodeName_NSCT").value;
		if(!adminScoaTab.req.submitReq(sname))return false;
		if(!adminScoaTab.req.snameReq(sname))return false;
	},
	inactiveScoaCode : function (){
		var sname = adminScoaTab.$("inactiveScoaCodeName_NSCT").value;
		if(!adminScoaTab.req.submitReq(sname))return false;
		if(!adminScoaTab.req.snameInactiveReq(sname))return false;
	}
}
