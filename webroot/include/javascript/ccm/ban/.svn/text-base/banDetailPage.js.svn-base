jQuery(function($){
	
	if($('#isApprove').val() == 'approve' || $('#isApprove').val() == 'reject'){
		$(".yui-nav").css("display","none"); 
		$("#banTitle").css("display","none"); 
	}else{
		$(".yui-nav").css("display","block"); 
		$("#banTitle").css("display","block"); 
	}
	$('#banDetail_frm_b_lineOfBusiness').change(function(v){
		//var ic_ = document.getElementById('banDetail_frm_b_invoiceChannel_id');
		//var if_ = document.getElementById('banDetail_frm_b_invoiceFormat_id');
//		var s_ = document.getElementById('banDetail_frm_b_scoaCodeType');
		//ic_.options.length = 0;
		//if_.options.length = 0;
//		s_.options.length = 0;
		//ic_.disabled = true;
		//if_.disabled = true;
		//add by mingyang.li 2013-11-05 start
		
		var lineOfBusiness = $('#banDetail_frm_b_lineOfBusiness').val();
		if(lineOfBusiness.indexOf('Power Supply') >= 0 ){
			$('#banDetail_frm_b_manualAdjustmentApprovalFlag').val('N');
			$('#banDetail_frm_b_maxApprovalLevel').val('1');
		}else{
//			if($('#banDetail_frm_b_id').val() == ''){
				$('#banDetail_frm_b_manualAdjustmentApprovalFlag').val('Y');
				$('#banDetail_frm_b_maxApprovalLevel').val('4');
//			}
		}
		banDetailPage_Json.starValidate($("#banDetail_frm_b_maxApprovalLevel").val());
		//add by mingyang.li 2013-11-05 end
//		s_.disabled = true;
//		if(v.target.value){
//			var callback = {
//		        success: function(o){
//					if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
//		                window.location.reload();
//		                return;
//		            }
//					var data = eval("("+o.responseText+")");
//					if(data.error){
//						alert("Error: " + data.error);
//						return;
//					}
//					//var channes = data.channeList;
//					var scoas = data.scoaList;
//					//var ic_ = document.getElementById('banDetail_frm_b_invoiceChannel_id');
//					var s_ = document.getElementById('banDetail_frm_b_scoaCodeType');
//					//ic_.options[0] = new Option("","");
//					s_.options[0] = new Option("","");
////					for(var i=0;i<channes.length;i++){
////						ic_.options[i+1] = new Option(channes[i].name,channes[i].id);
////					}
//					for(var k=0;k<scoas.length;k++){
//						s_.options[k+1] = new Option(scoas[k].name,scoas[k].id);
//					}
//					//ic_.disabled = false;
//					s_.disabled = false;
//				},
//		        failure: showError
//		    };
//			var data = "lineOfBussiness=" + v.target.value;
//		    YAHOO.util.Connect.asyncRequest("POST", "getInvoiceChannelAndFormatAndScoaJsonByLineOsBussiness.action", callback, data);
//		}
	});
	
	$('#banDetail_frm_b_invoiceChannel_id').change(function(v){
		var if_ = document.getElementById('banDetail_frm_b_invoiceFormat_id');
//		if_.selectedIndex = "";
//		if_.options.length = 0;
//		if_.disabled = true;
//		if(v.target.value){
//			var callback = {
//		        success: function(o){
//					if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
//		                window.location.reload();
//		                return;
//		            }
//					var data = eval("("+o.responseText+")");
//					if(data.error){
//						alert("Error: " + data.error);
//						return;
//					}
//					var formats = data.formatList;
//					var if_ = document.getElementById('banDetail_frm_b_invoiceFormat_id');
//					var invoiceFormat_value = $('#invoiceFormat_value').text();
//					if_.options[0] = new Option("","");
//					for(var i=0;i<formats.length;i++){
//						if_.options[i+1] = new Option(formats[i].name,formats[i].id);
//						if(invoiceFormat_value == formats[i].id){
//							if_.selectedIndex = i+1;
//							$('#invoiceFormat_value').text("");
//							continue;
//						}
//					}
//					if_.disabled = false;
//				},
//		        failure: showError
//		    };
//			//var data = "invoiceChannel=" + v.target.value+"&lineOfBussiness="+$('#banDetail_frm_b_lineOfBusiness').val();
//		    var data ="invoiceChannel=" + v.target.value+"";
//			YAHOO.util.Connect.asyncRequest("POST", "getInvoiceFormatsByLineOsBussinessAndChannel.action", callback, data);
//		}
	});
	
	// if update ban
	var lineOfBusiness_value = $("#lineOfBusiness_value").text();
	if(lineOfBusiness_value){
		var ops = document.getElementById("banDetail_frm_b_lineOfBusiness").options;
		for(var n=0; n<ops.length; n++){
			if(ops[n].value == lineOfBusiness_value){
				ops[n].selected = true;
				var ic_ = document.getElementById('banDetail_frm_b_invoiceChannel_id');
				var if_ = document.getElementById('banDetail_frm_b_invoiceFormat_id');
//				var s_ = document.getElementById('banDetail_frm_b_scoaCodeType');
				ic_.options.length = 0;
//				if_.options.length = 0;
//				s_.options.length = 0;
//				ic_.disabled = true;
//				if_.disabled = true;
//				s_.disabled = true;
				if(ops[n].value){
					var callback = {
				        success: function(o){
							if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
				                window.location.reload();
				                return;
				            }
							var data = eval("("+o.responseText+")");
							if(data.error){
								alert("Error: " + data.error);
								return;
							}
							var channes = data.channeList;
							var scoas = data.scoaList;
							var ic_ = document.getElementById('banDetail_frm_b_invoiceChannel_id');
//							var s_ = document.getElementById('banDetail_frm_b_scoaCodeType');
							ic_.options[0] = new Option("","");
//							s_.options[0] = new Option("","");
							var invoiceChannel_value = $("#invoiceChannel_value").text();
							var scoaCodeType_value = $("#scoaCodeType_value").text();
							for(var i=0;i<channes.length;i++){
								ic_.options[i+1] = new Option(channes[i].name,channes[i].id);
								if(channes[i].id == invoiceChannel_value){
									ic_.selectedIndex = i+1;
									$('#banDetail_frm_b_invoiceChannel_id').change();
									continue;
								}
							}
//							for(var k=0;k<scoas.length;k++){
//								s_.options[k+1] = new Option(scoas[k].name,scoas[k].id);
//								if(scoas[k].id == scoaCodeType_value){
//									s_.selectedIndex = k+1;
//									continue;
//								}
//							}
////							ic_.disabled = false;
//							s_.disabled = false;
						},
				        failure: showError
				    };
					var data = "lineOfBussiness=" + ops[n].value;
				    YAHOO.util.Connect.asyncRequest("POST", "getInvoiceChannelAndFormatAndScoaJsonByLineOsBussiness.action", callback, data);
				}
				break;
			}
		}
	}
});
var isMustWrite = true;
var auf=3;
//var banStatusId;
// @Auchor chao.Liu (Optimization of complete by xinyu.Liu)
var banDetailPage_Json = { 
	url : {
		getContactByVendorId : "getContactByVendorId.action",
		serachAccountNumber : "serachAccountNumber.action"
	},
	serachAccountNumber : function(isSubmit,isApprove){
		if(!banDetailPage_Json.saveValidate())return;
		var callback = {
	        success: function(o){
				if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
					searchMyWorkspace();
					searchBackup();
					searchMyTeam();
					if(isApprove == 'approve'){
						tems.actions.invoicesSearch.searchBanApprove($("[name='bvo.uid']").val(),'0');
					}else if(isApprove == 'reject'){
						tems.actions.invoicesSearch.searchBanReject($("[name='bvo.uid']").val(),'0');
					}else{
						window.location.reload();
					}
	                return;
	            }
				var data = eval("("+o.responseText+")");
				if(data.error){
					hideLoadingImg();
					alert("Error: " + data.error);
					return;
				}
				if(data.count != 0){
					alert("Account Number already exists !");
					removeClassName('banDetail_frm_b_accountNumber','validation-passed');
					addClassName('banDetail_frm_b_accountNumber','validation-failed');
					return false;
				}else{
					removeClassName('banDetail_frm_b_accountNumber','validation-failed');
					addClassName('banDetail_frm_b_accountNumber','validation-passed');
					document.getElementById('banDetail_frm').submit();
					infoData = $("#banDetail_frm").serializeArray();
				}
			},
	        failure: showError
	    };
		var getInvoiceData = "bvo.level1ApprovalFlag="+$('#banDetail_frm_b_level1_approval_flag').val()+"&bvo.banId=" + $('#banDetail_frm_b_id').val() + "&bvo.accountNumber=" + $('#banDetail_frm_b_accountNumber').val();
	    YAHOO.util.Connect.asyncRequest("POST", banDetailPage_Json.url.serachAccountNumber, callback, getInvoiceData);
	},
	onDOMValidate : function (){
		// Required Options Validate
		try{
			$.each($(".iShowStar"),function (i,n){
				if($("[name='b.banStatus.id']").val() == 1 || $("[name='b.banStatus.id']").val() == 4 || $("[name='b.banStatus.id']").val() == 5){
					n.innerHTML = "*"
						isMustWrite = true;
				}else{
					n.innerHTML = ""
						isMustWrite = false;
				}
			});
			if($("[name='b.banStatus.id']").val() == 1 || $("[name='b.banStatus.id']").val() == 4 || $("[name='b.banStatus.id']").val() == 5){
				banDetailPage_Json.starValidate($("#banDetail_frm_b_maxApprovalLevel").val());
			}
		}catch(e){
			
		}
	},
	
	onMibFlagChange : function (){
		// Required Options Validate
//		mib_flag影响autopay_flag逻辑去掉
//		if($("[name='b.mibFlag']").val() == "Y"){
//			$("[name='b.autopayFlag']").val('N');
//		}
	},
	
	onAutopayChange : function (){
		
//		mib_flag影响autopay_flag逻辑去掉
//		if($("[name='b.autopayFlag']").val() == "Y"){
//			$("#AutoPayTR").show();
//		}else{
//			$("#AutoPayTR").hide();
//		}
		// Required Options Validate
//		if($("[name='b.autopayFlag']").val() == "Y"){
//			if($("[name='b.mibFlag']").val() == "Y"){
//				$("[name='b.autopayFlag']").val('N');
//				if(!initFlag){
//					alert("Multiple invoices BAN flag is 'Y' !");
//				}
//			}else{
//				$("#AutoPayTR").show();
//			}
//		}else{
//			$("#AutoPayTR").hide();
//		}
	},
	
