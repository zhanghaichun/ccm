var pageNo = 1;
var pageNo2 = 1;
var recPerPage = 5;
var recPerPage2 = 10;
var totalPageNo;
var totalPageNo2;
var wikiPage;

function searchWiki(flag) {
	$("#wikiMangementList").show();
	$("#wikiPublish").hide();
	wikiPage.myParam =  "searchWikiVO.title=" + ccmEscape($.trim($("#searchTitle").val()));
	wikiPage.start();
}

function topWiki(id,status) {
	$.post("updateLististopById.action",{"wikiIds" : id+"","wiki.lististop" : status},function(){
		searchWiki();
	});
}

function cancelPublish() {
	$("#title").val("");
	$("#content").val("");
	$("#lististop").removeAttr("checked");
	resetResult();
}

function publishWiki() {
	var title =  $("#title").val();
	var content = getContentCkeditorValue();
	var id = $("#wikiId").val();
	var lististop = 0;
	if ($("#lististop")[0].checked) {
		lististop = 1;
	}
	if ($.trim(title) == "") {
		alert("The title cannot be empty!");
		return;
	}
//	var href = content.match(/href=".+"\s/);
//	for (var i=0;i<href.length;i++) {
//		var hrefValue = href[i].toString().substring(6,href[i].toString().length-2);
//		content = content.replace(href[i].toString(),'href="javascript:void(0)" onclick="window.open(\\\'' + hrefValue.toString() + '\\\') "');
//	}
	
	var contentText = CKEDITOR.instances.wikiContent.document.getBody().getText();
	if (contentText.length > 3000) {
		alert("Content length can't be more than 3000 characters!");
		return;
	}
	var param = {"wiki.title" : title,"wiki.content" : content,"wiki.id" : id,"wiki.lististop" : lististop,"wiki.contentText" : ccmEscape(contentText)};
	showLoadingModalLayer();
	$.post("publishOrUpdateWiki.action",param,function(){
		hideLoadingModalLayer();
		resetWikiHomeResult();
		alert("Publish Successfully!");
		$("#title").val("");
		$("#content").val("");
		CKEDITOR.instances.wikiContent.setData("");
		$("#lististop").removeAttr("checked");
		searchWiki();
	});
}

function editWiki(id) {
	$("#wikiMangementList").hide();
	$("#wikiPublish").show();
	var callback = {
	        success: function(o){
				hideLoadingModalLayer();
				var wiki = eval("("+o.responseText+")");
				$("#wikiId").val(wiki.id);
				$("#title").val(wiki.title.replace(/\n/g,'\\n').replace(/\r/g,'\\r'));
				$("#content").val(wiki.content);
				if (wiki.lististop == 1) {
					$("#lististop").attr("checked",true);
				}
				if (CKEDITOR.instances.wikiContent) {
					CKEDITOR.instances.wikiContent.destroy(true);
				}
				var editor = CKEDITOR.replace("wikiContent");
				editor.setData(wiki.content);
		},
	        failure: showError
	    };
	var param = "wiki.id=" + id;
	showLoadingModalLayer();
    YAHOO.util.Connect.asyncRequest("POST", "findWikiById.action", callback, param);
	
}

function removeWiki(id) {
	var callback = {
	        success: function(data){
				hideLoadingModalLayer();
				alert("Remove Successfully!");
				searchWiki();
		},
	        failure: showError
	    };
	var param = "wiki.id=" + id;
	showLoadingModalLayer();
    YAHOO.util.Connect.asyncRequest("POST", "deleteWiki.action", callback, param);
}

function publishView() {
	$("#wikiMangementList").hide();
	$("#wikiPublish").show();
	$("#title").val("");
	$("#content").val("");
	$("#wikiId").val("");
	if (CKEDITOR.instances.wikiContent) {
		CKEDITOR.instances.wikiContent.destroy(true);
	}
	if(!CKEDITOR.instances.wikiContent){  
        CKEDITOR.replace("wikiContent");   
   }
	$("#lististop").removeAttr("checked");
}

