/**
 demo:<button onclick="SANINCO.Download(50)">test</button>
 requier: page.js,yahoo dialog
*/
(function(){
	if(typeof SANINCO != 'object'){SANINCO={};}

	var A = function(ele,html){
		var e = document.createElement('div');
		ele.appendChild(e);
		e.innerHTML = html;
	};
	
	var did = "saninco_down_div_1_120";
	var dpid = "saninco_down_page_div_1_119";
	var formId = "sanincoDownloadForm_0_110";
	
	var html = '<div class="main-yui-pe-content" id="'+did+'" style="visibility: hidden;overflow-y:auto;max-height:650px;">';
	html += 	'<div class="hd" >';
	html += 		'Download';
	html += 	'</div>';
	html +=		'<form>';
	html +=			'<div class="bd"  style="overflow:auto">';
	html +=				'<div id="'+dpid+'" style="overflow:auto" ></div>';
	html +=			'</div>';
	html +=		'</form>';
	html += '</div>';
	
	var html1 = '<form id="'+formId+'" action="download.action" method="post" style="display: none;">';
	html1 +=		'<input type="hidden" name="filePath" value=""/>';
	html1 +=		'<input type="hidden" name="fileName" value=""/>';
	html1 +=    '</form>';
	html1 +=    '<div id="__sanincoDownloadIframeDiv'+formId+'"></div>';
	
 	var D = function(pointId){
 		var d = SANINCO.Download;
 		if(d.isInit){
 			A(document.body,html);
 			A(document.body,html1);
			d.dialog = new YAHOO.widget.Dialog(did, 
				{ width : "600px",
				  modal : true,
				  fixedcenter : true,
				  visible : false, 
				  constraintoviewport : true,
				  buttons : [{ text:"Cancel", handler:function() {
								this.cancel();
					}} ]
				});
			d.dialog.render();
			//----
			d.page = new SANINCO.Page(dpid,"sanincoDownloadPage_0_10_00",{
		        sortingField : "attachment_file.id",
		        sortingDirection : "asc",
		        vo : "svo",
		        recPerArray : [5,10,15,20,30],
				autoToTop : false,
		        totalPageUri : "doAttachmentPointIdCountService.action",
		        dataUri : "doSearchAttachmentPointIdService.action",
		        cols : [
			        {
			            title : "File Name",
			            dataField: "file_name" ,
			            sort : "attachment_file.file_name",
						width : "400px"
			        },{
			            title : "Download",
			            dataField:function(obj){
		                    return "<img src='include/images/download1.gif' onClick=\"SANINCO.Download.go(" + "'" + obj.file_name.replace(/\'/g, "\\'") + "','" + obj.file_path.replace(/\'/g, "\\'") + "'" + ");\">";
		          		},
						width : "100px"
			    	}
		    	]
		    });
		    sanincoDownloadPage_0_10_00 = d.page;
		    d.go = function(name,path){
		    	var iframeDiv = document.getElementById("__sanincoDownloadIframeDiv"+formId);
		    	var df = document.getElementById(formId);
		    	var random = Math.random();
		    	iframeDiv.innerHtml='<iframe id="__sanincoDownloadIframe'+random+'" style="display: none;"></iframe>';
		    	df.target = '__sanincoDownloadIframe'+random;
				df.fileName.value = name;
				df.filePath.value = path;
				df.submit();
		    };
 			d.isInit = false;
 		}
 		d.page.param = {"attachmentPointId":pointId};
 		
 		d.page.start(); 
 		d.dialog.show();
 	};
 	
	SANINCO.Download=D;
	SANINCO.Download.isInit = true;
})();
(function(){
	if(typeof SANINCO != 'object'){SANINCO={};}

	var A = function(ele,html){
		var e = document.createElement('div');
		ele.appendChild(e);
		e.innerHTML = html;
	};
	
	var did = "saninco_Ddown_div_1_120";
	var dpid = "saninco_Ddown_page_div_1_119";
	var formId = "sanincoDDownloadForm_0_110";
	
	var html = '<div class="main-yui-pe-content" id="'+did+'" style="visibility: hidden;">';
	html += 	'<div class="hd" >';
	html += 		'Download';
	html += 	'</div>';
	html +=		'<form>';
	html +=			'<div class="bd" style="overflow:auto">';
	html +=				'<div id="'+dpid+'"></div>';
	html +=			'</div>';
	html +=		'</form>';
	html += '</div>';
	
	var html1 = '<form id="'+formId+'" action="download.action" method="post" style="display: none;">';
	html1 +=		'<input type="hidden" name="filePath" value=""/>';
	html1 +=		'<input type="hidden" name="fileName" value=""/>';
	html1 +=    '</form>';
	html1 +=    '<div id="__sanincoDownloadIframeDiv'+formId+'"></div>';
	
 	var D = function(disputeId){
 		var d = SANINCO.disputeDownLoad;
 		if(d.isInit){
 			A(document.body,html);
 			A(document.body,html1);
			d.dialog = new YAHOO.widget.Dialog(did, 
				{ width : "600px",
				  modal : true,
				  fixedcenter : true,
				  visible : false, 
				  constraintoviewport : true,
				  buttons : [{ text:"Cancel", handler:function() {
								this.cancel();
					}} ]
				});
			d.dialog.render();
			//----
			d.page = new SANINCO.Page(dpid,"sanincoDDownloadPage_0_10_00",{
		        sortingField : "attachment_file.id",
		        sortingDirection : "asc",
		        vo : "svo",
		        recPerArray : [5,10,15,20,30],
				autoToTop : false,
		        totalPageUri : "disputeIdAttachmentPointIdCountService.action",
		        dataUri : "disputeSearchAttachmentPointIdService.action",
		        cols : [
			        {
			            title : "File Name",
			            dataField: "file_name" ,
			            sort : "attachment_file.file_name",
						width : "400px"
			        },{
			            title : "Download",
			            dataField:function(obj){
		                    return "<img src='include/images/download1.gif' onClick=\"SANINCO.disputeDownLoad.go(" + "'" + obj.file_name.replace(/\'/g, "\\'") + "','" + obj.file_path.replace(/\'/g, "\\'") + "'" + ");\">";
		          		},
						width : "100px"
			    	}
		    	]
		    });
		    sanincoDDownloadPage_0_10_00 = d.page;
		    d.go = function(name,path){
		    	var iframeDiv = document.getElementById("__sanincoDownloadIframeDiv"+formId);
		    	var df = document.getElementById(formId);
		    	var random = Math.random();
		    	iframeDiv.innerHtml='<iframe id="__sanincoDownloadIframe'+random+'" style="display: none;"></iframe>';
		    	df.target = '__sanincoDownloadIframe'+random;
				df.fileName.value = name;
				df.filePath.value = path;
				df.submit();
		    };
 			d.isInit = false;
 		}
 		d.page.param = {"disputeId":disputeId};
 		
 		d.page.start(); 
 		d.dialog.show();
 	};
 	
	SANINCO.disputeDownLoad=D;
	SANINCO.disputeDownLoad.isInit = true;
})();


