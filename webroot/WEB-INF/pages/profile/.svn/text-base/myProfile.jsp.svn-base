<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<link type="text/css" rel="stylesheet" href="include/javascript/Calendar/styles/calendar.css" />
<script type="text/javascript" src="include/javascript/Calendar/simplecalendar.js"></script>
<script type="text/javascript" src="include/javascript/yui2/uploader-min.js"></script>
<script type="text/javascript" src="include/javascript/ccm/quicklink.js"></script>
<script type="text/javascript" src="include/javascript/ccm/profile/profile.js"></script>
<div id="pageContainer" class="tabForm">
	<h3>
		My Profile
	</h3>
	<table width="100%" cellspacing="0" cellpadding="0" border="0" style="border-top: 0px none; margin-bottom: 4px" >
		<tr>
			<td>
				<div id="ctl00_MainContent_SearchInvoiceControl1_UpdatePanel1">
					<table class="tabDetailView" border="0" width=100% cellpadding="5" cellspacing="0">
						<tr valign="top" id="changePasswordContentDiv">
							<td class="tabDetailViewDL" width="90%">
								<div class="searchItemPanel">
									<s:form id="form0" action="doPwdChange.action" onsubmit="return validataForm0();" theme="simple" target="uploadFrame">
										<table width="100%" cellspacing="2" cellpadding="0" border="0">
											<tr>
												<td width="20%" align="left" style="padding-right:15px;">
													<b>Change Your Password</b>
												</td>
												<td>
													&nbsp;
												</td>
											</tr>
											<tr>
												<td width="20%" align="left" style="padding-right:15px;" colspan="2">
													(To create your password, enter at least 8 characters with 4 unique characters, at least 2 numbers and 2 symbols without space)
												</td>
											</tr>
											<tr>
												<td align="left" style="padding-right:15px;">
													Old Password
												</td>
												<td>
													<s:password onfocus="document.getElementById('showChangePwdMassageDiv').innerHTML = '';" name="oldPassword" cssClass="required" value=""></s:password>
													
												</td>
											</tr>
											<tr>
												<td align="left" style="padding-right:15px;">
													New Password
												</td>
												<td>
													<s:password onfocus="document.getElementById('showChangePwdMassageDiv').innerHTML = '';" name="newPassword" cssClass="required" value=""></s:password>
													
												</td>
											</tr>
											<tr>
												<td align="left" style="padding-right:15px;">
													Confirm New Password
												</td>
												<td>
													<s:password onfocus="document.getElementById('showChangePwdMassageDiv').innerHTML = '';" name="confirmPassword" cssClass="required" value=""></s:password>
													
												</td>
											</tr>
											<tr>
												<td>
													
												</td>
												<td  style="padding-right:15px;padding-top:2px;">
													<s:submit value="Save"></s:submit>
													<input type="button" onclick="document.getElementById('form0_oldPassword').value='';
													document.getElementById('form0_newPassword').value='';
													document.getElementById('form0_confirmPassword').value='';" value="Cancel"/>
												</td>
											</tr>
											<tr>
												<td id="showChangePwdErrorDiv" colspan="2" style="padding-left: 180px;">
												</td> 
											</tr>
											<tr>
												<td id="showChangePwdMassageDiv" colspan="2" style="padding-left: 180px;">
												
												</td> 
											</tr>
										</table>
									</s:form>
								</div>
							</td>
						</tr>
						
						<tr valign="top">
							<td class="tabDetailViewDL" width="90%">
								<div class="searchItemPanel">
										<table width="100%" cellspacing="5" cellpadding="0" border="0">
											<tr>
												<td width="30%" align="right" style="padding-right:15px;">
													<b>Welcome Page Management</b>
												</td>
												<td> &nbsp; </td>
											</tr>
											<tr>
												<td colspan="2">
													<table width="100%" cellspacing="5" cellpadding="0" border="0">
														<tr>
															<td width="100%">
																<table width="100%" cellspacing="0" cellpadding="0" border="0">
																	<tr id="user_pictures_tr">
																		
																	</tr>
																	<tr id="user_pictures_tr_h">
																		
																	</tr>
																</table>
															</td>
														</tr>
														<tr id="uploadImg_tr_1">
															<td>
																Add New Login Picture
															</td>
														</tr>
														<tr id="uploadImg_tr_2">
															<td>
																<s:form action="toUploadLoginPicture.action" target="uploadFrame" onsubmit="showLoadingModalLayer();" id="form2"
																style="position:relative;" enctype="multipart/form-data" method="POST" theme="simple">
																	<div style="padding:3px 0 0 0;height:19px;">
																	<div style="float:left;">
																	<input style="height:19px;width:240px;" type='text' id="__up_load_text_myprofile" value="" disabled="disabled">
		        													</div>
       																<div style="float:left; margin:0 0 0 2px">
		        													<span class="ccm_upload_img">
			        													<input  onchange="document.getElementById('__up_load_text_myprofile').value=this.value;" class="ccm_file" type="file" name="fileData" size="1" id = "up_load_text_myprofile_clear" />
			        													<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
		        													</span>
		        													</div>
       																</div>
																	<!--<input type="file" style="height:19px;" name="fileData" size="80" />
																	<input type="text" size="80" disabled="disabled" onclick="YAHOO.util.Dom.get('f___').click()"/>
																	<input type="button" value="Browse" onclick="adfadf()"/>
																	-->
																	<s:submit style="height:19px;position:absolute;left:318px;top:4px;" value="Upload"></s:submit>
																</s:form>
															</td>
														</tr>
														<tr id="uploadImg_tr_3" style="display: none;">
															<td>
																<b>
																<font color="#00CC99">You have already uploaded a maximum of 5 images to your profile.If you want to upload a new one, please delete an existing image first.</font>
																</b> 
															</td>
														</tr>
														<tr>
															<td id="showUploadErrorDiv">
															</td>
														</tr>
													</table>
												</td> 
											</tr>
										</table>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
</div>
<iframe name="uploadFrame" id="uploadFrame" style="display:none" src="javascript:false"></iframe>

