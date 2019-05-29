<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link rel="stylesheet" type="text/css" href="include/javascript/Calendar/styles/calendar.css">
<link rel="stylesheet" type="text/css" href="include/javascript/flexbox/css/jquery.flexbox.css"><link>

<script type="text/javascript" src="include/javascript/flexbox/js/jquery.flexbox.js"></script>
<script type="text/javascript" src="include/javascript/Calendar/simplecalendar.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/ccm/searchMessageCenter.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/ccm/quicklink.js" language="javascript"></script>
<SCRIPT type="text/javascript" src="include/javascript/ccm/common/simpleValidation.js"></SCRIPT>
<SCRIPT type="text/javascript" src="include/javascript/ccm/common/common.js"></SCRIPT>
<style>
    .tabDetailView {
        border-width: 1px;
    }
    
    .tabDetailViewDL.left-part {
        border-right: none;
    }
</style>
<script type="text/javascript"> 
var VL_Array = {
	"results": [
		{
		    "id": "all",
		    "name": ""
		}
		<c:forEach items="${CreatedByList}" var="v">
		,{
		    "id": "${v.key}",
		    "name": "${v.value}"
		}
		</c:forEach>
	],
	"total": ${fn:length(CreatedByList)}
};
$(function(){
	form0_searchMessageCenter_CreatedBy = $('#VL_Created_By').flexbox(VL_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 250,
		onSelect : function(){
		}
	});
	form0_searchMessageCenter_CreatedBy.setValue("all");
});
</script>
<div id="pageContainer" class="tabForm" onMouseDown="if(g_Calendar && g_Calendar.hide)g_Calendar.hide();" style="padding-bottom:25px;" >
	<h3 style="padding-top:0px; padding-bottom:4px;">
		Message Center
	</h3>
	<div style="border-top: 0px none;">
	<div id="ctl00_MainContent_SearchInvoiceControl1_UpdatePanel1">
						<s:form id="form0" action="" theme="simple" onsubmit="startSearch();return false;">
						<table class="tabDetailView" border=" 0" width="100%" cellpadding="5" cellspacing="0">
						<tr valign=top>
                            <td class="tabDetailViewDL left-part" width="52%"><div class=searchItemPanel>
                                <table border=0 cellspacing=3 cellpadding=0 width="100%">
                                  <tbody>
                                    <tr>
                                      <td width="30%">Reference</td>
                                      <td>
                                      	<span class="select1"><s:select id="Reference" name="searchMessageCenterVO.Reference" list="ReferenceList" headerKey="all"
																headerValue="All" listKey="key" listValue="value" cssStyle="width:250px"/></span>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td>Number</td>
                                      <td>
                                        <input style="width:250px" type="text" id ="Reference_Number_"/>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td>Created By</td>
                                      <td>
                                        <div id="VL_Created_By"></div>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td>Notes </td>

                                      	<td><s:textfield id="Notes_MC" name="searchMessageCenterVO.Notes"  cssStyle="width:250px"></s:textfield></td>
                                      
                                    </tr>
                                    </tbody>
                                </table>
                            </div></td>
                            <td class="tabDetailViewDL right-part"><div class=searchItemPanel>
                                <table border=0 cellspacing=2 cellpadding=0 width="100%">
                                  <tbody>
                                    <tr>
                                      <td height=25 colspan=3>Create Date  &nbsp;&nbsp;
                                        <input type="checkbox" id="__Create_date" name="lockBox" class="ClearCheckbox" onclick="clearDisabled('__Create_date',1);"/>
                                      </td>
                                    </tr>
                                    <tr>
                                      <td width="11%">
                                      </td>
                                      <td width="17%">From </td>
                                      <td nowrap="nowrap">
										<input id="searchMessageCenterVO_CreateDateStartsOn" name="searchMessageCenterVO.dateCreateDateFrom" cssClass="validate-date-ca" class="Clear1 Date" disabled="disabled" style="background:#ccc;width:152px;"/>
										<a onClick="if($('#__Create_date')[0].checked) g_Calendar.show(event,'searchMessageCenterVO_CreateDateStartsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;" href="javascript:%20void(0);"><img
												src="include/images/cal.gif" id="CreateDateStartsOnImg" border="0" align="middle">
										</a> yyyy/mm/dd
										</td>
                                    </tr>
                                    <tr>
                                      <td>&nbsp;</td>
                                      <td>To </td>
                                      <td>
											<input id="searchMessageCenterVO_CreateDateDateEndsOn" name="searchMessageCenterVO.dateCreateDateTo" cssClass="validate-date-ca" class="Clear1 Date" disabled="disabled" style="background:#ccc;width:152px;" />
											<a onClick="if($('#__Create_date')[0].checked) g_Calendar.show(event,'searchMessageCenterVO_CreateDateDateEndsOn',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;" href="javascript:%20void(0);"><img
													src="include/images/cal.gif" id="CreateDateDateEndsOnImg" border="0" align="middle" >
											</a> yyyy/mm/dd
										</td>
									</tr>
                                  </tbody>
                                </table>
                            </div></td>                                 
                          </tr>     
                          <tr>
	                          <td colspan="2" class=tabDetailViewDL>
	                          	  <div class=searchItemPanel>
	                          	  <table border=0 cellspacing=3 cellpadding=0 width="100%">
                                  <tbody>
                                  <tr>
                                      <td>
                                         Favorite &nbsp;&nbsp;<input type="checkbox" id="favorite_flag" class="ClearCheckbox"/>
                                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                          	 Private &nbsp;&nbsp;<input type="checkbox" id="private_flag" class="ClearCheckbox"/>
                                      </td>
                                  </tr>
                                  </tbody>
                                  </table>
			                         
		                          </div>
	                          </td>
                           </tr>              
						</table>
					</s:form>
	</div>
			<div style="width:100%;height:30px;">
				<div style="float: left; margin-top:-5px;">
					<br />
					<input type="button" id="" value="Search" onclick="startSearch();" />
					&nbsp;&nbsp;&nbsp;
					<input type="button" value="Clear" onclick="javascript:resetFormElementValue();" />
				</div>
				<div id="loadingImgDiv" style="display: none; float: right;">
					<img src="include/images/loading.gif" />
				</div>
		</div>
		<div style="width:100%;"><br />
			<div id="_MessageCenterDiv" style="width:100%;display:none"><br />
				<table border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed; width: 100%;">
					<tr>
						<td>
							<div align="left" style="width: 100%; overflow-x: auto;">
								<div id="_MessageCenter_dataTable"></div>
							</div>
						</td>
					</tr>
				</table>
				<table>
					<tr>
						<td>
							<span id="_MessageCenter_dataTable_page"></span>
						</td>
						<td>
							<input value="Download to Excel" type="button" onclick="downloadMessageCenterCsv()"
								style="margin-top:1px; cursor: pointer;">
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>	