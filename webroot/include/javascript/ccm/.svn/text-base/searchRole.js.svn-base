//@author chao.Liu (Optimization of complete by xinyu.Liu)
var id = "";
var rname = "";
var isSave = "";
var deleteRoleAction = "deleteRoleInfo.action";
var searchFunctionAction = "searchFunction.action";
var saveNameAction = "saveName.action";
var addFunOrDelAction = "addFunOrDel.action";
YAHOO.util.Event.onDOMReady(function(){
	searchRole();
});
function searchRole(){
	var myCols = [{title : "Role Name",dataField: function(row){
				             	 return '<a href="#" onclick="showInfo(\''+row.id+'\',\''+row.rname+'\')">' + row.rname + '</a>';
				         	 },sort : "role_name",filtration : {name:"role_name",alias:"Role Name"}
				 }]
	
	myCols.push(
				{title : "Edit",width:"25%",dataField: function(row){
			              		return '<img id="fn'+row.id+'Img" src="include/images/Edit-icon-s.png" onclick="eidtRole(\''+row.id+'\',\''+row.rname+'\')" />';
			        		  }
			        
			   },{title : "Delete",width:"30%",dataField: function(row){
			             		 return '<img src="include/images/reject16.png" onclick="deleteRole(\''+row.id+'\')" />';
			         		  }
		       }
	);
	
	rolePage = new SANINCO.Page("roleListDiv","rolePage",{
      sortingField : "role_name",
      sortingDirection : "asc",
      vo : "rvo",
      totalPageUri : "doSearchRoleTotalNO.action",
      dataUri : "doSearchRole.action",
      recordText: "",
      scrollTop: true,
      cols : myCols
  });
	
    filter = new SANINCO.Filter();
    filter.addEditeEvent(function(){rolePage.start()});
    filter.add('role_name', 'String');
    rolePage.setFilter(filter);
 	rolePage.start();
}

//Show Role Detail Info
function showInfo(id,rname){
	disable();
	isSave = "&rvo.isSave=Save";
	document.getElementById("rname").value = rname;
	searchFunction(id);
}
$(function(){
	selectMoveOption();
});
//Delete One Role
function deleteRole(id){
	if(!confirm("Are you sure you want to delete this role?")){return;}
	var callback = {
		success: deleteRoleIsSuccess,
		failure: showError
	};
	var deleteRolePramer = "rvo.id="+id+"";
	YAHOO.util.Connect.asyncRequest('POST' , deleteRoleAction , callback ,deleteRolePramer) 
}

function deleteRoleIsSuccess(o){
	if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
		window.location.reload();
		return;
	}
	var data = eval("("+o.responseText+")");
	if(data.error){
		alert("Delete Role Error:" + data.error);
		return;
	}
	disable();
	searchRole();
}

// Eidt Role
function eidtRole(rid,eidtName){
	disable();
	able(rid,eidtName);
	isSave = "&rvo.isSave=Save";
	searchFunction(id);
}

function newRole(){
	disable();
	document.getElementById("rname").disabled = "";
	document.getElementById("btnName").disabled = "";
	isSave = "";
}

function saveName(){
	rname = document.getElementById("rname").value;
	if(rname.indexOf("\\")>-1 || rname.indexOf("\"")>-1 || rname.indexOf("'")>-1){
		alert("Role name can not contain ' or \" or \\");
		return false;
	}
	var callback = {
		success: saveNameIsSuccess,
		failure: showError
	};
	var saveNamePramer = "rvo.id="+id+"&rvo.rname="+ccmEscape(rname)+isSave;
	YAHOO.util.Connect.asyncRequest('POST' , saveNameAction , callback ,saveNamePramer) 
}

function saveNameIsSuccess(o){
	if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
		window.location.reload();
		return;
	}
	var data = eval("("+o.responseText+")");
	if(data.error){
		alert(data.error);
		return;
	}
	searchRole();
	if(isSave == ""){
		able(data.id,rname);
		searchFunction(id);
		isSave = "&rvo.isSave=Save";
	}else{
		disable();
	}
}

