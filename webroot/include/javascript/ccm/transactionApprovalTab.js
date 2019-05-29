//judgement Rights 

var initTransactionApprovalTabSearch = function(){
    var G = function(id){
        return document.getElementById(id);
    };
    approveSelectUserDiv = new USERLIST.Page("approveSelectUserDiv",{width:"340px"});
    
	if(!window.judgementRights_transactionTab){
		/*
		judgementRights_transactionTab = function(){
		
			if(currentAssignmentId == 0) return false;
			if(currentWorkflowUserId == currentUserId){
				if(invoiceStatusId == 21 || invoiceStatusId == 25 || invoiceStatusId == 29 || invoiceStatusId == 33 || invoiceStatusId == 37)
				return false;
			}
			var flag = false;
		    if (invoiceStatusId >= 10 && invoiceStatusId <= 20 && SANINCO.ifAllGranted(Constants.FUNCTION.analystApproval)) {
		        flag = true;
		    }
		    if (invoiceStatusId >= 21 && invoiceStatusId <= 24 && SANINCO.ifAllGranted(Constants.FUNCTION.manager1Approval)) {
		        flag = true;
		    }
		    if (invoiceStatusId >= 25 && invoiceStatusId <= 28 && SANINCO.ifAllGranted(Constants.FUNCTION.manager2Approval)) {
		        flag = true;
		    }
		    if (invoiceStatusId >= 29 && invoiceStatusId <= 32 && SANINCO.ifAllGranted(Constants.FUNCTION.manager3Approval)) {
		        flag = true;
		    }
		    if (invoiceStatusId >= 33 && invoiceStatusId <= 36 && SANINCO.ifAllGranted(Constants.FUNCTION.manager4Approval)) {
		        flag = true;
		    }
			if(flag){
				var bo = false;
				Common.valiUserPrivilegeBanLock(invoiceId,function(data){
					if (data != true) {
						bo = false;
					}else{
						bo = true;
					}
				});
				if(!bo){
					Common.valiUserPrivilegeByUserId(currentAssignmentId,function(data){
						if(data != true){
							bo = false;
						}else{
							bo = true;
						}
					});
				}
				return bo;
			}else{
				return false;
			}
			
		};
		*/
		judgementRights_transactionTab = function(){
			var valid = false;
			$.ajax({
                url: "checkWorkflowPrivilege.action",
                type: "POST",
                dataType: "text",
                async: false,
                cache: false,
                data: ("invoiceId=" + invoiceId),
                success: function(o){
                    if (o.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                        window.location.reload();
                        return;
                    }
                    var data = eval("(" + o + ")");
                    if (data.error) {
                        alert(data.error);
                        return;
                    }
                    if (data.data != true) {
                    	valid = false;
                    } else {
                    	valid = true;
                    }
                },
				error : showError
            });
            return valid;
		};
	}
    
	try {
		if (judgementRights_transactionTab()) {
			G("handle_invoice_div_1").style.display = "block";
		}else{
			G("handle_invoice_div_1").style.display = "none";
		}
	} catch (e) { }
	
    var A = function(ele, html){
//        var e = document.createElement('<div>');
        var e = document.createElement('div');
        ele.appendChild(e);
        e.innerHTML = html;
    };
    var baseParam = {
        'invoiceId1': invoiceId,
        'banId1': currentBanId
    };
	var setMiscPaymentAmount = function(){
	    $.ajax({
            url: "getMiscPaymentAmount.action",
            type: "POST",
            dataType: "text",
            async: true,
            cache: false,
            data: ("svo.invoiceId1=" + invoiceId + "&svo.banId1="+currentBanId),
            success: function(o){
                if (o.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                    window.location.reload();
                    return;
                }
                var data = eval("(" + o + ")");
                if (data.error) {
                    alert(data.error);
                    return;
                }
                G("workflowActionMisceManeousAmount").innerHTML = data.result;
            },
			error : showError
        });
	};
			
    if (!window.handleInvoiceAHR) {
        handleInvoiceAHR = function(){
            YAHOO.ccm.container.handleInvoiceAHRDialog.show();
        };
        
        handleInvoiceAHR.call = function(flag){
            G("approveCallForPaymentAmount").innerHTML = flag == 1 ? G("workflowActionPaymentAmount").innerHTML : "0.00";
            G("approveCallForDisputeAmount").innerHTML = flag == 1 ? G("workflowActionDisputeAmount").innerHTML : "0.00";
            G("approveCallForMiscAdjustmentAmount").innerHTML = flag == 1 ? G("workflowActionMisceManeousAmount").innerHTML : "0.00";
        };
        
    }
    
    YAHOO.util.Connect.asyncRequest('POST', "validateMissingSCOA.action", {
        success: function(o){
            if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                window.location.reload();
                return;
            }
            var data = eval("(" + o.responseText + ")");
            if (data.error) {
                alert(data.error);
                return;
            }
            if (data.data) {
                G("su_miss_scoa_div").innerHTML = "There are items not SCOA coded, they are not included in this";
            } else {
                G("su_miss_scoa_div").innerHTML = "";
            }
        },
        failure: showError
    }, "svo.invoiceId1=" + invoiceId + "&svo.banId1=" + currentBanId);
    
    YAHOO.util.Connect.asyncRequest('POST', "getTotalPDAmount.action", {
        success: function(o){
            if (o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1) {
                window.location.reload();
                return;
            }
            var data = eval("(" + o.responseText + ")");
            if (data.error) {
                alert(data.error);
                return;
            }
            
            G("total_payment_amount_div_w").innerHTML = 'Total Payment:&nbsp; ' + (data.totalPayment ? data.totalPayment : "0.00");
            G("current_invoice_payments_div_w").innerHTML = 'Amount:&nbsp; ' + (data.currentInvoice ? data.currentInvoice : "0.00");
            G("manually_created_payments_div_w").innerHTML = 'Amount:&nbsp; ' + (data.manuallyCreated ? data.manuallyCreated : "0.00");
            G("dispute_payback_payments_div_w").innerHTML = 'Amount:&nbsp; ' + (data.disputePayback ? data.disputePayback : "0.00");
            G("applying_credit_payments_div_w").innerHTML = 'Amount:&nbsp; ' + (data.applyingCredit ? data.applyingCredit : "0.00");
            G("total_isPaidDispute_amount_div_w").innerHTML = 'Amount:&nbsp; ' + (data.totalPaidDisputePayment ? data.totalPaidDisputePayment : "0.00");
            G("total_isShortPaidDispute_amount_div_w").innerHTML = 'Amount:&nbsp; ' + (data.totalShortPaidDisputePayment ? data.totalShortPaidDisputePayment : "0.00");
            G("total_dispute_amount_div_w").innerHTML = 'Total Dispute:&nbsp; ' + (data.totalDispute ? data.totalDispute : "0.00");
            G("total_paid_dispute_amount_div_w").innerHTML = 'Short-paid dispute from current invoice:&nbsp;' + (data.totalShortPaidDispute ? data.totalShortPaidDispute : "0.00") +
            '<br/>Paid dispute from current invoice:&nbsp;' +
            (data.totalPaidDispute ? data.totalPaidDispute : "0.00") +
            '<br/>Short-paid dispute for previous charges:&nbsp;' +
            (data.shortPaidforPrevious ? data.shortPaidforPrevious : "0.00") +
            '<br/>Paid dispute for previous charges:&nbsp;' +
            (data.paidforPrevious ? data.paidforPrevious : "0.00");
			
			G("workflowActionPaymentAmount").innerHTML = (data.totalPayment ? data.totalPayment : "0.00");
			G("workflowActionDisputeAmount").innerHTML = (data.totalDispute ? data.totalDispute : "0.00");
			setMiscPaymentAmount();
        },
        failure: showError
    }, "svo.invoiceId1=" + invoiceId + "&svo.banId1=" + currentBanId);
    
    if (!window.pagePaymentScoaAmount) {
        pagePaymentScoaAmount = new SANINCO.Page("page_payment_scoa_amount_div", "pagePaymentScoaAmount", {
            sortingField: "s.account_code_name",
            sortingDirection: "asc",
            paginationDiv: "page_payment_scoa_amount_pagination_div",
            vo: "svo",
            recordText: '',
            recPerArray: [5, 10, 15, 20, 30, 50, 80, 100, 200],
            totalPageUri: "getPaymentScoaAmountTotalPageNo.action",
            dataUri: "getPaymentScoaAmount.action",
            cols: [{
                title: "SCOA",
                dataField: "scoa",
                sort: "s.account_code_name",
                width: "35%"
            }, {
                title: "Payment Amount",
                dataField: "totalPayment",
                sort: "sum(s.payment_amount)",
                width: "20%"
            }, {
                title: "SCOA Description",
                dataField: "scoaDescription",
				className : "table_td_br",
                width: "45%"
            }]
        });
        pagePaymentScoaAmount.addTotalSuccessEvent(function(data){
            if (data.totalResultNo == 0 || data.error) {
                G("saveExcelByAllPaymentsButton").style.display = "none";
            }
            else {
                G("saveExcelByAllPaymentsButton").style.display = "block";
            }
        });
    }
    pagePaymentScoaAmount.voParam = baseParam;
    pagePaymentScoaAmount.start();
    
    if (!window.pageCurrentInvoicePayment) {
        pageCurrentInvoicePayment = new SANINCO.Page("current_invoice_payment_div", "pageCurrentInvoicePayment", {
            sortingField: "p.payment_amount",
            sortingDirection: "asc",
            vo: "svo",
            recordText: '',
            recPerArray: [5, 10, 15, 20, 30, 50, 80, 100, 200],
            paginationDiv: "current_invoice_payment_pagination_div",
            totalPageUri: "getCurrentInvoicePaymentTotalPageNo.action",
            dataUri: "getCurrentInvoicePayment.action",
            cols: [{
                title: "Item Type",
                dataField: "itemType",
				sort: "it1.item_type_name",
                width: "200px"
            }, {
                title: "SCOA",
                dataField: "scoa",
				sort: "a.account_code_name",
                width: "200px"
            }, {
                title: "Amount",
                dataField: "amount",
                sort: "p.payment_amount",
                width: "110px"
            }]
        });
        pageCurrentInvoicePayment.addTotalSuccessEvent(function(data){
            if (data.totalResultNo == 0 || data.error) {
                G('downloadExcelPCIPButton').style.display = "none";
            }
            else {
                G("downloadExcelPCIPButton").style.display = "block";
            }
        });
    }
    pageCurrentInvoicePayment.voParam = baseParam;
    pageCurrentInvoicePayment.start();
    
    if (!window.pagePaidDisputePayment) {
        pagePaidDisputePayment = new SANINCO.Page("paid_dispute_payment_div", "pagePaidDisputePayment", {
            sortingField: "a.account_code_name",
            sortingDirection: "asc",
            vo: "svo",
            recordText: '',
            recPerArray: [5, 10, 15, 20, 30, 50, 80, 100, 200],
            paginationDiv: "paid_dispute_payment_pagination_div",
            totalPageUri: "getPaidDisputePaymentTotalPageNo.action",
            dataUri: "getPaidDisputePayment.action",
            cols: [{
                title: "SCOA",
                dataField: "scoa",
                sort: "a.account_code_name"
            }, {
                title: "Amount",
                dataField: "amount",
                sort: "p.payment_amount"
            }, {
                title: "Description",
                dataField: "description",
				className : "table_td_br",
				width : '260px',
                sort : "p.description" 
            }, {
                title: "Notes",
                dataField: "notes",
				className : "table_td_br",
				width : '260px',
                sort : "p.notes" 
            }, {
                title: "Attachment",
                dataField: function(obj){
                    return (obj.files ? ("<img  src=\"include/images/download1.gif\"  style=\"cursor: pointer;visibility: visible;display:block;\" onclick=\"SANINCO.Download(" + obj.files + ");\" />") : "");
                }
            }]
        });
        pagePaidDisputePayment.addTotalSuccessEvent(function(data){
            if (data.totalResultNo == 0 || data.error) {
                G('downloadExcelByPaidDisputePaymentButton').style.display = "none";
            }
            else {
                G("downloadExcelByPaidDisputePaymentButton").style.display = "block";
            }
        });
    }
    pagePaidDisputePayment.voParam = baseParam;
    pagePaidDisputePayment.start();
    
    if (!window.pageShortPaidDisputePayment) {
        pageShortPaidDisputePayment = new SANINCO.Page("short_paid_dispute_payment_div", "pageShortPaidDisputePayment", {
            sortingField: "a.account_code_name",
            sortingDirection: "asc",
            vo: "svo",
            recordText: '',
            recPerArray: [5, 10, 15, 20, 30, 50, 80, 100, 200],
            paginationDiv: "short_paid_dispute_payment_pagination_div",
            totalPageUri: "getShortPaidDisputePaymentTotalPageNo.action",
            dataUri: "getShortPaidDisputePayment.action",
            cols: [{
                title: "SCOA",
                dataField: "scoa",
                sort: "a.account_code_name",
                width: "200px"
            }, {
                title: "Amount",
                dataField: "amount",
                sort: "p.payment_amount",
                width: "110px"
            }, {
                title: "Description",
                dataField: "description",
                width: "240px"
                //sort : "p.description" 
            }, {
                title: "Attachment",
                dataField: function(obj){
                    return (obj.files ? ("<img  src=\"include/images/download1.gif\"  style=\"cursor: pointer;visibility: visible;display:block;\" onclick=\"SANINCO.Download(" + obj.files + ");\">") : "");
                },
                width: "80px"
            }]
        });
        pageShortPaidDisputePayment.addTotalSuccessEvent(function(data){
            if (data.totalResultNo == 0 || data.error) {
                G('downloadExcelByShortPaidDisputePaymentButton').style.display = "none";
            }
            else {
                G("downloadExcelByShortPaidDisputePaymentButton").style.display = "block";
            }
        });
    }
    pageShortPaidDisputePayment.voParam = baseParam;
    pageShortPaidDisputePayment.start();
    
    if (!window.pageManuallyCreatedPayment) {
        pageManuallyCreatedPayment = new SANINCO.Page("manually_created_payment_div", "pageManuallyCreatedPayment", {
            sortingField: "a.account_code_name",
            sortingDirection: "asc",
            vo: "svo",
            recordText: '',
            paginationDiv: "manually_created_payment_pagination_div",
            recPerArray: [5, 10, 15, 20, 30, 50, 80, 100, 200],
            totalPageUri: "getManuallyCreatedPaymentTotalPageNo.action",
            dataUri: "getManuallyCreatedPayment.action",
            cols: [{
                title: "SCOA",
                dataField: "scoa",
                sort: "a.account_code_name",
                width: "30%"
            }, {
                title: "Amount",
                dataField: "amount",
                sort: "p.payment_amount",
                width: "20%"
            }, {
                title: "Description",
				className : "table_td_br",
                dataField: "description",
                width: "35%"
                //sort : "p.description" 
            }, {
                title: "Attachment",
                dataField: function(obj){
                    return (obj.files ? ("<img  src=\"include/images/download1.gif\"  style=\"cursor: pointer;visibility: visible;display:block;\" onclick=\"SANINCO.Download(" + obj.files + ");\">") : "");
                },
                width: "15%"
            }]
        });
        pageManuallyCreatedPayment.addTotalSuccessEvent(function(data){
            if (data.totalResultNo == 0 || data.error) {
                G('downloadExcelPMCPButton').style.display = "none";
            }
            else {
                G("downloadExcelPMCPButton").style.display = "block";
            }
        });
    }
    pageManuallyCreatedPayment.voParam = baseParam;
    pageManuallyCreatedPayment.start();
    
    if (!window.pageDisputePaybackPayment) {
        pageDisputePaybackPayment = new SANINCO.Page("dispute_payback_payment_div", "pageDisputePaybackPayment", {
            sortingField: "a.account_code_name",
            sortingDirection: "asc",
            vo: "svo",
            recordText: '',
            paginationDiv: "dispute_payback_payment_pagination_div",
            recPerArray: [5, 10, 15, 20, 30, 50, 80, 100, 200],
            totalPageUri: "getDisputePaybackPaymentTotalPageNo.action",
            dataUri: "getDisputePaybackPayment.action",
            cols: [{
                title: "SCOA",
                dataField: "scoa",
                sort: "a.account_code_name",
                width: "24%"
            }, {
                title: "Amount",
                dataField: "amount",
                sort: "r.reconcile_amount",
                width: "13%"
            }, {
                title: "Description",
                dataField: "description",
				className : "table_td_br",
                width: "24%"
                //sort : "r.notes"
            }, {
                title: "Date",
                dataField: "date",
                sort: "r.reconcile_date",
                width: "13%"
            }, {
                title: "Analyst",
                dataField: "createBy",
                sort: "u.user_name",
                width: "13%"
            }, {
                title: "Attachment",
                dataField: function(obj){
                    return (obj.files ? ("<img  src=\"include/images/download1.gif\"  style=\"cursor: pointer;visibility: visible;display:block;\" onclick=\"SANINCO.Download(" + obj.files + ");\">") : "");
                },
                width: "13%"
            }]
        });
        pageDisputePaybackPayment.addTotalSuccessEvent(function(data){
            if (data.totalResultNo == 0 || data.error) {
                G('saveExcelByDisputePaybackPaymentsButton').style.display = "none";
            }
            else {
                G("saveExcelByDisputePaybackPaymentsButton").style.display = "block";
            }
        });
    }
    pageDisputePaybackPayment.voParam = baseParam;
    pageDisputePaybackPayment.start();
    
    if (!window.pageApplyingCreditPayment) {
        pageApplyingCreditPayment = new SANINCO.Page("applying_credit_payment_div", "pageApplyingCreditPayment", {
            sortingField: "a.account_code_name",
            sortingDirection: "asc",
            vo: "svo",
            recordText: '',
            paginationDiv: "applying_credit_payment_pagination_div",
            recPerArray: [5, 10, 15, 20, 30, 50, 80, 100, 200],
            totalPageUri: "getApplyingCreditPaymentTotalPageNo.action",
            dataUri: "getApplyingCreditPayment.action",
            cols: [{
                title: "SCOA",
                dataField: "scoa",
                sort: "a.account_code_name"
            }, {
                title: "Amount",
                dataField: "amount",
                sort: "r.reconcile_amount"
            }, {
                title: "Description",
				className : "table_td_br",
				width : '260px',
                dataField: "description"
                //sort : "r.notes"
            }, {
                title: "Date",
                dataField: "date",
                sort: "r.reconcile_date"
            }, {
                title: "Analyst",
                dataField: "createBy",
                sort: "u.user_name"
            }, {
                title: "Attachment",
                dataField: function(obj){
                    return (obj.files ? ("<img  src=\"include/images/download1.gif\"  style=\"cursor: pointer;visibility: visible;display:block;\" onclick=\"SANINCO.Download(" + obj.files + ");\">") : "");
                }
            }]
        });
        pageApplyingCreditPayment.addTotalSuccessEvent(function(data){
            if (data.totalResultNo == 0 || data.error) {
                G('saveExcelByApplyingCreditPaymentsButton').style.display = "none";
            }
            else {
                G("saveExcelByApplyingCreditPaymentsButton").style.display = "block";
            }
        });
    }
    pageApplyingCreditPayment.voParam = baseParam;
    pageApplyingCreditPayment.start();
    
    if (!window.currentDisputePage) {
        currentDisputePage = new SANINCO.Page("current_dispute_page_div", "currentDisputePage", {
            sortingField: "d.claim_number",
            sortingDirection: "asc",
            vo: "svo",
            recordText: '',
            recPerArray: [5, 10, 15, 20, 30, 50, 80, 100, 200],
            totalPageUri: "getCurrentDisputeTotalPageNoi.action",
            paginationDiv: "current_dispute_page_pagination_div",
            dataUri: "getCurrentDisputei.action",
            cols: [{
                title: "Claim Number",
                dataField: "claimNumber",
                sort: "d.claim_number"
            }, {
                title: "Dispute Category",
                dataField: "disputeCategory",
                sort: "dr.dispute_reason_name"
            }, {
                title: "Amount",
                dataField: "amount",
                sort: "d.dispute_amount"
            }, {
                title: "Recurring",
                dataField: function(o){
                    return o.recurring ? o.recurring.toUpperCase() : "";
                },
                sort: "d.flag_recurring"
            }, {
                title: "Short-pay",
                dataField: function(o){
                    return o.shortpaid ? o.shortpaid.toUpperCase() : "";
                },
                sort: "d.flag_shortpaid"
            }, {
                title: "Confidence Level",
                dataField: function(o){
                    return ((o.level) ? (o.level + '%') : "");
                },
                sort: "d.confidence_level"
            }, {
                title: "Reserve Amount",
                dataField: "reserveAmount",
                sort: "d.reserve_amount"
            }, {
                title: "Attachment",
                dataField: function(obj){
                    return (obj.files ? ("<img  src=\"include/images/download1.gif\"  style=\"cursor: pointer;visibility: visible;display:block;\" onclick=\"SANINCO.Download(" + obj.files + ");\">") : "");
                }
            }]
        });
        currentDisputePage.addTotalSuccessEvent(function(data){
            if (data.totalResultNo == 0 || data.error) {
                G('saveExcelByCurrentDisputeButton').style.display = "none";
            }
            else {
                G("saveExcelByCurrentDisputeButton").style.display = "block";
            }
        });
    }
    currentDisputePage.voParam = baseParam;
    currentDisputePage.start();
    
    if (!window.pastThreePaymentPage) {
        pastThreePaymentPage = new SANINCO.Page("past_three_payment_page_div", "pastThreePaymentPage", {
            sortingField: "d.claim_number",
            sortingDirection: "asc",
            vo: "svo",
            recordText: '',
            recPerArray: [5, 10, 15, 20, 30, 50, 80, 100, 200],
            dataUri: "getPastThreePaymentPage.action",
            //		totalPageUri: "getPastThreePaymentPageTotalPageNo.action",
            cols: [{
                title: "Date",
                dataField: "date"
            }, {
                title: "Payment",
                dataField: "paymentAmount"
            }, {
                title: "Dispute",
                dataField: "disputeAmount"
            }]
        });
    }
    pastThreePaymentPage.voParam = baseParam;
    pastThreePaymentPage.start();
    
    if (!window.invoiceHistoryPage) {
        invoiceHistoryPage = new SANINCO.Page("invoice_history_page_div_dj1", "invoiceHistoryPage", {
            sortingField: "d.claim_number",
            sortingDirection: "asc",
            vo: "svo",
            recordText: '',
            recPerArray: [5, 10, 15, 20, 30, 50, 80, 100, 200],
            dataUri: "getCurrentInvoiceHistory.action",
            cols: [{
                title: "Date",
                dataField: "created_timestamp"
            }, {
                title: "Invoice Status",
                dataField: "invoice_status_name"
            }, /*{
                title: "External Status",
                dataField: "external_invoice_status_name"
            }, */{
                title: "Payment status",
                dataField: "payment_status_name"
            }, {
                title: "Workflow Action",
                dataField: "workflow_action_name"
            }, {
                title: "Workflow User",
                dataField: "user"
            }, {
                title: "Description",
				className : "table_td_br",
				width : '260px',
                dataField: function(o){
					return "<div class=\"td-note-wrap\" style=\"white-space: pre-wrap;\">"+o.notes+"</div>"
				}
            }, {
                title: "Attachment",
                dataField: function(obj){
                    return (obj.files ? ("<img  src=\"include/images/download1.gif\"  style=\"cursor: pointer;visibility: visible;display:block;\" onclick=\"SANINCO.Download(" + obj.files + ");\">") : "");
                }
            }]
        });
        invoiceHistoryPage.addSuccessEvent(function(data){
            if (data.count == 0 || data.error) {
                G('saveExcelByActionHistoryButton').style.display = "none";
            }
            else {
                G("saveExcelByActionHistoryButton").style.display = "block";
            }
        });
        
        jQuery("#accordion_at_tab2_div").accordion({
            collapsible: true
        });
    }
    invoiceHistoryPage.voParam = baseParam;
    invoiceHistoryPage.start();
    
    
    
    if (!window.handleInvoiceAHRCallback) {
        handleInvoiceAHRCallback = function(obj){
            invoiceStatusId = obj.invoiceStatusId;
            judgementRightsResult = null;
            currentAssignmentId = obj.assignmentUserId;
            handleInvoiceAHR.call(obj.actionId);
            document.getElementById("invoiceStatus_divId").innerHTML = obj.invoiceStatusName;
            invoiceHistoryPage.start();
            YAHOO.ccm.container.handleInvoiceAHRDialog.cancel();
            $("#externalApprovalBtn").hide();
            $("#invoiceValidationBtn").hide();
            if(document.getElementById('approveResultMessage')){
            	document.getElementById('approveResultMessage').style.display = "block";
    			setTimeout(function(){
    				document.getElementById('approveResultMessage').style.display = "none";
    			},5000);
            }
            
            document.getElementById("handle_invoice_div_1").style.display = "none";
           
            if(typeof (obj.messageId) != "undefined" && typeof (obj.channelId) != "undefined" && obj.messageId != "" && obj.channelId != ""){
            	 jQuery.get("http://" + window.location.host + "/ccmApp/message.do?method=pushWebMessage&channelId="+handleInvoiceAHRCallback.channelId+"&messageId="+handleInvoiceAHRCallback.messageId+"",null);
            }
            
            if(searchLeftPageOnLoad)searchLeftPageOnLoad();
        };
    }
