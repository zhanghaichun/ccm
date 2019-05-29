// Author Chao.Liu
// 2010/05/07 Friday AM
// This is JS file be called on to treatment number and date;
// Automatic verification and Automatic exchange

// Version Bate 1.2

var saveDateTextBoxBGColor = "RPG"; 
var saveNumberTextBoxBGColor = "RPG";

function get(id) {
	return typeof (id) == "string" ? document.getElementById(id) : id;
}
/**
 * Automatic string completion
 * Must have two decimal point
 */

function simValidation(type, obj) {
	var tag = get(obj.id).tagName;
	if (tag != "INPUT") {
		alert("Temporary support only text box!");
		return false;
	}
	if (obj.value == "") {
		return true;
	}
	/*****************[Validation]*****************/
	if (type == "get") {
		if (isNaN(obj.value)) {
			get(obj.id).value = "0.00";
			alert("Not Numbers!");
			return false;
		}		
        
		var index = parseInt(obj.value.indexOf("."));
		if (index == -1) {
			get(obj.id).value += ".00";
			index = parseInt(obj.value.indexOf("."));
		}
		var str = obj.value.substring(index + 1, obj.value.length);
		var leg = parseInt(str.length);
		while (leg > -1 && leg < 2) {
			get(obj.id).value += "0";
			leg++;
		}
		
		//modified by Qiao.Yang on May 12 for CCM-140
		//regex of amount:
		var str1 = obj.value.substring(0,index);
		var regularAmount =/^(0|-?[1-9][0-9]*)get/ ;
        if((!regularAmount.exec(str1))&&(str1!="")){
          alert("Please re-enter the amount following correct format: not starting with more than one 0, two digits behind '.' ");
          get(obj.id).value = "0.00";
          return false;
        }	
		
		return true;
	}

	
	if (type == "Date") {
		var regularDate = /^[1-2]\d{3}\/(0?[1-9]||1[0-2])\/(0?[1-9]||[1-2][1-9]||3[0-1])get/;
		
		// 
		if(saveDateTextBoxBGColor == "RPG"){
			saveDateTextBoxBGColor = obj.style.background;
		}
		
		obj.style.background = saveDateTextBoxBGColor;
		
		if(!regularDate.exec(obj.value)){
			obj.style.background = "#ffccff";
			alert("Date Format Error!");
			return false;
		}
		return true;
	}
}
/**
 * Automatic exchange two number or more number
 * No perfect function
 */
function formLessThanTo(format, lid, bid) {
	var arg = arguments;
	if (arg.length < 3) {
		alert("Method warning:\nMethod 'formLessThanTo' Paramer don't less than three!");
		return false;
	}
	
	if (format == "Number") {
		if (arg.length == 3) {
			if(saveNumberTextBoxBGColor == "RPG"){
				saveNumberTextBoxBGColor = get(lid).style.background;
			}
			
			get(lid).style.background = saveNumberTextBoxBGColor;
			//////////// [change to 2010/05/12 AM  ADD]  ///////////////
			get(bid).style.background = saveNumberTextBoxBGColor;
			
			
			var little = parseInt(get(lid).value);
			var big = parseInt(get(bid).value);
			if (little > big) {
				get(lid).style.background = "red";
				get(bid).style.background = "red";
				
				var strMSG = "Method warning:\n";
				strMSG += "The first values greater than the second,\n";
				strMSG += "Whether the automatic exchange position?";
				if (confirm(strMSG)) {
					var temp = get(lid).value;
					get(lid).value = get(bid).value;
					get(bid).value = temp;
				} else {
					get(bid).value = "";
				}
				get(lid).style.background = saveNumberTextBoxBGColor;
				get(bid).style.background = saveNumberTextBoxBGColor;
				return true;
			}
		}
		return false;
	}
	if (format == "Date") {
		if (get(lid).value == "" || get(bid).value == "") {return true;}
		
		if (arg.length == 3) {
			var little = new Date(get(lid).value);
			var big = new Date(get(bid).value);
			if (little - big > 0) {
				var strMSG = "Method warning:\n";
				strMSG += "The first date values greater than the second,\n";
				strMSG += "Whether the automatic exchange position?";
				if (confirm(strMSG)) {
					var temp = get(lid).value;
					get(lid).value = get(bid).value;
					get(bid).value = temp;
				} else {
					get(bid).value = "";
					
				}
			}
			return true;
		}
		return false;
	}
}
/**
 * Add by Chao.Liu date to 2010/05/13 PM
 * Simple monetary formatting
 */
