<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.saninco.ccm.util.SystemUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%  
	pageContext.setAttribute("currentUserThemeId",SystemUtil.getCurrentUserThemeId());
	pageContext.setAttribute("currentUserAuthorities",SystemUtil.getCurrentUserAuthorities());
 %>
<html>
	<head>
		<title>TEMS - Search </title>
		<script type="text/javascript">
			if(typeof SANINCO != 'object'){ SANINCO={}; }
			SANINCO.authorities = "";
		<c:forEach items="${currentUserAuthorities}" var="authoritie">
			if(SANINCO.authorities.length==0){
				SANINCO.authorities += "${authoritie}";
			}else{
				SANINCO.authorities += ",${authoritie}";
			}
		</c:forEach>

		</script>
		<c:forEach items="${CCM_SYSTEM_THEMES}" var="the">
			<c:if test="${currentUserThemeId == the.key}">
		<link rel="stylesheet" type="text/css" href="themes/${the.value}/all.css" title="ccm_theme_link_20100716_${the.key}"/>
			</c:if>
		</c:forEach>
		<link rel="stylesheet" type="text/css" href="include/css/ccm.css" />
		<LINK href="include/javascript/yui2/fonts-min.css" type=text/css rel=stylesheet>
		<LINK href="include/javascript/yui2/button.css" type=text/css rel=stylesheet>
		<LINK href="include/javascript/yui2/container.css" type=text/css rel=stylesheet>
		<LINK href="include/css/message.css" type=text/css rel=stylesheet>
		
		<style>img{behavior:url(include/css/ie_bug.htc);}</style>
		<script type="text/javascript"> var CONTEXT_PATH = ""; </script>
		<!-- yui lib -->
		<script type="text/javascript" src="include/javascript/yui2/yahoo.js"></script>
		<script type="text/javascript" src="include/javascript/yui2/yahoo-dom-event.js"></script>
		<script type="text/javascript" src="include/javascript/yui2/jsvalidate.js"></script>
		<script type="text/javascript" src="include/javascript/yui2/connection.js"></script>
		<script type="text/javascript" src="include/javascript/yui2/selector.js"></script>
		<SCRIPT type="text/javascript" src="include/javascript/yui2/element-min.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="include/javascript/yui2/button-min.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="include/javascript/yui2/dragdrop-min.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="include/javascript/yui2/container-min.js"></SCRIPT>
		<!-- ccm lib -->
		<script type="text/javascript" src="include/site.js"></script>
		<script type="text/javascript" src="include/javascript/menu.js"></script>
		<script type="text/javascript" src="include/javascript/cookie.js"></script>
		<script type="text/javascript" src="include/javascript/jquery/jquery-1.4.2.min.js"></script>
		
<%--		<script type="text/javascript" src="include/javascript/jquery/jquery.json-2.4.min.js"></script>--%>
		<script type="text/javascript" src="include/javascript/constants.js"></script>
		<script type="text/javascript" src="include/javascript/ccm/common.js"></script>
		<!-- saninco lib  -->
		<script type="text/javascript" src="include/javascript/saninco/authoritie.js"></script>
		<script type="text/javascript" src="include/javascript/saninco/page.js"></script>
		<script type="text/javascript" src="include/javascript/saninco/filter.js"></script>
		<script type="text/javascript" src="include/javascript/saninco/downLoad.js"></script>
		<!-- saninco lib plug in -->
		<script type="text/javascript" src="include/javascript/saninco/array_Plug_In.js"></script>
		
		<!-- openfire lib -->
		<script type="text/javascript" src="include/javascript/openfire/jsjac/js/Command.js"></script>
        <script type="text/javascript" src="include/javascript/openfire/jsjac/js/JSJaC.js"></script>
        <script type="text/javascript" src="include/javascript/ccm/jsMap.js"></script>
        <!-- contextmenu lib -->
        <script type="text/javascript" src="include/javascript/contextmenu/jquery.contextmenu.r2.js"></script>
        
        <!-- ajaxfileupload -->
        <script type="text/javascript" src="include/javascript/jquery/jquery.form.js"></script>
        
		
		<script type="text/javascript">
			YAHOO.util.Event.onDOMReady(function () {
				var doms = jQuery("#tabRow_s a[class=middle]");
				var doms_first = jQuery("#tabRow_s a[class=middle first_a]"); //it's last tab css
				var f = 0;
				if(doms_first.length==1 && window.location.href.indexOf(doms_first[0].href)!=-1){ //it's last tab css
					YAHOO.util.Dom.removeClass(doms_first[0], 'first_a');
					YAHOO.util.Dom.addClass(doms_first[0], 'select');
				}else{
					for(var i=0;(i<=doms.length && f==0);i++){
						if(window.location.href.indexOf(doms[i].href)!=-1){
							 // YAHOO.util.Dom.addClass(doms[i], 'aOtherTabHover' );
							 YAHOO.util.Dom.addClass(doms[i], 'select');
							f++;
						}
						if(doms[i].href.indexOf('searchAdminAction.action')!=-1 && window.location.href.indexOf('securityManagement.action')!=-1){
							// YAHOO.util.Dom.addClass(doms[i], 'aOtherTabHover' );
							YAHOO.util.Dom.addClass(doms[i], "select");
							f++;
						}
						if(doms[i].href.indexOf('searchAdminAction.action')!=-1 && window.location.href.indexOf('banDetailAction.action')!=-1){
							// YAHOO.util.Dom.addClass(doms[i], 'aOtherTabHover' );
							YAHOO.util.Dom.addClass(doms[i], "select");
							f++;
						}
					};
				}
			});
		</script>
	</head>
	<body style="position: relative;top: 0;left: 0;">
	<div class="table_min">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td id="header" width="100%">
					<tiles:insertAttribute name="header" />
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td valign="top" style="width: 10px;" class="hideShow">
								<img style="cursor: pointer; cursor: hand;" id="HideHandle" name="HideHandle" 
								alt="" onclick='hideLeftCol("leftCol");'>
							</td>
							<td id="left" valign="top" style="width: 160px; padding-top:7px" class="leftColumn">
								<div style="display: none;" id="leftCol">
									<tiles:insertAttribute name="leftPanel" />
								</div>
							</td>
							<td id="main">
								<tiles:insertAttribute name="mainPanel" />
							</td>
						</tr>
					</table>
				</td>
			</tr>		
		</table>
		
		<table cellpadding='8' cellspacing='8' width='100%' border='0' class='underFooter'>