//    var i = 3;
    //YAHOO.util.Dom.removeClass("currentHandleInvoiceTags", "yui-pe-content");
  
    if (!YAHOO.ccm.container.handleInvoiceAHRDialog) {
        YAHOO.ccm.container.handleInvoiceAHRDialog = new YAHOO.widget.Dialog("handleInvoiceAHRDialog", {
            width: "35em",
            fixedcenter: true,
            visible: false,
            constraintoviewport: true,
			modal : true,
            buttons: [{
                text: "Approve",
                handler: function(){
                    if (confirm("Are you sure you want to Approve this payment?")) {
                        document.getElementById("handle_action").value = 1;
                        showLoadingModalLayer();
                        $("[name='approveWinUserId']").val(approveSelectUserDiv.getSelectUserList());
                    	$("[name='approveWinRoleId']").val(approveSelectUserDiv.getSelectRoleList());
                        document.getElementById("handleInvoiceWorkflowActionForm").submit();
                    }
                    else {
                        this.cancel();
                    }
                }
            }, {
                text: "Reject",
                handler: function(){
                    if (confirm("Are you sure you want to Reject this payment?")) {
                        document.getElementById("handle_action").value = 3;
                        showLoadingModalLayer();
                        $("[name='approveWinUserId']").val(approveSelectUserDiv.getSelectUserList());
                    	$("[name='approveWinRoleId']").val(approveSelectUserDiv.getSelectRoleList());
                        document.getElementById("handleInvoiceWorkflowActionForm").submit();
                    }
                    else {
                        this.cancel();
                    }
                }
            }, {
                text: "Cancel",
                handler: function(){
                    this.cancel();
                }
            }]
        });
        YAHOO.ccm.container.handleInvoiceAHRDialog.render();
        document.getElementById("handleInvoiceAHRDialog").style.display = "block";
       
    }
};