function wikiHomeSearch(flag) {
	var callback = {
			success:function(o){
				hideLoadingModalLayer();
				var data = eval("("+o.responseText+")");
				$("#wikiList").html('<tr class="tableth"><td width="56%" align="center" class="tdbox1 tdbox2">Title</td><td width="44%" align="center" class="tdbox1">Date</td></tr>');
				$("#__totalPageNo2").html(data.totalPage);
				totalPageNo2 = data.totalPage;
				
				if (parseInt(totalPageNo2) == 0) {
					$("#__curPageNo2").val(0);
					pageNo2=1;
				} else {
					if (flag != 'page'){
						$("#__curPageNo2").val(1);
						pageNo2=1;
					}
				}
				$.each(data.wikiList,function(i,item){
					$("#wikiList").append('<tr><td width="56%" class="tdbox1 tdbox2"><a class="wikia1" href="javascript:showDetail(' + item.id + ')">' + item.title + '</a></td><td width="44%" align="center" class="tdbox1">' + item.publishTime + '</td></tr>');
				});
				$("#resultCount2").html(data.totalCount);
		},
			failure: showError
		};
		if (flag != 'page') {
			pageNo2 = 1;
		}
		var submitParamer = "searchWikiVO.title=" + escape($.trim($("#searchTitle").val()))+"&searchWikiVO.pageNo=" + pageNo2 + "&searchWikiVO.recPerPage=" + recPerPage2;
		showLoadingModalLayer();
		YAHOO.util.Connect.asyncRequest('POST' , "wikiManagement.action" , callback , submitParamer );
}

function showDetail(id) {
	window.open("wikiDetail.action?wiki.id=" + id);
}

function requestData(flag){
		if (flag=='top') {
			reloadWikiHome();
			__curPageNo1.value = pageNo;
		} else {
			if (flag=='management') {
				searchWiki('page');
			} else {
				wikiHomeSearch('page');
			}
			__curPageNo2.value = pageNo2;
		}
	}

function reloadWikiHome() {
	var callback = {
			success:function(o){
				hideLoadingModalLayer();
				var data = eval("("+o.responseText+")");
				$("#wikiHomeBody").html("");
				var total = data.totalPage;
				$("#__totalPageNo").html(data.totalPage);
				totalPageNo = data.totalPage;
				
				if (parseInt(totalPageNo) == 0) {
					$("#__curPageNo").val(0);
				} else {
					if (pageNo == 1) {
					$("#__curPageNo").val(1);
					} 
				}
				$.each(data.wikiList,function(i,item){
//					var ht = '<tr class="tableth"><td align="left" class="tabletd">Title:<a href="javascript:showDetail(' + item.id + ')" class="wikia1">' + item.title + '</a> </td>';
//		                ht+='<td align="left" class="tabletd">Name:' + item.publishUser + '</td><td align="left">Date:' + item.publishTime + '</td> </tr>';
//		          	    ht+='<tr><td colspan="3" class="tablecon"><div class="wikitablecon">' + item.contentText + '</div></td></tr>';
					var ht = '<tr class="tableth" ><td align="left" colspan="2" style="font-weight:bold;font-size:12px;"><a href="javascript:showDetail(' + item.id + ')" class="wikia1">' + item.title + '</a> </td></tr>';
		                ht+='<tr class="tableth"><td align="left" class="tablecon">Name: ' + item.publishUser + '</td><td  class="tablecon" align="left">Date: ' + item.publishTime + '</td> </tr>';
		          	    ht+='<tr><td colspan="2" class="tablecon"><div class="wikitablecon" style="word-wrap:break-word;width:500px">' + unescape(item.contentText)  + '</div></td></tr>';
					$("#wikiHomeBody").append(ht);
				});
				$("#resultCount").html(data.totalCount);
		},
			failure: showError
		};
		var submitParamer = "searchWikiVO.pageNo=" + pageNo + "&searchWikiVO.recPerPage=" + recPerPage;
		showLoadingModalLayer();
		YAHOO.util.Connect.asyncRequest('POST' , "reloadHomeWikiList.action" , callback , submitParamer );
}

function CutStr(str,lenSp)
        {   
        	var s=""; 
			for(var i=0,len=str.length/lenSp;i<len;i++) {
	            var str1 = str.substring(i*lenSp,lenSp*(i+1));
	            s+= str1+"<br>";
            }
            return s;
        }

//fenye
function getNextPage(flag){
//	if(evaluateQueyChange()) return;
	if (flag=='top') {
		if((pageNo+1)>totalPageNo) return;
		pageNo++;
	} else {
		if((pageNo2+1)>totalPageNo2) return;
		pageNo2++;
	}
	requestData(flag);
}

