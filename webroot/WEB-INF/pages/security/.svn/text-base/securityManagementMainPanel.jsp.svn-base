<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<link rel="stylesheet" type="text/css" href="include/javascript/Calendar/styles/calendar.css">
<link href="include/css/security.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="include/javascript/flexbox/css/jquery.flexbox.css"><link>

<script type="text/javascript" src="include/javascript/ccm/common.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/flexbox/js/jquery.flexbox.js"></script>
<script type="text/javascript" src="include/javascript/Calendar/simplecalendar.js"></script>
<script type="text/javascript" src="include/javascript/yui2/tabview-min.js"></script>
<script type="text/javascript" src="include/javascript/ccm/quicklink.js" language="javascript"></script>
<script type="text/javascript" src="include/javascript/ccm/viewSecurityManagement.js" language="javascript"></script>


<div id="pageContainer" class="tabForm">
	<h3><b>Admin</b></h3>
	<div id="adminTab" class="yui-navset">
		<ul class="yui-nav">
		<sec:authorize ifAllGranted="FUNCTION_4100">
			<li class="selected" onclick="activeIndexClick(0)">
				<a href="#userListTab"><em>User</em> </a>
			</li>
		</sec:authorize>
		<sec:authorize ifAllGranted="FUNCTION_4200">
			<li onclick="activeIndexClick(1)">
				<a href="#roleTab"><em>Role</em> </a>
			</li>
		</sec:authorize>
		<sec:authorize ifAllGranted="FUNCTION_4500">
			<li onclick="activeIndexClick(2)">
				<a href="#reportAdminTab"><em>Report Admin</em> </a>
			</li>
		</sec:authorize>
		<sec:authorize ifAllGranted="FUNCTION_4400">
			<li onclick="activeIndexClick(3)">
				<a href="#transactionTab"><em>Transaction</em> </a>
			</li>
		</sec:authorize>
		<sec:authorize ifAllGranted="FUNCTION_4700">
			<li onclick="activeIndexClick(4)">
				<a href="#newScoaCodeTab"><em>SCOA Code</em> </a>
			</li>
		</sec:authorize>
		<sec:authorize ifAllGranted="FUNCTION_4800">
			<li onclick="activeIndexClick(5)">
				<a href="#vendorTab"><em>Vendor Maintenance</em> </a>
			</li>
		</sec:authorize>
		<sec:authorize ifAnyGranted="FUNCTION_4900">
			<li onclick="activeIndexClick(6)">
				<a href="#banTab"><em>BAN Maintenance</em> </a>
			</li>
		</sec:authorize>
		<sec:authorize ifAllGranted="FUNCTION_4110">
			<li onclick="activeIndexClick(7)">
				<a href="#teamTab"><em>Team Maintenance</em> </a>
			</li>
		</sec:authorize>
		<sec:authorize ifAllGranted="FUNCTION_4120">
			<li onclick="activeIndexClick(8)">
				<a href="#modifyAdjustmentTab"><em>Modify Adjustment</em> </a>
			</li>
		</sec:authorize>
		<sec:authorize ifAllGranted="FUNCTION_4130">
			<li onclick="activeIndexClick(9)" >
				<a href="#securityIp"><em>Team Security IP</em> </a>
			</li>
		</sec:authorize>
			
		<sec:authorize ifAllGranted="FUNCTION_4140">
			<li onclick="activeIndexClick(10)" >
				<a href="#wikiManagement"><em>Wiki Management</em> </a>
			</li>
		</sec:authorize>
		</ul>
	    
	      <div id="zhuye" >
	    	<div id="head" class="tabForm">
	    		<h3 style="padding-left:8px">User Data</h3>
		    <s:form id="form0_user_data">
	    	  <div id="head_1" >
	        	<table  width="100%"  border="0" cellpadding="0" cellspacing="3" >
	              <tr>
	                <td >First Name:</td>
	                <td ><s:textfield id="form0_first_name" name="user.firstName" cssClass="validate-name" />&nbsp;&nbsp;<span style="font:15px;color:red;vertical-align:middle;">*</span></td>
	                <td>Last Name:</td>
	                <td><s:textfield id="form0_last_name" name="user.lastName" cssClass="validate-name" />&nbsp;&nbsp;<span style="font:15px;color:red;vertical-align:middle;">*</span></td>
	              </tr>
	              <tr>
	                <td>Address:</td>
	                <td><s:textfield id="form0_address" name="user.address" cssClass="validate-address" /></td>
	                <td>Primary Phone:</td>
	                <td><s:textfield id="form0_primary_phone" name="user.primaryPhone" cssClass="validate-phone" /></td>
	              </tr>
	              <tr>
	                <td>Office Phone:</td>
	                <td><s:textfield id="form0_office_phone" name="user.officePhone" cssClass="validate-phone" /></td>
	                <td>Cell phone:</td>
	                <td><s:textfield id="form0_cell_plon" name="user.cellPhone" cssClass="validate-phone" /></td>
	              </tr>
	              <tr>
	              	<td>Fax Number:</td>
	                <td><s:textfield id="form0_fax_number" name="user.faxNumber" cssClass="validate-alp" /></td>
	                <td>Email:</td>
	                <td><s:textfield id="form0_email" name="user.email" cssClass="validate-email" /></td>
	              </tr>
	              <tr>
	              	<td>Initials:</td>
	                <td><s:textfield id="form0_initials" name="user.initials" cssClass="validate-alp" /></td>
	                <td>User Name:</td>
	               	<td><s:textfield id="form0_user_name" name="user.userName" cssClass="validate-name" />&nbsp;&nbsp;<span style="font:15px;color:red;vertical-align:middle;">*</span></td>
	               </tr>
	              <tr>
	              	<td>Supervisor User:</td>
	                <td><span class="select1"><s:select id="form0_supervis" name="user.supervisorUserId" list="userAllList" headerKey="" headerValue="" listKey="key" listValue="value" cssStyle="width:132px" /></span></td>
	                <td>Backup User:</td>
	                <td><span class="select1"><s:select id="form0_backupUserId" name="user.backupUserId" list="userAllList" headerKey="" headerValue="" listKey="key" listValue="value" cssStyle="width:132px" /></span></td>
	              </tr>
				<s:if test='userId!=null'>
	              <tr height="22px">
	              	<td>Created Time:</td>
	                <td><s:date format="yyyy-MM-dd  HH:mm:ss" name="user.createdTimestamp" /></td>
	                <td>Created By:</td>
	                <td><s:text name="createdUser.firstName" />&nbsp;<s:text name="createdUser.lastName" /></td>
	              </tr>
	              
	              <tr height="22px">
	              	<td>Modified Time:</td>
	                <td><s:date format="yyyy-MM-dd  HH:mm:ss" name="user.modifiedTimestamp" /></td>
	                <td>Modified By:</td>
	                <td><s:text name="modifiedUser.firstName" />&nbsp;<s:text name="modifiedUser.lastName" /></td>
	              </tr>
	              <tr height="22px">
	              	<td>Last Login Time:</td>
	                <td><s:date format="yyyy-MM-dd  HH:mm:ss" name="user.lastLoginTimestamp" /></td>
	              </tr>
				</s:if>
				   <tr>
	              	 <td valign="bottom" align="left"><input type="button" value="save" onclick="upRoleAndUserPs();">&nbsp;&nbsp;<input type="button" value="cancel" onclick="cancelPage();"></td>
	              	
	              	 <!--  <td valign="bottom" align="left"><input type="button" value="cancel" onclick="cancelPage();">&nbsp;&nbsp;</td>-->
	              	 
	              <s:if test='userId!=null'> 
	              	 <td valign="bottom" align="right"><input type="button" id="__active_flag" value="${(user.activeFlag != 'Y') ? "Active" : "De-active" }" onclick="modifyRadio();">&nbsp;&nbsp;</td>
	              </s:if>
	              
	               </tr>
	            </table>
	            </div>
	            	<div id="head_2">
		            	<table width="100%" cellpadding="0" cellspacing="3"  border="0">
		                  <tr>
		                    <td>New Password :</td>
		                    <td><input type="password" id="password" style="width: 128px;" />&nbsp;&nbsp;<span id="pswReadStar" style="font:15px;color:red;vertical-align:middle;display:none;">*</span></td>
		                  </tr>
		                  <tr>
		                    <td>Confirm New Password :</td>
		                    <td><input type="password" id="passwordAgain" style="width: 128px;" />&nbsp;&nbsp;<span id="pswaReadStar" style="font:15px;color:red;vertical-align:middle;display:none;">*</span></td>
		                  </tr>
		                  <tr>
		                 	 <td colspan="2">
		                 		 <div id="__judge_password" style="color: red;"></div>
		                  	</td>
		                  </tr>
		         <s:if test='userId!=null'>
		                  <tr>
		                 	<td>Login failure count :&nbsp;&nbsp;&nbsp;&nbsp;
		                 	<span id="__login_Failure_Count">
		                 		<fmt:formatNumber  value="${(user.loginFailureCount != null) ? user.loginFailureCount : 0}"/>
		                 	</span>
		                 	</td>
		                    <td><input type="button" value="Reset Failure Count" onclick="resetFailureCount();" /></td>
		                  </tr>
		         </s:if>
		                  <tr>
		                    <td colspan="2"><input type="checkbox" class="noBorderRadioButton" id="_lock_out" name="lockBox" ${user.isLockOut=="N"?"":"checked=\"checked\""}/>
		                    User is Locked Out
		                    </td>
		                   </tr>
		                  <tr>
		                  </tr>
		                </table><br />
		                <div id="head_2_1" style="padding-left:10px;">	
							<h3 style="padding-bottom:5px;" >Role List</h3>
							
							<s:checkboxlist theme="xhtml" template="customcheckboxlist.ftl" cssStyle="border:0px;" id="checktd" name="roleBox"  list="roleList" listKey="key" listValue="value" value="userRoleList" >
								
							</s:checkboxlist>
						</div>
		              </div>
		                <br />
		      </s:form>     	
			</div>
			
	<s:if test='userId!=null'>
		<div id="down">
	        <s:form id="form1">
	      	    <div id="down_1" class="tabForm"> 
	            <h3>Delegation</h3>
	            	 <table width="100%" border="0" height="90" cellspacing="0" cellpadding="0">
						<tr>
							<td >
								Delegation to:
							</td>
							<td>
								<span class="select1"><s:select id="viewSecurityVO_delegationId" list="userList" headerKey="" 
								headerValue="" listKey="key" listValue="value" value="user.id" cssStyle="width:128px"/></span>
							</td>
						</tr>
						<tr>
							<td>
								Start Date:
							</td>
							<td>
								<s:textfield name="ViewSecurityVO.delegationDateStartsOn"
									id="viewSecurityVO_delegationDateStartsOn" cssClass="validate-date-caa" ></s:textfield>
								<a
									onClick="g_Calendar.show(event,'viewSecurityVO_delegationDateStartsOn',false, 'yyyy-mm-dd', new Date(1990,1,1)); return false;"
									href="javascript:%20void(0);"><img
										src="include/images/cal.gif" id="delegationDateStartsOn"
										border="0" align="middle"></a>
										yyyy-MM-dd
							</td>
						</tr>
						<tr>
							<td>
								End Date:
							</td>
							<td>
								<s:textfield name="ViewSecurityVO.delegationDateEndsOn"
									id="viewSecurityVO_delegationDateEndsOn" cssClass="validate-date-caa" ></s:textfield>
								<a
									onClick="g_Calendar.show(event,'viewSecurityVO_delegationDateEndsOn',false, 'yyyy-mm-dd', new Date(1990,1,1)); return false;"
									href="javascript:%20void(0);"><img
										src="include/images/cal.gif" id="delegationDateEndsOn"
										border="0" align="middle"></a>
										yyyy-MM-dd
							</td>
						</tr>
						<tr>
							<td colspan="2"><div id="_buttonDelegation"></div></td>
						</tr>
					</table>
					<br />
	            	<div align="left" style="width: 98%; height: 215px;overflow-y: auto; border: 0px solid;" class="delegation_color" >
					 	<div id="_dataTableDelegation" ></div>
		            </div>
		            <div id="_dataTableDelegation_page"></div>
		    	</div>
	        </s:form>
	    
	     <s:form id="form2">
	        <div id="down_2" class="tabForm">
	        <h3>Privilege</h3>
	        	<table width="100%" height="90" border="0"cellspacing="0" cellpadding="0">
	        		<tr >
	        			<td>Vendor:</td>
	        			<td>
							<div id="security_previledge_vendorId"></div>
						</td>
	        		</tr>
	        		<tr>
	        			<td>Ban:</td>
	        			<td><span class="select1"><s:select id="security_previledge_banId" list="banList" headerKey="all"
							headerValue="All" listKey="key" listValue="value" cssStyle="width:240px"/></span></td>
	        		</tr>
	        		<tr>
	        			<td colspan="2"><input type="button" value="  Add   " onclick="verifyPreviledgeData();"></td>	
	        		</tr>
	        		<tr>
	        			<td colspan="2">&nbsp;</td>	
	        		</tr>
	        	</table><br />
	       		 <div align="left" style="width: 98%; height: 215px;overflow-y: auto; border: 0px solid;"  class="privilege_color">
					<div id="_dataTablePreviledge"></div>
					<div id="_dataTablePreviledge_page"></div>
				</div>
	    	</div>
	    </s:form>
		</div>
	</s:if>



