<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.Enumeration"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String loginError = request.getParameter("login_error");
	String ip = request.getRemoteAddr();
%>

<html>
	<head id="Head1">
		<title>TEMS - Login - Page</title>
		<link rel="stylesheet" type="text/css" href="include/css/ccm.css" />

		<style type="text/css">
			html,body {
				height: 100%;
				overflow: auto;
			}
			
			body {
				padding: 0;
				margin: 0;
			}
			
			#silverlightControlHost {
				height: 100%;
				text-align: center;
			}
		</style>

		<script type="text/javascript" src="include/javascript/yui2/yahoo.js"></script>
		<script type="text/javascript" src="include/javascript/yui2/yahoo-dom-event.js"></script>
		<script type="text/javascript" src="include/javascript/yui2/connection.js"></script>
 		<script type="text/javascript" src="include/javascript/cookie.js"></script>
 		<script type="text/javascript" src="include/javascript/jquery/jquery-1.4.2.min.js"></script>
 		<script type="text/javascript" src="include/showWelcomePic.js"></script>
	</head>
	<body>
		<span style="display:none;">CCM_USER_SESSION_TIME_OUT_AJAX_FLAG</span>
		
		<div id="showPic" style="width:100%;height:100%;text-align: center;display: none;">
			<img name="slide" border="0" style="filter:blendTrans(duration=3);width:94%;height:90%;"/>
		</div>
		
		<div style="position: absolute; margin-left: 50%; bottom: 15%; width: 260px; border:1px solid #000; background-color: white;filter:alpha(opacity=80); opacity: 0.8;">
			<form method="post" action="j_spring_security_check" id="form1"
				name="form1" onsubmit="return loginValidation();">
				<table style="width: 240px; margin-left: 10px; color: Black; font-family: Verdana; font-size: Smaller; border-collapse: collapse;">
					<tr>
						<td colspan="2" style="padding-top:10px;">
								<strong >Login to Rogers TEMS </strong>
						</td>
					</tr>
					<tr >
						<td width="50%" style="padding-top:8px;">
							<s:text name="page.login.label.username" />
						</td>
						<td width="50%"style="padding-top:10px;">
							<INPUT id="j_username" type="text" name="j_username" style="width: 110px;" />
						</td>
					</tr>
					<tr >
						<td>
							<s:text name="page.login.label.password" />
						</td>
						<td>
							<INPUT id="j_password" type="password" name="j_password" style="width: 110px;" />
						</td>
					</tr>
<%--					<tr >--%>
<%--						<td width="50%" style="padding-top:8px;">--%>
<%--							 IP--%>
<%--						</td>--%>
<%--						<td width="50%"style="padding-top:10px;">--%>
<%--							<%=request.getRemoteAddr()%>--%>
<%--						</td>--%>
<%--					</tr>--%>
					<tr>
						<td style="padding-top:5px;" colspan="2" align="center">
							<INPUT class="button" type="submit" style="width: 100px;"
								value="<s:text name='button.submit.value'/>" onclick="saveOrDeleteCookie()" />
						</td>
					</tr>
					<tr>
						<td colspan="2" >
							<input id="_spring_security_remember_me" class="noBorderRadioButton" type="checkbox"/>
							<label for="_spring_security_remember_me">
								Remember me next time.
							</label>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="padding-top:5px;">
							<img src="include/images/left.gif" />
							&nbsp;
							<A class="homeInfoTable" href="pwdReset.action"><s:text
									name="page.login.link.forgot.password" /> </A>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="padding-top:5px;">
							<img src="include/images/left.gif" />
							&nbsp;
							<A class="homeInfoTable" href="/ccmApp/download.jsp"><s:text
									name="page.login.link.download_mobile_app" /> </A>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="padding-top:10px;">
							<DIV>
								<FONT id="errorInfo" color="red">
									<%
										if (loginError != null) {
								 			Exception ex = (Exception) session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
							 				if (ex != null && ex.getClass().getName().endsWith("LockedException")) {
								 	%>
								 		<s:text name="page.login.error.account.locked" /> 
								 	<%
							 				} else if(ex != null && ex.getClass().getName().endsWith("ActiveException")) {
								 	%>
								 		<s:text name="page.login.error.active.locked" /> 
								 	<%
							 				} else if(ex != null && ex.getClass().getName().endsWith("IPSecurityException")) {
								 	%>
								 		<s:text name="page.login.error.ip.expired" /> <%=request.getRemoteAddr()%> , Username is : <s:property value='#session["SPRING_SECURITY_LAST_USERNAME"]'/>
								 	<%		
							 				}else{
							 		%>
								 		<s:text name="page.login.error.invalid.login" /> 
								 	<%
							 				}
										}
									%>
								</FONT>
							</DIV>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div style="position: absolute; padding-left:3%; top: 90%;width: 94%;">
			<jsp:include page="../footer.jsp"></jsp:include>
		</div>

		<script>
			function elementVisible(jselement)
			{ 
				do
				{
					if (jselement.style.display.toUpperCase() == 'NONE')
						return false;
					jselement=jselement.parentNode; 
				}
				while (jselement.tagName.toUpperCase() != 'BODY'); 
				return true;
			}
			if(elementVisible(document.forms[0].elements['j_username']))
				document.forms[0].elements['j_username'].focus();

			
			//add by mingyang.li 2012.04.04
			/*
			if(navigator.appName=="Microsoft Internet Explorer")
			{
				if(navigator.userAgent.indexOf("MSIE 5.5")>0 || navigator.userAgent.indexOf("MSIE 6.0")>0)
				{
					alert('Please use IE7 or later!');
				};
			}
			else
			{
				alert('Please use IE browser!')
			}
			*/
		</script>

	</body>
</html>
