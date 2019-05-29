<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title><s:text name="common.changePassword"/></title>
<script type="text/javascript">
	if(parent && parent.hideLoadingModalLayer)parent.hideLoadingModalLayer();
	function showChangePwdMassage(){
		if(parent && parent.showChangePwdMassage){
			parent.showChangePwdError(document.getElementById('hasActionErrors_div').innerHTML);
		}
	}
</script>
</head>
<body onload="showChangePwdMassage();">
<div id="wrap">

<script language="JavaScript">
function validate()
{
	if (document.forms.form1.confirmPassword.value!=document.forms.form1.newPassword.value)
	{	
	    alert("<s:text name='page.change.password.text.notmatch.alerterror'/>");
		//alert('Passwords do not match. Re-enter password');
		document.forms.form1.newPassword.value='';
		document.forms.form1.confirmPassword.value='';
		document.forms.form1.newPassword.focus();
		return false;
	}
	return true;
}
</script>
	<jsp:include page="../header.jsp"/>
<!-- content-wrap starts here -->
	<div id="content-wrap"><div id="content">
	

					<!-- main starts here -->
					<div id="main">	
					
			<table width="90%" align="left" border="0" id="passwordTable">
				<tr>
					<td width="150">&nbsp;</td>
					<td colspan="2">
						<h1><s:text name="common.changePassword"/></h1>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td colspan="2">
					<!-- start form table -->
					
					<form method="post" action="doPwdChange.action" id="form1" name="form1" onsubmit="return validate();">
					<input type="hidden" name="btnSubmit" value="Submit"/>
					<input type="hidden" name="go" value="1"/>
					<div id="hasActionErrors_div">
						<s:if test="hasActionErrors()">	
							<font color="red" size="2.5"><s:actionerror/></font>
						</s:if>
					</div>
					<table border="0">
						<tr id="oldpassword_block"> 
							<td> 
								<s:text name="email.change.password.label.old.password"/>&nbsp;<span class="required">*</span>
							</td>
							<td> 
								<input name="oldPassword" type="password" />
							</td>
						</tr>
						<tr id="newpassword_block"> 
							<td> 
								<s:text name="email.change.password.label.new.password"/>&nbsp;<span class="required">*</span>
							</td>
							<td> 
								<input type="password" name="newPassword" />
							</td>
						</tr>
						<tr id="confirmpassword_block"> 
							<td> 
								<s:text name="email.change.password.label.confirm.password"/>&nbsp;<span class="required">*</span>
							</td>
							<td> 
								<input type="password" name="confirmPassword" />
							</td>
							
	  						
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>
								<input type="submit" value="<s:text name='page.change.password.button.value.submit'/>" class="button"/>
							</td>
						</tr>
					</table>

					</form>
					<!-- end form table -->
					</td>
				</tr>
				
			</table>
			
			<br clear="all" />
			<br clear="all" />
<div id="spacer"></div>

<jsp:include page="../footer.jsp"></jsp:include>
					<!-- main ends here -->	
					</div>	
				
	<!-- content-wrap ends here -->		
	</div></div>

</div>
</body>
</html>

