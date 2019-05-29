<html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <head>
 	<meta charset="utf-8">
    <link rel="stylesheet" href="include/css/hint.min.css">
    <script src="include/javascript/jquery/jquery-1.4.2.js"> </script>

<script>
     
   jQuery(function(){
		var theFullYear = (new Date()).getFullYear();
		document.getElementById("ccm_copyRight_td").innerHTML = '&copy;&nbsp;' + (theFullYear-8) + "-" + theFullYear + '&nbsp ' + '<s:text name="copyright.company.name" />';
	});
     
   function Cancel() {
          if (confirm("Are you sure you want to close this page?")){
                window.close();}
    }
   function Save(e) {
   
   
    document.getElementById(e.id).disabled=true;
    document.getElementById("cancel_button").disabled=true;

    $.ajax({
	    url: "doExternalCommentsNote.action",
	    type: "POST",
	    dataType: "text",
	    data: ("userName=" + document.getElementById("_userName").value + "&invoiceId="+ document.getElementById("_invoiceId").value+ "&workflowUserName="+document.getElementById("_workflowUserName").value+ "&userId="+document.getElementById("_userId").value+ "&externalEmailId="+document.getElementById("_externalEmailId").value+ "&notes="+encodeURIComponent(document.getElementById("_notes").value)+ "&typeAction="+document.getElementById("_typeAction").value),
	    success: function(o){
	       var value = eval("(" + o + ")");
			if(value.error){
				alert(value.error);
			}	
            if (value.success){
                  alert(value.success);
                  window.close(); 
            }
            if (value.OUT){
                  window.location.href = "doExternalCommentsFromEmail.action?invoiceNumber=" + document.getElementById("_invoiceNumber").value+"&externalEmailId="+document.getElementById("_externalEmailId").value;
            }
			
			
	    },
		error : function(o){    
			alert("Error");
	    },

	  });
     
      
     }
     
    </script>
 </head>
<style>
body {
  margin: 0;
  padding: 0;
}
.underFooter {
     position:  fixed;
    bottom:  0px;
    margin: auto;
    width: 100%;
    height: 50px;
    left:  0px;
    color: #999999;
    background-color: #f1f9fc;
    border-color: #2e6291;
}
#approveId{
            width: 400px;
            position: fixed;
            top: 200px;
            left: 0px;
            right: 0px;
            bottom: 0px;
            margin: auto;
}
.headerBg {
		border-color: #999;
		color: #000000; 
		background-image: url(themes/Default/images/bgNavyHeader.gif);
		background-position: top right;
        background-size: cover;
        background-repeat: no-repeat;
        height: 80px;
	}
	

.login{
     height:40px;
     width:214px; 
     background-image: url(include/images/company_logo.png)!important;/* FF IE7 */ 
     background-repeat: no-repeat; 
     _filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src='include/images/company_logo.png'); /* IE6 */ 
     _background-image: none; /* IE6 */ 
     position: fixed;
     top: 10px;
     margin-left: 20px;
}

</style>
<body >
       <div width="100%" cellspacing="0" cellpadding="0" border="0" class="headerBg">
	      <div class="login"></div>
	   </div>
       <div id = "approveId">
         
                    <input  id = "_userName" name="userName" type="hidden" value="${userName}"/>
                    <input  id = "_invoiceId" name="invoiceId" type="hidden" value="${invoiceId}"/>
                    <input  id = "_workflowUserName" name="workflowUserName" type="hidden" value="${workflowUserName}"/>
                    <input  id = "_userId" name="userId" type="hidden" value="${userId}"/>
                    <input id = "_externalEmailId"  name="externalEmailId" type="hidden" value="${externalEmailId}"/>
                    <input id = "_typeAction"  type="hidden" value="${typeAction}"/>
                    <input id = "_invoiceNumber"  type="hidden" value="${invoiceNumber}"/>
                    
                    
               <div style="padding:5px 0px; font-size: 20px; font-weight: bold;">Comments</div>
               <textarea name="notes" autofocus="true" id="_notes"  style="padding:0px 1px;width:500px;height:160px; resize: none;"></textarea>
           <div style="margin-top :10px">
   
                 <c:if test="${typeAction == 'Approve' }">  
                  <input  id = "approve_button" type="button" value="Approve" onclick="Save(this)"/>
                 </c:if> 
                  <c:if test="${typeAction == 'Reject' }">  
                   <input  id = "reject_button" type="button" value="Reject" onclick="Save(this)"/> 
                  </c:if>   
               <input  id = "cancel_button" type="button" value="Cancel" onclick="Cancel()"/>
           </div>
		</div>
					
		<div cellpadding="8" cellspacing="8" width="100%" border="0" class="underFooter">
               <div align="center"  style="color: #000;   font-size: 15px; " id = "ccm_copyRight_td"> </div>
        </div>

  
</body>

</html>