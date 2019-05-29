
var reportAdminTagsHTML2="";

YAHOO.util.Event.onDOMReady(function(){
  getSecurity();
  
  reportAdminTagsHTML2 = document.getElementById("AdminSecuritIp").innerHTML;
  document.getElementById("update-report-admin-tags2").innerHTML = reportAdminTagsHTML2;

});


function showLoadingImg(){
	if(loadingImgDiv) loadingImgDiv.style.display="block";
}

function hideLoadingImg(){
	if(loadingImgDiv) loadingImgDiv.style.display="none";
}


function getSecurity(){
    var myCols = [
          		{title: "IP",dataField: "ip",sort: "si.ip_a",filtration : {name:"concat(si.ip_a,ifnull(si.ip_b,blank_space()))",alias:"IP"}
          		},{title: "User Name",dataField: "username",sort: "u1.first_name",filtration : {name:"concat(u1.first_name,blank_space(),u1.last_name)",alias:"User Name"}
          		},{title: "Start Date",dataField: "startdate",sort: "si.start_date",filtration : {name:"si.start_date",alias:"Start Date"}
          		},{title: "End Date",dataField: "enddate",sort: "si.end_date",filtration : {name:"si.end_date",alias:"End Date"}
          		},{title: "Active Status",dataField: "activeFlag",sort: "si.active_flag",filtration : {name:"si.active_flag",alias:"Active"}
          		},{title: "Modified Timestamp",dataField: "modified_timestamp",sort: "si.modified_timestamp",filtration : {name:"si.modified_timestamp",alias:"ModifiedTimestamp"}
          		},{title: "Modified By",dataField: "modified_by",sort: "u2.first_name",filtration : {name:"concat(u2.first_name,blank_space(),u2.last_name)",alias:"Modified By"}
          		},{title: "Edit",
                          dataField: function(o){
                              return "<img src=\"include/images/Edit-icon-s.png\" border=\"0\" onclick=\"upSecurity(" + o.id +",'"+o.ipa+"','"+o.ipb+"','"+o.activeFlag+"','"+o.iprangeflag+"','"+o.startdate+ "','"+o.enddate+ "','"+o.username+"');\"/>";
                          }
                  }, {title: "Delete",
                          dataField: function(o){
                                  return "<img src=\"include/images/reject16.png\" border=\"0\"  onclick=\"delSecurityIp(" + o.id + ");\"/>";
                          }
                  }];
                  
                  teamIpPage = new SANINCO.Page('__report_tags2', "teamIpPage", {
                	  sortingField: "si.ip_range_flag desc,si.created_timestamp",
                      sortingDirection: "desc",
                	  vo:"viewSecurityIpVo",
                      totalPageUri: "getSeacurityTotalPageNo.action",
                      dataUri: "searchSecurityIp.action",
                      paginationDiv: "__report_tags_page2",
                      recPerArray: [10, 15, 20, 30, 40, 50, 80, 100],
                      scrollTop: true,
                      cols: myCols
                  });
                  teamIpPage.addTotalSuccessEvent(function(data){
          			if(data.totalResultNo <= 0 || data.error){
          				document.getElementById('newSecurityIpBlock').style.display = "none";
          
          				
          			}else{
          				document.getElementById('newSecurityIpBlock').style.display = "block";
 
          			}
          		});
                  
                 filter1 = new SANINCO.Filter();
              	filter1.addEditeEvent(function(){teamIpPage.start();});
              	filter1.add("concat(si.ip_a,ifnull(si.ip_b,blank_space()))", "String");
              	filter1.add("concat(u1.first_name,blank_space(),u1.last_name)", "String");
              	filter1.add('si.start_date', 'String');
              	filter1.add('si.end_date', 'String');
              	filter1.add('si.active_flag', 'String');
              	filter1.add('si.modified_timestamp', 'String');
              	filter1.add('concat(u2.first_name,blank_space(),u2.last_name)', 'String');
              	teamIpPage.setFilter(filter1);

                  teamIpPage.start();
          
}



