<%@ page language="java" pageEncoding="ISO-8859-1"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.saninco.ccm.util.SystemUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script type="text/javascript" src="include/javascript/cookie.js"></script>
<script type="text/javascript" src="include/javascript/theme.js"></script>
<%
	//	pageContext.setAttribute("currentUserThemeId",SystemUtil.getCurrentUserThemeId());
	pageContext.setAttribute("currentUserThemeKey",SystemUtil.getThemeListKey());
 %>
<table width="100%" cellspacing="0" cellpadding="0" border="0" class="headerBg">
	<tr>
		<td class="logo" valign="bottom" rowspan="2">
			<div class="login"></div>
		</td>
		
		<td valign="top" align="right" >
			<table width="600px"  cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="welcome" id="welcome">
						Welcome,
						<strong><span id="ctl00_LoginName1"><sec:authentication property="principal.firstName"/>&nbsp;<sec:authentication property="principal.lastName"/></span> </strong>
						<a id="ctl00_LoginStatus1" href="securityLogout.action"><font style="color:000;">Logout</font></a>
					</td>
					<td nowrap class="welcome" align="right" style="padding-right: 8px;">
						<ul class="subTabs">
							<li>
								<a href="toMyProfile.action">My Profile </a>
							</li>

							<li>
								<a href="searchTicket.action" >Support </a>
							</li>
							<li>
								<a>Theme: &nbsp; 
								<span class="select1"><select onchange="changeUserTheme(this);">
									<c:forEach items="${CCM_SYSTEM_THEMES}" var="the">
										<option value="${the.key}"  ${currentUserThemeKey == the.key ? "selected=\"selected\"" : ""} >${the.value}</option>
									</c:forEach>
								</select></span>
								</a>
							</li>
						</ul>
					</td>
					
				</tr>
			</table>
		
		</td>
	</tr>
	<tr>
		<td align="right" style="padding: 0px 10px 0px 0px;">
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<table cellspacing="0" cellpadding="0" height="15px" border="0" width="100%">
				<tr>
					<td  colspan="2"  class="tabRow">
					<div id="tabRow_s">
						<ul>
						<sec:authorize ifAllGranted="FUNCTION_6000">
							<li>
								<a href="showDashboardList.action?currentPage=1&pageRecords=5" class="middle"><div class="left"></div><div class="middle">Dashboard</div><div class="right"></div><div class="clear"></div></a>
							</li>
						</sec:authorize>
						
						<sec:authorize ifAllGranted="FUNCTION_1000">
							<li>
								<a href="searchInvoice.action" class="middle"><div class="left"></div><div class="middle">Invoice</div><div class="right"></div><div class="clear"></div></a>
							</li>
                            
 						</sec:authorize>
 						
 						<sec:authorize ifAllGranted="FUNCTION_2000">
	 						<li>
	 							<a href="searchDispute.action" class="middle"><div class="left"></div><div class="middle">Dispute</div><div class="right"></div><div class="clear"></div></a>
	 						</li>
						</sec:authorize>
<%--						<sec:authorize ifAllGranted="FUNCTION_3000">--%>
						<sec:authorize ifAnyGranted="FUNCTION_3100,FUNCTION_3200">
							<li>
	                        	<a href="reportUser.action" class="middle"><div class="left"></div><div class="middle">Report</div><div class="right"></div><div class="clear"></div></a>
	                        </li>
						</sec:authorize>
							
<%--						<sec:authorize ifAllGranted="FUNCTION_4000">--%>
						<sec:authorize ifAnyGranted="FUNCTION_4100,FUNCTION_4110,FUNCTION_4120,FUNCTION_4130,FUNCTION_4140,FUNCTION_4200,FUNCTION_4400,FUNCTION_4500,FUNCTION_4700,FUNCTION_4800,FUNCTION_4900">
							<li>
								<!-- class="middle first_a" -->
	                        	<a href="searchAdminAction.action" class="middle"><div class="left"></div><div class="middle">Admin</div><div class="right"></div><div class="clear"></div></a>
	                        </li>
						
						</sec:authorize>
