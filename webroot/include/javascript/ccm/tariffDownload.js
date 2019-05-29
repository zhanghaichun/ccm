/**
 demo:<button onclick="SANINCO.TariffDownload(50)">test</button>
 requier: page.js,yahoo dialog
*/
(function(){
	if(typeof SANINCO != 'object'){SANINCO={};}

	var A = function(ele,html){
		var e = document.createElement('div');
		ele.appendChild(e);
		e.innerHTML = html;
	};
	
	var did = "saninco_down_div_1_220";
	var dpid = "saninco_down_page_div_1_219";
	var formId = "sanincoDownloadForm_0_210";
	
	var html = '<div class="main-yui-pe-content" id="'+did+'" style="visibility: hidden;">';
	html += 	'<div class="hd" >';
	html += 		'Click Download';
	html += 	'</div>';
	html +=		'<form style="max-height:500px;overflow-y: auto;overflow-x:hidden;">';
	html +=			'<div class="bd">';
	html +=				'<div id="'+dpid+'"></div>';
	html +=			'</div>';
	html +=		'</form>';
	html += '</div>';
	
	var html1 = '<form id="'+formId+'" action="download.action" method="post" style="display: none;">';
	html1 +=		'<input type="hidden" name="filePath" value=""/>';
	html1 +=		'<input type="hidden" name="fileName" value=""/>';
	html1 +=    '</form>';
	
 	var D = function(vendorCircuitId){
 		var d = SANINCO.TariffDownload;
 		if(d.isInit){
 			A(document.body,html);
 			A(document.body,html1);
			d.dialog = new YAHOO.widget.Dialog(did, 
				{ width : "700px",
				  modal : true,
				  fixedcenter : true,
				  visible : false, 
				  constraintoviewport : true,
				  buttons : [{ text:"Cancel", handler:function() {
								this.cancel();
					}} ]
				});
			d.dialog.render();
			//----
			d.page = new SANINCO.Page(dpid,"sanincoDownloadPage_0_10_20",{
		        sortingField : "",
			    sortingDirection : "",
				vo : "searchCircuitVO",
		        recPerArray : [5,10,15,20,30],
				autoToTop : false,
				tableWidth:"680px;table-layout: fixed;",
		        totalPageUri : "doSearchTariffTotalPageNo.action",
		        dataUri : "doSearchTariffList.action",
		        cols : [
			      {
			          title : "Tariff Name",
			          dataField: "tariff_name" ,
			          sort : "tariff_name",
			          width: "200px", 
			          className:"tariff-download-td-note-wrap",
			          filtration : {name:"tariff_name",alias:"Tariff Name"}
			      },{
			          title : "Upload Date",
			          dataField: "created_timestamp",
			          sort : "created_timestamp",
			          width: "125px", 
			          filtration : {name:"created_timestamp",alias:"Upload Date"}
			      },{
			          title : "Notes",
			          dataField: "notes" ,
			          sort : "notes",
			          width: "200px", 
			          className:"tariff-download-td-note-wrap",
			          filtration : {name:"notes",alias:"Notes"}
			      },{
			          title : "File",
			          dataField:function(obj){
		                return "<input value=\"Download\" type=\"button\" style=\"cursor: pointer;\"  onClick=\"SANINCO.TariffDownload.go(" + "'" + obj.tariff_name + "','" + obj.tariff_path + "'" + ");\">";
		              },
			          width: "75px",
			          textAlign: "center"
			      }]
		    });
		    
		    var filter = new SANINCO.Filter();
		    filter.addEditeEvent(function(){d.page.start();});
		  	filter.add('tariff_name', 'String');
			filter.add("created_timestamp", 'date');
		  	filter.add('notes', 'String');
		  	d.page.setFilter(filter);
		    
		    sanincoDownloadPage_0_10_20 = d.page;
		    d.go = function(name,path){
		    	var df = document.getElementById(formId);
				df.fileName.value = name;
				df.filePath.value = path;
				df.submit();
		    };
 			d.isInit = false;
 		}
 		d.page.myParam = "searchCircuitVO.vendorCircuitId=" + vendorCircuitId;
 		d.page.start(); 
 		d.dialog.show();
 	};
 	
	SANINCO.TariffDownload=D;
	SANINCO.TariffDownload.isInit = true;
})();