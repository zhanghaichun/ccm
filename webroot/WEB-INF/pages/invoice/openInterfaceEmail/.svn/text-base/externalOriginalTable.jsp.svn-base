<html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <head>
 	<meta charset="utf-8">
    <link rel="stylesheet" href="include/css/hint.min.css">
    <script src="include/javascript/jquery/jquery-1.4.2.js"></script>
 </head>
<style>
#originalFile{
            width: 800px;
            height: 100px;
            position: fixed;
            top: 0px;
            left: 0px;
            right: 0px;
            bottom: 0px;
            margin: auto;
            text-align:center;
}
</style>
<script type="text/javascript">

function downloadFile(invoiceId,originalId){
	var uri = "${systemUrlAdress}/downloadInvoiceOriginalFile.action?invoiceId="+invoiceId+"&originalId="+originalId;
	window.open(uri);
}

</script>
<body> 
<div id="originalFile">
	<table border="1" width="100%">
	<caption>Original File Download</caption>
	
	<c:forEach items="${list}" var="item" varStatus="i">
		<tr>
			<td width="80%">${item.file_name}</td>
			<td width="20%" align="center"><img src="include/images/download1.gif" style="cursor: pointer;" onclick="downloadFile('${item.invoice_id}','${item.original_id}')"></td>
		</tr>
	</c:forEach> 
	</table>
</div>
  
</body>
</html>