function simFormat(obj, type) {
	// Get Value
	var tag = get(obj.id).tagName;
	var val = tag == "INPUT" ? obj.value : obj.innerHTML;
	if (type == "get") { // Double [HuoBi]
		var index = parseInt(val.indexOf("."));
		if (isNaN(val)) {
			alert("'" + val + "' No a Number!");
			return;
		}
		if (index == -1) {
			return;
		}
		/**************[Format]*****************/
		if (type == "get") { // Double [HuoBi]
			var str = val.substring(index + 1, val.length);
			var leg = parseInt(str.length);
			while (leg > -1 && leg < 2) {
				val += "0";
				leg++;
			}
			val = val.substring(0, (index + 3));
		}
		/**************[Value]*****************/
		if (tag == "INPUT" || tag == "input") {
			get(obj.id).value = val;
		}
		if (tag == "SPAN" || tag == "span") {
			get(obj.id).innerHTML = val;
		}
	}
}

/**
 * Rounding
 * Add by Chao.Liu date to 2010/05/13 AM
 */
function simRounding(number,several){
	var result = "";
	var arg = arguments;
	if (arg.length < 1) {
		alert("Method warning:\nMethod 'simRounding' Paramer don't less than one!");
		return false;
	}else if(arg.length > 4){
		alert("Method warning:\nMethod 'simRounding' Paramer don't More than four!");
		return false;
	}
	
	if(number == ""){return true;}
	
	if (isNaN(number)) {
			alert("Not Numbers!");
			return false;
	}
	
	var index = parseInt(number.indexOf("."));
	
	if(index == 0){
		number = "0" + number;
	}
	
	var front = "";
	var behind = "";
	var str = "";
	var leg = 0;
	
	if(arguments[2] == "." || arguments[2] == "decimal"){
		if(arguments[1] == null || several == 0){
	    	result = Math.round(number);
	    }else{
			if(index == -1){
				number += ".";
				leg = index + 2;
			}else{
				front = number.substring(0, (index+1));
				behind = number.substring((index+1), number.length);
			
				str = behind.substring(0, behind.length);
				leg = parseInt(str.length)+1;
			
			}
			while (leg <= several) {
				number += "0";
				leg++;
			}
			result = number;
			if(leg > several+1){
				var isAdd = parseInt(behind.substring(several,several+1));
				if(isAdd < 5){
					return front+behind.substring(0,several);
				}else{ 
					number = front+behind.substring(0,several);
					
					front = number.substring(0,index);
					behind = number.substring((index+1),number.length);
					
					var endNum = (parseInt(behind) + 1).toString();
					if(endNum.toString().length > behind.length){
						front = (parseInt(front) + 1).toString();
						endNum = endNum.substring(1,endNum.length);
					}
					
					result = front + "." + endNum;
				}
			}
		}
	}else{ // It have decimal
	    if(arguments[1] == null || several == 0){
	    	result = Math.round(number);
	    }else{
			if(index != -1){
				number = number.substring(0, index);
			}
			
			var numLeg = number.length;
			if(numLeg <= several){
				result = 0;
			}else{
				fron = number.substring(0, (numLeg-several));
				behind = number.substring((numLeg-several), numLeg);
			
				var isAdd = parseInt(behind.substring(0,1));
				if(isAdd > 4){
					fron = parseInt(fron)+1;
				}
				var rep = "";
				while(rep.length < several){
					rep += "0";
				}
				result = fron.toString() + rep;
			}
		}
		
	}
	
	if(arguments[3] != null && typeof(arguments[3]) == "string"){
		var tag = get(arguments[3]).tagName;
		if(tag == "INPUT" || tag == "input"){
			get(arguments[3]).value = result;
			return true;
		}
	}else{
		return result;
	}
	return false;
}

/**
 * Add by Chao.Liu date to 2010/05/13 PM
 * Clear number befor "0";
 */
function clearBeforZero(str){
	if(arguments.length < 1 || arguments.length > 2){
		alert("Method warning:\nMethod 'clearBeforZero' Paramer Error!");
		return false;
	}
	
	if(str == ""){return true;};
	
	var index = 1;
	if(parseInt(str.indexOf(".")) == 0){
		str = "0" + str;
	}
	if(parseInt(str.indexOf(".")) != -1){
		index = str.substring(parseInt(str.indexOf("."))-1).length;
	}
	
	
	while(str.substring(0,1) == "0" && str.length > index){
		str = str.substring(1,str.length);
	}
	if(arguments[1] != null && typeof(arguments[1]) == "string"){
		var tag = get(arguments[1]).tagName;
		if(tag == "INPUT" || tag == "input"){
			get(arguments[1]).value = str;
			return true;
		}
	}else{
		return str;
	}
	
	return false;
}
 
