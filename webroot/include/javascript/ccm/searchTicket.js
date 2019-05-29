//@auchor chao.Liu (Optimization of complete by xinyu.Liu)
var saveQuicklinkUri = "doSaveTicketSearch.action";

actionUri = {
	getTicketTotalPageNoUri : "doGetTicketSearchTotalPageNo.action",
	searchTicketUri : "doSearchTicket.action",
	saveExcelBySearchTicketUri : "saveExcelBySearchTicket.action"
};

function composePostDataFromUi(){
	var postData = "";
	if($('#form0_searchTicketVO_ticketNumber').val()!="") postData += ("&searchTicketVO.ticketNumber=" + $('#form0_searchTicketVO_ticketNumber').val());
	if($('#form0_searchTicketVO_createdById').val()!="all") postData += ("&searchTicketVO.createdById=" + $('#form0_searchTicketVO_createdById').val());
	if($('#form0_searchTicketVO_modifiedById').val()!="all") postData += ("&searchTicketVO.modifiedById=" + $('#form0_searchTicketVO_modifiedById').val());
	
	if($('#form0_searchTicketVO_ticketDueStartsOn').val()!="") postData += ("&searchTicketVO.ticketDueStartsOn=" + $('#form0_searchTicketVO_ticketDueStartsOn').val());
	if($('#form0_searchTicketVO_ticketDueEndsOn').val()!="") postData += ("&searchTicketVO.ticketDueEndsOn=" + $('#form0_searchTicketVO_ticketDueEndsOn').val());
	if($('#form0_searchTicketVO_ticketDueWiPastNumOfDays').val()!="") postData += ("&searchTicketVO.ticketDueWiPastNumOfDays=" + $('#form0_searchTicketVO_ticketDueWiPastNumOfDays').val());
	
	if($('#form0_searchTicketVO_priorityId').val()!="all") postData += ("&searchTicketVO.priorityId=" + $('#form0_searchTicketVO_priorityId').val());
	if($('#form0_searchTicketVO_severityId').val()!="all") postData += ("&searchTicketVO.severityId=" + $('#form0_searchTicketVO_severityId').val());
	if($('#form0_searchTicketVO_statusId').val()!="all") postData += ("&searchTicketVO.statusId=" + $('#form0_searchTicketVO_statusId').val());
	
	return postData;
}

///validate data
function validateFields(){
	return FIC_checkForm('form0');
}


function judgDate(){
	var bo = true;
	var timePast = $('#form0_searchTicketVO_ticketDueWiPastNumOfDays').val();
	var nodes = YAHOO.util.Selector.query(".validate-date-ca",'form0');
	
	for(var d = 0; d<nodes.length; d++){
		if(d+1 < nodes.length){
			if((nodes[d].value != "") && (nodes[d+1].value != "")){
				if(!correct(nodes[d].value,nodes[d+1].value)){
					removeClassName(nodes[d].id,'validation-passed');
					addClassName(nodes[d].id,'validation-failed');
					removeClassName(nodes[d+1].id,'validation-passed');
					addClassName(nodes[d+1].id,'validation-failed');
					bo = false;
				}
			}
		}
		if(!isValidDate(nodes[d].value)) {
			removeClassName(nodes[d].id,'validation-passed');
			addClassName(nodes[d].id,'validation-failed');
			bo = false;
		}
	}
	
	if(timePast < 0 || timePast > 10000) {
		removeClassName('form0_searchTicketVO_ticketDueWiPastNumOfDays','validation-passed');
		addClassName('form0_searchTicketVO_ticketDueWiPastNumOfDays','validation-failed');
		bo = false;
	}
	return bo;
}

function saveTicketExcel(){
	showLoadingModalLayer();
	var titles = ticketPage.getTitlesParam('titles');
	window.location.href= actionUri.saveExcelBySearchTicketUri + "?" + titles + "&" + ticketPage.paramStr;
	hideLoadingModalLayer();
}

