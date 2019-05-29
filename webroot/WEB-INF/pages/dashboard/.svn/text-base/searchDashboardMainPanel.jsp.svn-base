<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<LINK type="text/css" href="include/javascript/jquery/App_Themes/flick/jquery.ui.all.css" rel="stylesheet"> 
<SCRIPT type="text/javascript" src="include/javascript/jquery/jquery.corner.js"></SCRIPT> 
<SCRIPT type="text/javascript" src="include/javascript/jquery/Portlets_files/jquery.ui.core.js"></SCRIPT> 
<SCRIPT type="text/javascript" src="include/javascript/jquery/Portlets_files/jquery.ui.widget.js"></SCRIPT> 
<SCRIPT type="text/javascript" src="include/javascript/jquery/Portlets_files/jquery.ui.mouse.js"></SCRIPT> 
<SCRIPT type="text/javascript" src="include/javascript/jquery/Portlets_files/jquery.ui.sortable.js"></SCRIPT> 

<script type="text/javascript" src="include/javascript/ccm/quicklink.js" language="javascript"></script>
<LINK href="include/javascript/ccm/dashboard/dashboard.css" type=text/css rel=stylesheet />
<script type="text/javascript" src="include/javascript/ccm/dashboard/dashboard.js"></script>

<STYLE type="text/css"> 
.column { width: 456px; float: left; padding-bottom: 100px; margin-right: 8px;}
.portlet { margin-bottom: 10px;}
.portlet-header {  margin: 8px auto; padding: 5px; width: 94%; }
.portlet-header .ui-icon { float: right; }
.portlet-content { padding: 0.4em; }
.portlet-content img { max-width: 430px; }
.ui-sortable-placeholder { border: 1px dotted black; visibility: visible !important; height: 50px !important; }
.ui-sortable-placeholder * { visibility: hidden; }
</STYLE> 


<div id="pageContainer">
	<div id="loadingDiv" style="background: gray;position: absolute;"></div>
	<div id="footerSaveDiv" class="dashboard_head" style="padding-left: 15px;">
		<div><h3 style='color:#fff;width: 70%;float: left;'>&clubs; Dashboard Page </h3></div>
		<div style="float: left;width: 29%">
			<h3 style="float: right;">
				<a href="javascript:void(0)" 
						onclick="dashboardPage.savePostion();"
						onmouseout="javascript:this.style.color='black';"
						onmouseover="javascript:this.style.color='white';"
						style='color:black;text-decoration: none;border-left: 1px solid white;border-right:  1px solid white;'>
					&nbsp;&nbsp;Save The Position&nbsp;&nbsp;
				</a>
			</h3>
		</div>
	</div>
	<center>
		<div style="width: 953; margin-top: 15px;">
			${pageHTML}
		</div>
	</center>
</div>