<!--			<tr>-->
<!--				<td align="center">-->
<!--				</td>-->
<!--			</tr>-->
			<tr>
				<td align='center' class='copyRight' id="ccm_copyRight_td">
					
				</td>
			</tr>
			<tr>
				<td height="2"></td>
		  </tr>
		</table>
		</div>
		<div class="xinfeng001 tray posdiy" id="noMessageTray" onclick="showMessagePanel(false)" title="Message" style="background-color: #ffffff;display: none;cursor: pointer;"></div>
		
		
		<div class="xinfeng002 tray posdiy" id="haveMessageTray" onclick="showMessagePanel(true)" style="background-color: #ffffff;cursor: pointer;display: none;">New Message</div>
		
		<div id="groupMember" class="zhankai003 memeber" style="display: none;">
		  <div class="zk3ttit">
		  Group Member<span onclick="closeGroupMember()" class="close2 textino">X</span>
		  </div>
			<div class="zk3ccon"><ul id="groupMemberPanel"></ul></div>
		</div>
		
		<div class="zhankai001" style="display: none;">
		  <ul class="zktop1">
		    <li class="ali1 on1" title="Conversations"></li>
		    <li class="ali2 off1" title="Contacts"></li>
		    <li class="ali3 off1" title="Groups"></li>
		  </ul>
		  <div class="zkmid1" id="panelList1">
		    <div class="groupone" style="margin-top:15px;display: none;">
		      <div class="titgro do3">Reference</div>
		      </div>
		    <div class="groupone">
		      <div class="titgro do2">Recent</div>
		      <ul id="recentChannelList">
		      </ul>
		    </div>
		  </div>
		  <div class="lfsearch2" style="display: none;"> <span class="sousuoss"></span>
        <input placeholder="Search" class="sousuoinp2" onkeyup="searchUserKeyword(this)">
      </div>
		  <div class="zkmid1" id="panelList2" style="display: none;height: 360px">
		    <ul id="contactList">
		    </ul>
		    <ul id="hideContactList" style="display: none;">
		    </ul>
		  </div>
		  <div class="zkmid1" id="panelList3" style="display: none;">
		  <div>
		    <div class="addgro" onclick="showAddDiscussionGroup()">+ Add Discussion Group</div><span class="mo hidebtn" title="Show hidden group" onclick="toHiddenPanel()" style="float: right;margin-top:-22px"></span></div>
		    <ul id="groupChannelList">
		    </ul>
		    <div class="contextMenu" id="menu" style="display: none;"> 
            <ul> 
                <li id="hide">Hide this group</li> 
            </ul> 
        	</div> 
		    <div class="contextMenu" id="showmenu" style="display: none;"> 
            <ul> 
                <li id="show">Show this group</li> 
            </ul> 
        	</div> 
		  </div>
		  <div class="zkmid1" id="hiddenGroupPanel" style="display: none;margin-left: 180px;margin-top:-400px">
		  <div>
		    <div class="hidetitle">Hidden Group</div><span class="mo backbtn" title="Back" onclick="back()" style="float: right;width: 45px;margin-top:-22px"></span></div>
		    <ul id="hideGroupChannelList">
		    </ul>
		  </div>
		  <div class="zkmid12" id="panelList4" style="display: none;">
		    <div class="tubut"><div class="radcheckg22" style="margin-left:11px;margin-top:5px;">
			<input type="checkbox" onchange="checkAll(this)" id="checkAll" name="">
			<label for="checkAll"></label>
			</div>
			<span class="cancno" onclick="cancelModifyGroupMember()">Cancel</span><span class="confirno"  onclick="doModifyGroupMember()">Confirm</span></div>
		    <ul id="modifyGroupMemberList">
		    </ul>
		  </div>
				  	  	  
		  <div class="zkbot1"><span onclick="hideMessagePanel()"></span></div>
		</div>
		
		<div class="zhankai002" id="modifyMember" style="display: none;">
		  <div class="zktop2"><span class="name22">+ Add Discussion Group</span><span onclick="hideModifyMember()" class="close2">X</span></div>
		  <div class="showkss">
		    <div class="zhjleft">
		      <div class="lfsearch"> <span class="sousuoss"></span>
		        <input class="sousuoinp" onkeyup="searchKeyword(this)" placeholder="Search" />
		      </div>
		      <div class="zuocezu">
		        <ul>
		        </ul>
		      </div>
		      <div class="zuocezu2" style="display: none;">
		        <ul>
		        </ul>
		      </div>
		    </div>
		    <div class="zhjiat"><span class="quxur" onclick="addAllMember()">&raquo;</span><span class="quxul" onclick="removeAllMember()">&laquo;</span></div>
		    <div class="zhjright">
		      <div class="rtselect">Selected:<span id="currentCount">0</span>/<span id="totalCount">0</span></div>
		      <div class="youcezu">
		        <ul>
		        </ul>
		      </div>
		    </div>
		  </div>
		  <!--showkss-->
		  
		  <div class="botsrk2"> 
		    
		    <!--  <div class="oneinput">
		       <span class="ccall">Check All</span> </div>-->
		    
		    <div class="yrbut"> <span class="cancels" onclick="hideModifyMember()">Cancel</span> <span class="send1" onclick="doModifyGroupMember()">Confirm</span> </div>
		  </div>
		</div>
		
		
		<div class="zhankai002" id="chatPanel" style="display: none;">
		  <form id="uploadMessageForm" action="/ccmApp/message.do?method=uploadMessageFiles" enctype="multipart/form-data" method="POST" target="uploadMessageFileFrame">
		  
		  <input type="hidden" name="channelId" id="msgChannelId">
		  <input type="hidden" name="toUserId" id="toUserId">
		  <input type="hidden" name="privateFlag" id="msgPrivateFlag">
		  <input type="hidden" name="referenceTypeId" id="msgReferenceTypeId">
		  <input type="hidden" name="referenceId" id="msgReferenceId">
		  <input type="hidden" name="referenceNumber" id="msgReferenceNumber">
		  <input type="hidden" name="referenceKey" id="msgReferenceKey">
		  
		  <div class="zktop2"><span class="name2">xin huang</span><div class="oknames"><div class="inpname"><input type="text" id="editGroupName" value="xin huang,lily,jenny"></div><div class="okbutt" onclick="doEditGroupName()">OK</div></div>
		    <div class="anniur"><span class="groupmember" title="View group member" onclick="showGroupMember()"></span> <span class="peradd" title="Edit group member" onclick="showModifyGroupMember()"></span> <span class="editss" title="Modify group name" onclick="showEditGroup()"></span> <span class="close2" onclick="closeChatPanel()">X</span></div>
  		  	<input type="hidden" id="channelId">
  		  	<input type="hidden" id="userId">
  		  	<input type="hidden" id="channelTypeId">
  		  	<input type="hidden" id="lastId">
  		  	<span class="close2 friendClose" onclick="closeChatPanel()">X</span>
  		  </div>
		  <div class="showk">
		  </div>

		  <div class="shuruk2">
		    <textarea id="msgText" name="msgContent" style="width:100%;height:80px;margin:0;"></textarea>
		  </div>
		  <div class="botsrk2">
		    <div class="zlin">
		      <div class="oneinput" id="linkToInvoice" style="display: none;">
		        <div class="radcheckg2">
		          <input type="checkbox" id="linkToInvoiceCheck" name="" />
		          <label for="linkToInvoiceCheck"></label>
		        </div>
		        <span id="linkToInvoiceLabel" class="youtext1">Link to this invoice</span> </div>
		      <div class="oneinput" id="linkToBan" style="display: none;">
		        <div class="radcheckg2">
		          <input type="checkbox" id="linkToBanCheck" name="" />
		          <label for="linkToBanCheck"></label>
		        </div>
		        <span class="youtext1">Link to this BAN</span> </div>
		    </div>
		    <div class="yrbut"> <span class="note1" id="sendNoteMessageBtn" onclick="sendMessage('Y')">Private Note</span> <span class="send1" id="sendMessageBtn" onclick="sendMessage('N')">Send</span> </div>
		  </div>
		  </form>
		</div>
		<iframe name="uploadMessageFileFrame" id="uploadMessageFileFrame" style="display: none" src="javascript:false"></iframe>
		
		<tiles:insertAttribute name="messagePanel" />
		<script type="text/javascript">
			jQuery(function(){
				var theFullYear = (new Date()).getFullYear();
				document.getElementById("ccm_copyRight_td").innerHTML = '&copy;&nbsp;' + (theFullYear-8) + "-" + theFullYear + '&nbsp' + '<s:text name="copyright.company.name" />';
			});			
		</script>

	</body>
</html>