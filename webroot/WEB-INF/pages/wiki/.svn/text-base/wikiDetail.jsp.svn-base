<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.saninco.ccm.util.SystemUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	pageContext.setAttribute("currentUserThemeId",SystemUtil.getCurrentUserThemeId());
	pageContext.setAttribute("currentUserAuthorities",SystemUtil.getCurrentUserAuthorities());
 %>
<script type="text/javascript">
			if(typeof SANINCO != 'object'){ SANINCO={}; }
			SANINCO.authorities = "";
		<c:forEach items="${currentUserAuthorities}" var="authoritie">
			if(SANINCO.authorities.length==0){
				SANINCO.authorities += "${authoritie}";
			}else{
				SANINCO.authorities += ",${authoritie}";
			}
		</c:forEach>
		</script>
		<c:forEach items="${CCM_SYSTEM_THEMES}" var="the">
			<c:if test="${currentUserThemeId == the.key}">
		<link rel="stylesheet" type="text/css" href="themes/${the.value}/all.css" title="ccm_theme_link_20100716_${the.key}"/>
			</c:if>
		</c:forEach>
		<link rel="stylesheet" type="text/css" href="include/css/ccm.css" />
		<LINK href="include/javascript/yui2/fonts-min.css" type=text/css rel=stylesheet>
		<LINK href="include/javascript/yui2/button.css" type=text/css rel=stylesheet>
		<LINK href="include/javascript/yui2/container.css" type=text/css rel=stylesheet>
		
		<style>img{behavior:url(include/css/ie_bug.htc);}</style>
		<script type="text/javascript"> var CONTEXT_PATH = ""; </script>
		<!-- yui lib -->
		<script type="text/javascript" src="include/javascript/yui2/yahoo.js"></script>
		<script type="text/javascript" src="include/javascript/yui2/yahoo-dom-event.js"></script>
		<script type="text/javascript" src="include/javascript/yui2/jsvalidate.js"></script>
		<script type="text/javascript" src="include/javascript/yui2/connection.js"></script>
		<script type="text/javascript" src="include/javascript/yui2/selector.js"></script>
		<SCRIPT type="text/javascript" src="include/javascript/yui2/element-min.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="include/javascript/yui2/button-min.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="include/javascript/yui2/dragdrop-min.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="include/javascript/yui2/container-min.js"></SCRIPT>
		<!-- ccm lib -->
		<script type="text/javascript" src="include/site.js"></script>
		<script type="text/javascript" src="include/javascript/menu.js"></script>
		<script type="text/javascript" src="include/javascript/cookie.js"></script>
		<script type="text/javascript" src="include/javascript/jquery/jquery-1.4.2.min.js"></script>
		
<%--		<script type="text/javascript" src="include/javascript/jquery/jquery.json-2.4.min.js"></script>--%>
		<script type="text/javascript" src="include/javascript/constants.js"></script>
		<script type="text/javascript" src="include/javascript/ccm/common.js"></script>
		<!-- saninco lib  -->
		<script type="text/javascript" src="include/javascript/saninco/authoritie.js"></script>
		<script type="text/javascript" src="include/javascript/saninco/page.js"></script>
		<script type="text/javascript" src="include/javascript/saninco/filter.js"></script>
		<script type="text/javascript" src="include/javascript/saninco/downLoad.js"></script>
		<!-- saninco lib plug in -->
		<script type="text/javascript" src="include/javascript/saninco/array_Plug_In.js"></script>

<div class="wikipop">
        	
            
			<div class="wikilogo"></div>
        	
            <div class="wikipopbox" style="overflow-y:auto;overflow-x:auto;">
            
            	<table width="100%" border="0" cellpadding="0" cellspacing="2" class="wikipoptable">
                  <tr>
                    <td width="50%">Title:<s:property value="wiki.title"/></td>
                    <td width="19%">Name:<s:property value="wiki.publishUser"/></td>
                    <td width="16%">Date:<s:property value="wiki.publishTime"/></td>
                  </tr>
                  <tr>
                    <td colspan="3" class="tdbox"><p>
                    <input type="hidden" id="wiki" value="<s:property value="wiki.content"/>">
					<script type="text/javascript">
					document.write($('#wiki').val());
					</script>
                    </p></td>
                  </tr>
              </table>
 
            
            </div>
        </div>
