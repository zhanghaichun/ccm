<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript" src="include/javascript/ccm/common/gotoWorkspace.js"></script>
<script type="text/javascript" src="include/javascript/ccm/common/workspace.js"></script>

 
<div>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td>
			<ul id="Left_Menu">
				<!-- My Workspace context menu -->
				<li id="workspaceLi">
					<div class="ML_Head">My Workspace</div>
					<!-- My Workspace Sections -->
					<ul id="My_Workspace_Menu" class="sections-menu">
						<!-- Past Due Section -->
						<li class="past-due">
							<div onclick="tems.actions.invoicesSearch.searchPastDue(-1,7);">
								<div><img src="include/images/light_bulb/red.png" /></div>
								<div><span>Past Due (<label id="redPastDue" class="redPastDue_"></label>)</span></div>
							</div>
						</li>
					   	
						<!-- Due in 7 days Section -->
						<li>
							<div>
								<div><img src="include/images/light_bulb/closeicon.png" id="menuqi"
									onclick="tems.actions.compandSubmenu.searchInvoice7('menuqi');"/></div>
								<div onclick="tems.actions.invoicesSearch.searchInvoice(-1,97,'Due in 7 days');">
									Due in 7 days(<label id="redInvoiceCount" class="redInvoiceCount_"></label>)
								</div>
							</div>
							<!-- Sub menu items of Due in 7 days Section -->
							<ul id="xialaimenuqi" class="submenu-section">
								<li>
									<div onclick="tems.actions.invoicesSearch.searchInvoice(-1,90,'Today to Due');">
										<div><img src="include/images/light_bulb/red.png" /></div>
										<div>Today to Due (<label id="redInvoiceCountr0" class="redInvoiceCountr0_"></label>)</div>
									</div>
								</li>
								<li>
									<div onclick="tems.actions.invoicesSearch.searchInvoice(-1,1,'1 Day to Due');">
										<div><img src="include/images/light_bulb/red.png" /></div>
										<div>1 Day to Due (<label id="redInvoiceCountr1" class="redInvoiceCountr1_"></label>)</div>
									</div>
								</li>
								<li>
									<div onclick="tems.actions.invoicesSearch.searchInvoice(-1,2,'2 Days to Due');">
										<div><img src="include/images/light_bulb/red.png" /></div>
										<div>2 Days to Due (<label id="redInvoiceCountr2" class="redInvoiceCountr2_"></label>)</div>
									</div>
								</li>
								<li>
									<div onclick="tems.actions.invoicesSearch.searchInvoice(-1,3,'3 Days to Due');">
										<div><img src="include/images/light_bulb/red.png" /></div>
										<div>3 Days to Due (<label id="redInvoiceCountr3" class="redInvoiceCountr3_"></label>)</div>    
									</div>             			
								</li>
								<li>
									<div onclick="tems.actions.invoicesSearch.searchInvoice(-1,4,'4 Days to Due');">
										<div><img src="include/images/light_bulb/red.png" /></div>
										<div>4 Days to Due (<label id="redInvoiceCountr4" class="redInvoiceCountr4_"></label>)</div>    
									</div>             			
								</li>
								<li>
									<div onclick="tems.actions.invoicesSearch.searchInvoice(-1,5,'5 Days to Due');">
										<div><img src="include/images/light_bulb/red.png" /></div>
										<div>5 Days to Due (<label id="redInvoiceCountr5" class="redInvoiceCountr5_"></label>)</div>    
									</div>             			
								</li>
								<li>
									<div onclick="tems.actions.invoicesSearch.searchInvoice(-1,6,'6 Days to Due');">
										<div><img src="include/images/light_bulb/red.png" /></div>
										<div>6 Days to Due (<label id="redInvoiceCountr6" class="redInvoiceCountr6_"></label>)</div>    
									</div>             			
								</li>
								<li>
									<div onclick="tems.actions.invoicesSearch.searchInvoice(-1,7,'7 Days to Due');">
										<div><img src="include/images/light_bulb/red.png" /></div>
										<div>7 Days to Due (<label id="redInvoiceCounty7" class="redInvoiceCountr7_"></label>)</div>    
									</div>             			
								</li>
							</ul>
						</li>
					   	 
						<!-- Due in 15 days Section -->
					 	<li>
							<div>
								<div><img src="include/images/light_bulb/closeicon.png" id ="menusw"
									onclick="tems.actions.compandSubmenu.searchInvoice15('menusw');"/></div>
								<div onclick="tems.actions.invoicesSearch.searchInvoice(-1,915,'Due in 15 days');">
									Due in 15 days
									(<label id="yellowInvoiceCount" class="yellowInvoiceCount_"></label>)
								</div>
							</div>

							<!-- Sub menu items of Due in 15 days Section -->
							<ul id="xialaiswmenusw" class="submenu-section">                  		   
								<li>
									<div onclick="tems.actions.invoicesSearch.searchInvoice(-1,8,'8 Days to Due');">
										<div><img src="include/images/light_bulb/yellow.png"/></div>
										<div>8 Days to Due (<label id="redInvoiceCounty8" class="redInvoiceCounty8_"></label>)</div>
									</div>
								</li>
								<li>
									<div onclick="tems.actions.invoicesSearch.searchInvoice(-1,9,'9 Days to Due');">
										<div><img src="include/images/light_bulb/yellow.png" /></div>
										<div>9 Days to Due (<label id="redInvoiceCounty9" class="redInvoiceCounty9_"></label>)</div>
									</div>
								</li>
								<li>
									<div onclick="tems.actions.invoicesSearch.searchInvoice(-1,10,'10 Days to Due');">
										<div><img src="include/images/light_bulb/yellow.png" /></div>
										<div>10 Days to Due (<label id="redInvoiceCounty10" class="redInvoiceCounty10_"></label>)</div>    
									</div>             			
								</li>
								<li>
									<div onclick="tems.actions.invoicesSearch.searchInvoice(-1,11,'11 Days to Due');">
										<div><img src="include/images/light_bulb/yellow.png" /></div>
										<div>11 Days to Due (<label id="redInvoiceCounty11" class="redInvoiceCounty11_"></label>)</div>    
									</div>             			
								</li>
								<li>
									<div onclick="tems.actions.invoicesSearch.searchInvoice(-1,12,'12 Days to Due');">
										<div><img src="include/images/light_bulb/yellow.png" /></div>
										<div>12 Days to Due (<label id="redInvoiceCounty12" class="redInvoiceCounty12_"></label>)</div>    
									</div>             			
								</li>
								<li>
									<div onclick="tems.actions.invoicesSearch.searchInvoice(-1,13,'13 Days to Due');">
										<div><img src="include/images/light_bulb/yellow.png" /></div>
										<div>13 Days to Due (<label id="redInvoiceCounty13" class="redInvoiceCounty13_"></label>)</div>    
									</div>             			
								</li>
								<li>
									<div onclick="tems.actions.invoicesSearch.searchInvoice(-1,14,'14 Days to Due');">
										<div><img src="include/images/light_bulb/yellow.png" /></div>
										<div>14 Days to Due (<label id="redInvoiceCounty14" class="redInvoiceCounty14_"></label>)</div>    
									</div>             			
								</li>
								<li>
									<div onclick="tems.actions.invoicesSearch.searchInvoice(-1,15,'15 Days to Due');">
										<div><img src="include/images/light_bulb/yellow.png" /></div>
										<div>15 Days to Due (<label id="redInvoiceCounty15" class="redInvoiceCounty15_"></label>)</div>    
									</div>             			
								</li>
							</ul>
						</li>
					   	 
						<!-- All My Invoices Section -->
						<li>
							<div onClick="tems.actions.invoicesSearch.searchInvoice(-1,-1);">
								<div><img src="include/images/light_bulb/green.png" /></div>
								<div>All My Invoices (<label id="greenInvoiceCount" class="greenInvoiceCount_"></label>)</div>
							</div>
						</li>
						
						<!-- External Approve Bucket -->
                        <li>
                            <div onClick="tems.actions.invoicesSearch.listExternalApproveInvoices('-1');">
                                <div><img src="include/images/light_bulb/green.png" /></div>
                                <div>External Approve (<label id="externalApproveInvoiceCount" class="externalApproveInvoiceCount_"></label>)</div>
                            </div>
                        </li>
					     
						<!-- Unknown Invoices Section -->
						<li>
							<div onClick="tems.actions.invoicesSearch.unknownInvoices('-1')">
								<div><img src="include/images/light_bulb/yellow.png" /></div>
								<div>Unknown Invoices (<label id="unknownInvoicesCount" class="unknownInvoicesCount_"></label>)</div>
							</div>
						</li>
						
						<!-- Missing Invoices Section -->
						<li>
							<div onClick="tems.actions.invoicesSearch.missingInvoices('-1')">
								<div><img src="include/images/light_bulb/red.png" /></div>
								<div>Missing Invoices (<label id="missingInvoicesCount" class="missingInvoicesCount_"></label>)</div>
							</div>
						</li>
						
						<!-- Preload Invoices Section -->
						<li>
							<div onClick="tems.actions.invoicesSearch.preloadInvoices('-1')">
								<div><img src="include/images/light_bulb/yellow.png" /></div>
								<div>Preload Invoices (<label id="preloadInvoicesCount" class="preloadInvoicesCount_"></label>)</div>
							</div>
						</li>
						
						<!-- Payment in Process Section -->
						<li>
							<div onClick="tems.actions.invoicesSearch.invoiceInProcess(-1,-1)">
								<div><img src="include/images/light_bulb/yellow.png" /></div>
								<div>Payment in Process (<label id="paymentInProcessCount" class="paymentInProcessCount_"></label>)</div>
							</div>
						</li>
						
						<!-- Disputes in Process Section 
						<li>
							<div onClick="tems.actions.invoicesSearch.invoiceInProcess(-1,-2)">
								<div><img src="include/images/light_bulb/yellow.png" /></div>
								<div>Disputes in Process (<label id="disputesInProcessCount"></label>)</div>
							</div>
						</li>
						-->
						<!-- Payment in Exception Section -->
						<li>
							<div onClick="tems.actions.invoicesSearch.invoiceInProcess(-1,-3)">
								<div><img src="include/images/light_bulb/yellow.png" /></div>
								<div>Payment in Exception (<label id="paymentInExceptionCount" class="paymentInExceptionCount_"></label>)</div>
							</div>
						</li>
						
						<!-- Invoice Reject Section -->
						<li>
							<div style="cursor: pointer; height:25px;" onClick="tems.actions.invoicesSearch.searchInvoiceReject(-1)">
								<div><img src="include/images/light_bulb/yellow.png" /></div>
								<div>Invoice Reject (<label id="invoiceRejectCount" class="invoiceRejectCount_"></label>)</div>
							</div>
						</li>
						<!-- Disputes Bucket Section -->
						<%--<li style="display: none;"> --%>
							<li>
								<div >
									<div><img src="include/images/light_bulb/closeicon.png" class="db-fold-menu-icon"
										onclick="tems.actions.compandSubmenu.compandDisputesBucket(0);"/></div>
									<div onclick="tems.actions.invoicesSearch.searchDisputesBucket(-1, -1, 'Dispute Buckets');">
										Dispute Buckets (<label id="totalDisputesBucket" class="totalDisputesBucket_"></label>)
									</div>
								</div>
								<!-- Sub menu items of Disputes Bucket Section -->
								<ul class="disputes-buckets-fold-menu submenu-section">
									<li>
										<div onclick="tems.actions.invoicesSearch.searchDisputesBucket(-1,30,'0 - 30 Days');">
											<div><img src="include/images/light_bulb/red.png" /></div>
											<div>0 - 30 Days (<label id="first30Disputes" class="first30Disputes_"></label>)</div>
										</div>
									</li>
									<li>
										<div onclick="tems.actions.invoicesSearch.searchDisputesBucket(-1,60,'31 - 60 Days');">
											<div><img src="include/images/light_bulb/red.png" /></div>
											<div>31 - 60 Days (<label id="range31To60Disputes" class="range31To60Disputes_"></label>)</div>
										</div>
									</li>
									<li>
										<div onclick="tems.actions.invoicesSearch.searchDisputesBucket(-1,90,'61 - 90 Days');">
											<div><img src="include/images/light_bulb/red.png" /></div>
											<div>61 - 90 Days (<label id="range61To90Disputes" class="range61To90Disputes_"></label>)</div>
										</div>
									</li>
									<li>
										<div onclick="tems.actions.invoicesSearch.searchDisputesBucket(-1,120,'91 - 120 Days');">
											<div><img src="include/images/light_bulb/red.png"/></div>
											<div>91 - 120 Days (<label id="range91To120Disputes" class="range91To120Disputes_"></label>)</div>    
										</div>             			
									</li>
									<li>
										<div onclick="tems.actions.invoicesSearch.searchDisputesBucket(-1,180,'121 - 180 Days');">
											<div><img src="include/images/light_bulb/red.png" /></div>
											<div>121 - 180 Days (<label id="range121To180Disputes" class="range121To180Disputes_"></label>)</div>    
										</div>             			
									</li>
									<li>
										<div class="divopen" onclick="tems.actions.invoicesSearch.searchDisputesBucket(-1, 0, '180+ Days');">
											<div><img src="include/images/light_bulb/red.png" /></div>
											<div>180+ Days (<label id="over180Disputes" class="over180Disputes_"></label>)</div>    
										</div>             			
									</li>
								</ul>
							</li>
					</ul>
				</li>
				
				<!-- Backup context menu -->
				<li id="backupLi" style="display: none;">
					<div class="ML_Head">&nbsp;&nbsp;Backup </div>
					<div id="backupDiv" style="border:0;">
						
					</div>
				</li>
				
				<!-- Delegation context menu -->
				<li id="delegationLi" style="display: none;">
					<div class="ML_Head">&nbsp;&nbsp;Delegation </div>
					<div id="delegationDiv" style="border:0;">
						
					</div>
				</li>
				
				<!-- My Team context menu -->
				<li id="myTeamLi" style="display: none;">
					<div class="ML_Head">&nbsp;&nbsp;My Team</div>
					<div id="myTeamDiv" style="border:0;">
					</div>
				</li>
				
				<!-- Quick Links context menu -->
				<li id="quickLinksLi">
					<div class="ML_Head">Quick Links</div>
					
				</li> <!-- quick Links Li -->
				
			</ul> <!-- left menu -->
		</td>
	</tr>
	<tr>
		<td>

			<div id="div1" style="visibility:visible;">
				<ul class="subMenu" id="_quicklink" style="visibility:visible;">
					<s:iterator value="quicklinkList" var="quicklinko">
						<li id="ql_<s:property value = "#quicklinko.key"/>">
							<table width="100%" border=0 cellpadding=0 cellspacing=0>
								<tr>
									<td valign="middle">
										<a href="#" onclick="getThisPageName(<s:property value = "#quicklinko.key"/>);">
											<s:property value="#quicklinko.value" /> 
										</a>
									</td>
									<td>
										<img style="width:16px;height:16px;" src="include/images/reject160.png" 
											onclick="deleteQuicklink('<s:property value = '#quicklinko.key'/>');" alt="Delete">
									</td>
								</tr>
							</table>
						</li>

					</s:iterator>
				</ul>
			</div>

			<script language="Javascript">

				/*'displayRateViewSearch.action'*/
				var url = window.location.href;
				var show;
				var cookieKey = 'showLeftCol'; 

				
				if ( url.indexOf('displayRateViewSearch.action') !== -1 ) { 
					// 针对 Rate Tab 页面需要在开始的时候隐藏左侧菜单
					
					cookieKey = 'showRateTabLeftCol';
					
					if (!Get_Cookie(cookieKey)) {
					    Set_Cookie(cookieKey, 'false', 30, '/', '', '');
					}

				} else {

					if (!Get_Cookie(cookieKey)) {
						Set_Cookie(cookieKey, 'true', 30, '/', '', '');
					}
					
				}

				var show = Get_Cookie(cookieKey);

				if (show == 'true') {
					this.document.getElementById('leftCol').style.display = 'inline';
					document['HideHandle'].src = 'include/images/hide.gif';
				} else {
					this.document.getElementById('leftCol').style.display = 'none';
					document['HideHandle'].src = 'include/images/show.gif';
				}
				
			</script>
            
			<img src="include/images/blank.gif" alt="" width="160" height="1" border="0">
		</td>
	</tr>