<div class="save-confirm" id="saveConfirm">
	<div class="hd">
		Please confirm
	</div>
	<div class="bd">
		<table cellspacing=8 cellpadding=0 border=0 width=100%>
			<tr>
				<td>
					Are you sure to delete the item ?
				</td>
			</tr>
		</table>
	</div>
</div>

<div class="yui-pe-content" id="buttonConfirm">
	<div class="hd">
		Please confirm
	</div>
	<div class="bd">
		<table cellspacing=8 cellpadding=0 border=0 width=100%>
			<tr>
				<td>
					Modify the user successfully, Make sure that the next step !
				</td>
			</tr>
		</table>
	</div>
</div>

<SCRIPT type="text/javascript"> 
	var user = ${param.userId == null ? "false":"true"};
	var userId = ${param.userId == null ? "null":param.userId};
	userName = "${user.userName}";
</SCRIPT>

<script type="text/javascript">
var VL_Array_Vendor = {
	"results": [
		{
		    "id": "all",
		    "name": "All"
		}
		<c:forEach items="${vendorList}" var="v">
		,{
		    "id": "${v.key}",
		    "name": "${v.value}"
		}
		</c:forEach>
	],
	"total": ${fn:length(vendorList)}
};
$(function(){
	securityPreviledgeVendorId = $('#security_previledge_vendorId').flexbox(VL_Array_Vendor, {
		maxCacheBytes : 200000,
		highlightMatches : true,
		autoCompleteFirstMatch : false,
		paging: false,
		width : 260,
		onSelect : function(){
			getBanListByVendorIdByPreviledge(0);
		}
	});
	securityPreviledgeVendorId.setValue("all");
});
</script>
