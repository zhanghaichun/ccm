var invo;
function initOriginalTabSearch(){
	//invo=document.getElementById("Originalidid").value;
	searchdOriginal(invo);
}

function searchdOriginal(invo)
{
	originalPage = new SANINCO.Page("_originalTable","originalPage",{
      sortingField : "original.file_name",
      recordText : "",
      sortingDirection : "asc",
      vo : "svo",
      recPerArray : [5,10,15,20,30],
      totalPageUri : "doGetOriginalTotalPageNo.action",
      dataUri : "getOriginal.action",
      cols : [
	      {
	          title : "File Name",
	          width: "30%",
	          dataField: "file_name" ,
	          sort : "original.file_name"
	      },{
	          title : "Created Time",
	          width: "25%",
	          dataField: "created_timestamp",
	          sort : "original.created_timestamp"
	      },{
	          title : "Modified Time",
	          width: "25%",
	          dataField: "modified_timestamp" ,
	          sort : "original.modified_timestamp"
	      },{
	          title : "File",
	          width: "10%",
	          dataField:function(obj){
                return "<img  src=\"include/images/download1.gif\"  style=\"cursor: pointer;\"  onClick=\"downloadFileO(" + "'" + obj.file_name + "','" + obj.file_path + "'" + ");\">";
//                return "<img  src=\"include/images/download1.gif\"  style=\"cursor: pointer;\"  onClick=\"previewFileO(" + "'" + obj.file_name + "','" + obj.file_path + "'" + ");\">";
            }
	      }
      ]
  });
  originalPage.param = {
       'svo.invoiceNumber': invoiceId,
	   'svo.barCode':invoiceBarCode
   };
  originalPage.start();
  }
  
  
function downloadFileO(name,path){
	var df = document.getElementById("downloadForm");
	df.filePath.value = path;
	df.fileName.value = name;
	df.submit();
}


function previewFileO(name,path){
	
	var callback = {
		success: function(o){
			var data = eval("("+o.responseText+")");
			if(data.success){
				window.open(data.success);
			}else{
				window.location.href="download.action";
			}
			
		},
		failure: showError
	};
	
	var postDate ="filePath=" + path;
		postDate += "&fileName=" + name;
	
	YAHOO.util.Connect.asyncRequest('POST' , "preview.action" , callback , postDate); 
}
  

  