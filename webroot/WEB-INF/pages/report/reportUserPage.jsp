<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link rel="stylesheet" type="text/css" href="include/javascript/Calendar/styles/calendar.css">
<link rel="stylesheet" type="text/css" href="include/javascript/flexbox/css/jquery.flexbox.css"><link>
 <link rel="stylesheet" type="text/css" href="include/css/hint.min.css">

<link rel="stylesheet" type="text/css" href="include/javascript/chosen/chosen.css"><link>
<style>

.chosen-container-multi .chosen-choices li.search-choice {
    padding: 3px 17px 3px 5px;
}

.chosen-container-active .chosen-choices {
  -webkit-box-shadow: none;
  box-shadow: none;
  min-height: 16px;
  overflow: hidden;
  box-shadow: 0 0 1px 1px #3877da;
  border-color: #3877da;
} 

.chosen-container-multi .chosen-choices {
	min-height: 16px;
	overflow: hidden;
}
.chosen-container-multi .chosen-choices li.search-field input[type="text"] {
  height: 16px;
  transform: translateY(-2px); /* 新加属性 */
  font-size: 12px;
}

.chosen-container-multi .chosen-choices li.search-field {
  height: 16px;
  line-height: 1;
}
.help-trigger {
    display: inline-block;
    width: 16px;
    height: 16px;
    margin-left: 5px;
    vertical-align: middle;
    background-image: url('include/images/validation_status/help.png');
    background-size: cover;
    margin-bottom: 3px;
}
.help-trigger::after {
    display: inline-block;
    min-width: 170px;
    white-space: normal;
    font-style: normal;
}
</style>

<script src="include/javascript/jquery/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
            var jQuery_1_12_4 = $.noConflict(true);
</script>
<script type="text/javascript" src="include/javascript/chosen/chosen.jquery.js"></script>
<script src="include/javascript/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="include/javascript/flexbox/js/jquery.flexbox.js"></script>
<script type="text/javascript" src="include/javascript/Calendar/simplecalendar.js"></script>
<script type="text/javascript" src="include/javascript/ccm/quicklink.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/yui2/tabview-min.js"></script>

<script type="text/javascript" src="include/javascript/yui/build/yahoo-dom-event.js"></script>
<script type="text/javascript" src="include/javascript/yui/build/animation-min.js"></script>
<script type="text/javascript" src="include/javascript/yui/build/slider-min.js"></script>
<script type="text/javascript" src="include/javascript/ccm/reportuser/runReportUser.js"></script>
<script type="text/javascript" src="include/javascript/ccm/reportuser/searchViewReportUser.js"></script>
<script type="text/javascript">
var BL_Array = {
	"results": [
		{
		    "id": "all",
		    "name": "All"
		}
		<c:forEach items="${banList}" var="v">
		,{
		    "id": "${v.key}",
		    "name": "${v.value}"
		}
		</c:forEach>
	],
	"total": ${fn:length(banList)}
};
var SC_Array = {
	"results": [
		{
		    "id": "",
		    "name": ""
		}
		<c:forEach items="${scoaCodeList}" var="v">
		,{
		    "id": "${v.key}",
		    "name": "${v.value}"
		}
		</c:forEach>
	],
	"total": ${fn:length(scoaCodeList)}
};
var CT_Array = {
	"results": [
		{
		    "id": "",
		    "name": ""
		}
		<c:forEach items="${chargeTypeList}" var="v">
		,{
		    "id": "${v.key}",
		    "name": "${v.value}"
		}
		</c:forEach>
	],
	"total": ${fn:length(chargeTypeList)}
};
var BanL_Array = {
	"results": [
		{
		    "id": "",
		    "name": ""
		}
		<c:forEach items="${banList}" var="v">
		,{
		    "id": "${v.key}",
		    "name": "${v.value}"
		}
		</c:forEach>
	],
	"total": ${fn:length(banList)}
};
$(function(){
	runReportBanListFlexbox = $('#BL_banList_Div').flexbox(BL_Array, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 260,
		onSelect : function(){
			$("#txtBanId").val(runReportBanListFlexbox.getValue());
			if(runReportBanListFlexbox.getValue() != "all"){
				getVendorNameByBanId($("#txtBanId").val());
			}else{
				$("#runReportVendorNameSpan").text("");
			}
		}
	});
	runReportBanListFlexbox.setValue("all");
  
});

