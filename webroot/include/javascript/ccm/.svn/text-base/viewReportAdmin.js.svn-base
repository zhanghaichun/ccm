//@author xinyu.Liu (Optimization of complete by xinyu.Liu)
var tagsAndRolesDataString;
var dataString;
var prompt;
var reportAdminTagsHTML = "";
var reportAdminTagsAndRolesHTML = "";

actionUri = {
	 delReportTagsUri : "delReportTags.action",
	 updateReportAdminTagsUri : "updateReportAdminTags.action",
	 addReportAdminTagsUri : "addReportAdminTags.action",
	 getReportAdminTagsTotalPageNoUri : "getReportAdminTagsTotalPageNo.action",
	 searchReportAdminTagsUri : "searchReportAdminTags.action",
	 delReportTagsAndRolesUri : "delReportTagsAndRoles.action",
	 updateReportAdminTagsAndRolesUri : "updateReportAdminTagsAndRoles.action",
	 addReportAdminTagsAndRolesUri : "addReportAdminTagsAndRoles.action",
	 getReportAdminTagsAndRolesTotalPageNoUri : "getReportAdminTagsAndRolesTotalPageNo.action",
	 searchReportAdminTagsAndRolesUri : "searchReportAdminTagsAndRoles.action",
	 reportAdminTagsAndRolesJudgementDataUri : "reportAdminTagsAndRolesJudgementData.action",
	 validationTagsUri : "validationTags.action",
	 associationDeleteTagsUri : "associationDeleteTags.action"
};

YAHOO.util.Event.onDOMReady(function(){
	getTags();
//    delReportAdminTagsWindows();
//	updateReportAdminTagsAndRolesWindows();
//	updateReportAdminTagsWindows();
	
	reportAdminTagsAndRolesHTML = document.getElementById("reportAdminTagsAndRoles").innerHTML;
    document.getElementById("update-report-admin-tagsandroles").innerHTML = reportAdminTagsAndRolesHTML;
    reportAdminTagsHTML = document.getElementById("reportAdminTags").innerHTML;
    document.getElementById("update-report-admin-tags").innerHTML = reportAdminTagsHTML;
    document.getElementById("colorShow").innerHTML = "<button id=\"show\">Palette</button>";
});

//get Access Level String 
function getAccess(number){
    if (number == 1) return "Read";
    if (number == 2) return "Read/Run";
    if (number == 3) return "Read/Run/Share";
    return "Data error!";
}

//get Email String 
function getEmail(number){
    if (number == 1) return "Attachment";
    if (number == 2) return "Link";
    if (number == 3) return "No";
    return "N/A";
}

//Report Admin Page  (### Tags and Roles)
function getTagsAndRoles(){
    if (!window.reportTagsAndRolesPage) {
        var myCols = [{title: "Tags",dataField: "tags",sort: "r.rtag_name",filtration: {name: "r.rtag_name",alias: "Tags"}
	       	 }, {title: "Roles",dataField: "roles",sort: "ro.role_name",filtration: {name: "ro.role_name",alias: "Roles"}}
//	         }, {title: "Access Level",dataField: function(o){return getAccess(o.accessLevel);},sort: "rr.access_level"
//	         }, {title: "Email",dataField: function(o){return getEmail(o.email);},sort: "rr.send_email"}
		];
        if (adminTabInit.report != "none") {
            myCols.push({title: "Edit",dataField: function(o){return "<img src=\"include/images/Edit-icon-s.png\"  onclick=\"upReportTagsAndRoles(" + o.id + ");\"/>";}
	            }, {title: "Delete",dataField: function(o){return "<img src=\"include/images/reject16.png\" onclick=\"invoke('\delReportTagsAndRoles(" + o.id + "\)');\"/>";}
	        });
        }
        
        reportTagsAndRolesPage = new SANINCO.Page('__report_tagsAndroles', "reportTagsAndRolesPage", {
            sortingField: "rr.created_timestamp",
            sortingDirection: "desc",
            vo: "viewReportAdminVO",
            totalPageUri: actionUri.getReportAdminTagsAndRolesTotalPageNoUri,
            dataUri: actionUri.searchReportAdminTagsAndRolesUri,
            paginationDiv: "__report_tagsAndroles_page",
            recPerArray: [10, 15, 20, 30, 40, 50, 80, 100],
            cols: myCols,
            scrollTop: true
        });
    }
    
    reportTagsAndRolesPage.addSuccessEvent(function(data){
        tagsAndRolesDataString = data.data;
    });
    
    filter1 = new SANINCO.Filter();
    filter1.addEditeEvent(function(){
        reportTagsAndRolesPage.start();
    });
    filter1.add('r.rtag_name', 'String');
    filter1.add('ro.role_name', 'String');
    reportTagsAndRolesPage.setFilter(filter1);
    
    reportTagsAndRolesPage.start();
}

