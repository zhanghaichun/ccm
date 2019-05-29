<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	if(parent && parent.hideLoadingModalLayer){
		parent.window.location.href="searchInvoice.action";
	}
</script>
<html>
<body>
<p align="center">Access is Denied.</p><br>
<p align="center"><%=request.getAttribute("errorMessage") %></p><br>
</body>
</html>