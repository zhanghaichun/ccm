<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript">
			function errorHandle(){
				parent.hideLoadingModalLayer();
				parent.addErrorMessage(document.getElementById("errorMsg").innerHTML);
			}
		</script>
	</head>
	<body onload="errorHandle()">
		<div id="errorMsg">
			<font color="red" size="2.5">
				<s:fielderror></s:fielderror>
				<s:actionerror></s:actionerror>
			</font>
		</div>
	</body>
</html>