</table>

<div id="_mainContentTemplate" style="display: none;">
	<table width="100%" cellspacing="0" cellpadding="0" border="0" style="margin-bottom: 4px" class="tabForm">
		<tr>
			<td>
				<!-- Invoices records table. -->
				<div id="_dataTable_"></div>
			</td>
		</tr>
		<tr>
			<td>
				<!-- Pagination ( and download to excel button). -->
				<table border=0 width=100% id="_paginationTable_" style="display: none;">
					<tbody style="float:right;">
						<tr width=100%>
							<td align="right" width=100% style="padding-right: 15px;">
								<img style="cursor: pointer;" src="include/images/button_previous_first.gif" title="First page"
									onclick="BUSINESS.page.getFirstPage();"/>
								<img style="cursor: pointer;" src="include/images/button_previous.gif" title="Previous page"
									onclick="BUSINESS.page.getPrevPage();">
								Page
								<input id="__curPageNo_" type="text" style="width: 30px;padding-left:0;text-align:center;" value="1"
									onkeydown="BUSINESS.page.getPage();" onblur="BUSINESS.page.getOnblurPage();"/>&nbsp;of&nbsp; 
								<span id="__totalPageNo_"></span>
								<img style="cursor: pointer;" src="include/images/button_next.gif" title="Next page"
									onclick="BUSINESS.page.getNextPage();">
								<img style="cursor: pointer;" src="include/images/button_next_last.gif" title="Last page"
									onclick="BUSINESS.page.getLastPage();">&nbsp;&nbsp;
								<span class="select1">
									<select id="_recPerPageSelect_" onchange="BUSINESS.service.selectSearch();">
										<option value="10">10</option>	<option value="20">20</option>
										<option value="30">30</option>	<option value="40">40</option>
										<option value="50">50</option>	<option value="100">100</option>
									</select>
								</span>
								
								<!-- The following input elements are button type, they are used to download the 
									invoice records table as EXCEL file. For different section types have 
									different 'id' attribute, but the value of 'class' attribute are all the same. -->
								<input id='workspace-MissingInvoices-download' class='workspace-download' type="button"
									onclick='tems.actions.downloadInvoiceRecordsTable.missingInvoicesDownload()' value='Download to Excel'/>
								<!-- add by donghao.guo download to excel-->
								<input id='workspace-PreloadInvoices-download' class='workspace-download' type="button"
									onclick='tems.actions.downloadInvoiceRecordsTable.preloadInvoicesDownload()' value='Download to Excel'/>
							 
								<input id='workspace-UnknownInvoices-download' class='workspace-download' type="button"
									onclick='tems.actions.downloadInvoiceRecordsTable.unknownInvoicesDownload()' value='Download to Excel'/>
									
								<input id='workspace-Invoice-download' class='workspace-download' type="button"
									onclick='tems.actions.downloadInvoiceRecordsTable.dueDaysInvoicesDownload()' value='Download to Excel'/>
								
								<!-- External Approve -->
								<input id='workspace-ExternalApproveInvoices-download' class='workspace-download' type="button"
									onclick='tems.actions.downloadInvoiceRecordsTable.downloadExternalApproveInvoices()' value='Download to Excel'/>
									
								<input id='forward-DisputesBucket-download' class='workspace-download' type="button"
									onclick='tems.actions.downloadInvoiceRecordsTable.forwardSelectedDisputeDownload()' value='Download to Excel'/>
									
								<input id='forward-DisputesBucket-dispute' class='workspace-download' type="button"
									onclick='tems.actions.downloadInvoiceRecordsTable.forwardSelectedDispute()' value='Forward Selected Dispute(s)'/>
								<input id='forward-DisputesBucket-bulkActionRequest' class='workspace-download' type="button"
									onclick='tems.actions.downloadInvoiceRecordsTable.createBulkActionRequestDispute()' value='Create Bulk Action Request'/>
							</td>
						</tr>
					</tbody>
				</table>
			</td>
		</tr>
	</table>
