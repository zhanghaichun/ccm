// Created By Chao.Liu
var reportUserTabInit = {
	runReportsIsInitTab1 : "searchViewReport();",
	runReportsIsInitTab2 : "searchReport();",
	get : function (key){
		if(key == "tab1"){
			eval(reportUserTabInit.runReportsIsInitTab1);
			reportUserTabInit.runReportsIsInitTab1 = "cancle()";
		}
		if(key == "tab2"){
			eval(reportUserTabInit.runReportsIsInitTab2);
			reportUserTabInit.runReportsIsInitTab2 = "";
		}
	},
	data : {},
	validate : function (){
		var boo = true;
		var f = getRDOM("_fdateTR").style.display;
		var t = getRDOM("_tdateTR").style.display;
		var req = /^\s*[1-2]\d{3}\/(0?[1-9]||1[0-2])\/(0?[1-9]||[1-2][0-9]||3[0-1])\s*$/;
		var fdate,tdate;
		if(!f){
			fdate = getRDOM("_p_from_date").value;
			if(!req.exec(fdate)){
				removeClassName("_p_from_date",'validation-passed');
				addClassName("_p_from_date","validation-failed");
				boo = false
			}else{
				removeClassName("_p_from_date",'validation-failed');
				addClassName("_p_from_date","validation-passed");
			}
		}
		if(!t){
			tdate = getRDOM("_p_to_date").value;
			if(!req.exec(tdate)){
				removeClassName("_p_to_date",'validation-passed');
				addClassName("_p_to_date","validation-failed");
				boo = false
			}else{
				removeClassName("_p_to_date",'validation-failed');
				addClassName("_p_to_date","validation-passed");
			}
		}
		if(boo) {
			if(!f&&!t){
				fdate = parseInt(fdate.replace(/\//g,""));
				tdate = parseInt(tdate.replace(/\//g,""));
				if(fdate > tdate){
					getRDOM("_p_to_date").value = "";
//					alert("To Date Don't Less Than From Date!");
					alert("Invoice To Date can not be less than Invoice From Date!");
					boo = false;
				}
		     }
		}	
		if(reportParameter){

			for(var i =0;i<reportParameter.length;i++) {
				if (reportParameter[i].filed_type == "date") {
					itemId = "v_report_parameter"+reportParameter[i].id+"_value";
					
					idate = $("#"+itemId).val();
					
					if(idate != "" && !req.exec(idate)){
						removeClassName(itemId,'validation-passed');
						addClassName(itemId,"validation-failed");
						boo = false
					}else{
						removeClassName(itemId,'validation-failed');
						addClassName(itemId,"validation-passed");
					}
				}
				
			 }			
		
		}
		
		if(getRDOM("_pBanIdTr").style.display == ""){
			if($("#txtBanId").val() == "" || $("#txtBanId").val() == "all") {
				$("#banIdIsNullMessageSpan").text("Ban not null!");
				boo = false;
			}else{
				$("#banIdIsNullMessageSpan").text("");
			}
		}
		return boo;
	}
};

function getRDOM(id){
	return typeof (id) == "string" ? document.getElementById(id) : id;
}

YAHOO.util.Event.onDOMReady(function () {
	var tabView = new YAHOO.widget.TabView('demo');
	$("#demo li a:eq(0)").click();
	tabView.selectTab(0);
	$("#ReportDetailDiv").children().css({display:"none"});
	// Moved Here By Chao.Liu to 10/07/22 AM
	$("#noneAndblok").css({display:"none"}); // Make In Wang
});

function searchReport(){
	ReportPage = new SANINCO.Page("reportListDiv","ReportPage",{
      sortingField : "re.report_name",
      sortingDirection : "asc",
      vo : "rvo",
      recPerArray : [30,50,100],
      totalPageUri : "doGetReportTotalNOTags.action",
      dataUri : "doSearchReportTags.action",
      cols : [
	      {
	          title : "Report Name",
	          dataField: function(row){
			  	  reportUserTabInit.data[row.rid] = row;
	              return '<a href="#" onclick="showReportDetails('+row.rid+')">' + row.rname + '</a>';
	          },
	          sort : "re.report_name",
	          filtration : {name:"re.report_name",alias:"Report Name"}
	      },{
	          title : "Rtag Color",
	          dataField: function(row){
	          	var cdata = row.cData;
	          	var colorCSS = "width:20px;height:13px;float:left;margin-left:5px;";
          		var cStr = '';
          		for(var i=0; i<cdata.length; i++){
          			cStr += '<div style="background:'+cdata[i].color+';'+colorCSS+'"></div>';
          		}
	          	return cStr; 
	      	  }
	      }
      ]
  });
  var filter = new SANINCO.Filter();
  filter.addEditeEvent(function(){ReportPage.start();});
  filter.add('re.report_name', 'String');
  ReportPage.setFilter(filter);
  
  ReportPage.start();
}

//show Dynamic FromDate And ToDate
function showDynamicFromDateAndToDate(rid){
	
	var fromDateLabel = getRDOM("p_from_date_label");
	var toDateLabel = getRDOM("p_to_date_label");

	switch (rid) {
	case 17 : 
		fromDateLabel.innerHTML = "Invoice From Date:";
		toDateLabel.innerHTML = "Invoice To Date:";	
		break;
	case 24 : 
		fromDateLabel.innerHTML = "Invoice From Date:";
		toDateLabel.innerHTML = "Invoice To Date:";	
		break;
	case 29 : 
		fromDateLabel.innerHTML = "Dispute Created From Date:";
		toDateLabel.innerHTML = "Dispute Created To Date:";	
		break;
	case 30 : 
		fromDateLabel.innerHTML = "Dispute Close From Date:";
		toDateLabel.innerHTML = "Dispute Close To Date:";	
		break;
	case 36 : 
		fromDateLabel.innerHTML = "Payment From Date:";
		toDateLabel.innerHTML = "Payment To Date:";	
		break;
	case 52 : 
		fromDateLabel.innerHTML = "First Invoice From Date:";
		toDateLabel.innerHTML = "First Invoice To Date:";	
		break;
	case 56 : 
		fromDateLabel.innerHTML = "Invoice From Date:";
		toDateLabel.innerHTML = "Invoice To Date:";	
		break;
	case 57 : 
		fromDateLabel.innerHTML = "Invoice From Date:";
		toDateLabel.innerHTML = "Invoice To Date:";	
		break;
	case 60 : 
		fromDateLabel.innerHTML = "Invoice From Date:";
		toDateLabel.innerHTML = "Invoice To Date:";	
		break;
	case 61 : 
		fromDateLabel.innerHTML = "Invoice From Date:";
		toDateLabel.innerHTML = "Invoice To Date:";	
		break;
	case 63 : 
		fromDateLabel.innerHTML = "Invoice Receive From Date:";
		toDateLabel.innerHTML = "Invoice Receive To Date:";	
		break;
	case 66 : 
		fromDateLabel.innerHTML = "Invoice From Date:";
		toDateLabel.innerHTML = "Invoice To Date:";	
		break;
	case 68 : 
		fromDateLabel.innerHTML = "Invoice From Date:";
		toDateLabel.innerHTML = "Invoice To Date:";	
		break;
	case 69 : 
		fromDateLabel.innerHTML = "Payment From Date:";
		toDateLabel.innerHTML = "Payment To Date:";	
		break;
	case 71 : 
		fromDateLabel.innerHTML = "Invoice From Date:";
		toDateLabel.innerHTML = "Invoice To Date:";	
		break;
	case 72 : 
		fromDateLabel.innerHTML = "Invoice From Date:";
		toDateLabel.innerHTML = "Invoice To Date:";	
		break;
	case 90 : 
		fromDateLabel.innerHTML = "Expecting From Date:";
		toDateLabel.innerHTML = "Expecting To Date:";	
		break;
	case 91 : 
		fromDateLabel.innerHTML = "Payment From Date:";
		toDateLabel.innerHTML = "Payment To Date:";	
	case 92 : 
		fromDateLabel.innerHTML = "Payment From Date:";
		toDateLabel.innerHTML = "Payment To Date:";	
	case 95 : 
		fromDateLabel.innerHTML = "Payment From Date:";
		toDateLabel.innerHTML = "Payment To Date:";	
		break;
	case 96 : 
		fromDateLabel.innerHTML = "Payment From Date:";
		toDateLabel.innerHTML = "Payment To Date:";	
		break;
	default:
		fromDateLabel.innerHTML = "From Date:";
		toDateLabel.innerHTML = "To Date:";	
	
	} 
		
	
}

//Shwo New Created Report Div
function showReportDetails(rid){
	$("#ReportDetailDiv").animate({ width: "600px" }, {queue:false, duration:1000} );
	$("#ReportDetailDiv").children().css({display:""});
	showDynamicFromDateAndToDate(rid);
	var row = reportUserTabInit.data[rid];
	getRDOM("reportNameTD").innerHTML = "<h3><b>"+row.rname+"</b></h3>";
	
	getRDOM("txtReportId").value = row.rid;

    $.ajax({
	    url: "doValidateUserReportFunction.action",
	    type: "POST",
	    dataType: "text",
	    async: true,
	    cache: false,
	    data: ("reportId=" + rid),
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
			getRDOM("sharingNew").disabled = !data.data;
	    },
		error : showError
	});
    
    var callback = {
			success: showReportParameter,
			failure: showError
		};
		var reportId = "reportId=" + rid;
		YAHOO.util.Connect.asyncRequest('POST' , "doReportParameterFunction.action" , callback , reportId) 
			
	getRDOM("txtCreatedReportNameNew").value = "";
	
	if(row.keyWord == "Y"){
		getRDOM("_keyWord").style.display = "";
		$('#_key_word').val("");
	}else{
		getRDOM("_keyWord").style.display = "none";
	}
	
	if(row.onlyUser=="N"){
		getRDOM("_onlyUserTR").style.display = "none";
	}else{
		getRDOM("_onlyUserTR").style.display = "";
		getRDOM("chkOnlyUser").checked = false;
	}

	if(row.pBanId=="N"){
		getRDOM("_pBanIdTr").style.display = "none";
	}else{
		getRDOM("_pBanIdTr").style.display = "";
		runReportBanListFlexbox.setValue("all");
	}
	
	getRDOM("_p_from_date").value = "";
	getRDOM("_p_to_date").value = "";
	getRDOM("analystId_LC").value = "0";
	getRDOM("sharingNew").value = "N";
	getRDOM("sendEmailNew").value = "N";
//	getRDOM("reportFormatNew").value = "csv";
	getRDOM("_fdateTR").style.display = row.fromDate;
	getRDOM("_tdateTR").style.display = row.toDate;
	getRDOM("_analystTR").style.display = row.analyst;
	
	getRDOM("isLogin").value = "N";
	getRDOM("txtEffectiveHour").value = "";
	getRDOM("txtDownloadTimes").value = "";
	$("#txtEffectiveHour").removeClass("validation-failed").addClass("validation-passed");
	$("#txtDownloadTimes").removeClass("validation-failed").addClass("validation-passed");

	
	getRDOM("isEmail").value = row.isEmail;
		
	getRDOM("isDownloadTimes").value = row.isDownloadTimes;
	getRDOM("isEffectiveHour").value = row.isEffectiveHour;
	getRDOM("isLogin").value = row.isLogin;
	
	getRDOM("_emailstTR").style.display = "none";
	getRDOM("_pDownloadTimes").style.display = "none";
	getRDOM("_pEffectiveHourTr").style.display = "none";
	getRDOM("_pLoginTr").style.display = "none";
	
/*	var toEmail = userEmail+";"+reportEmail;
	if (toEmail.substring(toEmail.length - 1,toEmail.length) == ";") {
		toEmail=toEmail.substring(0,toEmail.length - 1) ;
	}
	if (toEmail.substring(0,1) == ";") {
		toEmail=toEmail.substring(1) ;
	}*/

	
	$("#txtEmail").val( row.userEmail);
	
}
function showEmailText(val){
	var isEmail = getRDOM("isEmail").value;
	var isDownloadTimes = getRDOM("isDownloadTimes").value;
	var isEffectiveHour = getRDOM("isEffectiveHour").value;
	var isLogin = getRDOM("isLogin").value;

	if (val == "Y" && isEmail == "Y") {
		getRDOM("_emailstTR").style.display = "table-row";
	}else{
		getRDOM("_emailstTR").style.display = "none";
	}
	if (val == "Y" && isDownloadTimes == "Y") {
		getRDOM("txtDownloadTimes").value = "1";
		getRDOM("_pDownloadTimes").style.display = "table-row";
	}else{
		getRDOM("txtDownloadTimes").value = "";
		getRDOM("_pDownloadTimes").style.display = "none";
	}
	if (val == "Y" && isEffectiveHour == "Y") {
		getRDOM("txtEffectiveHour").value = "1";
		getRDOM("_pEffectiveHourTr").style.display = "table-row";
	}else{
		getRDOM("txtEffectiveHour").value = "";
		getRDOM("_pEffectiveHourTr").style.display = "none";
	}
	if (val == "Y" && isLogin == "Y") {
		getRDOM("isLogin").value = "N";
		getRDOM("_pLoginTr").style.display = "table-row";
	}else{
		getRDOM("isLogin").value = "";
		getRDOM("_pLoginTr").style.display = "none";
	}		
		
}
var reportParameter;
function showReportParameter(o){
	reportParameter = eval("(" + o.responseText + ")").data;
	var row = "";
	var itemId = "";
	var listArray = [];
	$("#_show_Report_Parameter").html("");
	if (reportParameter) {
		// 循环遍历 report parameter
		for(var i =0;i<reportParameter.length;i++) {
			itemId = "v_report_parameter"+reportParameter[i].id+"_value";
			// 根据 field_type 的不同类型来执行
			// 不同的逻辑
			if (reportParameter[i].filed_type == "text") {
				row ='<tr><td width="30%">'+reportParameter[i].view_name+
				':</td><td><input id="'+itemId+'"/></td></tr>';
				$("#_show_Report_Parameter").append(row); 
			} else if (reportParameter[i].filed_type == "date") {
				row ='<tr><td width="30%">'+reportParameter[i].view_name+
				':</td><td><input id="'+itemId+'"/> '+
				'<a onClick="g_Calendar.show(event,\''+itemId+'\',false, \'yyyy/mm/dd\', new Date(1990,1,1)); return false;" href="javascript:%20void(0);">'+
				'<img src="include/images/cal.gif" border="0" align="middle"></a>'+
				'<span style="font: 15px; color: red; vertical-align: middle;">  </span> yyyy/MM/dd'+
				'</td></tr>';
				$("#_show_Report_Parameter").append(row); 
				
			} else if (reportParameter[i].filed_type == "list") {
				row ='<tr><td width="30%">'+reportParameter[i].view_name+
				':</td><td><div id="'+itemId+'"/></div></tr>';
				$("#_show_Report_Parameter").append(row);
				
				if(reportParameter[i].table_filed == "charge_type") {
					listArray = CT_Array;
					$("#"+itemId).flexbox(listArray, {
						maxCacheBytes : 200000,
						highlightMatches : true,
						autoCompleteFirstMatch : false,
						paging: false,
						rowHeight:"14.5px",
						width : 270,
						onSelect : function(){
						}
					}).setValue("");
				}else if(reportParameter[i].table_filed == "scoa"){
					listArray = SC_Array;
					$("#"+itemId).flexbox(listArray, {
						maxCacheBytes : 200000,
						maxVisibleHeight : 170,
						highlightMatches : true,
						autoCompleteFirstMatch : false,
						paging: false,
						width : 270,
						onSelect : function(){
					}
					}).setValue("");
				}
			}else if(reportParameter[i].filed_type == "lists")  {
				
				if (reportParameter[i].table_filed == "ban"){
					var select = " <select data-placeholder=\"Choose Ban...\"  id=\""+itemId+"\" class=\"chosen-select\" multiple tabindex=\"4\" style=\"width: 190px;\"></select>"
					row ='<tr><td width="30%">'+reportParameter[i].view_name+
					':</td><td>     '+select+'</tr>';
					$("#_show_Report_Parameter").append(row);
					
					listArray = BanL_Array;
					var html = '';
					 for (var j = 0; j < listArray.total; j++) {
               html+='<option value="'+listArray.results[j].id+'">'+listArray.results[j].name+'</option>'
            }
					 $(".chosen-select").append(html);

					 jQuery_1_12_4(
							 function($){				
									 jQuery_1_12_4('.chosen-select').chosen({
										  no_results_text: 'Oops, nothing found!',
										  search_contains: true
										});
									
							 }
							 
					 );	

					 // 控制 jQuery Chosen 插件的的原始高度，
					 // 当文本框中有 ban item 的时候，高度自适应
					 // 反之， 设置文本框的高度为系统文本框的高度
					 // 和默认样式
					 // 这里必须使用 jQuery_1_12_4 来选择元素
					 jQuery_1_12_4('.chosen-select').on('change', function (e, params) {
					   
					   // 选中项的数量
					   var lengthOfChoice = $('.chosen-container .search-choice').length
					   // 根据 trigger 栏中有无 item
					   // 来设置文本框的高度。
					   if (lengthOfChoice === 1 && params.deselected) {
					     $('.chosen-container .chosen-choices .chosen-search-input')
					     .css({
					       height: '16px',
					       transform: 'translateY(-2px)'
					     })
					     
					     $('.chosen-container-multi .chosen-choices li.search-field')
					     .css('height', '16px');
					     
					     // 当文本框中没有 ban item 的时候，设置超出内容
					     // 隐藏
					     $('.chosen-container .chosen-choices').css('overflow', 'hidden')
					     
					   } else if (lengthOfChoice >= 1) {
					     $('.chosen-container .chosen-choices .chosen-search-input')
					     .css({
					       height: '25px',
					       transform: 'none'
					     })
					     
					     $('.chosen-container-multi .chosen-choices li.search-field')
					     .css('height', '25px');

					     if (lengthOfChoice >= 2) {
					     	$('.chosen-container .chosen-choices').css('overflow', 'auto')
					     }
					     
					   }
					 })		
				}

			}
		 }
	}
	
}	
function cancle(){
	$("#ReportDetailDiv").animate({ width: "0px" }, {queue:false, duration:1000} );
	$("#ReportDetailDiv").children().css({display:"none"});
}

function checkNameIsRepeat(){
	var crName = getRDOM("txtCreatedReportNameNew").value;	
	var req = new RegExp(/[(\^)(\&)]+/);
//	var req = new RegExp(/[(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\()(\))(\-)(\_)(\+)(\=)(\[)(\])(\{)(\})(\|)(\\)(\;)(\:)(\')(\")(\,)(\.)(\/)(\<)(\>)(\?)]+/);
	if(req.test(crName)){
		alert("Can't fill in the special symbol: ^&");
		return;
    }
	$("#txtEffectiveHour").removeClass("validation-failed").addClass("validation-passed");
	$("#txtDownloadTimes").removeClass("validation-failed").addClass("validation-passed");
	
//	验证EffectiveHour  是小数：
	var crEffectiveHour = getRDOM("txtEffectiveHour").value;	
	if (crEffectiveHour != "") {
		var ReEffectiveHour = /^([1-9]\d*|0)(\.\d{1,2})?$/;
		if(!ReEffectiveHour.test(crEffectiveHour)){
			$("#txtEffectiveHour").removeClass("validation-passed").addClass("validation-failed");
			return;
	    }
	    if(crEffectiveHour ==0) {
	    	$("#txtEffectiveHour").removeClass("validation-passed").addClass("validation-failed");
			return;
	    }
	}
//	验证DownloadTimes  下载次数是数字：
	var crDownloadTimes = getRDOM("txtDownloadTimes").value;
	if (crDownloadTimes != "") {
		var ReDownloadTimes = /^[1-9]\d*$/;
		if(!ReDownloadTimes.test(crDownloadTimes)){
			$("#txtDownloadTimes").removeClass("validation-passed").addClass("validation-failed");
			return;
	    }
	}
		
	//验证邮件地址
	if (getRDOM("sendEmailNew").value == "Y" && getRDOM("isEmail").value == "Y") {
		var Regex = /^(?:\w+\.?)*\w+@(?:\w+\.)*\w+$/; 
		var toAddress = $.trim(getRDOM("txtEmail").value);
		
		if (toAddress == null || toAddress == '') {
			alert("Email address cannot be blank.");
			return;
		}
		
		if($.trim($("#txtEmail").val()).length>255){
			flag=false;
			alert('Email address more than 255 words');
			return;
		}
		
		if(toAddress.split(";").length > 1) {
	    	var toAddressList = toAddress.split(";");
	    }else if(toAddress.split(",").length > 1){
	    	var toAddressList = toAddress.split(",");
	    }
	    
		if (toAddressList != null && toAddressList.length > 1) {
			for(var i=0;i<toAddressList.length;i++){
				if (!Regex.test(toAddressList[i])){                
			　　		alert("Please input the correct email address.");
					return;
			　　}
			}
		}else{
			if (!Regex.test(toAddress)){                
		　　		alert("Please input the correct email address.");
				return;
		　　}
		}
	}
	
	
	
	var callback = {
		success: checkNameIsRepeatIsSuccess,
		failure: showError
	};
	var checkNameIsRepeatParamer = "rvo.createdReportNameNew=" + ccmEscape(crName);
	YAHOO.util.Connect.asyncRequest('POST' , "checkReportNameIsRepeat.action" , callback , checkNameIsRepeatParamer) 
}
function checkNameIsRepeatIsSuccess(o){
	if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
		window.location.reload();
		return;
	}
	var data = eval("("+o.responseText+")");
	if(data.error){
		alert("Check Report Name Error:" + data.error);
		return false;
	}
	if(data.isRepeat){
		alert(data.isRepeat);
		getRDOM("txtCreatedReportNameNew").select();
	}else{
		saveCreatedReport();
	}
}	

function saveCreatedReport(){
	if(!reportUserTabInit.validate()){return false}
	var callback = {
		success: saveCreatedReportIsSuccess,
		failure: showError
	};
	var saveCreatedReportParamer = saveCRPString();
	YAHOO.util.Connect.asyncRequest('POST' , "saveCreatedReport.action" , callback , saveCreatedReportParamer) 
}

function saveCreatedReportIsSuccess(o){
	if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
		window.location.reload();
		return;
	}
	var data = eval("("+o.responseText+")");
	if(data.error){
		alert("Save CreatedReport Error:" + data.error);
		return;
	}
	alert("Your report has been submitted, please download your report from View Report after it's generated!");
	cancle();
}

function saveCRPString(){
	var rid = "rvo.reportId="+getRDOM("txtReportId").value;
	var crName = "&rvo.createdReportNameNew="+ccmEscape(getRDOM("txtCreatedReportNameNew").value);
	
	var crIsLogin = "&rvo.isLogin="+getRDOM("isLogin").value;
	var crEffectiveHour = "&rvo.effectiveHour="+ccmEscape(getRDOM("txtEffectiveHour").value);
	var crDownloadTimes = "&rvo.downloadTimes="+ccmEscape(getRDOM("txtDownloadTimes").value);
	
	var crSharingNew = "&rvo.sharingNew="+(getRDOM("sharingNew").disabled?"N":getRDOM("sharingNew").value);
	var crSendEmailNew = "&rvo.sendEmailNew="+getRDOM("sendEmailNew").value;
	if (getRDOM("sendEmailNew").value == "Y") {
		var crEmail = "&rvo.email="+getRDOM("txtEmail").value;
	}else {
		var crEmail = "";
	}
	var keyWord = "&rvo.keyWord="+ccmEscape($('#_key_word').val());
//	var crReportFormatNew = "&rvo.reportFormatNew="+getRDOM("reportFormatNew").value;
	var crOnlyUser = "";
	if(getRDOM("_onlyUserTR").style.display == ""){
		crOnlyUser = "&rvo.onlyUser="+(getRDOM("chkOnlyUser").checked?"Y":"N");
	}else{
		crOnlyUser = "&rvo.onlyUser=N";
	}
	var crPBanId = "";
	if(getRDOM("_pBanIdTr").style.display == ""){
		crPBanId = "&rvo.pBanId="+$("#txtBanId").val();
	}
	var fdate = "&rvo.fdate="+(getRDOM("_fdateTR").style.display==""?getRDOM("_p_from_date").value:"");
	var tdate = "&rvo.tdate="+(getRDOM("_tdateTR").style.display==""?getRDOM("_p_to_date").value:"");
	var analystId = "&rvo.analystId="+(getRDOM("_analystTR").style.display==""?getRDOM("analystId_LC").value:"");
	
	var strJson = "&rvo.strJson={";	
	var j = 0;
	var itemId = "";
	if(reportParameter){
		for(var i =0;i<reportParameter.length;i++)
		 {
			itemId = "v_report_parameter"+reportParameter[i].id+"_value";
			if ( $("#"+itemId).val() !="" && $("#"+itemId).val() !=undefined ) {
				if (reportParameter[i].filed_type == 'lists') {		
					strJson += "'"+reportParameter[i].table_filed + "':'" + $("#"+itemId).val().join(',') + "',";
				}else{
					strJson += "'"+reportParameter[i].table_filed + "':'" + ccmEscape(ccmEscape2($("#"+itemId).val())) + "',";
				}
				
				j++;
			}
			if ( $("#"+itemId).getValue() != "" && $("#"+itemId).getValue() !=undefined ){
				strJson += "'"+reportParameter[i].table_filed + "':'" + ccmEscape(ccmEscape2($("#"+itemId).getValue())) + "',";
				j++;
			}
		 }
		strJson = strJson.substr(0,strJson.length-1);
		strJson += "}";
	}
		if(j==0){
			strJson = "&rvo.strJson=";
		}	

	return rid+crName+crIsLogin+crEffectiveHour+crDownloadTimes+crSharingNew+crSendEmailNew+crEmail+crOnlyUser+fdate+tdate+analystId+crPBanId+keyWord+strJson; // +crReportFormatNew
}

function ccmEscape2 (value) {
	return value.replace(/\\/g, "\\\\").replace(/'/g, "\\'");
}

var getVendorNameByBanId = function (bid){
	function start(){
		var callback = {
			success: end,
			failure: showError
		};
		var startParamer = "rvo.banId=" + bid;
		YAHOO.util.Connect.asyncRequest('POST' , "getVendorNameByBanId.action" , callback , startParamer)
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
		$("#runReportVendorNameSpan").text(data.vendorName);
	}
	start();
}
function downloadFileO(name,path){
	var df = document.getElementById("Download");
	df.fileName.value = name;
	df.filePath.value = path;
	df.submit();
	
}