function delSecurityIp(id){
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
			teamIpPage.reload();
        },
        failure: showError
    };
    
    YAHOO.util.Connect.asyncRequest('POST', 'delSecurityIp.action' + "?viewSecurityIpVo.securityId=" + id, callback);
}



function upSecurity(id,textIpA,textIpB,activeFlag,ipRangeFlag,textStartDate,textEndDate,textUserName){
		
	   $("#textIpA").attr('disabled','true');
	   $("#textIpB").attr('disabled','true');
	   $("#ipRangeRadio").attr('disabled','true');
	   $("#ipRadio").attr('disabled','true');
	   $("#textIpA").val(textIpA);
	   $("#textIpB").val(textIpB);
	   $("#textStartDate").val(textStartDate);
	   $("#textEndDate").val(textEndDate);
	   $("#textActiveFlag option[text='"+activeFlag+"']").attr("selected", true);  
	   
	   if(ipRangeFlag == "Y"){
		   $('#textIpTo').show();
		   $('#textIpFrom').html('IP From');
		   $('#ipRangeRadio').attr("checked",'checked');
	   }else{
		   $('#textIpTo').hide();
		   $('#textIpFrom').html('IP');
		   $('#ipRadio').attr("checked",'checked');
	   }
	   
//	   $("#textsecuirtyuser").text(textUserName);
	   if(''!=$.trim(textUserName)){
		   $("#textsecuirtyuser option[text='"+textUserName+"']").attr("selected", true);   
	   }else{
		   $("#textsecuirtyuser")[0].selectedIndex=0;
	   }
	   removeClassName( document.getElementById("textStartDate"),'validation-failed');
	   removeClassName( document.getElementById("textEndDate"),'validation-failed');
	   document.getElementById("_save_button2").onclick = function(){
		   updateSecurity(id);
	    };
	    document.getElementById("_cancel_button2").onclick = function(){
	        YAHOO.ccm.container.updateSecurityIp.cancel();
	    };
	    YAHOO.ccm.container.updateSecurityIp.show();
	    
	}

function validateFieldsDup(){
	var formatValid=true;
	var textIpA= $.trim($("#textIpA").val());
	var textIpB= $.trim($("#textIpB").val());
	var textStartDate=$.trim($("#textStartDate").val());
	var textEndDate=$.trim($("#textEndDate").val());
	var textUserName=$.trim($("#textsecuirtyuser").val());
	if(!/^\d{1,3}[\.]\d{1,3}[\.]\d{1,3}[\.]\d{1,3}$/.test(textIpA)){
		if(textIpA == ""){
			if($('#ipRangeRadio').attr("checked"))
				alert("IP can't be empty!");
			else
				alert("IP From can't be empty!");
		}else{
			alert("IP Error!");
			
		}
		formatValid =false;
		return formatValid;
	}
	
	if($('#ipRangeRadio').attr("checked") && !/^\d{1,3}[\.]\d{1,3}[\.]\d{1,3}[\.]\d{1,3}$/.test(textIpB)){
		if(textIpB == ""){
			alert("IP To can't be empty!");
		}else{
			alert("IP Error!");
			
		}
		formatValid =false;
		return formatValid;
	}
	if($('#ipRangeRadio').attr("checked")){
		var strfrom= new Array(); 
		var strto= new Array();
		strfrom =textIpA.split(".");
		strto =textIpB.split(".");
		if(strfrom.length == strto.length){
			for (i=0;i<strfrom.length ;i++ ){
				if(strfrom[i]>strto[i]){
					alert("IP From shall be less than IP To. Please confirm the IP Address is correct.");
			    	formatValid =false;
					return formatValid;
				}else if(strfrom[i]==strto[i]){
					continue;
				}
				break;
			}
		}
	}
	if(''!=textStartDate && !isValidDate(textStartDate) && ''!=textEndDate && !isValidDate(textEndDate)){
		alert('Start Date and End Date error!');
		formatValid =false;
		return formatValid;
	}
	if(''!=textStartDate && !isValidDate(textStartDate)){
		alert('Start Date error!');
		formatValid =false;
		return formatValid;
	}
	
	if(''!=textEndDate && !isValidDate(textEndDate)){
		alert('End Date error!');
		formatValid =false;
		return formatValid;
	}
	if(''!=textStartDate && ''!=textEndDate){
	    if(textStartDate > textEndDate){
	    	alert("The start date must be less than or equal end date!");
	    	formatValid=false;
	    	return formatValid;
	    }
	}

    if(''!=textEndDate){
		
		var d = new Date();
		var currentDate = d.getFullYear()+"/"+((d.getMonth()+1)<10?("0"+(d.getMonth()+1)):(d.getMonth()+1))+"/"+(d.getDate()<10?("0"+d.getDate()):d.getDate());
		var endDate =textEndDate.replace(/-/g,"/");
		
		if(currentDate > endDate){
			alert('The end date should not be earlier than current date!');
			formatValid =false;
			return formatValid;
		}
	}
//	if(textIp.substring(0,3)=="255"){
//		formatValid=false;
//		alert("The IP should not start with 255 and above." );
//		return formatValid;
//	}
	
//	var strs= new Array();
//	strs=textIp.split(".");
//	for (i=0;i<strs.length ;i++ ) 
//	{ 
//		if(i == 0 && (strs[i].substring(0,1)=="0" || strs[i] == "127")){
//			formatValid=false;
//			alert("The IP should not start with 0 , 127 !");
//			return formatValid;
//		}
//		if(Number(strs[i]) < 0  || Number(strs[i]) > 254){
//			formatValid=false;
//			alert("The end of IP must be less than or equal 254!");
//			return formatValid;
//		}
//	} 
	
//	if(textIp.substring(0,1)=="0"){
//		formatValid=false;
//		alert("The IP should not start with 0");
//		return formatValid;
//	}
	
	return formatValid;
}

