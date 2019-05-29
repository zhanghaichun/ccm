<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html >
<head>
<title><s:text name="page.passwordreminder.title"/></title>
<link REL="stylesheet" href="css/styles.css" type="text/css">
<link rel="stylesheet" type="text/css" href="include/css/ccm.css" />
</head>
<body class="loginBody">
<script language = "JavaScript">
function OnKeyDown()
{
	e = window.event;
	if (e.keyCode == 13)
	{
		e.cancel = true;
		document.forms[0].submit();
	}	
}

function UpdateControls(searchBy)
{
    document.forms.form1.searchBy.value = searchBy;
    
	if (document.forms.form1.searchBy.value=="email") 
	{
		document.forms.form1.username.style.backgroundColor='gainsboro';
		document.forms.form1.email.style.backgroundColor='white';
		document.forms.form1.username.disabled=true; 
		document.forms.form1.email.disabled=false;
	}
	else
	{
		document.forms.form1.username.style.backgroundColor='white';
		document.forms.form1.email.style.backgroundColor='gainsboro';
		document.forms.form1.username.disabled=false; 
		document.forms.form1.email.disabled=true;
	}
}
</script>
<table align="center" border="0" width="850px">
		<tr>
			<td height=100 class="logo" valign="middle">
				<div class="login"></div>
			</td>
			<td></td>
		</tr>
		<tr>
			<td colspan="2">
				<h1><s:text name="page.passwordreminder.title"/></h1>
				
			</td>			
		 </tr>
		 <tr>
		 	<td colspan="2">
		 	<!-- start form table -->
		 	
		 	<form method="post" action="doPwdReset.action" id="form1" name="form1">
			<input type="hidden" name="btnSubmit" value="Remind"/>
			<s:if test="searchBy!='email'">
  				<input type="hidden" name="searchBy" value="username"/>
			</s:if>
			<s:else>
  				<input type="hidden" name="searchBy" value="email"/>
			</s:else>
		 	
		 	<table id="passwordTable">
				<tr> 
					<td colspan="2"><s:text name="page.passwordreminder.text.instructions"/></td>
				</tr>
				<tr id="username_block">
				
  				<s:if test="searchBy!='email'">
					<td align="left" nowrap="nowrap" width="20%"> 
						<input name="remtype" type="radio" onclick="UpdateControls('username');" checked="checked" value="username" style="display: none;" />
		   				&nbsp;&nbsp;<s:text name="page.passwordreminder.label.username"/>:
					</td>
					<td width="80%"> 
						<input name="username" value="<s:property value='username'/>" style="background-color:white;"/>
					</td>
  				</s:if>
  						
  				<s:else>
  					<td align="left"  nowrap="nowrap" > 
						<input name="remtype" type="radio" onclick="UpdateControls('username');" value="username" "/>
		   				&nbsp;&nbsp;<s:text name="page.passwordreminder.label.username"/>:
					<td > 
						<input name="username" value="" disabled="true" style="background-color:gainsboro;"/>
					</td>  
  				</s:else>

				</tr>
				<tr id="email_block"> 
  
  				<s:if test="searchBy!='email'">
					<td align="left"  nowrap="nowrap" style="display: none"> 
						<input type="radio" name="remtype" onclick="UpdateControls('email');" value="email"/>
						&nbsp;&nbsp;<s:text name="page.passwordreminder.label.email"/>:
					</td>
					<td style="display: none">    
						<input name="email" value="" disabled="true" style="background-color:gainsboro;"/>
					</td>
  				</s:if>
  
  				<s:else>
					<td align="left"  nowrap="nowrap" style="display: none"> 
						<input type="radio" name="remtype" onclick="UpdateControls('email');" checked="checked" value="email"/>
						&nbsp;&nbsp;<s:text name="page.passwordreminder.label.email"/>:
					</td>
					<td style="display: none">    
						<input name="email" value="<s:property value='email'/>" style="background-color:white;"/>
					</td>		  
  				</s:else>
  				
  				<s:if test="hasActionErrors()">	
	  				<div><font color="red"><s:actionerror/></font></div>
				</s:if>
  					
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>
						<input type="submit" value="<s:text name='Submit'/>" class="button">
						&nbsp;&nbsp;<a href="login.action"><s:text name="page.passwordreminder.link.backtologin"/></a>
					</td>
				</tr>
			</table>
		 	
		 	</form>
		 	<!-- end form table -->
		 	
		 	</td>
		 </tr>
</table>

<div id="spacer"></div>
<jsp:include page="../footer.jsp"></jsp:include>

</body>
</html>