</script>

<div id="pageContainer" class="tabForm" onMouseDown="if(g_Calendar && g_Calendar.hide)g_Calendar.hide();" style="padding-bottom:25px;" >
<!--<div id="pageContainer" class="tabForm">-->
	<h3 style="padding-top:0px; padding-bottom:4px;">
<!--	<h3>-->
		<b>Report User</b>
	</h3>
	<form id="downloadForm" action="download.action" method="post" target="__downloadIframe"
		style="display: none;">
		<input type="hidden" name="filePath"
			value="d:\X-Lite_Win32_1104o_56125_100106.exe" />
		<input type="hidden" name="fileName" value="aaa.exe" />
	</form>
	<div id="demo" class="yui-navset">
		<ul class="yui-nav">
			<sec:authorize ifAllGranted="FUNCTION_3100">
				<li>
					<a href="#tab1" onclick="reportUserTabInit.get('tab1');"><em>View Reports</em> </a> 
				</li>
			</sec:authorize>
			<sec:authorize ifAllGranted="FUNCTION_3200">
				<li>
					<a href="#tab2" onclick="reportUserTabInit.get('tab2');"><em>Run Reports</em> </a>
				</li>
			</sec:authorize>
		</ul>
		 <div class="yui-content">
			<div id="tab1">
				<table width="100%">
					<tr>
						<td valign="top">
							<div id="reportViewListDiv"></div>
						</td>
						<td valign="top" style="display: none;" id = "noneAndblok">
							<div id="ViewReportDetailDiv" style="margin-left:2%;height:100%;" >
								<div id="reportViewNameList" style="height:100%;width:99%; overflow-x: auto;" ></div>
								<div><input type="button" value="Cancel" onclick="cancleView();"></div>
							</div>
						</td>
					</tr>
				</table>
			  <form id="Download" action="download.action" method="post" style="display: none;">
	          <input type="hidden" name="filePath" value=""/>
		      <input type="hidden" name="fileName" value=""/>
	          </form>
			</div>

			<div id="tab2">
				<table width="100%">
					<tr>
						<td width="100%" valign="top">
							<div id="reportListDiv"></div>
						</td>
						<td valign="top">
							<div id="ReportDetailDiv">
								<div>
									<input id="txtReportId" type="hidden">
								</div>
								<div style="margin-left:20px;width:100%;height: 100%;" >
										<table width="100%" cellspacing="2" cellpadding="0" border="0">
														<tr>
															<td colspan="2"><div id="reportNameTD"></div></td>
														</tr>
														<tr>
															<td width="30%">
																Created Report Name:
															</td>
															<td>
																<input id="txtCreatedReportNameNew" />
																<span style="font: 15px; color: red; vertical-align: middle;">*</span>
															</td>
														</tr>
														
														<tr id="_analystTR">
															<td>Analyst User:</td>
															<td>
																<span class="select1"><s:select id="analystId_LC" name="rvo.analystId" list="anaylystUserList" headerKey="0"
																			headerValue="All" listKey="key" listValue="value"/></span>
															</td>
														</tr>
														<tr>
															<td>Sharing:</td>
															<td>
																<span class="select1"><select id="sharingNew" name="rvo.sharingNew" disabled="disabled" style="width:132px"/>
																	<option value="N">N</option>
																	<option value="Y">Y</option>
																</select></span>
															</td>
														</tr>
														
														<tr>
															<td>Send Email:</td>
															<td>
																<span class="select1"><select id="sendEmailNew" name="rvo.sendEmailNew" style="width:132px" onchange = "showEmailText(this.value)" />
																	<option value="N">N</option>
																	<option value="Y">Y</option>
																</select></span>
															</td>
														</tr>
														<tr id="_pLoginTr">
															<td>Login:</td>
															<td>
																<span class="select1"><select id="isLogin" name="rvo.isLogin" style="width:132px"   />
																	<option value="N">N</option>
																	<option value="Y">Y</option>
																</select></span>
																<div> <input type="hidden" id = "isLogin"/></div>
															</td>
														</tr>
														<tr id="_pEffectiveHourTr">
															<td>Effective Hour(s):</td>
															<td>
																<input id="txtEffectiveHour" type="text"/> <input type="hidden" id = "isEffectiveHour"/><i class="help-trigger hint--info hint--top-right hint--rounded" 
                                                          data-hint="Positive number or with one decimal positive number, eg.  1 or 1.5"></i>
                                                          
															</td>
														</tr>
														<tr id="_pDownloadTimes">
															<td>Download Time(s):</td>	
															<td>
																<input id="txtDownloadTimes" type="text"/> <input type="hidden" id = "isDownloadTimes"/> <i class="help-trigger hint--info hint--top-right hint--rounded" 
                                                          data-hint="Positive number without decimal,  eg. 1 or 2"></i>
                                                         
															</td>
														</tr>
														<tr id="_emailstTR">
															<td width="30%" valign="top">Email:</td>
															<td>
								                            	<textarea id="txtEmail" 
									                        	style="overflow:auto ; width:180px; height:100px;max-width:380px;resize: none;"
								           		                class="validate-text">
									                            </textarea>
                                                                <input type="hidden" id = "isEmail"/>
																<span style="font: 15px; color: red; vertical-align: middle;">*</span><i class="help-trigger hint--info hint--top-right hint--rounded" 
                                                                data-hint="Please use ',' to separate multiple email addresses."></i>
															</td>
														</tr>
														<tr id="_pBanIdTr">
															<td>Ban:</td>
															<td>
																<div><b>Vendor:</b><span id="runReportVendorNameSpan"></span></div>
																<input id="txtBanId" type="hidden">
																<span id="BL_banList_Div"></span>
																<span id="banIdIsNullMessageSpan" style="color:'red'"></span>
															</td>
														</tr>
														<!-- 
														<tr>
															<td>File Format:</td>
															<td>
																<select id="reportFormatNew" name="rvo.reportFormatNew" style="width:120px"  />
																	<option value="csv">CSV</option>
																</select>
															</td>
														</tr>
														 -->
														<tr id="_onlyUserTR">
															<td width="150"> 
																Current User Only: 
															</td>
															<td>
																<input id="chkOnlyUser" class="noBorderRadioButton" type="checkbox" />
															</td>
														</tr>
														<tr id="_fdateTR">
															<td id="p_from_date_label" width="150">
																From Date:
															</td>
															<td>
																<input id="_p_from_date" />
																<a
																	onClick="g_Calendar.show(event,'_p_from_date',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;"
																	href="javascript:%20void(0);"><img
																		src="include/images/cal.gif" id="disputeDateStartsOnImg"
																		border="0" align="middle"></a>
																<span style="font: 15px; color: red; vertical-align: middle;">*</span>
																		yyyy/MM/dd
															</td>
														</tr>
														<tr id="_tdateTR">
															<td id="p_to_date_label" width="150">
																To Date:
															</td>
															<td>
																<input id="_p_to_date" />
																<a
																	onClick="g_Calendar.show(event,'_p_to_date',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;"
																	href="javascript:%20void(0);"><img
																		src="include/images/cal.gif" id="disputeDateStartsOnImg"
																		border="0" align="middle"></a>
																<span style="font: 15px; color: red; vertical-align: middle;">*</span>
																		yyyy/MM/dd
															</td>
														</tr>
														<tr id="_keyWord">
															<td width="150">
																Keyword:
															</td>
															<td>
																<input id="_key_word" />
															</td>
														</tr>
														
														
										</table>
										<table width="100%" cellspacing="2" cellpadding="0" border="0" id = "_show_Report_Parameter">
										</table>
										
										<input type="button" value="Generate Report" onclick="checkNameIsRepeat();">
																&nbsp;&nbsp;&nbsp;
										<input type="button" value="Cancel" onclick="cancle();">
								  </div>
							</div>
						</td>
					</tr>
				</table>
			</div>
	    </div>
	</div>
</div>
<iframe id="__downloadIframe" style="display: none;"></iframe>
