function clearTargetVerficatoinValidateHint(){
	
	for(var i in rightState){
		//1.vendor
		if(allVender[i].getValue() == '-1'){
			
		}else{
			removeClassName(document.getElementById("venderList"+i+"_input"),'validation-failed');
			document.getElementById("venderList"+i).title="";
		}
		
		//2.ban = -1的检查
		if(allBan[i].getValue() == '-1'){
			removeClassName(document.getElementById("banList"+i+"_input"),'validation-failed');
			document.getElementById("banList"+i).title="";
		}

		//3.��֤ vendor �� ban
		if((allVender[i].getValue() == 'all' | allVender[i].getValue() == '') && (allBan[i].getValue() == '' | allBan[i].getValue() == 'all' )){
		}else{
			removeClassName(document.getElementById("venderList"+i+"_input"),'validation-failed');
			removeClassName(document.getElementById("banList"+i+"_input"),'validation-failed');
			document.getElementById("venderList"+i).title="";
			document.getElementById("banList"+i).title="";
		}
		
		//4.��֤Period from(YYYYmm) 
		if(!/\d{4}1[012]|\d{4}0[1-9]/.test($("#periodFrom"+i).val())){
		}else{
			removeClassName(document.getElementById("periodFrom"+i),'validation-failed');
			document.getElementById("periodFrom"+i).title="";
		}
		
		//5.��֤Period to > period from
		if($("#periodFrom"+i).val() > $("#periodTo"+i).val()){
		}else{
			removeClassName(document.getElementById("periodFrom"+i),'validation-failed');
			removeClassName(document.getElementById("periodTo"+i),'validation-failed');
			document.getElementById("periodFrom"+i).title="";
			document.getElementById("periodTo"+i).title="";
		}
		
		//6.��֤Allowed Period Shift
		if(!/^([1-9]|1[0-2])$/.test($("#aps"+i).val())){
		}else{
			removeClassName(document.getElementById("aps"+i),'validation-failed');
			document.getElementById("aps"+i).title="";
		}
		
		//7.��֤circuitList
		if(($("#circuitList"+i).val() != "all" &&  (allBan[i].getValue() == '' | allBan[i].getValue() == 'all' )) | $("#circuitList"+i).val() == "-1"){
		}else{
			removeClassName(document.getElementById("circuitList"+i),'validation-failed');
			document.getElementById("circuitList"+i).title="";
		}
		
		//8.��֤Target Amount
		if($("#chargetTypeList"+i+"  option:selected").text()=="Credit"){
			if(/^[-]\d{0,15}(\.\d{1,2})?$/g.test($("#ta"+i).val()) | $("#ta"+i).val()==0){
				removeClassName(document.getElementById("ta"+i),'validation-failed');
				document.getElementById("ta"+i).title="";
			}else{
				
			}
		}else{
			if(/^[-]?\d{0,15}(\.\d{1,2})?$/g.test($("#ta"+i).val()) | $("#ta"+i).val()==0){
				removeClassName(document.getElementById("ta"+i),'validation-failed');
				document.getElementById("ta"+i).title="";
			}else{
			}
		}
		
		//9.��֤Change Amount��Ϣ
		if(!/^\d{0,15}(\.\d{1,2})?$/g.test($("#ca"+i).val())){
		}else{
			removeClassName(document.getElementById("ca"+i),'validation-failed');
			document.getElementById("ca"+i).title="";
		}
		
		//10.��֤Change Percentage��Ϣ
		if(!/^\d{0,3}(\.\d{1,2})?$/g.test($("#"+i).val())){
		}else{
			removeClassName(document.getElementById("cp"+i),'validation-failed');
			document.getElementById("cp"+i).title="";

		}
		
		//11.��֤Allowed Variation Percentage(%)��Ϣ
		if(!/^\d{0,3}(\.\d{1,2})?$/g.test($("#avp"+i).val())){
		}else{
			removeClassName(document.getElementById("avp"+i),'validation-failed');
			document.getElementById("avp"+i).title="";
		}
		
		//12.验证Change Amount存在时Allowed Period Shift应>0
		if($("#ca"+i).val()!='' && ($("#aps"+i).val()==0 | $("#aps"+i).val()=='')){
			
		}else{
			removeClassName(document.getElementById("ca"+i),'validation-failed');
			document.getElementById("ca"+i).title="";
		}
		
		//13.验证Change Percentage(%)存在时Allowed Period Shift应>0
		if($("#cp"+i).val()!='' && ($("#aps"+i).val()==0 | $("#aps"+i).val()=='')){

		}else{
			removeClassName(document.getElementById("cp"+i),'validation-failed');
			document.getElementById("cp"+i).title="";
		}
		
		//14.验证Target Amount、Change Amount、Change Percentage(%)只能存在一个
		if(($("#ta"+i).val()!='' && $("#ca"+i).val()!='')
				|($("#ta"+i).val()!='' && $("#cp"+i).val()!='') 
				| ($("#ca"+i).val()!='' && $("#cp"+i).val()!='')){
			
		}else{
			removeClassName(document.getElementById("ta"+i),'validation-failed');
			removeClassName(document.getElementById("ca"+i),'validation-failed');
			removeClassName(document.getElementById("cp"+i),'validation-failed');
			document.getElementById("ta"+i).title="";
			document.getElementById("ca"+i).title="";
			document.getElementById("cp"+i).title="";
		}
		//15.验证
		if($("select[id='chargetTypeList"+i+"'] option:selected").text()=='All'){
			validataFlag=false;
			
		}else{
			removeClassName(document.getElementById("chargetTypeList"+i),'validation-failed');
			document.getElementById("chargetTypeList"+i).title="";
		}
		
		//16.验证
		var periodFromYear = $("#periodFrom"+i).val().substring(0,4);
		var periodFromMonth = $("#periodFrom"+i).val().substring(4,6);
		
		var periodToYear = $("#periodTo"+i).val().substring(0,4);
		var periodToMonth = $("#periodTo"+i).val().substring(4,6);
		
		
		var date1 = new Date(periodFromYear,periodFromMonth,1);
		var date2 = new Date(periodToYear,periodToMonth,1);
		var startYear = date1.getFullYear();
		var endYear = date2.getFullYear();
		var year2Month = (endYear-startYear)*12;
		var startMonth = date1.getMonth();
		var endMonth = date2.getMonth();
		var month1 = endMonth-startMonth;
		var count = (year2Month+month1)+1;

		
		if(count < 12){
//			var diffMonth = Math.ceil(12/count);
			var diffMonth = Math.floor(12/count);
			if($("#aps"+i).val() < diffMonth){
				removeClassName(document.getElementById("aps"+i),'validation-failed');
				document.getElementById("aps"+i).title="";
			}
			removeClassName(document.getElementById("periodFrom"+i),'validation-failed');
			removeClassName(document.getElementById("periodTo"+i),'validation-failed');
			document.getElementById("periodFrom"+i).title="";
			document.getElementById("periodTo"+i).title="";
		}
	}
	
}