</div>

	<!-- Add by Xin Huang on 2011-08-30 Start-->
	<form id="downloadForm_originalFile" action="download.action" method="post" target="__downloadIframe"
		style="display: none;">
		<input type="hidden" name="filePath" />
		<input type="hidden" name="fileName" />
	</form>
	
	<div class="edit-notes-window" id="editNotesWindow">
		<div class="hd">
			<h2>Edit Notes</h2>
		</div>
		<div class="bd">
			<s:form id="form_edit_notes" action="" theme="simple">
				<table height="100%" border="0" width="95%" align="center">
					<tr>
						<td valign="top" >
							Notes:
						</td>
						<td>
							<s:textarea  id="__invoice_notes"  cssStyle="padding:0px 1px;overflow:auto ;width:260px;height:100px;" cssClass="validate-text"></s:textarea>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
	</div>
	
	<div class="yui-pe-content" id="removeMissingInvoice" style="display: none;">
		<div class="hd">
			<h2>Remove Missing Invoice</h2>
		</div>
		<div class="bd">
			<table width="95%" border=0 align="center">
				<tr>
					<td style="padding-left: 10px" width="20%">
						Notes
					</td>
					<td style="padding-left: 10px">
						<s:textarea id="missingInvoiceNotes" cols="50" rows="5" style="margin: 0px; width: 100%; height: 84px;"></s:textarea>
						<input type="hidden" id="removeMissingInvoiceId">
					</td>
				</tr>
				
			</table>
		</div>
		<div class="bd">
		     <div style="padding-left :11px">
		                Missing Invoice and E-mail generate flag
						<s:select id="missingEmailflag" list="#{'Y':'Y','N':'N'}" listKey="key" listValue="value"></s:select>
			 </div>
		</div>
	</div>
	
	<div id="bulkActionRequestDisputeWindow" style="display: none;">
	<div class="hd" id = "addTitle">Add Action Request</div>
	<div class="bd" id="reg">
		<form id="handleBulkActionRequestForm" action="saveDisputeActionRequest.action" enctype="multipart/form-data" method="POST" target="uploadFrame_0">
			<table width="95%" align="center" style="margin: auto;" cellspacing="5">
				<tr>
					<td colspan="2">
						<input name="disputeId" type="hidden"  id="disputeNoteByDisputeIdId"/>
					</td>
				</tr>		
				<tr>
					<td style="vertical-align: text-top;" width='20%'>Notes:</td>
					<td width='80%'>
						<div>
							<div style="float:left;width:95%;">
								<textarea id="note_Notes" name = "actionRequestNotes"
									style="overflow:auto ; width:100%; height:60px;max-width:380px;resize: none;"
									class="validate-text" maxlength="500">
								</textarea>
							</div>
							<div  style="font-size: 15px; margin-left: 5px; color: red; float:left; ">*</div>
							<div class="clear"></div>
						</div>
					</td>
				</tr>
				<tr>
				<td></td>
				<td>
				<div style="text-align:right; padding:0 15px 0 0 ;margin-right:15px">
				<strong id="num_characters">500</strong>
				<strong> characters left</strong>
				</div>
				</td>
				</tr>			    
				<tr>
				<td></td>
					<td>
						
						<div style="float:right;padding-right: 15px;">