// Right is Able Or Disable
function able(rid,name){
	document.getElementById("rname").disabled = false;
	document.getElementById("btnName").disabled = false;
	document.getElementById("s1").disabled = false;
	document.getElementById("s2").disabled = false;
	document.getElementById("add_all").disabled = false;
	document.getElementById("remove_all").disabled = false;
	document.getElementById("add").disabled = false;
	document.getElementById("remove").disabled = false;
	
	document.getElementById("rname").value = name;
	id=rid;
	rname=name;
}

function disable(){
	document.getElementById("rname").disabled = true;
	document.getElementById("s1").disabled = true;
	document.getElementById("s2").disabled = true;
	document.getElementById("btnName").disabled = true;
	document.getElementById("add_all").disabled = true;
	document.getElementById("remove_all").disabled = true;
	document.getElementById("add").disabled = true;
	document.getElementById("remove").disabled = true;
	document.getElementById("rname").value = "";

	document.getElementById("s1").options.length = 0;
	document.getElementById("s2").options.length = 0;
	
	id="";
	rname="";
	isSave = "";
}

//Copy Left Value To Right 
function selectMoveOption(){
	$("#add").click(
		function (){
			var $options = $("#s1 option:selected");
			optionMenu("Add",$options);
		}			
	);
	$("#add_all").click(
		function (){
			var $options = $("#s1 option");
			optionMenu("Add",$options);
		}			
	);
	$("#s1").dblclick(
		function (){
			var $options = $("option:selected",this);
			optionMenu("Add",$options);
		}			
	);
	$("#remove").click(
		function (){
			var $options = $("#s2 option:selected");
			optionMenu("Del",$options);
		}			
	);
	$("#remove_all").click(
		function (){
			var $options = $("#s2 option");
			optionMenu("Del",$options);
		}			
	);
	$("#s2").dblclick(
		function (){
			var $options = $("option:selected",this);
			optionMenu("Del",$options);
		}			
	);
}

//Make Function Id Array
function optionMenu(menu,o){
	var fidArray = "";
	for(var i=0;i<o.length;i++){
		fidArray += o[i].value;
		if((i+1)<o.length){
			fidArray += ",";
		}
	}
	if(fidArray == ""){return;}
	
	addFunOrDel(menu,fidArray);
}

function searchFunction(id){
	var callback = {
		success: searchFunctionIsSuccess,
		failure: showError
	};
	var searchFunctionPramer = "rvo.id="+id+isSave;
	YAHOO.util.Connect.asyncRequest('POST' , searchFunctionAction , callback ,searchFunctionPramer) 
}

function searchFunctionIsSuccess(o){
	if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
		window.location.reload();
		return;
	}
	document.getElementById("s1").options.length = 0;
	document.getElementById("s2").options.length = 0;
	
	var data = eval("("+o.responseText+")");
	if(data.error){
		alert("Search Function Error:" + data.error);
		return;
	}
	var s1data = data.s1;
	var $s1 = $("#s1");
	var $s2 = $("#s2");
	for(var i=0;i<s1data.length;i++){
		$s1.append('<option value="'+s1data[i].key+'">'+s1data[i].value+'</option>');
	}
	var s2data = data.s2;
	for(var i=0;i<s2data.length;i++){
		$s2.append('<option value="'+s2data[i].key+'">'+s2data[i].value+'</option>');
	}
}

function addFunOrDel(menu,fidArray){
	var callback = {
		success: addFunOrDelIsSuccess,
		failure: showError
	};
	showLoadingModalLayer();
	var addFunOrDelPramer = "rvo.id="+id+"&rvo.fidArray="+fidArray+"&rvo.isAddOrDel="+menu;
	YAHOO.util.Connect.asyncRequest('POST' , addFunOrDelAction , callback ,addFunOrDelPramer) 
}

function addFunOrDelIsSuccess(o){
	if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
		window.location.reload();
		return;
	}
	var data = eval("("+o.responseText+")");
	if(data.error){
		alert("AddFunOrDel Error:" + data.error);
		hideLoadingModalLayer();
		return;
	}
	$("#fn"+id+"Img").click();
	hideLoadingModalLayer();
} 