function startSearch(){
	// Validation Date
	if((!validateFields()) | (!judgDate())) return;
	if(!window.ticketPage){
		ticketPage = new SANINCO.Page('_dataTable','ticketPage',{
		    sortingField : 'id',
		    sortingDirection : 'asc',
			vo : 'searchTicketVO',
			pageTable : 'block',
			height : '100%',
		    totalPageUri : actionUri.getTicketTotalPageNoUri,
		    dataUri : actionUri.searchTicketUri,
			paginationDiv : '_dataTable_page',
			recPerArray : [10,20,30,40,50,70,90,100],
		    cols : [
				   {title : "Ticket Number", dataField : function(o){
							return "<a href=\"javascript:mark(\'viewTicketDetail.action?ticketId="+o.id+"\')\">"+o.id+"</a>";
						}, sort : "id", filtration : {name:"id",alias:"Ticket Number"}
				  },{title : "Title", className : "table_td_br", width:"180px", dataField: "title", sort : "title", filtration : {name:"title",alias:"Title"}
				  },{title : "Priority", dataField : "priority_name", sort : "priority_id", filtration : {name:"priority_name",alias:"Priority"}
				  },{title : "Severity", dataField : "severity_name", sort : "severity_name" , filtration : {name:"severity_name",alias:"Severity"}
				  },{title : "Ticket Status", dataField : "ticket_status_name", sort : "ticket_status_name", filtration : {name:"ticket_status_name",alias:"Ticket Status"}
				  },{title : "Created Timestamp", dataField : "created_timestamp", sort : "created_timestamp", filtration : {name:"created_timestamp",alias:"Created Timestamp"}
				  },{title : "Created By", dataField : "created_name", sort : "created_name", filtration : {name:"created_name",alias:"Created By"}
				  },{title : "Modified By ", dataField : "modified_name", sort : "modified_name" , filtration : {name:"modified_name",alias:"Modified By "}
				  }
			]
		});
		
		ticketPage.addTotalSuccessEvent(function(m,t){
			if(m.totalResultNo == 0) document.getElementById('saveTicketExcel').style.display = "none";
			else document.getElementById('saveTicketExcel').style.display = "block";
		});
	}
		ticketPage.myParam = composePostDataFromUi();
		
	    ticketPagefilter = new SANINCO.Filter();
	    ticketPagefilter.addEditeEvent(function(){ticketPage.start();});
	    ticketPagefilter.add('id', 'Number');
	    ticketPagefilter.add('title', 'String');
	    ticketPagefilter.add('priority_name', 'String');
	    ticketPagefilter.add('severity_name', 'String');
	    ticketPagefilter.add('ticket_status_name', 'String');
	    ticketPagefilter.add('created_timestamp', 'String');
	    ticketPagefilter.add('created_name', 'String');
	    ticketPagefilter.add('modified_name', 'String'); 
	    ticketPage.setFilter(ticketPagefilter);
	
		ticketPage.start();
	
}

function resetRadioButton(){
	form0.selectTicketDateRadio[0].checked=true;
}

//update radio button after get saved quicklink.
function updateRadioButton(){
		if(get('form0_searchTicketVO_ticketDueWiPastNumOfDays').value != ""){
			form0.selectTicketDateRadio[1].checked=true;
		}
		if(get('form0_searchTicketVO_ticketDueStartsOn').value != "" || get('form0_searchTicketVO_ticketDueEndsOn').value != ""){
			form0.selectTicketDateRadio[0].checked=true;
		}
}

function changRadio(type){
	if(type == 0){
 		$('#form0_searchTicketVO_ticketDueWiPastNumOfDays').val('');
		form0.selectTicketDateRadio[0].checked = true;
	}
 	if(type == 1){
 		$('#form0_searchTicketVO_ticketDueStartsOn').val('');
 		$('#form0_searchTicketVO_ticketDueEndsOn').val('');
		form0.selectTicketDateRadio[1].checked = true;
	}
}