//delete an Report Admin Tags And Roles of start date and end date 
function delReportTagsAndRoles(o){
    YAHOO.ccm.container.delReportAdminTags.hide();
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
            reportTagsAndRolesPage.reload();
        },
        failure: showError
    };
    
    showLoadingModalLayer();
    YAHOO.util.Connect.asyncRequest('POST', actionUri.delReportTagsAndRolesUri + "?rtagRoleId=" + o, callback);
}

// Springs founds Tag and Role the prompt frame  
function newTagsAndRoles(){
	document.getElementById('__viewReportAdminVO_rtagId').options[0].selected = true;
//	document.getElementById('__access_level').options[0].selected = true;
	document.getElementById('__viewReportAdminVO_roleId').options[0].selected = true;
//	document.getElementById('__email').options[0].selected = true;
    document.getElementById("_save_button_two").onclick = judgementData;
    document.getElementById("_restore_button_two").onclick = newTagsAndRoles;
    document.getElementById("_cancel_button_two").onclick = function(){
        YAHOO.ccm.container.updateReportAdminTagsAndRoles.cancel();
    };
    YAHOO.ccm.container.updateReportAdminTagsAndRoles.show();
}

//Determine whether the data exists
function judgementData(){
    var callback = {
        success: function(o){
            if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
				window.location.reload();
				return;
			}
			var data = eval("("+o.responseText+")");
			if(data.data != true){
				alert("The same data already exists.");
				return;
			}
			addTagsAndRoles();
        },
        failure: showError
    };
    
    var previousPostedData = composePostTwoDataFromUi();
    YAHOO.util.Connect.asyncRequest('POST', actionUri.reportAdminTagsAndRolesJudgementDataUri, callback, previousPostedData);
}

//add Tags and Roles
function addTagsAndRoles(){
    YAHOO.ccm.container.updateReportAdminTagsAndRoles.hide();
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
            reportTagsAndRolesPage.start();
        },
        failure: showError
    };
    
    var previousPostedData = composePostTwoDataFromUi();
    showLoadingModalLayer();
    YAHOO.util.Connect.asyncRequest('POST', actionUri.addReportAdminTagsAndRolesUri, callback, previousPostedData);
}

//commit data
function composePostTwoDataFromUi(){
//    var postData = "viewReportAdminVO.accessLevel=" + $('#__access_level').val()
//	  var postData =  "&viewReportAdminVO.email=" + $('#__email').val()
	  var postData =  "viewReportAdminVO.roleId=" + $('#__viewReportAdminVO_roleId').val()
    	+ "&viewReportAdminVO.rtagId=" + $('#__viewReportAdminVO_rtagId').val();
    return postData;
}

function initializationSelect(){
    document.getElementById('__viewReportAdminVO_rtagId').options[0].selected = true;
    document.getElementById('__viewReportAdminVO_roleId').options[0].selected = true;
//    document.getElementById('__access_level').options[0].selected = true;
//    document.getElementById('__email').options[0].selected = true;
}

// To the select evaluation  
function select(name, id, bo){
    var rowNows = document.getElementById(id).options;
    for (var i = 0; i < rowNows.length; i++) {
        var rowNow = rowNows[i];
        if (bo == "name") {
            if (name == rowNow.innerHTML) {
                rowNow.selected = true;
            }
        }
        if (bo == "id") {
            if (name == rowNow.value) {
                rowNow.selected = true;
            }
        }
    }
}

