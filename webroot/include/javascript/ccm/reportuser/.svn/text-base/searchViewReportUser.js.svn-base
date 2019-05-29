// Created By pengfei.wang
//The reportName list
function searchViewReport(){
	ReportViewPage = new SANINCO.Page("reportViewListDiv","ReportViewPage",{
      sortingField : "re.report_name",
      sortingDirection : "asc",
      vo : "rvo",
      recPerArray : [30,50,100],
      totalPageUri : "doGetViewReportTotalNOTags.action",
      dataUri : "doSearchViewReportTags.action",
      cols : [
	      {
	          title : "Report Name",
	          dataField: function(row){
	              return '<a href="#" onclick="showViewReportDetails(\''+row.id+'\',\''+row.rname+'\',\''+row.alevel+'\')">' + row.rname + '</a>';
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
  filter.addEditeEvent(function(){ReportViewPage.start();});
  filter.add('re.report_name', 'String');
  ReportViewPage.setFilter(filter);
  ReportViewPage.start();
}

//$("#jDemo").css("opacity", 0.5); 
function showViewReportDetails(rid,rname,alevel){
	$("#ViewReportDetailDiv").animate( {queue:false, duration:1000} );
	$("#noneAndblok").css({display:""});

	searchViewName(rid);
}

function lables(){
	document.getElementById("label1").value='asdfasdfasdf';
}

//Take the list
function cancleView(){
	$("#ViewReportDetailDiv").animate({queue:false, duration:1000} );
	$("#noneAndblok").css({display:"none"});
}

//Click reportName, display list
function searchViewName(id){
	ReportViewPage2 = new SANINCO.Page("reportViewNameList","ReportViewPage2",{
      sortingField : "created_report.created_timestamp",
      sortingDirection : "desc",
      vo : "rvo",
      recPerArray : [30,50,100],
      totalPageUri : "doViewNameTotalNOTags.action?reportId="+id+" ",
      dataUri : "doViewSearchName.action?reportId="+id+" ",
      cols : [
	      {
	          title : "Report Name",
	          dataField: function(o){return "<div style=\"max-width :300px; white-space: normal;word-wrap: break-word\" >"+o.created_report_name+"<div>"},
	          sort : "created_report.created_report_name",
	          filtration : {name:"created_report.created_report_name",alias:"Report Name"}
	      },{
	          title : "Created By",
	          dataField: "user_name" ,
	          sort : "`user`.user_name",
	          filtration : {name:"`user`.user_name",alias:"Created By"}
	      },{
	          title : "Created Time",
	          dataField: "created_timestamp" ,
	          sort : "created_report.created_timestamp",
	          filtration : {name:"created_report.created_timestamp",alias:"Created Time"}
	      },{
	          title : "Status",
	          dataField: function(obj){
			  	if(obj.report_status==1){return "Requested";}
				else if(obj.report_status==2){return "Scheduled";}
				else if(obj.report_status==3){return "In Process";}
				else if(obj.report_status==4){return "Generated";}
				else if(obj.report_status==5){return "Failed";}
				else{
					return "";
				}
			  } ,
	          sort : "created_report.report_status",
	          filtration : {name:"created_report.report_status",alias:"Status"}
	      },{
	          title : "Attachment",
	          dataField:function(obj){
	          	if(obj.report_status==4){
                return "<input type=\"button\" value=\"Download\" onClick=\"downloadFileO(" + "'" + obj.file_name + "','" + obj.file_path + "'" + ");\">";
            	}else{
            		return "";
            	}
            }
	      },  
	      {
	           title : "Delete",
	          dataField:function(obj){
	          if(obj.owner_flag==0){
	           return "<img  src='include/images/reject16.png' onclick=\"deleteReport('"+obj.id+"','"+obj.report_id+"')\">";
	          }
	          }
	      }
      ]
  });
  filter1 = new SANINCO.Filter();
  filter1.addEditeEvent(function(){ReportViewPage2.start();});
  filter1.add('created_report.created_report_name', 'String');
  filter1.add('created_report.report_format', 'String');
  filter1.add('`user`.user_name', 'String');
  filter1.add('created_report.created_timestamp', 'Date');
  filter1.add('created_report.report_status', 'String');
  ReportViewPage2.setFilter(filter1);
  ReportViewPage2.start();
}
// By hongtao 2011-09-20
function deleteReport(id,reportId){
	if (!confirm("Are you sure to make this report result inactive?")) {
        return;
    }	
	function deleteCallback(o){
			searchViewName(reportId);
		
	}
	var callback = {
		success: deleteCallback,
		failure: showError
	};
	var getCreatedReportId = "createdReportId=" + id;
	YAHOO.util.Connect.asyncRequest('POST' , "deleteReportById.action" , callback ,getCreatedReportId)
}

function downloadFileO(name,path){
	
	showLoadingModalLayer();
	var titles = "filePath=" + path+""+name + "&fileName=" + name;
	window.location.href="doDownloadReport.action?"+ titles ;
	hideLoadingModalLayer();
	
//	var df = document.getElementById("downloadForm");
//	df.filePath.value = path+""+name;
//	df.fileName.value = name;
//	df.submit();
//	var callback = {
//			success: downloadFileOCallback,
//			failure: showError
//		};
//	var params = "filePath=" + path+""+name + "&fileName=" + name;
//	YAHOO.util.Connect.asyncRequest('POST' , "doDownloadReport.action" , callback ,params);
}

//function downloadFileOCallback(o) {
//	var data = eval("("+o.responseText+")");
//	if(data.error){
//		alert("Error: " + data.error);
//		return;
//	} else {
//		alert("Download Report Success!");
//	}
//}
