<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	var data = ${uploadsMessage} ;
	try{
		alert(data.error);
	}catch(e){
		alert(data);
	}
	parent.hideLoadingModalLayer();  
</script> 