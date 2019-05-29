function clearDisabled(id,number){
	if ($('#'+ id)[0].checked) {
		$(".Clear" + number).css("background","").attr("disabled","").val("");
	}else{
		$(".Clear" + number).css("background","#ccc").attr("disabled","disabled").val("");;
	}
}
function resetFormElementValue(){
	form0_searchMessageCenter_CreatedBy.setValue("all");
	cleanFormElementValue();
	clearCheckbox();
}
function resetRadioButton(){

}
function clearCheckbox(){
	$(".ClearCheckbox").removeAttr("checked");
	$(".Date").css("background","#ccc").attr("disabled","disabled").val("");
}

function startSearch() {	
	getMessageCenterList();
}
YAHOO.util.Event.onDOMReady(function(){
	 createMessageCenterPage();
	
});
function createMessageCenterPage(){
	 newPage = new SANINCO.Page("_MessageCenter_dataTable","newPage",{
		sortingField : "t.created_timestamp",
		sortingDirection : "desc",
		vo : "searchMessageCenterVO",
		pageTable : "block",
	    totalPageUri : "doGetMessageCenterSearchTotalPageNo.action",
	    dataUri : "doSearchMessageCenter.action",
		paginationDiv : "_MessageCenter_dataTable_page",
		recPerArray : [10,20,30,40,50,70,90,100],
		cols : [
				{   title : "Reference", dataField: "reference_type_name",sort : "t.reference_type_name", filtration : {name:"t.reference_type_name",alias:"Reference"}
			    },{ title : "Number",dataField:function(o){ if(o.reference_type_name=="Invoice"){return "<a href=\"javascript:mark('viewInvoiceDetails.action?invoiceId="+o.reference_id+"')\">"+o.reference_number+"</a>";} 
			    if(o.reference_type_name=="Dispute"){return "<a href=\"javascript:mark('viewDisputeDetails.action?disputeId="+o.reference_id+"','_blank')\">"+o.reference_number+"</a>";}
			    if(o.reference_type_name=="Circuit"){return "<a href=\"javascript:mark('showCircuitDetail.action?vendorCircuitId="+o.reference_id+"','_blank')\">"+o.reference_number+"</a>";}
			    if(o.reference_type_name=="Ban"){return o.reference_number}
			    if(o.reference_type_name=="Vendor"){return o.reference_number}
			    if(o.reference_type_name=="User"){return o.reference_number}
			    }			    			    
			    ,sort : "t.reference_number", filtration : {name:"t.reference_number",alias:"Number"}
			    },{ title : "Notes",dataField: function(o){return {less_sign:"<div >"+(o.msg_text).replace(/ /g, "&ensp;").replace(/\</g,"&lt;")+"</div>"}}
			    },{ title : "Attachment",dataField:function(o){
					return (o.attachment_point_id == "" ? "" : "<img  src=\"include/images/dowload55.png\" style=\"cursor: pointer; float:left\" onclick=\"SANINCO.Download(" + o.attachment_point_id + ");\"/><div style=\"padding-top: 5px;\">Download</div>");}
			    ,sort : "t.attachment_point_id"
			    },{ title : "Favorite",dataField:function(o){ if(o.favorite_flag=="Y"){return "<img id = \"message_icon_id"+o.id+"\" src=\"include/images/message-icon_54.png\"  name = "+o.favorite_flag +" onclick=\"favoriteFlagChange("+o.id+ ",this);\"/>";} if(o.favorite_flag=="N"){return"<img id = \"message_icon_id"+o.id+"\" src=\"include/images/message-icon_53.png\"  name = "+o.favorite_flag +" onclick=\"favoriteFlagChange("+o.id+ ",this);\" />";}},sort : "t.favorite_flag"
			    },{ title : "Private",dataField:function(o){ if(o.private_flag=="Y"){return "Yes";} else{return "No";}},sort : "t.private_flag"
			    },{ title : "Channel",dataField: function(o){return "<a href=\"javascript:openChannel("+o.channel_id+",'" + o.channel_name + "',"+o.channel_type_id+",'"+o.user_id+"')\">"+o.channel_name+"</a>";}, filtration : {name:"t.channel_name",alias:"Channel"},sort : "t.channel_name"
			    },{ title : "Created Time",dataField: "created_timestamp",sort : "t.created_timestamp", filtration : {name:"t.created_timestamp",alias:"createdTimestamp"}
			    }
			]
	});
	var filter = new SANINCO.Filter();
    filter.addEditeEvent(function(){newPage.start();});
    filter.add('t.created_timestamp', 'Date');
    filter.add('t.channel_name', 'String');
    filter.add('t.reference_type_name', 'String');
    filter.add('t.reference_number', 'String');
    newPage.setFilter(filter);
	//newPage.showCustomLoadingModalLayer = showCanStopLoadingModalLayer;
	//newPage.hideCustomLoadingModalLayer = hideCanStopLoadingModalLayer;
	newPage.addTotalSuccessEvent(function(data){
		document.getElementById('_MessageCenterDiv').style.display = "block";
	});
	return newPage;	               
	
}
function favoriteFlagChange(id,flag){
	var callback = {
			success: function(o){
         if(o.responseText=="success"){
		    if(flag.name=="Y"){
		    document.getElementById("message_icon_id"+id).src ="include/images/message-icon_53.png";
		    document.getElementById("message_icon_id"+id).name='N';
		    return
		    }
		    if(flag.name="N"){
		    document.getElementById("message_icon_id"+id).src ="include/images/message-icon_54.png";
		    document.getElementById("message_icon_id"+id).name='Y';
		    return
		    }
         }
			},
			failure: showError
		};

		var pdata = "messageId=" + id +"&favoriteFlag=" +flag.name;
		YAHOO.util.Connect.asyncRequest('POST' , "doUpdatMessageFavoriteFlag.action" , callback , pdata); 
	
}
function getMessageCenterList(){
	if((!judgDate())) return;
	var composePostData = composePostDataFromUi();
	newPage.myParam = composePostData;
	newPage.filter.clearAll();
	newPage.start();
}