// The form returns obviously  
function upReportTagsAndRoles(id){
    for (var i = 0; i < tagsAndRolesDataString.length; i++) {
        var row = tagsAndRolesDataString[i];
        if (row.id == id) {
            select(row.tags, "__viewReportAdminVO_rtagId", "name");
            select(row.roles, "__viewReportAdminVO_roleId", "name");
//            select(row.accessLevel, "__access_level", "id");
//            select(row.email, "__email", "id");
        }
    }
    
    document.getElementById("_save_button_two").onclick = function(){
        updateTagsAndRoles(id);
    };
    document.getElementById("_restore_button_two").onclick = function(){
        upReportTagsAndRoles(id);
    };
    document.getElementById("_cancel_button_two").onclick = function(){
        YAHOO.ccm.container.updateReportAdminTagsAndRoles.cancel();
    };
    
    YAHOO.ccm.container.updateReportAdminTagsAndRoles.show();
}

//update Tags And Roles
function updateTagsAndRoles(id){
    YAHOO.ccm.container.updateReportAdminTagsAndRoles.hide();
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
            reportTagsAndRolesPage.reload();
        },
        failure: showError
    };
    
    var previousPostedData = composePostTwoDataFromUi();
    showLoadingModalLayer();
    YAHOO.util.Connect.asyncRequest('POST', actionUri.updateReportAdminTagsAndRolesUri + "?rtagRoleId=" + id, callback, previousPostedData);
}

//Report Admin Page  (### Tags)
function getTags(){
    if (typeof reportTagsPage == "undefined") {
        var myCols = [
		  {title: "Tag Name",dataField: "name",sort: "r.rtag_name",filtration: {name: "r.rtag_name",alias: "Tags"}
		},{title: "Color",dataField: function(o){
                return "<div style=\"width: 50px;height: 20px; background-color:" + o.color + " \"/>";
            },sort: "r.rtag_color"
		},{title: "# of Reports",dataField: "ofReports",sort: "count(rr.rtag_id)"
        }];
        if (adminTabInit.report != "none") {
            myCols.push({
                title: "Edit",
                dataField: function(o){
                    return "<img src=\"include/images/Edit-icon-s.png\" border=\"0\" onclick=\"upReportTags(" + o.id + ");\"/>";
                }
            }, {
                title: "Delete",
                dataField: function(o){
                    if (o.ofReports == 0) 
                        return "<img src=\"include/images/reject16.png\" border=\"0\"  onclick=\"invoke('\delReportTags(" + o.id + "\)','"+o.id+"');\"/>";
                    else 
                        return "";
                }
            });
        }
        reportTagsPage = new SANINCO.Page('__report_tags', "reportTagsPage", {
            sortingField: "r.created_timestamp",
            sortingDirection: "desc",
            vo: "viewReportAdminVO",
            totalPageUri: actionUri.getReportAdminTagsTotalPageNoUri,
            dataUri: actionUri.searchReportAdminTagsUri,
            paginationDiv: "__report_tags_page",
            recPerArray: [10, 15, 20, 30, 40, 50, 80, 100],
            scrollTop: true,
            cols: myCols
        });
		
		reportTagsPage.addCompleteEvent(function(){
			document.getElementById('__validation_tag_innetHTML').innerHTML = "";
		});
		
    }
    
    reportTagsPage.addSuccessEvent(function(data){
        dataString = data.data;
    });
    
    filter = new SANINCO.Filter();
    filter.addEditeEvent(function(){
        reportTagsPage.start();
    });
    filter.add('r.rtag_name', 'String');
    reportTagsPage.setFilter(filter);
    
    reportTagsPage.start();
}

function promptColor(o){
    prompt = o;
    addClassName(o.parentNode.parentNode, 'hightlight');
}


//delete an Report Admin Tags of start date and end date 
function delReportTags(o){
    YAHOO.ccm.container.delReportAdminTags.hide();
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
            reportTagsPage.reload();
            showReportTagsPage(o);
        },
        failure: showError
    };
    
    YAHOO.util.Connect.asyncRequest('POST', actionUri.delReportTagsUri + "?rtagId=" + o, callback);
}

function validationTagData(id){
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
				document.getElementById('__validation_tag_innetHTML').innerHTML = "You can not delete this tag now, it is used by a role.";
				return;
			}
			YAHOO.ccm.container.delReportAdminTags.show();
        },
        failure: showError
    };
    YAHOO.util.Connect.asyncRequest('POST', actionUri.validationTagsUri + "?viewReportAdminVO.rtagId=" + id, callback);
}

//Association Delete Tags
function associationDelete(id){
	YAHOO.ccm.container.delReportAdminTags.hide();
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
            reportTagsPage.reload();
            showReportTagsPage(o);
        },
        failure: showError
    };
    
    YAHOO.util.Connect.asyncRequest('POST', actionUri.associationDeleteTagsUri + "?viewReportAdminVO.rtagId=" + id, callback);
}

