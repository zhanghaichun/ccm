var tinvo;
/* Delete By Chao.Liu to 10/07/21 PM
YAHOO.util.Event.onDOMReady(function () {
	tinvo=document.getElementById("Tifflinkidid").value;
	searchdTiff(tinvo);
});
*/
// Add By Chao.Liu to 10/07/21 PM
function initDTiffTabSearch(){
	tinvo=document.getElementById("Tifflinkidid").value;
	invoiceDate = document.getElementById("_invoiceDate").innerHTML;
	searchdTiff(tinvo);
}

function searchdTiff(tinvo)
{
	tiffPage = new SANINCO.Page("_tiffTable","tiffPage",{
      sortingField : "tariff_link.created_timestamp",
      recordText : "",
      sortingDirection : "desc",
      vo : "svo",
      recPerArray : [5,10,15,20,30],
      totalPageUri : "doGetTariffTotalPageNo.action?tnco="+tinvo+"",
      dataUri : "getTariff.action?tnco="+tinvo+"",
      cols : [
	      {
	          title : "Tariff Name",
	          dataField: "tariff_name" ,
	          sort : "tariff_link.tariff_name"
	      },{
	          title : "Created Time",
	          dataField: "created_timestamp",
	          sort : "tariff_link.created_timestamp"
	      },{
	          title : "Modified Time",
	          dataField: "modified_timestamp" ,
	          sort : "tariff_link.modified_timestamp"
	      },{
	          title : "	Notes",
	          dataField: "notes" ,
	          sort : "tariff_link.notes"
	      },{
	          title : "File",
	          dataField:function(obj){
                return "<img  src=\"include/images/download1.gif\"  style=\"cursor: pointer;\"  onClick=\"downloadFile(" + "'" + obj.tariff_name + "','" + obj.tariff_path + "'" + ");\">";
            }
	      }
      ]
  });
  tiffPage.start();
  } 



function downloadFile(name,path){
	var df = document.getElementById("downloadForm");
	df.filePath.value = path;
	df.fileName.value = name;
	df.submit();
}