(function(){
	if(typeof SANINCO != 'object'){SANINCO={};}

	var A = function(ele,html){
		var e = document.createElement('div');
		ele.appendChild(e);
		e.innerHTML = html;
	};
	
	var did = "saninco_invoice_notes_down_div_1_120";
	var dpid = "saninco_invoice_notes_down_page_div_1_119";
	var formId = "sanincoInvoiceNotesDownloadForm_0_110";
	
	var html = '<div class="main-yui-pe-content" id="'+did+'" style="visibility: hidden;overflow-y:auto;max-height:650px;">';
	html += 	'<div class="hd" >';
	html += 		'Download';
	html += 	'</div>';
	html +=		'<form>';
	html +=			'<div class="bd"  style="overflow:auto">';
	html +=				'<div id="'+dpid+'"></div>';
	html +=			'</div>';
	html +=		'</form>';
	html += '</div>';
	
	var html1 = '<form id="'+formId+'" action="download.action" method="post" style="display: none;">';
	html1 +=		'<input type="hidden" name="filePath" value=""/>';
	html1 +=		'<input type="hidden" name="fileName" value=""/>';
	html1 +=    '</form>';
	html1 +=    '<div id="__sanincoDownloadIframeDiv'+formId+'"></div>';
	
 	var D = function(pointId){
 		var d = SANINCO.invoiceNotesDownload;
 		if(d.isInvoiceInit){
 			A(document.body,html);
 			A(document.body,html1);
			d.dialog = new YAHOO.widget.Dialog(did, 
				{ width : "600px",
				  modal : true,
				  fixedcenter : true,
				  visible : false, 
				  constraintoviewport : true,
				  buttons : [{ text:"Cancel", handler:function() {
								this.cancel();
					}} ]
				});
			d.dialog.render();
			//----
			d.page = new SANINCO.Page(dpid,"sanincoInvoiceNotesDownloadPage_0_10_00",{
		        sortingField : "attachment_file.id",
		        sortingDirection : "asc",
		        vo : "svo",
		        recPerArray : [5,10,15,20,30],
				autoToTop : false,
		        totalPageUri : "doAttachmentPointIdCountService.action",
		        dataUri : "doSearchAttachmentPointIdService.action",
		        cols : [
			        {
			            title : "File Name",
			            dataField: "file_name" ,
			            sort : "attachment_file.file_name",
						width : "400px"
			        },{
			            title : "Download",
			            dataField:function(obj){
		                    return "<img src='include/images/download1.gif' onClick=\"SANINCO.invoiceNotesDownload.go(" + "'" + obj.file_name.replace(/\'/g, "\\'") + "','" + obj.file_path + "'" + ");\">";
		          		},
						width : "100px"
			    	},{
			            title : "Delete",
			            dataField:function(obj){
		                    return "<img src='include/images/delete.png' onClick=\"deleteAttachmentFile(" + "'" + obj.id + "','" +pointId+"'" + ");\">";
		          		},
						width : "30px"
			    	}
		    	]
		    });
			sanincoInvoiceNotesDownloadPage_0_10_00 = d.page;
		    d.go = function(name,path){
		    	var iframeDiv = document.getElementById("__sanincoDownloadIframeDiv"+formId);
		    	var df = document.getElementById(formId);
		    	var random = Math.random();
		    	iframeDiv.innerHtml='<iframe id="__sanincoInvoiceNotesDownloadIframe'+random+'" style="display: none;"></iframe>';
		    	df.target = '__sanincoInvoiceNotesDownloadIframe'+random;
				df.fileName.value = name;
				df.filePath.value = path;
				df.submit();
		    };
 			d.isInvoiceInit = false;
 		}
 		d.page.param = {"attachmentPointId":pointId};
 		
 		d.page.start(); 
 		d.dialog.show();
 	};
 	
	SANINCO.invoiceNotesDownload=D;
	SANINCO.invoiceNotesDownload.isInvoiceInit = true;
})();

function deleteAttachmentFile(attachmentFileId,pointId){
	if(confirm('Do you want to remove this file?')) {
		$.ajax({
			url: actionUri.deleteInvoiceNote,
			type: 'POST',
			dataType: 'text',
			data: {"attachmentFileId": attachmentFileId },
			success: function(o) {
				SANINCO.invoiceNotesDownload(pointId);
				invoiceTabInit.get('tab9');
			},
			error: function() {
				alert('Delete the file request failed!');
			}
		});
	}
}