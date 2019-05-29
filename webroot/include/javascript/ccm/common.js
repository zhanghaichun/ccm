YAHOO.namespace("ccm.container");

YAHOO.util.Event.onDOMReady(function () {
	document.body.className="yui-skin-sam";
	// 将Search按钮的Enter按键触发事件绑定到body上。
	$('form').bind('keydown',enterToStartSearch); 
});

/**
 * 搜索条件表单按下enter按钮执行search动作。
 */
function enterToStartSearch() {

	// key code => 13 的按键是Enter键， 系统中的search方法应该
	// 定义为startSearch.
	if( window.event.keyCode == 13) {

		if (window.startSearch) {
			startSearch();
		} else if (window.wikiHomeSearch) {
			wikiHomeSearch(); // Wiki Tab 页面的Search 搜索按钮。
		}
		
	}
}

if(!!-[1,]){
	CollectGarbage = function(){};
}


ccmEscape = function(s){
	if(typeof s == 'string'){
		return escape(s).replace(/\+/g, '%2B').replace("'", "\'").replace(/#/g, "%23");
	}else{
		return s;
	}
}

/**
 * 使用新的标签页打开链接，
 * 一般用于下载。
 * @param  {[type]} uri 下载文件的链接。
 * @return {[type]}     [description]
 */
function windowOpen(uri){	
	window.open(uri);
}

function windowLocationHref(uri){	
	window.location.href = uri;
}

function deletefile(number){	
	var div=document.getElementById(number);
	div.innerHTML=" ";
    div.parentNode.removeChild(div);
}

/*
删除后台返回rate字符串中多余的0
解决的方法就是将rate字符串值直接转换成
number 类型就可以了。

 */
function eliminateZeros(o, eliminateValueType) {

	var rateValue, 
		multiplierValue,
		discountValue,

		eliminateTypeValue,

		eliminatedValue;


	// 首先需要将逗号分隔符去掉
	
	
	

	// 根据类型取值
	switch(eliminateValueType){
		case 'rate':

			rateValue = isZero(o.rate) ? o.rate : o.rate.replace(',', '');
			eliminateTypeValue =  rateValue;
			break;

		case 'discount':

			discountValue = isZero(o.discount) ? o.discount : o.discount.replace(',', '');
			eliminateTypeValue =  discountValue;
			break;

		case 'multiplier':

			multiplierValue = isZero(o.multiplier) ? o.multiplier : o.multiplier.replace(',', '');
			eliminateTypeValue =  multiplierValue;
			break;

		default: 
			eliminateTypeValue =  'Invalid value type.';
			break;
	}

	var eliminatedValue = isNaN( parseFloat(eliminateTypeValue) ) ? eliminateTypeValue : parseFloat(eliminateTypeValue);
    return eliminatedValue;
}

/*
判断参数值是否是0， 或者可以转换成0的字符串值
 */
function isZero(paramValue) {

	return paramValue == 0 ? true : false;
}


function getUrlParms(key) {
     var args =  new Object(); 
     var query = location.search.substring(1); 
	 var pairs=query.split("&"); 
     for(var i=0;i<pairs.length;i++){   
         var pos=pairs[i].indexOf('=');   
            if(pos==-1)   continue; 
            var argname=pairs[i].substring(0,pos); 
            var value=pairs[i].substring(pos+1);
            args[argname]=unescape(value);      
	 }
     return parseInt(args[key]);
}

function clearFormUploadFiles(form,uploads){
	var F = ((typeof form == 'string') ? document.getElementById(form) : form);
	if(!uploads)uploads = "uploads";
	var us = F[uploads];
	if(!us)return;
	if(us.length){
		for(var i=0;i<us.length;i++){
			if(us[i].value != ""){
				try{
					us[i].outerHTML = us[i].outerHTML.replace(/(value=\").+\"/i,"$1\"");
				}catch(e){
					us[i].value = "";
				}
			}
		}
	}else{
		if(us.value != ""){
			try{
				us.outerHTML = us.outerHTML.replace(/(value=\").+\"/i,"$1\"");
			}catch(e){
				us.value = "";
			}
		}
	}
}

function showLoadingModalLayer(){
    if (!YAHOO.ccm.container.wait) {
        YAHOO.ccm.container.wait = new YAHOO.widget.Panel("wait", {
        	width: "240px", 
            fixedcenter: true, 
            close: false, 
            draggable: false, 
            zindex:4,
            modal: true,
            visible: false
       	});
        YAHOO.ccm.container.wait.setHeader("Loading, please wait...");
       	YAHOO.ccm.container.wait.setBody("<img src=\"include/images/loadingbar.gif\"/>");
        YAHOO.ccm.container.wait.render(document.body);
    }
	YAHOO.ccm.container.wait.show();
}

function hideLoadingModalLayer(){
	if (YAHOO.ccm.container.wait) YAHOO.ccm.container.wait.hide();
}

function showError(o){
	hideLoadingModalLayer();
	if (o.status == 401) {
		window.location = 'login.action';
	} else {
		var msg = "Error transaction Id: " + o.tId + ", status: " + o.statusText;
	    //var dlgRSWarning= new YAHOO.widget.Dialog('__divOpenDestWarning', { modal:true, visible:false, width:"550px", fixedcenter:true, constraintoviewport:true, draggable:false });
		//dlgRSWarning.render();
		//dlgRSWarning.cfg.setProperty('postmethod','none');
		//dlgRSWarning.cfg.setProperty('close',true);
		
		if (o.status == 403) {
			msg = "Access Denied : Your location is not authorized!";
		} 
		alert(msg);
	}
}

YAHOO.ccm.container.SysError= function()
{
	try{
		
		var oDIVSystemError=document.getElementById("__DIVSystemError");
		if(oDIVSystemError) {
			oDIVSystemError.parentNode.removeChild(oDIVSystemError);
		}
		
		oDIVSystemError = document.createElement("div");
		oDIVSystemError.id = "__DIVSystemError";
		oDIVSystemError.style.left="0px";
		oDIVSystemError.style.width="100%";
		document.body.appendChild(oDIVSystemError); 
		
		///////////////////////////////////////////
		var oObjectSysErrHD = document.createElement("div");
		oObjectSysErrHD.id = "__DIVSystemErrorHD";
		oObjectSysErrHD.className="hd";
		/********START-UIS******/
		/* 	Comment_Desc	: Display Message instead of System Error*/
		/**************/
		oObjectSysErrHD.innerHTML="Message";
		/********END-UIS******/
		oDIVSystemError.appendChild(oObjectSysErrHD); 
		
		///////////////////////////////////////////
		var oSysErrBODY = document.createElement("div");
		oSysErrBODY.className="leftMargin";		
		oDIVSystemError.appendChild(oSysErrBODY);		
		
		var sb = [];		
		sb[sb.length] ='<div class="errorImage"><img id="__DIVSystemErrorMsgIMG" src="include/images/errorimage.gif" width="32" height="32"></div>';
		sb[sb.length] ='<div class="errorMessage" id="__DIVSystemErrorMsg" >Database is down.<br>Please try again after some time.</div>';
		sb[sb.length] ='<div style="clear:both"></div>';
		sb[sb.length] ='<div class="buttonSet">';
		//sb[sb.length] ='<input id="__btnSysErrDlgCancel" class="normalButton" onclick="serr.dlgSysErr.hide();" type="button" value=" ok ">';
		sb[sb.length] ='<div style="float:right; margin-bottom:15px;"><input id="__btnSysErrDlgCancel" class="normalButton" onclick="serr.dlgSysErr.hide();serr.prepareCloseEvent();" type="button" value="ok" style="width:40px;"></div>';
		sb[sb.length] ='</div>';
		oSysErrBODY.innerHTML=sb.join("");
		
		////////////////////////////////////////////////////
		this.dlgSysErr= new YAHOO.widget.Dialog('__DIVSystemError' , { modal:true, visible:false, width:"430px", fixedcenter:true, constraintoviewport:true, draggable:false });
		this.dlgSysErr.render();
		this.dlgSysErr.cfg.setProperty('postmethod','none'); 
		this.dlgSysErr.cfg.setProperty('close',false);
		
	}catch(e) {alert("prepareSysErrorDlg " + e.description);}
}


YAHOO.ccm.container.SysError.prototype.prepareCloseEvent=function(){
   	/*var oDIVSystemErrorMsg=document.getElementById("__DIVSystemErrorMsg");
	var Message="";
	if(oDIVSystemErrorMsg) {
		Message=oDIVSystemErrorMsg.innerHTML.replace("<br>","\n");
	}*/
	this.onCloseErrorDialog(this.Message);
};


//CloseErrorDialog event
YAHOO.ccm.container.SysError.prototype.onCloseErrorDialog=function(){};

YAHOO.ccm.container.SysError.prototype.setErrorMsg = function(Message)
{
	try{
		var oDIVSystemErrorMsg=document.getElementById("__DIVSystemErrorMsg");
		if(oDIVSystemErrorMsg) {
			Message=Message.replace("\n","<br>");
			oDIVSystemErrorMsg.innerHTML=Message;
		}
		this.dlgSysErr.show();
	}catch(e) {alert("setErrorMsg " + e.description);}
}

YAHOO.ccm.container.SysError.prototype.processResponseXMLforError= function(ResponseXML,prevDLG)
{
	try{
		var errResult = ResponseXML.getElementsByTagName('Err');		
		if(errResult.length>0) {
			if(prevDLG!=null) prevDLG.hide();
			// Code added for decoding the french charecters in the error message
			errResult[0].firstChild.nodeValue = encodeFrenchCharsForRouteset(errResult[0].firstChild.nodeValue);
			this.Message=errResult[0].firstChild.nodeValue;			
			this.prepareError();
                       
			if(errResult[0].firstChild.nodeValue.indexOf("r_o__r_r__e")==-1)
			{		  
			  if(errResult[0].firstChild.nodeValue=='session_expired')
				{
				   window.onbeforeunload = null;				   
                   document.location='login.do';
				   return false;	 
				}
				else
				{
					this.setErrorMsg(errResult[0].firstChild.nodeValue);
				}
			}
			else
	        this.setErrorMsg(errResult[0].firstChild.nodeValue.substring(errResult[0].firstChild.nodeValue.indexOf("r_o__r_r__e")+11));

			return false;
		} else return true;
	}catch(e) {alert("processResponseXMLforError " + e.description);}
}

//Sudip 24-04-07
YAHOO.ccm.container.SysError.prototype.prepareError= function()
{
	var head=YAHOO.util.Dom.get("__DIVSystemErrorHD");
	if(head!=null) head.innerHTML="Message";
	var Img=YAHOO.util.Dom.get("__DIVSystemErrorMsgIMG");	
	if(Img!=null) Img.src="include/images/errorimage.gif";
}
YAHOO.ccm.container.SysError.prototype.prepareMoreInfo= function()
{
	var head=YAHOO.util.Dom.get("__DIVSystemErrorHD");
	if(head!=null) head.innerHTML="Information";
	var Img=YAHOO.util.Dom.get("__DIVSystemErrorMsgIMG");	
	if(Img!=null) Img.src="include/images/information.gif";
}


YAHOO.ccm.container.SysError.prototype.processResponseXMLforMoreInfo= function(ResponseXML,prevDLG)
{
	try{
		var errResult = ResponseXML.getElementsByTagName('DBUpdateStatus');		
		if(errResult.length>0) {
			if(errResult[0].getAttribute("MoreInfo")!=null)
			{
				if(prevDLG!=null) prevDLG.hide();
				
				this.prepareMoreInfo();
				
				this.setErrorMsg(errResult[0].getAttribute("MoreInfo"));
				return false;
			}else
				return true;
			
		} else return true;
	}catch(e) {alert("processResponseXMLforMoreInfo " + e.description);}
}

//Verify date is valid yyyy/mm/dd
function isValidDate(time){
	if(time == "") return true;
	//modified by xinyu.Liu on 2010.5.21 for CCM-241
	var r = new RegExp("^[1-2]\\d{3}/(0?[1-9]||1[0-2])/(0?[1-9]||[1-2][0-9]||3[0-1])$");
	if(r.test(time)){
			var dtToChk = new Date(time);  
			var strYear = dtToChk.getFullYear();  
			var month = dtToChk.getMonth() + 1;
			var strMonth;
			var strDate;
			if(month <10){
				strMonth = "0" + month ;  
			}else{
				strMonth = dtToChk.getMonth() + 1 + "";  
			}
			if(dtToChk.getDate() <10){
				strDate = "0" + dtToChk.getDate();  
			}else{
				strDate = dtToChk.getDate();  
			}
			var strTheDate= strYear + "/" + strMonth + "/" + strDate;  
				         
			if(strTheDate != time){ 
				return false;
			}else{
				return true;
			}  
	}else{
		return false;
	}
}

//Verify date is valid yyyy-mm-dd
function isValidDateFormat(time){
	if(time == "") return true;
	var r = new RegExp("^[1-2]\\d{3}-(0?[1-9]||1[0-2])-(0?[1-9]||[1-2][0-9]||3[0-1])$");
	if(r.test(time)){
			var ti = time.replace('-','/');
			ti = ti.replace('-','/');
			var dtToChk = new Date(ti);  
			var strYear = dtToChk.getFullYear();  
			var month = dtToChk.getMonth() + 1;
			var strMonth;
			var strDate;
			if(month <10){
				strMonth = "0" + month ;  
			}else{
				strMonth = dtToChk.getMonth() + 1 + "";  
			}
			if(dtToChk.getDate() <10){
				strDate = "0" + dtToChk.getDate();  
			}else{
				strDate = dtToChk.getDate();  
			}
			var strTheDate= strYear + "-" + strMonth + "-" + strDate;  
				         
			if(strTheDate != time){ 
				return false;
			}else{
				return true;
			}  
	}else{
		return false;
	}
}

//Size Date
function correct(timeStarts,timeEnds){
	var dtToChk = new Date(timeStarts);
	var dtToChkTwo = new Date(timeEnds);
	if((dtToChk - dtToChkTwo) <= 0){
		return true;
	}else{
		return false;
	}
}

//Echo drop-down list
function echoDropDownListByValue(divId,keyId){
	var opt = document.getElementById(divId).options;
	for(var i = 0; i < opt.length; i++){
		if (opt[i].value == keyId) {
			opt[i].selected = true;
			return;
		}
	}
}

//Empty Value
function emptyValue(obj){
	for(var i = 0; i < obj.length; i++){
		document.getElementById(obj[i]).value = "";
	}
}

//Escape Linebreak
function escapeLineBreak(str) {
	if (str == null || str == "") {
		return "";
	}
	str = str.replace(new RegExp("\r\n","gm"),"\\n");
	str = str.replace(new RegExp("\n\r","gm"),"\\n");
	str = str.replace(new RegExp("\n","gm"),"\\n");
	str = str.replace(new RegExp("\r","gm"),"\\n");
	return str;
}


var type; // FileType
//Initialization FileType Star
function initializationFileType(){
	type = new Array();
	type[0] = "gif";
	type[1] = "jpg";
	type[2] = "jpeg";
	type[3] = "bmp";
	type[4] = "doc";
	type[5] = "ppt";
	type[6] = "xls";
	type[7] = "docx";
	type[8] = "pptx";
	type[9] = "xlsx";
	type[10] = "txt";
	type[11] = "png";
	type[12] = "pdf";
	type[13] = "tif";
	type[14] = "eml";
	type[15] = "msg";
	type[16] = "csv";
}
initializationFileType();


function uploadFileValidate(id,maxSize){

	var uploads;
	var size = 0;
	if(id==null || id == ""){
		uploads = document.getElementsByName("uploads");
	}else{
		uploads=$("#"+id).find("input[name=uploads]");
	}
	var boo = true;

	for(var i=0;i<uploads.length;i++){
		if(uploads[i].value){
			for (var k=0;k<uploads[i].files.length;k++) {
				
				var fileSplit = uploads[i].files[k].name.split(".");
				var fileType = fileSplit[fileSplit.length-1].toLowerCase();
				
				var typeBoo = 0;
				for(var j=0;j<type.length;j++){
					if(fileType == type[j]){
						typeBoo += 1;
					}
				}

				if(typeBoo != 1){
					boo = false;
				}else{
					uploads[i].style.color="";
				}
				size = size+uploads[i].files[k].size;
			}
			
		}
	}
	if (maxSize){
		if(size>maxSize){
			
			alert("Attachments cannot be larger than "+parseInt(maxSize)/1024000+"M.");
			return false;
		} 
	}
	
	
	if(boo){
		return true;
	}else{
		alert("Please enter the following file format:.gif;.jpg;.bmp;.doc;.ppt;.xls;.docx;.pptx;.xlsx;.txt;.png;.pdf;.msg;.tif;.eml;.csv!");
		return false;
	}
}


renderTimeErrorMessage = function(divId,msg, m){
    if (renderTimeErrorMessage.timeout) {
        clearTimeout(renderTimeErrorMessage.timeout);
        $("#"+divId).html("");
    }
	$("#"+divId).html(msg);
    if (msg != "") {
    	renderTimeErrorMessage.timeout = setTimeout(function(){
            $("#"+divId).html("");
        }, m);
    }
};

