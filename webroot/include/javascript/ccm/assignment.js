/**
 * @author pengfei.wang
 * */
var vendorSid;
var banSid;
//Real-time loading list.....
//Paging query
function searchAssign(){
	var getAssishowFunction = function(obj){
		return function(){
            Assishow(obj.vendorId, obj.banId, obj.id, obj.AnalystId, obj.Approver1Id, obj.Approver2Id, obj.Approver3Id);
        };
	};
	var getDeletesFunction = function(obj){
		return function(){
           deletes(obj.id);
        };
	};
	
    var myCols = [{
        title: "Vendor",
        dataField: function(o){
            if (!o.vendor) {
                return "ANY";
            }
            else {
                return o.vendor;
            }
        },
        sort: "v.vendor_name",
        filtration: {
            name: "v.vendor_name",
            alias: "Vendor"
        }
    }, {
        title: "BAN",
        dataField: function(o){
            if (!o.ban) {
                return "ANY";
            }
            else {
                return o.ban;
            }
        },
        sort: "ba.account_number",
        filtration: {
            name: "ba.account_number",
            alias: "BAN"
        }
    }, {
        title: "Analyst",
        dataField: "Analyst",
		sort : "concat(analyst.first_name,blank_space(),analyst.last_name)",
		filtration : {name:"concat(analyst.first_name,blank_space(),analyst.last_name)",alias:"Analyst"}
    }, {
        title: "Approver1",
        dataField: function(o){
            if (!o.Approver1) {
                return "";
            }
            else {
                return o.Approver1;
            }
        },
		sort : "concat(approver1.first_name,blank_space(),approver1.last_name)",
		filtration : {name:"concat(approver1.first_name,blank_space(),approver1.last_name)",alias:"Approver1"}
    }, {
        title: "Approver2",
        dataField: function(o){
            if (!o.Approver2) {
                return "";
            }
            else {
                return o.Approver2;
            }
        },
		sort : "concat(approver2.first_name,blank_space(),approver2.last_name)",
		filtration : {name:"concat(approver2.first_name,blank_space(),approver2.last_name)",alias:"Approver2"}
    }, {
        title: "Approver3",
        dataField: function(o){
            if (!o.Approver3) {
                return "";
            }
            else {
                return o.Approver3;
            }
        },
		sort : "concat(approver3.first_name,blank_space(),approver3.last_name)",
		filtration : {name:"concat(approver3.first_name,blank_space(),approver3.last_name)",alias:"Approver3"}
    }]
    myCols.push({
        title: "Edit",
        dataField: function(obj){
            //return "<input type=\"button\" value=\" Edit \" onClick=\"Assishow(" + "'" + obj.vendorId + "','" + obj.banId + "','" + obj.id + "','" + obj.AnalystId + "','" + obj.Approver1Id + "','" + obj.Approver2Id + "','" + obj.Approver3Id + "'" + ");\">";
            var img = document.createElement('img');
	        img.src='include/images/Edit-icon-s.png';
            img.onclick = getAssishowFunction(obj);
            return img;
        }
    }, {
        title: "Delete",
        dataField: function(obj){
            if (obj.vendor) {
                //return "<input type=\"button\" value=\"Delete\" onclick=\"deletes('" + obj.id + "')\"/>";
                var img = document.createElement('img');
                img.src='include/images/reject16.png';
                img.onclick = getDeletesFunction(obj);
                return img;
            }
            else {
                return "";
            }
        }
    });
    page2 = new SANINCO.Page("page_1", "page2", {
        sortingField: "ba.id",
        sortingDirection: "asc",
        vo: "asvo",
        autoToTop: true,
        paginationDiv: "assignment_pageTable",
        recPerArray: [10, 20, 30, 40, 50, 70, 90, 100],
        totalPageUri: "searchAssignmentTotalNo111.action",
        dataUri: "searchAssignment111.action",
        cols: myCols
    });
    filter = new SANINCO.Filter();
    filter.addEditeEvent(function(){
        page2.start();
    });
    filter.add('v.vendor_name', 'String');
    filter.add('ba.account_number', 'String');
	filter.add('concat(analyst.first_name,blank_space(),analyst.last_name)', 'String');
	filter.add('concat(approver1.first_name,blank_space(),approver1.last_name)', 'String');
	filter.add('concat(approver2.first_name,blank_space(),approver2.last_name)', 'String');
	filter.add('concat(approver3.first_name,blank_space(),approver3.last_name)', 'String');      
	page2.setFilter(filter);
    page2.start();
}

//Loading Edit.....
function Assishow(vendor, ban, id, Analyst, Approver1, Approver2, Approver3){
    vendorSid = vendor;
    banSid = ban;
    YAHOO.util.Dom.get("assisId").value = id;//Get page transmission Id
    //-----start---// 
    var selectA1 = YAHOO.util.Dom.get("form0_searchReconcileVO_Approver1").value = Approver1;
    var selectA2 = YAHOO.util.Dom.get("form0_searchReconcileVO_Approver2").value = Approver2;
    var selectA3 = YAHOO.util.Dom.get("form0_searchReconcileVO_Approver3").value = Approver3;
    //----end---//
    document.getElementById("div").style.display = "";//Click Add button, will this div
    document.getElementById("editbutton").style.display = "";//Click Add button, will this div
    document.getElementById("addbutton").style.display = "none";//Click Add button, will this div
}