function isValidDate(date){
    var result = date.match(/^(\d{4})(-|\/)(\d{1,2})\2(\d{1,2})$/);

    if (result == null)
        return false;
    var d = new Date(result[1], result[3] - 1, result[4]);
    return ( (d.getMonth() + 1) == result[3] && d.getDate() == result[4]);

}

function newSecurityIp(){
	$("#textIpA").attr('disabled','');
	$("#textIpB").attr('disabled','');
	$("#ipRangeRadio").attr('disabled','');
	$("#ipRadio").attr('disabled','');
	$("#textIpA").val('');
	$("#textIpB").val('');
	$("#textStartDate").val('');
	$("#textEndDate").val('');
	$("#textsecuirtyuser")[0].selectedIndex=0;
	$("#textActiveFlag")[0].selectedIndex=0;
	$('#textIpTo').show();
    $('#textIpFrom').html('IP From');
    $('#ipRangeRadio').attr("checked",'checked');
    
	removeClassName( document.getElementById("textStartDate"),'validation-failed');
	removeClassName( document.getElementById("textEndDate"),'validation-failed');
   document.getElementById("_save_button2").onclick = function(){
        addSecurityIp();
    };
    document.getElementById("_cancel_button2").onclick = function(){
        YAHOO.ccm.container.updateSecurityIp.cancel();
    };
    YAHOO.ccm.container.updateSecurityIp.show();
}


function updateSecurity(id){
	
	var callback = {
        success: function(o){
			
			var data = eval("("+o.responseText+")");
			if(data.msg){
				if(data.securityIp){
					if(confirm(data.msg)){
						reactiveSecurity(data.securityIp);
					}
				}else{
					alert(data.msg);
				}
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
			teamIpPage.reload();
        },
        failure: showError
    };
	var textIpA= $.trim($("#textIpA").val());
	var textIpB= $.trim($("#textIpB").val());
	var textStartDate=$.trim($("#textStartDate").val());
	var textEndDate=$.trim($("#textEndDate").val());
	var textUserId=$.trim($("#textsecuirtyuser").val());
	var activeFlag=$.trim($("#textActiveFlag").val());
	var ipRangeFlag=$("#ipRadio").attr("checked")?"N":"Y";
	if(!validateFieldsDup())return;
    YAHOO.ccm.container.updateSecurityIp.hide();
    YAHOO.util.Connect.asyncRequest('POST','updateSecurity.action'+"?viewSecurityIpVo.securityId="+id+"&viewSecurityIpVo.securityIpA="+textIpA+"&viewSecurityIpVo.securityIpB="+textIpB+"&viewSecurityIpVo.activeFlag="+activeFlag+"&viewSecurityIpVo.securityStartDate="+textStartDate+"&viewSecurityIpVo.securityEndDate="+textEndDate+"&viewSecurityIpVo.securityUserId="+textUserId+"&viewSecurityIpVo.ipRangeFlag="+ipRangeFlag, callback);
}

function reactiveSecurity(id){
		
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
				teamIpPage.reload();
	        },
	        failure: showError
	   };
	   YAHOO.ccm.container.updateSecurityIp.hide();
	   YAHOO.util.Connect.asyncRequest('POST','reactiveSecurity.action'+"?viewSecurityIpVo.securityId="+id,callback);
	
}

