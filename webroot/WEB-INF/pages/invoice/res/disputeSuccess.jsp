<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	parent.initializationList();
	parent.getICDOM("txtOutstandingAmount").value = ${uploadsMessage};
	parent.hideLoadingModalLayer(); 
	parent.eidtDisputePanel.hide(); 
</script>