//Bind the select method. Realizing
function jsSelectIsExitItem(objSelect, objItemValue){
    var ops = objSelect.options;
    for (var i = 0; i < ops.length; i++) {
        if (ops[i].innerHTML == objItemValue) {
            ops[i].selected = true;
            break;
        }
    }
    
}

//Delete a Assignment.....
function deletes(id){
    if (!confirm("Do You Delete This Assignment?")) {
        return;
    }
    var callback = {
        success: deleteAssignment,
        failure: showError
    };
    var deleteAssignmentPramer = "deleteId=" + id;
    YAHOO.util.Connect.asyncRequest("POST", "deleteAssignment.action", "", deleteAssignmentPramer);
    searchAssign();
}

//After deletion callback
function deleteAssignment(o){
    if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
        window.location.reload();
        return;
    }
    var data = eval("(" + o.responseText + ")");
    if (data.error) {
        alert("Delete Assignment Error:" + data.error);
        return;
    }
}

function preEdit(){
    var vendorId = YAHOO.util.Dom.get("form0_searchReconcileVO_vendorId").value;
    if (vendorId == "all") {
        alert("The editor, Vendor can't choose All");
        return;
    }
    var banId = YAHOO.util.Dom.get("form0_searchReconcileVO_banId").value;
    var callback = {
        success: preEditcallback,
        failure: showError
    };
    var addAssignmentPramer = "vendorId=" + vendorId + "&" + "banId=" + banId;
    YAHOO.util.Connect.asyncRequest("POST", "getNumberOfAssignmentByVendorIdAndBanId.action", callback, addAssignmentPramer);
}

function preEditcallback(o){
    var data = eval("(" + o.responseText + ")");
    if (data >= 1) {
        alert('Vendor and ban already exists!');
        return;
    }
    else {
        adds();
    }
}

function preEditAssignment(){
    var vendorId1 = YAHOO.util.Dom.get("form0_searchReconcileVO_vendorId").value;
    if (vendorId1 == "all") {
        alert("The editor, Vendor can't choose All");
        return;
    }
    var banId1 = YAHOO.util.Dom.get("form0_searchReconcileVO_banId").value;
    var callback1 = {
        success: preEditcallback1,
        failure: showError
    };
    if ((vendorId1 == "" && banId1 == "") || (vendorId1 == vendorSid && banId1 == banSid)) {
        edits();
    }
    else {
        var addAssignmentPramer1 = "vendorId=" + vendorId1 + "&" + "banId=" + banId1;
        YAHOO.util.Connect.asyncRequest("POST", "getNumberOfAssignmentByVendorIdAndBanId.action", callback1, addAssignmentPramer1);
    }
}

function preEditcallback1(o){
    var data = eval("(" + o.responseText + ")");
    if (data >= 1) {
        alert('Vendor and ban already exists!');
        return;
    }
    edits();
}

//Edit a Assignment.....
function edits(){
    var anAssignmentId = YAHOO.util.Dom.get("assisId").value;
	var apro3=YAHOO.util.Dom.get("form0_searchReconcileVO_Approver3").value;
	if(apro3=="all"){
		 alert("The editor, Approver3 can't choose DeselectAll");
        return;
	}
    var approver1Id = YAHOO.util.Dom.get("form0_searchReconcileVO_Approver1").value;
    var approver2Id = YAHOO.util.Dom.get("form0_searchReconcileVO_Approver2").value;
    var approver3Id = YAHOO.util.Dom.get("form0_searchReconcileVO_Approver3").value;
    var callback = {
        success: ssignment,
        failure: showError
    };
    var addAssignmentPramer = "approver1Id=" + approver1Id + "&" + "approver2Id=" + approver2Id + "&" + "approver3Id=" + approver3Id + "&" + "anAssignmentId=" + anAssignmentId;
    YAHOO.util.Connect.asyncRequest("POST", "editAssignment.action", "", addAssignmentPramer);
    //window.top.location.reload();
    YAHOO.util.Dom.get("form0_searchReconcileVO_Approver1").value = "all";
    YAHOO.util.Dom.get("form0_searchReconcileVO_Approver2").value = "all";
    YAHOO.util.Dom.get("form0_searchReconcileVO_Approver3").value = "all";
	 document.getElementById("div").style.display = "none";
    searchAssign();
}


//After Add callback
function ssignment(o){
    if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
        window.location.reload();
        return;
    }
    var data = eval("(" + o.responseText + ")");
    if (data.error) {
        alert(" Assignment Error:" + data.error);
        return;
    }
}