function updateSecurityIp(){
	YAHOO.util.Dom.removeClass("updateSecurityIp", "update-ReportAdminTags");
    
    YAHOO.ccm.container.updateSecurityIp = new YAHOO.widget.Dialog("updateSecurityIp", {
        width: "36em",
        fixedcenter: true,
        visible: true,
        constraintoviewport: true
    });
    YAHOO.ccm.container.updateSecurityIp.render();
    YAHOO.ccm.container.updateSecurityIp.hide();
}

function addSecurityIp(){
	var textIpA= $.trim($("#textIpA").val());
	var textIpB= $("#ipRadio").attr("checked")?'':$.trim($("#textIpB").val());
	var textStartDate=$.trim($("#textStartDate").val());
	var textEndDate=$.trim($("#textEndDate").val());
	var textUserId=$.trim($("#textsecuirtyuser").val());
	var activeFlag=$.trim($("#textActiveFlag").val());
	var ipRangeFlag=$("#ipRadio").attr("checked")?"N":"Y";
	
    if(!validateFieldsDup())return;
	var callback = {
	        success: function(o){
			var data = eval("("+o.responseText+")");
//				if(data.msg){
//					alert(data.msg);
//					return;
//				}
			
				if(data.msg && confirm(data.msg)){
						updateSecurity(data.securityIp);
//						addSecurityIpTrueOrFalse(textIp,textStartDate,textEndDate,textUserName);
						teamIpPage.reload();
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
				if(data.success && data.success == "success"){
					teamIpPage.reload();
				}
	        },
	        failure: showError
	    };
     
    YAHOO.ccm.container.updateSecurityIp.hide();
    YAHOO.util.Connect.asyncRequest('POST','addSecurityIp.action'+"?viewSecurityIpVo.securityId="+id+"&viewSecurityIpVo.securityIpA="+textIpA+"&viewSecurityIpVo.securityIpB="+textIpB+"&viewSecurityIpVo.activeFlag="+activeFlag+"&viewSecurityIpVo.securityStartDate="+textStartDate+"&viewSecurityIpVo.securityEndDate="+textEndDate+"&viewSecurityIpVo.securityUserId="+textUserId+"&viewSecurityIpVo.ipRangeFlag="+ipRangeFlag, callback);
}

//function addSecurityIpTrueOrFalse(textIpA,textStartDate,textEndDate,textUserName){
//	var callback = {
//	        success: function(o){
//			var data = eval("("+o.responseText+")");
////				if(data.msg){
////					alert(data.msg);
////					return;
////				}
//				if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
//					window.location.reload();
//					return;
//				}
//				if(data.error){
//					hideLoadingImg();
//					alert("Error: " + data.error);
//					return;
//				}
//				
//	        },
//	        failure: showError
//	    };
//
//    YAHOO.ccm.container.updateSecurityIp.hide();
//    YAHOO.util.Connect.asyncRequest('POST','addSecurityIpTrueOrFalse.action'+"?securityId="+id+"&securityIpA="+textIpA+"&securityStartDate="+textStartDate+"&securityEndDate="+textEndDate+"&securityUserName="+textUserName, callback);
//}
