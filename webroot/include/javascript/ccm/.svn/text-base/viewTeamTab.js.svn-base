var reportAdminTagsHTML = "";
YAHOO.util.Event.onDOMReady(function(){
  getTeam();
  reportAdminTagsHTML = document.getElementById("adminTeam").innerHTML;
  document.getElementById("update-report-admin-tags1").innerHTML = reportAdminTagsHTML;
});

function getTeam(){
    
        var myCols = [
		{title: "From User",dataField: "fromName",sort: "u1.first_name"
		},{title: "To User",dataField: "toName",sort: "u2.first_name"
		},{title: "Edit",
                dataField: function(o){
                    return "<img src=\"include/images/Edit-icon-s.png\" border=\"0\" onclick=\"upTeam(" + o.id +","+o.fromId+","+o.toId+ ");\"/>";
                }
        }, {title: "Delete",
                dataField: function(o){
                        return "<img src=\"include/images/reject16.png\" border=\"0\"  onclick=\"delTeam(" + o.id + ");\"/>";
                }
        }];
        
        teamPage = new SANINCO.Page('__report_tags1', "teamPage", {
            sortingField: "v.created_timestamp",
            sortingDirection: "desc",
            vo: "viewReportAdminVO",
            totalPageUri: "getTeamTotalPageNo.action",
            dataUri: "searchTeams.action",
            paginationDiv: "__report_tags_page1",
            recPerArray: [10, 15, 20, 30, 40, 50, 80, 100],
            scrollTop: true,
            cols: myCols
        });
	

    
    teamPage.addSuccessEvent(function(data){
        dataString = data.data;
    });
    
 
    teamPage.start();
}

function delTeam(id){
	if(!confirm("Please confirm to delete.")) return;
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
            teamPage.reload();
        },
        failure: showError
    };
    
    YAHOO.util.Connect.asyncRequest('POST', 'delTeam.action' + "?rtagRoleId=" + id, callback);
}

function upTeam(id,fromId,toId){
	
   $("#_fromuser").val(fromId);
   $("#_touser").val(toId);
   document.getElementById("_save_button1").onclick = function(){
        updateTeams(id);
    };
    document.getElementById("_cancel_button1").onclick = function(){
        YAHOO.ccm.container.updateTeams.cancel();
    };
    YAHOO.ccm.container.updateTeams.show();
}

function newTeam(){
	$("#_fromuser")[0].selectedIndex = 0;
	$("#_touser")[0].selectedIndex = 0;
   document.getElementById("_save_button1").onclick = function(){
        addTeams();
    };
    document.getElementById("_cancel_button1").onclick = function(){
        YAHOO.ccm.container.updateTeams.cancel();
    };
    YAHOO.ccm.container.updateTeams.show();
}

function updateTeams(id){
	
	var callback = {
        success: function(o){
			
			var data = eval("("+o.responseText+")");
			if(data.msg){
				alert(data.msg);
				return;
			}
			if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
				window.location.reload();
				return;
			}
			if(data.error){
				hideLoadingImg();
				alert("Error: " + data.error);
				return;
			}
            teamPage.reload();
        },
        failure: showError
    };
    var fromId = $("#_fromuser").val();
    var toId=$("#_touser").val();
    if(fromId == toId){
    	alert("From user and To user must be different! ");
    	return;
    }
    YAHOO.ccm.container.updateTeams.hide();
    YAHOO.util.Connect.asyncRequest('POST','updateTeam.action'+"?teamId="+id+"&fromId="+fromId+"&toId="+toId, callback);
}

function updateTeamWindow(){
	YAHOO.util.Dom.removeClass("updateTeams", "update-ReportAdminTags");
    
    YAHOO.ccm.container.updateTeams = new YAHOO.widget.Dialog("updateTeams", {
        width: "31em",
        fixedcenter: true,
        visible: false,
        constraintoviewport: true
    });
    
    YAHOO.ccm.container.updateTeams.render();
}

function addTeams(){
	var callback = {
        success: function(o){
		var data = eval("("+o.responseText+")");
			if(data.msg){
				alert(data.msg);
				return;
			}
			
			if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
				window.location.reload();
				return;
			}
			if(data.error){
				hideLoadingImg();
				alert("Error: " + data.error);
				return;
			}
            teamPage.reload();
        },
        failure: showError
    };
    var fromId = $("#_fromuser").val();
    var toId=$("#_touser").val();
    if(fromId == toId){
    	alert("From user and To user must be different! ");
    	return;
    }
    YAHOO.ccm.container.updateTeams.hide();
    YAHOO.util.Connect.asyncRequest('POST','addTeam.action'+"?fromId="+fromId+"&toId="+toId, callback);
}
