<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>SPS</title>
<link href="<%=request.getContextPath() %>/include/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	var ContextPath = '<%=request.getContextPath() %>';
</script>
<script type="text/javascript" src="<%=request.getContextPath() %>/include/js/jquery.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/include/js/jsjac/js/Command.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/include/js/jsjac/js/JSJaC.js"></script>

        <script type="text/javascript">
            Command.username = '4';
			Command.password = '1';
			Command.ContextPath = '<%=request.getContextPath() %>';
           	Command.init();
            Command.receive = function(cmd) {
	            console.debug(cmd.event);
				console.debug(cmd.pictureId);
				
            	//alert(message);
            }

			var userId = 2;
			Command.isUserOnline(userId, function(isOnline) {
				if (isOnline == "Y") {
					alert("在线");
				} else {
					alert("离线");
				}
			});
			
			var params = {
				lockUserId : 2,
				tableName : "tb_round",
				tableId : 2,
				userType : "J"
			};
			
			Command.lockUser(params, function(result) {
				alert(result);
			});
            // ]]>
        </script>
</head>
</html>
