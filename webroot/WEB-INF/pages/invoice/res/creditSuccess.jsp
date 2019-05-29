<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	parent.initializationList();
	parent.getICDOM("btnEidtCredit").disabled = "disabled";
	parent.getICDOM("txtOutstandingAmount").value = "0";
	parent.hideLoadingModalLayer(); 
	parent.eidtCreditPanel.hide();
</script>