modifyAdjustment = function(did,sc_data,tax_data){
	
	var trList = document.getElementById(did).getElementsByTagName("tr")
	if(trList.length > 0){
		modifyAdjustment.add(did);
		return;
	}
	var C = modifyAdjustment;
	C[did] = {};
	C[did].id = did;
	C[did].index = 0;
	
	var div = document.getElementById(did);
	
	var table = document.createElement("table");
		table.width = "95%";
		table.align = "center";
	div.appendChild(table);
	
	var tbody = document.createElement("tbody");
	tbody.id = did+"_tbody";
	table.appendChild(tbody);
	
	var htr = document.createElement("tr");
	tbody.appendChild(htr);
	
	var tdh1 = document.createElement("td");
	var tdh2 = document.createElement("td");
	var tdh3 = document.createElement("td");
	var tdh4 = document.createElement("td");
	
	tdh1.innerHTML = "<b>Tax code<b>";
	tdh2.innerHTML = "<b>SCOA for Tax</b>";
	tdh3.innerHTML = "<b>Tax Amount</b>";
	tdh4.innerHTML = "<b>&nbsp;</b>";
	htr.appendChild(tdh1);
	htr.appendChild(tdh2);
	htr.appendChild(tdh3);
	htr.appendChild(tdh4);
	
	var tr1 = document.createElement("tr");
	tbody.appendChild(tr1);
	var td11 = document.createElement("td");
	var td12 = document.createElement("td");
	var td13 = document.createElement("td");
	var td14 = document.createElement("td");
	
	var cdiv1 = document.createElement("div");
	var cdiv11 = document.createElement("div");
	cdiv1.id = did + "_cdiv1_" + C[did].index;
	td11.appendChild(cdiv1);
	td11.appendChild(cdiv11);
	cdiv11.innerHTML = "<span style=\"font:15px;color:red;vertical-align:middle;\">*</span>";
	
	var cdiv2 = document.createElement("div");
	var cdiv22 = document.createElement("div");
	cdiv2.id = did + "_cdiv2_" + C[did].index;
	td12.appendChild(cdiv2);
	td12.appendChild(cdiv22);
	cdiv22.innerHTML = "<span style=\"font:15px;color:red;vertical-align:middle;\">*</span>";
	
	tr1.appendChild(td11);
	tr1.appendChild(td12);
	
	
	var sc_boxFiled = "box_sc_"+did+"_"+C[did].index;
	C[sc_boxFiled] = $(cdiv2).flexbox(sc_data, {
		'maxCacheBytes' : 200000,
		'highlightMatches' : true,
		'autoCompleteFirstMatch' : false,
		'paging': false,
		'width' : 260,
		onSelect : function(){
		}
	});
	var tax_boxFiled = sc_boxFiled+"_tax";
	C[tax_boxFiled] = $(cdiv1).flexbox(tax_data, {
		'maxCacheBytes' : 200000,
		'highlightMatches' : true,
		'autoCompleteFirstMatch' : false,
		'paging': false,
		'width' : 150,
		onSelect : function(){
		}
	});
	
	td13.innerHTML = "<input type='text' style='width:100px' id='"+sc_boxFiled+"_amount'/><span style=\"font:15px;color:red;vertical-align:middle;\">*</span>";
	td14.innerHTML = "<input type='button' value='Delete' onclick='modifyAdjustment.deleteItem(this,\""+did+"\",\""+sc_boxFiled+"\")'/>"
	tr1.appendChild(td13);
	tr1.appendChild(td14);
	C[did].index++;
	
	C.getParam = function(did){
		var C = modifyAdjustment;
		var vs = [];
		for(var f in C){
			if(C[f] && f.indexOf("box_sc_"+did) == 0 && f.substr(f.lastIndexOf("_"),4) != "_tax"){
				vs.push([document.getElementById(f+"_amount").value,C[f].getValue(),C[f+"_tax"].getValue(),C[f+"_tax"].getName()]);
			};
		}
		return vs;
	};
	
	C.reset = function(did){
		var C = modifyAdjustment;
		for(var f in C){
			if(C[f] && f.indexOf("box_"+did) == 0){
				C[f].setValue(C[did].value);
				document.getElementById(f+"_amount").value = "";
			};
		}
	};
	
	C.add = function(did){

		var tbody = document.getElementById(did+"_tbody");

		var trn = document.createElement("tr");
		tbody.appendChild(trn);
		var tdn1 = document.createElement("td");
		var tdn2 = document.createElement("td");
		var tdn3 = document.createElement("td");
		var tdn4 = document.createElement("td");

		var cdivn1 = document.createElement("div");
		var cdivn11 = document.createElement("div");
		cdivn1.id = did + "_cdivn1_" + C[did].index;
		tdn1.appendChild(cdivn1);
		tdn1.appendChild(cdivn11);
		cdivn11.innerHTML = "<span style=\"font:15px;color:red;vertical-align:middle;\">*</span>";
		
		
		
		var cdivn2 = document.createElement("div");
		var cdivn22 = document.createElement("div");
		cdivn2.id = did + "_cdivn2_" + C[did].index;
		tdn2.appendChild(cdivn2);
		tdn2.appendChild(cdivn22);
		cdivn22.innerHTML = "<span style=\"font:15px;color:red;vertical-align:middle;\">*</span>";

		trn.appendChild(tdn1);
		trn.appendChild(tdn2);

		
		var sc_boxFiled = "box_sc_"+did+"_"+C[did].index;
		C[sc_boxFiled] = $(cdivn2).flexbox(sc_data, {
			'maxCacheBytes' : 200000,
			'highlightMatches' : true,
			'autoCompleteFirstMatch' : false,
			'paging': false,
			'width' : 260,
			onSelect : function(){
			}
		});
		
		var tax_boxFiled = sc_boxFiled+"_tax";
		C[tax_boxFiled] = $(cdivn1).flexbox(tax_data, {
			'maxCacheBytes' : 200000,
			'highlightMatches' : true,
			'autoCompleteFirstMatch' : false,
			'paging': false,
			'width' : 150,
			onSelect : function(){
			}
		});
		
		tdn3.innerHTML = "<input type='text' style='width:100px' id='"+sc_boxFiled+"_amount'/><span style=\"font:15px;color:red;vertical-align:middle;\">*</span>";
		tdn4.innerHTML = "<input type='button' value='Delete' onclick='modifyAdjustment.deleteItem(this,\""+did+"\",\""+sc_boxFiled+"\")'/>"
		trn.appendChild(tdn3);
		trn.appendChild(tdn4);
		
		C[did].index++;
	};
	
	C.deleteItem = function(item,did,itemName){
		var C = modifyAdjustment;
		var trList = document.getElementById(did).getElementsByTagName("tr")
		if(trList.length != 2){
			$(item).parent().parent().remove();
		}else{
			$(item).parent().parent().parent().parent().remove();
		}
		modifyAdjustment[sc_boxFiled]=null;
		modifyAdjustment[tax_boxFiled]=null;
		if(C[itemName]){
			C[itemName] = null;
		};
		if(C[itemName+"_tax"]){
			C[itemName+"_tax"] = null;
		}
	
	};
};

