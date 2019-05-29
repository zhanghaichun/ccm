(function(W){

    var G = function(id){
        return document.getElementById(id);
    };
    var A = function(ele, html){
        var e = document.createElement('<span>');
        ele.appendChild(e);
        e.innerHTML = html;
    };
    
    var CI = function(){
        var TO = this;
        
        this.valiBanLock = function(banId, callBack){
            $.ajax({
                url: "doValidateBanLock.action",
                type: "POST",
                dataType: "text",
                async: false,
                cache: false,
                data: ("banId=" + banId),
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
                    callBack(data.invoiceStautsId, data.banProcess);
                },
				error : showError
            });
        };
		
		this.valiDisputeBanLock = function(disputeId, callBack){
            $.ajax({
                url: "doValidateDisputeBanLock.action",
                type: "POST",
                dataType: "text",
                async: false,
                cache: false,
                data: ("disputeId=" + disputeId),
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
                    callBack(data.disputeStatusId, data.banProcess);
                },
				error : showError
            });
        };
		
		this.valiUserPrivilegeBanLock = function(invoiceId, callBack){
            $.ajax({
                url: "doValiUserPrivilegeBanLock.action",
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
                    callBack(data.data);
                },
				error : showError
            });
        };
		
		this.valiUserPrivilegeBanLockDispute = function(invoiceId, callBack){
            $.ajax({
                url: "doValiUserPrivilegeBanLockDispute.action",
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
                    callBack(data.data);
                },
				error : showError
            });
        };
		
		this.valiUserPrivilegeByUserId = function(currentAssignmentId, callBack){
            $.ajax({
                url: "doValiUserPrivilegeByUserId.action",
                type: "POST",
                dataType: "text",
                async: false,
                cache: false,
                data: ("currentAssignmentId=" + currentAssignmentId),
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
                    callBack(data.data);
                },
				error : showError
            });
        };
		
    };
    
    Common = new CI();
})(window);