<%--						<%if(SystemUtil.getCurrentUserId() < 100){%>--%>
<%--						<li class="otherTab">--%>
<%--							<a href="doOperationsPage.action" class="otherTab">Operations</a>--%>
<%--						</li>--%>
<%--						<% }%>--%>
<%--     					<li class="otherTab1" >--%>
<%--							<img src="include/images/blank.gif" alt="" width="1" height="26" border="0">--%>
<%--						</li>--%>
						<sec:authorize ifAllGranted="FUNCTION_5600">
							<li>
	                        	<a href="showInventoryAndRate.action" class="middle"><div class="left"></div><div class="middle">Master Inventory</div><div class="right"></div><div class="clear"></div></a>
	                        </li>
	                    </sec:authorize>
 						<sec:authorize ifAllGranted="FUNCTION_5000">
 							<!-- <li>
	                        	<a href="searchCircuit.action" class="middle"><div class="left"></div><div class="middle">Circuit Inventory</div><div class="right"></div><div class="clear"></div></a>
	                        </li>
	 						<li>
								<a href="viewVendorInventory.action" class="middle"><div class="left"></div><div class="middle">Vendor Inventory</div><div class="right"></div><div class="clear"></div></a>
							</li>  -->
<%--							<li>--%>
<%--	                        	<a href="searchInventoryDashboard.action" class="middle"><div class="left"></div><div class="middle">Master Inventory</div><div class="right"></div><div class="clear"></div></a>--%>
<%--	                        </li>--%>
<%--							<li>--%>
<%--	                        	<a href="searchInventoryRateDashboard.action" class="middle"><div class="left"></div><div class="middle">Master Inventory Rate</div><div class="right"></div><div class="clear"></div></a>--%>
<%--	                        </li>--%>
	                        <li>
	                        	<a href="searchCircuitOld.action" class="middle"><div class="left"></div><div class="middle">Circuit Cost</div><div class="right"></div><div class="clear"></div></a>
	                        </li>
						</sec:authorize>
                       <!--  <sec:authorize ifAllGranted="FUNCTION_7200">
	                        <li>
	                        	<a href="searchContractAndTariffRules.action" class="middle"><div class="left"></div><div class="middle">Rate</div><div class="right"></div><div class="clear"></div></a>
	                        </li>
                        </sec:authorize> -->
                        <sec:authorize ifAllGranted="FUNCTION_7200">
	                        <li>
	                        	<a href="displayRateViewSearch.action" class="middle"><div class="left"></div><div class="middle">Rate</div><div class="right"></div><div class="clear"></div></a>
	                        </li>
                        </sec:authorize>
                        <sec:authorize ifAllGranted="FUNCTION_7300">
	                        <li>
	                        	<a href="searchQuoteInventory.action" class="middle"><div class="left"></div><div class="middle">Quote</div><div class="right"></div><div class="clear"></div></a>
	                        </li>
                        </sec:authorize>
	                       <!--  <li>
	                        	<a href="showTarget.action" class="middle first_a"><div class="left"></div><div class="middle">Target</div><div class="right"></div><div class="clear"></div></a>
	                        </li>-->
                        <sec:authorize ifAllGranted="FUNCTION_5300">
							<li>
								<a href="wikiShow.action" class="middle"><div class="left"></div><div class="middle">Wiki</div><div class="right"></div><div class="clear"></div></a>
							</li>
						</sec:authorize>
						<sec:authorize ifAllGranted="FUNCTION_5700">
							<li>
	                        	<a href="showMessageCenter.action" class="middle"><div class="left"></div><div class="middle">Message Center</div><div class="right"></div><div class="clear"></div></a>
	                        </li>
	                    </sec:authorize>
	                        <!-- <li>
	                        	<a href="searchContractTariffPriceList.action" class="middle"><div class="left"></div><div class="middle">Contract / Tariff / Price List</div><div class="right"></div><div class="clear"></div></a>
	                        </li> -->
	                        <sec:authorize ifAllGranted="FUNCTION_7100">
		                        <li>
		                        	<a href="searchContract.action" class="middle"><div class="left"></div><div class="middle">Contract</div><div class="right"></div><div class="clear"></div></a>
		                        </li>
	                        </sec:authorize>
	                        
						</ul>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>