//	changeValidate : function (){
//		// Required Options Validate
//		try{
//			$.each($(".iShowStar"),function (i,n){
//				if($("[name='b.banStatus.id']").val() == 1){
//					n.innerHTML = "*"
//					isMustWrite = true;
//				}else if(banStatusId == null){
//					n.innerHTML = ""
//					isMustWrite = false;
//				}else if(banStatusId == 2 && $("[name='b.banStatus.id']").val() == 1){
//					n.innerHTML = "*"
//					isMustWrite = true;
//				}else if(banStatusId == 3 && $("[name='b.banStatus.id']").val() == 1){
//					n.innerHTML = "*"
//					isMustWrite = true;
//				}else if(banStatusId == 2 && $("[name='b.banStatus.id']").val() == 3){
//					n.innerHTML = ""
//					isMustWrite = false;
//				}else if(banStatusId == 3 && $("[name='b.banStatus.id']").val() == 2){
//					n.innerHTML = ""
//					isMustWrite = false;
//				}else if(banStatusId == $("[name='b.banStatus.id']").val()){
//					n.innerHTML = ""
//					isMustWrite = false;
//				}
//			});
//		}catch(e){
//			
//		}
//	},
	saveValidate : function (){ // Tie to Jquery 1.4.2
		var boo = true;
		var style = {
			ok : function (id){
				$("#"+id).addClass("validation-passed").removeClass("validation-failed");
			},
			error : function (id){
				$("#"+id).addClass("validation-failed").removeClass("validation-passed");
			}
		}
		// Required Options Validate
		try{
			
			$.each($(".mustWrite"),function (i,n){
				if(!n.value && n.id && ($("[name='b.banStatus.id']").val() == '1' || $("[name='b.banStatus.id']").val() == 4 || $("[name='b.banStatus.id']").val() == 5) && isMustWrite){
					style.error(n.id);
					boo = false;
				}else{
					style.ok(n.id);
				}
			});
			
			if(!$("[name='b.banStatus.id']").val()){
				$("[name='b.banStatus.id']").addClass("validation-failed").removeClass("validation-passed");
				boo = false;
			}else{
				$("[name='b.banStatus.id']").addClass("validation-passed").removeClass("validation-failed");
			}
			
			if($("#bdpVendorListDiv_input").val() == "All"){
				style.error("bdpVendorListDiv_input");
				boo = false;
			}else{
				style.ok("bdpVendorListDiv_input");
			}
			
		}catch(e){
			
		}
		// Approver Validate 
		var approver1 = $("#banDetail_frm_b_approver1Id").val();			
		var approver2 = $("#banDetail_frm_b_approver2Id").val();			
		var approver3 = $("#banDetail_frm_b_approver3Id").val();
		var approver4 = $("#banDetail_frm_b_approver4Id").val();
		if(approver1 && approver1 == approver2){
			style.error("banDetail_frm_b_approver1Id");
			style.error("banDetail_frm_b_approver2Id");
			alert("Approver1,Approver2 and Approver 3 can not be same person!");
			boo = false
		}else if(approver2 && approver1 && approver1 == approver3){
			style.error("banDetail_frm_b_approver1Id");
			style.error("banDetail_frm_b_approver3Id");
			alert("Approver1,Approver2 and Approver 3 can not be same person!");
			boo = false
		}else if(approver3 && approver2 == approver3){
			style.error("banDetail_frm_b_approver2Id");
			style.error("banDetail_frm_b_approver3Id");
			alert("Approver1,Approver2 and Approver 3 can not be same person!");
			boo = false
		}else if(approver4 && approver4 == approver3){
			style.error("banDetail_frm_b_approver3Id");
			style.error("banDetail_frm_b_approver4Id");
			alert("Approver 3 and Approver 4 can not be same person!");
			boo = false
		}else if(approver4 && approver4 == approver2){
			style.error("banDetail_frm_b_approver2Id");
			style.error("banDetail_frm_b_approver4Id");
			alert("Approver 2 and Approver 4 can not be same person!");
			boo = false
		}else if(approver4 && approver4 == approver1){
			style.error("banDetail_frm_b_approver1Id");
			style.error("banDetail_frm_b_approver4Id");
			alert("Approver 1 and Approver 4 can not be same person!");
			boo = false
		}	
		// Optional Options Validate
		var optionalOptions = function (){
			var d = /^[\d]{0,}$/; // Req
			var ooValidate = function (o){ 
				if(!d.exec(o.val())){
					style.error(o.attr("id"));
					return false;
				}else{
					style.ok(o.attr("id"));
					return true;
				}
			}
			var scoaValidate = function (o,len){
				if(ooValidate(o)){
					if(o.val() != "" && o.val().length != len){
						style.error(o.attr("id"));
						boo = false;
					}else{
						style.ok(o.attr("id"));
					}
				}else{
					boo = false;
				}
			}
			// Must be a number
			var oo1 = $("#banDetail_frm_b_usageBackbillMonths");
			ooValidate(oo1)?"":boo=false;
			// The number must be 1-31
			// Modified The number must be 0-31 Date: 10/11/08 AM
			var oo2 = $("#banDetail_frm_b_billDay");
			if(ooValidate(oo2)){
				if(oo2.val() != "" && (oo2.val()<0 || oo2.val()>31)){
					style.error(oo2.attr("id"));
					boo = false;
				}else{
					style.ok(oo2.attr("id"));
				}
			}else{
				boo = false;
			}
			// Must be a number, but to save a string (3)
			var oo3 = $("#banDetail_frm_b_company");
			scoaValidate(oo3,3);
			// Must be a number, but to save a string (4, 0, can be four, for example, 0000)
			var oo4 = $("#banDetail_frm_b_location");
			scoaValidate(oo4,4);
			// Must be a number, but to save a string (4, 0, can be four, for example, 0000)
			var oo5 = $("#banDetail_frm_b_department");
			scoaValidate(oo5,4);
			// Must be a number, but to save a string (3, 0, can be three, for example, 000)
			var oo6 = $("#banDetail_frm_b_channel");
			scoaValidate(oo6,3);
			var oo7 = $("#banDetail_frm_b_daysToDue");
			if(!ooValidate(oo7))
				boo = false;
			
		}
		optionalOptions();

		var if_ = $("#banDetail_frm_b_invoiceFormat_id").val();
		if(if_ == 6 || if_ == 9 || if_ == 11 || if_ == 12 || if_ == 15){
			if(!$("#banDetail_frm_b_originalAccountNumber").val()){
				style.error("banDetail_frm_b_originalAccountNumber");
				boo = false;
			}else{
				style.ok("banDetail_frm_b_originalAccountNumber");
			}
		}
       
		return boo;
	},
	cancel : function (){
		if($("#banDetail_frm_b_id").val()){
			window.location = location;
		}else{
			common_Json.clearForm_YUI("banDetail_frm");
		}
	},
	banInfoLiTab :function (tab){
		
		document.getElementById("banTab").style.display="none";
		document.getElementById("banTariff").style.display="none";
		document.getElementById("banExemptionCertificate").style.display="none";
		document.getElementById("banInfoLi").title="";
		document.getElementById("banInfoLi").className="";
		document.getElementById("banTariffLi").title="";
		document.getElementById("banTariffLi").className="";
		document.getElementById("banExemptionCertificateLi").title="";
		document.getElementById("banExemptionCertificateLi").className="";
		
		if(tab == "A"){
			document.getElementById("banInfoLi").title="active";
			document.getElementById("banInfoLi").className="selected";
			document.getElementById("banTab").style.display="block";
		}else if (tab == "B"){
			document.getElementById("banTariffLi").title="active";
			document.getElementById("banTariffLi").className="selected";
			document.getElementById("banTariff").style.display="block";
		}else if (tab == "C"){
			document.getElementById("banExemptionCertificateLi").title="active";
			document.getElementById("banExemptionCertificateLi").className="selected";
			document.getElementById("banExemptionCertificate").style.display="block";
		}
		setTimeout(function(){
			document.body.scrollTop = 0;
			$("#main").scrollTop(0);
		},500);
				
	},
	
	popupUploadBanTariffWindow:function() {
		// clean upload form
		$(".upload_text").val("");
		$("#uploadNotes").val("");
		clearFormUploadFiles("uploadForm");
		$("#main").css("overflow-x","visible");
		YAHOO.ccm.container.uploadTariffWindow.show();
	},
	
	popupUploadBanExemptionCertificateWindow:function() {
		// clean upload form
		$(".upload_exemption_certificate_text").val("");
		$("#uploadExemptionCertificateNotes").val("");
		$("#banExemption_DateStartsOn").val("");
		$("#banExemption_DateEndsOn").val(""); 
		$('.word-number').html('500');
		$("#main").css("overflow-x","visible");
		removeClassName("banExemption_DateStartsOn",'validation-failed');
		addClassName("banExemption_DateStartsOn",'validation-passed');
		removeClassName("banExemption_DateEndsOn",'validation-failed');
		addClassName("banExemption_DateEndsOn",'validation-passed');

		var textarea = $('#uploadExemptionCertificateNotes');
		var numberElement = $('.word-number');
		banDetailPage_Json.calculateWordLimit(textarea, numberElement);
		clearFormUploadFiles("exemptionCertificateUploadForm");
		YAHOO.ccm.container.uploadExemptionCertificateWindow.show();
	},
	
	calculateWordLimit:function ( textElement, counterElement ) {

		textElement.keyup(check_keyup).bind('paste', function () {
			setTimeout(check_keyup, 50);
		});
		
		function check_keyup() {
			var num = 500 - textElement.val().length;
			counterElement.html(num);
		}
	},
	
	validateUploadForm:function () {
		var FileNum = $("#addFile").find("input[class = upload_text]");		
		var flag = 1;
		for(i = 0;i<FileNum.length;i++){
			if(FileNum[i].value){
				flag = 0;
			}
		}
		if (flag == 1) {			
			alert("Please at least upload one file.");
			return false;
		}else{
			return uploadFileValidate('addFile',20480000);
		}
	},
	submitUploadForm:function () {
		
		if($("[name='banId']").val() == "0" || $("[name='banId']").val() == ""){
			alert("Please select a BAN!");  
			return false;  
		}
		
		if (banDetailPage_Json.validateUploadForm()) {
			var uf = document.getElementById("uploadForm");
			uf.submit();
			YAHOO.ccm.container.uploadTariffWindow.hide();
		}
	},
	cancelUploadForm:function () {
		YAHOO.ccm.container.uploadTariffWindow.hide();
	},
	
	addUploadForm:function (){
		auf++;
		var string = "<div id =\"ContractFile"+auf+"\" style=\"padding:3px 0 0 0;height: 19px;\" ><div style=\"float:left;\">"		
	                  +"<input style=\"height:19px;width:170px;\" type='text' id=\"_upload_text_attachment_0"+auf+"\" disabled=\"disabled\" class=\"upload_text\">" 
		               +"</div><div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"		
		              + "<input onchange=\"document.getElementById('_upload_text_attachment_0"+auf+"').value=this.value;\" class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
		              + "<input style=\"height:19px;width:65px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/></span></div>"	   						
	                  + "<div class=\"approveicon\" onclick=\"deletefile('ContractFile"+auf+"');\"></div><div class=\"clear\" ></div></div>";
		

		$("#addFile").append(string);
	},
	validateUploadExemption:function () {
		var FileNum = $("#addExemptionCertificateFile").find("input[class = upload_exemption_certificate_text]");		
		var flag = 1;
		for(i = 0;i<FileNum.length;i++){
			if(FileNum[i].value){
				flag = 0;
			}
		}
		if (flag == 1) {			
			alert("Please at least upload one file.");
			return false;
		}else{
			return uploadFileValidate("addExemptionCertificateFile",20480000);
		}
	},
	submitUploadExemption:function () {
		 var beginDate=$("#banExemption_DateStartsOn").val();  
		 var endDate=$("#banExemption_DateEndsOn").val();  

		 if(beginDate==""||endDate=="")  
		 {  
		    alert("Term Start Date and Term End Date cannot be empty!");  
		    return false;  
		 }
		 if(!banDetailPage_Json.judgDate('exemptionCertificateUploadForm'))  
		 {  
		    return false;  
		 }
		 if($("[name='banId']").val() == "0" || $("[name='banId']").val() == ""){
			 alert("Please select a BAN!");  
			 return false;  
		 }
		 if (banDetailPage_Json.validateUploadExemption()) {
			var uf = document.getElementById("exemptionCertificateUploadForm");
			uf.submit();
			showLoadingModalLayer();
			YAHOO.ccm.container.uploadExemptionCertificateWindow.hide();
		 }
	},
	cancelUploadExemption:function () {
		removeClassName("updateBanExemption_DateStartsOn",'validation-failed');
		addClassName("updateBanExemption_DateStartsOn",'validation-passed');
		removeClassName("updateBanExemption_DateEndsOn",'validation-failed');
		addClassName("updateBanExemption_DateEndsOn",'validation-passed');
		YAHOO.ccm.container.uploadExemptionCertificateWindow.hide();
	},
	cancelUploadExemptionTime:function () {
		removeClassName("updateBanExemption_DateStartsOn",'validation-failed');
		addClassName("updateBanExemption_DateStartsOn",'validation-passed');
		removeClassName("updateBanExemption_DateEndsOn",'validation-failed');
		addClassName("updateBanExemption_DateEndsOn",'validation-passed');
		YAHOO.ccm.container.uploadExemptionTimeWindow.hide();
	},
	cancelRejectWindow:function () {
		$("[name='rejectNotes']").val("");
		YAHOO.ccm.container.RejectNoteWindow.hide();
	},
	saveUploadExemptionTime:function () {

		 if(!banDetailPage_Json.judgDate('uploadExemptionTimeWindow')){  
		    return false;  
		 }
		
		 var callback = {
				success: function(o){
			        if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
			           	window.location.reload();
				        return;
			        }
			        var data = eval("("+o.responseText+")");
			        if(data.error){
				        alert(data.error);
				        return;
			        }
			        if(data.success){
			        	 banExemptionCertificatePage.reload();
					     YAHOO.ccm.container.uploadExemptionTimeWindow.hide();
			        }
			
			      
				},
				failure:showError
		 };
		 var data = "banExemptionId="+$("#updateBanExemptionId").val()+"&termStartDate="+$("#updateBanExemption_DateStartsOn").val()+"&termEndDate="+$("#updateBanExemption_DateEndsOn").val();
		 YAHOO.util.Connect.asyncRequest("POST","doUploadExemption.action", callback, data);
			
			
	},
	addUploadExemption:function (){
		auf++;
		var string = "<div id =\"exemptionFile"+auf+"\" style=\"padding:3px 0 0 0;height: 19px;\" ><div style=\"float:left;\">"		
	                  +"<input style=\"height:19px;width:200px;\" type='text' id=\"_upload_exemption_attachment_0"+auf+"\" disabled=\"disabled\" class=\"upload_exemption_certificate_text\">" 
		               +"</div><div style=\"float:left; margin:0 0 0 2px\"><span class=\"ccm_upload_img\">"		
		              + "<input onchange=\"document.getElementById('_upload_exemption_attachment_0"+auf+"').value=this.value;\" class=\"ccm_file\" type=\"file\" name=\"uploads\" size=\"1\" />"
		              + "<input style=\"height:19px;width:65px;position:absolute;right:0px;top:0px;\" type=\"button\" value=\"Browse...\"/></span></div>"	   						
	                  + "<div class=\"approveicon\" onclick=\"deletefile('exemptionFile"+auf+"');\"></div><div class=\"clear\" ></div></div>";
		

		$("#addExemptionCertificateFile").append(string);
	},
	
	
	judgDate:function (id){
		var nodes = YAHOO.util.Selector.query(".validate-date-ca",id);
		var vbo = true;
		var bo = true;
		
        for(var i = 0; i<nodes.length; i++){
			
			if(!isValidDate(nodes[i].value) || nodes[i].value=="") {
				removeClassName(nodes[i].id,'validation-passed');
				addClassName(nodes[i].id,'validation-failed');
				vbo = false;		
			}else{
				removeClassName(nodes[i].id,'validation-failed');
				addClassName(nodes[i].id,'validation-passed');
			}
        }
        if(vbo) {
        	for(var d = 0; d<nodes.length; d++){

    			if(d+1 < nodes.length){
    				if((nodes[d].value!="") && (nodes[d+1].value!="")){
    					if(!correct(nodes[d].value,nodes[d+1].value)){
    						removeClassName(nodes[d].id,'validation-passed');
    						addClassName(nodes[d].id,'validation-failed');
    						removeClassName(nodes[d+1].id,'validation-passed');
    						addClassName(nodes[d+1].id,'validation-failed');
    						bo = false;
    					}
    				}
    			}
    		}
        }else{
        	return vbo;
        }
		
		return bo;
	},
	
	searchBanTraff:function (theValue){
		tiffPage = new SANINCO.Page("BanTarrfTable","tiffPage",{
		      sortingField : "tariff_link.created_timestamp",
		      recordText: '', 
		      sortingDirection : "desc",
		      vo : "cvo",
		      paginationDiv: "BanTarrf_pageTable",
		      recPerArray : [5,10,15,20,30],
		      dataUri : "doSearchTarrf.action",
		      totalPageUri: "doTariffsTotalPageNo.action",
		      cols : [
			      {
			          title : "Tariff Name",
			          dataField: "tariff_name" ,
			          sort : "tariff_link.tariff_name"
			      },{
			          title : "Created Time",
			          dataField: "created_timestamp",
			          sort : "tariff_link.created_timestamp"
			      },{
			          title : "Modified Time",
			          dataField: "modified_timestamp" ,
			          sort : "tariff_link.modified_timestamp"
			      },{
			          title : "Notes",
			          dataField: "notes" ,
			          sort : "tariff_link.notes"
			      },{
			          title : "Delete",
			          dataField: function(obj){
		                return "<img  src=\"include/images/delete.png\"  style=\"cursor: pointer;\"  onClick=\"banDetailPage_Json.deleteTariff('" + obj.id + "');\">";
		              },
			          width: "5%",
			          textAlign: "center"
			      },{
			          title : "File",
			          dataField:function(obj){
		                return "<img  src=\"include/images/download1.gif\"  style=\"cursor: pointer;\"  onClick=\"banDetailPage_Json.downloadFile(" + "'" + obj.tariff_name + "','" + obj.tariff_path + "'" + ");\">";
		            }
			      }
		      ]
		  });
		
		  tiffPage.voParam = {
			        ban : theValue
			    };
		  tiffPage.start();
	},	
    searchBanExemptionCertificate:function (theValue){
		banExemptionCertificatePage = new SANINCO.Page("banExemptionCertificateTable","banExemptionCertificatePage",{
		      sortingField : "ban_exemption.created_timestamp",
		      recordText: '', 
		      sortingDirection : "desc",
		      vo : "cvo",
		      paginationDiv: "BanExemptionCertificate_pageTable",
		      recPerArray : [5,10,15,20,30],
		      dataUri : "doSearchBanExemptionCertificate.action",
		      totalPageUri: "doBanExemptionCertificateTotalPageNo.action",
		      cols : [
			      {
			          title : "File Name",
			          dataField: function(obj){
					  return "<div style=\"white-space: pre-wrap;width: 135px;\">"+obj.certificate_name+"</div>"
				     },
			          sort : "ban_exemption.certificate_name"
			      },{
			          title : "Created Time",
			          dataField: "created_timestamp",
			          sort : "ban_exemption.created_timestamp"
			      },{
			          title : "Modified Time",
			          dataField: "modified_timestamp" ,
			          sort : "ban_exemption.modified_timestamp"
			      },{
			          title : "Notes",
			          dataField: function(obj){
					  return "<div style=\"white-space: pre-wrap;width: 450px;\">"+obj.notes+"</div>"
				     },
			          sort : "ban_exemption.notes"
			      },{
			    	  title : "Term Start Date",
			          dataField: "term_start_date" ,
			          sort : "ban_exemption.term_start_date"
			      },{
			    	  title : "Term End Date",
			          dataField: "term_end_date" ,
			          sort : "ban_exemption.term_end_date"
			      },{
			    	  title : "Edit",
			    	  dataField: function(obj){
	              		return "<img  src=\"include/images/Edit-icon-s.png\" onclick=\"banDetailPage_Json.eidtBanExemptionCertificate('" + obj.id + "');\" />";
	        		  },
	        		  width: "5%",
	        
	               },{ 
			          title : "Delete",
			          dataField: function(obj){
		                return "<img  src=\"include/images/delete.png\"  style=\"cursor: pointer;\"  onClick=\"banDetailPage_Json.deleteBanExemptionCertificate('" + obj.id + "');\">";
		              },
			          width: "5%",
			          textAlign: "center"
			      },{
			          title : "File",
			          dataField:function(obj){
		                return "<img  src=\"include/images/download1.gif\"  style=\"cursor: pointer;\"  onClick=\"banDetailPage_Json.downloadFile(" + "'" + obj.certificate_name + "','" + obj.file_path + "'" + ");\">";
		            }
			      }
		      ]
		  });
		
		banExemptionCertificatePage.voParam = {
			        ban : theValue
			    };
		banExemptionCertificatePage.start();
	},
	
	deleteTariff:function (tariffLinkId) {
		if(!confirm("Are you sure you want to delete this attachmenet?")){return false;}
		var callback = {
			success: function(o){
				tiffPage.reload();
			},
			failure:showError
		};
		var data = "tariffLink.id="+tariffLinkId;
		YAHOO.util.Connect.asyncRequest("POST","deleteTariffOfVendorCircuit.action", callback, data);
	},
	deleteBanExemptionCertificate:function (banExemptionId) {
		if(!confirm("Are you sure you want to delete this attachmenet?")){return false;}
		var callback = {
			success: function(o){
			banExemptionCertificatePage.reload();
			},
			failure:showError
		};
		var data = "banExemptionId="+banExemptionId;
		YAHOO.util.Connect.asyncRequest("POST","deleteBanExemptionCertificate.action", callback, data);
	},
	eidtBanExemptionCertificate:function (banExemptionId) {
		$("#updateBanExemption_DateStartsOn").val("");
		$("#updateBanExemption_DateEndsOn").val(""); 
		$("#updateBanExemptionId").val(banExemptionId);
		
		removeClassName("updateBanExemption_DateStartsOn",'validation-failed');
		addClassName("updateBanExemption_DateStartsOn",'validation-passed');
		removeClassName("updateBanExemption_DateEndsOn",'validation-failed');
		addClassName("updateBanExemption_DateEndsOn",'validation-passed');
		
		YAHOO.ccm.container.uploadExemptionTimeWindow.show();
	},
	saveOrUpdate : function (){
		if($("#banDetail_frm_b_id").val()){
			$("#saveOrUpdateMessageH3").text("BAN Info");
			$("#btnClearOrCancle").hide();
		}else{
			$("#saveOrUpdateMessageH3").text("Create New BAN");
			$("#btnClearOrCancle").show();
			$("#btnClearOrCancle").val("Clear");
		}
	},
	downloadFile:function(name,path){
		var df = document.getElementById("downloadForm");
		df.filePath.value = path;
		df.fileName.value = name;
		df.submit();
	},
	selectVendorContact : function (vid){
		function start(){
			var callback = {
				success: end,
				failure: showError
			};
			
			var starParamer = "bvo.vendorId="+ccmEscape(vid);
			if($('#banDetail_frm_b_id').val()){
				starParamer += "&&bvo.banId="+ccmEscape($("#banDetail_frm_b_id").val());
			}
			YAHOO.util.Connect.asyncRequest('POST' , banDetailPage_Json.url.getContactByVendorId , callback , starParamer )
			
		}
		function end(o){
			if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
				window.location.reload();
				return;
			}
			var data = eval("("+o.responseText+")");
			if(data.error){
				alert(data.error);
				return;
			}
			var cc = $(".Contact");
			for(var j=0;j<cc.length;j++){
				cc[j].options.length = 0;
			}
			cc.append('<option value=""></option>');
			$.each(data,function (i,n){
				cc.append('<option value="'+data[i].id+'">'+data[i].value+'</option>');
			});
			if(banDetailPage_Json.contactArr){
				$.each(banDetailPage_Json.contactArr,function (i,n){
					$("#"+n.id).val(n.value);
				})
			}
			banDetailPage_Json.contactArr = null;
			banDetailPage_Json.onDOMValidate();
			banDetailPage_Json.starValidate($("#banDetail_frm_b_maxApprovalLevel").val());
		}
		start();
	},
	scoaValidate : function (o,len){
		var req = /^[\d]*$/; 
		var str = "";
		$.each(o.value.split(""),function (i,n){
			if (req.exec(n)) {
				str += n;
			}
		});
		o.value = str;
		if(o.value && o.value.length != len){
			o.title = "The length of the SCOA is not enough";
		}else{
			o.title = "That right";
		}
	},
	itemValidate : function (o,len){
		var approval = $("#maxApprovalLevel").val()*1;
		var inputapproval=$("#banDetail_frm_b_maxApprovalLevel").val()*1;
		if(inputapproval == 0){
			if(confirm("Min Approval Level is 1.")){
				$("#banDetail_frm_b_maxApprovalLevel").val("1");
			}
		}else if(inputapproval>approval){
			if(confirm("Max Approval Level is "+approval+".")){
				$("#banDetail_frm_b_maxApprovalLevel").val(approval);
			}
		}
		var req = /^[\d]*$/; 
		var str = "";
		$.each(o.value.split(""),function (i,n){
			if (req.exec(n)) {
				str += n;
			}
		});
		o.value = str;
		if(o.value && o.value.length != len){
			o.title = "The length of the SCOA is not enough";
		}else{
			o.title = "That right";
		}
		banDetailPage_Json.starValidate(o.value);
	},
	starValidate : function (value){
		for(var i=0;i<4;i=i+1){
			
			if(i>=value){
				
				$("#banDetail_frm_b_approver"+(i+1)+"Id").toggleClass("mustWrite",false);
				$("#approver"+(i+1)+"_star").toggleClass("iShowStar",false);
				$("#approver"+(i+1)+"_star").html("");
			}else{
				if($("[name='b.banStatus.id']").val() == "1" || $("[name='b.banStatus.id']").val() == 4 || $("[name='b.banStatus.id']").val() == 5){
					$("#banDetail_frm_b_approver"+(i+1)+"Id").toggleClass("mustWrite",true);
					$("#approver"+(i+1)+"_star").toggleClass("iShowStar",true);
					$("#approver"+(i+1)+"_star").html("*");
				}else {
					$("#banDetail_frm_b_approver"+(i+1)+"Id").toggleClass("mustWrite",false);
					$("#approver"+(i+1)+"_star").toggleClass("iShowStar",false);
					$("#approver"+(i+1)+"_star").html("");
				}
				
			}
		}

	},
	contactArr : []
}

