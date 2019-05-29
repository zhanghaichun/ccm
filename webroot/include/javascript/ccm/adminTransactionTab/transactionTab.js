// @Auchor chao.Liu (Optimization of complete by xinyu.Liu)
var transactionTabLC = {
	$ : function (id){
		return typeof (id) == "string" ? document.getElementById(id) : id;
	},
	iid : "",
	updateHandle : false,
	clearPTXT : function (){
		var cls = document.getElementsByName("txtPaymentTransactionValue");
		for(var i=0; i<cls.length; i++){
			cls[i].value = "";
		}
		transactionTabLC.$("paymentTransactionTR").style.display = "none";
		return cls;
	},
	clearITXT : function (){
		var cls = document.getElementsByName("txtInvoiceIRValue");
		for(var i=0; i<cls.length; i++){
			cls[i].value = "";
		}
		transactionTabLC.$("invoiceIRTR").style.display = "none";
		return cls;
	},
	clearFileById : function (id,id2){
		$("#"+id2).val("");
		var file = transactionTabLC.$(id);
		try{ // FF Is Error,But IE Is OK!			
			file.outerHTML = file.outerHTML.replace(/(value=\").+\"/i,"$1\"");
		}catch(e){
			file.value = "";
		}
		
	},
	clearFileAll : function (){
		clearFormUploadFiles('frmPaymentTransaction');
		$("#__up_load_text_admin1").val("");
		$("#__up_load_text_admin2").val("");
		$("#__up_load_text_admin3").val("");
		$("#__up_load_text_admin4").val("");
		$("#__up_load_text_admin5").val("");
	},
	searchPT : function (){
		var pname = transactionTabLC.$("txtPaymentTransactionNumber").value;
		if(pname == ""){
			alert("Payment Transaction Number is required !");
			return false;
		}
		var fff = document.getElementById('frmPaymentTransaction');
		$(fff["searchPaymentVO.paymentReferenceCode"]).val("");
		$(fff["searchPaymentVO.paidDate"]).val("");
		$(fff["searchPaymentVO.notes"]).val("");
		$("#__up_load_text_admin1").val("");
		$("#__up_load_text_admin2").val("");
		$("#__up_load_text_admin3").val("");
		$("#__up_load_text_admin4").val("");
		$("#__up_load_text_admin5").val("");
		
		clearFormUploadFiles('frmPaymentTransaction');
		
		transactionTabLC.clearPTXT(); // Clear
		
		var callback = {
			success: transactionTabLC.searchPTIsSuccess,
			failure: showError
		};
		var searchPTParamer = "searchPaymentVO.paymentTransactionNumber="+ccmEscape(pname);
		YAHOO.util.Connect.asyncRequest('POST' , "doSearchPaymentByPName.action" , callback , searchPTParamer )
	},
	searchPTIsSuccess : function (o){
		if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
			window.location.reload();
			return;
		}
		var data = eval("("+o.responseText+")");
		if(data.error){
			alert(data.error);
			return false;
		} 
		var key = ["vendorName","ban","paymentTransactionNumber","paymentAmount","paymentDate","paymentStatus","description","iid"];
		var cls = transactionTabLC.clearPTXT();
		for(var i=0; i<key.length; i++){
			cls[i].value = data[key[i]];
		}

		var fff = document.getElementById('frmPaymentTransaction');
		$(fff["searchPaymentVO.paymentReferenceCode"]).val(data.paymentReferenceCode);
		$(fff["searchPaymentVO.paidDate"]).val(data.paidDate);
		$(fff["searchPaymentVO.notes"]).val(data.description);
		
		if(data.attachmentPoint)transactionTabLC.downloadFile.star(data.attachmentPoint);
		transactionTabLC.$("txtPymentIdPT").value = data.pid;
		transactionTabLC.$("paymentTransactionTR").style.display = "";
	},
	downloadFile : {
		initPage : function (){
			transactionTabLC.downloadFile.filePage = new SANINCO.Page("paymentTransactionDFDiv","transactionTabLC.downloadFile.filePage",{
				sortingField : "id",
				sortingDirection : "asc",
				vo : "searchPaymentVO",
				recPerArray : [5,10,15,20,30],
				totalPageUri : "doGetPaymentFileTotalNO.action",
				dataUri : "doSearchPaymentFileList.action",
				cols : [
					  {title : "File Name",dataField: "fileName"
					},{title : "Download",dataField: function(row){
					      			return '<img src="include/images/download1.gif" onclick="transactionTabLC.downloadFile.download(\''+row.fileName+'\',\''+row.filePath+'\')"/>'; 
					  	  		}
					},{title : "Delete",dataField: function(row){
					      			return '<img src="include/images/reject160.png" onclick="transactionTabLC.downloadFile.deleteFile.star(\''+row.id+'\');"/>'; 
					  			}
					}
				]
			});
		},
		star : function (pointId){
			if(!pointId){
				$("#paymentTransactionDFDiv").hide();
				return false;
			}else{
				$("#paymentTransactionDFDiv").show();
			}
			var args = arguments;
			transactionTabLC.downloadFile.filePage.voParam = {'attachmentPointId' : pointId};
			transactionTabLC.downloadFile.filePage.start();
		},
		download : function (name, path){
		    var df = document.getElementById("downloadPaymentTransactionForm");
		    df.filePath.value = path;
		    df.fileName.value = name;
		    df.submit();
		},
		deleteFile : {
			star : function (id){
				var callback = {
					success: transactionTabLC.downloadFile.deleteFile.end,
					failure: showError
				};
				var paramer = "searchPaymentVO.attachmentFileId="+id;
				YAHOO.util.Connect.asyncRequest('POST' , "doDeletePaymentFileByFId.action" , callback , paramer )
			},
			end : function (o){
				if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
					window.location.reload();
					return;
				}
				var data = eval("("+o.responseText+")");
				if(data.error){
					alert(data.error);
					return false;
				}
				transactionTabLC.downloadFile.filePage.reload();
			}
		}
	},
	searchIR : function (){
		var iname = transactionTabLC.$("txtInvoiceNumberIR").value;
		if(iname == ""){
			alert("Invoice Number is required!");
			return false;
		}
		
		transactionTabLC.clearITXT(); // Clear
		
		var callback = {
			success: transactionTabLC.searchIRIsSuccess,
			failure: showError
		};
		var searchIRParamer = "searchInvoiceVO.invoiceNumber="+ccmEscape(iname);
		YAHOO.util.Connect.asyncRequest('POST' , "doSearchInvoiceByIName.action" , callback , searchIRParamer )
	},
	searchPTOW : function (){
		var iname = transactionTabLC.$("txtInvoiceNumberPTOW").value;
		if(iname == ""){
			alert("Invoice Number is required!");
			return false;
		}
		var postData = "";
		if($('#txtInvoiceNumberPTOW').val()!= "") postData = ("searchInvoiceVO.invoiceNumber="+ccmEscape($('#txtInvoiceNumberPTOW').val()));
		PTOW.myParam = postData;
		filter1.clearAll();
		PTOW.start();
	},
	searchIRIsSuccess : function (o){
		if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
			window.location.reload();
			return;
		}
		var data = eval("("+o.responseText+")");
		if(data.error){
			alert(data.error);
			return false;
		}
		var key = ["vendorName","ban","invoiceNumber","status","invoiceDate"]
		var cls = transactionTabLC.clearITXT();
		for(var i=0; i<key.length; i++){
			cls[i].value = data[key[i]];
		}   
		
		transactionTabLC.iid = data.iid;
		if(transactionTabLC.updateHandle == true){
			transactionTabLC.$("invoiceIRTR").style.display = "none";
			transactionTabLC.updateHandle = false;
		}else{
			transactionTabLC.$("invoiceIRTR").style.display = "";
		}
		
	},

	paymentTraSubmit : function (id){
		transactionTabLC.$("txtWorkflowUserId").value = id;
		
		var boo = false;
		boo = uploadFileValidate("frmPaymentTransaction");
		
		if(!boo){return false;}
		
		var note = transactionTabLC.$("txtPaymentNotes").value;
		if(note.length <= 10 ){alert("Notes length must more than 10!");return false;}
		
		if(!confirm("Do you Confirm?")){return false;}
		
		transactionTabLC.$("frmPaymentTransaction").submit();
		$("#paymentTransactionDFDiv").show();
	},
	invoiceReject : {
		checkNotes : function (){
			var note = transactionTabLC.$("txtInvoiceRejectNotes").value;
			if(note.length == 0 || note.length >= 10 ){
				transactionTabLC.$("btnRejectIR").disabled = "";
			}else{
				transactionTabLC.$("btnRejectIR").disabled = "disabled";
			}
		},
		submit : function (){
			var note = transactionTabLC.$("txtInvoiceRejectNotes").value;
			if(note.length <= 10 ){alert("Notes length must more than 10!");return false;}
			if(!confirm("Are you sure you want to reject this invoice?")){return false;}
			var callback = {
				success: transactionTabLC.invoiceReject.submitIsSuccess,
				failure: showError
			};
			var submitParamer = "searchInvoiceVO.invoiceId="+transactionTabLC.iid+"&searchInvoiceVO.note="+note;
			showLoadingModalLayer();
			YAHOO.util.Connect.asyncRequest('POST' , "doSaveInvoiceReject.action" , callback , submitParamer )
		},
		submitIsSuccess : function (o){
			if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
				window.location.reload();
				return;
			}
			var data = eval("("+o.responseText+")");
			hideLoadingModalLayer();
			if(data.error){
				alert(data.error);
				return false;
			}
			transactionTabLC.updateHandle = true;
			transactionTabLC.searchIR();
		}
	},
	clearValueTab : function (){
		$("#paymentTransactionDFDiv").hide();
		$("#txtPaymentTransactionNumber").val("");
		transactionTabLC.clearPTXT();
		$('#txtInvoiceNumberIR').val('');
		transactionTabLC.clearITXT();
		transactionChildTabView.selectTab(0);
	},
	
	searchInvoiceNumberPRJ : function (){
		var invoiceNumber = $("#txtInvoiceNumberPRJ").val();
		if(invoiceNumber == ""){
			alert("Invoice Number is required!");
			return;
		}
		
		var callback = {
			success: transactionTabLC.searchPRJSuccess,
			failure: showError
		};
		var searchPRJParamer = "searchInvoiceVO.invoiceNumber="+ccmEscape(invoiceNumber)+"&searchInvoiceVO.reject=true";
		YAHOO.util.Connect.asyncRequest('POST' , "doSearchInvoiceForPRJ.action" , callback , searchPRJParamer )
	},
	
	searchPRJSuccess : function (o){
		if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
			window.location.reload();
			return;
		}
		var data = eval("("+o.responseText+")");
		if(data.error){
			alert(data.error);
			return false;
		}
		
		if(data.payment){
			alert("Reject Success!");
		}
	},
	
	searchForPRS : function (){
		var invoiceNumber = $("#txtInvoiceNumberPRS").val();
		var site = $("#txtSite").val();
		
		if(invoiceNumber == ""){
			alert("Invoice Number is required !");
			return;
		}
		
		var callback= {
			success: transactionTabLC.searchForPRSSuccess,
			failure: showError
		}
		
		var searchPRSParamer = "searchInvoiceVO.invoiceNumber="+ccmEscape(invoiceNumber)+"&searchInvoiceVO.site="+site;
		
		YAHOO.util.Connect.asyncRequest('POST' , "doSearchInvoiceForPRS.action" , callback , searchPRSParamer )
	},
	
	searchForPRSSuccess : function (o){
		if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
			window.location.reload();
			return;
		}
		var data = eval("("+o.responseText+")");
		if(data.error){
			alert(data.error);
			return false;
		}
		
		if(data.payment){
			alert("Resend Success!");
		}
		if(data.equals){
			if(confirm("The ap_supplier_site is same as last sent. Do you want to resend ?")){
				var callback= {
					success: transactionTabLC.PRSConfirmSuccess,
					failure: showError
				}
				var invoiceNumber = $("#txtInvoiceNumberPRS").val();
				var site = $("#txtSite").val();
				var searchPRSParamer = "searchInvoiceVO.invoiceNumber="+ccmEscape(invoiceNumber)+"&searchInvoiceVO.site="+site;
				YAHOO.util.Connect.asyncRequest('POST' , "doPRSConfirmed.action" , callback , searchPRSParamer );
			}
		}
	},
	
	PRSConfirmSuccess : function (o){
		var data = eval("("+o.responseText+")");
		if(data.error){
			alert(data.error);
			return false;
		}
		if(data.payment){
			alert("Resend Success!");
		}
	},
	
	searchForScoa : function (){
		var invoiceNumber = $("#txtInvoiceNumberPRS1").val();
		var lineNumber = $("#txtLineNumber").val();
		var scoa = 	$("#txtSCOA").val();	
		if(invoiceNumber == ""){
			alert("Invoice Number is required !");
			return;
		}
		if(lineNumber == ""){
			alert("Line Number is required !");
			return;
		}
		if(scoa == ""){
			alert("SCOA: is required !");
			return;
		}
		
		var callback= {
			success: transactionTabLC.searchForScoaSuccess,
			failure: showError
		}
		
		var searchForScoaParamer = "searchInvoiceVO.invoiceNumber="+ccmEscape(invoiceNumber)+"&searchInvoiceVO.scoacode="+scoa+"&searchInvoiceVO.lineOfBusiness="+lineNumber;
		
		YAHOO.util.Connect.asyncRequest('POST' , "doSearchForScoa.action" , callback , searchForScoaParamer )
	},
	
	searchForScoaSuccess : function (o){
		if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
			window.location.reload();
			return;
		}
		var data = eval("("+o.responseText+")");
		if(data.error){
			alert(data.error);
			return false;
		}
		if(data.payment){
			alert("Resend Success!");
		}
		if(data.up){
			alert(data.up);
		}
		
		if(data.insert){
			if(confirm("New SCOA! Do you want to continue?")){
				var callback= {
					success: transactionTabLC.SCOAConfirmSuccess,
					failure: showError
				}
				var invoiceNumber = $("#txtInvoiceNumberPRS1").val();
				var lineNumber = $("#txtLineNumber").val();
				var scoa = 	$("#txtSCOA").val();
				
				var searchForScoaParamer = "searchInvoiceVO.invoiceNumber="+ccmEscape(invoiceNumber)+"&searchInvoiceVO.scoacode="+scoa+"&searchInvoiceVO.lineOfBusiness="+lineNumber+"&searchInvoiceVO.insertConfirmed=true";		
				YAHOO.util.Connect.asyncRequest('POST' , "doSearchForScoa.action" , callback , searchForScoaParamer )
			}
		}
		if(data.equals){
			if(confirm("This SCOA is same as last sent. Do you want to resend? ?")){
				var callback= {
					success: transactionTabLC.SCOAConfirmSuccess,
					failure: showError
				}
				var invoiceNumber = $("#txtInvoiceNumberPRS1").val();
				var lineNumber = $("#txtLineNumber").val();
				var scoa = 	$("#txtSCOA").val();
				
				var searchForScoaParamer = "searchInvoiceVO.invoiceNumber="+ccmEscape(invoiceNumber)+"&searchInvoiceVO.scoacode="+scoa+"&searchInvoiceVO.lineOfBusiness="+lineNumber;		
				YAHOO.util.Connect.asyncRequest('POST' , "doPRSConfirmed.action" , callback , searchForScoaParamer )
			}
		}
	},
	
	SCOAConfirmSuccess : function (o){
		var data = eval("("+o.responseText+")");
		if(data.error){
			alert(data.error);
			return false;
		}
		if(data.payment){
			alert("Resend Success!");
			return false;
		}
		if(data.up){
			alert(data.up);
		}
	},
	
	panelInit : {
		createPanel : function (){
			YAHOO.ccm.container.closeInvoicePanel = new YAHOO.widget.Dialog("closeInvoicePanel", {
		        width: "570px",
		        fixedcenter: true,
		        visible: false,
		        constraintoviewport: true,
		        modal: true,
		        buttons: [{
		            text: "Cancel Invoice",
		            handler: function(){
		        	var notes = $("#closeNote").val();
		        	if (notes.length < 10) {
		        		 $("#closeNote").removeClass("validation-passed").addClass("validation-failed");
		        		return;
		        		}
		        	var callback = {
		    				success: function(o){
		        			hideLoadingModalLayer();	
		        			var data = eval("("+o.responseText+")");
		        			if (data.info) {
		        			   YAHOO.ccm.container.closeInvoicePanel.hide();
		        			   alert(data.info);
		        			   PTOW.start();
		        			}
		        		},
		    				failure: showError
		    			};
		    			var submitParamer = "searchInvoiceVO.invoiceId="+$("#closeInvoiceId").val()+ "&searchInvoiceVO.note=" + notes;
		    			showLoadingModalLayer();
		    			YAHOO.util.Connect.asyncRequest('POST' , "doCancelInvoice.action" , callback , submitParamer );
		            }
		          },{
		            text: "Close Invoice",
		            handler: function(){
		        	var notes = $("#closeNote").val();
		        	if (notes.length < 10) {
		        		 $("#closeNote").removeClass("validation-passed").addClass("validation-failed");
		        		return;
		        		}
		        	var callback = {
		    				success: function(o){
		        			hideLoadingModalLayer();	
		        			var data = eval("("+o.responseText+")");
		        			if (data.info) {
		        			   YAHOO.ccm.container.closeInvoicePanel.hide();
		        			   alert(data.info);
		        			   PTOW.start();
		        			}
		        		},
		    				failure: showError
		    			};
		    			var submitParamer = "searchInvoiceVO.invoiceId="+$("#closeInvoiceId").val()+ "&searchInvoiceVO.note=" + notes;
		    			showLoadingModalLayer();
		    			YAHOO.util.Connect.asyncRequest('POST' , "doCloseInvoice.action" , callback , submitParamer );
		            }
		        }, {
		          text: "Cancel",
		          handler: function(){
			          this.cancel();
		          }
		        }]
		    });
			YAHOO.ccm.container.closeInvoicePanel.render();
			YAHOO.ccm.container.choosePanel = new YAHOO.widget.Dialog("choosePanel", {
				width: "570px",
				fixedcenter: true,
				visible: false,
				constraintoviewport: true,
				modal: true
			});
			YAHOO.ccm.container.choosePanel.render();
			YAHOO.ccm.container.throughAPPaymentPanel = new YAHOO.widget.Dialog("throughAPPaymentPanel", {
		        width: "570px",
		        fixedcenter: true,
		        visible: false,
		        constraintoviewport: true,
		        modal: true,
		        buttons: [{
		            text: "Close Invoice",
		            handler: function(){
		        	var notes = $("#throughAPPaymentCloseNote").val();
		        	var remittance = $("#remittance").val();
		        	var paidDate = $("#paidDate").val();
		        	var pass = true;
		        	if (notes.length < 10) {
		        		 $("#throughAPPaymentCloseNote").removeClass("validation-passed").addClass("validation-failed");
		                 pass = false;
		        		} else {
		    			$("#throughAPPaymentCloseNote").removeClass("validation-failed").addClass("validation-passed");
		        		}
		        	
		        	if (!pass) {
		        		return;
		        	}
		        	var callback = {
		    				success: function(o){
		        			hideLoadingModalLayer();	
		        			var data = eval("("+o.responseText+")");
		        			if (data.info) {
		        			   YAHOO.ccm.container.throughAPPaymentPanel.hide();
		        			   alert(data.info);
		        			   PTOW.start();
		        			}
		        		},
		    				failure: showError
		    			};
		    			var submitParamer = "searchInvoiceVO.invoiceId="+$("#throughAPPaymentCloseInvoiceId").val()+ "&searchInvoiceVO.note=" + notes + "&searchInvoiceVO.paymentReferenceCode=" + remittance + "&searchInvoiceVO.paidDate=" + paidDate+"&searchInvoiceVO.upPayment=true";
		    			showLoadingModalLayer();
		    			YAHOO.util.Connect.asyncRequest('POST' , "doCloseInvoice.action" , callback , submitParamer );
		            }
		        },{
		            text: "Close Invoice(A/P Payment)",
		            handler: function(){
		        	var notes = $("#throughAPPaymentCloseNote").val();
		        	var remittance = $("#remittance").val();
		        	var paidDate = $("#paidDate").val();
		        	var pass = true;
		        	if (notes.length < 10) {
		        		 $("#throughAPPaymentCloseNote").removeClass("validation-passed").addClass("validation-failed");
		                 pass = false;
		        		} else {
		    			$("#throughAPPaymentCloseNote").removeClass("validation-failed").addClass("validation-passed");
		        		}
		        	if (paidDate.length > 0 && !transactionTabLC.panelInit.sp_checkDate(paidDate)) {
		        		$("#paidDate").removeClass("validation-passed").addClass("validation-failed");
		        		pass = false;
		        	} else {
		        		$("#paidDate").removeClass("validation-failed").addClass("validation-passed");
		        	}
		        	if (!pass) {
		        		return;
		        	}
		        	var callback = {
		    				success: function(o){
		        			hideLoadingModalLayer();	
		        			var data = eval("("+o.responseText+")");
		        			if (data.info) {
		        			   YAHOO.ccm.container.throughAPPaymentPanel.hide();
		        			   alert(data.info);
		        			   PTOW.start();
		        			}
		        		},
		    				failure: showError
		    			};
		    			var submitParamer = "searchInvoiceVO.invoiceId="+$("#throughAPPaymentCloseInvoiceId").val()+ "&searchInvoiceVO.note=" + notes + "&searchInvoiceVO.paymentReferenceCode=" + remittance + "&searchInvoiceVO.paidDate=" + paidDate;
		    			showLoadingModalLayer();
		    			YAHOO.util.Connect.asyncRequest('POST' , "doThroughAPPaymentCloseInvoice.action" , callback , submitParamer )
		            }
		        }
//		        ,{
//		            text: "Cancel Invoice",
//		            handler: function(){
//		        	var notes = $("#throughAPPaymentCloseNote").val();
//		        	var remittance = $("#remittance").val();
//		        	var paidDate = $("#paidDate").val();
//		        	var pass = true;
//		        	if (notes.length < 10) {
//		        		 $("#throughAPPaymentCloseNote").removeClass("validation-passed").addClass("validation-failed");
//		                 pass = false;
//		        		} else {
//		    			$("#throughAPPaymentCloseNote").removeClass("validation-failed").addClass("validation-passed");
//		        		}
//		        	
//		        	if (paidDate.length > 0 && !transactionTabLC.panelInit.sp_checkDate(paidDate)) {
//		        		$("#paidDate").removeClass("validation-passed").addClass("validation-failed");
//		        		pass = false;
//		        	} else {
//		        		$("#paidDate").removeClass("validation-failed").addClass("validation-passed");
//		        	}
//		        	
//		        	if (!pass) {
//		        		return;
//		        	}
//		        	var callback = {
//		    				success: function(o){
//		        			hideLoadingModalLayer();	
//		        			var data = eval("("+o.responseText+")");
//		        			if (data.info) {
//		        			   YAHOO.ccm.container.throughAPPaymentPanel.hide();
//		        			   alert(data.info);
//		        			   PTOW.start();
//		        			}
//		        		},
//		    				failure: showError
//		    			};
//		    			var submitParamer = "searchInvoiceVO.invoiceId="+$("#throughAPPaymentCloseInvoiceId").val()+ "&searchInvoiceVO.note=" + notes + "&searchInvoiceVO.paymentReferenceCode=" + remittance + "&searchInvoiceVO.paidDate=" + paidDate+"&searchInvoiceVO.upPayment=true";
//		    			showLoadingModalLayer();
//		    			YAHOO.util.Connect.asyncRequest('POST' , "doCancelInvoice.action" , callback , submitParamer );
//		            }
//		          }
		        , {
		          text: "Cancel",
		          handler: function(){
			          this.cancel();
		          }
		        }]
		    });
			YAHOO.ccm.container.throughAPPaymentPanel.render();
//			YAHOO.ccm.container.tRegularCloseInvoicePanel = new YAHOO.widget.Dialog("tRegularCloseInvoicePanel", {
//		        width: "570px",
//		        fixedcenter: true,
//		        visible: false,
//		        constraintoviewport: true,
//		        modal: true,
//		        buttons: [{
//		            text: "Close Payment",
//		            handler: function(){
//		        	var notes = $("#tRegularCloseInvoiceCloseNote").val();
//		        	if (notes.length < 10) {
//		        		 $("#tRegularCloseInvoiceCloseNote").removeClass("validation-passed").addClass("validation-failed");
//		        		return;
//		        		}
//		        	var callback = {
//		    				success: function(o){
//		        			hideLoadingModalLayer();	
//		        			var data = eval("("+o.responseText+")");
//		        			if (data.info) {
//		        			   YAHOO.ccm.container.tRegularCloseInvoicePanel.hide();
//		        			   alert(data.info);
//		        			   PTOW.start();
//		        			}
//		        		},
//		    				failure: showError
//		    			};
//		    			var submitParamer = "searchInvoiceVO.invoiceId="+$("#tRegularCloseInvoiceCloseInvoiceId").val()+ "&searchInvoiceVO.note=" + notes;
//		    			showLoadingModalLayer();
//		    			YAHOO.util.Connect.asyncRequest('POST' , "doTRegularCloseInvoice.action" , callback , submitParamer )
//		            }
//		        }, {
//		          text: "Cancel",
//		          handler: function(){
//			          this.cancel();
//		          }
//		        }]
//		    });
//			YAHOO.ccm.container.tRegularCloseInvoicePanel.render();
			
			YAHOO.ccm.container.cancelInvoicePanel = new YAHOO.widget.Dialog("cancelInvoicePanel", {
		        width: "570px",
		        fixedcenter: true,
		        visible: false,
		        constraintoviewport: true,
		        modal: true,
		        buttons: [{
		            text: "Cancel Invoice",
		            handler: function(){
		        	var notes = $("#cancelInvoiceNote").val();

		        	var pass = true;
		        	if (notes.length < 10) {
		        		 $("#cancelInvoiceNote").removeClass("validation-passed").addClass("validation-failed");
		                 pass = false;
		        		} else {
		    			$("#cancelInvoiceNote").removeClass("validation-failed").addClass("validation-passed");
		        		}
		        	if (!pass) {
		        		return;
		        	}
		        	var callback = {
		    				success: function(o){
		        			hideLoadingModalLayer();	
		        			var data = eval("("+o.responseText+")");
		        			if (data.info) {
		        			   YAHOO.ccm.container.cancelInvoicePanel.hide();
		        			   alert(data.info);
		        			   PTOW.start();
		        			}
		        		},
		    				failure: showError
		    			};
		    			var submitParamer = "searchInvoiceVO.invoiceId="+$("#cancelInvoiceId").val()+ "&searchInvoiceVO.note=" + notes ;
		    			showLoadingModalLayer();
		    			YAHOO.util.Connect.asyncRequest('POST' , "doCancelInvoice.action" , callback , submitParamer )
		            }
		        }, {
		          text: "Cancel",
		          handler: function(){
			          this.cancel();
		          }
		        }]
		    });
			YAHOO.ccm.container.cancelInvoicePanel.render();
		},
		sp_checkDate : function (str){
			var req = new RegExp("^(?:(?:([0-9]{4}(-|\/)(?:(?:0?[1,3-9]|1[0-2])(-|\/)(?:29|30)|((?:0?[13578]|1[02])(-|\/)31)))|([0-9]{4}(-|\/)(?:0?[1-9]|1[0-2])(-|\/)(?:0?[1-9]|1\\d|2[0-8]))|(((?:(\\d\\d(?:0[48]|[2468][048]|[13579][26]))|(?:0[48]00|[2468][048]00|[13579][26]00))(-|\/)0?2(-|\/)29))))$");
			return req.test(str);
		}
	},
	
	pageInit : {
		createPage : function (){
			if(!window.PTOW){
				PTOW = new SANINCO.Page('_dataTable2',"PTOW",{
			    sortingField : "i.invoice_number",
			    sortingDirection : "desc",
				vo : "searchInvoiceVO",
				pageTable : "block",
			    totalPageUri : "doGetInvoiceByNumberSearchTotalPageNo.action",
			    dataUri : "doSearchPTOWInvoiceByINumber.action",
				paginationDiv : "_dataTable_page",
				recPerArray : [10,20,30,40,50,70,90,100],
			    cols : [
			            {   title : "Invoice Number",dataField: function(o){return "<a href=\"javascript:mark(\'viewInvoiceDetails.action?invoiceId="+o.id+"\')\">"+o.no+"</a>";},sort : "i.invoice_number", filtration : {name:"i.invoice_number",alias:"Invoice Number"}
						},{ title : "BAN",dataField: "ban",sort : "b.account_number", filtration : {name:"b.account_number",alias:"BAN"}
					    },{ title : "Vendor",dataField: "vendor",sort : "v.vendor_name", filtration : {name:"v.vendor_name",alias:"Vendor"}
						},{ title : "Invoice Date",dataField: "date",sort : "i.invoice_date", filtration : {name:"i.invoice_date",alias:"Invoice Date"}				
					    },{ title : "Invoice Status",dataField: "invoiceStatus",sort : "s.invoice_status_name", filtration : {name:"s.invoice_status_name",alias:"Invoice Status"}
					    },{ title : "invoice Current Due",dataField: "invoiceCurrentDue",sort : "if(i.invoice_current_due IS NULL, 0, i.invoice_current_due)", filtration : {name:"if(i.invoice_current_due IS NULL, 0, i.invoice_current_due)",alias:"invoice Current Due"}
					    },{ title : "Close",dataField: function(obj){return "<input type=\"button\" value=\"Close\" style=\"cursor:pointer\" onClick=\"transactionTabLC.pageInit.findInvoiceStatus(" + obj.id + ");\">";},sort : "s.invoice_status_name", filtration : {name:"s.invoice_status_name",alias:"Invoice Status"}
					    }
					]
			});
			}
			filter1= new SANINCO.Filter();
		    filter1.addEditeEvent(function(){PTOW.start();});
		    filter1.add('i.invoice_number', 'String');
		    filter1.add('v.vendor_name', 'String');
			filter1.add('b.account_number', 'String');
			filter1.add('i.invoice_date', 'date');
			filter1.add('s.invoice_status_name', 'String');
			filter1.add('if(i.invoice_current_due IS NULL, 0, i.invoice_current_due)', 'String');
			
			PTOW.setFilter(filter1);
		},
		findInvoiceStatus : function (id){
			transactionTabLC.pageInit.closeNotes();
			if (id != "") {
					var callback = {
						success: transactionTabLC.pageInit.closeInvoiceIsSuccess,
						failure: showError
					};
					var submitParamer = "searchInvoiceVO.invoiceId="+id;
					showLoadingModalLayer();
					YAHOO.util.Connect.asyncRequest('POST' , "doFindInvoiceStatus.action" , callback , submitParamer )	
			}
		},
		closeInvoiceIsSuccess : function (o){
			hideLoadingModalLayer();
			var data = eval("("+o.responseText+")");
			if (data.info) {
			   alert(data.info);
			} else if (data.cancel){
				$("#cancelInvoiceId").val(data.id);
				$("#cancelInvoiceNote").val("");
				$("#cancelInvoiceNumber").html(data.no);
				$("#cancelVendor").html(data.vendor);
				$("#cancelBan").html(data.ban);
				$("#cancelInvoiceStatus").html(data.invoiceStatus);
				$("#cancelInvoiceCurrentAmount").html(data.invoiceCurrentDue);
				YAHOO.ccm.container.cancelInvoicePanel.show();
			}else if (data.cancelOrClose) {
				$("#closeInvoiceId").val(data.id);
				$("#closeNote").val("");
				$("#closeInvoiceNumber").html(data.no);
				$("#closeVendor").html(data.vendor);
				$("#closeBan").html(data.ban);
				$("#closeInvoiceStatus").html(data.invoiceStatus);
				$("#closeInvoiceCurrentAmount").html(data.invoiceCurrentDue);
				YAHOO.ccm.container.closeInvoicePanel.show();
			} else if (data.cancelOrCloseAP) {
				$("#chooseTitle").html(data.choose);
				$("#throughAPPaymentCloseInvoiceId").val(data.id);
				$("#throughAPPaymentCloseNote").val("");
				$("#throughAPPaymentCloseInvoiceNumber").html(data.no);
				$("#throughAPPaymentCloseVendor").html(data.vendor);
				$("#throughAPPaymentCloseBan").html(data.ban);
				$("#throughAPPaymentCloseInvoiceStatus").html(data.invoiceStatus);
				$("#throughAPPaymentCloseInvoiceCurrentAmount").html(data.invoiceCurrentDue);
//				$("#tRegularCloseInvoiceCloseInvoiceId").val(data.id);
//				$("#tRegularCloseInvoiceCloseInvoiceNumber").html(data.no);
//				$("#tRegularCloseInvoiceCloseVendor").html(data.vendor);
//				$("#tRegularCloseInvoiceCloseBan").html(data.ban);
//				$("#tRegularCloseInvoiceCloseInvoiceStatus").html(data.invoiceStatus);
//				$("#tRegularCloseInvoiceCloseInvoiceCurrentAmount").html(data.invoiceCurrentDue);
				YAHOO.ccm.container.throughAPPaymentPanel.show();
			}
			
		},
		closeNotes : function (){
			$("#closeNote").val("");
			$("#remittance").val("");
			$("#paidDate").val("");
			$("#throughAPPaymentCloseNote").val("");
			$("#tRegularCloseInvoiceCloseNote").val("");
			$("input").removeClass("validation-failed").addClass("validation-passed");
			$("textarea").removeClass("validation-failed").addClass("validation-passed");
		}
	}
}

YAHOO.util.Event.onDOMReady(function(){
	transactionTabLC.panelInit.createPanel();
	transactionTabLC.pageInit.createPage();
});