/**
 * Total amount download.
 * @return {[type]} [description]
 */
saveExcelByAllPayments = function(){
    var titles = "titles=SCOA&titles=Payment Amount&titles=SCOA Description";
    var uri = "saveExcelByAllPayments.action" + "?" + titles + "&" + pagePaymentScoaAmount.paramStr;
    windowOpen(uri);
}

/**
 * Payment from current invoice download.
 * @return {[type]} [description]
 */
downloadExcelPCIP = function(){
    var titles = "titles=Item Type&titles=SCOA&titles=Amount";
    var uri = "saveExcelByPaymentTabPCIP.action?" + titles + "&" + pageCurrentInvoicePayment.paramStr;
    windowOpen(uri);
}

/**
 * Paid-dispute from current invoice download.
 * @return {[type]} [description]
 */
downloadExcelByPaidDisputePayment = function(){
    var titles = "titles=SCOA&titles=Amount&titles=Description&titles=Notes";
    var uri = "downloadExcelByPaidDisputePayment.action" + "?" + titles + "&" + pagePaidDisputePayment.paramStr;
    windowOpen(uri);
}

/**
 * Manually created short-paid dispute download.
 * @return {[type]} [description]
 */
downloadExcelByShortPaidDisputePayment = function(){
    var titles = "titles=SCOA&titles=Amount&titles=Description";
    var uri = "downloadExcelByShortPaidDisputePayment.action" + "?" + titles + "&" + pageShortPaidDisputePayment.paramStr;
    windowOpen(uri);
}