function getPrevPage(flag){
//	if(evaluateQueyChange()) return;
	if (flag=='top') {
		if((pageNo-1)<1) return;
		pageNo--;
		requestData(flag);
	} else {
		if((pageNo2-1)<1) return;
		pageNo2--;
		requestData(flag);
	}
	
}

function getFirstPage(flag){
//	if(evaluateQueyChange()) return;
	if (flag=='top') {
		pageNo = 1;
	} else {
		pageNo2 = 1;
	}
	requestData(flag);
}

function getLastPage(flag){
//	if(evaluateQueyChange()) return;
	if (flag=='top') {
		pageNo = totalPageNo;
	} else {
		pageNo2 = totalPageNo2;
	}
	requestData(flag);
}

function getPage(flag) {
	var pn = 0;
	var tn = 0;
	if (flag=='top') {
		pageNo = __curPageNo1.value;
		pn = __curPageNo1.value;
		tn = parseInt($("#__totalPageNo").text());
	} else {
		pageNo2 = __curPageNo2.value;
		pn = __curPageNo2.value;
		tn = parseInt($("#__totalPageNo2").text());
	}
	
	var re = /^[1-9]+[0-9]*]*$/;
	if(window.event.keyCode == 13){
		if(pn <= tn && pn > 0 && re.test(pn)){
	    	requestData(flag);
		}else{
			alert("Please enter the correct number of pages"); 
		}
	}
}


function resetResult() {
	$("#searchTitle").val("");
	searchWiki();
}

function resetWikiHomeResult() {
	$("#searchTitle").val("");
	wikiHomeSearch();
}

function buildWikiPage() {
	if (!window.wikiPage) {
		 var myCols = [
		       		{title: "Title",dataField: function(o){
		       				  return "<a href=\"javascript:showDetail(" + o.id + ")\">" + o.title.replace(/\n/g,'\\n').replace(/\r/g,'\\r') + "</a>";
                     }},{title: "Flag",width:50,
	                       dataField: function(o){
		       			  if (o.lististop == 1) {
		       				  return "<img src=\"themes/Autumn/images/wikitop.png\" border=\"0\"/>";
		       			  }else {
		       				return "";
		       			  }
                     }
		       		},{title: "Action", width:300,
		                       dataField: function(o){
		       					var html = "";
		       					if (o.lististop == 1) {
		       						html+="<a href=\"javascript:topWiki(" + o.id + ",0)\">UnTop</a><a style=\"margin-left:20px;\" href=\"javascript:showDetail(" + o.id + ")\">Preview</a>";
		       					} else {
		       						html+="<a href=\"javascript:topWiki(" + o.id + ",1)\">Top</a><a style=\"margin-left:32px;\" href=\"javascript:showDetail(" + o.id + ")\">Preview</a>";
		       					}
		                           return html+"<a style=\"margin-left:20px;\" href=\"javascript:editWiki(" + o.id + ")\">Edit</a><a style=\"margin-left:20px;\" href=\"javascript:removeWiki(" + o.id + ")\">Remove</a>";
		                       }
		               }];
		               
					 wikiPage = new SANINCO.Page('wiki_dataTable', "wikiPage", {
					                   sortingField: "v.created_timestamp",
					                   sortingDirection: "desc",
					                   vo: "searchWikiVO",
					                   totalPageUri: "queryWikiListTotalPageNo.action",
					                   dataUri: "queryWikiList.action",
					                   paginationDiv: "wiki_dataTable_page",
					                   recPerArray: [10, 15, 20, 30, 40, 50, 80, 100],
					                   scrollTop: true,
					                   cols: myCols
					               });
					 wikiPage.start();
					 $("#wikiMangementList").hide();
					 $("#wikiPublish").hide();
	}
}
YAHOO.util.Event.onDOMReady(function(){
	reloadWikiHome();
	wikiHomeSearch();
	totalPageNo = parseInt($("#__totalPageNo").text());
	totalPageNo2 = parseInt($("#__totalPageNo2").text());
	buildWikiPage();
});

function changePageCount2(select) {
	if (parseInt($("#resultCount2").html()) < parseInt(select.value)) {
		$("#pageResultCount2").html($("#resultCount2").html());
	} else {
		$("#pageResultCount2").html(select.value);
	}
}

function changePageCount1(select) {
	if (parseInt($("#resultCount").html()) < parseInt(select.value)) {
		$("#pageResultCount1").html($("#resultCount").html());
	} else {
		$("#pageResultCount1").html(select.value);
	}
}