YAHOO.util.Event.onDOMReady(function () {
	banDetailPage_Json.saveOrUpdate();
//	if($("[name='b.banStatus.id']").val()){
//		banStatusId = $("[name='b.banStatus.id']").val();
//	}
	if(!$("[name='b.daysToDue']").val()){
		$("[name='b.daysToDue']").val(30);
	}
	if($("#banDetail_frm_b_vendor_id").val()){
		banDetailPage_Json.selectVendorContact($("#banDetail_frm_b_vendor_id").val());
	}
	if($('#banDetail_frm_b_id').val()){
		banDetailPage_Json.searchBanTraff($('#banDetail_frm_b_id').val());
		$("[name='banId']").val($('#banDetail_frm_b_id').val());
		banDetailPage_Json.searchBanExemptionCertificate($('#banDetail_frm_b_id').val());
	}
	
	YAHOO.ccm.container.uploadTariffWindow = new YAHOO.widget.Dialog("uploadTariffWindow", 
			{ width : "35em",
			  fixedcenter : true,
			  visible : false, 
//			  modal : true,
			  constraintoviewport : true
			});
	YAHOO.ccm.container.uploadTariffWindow.render();
	
	YAHOO.ccm.container.uploadExemptionCertificateWindow = new YAHOO.widget.Dialog("uploadExemptionCertificateWindow", 
			{ width : "435px",
		      fixedcenter : true,
		      visible : false, 
		      modal : true,
		      constraintoviewport : true
			});
	YAHOO.ccm.container.uploadExemptionCertificateWindow.render();
	
	YAHOO.ccm.container.uploadExemptionTimeWindow = new YAHOO.widget.Dialog("uploadExemptionTimeWindow", 
			{ width : "435px",
		      fixedcenter : true,
		      visible : false, 
		      modal : true,
		      constraintoviewport : true
			});
	YAHOO.ccm.container.uploadExemptionTimeWindow.render();
	
	YAHOO.ccm.container.RejectNoteWindow = new YAHOO.widget.Dialog("RejectNoteWindow", {
        width: "37em",
        fixedcenter: true,
        visible: false,
    	modal : true,
        constraintoviewport: true
    });
    YAHOO.ccm.container.RejectNoteWindow.render();
	

	banDetailPage_Json.starValidate($("#banDetail_frm_b_maxApprovalLevel").val());
//	banDetailPage_Json.onAutopayChange();
});