//Call the confirmation box 
function invoke(fun,id){
    targetFun = fun;
	document.getElementById('__validation_tag_innetHTML').innerHTML = "";
	if(id != undefined){
		validationTagData(id);
	}else{
		YAHOO.ccm.container.delReportAdminTags.show();
	}	
}

function delReportAdminTagsWindows(){
	var handleSubmit = function(){
        eval(targetFun);
    };
    var handleCancel = function(){
        this.cancel();
    };
    YAHOO.util.Dom.removeClass("delReportAdminTags", "del-reportAdminTags");
    
    YAHOO.ccm.container.delReportAdminTags = new YAHOO.widget.Dialog("delReportAdminTags", {
        width: "30em",
        fixedcenter: true,
        visible: false,
        constraintoviewport: true,
        buttons: [{text: "Submit", handler: handleSubmit
        		},{text: "Cancel",handler: handleCancel
        }]
    });
    YAHOO.ccm.container.delReportAdminTags.render();
    YAHOO.ccm.container.delReportAdminTags.hide();
}

function updateReportAdminTagsAndRolesWindows(){
	YAHOO.util.Dom.removeClass("updateReportAdminTagsAndRoles", "update-ReportAdminTagsAndRoles");
    
    YAHOO.ccm.container.updateReportAdminTagsAndRoles = new YAHOO.widget.Dialog("updateReportAdminTagsAndRoles", {
        width: "40em",
        fixedcenter: true,
        visible: false,
        constraintoviewport: true
    });
    YAHOO.ccm.container.updateReportAdminTagsAndRoles.render();
    YAHOO.ccm.container.updateReportAdminTagsAndRoles.hide();
}

function updateReportAdminTagsWindows(){
	YAHOO.util.Dom.removeClass("updateReportAdminTags", "update-ReportAdminTags");
    
    YAHOO.ccm.container.updateReportAdminTags = new YAHOO.widget.Dialog("updateReportAdminTags", {
        width: "31em",
        fixedcenter: true,
        visible: false,
        constraintoviewport: true
    });
    
    YAHOO.ccm.container.updateReportAdminTags.render();
    YAHOO.ccm.container.updateReportAdminTags.hide();
}

//get Colo rHex (#FFFFFF)
function getColorHex(){
    var color = (__color_picker_iframe).document.getElementById('yui-picker-hex').value;
    var name = $('#__tag_name').val();
    
    $('#__tag_color').val('#' + color);
    $('#__tag_name').val(name);
    
    YAHOO.ccm.container.updateReportAdminTags.show();
    YAHOO.example.colorpicker.inDialog.dialog.cancel();
    
}

// Springs founds Tag the prompt frame  
function newTag(){
	document.getElementById('__validation_tag_innetHTML').innerHTML = "";
    $('#__tag_color').val('');
    $('#__tag_name').val('');
    
    document.getElementById("_save_button").onclick = addTags;
    document.getElementById("_restore_button").onclick = newTag;
    document.getElementById("_cancel_button").onclick = function(){
        YAHOO.ccm.container.updateReportAdminTags.cancel();
    };
    
    judgeNamePasses();
    judgeColorPasses();
    
    YAHOO.ccm.container.updateReportAdminTags.show();
}

// The confirmation passes  
function judgeNamePasses(){
    removeClassName('__tag_name', 'validation-failed');
    addClassName('__tag_name', 'validation-passed');
}

// The confirmation passes  
function judgeColorPasses(){
    removeClassName('__tag_color', 'validation-failed');
    addClassName('__tag_color', 'validation-passed');
}

// The form returns obviously  
function upReportTags(id){
	document.getElementById('__validation_tag_innetHTML').innerHTML = "";
    for (var i = 0; i < dataString.length; i++) {
        var row = dataString[i];
        if (row.id == id) {
            $('#__tag_color').val(row.color);
            $('#__tag_name').val(row.name);
            break;
        }
    }
    
    document.getElementById("_save_button").onclick = function(){
        updateTags(id);
    };
    document.getElementById("_restore_button").onclick = function(){
        upReportTags(id);
    };
    document.getElementById("_cancel_button").onclick = function(){
        YAHOO.ccm.container.updateReportAdminTags.cancel();
    };
    
    judgeNamePasses();
    judgeColorPasses();
    
    YAHOO.ccm.container.updateReportAdminTags.show();
}