function circuitEscape(s){
	if(typeof s == 'string'){
		s=s.replace(/\'/g,"'\'");
		return escape(s).replace(/\+/g, '%2B');
	}else{
		return s;
	}
}
function composePostDataFromUi(){
	
	var postData = "";
	if($("#Reference").val()!= "all"){
		postData += "&searchMessageCenterVO.referenceTypeId=" + $("#Reference").val();
	}
	if(form0_searchMessageCenter_CreatedBy.getValue()!= "all"){
		postData += "&searchMessageCenterVO.createdBy=" + form0_searchMessageCenter_CreatedBy.getValue();
	}
	if($.trim($("#searchMessageCenterVO_CreateDateStartsOn").val()).length>0){
		postData += "&searchMessageCenterVO.createdTimesFrom=" + $("#searchMessageCenterVO_CreateDateStartsOn").val();
	}
	if($.trim($("#searchMessageCenterVO_CreateDateDateEndsOn").val()).length>0){
		postData += "&searchMessageCenterVO.createdTimesTo=" + $("#searchMessageCenterVO_CreateDateDateEndsOn").val();
	}
	if($.trim($("#Notes_MC").val()).length>0){
		postData += "&searchMessageCenterVO.notes=" + circuitEscape($("#Notes_MC").val());
	}
	if($.trim($("#Reference_Number_").val()).length>0){
		postData += "&searchMessageCenterVO.reference_number=" + circuitEscape($("#Reference_Number_").val());
	}
	if($("#private_flag")[0].checked){
		postData += "&searchMessageCenterVO.privateFlag=" + "'Y'";
	}
    if($("#favorite_flag")[0].checked){
    	postData += "&searchMessageCenterVO.favoriteFlag=" + "'Y'";
	}
    postData = postData.substring(1, postData.length);
	return postData;
}

function downloadMessageCenterCsv(){
	var titles = newPage.getTitlesParam("titles");
	var uri =  "doDownloadMessageCenterCsv.action?"+ titles +"&"+ newPage.paramStr;
	windowOpen(uri);
}

function judgDate(){
	var nodes = YAHOO.util.Selector.query('input[cssClass=validate-date-ca]');
	var bo = true;
	var testBo = false;
	

    for(var d = 0; d<nodes.length; d++){
		
		if(!isValidDate(nodes[d].value)) {
			removeClassName(nodes[d].id,'validation-passed');
			addClassName(nodes[d].id,'validation-failed');
			bo = false;
		}else{
			if(d%2 == 0 && d+1 < nodes.length){
				if((nodes[d].value!="") && (nodes[d+1].value!="")){
					if(!correct(nodes[d].value,nodes[d+1].value)){
						removeClassName(nodes[d].id,'validation-passed');
						addClassName(nodes[d].id,'validation-failed');
						removeClassName(nodes[d+1].id,'validation-passed');
						addClassName(nodes[d+1].id,'validation-failed');
						bo = false;
					}else{
						removeClassName(nodes[d].id,'validation-failed');
						addClassName(nodes[d].id,'validation-passed');
						removeClassName(nodes[d+1].id,'validation-failed');
						addClassName(nodes[d+1].id,'validation-passed');
					}
				}
			}	
		}
		
	}
    
    $("#Notes_MC").addClass("validation-passed").removeClass("validation-failed");
    $("#Reference_Number_").addClass("validation-passed").removeClass("validation-failed");
	var req = new RegExp(/[(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\()(\))(\-)(\_)(\+)(\=)(\[)(\])(\{)(\})(\|)(\\)(\;)(\:)(\')(\")(\,)(\.)(\/)(\<)(\>)(\?)]+/);

	if(req.test($("#Notes_MC").val())){
		$("#Notes_MC").addClass("validation-failed").removeClass("validation-passed");
		testBo = true;
      }
	if(req.test($("#Reference_Number_").val())){
		$("#Reference_Number_").addClass("validation-failed").removeClass("validation-passed");
		testBo = true;
      }
	if(testBo) {
		alert("Can't fill in the special symbol: \~!@#$%^&*()_-+=[]{}|\;:\"',.<>?");
		bo = false;
	}
	
	return bo;
}