function TargetVerficatoinValidateHint(){
	clearTargetVerficatoinValidateHint();
	var validataFlag=true;
	
	for(var i in rightState){
		//1.vendor=-1区分大小写
		if(allVender[i].getValue() == '-1'){
			validataFlag=false;
			addClassName(document.getElementById("venderList"+i+"_input"),'validation-failed');
			document.getElementById("venderList"+i).title="Please input a valid varchar(Case sensitive).";
		}
		//2.ban=-1区分大小写
		if(allBan[i].getValue() == '-1'){
			validataFlag=false;
			addClassName(document.getElementById("banList"+i+"_input"),'validation-failed');
			document.getElementById("banList"+i).title="Please input a valid varchar(Case sensitive).";
		}
		//3.��֤ vendor �� ban
		if((allVender[i].getValue() == 'all' | allVender[i].getValue() == '') && (allBan[i].getValue() == '' | allBan[i].getValue() == 'all' )){
			validataFlag=false;
			addClassName(document.getElementById("venderList"+i+"_input"),'validation-failed');
			addClassName(document.getElementById("banList"+i+"_input"),'validation-failed');
			document.getElementById("venderList"+i).title="A valid Vendor or BAN must be entered";
			document.getElementById("banList"+i).title="A valid Vendor or BAN must be entered";
		}
		
		//4.��֤Period from(YYYYmm) 
		if(!/\d{4}1[012]|\d{4}0[1-9]/.test($("#periodFrom"+i).val())){
			validataFlag=false;
			addClassName(document.getElementById("periodFrom"+i),'validation-failed');
			document.getElementById("periodFrom"+i).title="YYYYmm";
		}
		
		//5.��֤Period to(YYYYmm)
		if(!/\d{4}1[012]|\d{4}0[1-9]/.test($("#periodTo"+i).val())){
			validataFlag=false;
			addClassName(document.getElementById("periodTo"+i),'validation-failed');
			document.getElementById("periodTo"+i).title="YYYYmm";
		}
		//6.��֤Period to > period from
		if($("#periodFrom"+i).val() > $("#periodTo"+i).val()){
			validataFlag=false;
			addClassName(document.getElementById("periodFrom"+i),'validation-failed');
			addClassName(document.getElementById("periodTo"+i),'validation-failed');
			document.getElementById("periodFrom"+i).title="Period to(YYYYmm)>Period from(YYYYmm)";
			document.getElementById("periodTo"+i).title="Period to(YYYYmm)>Period from(YYYYmm)";
		}
		
		//7.��֤Allowed Period Shift
		if(!/^([1-9]|1[0-2])$/.test($("#aps"+i).val())){
			validataFlag=false;
			addClassName(document.getElementById("aps"+i),'validation-failed');
			document.getElementById("aps"+i).title="Allowed Period Shift must be an integer(1~12)";
		}
		//8.��֤circuitList
		if(($("#circuitList"+i).val() != "all" &&  (allBan[i].getValue() == '' | allBan[i].getValue() == 'all' )) | $("#circuitList"+i).val() == "-1"){
			validataFlag=false;
			addClassName(document.getElementById("circuitList"+i),'validation-failed');
			document.getElementById("circuitList"+i).title="Please input a valid data";
		}
		//9.��֤Target Amount
		if($("#chargetTypeList"+i+"  option:selected").text()=="Credit"){
			if(/^[-]\d{0,15}(\.\d{1,2})?$/g.test($("#ta"+i).val()) || $("#ta"+i).val()==0 || /^\d{0,15}(\.\d{1,2})?$/g.test($("#ta"+i).val())) {
				
			}else{
				validataFlag=false;
				addClassName(document.getElementById("ta"+i),'validation-failed');
				document.getElementById("ta"+i).title="Please input a valid number";
			}
		}else{
			
			if($("#ta"+i).val() != "" && !/^\d{1,15}(\.\d{1,2})?$/.test($("#ta"+i).val()))
			{   						
				validataFlag=false;
				addClassName(document.getElementById("ta"+i),'validation-failed');
				document.getElementById("ta"+i).title="Please input a valid number";
			}
		/*	if(/^[-]?\d{0,20}(\.\d{1,2})?$/g.test($("#ta"+i).val()) | $("#ta"+i).val()==0){
				
			}else{
				validataFlag=false;
				addClassName(document.getElementById("ta"+i),'validation-failed');
				document.getElementById("ta"+i).title="Please input a valid number";
			}*/
		}
		//10.��֤Change Amount��Ϣ
		if(!/^\d{0,15}(\.\d{1,2})?$/g.test($("#ca"+i).val())){
			validataFlag=false;
			addClassName(document.getElementById("ca"+i),'validation-failed');
			document.getElementById("ca"+i).title="Please input a valid number";
		}

		if($("#ca"+i).val()!="" && !/^\d{1,15}(\.\d{1,2})?$/.test($("#ca"+i).val()))
		{
			validataFlag=false;
			addClassName(document.getElementById("ca"+i),'validation-failed');
			document.getElementById("ca"+i).title="Please input a valid number";
		}
				
		//11.��֤Change Percentage��Ϣ
		if(!/^\d{0,3}(\.\d{1,2})?$/g.test($("#cp"+i).val())){
			validataFlag=false;
			addClassName(document.getElementById("cp"+i),'validation-failed');
			document.getElementById("cp"+i).title="Please input a valid number(A maximum of 3 bit integer, decimal number 2)";
		}
		
		//12.��֤Allowed Variation Percentage(%)��Ϣ
		if(!/^\d{0,3}(\.\d{1,2})?$/g.test($("#avp"+i).val())){
			validataFlag=false;
			addClassName(document.getElementById("avp"+i),'validation-failed');
			document.getElementById("avp"+i).title="Please input a valid number(A maximum of 3 bit integer, decimal number 2)";
		}
		
		//13.验证Change Amount存在时Allowed Period Shift应>0
/*		if($("#ca"+i).val()!='' && ($("#aps"+i).val()==0 | $("#aps"+i).val()=='')){
			validataFlag=false;
			addClassName(document.getElementById("ca"+i),'validation-failed');
			document.getElementById("ca"+i).title="Please Input Allowed Period Shift(And >0)";
		}*/
		
		//14.验证Change Percentage(%)存在时Allowed Period Shift应>0
		
/*		if($("#cp"+i).val()!='' && ($("#aps"+i).val()==0 | $("#aps"+i).val()=='')){
			validataFlag=false;
			addClassName(document.getElementById("cp"+i),'validation-failed');
			document.getElementById("cp"+i).title="Please Input Allowed Period Shift(And >0)";
		}*/
		
/*		//Target Amount 存在时Allowed Period Shif 
		if($("#ta"+i).val()!='' && ($("#aps"+i).val()==0 | $("#aps"+i).val()=='')){
			validataFlag=false;
			removeClassName(document.getElementById("aps"+i),'validation-failed');
			document.getElementById("aps"+i).title="Please Input Allowed Period Shift(And >0)";
		}*/
		//15.验证Target Amount、Change Amount、Change Percentage(%)只能存在一个
		
		if(($("#ta"+i).val()!='' && $("#ca"+i).val()!='')
				|($("#ta"+i).val()!='' && $("#cp"+i).val()!='') 
				| ($("#ca"+i).val()!='' && $("#cp"+i).val()!='')){
			validataFlag=false;
			addClassName(document.getElementById("ta"+i),'validation-failed');
			addClassName(document.getElementById("ca"+i),'validation-failed');
			addClassName(document.getElementById("cp"+i),'validation-failed');
			document.getElementById("ta"+i).title="Please input a valid number";
			document.getElementById("ca"+i).title="Please input a valid number";
			document.getElementById("cp"+i).title="Please input a valid number";
		}
		
		//16.验证
		if($("select[id='chargetTypeList"+i+"'] option:selected").text()=='All'){
			validataFlag=false;
			addClassName(document.getElementById("chargetTypeList"+i),'validation-failed');
			document.getElementById("chargetTypeList"+i).title="Please input a valid number";
		}
		
		//17. 计算shift 值
		var periodFromYear = $("#periodFrom"+i).val().substring(0,4);
		var periodFromMonth = $("#periodFrom"+i).val().substring(4,6);
		
		var periodToYear = $("#periodTo"+i).val().substring(0,4);
		var periodToMonth = $("#periodTo"+i).val().substring(4,6);
		
		
		var date1 = new Date(periodFromYear,periodFromMonth,1);
		var date2 = new Date(periodToYear,periodToMonth,1);
		var startYear = date1.getFullYear();
		var endYear = date2.getFullYear();
		var year2Month = (endYear-startYear)*12;
		var startMonth = date1.getMonth();
		var endMonth = date2.getMonth();
		var month1 = endMonth-startMonth;
		var count = (year2Month+month1)+1;

		
		if(count > 12){
			validataFlag=false;
			addClassName(document.getElementById("periodFrom"+i),'validation-failed');
			addClassName(document.getElementById("periodTo"+i),'validation-failed');
			document.getElementById("periodFrom"+i).title="Period to(YYYYmm)~Period from(YYYYmm) Out of range";
			document.getElementById("periodTo"+i).title="Period to(YYYYmm)~Period from(YYYYmm) Out of range";
		}else{
//			var diffMonth = Math.ceil(12/count);
			var diffMonth = Math.floor(12/count);
			if($("#aps"+i).val() >diffMonth){
				validataFlag=false;
				addClassName(document.getElementById("aps"+i),'validation-failed');
				document.getElementById("aps"+i).title="Please input a valid number";
			}
		}
	}
	
	return validataFlag;
	
}