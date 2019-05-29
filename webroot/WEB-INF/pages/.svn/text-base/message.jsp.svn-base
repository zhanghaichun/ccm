<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.saninco.ccm.util.SystemUtil"%>
<script type="text/javascript">
			var isFreshed = false;
			var editChannelMemberOpen = false;
			var m_invoiceId, m_banId, m_invoiceNummber, m_accountNumber,
			m_disputeId, m_disputeNumber, m_circuitId, m_circuitNumber,
			m_userId, m_userNumber, m_vendorId, m_vendorName;
			var referencePage, referenceTypeValue, referenceArr;
			var notePage,noteArr;
			var fromHtml = "";
			var timeDifference;
			
		//	var oprHtml = "<div class=\"rmark\" style=\"z-index:999\"><span class=\"mo molink\" title=\"Private Note Search\" onclick=\"notePrev()\"></span><span class=\"ml mllink\" title=\"Search with a Known Invoice from Bottom Link\" onclick=\"prev()\"></span><span class=\"mr mrlink\" title=\"Search with a Known Invoice from Top Link\" onclick=\"next()\"></span></div>";
			var oprHtml = "<div class=\"rmark\" style=\"z-index:999; \"><div class=\"rightbut\"><span class=\"mo molink\" title=\"Private note search\" onclick=\"notePrev()\"></span><span class=\"ml mllink\" title=\"Search with a known invoice from bottom link\" onclick=\"prev()\"></span><span class=\"mr mrlink\" title=\"Search with a known invoice from top link\" onclick=\"next()\"></span></div><div class=\"leftbut\"><div class=\"uploadimg\"><div class=\"text\">Attach files</div><input class=\"upload\" type=\"file\" multiple=\"multiple\" onchange=\"doUploadMessageFiles(this.files)\" name=\"messageFiles\"></div><div onclick=\"showFileTips(this)\" class=\"uploadnum\">0</div></div></div>";
			var fileTip = '<div class="numtips" style="background:#ffffff;display:none;"><div class="strids"></div><div id="messageFileList"></div></div>';
			oprHtml+= fileTip;
			jQuery(function(){
				jQuery.get(window.location.protocol+"//" + window.location.host + "/ccmApp/message.do?method=findUnreadMessageCount",function(data){
					timeDifference = new Date().getTime() - data.serverTimestamp;
					if (data.count > 0) {
						jQuery("#haveMessageTray").show();
						jQuery("#noMessageTray").hide();
					} else {
						jQuery("#noMessageTray").show();
						jQuery("#haveMessageTray").hide();
					}
				});

				jQuery(".titgro").click(function(){
					if (jQuery(this).hasClass("do2")) {
						jQuery(this).removeClass("do2").addClass("do3");
						jQuery(this).next().hide();
					} else {
						jQuery(this).removeClass("do3").addClass("do2");
						jQuery(this).next().show();
					}
				});
				
				jQuery(".zktop1 li").click(function(){
					if (jQuery(this).hasClass("ali1")) {
						jQuery("#panelList1").show();
						jQuery("#panelList2").hide();
						jQuery(".lfsearch2").hide();
						jQuery("#panelList3").hide();
						jQuery("#hiddenGroupPanel").hide();
						jQuery("#panelList4").hide();
					} else if (jQuery(this).hasClass("ali2")) {
						jQuery("#panelList1").hide();
						jQuery("#panelList2").show();
						jQuery(".lfsearch2").show();
						jQuery("#panelList3").hide();
						jQuery("#hiddenGroupPanel").hide();
						jQuery("#panelList4").hide();
					} else {
						jQuery("#panelList1").hide();
						jQuery("#panelList2").hide();
						jQuery(".lfsearch2").hide();
						jQuery("#panelList3").show();
						jQuery("#hiddenGroupPanel").show();
					/*	if (!editChannelMemberOpen) {
							jQuery("#panelList3").show();
						} else {
							jQuery("#panelList4").show();
						}*/
					}
					jQuery(this).removeClass("off1").addClass("on1");
					jQuery(this).siblings().removeClass("on1").addClass("off1");
				});

				jQuery(".showk").scroll(function() {
				    if (jQuery(".showk").scrollTop() == 0) {
				    	var channelTypeId = jQuery("#channelTypeId").val();
						var userId = jQuery("#userId").val();
						var channelId = jQuery("#channelId").val();
						var lastId = jQuery("#lastId").val();
						if (lastId != 0) {
			                if (channelTypeId == 1) {
						    	loadChatRecord(userId,true,lastId,"");
			                } else {
						    	loadChatRecord(channelId,false,lastId,"");
			                }
						}
				    }
				    
				//    jQuery(".rmark").css("top",jQuery(".showk").scrollTop() + jQuery(".showk").height() - jQuery(".rmark").height());
				//    jQuery(".numtips").css("top",jQuery(".showk").scrollTop() + jQuery(".showk").height() - jQuery(".numtips").height()-30);
				    
				});

				// 显示Reference
				m_invoiceId = jQuery("#invoiceNoteInvoiceIdByinvoice").val();
				// invoice
				if (m_invoiceId) {
					m_invoiceNummber = '${invoice.invoiceNumber}';
					m_banId = jQuery("#invoiceNoteBanIdByinvoice").val();
					jQuery("#linkToInvoice").show();
					jQuery("#linkToBan").show();
					m_accountNumber = '${invoice.ban.accountNumber}';
				} else {
					// ban
					var bid = '${b.id}';
					if (bid) {
						m_banId = bid;
						m_accountNumber = '${b.accountNumber}';
						jQuery("#linkToInvoice").show();
						jQuery("#linkToInvoiceLabel").text("Link to this ban");
					} else {
						// dispute
						m_disputeId = '${dispute.id}';
						if (m_disputeId) {
							m_disputeNumber = '${dispute.disputeNumber}';
							jQuery("#linkToInvoice").show();
							jQuery("#linkToInvoiceLabel").text("Link to this dispute");
						} else {
							// circuit
							m_circuitId = '${vendorCircuit.id}';
							if (m_circuitId) {
								m_circuitNumber = '${vendorCircuit.circuitNumber}';
								jQuery("#linkToInvoice").show();
								jQuery("#linkToInvoiceLabel").text("Link to this circuit");
							}
						}
					}
				}

				
				});

			function chatShow(item) {
				closeGroupMember();
				jQuery("#modifyMember").hide();
				jQuery("#msgText").val("");
				jQuery(".showk").html(oprHtml);
				jQuery("#lastId").val(0);
				if (jQuery(item).hasClass("groups") || jQuery(item).hasClass("redgroup")) {
					jQuery("#channelId").val(jQuery(item).get(0).id);
					jQuery("#channelTypeId").val(2);
					loadChatRecord(jQuery(item).get(0).id,false,0,"");

					showGroupMember();
					// 更新组未读消息
					if (jQuery(item).hasClass("redgroup")) {
						jQuery.get(window.location.protocol+"//" + window.location.host + "/ccmApp/message.do?method=updateGroupUnreadStatus&channelId=" + jQuery(item).get(0).id,function(){
							
						});
					}
					jQuery(".name2").removeClass("chat_icon_single").addClass("chat_icon_group");
					if (jQuery(item).hasClass("redgroup")){
						jQuery(item).removeClass("redgroup").addClass("groups");
					}
					jQuery(".anniur").show();
					jQuery(".oknames").hide();
					jQuery(".name2").show();
					jQuery(".friendClose").hide();
					
				} else {
					jQuery("#userId").val(jQuery(item).get(0).id);
					jQuery("#channelTypeId").val(1);
					loadChatRecord(jQuery(item).get(0).id,true,0,"");
					// 更新好友未读消息
					if (jQuery(item).hasClass("redper")) {
						jQuery.get(window.location.protocol+"//" + window.location.host + "/ccmApp/message.do?method=updateFriendUnreadStatus&toUserId=" + jQuery(item).get(0).id,function(){
							
						});
					}
					jQuery(item).removeClass("redper");
					jQuery(".name2").removeClass("chat_icon_group").addClass("chat_icon_single");
					jQuery(".anniur").hide();
					jQuery(".oknames").hide();
					jQuery(".name2").show();
					jQuery(".friendClose").show();
				}
				jQuery(".name2").text(jQuery(item).text());
				jQuery("#chatPanel").show();
				jQuery("#msgText").focus();
				
			}

			function openChannel(channelId,channelName,channelTypeId,userId) {
				var display =jQuery(".zhankai001").css('display');
				if(display == 'none'){
					if (!jQuery("#noMessageTray").is("hidden")) {
						showMessagePanel(false)
					} else {
						showMessagePanel(true);
					}
				}
				jQuery(".showk").html(oprHtml);
				jQuery("#lastId").val(0);
				if (channelTypeId == 1) {
					jQuery("#userId").val(userId);
					jQuery("#channelTypeId").val(1);
					loadChatRecord(userId,true,0,"");
					// 更新好友未读消息
					jQuery.get(window.location.protocol+"//" + window.location.host + "/ccmApp/message.do?method=updateFriendUnreadStatus&toUserId=" + userId,function(){
						
					});
					jQuery(".name2").removeClass("chat_icon_group").addClass("chat_icon_single");
					jQuery(".anniur").hide();
					jQuery(".oknames").hide();
					jQuery(".name2").show();
					jQuery(".friendClose").show();
				} else {
					jQuery("#channelId").val(channelId);
					jQuery("#channelTypeId").val(2);
					loadChatRecord(channelId,false,0,"");
					// 更新组未读消息
					jQuery.get(window.location.protocol+"//" + window.location.host + "/ccmApp/message.do?method=updateGroupUnreadStatus&channelId=" + channelId,function(){
						
					});
					jQuery(".name2").removeClass("chat_icon_single").addClass("chat_icon_group");

					jQuery(".anniur").show();
					jQuery(".oknames").hide();
					jQuery(".name2").show();
					jQuery(".friendClose").hide();
				}
				jQuery(".name2").text(channelName);
				jQuery("#chatPanel").show();
				jQuery("#msgText").focus();
			}

			var lastHeight = 0;
			// 查询聊天记录
			function loadChatRecord(id, single, lastId, scrollToReferenceId) {
				var count = 10, maxLastId="";
				if (lastId != 0 && lastId != jQuery("#lastId").val()) {
					maxLastId = jQuery("#lastId").val();
				}
				var referenceId = "", referenceTypeId = "";
				if (m_vendorId) {
					referenceId = m_vendorId;
					referenceTypeId = 4;
					referenceTypeValue = "Vendor";
				} else if (m_invoiceId) {
					referenceId = m_invoiceId;
					referenceTypeId = 1;
					referenceTypeValue = "Invoice";
				} else if (m_banId) {
					referenceId = m_banId;
					referenceTypeId = 2;
					referenceTypeValue = "BAN";
				} else if (m_disputeId) {
					referenceId = m_disputeId;
					referenceTypeId = 3;
					referenceTypeValue = "Dispute";
				} else if (m_circuitId) {
					referenceId = m_circuitId;
					referenceTypeId = 5;
					referenceTypeValue = "Circuit";
				} else if (m_userId) {
					referenceId = m_userId;
					referenceTypeId = 6;
					referenceTypeValue = "User";
				}
				var isReference = false;
				if (single) {
					jQuery.get(window.location.protocol+"//" + window.location.host + "/ccmApp/message.do?method=queryFriendsMessageListWeb&toUserId=" + id + "&count=" + count + "&lastId=" + lastId + "&referenceId=" + referenceId + "&referenceTypeId=" + referenceTypeId + "&maxLastId=" + maxLastId,function(data){
						var object = jQuery.extend(true,[],data) ;
						if (data != null && data.length > 0) {
							var listHtml = "";
							var theLast = 0;
							if (data[data.length-1].messageId) {
								theLast = data[data.length-1].messageId;
							} else if (data[data.length-2].messageId) {
								theLast = data[data.length-2].messageId;
							} else if (data[data.length-3].messageId) {
								theLast = data[data.length-3].messageId;
							}
							data = data.sort(compare("createdTimestamp"));
							var referenceKey = "";
							var isReference = false;
							var lastMsg = "";
							for (var i=0;i<data.length;i++) {
								var message = data[i];
								if (message.referencePage) {
									isReference = true;
									referencePage = message.referencePage;
									referenceArr = new Array();
									var j=0;
									for (var prop in referencePage) {
										  var map = new Map(); 
										  map.put(prop,referencePage[prop]);
										  referenceArr[j] = map;
										  j++;
									}
								} else if (message.notePage) {
									notePage = message.notePage;
									noteArr = new Array();
									var k=0;
									for (var prop in notePage) {
										  var map = new Map(); 
										  map.put(prop,notePage[prop]);
										  noteArr[k] = map;
										  k++;
									}
								} else {
								var messageId = message.messageId;
								var channelId = message.channelId;
								var msgText = message.msgText;
								var privateFlag = message.privateFlag;
								var referenceTypeId = message.referenceTypeId;
								var referenceId = message.referenceId;
								var referenceNumber = message.referenceNumber;
								var createdTimestamp = message.createdTimestamp;
								var displayTimestamp = message.displayTimestamp;
								var ownerFlag = message.ownerFlag;
								var fromUserName = message.fromUserName;
								var attachmentFile = message.attachmentFile;

								var msgItemHtml = "";
								var dateHtml = "";
								if (!isReference) {
									dateHtml = "<div class=\"time2\">" + new Date(displayTimestamp).Format("MM-dd hh:mm") + "</div>";
								}
								msgItemHtml+= dateHtml;
								if (referenceTypeId) {
									var type = "Invoice", url = "#";
									if (referenceTypeId == 1) {
										type = "Invoice";
										url = "<%=request.getContextPath() %>" + "/viewInvoiceDetails.action?invoiceId=" + referenceId + "#NAME_invoiceNote";
									}
									else if (referenceTypeId == 2) {
										type = "BAN";
										url = "<%=request.getContextPath() %>" + "/banDetailAction.action?bvo.banId=" + referenceId + "#NAME_tiffPage";
									}
									else if (referenceTypeId == 3) {
										type = "Dispute";
										url = "<%=request.getContextPath() %>" + "/viewDisputeDetails.action?disputeId=" + referenceId + "#NAME_TransactionHistoryListPage";
									}
									else if (referenceTypeId == 4) {
										type = "Vendor";
										
									}
									else if (referenceTypeId == 5) {
										type = "Circuit";
										url = "<%=request.getContextPath() %>" + "/showCircuitDetail.action?vendorCircuitId=" + referenceId + "#NAME_scoaPage";
									}
									else if (referenceTypeId == 6) {
										type = "User";
										url = "<%=request.getContextPath() %>" + "/securityManagement.action?userId=" + referenceId + "#NAME_previledgeVendorBanPage";
									}
									var toValue = referenceNumber;
									if (ownerFlag == "Y") {
										if (privateFlag == "Y"){
											var privateHtml = "";
											if (!isReference) {
											  privateHtml = "<div class=\"gray2 ty2\"> <span class=\"gatop2\" id=\"note" + messageId + "\" style=\"border-bottom: 0px;\">" + msgText.replace(/ /g, "&ensp;").replace(/\</g,"&lt;") + "</span>";
											}
											privateHtml+= "<span id=\"" + type + messageId + "\" class=\"blbot2\" style=\"color:#b3b3b3;border-top: 1px solid #e6e6e6\">" + type + ":<a style=\"color:#b3b3b3;\" href=\"javascript:toHrefPage(" + referenceTypeId + "," + referenceId + ")\">" + toValue + "</a></span> ";
											if (message.referenceKey && !isReference) {} else {
												if (attachmentFile) {
													for (var f=0;f<attachmentFile.length;f++) {
														var file = attachmentFile[f];
														var fileName = file.fileName;
														var filePath = file.filePath;
														if (fileName && filePath) {
															privateHtml+='<span class="gryfiles"><a target="_blank" href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#b3b3b3">'+fileName+'</a></span>';
														}
													}
												}
												privateHtml+= "<span class=\"gabot2\" style=\"border-top: 1px solid #e6e6e6\">Remarked message, only read by myself.</span> </div>";
											}
											msgItemHtml+= privateHtml;
										} else {
											var toHtml = "";
											if (!isReference) {
											  toHtml = "<div class=\"blue2 ty2\"> <span class=\"bltop2\" style=\"word-wrap : break-word ;\">" + msgText.replace(/ /g, "&ensp;").replace(/\</g,"&lt;") + "</span>";
											}
											toHtml+= "<span id=\"" + type + messageId + "\" class=\"blbot2\">" + type + ":<a href=\"javascript:toHrefPage(" + referenceTypeId + "," + referenceId + ")\">" + toValue + "</a></span> ";
											if (message.referenceKey && !isReference) {} else {
												if (attachmentFile) {
													for (var f=0;f<attachmentFile.length;f++) {
														var file = attachmentFile[f];
														var fileName = file.fileName;
														var filePath = file.filePath;
														if (fileName && filePath) {
															toHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#ffffff">'+fileName+'</a></span>';
														}
													}
												}
												toHtml+= "</div>";
											}
											msgItemHtml+= toHtml;
										}
									} else {
										var fromUser = fromUserName;
									    var fromValue = msgText;
									    var fromHtml = "";
									    if (!isReference) {
									    	fromHtml = "<div class=\"green2 ty2\"> <span class=\"grtop2\">" + fromUser + "</span> <span class=\"grbot2\">" + fromValue + "</span>";
									    }
										fromHtml+= "<span id=\"" + type + messageId + "\" class=\"blbot2\" style=\"color:red;border-top:1px solid #e6e6e6\">" + type + ":<a style=\"color:red;\" href=\"javascript:toHrefPage(" + referenceTypeId + "," + referenceId + ")\">" + toValue + "</a></span> ";
										if (message.referenceKey && !isReference) {} else {
											if (attachmentFile) {
												for (var f=0;f<attachmentFile.length;f++) {
													var file = attachmentFile[f];
													var fileName = file.fileName;
													var filePath = file.filePath;
													if (fileName && filePath) {
														fromHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#333">'+fileName+'</a></span>';
													}
												}
											}
											fromHtml+= "</div>";
										}
										msgItemHtml+= fromHtml;
									}
								} else
								if (ownerFlag == "Y") {
									if (privateFlag == "Y"){
										var privateHtml = "<div class=\"gray2 ty2\"> <span class=\"gatop2\" id=\"note" + messageId + "\">" + msgText.replace(/ /g, "&ensp;").replace(/\</g,"&lt;") + "</span> ";
										if (attachmentFile) {
											for (var f=0;f<attachmentFile.length;f++) {
												var file = attachmentFile[f];
												var fileName = file.fileName;
												var filePath = file.filePath;
												if (fileName && filePath) {
													privateHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#b3b3b3">'+fileName+'</a></span>';
												}
											}
										}
										privateHtml+= "<span class=\"gabot2\">Remarked message, only read by myself.</span></div>";
										msgItemHtml+= privateHtml;
									} else {
										var toHtml = "<div class=\"blue2 ty2\"> <span class=\"bltop2\" style=\"word-wrap : break-word ;\">" + msgText.replace(/ /g, "&ensp;").replace(/\</g,"&lt;") + "</span>";
										if (attachmentFile) {
											for (var f=0;f<attachmentFile.length;f++) {
												var file = attachmentFile[f];
												var fileName = file.fileName;
												var filePath = file.filePath;
												if (fileName && filePath) {
													toHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#ffffff">'+fileName+'</a></span>';
												}
											}
										}
										toHtml+= "</div>";
										msgItemHtml+= toHtml;
									}
								} else {
									var fromUser = fromUserName;
								    var fromValue = msgText;
									var fromHtml = "<div class=\"green2 ty2\"> <span class=\"grtop2\">" + fromUser + "</span> <span class=\"grbot2\">" + fromValue + "</span> ";
									if (attachmentFile) {
										for (var f=0;f<attachmentFile.length;f++) {
											var file = attachmentFile[f];
											var fileName = file.fileName;
											var filePath = file.filePath;
											if (fileName && filePath) {
												fromHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#333">'+fileName+'</a></span>';
											}
										}
									}
									fromHtml+= "</div>";
									msgItemHtml+= fromHtml;
								}

								
								if (message.referenceKey) {
									if (referenceKey == message.referenceKey) {
										listHtml+= lastMsg + msgItemHtml;
										isReference = false;
									} else {
										referenceKey = message.referenceKey;
										lastMsg = msgItemHtml;
										isReference = true;
									}
								} else {
									listHtml+= msgItemHtml;
									isReference = false;
								}
							}
							    
							}
							jQuery(".showk").prepend(listHtml);
							var div = jQuery(".showk").get(0);
							if (lastId == 0) {
								
								div.scrollTop = div.scrollHeight;
							} else {
								div.scrollTop = div.scrollHeight - lastHeight;
							} 
							lastHeight = div.scrollHeight;
						//	jQuery(".rmark").css("top",div.scrollHeight);
						//	jQuery(".numtips").css("top",div.scrollHeight-60);
							if (scrollToReferenceId) {
								var container = jQuery('.showk'); 
								var scrollTo = jQuery('#' + scrollToReferenceId); 
								if (scrollTo.offset())
								container.animate({scrollTop: scrollTo.offset().top - container.offset().top + container.scrollTop() - container.height()/3},1000);
							}
							jQuery("#lastId").val(theLast);
							if (isReference) {
								jQuery(".rmark").show();
							} else {
								jQuery(".mllink").hide();
								jQuery(".mrlink").hide();
								jQuery(".molink").css("float","right");
						//		jQuery(".rmark").hide();
							}
						} else {
							if (lastId == 0) {
					//			jQuery(".rmark").hide();
							}
						}
					});
				} else {
					jQuery.get(window.location.protocol+"//" + window.location.host + "/ccmApp/message.do?method=queryGroupMessageListByChannelWeb&channelId=" + id + "&count=" + count + "&lastId=" + lastId + "&referenceId=" + referenceId + "&referenceTypeId=" + referenceTypeId + "&maxLastId=" + maxLastId,function(data){
						var object = data;
						if (data != null && data.length > 0) {
							var listHtml = "";
							var theLast = 0;
							if (data[data.length-1].messageId) {
								theLast = data[data.length-1].messageId;
							} else if (data[data.length-2].messageId) {
								theLast = data[data.length-2].messageId;
							} else if (data[data.length-3].messageId) {
								theLast = data[data.length-3].messageId;
							}
							data = data.sort(compare("createdTimestamp"));
							var referenceKey = "";
							var isReference = false;
							var lastMsg = "";
							for (var i=0;i<data.length;i++) {
								var message = data[i];
								if (message.referencePage) {
									isReference = true;
									referencePage = message.referencePage;
									referenceArr = new Array();
									var j=0;
									for (var prop in referencePage) {
										  var map = new Map(); 
										  map.put(prop,referencePage[prop]);
										  referenceArr[j] = map;
										  j++;
									}
								} else if (message.notePage) {
									notePage = message.notePage;
									noteArr = new Array();
									var k=0;
									for (var prop in notePage) {
										  var map = new Map(); 
										  map.put(prop,notePage[prop]);
										  noteArr[k] = map;
										  k++;
									}
								} else {
								var messageId = message.messageId;
								var channelId = message.channelId;
								var msgText = message.msgText;
								var privateFlag = message.privateFlag;
								var referenceTypeId = message.referenceTypeId;
								var referenceId = message.referenceId;
								var referenceNumber = message.referenceNumber;
								var createdTimestamp = message.createdTimestamp;
								var ownerFlag = message.ownerFlag;
								var fromUserName = message.fromUserName;
								var displayTimestamp = message.displayTimestamp;
								var attachmentFile = message.attachmentFile;

								var msgItemHtml = "";
								var dateHtml = "";
								if (!isReference) {
									dateHtml = "<div class=\"time2\">" + new Date(displayTimestamp).Format("MM-dd hh:mm") + "</div>";
								}
								msgItemHtml+= dateHtml;
								if (referenceTypeId) {
									var type = "Ivoice", url = "#";
									if (referenceTypeId == 1) {
										type = "Invoice";
										url = "<%=request.getContextPath() %>" + "/viewInvoiceDetails.action?invoiceId=" + referenceId + "#NAME_invoiceNote";
									}
									else if (referenceTypeId == 2) {
										type = "BAN";
										url = "<%=request.getContextPath() %>" + "/banDetailAction.action?bvo.banId=" + referenceId + "#NAME_tiffPage";
									}
									else if (referenceTypeId == 3) {
										type = "Dispute";
										url = "<%=request.getContextPath() %>" + "/viewDisputeDetails.action?disputeId=" + referenceId + "#NAME_TransactionHistoryListPage";
									}
									else if (referenceTypeId == 4) {
										type = "Vendor";
										
									}
									else if (referenceTypeId == 5) {
										type = "Circuit";
										url = "<%=request.getContextPath() %>" + "/showCircuitDetail.action?vendorCircuitId=" + referenceId + "#NAME_scoaPage";
									}
									else if (referenceTypeId == 6) {
										type = "User";
										url = "<%=request.getContextPath() %>" + "/securityManagement.action?userId=" + referenceId + "#NAME_previledgeVendorBanPage";
									}
									var toValue = referenceNumber;
									if (ownerFlag == "Y") {
										if (privateFlag == "Y"){
											var privateHtml = "";
											if (!isReference) {
											  privateHtml = "<div class=\"gray2 ty2\"> <span class=\"gatop2\" id=\"note" + messageId + "\" style=\"border-bottom: 0px;\">" + msgText.replace(/ /g, "&ensp;").replace(/\</g,"&lt;") + "</span>";
											}
											privateHtml+= "<span id=\"" + type + messageId + "\" class=\"blbot2\" style=\"color:#b3b3b3;border-top: 1px solid #e6e6e6\">" + type + ":<a style=\"color:#b3b3b3;\" href=\"javascript:toHrefPage(" + referenceTypeId + "," + referenceId + ")\">" + toValue + "</a></span> ";
											if (message.referenceKey && !isReference) {} else {
												if (attachmentFile) {
													for (var f=0;f<attachmentFile.length;f++) {
														var file = attachmentFile[f];
														var fileName = file.fileName;
														var filePath = file.filePath;
														if (fileName && filePath) {
															privateHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#b3b3b3">'+fileName+'</a></span>';
														}
													}
												}
												privateHtml+= "<span class=\"gabot2\" style=\"border-top: 1px solid #e6e6e6\">Remarked message, only read by myself.</span> </div>";
											}
											msgItemHtml+= privateHtml;
										} else {
											var toHtml = "";
											if (!isReference) {
											  toHtml = "<div class=\"blue2 ty2\"> <span class=\"bltop2\" style=\"word-wrap : break-word ;\">" + msgText.replace(/ /g, "&ensp;").replace(/\</g,"&lt;") + "</span>";
											}
											toHtml+= "<span id=\"" + type + messageId + "\" class=\"blbot2\">" + type + ":<a href=\"javascript:toHrefPage(" + referenceTypeId + "," + referenceId + ")\">" + toValue + "</a></span> ";
											if (message.referenceKey && !isReference) {} else {
												if (attachmentFile) {
													for (var f=0;f<attachmentFile.length;f++) {
														var file = attachmentFile[f];
														var fileName = file.fileName;
														var filePath = file.filePath;
														if (fileName && filePath) {
															toHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#ffffff">'+fileName+'</a></span>';
														}
													}
												}
												toHtml+= "</div>";
											}
											msgItemHtml+= toHtml;
										}
									} else {
											var fromUser = fromUserName;
										    var fromValue = msgText;
										    var fromHtml = "";
										    if (!isReference) {
											  fromHtml = "<div class=\"green2 ty2\"> <span class=\"grtop2\">" + fromUser + "</span> <span class=\"grbot2\">" + fromValue + "</span>";
										    }
											fromHtml+= "<span id=\"" + type + messageId + "\" class=\"blbot2\" style=\"color:red;border-top:1px solid #e6e6e6\">" + type + ":<a style=\"color:red;\" href=\"javascript:toHrefPage(" + referenceTypeId + "," + referenceId + ")\">" + toValue + "</a></span> ";
											if (message.referenceKey && !isReference) {} else {
												if (attachmentFile) {
													for (var f=0;f<attachmentFile.length;f++) {
														var file = attachmentFile[f];
														var fileName = file.fileName;
														var filePath = file.filePath;
														if (fileName && filePath) {
															fromHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#333">'+fileName+'</a></span>';
														}
													}
												}
												fromHtml+= "</div>";
											}
											msgItemHtml+= fromHtml;
									}
								} else
								if (ownerFlag == "Y") {
									if (privateFlag == "Y"){
										var privateHtml = "<div class=\"gray2 ty2\"> <span class=\"gatop2\" id=\"note" + messageId + "\">" + msgText.replace(/ /g, "&ensp;").replace(/\</g,"&lt;") + "</span> ";
										if (attachmentFile) {
											for (var f=0;f<attachmentFile.length;f++) {
												var file = attachmentFile[f];
												var fileName = file.fileName;
												var filePath = file.filePath;
												if (fileName && filePath) {
													privateHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#b3b3b3">'+fileName+'</a></span>';
												}
											}
										}
										privateHtml+= "<span class=\"gabot2\">Remarked message, only read by myself.</span></div>"; 
										msgItemHtml+= privateHtml;
									} else {
										var toHtml = "<div class=\"blue2 ty2\"> <span class=\"bltop2\" style=\"word-wrap : break-word ;\">" + msgText.replace(/ /g, "&ensp;").replace(/\</g,"&lt;") + "</span>";
										if (attachmentFile) {
											for (var f=0;f<attachmentFile.length;f++) {
												var file = attachmentFile[f];
												var fileName = file.fileName;
												var filePath = file.filePath;
												if (fileName && filePath) {
													toHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#ffffff">'+fileName+'</a></span>';
												}
											}
										}
										toHtml+= "</div>";
										msgItemHtml+= toHtml;
									}
								} else {
									var fromUser = fromUserName;
								    var fromValue = msgText;
									var fromHtml = "<div class=\"green2 ty2\"> <span class=\"grtop2\">" + fromUser + "</span> <span class=\"grbot2\">" + fromValue + "</span> ";
									if (attachmentFile) {
										for (var f=0;f<attachmentFile.length;f++) {
											var file = attachmentFile[f];
											var fileName = file.fileName;
											var filePath = file.filePath;
											if (fileName && filePath) {
												fromHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#333">'+fileName+'</a></span>';
											}
										}
									}
									fromHtml+= "</div>";
									msgItemHtml+= fromHtml;
								}
								if (message.referenceKey) {
									if (referenceKey == message.referenceKey) {
										listHtml+= lastMsg + msgItemHtml;
										isReference = false;
									} else {
										referenceKey = message.referenceKey;
										lastMsg = msgItemHtml;
										isReference = true;
									}
								} else {
									listHtml+= msgItemHtml;
									isReference = false;
								}
							}
							}
							jQuery(".showk").prepend(listHtml);
							var div = jQuery(".showk").get(0);
							if (lastId == 0) {
								div.scrollTop = div.scrollHeight;
							} else {
								div.scrollTop = div.scrollHeight - lastHeight;
							} 
							lastHeight = div.scrollHeight;
						//	jQuery(".rmark").css("top",div.scrollHeight);
						//	jQuery(".numtips").css("top",div.scrollHeight-60);
							if (scrollToReferenceId) {
								var container = jQuery('.showk'); 
								var scrollTo = jQuery('#' + scrollToReferenceId); 
								if (scrollTo.offset())
								container.animate({scrollTop: scrollTo.offset().top - container.offset().top + container.scrollTop() - container.height()/3},1000);
							}
							jQuery("#lastId").val(theLast);
							if (isReference) {
								jQuery(".rmark").show();
							} else {
					//			jQuery(".rmark").hide();
								jQuery(".mllink").hide();
								jQuery(".mrlink").hide();
								jQuery(".molink").css("float","right");
							}
						} else {
							if (lastId == 0) {
							//	jQuery(".rmark").hide();
							}
						}
						
					});
				}
			}

			function toHrefPage(referenceTypeId,referenceId) {
				var functionId = 0,url="";
				if (referenceTypeId == 1) {
					url = "<%=request.getContextPath() %>" + "/viewInvoiceDetails.action?invoiceId=" + referenceId + "#NAME_invoiceNote";
					functionId = 1000;
				}
				else if (referenceTypeId == 2) {
					url = "<%=request.getContextPath() %>" + "/banViewDetailAction.action?bvo.banId=" + referenceId + "#NAME_tiffPage";
					functionId = 4000;
				}
				else if (referenceTypeId == 3) {
					url = "<%=request.getContextPath() %>" + "/viewDisputeDetails.action?disputeId=" + referenceId + "#NAME_TransactionHistoryListPage";
					functionId = 2000;
				}
				else if (referenceTypeId == 4) {
					
				}
				else if (referenceTypeId == 5) {
					url = "<%=request.getContextPath() %>" + "/showCircuitDetail.action?vendorCircuitId=" + referenceId + "#NAME_scoaPage";
					functionId = 5000;
				}
				else if (referenceTypeId == 6) {
					url = "<%=request.getContextPath() %>" + "/securityManagement.action?userId=" + referenceId + "#NAME_previledgeVendorBanPage";
					functionId = 4000;
				}
				if (referenceTypeId !=2) {
				YAHOO.util.Connect.asyncRequest('POST' , "<%=request.getContextPath() %>" + "/verifyOperatePermission.action" , {
					success: function(o){
					var data = eval("("+o.responseText+")");
					if (!data.data) {
						alert("I'm sorry you do not have operation permissions!");
					} else {
						location.href = window.location.protocol+"//" + window.location.host + url;
					}
					
				}} , 
				"viewSecurityVO.userId="+"<%=SystemUtil.getCurrentUserId() %>" + "&viewSecurityVO.functionId=" + functionId); 
				} else {
					location.href = window.location.protocol+"//" + window.location.host + url;
				}
			}


			function showEditGroup() {
				jQuery("#editGroupName").val(jQuery(".name2").text());
				jQuery(".name2").hide();
				jQuery(".oknames").show();
			}

			function showAddDiscussionGroup() {
				jQuery("#channelId").val("")
				jQuery(".name22").text("Add Discussion Group");
				editChannelMemberOpen = true;
				jQuery("#channelId").val("");
			//	jQuery("#panelList4").show();
			//	jQuery("#panelList3").hide();
				jQuery("#modifyMember").show();
				jQuery("#currentCount").text(0);
				jQuery(".youcezu ul").html("");
				jQuery("#chatPanel").hide();
				jQuery("#groupMember").hide();
				jQuery(".zuocezu ul").html(jQuery(".zuocezu2 ul").html());
				jQuery(".sousuoinp").val("");
			}

			function hideModifyMember() {
				jQuery(".zuocezu ul").html(jQuery(".zuocezu2 ul").html());
				jQuery(".sousuoinp").val("");
				jQuery("#modifyMember").hide();
			}

			function showModifyGroupMember() {
				editChannelMemberOpen = true;
				jQuery(".name22").text("Edit Group Member");
				jQuery(".zuocezu ul").html(jQuery(".zuocezu2 ul").html());
				jQuery(".sousuoinp").val("");
				if (jQuery("#channelId").val()) {
					jQuery.get(window.location.protocol+"//" + window.location.host + "/ccmApp/message.do?method=queryUserListByGroupChannel&channelId=" + jQuery("#channelId").val(),function(data){
						var object = data;
						if (data.length > 0) {
							var modifyGroupMemberListHtml = "";
							var count = 0;
							for (var i=0;i<data.length;i++) {
								var user = data[i];
								var userId = user.userId;
								var username = user.username;
								var selectFlag = user.selectFlag;
								if (selectFlag == "Y") {
									count++;
									modifyGroupMemberListHtml+= "<li onmouseover=\"mouseOver(this)\" onmouseout=\"mouseOut(this)\" id=\"tm" + userId + "\">" + username + "<span class=\"chahaoss\" onclick=\"removeMember(this)\">X</span></li>";
								}
							}
							jQuery("#currentCount").text(count);
							jQuery(".youcezu ul").html(modifyGroupMemberListHtml);
						//	jQuery(".zktop1 li").removeClass("on1").addClass("off1");
						//	jQuery(".ali3").removeClass("off1").addClass("on1");
							jQuery("#modifyMember").show();
							jQuery("#chatPanel").hide();
							jQuery("#groupMember").hide();
					//		jQuery("#panelList2").hide();
					//		jQuery("#panelList1").hide();
						}
					});
					
				}
			}
			function showGroupMember() {
				if (jQuery("#groupMember").css("display") == "none") {
				if (jQuery("#channelId").val()) {
					jQuery.get(window.location.protocol+"//" + window.location.host + "/ccmApp/message.do?method=queryUserListByGroupChannel&channelId=" + jQuery("#channelId").val(),function(data){
						var object = data;
						if (data.length > 0) {
							var modifyGroupMemberListHtml = "";
							for (var i=0;i<data.length;i++) {
								var user = data[i];
								var userId = user.userId;
								var username = user.username;
								var selectFlag = user.selectFlag;
								if (selectFlag == "Y") {
									modifyGroupMemberListHtml+= "<li onclick=\"chatShow(this)\" id=" + userId + " channelTypeId=\"1\">" + username + "</li>";
								}
							}
							var currentUserName = '<%=SystemUtil.getCurrentUser().getFirstName() %>' + " " + '<%=SystemUtil.getCurrentUser().getLastName() %>';
							modifyGroupMemberListHtml+= "<li>" + currentUserName + "</li>";
							jQuery("#groupMemberPanel").html(modifyGroupMemberListHtml);
						}
					});
					
				}
				jQuery("#groupMember").show();
				} else 
					jQuery("#groupMember").hide();
			}

			function closeGroupMember() {
				jQuery("#groupMember").hide();
			}
			var noteIndex = null;
			function notePrev() {
				if (notePage) {
					if (noteIndex == null) {
						noteIndex = noteArr.length-1;
					}
				}
				if (noteIndex >= 0) {
					
					var id = noteArr[noteIndex].keys()[0];
					var value = noteArr[noteIndex].get(id);
					var lastId = jQuery("#lastId").val();
					var channelTypeId = jQuery("#channelTypeId").val();
					var userId = jQuery("#userId").val();
					var channelId = jQuery("#channelId").val();
					if (jQuery('#note' + id).offset()) {
						var container = jQuery('.showk'); 
						var scrollTo = jQuery('#note' + id); 
						container.animate({scrollTop: scrollTo.offset().top - container.offset().top + container.scrollTop() - container.height()/3},1000);
					} else {
						if (value < lastId) {
							if (channelTypeId == 1) {
								loadChatRecord(userId,true,value,"note" + id);
							} else {
								loadChatRecord(channelId,false,value,"note" + id);
							}
						}
					}
					if (noteIndex > 0)
						noteIndex--;
				}
			}

			var referenceIndex = null;
			function prev() {
				if (referencePage) {
					if (referenceIndex == null) {
						referenceIndex = referenceArr.length-1;
					}
					if (referenceIndex >= 0) {
						if (referenceIndex > 0)
							referenceIndex--;
						var id = referenceArr[referenceIndex].keys()[0];
						var value = referenceArr[referenceIndex].get(id);
						var lastId = jQuery("#lastId").val();
						var channelTypeId = jQuery("#channelTypeId").val();
						var userId = jQuery("#userId").val();
						var channelId = jQuery("#channelId").val();
						if (jQuery('#' + referenceTypeValue + id).offset()) {
							var container = jQuery('.showk'); 
							var scrollTo = jQuery('#' + referenceTypeValue + id); 
							container.animate({scrollTop: scrollTo.offset().top - container.offset().top + container.scrollTop() - container.height()/3},1000);
						} else {
							if (value < lastId) {
								if (channelTypeId == 1) {
									loadChatRecord(userId,true,value,referenceTypeValue + id);
								} else {
									loadChatRecord(channelId,false,value,referenceTypeValue + id);
								}
							}
						}
						
					}
				}
			}

			function next() {
				if (referencePage && referenceIndex != null && referenceIndex < referenceArr.length-1) {
					referenceIndex++;
					var id = referenceArr[referenceIndex].keys()[0];
					var value = referenceArr[referenceIndex].get(id);
					var lastId = jQuery("#lastId").val();
					var channelTypeId = jQuery("#channelTypeId").val();
					var userId = jQuery("#userId").val();
					var channelId = jQuery("#channelId").val();
					var container = jQuery('.showk'); 
					var scrollTo = jQuery('#' + referenceTypeValue + id); 
					if (scrollTo.offset()) 
						container.animate({scrollTop: scrollTo.offset().top - container.offset().top + container.scrollTop() - container.height()/3},1000);
					
				}
			}

			function cancelModifyGroupMember() {
				jQuery("#modifyGroupMemberList input[type=checkbox]").removeAttr("checked");
				editChannelMemberOpen = false;
				jQuery("#panelList4").hide();
				jQuery("#panelList3").show();
				jQuery("#hiddenGroupPanel").show();
			}

			function doModifyGroupMember() {
				if (jQuery(".youcezu ul li").length == 0) {
					alert("Please select at least one contact!");
					return;
				}
				var userIds = "";
				jQuery.each(jQuery(".youcezu ul li"),function(i,it){
					userIds+= jQuery(it)[0].id.replace("m","").replace("tm","").replace("t","") + ",";
				});
				if (userIds != "") {
					userIds = userIds.substring(0,userIds.length-1);
					var url = window.location.protocol+"//" + window.location.host + "/ccmApp/message.do";
					var params = {method:"saveGroupChannel",userIds:userIds};
					if (jQuery("#channelId").val()) {
						params = {method:"saveGroupChannel",userIds:userIds,channelId:jQuery("#channelId").val()};
					}
					if (jQuery("#channelId").val()) {
					} else {
						jQuery(".showk").html(oprHtml);
					}
					jQuery.post(url,params,function(data){
						var channelId = data.channelId;
						var channelTypeId = data.channelTypeId;
						var channelName = data.channelName;
						var fromUserId = data.fromUserId;
						var fromUsername = data.fromUsername;
						jQuery("#channelId").val(channelId);
						jQuery("#channelTypeId").val(2);
						jQuery(".name2").removeClass("chat_icon_single").addClass("chat_icon_group");
						
						jQuery(".anniur").show();
						jQuery(".oknames").hide();
						jQuery(".name2").show();
						jQuery(".friendClose").hide();
						jQuery(".name2").text(channelName);
						jQuery("#chatPanel").show();
						var div = jQuery(".showk").get(0);
						div.scrollTop = div.scrollHeight;
						
						getGroupList();
						jQuery.get(window.location.protocol+"//" + window.location.host + "/ccmApp/message.do?method=queryUserListByGroupChannel&channelId=" + jQuery("#channelId").val(),function(data){
							var object = data;
							if (data.length > 0) {
								var modifyGroupMemberListHtml = "";
								for (var i=0;i<data.length;i++) {
									var user = data[i];
									var userId = user.userId;
									var username = user.username;
									var selectFlag = user.selectFlag;
									if (selectFlag == "Y") {
										modifyGroupMemberListHtml+= "<li onclick=\"chatShow(this)\" id=" + userId + " channelTypeId=\"1\">" + username + "</li>";
									}
								}
								jQuery("#groupMemberPanel").html(modifyGroupMemberListHtml);
							}
						});
						
						jQuery("#modifyMember").hide();
						jQuery("#panelList3").show();
						jQuery("#hiddenGroupPanel").show();
					},'json');
				}
			}

			function doEditGroupName() {
				var channelId = jQuery("#channelId").val();
				if (jQuery.trim(jQuery("#editGroupName").val()) == "") {
					alert("Group name can’t be empty!");
					return;
				}
				var groupName = jQuery("#editGroupName").val().replace(/ /g," ");
				jQuery.get(window.location.protocol+"//" + window.location.host + "/ccmApp/message.do?method=modifyGroupName&channelId=" + channelId + "&groupName=" + groupName,function(){
					jQuery(".name2").text(jQuery("#editGroupName").val());
					jQuery(".oknames").hide();
					jQuery(".name2").show();
					getGroupList();
					getRecentChannelList();
				});
			}
			
			function showMessagePanel(hasMessage) {
				jQuery("#channelId").val("");
				if (!isFreshed) {
					// 查询最近联系列表
					getRecentChannelList();
					// 查询用户列表
					jQuery.get(window.location.protocol+"//" + window.location.host + "/ccmApp/message.do?method=queryContactList",function(data){
						if (data.length > 0) {
							var listHtml = "", modifyGroupMemberListHtml = "";
							var userId = '<%=SystemUtil.getCurrentUserId() %>';
							listHtml+= "<li onclick=\"chatShow(this)\" id=" + userId + " channelTypeId=\"1\">Myself</li>";
							for (var i=0;i<data.length;i++) {
								var user = data[i];
								var username = user.username;
								var userId = user.userId;
								listHtml+= "<li onclick=\"chatShow(this)\" id=" + userId + " channelTypeId=\"1\">" + username + "</li>";
								modifyGroupMemberListHtml+= "<li onclick=\"addToMember(this)\" id=m" + userId + ">" + username + "</li>";
							}
							jQuery("#totalCount").text(data.length);
							jQuery("#contactList").html(listHtml);
							jQuery("#hideContactList").html(listHtml);
							jQuery(".zuocezu ul").html(modifyGroupMemberListHtml);
							jQuery(".zuocezu2 ul").html(modifyGroupMemberListHtml);
							jQuery("#modifyGroupMemberList").html(modifyGroupMemberListHtml);
						}
					});
					// 查询讨论组列表
					getGroupList();
					isFreshed = true;
				}
				jQuery(".zhankai001").show();
				jQuery(".tray").hide();
					
			}

			// 查询最近联系列表
			function getRecentChannelList() {
				jQuery.get(window.location.protocol+"//" + window.location.host + "/ccmApp/message.do?method=queryRecentChannelList",function(data){
					if (data.length > 0) {
						var listHtml = "";
						for (var i=0;i<data.length;i++) {
							var channel = data[i];
							var channelTypeId = channel.channelTypeId;
							var channelName = channel.channelName ? channel.channelName : '';
							var unreadFlag =  channel.unreadFlag;
							if (channelTypeId == 1 || channelTypeId == 3) {
								listHtml+= "<li onclick=\"chatShow(this)\" ";
								if (unreadFlag=="Y") {
									listHtml+= "class=\"redper\"";
								}
								var userId = channel.toUserId;
								if (channelTypeId == 3) {
									userId = '<%=SystemUtil.getCurrentUserId() %>';
								}
								listHtml+= " id=" + userId + " channelTypeId=" + channelTypeId + " title=\"" + channelName + "\">" + channelName + "</li>";
							} else {
								listHtml+= "<li onclick=\"chatShow(this)\" ";
								if (unreadFlag=="Y") {
									listHtml+= "class=\"redgroup\"";
								} else {
									listHtml+= "class=\"groups\"";
								}
								listHtml+= " id=" + channel.channelId + " channelTypeId=" + channelTypeId + " title=\"" + channelName + "\">" + channelName + "</li>";
								
							}
						}
						jQuery("#recentChannelList").html(listHtml);
					}
				});
			}

			function getGroupList() {
				jQuery.get(window.location.protocol+"//" + window.location.host + "/ccmApp/message.do?method=queryGroupChannelList",function(data){
					var object = data;
					jQuery("#groupChannelList").html("");
					if (data.length > 0) {
						var listHtml = "";
						for (var i=0;i<data.length;i++) {
							var channel = data[i];
							var channelName = channel.channelName;
							var channelId = channel.channelId;
							listHtml+= "<li onclick=\"chatShow(this)\" class=\"groups\" id=" + channelId + " channelTypeId=\"2\" title=\"" + channelName + "\">" + channelName + "</li>";
						}
						jQuery("#groupChannelList").html(listHtml);
						jQuery("#groupChannelList li").contextMenu('menu', 
						         { 
						             bindings: 
						          { 
						              'hide': function(t, target) { 
											if (t.id) {
												jQuery.get(window.location.protocol+"//" + window.location.host + "/ccmApp/message.do?method=updateGroupChannelHiddenStatus&channelId=" + t.id + "&status=Y",function(data){
													getGroupList();
												});	
											}
						              }
						          }
						         }); 
						jQuery("#jqContextMenu").css("z-index",99999);
					}
					
				});
			}

			function queryHiddenGroupList() {
				jQuery("#hideGroupChannelList").html("");
				jQuery.get(window.location.protocol+"//" + window.location.host + "/ccmApp/message.do?method=queryHiddenGroupChannelList",function(data){
					var object = data;
					if (data.length > 0) {
						var listHtml = "";
						for (var i=0;i<data.length;i++) {
							var channel = data[i];
							var channelName = channel.channelName;
							var channelId = channel.channelId;
							listHtml+= "<li onclick=\"chatShow(this)\" class=\"groups\" id=" + channelId + " channelTypeId=\"2\" title=\"" + channelName + "\">" + channelName + "</li>";
						}
						jQuery("#hideGroupChannelList").html(listHtml);
						jQuery("#hideGroupChannelList li").contextMenu('showmenu', 
						         { 
						             bindings: 
						          { 
						              'show': function(item, target) { 
											if (item.id) {
												jQuery.get(window.location.protocol+"//" + window.location.host + "/ccmApp/message.do?method=updateGroupChannelHiddenStatus&channelId=" + item.id + "&status=N",function(data){
													queryHiddenGroupList();
												});
											}
						              }
						          }
						         }); 
						jQuery("#jqContextMenu").css("z-index",99999);
					}
				});
			}

			var messageFileHtml="";
			var isSendingMessage = false;

			// 发送消息
			function sendMessage(privateFlag) {
				if (isSendingMessage) return;
				var mergeId = "";
				var msgText = jQuery("#msgText").val();
				console.debug(msgText);
				var invoiceChecked = jQuery("#linkToInvoiceCheck").attr("checked");
				var banChecked = jQuery("#linkToBanCheck").attr("checked");
				var fileCount = parseInt($(".uploadnum").text(),10);
				if (fileCount == 0) {
					isSendingMessage = false;
				} else {
					isSendingMessage = true;
				}
				var fileErrorCount = $("#messageFileList").find(".icoWarning").length;
				if (fileErrorCount > 0) {
					if ($(".numtips").is(":hidden")) $(".numtips").show();
					alert("Please check uploaded document: document number can not be more than 10, document size can not be more than 30MB, document type should be included in ‘.gif’,’.jpg,’.jpeg’,’.bmp’,’.doc’,’.ppt’,’.xls’,’.docx’,’.pptx’,’.xlsx’,’.txt’.,’.png’.,’.pdf’,’.tif’. Please delete improper document and then upload again!");
					isSendingMessage = false;
					return;
				}
				if (fileCount > 10) {
					if ($(".numtips").is(":hidden")) $(".numtips").show();
					alert("Uploaded files can not be more than 10!");
					isSendingMessage = false;
					return;
				}
				
				if (msgText == "" && fileCount == 0) {
					alert("Please input message!");
					isSendingMessage = false;
					return;
				}
				
				msgText = msgText.replace(/ /g," ");
				if (msgText.length > 768) {
					jQuery(".showk").append("<div class=\"centoolong\">It's too long, pls divide them and send again.</div>");
					jQuery(".showk").get(0).scrollTop = jQuery(".showk").get(0).scrollHeight;
					isSendingMessage = false;
					return;
				}
				var type,url,toValue, referenceTypeId="",referenceId="",referenceNumber="";
				if (m_vendorId) {
					type = "Vendor";
					url = "#";
					toValue = m_vendorName;
					referenceId = m_vendorId;
					referenceTypeId = 4;
					referenceNumber = m_vendorName;
				} else if (m_invoiceId) {
					type= "Invoice";
					toValue = m_invoiceNummber;
					referenceId = m_invoiceId;
					referenceTypeId = 1;
					referenceNumber = m_invoiceNummber;
					url = "<%=request.getContextPath() %>" + "/viewInvoiceDetails.action?invoiceId=" + m_invoiceId + "#NAME_invoiceNote";
				} else if (m_banId) {
					type= "BAN";
					toValue = m_accountNumber;
					referenceId = m_banId;
					referenceTypeId = 2;
					referenceNumber = m_accountNumber;
					url = "<%=request.getContextPath() %>" + "/banDetailAction.action?bvo.banId=" + referenceId + "#NAME_tiffPage";
				} else if (m_disputeId) {
					type= "Dispute";
					toValue = m_disputeNumber;
					referenceId = m_disputeId;
					referenceTypeId = 3;
					referenceNumber = m_disputeNumber;
					url = "<%=request.getContextPath() %>" + "/viewDisputeDetails.action?disputeId=" + referenceId + "#NAME_TransactionHistoryListPage";
				} else if (m_circuitId) {
					type= "Circuit";
					toValue = m_circuitNumber;
					referenceId = m_circuitId;
					referenceTypeId = 5;
					referenceNumber = m_circuitNumber;
					url = "<%=request.getContextPath() %>" + "/showCircuitDetail.action?vendorCircuitId=" + referenceId + "#NAME_scoaPage";
				} else if (m_userId) {
					type= "User";
					toValue = userNumber;
					referenceId = m_userId;
					referenceTypeId = 6;
					referenceNumber = userNumber;
					url = "<%=request.getContextPath() %>" + "/securityManagement.action?userId=" + referenceId + "#NAME_previledgeVendorBanPage";
				}
				var errorSpanId = new Date().getTime();
				var currentServerTime = new Date().getTime() - timeDifference;
				if (jQuery("#channelTypeId").val() == 1) {
					var userId = jQuery("#userId").val();
					var listHtml="";
					
					var dateHtml = "<div class=\"time2\">" + new Date(currentServerTime).Format("MM-dd hh:mm") + "</div>";
					listHtml+= dateHtml;
					if ("Y" == privateFlag) {
						var privateHtml = "<div class=\"gray2 ty2\"> <span class=\"gatop2\" id=\"note1\">" + msgText.replace(/ /g, "&ensp;").replace(/\</g,"&lt;") + "</span> ";
						if (!invoiceChecked || !banChecked)
							privateHtml+= "<span class=\"gabot2\">Remarked message, only read by myself.</span>";
						if (type && invoiceChecked) {
							privateHtml = "<div class=\"gray2 ty2\"> <span class=\"gatop2\" id=\"note1\" style=\"border-bottom: 0px;\">" + msgText.replace(/ /g, "&ensp;").replace(/\</g,"&lt;") + "</span>";
							privateHtml+= "<span id=\"invoice1\" class=\"blbot2\" style=\"color:#b3b3b3;border-top: 1px solid #e6e6e6\">" + type + ":<a style=\"color:#b3b3b3;\" href=\"javascript:toHrefPage(" + referenceTypeId + "," + referenceId + ")\">" + toValue + "</a></span> ";
							if (!invoiceChecked || !banChecked)
								privateHtml+= "<span class=\"gabot2\" style=\"border-top: 1px solid #e6e6e6\">Remarked message, only read by myself.</span></div>";
						} else {
							if (!invoiceChecked || !banChecked)
							privateHtml+= "</div>";
						}
						listHtml+= privateHtml;
					} else {
						var toHtml = "<div class=\"blue2 ty2\"> <span class=\"bltop2\" style=\"word-wrap : break-word ;\">" + msgText.replace(/ /g, "&ensp;").replace(/\</g,"&lt;") + "</span><div class=\"tanhaods\" id=\"" + errorSpanId + "\"></div>";
						if (type && invoiceChecked) {
							toHtml+= "<span id=\"invoice1\" class=\"blbot2\">" + type + " : <a href=\"javascript:toHrefPage(" + referenceTypeId + "," + referenceId + ")\">" + toValue + "</a></span>";
							if (!invoiceChecked || !banChecked)
								toHtml+= "</div>";
						} else {
							if (!invoiceChecked || !banChecked)
								toHtml+= "</div>";
						}
						listHtml+= toHtml;
					}
					if (m_invoiceId) {


						if (invoiceChecked && banChecked) {
							mergeId = m_banId + "_" + m_invoiceId + "_" + new Date().Format('yyyy_MM_dd_hh_mm_ss');

							if (fileCount > 0) {
								// 发送Invoice
							  $("#msgChannelId").val("");
							  $("#toUserId").val(userId);
							  $("#msgPrivateFlag").val(privateFlag);
							  $("#msgReferenceTypeId").val(referenceTypeId);
							  $("#msgReferenceId").val(referenceId);
							  $("#msgReferenceNumber").val(referenceNumber);
							  $("#msgReferenceKey").val(mergeId);
							  $("#uploadMessageForm").ajaxSubmit({
				                    success: function(data){
				                    	var resultHtml = data.replace('<head></head><body><pre style="word-wrap: break-word; white-space: pre-wrap;">','').replace('</pre></body>','');
				                    	data = JSON.parse(resultHtml);
				                 //   	jQuery("#msgText").val("");
				                    	$(".numtips").hide();
				        		//		$(".uploadimg").html('<div class="text">Attach files</div><input class="upload" type="file" multiple="multiple" onchange="uploadMessageFiles(this.files)" name="messageFiles">');
				        		//		$(".uploadnum").text("0");
				                    	
				                    	isSendingMessage = false;
								    	if (data) {
								    		if (data.referencePage) {
												isReference = true;
												referencePage = data.referencePage;
												referenceArr = new Array();
												var j=0;
												for (var prop in referencePage) {
													  var map = new Map(); 
													  map.put(prop,referencePage[prop]);
													  referenceArr[j] = map;
													  j++;
												}
											}
											if (data.notePage) {
												notePage = data.notePage;
												noteArr = new Array();
												var k=0;
												for (var prop in notePage) {
													  var map = new Map(); 
													  map.put(prop,notePage[prop]);
													  noteArr[k] = map;
													  k++;
												}
											}
											var attachmentFile = data.attachmentFile;
											var jMessageFileHtml = $(messageFileHtml);
											
											if ("Y" == privateFlag) {
												var fileListHtml = "";
												if (attachmentFile) {
													for (var f=0;f<attachmentFile.length;f++) {
														var file = attachmentFile[f];
														var fileName = file.fileName;
														var filePath = file.filePath;
														if (fileName && filePath) {
															fileListHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#b3b3b3">'+fileName+'</a></span>';
														}
													}
												}
												jMessageFileHtml.find(".gabot2").before(fileListHtml);
											} else {
												var fileListHtml = "";
												if (attachmentFile) {
													for (var f=0;f<attachmentFile.length;f++) {
														var file = attachmentFile[f];
														var fileName = file.fileName;
														var filePath = file.filePath;
														if (fileName && filePath) {
															fileListHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#ffffff">'+fileName+'</a></span>';
														}
													}
												}
												if (jMessageFileHtml[1])
													$(jMessageFileHtml[1]).append(fileListHtml);
												else 
													$(jMessageFileHtml[0]).append(fileListHtml);
											}
											//jQuery(".showk").append(jMessageFileHtml);
											jQuery("#invoice1").attr("id",type+data.id);
											if ("Y" == privateFlag) {
												jQuery("#note1").attr("id","note"+data.id);
											}
								    	}
										getRecentChannelList();


										// 发送BAN
									  	type= "BAN";
										toValue = m_accountNumber;
										referenceId = m_banId;
										referenceTypeId = 2;
										referenceNumber = m_accountNumber;
										url = "<%=request.getContextPath() %>" + "/banDetailAction.action?bvo.banId=" + referenceId + "#NAME_tiffPage";
										if ("Y" == privateFlag) {
											var privateHtml = "";
											if (type) {
												privateHtml = "<span id=\"invoice1\" class=\"blbot2\" style=\"color:#b3b3b3;border-top: 1px solid #e6e6e6\">" + type + ":<a style=\"color:#b3b3b3;\" href=\"javascript:toHrefPage(" + referenceTypeId + "," + referenceId + ")\">" + toValue + "</a></span> <span class=\"gabot2\" style=\"border-top: 1px solid #e6e6e6\">Remarked message, only read by myself.</span> </div>";
											} else
												privateHtml+= "</div>";
											listHtml+= privateHtml;
										} else {
											var toHtml = "";
											if (type) {
												toHtml+= "<span id=\"invoice1\" class=\"blbot2\">" + type + " : <a href=\"javascript:toHrefPage(" + referenceTypeId + "," + referenceId + ")\">" + toValue + "</a></span> </div>";
											} else
												toHtml+= "</div>";
											listHtml+= toHtml;
										}
										messageFileHtml= listHtml;
										  $("#msgChannelId").val("");
										  $("#toUserId").val(userId);
										  $("#msgPrivateFlag").val(privateFlag);
										  $("#msgReferenceTypeId").val(referenceTypeId);
										  $("#msgReferenceId").val(referenceId);
										  $("#msgReferenceNumber").val(referenceNumber);
										  $("#msgReferenceKey").val(mergeId);
										  $("#uploadMessageForm").ajaxSubmit({
							                    success: function(data){
							                    	var resultHtml = data.replace('<head></head><body><pre style="word-wrap: break-word; white-space: pre-wrap;">','').replace('</pre></body>','');
							                    	data = JSON.parse(resultHtml);
							                    	jQuery("#msgText").val("");
							                    	$("#messageFileList").html("");
							                    	$(".numtips").hide();
							        				$(".uploadimg").html('<div class="text">Attach files</div><input class="upload" type="file" multiple="multiple" onchange="doUploadMessageFiles(this.files)" name="messageFiles">');
							        				$(".uploadnum").text("0");
							                    	
							                    	isSendingMessage = false;
											    	if (data) {
											    		if (data.referencePage) {
															isReference = true;
															referencePage = data.referencePage;
															referenceArr = new Array();
															var j=0;
															for (var prop in referencePage) {
																  var map = new Map(); 
																  map.put(prop,referencePage[prop]);
																  referenceArr[j] = map;
																  j++;
															}
														}
														if (data.notePage) {
															notePage = data.notePage;
															noteArr = new Array();
															var k=0;
															for (var prop in notePage) {
																  var map = new Map(); 
																  map.put(prop,notePage[prop]);
																  noteArr[k] = map;
																  k++;
															}
														}
														var attachmentFile = data.attachmentFile;
														var jMessageFileHtml = $(messageFileHtml);
														
														if ("Y" == privateFlag) {
															var fileListHtml = "";
															if (attachmentFile) {
																for (var f=0;f<attachmentFile.length;f++) {
																	var file = attachmentFile[f];
																	var fileName = file.fileName;
																	var filePath = file.filePath;
																	if (fileName && filePath) {
																		fileListHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#b3b3b3">'+fileName+'</a></span>';
																	}
																}
															}
															jMessageFileHtml.find(".gabot2").before(fileListHtml);
														} else {
															var fileListHtml = "";
															if (attachmentFile) {
																for (var f=0;f<attachmentFile.length;f++) {
																	var file = attachmentFile[f];
																	var fileName = file.fileName;
																	var filePath = file.filePath;
																	if (fileName && filePath) {
																		fileListHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#ffffff">'+fileName+'</a></span>';
																	}
																}
															}
															if (jMessageFileHtml[1])
																$(jMessageFileHtml[1]).append(fileListHtml);
															else 
																$(jMessageFileHtml[0]).append(fileListHtml);
														}
														jQuery(".showk").append(jMessageFileHtml);
														jQuery("#invoice1").attr("id",type+data.id);
														if ("Y" == privateFlag) {
															jQuery("#note1").attr("id","note"+data.id);
														}
														jQuery(".showk").get(0).scrollTop = jQuery(".showk").get(0).scrollHeight;
											    	}
													getRecentChannelList();
							                    },
							                    error: function(XmlHttpRequest, textStatus, errorThrown){
							                        alert( "Network error!");
							                        isSendingMessage = false;
							                    }
							                });
				                    },
				                    error: function(XmlHttpRequest, textStatus, errorThrown){
				                        alert( "Network error!");
				                        isSendingMessage = false;
				                    }
				                });
								
							} else {
							
							// 发送invoice
							jQuery.ajax( {  
							    url:window.location.protocol+"//" + window.location.host + "/ccmApp/message.do",// 跳转到 action  
							    data:{method:"insertFriendsMessage",toUserId:userId,msgText:msgText,privateFlag:privateFlag,referenceTypeId:referenceTypeId,referenceId:referenceId,referenceNumber:referenceNumber,referenceKey:mergeId},  
							    type:'post',  
							    dataType:'json',  
							    success:function(data) {  
							    	if (data == null) {
							    		jQuery("#" + errorSpanId).show(); 
										alert( "Network error!");
									} else {
							    	jQuery("#msgText").val("");
							    	if (data) {
							    		if (data.referencePage) {
											isReference = true;
											referencePage = data.referencePage;
											referenceArr = new Array();
											var j=0;
											for (var prop in referencePage) {
												  var map = new Map(); 
												  map.put(prop,referencePage[prop]);
												  referenceArr[j] = map;
												  j++;
											}
										}
										if (data.notePage) {
											notePage = data.notePage;
											noteArr = new Array();
											var k=0;
											for (var prop in notePage) {
												  var map = new Map(); 
												  map.put(prop,notePage[prop]);
												  noteArr[k] = map;
												  k++;
											}
										}
										jQuery("#invoice1").attr("id",type+data.id);
										if ("Y" == privateFlag) {
											jQuery("#note1").attr("id","note"+data.id);
										}
							    	}
									getRecentChannelList();
									}
							     },  
							     error : function(error) { 
								     jQuery("#" + errorSpanId).show(); 
							     }  
							});
							

							// 发送BAN
							type= "BAN";
							toValue = m_accountNumber;
							referenceId = m_banId;
							referenceTypeId = 2;
							referenceNumber = m_accountNumber;
							url = "<%=request.getContextPath() %>" + "/banDetailAction.action?bvo.banId=" + referenceId + "#NAME_tiffPage";
							if ("Y" == privateFlag) {
								var privateHtml = "";
								if (type) {
									privateHtml = "<span id=\"invoice1\" class=\"blbot2\" style=\"color:#b3b3b3;border-top: 1px solid #e6e6e6\">" + type + ":<a style=\"color:#b3b3b3;\" href=\"javascript:toHrefPage(" + referenceTypeId + "," + referenceId + ")\">" + toValue + "</a></span> <span class=\"gabot2\" style=\"border-top: 1px solid #e6e6e6\">Remarked message, only read by myself.</span> </div>";
								} else
									privateHtml+= "</div>";
								listHtml+= privateHtml;
							} else {
								var toHtml = "";
								if (type) {
									toHtml+= "<span id=\"invoice1\" class=\"blbot2\">" + type + " : <a href=\"javascript:toHrefPage(" + referenceTypeId + "," + referenceId + ")\">" + toValue + "</a></span> </div>";
								} else
									toHtml+= "</div>";
								listHtml+= toHtml;
							}

							jQuery.ajax( {  
							    url:window.location.protocol+"//" + window.location.host + "/ccmApp/message.do",// 跳转到 action  
							    data:{method:"insertFriendsMessage",toUserId:userId,msgText:msgText,privateFlag:privateFlag,referenceTypeId:referenceTypeId,referenceId:referenceId,referenceNumber:referenceNumber,referenceKey:mergeId},  
							    type:'post',  
							    dataType:'json',  
							    success:function(data) {  
							    	if (data == null) {
							    		jQuery("#" + errorSpanId).show(); 
										alert( "Network error!");
									} else {
							    	jQuery("#msgText").val("");
							    	if (data) {
							    		if (data.referencePage) {
											isReference = true;
											referencePage = data.referencePage;
											referenceArr = new Array();
											var j=0;
											for (var prop in referencePage) {
												  var map = new Map(); 
												  map.put(prop,referencePage[prop]);
												  referenceArr[j] = map;
												  j++;
											}
										}
										if (data.notePage) {
											notePage = data.notePage;
											noteArr = new Array();
											var k=0;
											for (var prop in notePage) {
												  var map = new Map(); 
												  map.put(prop,notePage[prop]);
												  noteArr[k] = map;
												  k++;
											}
										}
										jQuery("#invoice1").attr("id",type+data.id);
										if ("Y" == privateFlag) {
											jQuery("#note1").attr("id","note"+data.id);
										}
							    	}
									getRecentChannelList();
									}
							     },  
							     error : function(error) {  
							    	 jQuery("#" + errorSpanId).show(); 
							     }  
							});
							}
							if (fileCount == 0)
							jQuery(".showk").append(listHtml);
							else
								messageFileHtml = listHtml;
						} else {
						if (!invoiceChecked) {
							referenceId = "";
							referenceTypeId = "";
							referenceNumber = "";
						} else {
							if (fileCount > 0) {
								  $("#msgChannelId").val("");
								  $("#toUserId").val(userId);
								  $("#msgPrivateFlag").val(privateFlag);
								  $("#msgReferenceTypeId").val(referenceTypeId);
								  $("#msgReferenceId").val(referenceId);
								  $("#msgReferenceNumber").val(referenceNumber);
								  $("#msgReferenceKey").val(mergeId);
								  $("#uploadMessageForm").ajaxSubmit({
					                    success: function(data){
					                    	var resultHtml = data.replace('<head></head><body><pre style="word-wrap: break-word; white-space: pre-wrap;">','').replace('</pre></body>','');
					                    	data = JSON.parse(resultHtml);
					                    	jQuery("#msgText").val("");
					                    	$("#messageFileList").html("");
					                    	$(".numtips").hide();
					        				$(".uploadimg").html('<div class="text">Attach files</div><input class="upload" type="file" multiple="multiple" onchange="doUploadMessageFiles(this.files)" name="messageFiles">');
					        				$(".uploadnum").text("0");
					                    	isSendingMessage = false;
									    	if (data) {
									    		if (data.referencePage) {
													isReference = true;
													referencePage = data.referencePage;
													referenceArr = new Array();
													var j=0;
													for (var prop in referencePage) {
														  var map = new Map(); 
														  map.put(prop,referencePage[prop]);
														  referenceArr[j] = map;
														  j++;
													}
												}
												if (data.notePage) {
													notePage = data.notePage;
													noteArr = new Array();
													var k=0;
													for (var prop in notePage) {
														  var map = new Map(); 
														  map.put(prop,notePage[prop]);
														  noteArr[k] = map;
														  k++;
													}
												}
												var attachmentFile = data.attachmentFile;
												var jMessageFileHtml = $(messageFileHtml);
												
												if ("Y" == privateFlag) {
													var fileListHtml = "";
													if (attachmentFile) {
														for (var f=0;f<attachmentFile.length;f++) {
															var file = attachmentFile[f];
															var fileName = file.fileName;
															var filePath = file.filePath;
															if (fileName && filePath) {
																fileListHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#b3b3b3">'+fileName+'</a></span>';
															}
														}
													}
													jMessageFileHtml.find(".gabot2").before(fileListHtml);
												} else {
													var fileListHtml = "";
													if (attachmentFile) {
														for (var f=0;f<attachmentFile.length;f++) {
															var file = attachmentFile[f];
															var fileName = file.fileName;
															var filePath = file.filePath;
															if (fileName && filePath) {
																fileListHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#ffffff">'+fileName+'</a></span>';
															}
														}
													}
													if (jMessageFileHtml[1])
														$(jMessageFileHtml[1]).append(fileListHtml);
													else 
														$(jMessageFileHtml[0]).append(fileListHtml);
												}
												jQuery(".showk").append(jMessageFileHtml);
												jQuery("#invoice1").attr("id",type+data.id);
												if ("Y" == privateFlag) {
													jQuery("#note1").attr("id","note"+data.id);
												}
												jQuery(".showk").get(0).scrollTop = jQuery(".showk").get(0).scrollHeight;
									    	}
											getRecentChannelList();
					                    },
					                    error: function(XmlHttpRequest, textStatus, errorThrown){
					                        alert( "Network error!");
					                        isSendingMessage = false;
					                    }
					                });
								} else {
							jQuery.ajax( {  
							    url:window.location.protocol+"//" + window.location.host + "/ccmApp/message.do",// 跳转到 action  
							    data:{method:"insertFriendsMessage",toUserId:userId,msgText:msgText,privateFlag:privateFlag,referenceTypeId:referenceTypeId,referenceId:referenceId,referenceNumber:referenceNumber},  
							    type:'post',  
							    dataType:'json',  
							    success:function(data) {  
							    	if (data == null) {
							    		jQuery("#" + errorSpanId).show(); 
										alert( "Network error!");
									} else {
							    	jQuery("#msgText").val("");
							    	if (data) {
							    		if (data.referencePage) {
											isReference = true;
											referencePage = data.referencePage;
											referenceArr = new Array();
											var j=0;
											for (var prop in referencePage) {
												  var map = new Map(); 
												  map.put(prop,referencePage[prop]);
												  referenceArr[j] = map;
												  j++;
											}
										}
										if (data.notePage) {
											notePage = data.notePage;
											noteArr = new Array();
											var k=0;
											for (var prop in notePage) {
												  var map = new Map(); 
												  map.put(prop,notePage[prop]);
												  noteArr[k] = map;
												  k++;
											}
										}
										jQuery("#invoice1").attr("id",type+data.id);
										if ("Y" == privateFlag) {
											jQuery("#note1").attr("id","note"+data.id);
										}
							    	}
									getRecentChannelList();
									}
							     },  
							     error : function(error) { 
								     jQuery("#" + errorSpanId).show(); 
							     }  
							});
							}
						}
						if (banChecked) {
							if (!invoiceChecked) {
								listHtml="";
							}
							type= "BAN";
							toValue = m_accountNumber;
							referenceId = m_banId;
							referenceTypeId = 2;
							referenceNumber = m_accountNumber;
							url = "<%=request.getContextPath() %>" + "/banDetailAction.action?bvo.banId=" + referenceId + "#NAME_tiffPage";
							if ("Y" == privateFlag) {
								var privateHtml = "<div class=\"gray2 ty2\"> <span class=\"gatop2\" id=\"note1\">" + msgText.replace(/ /g, "&ensp;").replace(/\</g,"&lt;") + "</span> <span class=\"gabot2\">Remarked message, only read by myself.</span>";
								if (type) {
									privateHtml = "<div class=\"gray2 ty2\"> <span class=\"gatop2\" id=\"note1\" style=\"border-bottom: 0px;\">" + msgText.replace(/ /g, "&ensp;").replace(/\</g,"&lt;") + "</span>";
									privateHtml+= "<span id=\"invoice1\" class=\"blbot2\" style=\"color:#b3b3b3;border-top: 1px solid #e6e6e6\">" + type + ":<a style=\"color:#b3b3b3;\" href=\"javascript:toHrefPage(" + referenceTypeId + "," + referenceId + ")\">" + toValue + "</a></span> <span class=\"gabot2\" style=\"border-top: 1px solid #e6e6e6\">Remarked message, only read by myself.</span> </div>";
								} else
									privateHtml+= "</div>";
								listHtml+= privateHtml;
							} else {
								var toHtml = "<div class=\"blue2 ty2\"> <span class=\"bltop2\" style=\"word-wrap : break-word ;\">" + msgText.replace(/ /g, "&ensp;").replace(/\</g,"&lt;") + "</span><div class=\"tanhaods\"  id=\"" + errorSpanId + "\"></div>";
								if (type) {
									toHtml+= "<span id=\"invoice1\" class=\"blbot2\">" + type + " : <a href=\"javascript:toHrefPage(" + referenceTypeId + "," + referenceId + ")\">" + toValue + "</a></span> </div>";
								} else
									toHtml+= "</div>";
								listHtml+= toHtml;
							}
							messageFileHtml = listHtml;
							if (fileCount > 0) {
								  $("#msgChannelId").val("");
								  $("#toUserId").val(userId);
								  $("#msgPrivateFlag").val(privateFlag);
								  $("#msgReferenceTypeId").val(referenceTypeId);
								  $("#msgReferenceId").val(referenceId);
								  $("#msgReferenceNumber").val(referenceNumber);
								  $("#msgReferenceKey").val(mergeId);
								  $("#uploadMessageForm").ajaxSubmit({
					                    success: function(data){
					                    	var resultHtml = data.replace('<head></head><body><pre style="word-wrap: break-word; white-space: pre-wrap;">','').replace('</pre></body>','');
					                    	data = JSON.parse(resultHtml);
					                    	jQuery("#msgText").val("");
					                    	$("#messageFileList").html("");
					                    	$(".numtips").hide();
					        				$(".uploadimg").html('<div class="text">Attach files</div><input class="upload" type="file" multiple="multiple" onchange="doUploadMessageFiles(this.files)" name="messageFiles">');
					        				$(".uploadnum").text("0");
					                    	isSendingMessage = false;
									    	if (data) {
									    		if (data.referencePage) {
													isReference = true;
													referencePage = data.referencePage;
													referenceArr = new Array();
													var j=0;
													for (var prop in referencePage) {
														  var map = new Map(); 
														  map.put(prop,referencePage[prop]);
														  referenceArr[j] = map;
														  j++;
													}
												}
												if (data.notePage) {
													notePage = data.notePage;
													noteArr = new Array();
													var k=0;
													for (var prop in notePage) {
														  var map = new Map(); 
														  map.put(prop,notePage[prop]);
														  noteArr[k] = map;
														  k++;
													}
												}
												var attachmentFile = data.attachmentFile;
												var jMessageFileHtml = $(messageFileHtml);
												
												if ("Y" == privateFlag) {
													var fileListHtml = "";
													if (attachmentFile) {
														for (var f=0;f<attachmentFile.length;f++) {
															var file = attachmentFile[f];
															var fileName = file.fileName;
															var filePath = file.filePath;
															if (fileName && filePath) {
																fileListHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#b3b3b3">'+fileName+'</a></span>';
															}
														}
													}
													jMessageFileHtml.find(".gabot2").before(fileListHtml);
												} else {
													var fileListHtml = "";
													if (attachmentFile) {
														for (var f=0;f<attachmentFile.length;f++) {
															var file = attachmentFile[f];
															var fileName = file.fileName;
															var filePath = file.filePath;
															if (fileName && filePath) {
																fileListHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#ffffff">'+fileName+'</a></span>';
															}
														}
													}
													if (jMessageFileHtml[1])
														$(jMessageFileHtml[1]).append(fileListHtml);
													else 
														$(jMessageFileHtml[0]).append(fileListHtml);
												}
												jQuery(".showk").append(jMessageFileHtml);
												jQuery("#invoice1").attr("id",type+data.id);
												if ("Y" == privateFlag) {
													jQuery("#note1").attr("id","note"+data.id);
												}
												jQuery(".showk").get(0).scrollTop = jQuery(".showk").get(0).scrollHeight;
									    	}
											getRecentChannelList();
					                    },
					                    error: function(XmlHttpRequest, textStatus, errorThrown){
					                        alert( "Network error!");
					                        isSendingMessage = false;
					                    }
					                });
								} else {
							jQuery.ajax( {  
							    url:window.location.protocol+"//" + window.location.host + "/ccmApp/message.do",// 跳转到 action  
							    data:{method:"insertFriendsMessage",toUserId:userId,msgText:msgText,privateFlag:privateFlag,referenceTypeId:referenceTypeId,referenceId:referenceId,referenceNumber:referenceNumber},  
							    type:'post',  
							    dataType:'json',  
							    success:function(data) {  
							    	if (data == null) {
							    		jQuery("#" + errorSpanId).show(); 
										alert( "Network error!");
									} else {
							    	jQuery("#msgText").val("");
							    	if (data) {
							    		if (data.referencePage) {
											isReference = true;
											referencePage = data.referencePage;
											referenceArr = new Array();
											var j=0;
											for (var prop in referencePage) {
												  var map = new Map(); 
												  map.put(prop,referencePage[prop]);
												  referenceArr[j] = map;
												  j++;
											}
										}
										if (data.notePage) {
											notePage = data.notePage;
											noteArr = new Array();
											var k=0;
											for (var prop in notePage) {
												  var map = new Map(); 
												  map.put(prop,notePage[prop]);
												  noteArr[k] = map;
												  k++;
											}
										}
										jQuery("#invoice1").attr("id",type+data.id);
										if ("Y" == privateFlag) {
											jQuery("#note1").attr("id","note"+data.id);
										}
							    	}
									getRecentChannelList();
									}
							     },  
							     error : function(error) {  
							    	 jQuery("#" + errorSpanId).show(); 
							     }  
							});
							}
						}

						if (!invoiceChecked && !banChecked) {
							referenceId = "";
							referenceTypeId = "";
							referenceNumber = "";
							if (fileCount > 0) {
								  $("#msgChannelId").val("");
								  $("#toUserId").val(userId);
								  $("#msgPrivateFlag").val(privateFlag);
								  $("#msgReferenceTypeId").val(referenceTypeId);
								  $("#msgReferenceId").val(referenceId);
								  $("#msgReferenceNumber").val(referenceNumber);
								  $("#msgReferenceKey").val(mergeId);

								  $("#uploadMessageForm").ajaxSubmit({
					                    success: function(data){
					                    	var resultHtml = data.replace('<head></head><body><pre style="word-wrap: break-word; white-space: pre-wrap;">','').replace('</pre></body>','');
					                    	data = JSON.parse(resultHtml);
					                    	jQuery("#msgText").val("");
					                    	$("#messageFileList").html("");
					                    	$(".numtips").hide();
					        				$(".uploadimg").html('<div class="text">Attach files</div><input class="upload" type="file" multiple="multiple" onchange="doUploadMessageFiles(this.files)" name="messageFiles">');
					        				$(".uploadnum").text("0");
					                    	isSendingMessage = false;
									    	if (data) {
									    		if (data.referencePage) {
													isReference = true;
													referencePage = data.referencePage;
													referenceArr = new Array();
													var j=0;
													for (var prop in referencePage) {
														  var map = new Map(); 
														  map.put(prop,referencePage[prop]);
														  referenceArr[j] = map;
														  j++;
													}
												}
												if (data.notePage) {
													notePage = data.notePage;
													noteArr = new Array();
													var k=0;
													for (var prop in notePage) {
														  var map = new Map(); 
														  map.put(prop,notePage[prop]);
														  noteArr[k] = map;
														  k++;
													}
												}
												var attachmentFile = data.attachmentFile;
												var jMessageFileHtml = $(messageFileHtml);
												
												if ("Y" == privateFlag) {
													var fileListHtml = "";
													if (attachmentFile) {
														for (var f=0;f<attachmentFile.length;f++) {
															var file = attachmentFile[f];
															var fileName = file.fileName;
															var filePath = file.filePath;
															if (fileName && filePath) {
																fileListHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#b3b3b3">'+fileName+'</a></span>';
															}
														}
													}
													jMessageFileHtml.find(".gabot2").before(fileListHtml);
												} else {
													var fileListHtml = "";
													if (attachmentFile) {
														for (var f=0;f<attachmentFile.length;f++) {
															var file = attachmentFile[f];
															var fileName = file.fileName;
															var filePath = file.filePath;
															if (fileName && filePath) {
																fileListHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#ffffff">'+fileName+'</a></span>';
															}
														}
													}
													if (jMessageFileHtml[1])
														$(jMessageFileHtml[1]).append(fileListHtml);
													else 
														$(jMessageFileHtml[0]).append(fileListHtml);
												}
												jQuery(".showk").append(jMessageFileHtml);
												jQuery("#invoice1").attr("id",type+data.id);
												if ("Y" == privateFlag) {
													jQuery("#note1").attr("id","note"+data.id);
												}
												jQuery(".showk").get(0).scrollTop = jQuery(".showk").get(0).scrollHeight;
									    	}
											getRecentChannelList();
					                    },
					                    error: function(XmlHttpRequest, textStatus, errorThrown){
					                        alert( "Network error!");
					                        isSendingMessage = false;
					                    }
					                });
								//  $("#uploadMessageForm").submit();
								} else {
							jQuery.ajax( {  
							    url:window.location.protocol+"//" + window.location.host + "/ccmApp/message.do",// 跳转到 action  
							    data:{method:"insertFriendsMessage",toUserId:userId,msgText:msgText,privateFlag:privateFlag,referenceTypeId:referenceTypeId,referenceId:referenceId,referenceNumber:referenceNumber},  
							    type:'post',  
							    dataType:'json',  
							    success:function(data) {  
									if (data == null) {
										jQuery("#" + errorSpanId).show(); 
										alert( "Network error!");
									} else {
							    	jQuery("#msgText").val("");
							    	if (data) {
							    		if (data.referencePage) {
											isReference = true;
											referencePage = data.referencePage;
											referenceArr = new Array();
											var j=0;
											for (var prop in referencePage) {
												  var map = new Map(); 
												  map.put(prop,referencePage[prop]);
												  referenceArr[j] = map;
												  j++;
											}
										}
										if (data.notePage) {
											notePage = data.notePage;
											noteArr = new Array();
											var k=0;
											for (var prop in notePage) {
												  var map = new Map(); 
												  map.put(prop,notePage[prop]);
												  noteArr[k] = map;
												  k++;
											}
										}
										jQuery("#invoice1").attr("id",type+data.id);
										if ("Y" == privateFlag) {
											jQuery("#note1").attr("id","note"+data.id);
										}
							    	}
									getRecentChannelList();
									}
							     },  
							     error : function(error) {  
							    	 jQuery("#" + errorSpanId).show(); 
							     }  
							});
							}
						}
						if (fileCount == 0)
								jQuery(".showk").append(listHtml);
							else
								messageFileHtml = listHtml;
						}
						jQuery("#linkToInvoiceCheck").removeAttr("checked");
						jQuery("#linkToBanCheck").removeAttr("checked");
						jQuery(".showk").get(0).scrollTop = jQuery(".showk").get(0).scrollHeight;
				//		jQuery("#msgText").val("");
						
						return;
					}
					if (fileCount == 0)
						jQuery(".showk").append(listHtml);
					else
						messageFileHtml = listHtml;
					if (!invoiceChecked) {
						referenceId = "";
						referenceTypeId = "";
						referenceNumber = "";
					}
					if (fileCount > 0) {
						  $("#msgChannelId").val("");
						  $("#toUserId").val(userId);
						  $("#msgPrivateFlag").val(privateFlag);
						  $("#msgReferenceTypeId").val(referenceTypeId);
						  $("#msgReferenceId").val(referenceId);
						  $("#msgReferenceNumber").val(referenceNumber);
						  $("#msgReferenceKey").val(mergeId);
						  $("#uploadMessageForm").ajaxSubmit({
			                    success: function(data){
			                    	var resultHtml = data.replace('<head></head><body><pre style="word-wrap: break-word; white-space: pre-wrap;">','').replace('</pre></body>','');
			                    	data = JSON.parse(resultHtml);
			                    	jQuery("#msgText").val("");
			                    	$("#messageFileList").html("");
			                    	$(".numtips").hide();
			        				$(".uploadimg").html('<div class="text">Attach files</div><input class="upload" type="file" multiple="multiple" onchange="doUploadMessageFiles(this.files)" name="messageFiles">');
			        				$(".uploadnum").text("0");
			                    	isSendingMessage = false;
							    	if (data) {
							    		if (data.referencePage) {
											isReference = true;
											referencePage = data.referencePage;
											referenceArr = new Array();
											var j=0;
											for (var prop in referencePage) {
												  var map = new Map(); 
												  map.put(prop,referencePage[prop]);
												  referenceArr[j] = map;
												  j++;
											}
										}
										if (data.notePage) {
											notePage = data.notePage;
											noteArr = new Array();
											var k=0;
											for (var prop in notePage) {
												  var map = new Map(); 
												  map.put(prop,notePage[prop]);
												  noteArr[k] = map;
												  k++;
											}
										}
										var attachmentFile = data.attachmentFile;
										var jMessageFileHtml = $(messageFileHtml);
										
										if ("Y" == privateFlag) {
											var fileListHtml = "";
											if (attachmentFile) {
												for (var f=0;f<attachmentFile.length;f++) {
													var file = attachmentFile[f];
													var fileName = file.fileName;
													var filePath = file.filePath;
													if (fileName && filePath) {
														fileListHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#b3b3b3">'+fileName+'</a></span>';
													}
												}
											}
											jMessageFileHtml.find(".gabot2").before(fileListHtml);
										} else {
											var fileListHtml = "";
											if (attachmentFile) {
												for (var f=0;f<attachmentFile.length;f++) {
													var file = attachmentFile[f];
													var fileName = file.fileName;
													var filePath = file.filePath;
													if (fileName && filePath) {
														fileListHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#ffffff">'+fileName+'</a></span>';
													}
												}
											}
											if (jMessageFileHtml[1])
												$(jMessageFileHtml[1]).append(fileListHtml);
											else 
												$(jMessageFileHtml[0]).append(fileListHtml);
										}
										jQuery(".showk").append(jMessageFileHtml);
										jQuery("#invoice1").attr("id",type+data.id);
										if ("Y" == privateFlag) {
											jQuery("#note1").attr("id","note"+data.id);
										}
										jQuery(".showk").get(0).scrollTop = jQuery(".showk").get(0).scrollHeight;
							    	}
									getRecentChannelList();
			                    },
			                    error: function(XmlHttpRequest, textStatus, errorThrown){
			                        alert( "Network error!");
			                        isSendingMessage = false;
			                    }
			                });
						 // $("#uploadMessageForm").submit();
						} else {
					jQuery.ajax( {  
					    url:window.location.protocol+"//" + window.location.host + "/ccmApp/message.do",// 跳转到 action  
					    data:{method:"insertFriendsMessage",toUserId:userId,msgText:msgText,privateFlag:privateFlag,referenceTypeId:referenceTypeId,referenceId:referenceId,referenceNumber:referenceNumber},  
					    type:'post',  
					    dataType:'json',  
					    success:function(data) { 
					    	if (data == null) {
					    		jQuery("#" + errorSpanId).show(); 
								alert( "Network error!");
							} else { 
					    	jQuery("#msgText").val("");
					    	if (data) {
					    		if (data.referencePage) {
									isReference = true;
									referencePage = data.referencePage;
									referenceArr = new Array();
									var j=0;
									for (var prop in referencePage) {
										  var map = new Map(); 
										  map.put(prop,referencePage[prop]);
										  referenceArr[j] = map;
										  j++;
									}
								}
								if (data.notePage) {
									notePage = data.notePage;
									noteArr = new Array();
									var k=0;
									for (var prop in notePage) {
										  var map = new Map(); 
										  map.put(prop,notePage[prop]);
										  noteArr[k] = map;
										  k++;
									}
								}
								jQuery("#invoice1").attr("id",type+data.id);
								if ("Y" == privateFlag) {
									jQuery("#note1").attr("id","note"+data.id);
								}
					    	}
							getRecentChannelList();
							}
					     },  
					     error : function(error) {  
					    	 jQuery("#" + errorSpanId).show(); 
					     }  
					});
					}
				} else {
					var channelId = jQuery("#channelId").val();
					var listHtml="";
					var dateHtml = "<div class=\"time2\">" + new Date(currentServerTime).Format("MM-dd hh:mm") + "</div>";
					listHtml+= dateHtml;
					if ("Y" == privateFlag) {
						var privateHtml = "<div class=\"gray2 ty2\"> <span class=\"gatop2\" id=\"note1\">" + msgText.replace(/ /g, "&ensp;").replace(/\</g,"&lt;") + "</span>";
						if (!invoiceChecked || !banChecked)
							privateHtml+= "<span class=\"gabot2\">Remarked message, only read by myself.</span>";
						if (type && invoiceChecked) {
							privateHtml = "<div class=\"gray2 ty2\"> <span class=\"gatop2\" id=\"note1\" style=\"border-bottom: 0px;\">" + msgText.replace(/ /g, "&ensp;").replace(/\</g,"&lt;") + "</span>";
							privateHtml+= "<span id=\"invoice1\" class=\"blbot2\" style=\"color:#b3b3b3;border-top: 1px solid #e6e6e6\">" + type + ":<a style=\"color:#b3b3b3;\" href=\"javascript:toHrefPage(" + referenceTypeId + "," + referenceId + ")\">" + toValue + "</a></span>";
							if (!invoiceChecked || !banChecked)
								privateHtml+= "<span class=\"gabot2\" style=\"border-top: 1px solid #e6e6e6\">Remarked message, only read by myself.</span> </div>";
						} else
							if (!invoiceChecked || !banChecked)
								privateHtml+= "</div>";
						listHtml+= privateHtml;
					} else {
						var toHtml = "<div class=\"blue2 ty2\"> <span class=\"bltop2\" style=\"word-wrap : break-word ;\">" + msgText.replace(/ /g, "&ensp;").replace(/\</g,"&lt;") + "</span><div class=\"tanhaods\"  id=\"" + errorSpanId + "\"></div>";
						if (type && invoiceChecked) {
							toHtml+= "<span id=\"invoice1\" class=\"blbot2\">" + type + " : <a href=\"javascript:toHrefPage(" + referenceTypeId + "," + referenceId + ")\">" + toValue + "</a></span>";
							if (!invoiceChecked || !banChecked)
								toHtml+= "</div>";
						} else
							if (!invoiceChecked || !banChecked)
							toHtml+= "</div>";
						listHtml+= toHtml;
					}
					if (m_invoiceId) {
						if (invoiceChecked && banChecked) {
							mergeId = m_banId + "_" + m_invoiceId + "_" + new Date().Format('yyyy_MM_dd_hh_mm_ss');

							if (fileCount > 0) {
								  $("#msgChannelId").val(channelId);
								  $("#toUserId").val("");
								  $("#msgPrivateFlag").val(privateFlag);
								  $("#msgReferenceTypeId").val(referenceTypeId);
								  $("#msgReferenceId").val(referenceId);
								  $("#msgReferenceNumber").val(referenceNumber);
								  $("#msgReferenceKey").val(mergeId);
								  $("#uploadMessageForm").ajaxSubmit({
					                    success: function(data){
					                    	var resultHtml = data.replace('<head></head><body><pre style="word-wrap: break-word; white-space: pre-wrap;">','').replace('</pre></body>','');
					                    	data = JSON.parse(resultHtml);
					                    //	jQuery("#msgText").val("");
					                    //	$("#messageFileList").html("");
					                    	$(".numtips").hide();
					        			//	$(".uploadimg").html('<div class="text">Attach files</div><input class="upload" type="file" multiple="multiple" onchange="doUploadMessageFiles(this.files)" name="messageFiles">');
					        			//	$(".uploadnum").text("0");
					                    	isSendingMessage = false;
									    	if (data) {
									    		if (data.referencePage) {
													isReference = true;
													referencePage = data.referencePage;
													referenceArr = new Array();
													var j=0;
													for (var prop in referencePage) {
														  var map = new Map(); 
														  map.put(prop,referencePage[prop]);
														  referenceArr[j] = map;
														  j++;
													}
												}
												if (data.notePage) {
													notePage = data.notePage;
													noteArr = new Array();
													var k=0;
													for (var prop in notePage) {
														  var map = new Map(); 
														  map.put(prop,notePage[prop]);
														  noteArr[k] = map;
														  k++;
													}
												}
												var attachmentFile = data.attachmentFile;
												var jMessageFileHtml = $(messageFileHtml);
												
												if ("Y" == privateFlag) {
													var fileListHtml = "";
													if (attachmentFile) {
														for (var f=0;f<attachmentFile.length;f++) {
															var file = attachmentFile[f];
															var fileName = file.fileName;
															var filePath = file.filePath;
															if (fileName && filePath) {
																fileListHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#b3b3b3">'+fileName+'</a></span>';
															}
														}
													}
													jMessageFileHtml.find(".gabot2").before(fileListHtml);
												} else {
													var fileListHtml = "";
													if (attachmentFile) {
														for (var f=0;f<attachmentFile.length;f++) {
															var file = attachmentFile[f];
															var fileName = file.fileName;
															var filePath = file.filePath;
															if (fileName && filePath) {
																fileListHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#ffffff">'+fileName+'</a></span>';
															}
														}
													}
													if (jMessageFileHtml[1])
														$(jMessageFileHtml[1]).append(fileListHtml);
													else 
														$(jMessageFileHtml[0]).append(fileListHtml);
												}
											//	jQuery(".showk").append(jMessageFileHtml);
												jQuery("#invoice1").attr("id",type+data.id);
												if ("Y" == privateFlag) {
													jQuery("#note1").attr("id","note"+data.id);
												}
												jQuery(".showk").get(0).scrollTop = jQuery(".showk").get(0).scrollHeight;
									    	}
											getRecentChannelList();

											// 发送BAN
											type= "BAN";
											toValue = m_accountNumber;
											referenceId = m_banId;
											referenceTypeId = 2;
											referenceNumber = m_accountNumber;
											url = "<%=request.getContextPath() %>" + "/banDetailAction.action?bvo.banId=" + referenceId + "#NAME_tiffPage";
											if ("Y" == privateFlag) {
												if (type && invoiceChecked) {
													privateHtml = "<span id=\"invoice1\" class=\"blbot2\" style=\"color:#b3b3b3;border-top: 1px solid #e6e6e6\">" + type + ":<a style=\"color:#b3b3b3;\" href=\"javascript:toHrefPage(" + referenceTypeId + "," + referenceId + ")\">" + toValue + "</a></span> <span class=\"gabot2\" style=\"border-top: 1px solid #e6e6e6\">Remarked message, only read by myself.</span> </div>";
												} else
													privateHtml+= "</div>";
												listHtml+= privateHtml;
											} else {
												var toHtml = "";
												if (type) {
													toHtml+= "<span id=\"invoice1\" class=\"blbot2\">" + type + " : <a href=\"javascript:toHrefPage(" + referenceTypeId + "," + referenceId + ")\">" + toValue + "</a></span> </div>";
												} else
													toHtml+= "</div>";
												listHtml+= toHtml;
											}
											messageFileHtml= listHtml;
												  $("#msgChannelId").val(channelId);
												  $("#toUserId").val("");
												  $("#msgPrivateFlag").val(privateFlag);
												  $("#msgReferenceTypeId").val(referenceTypeId);
												  $("#msgReferenceId").val(referenceId);
												  $("#msgReferenceNumber").val(referenceNumber);
												  $("#msgReferenceKey").val(mergeId);
												  $("#uploadMessageForm").ajaxSubmit({
									                    success: function(data){
									                    	var resultHtml = data.replace('<head></head><body><pre style="word-wrap: break-word; white-space: pre-wrap;">','').replace('</pre></body>','');
									                    	data = JSON.parse(resultHtml);
									                    	jQuery("#msgText").val("");
									                    	$("#messageFileList").html("");
									                    	$(".numtips").hide();
									        				$(".uploadimg").html('<div class="text">Attach files</div><input class="upload" type="file" multiple="multiple" onchange="doUploadMessageFiles(this.files)" name="messageFiles">');
									        				$(".uploadnum").text("0");
									                    	isSendingMessage = false;
													    	if (data) {
													    		if (data.referencePage) {
																	isReference = true;
																	referencePage = data.referencePage;
																	referenceArr = new Array();
																	var j=0;
																	for (var prop in referencePage) {
																		  var map = new Map(); 
																		  map.put(prop,referencePage[prop]);
																		  referenceArr[j] = map;
																		  j++;
																	}
																}
																if (data.notePage) {
																	notePage = data.notePage;
																	noteArr = new Array();
																	var k=0;
																	for (var prop in notePage) {
																		  var map = new Map(); 
																		  map.put(prop,notePage[prop]);
																		  noteArr[k] = map;
																		  k++;
																	}
																}
																var attachmentFile = data.attachmentFile;
																var jMessageFileHtml = $(messageFileHtml);
																
																if ("Y" == privateFlag) {
																	var fileListHtml = "";
																	if (attachmentFile) {
																		for (var f=0;f<attachmentFile.length;f++) {
																			var file = attachmentFile[f];
																			var fileName = file.fileName;
																			var filePath = file.filePath;
																			if (fileName && filePath) {
																				fileListHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#b3b3b3">'+fileName+'</a></span>';
																			}
																		}
																	}
																	jMessageFileHtml.find(".gabot2").before(fileListHtml);
																} else {
																	var fileListHtml = "";
																	if (attachmentFile) {
																		for (var f=0;f<attachmentFile.length;f++) {
																			var file = attachmentFile[f];
																			var fileName = file.fileName;
																			var filePath = file.filePath;
																			if (fileName && filePath) {
																				fileListHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#ffffff">'+fileName+'</a></span>';
																			}
																		}
																	}
																	if (jMessageFileHtml[1])
																		$(jMessageFileHtml[1]).append(fileListHtml);
																	else 
																		$(jMessageFileHtml[0]).append(fileListHtml);
																}
																jQuery(".showk").append(jMessageFileHtml);
																jQuery("#invoice1").attr("id",type+data.id);
																if ("Y" == privateFlag) {
																	jQuery("#note1").attr("id","note"+data.id);
																}
																jQuery(".showk").get(0).scrollTop = jQuery(".showk").get(0).scrollHeight;
													    	}
															getRecentChannelList();
									                    },
									                    error: function(XmlHttpRequest, textStatus, errorThrown){
									                        alert( "Network error!");
									                        isSendingMessage = false;
									                    }
									                });
					                    },
					                    error: function(XmlHttpRequest, textStatus, errorThrown){
					                        alert( "Network error!");
					                        isSendingMessage = false;
					                    }
					                });
								
					                
								} else {
							// 发送invoice
							jQuery.ajax( {  
							    url:window.location.protocol+"//" + window.location.host + "/ccmApp/message.do",// 跳转到 action  
							    data:{method:"insertGroupMessage",channelId: channelId,msgText:msgText,privateFlag:privateFlag,referenceTypeId:referenceTypeId,referenceId:referenceId,referenceNumber:referenceNumber,referenceKey:mergeId},  
							    type:'post',  
							    dataType:'json',  
							    success:function(data) {  
							    	if (data == null) {
							    		jQuery("#" + errorSpanId).show(); 
										alert( "Network error!");
									} else {
							    	jQuery("#msgText").val("");
							    	if (data) {
							    		if (data.referencePage) {
											isReference = true;
											referencePage = data.referencePage;
											referenceArr = new Array();
											var j=0;
											for (var prop in referencePage) {
												  var map = new Map(); 
												  map.put(prop,referencePage[prop]);
												  referenceArr[j] = map;
												  j++;
											}
										}
										if (data.notePage) {
											notePage = data.notePage;
											noteArr = new Array();
											var k=0;
											for (var prop in notePage) {
												  var map = new Map(); 
												  map.put(prop,notePage[prop]);
												  noteArr[k] = map;
												  k++;
											}
										}
										jQuery("#invoice1").attr("id",type+data.id);
										if ("Y" == privateFlag) {
											jQuery("#note1").attr("id","note"+data.id);
										}
							    	}
									getRecentChannelList();
									}
							     },  
							     error : function(error) {  
							    	 jQuery("#" + errorSpanId).show(); 
							     }  
							});
							

							// 发送BAN
							type= "BAN";
							toValue = m_accountNumber;
							referenceId = m_banId;
							referenceTypeId = 2;
							referenceNumber = m_accountNumber;
							url = "<%=request.getContextPath() %>" + "/banDetailAction.action?bvo.banId=" + referenceId + "#NAME_tiffPage";
							if ("Y" == privateFlag) {
								if (type && invoiceChecked) {
									privateHtml = "<span id=\"invoice1\" class=\"blbot2\" style=\"color:#b3b3b3;border-top: 1px solid #e6e6e6\">" + type + ":<a style=\"color:#b3b3b3;\" href=\"javascript:toHrefPage(" + referenceTypeId + "," + referenceId + ")\">" + toValue + "</a></span> <span class=\"gabot2\" style=\"border-top: 1px solid #e6e6e6\">Remarked message, only read by myself.</span> </div>";
								} else
									privateHtml+= "</div>";
								listHtml+= privateHtml;
							} else {
								var toHtml = "";
								if (type) {
									toHtml+= "<span id=\"invoice1\" class=\"blbot2\">" + type + " : <a href=\"javascript:toHrefPage(" + referenceTypeId + "," + referenceId + ")\">" + toValue + "</a></span> </div>";
								} else
									toHtml+= "</div>";
								listHtml+= toHtml;
							}
							if (fileCount > 0) {
								  $("#msgChannelId").val(channelId);
								  $("#toUserId").val("");
								  $("#msgPrivateFlag").val(privateFlag);
								  $("#msgReferenceTypeId").val(referenceTypeId);
								  $("#msgReferenceId").val(referenceId);
								  $("#msgReferenceNumber").val(referenceNumber);
								  $("#msgReferenceKey").val(mergeId);
								  $("#uploadMessageForm").ajaxSubmit({
					                    success: function(data){
					                    	var resultHtml = data.replace('<head></head><body><pre style="word-wrap: break-word; white-space: pre-wrap;">','').replace('</pre></body>','');
					                    	data = JSON.parse(resultHtml);
					                    	jQuery("#msgText").val("");
					                    	$("#messageFileList").html("");
					                    	$(".numtips").hide();
					        				$(".uploadimg").html('<div class="text">Attach files</div><input class="upload" type="file" multiple="multiple" onchange="doUploadMessageFiles(this.files)" name="messageFiles">');
					        				$(".uploadnum").text("0");
					                    	isSendingMessage = false;
									    	if (data) {
									    		if (data.referencePage) {
													isReference = true;
													referencePage = data.referencePage;
													referenceArr = new Array();
													var j=0;
													for (var prop in referencePage) {
														  var map = new Map(); 
														  map.put(prop,referencePage[prop]);
														  referenceArr[j] = map;
														  j++;
													}
												}
												if (data.notePage) {
													notePage = data.notePage;
													noteArr = new Array();
													var k=0;
													for (var prop in notePage) {
														  var map = new Map(); 
														  map.put(prop,notePage[prop]);
														  noteArr[k] = map;
														  k++;
													}
												}
												var attachmentFile = data.attachmentFile;
												var jMessageFileHtml = $(messageFileHtml);
												
												if ("Y" == privateFlag) {
													var fileListHtml = "";
													if (attachmentFile) {
														for (var f=0;f<attachmentFile.length;f++) {
															var file = attachmentFile[f];
															var fileName = file.fileName;
															var filePath = file.filePath;
															if (fileName && filePath) {
																fileListHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#b3b3b3">'+fileName+'</a></span>';
															}
														}
													}
													jMessageFileHtml.find(".gabot2").before(fileListHtml);
												} else {
													var fileListHtml = "";
													if (attachmentFile) {
														for (var f=0;f<attachmentFile.length;f++) {
															var file = attachmentFile[f];
															var fileName = file.fileName;
															var filePath = file.filePath;
															if (fileName && filePath) {
																fileListHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#ffffff">'+fileName+'</a></span>';
															}
														}
													}
													if (jMessageFileHtml[1])
														$(jMessageFileHtml[1]).append(fileListHtml);
													else 
														$(jMessageFileHtml[0]).append(fileListHtml);
												}
												jQuery(".showk").append(jMessageFileHtml);
												jQuery("#invoice1").attr("id",type+data.id);
												if ("Y" == privateFlag) {
													jQuery("#note1").attr("id","note"+data.id);
												}
												jQuery(".showk").get(0).scrollTop = jQuery(".showk").get(0).scrollHeight;
									    	}
											getRecentChannelList();
					                    },
					                    error: function(XmlHttpRequest, textStatus, errorThrown){
					                        alert( "Network error!");
					                        isSendingMessage = false;
					                    }
					                });
							
								} else {
							jQuery.ajax( {  
							    url:window.location.protocol+"//" + window.location.host + "/ccmApp/message.do",// 跳转到 action  
							    data:{method:"insertGroupMessage",channelId: channelId,msgText:msgText,privateFlag:privateFlag,referenceTypeId:referenceTypeId,referenceId:referenceId,referenceNumber:referenceNumber,referenceKey:mergeId},  
							    type:'post',  
							    dataType:'json',  
							    success:function(data) {  
							    	if (data == null) {
							    		jQuery("#" + errorSpanId).show(); 
										alert( "Network error!");
									} else {
							    	jQuery("#msgText").val("");
							    	if (data) {
							    		if (data.referencePage) {
											isReference = true;
											referencePage = data.referencePage;
											referenceArr = new Array();
											var j=0;
											for (var prop in referencePage) {
												  var map = new Map(); 
												  map.put(prop,referencePage[prop]);
												  referenceArr[j] = map;
												  j++;
											}
										}
										if (data.notePage) {
											notePage = data.notePage;
											noteArr = new Array();
											var k=0;
											for (var prop in notePage) {
												  var map = new Map(); 
												  map.put(prop,notePage[prop]);
												  noteArr[k] = map;
												  k++;
											}
										}
										jQuery("#invoice1").attr("id",type+data.id);
										if ("Y" == privateFlag) {
											jQuery("#note1").attr("id","note"+data.id);
										}
							    	}
									getRecentChannelList();
									}
							     },  
							     error : function(error) {  
							    	 jQuery("#" + errorSpanId).show(); 
							     }  
							});
							}
							if (fileCount == 0)
								jQuery(".showk").append(listHtml);
							else
								messageFileHtml = listHtml;
								}
						} else {
						if (!invoiceChecked) {
							referenceId = "";
							referenceTypeId = "";
							referenceNumber = "";
						}else {
							if (fileCount > 0) {
								  $("#msgChannelId").val(channelId);
								  $("#toUserId").val("");
								  $("#msgPrivateFlag").val(privateFlag);
								  $("#msgReferenceTypeId").val(referenceTypeId);
								  $("#msgReferenceId").val(referenceId);
								  $("#msgReferenceNumber").val(referenceNumber);
								  $("#msgReferenceKey").val(mergeId);
								  $("#uploadMessageForm").ajaxSubmit({
					                    success: function(data){
					                    	var resultHtml = data.replace('<head></head><body><pre style="word-wrap: break-word; white-space: pre-wrap;">','').replace('</pre></body>','');
					                    	data = JSON.parse(resultHtml);
					                    	jQuery("#msgText").val("");
					                    	$("#messageFileList").html("");
					                    	$(".numtips").hide();
					        				$(".uploadimg").html('<div class="text">Attach files</div><input class="upload" type="file" multiple="multiple" onchange="doUploadMessageFiles(this.files)" name="messageFiles">');
					        				$(".uploadnum").text("0");
					                    	isSendingMessage = false;
									    	if (data) {
									    		if (data.referencePage) {
													isReference = true;
													referencePage = data.referencePage;
													referenceArr = new Array();
													var j=0;
													for (var prop in referencePage) {
														  var map = new Map(); 
														  map.put(prop,referencePage[prop]);
														  referenceArr[j] = map;
														  j++;
													}
												}
												if (data.notePage) {
													notePage = data.notePage;
													noteArr = new Array();
													var k=0;
													for (var prop in notePage) {
														  var map = new Map(); 
														  map.put(prop,notePage[prop]);
														  noteArr[k] = map;
														  k++;
													}
												}
												var attachmentFile = data.attachmentFile;
												var jMessageFileHtml = $(messageFileHtml);
												
												if ("Y" == privateFlag) {
													var fileListHtml = "";
													if (attachmentFile) {
														for (var f=0;f<attachmentFile.length;f++) {
															var file = attachmentFile[f];
															var fileName = file.fileName;
															var filePath = file.filePath;
															if (fileName && filePath) {
																fileListHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#b3b3b3">'+fileName+'</a></span>';
															}
														}
													}
													jMessageFileHtml.find(".gabot2").before(fileListHtml);
												} else {
													var fileListHtml = "";
													if (attachmentFile) {
														for (var f=0;f<attachmentFile.length;f++) {
															var file = attachmentFile[f];
															var fileName = file.fileName;
															var filePath = file.filePath;
															if (fileName && filePath) {
																fileListHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#ffffff">'+fileName+'</a></span>';
															}
														}
													}
													if (jMessageFileHtml[1])
														$(jMessageFileHtml[1]).append(fileListHtml);
													else 
														$(jMessageFileHtml[0]).append(fileListHtml);
												}
												jQuery(".showk").append(jMessageFileHtml);
												jQuery("#invoice1").attr("id",type+data.id);
												if ("Y" == privateFlag) {
													jQuery("#note1").attr("id","note"+data.id);
												}
												jQuery(".showk").get(0).scrollTop = jQuery(".showk").get(0).scrollHeight;
									    	}
											getRecentChannelList();
					                    },
					                    error: function(XmlHttpRequest, textStatus, errorThrown){
					                        alert( "Network error!");
					                        isSendingMessage = false;
					                    }
					                });
								} else {
							jQuery.ajax( {  
							    url:window.location.protocol+"//" + window.location.host + "/ccmApp/message.do",// 跳转到 action  
							    data:{method:"insertGroupMessage",channelId: channelId,msgText:msgText,privateFlag:privateFlag,referenceTypeId:referenceTypeId,referenceId:referenceId,referenceNumber:referenceNumber},  
							    type:'post',  
							    dataType:'json',  
							    success:function(data) {  
							    	if (data == null) {
							    		jQuery("#" + errorSpanId).show(); 
										alert( "Network error!");
									} else {
							    	jQuery("#msgText").val("");
							    	if (data) {
							    		if (data.referencePage) {
											isReference = true;
											referencePage = data.referencePage;
											referenceArr = new Array();
											var j=0;
											for (var prop in referencePage) {
												  var map = new Map(); 
												  map.put(prop,referencePage[prop]);
												  referenceArr[j] = map;
												  j++;
											}
										}
										if (data.notePage) {
											notePage = data.notePage;
											noteArr = new Array();
											var k=0;
											for (var prop in notePage) {
												  var map = new Map(); 
												  map.put(prop,notePage[prop]);
												  noteArr[k] = map;
												  k++;
											}
										}
										jQuery("#invoice1").attr("id",type+data.id);
										if ("Y" == privateFlag) {
											jQuery("#note1").attr("id","note"+data.id);
										}
							    	}
									getRecentChannelList();
									}
							     },  
							     error : function(error) {  
							    	 jQuery("#" + errorSpanId).show(); 
							     }  
							});
							}
						}
						if (banChecked) {
							if (!invoiceChecked) {
								listHtml="";
							}
							type= "BAN";
							toValue = m_accountNumber;
							referenceId = m_banId;
							referenceTypeId = 2;
							referenceNumber = m_accountNumber;
							url = "<%=request.getContextPath() %>" + "/banDetailAction.action?bvo.banId=" + referenceId + "#NAME_tiffPage";
							if ("Y" == privateFlag) {
								var privateHtml = "<div class=\"gray2 ty2\"> <span class=\"gatop2\" id=\"note1\">" + msgText.replace(/ /g, "&ensp;").replace(/\</g,"&lt;") + "</span> <span class=\"gabot2\">Remarked message, only read by myself.</span>";
								if (type && invoiceChecked) {
									privateHtml = "<div class=\"gray2 ty2\"> <span class=\"gatop2\" id=\"note1\" style=\"border-bottom: 0px;\">" + msgText.replace(/ /g, "&ensp;").replace(/\</g,"&lt;") + "</span>";
									privateHtml+= "<span id=\"invoice1\" class=\"blbot2\" style=\"color:#b3b3b3;border-top: 1px solid #e6e6e6\">" + type + ":<a style=\"color:#b3b3b3;\" href=\"javascript:toHrefPage(" + referenceTypeId + "," + referenceId + ")\">" + toValue + "</a></span> <span class=\"gabot2\" style=\"border-top: 1px solid #e6e6e6\">Remarked message, only read by myself.</span> </div>";
								} else
									privateHtml+= "</div>";
								listHtml+= privateHtml;
							} else {
								var toHtml = "<div class=\"blue2 ty2\"> <span class=\"bltop2\" style=\"word-wrap : break-word ;\">" + msgText.replace(/ /g, "&ensp;").replace(/\</g,"&lt;") + "</span><div class=\"tanhaods\"  id=\"" + errorSpanId + "\"></div>";
								if (type) {
									toHtml+= "<span id=\"invoice1\" class=\"blbot2\">" + type + " : <a href=\"javascript:toHrefPage(" + referenceTypeId + "," + referenceId + ")\">" + toValue + "</a></span> </div>";
								} else
									toHtml+= "</div>";
								listHtml+= toHtml;
							}
							messageFileHtml = listHtml;
							if (fileCount > 0) {
								  $("#msgChannelId").val(channelId);
								  $("#toUserId").val("");
								  $("#msgPrivateFlag").val(privateFlag);
								  $("#msgReferenceTypeId").val(referenceTypeId);
								  $("#msgReferenceId").val(referenceId);
								  $("#msgReferenceNumber").val(referenceNumber);
								  $("#msgReferenceKey").val(mergeId);
								  $("#uploadMessageForm").ajaxSubmit({
					                    success: function(data){
					                    	var resultHtml = data.replace('<head></head><body><pre style="word-wrap: break-word; white-space: pre-wrap;">','').replace('</pre></body>','');
					                    	data = JSON.parse(resultHtml);
					                    	jQuery("#msgText").val("");
					                    	$("#messageFileList").html("");
					                    	$(".numtips").hide();
					        				$(".uploadimg").html('<div class="text">Attach files</div><input class="upload" type="file" multiple="multiple" onchange="doUploadMessageFiles(this.files)" name="messageFiles">');
					        				$(".uploadnum").text("0");
					                    	isSendingMessage = false;
									    	if (data) {
									    		if (data.referencePage) {
													isReference = true;
													referencePage = data.referencePage;
													referenceArr = new Array();
													var j=0;
													for (var prop in referencePage) {
														  var map = new Map(); 
														  map.put(prop,referencePage[prop]);
														  referenceArr[j] = map;
														  j++;
													}
												}
												if (data.notePage) {
													notePage = data.notePage;
													noteArr = new Array();
													var k=0;
													for (var prop in notePage) {
														  var map = new Map(); 
														  map.put(prop,notePage[prop]);
														  noteArr[k] = map;
														  k++;
													}
												}
												var attachmentFile = data.attachmentFile;
												var jMessageFileHtml = $(messageFileHtml);
												
												if ("Y" == privateFlag) {
													var fileListHtml = "";
													if (attachmentFile) {
														for (var f=0;f<attachmentFile.length;f++) {
															var file = attachmentFile[f];
															var fileName = file.fileName;
															var filePath = file.filePath;
															if (fileName && filePath) {
																fileListHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#b3b3b3">'+fileName+'</a></span>';
															}
														}
													}
													jMessageFileHtml.find(".gabot2").before(fileListHtml);
												} else {
													var fileListHtml = "";
													if (attachmentFile) {
														for (var f=0;f<attachmentFile.length;f++) {
															var file = attachmentFile[f];
															var fileName = file.fileName;
															var filePath = file.filePath;
															if (fileName && filePath) {
																fileListHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#ffffff">'+fileName+'</a></span>';
															}
														}
													}
													if (jMessageFileHtml[1])
														$(jMessageFileHtml[1]).append(fileListHtml);
													else 
														$(jMessageFileHtml[0]).append(fileListHtml);
												}
												jQuery(".showk").append(jMessageFileHtml);
												jQuery("#invoice1").attr("id",type+data.id);
												if ("Y" == privateFlag) {
													jQuery("#note1").attr("id","note"+data.id);
												}
												jQuery(".showk").get(0).scrollTop = jQuery(".showk").get(0).scrollHeight;
									    	}
											getRecentChannelList();
					                    },
					                    error: function(XmlHttpRequest, textStatus, errorThrown){
					                        alert( "Network error!");
					                        isSendingMessage = false;
					                    }
					                });
								} else {
							jQuery.ajax( {  
							    url:window.location.protocol+"//" + window.location.host + "/ccmApp/message.do",// 跳转到 action  
							    data:{method:"insertGroupMessage",channelId: channelId,msgText:msgText,privateFlag:privateFlag,referenceTypeId:referenceTypeId,referenceId:referenceId,referenceNumber:referenceNumber},  
							    type:'post',  
							    dataType:'json',  
							    success:function(data) {  
							    	if (data == null) {
							    		jQuery("#" + errorSpanId).show(); 
										alert( "Network error!");
									} else {
							    	jQuery("#msgText").val("");
							    	if (data) {
							    		if (data.referencePage) {
											isReference = true;
											referencePage = data.referencePage;
											referenceArr = new Array();
											var j=0;
											for (var prop in referencePage) {
												  var map = new Map(); 
												  map.put(prop,referencePage[prop]);
												  referenceArr[j] = map;
												  j++;
											}
										}
										if (data.notePage) {
											notePage = data.notePage;
											noteArr = new Array();
											var k=0;
											for (var prop in notePage) {
												  var map = new Map(); 
												  map.put(prop,notePage[prop]);
												  noteArr[k] = map;
												  k++;
											}
										}
										jQuery("#invoice1").attr("id",type+data.id);
										if ("Y" == privateFlag) {
											jQuery("#note1").attr("id","note"+data.id);
										}
							    	}
									getRecentChannelList();
									}
							     },  
							     error : function(error) {  
							    	 jQuery("#" + errorSpanId).show(); 
							     }  
							});
							}
						}
						if (!invoiceChecked && !banChecked) {
							referenceId = "";
							referenceTypeId = "";
							referenceNumber = "";
							if (fileCount > 0) {
								  $("#msgChannelId").val(channelId);
								  $("#toUserId").val("");
								  $("#msgPrivateFlag").val(privateFlag);
								  $("#msgReferenceTypeId").val(referenceTypeId);
								  $("#msgReferenceId").val(referenceId);
								  $("#msgReferenceNumber").val(referenceNumber);
								  $("#msgReferenceKey").val(mergeId);
								  $("#uploadMessageForm").ajaxSubmit({
					                    success: function(data){
					                    	var resultHtml = data.replace('<head></head><body><pre style="word-wrap: break-word; white-space: pre-wrap;">','').replace('</pre></body>','');
					                    	data = JSON.parse(resultHtml);
					                    	jQuery("#msgText").val("");
					                    	$("#messageFileList").html("");
					                    	$(".numtips").hide();
					        				$(".uploadimg").html('<div class="text">Attach files</div><input class="upload" type="file" multiple="multiple" onchange="doUploadMessageFiles(this.files)" name="messageFiles">');
					        				$(".uploadnum").text("0");
					                    	isSendingMessage = false;
									    	if (data) {
									    		if (data.referencePage) {
													isReference = true;
													referencePage = data.referencePage;
													referenceArr = new Array();
													var j=0;
													for (var prop in referencePage) {
														  var map = new Map(); 
														  map.put(prop,referencePage[prop]);
														  referenceArr[j] = map;
														  j++;
													}
												}
												if (data.notePage) {
													notePage = data.notePage;
													noteArr = new Array();
													var k=0;
													for (var prop in notePage) {
														  var map = new Map(); 
														  map.put(prop,notePage[prop]);
														  noteArr[k] = map;
														  k++;
													}
												}
												var attachmentFile = data.attachmentFile;
												var jMessageFileHtml = $(messageFileHtml);
												
												if ("Y" == privateFlag) {
													var fileListHtml = "";
													if (attachmentFile) {
														for (var f=0;f<attachmentFile.length;f++) {
															var file = attachmentFile[f];
															var fileName = file.fileName;
															var filePath = file.filePath;
															if (fileName && filePath) {
																fileListHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#b3b3b3">'+fileName+'</a></span>';
															}
														}
													}
													jMessageFileHtml.find(".gabot2").before(fileListHtml);
												} else {
													var fileListHtml = "";
													if (attachmentFile) {
														for (var f=0;f<attachmentFile.length;f++) {
															var file = attachmentFile[f];
															var fileName = file.fileName;
															var filePath = file.filePath;
															if (fileName && filePath) {
																fileListHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#ffffff">'+fileName+'</a></span>';
															}
														}
													}
													if (jMessageFileHtml[1])
														$(jMessageFileHtml[1]).append(fileListHtml);
													else 
														$(jMessageFileHtml[0]).append(fileListHtml);
												}
												jQuery(".showk").append(jMessageFileHtml);
												jQuery("#invoice1").attr("id",type+data.id);
												if ("Y" == privateFlag) {
													jQuery("#note1").attr("id","note"+data.id);
												}
												jQuery(".showk").get(0).scrollTop = jQuery(".showk").get(0).scrollHeight;
									    	}
											getRecentChannelList();
					                    },
					                    error: function(XmlHttpRequest, textStatus, errorThrown){
					                        alert( "Network error!");
					                        isSendingMessage = false;
					                    }
					                });
								} else {
							jQuery.ajax( {  
							    url:window.location.protocol+"//" + window.location.host + "/ccmApp/message.do",// 跳转到 action  
							    data:{method:"insertGroupMessage",channelId: channelId,msgText:msgText,privateFlag:privateFlag,referenceTypeId:referenceTypeId,referenceId:referenceId,referenceNumber:referenceNumber},  
							    type:'post',  
							    dataType:'json',  
							    success:function(data) {  
							    	if (data == null) {
							    		jQuery("#" + errorSpanId).show(); 
										alert( "Network error!");
									} else {
							    	jQuery("#msgText").val("");
							    	if (data) {
							    		if (data.referencePage) {
											isReference = true;
											referencePage = data.referencePage;
											referenceArr = new Array();
											var j=0;
											for (var prop in referencePage) {
												  var map = new Map(); 
												  map.put(prop,referencePage[prop]);
												  referenceArr[j] = map;
												  j++;
											}
										}
										if (data.notePage) {
											notePage = data.notePage;
											noteArr = new Array();
											var k=0;
											for (var prop in notePage) {
												  var map = new Map(); 
												  map.put(prop,notePage[prop]);
												  noteArr[k] = map;
												  k++;
											}
										}
										jQuery("#invoice1").attr("id",type+data.id);
										if ("Y" == privateFlag) {
											jQuery("#note1").attr("id","note"+data.id);
										}
							    	}
									getRecentChannelList();
									}
							     },  
							     error : function(error) {  
							    	 jQuery("#" + errorSpanId).show(); 
							     }  
							});
							}
						}
						if (fileCount == 0)
							jQuery(".showk").append(listHtml);
						else
							messageFileHtml = listHtml;
						}
						jQuery("#linkToInvoiceCheck").removeAttr("checked");
						jQuery("#linkToBanCheck").removeAttr("checked");
						jQuery(".showk").get(0).scrollTop = jQuery(".showk").get(0).scrollHeight;
		//				jQuery("#msgText").val("");
						return;
					}
					if (fileCount == 0)
						jQuery(".showk").append(listHtml);
					else
						messageFileHtml = listHtml;
					if (!invoiceChecked) {
						referenceId = "";
						referenceTypeId = "";
						referenceNumber = "";
					}
					if (fileCount > 0) {
						  $("#msgChannelId").val(channelId);
						  $("#toUserId").val("");
						  $("#msgPrivateFlag").val(privateFlag);
						  $("#msgReferenceTypeId").val(referenceTypeId);
						  $("#msgReferenceId").val(referenceId);
						  $("#msgReferenceNumber").val(referenceNumber);
						  $("#msgReferenceKey").val(mergeId);
						  $("#uploadMessageForm").ajaxSubmit({
			                    success: function(data){
			                    	var resultHtml = data.replace('<head></head><body><pre style="word-wrap: break-word; white-space: pre-wrap;">','').replace('</pre></body>','');
			                    	data = JSON.parse(resultHtml);
			                    	jQuery("#msgText").val("");
			                    	$("#messageFileList").html("");
			                    	$(".numtips").hide();
			        				$(".uploadimg").html('<div class="text">Attach files</div><input class="upload" type="file" multiple="multiple" onchange="doUploadMessageFiles(this.files)" name="messageFiles">');
			        				$(".uploadnum").text("0");
			                    	isSendingMessage = false;
							    	if (data) {
							    		if (data.referencePage) {
											isReference = true;
											referencePage = data.referencePage;
											referenceArr = new Array();
											var j=0;
											for (var prop in referencePage) {
												  var map = new Map(); 
												  map.put(prop,referencePage[prop]);
												  referenceArr[j] = map;
												  j++;
											}
										}
										if (data.notePage) {
											notePage = data.notePage;
											noteArr = new Array();
											var k=0;
											for (var prop in notePage) {
												  var map = new Map(); 
												  map.put(prop,notePage[prop]);
												  noteArr[k] = map;
												  k++;
											}
										}
										var attachmentFile = data.attachmentFile;
										var jMessageFileHtml = $(messageFileHtml);
										
										if ("Y" == privateFlag) {
											var fileListHtml = "";
											if (attachmentFile) {
												for (var f=0;f<attachmentFile.length;f++) {
													var file = attachmentFile[f];
													var fileName = file.fileName;
													var filePath = file.filePath;
													if (fileName && filePath) {
														fileListHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#b3b3b3">'+fileName+'</a></span>';
													}
												}
											}
											jMessageFileHtml.find(".gabot2").before(fileListHtml);
										} else {
											var fileListHtml = "";
											if (attachmentFile) {
												for (var f=0;f<attachmentFile.length;f++) {
													var file = attachmentFile[f];
													var fileName = file.fileName;
													var filePath = file.filePath;
													if (fileName && filePath) {
														fileListHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" target="_blank" style="color:#ffffff">'+fileName+'</a></span>';
													}
												}
											}
											if (jMessageFileHtml[1])
												$(jMessageFileHtml[1]).append(fileListHtml);
											else 
												$(jMessageFileHtml[0]).append(fileListHtml);
										}
										jQuery(".showk").append(jMessageFileHtml);
										jQuery("#invoice1").attr("id",type+data.id);
										if ("Y" == privateFlag) {
											jQuery("#note1").attr("id","note"+data.id);
										}
										jQuery(".showk").get(0).scrollTop = jQuery(".showk").get(0).scrollHeight;
							    	}
									getRecentChannelList();
			                    },
			                    error: function(XmlHttpRequest, textStatus, errorThrown){
			                        alert( "Network error!");
			                        isSendingMessage = false;
			                    }
			                });
						//  $("#uploadMessageForm").submit();
						} else {
					jQuery.ajax( {  
					    url:window.location.protocol+"//" + window.location.host + "/ccmApp/message.do",// 跳转到 action  
					    data:{method:"insertGroupMessage",channelId: channelId,msgText:msgText,privateFlag:privateFlag,referenceTypeId:referenceTypeId,referenceId:referenceId,referenceNumber:referenceNumber},  
					    type:'post',  
					    dataType:'json',  
					    success:function(data) {  
					    	if (data == null) {
					    		jQuery("#" + errorSpanId).show(); 
								alert( "Network error!");
							} else {
					    	jQuery("#msgText").val("");
					    	if (data) {
					    		if (data.referencePage) {
									isReference = true;
									referencePage = data.referencePage;
									referenceArr = new Array();
									var j=0;
									for (var prop in referencePage) {
										  var map = new Map(); 
										  map.put(prop,referencePage[prop]);
										  referenceArr[j] = map;
										  j++;
									}
								}
								if (data.notePage) {
									notePage = data.notePage;
									noteArr = new Array();
									var k=0;
									for (var prop in notePage) {
										  var map = new Map(); 
										  map.put(prop,notePage[prop]);
										  noteArr[k] = map;
										  k++;
									}
								}
								jQuery("#invoice1").attr("id",type+data.id);
								if ("Y" == privateFlag) {
									jQuery("#note1").attr("id","note"+data.id);
								}
					    	}
							getRecentChannelList();
							}
					     },  
					     error : function(error) {  
					    	 jQuery("#" + errorSpanId).show(); 
					     }  
					});	
					}
					
				}
			//	jQuery("#msgText").val("");
				jQuery("#linkToInvoiceCheck").removeAttr("checked");
				jQuery("#linkToBanCheck").removeAttr("checked");
				jQuery(".showk").get(0).scrollTop = jQuery(".showk").get(0).scrollHeight;
				jQuery("#msgText").focus();
				
			}

			function closeChatPanel() {
				jQuery("#channelTypeId").val("");
				jQuery("#userId").val("");
				jQuery("#channelId").val("");
				jQuery("#chatPanel").hide();
				jQuery("#groupMember").hide();
			}

			function hideMessagePanel() {
				jQuery(".zhankai001").hide();
				jQuery("#chatPanel").hide();
				jQuery("#groupMember").hide();
				jQuery("#modifyMember").hide();
				jQuery.get(window.location.protocol+"//" + window.location.host + "/ccmApp/message.do?method=findUnreadMessageCount",function(data){
					if (data > 0) {
						jQuery("#haveMessageTray").show();
					} else {
						jQuery("#noMessageTray").show();
					}
				});	
			}

			function searchKeyword(item) {
				if (jQuery(item).val() == "") {
					jQuery(".zuocezu ul").html(jQuery(".zuocezu2 ul").html());
				} else {
					var html = "";
					jQuery.each(jQuery(".zuocezu2 ul li"),function(i,it){
						var thisTxt = jQuery(it).text().toUpperCase();
						var txt = jQuery(item).val().toUpperCase();
						if (thisTxt.startWith(txt)) {
							html+= jQuery(it)[0].outerHTML;
						}
					});
					jQuery(".zuocezu ul").html(html);
				}
			}
			
			function searchUserKeyword(item) {
				if (jQuery(item).val() == "") {
					jQuery("#contactList").html(jQuery("#hideContactList").html());
				} else {
					var html = "";
					jQuery.each(jQuery("#hideContactList li"),function(i,it){
						var thisTxt = jQuery(it).text().toUpperCase();
						var txt = jQuery(item).val().toUpperCase();
						if (thisTxt.startWith(txt)) {
							html+= jQuery(it)[0].outerHTML;
						}
					});
					jQuery("#contactList").html(html);
				}
			}

			function addToMember(item) {
				var id = "t" + item.id;
				var has = false;
				jQuery.each(jQuery(".youcezu ul li"),function(i,it){
					if (jQuery(it)[0].id == id || jQuery(it)[0].id == item.id) {
						has = true;
					}
				});
				if (!has) {
					jQuery(".youcezu ul").append("<li onmouseover=\"mouseOver(this)\" onmouseout=\"mouseOut(this)\" id=\"t" + item.id + "\">" + jQuery(item).text() + "<span class=\"chahaoss\" onclick=\"removeMember(this)\">X</span></li>");
					jQuery("#currentCount").text(parseInt(jQuery("#currentCount").text(),10)+1);
				}
			}

			function mouseOver(item) {
				jQuery(item).children(".chahaoss").show();
			}
			
			function mouseOut(item) {
				jQuery(item).children(".chahaoss").hide();
			}

			function removeMember(item) {
				jQuery(item).parent().remove();
				jQuery("#currentCount").text(parseInt(jQuery("#currentCount").text(),10)-1);
			}

			function removeAllMember() {
				jQuery(".youcezu ul").html("");
				jQuery("#currentCount").text(0);
			}

			function addAllMember() {
				var html = "";
				jQuery.each(jQuery(".zuocezu2 ul li"),function(i,it){
					html+= jQuery(it)[0].outerHTML;
				});
				jQuery("#currentCount").text(jQuery("#totalCount").text());
				jQuery(".youcezu ul").html(html);
			}

			function toHiddenPanel() {
				queryHiddenGroupList();
				jQuery("#hiddenGroupPanel").show();
				$("#panelList3").css("overflow-y","hidden");
				$("#panelList3").animate({ marginLeft: '-180px' }, "slow");
				$("#hiddenGroupPanel").animate({ marginLeft: '0' }, "slow",function(){
					$("#hiddenGroupPanel").css("overflow-y","auto");
				});
			}

			function back() {
				getGroupList();
				$("#panelList3").animate({ marginLeft: '0' }, "slow",function(){
					$("#panelList3").css("overflow-y","auto");
				});
				$("#hiddenGroupPanel").animate({ marginLeft: '180' }, "slow");
			}
			
			Command.username = '<%=SystemUtil.getCurrentUserId() %>';
			Command.password = '<%=SystemUtil.getCurrentUserId() %>';
			Command.ContextPath = '<%=request.getContextPath() %>';
           	Command.init();
           	var isReference = false;
           	var referenceKey = "";
           	var lastMsg = "";
           	
            Command.receive = function(cmd) {
				var channelTypeId = jQuery("#channelTypeId").val();
				var userId = jQuery("#userId").val();
				var channelId = jQuery("#channelId").val();
				if (!fromHtml) fromHtml = "";
				if (cmd.privateFlag) {
	                if (channelTypeId == cmd.channelTypeId && channelTypeId == "1") {
						if (userId == cmd.fromUserId) {
							var fromUser = cmd.fromUserName;
						    var fromValue = cmd.msgText;
						    var messageId = cmd.messageId;
						    var referenceId = cmd.referenceId;
						    var referenceTypeId = cmd.referenceTypeId;
						    var referenceNumber = cmd.referenceNumber;
						    var attachmentFile = cmd.attachmentFile;
						    if (referenceTypeId) {
								var type = "Invoice", url = "#";
								if (referenceTypeId == 1) {
									type = "Invoice";
									url = "<%=request.getContextPath() %>" + "/viewInvoiceDetails.action?invoiceId=" + referenceId + "#NAME_invoiceNote";
								}
								else if (referenceTypeId == 2) {
									type = "BAN";
									url = "<%=request.getContextPath() %>" + "/banDetailAction.action?bvo.banId=" + referenceId + "#NAME_tiffPage";
								}
								else if (referenceTypeId == 3) {
									type = "Dispute";
									url = "<%=request.getContextPath() %>" + "/viewDisputeDetails.action?disputeId=" + referenceId + "#NAME_TransactionHistoryListPage";
								}
								else if (referenceTypeId == 4) {
									type = "Vendor";
									
								}
								else if (referenceTypeId == 5) {
									type = "Circuit";
									url = "<%=request.getContextPath() %>" + "/showCircuitDetail.action?vendorCircuitId=" + referenceId + "#NAME_scoaPage";
								}
								else if (referenceTypeId == 6) {
									type = "User";
									url = "<%=request.getContextPath() %>" + "/securityManagement.action?userId=" + referenceId + "#NAME_previledgeVendorBanPage";
								}
								
								var toValue = referenceNumber;
								if (!isReference)
									fromHtml = "<div class=\"green2 ty2\"> <span class=\"grtop2\">" + fromUser + "</span> <span class=\"grbot2\">" + fromValue + "</span>";
								fromHtml+= "<span id=\"" + type + messageId + "\" class=\"blbot2\" style=\"color:red;border-top:1px solid #e6e6e6\">" + type + ":<a style=\"color:red;\" href=\"javascript:toHrefPage(" + referenceTypeId + "," + referenceId + ")\">" + toValue + "</a></span> ";
								if (cmd.referenceKey && !isReference) {} else {
									if (attachmentFile) {
										attachmentFile = JSON.parse(attachmentFile); 
										for (var f=0;f<attachmentFile.length;f++) {
											var file = attachmentFile[f];
											var fileName = file.fileName;
											var filePath = file.filePath;
											if (fileName && filePath) {
												fromHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" style="color:#333">'+fileName+'</a></span>';
											}
										}
									}
									fromHtml+= "</div>";
								}
								
								if (cmd.referenceKey) {
									if (referenceKey == cmd.referenceKey) {
										fromHtml = lastMsg + fromHtml;
										isReference = false;
										jQuery(".showk").append(fromHtml);
									} else {
										referenceKey = cmd.referenceKey;
										lastMsg = fromHtml;
										isReference = true;
									}
								} else {
									jQuery(".showk").append(fromHtml);
								}
								
							} else {
								fromHtml = "<div class=\"green2 ty2\"> <span class=\"grtop2\">" + fromUser + "</span> <span class=\"grbot2\">" + fromValue + "</span> ";
								if (attachmentFile) {
									attachmentFile = JSON.parse(attachmentFile); 
									for (var f=0;f<attachmentFile.length;f++) {
										var file = attachmentFile[f];
										var fileName = file.fileName;
										var filePath = file.filePath;
										if (fileName && filePath) {
											fromHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" style="color:#333">'+fileName+'</a></span>';
										}
									}
								}
								fromHtml+= "</div>";
								jQuery(".showk").append(fromHtml);
							}
						    jQuery.get(window.location.protocol+"//" + window.location.host + "/ccmApp/message.do?method=updateFriendUnreadStatus&toUserId=" + userId,function(){
								getRecentChannelList();
							});
						}
	                } else {
	                	if (channelId == cmd.channelId) {
							var fromUser = cmd.fromUserName;
						    var fromValue = cmd.msgText;
						    var messageId = cmd.messageId;
						    var referenceId = cmd.referenceId;
						    var referenceTypeId = cmd.referenceTypeId;
						    var referenceNumber = cmd.referenceNumber;
						    var attachmentFile = cmd.attachmentFile;
						    if (referenceTypeId) {
								var type = "Invoice", url = "#";
								if (referenceTypeId == 1) {
									type = "Invoice";
									url = "<%=request.getContextPath() %>" + "/viewInvoiceDetails.action?invoiceId=" + referenceId + "#NAME_invoiceNote";
								}
								else if (referenceTypeId == 2) {
									type = "BAN";
									url = "<%=request.getContextPath() %>" + "/banDetailAction.action?bvo.banId=" + referenceId + "#NAME_tiffPage";
								}
								else if (referenceTypeId == 3) {
									type = "Dispute";
									url = "<%=request.getContextPath() %>" + "/viewDisputeDetails.action?disputeId=" + referenceId + "#NAME_TransactionHistoryListPage";
								}
								else if (referenceTypeId == 4) {
									type = "Vendor";
									
								}
								else if (referenceTypeId == 5) {
									type = "Circuit";
									url = "<%=request.getContextPath() %>" + "/showCircuitDetail.action?vendorCircuitId=" + referenceId + "#NAME_scoaPage";
								}
								else if (referenceTypeId == 6) {
									type = "User";
									url = "<%=request.getContextPath() %>" + "/securityManagement.action?userId=" + referenceId + "#NAME_previledgeVendorBanPage";
								}
								var toValue = referenceNumber;
								if (!isReference)
								  fromHtml = "<div class=\"green2 ty2\"> <span class=\"grtop2\">" + fromUser + "</span> <span class=\"grbot2\">" + fromValue + "</span>";
								fromHtml+= "<span id=\"" + type + messageId + "\" class=\"blbot2\" style=\"color:red;border-top:1px solid #e6e6e6\">" + type + ":<a style=\"color:red;\" href=\"javascript:toHrefPage(" + referenceTypeId + "," + referenceId + ")\">" + toValue + "</a></span>";
								if (cmd.referenceKey && !isReference) {} else {
									if (attachmentFile) {
										attachmentFile = JSON.parse(attachmentFile); 
										for (var f=0;f<attachmentFile.length;f++) {
											var file = attachmentFile[f];
											var fileName = file.fileName;
											var filePath = file.filePath;
											if (fileName && filePath) {
												fromHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" style="color:#333">'+fileName+'</a></span>';
											}
										}
									}
									fromHtml+= "</div>";
								}
									
								if (cmd.referenceKey) {
									if (referenceKey == cmd.referenceKey) {
										fromHtml = lastMsg + fromHtml;
										isReference = false;
										jQuery(".showk").append(fromHtml);
									} else {
										referenceKey = cmd.referenceKey;
										lastMsg = fromHtml;
										isReference = true;
									}
								} else {
									jQuery(".showk").append(fromHtml);
								}
							} else {
								var fromHtml = "<div class=\"green2 ty2\"> <span class=\"grtop2\">" + fromUser + "</span> <span class=\"grbot2\">" + fromValue + "</span>";

								if (attachmentFile) {
									attachmentFile = JSON.parse(attachmentFile); 
									for (var f=0;f<attachmentFile.length;f++) {
										var file = attachmentFile[f];
										var fileName = file.fileName;
										var filePath = file.filePath;
										if (fileName && filePath) {
											fromHtml+='<span class="blfiles"><a href="javascript:openFileUrl(\'/ccmApp/'+filePath+'\')" style="color:#333">'+fileName+'</a></span>';
										}
									}
								}
								fromHtml+= " </div>";
								jQuery(".showk").append(fromHtml);
							}
							jQuery.get(window.location.protocol+"//" + window.location.host + "/ccmApp/message.do?method=updateGroupUnreadStatus&channelId=" + channelId,function(){
								getRecentChannelList();
							});
						}
	                }
	                if (jQuery("#haveMessageTray").is(":hidden")) {
	                	jQuery("#haveMessageTray").show();
	                }
	                var scrollHeight = (jQuery(".showk")[0].scrollHeight-320) - jQuery(".showk").scrollTop();
	                if (scrollHeight < 200) {
	                	jQuery(".showk").get(0).scrollTop = jQuery(".showk").get(0).scrollHeight;
	                }
	                getRecentChannelList();
				} else {
					if (channelTypeId == cmd.channelTypeId == 1) {
						if (userId == cmd.fromUserId) {
							var fromUser = cmd.fromUserName;
						    var fromValue = cmd.msgText;
							var fromHtml = "<div class=\"green2 ty2\"> <span class=\"grtop2\">" + fromUser + "</span> <span class=\"grbot2\">" + fromValue + "</span> </div>";
							jQuery(".showk").append(fromHtml);
							jQuery.get(window.location.protocol+"//" + window.location.host + "/ccmApp/message.do?method=updateFriendUnreadStatus&toUserId=" + userId,function(){
								getRecentChannelList();
							});
						}
	                } else {
	                	if (channelId == cmd.channelId) {
							var fromUser = cmd.fromUserName;
						    var fromValue = cmd.msgText;
							var fromHtml = "<div class=\"green2 ty2\"> <span class=\"grtop2\">" + fromUser + "</span> <span class=\"grbot2\">" + fromValue + "</span> </div>";
							jQuery(".showk").append(fromHtml);
							jQuery.get(window.location.protocol+"//" + window.location.host + "/ccmApp/message.do?method=updateGroupUnreadStatus&channelId=" + channelId,function(){
								getRecentChannelList();
							});
						}
	                }
	                if (jQuery("#haveMessageTray").is(":hidden")) {
	                	jQuery("#haveMessageTray").show();
	                }
	                var scrollHeight = (jQuery(".showk")[0].scrollHeight-320) - jQuery(".showk").scrollTop();
	                if (scrollHeight < 200) {
	                	jQuery(".showk").get(0).scrollTop = jQuery(".showk").get(0).scrollHeight;
	                }
	                getRecentChannelList();
					getGroupList();
				}
	            console.debug(cmd.channelId);
				console.debug(cmd.msgText);
				
            	//alert(message);
            }

            // 显示文件漂浮框
            function showFileTips(item) {
				if ($(".numtips").is(":hidden") && parseInt($(item).text(),10)>0) {
					$(".numtips").show();
				} else 
					$(".numtips").hide();
            }

            function doUploadMessageFiles(files){
                
				//$("#messageFileList").html("");
				var html = "";
            	for( var i = 0; i < files.length; i++) {
            		var file = files[i];
            		//1:invoice detail notes window ; 2:approve notes window
            		var fileSize = file.size/1024/1024;
            		var fileName = file.name;
            		var fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
            		var type = ["gif","jpg","jpeg","bmp","doc","ppt","xls","docx","pptx","xlsx","txt","png","pdf","tif"];
					var typeBoo = 0;
           		    for (var j = 0; j < type.length; j++) {
        		                if (fileExtension == type[j]) {
        		                    typeBoo++;
        		                }
           		       }
        		    if (typeBoo == 0) {
        		    	html+= '<div class="filesrow"><span class="icoWarning" title="This document type can not be allowed!"></span><span class="filename" title="'+file.name+'">'+file.name+'</span><span class="filedel" onclick="removeMessageFile(this)">x</span><input name="effectiveFile" type="hidden" value="'+file.name+':'+file.size+'"/></div><input name="uploadMessageFiles" type="hidden" value="'+file.name+'"/>';
        		    	continue;
        		    }
            		if (fileSize > 30) {
            			html+= '<div class="filesrow"><span class="icoWarning" title="Document size can not be more than 30MB!"></span><span class="filename" title="'+file.name+'">'+file.name+'</span><span class="filedel" onclick="removeMessageFile(this)">x</span><input name="effectiveFile" type="hidden" value="'+file.name+':'+file.size+'"/></div><input name="uploadMessageFiles" type="hidden" value="'+file.name+'"/>';
        		    	continue;
            		}
            		html+= '<div class="filesrow"><span class="icoMessage"></span><span class="filename" title="'+file.name+'">'+file.name+'</span><span class="filedel" onclick="removeMessageFile(this)">x</span><input name="effectiveFile" type="hidden" value="'+file.name+':'+file.size+'"/></div><input name="uploadMessageFiles" type="hidden" value="'+file.name+'"/>';
            	}
            	$("#messageFileList").append(html);
            	$(".uploadnum").text(parseInt($(".uploadnum").text(),10)+files.length);
            	$(".uploadimg .upload").hide();
            	$(".uploadimg").append('<input class="upload" type="file" multiple="multiple" onchange="doUploadMessageFiles(this.files)" name="messageFiles">');
            }

            function openFileUrl(url) {
                var arr = url.split("/");
                var url = url.substring(0,url.lastIndexOf("/")) + "/" + encodeURIComponent(arr[arr.length-1]);
                location.href = "/ccmApp/message.do?method=downloadFiles&url=" + url;
            }

            function removeMessageFile(item) {
                $(item).parent().remove();
				var count = $("#messageFileList").find(".filedel").length;
				if (count == 0) {
					$(".numtips").hide();
					$(".uploadimg").html('<div class="text">Attach files</div><input class="upload" type="file" multiple="multiple" onchange="doUploadMessageFiles(this.files)" name="messageFiles">');
				} else {
					var top = $(".numtips").position().top;
				//	jQuery(".numtips").css("top",top+24);
				}
				$(".uploadnum").text(count);
            }

            window.setInterval(getRecentChannelList, 1200000);  // 20分钟定时执行 


			// 对Date的扩展，将 Date 转化为指定格式的String
			// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
			// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
			// 例子： 
			// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
			// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
			Date.prototype.Format = function (fmt) { //author: meizz 
			    var o = {
			        "M+": this.getMonth() + 1, //月份 
			        "d+": this.getDate(), //日 
			        "h+": this.getHours(), //小时 
			        "m+": this.getMinutes(), //分 
			        "s+": this.getSeconds(), //秒 
			        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
			        "S": this.getMilliseconds() //毫秒 
			    };
			    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
			    for (var k in o)
			    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
			    return fmt;
			}

			function compare(propertyName) { 
				return function (object1, object2) { 
					var value1 = object1[propertyName]; 
					var value2 = object2[propertyName]; 
					if (value2 < value1) { 
						return 1; 
					} 
					else if (value2 > value1) { 
						return -1; 
					} 
					else { 
						return 0; 
						} 
					} 
				} 

			function checkAll(check) {
				if (check.checked) {
					jQuery("#modifyGroupMemberList input[type=checkbox]").attr("checked",true);
				}
				  else
					jQuery("#modifyGroupMemberList input[type=checkbox]").attr("checked",false);
			}

			jQuery.expr[':'].Contains = function(a, i, m) {
				  return jQuery(a).text().toUpperCase()
				      .indexOf(m[3].toUpperCase()) >= 0;
				};
				 
				jQuery.expr[':'].contains = function(a, i, m) {
				  return jQuery(a).text().toUpperCase()
				      .indexOf(m[3].toUpperCase()) >= 0;
				};


				String.prototype.startWith=function(str){
					  if(str==null||str==""||this.length==0||str.length>this.length)
					   return false;
					  if(this.substr(0,str.length)==str)
					     return true;
					  else
					     return false;
					  return true;
					 }

				// 对Date的扩展，将 Date 转化为指定格式的String
				// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
				// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
				// 例子： 
				// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
				// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
				Date.prototype.Format = function (fmt) { //author: meizz 
				    var o = {
				        "M+": this.getMonth() + 1, //月份 
				        "d+": this.getDate(), //日 
				        "h+": this.getHours(), //小时 
				        "m+": this.getMinutes(), //分 
				        "s+": this.getSeconds(), //秒 
				        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
				        "S": this.getMilliseconds() //毫秒 
				    };
				    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
				    for (var k in o)
				    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
				    return fmt;
				}
		</script>