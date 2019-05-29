<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="include/javascript/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript">
	alert("${bvo.pageMessage}");
	//parent.common_Json.clearForm_YUI("banDetail_frm");
	parent.hideLoadingModalLayer();
	$(document).ready(function(){
		if(parent.tiffPage != null){
			parent.tiffPage.reload();
		}else{
			var banId = $("[name='b.id']").val();
			parent.banDetailPage_Json.searchBanTraff(banId);
			parent.banDetailPage_Json.searchBanExemptionCertificate(banId);
			parent.$("[name='banId']").val(banId);
			parent.$("#banDetail_frm_b_id").val(banId);
		}
// 		parent.banStatusId = parent.$("[name='b.banStatus.id']").val();
    });
</script>
<s:hidden name="b.id"/>
