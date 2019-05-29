<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<script type="text/javascript">
var a = '<%=request.getAttribute("errorMessage").toString()%>';
	parent.uploadCallbackInput(a);
</script> 
