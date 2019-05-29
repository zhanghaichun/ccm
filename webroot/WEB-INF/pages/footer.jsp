<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<table cellpadding='0' cellspacing='10' width='100%' border='0'
	class='underFooter'>
	<tr>
		<td align='center' id="ccm_copyRight_td" class='copyRight'>
			
		</td>
	</tr>
</table>
<script type="text/javascript" src="include/javascript/jquery/jquery-1.4.2.min.js"></script>

<script type="text/javascript">
	jQuery(function(){
		var theFullYear = (new Date()).getFullYear();
		document.getElementById("ccm_copyRight_td").innerHTML = '&copy;&nbsp;' + (theFullYear-8) + "-" + theFullYear + '&nbsp ' + '<s:text name="copyright.company.name" />';
	});
</script>