activeIndexClick = function(index){
	showLoadingModalLayer();
//	window.location.href = "searchAdminAction.action?adminTabActiveIndex=" + index;
	var uri = "searchAdminAction.action?adminTabActiveIndex=" + index;
	windowLocationHref(uri);
};


doValidationAccountExis = function(str){
	var callback = {
		success: function(o){
			if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
				window.location.reload();
				return;
			}
			var data = eval("("+o.responseText+")");
			if(data.error){
				alert(data.error);
				return;
			}
			if(data.data){
				$("#doValidationAccountExisResultDiv").attr("color","green").text("Authentication is successful");
			}else{
				$("#doValidationAccountExisResultDiv").attr("color","red").text("already exists");
			}
			setTimeout(function(){
				$("#doValidationAccountExisResultDiv").text("");
			},3000);
		},
		failure: showError
	};
	var starParamer = "accNoName="+str;
	YAHOO.util.Connect.asyncRequest('POST' , "doValidationAccountExis.action" , callback , starParamer );
}

function judgesubmitForm() {
    var form = $("#banDetail_frm").serializeArray();
    for (var i = 0; i < form.length; i++) {
    	var element = form[i];
    	for(var j = 0; j < infoData.length; j++){
    		var initElement = infoData[j];
    		if(element["name"] == initElement["name"]){
    			if(element["value"] == initElement["value"]){
    				break;
    			}else{
    				return true;
    			}
    		}
    	}
    }
    return false;
}
