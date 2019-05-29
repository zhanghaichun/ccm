customMotiScoa = function(did,data,value){
	var C = customMotiScoa;
	C[did] = {};
	C[did].id = did;
	C[did].data = data;
	C[did].value = value;
	C[did].index = 0;
	
	var div = document.getElementById(did);
	
	var table = document.createElement("table");
	div.appendChild(table);
	
	var tbody = document.createElement("tbody");
	tbody.id = did+"_tbody";
	table.appendChild(tbody);
	
	var htr = document.createElement("tr");
	tbody.appendChild(htr);
	
	var tdh1 = document.createElement("td");
	var tdh2 = document.createElement("td");
	var tdh3 = document.createElement("td");
	htr.appendChild(tdh1);
	htr.appendChild(tdh2);
	htr.appendChild(tdh3);
	tdh1.width = "275px";
	tdh1.innerHTML = "<b>SCOA</b>";
	tdh2.innerHTML = "<b>Payment Amount</b>";
	tdh3.innerHTML = "&nbsp;";
	
	var tr1 = document.createElement("tr");
	tbody.appendChild(tr1);
	var td11 = document.createElement("td");
	var td12 = document.createElement("td");
	var td13 = document.createElement("td");
	tr1.appendChild(td11);
	tr1.appendChild(td12);
	tr1.appendChild(td13);

	var cdiv1 = document.createElement("div");
	cdiv1.id = did + "_cdiv" + C[did].index;
	td11.appendChild(cdiv1);

	td13.innerHTML = "<input type='button' value='ADD SCOA' onclick='customMotiScoa.add(\""+did+"\")'/>"

	var boxFiled = "box_"+did+"_"+C[did].index;
	C[boxFiled] = $(cdiv1).flexbox(data, {
		'maxCacheBytes' : 200000,
		'highlightMatches' : true,
		'autoCompleteFirstMatch' : false,
		'paging': false,
		'width' : 268
	});
	C[boxFiled].setValue(value);
	
	td12.innerHTML = "<input type='text' style='width:97px' id='"+boxFiled+"_amount' />";
	
	C[did].index++;
	
	C.getParam = function(did){
		var C = customMotiScoa;
		var vs = [];
		for(var f in C){
			if(C[f] && f.indexOf("box_"+did) == 0){
				vs.push([C[f].getValue(),document.getElementById(f+"_amount").value]);
			};
		}
		return vs;
	};
	
	C.reset = function(did){
		var C = customMotiScoa;
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
		trn.appendChild(tdn1);
		trn.appendChild(tdn2);
		trn.appendChild(tdn3);

		var cdivn = document.createElement("div");
		cdivn.id = did + "_cdiv" + C[did].index;
		tdn1.appendChild(cdivn);
		
		var boxFiled = "box_"+did+"_"+C[did].index;
		C[boxFiled] = $(cdivn).flexbox(C[did].data, {
			'maxCacheBytes' : 200000,
			'highlightMatches' : true,
			'autoCompleteFirstMatch' : false,
			'paging': false,
			'width' : 268
		});
		C[boxFiled].setValue(C[did].value);
		
		tdn2.innerHTML = "<input type='text' style='width:97px' id='"+boxFiled+"_amount' />";
		tdn3.innerHTML = "<input type='button' value='  DELETE  ' onclick='$(this).parent().parent().remove();customMotiScoa[\""+boxFiled+"\"]=null;'/>"
		
		C[did].index++;
	};
	
	C.clear = function(did){
		var C = customMotiScoa;
		$('#'+did+' input[type="button"][value="  DELETE  "]').parent().parent().remove();
		for(var f in C){
			if(C[f] && f.indexOf("box_"+did) == 0 && f!="box_"+did+"_0"){
				C[f] = null;
			};
		}
	};
};

