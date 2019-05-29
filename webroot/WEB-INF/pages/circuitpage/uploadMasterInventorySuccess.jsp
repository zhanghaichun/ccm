<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
parent.hideLoadingModalLayer();
<c:if test="${returnMap.validationFailure == 'Y'}">
	alert('Upload File Format Error: Please use the provided template and try again.');
</c:if>
<c:if test="${returnMap.maxSize == 'Y'}">
	alert('The maximum size of uploaded file is 5M.');
</c:if>

<c:if test="${returnMap.validationFailure != 'Y'}">
	<c:if test="${returnMap.isSuccess == 'Y'}">
		alert('File Uploaded Sucessfully.');
		parent.startSearch();
	</c:if>
	<c:if test="${returnMap.isSuccess == 'N'}">
		parent.showMasterInventoryWindow('${returnMap.errorFilePath}');
	</c:if>
</c:if>
</script>