/**
 * Manually created adjustment download.
 * @return {[type]} [description]
 */
downloadExcelPMCP = function(){
    var titles = "titles=SCOA&titles=Amount&titles=Description";
    var uri = "saveExcelByPaymentTabPMCP.action?" + titles + "&" + pageManuallyCreatedPayment.paramStr;
    windowOpen(uri);
}

/**
 * Dispute payback download.
 * @return {[type]} [description]
 */
saveExcelByDisputePaybackPayments = function(){
    var titles = "titles=SCOA&titles=Amount&titles=Description&titles=Date&titles=Analyst";
    var uri = "saveExcelByDisputePaybackPayments.action" + "?" + titles + "&" + pageDisputePaybackPayment.paramStr;
    windowOpen(uri);
}

/**
 * Applied credit download.
 * @return {[type]} [description]
 */
saveExcelByApplyingCreditPayments = function(){
    var titles = "titles=SCOA&titles=Amount&titles=Description&titles=Date&titles=Analyst";
    var uri = "saveExcelByApplyingCreditPayments.action" + "?" + titles + "&" + pageApplyingCreditPayment.paramStr;
    windowOpen(uri);
}

/**
 * Current disputes download.
 * @return {[type]} [description]
 */
saveExcelByCurrentDispute = function(){
    var titles = "titles=Claim Number&titles=Dispute Category&titles=Amount&titles=Recurring&titles=Short-pay&titles=Confidence Level&titles=Reserve Amount";
    var uri = "getSaveExcelByCreateDispute.action" + "?" + titles + "&" + currentDisputePage.paramStr;
    windowOpen(uri);
}

/**
 * Invoice Action History download.
 * @return {[type]} [description]
 */
saveExcelByActionHistory = function(){
    var titles = "titles=Date&titles=Invoice Status&titles=Payment status&titles=Workflow Action&titles=Workflow User&titles=Description";
    var uri = "saveExcelByActionHistory.action" + "?" + titles + "&" + invoiceHistoryPage.paramStr;
    windowOpen(uri);
}

function clearfiles(){
	$("#textareaNoteId").val("");
	$("#addFile").html(""); 
	cleanHiddenAtach(2);
	approveSelectUserDiv.cleanSelect();
}
