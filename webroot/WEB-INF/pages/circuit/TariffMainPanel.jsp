<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<SCRIPT type="text/javascript"
	src="include/javascript/ccm/common/common.js"></SCRIPT>
	


<style>

.td-values-wrap {
	width:240px;
	word-wrap:break-word;
}
.ggcon {
	width:100%;
	background-color:#ededed;
}



.topggbg {
	background-color:#f7f7f7;
	border-bottom:1px solid #b4b4b4;
	height:32px;
	line-height:32px;
	text-align:center;
	font-weight:bold;
	font-size:11px;
	width:100%;
}
.twolr {
	width:100%;
}

.ggl {
	float:left;
}
.ggr {
	float:right;
}
.ggn {
	width:130px;
}
.ggm {
	width:100px;
}

.Cir_Det_table1 td {
	padding: 1px 0;
}


.th333 {
	border-bottom: 1px solid #e4e4e4;
	height: 18px;
	line-height: 18px;
	text-align: left;
	color: #595758;
}
.datass table{
padding:0 5px;
}

.xzdown45 dl{
max-width:1070px;}
.xzdown45 dl dd{
float: left;
    width: 345px;
    margin-right: 10px;}
    .sgkle3{
width:537px;
float:left;}
.sgkrg3{
width:537px;float:left;

        border-left: 1px solid #b4b4b4;}
</style>

<div class="update-ReportAdminTags" style="width:1100px; border-top:1px #666666 solid;border-bottom:1px #666666 solid;" id="circuitDetail">
  <div class="tabForm">
    <div class="Cir_Det" id = "heightID">
      <!--start yes-->
     
     <div class="ggcon" id="allheight">
       <div class="topggbg">Cost Detail</div>
      <div class="sgk3">
       <div class="sgkle3"><ul>      
       <li><div class="sgkjuzuo2">Recent Rate Change</div><div class="sgkjuyou2">${tariff.recent_rate_change}</div></li>
       <div style="clear:both;"></div>
       </ul></div>
      
         <div class="sgkrg3"><ul>
         <li><div class="sgkjuzuo2">Effective Date</div><div class="sgkjuyou2"><fmt:formatDate value="${tariff.effective_date}" type="date"/></div></li>
        
       <div style="clear:both;"></div>
       </ul></div>
         <div style="clear:both;"></div>
      </div>
      
     <div class="xzdown45">
     <dl>
     <dt>File Download</dt>
              <c:forEach items="${Attachment.file}" var="jl" varStatus="i">
              <dd>${jl.fileName}<span class="tbdow" onClick="Download('${jl.fileName}','${jl.filePath}');"></span></dd>
              </c:forEach>  
         <div style="clear:both;"></div> </dl>
     </div>
              <form id="Download" action="download.action" method="post" style="display: none;">
	          <input type="hidden" name="filePath" value=""/>
		      <input type="hidden" name="fileName" value=""/>
	          </form>
        </div>
        <!--leftggcon-->

      <!--start no-->
      
      
      
      
    </div>
    <div style="width:100%; height:40px; overflow:hidden;">
      <div style="float: left; margin-top:10px;">
        <input type="button" onClick="closeCircuitDetail()" value="Close"/>
        <!--&nbsp;
        <input type="button" id="saveQueryButton" value="Save" />-->
      </div>
    </div>
  </div>
</div>
