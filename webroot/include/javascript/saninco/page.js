/**
 * @author Jian.Dong 
 */
(function(){
	if(typeof SANINCO != 'object'){SANINCO={};}
	var G = function(id){
		return document.getElementById(id);
	};
	var C = YAHOO.util.Connect;
	var S = showLoadingModalLayer;
	var H = hideLoadingModalLayer;
	
    var P = function(divId, globalName, c){
		var TO = this;
		this.did = divId;
		this.gn = globalName;
		this.autoToTop = true;
		this.autoToLeft = true;
		this.scrollLeft = 0;
		this.base = "";
    	this.pageNo = 1;
		this.height = "30px";
		this.recPerArray = [10,15,20,30,50,80,100,200];
		this.paginationDiv = null;
		this.paginationCustomDiv = null;
		this.otherPaginationDivs = [];
		this.recPerPage = 10;
		this.totalPageNo = "";
		this.totalResultNo = "";
		this.sortingField = "";
		this.sortingDirection = "";
		this.vo = "";
		this.resultBegin = null;
		this.resultEnd = null;
		this.paramStr = "";
		this.totalPageUri = "";
		this.dataUri = "";
		this.cols = [];//title,dataField,sort,width, display,className, notDownload
		this.tableWidth = "100%";
		this.voParam = null;
		this.myParam = null;
		this.param = null;
		this.SUCCESS = [];
		this.TOTAL_SUCCESS = [];
		this.BEFORE_SEARCH = [];
		this.COMPLETE = [];
		this.pageTable = "block"; //auto,none,block
		this.pageTitle = "block"; //auto,none,block
		this.thNowrap="nowrap";
		this.filter = null;
		this.scrollTop = false;
		this.noRecordText = '&nbsp;&nbsp;&nbsp;&nbsp;No records found.';
		this.recordText = 'Search Results:';
		TO.showCustomLoadingModalLayer = null;
		TO.hideCustomLoadingModalLayer = null;
		var connection = null;
        if (c) {
            for (var m in c) {
				if(typeof c[m] == 'function'){
					this[m] = c[m]();
				}else{
	                this[m] = c[m];
				}
            }
        }
		this.recPerPage = this.recPerArray[0];
		
		if(this.vo!=""){
			this.vo += ".";
		}
		this.setFilter = function(f){
			this.filter = f;
		};
		this.init = function(){
			if(TO.paginationDiv!= null && TO.paginationDiv.indexOf(",") != -1){
				var otherPaginationDivs = TO.paginationDiv.split(",");
				for(var h=0;h<otherPaginationDivs.length;h++){
					if(h == 0){
						TO.paginationDiv = otherPaginationDivs[h];
					}else{
						TO.otherPaginationDivs.push(otherPaginationDivs[h]);
					}
				}
				
			}
			var html =  (TO.autoToTop ? ('<a name="NAME_'+TO.gn+'" href="#NAME_'+TO.gn+'" id="a_by_NAME_'+TO.gn+'"></a>') : '');
				html += '<div id="_table_'+TO.gn+'"></div>';
			var phtml = '<table  border="0" width="100%" id="_paginationTable_'+TO.gn+'" style="display: none;">';
			phtml += 	    '<tr>';
			//phtml += 			'<td align="left" style="padding-right: 15px;">';
			phtml += 			'<td align="left" style="">';
			phtml += 				'<img src="include/images/button_previous_first.gif" title="First page" onclick="'+TO.gn+'.getFirstPage();">';
			phtml += 				'<img src="include/images/button_previous.gif" title="Previous page" onclick="'+TO.gn+'.getPrevPage();">Page&nbsp;';
			phtml += 				'<input id="_curPageNo_'+TO.gn+'" type="text" autocomplete="off" style="width: 30px;text-align:center;padding-left:0;" value="1" onkeydown="'+TO.gn+'.getPage(this.value);">&nbsp;of&nbsp;';
			phtml += 				'<span id="_totalPageNo_'+TO.gn+'"></span>';
			phtml += 				'<img src="include/images/button_next.gif" title="Next page" onclick="'+TO.gn+'.getNextPage();">';
			phtml += 				'<img src="include/images/button_next_last.gif" title="Last page" onclick="'+TO.gn+'.getLastPage();">&nbsp;&nbsp;';
			phtml += 				'<span class="select1"><select id="_recPerPageSelect_'+TO.gn+'"onchange="'+TO.gn+'.recPerPageStart(this);this.blur();">';
			for(var h=0;h<this.recPerArray.length;h++){
				phtml += 				'<option value="'+this.recPerArray[h]+'">'+this.recPerArray[h]+'</option>'; 
			}
			phtml += 				'</select></span>';
			phtml += 			'</td>';
			phtml += 	   '</tr>';
			phtml += '</table>';
			if(this.paginationDiv == null){
				G(divId).innerHTML = html + phtml;
			}else{
				G(divId).innerHTML = html;
				G(this.paginationDiv).innerHTML = phtml;
			}
			delete html;
			delete phtml;
			//G(divId).style.marginBottom = "20px"; 
		};
		this.init();
		
		this.clean = function(){
			G('_table_'+TO.gn).innerHTML = "";
		};
		
		this.resetParam = function(){
			delete this.paramStr;
			this.paramStr = "";
			this.paramStr += (TO.vo + "pageNo=" + TO.pageNo+"&");
			this.paramStr += (TO.vo + "recPerPage=" + TO.recPerPage+"&");
			if (TO.sortingField) this.paramStr += (TO.vo + "sortField=" + TO.sortingField+"&");
			if (TO.sortingDirection) this.paramStr += (TO.vo + "sortingDirection=" + TO.sortingDirection+"&");
			if (TO.voParam) {
				var vopa = TO.voParam;
				for(var j in vopa){
					if(vopa[j])this.paramStr += (TO.vo + j + '=' + vopa[j]+"&");
				}
			}
			if (TO.param) {
				var pa = TO.param;
				for(var k in pa){
					if(pa[k])this.paramStr += (k + '=' + pa[k]+"&");
				}
			}
			if(TO.filter){
				var s = TO.filter.getCause();
				if(s)this.paramStr += (TO.vo + "filter" + '=' + s + "&");
			}
			if (TO.myParam) {
				this.paramStr = TO.paramStr + TO.myParam;
			}
		};
		
		this.getTitlesParam = function(f){
			var str = "";
			for(var i=0;i<TO.cols.length;i++){
				if (TO.cols[i].notDownload != 'Y') {
					var tt = "";
					if(typeof TO.cols[i].title == 'function'){
						tt = TO.cols[i].title(TO,true);
					}else if(typeof TO.cols[i].title == 'string'){
						tt = TO.cols[i].title;
					}
					if(typeof tt=="string"){
						
						if (i == 0) {
							if(tt.toLowerCase().indexOf('<') == -1)str += f+"="+tt;
						}else{
							if(tt.toLowerCase().indexOf('<') == -1)str += "&"+f+"="+tt;
						}
					}
				}
			}
			return str;
		};
				
		this.getSelectByDataField = function(){
			var str = "";
			var flag = true;
			for(var i=0;i<TO.cols.length;i++){
				var tt = TO.cols[i].dataField;
				if (flag) {
					str += tt;
					flag = false;
				}else{
					str += ","+tt;
				}
			}
			return str;
		};
		
		this.getSelectBySort = function(){
			var str = "";
			var flag = true;
			for(var i=0;i<TO.cols.length;i++){
				var tt = TO.cols[i].sort;
				if(tt){
					if (flag) {
						str += tt;
						flag = false;
					}else{
						str += ","+tt;
					}
					
				}
			}
			return str;
		};
	
		this.start = function(){
			TO.clean();
			for(var cc=0;cc<TO.BEFORE_SEARCH.length;cc++){
				TO.BEFORE_SEARCH[cc]("start",TO);
			}
			this.pageNo = 1;
			this.scrollLeft = 0;
			TO.resetParam();
			TO.showCustomLoadingModalLayer ? TO.showCustomLoadingModalLayer() : S();
			if(TO.totalPageUri){
				var cb = {
					success: TO.renderTable,
					failure: showError
				}; 			
				connection = C.asyncRequest('POST' , TO.totalPageUri , cb , TO.paramStr); 
			} else{
				var cb = {
					success: TO.renderPageAll,
					failure: showError
				};
				connection = C.asyncRequest('POST' , TO.dataUri , cb , TO.paramStr);
			}
		};
		
		this.recPerPageStart = function(ele){
			TO.clean();
			for(var cc=0;cc<TO.BEFORE_SEARCH.length;cc++){
				TO.BEFORE_SEARCH[cc]("recPerPageStart",TO);
			}
			var ops = G("_recPerPageSelect_"+TO.gn).options;
			for(var r=0; r<ops.length; r++){
				if(ops[r].value == ele.value){
					ops[r].selected = true;
					break;
				}
			}
			this.recPerPage = G('_recPerPageSelect_'+TO.gn).value;
			this.pageNo = 1;
			this.scrollLeft = 0;
			TO.resetParam();
			TO.showCustomLoadingModalLayer ? TO.showCustomLoadingModalLayer() : S();
			if(TO.totalPageUri){
				var cb = {
					success: TO.renderTable,
					failure: showError
				}; 			
				connection = C.asyncRequest('POST' , TO.totalPageUri , cb , TO.paramStr); 
			} else{
				var cb = {
					success: TO.renderPageAll,
					failure: showError
				};
				connection = C.asyncRequest('POST' , TO.dataUri , cb , TO.paramStr);
			}
		};
		
		this.reload = function(){
			TO.clean();
			for(var cc=0;cc<TO.BEFORE_SEARCH.length;cc++){
				TO.BEFORE_SEARCH[cc]("reload",TO);
			}
			TO.showCustomLoadingModalLayer ? TO.showCustomLoadingModalLayer() : S();
			if (TO.totalPageUri) {
				var cb = {
					success: TO.renderTable,
					failure: showError
				}; 			
				connection = C.asyncRequest('POST' , TO.totalPageUri , cb , TO.paramStr); 
			}else{
				var cb = {
					success: TO.renderPageAll,
					failure: showError
				};
				connection = C.asyncRequest('POST' , TO.dataUri , cb , TO.paramStr);
			}
		};
		
		this.renderTable = function(o){
			if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
				window.location.reload();
				return;
			}
			if(TO.autoToTop){
				try {
					document.getElementById('a_by_NAME_'+TO.gn).click();
				} catch (e) {
					window.scrollTo(0,0);
				}
			}
			var data = eval("("+o.responseText+")");
			delete o;
			for(var c=0;c<TO.TOTAL_SUCCESS.length;c++){
				TO.TOTAL_SUCCESS[c](data,TO);
			}
			if(data.error){
				if(TO.pageTitle == "block"){
					TO.renderTitle("<font color='red'>Error: " + data.error+"</font>");
				}else{
					G('_table_'+TO.gn).innerHTML = "<font color='red'>Error: " + data.error+"</font>";
				}
				G('_paginationTable_'+TO.gn).style.display = "none";
				TO.addOtherPagination("none");
				TO.hideCustomLoadingModalLayer ? TO.hideCustomLoadingModalLayer() : H();
				if(TO.TOTAL_SUCCESS.length == 0) delete data;
				CollectGarbage();
				return;
			}
			if(data.totalResultNo == 0){
				if(TO.pageTitle == "block"){
					TO.renderTitle(TO.noRecordText);
				}else{
					G('_table_'+TO.gn).innerHTML = TO.noRecordText;
				}
				TO.totalResultNo = 0;
				TO.totalPageNo = 0;
				G('_curPageNo_'+TO.gn).value = "";
				G('_totalPageNo_'+TO.gn).innerHTML = "0";
				G('_paginationTable_'+TO.gn).style.display="none";
				TO.addOtherPagination("none");
				TO.hideCustomLoadingModalLayer ? TO.hideCustomLoadingModalLayer() : H();
				if(TO.TOTAL_SUCCESS.length == 0 && TO.COMPLETE.length == 0) delete data;
				for (var e = 0; e < TO.COMPLETE.length; e++) {
					TO.COMPLETE[e](data,TO);
				}
				CollectGarbage();
			}else{
				TO.totalResultNo = data.totalResultNo;
				TO.totalPageNo = data.totalPageNo;
				G('_curPageNo_'+TO.gn).value = TO.pageNo;
				G('_totalPageNo_'+TO.gn).innerHTML = TO.totalPageNo;
				G('_paginationTable_'+TO.gn).style.display="none";
				if(TO.TOTAL_SUCCESS.length == 0) delete data;
				TO.hideCustomLoadingModalLayer ? TO.hideCustomLoadingModalLayer() : H();
				TO.requestData();
			}
			if(TO.scrollTop) {
				document.body.scrollTop = 0;
				$("#main").scrollTop(0);
			}
		};
		
		this.addOtherPagination = function(display){
			var ar = TO.otherPaginationDivs;
			var cd = TO.paginationCustomDiv;
			if (display == "block") {
				var htmlM = G('_paginationTable_'+TO.gn).innerHTML;
				htmlM = htmlM.replace(/_recPerPageSelect_/g,"");
				htmlM = htmlM.replace(/_totalPageNo_/g,"");
				htmlM = htmlM.replace(/_curPageNo_/g,"");
				for(var k=0;k<ar.length;k++){
					var html = '<table border="0" width="100%" style="visibility: visible">';
					html += htmlM;
					html += '</table>';
					G(ar[k]).innerHTML = html;
					G(ar[k]).style.display = "block";
					delete html;
				}
				if(TO.paginationCustomDiv!= null){
					G(cd).style.display = "block";
				}
				delete htmlM;
			} else {
				for(var k=0;k<ar.length;k++){
					G(ar[k]).style.display = "none";
				}
				if(TO.paginationCustomDiv!= null){
					G(cd).style.display = "none";
				}
			}

		}
		
		this.requestData = function(){
			TO.scrollLeft = $("#"+TO.did).parent().scrollLeft();
			TO.clean();
			TO.showCustomLoadingModalLayer ? TO.showCustomLoadingModalLayer() : S();
			if(TO.pageNo>1 && (TO.recPerPage*(TO.pageNo-1))>=TO.totalResultNo){
				TO.pageNo--;
				TO.resetParam();
			}
			TO.resetParam();
			var cb = {
				success: TO.renderPage,
				failure: showError
			}; 
			connection = C.asyncRequest('POST' , TO.dataUri , cb , TO.paramStr );
		};
		
		this.renderPage = function(o){
			if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
				delete o;
				window.location.reload();
				return false;
			}
			if(TO.autoToTop){
				try {
					document.getElementById('a_by_NAME_'+TO.gn).click();
				} catch (e) {
					window.scrollTo(0,0);
				}
			}
			var data = eval("("+o.responseText+")");
			delete o;
			for(var v=0;v<TO.SUCCESS.length;v++){
				TO.SUCCESS[v](data,TO);
			}
						TO.hideCustomLoadingModalLayer ? TO.hideCustomLoadingModalLayer() : H();
			if(data.count == 0){
				if(TO.pageTitle == "block"){
					TO.renderTitle(TO.noRecordText);
				}else{
					G('_table_'+TO.gn).innerHTML = TO.noRecordText;
				}
				TO.addOtherPagination("none");
				CollectGarbage();
				return;
			}
			if(data.error){
				if(TO.pageTitle == "block"){
					TO.renderTitle("<font color='red'>Error: " + data.error+"</font>");
				}else{
					G('_table_'+TO.gn).innerHTML = "<font color='red'>Error: " + data.error+"</font>";
				}
				G('_paginationTable_'+TO.gn).style.display="none"; 
				TO.addOtherPagination("none");
				CollectGarbage();
				return;
			}
			
			if(TO.totalPageNo == 1){
				if(TO.pageTable == "block"){
					G('_paginationTable_'+TO.gn).style.display="block";
					TO.addOtherPagination("block");
				}else{
					G('_paginationTable_'+TO.gn).style.display="none";
					TO.addOtherPagination("none");
				}
			}else{
				if(TO.pageTable == "none"){
					G('_paginationTable_'+TO.gn).style.display="none";
					TO.addOtherPagination("none");
				}else{
					G('_paginationTable_'+TO.gn).style.display="block";
					TO.addOtherPagination("block");
				}
			}
				
			var rows = data.data;
			var cs = TO.cols;
			TO.resultBegin = data.begin;
			TO.resultEnd = data.end;
			var t = '<div style="padding:3px 0 5px 0">'+TO.recordText + ' Items '+data.begin+' - '+data.end+' of '+ TO.totalResultNo+'</div>'
				+ '<table class="gridview" cellspacing="0" rules="all" border="1"'
				+ 'style="width:'+TO.tableWidth+'; border-collapse: collapse;">'+
				'<tbody id="_table_tbody_'+TO.gn+'"><tr class="listViewThLinkS1" height="30px">';
			for(var n=0;n<cs.length;n++){
				var title = "";
				if(typeof cs[n].title == 'function'){
					title = cs[n].title(TO);
				}else{
					title = cs[n].title;
				}
				t += '<th scope="col" nowrap="'+TO.thNowrap+'" '+(cs[n].width ? " width='"+cs[n].width+"'" : "")+(cs[n].display ? " style='display:"+cs[n].display+"'" : "")+(cs[n].className ? " class="+cs[n].className : "")+'>';
				if (cs[n].sort) {
					t += '<a href="javascript:void(0)" onclick="'+TO.gn+'.sort(event,\''+cs[n].sort+'\');">'+title;
					t += TO.getSortStr(cs[n].sort)+'</a>';
				}else {
					t += title;
				}
				delete title;
				if(cs[n].filtration){
					if(!TO.filter){
						alert('Error:The filter is undefined');
					}else{
						var fr = cs[n].filtration;
						t += '&nbsp;&nbsp;<img src="'+TO.filter.getImg(fr.name)+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="'+TO.gn+'.filter.edite(\''+fr.name+'\',\''+fr.alias+'\');">';
					}
				}
				t += '</th>';
			}
			t+= '</tr>';
			t += '</tbody></table>';
		    G('_table_'+TO.gn).innerHTML = t;
			delete t;
			G('_curPageNo_'+TO.gn).value = TO.pageNo;
			TO.addOtherPagination("block");
			var tcontent = G('_table_tbody_'+TO.gn);
			for(var i = 0; i<rows.length; i++){
			 	var row = rows[i];
				var tr = document.createElement('tr');
				tcontent.appendChild(tr);
				tr.className = (i%2==0?'evenListRowS1':'even');
				tr.style.height = TO.height;
				for(var n=0;n<cs.length;n++){
					var dStr = "";
					if(typeof cs[n].dataField == 'function'){
						dStr = cs[n].dataField(row,TO);
					}else{
						dStr = row[cs[n].dataField];
					}
					var td1 = document.createElement("td");
					tr.appendChild(td1);
					if(cs[n].display) td1.style.display = cs[n].display;
					if(cs[n].textAlign) td1.style.textAlign = cs[n].textAlign;
					if(cs[n].className) td1.className = cs[n].className;
					if(typeof dStr == 'object' && dStr.less_sign){
						$(td1).append(dStr.less_sign);
					}else{
						$(td1).append(((typeof dStr == 'string') ? dStr.replace(/&/g,"&amp;") : dStr));
					}
					delete dStr;
					delete td1;
				}
				delete tr;
				if(TO.SUCCESS.length == 0 && TO.COMPLETE.length == 0) delete rows[i];
 				if(TO.SUCCESS.length == 0 && TO.COMPLETE.length == 0) delete row;
			}
			if(TO.SUCCESS.length == 0 && TO.COMPLETE.length == 0) delete rows;
			delete tcontent;
			for (var e = 0; e < TO.COMPLETE.length; e++) {
				TO.COMPLETE[e](data,TO);
			}
			if(TO.SUCCESS.length == 0 && TO.COMPLETE.length == 0) delete data;
			CollectGarbage();
			if (TO.autoToLeft) {
				setTimeout(function(){
					TO.goToLeft();
				},500);
			}
			if(TO.scrollTop) {
				document.body.scrollTop = 0;
				$("#main").scrollTop(0);
			}
			$("select[id=ProposalViewListPage]").val($("#_recPerPageSelect_" + TO.gn).val());
		};
		
		this.goToLeft = function(){
			$("#"+this.did).parent().scrollLeft(this.scrollLeft);
		}
		
		this.renderPageAll = function(o){
			if(o.responseText.indexOf("CCM_USER_SESSION_TIME_OUT_AJAX_FLAG") != -1){
				delete o;
				window.location.reload();
				return false;
			}
			if(TO.autoToTop){
				try {
					document.getElementById('a_by_NAME_'+TO.gn).click();
				} catch (e) {
					window.scrollTo(0,0);
				}
			}
			var data = eval("("+o.responseText+")");
			delete o;
			for(var v=0;v<TO.SUCCESS.length;v++){
				TO.SUCCESS[v](data,TO);
			}
			G('_paginationTable_'+TO.gn).style.display="none";
			TO.addOtherPagination("none");
			TO.hideCustomLoadingModalLayer ? TO.hideCustomLoadingModalLayer() : H();
			if(data.error){
				if(TO.pageTitle == "block"){
					TO.renderTitle("<font color='red'>Error: " + data.error+"</font>");
				}else{
					G('_table_'+TO.gn).innerHTML = "<font color='red'>Error: " + data.error+"</font>";
				}
				CollectGarbage();
				return;
			}
			if(data.count == 0){
				if(TO.pageTitle == "block"){
					TO.renderTitle(TO.noRecordText);
				}else{
					G('_table_'+TO.gn).innerHTML = TO.noRecordText;
				}
				CollectGarbage();
				return;
			}
			var rows = data.data ? data.data : data;
			var cs = TO.cols;
			var t = '<table class="gridview" cellspacing="0"  rules="all" border="1"'
				+ 'style="width:'+TO.tableWidth+'; border-collapse: collapse;">'
				+ '<tbody id="_table_tbody_'+TO.gn+'"><tr class="listViewThLinkS1" height="30px">';
			for(var n=0;n<cs.length;n++){
				var title = "";
				if(typeof cs[n].title == 'function'){
					title = cs[n].title(TO);
				}else{
					title = cs[n].title;
				}
				t += '<th scope="col" nowrap="'+TO.thNowrap+'" '+(cs[n].width ? "width='"+cs[n].width+"'" : "")+(cs[n].display ? "style='display:"+cs[n].display+"'" : "")+'>';
				if (cs[n].sort) {
					t += '<a href="javascript:void(0)" onclick="'+TO.gn+'.sort(event,\''+cs[n].sort+'\');">'+title;
					t += TO.getSortStr(cs[n].sort)+'</a>';
				}else {
					t += title;
				}
				delete title;
				if(cs[n].filtration){
					if(typeof TO.filter == 'undefined'){
						alert('Error:The filter is undefined');
					}else{
						var fr = cs[n].filtration;
						t += '&nbsp;&nbsp;<img src="'+TO.filter.getImg(fr.name)+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="'+TO.gn+'.filter.edite(\''+fr.name+'\',\''+fr.alias+'\');">';
					}
				}
				t += '</th>';
			}
			t+= '</tr>';
			t += '</tbody></table>';
		    G('_table_'+TO.gn).innerHTML = t;
			delete t;
			var tcontent = G('_table_tbody_'+TO.gn);
			for(var i = 0; i<rows.length; i++){
			 	var row = rows[i];
				var tr = document.createElement('tr');
				tcontent.appendChild(tr);
				tr.className = (i%2==0?'evenListRowS1':'even');
				tr.style.height = TO.height;
				for(var n=0;n<cs.length;n++){
					var dStr = "";
					if(typeof cs[n].dataField == 'function'){
						dStr = cs[n].dataField(row,TO);
					}else{
						dStr = row[cs[n].dataField];
					}
					var td1 = document.createElement("td");
					tr.appendChild(td1);
					if(cs[n].display) td1.style.display = cs[n].display;
					if(cs[n].className) td1.className = cs[n].className;
					$(td1).append(((typeof dStr == 'string') ? dStr.replace(/&/g,"&amp;") : dStr));
					delete dStr;
					delete td1;
				}
				if(TO.SUCCESS.length == 0 && TO.COMPLETE.length == 0) delete rows[i];
 				if(TO.SUCCESS.length == 0 && TO.COMPLETE.length == 0) delete row;
				delete tr;
			}
			if(TO.SUCCESS.length == 0 && TO.COMPLETE.length == 0) delete rows;
			delete tcontent;
			for (var e = 0; e < TO.COMPLETE.length; e++) {
				TO.COMPLETE[e](data,TO);
			}
			if(TO.SUCCESS.length == 0 && TO.COMPLETE.length == 0) delete data;
			CollectGarbage();
			if (TO.autoToLeft) {
				setTimeout(function(){
					TO.goToLeft();
				},500);
			}
			$("select[id=ProposalViewListPage]").val($("#_recPerPageSelect_" + TO.gn).val());
		};
		
		this.sort = function(e, sfn){
			for(var cc=0;cc<TO.BEFORE_SEARCH.length;cc++){
				TO.BEFORE_SEARCH[cc]("sort",TO);
			}
			if(this.sortingField != sfn){
				this.sortingField = sfn;
				this.sortingDirection = "asc";
			}else{
				if(this.sortingDirection == "asc") this.sortingDirection = "desc";
				else this.sortingDirection = "asc";
			}
			this.requestData();
		};
		
		this.getSortStr = function(fieldName){
			if(TO.sortingField==fieldName){
				if(TO.sortingDirection=='asc') return '<img src="include/images/upsort.gif">';
				else return '<img src="include/images/downsort.gif">';
			}else return '';
		};
		
		this.getFirstPage = function(){
			for(var cc=0;cc<TO.BEFORE_SEARCH.length;cc++){
				TO.BEFORE_SEARCH[cc]("getFirstPage",TO);
			}
			if(TO.pageNo >1){
				TO.pageNo = 1;
				TO.requestData();
			}
		};
		
		this.getPrevPage = function(){
			for(var cc=0;cc<TO.BEFORE_SEARCH.length;cc++){
				TO.BEFORE_SEARCH[cc]("getPrevPage",TO);
			}
			if(TO.pageNo >1){
				TO.pageNo--;
				TO.requestData();
			}
		};
		
		this.getNextPage = function(){
			for(var cc=0;cc<TO.BEFORE_SEARCH.length;cc++){
				TO.BEFORE_SEARCH[cc]("getNextPage",TO);
			}
			if(TO.pageNo < TO.totalPageNo){
				TO.pageNo++;
				TO.requestData();
			}
		};
		
		this.getLastPage = function(){
			for(var cc=0;cc<TO.BEFORE_SEARCH.length;cc++){
				TO.BEFORE_SEARCH[cc]("getLastPage",TO);
			}
			if(TO.pageNo < TO.totalPageNo){
				TO.pageNo = TO.totalPageNo;
				TO.requestData();
			}
		};
		
		this.getPage = function(pn){
			
			for(var cc=0;cc<TO.BEFORE_SEARCH.length;cc++){
				TO.BEFORE_SEARCH[cc]("getPage",TO);
			}
			var re = /^[1-9]+[0-9]*]*$/;
			if(window.event.keyCode == 13){
				if(pn <= TO.totalPageNo && pn > 0 && re.test(pn)){
					TO.pageNo = pn;
			    	TO.requestData();
				}else{
					alert("Please enter the correct number of pages");   //modiby 2013/5/31 yongzhen.li
				}
				window.event.preventDefault();
			}
		};
		
		this.addBeforeSearch= function(fun){
			if (typeof fun == 'function') {
				TO.BEFORE_SEARCH.push(fun);
			} else {
				alert("This parameter must be a function.");
			} 
		};
		this.addSuccessEvent = function(fun){
			if (typeof fun == 'function') {
				TO.SUCCESS.push(fun);
			} else {
				alert("This parameter must be a function.");
			} 
		};
		this.addTotalSuccessEvent = function(fun){
			if (typeof fun == 'function') {
				TO.TOTAL_SUCCESS.push(fun);
			} else {
				alert("This parameter must be a function.");
			} 
		};
		this.addCompleteEvent = function(fun){
			if (typeof fun == 'function') {
				TO.COMPLETE.push(fun);
			} else {
				alert("This parameter must be a function.");
			} 
		};
		this.renderTitle = function(text){
			var cs = TO.cols;
			var t = '<div style="padding:3px 0 5px 0">'+TO.recordText+'</div><table class="gridview" cellspacing="0" rules="all" border="1"'
 				+ 'style="width:'+TO.tableWidth+'; border-collapse: collapse;">'
				+ '<tr class="listViewThLinkS1" height="30px">';
			for(var n=0;n<cs.length;n++){
				var title = "";
				if(typeof cs[n].title == 'function'){
					title = cs[n].title(TO);
				}else{
					title = cs[n].title;
				}
				t += '<th scope="col" nowrap="'+TO.thNowrap+'" '+(cs[n].width ? "width='"+cs[n].width+"'" : "")+(cs[n].display ? "style='display:"+cs[n].display+"'" : "")+'>';
				t += title;
				delete title;
				if(cs[n].filtration){
					if(!TO.filter){
						alert('Error:The filter is undefined');
					}else{
						var fr = cs[n].filtration;
						t += '&nbsp;&nbsp;<img src="'+TO.filter.getImg(fr.name)+'"  style="width:13px;height:13px;cursor: pointer;"  onclick="'+TO.gn+'.filter.edite(\''+fr.name+'\',\''+fr.alias+'\');">';
					}
				}
				t += '</th>';
			}
			t += '</tr>';
			var csLength = 0;
			for(var n=0;n<cs.length;n++){
				if(cs[n].display != "none")
					csLength ++ ;
			}
			t += '<tr class="evenListRowS1"><td colspan="'+csLength+'" align="center">';
			t += text;
			t += '</td></tr>';
			t += '</table>';
		    var tvar = G('_table_'+TO.gn);
			tvar.innerHTML = t;
			delete tvar; 
			delete t;
		};
		this.abortSending = function() {
			C.abort(connection, null, true);
		}
    };

	SANINCO.Page=P;
})();