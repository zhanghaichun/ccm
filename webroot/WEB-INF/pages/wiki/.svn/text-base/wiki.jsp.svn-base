<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script type="text/javascript"
	src="include/javascript/ccm/wiki/wiki.js" language="javascript"></script>
	
<script type="text/javascript">
<!--
	$().ready(function(){
		wikiHomeSearch();
	});
//-->
</script>

<div id="pageContainer" class="tabForm" style="padding-bottom:25px;" >
<h3 style="padding-top:0px; padding-bottom:4px;">
		Wiki
</h3>
<div class="wikimain">
<table border="0" cellspacing="0" cellpadding="0" width="100%" class="wikimaint">
  <tr>
    <td width="59%" align="left" valign="top" class="wikimaintd">
    	<div style="height:625px; overflow-y:auto;overflow-x:auto;">
	        <table border="0" cellspacing="0" cellpadding="0" width="100%" class="wikitable1">
	        	<tbody id="wikiHomeBody">
	          	</tbody>
	        </table>
        </div>
        <div id="tab3">
		<table border=0 width=100% id="_paginationTable">
			<tr>
				<td valign="top" style="padding-right: 10px;width: 260px;">
					<img src="include/images/button_previous_first.gif"
						title="First page" onclick="getFirstPage('top');">
					<img src="include/images/button_previous.gif"
						title="Previous page" onclick="getPrevPage('top');">
					Page&nbsp;
					<input id="__curPageNo1" type="text" style="width: 30px;"
						value="1" onkeydown="getPage('top');">
					&nbsp;of&nbsp;
					<span id="__totalPageNo"><s:property value="totalHomePage"/></span>
					<img src="include/images/button_next.gif" title="Next page"
						onclick="getNextPage('top');">
					<img src="include/images/button_next_last.gif"
						title="Last page" onclick="getLastPage('top');">
					&nbsp;&nbsp;
					<span class="select1"><select id="_recPerPageSelect"
							onchange="recPerPage=this.value;reloadWikiHome();changePageCount1(this)">
							<option value="5">
								5
							</option>
							<option value="10">
								10
							</option>
							<option value="20">
								20
							</option>
							<option value="30">
								30
							</option>
							<option value="40">
								40
							</option>
							<option value="50">
								50
							</option>
							<option value="100">
								100
							</option>
						</select> </span>
				</td>
				<td>
					Search Results: Items 1 - <span id="pageResultCount1">5</span> of <span id="resultCount"></span>
				</td>
			</tr>
		</table>
	</div>
    </td>
    <td width="1%" class="wikimaintd1">&nbsp;</td>
    <td width="40%" valign="top" class="wikimaintd">
    
    	<table border=0 width=100%>
	    	<tr>
		    	<td class="wikirt">
		    	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		            <tr>
		              <td width="160"><input id="searchTitle" style="width: 150px"></td>
		              <td><input value="Search" onclick="wikiHomeSearch()" type="button" style="padding:0 5px;">&nbsp;&nbsp;&nbsp;<input value="Reset" onclick="resetWikiHomeResult()" type="button" style="padding:0 5px;"></td>
		            </tr>
		          </table>
		    	</td>
	    	</tr>
	    	<tr>
		    	<td>
		        	<div class="wikisearch" style="height:584px; overflow-y:auto;overflow-x:auto;">
			            <table border="0" cellspacing="0" cellpadding="0" width="100%" class="wikitable1">
				            <TBODY id="wikiList">
				            </TBODY>
			            </table>
		        	</div>
	        	</td>
        	</tr>
        <tr>
        <td>
            <table border=0 width=100% id="_paginationTable">
				<tr>
					<td valign="top" style="padding-right: 10px;width: 260px;">
													<img src="include/images/button_previous_first.gif"
														title="First page" onclick="getFirstPage();">
													<img src="include/images/button_previous.gif"
														title="Previous page" onclick="getPrevPage();">
													Page&nbsp;
													<input id="__curPageNo2" type="text" style="width: 30px;"
														value="1" onkeydown="getPage();">
													&nbsp;of&nbsp;
													<span id="__totalPageNo2"><s:property value="totalPage"/></span>
													<img src="include/images/button_next.gif" title="Next page"
														onclick="getNextPage();">
													<img src="include/images/button_next_last.gif"
														title="Last page" onclick="getLastPage();">
													&nbsp;&nbsp;
													<span class="select1"><select id="_recPerPageSelect2"
															onchange="recPerPage2=this.value;wikiHomeSearch();changePageCount2(this);">
															<option value="10">
																10
															</option>
															<option value="20">
																20
															</option>
															<option value="30">
																30
															</option>
															<option value="40">
																40
															</option>
															<option value="50">
																50
															</option>
															<option value="100">
																100
															</option>
														</select> </span>
							</td>
							<td>
													Search Results: Items 1 - <span id="pageResultCount2">10</span> of <span id="resultCount2"></span>
							</td>
						</tr>
					</table>
				</td>
			</tr>					
		</table>
	</td>
  </tr>
</table>
</div>
</div>