// Confirms color  
function judgeTagsColor(){
    var color = $('#__tag_color').val();
    if (color.length == 7 && color.match(/^#[0-9a-fA-F]+$/)) {
        judgeColorPasses();
        return true;
    }
    else {
        removeClassName('__tag_color', 'validation-passed');
        addClassName('__tag_color', 'validation-failed');
        return false;
    }
}

// Confirms name 
function judgeTagsName(){
    var name = $('#__tag_name').val();
    if (name.length > 0 && name.length <= 64 && name.match(/[^"+]/) && name.match(/^[^ ]/) && name.match(/[^ ]$/)) {
        judgeNamePasses();
        return true;
    }
    else {
        removeClassName('__tag_name', 'validation-passed');
        addClassName('__tag_name', 'validation-failed');
        return false;
    }
}

//update Tags
function updateTags(id){
    if ((!judgeTagsColor()) | (!judgeTagsName())) return;
    YAHOO.ccm.container.updateReportAdminTags.hide();
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
            reportTagsPage.reload();
        },
        failure: showError
    };
    
    var previousPostedData = composePostDataFromUi();
    showLoadingModalLayer();
    YAHOO.util.Connect.asyncRequest('POST', actionUri.updateReportAdminTagsUri + "?rtagId=" + id, callback, previousPostedData);
}

//add Tags
function addTags(){
    if ((!judgeTagsColor()) | (!judgeTagsName())) return;
    YAHOO.ccm.container.updateReportAdminTags.hide();
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
            showReportTagsPage(o);
            reportTagsPage.start();
        },
        failure: showError
    };
    
    var previousPostedData = composePostDataFromUi();
    YAHOO.util.Connect.asyncRequest('POST', actionUri.addReportAdminTagsUri, callback, previousPostedData);
}

function showReportTagsPage(o){
	if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
		window.location.reload();
		return;
	}
    var data = eval("(" + o.responseText + ")");
    var rows = data.data;
    var selectNows = document.getElementById('__viewReportAdminVO_rtagId');
    selectNows.options.length = 0;
    for (var j = 0; j < rows.length; j++) {
        var row = rows[j];
        var option = document.createElement("option");
        option.value = row.id;
        option.innerHTML = row.name;
        selectNows.appendChild(option);
    }
	CollectGarbage(); 
}

//commit data
function composePostDataFromUi(){
    var postData = "viewReportAdminVO.tagsName=" + ccmEscape($('#__tag_name').val())
		+ "&viewReportAdminVO.tagsColor=" + $('#__tag_color').val();
    return postData;
}

//create a namespace object in the example namespace:
YAHOO.namespace("example.colorpicker")

//create a new object for this module:
YAHOO.example.colorpicker.inDialog = function(){

    //Some shortcuts to use in our example:
    var Event = YAHOO.util.Event, Dom = YAHOO.util.Dom, lang = YAHOO.lang;
    
    return {
    
        //in its render event, we'll create our Color Picker instance.
        init: function(){
        
            // Instantiate the Dialog
            this.dialog = new YAHOO.widget.Dialog("yui-picker-panel", {
                width: "500px",
                close: true,
                fixedcenter: true,
                visible: false,
                constraintoviewport: true,
                buttons: [{
                    text: "Submit",
                    handler: getColorHex
                }, {
                    text: "Cancel",
                    handler: this.handleCancel
                }]
            });
            
            
            // Wire up the success and failure handlers
            this.dialog.callback = {
                success: this.handleSuccess,
                thisfailure: this.handleFailure
            };
            
            // We're all set up with our Dialog's configurations;
            // now, render the Dialog
            this.dialog.render();
            
            Event.on("show", "click", this.dialog.show, this.dialog, true);
            
        },
        
        handleSubmit: function(){},
        handleCancel: function(){
            this.cancel();
        },
        handleSuccess: function(o){},
        handleFailure: function(o){}
        
    }
    
}();

//The earliest safe moment to instantiate a Dialog (or any
//Container element is onDOMReady; we'll initialize then:
YAHOO.util.Event.onDOMReady(YAHOO.example.colorpicker.inDialog.init, YAHOO.example.colorpicker.inDialog, true);