<%--							<input style="text-align:center; padding:1px 4px;border-color: #808080;" type="button" value="Save" onclick="saveBulkActionRequestDisputeNotes()"/>--%>
							<input style="text-align:center; padding:1px 4px;border-color: #808080;" type="button" value="Pending" onclick="saveBulkActionRequestDisputeNotes('2')"/>
							<input style="text-align:center; padding:1px 4px;border-color: #808080;" type="button" value="Complete" onclick="saveBulkActionRequestDisputeNotes('3')"/>
							<input style="text-align:center; padding:1px 4px;border-color: #808080;" type="button" value="Clear" onclick="clearBulkActionRequestDisputeNotes()"/>
							<input style="text-align:center; padding:1px 4px;border-color: #808080;" type="button" value="Cancel" onclick="YAHOO.ccm.container.bulkActionRequestDisputeWindow.hide();"/>
						</div>
						
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
	
	<div class="yui-pe-content" id="removeUnknownInvoice" style="display: none;">
		<div class="hd">
			<h2>Remove Unknown Invoice</h2>
		</div>
		<div class="bd">
			<table width="95%" border=0 align="center">
				<tr>
					<td style="padding-left: 10px" width="20%">
						Notes
					</td>
					<td style="padding-left: 10px">
						<s:textarea id="unknownInvoiceNotes" cols="50" rows="5"></s:textarea>
						<input type="hidden" id="removeUnknownInvoiceId">
					</td>
				</tr>
			</table>
		</div>
	</div>

</div>

<iframe id="__downloadIframe" style="display: none;"></iframe>