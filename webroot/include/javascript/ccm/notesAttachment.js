var getInvoiceNoteTotalPageNoUri="doGetInvoiceNoteTotalPageNoUri.action";
var searchInvoiceNoteUri="doGetInvoiceNoteUri.action";


function notesAttachment(){
	
		notesAttachmentTabel = new SANINCO.Page('_dataTable2',"notesAttachmentTabel",{
		    sortingField : "t.created_timestamp",
		    sortingDirection : "desc",
		    recordText: "",
			vo : "invoiceNoteVO",
		    totalPageUri : getInvoiceNoteTotalPageNoUri,
		    dataUri : searchInvoiceNoteUri,
			paginationDiv : "_dataTable_page2",
			recPerArray : [5,10,20,40,60,80,100],
		    cols : [
					{ title : "Notes",dataField:function(o){return {less_sign:(o.notes).replace(/\</g,"&lt;")}},sort : "t.notes", filtration : {name:"t.notes",alias:"Notes"},className:"td-note-wrap"
					},
					{ title : "Analyst Name",dataField: "analyst",sort : "concat(u.first_name,u.last_name)", filtration : {name:"concat(u.first_name,blank_space(),u.last_name)",alias:"Analyst Name"}
					},
					{ title : "Created Date",dataField: "created_timestamp",sort : "t.created_timestamp", filtration : {name:"t.created_timestamp",alias:"Created Date"}
				    },
					{ title : "File",dataField:function(o){
						return (o.attachmentPoint == "" ? "" : "<img src=\"include/images/attachment.png\" style=\"cursor: pointer;\" onclick=\"SANINCO.invoiceNotesDownload(" + o.attachmentPoint + ");\"/>");}
				    }
				]
		}); 
		

		
	notesAttachmentFilter = new SANINCO.Filter();
	notesAttachmentFilter.addEditeEvent(function(){notesAttachmentTabel.start();});
	notesAttachmentFilter.add('t.created_timestamp', 'String');
	notesAttachmentFilter.add("concat(u.first_name,blank_space(),u.last_name)", "String");
	notesAttachmentFilter.add('t.notes', 'String');
	notesAttachmentTabel.setFilter(notesAttachmentFilter);
	
	notesAttachmentTabel.myParam=composePostDataFromNnotesAttachment();
	notesAttachmentTabel.start();
	
//	initInvoiceNoteWindow();
	
	//-------------------------------
}
function composePostDataFromNnotesAttachment(){	
	var postDate ="invoiceNoteVO.invoiceId="+$('#invoiceNoteInvoiceIdByinvoice').val();
	return postDate;
}

